 package es.jcyl.fqs.ocap.ln.gestionCtes;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.ctes.OCAPCtesOAD;
 import es.jcyl.fqs.ocap.oad.ctesactivados.OCAPCtesactivadosOAD;
 import es.jcyl.fqs.ocap.ot.gestionCtes.OCAPGestionCtesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPGestionCtesLN
 {
   public Logger loggerBD;
   OCAPCtesOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPGestionCtesLN(JCYLUsuario jcylUsuario)
   {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public long altaOCAPCtes(OCAPGestionCtesOT componente)
     throws SQLException, Exception
   {
     OCAPGestionCtesOT dato = null;
     long idCte = 0L;
     int result = 0;
     try {
       OCAPConfigApp.logger.info("Inicio");
       this.variableOAD = OCAPCtesOAD.getInstance();
 
       idCte = this.variableOAD.altaOCAPCtes(componente);
 
       OCAPConfigApp.logger.info("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
 
       throw e;
     }
 
     return idCte;
   }
 
   public OCAPGestionCtesOT buscarOCAPCte(long cCteId)
     throws Exception
   {
     OCAPGestionCtesOT dato = null;
     try
     {
       this.variableOAD = OCAPCtesOAD.getInstance();
 
       dato = this.variableOAD.buscarOCAPCte(cCteId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return dato;
   }
 
   public int modificacionOCAPCtesactivados(OCAPGestionCtesOT datos)
     throws SQLException, Exception
   {
     Connection con = null;
     int result = 0;
     try
     {
       OCAPConfigApp.logger.info("modificacionOCAPCtes");
       OCAPCtesOAD variableOAD = OCAPCtesOAD.getInstance();
       OCAPCtesactivadosOAD ctesactivadosOAD = OCAPCtesactivadosOAD.getInstance();
       JCYLGestionTransacciones.open(false);
 
       result = variableOAD.modificacionOCAPCtes(datos);
 
       result = ctesactivadosOAD.modificacionOCAPCtesactivados(datos);
 
       JCYLGestionTransacciones.commit(true);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       JCYLGestionTransacciones.rollback();
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       JCYLGestionTransacciones.rollback();
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
 
     return result;
   }
 
   public ArrayList consultaOCAPCtesAct()
     throws Exception
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPCtesOAD.getInstance();
 
       array = this.variableOAD.consultaOCAPCtesAct();
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public ArrayList consultaOCAPCtes()
     throws Exception
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPCtesOAD.getInstance();
 
       array = this.variableOAD.consultaOCAPCtes();
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public void bajaOCAPCtes(OCAPGestionCtesOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPConfigApp.logger.info("bajaOCAPCtes");
 
       JCYLGestionTransacciones.open(false);
 
       this.variableOAD = OCAPCtesOAD.getInstance();
       OCAPCtesactivadosOAD ctesactivadosOAD = OCAPCtesactivadosOAD.getInstance();
 
       result = this.variableOAD.bajaOCAPCtes(datos);
 
       if (result != 0) {
         result = ctesactivadosOAD.bajaOCAPCtesactivados(datos);
       }
 
       JCYLGestionTransacciones.commit(true);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       JCYLGestionTransacciones.rollback();
       throw exSQL;
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       JCYLGestionTransacciones.rollback();
 
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
   }
 
   public int activaOCAPCtes(OCAPGestionCtesOT componente)
     throws SQLException, Exception
   {
     OCAPGestionCtesOT dato = null;
     int result = 0;
     try
     {
       OCAPCtesactivadosOAD ctesactivadosOAD = OCAPCtesactivadosOAD.getInstance();
       OCAPConfigApp.logger.info("activaOCAPCtes");
 
       result = ctesactivadosOAD.activaOCAPCtes(componente);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public ArrayList buscarOCAPCteConv(long cCteId)
     throws Exception
   {
     ArrayList dato = null;
     try
     {
       OCAPCtesactivadosOAD ctesactivadosOAD = OCAPCtesactivadosOAD.getInstance();
 
       dato = ctesactivadosOAD.buscarOCAPCteConv(cCteId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return dato;
   }
 }

