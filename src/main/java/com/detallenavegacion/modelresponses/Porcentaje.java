package com.detallenavegacion.modelresponses;

import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType(propOrder = { "id", "nombre", "total", "porcentaje" })
public class Porcentaje implements Serializable, Comparable<Porcentaje> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String nombre;
	private long total;
	private double porcentaje;

	public Porcentaje() {
		super();
	}

	public Porcentaje(long id, String nombre, long total, double porcentaje) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.total = total;
		this.porcentaje = porcentaje;
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

	public double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}

	@Override
	public String toString() {
		return "Porcentaje [id=" + id + ", nombre=" + nombre + ", total=" + total + ", porcentaje=" + porcentaje + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		long temp;
		temp = Double.doubleToLongBits(porcentaje);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (total ^ (total >>> 32));
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
		Porcentaje other = (Porcentaje) obj;
		if (id != other.id)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (Double.doubleToLongBits(porcentaje) != Double.doubleToLongBits(other.porcentaje))
			return false;
		if (total != other.total)
			return false;
		return true;
	}

	@Override
	public int compareTo(Porcentaje o) {
		Long a = new Long(this.total);
		Long b = new Long(o.getTotal());
		// return a.compareTo(b);
		if (a.longValue() == b.longValue()) {
			return 0;
		} else if (a.longValue() > b.longValue()) {
			return 1;
		} else { // if(a.longValue() < b.longValue())
			return -1;
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
