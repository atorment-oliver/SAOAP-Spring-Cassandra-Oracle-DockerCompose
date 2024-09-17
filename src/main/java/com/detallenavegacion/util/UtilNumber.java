package com.detallenavegacion.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UtilNumber implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Logger log = LogManager.getLogger(UtilNumber.class);

	public static boolean esNroTigo(String isdn, String expresionRegular) {
		// log.info("Parametros.expresionRegularNroTigo: " + Parametros.expresionRegularNroTigo);
		Pattern pattern = Pattern.compile(expresionRegular);
		// Pattern pat = Pattern.compile("^[0-9]{8}$");
		Matcher matcher = pattern.matcher(isdn);
		if (matcher.find()) {
			log.debug("nro=" + isdn + " es valido");
			return true;

		}
		log.debug("nro=" + isdn + " NO es valido");
		return false;
	}

	public static double redondear(double numero, int digitos) {

		// BigDecimal bd = new BigDecimal(numero);
		// bd.setScale(digitos, RoundingMode.HALF_UP);
		// return bd.doubleValue();

		int cifras = (int) Math.pow(10, digitos);
		return Math.rint(numero * cifras) / cifras;
	}

	public static String doubleToString(double number, int nroDecimales) {
		String result = "";
		// %n
		String format = "%." + nroDecimales + "f";
		try {
			// String country = System.getProperty("user.country");
			// String lan = System.getProperty("user.language");
			// log.debug("country: " + country);
			// log.debug("lan: " + lan);

			// result = String.format(Locale.US, format, number);
			Locale locale = new Locale("es", "BO");
			result = String.format(locale, format, number);
			// result = String.format(format, number);

		} catch (Exception e) {
			log.error("Error al convertir double a string: ", e);
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public synchronized static HashMap sortByValues(HashMap map) {
		// ORDENA DE MENOR A MAYOR
		List list = new LinkedList(map.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, (Object o1, Object o2) -> ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue()));

		// Here I am copying the sorted list in HashMap
		// using LinkedHashMap to preserve the insertion order
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}
}
