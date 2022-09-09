 package es.jcyl.fqs.ocap.ln.regimenJuridico;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.regimenJuridico.OCAPRegimenJuridicoOAD;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPRegimenJuridicoLN
 {
   OCAPRegimenJuridicoOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   public void OCAPRegimenJuridicoLN()
   {
     this.variableOAD = OCAPRegimenJuridicoOAD.getInstance();
   }
 
   public OCAPRegimenJuridicoLN(JCYLUsuario jcylUsuario) {
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList listadoOCAPRegimenJuridico()
     throws Exception
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPRegimenJuridicoOAD.getInstance();
       array = this.variableOAD.listadoOCAPRegimenJuridico();
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 }

