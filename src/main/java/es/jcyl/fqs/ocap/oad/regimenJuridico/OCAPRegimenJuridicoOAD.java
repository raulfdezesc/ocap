package es.jcyl.fqs.ocap.oad.regimenJuridico;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.regimenJuridico.OCAPRegimenJuridicoOT;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPRegimenJuridicoOAD {
	public Logger logger;
	private static OCAPRegimenJuridicoOAD instance;

	private void $init$() {
		this.logger = OCAPConfigApp.logger;
	}

	public static OCAPRegimenJuridicoOAD getInstance() {
		if (instance == null) {
			instance = new OCAPRegimenJuridicoOAD();
		}
		return instance;
	}

	private OCAPRegimenJuridicoOAD() {
		$init$();
	}

	public ArrayList listadoOCAPRegimenJuridico() throws Exception {
		return listadoOCAPRegimenJuridico(1, -1);
	}

	public ArrayList listadoOCAPRegimenJuridico(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT B_BORRADO, C_JURIDICO_ID, C_USUALTA, C_USUMODI, D_DESCRIPCION, D_NOMBRE, F_USUALTA, F_USUMODI FROM OCAP_REG_JURIDICO WHERE B_BORRADO = 'N' order by C_JURIDICO_ID";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPRegimenJuridicoOT datos = new OCAPRegimenJuridicoOT();
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setCJuridicoId(new Long(rs.getLong("C_JURIDICO_ID")));
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
