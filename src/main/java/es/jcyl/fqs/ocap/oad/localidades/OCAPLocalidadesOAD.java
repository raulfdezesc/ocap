 package es.jcyl.fqs.ocap.oad.localidades;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.localidades.OCAPLocalidadesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPLocalidadesOAD
 {
   public Logger logger;
   private static OCAPLocalidadesOAD instance;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
   public static OCAPLocalidadesOAD getInstance() {
     if (instance == null) {
       instance = new OCAPLocalidadesOAD();
     }
     return instance;
   }
 
   private OCAPLocalidadesOAD() {
     $init$();
   }
 
   public ArrayList listarLocalidadesProvincia(String cProvId)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     ArrayList listado = null;
     try
     {
       String sqlStatement = "SELECT C_PROVINCIA_ID ||'-'|| C_MUNICIPIO_ID ||'-'|| C_ENTCOLEC_ID ||'-'|| C_ENTSING_ID C_LOCALIDAD_ID, D_LOCALIDAD  FROM COMU_LOCALIDADES  WHERE C_PROVINCIA_ID = ?  ORDER BY D_LOCALIDAD";
 
       OCAPConfigApp.logger.info("Sentencia SQL: " + sqlStatement);
       st = con.prepareStatement(sqlStatement, 1004, 1008);
       st.setString(1, cProvId);
       rs = st.executeQuery();
       listado = new ArrayList();
       int i = 0;
       while (rs.next()) {
         OCAPLocalidadesOT datos = new OCAPLocalidadesOT();
         datos.setCLocalidadId(rs.getString("C_LOCALIDAD_ID"));
         datos.setDLocalidad(rs.getString("D_LOCALIDAD"));
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
 
   public OCAPLocalidadesOT buscarLocalidad(String cLocalidadId)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     OCAPLocalidadesOT datos = null;
     try
     {
       String sqlStatement = "SELECT C_PROVINCIA_ID || '-' || C_MUNICIPIO_ID || '-' || C_ENTCOLEC_ID || '-' || C_ENTSING_ID C_LOCALIDAD_ID, D_LOCALIDAD  FROM COMU_LOCALIDADES  WHERE C_PROVINCIA_ID || '-' || C_MUNICIPIO_ID || '-' || C_ENTCOLEC_ID || '-' || C_ENTSING_ID = ?  ORDER BY D_LOCALIDAD ";
 
       OCAPConfigApp.logger.info("Sentencia SQL: " + sqlStatement);
       st = con.prepareStatement(sqlStatement);
       st.setString(1, cLocalidadId);
       rs = st.executeQuery();
       datos = new OCAPLocalidadesOT();
       while (rs.next()) {
         datos.setCLocalidadId(rs.getString("C_LOCALIDAD_ID"));
         datos.setDLocalidad(rs.getString("D_LOCALIDAD"));
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
 
     return datos;
   }
   
   public OCAPLocalidadesOT buscarLocalidadByName(String dLocalidad)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     OCAPLocalidadesOT datos = null;
     try
     {
       String sqlStatement = "SELECT C_PROVINCIA_ID || '-' || C_MUNICIPIO_ID || '-' || C_ENTCOLEC_ID || '-' || C_ENTSING_ID C_LOCALIDAD_ID, D_LOCALIDAD  FROM COMU_LOCALIDADES  WHERE D_LOCALIDAD = ?  ORDER BY D_LOCALIDAD ";
 
       OCAPConfigApp.logger.info("Sentencia SQL: " + sqlStatement);
       st = con.prepareStatement(sqlStatement);
       st.setString(1, dLocalidad.toUpperCase());
       rs = st.executeQuery();
       datos = new OCAPLocalidadesOT();
       while (rs.next()) {
         datos.setCLocalidadId(rs.getString("C_LOCALIDAD_ID"));
         datos.setDLocalidad(rs.getString("D_LOCALIDAD"));
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
 
     return datos;
   }
 }

