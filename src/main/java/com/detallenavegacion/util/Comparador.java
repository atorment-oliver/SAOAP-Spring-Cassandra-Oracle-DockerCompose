package com.detallenavegacion.util;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.detallenavegacion.modelresponses.Reporte2;


public class Comparador implements Comparator<Reporte2>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(Reporte2 o1, Reporte2 o2) {
		// TODO Auto-generated method stub
		 return new CompareToBuilder()
	                .append(o1.getFechaDate(), o2.getFechaDate())
	                .append(o1.getHora(), o2.getHora())
	                .append(o2.getTotal(), o1.getTotal()).toComparison();
	}

}
