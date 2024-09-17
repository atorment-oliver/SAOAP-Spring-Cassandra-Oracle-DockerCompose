package com.detallenavegacion.model;


import java.math.BigInteger;
import java.time.LocalDate;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.util.ObjectUtils;

@Table("dn_detalle_hora_application")
public class dn_detalle_hora_application {

	private static final String DN_DETALLE_HORA_APPLICATION_TO_STRING = "{ msisdn = %1$s, fecha = %2$s, hora = %3$d, downlink_traffic = %4$d, application = %5$d, code_application = %6$d }";
	@PrimaryKeyColumn(name = "msisdn", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private final String msisdn;
	@PrimaryKeyColumn(name = "fecha", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private final LocalDate fecha;
	private int hora;
	@PrimaryKeyColumn(name = "downlink_traffic", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
	private final BigInteger downlink_traffic;
	@PrimaryKeyColumn(name = "application", ordinal = 3, type = PrimaryKeyType.PARTITIONED)
	private final String application;
	@PrimaryKeyColumn(name = "code_application", ordinal = 4, type = PrimaryKeyType.PARTITIONED)
	private int code_application;

	public static dn_detalle_hora_application create(String msisdn, LocalDate fecha, int hora, BigInteger downlink_traffic, String application, int code_application) {
		return new dn_detalle_hora_application(msisdn, fecha, hora, downlink_traffic, application, code_application);
	}

	public dn_detalle_hora_application(String msisdn, LocalDate fecha, int hora, BigInteger downlink_traffic, String application, int code_application) {

		this.msisdn = msisdn;
		this.fecha = fecha;
		this.hora = hora;
		this.downlink_traffic = downlink_traffic;
		this.application = application;
		this.code_application = code_application;
	}
	public String getMsisdn() {
		return this.msisdn;
	}

	public LocalDate getFecha() {
		return this.fecha;
	}

	public int getHora() {
		return this.hora;
	}

	public BigInteger getDownlink_traffic() {
		return this.downlink_traffic;
	}

	public String getApplication() {
		return this.application;
	}
	public int getCode_application() {
		return this.code_application;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}


		dn_detalle_hora_application that = (dn_detalle_hora_application) obj;

		return ObjectUtils.nullSafeEquals(this.getMsisdn(), that.getMsisdn())
			|| (ObjectUtils.nullSafeEquals(this.getFecha(), that.getFecha())
			|| ObjectUtils.nullSafeEquals(this.getHora(), that.getHora())
			|| ObjectUtils.nullSafeEquals(this.getDownlink_traffic(), that.getDownlink_traffic())
			|| ObjectUtils.nullSafeEquals(this.getApplication(), that.getApplication())
			|| ObjectUtils.nullSafeEquals(this.getCode_application(), that.getCode_application()));
	}

	@Override
	public int hashCode() {

		int hashValue = 17;

		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getMsisdn());
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getFecha());
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getHora());
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getDownlink_traffic());
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getApplication());
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getCode_application());

		return hashValue;
	}

	@Override
	public String toString() {
		return String.format(DN_DETALLE_HORA_APPLICATION_TO_STRING, getMsisdn(), getFecha(), getHora(), getDownlink_traffic(), getApplication(), getCode_application());
	}
}
