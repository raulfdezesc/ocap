package es.jcyl.fqs.ocap.oad.preguntas;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.preguntas.OCAPPreguntasOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPPreguntasOAD {
	public Logger logger;
	private static OCAPPreguntasOAD instance;

	private void $init$() {
		this.logger = OCAPConfigApp.logger;
	}

	public static OCAPPreguntasOAD getInstance() {
		if (instance == null) {
			instance = new OCAPPreguntasOAD();
		}
		return instance;
	}

	private OCAPPreguntasOAD() {
		$init$();
	}

	public ArrayList buscarOCAPPreguntas(long idPregunta, String dNombre, int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		OCAPPreguntasOT datos = null;
		ArrayList listado = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(
					" SELECT C_PREGUNTA_ID, D_NOMBRE, D_OBSERVACIONES, C_TIPO_PREGUNTA_ID, F_USUALTA, F_USUMODI, N_ELEMENTOS, N_NIVEL, N_SUB_ELEMENTOS, C_USUALTA, ")
					.append(" C_USUMODI, B_NUMERACION, B_CORTO ").append(" FROM ITCP_PREGUNTAS ")
					.append(" WHERE UPPER (TRANSLATE (D_NOMBRE, 'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) LIKE UPPER (TRANSLATE ('%")
					.append(dNombre).append("%', 'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) ");

			String idP = String.valueOf(idPregunta);
			Long idPreg = Long.valueOf(idP);

			if ((idPreg != null) && (!idPreg.equals(Long.valueOf("0")))) {
				sql.append(" AND C_PREGUNTA_ID = ").append(idPreg);
			}

			sql.append(" ORDER BY c_pregunta_id");
			st = con.prepareStatement(sql.toString(), 1004, 1008);

			rs = st.executeQuery();
			if (inicio > 1)
				rs.absolute(inicio - 1);
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				datos = new OCAPPreguntasOT();
				datos.setCPreguntaId(rs.getLong("C_PREGUNTA_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
				datos.setCTipoPregunta(rs.getString("C_TIPO_PREGUNTA_ID"));
				datos.setFUsuAlta(rs.getDate("F_USUALTA"));
				datos.setFUsuModi(rs.getDate("F_USUMODI"));
				datos.setNElementos(rs.getLong("N_ELEMENTOS"));
				datos.setNNivel(rs.getLong("N_NIVEL"));
				datos.setNSubElementos(rs.getLong("N_SUB_ELEMENTOS"));
				datos.setCUsuAlta(rs.getString("C_USUALTA"));
				datos.setCUsuModi(rs.getString("C_USUMODI"));
				datos.setBNumeracion(rs.getString("B_NUMERACION"));
				datos.setBCorto(rs.getString("B_CORTO"));

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

	public int listadoOCAPPreguntasCuenta(String dNombre) throws Exception {
		ResultSet rs = null;
		PreparedStatement st = null;
		String where = "";

		Connection con = null;
		int total = -1;
		try {
			con = JCYLGestionTransacciones.getConnection();

			StringBuffer sql = new StringBuffer();
			sql.append(
					" SELECT COUNT(C_PREGUNTA_ID) FROM (SELECT C_PREGUNTA_ID, D_NOMBRE, D_OBSERVACIONES, C_TIPO_PREGUNTA_ID, F_USUALTA, F_USUMODI, N_ELEMENTOS, ")
					.append(" N_NIVEL, N_SUB_ELEMENTOS, C_USUALTA, C_USUMODI, B_NUMERACION, B_CORTO ")
					.append(" FROM ITCP_PREGUNTAS ")
					.append(" WHERE UPPER (TRANSLATE (D_NOMBRE, 'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) LIKE UPPER (TRANSLATE ('%")
					.append(dNombre).append("%', 'áéíóúÁÉÍÓÚ', 'aeiouAEIOU'))) ");

			String sqlStatement = sql.toString() + where;

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

	public OCAPPreguntasOT buscarOCAPPreguntaId(long cPreguntaId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		OCAPPreguntasOT datos = null;
		try {
			String sql = "SELECT C_PREGUNTA_ID, D_NOMBRE, D_OBSERVACIONES, C_TIPO_PREGUNTA_ID, F_USUALTA, F_USUMODI, N_ELEMENTOS, N_NIVEL, N_SUB_ELEMENTOS, C_USUALTA, C_USUMODI, B_NUMERACION, B_CORTO FROM ITCP_PREGUNTAS WHERE C_PREGUNTA_ID = ? ";

			st = con.prepareStatement(sql);
			st.setLong(1, cPreguntaId);
			rs = st.executeQuery();
			datos = new OCAPPreguntasOT();
			if (rs.next()) {
				datos.setCPreguntaId(rs.getLong("C_PREGUNTA_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
				datos.setCTipoPregunta(rs.getString("C_TIPO_PREGUNTA_ID"));
				datos.setFUsuAlta(rs.getDate("F_USUALTA"));
				datos.setFUsuModi(rs.getDate("F_USUMODI"));
				datos.setNElementos(rs.getLong("N_ELEMENTOS"));
				datos.setNNivel(rs.getLong("N_NIVEL"));
				datos.setNSubElementos(rs.getLong("N_SUB_ELEMENTOS"));
				datos.setCUsuAlta(rs.getString("C_USUALTA"));
				datos.setCUsuModi(rs.getString("C_USUMODI"));
				datos.setBNumeracion(rs.getString("B_NUMERACION"));
				datos.setBCorto(rs.getString("B_CORTO"));
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

	public int altaOCAPPreguntas(OCAPPreguntasOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			String sql = "INSERT INTO ITCP_PREGUNTAS (C_PREGUNTA_ID, D_NOMBRE, D_OBSERVACIONES, C_TIPO_PREGUNTA_ID, F_USUALTA, F_USUMODI, N_ELEMENTOS, N_NIVEL, N_SUB_ELEMENTOS, C_USUALTA, C_USUMODI, B_NUMERACION, B_CORTO) VALUES (?, ?, ?, ?, SYSDATE, SYSDATE, ?, ?, ?, ?, ?, ?, ?)";

			st = con.prepareStatement(sql);

			st.setLong(1, datos.getCPreguntaId());
			st.setString(2, datos.getDNombre());
			st.setString(3, datos.getDObservaciones());
			st.setString(4, datos.getCTipoPregunta());
			st.setLong(5, datos.getNElementos());
			st.setLong(6, datos.getNNivel());
			st.setLong(7, datos.getNSubElementos());
			st.setNull(8, 12);
			st.setNull(9, 12);
			st.setString(10, datos.getBNumeracion());
			st.setString(11, datos.getBCorto());

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

	public int altaOCAPRespuestas(OCAPPreguntasOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			String sql = "INSERT INTO ITCP_POSIBLES_RESPUESTAS (C_RESPUESTA_ID, C_CUESTIONARIO_ID, C_PREGUNTA_ID, D_NOMBRE, D_VALOR, N_CREDITOS, C_USUALTA, C_USUMODI) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			st = con.prepareStatement(sql);

			st.setLong(1, datos.getCRespuestaId());
			st.setLong(2, datos.getCCuestionarioId());
			st.setLong(3, datos.getCPreguntaId());
			st.setString(4, datos.getDNombreRespuesta());
			st.setString(5, datos.getDValor());
			st.setFloat(6, datos.getNCreditos());
			st.setString(7, datos.getCUsuAlta());
			st.setString(8, datos.getCUsuModi());

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

	public OCAPPreguntasOT buscarOCAPPregunta(long cPreguntaId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		OCAPPreguntasOT datos = null;
		try {
			String sql = "SELECT C_PREGUNTA_ID, D_NOMBRE, D_OBSERVACIONES, C_TIPO_PREGUNTA_ID, N_ELEMENTOS, N_SUB_ELEMENTOS FROM ITCP_PREGUNTAS pre WHERE pre.C_PREGUNTA_ID = ?";

			st = con.prepareStatement(sql);
			st.setLong(1, cPreguntaId);
			rs = st.executeQuery();
			datos = new OCAPPreguntasOT();
			if (rs.next()) {
				datos.setCPreguntaId(rs.getLong("C_PREGUNTA_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
				datos.setCTipoPregunta(rs.getString("C_TIPO_PREGUNTA_ID"));
				datos.setNElementos(rs.getLong("N_ELEMENTOS"));
				datos.setNSubElementos(rs.getLong("N_SUB_ELEMENTOS"));
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
}
