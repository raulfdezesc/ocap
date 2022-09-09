package es.jcyl.fqs.ocap.oad.factoresImpacto;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.factoresImpacto.OCAPFactoresImpactoOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPFactoresImpactoOAD {
	public Logger logger;
	private static OCAPFactoresImpactoOAD instance;

	private void $init$() {
		this.logger = OCAPConfigApp.logger;
	}

	public static OCAPFactoresImpactoOAD getInstance() {
		if (instance == null) {
			instance = new OCAPFactoresImpactoOAD();
		}
		return instance;
	}

	private OCAPFactoresImpactoOAD() {
		$init$();
	}

	public int altaOCAPFactoresImpacto(OCAPFactoresImpactoOT datos) throws Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_FACTORESIMPACTO (C_FACTOR_ID, B_BORRADO, D_NOMBRE, F_USUALTA, D_OBSERVACIONES, C_USUALTA) VALUES (OCAP_FAC_ID_SEQ.nextval, 'N', ?, SYSDATE, ?, ?)";

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

	public int bajaOCAPFactoresImpacto(int cFactorId) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_FACTORESIMPACTO SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_FACTOR_ID =  ?";

			st = con.prepareStatement(sql);
			st.setInt(1, cFactorId);

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

	public int modificacionOCAPFactoresImpacto(OCAPFactoresImpactoOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_FACTORESIMPACTO SET D_NOMBRE = ?, F_USUMODI = SYSDATE, D_OBSERVACIONES = ? WHERE C_FACTOR_ID = ?";

			st = con.prepareStatement(sql);

			st.setString(1, datos.getDNombre());

			if (datos.getDObservaciones() != null)
				st.setString(2, datos.getDObservaciones());
			else {
				st.setNull(2, 12);
			}
			st.setLong(3, datos.getCFactorId().longValue());

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

	public ArrayList listadoOCAPFactoresImpacto() throws Exception {
		return listadoOCAPFactoresImpacto(1, -1);
	}

	public ArrayList listadoOCAPFactoresImpacto(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT C_FACTOR_ID, D_NOMBRE, D_OBSERVACIONES, F_USUMODI, F_USUALTA, B_BORRADO FROM OCAP_FACTORESIMPACTO WHERE B_BORRADO = 'N' order by C_FACTOR_ID";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {

				OCAPFactoresImpactoOT datos = new OCAPFactoresImpactoOT();
				datos.setCFactorId(new Long(rs.getLong("C_FACTOR_ID")));
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

	public ArrayList buscarOCAPFactoresImpactoDeMeritocurricular(Integer cDefMeritoId, Integer cEstatutId,
			String dMeritoNombre, Integer cFactorId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		OCAPFactoresImpactoOT datos = null;
		try {
			String sql = "select C_FACTOR_ID, D_NOMBRE, D_OBSERVACIONES, B_BORRADO from ocap_factoresimpacto where c_factor_id in (select distinct(c_factor_id) from ocap_meritoscurriculares  where c_defmerito_id= ?   and c_estatut_id=?   and d_nombre=?  and (B_BORRADO = 'N' OR c_factor_id = ?) )  AND (B_BORRADO = 'N' OR c_factor_id = ?)  order by C_FACTOR_ID ";

			st = con.prepareStatement(sql);
			st.setInt(1, cDefMeritoId.intValue());
			st.setInt(2, cEstatutId.intValue());
			st.setString(3, dMeritoNombre);
			st.setInt(4, cFactorId != null ? cFactorId.intValue() : 0);
			st.setInt(5, cFactorId != null ? cFactorId.intValue() : 0);

			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				datos = new OCAPFactoresImpactoOT();
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES") == null ? "" : rs.getString("D_OBSERVACIONES"));
				datos.setCFactorId(new Long(rs.getLong("C_FACTOR_ID")));
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

	public OCAPFactoresImpactoOT buscarOCAPFactoresImpacto(long cFactorId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPFactoresImpactoOT datos = null;
		try {
			String sql = "SELECT C_FACTOR_ID, D_NOMBRE, D_OBSERVACIONES, F_USUMODI, F_USUALTA, B_BORRADO FROM OCAP_FACTORESIMPACTO WHERE C_FACTOR_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cFactorId);

			rs = st.executeQuery();

			datos = new OCAPFactoresImpactoOT();
			if (rs.next()) {
				datos.setCFactorId(new Long(rs.getLong("C_FACTOR_ID")));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
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

	public int listadoOCAPFactoresImpactoCuenta(long cFactorId, String dNombre, String dObservaciones, String fCreacion,
			String fModificacion) throws Exception {
		ResultSet rs = null;
		Statement st = null;
		String where = "";
		StringBuffer sbWhere = new StringBuffer(40);
		Connection con = JCYLGestionTransacciones.getConnection();
		String sql = "SELECT COUNT(*) FROM OCAP_FACTORESIMPACTO ";

		boolean isCFactorId = cFactorId != 0L;
		if (isCFactorId)
			sbWhere.append("C_FACTOR_ID = ").append(cFactorId).append(" AND ");
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

	public ArrayList consultaOCAPFactoresImpacto(long cFactorId, String dNombre, String dObservaciones,
			String fCreacion, String fModificacion, int inicio, int cuantos) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT C_FACTOR_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_OBSERVACIONES FROM OCAP_FACTORESIMPACTO";

			String orderBy = " order by C_FACTOR_ID";
			boolean isCFactorId = cFactorId != 0L;
			if (isCFactorId)
				sbWhere.append("C_FACTOR_ID = ?  AND ");
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
			OCAPFactoresImpactoOT datos = new OCAPFactoresImpactoOT();

			if (isCFactorId) {
				st.setLong(index++, cFactorId);
			}
			rs = st.executeQuery();
			if (inicio > 1)
				rs.absolute(inicio - 1);
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {

				datos = new OCAPFactoresImpactoOT();
				datos.setCFactorId(new Long(rs.getLong("C_FACTOR_ID")));
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
