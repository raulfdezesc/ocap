 package es.jcyl.fqs.ocap.ln.respuestas;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.respuestas.OCAPRespuestasOAD;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPRespuestasLN
 {
   Logger logger;
   private OCAPRespuestasOAD respuestasOAD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
 
   public void OCAPRespuestasLN()
   {
     this.respuestasOAD = OCAPRespuestasOAD.getInstance();
   }
   public OCAPRespuestasLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList buscarOCAPRespuestasPorCuestionarioPregunta(long cCuestionId, long cPreguntaId)
   {
     ArrayList listado = null;
     try
     {
       this.respuestasOAD = OCAPRespuestasOAD.getInstance();
       listado = this.respuestasOAD.buscarOCAPRespuestasPorCuestionarioPregunta(cCuestionId, cPreguntaId);
     }
     catch (Exception e) {
       listado = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return listado;
   }
 }

