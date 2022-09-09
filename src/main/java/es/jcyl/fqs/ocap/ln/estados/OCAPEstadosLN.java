 package es.jcyl.fqs.ocap.ln.estados;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.estados.OCAPEstadosOAD;
 import es.jcyl.fqs.ocap.ot.estados.OCAPEstadosOT;
 import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPEstadosLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPEstadosLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public OCAPEstadosOT buscarEstados(long cEstadoId)
     throws SQLException, Exception
   {
     OCAPEstadosOT estado = null;
     try
     {
       this.logger.info("Inicio");
       OCAPEstadosOAD estadosOAD = OCAPEstadosOAD.getInstance();
       estado = estadosOAD.buscarEstados(cEstadoId);
       this.logger.info("Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return estado;
   }
 
   public OCAPEstadosOT buscarEstadosCompleto(long cEstadoId)
     throws SQLException, Exception
   {
     OCAPEstadosOT estado = null;
     try
     {
       this.logger.info("Inicio buscarEstadosCompleto");
       OCAPEstadosOAD estadosOAD = OCAPEstadosOAD.getInstance();
       estado = estadosOAD.buscarEstadosCompleto(cEstadoId);
       this.logger.info("Fin buscarEstadosCompleto");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return estado;
   }
 
   public ArrayList listarEstados()
     throws SQLException, Exception
   {
     ArrayList listado = null;
     try {
       this.logger.info("Inicio");
       OCAPEstadosOAD estadosOAD = OCAPEstadosOAD.getInstance();
       listado = estadosOAD.listarEstados();
       this.logger.info("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return listado;
   }
 
   public ArrayList listarEstadosFase(String fase, String bListado)
     throws SQLException, Exception
   {
     ArrayList listado = null;
     try
     {
       this.logger.info("Inicio");
       OCAPEstadosOAD estadosOAD = OCAPEstadosOAD.getInstance();
       listado = estadosOAD.listarEstadosFase(fase, bListado);
       this.logger.info("Fin");
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

