 package es.jcyl.fqs.ocap.util;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;

import java.awt.Font;
 import java.io.File;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.GregorianCalendar;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import net.sourceforge.barbecue.Barcode;
 import net.sourceforge.barbecue.BarcodeFactory;
 import org.apache.log4j.Logger;
 
 public class Utilidades
 {
   public Logger logger;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
   public Utilidades() {
     $init$();
   }
 
   public String checkABbdd(String valor)
     throws Exception
   {
     String resultado = new String();
     try
     {
       if ((valor == null) || (valor.equals("")))
         resultado = "N";
       else
         resultado = "Y";
     }
     catch (Exception e) {
       this.logger.error(e.getMessage());
       throw e;
     }
 
     return resultado;
   }
 
   public String bbddAcheck(String valor)
     throws Exception
   {
     String resultado = new String();
     try
     {
       if (valor.equals("Y"))
         resultado = "on";
       else
         resultado = "off";
     } catch (Exception e) {
       this.logger.error(e.getMessage());
       throw e;
     }
 
     return resultado;
   }
 
   public static Date fechaMas(Date fch, int dias)
   {
     Calendar cal = new GregorianCalendar();
     cal.setTimeInMillis(fch.getTime());
     cal.add(5, dias);
 
     return new Date(cal.getTimeInMillis());
   }
 
   public Date horaMas(Date fch, String minutos)
   {
     Calendar cal = new GregorianCalendar();
     cal.setTimeInMillis(fch.getTime());
     cal.add(12, Integer.parseInt(minutos));
 
     return new Date(cal.getTimeInMillis());
   }
 
   public static ArrayList rangoHoras(String minutos, int horaIni, int minIni, int horaFin)
   {
     Calendar calendar = Calendar.getInstance();
     Utilidades utilidades = new Utilidades();
 
     ArrayList listado = new ArrayList();
 
     String hora = "";
     calendar.clear();
     calendar.set(10, horaIni);
     calendar.set(12, minIni);
     Date date = calendar.getTime();
     String intervalo = minutos;
     hora = date.toString().substring(11, 16);
     int numListado = 0;
     listado.add(numListado, hora);
 
     if ((intervalo != null) && (!intervalo.equals(""))) {
       while (Integer.parseInt(hora.substring(0, 2)) < horaFin) {
         numListado++;
         date = utilidades.horaMas(date, intervalo);
         hora = date.toString().substring(11, 16);
         listado.add(numListado, hora);
       }
 
     }
 
     return listado;
   }
 
   public String[] ArrayListToArrayString(ArrayList lista)
   {
     int i = 0;
     String[] arrayTemporal = new String[lista.size()];
     for (i = 0; i < lista.size(); i++) {
       arrayTemporal[i] = lista.get(i).toString();
     }
 
     return arrayTemporal;
   }
 
   public ArrayList ArrayObjectToArrayList(Object[] array)
   {
     int i = 0;
     ArrayList listado = new ArrayList();
     for (i = 0; i < array.length; i++) {
       listado.add(array[i]);
     }
 
     return listado;
   }
 
   public int calcularEdad(Calendar fechaNac)
   {
     Calendar today = Calendar.getInstance();
 
     int diff_year = today.get(1) - fechaNac.get(1);
     int diff_month = today.get(2) - fechaNac.get(2);
     int diff_day = today.get(5) - fechaNac.get(5);
 
     if ((diff_month < 0) || ((diff_month == 0) && (diff_day < 0))) {
       diff_year -= 1;
     }
 
     return diff_year;
   }
 
   public void setOutputInput(InputStream pInput, int chunk, OutputStream pOutput)
     throws Exception
   {
     try
     {
       int bytesRead = 0;
       byte[] buffer = new byte[chunk];
 
       while ((bytesRead = pInput.read(buffer, 0, chunk)) != -1)
         pOutput.write(buffer, 0, bytesRead);
     }
     catch (IOException e) {
       this.logger.error(e.toString());
       throw new Exception(e.getMessage() + " Error al escribir en el objeto output el documento");
     } catch (Exception e) {
       this.logger.error(e.toString());
       throw new Exception(e.getMessage() + " Error al escribir en el objeto output el documento");
     }
     finally {
       try {
         pOutput.close();
         pInput.close();
       } catch (Exception localException1) {
         this.logger.error(localException1.toString());
         throw new Exception(localException1.getMessage() + " Error al cerrar los objeto input/output");
       }
     }
   }
 
   public static String getNumeroRomano(int entero)
   {
     int[] valor = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
     String[] simbolo = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
 
     String r = "";
     int p = 0;
 
     if ((entero >= 1) && (entero < 4000)) {
       int x = entero;
       while (x > 0)
       {
         while (x >= valor[p])
         {
           r = r + simbolo[p];
 
           x -= valor[p];
         }
 
         p++;
       }
     }
 
     return r;
   }
 
   public boolean esNumerico(String dato)
   {
     try
     {
       Integer.parseInt(dato);
       return true;
     }
     catch (NumberFormatException nfe) {
       return false;
     }
   }
 
   public boolean esNumericoFloat(String dato)
   {
     try
     {
       Float.parseFloat(dato);
       return true;
     }
     catch (NumberFormatException nfe) {
       return false;
     }
   }
 
   public boolean esEmail(String correo)
   {
     Pattern pat = null;
     Matcher mat = null;
 
     pat = Pattern.compile("[a-zA-Z0-9]+[.[a-zA-Z0-9_-]+]*@[a-z0-9][\\w\\.-]*[a-z0-9]\\.[a-z][a-z\\.]*[a-z]$");
     mat = pat.matcher(correo);
 
     return mat.find();
   }
 
   public static boolean esDNI(String nif)
   {
     boolean valido = false;
 
     int resultado = validarNI(formatearNI(nif));
     if (resultado != 0) {
       valido = true;
     }
 
     return valido;
   }
 
   public static String formatearNI(String ni)
   {
     String niFormateado = null;
     try {
       niFormateado = "";
 		char aux;
       for (int i = 0; i < ni.length(); i++) {
         aux = ni.toUpperCase().charAt(i);
 
         if (((aux >= '0') && (aux <= '9')) || ((aux >= 'A') && (aux <= 'Z'))) {
           niFormateado = niFormateado + aux;
         }
       }
 
       aux = niFormateado.charAt(0);
 
       String auxCeros = "";
       int longitud = 9;
       if ((aux >= '0') && (aux <= '9')) {
         for (int i = niFormateado.length(); i < longitud; i++)
           auxCeros = auxCeros + "0";
         niFormateado = auxCeros + niFormateado;
       } else if ((aux == 'X') || (aux == 'Y') || (aux == 'Z')) {
         for (int i = niFormateado.substring(1, niFormateado.length()).length(); i < longitud; i++)
           auxCeros = auxCeros + "0";
         niFormateado = aux + auxCeros + niFormateado.substring(1, niFormateado.length());
       }
     }
     catch (Exception e) {
       niFormateado = null;
     }
 
     return niFormateado;
   }
 
   public static String calcularLetraNIF(String numero)
   {
     String tablaLetras = "TRWAGMYFPDXBNJZSQVHLCKE";
     String resultado = "0";
     try
     {
       if ((numero == null) || (numero.length() > 8)) {
         throw new Exception();
       }
 
       int posicion = Integer.parseInt(numero) % 23;
       resultado = tablaLetras.substring(posicion, posicion + 1);
     }
     catch (Exception e) {
       resultado = "0";
     }
 
     return resultado;
   }
 
   public static int validarNIF(String documento)
   {
     int resultado = 0;
     String nif = "";
     int numero = 0;
     String letraNIF = "";
     try
     {
       if ((documento == null) || (documento.length() > 9)) {
         throw new Exception();
       }
       nif = documento.substring(0, documento.length() - 1);
       numero = Integer.parseInt(nif);
       letraNIF = documento.substring(documento.length() - 1).toUpperCase();
 
       if (!letraNIF.equals(calcularLetraNIF(nif))) {
         throw new Exception();
       }
       resultado = 1;
     }
     catch (Exception e) {
       resultado = 0;
     }
 
     return resultado;
   }
 
   public static int validarNIE(String documento)
   {
     int resultado = 0;
     String nie = "";
     String letraNIE = "";
     String peso = "";
     try
     {
       if ((documento == null) || (documento.length() > 10))
         throw new Exception();
       letraNIE = documento.toUpperCase().substring(0, 1);
 
       if (letraNIE.equals("X"))
         peso = "0";
       else if (letraNIE.equals("Y"))
         peso = "1";
       else if (letraNIE.equals("Z"))
         peso = "2";
       else {
         throw new Exception();
       }
 
       if ((documento.length() == 10) && (documento.substring(1, 2).equals("0")))
         nie = documento.substring(2);
       else {
         nie = documento.substring(1);
       }
 
       resultado = validarNIF(peso + nie);
     }
     catch (Exception e) {
       resultado = 0;
     }
 
     return resultado;
   }
 
   public static int validarNI(String documento)
   {
     int resultado = 0;
 
     if (documento == null)
     {
       return 0;
     }
     if (validarNIF(documento) == 1)
       resultado = 1;
     else if (validarNIE(documento) == 1)
       resultado = 2;
     else {
       resultado = 0;
     }
 
     return resultado;
   }
 
   public static void main(String[] args)
   {
     System.out.println("Entrada");
     String m = "";
     try {
       String a = "" + validarNI(formatearNI("X9440038X"));
       String b = "" + validarNI(formatearNI("X-9440032-X"));
       String c = "" + validarNI(formatearNI("X-9440038-Y"));
       String d = "" + validarNI(formatearNI("9305979-H"));
       String e = "" + validarNI(formatearNI("33514498-W"));
       String f = "" + validarNI(formatearNI("93w5979H"));
       String g = "" + validarNI(formatearNI("21000011E"));
       String h = "" + validarNI(formatearNI("2100r011E"));
       String i = "" + validarNI(formatearNI("21002575X"));
       String j = "" + validarNI(formatearNI(" "));
       String k = "" + validarNI(formatearNI(null));
       String l = "" + validarNI(null);
       m = "" + validarNI(formatearNI("X-9440038X"));
     }
     catch (Exception e)
     {
       System.out.println(e.getMessage());
     }
 
     System.out.println("Salida");
   }
 
   public static String obtenerNumeracion(String numeracion, int nivel, String textoElementos, int i, int numPreguntasNivel0, int numPreguntasNivel1, int numPreguntasNivel2)
   {
     String resultado = "";
     if ("Simple".equals(numeracion)) {
       resultado = resultado + textoElementos + (i + 1);
     } else if ("Romana".equals(numeracion)) {
       if (nivel == 0)
         resultado = resultado + textoElementos + getNumeroRomano(numPreguntasNivel0) + "." + getNumeroRomano(i + 1);
       if (nivel == 1)
         resultado = resultado + textoElementos + getNumeroRomano(numPreguntasNivel0) + "." + getNumeroRomano(numPreguntasNivel1) + "." + getNumeroRomano(i + 1);
       if (nivel == 2)
         resultado = resultado + textoElementos + getNumeroRomano(numPreguntasNivel0) + "." + getNumeroRomano(numPreguntasNivel1) + "." + getNumeroRomano(numPreguntasNivel2) + "." + getNumeroRomano(i + 1);
     } else if (!"No".equals(numeracion)) {
       if (nivel == 0)
         resultado = resultado + textoElementos + numPreguntasNivel0 + "." + (i + 1);
       if (nivel == 1)
         resultado = resultado + textoElementos + numPreguntasNivel0 + "." + numPreguntasNivel1 + "." + (i + 1);
       if (nivel == 2) {
         resultado = resultado + textoElementos + numPreguntasNivel0 + "." + numPreguntasNivel1 + "." + numPreguntasNivel2 + "." + (i + 1);
       }
     }
 
     return resultado;
   }
 
   public static String obtenerNumeracionTotal(int nivel, String textoElementos, int i, int j, int numPreguntasNivel0, int numPreguntasNivel1, int numPreguntasNivel2)
   {
     String resultado = "";
     if (j == 0) {
       if (nivel == 0)
         resultado = resultado + textoElementos + numPreguntasNivel0 + "." + (i + 1);
       if (nivel == 1)
         resultado = resultado + textoElementos + numPreguntasNivel0 + "." + numPreguntasNivel1 + "." + (i + 1);
       if (nivel == 2)
         resultado = resultado + textoElementos + numPreguntasNivel0 + "." + numPreguntasNivel1 + "." + numPreguntasNivel2 + "." + (i + 1);
     } else {
       if (nivel == 0)
         resultado = resultado + numPreguntasNivel0 + "." + (i + 1) + "." + j;
       if (nivel == 1)
         resultado = resultado + numPreguntasNivel0 + "." + numPreguntasNivel1 + "." + (i + 1) + "." + j;
       if (nivel == 2) {
         resultado = resultado + numPreguntasNivel0 + "." + numPreguntasNivel1 + "." + numPreguntasNivel2 + "." + (i + 1) + "." + j;
       }
     }
 
     return resultado;
   }
   public static synchronized void escribirMemoriaLog(Logger pLogger) {
     Runtime runtime = Runtime.getRuntime();
     pLogger.info("Mem. Total: " + (int)((runtime.totalMemory() + 1023L) / 1024L) + " Kb Mem. Usada: " + (int)((runtime.totalMemory() - runtime.freeMemory() + 1023L) / 1024L) + " Kb Mem. Libre: " + (int)((runtime.freeMemory() + 1023L) / 1024L));
   }
 
   public static Barcode obtenerCodigoBarras(String codigo)
   {
     Barcode barcode = null;
     try
     {
       String codigoDeBarras = codigo;
       barcode = BarcodeFactory.createCode128B(codigoDeBarras);
 
       barcode.setBarHeight(50);
       barcode.setBarWidth(3);
       barcode.setFont(new Font(null, 0, 0));
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return barcode;
   }
 
   public static void borrarDirectorio(File directorio)
     throws Exception
   {
     File[] ficheros = directorio.listFiles();
     try
     {
       for (int x = 0; x < ficheros.length; x++) {
         if (ficheros[x].isDirectory()) {
           borrarDirectorio(ficheros[x]);
         }
 
         ficheros[x].delete();
       }
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }

   }

	/**
	 * 
	 * @param cadena
	 * @return
	 */
	public static boolean isNullOrIsEmpty(String cadena) {

		return cadena == null || cadena.isEmpty();
	}
	
	/**
	 * 
	 * @param cadena
	 * @return
	 */
	public static boolean notNullAndNotEmpty(String cadena) {

		return cadena != null && !cadena.isEmpty();
	}
	
	public static String prepararInsercionClob(String cadena) {
		String retorno = "";
		if (null == cadena) {
			return "TO_CLOB(\"''\")";
		}else if (cadena != null && cadena.length() <= 4000) {
			return "TO_CLOB('"+cadena+"')";	
		}else if (cadena != null && cadena.length() > 4000) {
			String aux = "";
			while (cadena.length() > 4000) {
				aux = cadena.substring(0,4000);				
				retorno += "TO_CLOB('"+aux+"') || ";
				cadena = cadena.substring(4000);
				if (cadena.length() <= 4000) {
					//No habrá mas iteraciones
					retorno += "TO_CLOB('"+cadena+"') ";
				}
			}
		}
		return retorno;
		
	}
	
	public static String getStackTrace(final Throwable throwable) {
	     final StringWriter sw = new StringWriter();
	     final PrintWriter pw = new PrintWriter(sw, true);
	     throwable.printStackTrace(pw);
	     return sw.getBuffer().toString();
	}
	public static boolean notNullAndNotEmpty(ArrayList listado) {
		return listado != null && !listado.isEmpty();
	}
	
	public static boolean fechaActualEntreMeritosCurriculares(OCAPExpedientecarreraOT expCarreraOT, OCAPConvocatoriasOT convoOT) {
		try {
			
			if (Utilidades.notNullAndNotEmpty(convoOT.getFInicioMC()) && Utilidades.notNullAndNotEmpty(convoOT.getFFinMC())) {
				Date fechaInicioMC = DateUtil.convertStringToDate(convoOT.getFInicioMC());  
				Date fechaFinMC = DateUtil.convertStringToDate(convoOT.getFFinMC());
				//Miramos que fecha actual esté comprendida entre F_INICIO_MC - F_FIN_MC
				if(eliminarHoras(new Date()).compareTo(fechaInicioMC) >= 0 && eliminarHoras(new Date()).compareTo(fechaFinMC) <= 0) {
					return true;
				}
			}
			
			if (expCarreraOT.getFInicioMc() != null && expCarreraOT.getFFinMc() != null) {
				//Miramos que la F_INICIO_MC del expediente sea siempre posterior a la de la convocatoria, y luego 
				//miramos que fecha actual esté comprendida entre F_INICIO_MC - F_FIN_MC
				if(DateUtil.convertStringToDate(convoOT.getFInicioMC()).compareTo(expCarreraOT.getFInicioMc()) <= 0
						&& eliminarHoras(new Date()).compareTo(expCarreraOT.getFInicioMc()) >= 0 
						&& eliminarHoras(new Date()).compareTo(expCarreraOT.getFFinMc()) <= 0) {
					return true;
				}
			}
			
			return false;
		}catch (Exception e) {
			OCAPConfigApp.logger.error(e);
			return false;
		}
	}
	
	public static boolean fechaActualEntreMeritosAsistenciales(OCAPExpedientecarreraOT expCarreraOT, OCAPConvocatoriasOT convoOT) {
		try {
			
			if (Utilidades.notNullAndNotEmpty(convoOT.getFInicioCA()) && Utilidades.notNullAndNotEmpty(convoOT.getFFinCA())) {
				Date fechaInicioCA = DateUtil.convertStringToDate(convoOT.getFInicioCA());  
				Date fechaFinCA = DateUtil.convertStringToDate(convoOT.getFFinCA());
				//Miramos que fecha actual esté comprendida entre F_INICIO_CA - F_FIN_CA
				if(eliminarHoras(new Date()).compareTo(fechaInicioCA) >= 0 && eliminarHoras(new Date()).compareTo(fechaFinCA) <= 0) {
					return true;
				}
			}
			
			if (expCarreraOT.getFInicioCa() != null && expCarreraOT.getFFinCa() != null) {
				//Miramos que la F_INICIO_CA del expediente sea siempre posterior a la de la convocatoria, y luego 
				//miramos que fecha actual esté comprendida entre F_INICIO_CA - F_FIN_CA
				if(DateUtil.convertStringToDate(convoOT.getFInicioCA()).compareTo(expCarreraOT.getFInicioCa()) <= 0 
						&& eliminarHoras(new Date()).compareTo(expCarreraOT.getFInicioCa()) >= 0 
						&& eliminarHoras(new Date()).compareTo(expCarreraOT.getFFinCa()) <= 0) {
					return true;
				}
			}
			
			return false;
		}catch (Exception e) {
			OCAPConfigApp.logger.error(e);
			return false;
		}
	}
	
	
	public static boolean fechaActualEntreMeritosExpediente(OCAPExpedientecarreraOT expCarreraOT, OCAPConvocatoriasOT convoOT) {
		try {
			
			if (expCarreraOT.getFInicioMc() != null && expCarreraOT.getFFinMc() != null) {
				//Miramos que la F_INICIO_MC del expediente sea siempre posterior a la de la convocatoria, y luego 
				//miramos que fecha actual esté comprendida entre F_INICIO_MC - F_FIN_MC
				if(DateUtil.convertStringToDate(convoOT.getFInicioMC()).compareTo(expCarreraOT.getFInicioMc()) <= 0
						&& eliminarHoras(new Date()).compareTo(expCarreraOT.getFInicioMc()) >= 0 
						&& eliminarHoras(new Date()).compareTo(expCarreraOT.getFFinMc()) <= 0) {
					return true;
				}
			}
			
			if (expCarreraOT.getFInicioCa() != null && expCarreraOT.getFFinCa() != null) {
				//Miramos que la F_INICIO_CA del expediente sea siempre posterior a la de la convocatoria, y luego 
				//miramos que fecha actual esté comprendida entre F_INICIO_CA - F_FIN_CA
				if(DateUtil.convertStringToDate(convoOT.getFInicioCA()).compareTo(expCarreraOT.getFInicioCa()) <= 0 
						&& eliminarHoras(new Date()).compareTo(expCarreraOT.getFInicioCa()) >= 0 
						&& eliminarHoras(new Date()).compareTo(expCarreraOT.getFFinCa()) <= 0) {
					return true;
				}
			}
			
			return false;
			
		}catch (Exception e) {
			OCAPConfigApp.logger.error(e);
			return false;
		}
	}

	public static boolean fechaActualEntreMeritosConvocatoria(OCAPConvocatoriasOT convoOT) {
		try {
			if (Utilidades.notNullAndNotEmpty(convoOT.getFInicioMC()) && Utilidades.notNullAndNotEmpty(convoOT.getFFinMC())) {
				Date fechaInicioMC = DateUtil.convertStringToDate(convoOT.getFInicioMC());  
				Date fechaFinMC = DateUtil.convertStringToDate(convoOT.getFFinMC());
				//Miramos que fecha actual esté comprendida entre F_INICIO_MC - F_FIN_MC
				if(eliminarHoras(new Date()).compareTo(fechaInicioMC) >= 0 && eliminarHoras(new Date()).compareTo(fechaFinMC) <= 0) {
					return true;
				}
			}
			
			if (Utilidades.notNullAndNotEmpty(convoOT.getFInicioCA()) && Utilidades.notNullAndNotEmpty(convoOT.getFFinCA())) {
				Date fechaInicioCA = DateUtil.convertStringToDate(convoOT.getFInicioCA());  
				Date fechaFinCA = DateUtil.convertStringToDate(convoOT.getFFinCA());
				//Miramos que fecha actual esté comprendida entre F_INICIO_CA - F_FIN_CA
				if(eliminarHoras(new Date()).compareTo(fechaInicioCA) >= 0 && eliminarHoras(new Date()).compareTo(fechaFinCA) <= 0) {
					return true;
				}
			}
			
			return false;
			
		}catch (Exception e) {
			OCAPConfigApp.logger.error(e);
			return false;
		}
	}

	public static Date eliminarHoras(Date fecha) {
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(fecha);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
		
	}
}
