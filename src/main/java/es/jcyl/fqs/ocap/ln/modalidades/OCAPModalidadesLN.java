 package es.jcyl.fqs.ocap.ln.modalidades;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.modalidades.OCAPModalidadesOAD;
 import es.jcyl.fqs.ocap.ot.modalidades.OCAPModalidadesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPModalidadesLN
 {
   Logger logger;
   private OCAPModalidadesOAD modalidadesOAD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
 
   public void OCAPModalidadesLN()
   {
     this.modalidadesOAD = OCAPModalidadesOAD.getInstance();
   }
   public OCAPModalidadesLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList listarModalidades()
     throws Exception
   {
     ArrayList resultado = null;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " listarModalidades: Inicio");
       this.modalidadesOAD = OCAPModalidadesOAD.getInstance();
       resultado = this.modalidadesOAD.listarModalidades();
       OCAPConfigApp.logger.info(getClass().getName() + " listarModalidades: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " listarModalidades: ERROR: " + e.getMessage());
       throw e;
     }
 
     return resultado;
   }
 
   public OCAPModalidadesOT buscarModalidad(long cModalidadId)
   {
     OCAPModalidadesOT dato = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarModalidad: Inicio");
       this.modalidadesOAD = OCAPModalidadesOAD.getInstance();
       dato = this.modalidadesOAD.buscarModalidad(cModalidadId);
       OCAPConfigApp.logger.info(getClass().getName() + " buscarModalidad: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarModalidad: ERROR: " + e.getMessage());
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return dato;
   }
 }

