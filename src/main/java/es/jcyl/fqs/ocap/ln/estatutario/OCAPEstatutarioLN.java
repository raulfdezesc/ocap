 package es.jcyl.fqs.ocap.ln.estatutario;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.estatutario.OCAPEstatutarioOAD;
 import es.jcyl.fqs.ocap.ot.estatutario.OCAPEstatutarioOT;
 import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPEstatutarioLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPEstatutarioLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPEstatutario(OCAPEstatutarioOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPEstatutarioOAD estatutarioOAD = OCAPEstatutarioOAD.getInstance();
       this.logger.info("LN");
       result = estatutarioOAD.altaOCAPEstatutario(datos);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return result;
   }
 
   public int bajaOCAPEstatutario(int cEstatutId)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPEstatutarioOAD estatutarioOAD = OCAPEstatutarioOAD.getInstance();
       result = estatutarioOAD.bajaOCAPEstatutario(cEstatutId);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return result;
   }
 
   public int modificacionOCAPEstatutario(OCAPEstatutarioOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try {
       OCAPEstatutarioOAD estatutarioOAD = OCAPEstatutarioOAD.getInstance();
       this.logger.info("LN");
       result = estatutarioOAD.modificacionOCAPEstatutario(datos);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return result;
   }
 
   public OCAPEstatutarioOT buscarOCAPEstatutario(long cEstatutId)
     throws SQLException, Exception
   {
     OCAPEstatutarioOT dato = null;
     try
     {
       OCAPEstatutarioOAD estatutarioOAD = OCAPEstatutarioOAD.getInstance();
       dato = estatutarioOAD.buscarOCAPEstatutario(cEstatutId);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return dato;
   }
 
   public int listadoOCAPEstatutarioCuenta(long cEstatutId, long cPersonalId, String dNombre, String dDescripcion, String fCreacion, String fModificacion)
     throws SQLException, Exception
   {
     int total = 0;
     try
     {
       OCAPEstatutarioOAD estatutarioOAD = OCAPEstatutarioOAD.getInstance();
       total = estatutarioOAD.listadoOCAPEstatutarioCuenta(cEstatutId, cPersonalId, dNombre, dDescripcion, fCreacion, fModificacion);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPEstatutario(long cEstatutId, long cPersonalId, String dNombre, String dDescripcion, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
     throws SQLException, Exception
   {
     ArrayList array = null;
     try
     {
       OCAPEstatutarioOAD estatutarioOAD = OCAPEstatutarioOAD.getInstance();
       array = estatutarioOAD.consultaOCAPEstatutario(cEstatutId, cPersonalId, dNombre, dDescripcion, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPEstatutario()
     throws SQLException, Exception
   {
     ArrayList array = new ArrayList();
     try
     {
       OCAPEstatutarioOAD estatutarioOAD = OCAPEstatutarioOAD.getInstance();
       array = estatutarioOAD.listadoOCAPEstatutario();
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPEstatutariosUsados()
     throws SQLException, Exception
   {
     ArrayList array = new ArrayList();
     try
     {
       OCAPEstatutarioOAD estatutarioOAD = OCAPEstatutarioOAD.getInstance();
       array = estatutarioOAD.listadoOCAPEstatutariosUsados();
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPEstatutario(int inicio, int cuantos)
     throws SQLException, Exception
   {
     ArrayList array = null;
     try {
       OCAPEstatutarioOAD estatutarioOAD = OCAPEstatutarioOAD.getInstance();
       array = estatutarioOAD.listadoOCAPEstatutario(inicio, cuantos);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return array;
   }
 }

