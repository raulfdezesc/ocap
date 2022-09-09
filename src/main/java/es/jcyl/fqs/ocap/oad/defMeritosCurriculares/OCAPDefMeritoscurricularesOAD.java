package es.jcyl.fqs.ocap.oad.defMeritosCurriculares;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.defMeritosCurriculares.OCAPDefMeritoscurricularesOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPDefMeritoscurricularesOAD {
	public Logger loggerBD;
	private static OCAPDefMeritoscurricularesOAD instance;

	private void $init$() {
		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public static OCAPDefMeritoscurricularesOAD getInstance() {
		if (instance == null) {
			instance = new OCAPDefMeritoscurricularesOAD();
		}
		return instance;
	}

	private OCAPDefMeritoscurricularesOAD() {
		$init$();
	}

	public int altaOCAPDefMeritoscurriculares(OCAPDefMeritoscurricularesOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_DEF_MERITOSCURRICULARES (B_BORRADO, D_NOMBRE,  F_USUALTA, D_DESCRIPCION, C_DEFMERITO_ID, C_USUALTA) VALUES ('N', ?, SYSDATE, ?, OCAP_DEF_ID_SEQ.nextval, ?)";

			st = con.prepareStatement(sql);
			st.setString(1, datos.getDNombre());

			if (datos.getDDescripcion() != null)
				st.setString(2, datos.getDDescripcion());
			else
				st.setNull(2, 12);
			st.setString(3, datos.getACreadoPor());

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

	public int bajaOCAPDefMeritoscurriculares(int cDefmeritoId) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_DEF_MERITOSCURRICULARES SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_DEFMERITO_ID =  ?";

			st = con.prepareStatement(sql);

			st.setInt(1, cDefmeritoId);

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

	public int modificacionOCAPDefMeritoscurriculares(OCAPDefMeritoscurricularesOT datos)
			throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_DEF_MERITOSCURRICULARES SET D_NOMBRE = ?, F_USUMODI = SYSDATE, D_DESCRIPCION = ? WHERE C_DEFMERITO_ID = ?";

			st = con.prepareStatement(sql);

			st.setString(1, datos.getDNombre());

			if (datos.getDDescripcion() != null)
				st.setString(2, datos.getDDescripcion());
			else {
				st.setNull(2, 12);
			}
			st.setLong(3, datos.getCDefmeritoId());

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

	public OCAPDefMeritoscurricularesOT buscarOCAPDefMeritoscurriculares(long cDefmeritoId)
			throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;

		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPDefMeritoscurricularesOT datos = null;
		try {
			String sql = "SELECT B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION, C_DEFMERITO_ID FROM OCAP_DEF_MERITOSCURRICULARES WHERE C_DEFMERITO_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);

			st.setLong(1, cDefmeritoId);

			rs = st.executeQuery();

			datos = new OCAPDefMeritoscurricularesOT();
			if (rs.next()) {
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setCDefmeritoId(rs.getLong("C_DEFMERITO_ID"));
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

	public ArrayList listadoOCAPDefMeritoscurriculares() throws SQLException, Exception {
		return listadoOCAPDefMeritoscurriculares(1, -1);
	}

	public ArrayList listadoOCAPDefMeritoscurriculares(int inicio, int cuantos) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;

		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION, C_DEFMERITO_ID FROM OCAP_DEF_MERITOSCURRICULARES WHERE B_BORRADO = 'N' order by C_DEFMERITO_ID";

			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPDefMeritoscurricularesOT datos = new OCAPDefMeritoscurricularesOT();
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setCDefmeritoId(rs.getLong("C_DEFMERITO_ID"));

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

	public int listadoOCAPDefMeritoscurricularesCuenta(long cDefmeritoId, String dNombre, String dDescripcion,
			String fCreacion, String fModificacion) throws SQLException, Exception {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int total = -1;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);

			String sql = "SELECT COUNT(*) FROM OCAP_DEF_MERITOSCURRICULARES ";

			boolean isCDefmeritoId = cDefmeritoId != 0L;
			if (isCDefmeritoId)
				sbWhere.append("C_DEFMERITO_ID = ").append(cDefmeritoId).append(" AND ");
			boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
			if (isDNombre)
				sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
			boolean isDDescripcion = (dDescripcion != null) && (!dDescripcion.equals(""));
			if (isDDescripcion)
				sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dDescripcion).append("%')) AND ");
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

			st = con.prepareStatement(sqlStatement);

			rs = st.executeQuery(sqlStatement);
			if (rs.next())
				total = rs.getInt(1);
		} catch (SQLException ex) {
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

	public ArrayList consultaOCAPDefMeritoscurriculares(long cDefmeritoId, String dNombre, String dDescripcion,
			String fCreacion, String fModificacion, int inicio, int cuantos) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT C_DEFMERITO_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION FROM OCAP_DEF_MERITOSCURRICULARES";

			String orderBy = " order by C_DEFMERITO_ID";
			boolean isCDefmeritoId = cDefmeritoId != 0L;
			if (isCDefmeritoId)
				sbWhere.append("C_DEFMERITO_ID = ?  AND ");
			boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
			if (isDNombre)
				sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
			boolean isDDescripcion = (dDescripcion != null) && (!dDescripcion.equals(""));
			if (isDDescripcion)
				sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dDescripcion).append("%')) AND ");
			boolean isFCreacion = (fCreacion != null) && (!fCreacion.equals(""));
			if (isFCreacion)
				sbWhere.append("to_char(F_USUALTA,'DD/MM/RRRR') = '").append(fCreacion).append("' AND ");
			boolean isFModificacion = (fModificacion != null) && (!fModificacion.equals(""));
			if (isFModificacion) {
				sbWhere.append("to_char(F_USUMODI,'DD/MM/RRRR') = '").append(fModificacion).append("' AND ");
			}
			sbWhere.append("B_BORRADO = 'N'  AND ");
			int len = sbWhere.length();
			if (len > 0) {
				where = sbWhere.insert(0, " Where ").substring(0, len + 1);
			}
			String sqlStatement = selectFrom + where + orderBy;

			this.loggerBD.info("Sentencia SQL:" + sqlStatement);

			st = con.prepareStatement(sqlStatement, 1004, 1008);

			int index = 1;
			if (isCDefmeritoId) {
				st.setLong(index++, cDefmeritoId);
			}
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPDefMeritoscurricularesOT datos = new OCAPDefMeritoscurricularesOT();
				datos.setCDefmeritoId(rs.getLong("C_DEFMERITO_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setBBorrado(rs.getString("B_BORRADO"));

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

	public ArrayList listadoOCAPDefMeritoscurricularesPorCatProfesional(long gradoId, long categoriaProfesionalId)
			throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT defm.c_defmerito_id, defm.d_nombre, defm.d_descripcion, NVL(cred.presente,0) presente ")
					.append(" FROM ( ").append(" SELECT crdi.c_defmerito_id, count(crdi.c_credito_id) presente ")
					.append(" FROM ( ").append(" SELECT c_defmerito_id, c_credito_id, c_grado_id ")
					.append(" FROM ocap_creditos ").append(" WHERE c_grado_id = ? ").append(" AND b_borrado='N' ")
					.append(" AND c_estatut_id = (SELECT DISTINCT c_estatut_id FROM ocap_categ_profesionales WHERE c_profesional_id = ?)  ")
					.append(" ) crdi ").append(" GROUP BY crdi.c_defmerito_id ").append(" ) cred,")
					.append(" ocap_def_meritoscurriculares defm ").append(" WHERE  defm.b_borrado = 'N' ")
					.append(" AND defm.c_defmerito_id = cred.c_defmerito_id(+) ").append(" ORDER BY c_defmerito_id ");

			st = con.prepareStatement(sql.toString());

			st.setLong(1, gradoId);
			st.setLong(2, categoriaProfesionalId);

			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				OCAPDefMeritoscurricularesOT datos = new OCAPDefMeritoscurricularesOT();
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setCDefmeritoId(rs.getLong("C_DEFMERITO_ID"));
				if (rs.getInt("PRESENTE") > 0)
					datos.setBPresente(Constantes.SI);
				else {
					datos.setBPresente(Constantes.NO);
				}
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
}
