 package es.jcyl.fqs.ocap.oad.expteComponentesfqs;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.io.StringReader;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.SQLException;
 import org.apache.log4j.Logger;
 
 public class OCAPExpteComponentesfqsOAD
 {
   public Logger logger;
   private static OCAPExpteComponentesfqsOAD instance;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
   public static OCAPExpteComponentesfqsOAD getInstance() {
     if (instance == null) {
       instance = new OCAPExpteComponentesfqsOAD();
     }
     return instance;
   }
 
   private OCAPExpteComponentesfqsOAD() {
     $init$();
   }
 
   public int altaOCAPExpteComponentesfqs(OCAPComponentesfqsOT datos)
     throws Exception
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "INSERT INTO OCAP_EXPTE_COMPONENTESFQS (C_EXPTECOMPFQS_ID, C_COMPFQS_ID, A_TITULACION, A_ESPECIALIDAD, F_USUALTA,B_BORRADO,C_USUALTA, A_FORM_ACREDITACION, A_FORM_GESTION, A_FORM_EVALUACION, A_DATOSPROFESIONALES, A_CENTROTRABAJO, C_COMUNI_ID, C_PROVINCIA_ID, A_EXPPROF_SS, A_EXPCAL_ASISTENCIA,A_ACTDOCENTE,A_CATEGORIA,A_CARGO_CTE,C_GRADO_ID) VALUES (OCAP_FQS_ID_SEQ.nextval, ?, ?, ?, SYSDATE, 'N', ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?)";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
 
       int cont = 1;
       st.setLong(cont++, datos.getCCompfqsId());
       st.setString(cont++, datos.getATitulacion());
       st.setString(cont++, datos.getAEspecialidad());
 
       st.setString(cont++, datos.getACreadoPorExp());
 
       if (datos.getAFormAcreditacion() != null) {
         StringReader reader1 = new StringReader(datos.getAFormAcreditacion());
         st.setCharacterStream(cont++, reader1, datos.getAFormAcreditacion().length());
       } else {
         st.setNull(cont++, 2005);
       }
       if (datos.getAFormGestion() != null) {
         StringReader reader2 = new StringReader(datos.getAFormGestion());
         st.setCharacterStream(cont++, reader2, datos.getAFormGestion().length());
       } else {
         st.setNull(cont++, 2005);
       }
       if (datos.getAFormEvaluacion() != null) {
         StringReader reader3 = new StringReader(datos.getAFormEvaluacion());
         st.setCharacterStream(cont++, reader3, datos.getAFormEvaluacion().length());
       } else {
         st.setNull(cont++, 2005);
       }
 
       if (datos.getADatosprofesionales() != null)
         st.setString(cont++, datos.getADatosprofesionales());
       else {
         st.setNull(cont++, 2005);
       }
       st.setString(cont++, datos.getACentrotrabajo());
       st.setString(cont++, datos.getCComuniId());
       st.setString(cont++, datos.getCProvinId());
 
       if (datos.getAExpprofSs() != null)
         st.setString(cont++, datos.getAExpprofSs());
       else {
         st.setNull(cont++, 2005);
       }
       if (datos.getAExpcalAsistencia() != null)
         st.setString(cont++, datos.getAExpcalAsistencia());
       else {
         st.setNull(cont++, 2005);
       }
       if (datos.getAActDocente() != null)
         st.setString(cont++, datos.getAActDocente());
       else {
         st.setNull(cont++, 2005);
       }
       if (datos.getACategoria() != null)
         st.setString(cont++, datos.getACategoria());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getACargo() != null)
         st.setString(cont++, datos.getACargo());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getCGradoId() != 0L)
         st.setLong(cont++, datos.getCGradoId());
       else {
         st.setNull(cont++, 2);
       }
       filas = st.executeUpdate();
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
 
     return filas;
   }
 
   public int modificacionOCAPExpteComponentesfqs(OCAPComponentesfqsOT datos)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_EXPTE_COMPONENTESFQS SET A_FORM_ACREDITACION = ?, A_FORM_GESTION = ?, A_FORM_EVALUACION = ?, F_USUMODI = SYSDATE, C_USUMODI = ?, A_DATOSPROFESIONALES = ?, A_TITULACION = ?, A_ESPECIALIDAD = ?, C_GRADO_ID = ?, A_CENTROTRABAJO = ?, C_COMUNI_ID = ?, C_PROVINCIA_ID = ?, A_EXPPROF_SS = ?, A_EXPCAL_ASISTENCIA = ?, A_ACTDOCENTE = ?, A_CATEGORIA = ?, A_CARGO_CTE = ? WHERE C_COMPFQS_ID = ?";
 
       st = con.prepareStatement(sql);
 
       int cont = 1;
 
       if (datos.getAFormAcreditacion() != null) {
         StringReader reader1 = new StringReader(datos.getAFormAcreditacion());
         st.setCharacterStream(cont++, reader1, datos.getAFormAcreditacion().length());
       } else {
         st.setNull(cont++, 2005);
       }
       if (datos.getAFormGestion() != null) {
         StringReader reader2 = new StringReader(datos.getAFormGestion());
         st.setCharacterStream(cont++, reader2, datos.getAFormGestion().length());
       } else {
         st.setNull(cont++, 2005);
       }
       if (datos.getAFormEvaluacion() != null) {
         StringReader reader4 = new StringReader(datos.getAFormEvaluacion());
         st.setCharacterStream(cont++, reader4, datos.getAFormEvaluacion().length());
       } else {
         st.setNull(cont++, 2005);
       }
       st.setString(cont++, datos.getAModificadoPorExp());
 
       if (datos.getADatosprofesionales() != null)
         st.setString(cont++, datos.getADatosprofesionales());
       else {
         st.setNull(cont++, 12);
       }
 
       if (datos.getATitulacion() != null)
         st.setString(cont++, datos.getATitulacion());
       else {
         st.setNull(cont++, 12);
       }
 
       if (datos.getAEspecialidad() != null)
         st.setString(cont++, datos.getAEspecialidad());
       else {
         st.setNull(cont++, 12);
       }
 
       if (datos.getCGradoId() != 0L)
         st.setLong(cont++, datos.getCGradoId());
       else {
         st.setNull(cont++, 2);
       }
       if (datos.getACentrotrabajo() != null)
         st.setString(cont++, datos.getACentrotrabajo());
       else {
         st.setNull(cont++, 12);
       }
 
       if (datos.getCComuniId() != null)
         st.setString(cont++, datos.getCComuniId());
       else {
         st.setNull(cont++, 12);
       }
 
       if (datos.getCProvinId() != null)
         st.setString(cont++, datos.getCProvinId());
       else {
         st.setNull(cont++, 12);
       }
 
       if (datos.getAExpprofSs() != null)
         st.setString(cont++, datos.getAExpprofSs());
       else {
         st.setNull(cont++, 2005);
       }
 
       if (datos.getAExpcalAsistencia() != null)
         st.setString(cont++, datos.getAExpcalAsistencia());
       else {
         st.setNull(cont++, 2005);
       }
 
       if (datos.getAActDocente() != null)
         st.setString(cont++, datos.getAActDocente());
       else {
         st.setNull(cont++, 2005);
       }
 
       if (datos.getACategoria() != null)
         st.setString(cont++, datos.getACategoria());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getACargo() != null)
         st.setString(cont++, datos.getACargo());
       else {
         st.setNull(cont++, 12);
       }
       st.setLong(cont++, datos.getCCompfqsId());
 
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
 
   public int bajaOCAPComponentesCtes(OCAPComponentesfqsOT datos)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_EXPTE_COMPONENTESFQS SET B_BORRADO = 'Y', F_USUMODI = SYSDATE, C_USUMODI = ? WHERE C_COMPFQS_ID =  ?";
 
       st = con.prepareStatement(sql);
 
       st.setString(1, datos.getAModificadoPorExp());
       st.setLong(2, datos.getCCompfqsId());
 
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
 }

