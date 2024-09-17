package com.detallenavegacion.modelresponses;

import java.io.Serializable;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "id", "nombre", "total", "fecha", "hora" })
public  class Reporte2 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String nombre;
	private long total;
	private String fecha;
	private LocalDate fechaDate;
	private String hora;

	public Reporte2(long id, String nombre, long total, String fecha,LocalDate fechaDate, String hora) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.total = total;
		this.fecha = fecha;
		this.fechaDate = fechaDate;
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
	
	

	public LocalDate getFechaDate() {
		return fechaDate;
	}

	public void setFechaDate(LocalDate fechaDate) {
		this.fechaDate = fechaDate;
	}

	@Override
	public String toString() {
		return "Reporte [id=" + id + ", nombre=" + nombre + ", total=" + total + ", fecha=" + fecha + ", hora=" + hora + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reporte2 other = (Reporte2) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	

	
	
}
