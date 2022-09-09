 package es.jcyl.fqs.ocap.ln.monitorizacion;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.monitorizacion.OCAPMonitorizacionOAD;
 import es.jcyl.fqs.ocap.ot.monitorizacion.OCAPMonitorizacionOT;
 import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPMonitorizacionLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPMonitorizacionLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList monitorizarBD(OCAPMonitorizacionOT monitorizacionOT)
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try {
       this.logger.debug("Inicio");
       OCAPMonitorizacionOAD monitorizacionOAD = OCAPMonitorizacionOAD.getInstance();
       resultado = monitorizacionOAD.monitorizarBD(monitorizacionOT);
       this.logger.debug("Fin");
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

