 package es.jcyl.fqs.ocap.oad.ctesactivados;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.gestionCtes.OCAPGestionCtesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPCtesactivadosOAD
 {
   public Logger logger;
   public Logger loggerBD;
   private static OCAPCtesactivadosOAD instance;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public static OCAPCtesactivadosOAD getInstance() {
     if (instance == null) {
       instance = new OCAPCtesactivadosOAD();
     }
     return instance;
   }
 
   private OCAPCtesactivadosOAD() {
     $init$();
   }
 
   public int activaOCAPCtes(OCAPGestionCtesOT datos)
     throws Exception
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       this.loggerBD.info(" activarConvocatoria: ");
       String sql = "INSERT INTO OCAP_CTESACTIVADOS (C_CTEACTIVADO_ID, C_CTE_ID, C_CONVOCATORIA_ID,F_USUALTA,B_BORRADO, C_USUALTA) VALUES (OCAP_CAC_ID_SEQ.nextval, ?, ?, SYSDATE, 'N', ?)";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
       st.setLong(1, datos.getCCteId());
 
       if (datos.getCConvocatoriaId() != 0L)
         st.setLong(2, datos.getCConvocatoriaId());
       else {
         st.setNull(2, 2);
       }
       st.setString(3, datos.getACreadoPorCac());
 
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
 
   public int modificacionOCAPCtesactivados(OCAPGestionCtesOT datos)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     int filas = 0;
     try
     {
       this.loggerBD.info("Inicio");
 
       String sql = "UPDATE OCAP_CTESACTIVADOS SET C_CONVOCATORIA_ID = ?, F_USUMODI = SYSDATE, C_USUMODI = ? WHERE C_CTE_ID = ?";
 
       st = con.prepareStatement(sql);
 
       if (datos.getCConvocatoriaId() != 0L)
         st.setLong(1, datos.getCConvocatoriaId());
       else {
         st.setNull(1, 2);
       }
       st.setString(2, datos.getAModificadoPorCac());
       st.setLong(3, datos.getCCteId());
 
       filas = st.executeUpdate();
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       if (st != null) {
         st.close();
       }
 
     }
 
     return filas;
   }
 
   public int bajaOCAPCtesactivados(OCAPGestionCtesOT datos)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_CTESACTIVADOS SET B_BORRADO = 'Y', F_USUMODI = SYSDATE, C_USUMODI = ? WHERE C_CTE_ID =  ?";
 
       st = con.prepareStatement(sql);
 
       st.setString(1, datos.getAModificadoPorCac());
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
 
   public ArrayList buscarOCAPCteConv(long cCteId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String sql = "SELECT C_CONVOCATORIA_ID FROM OCAP_CTESACTIVADOS WHERE C_CTE_ID = ? AND B_BORRADO = 'N'AND C_CONVOCATORIA_ID IN(select C_CONVOCATORIA_ID from OCAP_CONVOCATORIAS where sysdate > f_publicacion and sysdate < f_fincp and b_borrado = 'N') ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cCteId);
 
       rs = st.executeQuery();
 
       listado = new ArrayList();
 
       OCAPGestionCtesOT datos = new OCAPGestionCtesOT();
 
       while (rs.next()) {
         datos = new OCAPGestionCtesOT();
         datos.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
 
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
 }

