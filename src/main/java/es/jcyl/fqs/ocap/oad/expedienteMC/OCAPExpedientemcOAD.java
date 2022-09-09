package es.jcyl.fqs.ocap.oad.expedienteMC;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.expedienteMC.OCAPExpedientemcOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPExpedientemcOAD {
	public static Logger logger = OCAPConfigApp.logger;
	public Logger loggerBD;
	private static OCAPExpedientemcOAD instance;

	private void $init$() {
		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public static OCAPExpedientemcOAD getInstance() {
		if (instance == null) {
			instance = new OCAPExpedientemcOAD();
		}
		return instance;
	}

	private OCAPExpedientemcOAD() {
		$init$();
	}

	public int altaOCAPExpeditentemc(OCAPExpedientemcOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = null;
		int filas = 0;
		try {
			this.loggerBD.info(" altaOCAPExpeditentemc ");
			OCAPConfigApp.logger.info(getClass().getName() + " altaOCAPExpeditentemc: Inicio");
			con = JCYLGestionTransacciones.getConnection();

			String sql = "INSERT INTO OCAP_EXPEDIENTESMCS (C_MERITO_ID, N_ANNIOS, N_MESES, B_BORRADO, N_SESIONES, N_HORAS, F_ANNIO, N_CRED_CURSO, F_FINALIZACION, B_PARTICIPACION, CLOB_ORGANIZADOR, D_TITULO, N_ISBN, D_EDITORIAL, C_EXP_ID, F_INICIO, A_LUGAR_EXPEDICION, N_DIAS, F_EXPEDICION, C_EXPMC_ID, F_USUALTA, B_OPCIONAL, A_CARGO , A_OBJETIVO, A_NOMBRE_REVISTA, C_USUALTA, B_ACREDITADO, N_CRED_USUARIO, N_CRED_CEIS, N_CRED_CC) VALUES ((SELECT c_merito_id FROM ocap_meritoscurriculares WHERE c_defmerito_id = ? AND d_nombre = ? AND c_estatut_id = ? ";

			if (datos.getCActividadId() != null)
				sql = sql + "    AND c_actividad_id= ? ";
			else
				sql = sql + " AND c_actividad_id is null ";
			if (datos.getCModalidadId() != null)
				sql = sql + "    AND c_modalidad_id = ? ";
			else
				sql = sql + "AND c_modalidad_id is null";
			if (datos.getCTipoId() != null)
				sql = sql + "    AND c_tipo_id= ? ";
			else
				sql = sql + " AND c_tipo_id is null ";
			if (datos.getCFactorId() != null)
				sql = sql + "    AND c_factor_id= ? ";
			else
				sql = sql + " AND c_factor_id is null ";
			if (datos.getBAcreditado() != null) {
				sql = sql + "    AND b_acreditado= ? ";
			}
			sql = sql + " ) ,  ?, ?, " + " 'N', ?, " + "?, ?, ?, TO_DATE(?," + Constantes.FECHA_CORTA + "), "
					+ "?, ?, ?, ?, ?, ?, " + "TO_DATE(?," + Constantes.FECHA_CORTA + "), ?, ?, TO_DATE(?," + Constantes.FECHA_CORTA
					+ "), " + " OCAP_EMC_ID_SEQ.NEXTVAL, SYSDATE, ?, ?, ?, ?, ?,?,?,?,?)";

			st = con.prepareStatement(sql);
			int cont = 1;
			st.setString(cont++, datos.getCDefMeritoId());
			st.setString(cont++, datos.getDTipoMerito());
			if (datos.getCEstatutId() != null)
				st.setInt(cont++, datos.getCEstatutId().intValue());
			else
				st.setNull(cont++, 2);
			if (datos.getCActividadId() != null)
				st.setInt(cont++, datos.getCActividadId().intValue());
			if (datos.getCModalidadId() != null)
				st.setInt(cont++, datos.getCModalidadId().intValue());
			if (datos.getCTipoId() != null)
				st.setInt(cont++, datos.getCTipoId().intValue());
			if (datos.getCFactorId() != null)
				st.setInt(cont++, datos.getCFactorId().intValue());
			if (datos.getBAcreditado() != null)
				st.setString(cont++, datos.getBAcreditado());
			if (datos.getNAnnios() != null)
				st.setInt(cont++, datos.getNAnnios().intValue());
			else
				st.setNull(cont++, 2);
			if (datos.getNMeses() != null)
				st.setInt(cont++, datos.getNMeses().intValue());
			else
				st.setNull(cont++, 2);
			if (datos.getNSesiones() != null)
				st.setInt(cont++, datos.getNSesiones().intValue());
			else
				st.setNull(cont++, 2);
			if (datos.getNHoras() != null)
				st.setInt(cont++, datos.getNHoras().intValue());
			else
				st.setNull(cont++, 2);
			if (datos.getFAnnio() != null)
				st.setInt(cont++, datos.getFAnnio().intValue());
			else
				st.setNull(cont++, 2);
			if (datos.getNCreditosCurso() != null)
				st.setDouble(cont++, Double.valueOf(datos.getNCreditosCurso()).doubleValue());
			else
				st.setNull(cont++, 2);
			st.setString(cont++, datos.getFFinalizacion());
			st.setString(cont++, datos.getBParticipacion());
			//st.setString(cont++, Utilidades.prepararInsercionClob(datos.getAOrganizador()));
			st.setString(cont++, datos.getAOrganizador());
			st.setString(cont++, datos.getDTitulo());
			st.setString(cont++, datos.getNISBN());
			st.setString(cont++, datos.getDEditorial());
			if (datos.getCExpId() != null)
				st.setLong(cont++, datos.getCExpId().longValue());
			else
				st.setNull(cont++, 2);
			st.setString(cont++, datos.getFInicio());
			st.setString(cont++, datos.getALugarExpedicion());
			if (datos.getNDias() != null)
				st.setInt(cont++, datos.getNDias().intValue());
			else
				st.setNull(cont++, 2);
			st.setString(cont++, datos.getFExpedicion());
			st.setString(cont++, datos.getBOpcional());
			st.setString(cont++, datos.getACargo());
			st.setString(cont++, datos.getAObjetivo());
			st.setString(cont++, datos.getANombreRevista());
			st.setString(cont++, datos.getACreadoPor());
			if (Constantes.SI.equals(datos.getBAcreditado()))
				st.setString(cont++, datos.getBAcreditado());
			else {
				st.setString(cont++, Constantes.NO);
			}

			if ((datos.getNCreditos() == null)
					|| (("Taller".equals(datos.getCTipoMerito())) && (Constantes.NO.equals(datos.getBAcreditado())))) {
				st.setNull(cont++, 2);
				st.setNull(cont++, 2);
				st.setNull(cont++, 2);
			} else {
				st.setFloat(cont++, datos.getNCreditos().floatValue());
				st.setFloat(cont++, datos.getNCreditos().floatValue());
				st.setFloat(cont++, datos.getNCreditos().floatValue());
			}

			filas = st.executeUpdate();

			OCAPConfigApp.logger.info(getClass().getName() + " altaOCAPExpeditentemc: Fin");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
	}

	public int bajaOCAPExpeditentemc(Long cExpmcId, String userMod) throws SQLException, Exception {
		PreparedStatement st = null;
		int filas = 0;
		Connection con = null;
		try {
			this.loggerBD.info(" bajaOCAPExpeditentemc: " + cExpmcId);

			con = JCYLGestionTransacciones.getConnection();

			String sql = "UPDATE OCAP_EXPEDIENTESMCS SET B_BORRADO= 'Y',  F_USUMODI = SYSDATE, C_USUMODI = ?  WHERE C_EXPMC_ID =  ?";

			st = con.prepareStatement(sql);
			st.setString(1, userMod);
			st.setLong(2, cExpmcId.longValue());

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

	public int invalidaOCAPExpeditentemc(Long cExpmcId, boolean bInvalidar, String perfil,String userMod)
			throws SQLException, Exception {
		PreparedStatement st = null;
		int filas = 0;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			String sql = "UPDATE OCAP_EXPEDIENTESMCS ";
			if (bInvalidar) {
				sql = sql + "SET B_INVALIDADO_CC= 'Y', B_PDTE_ACLARAR_CC= 'N', ";
				if (Constantes.OCAP_CEIS.equals(perfil)) {
					sql = sql + "B_INVALIDADO_CEIS= 'Y', ";
					sql = sql + "B_PDTE_ACLARAR_CEIS= 'N', ";
				}
			} else {
				sql = sql + "SET B_INVALIDADO_CC= 'N', ";
				if (Constantes.OCAP_CEIS.equals(perfil))
					sql = sql + "B_INVALIDADO_CEIS= 'N', ";
			}
			sql = sql + " F_USUMODI = SYSDATE, C_USUMODI = ?  WHERE C_EXPMC_ID =  ?";

			st = con.prepareStatement(sql);
			st.setString(1, userMod);
			st.setLong(2, cExpmcId.longValue());

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

	public int aclaraOCAPExpeditentemc(Long cExpmcId, boolean bPedirAclarar, String perfil, String userMod) throws SQLException {
		PreparedStatement st = null;
		int filas = 0;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			String sql = "UPDATE OCAP_EXPEDIENTESMCS ";
			if (bPedirAclarar) {
				sql = sql + "SET B_PDTE_ACLARAR_CC= 'Y', ";
				if (Constantes.OCAP_CEIS.equals(perfil))
					sql = sql + "B_PDTE_ACLARAR_CEIS= 'Y', ";
			} else {
				sql = sql + "SET B_PDTE_ACLARAR_CC= 'N', ";
				if (Constantes.OCAP_CEIS.equals(perfil))
					sql = sql + "B_PDTE_ACLARAR_CEIS= 'N', ";
			}
			sql = sql + " F_USUMODI = SYSDATE, C_USUMODI =?  WHERE C_EXPMC_ID =  ?";

			st = con.prepareStatement(sql);
			st.setString(1, userMod);
			st.setLong(2, cExpmcId.longValue());

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

	public int desinvalidarOCAPporExpId(Long cExpId, String userMod) throws SQLException, Exception {
		PreparedStatement st = null;
		int filas = 0;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			this.loggerBD.info(" desinvalidarOCAPporExpId: " + cExpId);

			String sql = "UPDATE OCAP_EXPEDIENTESMCS SET B_INVALIDADO_CEIS= '', B_INVALIDADO_CC= '', F_USUMODI = SYSDATE, C_USUMODI=?  WHERE C_EXP_ID =  ?  AND B_BORRADO = 'N' ";

			st = con.prepareStatement(sql);
			st.setString(1, userMod);
			st.setLong(2, cExpId.longValue());

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

	public int modificacionOCAPExpeditentemc(OCAPExpedientemcOT datos, String userMod) throws SQLException, Exception {
		PreparedStatement st = null;
		int filas = 0;
		Connection con = null;
		try {
			this.loggerBD.info(" modificacionOCAPExpeditentemc ");
			con = JCYLGestionTransacciones.getConnection();

			String sql = "UPDATE OCAP_EXPEDIENTESMCS SET C_MERITO_ID = (SELECT c_merito_id FROM ocap_meritoscurriculares WHERE c_defmerito_id = ?  AND d_nombre = ?  AND c_estatut_id = ? ";

			if (datos.getCActividadId() != null)
				sql = sql + "    AND c_actividad_id= ? ";
			else
				sql = sql + " AND c_actividad_id is null ";
			if (datos.getCModalidadId() != null)
				sql = sql + "    AND c_modalidad_id = ? ";
			else
				sql = sql + "AND c_modalidad_id is null ";
			if (datos.getCTipoId() != null)
				sql = sql + "    AND c_tipo_id= ? ";
			else
				sql = sql + " AND c_tipo_id is null ";
			if (datos.getCFactorId() != null)
				sql = sql + "    AND c_factor_id= ? ";
			else
				sql = sql + " AND c_factor_id is null ";
			if (datos.getBAcreditado() != null)
				sql = sql + "    AND b_acreditado= ? ";
			sql = sql + " ) , ";

			if (datos.getNSesiones() != null)
				sql = sql + "N_SESIONES = ?, ";
			if (datos.getNHoras() != null)
				sql = sql + "N_HORAS = ?, ";
			if (datos.getFAnnio() != null)
				sql = sql + "F_ANNIO = ?, ";
			if (datos.getFFinalizacion() != null)
				sql = sql + "F_FINALIZACION = TO_DATE(?,'DD/MM/RRRR'), ";
			if (datos.getBParticipacion() != null)
				sql = sql + "B_PARTICIPACION = ?, ";
			if (datos.getAOrganizador() != null)
				sql = sql + "CLOB_ORGANIZADOR = ?, ";
			if (datos.getDTitulo() != null)
				sql = sql + "D_TITULO = ?, ";
			if (datos.getNISBN() != null)
				sql = sql + "N_ISBN = ?, ";
			if (datos.getDEditorial() != null)
				sql = sql + "D_EDITORIAL = ?, ";
			if (datos.getFInicio() != null)
				sql = sql + "F_INICIO = TO_DATE(?,'DD/MM/RRRR'), ";
			if (datos.getALugarExpedicion() != null)
				sql = sql + "A_LUGAR_EXPEDICION = ?, ";
			if (datos.getNDias() != null)
				sql = sql + "N_DIAS = ?, ";
			if (datos.getFExpedicion() != null)
				sql = sql + "F_EXPEDICION = TO_DATE(?,'DD/MM/RRRR'), ";
			if (datos.getNAnnios() != null)
				sql = sql + "N_ANNIOS = ?, ";
			if (datos.getNMeses() != null)
				sql = sql + "N_MESES = ?, ";
			if (datos.getACargo() != null)
				sql = sql + "A_CARGO = ?, ";
			if (datos.getAObjetivo() != null)
				sql = sql + "A_OBJETIVO = ?, ";
			if (datos.getANombreRevista() != null)
				sql = sql + "A_NOMBRE_REVISTA = ?, ";
			if (datos.getNCreditosCurso() != null)
				sql = sql + "N_CRED_CURSO = ?, ";
			if (datos.getBAcreditado() != null)
				sql = sql + "B_ACREDITADO = ?, ";
			if (datos.getNCreditos() != null) {
				sql = sql + "N_CRED_USUARIO = ?, ";
				sql = sql + "N_CRED_CEIS = ?, ";
				sql = sql + "N_CRED_CC = ?, ";
			}
			sql = sql + "F_USUMODI = SYSDATE, C_USUMODI=?  WHERE C_EXPMC_ID = ?";

			st = con.prepareStatement(sql);
			int cont = 1;
			st.setString(cont++, datos.getCDefMeritoId());
			st.setString(cont++, datos.getDTipoMerito());
			if (datos.getCEstatutId() != null)
				st.setInt(cont++, datos.getCEstatutId().intValue());
			else
				st.setNull(cont++, 2);
			if (datos.getCActividadId() != null)
				st.setInt(cont++, datos.getCActividadId().intValue());
			if (datos.getCModalidadId() != null)
				st.setInt(cont++, datos.getCModalidadId().intValue());
			if (datos.getCTipoId() != null)
				st.setInt(cont++, datos.getCTipoId().intValue());
			if (datos.getCFactorId() != null)
				st.setInt(cont++, datos.getCFactorId().intValue());
			if (datos.getBAcreditado() != null)
				st.setString(cont++, datos.getBAcreditado());
			if (datos.getNSesiones() != null)
				st.setInt(cont++, datos.getNSesiones().intValue());
			if (datos.getNHoras() != null)
				st.setInt(cont++, datos.getNHoras().intValue());
			if (datos.getFAnnio() != null)
				st.setInt(cont++, datos.getFAnnio().intValue());
			if (datos.getFFinalizacion() != null)
				st.setString(cont++, datos.getFFinalizacion());
			if (datos.getBParticipacion() != null)
				st.setString(cont++, datos.getBParticipacion());
			if (datos.getAOrganizador() != null)
				//st.setString(cont++, Utilidades.prepararInsercionClob(datos.getAOrganizador()));
				st.setString(cont++, datos.getAOrganizador());
			if (datos.getDTitulo() != null)
				st.setString(cont++, datos.getDTitulo());
			if (datos.getNISBN() != null)
				st.setString(cont++, datos.getNISBN());
			if (datos.getDEditorial() != null)
				st.setString(cont++, datos.getDEditorial());
			if (datos.getFInicio() != null)
				st.setString(cont++, datos.getFInicio());
			if (datos.getALugarExpedicion() != null)
				st.setString(cont++, datos.getALugarExpedicion());
			if (datos.getNDias() != null)
				st.setInt(cont++, datos.getNDias().intValue());
			if (datos.getFExpedicion() != null)
				st.setString(cont++, datos.getFExpedicion());
			if (datos.getNAnnios() != null)
				st.setInt(cont++, datos.getNAnnios().intValue());
			if (datos.getNMeses() != null)
				st.setInt(cont++, datos.getNMeses().intValue());
			if (datos.getACargo() != null)
				st.setString(cont++, datos.getACargo());
			if (datos.getAObjetivo() != null)
				st.setString(cont++, datos.getAObjetivo());
			if (datos.getANombreRevista() != null)
				st.setString(cont++, datos.getANombreRevista());
			if (datos.getNCreditosCurso() != null) {
				if (datos.getNCreditosCurso().equals(""))
					st.setNull(cont++, 2);
				else
					st.setDouble(cont++, Double.valueOf(datos.getNCreditosCurso()).doubleValue());
			}
			if (datos.getBAcreditado() != null) {
				st.setString(cont++, datos.getBAcreditado());
			}

			if ((datos.getNCreditos() == null)
					|| (("Taller".equals(datos.getCTipoMerito())) && (Constantes.NO.equals(datos.getBAcreditado())))) {
				st.setNull(cont++, 2);
				st.setNull(cont++, 2);
				st.setNull(cont++, 2);
			} else {
				st.setFloat(cont++, datos.getNCreditos().floatValue());
				st.setFloat(cont++, datos.getNCreditos().floatValue());
				st.setFloat(cont++, datos.getNCreditos().floatValue());
			}
			st.setString(cont++, userMod);
			if (datos.getCExpmcId() != null) {
				st.setLong(cont++, datos.getCExpmcId().longValue());
			}
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

	public int enviarOCAPExpeditentemcOpcionales(Long cExpmcId, Connection con, String userMod) throws SQLException, Exception {
		PreparedStatement st = null;
		int filas = 0;
		try {
			this.loggerBD.info(" enviarOCAPExpeditentemcOpcionales: " + cExpmcId + " con:" + con);

			String sql = "UPDATE OCAP_EXPEDIENTESMCS SET B_OPCIONAL = 'Y', F_USUMODI = SYSDATE,C_USUMODI=?  WHERE C_EXPMC_ID = ?";

			st = con.prepareStatement(sql);
			st.setString(1, userMod);
			st.setLong(2, cExpmcId.longValue());

			filas = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (st != null) {
				st.close();
			}
		}

		return filas;
	}

	public OCAPExpedientemcOT buscarOCAPExpeditentemc(Long cExpmcId, Connection con) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		OCAPExpedientemcOT datos = null;
		try {
			this.loggerBD.info(" buscarOCAPExpeditentemc: " + cExpmcId + " con: " + con);
			String sql = "SELECT C_MERITO_ID, (select c_actividad_id from ocap_meritoscurriculares  where c_merito_id = oe.c_merito_id ) as c_actividad_id, TO_CHAR(F_USUMODI,'DD/MM/RRRR') F_USUMODI, B_BORRADO, N_SESIONES, (select c_modalidad_id from ocap_meritoscurriculares  where c_merito_id = oe.c_merito_id ) as c_modalidad_id, N_HORAS, F_ANNIO, TO_CHAR(F_FINALIZACION,'DD/MM/RRRR') F_FINALIZACION, B_PARTICIPACION, CLOB_ORGANIZADOR, D_TITULO, D_CAPITULO, N_ISBN, D_EDITORIAL, C_EXP_ID, TO_CHAR(F_INICIO,'DD/MM/RRRR') F_INICIO, A_LUGAR_EXPEDICION, N_DIAS, N_CRED_CURSO, N_ANNIOS, N_MESES, TO_CHAR(F_EXPEDICION,'DD/MM/RRRR') F_EXPEDICION, A_CARGO, A_NOMBRE_REVISTA, A_OBJETIVO, C_EXPMC_ID, (select c_tipo_id from ocap_meritoscurriculares    where c_merito_id=oe.c_merito_id) as c_tipo_id, (select c_factor_id from ocap_meritoscurriculares    where c_merito_id=oe.c_merito_id) as c_factor_id, B_INVALIDADO_CEIS, B_INVALIDADO_CC,B_PDTE_ACLARAR_CEIS, B_PDTE_ACLARAR_CC, TO_CHAR(F_USUALTA,'DD/MM/RRRR') F_USUALTA, oe.D_MOTIVOS_CC FROM OCAP_EXPEDIENTESMCS oe WHERE C_EXPMC_ID = ? ";

			st = con.prepareStatement(sql);
			st.setLong(1, cExpmcId.longValue());

			rs = st.executeQuery();

			datos = new OCAPExpedientemcOT();
			if (rs.next()) {
				datos.setCMeritoId(new Integer(rs.getInt("C_MERITO_ID")));
				datos.setCActividadId(new Integer(rs.getInt("C_ACTIVIDAD_ID")));
				datos.setFModificacion(rs.getString("F_USUMODI"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setNSesiones(new Integer(rs.getInt("N_SESIONES")));
				datos.setCModalidadId(new Integer(rs.getInt("C_MODALIDAD_ID")));
				datos.setNHoras(new Integer(rs.getInt("N_HORAS")));
				datos.setFAnnio(new Integer(rs.getInt("F_ANNIO")));
				datos.setFFinalizacion(rs.getString("F_FINALIZACION"));
				datos.setBParticipacion(rs.getString("B_PARTICIPACION"));
				datos.setAOrganizador(rs.getString("CLOB_ORGANIZADOR"));
				datos.setDTitulo(rs.getString("D_TITULO"));
				datos.setNISBN(rs.getString("N_ISBN"));
				datos.setDEditorial(rs.getString("D_EDITORIAL"));
				datos.setFInicio(rs.getString("F_INICIO"));
				datos.setALugarExpedicion(rs.getString("A_LUGAR_EXPEDICION"));
				datos.setNDias(new Integer(rs.getInt("N_DIAS")));
				datos.setNCreditosCurso(rs.getFloat("N_CRED_CURSO") + "");
				datos.setNAnnios(new Integer(rs.getInt("N_ANNIOS")));
				datos.setNMeses(new Integer(rs.getInt("N_MESES")));
				datos.setFExpedicion(rs.getString("F_EXPEDICION"));
				datos.setACargo(rs.getString("A_CARGO"));
				datos.setAObjetivo(rs.getString("A_OBJETIVO"));
				datos.setCExpmcId(cExpmcId);
				datos.setCExpId(new Long(rs.getLong("C_EXP_ID")));
				datos.setCTipoId(new Integer(rs.getInt("C_TIPO_ID")));
				datos.setCFactorId(new Integer(rs.getInt("C_FACTOR_ID")));
				datos.setFCreacion(rs.getString("F_USUALTA"));
				datos.setANombreRevista(rs.getString("A_NOMBRE_REVISTA"));
				datos.setBInvalidadoCeis(rs.getString("B_INVALIDADO_CEIS"));
				datos.setBInvalidadoCc(rs.getString("B_INVALIDADO_CC"));
				datos.setBPdteAclararCeis(rs.getString("B_PDTE_ACLARAR_CEIS"));
				datos.setBPdteAclararCc(rs.getString("B_PDTE_ACLARAR_CC"));
				datos.setDMotivosCc(rs.getString("D_MOTIVOS_CC"));

				

			}
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null) {
				st.close();
			}
		}

		return datos;
	}

	public long buscarOCAPExpeditentemcRepetidoPorFAnnio(OCAPExpedientemcOT expmcOT) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		long cExpmcId = 0L;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			this.loggerBD.info(" buscarOCAPExpeditentemcRepetidoPorFAnnio");

			String sql = "select c_expmc_id from ocap_expedientesmcs  where C_EXP_ID = ?  AND b_borrado != 'Y' AND C_MERITO_ID IN  (select C_MERITO_ID from ocap_meritoscurriculares   where D_NOMBRE=?   AND C_DEFMERITO_ID=?   AND C_ESTATUT_ID= ? )  AND f_Annio = ?";

			st = con.prepareStatement(sql);
			st.setLong(1, expmcOT.getCExpId().longValue());
			st.setString(2, expmcOT.getDTipoMerito());
			st.setString(3, expmcOT.getCDefMeritoId());
			st.setInt(4, expmcOT.getCEstatutId().intValue());
			st.setInt(5, expmcOT.getFAnnio().intValue());

			rs = st.executeQuery();

			if (rs.next())
				cExpmcId = rs.getLong("C_EXPMC_ID");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return cExpmcId;
	}

	public long buscarOCAPExpeditentemcPdtesAclararPorExpId(long cExpId, String perfil, Connection con)
			throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		ArrayList listado = null;
		OCAPExpedientemcOT datos = null;
		long contador = 0L;
		try {
			this.loggerBD.info("buscarOCAPExpeditentemcPdtesAclararPorExpId: " + cExpId);

			String sql = "SELECT count(oex.C_EXPMC_ID) as contador FROM OCAP_EXPEDIENTESMCS oex WHERE oex.C_EXP_ID = ?  AND oex.B_BORRADO = 'N' ";
			if (Constantes.OCAP_CEIS.equals(perfil))
				sql = sql + " AND oex.B_PDTE_ACLARAR_CEIS = 'Y'";
			else {
				sql = sql + " AND oex.B_PDTE_ACLARAR_CC = 'Y'";
			}
			st = con.prepareStatement(sql);
			st.setLong(1, cExpId);

			rs = st.executeQuery();

			if (rs.next())
				contador = rs.getLong("contador");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null) {
				st.close();
			}
		}

		return contador;
	}

	public int eliminarAclaracionesInvalidarOCAPExpeditentemc(long cExpId, String perfil)
			throws SQLException, Exception {
		PreparedStatement st = null;
		int contador = 0;
		Connection con = null;
		StringBuffer sql = new StringBuffer();
		try {
			this.loggerBD.info(" eliminarAclaracionesInvalidarOCAPExpeditentemc: " + cExpId);

			con = JCYLGestionTransacciones.getConnection();

			sql.append("UPDATE OCAP_EXPEDIENTESMCS ").append("set B_PDTE_ACLARAR_CC='N', ")
					.append("  B_INVALIDADO_CC='Y' ");
			if (Constantes.OCAP_CEIS.equals(perfil)) {
				sql.append(", B_PDTE_ACLARAR_CEIS='N' ").append(", B_INVALIDADO_CEIS='Y' ");
			}

			sql.append(" WHERE C_EXP_ID = ? ").append(" AND B_BORRADO = 'N' ");

			if (Constantes.OCAP_CEIS.equals(perfil))
				sql.append(" AND B_PDTE_ACLARAR_CEIS = 'Y'");
			else {
				sql.append(" AND B_PDTE_ACLARAR_CC = 'Y'");
			}
			st = con.prepareStatement(sql.toString());
			st.setLong(1, cExpId);

			contador = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return contador;
	}

	/**
	 * @param expmcOT Expediente
	 * @param con Conexion
	 * @param opcional  Flag indicando si se filtra por Opcionales (O), No opcionales (N) o TODOS (T)  
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList buscarOCAPExpeditentemcPorMeritoId(OCAPExpedientemcOT expmcOT, Connection con,String opcional)
			throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		ArrayList listado = null;
		OCAPExpedientemcOT datos = null;
		StringBuilder sql = new StringBuilder("");
		try {
			this.loggerBD.info(" buscarOCAPExpeditentemcPorMeritoId: con " + con);

			 sql.append("SELECT oex.C_EXP_ID, oex.C_EXPMC_ID, oex.C_MERITO_ID, omc.C_ACTIVIDAD_ID, omc.C_MODALIDAD_ID, oex.D_TITULO, TO_CHAR(oex.F_USUMODI,'DD/MM/RRRR') F_USUMODI, TO_CHAR(oex.F_USUALTA,'DD/MM/RRRR') F_USUALTA, TO_CHAR(oex.F_INICIO,'DD/MM/RRRR') F_INICIO, TO_CHAR(oex.F_FINALIZACION,'DD/MM/RRRR') F_FINALIZACION, oex.CLOB_ORGANIZADOR, oex.F_ANNIO, oex.N_SESIONES, TO_CHAR(oex.F_EXPEDICION,'DD/MM/RRRR') F_EXPEDICION, oex.A_LUGAR_EXPEDICION, oex.N_HORAS, oex.N_DIAS, oex.B_PARTICIPACION, oex.B_OPCIONAL, oex.A_CARGO, oex.A_OBJETIVO, oex.N_ANNIOS, oex.N_MESES, oex.A_NOMBRE_REVISTA, omc.C_TIPO_MERITO, omc.C_DEFMERITO_ID, omc.N_CREDITOS, oex.B_INVALIDADO_CEIS, oex.B_INVALIDADO_CC, oex.B_PDTE_ACLARAR_CEIS, oex.B_PDTE_ACLARAR_CC, oex.N_CRED_USUARIO FROM OCAP_EXPEDIENTESMCS oex, ocap_meritoscurriculares omc WHERE omc.C_ESTATUT_ID = ?  AND omc.C_DEFMERITO_ID = ?  AND omc.D_NOMBRE = ?  AND oex.C_EXP_ID = ?  AND oex.B_BORRADO = 'N'  AND oex.C_MERITO_ID = omc.C_MERITO_ID ");
			 
			 if (opcional.equals(Constantes.FLAG_MERITOS_OPCIONALES)){
				 
				 sql.append(" and oex.b_opcional='Y' ");
				 
			 }else if (opcional.equals(Constantes.FLAG_MERITOS_NO_OPCIONALES)){
				 sql.append(" and oex.b_opcional='N' ");
			 }
			 
			 sql.append(" ORDER BY B_OPCIONAL, c_defmerito_id ");
			 

			st = con.prepareStatement(sql.toString());
			int cont = 1;
			st.setLong(cont++, expmcOT.getCEstatutId().longValue());
			st.setLong(cont++, Long.parseLong(expmcOT.getCDefMeritoId()));
			st.setString(cont++, expmcOT.getDTipoMerito());
			st.setLong(cont++, expmcOT.getCExpId().longValue());

			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				datos = new OCAPExpedientemcOT();
				datos.setCExpId(new Long(rs.getString("C_EXP_ID")));
				datos.setCExpmcId(new Long(rs.getString("C_EXPMC_ID")));
				datos.setCMeritoId(new Integer(rs.getInt("C_MERITO_ID")));
				datos.setCActividadId(new Integer(rs.getInt("C_ACTIVIDAD_ID")));
				datos.setCModalidadId(new Integer(rs.getInt("C_MODALIDAD_ID")));
				datos.setDTitulo(rs.getString("D_TITULO"));
				datos.setFModificacion(rs.getString("F_USUMODI"));
				datos.setFCreacion(rs.getString("F_USUALTA"));
				datos.setFInicio(rs.getString("F_INICIO"));
				datos.setFFinalizacion(rs.getString("F_FINALIZACION"));
				datos.setAOrganizador(rs.getString("CLOB_ORGANIZADOR"));
				datos.setFAnnio(new Integer(rs.getInt("F_ANNIO")));
				datos.setNSesiones(new Integer(rs.getInt("N_SESIONES")));
				datos.setFExpedicion(rs.getString("F_EXPEDICION"));
				datos.setALugarExpedicion(rs.getString("A_LUGAR_EXPEDICION"));
				datos.setNHoras(new Integer(rs.getInt("N_HORAS")));
				datos.setNDias(new Integer(rs.getInt("N_DIAS")));
				datos.setBParticipacion(rs.getString("B_PARTICIPACION"));
				datos.setBOpcional(rs.getString("B_OPCIONAL"));
				datos.setACargo(rs.getString("A_CARGO"));
				datos.setAObjetivo(rs.getString("A_OBJETIVO"));
				datos.setNAnnios(new Integer(rs.getInt("N_ANNIOS")));
				datos.setNMeses(new Integer(rs.getInt("N_MESES")));
				datos.setANombreRevista(rs.getString("A_NOMBRE_REVISTA"));
				datos.setCTipoMerito(rs.getString("C_TIPO_MERITO"));
				if (Constantes.SI.equals(datos.getBOpcional()))
					datos.setCDefMeritoId("5");
				else {
					datos.setCDefMeritoId(rs.getString("C_DEFMERITO_ID"));
				}
				datos.setBInvalidadoCeis(rs.getString("B_INVALIDADO_CEIS"));
				datos.setBInvalidadoCc(rs.getString("B_INVALIDADO_CC"));
				datos.setBPdteAclararCeis(rs.getString("B_PDTE_ACLARAR_CEIS"));
				datos.setBPdteAclararCc(rs.getString("B_PDTE_ACLARAR_CC"));

				datos.setNCreditos(new Float(rs.getFloat("n_cred_usuario")));
				listado.add(datos);
			}
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null) {
				st.close();
			}
		}

		return listado;
	}

	public ArrayList listadoOCAPExpeditentemc() throws SQLException {
		return listadoOCAPExpeditentemc(1, -1);
	}

	public ArrayList listadoOCAPExpeditentemc(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String sql = "SELECT C_MERITO_ID, (select c_actividad_id from ocap_meritoscurriculares where c_merito_id = oe.c_merito_id ) as c_actividad_id, TO_CHAR(F_USUMODI,'DD/MM/RRRR'), B_BORRADO, N_SESIONES, (select c_modalidad_id from ocap_meritoscurriculares where c_merito_id = oe.c_merito_id ) as c_modalidad_id, N_HORAS, F_ANNIO, TO_CHAR(F_FINALIZACION,'DD/MM/RRRR'), B_PARTICIPACION, CLOB_ORGANIZADOR, D_TITULO, C_EXP_ID, TO_CHAR(F_INICIO,'DD/MM/RRRR'), A_LUGAR_EXPEDICION, N_DIAS, TO_CHAR(F_EXPEDICION,'DD/MM/RRRR'), B_OPCIONAL, C_EXPMC_ID, TO_CHAR(F_USUALTA,'DD/MM/RRRR') FROM OCAP_EXPEDITENTEMC oe";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {

				OCAPExpedientemcOT datos = new OCAPExpedientemcOT();
				datos.setBOpcional(rs.getString("B_OPCIONAL"));
				datos.setCMeritoId(new Integer(rs.getInt("C_MERITO_ID")));
				datos.setCActividadId(new Integer(rs.getInt("C_ACTIVIDAD_ID")));
				datos.setFModificacion(rs.getString("F_USUMODI"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setNSesiones(new Integer(rs.getInt("N_SESIONES")));
				datos.setCModalidadId(new Integer(rs.getInt("C_MODALIDAD_ID")));
				datos.setNHoras(new Integer(rs.getInt("N_HORAS")));
				datos.setFAnnio(new Integer(rs.getInt("F_ANNIO")));
				datos.setFFinalizacion(rs.getString("F_FINALIZACION"));
				datos.setBParticipacion(rs.getString("B_PARTICIPACION"));
				datos.setAOrganizador(rs.getString("CLOB_ORGANIZADOR"));
				datos.setDTitulo(rs.getString("D_TITULO"));
				datos.setFInicio(rs.getString("F_INICIO"));
				datos.setALugarExpedicion(rs.getString("A_LUGAR_EXPEDICION"));
				datos.setNDias(new Integer(rs.getInt("N_DIAS")));
				datos.setFExpedicion(rs.getString("F_EXPEDICION"));
				datos.setFCreacion(rs.getString("F_USUALTA"));

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

	public int modificarCreditosMerito(OCAPExpedientemcOT datos, JCYLUsuario jcylUsuario, boolean modificarHoras)
			throws SQLException, Exception {
		PreparedStatement st = null;
		int filas = 0;
		Connection con = JCYLGestionTransacciones.getConnection();
		StringBuffer consulta = new StringBuffer();
		try {
			this.loggerBD.info(" modificarCreditosMerito");

			consulta.append("UPDATE OCAP_EXPEDIENTESMCS ");
			if(modificarHoras) {
				consulta.append(" SET N_HORAS = ?, D_MOTIVOS_CC = ?,");
			}else {
				if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {
					consulta.append(" SET N_CRED_CC= ?, D_MOTIVOS_CC = ?,");
				}else if(Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())){
					consulta.append(" SET N_CRED_CEIS= ?,D_MOTIVOS_CEIS =?,N_CRED_CC = ?,");
				}
			}
			

			consulta.append(" F_USUMODI = SYSDATE,C_USUMODI=?  ").append("WHERE C_EXPMC_ID = ?").append(" AND B_BORRADO = 'N' ");

			st = con.prepareStatement(consulta.toString());
			int cont = 1;
			if(modificarHoras) {
				st.setInt(cont++, datos.getNHoras());
				st.setString(cont++,datos.getDMotivosCc() );
			}else {
				if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {
					st.setFloat(cont++, datos.getNCredCc().floatValue());
					st.setString(cont++,datos.getDMotivosCc() );
				}else if(Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())){
					st.setFloat(cont++, datos.getNCredCeis().floatValue());
					st.setString(cont++, datos.getDMotivosCeis());
					st.setFloat(cont++, datos.getNCredCc().floatValue());
				}
			}
			st.setString(cont++, (jcylUsuario.getUser().getC_usr_id()));
			st.setLong(cont++, datos.getCExpmcId().longValue());

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
	
	public OCAPExpedientemcOT buscarOCAPExpeditentemcModif(Long cExpmcId, Connection con) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		OCAPExpedientemcOT datos = null;
		try {
			this.loggerBD.info(" buscarOCAPExpeditentemc: " + cExpmcId + " con: " + con);
			String sql = "SELECT D_MOTIVOS_CEIS,D_MOTIVOS_CC FROM OCAP_EXPEDIENTESMCS oe WHERE C_EXPMC_ID = ? ";

			st = con.prepareStatement(sql);
			st.setLong(1, cExpmcId.longValue());

			rs = st.executeQuery();

			datos = new OCAPExpedientemcOT();
			if (rs.next()) {
				datos.setDMotivosCeis(rs.getString("D_MOTIVOS_CEIS"));
				datos.setDMotivosCc(rs.getString("D_MOTIVOS_CC"));

				

			}
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null) {
				st.close();
			}
		}

		return datos;
	}
}
