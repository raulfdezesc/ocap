package es.jcyl.fqs.ocap.oad.categProfesionales;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.categProfesionales.OCAPCategProfesionalesOT;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPCategProfesionalesOAD {
	public Logger loggerBD;
	private static OCAPCategProfesionalesOAD instance;

	private void $init$() {
		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public static OCAPCategProfesionalesOAD getInstance() {
		if (instance == null) {
			instance = new OCAPCategProfesionalesOAD();
		}
		return instance;
	}

	private OCAPCategProfesionalesOAD() {
		$init$();
	}

	public int altaOCAPCategProfesionales(OCAPCategProfesionalesOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_CATEG_PROFESIONALES (C_ESTATUT_ID, B_BORRADO, D_DESCRIPCION, D_NOMBRE, C_PROFESIONAL_ID, F_USUALTA, C_USUALTA, C_MODALIDAD_ID) VALUES (?, 'N', ?, ?, OCAP_CAT_ID_SEQ.nextval, SYSDATE, ?, ?)";

			st = con.prepareStatement(sql);
			st.setLong(1, datos.getCEstatutId());

			if (datos.getDDescripcion() != null)
				st.setString(2, datos.getDDescripcion());
			else {
				st.setNull(2, 12);
			}
			st.setString(3, datos.getDNombre());
			st.setString(4, datos.getACreadoPor());

			if (datos.getCModalidadId() != 0L)
				st.setLong(5, datos.getCModalidadId());
			else {
				st.setNull(5, 2);
			}
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

	public int bajaOCAPCategProfesionales(long cProfesionalId) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_CATEG_PROFESIONALES SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_PROFESIONAL_ID =  ?";

			st = con.prepareStatement(sql);
			st.setLong(1, cProfesionalId);

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

	public int modificacionOCAPCategProfesionales(OCAPCategProfesionalesOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_CATEG_PROFESIONALES SET C_ESTATUT_ID = ?, D_DESCRIPCION = ?, D_NOMBRE = ?, C_MODALIDAD_ID = ?, F_USUMODI = SYSDATE WHERE C_PROFESIONAL_ID = ?";

			st = con.prepareStatement(sql);
			st.setLong(1, datos.getCEstatutId());

			if (datos.getDDescripcion() != null)
				st.setString(2, datos.getDDescripcion());
			else {
				st.setNull(2, 12);
			}
			st.setString(3, datos.getDNombre());

			if (datos.getCModalidadId() != 0L)
				st.setLong(4, datos.getCModalidadId());
			else {
				st.setNull(4, 2);
			}
			st.setLong(5, datos.getCProfesionalId());

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

	public OCAPCategProfesionalesOT buscarOCAPCategProfesionales(long cProfesionalId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPCategProfesionalesOT datos = null;
		try {
			this.loggerBD.info(" buscarOCAPCategProfesionales: " + cProfesionalId);

			String sql = "SELECT C_ESTATUT_ID, B_BORRADO, D_DESCRIPCION, D_NOMBRE, C_PROFESIONAL_ID, F_USUMODI, F_USUALTA, C_MODALIDAD_ID FROM OCAP_CATEG_PROFESIONALES WHERE C_PROFESIONAL_ID = ? ";

			st = con.prepareStatement(sql);
			st.setLong(1, cProfesionalId);

			rs = st.executeQuery();

			datos = new OCAPCategProfesionalesOT();
			if (rs.next()) {
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCProfesionalId(cProfesionalId);
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setCModalidadId(rs.getLong("C_MODALIDAD_ID"));
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

	public ArrayList listadoOCAPCategProfesionales() throws SQLException, Exception {
		return listadoOCAPCategProfesionales(1, -1);
	}

	public ArrayList listadoOCAPCategProfesionales(int inicio, int cuantos) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT c_estatut_id, d_descripcion, d_nombre, ")
					.append(" c_profesional_id, b_borrado, f_usumodi, f_usualta ")
					.append(" FROM ocap_categ_profesionales  ").append(" WHERE b_borrado = 'N' ")
					.append(" ORDER BY  D_NOMBRE ");

			st = con.prepareStatement(sql.toString(), 1004, 1008);

			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPCategProfesionalesOT datos = new OCAPCategProfesionalesOT();
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
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

	public int listadoOCAPCategProfesionalesCuenta(long cProfesionalId, String dNombre, long cEstatutId,
			String dDescripcionripcion, String fCreacion, String fModificacion, long cModalidadId)
			throws SQLException, Exception {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int total = -1;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);

			String sql = "SELECT COUNT(*) FROM OCAP_CATEG_PROFESIONALES";

			boolean isCProfesionalId = cProfesionalId != 0L;
			if (isCProfesionalId)
				sbWhere.append("C_PROFESIONAL_ID = ").append(cProfesionalId).append(" AND ");
			boolean isDDescripcion = (dDescripcionripcion != null) && (!dDescripcionripcion.equals(""));
			if (isDDescripcion)
				sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dDescripcionripcion).append("%')) AND ");
			boolean isCEstatutId = cEstatutId != 0L;
			if (isCEstatutId)
				sbWhere.append("C_ESTATUT_ID = ").append(cEstatutId).append(" AND ");
			boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
			if (isDNombre)
				sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
			boolean isFCreacion = (fCreacion != null) && (!fCreacion.equals(""));
			if (isFCreacion)
				sbWhere.append("to_char(F_USUALTA,'DD/MM/RRRR') = '").append(fCreacion).append("' AND ");
			boolean isFModificacion = (fModificacion != null) && (!fModificacion.equals(""));
			if (isFModificacion)
				sbWhere.append("to_char(F_USUMODI,'DD/MM/RRRR') = '").append(fModificacion).append("' AND ");
			boolean isCModalidadId = cModalidadId != 0L;
			if (isCModalidadId)
				sbWhere.append("C_MODALIDAD_ID = ").append(cModalidadId).append(" AND ");
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

	public ArrayList consultaOCAPCategProfesionales(long cProfesionalId, String dNombre, long cEstatutId,
			long cPersonalId, String dDescripcionripcion, String fCreacion, String fModificacion, long cModalidadId,
			int inicio, int cuantos) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT C_PROFESIONAL_ID, B_BORRADO, D_NOMBRE, C_ESTATUT_ID, F_USUMODI, F_USUALTA, D_DESCRIPCION, C_MODALIDAD_ID FROM OCAP_CATEG_PROFESIONALES";

			String orderBy = " order by D_NOMBRE";
			boolean isCProfesionalId = cProfesionalId != 0L;
			if (isCProfesionalId)
				sbWhere.append("C_PROFESIONAL_ID = ?  AND ");
			boolean isDDescripcion = (dDescripcionripcion != null) && (!dDescripcionripcion.equals(""));
			if (isDDescripcion)
				sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dDescripcionripcion).append("%')) AND ");
			boolean isCEstatutId = cEstatutId != 0L;
			if (isCEstatutId)
				sbWhere.append("C_ESTATUT_ID = ?  AND ");
			boolean isCPersonalId = cPersonalId != 0L;
			if (isCPersonalId)
				sbWhere.append(
						"C_ESTATUT_ID IN (select c_estatut_id from ocap_estatutario where c_personal_id = ?) AND ");
			boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
			if (isDNombre)
				sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
			boolean isFCreacion = (fCreacion != null) && (!fCreacion.equals(""));
			if (isFCreacion)
				sbWhere.append("to_char(F_USUALTA,'DD/MM/RRRR') = '").append(fCreacion).append("' AND ");
			boolean isFModificacion = (fModificacion != null) && (!fModificacion.equals(""));
			if (isFModificacion)
				sbWhere.append("to_char(F_USUMODI,'DD/MM/RRRR') = '").append(fModificacion).append("' AND ");
			boolean isCModalidadId = cModalidadId != 0L;
			if (isCModalidadId)
				sbWhere.append("C_MODALIDAD_ID = ?  AND ");
			sbWhere.append("B_BORRADO = 'N'  AND ");
			int len = sbWhere.length();
			if (len > 0) {
				where = sbWhere.insert(0, " Where ").substring(0, len + 1);
			}
			String sqlStatement = selectFrom + where + orderBy;

			this.loggerBD.info("Sentencia SQL:" + sqlStatement);

			st = con.prepareStatement(sqlStatement, 1004, 1008);

			int index = 1;
			if (isCProfesionalId) {
				st.setLong(index++, cProfesionalId);
			}
			if (isCEstatutId)
				st.setLong(index++, cEstatutId);
			if (isCPersonalId)
				st.setLong(index++, cPersonalId);
			if (isCModalidadId)
				st.setLong(index++, cModalidadId);
			rs = st.executeQuery();
			if (inicio > 1)
				rs.absolute(inicio - 1);
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPCategProfesionalesOT datos = new OCAPCategProfesionalesOT();
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setCModalidadId(rs.getLong("C_MODALIDAD_ID"));

				listado.add(datos);
				if (++i == cuantos)	break;
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

	public ArrayList listarCategoriasRJ(String cJuridico) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sqlStatement = "SELECT C_PROFESIONAL_ID, B_BORRADO, D_NOMBRE, C_ESTATUT_ID, F_USUMODI, F_USUALTA, D_DESCRIPCION FROM OCAP_CATEG_PROFESIONALES ";

			if (cJuridico.equals("2")) {
				sqlStatement = sqlStatement
						+ "WHERE C_ESTATUT_ID IN (select c_estatut_id from ocap_estatutario where c_personal_id = ?)";
			}

			sqlStatement = sqlStatement + " order by D_NOMBRE ";
			this.loggerBD.info("Sentencia SQL:" + sqlStatement);

			st = con.prepareStatement(sqlStatement, 1004, 1008);

			if (cJuridico.equals("2")) {
				cJuridico = "3";
				st.setString(1, cJuridico);
			}

			rs = st.executeQuery();

			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPCategProfesionalesOT datos = new OCAPCategProfesionalesOT();
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));

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

	public ArrayList<OCAPCategProfesionalesOT> listarCategoriasRJFijo(String cJuridico) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList<OCAPCategProfesionalesOT> listado = null;
		try {
			String sqlStatement = "SELECT C_PROFESIONAL_ID, B_BORRADO, D_NOMBRE, C_ESTATUT_ID, F_USUMODI, F_USUALTA, D_DESCRIPCION FROM OCAP_CATEG_PROFESIONALES WHERE C_ESTATUT_ID IN (select c_estatut_id from ocap_estatutario where c_personal_id = ?)  order by  D_NOMBRE";

			this.loggerBD.info("Sentencia SQL:" + sqlStatement);

			st = con.prepareStatement(sqlStatement, 1004, 1008);

			st.setString(1, cJuridico);

			rs = st.executeQuery();

			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPCategProfesionalesOT datos = new OCAPCategProfesionalesOT();
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));

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

	public ArrayList listarCategoriasProfesionales(String tabla) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			StringBuffer sbWhere = new StringBuffer(40);
			String sql = "SELECT C_PROFESIONAL_ID, D_NOMBRE FROM OCAP_CATEG_PROFESIONALES WHERE B_BORRADO = 'N' ";

			if (tabla.equals("Puesto"))
				sbWhere.append("AND C_ESTATUT_ID IN(7,8,9,10,11)");
			if (tabla.equals("Unidad"))
				sbWhere.append("AND C_ESTATUT_ID IN(3,4)");
			if (tabla.equals("Servicio"))
				sbWhere.append("AND C_ESTATUT_ID = 1");

			String sqlStatement = sql + sbWhere;

			st = con.prepareStatement(sqlStatement, 1004, 1008);

			rs = st.executeQuery();

			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPCategProfesionalesOT datos = new OCAPCategProfesionalesOT();
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));

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
