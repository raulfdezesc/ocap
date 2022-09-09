package es.jcyl.fqs.ocap.oad.mensajes;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.mensajes.OCAPMensajesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPMensajesOAD {
	public static Logger logger = OCAPConfigApp.logger;
	public Logger loggerBD;
	private static OCAPMensajesOAD instance;

	private void $init$() {
		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public static OCAPMensajesOAD getInstance() {
		if (instance == null) {
			instance = new OCAPMensajesOAD();
		}
		return instance;
	}

	private OCAPMensajesOAD() {
		$init$();
	}

	public int altaOCAPMensajes(OCAPMensajesOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_MENSAJES (C_ESTATUT_ID, C_MENSAJE_ID, B_BORRADO, F_USUALTA, C_GRADO_ID, D_DESCRIPCION, C_USUALTA) VALUES (?, OCAP_MEN_ID_SEQ.nextval, 'N', SYSDATE, ?, ?, ?)";

			st = con.prepareStatement(sql);
			st.setLong(1, datos.getCEstatutId());
			st.setLong(2, datos.getCGradoId());
			st.setString(3, datos.getACreadoPor());

			StringReader reader1 = new StringReader(datos.getDDescripcion());
			st.setCharacterStream(3, reader1, datos.getDDescripcion().length());

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

	public int bajaOCAPMensajes(int cMensajeId) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_MENSAJES SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_MENSAJE_ID =  ?";

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

	public int modificacionOCAPMensajes(OCAPMensajesOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_MENSAJES SET C_ESTATUT_ID = ?, F_USUMODI = SYSDATE, C_GRADO_ID = ?, D_DESCRIPCION = ? WHERE C_MENSAJE_ID = ?";

			st = con.prepareStatement(sql);

			st.setLong(1, datos.getCEstatutId());
			st.setLong(2, datos.getCGradoId());
			StringReader reader1 = new StringReader(datos.getDDescripcion());
			st.setCharacterStream(3, reader1, datos.getDDescripcion().length());
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

	public OCAPMensajesOT buscarOCAPMensajes(long cMensajeId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPMensajesOT datos = null;
		try {
			String sql = "SELECT C_ESTATUT_ID, C_MENSAJE_ID, B_BORRADO, F_USUMODI, F_USUALTA, C_GRADO_ID, DBMS_LOB.SUBSTR(D_DESCRIPCION) as D_DESCRIPCION FROM OCAP_MENSAJES WHERE C_MENSAJE_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cMensajeId);

			rs = st.executeQuery();

			datos = new OCAPMensajesOT();
			if (rs.next()) {
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setCMensajeId(cMensajeId);
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
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

	public OCAPMensajesOT buscarOCAPMensajesPorGradoYEstatut(Integer cGradoId, Integer cEstatutId)
			throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPMensajesOT datos = null;
		try {
			this.loggerBD.info("buscarOCAPMensajesPorGradoYEstatut: " + cGradoId + ", " + cEstatutId);

			String sql = "SELECT C_ESTATUT_ID, C_MENSAJE_ID, B_BORRADO, F_USUMODI, F_USUALTA, C_GRADO_ID, DBMS_LOB.SUBSTR(D_DESCRIPCION) as D_DESCRIPCION FROM OCAP_MENSAJES WHERE C_GRADO_ID = ? AND C_ESTATUT_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setInt(1, cGradoId.intValue());
			st.setInt(2, cEstatutId.intValue());

			rs = st.executeQuery();

			datos = new OCAPMensajesOT();
			if (rs.next()) {
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setCMensajeId(rs.getLong("C_MENSAJE_ID"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
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

	public OCAPMensajesOT buscarOCAPMensajesPorGradoIdEstatutIdDefMeritoId(Integer cGradoId, Integer cEstatutId,
			long cDefMeritoId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPMensajesOT datos = null;
		try {
			String sql = "SELECT C_ESTATUT_ID, C_MENSAJE_ID, B_BORRADO, F_USUMODI, F_USUALTA, C_GRADO_ID, C_DEFMERITO_ID, DBMS_LOB.SUBSTR(D_DESCRIPCION) as D_DESCRIPCION FROM OCAP_MENSAJES WHERE C_GRADO_ID = ? AND C_ESTATUT_ID = ? AND C_DEFMERITO_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setInt(1, cGradoId.intValue());
			st.setInt(2, cEstatutId.intValue());
			st.setLong(3, cDefMeritoId);

			rs = st.executeQuery();

			datos = new OCAPMensajesOT();
			if (rs.next()) {
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setCMensajeId(rs.getLong("C_MENSAJE_ID"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
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

	public ArrayList listadoOCAPMensajes() throws SQLException {
		return listadoOCAPMensajes(1, -1);
	}

	public ArrayList listadoOCAPMensajes(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT D_DESCRIPCION, C_ESTATUT_ID, C_MENSAJE_ID, B_BORRADO, F_USUMODI, F_USUALTA, C_GRADO_ID FROM OCAP_MENSAJES WHERE B_BORRADO = 'N' order by C_MENSAJE_ID";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPMensajesOT datos = new OCAPMensajesOT();
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setCMensajeId(rs.getLong("C_MENSAJE_ID"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));

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

	public int listadoOCAPMensajesCuenta(long cMensajeId, long cEstatutId, long cGradoId, String dDescripcion,
			String fCreacion, String fModificacion) throws Exception {
		ResultSet rs = null;
		PreparedStatement st = null;

		String where = "";
		StringBuffer sbWhere = new StringBuffer(40);
		Connection con = JCYLGestionTransacciones.getConnection();
		String sql = "SELECT COUNT(*) FROM OCAP_MENSAJES";

		boolean iscMensajeId = cMensajeId != 0L;
		if (iscMensajeId)
			sbWhere.append("C_MENSAJE_ID = ").append(cMensajeId).append(" AND ");
		boolean isCEstatutId = cEstatutId != 0L;
		if (isCEstatutId)
			sbWhere.append("C_ESTATUT_ID = ").append(cEstatutId).append(" AND ");
		boolean isCGradoId = cGradoId != 0L;
		if (isCGradoId)
			sbWhere.append("C_GRADO_ID = ").append(cGradoId).append(" AND ");
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

	public ArrayList consultaOCAPMensajes(long cMensajeId, long cEstatutId, long cGradoId, String dDescripcion,
			String fCreacion, String fModificacion, int inicio, int cuantos) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT C_MENSAJE_ID, C_ESTATUT_ID, C_GRADO_ID, DBMS_LOB.SUBSTR(D_DESCRIPCION) as D_DESCRIPCION, B_BORRADO, F_USUMODI, F_USUALTA FROM OCAP_MENSAJES";

			String orderBy = " order by C_MENSAJE_ID";
			boolean iscMensajeId = cMensajeId != 0L;
			if (iscMensajeId)
				sbWhere.append("C_MENSAJE_ID = ?  AND ");
			boolean isCEstatutId = cEstatutId != 0L;
			if (isCEstatutId)
				sbWhere.append("C_ESTATUT_ID = ?  AND ");
			boolean isCGradoId = cGradoId != 0L;
			if (isCGradoId)
				sbWhere.append("C_GRADO_ID = ?  AND ");
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
			OCAPMensajesOT datos = new OCAPMensajesOT();

			if (iscMensajeId)
				st.setLong(index++, cMensajeId);
			if (isCEstatutId)
				st.setLong(index++, cEstatutId);
			if (isCGradoId) {
				st.setLong(index++, cGradoId);
			}
			rs = st.executeQuery();
			if (inicio > 1)
				rs.absolute(inicio - 1);
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				datos = new OCAPMensajesOT();
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setCMensajeId(rs.getLong("C_MENSAJE_ID"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));

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
