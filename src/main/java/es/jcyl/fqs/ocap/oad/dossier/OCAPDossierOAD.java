 package es.jcyl.fqs.ocap.oad.dossier;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.encuestaCalidad.OCAPEncuestaCalidadOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPDossierOAD
 {
   public Logger logger;
   private static OCAPDossierOAD instance;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
   public static OCAPDossierOAD getInstance() {
     if (instance == null) {
       instance = new OCAPDossierOAD();
     }
     return instance;
   }
 
   private OCAPDossierOAD() {
     $init$();
   }
 
   public ArrayList OCAPBuscaRespuestasDossier(long expId)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = null;
     OCAPEncuestaCalidadOT datos = null;
     ArrayList listado = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       String sql = "select c_exp_dossier_id, n_evidencia, b_entregado  from ocap_expedientes_dossiers where c_exp_id = ? and b_borrado ='N' order by c_exp_dossier_id ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, expId);
       rs = st.executeQuery();
       listado = new ArrayList();
       while (rs.next()) {
         datos = new OCAPEncuestaCalidadOT();
         datos.setCExpDossierId(rs.getLong("c_exp_dossier_id"));
         datos.setNEvidencia(rs.getLong("n_evidencia"));
         datos.setBEntregado(rs.getString("b_entregado"));
         listado.add(datos);
       }
     } catch (SQLException ex) {
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
 
   public void OCAPEliminaDossier(long expId)
     throws Exception
   {
     PreparedStatement st = null;
     int respuestas = 0;
     boolean bCrearConexion = false;
     Connection con = JCYLGestionTransacciones.getConnection();
     try {
       String sql = "delete from ocap_expedientes_dossiers where c_exp_id=? ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, expId);
 
       st.executeUpdate();
     } catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (st != null)
         st.close();
       if (bCrearConexion)
         JCYLGestionTransacciones.close(con.getAutoCommit());
     }
   }
 
   public int rellenaOCAPDossier(OCAPEncuestaCalidadOT datos)
     throws Exception
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "INSERT INTO OCAP_EXPEDIENTES_DOSSIERS (C_EXP_DOSSIER_ID, C_EXP_ID, N_EVIDENCIA, B_ENTREGADO, B_BORRADO, F_USUALTA, C_USUALTA) VALUES (OCAP_EDO_ID_SEQ.nextval, ?, ?, ?, 'N', SYSDATE,?)";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
 
       st.setLong(1, datos.getCExpId());
       st.setLong(2, datos.getNEvidencia());
       st.setString(3, datos.getBEntregado());
       st.setString(4, datos.getACreadoPor());
 
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
 }

