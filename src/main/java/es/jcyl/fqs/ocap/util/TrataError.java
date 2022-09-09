 package es.jcyl.fqs.ocap.util;
 
 import java.util.ArrayList;
 
 public class TrataError
 {
   public static String tratarError(Exception ex, String errorpordefecto)
   {
     ArrayList lista = new ArrayList();
     String mensajeError = ex.getMessage();
 
     if ((mensajeError != null) && (mensajeError.length() >= 4)) {
       String str = mensajeError.substring(0, 4);
       if (str.equals("ORA-"))
       {
         return ex.getMessage();
       }
 
       return errorpordefecto;
     }
 
     return errorpordefecto;
   }
 }

