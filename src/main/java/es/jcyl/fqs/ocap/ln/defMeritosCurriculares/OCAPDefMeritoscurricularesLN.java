 package es.jcyl.fqs.ocap.ln.defMeritosCurriculares;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.defMeritosCurriculares.OCAPDefMeritoscurricularesOAD;
 import es.jcyl.fqs.ocap.ot.defMeritosCurriculares.OCAPDefMeritoscurricularesOT;
 import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPDefMeritoscurricularesLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPDefMeritoscurricularesLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPDefMeritoscurriculares(OCAPDefMeritoscurricularesOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPDefMeritoscurricularesOAD defMeritoscurricularesOAD = OCAPDefMeritoscurricularesOAD.getInstance();
       this.logger.info("LN");
       result = defMeritoscurricularesOAD.altaOCAPDefMeritoscurriculares(datos);
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
 
   public int bajaOCAPDefMeritoscurriculares(int cDefmeritoId)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPDefMeritoscurricularesOAD defMeritoscurricularesOAD = OCAPDefMeritoscurricularesOAD.getInstance();
       result = defMeritoscurricularesOAD.bajaOCAPDefMeritoscurriculares(cDefmeritoId);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return result;
   }
 
   public int modificacionOCAPDefMeritoscurriculares(OCAPDefMeritoscurricularesOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPDefMeritoscurricularesOAD defMeritoscurricularesOAD = OCAPDefMeritoscurricularesOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = defMeritoscurricularesOAD.modificacionOCAPDefMeritoscurriculares(datos);
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
 
   public OCAPDefMeritoscurricularesOT buscarOCAPDefMeritoscurriculares(long cDefmeritoId)
     throws SQLException, Exception
   {
     OCAPDefMeritoscurricularesOT dato = null;
     try
     {
       OCAPDefMeritoscurricularesOAD defMeritoscurricularesOAD = OCAPDefMeritoscurricularesOAD.getInstance();
       dato = defMeritoscurricularesOAD.buscarOCAPDefMeritoscurriculares(cDefmeritoId);
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
 
   public int listadoOCAPDefMeritoscurricularesCuenta(long cDefmeritoId, String dNombre, String dDescripcion, String fCreacion, String fModificacion)
     throws SQLException, Exception
   {
     int total = 0;
     try {
       OCAPDefMeritoscurricularesOAD defMeritoscurricularesOAD = OCAPDefMeritoscurricularesOAD.getInstance();
       total = defMeritoscurricularesOAD.listadoOCAPDefMeritoscurricularesCuenta(cDefmeritoId, dNombre, dDescripcion, fCreacion, fModificacion);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPDefMeritoscurriculares(long cDefmeritoId, String dNombre, String dDescripcion, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
     throws SQLException, Exception
   {
     ArrayList array = null;
     try
     {
       OCAPDefMeritoscurricularesOAD defMeritoscurricularesOAD = OCAPDefMeritoscurricularesOAD.getInstance();
       array = defMeritoscurricularesOAD.consultaOCAPDefMeritoscurriculares(cDefmeritoId, dNombre, dDescripcion, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
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
 
   public ArrayList listadoOCAPDefMeritoscurriculares()
     throws SQLException, Exception
   {
     ArrayList array = new ArrayList();
     try
     {
       OCAPDefMeritoscurricularesOAD defMeritoscurricularesOAD = OCAPDefMeritoscurricularesOAD.getInstance();
       array = defMeritoscurricularesOAD.listadoOCAPDefMeritoscurriculares();
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
 
   public ArrayList listadoOCAPDefMeritoscurriculares(int inicio, int cuantos)
     throws SQLException, Exception
   {
     ArrayList array = null;
     try {
       OCAPDefMeritoscurricularesOAD defMeritoscurricularesOAD = OCAPDefMeritoscurricularesOAD.getInstance();
       array = defMeritoscurricularesOAD.listadoOCAPDefMeritoscurriculares(inicio, cuantos);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     }
     catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listarDefmeritos()
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try
     {
       this.logger.info("Inicio");
       OCAPDefMeritoscurricularesOAD defMeritoscurricularesOAD = OCAPDefMeritoscurricularesOAD.getInstance();
       resultado = defMeritoscurricularesOAD.listadoOCAPDefMeritoscurriculares();
       this.logger.info("Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return resultado;
   }
 
   public ArrayList listarDefmeritosPorCatProfesional(long gradoId, long profesionalId)
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try {
       this.logger.info("Inicio");
       OCAPDefMeritoscurricularesOAD defMeritoscurricularesOAD = OCAPDefMeritoscurricularesOAD.getInstance();
       resultado = defMeritoscurricularesOAD.listadoOCAPDefMeritoscurricularesPorCatProfesional(gradoId, profesionalId);
       this.logger.info("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return resultado;
   }
 }

