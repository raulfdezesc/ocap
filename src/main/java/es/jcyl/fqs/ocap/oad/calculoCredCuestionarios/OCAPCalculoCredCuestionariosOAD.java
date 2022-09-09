 package es.jcyl.fqs.ocap.oad.calculoCredCuestionarios;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.calculoCredCuestionarios.OCAPCalculoCredCuestionariosOT;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPCalculoCredCuestionariosOAD
 {
   public static Logger logger = OCAPConfigApp.logger;
   private static OCAPCalculoCredCuestionariosOAD instance;
 
   public static OCAPCalculoCredCuestionariosOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPCalculoCredCuestionariosOAD();
     }
     return instance;
   }
 
   public ArrayList buscarOCAPCalculoCredCuestionario(long cCuestionarioId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
 
     OCAPCalculoCredCuestionariosOT datos = null;
     ArrayList listado = null;
     Connection con = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       String sql = "SELECT C_CALCULO_ID, C_CUESTIONARIO_ID, N_PUNTUACION_MIN, N_PUNTUACION_MAX, N_CREDITOS  FROM ITCP_CALCULO_CREDCUESTIONARIOS  WHERE C_CUESTIONARIO_ID = ? ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cCuestionarioId);
       rs = st.executeQuery();
       listado = new ArrayList();
       while (rs.next()) {
         datos = new OCAPCalculoCredCuestionariosOT();
         datos.setCCalculoId(rs.getLong("C_CALCULO_ID"));
         datos.setCCuestionarioId(rs.getLong("C_CUESTIONARIO_ID"));
         if ((rs.getString("N_CREDITOS") != null) && (!"".equals(rs.getString("N_CREDITOS"))))
           datos.setNCreditos(Float.valueOf(rs.getFloat("N_CREDITOS")));
         datos.setNPuntuacionMin(rs.getFloat("N_PUNTUACION_MIN"));
         datos.setNPuntuacionMax(rs.getFloat("N_PUNTUACION_MAX"));
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

