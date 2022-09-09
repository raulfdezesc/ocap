 package es.jcyl.fqs.ocap.ln.expedientesexclusion;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.expedientesexclusion.OCAPExpedientesExclusionOAD;
 import es.jcyl.fqs.ocap.ot.expedientesexclusion.OCAPExpedientesExclusionOT;
 import es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT;
 import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPExpedientesExclusionLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPExpedientesExclusionLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public void altaExpediente(OCAPExpedientesExclusionOT expediente)
     throws SQLException, Exception
   {
     try
     {
       this.logger.info(getClass().getName() + " altaExpediente: Inicio");
       OCAPExpedientesExclusionOAD expedientesExclusionOAD = OCAPExpedientesExclusionOAD.getInstance();
       expedientesExclusionOAD.altaExpediente(expediente);
       this.logger.info(getClass().getName() + " altaExpediente: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.toString());
       throw e;
     }
   }
 
   public ArrayList buscarOCAPExpedientesExclusion(OCAPSolicitudesOT solicitudOT)
     throws SQLException, Exception
   {
     ArrayList motivos = null;
     try
     {
       this.logger.info(getClass().getName() + " buscarOCAPExpedientesExclusion: Inicio");
       OCAPExpedientesExclusionOAD expedientesExclusionOAD = OCAPExpedientesExclusionOAD.getInstance();
       motivos = expedientesExclusionOAD.buscarOCAPExpedientesExclusion(solicitudOT);
       this.logger.info(getClass().getName() + " buscarOCAPExpedientesExclusion: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.toString());
       throw e;
     }
 
     return motivos;
   }
 
   public void bajaExpediente(OCAPExpedientesExclusionOT expediente)
     throws SQLException, Exception
   {
     try
     {
       this.logger.info(getClass().getName() + " borrarExpediente: Inicio");
       OCAPExpedientesExclusionOAD expedientesExclusionOAD = OCAPExpedientesExclusionOAD.getInstance();
       expedientesExclusionOAD.bajaExpediente(expediente);
       this.logger.info(getClass().getName() + " borrarExpediente: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.toString());
       throw e;
     }
   }
 
   public void borraExpediente(long expedienteId)
     throws SQLException, Exception
   {
     try
     {
       this.logger.info(getClass().getName() + " borrarExpediente: Inicio");
       OCAPExpedientesExclusionOAD expedientesExclusionOAD = OCAPExpedientesExclusionOAD.getInstance();
       expedientesExclusionOAD.borraExpediente(expedienteId);
       this.logger.info(getClass().getName() + " borrarExpediente: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.toString());
       throw e;
     }
   }
 }

