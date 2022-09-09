package es.jcyl.fqs.ocap.actions.solicitudes;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;

import es.jcyl.cf.seguridad.util.Usuario;
import es.jcyl.fqs.ocap.actionforms.solicitudes.OCAPNuevaSolicitudForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
import es.jcyl.fqs.ocap.ln.centroTrabajo.OCAPCentroTrabajoLN;
import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
import es.jcyl.fqs.ocap.ln.documentos.OCAPDocumentosLN;
import es.jcyl.fqs.ocap.ln.especialidades.OCAPEspecialidadesLN;
import es.jcyl.fqs.ocap.ln.estados.OCAPEstadosLN;
import es.jcyl.fqs.ocap.ln.estadosCuestionario.OCAPEstadosCuestionarioLN;
import es.jcyl.fqs.ocap.ln.estatutario.OCAPEstatutarioLN;
import es.jcyl.fqs.ocap.ln.evaluadores.OCAPEvaluadoresLN;
import es.jcyl.fqs.ocap.ln.expedienteCarrera.OCAPExpedienteCarreraLN;
import es.jcyl.fqs.ocap.ln.expedientesexclusion.OCAPExpedientesExclusionLN;
import es.jcyl.fqs.ocap.ln.gerencias.OCAPGerenciasLN;
import es.jcyl.fqs.ocap.ln.grado.OCAPGradoLN;
import es.jcyl.fqs.ocap.ln.itinerarios.OCAPItinerariosLN;
import es.jcyl.fqs.ocap.ln.localidades.OCAPLocalidadesLN;
import es.jcyl.fqs.ocap.ln.modalidadAnterior.OCAPModalidadAnteriorLN;
import es.jcyl.fqs.ocap.ln.motExclusion.OCAPMotExclusionLN;
import es.jcyl.fqs.ocap.ln.procedimiento.OCAPProcedimientoLN;
import es.jcyl.fqs.ocap.ln.provincias.OCAPProvinciasLN;
import es.jcyl.fqs.ocap.ln.regimenJuridico.OCAPRegimenJuridicoLN;
import es.jcyl.fqs.ocap.ln.revisiones.OCAPRevisionesLN;
import es.jcyl.fqs.ocap.ln.sit_Administrativa.OCAPSit_AdministrativaLN;
import es.jcyl.fqs.ocap.ln.solicitudes.OCAPNuevaSolicitudLN;
import es.jcyl.fqs.ocap.ln.solicitudes.OCAPSolicitudesLN;
import es.jcyl.fqs.ocap.ln.subsanaciones.OCAPSubsanacionesLN;
import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
import es.jcyl.fqs.ocap.ot.categProfesionales.OCAPCategProfesionalesOT;
import es.jcyl.fqs.ocap.ot.centroTrabajo.OCAPCentroTrabajoOT;
import es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT;
import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
import es.jcyl.fqs.ocap.ot.especialidades.OCAPEspecialidadesOT;
import es.jcyl.fqs.ocap.ot.estados.OCAPEstadosOT;
import es.jcyl.fqs.ocap.ot.estatutario.OCAPEstatutarioOT;
import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;
import es.jcyl.fqs.ocap.ot.expedientesexclusion.OCAPExpedientesExclusionOT;
import es.jcyl.fqs.ocap.ot.gerencias.OCAPGerenciasOT;
import es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT;
import es.jcyl.fqs.ocap.ot.itinerarios.OCAPItinerariosOT;
import es.jcyl.fqs.ocap.ot.localidades.OCAPLocalidadesOT;
import es.jcyl.fqs.ocap.ot.modalidadAnterior.OCAPModalidadAnteriorOT;
import es.jcyl.fqs.ocap.ot.procedimiento.OCAPProcedimientoOT;
import es.jcyl.fqs.ocap.ot.provincias.OCAPProvinciasOT;
import es.jcyl.fqs.ocap.ot.revisiones.OCAPRevisionesOT;
import es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.Pagina;
import es.jcyl.fqs.ocap.util.TrataError;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;

public class OCAPNuevaSolicitudAction extends OCAPGenericAction {
	private static final String ORDEN_GERENCIA = "G";
	private static final String ORDEN_ALFABETICO = "A";
	private static final String TIPO_CC = "CC";
	private static final String TIPO_MC = "MC";
	private boolean reasociarGrs = false;

	public ActionForward irInsertar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoProcedimientos = null;
		ArrayList listadoSit_Administrativas = null;
		ArrayList listadoRegimenJuridico = null;
		ArrayList listadoCategorias = null;
		ArrayList listadoEspecialidades = null;
		ArrayList listadoGrados = null;
		ArrayList listadoGerencias = null;
		ArrayList listadoCentros = null;
		ArrayList listadoProvincias = null;
		ArrayList listadoLocalidades = null;
		ArrayList listadoModAnterior = null;
		boolean desdeModificar = false;
		if((request.getParameter("desdeModificar") != null) &&((String)request.getParameter("desdeModificar")).equals("true")){
			desdeModificar = true;
		}else{
			desdeModificar = false;
		}

		String dni = null;

		OCAPUsuariosOT usuOT = null;

		ArrayList listaVinculos = null;
		ArrayList listaTodasProvincias = null;
		ArrayList listadoConvocatorias = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			ArrayList solicitudesUsrConv = null;
					
			if (Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol()) && !desdeModificar) {
				OCAPSolicitudesLN solicLN = new OCAPSolicitudesLN(jcylUsuario);
								
				solicitudesUsrConv = solicLN.existeSolicParaUsrYConv(jcylUsuario, 
						jcylUsuario.getParametrosUsuario()!=null && 
						jcylUsuario.getParametrosUsuario().get("PARAM_CONVOCATORIA_USUARIO")!=null?
								(String)jcylUsuario.getParametrosUsuario().get("PARAM_CONVOCATORIA_USUARIO"):
								((OCAPNuevaSolicitudForm) form).getCConvocatoriaId(),
						((OCAPNuevaSolicitudForm) form));
				((OCAPNuevaSolicitudForm) form).setbExisteSolicParaNifConv( 
						(solicitudesUsrConv==null || solicitudesUsrConv.size()<=0)?false:true);
				
				if(solicitudesUsrConv==null || solicitudesUsrConv.size()<=0){
					((OCAPNuevaSolicitudForm) form).setDApellido1(
							((OCAPNuevaSolicitudForm) form).getDApellido1()!=null?
									((OCAPNuevaSolicitudForm) form).getDApellido1():
									jcylUsuario.getUser().getDApell());
					((OCAPNuevaSolicitudForm) form).setDNombre(
							((OCAPNuevaSolicitudForm) form).getDNombre()!=null?
									((OCAPNuevaSolicitudForm) form).getDNombre():
									jcylUsuario.getUser().getDNom());
					dni = jcylUsuario.getUser().getC_usr_id();
					((OCAPNuevaSolicitudForm) form).setCDni(jcylUsuario.getUser().getC_usr_id());
	
					if (Pattern.matches(".*[a-zA-Z]{2}", dni))
						((OCAPNuevaSolicitudForm) form).setCDniReal(dni.substring(0, dni.length() - 1));
					else {
						((OCAPNuevaSolicitudForm) form).setCDniReal(dni);
					}
	
					if ((((OCAPNuevaSolicitudForm) form).getDCorreoelectronico() == null)
							&& (jcylUsuario.getUser().getD_correo() != null)) {
						((OCAPNuevaSolicitudForm) form).setDCorreoelectronico(jcylUsuario.getUser().getD_correo());
					}
					if (((OCAPNuevaSolicitudForm) form).getNTelefono1() == null) {
						if (jcylUsuario.getUser().getD_telefono() != null)
							((OCAPNuevaSolicitudForm) form).setNTelefono1(jcylUsuario.getUser().getD_telefono());
						else if (jcylUsuario.getUser().getD_movil() != null) {
							((OCAPNuevaSolicitudForm) form).setNTelefono1(jcylUsuario.getUser().getD_movil());
						}
					}

					OCAPUsuariosLN usuarioLN = new OCAPUsuariosLN(jcylUsuario);
					usuOT = usuarioLN.buscarOCAPUsuariosPorNIF(jcylUsuario.getUser().getC_usr_id());
	
					if (usuOT != null) {
						if ((((OCAPNuevaSolicitudForm) form).getDCorreoelectronico() == null)
								&& (usuOT.getDCorreoelectronico() != null))
							((OCAPNuevaSolicitudForm) form).setDCorreoelectronico(usuOT.getDCorreoelectronico());
						if ((((OCAPNuevaSolicitudForm) form).getNTelefono1() == null) && (usuOT.getNTelefono1() != null)
								&& (usuOT.getNTelefono1().intValue() != 0))
							((OCAPNuevaSolicitudForm) form).setNTelefono1(usuOT.getNTelefono1().toString());
						if ((((OCAPNuevaSolicitudForm) form).getBSexo() == null) && (usuOT.getBSexo() != null)) {
							((OCAPNuevaSolicitudForm) form).setBSexo(usuOT.getBSexo());
						}
	
					}
				}
				
				request.setAttribute("esUsuario", Constantes.SI);
			}else if(Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())){
				request.setAttribute("esUsuario", Constantes.SI);
			}else {
				request.setAttribute("esUsuario", Constantes.NO);
			}

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			listadoGrados = gradoLN.listarGrados();

			if ((listadoGrados == null) || (listadoGrados.size() == 0)) {
				return mapping.findForward("irNoExistenConvocatoriasSolic");
			}
			
			
			
			//OCAP-1528
			if (!Utilidades.isNullOrIsEmpty(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId())) {
				OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
				ArrayList listadoGradosConvocatoria = convocatoriasLN.consultaGradoDeConvocatoria(Integer.parseInt(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));

				if(listadoGradosConvocatoria == null || listadoGradosConvocatoria.isEmpty()) {
					//Si entramos aquí es porque la convocatoria no tiene asociado ningun grado concreto.
					//En este caso, metemos todos los grados posibles para que el usuario seleccione
					listadoGradosConvocatoria = gradoLN.listarGrados();
				}
				
				session.setAttribute(Constantes.COMBO_GRADOS_ALTA, listadoGradosConvocatoria);
				
			} else {
				session.setAttribute(Constantes.COMBO_GRADOS_ALTA, listadoGrados);
			}
			
			

			if (session.getAttribute(Constantes.COMBO_TODAS_PROVINCIAS) == null) {
				OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
				listaTodasProvincias = provinciasLN.listarProvincias();
				session.setAttribute(Constantes.COMBO_TODAS_PROVINCIAS, listaTodasProvincias);
			}

			if (session.getAttribute(Constantes.COMBO_PERSONAL) == null) {
				OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
				listadoProcedimientos = procLN.listadoOCAPProcedimiento();
				session.setAttribute(Constantes.COMBO_PERSONAL, listadoProcedimientos);
			}

			if (session.getAttribute(Constantes.COMBO_REGIMEN_JURIDICO) == null) {
				OCAPRegimenJuridicoLN regJuridicoLN = new OCAPRegimenJuridicoLN(jcylUsuario);
				listadoRegimenJuridico = regJuridicoLN.listadoOCAPRegimenJuridico();
				session.setAttribute(Constantes.COMBO_REGIMEN_JURIDICO, listadoRegimenJuridico);
			}

			if (session.getAttribute(Constantes.COMBO_SIT_ADMIN) == null) {
				OCAPSit_AdministrativaLN sit_AdministrativaLN = new OCAPSit_AdministrativaLN(jcylUsuario);
				listadoSit_Administrativas = sit_AdministrativaLN.listadoOCAPSit_Administrativa();
				session.setAttribute(Constantes.COMBO_SIT_ADMIN, listadoSit_Administrativas);
			}

			if (session.getAttribute(Constantes.COMBO_PROVINCIAS) == null) {
				OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
				listadoProvincias = provinciasLN.listarProvinciasComunidad("CL");
				session.setAttribute(Constantes.COMBO_PROVINCIAS, listadoProvincias);
			}

			if (session.getAttribute(Constantes.COMBO_MOD_ANTERIOR) == null) {
				OCAPModalidadAnteriorLN modalidadAnteriorLN = new OCAPModalidadAnteriorLN(jcylUsuario);
				listadoModAnterior = modalidadAnteriorLN.listarModalidadAnterior();
				session.setAttribute(Constantes.COMBO_MOD_ANTERIOR, listadoModAnterior);
			}

			if ((((OCAPNuevaSolicitudForm) form).getCProvincia_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCProvincia_id().equals(""))) {
				OCAPLocalidadesLN localidadesLN = new OCAPLocalidadesLN(jcylUsuario);
				listadoLocalidades = localidadesLN
						.listarLocalidadesProvincia(((OCAPNuevaSolicitudForm) form).getCProvincia_id());
				session.setAttribute(Constantes.COMBO_LOCALIDADES, listadoLocalidades);
			} else {
				session.setAttribute(Constantes.COMBO_LOCALIDADES, new ArrayList());
			}

			if ((((OCAPNuevaSolicitudForm) form).getCJuridicoId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCJuridicoId().equals(""))) {
				OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
				listadoCategorias = categProfesionalesLN
						.listarCategoriasRJ(((OCAPNuevaSolicitudForm) form).getCJuridicoId());
				session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
			} else {
				session.setAttribute(Constantes.COMBO_CATEGORIA, new ArrayList());
			}

			OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
			if ((((OCAPNuevaSolicitudForm) form).getCProfesional_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCProfesional_id().equals(""))) {
				listadoEspecialidades = especialidadesLN
						.listarEspecialidades(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesional_id()));
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
			} else if ((((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id().equals(""))) {
				listadoEspecialidades = especialidadesLN
						.listarEspecialidades(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id()));
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
			} else {
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
			}

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			listadoConvocatorias = convocatoriasLN.listarConvocatorias();

			if (jcylUsuario.getUser().getRol() != null
					&& (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE))) {
				session.setAttribute(Constantes.COMBO_CONVOCATORIAS, convocatoriasLN.listarConvocatoriasEdicionPgp());

			} else {
				session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);
			}
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS_ALTA, convocatoriasLN.listarConvocatoriasActivasAlta());
			((OCAPNuevaSolicitudForm) form).setAccionEnviar("Registrar");

			if (((OCAPNuevaSolicitudForm) form).getCConvocatoriaId() == null) {
				((OCAPNuevaSolicitudForm) form)
						.setCConvocatoriaId(jcylUsuario.getParametrosUsuario().get("PARAM_CONVOCATORIA_USUARIO") != null
								? (String) jcylUsuario.getParametrosUsuario().get("PARAM_CONVOCATORIA_USUARIO") : null);
			}

			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_COMPLETA_DATEUTIL);
			Date hoy = new Date();
			
			if(solicitudesUsrConv==null || solicitudesUsrConv.size()<=0){
				((OCAPNuevaSolicitudForm) form).setFRegistro_solic(df.format(hoy));
			}

			if ((jcylUsuario.getUser().getRol() != null)
					&& ((jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE))
							|| (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIOS)))) {
				Map parametros = jcylUsuario.getParametrosUsuario();
				OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
				OCAPGerenciasOT gerenciaOT = null;

				if(((OCAPNuevaSolicitudForm) form).getCProvCarreraActualId()!=null &&  !((OCAPNuevaSolicitudForm) form).getCProvCarreraActualId().equalsIgnoreCase("") ){
					listadoGerencias = gerenciasLN.listarGerencias(
							((OCAPNuevaSolicitudForm) form).getCProvCarreraActualId(),null);
					gerenciaOT = gerenciasLN.buscarOCAPGerencias(
							Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGerenciaActualId()));
				} else {
					listadoGerencias = gerenciasLN.listarGerencias((String) parametros.get("PARAM_PROV"),
							(String) parametros.get("PARAM_TIPO_GERENCIA"));
					gerenciaOT = gerenciasLN.buscaOCAPGerenciaLDAP(jcylUsuario.getUser().getD_gerencia());
				}

				OCAPCentroTrabajoLN centroTrabajoLN = new OCAPCentroTrabajoLN(jcylUsuario);
				listadoCentros = centroTrabajoLN.listarCentroTrabajo(String.valueOf(gerenciaOT.getCGerenciaId()), null);

				if(solicitudesUsrConv==null || solicitudesUsrConv.size()<=0){
					((OCAPNuevaSolicitudForm) form).setCProvCarreraActualId(gerenciaOT.getCProvinciaId());
					((OCAPNuevaSolicitudForm) form).setDProvCarreraActual(gerenciaOT.getDProvinciaNombre());
					((OCAPNuevaSolicitudForm) form).setCGerenciaActualId(String.valueOf(gerenciaOT.getCGerenciaId()));
					((OCAPNuevaSolicitudForm) form).setDGerenciaActual(gerenciaOT.getDNombre());
				}
				session.setAttribute(Constantes.COMBO_GERENCIA_ACTUAL, listadoGerencias);
				session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO_CARRERA_ACTUAL, listadoCentros);

				if ((((OCAPNuevaSolicitudForm) form).getCProvinciaCarrera_id() == null)
						|| ("".equals(((OCAPNuevaSolicitudForm) form).getCProvinciaCarrera_id()))
						|| (((OCAPNuevaSolicitudForm) form).getCProvinciaCarrera_id()
								.equals(gerenciaOT.getCProvinciaId()))) {
					if(solicitudesUsrConv==null || solicitudesUsrConv.size()<=0){
						((OCAPNuevaSolicitudForm) form).setCProvinciaCarrera_id(gerenciaOT.getCProvinciaId());
					}
				} else {
					listadoGerencias = gerenciasLN
							.listarGerenciasSolicitud(((OCAPNuevaSolicitudForm) form).getCProvinciaCarrera_id());
				}

				if ((((OCAPNuevaSolicitudForm) form).getCGerencia_id() == null)
						|| ("".equals(((OCAPNuevaSolicitudForm) form).getCGerencia_id()))
						|| (((OCAPNuevaSolicitudForm) form).getCGerencia_id()
								.equals(String.valueOf(gerenciaOT.getCGerenciaId())))) {
					if(solicitudesUsrConv==null || solicitudesUsrConv.size()<=0){
						((OCAPNuevaSolicitudForm) form).setCGerencia_id(String.valueOf(gerenciaOT.getCGerenciaId()));
					}
				} else {
					listadoCentros = centroTrabajoLN
							.listarCentroTrabajo(((OCAPNuevaSolicitudForm) form).getCGerencia_id(), null);
				}

				session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);
				session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO_CARRERA, listadoCentros);
			} else {
				session.setAttribute(Constantes.COMBO_GERENCIA, new ArrayList());
				session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO_CARRERA, new ArrayList());
				session.setAttribute(Constantes.COMBO_GERENCIA_ACTUAL, new ArrayList());
				session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO_CARRERA_ACTUAL, new ArrayList());
			}

			request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);
			request.setAttribute("tipoAccionBis", Constantes.SI);
			request.setAttribute("primeraCarga", Constantes.SI);

			if (session.getAttribute(Constantes.COMBO_VINCULOS) == null) {
				OCAPNuevaSolicitudLN solicitudesLN = new OCAPNuevaSolicitudLN(jcylUsuario);
				listaVinculos = solicitudesLN.listaVinculos();
				session.setAttribute(Constantes.COMBO_VINCULOS, listaVinculos);
			}

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irNuevaInsertar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irDetalleAlta(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irDetalleAlta: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();

			OCAPProvinciasLN provinciaLN = new OCAPProvinciasLN(jcylUsuario);
			OCAPProvinciasOT provinciaOT = null;
			provinciaOT = provinciaLN.buscarProvincia(((OCAPNuevaSolicitudForm) form).getCProvincia_id());

			((OCAPNuevaSolicitudForm) form).setDProvincia(Cadenas.capitalizar(provinciaOT.getDProvincia()));

			OCAPLocalidadesLN localidadLN = new OCAPLocalidadesLN(jcylUsuario);
			OCAPLocalidadesOT localidadOT = null;
			localidadOT = localidadLN.buscarLocalidad(((OCAPNuevaSolicitudForm) form).getCLocalidad_id());

			((OCAPNuevaSolicitudForm) form).setDLocalidad((localidadOT.getDLocalidad().toUpperCase()));

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			OCAPGradoOT gradoOT = null;
			if ((((OCAPNuevaSolicitudForm) form).getCGrado_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCGrado_id().equals("")))
				gradoOT = gradoLN.buscarOCAPGrado(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGrado_id()));
			else {
				gradoOT = gradoLN.buscarOCAPGrado(expedienteCarreraOT.getCGradoId().longValue());
			}
			((OCAPNuevaSolicitudForm) form).setDGrado_des(gradoOT.getDDescripcion());

			OCAPModalidadAnteriorLN modalidadAnteriorLN = new OCAPModalidadAnteriorLN(jcylUsuario);
			OCAPModalidadAnteriorOT modalidadAnteriorOT = null;
			if ((((OCAPNuevaSolicitudForm) form).getCModAnteriorId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCModAnteriorId().equals(""))) {
				modalidadAnteriorOT = modalidadAnteriorLN
						.buscarOCAPModalidad(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCModAnteriorId()));
			}
			((OCAPNuevaSolicitudForm) form).setDModAnterior(modalidadAnteriorOT.getDDescripcion());
			((OCAPNuevaSolicitudForm) form).setCGradoAnteriorId(((OCAPNuevaSolicitudForm) form).getCGradoAnteriorId());

			OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
			OCAPProcedimientoOT procOT = null;
			if ((((OCAPNuevaSolicitudForm) form).getCProcedimientoId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCProcedimientoId().equals("")))
				procOT = procLN
						.buscarOCAPProcedimiento(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProcedimientoId()));
			else {
				procOT = procLN.buscarOCAPProcedimiento(Long.parseLong(expedienteCarreraOT.getCProcedimientoId()));
			}
			((OCAPNuevaSolicitudForm) form).setDProcedimiento(procOT.getDNombre());

			if ((((OCAPNuevaSolicitudForm) form).getCSitAdministrativaId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCSitAdministrativaId().equals("6"))) {
				((OCAPNuevaSolicitudForm) form).setDSitAdministrativaOtros("");
			}

			if ((((OCAPNuevaSolicitudForm) form).getCJuridicoId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCJuridicoId().equals("3"))) {
				((OCAPNuevaSolicitudForm) form).setDRegimenJuridicoOtros("");
			}

			OCAPCategProfesionalesLN categProfLN = new OCAPCategProfesionalesLN(jcylUsuario);
			OCAPCategProfesionalesOT categProfOT = null;
			if ((((OCAPNuevaSolicitudForm) form).getCProfesional_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCProfesional_id().equals("")))
				categProfOT = categProfLN.buscarOCAPCategProfesionales(
						Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesional_id()));
			else if ((((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id().equals(""))) {
				categProfOT = categProfLN.buscarOCAPCategProfesionales(
						Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id()));
			}
			((OCAPNuevaSolicitudForm) form).setDProfesional_nombre(categProfOT.getDNombre());

			OCAPEspecialidadesLN especLN = new OCAPEspecialidadesLN(jcylUsuario);
			OCAPEspecialidadesOT especOT = null;
			if ((((OCAPNuevaSolicitudForm) form).getCEspec_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCEspec_id().equals(""))
					&& (!((OCAPNuevaSolicitudForm) form).getCEspec_id().equals("Seleccione"))) {
				especOT = especLN
						.buscarOCAPEspecialidades(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCEspec_id()));
				((OCAPNuevaSolicitudForm) form).setDEspec_nombre(especOT.getDNombre());
			} else if ((((OCAPNuevaSolicitudForm) form).getCEspecFijo_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCEspecFijo_id().equals(""))
					&& (!((OCAPNuevaSolicitudForm) form).getCEspecFijo_id().equals("Seleccione"))) {
				especOT = especLN
						.buscarOCAPEspecialidades(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCEspecFijo_id()));
				((OCAPNuevaSolicitudForm) form).setDEspec_nombre(especOT.getDNombre());
			}

			provinciaOT = provinciaLN.buscarProvincia(((OCAPNuevaSolicitudForm) form).getCProvinciaCarrera_id());
			((OCAPNuevaSolicitudForm) form).setDProvinciaCarrera(Cadenas.capitalizar(provinciaOT.getDProvincia()));

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			OCAPGerenciasOT gerenciasOT = null;
			gerenciasOT = gerenciasLN
					.buscarOCAPGerencias(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGerencia_id()));
			((OCAPNuevaSolicitudForm) form).setDGerencia_nombre(gerenciasOT.getDNombre());

			OCAPCentroTrabajoLN centroLN = new OCAPCentroTrabajoLN(jcylUsuario);
			OCAPCentroTrabajoOT centroOT = null;
			centroOT = centroLN
					.buscarOCAPCentroTrabajo(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCCentrotrabajo_id()));
			((OCAPNuevaSolicitudForm) form).setDCentrotrabajo_nombre(centroOT.getDNombre());

			OCAPConvocatoriasLN convoLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT convoOT = null;
			convoOT = convoLN
					.buscarOCAPConvocatorias(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));
			((OCAPNuevaSolicitudForm) form).setDConvocatoria_nombre(convoOT.getDNombre());

			if (!"6".equals(((OCAPNuevaSolicitudForm) form).getCSitAdmonActualId())) {
				((OCAPNuevaSolicitudForm) form).setAOtraSitAdmonActual("");
			}

			OCAPProvinciasOT provCarreraActualOT = null;
			provCarreraActualOT = provinciaLN
					.buscarProvincia(((OCAPNuevaSolicitudForm) form).getCProvCarreraActualId());
			((OCAPNuevaSolicitudForm) form)
					.setDProvCarreraActual(Cadenas.capitalizar(provCarreraActualOT.getDProvincia()));

			OCAPGerenciasOT gerenciasActualOT = null;
			gerenciasActualOT = gerenciasLN
					.buscarOCAPGerencias(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGerenciaActualId()));
			((OCAPNuevaSolicitudForm) form).setDGerenciaActual(gerenciasActualOT.getDNombre());

			OCAPCentroTrabajoOT centroActualOT = null;
			centroActualOT = centroLN
					.buscarOCAPCentroTrabajo(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCCentroTrabActualId()));
			((OCAPNuevaSolicitudForm) form).setDCentroTrabActual(centroActualOT.getDNombre());

			if (Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
				((OCAPNuevaSolicitudForm) form).setBLopdSolicitud(Constantes.SI);
			}
			request.setAttribute("tipoAccionBis", Constantes.SI);
			request.setAttribute("tipoAccion", Constantes.IR_DETALLE);
			request.setAttribute("opcion", Constantes.IR_DETALLE);
			
			ArrayList solicitudesUsrConv = null;
			OCAPSolicitudesLN solicLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPNuevaSolicitudForm aux = new OCAPNuevaSolicitudForm();
			aux.setCDniReal(((OCAPNuevaSolicitudForm) form).getCDniReal());
			solicitudesUsrConv = solicLN.existeSolicParaUsrYConv(jcylUsuario, 
					((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()!=null ?
							((OCAPNuevaSolicitudForm) form).getCConvocatoriaId() :
								jcylUsuario.getParametrosUsuario()!=null && 
								jcylUsuario.getParametrosUsuario().get("PARAM_CONVOCATORIA_USUARIO")!=null?
									(String)jcylUsuario.getParametrosUsuario().get("PARAM_CONVOCATORIA_USUARIO"):null,
					aux);
			((OCAPNuevaSolicitudForm) form).setbExisteSolicParaNifConv( 
					(solicitudesUsrConv==null || solicitudesUsrConv.size()<=0)?false:true);
			

			OCAPConfigApp.logger.info(getClass().getName() + " irDetalleAlta: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irNuevaInsertar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irDetalleAltaPublico(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irDetalleAltaPublico: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			boolean captchaPassed = SimpleImageCaptchaServlet.validateResponse(
					request, ((OCAPNuevaSolicitudForm) form).getCodigoCaptcha());
			if(!captchaPassed){
				throw new Exception("ErrorCaptcha");
			}
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();

			OCAPProvinciasOT provinciaOT = null;
			OCAPProvinciasLN provinciaLN = new OCAPProvinciasLN(jcylUsuario);
			provinciaOT = provinciaLN.buscarProvincia(((OCAPNuevaSolicitudForm) form).getCProvincia_id());

			((OCAPNuevaSolicitudForm) form).setDProvincia(Cadenas.capitalizar(provinciaOT.getDProvincia()));

			OCAPLocalidadesOT localidadOT = null;
			OCAPLocalidadesLN localidadLN = new OCAPLocalidadesLN(jcylUsuario);
			localidadOT = localidadLN.buscarLocalidad(((OCAPNuevaSolicitudForm) form).getCLocalidad_id());

			((OCAPNuevaSolicitudForm) form).setDLocalidad(Cadenas.capitalizar(localidadOT.getDLocalidad()));

			OCAPGradoOT gradoOT = null;
			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			if ((((OCAPNuevaSolicitudForm) form).getCGrado_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCGrado_id().equals("")))
				gradoOT = gradoLN.buscarOCAPGrado(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGrado_id()));
			else {
				gradoOT = gradoLN.buscarOCAPGrado(expedienteCarreraOT.getCGradoId().longValue());
			}
			((OCAPNuevaSolicitudForm) form).setDGrado_des(gradoOT.getDDescripcion());

			OCAPModalidadAnteriorOT modalidadAnteriorOT = null;
			OCAPModalidadAnteriorLN modalidadAnteriorLN = new OCAPModalidadAnteriorLN(jcylUsuario);
			if ((((OCAPNuevaSolicitudForm) form).getCModAnteriorId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCModAnteriorId().equals(""))) {
				modalidadAnteriorOT = modalidadAnteriorLN
						.buscarOCAPModalidad(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCModAnteriorId()));
			}
			((OCAPNuevaSolicitudForm) form).setDModAnterior(modalidadAnteriorOT.getDDescripcion());
			((OCAPNuevaSolicitudForm) form).setCGradoAnteriorId(((OCAPNuevaSolicitudForm) form).getCGradoAnteriorId());

			OCAPProcedimientoOT procOT = null;
			OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
			if ((((OCAPNuevaSolicitudForm) form).getCProcedimientoId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCProcedimientoId().equals("")))
				procOT = procLN
						.buscarOCAPProcedimiento(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProcedimientoId()));
			else {
				procOT = procLN.buscarOCAPProcedimiento(Long.parseLong(expedienteCarreraOT.getCProcedimientoId()));
			}
			((OCAPNuevaSolicitudForm) form).setDProcedimiento(procOT.getDNombre());

			if ((((OCAPNuevaSolicitudForm) form).getCSitAdministrativaId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCSitAdministrativaId().equals("6"))) {
				((OCAPNuevaSolicitudForm) form).setDSitAdministrativaOtros("");
			}

			if ((((OCAPNuevaSolicitudForm) form).getCJuridicoId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCJuridicoId().equals("3"))) {
				((OCAPNuevaSolicitudForm) form).setDRegimenJuridicoOtros("");
			}

			OCAPCategProfesionalesOT categProfOT = null;
			OCAPCategProfesionalesLN categProfLN = new OCAPCategProfesionalesLN(jcylUsuario);
			if ((((OCAPNuevaSolicitudForm) form).getCProfesional_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCProfesional_id().equals("")))
				categProfOT = categProfLN.buscarOCAPCategProfesionales(
						Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesional_id()));
			else if ((((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id().equals(""))) {
				categProfOT = categProfLN.buscarOCAPCategProfesionales(
						Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id()));
			}
			((OCAPNuevaSolicitudForm) form).setDProfesional_nombre(categProfOT.getDNombre());

			OCAPEspecialidadesOT especOT = null;
			OCAPEspecialidadesLN especLN = new OCAPEspecialidadesLN(jcylUsuario);
			if ((((OCAPNuevaSolicitudForm) form).getCEspec_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCEspec_id().equals(""))
					&& (!((OCAPNuevaSolicitudForm) form).getCEspec_id().equals("Seleccione"))) {
				especOT = especLN
						.buscarOCAPEspecialidades(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCEspec_id()));
				((OCAPNuevaSolicitudForm) form).setDEspec_nombre(especOT.getDNombre());
			} else if ((((OCAPNuevaSolicitudForm) form).getCEspecFijo_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCEspecFijo_id().equals(""))
					&& (!((OCAPNuevaSolicitudForm) form).getCEspecFijo_id().equals("Seleccione"))) {
				especOT = especLN
						.buscarOCAPEspecialidades(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCEspecFijo_id()));
				((OCAPNuevaSolicitudForm) form).setDEspec_nombre(especOT.getDNombre());
			}

			OCAPProvinciasOT provinciaCarreraOT = null;
			provinciaCarreraOT = provinciaLN.buscarProvincia(((OCAPNuevaSolicitudForm) form).getCProvinciaCarrera_id());
			((OCAPNuevaSolicitudForm) form)
					.setDProvinciaCarrera(Cadenas.capitalizar(provinciaCarreraOT.getDProvincia()));

			OCAPGerenciasOT gerenciasOT = null;
			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			gerenciasOT = gerenciasLN
					.buscarOCAPGerencias(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGerencia_id()));
			((OCAPNuevaSolicitudForm) form).setDGerencia_nombre(gerenciasOT.getDNombre());

			OCAPCentroTrabajoOT centroOT = null;
			OCAPCentroTrabajoLN centroLN = new OCAPCentroTrabajoLN(jcylUsuario);
			centroOT = centroLN
					.buscarOCAPCentroTrabajo(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCCentrotrabajo_id()));
			((OCAPNuevaSolicitudForm) form).setDCentrotrabajo_nombre(centroOT.getDNombre());

			if (!"6".equals(((OCAPNuevaSolicitudForm) form).getCSitAdmonActualId())) {
				((OCAPNuevaSolicitudForm) form).setAOtraSitAdmonActual("");
			}

			OCAPProvinciasOT provCarreraActualOT = null;
			provCarreraActualOT = provinciaLN
					.buscarProvincia(((OCAPNuevaSolicitudForm) form).getCProvCarreraActualId());
			((OCAPNuevaSolicitudForm) form)
					.setDProvCarreraActual(Cadenas.capitalizar(provCarreraActualOT.getDProvincia()));

			OCAPGerenciasOT gerenciasActualOT = null;
			gerenciasActualOT = gerenciasLN
					.buscarOCAPGerencias(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGerenciaActualId()));
			((OCAPNuevaSolicitudForm) form).setDGerenciaActual(gerenciasActualOT.getDNombre());

			OCAPCentroTrabajoOT centroActualOT = null;
			centroActualOT = centroLN
					.buscarOCAPCentroTrabajo(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCCentroTrabActualId()));
			((OCAPNuevaSolicitudForm) form).setDCentroTrabActual(centroActualOT.getDNombre());

			request.setAttribute("tipoAccionBis", Constantes.SI);
			request.setAttribute("tipoAccion", Constantes.IR_DETALLE);
			request.setAttribute("opcion", Constantes.IR_DETALLE);
			
			OCAPConvocatoriasLN convLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT conv =
				convLN.buscarOCAPConvocatorias(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));
			((OCAPNuevaSolicitudForm) form).setDConvocatoria_nombre(conv.getDNombre());
			
			OCAPSolicitudesLN solicLN = new OCAPSolicitudesLN(jcylUsuario);
			ArrayList solicitudesUsrConv = null;
			OCAPNuevaSolicitudForm aux = new OCAPNuevaSolicitudForm();
			aux.setCDniReal(((OCAPNuevaSolicitudForm) form).getCDniReal());
			solicitudesUsrConv = solicLN.existeSolicParaUsrYConv(jcylUsuario, 
					((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()!=null ?
							((OCAPNuevaSolicitudForm) form).getCConvocatoriaId() :
								jcylUsuario.getParametrosUsuario()!=null && 
								jcylUsuario.getParametrosUsuario().get("PARAM_CONVOCATORIA_USUARIO")!=null?
									(String)jcylUsuario.getParametrosUsuario().get("PARAM_CONVOCATORIA_USUARIO"):null,
					aux);
			((OCAPNuevaSolicitudForm) form).setbExisteSolicParaNifConv( 
					(solicitudesUsrConv==null || solicitudesUsrConv.size()<=0)?false:true);

			OCAPConfigApp.logger.info(getClass().getName() + " irDetalleAltaPublico: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			if ((e.getMessage() != null) && (e.getMessage().equals("ErrorCaptcha"))) {
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						new ActionError(TrataError.tratarError(e, "error.captcha")));
			} else
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irNuevaSolicitud");
		}
		saveErrors(request, errors);

		return irSolicitud(mapping, form, request, response);
	}

	public ActionForward insertar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();

		int idSolicitud = 0;
		String fw = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " insertar: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPNuevaSolicitudLN solicitudesLN = new OCAPNuevaSolicitudLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			if (request.getParameter("irDetalle").equals("NU")) {
				OCAPLocalidadesOT localidadOT = null;
				OCAPLocalidadesLN localidadLN = new OCAPLocalidadesLN(jcylUsuario);
				localidadOT = localidadLN.buscarLocalidad(((OCAPNuevaSolicitudForm) form).getCLocalidad_id());
				((OCAPNuevaSolicitudForm) form).setDLocalidad(localidadOT.getDLocalidad());
			}

			solicitudesOT.setDNombre(((OCAPNuevaSolicitudForm) form).getDNombre());
			solicitudesOT.setDApellido1(((OCAPNuevaSolicitudForm) form).getDApellido1());
			solicitudesOT.setCDni(((OCAPNuevaSolicitudForm) form).getCDniReal());
			solicitudesOT.setCDniReal(((OCAPNuevaSolicitudForm) form).getCDniReal());
			solicitudesOT.setBSexo(((OCAPNuevaSolicitudForm) form).getBSexo());
			solicitudesOT.setNTelefono1(((OCAPNuevaSolicitudForm) form).getNTelefono1());
			solicitudesOT.setNTelefono2(((OCAPNuevaSolicitudForm) form).getNTelefono2());
			solicitudesOT.setDCorreoelectronico(((OCAPNuevaSolicitudForm) form).getDCorreoelectronico());
			solicitudesOT.setDDomicilio(((OCAPNuevaSolicitudForm) form).getDDomicilio());
			solicitudesOT.setDProvincia(((OCAPNuevaSolicitudForm) form).getDProvincia());
			solicitudesOT.setCProvincia_id(((OCAPNuevaSolicitudForm) form).getCProvincia_id());
			solicitudesOT.setDLocalidad(((OCAPNuevaSolicitudForm) form).getDLocalidad());
			solicitudesOT.setCPostalUsu(((OCAPNuevaSolicitudForm) form).getCPostalUsu());

			if ((((OCAPNuevaSolicitudForm) form).getCGrado_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCGrado_id().equals(""))) {
				solicitudesOT.setCGrado_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGrado_id()));
			}
			if ((((OCAPNuevaSolicitudForm) form).getCModAnteriorId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCModAnteriorId().equals(""))) {
				solicitudesOT.setCModAnterior_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCModAnteriorId()));
			}
			solicitudesOT.setCSitAdministrativaAuxId(((OCAPNuevaSolicitudForm) form).getCSitAdministrativaId() == null
					? "" : ((OCAPNuevaSolicitudForm) form).getCSitAdministrativaId());

			if ((((OCAPNuevaSolicitudForm) form).getCSitAdministrativaId() != null)
					&& ("6".equals(((OCAPNuevaSolicitudForm) form).getCSitAdministrativaId()))) {
				solicitudesOT.setDSitAdministrativaOtros(((OCAPNuevaSolicitudForm) form).getDSitAdministrativaOtros());
			}
			solicitudesOT.setCJuridicoId(((OCAPNuevaSolicitudForm) form).getCJuridicoId());

			if ((((OCAPNuevaSolicitudForm) form).getCJuridicoId() != null)
					&& ("3".equals(((OCAPNuevaSolicitudForm) form).getCJuridicoId()))) {
				solicitudesOT.setDRegimenJuridicoOtros(((OCAPNuevaSolicitudForm) form).getDRegimenJuridicoOtros());
			}
			if ((((OCAPNuevaSolicitudForm) form).getCProfesional_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCProfesional_id().equals("")))
				solicitudesOT.setCProfesional_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesional_id()));
			else if ((((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id().equals(""))) {
				solicitudesOT
						.setCProfesional_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id()));
			}

			if ((((OCAPNuevaSolicitudForm) form).getCEspec_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCEspec_id().equals("")))
				solicitudesOT.setCEspec_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCEspec_id()));
			else if ((((OCAPNuevaSolicitudForm) form).getCEspecFijo_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCEspecFijo_id().equals(""))) {
				solicitudesOT.setCEspec_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCEspecFijo_id()));
			}

			solicitudesOT.setCProvinciaCarrera_id(((OCAPNuevaSolicitudForm) form).getCProvinciaCarrera_id());
			solicitudesOT.setCGerencia_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGerencia_id()));
			solicitudesOT.setCCentrotrabajo_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCCentrotrabajo_id()));
			solicitudesOT.setAServicio(((OCAPNuevaSolicitudForm) form).getDServicio());
			solicitudesOT.setAPuesto(((OCAPNuevaSolicitudForm) form).getDPuesto());
			solicitudesOT.setCProcedimientoId(((OCAPNuevaSolicitudForm) form).getCProcedimientoId());

			if ((((OCAPNuevaSolicitudForm) form).getNAniosantiguedad() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getNAniosantiguedad().equals(""))) {
				solicitudesOT
						.setNAniosantiguedad(Long.parseLong(((OCAPNuevaSolicitudForm) form).getNAniosantiguedad()));
			}
			if (solicitudesOT.getNAniosantiguedad() == 0L)
				solicitudesOT.setNAniosantiguedad(-1);
			if ((((OCAPNuevaSolicitudForm) form).getNMesesantiguedad() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getNMesesantiguedad().equals("")))
				solicitudesOT
						.setNMesesantiguedad(Long.parseLong(((OCAPNuevaSolicitudForm) form).getNMesesantiguedad()));
			if ((((OCAPNuevaSolicitudForm) form).getNDiasantiguedad() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getNDiasantiguedad().equals(""))) {
				solicitudesOT.setNDiasantiguedad(Long.parseLong(((OCAPNuevaSolicitudForm) form).getNDiasantiguedad()));
			}
			if ((((OCAPNuevaSolicitudForm) form).getCConvocatoriaId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCConvocatoriaId().equals(""))) {
				solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));
			}
			solicitudesOT.setBOtroServicio(((OCAPNuevaSolicitudForm) form).getBOtroServicio());

			if (Constantes.SI.equals(solicitudesOT.getBOtroServicio())) {
				solicitudesOT.setADondeServicio(((OCAPNuevaSolicitudForm) form).getADondeServicio());
			}
			solicitudesOT.setCUsuAlta(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());

			solicitudesOT.setResumenCentros(((OCAPNuevaSolicitudForm) form).getResumenCentros());

			solicitudesOT.setCEstado(String.valueOf(1L));

			if (request.getParameter("irDetalle").equals("NU")) {
				solicitudesOT.setBLopdSolicitud(Constantes.SI);
			} else {
				solicitudesOT.setBLopdSolicitud(((OCAPNuevaSolicitudForm) form).getBLopdSolicitud());
			}

			solicitudesOT.setCSitAdmonActualId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCSitAdmonActualId()));

			if ("6".equals(((OCAPNuevaSolicitudForm) form).getCSitAdmonActualId())) {
				solicitudesOT.setAOtraSitAdmonActual(((OCAPNuevaSolicitudForm) form).getAOtraSitAdmonActual());
			}
			solicitudesOT.setCProvCarreraActualId(((OCAPNuevaSolicitudForm) form).getCProvCarreraActualId());
			solicitudesOT.setCGerenciaActualId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGerenciaActualId()));
			solicitudesOT
					.setCCentroTrabActualId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCCentroTrabActualId()));
			solicitudesOT.setDConvocatoria_nombre(((OCAPNuevaSolicitudForm) form).getDConvocatoria_nombre());

			
			OCAPSolicitudesLN solicLN = new OCAPSolicitudesLN(jcylUsuario);
			ArrayList solicitudesUsrConv = solicLN.existeSolicParaUsrYConv(jcylUsuario, ((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()!=null ?
							((OCAPNuevaSolicitudForm) form).getCConvocatoriaId() :
								jcylUsuario.getParametrosUsuario()!=null && 
								jcylUsuario.getParametrosUsuario().get("PARAM_CONVOCATORIA_USUARIO")!=null?
									(String)jcylUsuario.getParametrosUsuario().get("PARAM_CONVOCATORIA_USUARIO"):null,
					((OCAPNuevaSolicitudForm) form));
			/* Inserción (No existe una solicitud para NIF y convocatoria) */
			if(!((OCAPNuevaSolicitudForm) form).getbExisteSolicParaNifConv()){
				idSolicitud = solicitudesLN.insertar(solicitudesOT, jcylUsuario);
			}
			/* Actualización (existe una solicitud para NIF y convocatoria) */
			else {
				solicitudesOT.setCSolicitudId(((OCAPSolicitudesOT) solicitudesUsrConv.get(0)).getCSolicitudId());
				idSolicitud = (int) solicitudesOT.getCSolicitudId();
				solicitudesLN.actualizar(solicitudesOT, jcylUsuario);
			}

			if (request.getParameter("irDetalle").equals("NU")) {
				((OCAPNuevaSolicitudForm) form).setCSolicitudId(idSolicitud);
				fw = "irImprimirSolicitudPublica";
			} else if (Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
				((OCAPNuevaSolicitudForm) form).setCSolicitudId(idSolicitud);
				fw = "irImprimirSolicitud";
			} else {
				fw = "mensajeExito";

				request.setAttribute("rutaVuelta", "OCAPNuevaSolicitud.do?accion=detalleSolicitud&cSolicitudId="
						+ idSolicitud + "&vuelta=" + "Alta");
			}

			OCAPConfigApp.logger.info(getClass().getName() + " insertar: Fin");
		} catch (SendFailedException e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.envioEmail")));
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			if ((e.getMessage() != null) && (e.getMessage().equals("ExisteSolicitudConvocatoria")))
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						new ActionError(TrataError.tratarError(e, "error.existeSolicitudConvocatoria")));
			else if ((e.getMessage() != null) && (e.getMessage().equals("ErrorCaptcha"))) {
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						new ActionError(TrataError.tratarError(e, "error.captcha")));
			} else
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty()))) {
			return mapping.findForward(fw);
		}
		if ((errors != null) && (!errors.isEmpty())) {
			saveErrors(request, errors);
			if ("NU".equals(request.getParameter("irDetalle"))) {
				return irSolicitud(mapping, form, request, response);
			}

			return mapping.findForward("fallo");
		}
		saveMessages(request, messages);

		return mapping.findForward("publico");
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		String fw = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " modificar: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			solicitudesOT.setCSolicitudId(((OCAPNuevaSolicitudForm) form).getCSolicitudId());

			solicitudesOT.setCUsr_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCUsr_id()));
			solicitudesOT.setDNombre(((OCAPNuevaSolicitudForm) form).getDNombre());
			solicitudesOT.setDApellido1(((OCAPNuevaSolicitudForm) form).getDApellido1());

			solicitudesOT.setCDniReal(((OCAPNuevaSolicitudForm) form).getCDniReal());
			solicitudesOT.setBSexo(((OCAPNuevaSolicitudForm) form).getBSexo());
			solicitudesOT.setNTelefono1(((OCAPNuevaSolicitudForm) form).getNTelefono1());
			solicitudesOT.setNTelefono2(((OCAPNuevaSolicitudForm) form).getNTelefono2());
			solicitudesOT.setDCorreoelectronico(((OCAPNuevaSolicitudForm) form).getDCorreoelectronico());
			solicitudesOT.setDDomicilio(((OCAPNuevaSolicitudForm) form).getDDomicilio());
			solicitudesOT.setDProvincia(((OCAPNuevaSolicitudForm) form).getDProvincia());
			solicitudesOT.setCProvincia_id(((OCAPNuevaSolicitudForm) form).getCProvincia_id());
			
			if (((OCAPNuevaSolicitudForm) form).getCLocalidad_id() != null) {
				OCAPLocalidadesLN localidadesLN = new OCAPLocalidadesLN(jcylUsuario);
				OCAPLocalidadesOT localidadOT = localidadesLN
						.buscarLocalidad(((OCAPNuevaSolicitudForm) form).getCLocalidad_id());
				solicitudesOT.setDLocalidad(localidadOT.getDLocalidad());
				solicitudesOT.setDLocalidadUsu(localidadOT.getDLocalidad());
			}
			
			solicitudesOT.setCPostalUsu(((OCAPNuevaSolicitudForm) form).getCPostalUsu());

			if ((((OCAPNuevaSolicitudForm) form).getCGrado_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCGrado_id().equals(""))) {
				solicitudesOT.setCGrado_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGrado_id()));
			}
			if ((((OCAPNuevaSolicitudForm) form).getCModAnteriorId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCModAnteriorId().equals(""))) {
				solicitudesOT.setCModAnterior_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCModAnteriorId()));
			}
			solicitudesOT.setCProcedimientoId(((OCAPNuevaSolicitudForm) form).getCProcedimientoId());

			solicitudesOT.setCJuridicoId(((OCAPNuevaSolicitudForm) form).getCJuridicoId());
			if ((((OCAPNuevaSolicitudForm) form).getCJuridicoCombo() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getCJuridicoCombo()))) {
				solicitudesOT.setCEstatutId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCJuridicoCombo()));
			}
			if ((((OCAPNuevaSolicitudForm) form).getCJuridicoId() != null)
					&& ("3".equals(((OCAPNuevaSolicitudForm) form).getCJuridicoId()))) {
				solicitudesOT.setDRegimenJuridicoOtros(((OCAPNuevaSolicitudForm) form).getDRegimenJuridicoOtros());
			}

			if ("1".equals(((OCAPNuevaSolicitudForm) form).getCJuridicoId())) {
				solicitudesOT
						.setCProfesional_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id()));
				if ((((OCAPNuevaSolicitudForm) form).getCEspecFijo_id() != null)
						&& (!((OCAPNuevaSolicitudForm) form).getCEspecFijo_id().equals("")))
					solicitudesOT.setCEspec_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCEspecFijo_id()));
				else
					solicitudesOT.setCEspec_id(-1);
			} else {
				solicitudesOT.setCProfesional_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesional_id()));
				if ((((OCAPNuevaSolicitudForm) form).getCEspec_id() != null)
						&& (!((OCAPNuevaSolicitudForm) form).getCEspec_id().equals("")))
					solicitudesOT.setCEspec_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCEspec_id()));
				else {
					solicitudesOT.setCEspec_id(-1);
				}
			}
			solicitudesOT.setCSitAdministrativaAuxId(((OCAPNuevaSolicitudForm) form).getCSitAdministrativaId());

			if ((((OCAPNuevaSolicitudForm) form).getCSitAdministrativaId() != null)
					&& ("6".equals(((OCAPNuevaSolicitudForm) form).getCSitAdministrativaId()))) {
				solicitudesOT.setDSitAdministrativaOtros(((OCAPNuevaSolicitudForm) form).getDSitAdministrativaOtros());
			}
			solicitudesOT.setCProvinciaCarrera_id(((OCAPNuevaSolicitudForm) form).getCProvinciaCarrera_id());
			solicitudesOT.setCGerencia_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGerencia_id()));
			solicitudesOT.setCCentrotrabajo_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCCentrotrabajo_id()));
			solicitudesOT.setAServicio(((OCAPNuevaSolicitudForm) form).getDServicio());
			solicitudesOT.setAPuesto(((OCAPNuevaSolicitudForm) form).getDPuesto());

			if ((((OCAPNuevaSolicitudForm) form).getNAniosantiguedad() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getNAniosantiguedad().equals("")))
				solicitudesOT
						.setNAniosantiguedad(Long.parseLong(((OCAPNuevaSolicitudForm) form).getNAniosantiguedad()));
			if ((((OCAPNuevaSolicitudForm) form).getNMesesantiguedad() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getNMesesantiguedad().equals("")))
				solicitudesOT
						.setNMesesantiguedad(Long.parseLong(((OCAPNuevaSolicitudForm) form).getNMesesantiguedad()));
			if ((((OCAPNuevaSolicitudForm) form).getNDiasantiguedad() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getNDiasantiguedad().equals(""))) {
				solicitudesOT.setNDiasantiguedad(Long.parseLong(((OCAPNuevaSolicitudForm) form).getNDiasantiguedad()));
			}

			if (solicitudesOT.getNAniosantiguedad() == 0L)
				solicitudesOT.setNAniosantiguedad(-1);
			if (solicitudesOT.getNMesesantiguedad() == 0L)
				solicitudesOT.setNMesesantiguedad(-1);
			if (solicitudesOT.getNDiasantiguedad() == 0L) {
				solicitudesOT.setNDiasantiguedad(-1);
			}

			if (Constantes.SI.equals(solicitudesOT.getBOtroServicio())) {
				solicitudesOT.setADondeServicio(((OCAPNuevaSolicitudForm) form).getADondeServicio());
			}
			solicitudesOT.setResumenCentros(((OCAPNuevaSolicitudForm) form).getResumenCentros());

			solicitudesOT.setCExp_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCExp_id()));
			solicitudesOT.setAModificadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());

			//OCAP-1327
			if(((OCAPNuevaSolicitudForm) form).getCProvCarreraActualId() != null) {
				solicitudesOT.setCProvCarreraActualId(((OCAPNuevaSolicitudForm) form).getCProvCarreraActualId());
			}
			
			if(((OCAPNuevaSolicitudForm) form).getCGerenciaActualId() != null) {
				solicitudesOT.setCGerenciaActualId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGerenciaActualId()));
			}
			
			if(((OCAPNuevaSolicitudForm) form).getCCentroTrabActualId() != null) {
				solicitudesOT.setCCentroTrabActualId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCCentroTrabActualId()));
			}
			if(((OCAPNuevaSolicitudForm) form).getDSitAdmonActual() != null) {
				solicitudesOT.setDSitAdmonActual(((OCAPNuevaSolicitudForm) form).getDSitAdmonActual());
			}
			if(((OCAPNuevaSolicitudForm) form).getCSitAdmonActualId() != null) {
				solicitudesOT.setCSitAdmonActualId(new Long(((OCAPNuevaSolicitudForm) form).getCSitAdmonActualId()));
			}
			if(Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
				solicitudesLN.modificarSolicitud(solicitudesOT, jcylUsuario,true);
			}else {
				solicitudesLN.modificarSolicitud(solicitudesOT, jcylUsuario,false);
			}
			

			request.setAttribute("rutaVuelta", "OCAPNuevaSolicitud.do?accion=buscar&recuperarBusquedaAnterior=Y");
			fw = "mensajeExito";

			OCAPConfigApp.logger.info(getClass().getName() + " modificar: Fin");
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

	public ActionForward generarPDFSolic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		String pathBase = null;
		OCAPSolicitudesOT solicitudOT = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarPDFSolic: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPNuevaSolicitudLN solicitudLN = new OCAPNuevaSolicitudLN(jcylUsuario);
			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);

			String cSolicitudIdS = request.getParameter("cSolicitudId");
			int cSolicitudId = Integer.parseInt(cSolicitudIdS);

			solicitudOT = solicitudLN.detalleSolicitud(cSolicitudId);
			pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");
			solicitudesLN.crearPDFSolicitud(response, solicitudOT, pathBase);

			OCAPConfigApp.logger.info(getClass().getName() + " generarPDF: Fin");
		} catch (SendFailedException e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.envioEmail")));
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mensajeExito");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	private String formatCadena(String cad) {
		cad = cad.replaceAll("'", "&rsquo;");

		return cad;
	}

	public ActionForward cargarLocalidades(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Type", "application/json;charset=ISO-8859-1");
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoLocalidades = null;
		String textReturn = "";
		try {
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			String provinciaId = request.getParameter("val");

			if ((provinciaId == null) || (provinciaId.equals(""))) {
				textReturn = getExceptionCategoria(provinciaId);
			} else {
				OCAPLocalidadesLN localidadesLN = new OCAPLocalidadesLN(jcylUsuario);
				listadoLocalidades = localidadesLN.listarLocalidadesProvincia(provinciaId);
				session.setAttribute(Constantes.COMBO_LOCALIDADES, listadoLocalidades);
				textReturn = getCascadeLocalidades(listadoLocalidades, provinciaId);
			}

			PrintWriter out = response.getWriter();
			out.println(textReturn);
			out.flush();
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		return null;
	}

	private String getCascadeLocalidades(ArrayList listadoLocalidades, String provinciaId) {
		StringBuffer textReturn = new StringBuffer();
		if ((listadoLocalidades != null) && (listadoLocalidades.size() > 0)) {
			OCAPLocalidadesOT localidadOT = null;
			textReturn.append("[");

			textReturn.append("{'When':'" + provinciaId + "','Value':'','Text':'Seleccione...'},");

			for (int i = 0; i < listadoLocalidades.size(); i++) {
				localidadOT = (OCAPLocalidadesOT) listadoLocalidades.get(i);
				textReturn.append("{'When':'").append(provinciaId).append("','Value':'")
						.append(localidadOT.getCLocalidadId()).append("','Text':'")
						.append(formatCadena(localidadOT.getDLocalidad())).append("'},");
			}
			textReturn.append("]");
		}

		return textReturn.toString();
	}

	private String getExceptionLocalidad(String provinciaId) {
		StringBuffer textReturn = new StringBuffer();
		textReturn.append("[");
		textReturn.append("{'When':'").append(provinciaId).append("','Value':'").append("").append("','Text':'")
				.append("Seleccione...").append("'},").append("]");

		return textReturn.toString();
	}

	public ActionForward cargarLocalidadesMod(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoLocalidades = null;
		String textReturn = "";
		try {
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			String provinciaId = request.getParameter("val");

			if ((provinciaId == null) || (provinciaId.equals(""))) {
				textReturn = getExceptionCategoria(provinciaId);
			} else {
				OCAPLocalidadesLN localidadesLN = new OCAPLocalidadesLN(jcylUsuario);
				listadoLocalidades = localidadesLN.listarLocalidadesProvincia(provinciaId);
				session.setAttribute(Constantes.COMBO_LOCALIDADES, listadoLocalidades);
				textReturn = getCascadeLocalidadesMod(listadoLocalidades, provinciaId);
			}

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(textReturn);
			out.flush();
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		return null;
	}

	private String getCascadeLocalidadesMod(ArrayList listadoLocalidades, String provinciaId) {
		StringBuffer textReturn = new StringBuffer();
		if ((listadoLocalidades != null) && (listadoLocalidades.size() > 0)) {
			OCAPLocalidadesOT localidadOT = null;
			textReturn.append("[");

			textReturn.append("{'When':'" + provinciaId + "','Value':'','Text':'Seleccione...'},");

			for (int i = 0; i < listadoLocalidades.size(); i++) {
				localidadOT = (OCAPLocalidadesOT) listadoLocalidades.get(i);
				textReturn.append("{'When':'").append(provinciaId).append("','Value':'")
						.append(localidadOT.getDLocalidad()).append("','Text':'")
						.append(formatCadena(localidadOT.getDLocalidad())).append("'},");
			}
			textReturn.append("]");
		}

		return textReturn.toString();
	}

	public ActionForward cargarCategorias(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Type", "application/json;charset=ISO-8859-1");
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoCategorias = null;
		String textReturn = "";
		try {
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			String cJuridico = request.getParameter("val");

			if ((cJuridico == null) || (cJuridico.equals(""))) {
				textReturn = getExceptionCategoria(cJuridico);
			} else {
				OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
				if (!cJuridico.equals("1")) {
					listadoCategorias = categProfesionalesLN.listarCategoriasRJ(cJuridico);
				}
				textReturn = getCascadeCategoria(listadoCategorias, cJuridico);
			}

			PrintWriter out = response.getWriter();
			out.println(textReturn);
			out.flush();
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		return null;
	}

	private String getCascadeCategoria(ArrayList listadoCategorias, String estatutarioId) {
		StringBuffer textReturn = new StringBuffer();
		if ((listadoCategorias != null) && (listadoCategorias.size() > 0)) {
			OCAPCategProfesionalesOT categoriaOT = null;
			textReturn.append("[");

			textReturn.append("{'When':'" + estatutarioId + "','Value':'','Text':'Seleccione...'},");

			for (int i = 0; i < listadoCategorias.size(); i++) {
				categoriaOT = (OCAPCategProfesionalesOT) listadoCategorias.get(i);

				textReturn.append("{'When':'").append(estatutarioId).append("','Value':'")
						.append(categoriaOT.getCProfesionalId()).append("','Text':'")
						.append(formatCadena(categoriaOT.getDNombre())).append("'},");
			}
			textReturn.append("]");
		} else {
			textReturn.append("[");

			textReturn.append("{'When':'" + estatutarioId + "','Value':'','Text':'Seleccione...'},");

			textReturn.append("]");
		}

		return textReturn.toString();
	}

	private String getExceptionCategoria(String estatutarioId) {
		StringBuffer textReturn = new StringBuffer();
		textReturn.append("[");
		textReturn.append("{'When':'").append(estatutarioId).append("','Value':'").append("").append("','Text':'")
				.append("Seleccione...").append("'},").append("]");

		return textReturn.toString();
	}

	public ActionForward cargarCategoriasEstaturioFijo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Content-Type", "application/json;charset=ISO-8859-1");
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoCategorias = null;
		String textReturn = "";
		try {
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			String cJuridico = request.getParameter("val");

			if ((cJuridico == null) || (cJuridico.equals(""))) {
				textReturn = getExceptionCategoriaEstatutarioFijo(cJuridico);
			} else {
				OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
				listadoCategorias = categProfesionalesLN.listarCategoriasRJFijo(cJuridico);
				textReturn = getCascadeCategoria(listadoCategorias, cJuridico);
			}

			PrintWriter out = response.getWriter();
			out.println(textReturn);
			out.flush();
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		return null;
	}

	private String getCascadeCategoriaEstatutarioFijo(ArrayList listadoCategorias, String estatutarioId) {
		StringBuffer textReturn = new StringBuffer();
		if ((listadoCategorias != null) && (listadoCategorias.size() > 0)) {
			OCAPCategProfesionalesOT categoriaOT = null;
			textReturn.append("[");

			textReturn.append("{'When':'" + estatutarioId + "','Value':'','Text':'Seleccione...'},");

			for (int i = 0; i < listadoCategorias.size(); i++) {
				categoriaOT = (OCAPCategProfesionalesOT) listadoCategorias.get(i);

				textReturn.append("{'When':'").append(estatutarioId).append("','Value':'")
						.append(categoriaOT.getCProfesionalId()).append("','Text':'")
						.append(formatCadena(categoriaOT.getDNombre())).append("'},");
			}
			textReturn.append("]");
		}

		return textReturn.toString();
	}

	private String getExceptionCategoriaEstatutarioFijo(String estatutarioId) {
		StringBuffer textReturn = new StringBuffer();
		textReturn.append("[");
		textReturn.append("{'When':'").append(estatutarioId).append("','Value':'").append("").append("','Text':'")
				.append("Seleccione...").append("'},").append("]");

		return textReturn.toString();
	}

	public ActionForward cargarEspecialidades(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Type", "application/json;charset=ISO-8859-1");
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoEspecialidades = null;
		String textReturn = "";
		try {
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			String especialidadId = request.getParameter("val");

			if ((especialidadId == null) || (especialidadId.equals(""))) {
				textReturn = getExceptionEspecialidades(especialidadId);
			} else {
				OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
				listadoEspecialidades = especialidadesLN.listarEspecialidades(Long.parseLong(especialidadId));
				textReturn = getCascadeEspecialidades(listadoEspecialidades, especialidadId);
			}

			PrintWriter out = response.getWriter();
			out.println(textReturn);
			out.flush();
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		return null;
	}

	private String getCascadeEspecialidades(ArrayList listadoEspecialidades, String profesionalId) {
		StringBuffer textReturn = new StringBuffer();
		if ((listadoEspecialidades != null) && (listadoEspecialidades.size() > 0)) {
			OCAPEspecialidadesOT especialidadOT = null;
			textReturn.append("[");

			textReturn.append("{'When':'" + profesionalId + "','Value':'','Text':'Seleccione...'},");

			for (int i = 0; i < listadoEspecialidades.size(); i++) {
				especialidadOT = (OCAPEspecialidadesOT) listadoEspecialidades.get(i);

				textReturn.append("{'When':'").append(profesionalId).append("','Value':'")
						.append(especialidadOT.getCEspecId()).append("','Text':'")
						.append(formatCadena(especialidadOT.getDNombre())).append("'},");
			}
			textReturn.append("]");
		}

		return textReturn.toString();
	}

	private String getCascadeEspecialidadesEstatutarioFijo(ArrayList listadoEspecialidades, String profesionalId) {
		StringBuffer textReturn = new StringBuffer();
		if ((listadoEspecialidades != null) && (listadoEspecialidades.size() > 0)) {
			OCAPEspecialidadesOT especialidadOT = null;
			textReturn.append("[");

			textReturn.append("{'When':'" + profesionalId + "','Value':'','Text':'Seleccione...'},");

			for (int i = 0; i < listadoEspecialidades.size(); i++) {
				especialidadOT = (OCAPEspecialidadesOT) listadoEspecialidades.get(i);

				textReturn.append("{'When':'").append(profesionalId).append("','Value':'")
						.append(especialidadOT.getCEspecId()).append("','Text':'")
						.append(formatCadena(especialidadOT.getDNombre())).append("'},");
			}
			textReturn.append("]");
		}

		return textReturn.toString();
	}

	private String getExceptionEspecialidades(String profesionalId) {
		StringBuffer textReturn = new StringBuffer();
		textReturn.append("[");
		textReturn.append("{'When':'").append(profesionalId).append("','Value':'").append("").append("','Text':'")
				.append("Seleccione...").append("'},").append("]");

		return textReturn.toString();
	}

	private String getExceptionEspecialidadesEstatutarioFijo(String profesionalId) {
		StringBuffer textReturn = new StringBuffer();
		textReturn.append("[");
		textReturn.append("{'When':'").append(profesionalId).append("','Value':'").append("").append("','Text':'")
				.append("Seleccione...").append("'},").append("]");

		return textReturn.toString();
	}

	public ActionForward cargarEspecialidadesEstatutarioFijo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Content-Type", "application/json;charset=ISO-8859-1");
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoEspecialidades = null;
		String textReturn = "";
		try {
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			String especialidadId = request.getParameter("val");

			if ((especialidadId == null) || (especialidadId.equals(""))) {
				textReturn = getExceptionEspecialidadesEstatutarioFijo(especialidadId);
			} else {
				OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
				listadoEspecialidades = especialidadesLN.listarEspecialidades(Long.parseLong(especialidadId));
				textReturn = getCascadeEspecialidadesEstatutarioFijo(listadoEspecialidades, especialidadId);
			}

			PrintWriter out = response.getWriter();
			out.println(textReturn);
			out.flush();
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		return null;
	}

	public ActionForward cargarComboConvocatorias(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoConvocatorias = null;

		String salida = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboConvocatorias: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			if ((((OCAPNuevaSolicitudForm) form).getCGrado_id() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getCGrado_id())))
				listadoConvocatorias = convocatoriasLN.consultaOCAPConvocatoriasPorGradoId(
						Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGrado_id()));
			else {
				listadoConvocatorias = new ArrayList();
			}
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);
			((OCAPNuevaSolicitudForm) form).setjspInicio(true);
			salida = "irBuscarSolic";
			request.setAttribute("opcion", request.getParameter("opcion"));
			request.setAttribute("fase", request.getParameter("fase"));
			request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);
			request.setAttribute("tipoAccionBis", Constantes.SI);

			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboConvocatorias: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
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
		ArrayList listadoConvocatorias = null;

		String salida = "";
		ArrayList listaEstados = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboEstados: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			if ((((OCAPNuevaSolicitudForm) form).getCGrado_id() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getCGrado_id())))
				listadoConvocatorias = convocatoriasLN.consultaOCAPConvocatoriasPorGradoId(
						Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGrado_id()));
			else {
				listadoConvocatorias = new ArrayList();
			}
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);

			if (request.getParameter("opcion").equals("todo")) {
				listaEstados = new ArrayList();
				if (request.getParameter("estado") != null) {
					request.setAttribute("estado", request.getParameter("estado"));
					if ("P".equals(request.getParameter("estado")))
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_PENDIENTE, "P"));
					if ("A".equals(request.getParameter("estado")))
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_ACEPTADO, "A"));
					if (Constantes.ESTADO_DESESTIMADO_VALUE.equals(request.getParameter("estado")))
						listaEstados.add(
								new LabelValueBean(Constantes.ESTADO_DESESTIMADO, Constantes.ESTADO_DESESTIMADO_VALUE));
					if ("D".equals(request.getParameter("estado")))
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_DENEGADO, "D"));
				} else if ((((OCAPNuevaSolicitudForm) form).getCFase() != null)
						&& (!"".equals(((OCAPNuevaSolicitudForm) form).getCFase()))) {
					if (Constantes.FASE_INICIACION.equals(((OCAPNuevaSolicitudForm) form).getCFase())) {
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_PENDIENTE, "P"));
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_ACEPTADO, "A"));
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_EXCLUIDO, Constantes.ESTADO_EXCLUIDO_VALUE));
					}

					if (Constantes.FASE_VALIDACION.equals(((OCAPNuevaSolicitudForm) form).getCFase())) {
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_PENDIENTE, "P"));
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_ACEPTADO, "A"));
						listaEstados.add(
								new LabelValueBean(Constantes.ESTADO_EXCLUIDO, Constantes.ESTADO_DESESTIMADO_VALUE));
					}
					if (Constantes.FASE_VALIDACION_CC.equals(((OCAPNuevaSolicitudForm) form).getCFase())) {
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_PENDIENTE, "P"));
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_ACEPTADO, "A"));
						listaEstados.add(new LabelValueBean(Constantes.ESTADO_EXCLUIDO, "D"));
					}
				}

				session.setAttribute(Constantes.COMBO_ESTADOS, listaEstados);
			}

			request.setAttribute("opcion", request.getParameter("opcion"));

			if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("alta"))) {
				salida = "irInsertar";
			} else if ((request.getParameter("jspVuelta") != null)
					&& (request.getParameter("jspVuelta").equals("buscador"))) {
				((OCAPNuevaSolicitudForm) form).setjspInicio(true);
				salida = "irBuscar";
			} else if ((request.getParameter("jspVuelta") != null)
					&& (request.getParameter("jspVuelta").equals("irCSVDinamico"))) {
				salida = "irCSVDinamico";
			} else if ((request.getParameter("jspVuelta") != null)
					&& (request.getParameter("jspVuelta").equals("buscadorGrs"))) {
				((OCAPNuevaSolicitudForm) form).setjspInicio(true);
				salida = "irBuscarGrs";
			}

			request.setAttribute("opcion", request.getParameter("opcion"));
			request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);
			request.setAttribute("tipoAccionBis", Constantes.SI);
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboEstados: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(salida);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward cargarCombosEspecialidadesPuestos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoEspecialidades = null;
		ArrayList listadoCategorias = null;

		String salida = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarCombosEspecialidadesPuestos: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
			if ((request.getParameter("actual") != null) && (Constantes.NO.equals(request.getParameter("actual")))) {
				if ((((OCAPNuevaSolicitudForm) form).getCProfesional_id() != null)
						&& (!"".equals(((OCAPNuevaSolicitudForm) form).getCProfesional_id())))
					listadoEspecialidades = especialidadesLN
							.listarEspecialidades(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesional_id()));
				else {
					listadoEspecialidades = new ArrayList();
				}
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
			} else {
				if ((((OCAPNuevaSolicitudForm) form).getCProfesional_id() != null)
						&& (!"".equals(((OCAPNuevaSolicitudForm) form).getCProfesional_id()))) {
					listadoEspecialidades = especialidadesLN
							.listarEspecialidades(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesional_id()));
					OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
					listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();
				} else {
					listadoEspecialidades = new ArrayList();
				}
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD_ACTUAL, listadoEspecialidades);
			}

			if ((request.getParameter("jspVuelta") != null)
					&& (request.getParameter("jspVuelta").equals("irCSVDinamico"))) {
				if ((((OCAPNuevaSolicitudForm) form).getCProfesional_id() != null)
						&& (!"".equals(((OCAPNuevaSolicitudForm) form).getCProfesional_id()))) {
					listadoEspecialidades = especialidadesLN
							.listarEspecialidades(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesional_id()));
					OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
					listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();
				} else {
					listadoEspecialidades = new ArrayList();
				}
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
			}

			if ((request.getParameter("jspVuelta") != null) && (request.getParameter("jspVuelta").equals("alta"))) {
				salida = "irInsertar";
			} else if ((request.getParameter("jspVuelta") != null)
					&& (request.getParameter("jspVuelta").equals("modif"))) {
				salida = "irModificar";
			} else if ((request.getParameter("jspVuelta") != null)
					&& (request.getParameter("jspVuelta").equals("buscador"))) {
				((OCAPNuevaSolicitudForm) form).setjspInicio(true);
				salida = "irBuscar";
			} else if ((request.getParameter("jspVuelta") != null)
					&& (request.getParameter("jspVuelta").equals("buscadorB"))) {
				((OCAPNuevaSolicitudForm) form).setjspInicio(true);
				salida = "irBuscarB";
			} else if ((request.getParameter("jspVuelta") != null)
					&& (request.getParameter("jspVuelta").equals("irCSVDinamico"))) {
				salida = "irCSVDinamico";
			} else if ((request.getParameter("jspVuelta") != null)
					&& (request.getParameter("jspVuelta").equals("solicitud"))) {
				if ((request.getParameter("aceptado") != null)
						&& (request.getParameter("aceptado").equals(Constantes.SI_ESP))) {
					request.setAttribute("primeraSolic", Constantes.NO);
				}
				salida = "irSolicitud";
			}

			if ((request.getParameter("jspVuelta") != null) && (!request.getParameter("jspVuelta").equals("solicitud"))
					&& (jcylUsuario.getUser().getRol() != null)
					&& (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE))) {
				Map parametros = jcylUsuario.getParametrosUsuario();
				((OCAPNuevaSolicitudForm) form).setCGerencia_id((String) parametros.get("PARAM_GERENCIA"));
				((OCAPNuevaSolicitudForm) form).setCProvincia_id((String) parametros.get("PARAM_PROV"));
				((OCAPNuevaSolicitudForm) form).setCTipogerencia_id((String) parametros.get("PARAM_TIPO_GERENCIA"));
			}

			request.setAttribute("opcion", request.getParameter("opcion"));
			request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " cargarCombosEspecialidadesPuestos: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(salida);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irSolicitud(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ArrayList listadoRegimenJuridico = null;
		ArrayList listadoProcedimientos = null;
		ArrayList listadoSit_Administrativas = null;

		ArrayList listadoCategorias = null;
		ArrayList listadoEspecialidades = null;
		ArrayList listadoGrados = null;
		ArrayList listadoGerencias = null;
		ArrayList listadoCentros = null;
		ArrayList listadoModAnterior = null;

		ArrayList listaSituaciones = null;
		ArrayList listaVinculos = null;
		ArrayList listaTodasProvincias = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irSolicitud: Inicio");
			validarAccion(mapping, form, request, response);

			Usuario usuarioSEGU = new Usuario();
			JCYLUsuario usuario = new JCYLUsuario(usuarioSEGU);
			OCAPGradoLN gradoLN = new OCAPGradoLN(usuario);
			listadoGrados = gradoLN.listarGrados();			
			
			if ((listadoGrados == null) || (listadoGrados.size() == 0)) {
				messages.add("mensaje", new ActionMessage("mensaje.noExistenConvocatoriasSolic"));
				saveMessages(request, messages);
				return mapping.findForward("falloPublico");
			}

			
			//OCAP-1528
			if (!Utilidades.isNullOrIsEmpty(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId())) {
				OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(usuario);
				ArrayList listadoGradosConvocatoria = convocatoriasLN.consultaGradoDeConvocatoria(Integer.parseInt(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));

				if(listadoGradosConvocatoria == null || listadoGradosConvocatoria.isEmpty()) {
					//Si entramos aquí es porque la convocatoria no tiene asociado ningun grado concreto.
					//En este caso, metemos todos los grados posibles para que el usuario seleccione
					listadoGradosConvocatoria = gradoLN.listarGrados();
				}
				
				session.setAttribute(Constantes.COMBO_GRADOS_ALTA, listadoGradosConvocatoria);
				
			} else {
				session.setAttribute(Constantes.COMBO_GRADOS_ALTA, listadoGrados);
			}

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			ArrayList listadoConvocatorias = convocatoriasLN.listarConvocatorias();
			listadoConvocatorias = convocatoriasLN.listarConvocatorias();
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS_ALTA, convocatoriasLN.listarConvocatoriasActivasAlta());


			OCAPConvocatoriasLN convoLN = new OCAPConvocatoriasLN(usuario);
			ArrayList listaConvocatorias = convoLN.listarConvocatoriasActivasAlta();
			if ((listaConvocatorias == null) || (listaConvocatorias.size() == 0)) {
				messages.add("mensaje", new ActionMessage("mensaje.noExistenConvocatoriasSolic"));
				saveMessages(request, messages);
				return mapping.findForward("falloPublico");
			}
			if (Constantes.SI.equals(((OCAPConvocatoriasOT) listaConvocatorias.get(0)).getBCierreSo())) {
				messages.add("mensaje", new ActionMessage("mensaje.convocatoriaCerrada"));
				saveMessages(request, messages);
				return mapping.findForward("falloPublico");
			}

			 //((OCAPNuevaSolicitudForm)form).setAnioConvocatoria(((OCAPConvocatoriasOT)listaConvocatorias.get(0)).getDAnioConvocatoria());

			if (session.getAttribute(Constantes.COMBO_MOD_ANTERIOR) == null) {
				OCAPModalidadAnteriorLN modalidadAnteriorLN = new OCAPModalidadAnteriorLN(usuario);
				listadoModAnterior = modalidadAnteriorLN.listarModalidadAnterior();
				session.setAttribute(Constantes.COMBO_MOD_ANTERIOR, listadoModAnterior);
			}

			if (session.getAttribute(Constantes.COMBO_PERSONAL) == null) {
				OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(usuario);
				listadoProcedimientos = procLN.listadoOCAPProcedimiento();
				session.setAttribute(Constantes.COMBO_PERSONAL, listadoProcedimientos);
			}

			if (session.getAttribute(Constantes.COMBO_SIT_ADMIN) == null) {
				OCAPSit_AdministrativaLN sit_AdministrativaLN = new OCAPSit_AdministrativaLN(usuario);
				listadoSit_Administrativas = sit_AdministrativaLN.listadoOCAPSit_Administrativa();
				session.setAttribute(Constantes.COMBO_SIT_ADMIN, listadoSit_Administrativas);
			}

			if (session.getAttribute(Constantes.COMBO_REGIMEN_JURIDICO) == null) {
				OCAPRegimenJuridicoLN regJuridicoLN = new OCAPRegimenJuridicoLN(usuario);
				listadoRegimenJuridico = regJuridicoLN.listadoOCAPRegimenJuridico();
				session.setAttribute(Constantes.COMBO_REGIMEN_JURIDICO, listadoRegimenJuridico);
			}

			if (session.getAttribute(Constantes.COMBO_PROVINCIAS) == null) {
				OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(usuario);
				ArrayList listadoProvincias = provinciasLN.listarProvinciasComunidad("CL");
				session.setAttribute(Constantes.COMBO_PROVINCIAS, listadoProvincias);
			}

			if ((((OCAPNuevaSolicitudForm) form).getCProvincia_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCProvincia_id().equals(""))) {
				OCAPLocalidadesLN localidadesLN = new OCAPLocalidadesLN(usuario);
				ArrayList listadoLocalidades = localidadesLN
						.listarLocalidadesProvincia(((OCAPNuevaSolicitudForm) form).getCProvincia_id());
				session.setAttribute(Constantes.COMBO_LOCALIDADES, listadoLocalidades);
			} else {
				session.setAttribute(Constantes.COMBO_LOCALIDADES, new ArrayList());
			}

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(usuario);
			if ((((OCAPNuevaSolicitudForm) form).getCJuridicoId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCJuridicoId().equals(""))) {
				listadoCategorias = categProfesionalesLN
						.listarCategoriasRJ(((OCAPNuevaSolicitudForm) form).getCJuridicoId());
				session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
			} else {
				session.setAttribute(Constantes.COMBO_CATEGORIA, new ArrayList());
			}

			OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(usuario);
			if ((((OCAPNuevaSolicitudForm) form).getCProfesional_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCProfesional_id().equals(""))) {
				listadoEspecialidades = especialidadesLN
						.listarEspecialidades(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesional_id()));
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
			} else if ((((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id().equals(""))) {
				listadoEspecialidades = especialidadesLN
						.listarEspecialidades(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id()));
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
			} else {
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
			}

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(usuario);
			if (((OCAPNuevaSolicitudForm) form).getCProvinciaCarrera_id() != null) {

				listadoGerencias = gerenciasLN
						.listarGerenciasSolicitud(((OCAPNuevaSolicitudForm) form).getCProvinciaCarrera_id());
				session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);
			} else {
				listadoGerencias = gerenciasLN.listadoOCAPGerencias();
				session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);
				session.setAttribute(Constantes.COMBO_GERENCIA_ACTUAL, listadoGerencias);
			}

			if (((OCAPNuevaSolicitudForm) form).getCGerencia_id() != null) {
				OCAPCentroTrabajoLN centroTrabajoLN = new OCAPCentroTrabajoLN(usuario);
				listadoCentros = centroTrabajoLN.listarCentroTrabajo(((OCAPNuevaSolicitudForm) form).getCGerencia_id(),
						null);
				session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO_CARRERA, listadoCentros);
			} else {
				session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO_CARRERA, new ArrayList());
			}

			if (((OCAPNuevaSolicitudForm) form).getCProvCarreraActualId() != null) {
				listadoGerencias = gerenciasLN
						.listarGerenciasSolicitud(((OCAPNuevaSolicitudForm) form).getCProvCarreraActualId());
				session.setAttribute(Constantes.COMBO_GERENCIA_ACTUAL, listadoGerencias);
			} else {
				listadoGerencias = gerenciasLN.listadoOCAPGerencias();
				session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);
				session.setAttribute(Constantes.COMBO_GERENCIA_ACTUAL, listadoGerencias);
			}

			if (((OCAPNuevaSolicitudForm) form).getCGerenciaActualId() != null) {
				OCAPCentroTrabajoLN centroTrabajoLN = new OCAPCentroTrabajoLN(usuario);
				listadoCentros = centroTrabajoLN
						.listarCentroTrabajo(((OCAPNuevaSolicitudForm) form).getCGerenciaActualId(), null);
				session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO_CARRERA_ACTUAL, listadoCentros);
			} else {
				session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO_CARRERA_ACTUAL, new ArrayList());
			}

			request.setAttribute("primeraSolic", Constantes.SI);

			OCAPNuevaSolicitudLN solicitudesLN = new OCAPNuevaSolicitudLN(usuario);
			if (session.getAttribute(Constantes.COMBO_SITUACIONES) == null) {
				listaSituaciones = solicitudesLN.listaSituaciones();
				session.setAttribute(Constantes.COMBO_SITUACIONES, listaSituaciones);
			}
			if (session.getAttribute(Constantes.COMBO_VINCULOS) == null) {
				listaVinculos = solicitudesLN.listaVinculos();
				session.setAttribute(Constantes.COMBO_VINCULOS, listaVinculos);
			}

			if (session.getAttribute(Constantes.COMBO_TODAS_PROVINCIAS) == null) {
				OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(usuario);
				listaTodasProvincias = provinciasLN.listarProvincias();
				session.setAttribute(Constantes.COMBO_TODAS_PROVINCIAS, listaTodasProvincias);
			}

			Random rdm = new Random();
			int rl = rdm.nextInt();
			String hash1 = Integer.toHexString(rl);
			String capstr = hash1.substring(0, 5);

			String codigo_generado = (String) session.getAttribute("key");

			if (codigo_generado == null) {
				session.setAttribute("key", capstr);
			} else {
				session.removeAttribute("key");
				session.setAttribute("key", capstr);
			}

			((OCAPNuevaSolicitudForm) form).setCodigoCaptcha("");

			request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);
			request.setAttribute("tipoAccionBis", Constantes.SI);
			request.setAttribute("primeraCarga", Constantes.SI);

			OCAPConfigApp.logger.info(getClass().getName() + " irSolicitud: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irNuevaSolicitud");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irBuscarSolic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoGrados = null;
		ArrayList listadoConvocatorias = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irBuscarSolic: Inicio");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			listadoGrados = gradoLN.listarGrados();

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			listadoConvocatorias = convocatoriasLN.listarConvocatorias();

			session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, listadoGrados);
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);
			session.setAttribute("iRegistro", Integer.toString(1));

			((OCAPNuevaSolicitudForm) form).setjspInicio(true);
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irBuscarSolic");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward buscarSolicitudesDNI(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoSolicitudes = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudesDNI: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPNuevaSolicitudLN solicitudesLN = new OCAPNuevaSolicitudLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPNuevaSolicitudForm) request.getSession().getAttribute("OCAPNuevaSolicitudFormBuscador");
				request.setAttribute("OCAPNuevaSolicitudForm", form);
			} else {
				request.getSession().setAttribute("OCAPNuevaSolicitudFormBuscador", form);
				session.setAttribute("iRegistro", 1 + "");
			}

			int primerRegistro = 1;
			int registrosPorPagina = 20;
			String registro = null;

			if ((registro = request.getParameter("iRegistro")) != null)
				try {
					primerRegistro = Integer.parseInt(registro);
					session.setAttribute("iRegistro", primerRegistro + "");
				} catch (NumberFormatException ne) {
				}
			else if ((registro = session.getAttribute("iRegistro") == null ? null
					: session.getAttribute("iRegistro").toString()) != null) {
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

			if ((((OCAPNuevaSolicitudForm) form).getCDni() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCDni().equals(""))) {
				solicitudesOT.setCDni(((OCAPNuevaSolicitudForm) form).getCDni().toUpperCase());
			}
			if ((((OCAPNuevaSolicitudForm) form).getDApellido1() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getDApellido1().equals(""))) {
				solicitudesOT.setDApellido1(((OCAPNuevaSolicitudForm) form).getDApellido1());
			}
			if ((((OCAPNuevaSolicitudForm) form).getDNombre() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getDNombre().equals(""))) {
				solicitudesOT.setDNombre(((OCAPNuevaSolicitudForm) form).getDNombre());
			}
			if ((((OCAPNuevaSolicitudForm) form).getCGrado_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCGrado_id().equals(""))) {
				solicitudesOT.setCGrado_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGrado_id()));
			}
			if ((((OCAPNuevaSolicitudForm) form).getCConvocatoriaId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCConvocatoriaId().equals(""))) {
				solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));
			}
			listadoSolicitudes = solicitudesLN.buscarSolicitudes(primerRegistro, registrosPorPagina, solicitudesOT,
					jcylUsuario);
			int numSolicitudes = solicitudesLN.contarSolicitudes(solicitudesOT, jcylUsuario);
			session.setAttribute("numSolicitudes", new Integer(numSolicitudes));

			Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
			pagina.setElementos(listadoSolicitudes);
			pagina.setNRegistros(((Integer) session.getAttribute("numSolicitudes")).intValue());
			pagina.setRegistroActual(primerRegistro);
			pagina.setRegistrosPorPagina(registrosPorPagina);

			request.setAttribute("listados", pagina);

			OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudesDNI: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irBuscarSolic");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward detalleSolicitud(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     HttpSession session = request.getSession();
     ActionErrors errors = new ActionErrors();
     ArrayList listadoSolicitudes = null;
     OCAPSolicitudesOT solicitudOT = null;
 
     String forward = "";
     ArrayList listaEstados = null;
     ArrayList listaMotivos = null;
     boolean bPenaliza = false;
     boolean bSubsana = false;
     boolean bExcluye = false;
     boolean asociarSolicitudPgp = false;
     ArrayList listadoConvoc = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " detalleSolicitud: Inicio");
       validarAccion(mapping, form, request, response);
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPUsuariosLN usuarioLN = new OCAPUsuariosLN(jcylUsuario);
       OCAPConvocatoriasLN convoLN = new OCAPConvocatoriasLN(jcylUsuario);
       OCAPConvocatoriasOT convOT = null;
 
       OCAPEstadosLN estadosLN = new OCAPEstadosLN(jcylUsuario);
       listaEstados = estadosLN.listarEstadosFase("I", null);
 
       ArrayList estadosAct = comboEstados(listaEstados, bSubsana, bExcluye, jcylUsuario);
       session.setAttribute(Constantes.COMBO_ESTADOS, estadosAct);
       
       if (request.getParameter("asociar") != null && ((String) request.getParameter("asociar")).equals("true")){
    	   asociarSolicitudPgp = true;
       }else{
    	   asociarSolicitudPgp = false;
       }
       
       if (request.getParameter("cSolicitudId") != null)
       {
         int cSolicitudId = Integer.parseInt(request.getParameter("cSolicitudId"));
         OCAPNuevaSolicitudLN solicitudesLN = new OCAPNuevaSolicitudLN(jcylUsuario);
         solicitudOT = solicitudesLN.detalleSolicitud(cSolicitudId);
         convOT = convoLN.buscarOCAPConvocatorias(solicitudOT.getCConvocatoriaId());
         ((OCAPNuevaSolicitudForm)form).setCSolicitudId(cSolicitudId);
         request.setAttribute("opcion", "Tramitar");
       }
       else {
         OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
         solicitudOT = new OCAPSolicitudesOT();
         solicitudOT.setCExp_id(Long.parseLong(request.getParameter("CExp_id")));
         solicitudOT = solicitudesLN.datosSolicitud(solicitudOT, jcylUsuario);
         convOT = convoLN.buscarOCAPConvocatorias(solicitudOT.getCConvocatoriaId());
         ((OCAPNuevaSolicitudForm)form).setCSolicitudId(solicitudOT.getCSolicitudId());
 
         Date fecha = null;
         OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
         listadoConvoc = expedienteCarreraLN.buscarOCAPExpedienteRechaz(solicitudOT.getCUsr_id());
 
         for (int j = 0; j < listadoConvoc.size(); j++) {
           OCAPExpedientecarreraOT convocatorias = (OCAPExpedientecarreraOT)listadoConvoc.get(j);
           if ((fecha == null) || (convocatorias.getFRecGrado().after(fecha))) {
             fecha = convocatorias.getFRecGrado();
           }
         }
         if (fecha != null) {
           Date fFinPenaliza = DateUtil.addAnios(fecha, 2);
           if (fFinPenaliza.after(DateUtil.convertStringToDate(convOT.getFPublicacion()))) {
             ((OCAPNuevaSolicitudForm)form).setFRecGrado(DateUtil.convertDateToString(fecha));
             ((OCAPNuevaSolicitudForm)form).setFFin_penaliza(DateUtil.convertDateToString(fFinPenaliza));
             bPenaliza = true;
             request.setAttribute("penaliza", Constantes.SI);
           }
         }
 
         if ((convOT.getFAlegsolicitud() == null) || (DateUtil.convertStringToDate(convOT.getFAlegsolicitud()).after(new Date()))) {
           bSubsana = true;
         }
         if ((4 == solicitudOT.getCEstado_id()) && (solicitudOT.getFInconf_solic() == null)) {
           bExcluye = true;
         }
         OCAPEstadosOT estadoOT = estadosLN.buscarEstadosCompleto(solicitudOT.getCEstado_id());
 
         if ("I".equals(estadoOT.getDFase())) request.setAttribute("opcion", "Cambiar"); else {
           request.setAttribute("opcion", Constantes.VER_DETALLE);
         }
       }
       
       if (Utilidades.isNullOrIsEmpty(convOT.getFFinPGP()) || 
    		   (convOT.getFFinPGP() != null && !DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH).after(DateUtil.convertStringToDate(convOT.getFFinPGP())))) {
    	   ((OCAPNuevaSolicitudForm)form).setBFechaFinPgp(Constantes.NO);
       }else {
    	   ((OCAPNuevaSolicitudForm)form).setBFechaFinPgp(Constantes.SI);
       }

       ((OCAPNuevaSolicitudForm)form).setBCierreSo(convOT.getBCierreSo());
       ((OCAPNuevaSolicitudForm)form).setCSolicitudId(solicitudOT.getCSolicitudId());
 
       ((OCAPNuevaSolicitudForm)form).setCUsr_id(String.valueOf(solicitudOT.getCUsr_id()));
       ((OCAPNuevaSolicitudForm)form).setDNombre(solicitudOT.getDNombre());
       ((OCAPNuevaSolicitudForm)form).setDApellido1(solicitudOT.getDApellido1());
       ((OCAPNuevaSolicitudForm)form).setCDniReal(solicitudOT.getCDniReal());
       ((OCAPNuevaSolicitudForm)form).setBSexo(solicitudOT.getBSexo());
       ((OCAPNuevaSolicitudForm)form).setNTelefono1((solicitudOT.getNTelefono1() != null) && (!"0".equals(solicitudOT.getNTelefono1())) ? solicitudOT.getNTelefono1() : "");
       ((OCAPNuevaSolicitudForm)form).setNTelefono2((solicitudOT.getNTelefono2() != null) && (!"0".equals(solicitudOT.getNTelefono2())) ? solicitudOT.getNTelefono2() : "");
       ((OCAPNuevaSolicitudForm)form).setDCorreoelectronico(solicitudOT.getDCorreoelectronico());
       ((OCAPNuevaSolicitudForm)form).setDDomicilio(solicitudOT.getDDomicilio());
       ((OCAPNuevaSolicitudForm)form).setDProvincia(solicitudOT.getDProvincia());
 
       OCAPProvinciasLN provinciaLN = new OCAPProvinciasLN(jcylUsuario);
       OCAPProvinciasOT provinciaOT = null;
       if ((solicitudOT.getCProvincia_id() != null) && (!solicitudOT.getCProvincia_id().equals(""))) {
         provinciaOT = provinciaLN.buscarProvincia(solicitudOT.getCProvincia_id());
         ((OCAPNuevaSolicitudForm)form).setDProvincia(Cadenas.capitalizar(provinciaOT.getDProvincia()));
         ((OCAPNuevaSolicitudForm)form).setCProvincia_id(String.valueOf(solicitudOT.getCProvincia_id()));
       }

       ((OCAPNuevaSolicitudForm)form).setDLocalidad(solicitudOT.getDLocalidad());
       if (solicitudOT.getDLocalidad() != null) {
    	   OCAPLocalidadesLN locLN = new OCAPLocalidadesLN(jcylUsuario);
           OCAPLocalidadesOT locOT = null;
           locOT = locLN.buscarLocalidadByName(solicitudOT.getDLocalidad());
         ((OCAPNuevaSolicitudForm)form).setCLocalidad_id(locOT.getCLocalidadId());
       }
       DecimalFormat formato = new DecimalFormat("00000");
       ((OCAPNuevaSolicitudForm)form).setCPostalUsu(solicitudOT.getCPostalUsu() == null ? "" : formato.format(Long.parseLong(solicitudOT.getCPostalUsu())));
 
       OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
       OCAPGradoOT gradoOT = null;
       if (solicitudOT.getCGrado_id() != 0L) {
         gradoOT = gradoLN.buscarOCAPGrado(solicitudOT.getCGrado_id());
         ((OCAPNuevaSolicitudForm)form).setDGrado_des(gradoOT.getDDescripcion());
         ((OCAPNuevaSolicitudForm)form).setCGrado_id(String.valueOf(solicitudOT.getCGrado_id()));
       }
 
       OCAPModalidadAnteriorLN modalidadAnteriorLN = new OCAPModalidadAnteriorLN(jcylUsuario);
       OCAPModalidadAnteriorOT modaliadAnteriorOT = null;
       if (solicitudOT.getCModAnterior_id() != 0L) {
         modaliadAnteriorOT = modalidadAnteriorLN.buscarOCAPModalidad(solicitudOT.getCModAnterior_id());
         ((OCAPNuevaSolicitudForm)form).setDModAnterior(modaliadAnteriorOT.getDDescripcion());
         ((OCAPNuevaSolicitudForm)form).setCModAnteriorId(String.valueOf(solicitudOT.getCModAnterior_id()));
         ((OCAPNuevaSolicitudForm)form).setCGradoAnteriorId(String.valueOf(modaliadAnteriorOT.getCGradoId()));
       }
 
       OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
       OCAPProcedimientoOT procOT = null;
       if ((solicitudOT.getCProcedimientoId() != null) && (!solicitudOT.getCProcedimientoId().equals(""))) {
         procOT = procLN.buscarOCAPProcedimiento(Long.parseLong(solicitudOT.getCProcedimientoId()));
         ((OCAPNuevaSolicitudForm)form).setDProcedimiento(procOT.getDNombre());
         ((OCAPNuevaSolicitudForm)form).setCProcedimientoId(solicitudOT.getCProcedimientoId());
       }
 
       OCAPCategProfesionalesLN categProfLN = new OCAPCategProfesionalesLN(jcylUsuario);
       OCAPCategProfesionalesOT categProfOT = null;
       if (solicitudOT.getCProfesional_id() != 0L) {
         categProfOT = categProfLN.buscarOCAPCategProfesionales(solicitudOT.getCProfesional_id());
         ((OCAPNuevaSolicitudForm)form).setDProfesional_nombre(categProfOT.getDNombre());
         ((OCAPNuevaSolicitudForm)form).setCProfesional_id(String.valueOf(solicitudOT.getCProfesional_id()));
       }
 
       ((OCAPNuevaSolicitudForm)form).setCJuridicoId(solicitudOT.getCJuridicoId());
       if ("3".equals(solicitudOT.getCJuridicoId())) {
         ((OCAPNuevaSolicitudForm)form).setDRegimenJuridicoOtros(solicitudOT.getDRegimenJuridicoOtros());
       }
 
       convoLN = new OCAPConvocatoriasLN(jcylUsuario);
       OCAPConvocatoriasOT convoOT = null;
       convoOT = convoLN.buscarOCAPConvocatorias(solicitudOT.getCConvocatoriaId());
       ((OCAPNuevaSolicitudForm)form).setDConvocatoria_nombre(convoOT.getDNombre());
 
       OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
       OCAPEstatutarioOT estatutarioOT = null;
       if (categProfOT != null) {
         estatutarioOT = estatutarioLN.buscarOCAPEstatutario(categProfOT.getCEstatutId());
         ((OCAPNuevaSolicitudForm)form).setCEstatutId(categProfOT.getCEstatutId());
         if ("1".equals(solicitudOT.getCJuridicoId())) {
           ((OCAPNuevaSolicitudForm)form).setCJuridicoCombo(String.valueOf(estatutarioOT.getCPersonalId()));
           if (solicitudOT.getCProfesional_id() != 0L)
             ((OCAPNuevaSolicitudForm)form).setCProfesionalFijo_id(String.valueOf(solicitudOT.getCProfesional_id()));
           if (solicitudOT.getCEspec_id() != 0L) {
             ((OCAPNuevaSolicitudForm)form).setCEspecFijo_id(String.valueOf(solicitudOT.getCEspec_id()));
           }
         }
       }
 
       OCAPEspecialidadesLN especLN = new OCAPEspecialidadesLN(jcylUsuario);
       OCAPEspecialidadesOT especOT = null;
       if (solicitudOT.getCEspec_id() != 0L) {
         especOT = especLN.buscarOCAPEspecialidades(solicitudOT.getCEspec_id());
         ((OCAPNuevaSolicitudForm)form).setDEspec_nombre(especOT.getDNombre());
         ((OCAPNuevaSolicitudForm)form).setCEspec_id(String.valueOf(solicitudOT.getCEspec_id()));
       }
 
       ((OCAPNuevaSolicitudForm)form).setCSitAdministrativaId(solicitudOT.getCSitAdministrativaAuxId());
 
       ((OCAPNuevaSolicitudForm)form).setDSitAdministrativaOtros(solicitudOT.getDSitAdministrativaOtros());
 
       OCAPProvinciasOT provinciaCarreraOT = null;
       if ((solicitudOT.getCProvinciaCarrera_id() != null) && (!solicitudOT.getCProvinciaCarrera_id().equals(""))) {
         provinciaCarreraOT = provinciaLN.buscarProvincia(String.valueOf(solicitudOT.getCProvinciaCarrera_id()));
         ((OCAPNuevaSolicitudForm)form).setDProvinciaCarrera(Cadenas.capitalizar(provinciaCarreraOT.getDProvincia()));
         ((OCAPNuevaSolicitudForm)form).setCProvinciaCarrera_id(String.valueOf(solicitudOT.getCProvinciaCarrera_id()));
       }
 
       OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
       OCAPGerenciasOT gerenciasOT = null;
       if (solicitudOT.getCGerencia_id() != 0L) {
         gerenciasOT = gerenciasLN.buscarOCAPGerencias(solicitudOT.getCGerencia_id());
         ((OCAPNuevaSolicitudForm)form).setDGerencia_nombre(gerenciasOT.getDNombre());
         ((OCAPNuevaSolicitudForm)form).setCGerencia_id(String.valueOf(solicitudOT.getCGerencia_id()));
       }
 
       OCAPCentroTrabajoLN centroLN = new OCAPCentroTrabajoLN(jcylUsuario);
       OCAPCentroTrabajoOT centroOT = null;
       if (solicitudOT.getCCentrotrabajo_id() != 0L) {
         centroOT = centroLN.buscarOCAPCentroTrabajo(solicitudOT.getCCentrotrabajo_id());
         ((OCAPNuevaSolicitudForm)form).setDCentrotrabajo_nombre(centroOT.getDNombre());
         ((OCAPNuevaSolicitudForm)form).setCCentrotrabajo_id(String.valueOf(solicitudOT.getCCentrotrabajo_id()));
       }
 
       ((OCAPNuevaSolicitudForm)form).setCSitAdmonActualId(String.valueOf(solicitudOT.getCSitAdmonActualId()));
       ((OCAPNuevaSolicitudForm)form).setAOtraSitAdmonActual(solicitudOT.getAOtraSitAdmonActual());
 
       OCAPProvinciasOT provCarreraActualOT = null;
       if ((solicitudOT.getCProvCarreraActualId() != null) && (!"".equals(solicitudOT.getCProvCarreraActualId()))) {
         provCarreraActualOT = provinciaLN.buscarProvincia(String.valueOf(solicitudOT.getCProvCarreraActualId()));
         ((OCAPNuevaSolicitudForm)form).setDProvCarreraActual(Cadenas.capitalizar(provCarreraActualOT.getDProvincia()));
         ((OCAPNuevaSolicitudForm)form).setCProvCarreraActualId(String.valueOf(solicitudOT.getCProvCarreraActualId()));
       }
 
       OCAPGerenciasOT gerenciaActualOT = null;
       if (solicitudOT.getCGerenciaActualId() != 0L) {
         gerenciaActualOT = gerenciasLN.buscarOCAPGerencias(solicitudOT.getCGerenciaActualId());
         ((OCAPNuevaSolicitudForm)form).setDGerenciaActual(gerenciaActualOT.getDNombre());
         ((OCAPNuevaSolicitudForm)form).setCGerenciaActualId(String.valueOf(solicitudOT.getCGerenciaActualId()));
       }
 
       OCAPCentroTrabajoOT centroActualOT = null;
       if (solicitudOT.getCCentroTrabActualId() != 0L) {
         centroActualOT = centroLN.buscarOCAPCentroTrabajo(solicitudOT.getCCentroTrabActualId());
         ((OCAPNuevaSolicitudForm)form).setDCentroTrabActual(centroActualOT.getDNombre());
         ((OCAPNuevaSolicitudForm)form).setCCentroTrabActualId(String.valueOf(solicitudOT.getCCentroTrabActualId()));
       }
 
       ((OCAPNuevaSolicitudForm)form).setNAniosantiguedad(String.valueOf(solicitudOT.getNAniosantiguedad()));
       ((OCAPNuevaSolicitudForm)form).setNMesesantiguedad(String.valueOf(solicitudOT.getNMesesantiguedad()));
       ((OCAPNuevaSolicitudForm)form).setNDiasantiguedad(String.valueOf(solicitudOT.getNDiasantiguedad()));
 
       ((OCAPNuevaSolicitudForm)form).setCConvocatoriaId(String.valueOf(solicitudOT.getCConvocatoriaId()));
       ((OCAPNuevaSolicitudForm)form).setBOtroServicio(solicitudOT.getBOtroServicio());
       ((OCAPNuevaSolicitudForm)form).setADondeServicio(solicitudOT.getADondeServicio());
       ((OCAPNuevaSolicitudForm)form).setResumenCentros(solicitudOT.getResumenCentros());
       ((OCAPNuevaSolicitudForm)form).setFRegistro_solic(solicitudOT.getFRegistro_solic());
       ((OCAPNuevaSolicitudForm)form).setDServicio(solicitudOT.getAServicio());
       ((OCAPNuevaSolicitudForm)form).setDPuesto(solicitudOT.getAPuesto());
       if(!asociarSolicitudPgp){
    	   ((OCAPNuevaSolicitudForm)form).setFRegistro_oficial(solicitudOT.getFRegistro_oficial());
       }
 
       OCAPEstadosOT estadoOT = estadosLN.buscarEstados(solicitudOT.getCEstado_id());
       ((OCAPNuevaSolicitudForm)form).setCEstado(estadoOT.getDNombre());
       ((OCAPNuevaSolicitudForm)form).setCEstadoId(solicitudOT.getCEstado_id());
       ((OCAPNuevaSolicitudForm)form).setFInconf_solic(DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, solicitudOT.getFInconf_solic()));
       ((OCAPNuevaSolicitudForm)form).setDMotivo_inconf_solic(solicitudOT.getDMotivo_inconf_solic());
       ((OCAPNuevaSolicitudForm)form).setFNegacion_solic(DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, solicitudOT.getFNegacion_solic()));
       ((OCAPNuevaSolicitudForm)form).setAnioConvocatoria(solicitudOT.getDAnioConvocatoria());
 
       OCAPMotExclusionLN motivosExcLN = new OCAPMotExclusionLN(jcylUsuario);
       if (session.getAttribute("listaMotivosExclusion") == null) {
    	  /*
		   * OCAP-103 
		   */    	   
         ArrayList listaMotivosExclusion = motivosExcLN.listarMotivosExclusion(convOT.getDAnioConvocatoria());
         session.setAttribute("listaMotivosExclusion", listaMotivosExclusion);
         listaMotivosExclusion = null;
       }
 
       if (solicitudOT.getCEstado_id() == 4)
       {
         OCAPExpedientesExclusionOT lista = null;
         OCAPExpedientesExclusionLN motivosLN = new OCAPExpedientesExclusionLN(jcylUsuario);
         listaMotivos = motivosLN.buscarOCAPExpedientesExclusion(solicitudOT);
 
         int numMotivos = listaMotivos.size();
         String[] c_motivos_exclusion = new String[numMotivos];
 
         for (int j = 0; j < numMotivos; j++)
         {
           lista = (OCAPExpedientesExclusionOT)listaMotivos.get(j);
           c_motivos_exclusion[j] = (String.valueOf(lista.getCExclusionId()) + "#" + lista.getDNombre());
           if ((lista.getDOtrosMotivos() != null) && (!"".equals(lista.getDOtrosMotivos()))) {
             ((OCAPNuevaSolicitudForm)form).setDOtrosMotivosExclusion(lista.getDOtrosMotivos());
           }
 
         }
 
         ((OCAPNuevaSolicitudForm)form).setCMotivosExclusion(c_motivos_exclusion);
         c_motivos_exclusion = null;
         listaMotivos = null;
       }
 
       listadoSolicitudes = usuarioLN.buscarOCAPUsuariosPorDniReal(solicitudOT.getCDniReal());
       ((OCAPNuevaSolicitudForm)form).setListaUsuarios(listadoSolicitudes);
 
       request.setAttribute("tipoAccion", Constantes.IR_DETALLE);
       request.setAttribute("tipoAccionBis", Constantes.IR_DETALLE);
 
       if (bPenaliza) request.setAttribute("penaliza", Constantes.SI);
 
       if (("Tramitar".equals(request.getParameter("opcion"))) && (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())))
         request.setAttribute("opcion", "Reasociar");
       else if ((Constantes.VER_DETALLE.equals(request.getParameter("opcion"))) || (Constantes.VER_DETALLE.equals(request.getAttribute("opcion"))))
         request.setAttribute("opcion", Constantes.VER_DETALLE);
       else if (("Cambiar".equals(request.getParameter("opcion"))) || ("Cambiar".equals(request.getAttribute("opcion"))))
         request.setAttribute("opcion", "Cambiar");
       else {
         request.setAttribute("opcion", "Tramitar");
       }
       request.setAttribute("vuelta", request.getParameter("vuelta") == null ? request.getAttribute("vuelta") : request.getParameter("vuelta"));
        
       session.setAttribute(Constantes.COMBO_CONVOCATORIAS_ALTA, convoLN.listarConvocatoriasActivasAlta());
       
       	/* Se fija si la solicitud está en plazo de ser modificada */
       	session.setAttribute(Constantes.PARAM_SOLICITUD_MODIFICABLE,"false");
		for (OCAPConvocatoriasOT conv : (ArrayList<OCAPConvocatoriasOT>) session
				.getAttribute(Constantes.COMBO_CONVOCATORIAS_ALTA)) {
			if (String.valueOf(conv.getCConvocatoriaId())
					.equals(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId())) {
				session.setAttribute(Constantes.PARAM_SOLICITUD_MODIFICABLE,"true");
				break;
			}
		}
		
       
       if (Constantes.MODIFICAR.equals(request.getParameter("modificar")))
       {
         forward = "irNuevaModificar";
 
         if (session.getAttribute(Constantes.COMBO_GRADOS_ALTA) == null) {
           ArrayList listadoGrados = gradoLN.listarGrados();
 
           if ((listadoGrados == null) || (listadoGrados.size() == 0)) {
             return mapping.findForward("irNoExistenConvocatoriasSolic");
           }
           session.setAttribute(Constantes.COMBO_GRADOS_ALTA, listadoGrados);
         }
 
         if (session.getAttribute(Constantes.COMBO_PERSONAL) == null) {
           ArrayList listadoProcedimientos = procLN.listadoOCAPProcedimiento();
           session.setAttribute(Constantes.COMBO_PERSONAL, listadoProcedimientos);
         }
 
         if (session.getAttribute(Constantes.COMBO_REGIMEN_JURIDICO) == null) {
           OCAPRegimenJuridicoLN regJuridicoLN = new OCAPRegimenJuridicoLN(jcylUsuario);
           ArrayList listadoRegimenJuridico = regJuridicoLN.listadoOCAPRegimenJuridico();
           session.setAttribute(Constantes.COMBO_REGIMEN_JURIDICO, listadoRegimenJuridico);
         }
 
         if (session.getAttribute(Constantes.COMBO_SIT_ADMIN) == null) {
           OCAPSit_AdministrativaLN sit_AdministrativaLN = new OCAPSit_AdministrativaLN(jcylUsuario);
           ArrayList listadoSit_Administrativas = sit_AdministrativaLN.listadoOCAPSit_Administrativa();
           session.setAttribute(Constantes.COMBO_SIT_ADMIN, listadoSit_Administrativas);
         }
 
         if (session.getAttribute(Constantes.COMBO_TODAS_PROVINCIAS) == null) {
           ArrayList listaTodasProvincias = provinciaLN.listarProvincias();
           session.setAttribute(Constantes.COMBO_TODAS_PROVINCIAS, listaTodasProvincias);
         }
 
         if (session.getAttribute(Constantes.COMBO_PROVINCIAS) == null) {
           ArrayList listadoProvincias = provinciaLN.listarProvinciasComunidad("CL");
           session.setAttribute(Constantes.COMBO_PROVINCIAS, listadoProvincias);
         }
 
         if (session.getAttribute(Constantes.COMBO_MOD_ANTERIOR) == null) {
           ArrayList listadoModAnterior = modalidadAnteriorLN.listarModalidadAnterior();
           session.setAttribute(Constantes.COMBO_MOD_ANTERIOR, listadoModAnterior);
         }
 
         if ((((OCAPNuevaSolicitudForm)form).getCProvincia_id() != null) && (!((OCAPNuevaSolicitudForm)form).getCProvincia_id().equals(""))) {
           OCAPLocalidadesLN localidadesLN = new OCAPLocalidadesLN(jcylUsuario);
           ArrayList listadoLocalidades = localidadesLN.listarLocalidadesProvincia(((OCAPNuevaSolicitudForm)form).getCProvincia_id());
           session.setAttribute(Constantes.COMBO_LOCALIDADES, listadoLocalidades);
         } else {
           session.setAttribute(Constantes.COMBO_LOCALIDADES, new ArrayList());
         }
 
         OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
         if ((((OCAPNuevaSolicitudForm)form).getCJuridicoId() != null) && (!((OCAPNuevaSolicitudForm)form).getCJuridicoId().equals(""))) {
           ArrayList listadoCategorias = categProfesionalesLN.listarCategoriasRJ(((OCAPNuevaSolicitudForm)form).getCJuridicoId());
           session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
         } else {
           session.setAttribute(Constantes.COMBO_CATEGORIA, new ArrayList());
         }
 
         OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
         if ((((OCAPNuevaSolicitudForm)form).getCProfesional_id() != null) && (!((OCAPNuevaSolicitudForm)form).getCProfesional_id().equals(""))) {
           ArrayList listadoEspecialidades = especialidadesLN.listarEspecialidades(Long.parseLong(((OCAPNuevaSolicitudForm)form).getCProfesional_id()));
           session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
         } else if ((((OCAPNuevaSolicitudForm)form).getCProfesionalFijo_id() != null) && (!((OCAPNuevaSolicitudForm)form).getCProfesionalFijo_id().equals(""))) {
           ArrayList listadoEspecialidades = especialidadesLN.listarEspecialidades(Long.parseLong(((OCAPNuevaSolicitudForm)form).getCProfesionalFijo_id()));
           session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
         } else {
           session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
         }
 
         OCAPNuevaSolicitudLN solicitudesLN = new OCAPNuevaSolicitudLN(jcylUsuario);
         ArrayList listaVinculos = solicitudesLN.listaVinculos();
         session.setAttribute(Constantes.COMBO_VINCULOS, listaVinculos);
 
         if ((((OCAPNuevaSolicitudForm)form).getCProvinciaCarrera_id() != null) && (!"".equals(((OCAPNuevaSolicitudForm)form).getCProvinciaCarrera_id())))
         {
           ArrayList listadoGerencias = gerenciasLN.listarGerenciasSolicitud(((OCAPNuevaSolicitudForm)form).getCProvinciaCarrera_id());
           session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);
         } else {
           session.setAttribute(Constantes.COMBO_GERENCIA, new ArrayList());
         }
 
         if ((((OCAPNuevaSolicitudForm)form).getCGerencia_id() != null) && (!"".equals(((OCAPNuevaSolicitudForm)form).getCGerencia_id())))
         {
           ArrayList listadoCentros = centroLN.listarCentroTrabajo(((OCAPNuevaSolicitudForm)form).getCGerencia_id(), null);
           session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO_CARRERA, listadoCentros);
         } else {
           session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO_CARRERA, new ArrayList());
         }
 
         ((OCAPNuevaSolicitudForm)form).setCExp_id(request.getParameter("CExp_id"));
         request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);
       }
       else
       {
         forward = "irNuevaInsertar";
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " detalleSolicitud: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward(forward);
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }

	public ActionForward detalleExpediente(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoSolicitudes = null;
		OCAPSolicitudesOT solicitudOT = null;
		OCAPConvocatoriasOT convOT = null;
		String forward = "";
		ArrayList listaEstados = null;
		ArrayList listaMotivos = null;
		boolean bPenaliza = false;
		boolean bSubsana = false;
		boolean bExcluye = false;
		ArrayList listadoConvoc = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " detalleExpediente: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			if (request.getParameter("CExp_id") != null) {
				OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
				solicitudOT = new OCAPSolicitudesOT();
				solicitudOT.setCExp_id(Long.parseLong(request.getParameter("CExp_id")));
				solicitudOT = solicitudesLN.datosSolicitud(solicitudOT, jcylUsuario);

				OCAPConvocatoriasLN convoLN = new OCAPConvocatoriasLN(jcylUsuario);
				convOT = convoLN.buscarOCAPConvocatorias(solicitudOT.getCConvocatoriaId());
				
				if (null!= convOT){
					((OCAPNuevaSolicitudForm) form).setDConvocatoria_nombre(convOT.getDNombre());					
				}

				Date fecha = null;
				OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
				listadoConvoc = expedienteCarreraLN.buscarOCAPExpedienteRechaz(solicitudOT.getCUsr_id());

				for (int j = 0; j < listadoConvoc.size(); j++) {
					OCAPExpedientecarreraOT convocatorias = (OCAPExpedientecarreraOT) listadoConvoc.get(j);
					if ((fecha == null) || (convocatorias.getFRecGrado() != null && convocatorias.getFRecGrado().after(fecha))) {
						fecha = convocatorias.getFRecGrado();
					}
				}
				if (fecha != null) {
					Date fFinPenaliza = DateUtil.addAnios(fecha, 2);
					if (fFinPenaliza.after(DateUtil.convertStringToDate(convOT.getFPublicacion()))) {
						((OCAPNuevaSolicitudForm) form).setFRecGrado(DateUtil.convertDateToString(fecha));
						((OCAPNuevaSolicitudForm) form).setFFin_penaliza(DateUtil.convertDateToString(fFinPenaliza));
						bPenaliza = true;
						request.setAttribute("penaliza", Constantes.SI);
					}
				}

				if ((convOT.getFAlegsolicitud() == null)
						|| (DateUtil.convertStringToDate(convOT.getFAlegsolicitud()).after(new Date()))) {
					bSubsana = true;
				}
				if ((4 == solicitudOT.getCEstado_id()) && (solicitudOT.getFInconf_solic() == null)) {
					bExcluye = true;
				}
				OCAPEstadosLN estadosLN = new OCAPEstadosLN(jcylUsuario);
				listaEstados = estadosLN.listarEstadosFase("I", null);
				ArrayList estadosAct = comboEstados(listaEstados, bSubsana, bExcluye, jcylUsuario);
				session.setAttribute(Constantes.COMBO_ESTADOS, estadosAct);
				OCAPEstadosOT estadoOT = estadosLN.buscarEstadosCompleto(solicitudOT.getCEstado_id());

				OCAPNuevaSolicitudLN nuevaSolicitudLN = new OCAPNuevaSolicitudLN(jcylUsuario);
				OCAPSolicitudesOT solicitudUsuarioOT = nuevaSolicitudLN.detalleSolicitud(solicitudOT.getCSolicitudId());
				
				if ("I".equals(estadoOT.getDFase()))
					request.setAttribute("opcion", "Cambiar");
				else {
					request.setAttribute("opcion", Constantes.VER_DETALLE);
				}
				
		       if (Utilidades.isNullOrIsEmpty(convOT.getFFinPGP()) || 
		    		   (convOT.getFFinPGP() != null && !DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH).after(DateUtil.convertStringToDate(convOT.getFFinPGP())))) {
		    	   ((OCAPNuevaSolicitudForm)form).setBFechaFinPgp(Constantes.NO);
		       }else {
		    	   ((OCAPNuevaSolicitudForm)form).setBFechaFinPgp(Constantes.SI);
		       }
				
				((OCAPNuevaSolicitudForm) form).setCSolicitudId(solicitudOT.getCSolicitudId());
				((OCAPNuevaSolicitudForm) form).setBCierreSo(convOT.getBCierreSo());

				((OCAPNuevaSolicitudForm) form).setCUsr_id(String.valueOf(solicitudOT.getCUsr_id()));
				((OCAPNuevaSolicitudForm) form).setDNombre(solicitudUsuarioOT.getDNombre());
				((OCAPNuevaSolicitudForm) form).setDApellido1(solicitudUsuarioOT.getDApellido1());
				((OCAPNuevaSolicitudForm) form).setCDniReal(solicitudOT.getCDniReal());
				((OCAPNuevaSolicitudForm) form).setBSexo(solicitudUsuarioOT.getBSexo());
				((OCAPNuevaSolicitudForm) form).setNTelefono1(
						(solicitudUsuarioOT.getNTelefono1() != null) && (!"0".equals(solicitudUsuarioOT.getNTelefono1()))
								? solicitudUsuarioOT.getNTelefono1() : "");
				((OCAPNuevaSolicitudForm) form).setNTelefono2(
						(solicitudUsuarioOT.getNTelefono2() != null) && (!"0".equals(solicitudUsuarioOT.getNTelefono2()))
								? solicitudUsuarioOT.getNTelefono2() : "");
				((OCAPNuevaSolicitudForm) form).setDCorreoelectronico(solicitudUsuarioOT.getDCorreoelectronico());
				((OCAPNuevaSolicitudForm) form).setDDomicilio(solicitudUsuarioOT.getDDomicilio());
				((OCAPNuevaSolicitudForm) form).setDProvincia(solicitudUsuarioOT.getDProvincia());

				OCAPProvinciasLN provinciaLN = new OCAPProvinciasLN(jcylUsuario);
				OCAPProvinciasOT provinciaOT = null;
				if ((solicitudUsuarioOT.getCProvincia_id() != null) && (!solicitudUsuarioOT.getCProvincia_id().equals(""))) {
					provinciaOT = provinciaLN.buscarProvincia(solicitudUsuarioOT.getCProvincia_id());
					((OCAPNuevaSolicitudForm) form).setDProvincia(Cadenas.capitalizar(provinciaOT.getDProvincia()));
					((OCAPNuevaSolicitudForm) form).setCProvincia_id(String.valueOf(solicitudUsuarioOT.getCProvincia_id()));
				}

				((OCAPNuevaSolicitudForm) form).setDLocalidad(solicitudUsuarioOT.getDLocalidad());
				if (solicitudUsuarioOT.getDLocalidad() != null) {
					OCAPLocalidadesLN localiLn = new OCAPLocalidadesLN(jcylUsuario);
					OCAPLocalidadesOT locOt = localiLn.buscarLocalidadByName(solicitudUsuarioOT.getDLocalidad());
					((OCAPNuevaSolicitudForm) form).setCLocalidad_id(locOt.getCLocalidadId());
				}
				DecimalFormat formato = new DecimalFormat("00000");
				((OCAPNuevaSolicitudForm) form).setCPostalUsu(solicitudUsuarioOT.getCPostalUsu() == null ? ""
						: formato.format(Long.parseLong(solicitudUsuarioOT.getCPostalUsu())));

				OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
				OCAPGradoOT gradoOT = null;
				if (solicitudOT.getCGrado_id() != 0L) {
					gradoOT = gradoLN.buscarOCAPGrado(solicitudOT.getCGrado_id());
					((OCAPNuevaSolicitudForm) form).setDGrado_des(gradoOT.getDDescripcion());
					((OCAPNuevaSolicitudForm) form).setCGrado_id(String.valueOf(solicitudOT.getCGrado_id()));
				}

				OCAPModalidadAnteriorLN modalidadAnteriorLN = new OCAPModalidadAnteriorLN(jcylUsuario);
				OCAPModalidadAnteriorOT modaliadAnteriorOT = null;
				if (solicitudOT.getCModAnterior_id() != 0L) {
					modaliadAnteriorOT = modalidadAnteriorLN.buscarOCAPModalidad(solicitudOT.getCModAnterior_id());
					((OCAPNuevaSolicitudForm) form).setDModAnterior(modaliadAnteriorOT.getDDescripcion());
					((OCAPNuevaSolicitudForm) form).setCModAnteriorId(String.valueOf(solicitudOT.getCModAnterior_id()));
					((OCAPNuevaSolicitudForm) form)
							.setCGradoAnteriorId(String.valueOf(modaliadAnteriorOT.getCGradoId()));
				}

				OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
				OCAPProcedimientoOT procOT = null;
				if ((solicitudOT.getCProcedimientoId() != null) && (!solicitudOT.getCProcedimientoId().equals(""))) {
					procOT = procLN.buscarOCAPProcedimiento(Long.parseLong(solicitudOT.getCProcedimientoId()));
					((OCAPNuevaSolicitudForm) form).setDProcedimiento(procOT.getDNombre());
					((OCAPNuevaSolicitudForm) form).setCProcedimientoId(solicitudOT.getCProcedimientoId());
				}

				OCAPCategProfesionalesLN categProfLN = new OCAPCategProfesionalesLN(jcylUsuario);
				OCAPCategProfesionalesOT categProfOT = null;
				if (solicitudOT.getCProfesional_id() != 0L) {
					categProfOT = categProfLN.buscarOCAPCategProfesionales(solicitudOT.getCProfesional_id());
					((OCAPNuevaSolicitudForm) form).setDProfesional_nombre(categProfOT.getDNombre());
					((OCAPNuevaSolicitudForm) form)
							.setCProfesional_id(String.valueOf(solicitudOT.getCProfesional_id()));
				}

				((OCAPNuevaSolicitudForm) form).setCJuridicoId(solicitudOT.getCJuridicoId());
				if ("3".equals(solicitudOT.getCJuridicoId())) {
					((OCAPNuevaSolicitudForm) form).setDRegimenJuridicoOtros(solicitudOT.getDRegimenJuridicoOtros());
				}

				OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
				OCAPEstatutarioOT estatutarioOT = null;
				if (categProfOT != null) {
					estatutarioOT = estatutarioLN.buscarOCAPEstatutario(categProfOT.getCEstatutId());
					((OCAPNuevaSolicitudForm) form).setCEstatutId(categProfOT.getCEstatutId());
					if ("1".equals(solicitudOT.getCJuridicoId())) {
						((OCAPNuevaSolicitudForm) form)
								.setCJuridicoCombo(String.valueOf(estatutarioOT.getCPersonalId()));
						if (solicitudOT.getCProfesional_id() != 0L)
							((OCAPNuevaSolicitudForm) form)
									.setCProfesionalFijo_id(String.valueOf(solicitudOT.getCProfesional_id()));
						if (solicitudOT.getCEspec_id() != 0L) {
							((OCAPNuevaSolicitudForm) form)
									.setCEspecFijo_id(String.valueOf(solicitudOT.getCEspec_id()));
						}
					}
				}

				OCAPEspecialidadesLN especLN = new OCAPEspecialidadesLN(jcylUsuario);
				OCAPEspecialidadesOT especOT = null;
				if (solicitudOT.getCEspec_id() != 0L) {
					especOT = especLN.buscarOCAPEspecialidades(solicitudOT.getCEspec_id());
					((OCAPNuevaSolicitudForm) form).setDEspec_nombre(especOT.getDNombre());
					((OCAPNuevaSolicitudForm) form).setCEspec_id(String.valueOf(solicitudOT.getCEspec_id()));
				}

				((OCAPNuevaSolicitudForm) form).setCSitAdministrativaId(solicitudOT.getCSitAdministrativaAuxId());

				((OCAPNuevaSolicitudForm) form).setDSitAdministrativaOtros(solicitudOT.getDSitAdministrativaOtros());

				OCAPProvinciasOT provinciaCarreraOT = null;
				if ((solicitudUsuarioOT.getCProvCarreraActualId() != null)
						&& (!solicitudUsuarioOT.getCProvCarreraActualId().equals(""))) {
					provinciaCarreraOT = provinciaLN
							.buscarProvincia(String.valueOf(solicitudUsuarioOT.getCProvCarreraActualId()));
					((OCAPNuevaSolicitudForm) form)
							.setDProvinciaCarrera(Cadenas.capitalizar(provinciaCarreraOT.getDProvincia()));
					((OCAPNuevaSolicitudForm) form)
							.setCProvinciaCarrera_id(String.valueOf(solicitudUsuarioOT.getCProvCarreraActualId()));
				}

				OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
				OCAPGerenciasOT gerenciasOT = null;
				if (solicitudUsuarioOT.getCGerenciaActualId() != 0L) {
					gerenciasOT = gerenciasLN.buscarOCAPGerencias(solicitudUsuarioOT.getCGerenciaActualId());
					((OCAPNuevaSolicitudForm) form).setDGerencia_nombre(gerenciasOT.getDNombre());
					((OCAPNuevaSolicitudForm) form).setCGerencia_id(String.valueOf(solicitudUsuarioOT.getCGerenciaActualId()));
				}

				OCAPCentroTrabajoLN centroLN = new OCAPCentroTrabajoLN(jcylUsuario);
				OCAPCentroTrabajoOT centroOT = null;
				if (solicitudUsuarioOT.getCCentroTrabActualId() != 0L) {
					centroOT = centroLN.buscarOCAPCentroTrabajo(solicitudUsuarioOT.getCCentroTrabActualId());
					((OCAPNuevaSolicitudForm) form).setDCentrotrabajo_nombre(centroOT.getDNombre());
					((OCAPNuevaSolicitudForm) form)
							.setCCentrotrabajo_id(String.valueOf(solicitudUsuarioOT.getCCentroTrabActualId()));
				}

				((OCAPNuevaSolicitudForm) form).setNAniosantiguedad(String.valueOf(solicitudOT.getNAniosantiguedad()));
				((OCAPNuevaSolicitudForm) form).setNMesesantiguedad(String.valueOf(solicitudOT.getNMesesantiguedad()));
				((OCAPNuevaSolicitudForm) form).setNDiasantiguedad(String.valueOf(solicitudOT.getNDiasantiguedad()));

				((OCAPNuevaSolicitudForm) form).setCConvocatoriaId(String.valueOf(solicitudOT.getCConvocatoriaId()));
				((OCAPNuevaSolicitudForm) form).setBOtroServicio(solicitudOT.getBOtroServicio());
				((OCAPNuevaSolicitudForm) form).setADondeServicio(solicitudOT.getADondeServicio());
				((OCAPNuevaSolicitudForm) form).setResumenCentros(solicitudOT.getResumenCentros());
				((OCAPNuevaSolicitudForm) form).setFRegistro_solic(solicitudOT.getFRegistro_solic());
				((OCAPNuevaSolicitudForm) form).setDServicio(solicitudOT.getAServicio());
				((OCAPNuevaSolicitudForm) form).setDPuesto(solicitudOT.getAPuesto());
				((OCAPNuevaSolicitudForm) form).setFRegistro_oficial(solicitudOT.getFRegistro_oficial());

				((OCAPNuevaSolicitudForm) form).setCEstado(estadoOT.getDNombre());
				((OCAPNuevaSolicitudForm) form).setCEstadoId(solicitudOT.getCEstado_id());
				((OCAPNuevaSolicitudForm) form).setFInconf_solic(DateUtil
						.convertDateToStringLarga(Constantes.FECHA_COMPLETA_DATEUTIL, solicitudOT.getFInconf_solic()));
				((OCAPNuevaSolicitudForm) form).setDMotivo_inconf_solic(solicitudOT.getDMotivo_inconf_solic());
				((OCAPNuevaSolicitudForm) form).setFNegacion_solic(DateUtil.convertDateToStringLarga(
						Constantes.FECHA_COMPLETA_DATEUTIL, solicitudOT.getFNegacion_solic()));
				
				((OCAPNuevaSolicitudForm) form).setAnioConvocatoria(convOT.getDAnioConvocatoria()!=null?convOT.getDAnioConvocatoria():
																		"");

				

				OCAPMotExclusionLN motivosExcLN = new OCAPMotExclusionLN(jcylUsuario);
				if (session.getAttribute("listaMotivosExclusion") == null) {
					/*
					 * OCAP-103 
					 */
					ArrayList listaMotivosExclusion = motivosExcLN.listarMotivosExclusion(convOT.getDAnioConvocatoria());
					session.setAttribute("listaMotivosExclusion", listaMotivosExclusion);
					listaMotivosExclusion = null;
				}

				if (solicitudOT.getCEstado_id() == 4) {
					OCAPExpedientesExclusionOT lista = null;
					OCAPExpedientesExclusionLN motivosLN = new OCAPExpedientesExclusionLN(jcylUsuario);
					listaMotivos = motivosLN.buscarOCAPExpedientesExclusion(solicitudOT);

					int numMotivos = listaMotivos.size();
					String[] c_motivos_exclusion = new String[numMotivos];

					for (int j = 0; j < numMotivos; j++) {
						lista = (OCAPExpedientesExclusionOT) listaMotivos.get(j);
						c_motivos_exclusion[j] = (String.valueOf(lista.getCExclusionId()) + "#" + lista.getDNombre());
						if ((lista.getDOtrosMotivos() != null) && (!"".equals(lista.getDOtrosMotivos()))) {
							((OCAPNuevaSolicitudForm) form).setDOtrosMotivosExclusion(lista.getDOtrosMotivos());
						}

					}

					((OCAPNuevaSolicitudForm) form).setCMotivosExclusion(c_motivos_exclusion);
					c_motivos_exclusion = null;
					listaMotivos = null;
				}

				OCAPUsuariosLN usuarioLN = new OCAPUsuariosLN(jcylUsuario);
				listadoSolicitudes = usuarioLN.buscarOCAPUsuariosPorNIF_a(solicitudOT.getCDniReal());
				((OCAPNuevaSolicitudForm) form).setListaUsuarios(listadoSolicitudes);

				request.setAttribute("tipoAccion", Constantes.IR_DETALLE);
				request.setAttribute("tipoAccionBis", Constantes.IR_DETALLE);

				if (bPenaliza)
					request.setAttribute("penaliza", Constantes.SI);

				if (("Tramitar".equals(request.getParameter("opcion")))
						&& (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())))
					request.setAttribute("opcion", "Reasociar");
				else if ((Constantes.VER_DETALLE.equals(request.getParameter("opcion")))
						|| (Constantes.VER_DETALLE.equals(request.getAttribute("opcion"))))
					request.setAttribute("opcion", Constantes.VER_DETALLE);
				else if (("Cambiar".equals(request.getParameter("opcion")))
						|| ("Cambiar".equals(request.getAttribute("opcion"))))
					request.setAttribute("opcion", "Cambiar");
				else {
					request.setAttribute("opcion", "Tramitar");
				}
				request.setAttribute("vuelta", request.getParameter("vuelta") == null ? request.getAttribute("vuelta")
						: request.getParameter("vuelta"));				
				
				request.setAttribute("provincia2010",
						solicitudUsuarioOT.getDProvincia() != null ? solicitudUsuarioOT.getDProvincia() : "");
				request.setAttribute("gerencia2010", solicitudUsuarioOT.getDGerencia_nombre() != null
						? solicitudUsuarioOT.getDGerencia_nombre() : "");
				request.setAttribute("centro2010", solicitudUsuarioOT.getDCentrotrabajo_nombre() != null
						? solicitudUsuarioOT.getDCentrotrabajo_nombre() : "");
				((OCAPNuevaSolicitudForm) form)
						.setCSitAdmonActualId(String.valueOf(solicitudUsuarioOT.getCSitAdmonActualId()));
				((OCAPNuevaSolicitudForm) form).setDSitAdmonActual(solicitudUsuarioOT.getDSitAdmonActual());
				
				((OCAPNuevaSolicitudForm) form).setAOtraSitAdmonActual(solicitudUsuarioOT.getAOtraSitAdmonActual());

				if (Constantes.MODIFICAR.equals(request.getParameter("modificar"))) {
					forward = "irNuevaModificar";


					ArrayList listadoGrados = gradoLN.listarGrados();

					if ((listadoGrados == null) || (listadoGrados.size() == 0)) {
						return mapping.findForward("irNoExistenConvocatoriasSolic");
					}
					session.setAttribute(Constantes.COMBO_GRADOS_ALTA, listadoGrados);


					if (session.getAttribute(Constantes.COMBO_PERSONAL) == null) {
						ArrayList listadoProcedimientos = procLN.listadoOCAPProcedimiento();
						session.setAttribute(Constantes.COMBO_PERSONAL, listadoProcedimientos);
					}

					if (session.getAttribute(Constantes.COMBO_REGIMEN_JURIDICO) == null) {
						OCAPRegimenJuridicoLN regJuridicoLN = new OCAPRegimenJuridicoLN(jcylUsuario);
						ArrayList listadoRegimenJuridico = regJuridicoLN.listadoOCAPRegimenJuridico();
						session.setAttribute(Constantes.COMBO_REGIMEN_JURIDICO, listadoRegimenJuridico);
					}

					if (session.getAttribute(Constantes.COMBO_SIT_ADMIN) == null) {
						OCAPSit_AdministrativaLN sit_AdministrativaLN = new OCAPSit_AdministrativaLN(jcylUsuario);
						ArrayList listadoSit_Administrativas = sit_AdministrativaLN.listadoOCAPSit_Administrativa();
						session.setAttribute(Constantes.COMBO_SIT_ADMIN, listadoSit_Administrativas);
					}

					if (session.getAttribute(Constantes.COMBO_TODAS_PROVINCIAS) == null) {
						ArrayList listaTodasProvincias = provinciaLN.listarProvincias();
						session.setAttribute(Constantes.COMBO_TODAS_PROVINCIAS, listaTodasProvincias);
					}

					if (session.getAttribute(Constantes.COMBO_PROVINCIAS) == null) {
						ArrayList listadoProvincias = provinciaLN.listarProvinciasComunidad("CL");
						session.setAttribute(Constantes.COMBO_PROVINCIAS, listadoProvincias);
					}

					if (session.getAttribute(Constantes.COMBO_MOD_ANTERIOR) == null) {
						ArrayList listadoModAnterior = modalidadAnteriorLN.listarModalidadAnterior();
						session.setAttribute(Constantes.COMBO_MOD_ANTERIOR, listadoModAnterior);
					}

					if ((((OCAPNuevaSolicitudForm) form).getCProvincia_id() != null)
							&& (!((OCAPNuevaSolicitudForm) form).getCProvincia_id().equals(""))) {
						OCAPLocalidadesLN localidadesLN = new OCAPLocalidadesLN(jcylUsuario);
						ArrayList listadoLocalidades = localidadesLN
								.listarLocalidadesProvincia(((OCAPNuevaSolicitudForm) form).getCProvincia_id());
						session.setAttribute(Constantes.COMBO_LOCALIDADES, listadoLocalidades);
					} else {
						session.setAttribute(Constantes.COMBO_LOCALIDADES, new ArrayList());
					}

					OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
					if ((((OCAPNuevaSolicitudForm) form).getCJuridicoId() != null)
							&& (!((OCAPNuevaSolicitudForm) form).getCJuridicoId().equals(""))) {
						ArrayList listadoCategorias = categProfesionalesLN
								.listarCategoriasRJ(((OCAPNuevaSolicitudForm) form).getCJuridicoId());
						session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
					} else {
						session.setAttribute(Constantes.COMBO_CATEGORIA, new ArrayList());
					}

					OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
					if ((((OCAPNuevaSolicitudForm) form).getCProfesional_id() != null)
							&& (!((OCAPNuevaSolicitudForm) form).getCProfesional_id().equals(""))) {
						ArrayList listadoEspecialidades = especialidadesLN.listarEspecialidades(
								Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesional_id()));
						session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
					} else if ((((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id() != null)
							&& (!((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id().equals(""))) {
						ArrayList listadoEspecialidades = especialidadesLN.listarEspecialidades(
								Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesionalFijo_id()));
						session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
					} else {
						session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
					}

					ArrayList listaVinculos = solicitudesLN.listaVinculos();
					session.setAttribute(Constantes.COMBO_VINCULOS, listaVinculos);

					if ((((OCAPNuevaSolicitudForm) form).getCProvinciaCarrera_id() != null)
							&& (!"".equals(((OCAPNuevaSolicitudForm) form).getCProvinciaCarrera_id()))) {
						ArrayList listadoGerencias = gerenciasLN
								.listarGerenciasSolicitud(((OCAPNuevaSolicitudForm) form).getCProvinciaCarrera_id());
						session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);
					} else {
						session.setAttribute(Constantes.COMBO_GERENCIA, new ArrayList());
					}

					if ((((OCAPNuevaSolicitudForm) form).getCGerencia_id() != null)
							&& (!"".equals(((OCAPNuevaSolicitudForm) form).getCGerencia_id()))) {
						ArrayList listadoCentros = centroLN
								.listarCentroTrabajo(((OCAPNuevaSolicitudForm) form).getCGerencia_id(), null);
						session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO_CARRERA, listadoCentros);
					} else {
						session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO_CARRERA, new ArrayList());
					}
					
					
					//OCAP-1327
					if ((solicitudUsuarioOT.getCProvinciaCarrera_id() != null)
							&& (!solicitudUsuarioOT.getCProvinciaCarrera_id().equals(""))) {
						provinciaCarreraOT = provinciaLN
								.buscarProvincia(String.valueOf(solicitudUsuarioOT.getCProvinciaCarrera_id()));
						((OCAPNuevaSolicitudForm) form)
								.setDProvCarreraActual(Cadenas.capitalizar(provinciaCarreraOT.getDProvincia()));
						((OCAPNuevaSolicitudForm) form)
								.setCProvCarreraActualId(solicitudUsuarioOT.getCProvinciaCarrera_id());
					}
					
					if (solicitudUsuarioOT.getCGerencia_id() != 0L) {
						gerenciasOT = gerenciasLN.buscarOCAPGerencias(solicitudUsuarioOT.getCGerencia_id());
						((OCAPNuevaSolicitudForm) form).setDGerenciaActual(gerenciasOT.getDNombre());
						((OCAPNuevaSolicitudForm) form).setCGerenciaActualId(String.valueOf(solicitudUsuarioOT.getCGerencia_id()));
					}

					if (solicitudUsuarioOT.getCCentrotrabajo_id() != 0L) {
						centroOT = centroLN.buscarOCAPCentroTrabajo(solicitudUsuarioOT.getCCentrotrabajo_id());
						((OCAPNuevaSolicitudForm) form).setDCentroTrabActual(centroOT.getDNombre());
						((OCAPNuevaSolicitudForm) form)
								.setCCentroTrabActualId(String.valueOf(solicitudUsuarioOT.getCCentrotrabajo_id()));
					}
					
					//Actualizamos los combos
					if ((((OCAPNuevaSolicitudForm) form).getCProvCarreraActualId() != null)
							&& (!"".equals(((OCAPNuevaSolicitudForm) form).getCProvCarreraActualId()))) {
						ArrayList listadoGerencias = gerenciasLN
								.listarGerenciasSolicitud(((OCAPNuevaSolicitudForm) form).getCProvCarreraActualId());
						session.setAttribute(Constantes.COMBO_GERENCIA_ACTUAL, listadoGerencias);
					} else {
						session.setAttribute(Constantes.COMBO_GERENCIA_ACTUAL, new ArrayList());
					}

					if ((((OCAPNuevaSolicitudForm) form).getCGerenciaActualId() != null)
							&& (!"".equals(((OCAPNuevaSolicitudForm) form).getCGerenciaActualId()))) {
						ArrayList listadoCentros = centroLN
								.listarCentroTrabajo(((OCAPNuevaSolicitudForm) form).getCGerenciaActualId(), null);
						session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO_CARRERA_ACTUAL, listadoCentros);
					} else {
						session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO_CARRERA_ACTUAL, new ArrayList());
					}
					

					((OCAPNuevaSolicitudForm) form).setCExp_id(request.getParameter("CExp_id"));
					request.setAttribute("tipoAccion", Constantes.IR_INSERTAR);
				} else {
					forward = "irNuevaInsertarExp";
				}

			} else {
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						new ActionError("Se ha producido un error al recuperar los datos del Expediente"));
			}

			OCAPConfigApp.logger.info(getClass().getName() + " detalleExpediente: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(forward);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward insertarNuevo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		ArrayList listadoEstados = null;
		String fw = "";

		boolean bSubsana = false;
		boolean bExcluye = false;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " insertarNuevo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();
			String cSolicitudIdS = request.getParameter("cSolicitudId");
			solicitudesOT.setCSolicitudId(Long.parseLong(cSolicitudIdS));

			solicitudesOT.setDNombre(((OCAPNuevaSolicitudForm) form).getDNombre());
			solicitudesOT.setDApellido1(((OCAPNuevaSolicitudForm) form).getDApellido1());

			solicitudesOT.setCDniReal(((OCAPNuevaSolicitudForm) form).getCDniReal());
			solicitudesOT.setDCorreoelectronico(((OCAPNuevaSolicitudForm) form).getDCorreoelectronico());
			solicitudesOT.setNTelefono1(((OCAPNuevaSolicitudForm) form).getNTelefono1());
			solicitudesOT.setNTelefono2(((OCAPNuevaSolicitudForm) form).getNTelefono2());
			solicitudesOT.setBSexo(((OCAPNuevaSolicitudForm) form).getBSexo());
			solicitudesOT.setDDomicilio(((OCAPNuevaSolicitudForm) form).getDDomicilio());

			solicitudesOT.setCProvinciaUsu_id(((OCAPNuevaSolicitudForm) form).getCProvincia_id());

			solicitudesOT.setDLocalidadUsu(((OCAPNuevaSolicitudForm) form).getDLocalidad());
			solicitudesOT.setCPostalUsu(((OCAPNuevaSolicitudForm) form).getCPostalUsu());

			solicitudesOT.setCJuridico(((OCAPNuevaSolicitudForm) form).getCJuridicoId());
			solicitudesOT.setDRegimenJuridicoOtros(((OCAPNuevaSolicitudForm) form).getDRegimenJuridicoOtros());
			solicitudesOT.setCProcedimientoId(((OCAPNuevaSolicitudForm) form).getCProcedimientoId());
			solicitudesOT.setCSitAdministrativaAuxId(((OCAPNuevaSolicitudForm) form).getCSitAdministrativaId());

			if ((((OCAPNuevaSolicitudForm) form).getCSitAdministrativaId() != null)
					&& ("6".equals(((OCAPNuevaSolicitudForm) form).getCSitAdministrativaId()))) {
				solicitudesOT
						.setDSitAdministrativaAuxOtros(((OCAPNuevaSolicitudForm) form).getDSitAdministrativaOtros());
			}
			solicitudesOT.setCEstatutId(((OCAPNuevaSolicitudForm) form).getCEstatutId());
			solicitudesOT.setCProfesional_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesional_id()));
			if ((((OCAPNuevaSolicitudForm) form).getCEspec_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCEspec_id().equals(""))) {
				solicitudesOT.setCEspec_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCEspec_id()));
			}

			solicitudesOT
					.setCGerencia_id(Long.valueOf(((OCAPNuevaSolicitudForm) form).getCGerenciaActualId()).longValue());
			solicitudesOT.setCCentrotrabajo_id(
					Long.valueOf(((OCAPNuevaSolicitudForm) form).getCCentroTrabActualId()).longValue());

			if ((((OCAPNuevaSolicitudForm) form).getNAniosantiguedad() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getNAniosantiguedad().equals(""))) {
				solicitudesOT
						.setNAniosantiguedad(Long.parseLong(((OCAPNuevaSolicitudForm) form).getNAniosantiguedad()));
			}
			if (solicitudesOT.getNAniosantiguedad() == 0L)
				solicitudesOT.setNAniosantiguedad(-1);
			if ((((OCAPNuevaSolicitudForm) form).getNMesesantiguedad() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getNMesesantiguedad().equals("")))
				solicitudesOT
						.setNMesesantiguedad(Long.parseLong(((OCAPNuevaSolicitudForm) form).getNMesesantiguedad()));
			if ((((OCAPNuevaSolicitudForm) form).getNDiasantiguedad() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getNDiasantiguedad().equals("")))
				solicitudesOT.setNDiasantiguedad(Long.parseLong(((OCAPNuevaSolicitudForm) form).getNDiasantiguedad()));
			if ((((OCAPNuevaSolicitudForm) form).getCGrado_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCGrado_id().equals("")))
				solicitudesOT.setCGrado_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGrado_id()));
			if ((((OCAPNuevaSolicitudForm) form).getCModAnteriorId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCModAnteriorId().equals("")))
				solicitudesOT.setCModAnterior_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCModAnteriorId()));
			if ((((OCAPNuevaSolicitudForm) form).getCConvocatoriaId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCConvocatoriaId().equals(""))) {
				solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));
			}
			solicitudesOT.setFRegistro_oficial(((OCAPNuevaSolicitudForm) form).getFRegistro_oficial() + " "
					+ ((OCAPNuevaSolicitudForm) form).getNHoraRegistro());
			solicitudesOT.setFRegistro_solic(((OCAPNuevaSolicitudForm) form).getFRegistro_solic());
			solicitudesOT.setBOtroServicio(((OCAPNuevaSolicitudForm) form).getBOtroServicio());
			solicitudesOT.setAPuesto(((OCAPNuevaSolicitudForm) form).getDPuesto());
			solicitudesOT.setAServicio(((OCAPNuevaSolicitudForm) form).getDServicio());

			if (Constantes.SI.equals(solicitudesOT.getBOtroServicio())) {
				solicitudesOT.setADondeServicio(((OCAPNuevaSolicitudForm) form).getADondeServicio());
			}
			solicitudesOT.setACreadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
			solicitudesOT.setResumenCentros(((OCAPNuevaSolicitudForm) form).getResumenCentros());
			solicitudesOT.setCEstado_id(2);

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			solicitudesOT = solicitudesLN.insertar_a(solicitudesOT, jcylUsuario);

			solicitudesOT.setFRegistro_oficial(solicitudesOT.getFRegistro_oficial());
			solicitudesOT.setAModificadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());

			OCAPNuevaSolicitudLN nuevaSolLN = new OCAPNuevaSolicitudLN(jcylUsuario);
			nuevaSolLN.modificarSolicitud(solicitudesOT);

			OCAPEstadosLN estadosLN = new OCAPEstadosLN(jcylUsuario);
			OCAPEstadosOT estadoOT = estadosLN.buscarEstados(2);
			listadoEstados = estadosLN.listarEstadosFase("I", null);

			OCAPConvocatoriasLN convoLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT convOT = convoLN.buscarOCAPConvocatorias(solicitudesOT.getCConvocatoriaId());

			if ((convOT.getFAlegsolicitud() == null)
					|| (DateUtil.convertStringToDate(convOT.getFAlegsolicitud()).after(new Date()))) {
				bSubsana = true;
			}
			if ((4 == solicitudesOT.getCEstado_id()) && (solicitudesOT.getFInconf_solic() == null)) {
				bExcluye = true;
			}
			ArrayList estadosAct = comboEstados(listadoEstados, bSubsana, bExcluye, jcylUsuario);
			session.setAttribute(Constantes.COMBO_ESTADOS, estadosAct);

			((OCAPNuevaSolicitudForm) form).setCEstado(estadoOT.getDNombre());
			((OCAPNuevaSolicitudForm) form).setFRegistro_oficial(solicitudesOT.getFRegistro_oficial());
			((OCAPNuevaSolicitudForm) form).setCUsr_id(String.valueOf(solicitudesOT.getCUsr_id()));
			((OCAPNuevaSolicitudForm) form).setCExp_id(String.valueOf(solicitudesOT.getCExp_id()));

			request.setAttribute("tipoAccion", Constantes.IR_DETALLE);
			request.setAttribute("tipoAccionBis", Constantes.IR_DETALLE);
			request.setAttribute("opcion", "Cambiar");
			request.setAttribute("vuelta", request.getParameter("vuelta") == null ? request.getAttribute("vuelta")
					: request.getParameter("vuelta"));

			fw = "irNuevaInsertar";

			OCAPConfigApp.logger.info(getClass().getName() + " insertarNuevo: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			if ((e.getMessage() != null) && (e.getMessage().equals("ExisteSolicitudConvocatoria")))
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						new ActionError(TrataError.tratarError(e, "error.existeSolicitudConvocatoria")));
			else {
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						new ActionError(TrataError.tratarError(e, "error.general")));
			}
		}
		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(fw);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward asignarUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();

		ArrayList listadoEstados = null;
		boolean bPenaliza = false;
		boolean bSubsana = false;
		boolean bExcluye = false;
		ArrayList listadoConvoc = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " asignarUsuario: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPNuevaSolicitudLN solicitudesLN = new OCAPNuevaSolicitudLN(jcylUsuario);
			OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			OCAPSolicitudesOT solicitudOT = new OCAPSolicitudesOT();
			OCAPUsuariosOT usuariosOT = new OCAPUsuariosOT();
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();
			OCAPEstadosOT estadoOT = new OCAPEstadosOT();

			String cUsuarioIdS = request.getParameter("cUsuarioId");
			String cSolicitudIdS = request.getParameter("cSolicitudId");

			expedienteCarreraOT.setCUsrId(new Long(cUsuarioIdS));
			expedienteCarreraOT.setCGradoId(Integer.valueOf(((OCAPNuevaSolicitudForm) form).getCGrado_id()));
			expedienteCarreraOT
					.setCConvocatoriaId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));

			expedienteCarreraOT = expedienteCarreraLN.buscarExpediente(expedienteCarreraOT);

			solicitudOT.setCSolicitudId(Integer.parseInt(cSolicitudIdS));
			solicitudOT.setCUsr_id(new Long(cUsuarioIdS).longValue());
			solicitudOT.setCEstado_id(2);
			solicitudOT.setAModificadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
			solicitudOT.setFRegistro_solic(((OCAPNuevaSolicitudForm) form).getFRegistro_solic());
			solicitudOT.setFRegistro_oficial(((OCAPNuevaSolicitudForm) form).getFRegistro_oficial() + " "
					+ ((OCAPNuevaSolicitudForm) form).getNHoraRegistro());

			usuariosOT.setCUsrId(new Long(cUsuarioIdS));

			usuariosOT.setCCentrotrabajoId(Integer.valueOf(((OCAPNuevaSolicitudForm) form).getCCentroTrabActualId()));
			usuariosOT.setCGerenciaId(Integer.valueOf(((OCAPNuevaSolicitudForm) form).getCGerenciaActualId()));

			usuariosOT.setDNombre(((OCAPNuevaSolicitudForm) form).getDNombre());
			usuariosOT.setDApellido1(((OCAPNuevaSolicitudForm) form).getDApellido1());
			usuariosOT.setCDniReal(((OCAPNuevaSolicitudForm) form).getCDniReal());
			usuariosOT.setBSexo(((OCAPNuevaSolicitudForm) form).getBSexo());
			usuariosOT.setDCorreoelectronico(((OCAPNuevaSolicitudForm) form).getDCorreoelectronico());
			usuariosOT.setDDomicilio(((OCAPNuevaSolicitudForm) form).getDDomicilio());
			usuariosOT.setCProvinciaUsu_id(((OCAPNuevaSolicitudForm) form).getCProvincia_id());
			usuariosOT.setDLocalidadUsu(((OCAPNuevaSolicitudForm) form).getDLocalidad());
			usuariosOT.setCPostalUsu(((OCAPNuevaSolicitudForm) form).getCPostalUsu());
			if ((((OCAPNuevaSolicitudForm) form).getNTelefono1() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getNTelefono1().equals("")))
				usuariosOT.setNTelefono1(Integer.valueOf(((OCAPNuevaSolicitudForm) form).getNTelefono1()));
			if ((((OCAPNuevaSolicitudForm) form).getNTelefono2() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getNTelefono2().equals("")))
				usuariosOT.setNTelefono2(Integer.valueOf(((OCAPNuevaSolicitudForm) form).getNTelefono2()));
			usuariosOT.setBBorrado(Constantes.NO);
			usuariosOT.setAModificadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());

			if ((expedienteCarreraOT.getCExpId() != null) && (expedienteCarreraOT.getCExpId().longValue() != 0L)) {
				if (expedienteCarreraOT.getFRegistroOficial().before(DateUtil
						.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL, solicitudOT.getFRegistro_oficial()))) {
					expedienteCarreraOT.setCGradoId(Integer.valueOf(((OCAPNuevaSolicitudForm) form).getCGrado_id()));
					expedienteCarreraOT
							.setCConvocatoriaId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));
					expedienteCarreraOT.setCProcedimientoId(((OCAPNuevaSolicitudForm) form).getCProcedimientoId());
					expedienteCarreraOT.setCEstatutId(((OCAPNuevaSolicitudForm) form).getCEstatutId());
					expedienteCarreraOT
							.setNAniosAntiguedad(Long.parseLong(((OCAPNuevaSolicitudForm) form).getNAniosantiguedad()));
					expedienteCarreraOT
							.setNMesesAntiguedad(Long.parseLong(((OCAPNuevaSolicitudForm) form).getNMesesantiguedad()));
					expedienteCarreraOT
							.setNDiasAntiguedad(Long.parseLong(((OCAPNuevaSolicitudForm) form).getNDiasantiguedad()));
					expedienteCarreraOT.setFRegistroSolic(DateUtil.convertStringToDate(
							Constantes.FECHA_COMPLETA_DATEUTIL, ((OCAPNuevaSolicitudForm) form).getFRegistro_solic()));
					expedienteCarreraOT
							.setCSitAdministrativaAuxId(((OCAPNuevaSolicitudForm) form).getCSitAdministrativaId());
					if (((OCAPNuevaSolicitudForm) form).getDSitAdministrativaOtros() != null)
						expedienteCarreraOT.setDSitAdministrativaAuxOtros(
								((OCAPNuevaSolicitudForm) form).getDSitAdministrativaOtros());
					else
						expedienteCarreraOT.setDSitAdministrativaAuxOtros("");
					expedienteCarreraOT.setCJuridico(((OCAPNuevaSolicitudForm) form).getCJuridicoId());
					expedienteCarreraOT.setFRegistroOficial(DateUtil.convertStringToDate(
							Constantes.FECHA_COMPLETA_DATEUTIL, solicitudOT.getFRegistro_oficial()));
					expedienteCarreraOT.setBOtroServicio(((OCAPNuevaSolicitudForm) form).getBOtroServicio());
					expedienteCarreraOT.setADondeServicio(((OCAPNuevaSolicitudForm) form).getADondeServicio());
					expedienteCarreraOT.setAServicio(((OCAPNuevaSolicitudForm) form).getDServicio());
					expedienteCarreraOT.setAPuesto(((OCAPNuevaSolicitudForm) form).getDPuesto());
					expedienteCarreraOT
							.setAModificadoPor(((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario"))
									.getUsuario().getC_usr_id());
					expedienteCarreraOT.setCSolicitudId(Long.parseLong(cSolicitudIdS));
					expedienteCarreraOT.setCProfesionalId(
							Long.valueOf(((OCAPNuevaSolicitudForm) form).getCProfesional_id()).longValue());
					if ((((OCAPNuevaSolicitudForm) form).getCEspec_id() != null)
							&& (!((OCAPNuevaSolicitudForm) form).getCEspec_id().equals("")))
						expedienteCarreraOT
								.setCEspecId(Long.valueOf(((OCAPNuevaSolicitudForm) form).getCEspec_id()).longValue());
					if (((OCAPNuevaSolicitudForm) form).getDRegimenJuridicoOtros() != null)
						expedienteCarreraOT
								.setDRegimenJuridicoOtros(((OCAPNuevaSolicitudForm) form).getDRegimenJuridicoOtros());
					else
						expedienteCarreraOT.setDRegimenJuridicoOtros("");
					expedienteCarreraOT.setCEstadoId(2);
					expedienteCarreraOT
							.setCModAnteriorId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCModAnteriorId()));

					expedienteCarreraOT.setFAceptacSolic(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
					expedienteCarreraOT.setFNegacionSolic(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
					expedienteCarreraOT.setFDesistido(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
					expedienteCarreraOT.setFSubsanacion(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
					expedienteCarreraOT.setFInconf_solic(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
					expedienteCarreraOT.setDMotivoInconf_solic(" ");

					solicitudesLN.asignarUsuarioExiste(usuariosOT, expedienteCarreraOT, solicitudOT, jcylUsuario);
				} else {
					solicitudOT.setCEstado_id(8);
					solicitudesLN.modificarSolicitud(solicitudOT);
					messages.add("dfecha", new ActionMessage("error.solicitudFechaRegistro", "Fecha"));
					request.setAttribute("rutaVuelta",
							"OCAPNuevaSolicitud.do?accion=buscarSolicitudesDNI&recuperarBusquedaAnterior=Y");
				}
			} else {
				expedienteCarreraOT.setCUsrId(new Long(cUsuarioIdS));
				expedienteCarreraOT.setCGradoId(Integer.valueOf(((OCAPNuevaSolicitudForm) form).getCGrado_id()));
				expedienteCarreraOT
						.setCConvocatoriaId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));
				expedienteCarreraOT.setCProcedimientoId(((OCAPNuevaSolicitudForm) form).getCProcedimientoId());
				expedienteCarreraOT.setCEstatutId(((OCAPNuevaSolicitudForm) form).getCEstatutId());
				expedienteCarreraOT
						.setNAniosAntiguedad(Long.parseLong(((OCAPNuevaSolicitudForm) form).getNAniosantiguedad()));
				expedienteCarreraOT
						.setNMesesAntiguedad(Long.parseLong(((OCAPNuevaSolicitudForm) form).getNMesesantiguedad()));
				expedienteCarreraOT
						.setNDiasAntiguedad(Long.parseLong(((OCAPNuevaSolicitudForm) form).getNDiasantiguedad()));
				expedienteCarreraOT.setFRegistroSolic(DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL,
						((OCAPNuevaSolicitudForm) form).getFRegistro_solic()));
				expedienteCarreraOT
						.setCSitAdministrativaAuxId(((OCAPNuevaSolicitudForm) form).getCSitAdministrativaId());
				expedienteCarreraOT
						.setDSitAdministrativaAuxOtros(((OCAPNuevaSolicitudForm) form).getDSitAdministrativaOtros());
				expedienteCarreraOT.setCJuridico(((OCAPNuevaSolicitudForm) form).getCJuridicoId());
				expedienteCarreraOT.setFRegistroOficial(DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL,
						solicitudOT.getFRegistro_oficial()));
				expedienteCarreraOT.setACreadoPor(
						((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
				expedienteCarreraOT.setBOtroServicio(((OCAPNuevaSolicitudForm) form).getBOtroServicio());
				expedienteCarreraOT.setADondeServicio(((OCAPNuevaSolicitudForm) form).getADondeServicio());
				expedienteCarreraOT.setBBorrado(Constantes.NO);
				expedienteCarreraOT.setAServicio(((OCAPNuevaSolicitudForm) form).getDServicio());
				expedienteCarreraOT.setAPuesto(((OCAPNuevaSolicitudForm) form).getDPuesto());
				expedienteCarreraOT.setCSolicitudId(((OCAPNuevaSolicitudForm) form).getCSolicitudId());
				expedienteCarreraOT
						.setCModAnteriorId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCModAnteriorId()));
				expedienteCarreraOT.setCProfesionalId(
						Long.valueOf(((OCAPNuevaSolicitudForm) form).getCProfesional_id()).longValue());
				if ((((OCAPNuevaSolicitudForm) form).getCEspec_id() != null)
						&& (!"".equals(((OCAPNuevaSolicitudForm) form).getCEspec_id())))
					expedienteCarreraOT
							.setCEspecId(Long.valueOf(((OCAPNuevaSolicitudForm) form).getCEspec_id()).longValue());
				else
					expedienteCarreraOT.setCEspecId(0L);
				expedienteCarreraOT
						.setDRegimenJuridicoOtros(((OCAPNuevaSolicitudForm) form).getDRegimenJuridicoOtros());
				expedienteCarreraOT.setCEstadoId(2);

				solicitudesLN.asignarUsuarioNuevo(usuariosOT, expedienteCarreraOT, solicitudOT, jcylUsuario);
			}

			if ((messages == null) || (messages.isEmpty())) {
				OCAPEstadosLN estadosLN = new OCAPEstadosLN(jcylUsuario);
				OCAPConvocatoriasOT convOT = new OCAPConvocatoriasOT();
				OCAPConvocatoriasLN convoLN = new OCAPConvocatoriasLN(jcylUsuario);
				OCAPEstadosOT estados = null;
				estadoOT = estadosLN.buscarEstados(2);
				listadoEstados = estadosLN.listarEstadosFase("I", null);
				convOT = convoLN
						.buscarOCAPConvocatorias(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));

				OCAPExpedientecarreraOT convocatorias = new OCAPExpedientecarreraOT();
				Date fecha = null;
				listadoConvoc = expedienteCarreraLN.buscarOCAPExpedienteRechaz(new Long(cUsuarioIdS).longValue());
				for (int j = 0; j < listadoConvoc.size(); j++) {
					convocatorias = (OCAPExpedientecarreraOT) listadoConvoc.get(j);
					if ((fecha == null) || (convocatorias.getFRecGrado() != null && convocatorias.getFRecGrado().after(fecha))) {
						fecha = convocatorias.getFRecGrado();
					}
				}
				if (fecha != null) {
					Date fFinPenaliza = DateUtil.addAnios(fecha, 2);
					if (fFinPenaliza.after(DateUtil.convertStringToDate(convOT.getFPublicacion()))) {
						((OCAPNuevaSolicitudForm) form).setFRecGrado(DateUtil.convertDateToString(fecha));
						((OCAPNuevaSolicitudForm) form).setFFin_penaliza(DateUtil.convertDateToString(fFinPenaliza));
						bPenaliza = true;
						request.setAttribute("penaliza", Constantes.SI);
					}
				}

				if ((convOT.getFAlegsolicitud() == null)
						|| (DateUtil.convertStringToDate(convOT.getFAlegsolicitud()).after(new Date()))) {
					bSubsana = true;
				}
				if ((4 == solicitudOT.getCEstado_id()) && (solicitudOT.getFInconf_solic() == null)) {
					bExcluye = true;
				}
				ArrayList estadosAct = new ArrayList();
				estadosAct = comboEstados(listadoEstados, bSubsana, bExcluye, jcylUsuario);
				session.setAttribute(Constantes.COMBO_ESTADOS, estadosAct);

				((OCAPNuevaSolicitudForm) form).setCEstado(estadoOT.getDNombre());
				((OCAPNuevaSolicitudForm) form).setCExp_id(String.valueOf(expedienteCarreraOT.getCExpId()));
				((OCAPNuevaSolicitudForm) form).setFRegistro_oficial(solicitudOT.getFRegistro_oficial());

				request.setAttribute("tipoAccion", Constantes.IR_DETALLE);
				request.setAttribute("tipoAccionBis", Constantes.IR_DETALLE);
				request.setAttribute("opcion", "Cambiar");

				request.setAttribute("vuelta", request.getParameter("vuelta") == null ? request.getAttribute("vuelta")
						: request.getParameter("vuelta"));
			}

			OCAPConfigApp.logger.info(getClass().getName() + " asignarUsuario: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty()))) {
			return mapping.findForward("irNuevaInsertar");
		}
		if ((errors != null) && (!errors.isEmpty())) {
			saveErrors(request, errors);

			return mapping.findForward("fallo");
		}
		saveMessages(request, messages);

		return mapping.findForward("falloSolicitud");
	}

	public ActionForward reasignarUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();

		String mensaje = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " reasignarUsuario: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			String cUsuarioIdS = request.getParameter("cUsuarioId");

			OCAPNuevaSolicitudLN solicitudesLN = new OCAPNuevaSolicitudLN(jcylUsuario);
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();

			expedienteCarreraOT.setCUsrId(Long.valueOf(cUsuarioIdS));
			expedienteCarreraOT.setCGradoId(Integer.valueOf(((OCAPNuevaSolicitudForm) form).getCGrado_id()));
			expedienteCarreraOT
					.setCConvocatoriaId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));
			expedienteCarreraOT.setFRegistroOficial(DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL,
					((OCAPNuevaSolicitudForm) form).getFRegistro_oficial()));
			expedienteCarreraOT.setCSolicitudId(((OCAPNuevaSolicitudForm) form).getCSolicitudId());
			expedienteCarreraOT.setAModificadoPor(jcylUsuario.getUser().getC_usr_id());
			expedienteCarreraOT.setCExpId(Long.valueOf(((OCAPNuevaSolicitudForm) form).getCExp_id()));

			mensaje = solicitudesLN.reasignarUsuario(expedienteCarreraOT, ((OCAPNuevaSolicitudForm) form).getCUsr_id(),
					jcylUsuario);
			request.setAttribute("mensaje", mensaje);

			request.setAttribute("rutaVuelta", "OCAPNuevaSolicitud.do?accion=buscar&recuperarBusquedaAnterior=Y");

			OCAPConfigApp.logger.info(getClass().getName() + " reasignarUsuario: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty()))) {
			return mapping.findForward("mensajeConfirma");
		}
		if ((errors != null) && (!errors.isEmpty())) {
			saveErrors(request, errors);

			return mapping.findForward("fallo");
		}
		saveMessages(request, messages);

		return mapping.findForward("falloSolicitud");
	}

	public ActionForward cargarGerencias(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Type", "application/json;charset=ISO-8859-1");
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoGerencias = new ArrayList();
		String textReturn = "";
		try {
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			String provinciaId = request.getParameter("val");

			if ((provinciaId == null) || (provinciaId.equals(""))) {
				textReturn = getExceptionGerencias(provinciaId);
			} else {
				OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
				listadoGerencias = gerenciasLN.listarGerenciasSolicitud(provinciaId);

				session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);

				textReturn = getCascadeGerencias(listadoGerencias, provinciaId);
			}

			PrintWriter out = response.getWriter();
			out.println(textReturn);
			out.flush();
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		return null;
	}

	private String getExceptionGerencias(String provincia) {
		StringBuffer textReturn = new StringBuffer();
		textReturn.append("[");
		textReturn.append("{'When':'").append(provincia).append("','Value':'").append("").append("','Text':'")
				.append("").append("'},").append("]");

		return textReturn.toString();
	}

	private String getCascadeGerencias(ArrayList listadoGerencias, String provinciaId) {
		StringBuffer textReturn = new StringBuffer();
		if ((listadoGerencias != null) && (listadoGerencias.size() > 0)) {
			OCAPGerenciasOT gerenciaOT = null;
			textReturn.append("[");

			textReturn.append("{'When':'" + provinciaId + "','Value':'','Text':'Seleccione...'},");

			for (int i = 0; i < listadoGerencias.size(); i++) {
				gerenciaOT = (OCAPGerenciasOT) listadoGerencias.get(i);
				textReturn.append("{'When':'").append(provinciaId).append("','Value':'")
						.append(gerenciaOT.getCGerenciaId()).append("','Text':'")
						.append(formatCadena(gerenciaOT.getDNombre())).append("'},");
			}
			textReturn.append("]");
		}

		return textReturn.toString();
	}

	public ActionForward cargarCentroTrabajo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Type", "application/json;charset=ISO-8859-1");
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoCentrosTrabajo = new ArrayList();
		String textReturn = "";
		try {
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			String gerenciaId = request.getParameter("val");

			if ((gerenciaId == null) || (gerenciaId.equals(""))) {
				textReturn = getException(gerenciaId);
			} else {
				OCAPCentroTrabajoLN centroTrabajoLN = new OCAPCentroTrabajoLN(jcylUsuario);
				listadoCentrosTrabajo = centroTrabajoLN.listarCentroTrabajo(gerenciaId, null);
				session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO, listadoCentrosTrabajo);
				textReturn = getCascadeCentroTrabajo(listadoCentrosTrabajo, gerenciaId);
			}

			PrintWriter out = response.getWriter();
			out.println(textReturn);
			out.flush();
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		return null;
	}

	private String getCascadeCentroTrabajo(ArrayList listadoCentrosTrabajo, String gerenciaId) {
		StringBuffer textReturn = new StringBuffer();
		if ((listadoCentrosTrabajo != null) && (listadoCentrosTrabajo.size() > 0)) {
			OCAPCentroTrabajoOT centroTrabajoOT = null;
			textReturn.append("[");

			textReturn.append("{'When':'" + gerenciaId + "','Value':'','Text':'Seleccione...'},");

			for (int i = 0; i < listadoCentrosTrabajo.size(); i++) {
				centroTrabajoOT = (OCAPCentroTrabajoOT) listadoCentrosTrabajo.get(i);
				textReturn.append("{'When':'").append(gerenciaId).append("','Value':'")
						.append(centroTrabajoOT.getCCentrotrabajoId()).append("','Text':'")
						.append(formatCadena(centroTrabajoOT.getDNombre())).append("'},");
			}
			textReturn.append("]");
		}

		return textReturn.toString();
	}

	private String getException(String valor) {
		StringBuffer textReturn = new StringBuffer();
		textReturn.append("[");
		textReturn.append("{'When':'").append(valor).append("','Value':'").append("").append("','Text':'").append("")
				.append("'},").append("]");

		return textReturn.toString();
	}

	public ActionForward cargarCentroTrabajoActual(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoCentrosTrabajo = new ArrayList();
		String textReturn = "";
		try {
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			String gerenciaId = request.getParameter("val");

			if ((gerenciaId == null) || (gerenciaId.equals(""))) {
				textReturn = getException(gerenciaId);
			} else {
				OCAPCentroTrabajoLN centroTrabajoLN = new OCAPCentroTrabajoLN(jcylUsuario);
				listadoCentrosTrabajo = centroTrabajoLN.listarCentroTrabajo(gerenciaId, null);
				session.setAttribute(Constantes.COMBO_CENTRO_TRABAJO, listadoCentrosTrabajo);
				textReturn = getCascadeCentroTrabajoActual(listadoCentrosTrabajo, gerenciaId);
			}

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(textReturn);
			out.flush();
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		return null;
	}

	private String getCascadeCentroTrabajoActual(ArrayList listadoCentrosTrabajo, String gerenciaId) {
		StringBuffer textReturn = new StringBuffer();
		if ((listadoCentrosTrabajo != null) && (listadoCentrosTrabajo.size() > 0)) {
			OCAPCentroTrabajoOT centroTrabajoOT = null;
			textReturn.append("[");

			textReturn.append("{'When':'" + gerenciaId + "','Value':'','Text':'Seleccione...'},");

			for (int i = 0; i < listadoCentrosTrabajo.size(); i++) {
				centroTrabajoOT = (OCAPCentroTrabajoOT) listadoCentrosTrabajo.get(i);
				textReturn.append("{'When':'").append(gerenciaId).append("','Value':'")
						.append(centroTrabajoOT.getCCentrotrabajoId()).append("','Text':'")
						.append(formatCadena(centroTrabajoOT.getDNombre())).append("'},");
			}
			textReturn.append("]");
		}

		return textReturn.toString();
	}

	public ActionForward cambiarEstado(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = null;

		boolean bExclusionAnterior = false;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " cambiarEstado: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			long cEstadoId = Long.parseLong(((OCAPNuevaSolicitudForm) form).getCEstadoFiltro());

			OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();

			expedienteCarreraOT.setCExpId(Long.valueOf(((OCAPNuevaSolicitudForm) form).getCExp_id()));
			expedienteCarreraOT.setCEstadoId(cEstadoId);
			expedienteCarreraOT.setCSolicitudId(((OCAPNuevaSolicitudForm) form).getCSolicitudId());
			expedienteCarreraOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());

			if (((OCAPNuevaSolicitudForm) form).getFNegacion_solic() != null) {
				bExclusionAnterior = true;
			}
			if (cEstadoId == 3) {
				expedienteCarreraOT.setFAceptacSolic(new Date());
				expedienteCarreraOT.setFNegacionSolic(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
			} else if (cEstadoId == 4) {
				expedienteCarreraOT.setFNegacionSolic(new Date());
				expedienteCarreraOT.setFAceptacSolic(DateUtil.convertStringToDate(Constantes.FECHA_NULA));

				if ((((OCAPNuevaSolicitudForm) form).getCMotivosExclusion() != null)
						&& (((OCAPNuevaSolicitudForm) form).getCMotivosExclusion().length > 0)) {
					ArrayList motivosEx = new ArrayList();
					for (int i = 0; i < ((OCAPNuevaSolicitudForm) form).getCMotivosExclusion().length; i++) {
						OCAPExpedientesExclusionOT expedientesExclusionOT = new OCAPExpedientesExclusionOT();
						expedientesExclusionOT.setCUsualta(jcylUsuario.getUsuario().getC_usr_id());
						expedientesExclusionOT.setCExpId(expedienteCarreraOT.getCExpId().longValue());
						String[] motivoSeleccionado = ((OCAPNuevaSolicitudForm) form).getCMotivosExclusion()[i]
								.split("#");
						expedientesExclusionOT.setCExclusionId(Long.parseLong(motivoSeleccionado[0]));
						if ((motivoSeleccionado[1] != null) && ("Otros".equalsIgnoreCase(motivoSeleccionado[1])))
							expedientesExclusionOT
									.setDOtrosMotivos(((OCAPNuevaSolicitudForm) form).getDOtrosMotivosExclusion());
						motivosEx.add(expedientesExclusionOT);
					}
					expedienteCarreraOT.setMotivosExclusion(motivosEx);
					motivosEx = null;
				}
			} else if (cEstadoId == 5) {
				expedienteCarreraOT.setFDesistido(new Date());
				expedienteCarreraOT.setFAceptacSolic(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
				expedienteCarreraOT.setFNegacionSolic(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
			} else if (cEstadoId == 6) {
				expedienteCarreraOT.setFSubsanacion(new Date());
				expedienteCarreraOT.setFAceptacSolic(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
				expedienteCarreraOT.setFNegacionSolic(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
				expedienteCarreraOT.setAPeticionSubsanacion(((OCAPNuevaSolicitudForm) form).getAPeticion());
			} else if (cEstadoId == 7) {
				expedienteCarreraOT.setDMotivoInconf_solic(((OCAPNuevaSolicitudForm) form).getDMotivo_inconf_solic());
				expedienteCarreraOT.setFInconf_solic(DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL,
						((OCAPNuevaSolicitudForm) form).getFInconf_solic() + " "
								+ ((OCAPNuevaSolicitudForm) form).getNHoraAlega()));
			}

			expedienteCarreraLN.cambiarEstadoExpediente(expedienteCarreraOT, bExclusionAnterior);

			if (cEstadoId == 6) {
				request.setAttribute("opcion", request.getParameter("opcion"));
				request.setAttribute("vuelta", request.getParameter("vuelta"));
				((OCAPNuevaSolicitudForm) form).setCEstadoFiltro("");
				((OCAPNuevaSolicitudForm) form).setAPeticion("");
				return detalleExpediente(mapping, form, request, response);
			}

			request.setAttribute("rutaVuelta",
					"OCAPNuevaSolicitud.do?accion=detalleExpediente&vuelta=" + request.getParameter("vuelta")
							+ "&opcion=" + request.getParameter("opcion") + "&CExp_id="
							+ ((OCAPNuevaSolicitudForm) form).getCExp_id());

			OCAPConfigApp.logger.info(getClass().getName() + " cambiarEstado: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors = new ActionErrors();
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mensajeExito");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}
	
	public ActionForward irReasociar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				setReasociarGrs(true);
				return irBuscar(mapping, form, request, response);
		
	}
	
	public ActionForward irBuscarGrs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				setReasociarGrs(false);
				return irBuscar(mapping, form, request, response);
		
	}

	public ActionForward irBuscar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoCategorias = null;
		ArrayList listadoGrados = null;
		ArrayList listadoConvocatorias = null;
		ArrayList listaEstados = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irBuscar: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			listadoGrados = gradoLN.listarGrados();

			session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, listadoGrados);
			session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
			session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			listadoConvocatorias = convocatoriasLN.listarConvocatorias();
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);

			OCAPEstadosLN estadosLN = new OCAPEstadosLN(jcylUsuario);
			listaEstados = estadosLN.listarEstadosFase(null, Constantes.SI);
			session.setAttribute(Constantes.COMBO_ESTADOS, listaEstados);

			((OCAPNuevaSolicitudForm) form).setjspInicio(true);
			
			if (isReasociarGrs()) {
				((OCAPNuevaSolicitudForm) form).setReasociarGrs(true);
			}else {
				((OCAPNuevaSolicitudForm) form).setReasociarGrs(false);
			}

			session.setAttribute("iRegistro", Integer.toString(1));

			OCAPConfigApp.logger.info(getClass().getName() + " irBuscar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irBuscarB");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward buscar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoSolicitudes = null;
		ArrayList listaEstados = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscar: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPNuevaSolicitudForm) request.getSession().getAttribute("OCAPNuevaSolicitudFormBuscador");
				request.setAttribute("OCAPNuevaSolicitudForm", form);
			} else {
				request.getSession().setAttribute("OCAPNuevaSolicitudFormBuscador", form);
				session.setAttribute("iRegistro", 1 + "");
			}

			int primerRegistro = 1;
			int registrosPorPagina = 20;
			String registro = null;

			if ((registro = request.getParameter("iRegistro")) != null)
				try {
					primerRegistro = Integer.parseInt(registro);
					session.setAttribute("iRegistro", primerRegistro + "");
				} catch (NumberFormatException ne) {
				}
			else if ((registro = session.getAttribute("iRegistro") == null ? null
					: session.getAttribute("iRegistro").toString()) != null) {
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

			solicitudesOT.setDApellido1(((OCAPNuevaSolicitudForm) form).getDApellido1());

			solicitudesOT.setDNombre(((OCAPNuevaSolicitudForm) form).getDNombre());
			if (((OCAPNuevaSolicitudForm) form).getCDni() != null)
				solicitudesOT.setCDni(((OCAPNuevaSolicitudForm) form).getCDni().toUpperCase());
			if (((OCAPNuevaSolicitudForm) form).getCDniReal() != null)
				solicitudesOT.setCDniReal(((OCAPNuevaSolicitudForm) form).getCDniReal().toUpperCase());
			if ((((OCAPNuevaSolicitudForm) form).getCProfesional_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCProfesional_id().equals("")))
				solicitudesOT.setCProfesional_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesional_id()));
			else if ((((OCAPNuevaSolicitudForm) form).getCProfesional_id() == null)
					|| (((OCAPNuevaSolicitudForm) form).getCProfesional_id().equals(""))) {
				solicitudesOT.setCProfesional_id(0L);
			}
			if ((((OCAPNuevaSolicitudForm) form).getCEspec_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCEspec_id().equals("")))
				solicitudesOT.setCEspec_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCEspec_id()));
			else if ((((OCAPNuevaSolicitudForm) form).getCEspec_id() == null)
					|| (((OCAPNuevaSolicitudForm) form).getCEspec_id().equals(""))) {
				solicitudesOT.setCEspec_id(0L);
			}
			if ((((OCAPNuevaSolicitudForm) form).getCGrado_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCGrado_id().equals("")))
				solicitudesOT.setCGrado_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGrado_id()));
			else if ((((OCAPNuevaSolicitudForm) form).getCGrado_id() == null)
					|| (((OCAPNuevaSolicitudForm) form).getCGrado_id().equals(""))) {
				solicitudesOT.setCGrado_id(0L);
			}
			if ((((OCAPNuevaSolicitudForm) form).getCConvocatoriaId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCConvocatoriaId().equals("")))
				solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));
			else if ((((OCAPNuevaSolicitudForm) form).getCConvocatoriaId() == null)
					|| (((OCAPNuevaSolicitudForm) form).getCConvocatoriaId().equals(""))) {
				solicitudesOT.setCConvocatoriaId(0L);
			}
			solicitudesOT.setCEstado(((OCAPNuevaSolicitudForm) form).getCEstado());

			int numSolicitudes = 0;
			if (((OCAPNuevaSolicitudForm) form).isReasociarGrs()) {
				listadoSolicitudes = solicitudesLN.buscar(primerRegistro, registrosPorPagina, solicitudesOT, false, "", true);
				numSolicitudes = solicitudesLN.contarSolicitudes(solicitudesOT, jcylUsuario, "", true);
			}else {
				listadoSolicitudes = solicitudesLN.buscar(primerRegistro, registrosPorPagina, solicitudesOT, false, "", false);
				numSolicitudes = solicitudesLN.contarSolicitudes(solicitudesOT, jcylUsuario, "", false);
			}


			session.setAttribute("numSolicitudes", new Integer(numSolicitudes));

			Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
			pagina.setElementos(listadoSolicitudes);
			pagina.setNRegistros(((Integer) session.getAttribute("numSolicitudes")).intValue());
			pagina.setRegistroActual(primerRegistro);
			pagina.setRegistrosPorPagina(registrosPorPagina);

			request.setAttribute("listados", pagina);

			OCAPEstadosLN estadosLN = new OCAPEstadosLN(jcylUsuario);
			listaEstados = estadosLN.listarEstadosFase(null, Constantes.SI);
			session.setAttribute(Constantes.COMBO_ESTADOS, listaEstados);

			OCAPConfigApp.logger.info(getClass().getName() + " buscar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irBuscarB");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irListado(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoGrados = null;
		ArrayList listadoConvocatorias = null;
		ArrayList listaEstados = null;
		ArrayList listaGerencias = null;
		ArrayList listaFases = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irListado: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			listadoConvocatorias = convocatoriasLN.listarConvocatorias();
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			listadoGrados = gradoLN.listarGrados();
			session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, listadoGrados);

			listaEstados = new ArrayList();
			listaEstados.add(new LabelValueBean(Constantes.ESTADO_ACEPTADO, "A"));
			listaEstados.add(new LabelValueBean(Constantes.ESTADO_EXCLUIDO, Constantes.ESTADO_EXCLUIDO_VALUE));
			listaEstados.add(new LabelValueBean(Constantes.ESTADO_DESISTIDO, Constantes.ESTADO_DESISTIDO_VALUE));
			session.setAttribute(Constantes.COMBO_ESTADOS, listaEstados);

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			listaGerencias = gerenciasLN.listadoOCAPGerencias();
			session.setAttribute(Constantes.COMBO_GERENCIA, listaGerencias);

			listaFases = new ArrayList();
			listaFases.add(new LabelValueBean(Constantes.FASE_1, Constantes.FASE_INICIACION));
			listaFases.add(new LabelValueBean(Constantes.FASE_2, Constantes.FASE_VALIDACION_LISTADOS));
			session.setAttribute(Constantes.COMBO_FASES, listaFases);

			session.setAttribute("tipoListado", request.getParameter("tipo"));
			((OCAPNuevaSolicitudForm) form).setOrdenListado(ORDEN_GERENCIA);
			OCAPConfigApp.logger.info(getClass().getName() + " irListado: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irListado");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward generarPDFListado(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		ArrayList<OCAPSolicitudesOT> listadoSolicitudes = null;
		String pathBase = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarPDFListado: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			String opcion = session.getAttribute("tipoListado") == null ? "P"
					: session.getAttribute("tipoListado").toString();
			solicitudesOT.setCTipo(opcion);
			solicitudesOT.setCFase(((OCAPNuevaSolicitudForm) form).getCFase());

			if ((((OCAPNuevaSolicitudForm) form).getCGrado_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCGrado_id().equals("")))
				solicitudesOT.setCGrado_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGrado_id()));
			else if (((OCAPNuevaSolicitudForm) form).getCGrado_id().equals("")) {
				solicitudesOT.setCGrado_id(0L);
			}

			if ((((OCAPNuevaSolicitudForm) form).getCConvocatoriaId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCConvocatoriaId().equals("")))
				solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));
			else if (((OCAPNuevaSolicitudForm) form).getCConvocatoriaId().equals("")) {
				solicitudesOT.setCConvocatoriaId(0L);
			}

			if ((((OCAPNuevaSolicitudForm) form).getCEstado() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCEstado().equals(""))) {
				solicitudesOT.setCEstado(((OCAPNuevaSolicitudForm) form).getCEstado());
			}

			if ((((OCAPNuevaSolicitudForm) form).getCGerencia_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCGerencia_id().equals(""))) {
				solicitudesOT.setCGerencia_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGerencia_id()));
			}
			solicitudesOT.setOrden(((OCAPNuevaSolicitudForm) form).getOrdenListado());
			if ((Constantes.FASE_INICIACION.equals(((OCAPNuevaSolicitudForm) form).getCFase()))
					|| (Constantes.FASE_VALIDACION_LISTADOS.equals(((OCAPNuevaSolicitudForm) form).getCFase())))
				listadoSolicitudes = solicitudesLN.buscarPDF(0, 0, solicitudesOT);
			else {
				listadoSolicitudes = solicitudesLN.buscar(0, 0, solicitudesOT, true, "", false);
			}
			if ((listadoSolicitudes == null) || (listadoSolicitudes.size() == 0)) {
				request.setAttribute("sinDatos", Constantes.SI);
			} else {
				pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");

				String gerenciaNombre = "";

				if ((request.getParameter("CSV") != null) && (Constantes.SI.equals(request.getParameter("CSV"))))
					solicitudesLN.crearCSVListado(response, listadoSolicitudes, pathBase,
							((OCAPNuevaSolicitudForm) form).getCEstado(), ((OCAPNuevaSolicitudForm) form).getCFase(),
							gerenciaNombre, jcylUsuario);
				else {
					if(solicitudesOT.getOrden() != null && solicitudesOT.getOrden().equals(ORDEN_ALFABETICO)){
						Collections.sort(listadoSolicitudes);
					}
					solicitudesLN.crearInformeListado(response, listadoSolicitudes, pathBase,
							((OCAPNuevaSolicitudForm) form).getCEstado(), ((OCAPNuevaSolicitudForm) form).getCFase(),
							gerenciaNombre, opcion, jcylUsuario, ((OCAPNuevaSolicitudForm) form).getOrdenListado(),solicitudesOT.getCConvocatoriaId());
				}
			}
			OCAPConfigApp.logger.info(getClass().getName() + " generarPDFListado: Fin");
		} catch (SendFailedException e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.envioEmail")));
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irListado");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward generarPDFSubsanacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();

		String pathBase = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarPDFSubsanacion: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			if ((((OCAPNuevaSolicitudForm) form).getCGrado_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCGrado_id().equals("")))
				solicitudesOT.setCGrado_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGrado_id()));
			else if (((OCAPNuevaSolicitudForm) form).getCGrado_id().equals("")) {
				solicitudesOT.setCGrado_id(0L);
			}
			if ((((OCAPNuevaSolicitudForm) form).getCConvocatoriaId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCConvocatoriaId().equals("")))
				solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));
			else if (((OCAPNuevaSolicitudForm) form).getCConvocatoriaId().equals("")) {
				solicitudesOT.setCConvocatoriaId(0L);
			}
			if ((((OCAPNuevaSolicitudForm) form).getCEstado() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCEstado().equals(""))) {
				solicitudesOT.setCEstado(((OCAPNuevaSolicitudForm) form).getCEstado());
			}

			OCAPSubsanacionesLN subsanacionesLN = new OCAPSubsanacionesLN(jcylUsuario);
			solicitudesOT.setDObserv_neg_solic(
					subsanacionesLN.buscaUltimaSubsanacion(((OCAPNuevaSolicitudForm) form).getCSolicitudId()));

			pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");

			solicitudesOT.setDNombre(((OCAPNuevaSolicitudForm) form).getDNombre());
			solicitudesOT.setDApellido1(((OCAPNuevaSolicitudForm) form).getDApellido1());
			solicitudesOT.setDDomicilio(((OCAPNuevaSolicitudForm) form).getDDomicilio());
			solicitudesOT.setCPostalUsu(((OCAPNuevaSolicitudForm) form).getCPostalUsu());
			solicitudesOT.setDLocalidad(((OCAPNuevaSolicitudForm) form).getDLocalidad());
			solicitudesOT.setDProvinciaUsu(((OCAPNuevaSolicitudForm) form).getDProvincia());
			solicitudesOT.setDProvincia(((OCAPNuevaSolicitudForm) form).getDProvinciaCarrera());
			solicitudesOT.setCGerencia_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGerencia_id() == null ? "0"
					: ((OCAPNuevaSolicitudForm) form).getCGerencia_id()));
			if (35 == solicitudesOT.getCGerencia_id()) {
				solicitudesOT.setDGerencia_nombre("JEFE/A DEL SERVICIO DE FORMACIÓN");
				solicitudesOT.setDDirector("____________________________");
			} else {
				solicitudesOT.setDGerencia_nombre("EL DIRECTOR DE GESTIÓN DE LA "
						+ ((OCAPNuevaSolicitudForm) form).getDGerencia_nombre().toUpperCase());
				OCAPGerenciasLN gerenciaLN = new OCAPGerenciasLN(jcylUsuario);
				OCAPGerenciasOT gerenciaOT = gerenciaLN.buscarOCAPGerencias(solicitudesOT.getCGerencia_id());
				solicitudesOT.setDDirector(gerenciaOT.getDDirector());
			}
			solicitudesOT.setFRegistro_oficial(((OCAPNuevaSolicitudForm) form).getFRegistro_oficial().substring(0, 10));
			solicitudesOT.setBSexo(((OCAPNuevaSolicitudForm) form).getBSexo());
			solicitudesLN.crearPDFSubsanacion(response, solicitudesOT, pathBase);

			OCAPConfigApp.logger.info(getClass().getName() + " generarPDFSubsanacion: Fin");
		} catch (SendFailedException e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.envioEmail")));
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irListado");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ArrayList comboEstados(ArrayList listaEstados, boolean bSubsana, boolean bExcluye, JCYLUsuario jcylUsuario) throws Exception {
		ArrayList estadosAct = null;
		OCAPEstadosOT estados = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " comboEstados: Inicio");

			estadosAct = new ArrayList();
			for (int j = 0; j < listaEstados.size(); j++) {
				estados = (OCAPEstadosOT) listaEstados.get(j);
				if (estados.getcEstadoId() != 2) {
					if (bSubsana) {
						if (estados.getcEstadoId() != 7) {
							if(!estadoDesistidoPgp(estados, jcylUsuario)) {
								estadosAct.add(estados);
							}
						}
					} else if (estados.getcEstadoId() != 6) {
						if (estados.getcEstadoId() == 7) {
							if (bExcluye) {
								estadosAct.add(estados);
							}

						} else {
							if(!estadoDesistidoPgp(estados, jcylUsuario)) {
								estadosAct.add(estados);
							}
						}
					}
				}
			}

			OCAPConfigApp.logger.info(getClass().getName() + " comboEstados: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return estadosAct;
	}

	private boolean estadoDesistidoPgp(OCAPEstadosOT estados, JCYLUsuario jcylUsuario) {
		return estados.getcEstadoId() == Constantes.ESTADO_DESISTIDA && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE);
	}

	public ActionForward irCopiarDNI(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irCopiarDNI: Inicio");
			validarAccion(mapping, form, request, response);

			Usuario usuarioSEGU = new Usuario();
			JCYLUsuario jcylUsuario = new JCYLUsuario(usuarioSEGU);

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			ArrayList listadoConvocatorias = convocatoriasLN.listarConvocatorias();

			if (session.getAttribute(Constantes.COMBO_CONVOCATORIAS) != null)
				session.removeAttribute(Constantes.COMBO_CONVOCATORIAS);
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);

			OCAPConfigApp.logger.info(getClass().getName() + " irCopiarDNI: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irCopiarDNI");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward copiarDNI(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionErrors errors = new ActionErrors();
		ArrayList listaRepetidos = null;
		boolean bDNIsRepetidos = false;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " copiarDNI: Inicio");
			validarAccion(mapping, form, request, response);

			Usuario usuarioSEGU = new Usuario();
			JCYLUsuario jcylUsuario = new JCYLUsuario(usuarioSEGU);

			OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);
			listaRepetidos = usuariosLN.reemplazarDNIRealPorConvocatoriaGradoYEstado(
					Long.parseLong(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()), 0L, 3);

			if ((listaRepetidos != null) && (listaRepetidos.size() != 0)) {
				bDNIsRepetidos = true;
				((OCAPNuevaSolicitudForm) form).setListaUsuarios(listaRepetidos);
			}

			OCAPConfigApp.logger.info(getClass().getName() + " copiarDNI: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			if (!bDNIsRepetidos) {
				request.setAttribute("rutaVuelta", "PaginaInicio.do");

				return mapping.findForward("mensajeExitoSinFormulario");
			}

			return mapping.findForward("irCopiarDNI");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irBuscarAdmGrs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoGrados = null;
		ArrayList listadoGerencias = null;
		ArrayList listaAccion = new ArrayList();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irBuscarAdmGrs: Inicio");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			listadoGrados = gradoLN.listarGrados();
			session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, listadoGrados);

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);

			listadoGerencias = gerenciasLN.listadoOCAPGerencias();
			session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);

			session.setAttribute("iRegistro", Integer.toString(1));

			OCAPConvocatoriasLN convoLN = new OCAPConvocatoriasLN(jcylUsuario);
			ArrayList listaConvocatorias = convoLN.listarConvocatoriasActivas();
			if ((listaConvocatorias == null) || (listaConvocatorias.size() == 0)) {
				return mapping.findForward("irNoExistenConvocatorias");
			}
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listaConvocatorias);

			((OCAPNuevaSolicitudForm) form).setjspInicio(true);

			if ("edc".equals(request.getParameter("opcion"))) {
				listaAccion.add(new LabelValueBean("Excluir", Constantes.ESTADO_EXCLUIDO_VALUE));
				listaAccion.add(new LabelValueBean("Incluir", "I"));
				session.setAttribute(Constantes.COMBO_ACCIONES, listaAccion);
			}

			request.setAttribute("opcion", request.getParameter("opcion"));
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			if ((request.getParameter("opcion") != null)
					&& (request.getParameter("opcion").equals("fechasExpediente"))) {
				return mapping.findForward("irBuscarAdmGrsFechas");
			}

			return mapping.findForward("irBuscarAdmGrs");
		}

		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward buscarAdmGrs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoSolicitudes = null;

		boolean esAgrupado = false;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarAdmGrs: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPNuevaSolicitudForm) request.getSession().getAttribute("OCAPNuevaSolicitudFormBuscador");
				request.setAttribute("OCAPNuevaSolicitudForm", form);
			} else {
				request.getSession().setAttribute("OCAPNuevaSolicitudFormBuscador", form);

				session.setAttribute("iRegistro", 1 + "");
			}

			int primerRegistro = 1;
			int registrosPorPagina = 20;
			String registro = null;

			if ((registro = request.getParameter("iRegistro")) != null)
				try {
					primerRegistro = Integer.parseInt(registro);
					session.setAttribute("iRegistro", primerRegistro + "");
				} catch (NumberFormatException ne) {
				}
			else if ((registro = session.getAttribute("iRegistro") == null ? null
					: session.getAttribute("iRegistro").toString()) != null) {
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

			solicitudesOT.setDApellido1(((OCAPNuevaSolicitudForm) form).getDApellido1());
			solicitudesOT.setDNombre(((OCAPNuevaSolicitudForm) form).getDNombre());
			if (((OCAPNuevaSolicitudForm) form).getCDni() != null) {
				solicitudesOT.setCDni(((OCAPNuevaSolicitudForm) form).getCDni().toUpperCase());
			}
			if ((((OCAPNuevaSolicitudForm) form).getCGrado_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCGrado_id().equals("")))
				solicitudesOT.setCGrado_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGrado_id()));
			else if ((((OCAPNuevaSolicitudForm) form).getCGrado_id() == null)
					|| (((OCAPNuevaSolicitudForm) form).getCGrado_id().equals(""))) {
				solicitudesOT.setCGrado_id(0L);
			}

			if ((((OCAPNuevaSolicitudForm) form).getCGerencia_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCGerencia_id().equals("")))
				solicitudesOT.setCGerencia_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGerencia_id()));
			else if ((((OCAPNuevaSolicitudForm) form).getCGerencia_id() == null)
					|| (((OCAPNuevaSolicitudForm) form).getCGerencia_id().equals(""))) {
				solicitudesOT.setCGerencia_id(0L);
			}
			
			
			if ((((OCAPNuevaSolicitudForm) form).getCConvocatoriaId() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCConvocatoriaId().equals("")))
				solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));
			else if ((((OCAPNuevaSolicitudForm) form).getCConvocatoriaId() == null)
					|| (((OCAPNuevaSolicitudForm) form).getCConvocatoriaId().equals(""))) {
				solicitudesOT.setCConvocatoriaId(0L);
			}			

			//solicitudesOT.setCConvocatoriaId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));

			if ("fechas".equals(request.getParameter("opcion"))) {
				solicitudesOT.setCFase(Constantes.FASE_MC);
				solicitudesOT.setCEstado(Constantes.FASE_MC);
			} else if (!"fechasExpediente".equals(request.getParameter("opcion"))) {
				esAgrupado = true;
				solicitudesOT.setCFase(Constantes.FASE_CA_INCL);
				if ((((OCAPNuevaSolicitudForm) form).getDAccion() != null)
						&& (!"".equals(((OCAPNuevaSolicitudForm) form).getDAccion()))) {
					if (Constantes.ESTADO_EXCLUIDO_VALUE.equals(((OCAPNuevaSolicitudForm) form).getDAccion()))
						solicitudesOT.setCEstado(Constantes.ESTADO_EXCLUIDO_VALUE);
					else {
						solicitudesOT.setCEstado("I");
					}
				}
			}
			int numSolicitudes = 0;
			if (!"fechasExpediente".equals(request.getParameter("opcion"))) {
				listadoSolicitudes = solicitudesLN.buscar(primerRegistro, registrosPorPagina, solicitudesOT, esAgrupado,
						"", false);
				numSolicitudes = solicitudesLN.contarSolicitudes(solicitudesOT, jcylUsuario, "", false);
			} else {
				listadoSolicitudes = solicitudesLN.buscar(primerRegistro, registrosPorPagina, solicitudesOT, esAgrupado,
						"fechasExpediente", false);
				numSolicitudes = solicitudesLN.contarSolicitudes(solicitudesOT, jcylUsuario, "fechasExpediente", false);
			}

			session.setAttribute("numSolicitudes", Integer.valueOf(numSolicitudes));

			Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
			pagina.setElementos(listadoSolicitudes);
			pagina.setNRegistros(((Integer) session.getAttribute("numSolicitudes")).intValue());
			pagina.setRegistroActual(primerRegistro);
			pagina.setRegistrosPorPagina(registrosPorPagina);

			request.setAttribute("listados", pagina);
			request.setAttribute("opcion", request.getParameter("opcion"));

			HttpSession sesion = request.getSession();
			OCAPEstadosLN estadosLN = new OCAPEstadosLN(jcylUsuario);
			ArrayList listadoEstados = estadosLN.listarEstados();
			sesion.setAttribute(Constantes.COMBO_ESTADOS, listadoEstados);

			OCAPConfigApp.logger.info(getClass().getName() + " buscarAdmGrs: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			if ("fechasExpediente".equals(request.getParameter("opcion"))) {
				return mapping.findForward("irBuscarAdmGrsFechas");
			}

			return mapping.findForward("irBuscarAdmGrs");
		}

		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward detalleSolicitudGrs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		OCAPSolicitudesOT solicitudOT = null;
		String forward = "";
		OCAPConvocatoriasLN convocatoriaLN = null;
		OCAPConvocatoriasOT convoOT = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " detalleSolicitudGrs: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			solicitudOT = new OCAPSolicitudesOT();
			solicitudOT.setCExp_id(Long.parseLong(request.getParameter("CExp_id")));
			solicitudOT = solicitudesLN.datosSolicitud(solicitudOT, jcylUsuario);
			OCAPNuevaSolicitudLN nuevaSolicitudLN = new OCAPNuevaSolicitudLN(jcylUsuario);
			OCAPSolicitudesOT solicitudUsuarioOT = nuevaSolicitudLN.detalleSolicitud(solicitudOT.getCSolicitudId());
			
			((OCAPNuevaSolicitudForm) form).setCSolicitudId(solicitudOT.getCSolicitudId());

			((OCAPNuevaSolicitudForm) form).setCUsr_id(String.valueOf(solicitudOT.getCUsr_id()));
			((OCAPNuevaSolicitudForm) form).setDNombre(solicitudUsuarioOT.getDNombre());
			((OCAPNuevaSolicitudForm) form).setDApellido1(solicitudUsuarioOT.getDApellido1());
			((OCAPNuevaSolicitudForm) form).setCDniReal(solicitudUsuarioOT.getCDniReal());
			((OCAPNuevaSolicitudForm) form).setBSexo(solicitudUsuarioOT.getBSexo());
			((OCAPNuevaSolicitudForm) form).setNTelefono1(solicitudUsuarioOT.getNTelefono1());
			((OCAPNuevaSolicitudForm) form).setNTelefono2(solicitudUsuarioOT.getNTelefono2());
			((OCAPNuevaSolicitudForm) form).setDCorreoelectronico(solicitudUsuarioOT.getDCorreoelectronico());
			((OCAPNuevaSolicitudForm) form).setDDomicilio(solicitudUsuarioOT.getDDomicilio());
			((OCAPNuevaSolicitudForm) form).setDProvincia(solicitudUsuarioOT.getDProvinciaUsu());
			((OCAPNuevaSolicitudForm) form).setDLocalidad(solicitudUsuarioOT.getDLocalidad());

			if (solicitudUsuarioOT.getDLocalidad() != null)
				((OCAPNuevaSolicitudForm) form).setCLocalidad_id(solicitudUsuarioOT.getDLocalidad().toUpperCase());
			DecimalFormat formato = new DecimalFormat("00000");
			((OCAPNuevaSolicitudForm) form).setCPostalUsu(solicitudUsuarioOT.getCPostalUsu() == null ? ""
					: formato.format(Long.parseLong(solicitudUsuarioOT.getCPostalUsu())));
			
					
			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			OCAPGradoOT gradoOT = new OCAPGradoOT();
			if (solicitudOT.getCGrado_id() != 0L) {
				gradoOT = gradoLN.buscarOCAPGrado(solicitudOT.getCGrado_id());
				((OCAPNuevaSolicitudForm) form).setDGrado_des(gradoOT.getDDescripcion());
				((OCAPNuevaSolicitudForm) form).setCGrado_id(String.valueOf(solicitudOT.getCGrado_id()));
			}

			OCAPModalidadAnteriorLN modalidadAnteriorLN = new OCAPModalidadAnteriorLN(jcylUsuario);

			if (solicitudOT.getCModAnterior_id() != 0L) {
				OCAPModalidadAnteriorOT modaliadAnteriorOT = modalidadAnteriorLN
						.buscarOCAPModalidad(solicitudOT.getCModAnterior_id());
				((OCAPNuevaSolicitudForm) form).setDModAnterior(modaliadAnteriorOT.getDDescripcion());
				((OCAPNuevaSolicitudForm) form).setCModAnteriorId(String.valueOf(solicitudOT.getCModAnterior_id()));
				((OCAPNuevaSolicitudForm) form).setCGradoAnteriorId(String.valueOf(modaliadAnteriorOT.getCGradoId()));
			}

			OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);

			if ((solicitudOT.getCProcedimientoId() != null) && (!solicitudOT.getCProcedimientoId().equals(""))) {
				OCAPProcedimientoOT procOT = procLN
						.buscarOCAPProcedimiento(Long.parseLong(solicitudOT.getCProcedimientoId()));
				((OCAPNuevaSolicitudForm) form).setDProcedimiento(procOT.getDNombre());
				((OCAPNuevaSolicitudForm) form).setCProcedimientoId(solicitudOT.getCProcedimientoId());
			}

			OCAPCategProfesionalesLN categProfLN = new OCAPCategProfesionalesLN(jcylUsuario);
			OCAPCategProfesionalesOT categProfOT = null;
			if (solicitudOT.getCProfesional_id() != 0L) {
				categProfOT = categProfLN.buscarOCAPCategProfesionales(solicitudOT.getCProfesional_id());
				((OCAPNuevaSolicitudForm) form).setDProfesional_nombre(categProfOT.getDNombre());
				((OCAPNuevaSolicitudForm) form).setCProfesional_id(String.valueOf(solicitudOT.getCProfesional_id()));
			} else {
				categProfOT = new OCAPCategProfesionalesOT();
			}

			OCAPEspecialidadesLN especLN = new OCAPEspecialidadesLN(jcylUsuario);

			if (solicitudOT.getCEspec_id() != 0L) {
				OCAPEspecialidadesOT especOT = especLN.buscarOCAPEspecialidades(solicitudOT.getCEspec_id());
				((OCAPNuevaSolicitudForm) form).setDEspec_nombre(especOT.getDNombre());
				((OCAPNuevaSolicitudForm) form).setCEspec_id(String.valueOf(solicitudOT.getCEspec_id()));
			}

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);

			if (solicitudUsuarioOT.getCGerencia_id() != 0L) {
				OCAPGerenciasOT gerenciasOT = gerenciasLN.buscarOCAPGerencias(solicitudUsuarioOT.getCGerencia_id());
				((OCAPNuevaSolicitudForm) form).setDGerencia_nombre(gerenciasOT.getDNombre());
				((OCAPNuevaSolicitudForm) form).setCGerencia_id(String.valueOf(solicitudUsuarioOT.getCGerencia_id()));
			}

			OCAPProvinciasOT provinciaOT = null;
			OCAPProvinciasLN provinciaLN = new OCAPProvinciasLN(jcylUsuario);
			if ((solicitudUsuarioOT.getCProvinciaCarrera_id() != null)
					&& (!solicitudUsuarioOT.getCProvinciaCarrera_id().equals(""))) {
				provinciaOT = provinciaLN.buscarProvincia(String.valueOf(solicitudUsuarioOT.getCProvinciaCarrera_id()));
				((OCAPNuevaSolicitudForm) form).setDProvinciaCarrera(Cadenas.capitalizar(provinciaOT.getDProvincia()));
				((OCAPNuevaSolicitudForm) form)
						.setCProvinciaCarrera_id(String.valueOf(solicitudUsuarioOT.getCProvinciaCarrera_id()));
			}

			if ((solicitudOT.getCProvincia_id() != null) && (!solicitudOT.getCProvincia_id().equals(""))) {
				provinciaOT = provinciaLN.buscarProvincia(solicitudOT.getCProvincia_id());
				//((OCAPNuevaSolicitudForm) form).setDProvincia(Cadenas.capitalizar(provinciaOT.getDProvincia()));
				((OCAPNuevaSolicitudForm) form).setCProvincia_id(String.valueOf(solicitudOT.getCProvincia_id()));
			}

			OCAPCentroTrabajoLN centroLN = new OCAPCentroTrabajoLN(jcylUsuario);
			OCAPCentroTrabajoOT centroOT = centroLN.buscarOCAPCentroTrabajo(solicitudUsuarioOT.getCCentrotrabajo_id());
			((OCAPNuevaSolicitudForm) form).setDCentrotrabajo_nombre(centroOT.getDNombre());
			((OCAPNuevaSolicitudForm) form).setCCentrotrabajo_id(String.valueOf(solicitudUsuarioOT.getCCentrotrabajo_id()));

			((OCAPNuevaSolicitudForm) form).setCJuridicoId(solicitudOT.getCJuridicoId());

			OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
			OCAPEstatutarioOT estatutarioOT = estatutarioLN.buscarOCAPEstatutario(categProfOT.getCEstatutId());
			((OCAPNuevaSolicitudForm) form).setCEstatutId(categProfOT.getCEstatutId());

			if ("1".equals(solicitudOT.getCJuridicoId())) {
				((OCAPNuevaSolicitudForm) form).setCJuridicoCombo(String.valueOf(estatutarioOT.getCPersonalId()));
				if (solicitudOT.getCProfesional_id() != 0L)
					((OCAPNuevaSolicitudForm) form)
							.setCProfesionalFijo_id(String.valueOf(solicitudOT.getCProfesional_id()));
				if (solicitudOT.getCEspec_id() != 0L) {
					((OCAPNuevaSolicitudForm) form).setCEspecFijo_id(String.valueOf(solicitudOT.getCEspec_id()));
				}
			}
			if ("3".equals(solicitudOT.getCJuridicoId())) {
				((OCAPNuevaSolicitudForm) form).setDRegimenJuridicoOtros(solicitudOT.getDRegimenJuridicoOtros());
			}

			((OCAPNuevaSolicitudForm) form).setCSitAdministrativaId(solicitudOT.getCSitAdministrativaAuxId());

			((OCAPNuevaSolicitudForm) form).setDSitAdministrativaOtros(solicitudOT.getDSitAdministrativaOtros());
			((OCAPNuevaSolicitudForm) form).setNAniosantiguedad(String.valueOf(solicitudOT.getNAniosantiguedad()));
			((OCAPNuevaSolicitudForm) form).setNMesesantiguedad(String.valueOf(solicitudOT.getNMesesantiguedad()));
			((OCAPNuevaSolicitudForm) form).setNDiasantiguedad(String.valueOf(solicitudOT.getNDiasantiguedad()));
			((OCAPNuevaSolicitudForm) form).setCConvocatoriaId(String.valueOf(solicitudOT.getCConvocatoriaId()));
			((OCAPNuevaSolicitudForm) form).setBOtroServicio(solicitudOT.getBOtroServicio());
			((OCAPNuevaSolicitudForm) form).setADondeServicio(solicitudOT.getADondeServicio());
			((OCAPNuevaSolicitudForm) form).setResumenCentros(solicitudOT.getResumenCentros());
			((OCAPNuevaSolicitudForm) form).setFRegistro_solic(solicitudOT.getFRegistro_solic());
			((OCAPNuevaSolicitudForm) form).setDServicio(solicitudOT.getAServicio());
			((OCAPNuevaSolicitudForm) form).setDPuesto(solicitudOT.getAPuesto());
			((OCAPNuevaSolicitudForm) form).setDConvocatoria_nombre(solicitudOT.getDConvocatoria_nombre());
			((OCAPNuevaSolicitudForm) form).setAnioConvocatoria(solicitudOT.getDAnioConvocatoria());
			((OCAPNuevaSolicitudForm) form).setMeritosBloqueados(solicitudOT.getMeritosBloqueados() != null?solicitudOT.getMeritosBloqueados().toUpperCase():"N");
		//	((OCAPNuevaSolicitudForm) form).setEliminarEvaluacion(solicitudUsuarioOT.isEliminarEvaluacion());

			if (solicitudOT.getFInicio_mc() != null) {
				((OCAPNuevaSolicitudForm) form)
						.setFInicio_mc(DateUtil.convertDateToString(solicitudOT.getFInicio_mc()));
			}
			if (solicitudOT.getFFin_mc() != null) {
				((OCAPNuevaSolicitudForm) form).setFFin_mc(DateUtil.convertDateToString(DateUtil.addDias(solicitudOT.getFFin_mc(), -1)));
			}
			if (solicitudOT.getFInicio_eval_mc() != null) {
				((OCAPNuevaSolicitudForm) form)
						.setFInicio_eval_mc(DateUtil.convertDateToString(solicitudOT.getFInicio_eval_mc()));
			}
			if (solicitudOT.getFFin_eval_mc() != null) {
				((OCAPNuevaSolicitudForm) form)
						.setFFin_eval_mc(DateUtil.convertDateToString(solicitudOT.getFFin_eval_mc()));
			}
			if (solicitudOT.getFNegacion_mc() != null) {
				((OCAPNuevaSolicitudForm) form)
						.setFNegacion_mc(DateUtil.convertDateToString(solicitudOT.getFNegacion_mc()));
			}
			if (solicitudOT.getFAceptacionceis() != null) {
				((OCAPNuevaSolicitudForm) form)
						.setFAceptacionceis(DateUtil.convertDateToString(solicitudOT.getFAceptacionceis()));
			}
			if (solicitudOT.getFInformeEval() != null) {
				((OCAPNuevaSolicitudForm) form)
						.setFInformeEval(DateUtil.convertDateToString(solicitudOT.getFInformeEval()));
			}
			if (solicitudOT.getFFin_ca() != null) {
				((OCAPNuevaSolicitudForm) form).setFFin_ca(DateUtil.convertDateToString(solicitudOT.getFFin_ca()));
			}
			if (solicitudOT.getFInicio_cc() != null) {
				((OCAPNuevaSolicitudForm) form)
						.setFIniciocc(DateUtil.convertDateToString(solicitudOT.getFInicio_cc()));
			}
			if (solicitudOT.getFFin_cc() != null) {
				((OCAPNuevaSolicitudForm) form)
						.setFFincc(DateUtil.convertDateToString(solicitudOT.getFFin_cc()));
			}
			((OCAPNuevaSolicitudForm) form).setCEstadoFiltro(String.valueOf(solicitudOT.getCEstado_id()));

			convocatoriaLN = new OCAPConvocatoriasLN(jcylUsuario);
			convoOT = convocatoriaLN.buscarOCAPConvocatorias(solicitudOT.getCConvocatoriaId());

			((OCAPNuevaSolicitudForm) form).setFInicio_cc(convoOT.getFInicioEstudioCC());
			((OCAPNuevaSolicitudForm) form).setFFin_cc(convoOT.getFFinEstudioCC());
			if ((request.getParameter("menu") != null) && (!request.getParameter("menu").equals("modificarFechas"))) {
				((OCAPNuevaSolicitudForm) form).setFInicio_mc(DateUtil
						.convertDateToString(DateUtil.addDias(DateUtil.convertStringToDate(convoOT.getFInicioMC()),
								new Long(convoOT.getNDiasAutoMc()).intValue())));
			}
			((OCAPNuevaSolicitudForm) form).setFRegistro_oficial(
					DateUtil.convertDateToString(DateUtil.convertStringToDate(solicitudOT.getFRegistro_oficial())));

			if (solicitudOT.getFAceptac_solic() != null) {
				((OCAPNuevaSolicitudForm) form)
						.setFAceptac_solic(DateUtil.convertDateToString(solicitudOT.getFAceptac_solic()));
			}
			if (solicitudOT.getFAceptacionCC() != null) {
				((OCAPNuevaSolicitudForm) form)
						.setFAceptacionCC(DateUtil.convertDateToString(solicitudOT.getFAceptacionCC()));
			}
			if (solicitudOT.getFInicio_ca() != null) {
				((OCAPNuevaSolicitudForm) form)
						.setFInicio_ca(DateUtil.convertDateToString(solicitudOT.getFInicio_ca()));
			}
			if (solicitudOT.getFFin_ca() != null) {
				((OCAPNuevaSolicitudForm) form).setFFin_ca(DateUtil.convertDateToString(solicitudOT.getFFin_ca()));
			}
			if (solicitudOT.getFRespuestaInconf_MC() != null) {
				((OCAPNuevaSolicitudForm) form)
						.setFRespuesta_inconf_mc(DateUtil.convertDateToString(solicitudOT.getFRespuestaInconf_MC()));
			}
			if (solicitudOT.getFInconf_mc() != null) {
				((OCAPNuevaSolicitudForm) form)
						.setFInconf_mc(DateUtil.convertDateToString(solicitudOT.getFInconf_mc()));
			}
			if ((request.getParameter("opcion") != null) && ("edc".equals(request.getParameter("opcion")))) {
				if (solicitudOT.getFRespuestaInconf_MC() != null) {
					request.setAttribute("excluir", Constantes.NO);
					((OCAPNuevaSolicitudForm) form).setFInicio_ca(DateUtil.convertDateToString(new Date()));
					((OCAPNuevaSolicitudForm) form).setFFin_ca(DateUtil
							.convertDateToString(DateUtil.addDias(DateUtil.convertStringToDate(convoOT.getFInicioCA()),
									new Long(convoOT.getNDiasAutoCa()).intValue())));
				} else {
					request.setAttribute("excluir", Constantes.SI);
				}
			}
			
			((OCAPNuevaSolicitudForm) form).setCSitAdmonActualId(String.valueOf(solicitudUsuarioOT.getCSitAdmonActualId()));
			((OCAPNuevaSolicitudForm) form).setDSitAdmonActual(solicitudUsuarioOT.getDSitAdmonActual());
			((OCAPNuevaSolicitudForm) form).setAOtraSitAdmonActual(solicitudUsuarioOT.getAOtraSitAdmonActual());
			provinciaLN = new OCAPProvinciasLN(jcylUsuario);
			if ((solicitudUsuarioOT.getCProvCarreraActualId() != null)
					&& (!solicitudUsuarioOT.getCProvCarreraActualId().equals(""))) {
				provinciaOT = provinciaLN.buscarProvincia(String.valueOf(solicitudUsuarioOT.getCProvCarreraActualId()));
				((OCAPNuevaSolicitudForm) form).setDProvCarreraActual(Cadenas.capitalizar(provinciaOT.getDProvincia()));
				((OCAPNuevaSolicitudForm) form)
						.setCProvCarreraActualId(String.valueOf(solicitudUsuarioOT.getCProvCarreraActualId()));
			}
			if (solicitudUsuarioOT.getCGerenciaActualId() != 0L) {
				OCAPGerenciasOT gerenciasOT = gerenciasLN.buscarOCAPGerencias(solicitudUsuarioOT.getCGerenciaActualId());
				((OCAPNuevaSolicitudForm) form).setDGerenciaActual(gerenciasOT.getDNombre());
				((OCAPNuevaSolicitudForm) form).setCGerenciaActualId(String.valueOf(solicitudUsuarioOT.getCGerenciaActualId()));
			}

			OCAPCentroTrabajoOT centroActualOT = null;
			if (solicitudUsuarioOT.getCCentroTrabActualId() != 0L) {
				centroActualOT = centroLN.buscarOCAPCentroTrabajo(solicitudUsuarioOT.getCCentroTrabActualId());
				((OCAPNuevaSolicitudForm) form).setDCentroTrabActual(centroActualOT.getDNombre());
				((OCAPNuevaSolicitudForm) form)
						.setCCentroTrabActualId(String.valueOf(solicitudUsuarioOT.getCCentroTrabActualId()));
			}
			
			if (solicitudOT.getFRenuncia() != null) {
				((OCAPNuevaSolicitudForm) form)
						.setFRenuncia(solicitudOT.getFRenuncia());
			}
			String convF = convocatoriaLN.buscarOCAPConvocatoriasPorCA(convoOT.getCConvocatoriaId());
			((OCAPNuevaSolicitudForm) form).setcItineraio_id(solicitudOT.getCItinerario_id()== 0?null:String.valueOf(solicitudOT.getCItinerario_id()));
			((OCAPNuevaSolicitudForm) form).setfFin_ca_convocatoria(convF);
			request.setAttribute("tipoAccionBis", Constantes.FASE_MC);
			request.setAttribute("tipoAccion", Constantes.IR_DETALLE);
			request.setAttribute("opcion", request.getParameter("opcion"));
			request.setAttribute("vuelta", request.getParameter("vuelta") == null ? request.getAttribute("vuelta")
					: request.getParameter("vuelta"));

			if ((request.getParameter("menu") != null) && (request.getParameter("menu").equals("modificarFechas"))) {
				forward = "irNuevaInsertarFechas";
			} else
				forward = "irNuevaInsertar";

			OCAPConfigApp.logger.info(getClass().getName() + " detalleSolicitudGrs: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(forward);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward modificarFechasMC(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " modificarFechasMC: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();

			expedienteCarreraOT.setCExpId(Long.valueOf(((OCAPNuevaSolicitudForm) form).getCExp_id()));

			if ((((OCAPNuevaSolicitudForm) form).getFFin_mc() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getFFin_mc()))) {
				expedienteCarreraOT
						.setFFinMc(DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFFin_mc()));
			}
			if ((((OCAPNuevaSolicitudForm) form).getFFin_eval_mc() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getFFin_eval_mc()))) {
				expedienteCarreraOT
						.setFFinEvalMc(DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFFin_eval_mc()));
			}
			if ((((OCAPNuevaSolicitudForm) form).getFInicio_eval_mc() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getFInicio_eval_mc()))) {
				expedienteCarreraOT.setFInicioEvalMc(
						DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFInicio_eval_mc()));
			}
			expedienteCarreraOT.setAModificadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());

			expedienteCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);

			request.setAttribute("rutaVuelta",
					"OCAPNuevaSolicitud.do?accion=buscarAdmGrs&recuperarBusquedaAnterior=Y&opcion="
							+ request.getParameter("opcion"));

			OCAPConfigApp.logger.info(getClass().getName() + " modificarFinMC: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty()))) {
			return mapping.findForward("mensajeExito");
		}
		if ((errors != null) && (!errors.isEmpty())) {
			saveErrors(request, errors);

			return mapping.findForward("fallo");
		}
		saveMessages(request, messages);

		return mapping.findForward("irNuevaInsertar");
	}

	public ActionForward irDetalleFqs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoEvaluadores = null;
		OCAPDocumentosLN docuLN = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + ": irDetalleFqs");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
			OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();
			solicitudesOT.setCExp_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCExp_id()));
			solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);

			((OCAPNuevaSolicitudForm) form).setDNombre(solicitudesOT.getDNombre());
			((OCAPNuevaSolicitudForm) form).setDApellido1(solicitudesOT.getDApellido1());
			((OCAPNuevaSolicitudForm) form).setDCorreoelectronico(solicitudesOT.getDCorreoelectronico());
			((OCAPNuevaSolicitudForm) form).setCDniReal(solicitudesOT.getCDniReal());
			((OCAPNuevaSolicitudForm) form).setBSexo(solicitudesOT.getBSexo());

			if (solicitudesOT.getNTelefono1().equals("0"))
				((OCAPNuevaSolicitudForm) form).setNTelefono1("-");
			else {
				((OCAPNuevaSolicitudForm) form).setNTelefono1(String.valueOf(solicitudesOT.getNTelefono1()));
			}

			if (solicitudesOT.getNTelefono2().equals("0"))
				((OCAPNuevaSolicitudForm) form).setNTelefono2("-");
			else {
				((OCAPNuevaSolicitudForm) form).setNTelefono2(String.valueOf(solicitudesOT.getNTelefono2()));
			}

			((OCAPNuevaSolicitudForm) form).setDDomicilio(solicitudesOT.getDDomicilio());
			((OCAPNuevaSolicitudForm) form).setDLocalidad(solicitudesOT.getDLocalidad());

			DecimalFormat formato = new DecimalFormat("00000");
			((OCAPNuevaSolicitudForm) form).setCPostalUsu(solicitudesOT.getCPostalUsu() == null ? ""
					: formato.format(Long.parseLong(solicitudesOT.getCPostalUsu())));
			((OCAPNuevaSolicitudForm) form).setDGrado_des(solicitudesOT.getDGrado_des());

			OCAPModalidadAnteriorLN modalidadAnteriorLN = new OCAPModalidadAnteriorLN(jcylUsuario);
			if (solicitudesOT.getCModAnterior_id() != 0L) {
				OCAPModalidadAnteriorOT modaliadAnteriorOT = modalidadAnteriorLN
						.buscarOCAPModalidad(solicitudesOT.getCModAnterior_id());
				((OCAPNuevaSolicitudForm) form).setDModAnterior(modaliadAnteriorOT.getDDescripcion());
				((OCAPNuevaSolicitudForm) form).setCModAnteriorId(String.valueOf(solicitudesOT.getCModAnterior_id()));
				((OCAPNuevaSolicitudForm) form).setCGradoAnteriorId(String.valueOf(modaliadAnteriorOT.getCGradoId()));
			}

			OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
			if ((solicitudesOT.getCProcedimientoId() != null) && (!solicitudesOT.getCProcedimientoId().equals(""))) {
				OCAPProcedimientoOT procOT = procLN
						.buscarOCAPProcedimiento(Long.parseLong(solicitudesOT.getCProcedimientoId()));
				((OCAPNuevaSolicitudForm) form).setDProcedimiento(procOT.getDNombre());
			}

			((OCAPNuevaSolicitudForm) form).setCJuridicoId(solicitudesOT.getCJuridicoId());

			OCAPCategProfesionalesLN categProfLN = new OCAPCategProfesionalesLN(jcylUsuario);
			OCAPCategProfesionalesOT categProfOT = null;
			if (solicitudesOT.getCProfesional_id() != 0L) {
				categProfOT = categProfLN.buscarOCAPCategProfesionales(solicitudesOT.getCProfesional_id());
				((OCAPNuevaSolicitudForm) form).setDProfesional_nombre(categProfOT.getDNombre());
			} else {
				categProfOT = new OCAPCategProfesionalesOT();
			}

			OCAPEspecialidadesLN especLN = new OCAPEspecialidadesLN(jcylUsuario);
			if (solicitudesOT.getCEspec_id() != 0L) {
				OCAPEspecialidadesOT especOT = especLN.buscarOCAPEspecialidades(solicitudesOT.getCEspec_id());
				((OCAPNuevaSolicitudForm) form).setDEspec_nombre(especOT.getDNombre());
			}

			OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
			OCAPEstatutarioOT estatutarioOT = estatutarioLN.buscarOCAPEstatutario(categProfOT.getCEstatutId());

			if ("1".equals(solicitudesOT.getCJuridicoId())) {
				((OCAPNuevaSolicitudForm) form).setCJuridicoCombo(String.valueOf(estatutarioOT.getCPersonalId()));
			}

			if ("3".equals(solicitudesOT.getCJuridicoId())) {
				((OCAPNuevaSolicitudForm) form).setDRegimenJuridicoOtros(solicitudesOT.getDRegimenJuridicoOtros());
			}

			((OCAPNuevaSolicitudForm) form).setCSitAdministrativaId(solicitudesOT.getCSitAdministrativaAuxId());

			OCAPProvinciasOT provinciaOT = null;
			OCAPProvinciasLN provinciaLN = new OCAPProvinciasLN(jcylUsuario);
			if ((solicitudesOT.getCProvinciaCarrera_id() != null)
					&& (!solicitudesOT.getCProvinciaCarrera_id().equals(""))) {
				provinciaOT = provinciaLN.buscarProvincia(String.valueOf(solicitudesOT.getCProvinciaCarrera_id()));
				((OCAPNuevaSolicitudForm) form).setDProvinciaCarrera(Cadenas.capitalizar(provinciaOT.getDProvincia()));
			}

			if ((solicitudesOT.getCProvincia_id() != null) && (!solicitudesOT.getCProvincia_id().equals(""))) {
				provinciaOT = provinciaLN.buscarProvincia(solicitudesOT.getCProvincia_id());
				((OCAPNuevaSolicitudForm) form).setDProvincia(Cadenas.capitalizar(provinciaOT.getDProvincia()));
			}

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			if (solicitudesOT.getCGerencia_id() != 0L) {
				OCAPGerenciasOT gerenciasOT = gerenciasLN.buscarOCAPGerencias(solicitudesOT.getCGerencia_id());
				((OCAPNuevaSolicitudForm) form).setDGerencia_nombre(gerenciasOT.getDNombre());
			}

			OCAPCentroTrabajoLN centroLN = new OCAPCentroTrabajoLN(jcylUsuario);
			OCAPCentroTrabajoOT centroOT = centroLN.buscarOCAPCentroTrabajo(solicitudesOT.getCCentrotrabajo_id());
			((OCAPNuevaSolicitudForm) form).setDCentrotrabajo_nombre(centroOT.getDNombre());

			((OCAPNuevaSolicitudForm) form).setDServicio(solicitudesOT.getAServicio());
			((OCAPNuevaSolicitudForm) form).setDPuesto(solicitudesOT.getAPuesto());
			((OCAPNuevaSolicitudForm) form).setNAniosantiguedad(String.valueOf(solicitudesOT.getNAniosantiguedad()));
			((OCAPNuevaSolicitudForm) form).setNMesesantiguedad(String.valueOf(solicitudesOT.getNMesesantiguedad()));
			((OCAPNuevaSolicitudForm) form).setNDiasantiguedad(String.valueOf(solicitudesOT.getNDiasantiguedad()));

			if (solicitudesOT.getFInformeEval() != null)
				((OCAPNuevaSolicitudForm) form)
						.setFInformeEval(DateUtil.convertDateToString(solicitudesOT.getFInformeEval()));
			else {
				((OCAPNuevaSolicitudForm) form).setFInformeEval("");
			}
			((OCAPNuevaSolicitudForm) form).setListaCreditos(solicitudesOT.getListaCreditos());

			OCAPEvaluadoresLN evaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);

			if ((jcylUsuario.getUser().getRol() != null)
					&& (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_FQS_GEST))) {
				listadoEvaluadores = evaluadoresLN.listadoOCAPEvaluadores(solicitudesOT.getCItinerario_id());
				session.setAttribute(Constantes.COMBO_EVALUADORES, listadoEvaluadores);
				Object[] listadoeval = listadoEvaluadores.toArray();
				for (int i = 0; i < listadoEvaluadores.size(); i++)
					if (((OCAPComponentesfqsOT) listadoeval[i]).getCCompfqsId() == solicitudesOT.getCCompfqs_id()) {
						((OCAPNuevaSolicitudForm) form)
								.setCCompfqs_id(((OCAPComponentesfqsOT) listadoeval[i]).getCCompfqsId());
						break;
					}
			} else {
				OCAPComponentesfqsOT componentesOT = null;
				if (((OCAPNuevaSolicitudForm) form).getCCompfqs_id() != 0L)
					componentesOT = evaluadoresLN
							.buscarOCAPEvaluadores(((OCAPNuevaSolicitudForm) form).getCCompfqs_id());
				else {
					componentesOT = evaluadoresLN.buscarOCAPEvaluadores(solicitudesOT.getCCompfqs_id());
				}
				((OCAPNuevaSolicitudForm) form).setDApellNom(componentesOT.getDApellNom());

				if ((jcylUsuario.getUser().getRol() != null)
						&& (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION))) {
					listadoEvaluadores = evaluadoresLN.listadoOCAPEvaluadoresSegunda(solicitudesOT.getCItinerario_id());
					session.setAttribute(Constantes.COMBO_EVALUADORES, listadoEvaluadores);
					Object[] listadoeval = listadoEvaluadores.toArray();
					for (int i = 0; i < listadoEvaluadores.size(); i++) {
						if (((OCAPComponentesfqsOT) listadoeval[i]).getCCompfqsId() == solicitudesOT
								.getCCompfqs_id2()) {
							((OCAPNuevaSolicitudForm) form)
									.setCCompfqs_id(((OCAPComponentesfqsOT) listadoeval[i]).getCCompfqsId());
							break;
						}
					}
				}
			}

			if ((jcylUsuario.getUser().getRol() != null)
					&& (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_FQS_ADTVO))) {
				docuLN = new OCAPDocumentosLN();
				ArrayList listado = docuLN.buscarDocumentosRellenables(solicitudesOT.getCExp_id(),
						solicitudesOT.getCItinerario_id());
				((OCAPNuevaSolicitudForm) form).setListaDocumentos(listado);
			}

			if ((jcylUsuario.getUser().getRol() != null)
					&& (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CTE))) {
				request.setAttribute("cte", request.getParameter("cte"));
				request.setAttribute("nombre", request.getParameter("nombre"));
				request.setAttribute("apell", request.getParameter("apell"));
			}

			((OCAPNuevaSolicitudForm) form).setCFase(Constantes.FASE_AUTOEVALUACION);
			((OCAPNuevaSolicitudForm) form).setRContinuidad_proceso_ca(solicitudesOT.getRContinuidad_proceso_ca());
			((OCAPNuevaSolicitudForm) form).setRFase_ca(solicitudesOT.getRFase_ca());

			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			Date hoy = new Date();
			((OCAPNuevaSolicitudForm) form).setFRegistro_oficial(df.format(hoy));

			request.setAttribute("fase", request.getParameter("fase"));

			OCAPConfigApp.logger.info(getClass().getName() + " irDetalleFqs: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("detalleEvaluado");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irInsertarUsuarioPruebas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoProcedimientos = null;

		ArrayList listadoCategorias = null;
		ArrayList listadoEspecialidades = null;
		ArrayList listadoGrados = null;

		OCAPItinerariosLN itinerariosLN = null;
		ArrayList listaItinerarios = null;
		try {
			OCAPConfigApp.logger.info("irInsertarUsuarioPruebas: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			request.setAttribute("esUsuario", Constantes.SI);

			session.removeAttribute("UsuarioPruebaItinerario");

			Enumeration atributos = session.getAttributeNames();
			String nombreAtributo = "";
			while (atributos.hasMoreElements()) {
				nombreAtributo = atributos.nextElement().toString();
				if (nombreAtributo.startsWith("puntosPruebas"))
					session.removeAttribute(nombreAtributo);
				else if (nombreAtributo.startsWith("creditosPruebas")) {
					session.removeAttribute(nombreAtributo);
				}
			}

			if (session.getAttribute(Constantes.COMBO_GRADOS_ALTA) == null) {
				OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
				listadoGrados = gradoLN.listarGrados();
				session.setAttribute(Constantes.COMBO_GRADOS_ALTA, listadoGrados);
			}

			if (session.getAttribute(Constantes.COMBO_PERSONAL) == null) {
				OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
				listadoProcedimientos = procLN.listadoOCAPProcedimiento();
				session.setAttribute(Constantes.COMBO_PERSONAL, listadoProcedimientos);
			}

			if (session.getAttribute(Constantes.COMBO_CATEGORIA) == null) {
				OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
				listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();
				session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
			}

			OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
			if ((((OCAPNuevaSolicitudForm) form).getCProfesional_id() != null)
					&& (!((OCAPNuevaSolicitudForm) form).getCProfesional_id().equals(""))) {
				listadoEspecialidades = especialidadesLN
						.listarEspecialidades(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCProfesional_id()));
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
			} else {
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
			}

			itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
			long cGradoId = ((OCAPNuevaSolicitudForm) form).getCGrado_id() == null ? 0L
					: Long.parseLong("".equals(((OCAPNuevaSolicitudForm) form).getCGrado_id()) ? "0"
							: ((OCAPNuevaSolicitudForm) form).getCGrado_id());
			long cProfesionalId = ((OCAPNuevaSolicitudForm) form).getCProfesional_id() == null ? 0L
					: Long.parseLong("".equals(((OCAPNuevaSolicitudForm) form).getCProfesional_id()) ? "0"
							: ((OCAPNuevaSolicitudForm) form).getCProfesional_id());
			long cEspecId = ((OCAPNuevaSolicitudForm) form).getCEspec_id() == null ? 0L
					: Long.parseLong("".equals(((OCAPNuevaSolicitudForm) form).getCEspec_id()) ? "0"
							: ((OCAPNuevaSolicitudForm) form).getCEspec_id());
			long cProcedimientoId = ((OCAPNuevaSolicitudForm) form).getCProcedimientoId() == null ? 0L
					: Long.parseLong("".equals(((OCAPNuevaSolicitudForm) form).getCProcedimientoId()) ? "0"
							: ((OCAPNuevaSolicitudForm) form).getCProcedimientoId());
			listaItinerarios = itinerariosLN.listarItinerarios(cGradoId, cProfesionalId, cEspecId, cProcedimientoId);
			session.setAttribute(Constantes.COMBO_ITINERARIOS, listaItinerarios);

			OCAPConfigApp.logger.info("irInsertarUsuarioPruebas: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irNuevaInsertarPrueba");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward guardarItinerarioUsuarioPruebas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		OCAPUsuariosOT usuOT = new OCAPUsuariosOT();
		try {
			OCAPConfigApp.logger.info("Inicio");
			validarAccion(mapping, form, request, response);

			usuOT.setCProfesionalId(Integer.valueOf(((OCAPNuevaSolicitudForm) form).getCProfesional_id()));
			usuOT.setDProfesional_nombre(((OCAPNuevaSolicitudForm) form).getDProfesional_nombre());
			if ((((OCAPNuevaSolicitudForm) form).getCEspec_id() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getCEspec_id())))
				usuOT.setCEspecId(Integer.valueOf(((OCAPNuevaSolicitudForm) form).getCEspec_id()));
			usuOT.setDEspec_nombre(((OCAPNuevaSolicitudForm) form).getDEspec_nombre());
			usuOT.setCItinerarioId(((OCAPNuevaSolicitudForm) form).getCItinerarioId());
			usuOT.setCGrado_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCGrado_id()));
			usuOT.setDGrado_des(((OCAPNuevaSolicitudForm) form).getDGrado_des());
			usuOT.setCExpId(Long.valueOf(0L));
			usuOT.setCUsrId(Long.valueOf(0L));
			usuOT.setNTelefono1(Integer.valueOf(0));
			usuOT.setFFinCA(DateUtil.addDias(new Date(), 2));

			session.setAttribute("UsuarioPruebaItinerario", usuOT);

			OCAPConfigApp.logger.info("Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			if ((e.getMessage() != null) && (e.getMessage().equals("ExisteSolicitudConvocatoria")))
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						new ActionError(TrataError.tratarError(e, "error.existeSolicitudConvocatoria")));
			else {
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						new ActionError(TrataError.tratarError(e, "error.general")));
			}
		}
		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irItinerarioPruebas");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward faseEDC(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " faseEDC: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();

			expedienteCarreraOT.setCExpId(Long.valueOf(((OCAPNuevaSolicitudForm) form).getCExp_id()));
			expedienteCarreraOT.setAModificadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
			expedienteCarreraOT.setDMotivosCambio(((OCAPNuevaSolicitudForm) form).getDMotivosCambio());

			if (Constantes.ESTADO_EXCLUIDO_VALUE.equals(request.getParameter("tipo"))) {
				expedienteCarreraOT.setFFinCa(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
				expedienteCarreraOT.setCEstadoId(10);
				expedienteCarreraOT.setFAceptacionCC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
				expedienteCarreraOT.setFRespuestaInconf_MC(new Date());
				expedienteCarreraOT.setBExclusion(Constantes.SI);
			} else {
				expedienteCarreraOT
						.setFInicioCa(DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFInicio_ca()));
				expedienteCarreraOT
						.setFFinCa(DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFFin_ca()));
				expedienteCarreraOT.setFAceptacionCC(new Date());
				expedienteCarreraOT.setFRespuestaInconf_MC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
				expedienteCarreraOT.setCEstadoId(9);
				expedienteCarreraOT.setBExclusion(Constantes.NO);
			}

			expedienteCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);

			request.setAttribute("rutaVuelta",
					"OCAPNuevaSolicitud.do?accion=buscarAdmGrs&recuperarBusquedaAnterior=Y&opcion="
							+ request.getParameter("opcion"));

			OCAPConfigApp.logger.info(getClass().getName() + " modificarFinMC: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty()))) {
			return mapping.findForward("mensajeExito");
		}
		if ((errors != null) && (!errors.isEmpty())) {
			saveErrors(request, errors);

			return mapping.findForward("fallo");
		}
		saveMessages(request, messages);

		return mapping.findForward("irNuevaInsertar");
	}

	public ActionForward asignarEvaluador(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int result = 0;
		String fw = "";
		String pathBase = "";
		String destino = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " asignarEvaluador: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			if (((OCAPNuevaSolicitudForm) form).getCCompfqs_id() == 0L) {
				messages.add("dEvaluador", new ActionMessage("errors.required", "Evaluador"));
			} else {
				OCAPExpedienteCarreraLN expedienteLN = new OCAPExpedienteCarreraLN(jcylUsuario);
				OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
				OCAPEvaluadoresLN evaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
				OCAPExpedientecarreraOT expedienteOT = new OCAPExpedientecarreraOT();
				OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();
				OCAPComponentesfqsOT componentesOT = null;

				expedienteOT.setCCompfqs_id(((OCAPNuevaSolicitudForm) form).getCCompfqs_id());
				expedienteOT.setFRegistroOficial_Ca(
						DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFRegistro_oficial()));
				expedienteOT.setCExpId(Long.valueOf(((OCAPNuevaSolicitudForm) form).getCExp_id()));
				expedienteOT.setAModificadoPor(
						((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());

				result = expedienteLN.modificacionOCAPExpedientecarrera(expedienteOT, false);
				componentesOT = evaluadoresLN.buscarOCAPEvaluadores(expedienteOT.getCCompfqs_id());

				solicitudesOT.setDCorreoelectronico(componentesOT.getACorreoelectronico());
				solicitudesOT.setDNombre(componentesOT.getDNombre());
				solicitudesOT.setDApellido1(componentesOT.getDApellidos());
				solicitudesOT.setBSexo(componentesOT.getBSexo());
				solicitudesOT.setFRegistro_oficial(((OCAPNuevaSolicitudForm) form).getFRegistro_oficial());
				destino = Constantes.OCAP_EVAL;

				if ((solicitudesOT.getDCorreoelectronico() != null)
						&& (!solicitudesOT.getDCorreoelectronico().equals(""))) {
					solicitudesLN.crearInformeAutoevaluacionCa(response, solicitudesOT, pathBase, destino);
				}

				request.setAttribute("rutaVuelta",
						"OCAPSolicitudes.do?accion=listarFase&recuperarBusquedaAnterior=Y&fase="
								+ request.getParameter("fase"));
				fw = "mensajeExito";
			}

			OCAPConfigApp.logger.info(getClass().getName() + " asignarEvaluador: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
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

	public ActionForward asignarSegundoEvaluador(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		long result = 0L;
		String fw = "";
		String pathBase = "";
		String destino = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " asignarSegundoEvaluador: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			if (((OCAPNuevaSolicitudForm) form).getCCompfqs_id() == 0L) {
				messages.add("dEvaluador", new ActionMessage("errors.required", "Evaluador"));
			} else {
				OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
				OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();
				OCAPComponentesfqsOT componentesOT = null;
				OCAPRevisionesLN revisionesLN = new OCAPRevisionesLN(jcylUsuario);
				OCAPRevisionesOT revisionOT = null;
				OCAPEvaluadoresLN evaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);

				revisionOT = revisionesLN
						.buscarOCAPRevisiones(Long.valueOf(((OCAPNuevaSolicitudForm) form).getCExp_id()).longValue());

				if ((revisionOT != null)
						&& (revisionOT.getCCompFqsId() != ((OCAPNuevaSolicitudForm) form).getCCompfqs_id())) {
					revisionOT.setCCompFqsId(((OCAPNuevaSolicitudForm) form).getCCompfqs_id());
					revisionOT.setCExpId(Long.valueOf(((OCAPNuevaSolicitudForm) form).getCExp_id()));
					revisionOT.setAModificadoPor(((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario"))
							.getUsuario().getC_usr_id());

					revisionesLN.bajaOCAPRevisiones(revisionOT);

					result = revisionesLN.altaOCAPRevisiones(revisionOT);
				}

				componentesOT = evaluadoresLN.buscarOCAPEvaluadores(revisionOT.getCCompFqsId());

				solicitudesOT.setCExp_id(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCExp_id()));
				solicitudesOT.setDCorreoelectronico(componentesOT.getACorreoelectronico());
				solicitudesOT.setDNombre(componentesOT.getDNombre());
				solicitudesOT.setDApellido1(componentesOT.getDApellidos());
				solicitudesOT.setBSexo(componentesOT.getBSexo());
				destino = Constantes.OCAP_EVAL;

				if ((solicitudesOT.getDCorreoelectronico() != null)
						&& (!solicitudesOT.getDCorreoelectronico().equals(""))) {
					solicitudesLN.crearInformeAutoevaluacionCa(response, solicitudesOT, pathBase, destino);
				}

				request.setAttribute("rutaVuelta",
						"OCAPSolicitudes.do?accion=listarFase&recuperarBusquedaAnterior=Y&fase="
								+ request.getParameter("fase"));
				fw = "mensajeExito";
			}

			OCAPConfigApp.logger.info(getClass().getName() + " asignarSegundoEvaluador: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
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

	public ActionForward modificarFechasExpediente(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " modificarFechasExpediente: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			OCAPEstadosCuestionarioLN estadosCuestionariosLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
			OCAPConvocatoriasLN convocatoriaLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT convocatoriaOT = null;
			OCAPItinerariosLN itiLN = new OCAPItinerariosLN(jcylUsuario);
			OCAPExpedientecarreraOT expedienteCarreraOT = new OCAPExpedientecarreraOT();
			boolean flagExcluidaAceptada = false;
			expedienteCarreraOT.setCExpId(Long.valueOf(((OCAPNuevaSolicitudForm) form).getCExp_id()));

			 OCAPExpedientecarreraOT expedienteOriginal = expedienteCarreraLN.buscarOCAPExpedienteCarrera(expedienteCarreraOT.getCExpId());
			
			if ((((OCAPNuevaSolicitudForm) form).getFInicio_mc() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getFInicio_mc())))
				expedienteCarreraOT
						.setFInicioMc(DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFInicio_mc()));
			else {
				expedienteCarreraOT.setFInicioMc(null);
			}
			if ((((OCAPNuevaSolicitudForm) form).getFFin_mc() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getFFin_mc())))
				expedienteCarreraOT
						.setFFinMc(DateUtil.addDias(DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFFin_mc()),1));
			else {
				expedienteCarreraOT.setFFinMc(null);
			}
			if ((((OCAPNuevaSolicitudForm) form).getMeritosBloqueados() != null &&
					!((OCAPNuevaSolicitudForm) form).getMeritosBloqueados().isEmpty() && 
					((OCAPNuevaSolicitudForm) form).getMeritosBloqueados().toUpperCase().equals("S")))
				expedienteCarreraOT.setMeritosBloqueados(Constantes.SI_ESP);
			else {
				expedienteCarreraOT.setMeritosBloqueados(Constantes.NO);
			}
			if (((OCAPNuevaSolicitudForm) form).isEliminarEvaluacion())
				expedienteCarreraOT.setEliminarEvaluacion(true);
			else {
				expedienteCarreraOT.setEliminarEvaluacion(false);
			}
			if ((((OCAPNuevaSolicitudForm) form).getFInicio_eval_mc() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getFInicio_eval_mc()))){
				expedienteCarreraOT.setFInicioEvalMc(
						DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFInicio_eval_mc()));
				expedienteCarreraOT.setFFinEvalMc(calcularFechaFin(expedienteCarreraOT.getFInicioEvalMc(), jcylUsuario, form, TIPO_MC));
			}else {
				expedienteCarreraOT.setFInicioEvalMc(null);
				expedienteCarreraOT.setFFinEvalMc(null);
			}
			if ((((OCAPNuevaSolicitudForm) form).getFNegacion_mc() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getFNegacion_mc())))
				expedienteCarreraOT.setFNegacionMC(
						DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFNegacion_mc()));
			else {
				expedienteCarreraOT.setFNegacionMC(null);
			}
			if ((((OCAPNuevaSolicitudForm) form).getFAceptacionceis() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getFAceptacionceis())))
				expedienteCarreraOT.setFAceptacionceis(
						DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFAceptacionceis()));
			else {
				expedienteCarreraOT.setFAceptacionceis(null);
			}

			if ((((OCAPNuevaSolicitudForm) form).getFInicio_ca() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getFInicio_ca())))
				expedienteCarreraOT
						.setFInicioCa(DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFInicio_ca()));
			else {
				expedienteCarreraOT.setFInicioCa(null);
			}
			if ((((OCAPNuevaSolicitudForm) form).getFFin_ca() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getFFin_ca())))
				expedienteCarreraOT
						.setFFinCa(DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFFin_ca()));
			else {
				expedienteCarreraOT.setFFinCa(null);
			}

			if ((((OCAPNuevaSolicitudForm) form).getFInformeEval() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getFInformeEval())))
				expedienteCarreraOT.setFInformeEval(
						DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFInformeEval()));
			else {
				expedienteCarreraOT.setFInformeEval(DateUtil.convertStringToDate(""));
			}
			if ((((OCAPNuevaSolicitudForm) form).getFIniciocc() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getFIniciocc()))){
				expedienteCarreraOT.setFInicioCc(
						DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFIniciocc()));
				expedienteCarreraOT.setFFinCc(calcularFechaFin(expedienteCarreraOT.getFInicioCc(), jcylUsuario, form, TIPO_CC));
			}else {
				expedienteCarreraOT.setFInicioCc(DateUtil.convertStringToDate(""));
				expedienteCarreraOT.setFFinCc(DateUtil.convertStringToDate(""));
			}
			if ((((OCAPNuevaSolicitudForm) form).getCEstadoFiltro() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getCEstadoFiltro()))) {
				expedienteCarreraOT.setCEstadoId(Long.parseLong(((OCAPNuevaSolicitudForm) form).getCEstadoFiltro()));
			}
			expedienteCarreraOT.setAModificadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
			
			//OCAP-1342
			//Si se selecciona el estado "No aporta Meritos curriculares" o el estado
			//"No aporta Meritos asistenciales" tenemos que grabar el usuario y la fecha
			if (expedienteCarreraOT.getCEstadoId() == Constantes.ESTADO_NO_APORTA_MC) {
				expedienteCarreraOT.setUsrNoMeritosCurriculares(((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
			}else if(expedienteCarreraOT.getCEstadoId() == Constantes.ESTADO_NO_APORTA_MA) {
				expedienteCarreraOT.setUsrNoMeritosAsistenciales(((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
			}
			
			//OCAP-1373
			if (Utilidades.notNullAndNotEmpty(((OCAPNuevaSolicitudForm) form).getFAceptacionCC()))
				expedienteCarreraOT
						.setFAceptacionCC(DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFAceptacionCC()));
			else {
				expedienteCarreraOT.setFAceptacionCC(null);
			}
			
			if (Utilidades.notNullAndNotEmpty(((OCAPNuevaSolicitudForm) form).getFInconf_mc()))
				expedienteCarreraOT
						.setFInconfMC(DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFInconf_mc()));
			else {
				expedienteCarreraOT.setFInconfMC(null);
			}
			
			if (Utilidades.notNullAndNotEmpty(((OCAPNuevaSolicitudForm) form).getFRespuesta_inconf_mc()))
				expedienteCarreraOT
						.setFRespuestaInconf_MC(DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFRespuesta_inconf_mc()));
			else {
				expedienteCarreraOT.setFRespuestaInconf_MC(null);
			}
			
			if ((((OCAPNuevaSolicitudForm) form).getFRenuncia() != null)
					&& (!"".equals(((OCAPNuevaSolicitudForm) form).getFRenuncia())))
				expedienteCarreraOT.setFRenuncia(
						DateUtil.convertStringToDate(((OCAPNuevaSolicitudForm) form).getFRenuncia()));
			else {
				expedienteCarreraOT.setFRenuncia(null);
			}
			
			if(((OCAPNuevaSolicitudForm) form).getcItineraio_id() !=null 
					&& !"".equals(((OCAPNuevaSolicitudForm) form).getcItineraio_id()))
			{
				expedienteCarreraOT.setcItinerario_id(((OCAPNuevaSolicitudForm) form).getcItineraio_id());
				OCAPItinerariosOT it =  itiLN.buscarOCAPItinerarios(new Long(((OCAPNuevaSolicitudForm) form).getcItineraio_id()));
				if (null==it || (null!=it && null== it.getDNombre())) {
					 messages.add("Itinerario", new ActionMessage("itinerario.noExiste", "Itinerario"));
					 if ((messages != null) && (!messages.isEmpty())) {
					       saveMessages(request, messages);
					 
					       request.setAttribute("rutaVuelta","detalleSolicitudGrs&CExp_id="+((OCAPNuevaSolicitudForm) form).getCExp_id()+"&opcion=fechas&menu=modificarFechas");
					       return mapping.findForward("fallo");
					     }
				}
			}else
				expedienteCarreraOT.setcItinerario_id(null);
			expedienteCarreraOT.setDesbloqueoAsistenciales(((OCAPNuevaSolicitudForm) form).isDesbloqueoAsistenciales());
			expedienteCarreraOT.setCConvocatoriaId(new Long(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId()));
			convocatoriaOT = convocatoriaLN.buscarOCAPConvocatorias(expedienteCarreraOT.getCConvocatoriaId());
			
			if(fechaConvocatoriaPasada(expedienteCarreraOT, convocatoriaOT)) {
    			//Mantenemos la fecha fin_ca del expediente
				expedienteCarreraOT.setfFin_ca_convocatoria(expedienteCarreraOT.getFFinCa());
    		}else {
    			//OCAP-1194
    			if(convocatoriaOT.getFFinCA() != null) {
		        	 Calendar c = Calendar.getInstance();
		        	 c.setTime(DateUtil.convertStringToDate(convocatoriaOT.getFFinCA())); 
		        	 c.add(Calendar.DATE, 1);
		        	 expedienteCarreraOT.setfFin_ca_convocatoria(c.getTime());
    			}
	        	 
    		}

			
			//OCAP-1530
			if (expedienteOriginal != null 
					&& expedienteOriginal.getCEstadoId() == Constantes.ESTADO_EXCLUIDA
					&& expedienteCarreraOT.getCEstadoId() == Constantes.ESTADO_ACEPTADA) {
				expedienteCarreraOT.setFAceptacSolic(new Date());
				expedienteCarreraOT.setFNegacionSolic(null);
				expedienteCarreraOT.setFInicioCc(null);
				expedienteCarreraOT.setFFinCc(null);
				flagExcluidaAceptada = true;
			}
			expedienteCarreraOT.setDniReal(((OCAPNuevaSolicitudForm) form).getCDniReal());
			expedienteCarreraLN.modificacionFechaOCAPExpedientecarrera(expedienteCarreraOT, flagExcluidaAceptada);
			if(expedienteCarreraOT.isDesbloqueoAsistenciales()) {
				expedienteCarreraLN.modificacionFechaMA(expedienteCarreraOT);
				estadosCuestionariosLN.actualizarDesbloqueoF3(expedienteCarreraOT.getCExpId());
			}

			request.setAttribute("rutaVuelta",
					"OCAPNuevaSolicitud.do?accion=buscarAdmGrs&recuperarBusquedaAnterior=Y&opcion=fechasExpediente");

			OCAPConfigApp.logger.info(getClass().getName() + " modificarFinMC: Fin");
		} catch (Exception e) {
			if (e.getMessage().startsWith("ORA-")) {
		         messages.add("Solicitud", new ActionMessage("Error", "Solicitud"));
		     }else {
				OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		    }
		}

		 if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
	     {
	       return mapping.findForward("mensajeExito");
	     }
		 if ((errors != null) && (!errors.isEmpty())) {
	       saveErrors(request, errors);
	 
	       return mapping.findForward("fallo");
	     }
	    saveMessages(request, messages);
		return mapping.findForward("fallo");
	}

	private Date calcularFechaFin(Date fInicio, JCYLUsuario jcylUsuario, ActionForm form, String tipoFechaFin) {
		if(fInicio != null){
			try {
				OCAPConvocatoriasLN convocatoriaLN = new OCAPConvocatoriasLN(jcylUsuario);
				OCAPConvocatoriasOT convocatoriaOT = convocatoriaLN.buscarOCAPConvocatorias(obtenerIdConvocatoria(form));
				if(tipoFechaFin.equals(TIPO_CC)){
					return calculoFechaFinCC(convocatoriaOT,fInicio);
				}else if(tipoFechaFin.equals(TIPO_MC)){
					return calculoFechaFinMC(convocatoriaOT,fInicio);
				}else{
					return null;
				}
			} catch (Exception e) {
				OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
				return null;
			}
			
		}else{
			return null;
		}
	}


	private Date calculoFechaFinMC(OCAPConvocatoriasOT convocatoriaOT, Date fInicioMc) {
		if(convocatoriaOT.getNDiasValMc() >= 0){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(fInicioMc);
			c.add(Calendar.DATE, (int)convocatoriaOT.getNDiasValMc());
			return c.getTime();
		}else{
			return null;
		}
	}

	private Date calculoFechaFinCC(OCAPConvocatoriasOT convocatoriaOT, Date fInicioCc) {
		  if(convocatoriaOT.getNDiasValCc() >= 0){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Calendar c = Calendar.getInstance();
				c.setTime(fInicioCc);
				c.add(Calendar.DATE, (int)convocatoriaOT.getNDiasValCc());
				return c.getTime();
			}else{
				return null;
			}
	}

	private long obtenerIdConvocatoria(ActionForm form) {
		  if(form != null){
			  try{
				  return Long.parseLong(((OCAPNuevaSolicitudForm) form).getCConvocatoriaId());
			  }catch(Exception e){
				  return 0;
			  }
		  }		  
		  return 0;
	}
	
	public ActionForward cargarLocalidadesAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Type", "application/json;charset=ISO-8859-1");
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoLocalidades = null;
		String textReturn = "";
		
		try {
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			String provinciaId = request.getParameter("val");
			
			if (provinciaId == null || provinciaId.equals("")) {
				provinciaId = request.getParameter("codProvincia");
			}

			if ((provinciaId == null) || (provinciaId.equals(""))) {
				textReturn = getExceptionCategoria(provinciaId);
			} else {
				OCAPLocalidadesLN localidadesLN = new OCAPLocalidadesLN(jcylUsuario);
				listadoLocalidades = localidadesLN.listarLocalidadesProvincia(provinciaId);
				session.setAttribute(Constantes.COMBO_LOCALIDADES, listadoLocalidades);
				textReturn = generarLocalidadesAJAX(listadoLocalidades, provinciaId);
			}

			PrintWriter out = response.getWriter();
			out.print(textReturn);
			out.flush();
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		return null;
	}

	private String generarLocalidadesAJAX(ArrayList<OCAPLocalidadesOT> listadoLocalidades, String provinciaId) {
		String respuesta = "";
		respuesta += "<option value=''>Seleccione...</option>";
		for (OCAPLocalidadesOT loc : listadoLocalidades) {
			respuesta += "<option value='"+loc.getCLocalidadId()+"'>"+loc.getDLocalidad()+"</option>";
		}
		return respuesta;
	}

	public boolean isReasociarGrs() {
		return reasociarGrs;
	}

	public void setReasociarGrs(boolean reasociarGrs) {
		this.reasociarGrs = reasociarGrs;
	}
	
	public ActionForward cargarGradoConvocatoria(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Type", "application/json;charset=ISO-8859-1");
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoGrados = new ArrayList();
		String textReturn = "";
		try {
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			String convocatoriaId = request.getParameter("val");

			if (Utilidades.isNullOrIsEmpty(convocatoriaId) || !StringUtils.isNumeric(convocatoriaId)) {
				textReturn = getExceptionGerencias(convocatoriaId);
			} else {
				OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
				listadoGrados = convocatoriasLN.consultaGradoDeConvocatoria(Integer.parseInt(convocatoriaId));

				if(listadoGrados == null || listadoGrados.isEmpty()) {
					//Si entramos aquí es porque la convocatoria no tiene asociado ningun grado concreto.
					//En este caso, metemos todos los grados posibles para que el usuario seleccione
					OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
					listadoGrados = gradoLN.listarGrados();
				}
				
				session.setAttribute(Constantes.COMBO_GRADOS_ALTA, listadoGrados);

				textReturn = getCascadeGrados(listadoGrados, convocatoriaId);
			}

			PrintWriter out = response.getWriter();
			out.println(textReturn);
			out.flush();
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		return null;
	}

	private String getCascadeGrados(ArrayList listadoGrados, String convocatoriaId) {
		StringBuffer textReturn = new StringBuffer();
		if ((listadoGrados != null) && (listadoGrados.size() > 0)) {
			OCAPGradoOT gradoOT = null;
			textReturn.append("[");
			if(listadoGrados.size() != 1) {
				textReturn.append("{'When':'" + convocatoriaId + "','Value':'','Text':'Seleccione...'},");
			}

			for (int i = 0; i < listadoGrados.size(); i++) {
				gradoOT = (OCAPGradoOT) listadoGrados.get(i);
				textReturn.append("{'When':'").append(convocatoriaId).append("','Value':'")
						.append(gradoOT.getCGradoId()).append("','Text':'")
						.append(formatCadena(gradoOT.getDDescripcion())).append("'},");
			}
			textReturn.append("]");
		}

		return textReturn.toString();
	}
	
	private boolean fechaConvocatoriaPasada(OCAPExpedientecarreraOT expedienteOT, OCAPConvocatoriasOT convocatoriaOT) {
		try {
			if (Utilidades.notNullAndNotEmpty(convocatoriaOT.getFFinCA())) {
				Date fechaFinCA = DateUtil.convertStringToDate(convocatoriaOT.getFFinCA());
				// Miramos que fecha actual sea posterior a F_FIN_CA
				if (Utilidades.eliminarHoras(new Date()).compareTo(fechaFinCA) > 0) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			return false;
		}
	}
	
}
