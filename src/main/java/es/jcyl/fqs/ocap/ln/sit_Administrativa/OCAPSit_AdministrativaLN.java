 package es.jcyl.fqs.ocap.ln.sit_Administrativa;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.sit_Administrativa.OCAPSit_AdministrativaOAD;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPSit_AdministrativaLN
 {
   OCAPSit_AdministrativaOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   public void OCAPSit_AdministrativaLN()
   {
     this.variableOAD = OCAPSit_AdministrativaOAD.getInstance();
   }
 
   public OCAPSit_AdministrativaLN(JCYLUsuario jcylUsuario) {
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList listadoOCAPSit_Administrativa()
     throws Exception
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPSit_AdministrativaOAD.getInstance();
       array = this.variableOAD.listadoOCAPSit_Administrativa();
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 }

