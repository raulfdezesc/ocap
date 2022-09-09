 package es.jcyl.fqs.ocap.oad.creditosCuestionarios;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.creditosCuestionarios.OCAPCreditosCuestionariosOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPCreditosCuestionariosOAD
 {
   public static Logger logger = OCAPConfigApp.logger;
   private static OCAPCreditosCuestionariosOAD instance;
 
   public static OCAPCreditosCuestionariosOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPCreditosCuestionariosOAD();
     }
     return instance;
   }
 
   public int altaOCAPCreditosCuestionarios(OCAPCreditosCuestionariosOT datos)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = null;
     int filas = 0;
     StringBuffer sql = new StringBuffer();
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       sql.append("INSERT INTO OCAP_CREDITOSCUESTIONARIOS ( ").append("C_CCUESTIONARIO_ID, B_BORRADO, C_EXP_ID, C_CUESTIONARIO_ID, N_REPETICION, N_CREDITOS, N_PUNTUACION, ").append("N_CREDITOS_EVALUADOR, N_CREDITOS_FINALES, B_ACUERDO, B_PROPOSICION, A_RAZON, A_COMENTARIOS, B_ACUERDO2E, A_RAZON2E, A_COMENTARIOS2E, ").append("B_AUDITORIA, A_TIPOREGISTRO, A_DOCUMENTO, A_NHISTORIA1, A_NHISTORIA2, A_NHISTORIA3, F_USUALTA, C_USUALTA, C_PADRE_EVIDENCIA_ID, N_EVIDENCIA) ").append("VALUES ( ").append("OCAP_CCU_ID_SEQ.nextval, 'N', ?,?, TO_NUMBER(?,'999999D99'), TO_NUMBER(?,'999999D99'), TO_NUMBER(?,'999999D99'), ").append("TO_NUMBER(?,'999999D99'), TO_NUMBER(?,'999999D99'), ?, ?, ?, ?, ").append("?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, ?, ? )");
 
       OCAPConfigApp.logger.info("Sentencia SQL: " + sql.toString());
       st = con.prepareStatement(sql.toString());
       int cont = 1;
       st.setLong(cont++, datos.getCExpId());
       st.setLong(cont++, datos.getCCuestionarioId());
       st.setInt(cont++, datos.getNRepeticion());
 
       double newNum = Math.round(datos.getNCreditos() * 100.0D) / 100.0D;
       float credito = (float)newNum;
       st.setFloat(cont++, credito);
 
       newNum = Math.round(datos.getNPuntuacion() * 100.0D) / 100.0D;
       credito = (float)newNum;
 
       st.setFloat(cont++, credito);
       if (datos.getNCreditosEvaluador() != 0.0F)
       {
         newNum = Math.round(datos.getNCreditosEvaluador() * 100.0D) / 100.0D;
         credito = (float)newNum;
         st.setFloat(cont++, credito);
       }
       else {
         st.setNull(cont++, 6);
       }if (datos.getNCreditosFinales() != 0.0F)
       {
         newNum = Math.round(datos.getNCreditosFinales() * 100.0D) / 100.0D;
         credito = (float)newNum;
         st.setFloat(cont++, credito);
       }
       else {
         st.setNull(cont++, 6);
       }st.setString(cont++, datos.getBAcuerdo());
       st.setString(cont++, datos.getBProposicion());
       st.setString(cont++, datos.getARazon());
       st.setString(cont++, datos.getAComentarios());
       st.setString(cont++, datos.getBAcuerdo2());
       st.setString(cont++, datos.getARazon2());
       st.setString(cont++, datos.getAComentarios2());
       st.setString(cont++, datos.getBAuditoria());
       st.setString(cont++, datos.getATipoRegistro());
       st.setString(cont++, datos.getADocumento());
       st.setString(cont++, datos.getANHistoria1());
       st.setString(cont++, datos.getANHistoria2());
       st.setString(cont++, datos.getANHistoria3());
       st.setString(cont++, datos.getACreadoPor());
       if (datos.getCPadreEvidenciaId() != 0L)
         st.setLong(cont++, datos.getCPadreEvidenciaId());
       else
         st.setNull(cont++, 2);
       st.setLong(cont++, datos.getNEvidencia());
 
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
 
   public int bajaOCAPCreditosCuestionarios(int cCCuestionariosId)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_CREDITOSCUESTIONARIOS SET B_BORRADO = 'Y' WHERE C_CCUESTIONARIO_ID = ?";
       st = con.prepareStatement(sql);
       st.setInt(1, cCCuestionariosId);
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
 
   public int borrarOCAPCreditosCuestionario(OCAPCreditosCuestionariosOT datos)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     int filas = 0;
     try
     {
       String sql = "DELETE OCAP_CREDITOSCUESTIONARIOS  WHERE C_CUESTIONARIO_ID = ?  AND C_EXP_ID = ?  AND N_REPETICION = ? ";
 
       if (datos.getCPadreEvidenciaId() != 0L)
         sql = sql + " AND C_PADRE_EVIDENCIA_ID = ? ";
       if (datos.getNEvidencia() != 0) {
         sql = sql + " AND N_EVIDENCIA = ? ";
       }
       int cont = 1;
       st = con.prepareStatement(sql);
       st.setLong(cont++, datos.getCCuestionarioId());
       st.setLong(cont++, datos.getCExpId());
       st.setInt(cont++, datos.getNRepeticion());
       if (datos.getCPadreEvidenciaId() != 0L)
         st.setLong(cont++, datos.getCPadreEvidenciaId());
       if (datos.getNEvidencia() != 0) {
         st.setInt(cont++, datos.getNEvidencia());
       }
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
 
   public int borrarOCAPCreditosCuestionarioExpId(long cExpId, Connection con)
     throws SQLException
   {
     PreparedStatement st = null;
     int filas = 0;
     try
     {
       String sql = "DELETE OCAP_CREDITOSCUESTIONARIOS WHERE C_EXP_ID = ? ";
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId);
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
 
   public int modificacionOCAPCreditosCuestionarios(OCAPCreditosCuestionariosOT datos, boolean bAutoponderacion)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     int filas = 0;
     Double creditosEvidencia = null;
     float credito = 0;
     String sql = "";
     try
     {
    	 if (bAutoponderacion) {
    		 //Si es autoponderacion, miramos si ya hay una evidencia rellenada y con puntuacion para ese cuestionario. Si es afirmativo, tenemos que sumar 
    		 //la puntuacion que nos llega ahora por parametro a lo que ya tenia la evidencia.
    		 //Posteriormente hacemos el update de la puntuacion del cuestionario (tal y como funcionaba antes)
    		 creditosEvidencia = tieneEvidenciaRellenada(datos, con);
    		 if (creditosEvidencia != null) {
    			 double creditosOriginales = Math.round(datos.getNCreditos() * 100.0D) / 100.0D;
    		     credito = (float)creditosOriginales;
    		     credito += creditosEvidencia;
    		     credito = (float) (Math.round(credito * 100.0D) / 100.0D);
    		     sql = "UPDATE OCAP_CREDITOSCUESTIONARIOS SET N_CREDITOS = TO_NUMBER(?,'999999D99'), F_USUMODI = SYSDATE WHERE N_REPETICION = ? AND C_EXP_ID = ? AND C_PADRE_EVIDENCIA_ID = ? ";
    		     st = con.prepareStatement(sql);
    		     st.setFloat(1, credito);
    		     st.setInt(2, datos.getNRepeticion());
    		     st.setLong(3, datos.getCExpId());
    		     st.setLong(4, datos.getCCuestionarioId());
    		     filas = st.executeUpdate();
    		     if (st != null)
    		         st.close();
    		 }
    		 
    	 } 
    	
       double newNum = Math.round(datos.getNCreditos() * 100.0D) / 100.0D;
       credito = (float)newNum;
       sql = "UPDATE OCAP_CREDITOSCUESTIONARIOS SET N_CREDITOS = TO_NUMBER(?,'999999D99'), F_USUMODI = SYSDATE WHERE N_REPETICION = ? AND C_EXP_ID = ? AND C_CUESTIONARIO_ID = ? ";
       
       st = con.prepareStatement(sql);
       st.setFloat(1, credito);
       st.setInt(2, datos.getNRepeticion());
       st.setLong(3, datos.getCExpId());
       st.setLong(4, datos.getCCuestionarioId());
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
 
   private Double tieneEvidenciaRellenada(OCAPCreditosCuestionariosOT datosEntrada, Connection con) {
		Double creditosEvidencia = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			String sql = " SELECT SUM (n_creditos) AS suma "  
					+ "                  FROM ocap_creditoscuestionarios "
					+ "                 WHERE     c_exp_id = ? "
					+ "                       AND c_padre_evidencia_id = ? "
					+ "                       AND c_cuestionario_id IN "
					+ "                              (SELECT c_evidencia_id "
					+ "                                 FROM itcp_evidencias_cuestionarios "
					+ "                                WHERE c_cuestionario_id = ?) ";

			st = con.prepareStatement(sql);
			st.setLong(1, datosEntrada.getCExpId());
			st.setLong(2, datosEntrada.getCCuestionarioId());
			st.setLong(3, datosEntrada.getCCuestionarioId());
			rs = st.executeQuery();
			while (rs.next()) {
				if (rs.getFloat("suma") != 0) {
					creditosEvidencia = new Double(rs.getFloat("suma"));
					creditosEvidencia = Math.round(creditosEvidencia * 100.0) / 100.0;
				}
			}
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
			} catch (Exception e) {
				OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			}
		}
  
  	return creditosEvidencia;
}

public ArrayList buscarOCAPCreditosCuestionario(long cCuestionarioId, int idRepeticion, long cExpId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     OCAPCreditosCuestionariosOT datos = null;
     ArrayList listado = null;
     Connection con = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       String sql = "SELECT C_CCUESTIONARIO_ID, B_BORRADO, C_EXP_ID, C_CUESTIONARIO_ID, N_REPETICION, N_PUNTUACION, N_CREDITOS, N_CREDITOS_EVALUADOR, N_CREDITOS_FINALES, B_ACUERDO, B_PROPOSICION, A_RAZON, A_COMENTARIOS, B_ACUERDO2E, A_RAZON2E, A_COMENTARIOS2E, B_AUDITORIA, A_TIPOREGISTRO, A_DOCUMENTO, A_NHISTORIA1, A_NHISTORIA2, A_NHISTORIA3  FROM OCAP_CREDITOSCUESTIONARIOS  WHERE C_CUESTIONARIO_ID = ?  AND B_BORRADO = 'N' AND C_EXP_ID = ? ";
 
       if (idRepeticion != 0) sql = sql + " AND N_REPETICION = ? "; else {
         sql = sql + " ORDER BY N_REPETICION ";
       }
       st = con.prepareStatement(sql);
       st.setLong(1, cCuestionarioId);
       st.setLong(2, cExpId);
       if (idRepeticion != 0) {
         st.setInt(3, idRepeticion);
       }
       rs = st.executeQuery();
       listado = new ArrayList();
       while (rs.next()) {
         datos = new OCAPCreditosCuestionariosOT();
         datos.setCCCuestionariosId(rs.getLong("C_CCUESTIONARIO_ID"));
         datos.setCExpId(rs.getLong("C_EXP_ID"));
         datos.setCCuestionarioId(rs.getLong("C_CUESTIONARIO_ID"));
         datos.setNRepeticion(rs.getInt("N_REPETICION"));
         datos.setNCreditos(rs.getFloat("N_CREDITOS"));
         datos.setNPuntuacion(rs.getFloat("N_PUNTUACION"));
         datos.setBBorrado(rs.getString("B_BORRADO"));
         datos.setNCreditosFinales(rs.getFloat("N_CREDITOS_FINALES"));
         datos.setNCreditosEvaluador(rs.getFloat("N_CREDITOS_EVALUADOR"));
 
         if ((rs.getString("N_CREDITOS_EVALUADOR") != null) && (rs.getString("B_ACUERDO") == null))
           datos.setBSimulado(true);
         else datos.setBSimulado(false);
         datos.setBAcuerdo(rs.getString("B_ACUERDO"));
         datos.setBProposicion(rs.getString("B_PROPOSICION"));
         datos.setARazon(rs.getString("A_RAZON"));
         datos.setAComentarios(rs.getString("A_COMENTARIOS"));
         datos.setBAcuerdo2(rs.getString("B_ACUERDO2E"));
         datos.setARazon2(rs.getString("A_RAZON2E"));
         datos.setAComentarios2(rs.getString("A_COMENTARIOS2E"));
         datos.setBAuditoria(rs.getString("B_AUDITORIA"));
         datos.setATipoRegistro(rs.getString("A_TIPOREGISTRO"));
         datos.setADocumento(rs.getString("A_DOCUMENTO"));
         datos.setANHistoria1(rs.getString("A_NHISTORIA1"));
         datos.setANHistoria2(rs.getString("A_NHISTORIA2"));
         datos.setANHistoria3(rs.getString("A_NHISTORIA3"));
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
 
   public ArrayList buscarOCAPCreditosCuestionariosPorExpId(long cExpId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     ArrayList listado = null;
     OCAPCreditosCuestionariosOT datos = null;
     try
     {
       String sql = "SELECT DISTINCT (cre.c_defmerito_id), cre.n_creditos creditos, exc.C_EXP_ID, defm.d_nombre d_defmerito_nombre,cc.N_CREDITOS, cc.C_CCUESTIONARIO_ID FROM OCAP_CREDITOSCUESTIONARIOS cc, ocap_creditos cre, ocap_categ_profesionales pro,ocap_def_meritoscurriculares defm,ocap_expedientescarrera exc,ocap_usuarios usu WHERE exc.C_EXP_ID = ?  AND pro.c_estatut_id = cre.c_estatut_id  AND defm.c_defmerito_id = cre.c_defmerito_id  AND cc.C_DEFMERITO_ID = defm.C_DEFMERITO_ID  AND exc.C_EXP_ID = cc.C_EXP_ID  AND exc.C_GRADO_ID = cre.c_grado_id  AND exc.C_USR_ID = usu.C_USR_ID  AND exc.C_PROFESIONAL_ID = pro.C_PROFESIONAL_ID  AND cre.b_borrado ='N'  AND pro.b_borrado = 'N'  AND defm.b_borrado = 'N'  AND exc.B_BORRADO = 'N'  AND cc.B_BORRADO = 'N'  AND usu.B_BORRADO = 'N'  ORDER BY cre.c_defmerito_id ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId);
       rs = st.executeQuery();
       listado = new ArrayList();
       while (rs.next()) {
         datos = new OCAPCreditosCuestionariosOT();
         datos.setCCCuestionariosId(rs.getLong("C_CCUESTIONARIO_ID"));
         datos.setCExpId(rs.getLong("C_EXP_ID"));
         datos.setNRepeticion(rs.getInt("N_REPETICION"));
         datos.setNCreditos(rs.getFloat("N_CREDITOS"));
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
 
   public ArrayList buscarOCAPPuntosCuestionariosArea(long cAreaId, long cExpId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     ArrayList listado = null;
     OCAPCreditosCuestionariosOT datos = null;
     try
     {
       String sql = " select distinct(ocr.C_CUESTIONARIO_ID),  (SELECT SUM (ocr_aux.N_CREDITOS) FROM ocap_creditoscuestionarios ocr_aux WHERE ocr_aux.c_exp_id=?      AND ocr_aux.C_CUESTIONARIO_ID in     (select c_cuestionario_id from itcp_cuestionarios ocu_aux          where c_cuestionario_id = ocr.c_cuestionario_id  OR c_cuestionario_padre_idp = ocr.c_cuestionario_id )   ) as n_creditos,  ocr.C_EXP_ID,  (select d_nombre from itcp_cuestionarios where c_cuestionario_id = ocr.c_cuestionario_id) as d_cuestionario,  (select d_nombre_largo from itcp_cuestionarios where c_cuestionario_id = ocr.c_cuestionario_id) as d_largo_cuestionario,  (select n_creditos*n_repeticiones from itcp_cuestionarios where c_cuestionario_id = ocr.c_cuestionario_id) as n_creditos_posibles  from ocap_creditoscuestionarios ocr  where ocr.c_exp_id = ?  AND ocr.B_BORRADO='N'  AND ocr.c_cuestionario_id IN   (select c_cuestionario_id from itcp_cuestionarios ocu where ocu.C_AREA_ID = ? AND c_cuestionario_padre_idp=0      AND c_cuestionario_id not in (select c_cuestionario_intermedio_idp from itcp_cuestionarios where c_area_id = ?  AND c_cuestionario_intermedio_idp is not null)   )  order by c_cuestionario_id ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId);
       st.setLong(2, cExpId);
       st.setLong(3, cAreaId);
       st.setLong(4, cAreaId);
       rs = st.executeQuery();
       listado = new ArrayList();
       while (rs.next()) {
         datos = new OCAPCreditosCuestionariosOT();
 
         datos.setCCuestionarioId(rs.getLong("C_CUESTIONARIO_ID"));
 
         datos.setCExpId(rs.getLong("C_EXP_ID"));
         datos.setNCreditos(rs.getFloat("N_CREDITOS"));
 
         datos.setDCuestionario(rs.getString("D_CUESTIONARIO") + " - " + rs.getString("d_largo_cuestionario"));
         datos.setNCreditosPosibles(rs.getFloat("N_CREDITOS_POSIBLES"));
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
 
   public int modificaOCAPCreditosCuestionarios(OCAPCreditosCuestionariosOT datos)
     throws SQLException
   {
     PreparedStatement st = null;
     int filas = 0;
     Connection con = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       String sql = "UPDATE OCAP_CREDITOSCUESTIONARIOS SET N_CREDITOS_EVALUADOR = TO_NUMBER(?,'999999D99'), N_CREDITOS_FINALES = TO_NUMBER(?,'999999D99'), B_ACUERDO = ?, B_PROPOSICION = ?, A_RAZON = ?, A_COMENTARIOS = ?, C_USUMODI = ?, F_USUMODI = SYSDATE WHERE N_REPETICION = ? AND C_EXP_ID = ? AND C_CUESTIONARIO_ID = ? ";
 
       st = con.prepareStatement(sql);
 
       double newNum = Math.round(datos.getNCreditosEvaluador() * 100.0D) / 100.0D;
       float credito = (float)newNum;
       st.setFloat(1, credito);
 
       newNum = Math.round(datos.getNCreditosFinales() * 100.0D) / 100.0D;
       credito = (float)newNum;
       st.setFloat(2, credito);
 
       st.setString(3, datos.getBAcuerdo());
       st.setString(4, datos.getBProposicion());
       st.setString(5, datos.getARazon());
       st.setString(6, datos.getAComentarios());
       st.setString(7, datos.getAModificadoPor());
       st.setInt(8, datos.getNRepeticion() == 0 ? 1 : datos.getNRepeticion());
       st.setLong(9, datos.getCExpId());
       st.setLong(10, datos.getCCuestionarioId());
 
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
 
   public int modificaOCAPCreditosCuestionarios2(OCAPCreditosCuestionariosOT datos)
     throws SQLException
   {
     PreparedStatement st = null;
     int filas = 0;
     Connection con = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       String sql = "UPDATE OCAP_CREDITOSCUESTIONARIOS SET B_ACUERDO2E = ?, A_RAZON2E = ?, A_COMENTARIOS2E = ?, C_USUMODI = ?, B_AUDITORIA = ?, A_TIPOREGISTRO = ?, A_DOCUMENTO = ?, A_NHISTORIA1 = ?, A_NHISTORIA2 = ?, A_NHISTORIA3 = ?, F_USUMODI = SYSDATE WHERE N_REPETICION = ? AND C_EXP_ID = ? AND C_CUESTIONARIO_ID = ? ";
 
       st = con.prepareStatement(sql);
 
       st.setString(1, datos.getBAcuerdo2());
       st.setString(2, datos.getARazon2());
       st.setString(3, datos.getAComentarios2());
       st.setString(4, datos.getAModificadoPor());
       st.setString(5, datos.getBAuditoria());
       st.setString(6, datos.getATipoRegistro());
       st.setString(7, datos.getADocumento());
       st.setString(8, datos.getANHistoria1());
       st.setString(9, datos.getANHistoria2());
       st.setString(10, datos.getANHistoria3());
       st.setInt(11, datos.getNRepeticion() == 0 ? 1 : datos.getNRepeticion());
       st.setLong(12, datos.getCExpId());
       st.setLong(13, datos.getCCuestionarioId());
 
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
 
   public int cuentaSegundasAuditorias(long cExpId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     int contador = 0;
     Connection con = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       String sql = "SELECT count(distinct(c_cuestionario_id)) as contadorAuditorias  FROM OCAP_CREDITOSCUESTIONARIOS  WHERE C_EXP_ID = ?  AND B_AUDITORIA ='Y' AND B_BORRADO='N'";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId);
       rs = st.executeQuery();
       if (rs.next())
         contador = rs.getInt("contadorAuditorias");
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
 
     return contador;
   }
 
   public ArrayList buscarOCAPAuditoriasPorExpId(long cExpId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     ArrayList listado = null;
     OCAPCreditosCuestionariosOT datos = null;
     try
     {
       String sql = "SELECT A_TIPOREGISTRO, A_DOCUMENTO, A_NHISTORIA1, A_NHISTORIA2, A_NHISTORIA3 FROM OCAP_CREDITOSCUESTIONARIOS WHERE C_EXP_ID = ?  AND B_AUDITORIA = 'Y'  AND B_BORRADO = 'N' ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId);
       rs = st.executeQuery();
       listado = new ArrayList();
       while (rs.next()) {
         datos = new OCAPCreditosCuestionariosOT();
         datos.setATipoRegistro(rs.getString("A_TIPOREGISTRO"));
         datos.setADocumento(rs.getString("A_DOCUMENTO"));
         datos.setANHistoria1(rs.getString("A_NHISTORIA1"));
         datos.setANHistoria2(rs.getString("A_NHISTORIA2"));
         datos.setANHistoria3(rs.getString("A_NHISTORIA3"));
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

