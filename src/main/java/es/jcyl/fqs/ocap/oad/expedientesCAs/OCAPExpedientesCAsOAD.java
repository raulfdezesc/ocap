 package es.jcyl.fqs.ocap.oad.expedientesCAs;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.expedientesCAs.OCAPExpedientesCAsOT;
 import es.jcyl.fqs.ocap.util.Utilidades;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.lang.reflect.Method;
 import java.sql.Blob;
 import java.sql.CallableStatement;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPExpedientesCAsOAD
 {
   public static Logger logger = OCAPConfigApp.logger;
   private static OCAPExpedientesCAsOAD instance;
 
   public static OCAPExpedientesCAsOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPExpedientesCAsOAD();
     }
     return instance;
   }
 
   public long insertarOCAPExpedientesCAs(OCAPExpedientesCAsOT expCAOT)
     throws SQLException
   {
     CallableStatement st = null;
 
     Connection con = JCYLGestionTransacciones.getConnection();
 
     long idExpedienteCA = 0L;
     try
     {
       String sql = "BEGIN INSERT INTO OCAP_EXPEDIENTESCAS (C_EXPCA_ID, C_EXP_ID, C_CUESTIONARIO_ID, C_PREGUNTA_ID, N_REPETICION, D_VALOR, F_USUALTA, B_BORRADO, C_USUALTA, C_PADRE_EVIDENCIA_ID, A_RESPUESTAESCANEADA ) values ( OCAP_ECA_ID_SEQ.nextval, ?, ?, ? , ?, ?, SYSDATE, 'N', ?, ?, empty_blob())  RETURNING C_EXPCA_ID INTO ?; END;";
 
       st = con.prepareCall(sql);
       st.setLong(1, expCAOT.getCExpId());
       st.setLong(2, expCAOT.getCCuestionarioId());
       st.setLong(3, expCAOT.getCPreguntaId());
       st.setInt(4, expCAOT.getNRepeticion());
       st.setString(5, expCAOT.getDValor());
       st.setString(6, expCAOT.getACreadoPor());
       if (expCAOT.getCPadreEvidenciaId() != 0L)
         st.setLong(7, expCAOT.getCPadreEvidenciaId());
       else
         st.setNull(7, 2);
       st.registerOutParameter(8, 4);
       st.executeUpdate();
       idExpedienteCA = st.getInt(8);
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return idExpedienteCA;
   }
 
   public long insertarRepeticionesCuestionarios(long cExpCAId, long cCuestionarioId, int nRepeticion, String usuAlta)
     throws SQLException
   {
     CallableStatement st = null;
 
     Connection con = JCYLGestionTransacciones.getConnection();
 
     long idRepeticion = 0L;
     try
     {
       String sql = "BEGIN INSERT INTO OCAP_REPETICIONESCUESTIONARIOS (C_REPETICION_ID, C_EXPCA_ID, C_CUESTIONARIO_ID, N_REPETICION, C_USUALTA, F_USUALTA ) values ( OCAP_REP_ID_SEQ.nextval, '" + cExpCAId + "', '" + cCuestionarioId + "', '" + nRepeticion + "','" + usuAlta + "',SYSDATE) " + " RETURNING C_REPETICION_ID INTO ?; END;";
 
       st = con.prepareCall(sql);
       st.registerOutParameter(1, 4);
       st.executeUpdate();
       idRepeticion = st.getInt(1);
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return idRepeticion;
   }
 
   public ArrayList buscarOCAPExpedientesCAs(OCAPExpedientesCAsOT expCAOT, ArrayList listaRepeticiones, boolean bUsuario, boolean bSimulacion)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     ArrayList listado = null;
     OCAPExpedientesCAsOT datos = null;
     Blob blob = null;
 
     Connection con = JCYLGestionTransacciones.getConnection();
     try
     {
       String sql = "SELECT oe.C_CUESTIONARIO_ID, oe.C_PREGUNTA_ID, oe.C_EXPCA_ID, oe.C_EXP_ID, oe.N_REPETICION, oe.D_VALOR, oe.a_respuestaescaneada  FROM ocap_expedientescas oe  WHERE oe.B_BORRADO = 'N' AND oe.c_cuestionario_id = " + expCAOT.getCCuestionarioId() + " AND oe.c_EXP_ID = " + expCAOT.getCExpId() + " ";
 
       if (expCAOT.getNRepeticion() != 0)
         sql = sql + " AND oe.n_repeticion = " + expCAOT.getNRepeticion() + " ";
       if (expCAOT.getCPadreEvidenciaId() != 0L)
         sql = sql + " AND c_padre_evidencia_id = " + expCAOT.getCPadreEvidenciaId() + " ";
       if (bSimulacion) {
         if (bUsuario)
           sql = sql + " AND oe.C_USUALTA = (select c_dni from ocap_usuarios where c_usr_id in (select c_usr_id from ocap_expedientescarrera where c_exp_id=oe.c_exp_id)) ";
         else
           sql = sql + " AND oe.C_USUALTA != (select c_dni from ocap_usuarios where c_usr_id in (select c_usr_id from ocap_expedientescarrera where c_exp_id=oe.c_exp_id)) ";
       }
       if (expCAOT.getCPreguntaId() != 0L) {
         sql = sql + " AND oe.c_pregunta_id = " + expCAOT.getCPreguntaId() + " ";
       }
 
       sql = sql + " ORDER BY oe.C_EXPCA_ID ";
 
       st = con.prepareStatement(sql, 1004, 1008);
       rs = st.executeQuery();
       listado = new ArrayList();
       while (rs.next()) {
         datos = new OCAPExpedientesCAsOT();
         datos.setCCuestionarioId(rs.getLong("C_CUESTIONARIO_ID"));
         datos.setCPreguntaId(rs.getLong("C_PREGUNTA_ID"));
         datos.setCExpCaId(rs.getLong("C_EXPCA_ID"));
         datos.setCExpId(rs.getLong("C_EXP_ID"));
         datos.setNRepeticion(rs.getInt("N_REPETICION"));
         datos.setDValor(rs.getString("D_VALOR"));
         blob = rs.getBlob("a_respuestaescaneada");
         if ((blob != null) && (blob.length() != 0L))
           datos.setARespuestaescaneada(blob.getBinaryStream());
         listado.add(datos);
       }
     }
     catch (SQLException ex) {
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
 
   public void guardarDatosBlob(OCAPExpedientesCAsOT expCAOT)
     throws SQLException, Exception
   {
     PreparedStatement stmt = null;
     Connection con = null;
     ResultSet rs = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
 
       StringBuffer sql = new StringBuffer();
       sql.append("select a_respuestaescaneada from ocap_expedientescas where C_EXPCA_ID = ? for update ");
       stmt = con.prepareStatement(sql.toString());
       stmt.setLong(1, expCAOT.getCExpCaId());
       rs = stmt.executeQuery();
 
       if (rs.next()) {
         Blob blob = rs.getBlob("a_respuestaescaneada");
         Class blobClass = blob.getClass();
         Method methodGetBinaryOutputStream = blobClass.getMethod("getBinaryOutputStream", null);
         Method methodGetChunkSize = blobClass.getMethod("getChunkSize", null);
         OutputStream blobWrite = (OutputStream)methodGetBinaryOutputStream.invoke(blob, null);
         int chunkSizeBlobWrite = ((Integer)methodGetChunkSize.invoke(blob, null)).intValue();
         Utilidades utilidades = new Utilidades();
         utilidades.setOutputInput(expCAOT.getARespuestaescaneada(), chunkSizeBlobWrite, blobWrite);
       } else {
         throw new SQLException("Error al asociar el blob al expediente");
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " guardarBlob: Fin: ");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.info(getClass().getName() + " guardarBlob: ERROR: " + e.getMessage());
       throw e;
     } catch (Exception ex) {
       OCAPConfigApp.logger.info(getClass().getName() + " guardarBlob: ERROR: " + ex.getMessage());
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (stmt != null)
         stmt.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
     logger.info("Saliendo de guardarDatosBlob");
   }
 
   public InputStream buscarExpedientesCA(long cExpCaId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     InputStream datos = null;
     Blob blob = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     try
     {
       String sql = "SELECT a_respuestaescaneada FROM ocap_expedientescas WHERE C_EXPCA_ID = ? ";
 
       st = con.prepareStatement(sql, 1004, 1008);
       st.setLong(1, cExpCaId);
       rs = st.executeQuery();
 
       if (rs.next()) {
         blob = rs.getBlob("a_respuestaescaneada");
         if (blob != null)
           datos = blob.getBinaryStream();
       }
     }
     catch (SQLException ex) {
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

