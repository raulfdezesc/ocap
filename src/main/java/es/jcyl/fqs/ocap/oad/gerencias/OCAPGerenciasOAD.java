package es.jcyl.fqs.ocap.oad.gerencias;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.gerencias.OCAPGerenciasOT;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class OCAPGerenciasOAD {
	public Logger loggerBD;
	private static OCAPGerenciasOAD instance;

	public static OCAPGerenciasOAD getInstance() {
		if (instance == null) {
			instance = new OCAPGerenciasOAD();
		}
		return instance;
	}

	private OCAPGerenciasOAD() {
		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public int altaOCAPGerencias(OCAPGerenciasOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_GERENCIAS (C_GERENCIA_ID, B_BORRADO, D_NOMBRE, C_TIPOGERENCIA_ID, F_USUALTA, C_PROVINCIA_ID, D_NOMBRE_CORTO, A_CODLDAP, D_GERENTE, D_DIRECTOR,C_USUALTA) VALUES (OCAP_GER_ID_SEQ.nextval, 'N', ?, ?, SYSDATE, ?, ?, ?, ?, ?, ?)";

			this.loggerBD.info("Sentencia SQL:" + sql);
			st = con.prepareStatement(sql);

			st.setString(1, datos.getDNombre());
			st.setLong(2, datos.getCTipogerenciaId());
			st.setString(3, datos.getCProvinciaId());
			st.setString(4, datos.getDNombreCorto());
			st.setString(5, datos.getACodldap());
			st.setString(6, datos.getDGerente());
			st.setString(7, datos.getDDirector());
			st.setString(8, datos.getACreadoPor());

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

	public int bajaOCAPGerencias(int cGerenciaId) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_GERENCIAS SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_GERENCIA_ID =  ?";

			st = con.prepareStatement(sql);
			st.setInt(1, cGerenciaId);

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

	public int modificacionOCAPGerencias(OCAPGerenciasOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_GERENCIAS SET D_NOMBRE = ?, C_TIPOGERENCIA_ID = ?, F_USUMODI = SYSDATE, C_PROVINCIA_ID = ?, D_NOMBRE_CORTO = ?, A_CODLDAP = ?, D_GERENTE = ?, D_DIRECTOR = ? WHERE C_GERENCIA_ID = ?";

			st = con.prepareStatement(sql);

			st.setString(1, datos.getDNombre());
			st.setLong(2, datos.getCTipogerenciaId());
			st.setString(3, datos.getCProvinciaId());
			st.setString(4, datos.getDNombreCorto());
			st.setString(5, datos.getACodldap());
			st.setString(6, datos.getDGerente());
			st.setString(7, datos.getDDirector());
			st.setLong(8, datos.getCGerenciaId());

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

	public OCAPGerenciasOT buscarOCAPGerencias(long cGerenciaId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPGerenciasOT datos = null;
		try {
			this.loggerBD.info("buscarOCAPGerencias: " + cGerenciaId);

			String sql = "SELECT C_GERENCIA_ID, B_BORRADO, D_NOMBRE, C_TIPOGERENCIA_ID, F_USUMODI, F_USUALTA, C_PROVINCIA_ID, D_NOMBRE_CORTO, A_CODLDAP, D_GERENTE, D_DIRECTOR FROM OCAP_GERENCIAS WHERE C_GERENCIA_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cGerenciaId);

			rs = st.executeQuery();

			datos = new OCAPGerenciasOT();
			if (rs.next()) {
				datos.setCGerenciaId(rs.getLong("C_GERENCIA_ID"));
				datos.setCProvinciaId(rs.getString("C_PROVINCIA_ID"));
				datos.setCTipogerenciaId(rs.getLong("C_TIPOGERENCIA_ID"));
				datos.setDNombreCorto(rs.getString("D_NOMBRE_CORTO"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setACodldap(rs.getString("A_CODLDAP"));
				datos.setDGerente(rs.getString("D_GERENTE"));
				datos.setDDirector(rs.getString("D_DIRECTOR"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
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

	public ArrayList listadoOCAPGerencias() throws SQLException, Exception {
		return listadoOCAPGerencias(1, -1);
	}

	public ArrayList listadoOCAPGerencias(int inicio, int cuantos) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT C_GERENCIA_ID, B_BORRADO, D_NOMBRE, C_TIPOGERENCIA_ID, F_USUMODI, F_USUALTA, C_PROVINCIA_ID, D_NOMBRE_CORTO, A_CODLDAP, D_GERENTE, D_DIRECTOR FROM OCAP_GERENCIAS WHERE B_BORRADO = 'N' order by C_GERENCIA_ID";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPGerenciasOT datos = new OCAPGerenciasOT();
				datos.setCGerenciaId(rs.getLong("C_GERENCIA_ID"));
				datos.setCProvinciaId(rs.getString("C_PROVINCIA_ID"));
				datos.setCTipogerenciaId(rs.getLong("C_TIPOGERENCIA_ID"));
				datos.setDNombreCorto(rs.getString("D_NOMBRE_CORTO"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setACodldap(rs.getString("A_CODLDAP"));
				datos.setDGerente(rs.getString("D_GERENTE"));
				datos.setDDirector(rs.getString("D_DIRECTOR"));
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

	public ArrayList listadoOCAPGerencias(String idProv, String idTipoG, String bBorrado)
			throws SQLException, Exception {
		return listadoOCAPGerencias(1, -1, idProv, idTipoG, bBorrado);
	}

	public ArrayList listadoOCAPGerenciasSolicitud(String idProv, String bBorrado) throws SQLException, Exception {
		return listadoOCAPGerenciasSolicitud(1, -1, idProv, bBorrado);
	}

	public ArrayList listadoOCAPGerencias(int inicio, int cuantos, String idProv, String idTipoG, String bBorrado)
			throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT C_GERENCIA_ID, B_BORRADO, D_NOMBRE, C_TIPOGERENCIA_ID, F_USUMODI, F_USUALTA, C_PROVINCIA_ID FROM OCAP_GERENCIAS WHERE 0=0 ";

			if ((idProv != null) && (!idProv.equals("")))
				sql = sql + " AND C_PROVINCIA_ID = " + idProv;
			if ((idTipoG != null) && (!idTipoG.equals("")))
				sql = sql + " AND C_TIPOGERENCIA_ID = " + idTipoG;
			if ((bBorrado != null) && (!bBorrado.equals(""))) {
				sql = sql + " AND B_BORRADO = '" + bBorrado + "'";
			}
			sql = sql + " order by C_GERENCIA_ID";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPGerenciasOT datos = new OCAPGerenciasOT();
				datos.setCGerenciaId(rs.getLong("C_GERENCIA_ID"));
				datos.setCProvinciaId(rs.getString("C_PROVINCIA_ID"));
				datos.setCTipogerenciaId(rs.getLong("C_TIPOGERENCIA_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
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

	public ArrayList listadoOCAPGerenciasSolicitud(int inicio, int cuantos, String idProv, String bBorrado)
			throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT C_GERENCIA_ID, B_BORRADO, D_NOMBRE, C_TIPOGERENCIA_ID, F_USUMODI, F_USUALTA, C_PROVINCIA_ID FROM OCAP_GERENCIAS WHERE 0=0 ";

			if ((idProv != null) && (!idProv.equals("")))
				sql = sql + " AND C_PROVINCIA_ID = " + idProv;
			if ((bBorrado != null) && (!bBorrado.equals(""))) {
				sql = sql + " AND B_BORRADO = '" + bBorrado + "'";
			}
			sql = sql + " order by C_GERENCIA_ID";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			int i = 0;
			listado = new ArrayList();
			while (rs.next()) {
				OCAPGerenciasOT datos = new OCAPGerenciasOT();
				datos.setCGerenciaId(rs.getLong("C_GERENCIA_ID"));
				datos.setCProvinciaId(rs.getString("C_PROVINCIA_ID"));
				datos.setCTipogerenciaId(rs.getLong("C_TIPOGERENCIA_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
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

	public int listadoOCAPGerenciasCuenta(long cGerenciaId, String cProvinciaId, long cTipogerenciaId, String dNombre,
			String dNombreCorto, String aCodldap, String dGerente, String dDirector, String fCreacion,
			String fModificacion) throws SQLException, Exception {
		ResultSet rs = null;
		Statement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int total = -1;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String sql = "SELECT COUNT(*) FROM OCAP_GERENCIAS ";

			boolean isCGerenciaId = cGerenciaId != 0L;
			if (isCGerenciaId)
				sbWhere.append("C_GERENCIA_ID = ").append(cGerenciaId).append(" AND ");
			boolean isCProvinciaId = (cProvinciaId != null) && (!cProvinciaId.equals(""));
			if (isCProvinciaId)
				sbWhere.append("C_PROVINCIA_ID = ").append(cProvinciaId).append(" AND ");
			boolean isCTipogerenciaId = cTipogerenciaId != 0L;
			if (isCTipogerenciaId)
				sbWhere.append("C_TIPOGERENCIA_ID = ").append(cTipogerenciaId).append(" AND ");
			boolean isDNombreCorto = (dNombreCorto != null) && (!dNombreCorto.equals(""));
			if (isDNombreCorto)
				sbWhere.append("(upper(D_NOMBRE_CORTO) like upper('%").append(dNombreCorto).append("%')) AND ");
			boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
			if (isDNombre)
				sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
			boolean isACodldap = (aCodldap != null) && (!aCodldap.equals(""));
			if (isACodldap)
				sbWhere.append("(upper(A_CODLDAP) like upper('%").append(aCodldap).append("%')) AND ");
			boolean isDGerente = (dGerente != null) && (!dGerente.equals(""));
			if (isDGerente)
				sbWhere.append("(upper(D_GERENTE) like upper('%").append(dGerente).append("%')) AND ");
			boolean isDDirector = (dDirector != null) && (!dDirector.equals(""));
			if (isDDirector)
				sbWhere.append("(upper(D_DIRECTOR) like upper('%").append(dDirector).append("%')) AND ");
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

	public ArrayList consultaOCAPGerencias(long cGerenciaId, String cProvinciaId, long cTipogerenciaId, String dNombre,
			String dNombreCorto, String aCodldap, String dGerente, String dDirector, String fCreacion,
			String fModificacion, int inicio, int cuantos) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		ArrayList listado = null;

		Connection con = JCYLGestionTransacciones.getConnection();

		try {

			this.loggerBD.info("Autocommit Inicio " + con.getAutoCommit());
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);

			String selectFrom = "SELECT C_GERENCIA_ID, B_BORRADO, D_NOMBRE, C_TIPOGERENCIA_ID, F_USUMODI, F_USUALTA, C_PROVINCIA_ID, D_NOMBRE_CORTO, A_CODLDAP, D_GERENTE, D_DIRECTOR, (SELECT D_PROVINCIA FROM COMU_PROVINCIAS PROV WHERE PROV.C_PROVINCIA_ID = GER.C_PROVINCIA_ID) AS D_PROVINCIA FROM OCAP_GERENCIAS GER ";

			String orderBy = " order by C_GERENCIA_ID";
			boolean isCGerenciaId = cGerenciaId != 0L;
			if (isCGerenciaId)
				sbWhere.append("C_GERENCIA_ID = ?  AND ");
			boolean isCProvinciaId = (cProvinciaId != null) && (!cProvinciaId.equals(""));
			if (isCProvinciaId)
				sbWhere.append("C_PROVINCIA_ID = ?  AND ");
			boolean isCTipogerenciaId = cTipogerenciaId != 0L;
			if (isCTipogerenciaId)
				sbWhere.append("C_TIPOGERENCIA_ID = ?  AND ");
			boolean isDNombreCorto = (dNombreCorto != null) && (!dNombreCorto.equals(""));
			if (isDNombreCorto)
				sbWhere.append("(upper(D_NOMBRE_CORTO) like upper('%").append(dNombreCorto).append("%')) AND ");
			boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
			if (isDNombre)
				sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
			boolean isACodldap = (aCodldap != null) && (!aCodldap.equals(""));
			if (isACodldap)
				sbWhere.append("(upper(A_CODLDAP) = upper('").append(aCodldap).append("')) AND ");
			boolean isDGerente = (dGerente != null) && (!dGerente.equals(""));
			if (isDGerente)
				sbWhere.append("(upper(D_GERENTE) like upper('%").append(dGerente).append("%')) AND ");
			boolean isDDirector = (dDirector != null) && (!dDirector.equals(""));
			if (isDDirector)
				sbWhere.append("(upper(D_DIRECTOR) like upper('%").append(dDirector).append("%')) AND ");
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

			this.loggerBD.info("Sentencia SQL:" + sqlStatement);

			st = con.prepareStatement(sqlStatement, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			int index = 1;

			if (isCGerenciaId)
				st.setLong(index++, cGerenciaId);
			if (isCProvinciaId)
				st.setString(index++, cProvinciaId);
			if (isCTipogerenciaId) {
				st.setLong(index++, cTipogerenciaId);
			}
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}

			listado = new ArrayList();

			int i = 0;
			while (rs.next()) {

				OCAPGerenciasOT datos = new OCAPGerenciasOT();
				datos.setCGerenciaId(rs.getLong("C_GERENCIA_ID"));
				datos.setCProvinciaId(rs.getString("C_PROVINCIA_ID"));
				datos.setCTipogerenciaId(rs.getLong("C_TIPOGERENCIA_ID"));
				datos.setDNombreCorto(rs.getString("D_NOMBRE_CORTO"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setACodldap(rs.getString("A_CODLDAP"));
				datos.setDGerente(rs.getString("D_GERENTE"));
				datos.setDDirector(rs.getString("D_DIRECTOR"));
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

	public OCAPGerenciasOT buscarOCAPGerenciasCodLDAP(String aCodLdap) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPGerenciasOT datos = null;
		try {
			this.loggerBD.info("buscarOCAPGerenciasCodLDAP: " + aCodLdap);

			String sql = "SELECT C_GERENCIA_ID, B_BORRADO, D_NOMBRE, C_TIPOGERENCIA_ID, F_USUMODI, F_USUALTA, C_PROVINCIA_ID, D_NOMBRE_CORTO, A_CODLDAP, D_GERENTE, D_DIRECTOR FROM OCAP_GERENCIAS WHERE upper(A_CODLDAP) = upper(?) AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setString(1, aCodLdap);

			rs = st.executeQuery();

			datos = new OCAPGerenciasOT();
			if (rs.next()) {
				datos.setCGerenciaId(rs.getLong("C_GERENCIA_ID"));
				datos.setCProvinciaId(rs.getString("C_PROVINCIA_ID"));
				datos.setCTipogerenciaId(rs.getLong("C_TIPOGERENCIA_ID"));
				datos.setDNombreCorto(rs.getString("D_NOMBRE_CORTO"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setACodldap(rs.getString("A_CODLDAP"));
				datos.setDGerente(rs.getString("D_GERENTE"));
				datos.setDDirector(rs.getString("D_DIRECTOR"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
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
}
