 package es.jcyl.fqs.ocap.oad.expedientesexclusion;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.expedientesexclusion.OCAPExpedientesExclusionOT;
 import es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPExpedientesExclusionOAD
 {
   public static Logger logger = OCAPConfigApp.logger;
   private static OCAPExpedientesExclusionOAD instance;
 
   public static OCAPExpedientesExclusionOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPExpedientesExclusionOAD();
     }
     return instance;
   }
 
   public ArrayList buscarOCAPExpedientesExclusion(OCAPSolicitudesOT solicitudOT)
     throws SQLException
   {
     PreparedStatement sentencia = null;
     Connection con = null;
     StringBuffer consulta = new StringBuffer();
     ResultSet resultado = null;
     ArrayList listado = new ArrayList();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarOCAPExpedientesExclusion: Inicio");
 
       con = JCYLGestionTransacciones.getConnection();
       consulta.append("SELECT oee.c_exclusion_id, oee.d_otros_motivos, mex.d_descripcion, mex.d_nombre ").append(" FROM ocap_expedientesexclusion oee, ocap_mot_exclusion mex ").append(" WHERE mex.c_exclusion_id = oee.c_exclusion_id AND oee.c_exp_id = ? and oee.B_BORRADO = 'N' and mex.b_borrado = 'N' ");
 
       sentencia = con.prepareStatement(consulta.toString());
       sentencia.setLong(1, solicitudOT.getCExp_id());
       resultado = sentencia.executeQuery();
       OCAPExpedientesExclusionOT expedienteExclusionOT = null;
       while (resultado.next()) {
         expedienteExclusionOT = new OCAPExpedientesExclusionOT();
         expedienteExclusionOT.setCExclusionId(resultado.getLong("C_EXCLUSION_ID"));
         expedienteExclusionOT.setDOtrosMotivos(resultado.getString("D_OTROS_MOTIVOS"));
         expedienteExclusionOT.setDNombre(resultado.getString("D_NOMBRE"));
         expedienteExclusionOT.setDDescripcion(resultado.getString("D_DESCRIPCION"));
         listado.add(expedienteExclusionOT);
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarOCAPExpedientesExclusion: Fin");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw new SQLException(e.getMessage() + "<br>Error al buscar las buscarOCAPExpedientesExclusion");
     } finally {
       if (resultado != null)
         resultado.close();
       if (sentencia != null)
         sentencia.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 
   public void altaExpediente(OCAPExpedientesExclusionOT expediente)
     throws Exception
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     StringBuffer sql = new StringBuffer();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " altaExpediente: Inicio");
 
       sql.append("INSERT INTO OCAP_EXPEDIENTESEXCLUSION ").append("(C_EXPEXCLUSION_ID, C_EXP_ID, C_EXCLUSION_ID, D_OTROS_MOTIVOS, B_BORRADO, F_USUALTA, C_USUALTA) ").append("VALUES (OCAP_EEX_ID_SEQ.NEXTVAL, ?, ?, ?, 'N', SYSDATE, ?)");
 
       st = con.prepareStatement(sql.toString());
       int cont = 1;
       st.setLong(cont++, expediente.getCExpId());
       st.setLong(cont++, expediente.getCExclusionId());
       st.setString(cont++, expediente.getDOtrosMotivos());
       st.setString(cont++, expediente.getCUsualta());
       st.executeUpdate();
 
       OCAPConfigApp.logger.info(getClass().getName() + " altaExpediente: Fin");
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
   }
 
   public void altaMotivosExpediente(ArrayList motivosExclusion)
     throws Exception
   {
     PreparedStatement st = null;
     Connection con = null;
     int cont = 0;
     StringBuffer sql = new StringBuffer();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " altaMotivosExpediente: Inicio");
 
       if ((motivosExclusion != null) && (motivosExclusion.size() > 0))
       {
         con = JCYLGestionTransacciones.getConnection();
         sql.append("INSERT INTO OCAP_EXPEDIENTESEXCLUSION (C_EXPEXCLUSION_ID, C_EXP_ID, C_EXCLUSION_ID, D_OTROS_MOTIVOS, B_BORRADO, F_USUALTA, C_USUALTA) ").append("VALUES (OCAP_EEX_ID_SEQ.NEXTVAL, ?, ?, ?, 'N', SYSDATE, ?) ");
 
         st = con.prepareStatement(sql.toString());
         int numMotivos = motivosExclusion.size();
         for (int j = 0; j < numMotivos; j++) {
           OCAPExpedientesExclusionOT expediente = (OCAPExpedientesExclusionOT)motivosExclusion.get(j);
           cont = 1;
           st.setLong(cont++, expediente.getCExpId());
           st.setLong(cont++, expediente.getCExclusionId());
           st.setString(cont++, expediente.getDOtrosMotivos());
           st.setString(cont++, expediente.getCUsualta());
           st.executeUpdate();
         }
 
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " altaMotivosExpediente: Fin");
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
   }
 
   public void bajaExpediente(OCAPExpedientesExclusionOT expediente)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     StringBuffer sql = new StringBuffer();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " bajaExpediente: Inicio");
 
       sql.append("UPDATE OCAP_EXPEDIENTESEXCLUSION ").append("SET B_BORRADO = 'Y', F_USUMODI = SYSDATE, C_USUMODI = ? WHERE C_EXP_ID = ? ");
 
       st = con.prepareStatement(sql.toString());
       int cont = 1;
       st.setString(cont++, expediente.getCUsumodi());
       st.setLong(cont++, expediente.getCExpId());
       st.executeUpdate();
 
       OCAPConfigApp.logger.info(getClass().getName() + " bajaExpediente: Fin");
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
 
   public void borraExpediente(long expedienteId)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     StringBuffer sql = new StringBuffer();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " bajaExpediente: Inicio");
 
       sql.append("DELETE OCAP_EXPEDIENTESEXCLUSION WHERE C_EXP_ID = ? ");
       st = con.prepareStatement(sql.toString());
       st.setLong(1, expedienteId);
       st.executeUpdate();
 
       OCAPConfigApp.logger.info(getClass().getName() + " borraExpediente: Fin");
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

