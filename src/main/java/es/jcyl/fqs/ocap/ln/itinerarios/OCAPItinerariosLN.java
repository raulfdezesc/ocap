 package es.jcyl.fqs.ocap.ln.itinerarios;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.itinerarios.OCAPItinerariosOAD;
 import es.jcyl.fqs.ocap.ot.itinerarios.OCAPItinerariosOT;
 import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPItinerariosLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPItinerariosLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList listadoItinerarios()
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try
     {
       this.logger.info(getClass().getName() + " listadoItinerarios: Inicio");
       OCAPItinerariosOAD itinerariosOAD = OCAPItinerariosOAD.getInstance();
       resultado = itinerariosOAD.listadoItinerarios();
       this.logger.info(getClass().getName() + " listadoItinerarios: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return resultado;
   }
 
   public ArrayList listadoItinerariosUsados()
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try {
       this.logger.info(getClass().getName() + " listadoItinerariosUsados: Inicio");
       OCAPItinerariosOAD itinerariosOAD = OCAPItinerariosOAD.getInstance();
       resultado = itinerariosOAD.listadoItinerariosUsados();
       this.logger.info(getClass().getName() + " listadoItinerariosUsados: Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return resultado;
   }
 
   public OCAPItinerariosOT buscarOCAPItinerarios(long cItinerarioId)
     throws SQLException, Exception
   {
     OCAPItinerariosOT dato = null;
     try
     {
       OCAPItinerariosOAD itinerariosOAD = OCAPItinerariosOAD.getInstance();
       dato = itinerariosOAD.buscarOCAPItinerarios(cItinerarioId);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return dato;
   }
 
   public OCAPItinerariosOT buscarManualesReferencia(long cItinerarioId)
     throws SQLException, Exception
   {
     OCAPItinerariosOT dato = null;
     try {
       OCAPItinerariosOAD itinerariosOAD = OCAPItinerariosOAD.getInstance();
       dato = itinerariosOAD.buscarManualesReferencia(cItinerarioId);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return dato;
   }
 
   public ArrayList listarItinerarios(long cGradoId, long idCategProfesional, long idEspecialidad, long cProcedimientoId)
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try
     {
       this.logger.info(getClass().getName() + " listarItinerarios: Inicio");
       OCAPItinerariosOAD itinerariosOAD = OCAPItinerariosOAD.getInstance();
       resultado = itinerariosOAD.listadoOCAPItinerarios(cGradoId, idCategProfesional, idEspecialidad, cProcedimientoId);
       this.logger.info(getClass().getName() + " listarItinerarios: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.toString());
       throw e;
     }
 
     return resultado;
   }
 
   public ArrayList buscarOCAPCategEspecItinerarios(ArrayList itinerarios)
     throws SQLException, Exception
   {
     ArrayList datos = null;
     try {
       OCAPItinerariosOAD itinerariosOAD = OCAPItinerariosOAD.getInstance();
       datos = itinerariosOAD.buscarOCAPCategEspecItinerarios(itinerarios);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return datos;
   }
 
   public ArrayList listadoEvidencias(long cItinerarioId)
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try
     {
       this.logger.info(getClass().getName() + " listadoEvidencias: Inicio");
       OCAPItinerariosOAD itinerariosOAD = OCAPItinerariosOAD.getInstance();
       resultado = itinerariosOAD.listadoEvidencias(cItinerarioId);
       this.logger.info(getClass().getName() + " listadoEvidencias: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return resultado;
   }
 
   public ArrayList listarManualesCte(long cCteId)
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try {
       this.logger.info(getClass().getName() + " listarManualesCte: Inicio");
 
       OCAPItinerariosOAD itinerariosOAD = OCAPItinerariosOAD.getInstance();
       resultado = itinerariosOAD.listarManualesCte(cCteId);
 
       this.logger.info(getClass().getName() + " listarManualesCte: Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return resultado;
   }
 
   public ArrayList listarManualesPorGrupo(long cCteId, boolean verNoAsignadosCte)
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try {
       this.logger.info("Inicio");
 
       OCAPItinerariosOAD itinerariosOAD = OCAPItinerariosOAD.getInstance();
       resultado = itinerariosOAD.listarManualesPorGrupo(cCteId, verNoAsignadosCte);
 
       this.logger.info("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return resultado;
   }
 
   public ArrayList listarItinerariosConvocatoria(long cConvocatoriaId, long cCompfqsId)
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try
     {
       this.logger.info(getClass().getName() + " listarItinerariosConvocatoria: Inicio");
       OCAPItinerariosOAD itinerariosOAD = OCAPItinerariosOAD.getInstance();
       resultado = itinerariosOAD.listarItinerariosConvocatoria(cConvocatoriaId, cCompfqsId);
       this.logger.info(getClass().getName() + " listarItinerariosConvocatoria: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.toString());
       throw e;
     }
 
     return resultado;
   }
 }

