 package es.jcyl.fqs.ocap.ln.procedimiento;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.procedimiento.OCAPProcedimientoOAD;
 import es.jcyl.fqs.ocap.ot.procedimiento.OCAPProcedimientoOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPProcedimientoLN
 {
   public Logger loggerBD;
   OCAPProcedimientoOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public void OCAPProcedimientoLN()
   {
     this.variableOAD = OCAPProcedimientoOAD.getInstance();
   }
   public OCAPProcedimientoLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPProcedimiento(OCAPProcedimientoOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPProcedimientoOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.altaOCAPProcedimiento(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int bajaOCAPProcedimiento(int cTipoId)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPProcedimientoOAD.getInstance();
       result = this.variableOAD.bajaOCAPProcedimiento(cTipoId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPProcedimiento(OCAPProcedimientoOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPProcedimientoOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.modificacionOCAPProcedimiento(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public OCAPProcedimientoOT buscarOCAPProcedimiento(long cSitAdmId)
     throws SQLException, Exception
   {
     OCAPProcedimientoOT dato = null;
     try
     {
       OCAPConfigApp.logger.info("buscarOCAPProcedimiento");
       this.variableOAD = OCAPProcedimientoOAD.getInstance();
       dato = this.variableOAD.buscarOCAPProcedimiento(cSitAdmId);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return dato;
   }
 
   public ArrayList listadoOCAPProcedimiento()
     throws Exception
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPProcedimientoOAD.getInstance();
       array = this.variableOAD.listadoOCAPProcedimiento();
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 }

