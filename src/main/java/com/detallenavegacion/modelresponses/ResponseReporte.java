package com.detallenavegacion.modelresponses;


import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "code", "description", "total", "lista" })
public class ResponseReporte implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer code;
	private String description;
	private long total;
	private List<Reporte> lista;

	public ResponseReporte(Integer code, String description) {
		super();
		this.code = code;
		this.description = description;
	}

	public ResponseReporte(Integer code, String description, long total, List<Reporte> lista) {
		super();
		this.code = code;
		this.description = description;
		this.setTotal(total);
		this.lista = lista;
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

	public List<Reporte> getLista() {
		return lista;
	}

	public void setLista(List<Reporte> lista) {
		this.lista = lista;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "ResponseReporte [code=" + code + ", description=" + description + ", total=" + total + ", lista=" + lista + "]";
	}

}
