package es.jcyl.fqs.ocap.oad.dudasTutores;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.dudasTutores.OCAPDudasTutoresOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPDudasTutoresOAD {
	public Logger loggerBD;
	private static OCAPDudasTutoresOAD instance;

	private void $init$() {
		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public static OCAPDudasTutoresOAD getInstance() {
		if (instance == null) {
			instance = new OCAPDudasTutoresOAD();
		}
		return instance;
	}

	public OCAPDudasTutoresOAD() {
		$init$();
	}

	public long altaOCAPTutores(OCAPDudasTutoresOT datos) throws SQLException {
		CallableStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		long idTutor = 0L;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("BEGIN INSERT INTO OCAP_TUTORES ")
					.append("(C_TUTOR_ID, D_NOMBRE, D_APELLIDOS, C_TIPO_TUTOR, C_TIPO_DUDA, N_DUDASRECIBIDAS, N_DUDASCONTESTADAS, N_CONTROL, A_CORREO_ELECTRONICO, C_USUALTA, C_DNI, F_USUALTA) ")
					.append("VALUES ").append("(OCAP_TUT_ID_SEQ.nextval, ?, ?, ?, ?, 0, 0, 0, ?, ?, ?, SYSDATE) ")
					.append("RETURNING C_TUTOR_ID INTO ?; END;");

			st = con.prepareCall(sql.toString());
			st.setString(1, datos.getDNombreTutor());
			st.setString(2, datos.getDApellidosTutor());
			st.setString(3, datos.getCTipoTutor());
			st.setString(4, datos.getCTipoDuda());
			st.setString(5, datos.getDCorreoElectronicoTutor());
			st.setString(6, datos.getACreadoPor());
			st.setString(7, datos.getCDni());
			st.registerOutParameter(8, 4);

			filas = st.executeUpdate();
			idTutor = st.getLong(8);
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return idTutor;
	}

	public long asignaCorreoTutor(long cTutorId) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		long idTutor = 0L;
		String correoTutor = "";
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("UPDATE OCAP_TUTORES ").append(" SET A_CORREO_ELECTRONICO=? ").append(" WHERE C_TUTOR_ID=? ");

			st = con.prepareStatement(sql.toString());
			correoTutor = "tutor" + cTutorId + "@fqscyl.es";
			st.setString(1, correoTutor);
			st.setLong(2, cTutorId);

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

	public OCAPDudasTutoresOT buscarDatosTutor(long cTutorId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		OCAPDudasTutoresOT datos = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(
					"SELECT d_nombre, d_apellidos, c_dni, a_correo_electronico, b_activado, c_tipo_tutor, c_tipo_duda, n_dudasrecibidas, n_dudascontestadas, n_control ")
					.append(" FROM ocap_tutores ").append(" WHERE c_tutor_id=? AND b_borrado='N' ");

			st = con.prepareStatement(sql.toString());
			st.setLong(1, cTutorId);

			rs = st.executeQuery();

			if (rs.next()) {
				datos = new OCAPDudasTutoresOT();
				datos.setCTutorId(cTutorId);
				datos.setDNombreTutor(rs.getString("d_nombre"));
				datos.setDApellidosTutor(rs.getString("d_apellidos"));
				datos.setCDni(rs.getString("c_dni"));
				datos.setDCorreoElectronicoTutor(rs.getString("a_correo_electronico"));
				datos.setBActivado(rs.getString("b_activado"));
				datos.setCTipoTutor(rs.getString("c_tipo_tutor"));
				datos.setCTipoDuda(rs.getString("c_tipo_duda"));
				datos.setCTipoTutor(rs.getString("c_tipo_tutor"));
				datos.setNDudasRecibidas(rs.getLong("n_dudasrecibidas"));
				datos.setNDudasContestadas(rs.getLong("n_dudascontestadas"));
				datos.setNControl(rs.getLong("n_control"));
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

	public OCAPDudasTutoresOT buscarDatosTutor(String cDni) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		OCAPDudasTutoresOT datos = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(
					"SELECT c_tutor_id, d_nombre, d_apellidos, c_dni, a_correo_electronico, b_activado, c_tipo_tutor, c_tipo_duda, n_dudasrecibidas, n_dudascontestadas, n_control ")
					.append(" FROM ocap_tutores ").append(" WHERE UPPER(c_dni)=UPPER(?) AND b_borrado='N' ");

			st = con.prepareStatement(sql.toString());
			st.setString(1, cDni);

			rs = st.executeQuery();

			if (rs.next()) {
				datos = new OCAPDudasTutoresOT();
				datos.setCTutorId(rs.getLong("c_tutor_id"));
				datos.setDNombreTutor(rs.getString("d_nombre"));
				datos.setDApellidosTutor(rs.getString("d_apellidos"));
				datos.setCDni(rs.getString("c_dni"));
				datos.setDCorreoElectronicoTutor(rs.getString("a_correo_electronico"));
				datos.setBActivado(rs.getString("b_activado"));
				datos.setCTipoTutor(rs.getString("c_tipo_tutor"));
				datos.setCTipoDuda(rs.getString("c_tipo_duda"));
				datos.setCTipoTutor(rs.getString("c_tipo_tutor"));
				datos.setNDudasRecibidas(rs.getLong("n_dudasrecibidas"));
				datos.setNDudasContestadas(rs.getLong("n_dudascontestadas"));
				datos.setNControl(rs.getLong("n_control"));
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

	public long buscarMinNControlTutoresActivos(String cTipoDuda) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		long nControl = 0L;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("select min(n_control) as nControl from ocap_tutores ")
					.append(" where b_borrado='N' and b_activado='Y' and c_tipo_tutor='" + 1 + "' and c_tipo_duda=? ");

			st = con.prepareStatement(sql.toString());
			st.setString(1, cTipoDuda);

			rs = st.executeQuery();

			if (rs.next()) {
				nControl = rs.getLong("nControl");
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

		return nControl;
	}

	public ArrayList buscarTutores(OCAPDudasTutoresOT datosOT, int primerRegistro, int registrosPorPagina,
			String ordenacion, String tipoOrdenacion) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		OCAPDudasTutoresOT datos = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(
					"SELECT c_tutor_id, d_nombre, c_dni, d_apellidos, a_correo_electronico, b_activado, c_tipo_tutor, c_tipo_duda, n_dudasrecibidas, n_dudascontestadas, n_control, (n_dudasrecibidas - n_dudascontestadas) as n_dudasPendientes ")
					.append(" FROM ocap_tutores ");

			String sqlWhere = construirWhereBuscarTutores(datosOT);
			sql.append(sqlWhere);

			if ((ordenacion != null) && (!"".equals(ordenacion)))
				sql.append(" ORDER BY ").append(ordenacion).append(" ").append(tipoOrdenacion);
			else {
				sql.append(" ORDER BY d_apellidos, d_nombre ");
			}
			st = con.prepareStatement(sql.toString(), 1004, 1008);

			int cont = 1;
			if ((datosOT.getDApellidosTutor() != null) && (!"".equals(datosOT.getDApellidosTutor())))
				st.setString(cont++, datosOT.getDApellidosTutor());
			if ((datosOT.getDNombreTutor() != null) && (!"".equals(datosOT.getDNombreTutor())))
				st.setString(cont++, datosOT.getDNombreTutor());
			if ((datosOT.getCDni() != null) && (!"".equals(datosOT.getCDni())))
				st.setString(cont++, datosOT.getCDni());
			if ((datosOT.getCTipoTutor() != null) && (!"".equals(datosOT.getCTipoTutor())))
				st.setString(cont++, datosOT.getCTipoTutor());
			if ((datosOT.getCTipoDuda() != null) && (!"".equals(datosOT.getCTipoDuda())))
				st.setString(cont++, datosOT.getCTipoDuda());
			if ((datosOT.getBActivado() != null) && (!"".equals(datosOT.getBActivado()))) {
				st.setString(cont++, datosOT.getBActivado());
			}
			rs = st.executeQuery();

			if (primerRegistro > 1)
				rs.absolute(primerRegistro - 1);

			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				datos = new OCAPDudasTutoresOT();
				datos.setCTutorId(rs.getLong("c_tutor_id"));
				datos.setDNombreTutor(rs.getString("d_nombre"));
				datos.setDApellidosTutor(rs.getString("d_apellidos"));
				datos.setCDni(rs.getString("c_dni"));
				datos.setDCorreoElectronicoTutor(rs.getString("a_correo_electronico"));
				datos.setBActivado(rs.getString("b_activado"));
				datos.setCTipoTutor(rs.getString("c_tipo_tutor"));
				datos.setCTipoDuda(rs.getString("c_tipo_duda"));
				datos.setNDudasRecibidas(rs.getLong("n_dudasrecibidas"));
				datos.setNDudasContestadas(rs.getLong("n_dudascontestadas"));
				datos.setNDudasPendientes(rs.getLong("n_dudasPendientes"));
				datos.setNControl(rs.getLong("n_control"));

				listado.add(datos);

				if (++i == registrosPorPagina)
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

	private String construirWhereBuscarTutores(OCAPDudasTutoresOT datos) throws Exception {
		StringBuffer sqlWhere = new StringBuffer();
		sqlWhere.append(" WHERE b_borrado='N' ");
		if ((datos.getDApellidosTutor() != null) && (!"".equals(datos.getDApellidosTutor())))
			sqlWhere.append(
					" AND UPPER(translate(d_apellidos,'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) LIKE UPPER(translate('%'|| ? ||'%','áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) ");
		if ((datos.getDNombreTutor() != null) && (!"".equals(datos.getDNombreTutor())))
			sqlWhere.append(
					" AND UPPER(translate(d_nombre,'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) LIKE UPPER(translate('%'|| ? ||'%','áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) ");
		if ((datos.getCDni() != null) && (!"".equals(datos.getCDni())))
			sqlWhere.append(" AND UPPER(c_dni) = UPPER(?) ");
		if ((datos.getCTipoTutor() != null) && (!"".equals(datos.getCTipoTutor())))
			sqlWhere.append(" AND c_tipo_tutor = ? ");
		if ((datos.getCTipoDuda() != null) && (!"".equals(datos.getCTipoDuda())))
			sqlWhere.append(" AND c_tipo_duda = ? ");
		if ((datos.getBActivado() != null) && (!"".equals(datos.getBActivado()))) {
			sqlWhere.append(" AND b_activado = ? ");
		}

		return sqlWhere.toString();
	}

	public int buscarTutoresCuenta(OCAPDudasTutoresOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int numTutores = 0;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("SELECT count(*) as numTutores ").append(" FROM ocap_tutores ");

			String sqlWhere = construirWhereBuscarTutores(datos);
			sql.append(sqlWhere);

			st = con.prepareStatement(sql.toString());
			int cont = 1;
			if ((datos.getDApellidosTutor() != null) && (!"".equals(datos.getDApellidosTutor())))
				st.setString(cont++, datos.getDApellidosTutor());
			if ((datos.getDNombreTutor() != null) && (!"".equals(datos.getDNombreTutor())))
				st.setString(cont++, datos.getDNombreTutor());
			if ((datos.getCDni() != null) && (!"".equals(datos.getCDni())))
				st.setString(cont++, datos.getCDni());
			if ((datos.getCTipoTutor() != null) && (!"".equals(datos.getCTipoTutor())))
				st.setString(cont++, datos.getCTipoTutor());
			if ((datos.getCTipoDuda() != null) && (!"".equals(datos.getCTipoDuda())))
				st.setString(cont++, datos.getCTipoDuda());
			if ((datos.getBActivado() != null) && (!"".equals(datos.getBActivado()))) {
				st.setString(cont++, datos.getBActivado());
			}
			rs = st.executeQuery();

			if (rs.next())
				numTutores = rs.getInt("numTutores");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return numTutores;
	}

	public int bajaTutor(long cTutorId) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("UPDATE OCAP_TUTORES ").append(" SET b_borrado = 'Y' ").append(" WHERE c_tutor_id=? ");

			st = con.prepareStatement(sql.toString());
			st.setLong(1, cTutorId);

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

	public int modificarTutor(OCAPDudasTutoresOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("UPDATE OCAP_TUTORES ").append(" SET f_usumodi=SYSDATE ");

			if (datos.getDNombreTutor() != null)
				sql.append(", d_nombre=? ");
			if (datos.getDApellidosTutor() != null)
				sql.append(", d_apellidos=? ");
			if (datos.getCDni() != null)
				sql.append(", c_dni=? ");
			if (datos.getDCorreoElectronicoTutor() != null)
				sql.append(", a_correo_electronico=? ");
			if (datos.getCTipoTutor() != null)
				sql.append(", c_tipo_tutor=? ");
			if (datos.getCTipoDuda() != null)
				sql.append(", c_tipo_duda=? ");
			if (datos.getBActivado() != null)
				sql.append(", b_activado=? ");
			if (datos.getACreadoPor() != null)
				sql.append(", c_usumodi=? ");
			if (datos.getNDudasRecibidas() != 0L)
				sql.append(", n_dudasrecibidas=? ");
			if (datos.getNDudasContestadas() != 0L)
				sql.append(", n_dudascontestadas=? ");
			if (datos.getNControl() != 0L)
				sql.append(", n_control=? ");
			sql.append(" WHERE c_tutor_id=? ");

			st = con.prepareStatement(sql.toString());
			int cont = 1;
			if (datos.getDNombreTutor() != null)
				st.setString(cont++, datos.getDNombreTutor());
			if (datos.getDApellidosTutor() != null)
				st.setString(cont++, datos.getDApellidosTutor());
			if (datos.getCDni() != null)
				st.setString(cont++, datos.getCDni());
			if (datos.getDCorreoElectronicoTutor() != null)
				st.setString(cont++, datos.getDCorreoElectronicoTutor());
			if (datos.getCTipoTutor() != null)
				st.setString(cont++, datos.getCTipoTutor());
			if (datos.getCTipoDuda() != null)
				st.setString(cont++, datos.getCTipoDuda());
			if (datos.getBActivado() != null)
				st.setString(cont++, datos.getBActivado());
			if (datos.getACreadoPor() != null)
				st.setString(cont++, datos.getACreadoPor());
			if (datos.getNDudasRecibidas() != 0L)
				st.setLong(cont++, datos.getNDudasRecibidas());
			if (datos.getNDudasContestadas() != 0L)
				st.setLong(cont++, datos.getNDudasContestadas());
			if (datos.getNControl() != 0L)
				st.setLong(cont++, datos.getNControl());
			st.setLong(cont++, datos.getCTutorId());

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

	public int aumentarContadorRecibidas(OCAPDudasTutoresOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("UPDATE OCAP_TUTORES ").append(" SET f_usumodi=SYSDATE ");

			if (datos.getACreadoPor() != null)
				sql.append(", c_usumodi=? ");
			sql.append(", n_dudasrecibidas= (n_dudasrecibidas + 1) ");
			sql.append(", n_control= (n_control + 1) ");
			sql.append(" WHERE c_tutor_id = ? ");

			st = con.prepareStatement(sql.toString());
			int cont = 1;
			if (datos.getACreadoPor() != null)
				st.setString(cont++, datos.getACreadoPor());
			st.setLong(cont++, datos.getCTutorId());

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

	public int disminuirContadorRecibidas(OCAPDudasTutoresOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("UPDATE OCAP_TUTORES ").append(" SET f_usumodi=SYSDATE ");

			if (datos.getACreadoPor() != null)
				sql.append(", c_usumodi=? ");
			sql.append(", n_dudasrecibidas= (n_dudasrecibidas - 1) ");
			sql.append(" WHERE c_tutor_id = ? ");

			st = con.prepareStatement(sql.toString());
			int cont = 1;
			if (datos.getACreadoPor() != null)
				st.setString(cont++, datos.getACreadoPor());
			st.setLong(cont++, datos.getCTutorId());

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

	public int aumentarContadorContestadas(OCAPDudasTutoresOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("UPDATE OCAP_TUTORES ").append(" SET f_usumodi=SYSDATE ");

			if (datos.getACreadoPor() != null)
				sql.append(", c_usumodi=? ");
			sql.append(", n_dudascontestadas= (n_dudascontestadas+1) ").append(" WHERE c_tutor_id=? ");

			st = con.prepareStatement(sql.toString());
			int cont = 1;
			if (datos.getACreadoPor() != null)
				st.setString(cont++, datos.getACreadoPor());
			st.setLong(cont++, datos.getCTutorId());

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

	public ArrayList buscarTemas(String cTipoDuda) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		OCAPDudasTutoresOT datos = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("SELECT c_tema_id, d_tema, c_tipo_duda ").append(" FROM ocap_temas_dudas ")
					.append(" WHERE b_borrado='N' ");

			if (cTipoDuda != null) {
				sql.append(" AND c_tipo_duda = ? ");
			}
			sql.append(" ORDER BY c_tema_id ");

			st = con.prepareStatement(sql.toString(), 1004, 1008);
			if (cTipoDuda != null) {
				st.setString(1, cTipoDuda);
			}
			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				datos = new OCAPDudasTutoresOT();
				datos.setCTemaId(rs.getLong("c_tema_id"));
				datos.setDTema(rs.getString("d_tema"));
				datos.setCTipoDuda(rs.getString("c_tipo_duda"));

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

	public long altaDuda(OCAPDudasTutoresOT datos) throws SQLException {
		CallableStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		long idDuda = 0L;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("BEGIN INSERT INTO OCAP_DUDAS_EXPTES_CARRERA ")
					.append("(C_DUDA_ID, C_EXP_ID, C_TUTOR_ID, C_TIPO_DUDA, B_TIPO_CAMBIO, C_TEMA_ID, D_CORREO_ELECTRONICO, D_DUDA, B_CONTESTADO, F_RECEPCION, B_LEIDO, B_BORRADO, C_USUALTA, F_USUALTA, B_LLEGA_CAMBIO) ")
					.append("VALUES ")
					.append("(OCAP_DUD_ID_SEQ.nextval, ?, ?, ?, 'N', ?, ?, ?, 'N', SYSDATE, 'N', 'N', ?, SYSDATE, ?) ")
					.append("RETURNING C_DUDA_ID INTO ?; END;");

			st = con.prepareCall(sql.toString());
			st.setLong(1, datos.getCExpId());
			st.setLong(2, datos.getCTutorId());
			st.setString(3, datos.getCTipoDuda());
			st.setLong(4, datos.getCTemaId());
			st.setString(5, datos.getDCorreoElectronico());
			st.setString(6, datos.getDDuda());
			st.setString(7, datos.getACreadoPor());
			st.setString(8, datos.getBProcedeCambio());
			st.registerOutParameter(9, 4);

			filas = st.executeUpdate();
			idDuda = st.getLong(9);
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return idDuda;
	}

	public long buscarTutorAAsignar(String cTipoDuda, int cTipoTutor, long cTutorAnteriorId)
			throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		long cTutorId = 0L;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(" SELECT c_tutor_id FROM ocap_tutores ")
					.append(" WHERE b_borrado='N' AND b_activado='Y' AND c_tipo_tutor=? AND c_tipo_duda= ? ");

			if (cTutorAnteriorId != 0L)
				sql.append(" AND c_tutor_id != ?");
			sql.append("  ORDER BY n_control, c_tutor_id ");

			st = con.prepareStatement(sql.toString());
			st.setInt(1, cTipoTutor);
			st.setString(2, cTipoDuda);
			if (cTutorAnteriorId != 0L) {
				st.setLong(3, cTutorAnteriorId);
			}
			rs = st.executeQuery();

			if (rs.next()) {
				cTutorId = rs.getLong("c_tutor_id");
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

		return cTutorId;
	}

	public String buscarCorreoExp(long cExpId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		String correo = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(" SELECT d_correo_electronico FROM ocap_dudas_exptes_carrera ").append(" WHERE c_exp_id=? ")
					.append(" ORDER BY f_recepcion DESC ");

			st = con.prepareStatement(sql.toString());
			st.setLong(1, cExpId);

			rs = st.executeQuery();

			if (rs.next())
				correo = rs.getString("d_correo_electronico");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return correo;
	}

	public ArrayList buscarDudasTutores(OCAPDudasTutoresOT datosOT, int primerRegistro, int registrosPorPagina,
			String ordenacion, String tipoOrdenacion) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		OCAPDudasTutoresOT datos = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(
					"SELECT duec.c_duda_id, duec.c_exp_id, duec.c_tutor_id, tuto.d_nombre, tuto.d_apellidos, duec.c_tipo_duda, duec.b_tipo_cambio, duec.c_tema_id, tedu.d_tema, duec.b_llega_cambio, ")
					.append(" duec.d_correo_electronico, duec.d_duda, duec.b_contestado, duec.f_recepcion, duec.f_envio_respuesta, duec.b_leido, duec.d_respuesta, duec.n_tiempo_respuesta ")
					.append(" FROM ocap_dudas_exptes_carrera duec, ocap_temas_dudas tedu, ocap_tutores tuto ");

			String sqlWhere = construirWhereBuscarDudasTutores(datosOT);
			sql.append(sqlWhere);
			if ((ordenacion != null) && (!"".equals(ordenacion)))
				sql.append(" ORDER BY ").append(ordenacion).append(" ").append(tipoOrdenacion);
			else {
				sql.append(" ORDER BY f_recepcion ");
			}
			st = con.prepareStatement(sql.toString(), 1004, 1008);

			int cont = 1;
			if (datosOT.getCExpId() != 0L)
				st.setLong(cont++, datosOT.getCExpId());
			if (datosOT.getCTemaId() != 0L)
				st.setLong(cont++, datosOT.getCTemaId());
			if ((datosOT.getBCambio() != null) && (!"".equals(datosOT.getBCambio())))
				st.setString(cont++, datosOT.getBCambio());
			if ((datosOT.getBContestado() != null) && (!"".equals(datosOT.getBContestado())))
				st.setString(cont++, datosOT.getBContestado());
			if ((datosOT.getCTipoDuda() != null) && (!"".equals(datosOT.getCTipoDuda())))
				st.setString(cont++, datosOT.getCTipoDuda());
			if ((datosOT.getFInicio() != null) && (!"".equals(datosOT.getFInicio())))
				st.setString(cont++, datosOT.getFInicio());
			if ((datosOT.getFFin() != null) && (!"".equals(datosOT.getFFin())))
				st.setString(cont++, datosOT.getFFin());
			if ((datosOT.getFInicioRespuesta() != null) && (!"".equals(datosOT.getFInicioRespuesta())))
				st.setString(cont++, datosOT.getFInicioRespuesta());
			if ((datosOT.getFFinRespuesta() != null) && (!"".equals(datosOT.getFFinRespuesta())))
				st.setString(cont++, datosOT.getFFinRespuesta());
			if (datosOT.getCTutorId() != 0L) {
				st.setLong(cont++, datosOT.getCTutorId());
			}
			rs = st.executeQuery();

			if (primerRegistro > 1)
				rs.absolute(primerRegistro - 1);

			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				datos = new OCAPDudasTutoresOT();
				datos.setCDudaId(rs.getLong("c_duda_id"));
				datos.setCExpId(rs.getLong("c_exp_id"));
				DecimalFormat formato = new DecimalFormat("000000");
				datos.setCodigoEPR("EPR" + formato.format(datos.getCExpId()));
				datos.setCTutorId(rs.getLong("c_tutor_id"));
				datos.setCTipoDuda(rs.getString("c_tipo_duda"));
				datos.setBCambio(rs.getString("b_tipo_cambio"));
				datos.setCTemaId(rs.getLong("c_tema_id"));
				datos.setDTema(rs.getString("d_tema"));
				datos.setBProcedeCambio(rs.getString("b_llega_cambio"));
				datos.setDCorreoElectronico(rs.getString("d_correo_electronico"));
				if (rs.getClob("d_duda") != null) {
					InputStream inputStreamDDuda = rs.getClob("d_duda").getAsciiStream();
					StringBuffer dDuda = new StringBuffer();
					int caracter;
					while ((caracter = inputStreamDDuda.read()) != -1) {
						dDuda.append((char) caracter);
					}
					datos.setDDuda(dDuda.toString());
				}
				datos.setBContestado(rs.getString("b_contestado"));
				datos.setBLeido(rs.getString("b_leido"));
				datos.setFRecepcion(
						DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, rs.getTimestamp("f_recepcion")));
				datos.setFEnvioContestacion(
						DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, rs.getTimestamp("f_envio_respuesta")));

				datos.setNTiempoRespuesta(rs.getLong("n_tiempo_respuesta"));
				if (datos.getNTiempoRespuesta() != 0L)
					datos.setTiempoRespuesta(DateUtil.getHorasMinutos(datos.getNTiempoRespuesta()));
				if (rs.getClob("d_respuesta") != null) {
					InputStream inputStreamDRespuesta = rs.getClob("d_respuesta").getAsciiStream();
					StringBuffer dRespuesta = new StringBuffer();
					int caracter;
					while ((caracter = inputStreamDRespuesta.read()) != -1) {
						dRespuesta.append((char) caracter);
					}
					datos.setDRespuesta(dRespuesta.toString());
				}
				datos.setDNombreTutor(rs.getString("d_nombre"));
				datos.setDApellidosTutor(rs.getString("d_apellidos"));

				listado.add(datos);

				if (++i == registrosPorPagina)
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

	private String construirWhereBuscarDudasTutores(OCAPDudasTutoresOT datos) throws Exception {
		StringBuffer sqlWhere = new StringBuffer();
		sqlWhere.append(" WHERE duec.b_borrado='N' AND tedu.b_borrado='N' ")
				.append(" AND duec.c_tema_id = tedu.c_tema_id ").append(" AND duec.c_tutor_id = tuto.c_tutor_id ");

		if (datos.getCExpId() != 0L)
			sqlWhere.append(" AND duec.c_exp_id = ? ");
		if (datos.getCTemaId() != 0L)
			sqlWhere.append(" AND duec.c_tema_id = ? ");
		if ((datos.getBCambio() != null) && (!"".equals(datos.getBCambio())))
			sqlWhere.append(" AND duec.b_tipo_cambio = ? ");
		if ((datos.getBContestado() != null) && (!"".equals(datos.getBContestado())))
			sqlWhere.append(" AND duec.b_contestado = ? ");
		if ((datos.getCTipoDuda() != null) && (!"".equals(datos.getCTipoDuda())))
			sqlWhere.append(" AND duec.c_tipo_duda = ? ");
		if ((datos.getFInicio() != null) && (!"".equals(datos.getFInicio())))
			sqlWhere.append(" AND duec.f_recepcion >= TO_DATE(?, 'DD/MM/RRRR hh24:mi:ss') ");
		if ((datos.getFFin() != null) && (!"".equals(datos.getFFin())))
			sqlWhere.append(" AND duec.f_recepcion <= TO_DATE(?, 'DD/MM/RRRR hh24:mi:ss') ");
		if ((datos.getFInicioRespuesta() != null) && (!"".equals(datos.getFInicioRespuesta())))
			sqlWhere.append(" AND duec.f_envio_respuesta >= TO_DATE(?, 'DD/MM/RRRR hh24:mi:ss') ");
		if ((datos.getFFinRespuesta() != null) && (!"".equals(datos.getFFinRespuesta())))
			sqlWhere.append(" AND duec.f_envio_respuesta <= TO_DATE(?, 'DD/MM/RRRR hh24:mi:ss') ");
		if (datos.getCTutorId() != 0L) {
			sqlWhere.append(" AND duec.c_tutor_id = ? ");
		}
		if ((datos.getCTutorId() == 0L) && (datos.getCTipoDuda() == null)) {
			sqlWhere.append(" AND (duec.c_tipo_duda='1' OR b_contestado='Y')");
		}

		return sqlWhere.toString();
	}

	public int buscarDudasTutoresCuenta(OCAPDudasTutoresOT datosOT) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int numDudas = 0;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("SELECT count(*) as numDudas ")
					.append(" FROM ocap_dudas_exptes_carrera duec, ocap_temas_dudas tedu, ocap_tutores tuto  ");

			String sqlWhere = construirWhereBuscarDudasTutores(datosOT);
			sql.append(sqlWhere);

			st = con.prepareStatement(sql.toString());
			int cont = 1;
			if (datosOT.getCExpId() != 0L)
				st.setLong(cont++, datosOT.getCExpId());
			if (datosOT.getCTemaId() != 0L)
				st.setLong(cont++, datosOT.getCTemaId());
			if ((datosOT.getBCambio() != null) && (!"".equals(datosOT.getBCambio())))
				st.setString(cont++, datosOT.getBCambio());
			if ((datosOT.getBContestado() != null) && (!"".equals(datosOT.getBContestado())))
				st.setString(cont++, datosOT.getBContestado());
			if ((datosOT.getCTipoDuda() != null) && (!"".equals(datosOT.getCTipoDuda())))
				st.setString(cont++, datosOT.getCTipoDuda());
			if ((datosOT.getFInicio() != null) && (!"".equals(datosOT.getFInicio())))
				st.setString(cont++, datosOT.getFInicio());
			if ((datosOT.getFFin() != null) && (!"".equals(datosOT.getFFin())))
				st.setString(cont++, datosOT.getFFin());
			if ((datosOT.getFInicioRespuesta() != null) && (!"".equals(datosOT.getFInicioRespuesta())))
				st.setString(cont++, datosOT.getFInicioRespuesta());
			if ((datosOT.getFFinRespuesta() != null) && (!"".equals(datosOT.getFFinRespuesta())))
				st.setString(cont++, datosOT.getFFinRespuesta());
			if (datosOT.getCTutorId() != 0L) {
				st.setLong(cont++, datosOT.getCTutorId());
			}
			rs = st.executeQuery();

			if (rs.next())
				numDudas = rs.getInt("numDudas");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return numDudas;
	}

	public ArrayList buscarDudasUsuario(OCAPDudasTutoresOT datosOT, int primerRegistro, int registrosPorPagina,
			String ordenacion, String tipoOrdenacion) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		OCAPDudasTutoresOT datos = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(
					"SELECT c_duda_id, c_exp_id, c_tutor_id, duec.c_tipo_duda, b_tipo_cambio, duec.c_tema_id, d_tema, ")
					.append(" d_correo_electronico, d_duda, b_contestado, f_recepcion, f_envio_respuesta, b_leido, d_respuesta, duec.n_tiempo_respuesta ")
					.append(" FROM ocap_dudas_exptes_carrera duec, ocap_temas_dudas tedu ");

			String sqlWhere = construirWhereBuscarDudasUsuario(datosOT);
			sql.append(sqlWhere);
			if ((ordenacion != null) && (!"".equals(ordenacion)))
				sql.append(" ORDER BY ").append(ordenacion).append(" ").append(tipoOrdenacion);
			else {
				sql.append(" ORDER BY f_recepcion ");
			}
			st = con.prepareStatement(sql.toString(), 1004, 1008);

			int cont = 1;
			if (datosOT.getCExpId() != 0L)
				st.setLong(cont++, datosOT.getCExpId());
			if (datosOT.getCTemaId() != 0L)
				st.setLong(cont++, datosOT.getCTemaId());
			if ((datosOT.getBCambio() != null) && (!"".equals(datosOT.getBCambio())))
				st.setString(cont++, datosOT.getBCambio());
			if ((datosOT.getBContestado() != null) && (!"".equals(datosOT.getBContestado())))
				st.setString(cont++, datosOT.getBContestado());
			if ((datosOT.getFInicio() != null) && (!"".equals(datosOT.getFInicio())))
				st.setString(cont++, datosOT.getFInicio());
			if ((datosOT.getFFin() != null) && (!"".equals(datosOT.getFFin())))
				st.setString(cont++, datosOT.getFFin());
			if ((datosOT.getFInicioRespuesta() != null) && (!"".equals(datosOT.getFInicioRespuesta())))
				st.setString(cont++, datosOT.getFInicioRespuesta());
			if ((datosOT.getFFinRespuesta() != null) && (!"".equals(datosOT.getFFinRespuesta()))) {
				st.setString(cont++, datosOT.getFFinRespuesta());
			}
			rs = st.executeQuery();

			if (primerRegistro > 1)
				rs.absolute(primerRegistro - 1);

			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				datos = new OCAPDudasTutoresOT();
				datos.setCDudaId(rs.getLong("c_duda_id"));
				datos.setCExpId(rs.getLong("c_exp_id"));
				DecimalFormat formato = new DecimalFormat("000000");
				datos.setCodigoEPR("EPR" + formato.format(datos.getCExpId()));
				datos.setCTutorId(rs.getLong("c_tutor_id"));
				datos.setCTipoDuda(rs.getString("c_tipo_duda"));
				datos.setBCambio(rs.getString("b_tipo_cambio"));
				datos.setCTemaId(rs.getLong("c_tema_id"));
				datos.setDTema(rs.getString("d_tema"));
				datos.setDCorreoElectronico(rs.getString("d_correo_electronico"));
				if (rs.getClob("d_duda") != null) {
					InputStream inputStreamDDuda = rs.getClob("d_duda").getAsciiStream();
					StringBuffer dDuda = new StringBuffer();
					int caracter;
					while ((caracter = inputStreamDDuda.read()) != -1) {
						dDuda.append((char) caracter);
					}
					datos.setDDuda(dDuda.toString());
				}
				datos.setBContestado(rs.getString("b_contestado"));
				datos.setBLeido(rs.getString("b_leido"));
				datos.setFRecepcion(
						DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, rs.getTimestamp("f_recepcion")));
				datos.setFEnvioContestacion(
						DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, rs.getTimestamp("f_envio_respuesta")));

				datos.setNTiempoRespuesta(rs.getLong("n_tiempo_respuesta"));
				if (datos.getNTiempoRespuesta() != 0L)
					datos.setTiempoRespuesta(DateUtil.getHorasMinutos(datos.getNTiempoRespuesta()));
				if (rs.getClob("d_respuesta") != null) {
					InputStream inputStreamDRespuesta = rs.getClob("d_respuesta").getAsciiStream();
					StringBuffer dRespuesta = new StringBuffer();
					int caracter;
					while ((caracter = inputStreamDRespuesta.read()) != -1) {
						dRespuesta.append((char) caracter);
					}
					datos.setDRespuesta(dRespuesta.toString());
				}

				listado.add(datos);

				if (++i == registrosPorPagina)
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

	private String construirWhereBuscarDudasUsuario(OCAPDudasTutoresOT datos) throws Exception {
		StringBuffer sqlWhere = new StringBuffer();
		sqlWhere.append(" WHERE duec.b_borrado='N' AND tedu.b_borrado='N' ")
				.append(" AND duec.c_tema_id = tedu.c_tema_id ");

		if (datos.getCExpId() != 0L)
			sqlWhere.append(" AND c_exp_id = ? ");
		if (datos.getCTemaId() != 0L)
			sqlWhere.append(" AND duec.c_tema_id = ? ");
		if ((datos.getBCambio() != null) && (!"".equals(datos.getBCambio())))
			sqlWhere.append(" AND b_tipo_cambio = ? ");
		if ((datos.getBContestado() != null) && (!"".equals(datos.getBContestado())))
			sqlWhere.append(" AND b_contestado = ? ");
		if ((datos.getFInicio() != null) && (!"".equals(datos.getFInicio())))
			sqlWhere.append(" AND f_recepcion >= TO_DATE(?, 'DD/MM/RRRR hh24:mi:ss') ");
		if ((datos.getFFin() != null) && (!"".equals(datos.getFFin())))
			sqlWhere.append(" AND f_recepcion <= TO_DATE(?, 'DD/MM/RRRR hh24:mi:ss') ");
		if ((datos.getFInicioRespuesta() != null) && (!"".equals(datos.getFInicioRespuesta())))
			sqlWhere.append(" AND duec.f_envio_respuesta >= TO_DATE(?, 'DD/MM/RRRR hh24:mi:ss') ");
		if ((datos.getFFinRespuesta() != null) && (!"".equals(datos.getFFinRespuesta()))) {
			sqlWhere.append(" AND duec.f_envio_respuesta <= TO_DATE(?, 'DD/MM/RRRR hh24:mi:ss') ");
		}

		return sqlWhere.toString();
	}

	public int buscarDudasUsuarioCuenta(OCAPDudasTutoresOT datosOT) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int numDudas = 0;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("SELECT count(*) as numDudas ")
					.append(" FROM ocap_dudas_exptes_carrera duec, ocap_temas_dudas tedu ");

			String sqlWhere = construirWhereBuscarDudasUsuario(datosOT);
			sql.append(sqlWhere);

			st = con.prepareStatement(sql.toString());
			int cont = 1;
			if (datosOT.getCExpId() != 0L)
				st.setLong(cont++, datosOT.getCExpId());
			if (datosOT.getCTemaId() != 0L)
				st.setLong(cont++, datosOT.getCTemaId());
			if ((datosOT.getBCambio() != null) && (!"".equals(datosOT.getBCambio())))
				st.setString(cont++, datosOT.getBCambio());
			if ((datosOT.getBContestado() != null) && (!"".equals(datosOT.getBContestado())))
				st.setString(cont++, datosOT.getBContestado());
			if ((datosOT.getFInicio() != null) && (!"".equals(datosOT.getFInicio())))
				st.setString(cont++, datosOT.getFInicio());
			if ((datosOT.getFFin() != null) && (!"".equals(datosOT.getFFin())))
				st.setString(cont++, datosOT.getFFin());
			if ((datosOT.getFInicioRespuesta() != null) && (!"".equals(datosOT.getFInicioRespuesta())))
				st.setString(cont++, datosOT.getFInicioRespuesta());
			if ((datosOT.getFFinRespuesta() != null) && (!"".equals(datosOT.getFFinRespuesta()))) {
				st.setString(cont++, datosOT.getFFinRespuesta());
			}
			rs = st.executeQuery();

			if (rs.next())
				numDudas = rs.getInt("numDudas");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return numDudas;
	}

	public OCAPDudasTutoresOT buscarDatosDuda(long cDudaId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		OCAPDudasTutoresOT datos = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(
					"SELECT duec.c_duda_id, duec.c_exp_id, duec.c_tutor_id, duec.c_tipo_duda, duec.b_tipo_cambio, duec.c_tema_id, tedu.d_tema, ")
					.append(" duec.d_correo_electronico, duec.d_duda, duec.b_contestado, duec.b_leido, duec.d_respuesta, ")
					.append(" TO_CHAR(duec.f_recepcion,'DD/MM/RRRR hh24:mi:ss') as f_recepcion, TO_CHAR(duec.f_envio_respuesta,'DD/MM/RRRR hh24:mi:ss') as f_envio_respuesta ")
					.append(" FROM ocap_dudas_exptes_carrera duec, ocap_temas_dudas tedu ")
					.append(" WHERE duec.c_duda_id=? AND duec.b_borrado='N' ")
					.append(" AND duec.c_tema_id = tedu.c_tema_id ");

			st = con.prepareStatement(sql.toString());
			st.setLong(1, cDudaId);

			rs = st.executeQuery();

			if (rs.next()) {
				datos = new OCAPDudasTutoresOT();
				datos.setCDudaId(rs.getLong("c_duda_id"));
				datos.setCExpId(rs.getLong("c_exp_id"));
				DecimalFormat formato = new DecimalFormat("000000");
				datos.setCodigoEPR("EPR" + formato.format(datos.getCExpId()));
				datos.setCTutorId(rs.getLong("c_tutor_id"));
				datos.setCTipoDuda(rs.getString("c_tipo_duda"));
				datos.setBCambio(rs.getString("b_tipo_cambio"));
				datos.setCTemaId(rs.getLong("c_tema_id"));
				datos.setDTema(rs.getString("d_tema"));
				datos.setDCorreoElectronico(rs.getString("d_correo_electronico"));
				if (rs.getClob("d_duda") != null) {
					InputStream inputStreamDDuda = rs.getClob("d_duda").getAsciiStream();
					StringBuffer dDuda = new StringBuffer();
					int caracter;
					while ((caracter = inputStreamDDuda.read()) != -1) {
						dDuda.append((char) caracter);
					}
					datos.setDDuda(dDuda.toString());
				}
				datos.setBContestado(rs.getString("b_contestado"));
				datos.setBLeido(rs.getString("b_leido"));
				datos.setFRecepcion(rs.getString("f_recepcion"));
				datos.setFEnvioContestacion(rs.getString("f_envio_respuesta"));
				if (rs.getClob("d_respuesta") != null) {
					InputStream inputStreamDRespuesta = rs.getClob("d_respuesta").getAsciiStream();
					StringBuffer dRespuesta = new StringBuffer();
					int caracter;
					while ((caracter = inputStreamDRespuesta.read()) != -1) {
						dRespuesta.append((char) caracter);
					}
					datos.setDRespuesta(dRespuesta.toString());
				}
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

	public int contestarDuda(OCAPDudasTutoresOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("UPDATE OCAP_DUDAS_EXPTES_CARRERA ").append(
					" SET f_usumodi=SYSDATE, f_envio_respuesta=SYSDATE, n_tiempo_respuesta=((sysdate - f_recepcion)*24*60*60) ");

			if (datos.getBContestado() != null)
				sql.append(", b_contestado=? ");
			if (datos.getDRespuesta() != null)
				sql.append(", d_respuesta=? ");
			if (datos.getCTutorId() != 0L)
				sql.append(", c_tutor_id=? ");
			if (datos.getBCambio() != null)
				sql.append(", b_tipo_cambio=? ");
			if (datos.getACreadoPor() != null) {
				sql.append(", c_usumodi=? ");
			}
			sql.append(" WHERE c_duda_id=? ");

			st = con.prepareStatement(sql.toString());
			int cont = 1;
			if (datos.getBContestado() != null)
				st.setString(cont++, datos.getBContestado());
			if (datos.getDRespuesta() != null)
				st.setString(cont++, datos.getDRespuesta());
			if (datos.getCTutorId() != 0L)
				st.setLong(cont++, datos.getCTutorId());
			if (datos.getBCambio() != null)
				st.setString(cont++, datos.getBCambio());
			if (datos.getACreadoPor() != null)
				st.setString(cont++, datos.getACreadoPor());
			st.setLong(cont++, datos.getCDudaId());

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

	public int marcarDudaLeida(OCAPDudasTutoresOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("UPDATE OCAP_DUDAS_EXPTES_CARRERA ").append(" SET f_usumodi=SYSDATE ");

			if (datos.getBLeido() != null)
				sql.append(", b_leido=? ");
			if (datos.getACreadoPor() != null) {
				sql.append(", c_usumodi=? ");
			}
			sql.append(" WHERE c_duda_id=? ");

			st = con.prepareStatement(sql.toString());
			int cont = 1;
			if (datos.getBLeido() != null)
				st.setString(cont++, datos.getBLeido());
			if (datos.getACreadoPor() != null)
				st.setString(cont++, datos.getACreadoPor());
			st.setLong(cont++, datos.getCDudaId());

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

	public int modificarDuda(OCAPDudasTutoresOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("UPDATE OCAP_DUDAS_EXPTES_CARRERA ").append(" SET f_usumodi=SYSDATE ");

			if (datos.getCTutorId() != 0L)
				sql.append(", c_tutor_id=? ");
			if (datos.getACreadoPor() != null) {
				sql.append(", c_usumodi=? ");
			}
			sql.append(" WHERE c_duda_id=? ");

			st = con.prepareStatement(sql.toString());
			int cont = 1;
			if (datos.getCTutorId() != 0L)
				st.setLong(cont++, datos.getCTutorId());
			if (datos.getACreadoPor() != null)
				st.setString(cont++, datos.getACreadoPor());
			st.setLong(cont++, datos.getCDudaId());

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

	public ArrayList buscarDudasCSV(OCAPDudasTutoresOT datosOT) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		OCAPDudasTutoresOT datos = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(
					"SELECT duec.c_duda_id, duec.c_exp_id, duec.c_tutor_id, tuto.d_nombre, tuto.d_apellidos, duec.c_tipo_duda, duec.b_tipo_cambio, duec.c_tema_id, tedu.d_tema, duec.b_llega_cambio, ")
					.append(" duec.d_correo_electronico, duec.d_duda, duec.b_contestado, duec.f_recepcion, TO_CHAR(duec.f_envio_respuesta,'DD/MM/RRRR hh24:mi:ss') as f_envio_respuesta, duec.b_leido, duec.d_respuesta, duec.n_tiempo_respuesta, ")
					.append(" usua.c_gerencia_id, gere.D_NOMBRE as dGerencia, exca.c_profesional_id, cpro.d_nombre as dProfesional, exca.C_ESPEC_ID, espe.D_NOMBRE as dEspecialidad ")
					.append(" FROM ocap_dudas_exptes_carrera duec, ocap_temas_dudas tedu, ocap_tutores tuto, ocap_expedientescarrera exca, ocap_usuarios usua, ocap_gerencias gere, ocap_categ_profesionales cpro, ocap_especialidades espe ")
					.append(" WHERE exca.c_exp_id = duec.c_exp_id AND exca.c_usr_id = usua.c_usr_id AND usua.C_GERENCIA_ID = gere.c_gerencia_id ")
					.append(" AND exca.c_profesional_id = cpro.c_profesional_id AND exca.c_espec_id = espe.c_espec_id(+) ")
					.append(" AND duec.c_tema_id = tedu.c_tema_id AND duec.c_tutor_id = tuto.c_tutor_id ")
					.append(" AND duec.b_borrado='N' AND tedu.b_borrado='N' AND exca.b_borrado='N' AND usua.b_borrado='N' ");

			if (datosOT.getCExpId() != 0L)
				sql.append(" AND duec.c_exp_id = ? ");
			if ((datosOT.getFInicio() != null) && (!"".equals(datosOT.getFInicio())))
				sql.append(" AND duec.f_recepcion >= TO_DATE(?, 'DD/MM/RRRR hh24:mi:ss') ");
			if ((datosOT.getFFin() != null) && (!"".equals(datosOT.getFFin())))
				sql.append(" AND duec.f_recepcion <= TO_DATE(?, 'DD/MM/RRRR hh24:mi:ss') ");
			if ((datosOT.getFInicioRespuesta() != null) && (!"".equals(datosOT.getFInicioRespuesta())))
				sql.append(" AND duec.f_envio_respuesta >= TO_DATE(?, 'DD/MM/RRRR hh24:mi:ss') ");
			if ((datosOT.getFFinRespuesta() != null) && (!"".equals(datosOT.getFFinRespuesta())))
				sql.append(" AND duec.f_envio_respuesta <= TO_DATE(?, 'DD/MM/RRRR hh24:mi:ss') ");
			if (((datosOT.getFInicioRespuesta() != null) && (!"".equals(datosOT.getFInicioRespuesta())))
					|| ((datosOT.getFFinRespuesta() != null) && (!"".equals(datosOT.getFFinRespuesta())))) {
				sql.append(" AND duec.f_envio_respuesta is not null ");
			}
			if (datosOT.getCGerenciaId() != 0L)
				sql.append(" AND usua.c_gerencia_id = ? ");
			if (datosOT.getCProfesionalId() != 0L)
				sql.append(" AND cpro.c_profesional_id = ? ");
			if (datosOT.getCEspecialidadId() != 0L)
				sql.append(" AND espe.c_espec_id = ? ");
			if (datosOT.getCTemaId() != 0L)
				sql.append(" AND duec.c_tema_id = ? ");
			if ((datosOT.getCTipoDuda() != null) && (!"".equals(datosOT.getCTipoDuda())))
				sql.append(" AND duec.c_tipo_duda = ? ");
			if ((datosOT.getBContestado() != null) && (!"".equals(datosOT.getBContestado())))
				sql.append(" AND duec.b_contestado = ? ");
			sql.append(" ORDER BY f_recepcion ");

			st = con.prepareStatement(sql.toString(), 1004, 1008);

			int cont = 1;
			if (datosOT.getCExpId() != 0L)
				st.setLong(cont++, datosOT.getCExpId());
			if ((datosOT.getFInicio() != null) && (!"".equals(datosOT.getFInicio())))
				st.setString(cont++, datosOT.getFInicio());
			if ((datosOT.getFFin() != null) && (!"".equals(datosOT.getFFin())))
				st.setString(cont++, datosOT.getFFin());
			if ((datosOT.getFInicioRespuesta() != null) && (!"".equals(datosOT.getFInicioRespuesta())))
				st.setString(cont++, datosOT.getFInicioRespuesta());
			if ((datosOT.getFFinRespuesta() != null) && (!"".equals(datosOT.getFFinRespuesta())))
				st.setString(cont++, datosOT.getFFinRespuesta());
			if (datosOT.getCGerenciaId() != 0L)
				st.setLong(cont++, datosOT.getCGerenciaId());
			if (datosOT.getCProfesionalId() != 0L)
				st.setLong(cont++, datosOT.getCProfesionalId());
			if (datosOT.getCEspecialidadId() != 0L)
				st.setLong(cont++, datosOT.getCEspecialidadId());
			if (datosOT.getCTemaId() != 0L)
				st.setLong(cont++, datosOT.getCTemaId());
			if ((datosOT.getCTipoDuda() != null) && (!"".equals(datosOT.getCTipoDuda())))
				st.setString(cont++, datosOT.getCTipoDuda());
			if ((datosOT.getBContestado() != null) && (!"".equals(datosOT.getBContestado()))) {
				st.setString(cont++, datosOT.getBContestado());
			}
			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				datos = new OCAPDudasTutoresOT();
				datos.setCDudaId(rs.getLong("c_duda_id"));
				datos.setCExpId(rs.getLong("c_exp_id"));
				DecimalFormat formato = new DecimalFormat("000000");
				datos.setCodigoEPR("EPR" + formato.format(datos.getCExpId()));
				datos.setCTutorId(rs.getLong("c_tutor_id"));
				datos.setCTipoDuda(rs.getString("c_tipo_duda"));
				datos.setBCambio(rs.getString("b_tipo_cambio"));
				datos.setCTemaId(rs.getLong("c_tema_id"));
				datos.setDTema(rs.getString("d_tema"));
				datos.setBProcedeCambio(rs.getString("b_llega_cambio"));
				datos.setDCorreoElectronico(rs.getString("d_correo_electronico"));
				if (rs.getClob("d_duda") != null) {
					InputStream inputStreamDDuda = rs.getClob("d_duda").getAsciiStream();
					StringBuffer dDuda = new StringBuffer();
					int caracter;
					while ((caracter = inputStreamDDuda.read()) != -1) {
						dDuda.append((char) caracter);
					}
					datos.setDDuda(dDuda.toString());
				}
				datos.setBContestado(rs.getString("b_contestado"));
				datos.setBLeido(rs.getString("b_leido"));
				datos.setFRecepcion(
						DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, rs.getTimestamp("f_recepcion")));
				datos.setFEnvioContestacion(rs.getString("f_envio_respuesta"));

				datos.setNTiempoRespuesta(rs.getLong("n_tiempo_respuesta"));
				if (datos.getNTiempoRespuesta() != 0L)
					datos.setTiempoRespuesta(DateUtil.getHorasMinutos(datos.getNTiempoRespuesta()));
				datos.setFEnvioContestacion(datos.getFEnvioContestacion() == null ? "" : datos.getFEnvioContestacion());
				if (rs.getClob("d_respuesta") != null) {
					InputStream inputStreamDRespuesta = rs.getClob("d_respuesta").getAsciiStream();
					StringBuffer dRespuesta = new StringBuffer();
					int caracter;
					while ((caracter = inputStreamDRespuesta.read()) != -1) {
						dRespuesta.append((char) caracter);
					}
					datos.setDRespuesta(dRespuesta.toString());
				}
				datos.setDNombreTutor(rs.getString("d_nombre"));
				datos.setDApellidosTutor(rs.getString("d_apellidos"));
				datos.setCGerenciaId(rs.getLong("c_gerencia_id"));
				datos.setDGerencia(rs.getString("dGerencia"));
				datos.setCProfesionalId(rs.getLong("c_profesional_id"));
				datos.setDProfesional(rs.getString("dProfesional"));
				datos.setCEspecialidadId(rs.getLong("c_espec_id"));
				datos.setDEspecialidad(rs.getString("dEspecialidad") == null ? "" : rs.getString("dEspecialidad"));

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

	public ArrayList buscarDudas(OCAPDudasTutoresOT datosOT, int primerRegistro, int registrosPorPagina,
			String ordenacion, String tipoOrdenacion) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		OCAPDudasTutoresOT datos = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(
					"SELECT duec.c_duda_id, duec.c_exp_id, duec.c_tutor_id, tuto.d_nombre, tuto.d_apellidos, duec.c_tipo_duda, duec.b_tipo_cambio, duec.c_tema_id, tedu.d_tema, duec.b_llega_cambio, ")
					.append(" duec.d_correo_electronico, duec.d_duda, duec.b_contestado, duec.f_recepcion, duec.f_envio_respuesta, duec.b_leido, duec.d_respuesta, duec.n_tiempo_respuesta, ")
					.append(" usua.c_gerencia_id, gere.D_NOMBRE as dGerencia, exca.c_profesional_id, cpro.d_nombre as dProfesional, exca.C_ESPEC_ID, espe.D_NOMBRE as dEspecialidad ")
					.append(" FROM ocap_dudas_exptes_carrera duec, ocap_temas_dudas tedu, ocap_tutores tuto, ocap_expedientescarrera exca, ocap_usuarios usua, ocap_gerencias gere, ocap_categ_profesionales cpro, ocap_especialidades espe ");

			String sqlWhere = construirWhereBuscarDudas(datosOT);
			sql.append(sqlWhere);
			if ((ordenacion != null) && (!"".equals(ordenacion)))
				sql.append(" ORDER BY ").append(ordenacion).append(" ").append(tipoOrdenacion);
			else {
				sql.append(" ORDER BY f_recepcion ");
			}
			st = con.prepareStatement(sql.toString(), 1004, 1008);

			int cont = 1;
			if (datosOT.getCExpId() != 0L)
				st.setLong(cont++, datosOT.getCExpId());
			if (datosOT.getCTemaId() != 0L)
				st.setLong(cont++, datosOT.getCTemaId());
			if ((datosOT.getBContestado() != null) && (!"".equals(datosOT.getBContestado())))
				st.setString(cont++, datosOT.getBContestado());
			if ((datosOT.getCTipoDuda() != null) && (!"".equals(datosOT.getCTipoDuda())))
				st.setString(cont++, datosOT.getCTipoDuda());
			if ((datosOT.getFInicio() != null) && (!"".equals(datosOT.getFInicio())))
				st.setString(cont++, datosOT.getFInicio());
			if ((datosOT.getFFin() != null) && (!"".equals(datosOT.getFFin())))
				st.setString(cont++, datosOT.getFFin());
			if ((datosOT.getFInicioRespuesta() != null) && (!"".equals(datosOT.getFInicioRespuesta())))
				st.setString(cont++, datosOT.getFInicioRespuesta());
			if ((datosOT.getFFinRespuesta() != null) && (!"".equals(datosOT.getFFinRespuesta())))
				st.setString(cont++, datosOT.getFFinRespuesta());
			if (datosOT.getCGerenciaId() != 0L)
				st.setLong(cont++, datosOT.getCGerenciaId());
			if (datosOT.getCProfesionalId() != 0L)
				st.setLong(cont++, datosOT.getCProfesionalId());
			if (datosOT.getCEspecialidadId() != 0L) {
				st.setLong(cont++, datosOT.getCEspecialidadId());
			}
			rs = st.executeQuery();

			if (primerRegistro > 1)
				rs.absolute(primerRegistro - 1);

			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				datos = new OCAPDudasTutoresOT();
				datos.setCDudaId(rs.getLong("c_duda_id"));
				datos.setCExpId(rs.getLong("c_exp_id"));
				DecimalFormat formato = new DecimalFormat("000000");
				datos.setCodigoEPR("EPR" + formato.format(datos.getCExpId()));
				datos.setCTutorId(rs.getLong("c_tutor_id"));
				datos.setCTipoDuda(rs.getString("c_tipo_duda"));
				datos.setBCambio(rs.getString("b_tipo_cambio"));
				datos.setCTemaId(rs.getLong("c_tema_id"));
				datos.setDTema(rs.getString("d_tema"));
				datos.setBProcedeCambio(rs.getString("b_llega_cambio"));
				datos.setDCorreoElectronico(rs.getString("d_correo_electronico"));
				if (rs.getClob("d_duda") != null) {
					InputStream inputStreamDDuda = rs.getClob("d_duda").getAsciiStream();
					StringBuffer dDuda = new StringBuffer();
					int caracter;
					while ((caracter = inputStreamDDuda.read()) != -1) {
						dDuda.append((char) caracter);
					}
					datos.setDDuda(dDuda.toString());
				}
				datos.setBContestado(rs.getString("b_contestado"));
				datos.setBLeido(rs.getString("b_leido"));
				datos.setFRecepcion(
						DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, rs.getTimestamp("f_recepcion")));
				datos.setFEnvioContestacion(
						DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, rs.getTimestamp("f_envio_respuesta")));

				datos.setNTiempoRespuesta(rs.getLong("n_tiempo_respuesta"));
				if (datos.getNTiempoRespuesta() != 0L)
					datos.setTiempoRespuesta(DateUtil.getHorasMinutos(datos.getNTiempoRespuesta()));
				if (rs.getClob("d_respuesta") != null) {
					InputStream inputStreamDRespuesta = rs.getClob("d_respuesta").getAsciiStream();
					StringBuffer dRespuesta = new StringBuffer();
					int caracter;
					while ((caracter = inputStreamDRespuesta.read()) != -1) {
						dRespuesta.append((char) caracter);
					}
					datos.setDRespuesta(dRespuesta.toString());
				}
				datos.setDNombreTutor(rs.getString("d_nombre"));
				datos.setDApellidosTutor(rs.getString("d_apellidos"));

				datos.setDProfesional(rs.getString("dProfesional"));
				if (rs.getString("dEspecialidad") != null) {
					datos.setDProfesional(datos.getDProfesional() + "/" + rs.getString("dEspecialidad"));
				}
				listado.add(datos);

				if (++i == registrosPorPagina)
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

	private String construirWhereBuscarDudas(OCAPDudasTutoresOT datos) throws Exception {
		StringBuffer sqlWhere = new StringBuffer();
		sqlWhere.append(
				" WHERE exca.c_exp_id = duec.c_exp_id AND exca.c_usr_id = usua.c_usr_id AND usua.C_GERENCIA_ID = gere.c_gerencia_id ")
				.append(" AND exca.c_profesional_id = cpro.c_profesional_id AND exca.c_espec_id = espe.c_espec_id(+) ")
				.append(" AND duec.c_tema_id = tedu.c_tema_id AND duec.c_tutor_id = tuto.c_tutor_id ")
				.append(" AND duec.b_borrado='N' AND tedu.b_borrado='N' AND exca.b_borrado='N' AND usua.b_borrado='N' ");

		if (datos.getCExpId() != 0L)
			sqlWhere.append(" AND duec.c_exp_id = ? ");
		if (datos.getCTemaId() != 0L)
			sqlWhere.append(" AND duec.c_tema_id = ? ");
		if ((datos.getBContestado() != null) && (!"".equals(datos.getBContestado())))
			sqlWhere.append(" AND duec.b_contestado = ? ");
		if ((datos.getCTipoDuda() != null) && (!"".equals(datos.getCTipoDuda())))
			sqlWhere.append(" AND duec.c_tipo_duda = ? ");
		if ((datos.getFInicio() != null) && (!"".equals(datos.getFInicio())))
			sqlWhere.append(" AND duec.f_recepcion >= TO_DATE(?, 'DD/MM/RRRR hh24:mi:ss') ");
		if ((datos.getFFin() != null) && (!"".equals(datos.getFFin())))
			sqlWhere.append(" AND duec.f_recepcion <= TO_DATE(?, 'DD/MM/RRRR hh24:mi:ss') ");
		if ((datos.getFInicioRespuesta() != null) && (!"".equals(datos.getFInicioRespuesta())))
			sqlWhere.append(" AND duec.f_envio_respuesta >= TO_DATE(?, 'DD/MM/RRRR hh24:mi:ss') ");
		if ((datos.getFFinRespuesta() != null) && (!"".equals(datos.getFFinRespuesta())))
			sqlWhere.append(" AND duec.f_envio_respuesta <= TO_DATE(?, 'DD/MM/RRRR hh24:mi:ss') ");
		if (datos.getCGerenciaId() != 0L)
			sqlWhere.append(" AND usua.c_gerencia_id = ? ");
		if (datos.getCProfesionalId() != 0L)
			sqlWhere.append(" AND cpro.c_profesional_id = ? ");
		if (datos.getCEspecialidadId() != 0L) {
			sqlWhere.append(" AND espe.c_espec_id = ? ");
		}

		return sqlWhere.toString();
	}

	public int buscarDudasCuenta(OCAPDudasTutoresOT datosOT) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int numDudas = 0;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("SELECT count(*) as numDudas ").append(
					" FROM ocap_dudas_exptes_carrera duec, ocap_temas_dudas tedu, ocap_tutores tuto, ocap_expedientescarrera exca, ocap_usuarios usua, ocap_gerencias gere, ocap_categ_profesionales cpro, ocap_especialidades espe ");

			String sqlWhere = construirWhereBuscarDudas(datosOT);
			sql.append(sqlWhere);

			st = con.prepareStatement(sql.toString());
			int cont = 1;
			if (datosOT.getCExpId() != 0L)
				st.setLong(cont++, datosOT.getCExpId());
			if (datosOT.getCTemaId() != 0L)
				st.setLong(cont++, datosOT.getCTemaId());
			if ((datosOT.getBContestado() != null) && (!"".equals(datosOT.getBContestado())))
				st.setString(cont++, datosOT.getBContestado());
			if ((datosOT.getCTipoDuda() != null) && (!"".equals(datosOT.getCTipoDuda())))
				st.setString(cont++, datosOT.getCTipoDuda());
			if ((datosOT.getFInicio() != null) && (!"".equals(datosOT.getFInicio())))
				st.setString(cont++, datosOT.getFInicio());
			if ((datosOT.getFFin() != null) && (!"".equals(datosOT.getFFin())))
				st.setString(cont++, datosOT.getFFin());
			if ((datosOT.getFInicioRespuesta() != null) && (!"".equals(datosOT.getFInicioRespuesta())))
				st.setString(cont++, datosOT.getFInicioRespuesta());
			if ((datosOT.getFFinRespuesta() != null) && (!"".equals(datosOT.getFFinRespuesta())))
				st.setString(cont++, datosOT.getFFinRespuesta());
			if (datosOT.getCGerenciaId() != 0L)
				st.setLong(cont++, datosOT.getCGerenciaId());
			if (datosOT.getCProfesionalId() != 0L)
				st.setLong(cont++, datosOT.getCProfesionalId());
			if (datosOT.getCEspecialidadId() != 0L) {
				st.setLong(cont++, datosOT.getCEspecialidadId());
			}
			rs = st.executeQuery();

			if (rs.next())
				numDudas = rs.getInt("numDudas");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return numDudas;
	}
}
