 package es.jcyl.fqs.ocap.ln.revisiones;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.revisiones.OCAPRevisionesOAD;
 import es.jcyl.fqs.ocap.ot.revisiones.OCAPRevisionesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import org.apache.log4j.Logger;
 
 public class OCAPRevisionesLN
 {
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPRevisionesLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public long altaOCAPRevisiones(OCAPRevisionesOT revision)
     throws Exception
   {
     long result = 0L;
     try
     {
       OCAPRevisionesOAD variableOAD = OCAPRevisionesOAD.getInstance();
       result = variableOAD.altaOCAPRevisiones(revision);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public void bajaOCAPRevisiones(OCAPRevisionesOT revision)
     throws Exception
   {
     try
     {
       OCAPRevisionesOAD variableOAD = OCAPRevisionesOAD.getInstance();
       variableOAD.bajaOCAPRevisiones(revision);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
   }
 
   public OCAPRevisionesOT buscarOCAPRevisiones(long cExpId)
     throws SQLException, Exception
   {
     OCAPRevisionesOT revisionOT = null;
     try
     {
       OCAPRevisionesOAD variableOAD = OCAPRevisionesOAD.getInstance();
       revisionOT = variableOAD.buscarOCAPRevisiones(cExpId);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return revisionOT;
   }
 
   public void modificaOCAPRevisiones(OCAPRevisionesOT revision)
     throws Exception
   {
     try
     {
       OCAPRevisionesOAD variableOAD = OCAPRevisionesOAD.getInstance();
       variableOAD.modificaOCAPRevisiones(revision);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
   }
 }

