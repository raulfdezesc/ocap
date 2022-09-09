 package es.jcyl.framework.utiles;
 
 import es.jcyl.cf.seguridad.util.Funcion;
 import java.util.ArrayList;
 import java.util.Iterator;
 import org.apache.commons.validator.GenericValidator;
 
 public class JCYLSeguTigra
 {
   public static String serializarATigraMenu2_0(ArrayList FuncionesMenu, String target)
   {
     StringBuffer salida = new StringBuffer();
     salida.append("[");
     Funcion fn = new Funcion();
     Iterator it = FuncionesMenu.iterator();
     int profundidad = 0;
     while (it.hasNext())
     {
       fn = (Funcion)it.next();
 
       while (fn.getNProfundidad() <= profundidad)
       {
         salida.append("],");
         profundidad--;
       }
       while (fn.getNProfundidad() > profundidad)
       {
         profundidad++;
       }
       profundidad = fn.getNProfundidad();
 
       salida.append("['");
       salida.append(fn.getDDescripcion());
       salida.append("',");
 
       if ((GenericValidator.isBlankOrNull(fn.getDProceso())) || (fn.getDProceso().equals("#")))
       {
         salida.append("null, null,");
       } else {
         salida.append("'");
 
         if (fn.getDProceso().toLowerCase().indexOf("javascript:") == -1) {
           salida.append(fn.getDProceso());
           salida.append("',");
 
           salida.append("{'tw' : '" + target + "','js':'try{despejarGUI(false);}catch(e){;};'");
         } else {
           salida.append("null',{'tw' : 'null','js' : '");
           salida.append(fn.getDProceso());
           salida.append(";'");
         }
 
         salida.append("},");
       }
 
     }
 
     while (1 <= profundidad) {
       salida.append("],");
       profundidad--;
     }
 
     salida.append("]");
 
     return salida.toString();
   }
 
   public static int getNMenusPrimerNivel(ArrayList FuncionesMenu)
   {
     int nEltos = 0;
     Funcion fn = new Funcion();
     Iterator it = FuncionesMenu.iterator();
     while (it.hasNext()) {
       fn = (Funcion)it.next();
       if (fn.getNProfundidad() <= 1) {
         nEltos++;
       }
     }
     return nEltos;
   }
 }

