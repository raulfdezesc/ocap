package es.jcyl.fqs.ocap.oad.mensajesMc;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.mensajesMc.OCAPMensajesMcOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPMensajesMcOAD {
	public Logger logger;
	public Logger loggerBD;
	private static OCAPMensajesMcOAD instance;

	private void $init$() {
		this.logger = OCAPConfigApp.logger;

		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public static OCAPMensajesMcOAD getInstance() {
		if (instance == null) {
			instance = new OCAPMensajesMcOAD();
		}
		return instance;
	}

	private OCAPMensajesMcOAD() {
		$init$();
	}

	public int altaOCAPMensajesMc(OCAPMensajesMcOT datos) throws Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_MENSAJES_MC(C_MENSAJE_ID, C_ESTATUT_ID, C_DEFMERITO_ID, B_BORRADO, D_DESCRIPCION, F_USUALTA, C_USUALTA) VALUES (OCAP_MMC_ID_SEQ.nextval, ?, ?, 'N', ?, SYSDATE, ?)";

			OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
			st = con.prepareStatement(sql);
			st.setLong(1, datos.getCEstatutId());
			st.setLong(2, datos.getCDefmeritoId());
			st.setString(3, datos.getACreadoPor());

			StringReader reader1 = new StringReader(datos.getDDescripcion());
			st.setCharacterStream(3, reader1, datos.getDDescripcion().length());

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

	public int bajaOCAPMensajesMc(int cMensajeId) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_MENSAJES_MC SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_MENSAJE_ID =  ?";

			st = con.prepareStatement(sql);
			st.setInt(1, cMensajeId);

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

	public int modificacionOCAPMensajesMc(OCAPMensajesMcOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_MENSAJES_MC SET D_DESCRIPCION = ?, F_USUMODI = SYSDATE, C_ESTATUT_ID = ?, C_DEFMERITO_ID = ? WHERE C_MENSAJE_ID = ?";

			st = con.prepareStatement(sql);

			StringReader reader1 = new StringReader(datos.getDDescripcion());
			st.setCharacterStream(1, reader1, datos.getDDescripcion().length());

			st.setLong(2, datos.getCEstatutId());
			st.setLong(3, datos.getCDefmeritoId());
			st.setLong(4, datos.getCMensajeId());

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

	public OCAPMensajesMcOT buscarOCAPMensajesMc(long cMensajeId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPMensajesMcOT datos = null;
		try {
			String sql = "SELECT C_MENSAJE_ID, B_BORRADO, DBMS_LOB.SUBSTR(D_DESCRIPCION) as D_DESCRIPCION, F_USUMODI, F_USUALTA, C_ESTATUT_ID, C_DEFMERITO_ID FROM OCAP_MENSAJES_MC WHERE C_MENSAJE_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cMensajeId);

			rs = st.executeQuery();

			datos = new OCAPMensajesMcOT();
			if (rs.next()) {
				datos.setCMensajeId(rs.getLong("C_MENSAJE_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setCDefmeritoId(rs.getLong("C_DEFMERITO_ID"));
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

	public OCAPMensajesMcOT buscarOCAPMensajesPorEstatutIdDefMeritoId(long cEstatutId, long cDefMeritoId)
			throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPMensajesMcOT datos = null;
		try {
			this.loggerBD.info(" buscarOCAPMensajesPorEstatutIdDefMeritoId: " + cEstatutId + "," + cDefMeritoId);

			String sql = "SELECT C_MENSAJE_ID, B_BORRADO, DBMS_LOB.SUBSTR(D_DESCRIPCION) as D_DESCRIPCION, F_USUMODI, F_USUALTA, C_ESTATUT_ID, C_DEFMERITO_ID  FROM OCAP_MENSAJES_MC  WHERE C_ESTATUT_ID = ?  AND  C_DEFMERITO_ID = ?  AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cEstatutId);
			st.setLong(2, cDefMeritoId);

			rs = st.executeQuery();

			datos = new OCAPMensajesMcOT();
			if (rs.next()) {
				datos.setCMensajeId(rs.getLong("C_MENSAJE_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setCDefmeritoId(rs.getLong("C_DEFMERITO_ID"));
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

	public ArrayList listadoOCAPMensajesMc() throws Exception {
		return listadoOCAPMensajesMc(1, -1);
	}

	public ArrayList listadoOCAPMensajesMc(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT C_MENSAJE_ID, B_BORRADO, D_DESCRIPCION, F_USUMODI, F_USUALTA, C_ESTATUT_ID, C_DEFMERITO_ID FROM OCAP_MENSAJES_MC WHERE B_BORRADO = 'N' order by C_MENSAJE_ID ";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPMensajesMcOT datos = new OCAPMensajesMcOT();
				datos.setCMensajeId(rs.getLong("C_MENSAJE_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setCDefmeritoId(rs.getLong("C_DEFMERITO_ID"));
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

	public int listadoOCAPMensajesMcCuenta(long cMensajeId, long cDefmeritoId, long cEstatutId, String dMensajeDesc,
			String fCreacion, String fModificacion) throws Exception {
		ResultSet rs = null;
		Statement st = null;

		String where = "";
		StringBuffer sbWhere = new StringBuffer(40);
		Connection con = JCYLGestionTransacciones.getConnection();
		String sql = "SELECT COUNT(*) FROM OCAP_MENSAJES_MC ";

		boolean iscMensajeId = cMensajeId != 0L;
		if (iscMensajeId)
			sbWhere.append("C_MENSAJE_ID = ").append(cMensajeId).append(" AND ");
		boolean isCDefmeritoId = cDefmeritoId != 0L;
		if (isCDefmeritoId)
			sbWhere.append("C_DEFMERITO_ID = ").append(cDefmeritoId).append(" AND ");
		boolean isCEstatutId = cEstatutId != 0L;
		if (isCEstatutId)
			sbWhere.append("C_ESTATUT_ID = ").append(cEstatutId).append(" AND ");
		boolean isdMensajeDesc = (dMensajeDesc != null) && (!dMensajeDesc.equals(""));
		if (isdMensajeDesc)
			sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dMensajeDesc).append("%')) AND ");
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

	public ArrayList consultaOCAPMensajesMc(long cMensajeId, long cDefmeritoId, long cEstatutId, String dMensajeDesc,
			String fCreacion, String fModificacion, int inicio, int cuantos) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT C_MENSAJE_ID, B_BORRADO, DBMS_LOB.SUBSTR(D_DESCRIPCION) as D_DESCRIPCION, F_USUMODI, F_USUALTA, C_ESTATUT_ID, C_DEFMERITO_ID FROM OCAP_MENSAJES_MC";

			String orderBy = " order by C_MENSAJE_ID";
			boolean iscMensajeId = cMensajeId != 0L;
			if (iscMensajeId)
				sbWhere.append("C_MENSAJE_ID = ?  AND ");
			boolean iscDefmeritoId = cDefmeritoId != 0L;
			if (iscDefmeritoId)
				sbWhere.append("C_DEFMERITO_ID = ?  AND ");
			boolean iscEstatutId = cEstatutId != 0L;
			if (iscEstatutId)
				sbWhere.append("C_ESTATUT_ID = ?  AND ");
			boolean isdMensajeDesc = (dMensajeDesc != null) && (!dMensajeDesc.equals(""));
			if (isdMensajeDesc)
				sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dMensajeDesc).append("%')) AND ");
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

			if (iscMensajeId) {
				st.setLong(index++, cMensajeId);
			}
			if (iscDefmeritoId) {
				st.setLong(index++, cDefmeritoId);
			}
			if (iscEstatutId) {
				st.setLong(index++, cEstatutId);
			}
			rs = st.executeQuery();
			if (inicio > 1)
				rs.absolute(inicio - 1);
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPMensajesMcOT datos = new OCAPMensajesMcOT();
				datos.setCMensajeId(rs.getLong("C_MENSAJE_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setCDefmeritoId(rs.getLong("C_DEFMERITO_ID"));
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
