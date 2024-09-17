package com.detallenavegacion.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Parametros {

	public final static String configFile = "/";

	protected static final Properties prop = new Properties();
	public static Logger log = LogManager.getLogger(Parametros.class);

	static {
		try {
			Parametros p = new Parametros();
			p.init();
		} catch (Exception e) {
			log.error("Error al cargar archivo de propiedades pathFile: " + configFile, e);
		}
	}
	public static String formatoObtenerFechaParaConsulta;
	public static String formatoMostrarResponseServicio;
	public static String expresionRegularNroTigo;
	public static String ConsultaAplicacionHoraGetConsumoHora;
	public static String ConsultaAplicacionDiaGetConsumoHora;

	public static String jndiConexionBD;

	public static String minDowlinkTraffic;
	public static boolean flagMinDowlinkTraffic;

	public static String bdIp;
	public static int bdPuerto;
	public static String bdNombre;
	public static int bdCantidadConexionsPool;
	public static String bdQueryValidacion;
	public static long bdTimeoutConexion;
	public static int bdConsultaFetchSize;
	public static long tareaReconexionBdMilisegundos;
	public static long tareaValidarConexionBdMilisegundos;
	
	public static boolean bdConectarConCredenciales;
	public static String bdUsuario;
	public static String bdPassword;
	
	public static long dormirHiloAlNoCerrarConexion;

	public static int maxRequestsConnectionHostLocal;
	public static int maxRequestsConnectionHostRemote;
	public static int maxConnectionHostLocal;
	public static int maxConnectionHostRemote;
	public static int connectionTimeOut;
	public static int readTimeOut;
	public static String listApplicationNames;

	private void init() {
		//FileInputStream is = null;
		InputStream is=null;
		try { // Ubicar el archivo en el ClassPath actual
			Properties prop = new Properties();
				prop.load(new FileInputStream("configuration.properties"));

				log.debug("cargando parametros......");
				Parametros.formatoObtenerFechaParaConsulta = prop.getProperty("FORMATO.OBTENER.FECHA.PARA.CONSULTA");
				Parametros.formatoMostrarResponseServicio = prop.getProperty("FORMATO.FECHA.MOSTRAR.RESPONSE.SERVICIO");
				Parametros.expresionRegularNroTigo = prop.getProperty("EXPRESION.REGULAR.NRO.TIGO");
				Parametros.ConsultaAplicacionHoraGetConsumoHora = prop.getProperty("CONSULTA.APLICACION.HORA.GET.CONSUMO.HORA");
				Parametros.ConsultaAplicacionDiaGetConsumoHora = prop.getProperty("CONSULTA.APLICACION.DIA.GET.CONSUMO.HORA");
				Parametros.jndiConexionBD = prop.getProperty("JNDI.CONEXION.BD");

				Parametros.minDowlinkTraffic = prop.getProperty("MIN.DOWNLINK.TRAFFIC");
				Parametros.flagMinDowlinkTraffic = Boolean.valueOf(prop.getProperty("FLAG.MIN.DOWNLINK.TRAFFIC"));

				Parametros.bdIp = prop.getProperty("BD.IP");
				Parametros.bdPuerto = Integer.parseInt(prop.getProperty("BD.PUERTO"));
				Parametros.bdNombre = prop.getProperty("BD.NOMBRE");
				Parametros.bdCantidadConexionsPool = Integer.parseInt(prop.getProperty("BD.CANTIDAD.CONEXIONES.POOL"));
				Parametros.bdQueryValidacion = prop.getProperty("BD.QUERY.VALIDACION");
				Parametros.bdTimeoutConexion = Long.parseLong(prop.getProperty("BD.TIMEOUT.CONEXION"));

				Parametros.bdConsultaFetchSize = Integer.parseInt(prop.getProperty("BD.CONSULTA.FETCHSIZE"));
				Parametros.tareaReconexionBdMilisegundos = Long.parseLong(prop.getProperty("TAREA.RECONEXION.BD.MILISEGUNDOS"));
				Parametros.tareaValidarConexionBdMilisegundos = Long.parseLong(prop.getProperty("TAREA.VALIDAR.CONEXION.BD.MILISEGUNDOS"));
				
				
				Parametros.bdConectarConCredenciales = Boolean.parseBoolean(prop.getProperty("BD.CONECTAR.CON.CREDENCIALES"));
				Parametros.bdUsuario = prop.getProperty("BD.USUARIO");
				Parametros.bdPassword = prop.getProperty("BD.PASSWORD");
				
				Parametros.dormirHiloAlNoCerrarConexion = Long.parseLong(prop.getProperty("DORMIR.HILO.AL.NO.CERRAR.CONEXION"));

				Parametros.maxRequestsConnectionHostLocal = Integer.parseInt(prop.getProperty("MAX.REQUESTS.CONNECTION.HOST.LOCAL"));
				Parametros.maxRequestsConnectionHostRemote = Integer.parseInt(prop.getProperty("MAX.REQUESTS.CONNECTION.HOST.REMOTE"));
				Parametros.maxConnectionHostLocal = Integer.parseInt(prop.getProperty("MAX.CONNECTION.HOST.LOCAL"));
				Parametros.maxConnectionHostRemote = Integer.parseInt(prop.getProperty("MAX.CONNECTION.HOST.REMOTE"));
				Parametros.connectionTimeOut = Integer.parseInt(prop.getProperty("CONNECTION.TIMEOUT.MS"));
				Parametros.readTimeOut = Integer.parseInt(prop.getProperty("READ.TIMEOUT.MS"));
				Parametros.listApplicationNames = prop.getProperty("LIST.APPLICATION.NAMES");
			
		} catch (IOException e) {
			log.error("Error al cargar archivo de propiedades: " + configFile, e);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				log.error("Error al cerrar archivo: ", e);
			}
		}
	}

}
