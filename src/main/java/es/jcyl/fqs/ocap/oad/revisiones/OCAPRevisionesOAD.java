 package es.jcyl.fqs.ocap.oad.revisiones;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.revisiones.OCAPRevisionesOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.CallableStatement;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import org.apache.log4j.Logger;
 
 public class OCAPRevisionesOAD
 {
   public Logger logger;
   public Logger loggerBD;
   private static OCAPRevisionesOAD instance;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public static OCAPRevisionesOAD getInstance() {
     if (instance == null) {
       instance = new OCAPRevisionesOAD();
     }
     return instance;
   }
 
   private OCAPRevisionesOAD() {
     $init$();
   }
 
   public long altaOCAPRevisiones(OCAPRevisionesOT datos)
     throws Exception
   {
     CallableStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     int filas = 0;
     long idReunion = 0L;
     try
     {
       String sql = "BEGIN INSERT INTO OCAP_REVISIONES (C_REVISION_ID, C_EXP_ID, C_COMPFQS_ID, F_USUALTA, C_USUALTA, B_BORRADO) VALUES (ocap_rev_id_seq.nextval, ?, ?, SYSDATE, ?, 'N') RETURNING C_REVISION_ID INTO ?; END;";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareCall(sql);
 
       int cont = 1;
 
       st.setLong(cont++, datos.getCExpId().longValue());
       st.setLong(cont++, datos.getCCompFqsId());
       st.setString(cont++, datos.getAModificadoPor());
 
       st.registerOutParameter(cont++, 4);
 
       filas = st.executeUpdate();
       idReunion = st.getLong(--cont);
 
       OCAPConfigApp.logger.info("filas: " + filas);
     }
     catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return idReunion;
   }
 
   public void bajaOCAPRevisiones(OCAPRevisionesOT datos)
     throws Exception
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     try
     {
       String sql = "UPDATE OCAP_REVISIONES  SET B_BORRADO  ='Y'  WHERE C_EXP_ID = ? ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, datos.getCExpId().longValue());
 
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
 
   public OCAPRevisionesOT buscarOCAPRevisiones(long cExpId)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = null;
     OCAPRevisionesOT datos = null;
     try
     {
       this.loggerBD.info("buscarOCAPRevisiones: Ini - C_EXP_ID= " + cExpId);
 
       con = JCYLGestionTransacciones.getConnection();
 
       String sql = "SELECT c_revision_id, c_exp_id, c_compfqs_id, B_AUDITORIA,  TO_DATE(F_INICIO_EVALUACION,'DD/MM/RRRR hh24:mi:ss') as F_INICIO_EVALUACION,  A_MOTIVOAUDITORIA, A_LUGARREALIZACION, TO_DATE(F_AUDITORIA,'DD/MM/RRRR') as F_AUDITORIA, A_SUPERIOR, A_HALLAZGOS,  B_CUMPLIMIENTO, B_CATEGORIZACION, A_RECOMENDACIONES, A_CONCLUSIONES, TO_DATE(F_INFORME,'DD/MM/RRRR') as F_INFORME, TO_CHAR(F_AUDITORIA_PROPUESTA,'DD/MM/RRRR hh24:mi:ss') as F_AUDITORIA_PROPUESTA,  B_AUTOEVALUACION, B_2EVALUACION, B_ANALISIS, A_RESULTADO  FROM OCAP_REVISIONES  WHERE B_BORRADO = 'N'     and  C_EXP_ID= ? ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId);
       rs = st.executeQuery();
       datos = new OCAPRevisionesOT();
       if (rs.next()) {
         datos.setCRevisionId(rs.getLong("c_revision_id"));
         datos.setCExpId(new Long(rs.getLong("c_exp_id")));
         datos.setCCompFqsId(rs.getLong("c_compfqs_id"));
         datos.setFInicioEval(rs.getDate("F_INICIO_EVALUACION"));
         datos.setBAuditoria(rs.getString("B_AUDITORIA"));
         datos.setAMotivoAuditoria(rs.getString("A_MOTIVOAUDITORIA"));
         datos.setALugarRealizacion(rs.getString("A_LUGARREALIZACION"));
         datos.setFAuditoria(rs.getDate("F_AUDITORIA"));
         datos.setASuperior(rs.getString("A_SUPERIOR"));
         datos.setAHallazgos(rs.getString("A_HALLAZGOS"));
         datos.setBCumplimiento(rs.getString("B_CUMPLIMIENTO"));
         datos.setBCategorizacion(rs.getString("B_CATEGORIZACION"));
         datos.setARecomendaciones(rs.getString("A_RECOMENDACIONES"));
         datos.setAConclusiones(rs.getString("A_CONCLUSIONES"));
         datos.setFInforme(rs.getDate("F_INFORME"));
         datos.setFAuditoriaPropuesta(DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_HHMM, rs.getString("F_AUDITORIA_PROPUESTA")));
         datos.setBAutoEvaluacion(rs.getString("B_AUTOEVALUACION"));
         datos.setB2Evaluacion(rs.getString("B_2EVALUACION"));
         datos.setBAnalisis(rs.getString("B_ANALISIS"));
         datos.setAResultados(rs.getString("A_RESULTADO"));
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
 
   public void modificaOCAPRevisiones(OCAPRevisionesOT datos)
     throws Exception
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     try
     {
       String sql = "UPDATE OCAP_REVISIONES  SET C_EXP_ID = ? ";
 
       if (datos.getFInicioEval() != null)
         sql = sql + ", F_INICIO_EVALUACION = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss') ";
       if (datos.getBAuditoria() != null)
         sql = sql + ", B_AUDITORIA = ? ";
       if (datos.getAMotivoAuditoria() != null)
         sql = sql + ", A_MOTIVOAUDITORIA = ? ";
       if (datos.getALugarRealizacion() != null)
         sql = sql + ", A_LUGARREALIZACION = ? ";
       if (datos.getFAuditoria() != null)
         sql = sql + ", F_AUDITORIA = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss') ";
       if (datos.getASuperior() != null)
         sql = sql + ", A_SUPERIOR = ? ";
       if (datos.getAHallazgos() != null)
         sql = sql + ", A_HALLAZGOS = ? ";
       if (datos.getBCumplimiento() != null)
         sql = sql + ", B_CUMPLIMIENTO = ? ";
       if (datos.getBCategorizacion() != null)
         sql = sql + ", B_CATEGORIZACION = ? ";
       if (datos.getARecomendaciones() != null)
         sql = sql + ", A_RECOMENDACIONES = ? ";
       if (datos.getAConclusiones() != null)
         sql = sql + ", A_CONCLUSIONES = ? ";
       if (datos.getFInforme() != null)
         sql = sql + ", F_INFORME = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss') ";
       if (datos.getFAuditoriaPropuesta() != null)
         sql = sql + ", F_AUDITORIA_PROPUESTA = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss') ";
       if (datos.getBAutoEvaluacion() != null)
         sql = sql + ", B_AUTOEVALUACION = ? ";
       if (datos.getB2Evaluacion() != null)
         sql = sql + ", B_2EVALUACION = ? ";
       if (datos.getBAnalisis() != null)
         sql = sql + ", B_ANALISIS = ? ";
       if (datos.getAResultados() != null)
         sql = sql + ", A_RESULTADO = ? ";
       sql = sql + " WHERE C_EXP_ID = ? AND B_BORRADO='N'";
 
       st = con.prepareStatement(sql);
       int cont = 1;
       st.setLong(cont++, datos.getCExpId().longValue());
       if (datos.getFInicioEval() != null)
         st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInicioEval()));
       if (datos.getBAuditoria() != null)
         st.setString(cont++, datos.getBAuditoria());
       if (datos.getAMotivoAuditoria() != null)
         st.setString(cont++, datos.getAMotivoAuditoria());
       if (datos.getALugarRealizacion() != null)
         st.setString(cont++, datos.getALugarRealizacion());
       if (datos.getFAuditoria() != null)
         st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFAuditoria()));
       if (datos.getASuperior() != null)
         st.setString(cont++, datos.getASuperior());
       if (datos.getAHallazgos() != null)
         st.setString(cont++, datos.getAHallazgos());
       if (datos.getBCumplimiento() != null)
         st.setString(cont++, datos.getBCumplimiento());
       if (datos.getBCategorizacion() != null)
         st.setString(cont++, datos.getBCategorizacion());
       if (datos.getARecomendaciones() != null)
         st.setString(cont++, datos.getARecomendaciones());
       if (datos.getAConclusiones() != null)
         st.setString(cont++, datos.getAConclusiones());
       if (datos.getFInforme() != null)
         st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInforme()));
       if (datos.getFAuditoriaPropuesta() != null)
         st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFAuditoriaPropuesta()));
       if (datos.getBAutoEvaluacion() != null)
         st.setString(cont++, datos.getBAutoEvaluacion());
       if (datos.getB2Evaluacion() != null)
         st.setString(cont++, datos.getB2Evaluacion());
       if (datos.getBAnalisis() != null)
         st.setString(cont++, datos.getBAnalisis());
       if (datos.getAResultados() != null)
         st.setString(cont++, datos.getAResultados());
       st.setLong(cont++, datos.getCExpId().longValue());
 
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
 }

