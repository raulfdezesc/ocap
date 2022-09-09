package es.jcyl.fqs.ocap.oad.tipoGerencias;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.tipoGerencias.OCAPTipoGerenciasOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPTipoGerenciasOAD {
	public static Logger logger = OCAPConfigApp.logger;
	private static OCAPTipoGerenciasOAD instance;

	public static OCAPTipoGerenciasOAD getInstance() {
		if (instance == null) {
			instance = new OCAPTipoGerenciasOAD();
		}
		return instance;
	}

	public int altaOCAPTipoGerencias(OCAPTipoGerenciasOT datos) throws Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_TIPOGERENCIAS (C_TIPOGERENCIA_ID, B_BORRADO, D_NOMBRE, D_DESCRIPCION, F_USUALTA, C_USUALTA) VALUES (OCAP_TGE_ID_SEQ.nextval, 'N', ?, ?, SYSDATE, ?)";

			OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
			st = con.prepareStatement(sql);
			st.setString(1, datos.getDNombre());

			if (datos.getDDescripcion() != null)
				st.setString(2, datos.getDDescripcion());
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

	public int bajaOCAPTipoGerencias(int cTipoGerenciaId) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_TIPOGERENCIAS SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_TIPOGERENCIA_ID =  ?";

			st = con.prepareStatement(sql);
			st.setInt(1, cTipoGerenciaId);

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

	public int modificacionOCAPTipoGerencias(OCAPTipoGerenciasOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_TIPOGERENCIAS SET D_NOMBRE = ?, F_USUMODI = SYSDATE, D_DESCRIPCION = ? WHERE C_TIPOGERENCIA_ID = ?";

			st = con.prepareStatement(sql);

			st.setString(1, datos.getDNombre());

			if (datos.getDDescripcion() != null)
				st.setString(2, datos.getDDescripcion());
			else {
				st.setNull(2, 12);
			}
			st.setLong(3, datos.getCTipogerenciaId());

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

	public OCAPTipoGerenciasOT buscarOCAPTipoGerencias(long cTipoGerenciaId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPTipoGerenciasOT datos = null;
		try {
			String sql = "SELECT C_TIPOGERENCIA_ID, B_BORRADO,D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION FROM OCAP_TIPOGERENCIAS WHERE C_TIPOGERENCIA_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cTipoGerenciaId);

			rs = st.executeQuery();

			datos = new OCAPTipoGerenciasOT();
			if (rs.next()) {
				datos.setCTipogerenciaId(rs.getLong("C_TIPOGERENCIA_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
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

	public ArrayList listadoOCAPTipoGerencias() throws Exception {
		return listadoOCAPTipoGerencias(1, -1);
	}

	public ArrayList listadoOCAPTipoGerencias(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT C_TIPOGERENCIA_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION FROM OCAP_TIPOGERENCIAS WHERE B_BORRADO = 'N' order by C_TIPOGERENCIA_ID";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPTipoGerenciasOT datos = new OCAPTipoGerenciasOT();
				datos.setCTipogerenciaId(rs.getLong("C_TIPOGERENCIA_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));

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

	public int listadoOCAPTipoGerenciasCuenta(long cTipoGerenciaId, String dNombre, String dDescripcion,
			String fCreacion, String fModificacion) throws Exception {
		ResultSet rs = null;
		Statement st = null;
		String where = "";
		StringBuffer sbWhere = new StringBuffer(40);
		Connection con = JCYLGestionTransacciones.getConnection();
		String sql = "SELECT COUNT(*) FROM OCAP_TIPOGERENCIAS ";

		boolean isCTipoGerenciaId = cTipoGerenciaId != 0L;
		if (isCTipoGerenciaId)
			sbWhere.append("C_TIPOGERENCIA_ID = ").append(cTipoGerenciaId).append(" AND ");
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

	public ArrayList consultaOCAPTipoGerencias(long cTipoGerenciaId, String dNombre, String dDescripcion,
			String fCreacion, String fModificacion, int inicio, int cuantos) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT C_TIPOGERENCIA_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION FROM OCAP_TIPOGERENCIAS";

			String orderBy = " order by C_TIPOGERENCIA_ID";
			boolean isCTipoGerenciaId = cTipoGerenciaId != 0L;
			if (isCTipoGerenciaId)
				sbWhere.append("C_TIPOGERENCIA_ID = ?  AND ");
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
			OCAPTipoGerenciasOT datos = new OCAPTipoGerenciasOT();

			if (isCTipoGerenciaId) {
				st.setLong(index++, cTipoGerenciaId);
			}
			rs = st.executeQuery();
			if (inicio > 1)
				rs.absolute(inicio - 1);
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				datos = new OCAPTipoGerenciasOT();
				datos.setCTipogerenciaId(rs.getLong("C_TIPOGERENCIA_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));

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
