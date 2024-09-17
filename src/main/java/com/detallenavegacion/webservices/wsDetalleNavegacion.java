package com.detallenavegacion.webservices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.detallenavegacion.brl.brlDetalleNavegacion;
import com.detallenavegacion.util.Constante;
import com.detallenavegacion.util.Constante.Respuesta;
import com.detallenavegacion.util.Parametros;
import com.detallenavegacion.util.UtilDate;
import com.detallenavegacion.util.UtilNumber;
import com.detallenavegacion.xml.report.GetConsumoPorHoraResponse;
import com.detallenavegacion.xml.report.Reporte;

@Service
@Component
public class wsDetalleNavegacion {

	public static Logger log = LogManager.getLogger(wsDetalleNavegacion.class);
	@Autowired
	private brlDetalleNavegacion aplicacionBrl;
	
	//////////////////ORACLE////////////////////
	//////////////////ORACLE////////////////////
	//////////////////ORACLE////////////////////



	//////////////////CASSANDRA////////////////////
	//////////////////CASSANDRA////////////////////
	//////////////////CASSANDRA////////////////////
	public GetConsumoPorHoraResponse getConsumoPorHora(String isdn,  Date fechaInicio,  Date fechaFin,  String categoria) {
		log.info("isdn: " + isdn + ", categoria: " + categoria + ", fechaInicio: " + UtilDate.dateToString(fechaInicio, "dd-MM-yyyy HH:mm:ss") + ", fechaFin: " + UtilDate.dateToString(fechaFin, "dd-MM-yyyy HH:mm:ss"));

		long ini = System.currentTimeMillis();
		String tiempo = "";

		GetConsumoPorHoraResponse response = new GetConsumoPorHoraResponse();
		response.setCode(Constante.OK.getCode());
		response.setDescription(Constante.OK.getDescription());
		response.setTotal(0);
		List<Reporte> lista = new ArrayList<>();
		List<Reporte> lista2 = new ArrayList<>();
		lista = response.getLista();
		try {

			// Thread.sleep(15000);
			if (categoria == null) {
				response.setCode(Constante.ERROR_CATEGORIA.getCode());
				response.setDescription(Constante.ERROR_CATEGORIA.getDescription());
				return response;
			}

			if (categoria.equals(Constante.APLICACION)) {
				tiempo = Constante.HORA;
			} else if (categoria.equals(Constante.APLICACION_DIA)) {
				tiempo = Constante.DIA;
			} else {
				response.setCode(Constante.ERROR_CATEGORIA.getCode());
				response.setDescription(Constante.ERROR_CATEGORIA.getDescription());
				return response;
			}

			// Respuesta validar = validar(isdn, fechaInicio, fechaFin, "DIA", categoria);
			Respuesta validar = validar(isdn, fechaInicio, fechaFin, tiempo, categoria);
			log.debug("isdn: " + isdn + ", validar: " + validar);
			if (validar != null) {
				if (validar.getCode() != null) {
					if (validar.getCode() != 0) {
						response.setCode(validar.getCode());
						response.setDescription(validar.getDescription());
						return response;
					}
				} else {
					log.debug("code validacion resuelto a nulo");
					response.setCode(Constante.ERROR_VALIDACION.getCode());
					response.setDescription(Constante.ERROR_VALIDACION.getDescription());
					return response;
				}
			} else {
				log.debug("validacion resuelto a nulo");
				response.setCode(Constante.ERROR_VALIDACION.getCode());
				response.setDescription(Constante.ERROR_VALIDACION.getDescription());
				return response;
			}

			Calendar calInicio = Calendar.getInstance();
			calInicio.setTimeInMillis(fechaInicio.getTime());
			Calendar calFin = Calendar.getInstance();
			calFin.setTimeInMillis(fechaFin.getTime());

			calInicio.set(Calendar.MILLISECOND, 0);
			calFin.set(Calendar.MILLISECOND, 0);

			if (tiempo != null && tiempo.equals(Constante.HORA)) {
				if (categoria.equals(Constante.APLICACION)) {
					log.info("getConsumoPorHora - ");
					lista.addAll(aplicacionBrl.getConsumoPorHora(isdn, calInicio.getTime(), calFin.getTime(), response));
				}
			} else {
				if (tiempo != null && tiempo.equals(Constante.DIA)) {
					if (categoria.equals(Constante.APLICACION_DIA)) {
						lista = aplicacionBrl.getConsumoPorDia(isdn, calInicio.getTime(), calFin.getTime(), response);
					}
				}
			}

		} catch (Exception e) {
			log.error("Error al obtener detalle de navegacion de consumo por hora: ", e);
			response.setCode(Constante.ERROR_SISTEMA.getCode());
			response.setDescription(Constante.ERROR_SISTEMA.getDescription() + ": " + e.getMessage());
		} finally {
			long fin = System.currentTimeMillis();
			log.info("Tiempo de respuesta del servicio, consumo por hora: isdn: " + isdn + ", " + (fin - ini) + " milisegundos");
		}
		return response;
	}

	private Respuesta validar(String isdn, Date fechaInicio, Date fechaFin, String tiempo, String categoria) {
		log.debug("isdn: " + isdn + ", tiempo: " + tiempo + ", categoria: " + categoria + ", fechaInicio: " + UtilDate.dateToString(fechaInicio, "dd-MM-yyyy HH:mm:ss") + ", fechaFin: " + UtilDate.dateToString(fechaFin, "dd-MM-yyyy HH:mm:ss"));
		Respuesta response = new Respuesta(Constante.OK.getCode(), Constante.OK.getDescription());
		try {
			if (isdn == null || !UtilNumber.esNroTigo(isdn, Parametros.expresionRegularNroTigo)) {
				response.setCode(Constante.ERROR_NUMERO_NO_VALIDO.getCode());
				response.setDescription(Constante.ERROR_NUMERO_NO_VALIDO.getDescription());
				return response;
			}
			if (tiempo == null) {
				response.setCode(Constante.ERROR_TIEMPO.getCode());
				response.setDescription(Constante.ERROR_TIEMPO.getDescription());
				return response;
			} else {
				if (tiempo.trim().isEmpty() || (!tiempo.equals(Constante.HORA) && !tiempo.equals(Constante.DIA))) {
					response.setCode(Constante.ERROR_TIEMPO.getCode());
					response.setDescription(Constante.ERROR_TIEMPO.getDescription());
					return response;
				}
			}

			if (fechaInicio == null) {
				response.setCode(Constante.ERROR_FECHA_INICIO_NO_VALIDA.getCode());
				response.setDescription(Constante.ERROR_FECHA_INICIO_NO_VALIDA.getDescription());
				return response;
			}

			if (fechaFin == null) {
				response.setCode(Constante.ERROR_FECHA_FIN_NO_VALIDA.getCode());
				response.setDescription(Constante.ERROR_FECHA_FIN_NO_VALIDA.getDescription());
				return response;
			}
			if (categoria == null) {
				response.setCode(Constante.ERROR_CATEGORIA.getCode());
				response.setDescription(Constante.ERROR_CATEGORIA.getDescription());
				return response;
			} else {
				if (categoria.trim().isEmpty() || (!categoria.equals(Constante.APLICACION) && !categoria.equals(Constante.APLICACION_DIA))) {
					response.setCode(Constante.ERROR_CATEGORIA.getCode());
					response.setDescription(Constante.ERROR_CATEGORIA.getDescription());
					return response;
				}
			}

			Calendar calActual = Calendar.getInstance();
			Calendar calInicio = Calendar.getInstance();
			calInicio.setTimeInMillis(fechaInicio.getTime());
			Calendar calFin = Calendar.getInstance();
			calFin.setTimeInMillis(fechaFin.getTime());

			calActual.set(Calendar.MILLISECOND, 0);
			calInicio.set(Calendar.MILLISECOND, 0);
			calFin.set(Calendar.MILLISECOND, 0);
			
			if (tiempo.equals(Constante.DIA)) {
				calActual.set(Calendar.HOUR_OF_DAY, 0);
				calActual.set(Calendar.MINUTE, 0);
				calActual.set(Calendar.SECOND, 0);
				calInicio.set(Calendar.HOUR_OF_DAY, 0);
				calInicio.set(Calendar.MINUTE, 0);
				calInicio.set(Calendar.SECOND, 0);
				calFin.set(Calendar.HOUR_OF_DAY, 0);
				calFin.set(Calendar.MINUTE, 0);
				calFin.set(Calendar.SECOND, 0);
			} else {
				calActual.set(Calendar.MINUTE, 0);
				calActual.set(Calendar.SECOND, 0);
				calInicio.set(Calendar.MINUTE, 0);
				calInicio.set(Calendar.SECOND, 0);
				calFin.set(Calendar.MINUTE, 0);
				calFin.set(Calendar.SECOND, 0);
			}

			if (calInicio.getTimeInMillis() > calActual.getTimeInMillis()) {
				response.setCode(Constante.ERROR_FECHA_INICIO_NO_VALIDA.getCode());
				response.setDescription(Constante.ERROR_FECHA_INICIO_NO_VALIDA.getDescription());
				return response;
			}

			if (calFin.getTimeInMillis() > calActual.getTimeInMillis()) {
				response.setCode(Constante.ERROR_FECHA_FIN.getCode());
				response.setDescription(Constante.ERROR_FECHA_FIN.getDescription());
				return response;
			}

			if (calInicio.getTimeInMillis() > calFin.getTimeInMillis()) {
				response.setCode(Constante.ERROR_FECHAS_NO_VALIDAS.getCode());
				response.setDescription(Constante.ERROR_FECHAS_NO_VALIDAS.getDescription());
				return response;
			}

		} catch (Exception e) {
			log.error("Error al validar los datos de entrada: ", e);
			response.setCode(Constante.ERROR_SISTEMA.getCode());
			response.setDescription(Constante.ERROR_SISTEMA.getDescription());
		}
		return response;
	}

}
