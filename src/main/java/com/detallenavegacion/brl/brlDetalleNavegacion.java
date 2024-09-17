package com.detallenavegacion.brl;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Named;
import javax.jws.WebMethod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractSessionConfiguration;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;


import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.detallenavegacion.model.dn_detalle_dia_application;
import com.detallenavegacion.model.dn_detalle_hora_application;
import com.detallenavegacion.modelresponses.DiaAplicacion;

import com.detallenavegacion.modelresponses.HoraAplicacion;
import com.detallenavegacion.modelresponses.Reporte2;
import com.detallenavegacion.modelresponses.Reporte3;
import com.detallenavegacion.util.Comparador;
import com.detallenavegacion.util.Constante;
import com.detallenavegacion.util.Parametros;
import com.detallenavegacion.util.UtilDate;
import static com.detallenavegacion.util.UtilDate.DateToStringFormatted;
import static com.detallenavegacion.util.UtilDate.OnlyDateToStringFormatted;
import static com.detallenavegacion.util.UtilDate.convertToLocalDate;
import static com.detallenavegacion.util.UtilDate.formatDate;
import com.detallenavegacion.xml.report.GetConsumoPorHoraResponse;
import com.detallenavegacion.xml.report.Reporte;
import com.detallenavegacion.xml.report.ResponseReporte;

@SuppressWarnings("unused")
@Component
public class brlDetalleNavegacion implements Serializable {
	private static final long serialVersionUID = 1L;
	public static Logger log = LogManager.getLogger(brlDetalleNavegacion.class);

	List<String> listApplicationNames;

	@Autowired
	private detalleHoraRepository detalle_Repository;

	@Autowired
	private detalleDiaRepository detalle_dia_Repository;

	@Autowired
	public brlDetalleNavegacion(detalleHoraRepository detalle_Repository, detalleDiaRepository detalle_dia_Repository) {
		this.detalle_Repository = detalle_Repository;
		this.detalle_dia_Repository = detalle_dia_Repository;
	}

	@WebMethod()
	public List<Reporte> getConsumoPorHora(String isdn, Date fechaInicio, Date fechaFin, GetConsumoPorHoraResponse rr)
			throws Exception {
		log.info("isdn: " + isdn + ", fechaInicio: " + UtilDate.dateToString(fechaInicio, "dd-MM-yyyy HH:mm:ss")
				+ ", fechaFin: " + UtilDate.dateToString(fechaFin, "dd-MM-yyyy HH:mm:ss"));
		long total = 0;

		Map<String, Reporte2> hash = new HashMap<>();

		BigInteger minDowlinkTraffic = BigInteger.ZERO;

		if (Parametros.flagMinDowlinkTraffic) {
			minDowlinkTraffic = new BigInteger(Parametros.minDowlinkTraffic);
		}
		else{
			minDowlinkTraffic = new BigInteger("0");
		}


		List<Reporte> response = new ArrayList<>();

		SimpleDateFormat sdf2 = new SimpleDateFormat(Parametros.formatoMostrarResponseServicio);
		log.info("FORMATO FECHA RESPONSE: " + Parametros.formatoMostrarResponseServicio);
		LocalDate dFechaInicio = convertToLocalDate(fechaInicio);
		LocalDate dFechaFin = convertToLocalDate(fechaFin);
		// APLICACION_ID - NOMBRE - TOTAL - FECHA_HORA
		listApplicationNames = Arrays.asList(Parametros.listApplicationNames.split(","));
		List<HoraAplicacion> horaAplicacion = getHoraAplicacion(isdn, dFechaInicio, dFechaFin, minDowlinkTraffic, listApplicationNames, rr);
		if (horaAplicacion != null) {

			for (int j = 0; j < horaAplicacion.size(); j++) {
				String hora = "0";
				HoraAplicacion item = horaAplicacion.get(j);
				if (item.getHora() < 10) {
					hora = hora + item.getHora();
				} else {
					hora = String.valueOf(item.getHora());
				}
				Date dFecha = Date.from(item.getFecha().atStartOfDay(ZoneId.systemDefault()).toInstant());
				String fecha = sdf2.format(dFecha);
				Reporte2 reg = new Reporte2(item.getCode_application(), 
				item.getApplication(),
				item.getDownlink_traffic(), 
				fecha, 
				item.getFecha(), 
				hora);
				if (item.getApplication() == null) {
					reg.setNombre(reg.getId() + "");
				}
				total = total + reg.getTotal();
				Reporte2 regHash = hash.get(item.getCode_application() + fecha + hora);
				if (regHash != null) {
					regHash.setTotal(regHash.getTotal() + reg.getTotal());
				} else {
					hash.put(item.getCode_application() + fecha + hora, reg);
				}
			}
			List<Reporte2> response2;
			response2 = hash.values().stream().collect(Collectors.toList());

			Collections.sort(response2, new Comparador());
			for (int i = 0; i < response2.size(); i++) {
				Reporte2 item = response2.get(i);
				Reporte reporte = new Reporte();
				reporte.setId(item.getId());
				reporte.setNombre(item.getNombre());
				reporte.setTotal(item.getTotal());
				reporte.setHora(item.getHora());
				reporte.setFecha(item.getFecha());
				response.add(reporte);
			}
		}
		rr.setTotal(total);
		log.debug("total: " + total + ", size: " + response.size());

		return response;
	}

	public List<HoraAplicacion> getHoraAplicacion(String isdn, LocalDate fechaInicio, LocalDate fechaFin, BigInteger minDowlinkTraffic, List<String> listApplications, GetConsumoPorHoraResponse rr) {
		List<HoraAplicacion> horaAplicacion = new ArrayList<>();
		long iniMetodo = System.currentTimeMillis();
		try {
			try {
				log.info("Tiempo de respuesta solo de la consulta: " + (System.currentTimeMillis()) + " ms");
				log.info("FORMATO FECHA: " + fechaInicio.toString() + " FECHA FIN " + fechaFin.toString());
				log.info("LISTA  APPS: " + listApplications.toString());

				List<dn_detalle_hora_application> queriedAll = this.detalle_Repository.findWithCondition(
					fechaInicio, fechaFin, isdn, minDowlinkTraffic.longValue(), listApplications);
					if (queriedAll.isEmpty()) {
						// Handle empty list scenario (e.g., return an empty list, log a message)
						return Collections.emptyList();
					}
					System.out.println("FILTRO APLICADO - " + queriedAll.size());
						for (int i=0;i<queriedAll.size();i++) {
							LocalDate localDate;
							if (queriedAll.get(i).getFecha() != null) {
								localDate = queriedAll.get(i).getFecha();
							} else {
								log.warn("The attribute 'fecha' is null for an element in the list.");
							}
							HoraAplicacion hora = new HoraAplicacion();
							localDate = queriedAll.get(i).getFecha();
							hora.setFecha(localDate);
							hora.setHora(queriedAll.get(i).getHora());
							hora.setApplication(queriedAll.get(i).getApplication());
							hora.setCode_application(queriedAll.get(i).getCode_application());
							hora.setDownlink_traffic(queriedAll.get(i).getDownlink_traffic().longValue());
							horaAplicacion.add(hora);
						  } 
			} catch (Exception e) {
				log.error("Error al consultar BD: ", e);
				rr.setCode(Constante.ERROR_SISTEMA.getCode());
				rr.setDescription(
						Constante.ERROR_SISTEMA.getDescription() + ": Se obtuvo una conexion pero no valida de la BD");
			}

		} catch (Exception e) {
			rr.setCode(Constante.ERROR_SISTEMA.getCode());
			rr.setDescription(Constante.ERROR_SISTEMA.getDescription() + ": " + e);
			log.error("getHoraAplicacion[Error]: " + e.getMessage(), e);
		} finally {
			log.info("Tiempo de respuesta de HoraAplicacion: " + (System.currentTimeMillis() - iniMetodo) + " ms.");
		}
		return horaAplicacion;
	}
	@WebMethod()
	public List<Reporte> getConsumoPorDia(String isdn, Date fechaInicio, Date fechaFin, GetConsumoPorHoraResponse rr) throws Exception {
		log.info("isdn: " + isdn + ", fechaInicio: " + UtilDate.dateToString(fechaInicio, "dd-MM-yyyy HH:mm:ss") + ", fechaFin: " + UtilDate.dateToString(fechaFin, "dd-MM-yyyy HH:mm:ss"));
		long total = 0;
		Map<String, Reporte2> hash = new HashMap<>();

		SimpleDateFormat sdf = new SimpleDateFormat(Parametros.formatoObtenerFechaParaConsulta);

		String fechaI = sdf.format(fechaInicio);
		String fechaF = sdf.format(fechaFin);

		BigInteger minDowlinkTraffic = BigInteger.ZERO;

		if (Parametros.flagMinDowlinkTraffic) {
			minDowlinkTraffic = new BigInteger(Parametros.minDowlinkTraffic);
		}
		else{
			minDowlinkTraffic = new BigInteger("0");
		}

		List<Reporte> response = new ArrayList<>();

		SimpleDateFormat sdf2 = new SimpleDateFormat(Parametros.formatoMostrarResponseServicio);
		log.info("FORMATO FECHA RESPONSE: " + Parametros.formatoMostrarResponseServicio);
		LocalDate dFechaInicio = convertToLocalDate(fechaInicio);
		LocalDate dFechaFin = convertToLocalDate(fechaFin);
		// APLICACION_ID - NOMBRE - TOTAL - FECHA_HORA
		listApplicationNames = Arrays.asList(Parametros.listApplicationNames.split(","));
		List<DiaAplicacion> lstDiaAplicacion = getDiaAplicacion(isdn, dFechaInicio, dFechaFin, minDowlinkTraffic, listApplicationNames, rr);
		if (lstDiaAplicacion != null) {
			
			for (int j = 0; j < lstDiaAplicacion.size(); j++) {
				String hora = "0";
				DiaAplicacion item = lstDiaAplicacion.get(j);
				Date dFecha = Date.from(item.getFecha().atStartOfDay(ZoneId.systemDefault()).toInstant());
				String fecha = sdf2.format(dFecha);
				Reporte2 reg = new Reporte2(item.getCode_application(), 
				item.getApplication(),
				item.getDownlink_traffic(), 
				fecha, 
				item.getFecha(), 
				"");
				if (item.getApplication() == null) {
					reg.setNombre(reg.getId() + "");
				}
				total = total + reg.getTotal();
				Reporte2 regHash = hash.get(item.getCode_application() + fecha + hora);
				if (regHash != null) {
					regHash.setTotal(regHash.getTotal() + reg.getTotal());
				} else {
					hash.put(item.getCode_application() + fecha + hora, reg);
				}
			}
			List<Reporte2> response2;
			response2 = hash.values().stream().collect(Collectors.toList());

			Collections.sort(response2, new Comparador());
			for (int i = 0; i < response2.size(); i++) {
				Reporte2 item = response2.get(i);
				Reporte reporte = new Reporte();
				reporte.setId(item.getId());
				reporte.setNombre(item.getNombre());
				reporte.setTotal(item.getTotal());
				reporte.setHora(item.getHora());
				reporte.setFecha(item.getFecha());
				response.add(reporte);
			}
		}
		rr.setTotal(total);
		
		log.debug("total: " + total + ", size: " + response.size());
		return response;

	}
	public List<DiaAplicacion> getDiaAplicacion(String isdn, LocalDate fechaInicio, LocalDate fechaFin, BigInteger minDowlinkTraffic, List<String> listApplications, GetConsumoPorHoraResponse rr) {
		List<DiaAplicacion> lstDiaAplicacion = new ArrayList<>();
		try {
		log.info("Tiempo de respuesta solo de la consulta: " + (System.currentTimeMillis()) + " ms");

		List<dn_detalle_dia_application> queriedAll = this.detalle_dia_Repository.findDiaWithCondition(
		fechaInicio, fechaFin, isdn, minDowlinkTraffic.longValue(), listApplications);
					if (queriedAll.isEmpty()) {
						// Handle empty list scenario (e.g., return an empty list, log a message)
						return Collections.emptyList();
					}
					for (int i=0;i<queriedAll.size();i++) {
								LocalDate localDate;
							if (queriedAll.get(i).getFecha() != null) {
								localDate = queriedAll.get(i).getFecha();
							} else {
								log.warn("The attribute 'fecha' is null for an element in the list.");
							}
							DiaAplicacion dia = new DiaAplicacion();
							localDate = queriedAll.get(i).getFecha();
							dia.setFecha(localDate);
							dia.setApplication(queriedAll.get(i).getApplication());
							dia.setCode_application(queriedAll.get(i).getCode_application());
							dia.setDownlink_traffic(queriedAll.get(i).getDownlink_traffic().longValue());
								lstDiaAplicacion.add(dia);
							}
						
					} catch (Exception e) {
						log.error("Error al consultar BD: ", e);
						rr.setCode(Constante.ERROR_SISTEMA.getCode());
						rr.setDescription(Constante.ERROR_SISTEMA.getDescription() + ": Se obtuvo una conexion pero no valida de la BD");
					}
				finally {
			log.info("Tiempo de respuesta de DiaAplicacion: " + (System.currentTimeMillis()) + " ms.");
		}

		return lstDiaAplicacion;
	}
}
@Service
@Repository
@Component
interface detalleHoraRepository extends CassandraRepository<dn_detalle_hora_application, String> {
	@Query("select * from dn_detalle_hora_application where fecha >= :pfechaDesde and fecha <= :pfechaHasta and hora >= 0 and hora <= 23" +
	" and msisdn = :pMsisdn and downlink_traffic > :pdownloadLinkTraffic and code_application > -1 and application in :pListApplication ALLOW FILTERING;")
	List<dn_detalle_hora_application> findWithCondition(
			@Param("pfechaDesde") LocalDate fechaDesde, @Param("pfechaHasta") LocalDate fechaHastaDate,
			@Param("pMsisdn") String msisdn, @Param("pdownloadLinkTraffic") Long downloadLinkTraffic,
			@Param("pListApplication") List<String> listApplicationNames);
}
@Service
@Repository
@Component
interface detalleDiaRepository extends CassandraRepository<dn_detalle_dia_application, String> {
	@Query("select * from dn_detalle_dia_application where fecha >= :pfechaDesde and fecha <= :pfechaHasta " +
	" and msisdn = :pMsisdn and downlink_traffic > :pdownloadLinkTraffic and code_application > -1 and application in :pListApplication ALLOW FILTERING;")
	List<dn_detalle_dia_application> findDiaWithCondition(
			@Param("pfechaDesde") LocalDate fechaDesde, @Param("pfechaHasta") LocalDate fechaHastaDate,
			@Param("pMsisdn") String msisdn, @Param("pdownloadLinkTraffic") Long downloadLinkTraffic,
			@Param("pListApplication") List<String> listApplicationNames);
}
/* 
@Configuration
@SuppressWarnings("unused")
@Service
@EnableCassandraRepositories(basePackages = "com.detallenavegacion.brl")
class CassandraConfiguration extends AbstractSessionConfiguration {

	@Autowired
	Environment env;

	@Override
	protected String getContactPoints() {
		return env.getProperty("cassandra.cluster.contact-points", Parametros.bdIp);
	}

	@Override
	protected String getKeyspaceName() {
		return env.getProperty("cassandra.keyspace", Parametros.bdNombre);
	}

	@Override
	protected int getPort() {
		return env.getProperty("cassandra.cluster.port", Integer.TYPE, Parametros.bdPuerto);
	}

    protected boolean getMetricsEnabled() {
        return false;
    }
}
*/