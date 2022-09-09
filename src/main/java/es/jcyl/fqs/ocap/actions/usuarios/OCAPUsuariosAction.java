package es.jcyl.fqs.ocap.actions.usuarios;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.jcyl.fqs.ocap.actionforms.usuarios.OCAPUsuariosForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
import es.jcyl.fqs.ocap.ln.centroTrabajo.OCAPCentroTrabajoLN;
import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
import es.jcyl.fqs.ocap.ln.especialidades.OCAPEspecialidadesLN;
import es.jcyl.fqs.ocap.ln.estatutario.OCAPEstatutarioLN;
import es.jcyl.fqs.ocap.ln.expedienteCarrera.OCAPExpedienteCarreraLN;
import es.jcyl.fqs.ocap.ln.expedientes.OCAPReportExpedientesLN;
import es.jcyl.fqs.ocap.ln.gerencias.OCAPGerenciasLN;
import es.jcyl.fqs.ocap.ln.modalidadAnterior.OCAPModalidadAnteriorLN;
import es.jcyl.fqs.ocap.ln.procedimiento.OCAPProcedimientoLN;
import es.jcyl.fqs.ocap.ln.provincias.OCAPProvinciasLN;
import es.jcyl.fqs.ocap.ln.solicitudes.OCAPNuevaSolicitudLN;
import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
import es.jcyl.fqs.ocap.ot.categProfesionales.OCAPCategProfesionalesOT;
import es.jcyl.fqs.ocap.ot.centroTrabajo.OCAPCentroTrabajoOT;
import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
import es.jcyl.fqs.ocap.ot.especialidades.OCAPEspecialidadesOT;
import es.jcyl.fqs.ocap.ot.estatutario.OCAPEstatutarioOT;
import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;
import es.jcyl.fqs.ocap.ot.expedienteMC.OCAPExpedientemcOT;
import es.jcyl.fqs.ocap.ot.expedientes.OCAPReportExpedientesOT;
import es.jcyl.fqs.ocap.ot.gerencias.OCAPGerenciasOT;
import es.jcyl.fqs.ocap.ot.meritosCurriculares.OCAPMeritoscurricularesOT;
import es.jcyl.fqs.ocap.ot.modalidadAnterior.OCAPModalidadAnteriorOT;
import es.jcyl.fqs.ocap.ot.procedimiento.OCAPProcedimientoOT;
import es.jcyl.fqs.ocap.ot.provincias.OCAPProvinciasOT;
import es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.TrataError;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.fqs.ocap.util.reports.Report;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLUsuario;

public class OCAPUsuariosAction extends OCAPGenericAction {
	private static final String FORMA_NUEVO_PERSONAL = "FormaNuevoPersonal";
	private JCYLUsuario jcylUsuario;
	public ActionForward irInsertar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		Date dHoy = null;
		OCAPUsuariosOT usuariosOT = null;
		String usuId = null;
		OCAPConvocatoriasLN convocatoriaLN = null;
		OCAPConvocatoriasOT convoOT = null;
		boolean modifFechas = false;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			long cExpId = 0L;
			// Si entra el CEIS, viene el cExpId en la request
			if (request.getParameter("CExp_id") != null) {
				session.setAttribute("CExp_id", request.getParameter("CExp_id"));
			}
			if (session.getAttribute("CExp_id") != null) {
				cExpId = Long.parseLong((String) session.getAttribute("CExp_id"));
				OCAPExpedienteCarreraLN expedientesLN = new OCAPExpedienteCarreraLN(jcylUsuario);
				usuId = expedientesLN.buscarDNIUsuarioExpediente(cExpId);
			} else if (request.getParameter("cDni") != null) {
				usuId = request.getParameter("cDni");
			} else {
				usuId = jcylUsuario.getUser().getC_usr_id();
			}

			((OCAPUsuariosForm) form).setPestanaSeleccionada(
					request.getParameter("pestana") == null ? Constantes.EMPTY : request.getParameter("pestana"));

			OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);
			usuariosOT = usuariosLN.datosPersonalesUsuario(usuId, cExpId, jcylUsuario);
			
			if (usuariosOT == null) {
				return mapping.findForward("irNoExisteSolicitud");
			}

			OCAPConfigApp.logger
					.info(getClass().getName() + " irInsertar: Usuario Encontrado: " + usuariosOT.getCUsrId());

			convocatoriaLN = new OCAPConvocatoriasLN(jcylUsuario);
			convoOT = convocatoriaLN.buscarOCAPConvocatorias(usuariosOT.getCConvocatoriaId());
			
			
			if (convoOT!=null){
				((OCAPUsuariosForm) form).setDConvocatoriaNombre(convoOT.getDNombre());	
				((OCAPUsuariosForm) form).setDAnioConvocatoria(convoOT.getDAnioConvocatoria());	
				JCYLConfiguracion.setValor("FECHA_COMPROBAR_MC", "31/12/"+convoOT.getDAnioConvocatoria());				
				OCAPConfigApp.loggerTrazas.warn ("TRAZA PROD- Linea 115 Meto en sesión atributo FECHA_COMPROBAR_MC con valor: " + "31/12/"+convoOT.getDAnioConvocatoria());
			}
			
			OCAPExpedienteCarreraLN expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			OCAPExpedientecarreraOT expedienteCarreraOT = expCarreraLN.buscarExpedienteCarreraPorUsuarioConvocatoria(
					usuariosOT.getCUsrId(), Long.valueOf(usuariosOT.getCConvocatoriaId()));

			/*
			 * Fijamos si estamos en periodo de Valoración de MC OCAP 21 :
			 * Tomanos las fechas indicadas en el expediente, no en la convocatoria
			 */
			String fechaOriginalValoracionMC = convoOT.getFInicioValoracionMC();
			long plazoOriginalValMC = convoOT.getNDiasValMc();
			Calendar c = Calendar.getInstance();
			/* Datos de convocatoria antiguos => calculamos fechas */
			if (expedienteCarreraOT.getFFinEvalMc() == null) {
				Calendar aux = Calendar.getInstance();
				/* F Inicio Valoracion MC = F Inicio MC + Plazo autoev MC */
				aux.setTime(DateUtil.convertStringToDate(convoOT.getFInicioMC()));
				aux.add(Calendar.DATE, (int) convoOT.getNDiasAutoMc());
				convoOT.setFInicioValoracionMC(DateUtil.convertDateToString(aux.getTime()));
				aux.setTime(DateUtil.convertStringToDate(convoOT.getFInicioValoracionMC()));
			    // F Fin Valoracion MC = F Inicio Valoracion MC + Plazo valoracion MC
				aux.add(Calendar.DATE, (int) convoOT.getNDiasValMc());
				convoOT.setFFinValoracionMC(DateUtil.convertDateToString(aux.getTime()));
				expedienteCarreraOT.setFFinEvalMc(aux.getTime());
			}
			

			/* Fijamos si estamos en periodo de Valoración de CC */
			c = Calendar.getInstance();
			/* Datos de convocatoria antiguos => calculamos fechas */

			if (expedienteCarreraOT.getFFinCc() == null) {
				Calendar aux = Calendar.getInstance();
				/* F Inicio Valoracion CC = F Fin Valoracion MC + 1 */
				aux.setTime(DateUtil.convertStringToDate(convoOT.getFFinValoracionMC()));
				aux.add(Calendar.DATE, 1);
				convoOT.setFInicioValoracionCC(DateUtil.convertDateToString(aux.getTime()));
				aux.setTime(DateUtil.convertStringToDate(convoOT.getFInicioValoracionCC()));
				 // F Fin Valoracion CC = F Inicio Valoracion CC + Plazo valoracion CC
				aux.add(Calendar.DATE, (int) convoOT.getNDiasValCc());
				expedienteCarreraOT.setFFinCc(aux.getTime());

			}
			

			/*RFDEZ: En estos puntos se actualiza la fecha de inicio de evaluación de MC, reemplazando lo que haya en el expediente, por lo que diga
			 *       la convocatoria asociada
			 *       Cambiamos la asociación a los nuevos campos de Convocatorioa (si en el expediente no hay datos de Finicio/FFin evaluacion MC, cogera
			 *       los campos similares de la convocatoria.
			 */
			if ((convoOT.getFInicioValoracionMC() != null) && ((usuariosOT.getFInicioEvalMC() == null) ||
				(usuariosOT.getFInicioEvalMC().before(DateUtil.convertStringToDate(convoOT.getFInicioValoracionMC()))))) {
				expedienteCarreraOT.setFInicioEvalMc(DateUtil.convertStringToDate(fechaOriginalValoracionMC));
				usuariosOT.setFInicioEvalMC(DateUtil.convertStringToDate(fechaOriginalValoracionMC));
				modifFechas = true;
			}

			if ((convoOT.getFFinValoracionMC() != null) && ((usuariosOT.getFFinEvalMC() == null)
					|| (usuariosOT.getFFinEvalMC().before(DateUtil.convertStringToDate(convoOT.getFFinValoracionMC()))))) {
				Calendar aux = Calendar.getInstance();
				aux.setTime(DateUtil.convertStringToDate(fechaOriginalValoracionMC));
				aux.add(Calendar.DATE, (int)plazoOriginalValMC);
				expedienteCarreraOT.setFFinEvalMc(aux.getTime());
				usuariosOT.setFFinEvalMC(aux.getTime());
				modifFechas = true;
			}
					
			expedienteCarreraOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());
			if (modifFechas)
				expCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);

			
			
			((OCAPUsuariosForm) form).setenPeriodoValMc(Constantes.NO);
			((OCAPUsuariosForm) form).setenPeriodoValCc(Constantes.NO);
			
			/* Durante el último día de Val MC también se valoran méritos */
			c.setTime(expedienteCarreraOT.getFFinEvalMc());
			c.add(Calendar.DATE, 1);
			/* Si estamos entre las fechas de inicio y fin */
			if ((new Date()).getTime() >= (expedienteCarreraOT.getFInicioEvalMc()).getTime()
					&& (new Date()).getTime() < c.getTime().getTime()) {
				((OCAPUsuariosForm) form).setenPeriodoValMc(Constantes.SI);
			}
			
			/* Durante el último día de Val CC también se valoran méritos */
			c.setTime(expedienteCarreraOT.getFFinCc());
			c.add(Calendar.DATE, 1);
			/* Si estamos entre las fechas de inicio y fin */
			if ((expedienteCarreraOT.getFInicioCc() != null) && ((new Date()).getTime() >= (expedienteCarreraOT.getFInicioCc()).getTime()
					&& (new Date()).getTime() < c.getTime().getTime()))
			{
				((OCAPUsuariosForm) form).setenPeriodoValCc(Constantes.SI);
			}

			session.setAttribute("Convocatoria", Long.valueOf(expedienteCarreraOT.getCConvocatoriaId()));

			OCAPCategProfesionalesLN categoriaLN = new OCAPCategProfesionalesLN(jcylUsuario);
			OCAPCategProfesionalesOT categoriaOT = categoriaLN.buscarOCAPCategProfesionales(expedienteCarreraOT.getCProfesionalId());

			((OCAPUsuariosForm) form).setDProfesional_nombre(categoriaOT.getDNombre());
			((OCAPUsuariosForm) form).setCProfesional_id(String.valueOf(expedienteCarreraOT.getCProfesionalId()));

			usuariosOT = usuariosLN.datosMCUsuario(usuariosOT, ((OCAPUsuariosForm) form).getPestanaSeleccionada());
			
			//OCAP-1390
			ordenacionPersonalNuevaIncorporacion(usuariosOT);
			
			OCAPNuevaSolicitudLN solicitudesLN = new OCAPNuevaSolicitudLN(jcylUsuario);
			
			OCAPSolicitudesOT solicitudUsuarioOT = solicitudesLN.detalleSolicitudconIdExpdte(usuariosOT.getCExpId());
			
			if (usuariosOT == null) {
				errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.noExistenMeritos"));
			} else {
				((OCAPUsuariosForm) form).setCExp_id(String.valueOf(usuariosOT.getCExpId().longValue()));
				((OCAPUsuariosForm) form).setCExpId(usuariosOT.getCExpId());
				((OCAPUsuariosForm) form).setCConvocatoriaId(Long.toString(usuariosOT.getCConvocatoriaId()));

				if (expedienteCarreraOT.getBLopdMc() == null) {
					expedienteCarreraOT.setAModificadoPor(((JCYLUsuario) request.getSession().getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO)).getUsuario().getC_usr_id());
					expedienteCarreraOT.setBLopdMc(Constantes.SI);
					expCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT,false);
				}

				((OCAPUsuariosForm) form).setCUsr_id(usuariosOT.getCUsrId().toString());
				((OCAPUsuariosForm) form).setDNombre(solicitudUsuarioOT.getDNombre());
				((OCAPUsuariosForm) form).setDApellido1(solicitudUsuarioOT.getDApellido1());
				((OCAPUsuariosForm) form).setBSexo(solicitudUsuarioOT.getBSexo());
				((OCAPUsuariosForm) form).setDCorreoelectronico(solicitudUsuarioOT.getDCorreoelectronico());

				if (solicitudUsuarioOT.getNTelefono1() != null)
					((OCAPUsuariosForm) form).setNTelefono1(solicitudUsuarioOT.getNTelefono1());
				else {
					((OCAPUsuariosForm) form).setNTelefono1("-");
				}
				if (solicitudUsuarioOT.getNTelefono2() != null)
					((OCAPUsuariosForm) form).setNTelefono2(solicitudUsuarioOT.getNTelefono2());
				else {
					((OCAPUsuariosForm) form).setNTelefono2("-");
				}
				((OCAPUsuariosForm) form).setCDni(solicitudUsuarioOT.getCDni());
				((OCAPUsuariosForm) form).setCDniReal(solicitudUsuarioOT.getCDni());
				((OCAPUsuariosForm) form).setADomicilio(solicitudUsuarioOT.getDDomicilio());
				((OCAPUsuariosForm) form).setDProvincia(solicitudUsuarioOT.getDProvinciaUsu());
				((OCAPUsuariosForm) form).setDLocalidad(solicitudUsuarioOT.getDLocalidad());

				DecimalFormat formato = new DecimalFormat("00000");
				((OCAPUsuariosForm) form).setCPostalUsu(solicitudUsuarioOT.getCPostalUsu() == null ? "-"
						: formato.format(Long.parseLong(solicitudUsuarioOT.getCPostalUsu())));

				((OCAPUsuariosForm) form).setDGrado_des(usuariosOT.getDGrado_des());
				((OCAPUsuariosForm) form).setCGrado_id(String.valueOf(usuariosOT.getCGrado_id()));

				// MODALIDAD
				OCAPModalidadAnteriorLN modalidadLN = new OCAPModalidadAnteriorLN(jcylUsuario);
				OCAPModalidadAnteriorOT modalidadOT = null;
				if (expedienteCarreraOT.getCModAnteriorId() != 0L) {
					modalidadOT = modalidadLN.buscarOCAPModalidad(expedienteCarreraOT.getCModAnteriorId());
					((OCAPUsuariosForm) form).setDModAnterior(modalidadOT.getDDescripcion());
				}
				// PROCEDIMIENTO
				OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
				OCAPProcedimientoOT procOT = null;
				if ((expedienteCarreraOT.getCProcedimientoId() != null)
						&& (!Constantes.EMPTY.equals(expedienteCarreraOT.getCProcedimientoId()))) {
					procOT = procLN.buscarOCAPProcedimiento(Long.parseLong(expedienteCarreraOT.getCProcedimientoId()));
					((OCAPUsuariosForm) form).setDProcedimiento(procOT.getDNombre());
					((OCAPUsuariosForm) form).setCProcedimientoId(procOT.getCProcedimientoId().toString());
				}

				((OCAPUsuariosForm) form).setCJuridico(expedienteCarreraOT.getCJuridico());

				OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
				OCAPEstatutarioOT estatutarioOT = estatutarioLN.buscarOCAPEstatutario(categoriaOT.getCEstatutId());
				((OCAPUsuariosForm) form).setCPersonalId(String.valueOf(estatutarioOT.getCPersonalId()));

				if (Constantes.C_JURIDICO_ESTATUTARIO_COD.equals(expedienteCarreraOT.getCJuridico())) {
					((OCAPUsuariosForm) form).setCJuridicoCombo(String.valueOf(estatutarioOT.getCPersonalId()));
				}

				if (Constantes.C_JURIDICO_OTROS_COD.equals(expedienteCarreraOT.getCJuridico())) {
					((OCAPUsuariosForm) form).setDRegimenJuridicoOtros(expedienteCarreraOT.getDRegimenJuridicoOtros());
				}

				((OCAPUsuariosForm) form).setCSitAdministrativaAuxId(expedienteCarreraOT.getCSitAdministrativaAuxId());
				((OCAPUsuariosForm) form).setDSitAdministrativaAuxOtros(expedienteCarreraOT.getDSitAdministrativaAuxOtros());

				// Obtenemos la descripción de la Especialidad
				OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
				OCAPEspecialidadesOT especialidadesOT = especialidadesLN.buscarOCAPEspecialidades(expedienteCarreraOT.getCEspecId());
				((OCAPUsuariosForm) form).setDEspec_nombre(especialidadesOT.getDNombre());

				// Obtenemos la descripción de la Gerencia y el codigo de su provincia
				OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
				OCAPGerenciasOT gerenciasOT = gerenciasLN.buscarOCAPGerencias(Long.parseLong(String.valueOf(solicitudUsuarioOT.getCGerenciaActualId())));
				((OCAPUsuariosForm) form).setDGerencia_nombre(gerenciasOT.getDNombre());

				OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
				OCAPProvinciasOT provinciasOT = provinciasLN.buscarProvincia(gerenciasOT.getCProvinciaId());

				if ((gerenciasOT.getCProvinciaId() != null) && (!gerenciasOT.getCProvinciaId().equals(Constantes.EMPTY))) {
					((OCAPUsuariosForm) form).setDProvinciaCarrera(Cadenas.capitalizar(provinciasOT.getDProvincia()));
				}

				// Obtenemos la descripción del Centro de Trabajo
				OCAPCentroTrabajoLN centroTrabajoLN = new OCAPCentroTrabajoLN(jcylUsuario);
				OCAPCentroTrabajoOT centroTrabajoOT = centroTrabajoLN.buscarOCAPCentroTrabajo(solicitudUsuarioOT.getCCentroTrabActualId());
				((OCAPUsuariosForm) form).setDCentrotrabajo_nombre(centroTrabajoOT.getDNombre());

				((OCAPUsuariosForm) form).setCEstatutId(usuariosOT.getCEstatutId());
				((OCAPUsuariosForm) form).setCExpId(usuariosOT.getCExpId());

				((OCAPUsuariosForm) form).setDServicio(expedienteCarreraOT.getAServicio());
				((OCAPUsuariosForm) form).setDPuesto(expedienteCarreraOT.getAPuesto());

				((OCAPUsuariosForm) form).setNAniosantiguedad(String.valueOf(expedienteCarreraOT.getNAniosAntiguedad()));
				((OCAPUsuariosForm) form).setNMesesantiguedad(String.valueOf(expedienteCarreraOT.getNMesesAntiguedad()));
				((OCAPUsuariosForm) form).setNDiasantiguedad(String.valueOf(expedienteCarreraOT.getNDiasAntiguedad()));

				/*
				 * OCAP-102 
				 */
				((OCAPUsuariosForm) form).setDMensaje(usuariosOT.getDMensaje().replace("[año]", convoOT.getDAnioConvocatoria()!=null?convoOT.getDAnioConvocatoria():""));

				((OCAPUsuariosForm) form).setListaCreditos(usuariosOT.getListaCreditos());
				((OCAPUsuariosForm) form).setListaMeritos(usuariosOT.getListaMeritos());
				((OCAPUsuariosForm) form).setListaMeritosOpcionales(usuariosOT.getListaMeritosOpcionales());

				((OCAPUsuariosForm) form).setCExpmc_id(usuariosOT.getCExpmc_id());

				if (expedienteCarreraOT.getFFinMc() != null) {
					((OCAPUsuariosForm) form).setFFin_mc(DateUtil.convertDateToString(expedienteCarreraOT.getFFinMc()));
				}
				request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);

				// Comprobar si hoy esta entre las fechas de f_inicio_mc y f_fin_mc
				// Si no esta, no podra annadir ni modificar meritos

				dHoy = new Date();
				request.setAttribute("periodoMC", Constantes.NO);
				if (dHoy.after(
						DateUtil.addDias(usuariosOT.getFInicioEvalMC(), (int) (convoOT.getNDiasAutoMc() + convoOT.getNDiasValMc())))) {
					request.setAttribute("permisoModificar", Constantes.NO);
				} else if ((usuariosOT.getFIncioMC() == null) || (dHoy.before(usuariosOT.getFIncioMC()))
						|| ((usuariosOT.getFFinMC() != null) && (dHoy.after(usuariosOT.getFFinMC())))) {

					if (Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
						request.setAttribute("permisoModificar", Constantes.SI_USUARIO);
					} else {
						request.setAttribute("permisoModificar", Constantes.NO);
					}
				} else if ((Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()))
						|| (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
						|| (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol()))) {
					request.setAttribute("permisoModificar", Constantes.NO);
					//Establecemos periodo de Meritos a SI
					request.setAttribute("periodoMC", Constantes.SI);
				} else {
					if(expedienteCarreraOT.getMeritosBloqueados() != null 
							&& !expedienteCarreraOT.getMeritosBloqueados().isEmpty() 
							&& expedienteCarreraOT.getMeritosBloqueados().toUpperCase().equals(Constantes.SI_ESP)){
						request.setAttribute("permisoModificar", Constantes.NO);
					}else{
						request.setAttribute("permisoModificar", Constantes.SI);
					}
				}

				if (Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
					if ((usuariosOT.getFFinMC() != null) && (dHoy.after(usuariosOT.getFFinMC())) && (convoOT.getFInicioEstudioCC() != null)
							&& (dHoy.before(DateUtil.convertStringToDate(convoOT.getFInicioEstudioCC())))) {
						request.setAttribute("permisoVerCeis", Constantes.NO);
					} else
						request.setAttribute("permisoVerCeis", Constantes.SI);
				} else {
					request.setAttribute("permisoVerCeis", Constantes.SI);
				}
				
				//OCAP-80				
				if (Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())			
						&&  (DateUtil.convertStringToDate(convoOT.getFFinEstudioCC()).compareTo(dHoy)<=0 )) 
				{
					request.setAttribute("permisoUsuarioVerCreditos", Constantes.SI);
					((OCAPUsuariosForm) form).setBVerCeisReport(true);
					((OCAPUsuariosForm) form).setBVerCCReport(true);
				}else
				{
					request.setAttribute("permisoUsuarioVerCreditos", Constantes.NO);
				}
				
				

				request.setAttribute("estado", Constantes.EMPTY);

				// se comprueba si ya fue tratado por el CEIS o por el CC como  CEIS
				if ((expedienteCarreraOT.getFAceptacionceis() == null) && (expedienteCarreraOT.getFNegacionMC() == null)) {
					if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) 
					{
						((OCAPUsuariosForm) form).setCTipo(Constantes.FASE_VALIDACION);
					}
					else
					{
						((OCAPUsuariosForm) form).setCTipo(Constantes.ESTADO_PENDIENTE_VALUE);
					}

				} else if ((expedienteCarreraOT.getFAceptacionCC() == null) && (expedienteCarreraOT.getFRespuestaInconf_MC() == null)) 
				{
					((OCAPUsuariosForm) form).setCTipo(Constantes.FASE_VALIDACION_CC);
				}
				else 
				{
					((OCAPUsuariosForm) form).setCTipo(Constantes.FASE_CA);
				}
				
				if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()))
				{
					if (((OCAPUsuariosForm) form).getenPeriodoValMc().equals(Constantes.SI)){
						// (dHoy.before(usuariosOT.getFInicioEvalMC()))
						request.setAttribute("estado", Constantes.ESTADO_PENDIENTE_VALUE);
						
					}					
					
						
				} else if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
				{
					// CC como CEIS
					if (((Constantes.SITUACION_DIRECTIVO.equals(usuariosOT.getCProcedimientoId()))
							|| (Constantes.SITUACION_ESTRUCTURA.equals(usuariosOT.getCProcedimientoId()))))
					{

						if (((OCAPUsuariosForm) form).getenPeriodoValCc().equals(Constantes.SI))
						{
							((OCAPUsuariosForm) form).setCTipo(Constantes.ESTADO_PENDIENTE_VALUE);
														
						}

						request.setAttribute("estado", Constantes.ESTADO_PENDIENTE_VALUE);

					}else if (((OCAPUsuariosForm) form).getenPeriodoValCc().equals(Constantes.SI)){
						
						request.setAttribute("estado", Constantes.ESTADO_PENDIENTE_VALUE);
						
						
						
					}
				}
				
				// Si el usuario tiene meritos pendientes de aclarar, mostramos los botones de
				// generar solicitud aclaracion y eliminar todas las aclaraciones
				
				if ((usuariosOT.getBPdtesAclarar() != null) && (Constantes.SI.equals(usuariosOT.getBPdtesAclarar())))
					request.setAttribute("pendientesAclaracion", Constantes.SI);
				else {
					request.setAttribute("pendientesAclaracion", Constantes.NO);
				}
				
				//OCAP-84
				
				boolean sinPermisoModificar  = (request.getAttribute("permisoModificar").equals(Constantes.NO)); 
				
				
				if (sinPermisoModificar	&& (!((OCAPUsuariosForm) form).getBVerCCReport() || (!((OCAPUsuariosForm) form).getBVerCeisReport())))
				{

					//USUARIO CC
					if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
					{
						// CC como CEIS
						if (((Constantes.SITUACION_DIRECTIVO.equals(usuariosOT.getCProcedimientoId()))
								|| (Constantes.SITUACION_ESTRUCTURA.equals(usuariosOT.getCProcedimientoId()))) && (((OCAPUsuariosForm) form).getCTipo().equals(Constantes.ESTADO_PENDIENTE_VALUE)))
						{
							((OCAPUsuariosForm) form).setBVerCeisReport(true);
							
						}else if (!((OCAPUsuariosForm) form).getCTipo().equals(Constantes.ESTADO_PENDIENTE_VALUE))
								{
							((OCAPUsuariosForm) form).setBVerCeisReport(true);
							((OCAPUsuariosForm) form).setBVerCCReport(true);
						}						
						
						//CEIS
					}else if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()))
					{
						((OCAPUsuariosForm) form).setBVerCeisReport(true);
						//GRS
					}else  if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){
						
						if (!((OCAPUsuariosForm) form).getCTipo().equals(Constantes.FASE_VALIDACION))
						{
							
							((OCAPUsuariosForm) form).setBVerCeisReport(true);
						}else
						{
							((OCAPUsuariosForm) form).setBVerCeisReport(true);
							((OCAPUsuariosForm) form).setBVerCCReport(true);
						}
						
					}															
				}
				
			}

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irInsertar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	private void ordenacionPersonalNuevaIncorporacion(OCAPUsuariosOT usuariosOT) {
		try {
			ArrayList listado = usuariosOT.getListaMeritos();
			if (Utilidades.notNullAndNotEmpty(listado)) {
				for (int i=0; i< listado.size(); i++) {
					OCAPMeritoscurricularesOT elemento = (OCAPMeritoscurricularesOT) listado.get(i);
					if (elemento != null && Utilidades.notNullAndNotEmpty(elemento.getCTipoMerito()) && elemento.getCTipoMerito().equals(FORMA_NUEVO_PERSONAL)) {
						if (Utilidades.notNullAndNotEmpty(elemento.getListaExpmc())) {
							ArrayList<OCAPExpedientemcOT> listaExpOrdenada = new ArrayList<OCAPExpedientemcOT>();
							listaExpOrdenada.addAll(elemento.getListaExpmc());
							Collections.sort(listaExpOrdenada, new Comparator<OCAPExpedientemcOT>() {
							    public int compare(OCAPExpedientemcOT o1, OCAPExpedientemcOT o2) {
							        return Integer.compare(o1.getFAnnio(), o2.getFAnnio());
							    	}
							    });
							elemento.setListaExpmc(listaExpOrdenada);
						}
					}
				}
			}
		}catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
		}
	}

	public ActionForward verCV(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		OCAPUsuariosOT usuariosOT = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " verCV: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			String usuId = jcylUsuario.getUser().getC_usr_id();

			OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);

			usuariosOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);
			usuariosOT = usuariosLN.datosMCUsuario(usuariosOT, "0");

			OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
			Collection listadoProcedimientos = procLN.listadoOCAPProcedimiento();
			Object[] listadopers = listadoProcedimientos.toArray();
			for (int i = 0; i < listadoProcedimientos.size(); i++) {
				if (((OCAPProcedimientoOT) listadopers[i]).getCProcedimientoId().longValue() == Long
						.parseLong(usuariosOT.getCProcedimientoId())) {
					usuariosOT.setDProcedimiento(((OCAPProcedimientoOT) listadopers[i]).getDNombre());
					break;
				}

			}

			String pathBase = this.servlet.getServletConfig().getServletContext().getRealPath(Constantes.EMPTY);
			String marcaAgua = request.getParameter("marcaAgua");
			usuariosLN.generarCVenPDF(response, usuariosOT, pathBase, marcaAgua);

			OCAPConfigApp.logger.info(getClass().getName() + " verCV: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irInsertar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irProteccionDatos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		String fw = null;
		String cConvocatoriaId = null;
		try {
			OCAPConfigApp.logger.debug(getClass().getName() + " irProteccionDatos: Inicio");
			validarAccion(mapping, form, request, response);

			if (mapping.getAttribute() != null) {
				if ("request".equals(mapping.getScope()))
					request.removeAttribute(mapping.getAttribute());
				else {
					session.removeAttribute(mapping.getAttribute());
				}
			}

			OCAPUsuariosForm formulario = new OCAPUsuariosForm();
			session.setAttribute("OCAPUsuariosForm", formulario);
			cConvocatoriaId = obtenerConvocatoria(request, null);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			String usuId = jcylUsuario.getUser().getC_usr_id();
			OCAPConfigApp.logger.debug(getClass().getName() + " irProteccionDatos: UsuarioId: " + usuId);

			OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);
			OCAPUsuariosOT usuariosOT = usuariosLN.buscarOCAPUsuariosPorNIF(usuId);

			if ((usuariosOT == null) || (usuariosOT.getCUsrId() == null)) {
				return mapping.findForward("irNoExisteSolicitud");
			}

			OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			OCAPExpedientecarreraOT expCarreraOT = expedienteCarreraLN.buscarExpedienteCarreraPorUsuarioConvocatoria(
					usuariosOT.getCUsrId(), Long.valueOf(cConvocatoriaId));

			if ((expCarreraOT == null) || (expCarreraOT.getCExpId() == null)) {
				return mapping.findForward("irNoExisteSolicitud");
			}

			if ((expCarreraOT.getFAceptacSolic() == null) || (expCarreraOT.getFAceptacSolic().getTime() == 0L)) {
				return mapping.findForward("irSolicitudNoAceptada");
			}

			if (expCarreraOT.getCEstadoId() == Constantes.ESTADO_RENUNCIA) {
				return mapping.findForward("irRenunciaSolicitud");
			}

			OCAPConvocatoriasLN convocatoriaLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT convoOT = convocatoriaLN.buscarOCAPConvocatorias(expCarreraOT.getCConvocatoriaId());
			
			if (convoOT!=null){
				((OCAPUsuariosForm) form).setDAnioConvocatoria(convoOT.getDAnioConvocatoria());	
				JCYLConfiguracion.setValor("FECHA_COMPROBAR_MC", "31/12/"+convoOT.getDAnioConvocatoria());
				OCAPConfigApp.loggerTrazas.warn ("TRAZA PROD- Linea 637 Meto en sesión atributo FECHA_COMPROBAR_MC con valor: " + "31/12/"+convoOT.getDAnioConvocatoria());
			}
			
			
			if (convoOT.getFInicioMC() == null) {
				return mapping.findForward("irFaseMCnoIniciada");
			}

			// RFdez: Casos en los que se actualizan las fechas del expediente.
			Date fechaFinMc = DateUtil.addDias(DateUtil.convertStringToDate(convoOT.getFInicioMC()),
					new Long(convoOT.getNDiasAutoMc()).intValue());

			// modificar expedienteCarrera para añadir fInicioMC y fFinMC segun
			// convocatoria
			if ((expCarreraOT.getFInicioMc() == null)
					|| (expCarreraOT.getFInicioMc().before(DateUtil.convertStringToDate(convoOT.getFInicioMC())))) {
				expCarreraOT.setFInicioMc(new Date());
				expCarreraOT.setFFinMc(fechaFinMc);
		
         //OCAP-76
         expCarreraOT.setFInicioCc(obtenerFecha(convoOT.getFInicioValoracionCC()));
         expCarreraOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());
         expCarreraOT.setFFinCc(obtenerFecha(convoOT.getFFinValoracionCC()));
				expedienteCarreraLN.modificacionOCAPExpedientecarrera(expCarreraOT, false);
			} else if (expCarreraOT.getFFinMc().before(fechaFinMc)) {
				expCarreraOT.setFFinMc(fechaFinMc);
				expedienteCarreraLN.modificacionOCAPExpedientecarrera(expCarreraOT, false);
			}

			if (new Date().before(DateUtil.convertStringToDate(convoOT.getFInicioMC()))) {
				return mapping.findForward("irFaseMCnoIniciada");
			}

			if (((expCarreraOT.getBLopdMc() != null) && (expCarreraOT.getBLopdMc().equals(Constantes.SI)))
					|| (request.getParameter("LOPD") != null)) {
				return irInsertar(mapping, formulario, request, response);
			}
			fw = "irProteccionDatos";
			request.setAttribute("fase", "Inicio Autoevaluación Méritos");

			OCAPConfigApp.logger.debug(getClass().getName() + " irProteccionDatos: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(fw);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	private Date obtenerFecha(String fecha) {
		if (fecha != null && !fecha.equals(Constantes.EMPTY)){
			try{
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				Date date = format.parse(fecha);
				return date;
			}catch (Exception e){
				return null;
			}
		}
		return null;
	}
	public ActionForward guardarPuesto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		OCAPExpedienteCarreraLN expCarreraLN = null;
		OCAPExpedientecarreraOT expedienteCarreraOT = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " guardarPuesto: Inicio");

			validarAccion(mapping, form, request, response);
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			expedienteCarreraOT = new OCAPExpedientecarreraOT();
			expedienteCarreraOT.setCExpId(((OCAPUsuariosForm) form).getCExpId());
			expedienteCarreraOT.setAModificadoPor(
					((JCYLUsuario) request.getSession().getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO))
							.getUsuario().getC_usr_id());
			expedienteCarreraOT.setAPuesto(((OCAPUsuariosForm) form).getAPuesto());
			expCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);

			OCAPConfigApp.logger.info(getClass().getName() + " guardarPuesto: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return irInsertar(mapping, form, request, response);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irBuscadorUsuariosDNI(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession sesion = request.getSession();
		ActionErrors errores = new ActionErrors();
		try {
			validarAccion(mapping, form, request, response);

			if (mapping.getAttribute() != null) {
				if ("request".equals(mapping.getScope()))
					request.removeAttribute(mapping.getAttribute());
				else {
					sesion.removeAttribute(mapping.getAttribute());
				}
			}
			OCAPUsuariosForm formulario = new OCAPUsuariosForm();
			sesion.setAttribute("OCAPUsuariosForm", formulario);
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errores.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		}

		if (errores.size() != 0) {
			saveErrors(request, errores);

			return mapping.findForward("fallo");
		}

		return mapping.findForward("irBuscadorUsuariosDNI");
	}

	public ActionForward listarUsuariosDNI(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession sesion = request.getSession();
		ActionErrors errores = new ActionErrors();
		try {
			if (request.getAttribute("erroresAction") != null) {
				saveErrors(request, (ActionErrors) request.getAttribute("erroresAction"));
				return mapping.findForward("irBuscadorUsuariosDNI");
			}

			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) sesion.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);

			ArrayList listadoUsuariosDNI = usuariosLN
					.buscarOCAPUsuariosPorNIF_a(((OCAPUsuariosForm) form).getCDniReal_Busqueda());
			if (listadoUsuariosDNI != null)
				request.setAttribute("listadoUsuariosDNI", listadoUsuariosDNI);
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errores.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		}

		if (errores.size() != 0) {
			saveErrors(request, errores);

			return mapping.findForward("fallo");
		}

		return mapping.findForward("listarUsuariosDNI");
	}

	public ActionForward irModificarUsuarioDNI(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession sesion = request.getSession();
		ActionErrors errores = new ActionErrors();
		try {
			validarAccion(mapping, form, request, response);

			String DNIBuscado = ((OCAPUsuariosForm) form).getCDniReal_Busqueda();

			if (mapping.getAttribute() != null) {
				if ("request".equals(mapping.getScope()))
					request.removeAttribute(mapping.getAttribute());
				else {
					sesion.removeAttribute(mapping.getAttribute());
				}
			}
			OCAPUsuariosForm formulario = new OCAPUsuariosForm();
			sesion.setAttribute("OCAPUsuariosForm", formulario);

			JCYLUsuario jcylUsuario = (JCYLUsuario) sesion.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);
			OCAPUsuariosOT usuarioOT = new OCAPUsuariosOT();
			try {
				if (request.getParameter("cUsrId") == null) {
					throw new Exception();
				}
				usuarioOT.setCUsrId(new Long(request.getParameter("cUsrId")));
			} catch (Exception e) {
				OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
				errores.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.parametros"));
				return mapping.findForward("fallo");
			}

			usuarioOT = usuariosLN.obtenerUsuario(usuarioOT);

			formulario.setCDniReal_Busqueda(DNIBuscado);
			formulario.setCUsr_id(usuarioOT.getCUsrId().toString());
			formulario.setDNombre(usuarioOT.getDNombre());
			formulario.setDApellido1(usuarioOT.getDApellido1());
			formulario.setCDniReal(usuarioOT.getCDniReal());
			formulario.setCDni(usuarioOT.getCDni());
			if ((usuarioOT != null) && (usuarioOT.getCDni() != null) && (!Utilidades.esDNI(usuarioOT.getCDni())))
				formulario.setLetraUID(usuarioOT.getCDni().substring(usuarioOT.getCDni().length() - 1));
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errores.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		}

		if (errores.size() != 0) {
			saveErrors(request, errores);

			return mapping.findForward("fallo");
		}

		return mapping.findForward("irModificarUsuarioDNI");
	}

	public ActionForward modificarUsuarioDNI(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession sesion = request.getSession();
		ActionErrors errores = new ActionErrors();
		try {
			if (request.getAttribute("erroresAction") != null) {
				saveErrors(request, (ActionErrors) request.getAttribute("erroresAction"));
				return mapping.findForward("irModificarUsuarioDNI");
			}

			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) sesion.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);
			OCAPUsuariosOT usuarioOT = new OCAPUsuariosOT();

			OCAPUsuariosForm formulario = (OCAPUsuariosForm) form;
			usuarioOT.setCUsrId(Long.valueOf(formulario.getCUsr_id()));
			usuarioOT.setDNombre(formulario.getDNombre());
			usuarioOT.setDApellido1(formulario.getDApellido1());
			usuarioOT.setCDniReal(formulario.getCDniReal());
			if ((formulario != null) && (formulario.getLetraUID() != null)
					&& (!formulario.getLetraUID().trim().equals("")))
				usuarioOT.setCDni(formulario.getCDniReal() + formulario.getLetraUID().toUpperCase());
			else {
				usuarioOT.setCDni(formulario.getCDniReal());
			}
			usuariosLN.actualizarUID(usuarioOT);

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irBuscadorUsuariosDNI");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			if (e.getMessage().startsWith("ORA-00001"))
				errores.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.dniDupli"));
			else {
				errores.add("org.apache.struts.action.GLOBAL_ERROR",
						new ActionError(TrataError.tratarError(e, "error.general")));
			}
		}
		if (errores.size() != 0) {
			saveErrors(request, errores);

			return mapping.findForward("fallo");
		}

		return mapping.findForward("exito");
	}

	public ActionForward elegirConvocatoria(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errores = new ActionErrors();
		String sig = "inicio";
		try {
			OCAPConfigApp.logger.debug(getClass().getName() + "elegirConvocatoria Ini");

			JCYLUsuario jcylUsuario = (JCYLUsuario) request.getSession()
					.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if ((((OCAPUsuariosForm) form).getCConvocatoriaId() != null)
					&& (!"".equals(((OCAPUsuariosForm) form).getCConvocatoriaId()))
					&& (Long.parseLong(((OCAPUsuariosForm) form).getCConvocatoriaId()) != 0L)) {
				jcylUsuario.getParametrosUsuario().put("PARAM_CONVOCATORIA_USUARIO",	((OCAPUsuariosForm) form).getCConvocatoriaId());
				sig = "inicio";
			} else {
				OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
				request.setAttribute(Constantes.COMBO_CONVOCATORIAS, convocatoriasLN.obtenerConvocatorias(true));
				sig = "elegirConvocatoria";
				if ("0".equals(((OCAPUsuariosForm) form).getCConvocatoriaId())) {
					ActionMessages messages = new ActionMessages();
					messages.add("usuarioSinConvocatoria", new ActionMessage("mensaje.usuario.convocatoria"));
					saveMessages(request, messages);
				}
			}

			OCAPConfigApp.logger.debug(getClass().getName() + "elegirConvocatoria Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errores.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		}

		if (errores.size() != 0) {
			saveErrors(request, errores);
			sig = "error";
		}

		return mapping.findForward(sig);
	}

	public ActionForward imprimirCV(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession sesion = request.getSession();
		ActionErrors errores = new ActionErrors();
		try {
			JCYLUsuario jcylUsuario = (JCYLUsuario) sesion.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getAttribute("erroresAction") != null) {
				saveErrors(request, (ActionErrors) request.getAttribute("erroresAction"));
				return mapping.findForward("");
			}

			String rutaRaiz = this.servlet.getServletConfig().getServletContext().getRealPath("");

			OCAPReportExpedientesLN reportExpedientesLN = new OCAPReportExpedientesLN(jcylUsuario);
			OCAPReportExpedientesOT datosBusqueda = new OCAPReportExpedientesOT();
			long[] expedientes = { ((OCAPUsuariosForm) form).getCExpId() };

			datosBusqueda.setListaExpedientesBusqueda(expedientes);
			datosBusqueda.setBMCUsuario(Constantes.SI);
			
			
			datosBusqueda.setDOrigenReport(Constantes.ORIGEN_USUARIOS); // Para indicar al report desde donde se llama
			
			datosBusqueda.setBVerCeisReport(new Boolean(((OCAPUsuariosForm) form).getBVerCeisReport()));
			datosBusqueda.setBVerCCReport(new Boolean(((OCAPUsuariosForm) form).getBVerCCReport()));
			datosBusqueda.setJcylUsuario(jcylUsuario);


			Utilidades.escribirMemoriaLog(OCAPConfigApp.logger);

			ArrayList reports = reportExpedientesLN.crearListadoReportsExpedientesMerCurriculares(datosBusqueda,
					rutaRaiz);

			Utilidades.escribirMemoriaLog(OCAPConfigApp.logger);

			ServletOutputStream out = response.getOutputStream();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=\"CV.pdf\"");

			Report.reportToPDFCat(out, reports);
			out.flush();
			out.close();
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errores.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		}

		if (errores.size() != 0) {
			saveErrors(request, errores);

			return mapping.findForward("fallo");
		}

		return mapping.findForward("");
	}
	
	public ActionForward finalizarFaseMeritos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
		OCAPExpedienteCarreraLN expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
		expCarreraLN.finalizarFaseMeritos(((OCAPUsuariosForm) form).getCExp_id(), jcylUsuario.getUsuario().getC_usr_id());		
		return irInsertar(mapping, form, request, response);
	}

}
