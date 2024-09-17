package com.detallenavegacion.util;

import java.io.Serializable;

public class Constante implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Respuesta OK = new Respuesta(0, "Ejecutado satisfactoriamente");
	public static final Respuesta ERROR_SISTEMA = new Respuesta(1, "Error en el Sistema");
	public static final Respuesta ERROR_NUMERO_NO_VALIDO = new Respuesta(2, "Numero de Cuenta no valido");
	public static final Respuesta ERROR_FECHA_INICIO_NO_VALIDA = new Respuesta(3, "Fecha Inicio no valida");
	public static final Respuesta ERROR_FECHA_FIN_NO_VALIDA = new Respuesta(13, "Fecha Fin no valida");
	public static final Respuesta ERROR_CATEGORIA = new Respuesta(6, "Categoria no valida, valores validos: APLICACION, APLICACION_DIA");
	public static final Respuesta ERROR_FECHAS_NO_VALIDAS = new Respuesta(7, "La Fecha Inicio no puede ser mayor que la Fecha Fin");
	public static final Respuesta ERROR_VALIDACION = new Respuesta(8, "Error al validar los datos de entrada");
	public static final Respuesta ERROR_TIEMPO = new Respuesta(5, "Tiempo no valido, valores validos: HORA, DIA");

	public static final Respuesta ERROR_FECHA_FIN = new Respuesta(9, "La Fecha Fin tiene que ser menor o igual que la Fecha Actual del Sistema");
	

	public static final String HORA = "HORA";
	public static final String DIA = "DIA";
	public static final String APLICACION = "APLICACION";
	public static final String APLICACION_DIA = "APLICACION_DIA";

	public static final String expresionRegularNroTigo = "EXPRESION.REGULAR.NRO.TIGO";



	public static final String STRING = "STRING";
	public static final String ENTERO = "ENTERO";
	public static final String LONG = "LONG";
	public static final String BOOLEAN = "BOOLEAN";
	public static final String DATE = "DATE";
	public static final String DOUBLE = "DOUBLE";

	public static class Respuesta {
		private Integer code;
		private String description;

		public Respuesta() {
			super();
		}

		public Respuesta(Integer code, String description) {
			super();
			this.code = code;
			this.description = description;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			return "Error [code=" + code + ", description=" + description + "]";
		}

	}

}
