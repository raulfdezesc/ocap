 package es.jcyl.fqs.ocap.ln.provincias;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.provincias.OCAPProvinciasOAD;
 import es.jcyl.fqs.ocap.ot.provincias.OCAPProvinciasOT;
 import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPProvinciasLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPProvinciasLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList listarProvincias()
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try {
       OCAPProvinciasOAD provinciasOAD = OCAPProvinciasOAD.getInstance();
       this.logger.info("LN");
       resultado = provinciasOAD.listarProvincias();
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return resultado;
   }
 
   public ArrayList listarProvinciasComunidad(String cComuniId)
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try {
       OCAPProvinciasOAD provinciasOAD = OCAPProvinciasOAD.getInstance();
       this.logger.info("LN");
       resultado = provinciasOAD.listarProvinciasComunidad(cComuniId);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return resultado;
   }
 
   public OCAPProvinciasOT buscarProvincia(String cProvinciaId)
     throws SQLException, Exception
   {
     OCAPProvinciasOT dato = null;
     try {
       OCAPProvinciasOAD provinciasOAD = OCAPProvinciasOAD.getInstance();
       dato = provinciasOAD.buscarProvincia(cProvinciaId);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return dato;
   }
 }

