package es.jcyl.fqs.ocap.oad.especialidades;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.especialidades.OCAPEspecialidadesOT;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPEspecialidadesOAD {
	public Logger loggerBD;
	private static OCAPEspecialidadesOAD instance;

	private void $init$() {
		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public static OCAPEspecialidadesOAD getInstance() {
		if (instance == null) {
			instance = new OCAPEspecialidadesOAD();
		}
		return instance;
	}

	private OCAPEspecialidadesOAD() {
		$init$();
	}

	public int altaOCAPEspecialidades(OCAPEspecialidadesOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_ESPECIALIDADES (C_ESPEC_ID, C_PROFESIONAL_ID, B_BORRADO, D_NOMBRE, F_USUALTA, D_DESCRIPCION, C_USUALTA) VALUES (OCAP_ESP_ID_SEQ.nextval, ?, 'N', ?, SYSDATE, ?, ?)";

			this.loggerBD.info("Sentencia SQL:" + sql);
			st = con.prepareStatement(sql);
			st.setLong(1, datos.getCProfesionalId());

			if (datos.getDNombre() != null)
				st.setString(2, datos.getDNombre());
			else {
				st.setNull(2, 12);
			}
			if (datos.getDDescripcion() != null)
				st.setString(3, datos.getDDescripcion());
			else {
				st.setNull(3, 12);
			}
			st.setString(4, datos.getACreadoPor());
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

	public int bajaOCAPEspecialidades(int cEspecId) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_ESPECIALIDADES SET B_BORRADO = 'Y',  F_USUMODI = SYSDATE WHERE C_ESPEC_ID =  ?";

			st = con.prepareStatement(sql);

			st.setInt(1, cEspecId);

			filas = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null) {
				st.close();
			}
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
	}

	public int modificacionOCAPEspecialidades(OCAPEspecialidadesOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_ESPECIALIDADES SET D_NOMBRE = ?, F_USUMODI = SYSDATE, D_DESCRIPCION = ?, C_PROFESIONAL_ID = ? WHERE C_ESPEC_ID = ?";

			st = con.prepareStatement(sql);

			if (datos.getDNombre() != null)
				st.setString(1, datos.getDNombre());
			else {
				st.setNull(1, 12);
			}
			if (datos.getDDescripcion() != null)
				st.setString(2, datos.getDDescripcion());
			else {
				st.setNull(2, 12);
			}
			st.setLong(3, datos.getCProfesionalId());

			st.setLong(4, datos.getCEspecId());

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

	public OCAPEspecialidadesOT buscarOCAPEspecialidades(long cEspecId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPEspecialidadesOT datos = null;
		try {
			this.loggerBD.info(" buscarOCAPEspecialidades: " + cEspecId);

			String sql = "SELECT C_ESPEC_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION, C_PROFESIONAL_ID FROM OCAP_ESPECIALIDADES WHERE C_ESPEC_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cEspecId);

			rs = st.executeQuery();

			datos = new OCAPEspecialidadesOT();
			if (rs.next()) {
				datos.setCEspecId(rs.getLong("C_ESPEC_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
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

	public ArrayList listadoOCAPEspecialidades(long idCategProfesional, String bBorrado)
			throws SQLException, Exception {
		return listadoOCAPEspecialidades(1, -1, idCategProfesional, bBorrado);
	}

	public ArrayList<OCAPEspecialidadesOT> listadoOCAPEspecialidades(int inicio, int cuantos, long idCategProfesional, String bBorrado)
			throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList<OCAPEspecialidadesOT> listado = null;
		try {
			String sql = "SELECT D_DESCRIPCION, B_BORRADO, C_PROFESIONAL_ID, F_USUMODI, F_USUALTA, D_NOMBRE, C_ESPEC_ID FROM OCAP_ESPECIALIDADES WHERE 0=0 ";

			if (idCategProfesional != 0L)
				sql = sql + " AND C_PROFESIONAL_ID = " + idCategProfesional;
			if ((bBorrado != null) && (!bBorrado.equals(""))) {
				sql = sql + " AND B_BORRADO = '" + bBorrado + "'";
			}
			sql = sql + "  order by D_NOMBRE";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPEspecialidadesOT datos = new OCAPEspecialidadesOT();
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCEspecId(rs.getLong("C_ESPEC_ID"));
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

	public int listadoOCAPEspecialidadesCuenta(long cEspecId, long cProfesionalId, String dNombre, String dDescripcion,
			String fCreacion, String fModificacion) throws SQLException, Exception {
		ResultSet rs = null;
		PreparedStatement st = null;

		Connection con = JCYLGestionTransacciones.getConnection();

		int total = -1;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String sql = "SELECT COUNT(*) FROM OCAP_ESPECIALIDADES ";

			boolean isCEspecId = cEspecId != 0L;
			if (isCEspecId)
				sbWhere.append("C_ESPEC_ID = ").append(cEspecId).append(" AND ");
			boolean isCProfesionalId = cProfesionalId != 0L;
			if (isCProfesionalId)
				sbWhere.append("C_PROFESIONAL_ID = ").append(cProfesionalId).append(" AND ");
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

	public ArrayList consultaOCAPEspecialidades(long cEspecId, long cProfesionalId, String dNombre, String dDescripcion,
			String fCreacion, String fModificacion, int inicio, int cuantos) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT C_ESPEC_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION, C_PROFESIONAL_ID FROM OCAP_ESPECIALIDADES";

			String orderBy = " order by D_NOMBRE";
			boolean isCEspecId = cEspecId != 0L;
			if (isCEspecId)
				sbWhere.append("C_ESPEC_ID = ?  AND ");
			boolean isCProfesionalId = cProfesionalId != 0L;
			if (isCProfesionalId)
				sbWhere.append("C_PROFESIONAL_ID = ?  AND ");
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

			if (isCEspecId)
				st.setLong(index++, cEspecId);
			if (isCProfesionalId) {
				st.setLong(index++, cProfesionalId);
			}
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();

			int i = 0;
			while (rs.next()) {
				OCAPEspecialidadesOT datos = new OCAPEspecialidadesOT();
				datos.setCEspecId(rs.getLong("C_ESPEC_ID"));
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
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

	public ArrayList listarEspecialidadesServicio() throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT C_ESPEC_ID, D_DESCRIPCION FROM OCAP_ESPECIALIDADES WHERE D_DESCRIPCION IN ('Hospitalización a Domicilio','Unidad  de Cuidados Paliativos') AND B_BORRADO = 'N' ";

			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();

			listado = new ArrayList();

			int i = 0;
			while (rs.next()) {
				OCAPEspecialidadesOT datos = new OCAPEspecialidadesOT();
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setCEspecId(rs.getLong("C_ESPEC_ID"));
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
