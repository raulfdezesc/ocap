 package es.jcyl.fqs.ocap.oad.componentesfqs;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.CallableStatement;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.text.DecimalFormat;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPComponentesfqsOAD
 {
   public Logger logger;
   private static OCAPComponentesfqsOAD instance;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
 
   public static OCAPComponentesfqsOAD getInstance() {
     if (instance == null) {
       instance = new OCAPComponentesfqsOAD();
     }
     return instance;
   }
 
   private OCAPComponentesfqsOAD() {
     $init$();
   }
 
   public long altaOCAPComponentesfqs(OCAPComponentesfqsOT datos)
     throws Exception
   {
     CallableStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     long idComp = 0L;
     try
     {
       String sql = "BEGIN INSERT INTO OCAP_COMPONENTESFQS (C_COMPFQS_ID, D_APELLIDOS, D_NOMBRE, C_DNI, B_SEXO, N_TELEFONO,A_CORREOELECTRONICO,F_USUALTA,B_BORRADO,C_USUALTA, C_PERFIL_ID,A_DIR_NOMBRE,A_DIR_NUM,A_DIR_ESCALERA,A_DIR_PISO,A_DIR_LETRA,C_CP,C_PROV_ID,C_LOCALIDAD_ID, C_COMUNIDAD_ID) VALUES (OCAP_COM_ID_SEQ.nextval, ?, ?, LPAD(?,9,'0'), ?, ?, ?, SYSDATE, 'N', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING C_COMPFQS_ID INTO ?; END;";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareCall(sql);
 
       int cont = 1;
 
       st.setString(cont++, datos.getDApellidos());
       st.setString(cont++, datos.getDNombre());
       st.setString(cont++, datos.getCDni());
 
       if (datos.getBSexo() != null)
         st.setString(cont++, datos.getBSexo());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getNTelefono1() != null)
         st.setString(cont++, datos.getNTelefono1());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getACorreoelectronico() != null)
         st.setString(cont++, datos.getACorreoelectronico());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getaCreadoPorComponente() != null)
         st.setString(cont++, datos.getaCreadoPorComponente());
       else {
         st.setNull(cont++, 12);
       }
 
       if (datos.getCPerfilId() != 0L)
         st.setLong(cont++, datos.getCPerfilId());
       else {
         st.setNull(cont++, 2);
       }
       if (datos.getADirNombre() != null)
         st.setString(cont++, datos.getADirNombre());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getADirNum() != null)
         st.setString(cont++, datos.getADirNum());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getADirEscalera() != null)
         st.setString(cont++, datos.getADirEscalera());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getADirPiso() != null)
         st.setString(cont++, datos.getADirPiso());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getADirLetra() != null)
         st.setString(cont++, datos.getADirLetra());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getCCp() != null)
         st.setString(cont++, datos.getCCp());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getCProvId() != null)
         st.setString(cont++, datos.getCProvId());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getCLocalidadId() != null)
         st.setString(cont++, datos.getCLocalidadId());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getCComunidadId() != null)
         st.setString(cont++, datos.getCComunidadId());
       else {
         st.setNull(cont++, 12);
       }
       st.registerOutParameter(cont++, 4);
 
       filas = st.executeUpdate();
       idComp = st.getLong(--cont);
     }
     catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     }
 
     return idComp;
   }
 
   public ArrayList consultaOCAPEvaluadores(OCAPComponentesfqsOT componentesOT, String opcion, int inicio, int cuantos, String bEvaluacion2)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String where = "";
       StringBuffer sbWhere = new StringBuffer(40);
       StringBuffer sbConv = new StringBuffer(40);
       String selectFrom = "SELECT D_APELLIDOS, D_NOMBRE, C_DNI, A_TITULACION, A_ESPECIALIDAD, B_EVALUACION2, comp.C_COMPFQS_ID FROM OCAP_COMPONENTESFQS comp, OCAP_EXPTE_COMPONENTESFQS exp WHERE comp.B_BORRADO = 'N' AND exp.B_BORRADO = 'N' AND comp.C_COMPFQS_ID = exp.C_COMPFQS_ID ";
 
       String selectReg = " AND comp.c_compfqs_id not in( select distinct(c_compfqs_id) from OCAP_COMPFQS_CONVOCATORIAS where c_convocatoria_id in (select C_CONVOCATORIA_ID from OCAP_CONVOCATORIAS where sysdate > f_publicacion and sysdate < f_fincp and b_borrado = 'N') and b_borrado = 'N') ";
 
       String selectConv = " AND comp.c_compfqs_id in( select c_compfqs_id from OCAP_COMPFQS_CONVOCATORIAS where b_borrado = 'N' ";
 
       String orderBy = " order by comp.C_COMPFQS_ID";
 
       if ((componentesOT.getDNombre() != null) && (!componentesOT.getDNombre().equals("")))
         sbWhere.append("AND (upper(D_NOMBRE) like upper('%").append(componentesOT.getDNombre()).append("%')) ");
       if ((componentesOT.getDApellidos() != null) && (!componentesOT.getDApellidos().equals("")))
         sbWhere.append("AND (upper(D_APELLIDOS) like upper('%").append(componentesOT.getDApellidos()).append("%')) ");
       if ((componentesOT.getCDni() != null) && (!componentesOT.getCDni().equals("")))
         sbWhere.append("AND (upper(C_DNI) = upper('").append(componentesOT.getCDni()).append("')) ");
       if ((componentesOT.getATitulacion() != null) && (!componentesOT.getATitulacion().equals("")))
         sbWhere.append("AND (upper(A_TITULACION) like upper('%").append(componentesOT.getATitulacion()).append("%')) ");
       if (componentesOT.getCPerfilId() != 0L)
         sbWhere.append("AND C_PERFIL_ID = ").append(componentesOT.getCPerfilId());
       if ((opcion != null) && (opcion.equals("registro"))) {
         sbWhere.append(selectReg);
       } else {
         if (componentesOT.getCCteId() != 0L) {
           sbConv.append(" AND C_CTE_ID = ").append(componentesOT.getCCteId());
         }
         if (componentesOT.getCConvocatoriaId() != 0L) {
           sbConv.append(" AND C_CONVOCATORIA_ID = ").append(componentesOT.getCConvocatoriaId());
         }
         if ((!Constantes.SI.equals(bEvaluacion2)) && 
           (componentesOT.getCItinerarioId() != 0L)) {
           sbConv.append(" AND C_ITINERARIO_ID = ").append(componentesOT.getCItinerarioId());
         }
 
         if (sbConv.length() != 0) {
           sbWhere.append(selectConv).append(sbConv).append(")");
         }
       }
       if ((componentesOT.getNAniosConv() != null) && (!componentesOT.getNAniosConv().equals(""))) {
         sbWhere.append(" AND comp.C_COMPFQS_ID IN( ").append("select c_compfqs_id from OCAP_COMPFQS_CONVOCATORIAS where c_convocatoria_id in (").append("select C_CONVOCATORIA_ID from OCAP_CONVOCATORIAS ").append("WHERE to_char(F_PUBLICACION,'YYYY') <= '").append(componentesOT.getNAniosConv()).append("' AND to_char(F_FINCP,'YYYY') >= '").append(componentesOT.getNAniosConv()).append("' AND B_BORRADO = 'N')").append(" AND B_BORRADO = 'N')");
       }
 
       String sbWhereEval2 = "";
       if (Constantes.SI.equals(bEvaluacion2)) {
         sbWhereEval2 = " AND comp.B_EVALUACION2 ='Y' ";
       }
 
       String sqlStatement = selectFrom + sbWhere + sbWhereEval2 + orderBy;
 
       st = con.prepareStatement(sqlStatement, 1004, 1008);
 
       int cont = 1;
 
       rs = st.executeQuery();
       if (inicio > 1)
         rs.absolute(inicio - 1);
       listado = new ArrayList();
       OCAPComponentesfqsOT datos = new OCAPComponentesfqsOT();
 
       int i = 0;
       while (rs.next()) {
         datos = new OCAPComponentesfqsOT();
         datos.setCCompfqsId(rs.getLong("C_COMPFQS_ID"));
         datos.setDApellidos(rs.getString("D_APELLIDOS"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setCDni(rs.getString("C_DNI"));
         datos.setATitulacion(rs.getString("A_TITULACION"));
         if ((opcion != null) && (opcion.equals("cp")) && (componentesOT.getCConvocatoriaId() != 0L)) {
           datos.setCConvocatoriaId(componentesOT.getCConvocatoriaId());
         }
 
         datos.setDApellNom(datos.getDApellidos() + ", " + datos.getDNombre());
 
         datos.setBEvaluacion2(rs.getString("B_EVALUACION2"));
 
         DecimalFormat formato = new DecimalFormat("000000");
         datos.setCodigoId("REX" + formato.format(datos.getCCompfqsId()));
 
         datos.setAEspecialidad(rs.getString("A_ESPECIALIDAD"));
 
         listado.add(datos);
 
         if (++i == cuantos)	break;
       }
     }
     catch (Exception ex) {
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
 
   public int listadoOCAPEvaluadoresCuenta(OCAPComponentesfqsOT componentesOT, String opcion)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     int total = -1;
     try
     {
       String where = "";
       StringBuffer sbWhere = new StringBuffer(40);
       StringBuffer sbConv = new StringBuffer(40);
       String selectFrom = "SELECT COUNT(*) FROM OCAP_COMPONENTESFQS comp, OCAP_EXPTE_COMPONENTESFQS exp WHERE comp.B_BORRADO = 'N' AND exp.B_BORRADO = 'N' AND comp.C_COMPFQS_ID = exp.C_COMPFQS_ID ";
 
       String selectReg = " AND comp.c_compfqs_id not in( select distinct(c_compfqs_id) from OCAP_COMPFQS_CONVOCATORIAS where c_convocatoria_id in (select C_CONVOCATORIA_ID from OCAP_CONVOCATORIAS where sysdate > f_publicacion and sysdate < f_fincp and b_borrado = 'N') and b_borrado = 'N') ";
 
       String selectConv = " AND comp.c_compfqs_id in( select c_compfqs_id from OCAP_COMPFQS_CONVOCATORIAS where b_borrado = 'N' ";
 
       String orderBy = " order by comp.C_COMPFQS_ID";
 
       if ((componentesOT.getDNombre() != null) && (!componentesOT.getDNombre().equals("")))
         sbWhere.append("AND (upper(D_NOMBRE) like upper('%").append(componentesOT.getDNombre()).append("%')) ");
       if ((componentesOT.getDApellidos() != null) && (!componentesOT.getDApellidos().equals("")))
         sbWhere.append("AND (upper(D_APELLIDOS) like upper('%").append(componentesOT.getDApellidos()).append("%')) ");
       if ((componentesOT.getCDni() != null) && (!componentesOT.getCDni().equals("")))
         sbWhere.append("AND (upper(C_DNI) like upper('%").append(componentesOT.getCDni()).append("%')) ");
       if ((componentesOT.getATitulacion() != null) && (!componentesOT.getATitulacion().equals("")))
         sbWhere.append("AND (upper(A_TITULACION) like upper('%").append(componentesOT.getATitulacion()).append("%')) ");
       if (componentesOT.getCPerfilId() != 0L)
         sbWhere.append("AND C_PERFIL_ID = ").append(componentesOT.getCPerfilId());
       if ((opcion != null) && (opcion.equals("registro"))) {
         sbWhere.append(selectReg);
       } else {
         if (componentesOT.getCCteId() != 0L) {
           sbConv.append(" AND C_CTE_ID = ").append(componentesOT.getCCteId());
         }
         if (componentesOT.getCConvocatoriaId() != 0L) {
           sbConv.append(" AND C_CONVOCATORIA_ID = ").append(componentesOT.getCConvocatoriaId());
         }
         if (componentesOT.getCItinerarioId() != 0L) {
           sbConv.append(" AND C_ITINERARIO_ID = ").append(componentesOT.getCItinerarioId());
         }
         if (sbConv.length() != 0) {
           sbWhere.append(selectConv).append(sbConv).append(")");
         }
       }
       if ((componentesOT.getNAniosConv() != null) && (!componentesOT.getNAniosConv().equals(""))) {
         sbWhere.append("AND comp.C_COMPFQS_ID IN( ").append("select c_compfqs_id from OCAP_COMPFQS_CONVOCATORIAS where c_convocatoria_id in (").append("select C_CONVOCATORIA_ID from OCAP_CONVOCATORIAS ").append("WHERE to_char(F_PUBLICACION,'YYYY') <= '").append(componentesOT.getNAniosConv()).append("' AND to_char(F_FINCP,'YYYY') >= '").append(componentesOT.getNAniosConv()).append("' AND B_BORRADO = 'N')").append(" AND B_BORRADO = 'N')");
       }
 
       String sqlStatement = selectFrom + sbWhere + orderBy;
 
       st = con.prepareStatement(sqlStatement);
 
       int cont = 1;
 
       rs = st.executeQuery(sqlStatement);
 
       if (rs.next())
         total = rs.getInt(1);
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return total;
   }
 
   public int listadoOCAPEvaluadoresCuentaAnio(OCAPComponentesfqsOT componentesOT, String opcion)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     int total = -1;
     try
     {
       String where = "";
       StringBuffer sbWhere = new StringBuffer(40);
       String selectFrom = "SELECT COUNT(*) FROM OCAP_COMPONENTESFQS comp, OCAP_EXPTE_COMPONENTESFQS exp WHERE comp.B_BORRADO = 'N' AND exp.B_BORRADO = 'N' AND comp.C_COMPFQS_ID = exp.C_COMPFQS_ID ";
 
       String selectConv = " AND comp.c_compfqs_id not in( select c_compfqs_id from OCAP_COMPFQS_CONVOCATORIAS where c_convocatoria_id in (select C_CONVOCATORIA_ID from OCAP_CONVOCATORIAS where sysdate > f_publicacion and sysdate < f_fincp and b_borrado = 'N') and b_borrado = 'N') ";
 
       String orderBy = " order by comp.C_COMPFQS_ID";
 
       if ((componentesOT.getDNombre() != null) && (!componentesOT.getDNombre().equals("")))
         sbWhere.append("AND (upper(D_NOMBRE) like upper('%").append(componentesOT.getDNombre()).append("%')) ");
       if ((componentesOT.getDApellidos() != null) && (!componentesOT.getDApellidos().equals("")))
         sbWhere.append("AND (upper(D_APELLIDOS) like upper('%").append(componentesOT.getDApellidos()).append("%')) ");
       if ((componentesOT.getCDni() != null) && (!componentesOT.getCDni().equals("")))
         sbWhere.append("AND (upper(C_DNI) like upper('%").append(componentesOT.getCDni()).append("%')) ");
       if ((componentesOT.getATitulacion() != null) && (!componentesOT.getATitulacion().equals("")))
         sbWhere.append("AND (upper(A_TITULACION) like upper('%").append(componentesOT.getATitulacion()).append("%')) ");
       if (componentesOT.getCPerfilId() != 0L)
         sbWhere.append("AND C_PERFIL_ID = ").append(componentesOT.getCPerfilId());
       if (componentesOT.getCCteId() != 0L)
         sbWhere.append(" AND C_CTE_ID = ? ");
       if ((opcion != null) && (opcion.equals("registro")))
         sbWhere.append(selectConv);
       else if (componentesOT.getCConvocatoriaId() != 0L) {
         sbWhere.append(" AND C_CONVOCATORIA_ID = ? ");
       }
 
       String sqlStatement = selectFrom + sbWhere + orderBy;
 
       st = con.prepareStatement(sqlStatement);
 
       int cont = 1;
 
       rs = st.executeQuery(sqlStatement);
 
       if (rs.next())
         total = rs.getInt(1);
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return total;
   }
 
   public OCAPComponentesfqsOT buscarOCAPComponentesfqs(long cCompfqsId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     OCAPComponentesfqsOT datos = null;
     try
     {
       String sql = "SELECT D_NOMBRE, D_APELLIDOS, C_DNI, B_SEXO, N_TELEFONO, A_CORREOELECTRONICO, comp.C_COMPFQS_ID, A_FORM_ACREDITACION, A_FORM_GESTION, A_FORM_COMUNICACION, A_FORM_EVALUACION, A_DATOSPROFESIONALES, A_DIR_NOMBRE, A_DIR_NUM, A_DIR_ESCALERA, A_DIR_PISO, A_DIR_LETRA, C_CP, C_PROV_ID, C_LOCALIDAD_ID, A_TITULACION, A_ESPECIALIDAD, A_CENTROTRABAJO, C_GRADO_ID, C_COMUNI_ID, C_PROVINCIA_ID, A_EXPPROF_SS, A_EXPCAL_ASISTENCIA, A_ACTDOCENTE, A_CATEGORIA, A_CARGO_CTE, C_COMUNIDAD_ID FROM OCAP_COMPONENTESFQS comp, OCAP_EXPTE_COMPONENTESFQS exp WHERE comp.C_COMPFQS_ID = exp.C_COMPFQS_ID AND comp.B_BORRADO = 'N' AND exp.B_BORRADO = 'N' AND comp.C_COMPFQS_ID = ?";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cCompfqsId);
 
       rs = st.executeQuery();
 
       datos = new OCAPComponentesfqsOT();
       if (rs.next()) {
         datos.setCCompfqsId(rs.getLong("C_COMPFQS_ID"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setDApellidos(rs.getString("D_APELLIDOS"));
         datos.setCDni(rs.getString("C_DNI"));
         datos.setBSexo(rs.getString("B_SEXO"));
         datos.setNTelefono1(rs.getString("N_TELEFONO"));
         datos.setACorreoelectronico(rs.getString("A_CORREOELECTRONICO"));
         datos.setAFormAcreditacion(rs.getString("A_FORM_ACREDITACION"));
         datos.setAFormGestion(rs.getString("A_FORM_GESTION"));
         datos.setAFormComunicacion(rs.getString("A_FORM_COMUNICACION"));
         datos.setAFormEvaluacion(rs.getString("A_FORM_EVALUACION"));
         datos.setADatosprofesionales(rs.getString("A_DATOSPROFESIONALES"));
         datos.setADirNombre(rs.getString("A_DIR_NOMBRE"));
         datos.setADirNum(rs.getString("A_DIR_NUM"));
         datos.setADirEscalera(rs.getString("A_DIR_ESCALERA"));
         datos.setADirPiso(rs.getString("A_DIR_PISO"));
         datos.setADirLetra(rs.getString("A_DIR_LETRA"));
         datos.setCCp(rs.getString("C_CP"));
         datos.setCProvId(rs.getString("C_PROV_ID"));
         datos.setCLocalidadId(rs.getString("C_LOCALIDAD_ID"));
         datos.setATitulacion(rs.getString("A_TITULACION"));
         datos.setAEspecialidad(rs.getString("A_ESPECIALIDAD"));
         datos.setACentrotrabajo(rs.getString("A_CENTROTRABAJO"));
         datos.setCGradoId(rs.getLong("C_GRADO_ID"));
         datos.setCComuniId(rs.getString("C_COMUNI_ID"));
         datos.setCProvinId(rs.getString("C_PROVINCIA_ID"));
         datos.setAExpprofSs(rs.getString("A_EXPPROF_SS"));
         datos.setAExpcalAsistencia(rs.getString("A_EXPCAL_ASISTENCIA"));
         datos.setAActDocente(rs.getString("A_ACTDOCENTE"));
         datos.setACategoria(rs.getString("A_CATEGORIA"));
         datos.setACargo(rs.getString("A_CARGO_CTE"));
         datos.setCComunidadId(rs.getString("C_COMUNIDAD_ID"));
 
         datos.setDApellNom(datos.getDApellidos() + ", " + datos.getDNombre());
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
 
   public OCAPComponentesfqsOT buscarOCAPComponentesfqsCategoria(OCAPComponentesfqsOT datos, long cItinerarioId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
 
     Connection con = JCYLGestionTransacciones.getConnection();
     try
     {
       StringBuffer sql = new StringBuffer();
       sql.append("SELECT conv.c_profesional_id, cate.d_nombre as dProfesional_nombre, ").append(" conv.c_espec_id, espe.d_nombre as dEspec_nombre ").append(" FROM ocap_compfqs_convocatorias conv, ").append(" ocap_categ_profesionales cate, ").append(" ocap_especialidades espe ").append(" WHERE conv.b_borrado = 'N' ").append(" AND conv.c_compfqs_id = ? ").append(" AND conv.c_itinerario_id = ? ").append(" AND conv.c_profesional_id = cate.c_profesional_id(+) ").append(" AND conv.c_profesional_id = espe.c_profesional_id(+) ").append(" AND conv.c_espec_id = espe.c_espec_id(+) ");
 
       st = con.prepareStatement(sql.toString());
 
       st.setLong(1, datos.getCCompfqsId());
       st.setLong(2, cItinerarioId);
 
       rs = st.executeQuery();
 
       if (rs.next()) {
         datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
         datos.setDProfesionalNombre(rs.getString("dProfesional_nombre"));
         datos.setCEspecId(rs.getLong("C_ESPEC_ID"));
         datos.setDEspecNombre(rs.getString("dEspec_nombre"));
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
 
   public int modificacionOCAPComponentesfqs(OCAPComponentesfqsOT datos)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_COMPONENTESFQS SET D_NOMBRE = ?, D_APELLIDOS = ?, C_DNI = LPAD(?, 9, '0'), N_TELEFONO = ?, A_CORREOELECTRONICO = ?,F_USUMODI = SYSDATE, C_USUMODI = ?, A_DIR_NOMBRE = ?, A_DIR_NUM = ?, A_DIR_ESCALERA = ?, A_DIR_PISO = ?, A_DIR_LETRA = ?, C_CP = ?, C_PROV_ID = ?, C_LOCALIDAD_ID = ?, C_COMUNIDAD_ID = ? WHERE C_COMPFQS_ID = ?";
 
       st = con.prepareStatement(sql);
 
       int cont = 1;
 
       st.setString(cont++, datos.getDNombre());
       st.setString(cont++, datos.getDApellidos());
       st.setString(cont++, datos.getCDni());
 
       if (datos.getNTelefono1() != null)
         st.setString(cont++, datos.getNTelefono1());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getACorreoelectronico() != null)
         st.setString(cont++, datos.getACorreoelectronico());
       else {
         st.setNull(cont++, 12);
       }
       st.setString(cont++, datos.getAModificadoPorComponente());
 
       if (datos.getADirNombre() != null)
         st.setString(cont++, datos.getADirNombre());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getADirNum() != null)
         st.setString(cont++, datos.getADirNum());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getADirEscalera() != null)
         st.setString(cont++, datos.getADirEscalera());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getADirPiso() != null)
         st.setString(cont++, datos.getADirPiso());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getADirLetra() != null)
         st.setString(cont++, datos.getADirLetra());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getCCp() != null)
         st.setString(cont++, datos.getCCp());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getCProvId() != null)
         st.setString(cont++, datos.getCProvId());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getCLocalidadId() != null)
         st.setString(cont++, datos.getCLocalidadId());
       else {
         st.setNull(cont++, 12);
       }
       if (datos.getCComunidadId() != null)
         st.setString(cont++, datos.getCComunidadId());
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
 
   public int activarEvaluadoresSegunda(OCAPComponentesfqsOT datos)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_COMPONENTESFQS SET B_EVALUACION2 = ? WHERE C_COMPFQS_ID = ?";
 
       st = con.prepareStatement(sql);
 
       int cont = 1;
 
       st.setString(cont++, datos.getBEvaluacion2());
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
 
   public int listadoOCAPDniCuenta(String cDni, int perfil)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     StringBuffer consulta = new StringBuffer();
 
     int total = -1;
     try
     {
       consulta = new StringBuffer();
       String sql = "SELECT COUNT(*) FROM OCAP_COMPONENTESFQS WHERE B_BORRADO = 'N' and C_DNI = '" + cDni + "'";
 
       if (perfil != 0) {
         sql = sql + " AND C_PERFIL_ID = " + perfil;
       }
 
       String sqlStatement = sql;
 
       st = con.prepareStatement(sqlStatement);
 
       rs = st.executeQuery(sqlStatement);
 
       if (rs.next())
         total = rs.getInt(1);
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return total;
   }
 
   public int listadoOCAPDniCTECuenta(long cCteId, String cDni, int perfil)
		     throws Exception
		   {
		     PreparedStatement st = null;
		     ResultSet rs = null;
		     Connection con = JCYLGestionTransacciones.getConnection();
		     StringBuffer consulta = new StringBuffer();
		 
		     int total = -1;
		     try
		     {
		       consulta = new StringBuffer();
		       String sql = "SELECT COUNT(*) FROM OCAP_COMPONENTESFQS a, OCAP_COMPFQS_CONVOCATORIAS c WHERE a.C_COMPFQS_ID=c.C_COMPFQS_ID and c.B_BORRADO = 'N' AND A.C_DNI         = '" + cDni + "'" +" AND c.C_CTE_ID =  "+ cCteId ;
		 
		       if (perfil != 0) {
		         sql = sql + " AND C_PERFIL_ID = " + perfil;
		       }
		 
		       String sqlStatement = sql;
		 
		       st = con.prepareStatement(sqlStatement);
		 
		       rs = st.executeQuery(sqlStatement);
		 
		       if (rs.next())
		         total = rs.getInt(1);
		     }
		     catch (Exception ex) {
		       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
		       throw ex;
		     } finally {
		       if (rs != null)
		         rs.close();
		       if (st != null)
		         st.close();
		       JCYLGestionTransacciones.close(con.getAutoCommit());
		     }
		 
		     return total;
		   }
   public ArrayList listarComponentes(OCAPComponentesfqsOT datosFiltro, String opcion, int inicio, int cuantos)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String sql = "SELECT D_APELLIDOS, D_NOMBRE, C_DNI, to_char(F_VINCULACION,'DD/MM/YYYY') as f_vinculacion, to_char(F_FINALIZACION,'DD/MM/YYYY') as f_finalizacion, comp.C_COMPFQS_ID, C_COMPFQS_CONVO_ID FROM OCAP_COMPONENTESFQS comp, OCAP_COMPFQS_CONVOCATORIAS con WHERE con.C_COMPFQS_ID = comp.C_COMPFQS_ID AND comp.B_BORRADO = 'N' AND con.B_BORRADO = 'N' ";
       if (datosFiltro.getCCteId() != 0L) {
         sql = sql + " AND con.C_CTE_ID = ? ";
       }
       if (datosFiltro.getCPerfilId() != 0L) {
         sql = sql + " AND C_PERFIL_ID = ? ";
       }
       if (datosFiltro.getCCompfqsId() != 0L) {
         sql = sql + " AND con.C_COMPFQS_ID = ? ";
       }
       st = con.prepareStatement(sql, 1004, 1008);
       int cont = 1;
       if (datosFiltro.getCCteId() != 0L) {
         st.setLong(cont++, datosFiltro.getCCteId());
       }
       if (datosFiltro.getCPerfilId() != 0L) {
         st.setLong(cont++, datosFiltro.getCPerfilId());
       }
       if (datosFiltro.getCCompfqsId() != 0L) {
         st.setLong(cont++, datosFiltro.getCCompfqsId());
       }
       rs = st.executeQuery();
       if (inicio > 1)
         rs.absolute(inicio - 1);
       listado = new ArrayList();
       OCAPComponentesfqsOT datos = new OCAPComponentesfqsOT();
       int i = 0;
       while (rs.next()) {
         datos = new OCAPComponentesfqsOT();
         datos.setDApellidos(rs.getString("D_APELLIDOS"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setCDni(rs.getString("C_DNI"));
         datos.setCCompfqsId(rs.getLong("C_COMPFQS_ID"));
         datos.setCCteId(datosFiltro.getCCteId());
         datos.setFVinculacion(rs.getString("F_VINCULACION"));
         datos.setFFinalizacion(rs.getString("f_finalizacion"));
         datos.setCCompfqsConvoId(rs.getLong("C_COMPFQS_CONVO_ID"));
 
         listado.add(datos);
 
         if (++i == cuantos)	break;
       }
     }
     catch (Exception ex) {
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
 
   public int listarComponentesCuenta(OCAPComponentesfqsOT datos, String opcion)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int total = -1;
     try
     {
       String sql = "SELECT COUNT(*) FROM OCAP_COMPONENTESFQS comp, OCAP_COMPFQS_CONVOCATORIAS con WHERE con.C_COMPFQS_ID = comp.C_COMPFQS_ID AND comp.B_BORRADO = 'N' AND con.B_BORRADO = 'N' ";
       if (datos.getCCteId() != 0L) {
         sql = sql + " AND con.C_CTE_ID = ? ";
       }
       if (datos.getCPerfilId() != 0L) {
         sql = sql + " AND C_PERFIL_ID = ? ";
       }
       if (datos.getCCompfqsId() != 0L) {
         sql = sql + " AND con.C_COMPFQS_ID = ? ";
       }
       st = con.prepareStatement(sql, 1004, 1008);
 
       int cont = 1;
       if (datos.getCCteId() != 0L) {
         st.setLong(cont++, datos.getCCteId());
       }
       if (datos.getCPerfilId() != 0L) {
         st.setLong(cont++, datos.getCPerfilId());
       }
       if (datos.getCCompfqsId() != 0L) {
         st.setLong(cont++, datos.getCCompfqsId());
       }
       rs = st.executeQuery();
 
       if (rs.next())
         total = rs.getInt(1);
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return total;
   }
 
   public int bajaOCAPComponentesCtes(OCAPComponentesfqsOT datos)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_COMPONENTESFQS SET B_BORRADO = 'Y', F_USUMODI = SYSDATE, C_USUMODI = ? WHERE C_COMPFQS_ID =  ?";
 
       st = con.prepareStatement(sql);
 
       st.setString(1, datos.getAModificadoPorComponente());
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
 
   public OCAPComponentesfqsOT buscarOCAPComponenteDni(String cDni, long cPerfilId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     OCAPComponentesfqsOT datos = null;
     try
     {
       String sql = "SELECT C_COMPFQS_ID FROM OCAP_COMPONENTESFQS WHERE C_DNI = ? AND C_PERFIL_ID = ? AND B_BORRADO = 'N' ";
 
       st = con.prepareStatement(sql);
       st.setString(1, cDni);
       st.setLong(2, cPerfilId);
 
       rs = st.executeQuery();
 
       datos = new OCAPComponentesfqsOT();
 
       if (rs.next())
         datos.setCCompfqsId(rs.getLong("C_COMPFQS_ID"));
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
 
   public ArrayList consultaOCAPEvaluadoresCte(OCAPComponentesfqsOT componentesOT, int inicio, int cuantos)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String where = "";
       StringBuffer sbWhere = new StringBuffer(40);
       String selectFrom = "SELECT DISTINCT C_DNI , D_NOMBRE, D_APELLIDOS, A_TITULACION, C_CTE_ID, A_ESPECIALIDAD, comp.C_COMPFQS_ID FROM OCAP_COMPONENTESFQS comp, OCAP_EXPTE_COMPONENTESFQS exp, OCAP_COMPFQS_CONVOCATORIAS conv WHERE comp.B_BORRADO = 'N' AND exp.B_BORRADO = 'N' AND conv.B_BORRADO = 'N' AND comp.C_COMPFQS_ID = exp.C_COMPFQS_ID AND comp.C_COMPFQS_ID = conv.C_COMPFQS_ID ";
 
       String orderBy = " order by C_CTE_ID, D_APELLIDOS";
 
       if ((componentesOT.getDNombre() != null) && (!componentesOT.getDNombre().equals("")))
         sbWhere.append("AND (upper(D_NOMBRE) like upper('%").append(componentesOT.getDNombre()).append("%')) ");
       if ((componentesOT.getDApellidos() != null) && (!componentesOT.getDApellidos().equals("")))
         sbWhere.append("AND (upper(D_APELLIDOS) like upper('%").append(componentesOT.getDApellidos()).append("%')) ");
       if ((componentesOT.getCDni() != null) && (!componentesOT.getCDni().equals("")))
         sbWhere.append("AND (upper(C_DNI) like upper('%").append(componentesOT.getCDni()).append("%')) ");
       if (componentesOT.getCPerfilId() != 0L)
         sbWhere.append("AND C_PERFIL_ID = ").append(componentesOT.getCPerfilId());
       if (componentesOT.getCCteId() != 0L)
         sbWhere.append(" AND C_CTE_ID = ? ");
       if (componentesOT.getCConvocatoriaId() != 0L) {
         sbWhere.append(" AND C_CONVOCATORIA_ID = ? ");
       }
       String sqlStatement = selectFrom + sbWhere + orderBy;
 
       st = con.prepareStatement(sqlStatement, 1004, 1008);
 
       int cont = 1;
 
       if (componentesOT.getCCteId() != 0L)
         st.setLong(cont++, componentesOT.getCCteId());
       if (componentesOT.getCConvocatoriaId() != 0L) {
         st.setLong(cont++, componentesOT.getCConvocatoriaId());
       }
       rs = st.executeQuery();
       if (inicio > 1)
         rs.absolute(inicio - 1);
       listado = new ArrayList();
       OCAPComponentesfqsOT datos = new OCAPComponentesfqsOT();
       int i = 0;
       while (rs.next()) {
         datos = new OCAPComponentesfqsOT();
         datos.setCCompfqsId(rs.getLong("C_COMPFQS_ID"));
         datos.setDApellidos(rs.getString("D_APELLIDOS"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setCDni(rs.getString("C_DNI"));
         datos.setATitulacion(rs.getString("A_TITULACION"));
         datos.setAEspecialidad(rs.getString("A_ESPECIALIDAD"));
         datos.setCCteId(rs.getLong("C_CTE_ID"));
 
         listado.add(datos);
 
         if (++i == cuantos)	break;
       }
     }
     catch (Exception ex) {
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
 
   public OCAPComponentesfqsOT buscarCteUsuario(long cExpId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     OCAPComponentesfqsOT datos = null;
     try
     {
       String sql = "SELECT distinct(conv.c_cte_id), cte.d_nombre, (select d_nombre from OCAP_COMPONENTESFQS comp2, OCAP_COMPFQS_CONVOCATORIAS conv2 where conv2.c_compfqs_id = comp2.C_COMPFQS_ID and comp2.c_compfqs_id in( select c_compfqs_id from OCAP_EXPTE_COMPONENTESFQS where a_cargo_cte = '1' and b_borrado = 'N') and comp2.b_borrado = 'N' and conv2.b_borrado = 'N' and conv2.c_cte_id = conv.c_cte_id and rownum=1 ) as nombre,  (select d_apellidos from OCAP_COMPONENTESFQS comp2, OCAP_COMPFQS_CONVOCATORIAS conv2 where conv2.c_compfqs_id = comp2.C_COMPFQS_ID and comp2.c_compfqs_id in( select c_compfqs_id from OCAP_EXPTE_COMPONENTESFQS where a_cargo_cte = '1' and b_borrado = 'N') and comp2.b_borrado = 'N' and conv2.b_borrado = 'N' and conv2.c_cte_id = conv.c_cte_id and rownum=1 ) as apellidos  FROM OCAP_COMPONENTESFQS com, OCAP_COMPFQS_CONVOCATORIAS conv, OCAP_CTES cte WHERE conv.c_cte_id = (select distinct(occ.c_cte_id) from ocap_compfqs_convocatorias occ where occ.b_borrado = 'N' AND occ.c_compfqs_id = (select c_compfqs_id from ocap_expedientescarrera where c_exp_id=?) and occ.c_convocatoria_id = (select c_convocatoria_id from ocap_expedientescarrera where c_exp_id=?)  and occ.c_itinerario_id = (select c_itinerario_id from ocap_expedientescarrera where c_exp_id=?)) AND com.B_BORRADO = 'N' and  com.C_COMPFQS_ID = conv.C_COMPFQS_ID AND conv.B_BORRADO = 'N' and  cte.c_cte_id = conv.c_cte_id  AND cte.B_BORRADO = 'N' ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId);
       st.setLong(2, cExpId);
       st.setLong(3, cExpId);
 
       rs = st.executeQuery();
 
       datos = new OCAPComponentesfqsOT();
 
       if (rs.next()) {
         datos.setDNombre(rs.getString("d_nombre"));
         datos.setDNombrePresidente(rs.getString("nombre"));
         datos.setDApellidos(rs.getString("apellidos"));
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
 
   public ArrayList listarEvaluados(long cCteId, long cPerfilId, int inicio, int cuantos)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String sql = "select distinct(oec.c_exp_id), ocf.c_compfqs_id, TO_DATE(oec.f_informe_ce , 'DD/MM/RRRR hh24:mi:ss') AS f_informe_ce from ocap_compfqs_convocatorias occ, ocap_expedientescarrera oec, ocap_componentesfqs ocf WHERE occ.C_CTE_ID = ? AND occ.C_COMPFQS_ID = ocf.C_COMPFQS_ID AND occ.C_COMPFQS_ID = oec.C_COMPFQS_ID AND ocf.C_PERFIL_ID = ? AND oec.C_ITINERARIO_ID = occ.C_ITINERARIO_ID AND oec.C_CONVOCATORIA_ID = occ.C_CONVOCATORIA_ID AND oec.B_BORRADO='N' AND occ.B_BORRADO='N' AND ocf.B_BORRADO='N' AND oec.F_INFORME_CTE is not null order by c_compfqs_id, c_exp_id ";
 
       st = con.prepareStatement(sql, 1004, 1008);
       st.setLong(1, cCteId);
       st.setLong(2, cPerfilId);
 
       rs = st.executeQuery();
       if (inicio > 1)
         rs.absolute(inicio - 1);
       listado = new ArrayList();
       OCAPComponentesfqsOT datos = new OCAPComponentesfqsOT();
       int i = 0;
       while (rs.next()) {
         datos = new OCAPComponentesfqsOT();
         datos.setCExptecompfqsId(rs.getLong("C_EXP_ID"));
         datos.setCCompfqsId(rs.getLong("C_COMPFQS_ID"));
         datos.setFInformeCE(rs.getDate("F_INFORME_CE"));
         DecimalFormat formato = new DecimalFormat("000000");
         datos.setCodigoId("REX" + formato.format(datos.getCCompfqsId()));
         datos.setCodigoEvalId("EPR" + formato.format(datos.getCExptecompfqsId()));
 
         listado.add(datos);
 
         if (++i == cuantos)	break;
       }
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 
   public int contarEvaluados(long cCteId, long cPerfilId)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     StringBuffer consulta = new StringBuffer();
     int numRegistros = -1;
     try
     {
       consulta = new StringBuffer();
       consulta.append("select count(*) from ").append(" (select distinct(oec.c_exp_id), ocf.c_compfqs_id ").append("from ocap_compfqs_convocatorias occ, ocap_expedientescarrera oec, ocap_componentesfqs ocf ").append("WHERE occ.C_CTE_ID = '" + cCteId + "'").append("AND occ.C_COMPFQS_ID = ocf.C_COMPFQS_ID ").append("AND occ.C_COMPFQS_ID = oec.C_COMPFQS_ID ").append("AND ocf.C_PERFIL_ID = '" + cPerfilId + "'").append("AND oec.C_ITINERARIO_ID = occ.C_ITINERARIO_ID ").append("AND oec.C_CONVOCATORIA_ID = occ.C_CONVOCATORIA_ID ").append("AND oec.B_BORRADO='N' ").append("AND occ.B_BORRADO='N' ").append("AND ocf.B_BORRADO='N' ").append("AND oec.F_INFORME_CTE is not null ) ");
 
       st = con.prepareStatement(consulta.toString(), 1004, 1008);
 
       rs = st.executeQuery();
 
       if (rs.next())
         numRegistros = rs.getInt(1);
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return numRegistros;
   }
 
   public long buscarGerenciaCte(String dni)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     long c_gerencia_id = 0L;
     OCAPComponentesfqsOT datos = null;
     try
     {
       String sql = "SELECT cte.c_gerencia_id FROM OCAP_COMPONENTESFQS com, OCAP_COMPFQS_CONVOCATORIAS conv, OCAP_CTES cte WHERE com.C_DNI = '" + dni + "' " + " and com.C_PERFIL_ID = 4 " + "and com.B_BORRADO = 'N' " + "and com.C_COMPFQS_ID = conv.C_COMPFQS_ID " + "and conv.B_BORRADO = 'N' " + "and conv.C_CTE_ID = cte.C_CTE_ID " + "and cte.B_BORRADO ='N' ";
 
       st = con.prepareStatement(sql);
 
       rs = st.executeQuery();
 
       if (rs.next()) {
         c_gerencia_id = rs.getLong("c_gerencia_id");
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
 
     return c_gerencia_id;
   }
 
   public String buscarNombreCte(String dni)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     String nombrePresidente = "";
     try
     {
       String sql = "SELECT cte.d_nombre FROM OCAP_COMPONENTESFQS com, OCAP_COMPFQS_CONVOCATORIAS conv, OCAP_CTES cte WHERE com.C_DNI = '" + dni + "' " + " and com.C_PERFIL_ID = 4 " + "and com.B_BORRADO = 'N' " + "and com.C_COMPFQS_ID = conv.C_COMPFQS_ID " + "and conv.B_BORRADO = 'N' " + "and conv.C_CTE_ID = cte.C_CTE_ID " + "and cte.B_BORRADO ='N' ";
 
       st = con.prepareStatement(sql);
 
       rs = st.executeQuery();
 
       if (rs.next()) {
         nombrePresidente = rs.getString("d_nombre");
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
 
     return nombrePresidente;
   }
 }

