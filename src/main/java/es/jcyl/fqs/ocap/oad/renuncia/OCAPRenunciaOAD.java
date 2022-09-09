 package es.jcyl.fqs.ocap.oad.renuncia;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.expedientesexclusion.OCAPExpedientesExclusionOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.SQLException;
 import org.apache.log4j.Logger;
 
 public class OCAPRenunciaOAD
 {
   public Logger logger;
   private static OCAPRenunciaOAD instance;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
   public static OCAPRenunciaOAD getInstance() {
     if (instance == null) {
       instance = new OCAPRenunciaOAD();
     }
     return instance;
   }
 
   private OCAPRenunciaOAD() {
     $init$();
   }
 
   public int altaOCAPExpedienteExclusion(OCAPExpedientesExclusionOT datos, Connection con)
     throws Exception
   {
     PreparedStatement st = null;
 
     int filas = 0;
     try
     {
       String sql = "INSERT INTO OCAP_EXPEDIENTESEXCLUSION (C_EXPEXCLUSION_ID, C_EXP_ID, C_EXCLUSION_ID, B_BORRADO, F_USUALTA, C_USUALTA, F_USUMODI, C_USUMODI) VALUES (OCAP_EEX_ID_SEQ.nextval, ?, ?, 'N', SYSDATE, ?, ?, ?)";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
       st.setLong(1, datos.getCExpId());
       st.setLong(2, datos.getCExclusionId());
       st.setString(3, datos.getCUsualta());
       st.setString(4, null);
       st.setString(5, null);
 
       filas = st.executeUpdate();
     }
     catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return filas;
   }
 }

