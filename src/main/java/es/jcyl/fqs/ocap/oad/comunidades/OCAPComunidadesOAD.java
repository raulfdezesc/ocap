 package es.jcyl.fqs.ocap.oad.comunidades;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.comunidades.OCAPComunidadesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPComunidadesOAD
 {
   public Logger logger;
   private static OCAPComunidadesOAD instance;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
   public static OCAPComunidadesOAD getInstance() {
     if (instance == null) {
       instance = new OCAPComunidadesOAD();
     }
     return instance;
   }
 
   private OCAPComunidadesOAD() {
     $init$();
   }
 
   public ArrayList listarComunidades()
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     ArrayList listado = null;
     try
     {
       String sqlStatement = "SELECT C_COMUNI_ID, D_COMUNI FROM COMU_COMUNIDADES order by C_COMUNI_ID";
       OCAPConfigApp.logger.info("Sentencia SQL: " + sqlStatement);
       st = con.prepareStatement(sqlStatement, 1004, 1008);
       rs = st.executeQuery();
       listado = new ArrayList();
       int i = 0;
       while (rs.next()) {
         OCAPComunidadesOT datos = new OCAPComunidadesOT();
         datos.setDComuni(rs.getString("D_COMUNI").toUpperCase());
         datos.setCComuniId(rs.getString("C_COMUNI_ID"));
         listado.add(datos);
       }
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 
   public String buscarComunidad(String cComuniId)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     String nombre = null;
     try
     {
       String sql = "SELECT D_COMUNI FROM COMU_COMUNIDADES WHERE C_COMUNI_ID = ? ";
       OCAPConfigApp.logger.info("Sentencia SQL: " + sql);
       st = con.prepareStatement(sql);
       st.setString(1, cComuniId);
       rs = st.executeQuery();
       int i = 0;
       if (rs.next())
         nombre = rs.getString("D_COMUNI");
     }
     catch (Exception ex)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return nombre;
   }
 }

