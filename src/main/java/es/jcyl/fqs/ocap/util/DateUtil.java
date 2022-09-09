 package es.jcyl.fqs.ocap.util;
 
 import java.text.DateFormatSymbols;
 import java.text.DecimalFormat;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.GregorianCalendar;
 import java.util.Locale;
 import java.util.MissingResourceException;
 import java.util.ResourceBundle;
 import org.apache.commons.lang.StringUtils;
 
 public class DateUtil
 {
   private static String defaultDatePattern = null;
   private static String timePattern = "HH:mm";
 
   public static synchronized String getDatePattern()
   {
     Locale locale = Locale.getDefault();
     try {
       defaultDatePattern = ResourceBundle.getBundle("ApplicationResources", locale).getString("date.format");
     } catch (MissingResourceException mse) {
       defaultDatePattern = "dd/MM/yyyy";
     }
 
     return defaultDatePattern;
   }
 
   public static final String getDate(Date aDate)
   {
     SimpleDateFormat df = null;
     String returnValue = "";
 
     if (aDate != null) {
       df = new SimpleDateFormat(getDatePattern());
       returnValue = df.format(aDate);
     }
 
     return returnValue;
   }
 
   public static final Date convertStringToDate(String aMask, String strDate)
     throws ParseException
   {
     SimpleDateFormat df = null;
     Date date = null;
     df = new SimpleDateFormat(aMask);
     try
     {
       if (!StringUtils.isEmpty(strDate))
         date = df.parse(strDate);
     }
     catch (ParseException pe)
     {
       throw new ParseException(pe.getMessage(), pe.getErrorOffset());
     }
 
     return date;
   }
 
   public static String getTimeNow(Date theTime)
   {
     return getDateTime(timePattern, theTime);
   }
 
   public static Calendar getToday()
     throws ParseException
   {
     Date today = new Date();
     SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
 
     String todayAsString = df.format(today);
     Calendar cal = new GregorianCalendar();
     cal.setTime(convertStringToDate(todayAsString));
 
     return cal;
   }
 
   public static Date getTodayDate()
     throws ParseException
   {
     Date today = new Date();
     SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
 
     String todayAsString = df.format(today);
 
     return convertStringToDate(todayAsString);
   }
 
   public static final String getDateTime(String aMask, Date aDate)
   {
     SimpleDateFormat df = null;
     String returnValue = "";
 
     df = new SimpleDateFormat(aMask);
     returnValue = df.format(aDate);
 
     return returnValue;
   }
 
   public static final String convertDateToString(Date aDate)
   {
     return getDateTime(getDatePattern(), aDate);
   }
 
   public static Date convertStringToDate(String strDate)
     throws ParseException
   {
     Date aDate = null;
     try
     {
       aDate = convertStringToDate(getDatePattern(), strDate);
     } catch (ParseException pe) {
       pe.printStackTrace();
       throw new ParseException(pe.getMessage(), pe.getErrorOffset());
     }
 
     return aDate;
   }
 
   public static int getNumDiasHabiles(Date dInicio, Date dFin)
   {
     GregorianCalendar fechainicio = new GregorianCalendar();
     fechainicio.setTime(dInicio);
     GregorianCalendar fechafin = new GregorianCalendar();
     fechafin.setTime(dFin);
     int dias = 0;
 
     while (fechainicio.before(fechafin)) {
       if ((fechainicio.get(7) != 7) && (fechainicio.get(7) != 1)) {
         dias++;
       }
       fechainicio.add(5, 1);
     }
     if ((fechainicio.get(7) != 7) && (fechainicio.get(7) != 1)) {
       dias++;
     }
 
     return dias;
   }
 
   public static int getNumDias(Date dInicio, Date dFin)
   {
     int dias = 0;
 
     float nMiliSeg = (float)(dFin.getTime() - dInicio.getTime());
     float dDias = nMiliSeg / 86400000.0F;
     dias = Math.round(dDias) + 1;
 
     return dias;
   }
 
   public static int getNumAnnios(Date dInicio, Date dFin)
   {
     GregorianCalendar fechainicio = new GregorianCalendar();
     fechainicio.setTime(dInicio);
     GregorianCalendar fechafin = new GregorianCalendar();
     fechafin.setTime(dFin);
     int annios = 0;
     fechainicio.add(1, 1);
     while (!fechainicio.after(fechafin)) {
       annios++;
       fechainicio.add(1, 1);
     }
 	if (fechasYear(fechainicio, fechafin)) {
		annios++;
	}
     return annios;
   }
	/**
	 * Método encargado de comprobar si dos fechas suman un año completo
	 * 
	 * @param fechainicio
	 *            fecha de inicio
	 * @param fechafin
	 *            fecha de fin
	 * @return TRUE si las fechas tienen un año; FALSE en caso contrario
	 */
	private static boolean fechasYear(GregorianCalendar fechainicio, GregorianCalendar fechafin) {

		return fechainicio.getActualMinimum(Calendar.DAY_OF_MONTH) == fechainicio.get(Calendar.DAY_OF_MONTH)
				&& fechainicio.getActualMinimum(Calendar.MONTH) == fechainicio.get(Calendar.MONTH)
				&& fechafin.getActualMaximum(Calendar.DAY_OF_MONTH) == fechafin.get(Calendar.DAY_OF_MONTH)
				&& fechafin.getActualMaximum(Calendar.MONTH) == fechafin.get(Calendar.MONTH);

	}
   public static int getNumMeses(Date dInicio, Date dFin)
   {
     GregorianCalendar fechainicio = new GregorianCalendar();
     fechainicio.setTime(dInicio);
     GregorianCalendar fechafin = new GregorianCalendar();
     fechafin.setTime(dFin);
     int meses = 0;
     fechainicio.add(2, 1);
     while (!fechainicio.after(fechafin)) {
       meses++;
       fechainicio.add(2, 1);
     }
 
     return meses;
   }
 
   public static float getNumHoras(Date dInicio, Date dFin)
   {
     float horas = 0.0F;
     float nMiliSeg = (float)(dFin.getTime() - dInicio.getTime());
     float dHoras = nMiliSeg / 3600000.0F;
     horas = (float)(Math.rint(dHoras * 100.0F) / 100.0D);
 
     return horas;
   }
 
   public static String getHorasMinutos(long nSegundos)
   {
     DecimalFormat df = new DecimalFormat("00");
     long horas = nSegundos / 3600L;
     int minutos = (int)(nSegundos / 60 % 60);
     int segundos = (int)(nSegundos % 60);
 
     return df.format(horas) + ":" + df.format(minutos) + ":" + df.format(segundos);
   }
 
   public static Date addSemanas(Date dFecha, int nSemanas)
   {
     GregorianCalendar fecha = new GregorianCalendar();
     fecha.setTime(dFecha);
     fecha.add(3, nSemanas);
 
     return fecha.getTime();
   }
 
   public static Date addDias(Date dFecha, int nDias)
   {
     GregorianCalendar fecha = new GregorianCalendar();
     fecha.setTime(dFecha);
     fecha.add(6, nDias);
 
     return fecha.getTime();
   }
 
   public static Date addAnios(Date dFecha, int nAnios)
   {
     GregorianCalendar fecha = new GregorianCalendar();
     fecha.setTime(dFecha);
     fecha.add(1, nAnios);
 
     return fecha.getTime();
   }
 
   public static final String convertDateToStringLarga(String aMask, Date dFecha) throws ParseException
   {
     SimpleDateFormat df = null;
     String cadenaFecha = null;
     df = new SimpleDateFormat(aMask, new Locale("es", "ES"));
     try
     {
       if (dFecha != null)
         cadenaFecha = df.format(dFecha);
     }
     catch (Exception pe)
     {
       return null;
     }
 
     return cadenaFecha;
   }
 
   public static final String convertDateToStringLargaMesMayus(String aMask, Date dFecha)
     throws ParseException
   {
     String cadenaFecha = null;
     DateFormatSymbols symbols = new DateFormatSymbols(new Locale("es", "ES"));
 
     String[] mesesIniMayus = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre", "" };
     symbols.setMonths(mesesIniMayus);
 
     String[] mesesAbreIniMayus = { "Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic", "" };
     symbols.setShortMonths(mesesAbreIniMayus);
 
     SimpleDateFormat formatter = new SimpleDateFormat(aMask, symbols);
     try
     {
       if (dFecha != null)
         cadenaFecha = formatter.format(dFecha);
     }
     catch (Exception pe)
     {
       return null;
     }
 
     return cadenaFecha;
   }
 }

