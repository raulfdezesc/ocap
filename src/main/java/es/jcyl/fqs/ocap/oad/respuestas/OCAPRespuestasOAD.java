 package es.jcyl.fqs.ocap.oad.respuestas;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ln.expedientesCAs.OCAPExpedientesCAsLN;
 import es.jcyl.fqs.ocap.ln.respuestas.OCAPRespuestasLN;
 import es.jcyl.fqs.ocap.ot.expedientesCAs.OCAPExpedientesCAsOT;
 import es.jcyl.fqs.ocap.ot.respuestas.OCAPRespuestasOT;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPRespuestasOAD
 {
   public static Logger logger = OCAPConfigApp.logger;
   private static OCAPRespuestasOAD instance;
 
   public static OCAPRespuestasOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPRespuestasOAD();
     }
     return instance;
   }
 
   public ArrayList buscarOCAPRespuestasPorCuestionarioPregunta(long cCuestionId, long cPreguntaId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     ArrayList listado = null;
     OCAPRespuestasOT datos = null;
     OCAPExpedientesCAsOT expedienteCAOT = null;
     OCAPExpedientesCAsLN expedienteCALN = null;
     OCAPRespuestasLN respuestasLN = null;
     try {
       String sql = "SELECT C_RESPUESTA_ID, C_CUESTIONARIO_ID, C_PREGUNTA_ID, D_NOMBRE,  D_VALOR, N_CREDITOS, b_opcional, n_orden, C_SIG_CUESTIONARIO_ID  FROM ITCP_POSIBLES_RESPUESTAS  WHERE C_CUESTIONARIO_ID = '" + cCuestionId + "' " + " AND C_PREGUNTA_ID = '" + cPreguntaId + "' " + " ORDER BY N_ORDEN ";
 
       st = con.prepareStatement(sql);
       rs = st.executeQuery();
 
       listado = new ArrayList();
       while (rs.next()) {
         datos = new OCAPRespuestasOT();
         datos.setCRespuestaId(rs.getLong("C_RESPUESTA_ID"));
         datos.setCCuestionarioId(rs.getLong("C_CUESTIONARIO_ID"));
         datos.setCPreguntaId(rs.getLong("C_PREGUNTA_ID"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         if ((rs.getString("C_SIG_CUESTIONARIO_ID") != null) && (!"".equals(rs.getString("C_SIG_CUESTIONARIO_ID").trim())))
           datos.setDValor(rs.getString("C_SIG_CUESTIONARIO_ID"));
         else
           datos.setDValor(rs.getString("D_VALOR"));
         datos.setNCreditos(rs.getFloat("N_CREDITOS"));
         datos.setBOpcional(rs.getString("b_opcional"));
         listado.add(datos);
       }
     } catch (SQLException ex) {
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null) {
         st.close();
       }
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 }

