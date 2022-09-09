package es.jcyl.fqs.ocap.oad.meritosCurriculares;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.expedienteMC.OCAPExpedientemcOT;
import es.jcyl.fqs.ocap.ot.meritosCurriculares.OCAPMeritoscurricularesOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
import es.jcyl.framework.db.JCYLGestionTransacciones;

public class OCAPMeritoscurricularesOAD {
	public Logger loggerBD;
	public static Logger logger = OCAPConfigApp.logger;
	private static OCAPMeritoscurricularesOAD instance;

	private void $init$() {
		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public static OCAPMeritoscurricularesOAD getInstance() {
		if (instance == null) {
			instance = new OCAPMeritoscurricularesOAD();
		}
		return instance;
	}

	private OCAPMeritoscurricularesOAD() {
		$init$();
	}

	public int bajaOCAPMeritoscurriculares(int cMeritoId) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			String sql = "UPDATE OCAP_MERITOSCURRICULARES SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_MERITO_ID =  ?";
			st = con.prepareStatement(sql);
			st.setInt(1, cMeritoId);
			filas = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
	}

	public int modificacionOCAPMeritoscurriculares(OCAPMeritoscurricularesOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			String sql = "UPDATE OCAP_MERITOSCURRICULARES SET C_DEFMERITO_ID = ?, C_ESTATUT_ID = ?, C_ACTIVIDAD_ID = ?, C_MODALIDAD_ID = ?, C_FACTOR_ID = ?, C_TIPO_ID = ?, D_NOMBRECORTO = ?, D_NOMBRE = ?, D_DESCRIPCION = ?, D_OBSERVACIONES = ?, N_CREDITOS = ?, F_USUMODI = SYSDATE WHERE C_MERITO_ID = ?";

			st = con.prepareStatement(sql);
			st.setLong(1, datos.getCDefmeritoId());
			st.setLong(2, datos.getCEstatutId());

			if (datos.getCActividadId() != 0L)
				st.setLong(3, datos.getCActividadId());
			else {
				st.setNull(3, 2);
			}
			if (datos.getCModalidadId() != 0L)
				st.setLong(4, datos.getCModalidadId());
			else {
				st.setNull(4, 2);
			}
			if (datos.getCFactorId() != 0L)
				st.setLong(5, datos.getCFactorId());
			else {
				st.setNull(5, 2);
			}
			if (datos.getCTipoId() != 0L)
				st.setLong(6, datos.getCTipoId());
			else {
				st.setNull(6, 2);
			}
			st.setString(7, datos.getDNombrecorto());
			st.setString(8, datos.getDNombre());

			if (datos.getDDescripcion() != null)
				st.setString(9, datos.getDDescripcion());
			else {
				st.setNull(9, 12);
			}
			if (datos.getDObservaciones() != null)
				st.setString(10, datos.getDObservaciones());
			else {
				st.setNull(10, 12);
			}
			st.setFloat(11, datos.getNCreditos());
			st.setLong(12, datos.getCMeritoId());

			filas = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
	}

	public OCAPMeritoscurricularesOT buscarOCAPMeritoscurriculares(long cMeritoId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		OCAPMeritoscurricularesOT datos = null;
		try {
			String sql = "SELECT C_MERITO_ID, C_DEFMERITO_ID, C_ESTATUT_ID, C_ACTIVIDAD_ID, C_MODALIDAD_ID, C_FACTOR_ID, C_TIPO_ID, C_TIPO_MERITO, D_NOMBRECORTO, D_NOMBRE, D_DESCRIPCION, D_OBSERVACIONES, N_CREDITOS, F_USUALTA, F_USUMODI, B_ACREDITADO FROM OCAP_MERITOSCURRICULARES WHERE C_MERITO_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cMeritoId);
			rs = st.executeQuery();
			datos = new OCAPMeritoscurricularesOT();
			if (rs.next()) {
				datos.setCMeritoId(cMeritoId);
				datos.setCDefmeritoId(rs.getLong("C_DEFMERITO_ID"));
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setCActividadId(rs.getLong("C_ACTIVIDAD_ID"));
				datos.setCModalidadId(rs.getLong("C_MODALIDAD_ID"));
				datos.setCFactorId(rs.getLong("C_FACTOR_ID"));
				datos.setCTipoId(rs.getLong("C_TIPO_ID"));
				datos.setCTipoMerito(rs.getString("C_TIPO_MERITO"));
				datos.setDNombrecorto(rs.getString("D_NOMBRECORTO"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
				datos.setNCreditos(rs.getFloat("N_CREDITOS"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setBAcreditado(rs.getString("B_ACREDITADO"));
			}
		} catch (SQLException ex) {
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

	public ArrayList buscarOCAPMeritoscurricularesPorDefMeritoIdYEstatutId(Integer cDefMeritoId, Integer cEstatutId)
			throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		OCAPMeritoscurricularesOT datos = null;
		try {
			String sql = "SELECT distinct(D_MERITO_NOMBRE), F_USUALTA, C_DEFMERITO_ID, D_MERITO_DESC, D_MERITO_OBSERV, B_BORRADO, F_USUMODI, C_ESTATUT_ID FROM OCAP_MERITOSCURRICULARES WHERE C_DEFMERITO_ID = ?  AND C_ESTATUT_ID = ?";

			st = con.prepareStatement(sql);
			st.setInt(1, cDefMeritoId.intValue());
			st.setInt(2, cEstatutId.intValue());

			rs = st.executeQuery();
			listado = new ArrayList();
			while (rs.next()) {
				datos = new OCAPMeritoscurricularesOT();
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setCDefmeritoId(rs.getLong("C_DEFMERITO_ID"));
				datos.setDDescripcion(rs.getString("D_MERITO_DESC"));
				datos.setDObservaciones(rs.getString("D_MERITO_OBSERV"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setDNombre(rs.getString("D_MERITO_NOMBRE"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				listado.add(datos);
			}
		} catch (SQLException ex) {
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

	public OCAPMeritoscurricularesOT buscarOCAPMeritoscurricularesPorUsuarioOT(OCAPUsuariosOT usuarioOT,
			Long cDefMeritoId, String bOpcionales, JCYLUsuario jcylUsuario, boolean buscaCV)
			throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		Connection con = JCYLGestionTransacciones.getConnection();
		OCAPMeritoscurricularesOT datosConjunto = new OCAPMeritoscurricularesOT();
		try {
			this.loggerBD.info(" buscarOCAPMeritoscurricularesPorUsuarioOT");

			StringBuffer sql = new StringBuffer();
				sql.append("SELECT ")

				//OCAP-1433
					.append("CASE" + 
							"            WHEN (mecuTotal.c_tipo_merito IN" + 
							"                    (SELECT DISTINCT C_TIPO_MERITO" + 
							"                       FROM OCAP_MERITOSCURRICULARES" + 
							"                      WHERE     C_MERITO_ID IN" + 
							"                                   (SELECT DISTINCT C_MERITO_ID" + 
							"                                      FROM ocap_expedientesmcs" + 
							"                                     WHERE     C_EXP_ID IN" + 
							"                                                  (SELECT C_EXP_ID" + 
							"                                                     FROM OCAP_EXPEDIENTESCARRERA" + 
							"                                                    WHERE     C_USR_ID IN" + 
							"                                                                 (SELECT C_USR_ID" + 
							"                                                                    FROM OCAP_EXPEDIENTESCARRERA" + 
							"                                                                   WHERE C_EXP_ID =" + 
							"                                                                            ?)" + 
							"                                                          AND C_ESTADO_ID = 12" + 
							"                                                          AND C_EXP_ID <>" + 
							"                                                                 ?)" + 
							"                                           AND B_BORRADO = 'N'" + 
							"                                           AND B_OPCIONAL = ? )" + 
							"                            AND C_DEFMERITO_ID = mecuTotal.c_defmerito_id)" + 
							"                            AND mecuTotal.d_merito IN " + 
							"                            (SELECT DISTINCT D_NOMBRE" + 
							"                       FROM OCAP_MERITOSCURRICULARES" + 
							"                      WHERE     C_MERITO_ID IN" + 
							"                                   (SELECT DISTINCT C_MERITO_ID" + 
							"                                      FROM ocap_expedientesmcs" + 
							"                                     WHERE     C_EXP_ID IN" + 
							"                                                  (SELECT C_EXP_ID" + 
							"                                                     FROM OCAP_EXPEDIENTESCARRERA" + 
							"                                                    WHERE     C_USR_ID IN" + 
							"                                                                 (SELECT C_USR_ID" + 
							"                                                                    FROM OCAP_EXPEDIENTESCARRERA" + 
							"                                                                   WHERE C_EXP_ID =" + 
							"                                                                            ?)" + 
							"                                                          AND C_ESTADO_ID = 12" + 
							"                                                          AND C_EXP_ID <>" + 
							"                                                                 ?)" + 
							"                                           AND B_BORRADO = 'N'" + 
							"                                           AND B_OPCIONAL = ? )" +  
							"                            AND C_DEFMERITO_ID = mecuTotal.c_defmerito_id)" + 
							"                            )" + 
							"            THEN" + 
							"               'S'" + 
							"            ELSE" + 
							"               'N'" + 
							"         END AS meritosAnterioresValidados,")
					//FIN OCAP-1433
					
					.append("expmcAgrup.c_exp_id, mecuTotal.c_defmerito_id, expmcAgrup.c_merito_id, mecuTotal.c_estatut_id, ")
					.append(" mecuTotal.c_tipo_merito, mecuTotal.d_merito, expmcAgrup.d_merito_corto, ")
					.append(" mecuTotal.d_descripcion, mecuTotal.d_observaciones, ")
					.append(" expmcAgrup.c_mer_actividad_mc, expmcAgrup.d_mer_actividad_mc, ")
					.append(" expmcAgrup.c_mer_modalidad_mc, expmcAgrup.c_factor_impacto_mc, expmcAgrup.c_tipo_firmante_mc, ")
					.append(" expmcAgrup.n_creditos_mc, expmcAgrup.b_acreditado_mc, ")
					.append(" expmcAgrup.c_expmc_id, expmcAgrup.d_expmc, expmcAgrup.b_opcional_emc,  ")
					.append(" TO_CHAR(expmcAgrup.f_inicio,").append(Constantes.FECHA_CORTA).append(")f_inicio, ")
					.append(" TO_CHAR(expmcAgrup.f_finalizacion,").append(Constantes.FECHA_CORTA).append(")f_finalizacion, ")
					.append(" TO_CHAR(expmcAgrup.f_expedicion,").append(Constantes.FECHA_CORTA).append(")f_expedicion, ")
					.append(" expmcAgrup.CLOB_ORGANIZADOR, expmcAgrup.a_lugar_expedicion, ")
					.append(" expmcAgrup.f_annio, expmcAgrup.n_sesiones, ")
					.append(" expmcAgrup.n_horas, expmcAgrup.n_dias, expmcAgrup.n_annios, ")
					.append(" expmcAgrup.b_participacion, expmcAgrup.a_nombre_revista, ")
					.append(" expmcAgrup.a_cargo, expmcAgrup.a_objetivo, ")
					.append(" expmcAgrup.n_meses, expmcAgrup.n_cred_curso, ")
					.append(" expmcAgrup.n_isbn, expmcAgrup.d_editorial, expmcAgrup.d_capitulo, ")
					.append(" expmcAgrup.b_acreditado_emc, expmcAgrup.n_cred_usuario, ")
					.append(" expmcAgrup.n_cred_ceis, expmcAgrup.b_invalidado_ceis, expmcAgrup.b_pdte_aclarar_ceis, ")
					.append(" expmcAgrup.n_cred_cc, expmcAgrup.b_invalidado_cc, expmcAgrup.b_pdte_aclarar_cc,expmcAgrup.D_MOTIVOS_CEIS,expmcAgrup.D_MOTIVOS_CC  ")
					.append(" FROM ( ")
					.append(" SELECT DISTINCT(mecuaux.d_nombre)d_merito, mecuaux.c_tipo_merito, mecuaux.c_defmerito_id, mecuaux.c_estatut_id, ")
					.append(" mecuaux.d_descripcion, mecuaux.d_observaciones, mecuaux.n_orden, mecuaux.b_borrado ")
					.append(" FROM ocap_meritoscurriculares mecuaux ").append(" WHERE mecuaux.c_defmerito_id = ? ")
					.append(" AND mecuaux.c_estatut_id = ? ").append(" AND mecuaux.b_borrado = 'N' ")
					.append(" ORDER BY mecuaux.n_orden asc)mecuTotal, ").append(" ( ")
					.append(" SELECT exmc.c_exp_id, exmc.c_merito_id, mecu.d_nombrecorto d_merito_corto, mecu.d_nombre d_merito, ")
					.append(" mecu.c_actividad_id c_mer_actividad_mc, meac.d_nombre d_mer_actividad_mc, mecu.c_modalidad_id c_mer_modalidad_mc, mecu.c_factor_id c_factor_impacto_mc, mecu.c_tipo_id c_tipo_firmante_mc, ")
					.append(" mecu.n_creditos n_creditos_mc, mecu.b_acreditado b_acreditado_mc, exmc.c_expmc_id,  exmc.d_titulo d_expmc, exmc.b_opcional b_opcional_emc, ")
					.append(" exmc.f_inicio, exmc.f_finalizacion, exmc.f_expedicion, exmc.CLOB_ORGANIZADOR, exmc.a_lugar_expedicion, ")
					.append(" exmc.f_annio, exmc.n_sesiones, exmc.n_horas, exmc.n_dias, exmc.n_annios, ")
					.append(" exmc.b_participacion, exmc.a_nombre_revista, exmc.a_cargo, exmc.a_objetivo, ")
					.append(" exmc.n_meses, exmc.n_cred_curso, exmc.n_isbn, exmc.d_editorial, exmc.d_capitulo, ")
					.append(" exmc.b_acreditado b_acreditado_emc, exmc.n_cred_usuario, exmc.n_cred_ceis, exmc.b_invalidado_ceis, exmc.b_pdte_aclarar_ceis, ")
					.append(" exmc.n_cred_cc, exmc.b_invalidado_cc, exmc.b_pdte_aclarar_cc, exmc.D_MOTIVOS_CEIS ,exmc.D_MOTIVOS_CC  ")
					.append(" FROM ocap_expedientesmcs exmc, ocap_meritoscurriculares mecu, ocap_meractividades meac ")
					.append(" WHERE exmc.c_exp_id = ? ").append(" AND c_defmerito_id = ? ")
					.append(" AND c_estatut_id = ? ").append(" AND exmc.b_opcional = ? ");

			if (buscaCV) {
				sql.append(" AND (exmc.b_participacion IS NULL OR exmc.b_participacion = '").append(Constantes.SI).append("') ");
			}
			sql.append(" AND exmc.c_merito_id = mecu.c_merito_id ")
					.append(" AND mecu.c_actividad_id = meac.c_actividad_id(+) ").append(" AND mecu.b_borrado = 'N' ")
					.append(" AND exmc.b_borrado = 'N' ").append(" ORDER BY mecu.n_orden ASC)expmcAgrup ")
					.append(" WHERE mecuTotal.d_merito = expmcAgrup.d_merito (+) ")
					.append(" ORDER BY mecuTotal.n_orden asc, expmcAgrup.b_acreditado_emc desc, expmcAgrup.c_mer_modalidad_mc asc, expmcAgrup.c_expmc_id asc ");

			this.loggerBD.info("buscarOCAPMeritoscurricularesPorUsuarioOT SQL: " + sql.toString());

			st = con.prepareStatement(sql.toString(), 1004, 1007);
			
			//OCAP-1433
			st.setLong(i++, usuarioOT.getCExpId().longValue());
			st.setLong(i++, usuarioOT.getCExpId().longValue());
			st.setString(i++, bOpcionales);
			st.setLong(i++, usuarioOT.getCExpId().longValue());
			st.setLong(i++, usuarioOT.getCExpId().longValue());
			st.setString(i++, bOpcionales);
			//FIN OCAP-1433
			
			st.setLong(i++, cDefMeritoId.longValue());
			st.setInt(i++, usuarioOT.getCEstatutId().intValue());
			st.setLong(i++, usuarioOT.getCExpId().longValue());
			st.setLong(i++, cDefMeritoId.longValue());
			st.setInt(i++, usuarioOT.getCEstatutId().intValue());
			st.setString(i++, bOpcionales);

			rs = st.executeQuery();
			
			datosConjunto = rellenarDatosConResultSet(rs, jcylUsuario, buscaCV, true);
			
			
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return datosConjunto;
	}

	private OCAPMeritoscurricularesOT rellenarDatosConResultSet(ResultSet rs, JCYLUsuario jcylUsuario, boolean buscaCV, boolean botonMeritosAnteriores) throws SQLException, UnsupportedEncodingException {
		OCAPMeritoscurricularesOT datosConjunto = new OCAPMeritoscurricularesOT();
		
		try {
			ArrayList listadoMC = new ArrayList();
			ArrayList listadoExpMC = new ArrayList();
			ArrayList listadoExpMCCTSP = new ArrayList();
			ArrayList listadoExpMCCTSNP = new ArrayList();
			ArrayList listadoExpMCCTSM = new ArrayList();
	
			int contadorCapitulos = 0;
			float creditosTot = 0.0F;
			float creditosTotValidadosCeis = 0.0F;
			float creditosTotValidadosCc = 0.0F;
	
			int horasTotCTSP = 0;
			int horasTotValidadasCeisCTSP = 0;
			int horasTotValidadasCcCTSP = 0;
			float creditosTotCTSP = 0.0F;
			float creditosTotValidadosCeisCTSP = 0.0F;
			float creditosTotValidadosCcCTSP = 0.0F;
	
			int horasTotCTSNP = 0;
			int horasTotValidadasCeisCTSNP = 0;
			int horasTotValidadasCcCTSNP = 0;
			float creditosTotCTSNP = 0.0F;
			float creditosTotValidadosCeisCTSNP = 0.0F;
			float creditosTotValidadosCcCTSNP = 0.0F;
	
			int horasTotCTSM = 0;
			int horasTotValidadasCeisCTSM = 0;
			int horasTotValidadasCcCTSM = 0;
			float creditosTotCTSM = 0.0F;
			float creditosTotValidadosCeisCTSM = 0.0F;
			float creditosTotValidadosCcCTSM = 0.0F;
	
			boolean bCalcularCreditosCTSNoAcre = false;
			float numCreditosHoraCTSP = 0.0F;
			float numCreditosHoraCTSNP = 0.0F;
			float numCreditosHoraCTSM = 0.0F;
			boolean siCorrido = false;
			String meritoRecorrido = "";
			String tipoMeritoRecorrido = "";
			OCAPMeritoscurricularesOT datos = null;
	
			while (rs.next()) {
				siCorrido = true;
	
				if (!meritoRecorrido.equals(rs.getString("d_merito"))) {
					if ((!meritoRecorrido.equals("")) && (!meritoRecorrido.equals(rs.getString("d_merito")))) {
						if ((Constantes.MC_TALLER.equals(tipoMeritoRecorrido)) && (bCalcularCreditosCTSNoAcre)) {
							creditosTotCTSP = (float) (Math.floor(horasTotCTSP / 10) * numCreditosHoraCTSP);
							creditosTotCTSNP = (float) (Math.floor(horasTotCTSNP / 10) * numCreditosHoraCTSNP);
							creditosTotCTSM = (float) (Math.floor(horasTotCTSM / 10) * numCreditosHoraCTSM);
							creditosTot = creditosTot + creditosTotCTSP + creditosTotCTSNP + creditosTotCTSM;
	
							creditosTotValidadosCeisCTSP = (float) (Math.floor(horasTotValidadasCeisCTSP / 10)
									* numCreditosHoraCTSP);
							creditosTotValidadosCeisCTSNP = (float) (Math.floor(horasTotValidadasCeisCTSNP / 10)
									* numCreditosHoraCTSNP);
							creditosTotValidadosCeisCTSM = (float) (Math.floor(horasTotValidadasCeisCTSM / 10)
									* numCreditosHoraCTSM);
							creditosTotValidadosCeis = creditosTotValidadosCeis + creditosTotValidadosCeisCTSP
									+ creditosTotValidadosCeisCTSNP + creditosTotValidadosCeisCTSM;
	
							creditosTotValidadosCcCTSP = (float) (Math.floor(horasTotValidadasCcCTSP / 10)
									* numCreditosHoraCTSP);
							creditosTotValidadosCcCTSNP = (float) (Math.floor(horasTotValidadasCcCTSNP / 10)
									* numCreditosHoraCTSNP);
							creditosTotValidadosCcCTSM = (float) (Math.floor(horasTotValidadasCcCTSM / 10)
									* numCreditosHoraCTSM);
							creditosTotValidadosCc = creditosTotValidadosCc + creditosTotValidadosCcCTSP
									+ creditosTotValidadosCcCTSNP + creditosTotValidadosCcCTSM;
	
							creditosTotCTSP = (float) (Math.rint(creditosTotCTSP * 100.0F) / 100.0D);
							creditosTotCTSNP = (float) (Math.rint(creditosTotCTSNP * 100.0F) / 100.0D);
							creditosTotCTSM = (float) (Math.rint(creditosTotCTSM * 100.0F) / 100.0D);
	
							creditosTotValidadosCeisCTSP = (float) (Math.rint(creditosTotValidadosCeisCTSP * 100.0F)
									/ 100.0D);
							creditosTotValidadosCeisCTSNP = (float) (Math.rint(creditosTotValidadosCeisCTSNP * 100.0F)
									/ 100.0D);
							creditosTotValidadosCeisCTSM = (float) (Math.rint(creditosTotValidadosCeisCTSM * 100.0F)
									/ 100.0D);
	
							creditosTotValidadosCcCTSP = (float) (Math.rint(creditosTotValidadosCcCTSP * 100.0F)
									/ 100.0D);
							creditosTotValidadosCcCTSNP = (float) (Math.rint(creditosTotValidadosCcCTSNP * 100.0F)
									/ 100.0D);
							creditosTotValidadosCcCTSM = (float) (Math.rint(creditosTotValidadosCcCTSM * 100.0F)
									/ 100.0D);
	
							datos.setCreditosCTSP(Float.valueOf(creditosTotCTSP));
							datos.setCreditosCTSNP(Float.valueOf(creditosTotCTSNP));
							datos.setCreditosCTSM(Float.valueOf(creditosTotCTSM));
	
							datos.setCreditosValidadosCeis(Float.valueOf(creditosTotValidadosCeis));
							datos.setCreditosValidadosCeisCTSP(Float.valueOf(creditosTotValidadosCeisCTSP));
							datos.setCreditosValidadosCeisCTSNP(Float.valueOf(creditosTotValidadosCeisCTSNP));
							datos.setCreditosValidadosCeisCTSM(Float.valueOf(creditosTotValidadosCeisCTSM));
	
							datos.setCreditosValidadosCc(Float.valueOf(creditosTotValidadosCc));
							datos.setCreditosValidadosCcCTSP(Float.valueOf(creditosTotValidadosCcCTSP));
							datos.setCreditosValidadosCcCTSNP(Float.valueOf(creditosTotValidadosCcCTSNP));
							datos.setCreditosValidadosCcCTSM(Float.valueOf(creditosTotValidadosCcCTSM));
						}
	
						if (buscaCV) {
							if (listadoExpMC.size() != 0)
								datos.setListaExpmc(listadoExpMC);
							if (listadoExpMCCTSP.size() != 0)
								datos.setListaExpmcCTSP(listadoExpMCCTSP);
							if (listadoExpMCCTSNP.size() != 0)
								datos.setListaExpmcCTSNP(listadoExpMCCTSNP);
							if (listadoExpMCCTSM.size() != 0)
								datos.setListaExpmcCTSM(listadoExpMCCTSM);
							if ((listadoExpMC.size() != 0) || (listadoExpMCCTSP.size() != 0)
									|| (listadoExpMCCTSNP.size() != 0) || (listadoExpMCCTSM.size() != 0))
								listadoMC.add(datos);
						} else {
							datos.setListaExpmc(listadoExpMC);
							datos.setListaExpmcCTSP(listadoExpMCCTSP);
							datos.setListaExpmcCTSNP(listadoExpMCCTSNP);
							datos.setListaExpmcCTSM(listadoExpMCCTSM);
							listadoMC.add(datos);
						}
	
						contadorCapitulos = 0;
						listadoExpMC = new ArrayList();
						listadoExpMCCTSP = new ArrayList();
						listadoExpMCCTSNP = new ArrayList();
						listadoExpMCCTSM = new ArrayList();
					}
	
					datos = new OCAPMeritoscurricularesOT();
					datos.setCDefmeritoId(rs.getLong("c_defmerito_id"));
					datos.setDDescripcion(rs.getString("d_descripcion"));
					datos.setDObservaciones(rs.getString("d_observaciones"));
					datos.setDNombre(rs.getString("d_merito"));
					datos.setCTipoMerito(rs.getString("c_tipo_merito"));
					if(botonMeritosAnteriores) {
						datos.setMeritosAnterioresValidados(rs.getString("meritosAnterioresValidados"));
					}
					
	
					meritoRecorrido = rs.getString("d_merito");
					tipoMeritoRecorrido = rs.getString("c_tipo_merito");
				}
	
				if (rs.getLong("c_expmc_id") != 0L) {
					OCAPExpedientemcOT expedienteMC = new OCAPExpedientemcOT();
					expedienteMC.setCExpmcId(Long.valueOf(rs.getLong("c_expmc_id")));
	
					if ((rs.getString("d_expmc") != null) && (!rs.getString("d_expmc").equals("")))
						expedienteMC.setDTitulo(rs.getString("d_expmc"));
					else if ((rs.getString("f_annio") != null) && (!rs.getString("f_annio").equals(""))) {
						if ((rs.getString("c_tipo_merito").equals("DocenciaPre"))
								|| (rs.getString("c_tipo_merito").equals("DocenciaPost")))
							expedienteMC.setDTitulo(rs.getString("d_mer_actividad_mc"));
						else
							expedienteMC.setDTitulo("Año " + rs.getString("f_annio"));
					} else if ((rs.getString("d_mer_actividad_mc") != null)
							&& (!rs.getString("d_mer_actividad_mc").equals("")))
						expedienteMC.setDTitulo(rs.getString("d_mer_actividad_mc"));
					else if ((rs.getString("b_participacion") != null)
							&& (!rs.getString("b_participacion").equals(""))) {
						if (rs.getString("b_participacion").equals(Constantes.SI))
							expedienteMC.setDTitulo(Constantes.SI_TEXTO_min);
						else
							expedienteMC.setDTitulo(Constantes.NO_TEXTO_min);
					} else if (rs.getString("c_tipo_merito").equals(Constantes.MC_BECA))
						expedienteMC.setDTitulo("Beca en " + rs.getString("a_lugar_expedicion"));
					else if (rs.getString("c_tipo_merito").equals(Constantes.MC_SESION_CLINICA))
						expedienteMC.setDTitulo("Sesiones de los últimos 12 meses ");
					else if (rs.getString("c_tipo_merito").equals(Constantes.MC_CENTINELA))
						expedienteMC.setDTitulo(Constantes.MC_CENTINELA);
					else if (rs.getString("c_tipo_merito").equals(Constantes.MC_COORDINA_EAP))
						expedienteMC.setDTitulo("Atención primaria");
					else if (rs.getString("c_tipo_merito").equals("JefeServicio"))
						expedienteMC.setDTitulo("Jefe de Servicio");
					else if (rs.getString("c_tipo_merito").equals(Constantes.MC_RES_CARTERA_SERVICIOS))
						expedienteMC.setDTitulo("Responsable");
					else if (rs.getString("c_tipo_merito").equals("Bibliograficas"))
						expedienteMC.setDTitulo("Por cada 5 horas acreditadas ");
					else {
						expedienteMC.setDTitulo("-");
					}
	
					expedienteMC.setDTitulo(Cadenas.cortarCadena(expedienteMC.getDTitulo(), 50));
					
					expedienteMC.setDTituloCodificado(URLEncoder.encode(expedienteMC.getDTitulo(), "UTF-8"));
					
					expedienteMC.setBParticipacion(rs.getString("b_participacion"));
					expedienteMC.setBInvalidadoCeis(rs.getString("b_invalidado_ceis"));
					expedienteMC.setBPdteAclararCeis(rs.getString("b_pdte_aclarar_ceis"));
					expedienteMC.setBInvalidadoCc(rs.getString("b_invalidado_cc"));
					expedienteMC.setBPdteAclararCc(rs.getString("b_pdte_aclarar_cc"));
	
					if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
						expedienteMC.setBPdteAclarar(expedienteMC.getBPdteAclararCeis());
						expedienteMC.setBInvalidado(expedienteMC.getBInvalidadoCeis());
						if(rs.getInt("n_horas") != 0) {
							expedienteMC.setDMotivosCeis(rs.getString("D_MOTIVOS_CC"));
						}else {
							if(rs.getString("D_MOTIVOS_CEIS")!=null)
								expedienteMC.setDMotivosCeis(rs.getString("D_MOTIVOS_CEIS"));
							else
								expedienteMC.setDMotivosCeis("");
						}
					} else {
						expedienteMC.setBPdteAclarar(expedienteMC.getBPdteAclararCc());
						expedienteMC.setBInvalidado(expedienteMC.getBInvalidadoCc());
						if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {
							if(rs.getString("D_MOTIVOS_CC")!=null) {
								String mot;
								if(rs.getInt("n_horas") != 0) {
									mot =rs.getString("D_MOTIVOS_CC");
								}else {
									mot ="CC: "+rs.getString("D_MOTIVOS_CC");
								}
								
								if(rs.getString("D_MOTIVOS_CEIS")!=null) {
									mot  =mot+ ". CEIS: "+ rs.getString("D_MOTIVOS_CEIS");
								}
								expedienteMC.setDMotivosCc(mot);
							}else {
								if(rs.getString("D_MOTIVOS_CEIS")!=null) {
									expedienteMC.setDMotivosCc("CEIS: "+ rs.getString("D_MOTIVOS_CEIS") );
								}else {
									expedienteMC.setDMotivosCc("");
								}
							}
						}
					}
					
					if (rs.getLong("c_exp_id") != 0L) {
						expedienteMC.setCExpId(rs.getLong("c_exp_id"));
					}
					if (rs.getString("b_opcional_emc") != null) {
						expedienteMC.setBOpcional(rs.getString("b_opcional_emc"));
					}
					expedienteMC.setCActividadId(Integer.valueOf(rs.getInt("c_mer_actividad_mc")));
					expedienteMC.setCModalidadId(Integer.valueOf(rs.getInt("c_mer_modalidad_mc")));
					expedienteMC.setFInicio(rs.getString("f_inicio"));
					expedienteMC.setFFinalizacion(rs.getString("f_finalizacion"));
					expedienteMC.setFExpedicion(rs.getString("f_expedicion"));
					expedienteMC.setALugarExpedicion(rs.getString("a_lugar_expedicion"));
					expedienteMC.setFAnnio(Integer.valueOf(rs.getInt("f_annio")));
					expedienteMC.setNAnnios(Integer.valueOf(rs.getInt("n_annios")));
					expedienteMC.setNMeses(Integer.valueOf(rs.getInt("n_meses")));
					expedienteMC.setNHoras(Integer.valueOf(rs.getInt("n_horas")));
					expedienteMC.setNDias(Integer.valueOf(rs.getInt("n_dias")));
					expedienteMC.setNSesiones(Integer.valueOf(rs.getInt("n_sesiones")));
					expedienteMC.setCTipoMerito(rs.getString("c_tipo_merito"));
					expedienteMC.setAOrganizador(rs.getString("CLOB_ORGANIZADOR"));
					expedienteMC.setBAcreditado(rs.getString("b_acreditado_emc"));
					expedienteMC.setCTipoId(Integer.valueOf(rs.getInt("c_tipo_firmante_mc")));
					expedienteMC.setCFactorId(Integer.valueOf(rs.getInt("c_factor_impacto_mc")));
					expedienteMC.setCEstatutId(Integer.valueOf(rs.getInt("c_estatut_id")));
	
					float creditosMerito = 0.0F;
					float creditosCeisMerito = 0.0F;
					float creditosCcMerito = 0.0F;
	
					if ((Constantes.MC_TALLER.equals(expedienteMC.getCTipoMerito())) && (Constantes.NO.equals(expedienteMC.getBAcreditado()))
							&& (expedienteMC.getCModalidadId().intValue() == 1)) {
						horasTotCTSP += expedienteMC.getNHoras().intValue();
						numCreditosHoraCTSP = rs.getFloat("n_creditos_mc");
						if ((expedienteMC.getBInvalidadoCeis() == null)
								|| (!Constantes.SI.equals(expedienteMC.getBInvalidadoCeis()))) {
							horasTotValidadasCeisCTSP += expedienteMC.getNHoras().intValue();
						}
						if ((expedienteMC.getBInvalidadoCc() == null)
								|| (!Constantes.SI.equals(expedienteMC.getBInvalidadoCc()))) {
							horasTotValidadasCcCTSP += expedienteMC.getNHoras().intValue();
						}
						listadoExpMCCTSP.add(expedienteMC);
						bCalcularCreditosCTSNoAcre = true;
					} else if ((Constantes.MC_TALLER.equals(expedienteMC.getCTipoMerito()))
							&& (Constantes.NO.equals(expedienteMC.getBAcreditado()))
							&& (expedienteMC.getCModalidadId().intValue() == 2)) {
						horasTotCTSNP += expedienteMC.getNHoras().intValue();
						numCreditosHoraCTSNP = rs.getFloat("n_creditos_mc");
						if ((expedienteMC.getBInvalidadoCeis() == null)
								|| (!Constantes.SI.equals(expedienteMC.getBInvalidadoCeis()))) {
							horasTotValidadasCeisCTSNP += expedienteMC.getNHoras().intValue();
						}
						if ((expedienteMC.getBInvalidadoCc() == null)
								|| (!Constantes.SI.equals(expedienteMC.getBInvalidadoCc()))) {
							horasTotValidadasCcCTSNP += expedienteMC.getNHoras().intValue();
						}
						listadoExpMCCTSNP.add(expedienteMC);
						bCalcularCreditosCTSNoAcre = true;
					} else if ((Constantes.MC_TALLER.equals(expedienteMC.getCTipoMerito()))
							&& (Constantes.NO.equals(expedienteMC.getBAcreditado()))
							&& (expedienteMC.getCModalidadId().intValue() == 8)) {
						horasTotCTSM += expedienteMC.getNHoras().intValue();
						numCreditosHoraCTSM = rs.getFloat("n_creditos_mc");
						if ((expedienteMC.getBInvalidadoCeis() == null)
								|| (!Constantes.SI.equals(expedienteMC.getBInvalidadoCeis()))) {
							horasTotValidadasCeisCTSM += expedienteMC.getNHoras().intValue();
						}
						if ((expedienteMC.getBInvalidadoCc() == null)
								|| (!Constantes.SI.equals(expedienteMC.getBInvalidadoCc()))) {
							horasTotValidadasCcCTSM += expedienteMC.getNHoras().intValue();
						}
						listadoExpMCCTSM.add(expedienteMC);
						bCalcularCreditosCTSNoAcre = true;
					} else {
						if ((Constantes.MC_LIBRO_ISBN.equals(expedienteMC.getCTipoMerito()))
								&& (expedienteMC.getCTipoId().intValue() == 6)) {
							contadorCapitulos++;
							if (contadorCapitulos <= 5) {
								creditosMerito = rs.getFloat("n_cred_usuario");
								creditosCeisMerito = rs.getFloat("n_cred_ceis");
								creditosCcMerito = rs.getFloat("n_cred_cc");
							}
						} else {
							creditosMerito = rs.getFloat("n_cred_usuario");
							creditosCeisMerito = rs.getFloat("n_cred_ceis");
							creditosCcMerito = rs.getFloat("n_cred_cc");
						}
	
						expedienteMC.setNCreditos(Float.valueOf(creditosMerito));
						creditosTot += creditosMerito;
	
						if ((expedienteMC.getBInvalidadoCeis() == null)
								|| (!Constantes.SI.equals(expedienteMC.getBInvalidadoCeis()))) {
							creditosTotValidadosCeis += creditosCeisMerito;
							expedienteMC.setNCredCeis(Float.valueOf(creditosCeisMerito));
						} else {
							expedienteMC.setNCredCeis(Float.valueOf(0.0F));
						}
	
						if ((expedienteMC.getBInvalidadoCc() == null)
								|| (!Constantes.SI.equals(expedienteMC.getBInvalidadoCc()))) {
							creditosTotValidadosCc += creditosCcMerito;
							expedienteMC.setNCredCc(Float.valueOf(creditosCcMerito));
						} else {
							expedienteMC.setNCredCc(Float.valueOf(0.0F));
						}
	
						listadoExpMC.add(expedienteMC);
					}
	
				}
	
			}
	
			if (siCorrido) {
				if ((Constantes.MC_TALLER.equals(tipoMeritoRecorrido)) && (bCalcularCreditosCTSNoAcre)) {
					creditosTotCTSP = (float) (Math.floor(horasTotCTSP / 10) * numCreditosHoraCTSP);
					creditosTotCTSNP = (float) (Math.floor(horasTotCTSNP / 10) * numCreditosHoraCTSNP);
					creditosTotCTSM = (float) (Math.floor(horasTotCTSM / 10) * numCreditosHoraCTSM);
					creditosTot = creditosTot + creditosTotCTSP + creditosTotCTSNP + creditosTotCTSM;
	
					creditosTotValidadosCeisCTSP = (float) (Math.floor(horasTotValidadasCeisCTSP / 10)
							* numCreditosHoraCTSP);
					creditosTotValidadosCeisCTSNP = (float) (Math.floor(horasTotValidadasCeisCTSNP / 10)
							* numCreditosHoraCTSNP);
					creditosTotValidadosCeisCTSM = (float) (Math.floor(horasTotValidadasCeisCTSM / 10)
							* numCreditosHoraCTSM);
					creditosTotValidadosCeis = creditosTotValidadosCeis + creditosTotValidadosCeisCTSP
							+ creditosTotValidadosCeisCTSNP + creditosTotValidadosCeisCTSM;
	
					creditosTotValidadosCcCTSP = (float) (Math.floor(horasTotValidadasCcCTSP / 10)
							* numCreditosHoraCTSP);
					creditosTotValidadosCcCTSNP = (float) (Math.floor(horasTotValidadasCcCTSNP / 10)
							* numCreditosHoraCTSNP);
					creditosTotValidadosCcCTSM = (float) (Math.floor(horasTotValidadasCcCTSM / 10)
							* numCreditosHoraCTSM);
					creditosTotValidadosCc = creditosTotValidadosCc + creditosTotValidadosCcCTSP
							+ creditosTotValidadosCcCTSNP + creditosTotValidadosCcCTSM;
	
					creditosTotCTSP = (float) (Math.rint(creditosTotCTSP * 100.0F) / 100.0D);
					creditosTotCTSNP = (float) (Math.rint(creditosTotCTSNP * 100.0F) / 100.0D);
					creditosTotCTSM = (float) (Math.rint(creditosTotCTSM * 100.0F) / 100.0D);
	
					creditosTotValidadosCeisCTSP = (float) (Math.rint(creditosTotValidadosCeisCTSP * 100.0F) / 100.0D);
					creditosTotValidadosCeisCTSNP = (float) (Math.rint(creditosTotValidadosCeisCTSNP * 100.0F)
							/ 100.0D);
					creditosTotValidadosCeisCTSM = (float) (Math.rint(creditosTotValidadosCeisCTSM * 100.0F) / 100.0D);
	
					creditosTotValidadosCcCTSP = (float) (Math.rint(creditosTotValidadosCcCTSP * 100.0F) / 100.0D);
					creditosTotValidadosCcCTSNP = (float) (Math.rint(creditosTotValidadosCcCTSNP * 100.0F) / 100.0D);
					creditosTotValidadosCcCTSM = (float) (Math.rint(creditosTotValidadosCcCTSM * 100.0F) / 100.0D);
	
					datos.setCreditosCTSP(Float.valueOf(creditosTotCTSP));
					datos.setCreditosCTSNP(Float.valueOf(creditosTotCTSNP));
					datos.setCreditosCTSM(Float.valueOf(creditosTotCTSM));
	
					datos.setCreditosValidadosCeis(Float.valueOf(creditosTotValidadosCeis));
					datos.setCreditosValidadosCeisCTSP(Float.valueOf(creditosTotValidadosCeisCTSP));
					datos.setCreditosValidadosCeisCTSNP(Float.valueOf(creditosTotValidadosCeisCTSNP));
					datos.setCreditosValidadosCeisCTSM(Float.valueOf(creditosTotValidadosCeisCTSM));
	
					datos.setCreditosValidadosCc(Float.valueOf(creditosTotValidadosCc));
					datos.setCreditosValidadosCcCTSP(Float.valueOf(creditosTotValidadosCcCTSP));
					datos.setCreditosValidadosCcCTSNP(Float.valueOf(creditosTotValidadosCcCTSNP));
					datos.setCreditosValidadosCcCTSM(Float.valueOf(creditosTotValidadosCcCTSM));
				}
	
				if (buscaCV) {
					if (listadoExpMC.size() != 0)
						datos.setListaExpmc(listadoExpMC);
					if (listadoExpMCCTSP.size() != 0)
						datos.setListaExpmcCTSP(listadoExpMCCTSP);
					if (listadoExpMCCTSNP.size() != 0)
						datos.setListaExpmcCTSNP(listadoExpMCCTSNP);
					if (listadoExpMCCTSM.size() != 0)
						datos.setListaExpmcCTSM(listadoExpMCCTSM);
					if ((listadoExpMC.size() != 0) || (listadoExpMCCTSP.size() != 0) || (listadoExpMCCTSNP.size() != 0)
							|| (listadoExpMCCTSM.size() != 0))
						listadoMC.add(datos);
				} else {
					datos.setListaExpmc(listadoExpMC);
					datos.setListaExpmcCTSP(listadoExpMCCTSP);
					datos.setListaExpmcCTSNP(listadoExpMCCTSNP);
					datos.setListaExpmcCTSM(listadoExpMCCTSM);
					listadoMC.add(datos);
				}
	
			}
	
			creditosTot = (float) (Math.rint(creditosTot * 100.0F) / 100.0D);
			creditosTotValidadosCeis = (float) (Math.rint(creditosTotValidadosCeis * 100.0F) / 100.0D);
			creditosTotValidadosCc = (float) (Math.rint(creditosTotValidadosCc * 100.0F) / 100.0D);
	
			datosConjunto.setCreditos(Float.valueOf(creditosTot));
			datosConjunto.setCreditosValidadosCeis(Float.valueOf(creditosTotValidadosCeis));
			datosConjunto.setCreditosValidadosCc(Float.valueOf(creditosTotValidadosCc));
	
			datosConjunto.setListaMeritosUsuario(listadoMC);
		
		} catch (SQLException ex) {
			throw ex;
		} catch (UnsupportedEncodingException e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
		}
		
		return datosConjunto;
	}

	public OCAPMeritoscurricularesOT buscarOCAPMeritoscurricularesPorNombre(String nombreMerito, String estatutId)
			throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		OCAPMeritoscurricularesOT datos = null;
		try {
			this.loggerBD.info(" buscarOCAPMeritoscurricularesPorNombre: " + nombreMerito + "," + estatutId);
			String sql = "SELECT distinct(D_NOMBRE), D_DESCRIPCION, D_OBSERVACIONES FROM OCAP_MERITOSCURRICULARES WHERE D_NOMBRE = ?  AND C_ESTATUT_ID = ?  AND B_BORRADO='N'";
			
			if(nombreMerito!=null && nombreMerito.contains("ano")){ 
				nombreMerito = nombreMerito.replaceAll("ano", "año");
			}
			
			st = con.prepareStatement(sql);
			st.setString(1, nombreMerito);
			st.setString(2, estatutId);

			rs = st.executeQuery();
			datos = new OCAPMeritoscurricularesOT();
			if (rs.next()) {
				datos.setDDescripcion(rs.getString("D_DESCRIPCION") == null ? "" : rs.getString("D_DESCRIPCION"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES") == null ? "" : rs.getString("D_OBSERVACIONES"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
			}
		} catch (SQLException ex) {
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

	public ArrayList listadoOCAPMeritoscurriculares() throws SQLException {
		return listadoOCAPMeritoscurriculares(1, -1);
	}

	public ArrayList listadoOCAPMeritoscurriculares(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String sql = "SELECT F_USUALTA, C_DEFMERITO_ID, C_ESTATUT_ID, C_TIPO_ID, C_TIPO_MERITO, N_CREDITOS, D_DESCRIPCION, C_MERITO_ID, D_OBSERVACIONES, B_BORRADO, D_NOMBRE, D_NOMBRECORTO, F_USUMODI, C_FACTOR_ID, C_ACTIVIDAD_ID, C_MODALIDAD_ID  FROM OCAP_MERITOSCURRICULARES";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPMeritoscurricularesOT datos = new OCAPMeritoscurricularesOT();
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setCDefmeritoId(rs.getLong("C_DEFMERITO_ID"));
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setCTipoId(rs.getLong("C_TIPO_ID"));
				datos.setCTipoMerito(rs.getString("C_TIPO_MERITO"));
				datos.setNCreditos(rs.getFloat("N_CREDITOS"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setCMeritoId(rs.getLong("C_MERITO_ID"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDNombrecorto(rs.getString("D_NOMBRECORTO"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setCFactorId(rs.getLong("C_FACTOR_ID"));
				datos.setCActividadId(rs.getLong("C_ACTIVIDAD_ID"));
				datos.setCModalidadId(rs.getLong("C_MODALIDAD_ID"));

				listado.add(datos);

				if (++i == cuantos)
					break;
			}

		} catch (SQLException ex) {
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

	public int listadoOCAPMeritoscurricularesCuenta(long cMeritoId, long cDefmeritoId, long cEstatutId,
			long cActividadId, long cModalidadId, long cFactorId, long cTipoId, String cTipoMerito, String dNombrecorto,
			String dNombre, String dDescripcion, String dObservaciones, String nCreditos, String fCreacion,
			String fModificacion) throws Exception {
		ResultSet rs = null;
		Statement st = null;

		String where = "";
		StringBuffer sbWhere = new StringBuffer(40);
		Connection con = JCYLGestionTransacciones.getConnection();
		String sql = "SELECT COUNT(*) FROM OCAP_MERITOSCURRICULARES ";

		boolean isCMeritoId = cMeritoId != 0L;
		if (isCMeritoId)
			sbWhere.append("C_MERITO_ID = ").append(cMeritoId).append(" AND ");
		boolean isCDefmeritoId = cDefmeritoId != 0L;
		if (isCDefmeritoId)
			sbWhere.append("C_DEFMERITO_ID = ").append(cDefmeritoId).append(" AND ");
		boolean isCEstatutId = cEstatutId != 0L;
		if (isCEstatutId)
			sbWhere.append("C_ESTATUT_ID = ").append(cEstatutId).append(" AND ");
		boolean isCActividadId = cActividadId != 0L;
		if (isCActividadId)
			sbWhere.append("C_ACTIVIDAD_ID = ").append(cActividadId).append(" AND ");
		boolean isCModalidadId = cModalidadId != 0L;
		if (isCModalidadId)
			sbWhere.append("C_MODALIDAD_ID = ").append(cModalidadId).append(" AND ");
		boolean isCFactorId = cFactorId != 0L;
		if (isCFactorId)
			sbWhere.append("C_FACTOR_ID = ").append(cFactorId).append(" AND ");
		boolean isCTipoId = cTipoId != 0L;
		if (isCTipoId)
			sbWhere.append("C_TIPO_ID = ").append(cTipoId).append(" AND ");
		boolean isCTipoMerito = (cTipoMerito != null) && (!cTipoMerito.equals(""));
		if (isCTipoMerito)
			sbWhere.append("(upper(C_TIPO_MERITO) like upper('%").append(cTipoMerito).append("%')) AND ");
		boolean isDNombrecorto = (dNombrecorto != null) && (!dNombrecorto.equals(""));
		if (isDNombrecorto)
			sbWhere.append("(upper(D_NOMBRECORTO) like upper('%").append(dNombrecorto).append("%')) AND ");
		boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
		if (isDNombre)
			sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
		boolean isDDescripcion = (dDescripcion != null) && (!dDescripcion.equals(""));
		if (isDDescripcion)
			sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dDescripcion).append("%')) AND ");
		boolean isDObservaciones = (dObservaciones != null) && (!dObservaciones.equals(""));
		if (isDObservaciones)
			sbWhere.append("(upper(D_OBSERVACIONES) like upper('%").append(dObservaciones).append("%')) AND ");
		boolean isNCreditos = nCreditos != null;
		if (isNCreditos)
			sbWhere.append("N_CREDITOS = ").append(nCreditos).append(" AND ");
		boolean isFCreacion = (fCreacion != null) && (!fCreacion.equals(""));
		if (isFCreacion)
			sbWhere.append("to_char(F_USUALTA,'DD/MM/RRRR') = '").append(fCreacion).append("' AND ");
		boolean isFModificacion = (fModificacion != null) && (!fModificacion.equals(""));
		if (isFModificacion)
			sbWhere.append("to_char(F_USUMODI,'DD/MM/RRRR') = '").append(fModificacion).append("' AND ");
		sbWhere.append("B_BORRADO = 'N'  AND ");
		int len = sbWhere.length();
		if (len > 0) {
			where = sbWhere.insert(0, " Where ").substring(0, len + 1);
		}
		String sqlStatement = sql + where;

		int total = -1;
		try {
			st = con.prepareStatement(sqlStatement);
			rs = st.executeQuery(sqlStatement);
			if (rs.next())
				total = rs.getInt(1);
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return total;
	}

	public ArrayList consultaOCAPMeritoscurriculares(long cMeritoId, long cDefmeritoId, long cEstatutId,
			long cActividadId, long cModalidadId, long cFactorId, long cTipoId, String cTipoMerito, String dNombrecorto,
			String dNombre, String dDescripcion, String dObservaciones, String nCreditos, String fCreacion,
			String fModificacion, int inicio, int cuantos) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT C_MERITO_ID, C_DEFMERITO_ID, C_ESTATUT_ID, C_ACTIVIDAD_ID, C_MODALIDAD_ID, C_FACTOR_ID, C_TIPO_ID, C_TIPO_MERITO, D_NOMBRECORTO, D_NOMBRE, D_DESCRIPCION, D_OBSERVACIONES, N_CREDITOS, F_USUMODI, F_USUALTA, B_BORRADO FROM OCAP_MERITOSCURRICULARES";

			String orderBy = " order by C_MERITO_ID";
			boolean isCMeritoId = cMeritoId != 0L;
			if (isCMeritoId)
				sbWhere.append("C_MERITO_ID = ?  AND ");
			boolean isCDefmeritoId = cDefmeritoId != 0L;
			if (isCDefmeritoId)
				sbWhere.append("C_DEFMERITO_ID = ?  AND ");
			boolean isCEstatutId = cEstatutId != 0L;
			if (isCEstatutId)
				sbWhere.append("C_ESTATUT_ID = ?  AND ");
			boolean isCActividadId = cActividadId != 0L;
			if (isCActividadId)
				sbWhere.append("C_ACTIVIDAD_ID = ?  AND ");
			boolean isCModalidadId = cModalidadId != 0L;
			if (isCModalidadId)
				sbWhere.append("C_MODALIDAD_ID = ?  AND ");
			boolean isCFactorId = cFactorId != 0L;
			if (isCFactorId)
				sbWhere.append("C_FACTOR_ID = ?  AND ");
			boolean isCTipoId = cTipoId != 0L;
			if (isCTipoId)
				sbWhere.append("C_TIPO_ID = ?  AND ");
			boolean isCTipoMerito = (cTipoMerito != null) && (!cTipoMerito.equals(""));
			if (isCTipoMerito)
				sbWhere.append("(upper(C_TIPO_MERITO) like upper('%").append(cTipoMerito).append("%')) AND ");
			boolean isDNombrecorto = (dNombrecorto != null) && (!dNombrecorto.equals(""));
			if (isDNombrecorto)
				sbWhere.append("(upper(D_NOMBRECORTO) like upper('%").append(dNombrecorto).append("%')) AND ");
			boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
			if (isDNombre)
				sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
			boolean isDDescripcion = (dDescripcion != null) && (!dDescripcion.equals(""));
			if (isDDescripcion)
				sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dDescripcion).append("%')) AND ");
			boolean isDObservaciones = (dObservaciones != null) && (!dObservaciones.equals(""));
			if (isDObservaciones)
				sbWhere.append("(upper(D_OBSERVACIONES) like upper('%").append(dObservaciones).append("%')) AND ");
			boolean isNCreditos = nCreditos != null;
			if (isNCreditos)
				sbWhere.append("N_CREDITOS = ?  AND ");
			boolean isFCreacion = (fCreacion != null) && (!fCreacion.equals(""));
			if (isFCreacion)
				sbWhere.append("to_char(F_USUALTA,'DD/MM/RRRR') = '").append(fCreacion).append("' AND ");
			boolean isFModificacion = (fModificacion != null) && (!fModificacion.equals(""));
			if (isFModificacion)
				sbWhere.append("to_char(F_USUMODI,'DD/MM/RRRR') = '").append(fModificacion).append("' AND ");
			sbWhere.append("B_BORRADO = 'N'  AND ");
			int len = sbWhere.length();
			if (len > 0) {
				where = sbWhere.insert(0, " Where ").substring(0, len + 1);
			}
			String sqlStatement = selectFrom + where + orderBy;
			OCAPConfigApp.logger.info("Sentencia SQL:" + sqlStatement);
			st = con.prepareStatement(sqlStatement, 1004, 1008);

			int index = 1;
			OCAPMeritoscurricularesOT datos = new OCAPMeritoscurricularesOT();

			if (isCMeritoId)
				st.setLong(index++, cMeritoId);
			if (isCDefmeritoId)
				st.setLong(index++, cDefmeritoId);
			if (isCEstatutId)
				st.setLong(index++, cEstatutId);
			if (isCActividadId)
				st.setLong(index++, cActividadId);
			if (isCModalidadId)
				st.setLong(index++, cModalidadId);
			if (isCFactorId)
				st.setLong(index++, cFactorId);
			if (isCTipoId)
				st.setLong(index++, cTipoId);
			if (isNCreditos) {
				st.setFloat(index++, Float.valueOf(nCreditos).floatValue());
			}
			rs = st.executeQuery();
			if (inicio > 1)
				rs.absolute(inicio - 1);
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				datos = new OCAPMeritoscurricularesOT();
				datos.setCMeritoId(rs.getLong("C_MERITO_ID"));
				datos.setCDefmeritoId(rs.getLong("C_DEFMERITO_ID"));
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setCActividadId(rs.getLong("C_ACTIVIDAD_ID"));
				datos.setCModalidadId(rs.getLong("C_MODALIDAD_ID"));
				datos.setCFactorId(rs.getLong("C_FACTOR_ID"));
				datos.setCTipoId(rs.getLong("C_TIPO_ID"));
				datos.setCTipoMerito(rs.getString("C_TIPO_MERITO"));
				datos.setDNombrecorto(rs.getString("D_NOMBRECORTO"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
				datos.setNCreditos(rs.getFloat("N_CREDITOS"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setBBorrado(rs.getString("B_BORRADO"));

				listado.add(datos);
				if (++i == cuantos)
					break;
			}
		} catch (Exception ex) {
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

	public OCAPExpedientemcOT buscarMeritoCurricular(OCAPExpedientemcOT expedientemcOT) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		StringBuffer consulta = new StringBuffer();
		try {
			consulta.append("SELECT mecu.n_creditos n_creditos_mer, tram.n_creditos n_creditos_tra ")
					.append("FROM OCAP_MERITOSCURRICULARES mecu LEFT JOIN OCAP_TRAMOS tram ")
					.append(" ON (mecu.C_MERITO_ID = tram.C_MERITO_ID ");

			if (Constantes.SI.equals(expedientemcOT.getBAcreditado()))
				consulta.append(" AND ? BETWEEN tram.N_INICIO_TRAMO AND tram.N_FIN_TRAMO) ");
			else {
				consulta.append(" ) ");
			}
			consulta.append(" WHERE C_DEFMERITO_ID = ? ").append("  AND C_ESTATUT_ID = ? ")
					.append("  AND C_TIPO_MERITO = ? ").append("  AND D_NOMBRE = ? ");

			if (expedientemcOT.getCActividadId() != null)
				consulta.append(" AND c_actividad_id= ? ");
			else {
				consulta.append(" AND c_actividad_id is null ");
			}
			if (expedientemcOT.getCModalidadId() != null)
				consulta.append(" AND c_modalidad_id = ? ");
			else {
				consulta.append(" AND c_modalidad_id is null ");
			}
			if (expedientemcOT.getCTipoId() != null)
				consulta.append(" AND c_tipo_id= ? ");
			else {
				consulta.append(" AND c_tipo_id is null ");
			}
			if (expedientemcOT.getCFactorId() != null)
				consulta.append(" AND c_factor_id= ? ");
			else {
				consulta.append(" AND c_factor_id is null ");
			}
			if (expedientemcOT.getBAcreditado() != null) {
				consulta.append(" AND b_acreditado= ? ");
			}
			OCAPConfigApp.logger.info(getClass().getName() + " buscarMeritoCurricular Ini SQL: " + consulta.toString());
			st = con.prepareStatement(consulta.toString());
			int cont = 1;

			if (Constantes.SI.equals(expedientemcOT.getBAcreditado())) {
				if (expedientemcOT.getNCreditosCurso() != null)
					st.setFloat(cont++, Float.parseFloat(expedientemcOT.getNCreditosCurso()));
				else if (expedientemcOT.getNHoras() != null) {
					st.setInt(cont++, expedientemcOT.getNHoras().intValue());
				}
			}
			st.setInt(cont++, new Integer(expedientemcOT.getCDefMeritoId()).intValue());
			st.setInt(cont++, expedientemcOT.getCEstatutId().intValue());
			st.setString(cont++, expedientemcOT.getCTipoMerito());
			st.setString(cont++, expedientemcOT.getDTipoMerito());

			if (expedientemcOT.getCActividadId() != null) {
				st.setInt(cont++, expedientemcOT.getCActividadId().intValue());
			}
			if (expedientemcOT.getCModalidadId() != null) {
				st.setInt(cont++, expedientemcOT.getCModalidadId().intValue());
			}
			if (expedientemcOT.getCTipoId() != null) {
				st.setInt(cont++, expedientemcOT.getCTipoId().intValue());
			}
			if (expedientemcOT.getCFactorId() != null) {
				st.setInt(cont++, expedientemcOT.getCFactorId().intValue());
			}
			if (expedientemcOT.getBAcreditado() != null) {
				st.setString(cont++, expedientemcOT.getBAcreditado());
			}
			rs = st.executeQuery();

			if (rs.next()) {
				expedientemcOT.setNCreditos(new Float(rs.getFloat("n_creditos_mer")));
				expedientemcOT.setNCreditosTramo(new Float(rs.getFloat("n_creditos_tra")));
				OCAPConfigApp.logger.info(getClass().getName()
						+ " buscarMeritoCurricular - la consulta ha devuelto los siguientes valores: creditos="
						+ expedientemcOT.getNCreditos() + " creditosTramo=" + expedientemcOT.getNCreditosTramo());
			}

			OCAPConfigApp.logger.info(getClass().getName() + " buscarMeritoCurricular: Fin");
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return expedientemcOT;
	}
	
	public OCAPMeritoscurricularesOT buscarOCAPMeritosValidadosPorUsuarioOT(OCAPUsuariosOT usuarioOT,
			Long cDefMeritoId, String bOpcionales, JCYLUsuario jcylUsuario)
			throws SQLException, Exception {

		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		Connection con = JCYLGestionTransacciones.getConnection();
		OCAPMeritoscurricularesOT datosConjunto = new OCAPMeritoscurricularesOT();
		try {
			this.loggerBD.info(" buscarOCAPMeritosValidadosPorUsuarioOT");

			StringBuffer sql = new StringBuffer();
				sql.append("SELECT ")

					.append("expmcAgrup.c_exp_id, mecuTotal.c_defmerito_id, expmcAgrup.c_merito_id, mecuTotal.c_estatut_id, ")
					.append(" mecuTotal.c_tipo_merito, mecuTotal.d_merito, expmcAgrup.d_merito_corto, ")
					.append(" mecuTotal.d_descripcion, mecuTotal.d_observaciones, ")
					.append(" expmcAgrup.c_mer_actividad_mc, expmcAgrup.d_mer_actividad_mc, ")
					.append(" expmcAgrup.c_mer_modalidad_mc, expmcAgrup.c_factor_impacto_mc, expmcAgrup.c_tipo_firmante_mc, ")
					.append(" expmcAgrup.n_creditos_mc, expmcAgrup.b_acreditado_mc, ")
					.append(" expmcAgrup.c_expmc_id, expmcAgrup.d_expmc, expmcAgrup.b_opcional_emc,  ")
					.append(" TO_CHAR(expmcAgrup.f_inicio,").append(Constantes.FECHA_CORTA).append(")f_inicio, ")
					.append(" TO_CHAR(expmcAgrup.f_finalizacion,").append(Constantes.FECHA_CORTA).append(")f_finalizacion, ")
					.append(" TO_CHAR(expmcAgrup.f_expedicion,").append(Constantes.FECHA_CORTA).append(")f_expedicion, ")
					.append(" expmcAgrup.CLOB_ORGANIZADOR, expmcAgrup.a_lugar_expedicion, ")
					.append(" expmcAgrup.f_annio, expmcAgrup.n_sesiones, ")
					.append(" expmcAgrup.n_horas, expmcAgrup.n_dias, expmcAgrup.n_annios, ")
					.append(" expmcAgrup.b_participacion, expmcAgrup.a_nombre_revista, ")
					.append(" expmcAgrup.a_cargo, expmcAgrup.a_objetivo, ")
					.append(" expmcAgrup.n_meses, expmcAgrup.n_cred_curso, ")
					.append(" expmcAgrup.n_isbn, expmcAgrup.d_editorial, expmcAgrup.d_capitulo, ")
					.append(" expmcAgrup.b_acreditado_emc, expmcAgrup.n_cred_usuario, ")
					.append(" expmcAgrup.n_cred_ceis, expmcAgrup.b_invalidado_ceis, expmcAgrup.b_pdte_aclarar_ceis, ")
					.append(" expmcAgrup.n_cred_cc, expmcAgrup.b_invalidado_cc, expmcAgrup.b_pdte_aclarar_cc,expmcAgrup.D_MOTIVOS_CEIS,expmcAgrup.D_MOTIVOS_CC  ")
					.append(" FROM ( ")
					
					.append(" SELECT DISTINCT(mecuaux.d_nombre)d_merito, mecuaux.c_tipo_merito, mecuaux.c_defmerito_id, mecuaux.c_estatut_id, ")
					.append(" mecuaux.d_descripcion, mecuaux.d_observaciones, mecuaux.n_orden, mecuaux.b_borrado ")
					.append(" FROM ocap_meritoscurriculares mecuaux ").append(" WHERE mecuaux.c_defmerito_id = ? ")
					.append(" AND mecuaux.b_borrado = 'N' ")
					.append(" ORDER BY mecuaux.n_orden asc)mecuTotal, ")
					
					.append(" ( ")
					.append(" SELECT exmc.c_exp_id, mecu.c_estatut_id, exmc.c_merito_id, mecu.d_nombrecorto d_merito_corto, mecu.d_nombre d_merito, mecu.c_tipo_merito, ")
					.append(" mecu.c_actividad_id c_mer_actividad_mc, meac.d_nombre d_mer_actividad_mc, mecu.c_modalidad_id c_mer_modalidad_mc, mecu.c_factor_id c_factor_impacto_mc, mecu.c_tipo_id c_tipo_firmante_mc, ")
					.append(" mecu.n_creditos n_creditos_mc, mecu.b_acreditado b_acreditado_mc, exmc.c_expmc_id,  exmc.d_titulo d_expmc, exmc.b_opcional b_opcional_emc, ")
					.append(" exmc.f_inicio, exmc.f_finalizacion, exmc.f_expedicion, exmc.CLOB_ORGANIZADOR, exmc.a_lugar_expedicion, ")
					.append(" exmc.f_annio, exmc.n_sesiones, exmc.n_horas, exmc.n_dias, exmc.n_annios, ")
					.append(" exmc.b_participacion, exmc.a_nombre_revista, exmc.a_cargo, exmc.a_objetivo, ")
					.append(" exmc.n_meses, exmc.n_cred_curso, exmc.n_isbn, exmc.d_editorial, exmc.d_capitulo, ")
					.append(" exmc.b_acreditado b_acreditado_emc, exmc.n_cred_usuario, exmc.n_cred_ceis, exmc.b_invalidado_ceis, exmc.b_pdte_aclarar_ceis, ")
					.append(" exmc.n_cred_cc, exmc.b_invalidado_cc, exmc.b_pdte_aclarar_cc, exmc.D_MOTIVOS_CEIS ,exmc.D_MOTIVOS_CC  ")
					.append(" FROM ocap_expedientesmcs exmc, ocap_meritoscurriculares mecu, ocap_meractividades meac ")
					.append(" WHERE exmc.c_exp_id IN (SELECT C_EXP_ID FROM OCAP_EXPEDIENTESCARRERA" + 
							"                                      WHERE     C_USR_ID IN  (SELECT C_USR_ID FROM OCAP_EXPEDIENTESCARRERA WHERE C_EXP_ID = ? )" + 
							"                                              AND C_ESTADO_ID = 12" + 
							"                                              AND C_EXP_ID <> ? ) ")
					
					.append(" AND c_defmerito_id = ? ")
					.append(" AND c_estatut_id = (SELECT C_ESTATUT_ID FROM OCAP_CATEG_PROFESIONALES WHERE C_PROFESIONAL_ID = (SELECT C_PROFESIONAL_ID FROM OCAP_EXPEDIENTESCARRERA WHERE C_EXP_ID = exmc.c_exp_id)) ")
					.append(" AND exmc.b_opcional = ? ");
				 sql.append(" AND exmc.c_merito_id = mecu.c_merito_id ")
					.append(" AND mecu.c_actividad_id = meac.c_actividad_id(+) ")
					.append(" AND mecu.b_borrado = 'N' ")
					.append(" AND exmc.b_borrado = 'N' ")
					.append("AND mecu.c_tipo_merito = ? ")
					.append("AND mecu.d_nombre = ? ")
					.append(" ORDER BY mecu.n_orden ASC)expmcAgrup ")
					
					
					.append(" WHERE mecuTotal.d_merito = expmcAgrup.d_merito ")
					.append(" AND MECUTOTAL.c_tipo_merito = expmcAgrup.c_tipo_merito ")
					.append(" AND MECUTOTAL.C_ESTATUT_ID = expmcAgrup.c_estatut_id ")
					
					.append(" ORDER BY mecuTotal.n_orden asc, expmcAgrup.b_acreditado_emc desc, expmcAgrup.c_mer_modalidad_mc asc, expmcAgrup.c_expmc_id asc ");

			this.loggerBD.info("buscarOCAPMeritosValidadosPorUsuarioOT SQL: " + sql.toString());

			st = con.prepareStatement(sql.toString(), 1004, 1007);
			
			st.setLong(i++, cDefMeritoId.longValue());
			st.setLong(i++, usuarioOT.getCExpId().longValue());
			st.setLong(i++, usuarioOT.getCExpId().longValue());
			st.setLong(i++, cDefMeritoId.longValue());
			st.setString(i++, bOpcionales);
			
			
			st.setString(i++, usuarioOT.getCodigoId());
			st.setString(i++, usuarioOT.getDDescripcion());

			rs = st.executeQuery();
			
			datosConjunto = rellenarDatosConResultSet(rs, jcylUsuario, false, false);
			
			
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return datosConjunto;
	
	
	}
}
