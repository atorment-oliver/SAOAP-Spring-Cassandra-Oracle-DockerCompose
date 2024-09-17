package com.detallenavegacion.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UtilDate implements Serializable {
	public static Logger log = LogManager.getLogger(UtilDate.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Date stringToDate(String fecha, String format) {
		Date date = null;
		try {
			DateFormat formatter;
			// yyyy-MM-dd HH:mm
			formatter = new SimpleDateFormat(format);
			date = (Date) formatter.parse(fecha);
		} catch (Exception e) {
			e.getMessage();
		}
		return date;
	}

	public static String dateToString(Date fecha, String format) {
		String date = "";
		try {
			if (fecha != null) {
				DateFormat formatter = new SimpleDateFormat(format);
				date = formatter.format(fecha);
			} else {
				date = "NULL";
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return date;
	}
	public static Date convertToDate(XMLGregorianCalendar xmlGregorianCalendar) throws ParseException{
        if (xmlGregorianCalendar == null) {
            return null;
        }
        GregorianCalendar gregorianCalendar = xmlGregorianCalendar.toGregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
		int año = gregorianCalendar.get(Calendar.YEAR);
        int mes = gregorianCalendar.get(Calendar.MONTH) + 1; // Los meses comienzan en 0
        int dia = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
        int hora = gregorianCalendar.get(Calendar.HOUR_OF_DAY);
        int minuto = gregorianCalendar.get(Calendar.MINUTE);
        int segundo = gregorianCalendar.get(Calendar.SECOND); 
		String sDateFormat = Integer.toString(año) + "-" + Integer.toString(mes) + "-" + Integer.toString(dia) 
		+ " " + Integer.toString(hora) + ":" + Integer.toString(minuto) + ":" + Integer.toString(segundo)  ;
		Date fecha = sdf.parse(sDateFormat);
        return fecha;
    }
	public static Date formatDate(Date fecha) {
        SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaFormateada = formatoSalida.format(fecha);
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fechaFormateadaComoDate = null;
        try {
            fechaFormateadaComoDate = formatoEntrada.parse(fechaFormateada);
        } catch (ParseException e) {
            log.error(e);
        }

        return fechaFormateadaComoDate;
    }
	public static String DateToStringFormatted(Date fecha) {
        SimpleDateFormat formatoAño = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatoMes = new SimpleDateFormat("MM");
        SimpleDateFormat formatoDia = new SimpleDateFormat("dd");

        SimpleDateFormat formatoHora = new SimpleDateFormat("HH");
        SimpleDateFormat formatoMinuto = new SimpleDateFormat("mm");
        SimpleDateFormat formatoSegundo = new SimpleDateFormat("ss");

        String año = formatoAño.format(fecha);
        String mes = formatoMes.format(fecha);
        String dia = formatoDia.format(fecha);
        String hora = formatoHora.format(fecha);
        String minuto = formatoMinuto.format(fecha);
        String segundo = formatoSegundo.format(fecha);
		return año + "-" + mes + "-" + dia + " "+ hora + ":" + minuto + ":" + segundo;
    }
	public static String OnlyDateToStringFormatted(Date fecha) {
        SimpleDateFormat formatoAño = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatoMes = new SimpleDateFormat("MM");
        SimpleDateFormat formatoDia = new SimpleDateFormat("dd");

        String año = formatoAño.format(fecha);
        String mes = formatoMes.format(fecha);
        String dia = formatoDia.format(fecha);
		return año + "-" + mes + "-" + dia;
    }
	public static LocalDate convertToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
