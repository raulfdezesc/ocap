 package es.jcyl.fqs.ocap.ln.meritos;
 
 import es.jcyl.fqs.ocap.actionforms.meritos.OCAPMeritosForm;
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ln.expedienteCarrera.OCAPExpedienteCarreraLN;
 import es.jcyl.fqs.ocap.ln.expedienteMC.OCAPExpedientemcLN;
 import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.SQLException;
 import java.util.Date;
 import org.apache.log4j.Logger;
 
 public class OCAPMeritosLN
 {
   Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPMeritosLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public void eliminarAclaracionesInvalidarMeritos(OCAPMeritosForm formulario, JCYLUsuario jcylUsuario)
     throws SQLException, Exception
   {
     OCAPExpedientemcLN expmcLN = null;
     OCAPExpedienteCarreraLN expCarreraLN = null;
     OCAPExpedientecarreraOT expCarreraOT = null;
     try
     {
       OCAPConfigApp.logger.info("eliminarAclaracionesInvalidarMeritos");
 
       JCYLGestionTransacciones.open(false);
 
       expmcLN = new OCAPExpedientemcLN(jcylUsuario);
       expmcLN.eliminarAclaracionesInvalidar(formulario.getCExpId().longValue(), jcylUsuario.getUser().getRol());
 
       expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
       expCarreraOT = new OCAPExpedientecarreraOT();
       expCarreraOT.setCExpId(formulario.getCExpId());
       expCarreraOT.setFDesistidoMC(new Date());
       expCarreraOT.setAModificadoPor(this.jcylUsuario.getUsuario().getC_usr_id());
       expCarreraLN.modificacionOCAPExpedientecarrera(expCarreraOT, false);
 
       JCYLGestionTransacciones.commit(true);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       JCYLGestionTransacciones.rollback();
       throw exSQL;
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       JCYLGestionTransacciones.rollback();
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
   }
 
   public void invalidarMerito(OCAPMeritosForm formulario, boolean bInvalidar, String perfil, OCAPExpedientecarreraOT expOT)
     throws SQLException, Exception
   {
     OCAPExpedientemcLN expmcLN = null;
     OCAPExpedienteCarreraLN expCarreraLN = null;
     try
     {
       OCAPConfigApp.logger.info("invalidarMerito");
 
       JCYLGestionTransacciones.open(false);
 
       expmcLN = new OCAPExpedientemcLN(this.jcylUsuario);
 
       expmcLN.invalidar(formulario.getCExpmcId(), bInvalidar, perfil, this.jcylUsuario.getUsuario().getC_usr_id());
 
       expCarreraLN = new OCAPExpedienteCarreraLN(this.jcylUsuario);
       expOT.setAModificadoPor(this.jcylUsuario.getUsuario().getC_usr_id());
       expCarreraLN.modificacionOCAPExpedientecarrera(expOT, false);
 
       JCYLGestionTransacciones.commit(true);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       JCYLGestionTransacciones.rollback();
       throw exSQL;
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       JCYLGestionTransacciones.rollback();
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
   }
 }

