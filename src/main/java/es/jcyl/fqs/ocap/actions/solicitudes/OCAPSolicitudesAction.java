package es.jcyl.fqs.ocap.actions.solicitudes;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import es.jcyl.cf.seguridad.util.Usuario;
import es.jcyl.fqs.ocap.actionforms.solicitudes.OCAPSolicitudesForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
import es.jcyl.fqs.ocap.ln.centroTrabajo.OCAPCentroTrabajoLN;
import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
import es.jcyl.fqs.ocap.ln.cuestionarios.OCAPCuestionariosLN;
import es.jcyl.fqs.ocap.ln.defMeritosCurriculares.OCAPDefMeritoscurricularesLN;
import es.jcyl.fqs.ocap.ln.especialidades.OCAPEspecialidadesLN;
import es.jcyl.fqs.ocap.ln.estados.OCAPEstadosLN;
import es.jcyl.fqs.ocap.ln.estatutario.OCAPEstatutarioLN;
import es.jcyl.fqs.ocap.ln.expedienteCarrera.OCAPExpedienteCarreraLN;
import es.jcyl.fqs.ocap.ln.expedienteMC.OCAPExpedientemcLN;
import es.jcyl.fqs.ocap.ln.gerencias.OCAPGerenciasLN;
import es.jcyl.fqs.ocap.ln.grado.OCAPGradoLN;
import es.jcyl.fqs.ocap.ln.localidades.OCAPLocalidadesLN;
import es.jcyl.fqs.ocap.ln.meritosCurriculares.OCAPMeritoscurricularesLN;
import es.jcyl.fqs.ocap.ln.modalidadAnterior.OCAPModalidadAnteriorLN;
import es.jcyl.fqs.ocap.ln.personalEstatutario.OCAPPersEstatutarioLN;
import es.jcyl.fqs.ocap.ln.procedimiento.OCAPProcedimientoLN;
import es.jcyl.fqs.ocap.ln.provincias.OCAPProvinciasLN;
import es.jcyl.fqs.ocap.ln.regimenJuridico.OCAPRegimenJuridicoLN;
import es.jcyl.fqs.ocap.ln.sit_Administrativa.OCAPSit_AdministrativaLN;
import es.jcyl.fqs.ocap.ln.solicitudes.OCAPNuevaSolicitudLN;
import es.jcyl.fqs.ocap.ln.solicitudes.OCAPSolicitudesLN;
import es.jcyl.fqs.ocap.ln.tipoGerencias.OCAPTipoGerenciasLN;
import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
import es.jcyl.fqs.ocap.ot.categProfesionales.OCAPCategProfesionalesOT;
import es.jcyl.fqs.ocap.ot.centroTrabajo.OCAPCentroTrabajoOT;
import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
import es.jcyl.fqs.ocap.ot.creditos.OCAPCreditosOT;
import es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT;
import es.jcyl.fqs.ocap.ot.defMeritosCurriculares.OCAPDefMeritoscurricularesOT;
import es.jcyl.fqs.ocap.ot.especialidades.OCAPEspecialidadesOT;
import es.jcyl.fqs.ocap.ot.estados.OCAPEstadosOT;
import es.jcyl.fqs.ocap.ot.estatutario.OCAPEstatutarioOT;
import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;
import es.jcyl.fqs.ocap.ot.gerencias.OCAPGerenciasOT;
import es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT;
import es.jcyl.fqs.ocap.ot.meritosCurriculares.OCAPMeritoscurricularesOT;
import es.jcyl.fqs.ocap.ot.modalidadAnterior.OCAPModalidadAnteriorOT;
import es.jcyl.fqs.ocap.ot.personalEstatutario.OCAPPersEstatutarioOT;
import es.jcyl.fqs.ocap.ot.procedimiento.OCAPProcedimientoOT;
import es.jcyl.fqs.ocap.ot.provincias.OCAPProvinciasOT;
import es.jcyl.fqs.ocap.ot.reports.OCAPAsistenteOT;
import es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT;
import es.jcyl.fqs.ocap.ot.tipoGerencias.OCAPTipoGerenciasOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.Pagina;
import es.jcyl.fqs.ocap.util.TrataError;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLUsuario;

public class OCAPSolicitudesAction extends OCAPGenericAction {
	public ActionForward irInsertar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoProcedimientos = null;
		ArrayList listadoCategoriasActuales = null;
		ArrayList listadoEspecialidadesActuales = null;
		ArrayList listadoEstatutarios = null;
		ArrayList listadoCategorias = null;
		ArrayList listadoEspecialidades = null;
		ArrayList listadoGrados = null;
		ArrayList listadoTipoGerencias = null;
		ArrayList listadoGerencias = null;
		ArrayList listadoCentros = null;
		ArrayList listadoProvincias = null;
		Utilidades utilidades = null;

		OCAPGerenciasOT gerenciaOT = null;

		ArrayList listaSituaciones = null;
		ArrayList listaVinculos = null;
		ArrayList listaTodasProvincias = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);

			listadoGrados = gradoLN.listarGradosConvocatoriasAbiertas("", jcylUsuario);

			if ((listadoGrados == null) || (listadoGrados.size() == 0)) {
				return mapping.findForward("irNoExistenConvocatoriasSolic");
			}
			session.setAttribute(Constantes.COMBO_GRADOS_ALTA, listadoGrados);

			OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
			listadoProcedimientos = procLN.listadoOCAPProcedimiento();
			session.setAttribute(Constantes.COMBO_PERSONAL, listadoProcedimientos);

			OCAPPersEstatutarioLN estatutarioLN = new OCAPPersEstatutarioLN(jcylUsuario);
			listadoEstatutarios = estatutarioLN.listadoOCAPPersEstatutario();
			session.setAttribute(Constantes.COMBO_ESTATUTARIO, listadoEstatutarios);

			OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
			listadoProvincias = provinciasLN.listarProvinciasComunidad(Constantes.CODIGO_CYL);
			session.setAttribute(Constantes.COMBO_PROVINCIAS, listadoProvincias);

			OCAPTipoGerenciasLN tipoGerenciasLN = new OCAPTipoGerenciasLN(jcylUsuario);
			listadoTipoGerencias = tipoGerenciasLN.listarTipoGerencias();
			session.setAttribute(Constantes.COMBO_TIPOS_GERENCIAS, listadoTipoGerencias);

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			if ((((OCAPSolicitudesForm) form).getCEstatutarioId() != null)
					&& (!"".equals(((OCAPSolicitudesForm) form).getCEstatutarioId()))) {
				listadoCategorias = categProfesionalesLN.consultaOCAPCategProfesionales(0L, null, 0L,
						Long.parseLong(((OCAPSolicitudesForm) form).getCEstatutarioId()), null, null, null, 0L, 0, 0);
				session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
			} else {
				session.setAttribute(Constantes.COMBO_CATEGORIA, new ArrayList());
			}

			OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
			if ((((OCAPSolicitudesForm) form).getCProfesional_id() != null)
					&& (!"".equals(((OCAPSolicitudesForm) form).getCProfesional_id()))) {
				listadoEspecialidades = especialidadesLN
						.listarEspecialidades(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesional_id()));
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
			} else {
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
			}

			if ((((OCAPSolicitudesForm) form).getCEstatutarioActualId() != null)
					&& (!"".equals(((OCAPSolicitudesForm) form).getCEstatutarioActualId()))) {
				listadoCategoriasActuales = categProfesionalesLN.consultaOCAPCategProfesionales(0L, null, 0L,
						Long.parseLong(((OCAPSolicitudesForm) form).getCEstatutarioActualId()), null, null, null, 0L, 0, 0);
				session.setAttribute(Constantes.COMBO_CATEGORIA_ACTUAL, listadoCategoriasActuales);
			} else {
				session.setAttribute(Constantes.COMBO_CATEGORIA_ACTUAL, new ArrayList());
			}

			if ((((OCAPSolicitudesForm) form).getCProfesionalActual_id() != null)
					&& (!"".equals(((OCAPSolicitudesForm) form).getCProfesionalActual_id()))) {
				listadoEspecialidadesActuales = especialidadesLN
						.listarEspecialidades(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesionalActual_id()));
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD_ACTUAL, listadoEspecialidadesActuales);
			} else {
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD_ACTUAL, new ArrayList());
			}

			if ((((OCAPSolicitudesForm) form).getCProvinciaUsu_id() != null)
					&& (!((OCAPSolicitudesForm) form).getCProvinciaUsu_id().equals(""))) {
				OCAPLocalidadesLN localidadesLN = new OCAPLocalidadesLN(jcylUsuario);
				Collection listadoLocal = localidadesLN.listarLocalidadesProvincia(((OCAPSolicitudesForm) form).getCProvinciaUsu_id());
				Object[] listadoLocalidades = listadoLocal.toArray();

				utilidades = new Utilidades();
				session.setAttribute(Constantes.COMBO_LOCALIDADES, utilidades.ArrayObjectToArrayList(listadoLocalidades));
			} else {
				session.setAttribute(Constantes.COMBO_LOCALIDADES, new ArrayList());
			}

			((OCAPSolicitudesForm) form).setAccionEnviar("Registrar");

			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_COMPLETA_DATEUTIL);
			Date hoy = new Date();
			((OCAPSolicitudesForm) form).setFRegistro_solic(df.format(hoy));

			if ((jcylUsuario.getUser().getRol() != null) && ((jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE))
					|| (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIOS)))) {
				Map parametros = jcylUsuario.getParametrosUsuario();

				OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
				listadoGerencias = gerenciasLN.listarGerencias((String) parametros.get("PARAM_PROV"),
						(String) parametros.get("PARAM_TIPO_GERENCIA"));
				session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);

				gerenciaOT = gerenciasLN.buscaOCAPGerenciaLDAP(jcylUsuario.getUser().getD_gerencia());
				((OCAPSolicitudesForm) form).setCGerencia_id(String.valueOf(gerenciaOT.getCGerenciaId()));
				((OCAPSolicitudesForm) form).setCTipogerencia_id(String.valueOf(gerenciaOT.getCTipogerenciaId()));
				((OCAPSolicitudesForm) form).setCProvincia_id(gerenciaOT.getCProvinciaId());

				OCAPCentroTrabajoLN centroTrabajoLN = new OCAPCentroTrabajoLN(jcylUsuario);
				listadoCentros = centroTrabajoLN.listarCentroTrabajo(String.valueOf(gerenciaOT.getCGerenciaId()), null);
				session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO, listadoCentros);
			} else {
				session.setAttribute(Constantes.COMBO_GERENCIA, new ArrayList());
				session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO, new ArrayList());
			}

			request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);
			request.setAttribute("primeraCarga", Constantes.SI);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			listaSituaciones = solicitudesLN.listaSituaciones();
			session.setAttribute(Constantes.COMBO_SITUACIONES, listaSituaciones);
			listaVinculos = solicitudesLN.listaVinculos();
			session.setAttribute(Constantes.COMBO_VINCULOS, listaVinculos);
			listaTodasProvincias = provinciasLN.listarProvincias();
			session.setAttribute(Constantes.COMBO_TODAS_PROVINCIAS, listaTodasProvincias);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irInsertar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irDetalleAlta(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.debug(getClass().getName() + " irDetalleAlta: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			boolean flag = true;

			OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			OCAPExpedientecarreraOT expedienteCarreraOT = null;
			OCAPUsuariosLN usuarioLN = new OCAPUsuariosLN(jcylUsuario);
			OCAPUsuariosOT usuariosOT = null;

			if (((OCAPSolicitudesForm) form).getCProfesional_id() == null) {
				usuariosOT = usuarioLN.buscarOCAPUsuariosPorNIF(jcylUsuario.getUser().getC_usr_id());

				if ((usuariosOT == null) || (usuariosOT.getCUsrId() == null)) {
					return mapping.findForward("irNoExisteSolicitud");
				}

				String cConvocatoriaId = obtenerConvocatoria(request, ((OCAPSolicitudesForm) form).getCConvocatoriaId());
				
				
				OCAPConfigApp.logger.warn ("TRAZA PROD- -------------------------------------------------------------------------------");
			       OCAPConfigApp.logger.warn ("TRAZA PROD- Valores de convocatoria en OCAPSolicitudesAction.irDetalleAlta extraida del OCAPSolicitudesForm " +cConvocatoriaId);
			       OCAPConfigApp.logger.warn ("TRAZA PROD- -------------------------------------------------------------------------------");
				
				
				expedienteCarreraOT = expedienteCarreraLN.buscarExpedienteCarreraPorUsuarioConvocatoria(usuariosOT.getCUsrId(),
						Long.valueOf(cConvocatoriaId));
				flag = false;

				if ((expedienteCarreraOT == null) || (expedienteCarreraOT.getCExpId() == null)
						|| (expedienteCarreraOT.getCExpId().longValue() == 0L)) {
					return mapping.findForward("irNoExisteSolicitud");
				}

				((OCAPSolicitudesForm) form).setCExp_id(String.valueOf(expedienteCarreraOT.getCExpId()));
				((OCAPSolicitudesForm) form).setDApellido1(usuariosOT.getDApellido1());
				((OCAPSolicitudesForm) form).setDNombre(usuariosOT.getDNombre());
				((OCAPSolicitudesForm) form).setCDni(usuariosOT.getCDni());
				((OCAPSolicitudesForm) form).setCDniReal(usuariosOT.getCDniReal());
				((OCAPSolicitudesForm) form).setDCorreoelectronico(usuariosOT.getDCorreoelectronico());
				((OCAPSolicitudesForm) form).setBSexo(usuariosOT.getBSexo());

				if (usuariosOT.getNTelefono1().intValue() != 0)
					((OCAPSolicitudesForm) form).setNTelefono1(String.valueOf(usuariosOT.getNTelefono1()));
				else {
					((OCAPSolicitudesForm) form).setNTelefono1("-");
				}
				if (usuariosOT.getNTelefono2().intValue() != 0)
					((OCAPSolicitudesForm) form).setNTelefono2(String.valueOf(usuariosOT.getNTelefono2()));
				else
					((OCAPSolicitudesForm) form).setNTelefono2("-");
			} else {
				usuariosOT = new OCAPUsuariosOT();
				expedienteCarreraOT = new OCAPExpedientecarreraOT();
			}

			OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
			OCAPProcedimientoOT procOT = null;
			if ((((OCAPSolicitudesForm) form).getCProcedimientoId() != null)
					&& (!((OCAPSolicitudesForm) form).getCProcedimientoId().equals("")))
				procOT = procLN.buscarOCAPProcedimiento(Long.parseLong(((OCAPSolicitudesForm) form).getCProcedimientoId()));
			else {
				procOT = procLN.buscarOCAPProcedimiento(Long.parseLong(expedienteCarreraOT.getCProcedimientoId()));
			}
			((OCAPSolicitudesForm) form).setDProcedimiento(procOT.getDNombre());

			OCAPPersEstatutarioLN personalLN = new OCAPPersEstatutarioLN(jcylUsuario);
			OCAPPersEstatutarioOT personalOT = null;
			OCAPPersEstatutarioOT personalActualOT = null;

			if ((((OCAPSolicitudesForm) form).getCEstatutarioId() != null)
					&& (!((OCAPSolicitudesForm) form).getCEstatutarioId().equals("")))
				personalOT = personalLN.buscarOCAPPersEstatutario(Long.parseLong(((OCAPSolicitudesForm) form).getCEstatutarioId()));
			else {
				personalOT = personalLN.buscarOCAPPersEstatutario(expedienteCarreraOT.getCEstatutId());
			}
			if ((((OCAPSolicitudesForm) form).getCEstatutarioActualId() != null)
					&& (!((OCAPSolicitudesForm) form).getCEstatutarioActualId().equals("")))
				personalActualOT = personalLN
						.buscarOCAPPersEstatutario(Long.parseLong(((OCAPSolicitudesForm) form).getCEstatutarioActualId()));
			else {
				personalActualOT = personalLN.buscarOCAPPersEstatutario(expedienteCarreraOT.getCEstatutActualId());
			}
			((OCAPSolicitudesForm) form).setDEstatutario_nombre(personalOT.getDNombre());
			((OCAPSolicitudesForm) form).setDEstatutarioActual_nombre(personalActualOT.getDNombre());

			OCAPCategProfesionalesLN categProfLN = new OCAPCategProfesionalesLN(jcylUsuario);
			OCAPCategProfesionalesOT categProfOT = null;
			OCAPCategProfesionalesOT categProfActualOT = null;
			if (flag) {
				categProfOT = categProfLN.buscarOCAPCategProfesionales(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesional_id()));
				categProfActualOT = categProfLN
						.buscarOCAPCategProfesionales(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesionalActual_id()));
			} else {
				categProfOT = categProfLN.buscarOCAPCategProfesionales(Long.parseLong(String.valueOf(usuariosOT.getCProfesionalId())));
				categProfActualOT = categProfLN.buscarOCAPCategProfesionales(expedienteCarreraOT.getCProfesionalActual_id());
			}
			((OCAPSolicitudesForm) form).setDProfesional_nombre(categProfOT.getDNombre());
			((OCAPSolicitudesForm) form).setDProfesionalActual_nombre(categProfActualOT.getDNombre());

			OCAPEspecialidadesLN especLN = new OCAPEspecialidadesLN(jcylUsuario);
			if (flag) {
				if ((((OCAPSolicitudesForm) form).getCEspec_id() != null) && (!((OCAPSolicitudesForm) form).getCEspec_id().equals("0"))) {
					OCAPEspecialidadesOT especOT = especLN
							.buscarOCAPEspecialidades(Long.parseLong(((OCAPSolicitudesForm) form).getCEspec_id()));
					((OCAPSolicitudesForm) form).setDEspec_nombre(especOT.getDNombre());
				}
				if ((((OCAPSolicitudesForm) form).getCEspecActual_id() != null)
						&& (!((OCAPSolicitudesForm) form).getCEspecActual_id().equals("0"))) {
					OCAPEspecialidadesOT especActualOT = especLN
							.buscarOCAPEspecialidades(Long.parseLong(((OCAPSolicitudesForm) form).getCEspecActual_id()));
					((OCAPSolicitudesForm) form).setDEspecActual_nombre(especActualOT.getDNombre());
				}
			} else {
				if (usuariosOT.getCEspecId() != null) {
					OCAPEspecialidadesOT especOT = especLN
							.buscarOCAPEspecialidades(Long.parseLong(String.valueOf(usuariosOT.getCEspecId().intValue())));
					((OCAPSolicitudesForm) form).setDEspec_nombre(especOT.getDNombre());
				}
				if (expedienteCarreraOT.getCEspecActual_id() != 0L) {
					OCAPEspecialidadesOT especActualOT = especLN.buscarOCAPEspecialidades(expedienteCarreraOT.getCEspecActual_id());
					((OCAPSolicitudesForm) form).setDEspecActual_nombre(especActualOT.getDNombre());
				}
			}

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			OCAPGerenciasOT gerenciasOT = null;
			if (flag)
				gerenciasOT = gerenciasLN.buscarOCAPGerencias(Long.parseLong(((OCAPSolicitudesForm) form).getCGerencia_id()));
			else {
				gerenciasOT = gerenciasLN.buscarOCAPGerencias(Long.parseLong(String.valueOf(usuariosOT.getCGerenciaId().intValue())));
			}
			((OCAPSolicitudesForm) form).setDGerencia_nombre(gerenciasOT.getDNombre());

			OCAPTipoGerenciasLN tipoGerenciasLN = new OCAPTipoGerenciasLN(jcylUsuario);
			OCAPTipoGerenciasOT tipoGerenciasOT = null;
			if (flag)
				tipoGerenciasOT = tipoGerenciasLN
						.buscarOCAPTipoGerencias(Long.parseLong(((OCAPSolicitudesForm) form).getCTipogerencia_id()));
			else {
				tipoGerenciasOT = tipoGerenciasLN.buscarOCAPTipoGerencias(gerenciasOT.getCTipogerenciaId());
			}
			((OCAPSolicitudesForm) form).setDTipogerencia_desc(tipoGerenciasOT.getDDescripcion());

			OCAPProvinciasOT provinciaOT = null;
			OCAPProvinciasLN provinciaLN = new OCAPProvinciasLN(jcylUsuario);
			if (flag)
				provinciaOT = provinciaLN.buscarProvincia(((OCAPSolicitudesForm) form).getCProvincia_id());
			else {
				provinciaOT = provinciaLN.buscarProvincia(gerenciasOT.getCProvinciaId());
			}
			((OCAPSolicitudesForm) form).setDProvincia(Cadenas.capitalizar(provinciaOT.getDProvincia()));

			if (flag) {
				provinciaOT = provinciaLN.buscarProvincia(((OCAPSolicitudesForm) form).getCProvinciaUsu_id());
				((OCAPSolicitudesForm) form).setCProvinciaUsu_id(((OCAPSolicitudesForm) form).getCProvinciaUsu_id());
			}

			((OCAPSolicitudesForm) form).setDProvinciaUsu(Cadenas.capitalizar(provinciaOT.getDProvincia()));

			OCAPCentroTrabajoLN centroLN = new OCAPCentroTrabajoLN(jcylUsuario);
			OCAPCentroTrabajoOT centroOT = null;
			if (flag)
				centroOT = centroLN.buscarOCAPCentroTrabajo(Long.parseLong(((OCAPSolicitudesForm) form).getCCentrotrabajo_id()));
			else {
				centroOT = centroLN.buscarOCAPCentroTrabajo(Long.parseLong(String.valueOf(usuariosOT.getCCentrotrabajoId().intValue())));
			}
			((OCAPSolicitudesForm) form).setDCentrotrabajo_nombre(centroOT.getDNombre());

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			OCAPGradoOT gradoOT = null;
			if ((((OCAPSolicitudesForm) form).getCGrado_id() != null) && (!((OCAPSolicitudesForm) form).getCGrado_id().equals("")))
				gradoOT = gradoLN.buscarOCAPGrado(Long.parseLong(((OCAPSolicitudesForm) form).getCGrado_id()));
			else {
				gradoOT = gradoLN.buscarOCAPGrado(expedienteCarreraOT.getCGradoId().longValue());
			}
			((OCAPSolicitudesForm) form).setDGrado_des(gradoOT.getDDescripcion());

			OCAPConvocatoriasLN convoLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT convoOT = null;
			if ((((OCAPSolicitudesForm) form).getCConvocatoriaId() != null)
					&& (!((OCAPSolicitudesForm) form).getCConvocatoriaId().equals("")))
				convoOT = convoLN.buscarOCAPConvocatorias(Long.parseLong(((OCAPSolicitudesForm) form).getCConvocatoriaId()));
			else {
				convoOT = convoLN.buscarOCAPConvocatorias(expedienteCarreraOT.getCConvocatoriaId());
			}
			if ((convoOT == null) || (convoOT.getNDiasRegsolicitud() == 0L))
				((OCAPSolicitudesForm) form).setNDiasPresentarSolicitud("los 15 días siguientes");
			else {
				((OCAPSolicitudesForm) form).setNDiasPresentarSolicitud("los " + convoOT.getNDiasRegsolicitud() + " días siguientes");
			}
			if (flag) {
				((OCAPSolicitudesForm) form).setACiudad(((OCAPSolicitudesForm) form).getACiudad());
				((OCAPSolicitudesForm) form).setBOtroServicio(((OCAPSolicitudesForm) form).getBOtroServicio());
				((OCAPSolicitudesForm) form).setADondeServicio(((OCAPSolicitudesForm) form).getADondeServicio());
				((OCAPSolicitudesForm) form).setCJuridico(((OCAPSolicitudesForm) form).getCJuridico());
				((OCAPSolicitudesForm) form).setFRegistro_oficial(((OCAPSolicitudesForm) form).getFRegistro_oficial());
				((OCAPSolicitudesForm) form).setCSitAdministrativaAuxId(((OCAPSolicitudesForm) form).getCSitAdministrativaAuxId());
				((OCAPSolicitudesForm) form).setDSitAdministrativaAuxOtros(((OCAPSolicitudesForm) form).getDSitAdministrativaAuxOtros());
				((OCAPSolicitudesForm) form).setCEstatutarioActualId(((OCAPSolicitudesForm) form).getCEstatutarioActualId());
				((OCAPSolicitudesForm) form).setCProfesionalActual_id(((OCAPSolicitudesForm) form).getCProfesionalActual_id());
				((OCAPSolicitudesForm) form).setCEspecActual_id(((OCAPSolicitudesForm) form).getCEspecActual_id());
			} else {
				((OCAPSolicitudesForm) form).setBOtroServicio(expedienteCarreraOT.getBOtroServicio());
				((OCAPSolicitudesForm) form).setADondeServicio(expedienteCarreraOT.getADondeServicio());
				((OCAPSolicitudesForm) form).setCJuridico(expedienteCarreraOT.getCJuridico());
				((OCAPSolicitudesForm) form).setFRegistro_oficial(DateUtil.convertDateToString(expedienteCarreraOT.getFRegistroOficial()));
			}

			if (flag) {
				((OCAPSolicitudesForm) form).setResumenCentros(((OCAPSolicitudesForm) form).getResumenCentros());
			}

			if (((OCAPSolicitudesForm) form).getCProfesional_id() != null) {
				request.setAttribute("tipoAccionBis", Constantes.SI);
			} else if (((OCAPSolicitudesForm) form).getCProfesional_id() == null) {
				((OCAPSolicitudesForm) form).setFRegistro_solic(
						DateUtil.getDateTime(Constantes.FECHA_COMPLETA_DATEUTIL, expedienteCarreraOT.getFRegistroSolic()));
				((OCAPSolicitudesForm) form).setNAniosantiguedad(String.valueOf(expedienteCarreraOT.getNAniosAntiguedad()));

				request.setAttribute("tipoAccionBis", Constantes.NO);
			}

			request.setAttribute("tipoAccion", Constantes.IR_DETALLE);

			OCAPConfigApp.logger.debug(getClass().getName() + " irDetalleAlta: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irInsertar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irBuscar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoCategorias = null;
		ArrayList listadoGrados = null;
		ArrayList listadoConvocatorias = null;
		ArrayList listaEstados = null;
		ArrayList listaFases = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irBuscar: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			listadoGrados = gradoLN.listarGrados();

			if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
				OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
				ArrayList listadoGerencias = gerenciasLN.listadoOCAPGerencias();
				session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);
			}

			if (request.getParameter("opcion").equals("todo")) {
				listaEstados = new ArrayList();
				if (request.getParameter("estado") != null) {
					request.setAttribute("estado", request.getParameter("estado"));
					if (Constantes.ESTADO_PENDIENTE_VALUE.equals(request.getParameter("estado")))
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_PENDIENTE, Constantes.ESTADO_PENDIENTE_VALUE));
					if (Constantes.ESTADO_ACEPTADO_VALUE.equals(request.getParameter("estado")))
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_ACEPTADO, Constantes.ESTADO_ACEPTADO_VALUE));
					if (Constantes.ESTADO_DESESTIMADO_VALUE.equals(request.getParameter("estado")))
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_DESESTIMADO, Constantes.ESTADO_DESESTIMADO_VALUE));
					if (Constantes.ESTADO_DENEGADO_VALUE.equals(request.getParameter("estado")))
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_DENEGADO, Constantes.ESTADO_DENEGADO_VALUE));
					if (Constantes.ESTADO_AUTOEVALUADO_VALUE.equals(request.getParameter("estado")))
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_AUTOEVALUADO, Constantes.ESTADO_AUTOEVALUADO_VALUE));
				} else {
					listaEstados.add(new LabelValueBean(Constantes.ESTADO_PENDIENTE, Constantes.ESTADO_PENDIENTE_VALUE));
					listaEstados.add(new LabelValueBean(Constantes.ESTADO_ACEPTADO, Constantes.ESTADO_ACEPTADO_VALUE));

					listaEstados.add(new LabelValueBean(Constantes.ESTADO_EXCLUIDO, Constantes.ESTADO_DENEGADO_VALUE));
				}

				session.setAttribute(Constantes.COMBO_ESTADOS, listaEstados);

				listaFases = new ArrayList();
				listaFases.add(new LabelValueBean(Constantes.FASE_INICIACION_COMBO, Constantes.FASE_INICIACION));
				listaFases.add(new LabelValueBean(Constantes.FASE_MC_COMBO, Constantes.FASE_MC));

				listaFases.add(new LabelValueBean(Constantes.FASE_VALIDACION_CEIS_COMBO, Constantes.FASE_VALIDACION));
				listaFases.add(new LabelValueBean(Constantes.FASE_VALIDACION_CC_COMBO, Constantes.FASE_VALIDACION_CC));
				session.setAttribute(Constantes.COMBO_FASES, listaFases);
			} else if (request.getParameter("opcion").equals("alega")) {
				((OCAPSolicitudesForm) form).setCEstado(Constantes.ESTADO_EXCLUIDO_VALUE);
			} else if (!request.getParameter("opcion").equals("competencia")) {
				listaEstados = new ArrayList();
				session.setAttribute("opcion", request.getParameter("opcion"));
				
				listaEstados.add(new LabelValueBean(Constantes.ESTADO_VACIO, Constantes.ESTADO_VACIO_VALUE));
				
				//OCAP-1386 se elimina el estado "Cero meritos" para el perfil CEIS
				if(!Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()))
					listaEstados.add(new LabelValueBean(Constantes.ESTADO_CERO_MERITOS_NOMBRE, Constantes.ESTADO_CERO_MERITOS_VALUE));
				

				//OCAP-759 se elimina de la lista el estado renuncia para los perfiles CESIS y CC
				if(!Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) && !Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
							listaEstados.add(new LabelValueBean(Constantes.ESTADO_RENUNCIA_NOMBRE, Constantes.ESTADO_RENUNCIA_VALUE));
				listaEstados.add(new LabelValueBean(Constantes.ESTADO_PENDIENTE, Constantes.ESTADO_PENDIENTE_VALUE));
				listaEstados.add(new LabelValueBean(Constantes.ESTADO_ACEPTADO, Constantes.ESTADO_ACEPTADO_VALUE));
				listaEstados.add(new LabelValueBean(Constantes.ESTADO_EXCLUIDO, Constantes.ESTADO_EXCLUIDO_VALUE));
				listaEstados.add(new LabelValueBean(Constantes.ESTADO_AUTOEVALUADO, Constantes.ESTADO_AUTOEVALUADO_VALUE));
				
				//OCAP-1349
				if(Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
					listaEstados.add(new LabelValueBean(Constantes.ESTADO_DESISTIDO, Constantes.ESTADO_DESISTIDO_VALUE));
				}

				if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {
					listaEstados.add(new LabelValueBean(Constantes.ESTADO_EXCLUIDO_ALEGA, Constantes.ESTADO_EXCLUIDO_ALEGA_VALUE));
				}
				session.setAttribute(Constantes.COMBO_ESTADOS, listaEstados);
			}

			session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, listadoGrados);
			session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
			session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			listadoConvocatorias = convocatoriasLN.listarConvocatorias();
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);

			request.setAttribute("opcion", request.getParameter("opcion"));
			session.setAttribute("opcion", request.getParameter("opcion"));
			session.setAttribute("fase", request.getParameter("opcion"));
			((OCAPSolicitudesForm) form).setjspInicio(true);
			session.setAttribute("iRegistro", Integer.toString(1));

			OCAPConfigApp.logger.info(getClass().getName() + " irBuscar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irBuscar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irGenerarActas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoGrados = null;
		ArrayList listadoGerencias = null;
		ArrayList listadoCategorias = null;
		ArrayList listadoVocales = new ArrayList();
		listadoVocales.add(new OCAPAsistenteOT());
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irGenerarActas: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			listadoGrados = gradoLN.listarGrados();

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			listadoGerencias = gerenciasLN.listadoOCAPGerencias();

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			ArrayList listadoConvocatorias = convocatoriasLN.listarConvocatorias();

			session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, listadoGrados);
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);
			session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
			session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);

			Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
			pagina.setElementos(listadoVocales);
			session.setAttribute("listadoVocales", pagina);

			((OCAPSolicitudesForm) form).setjspInicio(true);

			OCAPConfigApp.logger.info(getClass().getName() + " irGenerarActas: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irGenerarActas");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irDenegacion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irDenegacion: Inicio");
			validarAccion(mapping, form, request, response);

			request.setAttribute("fase", request.getParameter("fase"));

			OCAPConfigApp.logger.info(getClass().getName() + " irDenegacion: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irDenegacion");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irInconformidad(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irInconformidad: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			solicitudesOT.setCExp_id(Long.parseLong(request.getParameter("CExp_id")));

			solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);

			((OCAPSolicitudesForm) form).setDNombre(solicitudesOT.getDNombre());
			((OCAPSolicitudesForm) form).setDApellido1(solicitudesOT.getDApellido1());
			((OCAPSolicitudesForm) form).setCDniReal(solicitudesOT.getCDniReal());

			((OCAPSolicitudesForm) form).setBSexo(solicitudesOT.getBSexo());
			((OCAPSolicitudesForm) form).setNTelefono1(solicitudesOT.getNTelefono1());
			((OCAPSolicitudesForm) form).setNTelefono2(solicitudesOT.getNTelefono2());
			((OCAPSolicitudesForm) form).setDCorreoelectronico(solicitudesOT.getDCorreoelectronico());
			((OCAPSolicitudesForm) form).setDDomicilio(solicitudesOT.getDDomicilio());
			((OCAPSolicitudesForm) form).setDProvincia(solicitudesOT.getDProvincia());
			((OCAPSolicitudesForm) form).setDLocalidad(solicitudesOT.getDLocalidad());

			DecimalFormat formatoPost = new DecimalFormat("00000");
			((OCAPSolicitudesForm) form).setCPostalUsu(
					solicitudesOT.getCPostalUsu() == null ? "" : formatoPost.format(Long.parseLong(solicitudesOT.getCPostalUsu())));

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			OCAPGradoOT gradoOT = new OCAPGradoOT();
			if (solicitudesOT.getCGrado_id() != 0L) {
				gradoOT = gradoLN.buscarOCAPGrado(solicitudesOT.getCGrado_id());
				((OCAPSolicitudesForm) form).setDGrado_des(gradoOT.getDDescripcion());
			}

			OCAPModalidadAnteriorLN modalidadAnteriorLN = new OCAPModalidadAnteriorLN(jcylUsuario);
			OCAPModalidadAnteriorOT modaliadAnteriorOT = new OCAPModalidadAnteriorOT();
			if (solicitudesOT.getCModAnterior_id() != 0L) {
				modaliadAnteriorOT = modalidadAnteriorLN.buscarOCAPModalidad(solicitudesOT.getCModAnterior_id());
				((OCAPSolicitudesForm) form).setDModAnterior(modaliadAnteriorOT.getDDescripcion());
			}

			OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
			OCAPProcedimientoOT procOT = new OCAPProcedimientoOT();
			if ((solicitudesOT.getCProcedimientoId() != null) && (!solicitudesOT.getCProcedimientoId().equals(""))) {
				procOT = procLN.buscarOCAPProcedimiento(Long.parseLong(solicitudesOT.getCProcedimientoId()));
				((OCAPSolicitudesForm) form).setDProcedimiento(procOT.getDNombre());
			}

			OCAPCategProfesionalesLN categProfLN = new OCAPCategProfesionalesLN(jcylUsuario);
			OCAPCategProfesionalesOT categProfOT = new OCAPCategProfesionalesOT();

			if (solicitudesOT.getCProfesional_id() != 0L) {
				categProfOT = categProfLN.buscarOCAPCategProfesionales(solicitudesOT.getCProfesional_id());
				((OCAPSolicitudesForm) form).setDProfesional_nombre(categProfOT.getDNombre());
			}

			OCAPEspecialidadesLN especLN = new OCAPEspecialidadesLN(jcylUsuario);
			OCAPEspecialidadesOT especOT = new OCAPEspecialidadesOT();

			if (solicitudesOT.getCEspec_id() != 0L) {
				especOT = especLN.buscarOCAPEspecialidades(solicitudesOT.getCEspec_id());
				((OCAPSolicitudesForm) form).setDEspec_nombre(especOT.getDNombre());
			}

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			OCAPGerenciasOT gerenciasOT = new OCAPGerenciasOT();

			if (solicitudesOT.getCGerencia_id() != 0L) {
				gerenciasOT = gerenciasLN.buscarOCAPGerencias(solicitudesOT.getCGerencia_id());
				((OCAPSolicitudesForm) form).setDGerencia_nombre(gerenciasOT.getDNombre());
			}

			OCAPProvinciasOT provinciaOT = new OCAPProvinciasOT();
			OCAPProvinciasLN provinciaLN = new OCAPProvinciasLN(jcylUsuario);

			if ((solicitudesOT.getCProvinciaCarrera_id() != null) && (!solicitudesOT.getCProvinciaCarrera_id().equals(""))) {
				provinciaOT = provinciaLN.buscarProvincia(String.valueOf(solicitudesOT.getCProvinciaCarrera_id()));
				((OCAPSolicitudesForm) form).setDProvinciaCarrera(Cadenas.capitalizar(provinciaOT.getDProvincia()));
			}

			if ((solicitudesOT.getCProvincia_id() != null) && (!solicitudesOT.getCProvincia_id().equals(""))) {
				provinciaOT = provinciaLN.buscarProvincia(solicitudesOT.getCProvincia_id());
				((OCAPSolicitudesForm) form).setDProvincia(Cadenas.capitalizar(provinciaOT.getDProvincia()));
			}

			OCAPCentroTrabajoLN centroLN = new OCAPCentroTrabajoLN(jcylUsuario);
			OCAPCentroTrabajoOT centroOT = new OCAPCentroTrabajoOT();
			centroOT = centroLN.buscarOCAPCentroTrabajo(solicitudesOT.getCCentrotrabajo_id());
			((OCAPSolicitudesForm) form).setDCentrotrabajo_nombre(centroOT.getDNombre());

			((OCAPSolicitudesForm) form).setCJuridicoId(solicitudesOT.getCJuridicoId());

			OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
			OCAPEstatutarioOT estatutarioOT = new OCAPEstatutarioOT();
			estatutarioOT = estatutarioLN.buscarOCAPEstatutario(categProfOT.getCEstatutId());

			if (Constantes.C_JURIDICO_ESTATUTARIO_COD.equals(solicitudesOT.getCJuridicoId())) {
				((OCAPSolicitudesForm) form).setCJuridicoCombo(String.valueOf(estatutarioOT.getCPersonalId()));
			}

			if ("3".equals(solicitudesOT.getCJuridicoId())) {
				((OCAPSolicitudesForm) form).setDRegimenJuridicoOtros(solicitudesOT.getDRegimenJuridicoOtros());
			}

			((OCAPSolicitudesForm) form).setCSitAdministrativaAuxId(solicitudesOT.getCSitAdministrativaAuxId());
			((OCAPSolicitudesForm) form).setDSitAdministrativaOtros(solicitudesOT.getDSitAdministrativaOtros());

			((OCAPSolicitudesForm) form).setNAniosantiguedad(String.valueOf(solicitudesOT.getNAniosantiguedad()));
			((OCAPSolicitudesForm) form).setNMesesantiguedad(String.valueOf(solicitudesOT.getNMesesantiguedad()));
			((OCAPSolicitudesForm) form).setNDiasantiguedad(String.valueOf(solicitudesOT.getNDiasantiguedad()));

			((OCAPSolicitudesForm) form).setDServicio(solicitudesOT.getAServicio());
			((OCAPSolicitudesForm) form).setDPuesto(solicitudesOT.getAPuesto());

			if (Constantes.VER_DETALLE.equals(request.getParameter("tipoAccion"))) {
				request.setAttribute("tipoAccion", request.getParameter("tipoAccion"));

				OCAPExpedienteCarreraLN expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
				OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();

				expedienteCarreraOT.setCExpId(new Long(solicitudesOT.getCExp_id()));
				expedienteCarreraOT = expCarreraLN.buscarOCAPExpedienteCarrera(expedienteCarreraOT);

				((OCAPSolicitudesForm) form).setFInconf_solic(DateUtil.convertDateToString(expedienteCarreraOT.getFInconfMC()));
				((OCAPSolicitudesForm) form).setDMotivo_inconf_solic(expedienteCarreraOT.getDMotivoInconfMC());
			}

			request.setAttribute("fase", request.getParameter("fase"));

			OCAPConfigApp.logger.info(getClass().getName() + " irInconformidad: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irInconformidad");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward verInconformidad(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " verInconformidad: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			request.getParameter("expId");
			request.getParameter("fase");

			solicitudesOT.setCExp_id(Long.parseLong(request.getParameter("expId")));
			solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);

			((OCAPSolicitudesForm) form).setDNombre(solicitudesOT.getDNombre());
			((OCAPSolicitudesForm) form).setDApellido1(solicitudesOT.getDApellido1());

			((OCAPSolicitudesForm) form).setDCorreoelectronico(solicitudesOT.getDCorreoelectronico());
			((OCAPSolicitudesForm) form).setCDni(solicitudesOT.getCDni());
			((OCAPSolicitudesForm) form).setCDniReal(solicitudesOT.getCDniReal());
			if (solicitudesOT.getNTelefono1().equals("0"))
				((OCAPSolicitudesForm) form).setNTelefono1("-");
			else {
				((OCAPSolicitudesForm) form).setNTelefono1(String.valueOf(solicitudesOT.getNTelefono1()));
			}

			((OCAPSolicitudesForm) form).setBSexo(solicitudesOT.getBSexo());
			((OCAPSolicitudesForm) form).setCJuridico(solicitudesOT.getCJuridico());
			((OCAPSolicitudesForm) form).setDProcedimiento(solicitudesOT.getDProcedimiento());
			((OCAPSolicitudesForm) form).setDEstatutario_nombre(solicitudesOT.getDEstatut_nombre());
			((OCAPSolicitudesForm) form).setDProfesional_nombre(solicitudesOT.getDProfesional_nombre());

			if (solicitudesOT.getDEspec_nombre() != null)
				((OCAPSolicitudesForm) form).setDEspec_nombre(solicitudesOT.getDEspec_nombre());
			else {
				((OCAPSolicitudesForm) form).setDEspec_nombre("-");
			}

			((OCAPSolicitudesForm) form).setDProvincia(Cadenas.capitalizar(solicitudesOT.getDProvincia()));
			((OCAPSolicitudesForm) form).setDTipogerencia_desc(solicitudesOT.getDTipogerencia_desc());
			((OCAPSolicitudesForm) form).setDGerencia_nombre(solicitudesOT.getDGerencia_nombre());
			((OCAPSolicitudesForm) form).setDCentrotrabajo_nombre(solicitudesOT.getDCentrotrabajo_nombre());
			((OCAPSolicitudesForm) form).setDGrado_des(solicitudesOT.getDGrado_des());
			((OCAPSolicitudesForm) form).setFRegistro_solic(solicitudesOT.getFRegistro_solic());

			if (solicitudesOT.getFRegistro_oficial() != null)
				((OCAPSolicitudesForm) form).setFRegistro_oficial(solicitudesOT.getFRegistro_oficial());
			else {
				((OCAPSolicitudesForm) form).setFRegistro_oficial("-");
			}

			((OCAPSolicitudesForm) form).setCConvocatoriaId(solicitudesOT.getCConvocatoriaId() + "");

			if (Constantes.SOLIC_INCONF_REGIS.equals(request.getParameter("fase"))) {
				request.setAttribute("fase", Constantes.FASE_INICIACION);
				((OCAPSolicitudesForm) form)
						.setFInconf_solic(DateUtil.getDateTime(Constantes.FECHA_COMPLETA_DATEUTIL, solicitudesOT.getFInconf_solic()));
				((OCAPSolicitudesForm) form).setDMotivo_inconf_solic(solicitudesOT.getDMotivo_inconf_solic());
			}
			if ("Registro de Alegación de Recepción".equals(request.getParameter("fase"))) {
				request.setAttribute("fase", Constantes.FASE_TRAMITACION);
				((OCAPSolicitudesForm) form)
						.setFInconf_solic(DateUtil.getDateTime(Constantes.FECHA_COMPLETA_DATEUTIL, solicitudesOT.getFInconf_tmc()));
				((OCAPSolicitudesForm) form).setDMotivo_inconf_solic(solicitudesOT.getDMotivo_inconf_tmc());
			}
			if ("Registro Alegación de Méritos".equals(request.getParameter("fase"))) {
				request.setAttribute("fase", Constantes.FASE_VALIDACION);
				((OCAPSolicitudesForm) form)
						.setFInconf_solic(DateUtil.getDateTime(Constantes.FECHA_COMPLETA_DATEUTIL, solicitudesOT.getFInconf_mc()));
				((OCAPSolicitudesForm) form).setDMotivo_inconf_solic(solicitudesOT.getDMotivo_inconf_mc());
			}

			request.setAttribute("tipoAccion", Constantes.VER_DETALLE);
			OCAPConfigApp.logger.info(getClass().getName() + " verInconformidad: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irInconformidad");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward insertar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		int idExpediente = 0;
		String fw = "";
		String pathBase = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " insertar: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();
			OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
			OCAPProvinciasOT provinciaOT = new OCAPProvinciasOT();

			solicitudesOT.setDNombre(((OCAPSolicitudesForm) form).getDNombre());
			solicitudesOT.setDApellido1(((OCAPSolicitudesForm) form).getDApellido1());

			solicitudesOT.setCDni(((OCAPSolicitudesForm) form).getCDniReal());
			solicitudesOT.setCDniReal(((OCAPSolicitudesForm) form).getCDniReal());
			solicitudesOT.setDCorreoelectronico(((OCAPSolicitudesForm) form).getDCorreoelectronico());
			solicitudesOT.setNTelefono1(((OCAPSolicitudesForm) form).getNTelefono1());
			solicitudesOT.setNTelefono2(((OCAPSolicitudesForm) form).getNTelefono2());
			solicitudesOT.setBSexo(((OCAPSolicitudesForm) form).getBSexo());
			solicitudesOT.setDDomicilio(((OCAPSolicitudesForm) form).getDDomicilio());
			solicitudesOT.setDProvinciaUsu(((OCAPSolicitudesForm) form).getDProvinciaUsu());
			solicitudesOT.setCProvinciaUsu_id(((OCAPSolicitudesForm) form).getCProvinciaUsu_id());
			solicitudesOT.setDLocalidadUsu(((OCAPSolicitudesForm) form).getDLocalidadUsu());
			solicitudesOT.setCPostalUsu(((OCAPSolicitudesForm) form).getCPostalUsu());

			solicitudesOT.setCJuridico(((OCAPSolicitudesForm) form).getCJuridico());
			solicitudesOT.setCProcedimientoId(((OCAPSolicitudesForm) form).getCProcedimientoId());
			solicitudesOT.setCEstatutActualId(Long.parseLong(((OCAPSolicitudesForm) form).getCEstatutarioActualId()));
			solicitudesOT.setCProfesionalActual_id(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesionalActual_id()));
			if ((((OCAPSolicitudesForm) form).getCEspecActual_id() != null)
					&& (!((OCAPSolicitudesForm) form).getCEspecActual_id().equals("")))
				solicitudesOT.setCEspecActual_id(Long.parseLong(((OCAPSolicitudesForm) form).getCEspecActual_id()));
			solicitudesOT.setCSitAdministrativaAuxId(((OCAPSolicitudesForm) form).getCSitAdministrativaAuxId());

			if ((((OCAPSolicitudesForm) form).getCSitAdministrativaAuxId() != null)
					&& ("6".equals(((OCAPSolicitudesForm) form).getCSitAdministrativaAuxId())))
				solicitudesOT.setDSitAdministrativaAuxOtros(((OCAPSolicitudesForm) form).getDSitAdministrativaAuxOtros());
			solicitudesOT.setCEstatutId(Long.parseLong(((OCAPSolicitudesForm) form).getCEstatutarioId()));
			solicitudesOT.setCProfesional_id(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesional_id()));
			if ((((OCAPSolicitudesForm) form).getCEspec_id() != null) && (!((OCAPSolicitudesForm) form).getCEspec_id().equals("")))
				solicitudesOT.setCEspec_id(Long.parseLong(((OCAPSolicitudesForm) form).getCEspec_id()));
			solicitudesOT.setCTipogerencia_id(((OCAPSolicitudesForm) form).getCTipogerencia_id());
			solicitudesOT.setCProvincia_id(((OCAPSolicitudesForm) form).getCProvincia_id());
			solicitudesOT.setCGerencia_id(Long.parseLong(((OCAPSolicitudesForm) form).getCGerencia_id()));
			solicitudesOT.setCCentrotrabajo_id(Long.parseLong(((OCAPSolicitudesForm) form).getCCentrotrabajo_id()));

			solicitudesOT.setFRegistro_oficial(((OCAPSolicitudesForm) form).getFRegistro_oficial());

			if ((((OCAPSolicitudesForm) form).getFPlazapropiedad() != null)
					&& (!((OCAPSolicitudesForm) form).getFPlazapropiedad().equals("")))
				solicitudesOT.setFPlazapropiedad(DateUtil.convertStringToDate(((OCAPSolicitudesForm) form).getFPlazapropiedad()));
			if ((((OCAPSolicitudesForm) form).getNAniosantiguedad() != null)
					&& (!((OCAPSolicitudesForm) form).getNAniosantiguedad().equals(""))) {
				solicitudesOT.setNAniosantiguedad(Long.parseLong(((OCAPSolicitudesForm) form).getNAniosantiguedad()));
			}
			if (solicitudesOT.getNAniosantiguedad() == 0L)
				solicitudesOT.setNAniosantiguedad(-1);
			if ((((OCAPSolicitudesForm) form).getNMesesantiguedad() != null)
					&& (!((OCAPSolicitudesForm) form).getNMesesantiguedad().equals("")))
				solicitudesOT.setNMesesantiguedad(Long.parseLong(((OCAPSolicitudesForm) form).getNMesesantiguedad()));
			if ((((OCAPSolicitudesForm) form).getNDiasantiguedad() != null)
					&& (!((OCAPSolicitudesForm) form).getNDiasantiguedad().equals("")))
				solicitudesOT.setNDiasantiguedad(Long.parseLong(((OCAPSolicitudesForm) form).getNDiasantiguedad()));
			if ((((OCAPSolicitudesForm) form).getCGrado_id() != null) && (!((OCAPSolicitudesForm) form).getCGrado_id().equals("")))
				solicitudesOT.setCGrado_id(Long.parseLong(((OCAPSolicitudesForm) form).getCGrado_id()));
			if ((((OCAPSolicitudesForm) form).getCConvocatoriaId() != null)
					&& (!((OCAPSolicitudesForm) form).getCConvocatoriaId().equals("")))
				solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPSolicitudesForm) form).getCConvocatoriaId()));
			solicitudesOT.setFRegistro_solic(((OCAPSolicitudesForm) form).getFRegistro_solic());
			solicitudesOT.setFRegistro_oficial(((OCAPSolicitudesForm) form).getFRegistro_oficial());
			solicitudesOT.setBOtroServicio(((OCAPSolicitudesForm) form).getBOtroServicio());

			if (Constantes.SI.equals(solicitudesOT.getBOtroServicio())) {
				solicitudesOT.setADondeServicio(((OCAPSolicitudesForm) form).getADondeServicio());
			}
			solicitudesOT.setACreadoPor(((JCYLUsuario) request.getSession().getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO))
					.getUsuario().getC_usr_id());

			solicitudesOT.setResumenCentros(((OCAPSolicitudesForm) form).getResumenCentros());

			idExpediente = solicitudesLN.insertar(solicitudesOT, jcylUsuario);

			solicitudesOT.setCExp_id(idExpediente);
			solicitudesOT.setDNombre(((OCAPSolicitudesForm) form).getDNombre());
			solicitudesOT.setDApellido1(((OCAPSolicitudesForm) form).getDApellido1());

			solicitudesOT.setDCorreoelectronico(((OCAPSolicitudesForm) form).getDCorreoelectronico());
			solicitudesOT.setDProcedimiento(((OCAPSolicitudesForm) form).getDProcedimiento());
			solicitudesOT.setDEstatut_nombre(((OCAPSolicitudesForm) form).getDEstatutario_nombre());
			solicitudesOT.setDProfesional_nombre(((OCAPSolicitudesForm) form).getDProfesional_nombre());
			solicitudesOT.setDEspec_nombre(((OCAPSolicitudesForm) form).getDEspec_nombre());
			solicitudesOT.setDProvincia(((OCAPSolicitudesForm) form).getDProvincia());
			solicitudesOT.setDTipogerencia_desc(((OCAPSolicitudesForm) form).getDTipogerencia_desc());
			solicitudesOT.setDCentrotrabajo_nombre(((OCAPSolicitudesForm) form).getDCentrotrabajo_nombre());
			solicitudesOT.setDGrado_des(((OCAPSolicitudesForm) form).getDGrado_des());
			solicitudesOT.setFRegistro_oficial(((OCAPSolicitudesForm) form).getFRegistro_oficial());

			pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");

			if ((((OCAPSolicitudesForm) form).getAccionEnviar() != null)
					&& (((OCAPSolicitudesForm) form).getAccionEnviar().equals("Registrar y Aceptar"))) {
				OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
				OCAPProcedimientoOT procOT = new OCAPProcedimientoOT();
				procOT = procLN.buscarOCAPProcedimiento(Long.parseLong(((OCAPSolicitudesForm) form).getCProcedimientoId()));

				OCAPPersEstatutarioLN personalLN = new OCAPPersEstatutarioLN(jcylUsuario);
				OCAPPersEstatutarioOT personalOT = new OCAPPersEstatutarioOT();
				personalOT = personalLN.buscarOCAPPersEstatutario(Long.parseLong(((OCAPSolicitudesForm) form).getCEstatutarioId()));

				OCAPCategProfesionalesLN categLN = new OCAPCategProfesionalesLN(jcylUsuario);
				OCAPCategProfesionalesOT categOT = new OCAPCategProfesionalesOT();
				categOT = categLN.buscarOCAPCategProfesionales(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesional_id()));

				OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
				OCAPEspecialidadesOT especialidadesOT = new OCAPEspecialidadesOT();
				if ((((OCAPSolicitudesForm) form).getCEspec_id() != null) && (!((OCAPSolicitudesForm) form).getCEspec_id().equals(""))) {
					especialidadesOT = especialidadesLN
							.buscarOCAPEspecialidades(Long.parseLong(((OCAPSolicitudesForm) form).getCEspec_id()));
				}

				OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
				OCAPGerenciasOT gerenciasOT = new OCAPGerenciasOT();
				gerenciasOT = gerenciasLN
						.buscarOCAPGerencias(Long.parseLong(String.valueOf(((OCAPSolicitudesForm) form).getCGerencia_id())));

				provinciaOT = provinciasLN.buscarProvincia(gerenciasOT.getCProvinciaId());

				OCAPTipoGerenciasLN tipoGerenciasLN = new OCAPTipoGerenciasLN(jcylUsuario);
				OCAPTipoGerenciasOT tipoGerenciasOT = new OCAPTipoGerenciasOT();
				tipoGerenciasOT = tipoGerenciasLN.buscarOCAPTipoGerencias(gerenciasOT.getCTipogerenciaId());

				OCAPCentroTrabajoLN centroTrabajoLN = new OCAPCentroTrabajoLN(jcylUsuario);
				OCAPCentroTrabajoOT centroTrabajoOT = new OCAPCentroTrabajoOT();
				centroTrabajoOT = centroTrabajoLN
						.buscarOCAPCentroTrabajo(Long.parseLong(((OCAPSolicitudesForm) form).getCCentrotrabajo_id()));

				OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
				OCAPGradoOT gradoOT = new OCAPGradoOT();
				gradoOT = gradoLN.buscarOCAPGrado(Long.parseLong(((OCAPSolicitudesForm) form).getCGrado_id()));

				solicitudesOT.setDProcedimiento(procOT.getDNombre());
				solicitudesOT.setDEstatut_nombre(personalOT.getDNombre());
				solicitudesOT.setDProfesional_nombre(categOT.getDNombre());
				solicitudesOT.setDEspec_nombre(especialidadesOT.getDNombre());
				solicitudesOT.setDProvincia(Cadenas.capitalizar(provinciaOT.getDProvincia()));
				solicitudesOT.setDTipogerencia_desc(tipoGerenciasOT.getDDescripcion());
				solicitudesOT.setDGerencia_nombre(gerenciasOT.getDNombre());
				solicitudesOT.setDCentrotrabajo_nombre(centroTrabajoOT.getDNombre());

				solicitudesOT.setDGrado_des(gradoOT.getDDescripcion());
				solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPSolicitudesForm) form).getCConvocatoriaId()));

				OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
				OCAPConvocatoriasOT convocatoriasOT = new OCAPConvocatoriasOT();
				convocatoriasOT = convocatoriasLN.buscarOCAPConvocatorias(solicitudesOT.getCConvocatoriaId());

				solicitudesOT.setDConvocatoria_nombre(convocatoriasOT.getDNombre());

				solicitudesLN.aceptarSolicitudIniciacion(idExpediente, ((OCAPSolicitudesForm) form).getFRegistro_solic(), solicitudesOT,
						pathBase, response);
			} else if ((((OCAPSolicitudesForm) form).getAccionEnviar() != null)
					&& (((OCAPSolicitudesForm) form).getAccionEnviar().equals("Registrar y Denegar"))) {
				request.setAttribute("fase", Constantes.FASE_INICIACION);
				request.setAttribute("denegacion", Constantes.SIN_DENEGAR);
				request.setAttribute("registrandoDenegando", Constantes.SI);
			}

			if ((((OCAPSolicitudesForm) form).getAccionEnviar() != null)
					&& (((OCAPSolicitudesForm) form).getAccionEnviar().equals("Registrar y Denegar"))) {
				((OCAPSolicitudesForm) form).setCExp_id(String.valueOf(idExpediente));

				OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
				OCAPProcedimientoOT procOT = new OCAPProcedimientoOT();
				procOT = procLN.buscarOCAPProcedimiento(Long.parseLong(((OCAPSolicitudesForm) form).getCProcedimientoId()));
				((OCAPSolicitudesForm) form).setDProcedimiento(procOT.getDNombre());

				OCAPPersEstatutarioLN personalLN = new OCAPPersEstatutarioLN(jcylUsuario);
				OCAPPersEstatutarioOT personalOT = new OCAPPersEstatutarioOT();
				personalOT = personalLN.buscarOCAPPersEstatutario(Long.parseLong(((OCAPSolicitudesForm) form).getCEstatutarioId()));
				((OCAPSolicitudesForm) form).setDEstatutario_nombre(personalOT.getDNombre());

				OCAPCategProfesionalesLN categProfLN = new OCAPCategProfesionalesLN(jcylUsuario);
				OCAPCategProfesionalesOT categProfOT = new OCAPCategProfesionalesOT();
				categProfOT = categProfLN.buscarOCAPCategProfesionales(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesional_id()));
				((OCAPSolicitudesForm) form).setDProfesional_nombre(categProfOT.getDNombre());

				OCAPEspecialidadesLN especLN = new OCAPEspecialidadesLN(jcylUsuario);
				OCAPEspecialidadesOT especOT = new OCAPEspecialidadesOT();
				if ((((OCAPSolicitudesForm) form).getCEspec_id() != null) && (!((OCAPSolicitudesForm) form).getCEspec_id().equals("0"))) {
					especOT = especLN.buscarOCAPEspecialidades(Long.parseLong(((OCAPSolicitudesForm) form).getCEspec_id()));
					((OCAPSolicitudesForm) form).setDEspec_nombre(especOT.getDNombre());
				}

				provinciaOT = provinciasLN.buscarProvincia(((OCAPSolicitudesForm) form).getCProvincia_id());
				((OCAPSolicitudesForm) form).setDProvincia(Cadenas.capitalizar(provinciaOT.getDProvincia()));

				OCAPTipoGerenciasLN tipoGerenciasLN = new OCAPTipoGerenciasLN(jcylUsuario);
				OCAPTipoGerenciasOT tipoGerenciasOT = new OCAPTipoGerenciasOT();
				tipoGerenciasOT = tipoGerenciasLN
						.buscarOCAPTipoGerencias(Long.parseLong(((OCAPSolicitudesForm) form).getCTipogerencia_id()));
				((OCAPSolicitudesForm) form).setDTipogerencia_desc(tipoGerenciasOT.getDDescripcion());

				OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
				OCAPGerenciasOT gerenciasOT = new OCAPGerenciasOT();
				gerenciasOT = gerenciasLN.buscarOCAPGerencias(Long.parseLong(((OCAPSolicitudesForm) form).getCGerencia_id()));
				((OCAPSolicitudesForm) form).setDGerencia_nombre(gerenciasOT.getDNombre());

				OCAPCentroTrabajoLN centroLN = new OCAPCentroTrabajoLN(jcylUsuario);
				OCAPCentroTrabajoOT centroOT = new OCAPCentroTrabajoOT();
				centroOT = centroLN.buscarOCAPCentroTrabajo(Long.parseLong(((OCAPSolicitudesForm) form).getCCentrotrabajo_id()));
				((OCAPSolicitudesForm) form).setDCentrotrabajo_nombre(centroOT.getDNombre());

				OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
				OCAPGradoOT gradoOT = new OCAPGradoOT();
				gradoOT = gradoLN.buscarOCAPGrado(Long.parseLong(((OCAPSolicitudesForm) form).getCGrado_id()));
				((OCAPSolicitudesForm) form).setDGrado_des(gradoOT.getDDescripcion());

				session.setAttribute("jspDenegacion", "insertar");
				fw = "irDenegacion";
			} else {
				if ((request.getParameter("irDetalle").equals(Constantes.SI)) && (idExpediente != 0)) {
					((OCAPSolicitudesForm) form).setCExp_id(idExpediente + "");
					return irDetalle(mapping, form, request, response);
				}
				request.setAttribute("rutaVuelta", "OCAPSolicitudes.do?accion=irInsertar");
				fw = "mensajeExito";
			}

			OCAPConfigApp.logger.info(getClass().getName() + " insertar: Fin");
		} catch (SendFailedException e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.envioEmail")));
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			if ((e.getMessage() != null) && (e.getMessage().equals("ExisteSolicitudConvocatoria")))
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						new ActionError(TrataError.tratarError(e, "error.existeSolicitudConvocatoria")));
			else {
				errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			}
		}
		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(fw);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward insertarDenegacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		String fw = "";

		ArrayList listaCreditosValidados = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " insertarDenegacion: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			solicitudesOT.setCExp_id(Long.parseLong(((OCAPSolicitudesForm) form).getCExp_id()));
			solicitudesOT.setDNombre(((OCAPSolicitudesForm) form).getDNombre());
			solicitudesOT.setDApellido1(((OCAPSolicitudesForm) form).getDApellido1());

			solicitudesOT.setCDni(((OCAPSolicitudesForm) form).getCDni());
			solicitudesOT.setCDniReal(((OCAPSolicitudesForm) form).getCDniReal());
			solicitudesOT.setBSexo(((OCAPSolicitudesForm) form).getBSexo());
			solicitudesOT.setDCorreoelectronico(((OCAPSolicitudesForm) form).getDCorreoelectronico());
			solicitudesOT.setCJuridico(((OCAPSolicitudesForm) form).getCJuridico());
			solicitudesOT.setCSitAdministrativaAuxId(((OCAPSolicitudesForm) form).getCSitAdministrativaAuxId());
			solicitudesOT.setDSitAdministrativaAuxOtros(((OCAPSolicitudesForm) form).getDSitAdministrativaAuxOtros());
			solicitudesOT.setDProcedimiento(((OCAPSolicitudesForm) form).getDProcedimiento());

			solicitudesOT.setDEstatutActual_nombre(((OCAPSolicitudesForm) form).getDEstatutarioActual_nombre());
			solicitudesOT.setDProfesionalActual_nombre(((OCAPSolicitudesForm) form).getDProfesionalActual_nombre());
			solicitudesOT.setDEspecActual_nombre(((OCAPSolicitudesForm) form).getDEspecActual_nombre());
			solicitudesOT.setDEstatut_nombre(((OCAPSolicitudesForm) form).getDEstatutario_nombre());
			solicitudesOT.setDProfesional_nombre(((OCAPSolicitudesForm) form).getDProfesional_nombre());
			solicitudesOT.setDEspec_nombre(((OCAPSolicitudesForm) form).getDEspec_nombre());
			if ((((OCAPSolicitudesForm) form).getDProvincia() != null) && (!((OCAPSolicitudesForm) form).getDProvincia().equals("")))
				solicitudesOT.setDProvincia(Cadenas.capitalizar(((OCAPSolicitudesForm) form).getDProvincia()));
			solicitudesOT.setACiudad(((OCAPSolicitudesForm) form).getACiudad());
			solicitudesOT.setDTipogerencia_desc(((OCAPSolicitudesForm) form).getDTipogerencia_desc());
			solicitudesOT.setDGerencia_nombre(((OCAPSolicitudesForm) form).getDGerencia_nombre());
			solicitudesOT.setDCentrotrabajo_nombre(((OCAPSolicitudesForm) form).getDCentrotrabajo_nombre());
			solicitudesOT.setDGrado_des(((OCAPSolicitudesForm) form).getDGrado_des());
			solicitudesOT.setFRegistro_solic(((OCAPSolicitudesForm) form).getFRegistro_solic());
			solicitudesOT.setFRegistro_oficial(((OCAPSolicitudesForm) form).getFRegistro_oficial());
			solicitudesOT.setBInconf_antiguedad(((OCAPSolicitudesForm) form).getBInconf_antiguedad());
			solicitudesOT.setBInconf_plazaprop(((OCAPSolicitudesForm) form).getBInconf_plazaprop());
			solicitudesOT.setBPersonales(((OCAPSolicitudesForm) form).getBPersonales());
			solicitudesOT.setBOtrosCentros(((OCAPSolicitudesForm) form).getBOtrosCentros());
			solicitudesOT.setBPlazo(((OCAPSolicitudesForm) form).getBPlazo());
			solicitudesOT.setFRegistro_oficialMC(((OCAPSolicitudesForm) form).getFRegistro_oficialMC());
			solicitudesOT.setCTipo(((OCAPSolicitudesForm) form).getCTipo());

			solicitudesOT.setCExp_id(Long.parseLong(((OCAPSolicitudesForm) form).getCExp_id()));

			if (((request.getParameter("jspAccion") != null) && (request.getParameter("jspAccion").equals("OCAPDenegacionAl")))
					|| ((request.getParameter("subsanacion") != null) && (request.getParameter("subsanacion").equals(Constantes.SI)))) {
				Date fecha = new Date();

				if (Constantes.FASE_INICIACION.equals(request.getParameter("fase"))) {
					solicitudesOT.setBInconf_plazaprop(((OCAPSolicitudesForm) form).getBInconf_plazaprop());
					solicitudesOT.setBInconf_antiguedad(((OCAPSolicitudesForm) form).getBInconf_antiguedad());
					solicitudesOT.setBPersonales(((OCAPSolicitudesForm) form).getBPersonales());
					solicitudesOT.setBOtrosCentros(((OCAPSolicitudesForm) form).getBOtrosCentros());
					solicitudesOT.setBPlazo(((OCAPSolicitudesForm) form).getBPlazo());
					if (!Constantes.SI.equals(request.getParameter("subsanacion"))) {
						if (Constantes.SIN_DENEGAR.equals(request.getParameter("denegacion"))) {
							solicitudesOT.setDObserv_neg_solic(((OCAPSolicitudesForm) form).getDObserv_neg_solic());

							solicitudesOT.setFNegacion_solic(fecha);
						} else if (Constantes.DENEGADO_CON_INCONF.equals(request.getParameter("denegacion"))) {
							solicitudesOT.setDRespuestaInconfSolic(((OCAPSolicitudesForm) form).getDObserv_neg_solic());

							solicitudesOT.setFRespuesta_inconf_solic(fecha);
						}
					} else
						solicitudesOT.setFSubsanacion(new Date());
				}

				if ((Constantes.FASE_VALIDACION.equals(request.getParameter("fase")))
						|| (Constantes.FASE_VALIDACION_CEIS.equals(request.getParameter("fase")))) {
					solicitudesOT.setDMotivo_neg_mc(((OCAPSolicitudesForm) form).getDMotivo_neg());

					solicitudesOT.setDObserv_neg_mc(((OCAPSolicitudesForm) form).getDObserv_neg_solic());

					solicitudesOT.setFNegacion_mc(fecha);
					solicitudesOT.setFAceptacionceis(DateUtil.convertStringToDate(Constantes.FECHA_NULA));

					solicitudesOT.setFRespuestaInconf_MC(fecha);
					solicitudesOT.setFAceptacionCC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
				}

				if ((Constantes.FASE_VALIDACION_CEIS_CC.equals(request.getParameter("fase")))
						|| (Constantes.FASE_VALIDACION_CC.equals(request.getParameter("fase")))) {
					solicitudesOT.setDMotivoNegRespuesta_MC(((OCAPSolicitudesForm) form).getDMotivo_neg());

					solicitudesOT.setDRespuestaInconfMC(((OCAPSolicitudesForm) form).getDObserv_neg_solic());

					solicitudesOT.setFRespuestaInconf_MC(fecha);

					solicitudesOT.setFAceptacionCC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
				}

				solicitudesLN.insertarDenegacion(solicitudesOT, jcylUsuario, listaCreditosValidados);

				if ((session.getAttribute("jspDenegacion") != null) && (session.getAttribute("jspDenegacion").equals("insertar")))
					request.setAttribute("rutaVuelta", "OCAPSolicitudes.do?accion=irInsertar");
				if ((session.getAttribute("jspDenegacion") != null) && (session.getAttribute("jspDenegacion").equals("detalle"))) {
					if (Constantes.SI.equals(request.getParameter("subsanacion"))) {
						if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()))
							request.setAttribute("rutaVuelta",
									"OCAPSolicitudes.do?accion=irDetalle&opcion=" + request.getParameter("opcion") + "&"
											+ Constantes.RECUPERAR_BUSQUEDA_ANTERIOR + "=" + Constantes.SI + "&CExp_id="
											+ solicitudesOT.getCExp_id());
						else {
							request.setAttribute("rutaVuelta", "OCAPSolicitudes.do?accion=buscar&opcion=" + request.getParameter("opcion")
									+ "&" + Constantes.RECUPERAR_BUSQUEDA_ANTERIOR + "=" + Constantes.SI);
						}
					} else {
						request.setAttribute("rutaVuelta", "OCAPSolicitudes.do?accion=buscar&opcion=" + request.getParameter("opcion") + "&"
								+ Constantes.RECUPERAR_BUSQUEDA_ANTERIOR + "=" + Constantes.SI);
					}
				}
				fw = "mensajeExito";
			} else if ((request.getParameter("jspAccion") != null) && (request.getParameter("jspAccion").equals("OCAPSolicitudesDt"))) {
				if ((((OCAPSolicitudesForm) form).getDEspec_nombre() == null)
						|| (((OCAPSolicitudesForm) form).getDEspec_nombre().equals(""))) {
					((OCAPSolicitudesForm) form).setDEspec_nombre("-");
				}
				if ((((OCAPSolicitudesForm) form).getDEspecActual_nombre() == null)
						|| (((OCAPSolicitudesForm) form).getDEspecActual_nombre().equals(""))) {
					((OCAPSolicitudesForm) form).setDEspecActual_nombre("-");
				}

				fw = "irDenegacion";
			}

			request.setAttribute("fase", request.getParameter("fase"));
			request.setAttribute("denegacion", request.getParameter("denegacion"));

			OCAPConfigApp.logger.info(getClass().getName() + " insertarDenegacion: Fin");
		} catch (SendFailedException e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.envioEmail")));
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(fw);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward insertarInconformidad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		String fw = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " insertarInconformidad: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			solicitudesOT.setCExp_id(Long.parseLong(((OCAPSolicitudesForm) form).getCExp_id()));

			String motivoInconf = ((OCAPSolicitudesForm) form).getDMotivo_inconf_solic();
			Date fecha = DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL, ((OCAPSolicitudesForm) form).getFInconf_solic());
			if (Constantes.FASE_INICIACION.equals(request.getParameter("fase"))) {
				solicitudesOT.setDMotivo_inconf_solic(motivoInconf);
				solicitudesOT.setFInconf_solic(fecha);
				solicitudesOT.setDDocAsociado("InconfSolicitud");
			}
			if (Constantes.FASE_TRAMITACION.equals(request.getParameter("fase"))) {
				solicitudesOT.setDMotivo_inconf_tmc(motivoInconf);
				solicitudesOT.setFInconf_tmc(fecha);
				solicitudesOT.setDDocAsociado("InconfRecepMC");
			}
			if (Constantes.FASE_VALIDACION.equals(request.getParameter("fase"))) {
				solicitudesOT.setDMotivo_inconf_mc(motivoInconf);
				solicitudesOT.setFInconf_mc(fecha);
				solicitudesOT.setDDocAsociado("InconfEvaMC");
				solicitudesOT.setCEstadoId(14);
			}

			solicitudesLN.insertarInconformidad(solicitudesOT, jcylUsuario);

			request.setAttribute("rutaVuelta", "OCAPSolicitudes.do?accion=buscar&opcion=" + request.getParameter("opcion") + "&"
					+ Constantes.RECUPERAR_BUSQUEDA_ANTERIOR + "=" + Constantes.SI);
			fw = "mensajeExito";

			OCAPConfigApp.logger.info(getClass().getName() + " insertarInconformidad: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(fw);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward reactivarCA(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		String fw = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " reactivarCA: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			if (((OCAPSolicitudesForm) form).getCodigoId() != null) {
				String codigoEvaluado = ((OCAPSolicitudesForm) form).getCodigoId();
				codigoEvaluado = codigoEvaluado.substring(Constantes.CODIGO_EVALUADO.length());
				solicitudesOT.setCExp_id(Long.parseLong(codigoEvaluado));
			}
			solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);

			if ((solicitudesOT == null) || (solicitudesOT.getCUsr_id() == 0L)) {
				fw = "irReactivarCA";
				request.setAttribute("noExisteExpediente", Constantes.SI);
			} else if ((solicitudesOT.getFAceptacionCC() == null) || ("".equals(solicitudesOT.getFAceptacionCC()))) {
				fw = "irReactivarCA";
				request.setAttribute("expedienteNoAceptadoMC", Constantes.SI);
			} else if (solicitudesOT.getCItinerario_id() == 0L) {
				fw = "irReactivarCA";
				request.setAttribute("expedienteItinerarioNoElegido", Constantes.SI);
			} else {
				request.setAttribute("rutaVuelta", "OCAPSolicitudes.do?accion=irReactivarCa");
				if (solicitudesLN.reabrirCA(solicitudesOT, jcylUsuario))
					fw = "mensajeExito";
				else {
					fw = "irFaseCAConcluida";
				}

			}

			OCAPConfigApp.logger.info(getClass().getName() + " reactivarCA: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(fw);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irReactivarCa(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.debug(getClass().getName() + " irReactivarCa: Inicio");
			validarAccion(mapping, form, request, response);

			OCAPConfigApp.logger.debug(getClass().getName() + " irReactivarCa: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irReactivarCA");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward validadoCEIS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		String fw = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " validadoCEIS: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			solicitudesOT.setCExp_id(Long.parseLong(((OCAPSolicitudesForm) form).getCExp_id()));

			solicitudesLN.validadoCEIS(solicitudesOT, jcylUsuario);

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&CExp_id=" + ((OCAPSolicitudesForm) form).getCExp_id());
			fw = "mensajeExito";

			OCAPConfigApp.logger.info(getClass().getName() + " validadoCEIS: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(fw);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward insertarAceptacionIniciacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		String pathBase = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " insertarAceptacionIniciacion: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			Date hoy = new Date();
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_COMPLETA_DATEUTIL);

			solicitudesOT.setCExp_id(Long.parseLong(((OCAPSolicitudesForm) form).getCExp_id()));
			solicitudesOT.setDNombre(((OCAPSolicitudesForm) form).getDNombre());
			solicitudesOT.setDApellido1(((OCAPSolicitudesForm) form).getDApellido1());

			solicitudesOT.setCDni(((OCAPSolicitudesForm) form).getCDni());
			solicitudesOT.setCDniReal(((OCAPSolicitudesForm) form).getCDniReal());
			solicitudesOT.setBSexo(((OCAPSolicitudesForm) form).getBSexo());
			solicitudesOT.setDCorreoelectronico(((OCAPSolicitudesForm) form).getDCorreoelectronico());
			solicitudesOT.setDProcedimiento(((OCAPSolicitudesForm) form).getDProcedimiento());

			solicitudesOT.setDEstatutActual_nombre(((OCAPSolicitudesForm) form).getDEstatutarioActual_nombre());
			solicitudesOT.setDProfesionalActual_nombre(((OCAPSolicitudesForm) form).getDProfesionalActual_nombre());
			solicitudesOT.setDEspecActual_nombre(((OCAPSolicitudesForm) form).getDEspecActual_nombre());
			solicitudesOT.setDEstatut_nombre(((OCAPSolicitudesForm) form).getDEstatutario_nombre());
			solicitudesOT.setDProfesional_nombre(((OCAPSolicitudesForm) form).getDProfesional_nombre());
			solicitudesOT.setDEspec_nombre(((OCAPSolicitudesForm) form).getDEspec_nombre());
			solicitudesOT.setDProvincia(Cadenas.capitalizar(((OCAPSolicitudesForm) form).getDProvincia()));
			solicitudesOT.setDTipogerencia_desc(((OCAPSolicitudesForm) form).getDTipogerencia_desc());
			solicitudesOT.setDGerencia_nombre(((OCAPSolicitudesForm) form).getDGerencia_nombre());
			solicitudesOT.setDCentrotrabajo_nombre(((OCAPSolicitudesForm) form).getDCentrotrabajo_nombre());
			solicitudesOT.setDGrado_des(((OCAPSolicitudesForm) form).getDGrado_des());
			solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPSolicitudesForm) form).getCConvocatoriaId()));
			solicitudesOT.setFRegistro_solic(((OCAPSolicitudesForm) form).getFRegistro_solic());
			solicitudesOT.setFRegistro_oficial(((OCAPSolicitudesForm) form).getFRegistro_oficial());

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT convocatoriasOT = new OCAPConvocatoriasOT();
			convocatoriasOT = convocatoriasLN.buscarOCAPConvocatorias(solicitudesOT.getCConvocatoriaId());

			solicitudesOT.setDConvocatoria_nombre(convocatoriasOT.getDNombre());

			OCAPUsuariosOT usuariosOT = new OCAPUsuariosOT();

			OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);

			usuariosOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDniReal(), solicitudesOT.getCExp_id(), jcylUsuario);

			ArrayList listaMeritos = solicitudesLN.buscarOCAPMeritosPorCategProfesional(usuariosOT.getCProfesionalId(),
					usuariosOT.getCGrado_id(), jcylUsuario);

			OCAPCreditosOT defCredOT = null;

			StringBuffer texto = new StringBuffer(40);
			int contador = 0;

			for (int j = 0; j < listaMeritos.size(); j++) {
				defCredOT = (OCAPCreditosOT) listaMeritos.get(j);

				if (Constantes.DEF_MERITO_FORMACION.equals(defCredOT.getCDefmeritoId() + "")) {
					texto.append(defCredOT.getDMerito());
					contador++;
				}
			}

			OCAPDefMeritoscurricularesOT defMCOT = null;
			OCAPMeritoscurricularesOT mcOTaux = null;
			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPDefMeritoscurricularesLN defMCLN = new OCAPDefMeritoscurricularesLN(jcylUsuario);
			ArrayList listaDefMeritos = defMCLN.listadoOCAPDefMeritoscurriculares();

			for (int j = 0; j < listaDefMeritos.size(); j++) {
				defMCOT = (OCAPDefMeritoscurricularesOT) listaDefMeritos.get(j);

				if ((!Constantes.DEF_MERITO_FORMACION.equals(defMCOT.getCDefmeritoId() + "")) && (!Constantes.DEF_MERITO_OPCIONALES.equals(defMCOT.getCDefmeritoId() + ""))) {
					mcOTaux = mcLN.buscarOCAPMeritoscurricularesPorUsuarioOT(usuariosOT, new Long(defMCOT.getCDefmeritoId()), Constantes.SI,
							jcylUsuario, false);
					listaMeritos = mcOTaux.getListaMeritosUsuario();

					if (listaMeritos.size() != 0) {
						if (contador != 0)
							texto.append(", ").append(defMCOT.getDNombre());
						else {
							texto.append(defMCOT.getDNombre());
						}

						contador++;
					}
				}
			}

			solicitudesOT.setDMensajeInforme(texto.toString());

			pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");

			solicitudesLN.aceptarSolicitudIniciacion(Integer.parseInt(((OCAPSolicitudesForm) form).getCExp_id()), df.format(hoy),
					solicitudesOT, pathBase, response);

			if ((request.getParameter("opcion") == null) || ("".equals(request.getParameter("opcion"))))
				request.setAttribute("rutaVuelta",
						"OCAPSolicitudes.do?accion=irDetalle&CExp_id=" + ((OCAPSolicitudesForm) form).getCExp_id());
			else
				request.setAttribute("rutaVuelta", "OCAPSolicitudes.do?accion=irDetalle&opcion=" + request.getParameter("opcion")
						+ "&CExp_id=" + ((OCAPSolicitudesForm) form).getCExp_id());
			OCAPConfigApp.logger.info(getClass().getName() + " insertarAceptacionIniciacion: Fin");
		} catch (SendFailedException e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.envioEmail")));
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mensajeExito");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward insertarAceptacionTramitacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " insertarAceptacionTramitacion: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			Date hoy = new Date();
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_COMPLETA_DATEUTIL);

			String fRegistroOficialMC = ((OCAPSolicitudesForm) form).getFRegistro_oficialMC();

			solicitudesLN.aceptarTramitacion(Integer.parseInt(((OCAPSolicitudesForm) form).getCExp_id()), df.format(hoy),
					((OCAPSolicitudesForm) form).getCConvocatoriaId(), fRegistroOficialMC);

			request.setAttribute("rutaVuelta", "OCAPSolicitudes.do?accion=buscar&opcion=" + request.getParameter("opcion") + "&"
					+ Constantes.RECUPERAR_BUSQUEDA_ANTERIOR + "=" + Constantes.SI);

			OCAPConfigApp.logger.info(getClass().getName() + " insertarAceptacionTramitacion: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			if ("convocatoriaEliminada".equals(e.getMessage()))
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						new ActionError(TrataError.tratarError(e, "error.convocatoriaEliminada")));
			else {
				errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			}
		}
		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mensajeExito");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward insertarAceptacionEvaluacionMC(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " insertarAceptacionEvaluacionMC: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);

			solicitudesLN.aceptarEvaluacionMC(Long.parseLong(((OCAPSolicitudesForm) form).getCExp_id()),
					((OCAPSolicitudesForm) form).getCConvocatoriaId(), jcylUsuario.getUser().getRol());

			if ((Constantes.GRS_MERIT.equals(request.getParameter("opcion")))
					|| (Constantes.GRS_PROCC.equals(request.getParameter("opcion")))) {
				request.setAttribute("rutaVuelta", "OCAPSolicitudes.do?accion=buscarGrs&opcion=" + request.getParameter("opcion") + "&"
						+ Constantes.RECUPERAR_BUSQUEDA_ANTERIOR + "=" + Constantes.SI);
			} else {
				request.setAttribute("rutaVuelta", "OCAPSolicitudes.do?accion=buscar&opcion=" + request.getParameter("opcion") + "&"
						+ Constantes.RECUPERAR_BUSQUEDA_ANTERIOR + "=" + Constantes.SI);
			}

			OCAPConfigApp.logger.info(getClass().getName() + " insertarAceptacionEvaluacionMC: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mensajeExito");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irDetalle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		OCAPConvocatoriasOT convoOT = null;
		OCAPConvocatoriasLN convoLN = null;
		try {
			OCAPConfigApp.logger.debug(getClass().getName() + " : Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			if (request.getParameter("opcion") == null)
				request.setAttribute("opcion", session.getAttribute("opcion"));
			else {
				request.setAttribute("opcion", request.getParameter("opcion"));
			}
			if (((OCAPSolicitudesForm) form).getCExp_id() == null) {
				OCAPUsuariosLN usuarioLN = new OCAPUsuariosLN(jcylUsuario);
				OCAPUsuariosOT usuariosOT = usuarioLN.buscarOCAPUsuariosPorNIF(jcylUsuario.getUser().getC_usr_id());

				if ((usuariosOT == null) || (usuariosOT.getCUsrId() == null)) {
					return mapping.findForward("irNoExisteSolicitud");
				}

				String cConvocatoria = obtenerConvocatoria(request, ((OCAPSolicitudesForm) form).getCConvocatoriaId());

				OCAPConfigApp.logger.warn ("TRAZA PROD- -------------------------------------------------------------------------------");
			       OCAPConfigApp.logger.warn ("TRAZA PROD- Valores de cConvocatoria en OCAPSolicitudesAction.irDetalle extraida del OCAPSolicitudesForm " +cConvocatoria);
			       OCAPConfigApp.logger.warn ("TRAZA PROD- -------------------------------------------------------------------------------");
				
				
				
				
				OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
				long expId = expedienteCarreraLN.buscarCExpIdPorUsuarioConvocatoria(usuariosOT.getCUsrId().longValue(),
						Long.parseLong(cConvocatoria));

				if (expId == 0L) {
					return mapping.findForward("irNoExisteSolicitud");
				}
				solicitudesOT.setCExp_id(expId);
			} else {
				solicitudesOT.setCExp_id(Long.parseLong(((OCAPSolicitudesForm) form).getCExp_id()));
			}

			solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);
			if (solicitudesOT == null) {
				return mapping.findForward("irNoExisteSolicitud");
			}
			
			OCAPNuevaSolicitudLN nuevaSolicitudLN = new OCAPNuevaSolicitudLN(jcylUsuario);
			OCAPSolicitudesOT solicitudUsuarioOT = nuevaSolicitudLN.detalleSolicitud(solicitudesOT.getCSolicitudId());
			
			((OCAPSolicitudesForm) form).setDNombre(solicitudUsuarioOT.getDNombre());
			((OCAPSolicitudesForm) form).setDApellido1(solicitudUsuarioOT.getDApellido1());
			((OCAPSolicitudesForm) form).setCDniReal(solicitudesOT.getCDniReal());
			((OCAPSolicitudesForm) form).setCDni(solicitudesOT.getCDni());

			((OCAPSolicitudesForm) form).setBSexo(solicitudUsuarioOT.getBSexo());
			((OCAPSolicitudesForm) form).setNTelefono1(solicitudUsuarioOT.getNTelefono1());
			((OCAPSolicitudesForm) form).setNTelefono2(solicitudUsuarioOT.getNTelefono2());
			((OCAPSolicitudesForm) form).setDCorreoelectronico(solicitudUsuarioOT.getDCorreoelectronico());
			((OCAPSolicitudesForm) form).setDDomicilio(solicitudUsuarioOT.getDDomicilio());
			((OCAPSolicitudesForm) form).setDProvincia(solicitudUsuarioOT.getDProvinciaUsu());
			((OCAPSolicitudesForm) form).setDLocalidad(solicitudUsuarioOT.getDLocalidad());

			DecimalFormat formato = new DecimalFormat("000000");
			((OCAPSolicitudesForm) form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(solicitudesOT.getCExp_id()));

			DecimalFormat formatoPost = new DecimalFormat("00000");
			((OCAPSolicitudesForm) form).setCPostalUsu(
					solicitudUsuarioOT.getCPostalUsu() == null ? "" : formatoPost.format(Long.parseLong(solicitudUsuarioOT.getCPostalUsu())));

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			if (solicitudesOT.getCGrado_id() != 0L) {
				OCAPGradoOT gradoOT = gradoLN.buscarOCAPGrado(solicitudesOT.getCGrado_id());
				((OCAPSolicitudesForm) form).setDGrado_des(gradoOT.getDDescripcion());
				((OCAPSolicitudesForm) form).setCGrado_id(String.valueOf(solicitudesOT.getCGrado_id()));
			}

			OCAPModalidadAnteriorLN modalidadAnteriorLN = new OCAPModalidadAnteriorLN(jcylUsuario);
			if (solicitudesOT.getCModAnterior_id() != 0L) {
				OCAPModalidadAnteriorOT modaliadAnteriorOT = modalidadAnteriorLN.buscarOCAPModalidad(solicitudesOT.getCModAnterior_id());
				((OCAPSolicitudesForm) form).setDModAnterior(modaliadAnteriorOT.getDDescripcion());
			}

			OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
			if ((solicitudesOT.getCProcedimientoId() != null) && (!solicitudesOT.getCProcedimientoId().equals(""))) {
				OCAPProcedimientoOT procOT = procLN.buscarOCAPProcedimiento(Long.parseLong(solicitudesOT.getCProcedimientoId()));
				((OCAPSolicitudesForm) form).setDProcedimiento(procOT.getDNombre());
				((OCAPSolicitudesForm) form).setCProcedimientoId(solicitudesOT.getCProcedimientoId());
			}

			OCAPCategProfesionalesLN categProfLN = new OCAPCategProfesionalesLN(jcylUsuario);
			OCAPCategProfesionalesOT categProfOT = null;
			if (solicitudesOT.getCProfesional_id() != 0L) {
				categProfOT = categProfLN.buscarOCAPCategProfesionales(solicitudesOT.getCProfesional_id());
				((OCAPSolicitudesForm) form).setDProfesional_nombre(categProfOT.getDNombre());
				((OCAPSolicitudesForm) form).setCProfesional_id(String.valueOf(solicitudesOT.getCProfesional_id()));
			} else {
				categProfOT = new OCAPCategProfesionalesOT();
			}

			OCAPEspecialidadesLN especLN = new OCAPEspecialidadesLN(jcylUsuario);
			if (solicitudesOT.getCEspec_id() != 0L) {
				OCAPEspecialidadesOT especOT = especLN.buscarOCAPEspecialidades(solicitudesOT.getCEspec_id());
				((OCAPSolicitudesForm) form).setDEspec_nombre(especOT.getDNombre());
				((OCAPSolicitudesForm) form).setCEspec_id(String.valueOf(solicitudesOT.getCEspec_id()));
			}

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			if (solicitudUsuarioOT.getCGerenciaActualId() != 0L) {
				OCAPGerenciasOT gerenciasOT = gerenciasLN.buscarOCAPGerencias(solicitudUsuarioOT.getCGerenciaActualId());
				((OCAPSolicitudesForm) form).setDGerencia_nombre(gerenciasOT.getDNombre());
				((OCAPSolicitudesForm) form).setCGerencia_id(String.valueOf(solicitudUsuarioOT.getCGerenciaActualId()));
			}

			OCAPProvinciasLN provinciaLN = new OCAPProvinciasLN(jcylUsuario);
			if ((solicitudUsuarioOT.getCProvCarreraActualId() != null) && (!solicitudUsuarioOT.getCProvCarreraActualId().equals(""))) {
				OCAPProvinciasOT provinciaOT = provinciaLN.buscarProvincia(String.valueOf(solicitudUsuarioOT.getCProvCarreraActualId()));
				((OCAPSolicitudesForm) form).setDProvinciaCarrera(Cadenas.capitalizar(provinciaOT.getDProvincia()));
			}

			if ((solicitudUsuarioOT.getCProvincia_id() != null) && (!solicitudUsuarioOT.getCProvincia_id().equals(""))) {
				OCAPProvinciasOT provinciaOT = provinciaLN.buscarProvincia(solicitudUsuarioOT.getCProvincia_id());
				((OCAPSolicitudesForm) form).setDProvincia(Cadenas.capitalizar(provinciaOT.getDProvincia()));
				((OCAPSolicitudesForm) form).setCProvincia_id(String.valueOf(solicitudUsuarioOT.getCProvincia_id()));
			}

			OCAPCentroTrabajoLN centroLN = new OCAPCentroTrabajoLN(jcylUsuario);
			OCAPCentroTrabajoOT centroOT = centroLN.buscarOCAPCentroTrabajo(solicitudUsuarioOT.getCCentroTrabActualId());
			((OCAPSolicitudesForm) form).setDCentrotrabajo_nombre(centroOT.getDNombre());
			((OCAPSolicitudesForm) form).setCCentrotrabajo_id(String.valueOf(solicitudUsuarioOT.getCCentroTrabActualId()));

			((OCAPSolicitudesForm) form).setCJuridicoId(solicitudesOT.getCJuridicoId());

			OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
			OCAPEstatutarioOT estatutarioOT = estatutarioLN.buscarOCAPEstatutario(categProfOT.getCEstatutId());
			if (Constantes.C_JURIDICO_ESTATUTARIO_COD.equals(solicitudesOT.getCJuridicoId())) {
				((OCAPSolicitudesForm) form).setCJuridicoCombo(String.valueOf(estatutarioOT.getCPersonalId()));
			}

			if (Constantes.C_JURIDICO_OTROS_COD.equals(solicitudesOT.getCJuridicoId())) {
				((OCAPSolicitudesForm) form).setDRegimenJuridicoOtros(solicitudesOT.getDRegimenJuridicoOtros());
			}

			((OCAPSolicitudesForm) form).setCSitAdministrativaAuxId(solicitudesOT.getCSitAdministrativaAuxId());
			((OCAPSolicitudesForm) form).setDSitAdministrativaOtros(solicitudesOT.getDSitAdministrativaOtros());
			((OCAPSolicitudesForm) form).setNAniosantiguedad(String.valueOf(solicitudesOT.getNAniosantiguedad()));
			((OCAPSolicitudesForm) form).setNMesesantiguedad(String.valueOf(solicitudesOT.getNMesesantiguedad()));
			((OCAPSolicitudesForm) form).setNDiasantiguedad(String.valueOf(solicitudesOT.getNDiasantiguedad()));

			((OCAPSolicitudesForm) form).setCConvocatoriaId(String.valueOf(solicitudesOT.getCConvocatoriaId()));
			((OCAPSolicitudesForm) form).setBOtroServicio(solicitudesOT.getBOtroServicio());
			((OCAPSolicitudesForm) form).setADondeServicio(solicitudesOT.getADondeServicio());
			((OCAPSolicitudesForm) form).setResumenCentros(solicitudesOT.getResumenCentros());
			((OCAPSolicitudesForm) form).setFRegistro_solic(solicitudesOT.getFRegistro_solic());
			((OCAPSolicitudesForm) form).setDServicio(solicitudesOT.getAServicio());
			((OCAPSolicitudesForm) form).setDPuesto(solicitudesOT.getAPuesto());
			((OCAPSolicitudesForm) form).setCConvocatoriaId(String.valueOf(solicitudesOT.getCConvocatoriaId()));

			((OCAPSolicitudesForm) form).setRContinuidad_proceso_validacion(solicitudesOT.getRContinuidad_proceso_validacion());
			((OCAPSolicitudesForm) form).setListaCreditos(solicitudesOT.getListaCreditos());
			((OCAPSolicitudesForm) form).setListaOtrosCentros(solicitudesOT.getListaOtrosCentros());
			((OCAPSolicitudesForm) form).setDDirector(solicitudesOT.getDDirector());
			((OCAPSolicitudesForm) form).setDGerente(solicitudesOT.getDGerente());
			((OCAPSolicitudesForm) form).setBValidacion_cc(solicitudesOT.getBValidacionCC());
			((OCAPSolicitudesForm) form).setBReconocimientoGrado(solicitudesOT.getBReconocimientoGrado());
			((OCAPSolicitudesForm) form).setCTipo(request.getParameter("CTipo"));

			if ((solicitudesOT.getBValidacionCC() != null) && (solicitudesOT.getBValidacionCC().equals(Constantes.NO))) {
				((OCAPSolicitudesForm) form).setRContinuidad_proceso_validacion(Constantes.NO);
			}

			if ((solicitudesOT.getFNegacion_solic() != null) && (solicitudesOT.getFAceptac_solic() == null))
				request.setAttribute("generarPDF", Constantes.SI);
			else {
				request.setAttribute("generarPDF", Constantes.NO);
			}

			convoLN = new OCAPConvocatoriasLN(jcylUsuario);
			convoOT = convoLN.buscarOCAPConvocatorias(solicitudesOT.getCConvocatoriaId());
			if (null!= convoOT){
				((OCAPSolicitudesForm) form).setDNombreConvocatoria(convoOT.getDNombre());
			}

			if ((convoOT.getFInicioEstudioCC() != null) && (DateUtil.convertStringToDate(convoOT.getFInicioEstudioCC()).before(new Date())))
				request.setAttribute("permisoGRS", Constantes.SI);
			else {
				request.setAttribute("permisoGRS", Constantes.NO);
			}
			
			boolean enPeriodoCEIS = false;
			boolean enPeriodoCC = false;
			Calendar c = Calendar.getInstance();
						
			if (solicitudesOT.getFFin_eval_mc()!= null && solicitudesOT.getFInicio_eval_mc()!=null )
			{
				/* Durante el último día de Val MC también se valoran méritos */
				c.setTime(solicitudesOT.getFFin_eval_mc());
				c.add(Calendar.DATE, 1);
				/* Si estamos entre las fechas de inicio y fin */
				if ((new Date()).getTime() >= (solicitudesOT.getFInicio_eval_mc()).getTime()
						&& (new Date()).getTime() < c.getTime().getTime()) {
					enPeriodoCEIS=true;
				}
			}
					
			if (solicitudesOT.getFFin_cc()!= null && solicitudesOT.getFInicio_cc()!=null )
			{
				/* Durante el último día de Val CC también se valoran méritos */			
				c.setTime(solicitudesOT.getFFin_cc());
				c.add(Calendar.DATE, 1);
				/* Si estamos entre las fechas de inicio y fin */
				if ((solicitudesOT.getFInicio_cc()!=null) &&  ((new Date()).getTime() >= (solicitudesOT.getFInicio_cc()).getTime()
						&& (new Date()).getTime() < c.getTime().getTime())) {

					enPeriodoCC=true;
				}
			}
	
			Date dHoy = new Date();

			OCAPExpedientemcLN expmcLN = new OCAPExpedientemcLN(jcylUsuario);
			if (expmcLN.buscarPdtesAclararPorExpId(solicitudesOT.getCExp_id(), jcylUsuario, null) > 0L)
				request.setAttribute("pendientesAclaracion", Constantes.SI);
			else
			{
				request.setAttribute("pendientesAclaracion", Constantes.NO);
			}
			if (solicitudesOT.getFAceptac_solic() == null)
			{
				request.setAttribute("fase", Constantes.FASE_INICIACION);

				if ((solicitudesOT.getFNegacion_solic() != null) && (solicitudesOT.getFInconf_solic() == null))
				{
					if ((convoOT.getFAlegsolicitud() == null)
							|| (!DateUtil.convertStringToDate(convoOT.getFAlegsolicitud()).before(new Date())))
					{
						request.setAttribute("generarPDF", Constantes.SI);
					}
					request.setAttribute("denegacion", Constantes.DENEGADO_SIN_INCONF);
				} else if ((solicitudesOT.getFNegacion_solic() != null) && (solicitudesOT.getFInconf_solic() != null)
						&& (solicitudesOT.getFRespuesta_inconf_solic() == null))
				{
					request.setAttribute("denegacion", Constantes.DENEGADO_CON_INCONF);

					if (convoOT.getFAlegsolicitud() != null)
					{
						Date fFinInconformidades = DateUtil.addDias(DateUtil.convertStringToDate(convoOT.getFAlegsolicitud()),
								(int) convoOT.getNDiasPublistado());
						if (solicitudesOT.getFInconf_solic().after(fFinInconformidades))
							request.setAttribute("inconformidadFueraPlazo", Constantes.SI);
					}
				} else if ((solicitudesOT.getFNegacion_solic() != null) && (solicitudesOT.getFInconf_solic() != null)
						&& (solicitudesOT.getFRespuesta_inconf_solic() != null))
				{
					request.setAttribute("denegacion", Constantes.REDENEGADO);
				} else
				{
					request.setAttribute("detalle", Constantes.NO);
				}
			} else if ((solicitudesOT.getFInicio_mc() == null)
					|| ((solicitudesOT.getFFin_mc() != null) && (dHoy.before(solicitudesOT.getFFin_mc()))))
			{
				request.setAttribute("fase", Constantes.FASE_MC);
				request.setAttribute("generarPDF", Constantes.SI);
			} else
			{
				
				
				boolean ccComoCeis = (Constantes.SITUACION_DIRECTIVO.equals(solicitudesOT.getCProcedimientoId()))
						|| (Constantes.SITUACION_ESTRUCTURA.equals(solicitudesOT.getCProcedimientoId()));
				
				
				// Rol CEIS
				if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()))
				{
					request.setAttribute("fase", Constantes.FASE_VALIDACION);
					if  (enPeriodoCEIS) request.setAttribute("estado", Constantes.ESTADO_PENDIENTE_VALUE);
					

					// Rol CC
				} else if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
				{
					if (ccComoCeis	&& enPeriodoCEIS) 
					{
						request.setAttribute("fase", Constantes.FASE_VALIDACION_CEIS);
						request.setAttribute("estado", Constantes.ESTADO_PENDIENTE_VALUE);
						 
					}
			 

					if (enPeriodoCC)
					{
						if (ccComoCeis)
							request.setAttribute("fase", Constantes.FASE_VALIDACION_CEIS_CC);
						else
							request.setAttribute("fase", Constantes.FASE_VALIDACION_CC);
						
						request.setAttribute("estado", Constantes.ESTADO_PENDIENTE_VALUE);
					}
				}  

				if (solicitudesOT.getFInicio_ca() != null)
				{
					if (solicitudesOT.getFFin_cc() == null)
						request.setAttribute("faseIII", Constantes.FASE_CC);
					else
						request.setAttribute("faseIII", Constantes.FASE_FIN);
				} else
					request.setAttribute("faseIII", "");

			}

			request.setAttribute("tipoAccion", Constantes.VER_DETALLE);
			session.setAttribute("jspDenegacion", "detalle");

			OCAPConfigApp.logger.debug(getClass().getName() + " irDetalle: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irDetalle");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward buscar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoSolicitudes = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscar: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPSolicitudesForm) request.getSession().getAttribute("OCAPSolicitudesFormBuscador");
				request.setAttribute("OCAPSolicitudesForm", form);
			} else {
				request.getSession().setAttribute("OCAPSolicitudesFormBuscador", form);
				session.setAttribute("iRegistro", 1 + "");
			}

			int primerRegistro = 1;
			int registrosPorPagina = 10;
			String registro = null;

			if ((registro = request.getParameter("iRegistro")) != null)
				try {
					primerRegistro = Integer.parseInt(registro);
					session.setAttribute("iRegistro", primerRegistro + "");
				} catch (NumberFormatException ne) {
				}
			else if ((registro = session.getAttribute("iRegistro") == null ? null : session.getAttribute("iRegistro").toString()) != null) {
				try {
					primerRegistro = Integer.parseInt(registro);
				} catch (NumberFormatException ne) {
				}
			}
			if ((registro = request.getParameter("nRegistrosP")) != null) {
				try {
					registrosPorPagina = Integer.parseInt(registro);
				} catch (NumberFormatException ne) {
				}
			}
			if ((!Constantes.SI.equals(request.getParameter("bPagina")))
					&& (!Constantes.SI.equals(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))) {
				primerRegistro = 1;
			}

			solicitudesOT.setDApellido1(((OCAPSolicitudesForm) form).getDApellido1());

			solicitudesOT.setDNombre(((OCAPSolicitudesForm) form).getDNombre());
			if (((OCAPSolicitudesForm) form).getCDni() != null)
				solicitudesOT.setCDni(((OCAPSolicitudesForm) form).getCDni().toUpperCase());
			if (((OCAPSolicitudesForm) form).getCDniReal() != null)
				solicitudesOT.setCDniReal(((OCAPSolicitudesForm) form).getCDniReal().toUpperCase());
			if ((((OCAPSolicitudesForm) form).getCProfesional_id() != null)
					&& (!((OCAPSolicitudesForm) form).getCProfesional_id().equals("")))
				solicitudesOT.setCProfesional_id(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesional_id()));
			else {
				solicitudesOT.setCProfesional_id(0L);
			}
			if ((((OCAPSolicitudesForm) form).getCEspec_id() != null) && (!((OCAPSolicitudesForm) form).getCEspec_id().equals("")))
				solicitudesOT.setCEspec_id(Long.parseLong(((OCAPSolicitudesForm) form).getCEspec_id()));
			else {
				solicitudesOT.setCEspec_id(0L);
			}
			if ((((OCAPSolicitudesForm) form).getCGrado_id() != null) && (!((OCAPSolicitudesForm) form).getCGrado_id().equals("")))
				solicitudesOT.setCGrado_id(Long.parseLong(((OCAPSolicitudesForm) form).getCGrado_id()));
			else {
				solicitudesOT.setCGrado_id(0L);
			}
			if ((((OCAPSolicitudesForm) form).getCConvocatoriaId() != null)
					&& (!((OCAPSolicitudesForm) form).getCConvocatoriaId().equals("")))
				solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPSolicitudesForm) form).getCConvocatoriaId()));
			else {
				solicitudesOT.setCConvocatoriaId(0L);
			}
			if ((((OCAPSolicitudesForm) form).getCGerencia_id() != null) && (!((OCAPSolicitudesForm) form).getCGerencia_id().equals("")))
				solicitudesOT.setCGerencia_id(Long.parseLong(((OCAPSolicitudesForm) form).getCGerencia_id()));
			else {
				solicitudesOT.setCGerencia_id(0L);
			}

			String opcion = request.getParameter("opcion") != null ? request.getParameter("opcion")
					: (String) session.getAttribute("opcion");
			request.setAttribute("opcion", opcion);

			solicitudesOT.setCTipo(opcion);
			if (opcion.equals("todo")) {
				solicitudesOT.setCFase(((OCAPSolicitudesForm) form).getCFase());
				solicitudesOT.setCEstado(((OCAPSolicitudesForm) form).getCEstado());
			} else if (opcion.equals("alega")) {
				solicitudesOT.setCFase(Constantes.FASE_VALIDACION);
				solicitudesOT.setCEstado(((OCAPSolicitudesForm) form).getCEstado());
			} else if (opcion.equals("competencia")) {
				solicitudesOT.setCFase(Constantes.FASE_FIN);
			} else {
				solicitudesOT.setCFase(Constantes.FASE_VALIDACION);
				solicitudesOT.setCEstado(((OCAPSolicitudesForm) form).getCEstado());
			}

			((OCAPSolicitudesForm) form).setCFase(solicitudesOT.getCFase());
			((OCAPSolicitudesForm) form).setCEstado(solicitudesOT.getCEstado());

			listadoSolicitudes = solicitudesLN.buscar(primerRegistro, registrosPorPagina, solicitudesOT, true, "", false);
			int numSolicitudes = solicitudesLN.contarSolicitudes(solicitudesOT, jcylUsuario, "", false);

			session.setAttribute("numSolicitudes", new Integer(numSolicitudes));

			Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
			pagina.setElementos(listadoSolicitudes);
			pagina.setNRegistros(((Integer) session.getAttribute("numSolicitudes")).intValue());
			pagina.setRegistroActual(primerRegistro);
			pagina.setRegistrosPorPagina(registrosPorPagina);

			request.setAttribute("listados", pagina);

			OCAPConfigApp.logger.info(getClass().getName() + " buscar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irBuscar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward cargarComboLocalidades(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionErrors errors = new ActionErrors();

		HttpSession session = request.getSession();
		Utilidades utilidades = new Utilidades();
		String salida = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboLocalidades: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPSolicitudesForm formulario = (OCAPSolicitudesForm) form;

			if ((formulario.getCProvinciaUsu_id() != null) && (!formulario.getCProvinciaUsu_id().equals(""))) {
				OCAPLocalidadesLN localidadesLN = new OCAPLocalidadesLN(jcylUsuario);
				Collection listadoLocal = localidadesLN.listarLocalidadesProvincia(formulario.getCProvinciaUsu_id());
				Object[] listadoLocalidades = listadoLocal.toArray();

				session.setAttribute(Constantes.COMBO_LOCALIDADES, utilidades.ArrayObjectToArrayList(listadoLocalidades));
			} else {
				session.setAttribute(Constantes.COMBO_LOCALIDADES, new ArrayList());
			}

			if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("alta"))) {
				salida = "irInsertar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("modif"))) {
				salida = "irModificar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("buscador"))) {
				((OCAPSolicitudesForm) form).setjspInicio(true);
				salida = "irBuscar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("solicitud"))) {
				if ((request.getParameter("aceptado") != null) && (request.getParameter("aceptado").equals(Constantes.SI_ESP))) {
					request.setAttribute("primeraSolic", Constantes.NO);
				}
				salida = "irSolicitud";
			}

			request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboLocalidades: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(salida);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward cargarComboCategorias(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoCategorias = new ArrayList();

		String salida = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboCategorias: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			if ((request.getParameter("actual") != null) && (Constantes.NO.equals(request.getParameter("actual")))) {
				if ((((OCAPSolicitudesForm) form).getCEstatutarioId() != null)
						&& (!"".equals(((OCAPSolicitudesForm) form).getCEstatutarioId()))) {
					listadoCategorias = categProfesionalesLN.consultaOCAPCategProfesionales(0L, null, 0L,
							Long.parseLong(((OCAPSolicitudesForm) form).getCEstatutarioId()), null, null, null, 0L, 0, 0);
				}
				session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
			} else {
				if ((((OCAPSolicitudesForm) form).getCEstatutarioActualId() != null)
						&& (!"".equals(((OCAPSolicitudesForm) form).getCEstatutarioActualId()))) {
					listadoCategorias = categProfesionalesLN.consultaOCAPCategProfesionales(0L, null, 0L,
							Long.parseLong(((OCAPSolicitudesForm) form).getCEstatutarioActualId()), null, null, null, 0L, 0, 0);
				}
				session.setAttribute(Constantes.COMBO_CATEGORIA_ACTUAL, listadoCategorias);
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD_ACTUAL, new ArrayList());
			}

			if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("alta"))) {
				salida = "irInsertar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("modif"))) {
				salida = "irModificar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("buscador"))) {
				((OCAPSolicitudesForm) form).setjspInicio(true);
				salida = "irBuscar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("solicitud"))) {
				if ((request.getParameter("aceptado") != null) && (request.getParameter("aceptado").equals(Constantes.SI_ESP))) {
					request.setAttribute("primeraSolic", Constantes.NO);
				}
				salida = "irSolicitud";
			}

			request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboCategorias: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(salida);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward cargarCombosEspecialidadesPuestos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoEspecialidades = new ArrayList();
		ArrayList listadoCategorias = new ArrayList();

		String salida = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarCombosEspecialidadesPuestos: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
			if ((request.getParameter("actual") != null) && (Constantes.NO.equals(request.getParameter("actual")))) {
				if ((((OCAPSolicitudesForm) form).getCProfesional_id() != null)
						&& (!"".equals(((OCAPSolicitudesForm) form).getCProfesional_id()))) {
					listadoEspecialidades = especialidadesLN
							.listarEspecialidades(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesional_id()));
					OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
					listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();
				}
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
			} else {
				if ((((OCAPSolicitudesForm) form).getCProfesionalActual_id() != null)
						&& (!"".equals(((OCAPSolicitudesForm) form).getCProfesionalActual_id()))) {
					listadoEspecialidades = especialidadesLN
							.listarEspecialidades(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesionalActual_id()));
					OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
					listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();
				}
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD_ACTUAL, listadoEspecialidades);
			}

			if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("irCSVDinamico"))) {
				if ((((OCAPSolicitudesForm) form).getCProfesional_id() != null)
						&& (!"".equals(((OCAPSolicitudesForm) form).getCProfesional_id()))) {
					listadoEspecialidades = especialidadesLN
							.listarEspecialidades(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesional_id()));
					OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
					listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();
				}
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
			}

			if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("alta"))) {
				salida = "irInsertar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("modif"))) {
				salida = "irModificar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("buscador"))) {
				((OCAPSolicitudesForm) form).setjspInicio(true);
				salida = "irBuscar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("buscadorB"))) {
				((OCAPSolicitudesForm) form).setjspInicio(true);
				salida = "irBuscarB";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("irCSVDinamico"))) {
				salida = "irCSVDinamico";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("solicitud"))) {
				if ((request.getParameter("aceptado") != null) && (request.getParameter("aceptado").equals(Constantes.SI_ESP))) {
					request.setAttribute("primeraSolic", Constantes.NO);
				}
				salida = "irSolicitud";
			}

			if ((request.getParameter("jspVuelta") != null) && (!request.getParameter("jspVuelta").equals("solicitud"))
					&& (jcylUsuario.getUser().getRol() != null)
					&& (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE))) {
				Map parametros = jcylUsuario.getParametrosUsuario();
				((OCAPSolicitudesForm) form).setCGerencia_id((String) parametros.get("PARAM_GERENCIA"));
				((OCAPSolicitudesForm) form).setCProvincia_id((String) parametros.get("PARAM_PROV"));
				((OCAPSolicitudesForm) form).setCTipogerencia_id((String) parametros.get("PARAM_TIPO_GERENCIA"));
			}

			request.setAttribute("opcion", request.getParameter("opcion"));
			request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " cargarCombosEspecialidadesPuestos: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(salida);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward cargarComboConvocatorias(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoConvocatorias = new ArrayList();

		String salida = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboConvocatorias: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			if ((((OCAPSolicitudesForm) form).getCGrado_id() != null) && (!"".equals(((OCAPSolicitudesForm) form).getCGrado_id()))) {
				listadoConvocatorias = convocatoriasLN
						.consultaOCAPConvocatoriasPorGradoId(Long.parseLong(((OCAPSolicitudesForm) form).getCGrado_id()));
			}
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);

			if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("alta"))) {
				salida = "irInsertar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("buscador"))) {
				((OCAPSolicitudesForm) form).setjspInicio(true);
				salida = "irBuscar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("irCSVDinamico"))) {
				salida = "irCSVDinamico";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("generador"))) {
				((OCAPSolicitudesForm) form).setjspInicio(true);
				salida = "irGenerarActas";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("buscadorGrs"))) {
				((OCAPSolicitudesForm) form).setjspInicio(true);
				salida = "irBuscarGrs";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("adminFinalizaMC"))) {
				salida = "irFinalizarEvaluacionFinPlazo";
			} else if ((request.getParameter("jspVuelta") != null)
					&& (request.getParameter("jspVuelta").equals("adminEliminaItinerario"))) {
				salida = "irEliminarItinerarioAsignadoUsuario";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("listarFase"))) {
				salida = "listarFase";
			}

			request.setAttribute("opcion", request.getParameter("opcion"));
			request.setAttribute("fase", request.getParameter("fase"));
			request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboConvocatorias: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(salida);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward cargarComboEstados(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoConvocatorias = new ArrayList();

		String salida = "";
		ArrayList listaEstados = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboEstados: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			if ((((OCAPSolicitudesForm) form).getCGrado_id() != null) && (!"".equals(((OCAPSolicitudesForm) form).getCGrado_id()))) {
				listadoConvocatorias = convocatoriasLN
						.consultaOCAPConvocatoriasPorGradoId(Long.parseLong(((OCAPSolicitudesForm) form).getCGrado_id()));
			}
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);

			if (request.getParameter("opcion").equals("todo")) {
				listaEstados = new ArrayList();
				if (request.getParameter("estado") != null) {
					request.setAttribute("estado", request.getParameter("estado"));
					if (Constantes.ESTADO_PENDIENTE_VALUE.equals(request.getParameter("estado")))
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_PENDIENTE, Constantes.ESTADO_PENDIENTE_VALUE));
					if (Constantes.ESTADO_ACEPTADO_VALUE.equals(request.getParameter("estado")))
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_ACEPTADO, Constantes.ESTADO_ACEPTADO_VALUE));
					if (Constantes.ESTADO_DESESTIMADO_VALUE.equals(request.getParameter("estado")))
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_DESESTIMADO, Constantes.ESTADO_DESESTIMADO_VALUE));
					if (Constantes.ESTADO_DENEGADO_VALUE.equals(request.getParameter("estado")))
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_DENEGADO, Constantes.ESTADO_DENEGADO_VALUE));
				} else if ((((OCAPSolicitudesForm) form).getCFase() != null) && (!"".equals(((OCAPSolicitudesForm) form).getCFase()))) {
					if (Constantes.FASE_INICIACION.equals(((OCAPSolicitudesForm) form).getCFase())) {
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_PENDIENTE, Constantes.ESTADO_PENDIENTE_VALUE));
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_ACEPTADO, Constantes.ESTADO_ACEPTADO_VALUE));
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_EXCLUIDO, Constantes.ESTADO_EXCLUIDO_VALUE));
					}

					if (Constantes.FASE_VALIDACION.equals(((OCAPSolicitudesForm) form).getCFase())) {
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_PENDIENTE, Constantes.ESTADO_PENDIENTE_VALUE));
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_ACEPTADO, Constantes.ESTADO_ACEPTADO_VALUE));
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_EXCLUIDO, Constantes.ESTADO_DESESTIMADO_VALUE));
					}
					if (Constantes.FASE_VALIDACION_CC.equals(((OCAPSolicitudesForm) form).getCFase())) {
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_PENDIENTE, Constantes.ESTADO_PENDIENTE_VALUE));
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_ACEPTADO, Constantes.ESTADO_ACEPTADO_VALUE));
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_EXCLUIDO, Constantes.ESTADO_DENEGADO_VALUE));
					}
				}

				session.setAttribute(Constantes.COMBO_ESTADOS, listaEstados);
			}

			request.setAttribute("opcion", request.getParameter("opcion"));

			if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("alta"))) {
				salida = "irInsertar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("buscador"))) {
				((OCAPSolicitudesForm) form).setjspInicio(true);
				salida = "irBuscar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("irCSVDinamico"))) {
				salida = "irCSVDinamico";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("buscadorGrs"))) {
				((OCAPSolicitudesForm) form).setjspInicio(true);
				salida = "irBuscarGrs";
			}

			request.setAttribute("opcion", request.getParameter("opcion"));
			request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboEstados: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(salida);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward cargarComboGerencias(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoGerencias = new ArrayList();

		String salida = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboGerencias: Inicio");
			validarAccion(mapping, form, request, response);

			if ((((OCAPSolicitudesForm) form).getCProvincia_id() != null) && (!((OCAPSolicitudesForm) form).getCProvincia_id().equals(""))
					&& (((OCAPSolicitudesForm) form).getCTipogerencia_id() != null)
					&& (!((OCAPSolicitudesForm) form).getCTipogerencia_id().equals(""))) {
				JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

				OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
				listadoGerencias = gerenciasLN.listarGerencias(((OCAPSolicitudesForm) form).getCProvincia_id(),
						((OCAPSolicitudesForm) form).getCTipogerencia_id());

				session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);
			} else {
				session.setAttribute(Constantes.COMBO_GERENCIA, new ArrayList());
			}
			session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO, new ArrayList());
			((OCAPSolicitudesForm) form).setACiudad("");

			if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("alta"))) {
				salida = "irInsertar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("modif"))) {
				salida = "irModificar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("solicitud"))) {
				if ((request.getParameter("aceptado") != null) && (request.getParameter("aceptado").equals(Constantes.SI_ESP))) {
					request.setAttribute("primeraSolic", Constantes.NO);
				}
				salida = "irSolicitud";
			}

			request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboGerencias: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(salida);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward cargarComboCentroTrabajo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoCentros = new ArrayList();

		String salida = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboCentroTrabajo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if ((((OCAPSolicitudesForm) form).getCGerencia_id() != null) && (!"".equals(((OCAPSolicitudesForm) form).getCGerencia_id()))) {
				OCAPCentroTrabajoLN centroTrabajoLN = new OCAPCentroTrabajoLN(jcylUsuario);
				listadoCentros = centroTrabajoLN.listarCentroTrabajo(String.valueOf(((OCAPSolicitudesForm) form).getCGerencia_id()), null);
				session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO, listadoCentros);
			} else {
				session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO, new ArrayList());
			}
			((OCAPSolicitudesForm) form).setACiudad("");

			request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);

			if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("alta"))) {
				salida = "irInsertar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("modif"))) {
				salida = "irModificar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("solicitud"))) {
				if ((request.getParameter("aceptado") != null) && (request.getParameter("aceptado").equals(Constantes.SI_ESP))) {
					request.setAttribute("primeraSolic", Constantes.NO);
				}
				salida = "irSolicitud";
			}

			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboCentroTrabajo: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(salida);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward cargarComboCiudad(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		String salida = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboCiudad: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPCentroTrabajoLN centroTrabajoLN = new OCAPCentroTrabajoLN(jcylUsuario);
			if ((((OCAPSolicitudesForm) form).getCCentrotrabajo_id() != null)
					&& (!"".equals(((OCAPSolicitudesForm) form).getCCentrotrabajo_id()))) {
				OCAPCentroTrabajoOT centroTrabajoOT = centroTrabajoLN
						.buscarOCAPCentroTrabajo(Long.parseLong(((OCAPSolicitudesForm) form).getCCentrotrabajo_id()));
				((OCAPSolicitudesForm) form).setACiudad(centroTrabajoOT.getACiudad());
			} else {
				((OCAPSolicitudesForm) form).setACiudad("");
			}
			request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);

			if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("alta"))) {
				salida = "irInsertar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("modif"))) {
				salida = "irModificar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("solicitud"))) {
				if ((request.getParameter("aceptado") != null) && (request.getParameter("aceptado").equals(Constantes.SI_ESP))) {
					request.setAttribute("primeraSolic", Constantes.NO);
				}
				salida = "irSolicitud";
			}

			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboCiudad: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(salida);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward obtenerAniosGrado(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " obtenerAniosGrado: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			OCAPGradoOT gradoOT = new OCAPGradoOT();
			if ((((OCAPSolicitudesForm) form).getCGrado_id() != null) && (!"".equals(((OCAPSolicitudesForm) form).getCGrado_id()))) {
				gradoOT = gradoLN.buscarOCAPGrado(Long.parseLong(((OCAPSolicitudesForm) form).getCGrado_id()));
				((OCAPSolicitudesForm) form).setNAniosejercicio(String.valueOf(gradoOT.getNAniosejercicio()));
			}

			OCAPConfigApp.logger.info(getClass().getName() + " obtenerAniosGrado: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irInsertar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward generarPDF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		ArrayList listadoSolicitudes = new ArrayList();
		String pathBase = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarPDF: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			solicitudesOT.setDApellido1(((OCAPSolicitudesForm) form).getDApellido1());

			solicitudesOT.setDNombre(((OCAPSolicitudesForm) form).getDNombre());
			solicitudesOT.setCDni(((OCAPSolicitudesForm) form).getCDni());
			solicitudesOT.setCDniReal(((OCAPSolicitudesForm) form).getCDniReal());
			solicitudesOT.setBSexo(((OCAPSolicitudesForm) form).getBSexo());

			solicitudesOT.setDSolicitud(((OCAPSolicitudesForm) form).getDSolicitud());

			String aux = request.getParameter("jspAccion");
			if ((aux != null) && (aux.equals("OCAPSolicitudesDt"))) {
				solicitudesOT.setCExp_id(Long.parseLong(((OCAPSolicitudesForm) form).getCExp_id()));
				solicitudesOT.setDNombre(((OCAPSolicitudesForm) form).getDNombre());
				solicitudesOT.setDApellido1(((OCAPSolicitudesForm) form).getDApellido1());

				solicitudesOT.setCDni(((OCAPSolicitudesForm) form).getCDni());
				solicitudesOT.setCDniReal(((OCAPSolicitudesForm) form).getCDniReal());
				solicitudesOT.setDCorreoelectronico(((OCAPSolicitudesForm) form).getDCorreoelectronico());

				solicitudesOT.setDProcedimiento(((OCAPSolicitudesForm) form).getDProcedimiento());
				solicitudesOT.setDEstatut_nombre(((OCAPSolicitudesForm) form).getDEstatutario_nombre());
				solicitudesOT.setDProfesional_nombre(((OCAPSolicitudesForm) form).getDProfesional_nombre());
				solicitudesOT.setDEspec_nombre(((OCAPSolicitudesForm) form).getDEspec_nombre());
				solicitudesOT.setDProvincia(((OCAPSolicitudesForm) form).getDProvincia());
				solicitudesOT.setDTipogerencia_desc(((OCAPSolicitudesForm) form).getDTipogerencia_desc());
				solicitudesOT.setDGerencia_nombre(((OCAPSolicitudesForm) form).getDGerencia_nombre());
				solicitudesOT.setDCentrotrabajo_nombre(((OCAPSolicitudesForm) form).getDCentrotrabajo_nombre());
				solicitudesOT.setDGrado_des(((OCAPSolicitudesForm) form).getDGrado_des());
				solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPSolicitudesForm) form).getCConvocatoriaId()));
				solicitudesOT.setFRegistro_solic(((OCAPSolicitudesForm) form).getFRegistro_solic());

				solicitudesOT.setDObserv_neg_solic(((OCAPSolicitudesForm) form).getDObserv_neg_solic());
				solicitudesOT.setDObserv_neg_tmc(((OCAPSolicitudesForm) form).getDObserv_neg_solic());
				solicitudesOT.setDObserv_neg_mc(((OCAPSolicitudesForm) form).getDObserv_neg_solic());
				solicitudesOT.setCExp_id(Long.parseLong(((OCAPSolicitudesForm) form).getCExp_id()));

				solicitudesOT.setDMotivo_neg(((OCAPSolicitudesForm) form).getDMotivo_neg());
				solicitudesOT.setDMotivo_neg_tmc(((OCAPSolicitudesForm) form).getDMotivo_neg());
				solicitudesOT.setDMotivo_neg_mc(((OCAPSolicitudesForm) form).getDMotivo_neg());

				OCAPExpedienteCarreraLN expLN = new OCAPExpedienteCarreraLN(jcylUsuario);
				OCAPExpedientecarreraOT expCarreraOT = new OCAPExpedientecarreraOT();
				expCarreraOT.setCExpId(new Long(solicitudesOT.getCExp_id()));
				expCarreraOT = expLN.buscarOCAPExpedienteCarrera(expCarreraOT);

				solicitudesOT.setCEstatutId(expCarreraOT.getCEstatutId());
				solicitudesOT.setCGrado_id(expCarreraOT.getCGradoId().longValue());
				solicitudesOT.setCProfesional_id(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesional_id()));

				if (expCarreraOT.getFAceptacSolic() != null)
					solicitudesOT.setFAceptac_solic(expCarreraOT.getFAceptacSolic());
				if (expCarreraOT.getFInicioMc() != null)
					solicitudesOT.setFInicio_mc(expCarreraOT.getFInicioMc());
				if (expCarreraOT.getFFinMc() != null)
					solicitudesOT.setFFin_mc(expCarreraOT.getFFinMc());
				if (expCarreraOT.getFNegacionSolic() != null)
					solicitudesOT.setFNegacion_solic(expCarreraOT.getFNegacionSolic());
				if (expCarreraOT.getFNegacionMC() != null)
					solicitudesOT.setFNegacion_mc(expCarreraOT.getFNegacionMC());
				if (expCarreraOT.getFRespuestaInconf_MC() != null)
					solicitudesOT.setFRespuestaInconf_MC(expCarreraOT.getFRespuestaInconf_MC());
				if (expCarreraOT.getFAceptacionceis() != null) {
					solicitudesOT.setFAceptacionceis(expCarreraOT.getFAceptacionceis());
				}

				solicitudesOT.setDObserv_neg_solic(expCarreraOT.getDObservNeg_solic());
				if ((expCarreraOT.getDRespuestaInconfSolic() != null) && (!expCarreraOT.getDRespuestaInconfSolic().equals(""))) {
					solicitudesOT.setDRespuestaInconfSolic(expCarreraOT.getDRespuestaInconfSolic());
				}
				solicitudesOT.setDObserv_neg_mc(expCarreraOT.getDObservNegMC());
				solicitudesOT.setDMotivo_neg(expCarreraOT.getDMotivoNeg());
				solicitudesOT.setDMotivo_neg_mc(expCarreraOT.getDMotivoNegMC());
				solicitudesOT.setDMotivoNegRespuesta_MC(expCarreraOT.getDMotivoNegRespuesta_MC());
				solicitudesOT.setDRespuestaInconfSolic(expCarreraOT.getDRespuestaInconfSolic());
				solicitudesOT.setDRespuestaInconfMC(expCarreraOT.getDRespuestaInconfMC());
			} else if ((aux != null) && (aux.equals("OCAPSolicitudesLs"))) {
				if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
						&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
					form = (OCAPSolicitudesForm) request.getSession().getAttribute("OCAPSolicitudesFormBuscador");
					request.setAttribute("OCAPSolicitudesForm", form);
				} else {
					request.getSession().setAttribute("OCAPSolicitudesFormBuscador", form);
					session.setAttribute("iRegistro", 1 + "");
				}

				solicitudesOT.setCProcedimientoId(((OCAPSolicitudesForm) form).getCProcedimientoId());

				if ((((OCAPSolicitudesForm) form).getCEstatutarioId() != null)
						&& (!((OCAPSolicitudesForm) form).getCEstatutarioId().equals("")))
					solicitudesOT.setCEstatutId(Long.parseLong(((OCAPSolicitudesForm) form).getCEstatutarioId()));
				else {
					solicitudesOT.setCEstatutId(0L);
				}

				if ((((OCAPSolicitudesForm) form).getCProfesional_id() != null)
						&& (!((OCAPSolicitudesForm) form).getCProfesional_id().equals("")))
					solicitudesOT.setCProfesional_id(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesional_id()));
				else if (((OCAPSolicitudesForm) form).getCProfesional_id().equals("")) {
					solicitudesOT.setCProfesional_id(0L);
				}
				if ((((OCAPSolicitudesForm) form).getCEspec_id() != null) && (!((OCAPSolicitudesForm) form).getCEspec_id().equals("")))
					solicitudesOT.setCEspec_id(Long.parseLong(((OCAPSolicitudesForm) form).getCEspec_id()));
				else if (((OCAPSolicitudesForm) form).getCEspec_id().equals("")) {
					solicitudesOT.setCEspec_id(0L);
				}
				if ((((OCAPSolicitudesForm) form).getCGrado_id() != null) && (!((OCAPSolicitudesForm) form).getCGrado_id().equals("")))
					solicitudesOT.setCGrado_id(Long.parseLong(((OCAPSolicitudesForm) form).getCGrado_id()));
				else if (((OCAPSolicitudesForm) form).getCGrado_id().equals("")) {
					solicitudesOT.setCGrado_id(0L);
				}
				if ((((OCAPSolicitudesForm) form).getCConvocatoriaId() != null)
						&& (!((OCAPSolicitudesForm) form).getCConvocatoriaId().equals("")))
					solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPSolicitudesForm) form).getCConvocatoriaId()));
				else if (((OCAPSolicitudesForm) form).getCConvocatoriaId().equals("")) {
					solicitudesOT.setCConvocatoriaId(0L);
				}
				if ((((OCAPSolicitudesForm) form).getCFase() != null) && (!((OCAPSolicitudesForm) form).getCFase().equals(""))) {
					solicitudesOT.setCFase(((OCAPSolicitudesForm) form).getCFase());
				}
				if ((((OCAPSolicitudesForm) form).getCEstado() != null) && (!((OCAPSolicitudesForm) form).getCEstado().equals(""))) {
					solicitudesOT.setCEstado(((OCAPSolicitudesForm) form).getCEstado());
				}
				if (Constantes.FASE_INICIACION.equals(((OCAPSolicitudesForm) form).getCFase()))
					listadoSolicitudes = solicitudesLN.buscarPDF(0, 0, solicitudesOT);
				else
					listadoSolicitudes = solicitudesLN.buscar(0, 0, solicitudesOT, true, "", false);
			} else if ((aux != null) && (aux.equals("OCAPSolicitudesAl"))) {
				solicitudesOT.setCExp_id(Long.parseLong(((OCAPSolicitudesForm) form).getCExp_id()));
				solicitudesOT.setBSexo(((OCAPSolicitudesForm) form).getBSexo());
				solicitudesOT.setDCorreoelectronico(((OCAPSolicitudesForm) form).getDCorreoelectronico());
				solicitudesOT.setDProcedimiento(((OCAPSolicitudesForm) form).getDProcedimiento());

				solicitudesOT.setDEstatut_nombre(((OCAPSolicitudesForm) form).getDEstatutario_nombre());
				solicitudesOT.setDProfesional_nombre(((OCAPSolicitudesForm) form).getDProfesional_nombre());
				solicitudesOT.setDEspec_nombre(((OCAPSolicitudesForm) form).getDEspec_nombre());
				solicitudesOT.setDProvincia(((OCAPSolicitudesForm) form).getDProvincia());
				solicitudesOT.setDTipogerencia_desc(((OCAPSolicitudesForm) form).getDTipogerencia_desc());
				solicitudesOT.setDGerencia_nombre(((OCAPSolicitudesForm) form).getDGerencia_nombre());
				solicitudesOT.setDCentrotrabajo_nombre(((OCAPSolicitudesForm) form).getDCentrotrabajo_nombre());
				solicitudesOT.setDGrado_des(((OCAPSolicitudesForm) form).getDGrado_des());

				if ((((OCAPSolicitudesForm) form).getNTelefono1() != null) && (!((OCAPSolicitudesForm) form).getNTelefono1().equals("")))
					solicitudesOT.setNTelefono1(((OCAPSolicitudesForm) form).getNTelefono1());
				else {
					solicitudesOT.setNTelefono1("-");
				}

				if ((((OCAPSolicitudesForm) form).getCGrado_id() != null) && (!((OCAPSolicitudesForm) form).getCGrado_id().equals("")))
					solicitudesOT.setCGrado_id(Long.parseLong(((OCAPSolicitudesForm) form).getCGrado_id()));
				else if (((OCAPSolicitudesForm) form).getCGrado_id().equals("")) {
					solicitudesOT.setCGrado_id(0L);
				}

				solicitudesOT.setFRegistro_solic(((OCAPSolicitudesForm) form).getFRegistro_solic());
				solicitudesOT.setNAniosantiguedad(Long.parseLong(((OCAPSolicitudesForm) form).getNAniosantiguedad()));
			}

			if ((((OCAPSolicitudesForm) form).getDEspec_nombre() != null) && (!((OCAPSolicitudesForm) form).getDEspec_nombre().equals("")))
				solicitudesOT.setDEspec_nombre(((OCAPSolicitudesForm) form).getDEspec_nombre());
			else if ((((OCAPSolicitudesForm) form).getDEspec_nombre() != null)
					&& (((OCAPSolicitudesForm) form).getDEspec_nombre().equals(""))) {
				solicitudesOT.setDEspec_nombre("-");
			}
			if ((((OCAPSolicitudesForm) form).getDProvincia() != null) && (!((OCAPSolicitudesForm) form).getDProvincia().equals(""))) {
				solicitudesOT.setDProvincia(Cadenas.capitalizar(((OCAPSolicitudesForm) form).getDProvinciaCarrera()));
			} else if ((((OCAPSolicitudesForm) form).getDProvincia() != null)
					&& (((OCAPSolicitudesForm) form).getDProvincia().equals(""))) {
				solicitudesOT.setDProvincia("-");
			}
			if ((((OCAPSolicitudesForm) form).getDTipogerencia_desc() != null)
					&& (!((OCAPSolicitudesForm) form).getDTipogerencia_desc().equals("")))
				solicitudesOT.setDTipogerencia_desc(((OCAPSolicitudesForm) form).getDTipogerencia_desc());
			else if ((((OCAPSolicitudesForm) form).getDTipogerencia_desc() != null)
					&& (((OCAPSolicitudesForm) form).getDTipogerencia_desc().equals(""))) {
				solicitudesOT.setDTipogerencia_desc("-");
			}
			if ((((OCAPSolicitudesForm) form).getDGerencia_nombre() != null)
					&& (!((OCAPSolicitudesForm) form).getDGerencia_nombre().equals("")))
				solicitudesOT.setDGerencia_nombre(((OCAPSolicitudesForm) form).getDGerencia_nombre());
			else if ((((OCAPSolicitudesForm) form).getDGerencia_nombre() != null)
					&& (((OCAPSolicitudesForm) form).getDGerencia_nombre().equals(""))) {
				solicitudesOT.setDGerencia_nombre("-");
			}
			if ((((OCAPSolicitudesForm) form).getDCentrotrabajo_nombre() != null)
					&& (!((OCAPSolicitudesForm) form).getDCentrotrabajo_nombre().equals("")))
				solicitudesOT.setDCentrotrabajo_nombre(((OCAPSolicitudesForm) form).getDCentrotrabajo_nombre());
			else if ((((OCAPSolicitudesForm) form).getDCentrotrabajo_nombre() != null)
					&& (((OCAPSolicitudesForm) form).getDCentrotrabajo_nombre().equals(""))) {
				solicitudesOT.setDCentrotrabajo_nombre("-");
			}

			solicitudesOT.setCSitAdministrativaAuxId(((OCAPSolicitudesForm) form).getCSitAdministrativaAuxId());
			if ("6".equals(solicitudesOT.getCSitAdministrativaAuxId())) {
				solicitudesOT.setDSitAdministrativaAuxOtros(((OCAPSolicitudesForm) form).getDSitAdministrativaOtros());
				solicitudesOT.setDSitAdministrativaAux_nombre("Otras: "
						+ (solicitudesOT.getDSitAdministrativaAuxOtros() == null ? "" : solicitudesOT.getDSitAdministrativaAuxOtros()));
			} else {
				solicitudesOT.setDSitAdministrativaAux_nombre("Activo");
			}

			pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");

			if ((aux != null) && (aux.equals("OCAPSolicitudesLs"))) {
				Map parametros = jcylUsuario.getParametrosUsuario();
				OCAPGerenciasLN gerenciaLN = new OCAPGerenciasLN(jcylUsuario);
				String gerenciaNombre = gerenciaLN.buscarOCAPGerencias(Long.valueOf((String) parametros.get("PARAM_GERENCIA")).longValue())
						.getDNombre();

				if ((request.getParameter("CSV") != null) && (Constantes.SI.equals(request.getParameter("CSV"))))
					solicitudesLN.crearCSVListado(response, listadoSolicitudes, pathBase, ((OCAPSolicitudesForm) form).getCEstado(),
							((OCAPSolicitudesForm) form).getCFase(), gerenciaNombre, jcylUsuario);
				else
					solicitudesLN.crearInformeListado(response, listadoSolicitudes, pathBase, ((OCAPSolicitudesForm) form).getCEstado(),
							((OCAPSolicitudesForm) form).getCFase(), gerenciaNombre, "", jcylUsuario, null, solicitudesOT.getCConvocatoriaId());
			} else if ((aux != null) && (aux.equals("OCAPSolicitudesDt"))) {
				if ((request.getParameter("fase") != null) && (Constantes.FASE_MC.equals(request.getParameter("fase")))) {
					OCAPUsuariosOT usuariosOT = new OCAPUsuariosOT();

					OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);

					usuariosOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDniReal(), solicitudesOT.getCExp_id(), jcylUsuario);

					ArrayList listaMeritos = solicitudesLN.buscarOCAPMeritosPorCategProfesional(usuariosOT.getCProfesionalId(),
							usuariosOT.getCGrado_id(), jcylUsuario);

					OCAPCreditosOT defCredOT = null;

					StringBuffer texto = new StringBuffer(40);
					int contador = 0;

					for (int j = 0; j < listaMeritos.size(); j++) {
						defCredOT = (OCAPCreditosOT) listaMeritos.get(j);

						if (Constantes.DEF_MERITO_FORMACION.equals(defCredOT.getCDefmeritoId() + "")) {
							texto.append(defCredOT.getDMerito());
							contador++;
						}
					}

					OCAPDefMeritoscurricularesOT defMCOT = null;
					OCAPMeritoscurricularesOT mcOTaux = null;
					OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
					OCAPDefMeritoscurricularesLN defMCLN = new OCAPDefMeritoscurricularesLN(jcylUsuario);

					ArrayList listaDefMeritos = defMCLN.listadoOCAPDefMeritoscurriculares();

					for (int j = 0; j < listaDefMeritos.size(); j++) {
						defMCOT = (OCAPDefMeritoscurricularesOT) listaDefMeritos.get(j);

						if ((!Constantes.DEF_MERITO_FORMACION.equals(defMCOT.getCDefmeritoId() + "")) && (!Constantes.DEF_MERITO_OPCIONALES.equals(defMCOT.getCDefmeritoId() + ""))) {
							mcOTaux = mcLN.buscarOCAPMeritoscurricularesPorUsuarioOT(usuariosOT, new Long(defMCOT.getCDefmeritoId()),
									Constantes.SI, jcylUsuario, false);
							listaMeritos = mcOTaux.getListaMeritosUsuario();

							if (listaMeritos.size() != 0) {
								if (contador != 0)
									texto.append(", ").append(defMCOT.getDNombre());
								else {
									texto.append(defMCOT.getDNombre());
								}

								contador++;
							}
						}
					}

					solicitudesOT.setDMensajeInforme(texto.toString());

					solicitudesOT.setFInicio_mc_str(DateUtil.convertDateToString(solicitudesOT.getFInicio_mc()));
					solicitudesOT.setFFin_mc_str(DateUtil.convertDateToString(solicitudesOT.getFFin_mc()));

					OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
					OCAPConvocatoriasOT convocatoriasOT = new OCAPConvocatoriasOT();
					convocatoriasOT = convocatoriasLN.buscarOCAPConvocatorias(solicitudesOT.getCConvocatoriaId());

					solicitudesOT.setDConvocatoria_nombre(convocatoriasOT.getDNombre());

					solicitudesLN.crearInformeAceptacion(response, solicitudesOT, pathBase, true);
				} else if ((request.getParameter("fase") != null) && (Constantes.FASE_INICIACION.equals(request.getParameter("fase")))) {
					solicitudesOT.setFRegistro_oficial(((OCAPSolicitudesForm) form).getFRegistro_oficial().substring(0, 10));
					solicitudesOT.setBSexo(((OCAPSolicitudesForm) form).getBSexo());
					solicitudesOT.setDDirector(((OCAPSolicitudesForm) form).getDDirector());
					solicitudesLN.crearPDFSubsanacion(response, solicitudesOT, pathBase);
				} else {
					if ((((OCAPSolicitudesForm) form).getDDirector() == null)
							|| ("".equals(((OCAPSolicitudesForm) form).getDDirector().trim())))
						solicitudesOT.setDDirector("D./Dña. ...............................................");
					else
						solicitudesOT.setDDirector(((OCAPSolicitudesForm) form).getDDirector());
					if ((((OCAPSolicitudesForm) form).getDGerente() == null)
							|| ("".equals(((OCAPSolicitudesForm) form).getDGerente().trim())))
						solicitudesOT.setDGerente("D./Dña. ...............................................");
					else {
						solicitudesOT.setDGerente(((OCAPSolicitudesForm) form).getDGerente());
					}
					solicitudesLN.guardarFechaInforme(solicitudesOT, request.getParameter("fase"), request.getParameter("denegacion"));
					solicitudesLN.crearInformeDenegacion(response, solicitudesOT, pathBase, true, request.getParameter("fase"),
							request.getParameter("denegacion"), jcylUsuario);
				}

				request.setAttribute("rutaVuelta", "OCAPSolicitudes.do?accion=irDetalle&opcion=" + request.getParameter("opcion")
						+ "&CExp_id=" + ((OCAPSolicitudesForm) form).getCExp_id());
			} else if ((aux != null) && (aux.equals("OCAPSolicitudesAl"))) {
				solicitudesLN.crearInformeDetalle(response, solicitudesOT, pathBase);
			}

			OCAPConfigApp.logger.info(getClass().getName() + " generarPDF: Fin");
		} catch (SendFailedException e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.envioEmail")));
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mensajeExito");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward anhadirVocalGeneracionActas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoCategorias = new ArrayList();

		String salida = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboCategorias: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			if ((request.getParameter("actual") != null) && (Constantes.NO.equals(request.getParameter("actual")))) {
				if ((((OCAPSolicitudesForm) form).getCEstatutarioId() != null)
						&& (!"".equals(((OCAPSolicitudesForm) form).getCEstatutarioId()))) {
					listadoCategorias = categProfesionalesLN.consultaOCAPCategProfesionales(0L, null, 0L,
							Long.parseLong(((OCAPSolicitudesForm) form).getCEstatutarioId()), null, null, null, 0L, 0, 0);
				}
				session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
			} else {
				if ((((OCAPSolicitudesForm) form).getCEstatutarioActualId() != null)
						&& (!"".equals(((OCAPSolicitudesForm) form).getCEstatutarioActualId()))) {
					listadoCategorias = categProfesionalesLN.consultaOCAPCategProfesionales(0L, null, 0L,
							Long.parseLong(((OCAPSolicitudesForm) form).getCEstatutarioActualId()), null, null, null, 0L, 0, 0);
				}
				session.setAttribute(Constantes.COMBO_CATEGORIA_ACTUAL, listadoCategorias);
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD_ACTUAL, new ArrayList());
			}

			if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("alta"))) {
				salida = "irInsertar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("modif"))) {
				salida = "irModificar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("buscador"))) {
				((OCAPSolicitudesForm) form).setjspInicio(true);
				salida = "irBuscar";
			} else if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("solicitud"))) {
				if ((request.getParameter("aceptado") != null) && (request.getParameter("aceptado").equals(Constantes.SI_ESP))) {
					request.setAttribute("primeraSolic", Constantes.NO);
				}
				salida = "irSolicitud";
			}

			request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboCategorias: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(salida);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward generarPDFActas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		String pathBase = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarPDFActas: Inicio");
			validarAccion(mapping, form, request, response);
			OCAPSolicitudesForm solicitudesForm = (OCAPSolicitudesForm) form;

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();
			solicitudesOT.setCGrado_id(
					Cadenas.EsVacia(solicitudesForm.getCGrado_id()) ? 0L : Long.valueOf(solicitudesForm.getCGrado_id()).longValue());
			solicitudesOT.setCConvocatoriaId(Cadenas.EsVacia(solicitudesForm.getCConvocatoriaId()) ? 0L
					: Long.valueOf(solicitudesForm.getCConvocatoriaId()).longValue());
			solicitudesOT.setCProfesional_id(Cadenas.EsVacia(solicitudesForm.getCProfesional_id()) ? 0L
					: Long.valueOf(solicitudesForm.getCProfesional_id()).longValue());
			solicitudesOT.setCGerencia_id(
					Cadenas.EsVacia(solicitudesForm.getCGerencia_id()) ? 0L : Long.valueOf(solicitudesForm.getCGerencia_id()).longValue());
			solicitudesOT.setDNombreApellidosPresi1(solicitudesForm.getDNombreApellidosPresi1());
			solicitudesOT.setDNombreApellidosPresi2(solicitudesForm.getDNombreApellidosPresi2());

			solicitudesOT.setDNombreApellidosSecre1(solicitudesForm.getDNombreApellidosSecre1());
			solicitudesOT.setDNombreApellidosSecre2(solicitudesForm.getDNombreApellidosSecre2());
			solicitudesOT.setBSexoPresi1(solicitudesForm.getBSexoPresi1());
			solicitudesOT.setBSexoPresi2(solicitudesForm.getBSexoPresi2());

			solicitudesOT.setBSexoSecre1(solicitudesForm.getBSexoSecre1());
			solicitudesOT.setBSexoSecre2(solicitudesForm.getBSexoSecre2());
			solicitudesOT.setBEnCalidadPresi1(solicitudesForm.getBEnCalidadPresi1());
			solicitudesOT.setBEnCalidadPresi2(solicitudesForm.getBEnCalidadPresi2());

			solicitudesOT.setBEnCalidadSecre1(solicitudesForm.getBEnCalidadSecre1());
			solicitudesOT.setBEnCalidadSecre2(solicitudesForm.getBEnCalidadSecre2());
			solicitudesOT.setDMiembrosAusentes(solicitudesForm.getDMiembrosAusentes());
			solicitudesOT.setDAsuntosTramite(solicitudesForm.getDAsuntosTramite());
			solicitudesOT.setDRuegosPreguntas(solicitudesForm.getDRuegosPreguntas());
			solicitudesOT.setFSesion(DateUtil.convertStringToDate(solicitudesForm.getFInicio()));
			solicitudesOT.setNHoraInicio(solicitudesForm.getNHoraInicio());
			solicitudesOT.setNMinutosInicio(solicitudesForm.getNMinutosInicio());
			solicitudesOT.setNHoraFin(solicitudesForm.getNHoraFin());
			solicitudesOT.setNMinutosFin(solicitudesForm.getNMinutosFin());

			ArrayList cadenasVocales = new ArrayList();
			if ((((OCAPSolicitudesForm) form).getResumenVocales() != null)
					&& (!"".equals(((OCAPSolicitudesForm) form).getResumenVocales()))) {
				cadenasVocales = Cadenas.obtenerArrayListTokens(((OCAPSolicitudesForm) form).getResumenVocales(), "#");
			}
			OCAPAsistenteOT vocalOT = null;
			ArrayList listadoVocales = new ArrayList();
			ArrayList listadoVocalesSuplentes = new ArrayList();
			for (int i = 0; i < cadenasVocales.size(); i++) {
				ArrayList campos = new ArrayList();
				String cadena = (String) cadenasVocales.get(i);
				StringTokenizer token = new StringTokenizer(cadena, "$");

				while (token.hasMoreTokens()) {
					campos.add(token.nextToken());
				}
				vocalOT = new OCAPAsistenteOT();
				vocalOT.setDNombreApellidos((String) campos.get(0));
				vocalOT.setBSexo((String) campos.get(1));
				vocalOT.setBEnCalidad((String) campos.get(2));
				if ("T".equals(vocalOT.getBEnCalidad()))
					listadoVocales.add(vocalOT);
				else {
					listadoVocalesSuplentes.add(vocalOT);
				}
			}

			pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");

			OCAPConfigApp.logger.info(getClass().getName() + " generarPDFActas: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mensajeExito");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward ayuda(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " ayuda: Inicio");
			validarAccion(mapping, form, request, response);

			OCAPConfigApp.logger.info(getClass().getName() + " ayuda: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("ayuda");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ArrayList obtenerFase(OCAPSolicitudesOT componente, OCAPConvocatoriasOT convoOT) throws Exception {
		ArrayList listado = null;
		try {
			listado = new ArrayList();
			OCAPSolicitudesOT datos = new OCAPSolicitudesOT();

			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_COMPLETA_DATEUTIL);
			SimpleDateFormat dfFecha = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);

			Date dHoy = new Date();

			datos = new OCAPSolicitudesOT();
			datos.setDFase(Constantes.SOLIC_REGISTRADA);
			datos.setFFechaRegistro(componente.getFRegistro_oficial());
			listado.add(datos);

			datos = new OCAPSolicitudesOT();
			datos.setDFase(Constantes.SOLIC_ALTA);
			datos.setFFechaRegistro(componente.getFRegistro_solic());
			listado.add(datos);

			if (componente.getFNegacion_solic() != null) {
				datos = new OCAPSolicitudesOT();
				datos.setDFase(Constantes.SOLIC_A_SUBSANAR);
				datos.setFFechaRegistro(df.format(componente.getFNegacion_solic()));
				listado.add(datos);

				if (componente.getFSubsanacion() != null) {
					datos = new OCAPSolicitudesOT();
					datos.setDFase(Constantes.SOLIC_SUBSANACION);
					datos.setFFechaRegistro(df.format(componente.getFSubsanacion()));
					listado.add(datos);
				}

				if ((convoOT.getFAlegsolicitud() != null) && (DateUtil.convertStringToDate(convoOT.getFAlegsolicitud()).before(new Date()))
						&& ((componente.getFAceptac_solic() == null)
								|| (componente.getFAceptac_solic().before(DateUtil.convertStringToDate(convoOT.getFAlegsolicitud()))))) {
					datos = new OCAPSolicitudesOT();
					datos.setDFase(Constantes.SOLIC_DESESTIMADA);
					datos.setFFechaRegistro(convoOT.getFAlegsolicitud());
					listado.add(datos);
				}

				if (componente.getFInconf_solic() != null) {
					datos = new OCAPSolicitudesOT();
					datos.setDFase(Constantes.SOLIC_INCONF_REGIS);
					datos.setFFechaRegistro(df.format(componente.getFInconf_solic()));

					listado.add(datos);

					if (componente.getFRespuesta_inconf_solic() != null) {
						datos = new OCAPSolicitudesOT();
						datos.setDFase(Constantes.SOLIC_INCONF_DESE);
						datos.setFFechaRegistro(df.format(componente.getFRespuesta_inconf_solic()));

						listado.add(datos);
					}
				}
			}

			if (componente.getFAceptac_solic() != null) {
				datos = new OCAPSolicitudesOT();
				datos.setDFase("Aceptación de Solicitud");
				datos.setFFechaRegistro(df.format(componente.getFAceptac_solic()));
				listado.add(datos);
			}

			if (componente.getFInicio_mc() != null) {
				datos = new OCAPSolicitudesOT();
				datos.setDFase("Inicio Autoevaluación Méritos");
				datos.setFFechaRegistro(df.format(componente.getFInicio_mc()));
				listado.add(datos);
			}

			if ((componente.getFFin_mc() != null) && (!dHoy.before(componente.getFFin_mc()))) {
				datos = new OCAPSolicitudesOT();
				datos.setDFase("Fin Autoevaluación Méritos");
				datos.setFFechaRegistro(df.format(componente.getFFin_mc()));
				listado.add(datos);

				if (componente.getFAclaraciones() != null) {
					datos = new OCAPSolicitudesOT();
					datos.setDFase("Solicitud de Aclaraciones de Méritos");
					datos.setFFechaRegistro(df.format(componente.getFAclaraciones()));
					listado.add(datos);
				}

				if (componente.getFDesistidoMC() != null) {
					datos = new OCAPSolicitudesOT();
					datos.setDFase("Aclaraciones de Méritos No Presentadas: Desistido");
					datos.setFFechaRegistro(df.format(componente.getFDesistidoMC()));
					listado.add(datos);
				}

				if (componente.getFNegacion_mc() != null) {
					datos = new OCAPSolicitudesOT();
					datos.setDFase("Desestimación de Méritos Curriculares");
					datos.setFFechaRegistro(df.format(componente.getFNegacion_mc()));
					listado.add(datos);

					if (componente.getFMotivadoNeg() != null) {
						datos = new OCAPSolicitudesOT();
						datos.setDFase("Generado Informe Motivado");
						datos.setFFechaRegistro(df.format(componente.getFMotivadoNeg()));
						listado.add(datos);
					}

					if (componente.getFInconf_mc() != null) {
						datos = new OCAPSolicitudesOT();
						datos.setDFase("Registro Alegación de Méritos");
						datos.setFFechaRegistro(df.format(componente.getFInconf_mc()));
						listado.add(datos);
					}
				}

				if (componente.getFAceptacionceis() != null) {
					datos = new OCAPSolicitudesOT();
					datos.setDFase("Aceptación de Méritos Curriculares - CEIS");
					datos.setFFechaRegistro(df.format(componente.getFAceptacionceis()));
					listado.add(datos);
				}
				if (componente.getFMotivadoAcep() != null) {
					datos = new OCAPSolicitudesOT();
					datos.setDFase("Generado Informe Motivado");
					datos.setFFechaRegistro(df.format(componente.getFMotivadoAcep()));
					listado.add(datos);
				}

				if (componente.getFRespuestaInconf_MC() != null) {
					datos = new OCAPSolicitudesOT();
					datos.setDFase("Inadmisión de Méritos Curriculares - CC");
					datos.setFFechaRegistro(df.format(componente.getFRespuestaInconf_MC()));
					listado.add(datos);
				}

				if (componente.getFInicio_ca() != null) {
					datos = new OCAPSolicitudesOT();
					datos.setDFase("Inicio evalu@net: Desempeño Competencia");
					datos.setFFechaRegistro(df.format(componente.getFInicio_ca()));
					listado.add(datos);
				}

				if ((componente.getFFin_ca() != null) && (!dHoy.before(componente.getFFin_ca()))) {
					datos = new OCAPSolicitudesOT();
					datos.setDFase("Fin evalu@net: Desempeño Competencia");
					datos.setFFechaRegistro(df.format(componente.getFFin_ca()));
					listado.add(datos);
				}
			}
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return listado;
	}

	public ActionForward irModificar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoTipoGerencias = new ArrayList();
		ArrayList listadoGerencias = new ArrayList();
		Utilidades utilidades = new Utilidades();

		ArrayList listaSituaciones = null;
		ArrayList listaVinculos = null;
		ArrayList listaTodasProvincias = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irModificar: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();
			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);

			solicitudesOT.setCExp_id(Long.parseLong(((OCAPSolicitudesForm) form).getCExp_id()));

			solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);

			session.setAttribute(Constantes.COMBO_CATEGORIA_ACTUAL, new ArrayList());
			session.setAttribute(Constantes.COMBO_ESPECIALIDAD_ACTUAL, new ArrayList());
			session.setAttribute(Constantes.COMBO_ESTATUTARIO, new ArrayList());
			session.setAttribute(Constantes.COMBO_CATEGORIA, new ArrayList());
			session.setAttribute(Constantes.COMBO_PROVINCIAS, new ArrayList());
			session.setAttribute(Constantes.COMBO_LOCALIDADES, new ArrayList());
			session.setAttribute(Constantes.COMBO_GRADOS_ALTA, new ArrayList());
			session.setAttribute(Constantes.COMBO_TIPOS_GERENCIAS, new ArrayList());
			session.setAttribute(Constantes.COMBO_GERENCIA, new ArrayList());
			session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO, new ArrayList());
			session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
			session.setAttribute(Constantes.COMBO_PERSONAL, new ArrayList());

			((OCAPSolicitudesForm) form).setDNombre(solicitudesOT.getDNombre());
			((OCAPSolicitudesForm) form).setDApellido1(solicitudesOT.getDApellido1());
			((OCAPSolicitudesForm) form).setCDni(solicitudesOT.getCDni());
			((OCAPSolicitudesForm) form).setCDniReal(solicitudesOT.getCDniReal());
			((OCAPSolicitudesForm) form).setBSexo(solicitudesOT.getBSexo());
			((OCAPSolicitudesForm) form).setDCorreoelectronico(solicitudesOT.getDCorreoelectronico());
			if (!solicitudesOT.getNTelefono1().equals("0"))
				((OCAPSolicitudesForm) form).setNTelefono1(solicitudesOT.getNTelefono1());
			if (!solicitudesOT.getNTelefono2().equals("0"))
				((OCAPSolicitudesForm) form).setNTelefono2(solicitudesOT.getNTelefono2());
			((OCAPSolicitudesForm) form).setDDomicilio(solicitudesOT.getDDomicilio());
			((OCAPSolicitudesForm) form).setDLocalidadUsu(solicitudesOT.getDLocalidadUsu());
			((OCAPSolicitudesForm) form).setCProvinciaUsu_id(solicitudesOT.getCProvinciaUsu_id());
			DecimalFormat formato = new DecimalFormat("00000");
			((OCAPSolicitudesForm) form).setCPostalUsu(
					solicitudesOT.getCPostalUsu() == null ? "-" : formato.format(Long.parseLong(solicitudesOT.getCPostalUsu())));

			((OCAPSolicitudesForm) form).setCJuridico(solicitudesOT.getCJuridico());
			((OCAPSolicitudesForm) form).setFRegistro_oficial(solicitudesOT.getFRegistro_oficial());

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);

			Collection listadoGrados = gradoLN.listarGradosConvocatoriasModif(solicitudesOT.getCDniReal(), solicitudesOT.getCExp_id());

			Object[] listadogrados = listadoGrados.toArray();

			for (int i = 0; i < listadoGrados.size(); i++) {
				if (((OCAPGradoOT) listadogrados[i]).getDDescripcion().equals(solicitudesOT.getDGrado_des())) {
					((OCAPSolicitudesForm) form).setCGrado_id(Long.toString(((OCAPGradoOT) listadogrados[i]).getCGradoId()));
					break;
				}
			}
			session.setAttribute(Constantes.COMBO_GRADOS_ALTA, listadoGrados);

			OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
			Collection listadoProcedimientos = procLN.listadoOCAPProcedimiento();

			session.setAttribute(Constantes.COMBO_PERSONAL, listadoProcedimientos);

			Object[] listadopers = listadoProcedimientos.toArray();

			for (int i = 0; i < listadoProcedimientos.size(); i++) {
				if (((OCAPProcedimientoOT) listadopers[i]).getDNombre().equals(solicitudesOT.getDProcedimiento())) {
					((OCAPSolicitudesForm) form)
							.setCProcedimientoId(((OCAPProcedimientoOT) listadopers[i]).getCProcedimientoId().toString());
					break;
				}
			}
			((OCAPSolicitudesForm) form).setCSitAdministrativaAuxId(solicitudesOT.getCSitAdministrativaAuxId());
			((OCAPSolicitudesForm) form).setDSitAdministrativaAuxOtros(solicitudesOT.getDSitAdministrativaAuxOtros());

			OCAPPersEstatutarioLN estatutarioLN = new OCAPPersEstatutarioLN(jcylUsuario);
			Collection listadoEstatutarios = estatutarioLN.listadoOCAPPersEstatutario();
			session.setAttribute(Constantes.COMBO_ESTATUTARIO, listadoEstatutarios);
			((OCAPSolicitudesForm) form).setCEstatutarioId(solicitudesOT.getCEstatutId() + "");
			((OCAPSolicitudesForm) form).setCEstatutarioActualId(solicitudesOT.getCEstatutActualId() + "");

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			Collection listadoCategorias = null;
			if (solicitudesOT.getCEstatutId() != 0L) {
				listadoCategorias = categProfesionalesLN.consultaOCAPCategProfesionales(0L, null, 0L, solicitudesOT.getCEstatutId(), null,
						null, null, 0L, 0, 0);
			}
			session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
			Object[] listadocateg = listadoCategorias.toArray();
			for (int i = 0; i < listadoCategorias.size(); i++) {
				if (((OCAPCategProfesionalesOT) listadocateg[i]).getDNombre().equals(solicitudesOT.getDProfesional_nombre())) {
					((OCAPSolicitudesForm) form)
							.setCProfesional_id(Long.toString(((OCAPCategProfesionalesOT) listadocateg[i]).getCProfesionalId()));
					break;
				}
			}
			Collection listadoCategoriasActuales = null;
			if (solicitudesOT.getCEstatutActualId() != 0L) {
				listadoCategoriasActuales = categProfesionalesLN.consultaOCAPCategProfesionales(0L, null, 0L,
						solicitudesOT.getCEstatutActualId(), null, null, null, 0L, 0, 0);
			}
			session.setAttribute(Constantes.COMBO_CATEGORIA_ACTUAL, listadoCategoriasActuales);
			Object[] listadocategActual = listadoCategoriasActuales.toArray();
			for (int i = 0; i < listadoCategoriasActuales.size(); i++) {
				if (((OCAPCategProfesionalesOT) listadocategActual[i]).getDNombre().equals(solicitudesOT.getDProfesionalActual_nombre())) {
					((OCAPSolicitudesForm) form).setCProfesionalActual_id(
							Long.toString(((OCAPCategProfesionalesOT) listadocategActual[i]).getCProfesionalId()));
					break;
				}
			}

			OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
			ArrayList listadoProvincias = provinciasLN.listarProvinciasComunidad(Constantes.CODIGO_CYL);
			session.setAttribute(Constantes.COMBO_PROVINCIAS, listadoProvincias);

			OCAPTipoGerenciasLN tipoGerenciasLN = new OCAPTipoGerenciasLN(jcylUsuario);
			listadoTipoGerencias = tipoGerenciasLN.listarTipoGerencias();
			session.setAttribute(Constantes.COMBO_TIPOS_GERENCIAS, listadoTipoGerencias);

			OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
			if ((solicitudesOT.getDEspec_nombre() != null) && (((OCAPSolicitudesForm) form).getCProfesional_id() != null)) {
				Collection listadoEspecialidades = especialidadesLN
						.listarEspecialidades(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesional_id()));
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
				Object[] listadoespec = listadoEspecialidades.toArray();
				for (int i = 0; i < listadoEspecialidades.size(); i++) {
					if (((OCAPEspecialidadesOT) listadoespec[i]).getDNombre().equals(solicitudesOT.getDEspec_nombre())) {
						((OCAPSolicitudesForm) form).setCEspec_id(Long.toString(((OCAPEspecialidadesOT) listadoespec[i]).getCEspecId()));
						break;
					}
				}
			}
			if ((solicitudesOT.getDEspecActual_nombre() != null) && (((OCAPSolicitudesForm) form).getCProfesionalActual_id() != null)) {
				Collection listadoEspecialidadesActuales = especialidadesLN
						.listarEspecialidades(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesionalActual_id()));
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD_ACTUAL, listadoEspecialidadesActuales);
				Object[] listadoespecActuales = listadoEspecialidadesActuales.toArray();
				for (int i = 0; i < listadoEspecialidadesActuales.size(); i++) {
					if (((OCAPEspecialidadesOT) listadoespecActuales[i]).getDNombre().equals(solicitudesOT.getDEspecActual_nombre())) {
						((OCAPSolicitudesForm) form)
								.setCEspecActual_id(Long.toString(((OCAPEspecialidadesOT) listadoespecActuales[i]).getCEspecId()));
						break;
					}
				}
			}

			if ((solicitudesOT.getCProvinciaUsu_id() != null) && (!solicitudesOT.getCProvinciaUsu_id().equals(""))) {
				OCAPLocalidadesLN localidadesLN = new OCAPLocalidadesLN(jcylUsuario);
				Collection listadoLocal = localidadesLN.listarLocalidadesProvincia(solicitudesOT.getCProvinciaUsu_id());
				Object[] listadoLocalidades = listadoLocal.toArray();

				session.setAttribute(Constantes.COMBO_LOCALIDADES, utilidades.ArrayObjectToArrayList(listadoLocalidades));
			}
			((OCAPSolicitudesForm) form).setDLocalidadUsu(solicitudesOT.getDLocalidadUsu());
			((OCAPSolicitudesForm) form).setACiudad(((OCAPSolicitudesForm) form).getACiudad());

			((OCAPSolicitudesForm) form).setAccionEnviar("Registrar");

			((OCAPSolicitudesForm) form).setFRegistro_solic(solicitudesOT.getFRegistro_solic());
			((OCAPSolicitudesForm) form).setNAniosantiguedad(Long.toString(solicitudesOT.getNAniosantiguedad()));
			((OCAPSolicitudesForm) form).setNMesesantiguedad(Long.toString(solicitudesOT.getNMesesantiguedad()));
			((OCAPSolicitudesForm) form).setNDiasantiguedad(Long.toString(solicitudesOT.getNDiasantiguedad()));

			if (solicitudesOT.getFPlazapropiedad() != null) {
				SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);

				String fechaplaza = df.format(solicitudesOT.getFPlazapropiedad());

				((OCAPSolicitudesForm) form).setFPlazapropiedad(fechaplaza);
			}

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			OCAPGerenciasOT gerenciasOT = new OCAPGerenciasOT();
			listadoGerencias = gerenciasLN.listarGerencias(solicitudesOT.getCProvincia_id(), solicitudesOT.getCTipogerencia_id() + "");
			session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);

			((OCAPSolicitudesForm) form).setCGerencia_id(solicitudesOT.getCGerencia_id() + "");
			((OCAPSolicitudesForm) form).setCTipogerencia_id(solicitudesOT.getCTipogerencia_id() + "");
			((OCAPSolicitudesForm) form).setCProvincia_id(solicitudesOT.getCProvincia_id());

			if (solicitudesOT.getDCentrotrabajo_nombre() != null) {
				OCAPCentroTrabajoLN centroTrabajoLN = new OCAPCentroTrabajoLN(jcylUsuario);
				Collection listadoCentros = centroTrabajoLN.listarCentroTrabajo(solicitudesOT.getCGerencia_id() + "", null);

				session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO, listadoCentros);

				Object[] listadocent = listadoCentros.toArray();

				for (int i = 0; i < listadoCentros.size(); i++) {
					if (((OCAPCentroTrabajoOT) listadocent[i]).getDNombre().equals(solicitudesOT.getDCentrotrabajo_nombre())) {
						((OCAPSolicitudesForm) form)
								.setCCentrotrabajo_id(Long.toString(((OCAPCentroTrabajoOT) listadocent[i]).getCCentrotrabajoId()));
						((OCAPSolicitudesForm) form).setACiudad(((OCAPCentroTrabajoOT) listadocent[i]).getACiudad());
						break;
					}
				}
			}

			listaSituaciones = solicitudesLN.listaSituaciones();
			session.setAttribute(Constantes.COMBO_SITUACIONES, listaSituaciones);
			listaVinculos = solicitudesLN.listaVinculos();
			session.setAttribute(Constantes.COMBO_VINCULOS, listaVinculos);
			listaTodasProvincias = provinciasLN.listarProvincias();
			session.setAttribute(Constantes.COMBO_TODAS_PROVINCIAS, listaTodasProvincias);

			((OCAPSolicitudesForm) form).setResumenCentros(solicitudesOT.getResumenCentros());

			request.setAttribute("opcion", request.getParameter("opcion"));

			OCAPConfigApp.logger.info(getClass().getName() + " irModificar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irModificar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		int idExpediente = 0;
		String fw = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " modificar: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();
			OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
			OCAPProvinciasOT provinciaOT = new OCAPProvinciasOT();

			solicitudesOT.setDNombre(((OCAPSolicitudesForm) form).getDNombre());
			solicitudesOT.setDApellido1(((OCAPSolicitudesForm) form).getDApellido1());

			solicitudesOT.setCDni(((OCAPSolicitudesForm) form).getCDni());
			solicitudesOT.setCDniReal(((OCAPSolicitudesForm) form).getCDniReal());
			solicitudesOT.setDCorreoelectronico(((OCAPSolicitudesForm) form).getDCorreoelectronico());
			solicitudesOT.setNTelefono1(((OCAPSolicitudesForm) form).getNTelefono1());
			solicitudesOT.setNTelefono2(((OCAPSolicitudesForm) form).getNTelefono2());
			solicitudesOT.setBSexo(((OCAPSolicitudesForm) form).getBSexo());
			solicitudesOT.setDDomicilio(((OCAPSolicitudesForm) form).getDDomicilio());
			solicitudesOT.setCProvinciaUsu_id(((OCAPSolicitudesForm) form).getCProvinciaUsu_id());
			solicitudesOT.setDLocalidadUsu(((OCAPSolicitudesForm) form).getDLocalidadUsu());
			solicitudesOT.setCPostalUsu(((OCAPSolicitudesForm) form).getCPostalUsu());

			solicitudesOT.setCProcedimientoId(((OCAPSolicitudesForm) form).getCProcedimientoId());
			solicitudesOT.setCEstatutActualId(Long.parseLong(((OCAPSolicitudesForm) form).getCEstatutarioActualId()));
			solicitudesOT.setCProfesionalActual_id(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesionalActual_id()));
			if ((((OCAPSolicitudesForm) form).getCEspecActual_id() != null)
					&& (!((OCAPSolicitudesForm) form).getCEspecActual_id().equals("")))
				solicitudesOT.setCEspecActual_id(Long.parseLong(((OCAPSolicitudesForm) form).getCEspecActual_id()));
			else
				solicitudesOT.setCEspecActual_id(-1);
			solicitudesOT.setCEstatutId(Long.parseLong(((OCAPSolicitudesForm) form).getCEstatutarioId()));
			solicitudesOT.setCProfesional_id(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesional_id()));
			if ((((OCAPSolicitudesForm) form).getCEspec_id() != null) && (!((OCAPSolicitudesForm) form).getCEspec_id().equals("")))
				solicitudesOT.setCEspec_id(Long.parseLong(((OCAPSolicitudesForm) form).getCEspec_id()));
			solicitudesOT.setCTipogerencia_id(((OCAPSolicitudesForm) form).getCTipogerencia_id());
			solicitudesOT.setCProvincia_id(((OCAPSolicitudesForm) form).getCProvincia_id());
			solicitudesOT.setCGerencia_id(Long.parseLong(((OCAPSolicitudesForm) form).getCGerencia_id()));
			solicitudesOT.setCCentrotrabajo_id(Long.parseLong(((OCAPSolicitudesForm) form).getCCentrotrabajo_id()));

			if ((((OCAPSolicitudesForm) form).getFPlazapropiedad() != null)
					&& (!((OCAPSolicitudesForm) form).getFPlazapropiedad().equals("")))
				solicitudesOT.setFPlazapropiedad(DateUtil.convertStringToDate(((OCAPSolicitudesForm) form).getFPlazapropiedad()));
			if ((((OCAPSolicitudesForm) form).getNAniosantiguedad() != null)
					&& (!((OCAPSolicitudesForm) form).getNAniosantiguedad().equals("")))
				solicitudesOT.setNAniosantiguedad(Long.parseLong(((OCAPSolicitudesForm) form).getNAniosantiguedad()));
			if ((((OCAPSolicitudesForm) form).getNMesesantiguedad() != null)
					&& (!((OCAPSolicitudesForm) form).getNMesesantiguedad().equals("")))
				solicitudesOT.setNMesesantiguedad(Long.parseLong(((OCAPSolicitudesForm) form).getNMesesantiguedad()));
			if ((((OCAPSolicitudesForm) form).getNDiasantiguedad() != null)
					&& (!((OCAPSolicitudesForm) form).getNDiasantiguedad().equals(""))) {
				solicitudesOT.setNDiasantiguedad(Long.parseLong(((OCAPSolicitudesForm) form).getNDiasantiguedad()));
			}
			if (solicitudesOT.getNAniosantiguedad() == 0L)
				solicitudesOT.setNAniosantiguedad(-1);
			if (solicitudesOT.getNMesesantiguedad() == 0L)
				solicitudesOT.setNMesesantiguedad(-1);
			if (solicitudesOT.getNDiasantiguedad() == 0L)
				solicitudesOT.setNDiasantiguedad(-1);
			if ((((OCAPSolicitudesForm) form).getCGrado_id() != null) && (!((OCAPSolicitudesForm) form).getCGrado_id().equals("")))
				solicitudesOT.setCGrado_id(Long.parseLong(((OCAPSolicitudesForm) form).getCGrado_id()));
			if ((((OCAPSolicitudesForm) form).getCConvocatoriaId() != null)
					&& (!((OCAPSolicitudesForm) form).getCConvocatoriaId().equals(""))) {
				solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPSolicitudesForm) form).getCConvocatoriaId()));
			}
			solicitudesOT.setFRegistro_oficial(((OCAPSolicitudesForm) form).getFRegistro_oficial());

			solicitudesOT.setCExp_id(Long.parseLong(((OCAPSolicitudesForm) form).getCExp_id()));

			solicitudesOT.setAModificadoPor(((JCYLUsuario) request.getSession().getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO))
					.getUsuario().getC_usr_id());

			solicitudesOT.setCJuridico(((OCAPSolicitudesForm) form).getCJuridico());

			solicitudesOT.setCSitAdministrativaAuxId(((OCAPSolicitudesForm) form).getCSitAdministrativaAuxId());

			if ((((OCAPSolicitudesForm) form).getCSitAdministrativaAuxId() != null)
					&& ("6".equals(((OCAPSolicitudesForm) form).getCSitAdministrativaAuxId())))
				solicitudesOT.setDSitAdministrativaAuxOtros(((OCAPSolicitudesForm) form).getDSitAdministrativaAuxOtros());
			else
				solicitudesOT.setDSitAdministrativaAuxOtros("");
			solicitudesOT.setResumenCentros(((OCAPSolicitudesForm) form).getResumenCentros());
			idExpediente = solicitudesLN.modificarSolicitud(solicitudesOT, jcylUsuario, false);

			solicitudesOT.setDNombre(((OCAPSolicitudesForm) form).getDNombre());
			solicitudesOT.setDApellido1(((OCAPSolicitudesForm) form).getDApellido1());

			solicitudesOT.setDCorreoelectronico(((OCAPSolicitudesForm) form).getDCorreoelectronico());

			solicitudesOT.setDProcedimiento(((OCAPSolicitudesForm) form).getDProcedimiento());
			solicitudesOT.setDEstatut_nombre(((OCAPSolicitudesForm) form).getDEstatutario_nombre());
			solicitudesOT.setDProfesional_nombre(((OCAPSolicitudesForm) form).getDProfesional_nombre());
			solicitudesOT.setDEspec_nombre(((OCAPSolicitudesForm) form).getDEspec_nombre());
			solicitudesOT.setDProvincia(((OCAPSolicitudesForm) form).getDProvincia());
			solicitudesOT.setDTipogerencia_desc(((OCAPSolicitudesForm) form).getDTipogerencia_desc());
			solicitudesOT.setDCentrotrabajo_nombre(((OCAPSolicitudesForm) form).getDCentrotrabajo_nombre());
			solicitudesOT.setDGrado_des(((OCAPSolicitudesForm) form).getDGrado_des());

			request.setAttribute("rutaVuelta", "OCAPSolicitudes.do?accion=buscar&opcion=" + request.getParameter("opcion") + "&"
					+ Constantes.RECUPERAR_BUSQUEDA_ANTERIOR + "=" + Constantes.SI);

			fw = "mensajeExito";

			OCAPConfigApp.logger.info(getClass().getName() + " insertar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(fw);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irListar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		ArrayList listadoGrados = null;
		ArrayList listaConvocatorias = null;

		String forward = "listarFase";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irListar: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (session.getAttribute(Constantes.COMBO_GRADOS_CONSULTA) == null) {
				OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
				listadoGrados = gradoLN.listarGrados();
				session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, listadoGrados);
			}

			if (session.getAttribute(Constantes.COMBO_CONVOCATORIAS) == null) {
				OCAPConvocatoriasLN convoLN = new OCAPConvocatoriasLN(jcylUsuario);
				listaConvocatorias = convoLN.listarConvocatorias();
				session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listaConvocatorias);
			}

			if ((request.getParameter("fase") != null) && (!"".equals(request.getParameter("fase")))) {
				request.setAttribute("fase", request.getParameter("fase"));
			} else {
				if (Constantes.OCAP_FQS_GEST.equals(jcylUsuario.getUser().getRol()))
					request.setAttribute("fase", Constantes.FASE_CA);
				if (Constantes.OCAP_FQS_ADTVO.equals(jcylUsuario.getUser().getRol()))
					request.setAttribute("fase", Constantes.FASE_CA_ESCANEADA);
				if (Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())) {
					request.setAttribute("fase", Constantes.FASE_CA_TERMINADA);
				}
			}
			if (Constantes.FASE_CA_TERMINADA.equals(request.getAttribute("fase"))) {
				forward = "listarFaseDocumentos";
			}
			((OCAPSolicitudesForm) form).setjspInicio(true);

			session.setAttribute("iRegistro", Integer.toString(1));

			OCAPConfigApp.logger.info(getClass().getName() + " irListar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(forward);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward listarFase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoSolicitudes = null;
		String forward = "listarFase";
		String menu = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscar: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPSolicitudesForm) request.getSession().getAttribute("OCAPSolicitudesFormBuscador");
				request.setAttribute("OCAPSolicitudesForm", form);
			} else {
				request.getSession().setAttribute("OCAPSolicitudesFormBuscador", form);
				session.setAttribute("iRegistro", 1 + "");
			}

			int primerRegistro = 1;
			int registrosPorPagina = 10;
			String registro = null;

			if ((registro = request.getParameter("iRegistro")) != null)
				try {
					primerRegistro = Integer.parseInt(registro);
					session.setAttribute("iRegistro", primerRegistro + "");
				} catch (NumberFormatException ne) {
				}
			else if ((registro = session.getAttribute("iRegistro") == null ? null : session.getAttribute("iRegistro").toString()) != null) {
				try {
					primerRegistro = Integer.parseInt(registro);
				} catch (NumberFormatException ne) {
				}
			}
			if ((registro = request.getParameter("nRegistrosP")) != null) {
				try {
					registrosPorPagina = Integer.parseInt(registro);
				} catch (NumberFormatException ne) {
				}
			}
			if ((!Constantes.SI.equals(request.getParameter("bPagina")))
					&& (!Constantes.SI.equals(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))) {
				primerRegistro = 1;
			}

			if (request.getParameter("fase") != null) {
				solicitudesOT.setCFase(request.getParameter("fase"));
			}

			solicitudesOT.setDApellido1(((OCAPSolicitudesForm) form).getDApellido1());

			solicitudesOT.setDNombre(((OCAPSolicitudesForm) form).getDNombre());
			if (((OCAPSolicitudesForm) form).getCDni() != null)
				solicitudesOT.setCDni(((OCAPSolicitudesForm) form).getCDni().toUpperCase());
			if (((OCAPSolicitudesForm) form).getCDniReal() != null)
				solicitudesOT.setCDniReal(((OCAPSolicitudesForm) form).getCDniReal().toUpperCase());
			if ((((OCAPSolicitudesForm) form).getCGrado_id() != null) && (!((OCAPSolicitudesForm) form).getCGrado_id().equals("")))
				solicitudesOT.setCGrado_id(Long.parseLong(((OCAPSolicitudesForm) form).getCGrado_id()));
			else if ((((OCAPSolicitudesForm) form).getCGrado_id() == null) || (((OCAPSolicitudesForm) form).getCGrado_id().equals(""))) {
				solicitudesOT.setCGrado_id(0L);
			}
			if ((((OCAPSolicitudesForm) form).getCConvocatoriaId() != null)
					&& (!((OCAPSolicitudesForm) form).getCConvocatoriaId().equals("")))
				solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPSolicitudesForm) form).getCConvocatoriaId()));
			else if ((((OCAPSolicitudesForm) form).getCConvocatoriaId() == null)
					|| (((OCAPSolicitudesForm) form).getCConvocatoriaId().equals(""))) {
				solicitudesOT.setCConvocatoriaId(0L);
			}
			if ((((OCAPSolicitudesForm) form).getCodigoId() != null) && (!"".equals(((OCAPSolicitudesForm) form).getCodigoId()))) {
				String codigoEvaluado = ((OCAPSolicitudesForm) form).getCodigoId();
				codigoEvaluado = codigoEvaluado.substring(Constantes.CODIGO_EVALUADO.length());
				solicitudesOT.setCExp_id(Long.parseLong(codigoEvaluado));
			}

			if (Constantes.FASE_CA_TERMINADA.equals(request.getParameter("fase")))
				menu = "aportacionEvidencias";
			else {
				menu = "";
			}
			listadoSolicitudes = solicitudesLN.buscar(primerRegistro, registrosPorPagina, solicitudesOT, true, menu, false);
			int numSolicitudes = solicitudesLN.contarSolicitudes(solicitudesOT, jcylUsuario, menu, false);

			if (Constantes.FASE_CA_TERMINADA.equals(request.getParameter("fase"))) {
				if (numSolicitudes == 1) {
					session.setAttribute("idExpediente", String.valueOf(solicitudesOT.getCExp_id()));
					forward = "irAltaDocumentos";
				} else {
					forward = "listarFaseDocumentos";
				}
			}
			session.setAttribute("numSolicitudes", new Integer(numSolicitudes));

			Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
			pagina.setElementos(listadoSolicitudes);
			pagina.setNRegistros(((Integer) session.getAttribute("numSolicitudes")).intValue());
			pagina.setRegistroActual(primerRegistro);
			pagina.setRegistrosPorPagina(registrosPorPagina);

			request.setAttribute("listados", pagina);

			OCAPConfigApp.logger.info(getClass().getName() + " listarFase: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(forward);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irSolicitud(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoSitAdministrativas = new ArrayList();
		ArrayList listadoCategoriasActuales = new ArrayList();
		ArrayList listadoEspecialidadesActuales = new ArrayList();
		ArrayList listadoEstatutarios = new ArrayList();
		ArrayList listadoCategorias = new ArrayList();
		ArrayList listadoEspecialidades = new ArrayList();
		ArrayList listadoGrados = new ArrayList();
		ArrayList listadoTipoGerencias = new ArrayList();

		ArrayList listadoCentros = new ArrayList();
		Utilidades utilidades = new Utilidades();

		String dni = null;

		ArrayList listaSituaciones = null;
		ArrayList listaVinculos = null;
		ArrayList listaTodasProvincias = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Inicio");
			validarAccion(mapping, form, request, response);

			Usuario usuarioSEGU = new Usuario();

			JCYLUsuario usuario = new JCYLUsuario(usuarioSEGU);

			OCAPGradoLN gradoLN = new OCAPGradoLN(usuario);
			listadoGrados = gradoLN.listarGradosConvocatoriasAbiertas("", usuario);

			if ((listadoGrados == null) || (listadoGrados.size() == 0)) {
				return mapping.findForward("irNoExistenConvocatoriasSolic");
			}

			OCAPPersEstatutarioLN estatutarioLN = new OCAPPersEstatutarioLN(usuario);
			listadoEstatutarios = estatutarioLN.listadoOCAPPersEstatutario();

			OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(usuario);
			ArrayList listadoProvincias = provinciasLN.listarProvinciasComunidad(Constantes.CODIGO_CYL);

			OCAPTipoGerenciasLN tipoGerenciasLN = new OCAPTipoGerenciasLN(usuario);
			listadoTipoGerencias = tipoGerenciasLN.listarTipoGerencias();

			OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(usuario);

			session.setAttribute(Constantes.COMBO_CATEGORIA_ACTUAL, new ArrayList());
			session.setAttribute(Constantes.COMBO_ESPECIALIDAD_ACTUAL, new ArrayList());
			session.setAttribute(Constantes.COMBO_ESTATUTARIO, new ArrayList());
			session.setAttribute(Constantes.COMBO_CATEGORIA, new ArrayList());
			session.setAttribute(Constantes.COMBO_PROVINCIAS, new ArrayList());
			session.setAttribute(Constantes.COMBO_LOCALIDADES, new ArrayList());
			session.setAttribute(Constantes.COMBO_GRADOS_ALTA, new ArrayList());
			session.setAttribute(Constantes.COMBO_TIPOS_GERENCIAS, new ArrayList());
			session.setAttribute(Constantes.COMBO_GERENCIA, new ArrayList());
			session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO, new ArrayList());
			session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
			session.setAttribute(Constantes.COMBO_PERSONAL, new ArrayList());

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(usuario);
			if ((((OCAPSolicitudesForm) form).getCEstatutarioId() != null)
					&& (!"".equals(((OCAPSolicitudesForm) form).getCEstatutarioId()))) {
				listadoCategorias = categProfesionalesLN.consultaOCAPCategProfesionales(0L, null, 0L,
						Long.parseLong(((OCAPSolicitudesForm) form).getCEstatutarioId()), null, null, null, 0L, 0, 0);
			}

			if ((((OCAPSolicitudesForm) form).getCProfesional_id() != null)
					&& (!"".equals(((OCAPSolicitudesForm) form).getCProfesional_id()))) {
				listadoEspecialidades = especialidadesLN
						.listarEspecialidades(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesional_id()));
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
			}

			if ((((OCAPSolicitudesForm) form).getCEstatutarioActualId() != null)
					&& (!"".equals(((OCAPSolicitudesForm) form).getCEstatutarioActualId()))) {
				listadoCategoriasActuales = categProfesionalesLN.consultaOCAPCategProfesionales(0L, null, 0L,
						Long.parseLong(((OCAPSolicitudesForm) form).getCEstatutarioActualId()), null, null, null, 0L, 0, 0);
			}

			if ((((OCAPSolicitudesForm) form).getCProfesionalActual_id() != null)
					&& (!"".equals(((OCAPSolicitudesForm) form).getCProfesionalActual_id()))) {
				listadoEspecialidadesActuales = especialidadesLN
						.listarEspecialidades(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesionalActual_id()));
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD_ACTUAL, listadoEspecialidadesActuales);
			}

			if ((((OCAPSolicitudesForm) form).getCProvinciaUsu_id() != null)
					&& (!((OCAPSolicitudesForm) form).getCProvinciaUsu_id().equals(""))) {
				OCAPLocalidadesLN localidadesLN = new OCAPLocalidadesLN(usuario);
				Collection listadoLocal = localidadesLN.listarLocalidadesProvincia(((OCAPSolicitudesForm) form).getCProvinciaUsu_id());
				Object[] listadoLocalidades = listadoLocal.toArray();

				session.setAttribute(Constantes.COMBO_LOCALIDADES, utilidades.ArrayObjectToArrayList(listadoLocalidades));
			}

			session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
			session.setAttribute(Constantes.COMBO_CATEGORIA_ACTUAL, listadoCategoriasActuales);
			session.setAttribute(Constantes.COMBO_ESTATUTARIO, listadoEstatutarios);
			session.setAttribute(Constantes.COMBO_PROVINCIAS, listadoProvincias);
			session.setAttribute(Constantes.COMBO_GRADOS_ALTA, listadoGrados);
			session.setAttribute(Constantes.COMBO_TIPOS_GERENCIAS, listadoTipoGerencias);
			session.setAttribute(Constantes.COMBO_PERSONAL, listadoSitAdministrativas);

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(usuario);
			OCAPGerenciasOT gerenciasOT = new OCAPGerenciasOT();

			session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO, listadoCentros);

			request.setAttribute("primeraSolic", Constantes.SI);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(usuario);
			listaSituaciones = solicitudesLN.listaSituaciones();
			session.setAttribute(Constantes.COMBO_SITUACIONES, listaSituaciones);
			listaVinculos = solicitudesLN.listaVinculos();
			session.setAttribute(Constantes.COMBO_VINCULOS, listaVinculos);
			listaTodasProvincias = provinciasLN.listarProvincias();
			session.setAttribute(Constantes.COMBO_TODAS_PROVINCIAS, listaTodasProvincias);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irSolicitud");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward generarPDFSolic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		String pathBase = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarPDFSolic: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();
			OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
			OCAPProvinciasOT provinciaOT = new OCAPProvinciasOT();

			solicitudesOT.setDApellido1(((OCAPSolicitudesForm) form).getDApellido1());
			solicitudesOT.setDApellido2(((OCAPSolicitudesForm) form).getDApellido2());
			solicitudesOT.setDNombre(((OCAPSolicitudesForm) form).getDNombre());
			solicitudesOT.setCDniReal(((OCAPSolicitudesForm) form).getCDniReal());
			solicitudesOT.setBSexo(((OCAPSolicitudesForm) form).getBSexo());
			solicitudesOT.setDCorreoelectronico(((OCAPSolicitudesForm) form).getDCorreoelectronico());

			if ((((OCAPSolicitudesForm) form).getNTelefono1() != null) && (!((OCAPSolicitudesForm) form).getNTelefono1().equals(""))) {
				solicitudesOT.setNTelefono1(((OCAPSolicitudesForm) form).getNTelefono1());
			}

			if ((((OCAPSolicitudesForm) form).getNTelefono2() != null) && (!((OCAPSolicitudesForm) form).getNTelefono2().equals(""))) {
				solicitudesOT.setNTelefono2(((OCAPSolicitudesForm) form).getNTelefono2());
			}

			solicitudesOT.setDDomicilio(((OCAPSolicitudesForm) form).getDDomicilio());
			solicitudesOT.setDLocalidadUsu(((OCAPSolicitudesForm) form).getDLocalidadUsu());

			provinciaOT = provinciasLN.buscarProvincia(((OCAPSolicitudesForm) form).getCProvinciaUsu_id());
			solicitudesOT.setDProvinciaUsu(Cadenas.capitalizar(provinciaOT.getDProvincia()));

			solicitudesOT.setCPostalUsu(((OCAPSolicitudesForm) form).getCPostalUsu());

			solicitudesOT.setCJuridico(((OCAPSolicitudesForm) form).getCJuridico());
			solicitudesOT.setCSitAdministrativaAuxId(((OCAPSolicitudesForm) form).getCSitAdministrativaAuxId());
			solicitudesOT.setDSitAdministrativaAuxOtros(((OCAPSolicitudesForm) form).getDSitAdministrativaAuxOtros());

			OCAPCategProfesionalesLN categProfAcLN = new OCAPCategProfesionalesLN(jcylUsuario);
			OCAPCategProfesionalesOT categProfAcOT = new OCAPCategProfesionalesOT();
			categProfAcOT = categProfAcLN
					.buscarOCAPCategProfesionales(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesionalActual_id()));
			solicitudesOT.setDProfesionalActual_nombre(categProfAcOT.getDNombre());

			OCAPEspecialidadesLN especAcLN = new OCAPEspecialidadesLN(jcylUsuario);
			OCAPEspecialidadesOT especAcOT = new OCAPEspecialidadesOT();
			if ((((OCAPSolicitudesForm) form).getCEspecActual_id() != null)
					&& (!((OCAPSolicitudesForm) form).getCEspecActual_id().equals("0"))) {
				especAcOT = especAcLN.buscarOCAPEspecialidades(Long.parseLong(((OCAPSolicitudesForm) form).getCEspecActual_id()));
				solicitudesOT.setDEspecActual_nombre(especAcOT.getDNombre());
			} else {
				solicitudesOT.setDEspecActual_nombre("");
			}

			provinciaOT = provinciasLN.buscarProvincia(((OCAPSolicitudesForm) form).getCProvincia_id());
			solicitudesOT.setDProvincia(Cadenas.capitalizar(provinciaOT.getDProvincia()));

			solicitudesOT.setACiudad(((OCAPSolicitudesForm) form).getACiudad());

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			OCAPGerenciasOT gerenciasOT = new OCAPGerenciasOT();
			gerenciasOT = gerenciasLN.buscarOCAPGerencias(Long.parseLong(((OCAPSolicitudesForm) form).getCGerencia_id()));
			solicitudesOT.setDGerencia_nombre(gerenciasOT.getDNombre());

			OCAPCentroTrabajoLN centroLN = new OCAPCentroTrabajoLN(jcylUsuario);
			OCAPCentroTrabajoOT centroOT = new OCAPCentroTrabajoOT();
			centroOT = centroLN.buscarOCAPCentroTrabajo(Long.parseLong(((OCAPSolicitudesForm) form).getCCentrotrabajo_id()));
			solicitudesOT.setDCentrotrabajo_nombre(centroOT.getDNombre());

			solicitudesOT.setCProcedimientoId(((OCAPSolicitudesForm) form).getCProcedimientoId());

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			OCAPGradoOT gradoOT = new OCAPGradoOT();
			gradoOT = gradoLN.buscarOCAPGrado(Long.parseLong(((OCAPSolicitudesForm) form).getCGrado_id()));
			solicitudesOT.setDGrado_des(gradoOT.getDDescripcion());

			OCAPCategProfesionalesLN categProfLN = new OCAPCategProfesionalesLN(jcylUsuario);
			OCAPCategProfesionalesOT categProfOT = new OCAPCategProfesionalesOT();
			categProfOT = categProfLN.buscarOCAPCategProfesionales(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesional_id()));
			solicitudesOT.setDProfesional_nombre(categProfOT.getDNombre());

			OCAPEspecialidadesLN especLN = new OCAPEspecialidadesLN(jcylUsuario);
			OCAPEspecialidadesOT especOT = new OCAPEspecialidadesOT();
			if ((((OCAPSolicitudesForm) form).getCEspec_id() != null) && (!((OCAPSolicitudesForm) form).getCEspec_id().equals("0"))) {
				especOT = especLN.buscarOCAPEspecialidades(Long.parseLong(((OCAPSolicitudesForm) form).getCEspec_id()));
				solicitudesOT.setDEspec_nombre(especOT.getDNombre());
			} else {
				solicitudesOT.setDEspec_nombre("");
			}

			solicitudesOT.setNAniosantiguedad(Long.parseLong(((OCAPSolicitudesForm) form).getNAniosantiguedad()));
			solicitudesOT.setNMesesantiguedad(Long.parseLong(((OCAPSolicitudesForm) form).getNMesesantiguedad()));
			solicitudesOT.setNDiasantiguedad(Long.parseLong(((OCAPSolicitudesForm) form).getNDiasantiguedad()));

			solicitudesOT.setBOtroServicio(((OCAPSolicitudesForm) form).getBOtroServicio());
			solicitudesOT.setADondeServicio(((OCAPSolicitudesForm) form).getADondeServicio());
			solicitudesOT.setResumenCentros(((OCAPSolicitudesForm) form).getResumenCentros());

			pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");

			solicitudesLN.crearPDFSolicitud(response, solicitudesOT, pathBase);

			OCAPConfigApp.logger.info(getClass().getName() + " generarPDF: Fin");
		} catch (SendFailedException e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.envioEmail")));
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mensajeExito");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irBuscarGrs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoGerencias = new ArrayList();
		ArrayList listaEstados = new ArrayList();

		ArrayList listadoGrados = new ArrayList();
		ArrayList listaConvocatorias = new ArrayList();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irBuscarGrs: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
		
			listadoGerencias = gerenciasLN.listadoOCAPGerencias();
			session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);
			
			OCAPGerenciasOT gerenciasOT = new OCAPGerenciasOT();
			gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			gerenciasOT = gerenciasLN.buscaOCAPGerenciaLDAP(jcylUsuario.getUser().getD_gerencia());
			OCAPSolicitudesForm formumario= new OCAPSolicitudesForm();
			((OCAPSolicitudesForm) form).setDNombre(gerenciasOT.getDNombre());
			session.setAttribute("OCAPSolicitudesForm", form);
			
			if (!request.getParameter("opcion").equals("recon")) {
				listaEstados = new ArrayList();

				if (request.getParameter("opcion").equals(Constantes.GRS_MERIT)) {
					listaEstados.add(new LabelValueBean(Constantes.FASE_AUTOEVALUACION, Constantes.FASE_AUTOEVALUACION_VALUE));
				}

				listaEstados.add(new LabelValueBean(Constantes.ESTADO_PENDIENTE, Constantes.ESTADO_PENDIENTE_VALUE));
				listaEstados.add(new LabelValueBean(Constantes.ESTADO_ACEPTADO, Constantes.ESTADO_ACEPTADO_VALUE));

				if (request.getParameter("opcion").equals(Constantes.GRS_SOLIC)) {
					listaEstados.add(new LabelValueBean(Constantes.ESTADO_EXCLUIDO, Constantes.ESTADO_EXCLUIDO_VALUE));
				}

				if (request.getParameter("opcion").equals(Constantes.GRS_MERIT)) {
					listaEstados.add(new LabelValueBean(Constantes.ESTADO_EXCLUIDO, Constantes.ESTADO_DESESTIMADO_VALUE));
				}

				if (request.getParameter("opcion").equals(Constantes.GRS_PROCC)) {
					listaEstados.add(new LabelValueBean(Constantes.ESTADO_EXCLUIDO, Constantes.ESTADO_DENEGADO_VALUE));
				}
				session.setAttribute(Constantes.COMBO_ESTADOS, listaEstados);
			}

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			listadoGrados = gradoLN.listarGrados();
			session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, listadoGrados);

			OCAPConvocatoriasLN convoLN = new OCAPConvocatoriasLN(jcylUsuario);
			listaConvocatorias = convoLN.listarConvocatorias();
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listaConvocatorias);

			request.setAttribute("opcion", request.getParameter("opcion"));
			((OCAPSolicitudesForm) form).setjspInicio(true);

			session.setAttribute("iRegistro", Integer.toString(1));

			OCAPConfigApp.logger.info(getClass().getName() + " irBuscarGrs: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irBuscarGrs");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward buscarGrs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoSolicitudes = new ArrayList();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarGrs: Inicio");
			validarAccion(mapping, form, request, response);
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPSolicitudesForm) request.getSession().getAttribute("OCAPSolicitudesFormBuscador");
				request.setAttribute("OCAPSolicitudesForm", form);
			} else {
				request.getSession().setAttribute("OCAPSolicitudesFormBuscador", form);
				session.setAttribute("iRegistro", 1 + "");
			}
			
			if (Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())) {
				OCAPGerenciasOT gerenciasOT = new OCAPGerenciasOT();
				OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
				gerenciasOT = gerenciasLN.buscaOCAPGerenciaLDAP(jcylUsuario.getUser().getD_gerencia());
				OCAPSolicitudesForm formumario = new OCAPSolicitudesForm();
				((OCAPSolicitudesForm) form).setDNombre(gerenciasOT.getDNombre());
				session.setAttribute("OCAPSolicitudesForm", form);
			}
			
			int primerRegistro = 1;
			int registrosPorPagina = 10;
			String registro = null;

			if ((registro = request.getParameter("iRegistro")) != null)
				try {
					primerRegistro = Integer.parseInt(registro);
					session.setAttribute("iRegistro", primerRegistro + "");
				} catch (NumberFormatException ne) {
				}
			else if ((registro = session.getAttribute("iRegistro") == null ? null : session.getAttribute("iRegistro").toString()) != null) {
				try {
					primerRegistro = Integer.parseInt(registro);
				} catch (NumberFormatException ne) {
				}
			}
			if ((registro = request.getParameter("nRegistrosP")) != null) {
				try {
					registrosPorPagina = Integer.parseInt(registro);
				} catch (NumberFormatException ne) {
				}
			}
			if ((!Constantes.SI.equals(request.getParameter("bPagina")))
					&& (!Constantes.SI.equals(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))) {
				primerRegistro = 1;
			}

			request.setAttribute("masiva", Constantes.NO);

			if ((jcylUsuario.getUser().getRol() != null) && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE))) {
				OCAPGerenciasLN gerenciaLN = new OCAPGerenciasLN(jcylUsuario);
				OCAPGerenciasOT gerenciaOT = new OCAPGerenciasOT();
				gerenciaLN = new OCAPGerenciasLN(jcylUsuario);
				gerenciaOT = gerenciaLN.buscaOCAPGerenciaLDAP(jcylUsuario.getUser().getD_gerencia());
				solicitudesOT.setCGerencia_id(gerenciaOT.getCGerenciaId());
			} else if ((((OCAPSolicitudesForm) form).getCGerencia_id() != null)
					&& (!((OCAPSolicitudesForm) form).getCGerencia_id().equals(""))) {
				solicitudesOT.setCGerencia_id(Long.parseLong(((OCAPSolicitudesForm) form).getCGerencia_id()));
			}

			solicitudesOT.setCEstado(((OCAPSolicitudesForm) form).getCEstado());

			if ((((OCAPSolicitudesForm) form).getCGrado_id() != null) && (!((OCAPSolicitudesForm) form).getCGrado_id().equals("")))
				solicitudesOT.setCGrado_id(Long.parseLong(((OCAPSolicitudesForm) form).getCGrado_id()));
			else if (((OCAPSolicitudesForm) form).getCGrado_id().equals("")) {
				solicitudesOT.setCGrado_id(0L);
			}
			if ((((OCAPSolicitudesForm) form).getCConvocatoriaId() != null)
					&& (!((OCAPSolicitudesForm) form).getCConvocatoriaId().equals("")))
				solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPSolicitudesForm) form).getCConvocatoriaId()));
			else if (((OCAPSolicitudesForm) form).getCConvocatoriaId().equals("")) {
				solicitudesOT.setCConvocatoriaId(0L);
			}

			String opcion = request.getParameter("opcion");

			request.setAttribute("opcion", opcion);

			if (opcion.equals(Constantes.GRS_SOLIC)) {
				solicitudesOT.setCFase(Constantes.FASE_INICIACION);
			} else if (opcion.equals(Constantes.GRS_MERIT)) {
				if ((solicitudesOT.getCEstado().equals(Constantes.ESTADO_ACEPTADO_VALUE)) && (solicitudesOT.getCConvocatoriaId() != 0L)) {
					OCAPConvocatoriasLN convoLN = new OCAPConvocatoriasLN(jcylUsuario);
					OCAPConvocatoriasOT convoOT = convoLN.buscarOCAPConvocatorias(solicitudesOT.getCConvocatoriaId());
					if ((convoOT.getFInicioEstudioCC() != null)
							&& (DateUtil.convertStringToDate(convoOT.getFInicioEstudioCC()).before(new Date()))) {
						request.setAttribute("masiva", Constantes.SI);
					}
				}
				solicitudesOT.setCFase(Constantes.FASE_MC);
			} else if (opcion.equals("recon")) {
				solicitudesOT.setCFase(Constantes.FASE_FIN);
			} else {
				solicitudesOT.setCFase(Constantes.FASE_VALIDACION_CC);
			}

			listadoSolicitudes = solicitudesLN.buscarGrs(primerRegistro, registrosPorPagina, solicitudesOT, false, opcion);
			int numSolicitudes = solicitudesLN.contarSolicitudesGrs(solicitudesOT, jcylUsuario);			
			
			session.setAttribute("numSolicitudes", new Integer(numSolicitudes));

			Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
			pagina.setElementos(listadoSolicitudes);
			pagina.setNRegistros(((Integer) session.getAttribute("numSolicitudes")).intValue());
			pagina.setRegistroActual(primerRegistro);
			pagina.setRegistrosPorPagina(registrosPorPagina);

			request.setAttribute("listados", pagina);

			OCAPConfigApp.logger.info(getClass().getName() + " buscarGrs: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irBuscarGrs");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward generarPDFGrs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		ArrayList listadoSolicitudes = null;
		String pathBase = null;
		boolean siAlega = true;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarPDFGrs: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPSolicitudesForm) request.getSession().getAttribute("OCAPSolicitudesFormBuscador");
				request.setAttribute("OCAPSolicitudesForm", form);
			} else {
				request.getSession().setAttribute("OCAPSolicitudesFormBuscador", form);
				session.setAttribute("iRegistro", 1 + "");
			}

			if ((jcylUsuario.getUser().getRol() != null) && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE))) {
				OCAPGerenciasLN gerenciaLN = new OCAPGerenciasLN(jcylUsuario);
				OCAPGerenciasOT gerenciaOT = gerenciaLN.buscaOCAPGerenciaLDAP(jcylUsuario.getUser().getD_gerencia());
				solicitudesOT.setCGerencia_id(gerenciaOT.getCGerenciaId());
			} else if ((((OCAPSolicitudesForm) form).getCGerencia_id() != null)
					&& (!((OCAPSolicitudesForm) form).getCGerencia_id().equals(""))) {
				solicitudesOT.setCGerencia_id(Long.parseLong(((OCAPSolicitudesForm) form).getCGerencia_id()));
			}

			solicitudesOT.setCEstado(((OCAPSolicitudesForm) form).getCEstado());

			if ((((OCAPSolicitudesForm) form).getCGrado_id() != null) && (!((OCAPSolicitudesForm) form).getCGrado_id().equals("")))
				solicitudesOT.setCGrado_id(Long.parseLong(((OCAPSolicitudesForm) form).getCGrado_id()));
			else if (((OCAPSolicitudesForm) form).getCGrado_id().equals("")) {
				solicitudesOT.setCGrado_id(0L);
			}
			if ((((OCAPSolicitudesForm) form).getCConvocatoriaId() != null)
					&& (!((OCAPSolicitudesForm) form).getCConvocatoriaId().equals("")))
				solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPSolicitudesForm) form).getCConvocatoriaId()));
			else if (((OCAPSolicitudesForm) form).getCConvocatoriaId().equals("")) {
				solicitudesOT.setCConvocatoriaId(0L);
			}

			String opcion = request.getParameter("opcion");

			if (opcion.equals(Constantes.GRS_MERIT)) {
				solicitudesOT.setCFase(Constantes.FASE_MC);
			} else if (opcion.equals(Constantes.GRS_SOLIC)) {
				solicitudesOT.setCFase(Constantes.FASE_INICIACION);
			} else {
				solicitudesOT.setCFase(Constantes.FASE_VALIDACION_CC);
				siAlega = false;
			}

			pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");

			if (opcion.equals(Constantes.GRS_SOLIC)) {
				listadoSolicitudes = solicitudesLN.buscarPDF(0, 0, solicitudesOT);
				if ((request.getParameter("CSV") != null) && (Constantes.SI.equals(request.getParameter("CSV"))))
					solicitudesLN.crearCSVListado(response, listadoSolicitudes, pathBase, ((OCAPSolicitudesForm) form).getCEstado(),
							Constantes.FASE_INICIACION, "", jcylUsuario);
				else
					solicitudesLN.crearInformeListado(response, listadoSolicitudes, pathBase, ((OCAPSolicitudesForm) form).getCEstado(),
							Constantes.FASE_INICIACION, "", "", jcylUsuario, null, solicitudesOT.getCConvocatoriaId());
			} else {
				listadoSolicitudes = solicitudesLN.buscarGrs(0, 0, solicitudesOT, false, opcion);
				solicitudesLN.crearInformeListadoGrs(response, listadoSolicitudes, pathBase, siAlega, solicitudesOT.getCEstado());
			}

			OCAPConfigApp.logger.info(getClass().getName() + " generarPDFGrs: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mensajeExito");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward generarPDFDinamicoGrs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		ArrayList listadoSolicitudes = new ArrayList();
		String pathBase = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarPDFDinamicoGrs: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				request.setAttribute("OCAPSolicitudesForm", form);
			} else {
				request.getSession().setAttribute("OCAPSolicitudesFormBuscador", form);
				session.setAttribute("iRegistro", 1 + "");
			}

			if ((((OCAPSolicitudesForm) form).getCGrado_id() != null) && (!((OCAPSolicitudesForm) form).getCGrado_id().equals("")))
				solicitudesOT.setCGrado_id(Long.parseLong(((OCAPSolicitudesForm) form).getCGrado_id()));
			else if (((OCAPSolicitudesForm) form).getCGrado_id().equals("")) {
				solicitudesOT.setCGrado_id(0L);
			}

			if ((((OCAPSolicitudesForm) form).getCConvocatoriaId() != null)
					&& (!((OCAPSolicitudesForm) form).getCConvocatoriaId().equals("")))
				solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPSolicitudesForm) form).getCConvocatoriaId()));
			else if (((OCAPSolicitudesForm) form).getCConvocatoriaId().equals("")) {
				solicitudesOT.setCConvocatoriaId(0L);
			}

			if (!Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())) {
				if ((((OCAPSolicitudesForm) form).getCGerenciaFiltro_id() != null)
						&& (!((OCAPSolicitudesForm) form).getCGerenciaFiltro_id().equals("")))
					solicitudesOT.setCGerenciaFiltro_id(Long.parseLong(((OCAPSolicitudesForm) form).getCGerenciaFiltro_id()));
				else if (((OCAPSolicitudesForm) form).getCGerenciaFiltro_id().equals(""))
					solicitudesOT.setCGerenciaFiltro_id(0L);
			} else {
				solicitudesOT.setCGerenciaFiltro_id(0L);
			}

			solicitudesOT.setCJuridico(((OCAPSolicitudesForm) form).getCJuridico());

			solicitudesOT.setCSitAdministrativaFiltro(((OCAPSolicitudesForm) form).getCSitAdministrativaAuxId());

			solicitudesOT.setCEstado(((OCAPSolicitudesForm) form).getCEstadoFiltro());

			solicitudesOT.setCEstadoHist(((OCAPSolicitudesForm) form).getCEstadoHistFiltro());

			solicitudesOT.setCProcedimientoFiltro(((OCAPSolicitudesForm) form).getCProcedimientoFiltro());

			if ((((OCAPSolicitudesForm) form).getBCSVSexo() == true) && (((OCAPSolicitudesForm) form).getBSexo() != null)
					&& (!((OCAPSolicitudesForm) form).getBSexo().equals(""))) {
				solicitudesOT.setBSexo(((OCAPSolicitudesForm) form).getBSexo());
			}

			if ((((OCAPSolicitudesForm) form).getBCategoria() == true) && (((OCAPSolicitudesForm) form).getCProfesional_id() != null)
					&& (!((OCAPSolicitudesForm) form).getCProfesional_id().equals(""))) {
				solicitudesOT.setCProfesional_id(Long.parseLong(((OCAPSolicitudesForm) form).getCProfesional_id()));
			}

			if ((((OCAPSolicitudesForm) form).getBEspecialidad() == true) && (((OCAPSolicitudesForm) form).getCEspec_id() != null)
					&& (!((OCAPSolicitudesForm) form).getCEspec_id().equals(""))) {
				solicitudesOT.setCEspec_id(Long.parseLong(((OCAPSolicitudesForm) form).getCEspec_id()));
			}

			if (((OCAPSolicitudesForm) form).getBEpr() == true) {
				DecimalFormat formato = new DecimalFormat("000000");
				if ((((OCAPSolicitudesForm) form).getCExp_id() != null) && (!((OCAPSolicitudesForm) form).getCExp_id().equals(""))) {
					((OCAPSolicitudesForm) form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(solicitudesOT.getCExp_id()));
				}

			}

			pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");

			listadoSolicitudes = solicitudesLN.buscarCSV(0, 0, solicitudesOT);

			for (int i = 0; i < listadoSolicitudes.size(); i++) {
				solicitudesOT = (OCAPSolicitudesOT) listadoSolicitudes.get(i);

				solicitudesOT.setBNifNie(((OCAPSolicitudesForm) form).getBNifNie());
				solicitudesOT.setBApellidos(((OCAPSolicitudesForm) form).getBApellidos());
				solicitudesOT.setBNombre(((OCAPSolicitudesForm) form).getBNombre());
				solicitudesOT.setBCSVSexo(((OCAPSolicitudesForm) form).getBCSVSexo());
				solicitudesOT.setBCategoria(((OCAPSolicitudesForm) form).getBCategoria());
				solicitudesOT.setBEspecialidad(((OCAPSolicitudesForm) form).getBEspecialidad());
				solicitudesOT.setBSituacionAdm(((OCAPSolicitudesForm) form).getBSituacionAdm());
				solicitudesOT.setBProcedimiento(((OCAPSolicitudesForm) form).getBProcedimiento());
				solicitudesOT.setBGerencia(((OCAPSolicitudesForm) form).getBGerencia());
				solicitudesOT.setBEstado(((OCAPSolicitudesForm) form).getBEstado());
				solicitudesOT.setBCausas(((OCAPSolicitudesForm) form).getBCausas());
				solicitudesOT.setBConvocatoria(((OCAPSolicitudesForm) form).getBConvocatoria());
				solicitudesOT.setBFechaSolic(((OCAPSolicitudesForm) form).getBFechaSolic());
				solicitudesOT.setBJuridico(((OCAPSolicitudesForm) form).getBJuridico());
				solicitudesOT.setBAnios(((OCAPSolicitudesForm) form).getBAnios());
				solicitudesOT.setBCentro(((OCAPSolicitudesForm) form).getBCentro());
				solicitudesOT.setBModalidad(((OCAPSolicitudesForm) form).getBModalidad());
				solicitudesOT.setBServicio(((OCAPSolicitudesForm) form).getBServicio());
				solicitudesOT.setBPuesto(((OCAPSolicitudesForm) form).getBPuesto());
				solicitudesOT.setBEpr(((OCAPSolicitudesForm) form).getBEpr());
				solicitudesOT.setDNombre(solicitudesOT.getDNombre() != null ? solicitudesOT.getDNombre().toUpperCase():null);
				solicitudesOT.setDApellido1(solicitudesOT.getDApellido1() != null ? solicitudesOT.getDApellido1().toUpperCase():null);
				solicitudesOT.setDApellido2(solicitudesOT.getDApellido2() != null ? solicitudesOT.getDApellido2().toUpperCase():null);
				solicitudesOT.setcDniEnmascarado(generarDniEnmascarado(solicitudesOT.getCDniReal()));
			}

			if ((request.getParameter("CSV") != null) && (Constantes.SI.equals(request.getParameter("CSV")))) {
				solicitudesLN.crearCSVDinamicoListado(response, listadoSolicitudes, pathBase, jcylUsuario);
			}

			OCAPConfigApp.logger.info(getClass().getName() + " generarPDFDinamicoGrs: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mensajeExito");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	private String generarDniEnmascarado(String cDniReal) {
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

	public ActionForward insertarAceptacionMasiva(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoSolicitudes = new ArrayList();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " insertarAceptacionMasiva: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			Date hoy = new Date();
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_COMPLETA_DATEUTIL);

			solicitudesOT.setCEstado(Constantes.ESTADO_ACEPTADO_VALUE);
			solicitudesOT.setCFase(Constantes.FASE_MC);

			listadoSolicitudes = solicitudesLN.buscarGrs(0, 0, solicitudesOT, true, null);

			for (int i = 0; i < listadoSolicitudes.size(); i++) {
				solicitudesOT = (OCAPSolicitudesOT) listadoSolicitudes.get(i);
				solicitudesLN.aceptarEvaluacionMC(solicitudesOT.getCExp_id(), Long.toString(solicitudesOT.getCConvocatoriaId()),
						jcylUsuario.getUser().getRol());
			}

			request.setAttribute("rutaVuelta", "OCAPSolicitudes.do?accion=buscarGrs&opcion=" + request.getParameter("opcion") + "&"
					+ Constantes.RECUPERAR_BUSQUEDA_ANTERIOR + "=" + Constantes.SI);

			OCAPConfigApp.logger.info(getClass().getName() + " insertarAceptacionMasiva: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mensajeExito");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward cambiarAceptado(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		int idExpediente = 0;
		String fw = "";
		ActionMessages messages = new ActionMessages();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " modificar: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();
			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);

			solicitudesOT.setCExp_id(Long.parseLong(request.getParameter("expediente")));

			solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);
			solicitudesOT.setAModificadoPor(((JCYLUsuario) request.getSession().getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO))
					.getUsuario().getC_usr_id());
			if ((solicitudesOT.getFInicio_mc() == null) && (solicitudesOT.getFInconf_solic() == null)) {
				
				idExpediente = solicitudesLN.cambiarAceptado(solicitudesOT, jcylUsuario);

				fw = "mensajeExito";
			} else if (solicitudesOT.getFInicio_mc() != null) {
				messages.add("dFaseMc", new ActionMessage("error.solicitudAceptadaMc"));
			} else {
				messages.add("dFaseAlega", new ActionMessage("error.solicitudAceptadaAlega"));
			}
			request.setAttribute("rutaVuelta", "OCAPSolicitudes.do?accion=buscar&opcion=" + request.getParameter("opcion") + "&"
					+ Constantes.RECUPERAR_BUSQUEDA_ANTERIOR + "=" + Constantes.SI);

			OCAPConfigApp.logger.info(getClass().getName() + " insertar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty()))) {
			return mapping.findForward(fw);
		}
		if ((errors != null) && (!errors.isEmpty())) {
			saveErrors(request, errors);

			return mapping.findForward("fallo");
		}
		saveMessages(request, messages);

		return mapping.findForward("fallo");
	}

	public ActionForward irCSVDinamico(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoGerencias = new ArrayList();
		ArrayList listadoCategorias = new ArrayList();
		ArrayList listadoGrados = new ArrayList();
		ArrayList listaEstados = new ArrayList();
		ArrayList listaEstadosExtendido = new ArrayList();

		ArrayList listaJuridico = new ArrayList();
		ArrayList listaSitAdmin = new ArrayList();
		ArrayList listadoProcedimientos = new ArrayList();
		ArrayList listaConvocatorias = new ArrayList();
		String vuelta = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irCSVDinamico: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			listadoGerencias = gerenciasLN.listadoOCAPGerencias();
			session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();

			OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
			listadoProcedimientos = procLN.listadoOCAPProcedimiento();

			session.setAttribute(Constantes.COMBO_PERSONAL, listadoProcedimientos);

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			listadoGrados = gradoLN.listarGrados();

			OCAPConvocatoriasLN convoLN = new OCAPConvocatoriasLN(jcylUsuario);
			listaConvocatorias = convoLN.listarConvocatorias();

			listaEstados = new ArrayList();

			OCAPEstadosLN estadosLN = new OCAPEstadosLN(jcylUsuario);
			listaEstados = estadosLN.listarEstados();

			session.setAttribute(Constantes.COMBO_ESTADOS, listaEstados);

			OCAPEstadosOT estadoCeroMeritos = new OCAPEstadosOT();
			estadoCeroMeritos.setBBorrado("N");
			estadoCeroMeritos.setcEstadoId(Constantes.ESTADO_CERO_MERITOS);
			estadoCeroMeritos.setDNombre(Constantes.ESTADO_CERO_MERITOS_NOMBRE);

			OCAPEstadosOT estadoProfNoHanAccedido = new OCAPEstadosOT();
			estadoProfNoHanAccedido.setBBorrado("N");
			estadoProfNoHanAccedido.setcEstadoId(Constantes.ESTADO_PROF_NO_ACCEDIDO);
			estadoProfNoHanAccedido.setDNombre(Constantes.ESTADO_PROF_NO_ACCEDIDO_NOMBRE);
			
			
			OCAPEstadosOT estadoProcesoAutoEvaluacion = new OCAPEstadosOT();
			estadoProcesoAutoEvaluacion.setBBorrado("N");
			estadoProcesoAutoEvaluacion.setcEstadoId(19);
			estadoProcesoAutoEvaluacion.setDNombre("Proceso autoevaluación");
			
			OCAPEstadosOT estadoFinAutoEvaluacion = new OCAPEstadosOT();
			estadoFinAutoEvaluacion.setBBorrado("N");
			estadoFinAutoEvaluacion.setcEstadoId(30);
			estadoFinAutoEvaluacion.setDNombre("Fin autoevaluación");
			
			OCAPEstadosOT estadoProcesoEvaluacion = new OCAPEstadosOT();
			estadoProcesoEvaluacion.setBBorrado("N");
			estadoProcesoEvaluacion.setcEstadoId(31);
			estadoProcesoEvaluacion.setDNombre("Proceso evaluación");
			
			OCAPEstadosOT estadoSinInformeCTE = new OCAPEstadosOT();
			estadoSinInformeCTE.setBBorrado("N");
			estadoSinInformeCTE.setcEstadoId(22);
			estadoSinInformeCTE.setDNombre("Sin informe CTE");
			
			OCAPEstadosOT estadoSinEvidenciasProfesioanles = new OCAPEstadosOT();
			estadoSinEvidenciasProfesioanles.setBBorrado("N");
			estadoSinEvidenciasProfesioanles.setcEstadoId(23);
			estadoSinEvidenciasProfesioanles.setDNombre("Sin evidencias profesionales");
			
			OCAPEstadosOT estadoPendienteAsignarEvaluador = new OCAPEstadosOT();
			estadoPendienteAsignarEvaluador.setBBorrado("N");
			estadoPendienteAsignarEvaluador.setcEstadoId(24);
			estadoPendienteAsignarEvaluador.setDNombre("Pendiente asignar evaluador");
			

			listaEstadosExtendido = new ArrayList();
			for (int i = 0; i < listaEstados.size(); i++) {
				listaEstadosExtendido.add(new OCAPEstadosOT());
				PropertyUtils.copyProperties(listaEstadosExtendido.get(i), listaEstados.get(i));
			}
			listaEstadosExtendido.add(0, estadoCeroMeritos);
			listaEstadosExtendido.add(1, estadoProfNoHanAccedido);
			listaEstadosExtendido.add(2, estadoProcesoAutoEvaluacion);
			listaEstadosExtendido.add(3, estadoFinAutoEvaluacion);
			listaEstadosExtendido.add(4, estadoProcesoEvaluacion);
			listaEstadosExtendido.add(5, estadoSinInformeCTE);
			listaEstadosExtendido.add(6, estadoSinEvidenciasProfesioanles);
			listaEstadosExtendido.add(7, estadoPendienteAsignarEvaluador);
			session.setAttribute(Constantes.COMBO_ESTADOS_EXTENDIDO, listaEstadosExtendido);

			OCAPRegimenJuridicoLN regJuridicoLN = new OCAPRegimenJuridicoLN(jcylUsuario);
			listaJuridico = regJuridicoLN.listadoOCAPRegimenJuridico();

			session.setAttribute(Constantes.COMBO_REGIMEN, listaJuridico);

			OCAPSit_AdministrativaLN sit_AdministrativaLN = new OCAPSit_AdministrativaLN(jcylUsuario);
			listaSitAdmin = sit_AdministrativaLN.listadoOCAPSit_Administrativa();

			session.setAttribute(Constantes.COMBO_SIT_ADMIN, listaSitAdmin);

			session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, listadoGrados);
			session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
			session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listaConvocatorias);

			if (request.getParameter("opcion").equals("listadoLDAP")) {
				((OCAPSolicitudesForm) form).setBNifNie(true);
				((OCAPSolicitudesForm) form).setBNombre(true);
				((OCAPSolicitudesForm) form).setBApellidos(true);
				((OCAPSolicitudesForm) form).setBGerencia(true);
				((OCAPSolicitudesForm) form).setBCentro(true);
				((OCAPSolicitudesForm) form).setBServicio(true);
				((OCAPSolicitudesForm) form).setBPuesto(true);
			} else if (request.getParameter("opcion").equals("listadoFQS")) {
				((OCAPSolicitudesForm) form).setBNifNie(true);
				((OCAPSolicitudesForm) form).setBNombre(true);
				((OCAPSolicitudesForm) form).setBApellidos(true);
				((OCAPSolicitudesForm) form).setBEpr(true);
				((OCAPSolicitudesForm) form).setBGerencia(true);
				((OCAPSolicitudesForm) form).setBCentro(true);
				((OCAPSolicitudesForm) form).setBServicio(true);
				((OCAPSolicitudesForm) form).setBPuesto(true);
				((OCAPSolicitudesForm) form).setBProcedimiento(true);
				((OCAPSolicitudesForm) form).setBCategoria(true);
				((OCAPSolicitudesForm) form).setBEspecialidad(true);
			} else if (request.getParameter("opcion").equals("listadoGRS")) {
				((OCAPSolicitudesForm) form).setBNifNie(true);
				((OCAPSolicitudesForm) form).setBNombre(true);
				((OCAPSolicitudesForm) form).setBApellidos(true);
				((OCAPSolicitudesForm) form).setBEpr(true);
				((OCAPSolicitudesForm) form).setBGerencia(true);
				((OCAPSolicitudesForm) form).setBCentro(true);
				((OCAPSolicitudesForm) form).setBServicio(true);
				((OCAPSolicitudesForm) form).setBPuesto(true);
				((OCAPSolicitudesForm) form).setBProcedimiento(true);
				((OCAPSolicitudesForm) form).setBCategoria(true);
				((OCAPSolicitudesForm) form).setBEspecialidad(true);
			}

			((OCAPSolicitudesForm) form).setjspInicio(true);

			OCAPConfigApp.logger.info(getClass().getName() + " irCSVDinamico: Fin");

			if ((jcylUsuario.getUser().getRol() != null) && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_GRS)))
				vuelta = "irCSVDinamico";
			else
				vuelta = "irCSVDinamicoFQS";
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(vuelta);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irFinalizarEvaluacionFinPlazo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoGrados = new ArrayList();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irFinalizarEvaluacionFinPlazo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			listadoGrados = gradoLN.listarGrados();

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			ArrayList listadoConvocatorias = convocatoriasLN.listarConvocatorias();

			session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, listadoGrados);
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);

			OCAPConfigApp.logger.info(getClass().getName() + " irFinalizarEvaluacionFinPlazo: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irFinalizarEvaluacionFinPlazo");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irEliminarItinerarioAsignadoUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoGrados = new ArrayList();
		ArrayList listadoConvocatorias = new ArrayList();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irEliminarItinerarioAsignadoUsuario: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			listadoGrados = gradoLN.listarGrados();

			session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, listadoGrados);

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			listadoConvocatorias = convocatoriasLN.listarConvocatorias();
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);

			OCAPConfigApp.logger.info(getClass().getName() + " irEliminarItinerarioAsignadoUsuario: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irEliminarItinerarioAsignadoUsuario");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward eliminarItinerarioAsignadoUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		OCAPUsuariosLN usuarioLN = null;
		OCAPUsuariosOT usuarioOT = new OCAPUsuariosOT();
		OCAPSolicitudesLN solicitudesLN = null;

		OCAPExpedienteCarreraLN expedienteCarreraLN = null;

		long expId = 0L;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " eliminarItinerarioAsignadoUsuario: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			request.setAttribute("rutaVuelta", "OCAPSolicitudes.do?accion=irEliminarItinerarioAsignadoUsuario");
			OCAPConvocatoriasLN convoLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT convoOT = convoLN
					.buscarOCAPConvocatorias(Long.parseLong(((OCAPSolicitudesForm) form).getCConvocatoriaId()));

			usuarioLN = new OCAPUsuariosLN(jcylUsuario);
			usuarioOT = usuarioLN.buscarOCAPUsuariosPorNIF(((OCAPSolicitudesForm) form).getCDni().toUpperCase());
			if ((usuarioOT == null) || (usuarioOT.getCUsrId() == null)) {
				return mapping.findForward("irNoExisteUsuario");
			}
			expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			expId = expedienteCarreraLN.buscarCExpIdPorUsuarioConvocatoria(usuarioOT.getCUsrId().longValue(),
					Long.parseLong(((OCAPSolicitudesForm) form).getCConvocatoriaId()));
			if (expId == 0L) {
				return mapping.findForward("irNoExisteUsuario");
			}
			expedienteCarreraLN.eliminarItinerarioAsignado(expId, jcylUsuario);

			OCAPConfigApp.logger.info(getClass().getName() + " eliminarItinerarioAsignadoUsuario: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mensajeExito");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irModificarCuestionarioObservEvidencia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		OCAPCuestionariosLN cuestionariosLN = null;
		OCAPSolicitudesForm solicitudesForm = (OCAPSolicitudesForm) form;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCuestionarioObservEvidencia: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);

			if (solicitudesForm.getCCuestionarioId() != 0L) {
				OCAPCuestionariosOT cuestionarioOT = cuestionariosLN.buscarCuestionario(solicitudesForm.getCCuestionarioId());
				solicitudesForm.setDObservacionesEvidencia(cuestionarioOT.getDObservacionesEvidencia());
			}

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCuestionarioObservEvidencia: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irModificarCuestionarioObservEvidencia");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward modificarCuestionarioObservEvidencia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		OCAPCuestionariosLN cuestionariosLN = null;
		OCAPSolicitudesForm solicitudesForm = (OCAPSolicitudesForm) form;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " modificarCuestionarioObservEvidencia: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);

			if (solicitudesForm.getCCuestionarioId() != 0L) {
				OCAPCuestionariosOT cuestionarioOT = new OCAPCuestionariosOT();
				cuestionarioOT.setCCuestionarioId(solicitudesForm.getCCuestionarioId());
				cuestionarioOT.setDObservacionesEvidencia(solicitudesForm.getDObservacionesEvidencia());
				cuestionariosLN.guardarCuestionario(cuestionarioOT);
				request.setAttribute("rutaVuelta", "OCAPSolicitudes.do?accion=irModificarCuestionarioObservEvidencia");
			}

			OCAPConfigApp.logger.info(getClass().getName() + " modificarCuestionarioObservEvidencia: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mensajeExito");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward generarPDFReconociGrado(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		String pathBase = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarPDFReconociGrado: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			request.getParameter("expId");
			
			solicitudesOT = obtenerDatosSolicitudCompleta(Long.parseLong(request.getParameter("expId")),jcylUsuario);

			pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");

			solicitudesLN.crearInformeReconociGrado(jcylUsuario, response, solicitudesOT, pathBase);

			OCAPConfigApp.logger.info(getClass().getName() + " generarPDFReconociGrado: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mensajeExito");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	private OCAPSolicitudesOT obtenerDatosSolicitudCompleta(long idExpediente, JCYLUsuario jcylUsuario) throws SQLException, Exception {
		OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();
		OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
		
		solicitudesOT.setCExp_id(idExpediente);
		solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);
		
		OCAPNuevaSolicitudLN nuevaSolicitudLN = new OCAPNuevaSolicitudLN(jcylUsuario);
		OCAPSolicitudesOT solicitudUsuarioOT = nuevaSolicitudLN.detalleSolicitud(solicitudesOT.getCSolicitudId());
		solicitudesOT.setDNombre(solicitudUsuarioOT.getDNombre());
		solicitudesOT.setDApellido1(solicitudUsuarioOT.getDApellido1());
		solicitudesOT.setDApellido2(solicitudUsuarioOT.getDApellido2());
		
		OCAPGerenciasOT gerenciasOT = new OCAPGerenciasOT();
		OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
		gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
		gerenciasOT = gerenciasLN.buscaOCAPGerenciaLDAP("GRS");

		solicitudesOT.setFSesion(new Date());
		solicitudesOT.setDGerente(gerenciasOT.getDGerente());

		if ((solicitudesOT.getDEspec_nombre() != null) && (!solicitudesOT.getDEspec_nombre().equals(""))) {
			solicitudesOT.setDProfesional_nombre(solicitudesOT.getDProfesional_nombre() + "/" + solicitudesOT.getDEspec_nombre() + "");
		}
		return solicitudesOT;
	}

	public ActionForward generarPDFIndiviAccesoGrado(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		String pathBase = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarPDFIndiviAccesoGrado: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();
			solicitudesOT.setCExp_id(Long.parseLong(((OCAPSolicitudesForm) form).getCExp_id()));
			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);

			solicitudesOT.setFSesion(new Date());
			solicitudesOT.setDNombreApellidos(solicitudesOT.getDNombre() + " " + solicitudesOT.getDApellido1());
			if (Constantes.C_SIT_ADM_AUX_ACTIVO_COD.equals(solicitudesOT.getCSitAdministrativaAuxId())) {
				solicitudesOT.setDSitAdministrativaAux_nombre("Activo");
			} else {
				solicitudesOT.setDSitAdministrativaAuxOtros(((OCAPSolicitudesForm) form).getDSitAdministrativaAuxOtros());
				solicitudesOT.setDSitAdministrativaAux_nombre("Otras: "
						+ (solicitudesOT.getDSitAdministrativaAuxOtros() == null ? "" : solicitudesOT.getDSitAdministrativaAuxOtros()));
			}

			if (Constantes.SI.equals(solicitudesOT.getBReconocimientoGrado()))
				solicitudesOT.setDAcreditaCumplimiento(Constantes.SI_TEXTO_min);
			else {
				solicitudesOT.setDAcreditaCumplimiento(Constantes.NO_TEXTO_min);
			}

			pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");

			solicitudesLN.crearInformeIndiviAccesoGrado(jcylUsuario, response, solicitudesOT, pathBase);

			OCAPConfigApp.logger.info(getClass().getName() + " generarPDFIndiviAccesoGrado: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mensajeExito");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward aceptarGrado(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		String fw = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " aceptarGrado: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedienteCarreraLN expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			OCAPExpedientecarreraOT expCarreraOT = new OCAPExpedientecarreraOT();

			expCarreraOT.setCExpId(new Long(((OCAPSolicitudesForm) form).getCExp_id()));
			expCarreraOT.setBReconocimientoGrado(((OCAPSolicitudesForm) form).getBReconocimientoGrado());
			expCarreraOT.setFFinCc(new Date());
			expCarreraOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());

			if (Constantes.SI.equals(expCarreraOT.getBReconocimientoGrado()))
				expCarreraOT.setCEstadoId(12);
			else {
				expCarreraOT.setCEstadoId(11);
			}
			expCarreraLN.modificacionOCAPExpedientecarrera(expCarreraOT, false);

			request.setAttribute("rutaVuelta", "OCAPSolicitudes.do?accion=irDetalle&opcion=" + request.getParameter("opcion") + "&CExp_id="
					+ ((OCAPSolicitudesForm) form).getCExp_id() + " ");
			fw = "mensajeExito";

			OCAPConfigApp.logger.info(getClass().getName() + " aceptarGrado: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(fw);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}
	
	public ActionForward generarInformesReconocimientoSeleccionados(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionErrors errors = new ActionErrors();
		
		try {
			HttpSession session = request.getSession();
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			long[] expedientesSeleccionados = ((OCAPSolicitudesForm) form).getListaExpedientes();
			List<OCAPSolicitudesOT> listaSolicitudes = new ArrayList<OCAPSolicitudesOT>();

			if (expedientesSeleccionados != null && expedientesSeleccionados.length > 0) {
				for (int i = 0; i < expedientesSeleccionados.length; i++) {
					OCAPSolicitudesOT solicitud = obtenerDatosSolicitudCompleta(expedientesSeleccionados[i],
							jcylUsuario);
					listaSolicitudes.add(solicitud);
				}
				String pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");
				solicitudesLN.crearInformeReconociGradoTotal(jcylUsuario, response, listaSolicitudes, pathBase);

			}
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}
		return mapping.findForward("");

	}
}
