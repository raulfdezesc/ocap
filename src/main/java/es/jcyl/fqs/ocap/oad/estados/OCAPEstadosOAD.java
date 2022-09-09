 package es.jcyl.fqs.ocap.oad.estados;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.estados.OCAPEstadosOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPEstadosOAD
 {
   public Logger loggerBD;
   private static OCAPEstadosOAD instance;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public static OCAPEstadosOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPEstadosOAD();
     }
     return instance;
   }
 
   private OCAPEstadosOAD() {
     $init$();
   }
 
   public OCAPEstadosOT buscarEstados(long cEstadoId)
     throws SQLException, Exception
   {
     this.loggerBD.debug("Inicio");
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     OCAPEstadosOT datos = null;
     try
     {
       String sql = "SELECT D_NOMBRE, D_DESCRIPCION FROM OCAP_ESTADOS WHERE C_ESTADO_ID = ? AND B_BORRADO = 'N'";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cEstadoId);
 
       rs = st.executeQuery();
 
       datos = new OCAPEstadosOT();
       if (rs.next()) {
         datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
       }
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       this.loggerBD.debug("Fin");
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return datos;
   }
 
   public OCAPEstadosOT buscarEstadosCompleto(long cEstadoId)
     throws SQLException, Exception
   {
     this.loggerBD.info(" buscarEstadosCompleto");
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     OCAPEstadosOT datos = null;
 
     StringBuffer sql = new StringBuffer();
     try
     {
       sql.append(" SELECT C_ESTADO_ID, D_NOMBRE, D_DESCRIPCION, B_BORRADO, ").append(" F_USUALTA, C_USUALTA, F_USUMODI, C_USUMODI, C_FASE, B_LISTADO ").append(" FROM OCAP_ESTADOS WHERE C_ESTADO_ID = ? AND B_BORRADO = 'N' ");
 
       st = con.prepareStatement(sql.toString());
 
       st.setLong(1, cEstadoId);
 
       rs = st.executeQuery();
 
       datos = new OCAPEstadosOT();
       if (rs.next()) {
         datos.setcEstadoId(rs.getLong("C_ESTADO_ID"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
         datos.setBBorrado(rs.getString("B_BORRADO"));
         datos.setFUsualta(rs.getDate("F_USUALTA"));
         datos.setCUsualta(rs.getString("C_USUALTA"));
         datos.setFUsumodi(rs.getDate("F_USUMODI"));
         datos.setCUsumodi(rs.getString("C_USUMODI"));
         datos.setCUsumodi(rs.getString("C_USUMODI"));
         datos.setDFase(rs.getString("C_FASE"));
         datos.setBListado(rs.getString("B_LISTADO"));
       }
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       this.loggerBD.debug("Fin");
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return datos;
   }
 
   public ArrayList listarEstados()
     throws SQLException, Exception
   {
     this.loggerBD.debug("Inicio");
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String sql = "SELECT D_NOMBRE, D_DESCRIPCION, C_ESTADO_ID FROM OCAP_ESTADOS WHERE B_BORRADO = 'N' AND C_ESTADO_ID <> 1 ORDER BY C_ESTADO_ID";
 
       st = con.prepareStatement(sql);
 
       rs = st.executeQuery();
 
       listado = new ArrayList();
 
       while (rs.next()) {
         OCAPEstadosOT datos = new OCAPEstadosOT();
         datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setcEstadoId(rs.getLong("C_ESTADO_ID"));
 
         listado.add(datos);
       }
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       this.loggerBD.debug("Fin");
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 
   public ArrayList listarEstadosFase(String fase, String bListado)
     throws SQLException, Exception
   {
     this.loggerBD.debug("Inicio");
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String sql = "SELECT D_NOMBRE, D_DESCRIPCION, C_ESTADO_ID FROM OCAP_ESTADOS WHERE B_BORRADO = 'N'";
 
       if (fase != null) {
         sql = sql + "AND C_FASE ='" + fase + "' ";
       }
       if (Constantes.SI.equals(bListado))
         sql = sql + " AND B_LISTADO='Y' ";
       sql = sql + "ORDER BY C_ESTADO_ID";
 
       st = con.prepareStatement(sql);
 
       rs = st.executeQuery();
 
       listado = new ArrayList();
 
       while (rs.next()) {
         OCAPEstadosOT datos = new OCAPEstadosOT();
         datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setcEstadoId(rs.getLong("C_ESTADO_ID"));
 
         listado.add(datos);
       }
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       this.loggerBD.debug("Fin");
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 }

