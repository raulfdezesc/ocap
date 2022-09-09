 package es.jcyl.fqs.ocap.oad.reuniones;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.reuniones.OCAPReunionesOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.Utilidades;

import java.sql.CallableStatement;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.SQLException;
 import org.apache.log4j.Logger;
 
 public class OCAPReunionesOAD
 {
   public Logger logger;
   private static OCAPReunionesOAD instance;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
   public static OCAPReunionesOAD getInstance() {
     if (instance == null) {
       instance = new OCAPReunionesOAD();
     }
     return instance;
   }
 
   private OCAPReunionesOAD() {
     $init$();
   }
 
   public long altaOCAPReuniones(OCAPReunionesOT datos, Connection con)
     throws Exception
   {
     CallableStatement st = null;
 
     int filas = 0;
     long idReunion = 0L;
     try
     {
       String sql = "BEGIN INSERT INTO OCAP_REUNIONES_CTE (C_REUNION_ID, C_CTE_ID, F_FECHA, D_ORDENDIA, D_DECISIONES, N_INFORMESTOTALES, F_USUALTA, C_USUALTA, B_BORRADO) VALUES (OCAP_REU_ID_SEQ.nextval, ?, TO_DATE(?,'DD/MM/RRRR hh24:mi'), ?, ?, ?, SYSDATE, ?, 'N') RETURNING C_REUNION_ID INTO ?; END;";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareCall(sql);
 
       int cont = 1;
 
       if (datos.getCCteId() != 0L)
         st.setLong(cont++, datos.getCCteId());
       else {
         st.setNull(cont++, 2);
       }
       st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_HHMM, datos.getFFecha()));
       st.setString(cont++, datos.getDOrdendia());
       st.setString(cont++, datos.getDDecisiones());
 
       if (datos.getNInformestotales() != 0L)
         st.setLong(cont++, datos.getNInformestotales());
       else {
         st.setNull(cont++, 2);
       }
       st.setString(cont++, datos.getACreadoPor());
 
       st.registerOutParameter(cont++, 4);
 
       filas = st.executeUpdate();
       idReunion = st.getLong(--cont);
 
       OCAPConfigApp.logger.info("filas: " + filas);
     }
     catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (st != null) {
         st.close();
       }
 
     }
 
     return idReunion;
   }
 
   public int altaOCAEvaluadoresTratados(OCAPReunionesOT datos, Connection con)
     throws Exception
   {
     PreparedStatement st = null;
 
     int filas = 0;
     try
     {
       String sql = "INSERT INTO OCAP_EVALUADORESTRATADOS (C_EVALUADOR_ID, C_REUNION_ID, N_EVALUACIONESEVALUADOR, N_INFORMESRECIBIDOS, N_INFORMESPOSITIVOS, N_INFORMESNEGATIVOS, F_USUALTA, C_USUALTA, B_BORRADO, C_EVALUADORTRATADO_ID) VALUES (?, ?,?,?,?,?,SYSDATE, ?, 'N', OCAP_EVT_ID_SEQ.nextval)";
 
       OCAPConfigApp.logger.info(getClass().getName() + " Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
       int cont = 1;
 
       st.setLong(cont++, datos.getCEvaluadorId());
       st.setLong(cont++, datos.getCReunionId());
 
       if (datos.getNEvaluacionesevaluador() != 0L)
         st.setLong(cont++, datos.getNEvaluacionesevaluador());
       else {
         st.setNull(cont++, 2);
       }
       if (datos.getNInformesrecibidos() != 0L)
         st.setLong(cont++, datos.getNInformesrecibidos());
       else {
         st.setNull(cont++, 2);
       }
       if (datos.getNInformespositivos() != 0L)
         st.setLong(cont++, datos.getNInformespositivos());
       else {
         st.setNull(cont++, 2);
       }
       if (datos.getNInformesnegativos() != 0L)
         st.setLong(cont++, datos.getNInformesnegativos());
       else {
         st.setNull(cont++, 2);
       }
       st.setString(cont++, datos.getACreadoPor());
 
       filas = st.executeUpdate();
     }
     catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     }
     finally {
       if (st != null) {
         st.close();
       }
     }
 
     return filas;
   }
 }

