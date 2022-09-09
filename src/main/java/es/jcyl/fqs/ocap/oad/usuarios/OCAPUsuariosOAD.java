package es.jcyl.fqs.ocap.oad.usuarios;

import es.jcyl.cf.seguridad.util.Usuario;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.framework.JCYLUsuario;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;

public class OCAPUsuariosOAD {
	public static Logger loggerBD = OCAPConfigApp.loggerBD;
	private static OCAPUsuariosOAD instance;

	public static OCAPUsuariosOAD getInstance() {
		if (instance == null) {
			instance = new OCAPUsuariosOAD();
		}
		return instance;
	}

	public int altaOCAPUsuarios(OCAPUsuariosOT datos) throws SQLException {
		CallableStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		int idUsr = 0;
		try {
			String sql = "BEGIN INSERT INTO OCAP_USUARIOS (D_NOMBRE, C_USR_ID, F_USUALTA, C_GERENCIA_ID, D_APELLIDOS, C_CENTROTRABAJO_ID, A_CORREOELECTRONICO, C_DNI, C_DNI_REAL, B_BORRADO, N_TELEFONO1, N_TELEFONO2, B_SEXO, A_DOMICILIO, C_PROVINCIA_ID, D_LOCAL, N_CODIGOPOSTAL, C_USUALTA ";

			sql = sql + ") VALUES (?, OCAP_USU_ID_SEQ.NEXTVAL, SYSDATE, ?, ?, ?, "
					+ "?, LPAD(?,9,'0'), LPAD(?,9,'0'), ?, ?, ?, ?, " + "?, ?, ?, ?, ? ";

			sql = sql + ")  RETURNING C_USR_ID INTO ?; END;";

			st = con.prepareCall(sql);
			int cont = 1;
			st.setString(cont++, datos.getDNombre());

			st.setInt(cont++, datos.getCGerenciaId().intValue());
			st.setString(cont++, datos.getDApellido1());
			st.setInt(cont++, datos.getCCentrotrabajoId().intValue());
			st.setString(cont++, datos.getDCorreoelectronico());
			if (datos.getCDni() != null)
				st.setString(cont++, datos.getCDni().toUpperCase());
			else
				st.setNull(cont++, 12);
			if (datos.getCDniReal() != null)
				st.setString(cont++, datos.getCDniReal().toUpperCase());
			else {
				st.setNull(cont++, 12);
			}
			st.setString(cont++, datos.getBBorrado());
			if (datos.getNTelefono1() != null)
				st.setInt(cont++, datos.getNTelefono1().intValue());
			else
				st.setNull(cont++, 2);
			if (datos.getNTelefono2() != null)
				st.setInt(cont++, datos.getNTelefono2().intValue());
			else
				st.setNull(cont++, 2);
			st.setString(cont++, datos.getBSexo());
			st.setString(cont++, datos.getDDomicilio());
			st.setString(cont++, datos.getCProvinciaUsu_id());
			st.setString(cont++, datos.getDLocalidadUsu());
			st.setString(cont++, datos.getCPostalUsu());
			st.setString(cont++, datos.getACreadoPor());

			st.registerOutParameter(cont++, 4);

			filas = st.executeUpdate();
			idUsr = st.getInt(--cont);
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return idUsr;
	}

	public int bajaOCAPUsuarios(Long cUsrId) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "DELETE FROM OCAP_USUARIOS WHERE C_USR_ID =  ?";

			st = con.prepareStatement(sql);
			st.setLong(1, cUsrId.longValue());

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

	public int modificacionOCAPUsuarios(OCAPUsuariosOT datos, Connection con) throws SQLException {
		PreparedStatement st = null;

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_USUARIOS SET D_NOMBRE = ?, C_GERENCIA_ID = ?, D_APELLIDOS = ?, C_CENTROTRABAJO_ID = ?, ";

			sql = sql + "A_CORREOELECTRONICO = ?, ";
			if (datos.getCDni() != null)
				sql = sql + "C_DNI = LPAD(?,9,'0'), ";
			if (datos.getCDniReal() != null)
				sql = sql + "C_DNI_REAL = LPAD(?,9,'0'), ";
			sql = sql + "B_BORRADO = ?, ";
			if (datos.getBSexo() != null)
				sql = sql + "B_SEXO = ?, ";
			sql = sql
					+ "A_DOMICILIO = ?, C_PROVINCIA_ID = ?, D_LOCAL= ?, N_CODIGOPOSTAL= ?, N_TELEFONO1= ?, N_TELEFONO2= ?, F_USUMODI = SYSDATE, C_USUMODI = ? WHERE C_USR_ID = ?";

			st = con.prepareStatement(sql);
			int cont = 1;
			st.setString(cont++, datos.getDNombre());

			st.setInt(cont++, datos.getCGerenciaId().intValue());
			st.setString(cont++, datos.getDApellido1());
			st.setInt(cont++, datos.getCCentrotrabajoId().intValue());

			st.setString(cont++, datos.getDCorreoelectronico());

			if (datos.getCDni() != null) {
				st.setString(cont++, datos.getCDni());
			}
			if (datos.getCDniReal() != null) {
				st.setString(cont++, datos.getCDniReal());
			}
			st.setString(cont++, datos.getBBorrado());

			if (datos.getBSexo() != null) {
				st.setString(cont++, datos.getBSexo());
			}
			if (datos.getDDomicilio() != null)
				st.setString(cont++, datos.getDDomicilio());
			else {
				st.setNull(cont++, 12);
			}
			if (datos.getCProvinciaUsu_id() != null)
				st.setString(cont++, datos.getCProvinciaUsu_id());
			else {
				st.setNull(cont++, 12);
			}
			if (datos.getDLocalidadUsu() != null)
				st.setString(cont++, datos.getDLocalidadUsu());
			else {
				st.setNull(cont++, 12);
			}
			if (datos.getCPostalUsu() != null)
				st.setString(cont++, datos.getCPostalUsu());
			else {
				st.setNull(cont++, 2);
			}
			if (datos.getNTelefono1() != null)
				st.setInt(cont++, datos.getNTelefono1().intValue());
			else {
				st.setNull(cont++, 2);
			}
			if (datos.getNTelefono2() != null)
				st.setInt(cont++, datos.getNTelefono2().intValue());
			else {
				st.setNull(cont++, 2);
			}
			if (datos.getAModificadoPor() != null)
				st.setString(cont++, datos.getAModificadoPor());
			else {
				st.setNull(cont++, 12);
			}
			st.setLong(cont++, datos.getCUsrId().longValue());

			filas = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null) {
				st.close();
			}

		}

		return filas;
	}

	public int modificacionDatosUsuarios(OCAPUsuariosOT datos, Connection con) throws SQLException {
		PreparedStatement st = null;

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_USUARIOS SET D_NOMBRE = ?, D_APELLIDOS = ?, A_CORREOELECTRONICO = ?, N_TELEFONO1 = ?, B_SEXO = ?, C_DNI = LPAD(?,9,'0'), C_DNI_REAL = LPAD(?,9,'0'), F_USUMODI = SYSDATE WHERE C_USR_ID = ?";

			st = con.prepareStatement(sql);
			int cont = 1;
			st.setString(cont++, datos.getDNombre());

			st.setString(cont++, datos.getDApellido1());
			st.setString(cont++, datos.getDCorreoelectronico());

			if (datos.getNTelefono1() != null)
				st.setString(cont++, String.valueOf(datos.getNTelefono1().intValue()));
			else {
				st.setNull(cont++, 2);
			}
			st.setString(cont++, datos.getBSexo());
			st.setString(cont++, datos.getCDni());
			st.setString(cont++, datos.getCDniReal());
			st.setLong(cont++, datos.getCUsrId().longValue());

			filas = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null) {
				st.close();
			}

		}

		return filas;
	}

	public OCAPUsuariosOT buscarOCAPUsuarios(Long cUsrId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = null;
		OCAPUsuariosOT datos = null;
		try {
			loggerBD.info("buscarOCAPUsuarios: " + cUsrId);
			con = JCYLGestionTransacciones.getConnection();

			String sql = "SELECT D_NOMBRE, C_USR_ID, F_USUALTA, C_GERENCIA_ID, D_APELLIDOS, C_CENTROTRABAJO_ID, A_CORREOELECTRONICO, C_DNI, C_DNI_REAL, B_BORRADO, N_TELEFONO1, N_TELEFONO2, B_SEXO, A_DOMICILIO, C_PROVINCIA_ID, D_LOCAL, N_CODIGOPOSTAL FROM OCAP_USUARIOS WHERE C_USR_ID = ? ";

			st = con.prepareStatement(sql);
			st.setLong(1, cUsrId.longValue());
			rs = st.executeQuery();
			datos = new OCAPUsuariosOT();
			if (rs.next()) {
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCUsrId(cUsrId);
				datos.setFCreacion(rs.getDate("F_USUALTA"));

				datos.setCGerenciaId(Integer.valueOf(rs.getInt("C_GERENCIA_ID")));
				datos.setDApellido1(rs.getString("D_APELLIDOS"));
				datos.setCCentrotrabajoId(Integer.valueOf(rs.getInt("C_CENTROTRABAJO_ID")));
				datos.setDCorreoelectronico(rs.getString("A_CORREOELECTRONICO"));
				datos.setCDni(rs.getString("C_DNI"));
				datos.setCDniReal(rs.getString("C_DNI_REAL"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setNTelefono1(Integer.valueOf(rs.getInt("N_TELEFONO1")));
				datos.setNTelefono2(Integer.valueOf(rs.getInt("N_TELEFONO2")));
				datos.setBSexo(rs.getString("B_SEXO"));
				datos.setDDomicilio(rs.getString("A_DOMICILIO"));
				datos.setCProvinciaUsu_id(rs.getString("C_PROVINCIA_ID"));
				datos.setDLocalidadUsu(rs.getString("D_LOCAL"));
				datos.setCPostalUsu(rs.getString("N_CODIGOPOSTAL"));
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

	public OCAPUsuariosOT buscarUsuarioPorExpId(long cExpId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		boolean bCrearConexion = false;
		OCAPUsuariosOT datos = null;
		Connection con = null;
		try {
			loggerBD.info("Inicio");
			con = JCYLGestionTransacciones.getConnection();
			String sql = "SELECT solic.D_NOMBRE, usua.C_USR_ID, exca.c_exp_id, solic.D_APELLIDOS, solic.C_DNI, C_DNI_REAL, solic.B_SEXO, gra.d_descripcion "
					+ " FROM OCAP_USUARIOS usua, ocap_expedientescarrera exca, ocap_grados gra, ocap_solicitudes solic  "
					+ " WHERE exca.c_exp_id = ? "
					+ " AND exca.C_USR_ID = usua.c_usr_id  "
					+ " AND exca.c_grado_id = gra.c_grado_id  "
					+ " AND exca.b_borrado='N'"
					+ " AND usua.b_borrado='N' "
					+ " AND solic.c_usr_id = usua.c_usr_id"
					+ " AND solic.C_CONVOCATORIA_ID = exca.C_CONVOCATORIA_ID "
					+ " AND exca.C_SOLICITUD_ID = solic.C_SOLICITUD_ID ";

			st = con.prepareStatement(sql);
			st.setLong(1, cExpId);

			rs = st.executeQuery();

			datos = new OCAPUsuariosOT();
			if (rs.next()) {
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCUsrId(new Long(rs.getLong("c_usr_id")));
				datos.setCExpId(new Long(rs.getLong("c_exp_id")));
				datos.setDApellido1(rs.getString("D_APELLIDOS"));
				datos.setCDni(rs.getString("C_DNI"));
				datos.setCDniReal(rs.getString("C_DNI_REAL"));
				datos.setBSexo(rs.getString("B_SEXO"));
				datos.setDGrado_des(rs.getString("d_descripcion"));
			}
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if ((bCrearConexion) && (con != null) && (!con.isClosed())) {
				JCYLGestionTransacciones.close(con.getAutoCommit());
			}

		}

		return datos;
	}

	public ArrayList buscarOCAPUsuariosConInformeMotivado(Date fMotivado, long cGradoId, long cConvocatoriaId,
			long cGerenciaId, long cProfesionalId, JCYLUsuario jcylUsuario) throws SQLException {
		Connection con = JCYLGestionTransacciones.getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;

		ArrayList listado = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT u.c_usr_id, ec.c_exp_id, s.D_NOMBRE, s.D_APELLIDOS, s.C_DNI ")
					.append(" FROM OCAP_EXPEDIENTESCARRERA EC, OCAP_USUARIOS U ,  OCAP_SOLICITUDES s")
					.append(" WHERE EC.C_USR_ID = U.C_USR_ID ")
					.append("  AND s.c_usr_id = u.c_usr_id AND s.C_CONVOCATORIA_ID = ec.C_CONVOCATORIA_ID ");
			if (cGradoId != 0L)
				sql.append(" AND EC.C_GRADO_ID = ? ");
			if (cGerenciaId != 0L)
				sql.append(" AND s.C_GERENCIA_ACTUAL_ID = ? ");
			sql.append(" AND EC.C_CONVOCATORIA_ID = ? ").append(" AND EC.C_PROFESIONAL_ID = ? ")
					.append(" AND U.B_BORRADO = 'N' ")
					.append(" AND f_aceptac_solic is not null AND SYSDATE >= f_fin_mc ")
					.append(" AND ec.c_estado_id not in (1, 2, 4, 5, 6, 7, 8, 15, 20, 21) ");

			if (fMotivado != null)
				sql.append(" AND (TO_DATE(EC.F_MOTIVADO_ACEP,'dd/mm/rrrr') = TO_DATE(?,'dd/mm/rrrr') ")
						.append(" OR TO_DATE(EC.F_MOTIVADO_NEG,'dd/mm/rrrr') = TO_DATE(?,'dd/mm/rrrr')) ");
			if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
				sql.append(" AND (ec.C_PROC_EVALUACION_ID = ").append("4").append(" OR ec.C_PROC_EVALUACION_ID = ")
						.append("5").append(") ");
			if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
				sql.append(" AND ec.C_PROC_EVALUACION_ID != ").append("4").append(" AND ec.C_PROC_EVALUACION_ID != ")
						.append("5").append(" ");
			}
			sql.append(" AND ec.C_SOLICITUD_ID = s.C_SOLICITUD_ID ");
			sql.append(" ORDER BY U.D_NOMBRE, U.D_APELLIDOS, U.C_DNI_REAL ");

			st = con.prepareStatement(sql.toString());
			int cont = 1;
			if (cGradoId != 0L)
				st.setLong(cont++, cGradoId);
			if (cGerenciaId != 0L)
				st.setLong(cont++, cGerenciaId);
			st.setLong(cont++, cConvocatoriaId);
			st.setLong(cont++, cProfesionalId);
			if (fMotivado != null) {
				String fechaConvertida = DateUtil.convertDateToString(fMotivado);
				st.setString(cont++, fechaConvertida);
				st.setString(cont++, fechaConvertida);
			}

			rs = st.executeQuery();

			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPUsuariosOT datos = new OCAPUsuariosOT();
				datos.setCUsrId(new Long(rs.getLong("c_usr_id")));
				datos.setCExpId(new Long(rs.getLong("c_exp_id")));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDApellido1(rs.getString("D_APELLIDOS"));
				datos.setCDniReal(rs.getString("C_DNI"));

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

	public ArrayList buscarOCAPUsuariosConAclaraciones(Date fAclaraciones, long cGradoId, long cConvocatoriaId,
			long cGerenciaId, long cProfesionalId, JCYLUsuario jcylUsuario) throws SQLException {
		Connection con = JCYLGestionTransacciones.getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;

		ArrayList listado = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT u.c_usr_id, ec.c_exp_id, U.D_NOMBRE, U.D_APELLIDOS, U.C_DNI_REAL ")
					.append(" FROM OCAP_EXPEDIENTESCARRERA EC, OCAP_USUARIOS U ")
					.append(" WHERE EC.C_USR_ID = U.C_USR_ID ").append(" AND EC.C_GRADO_ID = ? ")
					.append(" AND EC.C_CONVOCATORIA_ID = ? ").append(" AND U.C_GERENCIA_ID = ? ")
					.append(" AND EC.C_PROFESIONAL_ID = ? ").append(" AND U.B_BORRADO = 'N' ")
					.append(" AND f_aceptac_solic is not null AND SYSDATE >= f_fin_mc ")
					.append(" AND ec.c_estado_id not in (1, 2, 4, 5, 6, 7, 8, 15) ");

			if (fAclaraciones != null)
				sql.append(" AND TO_DATE(EC.F_ACLARACIONES,'dd/mm/rrrr') = TO_DATE(?,'dd/mm/rrrr') ");
			if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
				sql.append(" AND (ec.C_PROC_EVALUACION_ID = ").append("4").append(" OR ec.C_PROC_EVALUACION_ID = ")
						.append("5").append(") ");
			if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
				sql.append(" AND ec.C_PROC_EVALUACION_ID != ").append("4").append(" AND ec.C_PROC_EVALUACION_ID != ")
						.append("5").append(" ");
			}

			sql.append(" ORDER BY U.D_NOMBRE, U.D_APELLIDOS, U.C_DNI_REAL ");

			st = con.prepareStatement(sql.toString());
			st.setLong(1, cGradoId);
			st.setLong(2, cConvocatoriaId);
			st.setLong(3, cGerenciaId);
			st.setLong(4, cProfesionalId);
			if (fAclaraciones != null) {
				st.setString(5, DateUtil.convertDateToString(fAclaraciones));
			}
			rs = st.executeQuery();

			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPUsuariosOT datos = new OCAPUsuariosOT();
				datos.setCUsrId(new Long(rs.getLong("c_usr_id")));
				datos.setCExpId(new Long(rs.getLong("c_exp_id")));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDApellido1(rs.getString("D_APELLIDOS"));
				datos.setCDniReal(rs.getString("C_DNI_REAL"));

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

	public OCAPUsuariosOT buscarOCAPUsuariosPorNIF(String cNifId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		OCAPUsuariosOT datos = null;
		Connection con = null;
		try {
			loggerBD.debug("buscarOCAPUsuariosPorNIF: Inicio");

			con = JCYLGestionTransacciones.getConnection();
			String sql = "SELECT D_NOMBRE, C_USR_ID, F_USUALTA, C_GERENCIA_ID, D_APELLIDOS, C_CENTROTRABAJO_ID, A_CORREOELECTRONICO, C_DNI, C_DNI_REAL, B_BORRADO, N_TELEFONO1, N_TELEFONO2, B_SEXO, A_DOMICILIO, C_PROVINCIA_ID, D_LOCAL, N_CODIGOPOSTAL FROM OCAP_USUARIOS WHERE LPAD(UPPER(C_DNI),9,'0') = LPAD(UPPER(?),9,'0') ";

			st = con.prepareStatement(sql);
			st.setString(1, cNifId);
			rs = st.executeQuery();
			datos = new OCAPUsuariosOT();

			if (rs.next()) {
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCUsrId(Long.valueOf(rs.getLong("C_USR_ID")));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setCGerenciaId(Integer.valueOf(rs.getInt("C_GERENCIA_ID")));
				datos.setDApellido1(rs.getString("D_APELLIDOS"));
				datos.setCCentrotrabajoId(Integer.valueOf(rs.getInt("C_CENTROTRABAJO_ID")));
				datos.setDCorreoelectronico(rs.getString("A_CORREOELECTRONICO"));
				datos.setCDni(rs.getString("C_DNI"));
				datos.setCDniReal(rs.getString("C_DNI_REAL"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setNTelefono1(Integer.valueOf(rs.getInt("N_TELEFONO1")));
				datos.setNTelefono2(Integer.valueOf(rs.getInt("N_TELEFONO2")));
				datos.setBSexo(rs.getString("B_SEXO"));
				datos.setDDomicilio(rs.getString("A_DOMICILIO"));
				datos.setCProvinciaUsu_id(rs.getString("C_PROVINCIA_ID"));
				datos.setDLocalidadUsu(rs.getString("D_LOCAL"));
				datos.setCPostalUsu(rs.getString("N_CODIGOPOSTAL"));
			}

			loggerBD.debug("buscarOCAPUsuariosPorNIF: Fin");
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

	public long buscarOCAPUsuarioRepetido(OCAPUsuariosOT usuarioOT, Connection con) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		long usuId = 0L;
		try {
			String sql = "SELECT usu.C_USR_ID FROM OCAP_USUARIOS usu WHERE UPPER(C_DNI) = LPAD(UPPER(?),9,'0')  AND UPPER(D_APELLIDOS) = UPPER(?)  usu.B_BORRADO='N' ";

			st = con.prepareStatement(sql);
			st.setString(1, usuarioOT.getCDni());
			st.setString(2, usuarioOT.getDApellido1());

			rs = st.executeQuery();

			if (rs.next())
				usuId = rs.getLong("C_USR_ID");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null) {
				st.close();
			}

		}

		return usuId;
	}

	public ArrayList listadoOCAPUsuarios() throws SQLException {
		return listadoOCAPUsuarios(1, -1);
	}

	public ArrayList listadoOCAPUsuarios(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT D_NOMBRE, C_USR_ID, F_USUALTA, C_GERENCIA_ID, D_APELLIDOS, C_CENTROTRABAJO_ID, A_CORREOELECTRONICO, C_DNI, C_DNI_REAL, B_BORRADO FROM OCAP_USUARIOS";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {

				OCAPUsuariosOT datos = new OCAPUsuariosOT();
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));

				datos.setCGerenciaId(new Integer(rs.getInt("C_GERENCIA_ID")));
				datos.setDApellido1(rs.getString("D_APELLIDOS"));
				datos.setCCentrotrabajoId(new Integer(rs.getInt("C_CENTROTRABAJO_ID")));
				datos.setDCorreoelectronico(rs.getString("A_CORREOELECTRONICO"));
				datos.setCDni(rs.getString("C_DNI"));
				datos.setCDniReal(rs.getString("C_DNI_REAL"));
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

	public ArrayList listarEvaluados(OCAPUsuariosOT datos, JCYLUsuario jcylUsuario, int inicio, int cuantos,
			int cConvocatoriaId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;

		long cte = 0L;
		try {
			String where = "";
			String sqlStatement = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT convo.d_nombre_corto as convocatoria_corto, solic.D_APELLIDOS, solic.D_NOMBRE, g.D_descripcion as descgrado , solic.C_DNI as C_DNI_REAL, usu.C_USR_ID, F_INICIO_CA, exp.C_EXP_ID, exp.C_PROFESIONAL_ID, (select d_nombre from ocap_categ_profesionales where c_profesional_id = exp.c_profesional_id) as dCategoria, exp.C_ESPEC_ID, (select d_nombre from ocap_especialidades where c_espec_id = exp.c_espec_id) as dEspecialidad, C_ITINERARIO_ID, (select d_descripcion from ITCP_MANUALES_EVALUACION where c_manual_evaluacion_id = exp.c_itinerario_id) as dItinerario, F_INFORME_EVAL, F_INFORME_CTE, F_INFORME_CE, F_INFORME_CC, (select F_INFORME from OCAP_REVISIONES where b_borrado='N' and C_EXP_ID= exp.c_exp_id) as F_INFORME_EVAL2, (select count(*) FROM ocap_expedientesencuestas WHERE b_borrado = 'N' and c_exp_id = EXP.c_exp_id) as nRespuestasEncuesta , (select count(c_revision_id) from ocap_revisiones where c_exp_id = exp.c_exp_id and b_borrado='N' ";
			if (datos.getCCompfqsId() != 0L)
				selectFrom = selectFrom + " and c_compfqs_id= " + datos.getCCompfqsId();
			selectFrom = selectFrom
					+ ") as bSegundaRevision FROM OCAP_USUARIOS usu, OCAP_SOLICITUDES solic, OCAP_EXPEDIENTESCARRERA exp, ocap_grados g, ocap_convocatorias convo WHERE usu.B_BORRADO = 'N' and exp.c_convocatoria_id = convo.c_convocatoria_id "
					+ " AND solic.c_usr_id = usu.c_usr_id AND solic.c_convocatoria_id = exp.c_convocatoria_id "
					+ "and exp.c_grado_id = g.c_grado_id AND exp.B_BORRADO = 'N' AND usu.C_USR_ID = exp.C_USR_ID ";
			if ((Constantes.OCAP_ADMINISTRADOR.equals(jcylUsuario.getUser().getRol()))
					|| (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))) {
				selectFrom = selectFrom + "AND exp.F_ACEPTACIONCC is not null AND SYSDATE > exp.F_FIN_EVAL_MC ";
			} else if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION)) {
				if (datos.getCCompfqsId() != 0L) {
					if (Constantes.SI.equals(datos.getBSegundaEvaluacion()))
						selectFrom = selectFrom
								+ "AND (select c_compfqs_id from ocap_revisiones where c_compfqs_id = ? and c_exp_id= exp.c_exp_id and b_borrado='N') = ? ";
					else if (Constantes.NO.equals(datos.getBSegundaEvaluacion()))
						selectFrom = selectFrom + "AND exp.C_COMPFQS_ID = ? ";
					else
						selectFrom = selectFrom
								+ "AND ( exp.C_COMPFQS_ID = ? OR (select c_compfqs_id from ocap_revisiones where c_compfqs_id = ? and c_exp_id= exp.c_exp_id and b_borrado='N') = ? )";
				} else
					selectFrom = selectFrom + "AND exp.F_ACEPTACIONCC is not null AND SYSDATE > exp.F_FIN_EVAL_MC ";
			} else if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CTE)) {
				selectFrom = selectFrom + " AND exp.C_COMPFQS_ID = ? AND exp.F_INFORME_EVAL is not null ";
			} else if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE)) {
				selectFrom = selectFrom + "AND exp.F_INFORME_CTE is not null ";
			} else {
				if (Constantes.SI.equals(datos.getBSegundaEvaluacion()))
					selectFrom = selectFrom
							+ "AND (select c_compfqs_id from ocap_revisiones where c_compfqs_id = ? and c_exp_id= exp.c_exp_id and b_borrado='N') = ? ";
				else if (Constantes.NO.equals(datos.getBSegundaEvaluacion()))
					selectFrom = selectFrom + "AND exp.C_COMPFQS_ID = ? ";
				else {
					selectFrom = selectFrom
							+ "AND ( exp.C_COMPFQS_ID = ? OR (select c_compfqs_id from ocap_revisiones where c_compfqs_id = ? and c_exp_id= exp.c_exp_id and b_borrado='N') = ? )";
				}
				if (Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol())) {
					if (cConvocatoriaId != 0)
						selectFrom = selectFrom
								+ " AND exp.C_CONVOCATORIA_ID IN (select C_CONVOCATORIA_ID from OCAP_CONVOCATORIAS where sysdate > f_publicacion and sysdate < f_fincp and b_borrado = 'N' AND C_CONVOCATORIA_ID = "
								+ cConvocatoriaId + " ) ";
					else {
						selectFrom = selectFrom
								+ " AND exp.C_CONVOCATORIA_ID IN (select C_CONVOCATORIA_ID from OCAP_CONVOCATORIAS where sysdate > f_publicacion and sysdate < f_fincp and b_borrado = 'N') ";
					}

				}

			}

			String selectConv = "";

			String orderBy = " ORDER BY exp.F_INICIO_CA ";
			String orderByCat = " ORDER BY exp.C_PROFESIONAL_ID, exp.C_EXP_ID ";
			String orderByDni = " ORDER BY exp.C_PROFESIONAL_ID, C_DNI_REAL ";
			String orderByIti = " ORDER BY exp.C_PROFESIONAL_ID, exp.C_ESPEC_ID, exp.C_ITINERARIO_ID, D_APELLIDOS, D_NOMBRE  ";

			boolean isDNombre = (datos.getDNombre() != null) && (!datos.getDNombre().equals(""));
			if (isDNombre)
				sbWhere.append("AND (upper(solic.D_NOMBRE) like upper('%").append(datos.getDNombre()).append("%')) ");
			boolean isDApellidos = (datos.getDApellido1() != null) && (!datos.getDApellido1().equals(""));
			if (isDApellidos)
				sbWhere.append("AND (upper(solic.D_APELLIDOS) like upper('%").append(datos.getDApellido1()).append("%'))");
			boolean isCDni = (datos.getCDni() != null) && (!datos.getCDni().equals(""));
			if (isCDni) {
				sbWhere.append(" AND  (upper(solic.C_DNI)) ='").append(datos.getCDni().toUpperCase()+"'");
			}
			if ((datos.getCProfesionalId() != null) && (datos.getCProfesionalId().longValue() != 0L))
				sbWhere.append("AND exp.C_PROFESIONAL_ID =").append(datos.getCProfesionalId());
			if (datos.getCConvocatoriaId() != 0L)
				sbWhere.append("AND exp.C_CONVOCATORIA_ID =").append(datos.getCConvocatoriaId());
			if ((datos.getCEspecId() != null) && (datos.getCEspecId().longValue() != 0L))
				sbWhere.append(" AND exp.C_ESPEC_ID =").append(datos.getCEspecId());
			if (datos.getCItinerarioId() != 0L)
				sbWhere.append(" AND exp.C_ITINERARIO_ID =").append(datos.getCItinerarioId());
			if ((datos.getCExpId() != null) && (datos.getCExpId().longValue() != 0L))
				sbWhere.append(" AND exp.C_EXP_ID =").append(datos.getCExpId());
			if (datos.getCGerenciaId() != 0L)
				sbWhere.append("AND solic.C_GERENCIA_ACTUAL_ID =").append(datos.getCGerenciaId());
			if (Constantes.SI.equals(datos.getBContestado()))
				sbWhere.append(
						" AND exp.C_EXP_ID IN (select distinct(c_exp_id) from ocap_expedientesencuestas where b_borrado='N') ");
			if (Constantes.NO.equals(datos.getBContestado()))
				sbWhere.append(
						" AND 0=(select count(*) from ocap_expedientesencuestas where b_borrado='N' and c_exp_id = EXP.c_exp_id) ");
			if ((datos.getDEstado() != null) && (!datos.getDEstado().equals(""))) {
				if (datos.getDEstado().equals("1"))
					sbWhere.append(" AND exp.F_INICIO_CA is null AND exp.C_ITINERARIO_ID is null");
				else if (datos.getDEstado().equals("2"))
					sbWhere.append(
							" AND exp.F_FIN_CA > SYSDATE AND exp.C_EXP_ID not in(select distinct(c_exp_id) from OCAP_EXPEDIENTESCAS where b_borrado = 'N') AND exp.C_ITINERARIO_ID is not null");
				else if (datos.getDEstado().equals("3"))
					sbWhere.append(
							" AND exp.F_FIN_CA > SYSDATE ");
				else if (datos.getDEstado().equals("4"))
					sbWhere.append(
							" AND exp.F_FIN_CA < SYSDATE AND exp.C_EXP_ID in(select distinct(c_exp_id) from OCAP_EXPEDIENTESCAS where b_borrado = 'N') ");
				else if (datos.getDEstado().equals("5"))
					sbWhere.append(
							" AND exp.F_FIN_CA < SYSDATE AND exp.C_EXP_ID not in(select distinct(c_exp_id) from OCAP_EXPEDIENTESCAS where b_borrado = 'N') ");
				else if (datos.getDEstado().equals("6"))//NUEVO
					sbWhere.append(
							" AND exp.F_FIN_CA < SYSDATE ");
				else if (datos.getDEstado().equals("7"))
					sbWhere.append(
							" AND exp.F_INFORME_EVAL IS NULL");	
				else if (datos.getDEstado().equals("8"))//NUEVO 
					sbWhere.append(
							" AND exp.F_INFORME_EVAL IS NOT NULL");
				else if (datos.getDEstado().equals("9")) //6 pasa a ser 9
					sbWhere.append(
							" AND exp.C_EXP_ID in(select distinct(c_exp_id) from OCAP_REVISIONES where b_borrado = 'N') ");				
				else if (datos.getDEstado().equals("10")) //8 pasa a ser el 10
					sbWhere.append(
							" AND exp.F_INFORME_CTE IS NULL"); 
				else if (datos.getDEstado().equals("11")) //NUEVO
					sbWhere.append(
							" AND exp.C_EXP_ID not in (select distinct(c_exp_id) from OCAP_DOC_FORMULARIO)");
				
			}
			
			if(Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol())) {
				sbWhere.append(
						" AND exp.C_ESTADO_ID not in (20,21) ");
			}

			if (((Constantes.OCAP_DIRECCION.equals(jcylUsuario.getUser().getRol())) && (datos.getCCompfqsId() == 0L))
					|| (Constantes.OCAP_ADMINISTRADOR.equals(jcylUsuario.getUser().getRol()))
					|| (Constantes.OCAP_CE.equals(jcylUsuario.getUser().getRol()))) {
				if ("ENCUESTA".equals(datos.getOpcion()))
					sqlStatement = selectFrom + sbWhere + selectConv + orderByIti;
				else
					sqlStatement = selectFrom + sbWhere + selectConv + orderByCat;
			} else if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
				sqlStatement = selectFrom + sbWhere + selectConv + orderByCat;
			else {
				sqlStatement = selectFrom + sbWhere + orderBy;
			}

			st = con.prepareStatement(sqlStatement, 1004, 1008);

			if ((!Constantes.OCAP_DIRECCION.equals(jcylUsuario.getUser().getRol()))
					&& (!Constantes.OCAP_ADMINISTRADOR.equals(jcylUsuario.getUser().getRol()))
					&& (!Constantes.OCAP_CE.equals(jcylUsuario.getUser().getRol()))
					&& (!Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))) {
				st.setLong(1, datos.getCCompfqsId());
				if (!Constantes.OCAP_CTE.equals(jcylUsuario.getUser().getRol()))
					if (datos.getBSegundaEvaluacion() == null) {
						st.setLong(2, datos.getCCompfqsId());
						st.setLong(3, datos.getCCompfqsId());
					} else if (Constantes.SI.equals(datos.getBSegundaEvaluacion())) {
						st.setLong(2, datos.getCCompfqsId());
					}
			} else if ((Constantes.OCAP_DIRECCION.equals(jcylUsuario.getUser().getRol())) && (datos.getCCompfqsId() != 0L)) {
				st.setLong(1, datos.getCCompfqsId());
				if (datos.getBSegundaEvaluacion() == null) {
					st.setLong(2, datos.getCCompfqsId());
					st.setLong(3, datos.getCCompfqsId());
				} else if (Constantes.SI.equals(datos.getBSegundaEvaluacion())) {
					st.setLong(2, datos.getCCompfqsId());
				}
			}
			rs = st.executeQuery();
			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {

				OCAPUsuariosOT result = new OCAPUsuariosOT();
				result.setDConvocatoriaNombreCorto(rs.getString("convocatoria_corto"));
				result.setDGrado_des(rs.getString("descgrado"));
				result.setDNombre(rs.getString("D_NOMBRE"));
				result.setDApellido1(rs.getString("D_APELLIDOS"));
				result.setCDni(rs.getString("C_DNI_REAL"));
				result.setCUsrId(Long.valueOf(rs.getString("C_USR_ID")));
				result.setFIncioCA(rs.getDate("F_INICIO_CA"));
				result.setCExpId(new Long(rs.getLong("C_EXP_ID")));
				result.setCCompfqsId(datos.getCCompfqsId());
				result.setCProfesionalId(new Integer(rs.getInt("C_PROFESIONAL_ID")));
				result.setDProfesional_nombre(rs.getString("dCategoria"));
				result.setCEspecId(new Integer(rs.getInt("C_ESPEC_ID")));
				result.setDEspec_nombre(rs.getString("dEspecialidad"));
				result.setCItinerarioId(rs.getLong("C_ITINERARIO_ID"));
				result.setDItinerario(rs.getString("dItinerario"));
				result.setFInformeCe(rs.getDate("F_INFORME_CE"));
				result.setFInformeCte(rs.getDate("F_INFORME_CTE"));
				result.setFInformeEval(rs.getDate("F_INFORME_EVAL"));
				result.setFInformeEval2(rs.getDate("F_INFORME_EVAL2"));
				result.setFInformeCC(rs.getDate("F_INFORME_CC"));
				if (rs.getInt("nRespuestasEncuesta") == 0)
					result.setBContestado(Constantes.NO);
				else {
					result.setBContestado(Constantes.SI);
				}
				if (rs.getInt("bSegundaRevision") == 0)
					result.setBSegundaEvaluacion(Constantes.NO);
				else {
					result.setBSegundaEvaluacion(Constantes.SI);
				}
				DecimalFormat formato = new DecimalFormat("000000");
				result.setCodigoId("EPR" + formato.format(result.getCExpId().longValue()));

				listado.add(result);
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

	public int listarEvaluadosCuenta(OCAPUsuariosOT datos, JCYLUsuario jcylUsuario) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int total = -1;
		try {
			String where = "";
			String sqlStatement = "";
			StringBuffer sbWhere = new StringBuffer(40);

			String selectFrom = "SELECT count(*) FROM OCAP_USUARIOS usu, OCAP_EXPEDIENTESCARRERA exp WHERE usu.B_BORRADO = 'N' AND exp.B_BORRADO = 'N' AND usu.C_USR_ID = exp.C_USR_ID ";
			if ((Constantes.OCAP_ADMINISTRADOR.equals(jcylUsuario.getUser().getRol()))
					|| (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))) {
				selectFrom = selectFrom + "AND F_ACEPTACIONCC is not null AND SYSDATE > F_FIN_EVAL_MC ";
			} else if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION)) {
				if (datos.getCCompfqsId() != 0L) {
					if (Constantes.SI.equals(datos.getBSegundaEvaluacion()))
						selectFrom = selectFrom + "AND (select c_compfqs_id from ocap_revisiones where c_compfqs_id = "
								+ datos.getCCompfqsId() + " and c_exp_id= exp.c_exp_id and b_borrado='N') = "
								+ datos.getCCompfqsId();
					else if (Constantes.NO.equals(datos.getBSegundaEvaluacion()))
						selectFrom = selectFrom + "AND exp.C_COMPFQS_ID = " + datos.getCCompfqsId();
					else
						selectFrom = selectFrom + "AND ( exp.C_COMPFQS_ID = " + datos.getCCompfqsId() + " OR "
								+ "(select c_compfqs_id from ocap_revisiones where c_compfqs_id = "
								+ datos.getCCompfqsId() + " and c_exp_id= exp.c_exp_id and b_borrado='N') = "
								+ datos.getCCompfqsId() + " )";
				} else
					selectFrom = selectFrom + "AND F_ACEPTACIONCC is not null AND SYSDATE > F_FIN_EVAL_MC ";
			} else if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CTE)) {
				selectFrom = selectFrom + " AND F_INFORME_EVAL is not null AND exp.C_COMPFQS_ID = "
						+ datos.getCCompfqsId() + " ";
			} else if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE)) {
				selectFrom = selectFrom + "AND F_INFORME_CTE is not null ";
			} else {
				if (Constantes.SI.equals(datos.getBSegundaEvaluacion()))
					selectFrom = selectFrom + "AND (select c_compfqs_id from ocap_revisiones where c_compfqs_id = "
							+ datos.getCCompfqsId() + " and c_exp_id= exp.c_exp_id and b_borrado='N') = "
							+ datos.getCCompfqsId();
				else if (Constantes.NO.equals(datos.getBSegundaEvaluacion()))
					selectFrom = selectFrom + "AND exp.C_COMPFQS_ID = " + datos.getCCompfqsId();
				else {
					selectFrom = selectFrom + "AND ( exp.C_COMPFQS_ID = " + datos.getCCompfqsId() + " OR "
							+ "(select c_compfqs_id from ocap_revisiones where c_compfqs_id = " + datos.getCCompfqsId()
							+ " and c_exp_id= exp.c_exp_id and b_borrado='N') = " + datos.getCCompfqsId() + " )";
				}
				if (Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol())) {
					selectFrom = selectFrom
							+ " AND exp.C_CONVOCATORIA_ID IN (select C_CONVOCATORIA_ID from OCAP_CONVOCATORIAS where sysdate > f_publicacion and sysdate < f_fincp and b_borrado = 'N') ";
				}
			}

			String selectConv = "";

			boolean isDNombre = (datos.getDNombre() != null) && (!datos.getDNombre().equals(""));
			if (isDNombre)
				sbWhere.append("AND (upper(D_NOMBRE) like upper('%").append(datos.getDNombre()).append("%')) ");
			boolean isDApellidos = (datos.getDApellido1() != null) && (!datos.getDApellido1().equals(""));
			if (isDApellidos)
				sbWhere.append("AND (upper(D_APELLIDOS) like upper('%").append(datos.getDApellido1()).append("%'))");
			boolean isCDni = (datos.getCDni() != null) && (!datos.getCDni().equals(""));
			if (isCDni) {
				sbWhere.append("AND (upper(C_DNI_REAL) like upper('%").append(datos.getCDni()).append("%')) ");
			}
			if (datos.getCProfesionalId().longValue() != 0L)
				sbWhere.append("AND (exp.C_PROFESIONAL_ID) =").append(datos.getCProfesionalId());
			if (datos.getCConvocatoriaId() != 0L)
				sbWhere.append("AND exp.C_CONVOCATORIA_ID =").append(datos.getCConvocatoriaId());
			if (datos.getCEspecId().longValue() != 0L)
				sbWhere.append(" AND (exp.C_ESPEC_ID) =").append(datos.getCEspecId());
			if (datos.getCItinerarioId() != 0L)
				sbWhere.append(" AND (C_ITINERARIO_ID) =").append(datos.getCItinerarioId());
			if ((datos.getCExpId() != null) && (datos.getCExpId().longValue() != 0L))
				sbWhere.append(" AND (C_EXP_ID) =").append(datos.getCExpId());
			if (Constantes.SI.equals(datos.getBContestado()))
				sbWhere.append(
						" AND C_EXP_ID IN (select distinct(c_exp_id) from ocap_expedientesencuestas where b_borrado='N') ");
			if (Constantes.NO.equals(datos.getBContestado()))
				sbWhere.append(
						" AND 0=(select count(*) from ocap_expedientesencuestas where b_borrado='N' and c_exp_id = EXP.c_exp_id) ");
			if ((datos.getDEstado() != null) && (!datos.getDEstado().equals(""))) {
				if (datos.getDEstado().equals("1"))
					sbWhere.append(" AND F_INICIO_CA is null ");
				else if (datos.getDEstado().equals("2"))
					sbWhere.append(
							" AND F_FIN_CA > SYSDATE AND C_EXP_ID not in(select c_exp_id from OCAP_EXPEDIENTESCAS where b_borrado = 'N') ");
				else if (datos.getDEstado().equals("3"))
					sbWhere.append(
							" AND F_FIN_CA > SYSDATE AND C_EXP_ID in(select c_exp_id from OCAP_EXPEDIENTESCAS where b_borrado = 'N') ");
				else if (datos.getDEstado().equals("4"))
					sbWhere.append(
							" AND F_FIN_CA < SYSDATE AND C_EXP_ID in(select c_exp_id from OCAP_EXPEDIENTESCAS where b_borrado = 'N') ");
				else if (datos.getDEstado().equals("5"))
					sbWhere.append(
							" AND F_FIN_CA < SYSDATE AND C_EXP_ID not in(select c_exp_id from OCAP_EXPEDIENTESCAS where b_borrado = 'N') ");
				else if (datos.getDEstado().equals("6"))
					sbWhere.append(
							" AND C_EXP_ID in(select distinct(c_exp_id) from OCAP_REVISIONES where b_borrado = 'N') ");
				else if (datos.getDEstado().equals("7"))
					sbWhere.append(" AND F_INFORME_EVAL IS NULL");
				else if (datos.getDEstado().equals("8")) {
					sbWhere.append(" AND F_INFORME_CTE IS NULL");
				}
			}

			if ((jcylUsuario.getUser().getRol() != null)
					&& (((jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION)) && (datos.getCCompfqsId() == 0L))
							|| (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR))
							|| (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE))
							|| (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC))))
				sqlStatement = selectFrom + sbWhere + selectConv;
			else {
				sqlStatement = selectFrom + sbWhere;
			}

			st = con.prepareStatement(sqlStatement, 1004, 1008);

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

	public int modificarDatosUsuarios(OCAPUsuariosOT datos, Connection con) throws SQLException {
		PreparedStatement st = null;

		int filas = 0;
		StringBuffer sql = new StringBuffer();
		try {
			sql.append("UPDATE OCAP_USUARIOS ").append("SET D_NOMBRE = ?, ").append("D_APELLIDOS = ?, ")
					.append("B_SEXO = ?, ").append("N_TELEFONO1 = ?, ").append("N_TELEFONO2 = ?, ")
					.append("A_CORREOELECTRONICO = ?, ");
			if (datos.getCDni() != null)
				sql.append("C_DNI = LPAD(?,9,'0'), ");
			sql.append("C_DNI_REAL = LPAD(?,9,'0'), ").append("A_DOMICILIO = ?, ").append("C_PROVINCIA_ID = ?, ")
					.append("D_LOCAL = ?, ").append("N_CODIGOPOSTAL = ?, ").append("C_CENTROTRABAJO_ID = ?, ")
					.append("C_GERENCIA_ID = ?, ").append("F_USUMODI = SYSDATE, ").append("C_USUMODI = ? ")
					.append("WHERE C_USR_ID = ?");

			st = con.prepareStatement(sql.toString());
			int cont = 1;
			st.setString(cont++, datos.getDNombre());
			st.setString(cont++, datos.getDApellido1());
			st.setString(cont++, datos.getBSexo());
			if (datos.getNTelefono1() != null)
				st.setString(cont++, String.valueOf(datos.getNTelefono1().intValue()));
			else
				st.setNull(cont++, 2);
			if (datos.getNTelefono2() != null)
				st.setString(cont++, String.valueOf(datos.getNTelefono2().intValue()));
			else
				st.setNull(cont++, 2);
			st.setString(cont++, datos.getDCorreoelectronico());
			if (datos.getCDni() != null)
				st.setString(cont++, datos.getCDni());
			st.setString(cont++, datos.getCDniReal());
			st.setString(cont++, datos.getDDomicilio());
			st.setString(cont++, datos.getCProvinciaUsu_id());
			st.setString(cont++, datos.getDLocalidadUsu());
			st.setString(cont++, datos.getCPostalUsu());
			if (datos.getCCentrotrabajoId().intValue() != 0)
				st.setInt(cont++, datos.getCCentrotrabajoId().intValue());
			else {
				st.setNull(cont++, 4);
			}
			st.setInt(cont++, datos.getCGerenciaId().intValue());

			st.setString(cont++, datos.getAModificadoPor());

			st.setLong(cont++, datos.getCUsrId().longValue());

			filas = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null) {
				st.close();
			}
		}

		return filas;
	}

	public ArrayList buscarIndicador(String opcion) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String sqlStatement = "";
			StringBuffer sbWhere = new StringBuffer(40);

			String selectFromEval = "SELECT count(*) as total, exp.c_compfqs_id, com.d_nombre, com.d_apellidos, b_reconocimiento_grado FROM OCAP_EXPEDIENTESCARRERA exp, ocap_componentesfqs com ";
			String selectFromItin = "SELECT count(*) as total, exp.c_itinerario_id, iti.d_descripcion, b_reconocimiento_grado FROM OCAP_EXPEDIENTESCARRERA exp, ocap_itinerarios iti ";
			String selectFromGere = "SELECT count(*) as total, usu.c_gerencia_id, ger.d_nombre, b_reconocimiento_grado FROM ocap_expedientescarrera EXP, ocap_gerencias ger, ocap_usuarios usu ";
			String where = "WHERE exp.B_BORRADO = 'N' ";
			if (opcion.equals("eval")) {
				where = where + "AND com.B_BORRADO = 'N' AND EXP.c_compfqs_id = com.c_compfqs_id ";

				where = where
						+ "AND com.c_compfqs_id in (  select c_compfqs_id from ocap_compfqs_convocatorias occ   where occ.c_cte_id IN (   select oc.c_cte_id from ocap_ctes oc, ocap_ctesactivados oca where oc.c_cte_id = oca.c_cte_id and oc.b_borrado='N' and oca.b_borrado='N'   ) )";
			} else if (opcion.equals("itin")) {
				where = where
						+ "AND iti.B_BORRADO = 'N' AND EXP.c_itinerario_id = iti.c_itinerario_id and exp.C_COMPFQS_ID is not null ";
			} else if (opcion.equals("gere")) {
				where = where
						+ "AND ger.B_BORRADO = 'N'  AND usu.B_BORRADO = 'N' and exp.C_COMPFQS_ID is not null AND EXP.c_usr_id = usu.c_usr_id  AND usu.c_gerencia_id = ger.c_gerencia_id ";
			}

			where = where
					+ "and b_reconocimiento_grado in ('Y', 'N')  AND C_CONVOCATORIA_ID IN (select C_CONVOCATORIA_ID from OCAP_CONVOCATORIAS where sysdate > f_publicacion and sysdate < f_fincp and b_borrado = 'N') ";
			String groupByEval = "group by exp.c_compfqs_id, com.d_nombre, com.d_apellidos, b_reconocimiento_grado order by c_compfqs_id";
			String groupByItin = "group by exp.c_itinerario_id, iti.d_descripcion, b_reconocimiento_grado order by c_itinerario_id";
			String groupByGere = "group by usu.c_gerencia_id, ger.d_nombre, b_reconocimiento_grado order by c_gerencia_id";

			if (opcion.equals("eval"))
				sqlStatement = selectFromEval + where + groupByEval;
			else if (opcion.equals("itin"))
				sqlStatement = selectFromItin + where + groupByItin;
			else if (opcion.equals("gere"))
				sqlStatement = selectFromGere + where + groupByGere;
			else if (opcion.equals("evalfqs")) {
				sqlStatement = selectFromEval + where + groupByEval;
			}

			st = con.prepareStatement(sqlStatement, 1004, 1008);

			rs = st.executeQuery(sqlStatement);

			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPUsuariosOT result = new OCAPUsuariosOT();
				result.setTotalIndicador(rs.getInt("total"));
				if (opcion.equals("eval")) {
					result.setDApellido1(rs.getString("d_apellidos"));
					result.setDNombre(rs.getString("d_nombre"));
					result.setCCompfqsId(rs.getLong("c_compfqs_id"));
				} else if (opcion.equals("itin")) {
					result.setCItinerarioId(rs.getLong("c_itinerario_id"));
					result.setDDescripcion(rs.getString("d_descripcion"));
				} else if (opcion.equals("gere")) {
					result.setCGerenciaId(new Integer(rs.getInt("c_gerencia_id")));
					result.setDNombre(rs.getString("d_nombre"));
				}
				if (opcion.equals("evalfqs")) {
					result.setDApellido1(rs.getString("d_apellidos"));
					result.setDNombre(rs.getString("d_nombre"));
					result.setCCompfqsId(rs.getLong("c_compfqs_id"));
				}

				result.setBReconocimientoGrado(rs.getString("b_reconocimiento_grado"));

				listado.add(result);
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

	public ArrayList buscarIndicadorCTEMod(JCYLUsuario jcylUsuario, boolean mod, String opcion) throws Exception {
		ArrayList listado = null;

		ArrayList retorno = new ArrayList();

		OCAPUsuariosOAD oCAPUsuariosOAD = getInstance();

		listado = oCAPUsuariosOAD.listarCTEsMod();

		OCAPUsuariosOT usuarioOT = new OCAPUsuariosOT();
		OCAPUsuariosOT usuarioOTAux = new OCAPUsuariosOT();
		boolean aprobado = false;
		long cEvalAnt = 0L;
		int aprobados = 0;
		int noAprobados = 0;
		boolean bAux = false;
		try {
			OCAPUsuariosOT result = new OCAPUsuariosOT();
			OCAPUsuariosOT result2 = new OCAPUsuariosOT();
			for (int i = 0; i < listado.size(); i++) {
				usuarioOT = (OCAPUsuariosOT) listado.get(i);

				if (i == 0) {
					usuarioOTAux = (OCAPUsuariosOT) listado.get(i);
				}
				if ("cte".equals(opcion))
					bAux = usuarioOT.isBConformeCTE();
				else if ("ce".equals(opcion)) {
					bAux = usuarioOT.isBDecisionCE();
				}

				if (bAux == mod) {
					if (usuarioOT.getNCreditosEvaluador() >= usuarioOT.getNCreditosNecesarios())
						aprobados++;
					else {
						noAprobados++;
					}
					result = new OCAPUsuariosOT();
					if (mod)
						result.setTotalIndicadorMod(aprobados);
					else {
						result.setTotalIndicadorNoMod(aprobados);
					}
					result.setDApellido1(usuarioOT.getDApellido1());
					result.setDNombre(usuarioOT.getDNombre());
					result.setCCompfqsId(usuarioOT.getCCompfqsId());
					result.setBReconocimientoGrado(Constantes.SI);
					result.setCCteId(usuarioOT.getCCteId());

					retorno.add(result);

					result2 = new OCAPUsuariosOT();
					if (mod)
						result2.setTotalIndicadorMod(noAprobados);
					else
						result2.setTotalIndicadorNoMod(noAprobados);
					result2.setDApellido1(usuarioOT.getDApellido1());
					result2.setDNombre(usuarioOT.getDNombre());
					result2.setCCompfqsId(usuarioOT.getCCompfqsId());
					result2.setBReconocimientoGrado(Constantes.NO);

					retorno.add(result2);

					aprobados = 0;
					noAprobados = 0;
				}

			}

		} catch (Exception ex) {
			throw ex;
		} finally {
		}

		return retorno;
	}

	public ArrayList buscarIndicadorEvaluadoresMod(JCYLUsuario jcylUsuario, boolean mod) throws Exception {
		ArrayList listado = null;

		ArrayList retorno = new ArrayList();

		OCAPUsuariosOAD oCAPUsuariosOAD = getInstance();

		listado = oCAPUsuariosOAD.listarEvaluadosMod();

		OCAPUsuariosOT usuarioOT = new OCAPUsuariosOT();
		OCAPUsuariosOT usuarioOTAux = new OCAPUsuariosOT();
		boolean aprobado = false;
		long cEvalAnt = 0L;
		int aprobados = 0;
		int noAprobados = 0;
		try {
			OCAPUsuariosOT result = new OCAPUsuariosOT();
			OCAPUsuariosOT result2 = new OCAPUsuariosOT();
			for (int i = 0; i < listado.size(); i++) {
				usuarioOT = (OCAPUsuariosOT) listado.get(i);

				if (i == 0) {
					usuarioOTAux = (OCAPUsuariosOT) listado.get(i);
				}
				if (usuarioOT.isBModEvaluador() == mod) {
					if (usuarioOT.getNCreditosEvaluador() >= usuarioOT.getNCreditosNecesarios())
						aprobados++;
					else {
						noAprobados++;
					}
					result = new OCAPUsuariosOT();
					if (mod)
						result.setTotalIndicadorMod(aprobados);
					else {
						result.setTotalIndicadorNoMod(aprobados);
					}
					result.setDApellido1(usuarioOT.getDApellido1());
					result.setDNombre(usuarioOT.getDNombre());
					result.setCCompfqsId(usuarioOT.getCCompfqsId());
					result.setBReconocimientoGrado(Constantes.SI);

					retorno.add(result);

					result2 = new OCAPUsuariosOT();
					if (mod)
						result2.setTotalIndicadorMod(noAprobados);
					else
						result2.setTotalIndicadorNoMod(noAprobados);
					result2.setDApellido1(usuarioOT.getDApellido1());
					result2.setDNombre(usuarioOT.getDNombre());
					result2.setCCompfqsId(usuarioOT.getCCompfqsId());
					result2.setBReconocimientoGrado(Constantes.NO);

					retorno.add(result2);

					aprobados = 0;
					noAprobados = 0;
				}

			}

		} catch (Exception ex) {
			throw ex;
		} finally {
		}

		return retorno;
	}

	public ArrayList buscarIndicadorEvaluadoresMod(String opcion) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;

		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String sqlStatement = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFromEval = "SELECT count(*) as total, exp.c_compfqs_id, com.d_nombre, com.d_apellidos, b_reconocimiento_grado FROM OCAP_EXPEDIENTESCARRERA exp, ocap_componentesfqs com ";

			String where = "WHERE exp.B_BORRADO = 'N' ";

			where = where + "AND com.B_BORRADO = 'N' AND EXP.c_compfqs_id = com.c_compfqs_id ";
			where = where
					+ " and c_exp_id in (select distinct c_exp_id from ocap_creditoscuestionarios occ where  occ.B_ACUERDO = 'N' and occ.b_borrado = 'N') ";
			where = where
					+ " AND C_CONVOCATORIA_ID IN (select C_CONVOCATORIA_ID from OCAP_CONVOCATORIAS where sysdate > f_publicacion and sysdate < f_fincp and b_borrado = 'N') ";
			String groupByEval = "group by exp.c_compfqs_id, com.d_nombre, com.d_apellidos, b_reconocimiento_grado order by c_compfqs_id";

			sqlStatement = selectFromEval + where + groupByEval;

			st = con.prepareStatement(sqlStatement, 1004, 1008);

			rs = st.executeQuery(sqlStatement);

			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPUsuariosOT result = new OCAPUsuariosOT();

				result.setTotalIndicadorMod(rs.getInt("total"));

				if (opcion.equals("evalfqs")) {
					result.setDApellido1(rs.getString("d_apellidos"));
					result.setDNombre(rs.getString("d_nombre"));
					result.setCCompfqsId(rs.getLong("c_compfqs_id"));
				}

				result.setBReconocimientoGrado(rs.getString("b_reconocimiento_grado"));

				listado.add(result);
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

	public ArrayList buscarIndicadorEvaluadoresNoMod(String opcion) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;

		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String sqlStatement = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFromEval = "SELECT count(*) as total, exp.c_compfqs_id, com.d_nombre, com.d_apellidos, b_reconocimiento_grado FROM OCAP_EXPEDIENTESCARRERA exp, ocap_componentesfqs com ";

			String where = "WHERE exp.B_BORRADO = 'N' ";

			where = where + "AND com.B_BORRADO = 'N' AND EXP.c_compfqs_id = com.c_compfqs_id ";
			where = where
					+ " and c_exp_id in (select distinct c_exp_id from ocap_creditoscuestionarios occ where  occ.B_ACUERDO ='N' and occ.b_borrado = 'N') ";
			where = where
					+ " AND C_CONVOCATORIA_ID IN (select C_CONVOCATORIA_ID from OCAP_CONVOCATORIAS where sysdate > f_publicacion and sysdate < f_fincp and b_borrado = 'N') ";
			String groupByEval = "group by exp.c_compfqs_id, com.d_nombre, com.d_apellidos, b_reconocimiento_grado order by c_compfqs_id";

			sqlStatement = selectFromEval + where + groupByEval;

			st = con.prepareStatement(sqlStatement, 1004, 1008);

			rs = st.executeQuery(sqlStatement);

			listado = new ArrayList();
			int i = 0;

			while (rs.next()) {
				OCAPUsuariosOT result = new OCAPUsuariosOT();

				result.setTotalIndicador(rs.getInt("total"));

				if (opcion.equals("evalfqs")) {
					result.setDApellido1(rs.getString("d_apellidos"));
					result.setDNombre(rs.getString("d_nombre"));
					result.setCCompfqsId(rs.getLong("c_compfqs_id"));
				}

				result.setBReconocimientoGrado(rs.getString("b_reconocimiento_grado"));

				listado.add(result);
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

	public ArrayList buscarIndicadorEvaluadores(String opcion) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;

		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String sqlStatement = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFromEval = "SELECT count(*) as total, exp.c_compfqs_id, com.d_nombre, com.d_apellidos, b_reconocimiento_grado FROM OCAP_EXPEDIENTESCARRERA exp, ocap_componentesfqs com ";

			String where = "WHERE exp.B_BORRADO = 'N' ";

			where = where + "AND com.B_BORRADO = 'N' AND EXP.c_compfqs_id = com.c_compfqs_id ";
			where = where
					+ " AND C_CONVOCATORIA_ID IN (select C_CONVOCATORIA_ID from OCAP_CONVOCATORIAS where sysdate > f_publicacion and sysdate < f_fincp and b_borrado = 'N') ";
			String groupByEval = "group by exp.c_compfqs_id, com.d_nombre, com.d_apellidos, b_reconocimiento_grado order by c_compfqs_id";

			sqlStatement = selectFromEval + where + groupByEval;

			st = con.prepareStatement(sqlStatement, 1004, 1008);

			rs = st.executeQuery(sqlStatement);

			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPUsuariosOT result = new OCAPUsuariosOT();

				result.setTotalIndicador(rs.getInt("total"));

				if (opcion.equals("evalfqs")) {
					result.setDApellido1(rs.getString("d_apellidos"));
					result.setDNombre(rs.getString("d_nombre"));
					result.setCCompfqsId(rs.getLong("c_compfqs_id"));
				} else if (opcion.equals("cte")) {
					result.setDApellido1(rs.getString("d_apellidos"));
					result.setDNombre(rs.getString("d_nombre"));
					result.setCCompfqsId(rs.getLong("c_compfqs_id"));
				} else if (opcion.equals("ce")) {
					result.setDApellido1(rs.getString("d_apellidos"));
					result.setDNombre(rs.getString("d_nombre"));
					result.setCCompfqsId(rs.getLong("c_compfqs_id"));
				}

				result.setBReconocimientoGrado(rs.getString("b_reconocimiento_grado"));

				listado.add(result);
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

	public ArrayList listarEvaluados() throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;

		long cte = 0L;
		try {
			String where = "";
			String sqlStatement = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "";

			String selectFromEval = "SELECT  c_exp_id, EXP.c_compfqs_id, c_itinerario_id FROM ocap_expedientescarrera EXP ";

			where = "WHERE EXP.b_borrado = 'N' ";
			where = where + "AND b_validacioncc = 'Y' ";
			where = where + "and EXP.c_compfqs_id is not null ";
			where = where + "AND c_convocatoria_id IN ( ";
			where = where + "SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion ";
			where = where + "AND SYSDATE < f_fincp AND b_borrado = 'N') ";
			where = where + "order by EXP.c_compfqs_id, c_exp_id ";

			sqlStatement = selectFromEval + where;

			st = con.prepareStatement(sqlStatement, 1004, 1008);

			rs = st.executeQuery();

			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPUsuariosOT result = new OCAPUsuariosOT();

				result.setCExpId(new Long(rs.getLong("C_EXP_ID")));
				result.setCCompfqsId(rs.getLong("c_compfqs_id"));
				result.setCItinerarioId(rs.getLong("c_itinerario_id"));

				listado.add(result);
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

	public ArrayList listarEvaluadosMod() throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;

		long cte = 0L;
		try {
			String where = "";
			String sqlStatement = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "";

			String selectFromEval = "SELECT distinct ocf.D_APELLIDOS, ocf.D_NOMBRE, exp.c_exp_id, EXP.c_compfqs_id, exp.c_itinerario_id  , sum(occ.N_CREDITOS_EVALUADOR) as n_creditos_evaluador , SUM (DECODE (B_ACUERDO, 'N', 1, 0)) AS Modificado, oi.N_CREDITOS_NECESARIOS ";

			selectFromEval = selectFromEval
					+ "FROM ocap_expedientescarrera EXP , ocap_creditoscuestionarios occ, ocap_itinerarios oi , OCAP_COMPONENTESFQS ocf ";
			where = "WHERE EXP.b_borrado = 'N' ";
			where = where + "      AND b_validacioncc = 'Y'  ";
			where = where + "      and EXP.c_compfqs_id is not null  ";
			where = where + "and occ.C_EXP_ID = exp.C_EXP_ID  ";
			where = where + "and occ.B_BORRADO = 'N'  ";
			where = where + "      and occ.B_ACUERDO is not null  ";
			where = where + "and oi.C_ITINERARIO_ID = exp.C_ITINERARIO_ID  ";
			where = where + "and ocf.C_COMPFQS_ID = exp.C_COMPFQS_ID ";

			where = where + "and exp.C_PROC_EVALUACION_ID NOT IN (4, 5) ";
			where = where + "      AND c_convocatoria_id IN (  ";
			where = where
					+ "        SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion                ";
			where = where + "         AND SYSDATE < f_fincp AND b_borrado = 'N')  ";
			where = where
					+ "group by exp.c_exp_id, EXP.c_compfqs_id,  exp.c_itinerario_id, oi.n_creditos_necesarios, ocf.D_APELLIDOS, ocf.D_NOMBRE ";
			where = where + "order by EXP.c_compfqs_id, c_exp_id ";

			sqlStatement = selectFromEval + where;

			st = con.prepareStatement(sqlStatement, 1004, 1008);

			rs = st.executeQuery();

			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPUsuariosOT result = new OCAPUsuariosOT();

				result.setCExpId(new Long(rs.getLong("C_EXP_ID")));
				result.setCCompfqsId(rs.getLong("c_compfqs_id"));
				result.setCItinerarioId(rs.getLong("c_itinerario_id"));
				result.setNCreditosEvaluador(rs.getLong("n_creditos_evaluador"));
				result.setNCreditosNecesarios(rs.getLong("N_CREDITOS_NECESARIOS"));
				result.setDNombre(rs.getString("D_NOMBRE"));
				result.setDApellido1(rs.getString("D_APELLIDOS"));

				if (rs.getInt("Modificado") > 0)
					result.setBModEvaluador(true);
				else {
					result.setBModEvaluador(false);
				}
				listado.add(result);
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

	public ArrayList listarCTEsMod() throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;

		long cte = 0L;
		try {
			String where = "";
			String sqlStatement = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "";

			String selectFromEval = "SELECT distinct ocf.D_APELLIDOS, ocf.D_NOMBRE, exp.c_exp_id, EXP.c_compfqs_id, exp.c_itinerario_id  ,  ";
			selectFromEval = selectFromEval
					+ "sum(occ.N_CREDITOS_EVALUADOR) as n_creditos_evaluador , SUM (DECODE (B_ACUERDO, 'N', 1, 0)) AS Modificado, oi.N_CREDITOS_NECESARIOS, ";
			selectFromEval = selectFromEval
					+ " ocfc.C_CTE_ID, exp.B_CONFORMIDAD_CTE, octe.D_NOMBRE as NOMBRE_CTE , exp.B_DECISION_CE ";
			selectFromEval = selectFromEval
					+ "FROM ocap_expedientescarrera EXP , ocap_creditoscuestionarios occ, ocap_itinerarios oi ";
			selectFromEval = selectFromEval
					+ ", OCAP_COMPONENTESFQS ocf, OCAP_COMPFQS_CONVOCATORIAS ocfc, OCAP_CTES octe ";
			where = "WHERE EXP.b_borrado = 'N' ";
			where = where
					+ "AND b_validacioncc = 'Y' and EXP.c_compfqs_id is not null and occ.C_EXP_ID = exp.C_EXP_ID ";
			where = where
					+ "and occ.B_BORRADO = 'N' and occ.B_ACUERDO is not null and oi.C_ITINERARIO_ID = exp.C_ITINERARIO_ID ";
			where = where
					+ "and ocf.C_COMPFQS_ID = exp.C_COMPFQS_ID\tand ocfc.C_COMPFQS_ID = ocf.C_COMPFQS_ID and ocfc.C_COMPFQS_ID = exp.C_COMPFQS_ID ";
			where = where
					+ "and ocfc.C_ITINERARIO_ID = exp.C_ITINERARIO_ID and ocfc.C_CTE_ID = octe.C_CTE_ID and ocfc.B_BORRADO = 'N' ";
			where = where + "and ocf.b_borrado = 'N'\tand octe.b_borrado = 'N' and ocfc.C_CONVOCATORIA_ID in (  ";
			where = where + "SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion   ";
			where = where + "AND SYSDATE < f_fincp AND b_borrado = 'N') ";

			where = where + "and exp.C_PROC_EVALUACION_ID NOT IN (4, 5) ";
			where = where + "AND exp.c_convocatoria_id IN (  ";
			where = where + "SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion    ";
			where = where + "AND SYSDATE < f_fincp AND b_borrado = 'N')  ";
			where = where
					+ "group by exp.c_exp_id, EXP.c_compfqs_id,  exp.c_itinerario_id, oi.n_creditos_necesarios,ocf.D_APELLIDOS, ";
			where = where + "ocf.D_NOMBRE, ocfc.C_CTE_ID, exp.B_CONFORMIDAD_CTE, octe.D_NOMBRE , exp.B_DECISION_CE  ";
			where = where + "  order by ocfc.C_CTE_ID, EXP.c_compfqs_id, c_exp_id  ";

			sqlStatement = selectFromEval + where;

			st = con.prepareStatement(sqlStatement, 1004, 1008);

			rs = st.executeQuery();

			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPUsuariosOT result = new OCAPUsuariosOT();

				result.setCExpId(new Long(rs.getLong("C_EXP_ID")));
				result.setCCompfqsId(rs.getLong("c_compfqs_id"));
				result.setCItinerarioId(rs.getLong("c_itinerario_id"));
				result.setNCreditosEvaluador(rs.getLong("n_creditos_evaluador"));
				result.setNCreditosNecesarios(rs.getLong("N_CREDITOS_NECESARIOS"));
				result.setDNombre(rs.getString("NOMBRE_CTE"));
				result.setCCteId(rs.getLong("c_cte_id"));

				if (Constantes.NO.equals(rs.getString("B_DECISION_CE")))
					result.setBDecisionCE(false);
				else {
					result.setBDecisionCE(true);
				}

				if (Constantes.SI.equals(rs.getString("B_CONFORMIDAD_CTE")))
					result.setBConformeCTE(true);
				else {
					result.setBConformeCTE(false);
				}
				listado.add(result);
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

	public int contarEvaluadosExcluidos() throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int contador = 0;
		try {
			String sql = "select count(distinct(c_exp_id)) as contadorEvaluados  from ocap_expedientescarrera  where b_validacioncc='Y' and (    (F_FIN_CA < SYSDATE AND C_EXP_ID not in(select distinct(c_exp_id) from OCAP_EXPEDIENTESCAS where b_borrado = 'N') )     OR C_ITINERARIO_ID is null      OR f_fin_ca > SYSDATE      OR f_fin_ca is null     OR (F_FIN_CA < SYSDATE AND C_EXP_ID in(select distinct(c_exp_id) from OCAP_EXPEDIENTESCAS where b_borrado = 'N') AND F_INFORME_EVAL is null) )AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N') AND b_borrado='N'";

			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();
			if (rs.next())
				contador = rs.getInt("contadorEvaluados");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return contador;
	}

	public int contarEvaluadosFQS(String reconoceGrado) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int contador = 0;
		try {
			String sql = "select count(distinct(c_exp_id)) as contadorEvaluados  from ocap_expedientescarrera oex  where     F_FIN_CA < SYSDATE AND C_EXP_ID in(select distinct(c_exp_id) from OCAP_EXPEDIENTESCAS where b_borrado = 'N')  AND c_exp_id IN (SELECT DISTINCT (c_exp_id) FROM ocap_creditoscuestionarios WHERE b_borrado = 'N')    AND F_INFORME_EVAL is not null     AND C_PROC_EVALUACION_ID NOT IN (4, 5)  AND b_borrado='N'          AND oex.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oex.c_itinerario_id                              AND occ2.c_convocatoria_id = oex.c_convocatoria_id                              AND occ2.c_compfqs_id = oex.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc, ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))  AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')";
			if (Constantes.SI.equals(reconoceGrado))
				sql = sql + " AND B_RECONOCIMIENTO_GRADO = 'Y' ";
			if (Constantes.NO.equals(reconoceGrado)) {
				sql = sql + " AND B_RECONOCIMIENTO_GRADO = 'N' ";
			}
			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();
			if (rs.next())
				contador = rs.getInt("contadorEvaluados");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return contador;
	}

	public int contarEvaluadosAutoevaluacion(String apto, String bFQS) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int contador = 0;
		try {
			String sql = "SELECT COUNT  (DISTINCT (c_exp_id)) as contadorEvaluados  FROM ocap_expedientescarrera oexp, ocap_itinerarios oi   WHERE oexp.b_borrado = 'N'      AND f_informe_eval IS NOT NULL      AND F_FIN_CA < SYSDATE      AND oexp.c_itinerario_id = oi.c_manual_evaluacion_id      AND c_exp_id IN (SELECT DISTINCT (c_exp_id) FROM ocap_expedientescas WHERE b_borrado = 'N')      AND c_exp_id IN (SELECT DISTINCT (c_exp_id) FROM ocap_creditoscuestionarios WHERE b_borrado = 'N')  AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')          AND oexp.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oexp.c_itinerario_id                              AND occ2.c_convocatoria_id = oexp.c_convocatoria_id                              AND occ2.c_compfqs_id = oexp.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc, ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))      AND (SELECT SUM (n_creditos)         FROM ocap_creditoscuestionarios occ         WHERE occ.c_exp_id = oexp.c_exp_id         AND occ.b_borrado = 'N'         AND occ.c_cuestionario_id IN (                      SELECT c_cuestionario_id                        FROM ocap_cuestionarios                       WHERE c_itinerario_id = oexp.c_itinerario_id                       AND c_cuestionario_id not in                       (select c_cuestionario_asociado_id from ocap_cuestionarios                       where c_itinerario_id = oexp.c_itinerario_id and b_borrado='N' and c_cuestionario_asociado_id is not null)) )";

			if (Constantes.SI.equals(apto))
				sql = sql + "   >= oi.n_creditos_necesarios ";
			if (Constantes.NO.equals(apto))
				sql = sql + "   < oi.n_creditos_necesarios ";
			if (Constantes.SI.equals(bFQS))
				sql = sql + "     AND C_PROC_EVALUACION_ID NOT IN (4, 5) ";
			if (Constantes.NO.equals(bFQS)) {
				sql = sql + "     AND C_PROC_EVALUACION_ID IN (4, 5) ";
			}
			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();
			if (rs.next())
				contador = rs.getInt("contadorEvaluados");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return contador;
	}

	public int contarEvaluadosGRS(String reconoceGrado) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int contador = 0;
		try {
			String sql = "select count(distinct(c_exp_id)) as contadorEvaluados  from ocap_expedientescarrera  where     F_FIN_CA < SYSDATE AND C_EXP_ID in(select distinct(c_exp_id) from OCAP_EXPEDIENTESCAS where b_borrado = 'N')      AND F_INFORME_EVAL is not null  AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')     AND b_borrado='N'    AND C_PROC_EVALUACION_ID IN (4, 5) ";
			if (Constantes.SI.equals(reconoceGrado))
				sql = sql + " AND B_RECONOCIMIENTO_GRADO = 'Y' ";
			if (Constantes.NO.equals(reconoceGrado)) {
				sql = sql + " AND B_RECONOCIMIENTO_GRADO = 'N' ";
			}
			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();
			if (rs.next())
				contador = rs.getInt("contadorEvaluados");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return contador;
	}

	public ArrayList buscarEvaluadosPorCategoria(String quienEvalua, int totalEvaluados) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "select oec.c_profesional_id, (select ocp.D_NOMBRE from ocap_categ_profesionales ocp      where ocp.c_profesional_id = oec.c_profesional_id) as profesionalNombre,  (SELECT COUNT  (DISTINCT (c_exp_id)) as contadorEvaluados  FROM ocap_expedientescarrera oexp, ocap_itinerarios oi   WHERE oexp.b_borrado = 'N'   and oec.C_profesional_ID = oexp.c_profesional_id      AND f_informe_eval IS NOT NULL ";

			if ("gere".equals(quienEvalua))
				sql = sql + " AND C_PROC_EVALUACION_ID IN (4, 5) ";
			if ("evalfqs".equals(quienEvalua)) {
				sql = sql + " AND C_PROC_EVALUACION_ID NOT IN (4, 5) ";
			}
			sql = sql
					+ "     AND F_FIN_CA < SYSDATE      AND oexp.c_itinerario_id = oi.c_manual_evaluacion_id      AND c_exp_id IN (SELECT DISTINCT (c_exp_id) FROM ocap_expedientescas WHERE b_borrado = 'N')      AND c_exp_id IN (SELECT DISTINCT (c_exp_id) FROM ocap_creditoscuestionarios WHERE b_borrado = 'N')      AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')     AND (SELECT SUM (n_creditos)         FROM ocap_creditoscuestionarios occ         WHERE occ.c_exp_id = oexp.c_exp_id         AND occ.b_borrado = 'N'         AND occ.c_cuestionario_id IN (                      SELECT c_cuestionario_id                        FROM ocap_cuestionarios                       WHERE c_itinerario_id = oexp.c_itinerario_id                       AND c_cuestionario_id not in                       (select c_cuestionario_asociado_id from ocap_cuestionarios                       where c_itinerario_id = oexp.c_itinerario_id and b_borrado='N' and c_cuestionario_asociado_id is not null)) )   >= oi.n_creditos_necesarios ) as totOK,  (SELECT COUNT  (DISTINCT (c_exp_id)) as contadorEvaluados  FROM ocap_expedientescarrera oexp, ocap_itinerarios oi   WHERE oexp.b_borrado = 'N'   and oec.C_profesional_ID = oexp.c_profesional_id      AND f_informe_eval IS NOT NULL ";
			if ("gere".equals(quienEvalua))
				sql = sql + " AND C_PROC_EVALUACION_ID IN (4, 5) ";
			if ("evalfqs".equals(quienEvalua))
				sql = sql + " AND C_PROC_EVALUACION_ID NOT IN (4, 5) ";
			sql = sql
					+ "     AND F_FIN_CA < SYSDATE      AND oexp.c_itinerario_id = oi.c_itinerario_id      AND c_exp_id IN (SELECT DISTINCT (c_exp_id) FROM ocap_expedientescas WHERE b_borrado = 'N')      AND c_exp_id IN (SELECT DISTINCT (c_exp_id) FROM ocap_creditoscuestionarios WHERE b_borrado = 'N')      AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')          AND oexp.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oexp.c_itinerario_id                              AND occ2.c_convocatoria_id = oexp.c_convocatoria_id                              AND occ2.c_compfqs_id = oexp.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc, ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))      AND (SELECT SUM (n_creditos)         FROM ocap_creditoscuestionarios occ         WHERE occ.c_exp_id = oexp.c_exp_id         AND occ.b_borrado = 'N'         AND occ.c_cuestionario_id IN (                      SELECT c_cuestionario_id                        FROM ocap_cuestionarios                       WHERE c_itinerario_id = oexp.c_itinerario_id                       AND c_cuestionario_id not in                       (select c_cuestionario_asociado_id from ocap_cuestionarios                       where c_itinerario_id = oexp.c_itinerario_id and b_borrado='N' and c_cuestionario_asociado_id is not null)) )   < oi.n_creditos_necesarios ) as totKO  FROM ocap_expedientescarrera oec, ocap_usuarios ou  WHERE oec.c_usr_id = ou.c_usr_id AND b_validacioncc = 'Y'  AND oec.F_FIN_CA < SYSDATE AND oec.C_EXP_ID in(select distinct(c_exp_id) from OCAP_EXPEDIENTESCAS where b_borrado = 'N')  AND oec.f_informe_eval is not null  AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')";
			if ("gere".equals(quienEvalua))
				sql = sql + " AND oec.C_PROC_EVALUACION_ID IN (4, 5) ";
			if ("evalfqs".equals(quienEvalua))
				sql = sql + " AND oec.C_PROC_EVALUACION_ID NOT IN (4, 5) ";
			sql = sql + " group by oec.c_profesional_id order by oec.c_profesional_id ";

			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				OCAPUsuariosOT result = new OCAPUsuariosOT();

				result.setCProfesionalId(new Integer(rs.getInt("c_profesional_id")));
				result.setDProfesional_nombre(rs.getString("profesionalNombre"));
				result.setTotalIndicadoresOK(rs.getInt("totOK"));
				result.setTotalIndicadoresKO(rs.getInt("totKO"));

				int total = rs.getInt("totOK") + rs.getInt("totKO");
				result.setTotalIndicadores(total);
				DecimalFormat formateador = new DecimalFormat("###.##%");
				result.setPorcentajeIndicadores(formateador.format(total / totalEvaluados));
				if (total != 0)
					result.setPorcentajeIndicadoresOK(formateador.format(rs.getInt("totOK") / total));
				else
					result.setPorcentajeIndicadoresOK(formateador.format(0L));
				if (total != 0)
					result.setPorcentajeIndicadoresKO(formateador.format(rs.getInt("totKO") / total));
				else {
					result.setPorcentajeIndicadoresKO(formateador.format(0L));
				}
				listado.add(result);
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

	public int contarEvaluadoresFQS() throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int contador = 0;
		try {
			String sql = "select count(distinct(com.c_compfqs_id)) as nEvaluadoresFqsAsignados   FROM ocap_expedientescarrera EXP, ocap_componentesfqs com   WHERE EXP.b_borrado = 'N' AND com.b_borrado = 'N'      AND EXP.c_compfqs_id = com.c_compfqs_id      AND com.c_compfqs_id in         ( select c_compfqs_id from ocap_compfqs_convocatorias occ         where occ.c_cte_id IN            (select oc.c_cte_id from ocap_ctes oc, ocap_ctesactivados oca where oc.c_cte_id = oca.c_cte_id and oc.b_borrado='N' and oca.b_borrado='N' )        )";

			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();
			if (rs.next())
				contador = rs.getInt("nEvaluadoresFqsAsignados");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return contador;
	}

	public int contarEvaluadoresBajaFQS() throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int contador = 0;
		try {
			String sql = "select count(distinct(com.c_compfqs_id)) as nEvaluadoresFqsBaja   FROM ocap_componentesfqs com  WHERE com.b_borrado = 'N'      AND com.c_compfqs_id Not in (select distinct(c_compfqs_id) from ocap_expedientescarrera where b_borrado='N')      AND com.c_compfqs_id in         ( select c_compfqs_id from ocap_compfqs_convocatorias occ         where occ.c_cte_id IN            (select oc.c_cte_id from ocap_ctes oc, ocap_ctesactivados oca where oc.c_cte_id = oca.c_cte_id and oc.b_borrado='N' and oca.b_borrado='N' )        )";

			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();
			if (rs.next())
				contador = rs.getInt("nEvaluadoresFqsBaja");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return contador;
	}

	public int contarEvaluadoresFinFQS() throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int contador = 0;
		try {
			String sql = "select count(distinct(com.c_compfqs_id)) as nEvaluadoresFinalizados   FROM ocap_expedientescarrera EXP, ocap_componentesfqs com   WHERE EXP.b_borrado = 'N' AND com.b_borrado = 'N'      AND EXP.c_compfqs_id = com.c_compfqs_id      AND com.c_compfqs_id in         (select c_compfqs_id from ocap_compfqs_convocatorias occ         where occ.c_cte_id IN            (select oc.c_cte_id from ocap_ctes oc, ocap_ctesactivados oca where oc.c_cte_id = oca.c_cte_id and oc.b_borrado='N' and oca.b_borrado='N' )         )      AND exp.f_informe_eval is not null      AND exp.c_compfqs_id not in (select c_compfqs_id from ocap_expedientescarrera where b_borrado='N' and f_informe_eval is null and c_compfqs_id is not null) ";

			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();
			if (rs.next())
				contador = rs.getInt("nEvaluadoresFinalizados");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return contador;
	}

	public ArrayList buscarEvaluadoresPorCategoria(int totalEvaluadores) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		DecimalFormat formateador = new DecimalFormat("###.##%");
		ArrayList listado = null;
		ArrayList listadoCateg = null;
		ArrayList listadoTotal = null;
		try {
			String sql = "select oe.c_personal_id, count(distinct(occ.c_compfqs_id)) as nEvaluadoresFqs,  (select d_nombre from ocap_pers_estatutario where c_personal_id = oe.c_personal_id) as persNombre  FROM ocap_componentesfqs com, ocap_compfqs_convocatorias occ,   ocap_categ_profesionales ocp, ocap_estatutario oe, ocap_expedientescarrera EXP  WHERE com.b_borrado = 'N' AND com.c_compfqs_id in      (select c_compfqs_id from ocap_compfqs_convocatorias occ         where occ.c_cte_id IN         (select oc.c_cte_id from ocap_ctes oc, ocap_ctesactivados oca where oc.c_cte_id = oca.c_cte_id and oc.b_borrado='N' and oca.b_borrado='N')         and occ.c_convocatoria_id = exp.c_convocatoria_id and occ.c_itinerario_id = exp.c_itinerario_id      )   AND com.C_PERFIL_ID = 3 AND EXP.c_compfqs_id = com.c_compfqs_id   AND com.c_compfqs_id = occ.c_compfqs_id   AND ocp.c_profesional_id = exp.c_profesional_id   AND ocp.C_ESTATUT_ID = oe.c_estatut_id  group by oe.c_personal_id order by oe.c_personal_id ";

			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				OCAPUsuariosOT result = new OCAPUsuariosOT();

				result.setCEstatutId(new Integer(rs.getInt("c_personal_id")));
				result.setDEstatut_nombre(rs.getString("persNombre"));
				result.setTotalIndicadores(rs.getInt("nEvaluadoresFqs"));
				result.setPorcentajeIndicadores(formateador.format(rs.getInt("nEvaluadoresFqs") / totalEvaluadores));

				listado.add(result);
			}

			listadoTotal = new ArrayList();

			for (int i = 0; i < listado.size(); i++) {
				OCAPUsuariosOT usuOT = (OCAPUsuariosOT) listado.get(i);

				sql = " SELECT   COUNT (DISTINCT (com.c_compfqs_id)) AS nevaluadoresfqs,       ocp.c_profesional_id,       (SELECT d_nombre FROM ocap_categ_profesionales WHERE c_profesional_id = ocp.c_profesional_id) AS profnombre,       (SELECT COUNT (c_exp_id) FROM ocap_expedientescarrera oexp WHERE oexp.c_profesional_id = ocp.c_profesional_id           AND oexp.C_PROC_EVALUACION_ID NOT IN (4, 5)           AND oexp.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oexp.c_itinerario_id                              AND occ2.c_convocatoria_id = oexp.c_convocatoria_id                              AND occ2.c_compfqs_id = oexp.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc, ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))           AND f_informe_eval IS NOT NULL           AND c_exp_id IN (SELECT DISTINCT (c_exp_id) FROM ocap_expedientescas WHERE b_borrado = 'N' AND c_exp_id = oexp.c_exp_id)           AND c_exp_id IN (SELECT DISTINCT (c_exp_id) FROM ocap_creditoscuestionarios WHERE b_borrado = 'N' AND c_exp_id = oexp.c_exp_id)           AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N') ) AS nevaluados,       (SELECT COUNT (DISTINCT (c_exp_id))          FROM ocap_expedientescarrera oexp,               ocap_itinerarios oi         WHERE oexp.b_borrado = 'N'           AND ocp.c_profesional_id = oexp.c_profesional_id           AND oexp.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oexp.c_itinerario_id                              AND occ2.c_convocatoria_id = oexp.c_convocatoria_id                              AND occ2.c_compfqs_id = oexp.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc, ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))           AND f_informe_eval IS NOT NULL           AND oexp.c_itinerario_id = oi.c_itinerario_id           AND oexp.C_PROC_EVALUACION_ID NOT IN (4, 5)           AND c_exp_id IN (SELECT DISTINCT (c_exp_id) FROM ocap_expedientescas WHERE b_borrado = 'N' AND c_exp_id = oexp.c_exp_id)           AND c_exp_id IN (SELECT DISTINCT (c_exp_id) FROM ocap_creditoscuestionarios WHERE b_borrado = 'N' AND c_exp_id = oexp.c_exp_id)           AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')           AND (SELECT SUM (n_creditos_evaluador)                  FROM ocap_creditoscuestionarios occ                 WHERE occ.c_exp_id = oexp.c_exp_id                   AND occ.b_borrado = 'N'                   AND occ.c_cuestionario_id IN (                                  SELECT c_cuestionario_id                                    FROM ocap_cuestionarios                                   WHERE c_itinerario_id = oi.c_itinerario_id)) >= oi.n_creditos_necesarios)   AS nevaluadosok,       (SELECT COUNT (DISTINCT (c_exp_id))          FROM ocap_expedientescarrera oexp,               ocap_itinerarios oi         WHERE oexp.b_borrado = 'N'           AND ocp.c_profesional_id = oexp.c_profesional_id           AND oexp.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oexp.c_itinerario_id                              AND occ2.c_convocatoria_id = oexp.c_convocatoria_id                              AND occ2.c_compfqs_id = oexp.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc,                                                     ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))           AND oexp.C_PROC_EVALUACION_ID NOT IN (4, 5)           AND f_informe_eval IS NOT NULL           AND oexp.c_itinerario_id = oi.c_itinerario_id           AND c_exp_id IN (SELECT DISTINCT (c_exp_id) FROM ocap_expedientescas WHERE b_borrado = 'N' AND c_exp_id = oexp.c_exp_id)           AND c_exp_id IN (SELECT DISTINCT (c_exp_id) FROM ocap_creditoscuestionarios WHERE b_borrado = 'N' AND c_exp_id = oexp.c_exp_id)           AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')           AND (SELECT SUM (n_creditos_evaluador)                  FROM ocap_creditoscuestionarios occ                 WHERE occ.c_exp_id = oexp.c_exp_id                   AND occ.b_borrado = 'N'                   AND occ.c_cuestionario_id IN (                                  SELECT c_cuestionario_id                                    FROM ocap_cuestionarios                                   WHERE c_itinerario_id = oi.c_itinerario_id)) < oi.n_creditos_necesarios)   AS nevaluadosko,       (SELECT COUNT (DISTINCT (c_exp_id))          FROM ocap_expedientescarrera oexp         WHERE oexp.b_borrado = 'N'           AND ocp.c_profesional_id = oexp.c_profesional_id           AND oexp.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oexp.c_itinerario_id                              AND occ2.c_convocatoria_id = oexp.c_convocatoria_id                              AND occ2.c_compfqs_id = oexp.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc,                                                     ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))           AND oexp.C_PROC_EVALUACION_ID NOT IN (4, 5)           AND f_informe_eval IS NOT NULL           AND c_exp_id IN (SELECT DISTINCT (c_exp_id) FROM ocap_expedientescas WHERE b_borrado = 'N' AND c_exp_id = oexp.c_exp_id)           AND c_exp_id IN (SELECT DISTINCT (c_exp_id) FROM ocap_creditoscuestionarios WHERE b_borrado = 'N' AND c_exp_id = oexp.c_exp_id)           AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')           AND oexp.c_exp_id IN (                  SELECT DISTINCT (c_exp_id)                             FROM ocap_creditoscuestionarios                            WHERE b_acuerdo != 'N'                              AND b_acuerdo IS NOT NULL                              AND c_exp_id = oexp.c_exp_id)           AND ((SELECT COUNT (c_ccuestionario_id)                   FROM ocap_creditoscuestionarios                  WHERE c_exp_id = oexp.c_exp_id AND b_acuerdo = 'N') = 0))   AS nevaluadosconf,       (SELECT COUNT (DISTINCT (c_exp_id))          FROM ocap_expedientescarrera oexp         WHERE oexp.b_borrado = 'N'           AND ocp.c_profesional_id = oexp.c_profesional_id           AND oexp.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oexp.c_itinerario_id                              AND occ2.c_convocatoria_id = oexp.c_convocatoria_id                              AND occ2.c_compfqs_id = oexp.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc,                                                     ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))           AND oexp.C_PROC_EVALUACION_ID NOT IN (4, 5)           AND f_informe_eval IS NOT NULL           AND c_exp_id IN (SELECT DISTINCT (c_exp_id) FROM ocap_expedientescas WHERE b_borrado = 'N' AND c_exp_id = oexp.c_exp_id)           AND c_exp_id IN (SELECT DISTINCT (c_exp_id) FROM ocap_creditoscuestionarios WHERE b_borrado = 'N' AND c_exp_id = oexp.c_exp_id)           AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')           AND oexp.c_exp_id IN (                          SELECT DISTINCT (c_exp_id)                                     FROM ocap_creditoscuestionarios                                    WHERE b_acuerdo = 'N'                                      AND c_exp_id = oexp.c_exp_id))    AS nevaluadosnoconf  FROM ocap_componentesfqs com,       ocap_compfqs_convocatorias occ,       ocap_categ_profesionales ocp,       ocap_estatutario oe,       ocap_expedientescarrera EXP  WHERE com.b_borrado = 'N'   AND com.c_compfqs_id IN (          SELECT c_compfqs_id            FROM ocap_compfqs_convocatorias occ           WHERE occ.c_cte_id IN (                    SELECT oc.c_cte_id                      FROM ocap_ctes oc, ocap_ctesactivados oca                     WHERE oc.c_cte_id = oca.c_cte_id                       AND oc.b_borrado = 'N'                       AND oca.b_borrado = 'N'))   AND com.c_perfil_id = 3   AND EXP.c_compfqs_id = com.c_compfqs_id   AND com.c_compfqs_id = occ.c_compfqs_id   AND ocp.c_profesional_id = oexp.c_profesional_id   AND ocp.c_estatut_id = oe.c_estatut_id  AND oe.c_personal_id = ?  group by ocp.c_profesional_id order by ocp.c_profesional_id ";

				st = con.prepareStatement(sql, 1004, 1008);
				st.setInt(1, usuOT.getCEstatutId().intValue());
				rs = st.executeQuery();
				listadoCateg = new ArrayList();
				while (rs.next()) {
					OCAPUsuariosOT result = new OCAPUsuariosOT();

					result.setCProfesionalId(new Integer(rs.getInt("c_profesional_id")));
					result.setDProfesional_nombre(rs.getString("profNombre"));
					result.setTotalIndicadores(rs.getInt("nEvaluadoresFqs"));
					result.setTotalIndicador(rs.getInt("nEvaluados"));
					result.setPorcentajeIndicadores(
							formateador.format(rs.getInt("nEvaluadoresFqs") / usuOT.getTotalIndicadores()));
					result.setTotalIndicadorOK(rs.getInt("nEvaluadosOK"));
					if (rs.getInt("nEvaluados") != 0)
						result.setPorcentajeIndicadoresOK(
								formateador.format(rs.getInt("nEvaluadosOK") / rs.getInt("nEvaluados")));
					else
						result.setPorcentajeIndicadoresOK(formateador.format(0L));
					result.setTotalIndicadorKO(rs.getInt("nEvaluadosKO"));
					if (rs.getInt("nEvaluados") != 0)
						result.setPorcentajeIndicadoresKO(
								formateador.format(rs.getInt("nEvaluadosKO") / rs.getInt("nEvaluados")));
					else {
						result.setPorcentajeIndicadoresKO(formateador.format(0L));
					}
					result.setTotalIndicadoresModOK(rs.getInt("nEvaluadosConf"));
					if (rs.getInt("nEvaluados") != 0)
						result.setPorcentajeIndicadoresModOK(
								formateador.format(rs.getInt("nEvaluadosConf") / rs.getInt("nEvaluados")));
					else
						result.setPorcentajeIndicadoresModOK(formateador.format(0L));
					result.setTotalIndicadoresModKO(rs.getInt("nEvaluadosNoConf"));
					if (rs.getInt("nEvaluados") != 0)
						result.setPorcentajeIndicadoresModKO(
								formateador.format(rs.getInt("nEvaluadosNoConf") / rs.getInt("nEvaluados")));
					else {
						result.setPorcentajeIndicadoresModKO(formateador.format(0L));
					}
					if (rs.getInt("nEvaluados") != 0)
						listadoCateg.add(result);
				}
				usuOT.setListaCategorias(listadoCateg);
				listadoTotal.add(usuOT);
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

		return listadoTotal;
	}

	public ArrayList buscarEvaluadosPorItinerarioPorEvaluador() throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		DecimalFormat formateador = new DecimalFormat("###.##%");
		ArrayList listado = null;
		ArrayList listadoCateg = null;
		ArrayList listadoTotal = null;
		try {
			String sql = "SELECT DISTINCT (com.c_compfqs_id)      , (select d_nombre from ocap_componentesfqs where c_compfqs_id = com.c_compfqs_id) as evalNombre      , (select d_apellidos from ocap_componentesfqs where c_compfqs_id = com.c_compfqs_id) as evalApellidos      , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oexp          where c_compfqs_id = com.c_compfqs_id               AND C_PROC_EVALUACION_ID NOT IN (4, 5)              AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')           AND oexp.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oexp.c_itinerario_id                              AND occ2.c_convocatoria_id = oexp.c_convocatoria_id                              AND occ2.c_compfqs_id = oexp.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc, ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))              and f_informe_eval is not null        ) as nEvaluados     , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oex           where oex.c_compfqs_id = com.c_compfqs_id               AND oex.C_PROC_EVALUACION_ID NOT IN (4, 5)              AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')           AND oex.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oex.c_itinerario_id                              AND occ2.c_convocatoria_id = oex.c_convocatoria_id                              AND occ2.c_compfqs_id = oex.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc, ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))              and oex.f_informe_eval is not null              and oex.c_exp_id in (select distinct(c_exp_id) from ocap_creditoscuestionarios where b_acuerdo!='N' AND b_Acuerdo is not null AND c_exp_id = oex.c_exp_id)              AND ((SELECT count(c_ccuestionario_id) from ocap_creditoscuestionarios WHERE c_exp_id = oex.c_exp_id AND b_acuerdo ='N') = 0)        ) as nEvaluadosConf      , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oex           where oex.c_compfqs_id = com.c_compfqs_id               AND oex.C_PROC_EVALUACION_ID NOT IN (4, 5)              AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')           AND oex.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oex.c_itinerario_id                              AND occ2.c_convocatoria_id = oex.c_convocatoria_id                              AND occ2.c_compfqs_id = oex.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc, ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))              and oex.f_informe_eval is not null              and oex.c_exp_id in (select distinct(c_exp_id) from ocap_creditoscuestionarios where b_acuerdo='N' AND c_exp_id = oex.c_exp_id)        ) as nEvaluadosNoConf      , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oex           where oex.c_compfqs_id = com.c_compfqs_id               AND oex.C_PROC_EVALUACION_ID NOT IN (4, 5)              AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')           AND oex.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oex.c_itinerario_id                              AND occ2.c_convocatoria_id = oex.c_convocatoria_id                              AND occ2.c_compfqs_id = oex.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc, ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))              and oex.f_informe_eval is not null              and oex.c_exp_id in (select distinct(c_exp_id) from ocap_creditoscuestionarios where b_proposicion='A' AND c_exp_id = oex.c_exp_id)        ) as nEvaluadosAuditados  FROM ocap_expedientescarrera EXP, ocap_componentesfqs com    , ocap_compfqs_convocatorias occc   WHERE EXP.b_borrado = 'N'     AND com.b_borrado = 'N'     AND EXP.c_compfqs_id = com.c_compfqs_id     AND com.c_compfqs_id IN (            SELECT c_compfqs_id              FROM ocap_compfqs_convocatorias occ             WHERE occ.c_cte_id IN (                      SELECT oc.c_cte_id                        FROM ocap_ctes oc, ocap_ctesactivados oca                       WHERE oc.c_cte_id = oca.c_cte_id                         AND oc.b_borrado = 'N'                         AND oca.b_borrado = 'N'))    AND occc.b_borrado = 'N'    AND occc.c_compfqs_id = com.c_compfqs_id    AND exp.C_PROC_EVALUACION_ID NOT IN (4, 5)  group by com.c_compfqs_id  ORDER BY com.c_compfqs_id";

			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				OCAPUsuariosOT result = new OCAPUsuariosOT();

				result.setDApellido1(rs.getString("evalApellidos"));
				result.setDNombre(rs.getString("evalNombre"));
				result.setCCompfqsId(rs.getLong("c_compfqs_id"));
				DecimalFormat formato = new DecimalFormat("000000");
				result.setCodigoId("REX" + formato.format(rs.getLong("c_compfqs_id")));
				result.setTotalIndicador(rs.getInt("nEvaluados"));
				result.setTotalEvaluadosMod(rs.getInt("nEvaluadosConf"));
				result.setTotalEvaluadosNoMod(rs.getInt("nEvaluadosNoConf"));
				result.setTotalEvaluados(rs.getInt("nEvaluadosAuditados"));

				listado.add(result);
			}

			listadoTotal = new ArrayList();
			for (int i = 0; i < listado.size(); i++) {
				OCAPUsuariosOT usuOT = (OCAPUsuariosOT) listado.get(i);
				sql = "SELECT DISTINCT (com.c_compfqs_id) as c_compfqs_id , (select d_nombre from ocap_componentesfqs where c_compfqs_id = com.c_compfqs_id) as evalNombre , (select d_apellidos from ocap_componentesfqs where c_compfqs_id = com.c_compfqs_id) as evalApellidos , occc.c_itinerario_id, (select d_descripcion from ocap_itinerarios where c_itinerario_id = occc.c_itinerario_id) as dItinerario  , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oex     where c_compfqs_id = com.c_compfqs_id          AND C_PROC_EVALUACION_ID NOT IN (4, 5)         and c_itinerario_id = occc.c_itinerario_id         and f_informe_eval is not null         AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')           AND oex.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oex.c_itinerario_id                              AND occ2.c_convocatoria_id = oex.c_convocatoria_id                              AND occ2.c_compfqs_id = oex.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc, ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))   ) as nEvaluados , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oex      where c_compfqs_id = com.c_compfqs_id          AND oex.C_PROC_EVALUACION_ID NOT IN (4, 5)         and c_itinerario_id = occc.c_itinerario_id           AND oex.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oex.c_itinerario_id                              AND occ2.c_convocatoria_id = oex.c_convocatoria_id                              AND occ2.c_compfqs_id = oex.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc, ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))         AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')         and ((select sum(n_creditos_evaluador) from ocap_creditoscuestionarios                  where c_exp_id=oex.c_exp_id               )              >=             (select n_creditos_necesarios from ocap_itinerarios where c_itinerario_id = oex.c_itinerario_id)             )   ) as nEvaluadosOK , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oex      where c_compfqs_id = com.c_compfqs_id          AND oex.C_PROC_EVALUACION_ID NOT IN (4, 5)         and c_itinerario_id = occc.c_itinerario_id           AND oex.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oex.c_itinerario_id                              AND occ2.c_convocatoria_id = oex.c_convocatoria_id                              AND occ2.c_compfqs_id = oex.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc, ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))         AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')         and ((select sum(n_creditos_evaluador) from ocap_creditoscuestionarios                  where c_exp_id=oex.c_exp_id               )              <             (select n_creditos_necesarios from ocap_itinerarios where c_itinerario_id = oex.c_itinerario_id)             )   ) as nEvaluadosKO , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oex      where oex.c_compfqs_id = com.c_compfqs_id          AND oex.C_PROC_EVALUACION_ID NOT IN (4, 5)         and oex.c_itinerario_id = occc.c_itinerario_id           AND oex.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oex.c_itinerario_id                              AND occ2.c_convocatoria_id = oex.c_convocatoria_id                              AND occ2.c_compfqs_id = oex.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc, ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))         and oex.f_informe_eval is not null         AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')         and oex.c_exp_id in (select distinct(c_exp_id) from ocap_creditoscuestionarios where b_acuerdo!='N' AND b_Acuerdo is not null AND c_exp_id = oex.c_exp_id)         AND ((SELECT count(c_ccuestionario_id) from ocap_creditoscuestionarios WHERE c_exp_id = oex.c_exp_id AND b_acuerdo ='N') = 0)   ) as nEvaluadosConf , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oex      where oex.c_compfqs_id = com.c_compfqs_id          AND oex.C_PROC_EVALUACION_ID NOT IN (4, 5)         and oex.c_itinerario_id = occc.c_itinerario_id           AND oex.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oex.c_itinerario_id                              AND occ2.c_convocatoria_id = oex.c_convocatoria_id                              AND occ2.c_compfqs_id = oex.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc, ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))         and oex.f_informe_eval is not null         AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')         and oex.c_exp_id in (select distinct(c_exp_id) from ocap_creditoscuestionarios where b_acuerdo='N' AND c_exp_id = oex.c_exp_id)   ) as nEvaluadosNoConf FROM ocap_expedientescarrera EXP, ocap_componentesfqs com , ocap_compfqs_convocatorias occc WHERE EXP.b_borrado = 'N'   AND com.b_borrado = 'N'   AND EXP.c_compfqs_id = com.c_compfqs_id   AND com.c_compfqs_id IN (          SELECT c_compfqs_id            FROM ocap_compfqs_convocatorias occ           WHERE occ.c_cte_id IN (                    SELECT oc.c_cte_id                      FROM ocap_ctes oc, ocap_ctesactivados oca                     WHERE oc.c_cte_id = oca.c_cte_id                       AND oc.b_borrado = 'N'                       AND oca.b_borrado = 'N'))  AND occc.b_borrado = 'N'  AND occc.c_compfqs_id = com.c_compfqs_id  AND exp.C_PROC_EVALUACION_ID NOT IN (4, 5)  AND exp.c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')   AND occc.c_compfqs_id = ?  ORDER BY evalNombre, occc.c_itinerario_id";

				st = con.prepareStatement(sql, 1004, 1008);
				st.setLong(1, usuOT.getCCompfqsId());
				rs = st.executeQuery();

				listadoCateg = new ArrayList();
				while (rs.next()) {
					OCAPUsuariosOT result = new OCAPUsuariosOT();

					if (rs.getInt("nEvaluados") != 0) {
						result.setDApellido1(rs.getString("evalApellidos"));
						result.setDNombre(rs.getString("evalNombre"));
						result.setCCompfqsId(rs.getLong("c_compfqs_id"));
						result.setCItinerarioId(rs.getLong("c_itinerario_id"));
						result.setDItinerario(rs.getString("dItinerario"));
						result.setTotalIndicadores(rs.getInt("nEvaluados"));
						result.setTotalIndicadoresOK(rs.getInt("nEvaluadosOK"));
						result.setTotalIndicadoresKO(rs.getInt("nEvaluadosKO"));
						result.setTotalIndicadoresModOK(rs.getInt("nEvaluadosConf"));
						result.setTotalIndicadoresModKO(rs.getInt("nEvaluadosNoConf"));

						result.setPorcentajeIndicadores(
								formateador.format(rs.getInt("nEvaluados") / usuOT.getTotalIndicador()));
						result.setPorcentajeIndicadoresOK(
								formateador.format(rs.getInt("nEvaluadosOK") / rs.getInt("nEvaluados")));
						result.setPorcentajeIndicadoresKO(
								formateador.format(rs.getInt("nEvaluadosKO") / rs.getInt("nEvaluados")));
						result.setPorcentajeIndicadoresModOK(
								formateador.format(rs.getInt("nEvaluadosConf") / rs.getInt("nEvaluados")));
						result.setPorcentajeIndicadoresModKO(
								formateador.format(rs.getInt("nEvaluadosNoConf") / rs.getInt("nEvaluados")));

						listadoCateg.add(result);
					}
				}
				usuOT.setListaCategorias(listadoCateg);
				listadoTotal.add(usuOT);
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

		return listadoTotal;
	}

	public ArrayList buscarAuditoriasPorCategoria(int totalAuditorias) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "select distinct(oex.c_profesional_id), count(distinct(c_exp_id)) as nAuditorias  , (select d_nombre from ocap_categ_profesionales where c_profesional_id = oex.c_profesional_id) as profesionalNombre   , oex.c_espec_id, (select d_nombre from ocap_especialidades where c_espec_id = oex.c_espec_id) as especNombre  from ocap_expedientescarrera oex, ocap_usuarios ou   where oex.c_usr_id = ou.c_usr_id      AND oex.C_PROC_EVALUACION_ID NOT IN (4, 5)      and oex.f_informe_eval is not null      and oex.c_exp_id in (select distinct(c_exp_id) from ocap_creditoscuestionarios where b_proposicion='A' AND c_exp_id = oex.c_exp_id)      AND oex.c_compfqs_id IN (SELECT c_compfqs_id FROM ocap_compfqs_convocatorias occ         WHERE occ.c_cte_id IN (SELECT oc.c_cte_id FROM ocap_ctes oc, ocap_ctesactivados oca            WHERE oc.c_cte_id = oca.c_cte_id AND oc.b_borrado = 'N' AND oca.b_borrado = 'N'))      AND oex.c_itinerario_id IN (SELECT c_itinerario_id FROM ocap_compfqs_convocatorias WHERE b_borrado = 'N' AND c_compfqs_id = oex.c_compfqs_id)      AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')  group by oex.c_profesional_id, oex.c_espec_id  order by oex.c_profesional_id, oex.c_espec_id";

			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				OCAPUsuariosOT result = new OCAPUsuariosOT();

				result.setCProfesionalId(new Integer(rs.getInt("c_profesional_id")));
				result.setDProfesional_nombre(rs.getString("profesionalNombre"));
				result.setCEspecId(new Integer(rs.getInt("c_espec_id")));
				result.setDEspec_nombre(rs.getString("especNombre"));
				if ((rs.getString("especNombre") != null) && (!"".equals(rs.getString("especNombre"))))
					result.setDProfesional_nombre(result.getDProfesional_nombre() + " / " + result.getDEspec_nombre());
				result.setTotalIndicadores(rs.getInt("nAuditorias"));
				DecimalFormat formateador = new DecimalFormat("###.##%");
				result.setPorcentajeIndicadores(formateador.format(rs.getInt("nAuditorias") / totalAuditorias));

				listado.add(result);
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

	public int contarInformesFQS() throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int contador = 0;
		try {
			String sql = "select count(c_exp_id) as nInformes from ocap_expedientescarrera where b_borrado='N' AND f_informe_cc is not null  AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N') ";

			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();
			if (rs.next())
				contador = rs.getInt("nInformes");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return contador;
	}

	public int contarInformesCE(String bConforme) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int contador = 0;
		try {
			String sql = "select count(c_exp_id) as nInformesCE from ocap_expedientescarrera oex where b_borrado='N' AND f_informe_ce is not null    AND C_PROC_EVALUACION_ID NOT IN (4, 5)           AND oex.c_compfqs_id IN (                  SELECT DISTINCT (c_compfqs_id)                             FROM ocap_compfqs_convocatorias occ2                            WHERE b_borrado = 'N'                              AND occ2.c_itinerario_id = oex.c_itinerario_id                              AND occ2.c_convocatoria_id = oex.c_convocatoria_id                              AND occ2.c_compfqs_id = oex.c_compfqs_id                              AND c_cte_id IN (                                     SELECT DISTINCT (oc.c_cte_id)                                                FROM ocap_ctes oc, ocap_ctesactivados oca                                               WHERE oc.c_cte_id = oca.c_cte_id                                                 AND oc.b_borrado = 'N'                                                 AND oca.b_borrado = 'N'))  AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N') ";
			if (bConforme != null) {
				sql = sql + "AND B_DECISION_CE ='" + bConforme + "'";
			}
			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();
			if (rs.next())
				contador = rs.getInt("nInformesCE");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return contador;
	}

	public ArrayList buscarEvaluadosPorCE(int totalInformes, String bConforme) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "select distinct(c_cte_id), count(distinct(c_exp_id)) as nEvaluados, (select d_nombre from ocap_ctes where c_cte_id = occ.c_cte_id) as nombreCTE  from ocap_compfqs_convocatorias occ, ocap_expedientescarrera oex   where occ.b_borrado='N' AND oex.b_borrado='N'    AND oex.f_informe_ce is not null    AND oex.c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')    AND oex.c_convocatoria_id = occ.c_convocatoria_id    AND oex.B_DECISION_CE ='"
					+ bConforme + "' " + "   AND oex.c_compfqs_id= occ.c_compfqs_id "
					+ "   AND oex.C_PROC_EVALUACION_ID NOT IN (4, 5) "
					+ "   AND oex.c_itinerario_id= occ.c_itinerario_id "
					+ "   AND oex.c_itinerario_id IN (select c_itinerario_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id AND oex.c_compfqs_id= occ.c_compfqs_id) "
					+ "          AND oex.c_compfqs_id IN ( " + "                 SELECT DISTINCT (c_compfqs_id) "
					+ "                            FROM ocap_compfqs_convocatorias occ2 "
					+ "                           WHERE b_borrado = 'N' "
					+ "                             AND occ2.c_itinerario_id = oex.c_itinerario_id "
					+ "                             AND occ2.c_convocatoria_id = oex.c_convocatoria_id "
					+ "                             AND occ2.c_compfqs_id = oex.c_compfqs_id "
					+ "                             AND c_cte_id IN ( "
					+ "                                    SELECT DISTINCT (oc.c_cte_id) "
					+ "                                               FROM ocap_ctes oc, ocap_ctesactivados oca "
					+ "                                              WHERE oc.c_cte_id = oca.c_cte_id "
					+ "                                                AND oc.b_borrado = 'N' "
					+ "                                                AND oca.b_borrado = 'N')) "
					+ "   AND oex.c_compfqs_id IN (select c_compfqs_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id) "
					+ " group by c_cte_id order by nombreCTE ";

			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				OCAPUsuariosOT result = new OCAPUsuariosOT();

				result.setCCteId(rs.getLong("c_cte_id"));
				result.setDNombreCte(rs.getString("nombreCTE"));
				result.setTotalIndicadores(rs.getInt("nEvaluados"));
				DecimalFormat formateador = new DecimalFormat("###.##%");
				result.setPorcentajeIndicadores(formateador.format(rs.getInt("nEvaluados") / totalInformes));

				listado.add(result);
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

	public ArrayList buscarEvaluadosPorCategoriaPorCTE() throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		DecimalFormat formateador = new DecimalFormat("###.##%");
		ArrayList listado = null;
		ArrayList listadoCateg = null;
		ArrayList listadoTotal = null;
		try {
			String sql = "select distinct(c_cte_id)  , (select d_nombre from ocap_ctes where c_cte_id = occ.c_cte_id) as nombreCTE  , (select count(distinct(c_exp_id)) from ocap_expedientescarrera       where c_compfqs_id in (select c_compfqs_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id)           AND C_PROC_EVALUACION_ID NOT IN (4, 5)          AND c_convocatoria_id in (select c_convocatoria_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id)          AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')          and c_itinerario_id in (select c_itinerario_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id)          and f_informe_cte is not null    ) as nEvaluados  , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oex       where c_compfqs_id in (select c_compfqs_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id)           AND oex.C_PROC_EVALUACION_ID NOT IN (4, 5)          AND c_convocatoria_id in (select c_convocatoria_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id)          AND oex.c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')          and c_itinerario_id in (select c_itinerario_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id)          and f_informe_cte is not null          and ((select sum(n_creditos_evaluador) from ocap_creditoscuestionarios                   where c_exp_id=oex.c_exp_id                )               >=              (select n_creditos_necesarios from ocap_itinerarios where c_itinerario_id = oex.c_itinerario_id)              )    ) as nEvaluadosOK  , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oex       where c_compfqs_id in (select c_compfqs_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id)           AND oex.C_PROC_EVALUACION_ID NOT IN (4, 5)          AND c_convocatoria_id in (select c_convocatoria_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id)          AND oex.c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')          and c_itinerario_id in (select c_itinerario_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id)          and f_informe_cte is not null          and ((select sum(n_creditos_evaluador) from ocap_creditoscuestionarios                   where c_exp_id=oex.c_exp_id                )               <              (select n_creditos_necesarios from ocap_itinerarios where c_itinerario_id = oex.c_itinerario_id)              )    ) as nEvaluadosKO  from ocap_compfqs_convocatorias occ  where b_borrado='N'   AND c_compfqs_id in (select distinct(c_compfqs_id) from ocap_expedientescarrera where b_borrado='N'                      and C_PROC_EVALUACION_ID NOT IN (4, 5) and f_informe_cte is not null                     AND c_convocatoria_id = occ.c_convocatoria_id                     AND c_compfqs_id = occ.c_compfqs_id                     AND c_itinerario_id = occ.c_itinerario_id                     )   AND occ.c_cte_id IN (          SELECT oc.c_cte_id            FROM ocap_ctes oc, ocap_ctesactivados oca           WHERE oc.c_cte_id = oca.c_cte_id             AND oc.b_borrado = 'N'             AND oca.b_borrado = 'N')  group by c_cte_id order by nombreCTE";

			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				OCAPUsuariosOT result = new OCAPUsuariosOT();

				result.setCCteId(rs.getLong("c_cte_id"));
				result.setDNombreCte(rs.getString("nombreCTE"));
				result.setTotalIndicadores(rs.getInt("nEvaluados"));
				result.setTotalIndicadoresOK(rs.getInt("nEvaluadosOK"));
				result.setTotalIndicadoresKO(rs.getInt("nEvaluadosKO"));
				result.setPorcentajeIndicadoresOK(
						formateador.format(rs.getInt("nEvaluadosOK") / rs.getInt("nEvaluados")));
				result.setPorcentajeIndicadoresKO(
						formateador.format(rs.getInt("nEvaluadosKO") / rs.getInt("nEvaluados")));

				listado.add(result);
			}

			listadoTotal = new ArrayList();
			for (int i = 0; i < listado.size(); i++) {
				OCAPUsuariosOT usuOT = (OCAPUsuariosOT) listado.get(i);

				sql = "SELECT DISTINCT (oex.c_profesional_id),              COUNT (DISTINCT (c_exp_id)) AS nEvaluados             , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oex2              where c_compfqs_id in (select c_compfqs_id from ocap_compfqs_convocatorias where c_cte_id = ?)                  AND oex2.C_PROC_EVALUACION_ID NOT IN (4, 5)                 AND oex2.c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')                 and c_itinerario_id in (select c_itinerario_id from ocap_compfqs_convocatorias where c_cte_id = ?)                 and f_informe_cte is not null                 and ((select sum(n_creditos_evaluador) from ocap_creditoscuestionarios                          where c_exp_id=oex2.c_exp_id                       )                      >=                     (select n_creditos_necesarios from ocap_itinerarios where c_itinerario_id = oex2.c_itinerario_id)                     )           ) as nEvaluadosOK         , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oex2              where c_compfqs_id in (select c_compfqs_id from ocap_compfqs_convocatorias where c_cte_id = ?)                  AND oex2.C_PROC_EVALUACION_ID NOT IN (4, 5)                 AND oex2.c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')                 and c_itinerario_id in (select c_itinerario_id from ocap_compfqs_convocatorias where c_cte_id = ?)                 and f_informe_cte is not null                 and ((select sum(n_creditos_evaluador) from ocap_creditoscuestionarios                          where c_exp_id=oex2.c_exp_id                       )                      <                     (select n_creditos_necesarios from ocap_itinerarios where c_itinerario_id = oex2.c_itinerario_id)                     )           ) as nEvaluadosKO         ,                 (SELECT d_nombre            FROM ocap_categ_profesionales           WHERE c_profesional_id =                              oex.c_profesional_id)                                                  AS profesionalnombre     FROM ocap_expedientescarrera oex, ocap_usuarios ou, ocap_compfqs_convocatorias occ     where  oex.c_usr_id = ou.c_usr_id         AND occ.b_borrado = 'N'         AND oex.b_borrado = 'N'         AND oex.C_PROC_EVALUACION_ID NOT IN (4, 5)         AND oex.c_convocatoria_id = occ.c_convocatoria_id         AND oex.c_compfqs_id = occ.c_compfqs_id         AND oex.c_itinerario_id = occ.c_itinerario_id         AND oex.c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')         AND oex.c_compfqs_id IN (           SELECT c_compfqs_id             FROM ocap_compfqs_convocatorias occ            WHERE occ.c_cte_id IN (                     SELECT oc.c_cte_id                       FROM ocap_ctes oc, ocap_ctesactivados oca                      WHERE oc.c_cte_id = oca.c_cte_id                        AND oc.b_borrado = 'N'                        AND oca.b_borrado = 'N'))        AND oex.f_informe_cte IS NOT NULL        AND oex.c_compfqs_id in (select c_compfqs_id from ocap_compfqs_convocatorias where c_cte_id=?)        AND occ.c_cte_id = ?     GROUP BY oex.c_profesional_id ";

				st = con.prepareStatement(sql, 1004, 1008);
				st.setLong(1, usuOT.getCCteId());
				st.setLong(2, usuOT.getCCteId());
				st.setLong(3, usuOT.getCCteId());
				st.setLong(4, usuOT.getCCteId());
				st.setLong(5, usuOT.getCCteId());
				st.setLong(6, usuOT.getCCteId());
				rs = st.executeQuery();

				listadoCateg = new ArrayList();
				while (rs.next()) {
					OCAPUsuariosOT result = new OCAPUsuariosOT();

					if (rs.getInt("nEvaluados") != 0) {
						result.setCProfesionalId(new Integer(rs.getInt("c_profesional_id")));
						result.setDProfesional_nombre(rs.getString("profesionalNombre"));

						result.setTotalIndicadores(rs.getInt("nEvaluados"));
						result.setTotalIndicadoresOK(rs.getInt("nEvaluadosOK"));
						result.setTotalIndicadoresKO(rs.getInt("nEvaluadosKO"));

						result.setPorcentajeIndicadores(
								formateador.format(rs.getInt("nEvaluados") / usuOT.getTotalIndicadores()));
						result.setPorcentajeIndicadoresOK(
								formateador.format(rs.getInt("nEvaluadosOK") / rs.getInt("nEvaluados")));
						result.setPorcentajeIndicadoresKO(
								formateador.format(rs.getInt("nEvaluadosKO") / rs.getInt("nEvaluados")));

						listadoCateg.add(result);
					}
				}
				usuOT.setListaCategorias(listadoCateg);
				listadoTotal.add(usuOT);
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

		return listadoTotal;
	}

	public ArrayList buscarEvaluadosPorCTE() throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "select distinct(c_cte_id)         , (select d_nombre from ocap_ctes where c_cte_id = occ.c_cte_id) as nombreCTE         ,(select count(distinct(c_compfqs_id)) from ocap_compfqs_convocatorias occ2            where occ2.c_cte_id = occ.c_cte_id            AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND (SYSDATE < f_fincp OR f_fincp is null) AND b_borrado = 'N')         ) as nEvaluadoresCTE        ,(select count(distinct(c_compfqs_id)) from ocap_compfqs_convocatorias occ2            where occ2.c_cte_id = occ.c_cte_id            AND occ2.c_compfqs_id in (select distinct(c_compfqs_id) from ocap_expedientescarrera                 where b_borrado='N'                AND f_informe_eval is not null                AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND (SYSDATE < f_fincp OR f_fincp is null) AND b_borrado = 'N')                )         ) as nEvaluadoresInformeCTE        , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oex            where c_compfqs_id in (select c_compfqs_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id and c_itinerario_id=oex.c_itinerario_id and c_convocatoria_id = oex.c_convocatoria_id)                 AND C_PROC_EVALUACION_ID NOT IN (4, 5)                AND c_convocatoria_id in (select c_convocatoria_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id and c_compfqs_id=oex.c_compfqs_id)                AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')                and c_itinerario_id in (select c_itinerario_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id and c_compfqs_id=oex.c_compfqs_id)                and f_informe_cte is not null          ) as nEvaluados        , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oex             where c_compfqs_id in (select c_compfqs_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id and c_itinerario_id=oex.c_itinerario_id and c_convocatoria_id = oex.c_convocatoria_id)                 AND oex.C_PROC_EVALUACION_ID NOT IN (4, 5)                and c_itinerario_id in (select c_itinerario_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id and c_compfqs_id=oex.c_compfqs_id)                AND c_convocatoria_id in (select c_convocatoria_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id and c_compfqs_id=oex.c_compfqs_id)                AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')                and f_informe_cte is not null                and ((select sum(n_creditos_evaluador) from ocap_creditoscuestionarios                         where c_exp_id=oex.c_exp_id                      )                     >=                    (select n_creditos_necesarios from ocap_itinerarios where c_itinerario_id = oex.c_itinerario_id)                    )          ) as nEvaluadosOK        , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oex             where c_compfqs_id in (select c_compfqs_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id and c_itinerario_id=oex.c_itinerario_id and c_convocatoria_id = oex.c_convocatoria_id)                 AND oex.C_PROC_EVALUACION_ID NOT IN (4, 5)                and c_itinerario_id in (select c_itinerario_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id and c_compfqs_id=oex.c_compfqs_id)                AND c_convocatoria_id in (select c_convocatoria_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id and c_compfqs_id=oex.c_compfqs_id)                AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')                and f_informe_cte is not null                and ((select sum(n_creditos_evaluador) from ocap_creditoscuestionarios                         where c_exp_id=oex.c_exp_id                      )                     <                    (select n_creditos_necesarios from ocap_itinerarios where c_itinerario_id = oex.c_itinerario_id)                    )          ) as nEvaluadosKO        , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oex             where c_compfqs_id in (select c_compfqs_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id and c_itinerario_id=oex.c_itinerario_id and c_convocatoria_id = oex.c_convocatoria_id)                 AND oex.C_PROC_EVALUACION_ID NOT IN (4, 5)                AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')                and f_informe_cte is not null                and b_conformidad_cte = 'Y'          ) as nEvaluadosConf        , (select count(distinct(c_exp_id)) from ocap_expedientescarrera oex             where c_compfqs_id in (select c_compfqs_id from ocap_compfqs_convocatorias where c_cte_id = occ.c_cte_id and c_itinerario_id=oex.c_itinerario_id and c_convocatoria_id = oex.c_convocatoria_id)                 AND oex.C_PROC_EVALUACION_ID NOT IN (4, 5)                AND c_convocatoria_id IN (SELECT c_convocatoria_id FROM ocap_convocatorias WHERE SYSDATE > f_publicacion AND SYSDATE < f_fincp AND b_borrado = 'N')                and f_informe_cte is not null                and b_conformidad_cte = 'N'          ) as nEvaluadosNoConf    from ocap_compfqs_convocatorias occ    where b_borrado='N'     AND c_compfqs_id in (select distinct(c_compfqs_id) from ocap_expedientescarrera where b_borrado='N'                         and C_PROC_EVALUACION_ID NOT IN (4, 5) and f_informe_cte is not null                        AND c_convocatoria_id = occ.c_convocatoria_id                        AND c_compfqs_id = occ.c_compfqs_id                        AND c_itinerario_id = occ.c_itinerario_id                        )     AND occ.c_cte_id IN (             SELECT oc.c_cte_id               FROM ocap_ctes oc, ocap_ctesactivados oca              WHERE oc.c_cte_id = oca.c_cte_id                AND oc.b_borrado = 'N'                AND oca.b_borrado = 'N')    group by c_cte_id order by nombreCTE ";

			st = con.prepareStatement(sql, 1004, 1008);

			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				OCAPUsuariosOT result = new OCAPUsuariosOT();

				result.setCCteId(rs.getLong("c_cte_id"));
				result.setDNombreCte(rs.getString("nombreCTE"));
				result.setTotalIndicadores(rs.getInt("nEvaluadoresCTE"));
				DecimalFormat formateador = new DecimalFormat("###.##%");
				result.setPorcentajeIndicadores(
						formateador.format(rs.getInt("nEvaluadoresInformeCTE") / rs.getInt("nEvaluadoresCTE")));
				result.setTotalIndicador(rs.getInt("nEvaluados"));
				result.setTotalIndicadoresOK(rs.getInt("nEvaluadosOK"));
				result.setTotalIndicadoresKO(rs.getInt("nEvaluadosKO"));
				result.setTotalIndicadoresModOK(rs.getInt("nEvaluadosConf"));
				result.setTotalIndicadoresModKO(rs.getInt("nEvaluadosNoConf"));

				result.setPorcentajeIndicadoresOK(
						formateador.format(rs.getInt("nEvaluadosOK") / rs.getInt("nEvaluados")));
				result.setPorcentajeIndicadoresKO(
						formateador.format(rs.getInt("nEvaluadosKO") / rs.getInt("nEvaluados")));
				result.setPorcentajeIndicadoresModOK(
						formateador.format(rs.getInt("nEvaluadosConf") / rs.getInt("nEvaluados")));
				result.setPorcentajeIndicadoresModKO(
						formateador.format(rs.getInt("nEvaluadosNoConf") / rs.getInt("nEvaluados")));

				listado.add(result);
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

	public ArrayList buscarOCAPUsuariosPorNIF_a(String cNifNie) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = null;
		OCAPUsuariosOT datos = null;
		ArrayList listado = null;
		try {
			loggerBD.debug(getClass().getName() + " buscarOCAPUsuariosPorNIF_a: " + cNifNie);

			con = JCYLGestionTransacciones.getConnection();

			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT usua.c_usr_id, usua.d_nombre, usua.d_apellidos, usua.c_dni_real, usua.c_dni, ")
					.append("(SELECT count(*) FROM ocap_solicitudes WHERE c_usr_id = usua.c_usr_id and b_borrado='N') as numSolicitudes")
					.append(" FROM ocap_usuarios usua ")
					.append(" WHERE LPAD(UPPER(usua.c_dni_real),9,'0') = LPAD(UPPER(?),9,'0') ");

			loggerBD.info("SQL: " + sql.toString());

			st = con.prepareStatement(sql.toString(), 1004, 1007);
			st.setString(1, cNifNie);
			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				datos = new OCAPUsuariosOT();
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCUsrId(Long.valueOf(rs.getLong("C_USR_ID")));
				datos.setDApellido1(rs.getString("D_APELLIDOS"));
				datos.setCDniReal(rs.getString("C_DNI_REAL"));
				datos.setCDni(rs.getString("C_DNI"));
				datos.setNumSolicitudes(rs.getLong("numSolicitudes"));
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

	public ArrayList listadoUsuariosPorConvocatoriaGradoYEstado(long cConvocatoriaId, long cGradoId, long cEstadoId)
			throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		ArrayList listado = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(" SELECT usua.c_usr_id, usua.d_nombre, usua.d_apellidos, usua.c_dni, usua.c_dni_real ")
					.append(" FROM ocap_usuarios usua,  ").append(" (SELECT DISTINCT c_usr_id  ")
					.append(" FROM ocap_expedientescarrera  ").append(" WHERE b_borrado = 'N' ");

			if (cConvocatoriaId != 0L)
				sql.append(" AND c_convocatoria_id = ? ");
			if (cGradoId != 0L)
				sql.append(" AND c_grado_id = ? ");
			if (cEstadoId != 0L) {
				sql.append(" AND c_estado_id = ? ");
			}
			sql.append(" ) ecar ").append(" WHERE ecar.c_usr_id = usua.c_usr_id ").append(" AND usua.c_dni is null ");

			loggerBD.info("SQL: " + sql.toString());

			st = con.prepareStatement(sql.toString(), 1004, 1007);

			int cont = 1;
			if (cConvocatoriaId != 0L)
				st.setLong(cont++, cConvocatoriaId);
			if (cGradoId != 0L)
				st.setLong(cont++, cEstadoId);
			if (cEstadoId != 0L) {
				st.setLong(cont++, cEstadoId);
			}
			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				OCAPUsuariosOT datos = new OCAPUsuariosOT();
				datos.setCUsrId(new Long(rs.getLong("c_usr_id")));
				datos.setDNombre(rs.getString("d_nombre"));
				datos.setDApellido1(rs.getString("d_apellidos"));
				datos.setCDni(rs.getString("c_dni"));
				datos.setCDniReal(rs.getString("c_dni_real"));

				listado.add(datos);
			}
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null) {
				st.close();
			}
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return listado;
	}

	public OCAPUsuariosOT obtenerUsuario(OCAPUsuariosOT datosBusqueda) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		OCAPUsuariosOT datos = null;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("SELECT c_usr_id, d_nombre, d_apellidos, ").append(" c_dni_real, c_dni, ").append(" b_sexo,  ")
					.append(" c_centrotrabajo_id, c_gerencia_id, ")
					.append(" a_domicilio, c_provincia_id, d_local, n_codigopostal, ")
					.append(" n_telefono1, n_telefono2, a_correoelectronico, ").append(" b_borrado, f_usualta ")
					.append(" FROM ocap_usuarios ").append(" WHERE c_usr_id = ? ");

			loggerBD.info("SQL: " + sql.toString());

			st = con.prepareStatement(sql.toString());

			st.setLong(1, datosBusqueda.getCUsrId().longValue());

			rs = st.executeQuery();

			datos = new OCAPUsuariosOT();
			if (rs.next()) {
				datos.setCUsrId(datosBusqueda.getCUsrId());
				datos.setDNombre(rs.getString("d_nombre"));
				datos.setDApellido1(rs.getString("d_apellidos"));
				datos.setCDniReal(rs.getString("c_dni_real"));
				datos.setCDni(rs.getString("c_dni"));
				datos.setBSexo(rs.getString("b_sexo"));

				datos.setCCentrotrabajoId(new Integer(rs.getInt("c_centrotrabajo_id")));
				datos.setCGerenciaId(new Integer(rs.getInt("c_gerencia_id")));

				datos.setNTelefono1(new Integer(rs.getInt("n_telefono1")));
				datos.setNTelefono2(new Integer(rs.getInt("n_telefono2")));
				datos.setDCorreoelectronico(rs.getString("a_correoelectronico"));
				datos.setDDomicilio(rs.getString("a_domicilio"));
				datos.setCProvinciaUsu_id(rs.getString("c_provincia_id"));

				datos.setDLocalidadUsu(rs.getString("d_local"));
				datos.setCPostalUsu(rs.getString("n_codigopostal"));
				datos.setBBorrado(rs.getString("b_borrado"));
				datos.setFCreacion(rs.getDate("f_usualta"));
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

	public ArrayList reemplazarUIDPorReal(ArrayList listaUsuarios) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listaRepetidos = new ArrayList();
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("UPDATE ocap_usuarios ").append(" SET c_dni = c_dni_real ").append(" WHERE c_usr_id = ? ");

			loggerBD.info("SQL: " + sql.toString());

			st = con.prepareStatement(sql.toString());

			int i = 0;
			for (i = 0; i < listaUsuarios.size(); i++) {
				OCAPUsuariosOT datos = (OCAPUsuariosOT) listaUsuarios.get(i);
				st.setLong(1, datos.getCUsrId().longValue());
				try {
					st.executeUpdate();
				} catch (SQLException e) {
					if (e.getMessage().startsWith("ORA-00001"))
						listaRepetidos.add(datos);
					else {
						throw e;
					}
				}
				if ((i != 0) && (i % 100 == 0) && (listaRepetidos.size() == 0))
					con.commit();
			}
			if (((i == 0) || (i % 100 != 0)) && (listaRepetidos.size() == 0))
				con.commit();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return listaRepetidos;
	}

	public int modificarUID(OCAPUsuariosOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("UPDATE ocap_usuarios ").append(" SET ").append("c_usumodi = ?, ")
					.append("f_usumodi = SYSDATE, ").append("c_dni = ? ").append(" WHERE c_usr_id = ? ");

			loggerBD.info("SQL: " + sql.toString());

			st = con.prepareStatement(sql.toString());

			st.setString(1, datos.getAModificadoPor());
			st.setString(2, datos.getCDni());
			st.setLong(3, datos.getCUsrId().longValue());

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

	public ArrayList listarEvaluadosGerenciaCTE(OCAPUsuariosOT datos, int inicio, int cuantos, JCYLUsuario jcylUsuario) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;

		long cte = 0L;
		try {
			String where = "";
			String sqlStatement = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT convo.d_nombre_corto as convocatoria_corto, solic.D_APELLIDOS, solic.D_NOMBRE, solic.C_DNI as C_DNI_REAL, usu.C_USR_ID, F_INICIO_CA, exp.C_EXP_ID, exp.C_PROFESIONAL_ID, (select d_nombre from ocap_categ_profesionales where c_profesional_id = exp.c_profesional_id) as dCategoria, exp.C_ESPEC_ID, (select d_nombre from ocap_especialidades where c_espec_id = exp.c_espec_id) as dEspecialidad, C_ITINERARIO_ID, (select d_descripcion from ITCP_MANUALES_EVALUACION where c_manual_evaluacion_id = exp.c_itinerario_id) as dItinerario, F_INFORME_EVAL, F_INFORME_CTE, F_INFORME_CE, F_INFORME_CC, (select F_INFORME from OCAP_REVISIONES where b_borrado='N' and C_EXP_ID= exp.c_exp_id) as F_INFORME_EVAL2, (select count(*) FROM ocap_expedientesencuestas WHERE b_borrado = 'N' and c_exp_id = EXP.c_exp_id) as nRespuestasEncuesta , (select count(c_revision_id) from ocap_revisiones where c_exp_id = exp.c_exp_id and b_borrado='N' ";

			selectFrom = selectFrom
					+ ") as bSegundaRevision, exp.C_COMPFQS_ID  FROM OCAP_SOLICITUDES solic, OCAP_USUARIOS usu, OCAP_EXPEDIENTESCARRERA exp, ocap_convocatorias convo WHERE usu.B_BORRADO = 'N' AND solic.c_usr_id = usu.c_usr_id AND solic.c_convocatoria_id = exp.c_convocatoria_id and exp.c_convocatoria_id = convo.c_convocatoria_id AND exp.B_BORRADO = 'N' AND usu.C_USR_ID = exp.C_USR_ID AND EXP.c_estado_id IN (9, 11, 12) ";

			String selectConv = "";

			String orderBy =" ORDER BY  NLSSORT(solic.d_apellidos, 'NLS_SORT = Spanish') ASC, NLSSORT(solic.d_nombre, 'NLS_SORT = Spanish') ASC,NLSSORT(solic.C_DNI, 'NLS_SORT = Spanish') ASC "; 
					
		

			if ((datos.getCProfesionalId() != null) && (datos.getCProfesionalId().longValue() != 0L)){
				sbWhere.append(" AND exp.C_PROFESIONAL_ID =").append(datos.getCProfesionalId());
			}
			if (datos.getCConvocatoriaId() != 0L) {
				sbWhere.append(" AND exp.C_CONVOCATORIA_ID =").append(datos.getCConvocatoriaId());
			}
			if ((datos.getCGerenciaId() != null) && (datos.getCGerenciaId().intValue() != 0)) {
				sbWhere.append(" AND solic.C_GERENCIA_ACTUAL_ID =").append(datos.getCGerenciaId());
			}
			
			if ((datos.getCEspecId() != null) && (datos.getCEspecId().intValue() != 0)) {
				sbWhere.append(" AND exp.C_ESPEC_ID =").append(datos.getCEspecId());
			}
			if ((datos.getDEstado() != null) && (!datos.getDEstado().equals(""))) {

				if (datos.getDEstado().equals("7")) {
					sbWhere.append(" AND exp.F_INFORME_EVAL IS NULL");
				} else if (datos.getDEstado().equals("8")) {
					sbWhere.append(" AND exp.F_INFORME_CTE IS NULL");
				}

			}
			//xamp.sacyl.es/jira/browse/OCAP-1499
			sbWhere.append(" AND convo.B_BORRADO = 'N' ");
			//OCAP-886
			if (Constantes.OCAP_CTE.equals(jcylUsuario.getUser().getRol()) && Constantes.ID_GRS !=datos.getCGerenciaId()) {
				selectFrom+=" AND exp.c_proc_evaluacion_id not in ( 4,5) ";
			}
			
			sqlStatement = selectFrom + sbWhere + orderBy;

			st = con.prepareStatement(sqlStatement, 1004, 1008);

			rs = st.executeQuery();
			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {

				OCAPUsuariosOT result = new OCAPUsuariosOT();
				result.setDConvocatoriaNombreCorto(rs.getString("convocatoria_corto"));
				result.setDNombre(rs.getString("D_NOMBRE"));
				result.setDApellido1(rs.getString("D_APELLIDOS"));
				result.setCDni(rs.getString("C_DNI_REAL"));
				result.setCUsrId(Long.valueOf(rs.getString("C_USR_ID")));
				result.setFIncioCA(rs.getDate("F_INICIO_CA"));
				result.setCExpId(new Long(rs.getLong("C_EXP_ID")));
				result.setCCompfqsId(datos.getCCompfqsId());
				result.setCProfesionalId(new Integer(rs.getInt("C_PROFESIONAL_ID")));
				result.setDProfesional_nombre(rs.getString("dCategoria"));
				result.setCEspecId(new Integer(rs.getInt("C_ESPEC_ID")));
				result.setDEspec_nombre(rs.getString("dEspecialidad"));
				result.setCItinerarioId(rs.getLong("C_ITINERARIO_ID"));
				result.setDItinerario(rs.getString("dItinerario"));
				result.setFInformeCe(rs.getDate("F_INFORME_CE"));
				result.setFInformeCte(rs.getDate("F_INFORME_CTE"));
				result.setFInformeEval(rs.getDate("F_INFORME_EVAL"));
				result.setFInformeEval2(rs.getDate("F_INFORME_EVAL2"));
				result.setFInformeCC(rs.getDate("F_INFORME_CC"));
				result.setCCompfqsId(rs.getLong("C_COMPFQS_ID"));

				DecimalFormat formato = new DecimalFormat("000000");
				result.setCodigoId("EPR" + formato.format(result.getCExpId().longValue()));

				listado.add(result);
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

	public int listarEvaluadosCuentaGerenciaCTE(OCAPUsuariosOT datos, JCYLUsuario jcylUsuario) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int total = -1;
		try {
			String where = "";
			String sqlStatement = "";
			StringBuffer sbWhere = new StringBuffer(40);

			String selectFrom = "SELECT count(*) FROM OCAP_SOLICITUDES solic, OCAP_USUARIOS usu, OCAP_EXPEDIENTESCARRERA exp, ocap_convocatorias convo WHERE usu.B_BORRADO = 'N'  AND solic.c_usr_id = usu.c_usr_id AND solic.c_convocatoria_id = exp.c_convocatoria_id and exp.c_convocatoria_id = convo.c_convocatoria_id AND exp.B_BORRADO = 'N' AND usu.C_USR_ID = exp.C_USR_ID AND EXP.c_estado_id IN (9, 11, 12) ";

			String orderBy = " ORDER BY solic.D_APELLIDOS, solic.D_NOMBRE, solic.C_DNI ";

			if ((datos.getCProfesionalId() != null) && (datos.getCProfesionalId().longValue() != 0L))
				sbWhere.append(" AND exp.C_PROFESIONAL_ID =").append(datos.getCProfesionalId());
			if (datos.getCConvocatoriaId() != 0L) {
				sbWhere.append(" AND exp.C_CONVOCATORIA_ID =").append(datos.getCConvocatoriaId());
			}
			if ((datos.getCGerenciaId() != null) && (datos.getCGerenciaId().intValue() != 0)) {
				sbWhere.append(" AND solic.C_GERENCIA_ACTUAL_ID =").append(datos.getCGerenciaId());
			}
			
			if ((datos.getCEspecId() != null) && (datos.getCEspecId().intValue() != 0)) {
				sbWhere.append(" AND exp.C_ESPEC_ID =").append(datos.getCEspecId());
			}
			
			if ((datos.getDEstado() != null) && (!datos.getDEstado().equals(""))) {

				if (datos.getDEstado().equals("7")) {
					sbWhere.append(" AND F_INFORME_EVAL IS NULL");
				} else if (datos.getDEstado().equals("8")) {
					sbWhere.append(" AND F_INFORME_CTE IS NULL");
				}

			}
			//xamp.sacyl.es/jira/browse/OCAP-1499
			sbWhere.append(" AND convo.B_BORRADO = 'N' ");
			
			//OCAP-886
			if (Constantes.OCAP_CTE.equals(jcylUsuario.getUser().getRol()) && Constantes.ID_GRS !=datos.getCGerenciaId()) {
				selectFrom+=" AND exp.c_proc_evaluacion_id not in ( 4,5) ";
			}
			
			sqlStatement = selectFrom + sbWhere + orderBy;
			st = con.prepareStatement(sqlStatement, 1004, 1008);

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

	public ArrayList buscarOCAPUsuariosPorDniReal(String usuarioNifNie) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = null;
		OCAPUsuariosOT datos = null;
		ArrayList listado = null;
		try {
			loggerBD.debug(getClass().getName() + " buscarOCAPUsuariosPorDniReal: " + usuarioNifNie);

			con = JCYLGestionTransacciones.getConnection();

			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT usua.c_usr_id, usua.d_nombre, usua.d_apellidos, usua.c_dni_real, usua.c_dni, ")
					.append("(SELECT count(*) FROM ocap_solicitudes WHERE c_usr_id = usua.c_usr_id and b_borrado='N') as numSolicitudes")
					.append(" FROM ocap_usuarios usua ")
					.append(" WHERE LPAD(UPPER(usua.c_dni_real),9,'0') = LPAD(UPPER(?),9,'0') AND usua.C_DNI IS NOT NULL");

			loggerBD.info("SQL: " + sql.toString());

			st = con.prepareStatement(sql.toString(), 1004, 1007);
			st.setString(1, usuarioNifNie);
			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				datos = new OCAPUsuariosOT();
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCUsrId(Long.valueOf(rs.getLong("C_USR_ID")));
				datos.setDApellido1(rs.getString("D_APELLIDOS"));
				datos.setCDniReal(rs.getString("C_DNI_REAL"));
				datos.setCDni(rs.getString("C_DNI"));
				datos.setNumSolicitudes(rs.getLong("numSolicitudes"));
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
