package es.jcyl.fqs.ocap.oad.expedienteCarrera;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT;
import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;

public class OCAPExpedientecarreraOAD {
	public static Logger logger = OCAPConfigApp.logger;
	public Logger loggerBD;
	private static OCAPExpedientecarreraOAD instance;

	private void $init$() {
		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public static OCAPExpedientecarreraOAD getInstance() {
		if (instance == null) {
			instance = new OCAPExpedientecarreraOAD();
		}
		return instance;
	}

	private OCAPExpedientecarreraOAD() {
		$init$();
	}

	public int altaOCAPExpedientecarrera(OCAPExpedientecarreraOT datos) throws SQLException, Exception {
		CallableStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		int idExpediente = 0;
		try {
			String sql = "BEGIN INSERT INTO OCAP_EXPEDIENTESCARRERA (C_EXP_ID, C_USR_ID, C_USUALTA, B_LOPD_SOLICITUD";
			if (datos.getCGradoId().intValue() != 0) {
				sql = sql + ", C_GRADO_ID ";
			}
			sql = sql + ", B_BORRADO, F_REGISTRO_SOLIC, F_REGISTRO_OFICIAL, F_USUALTA ";

			if (datos.getNAniosAntiguedad() != 0L)
				sql = sql + ", N_ANIOSANTIGUEDAD ";
			if (datos.getNMesesAntiguedad() != 0L)
				sql = sql + ", N_MESES_ANTIGUEDAD ";
			if (datos.getNDiasAntiguedad() != 0L) {
				sql = sql + ", N_DIAS_ANTIGUEDAD ";
			}
			if (datos.getCConvocatoriaId() != 0L) {
				sql = sql + ", C_CONVOCATORIA_ID ";
			}
			if ((datos.getCProcedimientoId() != null) && (!"".equals(datos.getCProcedimientoId()))) {
				sql = sql + ", C_PROC_EVALUACION_ID ";
			}
			if (datos.getCEstatutId() != 0L) {
				sql = sql + ", C_PERSONAL_ID ";
			}
			if ((datos.getBOtroServicio() != null) && (!"".equals(datos.getBOtroServicio()))) {
				sql = sql + ", B_OTROSERVICIO ";
			}
			if ((datos.getCSitAdministrativaAuxId() != null) && (!"".equals(datos.getCSitAdministrativaAuxId()))) {
				sql = sql + ", C_SIT_ADMINISTRATIVA_ID ";
			}
			if ((datos.getDSitAdministrativaAuxOtros() != null)
					&& (!"".equals(datos.getDSitAdministrativaAuxOtros()))) {
				sql = sql + ", A_OTRASITADMINISTRATIVA ";
			}
			if ((datos.getCJuridico() != null) && (!"".equals(datos.getCJuridico()))) {
				sql = sql + ", C_JURIDICO_ID ";
			}
			if (datos.getCEstatutActualId() != 0L) {
				sql = sql + ", C_PERSONALACTUAL_ID ";
			}
			if (datos.getCProfesionalActual_id() != 0L) {
				sql = sql + ", C_CATEGACTUAL_ID ";
			}
			if (datos.getCEspecActual_id() != 0L) {
				sql = sql + ", C_ESPECACTUAL_ID ";
			}
			if ((datos.getAServicio() != null) && (!"".equals(datos.getAServicio()))) {
				sql = sql + ", A_SERVICIO ";
			}
			if ((datos.getAPuesto() != null) && (!"".equals(datos.getAPuesto()))) {
				sql = sql + ", A_PUESTO ";
			}
			if (datos.getCSolicitudId() != 0L) {
				sql = sql + ", C_SOLICITUD_ID ";
			}
			if (datos.getCProfesionalId() != 0L) {
				sql = sql + ", C_PROFESIONAL_ID ";
			}
			if (datos.getCEspecId() != 0L) {
				sql = sql + ", C_ESPEC_ID ";
			}
			if ((datos.getDRegimenJuridicoOtros() != null) && (!"".equals(datos.getDRegimenJuridicoOtros()))) {
				sql = sql + ", A_OTROJURIDICO ";
			}
			if (datos.getCModAnteriorId() != 0L) {
				sql = sql + ", C_MOD_ANTERIOR_ID ";
			}
			if (datos.getCEstadoId() != 0L) {
				sql = sql + ", C_ESTADO_ID ";
			}
			sql = sql + ") VALUES (OCAP_EXP_ID_SEQ.NEXTVAL, ?, ?, 'Y' ";

			if (datos.getCGradoId().intValue() != 0) {
				sql = sql + ", ? ";
			}
			sql = sql + " , ?, TO_DATE(?," + Constantes.FECHA_COMPLETA + "), TO_DATE(?," + Constantes.FECHA_COMPLETA
					+ "), SYSDATE ";

			if (datos.getNAniosAntiguedad() != 0L)
				sql = sql + ", ? ";
			if (datos.getNMesesAntiguedad() != 0L)
				sql = sql + ", ? ";
			if (datos.getNDiasAntiguedad() != 0L) {
				sql = sql + ", ? ";
			}
			if (datos.getCConvocatoriaId() != 0L) {
				sql = sql + ", ? ";
			}
			if ((datos.getCProcedimientoId() != null) && (!"".equals(datos.getCProcedimientoId()))) {
				sql = sql + ", ? ";
			}
			if (datos.getCEstatutId() != 0L) {
				sql = sql + ", ? ";
			}
			if ((datos.getBOtroServicio() != null) && (!"".equals(datos.getBOtroServicio()))) {
				sql = sql + ", ? ";
			}

			if ((datos.getCSitAdministrativaAuxId() != null) && (!"".equals(datos.getCSitAdministrativaAuxId()))) {
				sql = sql + ", ? ";
			}
			if ((datos.getDSitAdministrativaAuxOtros() != null)
					&& (!"".equals(datos.getDSitAdministrativaAuxOtros()))) {
				sql = sql + ", ? ";
			}

			if ((datos.getCJuridico() != null) && (!"".equals(datos.getCJuridico()))) {
				sql = sql + ", ? ";
			}
			if (datos.getCEstatutActualId() != 0L) {
				sql = sql + ", ? ";
			}
			if (datos.getCProfesionalActual_id() != 0L) {
				sql = sql + ", ? ";
			}
			if (datos.getCEspecActual_id() != 0L) {
				sql = sql + ", ? ";
			}
			if ((datos.getAServicio() != null) && (!"".equals(datos.getAServicio()))) {
				sql = sql + ", ? ";
			}
			if ((datos.getAPuesto() != null) && (!"".equals(datos.getAPuesto()))) {
				sql = sql + ", ? ";
			}
			if (datos.getCSolicitudId() != 0L) {
				sql = sql + ", ? ";
			}
			if (datos.getCProfesionalId() != 0L) {
				sql = sql + ", ? ";
			}
			if (datos.getCEspecId() != 0L) {
				sql = sql + ", ? ";
			}
			if ((datos.getDRegimenJuridicoOtros() != null) && (!"".equals(datos.getDRegimenJuridicoOtros()))) {
				sql = sql + ", ? ";
			}
			if (datos.getCModAnteriorId() != 0L) {
				sql = sql + ", ? ";
			}
			if (datos.getCEstadoId() != 0L) {
				sql = sql + ", ? ";
			}
			sql = sql + ") RETURNING C_EXP_ID INTO ?; END;";

			st = con.prepareCall(sql);

			int cont = 1;
			st.setLong(cont++, datos.getCUsrId().longValue());
			st.setString(cont++, datos.getACreadoPor());
			if (datos.getCGradoId().intValue() != 0)
				st.setInt(cont++, datos.getCGradoId().intValue());
			st.setString(cont++, datos.getBBorrado());
			st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFRegistroSolic()));
			st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFRegistroOficial()));
			if (datos.getNAniosAntiguedad() != 0L) {
				if (datos.getNAniosAntiguedad() == -1)
					st.setLong(cont++, 0L);
				else
					st.setLong(cont++, datos.getNAniosAntiguedad());
			}
			if (datos.getNMesesAntiguedad() != 0L)
				st.setLong(cont++, datos.getNMesesAntiguedad());
			if (datos.getNDiasAntiguedad() != 0L)
				st.setLong(cont++, datos.getNDiasAntiguedad());
			if (datos.getCConvocatoriaId() != 0L)
				st.setLong(cont++, datos.getCConvocatoriaId());
			if ((datos.getCProcedimientoId() != null) && (!"".equals(datos.getCProcedimientoId())))
				st.setString(cont++, datos.getCProcedimientoId());
			if (datos.getCEstatutId() != 0L)
				st.setLong(cont++, datos.getCEstatutId());
			if ((datos.getBOtroServicio() != null) && (!"".equals(datos.getBOtroServicio())))
				st.setString(cont++, datos.getBOtroServicio());
			if ((datos.getCSitAdministrativaAuxId() != null) && (!"".equals(datos.getCSitAdministrativaAuxId())))
				st.setString(cont++, datos.getCSitAdministrativaAuxId());
			if ((datos.getDSitAdministrativaAuxOtros() != null) && (!"".equals(datos.getDSitAdministrativaAuxOtros())))
				st.setString(cont++, datos.getDSitAdministrativaAuxOtros());
			if ((datos.getCJuridico() != null) && (!"".equals(datos.getCJuridico())))
				st.setString(cont++, datos.getCJuridico());
			if (datos.getCEstatutActualId() != 0L)
				st.setLong(cont++, datos.getCEstatutActualId());
			if (datos.getCProfesionalActual_id() != 0L)
				st.setLong(cont++, datos.getCProfesionalActual_id());
			if (datos.getCEspecActual_id() != 0L)
				st.setLong(cont++, datos.getCEspecActual_id());
			if ((datos.getAServicio() != null) && (!"".equals(datos.getAServicio())))
				st.setString(cont++, datos.getAServicio());
			if ((datos.getAPuesto() != null) && (!"".equals(datos.getAPuesto())))
				st.setString(cont++, datos.getAPuesto());
			if (datos.getCSolicitudId() != 0L)
				st.setLong(cont++, datos.getCSolicitudId());
			if (datos.getCProfesionalId() != 0L)
				st.setLong(cont++, datos.getCProfesionalId());
			if (datos.getCEspecId() != 0L)
				st.setLong(cont++, datos.getCEspecId());
			if ((datos.getDRegimenJuridicoOtros() != null) && (!"".equals(datos.getDRegimenJuridicoOtros())))
				st.setString(cont++, datos.getDRegimenJuridicoOtros());
			if (datos.getCModAnteriorId() != 0L)
				st.setLong(cont++, datos.getCModAnteriorId());
			if (datos.getCEstadoId() != 0L) {
				st.setLong(cont++, datos.getCEstadoId());
			}
			st.registerOutParameter(cont++, 4);

			filas = st.executeUpdate();
			idExpediente = st.getInt(--cont);
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return idExpediente;
	}

	public int bajaOCAPExpedientecarrera(Long cExpId) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			String sql = "DELETE FROM OCAP_EXPEDIENTESCARRERA WHERE C_EXP_ID = ?";
			st = con.prepareStatement(sql);
			st.setLong(1, cExpId.longValue());
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

	public int modificacionOCAPExpedientecarrera(OCAPExpedientecarreraOT datos, boolean sobreescribirExpdte, boolean resetearFechasCC) throws SQLException, Exception {
		PreparedStatement st = null;
		int filas = 0;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			this.loggerBD.info("modificacionOCAPExpedientecarrera");
			String sql = "UPDATE OCAP_EXPEDIENTESCARRERA SET ";
			if (datos.getFRespuestaInconf_solic() != null)
				sql = sql + " F_RESPUESTAINCONF_SOLIC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getCUsrId() != null)
				sql = sql + " C_USR_ID = ?, ";
			if ((datos.getDMotivoInconf_solic() != null) && (!datos.getDMotivoInconf_solic().equals("")))
				sql = sql + " D_MOTIVO_INCONF_SOLIC = ?, ";
			if ((datos.getDMotivoInconfMC() != null) && (!datos.getDMotivoInconfMC().equals("")))
				sql = sql + " D_MOTIVOINCONF_MC = ?, ";
			if (datos.getFModificacion() != null)
				sql = sql + " F_USUMODI = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			else {
				sql = sql + " F_USUMODI = SYSDATE, ";
			}
			if (datos.getCGradoId() != null)
				sql = sql + " C_GRADO_ID = ?, ";
			if (datos.getCModAnteriorId() != 0L)
				sql = sql + " C_MOD_ANTERIOR_ID = ?, ";
			if (datos.getFFinMc() != null)
				sql = sql + " F_FIN_MC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if ((datos.getBBorrado() != null) && (!datos.getBBorrado().equals("")))
				sql = sql + " B_BORRADO = ?, ";
			if (datos.getFInicioCc() != null) {
				sql = sql + " F_INICIO_CC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			} else if (resetearFechasCC) {
				sql = sql + " F_INICIO_CC = NULL, ";
			}
			if (datos.getFInicioCa() != null)
				sql = sql + " F_INICIO_CA = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFAceptacionceis() != null)
				sql = sql + " F_ACEPTACIONCEIS = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFAceptacionCC() != null)
				sql = sql + " F_ACEPTACIONCC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFAceptacSolic() != null)
				sql = sql + " F_ACEPTAC_SOLIC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if ((datos.getDMotivoNeg() != null) && (!datos.getDMotivoNeg().equals("")))
				sql = sql + " D_MOTIVO_NEG = ?, ";
			if ((datos.getDMotivoNegMC() != null) && (!datos.getDMotivoNegMC().equals("")))
				sql = sql + " D_MOTIVONEG_MC = ?, ";
			if (datos.getFInconf_solic() != null)
				sql = sql + " F_INCONF_SOLIC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFInconfMC() != null)
				sql = sql + " F_INCONF_MC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if ((datos.getDObservNeg_solic() != null) && (!datos.getDObservNeg_solic().equals("")))
				sql = sql + " D_OBSERVNEG_SOLIC = ?, ";
			if ((datos.getDObservNegMC() != null) && (!datos.getDObservNegMC().equals("")))
				sql = sql + " D_OBSERVNEG_MC = ?, ";
			if (datos.getFFinEvalMc() != null)
				sql = sql + " F_FIN_EVAL_MC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFRegistroSolic() != null)
				sql = sql + " F_REGISTRO_SOLIC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFInicioMc() != null)
				sql = sql + " F_INICIO_MC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFInicioEvalMc() != null)
				sql = sql + " F_INICIO_EVAL_MC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFFinCc() != null) {
				sql = sql + " F_FIN_CC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			}else if (resetearFechasCC) {
				sql = sql + " F_FIN_CC = NULL, ";
			}
			if (datos.getFFinCa() != null)
				sql = sql + " F_FIN_CA = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFNegacionSolic() != null)
				sql = sql + " F_NEGACION_SOLIC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFNegacionMC() != null)
				sql = sql + " F_NEGACION_MC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFCreacion() != null)
				sql = sql + " F_USUALTA = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if ((datos.getBInconf_plazaprop() != null) && (!datos.getBInconf_plazaprop().equals("")))
				sql = sql + " B_INCONF_PLAZAPROP = ?, ";
			if ((datos.getBInconf_antiguedad() != null) && (!datos.getBInconf_antiguedad().equals("")))
				sql = sql + " B_INCONF_ANTIGUEDAD = ?, ";
			if ((datos.getBPersonales() != null) && (!datos.getBPersonales().equals("")))
				sql = sql + " B_PERSONALES = ?, ";
			if ((datos.getBOtrosCentros() != null) && (!datos.getBOtrosCentros().equals("")))
				sql = sql + " B_OTROSCENTROS = ?, ";
			if ((datos.getBPlazo() != null) && (!datos.getBPlazo().equals("")))
				sql = sql + " B_PLAZO = ?, ";
			if (datos.getFSubsanacion() != null)
				sql = sql + " F_SUBSANACION = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if ((datos.getDRespuestaInconfSolic() != null) && (!datos.getDRespuestaInconfSolic().equals("")))
				sql = sql + " D_RESPUESTAINCONF_SOLIC = ?, ";
			if ((datos.getDRespuestaInconfMC() != null) && (!datos.getDRespuestaInconfMC().equals("")))
				sql = sql + " D_RESPUESTAINCONF_MC = ?, ";
			if (datos.getFRespuestaInconf_MC() != null) {
				sql = sql + " F_RESPUESTAINCONF_MC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			}

			if ((datos.getDMotivoNegRespuesta_MC() != null) && (!datos.getDMotivoNegRespuesta_MC().equals("")))
				sql = sql + " D_MOTIVONEGRESPUESTA_MC = ?, ";
			if (datos.getAModificadoPor() != null)
				sql = sql + " C_USUMODI = ?, ";
			if (datos.getNAniosAntiguedad() != 0L)
				sql = sql + " N_ANIOSANTIGUEDAD = ?, ";
			if(sobreescribirExpdte){
				if (datos.getNMesesAntiguedad() != 0L)
					sql = sql + " N_MESES_ANTIGUEDAD = ?, ";
				else
					sql = sql + " N_MESES_ANTIGUEDAD = NULL, ";
				if (datos.getNDiasAntiguedad() != 0L)
					sql = sql + " N_DIAS_ANTIGUEDAD = ?, ";
				else
					sql = sql + " N_DIAS_ANTIGUEDAD = NULL, ";
			}else{
				if (datos.getNMesesAntiguedad() != 0L)
					sql = sql + " N_MESES_ANTIGUEDAD = ?, ";
				if (datos.getNDiasAntiguedad() != 0L)
					sql = sql + " N_DIAS_ANTIGUEDAD = ?, ";
			}
			if (datos.getCConvocatoriaId() != 0L)
				sql = sql + " C_CONVOCATORIA_ID = ?, ";
			if (datos.getFRegistroOficial() != null)
				sql = sql + " F_REGISTRO_OFICIAL = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if ((datos.getCProcedimientoId() != null) && (!"".equals(datos.getCProcedimientoId())))
				sql = sql + " C_PROC_EVALUACION_ID = ?, ";
			if (datos.getCEstatutId() != 0L)
				sql = sql + " C_PERSONAL_ID = ?, ";
			if (datos.getCCompfqs_id() != 0L)
				sql = sql + " C_COMPFQS_ID = ?, ";
			if (datos.getBLopdMc() != null)
				sql = sql + " B_LOPD_MC = ?, ";
			if (datos.getBLopdCd() != null)
				sql = sql + " B_LOPD_CD = ?, ";
			if (datos.getFRegDocEscaneados() != null)
				sql = sql + " F_REG_DOCESCANEADOS = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFRegEvidenciasConf() != null)
				sql = sql + " F_REG_EVIDENCIASCONF = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if ((datos.getBOtroServicio() != null) && (!"".equals(datos.getBOtroServicio()))) {
				sql = sql + " B_OTROSERVICIO = ?, ";
			}
			if (datos.getCSitAdministrativaAuxId() != null) {
				sql = sql + " C_SIT_ADMINISTRATIVA_ID = ?, ";
			}
			if (datos.getDSitAdministrativaAuxOtros() != null) {
				sql = sql + " A_OTRASITADMINISTRATIVA = ?, ";
			}
			if ((datos.getCJuridico() != null) && (!"".equals(datos.getCJuridico()))) {
				sql = sql + " C_JURIDICO_ID = ?, ";
			}
			if (datos.getFRegistroOficial_Mc() != null) {
				sql = sql + " F_REGOFICIAL_MC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			}
			if (datos.getCEstatutActualId() != 0L) {
				sql = sql + "C_PERSONALACTUAL_ID = ?, ";
			}
			if (datos.getCProfesionalActual_id() != 0L) {
				sql = sql + "C_CATEGACTUAL_ID = ?, ";
			}
			if (datos.getCEspecActual_id() != 0L) {
				sql = sql + "C_ESPECACTUAL_ID = ?, ";
			}
			if ((datos.getBValidacioncc() != null) && (!datos.getBValidacioncc().equals(""))) {
				sql = sql + " B_VALIDACIONCC = ?, ";
			}
			if ((datos.getAPuesto() != null) && (!datos.getAPuesto().equals(""))) {
				sql = sql + " A_PUESTO = ?, ";
			}
			if ((datos.getAServicio() != null) && (!datos.getAServicio().equals(""))) {
				sql = sql + " A_SERVICIO = ?, ";
			}
			if ((datos.getBVerificaDatosFaseIII() != null) && (!datos.getBVerificaDatosFaseIII().equals(""))) {
				sql = sql + " B_VERIFICADATOS_FASEIII = ?, ";
			}
			if (datos.getFMotivadoAcep() != null)
				sql = sql + " F_MOTIVADO_ACEP = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFMotivadoNeg() != null)
				sql = sql + " F_MOTIVADO_NEG = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFAclaraciones() != null)
				sql = sql + " F_ACLARACIONES = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFDesistidoMC() != null) {
				sql = sql + " F_DESISTIDO_MC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			}

			if (datos.getCItinerarioId() != 0L)
				sql = sql + " C_ITINERARIO_ID = ?, ";
			if (datos.getAEspecificacionesEval() != null)
				sql = sql + " A_ESPECIFICACIONES_EVAL = ?, ";
			if (datos.getFInformeEval() != null)
				sql = sql + " F_INFORME_EVAL = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFInicioEvaluacion() != null)
				sql = sql + " F_INICIO_EVALUACION = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFInformeCTE() != null)
				sql = sql + " F_INFORME_CTE = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFReunionCTE() != null)
				sql = sql + " F_REUNION_CTE = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getBConformidadCTE() != null)
				sql = sql + " B_CONFORMIDAD_CTE = ?, ";
			if (datos.getBNuevaRevision() != null)
				sql = sql + " B_NUEVA_REVISION = ?, ";
			if (datos.getADiscrepanciasCTE() != null)
				sql = sql + " A_DISCREPANCIAS_CTE = ?, ";
			if (datos.getAEspecificacionesCTE() != null)
				sql = sql + " A_ESPECIFICACIONES_CTE = ?, ";
			if (datos.getFInformeCE() != null)
				sql = sql + " F_INFORME_CE = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getFReunionCE() != null)
				sql = sql + " F_REUNION_CE = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getBDecisionCE() != null)
				sql = sql + " B_DECISION_CE = ?, ";
			if (datos.getADiscrepanciasCE() != null)
				sql = sql + " A_DISCREPANCIAS_CE = ?, ";
			if (datos.getAEspecificacionesCE() != null)
				sql = sql + " A_ESPECIFICACIONES_CE = ?, ";
			if (datos.getBReconocimientoGrado() != null)
				sql = sql + " B_RECONOCIMIENTO_GRADO = ?, ";
			if (datos.getFInformeCC() != null)
				sql = sql + " F_INFORME_CC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if (datos.getAEspecificacionesCC() != null)
				sql = sql + " A_ESPECIFICACIONES_CC = ?, ";
			if (datos.getCSolicitudId() != 0L)
				sql = sql + " C_SOLICITUD_ID = ?, ";
			if ((datos.getDRegimenJuridicoOtros() != null) && (!datos.getDRegimenJuridicoOtros().equals("")))
				sql = sql + " A_OTROJURIDICO = ?, ";
			if (datos.getCEstadoId() != 0L)
				sql = sql + " C_ESTADO_ID = ?, ";
			if (datos.getCProfesionalId() != 0L)
				sql = sql + " C_PROFESIONAL_ID = ?, ";
			if (datos.getCEspecId() != 0L)
				sql = sql + " C_ESPEC_ID = ?, ";
			if (datos.getFDesistido() != null)
				sql = sql + " F_DESISTIDO = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			if ((datos.getDMotivosCambio() != null) && (!"".equals(datos.getDMotivosCambio())))
				sql = sql + " D_MOTIVOS_CAMBIO = ?, ";
			if ((datos.getBExclusion() != null) && (!"".equals(datos.getBExclusion()))) {
				sql = sql + " B_EXCLUSION = ?, ";
			}
			sql = sql + " N_CREDITOS_CTE  = ?, ";

			if (sql.substring(sql.length() - 2, sql.length()).equals(", ")) {
				sql = sql.substring(0, sql.length() - 2);
			}
			sql = sql + " WHERE C_EXP_ID = ?";

			st = con.prepareStatement(sql);
			int cont = 1;
			if (datos.getFRespuestaInconf_solic() != null) {
				st.setString(cont++,
						DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFRespuestaInconf_solic()));
			}
			if (datos.getCUsrId() != null)
				st.setLong(cont++, datos.getCUsrId().longValue());
			if ((datos.getDMotivoInconf_solic() != null) && (!datos.getDMotivoInconf_solic().equals("")))
				st.setString(cont++, datos.getDMotivoInconf_solic());
			if ((datos.getDMotivoInconfMC() != null) && (!datos.getDMotivoInconfMC().equals("")))
				st.setString(cont++, datos.getDMotivoInconfMC());
			if (datos.getFModificacion() != null)
				st.setString(cont++,
						DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFModificacion()));
			if (datos.getCGradoId() != null)
				st.setInt(cont++, datos.getCGradoId().intValue());
			if (datos.getCModAnteriorId() != 0L)
				st.setLong(cont++, datos.getCModAnteriorId());
			if (datos.getFFinMc() != null)
				st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFFinMc()));
			if ((datos.getBBorrado() != null) && (!datos.getBBorrado().equals("")))
				st.setString(cont++, datos.getBBorrado());
			if (datos.getFInicioCc() != null)
				st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInicioCc()));
			if (datos.getFInicioCa() != null) {
				if (datos.getFInicioCa().getTime() == 0L)
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInicioCa()));
			}
			if (datos.getFAceptacionceis() != null) {
				if (DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFAceptacionceis())
						.equals(Constantes.FECHA_NULA))
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFAceptacionceis()));
			}
			if (datos.getFAceptacionCC() != null) {
				if (DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFAceptacionCC())
						.equals(Constantes.FECHA_NULA))
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFAceptacionCC()));
			}
			if (datos.getFAceptacSolic() != null) {
				if (DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFAceptacSolic())
						.equals(Constantes.FECHA_NULA))
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFAceptacSolic()));
			}
			if ((datos.getDMotivoNeg() != null) && (!datos.getDMotivoNeg().equals("")))
				st.setString(cont++, datos.getDMotivoNeg());
			if ((datos.getDMotivoNegMC() != null) && (!datos.getDMotivoNegMC().trim().equals("") && (datos.getDMotivoNegMC().length()>=800)))
				st.setString(cont++, datos.getDMotivoNegMC().substring(0, 799));
			else if ((datos.getDMotivoNegMC() != null) && (!datos.getDMotivoNegMC().trim().equals("") && (datos.getDMotivoNegMC().length()<800))) 
					st.setString(cont++, datos.getDMotivoNegMC());
			if (datos.getFInconf_solic() != null) {
				if (DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInconf_solic())
						.equals(Constantes.FECHA_NULA))
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInconf_solic()));
			}
			if (datos.getFInconfMC() != null)
				st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInconfMC()));
			if ((datos.getDObservNeg_solic() != null) && (!datos.getDObservNeg_solic().equals("")))
				st.setString(cont++, datos.getDObservNeg_solic());
			if ((datos.getDObservNegMC() != null) && (!datos.getDObservNegMC().trim().equals("")))
				st.setString(cont++, datos.getDObservNegMC());
			else if (" ".equals(datos.getDObservNegMC()))
				st.setNull(cont++, 12);
			if (datos.getFFinEvalMc() != null)
				st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFFinEvalMc()));
			if (datos.getFRegistroSolic() != null)
				st.setString(cont++,
						DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFRegistroSolic()));
			if (datos.getFInicioMc() != null)
				st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInicioMc()));
			if (datos.getFInicioEvalMc() != null) {
				if (datos.getFInicioEvalMc().getTime() == 0L)
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInicioEvalMc()));
			}
			if (datos.getFFinCc() != null)
				st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFFinCc()));
			if (datos.getFFinCa() != null) {
				if (DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFFinCa())
						.equals(Constantes.FECHA_NULA))
					st.setNull(cont++, 91);
				else
					st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFFinCa()));
			}
			if (datos.getFNegacionSolic() != null) {
				if (DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFNegacionSolic())
						.equals(Constantes.FECHA_NULA))
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFNegacionSolic()));
			}
			if (datos.getFNegacionMC() != null) {
				if (DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFNegacionMC())
						.equals(Constantes.FECHA_NULA))
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFNegacionMC()));
			}
			if (datos.getFCreacion() != null)
				st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFCreacion()));
			if ((datos.getBInconf_plazaprop() != null) && (!datos.getBInconf_plazaprop().equals("")))
				st.setString(cont++, datos.getBInconf_plazaprop());
			if ((datos.getBInconf_antiguedad() != null) && (!datos.getBInconf_antiguedad().equals("")))
				st.setString(cont++, datos.getBInconf_antiguedad());
			if ((datos.getBPersonales() != null) && (!datos.getBPersonales().equals("")))
				st.setString(cont++, datos.getBPersonales());
			if ((datos.getBOtrosCentros() != null) && (!datos.getBOtrosCentros().equals("")))
				st.setString(cont++, datos.getBOtrosCentros());
			if ((datos.getBPlazo() != null) && (!datos.getBPlazo().equals("")))
				st.setString(cont++, datos.getBPlazo());
			if (datos.getFSubsanacion() != null) {
				if (DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFSubsanacion())
						.equals(Constantes.FECHA_NULA))
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFSubsanacion()));
			}
			if ((datos.getDRespuestaInconfSolic() != null) && (!datos.getDRespuestaInconfSolic().equals("")))
				st.setString(cont++, datos.getDRespuestaInconfSolic());
			if ((datos.getDRespuestaInconfMC() != null) && (!datos.getDRespuestaInconfMC().equals("")))
				st.setString(cont++, datos.getDRespuestaInconfMC());
			if (datos.getFRespuestaInconf_MC() != null) {
				if (DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFRespuestaInconf_MC())
						.equals(Constantes.FECHA_NULA))
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFRespuestaInconf_MC()));
			}
			if ((datos.getDMotivoNegRespuesta_MC() != null) && (!datos.getDMotivoNegRespuesta_MC().equals("")))
				st.setString(cont++, datos.getDMotivoNegRespuesta_MC());
			if (datos.getAModificadoPor() != null)
				st.setString(cont++, datos.getAModificadoPor());
			if (datos.getNAniosAntiguedad() != 0L) {
				if (datos.getNAniosAntiguedad() == -1)
					st.setLong(cont++, 0L);
				else
					st.setLong(cont++, datos.getNAniosAntiguedad());
			}
			if (datos.getNMesesAntiguedad() != 0L) {
				if (datos.getNMesesAntiguedad() == -1)
					st.setLong(cont++, 0L);
				else
					st.setLong(cont++, datos.getNMesesAntiguedad());
			}
			if (datos.getNDiasAntiguedad() != 0L) {
				if (datos.getNDiasAntiguedad() == -1)
					st.setLong(cont++, 0L);
				else
					st.setLong(cont++, datos.getNDiasAntiguedad());
			}
			if (datos.getCConvocatoriaId() != 0L)
				st.setLong(cont++, datos.getCConvocatoriaId());
			if (datos.getFRegistroOficial() != null)
				st.setString(cont++,
						DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFRegistroOficial()));
			if ((datos.getCProcedimientoId() != null) && (!"".equals(datos.getCProcedimientoId())))
				st.setString(cont++, datos.getCProcedimientoId());
			if (datos.getCEstatutId() != 0L)
				st.setLong(cont++, datos.getCEstatutId());
			if (datos.getCCompfqs_id() != 0L)
				st.setLong(cont++, datos.getCCompfqs_id());
			if (datos.getBLopdMc() != null)
				st.setString(cont++, datos.getBLopdMc());
			if (datos.getBLopdCd() != null)
				st.setString(cont++, datos.getBLopdCd());
			if (datos.getFRegDocEscaneados() != null) {
				if (datos.getFRegDocEscaneados().getTime() == 0L)
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFRegDocEscaneados()));
			}
			if (datos.getFRegEvidenciasConf() != null)
				st.setString(cont++,
						DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFRegEvidenciasConf()));
			if ((datos.getBOtroServicio() != null) && (!"".equals(datos.getBOtroServicio())))
				st.setString(cont++, datos.getBOtroServicio());
			if (datos.getCSitAdministrativaAuxId() != null)
				st.setString(cont++, datos.getCSitAdministrativaAuxId());
			if (datos.getDSitAdministrativaAuxOtros() != null)
				st.setString(cont++, datos.getDSitAdministrativaAuxOtros());
			if ((datos.getCJuridico() != null) && (!"".equals(datos.getCJuridico())))
				st.setString(cont++, datos.getCJuridico());
			if (datos.getFRegistroOficial_Mc() != null)
				st.setString(cont++,
						DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFRegistroOficial_Mc()));
			if (datos.getCEstatutActualId() != 0L)
				st.setLong(cont++, datos.getCEstatutActualId());
			if (datos.getCProfesionalActual_id() != 0L)
				st.setLong(cont++, datos.getCProfesionalActual_id());
			if (datos.getCEspecActual_id() > 0L)
				st.setLong(cont++, datos.getCEspecActual_id());
			else if (datos.getCEspecActual_id() < 0L) {
				st.setNull(cont++, 4);
			}
			if ((datos.getBValidacioncc() != null) && (!"".equals(datos.getBValidacioncc()))) {
				st.setString(cont++, datos.getBValidacioncc());
			}
			if ((datos.getAPuesto() != null) && (!datos.getAPuesto().equals(""))) {
				st.setString(cont++, datos.getAPuesto());
			}
			if ((datos.getAServicio() != null) && (!datos.getAServicio().equals(""))) {
				st.setString(cont++, datos.getAServicio());
			}
			if ((datos.getBVerificaDatosFaseIII() != null) && (!datos.getBVerificaDatosFaseIII().equals("")))
				st.setString(cont++, datos.getBVerificaDatosFaseIII());
			if (datos.getFMotivadoAcep() != null)
				st.setString(cont++,
						DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFMotivadoAcep()));
			if (datos.getFMotivadoNeg() != null)
				st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFMotivadoNeg()));
			if (datos.getFAclaraciones() != null)
				st.setString(cont++,
						DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFAclaraciones()));
			if (datos.getFDesistidoMC() != null) {
				st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFDesistidoMC()));
			}

			if (datos.getCItinerarioId() != 0L)
				st.setLong(cont++, datos.getCItinerarioId());
			if (datos.getAEspecificacionesEval() != null)
				st.setString(cont++, datos.getAEspecificacionesEval());
			if (datos.getFInformeEval() != null)
				st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInformeEval()));
			if (datos.getFInicioEvaluacion() != null)
				st.setString(cont++,
						DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInicioEvaluacion()));
			if (datos.getFInformeCTE() != null)
				st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInformeCTE()));
			if (datos.getFReunionCTE() != null)
				st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFReunionCTE()));
			if (datos.getBConformidadCTE() != null)
				st.setString(cont++, datos.getBConformidadCTE());
			if (datos.getBNuevaRevision() != null)
				st.setString(cont++, datos.getBNuevaRevision());
			if (datos.getADiscrepanciasCTE() != null)
				st.setString(cont++, datos.getADiscrepanciasCTE());
			if (datos.getAEspecificacionesCTE() != null)
				st.setString(cont++, datos.getAEspecificacionesCTE());
			if (datos.getFInformeCE() != null)
				st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInformeCE()));
			if (datos.getFReunionCE() != null)
				st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFReunionCE()));
			if (datos.getBDecisionCE() != null)
				st.setString(cont++, datos.getBDecisionCE());
			if (datos.getADiscrepanciasCE() != null)
				st.setString(cont++, datos.getADiscrepanciasCE());
			if (datos.getAEspecificacionesCE() != null)
				st.setString(cont++, datos.getAEspecificacionesCE());
			if (datos.getBReconocimientoGrado() != null)
				st.setString(cont++, datos.getBReconocimientoGrado());
			if (datos.getFInformeCC() != null)
				st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInformeCC()));
			if (datos.getAEspecificacionesCC() != null)
				st.setString(cont++, datos.getAEspecificacionesCC());
			if (datos.getCSolicitudId() != 0L)
				st.setLong(cont++, datos.getCSolicitudId());
			if ((datos.getDRegimenJuridicoOtros() != null) && (!datos.getDRegimenJuridicoOtros().equals("")))
				st.setString(cont++, datos.getDRegimenJuridicoOtros());
			if (datos.getCEstadoId() != 0L)
				st.setLong(cont++, datos.getCEstadoId());
			if (datos.getCProfesionalId() != 0L)
				st.setLong(cont++, datos.getCProfesionalId());
			if (datos.getCEspecId() > 0L)
				st.setLong(cont++, datos.getCEspecId());
			else if (datos.getCEspecId() < 0L)
				st.setNull(cont++, 4);
			if (datos.getFDesistido() != null) {
				if (DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFDesistido())
						.equals(Constantes.FECHA_NULA))
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFDesistido()));
			}
			if ((datos.getDMotivosCambio() != null) && (!"".equals(datos.getDMotivosCambio())))
				st.setString(cont++, datos.getDMotivosCambio());
			if ((datos.getBExclusion() != null) && (!"".equals(datos.getBExclusion()))) {
				st.setString(cont++, datos.getBExclusion());
			}
			st.setFloat(cont++, (float) datos.getNCreditosEvaluadosCTE());

			st.setLong(cont++, datos.getCExpId().longValue());

			filas = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
	}

	public OCAPExpedientecarreraOT buscarOCAPExpedientecarrera(Long cExpId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = null;
		OCAPExpedientecarreraOT datos = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarOCAPExpedientecarrera: Inicio");
			con = JCYLGestionTransacciones.getConnection();

			String sql = "SELECT F_INCONF_SOLIC, F_RESPUESTAINCONF_SOLIC, F_RESPUESTAINCONF_MC, C_USR_ID, F_USUMODI, C_GRADO_ID, F_INICIO_EVALUACION, F_FIN_MC, B_BORRADO, F_INICIO_CA, F_ACEPTACIONCC, F_ACEPTACIONCEIS, F_ACEPTAC_SOLIC, D_OBSERVNEG_SOLIC, D_OBSERVNEG_MC, F_FIN_EVAL_MC, F_REGISTRO_SOLIC, F_REGISTRO_OFICIAL, F_REG_DOCESCANEADOS, F_REG_EVIDENCIASCONF, F_INICIO_MC, F_INICIO_EVAL_MC, C_EXP_ID, F_INICIO_CC,F_FIN_CC, F_FIN_CA, F_NEGACION_SOLIC, F_USUALTA, N_ANIOSANTIGUEDAD, N_MESES_ANTIGUEDAD, N_DIAS_ANTIGUEDAD, B_INCONF_ANTIGUEDAD, B_INCONF_PLAZAPROP, B_PERSONALES, B_OTROSCENTROS, B_PLAZO, F_NEGACION_MC, F_INCONF_MC, C_CONVOCATORIA_ID, D_MOTIVONEG_MC, D_MOTIVO_INCONF_SOLIC, D_MOTIVOINCONF_MC, D_RESPUESTAINCONF_SOLIC, D_RESPUESTAINCONF_MC, D_OBSERVINCONF_SOLIC, D_OBSERVINCONF_MC, D_MOTIVONEGRESPUESTA_MC, C_PROC_EVALUACION_ID, C_PERSONAL_ID, C_COMPFQS_ID, B_OTROSERVICIO, C_SIT_ADMINISTRATIVA_ID, A_OTRASITADMINISTRATIVA, C_JURIDICO, F_REGOFICIAL_MC, C_PERSONALACTUAL_ID, C_CATEGACTUAL_ID, C_ESPECACTUAL_ID, F_SUBSANACION, B_VALIDACIONCC, B_VERIFICADATOS_FASEIII, F_ACLARACIONES, F_MOTIVADO_NEG, F_MOTIVADO_ACEP, F_DESISTIDO_MC, C_ITINERARIO_ID, F_INFORME_EVAL, F_INFORME_CE, F_INFORME_CTE, B_RECONOCIMIENTO_GRADO, C_SOLICITUD_ID, A_PUESTO, A_SERVICIO, C_JURIDICO_ID, A_OTROJURIDICO, C_PROFESIONAL_ID, C_ESPEC_ID, C_ESTADO_ID, C_MOD_ANTERIOR_ID, F_INFORME_CC, A_ESPECIFICACIONES_CC, A_ESPECIFICACIONES_CE, A_ESPECIFICACIONES_CTE, A_ESPECIFICACIONES_EVAL, A_DISCREPANCIAS_CE, A_DISCREPANCIAS_CTE, B_NUEVA_REVISION, F_REUNION_CE, F_REUNION_CTE, B_CONFORMIDAD_CTE, B_DECISION_CE, N_CREDITOS_CTE, B_MERITOS_BLOQUEADOS, F_RENUNCIA  FROM OCAP_EXPEDIENTESCARRERA WHERE C_EXP_ID = ? ";

			st = con.prepareStatement(sql);
			st.setLong(1, cExpId.longValue());
			OCAPConfigApp.logger
					.info(getClass().getName() + " buscarOCAPExpedientecarrera: C_EXP_ID: " + cExpId.longValue());

			rs = st.executeQuery();
			datos = new OCAPExpedientecarreraOT();

			if (rs.next()) {
				datos.setFInconf_solic(rs.getDate("F_INCONF_SOLIC"));
				datos.setFRespuestaInconf_solic(rs.getTimestamp("F_RESPUESTAINCONF_SOLIC"));
				datos.setFRespuestaInconf_MC(rs.getTimestamp("F_RESPUESTAINCONF_MC"));
				datos.setCUsrId(Long.valueOf(rs.getString("C_USR_ID")));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setCGradoId(new Integer(rs.getInt("C_GRADO_ID")));
				datos.setFFinMc(rs.getTimestamp("F_FIN_MC"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setFInicioCa(rs.getTimestamp("F_INICIO_CA"));
				datos.setFAceptacionCC(rs.getTimestamp("F_ACEPTACIONCC"));
				datos.setFAceptacionceis(rs.getTimestamp("F_ACEPTACIONCEIS"));
				datos.setFAceptacSolic(rs.getTimestamp("F_ACEPTAC_SOLIC"));
				datos.setDObservNeg_solic(rs.getString("D_OBSERVNEG_SOLIC"));
				datos.setDObservNegMC(rs.getString("D_OBSERVNEG_MC"));
				datos.setFFinEvalMc(rs.getTimestamp("F_FIN_EVAL_MC"));
				datos.setFRegistroSolic(rs.getTimestamp("F_REGISTRO_SOLIC"));
				datos.setFRegistroOficial(rs.getTimestamp("F_REGISTRO_OFICIAL"));
				datos.setFRegDocEscaneados(rs.getDate("F_REG_DOCESCANEADOS"));
				datos.setFRegEvidenciasConf(rs.getDate("F_REG_EVIDENCIASCONF"));
				datos.setFInicioMc(rs.getTimestamp("F_INICIO_MC"));
				datos.setFInicioEvalMc(rs.getTimestamp("F_INICIO_EVAL_MC"));
				datos.setCExpId(cExpId);
				datos.setFInicioCc(rs.getTimestamp("F_INICIO_CC"));
				datos.setFFinCc(rs.getTimestamp("F_FIN_CC"));
				datos.setFFinCa(rs.getTimestamp("F_FIN_CA"));
				datos.setFNegacionSolic(rs.getTimestamp("F_NEGACION_SOLIC"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setNAniosAntiguedad(rs.getLong("N_ANIOSANTIGUEDAD"));
				datos.setNMesesAntiguedad(rs.getLong("N_MESES_ANTIGUEDAD"));
				datos.setNDiasAntiguedad(rs.getLong("N_DIAS_ANTIGUEDAD"));
				datos.setBInconf_antiguedad(rs.getString("B_INCONF_ANTIGUEDAD"));
				datos.setBInconf_plazaprop(rs.getString("B_INCONF_PLAZAPROP"));
				datos.setBPersonales(rs.getString("B_PERSONALES"));
				datos.setBOtrosCentros(rs.getString("B_OTROSCENTROS"));
				datos.setBPlazo(rs.getString("B_PLAZO"));
				datos.setFNegacionMC(rs.getTimestamp("F_NEGACION_MC"));
				datos.setFInconfMC(rs.getTimestamp("F_INCONF_MC"));
				datos.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
				datos.setDMotivoNegMC(rs.getString("D_MOTIVONEG_MC"));
				datos.setDMotivoInconf_solic(rs.getString("D_MOTIVO_INCONF_SOLIC"));
				datos.setDMotivoInconfMC(rs.getString("D_MOTIVOINCONF_MC"));
				datos.setDRespuestaInconfSolic(rs.getString("D_RESPUESTAINCONF_SOLIC"));
				datos.setDRespuestaInconfMC(rs.getString("D_RESPUESTAINCONF_MC"));

				datos.setDMotivoNegRespuesta_MC(rs.getString("D_MOTIVONEGRESPUESTA_MC"));
				datos.setCProcedimientoId(rs.getString("C_PROC_EVALUACION_ID"));
				datos.setCEstatutId(rs.getLong("C_PERSONAL_ID"));
				datos.setCCompfqs_id(rs.getLong("C_COMPFQS_ID"));
				datos.setBOtroServicio(rs.getString("B_OTROSERVICIO"));
				datos.setCSitAdministrativaAuxId(rs.getString("C_SIT_ADMINISTRATIVA_ID"));
				datos.setDSitAdministrativaAuxOtros(rs.getString("A_OTRASITADMINISTRATIVA"));

				datos.setFRegistroOficial_Mc(rs.getTimestamp("F_REGOFICIAL_MC"));
				datos.setCEstatutActualId(rs.getLong("C_PERSONALACTUAL_ID"));
				datos.setCProfesionalActual_id(rs.getLong("C_CATEGACTUAL_ID"));
				datos.setCEspecActual_id(rs.getLong("C_ESPECACTUAL_ID"));
				datos.setFSubsanacion(rs.getTimestamp("F_SUBSANACION"));
				datos.setBValidacioncc(rs.getString("B_VALIDACIONCC"));
				datos.setBVerificaDatosFaseIII(rs.getString("B_VERIFICADATOS_FASEIII"));
				datos.setFAclaraciones(rs.getTimestamp("F_ACLARACIONES"));
				datos.setFMotivadoNeg(rs.getTimestamp("F_MOTIVADO_NEG"));
				datos.setFMotivadoAcep(rs.getTimestamp("F_MOTIVADO_ACEP"));
				datos.setFDesistidoMC(rs.getTimestamp("F_DESISTIDO_MC"));
				datos.setCItinerarioId(rs.getLong("C_ITINERARIO_ID"));
				datos.setFInformeEval(rs.getTimestamp("F_INFORME_EVAL"));
				datos.setFInformeCE(rs.getTimestamp("F_INFORME_CE"));
				datos.setFInformeCTE(rs.getTimestamp("F_INFORME_CTE"));
				datos.setFInformeCC(rs.getTimestamp("F_INFORME_CC"));
				datos.setBReconocimientoGrado(rs.getString("B_RECONOCIMIENTO_GRADO"));
				datos.setCSolicitudId(rs.getLong("C_SOLICITUD_ID"));
				datos.setAPuesto(rs.getString("A_PUESTO"));
				datos.setAServicio(rs.getString("A_SERVICIO"));
				datos.setCJuridico(rs.getString("C_JURIDICO_ID"));
				datos.setDRegimenJuridicoOtros(rs.getString("A_OTROJURIDICO"));
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
				datos.setCEspecId(rs.getLong("C_ESPEC_ID"));
				datos.setCEstadoId(rs.getLong("C_ESTADO_ID"));
				datos.setCModAnteriorId(rs.getLong("C_MOD_ANTERIOR_ID"));
				datos.setAEspecificacionesCC(rs.getString("A_ESPECIFICACIONES_CC"));
				datos.setAEspecificacionesCE(rs.getString("A_ESPECIFICACIONES_CE"));
				datos.setAEspecificacionesCTE(rs.getString("A_ESPECIFICACIONES_CTE"));
				datos.setAEspecificacionesEval(rs.getString("A_ESPECIFICACIONES_EVAL"));
				datos.setFInicioEvaluacion(rs.getDate("F_INICIO_EVALUACION"));
				datos.setFReunionCE(rs.getDate("F_REUNION_CE"));
				datos.setFReunionCTE(rs.getDate("F_REUNION_CTE"));
				datos.setADiscrepanciasCE(rs.getString("A_DISCREPANCIAS_CE"));
				datos.setADiscrepanciasCTE(rs.getString("A_DISCREPANCIAS_CTE"));
				datos.setBNuevaRevision(rs.getString("B_NUEVA_REVISION"));
				datos.setBConformidadCTE(rs.getString("B_CONFORMIDAD_CTE"));
				datos.setBDecisionCE(rs.getString("B_DECISION_CE"));
				datos.setNCreditosEvaluadosCTE(rs.getDouble("N_CREDITOS_CTE"));
				datos.setMeritosBloqueados(rs.getString("B_MERITOS_BLOQUEADOS"));
				datos.setFRenuncia(rs.getDate("F_RENUNCIA"));
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

	public long buscarCExpIdPorUsuarioConvocatoria(long cUsrId, long cConvoId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		long cExpId = 0L;
		Connection con = JCYLGestionTransacciones.getConnection();
		StringBuffer sql = new StringBuffer();
		try {
			sql.append("SELECT C_EXP_ID FROM OCAP_EXPEDIENTESCARRERA ").append("WHERE C_USR_ID = ? ");

			if (cConvoId != 0L) {
				sql.append(" AND C_CONVOCATORIA_ID = ? ");
			}
			sql.append(" AND B_BORRADO = 'N' ").append(" ORDER BY F_REGISTRO_OFICIAL DESC ");

			st = con.prepareStatement(sql.toString());
			st.setLong(1, cUsrId);
			if (cConvoId != 0L)
				st.setLong(2, cConvoId);
			rs = st.executeQuery();
			if (rs.next())
				cExpId = rs.getLong("C_EXP_ID");
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return cExpId;
	}

	public OCAPExpedientecarreraOT buscarExpedienteCarreraPorUsuarioConvocatoria(Long cUsrId, Long cConvocatoriaId)
			throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		StringBuffer sql = new StringBuffer();
		OCAPExpedientecarreraOT datos = null;
		try {
			sql.append(
					"SELECT C_EXP_ID, F_RESPUESTAINCONF_SOLIC, C_USR_ID, F_USUMODI, C_GRADO_ID, C_CONVOCATORIA_ID, TO_CHAR(F_FIN_MC,'DD/MM/RRRR hh24:mi')F_FIN_MC, ")
					.append("B_BORRADO, F_INICIO_CC, F_INICIO_CA, F_ACEPTACIONCC, F_ACEPTACIONCEIS, F_ACEPTAC_SOLIC, ")
					.append("F_INCONF_SOLIC, D_OBSERVNEG_SOLIC, F_FIN_EVAL_MC, F_REGISTRO_SOLIC, F_INICIO_MC, F_INICIO_EVAL_MC, C_EXP_ID, F_FIN_CC, ")
					.append("F_FIN_CA, F_NEGACION_SOLIC, F_USUALTA, N_ANIOSANTIGUEDAD, N_MESES_ANTIGUEDAD, N_DIAS_ANTIGUEDAD, B_INCONF_ANTIGUEDAD, ")
					.append("B_INCONF_PLAZAPROP, B_PERSONALES, B_OTROSCENTROS, B_PLAZO, C_PROC_EVALUACION_ID, C_PERSONAL_ID, B_LOPD_MC, B_LOPD_CD, ")
					.append("F_REG_DOCESCANEADOS, F_REG_EVIDENCIASCONF, B_OTROSERVICIO, C_SIT_ADMINISTRATIVA_ID, A_OTRASITADMINISTRATIVA, C_JURIDICO_ID, ")
					.append("C_PERSONALACTUAL_ID, C_CATEGACTUAL_ID, C_ESPECACTUAL_ID, A_PUESTO, F_NEGACION_MC, B_VALIDACIONCC, C_ITINERARIO_ID, ")
					.append("A_ESPECIFICACIONES_EVAL, F_INFORME_EVAL, F_INICIO_EVALUACION, B_CONFORMIDAD_CTE, B_NUEVA_REVISION, A_DISCREPANCIAS_CTE, ")
					.append("A_ESPECIFICACIONES_CTE, F_INFORME_CTE, F_REUNION_CTE, B_DECISION_CE, A_DISCREPANCIAS_CE, A_ESPECIFICACIONES_CE, F_INFORME_CE, ")
					.append("F_REUNION_CE, F_INFORME_CC, A_ESPECIFICACIONES_CC, C_PROFESIONAL_ID, C_ESPEC_ID, C_MOD_ANTERIOR_ID, A_SERVICIO, C_ESTADO_ID, ")
					.append("F_RESPUESTAINCONF_MC, N_CREDITOS_CTE, B_MERITOS_BLOQUEADOS ").append(" FROM OCAP_EXPEDIENTESCARRERA ")
					.append(" WHERE C_USR_ID = ? ");

			if ((cConvocatoriaId != null) && (cConvocatoriaId.longValue() != 0L)) {
				sql.append(" AND C_CONVOCATORIA_ID = ? ");
			}
			sql.append(" AND B_BORRADO = 'N' ").append(" ORDER BY F_REGISTRO_OFICIAL DESC ");

			st = con.prepareStatement(sql.toString());
			st.setLong(1, cUsrId.longValue());
			if ((cConvocatoriaId != null) && (cConvocatoriaId.longValue() != 0L))
				st.setLong(2, cConvocatoriaId.longValue());
			rs = st.executeQuery();
			datos = new OCAPExpedientecarreraOT();
			if (rs.next()) {
				datos.setCExpId(Long.valueOf(rs.getLong("C_EXP_ID")));
				datos.setFRespuestaInconf_solic(rs.getTimestamp("F_RESPUESTAINCONF_SOLIC"));
				datos.setCUsrId(Long.valueOf(rs.getLong("C_USR_ID")));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setCGradoId(Integer.valueOf(rs.getInt("C_GRADO_ID")));
				datos.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
				SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_COMPLETA_HHMM);
				if (rs.getString("F_FIN_MC") != null)
					datos.setFFinMc(df.parse(rs.getString("F_FIN_MC")));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setFInicioCc(rs.getDate("F_INICIO_CC"));
				datos.setFInicioCa(rs.getTimestamp("F_INICIO_CA"));
				datos.setFAceptacionceis(rs.getTimestamp("F_ACEPTACIONCEIS"));
				datos.setFAceptacionCC(rs.getTimestamp("F_ACEPTACIONCC"));
				datos.setFAceptacSolic(rs.getTimestamp("F_ACEPTAC_SOLIC"));
				datos.setFInconf_solic(rs.getTimestamp("F_INCONF_SOLIC"));
				datos.setDObservNeg_solic(rs.getString("D_OBSERVNEG_SOLIC"));
				datos.setFFinEvalMc(rs.getDate("F_FIN_EVAL_MC"));
				datos.setFRegistroSolic(rs.getTimestamp("F_REGISTRO_SOLIC"));
				datos.setFInicioMc(rs.getTimestamp("F_INICIO_MC"));
				datos.setFInicioEvalMc(rs.getDate("F_INICIO_EVAL_MC"));
				datos.setCExpId(Long.valueOf(rs.getString("C_EXP_ID")));
				datos.setFFinCc(rs.getDate("F_FIN_CC"));
				datos.setFFinCa(rs.getDate("F_FIN_CA"));
				datos.setFNegacionSolic(rs.getTimestamp("F_NEGACION_SOLIC"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setNAniosAntiguedad(rs.getLong("N_ANIOSANTIGUEDAD"));
				datos.setNMesesAntiguedad(rs.getLong("N_MESES_ANTIGUEDAD"));
				datos.setNDiasAntiguedad(rs.getLong("N_DIAS_ANTIGUEDAD"));
				datos.setBInconf_antiguedad(rs.getString("B_INCONF_ANTIGUEDAD"));
				datos.setBInconf_plazaprop(rs.getString("B_INCONF_PLAZAPROP"));
				datos.setBPersonales(rs.getString("B_PERSONALES"));
				datos.setBOtrosCentros(rs.getString("B_OTROSCENTROS"));
				datos.setBPlazo(rs.getString("B_PLAZO"));
				datos.setCProcedimientoId(rs.getString("C_PROC_EVALUACION_ID"));
				datos.setCEstatutId(rs.getLong("C_PERSONAL_ID"));
				datos.setBLopdMc(rs.getString("B_LOPD_MC"));
				datos.setBLopdCd(rs.getString("B_LOPD_CD"));
				datos.setFRegDocEscaneados(rs.getDate("F_REG_DOCESCANEADOS"));
				datos.setFRegEvidenciasConf(rs.getDate("F_REG_EVIDENCIASCONF"));
				datos.setBOtroServicio(rs.getString("B_OTROSERVICIO"));
				datos.setCSitAdministrativaAuxId(rs.getString("C_SIT_ADMINISTRATIVA_ID"));
				datos.setDSitAdministrativaAuxOtros(rs.getString("A_OTRASITADMINISTRATIVA"));
				datos.setCJuridico(rs.getString("C_JURIDICO_ID"));
				datos.setCEstatutActualId(rs.getLong("C_PERSONALACTUAL_ID"));
				datos.setCProfesionalActual_id(rs.getLong("C_CATEGACTUAL_ID"));
				datos.setCEspecActual_id(rs.getLong("C_ESPECACTUAL_ID"));
				datos.setAPuesto(rs.getString("A_PUESTO"));
				datos.setFNegacionMC(rs.getTimestamp("F_NEGACION_MC"));
				datos.setBValidacioncc(rs.getString("B_VALIDACIONCC"));
				datos.setCItinerarioId(rs.getLong("C_ITINERARIO_ID"));
				datos.setAEspecificacionesEval(rs.getString("A_ESPECIFICACIONES_EVAL"));
				datos.setFInformeEval(rs.getTimestamp("F_INFORME_EVAL"));
				datos.setFInicioEvaluacion(rs.getTimestamp("F_INICIO_EVALUACION"));
				datos.setBConformidadCTE(rs.getString("B_CONFORMIDAD_CTE"));
				datos.setBNuevaRevision(rs.getString("B_NUEVA_REVISION"));
				datos.setADiscrepanciasCTE(rs.getString("A_DISCREPANCIAS_CTE"));
				datos.setAEspecificacionesCTE(rs.getString("A_ESPECIFICACIONES_CTE"));
				datos.setFInformeCTE(rs.getTimestamp("F_INFORME_CTE"));
				datos.setFReunionCTE(rs.getTimestamp("F_REUNION_CTE"));
				datos.setBDecisionCE(rs.getString("B_DECISION_CE"));
				datos.setADiscrepanciasCE(rs.getString("A_DISCREPANCIAS_CE"));
				datos.setAEspecificacionesCE(rs.getString("A_ESPECIFICACIONES_CE"));
				datos.setFInformeCE(rs.getTimestamp("F_INFORME_CE"));
				datos.setFReunionCE(rs.getTimestamp("F_REUNION_CE"));
				datos.setFInformeCC(rs.getTimestamp("F_INFORME_CC"));
				datos.setAEspecificacionesCC(rs.getString("A_ESPECIFICACIONES_CC"));
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
				datos.setCEspecId(rs.getLong("C_ESPEC_ID"));
				datos.setCModAnteriorId(rs.getLong("C_MOD_ANTERIOR_ID"));
				datos.setAServicio(rs.getString("A_SERVICIO"));
				datos.setCEstadoId(rs.getLong("C_ESTADO_ID"));
				datos.setFRespuestaInconf_MC(rs.getTimestamp("F_RESPUESTAINCONF_MC"));
				datos.setNCreditosEvaluadosCTE(rs.getLong("N_CREDITOS_CTE"));
				datos.setMeritosBloqueados(rs.getString("B_MERITOS_BLOQUEADOS"));
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

	public OCAPExpedientecarreraOT buscarOCAPExpedientecarreraPorUsuario(Long cUsrId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		OCAPExpedientecarreraOT datos = null;
		try {
			this.loggerBD.info("buscarOCAPExpedientecarreraPorUsuario: " + cUsrId);

			String sql = "SELECT C_EXP_ID, F_RESPUESTAINCONF_SOLIC, C_USR_ID, F_USUMODI, C_GRADO_ID, C_CONVOCATORIA_ID, TO_CHAR(F_FIN_MC,'DD/MM/RRRR hh24:mi') F_FIN_MC, B_BORRADO, F_INICIO_CC, F_INICIO_CA, F_ACEPTACIONCC, F_ACEPTACIONCEIS, F_ACEPTAC_SOLIC, F_INCONF_SOLIC, D_OBSERVNEG_SOLIC, F_FIN_EVAL_MC, F_REGISTRO_SOLIC, F_INICIO_MC, F_INICIO_EVAL_MC, C_EXP_ID, F_FIN_CC, F_FIN_CA, F_NEGACION_SOLIC, F_USUALTA, N_ANIOSANTIGUEDAD, N_MESES_ANTIGUEDAD, N_DIAS_ANTIGUEDAD, B_INCONF_ANTIGUEDAD, B_INCONF_PLAZAPROP, B_PERSONALES, B_OTROSCENTROS, B_PLAZO, C_PROC_EVALUACION_ID, C_PERSONAL_ID, B_LOPD_MC, B_LOPD_CD, F_REG_DOCESCANEADOS, F_REG_EVIDENCIASCONF, B_OTROSERVICIO, C_SIT_ADMINISTRATIVA_ID, A_OTRASITADMINISTRATIVA, C_JURIDICO_ID, C_PERSONALACTUAL_ID, C_CATEGACTUAL_ID, C_ESPECACTUAL_ID, A_PUESTO, F_NEGACION_MC, B_VALIDACIONCC, C_ITINERARIO_ID, A_ESPECIFICACIONES_EVAL, F_INFORME_EVAL, F_INICIO_EVALUACION, B_CONFORMIDAD_CTE, B_NUEVA_REVISION, A_DISCREPANCIAS_CTE, A_ESPECIFICACIONES_CTE, F_INFORME_CTE, F_REUNION_CTE, B_DECISION_CE, A_DISCREPANCIAS_CE, A_ESPECIFICACIONES_CE, F_INFORME_CE, F_REUNION_CE, F_INFORME_CC, A_ESPECIFICACIONES_CC, C_PROFESIONAL_ID, C_ESPEC_ID, C_MOD_ANTERIOR_ID, A_SERVICIO, C_ESTADO_ID, F_RESPUESTAINCONF_MC  FROM OCAP_EXPEDIENTESCARRERA  WHERE C_USR_ID = ?  AND B_BORRADO = 'N'  ORDER BY F_REGISTRO_OFICIAL DESC";

			st = con.prepareStatement(sql);
			st.setLong(1, cUsrId.longValue());
			rs = st.executeQuery();
			datos = new OCAPExpedientecarreraOT();
			if (rs.next()) {
				datos.setCExpId(new Long(rs.getLong("C_EXP_ID")));
				datos.setFRespuestaInconf_solic(rs.getTimestamp("F_RESPUESTAINCONF_SOLIC"));
				datos.setCUsrId(Long.valueOf(rs.getString("C_USR_ID")));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setCGradoId(new Integer(rs.getInt("C_GRADO_ID")));
				datos.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
				SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_COMPLETA_HHMM);
				if (rs.getString("F_FIN_MC") != null)
					datos.setFFinMc(df.parse(rs.getString("F_FIN_MC")));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setFInicioCc(rs.getDate("F_INICIO_CC"));
				datos.setFInicioCa(rs.getTimestamp("F_INICIO_CA"));
				datos.setFAceptacionceis(rs.getTimestamp("F_ACEPTACIONCEIS"));
				datos.setFAceptacionCC(rs.getTimestamp("F_ACEPTACIONCC"));
				datos.setFAceptacSolic(rs.getTimestamp("F_ACEPTAC_SOLIC"));
				datos.setFInconf_solic(rs.getTimestamp("F_INCONF_SOLIC"));
				datos.setDObservNeg_solic(rs.getString("D_OBSERVNEG_SOLIC"));
				datos.setFFinEvalMc(rs.getDate("F_FIN_EVAL_MC"));
				datos.setFRegistroSolic(rs.getTimestamp("F_REGISTRO_SOLIC"));
				datos.setFInicioMc(rs.getTimestamp("F_INICIO_MC"));
				datos.setFInicioEvalMc(rs.getDate("F_INICIO_EVAL_MC"));
				datos.setCExpId(Long.valueOf(rs.getString("C_EXP_ID")));
				datos.setFFinCc(rs.getDate("F_FIN_CC"));
				datos.setFFinCa(rs.getDate("F_FIN_CA"));
				datos.setFNegacionSolic(rs.getTimestamp("F_NEGACION_SOLIC"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setNAniosAntiguedad(rs.getLong("N_ANIOSANTIGUEDAD"));
				datos.setNMesesAntiguedad(rs.getLong("N_MESES_ANTIGUEDAD"));
				datos.setNDiasAntiguedad(rs.getLong("N_DIAS_ANTIGUEDAD"));
				datos.setBInconf_antiguedad(rs.getString("B_INCONF_ANTIGUEDAD"));
				datos.setBInconf_plazaprop(rs.getString("B_INCONF_PLAZAPROP"));
				datos.setBPersonales(rs.getString("B_PERSONALES"));
				datos.setBOtrosCentros(rs.getString("B_OTROSCENTROS"));
				datos.setBPlazo(rs.getString("B_PLAZO"));
				datos.setCProcedimientoId(rs.getString("C_PROC_EVALUACION_ID"));
				datos.setCEstatutId(rs.getLong("C_PERSONAL_ID"));
				datos.setBLopdMc(rs.getString("B_LOPD_MC"));
				datos.setBLopdCd(rs.getString("B_LOPD_CD"));
				datos.setFRegDocEscaneados(rs.getDate("F_REG_DOCESCANEADOS"));
				datos.setFRegEvidenciasConf(rs.getDate("F_REG_EVIDENCIASCONF"));
				datos.setBOtroServicio(rs.getString("B_OTROSERVICIO"));
				datos.setCSitAdministrativaAuxId(rs.getString("C_SIT_ADMINISTRATIVA_ID"));
				datos.setDSitAdministrativaAuxOtros(rs.getString("A_OTRASITADMINISTRATIVA"));
				datos.setCJuridico(rs.getString("C_JURIDICO_ID"));
				datos.setCEstatutActualId(rs.getLong("C_PERSONALACTUAL_ID"));
				datos.setCProfesionalActual_id(rs.getLong("C_CATEGACTUAL_ID"));
				datos.setCEspecActual_id(rs.getLong("C_ESPECACTUAL_ID"));
				datos.setAPuesto(rs.getString("A_PUESTO"));
				datos.setFNegacionMC(rs.getTimestamp("F_NEGACION_MC"));
				datos.setBValidacioncc(rs.getString("B_VALIDACIONCC"));
				datos.setCItinerarioId(rs.getLong("C_ITINERARIO_ID"));
				datos.setAEspecificacionesEval(rs.getString("A_ESPECIFICACIONES_EVAL"));
				datos.setFInformeEval(rs.getTimestamp("F_INFORME_EVAL"));
				datos.setFInicioEvaluacion(rs.getTimestamp("F_INICIO_EVALUACION"));
				datos.setBConformidadCTE(rs.getString("B_CONFORMIDAD_CTE"));
				datos.setBNuevaRevision(rs.getString("B_NUEVA_REVISION"));
				datos.setADiscrepanciasCTE(rs.getString("A_DISCREPANCIAS_CTE"));
				datos.setAEspecificacionesCTE(rs.getString("A_ESPECIFICACIONES_CTE"));
				datos.setFInformeCTE(rs.getTimestamp("F_INFORME_CTE"));
				datos.setFReunionCTE(rs.getTimestamp("F_REUNION_CTE"));
				datos.setBDecisionCE(rs.getString("B_DECISION_CE"));
				datos.setADiscrepanciasCE(rs.getString("A_DISCREPANCIAS_CE"));
				datos.setAEspecificacionesCE(rs.getString("A_ESPECIFICACIONES_CE"));
				datos.setFInformeCE(rs.getTimestamp("F_INFORME_CE"));
				datos.setFReunionCE(rs.getTimestamp("F_REUNION_CE"));
				datos.setFInformeCC(rs.getTimestamp("F_INFORME_CC"));
				datos.setAEspecificacionesCC(rs.getString("A_ESPECIFICACIONES_CC"));
				datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
				datos.setCEspecId(rs.getLong("C_ESPEC_ID"));
				datos.setCModAnteriorId(rs.getLong("C_MOD_ANTERIOR_ID"));
				datos.setAServicio(rs.getString("A_SERVICIO"));
				datos.setCEstadoId(rs.getLong("C_ESTADO_ID"));
				datos.setFRespuestaInconf_MC(rs.getTimestamp("F_RESPUESTAINCONF_MC"));
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

	public ArrayList listadoOCAPExpedientecarrera() throws SQLException {
		return listadoOCAPExpedientecarrera(1, -1);
	}

	public ArrayList listadoOCAPExpedientecarrera(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList listado = null;
		try {
			String sql = "SELECT F_RESPUESTAINCONF_SOLIC, C_USR_ID, D_MOTIVO_INCONF_SOLIC, F_USUMODI, C_GRADO_ID, F_FIN_MC, B_BORRADO, F_INICIO_CC, F_INICIO_CA, F_ACEPTACIONCC, F_ACEPTACIONCEIS, F_ACEPTAC_SOLIC, D_MOTIVO_NEG, F_INCONF_SOLIC, D_OBSERVNEG_SOLIC, F_FIN_EVAL_MC, F_REGISTRO_SOLIC, F_INICIO_MC, F_INICIO_EVAL_MC, C_EXP_ID, F_FIN_CC, F_FIN_CA, F_NEGACION_SOLIC, F_USUALTA FROM OCAP_EXPEDIENTESCARRERA";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPExpedientecarreraOT datos = new OCAPExpedientecarreraOT();
				datos.setFRespuestaInconf_solic(rs.getDate("F_RESPUESTAINCONF_SOLIC"));
				datos.setDMotivoInconf_solic(rs.getString("D_MOTIVO_INCONF_SOLIC"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setCGradoId(new Integer(rs.getInt("C_GRADO_ID")));
				datos.setFFinMc(rs.getDate("F_FIN_MC"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setFInicioCc(rs.getDate("F_INICIO_CC"));
				datos.setFInicioCa(rs.getDate("F_INICIO_CA"));
				datos.setFAceptacionceis(rs.getDate("F_ACEPTACIONCEIS"));
				datos.setFAceptacionCC(rs.getDate("F_ACEPTACIONCC"));
				datos.setFAceptacSolic(rs.getDate("F_ACEPTAC_SOLIC"));
				datos.setDMotivoNeg(rs.getString("D_MOTIVO_NEG"));
				datos.setFInconf_solic(rs.getTimestamp("F_INCONF_SOLIC"));
				datos.setDObservNeg_solic(rs.getString("D_OBSERVNEG_SOLIC"));
				datos.setFFinEvalMc(rs.getDate("F_FIN_EVAL_MC"));
				datos.setFRegistroSolic(rs.getDate("F_REGISTRO_SOLIC"));
				datos.setFInicioMc(rs.getDate("F_INICIO_MC"));
				datos.setFInicioEvalMc(rs.getDate("F_INICIO_EVAL_MC"));
				datos.setFFinCc(rs.getDate("F_FIN_CC"));
				datos.setFFinCa(rs.getDate("F_FIN_CA"));
				datos.setFNegacionSolic(rs.getDate("F_NEGACION_SOLIC"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));

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

	public int cambiarAceptado(OCAPExpedientecarreraOT datos) throws SQLException, Exception {
		PreparedStatement st = null;
		int filas = 0;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			String sql = "UPDATE OCAP_EXPEDIENTESCARRERA SET F_ACEPTAC_SOLIC = null, B_INCONF_PLAZAPROP = null, B_INCONF_ANTIGUEDAD = null, B_PERSONALES = null, B_PLAZO = null, B_OTROSCENTROS = null, F_USUMODI = SYSDATE, C_USUMODI = ? WHERE C_EXP_ID = ?";

			st = con.prepareStatement(sql);
			st.setString(1, datos.getAModificadoPor());
			st.setLong(2, datos.getCExpId().longValue());
			filas = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
	}

	public int eliminarItinerarioAsignado(long cExpId, String usuModi) throws SQLException, Exception {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int filas = 0;
		try {
			String sql = "UPDATE OCAP_EXPEDIENTESCARRERA SET F_INICIO_CA = null, F_FIN_CA = null, B_VERIFICADATOS_FASEIII = null, C_ITINERARIO_ID = null, B_LOPD_CD = null, F_USUMODI = SYSDATE, C_USUMODI = ? WHERE C_EXP_ID = ?";

			st = con.prepareStatement(sql);
			st.setString(1, usuModi);
			st.setLong(2, cExpId);
			filas = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
	}

	public int buscarEvaluadosGRS() throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		int cuantos = 0;
		try {
			String sql = "SELECT count (*) as total ";
			sql = sql + "FROM ocap_expedientescarrera EXP ";
			sql = sql + "WHERE EXP.b_borrado = 'N'  ";
			sql = sql + "AND b_validacioncc = 'Y'  ";
			sql = sql + "and EXP.c_compfqs_id is not null  ";

			sql = sql + "and (exp.C_PROC_EVALUACION_ID = 4\tor exp.C_PROC_EVALUACION_ID = 5)  ";
			sql = sql + "AND c_convocatoria_id IN (  ";
			sql = sql + "SELECT c_convocatoria_id  ";
			sql = sql + "FROM ocap_convocatorias  ";
			sql = sql + "WHERE SYSDATE > f_publicacion  ";
			sql = sql + "AND SYSDATE < f_fincp  ";
			sql = sql + "AND b_borrado = 'N')  ";

			st = con.prepareStatement(sql);
			rs = st.executeQuery();

			if (rs.next())
				cuantos = rs.getInt("TOTAL");
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return cuantos;
	}

	public OCAPExpedientecarreraOT buscarExpediente_a(OCAPExpedientecarreraOT expedienteOT)
			throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		OCAPExpedientecarreraOT datos = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			String sql = " SELECT C_EXP_ID, TO_CHAR(F_REGISTRO_OFICIAL,'DD/MM/RRRR hh24:mi:ss') as F_REGISTRO_OFICIAL, C_SOLICITUD_ID FROM OCAP_EXPEDIENTESCARRERA  WHERE C_USR_ID = ? AND C_CONVOCATORIA_ID = ? ";

			st = con.prepareStatement(sql);
			st.setLong(1, expedienteOT.getCUsrId().longValue());
			st.setLong(2, expedienteOT.getCConvocatoriaId());

			rs = st.executeQuery();
			datos = new OCAPExpedientecarreraOT();
			if (rs.next()) {
				datos.setCExpId(Long.valueOf(rs.getLong("C_EXP_ID")));
				if (rs.getString("F_REGISTRO_OFICIAL") != null)
					datos.setFRegistroOficial(
							DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL, rs.getString("F_REGISTRO_OFICIAL")));
				datos.setCSolicitudId(rs.getLong("C_SOLICITUD_ID"));
			}
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception ex) {
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

	public ArrayList buscarOCAPExpedienteRechaz(long cUsrId) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		ArrayList datos = null;
		StringBuffer sql = new StringBuffer();
		try {
			sql.append("SELECT C_EXP_ID, exp.C_CONVOCATORIA_ID, F_REC_GRADO, F_PUBLICACION ")
					.append(" FROM OCAP_EXPEDIENTESCARRERA EXP, OCAP_CONVOCATORIAS CON ").append(" WHERE C_USR_ID = ")
					.append(cUsrId).append(" AND C_ESTADO_ID IN (").append(10).append(",").append(11).append(")")
					.append(" AND EXP.C_CONVOCATORIA_ID = CON.C_CONVOCATORIA_ID");

			st = con.prepareStatement(sql.toString());
			rs = st.executeQuery();
			datos = new ArrayList();
			while (rs.next()) {
				OCAPExpedientecarreraOT expediente = new OCAPExpedientecarreraOT();
				expediente.setCExpId(new Long(rs.getLong("C_EXP_ID")));
				expediente.setCConvocatoriaId(rs.getLong("C_CONVOCATORIA_ID"));
				expediente.setFRecGrado(rs.getDate("F_REC_GRADO"));
				expediente.setFPublicacion(rs.getDate("F_PUBLICACION"));

				datos.add(expediente);
			}
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return datos;
	}

	public int modificarEstadoExpCarrera(long cExpId, long estado,String usuModi, boolean modificarFechaRenuncia) throws SQLException, Exception {
		PreparedStatement st = null;
		int filas = 0;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			this.loggerBD.info("modificacionOCAPEstadoExpCarrera");
			String sql = "";
			if(modificarFechaRenuncia){
				sql = "UPDATE OCAP_EXPEDIENTESCARRERA SET C_ESTADO_ID = ?, F_RENUNCIA = SYSDATE, F_USUMODI=SYSDATE, C_USUMODI=?   WHERE C_EXP_ID = ?";
			}else{
				sql = "UPDATE OCAP_EXPEDIENTESCARRERA SET C_ESTADO_ID = ?, F_USUMODI=SYSDATE, C_USUMODI=? WHERE C_EXP_ID = ?";
			}

			st = con.prepareStatement(sql);
			int cont = 1;
			st.setLong(1, estado);
			st.setString(2, usuModi);
			st.setLong(3, cExpId);
			filas = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
	}

	public String buscarDNIUsuarioExpediente(long cExpId) throws SQLException, Exception {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String c_dni = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarDNIUsuarioExpediente Inicio");
			con = JCYLGestionTransacciones.getConnection();

			String sql = "SELECT C_EXP_ID, EXP.C_USR_ID, NVL(C_DNI, C_DNI_REAL)DNI, C_SOLICITUD_ID FROM OCAP_EXPEDIENTESCARRERA EXP, OCAP_USUARIOS USU WHERE EXP.C_USR_ID = USU.C_USR_ID AND EXP.C_EXP_ID = ? ";

			st = con.prepareStatement(sql);
			st.setLong(1, cExpId);
			rs = st.executeQuery();
			if (rs.next()) {
				c_dni = rs.getString("DNI");
			}

			OCAPConfigApp.logger.info(getClass().getName() + " buscarDNIUsuarioExpediente Fin");
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return c_dni;
	}

	public int modificacionFechaOCAPExpedientecarrera(OCAPExpedientecarreraOT datos, boolean flagExcluidaAceptada) throws SQLException, Exception {
		PreparedStatement st = null;
		int filas = 0;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			this.loggerBD.info("modificacionFechaOCAPExpedientecarrera");
			String sql = "UPDATE OCAP_EXPEDIENTESCARRERA SET ";

			if (datos.getCUsrId() != null) {
				sql = sql + " C_USR_ID = ?, ";
			}
			sql = sql + " F_INICIO_MC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";

			sql = sql + " F_FIN_MC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			
			sql = sql + " B_MERITOS_BLOQUEADOS = ?, ";

			sql = sql + " F_ACEPTACIONCEIS = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";

			sql = sql + " F_INICIO_EVAL_MC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			
			sql = sql + " F_FIN_EVAL_MC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";

			sql = sql + " F_INICIO_CA = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";

			sql = sql + " F_FIN_CA = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";

			sql = sql + " F_NEGACION_MC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";

			if(!datos.getEliminarEvaluacion()) {
				sql = sql + " F_INFORME_EVAL = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			}
			
			sql = sql + " F_INICIO_CC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			
			sql = sql + " F_FIN_CC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			
			sql = sql + " F_ACEPTACIONCC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			
			sql = sql + " F_INCONF_MC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			
			sql = sql + " F_RESPUESTAINCONF_MC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			
			sql = sql + " F_RENUNCIA = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			
			if (Utilidades.notNullAndNotEmpty(datos.getcItinerario_id())) {
				sql = sql + " B_VERIFICADATOS_FASEIII='Y', C_ITINERARIO_ID = ?, ";
			}else {
				sql = sql + " C_ITINERARIO_ID = ?, ";
			}
			
			if(datos.getEliminarEvaluacion()) {
				sql = sql + "b_conformidad_cte ="+  null+", ";

				sql = sql + "b_decision_ce = " + null+", ";

				sql = sql + "f_informe_cte ="+ null+", ";
				
				sql = sql + "f_informe_ce ="+ null+" , ";

				sql = sql + "f_informe_cc ="+  null+", ";
				   
				sql = sql + "b_reconocimiento_grado ="+ null+", ";
				       
				sql = sql + "f_informe_eval ="+null +", ";

				sql = sql + "a_especificaciones_eval ="+ null+", ";

				sql = sql + "f_reunion_ce ="+ null+", ";

				sql = sql + "f_reunion_cte ="+  null+", ";

				sql = sql + "f_inicio_evaluacion="+ null+", ";
				
				sql = sql + "c_estado_id = 9, ";


			}
			
			
			//OCAP-1530
			if (flagExcluidaAceptada) {
				sql = sql + " F_ACEPTAC_SOLIC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
				sql = sql + " F_NEGACION_SOLIC = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
			}
			
			sql = sql + " C_USUMODI = '" + datos.getAModificadoPor()+"' ";
			
			sql= sql + " ,F_USUMODI= SYSDATE, ";

			if (datos.getCEstadoId() != 0L) {
				sql = sql + " C_ESTADO_ID = ?, ";
				if (datos.getCEstadoId() == Constantes.ESTADO_NO_APORTA_MC) {
					sql = sql + " C_USU_NO_MERITOS_MC = ?, F_NO_MERITOS_MC = SYSDATE ";
				}else if (datos.getCEstadoId() == Constantes.ESTADO_NO_APORTA_MA) {
					sql = sql + " C_USU_NO_MERITOS_MA = ?, F_NO_MERITOS_MA = SYSDATE";
				}else if (datos.getCEstadoId() == Constantes.ESTADO_DESISTIDA) {
					sql = sql + " F_DESISTIDO = SYSDATE ";
				}
			}
			if (sql.substring(sql.length() - 2, sql.length()).equals(", ")) {
				sql = sql.substring(0, sql.length() - 2);
			}
			sql = sql + " WHERE C_EXP_ID = ?";

			st = con.prepareStatement(sql);
			int cont = 1;

			if (datos.getCUsrId() != null) {
				st.setLong(cont++, datos.getCUsrId().longValue());
			}

			if (datos.getFInicioMc() != null) {
				if (datos.getFInicioMc().getTime() == 0L)
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInicioMc()));
			} else
				st.setNull(cont++, 91);

			if (datos.getFFinMc() != null) {
 				if (datos.getFFinMc().getTime() == 0L)
					st.setNull(cont++, 91);
				else
					st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFFinMc()));
			} else
				st.setNull(cont++, 91);
			
			if (datos.getMeritosBloqueados() != null && !datos.getMeritosBloqueados().isEmpty() 
					&& datos.getMeritosBloqueados().equals(Constantes.SI_ESP)) {
				st.setString(cont++, Constantes.SI_ESP);
			} else
				st.setString(cont++, Constantes.NO);

			if (datos.getFAceptacionceis() != null) {
				if (datos.getFAceptacionceis().getTime() == 0L)
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFAceptacionceis()));
			} else
				st.setNull(cont++, 91);
			if (datos.getFInicioEvalMc() != null) {
					if (datos.getFInicioEvalMc().getTime() == 0L)
						st.setNull(cont++, 91);
					else
						st.setString(cont++,
								DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInicioEvalMc()));
			} else
					st.setNull(cont++, 91);
			
			if (datos.getFFinEvalMc() != null) {
				if (datos.getFFinEvalMc().getTime() == 0L)
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFFinEvalMc()));
			} else
				st.setNull(cont++, 91);

			if (datos.getFInicioCa() != null) {
				if (datos.getFInicioCa().getTime() == 0L)
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInicioCa()));
			} else
				st.setNull(cont++, 91);

			if (datos.getFFinCa() != null) {
				if (datos.getFFinCa().getTime() == 0L)
					st.setNull(cont++, 91);
				else
					st.setString(cont++, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFFinCa()));
			} else
				st.setNull(cont++, 91);

			if (datos.getFNegacionMC() != null) {
				if (datos.getFNegacionMC().getTime() == 0L)
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFNegacionMC()));
			} else
				st.setNull(cont++, 91);
			if(!datos.getEliminarEvaluacion()) {
				if (datos.getFInformeEval() != null) {
					if (datos.getFInformeEval().getTime() == 0L)
						st.setNull(cont++, 91);
					else
						st.setString(cont++,
								DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInformeEval()));
				} else
					st.setNull(cont++, 91);
			}
			
			if (datos.getFInicioCc() != null) {
				if (datos.getFInicioCc().getTime() == 0L)
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInicioCc()));
			} else
				st.setNull(cont++, 91);
			
			if (datos.getFFinCc() != null) {
				if (datos.getFFinCc().getTime() == 0L)
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFFinCc()));
			} else
				st.setNull(cont++, 91);
			
			if (datos.getFAceptacionCC() != null) {
				if (datos.getFAceptacionCC().getTime() == 0L)
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFAceptacionCC()));
			} else
				st.setNull(cont++, 91);
			
			if (datos.getFInconfMC() != null) {
				if (datos.getFInconfMC().getTime() == 0L)
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFInconfMC()));
			} else
				st.setNull(cont++, 91);
			
			if (datos.getFRespuestaInconf_MC() != null) {
				if (datos.getFRespuestaInconf_MC().getTime() == 0L)
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFRespuestaInconf_MC()));
			} else
				st.setNull(cont++, 91);
			
			if (datos.getFRenuncia() != null) {
				if (datos.getFRenuncia().getTime() == 0L)
					st.setNull(cont++, 91);
				else
					st.setString(cont++,
							DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFRenuncia()));
			} else
				st.setNull(cont++, 91);
			
			if(Utilidades.notNullAndNotEmpty(datos.getcItinerario_id())) {
					st.setLong(cont++, new Long(datos.getcItinerario_id()));
			} else
				st.setNull(cont++, java.sql.Types.DOUBLE);
			
			//OCAP-1530
			if (flagExcluidaAceptada) {
					st.setString(cont++,DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFAceptacSolic()));
					st.setNull(cont++, 91);
			}
			
			if (datos.getCEstadoId() != 0L) {
				st.setLong(cont++, datos.getCEstadoId());
				
				if (datos.getCEstadoId() == Constantes.ESTADO_NO_APORTA_MC) {
					st.setString(cont++, datos.getUsrNoMeritosCurriculares()!=null?datos.getUsrNoMeritosCurriculares():"");
				}else if (datos.getCEstadoId() == Constantes.ESTADO_NO_APORTA_MA) {
					st.setString(cont++, datos.getUsrNoMeritosAsistenciales()!=null?datos.getUsrNoMeritosAsistenciales():"");
				}
			}

			st.setLong(cont, datos.getCExpId().longValue());

			filas = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
	}

	public void asignarConvocatoriaYEvaluados(OCAPComponentesfqsOT datos, long cGerenciaId, String usuario)
			throws SQLException, Exception {
		PreparedStatement st = null;
		int filas = 0;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			String sql = "UPDATE OCAP_EXPEDIENTESCARRERA SET C_COMPFQS_ID =  " + datos.getCCompfqsId()
					+ " ,F_USUMODI = SYSDATE, C_USUMODI =  '" + usuario + "'" + " WHERE C_EXP_ID IN ( "
					+ " SELECT e.C_EXP_ID  FROM  OCAP_EXPEDIENTESCARRERA e, OCAP_USUARIOS u "
					+ " WHERE e.C_USR_ID = u.C_USR_ID " + " AND u.C_GERENCIA_ID = " + cGerenciaId
					+ " AND e.C_GRADO_ID = " + datos.getCGradoId() + " AND e.C_CONVOCATORIA_ID = "
					+ datos.getCConvocatoriaId() + " AND e.C_ITINERARIO_ID = " + datos.getCItinerarioId()
					+ " AND e.C_COMPFQS_ID IS NULL) ";

			st = con.prepareStatement(sql);

			filas = st.executeUpdate();
			OCAPConfigApp.logger.info("asignarConvocatoriaYEvaluados: se asigna evaluador a " + filas + " evaluados");
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		} finally {
			if (st != null)
				st.close();
		}
	}

	public int asignarEvaluador(long cCompfqsId, long[] listaPosiblesEvaluados, String usuario)
			throws SQLException, Exception {
		PreparedStatement st = null;
		int filas = 0;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE OCAP_EXPEDIENTESCARRERA SET C_COMPFQS_ID =  " + cCompfqsId + ", ")
					.append(" F_USUMODI = SYSDATE, C_USUMODI =  '" + usuario + "'").append(" WHERE C_EXP_ID IN ( ");

			for (int i = 0; i < listaPosiblesEvaluados.length; i++)
				sql.append("?,");
			sql.deleteCharAt(sql.length() - 1);

			sql.append(")");

			st = con.prepareStatement(sql.toString());

			int cont = 1;
			for (int i = 0; i < listaPosiblesEvaluados.length; i++) {
				st.setLong(cont++, listaPosiblesEvaluados[i]);
			}

			filas = st.executeUpdate();
			OCAPConfigApp.logger.info("asignarEvaluador: se asigna evaluador a " + filas + " evaluados");
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
	}

	public void finalizarFaseMeritos(String cExp_id, String usuMod) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE OCAP_EXPEDIENTESCARRERA SET B_MERITOS_BLOQUEADOS =  'S', C_USUMODI = ?, F_USUMODI=SYSDATE")
				.append(" WHERE C_EXP_ID = ?");
			
			st = con.prepareStatement(sql.toString());
			st.setString(1,usuMod);
			st.setLong(2, Long.parseLong(cExp_id));

			st.executeUpdate();
			OCAPConfigApp.logger.info("finalizar Meritos para el expediente: "+cExp_id);
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}		
	}
	
	public Integer buscarModAnteriorExpedienteCarrera(Long cExpId) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = null;
		Integer dato = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarModAnteriorExpedienteCarrera: Inicio");
			con = JCYLGestionTransacciones.getConnection();

			String sql = "SELECT C_MOD_ANTERIOR_ID FROM OCAP_EXPEDIENTESCARRERA WHERE C_EXP_ID = ? ";

			st = con.prepareStatement(sql);
			st.setLong(1, cExpId.longValue());
			OCAPConfigApp.logger
					.info(getClass().getName() + " buscarOCAPExpedientecarrera: C_EXP_ID: " + cExpId.longValue());

			rs = st.executeQuery();

			if (rs.next()) {
				dato = rs.getInt("C_MOD_ANTERIOR_ID");
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

		return dato;
	}
	
	public int modificacionFechaMA(OCAPExpedientecarreraOT datos ) throws SQLException, Exception {
		PreparedStatement st = null;
		int filas = 0;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			this.loggerBD.info("modificacionOCAPEstadoExpCarrera");
			String sql = "";
			sql = "update ocap_expedientescarrera " +  
					"set F_FIN_CA = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss')" + 
					"where b_borrado='N' AND " + 
					"      c_convocatoria_id=? and " + 
					"      c_usr_id in " + 
					"            (select c_usr_id " + 
					"             from ocap_usuarios " + 
					"             where c_dni_REAL = ?)";
			

			st = con.prepareStatement(sql);
			if(null!=datos.getfFin_ca_convocatoria())
				st.setString(1, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getfFin_ca_convocatoria()));
			else
				st.setString(1, DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, datos.getFFinCa()));
			st.setLong(2, datos.getCConvocatoriaId());
			st.setString(3, datos.getDniReal());
			filas = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
	}

	public int desbloquearMeritosCurriculares(OCAPExpedientecarreraOT expedienteOT) throws Exception {
		PreparedStatement st = null;
		int filas = 0;
		Connection con = JCYLGestionTransacciones.getConnection();
		try {
			this.loggerBD.info("desbloquearMeritosCurriculares");
			String sql = "";
			sql = "update ocap_expedientescarrera " +  
					"set B_MERITOS_BLOQUEADOS = 'N' " + 
					" where c_exp_id = ? ";			

			st = con.prepareStatement(sql);
			
			st.setLong(1, expedienteOT.getCExpId());
			filas = st.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
	}
}
