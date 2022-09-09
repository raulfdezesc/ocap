package es.jcyl.fqs.ocap.oad.sit_Administrativa;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.sit_Administrativa.OCAPSit_AdministrativaOT;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPSit_AdministrativaOAD {
	public Logger logger;
	private static OCAPSit_AdministrativaOAD instance;

	private void $init$() {
		this.logger = OCAPConfigApp.logger;
	}

	public static OCAPSit_AdministrativaOAD getInstance() {
		if (instance == null) {
			instance = new OCAPSit_AdministrativaOAD();
		}
		return instance;
	}

	private OCAPSit_AdministrativaOAD() {
		$init$();
	}

	public ArrayList listadoOCAPSit_Administrativa() throws Exception {
		return listadoOCAPSit_Administrativa(1, -1);
	}

	public ArrayList listadoOCAPSit_Administrativa(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT B_BORRADO, C_SIT_ADMINISTRATIVA_ID, C_USUALTA, C_USUMODI, D_DESCRIPCION, D_NOMBRE, F_USUALTA, F_USUMODI FROM OCAP_SIT_ADMINISTRATIVA WHERE B_BORRADO = 'N' order by C_SIT_ADMINISTRATIVA_ID";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPSit_AdministrativaOT datos = new OCAPSit_AdministrativaOT();
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setCSit_AdministrativaId(new Long(rs.getLong("C_SIT_ADMINISTRATIVA_ID")));
				datos.setCUsuAlta(rs.getString("C_USUALTA"));
				datos.setCUsuModi(rs.getString("C_USUMODI"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setFUsuAlta(rs.getDate("F_USUALTA"));
				datos.setFUsuModi(rs.getDate("F_USUMODI"));

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
