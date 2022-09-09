 package es.jcyl.fqs.ocap.ln.personalEstatutario;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.personalEstatutario.OCAPPersEstatutarioOAD;
 import es.jcyl.fqs.ocap.ot.personalEstatutario.OCAPPersEstatutarioOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPPersEstatutarioLN
 {
   public Logger loggerBD;
   OCAPPersEstatutarioOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public void OCAPPersEstatutarioLN()
   {
     this.variableOAD = OCAPPersEstatutarioOAD.getInstance();
   }
   public OCAPPersEstatutarioLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPPersEstatutario(OCAPPersEstatutarioOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPPersEstatutarioOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.altaOCAPPersEstatutario(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int bajaOCAPPersEstatutario(int cPersonalId)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPPersEstatutarioOAD.getInstance();
       result = this.variableOAD.bajaOCAPPersEstatutario(cPersonalId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPPersEstatutario(OCAPPersEstatutarioOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPPersEstatutarioOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.modificacionOCAPPersEstatutario(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public OCAPPersEstatutarioOT buscarOCAPPersEstatutario(long cPersonalId)
     throws SQLException, Exception
   {
     OCAPPersEstatutarioOT dato = null;
     try
     {
       OCAPConfigApp.logger.info("buscarOCAPPersEstatutario");
       this.variableOAD = OCAPPersEstatutarioOAD.getInstance();
       dato = this.variableOAD.buscarOCAPPersEstatutario(cPersonalId);
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
 
   public int listadoOCAPPersEstatutarioCuenta(long cPersonalId, String dNombre, String dDescripcion, String fCreacion, String fModificacion)
     throws Exception
   {
     int total = 0;
     try {
       this.variableOAD = OCAPPersEstatutarioOAD.getInstance();
       total = this.variableOAD.listadoOCAPPersEstatutarioCuenta(cPersonalId, dNombre, dDescripcion, fCreacion, fModificacion);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPPersEstatutario(long cPersonalId, String dNombre, String dDescripcion, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPPersEstatutarioOAD.getInstance();
       array = this.variableOAD.consultaOCAPPersEstatutario(cPersonalId, dNombre, dDescripcion, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPPersEstatutario()
     throws Exception
   {
     ArrayList array = new ArrayList();
     try {
       this.variableOAD = OCAPPersEstatutarioOAD.getInstance();
       array = this.variableOAD.listadoOCAPPersEstatutario();
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPPersEstatutario(int inicio, int cuantos)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPPersEstatutarioOAD.getInstance();
       array = this.variableOAD.listadoOCAPPersEstatutario(inicio, cuantos);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 }

