 package es.jcyl.fqs.ocap.ln.historicoProfesionales;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.historicoProfesionales.OCAPHistoricoProfesionalesOAD;
 import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPHistoricoProfesionalesLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPHistoricoProfesionalesLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList buscarHistorico(String dni)
     throws SQLException, Exception
   {
     ArrayList listado = null;
     try
     {
       this.logger.info(getClass().getName() + " buscarHistorico: Inicio");
       OCAPHistoricoProfesionalesOAD historicoProfesionalesOAD = OCAPHistoricoProfesionalesOAD.getInstance();
       listado = historicoProfesionalesOAD.buscarHistorico(dni);
       this.logger.info(getClass().getName() + " buscarHistorico: Fin");
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

