 package es.jcyl.fqs.ocap.ln.calculoCredCuestionarios;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.calculoCredCuestionarios.OCAPCalculoCredCuestionariosOAD;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPCalculoCredCuestionariosLN
 {
   private JCYLUsuario jcylUsuario;
 
   public OCAPCalculoCredCuestionariosLN(JCYLUsuario jcylUsuario)
   {
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList buscarOCAPCalculoCredCuestionario(long cCuestionarioId)
   {
     ArrayList listado = null;
     try
     {
       OCAPCalculoCredCuestionariosOAD calculoCredCuestionariosOAD = OCAPCalculoCredCuestionariosOAD.getInstance();
       listado = calculoCredCuestionariosOAD.buscarOCAPCalculoCredCuestionario(cCuestionarioId);
     }
     catch (Exception e) {
       listado = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return listado;
   }
 }

