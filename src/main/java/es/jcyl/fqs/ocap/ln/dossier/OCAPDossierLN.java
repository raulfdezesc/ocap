 package es.jcyl.fqs.ocap.ln.dossier;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.dossier.OCAPDossierOAD;
 import es.jcyl.fqs.ocap.ot.encuestaCalidad.OCAPEncuestaCalidadOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import java.util.Arrays;
 import org.apache.log4j.Logger;
 
 public class OCAPDossierLN
 {
   public Logger loggerBD;
   OCAPDossierOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public void OCAPEncuestaCalidadLN()
   {
     this.variableOAD = OCAPDossierOAD.getInstance();
   }
   public OCAPDossierLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList buscarOCAPDossier(long cExpId)
     throws Exception
   {
     ArrayList listado = null;
     try
     {
       this.variableOAD = OCAPDossierOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       listado = this.variableOAD.OCAPBuscaRespuestasDossier(cExpId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return listado;
   }
 
   public int guardarDossier(long cExpId, int nEvidencias, String usuId, String[] idEvidencia)
     throws SQLException, Exception
   {
     int result = 0;
     OCAPEncuestaCalidadOT encuestaOT = null;
     try
     {
       JCYLGestionTransacciones.open(false);
 
       ArrayList array = new ArrayList(Arrays.asList(idEvidencia));
       this.variableOAD = OCAPDossierOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       this.variableOAD.OCAPEliminaDossier(cExpId);
 
       for (int i = 0; i < nEvidencias; i++) {
         encuestaOT = new OCAPEncuestaCalidadOT();
         encuestaOT.setCExpId(cExpId);
         encuestaOT.setACreadoPor(usuId);
         encuestaOT.setNEvidencia(i + 1);
 
         if (array.contains(i + ""))
           encuestaOT.setBEntregado(Constantes.SI);
         else {
           encuestaOT.setBEntregado(Constantes.NO);
         }
         result = this.variableOAD.rellenaOCAPDossier(encuestaOT);
       }
 
       JCYLGestionTransacciones.commit(true);
     }
     catch (SQLException exSQL) {
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
 
     return result;
   }
 
   public void eliminarDossier(long cExpId)
     throws SQLException, Exception
   {
     try
     {
       this.variableOAD = OCAPDossierOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       this.variableOAD.OCAPEliminaDossier(cExpId);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
   }
 }

