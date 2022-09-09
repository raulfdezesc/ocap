package es.jcyl.fqs.ocap.oad.modalidadAnterior;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.modalidadAnterior.OCAPModalidadAnteriorOT;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 * @author 
 *
 */
public class OCAPModalidadAnteriorOAD {
	public static Logger logger = OCAPConfigApp.logger;
	public Logger loggerBD;
	private static OCAPModalidadAnteriorOAD instance;

	private void $init$() {
		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public static OCAPModalidadAnteriorOAD getInstance() {
		if (instance == null) {
			instance = new OCAPModalidadAnteriorOAD();
		}
		return instance;
	}

	private OCAPModalidadAnteriorOAD() {
		$init$();
	}

	public ArrayList listadoOCAPModAnterior() throws SQLException {
		return listadoOCAPModAnterior(1, -1);
	}

	public ArrayList listadoOCAPModAnterior(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT C_MOD_ANTERIOR_ID, D_NOMBRE, D_DESCRIPCION, C_GRADO_ID, B_BORRADO, A_MODALIDAD, F_USUALTA, C_USUALTA, F_USUMODI, C_USUMODI FROM OCAP_MOD_ANTERIOR  order by D_DESCRIPCION ";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPModalidadAnteriorOT datos = new OCAPModalidadAnteriorOT();
				datos.setCModAnteriorId(rs.getLong("C_MOD_ANTERIOR_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setAModalidad(rs.getString("A_MODALIDAD"));
				datos.setFUsuAlta(rs.getDate("F_USUALTA"));
				datos.setCUsuAlta(rs.getString("C_USUALTA"));
				datos.setFUsuModi(rs.getDate("F_USUMODI"));
				datos.setCUsuModi(rs.getString("C_USUMODI"));

				listado.add(datos);

				if (++i == cuantos)
					break;
			}

		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " listadoOCAPModAnterior: ERROR " + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return listado;
	}

	public OCAPModalidadAnteriorOT buscarOCAPModalidad(long cModAnteriorId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPModalidadAnteriorOT datos = null;
		try {
			this.loggerBD.info(" buscarOCAPModalidad: " + cModAnteriorId);

			String sql = "SELECT C_MOD_ANTERIOR_ID, D_NOMBRE, D_DESCRIPCION, C_GRADO_ID, B_BORRADO, A_MODALIDAD, F_USUALTA, C_USUALTA, F_USUMODI, C_USUMODI FROM OCAP_MOD_ANTERIOR WHERE C_MOD_ANTERIOR_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cModAnteriorId);

			rs = st.executeQuery();

			datos = new OCAPModalidadAnteriorOT();
			if (rs.next()) {
				datos.setCModAnteriorId(rs.getLong("C_MOD_ANTERIOR_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setAModalidad(rs.getString("A_MODALIDAD"));
				datos.setFUsuAlta(rs.getDate("F_USUALTA"));
				datos.setCUsuAlta(rs.getString("C_USUALTA"));
				datos.setFUsuModi(rs.getDate("F_USUMODI"));
				datos.setCUsuModi(rs.getString("C_USUMODI"));
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

	
	/**
	 * Devuelve los identificadores de las modalidades que son de grado
	 * extraordinario
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList listarModalidadAnteriorExtraordinaria() throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = " SELECT C_MOD_ANTERIOR_ID FROM OCAP_MOD_ANTERIOR "
						+" WHERE A_MODALIDAD = 'E' ";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				Integer datos = rs.getInt("C_MOD_ANTERIOR_ID");
				listado.add(datos);
			}

		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " listarModalidadAnteriorExtraordinaria: ERROR " + e);
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
