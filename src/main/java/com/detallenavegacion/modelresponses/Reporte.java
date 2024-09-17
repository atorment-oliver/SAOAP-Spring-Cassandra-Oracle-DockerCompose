package com.detallenavegacion.modelresponses;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "id", "nombre", "total", "fecha", "hora" })
public class Reporte implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String nombre;
	private long total;
	private String fecha;
	private String hora;

	public Reporte(long id, String nombre, long total, String fecha, String hora) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.total = total;
		this.fecha = fecha;
		this.hora = hora;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	@Override
	public String toString() {
		return "Reporte [id=" + id + ", nombre=" + nombre + ", total=" + total + ", fecha=" + fecha + ", hora=" + hora + "]";
	}

}
