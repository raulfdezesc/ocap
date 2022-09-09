package es.jcyl.fqs.ocap.oad.convocatorias;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
import es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPConvocatoriasOAD {
	public Logger loggerBD;
	private static OCAPConvocatoriasOAD instance;

	private void $init$() {
		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public static OCAPConvocatoriasOAD getInstance() {
		if (instance == null) {
			instance = new OCAPConvocatoriasOAD();
		}
		return instance;
	}

	private OCAPConvocatoriasOAD() {
		$init$();
	}

	public int altaOCAPConvocatorias(OCAPConvocatoriasOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_CONVOCATORIAS (C_CONVOCATORIA_ID, B_BORRADO, A_OBSERVACIONES, D_NOMBRE, F_USUALTA, F_RESOLUCION, F_PUBLICACION, N_DIAS_REGSOLICITUD, N_DIAS_REVSOLICITUD,  N_DIAS_PUBLISTADO, N_DIAS_AUTO_MC, N_DIAS_VAL_MC, N_DIAS_INCONF_MC, N_DIAS_AUTO_CA, N_DIAS_VAL_CA, N_DIAS_INCONF_CA, N_DIAS_VAL_CC, N_DIAS_RESPINCONF_MC, N_DIAS_RESPINCONF_CA, N_DIAS_RESPINCONF_CC, C_USUALTA, F_INICIOMC, F_ALEGSOLICITUD, F_ESTUDIO_CC, F_FINCP, F_REC_GRADO, F_INICIOCA, B_CIERRE_SO, F_FIN_ESTUDIO_CC,F_INI_SOLICITUD,F_FIN_SOLICITUD,F_INI_VAL_MC,F_FIN_VAL_MC,F_INI_VAL_CC,F_FIN_VAL_CC,F_FIN_MC,F_FIN_CA,F_INI_VAL_CA,F_FIN_VAL_CA,D_ANIO_CONVOCATORIA, D_NOMBRE_CORTO, C_GRADO_ID, F_FIN_PGP) VALUES (OCAP_CON_ID_SEQ.nextval, 'N', ?, ?,  SYSDATE, TO_DATE(?,'DD/MM/RRRR'), TO_DATE(?,'DD/MM/RRRR'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, TO_DATE(?,'DD/MM/RRRR'), TO_DATE(?,'DD/MM/RRRR'), TO_DATE(?,'DD/MM/RRRR'), TO_DATE(?,'DD/MM/RRRR'), TO_DATE(?,'DD/MM/RRRR'), TO_DATE(?,'DD/MM/RRRR'), ?, TO_DATE(?,'DD/MM/RRRR'),TO_DATE(?,'DD/MM/RRRR'),TO_DATE(?,'DD/MM/RRRR'),TO_DATE(?,'DD/MM/RRRR'),TO_DATE(?,'DD/MM/RRRR'),TO_DATE(?,'DD/MM/RRRR'),TO_DATE(?,'DD/MM/RRRR'),TO_DATE(?,'DD/MM/RRRR'),TO_DATE(?,'DD/MM/RRRR'),TO_DATE(?,'DD/MM/RRRR'),TO_DATE(?,'DD/MM/RRRR'),?,?,?,TO_DATE(?,'DD/MM/RRRR'))";

			st = con.prepareStatement(sql);

			if (datos.getAObservaciones() != null)
				st.setString(1, datos.getAObservaciones());
			else
				st.setNull(1, 12);
			st.setString(2, datos.getDNombre());
			st.setString(3, datos.getFResolucion());
			st.setString(4, datos.getFPublicacion());
			st.setLong(5, datos.getNDiasRegsolicitud());
			st.setLong(6, datos.getNDiasRevsolicitud());
			st.setLong(7, datos.getNDiasPublistado());
			st.setLong(8, datos.getNDiasAutoMc());
			st.setLong(9, datos.getNDiasValMc());
			st.setLong(10, datos.getNDiasInconfMc());
			st.setLong(11, datos.getNDiasAutoCa());
			st.setLong(12, datos.getNDiasValCa());
			st.setLong(13, datos.getNDiasInconfCa());
			st.setLong(14, datos.getNDiasValCc());
			st.setLong(15, datos.getNDiasRespinconfMc());
			st.setLong(16, datos.getNDiasRespinconfCa());
			st.setLong(17, datos.getNDiasRespinconfCc());
			st.setString(18, datos.getACreadoPor());
			st.setString(19, datos.getFInicioMC());
			st.setString(20, datos.getFAlegsolicitud());
			st.setString(21, datos.getFInicioEstudioCC());
			st.setString(22, datos.getFFinCp());
			st.setString(23, datos.getFRecGrado());
			st.setString(24, datos.getFInicioCA());
			st.setString(25, datos.getBCierreSo());
			st.setString(26, datos.getFInicioEstudioCC());
				
			st.setString(27, datos.getFInicioSolicitud());
			st.setString(28, datos.getFFinSolicitud());
			st.setString(29, datos.getFInicioValoracionMC());
			st.setString(30, datos.getFFinValoracionMC());
			st.setString(31, datos.getFInicioValoracionCC());
			st.setString(32, datos.getFFinValoracionCC());
			st.setString(33, datos.getFFinMC());
			st.setString(34, datos.getFFinCA());
			st.setString(35, datos.getFInicioValCA());
			st.setString(36, datos.getFFinValCA());
			st.setString(37, datos.getDAnioConvocatoria());
			st.setString(38, datos.getDNombreCorto());
			st.setLong(39, datos.getCGradoId());
			
			if (datos.getFFinPGP() != null)
				st.setString(40, datos.getFFinPGP());
			else
				st.setNull(40, 12);
			
			
			

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

	public int bajaOCAPConvocatorias(long cConvocatoriaId) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			String sql = "UPDATE OCAP_CONVOCATORIAS SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_CONVOCATORIA_ID = ?";
			st = con.prepareStatement(sql);
			st.setLong(1, cConvocatoriaId);
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

	public int modificacionOCAPConvocatorias(OCAPConvocatoriasOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			String sql = "UPDATE OCAP_CONVOCATORIAS SET D_NOMBRE = ?, F_RESOLUCION = to_date('" + datos.getFResolucion()
					+ "','DD/MM/YYYY'), " + "F_PUBLICACION = to_date('" + datos.getFPublicacion() + "','DD/MM/YYYY'), ";
			
			
			if (datos.getFInicioMC() != null)
				sql = sql + "F_INICIOMC = to_date('" + datos.getFInicioMC() + "','DD/MM/YYYY'), ";
			else {
				sql = sql + "F_INICIOMC = ?, ";
			}
			if (datos.getFAlegsolicitud() != null)
				sql = sql + "F_ALEGSOLICITUD = to_date('" + datos.getFAlegsolicitud() + "','DD/MM/YYYY'), ";
			else {
				sql = sql + "F_ALEGSOLICITUD = ?, ";
			}
			if ((datos.getFInicioEstudioCC() != null) && (!datos.getFInicioEstudioCC().equals(""))) {
				sql = sql + "F_ESTUDIO_CC = to_date('" + datos.getFInicioEstudioCC() + "','DD/MM/YYYY'), ";
			}
			if ((datos.getFFinEstudioCC() != null) && (!datos.getFFinEstudioCC().equals(""))) {
				sql = sql + "F_FIN_ESTUDIO_CC = to_date('" + datos.getFFinEstudioCC() + "','DD/MM/YYYY'), ";
			}
			if (datos.getFInicioCA() != null)
				sql = sql + "F_INICIOCA = to_date('" + datos.getFInicioCA() + "','DD/MM/YYYY'), ";
			else {
				sql = sql + "F_INICIOCA = ?, ";
			}
			if (datos.getFFinCp() != null)
				sql = sql + "F_FINCP = to_date('" + datos.getFFinCp() + "','DD/MM/YYYY'), ";
			else {
				sql = sql + "F_FINCP = ?, ";
			}
			if (datos.getFRecGrado() != null)
				sql = sql + "F_REC_GRADO = to_date('" + datos.getFRecGrado() + "','DD/MM/YYYY'), ";
			else {
				sql = sql + "F_REC_GRADO = ?, ";
			}
			if (datos.getFInicioSolicitud() != null) {
				sql = sql + "F_INI_SOLICITUD = to_date('" + datos.getFInicioSolicitud() + "','DD/MM/YYYY'), ";
			} else {
				sql = sql + "F_INI_SOLICITUD = ?, ";
			}
			if (datos.getFFinSolicitud() != null) {
				sql = sql + "F_FIN_SOLICITUD = to_date('" + datos.getFFinSolicitud() + "','DD/MM/YYYY'), ";
			} else {
				sql = sql + "F_FIN_SOLICITUD = ?, ";
			}
			if (datos.getFInicioValoracionMC() != null) {
				sql = sql + "F_INI_VAL_MC = to_date('" + datos.getFInicioValoracionMC() + "','DD/MM/YYYY'), ";
			} else {
				sql = sql + "F_INI_VAL_MC = ?, ";
			}
			if (datos.getFFinValoracionMC() != null) {
				sql = sql + "F_FIN_VAL_MC = to_date('" + datos.getFFinValoracionMC() + "','DD/MM/YYYY'), ";
			} else {
				sql = sql + "F_FIN_VAL_MC = ?, ";
			}
			if (datos.getFInicioValoracionCC() != null) {
				sql = sql + "F_INI_VAL_CC = to_date('" + datos.getFInicioValoracionCC() + "','DD/MM/YYYY'), ";
			} else {
				sql = sql + "F_INI_VAL_CC = ?, ";
			}
			if (datos.getFFinValoracionCC() != null) {
				sql = sql + "F_FIN_VAL_CC = to_date('" + datos.getFFinValoracionCC() + "','DD/MM/YYYY'), ";
			} else {
				sql = sql + "F_FIN_VAL_CC = ?, ";
			}
			if (datos.getFFinMC() != null) {
				sql = sql + "F_FIN_MC = to_date('" + datos.getFFinMC() + "','DD/MM/YYYY'), ";
			} else {
				sql = sql + "F_FIN_MC = ?, ";
			}
			if (datos.getFFinCA() != null) {
				sql = sql + "F_FIN_CA = to_date('" + datos.getFFinCA() + "','DD/MM/YYYY'), ";
			} else {
				sql = sql + "F_FIN_CA = ?, ";
			}
			if (datos.getFInicioValCA() != null) {
				sql = sql + "F_INI_VAL_CA = to_date('" + datos.getFInicioValCA() + "','DD/MM/YYYY'), ";
			} else {
				sql = sql + "F_INI_VAL_CA = ?, ";
			}
			if (datos.getFFinValCA() != null) {
				sql = sql + "F_FIN_VAL_CA = to_date('" + datos.getFFinValCA() + "','DD/MM/YYYY'), ";
			} else {
				sql = sql + "F_FIN_VAL_CA = ?, ";
			}
			
			if (datos.getFFinPGP() != null)
				sql = sql + "F_FIN_PGP = to_date('" + datos.getFFinPGP() + "','DD/MM/YYYY'), ";
			else {
				sql = sql + "F_FIN_PGP = ?, ";
			}
			
			sql = sql
					+ "N_DIAS_REGSOLICITUD = ?, N_DIAS_REVSOLICITUD = ?, N_DIAS_PUBLISTADO = ?, N_DIAS_AUTO_MC = ?, N_DIAS_VAL_MC = ?, N_DIAS_INCONF_MC = ?, N_DIAS_AUTO_CA = ?, N_DIAS_VAL_CA = ?, N_DIAS_INCONF_CA = ?, N_DIAS_VAL_CC = ?, A_OBSERVACIONES = ?, N_DIAS_RESPINCONF_MC = ?, N_DIAS_RESPINCONF_CA = ?, N_DIAS_RESPINCONF_CC = ?, F_USUMODI = SYSDATE, B_CIERRE_SO = ? , D_ANIO_CONVOCATORIA=? , D_NOMBRE_CORTO =? WHERE C_CONVOCATORIA_ID = ?";

			st = con.prepareStatement(sql);

			int cont = 1;
			st.setString(cont++, datos.getDNombre());
			if ((datos.getFInicioMC() == null) || (datos.getFInicioMC().equals(""))) {
				st.setNull(cont++, java.sql.Types.DATE);
			}
			if ((datos.getFAlegsolicitud() == null) || (datos.getFAlegsolicitud().equals(""))) {
				st.setNull(cont++, java.sql.Types.DATE);
			}
			if ((datos.getFInicioCA() == null) || (datos.getFInicioCA().equals(""))) {
				st.setNull(cont++, java.sql.Types.DATE);
			}
			if ((datos.getFFinCp() == null) || (datos.getFFinCp().equals(""))) {
				st.setNull(cont++, java.sql.Types.DATE);
			}
			if ((datos.getFRecGrado() == null) || (datos.getFRecGrado().equals(""))) {
				st.setNull(cont++, java.sql.Types.DATE);
			}
			if ((datos.getFInicioSolicitud() == null) || (datos.getFInicioSolicitud().equals(""))) {
				st.setNull(cont++, java.sql.Types.DATE);
			}
			if ((datos.getFFinSolicitud() == null) || (datos.getFFinSolicitud().equals(""))) {
				st.setNull(cont++, java.sql.Types.DATE);
			}
			if ((datos.getFInicioValoracionMC() == null) || (datos.getFInicioValoracionMC().equals(""))) {
				st.setNull(cont++, java.sql.Types.DATE);
			}
			if ((datos.getFFinValoracionMC() == null) || (datos.getFFinValoracionMC().equals(""))) {
				st.setNull(cont++, java.sql.Types.DATE);
			}
			if ((datos.getFInicioValoracionCC() == null) || (datos.getFInicioValoracionCC().equals(""))) {
				st.setNull(cont++, java.sql.Types.DATE);
			}
			if ((datos.getFFinValoracionCC() == null) || (datos.getFFinValoracionCC().equals(""))) {
				st.setNull(cont++, java.sql.Types.DATE);
			}
			if ((datos.getFFinMC() == null) || (datos.getFFinMC().equals(""))) {
				st.setNull(cont++, java.sql.Types.DATE);
			}
			if ((datos.getFFinCA() == null) || (datos.getFFinCA().equals(""))) {
				st.setNull(cont++, java.sql.Types.DATE);
			}
			if ((datos.getFInicioValCA() == null) || (datos.getFInicioValCA().equals(""))) {
				st.setNull(cont++, java.sql.Types.DATE);
			}
			if ((datos.getFFinValCA() == null) || (datos.getFFinValCA().equals(""))) {
				st.setNull(cont++, java.sql.Types.DATE);
			}
			if ((datos.getFFinPGP() == null) || (datos.getFFinPGP().equals(""))) {
				st.setNull(cont++, java.sql.Types.DATE);
			}
			st.setLong(cont++, datos.getNDiasRegsolicitud());
			st.setLong(cont++, datos.getNDiasRevsolicitud());
			st.setLong(cont++, datos.getNDiasPublistado());
			st.setLong(cont++, datos.getNDiasAutoMc());
			st.setLong(cont++, datos.getNDiasValMc());
			st.setLong(cont++, datos.getNDiasInconfMc());
			st.setLong(cont++, datos.getNDiasAutoCa());
			st.setLong(cont++, datos.getNDiasValCa());
			st.setLong(cont++, datos.getNDiasInconfCa());
			st.setLong(cont++, datos.getNDiasValCc());
			if (datos.getAObservaciones() != null)
				st.setString(cont++, datos.getAObservaciones());
			else
				st.setNull(cont++, 12);
			st.setLong(cont++, datos.getNDiasRespinconfMc());
			st.setLong(cont++, datos.getNDiasRespinconfCa());
			st.setLong(cont++, datos.getNDiasRespinconfCc());
			st.setString(cont++, datos.getBCierreSo());
			st.setString(cont++, datos.getDAnioConvocatoria());
			st.setString(cont++, datos.getDNombreCorto());
			st.setLong(cont++, datos.getCConvocatoriaId());
			

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

	public OCAPConvocatoriasOT buscarOCAPConvocatorias(long cConvocatoriaId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		OCAPConvocatoriasOT datos = null;
		try {
			this.loggerBD.info(getClass().getName() + " buscarOCAPConvocatorias: " + cConvocatoriaId);

			String sql = "SELECT C_CONVOCATORIA_ID, D_NOMBRE, D_NOMBRE_CORTO, to_char(F_RESOLUCION,'DD/MM/YYYY') as F_RESOLUCION, to_char(F_PUBLICACION,'DD/MM/YYYY') as F_PUBLICACION,to_char(F_FIN_PGP,'DD/MM/YYYY') as F_FIN_PGP, to_char(F_INICIOMC,'DD/MM/YYYY') as F_INICIOMC, to_char(F_ALEGSOLICITUD,'DD/MM/YYYY') as F_ALEGSOLICITUD, to_char(F_ESTUDIO_CC,'DD/MM/YYYY') as F_ESTUDIO_CC, to_char(F_FIN_ESTUDIO_CC,'DD/MM/YYYY') as F_FIN_ESTUDIO_CC, to_char(F_INICIOCA,'DD/MM/YYYY') as F_INICIOCA, to_char(F_FINCP,'DD/MM/YYYY') as F_FINCP, to_char(F_REC_GRADO,'DD/MM/YYYY') as F_REC_GRADO, N_DIAS_REGSOLICITUD, N_DIAS_REVSOLICITUD, N_DIAS_PUBLISTADO, N_DIAS_AUTO_MC, N_DIAS_VAL_MC, N_DIAS_INCONF_MC, N_DIAS_AUTO_CA, N_DIAS_VAL_CA, N_DIAS_INCONF_CA, N_DIAS_VAL_CC, N_DIAS_RESPINCONF_MC, N_DIAS_RESPINCONF_CA, N_DIAS_RESPINCONF_CC, A_OBSERVACIONES, to_char(F_USUMODI,'DD/MM/YYYY') as F_USUMODI, to_char(F_USUALTA,'DD/MM/YYYY') as F_USUALTA, B_CIERRE_SO, TO_CHAR(F_INI_SOLICITUD,'DD/MM/YYYY') AS F_INI_SOLICITUD,TO_CHAR(F_FIN_SOLICITUD,'DD/MM/YYYY') AS F_FIN_SOLICITUD,TO_CHAR(F_INI_VAL_MC,'DD/MM/YYYY') AS F_INI_VAL_MC,TO_CHAR(F_FIN_VAL_MC,'DD/MM/YYYY') AS F_FIN_VAL_MC,TO_CHAR(F_INI_VAL_CC,'DD/MM/YYYY') AS F_INI_VAL_CC,TO_CHAR(F_FIN_VAL_CC,'DD/MM/YYYY') AS F_FIN_VAL_CC,TO_CHAR(F_FIN_MC,'DD/MM/YYYY') AS F_FIN_MC,TO_CHAR(F_FIN_CA,'DD/MM/YYYY') AS F_FIN_CA,TO_CHAR(F_INI_VAL_CA,'DD/MM/YYYY') AS F_INI_VAL_CA,TO_CHAR(F_FIN_VAL_CA,'DD/MM/YYYY') AS F_FIN_VAL_CA, D_ANIO_CONVOCATORIA, C_GRADO_ID FROM OCAP_CONVOCATORIAS WHERE C_CONVOCATORIA_ID = ? AND B_BORRADO = 'N' ORDER BY D_NOMBRE ";
			
			st = con.prepareStatement(sql);
			st.setLong(1, cConvocatoriaId);
			rs = st.executeQuery();
			datos = new OCAPConvocatoriasOT();
			if (rs.next()) {
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDNombreCorto(rs.getString("D_NOMBRE_CORTO"));
				datos.setFResolucion(rs.getString("F_RESOLUCION"));
				datos.setFPublicacion(rs.getString("F_PUBLICACION"));
				datos.setFInicioMC(rs.getString("F_INICIOMC"));
				datos.setFAlegsolicitud(rs.getString("F_ALEGSOLICITUD"));
				datos.setFInicioEstudioCC(rs.getString("F_ESTUDIO_CC"));
				datos.setFFinEstudioCC(rs.getString("F_FIN_ESTUDIO_CC"));
				datos.setFInicioCA(rs.getString("F_INICIOCA"));
				datos.setFFinCp(rs.getString("F_FINCP"));
				datos.setFRecGrado(rs.getString("F_REC_GRADO"));
				datos.setNDiasRegsolicitud(rs.getLong("N_DIAS_REGSOLICITUD"));
				datos.setNDiasRevsolicitud(rs.getLong("N_DIAS_REVSOLICITUD"));
				datos.setNDiasPublistado(rs.getLong("N_DIAS_PUBLISTADO"));
				datos.setNDiasAutoMc(rs.getLong("N_DIAS_AUTO_MC"));
				datos.setNDiasValMc(rs.getLong("N_DIAS_VAL_MC"));
				datos.setNDiasInconfMc(rs.getLong("N_DIAS_INCONF_MC"));
				datos.setNDiasAutoCa(rs.getLong("N_DIAS_AUTO_CA"));
				datos.setNDiasValCa(rs.getLong("N_DIAS_VAL_CA"));
				datos.setNDiasInconfCa(rs.getLong("N_DIAS_INCONF_CA"));
				datos.setNDiasValCc(rs.getLong("N_DIAS_VAL_CC"));
				datos.setNDiasRespinconfMc(rs.getLong("N_DIAS_RESPINCONF_MC"));
				datos.setNDiasRespinconfCa(rs.getLong("N_DIAS_RESPINCONF_CA"));
				datos.setNDiasRespinconfCc(rs.getLong("N_DIAS_RESPINCONF_CC"));
				datos.setAObservaciones(rs.getString("A_OBSERVACIONES"));
				datos.setFModificacion(rs.getString("F_USUMODI"));
				datos.setFCreacion(rs.getString("F_USUALTA"));
				datos.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
				datos.setBCierreSo(rs.getString("B_CIERRE_SO"));
				datos.setFInicioSolicitud(rs.getString("F_INI_SOLICITUD"));
				datos.setFFinSolicitud(rs.getString("F_FIN_SOLICITUD"));
				datos.setFInicioValoracionMC(rs.getString("F_INI_VAL_MC"));
				datos.setFFinValoracionMC(rs.getString("F_FIN_VAL_MC"));
				datos.setFInicioValoracionCC(rs.getString("F_INI_VAL_CC"));
				datos.setFFinValoracionCC(rs.getString("F_FIN_VAL_CC"));
				datos.setFFinMC(rs.getString("F_FIN_MC"));
				datos.setFFinCA(rs.getString("F_FIN_CA"));
				datos.setFInicioValCA(rs.getString("F_INI_VAL_CA"));
				datos.setFFinValCA(rs.getString("F_FIN_VAL_CA"));
				datos.setDAnioConvocatoria(rs.getString("D_ANIO_CONVOCATORIA"));
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setFFinPGP(rs.getString("F_FIN_PGP"));
				
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

	public ArrayList listadoOCAPConvocatorias() throws SQLException, Exception {
		return listadoOCAPConvocatorias(1, -1);
	}

	public ArrayList listadoOCAPConvocatorias(int inicio, int cuantos) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String sql = "SELECT C_CONVOCATORIA_ID, D_NOMBRE, to_char(F_RESOLUCION,'DD/MM/YYYY') as F_RESOLUCION, to_char(F_PUBLICACION,'DD/MM/YYYY') as F_PUBLICACION, to_char(F_INICIOMC,'DD/MM/YYYY') as F_INICIOMC, to_char(F_ALEGSOLICITUD,'DD/MM/YYYY') as F_ALEGSOLICITUD, to_char(F_ESTUDIO_CC,'DD/MM/YYYY') as F_ESTUDIO_CC, to_char(F_FIN_ESTUDIO_CC,'DD/MM/YYYY') as F_FIN_ESTUDIO_CC, to_char(F_INICIOCA,'DD/MM/YYYY') as F_INICIOCA, to_char(F_FINCP,'DD/MM/YYYY') as F_FINCP, to_char(F_REC_GRADO,'DD/MM/YYYY') as F_REC_GRADO, N_DIAS_REGSOLICITUD, N_DIAS_REVSOLICITUD, N_DIAS_PUBLISTADO, N_DIAS_AUTO_MC, N_DIAS_VAL_MC, N_DIAS_INCONF_MC, N_DIAS_AUTO_CA, N_DIAS_VAL_CA, N_DIAS_INCONF_CA, N_DIAS_VAL_CC, N_DIAS_RESPINCONF_MC, N_DIAS_RESPINCONF_CA, N_DIAS_RESPINCONF_CC, A_OBSERVACIONES, to_char(F_USUMODI,'DD/MM/YYYY') as F_USUMODI, to_char(F_USUALTA,'DD/MM/YYYY') as F_USUALTA , TO_CHAR(F_INI_SOLICITUD,'DD/MM/YYYY') AS F_INI_SOLICITUD,TO_CHAR(F_FIN_SOLICITUD,'DD/MM/YYYY') AS F_FIN_SOLICITUD,TO_CHAR(F_INI_VAL_MC,'DD/MM/YYYY') AS F_INI_VAL_MC,TO_CHAR(F_FIN_VAL_MC,'DD/MM/YYYY') AS F_FIN_VAL_MC,TO_CHAR(F_INI_VAL_CC,'DD/MM/YYYY') AS F_INI_VAL_CC,TO_CHAR(F_FIN_VAL_CC,'DD/MM/YYYY') AS F_FIN_VAL_CC,TO_CHAR(F_FIN_MC,'DD/MM/YYYY') AS F_FIN_MC,TO_CHAR(F_FIN_CA,'DD/MM/YYYY') AS F_FIN_CA,TO_CHAR(F_INI_VAL_CA,'DD/MM/YYYY') AS F_INI_VAL_CA,TO_CHAR(F_FIN_VAL_CA,'DD/MM/YYYY') AS F_FIN_VAL_CA, D_ANIO_CONVOCATORIA FROM OCAP_CONVOCATORIAS WHERE B_BORRADO = 'N'ORDER BY D_NOMBRE";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPConvocatoriasOT datos = new OCAPConvocatoriasOT();

				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setFResolucion(rs.getString("F_RESOLUCION"));
				datos.setFPublicacion(rs.getString("F_PUBLICACION"));
				datos.setFInicioMC(rs.getString("F_INICIOMC"));
				datos.setFAlegsolicitud(rs.getString("F_ALEGSOLICITUD"));
				datos.setFInicioEstudioCC(rs.getString("F_ESTUDIO_CC"));
				datos.setFFinEstudioCC(rs.getString("F_FIN_ESTUDIO_CC"));
				datos.setFInicioCA(rs.getString("F_INICIOCA"));
				datos.setFFinCp(rs.getString("F_FINCP"));
				datos.setFRecGrado(rs.getString("F_REC_GRADO"));
				datos.setNDiasRegsolicitud(rs.getLong("N_DIAS_REGSOLICITUD"));
				datos.setNDiasRevsolicitud(rs.getLong("N_DIAS_REVSOLICITUD"));
				datos.setNDiasPublistado(rs.getLong("N_DIAS_PUBLISTADO"));
				datos.setNDiasAutoMc(rs.getLong("N_DIAS_AUTO_MC"));
				datos.setNDiasValMc(rs.getLong("N_DIAS_VAL_MC"));
				datos.setNDiasInconfMc(rs.getLong("N_DIAS_INCONF_MC"));
				datos.setNDiasAutoCa(rs.getLong("N_DIAS_AUTO_CA"));
				datos.setNDiasValCa(rs.getLong("N_DIAS_VAL_CA"));
				datos.setNDiasInconfCa(rs.getLong("N_DIAS_INCONF_CA"));
				datos.setNDiasValCc(rs.getLong("N_DIAS_VAL_CC"));
				datos.setAObservaciones(rs.getString("A_OBSERVACIONES"));
				datos.setFModificacion(rs.getString("F_USUMODI"));
				datos.setFCreacion(rs.getString("F_USUALTA"));
				datos.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
				datos.setNDiasRespinconfMc(rs.getLong("N_DIAS_RESPINCONF_MC"));
				datos.setNDiasRespinconfCa(rs.getLong("N_DIAS_RESPINCONF_CA"));
				datos.setNDiasRespinconfCc(rs.getLong("N_DIAS_RESPINCONF_CC"));
				datos.setFInicioSolicitud(rs.getString("F_INI_SOLICITUD"));
				datos.setFFinSolicitud(rs.getString("F_FIN_SOLICITUD"));
				datos.setFInicioValoracionMC(rs.getString("F_INI_VAL_MC"));
				datos.setFFinValoracionMC(rs.getString("F_FIN_VAL_MC"));
				datos.setFInicioValoracionCC(rs.getString("F_INI_VAL_CC"));
				datos.setFFinValoracionCC(rs.getString("F_FIN_VAL_CC"));
				datos.setFFinMC(rs.getString("F_FIN_MC"));
				datos.setFFinCA(rs.getString("F_FIN_CA"));
				datos.setFInicioValCA(rs.getString("F_INI_VAL_CA"));
				datos.setFFinValCA(rs.getString("F_FIN_VAL_CA"));
				datos.setDAnioConvocatoria(rs.getString("D_ANIO_CONVOCATORIA"));

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

	public int listadoOCAPConvocatoriasCuenta(OCAPConvocatoriasOT datos) throws SQLException, Exception {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int total = -1;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);

			String sql = "SELECT COUNT(*) FROM OCAP_CONVOCATORIAS";

			if (datos.getCConvocatoriaId() != 0L) {
				sbWhere.append("C_CONVOCATORIA_ID = ").append(datos.getCConvocatoriaId()).append(" AND ");
			}

			if ((datos.getDNombre() != null) && (!datos.getDNombre().equals(""))) {
				sbWhere.append("(upper(D_NOMBRE) like upper('%").append(datos.getDNombre()).append("%')) AND ");
			}
			if ((datos.getFResolucion() != null) && (!datos.getFResolucion().equals(""))) {
				sbWhere.append("to_char(F_RESOLUCION,'DD/MM/RRRR') = '").append(datos.getFResolucion())
						.append("' AND ");
			}

			if ((datos.getFPublicacion() != null) && (!datos.getFPublicacion().equals(""))) {
				sbWhere.append("to_char(F_PUBLICACION,'DD/MM/RRRR') = '").append(datos.getFPublicacion())
						.append("' AND ");
			}

			if ((datos.getFInicioMC() != null) && (!datos.getFInicioMC().equals(""))) {
				sbWhere.append("to_char(F_INICIOMC,'DD/MM/RRRR') = '").append(datos.getFInicioMC()).append("' AND ");
			}

			if ((datos.getFAlegsolicitud() != null) && (!datos.getFAlegsolicitud().equals(""))) {
				sbWhere.append("to_char(F_ALEGSOLICITUD,'DD/MM/RRRR') = '").append(datos.getFAlegsolicitud())
						.append("' AND ");
			}

			if ((datos.getFInicioEstudioCC() != null) && (!datos.getFInicioEstudioCC().equals(""))) {
				sbWhere.append("to_char(F_ESTUDIO_CC,'DD/MM/RRRR') = '").append(datos.getFInicioEstudioCC())
						.append("' AND ");
			}

			if ((datos.getFFinEstudioCC() != null) && (!datos.getFFinEstudioCC().equals(""))) {
				sbWhere.append("to_char(F_FIN_ESTUDIO_CC,'DD/MM/RRRR') = '").append(datos.getFFinEstudioCC())
						.append("' AND ");
			}

			if ((datos.getFInicioCA() != null) && (!datos.getFInicioCA().equals(""))) {
				sbWhere.append("to_char(F_INICIOCA,'DD/MM/RRRR') = '").append(datos.getFInicioCA()).append("' AND ");
			}

			if ((datos.getFFinCp() != null) && (!datos.getFFinCp().equals(""))) {
				sbWhere.append("to_char(F_FINCP,'DD/MM/RRRR') = '").append(datos.getFFinCp()).append("' AND ");
			}

			if ((datos.getFRecGrado() != null) && (!datos.getFRecGrado().equals(""))) {
				sbWhere.append("to_char(F_REC_GRADO,'DD/MM/RRRR') = '").append(datos.getFRecGrado()).append("' AND ");
			}

			if (datos.getNDiasRegsolicitud() != 0L) {
				sbWhere.append("N_DIAS_REGSOLICITUD = ").append(datos.getNDiasRegsolicitud()).append(" AND ");
			}
			if (datos.getNDiasRevsolicitud() != 0L) {
				sbWhere.append("N_DIAS_REVSOLICITUD = ").append(datos.getNDiasRevsolicitud()).append(" AND ");
			}
			if (datos.getNDiasPublistado() != 0L) {
				sbWhere.append("N_DIAS_PUBLISTADO = ").append(datos.getNDiasPublistado()).append(" AND ");
			}
			if (datos.getNDiasAutoMc() != 0L) {
				sbWhere.append("N_DIAS_AUTO_MC = ").append(datos.getNDiasAutoMc()).append(" AND ");
			}
			if (datos.getNDiasValMc() != 0L) {
				sbWhere.append("N_DIAS_VAL_MC = ").append(datos.getNDiasValMc()).append(" AND ");
			}
			if (datos.getNDiasInconfMc() != 0L) {
				sbWhere.append("N_DIAS_INCONF_MC = ").append(datos.getNDiasInconfMc()).append(" AND ");
			}
			if (datos.getNDiasAutoCa() != 0L) {
				sbWhere.append("N_DIAS_AUTO_CA = ").append(datos.getNDiasAutoCa()).append(" AND ");
			}
			if (datos.getNDiasValCa() != 0L) {
				sbWhere.append("N_DIAS_VAL_CA = ").append(datos.getNDiasValCa()).append(" AND ");
			}
			if (datos.getNDiasInconfCa() != 0L) {
				sbWhere.append("N_DIAS_INCONF_CA = ").append(datos.getNDiasInconfCa()).append(" AND ");
			}
			if (datos.getNDiasValCc() != 0L) {
				sbWhere.append("N_DIAS_VAL_CC = ").append(datos.getNDiasValCc()).append(" AND ");
			}
			if (datos.getNDiasRespinconfMc() != 0L) {
				sbWhere.append("N_DIAS_RESPINCONF_MC = ").append(datos.getNDiasRespinconfMc()).append(" AND ");
			}
			if (datos.getNDiasRespinconfCa() != 0L) {
				sbWhere.append("N_DIAS_RESPINCONF_CA = ").append(datos.getNDiasRespinconfCa()).append(" AND ");
			}
			if (datos.getNDiasRespinconfCc() != 0L) {
				sbWhere.append("N_DIAS_RESPINCONF_CC = ").append(datos.getNDiasRespinconfCa()).append(" AND ");
			}
			if ((datos.getAObservaciones() != null) && (!datos.getAObservaciones().equals(""))) {
				sbWhere.append("(upper(A_OBSERVACIONES) like upper('%").append(datos.getAObservaciones())
						.append("%')) AND ");
			}
			if ((datos.getFCreacion() != null) && (!datos.getFCreacion().equals(""))) {
				sbWhere.append("to_char(F_USUALTA,'DD/MM/RRRR') = '").append(datos.getFCreacion()).append("' AND ");
			}

			if ((datos.getFModificacion() != null) && (!datos.getFModificacion().equals(""))) {
				sbWhere.append("to_char(F_USUMODI,'DD/MM/RRRR') = '").append(datos.getFModificacion()).append("' AND ");
			}

			if ((datos.getBCierreSo() != null) && (!datos.getBCierreSo().equals(""))) {
				sbWhere.append("B_CIERRE_SO = '").append(datos.getBCierreSo()).append("' AND ");
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

	public ArrayList consultaOCAPConvocatorias(OCAPConvocatoriasOT datos, int inicio, int cuantos)
			throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);

			String selectFrom = "SELECT C_CONVOCATORIA_ID, D_NOMBRE, to_char(F_RESOLUCION,'DD/MM/YYYY') as F_RESOLUCION, F_PUBLICACION, to_char(F_INICIOMC,'DD/MM/YYYY') as F_INICIOMC, to_char(F_ALEGSOLICITUD,'DD/MM/YYYY') as F_ALEGSOLICITUD, to_char(F_ESTUDIO_CC,'DD/MM/YYYY') as F_ESTUDIO_CC, to_char(F_FIN_ESTUDIO_CC,'DD/MM/YYYY') as F_FIN_ESTUDIO_CC, to_char(F_INICIOCA,'DD/MM/YYYY') as F_INICIOCA, to_char(F_FINCP,'DD/MM/YYYY') as F_FINCP, to_char(F_REC_GRADO,'DD/MM/YYYY') as F_REC_GRADO, N_DIAS_REGSOLICITUD, N_DIAS_REVSOLICITUD, N_DIAS_PUBLISTADO, N_DIAS_AUTO_MC, N_DIAS_VAL_MC, N_DIAS_INCONF_MC, N_DIAS_AUTO_CA, N_DIAS_VAL_CA, N_DIAS_INCONF_CA, N_DIAS_VAL_CC, N_DIAS_RESPINCONF_MC, N_DIAS_RESPINCONF_CA, N_DIAS_RESPINCONF_CC, A_OBSERVACIONES, to_char(F_USUMODI,'DD/MM/YYYY') as F_USUMODI, to_char(F_USUALTA,'DD/MM/YYYY') as F_USUALTA, B_CIERRE_SO , TO_CHAR(F_INI_SOLICITUD,'DD/MM/YYYY') AS F_INI_SOLICITUD,TO_CHAR(F_FIN_SOLICITUD,'DD/MM/YYYY') AS F_FIN_SOLICITUD,TO_CHAR(F_INI_VAL_MC,'DD/MM/YYYY') AS F_INI_VAL_MC,TO_CHAR(F_FIN_VAL_MC,'DD/MM/YYYY') AS F_FIN_VAL_MC,TO_CHAR(F_INI_VAL_CC,'DD/MM/YYYY') AS F_INI_VAL_CC,TO_CHAR(F_FIN_VAL_CC,'DD/MM/YYYY') AS F_FIN_VAL_CC,TO_CHAR(F_FIN_MC,'DD/MM/YYYY') AS F_FIN_MC,TO_CHAR(F_FIN_CA,'DD/MM/YYYY') AS F_FIN_CA,TO_CHAR(F_INI_VAL_CA,'DD/MM/YYYY') AS F_INI_VAL_CA,TO_CHAR(F_FIN_VAL_CA,'DD/MM/YYYY') AS F_FIN_VAL_CA FROM OCAP_CONVOCATORIAS";

			String orderBy = " order by D_NOMBRE ";
			if (datos.getCConvocatoriaId() != 0L) {
				sbWhere.append("C_CONVOCATORIA_ID = ? AND ");
			}

			if ((datos.getDNombre() != null) && (!datos.getDNombre().equals(""))) {
				sbWhere.append("(upper(D_NOMBRE) like upper('%").append(datos.getDNombre()).append("%')) AND ");
			}
			if ((datos.getFResolucion() != null) && (!datos.getFResolucion().equals(""))) {
				sbWhere.append("to_char(F_RESOLUCION,'DD/MM/RRRR') = '").append(datos.getFResolucion())
						.append("' AND ");
			}

			if ((datos.getFPublicacion() != null) && (!datos.getFPublicacion().equals(""))) {
				sbWhere.append("to_char(F_PUBLICACION,'DD/MM/RRRR') = '").append(datos.getFPublicacion())
						.append("' AND ");
			}

			if ((datos.getFInicioMC() != null) && (!datos.getFInicioMC().equals(""))) {
				sbWhere.append("to_char(F_INICIOMC,'DD/MM/RRRR') = '").append(datos.getFInicioMC()).append("' AND ");
			}

			if ((datos.getFAlegsolicitud() != null) && (!datos.getFAlegsolicitud().equals(""))) {
				sbWhere.append("to_char(F_ALEGSOLICITUD,'DD/MM/RRRR') = '").append(datos.getFAlegsolicitud())
						.append("' AND ");
			}

			if ((datos.getFInicioEstudioCC() != null) && (!datos.getFInicioEstudioCC().equals(""))) {
				sbWhere.append("to_char(F_ESTUDIO_CC,'DD/MM/RRRR') = '").append(datos.getFInicioEstudioCC())
						.append("' AND ");
			}

			if ((datos.getFFinEstudioCC() != null) && (!datos.getFFinEstudioCC().equals(""))) {
				sbWhere.append("to_char(F_FIN_ESTUDIO_CC,'DD/MM/RRRR') = '").append(datos.getFFinEstudioCC())
						.append("' AND ");
			}

			if ((datos.getFInicioCA() != null) && (!datos.getFInicioCA().equals(""))) {
				sbWhere.append("to_char(F_INICIOCA,'DD/MM/RRRR') = '").append(datos.getFInicioCA()).append("' AND ");
			}

			if ((datos.getFFinCp() != null) && (!datos.getFFinCp().equals(""))) {
				sbWhere.append("to_char(F_FINCP,'DD/MM/RRRR') = '").append(datos.getFFinCp()).append("' AND ");
			}

			if ((datos.getFRecGrado() != null) && (!datos.getFRecGrado().equals(""))) {
				sbWhere.append("to_char(F_REC_GRADO,'DD/MM/RRRR') = '").append(datos.getFRecGrado()).append("' AND ");
			}

			if (datos.getNDiasRegsolicitud() != 0L) {
				sbWhere.append("N_DIAS_REGSOLICITUD = ? AND ");
			}
			if (datos.getNDiasRevsolicitud() != 0L) {
				sbWhere.append("N_DIAS_REVSOLICITUD = ? AND ");
			}
			if (datos.getNDiasPublistado() != 0L) {
				sbWhere.append("N_DIAS_PUBLISTADO = ? AND ");
			}
			if (datos.getNDiasAutoMc() != 0L) {
				sbWhere.append("N_DIAS_AUTO_MC = ? AND ");
			}
			if (datos.getNDiasValMc() != 0L) {
				sbWhere.append("N_DIAS_VAL_MC = ? AND ");
			}
			if (datos.getNDiasInconfMc() != 0L) {
				sbWhere.append("N_DIAS_INCONF_MC = ? AND ");
			}
			if (datos.getNDiasAutoCa() != 0L) {
				sbWhere.append("N_DIAS_AUTO_CA = ? AND ");
			}
			if (datos.getNDiasValCa() != 0L) {
				sbWhere.append("N_DIAS_VAL_CA = ? AND ");
			}
			if (datos.getNDiasInconfCa() != 0L) {
				sbWhere.append("N_DIAS_INCONF_CA = ? AND ");
			}
			if (datos.getNDiasValCc() != 0L) {
				sbWhere.append("N_DIAS_VAL_CC = ? AND ");
			}
			if (datos.getNDiasRespinconfMc() != 0L) {
				sbWhere.append("N_DIAS_RESPINCONF_MC = ? AND ");
			}
			if (datos.getNDiasRespinconfCa() != 0L) {
				sbWhere.append("N_DIAS_RESPINCONF_CA = ? AND ");
			}
			if (datos.getNDiasRespinconfCc() != 0L) {
				sbWhere.append("N_DIAS_RESPINCONF_CC = ? AND ");
			}
			if ((datos.getAObservaciones() != null) && (!datos.getAObservaciones().equals(""))) {
				sbWhere.append("(upper(A_OBSERVACIONES) like upper('%").append(datos.getAObservaciones())
						.append("%')) AND ");
			}
			if ((datos.getFCreacion() != null) && (!datos.getFCreacion().equals(""))) {
				sbWhere.append("to_char(F_USUALTA,'DD/MM/RRRR') = '").append(datos.getFCreacion()).append("' AND ");
			}

			if ((datos.getFModificacion() != null) && (!datos.getFModificacion().equals(""))) {
				sbWhere.append("to_char(F_USUMODI,'DD/MM/RRRR') = '").append(datos.getFModificacion()).append("' AND ");
			}

			if ((datos.getBCierreSo() != null) && (!datos.getBCierreSo().equals(""))) {
				sbWhere.append("B_CIERRE_SO = ? AND ");
			}
			
			if ((datos.getFInicioSolicitud() != null) && (!datos.getFInicioSolicitud().equals(""))) {
				sbWhere.append("F_INI_SOLICITUD = ? AND ");
			}
			
			if ((datos.getFFinSolicitud() != null) && (!datos.getFFinSolicitud().equals(""))) {
				sbWhere.append("F_FIN_SOLICITUD = ? AND ");
			}
			
			if ((datos.getFInicioValoracionMC() != null) && (!datos.getFInicioValoracionMC().equals(""))) {
				sbWhere.append("F_INI_VAL_MC = ? AND ");
			}
			
			if ((datos.getFFinValoracionMC() != null) && (!datos.getFFinValoracionMC().equals(""))) {
				sbWhere.append("F_FIN_VAL_MC = ? AND ");
			}
			
			if ((datos.getFInicioValoracionCC() != null) && (!datos.getFInicioValoracionCC().equals(""))) {
				sbWhere.append("F_INI_VAL_CC = ? AND ");
			}
			
			if ((datos.getFFinValoracionCC() != null) && (!datos.getFFinValoracionCC().equals(""))) {
				sbWhere.append("F_FIN_VAL_CC = ? AND ");
			}
			
			if ((datos.getFFinMC() != null) && (!datos.getFFinMC().equals(""))) {
				sbWhere.append("F_FIN_MC = ? AND ");
			}
			
			if ((datos.getFFinCA() != null) && (!datos.getFFinCA().equals(""))) {
				sbWhere.append("F_FIN_CA = ? AND ");
			}
			
			if ((datos.getFInicioValCA() != null) && (!datos.getFInicioValCA().equals(""))) {
				sbWhere.append("F_INI_VAL_CA = ? AND ");
			}
			
			if ((datos.getFFinValCA() != null) && (!datos.getFFinValCA().equals(""))) {
				sbWhere.append("F_FIN_VAL_CA = ? AND ");
			}
			
			sbWhere.append("B_BORRADO = 'N'  AND ");
			int len = sbWhere.length();
			if (len > 0) {
				where = sbWhere.insert(0, " Where ").substring(0, len + 1);
			}

			String sqlStatement = selectFrom + where + orderBy;
			this.loggerBD.info("Sentencia SQL:" + sqlStatement);
			st = con.prepareStatement(sqlStatement, 1004, 1008);

			int index = 1;

			if (datos.getCConvocatoriaId() != 0L) {
				st.setLong(index++, datos.getCConvocatoriaId());
			}

			if (datos.getNDiasRegsolicitud() != 0L) {
				st.setLong(index++, datos.getNDiasRegsolicitud());
			}
			if (datos.getNDiasRevsolicitud() != 0L) {
				st.setLong(index++, datos.getNDiasRevsolicitud());
			}
			if (datos.getNDiasPublistado() != 0L) {
				st.setLong(index++, datos.getNDiasPublistado());
			}
			if (datos.getNDiasAutoMc() != 0L) {
				st.setLong(index++, datos.getNDiasAutoMc());
			}
			if (datos.getNDiasValMc() != 0L) {
				st.setLong(index++, datos.getNDiasValMc());
			}
			if (datos.getNDiasInconfMc() != 0L) {
				st.setLong(index++, datos.getNDiasInconfMc());
			}
			if (datos.getNDiasAutoCa() != 0L) {
				st.setLong(index++, datos.getNDiasAutoCa());
			}
			if (datos.getNDiasValCa() != 0L) {
				st.setLong(index++, datos.getNDiasValCa());
			}
			if (datos.getNDiasInconfCa() != 0L) {
				st.setLong(index++, datos.getNDiasInconfCa());
			}
			if (datos.getNDiasValCc() != 0L) {
				st.setLong(index++, datos.getNDiasValCc());
			}
			if (datos.getNDiasRespinconfMc() != 0L) {
				st.setLong(index++, datos.getNDiasRespinconfMc());
			}
			if (datos.getNDiasRespinconfCa() != 0L) {
				st.setLong(index++, datos.getNDiasRespinconfCa());
			}
			if (datos.getNDiasRespinconfCc() != 0L) {
				st.setLong(index++, datos.getNDiasRespinconfCc());
			}
			if ((datos.getBCierreSo() != null) && (!datos.getBCierreSo().equals(""))) {
				st.setString(index++, datos.getBCierreSo());
			}
			if ((datos.getFInicioSolicitud() != null) && (!datos.getFInicioSolicitud().equals(""))) {
				st.setString(index++, datos.getFInicioSolicitud());
			}
			if ((datos.getFFinSolicitud() != null) && (!datos.getFFinSolicitud().equals(""))) {
				st.setString(index++, datos.getFFinSolicitud());
			}
			if ((datos.getFInicioValoracionMC() != null) && (!datos.getFInicioValoracionMC().equals(""))) {
				st.setString(index++, datos.getFInicioValoracionMC());
			}
			if ((datos.getFFinValoracionMC() != null) && (!datos.getFFinValoracionMC().equals(""))) {
				st.setString(index++, datos.getFFinValoracionMC());
			}
			if ((datos.getFFinValoracionMC() != null) && (!datos.getFFinValoracionMC().equals(""))) {
				st.setString(index++, datos.getFFinValoracionMC());
			}
			if ((datos.getFInicioValoracionCC() != null) && (!datos.getFInicioValoracionCC().equals(""))) {
				st.setString(index++, datos.getFInicioValoracionCC());
			}
			if ((datos.getFFinValoracionCC() != null) && (!datos.getFFinValoracionCC().equals(""))) {
				st.setString(index++, datos.getFFinValoracionCC());
			}
			if ((datos.getFFinMC() != null) && (!datos.getFFinMC().equals(""))) {
				st.setString(index++, datos.getFFinMC());
			}
			if ((datos.getFFinCA() != null) && (!datos.getFFinCA().equals(""))) {
				st.setString(index++, datos.getFFinCA());
			}
			if ((datos.getFInicioValCA() != null) && (!datos.getFInicioValCA().equals(""))) {
				st.setString(index++, datos.getFInicioValCA());
			}
			if ((datos.getFFinValCA() != null) && (!datos.getFFinValCA().equals(""))) {
				st.setString(index++, datos.getFFinValCA());
			}
			rs = st.executeQuery();

			if (inicio > 1)
				rs.absolute(inicio - 1);
			listado = new ArrayList();

			int i = 0;
			while (rs.next()) {
				OCAPConvocatoriasOT dato = new OCAPConvocatoriasOT();

				dato.setDNombre(rs.getString("D_NOMBRE"));
				dato.setFResolucion(rs.getString("F_RESOLUCION"));
				dato.setFPublicacion(DateUtil.convertDateToString(rs.getTimestamp("F_PUBLICACION")));
				dato.setFInicioMC(rs.getString("F_INICIOMC"));
				dato.setFAlegsolicitud(rs.getString("F_ALEGSOLICITUD"));
				dato.setFInicioEstudioCC(rs.getString("F_ESTUDIO_CC"));
				dato.setFFinEstudioCC(rs.getString("F_FIN_ESTUDIO_CC"));
				dato.setFInicioCA(rs.getString("F_INICIOCA"));
				dato.setFFinCp(rs.getString("F_FINCP"));
				dato.setFRecGrado(rs.getString("F_REC_GRADO"));
				dato.setNDiasRegsolicitud(rs.getLong("N_DIAS_REGSOLICITUD"));
				dato.setNDiasRevsolicitud(rs.getLong("N_DIAS_REVSOLICITUD"));
				dato.setNDiasPublistado(rs.getLong("N_DIAS_PUBLISTADO"));
				dato.setNDiasAutoMc(rs.getLong("N_DIAS_AUTO_MC"));
				dato.setNDiasValMc(rs.getLong("N_DIAS_VAL_MC"));
				dato.setNDiasInconfMc(rs.getLong("N_DIAS_INCONF_MC"));
				dato.setNDiasAutoCa(rs.getLong("N_DIAS_AUTO_CA"));
				dato.setNDiasValCa(rs.getLong("N_DIAS_VAL_CA"));
				dato.setNDiasInconfCa(rs.getLong("N_DIAS_INCONF_CA"));
				dato.setNDiasValCc(rs.getLong("N_DIAS_VAL_CC"));
				dato.setAObservaciones(rs.getString("A_OBSERVACIONES"));
				dato.setFModificacion(rs.getString("F_USUMODI"));
				dato.setFCreacion(rs.getString("F_USUALTA"));
				dato.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
				dato.setNDiasRespinconfMc(rs.getLong("N_DIAS_RESPINCONF_MC"));
				dato.setNDiasRespinconfCa(rs.getLong("N_DIAS_RESPINCONF_CA"));
				dato.setNDiasRespinconfCc(rs.getLong("N_DIAS_RESPINCONF_CC"));
				dato.setBCierreSo(rs.getString("B_CIERRE_SO"));
				datos.setFInicioSolicitud(rs.getString("F_INI_SOLICITUD"));
				datos.setFFinSolicitud(rs.getString("F_FIN_SOLICITUD"));
				datos.setFInicioValoracionMC(rs.getString("F_INI_VAL_MC"));
				datos.setFFinValoracionMC(rs.getString("F_FIN_VAL_MC"));
				datos.setFInicioValoracionCC(rs.getString("F_INI_VAL_CC"));
				datos.setFFinValoracionCC(rs.getString("F_FIN_VAL_CC"));
				datos.setFFinMC(rs.getString("F_FIN_MC"));
				datos.setFFinCA(rs.getString("F_FIN_CA"));
				datos.setFInicioValCA(rs.getString("F_INI_VAL_CA"));
				datos.setFFinValCA(rs.getString("F_FIN_VAL_CA"));

				listado.add(dato);
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

	public ArrayList consultaOCAPConvocatoriasPorGradoId(long cGradoId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String sqlStatement = "SELECT C_CONVOCATORIA_ID, C_GRADO_ID, D_NOMBRE, to_char(F_RESOLUCION,'DD/MM/YYYY') F_RESOLUCION, to_char(F_PUBLICACION,'DD/MM/YYYY') F_PUBLICACION, A_OBSERVACIONES, to_char(F_USUMODI,'DD/MM/YYYY') F_USUMODI, to_char(F_USUALTA,'DD/MM/YYYY') F_USUALTA FROM OCAP_CONVOCATORIAS WHERE B_BORRADO = 'N' AND C_GRADO_ID = ?  order by D_NOMBRE";

			this.loggerBD.info("Sentencia SQL:" + sqlStatement);
			st = con.prepareStatement(sqlStatement, 1004, 1008);
			st.setLong(1, cGradoId);
			rs = st.executeQuery();
			listado = new ArrayList();
			while (rs.next()) {
				OCAPConvocatoriasOT datos = new OCAPConvocatoriasOT();
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setFResolucion(rs.getString("F_RESOLUCION"));
				datos.setFPublicacion(rs.getString("F_PUBLICACION"));
				datos.setAObservaciones(rs.getString("A_OBSERVACIONES"));
				datos.setFModificacion(rs.getString("F_USUMODI"));
				datos.setFCreacion(rs.getString("F_USUALTA"));
				datos.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
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

	public ArrayList listarConvocatoriasActivas() throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String sqlStatement = "SELECT C_CONVOCATORIA_ID, D_NOMBRE FROM OCAP_CONVOCATORIAS WHERE B_BORRADO = 'N' AND SYSDATE >= F_PUBLICACION AND SYSDATE <= F_FINCP  ORDER BY D_NOMBRE ";

			this.loggerBD.info("Sentencia SQL:" + sqlStatement);
			st = con.prepareStatement(sqlStatement, 1004, 1008);
			rs = st.executeQuery();
			listado = new ArrayList();
			while (rs.next()) {
				OCAPConvocatoriasOT datos = new OCAPConvocatoriasOT();
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
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

	public ArrayList listarConvocatoriasActivasAlta() throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			//String sqlStatement = "SELECT C_CONVOCATORIA_ID, D_NOMBRE, B_CIERRE_SO FROM OCAP_CONVOCATORIAS WHERE B_BORRADO = 'N' AND SYSDATE >= F_PUBLICACION AND (SYSDATE <= F_ALEGSOLICITUD OR F_ALEGSOLICITUD IS NULL)  ORDER BY D_NOMBRE ";
			String sqlStatement = "SELECT conv.C_CONVOCATORIA_ID as C_CONVOCATORIA_ID, conv.D_NOMBRE as D_NOMBRE, conv.B_CIERRE_SO as B_CIERRE_SO, conv.D_ANIO_CONVOCATORIA as D_ANIO_CONVOCATORIA FROM OCAP_CONVOCATORIAS conv WHERE B_BORRADO = 'N' AND ( (conv.F_INI_SOLICITUD IS NOT NULL AND   conv.F_FIN_SOLICITUD IS NOT NULL AND   SYSDATE >= conv.F_INI_SOLICITUD AND   SYSDATE < (conv.F_FIN_SOLICITUD+1))   OR   (conv.F_INI_SOLICITUD IS NULL AND   conv.F_FIN_SOLICITUD IS NULL AND   SYSDATE >= (conv.F_PUBLICACION+1) AND   SYSDATE < (conv.F_PUBLICACION+1+conv.N_DIAS_REGSOLICITUD+1)) ) ";

			OCAPConfigApp.logger.info("Sentencia SQL:" + sqlStatement);
			st = con.prepareStatement(sqlStatement, 1004, 1008);
			rs = st.executeQuery();
			listado = new ArrayList();
			while (rs.next()) {
				OCAPConvocatoriasOT datos = new OCAPConvocatoriasOT();
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
				datos.setBCierreSo(rs.getString("B_CIERRE_SO"));
				datos.setDAnioConvocatoria(rs.getString("D_ANIO_CONVOCATORIA"));
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

	public ArrayList obtenerConvocatorias(boolean orderName) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String sqlStatement = "SELECT C_CONVOCATORIA_ID, D_NOMBRE FROM OCAP_CONVOCATORIAS WHERE B_BORRADO = 'N' ";

			if (orderName)
				sqlStatement = sqlStatement + "ORDER BY D_NOMBRE ASC";
			else {
				sqlStatement = sqlStatement + "ORDER BY C_CONVOCATORIA_ID DESC";
			}
			st = con.prepareStatement(sqlStatement, 1004, 1008);
			rs = st.executeQuery();
			listado = new ArrayList();
			while (rs.next()) {
				OCAPConvocatoriasOT datos = new OCAPConvocatoriasOT();
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
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

	public ArrayList listarConvocatoriasActivasEvaluador(long cCompfqsId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String sqlStatement = "SELECT  DISTINCT(c.C_CONVOCATORIA_ID), c.D_NOMBRE FROM OCAP_CONVOCATORIAS c, OCAP_COMPFQS_CONVOCATORIAS f WHERE c.B_BORRADO = 'N'  AND c.B_BORRADO = 'N' AND SYSDATE >= F_PUBLICACION AND SYSDATE <= F_FINCP AND C_COMPFQS_ID =  "
					+ cCompfqsId + " AND c.C_CONVOCATORIA_ID = f.C_CONVOCATORIA_ID " + " ORDER BY c.D_NOMBRE ";

			this.loggerBD.info("Sentencia SQL:" + sqlStatement);
			st = con.prepareStatement(sqlStatement, 1004, 1008);
			rs = st.executeQuery();
			listado = new ArrayList();
			while (rs.next()) {
				OCAPConvocatoriasOT datos = new OCAPConvocatoriasOT();
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
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
	
	
	public OCAPConvocatoriasOT buscarOCAPConvocatoriasPorExpediente(long cExpId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		OCAPConvocatoriasOT datos = null;
		StringBuilder sql = new StringBuilder("");
		
		
		try {
			this.loggerBD.info(getClass().getName() + " buscarOCAPConvocatorias: " + cExpId);

			//String sql = "SELECT C_CONVOCATORIA_ID, D_NOMBRE, D_NOMBRE_CORTO, to_char(F_RESOLUCION,'DD/MM/YYYY') as F_RESOLUCION, to_char(F_PUBLICACION,'DD/MM/YYYY') as F_PUBLICACION, to_char(F_INICIOMC,'DD/MM/YYYY') as F_INICIOMC, to_char(F_ALEGSOLICITUD,'DD/MM/YYYY') as F_ALEGSOLICITUD, to_char(F_ESTUDIO_CC,'DD/MM/YYYY') as F_ESTUDIO_CC, to_char(F_FIN_ESTUDIO_CC,'DD/MM/YYYY') as F_FIN_ESTUDIO_CC, to_char(F_INICIOCA,'DD/MM/YYYY') as F_INICIOCA, to_char(F_FINCP,'DD/MM/YYYY') as F_FINCP, to_char(F_REC_GRADO,'DD/MM/YYYY') as F_REC_GRADO, N_DIAS_REGSOLICITUD, N_DIAS_REVSOLICITUD, N_DIAS_PUBLISTADO, N_DIAS_AUTO_MC, N_DIAS_VAL_MC, N_DIAS_INCONF_MC, N_DIAS_AUTO_CA, N_DIAS_VAL_CA, N_DIAS_INCONF_CA, N_DIAS_VAL_CC, N_DIAS_RESPINCONF_MC, N_DIAS_RESPINCONF_CA, N_DIAS_RESPINCONF_CC, A_OBSERVACIONES, to_char(F_USUMODI,'DD/MM/YYYY') as F_USUMODI, to_char(F_USUALTA,'DD/MM/YYYY') as F_USUALTA, B_CIERRE_SO, TO_CHAR(F_INI_SOLICITUD,'DD/MM/YYYY') AS F_INI_SOLICITUD,TO_CHAR(F_FIN_SOLICITUD,'DD/MM/YYYY') AS F_FIN_SOLICITUD,TO_CHAR(F_INI_VAL_MC,'DD/MM/YYYY') AS F_INI_VAL_MC,TO_CHAR(F_FIN_VAL_MC,'DD/MM/YYYY') AS F_FIN_VAL_MC,TO_CHAR(F_INI_VAL_CC,'DD/MM/YYYY') AS F_INI_VAL_CC,TO_CHAR(F_FIN_VAL_CC,'DD/MM/YYYY') AS F_FIN_VAL_CC,TO_CHAR(F_FIN_MC,'DD/MM/YYYY') AS F_FIN_MC,TO_CHAR(F_FIN_CA,'DD/MM/YYYY') AS F_FIN_CA,TO_CHAR(F_INI_VAL_CA,'DD/MM/YYYY') AS F_INI_VAL_CA,TO_CHAR(F_FIN_VAL_CA,'DD/MM/YYYY') AS F_FIN_VAL_CA, D_ANIO_CONVOCATORIA FROM OCAP_CONVOCATORIAS WHERE C_CONVOCATORIA_ID = ? AND B_BORRADO = 'N' ORDER BY D_NOMBRE ";
			
			
			sql.append(" SELECT ")
		    .append(" c.c_convocatoria_id, ")
		    .append(" c.d_nombre, ")
		    .append(" c.d_nombre_corto, ")
		    .append(" to_char(c.f_resolucion, 'DD/MM/YYYY') AS f_resolucion, ")
		    .append(" to_char(c.f_publicacion, 'DD/MM/YYYY') AS f_publicacion, ")
		    .append(" to_char(c.f_iniciomc, 'DD/MM/YYYY') AS f_iniciomc, ")
		    .append(" to_char(c.f_alegsolicitud, 'DD/MM/YYYY') AS f_alegsolicitud, ")
		    .append(" to_char(c.f_estudio_cc, 'DD/MM/YYYY') AS f_estudio_cc, ")
		    .append(" to_char(c.f_fin_estudio_cc, 'DD/MM/YYYY') AS f_fin_estudio_cc, ")
		    .append(" to_char(c.f_inicioca, 'DD/MM/YYYY') AS f_inicioca, ")
		    .append(" to_char(c.f_fincp, 'DD/MM/YYYY') AS f_fincp, ")
		    .append(" to_char(c.f_rec_grado, 'DD/MM/YYYY') AS f_rec_grado, ")
		    .append(" c.n_dias_regsolicitud, ")
		    .append(" c.n_dias_revsolicitud, ")
		    .append(" c.n_dias_publistado, ")
		    .append(" c.n_dias_auto_mc, ")
		    .append(" c.n_dias_val_mc, ")
		    .append(" c.n_dias_inconf_mc, ")
		    .append(" c.n_dias_auto_ca, ")
		    .append(" c.n_dias_val_ca, ")
		    .append(" c.n_dias_inconf_ca, ")
		    .append(" c.n_dias_val_cc, ")
		    .append(" c.n_dias_respinconf_mc, ")
		    .append(" c.n_dias_respinconf_ca, ")
		    .append(" c.n_dias_respinconf_cc, ")
		    .append(" c.a_observaciones, ")
		    .append(" to_char(c.f_usumodi, 'DD/MM/YYYY') AS f_usumodi, ")
		    .append(" to_char(c.f_usualta, 'DD/MM/YYYY') AS f_usualta, ")
		    .append(" c.b_cierre_so, ")
		    .append(" to_char(c.f_ini_solicitud, 'DD/MM/YYYY') AS f_ini_solicitud, ")
		    .append(" to_char(c.f_fin_solicitud, 'DD/MM/YYYY') AS f_fin_solicitud, ")
		    .append(" to_char(c.f_ini_val_mc, 'DD/MM/YYYY') AS f_ini_val_mc, ")
		    .append(" to_char(c.f_fin_val_mc, 'DD/MM/YYYY') AS f_fin_val_mc, ")
		    .append(" to_char(c.f_ini_val_cc, 'DD/MM/YYYY') AS f_ini_val_cc, ")
		    .append(" to_char(c.f_fin_val_cc, 'DD/MM/YYYY') AS f_fin_val_cc, ")
		    .append(" to_char(c.f_fin_mc, 'DD/MM/YYYY') AS f_fin_mc, ")
		    .append(" to_char(c.f_fin_ca, 'DD/MM/YYYY') AS f_fin_ca, ")
		    .append(" to_char(c.f_ini_val_ca, 'DD/MM/YYYY') AS f_ini_val_ca, ")
		    .append(" to_char(c.f_fin_val_ca, 'DD/MM/YYYY') AS f_fin_val_ca, ")
		    .append(" c.d_anio_convocatoria ")
		    .append(" FROM ")
		    .append(" ocap_convocatorias c , ")
		    .append(" ocap_expedientescarrera exp ")
		    .append(" WHERE ")
		  .append(" exp.c_convocatoria_id= c.c_convocatoria_id ")
		  .append(" and exp.c_exp_id=? ")
		  .append(" AND c.b_borrado = 'N' ");
			
			
			st = con.prepareStatement(sql.toString());
			st.setLong(1, cExpId);
			rs = st.executeQuery();
			datos = new OCAPConvocatoriasOT();
			if (rs.next()) {
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setDNombreCorto(rs.getString("D_NOMBRE_CORTO"));
				datos.setFResolucion(rs.getString("F_RESOLUCION"));
				datos.setFPublicacion(rs.getString("F_PUBLICACION"));
				datos.setFInicioMC(rs.getString("F_INICIOMC"));
				datos.setFAlegsolicitud(rs.getString("F_ALEGSOLICITUD"));
				datos.setFInicioEstudioCC(rs.getString("F_ESTUDIO_CC"));
				datos.setFFinEstudioCC(rs.getString("F_FIN_ESTUDIO_CC"));
				datos.setFInicioCA(rs.getString("F_INICIOCA"));
				datos.setFFinCp(rs.getString("F_FINCP"));
				datos.setFRecGrado(rs.getString("F_REC_GRADO"));
				datos.setNDiasRegsolicitud(rs.getLong("N_DIAS_REGSOLICITUD"));
				datos.setNDiasRevsolicitud(rs.getLong("N_DIAS_REVSOLICITUD"));
				datos.setNDiasPublistado(rs.getLong("N_DIAS_PUBLISTADO"));
				datos.setNDiasAutoMc(rs.getLong("N_DIAS_AUTO_MC"));
				datos.setNDiasValMc(rs.getLong("N_DIAS_VAL_MC"));
				datos.setNDiasInconfMc(rs.getLong("N_DIAS_INCONF_MC"));
				datos.setNDiasAutoCa(rs.getLong("N_DIAS_AUTO_CA"));
				datos.setNDiasValCa(rs.getLong("N_DIAS_VAL_CA"));
				datos.setNDiasInconfCa(rs.getLong("N_DIAS_INCONF_CA"));
				datos.setNDiasValCc(rs.getLong("N_DIAS_VAL_CC"));
				datos.setNDiasRespinconfMc(rs.getLong("N_DIAS_RESPINCONF_MC"));
				datos.setNDiasRespinconfCa(rs.getLong("N_DIAS_RESPINCONF_CA"));
				datos.setNDiasRespinconfCc(rs.getLong("N_DIAS_RESPINCONF_CC"));
				datos.setAObservaciones(rs.getString("A_OBSERVACIONES"));
				datos.setFModificacion(rs.getString("F_USUMODI"));
				datos.setFCreacion(rs.getString("F_USUALTA"));
				datos.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
				datos.setBCierreSo(rs.getString("B_CIERRE_SO"));
				datos.setFInicioSolicitud(rs.getString("F_INI_SOLICITUD"));
				datos.setFFinSolicitud(rs.getString("F_FIN_SOLICITUD"));
				datos.setFInicioValoracionMC(rs.getString("F_INI_VAL_MC"));
				datos.setFFinValoracionMC(rs.getString("F_FIN_VAL_MC"));
				datos.setFInicioValoracionCC(rs.getString("F_INI_VAL_CC"));
				datos.setFFinValoracionCC(rs.getString("F_FIN_VAL_CC"));
				datos.setFFinMC(rs.getString("F_FIN_MC"));
				datos.setFFinCA(rs.getString("F_FIN_CA"));
				datos.setFInicioValCA(rs.getString("F_INI_VAL_CA"));
				datos.setFFinValCA(rs.getString("F_FIN_VAL_CA"));
				datos.setDAnioConvocatoria(rs.getString("D_ANIO_CONVOCATORIA"));				
				
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
	
	public ArrayList listarConvocatoriasFinMc() throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String sqlStatement = "SELECT C_CONVOCATORIA_ID, D_NOMBRE FROM OCAP_CONVOCATORIAS WHERE B_BORRADO = 'N' AND TRUNC(SYSDATE) > TRUNC(F_FIN_MC)  AND SYSDATE >= F_PUBLICACION AND SYSDATE <= F_FINCP   ORDER BY D_NOMBRE ";

			this.loggerBD.info("Sentencia SQL:" + sqlStatement);
			st = con.prepareStatement(sqlStatement, 1004, 1008);
			rs = st.executeQuery();
			listado = new ArrayList();
			while (rs.next()) {
				OCAPConvocatoriasOT datos = new OCAPConvocatoriasOT();
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
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
	
	public ArrayList listarConvocatoriasFinMa() throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String sqlStatement = "SELECT C_CONVOCATORIA_ID, D_NOMBRE FROM OCAP_CONVOCATORIAS WHERE B_BORRADO = 'N' AND TRUNC(SYSDATE) > TRUNC(F_FIN_CA) AND SYSDATE >= F_PUBLICACION AND SYSDATE <= F_FINCP  ORDER BY D_NOMBRE ";

			this.loggerBD.info("Sentencia SQL:" + sqlStatement);
			st = con.prepareStatement(sqlStatement, 1004, 1008);
			rs = st.executeQuery();
			listado = new ArrayList();
			while (rs.next()) {
				OCAPConvocatoriasOT datos = new OCAPConvocatoriasOT();
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
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

	public ArrayList consultaGradoDeConvocatoria(int cConvocatoriaId) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		
		try {
			String sqlStatement = "SELECT gra.C_GRADO_ID, gra.D_DESCRIPCION FROM OCAP_CONVOCATORIAS conv , OCAP_GRADOS gra WHERE conv.C_CONVOCATORIA_ID = ? AND conv.C_GRADO_ID = gra.C_GRADO_ID ";
			
			st = con.prepareStatement(sqlStatement);
			st.setInt(1, cConvocatoriaId);
			rs = st.executeQuery();
			listado = new ArrayList();
			while (rs.next()) {
				OCAPGradoOT gradoOt = new OCAPGradoOT();
				gradoOt.setCGradoId(rs.getLong("C_GRADO_ID"));
				gradoOt.setDDescripcion(rs.getString("D_DESCRIPCION"));
				listado.add(gradoOt);				
			}
		} catch (Exception ex) {
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
	
	public String buscarOCAPConvocatoriasPorCA(long cConvoId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String fecha = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarOCAPConvocatoriasPorCA Inicio");
			con = JCYLGestionTransacciones.getConnection();

			String sql = "SELECT TO_CHAR( f_fin_ca,'DD/MM/YYYY') AS f_fin_ca " + 
					"FROM ocap_convocatorias " + 
					"WHERE c_convocatoria_id =? " + 
					"AND b_borrado = 'N' " + 
					"AND TO_DATE(SYSDATE,'DD/MM/YY') >=F_INICIOCA " + 
					"AND TO_DATE(SYSDATE,'DD/MM/YY')<F_FIN_CA";

			st = con.prepareStatement(sql);
			st.setLong(1, cConvoId);
			rs = st.executeQuery();
			if (rs.next()) {
				fecha = rs.getString("f_fin_ca");
			}

			OCAPConfigApp.logger.info(getClass().getName() + " buscarOCAPConvocatoriasPorCA Fin");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return fecha;
	}

	public ArrayList listarConvocatoriasEdicionPgp() throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String sqlStatement = "SELECT conv.C_CONVOCATORIA_ID as C_CONVOCATORIA_ID, conv.D_NOMBRE as D_NOMBRE, conv.B_CIERRE_SO as B_CIERRE_SO, conv.D_ANIO_CONVOCATORIA as D_ANIO_CONVOCATORIA FROM OCAP_CONVOCATORIAS conv WHERE B_BORRADO = 'N' AND (F_FIN_PGP IS NULL OR (F_FIN_PGP IS NOT NULL AND F_FIN_PGP >= TRUNC(SYSDATE) )) ORDER BY D_NOMBRE ";

			OCAPConfigApp.logger.info("Sentencia SQL:" + sqlStatement);
			st = con.prepareStatement(sqlStatement, 1004, 1008);
			rs = st.executeQuery();
			listado = new ArrayList();
			while (rs.next()) {
				OCAPConvocatoriasOT datos = new OCAPConvocatoriasOT();
				datos.setDNombre(rs.getString("D_NOMBRE"));
				datos.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
				datos.setBCierreSo(rs.getString("B_CIERRE_SO"));
				datos.setDAnioConvocatoria(rs.getString("D_ANIO_CONVOCATORIA"));
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
