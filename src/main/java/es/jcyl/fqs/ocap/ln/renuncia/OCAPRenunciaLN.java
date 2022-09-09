 package es.jcyl.fqs.ocap.ln.renuncia;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ln.expedienteCarrera.OCAPExpedienteCarreraLN;
 import es.jcyl.fqs.ocap.oad.renuncia.OCAPRenunciaOAD;
 import es.jcyl.fqs.ocap.ot.expedientesexclusion.OCAPExpedientesExclusionOT;
 import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.SQLException;
 import org.apache.log4j.Logger;
 
 public class OCAPRenunciaLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
   public OCAPRenunciaLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int renunciar(OCAPExpedientesExclusionOT expedientesExclusionOT, JCYLUsuario jcylUsuario)
     throws SQLException, Exception
   {
     int idExpExclusion = 0;
     Connection con = null;
     try
     {
       OCAPConfigApp.logger.debug(getClass().getName() + " renunciar: Inicio");
 
       con = JCYLGestionTransacciones.getConnection();
 
       con.setAutoCommit(false);
 
       OCAPRenunciaOAD renunciaOAD = OCAPRenunciaOAD.getInstance();
       idExpExclusion = renunciaOAD.altaOCAPExpedienteExclusion(expedientesExclusionOT, con);
 
       OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
       expedienteCarreraLN.modificarEstadoExpCarrera(expedientesExclusionOT.getCExpId(), 15, true);
 
       con.commit();
 
       OCAPConfigApp.logger.debug(getClass().getName() + " renunciar: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     } finally {
       con.setAutoCommit(true);
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return idExpExclusion;
   }
 }

