 package es.jcyl.fqs.ocap.ln.mensajesMc;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.mensajesMc.OCAPMensajesMcOAD;
 import es.jcyl.fqs.ocap.ot.mensajesMc.OCAPMensajesMcOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPMensajesMcLN
 {
   public Logger loggerBD;
   OCAPMensajesMcOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public void OCAPMensajesMcLN()
   {
     this.variableOAD = OCAPMensajesMcOAD.getInstance();
   }
   public OCAPMensajesMcLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPMensajesMc(OCAPMensajesMcOT datos)
     throws Exception
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPMensajesMcOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.altaOCAPMensajesMc(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public int bajaOCAPMensajesMc(int cEstatutId)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPMensajesMcOAD.getInstance();
       result = this.variableOAD.bajaOCAPMensajesMc(cEstatutId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPMensajesMc(OCAPMensajesMcOT datos)
     throws Exception
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPMensajesMcOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.modificacionOCAPMensajesMc(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public OCAPMensajesMcOT buscarOCAPMensajesMc(long cEstatutId)
   {
     OCAPMensajesMcOT dato = null;
     try
     {
       this.variableOAD = OCAPMensajesMcOAD.getInstance();
       dato = this.variableOAD.buscarOCAPMensajesMc(cEstatutId);
     }
     catch (Exception e) {
       dato = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return dato;
   }
 
   public OCAPMensajesMcOT buscarOCAPMensajesPorEstatutIdDefMeritoId(long cEstatutId, long cDefMeritoId)
     throws SQLException, Exception
   {
     OCAPMensajesMcOT dato = null;
     try
     {
       this.variableOAD = OCAPMensajesMcOAD.getInstance();
       dato = this.variableOAD.buscarOCAPMensajesPorEstatutIdDefMeritoId(cEstatutId, cDefMeritoId);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return dato;
   }
 
   public int listadoOCAPMensajesMcCuenta(long cMensajeId, long cDefmeritoId, long cEstatutId, String dDescripcion, String fCreacion, String fModificacion)
     throws Exception
   {
     int total = 0;
     try {
       this.variableOAD = OCAPMensajesMcOAD.getInstance();
       total = this.variableOAD.listadoOCAPMensajesMcCuenta(cMensajeId, cDefmeritoId, cEstatutId, dDescripcion, fCreacion, fModificacion);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPMensajesMc(long cMensajeId, long cDefmeritoId, long cEstatutId, String dDescripcion, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPMensajesMcOAD.getInstance();
       array = this.variableOAD.consultaOCAPMensajesMc(cMensajeId, cDefmeritoId, cEstatutId, dDescripcion, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPMensajesMc()
     throws Exception
   {
     ArrayList array = new ArrayList();
     try {
       this.variableOAD = OCAPMensajesMcOAD.getInstance();
       array = this.variableOAD.listadoOCAPMensajesMc();
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPMensajesMc(int inicio, int cuantos)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPMensajesMcOAD.getInstance();
       array = this.variableOAD.listadoOCAPMensajesMc(inicio, cuantos);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 }

