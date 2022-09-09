package es.jcyl.fqs.ocap.oad.procedimiento;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.procedimiento.OCAPProcedimientoOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPProcedimientoOAD {
	public Logger logger;
	public Logger loggerBD;
	private static OCAPProcedimientoOAD instance;

	private void $init$() {
		this.logger = OCAPConfigApp.logger;

		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public static OCAPProcedimientoOAD getInstance() {
		if (instance == null) {
			instance = new OCAPProcedimientoOAD();
		}
		return instance;
	}

	private OCAPProcedimientoOAD() {
		$init$();
	}

	public ArrayList listadoOCAPProcedimiento() throws Exception {
		return listadoOCAPProcedimiento(1, -1);
	}

	public ArrayList listadoOCAPProcedimiento(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT C_PROC_EVALUACION_ID, D_NOMBRE, D_DESCRIPCION, F_USUMODI, F_USUALTA, B_BORRADO FROM OCAP_PROC_EVALUACION WHERE B_BORRADO = 'N' order by C_PROC_EVALUACION_ID";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPProcedimientoOT datos = new OCAPProcedimientoOT();
				datos.setCProcedimientoId(new Long(rs.getLong("C_PROC_EVALUACION_ID")));
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

	public OCAPProcedimientoOT buscarOCAPProcedimiento(long cProcedimientoId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPProcedimientoOT datos = null;
		try {
			this.loggerBD.info("buscarOCAPProcedimiento: " + cProcedimientoId);

			String sql = "SELECT C_PROC_EVALUACION_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION FROM OCAP_PROC_EVALUACION WHERE C_PROC_EVALUACION_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cProcedimientoId);

			rs = st.executeQuery();

			datos = new OCAPProcedimientoOT();
			if (rs.next()) {
				datos.setCProcedimientoId(new Long(rs.getLong("C_PROC_EVALUACION_ID")));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
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

	public int altaOCAPProcedimiento(OCAPProcedimientoOT datos) throws Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_PROC_EVALUACION (C_PROC_EVALUACION_ID, B_BORRADO, D_NOMBRE, F_USUALTA, D_DESCRIPCION, C_USUALTA) VALUES (OCAP_SIT_ID_SEQ.nextval, 'N', ?, SYSDATE, ?, ?)";

			OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
			st = con.prepareStatement(sql);
			st.setString(1, datos.getDNombre());

			if (datos.getDDescripcion() != null)
				st.setString(2, datos.getDDescripcion());
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

	public int bajaOCAPProcedimiento(int cProcedimientoId) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_PROC_EVALUACION SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_PROC_EVALUACION_ID =  ?";

			st = con.prepareStatement(sql);
			st.setInt(1, cProcedimientoId);

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

	public int modificacionOCAPProcedimiento(OCAPProcedimientoOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_PROC_EVALUACION SET D_NOMBRE = ?, F_USUMODI = SYSDATE, D_DESCRIPCION = ? WHERE C_PROC_EVALUACION_ID = ?";

			st = con.prepareStatement(sql);

			st.setString(1, datos.getDNombre());

			if (datos.getDDescripcion() != null)
				st.setString(2, datos.getDDescripcion());
			else {
				st.setNull(2, 12);
			}
			st.setLong(3, datos.getCProcedimientoId().longValue());

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
}
