 package es.jcyl.fqs.ocap.oad.estadosCuestionario;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;
 
 public class OCAPEstadosCuestionarioOAD
 {
   public static Logger logger = OCAPConfigApp.logger;
   private static OCAPEstadosCuestionarioOAD instance;
 
   public static OCAPEstadosCuestionarioOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPEstadosCuestionarioOAD();
     }
     return instance;
   }
 
   public void borraOCAPEstadosCuestionario(long cExpId, long cCuestionarioId, int idRepeticion, long idPadreEvidencia)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     try
     {
       String sql = "DELETE OCAP_ESTADOSCUESTIONARIO WHERE C_EXP_ID = ? AND C_CUESTIONARIO_ID = ? AND N_REPETICION = ? ";
 
       if (idPadreEvidencia != 0L) sql = sql + " AND C_PADRE_EVIDENCIA_ID = ? ";
 
       OCAPConfigApp.logger.info("Sentencia SQL: " + sql);
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId);
       st.setLong(2, cCuestionarioId);
       st.setInt(3, idRepeticion);
       if (idPadreEvidencia != 0L) st.setLong(4, idPadreEvidencia);
       st.executeUpdate();
     }
     catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
   }
 
   public void borraOCAPEstadosCuestionarioExpediente(long cExpId)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     try
     {
       String sql = "DELETE OCAP_ESTADOSCUESTIONARIO WHERE C_EXP_ID=? ";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId);
       st.executeUpdate();
     }
     catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
   }
 
   public int altaOCAPEstadosCuestionario(long cExpId, long cCuestionarioId, int idRepeticion, String cEstado, String cEstadoPonderacion, String usuAlta, long idPadreEvidencia)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     int filas = 0;
     try
     {
       String sql = "INSERT INTO OCAP_ESTADOSCUESTIONARIO (C_ESTCUES_ID, B_BORRADO, C_EXP_ID, C_CUESTIONARIO_ID, N_REPETICION, C_ESTADO, C_ESTADO_PONDERACION, F_USUALTA, C_USUALTA, C_PADRE_EVIDENCIA_ID) VALUES (OCAP_ESC_ID_SEQ.nextval, 'N', ?, ?, ?, ?, ?, SYSDATE, ?, ?)";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId);
       st.setLong(2, cCuestionarioId);
       st.setInt(3, idRepeticion);
       st.setString(4, cEstado);
       if (idPadreEvidencia != 0L)
         st.setString(5, "F");
       else
         st.setString(5, cEstadoPonderacion);
       st.setString(6, usuAlta);
       if (idPadreEvidencia != 0L)
         st.setLong(7, idPadreEvidencia);
       else
         st.setNull(7, 2);
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
 
   public String buscarEstadoCuestionario(long cExpId, long cCuestionarioId, int idRepeticion, long idPadreEvidencia)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     ResultSet rs = null;
     String estado = "";
     try
     {
       String sql = " SELECT C_ESTADO  FROM OCAP_ESTADOSCUESTIONARIO  WHERE C_EXP_ID = ?  AND C_CUESTIONARIO_ID = ?  AND N_REPETICION = ?  AND B_BORRADO = 'N' ";
 
       if (idPadreEvidencia != 0L) {
         sql = sql + " AND C_PADRE_EVIDENCIA_ID = ? ";
       }
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId);
       st.setLong(2, cCuestionarioId);
       st.setInt(3, idRepeticion);
       if (idPadreEvidencia != 0L)
         st.setLong(4, idPadreEvidencia);
       rs = st.executeQuery();
       if (rs.next())
         estado = rs.getString("C_ESTADO");
     }
     catch (SQLException ex)
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
 
     return estado;
   }
 
   public String buscarEstadoSimulacion(long cExpId, long cCuestionarioId, int idRepeticion)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     ResultSet rs = null;
     String estado = "";
     try
     {
       String sql = " SELECT C_ESTADO_SIMULACION  FROM OCAP_ESTADOSCUESTIONARIO WHERE C_EXP_ID = ? AND C_CUESTIONARIO_ID = ? AND N_REPETICION = ? AND B_BORRADO = 'N' ";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId);
       st.setLong(2, cCuestionarioId);
       st.setInt(3, idRepeticion);
       rs = st.executeQuery();
       if (rs.next())
         estado = rs.getString("C_ESTADO_SIMULACION") == null ? "" : rs.getString("C_ESTADO_SIMULACION");
     }
     catch (SQLException ex)
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
 
     return estado;
   }
 
   public String buscarEstadoPonderacion(long cExpId, long cCuestionarioId, int idRepeticion)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     ResultSet rs = null;
     String estado = "";
     try
     {
       String sql = " SELECT C_ESTADO_PONDERACION  FROM OCAP_ESTADOSCUESTIONARIO  WHERE C_EXP_ID = ?  AND C_CUESTIONARIO_ID = ?  AND N_REPETICION = ?  AND B_BORRADO = 'N' ";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId);
       st.setLong(2, cCuestionarioId);
       st.setInt(3, idRepeticion);
       rs = st.executeQuery();
       if (rs.next())
         estado = rs.getString("C_ESTADO_PONDERACION") == null ? "" : rs.getString("C_ESTADO_PONDERACION");
     }
     catch (SQLException ex)
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
 
     return estado;
   }
 
   public int modificacionOCAPEstadosCuestionario(long cExpId, long cCuestionarioId, int idRepeticion, String cEstado, String usuModi)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_ESTADOSCUESTIONARIO SET C_ESTADO_PONDERACION = ?, F_USUMODI = SYSDATE, C_USUMODI = ? WHERE C_EXP_ID = ? AND C_CUESTIONARIO_ID = ? AND N_REPETICION = ? ";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
       st.setString(1, cEstado);
       st.setString(2, usuModi);
       st.setLong(3, cExpId);
       st.setLong(4, cCuestionarioId);
       st.setInt(5, idRepeticion);
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
 
   public int modificarOCAPEstadoSimulacion(long cExpId, long cCuestionarioId, int idRepeticion, String cEstado, String usuModi)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_ESTADOSCUESTIONARIO SET C_ESTADO_SIMULACION = ?, F_USUMODI = SYSDATE, C_USUMODI = ? WHERE C_EXP_ID = ? AND C_CUESTIONARIO_ID = ? AND N_REPETICION = ? ";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
       st.setString(1, cEstado);
       st.setString(2, usuModi);
       st.setLong(3, cExpId);
       st.setLong(4, cCuestionarioId);
       st.setInt(5, idRepeticion);
       filas = st.executeUpdate();
     }
     catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(true);
     }
 
     return filas;
   }
 
   public int contarCuestionariosFinalizadosArea(long cExpId, long cAreaId)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     ResultSet rs = null;
     int contador = 0;
     try
     {
       String sql = " SELECT count(*) as numFinalizados  FROM ocap_estadoscuestionario  WHERE c_exp_id = ?  AND c_estado = 'F'  AND b_borrado = 'N'  AND c_cuestionario_id IN (SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_area_id = ? AND b_borrado ='N') ";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId);
       st.setLong(2, cAreaId);
       rs = st.executeQuery();
       if (rs.next())
         contador = rs.getInt("numFinalizados");
     }
     catch (SQLException ex)
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
 
     return contador;
   }
 
   public int borrarCuestionariosExp(long cExpId, long cCuestionarioId, String usuModi)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_ESTADOSCUESTIONARIO SET C_ESTADO = ?, B_BORRADO = ?, F_USUMODI = SYSDATE, C_USUMODI = ? WHERE C_EXP_ID = ? AND C_CUESTIONARIO_ID = ? ";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
       st.setString(1, Constantes.NO);
       st.setString(2, Constantes.SI);
       st.setString(3, usuModi);
       st.setLong(4, cExpId);
       st.setLong(5, cCuestionarioId);
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
   
   public int actualizarDesbloqueoF3(long cExpId)
		     throws SQLException
		   {
		     PreparedStatement st = null;
		     Connection con = JCYLGestionTransacciones.getConnection();
		     int filas = 0;
		     try
		     {
		       String sql = "UPDATE OCAP_ESTADOSCUESTIONARIO "
		       		+ " set c_estado='N',C_ESTADO_PONDERACION=NULL "  
		       		+ " where b_borrado='N' AND ( C_ESTADO='F' OR C_ESTADO_PONDERACION IS NOT NULL) "  
		       		+ "  and c_exp_id=?" ;
		 
		       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
		       st = con.prepareStatement(sql);
		       st.setLong(1, cExpId);
		       filas = st.executeUpdate();
		     }
		     catch (SQLException ex) {
		       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
		       throw ex;
		     } finally {
		       if (st != null)
		         st.close();
		       JCYLGestionTransacciones.close(true);
		     }
		 
		     return filas;
		   }
		 
 }

