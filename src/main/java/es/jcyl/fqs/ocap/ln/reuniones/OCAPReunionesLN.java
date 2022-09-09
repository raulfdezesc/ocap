 package es.jcyl.fqs.ocap.ln.reuniones;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.reuniones.OCAPReunionesOAD;
 import es.jcyl.fqs.ocap.ot.reuniones.OCAPReunionesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.sql.Connection;
 import org.apache.log4j.Logger;
 
 public class OCAPReunionesLN
 {
   OCAPReunionesOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   public OCAPReunionesLN(JCYLUsuario jcylUsuario)
   {
     this.jcylUsuario = jcylUsuario;
   }
 
   public long altaOCAPReuniones(OCAPReunionesOT reunion, Connection con)
     throws Exception
   {
     OCAPReunionesOT dato = null;
     long result = 0L;
     try
     {
       this.variableOAD = OCAPReunionesOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
 
       result = this.variableOAD.altaOCAPReuniones(reunion, con);
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public int altaOCAPEvaluadoresTratados(OCAPReunionesOT reunion, Connection con)
     throws Exception
   {
     OCAPReunionesOT dato = null;
     int result = 0;
     try
     {
       this.variableOAD = OCAPReunionesOAD.getInstance();
       OCAPConfigApp.logger.info(getClass().getName() + " LN");
 
       result = this.variableOAD.altaOCAEvaluadoresTratados(reunion, con);
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 }

