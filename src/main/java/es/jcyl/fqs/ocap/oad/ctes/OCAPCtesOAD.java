 package es.jcyl.fqs.ocap.oad.ctes;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.gestionCtes.OCAPGestionCtesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.CallableStatement;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPCtesOAD
 {
   public Logger logger;
   public Logger loggerBD;
   private static OCAPCtesOAD instance;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public static OCAPCtesOAD getInstance() {
     if (instance == null) {
       instance = new OCAPCtesOAD();
     }
     return instance;
   }
 
   private OCAPCtesOAD() {
     $init$();
   }
 
   public long altaOCAPCtes(OCAPGestionCtesOT datos)
     throws SQLException
   {
     CallableStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     long idCte = 0L;
     try
     {
       this.loggerBD.info(" altaOCAPCtes: " + datos.getDNombre());
       String sql = "BEGIN INSERT INTO OCAP_CTES (C_CTE_ID, D_NOMBRE, F_CONSTITUCION, F_USUALTA, B_BORRADO, C_USUALTA, C_GERENCIA_ID) VALUES (OCAP_CTE_ID_SEQ.nextval, ?, TO_DATE(?,'DD/MM/RRRR'), SYSDATE, 'N', ?, ?) RETURNING C_CTE_ID INTO ?; END;";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareCall(sql);
       st.setString(1, datos.getDNombre());
       st.setString(2, datos.getFConstitucion());
       st.setString(3, datos.getACreadoPorCte());
 
       st.setLong(4, datos.getCGerenciaId());
 
       st.registerOutParameter(5, 4);
 
       st.executeUpdate();
 
       idCte = st.getLong(5);
     }
     catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return idCte;
   }
 
   public OCAPGestionCtesOT buscarOCAPCte(long cCteId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     OCAPGestionCtesOT datos = null;
     try
     {
       String sql = "SELECT D_NOMBRE, to_char(F_CONSTITUCION,'DD/MM/YYYY'), C_GERENCIA_ID FROM OCAP_CTES WHERE C_CTE_ID = ? AND B_BORRADO = 'N'";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cCteId);
 
       rs = st.executeQuery();
 
       datos = new OCAPGestionCtesOT();
       if (rs.next()) {
         datos.setCCteId(cCteId);
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setFConstitucion(rs.getString(2));
         datos.setCGerenciaId(rs.getLong("C_GERENCIA_ID"));
       }
 
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
 
   public ArrayList consultaOCAPCtes()
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String sql = "SELECT D_NOMBRE,  C_CTE_ID FROM OCAP_CTES  WHERE B_BORRADO = 'N'  ORDER BY D_NOMBRE ";
 
       st = con.prepareStatement(sql);
 
       rs = st.executeQuery();
 
       listado = new ArrayList();
       OCAPGestionCtesOT datos = new OCAPGestionCtesOT();
 
       while (rs.next()) {
         datos = new OCAPGestionCtesOT();
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setCCteId(rs.getLong("C_CTE_ID"));
 
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
 
   public ArrayList consultaOCAPCtesAct()
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String sql = "SELECT distinct cte.C_CTE_ID,  D_NOMBRE FROM OCAP_CTES cte, OCAP_CTESACTIVADOS cac WHERE cte.B_BORRADO = 'N' and cte.C_CTE_ID = cac.C_CTE_ID and cac.B_BORRADO = 'N' and c_convocatoria_id in (select C_CONVOCATORIA_ID from OCAP_CONVOCATORIAS where sysdate > f_publicacion and sysdate < f_fincp and b_borrado = 'N')  ORDER BY D_NOMBRE";
 
       st = con.prepareStatement(sql);
 
       rs = st.executeQuery();
 
       listado = new ArrayList();
       OCAPGestionCtesOT datos = new OCAPGestionCtesOT();
 
       while (rs.next()) {
         datos = new OCAPGestionCtesOT();
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setCCteId(rs.getLong("C_CTE_ID"));
 
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
 
   public int bajaOCAPCtes(OCAPGestionCtesOT datos)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       this.loggerBD.info(" bajaOCAPCtes: " + datos.getCCteId());
       String sql = "UPDATE OCAP_CTES SET B_BORRADO = 'Y', F_USUMODI = SYSDATE, C_USUMODI = ? WHERE C_CTE_ID =  ?";
 
       st = con.prepareStatement(sql);
 
       st.setString(1, datos.getAModificadoPorCte());
       st.setLong(2, datos.getCCteId());
 
       filas = st.executeUpdate();
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return filas;
   }
 
   public int modificacionOCAPCtes(OCAPGestionCtesOT datos)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     int filas = 0;
     try
     {
       this.loggerBD.info(" modificacionOCAPCtes: " + datos.getCCteId());
 
       String sql = "UPDATE OCAP_CTES  SET D_NOMBRE = ?,  F_CONSTITUCION = TO_DATE(?,'DD/MM/RRRR'),  F_USUMODI = SYSDATE,  C_USUMODI = ?  WHERE C_CTE_ID = ?";
 
       st = con.prepareStatement(sql);
 
       st.setString(1, datos.getDNombre());
       st.setString(2, datos.getFConstitucion());
       st.setString(3, datos.getAModificadoPorCac());
       st.setLong(4, datos.getCCteId());
 
       filas = st.executeUpdate();
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return filas;
   }
 }

