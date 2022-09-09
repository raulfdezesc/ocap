package es.jcyl.fqs.ocap.oad.puestos;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.puestos.OCAPPuestosOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPPuestosOAD {
	public static Logger logger = OCAPConfigApp.logger;
	private static OCAPPuestosOAD instance;

	public static OCAPPuestosOAD getInstance() {
		if (instance == null) {
			instance = new OCAPPuestosOAD();
		}
		return instance;
	}

	public int altaOCAPPuestos(OCAPPuestosOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_PUESTOS (C_PUESTO_ID, C_PROFESIONAL_ID, B_BORRADO, D_NOMBRE, F_USUALTA, D_OBSERVACIONES, C_USUALTA, C_ITINERARIO_ID,A_NOMBRE_CORTO) VALUES (OCAP_PUE_ID_SEQ.nextval, ?,'N', ?, SYSDATE, ?, ?, ?, ?)";

			OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
			st = con.prepareStatement(sql);
			st.setLong(1, datos.getCProfesionalId());
			st.setString(2, datos.getDNombre());

			if (datos.getDObservaciones() != null)
				st.setString(3, datos.getDObservaciones());
			else {
				st.setNull(3, 12);
			}
			st.setString(4, datos.getACreadoPor());
			st.setLong(5, datos.getCItinerarioId());
			st.setString(6, datos.getANombreCorto());

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

	public int bajaOCAPPuestos(int cPuestoId, String usuario) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_PUESTOS SET B_BORRADO = 'Y',  F_USUMODI = SYSDATE, C_USUMODI = ? WHERE C_PUESTO_ID =  ?";

			st = con.prepareStatement(sql);
			st.setString(1, usuario);
			st.setInt(2, cPuestoId);

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

	public int modificacionOCAPPuestos(OCAPPuestosOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_PUESTOS SET C_ITINERARIO_ID = ?, F_USUMODI = SYSDATE, D_OBSERVACIONES = ?, C_USUMODI = ? WHERE C_PUESTO_ID = ?";

			st = con.prepareStatement(sql);

			st.setLong(1, datos.getCItinerarioId());

			if (datos.getDObservaciones() != null)
				st.setString(2, datos.getDObservaciones());
			else {
				st.setNull(2, 12);
			}
			st.setString(3, datos.getAModificadoPor());
			st.setLong(4, datos.getCPuestoId());

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

	public OCAPPuestosOT buscarOCAPPuestos(long cPuestoId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPPuestosOT datos = null;
		try {
			String sql = "SELECT C_PUESTO_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_OBSERVACIONES, C_PROFESIONAL_ID, C_ITINERARIO_ID, A_NOMBRE_CORTO FROM OCAP_PUESTOS WHERE C_PUESTO_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cPuestoId);

			rs = st.executeQuery();

			datos = new OCAPPuestosOT();
			if (rs.next()) {
				datos.setCPuestoId(rs.getLong("C_PUESTO_ID"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setCItinerarioId(rs.getLong("C_ITINERARIO_ID"));
				datos.setANombreCorto(rs.getString("A_NOMBRE_CORTO"));
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

	public int listadoOCAPPuestosCuenta(long cPuestoId, long cProfesionalId, String dNombre, String dObservaciones,
			long cItinerarioId, String aNombreCorto, String fCreacion, String fModificacion) throws Exception {
		ResultSet rs = null;
		PreparedStatement st = null;

		String where = "";
		StringBuffer sbWhere = new StringBuffer(40);
		Connection con = JCYLGestionTransacciones.getConnection();
		String sql = "SELECT COUNT(*) FROM OCAP_PUESTOS ";

		boolean isCPuestoId = cPuestoId != 0L;
		if (isCPuestoId)
			sbWhere.append("C_PUESTO_ID = ").append(cPuestoId).append(" AND ");
		boolean isCProfesionalId = cProfesionalId != 0L;
		if (isCProfesionalId)
			sbWhere.append("C_PROFESIONAL_ID = ").append(cProfesionalId).append(" AND ");
		boolean isCItinerarioId = cItinerarioId != 0L;
		if (isCItinerarioId)
			sbWhere.append("C_ITINERARIO_ID = ").append(cItinerarioId).append(" AND ");
		boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
		if (isDNombre)
			sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
		boolean isANombreCorto = (aNombreCorto != null) && (!aNombreCorto.equals(""));
		if (isANombreCorto)
			sbWhere.append("(upper(A_NOMBRE_CORTO) like upper('%").append(aNombreCorto).append("%')) AND ");
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

	public ArrayList consultaOCAPPuestos(long cPuestoId, long cProfesionalId, String dNombre, String dObservaciones,
			long cItinerarioId, String aNombreCorto, String fCreacion, String fModificacion, int inicio, int cuantos)
			throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT C_PUESTO_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_OBSERVACIONES, C_PROFESIONAL_ID, C_ITINERARIO_ID, A_NOMBRE_CORTO FROM OCAP_PUESTOS";

			String orderBy = " order by C_PUESTO_ID";
			boolean isCPuestoId = cPuestoId != 0L;
			if (isCPuestoId)
				sbWhere.append("C_PUESTO_ID = ?  AND ");
			boolean isCProfesionalId = cProfesionalId != 0L;
			if (isCProfesionalId)
				sbWhere.append("C_PROFESIONAL_ID = ?  AND ");
			boolean isCItinerarioId = cItinerarioId != 0L;
			if (isCItinerarioId)
				sbWhere.append("C_ITINERARIO_ID = ? AND ");
			boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
			if (isDNombre)
				sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
			boolean isANombreCorto = (aNombreCorto != null) && (!aNombreCorto.equals(""));
			if (isANombreCorto)
				sbWhere.append("(upper(A_NOMBRE_CORTO) like upper('%").append(aNombreCorto).append("%')) AND ");
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
			String sqlStatement = selectFrom + where + orderBy;
			OCAPConfigApp.logger.info("Sentencia SQL:" + sqlStatement);
			st = con.prepareStatement(sqlStatement, 1004, 1008);

			int index = 1;
			OCAPPuestosOT datos = new OCAPPuestosOT();

			if (isCPuestoId) {
				st.setLong(index++, cPuestoId);
			}
			if (isCProfesionalId) {
				st.setLong(index++, cProfesionalId);
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
				datos = new OCAPPuestosOT();
				datos.setCPuestoId(rs.getLong("C_PUESTO_ID"));
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
				datos.setCItinerarioId(rs.getLong("C_ITINERARIO_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setANombreCorto(rs.getString("A_NOMBRE_CORTO"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
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
}
