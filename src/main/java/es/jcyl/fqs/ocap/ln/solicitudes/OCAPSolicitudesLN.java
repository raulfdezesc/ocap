package es.jcyl.fqs.ocap.ln.solicitudes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;

import es.jcyl.fqs.ocap.actionforms.solicitudes.OCAPNuevaSolicitudForm;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
import es.jcyl.fqs.ocap.ln.centroTrabajo.OCAPCentroTrabajoLN;
import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
import es.jcyl.fqs.ocap.ln.creditos.OCAPCreditosLN;
import es.jcyl.fqs.ocap.ln.defMeritosCurriculares.OCAPDefMeritoscurricularesLN;
import es.jcyl.fqs.ocap.ln.especialidades.OCAPEspecialidadesLN;
import es.jcyl.fqs.ocap.ln.expedienteCarrera.OCAPExpedienteCarreraLN;
import es.jcyl.fqs.ocap.ln.gerencias.OCAPGerenciasLN;
import es.jcyl.fqs.ocap.ln.grado.OCAPGradoLN;
import es.jcyl.fqs.ocap.ln.localidades.OCAPLocalidadesLN;
import es.jcyl.fqs.ocap.ln.motExclusion.OCAPMotExclusionLN;
import es.jcyl.fqs.ocap.ln.otrosCentros.OCAPOtrosCentrosLN;
import es.jcyl.fqs.ocap.ln.personalEstatutario.OCAPPersEstatutarioLN;
import es.jcyl.fqs.ocap.ln.procedimiento.OCAPProcedimientoLN;
import es.jcyl.fqs.ocap.ln.provincias.OCAPProvinciasLN;
import es.jcyl.fqs.ocap.ln.revisiones.OCAPRevisionesLN;
import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
import es.jcyl.fqs.ocap.oad.categProfesionales.OCAPCategProfesionalesOAD;
import es.jcyl.fqs.ocap.oad.centroTrabajo.OCAPCentroTrabajoOAD;
import es.jcyl.fqs.ocap.oad.creditos.OCAPCreditosOAD;
import es.jcyl.fqs.ocap.oad.especialidades.OCAPEspecialidadesOAD;
import es.jcyl.fqs.ocap.oad.expedienteCarrera.OCAPExpedientecarreraOAD;
import es.jcyl.fqs.ocap.oad.expedientesexclusion.OCAPExpedientesExclusionOAD;
import es.jcyl.fqs.ocap.oad.gerencias.OCAPGerenciasOAD;
import es.jcyl.fqs.ocap.oad.grado.OCAPGradoOAD;
import es.jcyl.fqs.ocap.oad.motExclusion.OCAPMotExclusionOAD;
import es.jcyl.fqs.ocap.oad.provincias.OCAPProvinciasOAD;
import es.jcyl.fqs.ocap.oad.solicitudes.OCAPSolicitudesOAD;
import es.jcyl.fqs.ocap.oad.usuarios.OCAPUsuariosOAD;
import es.jcyl.fqs.ocap.ot.acceso.OCAPAccesoOT;
import es.jcyl.fqs.ocap.ot.categProfesionales.OCAPCategProfesionalesOT;
import es.jcyl.fqs.ocap.ot.centroTrabajo.OCAPCentroTrabajoOT;
import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
import es.jcyl.fqs.ocap.ot.creditos.OCAPCreditosOT;
import es.jcyl.fqs.ocap.ot.creditosCeis.OCAPCreditosCeisOT;
import es.jcyl.fqs.ocap.ot.defMeritosCurriculares.OCAPDefMeritoscurricularesOT;
import es.jcyl.fqs.ocap.ot.especialidades.OCAPEspecialidadesOT;
import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;
import es.jcyl.fqs.ocap.ot.expedienteMC.OCAPExpedientemcOT;
import es.jcyl.fqs.ocap.ot.expedientesexclusion.OCAPExpedientesExclusionOT;
import es.jcyl.fqs.ocap.ot.gerencias.OCAPGerenciasOT;
import es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT;
import es.jcyl.fqs.ocap.ot.meritosCurriculares.OCAPMeritoscurricularesOT;
import es.jcyl.fqs.ocap.ot.otrosCentros.OCAPOtrosCentrosOT;
import es.jcyl.fqs.ocap.ot.personalEstatutario.OCAPPersEstatutarioOT;
import es.jcyl.fqs.ocap.ot.procedimiento.OCAPProcedimientoOT;
import es.jcyl.fqs.ocap.ot.provincias.OCAPProvinciasOT;
import es.jcyl.fqs.ocap.ot.reports.OCAPAsistenteOT;
import es.jcyl.fqs.ocap.ot.reports.OCAPUsuarioOT;
import es.jcyl.fqs.ocap.ot.revisiones.OCAPRevisionesOT;
import es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.Ficheros;
import es.jcyl.fqs.ocap.util.UtilCorreo;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.fqs.ocap.util.reports.conceptos.ConceptoReport;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLUsuario;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class OCAPSolicitudesLN {
	private static final String PERFIL_PGP = "OCAP_PGP";
	private static final String USUARIO_PUBLICO = "PUBLICO";
	Logger logger;
	public Logger loggerBD;
	private JCYLUsuario jcylUsuario;

	private void $init$() {
		this.logger = OCAPConfigApp.logger;
		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public OCAPSolicitudesLN(JCYLUsuario jcylUsuario) {
		$init$();
		this.jcylUsuario = jcylUsuario;
	}

	public int insertar(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario) throws Exception {
		int idExpediente;
		int idUsr = 0;
		idExpediente = 0;
		try {
			OCAPConfigApp.logger
					.info((new StringBuilder()).append(getClass().getName()).append(" insertar: Inicio").toString());
			JCYLGestionTransacciones.open(false);
			OCAPUsuariosOAD usuarioOAD = OCAPUsuariosOAD.getInstance();
			OCAPExpedientecarreraOAD expedienteCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
			OCAPUsuariosOT usuariosOT = new OCAPUsuariosOT();
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();
			usuariosOT.setDApellido1(solicitudesOT.getDApellido1());
			usuariosOT.setDNombre(solicitudesOT.getDNombre());
			usuariosOT.setCDni(solicitudesOT.getCDni());
			usuariosOT.setCDniReal(solicitudesOT.getCDniReal());
			usuariosOT.setBSexo(solicitudesOT.getBSexo());
			if (solicitudesOT.getNTelefono1() != null && !solicitudesOT.getNTelefono1().equals(""))
				usuariosOT.setNTelefono1(Integer.valueOf(solicitudesOT.getNTelefono1()));
			if (solicitudesOT.getNTelefono2() != null && !solicitudesOT.getNTelefono2().equals(""))
				usuariosOT.setNTelefono2(Integer.valueOf(solicitudesOT.getNTelefono2()));
			usuariosOT.setDCorreoelectronico(solicitudesOT.getDCorreoelectronico());
			usuariosOT.setDDomicilio(solicitudesOT.getDDomicilio());
			usuariosOT.setDProvinciaUsu(solicitudesOT.getDProvinciaUsu());
			usuariosOT.setCProvinciaUsu_id(solicitudesOT.getCProvinciaUsu_id());
			usuariosOT.setDLocalidadUsu(solicitudesOT.getDLocalidadUsu());
			usuariosOT.setCPostalUsu(solicitudesOT.getCPostalUsu());
			usuariosOT.setCProfesionalId(Integer.valueOf(String.valueOf(solicitudesOT.getCProfesional_id())));
			usuariosOT.setCEspecId(Integer.valueOf(String.valueOf(solicitudesOT.getCEspec_id())));
			usuariosOT.setCGerenciaId(Integer.valueOf(String.valueOf(solicitudesOT.getCGerencia_id())));
			usuariosOT.setCCentrotrabajoId(Integer.valueOf(String.valueOf(solicitudesOT.getCCentrotrabajo_id())));
			usuariosOT.setFPlazapropiedad(solicitudesOT.getFPlazapropiedad());
			usuariosOT.setBBorrado(Constantes.NO);
			usuariosOT.setACreadoPor(solicitudesOT.getACreadoPor());
			OCAPUsuariosLN usuarioLN = new OCAPUsuariosLN(jcylUsuario);
			OCAPUsuariosOT usuariosOTBis = usuarioLN.buscarOCAPUsuariosPorNIF(usuariosOT.getCDniReal());

			if (usuariosOTBis.getCDni() != null && !usuariosOTBis.getCDni().equals(""))
				idUsr = Integer.parseInt(String.valueOf(usuariosOTBis.getCUsrId()));
			else
				idUsr = usuarioOAD.altaOCAPUsuarios(usuariosOT);
			expedienteCarreraOT.setBBorrado(Constantes.NO);
			expedienteCarreraOT.setCGradoId(Integer.valueOf(String.valueOf(solicitudesOT.getCGrado_id())));
			expedienteCarreraOT.setCUsrId(Long.valueOf(String.valueOf(idUsr)));
			expedienteCarreraOT.setFRegistroSolic(new Date());
			expedienteCarreraOT.setNAniosAntiguedad(solicitudesOT.getNAniosantiguedad());
			expedienteCarreraOT.setNMesesAntiguedad(solicitudesOT.getNMesesantiguedad());
			expedienteCarreraOT.setNDiasAntiguedad(solicitudesOT.getNDiasantiguedad());
			expedienteCarreraOT.setCConvocatoriaId(solicitudesOT.getCConvocatoriaId());
			expedienteCarreraOT.setACreadoPor(solicitudesOT.getACreadoPor());
			expedienteCarreraOT.setCProcedimientoId(solicitudesOT.getCProcedimientoId());
			expedienteCarreraOT.setCSitAdministrativaAuxId(solicitudesOT.getCSitAdministrativaAuxId());
			expedienteCarreraOT.setDSitAdministrativaAuxOtros(solicitudesOT.getDSitAdministrativaAuxOtros());
			expedienteCarreraOT.setCEstatutId(solicitudesOT.getCEstatutId());
			expedienteCarreraOT.setBOtroServicio(solicitudesOT.getBOtroServicio());
			expedienteCarreraOT.setADondeServicio(solicitudesOT.getADondeServicio());
			expedienteCarreraOT.setCJuridico(solicitudesOT.getCJuridico());
			expedienteCarreraOT.setFRegistroOficial(DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL,
					solicitudesOT.getFRegistro_oficial()));
			expedienteCarreraOT.setCEstatutActualId(solicitudesOT.getCEstatutActualId());
			expedienteCarreraOT.setCProfesionalActual_id(solicitudesOT.getCProfesionalActual_id());
			expedienteCarreraOT.setCEspecActual_id(solicitudesOT.getCEspecActual_id());
			OCAPExpedienteCarreraLN expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			long idExpAux = expCarreraLN.buscarCExpIdPorUsuarioConvocatoria(idUsr, solicitudesOT.getCConvocatoriaId());
			if (idExpAux != 0L)
				throw new Exception("ExisteSolicitudConvocatoria");
			idExpediente = expedienteCarreraOAD.altaOCAPExpedientecarrera(expedienteCarreraOT);
			ArrayList cadenasCentros = new ArrayList();
			if (solicitudesOT.getResumenCentros() != null && !"".equals(solicitudesOT.getResumenCentros()))
				cadenasCentros = Cadenas.obtenerArrayListTokens(solicitudesOT.getResumenCentros(), "#");
			OCAPOtrosCentrosOT otrosCentrosOT = null;
			ArrayList listaOtrosCentros = new ArrayList();
			for (int i = 0; i < cadenasCentros.size(); i++) {
				ArrayList campos = new ArrayList();
				String cadena = (String) cadenasCentros.get(i);
				for (StringTokenizer token = new StringTokenizer(cadena, "$"); token.hasMoreTokens(); campos
						.add(token.nextToken()))
					;
				otrosCentrosOT = new OCAPOtrosCentrosOT();
				otrosCentrosOT.setDNombre((String) campos.get(0));
				otrosCentrosOT.setAProvincia((String) campos.get(1));
				otrosCentrosOT.setACategoria((String) campos.get(2));
				otrosCentrosOT.setASituacion((String) campos.get(3));
				otrosCentrosOT.setAVinculo((String) campos.get(4));
				otrosCentrosOT.setFInicio(DateUtil.convertStringToDate((String) campos.get(5)));
				otrosCentrosOT.setFFin(DateUtil.convertStringToDate((String) campos.get(6)));
				listaOtrosCentros.add(otrosCentrosOT);
			}

			OCAPOtrosCentrosLN otrosCentrosLN = new OCAPOtrosCentrosLN(jcylUsuario);
			otrosCentrosLN.removeAllCentros(solicitudesOT.getCSolicitudId(), solicitudesOT.getCExp_id());
			otrosCentrosLN.altaOtrosCentros(idExpediente, listaOtrosCentros, jcylUsuario);
			JCYLGestionTransacciones.commit(true);
			OCAPConfigApp.logger
					.info((new StringBuilder()).append(getClass().getName()).append(" insertar: Fin").toString());
		} catch (Exception e) {
			OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName()).append(" insertar: ERROR: ")
					.append(e.getMessage()).toString());
			JCYLGestionTransacciones.rollback();
			throw e;
		} finally {
			JCYLGestionTransacciones.close(true);
		}

		return idExpediente;
	}

	public void insertarDenegacion(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario,
			ArrayList lCreditosValidados) throws Exception {
		OCAPCreditosCeisOT ccOT = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " insertarDenegacion: Inicio");

			OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();
			Utilidades utilidades = new Utilidades();

			expedienteCarreraOT.setCExpId(new Long(solicitudesOT.getCExp_id()));
			if ((solicitudesOT.getBInconf_plazaprop() != null)
					&& ((solicitudesOT.getBInconf_plazaprop().equals(Constantes.SI))
							|| (solicitudesOT.getBInconf_plazaprop().equals(Constantes.NO)))) {
				expedienteCarreraOT.setBInconf_plazaprop(solicitudesOT.getBInconf_plazaprop());
			}
			if ((solicitudesOT.getBInconf_antiguedad() != null)
					&& ((solicitudesOT.getBInconf_antiguedad().equals(Constantes.SI))
							|| (solicitudesOT.getBInconf_antiguedad().equals(Constantes.NO)))) {
				expedienteCarreraOT.setBInconf_antiguedad(solicitudesOT.getBInconf_antiguedad());
			}
			if ((solicitudesOT.getBPersonales() != null) && ((solicitudesOT.getBPersonales().equals(Constantes.SI))
					|| (solicitudesOT.getBPersonales().equals(Constantes.NO)))) {
				expedienteCarreraOT.setBPersonales(solicitudesOT.getBPersonales());
			}
			if ((solicitudesOT.getBOtrosCentros() != null) && ((solicitudesOT.getBOtrosCentros().equals(Constantes.SI))
					|| (solicitudesOT.getBOtrosCentros().equals(Constantes.NO)))) {
				expedienteCarreraOT.setBOtrosCentros(solicitudesOT.getBOtrosCentros());
			}
			if ((solicitudesOT.getBPlazo() != null) && ((solicitudesOT.getBPlazo().equals(Constantes.SI))
					|| (solicitudesOT.getBPlazo().equals(Constantes.NO)))) {
				expedienteCarreraOT.setBPlazo(solicitudesOT.getBPlazo());
			}
			if ((solicitudesOT.getDMotivo_neg_tmc() != null) && (!solicitudesOT.getDMotivo_neg_tmc().equals(""))) {
				expedienteCarreraOT.setDMotivoNegTmc(solicitudesOT.getDMotivo_neg_tmc());
			}
			if ((solicitudesOT.getDMotivo_neg_mc() != null) && (!solicitudesOT.getDMotivo_neg_mc().equals(""))) {
				expedienteCarreraOT.setDMotivoNegMC(solicitudesOT.getDMotivo_neg_mc());
			}
			if ((solicitudesOT.getDObserv_neg_solic() != null) && (!solicitudesOT.getDObserv_neg_solic().equals(""))) {
				expedienteCarreraOT.setDObservNeg_solic(solicitudesOT.getDObserv_neg_solic());
			}
			if ((solicitudesOT.getDObserv_neg_tmc() != null) && (!solicitudesOT.getDObserv_neg_tmc().equals(""))) {
				expedienteCarreraOT.setDObservNegTmc(solicitudesOT.getDObserv_neg_tmc());
			}
			if ((solicitudesOT.getDObserv_neg_mc() != null) && (!solicitudesOT.getDObserv_neg_mc().equals(""))) {
				expedienteCarreraOT.setDObservNegMC(solicitudesOT.getDObserv_neg_mc());
			}
			if (solicitudesOT.getFNegacion_solic() != null) {
				expedienteCarreraOT.setFNegacionSolic(solicitudesOT.getFNegacion_solic());
			}
			if (solicitudesOT.getFNegacion_tmc() != null) {
				expedienteCarreraOT.setFNegacionTmc(solicitudesOT.getFNegacion_tmc());
			}
			if (solicitudesOT.getFSubsanacion() != null) {
				expedienteCarreraOT.setFSubsanacion(solicitudesOT.getFSubsanacion());
			}
			if (solicitudesOT.getFNegacion_mc() != null) {
				expedienteCarreraOT.setFNegacionMC(solicitudesOT.getFNegacion_mc());
			}
			if (solicitudesOT.getDRespuestaInconfSolic() != null) {
				expedienteCarreraOT.setDRespuestaInconfSolic(solicitudesOT.getDRespuestaInconfSolic());
			}
			if (solicitudesOT.getDRespuestaInconfTmc() != null) {
				expedienteCarreraOT.setDRespuestaInconfTmc(solicitudesOT.getDRespuestaInconfTmc());
			}
			if (solicitudesOT.getDRespuestaInconfMC() != null) {
				expedienteCarreraOT.setDRespuestaInconfMC(solicitudesOT.getDRespuestaInconfMC());
			}
			if (solicitudesOT.getDRespuestaInconfCA() != null) {
				expedienteCarreraOT.setDRespuestaInconfCA(solicitudesOT.getDRespuestaInconfCA());
			}
			if (solicitudesOT.getFRespuesta_inconf_solic() != null) {
				expedienteCarreraOT.setFRespuestaInconf_solic(solicitudesOT.getFRespuesta_inconf_solic());
			}
			if (solicitudesOT.getFRespuestaInconf_Tmc() != null) {
				expedienteCarreraOT.setFRespuestaInconf_Tmc(solicitudesOT.getFRespuestaInconf_Tmc());
			}
			if (solicitudesOT.getFRespuestaInconf_MC() != null) {
				expedienteCarreraOT.setFRespuestaInconf_MC(solicitudesOT.getFRespuestaInconf_MC());
			}
			if ((solicitudesOT.getDMotivoNegRespuesta_Tmc() != null)
					&& (!solicitudesOT.getDMotivoNegRespuesta_Tmc().equals(""))) {
				expedienteCarreraOT.setDMotivoNegRespuesta_Tmc(solicitudesOT.getDMotivoNegRespuesta_Tmc());
			}
			if ((solicitudesOT.getDMotivoNegRespuesta_MC() != null)
					&& (!solicitudesOT.getDMotivoNegRespuesta_MC().equals(""))) {
				expedienteCarreraOT.setDMotivoNegRespuesta_MC(solicitudesOT.getDMotivoNegRespuesta_MC());
			}
			if ((solicitudesOT.getDMotivoNegRespuesta_CA() != null)
					&& (!solicitudesOT.getDMotivoNegRespuesta_CA().equals(""))) {
				expedienteCarreraOT.setDMotivoNegRespuesta_CA(solicitudesOT.getDMotivoNegRespuesta_CA());
			}
			expedienteCarreraOT
					.setFRegistroOficial_Mc(DateUtil.convertStringToDate(solicitudesOT.getFRegistro_oficialMC()));
			expedienteCarreraOT.setBValidacioncc(solicitudesOT.getBValidacionCC());

			expedienteCarreraOT.setCEstadoId(Constantes.ESTADO_EXCLUIDA_MC);
			expedienteCarreraOT.setAModificadoPor(this.jcylUsuario.getUsuario().getC_usr_id());
			expedienteCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);

			OCAPConfigApp.logger.info(getClass().getName() + " insertarDenegacion: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " insertarDenegacion: ERROR: " + e.getMessage());
			throw e;
		}
	}

	public void insertarInconformidad(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario) throws Exception {
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " insertarInconformidad: Inicio");

			OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();
			expedienteCarreraOT.setCExpId(new Long(solicitudesOT.getCExp_id()));
			if ((solicitudesOT.getDMotivo_inconf_solic() != null)
					&& (!solicitudesOT.getDMotivo_inconf_solic().equals(""))) {
				expedienteCarreraOT.setDMotivoInconf_solic(solicitudesOT.getDMotivo_inconf_solic());
			}
			if ((solicitudesOT.getDMotivo_inconf_tmc() != null)
					&& (!solicitudesOT.getDMotivo_inconf_tmc().equals(""))) {
				expedienteCarreraOT.setDMotivoInconfTmc(solicitudesOT.getDMotivo_inconf_tmc());
			}
			if ((solicitudesOT.getDMotivo_inconf_mc() != null) && (!solicitudesOT.getDMotivo_inconf_mc().equals(""))) {
				expedienteCarreraOT.setDMotivoInconfMC(solicitudesOT.getDMotivo_inconf_mc());
			}
			if (solicitudesOT.getFInconf_solic() != null) {
				expedienteCarreraOT.setFInconf_solic(solicitudesOT.getFInconf_solic());
			}
			if (solicitudesOT.getFInconf_tmc() != null) {
				expedienteCarreraOT.setFInconfTmc(solicitudesOT.getFInconf_tmc());
			}
			if (solicitudesOT.getFInconf_mc() != null) {
				expedienteCarreraOT.setFInconfMC(solicitudesOT.getFInconf_mc());
			}
			if (solicitudesOT.getCEstadoId() != 0L) {
				expedienteCarreraOT.setCEstadoId(solicitudesOT.getCEstadoId());
			}
			expedienteCarreraOT.setAModificadoPor(this.jcylUsuario.getUsuario().getC_usr_id());
			expedienteCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);

			OCAPConfigApp.logger.info(getClass().getName() + " insertarInconformidad: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " insertarInconformidad: ERROR: " + e.getMessage());
			throw e;
		}
	}

	public boolean reabrirCA(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario) throws Exception {
		OCAPConvocatoriasLN convoLN = null;
		OCAPConvocatoriasOT convoOT = null;
		OCAPExpedienteCarreraLN expedienteCarreraLN = null;
		OCAPExpedientecarreraOT expedienteCarreraOT = null;
		boolean permisoReabrir = false;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " reabrirCA: Inicio");

			expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			expedienteCarreraOT = new OCAPExpedientecarreraOT();
			Utilidades utilidades = new Utilidades();

			expedienteCarreraOT.setCExpId(new Long(solicitudesOT.getCExp_id()));

			convoLN = new OCAPConvocatoriasLN(jcylUsuario);
			convoOT = convoLN.buscarOCAPConvocatorias(solicitudesOT.getCConvocatoriaId());

			Date fFinCaConvo = DateUtil.addDias(DateUtil.convertStringToDate(convoOT.getFInicioCA()),
					(int) convoOT.getNDiasAutoCa());
			if (!new Date().after(fFinCaConvo)) {
				permisoReabrir = true;
				expedienteCarreraOT.setFFinCa(fFinCaConvo);

				Date fechaABorrar = new Date();
				fechaABorrar.setTime(0L);
				expedienteCarreraOT.setFRegDocEscaneados(fechaABorrar);
				expedienteCarreraOT.setAModificadoPor(this.jcylUsuario.getUsuario().getC_usr_id());
				expedienteCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " reabrirCA: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " reabrirCA: ERROR: " + e.getMessage());
			throw e;
		}
		return permisoReabrir;
	}

	public void validadoCEIS(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario) throws Exception {
		OCAPConvocatoriasLN convoLN = null;
		OCAPConvocatoriasOT convoOT = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " validadoCEIS: Inicio");

			OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();

			expedienteCarreraOT.setCExpId(new Long(solicitudesOT.getCExp_id()));
			expedienteCarreraOT.setAModificadoPor(this.jcylUsuario.getUsuario().getC_usr_id());
			expedienteCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);

			OCAPConfigApp.logger.info(getClass().getName() + " validadoCEIS: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " validadoCEIS: ERROR: " + e.getMessage());
			throw e;
		}
	}

	public OCAPSolicitudesOT datosSolicitud(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario)
			throws SQLException, Exception {
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " datosSolicitud: Inicio");

			OCAPUsuariosOAD usuarioOAD = OCAPUsuariosOAD.getInstance();
			OCAPExpedientecarreraOAD expedienteCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
			OCAPUsuariosOT usuariosOT = null;
			OCAPExpedientecarreraOT expedienteCarreraOT = null;

			expedienteCarreraOT = expedienteCarreraOAD
					.buscarOCAPExpedientecarrera(Long.valueOf(solicitudesOT.getCExp_id()));
			if ((expedienteCarreraOT == null) || (expedienteCarreraOT.getCUsrId() == null)) {
				return null;
			}
			usuariosOT = usuarioOAD.buscarOCAPUsuarios(expedienteCarreraOT.getCUsrId());

			solicitudesOT.setMeritosBloqueados(expedienteCarreraOT.getMeritosBloqueados());
			
			OCAPPersEstatutarioLN personalLN = new OCAPPersEstatutarioLN(jcylUsuario);
			OCAPPersEstatutarioOT personalOT = null;
			personalOT = personalLN.buscarOCAPPersEstatutario(expedienteCarreraOT.getCEstatutId());
			OCAPPersEstatutarioOT personalActualOT = null;
			personalActualOT = personalLN.buscarOCAPPersEstatutario(expedienteCarreraOT.getCEstatutActualId());

			OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
			OCAPProcedimientoOT procOT = null;
			if ((solicitudesOT.getCProcedimientoId() != null) && (!solicitudesOT.getCProcedimientoId().equals(""))) {
				procOT = procLN.buscarOCAPProcedimiento(Long.parseLong(expedienteCarreraOT.getCProcedimientoId()));
			} else {
				procOT = new OCAPProcedimientoOT();
			}
			solicitudesOT.setCProcedimientoId(expedienteCarreraOT.getCProcedimientoId());

			solicitudesOT.setCSitAdministrativaAuxId(expedienteCarreraOT.getCSitAdministrativaAuxId());
			solicitudesOT.setDSitAdministrativaOtros(expedienteCarreraOT.getDSitAdministrativaAuxOtros());

			OCAPCategProfesionalesOAD categOAD = OCAPCategProfesionalesOAD.getInstance();
			OCAPCategProfesionalesOT categOT = null;

			categOT = categOAD.buscarOCAPCategProfesionales(expedienteCarreraOT.getCProfesionalId());
			OCAPCategProfesionalesOT categActualOT = null;
			categActualOT = categOAD.buscarOCAPCategProfesionales(expedienteCarreraOT.getCProfesionalActual_id());

			OCAPEspecialidadesOAD especialidadesOAD = OCAPEspecialidadesOAD.getInstance();
			OCAPEspecialidadesOT especialidadesOT = null;

			especialidadesOT = especialidadesOAD.buscarOCAPEspecialidades(expedienteCarreraOT.getCEspecId());
			OCAPEspecialidadesOT especialidadActualOT = null;
			especialidadActualOT = especialidadesOAD.buscarOCAPEspecialidades(expedienteCarreraOT.getCEspecActual_id());

			OCAPGerenciasOAD gerenciasOAD = OCAPGerenciasOAD.getInstance();
			OCAPGerenciasOT gerenciasOT = null;
			gerenciasOT = gerenciasOAD.buscarOCAPGerencias(Long.parseLong(String.valueOf(usuariosOT.getCGerenciaId())));

			OCAPProvinciasOAD provinciasOAD = OCAPProvinciasOAD.getInstance();

			OCAPProvinciasOT provinciasOT2 = provinciasOAD.buscarProvincia(usuariosOT.getCProvinciaUsu_id());

			OCAPCentroTrabajoOAD centroTrabajoOAD = OCAPCentroTrabajoOAD.getInstance();
			OCAPCentroTrabajoOT centroTrabajoOT = null;
			centroTrabajoOT = centroTrabajoOAD.buscarOCAPCentroTrabajo(usuariosOT.getCCentrotrabajoId().longValue());

			OCAPGradoOAD gradoOAD = OCAPGradoOAD.getInstance();
			OCAPGradoOT gradoOT = null;
			gradoOT = gradoOAD.buscarOCAPGrado(expedienteCarreraOT.getCGradoId().longValue());

			solicitudesOT.setCUsr_id(expedienteCarreraOT.getCUsrId().longValue());
			solicitudesOT.setDApellido1(usuariosOT.getDApellido1());

			solicitudesOT.setDNombre(usuariosOT.getDNombre());
			solicitudesOT.setCDni(usuariosOT.getCDni());
			solicitudesOT.setCDniReal(usuariosOT.getCDniReal());
			solicitudesOT.setDCorreoelectronico(usuariosOT.getDCorreoelectronico());
			solicitudesOT.setNTelefono1(String.valueOf(usuariosOT.getNTelefono1().intValue()));
			solicitudesOT.setNTelefono2(String.valueOf(usuariosOT.getNTelefono2().intValue()));
			solicitudesOT.setBSexo(usuariosOT.getBSexo());

			solicitudesOT.setDDomicilio(usuariosOT.getDDomicilio());
			solicitudesOT.setCProvinciaUsu_id(usuariosOT.getCProvinciaUsu_id());
			solicitudesOT.setCProvincia_id(usuariosOT.getCProvinciaUsu_id());
			solicitudesOT.setDProvincia(provinciasOT2.getDProvincia());
			solicitudesOT.setDProvinciaUsu(provinciasOT2.getDProvincia());
			solicitudesOT.setDLocalidad(usuariosOT.getDLocalidadUsu());
			solicitudesOT.setDLocalidadUsu(usuariosOT.getDLocalidadUsu());
			solicitudesOT.setCPostalUsu(usuariosOT.getCPostalUsu());

			solicitudesOT.setCSolicitudId(expedienteCarreraOT.getCSolicitudId());
			solicitudesOT.setDProcedimiento(procOT.getDNombre());

			solicitudesOT.setCEstatutActualId(expedienteCarreraOT.getCEstatutActualId());
			solicitudesOT.setDEstatutActual_nombre(personalActualOT.getDNombre());
			solicitudesOT.setCProfesionalActual_id(expedienteCarreraOT.getCProfesionalActual_id());
			solicitudesOT.setDProfesionalActual_nombre(categActualOT.getDNombre());
			solicitudesOT.setCEstatutarioId(categOT.getCEstatutId());
			solicitudesOT.setCEspecActual_id(expedienteCarreraOT.getCEstatutActualId());
			solicitudesOT.setDEspecActual_nombre(especialidadActualOT.getDNombre());
			solicitudesOT.setDEstatut_nombre(personalOT.getDNombre());
			solicitudesOT.setCEstatutId(expedienteCarreraOT.getCEstatutId());
			solicitudesOT.setDProfesional_nombre(categOT.getDNombre());

			solicitudesOT.setCProfesional_id(expedienteCarreraOT.getCProfesionalId());
			solicitudesOT.setDEspec_nombre(especialidadesOT.getDNombre());

			solicitudesOT.setCEspec_id(expedienteCarreraOT.getCEspecId());

			solicitudesOT.setCProvinciaCarrera_id(gerenciasOT.getCProvinciaId());

			solicitudesOT.setDGerencia_nombre(gerenciasOT.getDNombre());
			solicitudesOT.setCGerencia_id(gerenciasOT.getCGerenciaId());
			solicitudesOT.setDDirector(gerenciasOT.getDDirector());
			solicitudesOT.setDGerente(gerenciasOT.getDGerente());
			solicitudesOT.setDCentrotrabajo_nombre(centroTrabajoOT.getDNombre());
			solicitudesOT.setCCentrotrabajo_id(centroTrabajoOT.getCCentrotrabajoId());
			solicitudesOT.setACiudad(centroTrabajoOT.getACiudad());
			solicitudesOT.setCSolicitudId(expedienteCarreraOT.getCSolicitudId());
			solicitudesOT.setAServicio(expedienteCarreraOT.getAServicio());
			solicitudesOT.setAPuesto(expedienteCarreraOT.getAPuesto());

			solicitudesOT.setBVerificaDatosFaseIII(expedienteCarreraOT.getBVerificaDatosFaseIII());
			solicitudesOT.setBReconocimientoGrado(expedienteCarreraOT.getBReconocimientoGrado());
			if (expedienteCarreraOT.getFNegacionSolic() != null) {
				solicitudesOT.setFNegacion_solic(expedienteCarreraOT.getFNegacionSolic());
			}
			if (expedienteCarreraOT.getFAceptacSolic() != null) {
				solicitudesOT.setFAceptac_solic(expedienteCarreraOT.getFAceptacSolic());
			}
			if (expedienteCarreraOT.getFSubsanacion() != null) {
				solicitudesOT.setFSubsanacion(expedienteCarreraOT.getFSubsanacion());
			}
			if (expedienteCarreraOT.getFInconf_solic() != null) {
				solicitudesOT.setFInconf_solic(expedienteCarreraOT.getFInconf_solic());
			}
			if (expedienteCarreraOT.getFInicioMc() != null) {
				solicitudesOT.setFInicio_mc(expedienteCarreraOT.getFInicioMc());
			}
			if (expedienteCarreraOT.getFFinMc() != null) {
				solicitudesOT.setFFin_mc(expedienteCarreraOT.getFFinMc());
			}
			if (expedienteCarreraOT.getFNegacionTmc() != null) {
				solicitudesOT.setFNegacion_tmc(expedienteCarreraOT.getFNegacionTmc());
			}
			if (expedienteCarreraOT.getFInconfTmc() != null) {
				solicitudesOT.setFInconf_tmc(expedienteCarreraOT.getFInconfTmc());
			}
			if (expedienteCarreraOT.getFInicioEvalMc() != null) {
				solicitudesOT.setFInicio_eval_mc(expedienteCarreraOT.getFInicioEvalMc());
			}
			if (expedienteCarreraOT.getFNegacionMC() != null) {
				solicitudesOT.setFNegacion_mc(expedienteCarreraOT.getFNegacionMC());
			}
			if (expedienteCarreraOT.getFInconfMC() != null) {
				solicitudesOT.setFInconf_mc(expedienteCarreraOT.getFInconfMC());
			}
			if (expedienteCarreraOT.getFInicioCa() != null) {
				solicitudesOT.setFInicio_ca(expedienteCarreraOT.getFInicioCa());
			}
			if (expedienteCarreraOT.getFAceptacionCC() != null) {
				solicitudesOT.setFAceptacionCC(expedienteCarreraOT.getFAceptacionCC());
			}
			if (expedienteCarreraOT.getFAceptacionceis() != null) {
				solicitudesOT.setFAceptacionceis(expedienteCarreraOT.getFAceptacionceis());
			}
			if (expedienteCarreraOT.getFInicioCc() != null) {
				solicitudesOT.setFInicio_cc(expedienteCarreraOT.getFInicioCc());
			}
			if (expedienteCarreraOT.getFRespuestaInconf_solic() != null) {
				solicitudesOT.setFRespuesta_inconf_solic(expedienteCarreraOT.getFRespuestaInconf_solic());
			}
			if (expedienteCarreraOT.getFRespuestaInconf_MC() != null) {
				solicitudesOT.setFRespuestaInconf_MC(expedienteCarreraOT.getFRespuestaInconf_MC());
			}
			if (expedienteCarreraOT.getFInicioEvalMc() != null) {
				solicitudesOT.setFInicio_eval_mc(expedienteCarreraOT.getFInicioEvalMc());
			}
			if (expedienteCarreraOT.getFFinEvalMc() != null) {
				solicitudesOT.setFFin_eval_mc(expedienteCarreraOT.getFFinEvalMc());
			}
			if (expedienteCarreraOT.getFFinCc() != null) {
				solicitudesOT.setFFin_cc(expedienteCarreraOT.getFFinCc());
			}
			if (expedienteCarreraOT.getFFinCa() != null) {
				solicitudesOT.setFFin_ca(expedienteCarreraOT.getFFinCa());
			}
			if (expedienteCarreraOT.getFRegistroOficial() != null) {
				solicitudesOT.setFRegistro_oficial(DateUtil.getDateTime(Constantes.FECHA_COMPLETA_DATEUTIL,
						expedienteCarreraOT.getFRegistroOficial()));
			}
			if (expedienteCarreraOT.getFRegistroOficial_Mc() != null) {
				solicitudesOT.setFRegistro_oficialMC(DateUtil.getDateTime(Constantes.FECHA_COMPLETA_DATEUTIL,
						expedienteCarreraOT.getFRegistroOficial_Mc()));
			}
			if (expedienteCarreraOT.getFRegDocEscaneados() != null) {
				solicitudesOT.setFRegDocEscaneados(expedienteCarreraOT.getFRegDocEscaneados());
			}
			if (expedienteCarreraOT.getFRegEvidenciasConf() != null) {
				solicitudesOT.setFRegEvidenciasConf(expedienteCarreraOT.getFRegEvidenciasConf());
			}
			if (expedienteCarreraOT.getFAclaraciones() != null) {
				solicitudesOT.setFAclaraciones(expedienteCarreraOT.getFAclaraciones());
			}
			if (expedienteCarreraOT.getFMotivadoNeg() != null) {
				solicitudesOT.setFMotivadoNeg(expedienteCarreraOT.getFMotivadoNeg());
			}
			if (expedienteCarreraOT.getFMotivadoAcep() != null) {
				solicitudesOT.setFMotivadoAcep(expedienteCarreraOT.getFMotivadoAcep());
			}
			if (expedienteCarreraOT.getFDesistidoMC() != null) {
				solicitudesOT.setFDesistidoMC(expedienteCarreraOT.getFDesistidoMC());
			}
			if (expedienteCarreraOT.getFInformeEval() != null) {
				solicitudesOT.setFInformeEval(expedienteCarreraOT.getFInformeEval());
			}
			if (expedienteCarreraOT.getFInformeCE() != null) {
				solicitudesOT.setFInformeCE(expedienteCarreraOT.getFInformeCE());
			}
			if (expedienteCarreraOT.getFRenuncia() != null) {
				solicitudesOT.setFRenuncia(DateUtil.convertDateToString(expedienteCarreraOT.getFRenuncia()));
			}
			solicitudesOT.setCItinerario_id(expedienteCarreraOT.getCItinerarioId());
			solicitudesOT.setNAniosantiguedad(expedienteCarreraOT.getNAniosAntiguedad());
			solicitudesOT.setNMesesantiguedad(expedienteCarreraOT.getNMesesAntiguedad());
			solicitudesOT.setNDiasantiguedad(expedienteCarreraOT.getNDiasAntiguedad());

			solicitudesOT.setDGrado_des(gradoOT.getDDescripcion());
			solicitudesOT.setCGrado_id(gradoOT.getCGradoId());
			solicitudesOT.setCConvocatoriaId(expedienteCarreraOT.getCConvocatoriaId());
			solicitudesOT.setCModAnterior_id(expedienteCarreraOT.getCModAnteriorId());

			OCAPConvocatoriasLN convocatoriaLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT convocatoriaOT = null;
			convocatoriaOT = convocatoriaLN.buscarOCAPConvocatorias(solicitudesOT.getCConvocatoriaId());
			solicitudesOT.setDConvocatoria_nombre(convocatoriaOT.getDNombre());
			solicitudesOT.setFConvocatoria(convocatoriaOT.getFResolucion());
			solicitudesOT.setPlazoDiasCC(convocatoriaOT.getNDiasValCc());
			solicitudesOT.setDAnioConvocatoria(convocatoriaOT.getDAnioConvocatoria());
			solicitudesOT.setBInconf_antiguedad(expedienteCarreraOT.getBInconf_antiguedad());
			solicitudesOT.setBInconf_plazaprop(expedienteCarreraOT.getBInconf_plazaprop());
			solicitudesOT.setBPersonales(expedienteCarreraOT.getBPersonales());
			solicitudesOT.setBOtrosCentros(expedienteCarreraOT.getBOtrosCentros());
			solicitudesOT.setBPlazo(expedienteCarreraOT.getBPlazo());
			if (expedienteCarreraOT.getFRegistroSolic() != null) {
				solicitudesOT.setRSolicitud_acceso(Constantes.SI);
			}
			solicitudesOT.setDMotivo_inconf_solic(expedienteCarreraOT.getDMotivoInconf_solic() == null ? ""
					: expedienteCarreraOT.getDMotivoInconf_solic());
			solicitudesOT.setDMotivo_inconf_mc(
					expedienteCarreraOT.getDMotivoInconfMC() == null ? "" : expedienteCarreraOT.getDMotivoInconfMC());
			solicitudesOT.setDMotivo_neg_mc(
					expedienteCarreraOT.getDMotivoNegMC() == null ? "" : expedienteCarreraOT.getDMotivoNegMC());
			solicitudesOT.setDObserv_neg_mc(
					expedienteCarreraOT.getDObservNegMC() == null ? "" : expedienteCarreraOT.getDObservNegMC());

			solicitudesOT.setFRegistro_solic(
					DateUtil.getDateTime(Constantes.FECHA_COMPLETA_DATEUTIL, expedienteCarreraOT.getFRegistroSolic()));

			solicitudesOT.setCCompfqs_id(expedienteCarreraOT.getCCompfqs_id());

			solicitudesOT.setCJuridicoId(expedienteCarreraOT.getCJuridico());
			solicitudesOT.setDRegimenJuridicoOtros(expedienteCarreraOT.getDRegimenJuridicoOtros());
			solicitudesOT.setCSitAdministrativaAuxId(expedienteCarreraOT.getCSitAdministrativaAuxId());
			solicitudesOT.setDSitAdministrativaAuxOtros(expedienteCarreraOT.getDSitAdministrativaAuxOtros());
			solicitudesOT.setBValidacionCC(expedienteCarreraOT.getBValidacioncc());
			solicitudesOT.setCEstado_id(expedienteCarreraOT.getCEstadoId());
			if (expedienteCarreraOT.getFAceptacSolic() != null) {
				solicitudesOT.setRContinuidad_proceso(Constantes.SI);
			} else if ((expedienteCarreraOT.getFNegacionSolic() != null)
					|| (expedienteCarreraOT.getFRespuestaInconf_solic() != null)) {
				solicitudesOT.setRContinuidad_proceso(Constantes.NO);
			}
			if (expedienteCarreraOT.getFInconf_solic() != null) {
				solicitudesOT.setRFase_inicio(Constantes.SI);
			} else {
				solicitudesOT.setRFase_inicio(Constantes.NO);
			}
			if (expedienteCarreraOT.getFInicioEvalMc() != null) {
				solicitudesOT.setRContinuidad_proceso_tramitacion(Constantes.SI);
			}
			solicitudesOT.setRFase_tramitacion(Constantes.NO);
			if (expedienteCarreraOT.getFAceptacionceis() != null) {
				solicitudesOT.setRContinuidad_proceso_validacion(Constantes.SI);
			} else if ((expedienteCarreraOT.getFNegacionMC() != null)
					|| (expedienteCarreraOT.getFRespuestaInconf_MC() != null)) {
				solicitudesOT.setRContinuidad_proceso_validacion(Constantes.NO);
			}
			if (expedienteCarreraOT.getFInconfMC() != null) {
				solicitudesOT.setRFase_validacion(Constantes.SI);
			} else {
				solicitudesOT.setRFase_validacion(Constantes.NO);
			}
			if (expedienteCarreraOT.getFInicioCc() != null) {
				solicitudesOT.setRContinuidad_proceso_ca(Constantes.SI);
			}
			solicitudesOT.setRFase_ca(Constantes.NO);

			OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);
			if ((solicitudesOT.getCDni() != null) && (solicitudesOT.getCDniReal() != null)) {
				usuariosOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(), solicitudesOT.getCExp_id(),
						jcylUsuario);

				OCAPCreditosLN creditosLN = new OCAPCreditosLN(jcylUsuario);
				ArrayList listaCreditos = creditosLN.buscarOCAPCreditosPorCategProfesional(usuariosOT,
						Integer.valueOf((int) usuariosOT.getCGrado_id()), "", jcylUsuario);

				listaCreditos.remove(0);
				solicitudesOT.setListaCreditos(listaCreditos);
				if ((expedienteCarreraOT.getFAceptacionceis() == null)
						&& (expedienteCarreraOT.getFNegacionMC() == null)) {
					if (listaCreditos.size() == 0) {
						solicitudesOT.setRContinuidad_proceso_validacion(Constantes.NO);
					} else {
						OCAPCreditosOT ccOT = null;
						boolean bContinua = true;
						for (int j = 1; (j < listaCreditos.size()) && (bContinua); j++) {
							ccOT = (OCAPCreditosOT) listaCreditos.get(j);
							if (ccOT.getNCreditosConseguidos().doubleValue() < ccOT.getNCreditos()) {
								bContinua = false;
							}
						}
						if (!bContinua) {
							solicitudesOT.setRContinuidad_proceso_validacion(Constantes.NO);
						} else {
							solicitudesOT.setRContinuidad_proceso_validacion(Constantes.SI);
						}
					}
				}
			}
			OCAPOtrosCentrosLN otrosCentrosLN = new OCAPOtrosCentrosLN(jcylUsuario);
			ArrayList listaOtrosCentros = otrosCentrosLN.buscarOCAPOtrosCentrosSolic(solicitudesOT.getCSolicitudId());
			solicitudesOT.setListaOtrosCentros(listaOtrosCentros);
			OCAPOtrosCentrosOT otrosCentrosOT = null;
			String cadenaCentros = "";
			for (int i = 0; i < listaOtrosCentros.size(); i++) {
				otrosCentrosOT = (OCAPOtrosCentrosOT) listaOtrosCentros.get(i);
				cadenaCentros = cadenaCentros + otrosCentrosOT.getDNombre() + "$" + otrosCentrosOT.getAProvincia() + "$"
						+ otrosCentrosOT.getACategoria() + "$" + otrosCentrosOT.getAVinculo() + "$"
						+ DateUtil.convertDateToString(otrosCentrosOT.getFInicio()) + "$"
						+ DateUtil.convertDateToString(otrosCentrosOT.getFFin()) + "#";
			}
			if (!"".equals(cadenaCentros)) {
				solicitudesOT.setResumenCentros(cadenaCentros.substring(0, cadenaCentros.length() - 1));
			}
			OCAPRevisionesLN revisionLN = new OCAPRevisionesLN(jcylUsuario);
			OCAPRevisionesOT revisionOT = revisionLN.buscarOCAPRevisiones(solicitudesOT.getCExp_id());
			solicitudesOT.setCCompfqs_id2(revisionOT.getCCompFqsId());

			OCAPConfigApp.logger.info(getClass().getName() + " datosSolicitud: Fin");
		} catch (SQLException exSQL) {
			this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: "
					+ exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
			throw exSQL;
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
		return solicitudesOT;
	}

	public void aceptarSolicitudIniciacion(int idExpediente, String fechaAceptacion, OCAPSolicitudesOT solicitudesOT,
			String pathBase, HttpServletResponse response) throws SendFailedException, Exception {
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " aceptarSolicitudIniciacion: Inicio");

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(this.jcylUsuario);
			OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(this.jcylUsuario);
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();
			OCAPConvocatoriasLN convocatoriaLN = new OCAPConvocatoriasLN(this.jcylUsuario);
			OCAPConvocatoriasOT convocatoriaOT = new OCAPConvocatoriasOT();

			convocatoriaOT = convocatoriaLN.buscarOCAPConvocatorias(solicitudesOT.getCConvocatoriaId());

			expedienteCarreraOT.setCExpId(new Long(idExpediente));
			Date fecha = DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL, fechaAceptacion);
			expedienteCarreraOT.setFAceptacSolic(fecha);
			expedienteCarreraOT.setBInconf_antiguedad(Constantes.NO);
			expedienteCarreraOT.setBInconf_plazaprop(Constantes.NO);
			expedienteCarreraOT.setBPersonales(Constantes.NO);
			expedienteCarreraOT.setBOtrosCentros(Constantes.NO);
			expedienteCarreraOT.setBPlazo(Constantes.NO);
			expedienteCarreraOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());

			idExpediente = expedienteCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);
			if ((solicitudesOT.getDEspec_nombre() != null) && (!solicitudesOT.getDEspec_nombre().equals(""))) {
				solicitudesOT.setDEspec_nombre(solicitudesOT.getDEspec_nombre());
			} else if ((solicitudesOT.getDEspec_nombre() != null) && (solicitudesOT.getDEspec_nombre().equals(""))) {
				solicitudesOT.setDEspec_nombre("-");
			}
			if ((solicitudesOT.getDProvincia() != null) && (!solicitudesOT.getDProvincia().equals(""))) {
				solicitudesOT.setDProvincia(Cadenas.capitalizar(solicitudesOT.getDProvincia()));
			} else if ((solicitudesOT.getDProvincia() != null) && (solicitudesOT.getDProvincia().equals(""))) {
				solicitudesOT.setDProvincia("-");
			}
			if ((solicitudesOT.getDTipogerencia_desc() != null)
					&& (!solicitudesOT.getDTipogerencia_desc().equals(""))) {
				solicitudesOT.setDTipogerencia_desc(solicitudesOT.getDTipogerencia_desc());
			} else if ((solicitudesOT.getDTipogerencia_desc() != null)
					&& (solicitudesOT.getDTipogerencia_desc().equals(""))) {
				solicitudesOT.setDTipogerencia_desc("-");
			}
			if ((solicitudesOT.getDGerencia_nombre() != null) && (!solicitudesOT.getDGerencia_nombre().equals(""))) {
				solicitudesOT.setDGerencia_nombre(solicitudesOT.getDGerencia_nombre());
			} else if ((solicitudesOT.getDGerencia_nombre() != null)
					&& (solicitudesOT.getDGerencia_nombre().equals(""))) {
				solicitudesOT.setDGerencia_nombre("-");
			}
			if ((solicitudesOT.getDCentrotrabajo_nombre() != null)
					&& (!solicitudesOT.getDCentrotrabajo_nombre().equals(""))) {
				solicitudesOT.setDCentrotrabajo_nombre(solicitudesOT.getDCentrotrabajo_nombre());
			} else if ((solicitudesOT.getDCentrotrabajo_nombre() != null)
					&& (solicitudesOT.getDCentrotrabajo_nombre().equals(""))) {
				solicitudesOT.setDCentrotrabajo_nombre("-");
			}
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);

			String fechainipru = df.format(fecha);

			solicitudesOT.setFInicio_mc_str(fechainipru);

			OCAPConfigApp.logger.info(getClass().getName() + " aceptarSolicitudIniciacion: Fin");
		} catch (SendFailedException e) {
			OCAPConfigApp.logger.info(
					getClass().getName() + " aceptarSolicitudIniciacion: ERROR enviando email: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " aceptarSolicitudIniciacion: ERROR: " + e.getMessage());
			throw e;
		}
	}

	public void aceptarTramitacion(int idExpediente, String fechaAceptacion, String cConvocatoriaId,
			String fRegOficialMC) throws Exception {
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " aceptarSolicitudTramitacion: Inicio");

			OCAPConvocatoriasLN convocatoriaLN = new OCAPConvocatoriasLN(this.jcylUsuario);
			OCAPConvocatoriasOT convocatoriaOT = convocatoriaLN
					.buscarOCAPConvocatorias(Long.parseLong(cConvocatoriaId));
			if ((convocatoriaOT == null) || (convocatoriaOT.getCConvocatoriaId() == 0L)) {
				throw new Exception("convocatoriaEliminada");
			}
			OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(this.jcylUsuario);
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();
			expedienteCarreraOT.setCExpId(Long.valueOf(idExpediente));

			Date fecha = DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL, fechaAceptacion);
			expedienteCarreraOT.setFInicioEvalMc(fecha);

			long dias = convocatoriaOT.getNDiasRegsolicitud() + convocatoriaOT.getNDiasRevsolicitud()
					+ convocatoriaOT.getNDiasPublistado() + convocatoriaOT.getNDiasAutoMc()
					+ convocatoriaOT.getNDiasValMc();

			Integer nDias = Integer.valueOf(String.valueOf(dias));
			Date fFinEvalMC = DateUtil.convertStringToDate(convocatoriaOT.getFPublicacion());
			fFinEvalMC = DateUtil.addDias(fFinEvalMC, nDias.intValue());
			expedienteCarreraOT.setFFinEvalMc(fFinEvalMC);
			expedienteCarreraOT.setFRegistroOficial_Mc(DateUtil.convertStringToDate(fRegOficialMC));
			expedienteCarreraOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());

			idExpediente = expedienteCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);

			OCAPConfigApp.logger.info(getClass().getName() + " aceptarSolicitudTramitacion: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			if ("convocatoriaEliminada".equals(e.getMessage())) {
				throw new Exception("convocatoriaEliminada");
			}
			throw e;
		}
	}

	public void aceptarEvaluacionMC(long idExpediente, String cConvocatoriaId, String perfil) throws Exception {
		Date dHoy = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " aceptarEvaluacionMC: Inicio");

			OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(this.jcylUsuario);
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();
			expedienteCarreraOT.setCExpId(Long.valueOf(idExpediente));
			expedienteCarreraOT = expedienteCarreraLN.buscarOCAPExpedienteCarrera(expedienteCarreraOT);

			dHoy = new Date();
			expedienteCarreraOT.setFRespuestaInconf_MC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
			expedienteCarreraOT.setCEstadoId(Constantes.ESTADO_ACEPTADA_MC);
			expedienteCarreraOT.setAModificadoPor(this.jcylUsuario.getUsuario().getC_usr_id());

			if ((Constantes.OCAP_CC.equals(perfil))) {

				expedienteCarreraOT.setFAceptacionCC(dHoy);
				expedienteCarreraOT.setFNegacionMC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));

				if ((Constantes.SITUACION_DIRECTIVO.equals(expedienteCarreraOT.getCProcedimientoId()))
						|| (Constantes.SITUACION_ESTRUCTURA.equals(expedienteCarreraOT.getCProcedimientoId()))) {
					// CC como CEIS
					expedienteCarreraOT.setFAceptacionceis(dHoy);
				}

			} else if (Constantes.OCAP_CEIS.equals(perfil)) {
				expedienteCarreraOT.setFAceptacionceis(dHoy);
				expedienteCarreraOT.setFNegacionMC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
			}
			idExpediente = expedienteCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);

			OCAPConfigApp.logger.info(getClass().getName() + " aceptarEvaluacionMC: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
	}

	public void denegarSolicitud(int idExpediente, String fecha) throws Exception {
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " denegarSolicitud: Inicio");

			OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(this.jcylUsuario);
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();

			expedienteCarreraOT.setCExpId(new Long(idExpediente));
			expedienteCarreraOT.setFNegacionSolic(DateUtil.convertStringToDate(fecha));
			expedienteCarreraOT.setAModificadoPor(this.jcylUsuario.getUsuario().getC_usr_id());

			idExpediente = expedienteCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);

			OCAPConfigApp.logger.info(getClass().getName() + " denegarSolicitud: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " denegarSolicitud: ERROR: " + e.getMessage());
			throw e;
		}
	}

	public ArrayList buscar(int inicio, int cuantos, OCAPSolicitudesOT solicitudesOT, boolean bAgrupado, String menu, boolean reasociarGrs)
			throws Exception {
		ArrayList solicitudesTotal = null;

		String gradoAnt = "";
		String categProfAnt = "";
		String especAnt = "";
		OCAPSolicitudesOT solicitudOT = null;

		OCAPSolicitudesOT categEspecSolicitudOT = null;
		OCAPSolicitudesOT gradosSolicitudOT = null;
		ArrayList listadoSolicitudes = null;
		ArrayList listadoGrados = null;
		ArrayList listadoCategEspec = null;
		ArrayList listaRetorno = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscar: Inicio");
			OCAPSolicitudesOAD solicitudesOAD = OCAPSolicitudesOAD.getInstance();
			solicitudesTotal = solicitudesOAD.buscarSolicitudes(inicio, cuantos, solicitudesOT, this.jcylUsuario, false,
					bAgrupado, menu, reasociarGrs);
			if (bAgrupado) {
				listadoGrados = new ArrayList();
				for (int i = 0; i < solicitudesTotal.size(); i++) {
					solicitudOT = (OCAPSolicitudesOT) solicitudesTotal.get(i);
					if ((solicitudOT.getDGrado_des() != null) && (!gradoAnt.equals(solicitudOT.getDGrado_des()))) {
						if ((listadoCategEspec != null) && (listadoSolicitudes != null)
								&& ((listadoCategEspec.size() > 0) || (listadoSolicitudes.size() > 0))) {
							categEspecSolicitudOT = new OCAPSolicitudesOT();
							categEspecSolicitudOT.setDProfesional_nombre(categProfAnt);
							categEspecSolicitudOT.setDEspec_nombre(especAnt);
							categEspecSolicitudOT.setListaSolicitudes(listadoSolicitudes);
							listadoCategEspec.add(categEspecSolicitudOT);

							gradosSolicitudOT = new OCAPSolicitudesOT();
							gradosSolicitudOT.setDGrado_des(gradoAnt);
							gradosSolicitudOT.setListaSolicitudes(listadoCategEspec);
							listadoGrados.add(gradosSolicitudOT);
						}
						gradoAnt = solicitudOT.getDGrado_des();
						categProfAnt = solicitudOT.getDProfesional_nombre();
						especAnt = solicitudOT.getDEspec_nombre();

						listadoCategEspec = new ArrayList();
						listadoSolicitudes = new ArrayList();
						listadoSolicitudes.add(solicitudOT);
					} else if ((solicitudOT.getDEspec_nombre() != null)
							&& (solicitudOT.getDEspec_nombre().equals(especAnt))
							&& (solicitudOT.getDProfesional_nombre() != null)
							&& (solicitudOT.getDProfesional_nombre().equals(categProfAnt))) {
						listadoSolicitudes.add(solicitudOT);
					} else {
						categEspecSolicitudOT = new OCAPSolicitudesOT();
						categEspecSolicitudOT.setDProfesional_nombre(categProfAnt);
						categEspecSolicitudOT.setDEspec_nombre(especAnt);
						categEspecSolicitudOT.setListaSolicitudes(listadoSolicitudes);
						listadoCategEspec.add(categEspecSolicitudOT);

						listadoSolicitudes = new ArrayList();
						listadoSolicitudes.add(solicitudOT);

						especAnt = solicitudOT.getDEspec_nombre();
						categProfAnt = solicitudOT.getDProfesional_nombre();
					}
				}
				if (solicitudesTotal.size() > 0) {
					categEspecSolicitudOT = new OCAPSolicitudesOT();
					categEspecSolicitudOT.setDProfesional_nombre(categProfAnt);
					categEspecSolicitudOT.setDEspec_nombre(especAnt);
					categEspecSolicitudOT.setListaSolicitudes(listadoSolicitudes);
					listadoCategEspec.add(categEspecSolicitudOT);

					gradosSolicitudOT = new OCAPSolicitudesOT();
					gradosSolicitudOT.setDGrado_des(gradoAnt);
					gradosSolicitudOT.setListaSolicitudes(listadoCategEspec);
					listadoGrados.add(gradosSolicitudOT);
				}
				listaRetorno = listadoGrados;
			} else {
				listaRetorno = solicitudesTotal;
			}
			OCAPConfigApp.logger.info(getClass().getName() + " buscar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
		return listaRetorno;
	}

	public ArrayList buscarPDF(int inicio, int cuantos, OCAPSolicitudesOT solicitudesOT) throws Exception {
		ArrayList solicitudesTotal = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarPDF: Inicio");
			OCAPSolicitudesOAD solicitudesOAD = OCAPSolicitudesOAD.getInstance();
			solicitudesTotal = solicitudesOAD.buscarSolicitudes(inicio, cuantos, solicitudesOT, this.jcylUsuario, true,
					false, "", false);
			OCAPConfigApp.logger.info(getClass().getName() + " buscarPDF: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
		return solicitudesTotal;
	}

	public ArrayList buscarCSV(int inicio, int cuantos, OCAPSolicitudesOT solicitudesOT) throws Exception {
		ArrayList solicitudesTotal = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarCSV: Inicio");
			OCAPSolicitudesOAD solicitudesOAD = OCAPSolicitudesOAD.getInstance();
			solicitudesTotal = solicitudesOAD.buscarSolicitudesCSV(inicio, cuantos, solicitudesOT,
					solicitudesOT.getCEstado(), this.jcylUsuario);
			OCAPConfigApp.logger.info(getClass().getName() + " buscarCSV: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
		return solicitudesTotal;
	}

	public int contarSolicitudes(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario, String menu, boolean reasociarGrs)
			throws Exception {
		int contador = 0;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " contarSolicitudes: Inicio");

			OCAPSolicitudesOAD solicitudesOAD = OCAPSolicitudesOAD.getInstance();

			contador = solicitudesOAD.contarSolicitudes(solicitudesOT, jcylUsuario, menu, reasociarGrs);

			OCAPConfigApp.logger.info(getClass().getName() + " contarSolicitudes: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " contarSolicitudes: ERROR: " + e.getMessage());
			throw e;
		}
		return contador;
	}

	public void crearInformeAceptacion(HttpServletResponse response, OCAPSolicitudesOT solicitudesOT, String pathBase,
			boolean soloInforme) throws SendFailedException, Exception {
		ArrayList codigosMut = null;
		String nombreReportPadre = null;
		Hashtable parametros = null;
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		String fecha = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeAceptacion: Inicio");
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_LETRA);
			Date hoy = new Date();
			fecha = df.format(hoy);

			nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "informeAceptacion" + ".jasper";

			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeAceptacion: Cargar report padre");
			File fichReportPadre = new File(nombreReportPadre);
			jasperReport = (JasperReport) JRLoader.loadObject(fichReportPadre);

			parametros = new Hashtable();
			parametros.put("ruta", pathBase);

			parametros.put("datosDocu", solicitudesOT);
			parametros.put("fecha", fecha);

			jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
			if (!soloInforme) {
				if ((solicitudesOT.getDCorreoelectronico() != null)
						&& (!solicitudesOT.getDCorreoelectronico().equals(""))) {
					String servidor_de_correo = JCYLConfiguracion.getValor("SERVIDOR_CORREO");
					String puerto = JCYLConfiguracion.getValor("PUERTO_CORREO");
					String remitente = JCYLConfiguracion.getValor("EMAIL_REMITENTE");
					String destinatario = solicitudesOT.getDCorreoelectronico();
					String asunto = "Carrera Profesional. Proceso ordinario";
					String cuerpo = null;
					if (solicitudesOT.getBSexo() == null) {
						cuerpo = "<p>Don/Do&ntilde;a ";
					} else if (Constantes.SEXO_HOMBRE_VALUE.equals(solicitudesOT.getBSexo())) {
						cuerpo = "<p>Don ";
					} else {
						cuerpo = "<p>Do&ntilde;a ";
					}
					cuerpo = cuerpo + solicitudesOT.getDNombre() + " " + solicitudesOT.getDApellido1() + ": <br />";
					cuerpo = cuerpo
							+ "<p>Le comunicamos que la solicitud presentada en fecha ###FECHA### ha sido aceptada. <br/>  Adjuntamos  el documento DCP1B NOTIFICACI&Oacute;N DE ACEPTACI&Oacute;N DE SOLICITUD  en el que se le dar&aacute; toda la informaci&oacute;n necesaria para que usted pueda comenzar con el proceso de evaluaci&oacute;n de m&eacute;ritos curriculares.</p><br />Atentamente. ";
					cuerpo = cuerpo.replaceFirst("###FECHA###", solicitudesOT.getFRegistro_oficial());

					UtilCorreo.enviarHtmlYAdjunto(servidor_de_correo, puerto, remitente, destinatario, asunto, cuerpo,
							"solicitudAceptada", bytes, null, null);
				}
			} else {
				OutputStream out = response.getOutputStream();
				response.setHeader("Content-disposition",
						"attachment; filename=\"informeAceptacion" + solicitudesOT.getCExp_id() + ".pdf\"");
				response.setContentType("application/pdf");
				response.setContentLength(bytes.length);
				out.write(bytes);
				out.flush();
				out.close();
			}
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeAceptacion: Fin");
		} catch (SendFailedException e) {
			OCAPConfigApp.logger
					.info(getClass().getName() + " crearInformeAceptacion: ERROR enviando email: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeAceptacion: ERROR" + e.getMessage());
			throw new Exception("Informe NO generado");
		}
	}

	public void guardarFechaInforme(OCAPSolicitudesOT solicitudesOT, String fase, String denegacion) throws Exception {
		OCAPExpedientecarreraOT expedienteCarreraOT = null;
		OCAPExpedienteCarreraLN expedienteCarreraLN = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " guardarFechaInforme: Inicio");
			expedienteCarreraOT = new OCAPExpedientecarreraOT();
			expedienteCarreraOT.setCExpId(new Long(solicitudesOT.getCExp_id()));
			expedienteCarreraLN = new OCAPExpedienteCarreraLN(this.jcylUsuario);
			if (Constantes.FASE_VALIDACION.equals(fase)) {
				if (("0".equals(denegacion)) || ("1".equals(denegacion))) {
					if (solicitudesOT.getFAceptacionceis() == null) {
						expedienteCarreraOT.setFMotivadoNeg(new Date());
					} else {
						expedienteCarreraOT.setFMotivadoAcep(new Date());
					}
				}
			} else if ((fase == null) || ("".equals(fase))) {
				expedienteCarreraOT.setFAclaraciones(new Date());
			}
			expedienteCarreraOT.setAModificadoPor(this.jcylUsuario.getUsuario().getC_usr_id());
			expedienteCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);

			OCAPConfigApp.logger.info(getClass().getName() + " guardarFechaInforme: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " guardarFechaInforme: ERROR" + e.getMessage());
			throw new Exception("No se puede guardar la fecha");
		}
	}

	public void crearInformeDenegacion(HttpServletResponse response, OCAPSolicitudesOT solicitudesOT, String pathBase,
			boolean soloInforme, String fase, String denegacion, JCYLUsuario jcylUsuario)
			throws SendFailedException, Exception {
		String nombreReportPadre = null;
		String nombreReportInconformidad = null;
		String nombreSubReport = null;
		Hashtable parametros = null;
		JasperReport jasperReport = null;
		JasperReport jasperSubReport = null;
		JasperPrint jasperPrint = null;
		String asunto = null;
		String cuerpo = null;
		String informe = null;
		String fechaForm = "";
		boolean bPDFInconformidad = false;
		SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		OCAPConvocatoriasLN convoLN = null;
		OCAPConvocatoriasOT convoOT = null;
		ConceptoReport matrizDatosReport = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeDenegacion: Inicio");

			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			SimpleDateFormat dfForma = new SimpleDateFormat(Constantes.FECHA_LETRA);
			Date hoy = new Date();

			String fecha = "";
			if (solicitudesOT.getFNegacion_solic() != null) {
				fecha = df.format(solicitudesOT.getFNegacion_solic());
			}
			if (solicitudesOT.getFNegacion_tmc() != null) {
				fecha = df.format(solicitudesOT.getFNegacion_tmc());
			}
			if (solicitudesOT.getFRespuestaInconf_Tmc() != null) {
				fecha = df.format(solicitudesOT.getFRespuestaInconf_Tmc());
			}
			if (solicitudesOT.getFNegacion_mc() != null) {
				fecha = df.format(solicitudesOT.getFNegacion_mc());
			}
			if (solicitudesOT.getFRespuestaInconf_MC() != null) {
				fecha = df.format(solicitudesOT.getFRespuestaInconf_MC());
			}
			if ((solicitudesOT.getFNegacion_solic() == null) && (solicitudesOT.getFNegacion_tmc() == null)
					&& (solicitudesOT.getFRespuestaInconf_Tmc() == null) && (solicitudesOT.getFNegacion_mc() == null)
					&& (solicitudesOT.getFRespuestaInconf_MC() == null)) {
				fecha = df.format(hoy);
			}
			fechaForm = dfForma.format(hoy);
			parametros = new Hashtable();
			if (!soloInforme) {
				if (solicitudesOT.getBSexo() == null) {
					cuerpo = "<p>Don/Do&ntilde;a ";
				} else if (Constantes.SEXO_HOMBRE_VALUE.equals(solicitudesOT.getBSexo())) {
					cuerpo = "<p>Don ";
				} else {
					cuerpo = "<p>Do&ntilde;a ";
				}
				cuerpo = cuerpo + solicitudesOT.getDNombre() + " " + solicitudesOT.getDApellido1() + ": <br />";
			}
			if (Constantes.FASE_INICIACION.equals(fase)) {
				if (("0".equals(denegacion)) || ("1".equals(denegacion))
						|| (("1i".equals(denegacion)) && (solicitudesOT.getDRespuestaInconfSolic() == null))) {
					nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados"
							+ File.separator + "informeDenegacion" + ".jasper";
					if (!soloInforme) {
						asunto = "Carrera Profesional. Proceso ordinario";

						cuerpo = cuerpo.replaceFirst("###FECHA###", solicitudesOT.getFRegistro_oficial());
						informe = "solicitudDesestimada";
					}
					if (!"1i".equals(denegacion)) {
						bPDFInconformidad = true;
					}
				} else if (("1i".equals(denegacion)) || ("2".equals(denegacion))) {
					nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados"
							+ File.separator + "informeRedenegacion" + ".jasper";
					if (!soloInforme) {
						asunto = "Carrera Profesional. Proceso ordinario";
						cuerpo = cuerpo
								+ "Le comunicamos que las alegaciones presentadas han sido desestimadas.<br />Atentamente. ";

						informe = "inconformidadDesestimada";
					}
				}
			}
			if (Constantes.FASE_TRAMITACION.equals(fase)) {
				if (("0".equals(denegacion)) || ("1".equals(denegacion))) {
					nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados"
							+ File.separator + "informeDenegacionTmc" + ".jasper";
					bPDFInconformidad = true;
					asunto = "Carrera Profesional. Proceso ordinario";

					cuerpo = cuerpo
							+ "<p>Le comunicamos que la documentaci&oacute;n correspondiente a la auto-evaluaci&oacute;n de sus m&eacute;ritos curriculares no se ha recibido correctamente.<br />Frente a esta resoluci&oacute;n dispone de 10 días naturales para poder presentar alegaciones. Le adjuntamos en este correo el modelo de la misma que deber&aacute; imprimir, rellenar y entregar en el Registro de su Gerencia.</p><br />Atentamente. ";

					informe = "recepcionIncorrectaMc";
				} else if (("1i".equals(denegacion)) || ("2".equals(denegacion))) {
					nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados"
							+ File.separator + "informeDenegacionInconfTmc" + ".jasper";
					if (!soloInforme) {
						asunto = "Carrera Profesional. Proceso ordinario";
						cuerpo = cuerpo
								+ "Le comunicamos que las alegaciones presentadas han sido desestimadas.<br />Atentamente. ";

						informe = "inconformidadDesestimada";
					}
				}
			}
			if (Constantes.FASE_VALIDACION.equals(fase)) {
				if (("0".equals(denegacion)) || ("1".equals(denegacion))) {
					if (solicitudesOT.getFAceptacionceis() == null) {
						nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados"
								+ File.separator + "informeMCmotivadoDenegado" + ".jasper";

						OCAPUsuariosOT usuariosOT = new OCAPUsuariosOT();

						OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);
						usuariosOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(),
								solicitudesOT.getCExp_id(), jcylUsuario);

						OCAPCreditosLN creditosLN = new OCAPCreditosLN(jcylUsuario);
						ArrayList listaCreditos = creditosLN.buscarOCAPCreditosPorCategProfesional(usuariosOT,
								new Integer((int) usuariosOT.getCGrado_id()), "", jcylUsuario);

						listaCreditos.remove(0);
						solicitudesOT.setListaCreditos(listaCreditos);

						ArrayList listaCreditosNoAlcanzados = new ArrayList();
						OCAPCreditosOT creditosOT = null;
						for (int k = 0; k < listaCreditos.size(); k++) {
							creditosOT = (OCAPCreditosOT) listaCreditos.get(k);
							if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
								if (creditosOT.getNCreditosValidadosCeis().doubleValue() < creditosOT.getNCreditos()) {
									listaCreditosNoAlcanzados.add(creditosOT);
								}
							} else if (creditosOT.getNCreditosValidadosCc().doubleValue() < creditosOT.getNCreditos()) {
								listaCreditosNoAlcanzados.add(creditosOT);
							}
						}
						String cadenaCreditos = null;
						if (listaCreditosNoAlcanzados.size() > 1) {
							cadenaCreditos = "las áreas de ";
						}
						for (int k = 0; k < listaCreditosNoAlcanzados.size() - 2; k++) {
							creditosOT = (OCAPCreditosOT) listaCreditosNoAlcanzados.get(k);
							cadenaCreditos = cadenaCreditos + "<style pdfFontName=\"Helvetica-Bold\">"
									+ creditosOT.getDMerito() + "</style>, ";
						}
						if (listaCreditosNoAlcanzados.size() > 1) {
							creditosOT = (OCAPCreditosOT) listaCreditosNoAlcanzados
									.get(listaCreditosNoAlcanzados.size() - 2);
							OCAPCreditosOT creditosOT2 = (OCAPCreditosOT) listaCreditosNoAlcanzados
									.get(listaCreditosNoAlcanzados.size() - 1);
							if (creditosOT2.getDMerito().toUpperCase().startsWith("I")) {
								cadenaCreditos = cadenaCreditos + "<style pdfFontName=\"Helvetica-Bold\">"
										+ creditosOT.getDMerito() + "</style> e <style pdfFontName=\"Helvetica-Bold\">"
										+ creditosOT2.getDMerito() + "</style>";
							} else {
								cadenaCreditos = cadenaCreditos + "<style pdfFontName=\"Helvetica-Bold\">"
										+ creditosOT.getDMerito() + "</style> y <style pdfFontName=\"Helvetica-Bold\">"
										+ creditosOT2.getDMerito() + "</style>";
							}
						} else if (listaCreditosNoAlcanzados.size() == 1) {
							creditosOT = (OCAPCreditosOT) listaCreditosNoAlcanzados.get(0);
							cadenaCreditos = "el área de <style pdfFontName=\"Helvetica-Bold\">"
									+ creditosOT.getDMerito() + "</style>";
						} else {
							cadenaCreditos = "el área de ..........";
						}
						parametros.put("areasNoSuperadas", cadenaCreditos);
					} else {
						nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados"
								+ File.separator + "informeMCmotivadoAceptado" + ".jasper";
					}
					nombreSubReport = pathBase + File.separator + "reports" + File.separator + "compilados"
							+ File.separator + "subinformeListaDefMeritos" + ".jasper";
					OCAPConfigApp.logger.info(getClass().getName() + " crearInformeDenegacion: Cargar subreport");
					File fichSubreport = new File(nombreSubReport);
					jasperSubReport = (JasperReport) JRLoader.loadObject(fichSubreport);

					parametros.put("SUB_REPORT", jasperSubReport);

					matrizDatosReport = new ConceptoReport();
					OCAPDefMeritoscurricularesLN defMCLN = new OCAPDefMeritoscurricularesLN(jcylUsuario);
					ArrayList listaDefMeritos = defMCLN.listarDefmeritosPorCatProfesional(solicitudesOT.getCGrado_id(),
							solicitudesOT.getCProfesional_id());
					matrizDatosReport = generarTablaDefMeritos(listaDefMeritos);
					parametros.put("datosReport", matrizDatosReport);

					fecha = df.format(hoy);
					if (!soloInforme) {
						asunto = "Carrera Profesional. Proceso ordinario";
						cuerpo = cuerpo
								+ "<p>Le comunicamos que seg&uacute;n el informe emitido por el Comit&eacute; Espec&iacute;fico de la Instituci&oacute;n Sanitaria de su Gerencia no ha llegado a conseguir los cr&eacute;ditos necesarios en relaci&oacute;n a sus m&eacute;ritos curriculares. <br />Adjuntamos el documento DCP5A  NOTIFICACI&Oacute;N DE NO VALIDACI&Oacute;N  DE  LA FASE DE M&Eacute;RITOS CURRICULARES  DE C.P. en el que se le comunica cu&aacute;l es el motivo de dicha desestimaci&oacute;n.<br />Frente a esta resoluci&oacute;n dispone de 10 días naturales para poder presentar alegaciones. Le adjuntamos en este correo el modelo de la misma que deber&aacute; imprimir, rellenar y entregar en el Registro de su Gerencia.</p><br />Atentamente. ";

						informe = "noValidaMc";
					}
				} else if (("1i".equals(denegacion)) || ("2".equals(denegacion))) {
					nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados"
							+ File.separator + "informeDenegacionInconfMC" + ".jasper";
					if (!soloInforme) {
						asunto = "Carrera Profesional. Proceso ordinario";
						cuerpo = cuerpo
								+ "Le comunicamos que las alegaciones presentadas han sido desestimadas.<br />Atentamente. ";

						informe = "inconformidadDesestimada";
					}
				}
			} else {
				fecha = df.format(hoy);
				nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados"
						+ File.separator + "informeAclaracionesMC" + ".jasper";
				nombreSubReport = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
						+ "subinformeAclaracionesMC" + ".jasper";
				OCAPConfigApp.logger.info(getClass().getName() + " crearInformeDenegacion: Cargar subreport");
				File fichSubreport = new File(nombreSubReport);
				jasperSubReport = (JasperReport) JRLoader.loadObject(fichSubreport);

				parametros.put("SUB_REPORT", jasperSubReport);

				matrizDatosReport = new ConceptoReport();
				OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);
				OCAPUsuariosOT usuariosOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(),
						solicitudesOT.getCExp_id(), jcylUsuario);
				usuariosOT = usuariosLN.datosMCUsuario(usuariosOT, "0");
				matrizDatosReport = generarTablaMeritosAclaraciones(usuariosOT);
				parametros.put("datosReport", matrizDatosReport);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeDenegacion: Cargar report padre");
			File fichReportPadre = new File(nombreReportPadre);
			jasperReport = (JasperReport) JRLoader.loadObject(fichReportPadre);

			parametros.put("ruta", pathBase);

			parametros.put("fecha",
					DateUtil.convertDateToStringLarga(Constantes.FECHA_LETRA, DateUtil.convertStringToDate(fecha)));
			parametros.put("fechaForm", fechaForm);

			parametros.put("datosDocu", solicitudesOT);

			convoLN = new OCAPConvocatoriasLN(jcylUsuario);
			convoOT = convoLN.buscarOCAPConvocatorias(solicitudesOT.getCConvocatoriaId());
			parametros.put("fechaConvocatoria", DateUtil.convertDateToStringLarga(Constantes.FECHA_LETRA,
					DateUtil.convertStringToDate(convoOT.getFResolucion())));
			if (solicitudesOT.getBSexo().equals(Constantes.SEXO_HOMBRE_VALUE)) {
				parametros.put("sexo", "D.");
			} else {
				parametros.put("sexo", "Dña.");
			}
			jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);

			byte[] bytesInconformidad = null;
			if (bPDFInconformidad) {
				nombreReportInconformidad = pathBase + File.separator + "reports" + File.separator + "compilados"
						+ File.separator + "informeInconformidad" + ".jasper";
				OCAPConfigApp.logger
						.info(getClass().getName() + " crearInformeDenegacion: Cargar report inconformidad");
				File fichReport = new File(nombreReportInconformidad);
				jasperReport = (JasperReport) JRLoader.loadObject(fichReport);

				parametros = new Hashtable();
				parametros.put("ruta", pathBase);
				parametros.put("fecha", fecha);
				if (Constantes.FASE_INICIACION.equals(fase)) {
					parametros.put("texto", Constantes.TEXT_INCONFORMIDAD_SOLICITUD);
				} else {
					parametros.put("texto", Constantes.TEXT_INCONFORMIDAD_NOSOLICITUD);
				}
				parametros.put("datosDocu", solicitudesOT);

				jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

				bytesInconformidad = JasperExportManager.exportReportToPdf(jasperPrint);
			}
			if (soloInforme) {
				OutputStream out = response.getOutputStream();
				if (bPDFInconformidad) {
					PdfReader reader1 = new PdfReader(bytes);
					PdfReader reader2 = new PdfReader(bytesInconformidad);
					PdfCopyFields copy = new PdfCopyFields(
							new FileOutputStream(pathBase + File.separator + "temp.pdf"));
					copy.addDocument(reader1);
					copy.addDocument(reader2);
					copy.close();

					response.setHeader("Content-disposition",
							"attachment; filename=\"Informe" + solicitudesOT.getCDni() + ".pdf\"");
					response.setContentType("application/pdf");
					InputStream archivo = new FileInputStream(pathBase + File.separator + "temp.pdf");

					int bit = 256;
					int i = 0;
					while (bit >= 0) {
						bit = archivo.read();
						out.write(bit);
					}
				} else {
					response.setHeader("Content-disposition",
							"attachment; filename=\"Informe" + solicitudesOT.getCDni() + ".pdf\"");
					response.setContentType("application/pdf");
					response.setContentLength(bytes.length);
					out.write(bytes);
				}
				out.flush();
				out.close();
			} else if ((solicitudesOT.getDCorreoelectronico() != null)
					&& (!solicitudesOT.getDCorreoelectronico().equals(""))) {
				String servidor_de_correo = JCYLConfiguracion.getValor("SERVIDOR_CORREO");
				String puerto = JCYLConfiguracion.getValor("PUERTO_CORREO");
				String remitente = JCYLConfiguracion.getValor("EMAIL_REMITENTE");
				String destinatario = solicitudesOT.getDCorreoelectronico();

				UtilCorreo.enviarHtmlYAdjunto(servidor_de_correo, puerto, remitente, destinatario, asunto, cuerpo,
						informe, bytes, bytesInconformidad, "Alegaciones");
			}
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeDenegacion: Fin");
		} catch (SendFailedException e) {
			OCAPConfigApp.logger
					.info(getClass().getName() + " crearInformeDenegacion: ERROR enviando email: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeDenegacion: ERROR" + e.getMessage());
			throw new Exception("Informe NO generado");
		}
	}

	public void crearActaReunion(JCYLUsuario jcylUsuario, HttpServletResponse response, OCAPSolicitudesOT solicitudesOT,
			String pathBase, ArrayList listadoVocales, ArrayList listadoVocalesSuplentes,
			ArrayList usuariosConInformeMotivado, ArrayList usuariosConAclaraciones) throws Exception {
		String nombreReportPadre = null;
		String nombreSubreportListadoVocales = null;
		String nombreSubreportListadoVocalesSuplentes = null;
		String nombreSubreportListadoUsuarios = null;
		Hashtable parametros = new Hashtable();
		JasperReport jasperReport = null;
		JasperReport jasperSubReportListadoVocales = null;
		JasperReport jasperSubReportListadoVocalesSuplentes = null;
		JasperReport jasperSubReportListadoUsuarios = null;
		JasperPrint jasperPrint = null;
		ConceptoReport datosSubReportListadoVocales = null;
		ConceptoReport datosSubReportListadoVocalesSuplentes = null;
		ConceptoReport datosSubReportListadoUsuarios = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearActaReunion: Inicio");

			nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "actaReunion" + ".jasper";
			nombreSubreportListadoVocales = pathBase + File.separator + "reports" + File.separator + "compilados"
					+ File.separator + "actasListadoVocales" + ".jasper";
			nombreSubreportListadoVocalesSuplentes = pathBase + File.separator + "reports" + File.separator
					+ "compilados" + File.separator + "actasListadoVocalesSuplentes" + ".jasper";
			nombreSubreportListadoUsuarios = pathBase + File.separator + "reports" + File.separator + "compilados"
					+ File.separator + "actasListadoUsuarios" + ".jasper";

			OCAPConfigApp.logger.info(getClass().getName() + " crearActaReunion: Cargar report padre");
			File fichReportPadre = new File(nombreReportPadre);
			jasperReport = (JasperReport) JRLoader.loadObject(fichReportPadre);

			File fichSubReportListadoVocales = new File(nombreSubreportListadoVocales);
			jasperSubReportListadoVocales = (JasperReport) JRLoader.loadObject(fichSubReportListadoVocales);
			File fichSubReportListadoVocalesSuplentes = new File(nombreSubreportListadoVocalesSuplentes);
			jasperSubReportListadoVocalesSuplentes = (JasperReport) JRLoader
					.loadObject(fichSubReportListadoVocalesSuplentes);
			File fichSubReportListadoUsuarios = new File(nombreSubreportListadoUsuarios);
			jasperSubReportListadoUsuarios = (JasperReport) JRLoader.loadObject(fichSubReportListadoUsuarios);

			parametros.put("ruta", pathBase);

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			OCAPGradoOT grado = gradoLN.buscarOCAPGrado(solicitudesOT.getCGrado_id());
			parametros.put("grado", grado);

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT convocatoria = convocatoriasLN
					.buscarOCAPConvocatorias(solicitudesOT.getCConvocatoriaId());
			parametros.put("convocatoria", convocatoria);

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			OCAPCategProfesionalesOT categoria = categProfesionalesLN
					.buscarOCAPCategProfesionales(solicitudesOT.getCProfesional_id());
			parametros.put("categoria", categoria);

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);

			Map parametrosUsuario = jcylUsuario.getParametrosUsuario();
			OCAPGerenciasOT gerencia = gerenciasLN
					.buscarOCAPGerencias(Long.parseLong((String) parametrosUsuario.get("PARAM_GERENCIA")));
			gerencia.setDProvinciaNombre(Cadenas.capitalizar(gerencia.getDProvinciaNombre()));
			parametros.put("gerencia", gerencia);

			ArrayList listadoUsuarios = new ArrayList();
			for (int i = 0; i < usuariosConInformeMotivado.size(); i++) {
				String dNombreApellidos = ((OCAPUsuariosOT) usuariosConInformeMotivado.get(i)).getDNombre() + " "
						+ ((OCAPUsuariosOT) usuariosConInformeMotivado.get(i)).getDApellido1();
				String dDNI = ((OCAPUsuariosOT) usuariosConInformeMotivado.get(i)).getCDniReal();
				String dGrado = ((OCAPUsuariosOT) usuariosConInformeMotivado.get(i)).getDGrado_des();
				listadoUsuarios.add(new OCAPUsuarioOT(dNombreApellidos, dDNI, "Informe motivado", dGrado));
			}
			for (int i = 0; i < usuariosConAclaraciones.size(); i++) {
				String dNombreApellidos = ((OCAPUsuariosOT) usuariosConAclaraciones.get(i)).getDNombre() + " "
						+ ((OCAPUsuariosOT) usuariosConAclaraciones.get(i)).getDApellido1();
				String dDNI = ((OCAPUsuariosOT) usuariosConAclaraciones.get(i)).getCDniReal();
				String dGrado = ((OCAPUsuariosOT) usuariosConInformeMotivado.get(i)).getDGrado_des();
				listadoUsuarios.add(new OCAPUsuarioOT(dNombreApellidos, dDNI, "Aclaraciones", dGrado));
			}
			solicitudesOT.setListadoVocales(listadoVocales);
			solicitudesOT.setListadoVocalesSuplentes(listadoVocalesSuplentes);

			anhadirTratamientoActaConstitucion(solicitudesOT);
			anhadirEnCalidadActaConstitucion(solicitudesOT);

			String fSesionImprimible = DateUtil.convertDateToStringLarga(Constantes.FECHA_LETRA,
					solicitudesOT.getFSesion());
			solicitudesOT.setFSesionImprimible(fSesionImprimible);

			parametros.put("datosDocu", solicitudesOT);

			parametros.put("listadoVocales", jasperSubReportListadoVocales);
			datosSubReportListadoVocales = new ConceptoReport();
			datosSubReportListadoVocales = generarDatosSubReportListadoVocales(listadoVocales, jcylUsuario);
			if (datosSubReportListadoVocales == null) {
				parametros.put("vocalesVacio", "");
			} else {
				parametros.put("datosListadoVocales", datosSubReportListadoVocales);
			}
			parametros.put("listadoVocalesSuplentes", jasperSubReportListadoVocalesSuplentes);
			datosSubReportListadoVocalesSuplentes = new ConceptoReport();
			datosSubReportListadoVocalesSuplentes = generarDatosSubReportListadoVocales(listadoVocalesSuplentes,
					jcylUsuario);
			if (datosSubReportListadoVocalesSuplentes == null) {
				parametros.put("vocalesSuplentesVacio", "");
			} else {
				parametros.put("datosListadoVocalesSuplentes", datosSubReportListadoVocalesSuplentes);
			}
			parametros.put("listadoUsuarios", jasperSubReportListadoUsuarios);
			datosSubReportListadoUsuarios = new ConceptoReport();
			datosSubReportListadoUsuarios = generarDatosSubReportListadoUsuarios(listadoUsuarios, jcylUsuario);
			if (datosSubReportListadoUsuarios == null) {
				parametros.put("usuariosVacio", "No se ha valorado ningún profesional en esta fecha.");
			} else {
				parametros.put("datosListadoUsuarios", datosSubReportListadoUsuarios);
			}
			jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);

			OutputStream out = response.getOutputStream();
			SimpleDateFormat ahora = new SimpleDateFormat("ddMMyyyy_hhmm");
			String nombreFichero = "actaReunion" + ahora.format(new Date()) + ".pdf";
			response.setHeader("Content-disposition", "attachment; filename=\"" + nombreFichero + "\"");
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();

			OCAPConfigApp.logger.info(getClass().getName() + " crearActaReunion: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " crearActaReunion: ERROR" + e.getMessage());
			throw new Exception("Acta de Constitución NO generada");
		}
	}

	private ArrayList obtenerVocalesTitulares(ArrayList listadoVocales) {
		ArrayList listadoVocalesTitulares = new ArrayList();
		for (int i = 0; i < listadoVocales.size(); i++) {
			if ("T".equals(((OCAPAsistenteOT) listadoVocales.get(i)).getBEnCalidad())) {
				listadoVocalesTitulares.add(listadoVocales.get(i));
			}
		}
		return listadoVocalesTitulares;
	}

	private ArrayList obtenerVocalesSuplentes(ArrayList listadoVocales) {
		ArrayList listadoVocalesSuplentes = new ArrayList();
		for (int i = 0; i < listadoVocales.size(); i++) {
			if (Constantes.SI_ESP.equals(((OCAPAsistenteOT) listadoVocales.get(i)).getBEnCalidad())) {
				listadoVocalesSuplentes.add(listadoVocales.get(i));
			}
		}
		return listadoVocalesSuplentes;
	}

	public void crearActaSoliAclaracion(JCYLUsuario jcylUsuario, HttpServletResponse response,
			OCAPSolicitudesOT solicitudesOT, String pathBase, ArrayList listadoVocales,
			ArrayList listadoVocalesSuplentes, ArrayList usuariosConAclaraciones) throws Exception {
		String nombreReportPadre = null;
		String nombreSubreportListadoVocales = null;
		String nombreSubreportListadoVocalesSuplentes = null;
		String nombreSubreportListadoUsuarios = null;
		Hashtable parametros = new Hashtable();
		JasperReport jasperReport = null;
		JasperReport jasperSubReportListadoVocales = null;
		JasperReport jasperSubReportListadoVocalesSuplentes = null;
		JasperReport jasperSubReportListadoUsuarios = null;
		JasperPrint jasperPrint = null;
		ConceptoReport datosSubReportListadoVocales = null;
		ConceptoReport datosSubReportListadoVocalesSuplentes = null;
		ConceptoReport datosSubReportListadoUsuarios = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearActaSoliAclaracion: Inicio");

			nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "actaAclaraciones" + ".jasper";
			nombreSubreportListadoVocales = pathBase + File.separator + "reports" + File.separator + "compilados"
					+ File.separator + "actasListadoVocales" + ".jasper";
			nombreSubreportListadoVocalesSuplentes = pathBase + File.separator + "reports" + File.separator
					+ "compilados" + File.separator + "actasListadoVocalesSuplentes" + ".jasper";
			nombreSubreportListadoUsuarios = pathBase + File.separator + "reports" + File.separator + "compilados"
					+ File.separator + "actasListadoUsuarios" + ".jasper";

			OCAPConfigApp.logger.info(getClass().getName() + " crearActaSoliAclaracion: Cargar report padre");
			File fichReportPadre = new File(nombreReportPadre);
			jasperReport = (JasperReport) JRLoader.loadObject(fichReportPadre);

			File fichSubReportListadoVocales = new File(nombreSubreportListadoVocales);
			jasperSubReportListadoVocales = (JasperReport) JRLoader.loadObject(fichSubReportListadoVocales);
			File fichSubReportListadoVocalesSuplentes = new File(nombreSubreportListadoVocalesSuplentes);
			jasperSubReportListadoVocalesSuplentes = (JasperReport) JRLoader
					.loadObject(fichSubReportListadoVocalesSuplentes);
			File fichSubReportListadoUsuarios = new File(nombreSubreportListadoUsuarios);
			jasperSubReportListadoUsuarios = (JasperReport) JRLoader.loadObject(fichSubReportListadoUsuarios);

			parametros.put("ruta", pathBase);

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			OCAPGradoOT grado = gradoLN.buscarOCAPGrado(solicitudesOT.getCGrado_id());
			parametros.put("grado", grado);

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT convocatoria = convocatoriasLN
					.buscarOCAPConvocatorias(solicitudesOT.getCConvocatoriaId());
			parametros.put("convocatoria", convocatoria);

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			OCAPCategProfesionalesOT categoria = categProfesionalesLN
					.buscarOCAPCategProfesionales(solicitudesOT.getCProfesional_id());
			parametros.put("categoria", categoria);

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);

			Map parametrosUsuario = jcylUsuario.getParametrosUsuario();
			OCAPGerenciasOT gerencia = gerenciasLN
					.buscarOCAPGerencias(Long.parseLong((String) parametrosUsuario.get("PARAM_GERENCIA")));
			gerencia.setDProvinciaNombre(Cadenas.capitalizar(gerencia.getDProvinciaNombre()));
			parametros.put("gerencia", gerencia);

			OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();

			ArrayList listadoUsuarios = new ArrayList();
			for (int i = 0; i < usuariosConAclaraciones.size(); i++) {
				String dNombreApellidos = ((OCAPUsuariosOT) usuariosConAclaraciones.get(i)).getDNombre() + " "
						+ ((OCAPUsuariosOT) usuariosConAclaraciones.get(i)).getDApellido1();
				String dDNI = ((OCAPUsuariosOT) usuariosConAclaraciones.get(i)).getCDniReal();
				String dGrado = ((OCAPUsuariosOT) usuariosConAclaraciones.get(i)).getDGrado_des();
				listadoUsuarios.add(new OCAPUsuarioOT(dNombreApellidos, dDNI, "Aclaraciones", dGrado));
			}
			solicitudesOT.setListadoVocales(listadoVocales);
			solicitudesOT.setListadoVocalesSuplentes(listadoVocalesSuplentes);

			anhadirTratamientoActaConstitucion(solicitudesOT);
			anhadirEnCalidadActaConstitucion(solicitudesOT);

			String fSesionImprimible = DateUtil.convertDateToStringLarga(Constantes.FECHA_LETRA,
					solicitudesOT.getFSesion());
			solicitudesOT.setFSesionImprimible(fSesionImprimible);

			parametros.put("datosDocu", solicitudesOT);

			parametros.put("listadoVocales", jasperSubReportListadoVocales);
			datosSubReportListadoVocales = new ConceptoReport();
			datosSubReportListadoVocales = generarDatosSubReportListadoVocales(listadoVocales, jcylUsuario);
			if (datosSubReportListadoVocales == null) {
				parametros.put("vocalesVacio", "");
			} else {
				parametros.put("datosListadoVocales", datosSubReportListadoVocales);
			}
			parametros.put("listadoVocalesSuplentes", jasperSubReportListadoVocalesSuplentes);
			datosSubReportListadoVocalesSuplentes = new ConceptoReport();
			datosSubReportListadoVocalesSuplentes = generarDatosSubReportListadoVocales(listadoVocalesSuplentes,
					jcylUsuario);
			if (datosSubReportListadoVocalesSuplentes == null) {
				parametros.put("vocalesSuplentesVacio", "");
			} else {
				parametros.put("datosListadoVocalesSuplentes", datosSubReportListadoVocalesSuplentes);
			}
			parametros.put("listadoUsuarios", jasperSubReportListadoUsuarios);
			datosSubReportListadoUsuarios = new ConceptoReport();
			datosSubReportListadoUsuarios = generarDatosSubReportListadoUsuarios(listadoUsuarios, jcylUsuario);
			if (datosSubReportListadoUsuarios == null) {
				parametros.put("usuariosVacio", "No se ha valorado ningún profesional en esta fecha.");
			} else {
				parametros.put("datosListadoUsuarios", datosSubReportListadoUsuarios);
			}
			jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);

			OutputStream out = response.getOutputStream();
			SimpleDateFormat ahora = new SimpleDateFormat("ddMMyyyy_hhmm");
			String nombreFichero = "actaSolicitudAclaraciones" + ahora.format(new Date()) + ".pdf";
			response.setHeader("Content-disposition", "attachment; filename=\"" + nombreFichero + "\"");
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();

			OCAPConfigApp.logger.info(getClass().getName() + " crearActaSoliAclaracion: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " crearActaSoliAclaracion: ERROR" + e.getMessage());
			throw new Exception("Acta de Solicitud Aclaraciones NO generada");
		}
	}

	public ConceptoReport generarTablaDefMeritos(ArrayList datos) throws Exception {
		ConceptoReport report = null;
		OCAPDefMeritoscurricularesOT defMerOT = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarTablaDefMeritos: Inicio");
			if (datos.size() != 0) {
				report = new ConceptoReport();
			}
			for (int i = 0; i < datos.size(); i++) {
				defMerOT = new OCAPDefMeritoscurricularesOT();
				defMerOT = (OCAPDefMeritoscurricularesOT) datos.get(i);
				report.addRow();
				report.putElement("nombreDefMerito", defMerOT.getDNombre());
				if ((defMerOT.getBPresente() != null) && (Constantes.SI.equals(defMerOT.getBPresente()))) {
					report.putElement("estaPresente", "X");
				} else {
					report.putElement("estaPresente", "");
				}
			}
			OCAPConfigApp.logger.info(getClass().getName() + " generarTablaDefMeritos: Fin");
			return report;
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " generarTablaDefMeritos: ERROR" + e.getMessage());
			throw e;
		}
	}

	public ConceptoReport generarTablaMeritosAclaraciones(OCAPUsuariosOT usuOT) throws Exception {
		ConceptoReport report = null;
		OCAPCreditosOT creditoOT = null;
		OCAPMeritoscurricularesOT mcOT = null;
		OCAPMeritoscurricularesOT mcOT2 = null;
		OCAPExpedientemcOT expmcOT = null;
		String cadenaAclaracion = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarTablaDefMeritos: Inicio");
			if (usuOT.getListaCreditos().size() != 0) {
				report = new ConceptoReport();
			}
			ArrayList listaDefMeritos = usuOT.getListaMeritos();
			ArrayList listaCreditos = usuOT.getListaCreditos();
			ArrayList listaDefMeritosOpcionales = usuOT.getListaMeritosOpcionales();

			int tamanoListaCreditos = listaCreditos != null ? listaCreditos.size() : 0;
			for (int i = 0; i < tamanoListaCreditos; i++) {
				creditoOT = (OCAPCreditosOT) listaCreditos.get(i);
				if (!"5".equals(String.valueOf(creditoOT.getCDefmeritoId()))) {
					int tamanoListaDefMeritos = listaDefMeritos != null ? listaDefMeritos.size() : 0;
					for (int j = 0; j < tamanoListaDefMeritos; j++) {
						mcOT = (OCAPMeritoscurricularesOT) listaDefMeritos.get(j);
						if (mcOT.getCDefmeritoId() == creditoOT.getCDefmeritoId()) {
							cadenaAclaracion = "Créditos de " + Cadenas.capitalizar(mcOT.getDNombre())
									+ " relativos a ";
							ArrayList listaTipoMeritos = mcOT.getListaMeritosUsuario();
							int tamanoListaTipoMeritos = listaTipoMeritos != null ? listaTipoMeritos.size() : 0;
							for (int l = 0; l < tamanoListaTipoMeritos; l++) {
								mcOT2 = (OCAPMeritoscurricularesOT) listaTipoMeritos.get(l);

								int tamanoListaMeritos = mcOT2.getListaExpmc() != null ? mcOT2.getListaExpmc().size()
										: 0;
								for (int k = 0; k < tamanoListaMeritos; k++) {
									expmcOT = (OCAPExpedientemcOT) mcOT2.getListaExpmc().get(k);
									if ((expmcOT.getBPdteAclararCc() != null)
											&& (Constantes.SI.equals(expmcOT.getBPdteAclararCc()))) {
										report.addRow();
										report.putElement("nombreMerito",
												cadenaAclaracion + "'" + expmcOT.getDTitulo() + "'.");
									}
								}
								int tamanoListaMeritosCTSP = mcOT2.getListaExpmcCTSP() != null
										? mcOT2.getListaExpmcCTSP().size() : 0;
								for (int k = 0; k < tamanoListaMeritosCTSP; k++) {
									expmcOT = (OCAPExpedientemcOT) mcOT2.getListaExpmcCTSP().get(k);
									if ((expmcOT.getBPdteAclararCc() != null)
											&& (Constantes.SI.equals(expmcOT.getBPdteAclararCc()))) {
										report.addRow();
										report.putElement("nombreMerito",
												cadenaAclaracion + "'" + expmcOT.getDTitulo() + "'.");
									}
								}
								int tamanoListaMeritosCTSNP = mcOT2.getListaExpmcCTSNP() != null
										? mcOT2.getListaExpmcCTSNP().size() : 0;
								for (int k = 0; k < tamanoListaMeritosCTSNP; k++) {
									expmcOT = (OCAPExpedientemcOT) mcOT2.getListaExpmcCTSNP().get(k);
									if ((expmcOT.getBPdteAclararCc() != null)
											&& (Constantes.SI.equals(expmcOT.getBPdteAclararCc()))) {
										report.addRow();
										report.putElement("nombreMerito",
												cadenaAclaracion + "'" + expmcOT.getDTitulo() + "'.");
									}
								}
								int tamanoListaMeritosCTSM = mcOT2.getListaExpmcCTSM() != null
										? mcOT2.getListaExpmcCTSM().size() : 0;
								for (int k = 0; k < tamanoListaMeritosCTSM; k++) {
									expmcOT = (OCAPExpedientemcOT) mcOT2.getListaExpmcCTSM().get(k);
									if ((expmcOT.getBPdteAclararCc() != null)
											&& (Constantes.SI.equals(expmcOT.getBPdteAclararCc()))) {
										report.addRow();
										report.putElement("nombreMerito",
												cadenaAclaracion + "'" + expmcOT.getDTitulo() + "'.");
									}
								}
							}
						}
					}
				} else {
					cadenaAclaracion = "Créditos de Opcionales relativos a ";
					int tamanoListaDefMeritosOpcionales = listaDefMeritosOpcionales != null
							? listaDefMeritosOpcionales.size() : 0;
					for (int j = 0; j < tamanoListaDefMeritosOpcionales; j++) {
						mcOT = (OCAPMeritoscurricularesOT) listaDefMeritosOpcionales.get(j);
						ArrayList listaTipoMeritos = mcOT.getListaMeritosUsuario();
						int tamanoListaTipoMeritos = listaTipoMeritos != null ? listaTipoMeritos.size() : 0;
						for (int l = 0; l < tamanoListaTipoMeritos; l++) {
							mcOT2 = (OCAPMeritoscurricularesOT) listaTipoMeritos.get(l);
							ArrayList listaMeritos = mcOT2.getListaExpmc();
							int tamanoListaMeritos = listaMeritos != null ? listaMeritos.size() : 0;
							for (int k = 0; k < tamanoListaMeritos; k++) {
								expmcOT = (OCAPExpedientemcOT) listaMeritos.get(k);
								if ((expmcOT.getBPdteAclararCc() != null)
										&& (Constantes.SI.equals(expmcOT.getBPdteAclararCc()))) {
									report.addRow();
									report.putElement("nombreMerito",
											cadenaAclaracion + "'" + expmcOT.getDTitulo() + "'.");
								}
							}
						}
					}
				}
			}
			OCAPConfigApp.logger.info(getClass().getName() + " generarTablaMeritosAclaraciones: Fin");
			return report;
		} catch (Exception e) {
			OCAPConfigApp.logger
					.error(getClass().getName() + " generarTablaMeritosAclaraciones: ERROR " + e.toString());
			throw e;
		}
	}

	public void crearActaConstitucion(JCYLUsuario jcylUsuario, HttpServletResponse response,
			OCAPSolicitudesOT solicitudesOT, String pathBase, ArrayList listadoVocales,
			ArrayList listadoVocalesSuplentes) throws Exception {
		String nombreReportPadre = null;
		String nombreSubreportVocales = null;
		String nombreSubreportVocalesSuplentes = null;
		Hashtable parametros = new Hashtable();
		JasperReport jasperReport = null;
		JasperReport jasperSubReport = null;
		JasperReport jasperSubReportSuplentes = null;
		JasperPrint jasperPrint = null;
		ConceptoReport datosSubReportListadoVocales = null;
		ConceptoReport datosSubReportListadoVocalesSuplentes = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearActaConstitucion: Inicio");

			nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "actaConstitucion" + ".jasper";
			nombreSubreportVocales = pathBase + File.separator + "reports" + File.separator + "compilados"
					+ File.separator + "actasListadoVocales" + ".jasper";
			nombreSubreportVocalesSuplentes = pathBase + File.separator + "reports" + File.separator + "compilados"
					+ File.separator + "actasListadoVocalesSuplentes" + ".jasper";

			OCAPConfigApp.logger.info(getClass().getName() + " crearActaConstitucion: Cargar report padre");
			File fichReportPadre = new File(nombreReportPadre);
			jasperReport = (JasperReport) JRLoader.loadObject(fichReportPadre);

			File fichSubReportListadoVocales = new File(nombreSubreportVocales);
			jasperSubReport = (JasperReport) JRLoader.loadObject(fichSubReportListadoVocales);

			File fichSubReportListadoVocalesSuplentes = new File(nombreSubreportVocalesSuplentes);
			jasperSubReportSuplentes = (JasperReport) JRLoader.loadObject(fichSubReportListadoVocalesSuplentes);

			parametros.put("ruta", pathBase);

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			OCAPGradoOT grado = gradoLN.buscarOCAPGrado(solicitudesOT.getCGrado_id());
			parametros.put("grado", grado);

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT convocatoria = convocatoriasLN
					.buscarOCAPConvocatorias(solicitudesOT.getCConvocatoriaId());
			parametros.put("convocatoria", convocatoria);

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			OCAPCategProfesionalesOT categoria = categProfesionalesLN
					.buscarOCAPCategProfesionales(solicitudesOT.getCProfesional_id());
			parametros.put("categoria", categoria);

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);

			Map parametrosUsuario = jcylUsuario.getParametrosUsuario();
			OCAPGerenciasOT gerencia = gerenciasLN
					.buscarOCAPGerencias(Long.parseLong((String) parametrosUsuario.get("PARAM_GERENCIA")));
			gerencia.setDProvinciaNombre(Cadenas.capitalizar(gerencia.getDProvinciaNombre()));
			parametros.put("gerencia", gerencia);

			solicitudesOT.setListadoVocales(listadoVocales);
			solicitudesOT.setListadoVocalesSuplentes(listadoVocalesSuplentes);

			anhadirTratamientoActaConstitucion(solicitudesOT);
			anhadirEnCalidadActaConstitucion(solicitudesOT);

			String fSesionImprimible = DateUtil.convertDateToStringLarga(Constantes.FECHA_LETRA,
					solicitudesOT.getFSesion());
			solicitudesOT.setFSesionImprimible(fSesionImprimible);

			parametros.put("datosDocu", solicitudesOT);

			parametros.put("listadoVocales", jasperSubReport);
			datosSubReportListadoVocales = new ConceptoReport();
			datosSubReportListadoVocales = generarDatosSubReportListadoVocales(listadoVocales, jcylUsuario);
			if (datosSubReportListadoVocales == null) {
				parametros.put("vocalesVacio", "");
			} else {
				parametros.put("datosListadoVocales", datosSubReportListadoVocales);
			}
			parametros.put("listadoVocalesSuplentes", jasperSubReportSuplentes);
			datosSubReportListadoVocalesSuplentes = new ConceptoReport();
			datosSubReportListadoVocalesSuplentes = generarDatosSubReportListadoVocales(listadoVocalesSuplentes,
					jcylUsuario);
			if (datosSubReportListadoVocalesSuplentes == null) {
				parametros.put("vocalesSuplentesVacio", "");
			} else {
				parametros.put("datosListadoVocalesSuplentes", datosSubReportListadoVocalesSuplentes);
			}
			jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);

			OutputStream out = response.getOutputStream();
			SimpleDateFormat ahora = new SimpleDateFormat("ddMMyyyy_hhmm");
			String nombreFichero = "actaConstitucion" + ahora.format(new Date()) + ".pdf";
			response.setHeader("Content-disposition", "attachment; filename=\"" + nombreFichero + "\"");
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();

			OCAPConfigApp.logger.info(getClass().getName() + " crearActaConstitucion: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " crearActaConstitucion: ERROR" + e.getMessage());
			throw new Exception("Acta de Constitución NO generada");
		}
	}

	private void anhadirTratamientoActaConstitucion(OCAPSolicitudesOT solicitudesOT) {
		if (!Cadenas.EsVacia(solicitudesOT.getBSexoPresi1())) {
			if (Constantes.SEXO_HOMBRE_VALUE.equals(solicitudesOT.getBSexoPresi1())) {
				solicitudesOT.setDNombreApellidosPresi1("D. " + solicitudesOT.getDNombreApellidosPresi1());
			} else {
				solicitudesOT.setDNombreApellidosPresi1("Dña. " + solicitudesOT.getDNombreApellidosPresi1());
			}
		}
		if (!Cadenas.EsVacia(solicitudesOT.getBSexoPresi2())) {
			if (Constantes.SEXO_HOMBRE_VALUE.equals(solicitudesOT.getBSexoPresi2())) {
				solicitudesOT.setDNombreApellidosPresi2("D. " + solicitudesOT.getDNombreApellidosPresi2());
			} else {
				solicitudesOT.setDNombreApellidosPresi2("Dña. " + solicitudesOT.getDNombreApellidosPresi2());
			}
		}
		for (int i = 0; i < solicitudesOT.getListadoVocales().size(); i++) {
			if (!Cadenas.EsVacia(((OCAPAsistenteOT) solicitudesOT.getListadoVocales().get(i)).getBSexo())) {
				if (Constantes.SEXO_HOMBRE_VALUE
						.equals(((OCAPAsistenteOT) solicitudesOT.getListadoVocales().get(i)).getBSexo())) {
					((OCAPAsistenteOT) solicitudesOT.getListadoVocales().get(i)).setDNombreApellidos(
							"D. " + ((OCAPAsistenteOT) solicitudesOT.getListadoVocales().get(i)).getDNombreApellidos());
				} else {
					((OCAPAsistenteOT) solicitudesOT.getListadoVocales().get(i)).setDNombreApellidos("Dña. "
							+ ((OCAPAsistenteOT) solicitudesOT.getListadoVocales().get(i)).getDNombreApellidos());
				}
			}
		}
		for (int i = 0; i < solicitudesOT.getListadoVocalesSuplentes().size(); i++) {
			if (!Cadenas.EsVacia(((OCAPAsistenteOT) solicitudesOT.getListadoVocalesSuplentes().get(i)).getBSexo())) {
				if (Constantes.SEXO_HOMBRE_VALUE
						.equals(((OCAPAsistenteOT) solicitudesOT.getListadoVocalesSuplentes().get(i)).getBSexo())) {
					((OCAPAsistenteOT) solicitudesOT.getListadoVocalesSuplentes().get(i)).setDNombreApellidos(
							"D. " + ((OCAPAsistenteOT) solicitudesOT.getListadoVocalesSuplentes().get(i))
									.getDNombreApellidos());
				} else {
					((OCAPAsistenteOT) solicitudesOT.getListadoVocalesSuplentes().get(i)).setDNombreApellidos(
							"Dña. " + ((OCAPAsistenteOT) solicitudesOT.getListadoVocalesSuplentes().get(i))
									.getDNombreApellidos());
				}
			}
		}
		if (!Cadenas.EsVacia(solicitudesOT.getBSexoSecre1())) {
			if (Constantes.SEXO_HOMBRE_VALUE.equals(solicitudesOT.getBSexoSecre1())) {
				solicitudesOT.setDNombreApellidosSecre1("D. " + solicitudesOT.getDNombreApellidosSecre1());
			} else {
				solicitudesOT.setDNombreApellidosSecre1("Dña. " + solicitudesOT.getDNombreApellidosSecre1());
			}
		}
		if (!Cadenas.EsVacia(solicitudesOT.getBSexoSecre2())) {
			if (Constantes.SEXO_HOMBRE_VALUE.equals(solicitudesOT.getBSexoSecre2())) {
				solicitudesOT.setDNombreApellidosSecre2("D. " + solicitudesOT.getDNombreApellidosSecre2());
			} else {
				solicitudesOT.setDNombreApellidosSecre2("Dña. " + solicitudesOT.getDNombreApellidosSecre2());
			}
		}
	}

	private void anhadirTratamientoInformeIndiviAccesoGrado(OCAPSolicitudesOT solicitudesOT) {
		if (!Cadenas.EsVacia(solicitudesOT.getBSexo())) {
			if (Constantes.SEXO_HOMBRE_VALUE.equals(solicitudesOT.getBSexo())) {
				solicitudesOT.setDNombreApellidos("D. " + solicitudesOT.getDNombreApellidos());
			} else {
				solicitudesOT.setDNombreApellidos("Dña. " + solicitudesOT.getDNombreApellidos());
			}
		}
		if (!Cadenas.EsVacia(solicitudesOT.getBSexoDtor())) {
			if (Constantes.SEXO_HOMBRE_VALUE.equals(solicitudesOT.getBSexoDtor())) {
				solicitudesOT.setDNombreApellidosDtor("D. " + solicitudesOT.getDNombreApellidosDtor());
			} else {
				solicitudesOT.setDNombreApellidosDtor("Dña. " + solicitudesOT.getDNombreApellidosDtor());
			}
		}
	}

	private void anhadirTratamientoInformeReconociGrado(OCAPSolicitudesOT solicitudesOT) {
		if (!Cadenas.EsVacia(solicitudesOT.getBSexo())) {
			if (Constantes.SEXO_HOMBRE_VALUE.equals(solicitudesOT.getBSexo())) {
				solicitudesOT.setDNombreApellidos("D. " + solicitudesOT.getDNombreApellidos());
			} else {
				solicitudesOT.setDNombreApellidos("Dña. " + solicitudesOT.getDNombreApellidos());
			}
		}
	}

	private void anhadirEnCalidadActaConstitucion(OCAPSolicitudesOT solicitudesOT) {
		if (!Cadenas.EsVacia(solicitudesOT.getBEnCalidadPresi1())) {
			if ("T".equals(solicitudesOT.getBEnCalidadPresi1())) {
				solicitudesOT.setDNombreApellidosPresi1(solicitudesOT.getDNombreApellidosPresi1() + " (Titular)");
			} else {
				solicitudesOT.setDNombreApellidosPresi1(solicitudesOT.getDNombreApellidosPresi1() + " (Suplente)");
			}
		}
		if (!Cadenas.EsVacia(solicitudesOT.getBEnCalidadPresi2())) {
			if ("T".equals(solicitudesOT.getBEnCalidadPresi2())) {
				solicitudesOT.setDNombreApellidosPresi2(solicitudesOT.getDNombreApellidosPresi2() + " (Titular)");
			} else {
				solicitudesOT.setDNombreApellidosPresi2(solicitudesOT.getDNombreApellidosPresi2() + " (Suplente)");
			}
		}
		if (!Cadenas.EsVacia(solicitudesOT.getBEnCalidadSecre1())) {
			if ("T".equals(solicitudesOT.getBEnCalidadSecre1())) {
				solicitudesOT.setDNombreApellidosSecre1(solicitudesOT.getDNombreApellidosSecre1() + " (Titular)");
			} else {
				solicitudesOT.setDNombreApellidosSecre1(solicitudesOT.getDNombreApellidosSecre1() + " (Suplente)");
			}
		}
		if (!Cadenas.EsVacia(solicitudesOT.getBEnCalidadSecre2())) {
			if ("T".equals(solicitudesOT.getBEnCalidadSecre2())) {
				solicitudesOT.setDNombreApellidosSecre2(solicitudesOT.getDNombreApellidosSecre2() + " (Titular)");
			} else {
				solicitudesOT.setDNombreApellidosSecre2(solicitudesOT.getDNombreApellidosSecre2() + " (Suplente)");
			}
		}
	}

	public void crearInformeListado(HttpServletResponse response, ArrayList listadoSolicitudes, String pathBase,
			String estado, String fase, String gerencia, String opcion, JCYLUsuario jcylUsuario, String orden, Long convocatoriaId)
			throws Exception {
		String nombreReportPadre = null;
		String nombreSubreport = null;
		Hashtable parametros = null;
		JasperReport jasperReport = null;
		JasperReport jasperSubReport = null;
		JasperPrint jasperPrint = null;
		ConceptoReport matrizDatosReport = null;
		Boolean marcaExclusion=Boolean.FALSE;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeListado: Inicio");
			boolean bDenegados = false;
			if ((Constantes.FASE_INICIACION.equals(fase)) || (Constantes.FASE_VALIDACION_LISTADOS.equals(fase))) {
				nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados"
						+ File.separator + "informeListadoFaseI" + ".jasper";
			
				if (orden != null && orden.equals("A")) {
					
					if (Constantes.FASE_INICIACION.equals(fase)  && (Constantes.ESTADO_EXCLUIDO_VALUE.equals(estado))){
						nombreSubreport = pathBase + File.separator + "reports" + File.separator + "compilados"
								+ File.separator + "subinformeListadoSinGroupExcluido" + ".jasper";
					}else{
						nombreSubreport = pathBase + File.separator + "reports" + File.separator + "compilados"
								+ File.separator + "subinformeListadoSinGroup" + ".jasper";	
					}
					
					
				} else {
					
					if (Constantes.FASE_INICIACION.equals(fase)  && (Constantes.ESTADO_EXCLUIDO_VALUE.equals(estado))){
						nombreSubreport = pathBase + File.separator + "reports" + File.separator + "compilados"
								+ File.separator + "subinformeListadoExcluido" + ".jasper";
					}else{
						nombreSubreport = pathBase + File.separator + "reports" + File.separator + "compilados"
								+ File.separator + "subinformeListado" + ".jasper";
					}
					
					
				}
				if ((Long.toString(4).equals(estado)) || (Constantes.ESTADO_EXCLUIDO_VALUE.equals(estado))) {
					bDenegados = true;
					if (Constantes.FASE_INICIACION.equals(fase)) {
						marcaExclusion=Boolean.TRUE;
					}
					
				} else if ((estado != null) && (estado.equals(5 + ""))) {
					bDenegados = true;
				}
			} else {
				if ((estado != null) && (estado.equals("A"))) {
					nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados"
							+ File.separator + "informeListadoMCAceptadas" + ".jasper";
				} else if ((estado != null) && (estado.equals("D"))) {
					nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados"
							+ File.separator + "informeListadoMCDenegadas" + ".jasper";
				} else if ((estado != null) && (estado.equals(Constantes.ESTADO_DESESTIMADO_VALUE))) {
					nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados"
							+ File.separator + "informeListadoMCDenegadas" + ".jasper";
				}
				nombreSubreport = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
						+ "informeSubListado" + ".jasper";
			}
			File fichReportPadre = new File(nombreReportPadre);
			jasperReport = (JasperReport) JRLoader.loadObject(fichReportPadre);

			File fichSubreport = new File(nombreSubreport);
			jasperSubReport = (JasperReport) JRLoader.loadObject(fichSubreport);

			parametros = new Hashtable();
			parametros.put("ruta", pathBase);
			parametros.put("gerencia", gerencia);
			parametros.put("SUB_REPORT", jasperSubReport);					
			parametros.put("excluidos",marcaExclusion);

			jasperSubReport = null;

			matrizDatosReport = new ConceptoReport();
			if ((Constantes.FASE_INICIACION.equals(fase)) || (Constantes.FASE_VALIDACION_LISTADOS.equals(fase))) {
				if ((Long.toString(3).equals(estado)) || ("A".equals(estado))) {
					matrizDatosReport = generarTablaSolicitudesAceptadas(listadoSolicitudes, opcion, fase, jcylUsuario);
				} else {
					matrizDatosReport = generarTablaSolicitudesExcluidas(listadoSolicitudes, opcion, fase, jcylUsuario);
					OCAPMotExclusionLN motivosExcLN = new OCAPMotExclusionLN(jcylUsuario);
					String listaMotivosExclusion = motivosExcLN.listarMotivosExclusionCompleto(convocatoriaId);
					parametros.put("listaCausasExc",listaMotivosExclusion);
					
				}
			} else {
				matrizDatosReport = generarTabla(listadoSolicitudes, bDenegados);
			}
			if (matrizDatosReport == null) {
				parametros.put("reportVacio", "NO HAY DATOS COINCIDENTES");
			} else {
				parametros.put("datosReport", matrizDatosReport);
			}
			matrizDatosReport = null;

			jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);

			parametros = null;
			jasperReport = null;
			jasperPrint = null;

			OutputStream out = response.getOutputStream();
			if ((estado != null) && (estado.equals(3 + ""))) {
				response.setHeader("Content-disposition", "attachment; filename=\"informeListadoAceptadas.pdf\"");
			} else if ((estado != null) && (estado.equals(4 + ""))) {
				response.setHeader("Content-disposition", "attachment; filename=\"informeListadoExcluidas.pdf\"");
			} else if ((estado != null) && (estado.equals(5 + ""))) {
				response.setHeader("Content-disposition", "attachment; filename=\"informeListadoDesistidas.pdf\"");
			} else {
				response.setHeader("Content-disposition", "attachment; filename=\"informeListado.pdf\"");
			}
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();

			OCAPConfigApp.logger.info("crearInformeListado Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw new Exception("Informe NO generado");
		}
	}

	public void crearCSVListado(HttpServletResponse response, ArrayList listadoSolicitudes, String pathBase,
			String solicitud, String fase, String gerencia, JCYLUsuario jcylUsuario) throws Exception {
		String cadena = "";
		try {
			OCAPConfigApp.logger.info(
					(new StringBuilder()).append(getClass().getName()).append(" crearCSVListado: Inicio").toString());
			boolean bDenegados = false;
			if (Constantes.FASE_INICIACION.equals(fase)) {
				if ((solicitud == null || !solicitud.equals("A"))
						&& (solicitud == null || !solicitud.equals("D") && !solicitud.equals(Constantes.ESTADO_EXCLUIDO_VALUE)) && solicitud != null)
					if (!solicitud.equals(Constantes.ESTADO_DESESTIMADO_VALUE))
						;
			} else if ((solicitud == null || !solicitud.equals("A")) && (solicitud == null || !solicitud.equals("D"))
					&& solicitud != null)
				if (!solicitud.equals(Constantes.ESTADO_DESESTIMADO_VALUE))
					;
			OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
					.append(" crearCSVListado: Calcular par\341metros").toString());
			if (Constantes.FASE_INICIACION.equals(fase))
				if (solicitud != null && solicitud.equals("A"))
					cadena = generarCSVSolicitudesAceptadas(listadoSolicitudes, jcylUsuario);
				else
					cadena = generarCSVSolicitudesExcluidas(listadoSolicitudes, jcylUsuario);
			OutputStream out = response.getOutputStream();
			if (solicitud != null && solicitud.equals("A"))
				response.setHeader("Content-Disposition", "attachment; filename=\"informeAceptadas.csv\"");
			else
				response.setHeader("Content-Disposition", "attachment; filename=\"informeExcluidas.csv\"");
			
			response.setContentType("text/csv;charset=ISO_8859_1");
			response.setHeader("Pragma", "no-cache");
			cadena = cadena.replaceAll("<\241>SALTO_LINEA<!>", "\n");
			byte bytes[] = cadena.getBytes(StandardCharsets.ISO_8859_1);
			response.setContentLength(bytes.length);
			OutputStreamWriter outWriter = new OutputStreamWriter(out, StandardCharsets.ISO_8859_1);
			outWriter.write(cadena, 0, cadena.length());
			outWriter.flush();
			outWriter.close();
			out.close();
			OCAPConfigApp.logger.info(
					(new StringBuilder()).append(getClass().getName()).append(" crearCSVListado: Fin").toString());
		} catch (Exception e) {
			OCAPConfigApp.logger.error((new StringBuilder()).append(getClass().getName())
					.append(" crearCSVListado: ERROR").append(e.getMessage()).toString());
			throw new Exception("CSV NO generado");
		}
	}

	public void crearCSVDinamicoListado(HttpServletResponse response, ArrayList listadoSolicitudes, String pathBase,
			JCYLUsuario jcylUsuario) throws Exception {
		String cadena = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearCSVDinamicoListado: Inicio");

			cadena = generarCSVDinamicoSolicitudes(listadoSolicitudes, jcylUsuario);

			OutputStream out = response.getOutputStream();
			response.setHeader("Content-Disposition", "attachment; filename=\"informeDinamico.csv\"");
			response.setContentType("text/csv;charset=ISO_8859_1");
			response.setHeader("Pragma", "no-cache");

			cadena = cadena.replaceAll(Constantes.SALTO_LINEA, "\n");
			byte[] bytes = cadena.getBytes(StandardCharsets.ISO_8859_1);
			response.setContentLength(bytes.length);
			OutputStreamWriter outWriter = new OutputStreamWriter(out, StandardCharsets.ISO_8859_1);
			outWriter.write(cadena, 0, cadena.length());
			outWriter.flush();
			outWriter.close();
			out.close();
   
			OCAPConfigApp.logger.info(getClass().getName() + " crearCSVDinamicoListado: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw new Exception("CSV NO generado");
		}
	}

	public ConceptoReport generarTabla(ArrayList datos, boolean bDenegados) throws Exception {
		ConceptoReport report = null;
		OCAPSolicitudesOT solicitudesOT = null;
		OCAPSolicitudesOT subsolicitudesOT = null;
		OCAPSolicitudesOT subsubsolicitudesOT = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarTabla: Inicio");
			if (datos.size() != 0) {
				report = new ConceptoReport();
			}
			for (int i = 0; i < datos.size(); i++) {
				solicitudesOT = (OCAPSolicitudesOT) datos.get(i);
				report.addRow();
				report.putElement("col12", solicitudesOT.getDGrado_des().toUpperCase());
				ArrayList subListado = solicitudesOT.getListaSolicitudes();
				for (int j = 0; j < subListado.size(); j++) {
					subsolicitudesOT = new OCAPSolicitudesOT();
					subsolicitudesOT = (OCAPSolicitudesOT) subListado.get(j);
					report.addRow();
					if (subsolicitudesOT.getDEspec_nombre() != null) {
						subsolicitudesOT.setDEspec_nombre(subsolicitudesOT.getDEspec_nombre().trim());
					}
					if ((subsolicitudesOT.getDEspec_nombre() != null)
							&& (!subsolicitudesOT.getDEspec_nombre().equals(""))) {
						report.putElement("col11", subsolicitudesOT.getDProfesional_nombre() + "/"
								+ subsolicitudesOT.getDEspec_nombre() + "");
					} else {
						report.putElement("col11", subsolicitudesOT.getDProfesional_nombre());
					}
					report.addRow();

					report.putElement("col2cabecera", "Apellidos");
					report.putElement("col4cabecera", "Nombre");
					if (bDenegados) {
						report.putElement("col3cabecera", "En Plazo");
						report.putElement("col6cabecera", "Consta Antigüedad");
						report.putElement("col7cabecera", "Consta Prop.Plaza");
						report.putElement("col8cabecera", "Datos Correctos");
						report.putElement("col9cabecera", "Consta Trabajo Otros Centros");
					}
					ArrayList subSubListado = subsolicitudesOT.getListaSolicitudes();
					for (int k = 0; k < subSubListado.size(); k++) {
						subsubsolicitudesOT = new OCAPSolicitudesOT();
						subsubsolicitudesOT = (OCAPSolicitudesOT) subSubListado.get(k);
						report.addRow();
						report.putElement("col11", "");

						report.putElement("col2", subsubsolicitudesOT.getDApellido1());

						report.putElement("col4", subsubsolicitudesOT.getDNombre());
						if (bDenegados) {
							if (Constantes.SI.equals(subsubsolicitudesOT.getBInconf_antiguedad())) {
								report.putElement("col6", Constantes.NO_TEXTO_min);
							} else {
								report.putElement("col6", Constantes.SI_TEXTO_min);
							}
							if (Constantes.SI.equals(subsubsolicitudesOT.getBInconf_plazaprop())) {
								report.putElement("col7", Constantes.NO_TEXTO_min);
							} else {
								report.putElement("col7", Constantes.SI_TEXTO_min);
							}
							if (Constantes.SI.equals(subsubsolicitudesOT.getBPersonales())) {
								report.putElement("col8", Constantes.NO_TEXTO_min);
							} else {
								report.putElement("col8", Constantes.SI_TEXTO_min);
							}
							if (subsubsolicitudesOT.getBOtrosCentros() != null) {
								if (Constantes.SI.equals(subsubsolicitudesOT.getBOtrosCentros())) {
									report.putElement("col9", Constantes.NO_TEXTO_min);
								} else {
									report.putElement("col9", Constantes.SI_TEXTO_min);
								}
							} else {
								report.putElement("col9", "-");
							}
							if (Constantes.SI.equals(subsubsolicitudesOT.getBPlazo())) {
								report.putElement("col3", Constantes.NO_TEXTO_min);
							} else {
								report.putElement("col3", Constantes.SI_TEXTO_min);
							}
						}
					}
				}
			}
			OCAPConfigApp.logger.info(getClass().getName() + " generarTabla: Fin");
			return report;
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " generarTabla: ERROR" + e.getMessage());
			throw e;
		}
	}

	public ConceptoReport generarDatosSubReportListadoVocales(ArrayList listadoVocales, JCYLUsuario jcylUsuario)
			throws Exception {
		ConceptoReport report = null;
		OCAPAsistenteOT asistenteOT = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarDatosSubReportListadoVocales: Inicio");
			if (listadoVocales.size() != 0) {
				report = new ConceptoReport();
			}
			for (int i = 0; i < listadoVocales.size(); i++) {
				asistenteOT = (OCAPAsistenteOT) listadoVocales.get(i);
				report.addRow();
				report.putElement("dNombreApellidos", asistenteOT.getDNombreApellidos());
			}
			OCAPConfigApp.logger.info(getClass().getName() + " generarDatosSubReportListadoVocales: Fin");

			return report;
		} catch (Exception e) {
			OCAPConfigApp.logger
					.info(getClass().getName() + " generarDatosSubReportListadoVocales: ERROR" + e.getMessage());
			throw e;
		}
	}

	public ConceptoReport generarDatosSubReportListadoUsuarios(ArrayList listadoUsuarios, JCYLUsuario jcylUsuario)
			throws Exception {
		ConceptoReport report = null;
		OCAPUsuarioOT usuarioOT = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarDatosSubReportListadoUsuarios: Inicio");
			if (listadoUsuarios.size() != 0) {
				report = new ConceptoReport();
			}
			for (int i = 0; i < listadoUsuarios.size(); i++) {
				usuarioOT = (OCAPUsuarioOT) listadoUsuarios.get(i);
				report.addRow();
				report.putElement("dNombreApellidos", usuarioOT.getDNombreApellidos());
				report.putElement("dDNI", usuarioOT.getDNIF());
				report.putElement("dAccion", usuarioOT.getDAccion());
			}
			OCAPConfigApp.logger.info(getClass().getName() + " generarDatosSubReportListadoUsuarios: Fin");

			return report;
		} catch (Exception e) {
			OCAPConfigApp.logger
					.info(getClass().getName() + " generarDatosSubReportListadoUsuarios: ERROR" + e.getMessage());
			throw e;
		}
	}

	public ConceptoReport generarTablaSolicitudesAceptadas(ArrayList datos, String opcion, String fase,
			JCYLUsuario jcylUsuario) throws Exception {
		ConceptoReport report = null;
		OCAPSolicitudesOT solicitudesOT = null;
		String nombreCentros = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarTablaSolicitudesAceptadas: Inicio");
			if (datos.size() != 0) {
				report = new ConceptoReport();
			}
			for (int i = 0; i < datos.size(); i++) {
				solicitudesOT = new OCAPSolicitudesOT();
				solicitudesOT = (OCAPSolicitudesOT) datos.get(i);
				report.addRow();
				if ("P".equals(opcion)) {
					report.putElement("definitiva", " PROVISIONAL");
				} else {
					report.putElement("definitiva", " DEFINITIVA");
				}
				if (Constantes.FASE_INICIACION.equals(fase)) {
					report.putElement("fase", "SOLICITUDES ACEPTADAS");
				} else {
					report.putElement("fase", "PROFESIONALES ACEPTADOS");
				}
				if (solicitudesOT.getOrden() != null && !solicitudesOT.getOrden().equals("")) {
					report.putElement("ordenFijo", solicitudesOT.getOrden());
				}
				report.putElement("dGrado_des", solicitudesOT.getDGrado_des());
				report.putElement("dConvocatoria", solicitudesOT.getDConvocatoria());
				report.putElement("fConvocatoria", solicitudesOT.getFConvocatoria());
				if ((solicitudesOT.getCodGerencia() != null) && ("GRS".equals(solicitudesOT.getCodGerencia()))) {
					nombreCentros = generarCadenaCentrosGerencia("GRS", jcylUsuario);
					if (nombreCentros.equals("")) {
						report.putElement("dGerencia_nombre", solicitudesOT.getDGerencia_nombre() + ".");
					} else {
						report.putElement("dGerencia_nombre", nombreCentros);
					}
				} else {
					report.putElement("dGerencia_nombre", solicitudesOT.getDGerencia_nombre() + ".");
				}
				report.putElement("dApellido1", solicitudesOT.getDApellido1());
				report.putElement("dNombre", solicitudesOT.getDNombre());
				report.putElement("dModalidad", solicitudesOT.getDModalidad());
				report.putElement("dProfesional_nombre", solicitudesOT.getDProfesional_nombre());
				report.putElement("dEspec_nombre", solicitudesOT.getDEspec_nombre());
				report.putElement("dSitAdministrativaAux_nombre", solicitudesOT.getDSitAdministrativaAux_nombre());
				report.putElement("dDni", generarDniEnmascarado(solicitudesOT.getCDniReal()));
			}
			OCAPConfigApp.logger.info(getClass().getName() + " generarTablaSolicitudesAceptadas: Fin");
			return report;
		} catch (Exception e) {
			OCAPConfigApp.logger
					.info(getClass().getName() + " generarTablaSolicitudesAceptadas: ERROR" + e.getMessage());
			throw e;
		}
	}

	private Object generarDniEnmascarado(String cDniReal) {
		String dniEnmascarado = "";
		if(cDniReal != null && !cDniReal.isEmpty() && cDniReal.length() == 9){
			String digitos = cDniReal.substring(0, 6);
			dniEnmascarado = digitos+ "***";
		}else if(cDniReal != null && !cDniReal.isEmpty()){
			while (cDniReal.length()<9){
				cDniReal = "0"+cDniReal;
			}
			String digitos = cDniReal.substring(0, 6);
			dniEnmascarado = digitos+ "***";
		}
		return dniEnmascarado;
	}

	public ConceptoReport generarTablaSolicitudesExcluidas(ArrayList datos, String opcion, String fase,
			JCYLUsuario jcylUsuario) throws Exception {
		ConceptoReport report = null;
		OCAPSolicitudesOT solicitudesOT = null;

		String nombreCentros = "";
		OCAPMotExclusionLN motExclusionLN = null;
		int numDatos = 0;
		try {
			OCAPConfigApp.logger.info("generarTablaSolicitudesExcluidas: Inicio");

			numDatos = datos != null ? datos.size() : 0;
			if (numDatos != 0) {
				report = new ConceptoReport();
			}
			motExclusionLN = new OCAPMotExclusionLN(jcylUsuario);
			for (int i = 0; i < numDatos; i++) {
				solicitudesOT = (OCAPSolicitudesOT) datos.get(i);
				report.addRow();
				if ("P".equals(opcion)) {
					report.putElement("definitiva", " PROVISIONAL");
				} else {
					report.putElement("definitiva", " DEFINITIVA");
				}
				if (Constantes.FASE_INICIACION.equals(fase)) {
					report.putElement("fase", "SOLICITUDES EXCLUIDAS");
				} else {
					report.putElement("fase", "PROFESIONALES EXCLUIDOS");
				}
				report.putElement("dGrado_des", solicitudesOT.getDGrado_des());
				report.putElement("dConvocatoria", solicitudesOT.getDConvocatoria());
				report.putElement("fConvocatoria", solicitudesOT.getFConvocatoria());
				if ((solicitudesOT.getCodGerencia() != null) && ("GRS".equals(solicitudesOT.getCodGerencia()))) {
					nombreCentros = generarCadenaCentrosGerencia("GRS", jcylUsuario);
					if (nombreCentros.equals("")) {
						report.putElement("dGerencia_nombre", solicitudesOT.getDGerencia_nombre() + ".");
					} else {
						report.putElement("dGerencia_nombre", nombreCentros);
					}
				} else {
					report.putElement("dGerencia_nombre", solicitudesOT.getDGerencia_nombre() + ".");
				}
				report.putElement("dApellido1", solicitudesOT.getDApellido1());
				report.putElement("dNombre", solicitudesOT.getDNombre());
				report.putElement("dModalidad", solicitudesOT.getDModalidad());
				report.putElement("dProfesional_nombre", solicitudesOT.getDProfesional_nombre());
				report.putElement("dEspec_nombre", solicitudesOT.getDEspec_nombre());
				report.putElement("dSitAdministrativaAux_nombre", solicitudesOT.getDSitAdministrativaAux_nombre());
				report.putElement("dDni", generarDniEnmascarado(solicitudesOT.getCDniReal()));
				if (Constantes.FASE_INICIACION.equals(fase)) {
					report.putElement("causasExclusion",
							motExclusionLN.buscarCodigoMotivosExpediente(solicitudesOT.getCExp_id()));
				} else {
					report.putElement("causasExclusion", obtenerMotExclusionFaseII(solicitudesOT, opcion, jcylUsuario));
				}
			}
			OCAPConfigApp.logger.info("generarTablaSolicitudesExcluidas: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
		return report;
	}

	private String obtenerMotExclusionFaseII(OCAPSolicitudesOT solicitudesOT, String opcion, JCYLUsuario jcylUsuario)
			throws Exception {
		ArrayList listaCreditosNoAlcanzados = new ArrayList();
		OCAPCreditosOT creditosOT = null;
		OCAPUsuariosOT usuariosOT = null;
		try {
			String cDni = solicitudesOT.getCDni();
			OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);
			usuariosOT = usuariosLN.datosPersonalesUsuario(cDni, solicitudesOT.getCExp_id(), jcylUsuario);
			OCAPCreditosLN creditosLN = new OCAPCreditosLN(jcylUsuario);
			ArrayList listaCreditos = creditosLN.buscarOCAPCreditosPorCategProfesional(usuariosOT,
					new Integer((int) usuariosOT.getCGrado_id()), "", jcylUsuario);
			listaCreditos.remove(0);
			for (int k = 0; k < listaCreditos.size(); k++) {
				creditosOT = (OCAPCreditosOT) listaCreditos.get(k);
				if ("P".equals(opcion)) {
					if (creditosOT.getNCreditosValidadosCeis().doubleValue() < creditosOT.getNCreditos()) {
						listaCreditosNoAlcanzados.add(creditosOT);
					}
				} else if (creditosOT.getNCreditosValidadosCc().doubleValue() < creditosOT.getNCreditos()) {
					listaCreditosNoAlcanzados.add(creditosOT);
				}
			}
			String cadenaCreditos = "No alcanza los créditos en ";
			if (listaCreditosNoAlcanzados.size() > 1) {
				cadenaCreditos = cadenaCreditos + "las áreas de ";
			}
			for (int k = 0; k < listaCreditosNoAlcanzados.size() - 2; k++) {
				creditosOT = (OCAPCreditosOT) listaCreditosNoAlcanzados.get(k);
				cadenaCreditos = cadenaCreditos + creditosOT.getDMerito() + ", ";
			}
			if (listaCreditosNoAlcanzados.size() > 1) {
				creditosOT = (OCAPCreditosOT) listaCreditosNoAlcanzados.get(listaCreditosNoAlcanzados.size() - 2);
				OCAPCreditosOT creditosOT2 = (OCAPCreditosOT) listaCreditosNoAlcanzados
						.get(listaCreditosNoAlcanzados.size() - 1);
				if (creditosOT2.getDMerito().toUpperCase().startsWith("I")) {
					cadenaCreditos = cadenaCreditos + creditosOT.getDMerito() + " e " + creditosOT2.getDMerito();
				} else {
					cadenaCreditos = cadenaCreditos + creditosOT.getDMerito() + " y " + creditosOT2.getDMerito();
				}
			} else if (listaCreditosNoAlcanzados.size() == 1) {
				creditosOT = (OCAPCreditosOT) listaCreditosNoAlcanzados.get(0);
				cadenaCreditos = cadenaCreditos + "el área de " + creditosOT.getDMerito();
			} else {
				cadenaCreditos = cadenaCreditos + "el área de ..........";
			}
			return cadenaCreditos;
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
	}

	public String generarCSVSolicitudesAceptadas(ArrayList datos, JCYLUsuario jcylUsuario) throws Exception {
		StringBuffer cadenaDatos = new StringBuffer();
		OCAPSolicitudesOT solicitudesOT = null;
		String nombreCentros = "";
		String gerenciaAnt = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarCSVSolicitudesAceptadas: Inicio");
			for (int i = 0; i < datos.size(); i++) {
				solicitudesOT = new OCAPSolicitudesOT();
				solicitudesOT = (OCAPSolicitudesOT) datos.get(i);
				if (i == 0) {
					cadenaDatos.append(
							"CONVOCATORIA DE FECHA " + solicitudesOT.getFConvocatoria() + ";" + Constantes.SALTO_LINEA);
					cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDGrado_des().toUpperCase()) + ";"
							+ Constantes.SALTO_LINEA);
				}
				if (!gerenciaAnt.equals(solicitudesOT.getDGerencia_nombre())) {
					gerenciaAnt = solicitudesOT.getDGerencia_nombre();

					cadenaDatos.append(Constantes.SALTO_LINEA);
					cadenaDatos.append(Constantes.SALTO_LINEA);
					cadenaDatos.append(Constantes.SALTO_LINEA);
					if ((solicitudesOT.getCodGerencia() != null) && ("GRS".equals(solicitudesOT.getCodGerencia()))) {
						nombreCentros = generarCadenaCentrosGerencia("GRS", jcylUsuario);
						if (nombreCentros.equals("")) {
							cadenaDatos
									.append("GERENCIA:;" + Cadenas.formatearParaCSV(solicitudesOT.getDGerencia_nombre())
											+ ";" + Constantes.SALTO_LINEA);
						} else {
							cadenaDatos.append("GERENCIA:;" + Cadenas.formatearParaCSV(nombreCentros) + ";"
									+ Constantes.SALTO_LINEA);
						}
					} else {
						cadenaDatos.append("GERENCIA:;" + Cadenas.formatearParaCSV(solicitudesOT.getDGerencia_nombre())
								+ ";" + Constantes.SALTO_LINEA);
					}
					cadenaDatos.append(Constantes.SALTO_LINEA);
					cadenaDatos.append("APELLIDOS;NOMBRE;CUERPO/CATEGORÍA;ESPECIALIDAD;SITUACIÓN ADMINISTRATIVA;")
							.append(Constantes.SALTO_LINEA);
				}
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDApellido1()) + ";");
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDNombre()) + ";");
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDProfesional_nombre()) + ";");
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDEspec_nombre()) + ";");
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDSitAdministrativaAux_nombre()) + ";");
				cadenaDatos.append(Constantes.SALTO_LINEA);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " generarCSVSolicitudesAceptadas: Fin");
			return cadenaDatos.toString();
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " generarCSVSolicitudesAceptadas: ERROR" + e.getMessage());
			throw e;
		}
	}

	public String generarCSVDinamicoSolicitudesAceptadas(ArrayList datos, JCYLUsuario jcylUsuario) throws Exception {
		StringBuffer cadenaDatos = new StringBuffer();
		OCAPSolicitudesOT solicitudesOT = null;
		String nombreCentros = "";
		String gerenciaAnt = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarCSVDinamicoSolicitudesAceptadas: Inicio");
			for (int i = 0; i < datos.size(); i++) {
				solicitudesOT = new OCAPSolicitudesOT();
				solicitudesOT = (OCAPSolicitudesOT) datos.get(i);
				if (i == 0) {
					cadenaDatos.append(
							"CONVOCATORIA DE FECHA " + solicitudesOT.getFConvocatoria() + ";" + Constantes.SALTO_LINEA);
					cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDGrado_des().toUpperCase()) + ";"
							+ Constantes.SALTO_LINEA);

					cadenaDatos.append(Constantes.SALTO_LINEA);
					cadenaDatos.append(construirCabecera(solicitudesOT));
					cadenaDatos.append(Constantes.SALTO_LINEA);
				}
				cadenaDatos.append(construirDatos(solicitudesOT));

				cadenaDatos.append(Constantes.SALTO_LINEA);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " generarCSVDinamicoSolicitudesAceptadas: Fin");
			return cadenaDatos.toString();
		} catch (Exception e) {
			OCAPConfigApp.logger
					.info(getClass().getName() + " generarCSVDinamicoSolicitudesAceptadas: ERROR" + e.getMessage());
			throw e;
		}
	}

	public StringBuffer construirCabecera(OCAPSolicitudesOT solicitudesOT) {
		StringBuffer cadenaDatos = new StringBuffer();
		if (solicitudesOT.getBEpr() == true) {
			cadenaDatos.append("EPR;");
		}
		if (solicitudesOT.getBNifNie() == true) {
			cadenaDatos.append("NIF / NIE;");
		}
		if (solicitudesOT.getBNifNie() == true) {
			cadenaDatos.append("NIF / NIE OCULTO;");
		}
		if (solicitudesOT.getBApellidos() == true) {
			cadenaDatos.append("APELLIDOS;");
		}
		if (solicitudesOT.getBNombre() == true) {
			cadenaDatos.append("NOMBRE;");
		}
		if (solicitudesOT.getBCSVSexo() == true) {
			cadenaDatos.append("SEXO;");
		}
		if (solicitudesOT.getBCategoria() == true) {
			cadenaDatos.append("CUERPO / CATEGORIA;");
		}
		if (solicitudesOT.getBEspecialidad() == true) {
			cadenaDatos.append("ESPECIALIDAD;");
		}
		if (solicitudesOT.getBSituacionAdm() == true) {
			cadenaDatos.append("SITUACIÓN ADMINISTRATIVA;");
		}
		if (solicitudesOT.getBModalidad() == true) {
			cadenaDatos.append("MODALIDAD;");
		}
		if (solicitudesOT.getBProcedimiento() == true) {
			cadenaDatos.append("PROCEDIMIENTO DE EVALUACIÓN POR EL QUE OPTA;");
		}
		if (solicitudesOT.getBGerencia() == true) {
			cadenaDatos.append("GERENCIA;");
		}
		if (Constantes.FASE_RECONOCIMIENTO_CC.equals(solicitudesOT.getCFase())) {
			cadenaDatos.append("GRADO;");
		}
		if (solicitudesOT.getBConvocatoria() == true) {
			cadenaDatos.append("CONVOCATORIA;");
		}
		cadenaDatos.append("GRADO SOLICITADO;");
		if (solicitudesOT.getBFechaSolic() == true) {
			cadenaDatos.append("FECHA DE SOLICITUD;");
		}
		if (solicitudesOT.getBJuridico() == true) {
			cadenaDatos.append("RÉGIMEN JURÍDICO;");
		}
		if (solicitudesOT.getBAnios() == true) {
			cadenaDatos.append("AÑOS EJERCICIO;");
		}
		if (solicitudesOT.getBCentro() == true) {
			cadenaDatos.append("CENTRO;");
		}
		if (solicitudesOT.getBServicio() == true) {
			cadenaDatos.append("SERVICIO;");
		}
		if (solicitudesOT.getBPuesto() == true) {
			cadenaDatos.append("PUESTO;");
		}
		if (solicitudesOT.getBEstado() == true) {
			cadenaDatos.append("ESTADO;");
		}
		if ((solicitudesOT.getBCausas() == true) && (!"A".equals(solicitudesOT.getCEstado()))
				&& (!"P".equals(solicitudesOT.getCEstado()))) {
			cadenaDatos.append("CAUSAS EXCLUSIÓN;");
		}
		cadenaDatos.append("FECHA DE RENUNCIA;");

		return cadenaDatos;
	}

	public StringBuffer construirDatos(OCAPSolicitudesOT solicitudesOT) throws Exception {
		StringBuffer cadenaDatos = new StringBuffer();
		try {
			if (solicitudesOT.getBNifNie() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getCDniReal()) + ";");
			}
			if (solicitudesOT.getBApellidos() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDApellido1()) + ";");
			}
			if (solicitudesOT.getBNombre() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDNombre()) + ";");
			}
			if (solicitudesOT.getBCSVSexo() == true) {
				if (solicitudesOT.getBSexo().equals(Constantes.SEXO_HOMBRE_VALUE)) {
					cadenaDatos.append(Cadenas.formatearParaCSV(Constantes.SEXO_HOMBRE) + ";");
				} else if (solicitudesOT.getBSexo().equals(Constantes.SEXO_MUJER_VALUE)) {
					cadenaDatos.append(Cadenas.formatearParaCSV(Constantes.SEXO_MUJER) + ";");
				}
			}
			if (solicitudesOT.getBCategoria() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDProfesional_nombre()) + ";");
			}
			if (solicitudesOT.getBEspecialidad() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDEspec_nombre()) + ";");
			}
			if (solicitudesOT.getBSituacionAdm() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDSitAdministrativaAux_nombre()) + ";");
			}
			if (solicitudesOT.getBProcedimiento() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDProcedimiento()) + ";");
			}
			if (solicitudesOT.getBGerencia() == true) {
				if ((solicitudesOT.getCodGerencia() != null)
						&& (Constantes.COD_LDAP_GRS.equals(solicitudesOT.getCodGerencia()))) {
					String nombreCentros = "";
					nombreCentros = generarCadenaCentrosGerencia(Constantes.COD_LDAP_GRS, this.jcylUsuario);
					if (nombreCentros.equals("")) {
						cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDGerencia_nombre()) + ";");
					} else {
						cadenaDatos.append(Cadenas.formatearParaCSV(nombreCentros) + ";");
					}
				} else {
					cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDGerencia_nombre()) + ";");
				}
			}
			if (solicitudesOT.getBConvocatoria() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDConvocatoria()) + ";");
			}
			if (solicitudesOT.getBFechaSolic() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getFRegistro_solic().toString()) + ";");
			}
			if (solicitudesOT.getBJuridico() == true) {
				if ("1".equals(solicitudesOT.getCJuridico())) {
					solicitudesOT.setCJuridico("Estatutario Fijo");
				} else if ("2".equals(solicitudesOT.getCJuridico())) {
					solicitudesOT.setCJuridico("Funcionario Sanitario Fijo");
				} else if ("3".equals(solicitudesOT.getCJuridico())) {
					solicitudesOT.setCJuridico("Otros");
				}
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getCJuridico()) + ";");
			}
			if (solicitudesOT.getBAnios() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(String.valueOf(solicitudesOT.getNAniosantiguedad())) + ";");
			}
			if (solicitudesOT.getBCentro() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDCentrotrabajo_nombre()) + ";");
			}
			if (Constantes.FASE_RECONOCIMIENTO_CC.equals(solicitudesOT.getCFase())) {
				if (Constantes.SI.equals(solicitudesOT.getBReconocimientoGrado())) {
					cadenaDatos.append(Constantes.SI_TEXTO);
				} else {
					cadenaDatos.append(Constantes.NO_TEXTO);
				}
			}
			if (solicitudesOT.getBEstado() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getCEstado()) + ";");
			}
			if ((solicitudesOT.getBCausas() == true) && (solicitudesOT.getDObservacionesEvidencia() != null)) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDObservacionesEvidencia()) + ";");
			}
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " construirDatos: ERROR" + e.getMessage());
			throw e;
		}
		return cadenaDatos;
	}

	public StringBuffer construirDatosCSV(OCAPSolicitudesOT solicitudesOT) throws Exception {
		StringBuffer cadenaDatos = new StringBuffer();
		try {
			if (solicitudesOT.getBEpr() == true) {
				DecimalFormat formato = new DecimalFormat("000000");
				cadenaDatos.append(Cadenas.formatearParaCSV(
						new StringBuilder().append("EPR").append(formato.format(solicitudesOT.getCExp_id())).toString())
						+ ";");
			}
			if (solicitudesOT.getBNifNie() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getCDniReal()) + ";");
			}
			if (solicitudesOT.getBNifNie() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getcDniEnmascarado()) + ";");
			}
			if (solicitudesOT.getBApellidos() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDApellido1()) + ";");
			}
			if (solicitudesOT.getBNombre() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDNombre()) + ";");
			}
			if (solicitudesOT.getBCSVSexo() == true) {
				if (solicitudesOT.getBSexo().equals(Constantes.SEXO_HOMBRE_VALUE)) {
					cadenaDatos.append(Cadenas.formatearParaCSV(Constantes.SEXO_HOMBRE) + ";");
				} else if (solicitudesOT.getBSexo().equals(Constantes.SEXO_MUJER_VALUE)) {
					cadenaDatos.append(Cadenas.formatearParaCSV(Constantes.SEXO_MUJER) + ";");
				}
			}
			if (solicitudesOT.getBCategoria() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDProfesional_nombre()) + ";");
			}
			if (solicitudesOT.getBEspecialidad() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDEspec_nombre()) + ";");
			}
			if (solicitudesOT.getBSituacionAdm() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDSitAdministrativaAux_nombre()) + ";");
			}
			if (solicitudesOT.getBModalidad() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDModalidad()) + ";");
			}
			if (solicitudesOT.getBProcedimiento() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDProcedimiento()) + ";");
			}
			if (solicitudesOT.getBGerencia() == true) {
				if ((solicitudesOT.getCodGerencia() != null)
						&& (Constantes.COD_LDAP_GRS.equals(solicitudesOT.getCodGerencia()))) {
					String nombreCentros = "";
					nombreCentros = generarCadenaCentrosGerencia(Constantes.COD_LDAP_GRS, this.jcylUsuario);
					if (nombreCentros.equals("")) {
						cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDGerencia_nombre()) + ";");
					} else {
						cadenaDatos.append(Cadenas.formatearParaCSV(nombreCentros) + ";");
					}
				} else {
					cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDGerencia_nombre()) + ";");
				}
			}
			if (solicitudesOT.getBConvocatoria() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDConvocatoria()) + ";");
			}
			cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDGrado_des()) + ";");
			if (solicitudesOT.getBFechaSolic() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getFRegistro_solic().toString()) + ";");
			}
			if (solicitudesOT.getBJuridico() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDRegimenJuridico()) + ";");
			}
			if (solicitudesOT.getBAnios() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(String.valueOf(solicitudesOT.getNAniosantiguedad())) + ";");
			}
			if (solicitudesOT.getBCentro() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDCentrotrabajo_nombre()) + ";");
			}
			if (solicitudesOT.getBServicio() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getAServicio()) + ";");
			}
			if (solicitudesOT.getBPuesto() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getAPuesto()) + ";");
			}
			if (solicitudesOT.getBEstado() == true) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getCEstado()) + ";");
			}
			if ((solicitudesOT.getBCausas() == true) && (solicitudesOT.getCExp_id() != 0L)) {
				if (solicitudesOT.getCEstado_id() == 4) {
					StringBuffer strMotivos = new StringBuffer();
					OCAPExpedientesExclusionOT expedientesExclusionOT = null;
					OCAPExpedientesExclusionOAD expedientesExclusionOAD = OCAPExpedientesExclusionOAD.getInstance();
					ArrayList motivos = expedientesExclusionOAD.buscarOCAPExpedientesExclusion(solicitudesOT);
					for (int i = 0; i < motivos.size(); i++) {
						expedientesExclusionOT = (OCAPExpedientesExclusionOT) motivos.get(i);
						if ("Otros".equalsIgnoreCase(expedientesExclusionOT.getDNombre())) {
							strMotivos.append(expedientesExclusionOT.getDOtrosMotivos());
						} else {
							strMotivos.append(expedientesExclusionOT.getDDescripcion());
						}
						strMotivos.append(" ");
					}
					cadenaDatos.append(Cadenas.formatearParaCSV(strMotivos.toString()) + ";");
				}
				if (solicitudesOT.getCEstado_id() == 10) {
					cadenaDatos.append(
							Cadenas.formatearParaCSV(obtenerMotExclusionFaseII(solicitudesOT, "D", this.jcylUsuario))
									+ ";");
				}
				if (solicitudesOT.getCEstado_id() == 11) {
					cadenaDatos.append("GRADO NO RECONOCIDO;");
				}
			}
			if (solicitudesOT.getFRenuncia() != null && !solicitudesOT.getFRenuncia().trim().equals("")) {
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getFRenuncia().toString()) + ";");
			} else {
				cadenaDatos.append(Cadenas.formatearParaCSV("--") + ";");
			}
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
		return cadenaDatos;
	}

	public String obtenerEstado(OCAPSolicitudesOT solicitudesOT) throws Exception {
		String estadoSolic = "";
		try {
			if (Constantes.SI.equals(solicitudesOT.getBReconocimientoGrado())) {
				estadoSolic = "A";
			} else if (Constantes.NO.equals(solicitudesOT.getBReconocimientoGrado())) {
				estadoSolic = Constantes.ESTADO_EXCLUIDO_VALUE;
			} else if (Constantes.SI.equals(solicitudesOT.getBValidacionCC())) {
				estadoSolic = "P";
			} else if (Constantes.NO.equals(solicitudesOT.getBValidacionCC())) {
				estadoSolic = Constantes.ESTADO_EXCLUIDO_VALUE;
			} else if ((solicitudesOT.getFAceptac_solic() != null) && (!solicitudesOT.getFAceptac_solic().equals(""))) {
				estadoSolic = "P";
			} else if (((solicitudesOT.getFNegacion_solic() != null)
					&& (!solicitudesOT.getFNegacion_solic().equals("")))
					|| ((solicitudesOT.getFRespuesta_inconf_solic() != null)
							&& (!solicitudesOT.getFRespuesta_inconf_solic().equals("")))) {
				estadoSolic = Constantes.ESTADO_EXCLUIDO_VALUE;
			}
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " obtenerEstado: ERROR" + e.getMessage());
			throw e;
		}
		return estadoSolic;
	}

	public String generarCSVSolicitudesExcluidas(ArrayList datos, JCYLUsuario jcylUsuario) throws Exception {
		StringBuffer cadenaDatos = new StringBuffer();
		OCAPSolicitudesOT solicitudesOT = null;
		String nombreCentros = "";
		String gerenciaAnt = "";
		String causasExclusion = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarCSVSolicitudesExcluidas: Inicio");
			OCAPMotExclusionOAD motExclusionOAD = OCAPMotExclusionOAD.getInstance();
			for (int i = 0; i < datos.size(); i++) {
				solicitudesOT = (OCAPSolicitudesOT) datos.get(i);
				if (i == 0) {
					cadenaDatos.append(
							"CONVOCATORIA DE FECHA " + solicitudesOT.getFConvocatoria() + ";" + Constantes.SALTO_LINEA);
					cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDGrado_des().toUpperCase()) + ";"
							+ Constantes.SALTO_LINEA);
				}
				if (!gerenciaAnt.equals(solicitudesOT.getDGerencia_nombre())) {
					gerenciaAnt = solicitudesOT.getDGerencia_nombre();

					cadenaDatos.append(Constantes.SALTO_LINEA);
					cadenaDatos.append(Constantes.SALTO_LINEA);
					cadenaDatos.append(Constantes.SALTO_LINEA);
					if ((solicitudesOT.getCodGerencia() != null)
							&& (Constantes.COD_LDAP_GRS.equals(solicitudesOT.getCodGerencia()))) {
						nombreCentros = generarCadenaCentrosGerencia(Constantes.COD_LDAP_GRS, jcylUsuario);
						if (nombreCentros.equals("")) {
							cadenaDatos
									.append("GERENCIA:;" + Cadenas.formatearParaCSV(solicitudesOT.getDGerencia_nombre())
											+ ";" + Constantes.SALTO_LINEA);
						} else {
							cadenaDatos.append("GERENCIA:;" + Cadenas.formatearParaCSV(nombreCentros) + ";"
									+ Constantes.SALTO_LINEA);
						}
					} else {
						cadenaDatos.append("GERENCIA:;" + Cadenas.formatearParaCSV(solicitudesOT.getDGerencia_nombre())
								+ ";" + Constantes.SALTO_LINEA);
					}
					cadenaDatos.append(Constantes.SALTO_LINEA);
					cadenaDatos
							.append("APELLIDOS;NOMBRE;CUERPO/CATEGORÍA;ESPECIALIDAD;SITUACIÓN ADMINISTRATIVA;CAUSAS EXCLUSIÓN;")
							.append(Constantes.SALTO_LINEA);
				}
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDApellido1()) + ";");
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDNombre()) + ";");
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDProfesional_nombre()) + ";");
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDEspec_nombre()) + ";");
				cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDSitAdministrativaAux_nombre()) + ";");

				causasExclusion = "";
				if (solicitudesOT.getCExp_id() != 0L) {
					causasExclusion = motExclusionOAD.buscarMotivosExpediente(solicitudesOT.getCExp_id());
				}
				cadenaDatos.append(Cadenas.formatearParaCSV(causasExclusion) + ";");
				cadenaDatos.append(Constantes.SALTO_LINEA);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " generarCSVSolicitudesExcluidas: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
		return cadenaDatos.toString();
	}

	public String generarCSVDinamicoSolicitudesExcluidas(ArrayList datos, JCYLUsuario jcylUsuario) throws Exception {
		StringBuffer cadenaDatos = new StringBuffer();
		OCAPSolicitudesOT solicitudesOT = null;
		String nombreCentros = "";
		String gerenciaAnt = "";
		String causasExclusion = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarCSVDinamicoSolicitudesExcluidas: Inicio");
			for (int i = 0; i < datos.size(); i++) {
				solicitudesOT = new OCAPSolicitudesOT();
				solicitudesOT = (OCAPSolicitudesOT) datos.get(i);
				if (i == 0) {
					cadenaDatos.append(
							"CONVOCATORIA DE FECHA " + solicitudesOT.getFConvocatoria() + ";" + Constantes.SALTO_LINEA);
					cadenaDatos.append(Cadenas.formatearParaCSV(solicitudesOT.getDGrado_des().toUpperCase()) + ";"
							+ Constantes.SALTO_LINEA);

					cadenaDatos.append(Constantes.SALTO_LINEA);
					cadenaDatos.append(construirCabecera(solicitudesOT));
					if ((solicitudesOT.getBNifNie() == false) && (solicitudesOT.getBApellidos() == false)
							&& (solicitudesOT.getBNombre() == false) && (solicitudesOT.getBCSVSexo() == false)
							&& (solicitudesOT.getBCategoria() == false) && (solicitudesOT.getBEspecialidad() == false)
							&& (solicitudesOT.getBSituacionAdm() == false)
							&& (solicitudesOT.getBProcedimiento() == false) && (solicitudesOT.getBGerencia() == false)
							&& (solicitudesOT.getBEstado() == false)) {
						cadenaDatos.append(Constantes.SALTO_LINEA);
					} else {
						cadenaDatos.append("CAUSAS EXCLUSIÓN;").append(Constantes.SALTO_LINEA);
					}
				}
				cadenaDatos.append(construirDatos(solicitudesOT));
				if ((solicitudesOT.getBNifNie() == false) && (solicitudesOT.getBApellidos() == false)
						&& (solicitudesOT.getBNombre() == false) && (solicitudesOT.getBCSVSexo() == false)
						&& (solicitudesOT.getBCategoria() == false) && (solicitudesOT.getBEspecialidad() == false)
						&& (solicitudesOT.getBSituacionAdm() == false) && (solicitudesOT.getBProcedimiento() == false)
						&& (solicitudesOT.getBGerencia() == false) && (solicitudesOT.getBEstado() == false)
						&& (solicitudesOT.getBCausas() == false) && (solicitudesOT.getBConvocatoria() == false)
						&& (solicitudesOT.getBFechaSolic() == false) && (solicitudesOT.getBJuridico() == false)
						&& (solicitudesOT.getBAnios() == false) && (solicitudesOT.getBCentro() == false)) {
					cadenaDatos.append(Constantes.SALTO_LINEA);
				} else {
					causasExclusion = "";
					if (Constantes.SI.equals(solicitudesOT.getBInconf_antiguedad())) {
						causasExclusion = causasExclusion
								+ "No acreditar el número de años de ejercicio profesional establecido. ";
					}
					if (Constantes.SI.equals(solicitudesOT.getBInconf_plazaprop())) {
						causasExclusion = causasExclusion
								+ "No ostentar la condición de personal estatutario fijo o funcionario sanitario fijo en Sacyl. ";
					}
					if ((solicitudesOT.getBOtrosCentros() != null)
							&& (Constantes.SI.equals(solicitudesOT.getBOtrosCentros()))) {
						causasExclusion = causasExclusion + "No constan servicios prestados en otros centros. ";
					}
					if (Constantes.SI.equals(solicitudesOT.getBPlazo())) {
						causasExclusion = causasExclusion + "Presentación fuera de plazo. ";
					}
					cadenaDatos.append(Cadenas.formatearParaCSV(causasExclusion) + ";");
					cadenaDatos.append(Constantes.SALTO_LINEA);
				}
			}
			OCAPConfigApp.logger.info(getClass().getName() + " generarCSVDinamicoSolicitudesExcluidas: Fin");
			return cadenaDatos.toString();
		} catch (Exception e) {
			OCAPConfigApp.logger
					.info(getClass().getName() + " generarCSVDinamicoSolicitudesExcluidas: ERROR" + e.getMessage());
			throw e;
		}
	}

	public String generarCSVDinamicoSolicitudes(ArrayList datos, JCYLUsuario jcylUsuario) throws Exception {
		StringBuffer cadenaDatos = new StringBuffer();
		OCAPSolicitudesOT solicitudesOT = null;

		boolean mostrarRegistro = false;

		int numDatos = 0;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarCSVDinamicoSolicitudes: Inicio");

			numDatos = datos != null ? datos.size() : 0;
			for (int i = 0; i < numDatos; i++) {
				mostrarRegistro = false;
				solicitudesOT = (OCAPSolicitudesOT) datos.get(i);
				if (i == 0) {
					cadenaDatos.append(Constantes.SALTO_LINEA);
					cadenaDatos.append(construirCabecera(solicitudesOT));
					cadenaDatos.append(Constantes.SALTO_LINEA);
				}
				cadenaDatos.append(construirDatosCSV(solicitudesOT));
				cadenaDatos.append(Constantes.SALTO_LINEA);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " generarCSVDinamicoSolicitudes: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
		return cadenaDatos.toString();
	}

	private String generarCadenaCentrosGerencia(String codLdapGerencia, JCYLUsuario jcylUsuario) throws Exception {
		OCAPCentroTrabajoLN centroLN = null;
		OCAPCentroTrabajoOT centroOT = null;
		ArrayList listaCentros = null;
		String nombreCentros = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarCadenaCentrosGerencia: Inicio");
			centroLN = new OCAPCentroTrabajoLN(jcylUsuario);
			listaCentros = centroLN.listarCentroTrabajo("", codLdapGerencia);
			for (int nCentros = 0; nCentros < listaCentros.size() - 2; nCentros++) {
				centroOT = (OCAPCentroTrabajoOT) listaCentros.get(nCentros);
				nombreCentros = nombreCentros + centroOT.getDNombre() + ", ";
			}
			if (listaCentros.size() > 1) {
				centroOT = (OCAPCentroTrabajoOT) listaCentros.get(listaCentros.size() - 2);
				nombreCentros = nombreCentros + centroOT.getDNombre() + " y ";
				centroOT = (OCAPCentroTrabajoOT) listaCentros.get(listaCentros.size() - 1);
				nombreCentros = nombreCentros + centroOT.getDNombre() + ".";
			}
			if (listaCentros.size() == 1) {
				centroOT = (OCAPCentroTrabajoOT) listaCentros.get(0);
				nombreCentros = nombreCentros + centroOT.getDNombre();
			}
			OCAPConfigApp.logger.info(getClass().getName() + " generarCadenaCentrosGerencia: Fin");
			return nombreCentros;
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " generarCadenaCentrosGerencia: ERROR " + e.getMessage());
			throw e;
		}
	}

	public ArrayList buscarOCAPMeritosPorCategProfesional(Integer cProfesionalId, long cGradoId,
			JCYLUsuario jcylUsuario) throws SQLException, Exception {
		ArrayList datos = null;
		try {
			OCAPConfigApp.logger.info("buscarOCAPMeritosPorCategProfesional");

			OCAPCreditosOAD creditosOAD = OCAPCreditosOAD.getInstance();
			datos = creditosOAD.buscarOCAPMeritosPorCategProfesional(cProfesionalId, cGradoId, jcylUsuario);
		} catch (SQLException exSQL) {
			this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: "
					+ exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
			throw exSQL;
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
		return datos;
	}

	public int modificarSolicitud(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario, boolean grs) throws Exception {
		int filas;
		Connection con;
		filas = 0;
		con = null;
		try {
			OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
					.append(" modificarSolicitud: Inicio").toString());
			con = JCYLGestionTransacciones.getConnection();
			con.setAutoCommit(false);
			OCAPUsuariosOT usuariosOT = new OCAPUsuariosOT();
			usuariosOT.setDApellido1(solicitudesOT.getDApellido1());
			usuariosOT.setDNombre(solicitudesOT.getDNombre());
			usuariosOT.setCDniReal(solicitudesOT.getCDniReal());
			usuariosOT.setBSexo(solicitudesOT.getBSexo());
			if (solicitudesOT.getNTelefono1() != null && !solicitudesOT.getNTelefono1().equals(""))
				usuariosOT.setNTelefono1(Integer.valueOf(solicitudesOT.getNTelefono1()));
			if (solicitudesOT.getNTelefono2() != null && !solicitudesOT.getNTelefono2().equals(""))
				usuariosOT.setNTelefono2(Integer.valueOf(solicitudesOT.getNTelefono2()));
			usuariosOT.setDCorreoelectronico(solicitudesOT.getDCorreoelectronico());
			usuariosOT.setDDomicilio(solicitudesOT.getDDomicilio());
			usuariosOT.setCProvinciaUsu_id(solicitudesOT.getCProvincia_id());
			usuariosOT.setDLocalidadUsu(solicitudesOT.getDLocalidadUsu());
			usuariosOT.setCPostalUsu(solicitudesOT.getCPostalUsu());
			usuariosOT.setCGerenciaId(Integer.valueOf(String.valueOf(solicitudesOT.getCGerencia_id())));
			usuariosOT.setCCentrotrabajoId(Integer.valueOf(String.valueOf(solicitudesOT.getCCentrotrabajo_id())));
			usuariosOT.setBBorrado(Constantes.NO);
			usuariosOT.setAModificadoPor(solicitudesOT.getAModificadoPor());
			usuariosOT.setCUsrId(new Long(solicitudesOT.getCUsr_id()));
			OCAPUsuariosOAD usuarioOAD = OCAPUsuariosOAD.getInstance();
			filas = usuarioOAD.modificarDatosUsuarios(usuariosOT, con);
			
			//Grabamos los cambios en la solicitud
			OCAPSolicitudesOAD solicitudOAD = OCAPSolicitudesOAD.getInstance();
			filas = solicitudOAD.modificarDatosSolicitud(solicitudesOT, con, grs);
			
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();
			expedienteCarreraOT.setCExpId(new Long(solicitudesOT.getCExp_id()));
			expedienteCarreraOT.setCGradoId(Integer.valueOf(String.valueOf(solicitudesOT.getCGrado_id())));
			expedienteCarreraOT.setCModAnteriorId(solicitudesOT.getCModAnterior_id());
			expedienteCarreraOT.setCProcedimientoId(solicitudesOT.getCProcedimientoId());
			expedienteCarreraOT.setCJuridico(solicitudesOT.getCJuridicoId());
			expedienteCarreraOT.setCEstatutId(solicitudesOT.getCEstatutId());
			expedienteCarreraOT.setDRegimenJuridicoOtros(solicitudesOT.getDRegimenJuridicoOtros());
			expedienteCarreraOT.setCSitAdministrativaAuxId(solicitudesOT.getCSitAdministrativaAuxId());
			expedienteCarreraOT.setDSitAdministrativaAuxOtros(solicitudesOT.getDSitAdministrativaOtros());
			expedienteCarreraOT.setCProfesionalId(solicitudesOT.getCProfesional_id());
			expedienteCarreraOT.setCEspecId(solicitudesOT.getCEspec_id());
			expedienteCarreraOT.setAServicio(solicitudesOT.getAServicio());
			expedienteCarreraOT.setAPuesto(solicitudesOT.getAPuesto());
			expedienteCarreraOT.setNAniosAntiguedad(solicitudesOT.getNAniosantiguedad());
			expedienteCarreraOT.setNMesesAntiguedad(solicitudesOT.getNMesesantiguedad());
			expedienteCarreraOT.setNDiasAntiguedad(solicitudesOT.getNDiasantiguedad());
			expedienteCarreraOT.setBOtroServicio(solicitudesOT.getBOtroServicio());
			expedienteCarreraOT.setADondeServicio(solicitudesOT.getADondeServicio());
			expedienteCarreraOT.setAModificadoPor(solicitudesOT.getAModificadoPor());
			OCAPExpedientecarreraOAD expedienteCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
			filas = expedienteCarreraOAD.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false, false);
			OCAPOtrosCentrosLN otrosCentrosLN = new OCAPOtrosCentrosLN(jcylUsuario);
			String modificador = jcylUsuario.getUser().getC_usr_id();
			otrosCentrosLN.bajaOCAPOtrosCentros(solicitudesOT.getCExp_id(), modificador, con);
			ArrayList cadenasCentros = new ArrayList();
			if (solicitudesOT.getResumenCentros() != null && !"".equals(solicitudesOT.getResumenCentros()))
				cadenasCentros = Cadenas.obtenerArrayListTokens(solicitudesOT.getResumenCentros(), "#");
			ArrayList listaOtrosCentros = new ArrayList();
			OCAPOtrosCentrosOT otrosCentrosOT = null;
			for (int i = 0; i < cadenasCentros.size(); i++) {
				ArrayList campos = new ArrayList();
				String cadena = (String) cadenasCentros.get(i);
				for (StringTokenizer token = new StringTokenizer(cadena, "$"); token.hasMoreTokens(); campos
						.add(token.nextToken()))
					;
				otrosCentrosOT = new OCAPOtrosCentrosOT();
				otrosCentrosOT.setDNombre((String) campos.get(0));
				otrosCentrosOT.setAProvincia((String) campos.get(1));
				otrosCentrosOT.setACategoria((String) campos.get(2));
				otrosCentrosOT.setAVinculo((String) campos.get(3));
				otrosCentrosOT.setFInicio(DateUtil.convertStringToDate((String) campos.get(4)));
				otrosCentrosOT.setFFin(DateUtil.convertStringToDate((String) campos.get(5)));
				otrosCentrosOT.setCSolicitud_id(solicitudesOT.getCSolicitudId());
				listaOtrosCentros.add(otrosCentrosOT);
			}
			otrosCentrosLN.removeAllCentros(solicitudesOT.getCSolicitudId(), solicitudesOT.getCExp_id());
			otrosCentrosLN.altaOtrosCentros(
					Integer.parseInt((new StringBuilder()).append(solicitudesOT.getCExp_id()).append("").toString()),
					listaOtrosCentros, jcylUsuario);
			con.commit();
			OCAPConfigApp.logger.info(
					(new StringBuilder()).append(getClass().getName()).append(" modificarSolicitud: Fin").toString());
		} catch (Exception e) {
			OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
					.append(" modificarSolicitud: ERROR: ").append(e.toString()).toString());
			con.rollback();
			throw e;
		} finally {
			con.setAutoCommit(true);
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
	}

	public void crearInformeAutoevaluacionCa(HttpServletResponse response, OCAPSolicitudesOT solicitudesOT,
			String pathBase, String destino) throws SendFailedException, Exception {
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeAutoevaluacionCa: Inicio");

			String servidor_de_correo = JCYLConfiguracion.getValor("SERVIDOR_CORREO");
			String puerto = JCYLConfiguracion.getValor("PUERTO_CORREO");
			String remitente = JCYLConfiguracion.getValor("EMAIL_REMITENTE");
			String destinatario = solicitudesOT.getDCorreoelectronico();
			String asunto = "Carrera Profesional. Proceso ordinario";
			String cuerpo = null;
			if (solicitudesOT.getBSexo() == null) {
				cuerpo = "<p>Don/Do&ntilde;a ";
			} else if (Constantes.SEXO_HOMBRE_VALUE.equals(solicitudesOT.getBSexo())) {
				cuerpo = "<p>Don ";
			} else {
				cuerpo = "<p>Do&ntilde;a ";
			}
			cuerpo = cuerpo + solicitudesOT.getDNombre() + " " + solicitudesOT.getDApellido1() + ": <br />";
			cuerpo = cuerpo.replaceAll("á", "&aacute;");
			cuerpo = cuerpo.replaceAll("Á", "&Aacute;");
			cuerpo = cuerpo.replaceAll("é", "&eacute;");
			cuerpo = cuerpo.replaceAll("É", "&Eacute;");
			cuerpo = cuerpo.replaceAll("í", "&iacute;");
			cuerpo = cuerpo.replaceAll("Í", "&Iacute;");
			cuerpo = cuerpo.replaceAll("ó", "&oacute;");
			cuerpo = cuerpo.replaceAll("Ó", "&Oacute;");
			cuerpo = cuerpo.replaceAll("ú", "&uacute;");
			cuerpo = cuerpo.replaceAll("Ú", "&Uacute;");
			cuerpo = cuerpo.replaceAll("ñ", "&ntilde;");
			cuerpo = cuerpo.replaceAll("Ñ", "&Ntilde;");
			if (destino.equals(Constantes.OCAP_USUARIOS)) {
				cuerpo = cuerpo
						+ "<p>Le comunicamos que la fase de m&eacute;ritos curriculares correspondiente a su auto-evaluaci&oacute;n ha sido ratificada por el Comit&eacute; Espec&iacute;fico de la Instituci&oacute;n Sanitaria de su Gerencia.<br />A partir del d&iacute;a ###FECHA### podr&aacute; comenzar la fase de evaluaci&oacute;n de la competencia del desempeño y dispondr&aacute; de 30 d&iacute;as naturales para la cumplimentaci&oacute;n y entrega de todos los documentos necesarios.</p><br />Atentamente. ";
				cuerpo = cuerpo.replaceFirst("###FECHA###", solicitudesOT.getFRegistro_oficial());
			}
			if (destino.equals(Constantes.OCAP_EVAL)) {
				cuerpo = cuerpo
						+ "<p>Le enviamos la autoevaluaci&oacute;n correspondiente al c&oacute;digo de expediente profesional ###CODIGO### para que proceda a realizar su revisi&oacute;n o su an&aacute;lisis seg&uacute;n el procedimiento establecido.</p><br />";
				DecimalFormat formato = new DecimalFormat("000000");
				cuerpo = cuerpo.replaceFirst("###CODIGO###", "EPR" + formato.format(solicitudesOT.getCExp_id()));
			}
			UtilCorreo.enviarHtml(servidor_de_correo, puerto, remitente, destinatario, asunto, cuerpo);

			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeAutoevaluacionCa: Fin");
		} catch (SendFailedException e) {
			OCAPConfigApp.logger.error(
					getClass().getName() + " crearInformeAutoevaluacionCa: ERROR enviando email: " + e.toString());
			throw e;
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw new Exception("Informe NO generado");
		}
	}

	public int finalizarSubidaEscaneados(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario) throws Exception {
		int filas = 0;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " finalizarSubidaEscaneados: Inicio");

			OCAPExpedientecarreraOAD expedienteCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();

			Date hoy = new Date();
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_COMPLETA_DATEUTIL);
			expedienteCarreraOT.setFRegDocEscaneados(df.parse(df.format(hoy)));
			expedienteCarreraOT.setCExpId(Long.valueOf(solicitudesOT.getCExp_id()));
			expedienteCarreraOT.setAModificadoPor(this.jcylUsuario.getUsuario().getC_usr_id());

			filas = expedienteCarreraOAD.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false, false);

			OCAPConfigApp.logger.info(getClass().getName() + " finalizarSubidaEscaneados: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
		return filas;
	}

	public void crearInformeDetalle(HttpServletResponse response, OCAPSolicitudesOT solicitudesOT, String pathBase)
			throws Exception {
		ArrayList codigosMut = null;
		String nombreReportPadre = null;
		Hashtable parametros = null;
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		String sexo = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeDetalle: Inicio");

			nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "informeDetalle" + ".jasper";

			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeDetalle: Cargar report padre");
			File fichReportPadre = new File(nombreReportPadre);
			jasperReport = (JasperReport) JRLoader.loadObject(fichReportPadre);

			parametros = new Hashtable();
			parametros.put("ruta", pathBase);
			if (solicitudesOT.getBSexo().equals(Constantes.SEXO_HOMBRE_VALUE)) {
				sexo = Constantes.SEXO_HOMBRE;
			} else {
				sexo = Constantes.SEXO_MUJER;
			}
			parametros.put("sexo", sexo);
			parametros.put("anio", Long.toString(solicitudesOT.getNAniosantiguedad()));

			parametros.put("fecha", " ");

			parametros.put("datosDocu", solicitudesOT);

			jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);

			OutputStream out = response.getOutputStream();
			response.setHeader("Content-disposition",
					"attachment; filename=\"informeDetalle" + solicitudesOT.getCExp_id() + ".pdf\"");
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();

			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeDetalle: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeDetalle: ERROR" + e.getMessage());
			throw new Exception("Informe NO generado");
		}
	}

	public void crearPDFSolicitud(HttpServletResponse response, OCAPSolicitudesOT solicitudesOT, String pathBase)
			throws Exception {
		String nombreSubreport = null;
		String nombreAnexo1 = null;
		String nombreAnexo2 = null;
		String nombreAnexo3 = null;
		Hashtable parametros = null;
		Hashtable parametros2 = null;
		Hashtable parametros3 = null;
		JasperReport jasperAnexo1 = null;
		JasperReport jasperAnexo2 = null;
		JasperReport jasperAnexo3 = null;
		JasperReport jasperSubReport = null;
		JasperPrint jasperPrint = null;
		JasperPrint jasperPrintReport2 = null;
		JasperPrint jasperPrintReport3 = null;
		ConceptoReport matrizDatosReport = null;
		String siHombre = "";
		String siMujer = "";

		String estatutario = "";
		String funcionario = "";
		String siActivo = "";
		String siGeneral = "";
		String siEspecial = "";
		String siDirectivo = "";
		String siExcedencia = "";
		String siLiberado = "";
		String siEstructura = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearPDFSolicitud: Inicio");

			nombreAnexo1 = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "anexo1" + ".jasper";
			nombreAnexo2 = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "anexo2" + ".jasper";
			nombreAnexo3 = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "anexo3" + ".jasper";

			File fichSubreport1 = new File(nombreAnexo1);
			jasperAnexo1 = (JasperReport) JRLoader.loadObject(fichSubreport1);

			File fichSubreport2 = new File(nombreAnexo2);
			jasperAnexo2 = (JasperReport) JRLoader.loadObject(fichSubreport2);
			
			File fichSubreport3 = new File(nombreAnexo3);
			jasperAnexo3 = (JasperReport) JRLoader.loadObject(fichSubreport3);

			parametros = new Hashtable();
			parametros2 = new Hashtable();
			parametros3 = new Hashtable();

			parametros.put("ruta", pathBase);
			parametros.put("datosReport", solicitudesOT);
			parametros2.put("datosReport", solicitudesOT);
			parametros3.put("datosReport", solicitudesOT);
			if (Constantes.SEXO_HOMBRE_VALUE.equals(solicitudesOT.getBSexo())) {
				siHombre = "X";
			} else {
				siMujer = "X";
			}
			parametros.put("siHombre", siHombre);
			parametros.put("siMujer", siMujer);
			if ("1".equals(solicitudesOT.getCJuridicoId())) {
				estatutario = "X";
			} else {
				funcionario = "X";
			}
			parametros.put("estatutario", estatutario);
			parametros.put("funcionario", funcionario);
			if ("1".equals(solicitudesOT.getCSitAdministrativaAuxId())) {
				siActivo = "X";
			}
			parametros.put("siActivo", siActivo);
			if ("General".equals(solicitudesOT.getCProcedimientoId())) {
				siGeneral = "X";
			} else {
				siEspecial = "X";
				if ("Directivo".equals(solicitudesOT.getCProcedimientoId())) {
					siDirectivo = "X";
				} else if ("Excedencia".equals(solicitudesOT.getCProcedimientoId())) {
					siExcedencia = "X";
				} else if ("Liberado".equals(solicitudesOT.getCProcedimientoId())) {
					siLiberado = "X";
				} else {
					siEstructura = "X";
				}
			}
			parametros.put("siGeneral", siGeneral);
			parametros.put("siEspecial", siEspecial);
			parametros.put("siDirectivo", siDirectivo);
			parametros.put("siExcedencia", siExcedencia);
			parametros.put("siLiberado", siLiberado);
			parametros.put("siEstructura", siEstructura);

			nombreSubreport = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "tablaCentros" + ".jasper";
			File fichSubreport = new File(nombreSubreport);
			jasperSubReport = (JasperReport) JRLoader.loadObject(fichSubreport);

			parametros.put("SUB_REPORT", jasperSubReport);

			matrizDatosReport = new ConceptoReport();
			matrizDatosReport = generarTablaCentros(solicitudesOT.getResumenCentros());
			if (matrizDatosReport == null) {
				parametros.put("reportVacio", "NO HAY DATOS COINCIDENTES");
			} else {
				parametros.put("datosSubReport", matrizDatosReport);
			}
			jasperPrint = JasperFillManager.fillReport(jasperAnexo1, parametros, new JREmptyDataSource());

			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
			jasperPrintReport2 = JasperFillManager.fillReport(jasperAnexo2, parametros2, new JREmptyDataSource());
			jasperPrintReport3 = JasperFillManager.fillReport(jasperAnexo3, parametros3, new JREmptyDataSource());
			byte[] bytesReport2 = JasperExportManager.exportReportToPdf(jasperPrintReport2);
			byte[] bytesReport3 = JasperExportManager.exportReportToPdf(jasperPrintReport3);

			OutputStream out = response.getOutputStream();
			PdfReader reader1 = new PdfReader(bytes);
			PdfReader reader2 = new PdfReader(bytesReport2);
			PdfReader reader3 = new PdfReader(bytesReport3);

			String nombreFichero = RandomStringUtils.randomAlphabetic(16) + "." + "pdf";
			String rutaFichero = pathBase + File.separator + "WEB-INF" + File.separator + "temp" + File.separator
					+ nombreFichero;

			File directory = new File(pathBase + File.separator + "WEB-INF" + File.separator + "temp" + File.separator);
			directory.mkdirs();
			File fichero = new File(directory, nombreFichero);

			PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(fichero));
			copy.addDocument(reader1);
			copy.addDocument(reader2);
			copy.addDocument(reader3);
			copy.close();

			response.setHeader("Content-disposition", "attachment; filename=\"solicitudGrado.pdf\"");
			response.setContentType("application/pdf");

			InputStream archivo = new FileInputStream(fichero);

			int bit = 256;
			int i = 0;
			while (bit >= 0) {
				bit = archivo.read();
				out.write(bit);
			}
			out.flush();
			out.close();

			archivo.close();
			if (fichero.exists()) {
				OCAPConfigApp.logger.info(getClass().getName() + " borramos el fichero generado " + nombreFichero
						+ " para la solicitud " + solicitudesOT.getCSolicitudId());
				Ficheros.borrarFichero(rutaFichero);
			}
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " crearPDFSolicitud: ERROR" + e.getMessage());
			throw new Exception("Informe NO generado");
		}
	}

	public ConceptoReport generarTablaCentros(String resumenCentros) throws Exception {
		ConceptoReport report = null;
		ConceptoReport subreport = null;
		OCAPSolicitudesOT solicitudesOT = null;
		OCAPSolicitudesOT subsolicitudesOT = null;
		OCAPSolicitudesOT subsubsolicitudesOT = null;
		ArrayList cadenasCentros = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarTablaCentros: Inicio");
			report = new ConceptoReport();
			if ((resumenCentros != null) && (resumenCentros.length() > 0)) {
				cadenasCentros = Cadenas.obtenerArrayListTokens(resumenCentros, "#");
				for (int i = 0; i < cadenasCentros.size(); i++) {
					ArrayList campos = new ArrayList();
					String cadena = (String) cadenasCentros.get(i);
					StringTokenizer token = new StringTokenizer(cadena, "$");
					while (token.hasMoreTokens()) {
						campos.add(token.nextToken());
					}
					report.addRow();
					report.putElement("centro", (String) campos.get(0));
					report.putElement("provincia", (String) campos.get(1));
					report.putElement("categoria", (String) campos.get(2));

					report.putElement("vinculo", (String) campos.get(4));
					report.putElement("fechaIni", (String) campos.get(5));
					report.putElement("fechaFin", (String) campos.get(6));
				}
			} else {
				cadenasCentros = new ArrayList();
				for (int i = 0; i < 4; i++) {
					report.addRow();
					report.putElement("centro", "");
					report.putElement("provincia", "");
					report.putElement("categoria", "");

					report.putElement("vinculo", "");
					report.putElement("fechaIni", "");
					report.putElement("fechaFin", "");
				}
			}
			OCAPConfigApp.logger.info(getClass().getName() + " generarTablaCentros: Fin");
			return report;
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " generarTablaCentros: ERROR" + e.getMessage());
			throw e;
		}
	}

	public ArrayList listaSituaciones() throws Exception {
		ArrayList listado = new ArrayList();
		listado.add(new LabelValueBean("Activo", "1"));
		listado.add(new LabelValueBean("Servicios Especiales", "2"));
		listado.add(new LabelValueBean("Excedencia por prestar servicios en sector público", "3"));
		listado.add(new LabelValueBean("Excedencia cuidado familiares", "4"));
		listado.add(new LabelValueBean("Otras", "5"));

		return listado;
	}

	public ArrayList listaVinculos() throws Exception {
		ArrayList listado = new ArrayList();
		listado.add(new LabelValueBean("Estatutario Propietario", "EP"));
		listado.add(new LabelValueBean("Estatutario Temporal", "ET"));
		listado.add(new LabelValueBean("Funcionario de Carrera", "FC"));
		listado.add(new LabelValueBean("Funcionario Temporal", "FT"));
		listado.add(new LabelValueBean("Laboral Fijo", "LF"));
		listado.add(new LabelValueBean("Laboral Temporal", "LT"));
	    listado.add(new LabelValueBean("Interino funcionario sanitario", "IF"));
	    listado.add(new LabelValueBean("Interino estatutario", "IE"));

		return listado;
	}

	public void crearPDFSubsanacion(HttpServletResponse response, OCAPSolicitudesOT solicitudesOT, String pathBase)
			throws Exception {
		String nombreReportPadre = null;
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		Hashtable parametros = null;
		String fecha = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearPDFSubsanacion: Inicio");

			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_LETRA, new Locale("es","ES"));
			Date hoy = new Date();
			fecha = df.format(hoy);

			nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "subsanaciones" + ".jasper";

			OCAPConfigApp.logger.info(getClass().getName() + " crearPDFSubsanacion: Cargar report padre");

			File fichReportPadre = new File(nombreReportPadre);
			jasperReport = (JasperReport) JRLoader.loadObject(fichReportPadre);

			parametros = new Hashtable();

			parametros.put("datosDocu", solicitudesOT);
			parametros.put("fecha", fecha);
			parametros.put("ruta", pathBase);
			if (solicitudesOT.getBSexo().equals(Constantes.SEXO_HOMBRE_VALUE)) {
				parametros.put("sexo", "Don");
			} else {
				parametros.put("sexo", "Doña");
			}
			if (solicitudesOT.getDObserv_neg_solic() != null) {
				parametros.put("otros", "X");
			}
			jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);

			OutputStream out = response.getOutputStream();

			response.setHeader("Content-disposition", "attachment; filename=\"subsanacion.pdf\"");
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " crearPDFSubsanacion: ERROR" + e.getMessage());
			throw new Exception("Informe NO generado");
		}
	}

	public ArrayList buscarGrs(int inicio, int cuantos, OCAPSolicitudesOT solicitudesOT, boolean bMasiva, String opcion)
			throws Exception {
		ArrayList solicitudesTotal = null;

		String gerenciaAnt = "";

		String gradoAnt = "";
		OCAPSolicitudesOT solicitudOT = null;
		OCAPSolicitudesOT gradoSolicitudOT = null;
		OCAPSolicitudesOT gerenciaSolicitudOT = null;
		ArrayList listadoSolicitudes = null;
		ArrayList listadoGerencias = null;
		ArrayList listadoGrados = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarGrs: Inicio");

			OCAPSolicitudesOAD solicitudesOAD = OCAPSolicitudesOAD.getInstance();
			solicitudesTotal = solicitudesOAD.buscarSolicitudesGrs(inicio, cuantos, solicitudesOT, this.jcylUsuario,
					opcion);
			listadoGerencias = new ArrayList();
			if (!bMasiva) {
				for (int i = 0; i < solicitudesTotal.size(); i++) {
					solicitudOT = (OCAPSolicitudesOT) solicitudesTotal.get(i);
					if ((solicitudesOT.getCEstado() != null) && (!"".equals(solicitudesOT.getCEstado()))) {
						if (solicitudesOT.getCEstado().equals("A")) {
							solicitudOT.setCEstado(Constantes.ESTADO_ACEPTADO);
						} else if (solicitudesOT.getCEstado().equals(Constantes.ESTADO_EXCLUIDO_VALUE)) {
							solicitudOT.setCEstado(Constantes.ESTADO_EXCLUIDO);
						} else if (solicitudesOT.getCEstado().equals("P")) {
							solicitudOT.setCEstado(Constantes.ESTADO_PENDIENTE);
						} else if (solicitudesOT.getCEstado().equals("D")) {
							solicitudOT.setCEstado(Constantes.ESTADO_EXCLUIDO);
						} else if (solicitudesOT.getCEstado().equals(Constantes.ESTADO_DESESTIMADO_VALUE)) {
							solicitudOT.setCEstado(Constantes.ESTADO_EXCLUIDO);
						} else if (solicitudesOT.getCEstado().equals(Constantes.FASE_AUTOEVALUACION_VALUE)) {
							solicitudOT.setCEstado(Constantes.FASE_AUTOEVALUACION);
						}
					} else if (solicitudesOT.getCFase().equals(Constantes.FASE_INICIACION)) {
						if (solicitudOT.getFAceptac_solic() != null) {
							solicitudOT.setCEstado(Constantes.ESTADO_ACEPTADO);
						} else if (solicitudOT.getFNegacion_solic() != null) {
							solicitudOT.setCEstado(Constantes.ESTADO_EXCLUIDO);
						} else {
							solicitudOT.setCEstado(Constantes.ESTADO_PENDIENTE);
						}
					} else if (solicitudesOT.getCFase().equals(Constantes.FASE_MC)) {
						if (solicitudOT.getFAceptacionceis() != null) {
							solicitudOT.setCEstado(Constantes.ESTADO_ACEPTADO);
						} else if (solicitudOT.getFRespuestaInconf_MC() != null) {
							solicitudOT.setCEstado(Constantes.ESTADO_EXCLUIDO);
						} else if (solicitudOT.getFNegacion_mc() != null) {
							solicitudOT.setCEstado(Constantes.ESTADO_EXCLUIDO);
						} else {
							Date hoy = new Date();
							if (hoy.before(solicitudOT.getFFin_mc())) {
								solicitudOT.setCEstado(Constantes.FASE_AUTOEVALUACION);
							} else {
								solicitudOT.setCEstado(Constantes.ESTADO_PENDIENTE);
							}
						}
					} else if (solicitudOT.getBValidacionCC() == null) {
						solicitudOT.setCEstado(Constantes.ESTADO_PENDIENTE);
					} else if (solicitudOT.getBValidacionCC().equals(Constantes.NO)) {
						solicitudOT.setCEstado(Constantes.ESTADO_EXCLUIDO);
					} else {
						solicitudOT.setCEstado(Constantes.ESTADO_ACEPTADO);
					}
					OCAPCategProfesionalesLN categProfLN = new OCAPCategProfesionalesLN(this.jcylUsuario);
					OCAPCategProfesionalesOT categProfOT = categProfLN
							.buscarOCAPCategProfesionales(solicitudOT.getCProfesional_id());
					solicitudOT.setDProfesional_nombre(categProfOT.getDNombre());

					OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(this.jcylUsuario);
					OCAPEspecialidadesOT especialidadesOT = null;
					if (solicitudOT.getCEspec_id() != 0L) {
						especialidadesOT = especialidadesLN.buscarOCAPEspecialidades(solicitudOT.getCEspec_id());
						solicitudOT.setDEspec_nombre(especialidadesOT.getDNombre());
					}
		
					if ((solicitudOT.getDGerencia_nombre() != null)
							&& (!gerenciaAnt.equals(solicitudOT.getDGerencia_nombre()))) {
						if ((listadoGrados != null) && (listadoSolicitudes != null)
								&& ((listadoGrados.size() > 0) || (listadoSolicitudes.size() > 0))) {
							gradoSolicitudOT = new OCAPSolicitudesOT();
							gradoSolicitudOT.setDGrado_des(gradoAnt);
							gradoSolicitudOT.setListaSolicitudes(listadoSolicitudes);
							listadoGrados.add(gradoSolicitudOT);

							gerenciaSolicitudOT = new OCAPSolicitudesOT();
							gerenciaSolicitudOT.setDGerencia_nombre(gerenciaAnt);
							gerenciaSolicitudOT.setListaSolicitudes(listadoGrados);
							listadoGerencias.add(gerenciaSolicitudOT);
						}
						gerenciaAnt = solicitudOT.getDGerencia_nombre();
						gradoAnt = solicitudOT.getDGrado_des();

						listadoGrados = new ArrayList();
						listadoSolicitudes = new ArrayList();

						listadoSolicitudes.add(solicitudOT);
					} else if ((solicitudOT.getDGrado_des() != null)
							&& (solicitudOT.getDGrado_des().equals(gradoAnt))) {
						listadoSolicitudes.add(solicitudOT);
					} else {
						gradoSolicitudOT = new OCAPSolicitudesOT();
						gradoSolicitudOT.setDGrado_des(gradoAnt);
						gradoSolicitudOT.setListaSolicitudes(listadoSolicitudes);
						listadoGrados.add(gradoSolicitudOT);

						listadoSolicitudes = new ArrayList();
						listadoSolicitudes.add(solicitudOT);

						gradoAnt = solicitudOT.getDGrado_des();
					}
				}
				if (solicitudesTotal.size() > 0) {
					gradoSolicitudOT = new OCAPSolicitudesOT();
					gradoSolicitudOT.setDGrado_des(gradoAnt);
					gradoSolicitudOT.setListaSolicitudes(listadoSolicitudes);
					listadoGrados.add(gradoSolicitudOT);

					gerenciaSolicitudOT = new OCAPSolicitudesOT();
					gerenciaSolicitudOT.setDGerencia_nombre(gerenciaAnt);
					gerenciaSolicitudOT.setListaSolicitudes(listadoGrados);
					listadoGerencias.add(gerenciaSolicitudOT);
				}
			} else {
				listadoGerencias = solicitudesTotal;
			}
			OCAPConfigApp.logger.info(getClass().getName() + " buscarGrs: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
		return listadoGerencias;
	}

	public int contarSolicitudesGrs(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario) throws Exception {
		int contador = 0;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " contarSolicitudesGrs: Inicio");

			OCAPSolicitudesOAD solicitudesOAD = OCAPSolicitudesOAD.getInstance();

			contador = solicitudesOAD.contarSolicitudesGrs(solicitudesOT, jcylUsuario);

			OCAPConfigApp.logger.info(getClass().getName() + " contarSolicitudesGrs: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " contarSolicitudesGrs: ERROR: " + e.getMessage());
			throw e;
		}
		return contador;
	}

	public void crearInformeListadoGrs(HttpServletResponse response, ArrayList listadoSolicitudes, String pathBase,
			boolean siAlega, String estado) throws Exception {
		ArrayList codigosMut = null;
		String nombreReportPadre = null;
		String nombreSubreport = null;
		Hashtable parametros = null;
		JasperReport jasperReport = null;
		JasperReport jasperSubReport = null;
		JasperPrint jasperPrint = null;
		ConceptoReport matrizDatosReport = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeListadoGRS: Inicio");

			nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "informeListadoGRS" + ".jasper";
			nombreSubreport = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "informeSubListadoGRS" + ".jasper";

			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeListadoGRS: Cargar report padre");
			File fichReportPadre = new File(nombreReportPadre);
			jasperReport = (JasperReport) JRLoader.loadObject(fichReportPadre);

			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeListadoGRS: Cargar subreport");
			File fichSubreport = new File(nombreSubreport);
			jasperSubReport = (JasperReport) JRLoader.loadObject(fichSubreport);

			parametros = new Hashtable();
			parametros.put("ruta", pathBase);
			if ((estado == null) || ("".equals(estado)) || ("P".equals(estado))) {
				parametros.put("estado", "");
			} else if ("A".equals(estado)) {
				parametros.put("estado", "SUPERADOS");
			} else {
				parametros.put("estado", "NO SUPERADOS");
			}
			parametros.put("SUB_REPORT", jasperSubReport);

			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeListado: Calcular parámetros");

			matrizDatosReport = new ConceptoReport();
			matrizDatosReport = generarTablaGrs(listadoSolicitudes, siAlega);
			if (matrizDatosReport == null) {
				parametros.put("reportVacio", "NO HAY DATOS COINCIDENTES");
			} else {
				parametros.put("datosReport", matrizDatosReport);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeListado: Rellenar informe");

			jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);

			OutputStream out = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename=\"informeListadoGrs.pdf\"");
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();

			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeListadoGRS: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeListadoGRS: ERROR" + e.getMessage());
			throw new Exception("Informe NO generado");
		}
	}

	public ConceptoReport generarTablaGrs(ArrayList datos, boolean siAlega) throws Exception {
		ConceptoReport report = null;
		ConceptoReport subreport = null;
		OCAPSolicitudesOT solicitudesOT = null;
		OCAPSolicitudesOT subsolicitudesOT = null;
		OCAPSolicitudesOT subsubsolicitudesOT = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarTablaGrs: Inicio");
			if (datos.size() != 0) {
				report = new ConceptoReport();
			}
			for (int i = 0; i < datos.size(); i++) {
				solicitudesOT = (OCAPSolicitudesOT) datos.get(i);
				report.addRow();
				report.putElement("col12", solicitudesOT.getDGerencia_nombre());
				ArrayList subListado = solicitudesOT.getListaSolicitudes();
				for (int j = 0; j < subListado.size(); j++) {
					subsolicitudesOT = new OCAPSolicitudesOT();
					subsolicitudesOT = (OCAPSolicitudesOT) subListado.get(j);
					report.addRow();
					report.putElement("col11", subsolicitudesOT.getDGrado_des());
					report.addRow();

					report.putElement("col2cabecera", "Apellidos");
					report.putElement("col4cabecera", "Nombre");
					report.putElement("col5cabecera", "Estado");
					if (siAlega) {
						report.putElement("col6cabecera", "Alegaciones");
					} else {
						report.putElement("col6cabecera", "");
					}
					ArrayList subSubListado = subsolicitudesOT.getListaSolicitudes();
					for (int k = 0; k < subSubListado.size(); k++) {
						subsubsolicitudesOT = new OCAPSolicitudesOT();
						subsubsolicitudesOT = (OCAPSolicitudesOT) subSubListado.get(k);
						report.addRow();
						report.putElement("col11", "");

						report.putElement("col2", subsubsolicitudesOT.getDApellido1());
						report.putElement("col4", subsubsolicitudesOT.getDNombre());
						report.putElement("col5", subsubsolicitudesOT.getCEstado());
						if (siAlega) {
							if ((subsubsolicitudesOT.getFInconf_mc() != null)
									&& (!subsubsolicitudesOT.getFInconf_mc().equals(""))) {
								report.putElement("col6", "Si");
							} else {
								report.putElement("col6", Constantes.NO_TEXTO_min);
							}
						} else {
							report.putElement("col6", "");
						}
					}
				}
			}
			OCAPConfigApp.logger.info(getClass().getName() + " generarTablaGrs: Fin");
			return report;
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " generarTablaGrs: ERROR" + e.getMessage());
			throw e;
		}
	}

	public int cambiarAceptado(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario) throws Exception {
		int filas = 0;
		try {
			OCAPConfigApp.logger.info(
					(new StringBuilder()).append(getClass().getName()).append(" cambiarAceptado: Inicio").toString());
			OCAPExpedientecarreraOAD expedienteCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();
			expedienteCarreraOT.setFAceptacSolic(solicitudesOT.getFAceptac_solic());
			expedienteCarreraOT.setAModificadoPor(solicitudesOT.getAModificadoPor());
			expedienteCarreraOT.setCExpId(new Long(solicitudesOT.getCExp_id()));
			filas = expedienteCarreraOAD.cambiarAceptado(expedienteCarreraOT);
			OCAPConfigApp.logger.info(
					(new StringBuilder()).append(getClass().getName()).append(" cambiarAceptado: Fin").toString());
		} catch (Exception e) {
			OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
					.append(" cambiarAceptado: ERROR: ").append(e.getMessage()).toString());
			throw e;
		}

		return filas;
	}

	public ArrayList buscarUsuariosSinFinalizarMC(long convoId, long gradoId, JCYLUsuario jcylUsuario)
			throws Exception {
		ArrayList listado = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarUsuariosSinFinalizarMC: Inicio");

			OCAPSolicitudesOAD solicitudesOAD = OCAPSolicitudesOAD.getInstance();

			listado = solicitudesOAD.buscarUsuariosSinFinalizarMC(convoId, gradoId);

			OCAPConfigApp.logger.info(getClass().getName() + " buscarUsuariosSinFinalizarMC: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarUsuariosSinFinalizarMC: ERROR: " + e.getMessage());
			throw e;
		}
		return listado;
	}

	public void crearInformeReconociGrado(JCYLUsuario jcylUsuario, HttpServletResponse response,
			OCAPSolicitudesOT solicitudesOT, String pathBase) throws Exception {
		String nombreReport = null;
		Hashtable parametros = new Hashtable();
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		OCAPConvocatoriasLN convoLN = null;
		OCAPConvocatoriasOT convoOT = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeReconociGrado: Inicio");

			if(Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())){
			nombreReport = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "informeReconociGradoPGP" + ".jasper";
			}else{
			
			nombreReport = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "informeReconociGrado" + ".jasper";
			}

			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeReconociGrado: Cargar report");
			File fichReport = new File(nombreReport);
			jasperReport = (JasperReport) JRLoader.loadObject(fichReport);

			parametros.put("ruta", pathBase);

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			Map parametrosUsuario = jcylUsuario.getParametrosUsuario();

			// Gerencia
			OCAPGerenciasOT gerencia = gerenciasLN
					.buscarOCAPGerencias(Long.parseLong((String) parametrosUsuario.get("PARAM_GERENCIA")));
			gerencia.setDProvinciaNombre(Cadenas.capitalizar(gerencia.getDProvinciaNombre()));
			parametros.put("gerencia", gerencia);

			// Fecha resolucion de convocaroria
			convoLN = new OCAPConvocatoriasLN(jcylUsuario);
			convoOT = convoLN.buscarOCAPConvocatorias(solicitudesOT.getCConvocatoriaId());
			String fechaResolucionCon = convoOT.getFResolucion();
			parametros.put("fechaResConvocatoria", fechaResolucionCon);
			
			// Solicitud
			solicitudesOT.setDNombreApellidos(
					solicitudesOT.getDNombre().toUpperCase() + " " + solicitudesOT.getDApellido1().toUpperCase());
			anhadirTratamientoInformeReconociGrado(solicitudesOT);
			String fSesionImprimible = DateUtil.convertDateToStringLargaMesMayus(Constantes.FECHA_LETRA_D,
					solicitudesOT.getFSesion());
			solicitudesOT.setFSesionImprimible(fSesionImprimible);
			parametros.put("datosDocu", solicitudesOT);

			jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);

			OutputStream out = response.getOutputStream();
			SimpleDateFormat ahora = new SimpleDateFormat("ddMMyyyy_hhmm");
			String nombreFichero = "informeReconocimientoGrado" + ahora.format(new Date()) + ".pdf";
			response.setHeader("Content-disposition", "attachment; filename=\"" + nombreFichero + "\"");
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();

			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeReconociGrado: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(getClass().getName() + " crearInformeReconociGrado: ", e);
			throw new Exception("Informe de Reconocimiento de Grado NO generado");
		}
	}

	public void crearInformeIndiviAccesoGrado(JCYLUsuario jcylUsuario, HttpServletResponse response,
			OCAPSolicitudesOT solicitudesOT, String pathBase) throws Exception {
		String nombreReport = null;
		Hashtable parametros = new Hashtable();
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeIndiviAccesoGrado: Inicio");

			nombreReport = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "informeIndiviAccesoGrado" + ".jasper";

			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeIndiviAccesoGrado: Cargar report");
			File fichReport = new File(nombreReport);
			jasperReport = (JasperReport) JRLoader.loadObject(fichReport);

			parametros.put("ruta", pathBase);

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			OCAPGradoOT grado = gradoLN.buscarOCAPGrado(solicitudesOT.getCGrado_id());
			parametros.put("grado", grado);

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			OCAPCategProfesionalesOT categoria = categProfesionalesLN
					.buscarOCAPCategProfesionales(solicitudesOT.getCProfesional_id());
			parametros.put("categoria", categoria);

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			Map parametrosUsuario = jcylUsuario.getParametrosUsuario();
			OCAPGerenciasOT gerencia = gerenciasLN
					.buscarOCAPGerencias(Long.parseLong((String) parametrosUsuario.get("PARAM_GERENCIA")));
			gerencia.setDProvinciaNombre(Cadenas.capitalizar(gerencia.getDProvinciaNombre()));
			if (Constantes.COD_LDAP_GRS.equals(gerencia.getACodldap().toUpperCase())) {
				gerencia.setDNombre("Directora General de Recursos Humanos");
			} else {
				gerencia.setDNombre("Director de Gestión y Servicios Generales");
			}
			parametros.put("gerencia", gerencia);

			anhadirTratamientoInformeIndiviAccesoGrado(solicitudesOT);

			String fConvocatoImprimible = DateUtil.convertDateToStringLargaMesMayus(Constantes.FECHA_LETRA_D,
					DateUtil.convertStringToDate(solicitudesOT.getFConvocatoria()));
			solicitudesOT.setFConvocatoImprimible(fConvocatoImprimible);

			String fSesionImprimible = DateUtil.convertDateToStringLargaMesMayus(Constantes.FECHA_LETRA_D,
					solicitudesOT.getFSesion());
			solicitudesOT.setFSesionImprimible(fSesionImprimible);

			parametros.put("datosDocu", solicitudesOT);

			jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);

			OutputStream out = response.getOutputStream();
			SimpleDateFormat ahora = new SimpleDateFormat("ddMMyyyy_hhmm");
			String nombreFichero = "informeIndividualizadoAccesoGrado" + ahora.format(new Date()) + ".pdf";
			response.setHeader("Content-disposition", "attachment; filename=\"" + nombreFichero + "\"");
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();

			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeIndiviAccesoGrado: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeIndiviAccesoGrado: ERROR" + e.getMessage());
			throw new Exception("Informe Individualizado de Acceso a Grado NO generado");
		}
	}

	public OCAPSolicitudesOT insertar_a(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario) throws Exception {
		Connection con;
		int idUsr = 0;
		int idExpediente = 0;
		con = null;
		try {
			OCAPConfigApp.logger
					.info((new StringBuilder()).append(getClass().getName()).append(" insertar_a: Inicio").toString());
			JCYLGestionTransacciones.open(false);
			OCAPUsuariosOAD usuarioOAD = OCAPUsuariosOAD.getInstance();
			OCAPExpedientecarreraOAD expedienteCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
			OCAPUsuariosOT usuariosOT = new OCAPUsuariosOT();
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();
			usuariosOT.setDApellido1(solicitudesOT.getDApellido1());
			usuariosOT.setDNombre(solicitudesOT.getDNombre());
			usuariosOT.setCDni(solicitudesOT.getCDniReal());
			usuariosOT.setCDniReal(solicitudesOT.getCDniReal());
			usuariosOT.setBSexo(solicitudesOT.getBSexo());
			if (solicitudesOT.getNTelefono1() != null && !solicitudesOT.getNTelefono1().equals(""))
				usuariosOT.setNTelefono1(Integer.valueOf(solicitudesOT.getNTelefono1()));
			if (solicitudesOT.getNTelefono2() != null && !solicitudesOT.getNTelefono2().equals(""))
				usuariosOT.setNTelefono2(Integer.valueOf(solicitudesOT.getNTelefono2()));
			usuariosOT.setDCorreoelectronico(solicitudesOT.getDCorreoelectronico());
			usuariosOT.setDDomicilio(solicitudesOT.getDDomicilio());
			usuariosOT.setDProvinciaUsu(solicitudesOT.getDProvinciaUsu());
			usuariosOT.setCProvinciaUsu_id(solicitudesOT.getCProvinciaUsu_id());
			usuariosOT.setDLocalidadUsu(solicitudesOT.getDLocalidadUsu());
			usuariosOT.setCPostalUsu(solicitudesOT.getCPostalUsu());
			usuariosOT.setCProfesionalId(Integer.valueOf(String.valueOf(solicitudesOT.getCProfesional_id())));
			usuariosOT.setCEspecId(Integer.valueOf(String.valueOf(solicitudesOT.getCEspec_id())));
			usuariosOT.setCGerenciaId(Integer.valueOf(String.valueOf(solicitudesOT.getCGerencia_id())));
			usuariosOT.setCCentrotrabajoId(Integer.valueOf(String.valueOf(solicitudesOT.getCCentrotrabajo_id())));
			usuariosOT.setFPlazapropiedad(solicitudesOT.getFPlazapropiedad());
			usuariosOT.setBBorrado(Constantes.NO);
			usuariosOT.setACreadoPor(solicitudesOT.getACreadoPor());
			idUsr = usuarioOAD.altaOCAPUsuarios(usuariosOT);
			expedienteCarreraOT.setBBorrado(Constantes.NO);
			expedienteCarreraOT.setCGradoId(Integer.valueOf(String.valueOf(solicitudesOT.getCGrado_id())));
			expedienteCarreraOT.setCModAnteriorId(solicitudesOT.getCModAnterior_id());
			expedienteCarreraOT.setCUsrId(Long.valueOf(String.valueOf(idUsr)));
			expedienteCarreraOT.setFRegistroSolic(DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL,
					solicitudesOT.getFRegistro_solic()));
			expedienteCarreraOT.setNAniosAntiguedad(solicitudesOT.getNAniosantiguedad());
			expedienteCarreraOT.setNMesesAntiguedad(solicitudesOT.getNMesesantiguedad());
			expedienteCarreraOT.setNDiasAntiguedad(solicitudesOT.getNDiasantiguedad());
			expedienteCarreraOT.setCConvocatoriaId(solicitudesOT.getCConvocatoriaId());
			expedienteCarreraOT.setACreadoPor(solicitudesOT.getACreadoPor());
			expedienteCarreraOT.setCProcedimientoId(solicitudesOT.getCProcedimientoId());
			expedienteCarreraOT.setCSitAdministrativaAuxId(solicitudesOT.getCSitAdministrativaAuxId());
			expedienteCarreraOT.setDSitAdministrativaAuxOtros(solicitudesOT.getDSitAdministrativaAuxOtros());
			expedienteCarreraOT.setCEstatutId(solicitudesOT.getCEstatutId());
			expedienteCarreraOT.setBOtroServicio(solicitudesOT.getBOtroServicio());
			expedienteCarreraOT.setADondeServicio(solicitudesOT.getADondeServicio());
			expedienteCarreraOT.setCJuridico(solicitudesOT.getCJuridico());
			expedienteCarreraOT.setFRegistroOficial(DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL,
					solicitudesOT.getFRegistro_oficial()));
			expedienteCarreraOT.setCEspecActual_id(solicitudesOT.getCEspecActual_id());
			expedienteCarreraOT.setAServicio(solicitudesOT.getAServicio());
			expedienteCarreraOT.setAPuesto(solicitudesOT.getAPuesto());
			expedienteCarreraOT.setCSolicitudId(solicitudesOT.getCSolicitudId());
			expedienteCarreraOT.setCProfesionalId(solicitudesOT.getCProfesional_id());
			expedienteCarreraOT.setCEspecId(solicitudesOT.getCEspec_id());
			expedienteCarreraOT.setDRegimenJuridicoOtros(solicitudesOT.getDRegimenJuridicoOtros());
			expedienteCarreraOT.setCEstadoId(solicitudesOT.getCEstado_id());
			idExpediente = expedienteCarreraOAD.altaOCAPExpedientecarrera(expedienteCarreraOT);
			solicitudesOT.setCUsr_id(idUsr);
			solicitudesOT.setCExp_id(idExpediente);
			OCAPOtrosCentrosLN otrosCentrosLN = new OCAPOtrosCentrosLN(jcylUsuario);
			otrosCentrosLN.modificarOtrosCentros(idExpediente, solicitudesOT.getCSolicitudId(),
					solicitudesOT.getACreadoPor(), con);
			JCYLGestionTransacciones.commit(true);
			OCAPConfigApp.logger
					.info((new StringBuilder()).append(getClass().getName()).append(" insertar: Fin").toString());
		} catch (Exception e) {
			OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName()).append(" insertar: ERROR: ")
					.append(e.toString()).toString());
			JCYLGestionTransacciones.rollback();
			throw e;
		} finally {
			JCYLGestionTransacciones.close(true);
		}

		return solicitudesOT;
	}

	/**
	 * 
	 * @param jcylUsuario
	 * @param cConvocatoriaId
	 * @return
	 * @throws Exception
	 */
	public ArrayList existeSolicParaUsrYConv(JCYLUsuario jcylUsuario, String cConvocatoriaId,
			OCAPNuevaSolicitudForm form) throws Exception {

		try{

			OCAPConfigApp.logger.info("existeSolicParaUsrYConv");
			
			OCAPSolicitudesOAD solicOAD = OCAPSolicitudesOAD.getInstance();
			ArrayList solicitudesUsrConv = null;
			
			solicitudesUsrConv = solicOAD.existeSolicParaUsrYConv(form.getCDniReal(), cConvocatoriaId);

			if (solicitudesUsrConv != null && solicitudesUsrConv.size() > 0) {
				((OCAPNuevaSolicitudForm) form)
						.setDApellido1(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getDApellido1());
				((OCAPNuevaSolicitudForm) form)
						.setDApellido2(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getDApellido2());
				((OCAPNuevaSolicitudForm) form)
						.setDNombre(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getDNombre());
				((OCAPNuevaSolicitudForm) form).setCDni(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCDni());
				((OCAPNuevaSolicitudForm) form).setCDniReal(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCDni());
				((OCAPNuevaSolicitudForm) form).setBSexo(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getBSexo());
				((OCAPNuevaSolicitudForm) form)
						.setNTelefono1(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getNTelefono1());
				((OCAPNuevaSolicitudForm) form)
						.setNTelefono2(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getNTelefono2());
				((OCAPNuevaSolicitudForm) form)
						.setDCorreoelectronico(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getDCorreoelectronico());
				((OCAPNuevaSolicitudForm) form)
						.setDDomicilio(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getDDomicilio());
				((OCAPNuevaSolicitudForm) form)
						.setCProvincia_id(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCProvincia_id());
				OCAPProvinciasLN provLN = new OCAPProvinciasLN(jcylUsuario);
				((OCAPNuevaSolicitudForm) form).setDProvincia(
						provLN.buscarProvincia(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCProvincia_id())
								.getDProvincia());
				OCAPLocalidadesLN locLN = new OCAPLocalidadesLN(jcylUsuario);
				if(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getDLocalidad() != null) {
					((OCAPNuevaSolicitudForm) form).setCLocalidad_id(
						locLN.buscarLocalidadByName(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getDLocalidad())
								.getCLocalidadId());
				}
				((OCAPNuevaSolicitudForm) form)
						.setDLocalidad(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getDLocalidad());
				((OCAPNuevaSolicitudForm) form)
						.setCPostalUsu(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCPostalUsu());

				((OCAPNuevaSolicitudForm) form)
						.setCJuridicoCombo(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCJuridicoId());
				((OCAPNuevaSolicitudForm) form)
						.setCJuridicoId(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCJuridicoId());
				((OCAPNuevaSolicitudForm) form).setCSitAdministrativaId(
						((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCSitAdministrativaAuxId());
				((OCAPNuevaSolicitudForm) form).setCGerencia_id(
						String.valueOf(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCGerencia_id()));
				((OCAPNuevaSolicitudForm) form).setCCentrotrabajo_id(
						String.valueOf(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCCentrotrabajo_id()));
				((OCAPNuevaSolicitudForm) form)
						.setCProcedimientoId(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCProcedimientoId());
				((OCAPNuevaSolicitudForm) form).setCProfesionalFijo_id(
						String.valueOf(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCProfesional_id()));
				((OCAPNuevaSolicitudForm) form).setCProfesional_id(
						String.valueOf(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCProfesional_id()));
				((OCAPNuevaSolicitudForm) form)
						.setCEspec_id(String.valueOf(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCEspec_id()));
				((OCAPNuevaSolicitudForm) form).setCEspecFijo_id(
						String.valueOf(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCEspec_id()));
				((OCAPNuevaSolicitudForm) form).setNAniosantiguedad(
						String.valueOf(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getNAniosantiguedad()));
				((OCAPNuevaSolicitudForm) form).setNMesesantiguedad(
						String.valueOf(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getNMesesantiguedad()));
				((OCAPNuevaSolicitudForm) form).setNDiasantiguedad(
						String.valueOf(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getNDiasantiguedad()));
				((OCAPNuevaSolicitudForm) form)
						.setFRegistro_solic(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getFRegistro_solic());
				((OCAPNuevaSolicitudForm) form)
						.setBLopdSolicitud(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getBLopdSolicitud());
				((OCAPNuevaSolicitudForm) form)
						.setFRegistro_oficial(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getFRegistro_oficial());
				((OCAPNuevaSolicitudForm) form).setDSitAdministrativaOtros(
						((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getDSitAdministrativaOtros());
				((OCAPNuevaSolicitudForm) form).setCProvinciaCarrera_id(
						((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCProvinciaCarrera_id());
				((OCAPNuevaSolicitudForm) form)
						.setCUsr_id(String.valueOf(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCUsr_id()));
				((OCAPNuevaSolicitudForm) form)
						.setCEstadoId(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCEstado_id());
				((OCAPNuevaSolicitudForm) form).setCModAnteriorId(
						String.valueOf(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCModAnterior_id()));
				((OCAPNuevaSolicitudForm) form)
						.setADondeServicio(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getADondeServicio());
				((OCAPNuevaSolicitudForm) form).setDRegimenJuridicoOtros(
						((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getDRegimenJuridicoOtros());
				((OCAPNuevaSolicitudForm) form)
						.setDServicio(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getAServicio());
				((OCAPNuevaSolicitudForm) form)
						.setDPuesto(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getAPuesto());
				((OCAPNuevaSolicitudForm) form).setCSitAdmonActualId(
						String.valueOf(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCSitAdmonActualId()));
				((OCAPNuevaSolicitudForm) form).setAOtraSitAdmonActual(
						((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getAOtraSitAdmonActual());
				((OCAPNuevaSolicitudForm) form).setCGerenciaActualId(
						String.valueOf(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCGerenciaActualId()));
				((OCAPNuevaSolicitudForm) form).setCGerenciaActualId(
						String.valueOf(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCGerenciaActualId()));
				OCAPGerenciasLN gerLN = new OCAPGerenciasLN(jcylUsuario);
				((OCAPNuevaSolicitudForm) form).setDGerenciaActual(gerLN
						.buscarOCAPGerencias(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCGerenciaActualId())
						.getDNombre());
				((OCAPNuevaSolicitudForm) form).setCCentroTrabActualId(
						String.valueOf(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCCentroTrabActualId()));
				((OCAPNuevaSolicitudForm) form).setCProvCarreraActualId(
						((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getDProvCarreraActual());
				((OCAPNuevaSolicitudForm) form).setDProvCarreraActual(
						provLN.buscarProvincia(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getDProvCarreraActual())
								.getDProvincia());
				((OCAPNuevaSolicitudForm) form).setCConvocatoriaId(
						String.valueOf(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCConvocatoriaId()));
				((OCAPNuevaSolicitudForm) form)
						.setCGrado_id(String.valueOf(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCGrado_id()));
				((OCAPNuevaSolicitudForm) form)
						.setCSolicitudId(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCSolicitudId());
				// ((OCAPNuevaSolicitudForm) form)
				// .setBOtrosCentros(((OCAPSolicitudesOT)
				// solicitudesUsrConv.get(0)).getBOtrosCentros());

				OCAPOtrosCentrosLN otrosCentrosLN = new OCAPOtrosCentrosLN(this.jcylUsuario);
				ArrayList listaOtrosCentros = otrosCentrosLN
						.buscarOCAPOtrosCentrosSolic(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCSolicitudId());
				((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).setListaOtrosCentros(listaOtrosCentros);
				OCAPOtrosCentrosOT otrosCentrosOT = null;
				String cadenaCentros = "";
				for (int i = 0; i < listaOtrosCentros.size(); i++) {
					otrosCentrosOT = (OCAPOtrosCentrosOT) listaOtrosCentros.get(i);
					cadenaCentros = cadenaCentros + otrosCentrosOT.getDNombre() + "$" + otrosCentrosOT.getAProvincia()
							+ "$" + otrosCentrosOT.getACategoria() + "$" + otrosCentrosOT.getASituacion() + "$"
							+ otrosCentrosOT.getAVinculo() + "$"
							+ DateUtil.convertDateToString(otrosCentrosOT.getFInicio()) + "$"
							+ DateUtil.convertDateToString(otrosCentrosOT.getFFin()) + "#";
				}

				if (!"".equals(cadenaCentros)) {
					((OCAPNuevaSolicitudForm) form)
							.setResumenCentros(cadenaCentros.substring(0, cadenaCentros.length() - 1));
				}
			}

			return solicitudesUsrConv;
		} catch (SQLException exSQL) {
			this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: "
					+ exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
			throw exSQL;
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
	}

	private boolean esUsuarioPGP(JCYLUsuario jcylUsuario) {
		if(jcylUsuario != null && jcylUsuario.getUser() != null && 
				jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(PERFIL_PGP)
				&& (jcylUsuario.getUser().getC_usr_id() != null && !jcylUsuario.getUser().getC_usr_id().equals(USUARIO_PUBLICO))){
			return true;
		}
		return false;
	}
	
	
	public OCAPAccesoOT usuarioTieneSolicitudAceptada (String aDni) throws Exception {
		
		OCAPAccesoOT  ocapAccesoOt = null ;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " usuarioTieneSolicitudAceptada: Inicio");

			OCAPSolicitudesOAD solicitudesOAD = OCAPSolicitudesOAD.getInstance();

			 ocapAccesoOt= solicitudesOAD.usuarioTieneSolicitudEnEstado(aDni, Constantes.ESTADO_ACEPTADA);

			OCAPConfigApp.logger.info(getClass().getName() + " usuarioTieneSolicitudAceptada: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " usuarioTieneSolicitudAceptada: ERROR: " + e.getMessage());
			throw e;
		}
		return ocapAccesoOt;
		
	}
	
	public OCAPAccesoOT usuarioTieneSolicitud(String aDni) throws Exception {

		OCAPAccesoOT ocapAccesoOt = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " usuarioTieneSolicitud: Inicio");

			OCAPSolicitudesOAD solicitudesOAD = OCAPSolicitudesOAD.getInstance();

			ocapAccesoOt = solicitudesOAD.usuarioTieneSolicitudEnEstado(aDni, Constantes.ESTADO_SIN_ESTADO);

			OCAPConfigApp.logger.info(getClass().getName() + " usuarioTieneSolicitud: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " usuarioTieneSolicitud: ERROR: " + e.getMessage());
			throw e;
		}
		return ocapAccesoOt;

	}
	
	public OCAPSolicitudesOT buscarSolicitud (long idSolicitud) {
		OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitud: Inicio");
		
		OCAPSolicitudesOAD solicitudesOAD = OCAPSolicitudesOAD.getInstance();
		OCAPSolicitudesOT solicitud = solicitudesOAD.buscarSolicitud(idSolicitud);
		
		OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitud: Fin");
		return solicitud;

	}

	public void crearInformeReconociGradoTotal(JCYLUsuario jcylUsuario, HttpServletResponse response,
			List<OCAPSolicitudesOT> listadoSolicitudes, String pathBase) throws Exception {

		Hashtable parametros = new Hashtable();
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		List<JasperPrint> listadoPdf = new ArrayList<JasperPrint>();
		OCAPConvocatoriasLN convoLN = null;
		OCAPConvocatoriasOT convoOT = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeReconociGradoTotal: Inicio");

			
			String nombreReport = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "informeReconociGradoPGP" + ".jasper";

			File fichReport = new File(nombreReport);
			jasperReport = (JasperReport) JRLoader.loadObject(fichReport);			

			// Gerencia
			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			Map parametrosUsuario = jcylUsuario.getParametrosUsuario();
			OCAPGerenciasOT gerencia = gerenciasLN
					.buscarOCAPGerencias(Long.parseLong((String) parametrosUsuario.get("PARAM_GERENCIA")));
			gerencia.setDProvinciaNombre(Cadenas.capitalizar(gerencia.getDProvinciaNombre()));
			

			for (OCAPSolicitudesOT elemento: listadoSolicitudes) {
				jasperPrint = new JasperPrint();
				jasperReport = (JasperReport) JRLoader.loadObject(fichReport);
				parametros = new Hashtable();
				
				parametros.put("ruta", pathBase);
				parametros.put("gerencia", gerencia);
				
				OCAPSolicitudesOT solicitudOT = elemento;
				// Fecha resolucion de convocaroria
				convoLN = new OCAPConvocatoriasLN(jcylUsuario);
				convoOT = convoLN.buscarOCAPConvocatorias(solicitudOT.getCConvocatoriaId());
				String fechaResolucionCon = convoOT.getFResolucion();
				parametros.put("fechaResConvocatoria", fechaResolucionCon);
				
				// Solicitud
				solicitudOT.setDNombreApellidos(
						solicitudOT.getDNombre().toUpperCase() + " " + solicitudOT.getDApellido1().toUpperCase());
				anhadirTratamientoInformeReconociGrado(solicitudOT);
				String fSesionImprimible = DateUtil.convertDateToStringLargaMesMayus(Constantes.FECHA_LETRA_D,
						solicitudOT.getFSesion());
				solicitudOT.setFSesionImprimible(fSesionImprimible);
				parametros.put("datosDocu", solicitudOT);

				jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
				
				listadoPdf.add(jasperPrint);
			}		

			OutputStream out = response.getOutputStream();
			SimpleDateFormat ahora = new SimpleDateFormat("ddMMyyyy_hhmm");
			String nombreFichero = "informeReconocimientoGrado" + ahora.format(new Date()) + ".pdf";
			response.setHeader("Content-disposition", "attachment; filename=\"" + nombreFichero + "\"");
			response.setContentType("application/pdf");

			
			JRPdfExporter exporter = new JRPdfExporter();
		    exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, listadoPdf);
		    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		    exporter.exportReport();

			OCAPConfigApp.logger.info(getClass().getName() + " crearInformeReconociGrado: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(getClass().getName() + " crearInformeReconociGrado: ERROR" + e.getMessage());
			throw new Exception("Informe de Reconocimiento de Grado NO generado");
		}
	
		
	}
	
}
