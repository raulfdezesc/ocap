package es.jcyl.fqs.ocap.oad.tiposFirmante;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.tiposFirmante.OCAPTiposFirmanteOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPTiposFirmanteOAD {
	public Logger logger;
	private static OCAPTiposFirmanteOAD instance;

	private void $init$() {
		this.logger = OCAPConfigApp.logger;
	}

	public static OCAPTiposFirmanteOAD getInstance() {
		if (instance == null) {
			instance = new OCAPTiposFirmanteOAD();
		}
		return instance;
	}

	private OCAPTiposFirmanteOAD() {
		$init$();
	}

	public ArrayList listadoOCAPTiposFirmante() throws Exception {
		return listadoOCAPTiposFirmante(1, -1);
	}

	public ArrayList listadoOCAPTiposFirmante(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT C_TIPO_ID, D_NOMBRE, D_OBSERVACIONES, F_USUMODI, F_USUALTA, B_BORRADO FROM OCAP_TIPOSFIRMANTE WHERE B_BORRADO = 'N' order by C_TIPO_ID";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPTiposFirmanteOT datos = new OCAPTiposFirmanteOT();
				datos.setCTipoId(new Long(rs.getLong("C_TIPO_ID")));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
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

	public OCAPTiposFirmanteOT buscarOCAPTiposFirmante(long cTipoId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPTiposFirmanteOT datos = null;
		try {
			String sql = "SELECT C_TIPO_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_OBSERVACIONES FROM OCAP_TIPOSFIRMANTE WHERE C_TIPO_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cTipoId);

			rs = st.executeQuery();

			datos = new OCAPTiposFirmanteOT();
			if (rs.next()) {
				datos.setCTipoId(new Long(rs.getLong("C_TIPO_ID")));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
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

	public int altaOCAPTiposFirmante(OCAPTiposFirmanteOT datos) throws Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_TIPOSFIRMANTE (C_TIPO_ID, B_BORRADO, D_NOMBRE, F_USUALTA, D_OBSERVACIONES, C_USUALTA) VALUES (OCAP_TIP_ID_SEQ.nextval, 'N', ?, SYSDATE, ?, ?)";

			OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
			st = con.prepareStatement(sql);
			st.setString(1, datos.getDNombre());

			if (datos.getDObservaciones() != null)
				st.setString(2, datos.getDObservaciones());
			else
				st.setNull(2, 12);
			st.setString(3, datos.getACreadoPor());
			filas = st.executeUpdate();
		} catch (SQLException ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			throw ex;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
	}

	public int bajaOCAPTiposFirmante(int cTipoId) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_TIPOSFIRMANTE SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_TIPO_ID =  ?";

			st = con.prepareStatement(sql);
			st.setInt(1, cTipoId);

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

	public int modificacionOCAPTiposFirmante(OCAPTiposFirmanteOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_TIPOSFIRMANTE SET D_NOMBRE = ?, F_USUMODI = SYSDATE, D_OBSERVACIONES = ? WHERE C_TIPO_ID = ?";

			st = con.prepareStatement(sql);

			st.setString(1, datos.getDNombre());

			if (datos.getDObservaciones() != null)
				st.setString(2, datos.getDObservaciones());
			else {
				st.setNull(2, 12);
			}
			st.setLong(3, datos.getCTipoId().longValue());

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

	public int listadoOCAPTiposFirmanteCuenta(long cTipoId, String dNombre, String dObservaciones, String fCreacion,
			String fModificacion) throws Exception {
		ResultSet rs = null;
		Statement st = null;
		String where = "";
		StringBuffer sbWhere = new StringBuffer(40);
		Connection con = JCYLGestionTransacciones.getConnection();
		String sql = "SELECT COUNT(*) FROM OCAP_TIPOSFIRMANTE ";

		boolean isCTipoId = cTipoId != 0L;
		if (isCTipoId)
			sbWhere.append("C_TIPO_ID = ").append(cTipoId).append(" AND ");
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

	public ArrayList consultaOCAPTiposFirmante(long cTipoId, String dNombre, String dObservaciones, String fCreacion,
			String fModificacion, int inicio, int cuantos) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT C_TIPO_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_OBSERVACIONES FROM OCAP_TIPOSFIRMANTE";

			String orderBy = " order by C_TIPO_ID";
			boolean isCTipoId = cTipoId != 0L;
			if (isCTipoId)
				sbWhere.append("C_TIPO_ID = ?  AND ");
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
			OCAPTiposFirmanteOT datos = new OCAPTiposFirmanteOT();

			if (isCTipoId) {
				st.setLong(index++, cTipoId);
			}
			rs = st.executeQuery();
			if (inicio > 1)
				rs.absolute(inicio - 1);
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				datos = new OCAPTiposFirmanteOT();
				datos.setCTipoId(new Long(rs.getLong("C_TIPO_ID")));
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

	public ArrayList buscarOCAPTiposFirmanteDeMeritocurricular(Integer cDefMeritoId, Integer cEstatutId,
			String dMeritoNombre, Integer cTipoId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		OCAPTiposFirmanteOT datos = null;
		try {
			String sql = "select C_TIPO_ID, D_NOMBRE, D_OBSERVACIONES, B_BORRADO from ocap_tiposfirmante where c_tipo_id in (select distinct(c_tipo_id) from ocap_meritoscurriculares  where c_defmerito_id= ?   and c_estatut_id=?   and d_nombre=?  and (B_BORRADO = 'N' OR c_tipo_id = ?) ) AND (B_BORRADO = 'N' OR c_tipo_id = ?)  order by C_TIPO_ID ";

			st = con.prepareStatement(sql);
			st.setInt(1, cDefMeritoId.intValue());
			st.setInt(2, cEstatutId.intValue());
			st.setString(3, dMeritoNombre);
			st.setInt(4, cTipoId != null ? cTipoId.intValue() : 0);
			st.setInt(5, cTipoId != null ? cTipoId.intValue() : 0);

			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				datos = new OCAPTiposFirmanteOT();
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES") == null ? "" : rs.getString("D_OBSERVACIONES"));
				datos.setCTipoId(new Long(rs.getLong("C_TIPO_ID")));
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
}
