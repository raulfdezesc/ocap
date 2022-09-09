 package es.jcyl.fqs.ocap.oad.subsanaciones;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.subsanaciones.OCAPSubsanacionesOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import org.apache.log4j.Logger;
 
 public class OCAPSubsanacionesOAD
 {
   public static Logger logger = OCAPConfigApp.logger;
   protected Connection conTransaccion;
   private static OCAPSubsanacionesOAD instance;
 
   public static OCAPSubsanacionesOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPSubsanacionesOAD();
     }
     return instance;
   }
 
   public void altaSubsanaciones(OCAPSubsanacionesOT datos)
     throws Exception
   {
     PreparedStatement st = null;
     int filas = 0;
     Connection con = JCYLGestionTransacciones.getConnection();
     try
     {
       String sql = " INSERT INTO OCAP_SUBSANACIONES (C_SUBSANACION_ID, C_SOLICITUD_ID, F_PETICION, B_BORRADO, A_PETICION, F_USUALTA, C_USUALTA )  VALUES (OCAP_SUB_ID_SEQ.NEXTVAL, ?, TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), 'N', ?, SYSDATE, ?) ";
 
       st = con.prepareStatement(sql);
       int cont = 1;
       st.setLong(cont++, datos.getCSolicitudId());
       st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFPeticion()));
       st.setString(cont++, datos.getAPeticion());
       st.setString(cont++, datos.getACreadoPor());
 
       filas = st.executeUpdate();
     }
     catch (Exception ex) {
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
   }
 
   public String buscaUltimaSubsanacion(long cSolicitudId)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     String observaciones = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     try
     {
       StringBuffer sql = new StringBuffer();
       sql.append(" select A_PETICION from OCAP_SUBSANACIONES ").append(" where B_BORRADO='N' AND C_SOLICITUD_ID = ? order by f_peticion desc ");
 
       st = con.prepareStatement(sql.toString(), 1004, 1008);
       int cont = 1;
       st.setLong(cont++, cSolicitudId);
       rs = st.executeQuery();
       if (rs.next())
         observaciones = rs.getString("A_PETICION");
     }
     catch (Exception ex)
     {
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return observaciones;
   }
 }

