 package es.jcyl.fqs.ocap.ln.comunidades;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.comunidades.OCAPComunidadesOAD;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPComunidadesLN
 {
   private JCYLUsuario jcylUsuario;
 
   public OCAPComunidadesLN(JCYLUsuario jcylUsuario)
   {
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList listarComunidades()
   {
     ArrayList resultado = null;
     try
     {
       OCAPComunidadesOAD variableOAD = OCAPComunidadesOAD.getInstance();
       resultado = variableOAD.listarComunidades();
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return resultado;
   }
 
   public String buscarComunidad(String cComuniId)
   {
     String nombre = null;
     try
     {
       OCAPComunidadesOAD variableOAD = OCAPComunidadesOAD.getInstance();
       nombre = variableOAD.buscarComunidad(cComuniId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return nombre;
   }
 }

