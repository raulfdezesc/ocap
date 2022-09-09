 package es.jcyl.fqs.ocap.oad.actas;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.actas.OCAPMiembrosComitesOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.CallableStatement;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPMiembrosComitesOAD
 {
   public static Logger logger = OCAPConfigApp.logger;
   public Logger loggerBD;
   private static OCAPMiembrosComitesOAD instance;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public static OCAPMiembrosComitesOAD getInstance() {
     if (instance == null) {
       instance = new OCAPMiembrosComitesOAD();
     }
     return instance;
   }
 
   private OCAPMiembrosComitesOAD() {
     $init$();
   }
 
   public int altaOCAPMiembrosComites(OCAPMiembrosComitesOT datos)
     throws SQLException
   {
     Connection con = JCYLGestionTransacciones.getConnection();
     CallableStatement st = null;
     StringBuffer sql = new StringBuffer();
     int filas = 0;
     try
     {
       sql.append(" INSERT INTO OCAP_MIEMBROS_COMITES(C_MIEMBRO_ID, C_CONVOCATORIA_ID, C_GERENCIA_ID, C_PROFESIONAL_ID, ").append(" B_BORRADO, C_SEXO, C_TIPO, C_USUALTA, C_USUMODI, D_APELLIDOS, D_NOMBRE, F_USUALTA, F_USUMODI) ").append(" VALUES (OCAP_MIE_ID_SEQ.nextval, ?, ?, ?, 'N', ?, ?, ?, ?, ?, ?, SYSDATE, ?) ");
 
       st = con.prepareCall(sql.toString());
       st.setLong(1, datos.getCConvocatoria());
 
       if (datos.getDRol().equals(Constantes.OCAP_CC)) {
         st.setString(2, null);
       }
       else if (!datos.getDRol().equals(Constantes.OCAP_CC)) {
         st.setLong(2, datos.getCGerenciaId());
       }
       st.setLong(3, datos.getCProfesionalId());
       st.setString(4, datos.getCSexo());
       st.setString(5, datos.getCTipo());
       st.setString(6, datos.getCUsuAlta());
       st.setString(7, datos.getCUsuModi());
       st.setString(8, datos.getDApellidos());
       st.setString(9, datos.getDNombre());
       st.setString(10, null);
 
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
 
   public ArrayList buscarMiembros(OCAPMiembrosComitesOT miembrosOT)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     OCAPMiembrosComitesOT datos = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     ArrayList listado = null;
     try {
       StringBuffer sql = new StringBuffer();
 
       sql.append("SELECT c_miembro_id, d_nombre, d_apellidos, c_sexo, c_tipo, c_convocatoria_id, c_gerencia_id, c_profesional_id ").append(" FROM ocap_miembros_comites ").append(" WHERE b_borrado='N'");
 
       if ((miembrosOT.getCTipo() != null) && (!"".equals(miembrosOT.getCTipo())))
         sql.append(" AND c_tipo = ? ");
       if (miembrosOT.getCConvocatoria() != 0L)
         sql.append(" AND c_convocatoria_id = ? ");
       if (miembrosOT.getCProfesionalId() != 0L)
         sql.append(" AND c_profesional_id = ? ");
       if (miembrosOT.getCGerenciaId() != 0L)
         sql.append(" AND c_gerencia_id = ? ");
       else
         sql.append(" AND c_gerencia_id is null ");
       sql.append(" ORDER BY d_apellidos, d_nombre ");
 
       st = con.prepareStatement(sql.toString(), 1004, 1008);
 
       int cont = 1;
       if ((miembrosOT.getCTipo() != null) && (!"".equals(miembrosOT.getCTipo())))
         st.setString(cont++, miembrosOT.getCTipo());
       if (miembrosOT.getCConvocatoria() != 0L)
         st.setLong(cont++, miembrosOT.getCConvocatoria());
       if (miembrosOT.getCProfesionalId() != 0L)
         st.setLong(cont++, miembrosOT.getCProfesionalId());
       if (miembrosOT.getCGerenciaId() != 0L) {
         st.setLong(cont++, miembrosOT.getCGerenciaId());
       }
       rs = st.executeQuery();
 
       listado = new ArrayList();
       while (rs.next()) {
         datos = new OCAPMiembrosComitesOT();
         datos.setCMiembroId(rs.getLong("c_miembro_id"));
         datos.setDNombre(rs.getString("d_nombre"));
         datos.setDApellidos(rs.getString("d_apellidos"));
         datos.setCSexo(rs.getString("c_sexo"));
         datos.setCTipo(rs.getString("c_tipo"));
         datos.setCConvocatoria(rs.getLong("c_convocatoria_id"));
         datos.setCGerenciaId(rs.getLong("c_gerencia_id"));
         datos.setCProfesionalId(rs.getLong("c_profesional_id"));
 
         listado.add(datos);
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
 
     return listado;
   }
 
   public int bajaOCAPMiembrosComites(int cMiembroId)
     throws SQLException
   {
     PreparedStatement st = null;
     StringBuffer sql = new StringBuffer();
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       sql.append(" UPDATE OCAP_MIEMBROS_COMITES SET b_borrado = 'Y'").append(" WHERE C_MIEMBRO_ID = ? ");
 
       st = con.prepareStatement(sql.toString());
       st.setLong(1, cMiembroId);
 
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
 
   public int contarMiembros(OCAPMiembrosComitesOT miembrosComitesOT, JCYLUsuario jcylUsuario)
     throws Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " contarMiembros: Inicio");
 
     PreparedStatement st = null;
     Connection con = null;
 
     StringBuffer consulta = new StringBuffer();
 
     ResultSet rs = null;
     ArrayList listado = null;
     int numMiembros = 0;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
 
       consulta.append("SELECT COUNT(c_miembro_id) numero ").append(" FROM ocap_miembros_comites ").append(" WHERE b_borrado='N'");
 
       if ((miembrosComitesOT.getCTipo() != null) && (!"".equals(miembrosComitesOT.getCTipo())))
         consulta.append(" AND c_tipo = ? ");
       if (miembrosComitesOT.getCConvocatoria() != 0L)
         consulta.append(" AND c_convocatoria_id = ? ");
       if (miembrosComitesOT.getCProfesionalId() != 0L)
         consulta.append(" AND c_profesional_id = ? ");
       if (miembrosComitesOT.getCGerenciaId() != 0L)
         consulta.append(" AND c_gerencia_id = ? ");
       else
         consulta.append(" AND c_gerencia_id is null ");
       consulta.append(" ORDER BY d_apellidos, d_nombre ");
 
       st = con.prepareStatement(consulta.toString(), 1004, 1008);
 
       int cont = 1;
       if ((miembrosComitesOT.getCTipo() != null) && (!"".equals(miembrosComitesOT.getCTipo())))
         st.setString(cont++, miembrosComitesOT.getCTipo());
       if (miembrosComitesOT.getCConvocatoria() != 0L)
         st.setLong(cont++, miembrosComitesOT.getCConvocatoria());
       if (miembrosComitesOT.getCProfesionalId() != 0L)
         st.setLong(cont++, miembrosComitesOT.getCProfesionalId());
       if (miembrosComitesOT.getCGerenciaId() != 0L) {
         st.setLong(cont++, miembrosComitesOT.getCGerenciaId());
       }
       rs = st.executeQuery();
 
       if (rs.next()) {
         numMiembros = rs.getInt("numero");
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " contarMiembros: Fin");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.info(getClass().getName() + " contarMiembros: ERROR: " + e.getMessage());
       throw new SQLException(e.getMessage() + "<br>Error al contar los miembros");
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return numMiembros;
   }
 
   public ArrayList buscar(int inicio, int cuantos, OCAPMiembrosComitesOT miembrosComitesOT, JCYLUsuario jcylUsuario)
     throws Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " buscar: Inicio");
 
     PreparedStatement sentencia = null;
     Connection con = null;
 
     StringBuffer consulta = new StringBuffer();
 
     ResultSet rs = null;
     ArrayList listado = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
 
       consulta.append("SELECT mico.c_miembro_id, mico.d_nombre, mico.d_apellidos, mico.c_sexo, mico.c_tipo, mico.c_convocatoria_id, mico.c_gerencia_id, mico.c_profesional_id, ").append(" cpro.d_nombre as dProfesional, conv.d_nombre as dConvocatoria ").append(" FROM ocap_miembros_comites mico, ocap_categ_profesionales cpro, ocap_convocatorias conv ").append(" WHERE mico.b_borrado='N' ").append(" AND mico.c_profesional_id = cpro.c_profesional_id ").append(" AND mico.c_convocatoria_id = conv.c_convocatoria_id ");
 
       if ((miembrosComitesOT.getCTipo() != null) && (!"".equals(miembrosComitesOT.getCTipo())))
         consulta.append(" AND c_tipo = '" + miembrosComitesOT.getCTipo() + "' ");
       if (miembrosComitesOT.getCConvocatoria() != 0L)
         consulta.append(" AND mico.c_convocatoria_id = '" + miembrosComitesOT.getCConvocatoria() + "' ");
       if (miembrosComitesOT.getCProfesionalId() != 0L)
         consulta.append(" AND mico.c_profesional_id = '" + miembrosComitesOT.getCProfesionalId() + "' ");
       if (miembrosComitesOT.getCGerenciaId() != 0L)
         consulta.append(" AND c_gerencia_id = '" + miembrosComitesOT.getCGerenciaId() + "' ");
       else
         consulta.append(" AND c_gerencia_id is null ");
       consulta.append(" ORDER BY d_apellidos, d_nombre ");
 
       sentencia = con.prepareStatement(consulta.toString(), 1004, 1008);
       rs = sentencia.executeQuery();
 
       if (inicio > 1) {
         rs.absolute(inicio - 1);
       }
       listado = new ArrayList();
       String aux = "";
       int i = 0;
       while (rs.next()) {
         miembrosComitesOT = new OCAPMiembrosComitesOT();
         miembrosComitesOT.setCMiembroId(rs.getLong("c_miembro_id"));
         miembrosComitesOT.setDNombre(rs.getString("d_nombre"));
         miembrosComitesOT.setDApellidos(rs.getString("d_apellidos"));
         miembrosComitesOT.setCSexo(rs.getString("c_sexo"));
         miembrosComitesOT.setCTipo(rs.getString("c_tipo"));
         miembrosComitesOT.setCConvocatoria(rs.getLong("c_convocatoria_id"));
         miembrosComitesOT.setDConvocatoria(rs.getString("dConvocatoria"));
         miembrosComitesOT.setCGerenciaId(rs.getLong("c_gerencia_id"));
         miembrosComitesOT.setCProfesionalId(rs.getLong("c_profesional_id"));
         miembrosComitesOT.setDProfesional(rs.getString("dProfesional"));
 
         listado.add(miembrosComitesOT);
 
         if (++i == cuantos)
             break;
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscar: Fin");
     } catch (SQLException e) {
       OCAPConfigApp.logger.info(getClass().getName() + " buscar: ERROR: " + e.getMessage());
       throw new SQLException(e.getMessage() + "<br>Error al buscar los miembros");
     } finally {
       if (rs != null)
         rs.close();
       if (sentencia != null)
         sentencia.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 
   public OCAPMiembrosComitesOT buscarDatosMiembro(long cMiembroId)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     OCAPMiembrosComitesOT datos = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     try {
       StringBuffer sql = new StringBuffer();
 
       sql.append("SELECT d_nombre, d_apellidos, c_sexo, c_tipo, c_convocatoria_id, c_gerencia_id, c_profesional_id ").append(" FROM ocap_miembros_comites ").append(" WHERE c_miembro_id=? AND b_borrado='N' ");
 
       st = con.prepareStatement(sql.toString());
       st.setLong(1, cMiembroId);
 
       rs = st.executeQuery();
 
       if (rs.next()) {
         datos = new OCAPMiembrosComitesOT();
         datos.setCMiembroId(cMiembroId);
         datos.setDNombre(rs.getString("d_nombre"));
         datos.setDApellidos(rs.getString("d_apellidos"));
         datos.setCSexo(rs.getString("c_sexo"));
         datos.setCTipo(rs.getString("c_tipo"));
         datos.setCConvocatoria(rs.getLong("c_convocatoria_id"));
         datos.setCGerenciaId(rs.getLong("c_gerencia_id"));
         datos.setCProfesionalId(rs.getLong("c_profesional_id"));
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
 }

