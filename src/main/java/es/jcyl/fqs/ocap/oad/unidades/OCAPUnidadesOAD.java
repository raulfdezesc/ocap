package es.jcyl.fqs.ocap.oad.unidades;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.unidades.OCAPUnidadesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPUnidadesOAD {
	public Logger logger;
	private static OCAPUnidadesOAD instance;

	private void $init$() {
		this.logger = OCAPConfigApp.logger;
	}

	public static OCAPUnidadesOAD getInstance() {
		if (instance == null) {
			instance = new OCAPUnidadesOAD();
		}
		return instance;
	}

	private OCAPUnidadesOAD() {
		$init$();
	}

	public int altaOCAPUnidades(OCAPUnidadesOT datos) throws Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_UNIDADES (C_UNIDAD_ID, C_AREA_ID, B_BORRADO, D_NOMBRE, F_USUALTA, D_DESCRIPCION,D_OBSERVACIONES,C_PROFESIONAL_ID,C_USUALTA, C_ITINERARIO_ID,A_NOMBRECORTO) VALUES (OCAP_UNI_ID_SEQ.nextval, ?, 'N', ?, SYSDATE, ?, ?, ?, ?, ?, ?)";

			OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
			st = con.prepareStatement(sql);
			st.setLong(1, datos.getCAreaId());
			st.setString(2, datos.getDNombre());

			if (datos.getDDescripcion() != null)
				st.setString(3, datos.getDDescripcion());
			else {
				st.setNull(3, 12);
			}
			if (datos.getDObservaciones() != null)
				st.setString(4, datos.getDObservaciones());
			else {
				st.setNull(4, 12);
			}
			st.setLong(5, datos.getCProfesionalId());
			st.setString(6, datos.getACreadoPor());
			st.setLong(7, datos.getCItinerarioId());
			st.setString(8, datos.getANombreCorto());

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

	public int bajaOCAPUnidades(long cUnidadId, String usuario) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_UNIDADES SET B_BORRADO = 'Y', F_USUMODI = SYSDATE, C_USUMODI = ? WHERE C_UNIDAD_ID =  ?";

			st = con.prepareStatement(sql);
			st.setString(1, usuario);
			st.setLong(2, cUnidadId);

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

	public int modificacionOCAPUnidades(OCAPUnidadesOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_UNIDADES SET C_ITINERARIO_ID = ?, F_USUMODI = SYSDATE, D_DESCRIPCION = ?, D_OBSERVACIONES = ?, C_USUMODI = ?, C_AREA_ID = ? WHERE C_UNIDAD_ID = ?";

			st = con.prepareStatement(sql);

			st.setLong(1, datos.getCItinerarioId());

			if (datos.getDDescripcion() != null)
				st.setString(2, datos.getDDescripcion());
			else {
				st.setNull(2, 12);
			}
			if (datos.getDObservaciones() != null)
				st.setString(3, datos.getDObservaciones());
			else {
				st.setNull(3, 12);
			}
			st.setString(4, datos.getAModificadoPor());
			st.setLong(5, datos.getCAreaId());
			st.setLong(6, datos.getCUnidadId());

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

	public OCAPUnidadesOT buscarOCAPUnidades(long cUnidadId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPUnidadesOT datos = null;
		try {
			String sql = "SELECT C_UNIDAD_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION, D_OBSERVACIONES, C_AREA_ID, C_PROFESIONAL_ID, C_ITINERARIO_ID, A_NOMBRECORTO FROM OCAP_UNIDADES WHERE C_UNIDAD_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cUnidadId);

			rs = st.executeQuery();

			datos = new OCAPUnidadesOT();
			if (rs.next()) {
				datos.setCAreaId(rs.getLong("C_AREA_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setCUnidadId(rs.getLong("C_UNIDAD_ID"));
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
				datos.setCItinerarioId(rs.getLong("C_ITINERARIO_ID"));
				datos.setANombreCorto(rs.getString("A_NOMBRECORTO"));
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

	public ArrayList listadoOCAPUnidades() throws Exception {
		return listadoOCAPUnidades(1, -1);
	}

	public ArrayList listadoOCAPUnidades(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT C_UNIDAD_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION, D_OBSERVACIONES, C_AREA_ID, C_PROFESIONAL_ID, C_ITINERARIO_ID, A_NOMBRECORTO FROM OCAP_UNIDADES WHERE B_BORRADO = 'N' order by C_UNIDAD_ID";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPUnidadesOT datos = new OCAPUnidadesOT();
				datos.setCAreaId(rs.getLong("C_AREA_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setCUnidadId(rs.getLong("C_UNIDAD_ID"));
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
				datos.setCItinerarioId(rs.getLong("C_ITINERARIO_ID"));
				datos.setANombreCorto(rs.getString("A_NOMBRECORTO"));

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

	public int listadoOCAPUnidadesCuenta(long cUnidadId, long cAreaId, String dNombre, String dDescripcion,
			String dObservaciones, long cProfesionalId, long cItinerarioId, String aNombreCorto, String fCreacion,
			String fModificacion) throws Exception {
		ResultSet rs = null;
		PreparedStatement st = null;

		String where = "";
		StringBuffer sbWhere = new StringBuffer(40);
		Connection con = JCYLGestionTransacciones.getConnection();
		String sql = "SELECT COUNT(*) FROM OCAP_UNIDADES";

		boolean isCUnidadId = cUnidadId != 0L;
		if (isCUnidadId)
			sbWhere.append("C_UNIDAD_ID = ").append(cUnidadId).append(" AND ");
		boolean isCAreaId = cAreaId != 0L;
		if (isCAreaId)
			sbWhere.append("C_AREA_ID = ").append(cAreaId).append(" AND ");
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
			sbWhere.append("(upper(A_NOMBRECORTO) like upper('%").append(aNombreCorto).append("%')) AND ");
		boolean isDDescripcion = (dDescripcion != null) && (!dDescripcion.equals(""));
		if (isDDescripcion)
			sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dDescripcion).append("%')) AND ");
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

	public ArrayList consultaOCAPUnidades(long cUnidadId, long cAreaId, String dNombre, String dDescripcion,
			String dObservaciones, long cProfesionalId, long cItinerarioId, String aNombreCorto, String fCreacion,
			String fModificacion, int inicio, int cuantos) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT C_AREA_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION, D_OBSERVACIONES, C_UNIDAD_ID, C_PROFESIONAL_ID, C_ITINERARIO_ID, A_NOMBRECORTO FROM OCAP_UNIDADES";

			String orderBy = " order by C_UNIDAD_ID";

			boolean isCUnidadId = cUnidadId != 0L;
			if (isCUnidadId)
				sbWhere.append("C_UNIDAD_ID = ").append(cUnidadId).append(" AND ");
			boolean isCAreaId = cAreaId != 0L;
			if (isCAreaId)
				sbWhere.append("C_AREA_ID = ?  AND ");
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
				sbWhere.append("(upper(A_NOMBRECORTO) like upper('%").append(aNombreCorto).append("%')) AND ");
			boolean isDDescripcion = (dDescripcion != null) && (!dDescripcion.equals(""));
			if (isDDescripcion)
				sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dDescripcion).append("%')) AND ");
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
			OCAPUnidadesOT datos = new OCAPUnidadesOT();

			if (isCAreaId) {
				st.setLong(index++, cAreaId);
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
				datos = new OCAPUnidadesOT();
				datos.setCAreaId(rs.getLong("C_AREA_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
				datos.setCItinerarioId(rs.getLong("C_ITINERARIO_ID"));
				datos.setANombreCorto(rs.getString("A_NOMBRECORTO"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setCUnidadId(rs.getLong("C_UNIDAD_ID"));

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
