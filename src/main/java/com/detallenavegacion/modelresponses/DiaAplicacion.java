package com.detallenavegacion.modelresponses;

import java.time.LocalDate;



public class DiaAplicacion {
	private String msisdn;
	private LocalDate fecha;
	private String application;
	private int code_application;
	private long downlink_traffic;

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public int getCode_application() {
		return code_application;
	}

	public void setCode_application(int code_application) {
		this.code_application = code_application;
	}

	public long getDownlink_traffic() {
		return downlink_traffic;
	}

	public void setDownlink_traffic(long downlink_traffic) {
		this.downlink_traffic = downlink_traffic;
	}
}
