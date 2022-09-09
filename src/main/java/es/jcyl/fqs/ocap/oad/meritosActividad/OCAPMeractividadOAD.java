package es.jcyl.fqs.ocap.oad.meritosActividad;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.meritosActividad.OCAPMeractividadOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPMeractividadOAD {
	public static Logger logger = OCAPConfigApp.logger;
	private static OCAPMeractividadOAD instance;

	public static OCAPMeractividadOAD getInstance() {
		if (instance == null) {
			instance = new OCAPMeractividadOAD();
		}
		return instance;
	}

	public int altaOCAPMeractividad(OCAPMeractividadOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_MERACTIVIDADES (B_BORRADO, D_OBSERVACIONES, C_ACTIVIDAD_ID, D_NOMBRE, F_USUALTA, C_USUALTA) VALUES ('N', ?, OCAP_ACT_ID_SEQ.nextval, ?, SYSDATE)";

			st = con.prepareStatement(sql);

			if (datos.getDObservaciones() != null)
				st.setString(1, datos.getDObservaciones());
			else {
				st.setNull(1, 12);
			}
			st.setString(2, datos.getDNombre());
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

	public int bajaOCAPMeractividad(int cActividadId) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_MERACTIVIDADES SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_Actividad_ID =  ?";

			st = con.prepareStatement(sql);
			st.setInt(1, cActividadId);

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

	public int modificacionOCAPMeractividad(OCAPMeractividadOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_MERACTIVIDADES SET D_OBSERVACIONES = ?, F_USUMODI = SYSDATE, D_NOMBRE = ? WHERE C_ACTIVIDAD_ID = ?";

			st = con.prepareStatement(sql);

			if (datos.getDObservaciones() != null)
				st.setString(1, datos.getDObservaciones());
			else {
				st.setNull(1, 12);
			}
			st.setString(2, datos.getDNombre());
			st.setLong(3, datos.getCActividadId().longValue());

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

	public OCAPMeractividadOT buscarOCAPMeractividad(long cActividadId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPMeractividadOT datos = null;
		try {
			String sql = "SELECT D_NOMBRE, B_BORRADO, F_USUMODI, D_OBSERVACIONES, C_ACTIVIDAD_ID, F_USUALTA FROM OCAP_MERACTIVIDADES WHERE C_ACTIVIDAD_ID = ? ";

			st = con.prepareStatement(sql);
			st.setLong(1, cActividadId);

			rs = st.executeQuery();

			datos = new OCAPMeractividadOT();
			if (rs.next()) {
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
				datos.setCActividadId(new Long(cActividadId));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
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

	public ArrayList listadoOCAPMeractividad() throws SQLException {
		return listadoOCAPMeractividad(1, -1);
	}

	public ArrayList listadoOCAPMeractividad(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT D_NOMBRE, B_BORRADO, F_USUMODI, D_OBSERVACIONES, C_ACTIVIDAD_ID, F_USUALTA FROM OCAP_MERACTIVIDADES WHERE B_BORRADO = 'N' order by C_ACTIVIDAD_ID";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPMeractividadOT datos = new OCAPMeractividadOT();
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
				datos.setCActividadId(new Long(rs.getLong("C_ACTIVIDAD_ID")));
				datos.setFCreacion(rs.getDate("F_USUALTA"));

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

	public ArrayList buscarOCAPMeractividadDeMeritocurricular(Integer cDefMeritoId, Integer cEstatutId,
			String dMeritoNombre, Integer cActividadId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		OCAPMeractividadOT datos = null;
		try {
			String sql = "select C_ACTIVIDAD_ID, D_NOMBRE, D_OBSERVACIONES, N_ORDEN, b_borrado from ocap_meractividades where c_actividad_id in (select distinct(c_actividad_id) from ocap_meritoscurriculares  where c_defmerito_id= ?   and c_estatut_id=?   and (b_borrado = 'N' OR c_actividad_id = ?)   and d_nombre=? )  AND (B_BORRADO = 'N' OR c_actividad_id = ? )  order by n_orden asc ";

			st = con.prepareStatement(sql);
			st.setInt(1, cDefMeritoId.intValue());
			st.setInt(2, cEstatutId.intValue());
			st.setInt(3, cActividadId != null ? cActividadId.intValue() : 0);
			st.setString(4, dMeritoNombre);
			st.setInt(5, cActividadId != null ? cActividadId.intValue() : 0);

			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				datos = new OCAPMeractividadOT();
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES") == null ? "" : rs.getString("D_OBSERVACIONES"));
				datos.setCActividadId(new Long(rs.getLong("C_ACTIVIDAD_ID")));
				datos.setBBorrado(rs.getString("B_BORRADO"));
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

	public int listadoOCAPMeractividadCuenta(long cActividadId, String dNombre, String dObservaciones, String fCreacion,
			String fModificacion) throws Exception {
		ResultSet rs = null;
		PreparedStatement st = null;

		String where = "";
		StringBuffer sbWhere = new StringBuffer(40);
		Connection con = JCYLGestionTransacciones.getConnection();
		String sql = "SELECT COUNT(*) FROM OCAP_MERACTIVIDADES";

		boolean isCActividadId = cActividadId != 0L;
		if (isCActividadId)
			sbWhere.append("C_ACTIVIDAD_ID = ").append(cActividadId).append(" AND ");
		boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
		if (isDNombre)
			sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
		boolean isDObservaciones = (dObservaciones != null) && (!dObservaciones.equals(""));
		if (isDObservaciones)
			sbWhere.append("(upper(D_OBSERVACIONES) like upper('%").append(dObservaciones).append("%')) AND ");
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

	public ArrayList consultaOCAPMeractividad(long cActividadId, String dNombre, String dObservaciones,
			String fCreacion, String fModificacion, int inicio, int cuantos) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT C_ACTIVIDAD_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_OBSERVACIONES FROM OCAP_MERACTIVIDADES";

			String orderBy = " order by C_Actividad_ID";
			boolean isCActividadId = cActividadId != 0L;
			if (isCActividadId)
				sbWhere.append("C_ACTIVIDAD_ID = ?  AND ");
			boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
			if (isDNombre)
				sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
			boolean isDObservaciones = (dObservaciones != null) && (!dObservaciones.equals(""));
			if (isDObservaciones)
				sbWhere.append("(upper(D_OBSERVACIONES) like upper('%").append(dObservaciones).append("%')) AND ");
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
			OCAPMeractividadOT datos = new OCAPMeractividadOT();

			if (isCActividadId) {
				st.setLong(index++, cActividadId);
			}
			rs = st.executeQuery();
			if (inicio > 1)
				rs.absolute(inicio - 1);
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				datos = new OCAPMeractividadOT();
				datos.setCActividadId(new Long(rs.getLong("C_ACTIVIDAD_ID")));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
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
}
