package es.jcyl.fqs.ocap.actions.actas;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.jcyl.fqs.ocap.actionforms.actas.OCAPActasForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.actas.OCAPActasLN;
import es.jcyl.fqs.ocap.ln.actas.OCAPMiembrosComitesLN;
import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
import es.jcyl.fqs.ocap.ln.grado.OCAPGradoLN;
import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
import es.jcyl.fqs.ocap.ot.actas.OCAPActasOT;
import es.jcyl.fqs.ocap.ot.actas.OCAPActasTiposOT;
import es.jcyl.fqs.ocap.ot.actas.OCAPMiembrosComitesOT;
import es.jcyl.fqs.ocap.ot.reports.OCAPAsistenteOT;
import es.jcyl.fqs.ocap.ot.reports.OCAPUsuarioOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.Pagina;
import es.jcyl.fqs.ocap.util.TrataError;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLUsuario;

public class OCAPActasAction extends OCAPGenericAction {
	public ActionForward irGenerarActas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoGrados = null;
		ArrayList listadoCategorias = null;
		ArrayList listadoConvocatorias = null;
		try {
			OCAPConfigApp.logger.info(" Inicio ");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			listadoConvocatorias = convocatoriasLN.listarConvocatorias();

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			listadoGrados = gradoLN.listarGrados();

			session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, listadoGrados);
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);
			session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);

			if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())||Constantes.OCAP_CTE.equals(jcylUsuario.getUser().getRol())) {
				Map parametros = jcylUsuario.getParametrosUsuario();
				String paramGerencia = (String) parametros.get(Constantes.PARAM_GERENCIA);
				((OCAPActasForm) form).setCGerenciaId(Long.parseLong(paramGerencia));
			}

			((OCAPActasForm) form).setjspInicio(true);

			session.setAttribute("tipoActa", request.getParameter("tipoActa"));

			((OCAPActasForm) form).setCGradoId(0L);
			((OCAPActasForm) form).setCProfesionalId(0L);
			((OCAPActasForm) form).setCConvocatoriaId(0L);
			((OCAPActasForm) form).setBPresidente(new String[0]);
			((OCAPActasForm) form).setBVocal(new String[0]);
			((OCAPActasForm) form).setBSecretario(new String[0]);
			((OCAPActasForm) form).setBEnCalidadPresi(new String[0]);
			((OCAPActasForm) form).setBEnCalidadVocal(new String[0]);
			((OCAPActasForm) form).setBEnCalidadSecre(new String[0]);
			((OCAPActasForm) form).setDAsuntosTramite("");
			((OCAPActasForm) form).setDMiembrosAusentes("");
			((OCAPActasForm) form).setDRuegosPreguntas("");
			((OCAPActasForm) form).setFInicio("");
			((OCAPActasForm) form).setNHoraInicio("");
			((OCAPActasForm) form).setNMinutosInicio("");
			((OCAPActasForm) form).setNHoraFin("");
			((OCAPActasForm) form).setNMinutosFin("");
			((OCAPActasForm) form).setListaPresidentes(null);
			((OCAPActasForm) form).setListaVocales(null);
			((OCAPActasForm) form).setListaSecretarios(null);
			((OCAPActasForm) form).setListaUsuariosInformeMotivado(null);
			((OCAPActasForm) form).setListaUsuariosAclaraciones(null);
			((OCAPActasForm) form).setBUsuario(new String[0]);
			((OCAPActasForm) form).setBTipoInformeUsuario(new String[0]);

			OCAPConfigApp.logger.info(" Fin ");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " irGenerarActas: ERROR: " + e.getMessage());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irGenerarActas");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}
	
	public ActionForward irConsultarActas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoGrados = null;
		ArrayList listadoCategorias = null;
		ArrayList listadoConvocatorias = null;
		ArrayList listadoTipos = null;
		try {
			OCAPConfigApp.logger.info(" Inicio ");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			listadoConvocatorias = convocatoriasLN.listarConvocatorias();

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			listadoGrados = gradoLN.listarGrados();
			
			listadoTipos = crearListadoActaTipos();

			session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, listadoGrados);
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);
			session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
			session.setAttribute(Constantes.COMBO_TIPO, listadoTipos);

			if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
				Map parametros = jcylUsuario.getParametrosUsuario();
				String paramGerencia = (String) parametros.get(Constantes.PARAM_GERENCIA);
				((OCAPActasForm) form).setCGerenciaId(Long.parseLong(paramGerencia));
			}

			((OCAPActasForm) form).setjspInicio(true);

			session.setAttribute("tipoActa", request.getParameter("tipoActa"));

			((OCAPActasForm) form).setJspInicio(true);
			((OCAPActasForm) form).setCGradoId(0);
			((OCAPActasForm) form).setCConvocatoriaId(0);
			((OCAPActasForm) form).setCProfesionalId(0);
			((OCAPActasForm) form).setCActaTipo(null);

			OCAPConfigApp.logger.info(" Fin ");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " irConsultarActas: ERROR: " + e.getMessage());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irConsultarActas");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}	

	public ActionForward cargarMiembros(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaPresidentes = new ArrayList();
		ArrayList listaVocales = new ArrayList();
		ArrayList listaSecretarios = new ArrayList();
		OCAPMiembrosComitesOT miembroOT = null;
		OCAPMiembrosComitesLN miembrosLN = null;
		OCAPUsuariosLN usuariosLN = null;
		try {
			OCAPConfigApp.logger.info(" Inicio ");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			miembrosLN = new OCAPMiembrosComitesLN(jcylUsuario);

			miembroOT = new OCAPMiembrosComitesOT();
			miembroOT.setCConvocatoria(((OCAPActasForm) form).getCConvocatoriaId());
			miembroOT.setCProfesionalId(((OCAPActasForm) form).getCProfesionalId());
			miembroOT.setCGerenciaId(((OCAPActasForm) form).getCGerenciaId());

			miembroOT.setCTipo("P");
			listaPresidentes = miembrosLN.buscarMiembros(miembroOT);
			((OCAPActasForm) form).setListaPresidentes(listaPresidentes);

			miembroOT.setCTipo(Constantes.VOCAL);
			listaVocales = miembrosLN.buscarMiembros(miembroOT);
			((OCAPActasForm) form).setListaVocales(listaVocales);

			miembroOT.setCTipo(Constantes.SECRETARIO);
			listaSecretarios = miembrosLN.buscarMiembros(miembroOT);
			((OCAPActasForm) form).setListaSecretarios(listaSecretarios);

			if (!"C".equals(session.getAttribute("tipoActa"))) {
				usuariosLN = new OCAPUsuariosLN(jcylUsuario);
				ArrayList usuariosConInformeMotivado = usuariosLN.buscarUsuariosConInformeMotivado(null,
						((OCAPActasForm) form).getCGradoId(), ((OCAPActasForm) form).getCConvocatoriaId(),
						((OCAPActasForm) form).getCGerenciaId(), ((OCAPActasForm) form).getCProfesionalId(),
						jcylUsuario);
				((OCAPActasForm) form).setListaUsuariosInformeMotivado(usuariosConInformeMotivado);
			}

			OCAPConfigApp.logger.info(" Fin ");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarMiembros: ERROR: " + e.getMessage());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irGenerarActas");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward guardarActa(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		String pathBase = null;
		ArrayList listadoMiembros = null;
		OCAPAsistenteOT miembroOT = null;
		ArrayList listadoUsuarios = null;
		long idActa = 0L;
		OCAPActasOT actasOT = null;
		OCAPUsuarioOT usuarioOT = null;
		try {
			OCAPConfigApp.logger.info("Inicio");
			validarAccion(mapping, form, request, response);
			OCAPActasForm actasForm = (OCAPActasForm) form;

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			actasOT = new OCAPActasOT();
			actasOT.setCGradoId(actasForm.getCGradoId());
			actasOT.setCConvocatoriaId(actasForm.getCConvocatoriaId());
			actasOT.setCProfesionalId(actasForm.getCProfesionalId());
			actasOT.setCGerenciaId(actasForm.getCGerenciaId());

			listadoMiembros = new ArrayList();

			for (int i = 0; (actasForm.getBPresidente() != null) && (i < actasForm.getBPresidente().length); i++) {
				miembroOT = null;
				miembroOT = new OCAPAsistenteOT();
				miembroOT.setCMiembroId(Long.parseLong(actasForm.getBPresidente()[i]));
				miembroOT.setBEnCalidad(actasForm.getBEnCalidadPresi()[i]);
				listadoMiembros.add(miembroOT);
			}

			for (int i = 0; (actasForm.getBSecretario() != null) && (i < actasForm.getBSecretario().length); i++) {
				miembroOT = null;
				miembroOT = new OCAPAsistenteOT();
				miembroOT.setCMiembroId(Long.parseLong(actasForm.getBSecretario()[i]));
				miembroOT.setBEnCalidad(actasForm.getBEnCalidadSecre()[i]);
				listadoMiembros.add(miembroOT);
			}

			for (int i = 0; (actasForm.getBVocal() != null) && (i < actasForm.getBVocal().length); i++) {
				miembroOT = null;
				miembroOT = new OCAPAsistenteOT();
				miembroOT.setCMiembroId(Long.parseLong(actasForm.getBVocal()[i]));
				miembroOT.setBEnCalidad(actasForm.getBEnCalidadVocal()[i]);
				listadoMiembros.add(miembroOT);
			}
			actasOT.setListadoMiembros(listadoMiembros);

			actasOT.setDMiembrosAusentes(actasForm.getDMiembrosAusentes());
			actasOT.setDAsuntosTramite(actasForm.getDAsuntosTramite());
			actasOT.setDRuegosPreguntas(actasForm.getDRuegosPreguntas());
			actasOT.setFSesion(DateUtil.convertStringToDate(actasForm.getFInicio()));
			actasOT.setNHoraInicio(actasForm.getNHoraInicio());
			actasOT.setNMinutosInicio(actasForm.getNMinutosInicio());
			actasOT.setNHoraFin(actasForm.getNHoraFin());
			actasOT.setNMinutosFin(actasForm.getNMinutosFin());

			listadoUsuarios = new ArrayList();

			for (int i = 0; (actasForm.getBUsuario() != null) && (i < actasForm.getBUsuario().length); i++) {
				usuarioOT = new OCAPUsuarioOT();
				usuarioOT.setDAccion(actasForm.getBTipoInformeUsuario()[i]);
				usuarioOT.setCExpId(Long.parseLong(actasForm.getBUsuario()[i]));
				listadoUsuarios.add(usuarioOT);
			}

			actasOT.setListadoUsuarios(listadoUsuarios);

			OCAPActasLN actasLN = new OCAPActasLN(jcylUsuario);
			actasOT.setCTipoActa(session.getAttribute("tipoActa").toString());
			actasOT.setACreadoPor(jcylUsuario.getUser().getC_usr_id());
			idActa = actasLN.altaDatosActa(actasOT);

			actasForm.setCActaId(idActa);

			OCAPConfigApp.logger.info("Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irImprimirActa");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward generarPDFActas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		String pathBase = null;
		long idActa = 0L;
		OCAPActasLN actasLN = null;
		OCAPActasOT actaOT = null;
		try {
			OCAPConfigApp.logger.info("Inicio");
			validarAccion(mapping, form, request, response);
			OCAPActasForm actasForm = (OCAPActasForm) form;

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			idActa = actasForm.getCActaId();

			actasLN = new OCAPActasLN(jcylUsuario);
			actaOT = actasLN.buscarDatosActa(idActa);

			pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");

			if ("C".equals(session.getAttribute("tipoActa").toString()))
				actasLN.crearActaConstitucion(jcylUsuario, response, actaOT, pathBase);
			else if ("R".equals(session.getAttribute("tipoActa").toString()))
				actasLN.crearActaReunion(jcylUsuario, response, actaOT, pathBase);
			else if ("A".equals(session.getAttribute("tipoActa").toString())) {
				actasLN.crearActaSoliAclaracion(jcylUsuario, response, actaOT, pathBase);
			}

			OCAPConfigApp.logger.info("Fin");
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
	
	public ActionForward buscarActas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoActas = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarActas: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPActasLN actasLN = new OCAPActasLN(jcylUsuario);
			OCAPActasOT actaOT = new OCAPActasOT();

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPActasForm) request.getSession().getAttribute("OCAPActasFormBuscador");
				request.setAttribute("OCAPActasForm", form);
			} else {
				request.getSession().setAttribute("OCAPActasFormBuscador", form);
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

			((OCAPActasForm) form).setjspInicio(false);
			actaOT = new OCAPActasOT();
			if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
				Map parametros = jcylUsuario.getParametrosUsuario();
				String paramGerencia = (String) parametros.get(Constantes.PARAM_GERENCIA);
				actaOT.setCGerenciaId(Long.parseLong(paramGerencia));
			}

			
			actaOT.setCGradoId(((OCAPActasForm) form).getCGradoId());
			actaOT.setCConvocatoriaId(((OCAPActasForm) form).getCConvocatoriaId());
			actaOT.setCProfesionalId(((OCAPActasForm) form).getCProfesionalId());
			actaOT.setCTipoActa(((OCAPActasForm) form).getCActaTipo());

			
			int numActas = actasLN.contarActas(actaOT);
			listadoActas = actasLN.buscarActas(primerRegistro, registrosPorPagina, actaOT);

			session.setAttribute("numActas", new Integer(numActas));

			Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
			pagina.setElementos(listadoActas);
			pagina.setNRegistros(((Integer) session.getAttribute("numActas")).intValue());
			pagina.setRegistroActual(primerRegistro);
			pagina.setRegistrosPorPagina(registrosPorPagina);

			request.setAttribute("listados", pagina);

			OCAPConfigApp.logger.info(getClass().getName() + " buscarActas: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irConsultarActas");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}	
	
	
	public ActionForward generarPDFActa(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		String pathBase = null;
		long idActa = 0L;
		OCAPActasLN actasLN = null;
		OCAPActasOT actaOT = null;
		try {
			OCAPConfigApp.logger.info("Inicio");
			validarAccion(mapping, form, request, response);
			OCAPActasForm actasForm = (OCAPActasForm) form;

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			idActa = Long.valueOf(request.getParameter("cActaId"));

			actasLN = new OCAPActasLN(jcylUsuario);
			actaOT = actasLN.buscarDatosActa(idActa);

			pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");

			if ("C".equals(actaOT.getCTipoActa())){
				actasLN.crearActaConstitucion(jcylUsuario, response, actaOT, pathBase);
			} else if ("R".equals(actaOT.getCTipoActa())){
				actasLN.crearActaReunion(jcylUsuario, response, actaOT, pathBase);
			} else if ("A".equals(actaOT.getCTipoActa())) {
				actasLN.crearActaSoliAclaracion(jcylUsuario, response, actaOT, pathBase);
			}

			OCAPConfigApp.logger.info("Fin");
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
	
	private ArrayList crearListadoActaTipos(){
		
		ArrayList listadoTipos = new ArrayList();
		OCAPActasTiposOT tipo = new OCAPActasTiposOT();
		tipo.setCActaTipo("A");
		tipo.setDNombre("Aclaraciones");
		listadoTipos.add(tipo);
		tipo = new OCAPActasTiposOT();
		tipo.setCActaTipo("C");
		tipo.setDNombre("Constitución");
		listadoTipos.add(tipo);
		tipo = new OCAPActasTiposOT();
		tipo.setCActaTipo("R");
		tipo.setDNombre("Reunión");
		listadoTipos.add(tipo);		
		
		return listadoTipos;
	}
	
	
	
}

