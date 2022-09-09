 package es.jcyl.framework.utiles;
 
 import es.jcyl.cf.seguridad.util.Funcion;
 import java.util.ArrayList;
 import java.util.Iterator;
 import org.apache.commons.validator.GenericValidator;
 
 public class JCYLSeguHTML
 {
   static String ESTILO_MENU = "menuNivel_";
   static String INI_LISTA = "<ul>";
   static String FIN_LISTA = "</ul>";
   static String INI_ENTRADA_P1 = "<li class=\"";
   static String INI_ENTRADA_P2 = "\"> ";
   static String FIN_ENTRADA = "</li>";
 
   static String ENLACE_VACIO = "#";
   static String ETIQUETA_INICIO_ENLACE = "<a href='";
   static String ETIQUETA_INICIO_ENLACE2 = "' >";
   static String ETIQUETA_FIN_ENLACE = "</a>";
   static int PRIMER_NIVEL = 1;
 
   public static String getMenuHTML(ArrayList FuncionesMenu, String itemPadreDesplegado)
   {
     return getMenuHTML(FuncionesMenu, itemPadreDesplegado, false);
   }
 
   public static String getMenuHTML(ArrayList FuncionesMenu)
   {
     return getMenuHTML(FuncionesMenu, "VACIO", true);
   }
 
   private static String getMenuHTML(ArrayList FuncionesMenu, String itemDesplegado, boolean todoDesplegado)
   {
     StringBuffer salida = new StringBuffer();
     salida.append(INI_LISTA);
     Funcion fn = new Funcion();
     Iterator it = FuncionesMenu.iterator();
     int miprofundidad = PRIMER_NIVEL;
     while (it.hasNext())
     {
       fn = (Funcion)it.next();
       if (fn.getNProfundidad() <= 2)
       {
         if ((fn.getCFuncionPadre().equals(itemDesplegado)) || (fn.getNProfundidad() == PRIMER_NIVEL) || (todoDesplegado))
         {
           while (fn.getNProfundidad() > miprofundidad)
           {
             miprofundidad++;
           }
           while (fn.getNProfundidad() < miprofundidad)
           {
             miprofundidad--;
           }
           miprofundidad = fn.getNProfundidad();
           String estiloAplicable = ESTILO_MENU + miprofundidad;
           salida.append(INI_ENTRADA_P1).append(estiloAplicable).append(INI_ENTRADA_P2);
           if ((GenericValidator.isBlankOrNull(fn.getDProceso())) || (fn.getDProceso().equals(ENLACE_VACIO)))
           {
             salida.append(fn.getDDescripcion()).append(FIN_ENTRADA);
           }
           else {
             salida.append(ETIQUETA_INICIO_ENLACE).append(fn.getDProceso()).append(ETIQUETA_INICIO_ENLACE2);
             salida.append(fn.getDDescripcion()).append(ETIQUETA_FIN_ENLACE).append(FIN_ENTRADA);
           }
         }
       }
     }
     while (1 < miprofundidad) {
       salida.append(FIN_LISTA);
       miprofundidad--;
     }
 
     salida.append(FIN_LISTA);
 
     return salida.toString();
   }
 }

