package es.jcyl.fqs.ocap.oad.modalidades;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.modalidades.OCAPModalidadesOT;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPModalidadesOAD {
	public Logger logger;
	private static OCAPModalidadesOAD instance;

	private void $init$() {
		this.logger = OCAPConfigApp.logger;
	}

	public static OCAPModalidadesOAD getInstance() {
		if (instance == null) {
			instance = new OCAPModalidadesOAD();
		}
		return instance;
	}

	private OCAPModalidadesOAD() {
		$init$();
	}

	public ArrayList listarModalidades() throws Exception {
		return listarModalidades(1, -1);
	}

	public ArrayList listarModalidades(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		StringBuffer sql = new StringBuffer();
		ArrayList listado = null;
		try {
			sql.append("SELECT C_MODALIDAD_ID, D_NOMBRE, D_DESCRIPCION ").append("FROM OCAP_MODALIDADES ")
					.append("WHERE B_BORRADO = 'N' ").append("order by C_MODALIDAD_ID");

			st = con.prepareStatement(sql.toString(), 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPModalidadesOT datos = new OCAPModalidadesOT();
				datos.setCModalidadId(rs.getLong("C_MODALIDAD_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDNombre(rs.getString("D_NOMBRE"));

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

	public OCAPModalidadesOT buscarModalidad(long cModalidadId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPModalidadesOT datos = null;
		try {
			String sql = "SELECT D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION FROM OCAP_MODALIDADES WHERE C_MODALIDAD_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cModalidadId);

			rs = st.executeQuery();

			datos = new OCAPModalidadesOT();
			if (rs.next()) {
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
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
}
