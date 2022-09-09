package es.jcyl.fqs.ocap.oad.centroTrabajo;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.centroTrabajo.OCAPCentroTrabajoOT;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPCentroTrabajoOAD {
	public Logger loggerBD;
	private static OCAPCentroTrabajoOAD instance;

	private void $init$() {
		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public static OCAPCentroTrabajoOAD getInstance() {
		if (instance == null) {
			instance = new OCAPCentroTrabajoOAD();
		}
		return instance;
	}

	private OCAPCentroTrabajoOAD() {
		$init$();
	}

	public int altaOCAPCentroTrabajo(OCAPCentroTrabajoOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_CENTROSTRABAJO (C_CENTROTRABAJO_ID, C_GERENCIA_ID, B_BORRADO, D_NOMBRE, F_USUALTA, D_DESCRIPCION, A_OBSERVACIONES, A_DIRECCION, A_CODIGOPOSTAL, A_TELEFONO, A_CIUDAD, C_USUALTA) VALUES (OCAP_CEN_ID_SEQ.nextval, ?,'N', ?, SYSDATE, ?, ?, ?, ?, ?, ?, ?)";

			this.loggerBD.info("Sentencia SQL: " + sql);

			st = con.prepareStatement(sql);

			st.setLong(1, datos.getCGerenciaId());
			st.setString(2, datos.getDNombre());

			if (datos.getDDescripcion() != null)
				st.setString(3, datos.getDDescripcion());
			else {
				st.setNull(3, 12);
			}
			if (datos.getAObservaciones() != null)
				st.setString(4, datos.getAObservaciones());
			else {
				st.setNull(4, 12);
			}
			if (datos.getADireccion() != null)
				st.setString(5, datos.getADireccion());
			else {
				st.setNull(5, 12);
			}
			if (datos.getACodigopostal() != null)
				st.setString(6, datos.getACodigopostal());
			else {
				st.setNull(6, 12);
			}
			if (datos.getATelefono() != null)
				st.setString(7, datos.getATelefono());
			else {
				st.setNull(7, 12);
			}
			if (datos.getACiudad() != null)
				st.setString(8, datos.getACiudad());
			else {
				st.setNull(8, 12);
			}
			st.setString(9, datos.getACreadoPor());

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

	public int bajaOCAPCentroTrabajo(int cCentrotrabajoId) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			String sql = "UPDATE OCAP_CENTROSTRABAJO SET B_BORRADO = 'Y',  F_USUMODI = SYSDATE WHERE C_CENTROTRABAJO_ID =  ?";

			st = con.prepareStatement(sql);

			st.setInt(1, cCentrotrabajoId);

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

	public int modificacionOCAPCentroTrabajo(OCAPCentroTrabajoOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			String sql = "UPDATE OCAP_CENTROSTRABAJO SET\tD_NOMBRE = ?, F_USUMODI = SYSDATE, D_DESCRIPCION = ?, C_GERENCIA_ID = ?, A_OBSERVACIONES = ?, A_DIRECCION = ?, A_CODIGOPOSTAL = ?, A_TELEFONO = ?, A_CIUDAD = ? WHERE C_CENTROTRABAJO_ID = ?";

			st = con.prepareStatement(sql);

			st.setString(1, datos.getDNombre());

			if (datos.getDDescripcion() != null)
				st.setString(2, datos.getDDescripcion());
			else {
				st.setNull(2, 12);
			}
			st.setLong(3, datos.getCGerenciaId());

			if (datos.getAObservaciones() != null)
				st.setString(4, datos.getAObservaciones());
			else {
				st.setNull(4, 12);
			}
			if (datos.getADireccion() != null)
				st.setString(5, datos.getADireccion());
			else {
				st.setNull(5, 12);
			}
			if (datos.getACodigopostal() != null)
				st.setString(6, datos.getACodigopostal());
			else {
				st.setNull(6, 12);
			}
			if (datos.getATelefono() != null)
				st.setString(7, datos.getATelefono());
			else {
				st.setNull(7, 12);
			}
			if (datos.getACiudad() != null)
				st.setString(8, datos.getACiudad());
			else {
				st.setNull(8, 12);
			}
			st.setLong(9, datos.getCCentrotrabajoId());

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

	public OCAPCentroTrabajoOT buscarOCAPCentroTrabajo(long cCentrotrabajoId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPCentroTrabajoOT datos = null;
		try {
			this.loggerBD.info(" buscarOCAPCentroTrabajo: " + cCentrotrabajoId);

			String sql = "SELECT C_CENTROTRABAJO_ID, C_GERENCIA_ID, D_NOMBRE, D_DESCRIPCION, A_OBSERVACIONES, F_USUALTA, F_USUMODI, B_BORRADO, A_DIRECCION, A_CODIGOPOSTAL, A_TELEFONO, A_CIUDAD FROM OCAP_CENTROSTRABAJO WHERE C_CENTROTRABAJO_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cCentrotrabajoId);

			rs = st.executeQuery();

			datos = new OCAPCentroTrabajoOT();
			if (rs.next()) {
				datos.setCCentrotrabajoId(rs.getLong("C_CENTROTRABAJO_ID"));
				datos.setCGerenciaId(rs.getLong("C_GERENCIA_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setAObservaciones(rs.getString("A_OBSERVACIONES"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setADireccion(rs.getString("A_DIRECCION"));
				datos.setACodigopostal(rs.getString("A_CODIGOPOSTAL"));
				datos.setATelefono(rs.getString("A_TELEFONO"));
				datos.setACiudad(rs.getString("A_CIUDAD"));
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

	public ArrayList listadoOCAPCentroTrabajo(String idGerencia, String bBorrado, String codLdapGerencia)
			throws SQLException, Exception {
		return listadoOCAPCentroTrabajo(1, -1, idGerencia, bBorrado, codLdapGerencia);
	}

	public ArrayList listadoOCAPCentroTrabajo(int inicio, int cuantos, String idGerencia, String bBorrado,
			String codLdapGerencia) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT cen.F_USUALTA, cen.A_OBSERVACIONES, cen.C_GERENCIA_ID, cen.A_DIRECCION, cen.D_DESCRIPCION, cen.C_CENTROTRABAJO_ID, cen.D_NOMBRE, cen.A_TELEFONO, cen.A_CODIGOPOSTAL, cen.A_CIUDAD, cen.B_BORRADO, cen.F_USUMODI FROM OCAP_CENTROSTRABAJO cen ";

			if ((codLdapGerencia != null) && (!codLdapGerencia.equals("")))
				sql = sql + ", OCAP_GERENCIAS ger ";
			sql = sql + "WHERE 0=0 ";
			if ((idGerencia != null) && (!idGerencia.equals("")))
				sql = sql + " AND cen.C_GERENCIA_ID = " + idGerencia;
			if ((bBorrado != null) && (!bBorrado.equals("")))
				sql = sql + " AND cen.B_BORRADO = '" + bBorrado + "'";
			if ((codLdapGerencia != null) && (!codLdapGerencia.equals(""))) {
				sql = sql + "AND cen.C_GERENCIA_ID = ger.C_GERENCIA_ID AND upper(ger.A_CODLDAP) = '" + codLdapGerencia
						+ "'";
			}
			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPCentroTrabajoOT datos = new OCAPCentroTrabajoOT();
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setAObservaciones(rs.getString("A_OBSERVACIONES"));
				datos.setCGerenciaId(rs.getLong("C_GERENCIA_ID"));
				datos.setADireccion(rs.getString("A_DIRECCION"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setCCentrotrabajoId(rs.getLong("C_CENTROTRABAJO_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setATelefono(rs.getString("A_TELEFONO"));
				datos.setACodigopostal(rs.getString("A_CODIGOPOSTAL"));
				datos.setACiudad(rs.getString("A_CIUDAD"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
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

	public int listadoOCAPCentroTrabajoCuenta(long cCentrotrabajoId, long cGerenciaId, String dNombre,
			String dDescripcion, String dObservaciones, String dDireccion, String dCodigopostal, String dTelefono,
			String dCiudad, String fCreacion, String fModificacion) throws SQLException, Exception {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int total = -1;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);

			String sql = "SELECT COUNT(*) FROM OCAP_CENTROSTRABAJO";

			boolean isCCentrotrabajoId = cCentrotrabajoId != 0L;
			if (isCCentrotrabajoId)
				sbWhere.append("C_CENTROTRABAJO_ID = ").append(cCentrotrabajoId).append(" AND ");
			boolean isCGerenciaId = cGerenciaId != 0L;
			if (isCGerenciaId)
				sbWhere.append("C_GERENCIA_ID = ").append(cGerenciaId).append(" AND ");
			boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
			if (isDNombre)
				sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
			boolean isDDescripcion = (dDescripcion != null) && (!dDescripcion.equals(""));
			if (isDDescripcion)
				sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dDescripcion).append("%')) AND ");
			boolean isDObservaciones = (dObservaciones != null) && (!dObservaciones.equals(""));
			if (isDObservaciones)
				sbWhere.append("(upper(A_OBSERVACIONES) like upper('%").append(dObservaciones).append("%')) AND ");
			boolean isDDireccion = (dDireccion != null) && (!dDireccion.equals(""));
			if (isDDireccion)
				sbWhere.append("(upper(A_DIRECCION) like upper('%").append(dDireccion).append("%')) AND ");
			boolean isDCodigopostal = (dCodigopostal != null) && (!dCodigopostal.equals(""));
			if (isDCodigopostal)
				sbWhere.append("(upper(A_CODIGOPOSTAL) like upper('%").append(dCodigopostal).append("%')) AND ");
			boolean isDTelefono = (dTelefono != null) && (!dTelefono.equals(""));
			if (isDTelefono)
				sbWhere.append("(upper(A_TELEFONO) like upper('%").append(dTelefono).append("%')) AND ");
			boolean isDCiudad = (dCiudad != null) && (!dCiudad.equals(""));
			if (isDCiudad)
				sbWhere.append("(upper(A_CIUDAD) like upper('%").append(dCiudad).append("%')) AND ");
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

	public ArrayList consultaOCAPCentroTrabajo(long cCentrotrabajoId, long cGerenciaId, String dNombre,
			String dDescripcion, String dObservaciones, String dDireccion, String dCodigopostal, String dTelefono,
			String dCiudad, String fCreacion, String fModificacion, int inicio, int cuantos)
			throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT C_CENTROTRABAJO_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION, A_OBSERVACIONES, A_DIRECCION, A_CODIGOPOSTAL, A_TELEFONO, A_CIUDAD, C_GERENCIA_ID FROM OCAP_CENTROSTRABAJO";

			String orderBy = " order by C_CENTROTRABAJO_ID";
			boolean isCCentrotrabajoId = cCentrotrabajoId != 0L;
			if (isCCentrotrabajoId)
				sbWhere.append("C_CENTROTRABAJO_ID = ?  AND ");
			boolean isCGerenciaId = cGerenciaId != 0L;
			if (isCGerenciaId)
				sbWhere.append("C_GERENCIA_ID = ?  AND ");
			boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
			if (isDNombre)
				sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
			boolean isDDescripcion = (dDescripcion != null) && (!dDescripcion.equals(""));
			if (isDDescripcion)
				sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dDescripcion).append("%')) AND ");
			boolean isDObservaciones = (dObservaciones != null) && (!dObservaciones.equals(""));
			if (isDObservaciones)
				sbWhere.append("(upper(A_OBSERVACIONES) like upper('%").append(dObservaciones).append("%')) AND ");
			boolean isDDireccion = (dDireccion != null) && (!dDireccion.equals(""));
			if (isDDireccion)
				sbWhere.append("(upper(A_DIRECCION) like upper('%").append(dDireccion).append("%')) AND ");
			boolean isDCodigopostal = (dCodigopostal != null) && (!dCodigopostal.equals(""));
			if (isDCodigopostal)
				sbWhere.append("(upper(A_CODIGOPOSTAL) like upper('%").append(dCodigopostal).append("%')) AND ");
			boolean isDTelefono = (dTelefono != null) && (!dTelefono.equals(""));
			if (isDTelefono)
				sbWhere.append("(upper(A_TELEFONO) like upper('%").append(dTelefono).append("%')) AND ");
			boolean isDCiudad = (dCiudad != null) && (!dCiudad.equals(""));
			if (isDCiudad) {
				sbWhere.append("(upper(A_CIUDAD) like upper('%").append(dCiudad).append("%')) AND ");
			}
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

			st = con.prepareStatement(sqlStatement, 1004, 1008);

			int index = 1;

			if (isCCentrotrabajoId) {
				st.setLong(index++, cCentrotrabajoId);
			}
			if (isCGerenciaId) {
				st.setLong(index++, cGerenciaId);
			}
			rs = st.executeQuery();

			if (inicio > 1)
				rs.absolute(inicio - 1);
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPCentroTrabajoOT datos = new OCAPCentroTrabajoOT();
				datos.setCCentrotrabajoId(rs.getLong("C_CENTROTRABAJO_ID"));
				datos.setCGerenciaId(rs.getLong("C_GERENCIA_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setAObservaciones(rs.getString("A_OBSERVACIONES"));
				datos.setADireccion(rs.getString("A_DIRECCION"));
				datos.setACodigopostal(rs.getString("A_CODIGOPOSTAL"));
				datos.setATelefono(rs.getString("A_TELEFONO"));
				datos.setACiudad(rs.getString("A_CIUDAD"));
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
}
