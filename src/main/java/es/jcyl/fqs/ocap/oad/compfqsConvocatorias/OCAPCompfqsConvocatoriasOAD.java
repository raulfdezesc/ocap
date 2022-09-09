 package es.jcyl.fqs.ocap.oad.compfqsConvocatorias;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPCompfqsConvocatoriasOAD
 {
   public Logger logger;
   private static OCAPCompfqsConvocatoriasOAD instance;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
   public static OCAPCompfqsConvocatoriasOAD getInstance() {
     if (instance == null) {
       instance = new OCAPCompfqsConvocatoriasOAD();
     }
     return instance;
   }
 
   private OCAPCompfqsConvocatoriasOAD() {
     $init$();
   }
 
   public int altaOCAPCompfqsConvocatorias(OCAPComponentesfqsOT datos, boolean conCommit)
     throws Exception
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     int filas = 0;
     try
     {
       String sql = "INSERT INTO OCAP_COMPFQS_CONVOCATORIAS (C_COMPFQS_CONVO_ID, C_COMPFQS_ID, C_CONVOCATORIA_ID, C_CTE_ID, F_USUALTA,B_BORRADO,C_USUALTA, C_ITINERARIO_ID, C_PROFESIONAL_ID, C_ESPEC_ID, F_VINCULACION, F_FINALIZACION) VALUES (OCAP_FQC_ID_SEQ.nextval, ?, ?, ?, SYSDATE, 'N', ?, ?, ?, ?, TO_DATE(?,'DD/MM/RRRR'), TO_DATE(?,'DD/MM/RRRR'))";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
 
       int cont = 1;
       st.setLong(cont++, datos.getCCompfqsId());
 
       if (datos.getCConvocatoriaId() != 0L)
         st.setLong(cont++, datos.getCConvocatoriaId());
       else {
         st.setNull(cont++, 2);
       }
       if (datos.getCCteId() != 0L)
         st.setLong(cont++, datos.getCCteId());
       else {
         st.setNull(cont++, 2);
       }
       st.setString(cont++, datos.getACreadoPorExp());
 
       if (datos.getCItinerarioId() != 0L)
         st.setLong(cont++, datos.getCItinerarioId());
       else {
         st.setNull(cont++, 2);
       }
       if (datos.getCProfesionalId() != 0L)
         st.setLong(cont++, datos.getCProfesionalId());
       else {
         st.setNull(cont++, 2);
       }
       if (datos.getCEspecId() != 0L)
         st.setLong(cont++, datos.getCEspecId());
       else {
         st.setNull(cont++, 2);
       }
       if (datos.getFVinculacion() != null)
         st.setString(cont++, datos.getFVinculacion());
       else {
         st.setNull(cont++, 12);
       }
 
       if (datos.getFFinalizacion() != null)
         st.setString(cont++, datos.getFFinalizacion());
       else {
         st.setNull(cont++, 12);
       }
 
       filas = st.executeUpdate();
     }
     catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (conCommit) {
         JCYLGestionTransacciones.close(con.getAutoCommit());
       }
 
     }
 
     return filas;
   }
 
   public ArrayList buscarOCAPConvAnt(long cCompfqsId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String sql = "SELECT C_CONVOCATORIA_ID, C_CTE_ID, C_ITINERARIO_ID, C_PROFESIONAL_ID, C_ESPEC_ID FROM OCAP_COMPFQS_CONVOCATORIAS WHERE C_COMPFQS_ID = ? AND B_BORRADO = 'N' AND C_CONVOCATORIA_ID is not null AND C_CONVOCATORIA_ID in( select C_CONVOCATORIA_ID from OCAP_CONVOCATORIAS where f_fincp < sysdate and b_borrado = 'N') ORDER BY C_CONVOCATORIA_ID ASC, C_ITINERARIO_ID ASC, C_PROFESIONAL_ID ASC, C_ESPEC_ID ASC";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cCompfqsId);
 
       rs = st.executeQuery();
 
       listado = new ArrayList();
 
       while (rs.next()) {
         OCAPComponentesfqsOT datos = new OCAPComponentesfqsOT();
         datos.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
         datos.setCCteId(rs.getLong("C_CTE_ID"));
         datos.setCItinerarioId(rs.getLong("C_ITINERARIO_ID"));
         datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
         datos.setCEspecId(rs.getLong("C_ESPEC_ID"));
 
         listado.add(datos);
       }
     }
     catch (SQLException ex)
     {
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
 
   public ArrayList buscarOCAPConvocatoriasAct(long cCompfqsId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String sql = "SELECT C_CONVOCATORIA_ID, C_CTE_ID, C_ITINERARIO_ID, C_PROFESIONAL_ID, C_ESPEC_ID, C_COMPFQS_CONVO_ID FROM OCAP_COMPFQS_CONVOCATORIAS WHERE C_COMPFQS_ID = ? AND B_BORRADO = 'N' AND C_CONVOCATORIA_ID in( select C_CONVOCATORIA_ID from OCAP_CONVOCATORIAS where sysdate > f_publicacion and sysdate < f_fincp and b_borrado = 'N') ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cCompfqsId);
 
       rs = st.executeQuery();
 
       listado = new ArrayList();
 
       while (rs.next()) {
         OCAPComponentesfqsOT datos = new OCAPComponentesfqsOT();
         datos.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
         datos.setCCteId(rs.getLong("C_CTE_ID"));
         datos.setCItinerarioId(rs.getLong("C_ITINERARIO_ID"));
         datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
         datos.setCEspecId(rs.getLong("C_ESPEC_ID"));
         datos.setCCompfqsConvoId(rs.getLong("C_COMPFQS_CONVO_ID"));
 
         listado.add(datos);
       }
     }
     catch (SQLException ex)
     {
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
 
   public OCAPComponentesfqsOT buscarOCAPComponentescte(long cCompfqsId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     OCAPComponentesfqsOT datos = null;
     try
     {
       String sql = "SELECT C_CTE_ID, C_COMPFQS_CONVO_ID, to_char(F_VINCULACION,'DD/MM/YYYY'), to_char(F_FINALIZACION,'DD/MM/YYYY') FROM OCAP_COMPFQS_CONVOCATORIAS WHERE C_COMPFQS_ID = ? AND B_BORRADO = 'N' ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cCompfqsId);
 
       rs = st.executeQuery();
 
       datos = new OCAPComponentesfqsOT();
 
       if (rs.next()) {
         datos.setCCompfqsConvoId(rs.getLong("C_COMPFQS_CONVO_ID"));
         datos.setCCteId(rs.getLong("C_CTE_ID"));
         datos.setFVinculacion(rs.getString(3));
         datos.setFFinalizacion(rs.getString(4));
       }
     }
     catch (SQLException ex)
     {
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
 
   public OCAPComponentesfqsOT buscarOCAPConvEvaluador(long cConvoId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     OCAPComponentesfqsOT dato = null;
     try
     {
       String sql = "SELECT C_CONVOCATORIA_ID, C_CTE_ID, C_ITINERARIO_ID, C_PROFESIONAL_ID, C_ESPEC_ID, C_COMPFQS_ID, c_grado_id FROM OCAP_COMPFQS_CONVOCATORIAS comp, ITCP_MANUALES_EVALUACION man WHERE comp.C_COMPFQS_CONVO_ID = ?  AND comp.c_itinerario_id = man.c_manual_evaluacion_id AND comp.B_BORRADO = 'N' ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cConvoId);
 
       rs = st.executeQuery();
 
       dato = new OCAPComponentesfqsOT();
 
       if (rs.next()) {
         dato.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
         dato.setCCteId(rs.getLong("C_CTE_ID"));
         dato.setCItinerarioId(rs.getLong("C_ITINERARIO_ID"));
         dato.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
         dato.setCEspecId(rs.getLong("C_ESPEC_ID"));
         dato.setCCompfqsId(rs.getLong("C_COMPFQS_ID"));
         dato.setCGradoId(rs.getLong("c_grado_id"));
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
 
     return dato;
   }
 
   public int modificacionOCAPConvocatoria(OCAPComponentesfqsOT datos)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_COMPFQS_CONVOCATORIAS SET C_CONVOCATORIA_ID = ?, C_ITINERARIO_ID = ?, C_PROFESIONAL_ID = ?, C_ESPEC_ID = ?,C_CTE_ID = ?,";
 
       if (datos.getFVinculacion() != null)
         sql = sql + "F_VINCULACION = to_date('" + datos.getFVinculacion() + "'," + Constantes.FECHA_CORTA + "), ";
       else {
         sql = sql + "F_VINCULACION = ?, ";
       }
 
       if ((datos.getFFinalizacion() != null) && (!datos.getFFinalizacion().equals("")))
         sql = sql + "F_FINALIZACION = to_date('" + datos.getFFinalizacion() + "'," + Constantes.FECHA_CORTA + "), ";
       else {
         sql = sql + "F_FINALIZACION = ?, ";
       }
 
       sql = sql + "F_USUMODI = SYSDATE, C_USUMODI = ? WHERE C_COMPFQS_CONVO_ID = ?";
 
       st = con.prepareStatement(sql);
 
       int cont = 1;
 
       if (datos.getCConvocatoriaId() != 0L)
         st.setLong(cont++, datos.getCConvocatoriaId());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getCItinerarioId() != 0L)
         st.setLong(cont++, datos.getCItinerarioId());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getCProfesionalId() != 0L)
         st.setLong(cont++, datos.getCProfesionalId());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getCEspecId() != 0L)
         st.setLong(cont++, datos.getCEspecId());
       else {
         st.setNull(cont++, 12);
       }
       st.setLong(cont++, datos.getCCteId());
 
       if ((datos.getFVinculacion() == null) || (datos.getFVinculacion().equals(""))) {
         st.setNull(cont++, 91);
       }
       if ((datos.getFFinalizacion() == null) || (datos.getFFinalizacion().equals(""))) {
         st.setNull(cont++, 91);
       }
 
       st.setString(cont++, datos.getAModificadoPorComponente());
       st.setLong(cont++, datos.getCCompfqsConvoId());
 
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
 
   public ArrayList buscarOCAPEvaluadorescte(long cCteId, long CPerfilId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String sql = "SELECT C_COMPFQS_ID FROM OCAP_COMPFQS_CONVOCATORIAS WHERE C_CTE_ID = ? AND B_BORRADO = 'N' AND C_COMPFQS_ID IN(SELECT C_COMPFQS_ID FROM OCAP_COMPONENTESFQS WHERE C_PERFIL_ID= ? AND B_BORRADO = 'N'";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cCteId);
       st.setLong(2, CPerfilId);
 
       rs = st.executeQuery();
 
       OCAPComponentesfqsOT datos = new OCAPComponentesfqsOT();
 
       while (rs.next()) {
         datos = new OCAPComponentesfqsOT();
         datos.setCCompfqsId(rs.getLong("C_COMPFQS_ID"));
 
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

