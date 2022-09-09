package es.jcyl.fqs.ocap.oad.servicios;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.servicios.OCAPServiciosOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPServiciosOAD {
	public Logger logger;
	private static OCAPServiciosOAD instance;

	private void $init$() {
		this.logger = OCAPConfigApp.logger;
	}

	public static OCAPServiciosOAD getInstance() {
		if (instance == null) {
			instance = new OCAPServiciosOAD();
		}
		return instance;
	}

	private OCAPServiciosOAD() {
		$init$();
	}

	public int altaOCAPServicios(OCAPServiciosOT datos) throws Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_SERVICIOS (C_SERVICIO_ID, B_BORRADO, D_NOMBRE_CORTO, F_USUALTA, D_NOMBRE_LARGO,D_OBSERVACIONES,C_PROFESIONAL_ID,C_ESPEC_ID,C_ITINERARIO_ID,C_USUALTA) VALUES (OCAP_SER_ID_SEQ.nextval, 'N', ?, SYSDATE, ?, ?, ?, ?, ?, ?)";

			OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
			st = con.prepareStatement(sql);
			st.setString(1, datos.getDNombreCorto());
			st.setString(2, datos.getDNombreLargo());

			if (datos.getDObservaciones() != null)
				st.setString(3, datos.getDObservaciones());
			else {
				st.setNull(3, 12);
			}
			st.setLong(4, datos.getCProfesionalId());

			if (datos.getCEspecId() != 0L)
				st.setLong(5, datos.getCEspecId());
			else {
				st.setNull(5, 4);
			}
			st.setLong(6, datos.getCItinerarioId());
			st.setString(7, datos.getACreadoPor());

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

	public int bajaOCAPServicios(int cServicioId, String usuario) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_SERVICIOS SET B_BORRADO = 'Y', F_USUMODI = SYSDATE, C_USUMODI = ? WHERE C_SERVICIO_ID =  ?";

			st = con.prepareStatement(sql);
			st.setString(1, usuario);
			st.setInt(2, cServicioId);

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

	public int modificacionOCAPServicios(OCAPServiciosOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_SERVICIOS SET F_USUMODI = SYSDATE, C_USUMODI = ?, D_OBSERVACIONES = ? WHERE C_SERVICIO_ID = ?";

			st = con.prepareStatement(sql);

			st.setString(1, datos.getAModificadoPor());

			if (datos.getDObservaciones() != null)
				st.setString(2, datos.getDObservaciones());
			else {
				st.setNull(2, 12);
			}
			st.setLong(3, datos.getCServicioId());

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

	public OCAPServiciosOT buscarOCAPServicios(long cServicioId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPServiciosOT datos = null;
		try {
			String sql = "SELECT C_SERVICIO_ID, B_BORRADO, D_NOMBRE_CORTO, F_USUMODI, F_USUALTA, D_NOMBRE_LARGO, D_OBSERVACIONES, C_PROFESIONAL_ID, C_ESPEC_ID, C_ITINERARIO_ID FROM OCAP_SERVICIOS WHERE C_SERVICIO_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cServicioId);

			rs = st.executeQuery();

			datos = new OCAPServiciosOT();
			if (rs.next()) {
				datos.setCServicioId(rs.getLong("C_SERVICIO_ID"));
				datos.setDNombreLargo(rs.getString("D_NOMBRE_LARGO"));
				datos.setDNombreCorto(rs.getString("D_NOMBRE_CORTO"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
				datos.setCEspecId(rs.getLong("C_ESPEC_ID"));
				datos.setCItinerarioId(rs.getLong("C_ITINERARIO_ID"));
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

	public ArrayList listadoOCAPServicios() throws Exception {
		return listadoOCAPServicios(1, -1);
	}

	public ArrayList listadoOCAPServicios(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT C_SERVICIO_ID, B_BORRADO, D_NOMBRE_CORTO, F_USUMODI, F_USUALTA, D_NOMBRE_LARGO, D_OBSERVACIONES, C_PROFESIONAL_ID, C_ESPEC_ID, C_ITINERARIO_ID FROM OCAP_SERVICIOS WHERE B_BORRADO = 'N' order by C_SERVICIO_ID";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPServiciosOT datos = new OCAPServiciosOT();
				datos.setCServicioId(rs.getLong("C_SERVICIO_ID"));
				datos.setDNombreLargo(rs.getString("D_NOMBRE_LARGO"));
				datos.setDNombreCorto(rs.getString("D_NOMBRE_CORTO"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setCServicioId(rs.getLong("C_PROFESIONAL_ID"));
				datos.setCServicioId(rs.getLong("C_ESPEC_ID"));
				datos.setCServicioId(rs.getLong("C_ITINERARIO_ID"));

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

	public int listadoOCAPServiciosCuenta(long cServicioId, String dNombreCorto, String dNombreLargo,
			String dObservaciones, long cProfesionalId, long cEspecId, long cItinerarioId, String fCreacion,
			String fModificacion) throws Exception {
		ResultSet rs = null;
		Statement st = null;
		String where = "";
		StringBuffer sbWhere = new StringBuffer(40);
		Connection con = JCYLGestionTransacciones.getConnection();
		String sql = "SELECT COUNT(*) FROM OCAP_SERVICIOS ";

		boolean isCServicioId = cServicioId != 0L;
		if (isCServicioId)
			sbWhere.append("C_SERVICIO_ID = ").append(cServicioId).append(" AND ");
		boolean isCProfesionalId = cProfesionalId != 0L;
		if (isCProfesionalId)
			sbWhere.append("C_PROFESIONAL_ID = ").append(cProfesionalId).append(" AND ");
		boolean isCEspecId = cEspecId != 0L;
		if (isCEspecId)
			sbWhere.append("C_ESPEC_ID = ").append(cEspecId).append(" AND ");
		boolean isCItinerarioId = cItinerarioId != 0L;
		if (isCItinerarioId)
			sbWhere.append("C_ITINERARIO_ID = ").append(cItinerarioId).append(" AND ");
		boolean isDNombreCorto = (dNombreCorto != null) && (!dNombreCorto.equals(""));
		if (isDNombreCorto)
			sbWhere.append("(upper(D_NOMBRE_CORTO) like upper('%").append(dNombreCorto).append("%')) AND ");
		boolean isDNombreLargo = (dNombreLargo != null) && (!dNombreLargo.equals(""));
		if (isDNombreLargo)
			sbWhere.append("(upper(D_NOMBRE_LARGO) like upper('%").append(dNombreLargo).append("%')) AND ");
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

	public ArrayList consultaOCAPServicios(long cServicioId, String dNombreCorto, String dNombreLargo,
			String dObservaciones, String fCreacion, String fModificacion, int inicio, int cuantos, long cCategoriaId,
			long cEspecId, long cItinerarioId) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT C_SERVICIO_ID, B_BORRADO, D_NOMBRE_CORTO, F_USUMODI, F_USUALTA, D_NOMBRE_LARGO, D_OBSERVACIONES, C_PROFESIONAL_ID, C_ESPEC_ID, C_ITINERARIO_ID FROM OCAP_SERVICIOS";

			String orderBy = " order by C_SERVICIO_ID";
			boolean isCServicioId = cServicioId != 0L;
			if (isCServicioId)
				sbWhere.append("C_SERVICIO_ID = ?  AND ");
			boolean isDNombreCorto = (dNombreCorto != null) && (!dNombreCorto.equals(""));
			if (isDNombreCorto)
				sbWhere.append("(upper(D_NOMBRE_CORTO) like upper('%").append(dNombreCorto).append("%')) AND ");
			boolean isDNombreLargo = (dNombreLargo != null) && (!dNombreLargo.equals(""));
			if (isDNombreLargo)
				sbWhere.append("(upper(D_NOMBRE_LARGO) like upper('%").append(dNombreLargo).append("%')) AND ");
			boolean isDObservaciones = (dObservaciones != null) && (!dObservaciones.equals(""));
			if (isDObservaciones)
				sbWhere.append("(upper(D_OBSERVACIONES) like upper('%").append(dObservaciones).append("%')) AND ");
			boolean isFCreacion = (fCreacion != null) && (!fCreacion.equals(""));
			if (isFCreacion)
				sbWhere.append("to_char(F_USUALTA,'DD/MM/RRRR') = '").append(fCreacion).append("' AND ");
			boolean isFModificacion = (fModificacion != null) && (!fModificacion.equals(""));
			if (isFModificacion)
				sbWhere.append("to_char(F_USUMODI,'DD/MM/RRRR') = '").append(fModificacion).append("' AND ");
			boolean isCCategoriaId = cCategoriaId != 0L;
			if (isCCategoriaId)
				sbWhere.append("C_PROFESIONAL_ID = ?  AND ");
			boolean isCEspecId = cEspecId != 0L;
			if (isCEspecId)
				sbWhere.append("C_ESPEC_ID = ?  AND ");
			boolean isCItinerarioId = cItinerarioId != 0L;
			if (isCItinerarioId)
				sbWhere.append("C_ITINERARIO_ID = ?  AND ");
			sbWhere.append("B_BORRADO = 'N'  AND ");
			int len = sbWhere.length();
			if (len > 0) {
				where = sbWhere.insert(0, " Where ").substring(0, len + 1);
			}
			String sqlStatement = selectFrom + where + orderBy;
			OCAPConfigApp.logger.info("Sentencia SQL:" + sqlStatement);
			st = con.prepareStatement(sqlStatement, 1004, 1008);

			int index = 1;
			OCAPServiciosOT datos = new OCAPServiciosOT();

			if (isCServicioId) {
				st.setLong(index++, cServicioId);
			}
			if (isCCategoriaId) {
				st.setLong(index++, cCategoriaId);
			}
			if (isCEspecId) {
				st.setLong(index++, cEspecId);
			}
			if (isCItinerarioId) {
				st.setLong(index++, cItinerarioId);
			}
			rs = st.executeQuery();
			if (inicio > 1)
				rs.absolute(inicio - 1);
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				datos = new OCAPServiciosOT();
				datos.setCServicioId(rs.getLong("C_SERVICIO_ID"));
				datos.setDNombreCorto(rs.getString("D_NOMBRE_CORTO"));
				datos.setDNombreLargo(rs.getString("D_NOMBRE_LARGO"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
				datos.setCEspecId(rs.getLong("C_ESPEC_ID"));
				datos.setCItinerarioId(rs.getLong("C_ITINERARIO_ID"));
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

	public ArrayList consultaOCAPAsociacionesServicios(long cCategoriaId, long cEspecId) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT ser.C_SERVICIO_ID IDSERVICIO, ser.D_NOMBRE_LARGO SERVICIO FROM OCAP_SERVICIOS ser ";

			String orderBy = " order by SERVICIO asc";

			sbWhere.append("WHERE ser.B_BORRADO = 'N' ");
			boolean isCCategoriaId = cCategoriaId != 0L;
			if (isCCategoriaId)
				sbWhere.append(" AND ser.C_PROFESIONAL_ID = ? ");
			boolean isCEspecId = cEspecId != 0L;
			if (isCEspecId)
				sbWhere.append(" AND ser.C_ESPEC_ID = ? ");

			String sqlStatement = selectFrom + sbWhere + orderBy;
			OCAPConfigApp.logger.info("Sentencia SQL:" + sqlStatement);
			st = con.prepareStatement(sqlStatement, 1004, 1008);

			int index = 1;
			OCAPServiciosOT datos = new OCAPServiciosOT();

			if (isCCategoriaId)
				st.setLong(index++, cCategoriaId);
			if (isCEspecId) {
				st.setLong(index++, cEspecId);
			}

			rs = st.executeQuery();

			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				datos = new OCAPServiciosOT();
				datos.setDNombreLargo(rs.getString("SERVICIO"));
				datos.setCServicioId(rs.getLong("IDSERVICIO"));

				listado.add(datos);
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
