package es.jcyl.fqs.ocap.oad.grado;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.framework.JCYLUsuario;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPGradoOAD {
	public Logger loggerBD;
	private static OCAPGradoOAD instance;

	private void $init$() {
		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public static OCAPGradoOAD getInstance() {
		if (instance == null) {
			instance = new OCAPGradoOAD();
		}
		return instance;
	}

	private OCAPGradoOAD() {
		$init$();
	}

	public int altaOCAPGrado(OCAPGradoOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_GRADOS (C_GRADO_ID, B_BORRADO, D_NOMBRE, F_USUALTA, D_DESCRIPCION, N_ANIOSEJERCICIO, C_USUALTA) VALUES (OCAP_GRA_ID_SEQ.nextval, 'N', ?, SYSDATE, ?, ?)";

			this.loggerBD.info("Sentencia SQL: " + sql);
			st = con.prepareStatement(sql);
			st.setString(1, datos.getDNombre());
			st.setString(2, datos.getDDescripcion());
			st.setInt(3, datos.getNAniosejercicio().intValue());
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

	public int bajaOCAPGrado(int cGradoId) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			String sql = "UPDATE OCAP_GRADOS SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_GRADO_ID = ?";

			st = con.prepareStatement(sql);
			st.setInt(1, cGradoId);
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

	public int modificacionOCAPGrado(OCAPGradoOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			String sql = "UPDATE OCAP_GRADOS SET D_NOMBRE = ?, F_USUMODI = SYSDATE, D_DESCRIPCION = ?, N_ANIOSEJERCICIO = ? WHERE C_GRADO_ID = ?";

			st = con.prepareStatement(sql);
			st.setString(1, datos.getDNombre());
			st.setString(2, datos.getDDescripcion());
			st.setInt(3, datos.getNAniosejercicio().intValue());
			st.setLong(4, datos.getCGradoId());
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

	public OCAPGradoOT buscarOCAPGrado(long cGradoId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		OCAPGradoOT datos = null;
		try {
			this.loggerBD.info("buscarOCAPGrado: " + cGradoId);

			String sql = "SELECT C_GRADO_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION, N_ANIOSEJERCICIO FROM OCAP_GRADOS WHERE C_GRADO_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cGradoId);
			rs = st.executeQuery();
			datos = new OCAPGradoOT();
			if (rs.next()) {
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setNAniosejercicio(new Integer(rs.getInt("N_ANIOSEJERCICIO")));
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

	public ArrayList listadoOCAPGrado() throws SQLException, Exception {
		return listadoOCAPGrado(1, -1);
	}

	public ArrayList listadoOCAPGrado(int inicio, int cuantos) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String sql = "SELECT C_GRADO_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION, N_ANIOSEJERCICIO FROM OCAP_GRADOS WHERE B_BORRADO = 'N' order by C_GRADO_ID";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1)
				rs.absolute(inicio - 1);
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPGradoOT datos = new OCAPGradoOT();
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setNAniosejercicio(new Integer(rs.getInt("N_ANIOSEJERCICIO")));
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

	public ArrayList listadoOCAPGradoConvocatoriasAbiertas(String cDni, JCYLUsuario jcylUsuario)
			throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		StringBuffer consulta = new StringBuffer();
		ArrayList listado = null;
		try {
			String sql = "SELECT og.C_GRADO_ID, og.D_NOMBRE, og.D_DESCRIPCION, og.N_ANIOSEJERCICIO, TO_CHAR(oc.F_PUBLICACION+(oc.N_DIAS_REGSOLICITUD),'DD/MM/RRRR') F_FIN_REGISTRO, oc.C_CONVOCATORIA_ID  FROM OCAP_GRADOS og, OCAP_CONVOCATORIAS oc  where og.C_GRADO_ID = oc.C_GRADO_ID  AND SYSDATE >= oc.f_publicacion  AND sysdate <= ";

			if ((jcylUsuario != null) && (jcylUsuario.getUser() != null) && (jcylUsuario.getUser().getRol() != null)
					&& (Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())))
				sql = sql + " oc.F_PUBLICACION + (oc.N_DIAS_REGSOLICITUD + oc.N_DIAS_REVSOLICITUD) ";
			else
				sql = sql + " oc.F_PUBLICACION + (oc.N_DIAS_REGSOLICITUD) ";
			sql = sql + " AND og.B_BORRADO = 'N'  AND oc.B_BORRADO = 'N' ";

			String sqlDni = "AND oc.c_convocatoria_id not in     (select oe.c_convocatoria_id from ocap_expedientescarrera oe, ocap_usuarios ou        where oe.c_usr_id= ou.c_usr_id AND LPAD(ou.C_DNI,10,'0') = LPAD('"
					+ cDni + "',10,'0') )";
			String aux = "";
			if (!cDni.equals(""))
				consulta.append(sqlDni);
			String orderBy = " order by C_GRADO_ID";
			int len = consulta.length();
			if (len > 0) {
				aux = consulta.toString();
			}

			String sqlStatement = sql + aux + orderBy;
			st = con.prepareStatement(sqlStatement, 1004, 1008);
			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				OCAPGradoOT datos = new OCAPGradoOT();
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setNAniosejercicio(new Integer(rs.getInt("N_ANIOSEJERCICIO")));
				datos.setCConvocatoriaId(new Long(rs.getLong("C_CONVOCATORIA_ID")));
				datos.setFFinRegistro(rs.getString("F_FIN_REGISTRO"));
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

	public ArrayList listadoOCAPGradoConvocatoriasModif(String cDni, long cExp) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String sql = "SELECT og.C_GRADO_ID, og.D_NOMBRE, og.D_DESCRIPCION, og.N_ANIOSEJERCICIO, TO_CHAR(oc.F_PUBLICACION+(oc.N_DIAS_REGSOLICITUD),'DD/MM/RRRR') F_FIN_REGISTRO, oc.C_CONVOCATORIA_ID  FROM OCAP_GRADOS og, OCAP_CONVOCATORIAS oc  where og.C_GRADO_ID = oc.C_GRADO_ID  AND SYSDATE >= oc.f_publicacion  AND og.B_BORRADO = 'N'  AND oc.B_BORRADO = 'N'  AND ((sysdate <=  oc.F_PUBLICACION + (oc.N_DIAS_REGSOLICITUD)  AND oc.c_convocatoria_id not in  (select oe.c_convocatoria_id from ocap_expedientescarrera oe, ocap_usuarios ou  where oe.c_usr_id= ou.c_usr_id AND LPAD(ou.C_DNI,10,'0') = LPAD('"
					+ cDni + "',10,'0') AND oe.c_exp_id != " + cExp + "))" + " OR oc.c_convocatoria_id = "
					+ " (select oe.c_convocatoria_id from ocap_expedientescarrera oe, ocap_usuarios ou "
					+ " where oe.c_usr_id= ou.c_usr_id AND LPAD(ou.C_DNI,10,'0') = LPAD('" + cDni
					+ "',10,'0') AND oe.c_exp_id = " + cExp + "))" + " order by C_GRADO_ID";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				OCAPGradoOT datos = new OCAPGradoOT();
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setNAniosejercicio(new Integer(rs.getInt("N_ANIOSEJERCICIO")));
				datos.setCConvocatoriaId(new Long(rs.getLong("C_CONVOCATORIA_ID")));
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

	public int listadoOCAPGradoCuenta(long cGradoId, String dNombre, String dDescripcion, String fCreacion,
			String fModificacion, String nAniosejercicio) throws SQLException, Exception {
		ResultSet rs = null;
		Statement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int total = -1;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String sql = "SELECT COUNT(*) FROM OCAP_GRADOS ";
			boolean isCGradoId = cGradoId != 0L;
			if (isCGradoId)
				sbWhere.append("C_GRADO_ID = ").append(cGradoId).append(" AND ");
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
			if (isFModificacion)
				sbWhere.append("to_char(F_USUMODI,'DD/MM/RRRR') = '").append(fModificacion).append("' AND ");
			boolean isNAniosejercicio = nAniosejercicio != null;
			if (isNAniosejercicio) {
				sbWhere.append("N_ANIOSEJERCICIO = ").append(nAniosejercicio).append(" AND ");
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

	public ArrayList consultaOCAPGrado(long cGradoId, String dNombre, String dDescripcion, String fCreacion,
			String fModificacion, String nAniosejercicio, int inicio, int cuantos) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT C_GRADO_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION, N_ANIOSEJERCICIO FROM OCAP_GRADOS ";

			String orderBy = " order by C_GRADO_ID";
			boolean isCGradoId = cGradoId != 0L;
			if (isCGradoId)
				sbWhere.append("C_GRADO_ID = ?  AND ");
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
			if (isFModificacion)
				sbWhere.append("to_char(F_USUMODI,'DD/MM/RRRR') = '").append(fModificacion).append("' AND ");
			boolean isNAniosejercicio = nAniosejercicio != null;
			if (isNAniosejercicio)
				sbWhere.append("N_ANIOSEJERCICIO = ?  AND ");
			sbWhere.append("B_BORRADO = 'N'  AND ");
			int len = sbWhere.length();
			if (len > 0) {
				where = sbWhere.insert(0, " Where ").substring(0, len + 1);
			}
			String sqlStatement = selectFrom + where + orderBy;
			this.loggerBD.info("Sentencia SQL:" + sqlStatement);
			st = con.prepareStatement(sqlStatement, 1004, 1008);

			int index = 1;
			OCAPGradoOT datos = new OCAPGradoOT();
			if (isCGradoId)
				st.setLong(index++, cGradoId);
			if (isNAniosejercicio)
				st.setInt(index++, Integer.parseInt(nAniosejercicio));
			rs = st.executeQuery();
			if (inicio > 1)
				rs.absolute(inicio - 1);
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				datos = new OCAPGradoOT();
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setNAniosejercicio(new Integer(rs.getInt("N_ANIOSEJERCICIO")));
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
