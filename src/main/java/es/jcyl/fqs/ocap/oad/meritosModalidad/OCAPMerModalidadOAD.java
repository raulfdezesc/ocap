package es.jcyl.fqs.ocap.oad.meritosModalidad;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.meritosModalidad.OCAPMerModalidadOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPMerModalidadOAD {
	public static Logger logger = OCAPConfigApp.logger;
	private static OCAPMerModalidadOAD instance;

	public static OCAPMerModalidadOAD getInstance() {
		if (instance == null) {
			instance = new OCAPMerModalidadOAD();
		}
		return instance;
	}

	public int altaOCAPMerModalidad(OCAPMerModalidadOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_MERMODALIDADES (B_BORRADO, D_DESCRIPCION, C_MODALIDAD_ID, D_NOMBRE, F_USUALTA, C_USUALTA) VALUES ('N', ?, OCAP_MOD_ID_SEQ.nextval, ?, SYSDATE, ?)";

			st = con.prepareStatement(sql);

			if (datos.getDDescripcion() != null)
				st.setString(1, datos.getDDescripcion());
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

	public int bajaOCAPMerModalidad(int cModalidadId) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_MERMODALIDADES SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_MODALIDAD_ID =  ?";

			st = con.prepareStatement(sql);
			st.setInt(1, cModalidadId);

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

	public int modificacionOCAPMerModalidad(OCAPMerModalidadOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_MERMODALIDADES SET D_DESCRIPCION = ?, F_USUMODI = SYSDATE, D_NOMBRE = ? WHERE C_MODALIDAD_ID = ?";

			st = con.prepareStatement(sql);

			if (datos.getDDescripcion() != null)
				st.setString(1, datos.getDDescripcion());
			else {
				st.setNull(1, 12);
			}
			st.setString(2, datos.getDNombre());
			st.setLong(3, datos.getCModalidadId().longValue());

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

	public OCAPMerModalidadOT buscarOCAPMerModalidad(long cModalidadId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPMerModalidadOT datos = null;
		try {
			String sql = "SELECT B_BORRADO, D_DESCRIPCION, C_MODALIDAD_ID, F_USUMODI, D_NOMBRE, F_USUALTA FROM OCAP_MERMODALIDADES WHERE C_MODALIDAD_ID = ? ";

			st = con.prepareStatement(sql);
			st.setLong(1, cModalidadId);

			rs = st.executeQuery();

			datos = new OCAPMerModalidadOT();
			if (rs.next()) {
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setCModalidadId(new Long(cModalidadId));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
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

	public ArrayList buscarOCAPMerModalidadDeMeritocurricular(Integer cDefMeritoId, Integer cEstatutId,
			String dMeritoNombre, Integer cModalidadId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		OCAPMerModalidadOT datos = null;
		try {
			String sql = "select C_MODALIDAD_ID, D_DESCRIPCION, D_NOMBRE, B_BORRADO from OCAP_MERMODALIDADES where C_MODALIDAD_ID in (select distinct(C_MODALIDAD_ID) from ocap_meritoscurriculares  where c_defmerito_id= ?   and c_estatut_id=?   and (b_borrado = 'N' OR c_modalidad_id = ?)   and d_nombre=? )  AND (B_BORRADO = 'N' OR c_modalidad_id = ?)  order by n_orden ";

			st = con.prepareStatement(sql);
			st.setInt(1, cDefMeritoId.intValue());
			st.setInt(2, cEstatutId.intValue());
			st.setInt(3, cModalidadId != null ? cModalidadId.intValue() : 0);
			st.setString(4, dMeritoNombre);
			st.setInt(5, cModalidadId != null ? cModalidadId.intValue() : 0);

			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				datos = new OCAPMerModalidadOT();
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION") == null ? "" : rs.getString("D_DESCRIPCION"));
				datos.setCModalidadId(new Long(rs.getLong("C_MODALIDAD_ID")));
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

	public ArrayList listadoOCAPMerModalidad() throws SQLException {
		return listadoOCAPMerModalidad(1, -1);
	}

	public ArrayList listadoOCAPMerModalidad(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT B_BORRADO, D_DESCRIPCION, C_MODALIDAD_ID, F_USUMODI, D_NOMBRE, F_USUALTA FROM OCAP_MERMODALIDADES WHERE B_BORRADO = 'N' order by C_MODALIDAD_ID";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPMerModalidadOT datos = new OCAPMerModalidadOT();
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setCModalidadId(new Long(rs.getLong("C_MODALIDAD_ID")));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
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

	public int listadoOCAPMerModalidadCuenta(long cModalidadId, String dNombre, String dDescripcion, String fCreacion,
			String fModificacion) throws Exception {
		ResultSet rs = null;
		PreparedStatement st = null;

		String where = "";
		StringBuffer sbWhere = new StringBuffer(40);
		Connection con = JCYLGestionTransacciones.getConnection();
		String sql = "SELECT COUNT(*) FROM OCAP_MERMODALIDADES";

		boolean isCModalidadId = cModalidadId != 0L;
		if (isCModalidadId)
			sbWhere.append("C_MODALIDAD_ID = ").append(cModalidadId).append(" AND ");
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

	public ArrayList consultaOCAPMerModalidad(long cModalidadId, String dNombre, String dDescripcion, String fCreacion,
			String fModificacion, int inicio, int cuantos) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT C_MODALIDAD_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION FROM OCAP_MERMODALIDADES";

			String orderBy = " order by C_MODALIDAD_ID";
			boolean isCModalidadId = cModalidadId != 0L;
			if (isCModalidadId)
				sbWhere.append("C_MODALIDAD_ID = ?  AND ");
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
			String sqlStatement = selectFrom + where + orderBy;
			OCAPConfigApp.logger.info("Sentencia SQL:" + sqlStatement);
			st = con.prepareStatement(sqlStatement, 1004, 1008);

			int index = 1;
			OCAPMerModalidadOT datos = new OCAPMerModalidadOT();

			if (isCModalidadId) {
				st.setLong(index++, cModalidadId);
			}
			rs = st.executeQuery();
			if (inicio > 1)
				rs.absolute(inicio - 1);
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				datos = new OCAPMerModalidadOT();
				datos.setCModalidadId(new Long(rs.getLong("C_MODALIDAD_ID")));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
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
