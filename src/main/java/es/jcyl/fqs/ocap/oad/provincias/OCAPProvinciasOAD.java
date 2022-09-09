 package es.jcyl.fqs.ocap.oad.provincias;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.provincias.OCAPProvinciasOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPProvinciasOAD
 {
   public Logger logger;
   public Logger loggerBD;
   private static OCAPProvinciasOAD instance;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public static OCAPProvinciasOAD getInstance() {
     if (instance == null) {
       instance = new OCAPProvinciasOAD();
     }
     return instance;
   }
 
   private OCAPProvinciasOAD() {
     $init$();
   }
 
   public ArrayList listarProvincias()
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String sqlStatement = "SELECT D_PROVINCIA, C_PROVINCIA_ID FROM COMU_PROVINCIAS ";
 
       String orderBy = " order by C_PROVINCIA_ID";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sqlStatement);
 
       st = con.prepareStatement(sqlStatement, 1004, 1008);
 
       rs = st.executeQuery();
 
       listado = new ArrayList();
       int i = 0;
       while (rs.next()) {
         OCAPProvinciasOT datos = new OCAPProvinciasOT();
         datos.setDProvincia(rs.getString("D_PROVINCIA"));
         datos.setCProvinciaId(rs.getString("C_PROVINCIA_ID"));
 
         listado.add(datos);
       }
     } catch (Exception ex) {
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
 
   public ArrayList listarProvinciasComunidad(String cComuniId)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String sqlStatement = "SELECT D_PROVINCIA, C_PROVINCIA_ID FROM COMU_PROVINCIAS WHERE C_COMUNI_ID = ? ";
 
       String orderBy = " order by C_PROVINCIA_ID";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sqlStatement);
 
       st = con.prepareStatement(sqlStatement, 1004, 1008);
 
       st.setString(1, cComuniId);
 
       rs = st.executeQuery();
 
       listado = new ArrayList();
       int i = 0;
       while (rs.next()) {
         OCAPProvinciasOT datos = new OCAPProvinciasOT();
         datos.setDProvincia(rs.getString("D_PROVINCIA"));
         datos.setCProvinciaId(rs.getString("C_PROVINCIA_ID"));
 
         listado.add(datos);
       }
     } catch (Exception ex) {
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
 
   public OCAPProvinciasOT buscarProvincia(String cProvinciaId)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     OCAPProvinciasOT datos = null;
     try
     {
       this.loggerBD.info(" buscarProvincia: " + cProvinciaId);
 
       String sqlStatement = "SELECT D_PROVINCIA FROM COMU_PROVINCIAS WHERE C_PROVINCIA_ID = ? ";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sqlStatement);
 
       st = con.prepareStatement(sqlStatement);
 
       st.setString(1, cProvinciaId);
 
       rs = st.executeQuery();
 
       datos = new OCAPProvinciasOT();
 
       while (rs.next())
         datos.setDProvincia(rs.getString("D_PROVINCIA"));
     }
     catch (SQLException ex)
     {
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return datos;
   }
 }

