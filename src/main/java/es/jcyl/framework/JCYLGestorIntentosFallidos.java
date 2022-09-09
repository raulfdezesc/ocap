 package es.jcyl.framework;
 
 import es.jcyl.framework.ot.JCYLIntentosFallidosOT;
 import java.util.Date;
 import java.util.Hashtable;
 
 public class JCYLGestorIntentosFallidos
 {
   private static int MINUTOS_PENALIZACION = 15;
   private static Hashtable intentosFallidos = new Hashtable();
 
   public static int nuevoIntentoFallido(String usuarioAplicacion) {
     int intentos = 1;
     if (intentosFallidos == null) intentosFallidos = new Hashtable();
     if (intentosFallidos.get(usuarioAplicacion) != null)
     {
       JCYLIntentosFallidosOT f = (JCYLIntentosFallidosOT)intentosFallidos.get(usuarioAplicacion);
       intentos = f.getNIntento();
       intentosFallidos.put(usuarioAplicacion, new JCYLIntentosFallidosOT(intentos++));
     }
     intentosFallidos.put(usuarioAplicacion, new JCYLIntentosFallidosOT(intentos));
 
     return intentos;
   }
 
   public static boolean haSuperadoMaxIntentos(String usuarioAplicacion, int intentos, String propMax)
   {
     try
     {
       int maxIntentos = Integer.parseInt(JCYLConfiguracion.getValor(propMax));
       if (intentos >= maxIntentos)
       {
         return true;
       }
       return false;
     }
     catch (Exception e)
     {
       return false;
     }
   }
 
   public static boolean cuentaBloqueada(String usuarioAplicacion) {
     if (intentosFallidos == null) {
       intentosFallidos = new Hashtable();
       try
       {
         MINUTOS_PENALIZACION = Integer.parseInt(JCYLConfiguracion.getValor("MINUTOS_PENALIZACION"));
       }
       catch (Exception e)
       {
       }
 
       return false;
     }
     JCYLIntentosFallidosOT intento = (JCYLIntentosFallidosOT)intentosFallidos.get(usuarioAplicacion);
     if (intento == null)
     {
       return false;
     }Date fechaBloqueo = intento.getFechaFinBloqueo();
     if (fechaBloqueo != null)
     {
       Date ahora = new Date();
       if (fechaBloqueo.before(ahora))
       {
         intento.setFechaFinBloqueo(null);
 
         return false;
       }
 
       return true;
     }
 
     return false;
   }
 
   public static void bloquearCuenta(String usuarioAplicacion)
   {
     JCYLIntentosFallidosOT intento = (JCYLIntentosFallidosOT)intentosFallidos.get(usuarioAplicacion);
     Date fecha = new Date();
     long longfecha = fecha.getTime() + 60000 * MINUTOS_PENALIZACION;
     fecha.setTime(longfecha);
     intento.setFechaFinBloqueo(fecha);
     intentosFallidos.put(usuarioAplicacion, intento);
   }
 
   public static void iniciarCuenta(String usuarioAplicacion)
   {
     intentosFallidos.remove(usuarioAplicacion);
     try
     {
       MINUTOS_PENALIZACION = Integer.parseInt(JCYLConfiguracion.getValor("MINUTOS_PENALIZACION"));
     }
     catch (Exception e)
     {
     }
   }
 
   public static int getNumIntentos(String usuarioAplicacion) {
     JCYLIntentosFallidosOT f = (JCYLIntentosFallidosOT)intentosFallidos.get(usuarioAplicacion);
 
     return f.getNIntento();
   }
 
   public static String toString(String usuarioAplicacion) {
     JCYLIntentosFallidosOT f = (JCYLIntentosFallidosOT)intentosFallidos.get(usuarioAplicacion);
 
     return f.toString();
   }
 }

