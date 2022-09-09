 package es.jcyl.fqs.ocap.oad.certificados;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.db.OCAPConexionDB;
 import es.jcyl.fqs.ocap.ot.certificados.OCAPCertificadosOT;
 import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPCertificadosOAD
 {
   public static Logger logger = OCAPConfigApp.logger;
   private static OCAPCertificadosOAD instance;
   protected Connection conTransaccion;
 
   public static OCAPCertificadosOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPCertificadosOAD();
     }
     return instance;
   }
 
   public void altaCertificados(Integer cExp, ArrayList certificados, String usuario, String docAsociado, JCYLUsuario jcylUsuario)
     throws SQLException
   {
     OCAPConfigApp.logger.info(getClass().getName() + " altaCertificados: Inicio");
 
     Connection con = null;
 
     if (this.conTransaccion == null)
       con = OCAPConexionDB.getDBConnection(null, jcylUsuario);
     else {
       con = this.conTransaccion;
     }
     PreparedStatement sentencia = null;
     StringBuffer consulta = new StringBuffer();
     int idExp = cExp.intValue();
     try
     {
       for (int i = 0; i < certificados.size(); i++) {
         consulta = new StringBuffer();
         OCAPCertificadosOT certificadosOT = (OCAPCertificadosOT)certificados.get(i);
         consulta.append("INSERT INTO OCAP_CERTIFICADOS (C_CERTIFICADO_ID, C_EXP_ID, D_TITULO, D_DESCRIPCION, F_USUALTA, C_USUALTA, B_BORRADO, C_DOC_ASOCIADO) VALUES (OCAP_CER_ID_SEQ.NEXTVAL, ?,?,?, SYSDATE, ?, 'N' , ?) ");
         sentencia = con.prepareStatement(consulta.toString());
 
         sentencia.setInt(1, idExp);
         sentencia.setString(2, certificadosOT.getDTitulo());
         sentencia.setString(3, certificadosOT.getDDescripcion());
         sentencia.setString(4, usuario);
         sentencia.setString(5, docAsociado);
 
         sentencia.executeUpdate();
       }
       OCAPConfigApp.logger.info(getClass().getName() + " altaCertificados: Fin");
     } catch (SQLException e) {
       OCAPConfigApp.logger.info(getClass().getName() + " altaCertificados: ERROR: " + e.getMessage());
       throw new SQLException(e.getMessage() + "<br>Error al insertar los certificados");
     } finally {
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
   }
 
   public ArrayList buscarOCAPCertificados(Long cExpId, Connection con)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     ArrayList listadoCertificados = new ArrayList();
     try
     {
       String sql = "SELECT C_CERTIFICADO_ID, C_EXP_ID, D_TITULO, D_DESCRIPCION, TO_CHAR(F_USUALTA, 'DD/MM/RRRR') F_USUALTA, F_MODICACION, B_BORRADO FROM OCAP_CERTIFICADOS WHERE C_EXP_ID = ? AND B_BORRADO = 'N' ORDER BY D_TITULO asc, F_USUALTA asc ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId.longValue());
 
       rs = st.executeQuery();
 
       while (rs.next()) {
         OCAPCertificadosOT datos = new OCAPCertificadosOT();
         datos.setCCertificado_id(rs.getLong("C_CERTIFICADO_ID"));
         datos.setCExp_id(rs.getLong("C_EXP_ID"));
         datos.setDTitulo(rs.getString("D_TITULO"));
         datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
         datos.setFCreacion(rs.getString("F_USUALTA"));
         datos.setFModicacion(rs.getDate("F_MODICACION"));
         datos.setBBorrado(rs.getString("B_BORRADO"));
 
         listadoCertificados.add(datos);
       }
     }
     catch (SQLException ex) {
       throw ex;
     } catch (Exception ex) {
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listadoCertificados;
   }
 
   public int bajaCertificados(long cExp, String modificado, JCYLUsuario jcylUsuario)
     throws SQLException
   {
     OCAPConfigApp.logger.info(getClass().getName() + " bajaCertificados: Inicio");
 
     PreparedStatement st = null;
     Connection con = null;
     int filas = 0;
 
     if (this.conTransaccion == null)
       con = OCAPConexionDB.getDBConnection(null, jcylUsuario);
     else {
       con = this.conTransaccion;
     }
     try
     {
       String sql = "UPDATE OCAP_CERTIFICADOS SET B_BORRADO = 'Y', C_USUMODI = ?, F_MODICACION = SYSDATE WHERE C_EXP_ID =  ?";
 
       st = con.prepareStatement(sql);
       st.setString(1, modificado);
       st.setLong(2, cExp);
 
       filas = st.executeUpdate();
 
       OCAPConfigApp.logger.info(getClass().getName() + " bajaCertificados: Fin");
     } catch (SQLException e) {
       OCAPConfigApp.logger.info(getClass().getName() + " bajaCertificados: ERROR: " + e.getMessage());
       throw new SQLException(e.getMessage() + "<br>Error al borrar los certificados");
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return filas;
   }
 
   public ArrayList buscarOCAPCertificadosDoc(long cExpId, String cDoc, Connection con)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     ArrayList listadoCertificados = new ArrayList();
     try
     {
       String sql = "SELECT D_TITULO, TO_CHAR(F_USUALTA, 'DD/MM/RRRR') F_USUALTA FROM OCAP_CERTIFICADOS WHERE C_EXP_ID = ? AND B_BORRADO = 'N' AND C_DOC_ASOCIADO = ? ORDER BY D_TITULO asc, F_USUALTA asc ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId);
       st.setString(2, cDoc);
 
       rs = st.executeQuery();
 
       while (rs.next()) {
         OCAPCertificadosOT datos = new OCAPCertificadosOT();
         datos.setDTitulo(rs.getString("D_TITULO"));
 
         listadoCertificados.add(datos);
       }
     }
     catch (SQLException ex) {
       throw ex;
     } catch (Exception ex) {
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listadoCertificados;
   }
 }

