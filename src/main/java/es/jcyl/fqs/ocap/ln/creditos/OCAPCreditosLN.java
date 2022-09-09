 package es.jcyl.fqs.ocap.ln.creditos;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.creditos.OCAPCreditosOAD;
 import es.jcyl.fqs.ocap.ot.creditos.OCAPCreditosOT;
 import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
 import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPCreditosLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPCreditosLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPCreditos(OCAPCreditosOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPCreditosOAD creditosOAD = OCAPCreditosOAD.getInstance();
       this.logger.info("LN");
       result = creditosOAD.altaOCAPCreditos(datos);
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
 
   public int bajaOCAPCreditos(int cCreditoId)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPCreditosOAD creditosOAD = OCAPCreditosOAD.getInstance();
       result = creditosOAD.bajaOCAPCreditos(cCreditoId);
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
 
   public int modificacionOCAPCreditos(OCAPCreditosOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try {
       OCAPCreditosOAD creditosOAD = OCAPCreditosOAD.getInstance();
       this.logger.info("LN");
       result = creditosOAD.modificacionOCAPCreditos(datos);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return result;
   }
 
   public OCAPCreditosOT buscarOCAPCreditos(long cCreditoId)
     throws SQLException, Exception
   {
     OCAPCreditosOT dato = null;
     try
     {
       OCAPCreditosOAD creditosOAD = OCAPCreditosOAD.getInstance();
       dato = creditosOAD.buscarOCAPCreditos(cCreditoId);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return dato;
   }
 
   public ArrayList buscarOCAPCreditosPorCategProfesional(OCAPUsuariosOT usuarioOT, Integer cGradoId, String meritosSeleccionados, JCYLUsuario jcylUsuario)
     throws SQLException, Exception
   {
     ArrayList datos = null;
     try
     {
       OCAPCreditosOAD creditosOAD = OCAPCreditosOAD.getInstance();
       datos = creditosOAD.buscarOCAPCreditosPorCategProfesional(usuarioOT, cGradoId, meritosSeleccionados, jcylUsuario);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return datos;
   }
 
   public ArrayList listadoOCAPCreditos()
     throws SQLException, Exception
   {
     ArrayList array = new ArrayList();
     try
     {
       OCAPCreditosOAD creditosOAD = OCAPCreditosOAD.getInstance();
       array = creditosOAD.listadoOCAPCreditos();
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return array;
   }
 
   public int listadoOCAPCreditosCuenta(long cCreditoId, long cEstatutId, long cGradoId, long cDefmeritoId, String nCreditos, String fCreacion, String fModificacion)
     throws SQLException, Exception
   {
     int total = 0;
     try
     {
       OCAPCreditosOAD creditosOAD = OCAPCreditosOAD.getInstance();
       total = creditosOAD.listadoOCAPCreditosCuenta(cCreditoId, cEstatutId, cGradoId, cDefmeritoId, nCreditos, fCreacion, fModificacion);
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
 
   public ArrayList consultaOCAPCreditos(long cCreditoId, long cEstatutId, long cGradoId, long cDefmeritoId, String nCreditos, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
     throws SQLException, Exception
   {
     ArrayList array = null;
     try
     {
       OCAPCreditosOAD creditosOAD = OCAPCreditosOAD.getInstance();
       array = creditosOAD.consultaOCAPCreditos(cCreditoId, cEstatutId, cGradoId, cDefmeritoId, nCreditos, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPCreditosCompetencias(int cEstatutId)
     throws SQLException, Exception
   {
     ArrayList listado = null;
     try
     {
       OCAPCreditosOAD creditosOAD = OCAPCreditosOAD.getInstance();
       listado = creditosOAD.listadoOCAPCreditosCompetencias(cEstatutId);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return listado;
   }
 }

