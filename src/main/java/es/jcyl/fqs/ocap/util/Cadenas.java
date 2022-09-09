 package es.jcyl.fqs.ocap.util;
 
 import java.util.ArrayList;
 import java.util.StringTokenizer;
 
 public class Cadenas
 {
   public static String formatearCampoParaScript(String origen)
   {
     String aux = new String();
     if ((origen == null) || (origen.equals("")))
     {
       return "";
     }
 
     aux = origen;
 
     aux = reemplazar("\"", "&quot;", aux);
     aux = reemplazar("'", "\\'", aux);
     aux = reemplazar("\n", " ", aux);
 
     return aux;
   }
 
   public static String escaparComillasSimplesParaSQL(String cadena)
   {
     String cadenaDevuelta = "";
     for (int i = 0; i < cadena.length(); i++) {
       if ((cadena.charAt(i) == '\'') && ((i == cadena.length()) || (cadena.charAt(i + 1) != '\'')))
         cadenaDevuelta = cadenaDevuelta + "'" + cadena.charAt(i);
       else {
         cadenaDevuelta = cadenaDevuelta + cadena.charAt(i);
       }
     }
 
     return cadenaDevuelta;
   }
 
   public static String formatearCampoActualizacion(String origen)
   {
     if ((origen == null) || (origen.equals("")))
     {
       return "";
     }
 
     return reemplazar("'", "''", origen).toUpperCase();
   }
 
   public static String formatearTexto(String origen)
   {
     if ((origen == null) || (origen.equals("")))
     {
       return "&nbsp;";
     }
 
     return reemplazar("\"", "&quot;", reemplazar("'", "\\'", reemplazar("\n", "<br/>", reemplazar("\r", "", origen))));
   }
 
   public static String tratarNulo(String origen)
   {
     if (origen == null)
     {
       return "";
     }
 
     return origen;
   }
 
   public static String reemplazar(char reemplazar, String nueva, String origen)
   {
     int indice = origen.indexOf(reemplazar);
 
     return origen.substring(0, indice) + nueva + origen.substring(indice + 1);
   }
 
   public static String reemplazar(String reemplazar, String nueva, String origen)
   {
     StringTokenizer st = new StringTokenizer(origen, reemplazar, true);
     StringBuffer aux = new StringBuffer(origen.length());
     String token = "";
 
     while (st.hasMoreTokens()) {
       token = st.nextToken().toString();
       if (token.equals(reemplazar))
         aux = aux.append(nueva);
       else {
         aux = aux.append(token);
       }
 
     }
 
     return aux.toString();
   }
 
   public static String reemplazar(String cadena, String caracter, String[] valores)
   {
     StringBuffer result = new StringBuffer();
     int oldpos = 0;
     int contador = 0;
     while (true) {
       int pos = cadena.indexOf(caracter, oldpos);
       if (pos < 0)
         break;
       result.append(cadena.substring(oldpos, pos));
       if (contador < valores.length)
         result.append(valores[(contador++)]);
       else break;
       pos += caracter.length();
       oldpos = pos;
     }
     if (oldpos == 0)
     {
       return cadena;
     }
     result.append(cadena.substring(oldpos));
 
     return new String(result);
   }
 
   public static String eliminaTokensDuplicados(String separador, String cadena)
   {
     String cadenaTokenOut = "";
     String cadenaFinal = "";
 
     cadena = cadena.trim().toLowerCase();
     StringTokenizer tokenIn = new StringTokenizer(cadena, separador);
 
     while (tokenIn.hasMoreTokens()) {
       String cadenaTokenIn = tokenIn.nextToken().trim();
       StringTokenizer tokenOut = new StringTokenizer(cadenaFinal, separador);
       boolean existe = false;
 
       while ((tokenOut.hasMoreTokens()) && (!existe)) {
         cadenaTokenOut = tokenOut.nextToken().trim();
 
         if (cadenaTokenIn.equals(cadenaTokenOut)) {
           existe = true;
         }
       }
 
       if (!existe) cadenaFinal = cadenaFinal + separador + cadenaTokenIn;
     }
     cadenaFinal = cadenaFinal.substring(1);
 
     return cadenaFinal;
   }
 
   public static ArrayList obtenerArrayListTokens(String cadena, String separador)
   {
     ArrayList campos = new ArrayList();
 
     StringTokenizer token = new StringTokenizer(cadena, separador);
 
     while (token.hasMoreTokens()) {
       campos.add(token.nextToken());
     }
 
     return campos;
   }
 
   public static String cortarCadena(String cadena, int numeroCaracteres)
   {
     String subCadena = new String("");
     int caracteres = 0;
 
     if (cadena.length() > numeroCaracteres)
     {
       subCadena = cadena.substring(0, numeroCaracteres);
 
       caracteres = subCadena.lastIndexOf(' ');
       if (caracteres != -1)
         subCadena = subCadena.substring(0, subCadena.lastIndexOf(' '));
       subCadena = subCadena + "...";
     } else {
       subCadena = cadena;
     }
 
     return subCadena;
   }
 
   public static String capitalizar(String origen)
   {
     if (origen == null)
     {
       return "";
     }
     String aux = "";
 
     aux = origen.substring(0, 1).toUpperCase();
     aux = aux + origen.substring(1).toLowerCase();
 
     return aux;
   }
 
   public static String formatearCaracteres (String origen){
	   if (origen == null || origen.equals("")) {
	       return "";
	    } else {
	       String destino="";
	       char[] caracteres = (origen.toUpperCase()).toCharArray();
	       for (int i = 0; i<caracteres.length; i++) {
	          char caracter = caracteres[i];
	          if (caracter == '\u00C1') caracteres[i]='A';
	          else if (caracter == '\u00C9') caracteres[i]='E';
	          else if (caracter == '\u00CD') caracteres[i]='I';
	          else if (caracter == '\u00D3') caracteres[i]='O';
	          else if (caracter == '\u00DA') caracteres[i]='U';
	          else if (caracter == '\u00DC') caracteres[i]='U';
	          else if (caracter == '\u00C7') caracteres[i]='C';       
	       }
	       destino = String.valueOf(caracteres);
	       return destino;      
	    }
	 }  // Fin de formatearTexto
 
   public static String obtenerCadenaFromArrayList(ArrayList origen, String separador)
   {
     String cadena = "";
     for (int i = 0; i < origen.size(); i++) {
       cadena = cadena + (String)origen.get(i) + separador;
     }
 
     return cadena;
   }
 
   public static boolean EsVacia(String cadena)
   {
     return (cadena == null) || (cadena.trim().equals("")) || (cadena.trim().equals(Constantes.VALOR_CADENA_NULA));
   }
 
   public static int contarOcurrencias(String cadena, String caracter)
   {
     int indice = 0;
     String temp = new String(cadena);
     int contador = 0;
     while (indice <= cadena.length()) {
       if (indice == 0)
         indice = temp.indexOf(caracter, indice);
       else
         indice = temp.indexOf(caracter, indice + 1);
       if (indice < 0) break;
       contador++;
     }
 
     return contador;
   }
 
   public static String convierteMas(String cadena) {
     cadena = cadena.replaceAll(Constantes.MAS, "\\+");
 
     return cadena;
   }
 
   public static String formatearParaCSV(String cadena) {
     if (cadena == null) {
       cadena = "--";
     } else {
       cadena = cadena.replaceAll(";", ",");
       cadena = cadena.replaceAll("\n", " ");
     }
 
     return cadena;
   }
 }

