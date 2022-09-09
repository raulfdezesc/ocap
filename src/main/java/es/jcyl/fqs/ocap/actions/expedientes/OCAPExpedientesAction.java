package es.jcyl.fqs.ocap.actions.expedientes;

import es.jcyl.fqs.ocap.actionforms.expedientes.OCAPExpedientesForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
import es.jcyl.fqs.ocap.ln.estados.OCAPEstadosLN;
import es.jcyl.fqs.ocap.ln.expedienteCarrera.OCAPExpedienteCarreraLN;
import es.jcyl.fqs.ocap.ln.expedientes.OCAPExpedientesLN;
import es.jcyl.fqs.ocap.ln.expedientes.OCAPReportExpedientesLN;
import es.jcyl.fqs.ocap.ln.gerencias.OCAPGerenciasLN;
import es.jcyl.fqs.ocap.ln.grado.OCAPGradoLN;
import es.jcyl.fqs.ocap.ot.estados.OCAPEstadosOT;
import es.jcyl.fqs.ocap.ot.expedientes.OCAPExpedientesOT;
import es.jcyl.fqs.ocap.ot.expedientes.OCAPReportExpedientesOT;
import es.jcyl.fqs.ocap.ot.gerencias.OCAPGerenciasOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.TrataError;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.fqs.ocap.util.reports.Report;
import es.jcyl.fqs.ocap.util.reports.ot.ReportOT;
import es.jcyl.framework.JCYLUsuario;
import java.util.ArrayList;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OCAPExpedientesAction extends OCAPGenericAction {
	public ActionForward irBuscadorExpedientes(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession sesion = request.getSession();
		ActionErrors errores = new ActionErrors();
		try {
			JCYLUsuario jcylUsuario = (JCYLUsuario) sesion.getAttribute("JCYLUsuario");

			sesion.removeAttribute(Constantes.COMBO_ESTADOS);

			if (mapping.getAttribute() != null) {
				if ("request".equals(mapping.getScope()))
					request.removeAttribute(mapping.getAttribute());
				else {
					sesion.removeAttribute(mapping.getAttribute());
				}
			}

			OCAPExpedientesForm formulario = new OCAPExpedientesForm();
			sesion.setAttribute("OCAPExpedientesForm", formulario);

			OCAPConvocatoriasLN convoLN = new OCAPConvocatoriasLN(jcylUsuario);
			ArrayList listaConvocatorias = convoLN.listarConvocatorias();
			sesion.setAttribute(Constantes.COMBO_CONVOCATORIAS, listaConvocatorias);

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			ArrayList listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();
			if (sesion.getAttribute(Constantes.COMBO_CATEGORIA) != null)
				sesion.removeAttribute(Constantes.COMBO_CATEGORIA);
			sesion.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);

			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			ArrayList listadoGrados = gradoLN.listarGrados();
			if (sesion.getAttribute(Constantes.COMBO_GRADOS_CONSULTA) != null)
				sesion.removeAttribute(Constantes.COMBO_GRADOS_CONSULTA);
			sesion.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, listadoGrados);

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);

			if ((Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
					|| (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol()))) {
				ArrayList listadoGerencias = gerenciasLN.listadoOCAPGerencias();
				if (sesion.getAttribute(Constantes.COMBO_GERENCIA) != null)
					sesion.removeAttribute(Constantes.COMBO_GERENCIA);
				sesion.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);
				


				String modo = request.getParameter("modo");

				if ("infMotivado".equals(request.getParameter("modo"))) {
					if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){
						formulario.setCEstadoId(14);
					} else {
						formulario.setCEstadoId(10);
					}
					OCAPEstadosLN estadosLN = new OCAPEstadosLN(jcylUsuario);
					OCAPEstadosOT estadoOT = null;
					ArrayList listadoEstados = new ArrayList();
					estadoOT = estadosLN.buscarEstadosCompleto(10);
					if (estadoOT != null) {
						listadoEstados.add(estadoOT);
					}
					estadoOT = estadosLN.buscarEstadosCompleto(9);
					if (estadoOT != null) {
						listadoEstados.add(estadoOT);
					}
					sesion.setAttribute(Constantes.COMBO_ESTADOS, listadoEstados);
				}
			} else {
				sesion.removeAttribute(Constantes.COMBO_GERENCIA);
				gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
				OCAPGerenciasOT gerenciasOT = gerenciasLN.buscaOCAPGerenciaLDAP(jcylUsuario.getUser().getD_gerencia());
				formulario.setCGerenciaId(gerenciasOT.getCGerenciaId());
				formulario.setDGerencia(gerenciasOT.getDNombre());
				if ("infMotivado".equals(request.getParameter("modo")))
					formulario.setCEstadoId(14);
				formulario.setBMCUsuario(Constantes.SI);
			}

			if ("etiqueta".equals(request.getParameter("modo"))) {
				OCAPEstadosLN estadosLN = new OCAPEstadosLN(jcylUsuario);
				ArrayList listadoEstados = estadosLN.listarEstados();
				sesion.setAttribute(Constantes.COMBO_ESTADOS, listadoEstados);
			}

			if (sesion.getAttribute("listadoExpedientes") != null) {
				sesion.removeAttribute("listadoExpedientes");
			}
			
			if ("infMotivado".equals(request.getParameter("modo"))) {
				//Incidencia Carrera Profesional OCAP-78
				if((Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol()))){
					sesion.removeAttribute(Constantes.COMBO_ESTADOS);
				}				
			}
			
			
			sesion.setAttribute("modo", request.getParameter("modo"));
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errores.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		}

		if (errores.size() != 0) {
			saveErrors(request, errores);

			return mapping.findForward("fallo");
		}

		return mapping.findForward("irBuscadorExpedientes");
	}

	public ActionForward listarExpedientes(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession sesion = request.getSession();
		ActionErrors errores = new ActionErrors();
		try {
			JCYLUsuario jcylUsuario = (JCYLUsuario) sesion.getAttribute("JCYLUsuario");

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPExpedientesForm) request.getSession().getAttribute("OCAPExpedientesFormBuscador");
				request.setAttribute("OCAPExpedientesForm", form);
			} else {
				request.getSession().setAttribute("OCAPExpedientesFormBuscador", form);
			}

			if (request.getAttribute("erroresAction") != null) {
				saveErrors(request, (ActionErrors) request.getAttribute("erroresAction"));
				return mapping.findForward("irBuscadorExpedientes");
			}

			validarAccion(mapping, form, request, response);

			((OCAPExpedientesForm) form).setListaExpedientes(new long[0]);

			OCAPExpedientesLN expedientesLN = new OCAPExpedientesLN(jcylUsuario);
			OCAPExpedientesOT busqueda = new OCAPExpedientesOT();
			busqueda.setDNombre(((OCAPExpedientesForm) form).getDNombre());
			busqueda.setDApellidos(((OCAPExpedientesForm) form).getDApellidos());
			busqueda.setCDNIReal(((OCAPExpedientesForm) form).getCDNIReal());
			busqueda.setCGerenciaId(((OCAPExpedientesForm) form).getCGerenciaId());
			busqueda.setCGradoId(((OCAPExpedientesForm) form).getCGradoId());
			busqueda.setCCategProfesionalId(((OCAPExpedientesForm) form).getCCategProfesionalId());
			busqueda.setCConvocatoriaId(((OCAPExpedientesForm) form).getCConvocatoriaId());
			busqueda.setCEstadoId(((OCAPExpedientesForm) form).getCEstadoId());

			if ((((OCAPExpedientesForm) form).getCodigoId() != null)
					&& (!"".equals(((OCAPExpedientesForm) form).getCodigoId()))) {
				String codigoEvaluado = ((OCAPExpedientesForm) form).getCodigoId().substring("EPR".length());
				busqueda.setCExpedienteId(Long.parseLong(codigoEvaluado));
			}

			busqueda.setPrimerRegistro(1);
			busqueda.setRegistrosPorPagina(-1);

			busqueda.setCModo(sesion.getAttribute("modo") == null ? "" : sesion.getAttribute("modo").toString());
			if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()))
				busqueda.setEsCEIS(Constantes.SI);
			else {
				busqueda.setEsCEIS(Constantes.NO);
			}
			if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol()))
				busqueda.setEsGRS(Constantes.SI);
			else {
				busqueda.setEsGRS(Constantes.NO);
			}

			ArrayList listadoExpedientes = expedientesLN.listarExpedientes(busqueda);
			int lala = expedientesLN.obtenerTotalExpedientes(busqueda);

			if (sesion.getAttribute("listadoExpedientes") != null)
				sesion.removeAttribute("listadoExpedientes");
			sesion.setAttribute("listadoExpedientes", listadoExpedientes);
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errores.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		}

		if (errores.size() != 0) {
			saveErrors(request, errores);

			return mapping.findForward("fallo");
		}

		return mapping.findForward("listarExpedientes");
	}

	public ActionForward generarReportPDFExpedientesMeritosCurriculares(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession sesion = request.getSession();
		ActionErrors errores = new ActionErrors();
		try {
			JCYLUsuario jcylUsuario = (JCYLUsuario) sesion.getAttribute("JCYLUsuario");

			if (request.getAttribute("erroresAction") != null) {
				saveErrors(request, (ActionErrors) request.getAttribute("erroresAction"));
				return mapping.findForward("listarExpedientes");
			}

			String rutaRaiz = this.servlet.getServletConfig().getServletContext().getRealPath("");

			OCAPReportExpedientesLN reportExpedientesLN = new OCAPReportExpedientesLN(jcylUsuario);
			OCAPReportExpedientesOT datosBusqueda = new OCAPReportExpedientesOT();
			datosBusqueda.setListaExpedientesBusqueda(((OCAPExpedientesForm) form).getListaExpedientes());
			datosBusqueda.setBMCUsuario(((OCAPExpedientesForm) form).getBMCUsuario());
			datosBusqueda.setDOrigenReport(Constantes.ORIGEN_EXPEDIENTE);
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

	public ActionForward generarReportPDFExpedientesEtiquetas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession sesion = request.getSession();
		ActionErrors errores = new ActionErrors();
		try {
			JCYLUsuario jcylUsuario = (JCYLUsuario) sesion.getAttribute("JCYLUsuario");

			if (request.getAttribute("erroresAction") != null) {
				saveErrors(request, (ActionErrors) request.getAttribute("erroresAction"));
				return mapping.findForward("listarExpedientes");
			}

			String rutaRaiz = this.servlet.getServletConfig().getServletContext().getRealPath("");

			OCAPReportExpedientesLN reportExpedientesLN = new OCAPReportExpedientesLN(jcylUsuario);
			OCAPReportExpedientesOT datosBusqueda = new OCAPReportExpedientesOT();
			datosBusqueda.setListaExpedientesBusqueda(((OCAPExpedientesForm) form).getListaExpedientes());

			Utilidades.escribirMemoriaLog(OCAPConfigApp.logger);

			ReportOT reportOT = reportExpedientesLN.crearReportExpedientesEtiquetas(datosBusqueda, rutaRaiz, 2);

			Utilidades.escribirMemoriaLog(OCAPConfigApp.logger);

			ServletOutputStream out = response.getOutputStream();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=\"etiquetas.pdf\"");

			Report.reportToPDF(out, reportOT);
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

	public ActionForward generarReportPDFItinerariosEtiquetas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession sesion = request.getSession();
		ActionErrors errores = new ActionErrors();
		try {
			JCYLUsuario jcylUsuario = (JCYLUsuario) sesion.getAttribute("JCYLUsuario");

			if (request.getAttribute("erroresAction") != null) {
				saveErrors(request, (ActionErrors) request.getAttribute("erroresAction"));
				return mapping.findForward("listarExpedientes");
			}

			String rutaRaiz = this.servlet.getServletConfig().getServletContext().getRealPath("");

			OCAPReportExpedientesLN reportExpedientesLN = new OCAPReportExpedientesLN(jcylUsuario);
			OCAPReportExpedientesOT datosBusqueda = new OCAPReportExpedientesOT();
			datosBusqueda.setListaExpedientesBusqueda(((OCAPExpedientesForm) form).getListaExpedientes());

			Utilidades.escribirMemoriaLog(OCAPConfigApp.logger);

			ArrayList reports = reportExpedientesLN.crearReportItinerariosEtiquetas(datosBusqueda, rutaRaiz);

			Utilidades.escribirMemoriaLog(OCAPConfigApp.logger);

			ServletOutputStream out = response.getOutputStream();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=\"etiquetas.pdf\"");

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

	public ActionForward modificarEstadoAceptacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionErrors errors = new ActionErrors();

		HttpSession session = request.getSession();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " modificarEstadoAceptacion: Inicio");	

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			String usuId = jcylUsuario.getUser().getC_usr_id();
			OCAPConfigApp.logger.info(getClass().getName() + " modificarEstadoAceptacion: UsuarioId: " + usuId);

			String estadoString = request.getParameter("estado");
			long estado = Long.parseLong(estadoString);

			if (estado == 13)
				estado = 9;
			else if (estado == 14) {
				estado = 10;
			}

			OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);

			for (int i = 0; i < ((OCAPExpedientesForm) form).getListaExpedientes().length; i++) {
				expedienteCarreraLN.modificarEstadoExpCarrera(((OCAPExpedientesForm) form).getListaExpedientes()[i],
						estado,false);
			}

			OCAPConfigApp.logger.info(getClass().getName() + " modificarEstadoAceptacion: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			request.setAttribute("rutaVuelta",
					"OCAPExpedientes.do?accion=listarExpedientes&recuperarBusquedaAnterior=Y");

			return mapping.findForward("exito");
		}
		saveErrors(request, errors);

		return mapping.findForward("error");
	}

	public ActionForward generarReportPDFInfMotivado(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession sesion = request.getSession();
		ActionErrors errores = new ActionErrors();
		try {
			JCYLUsuario jcylUsuario = (JCYLUsuario) sesion.getAttribute("JCYLUsuario");

			if (request.getAttribute("erroresAction") != null) {
				saveErrors(request, (ActionErrors) request.getAttribute("erroresAction"));
				return mapping.findForward("listarExpedientes");
			}

			String rutaRaiz = this.servlet.getServletConfig().getServletContext().getRealPath("");

			OCAPReportExpedientesLN reportExpedientesLN = new OCAPReportExpedientesLN(jcylUsuario);
			OCAPReportExpedientesOT datosBusqueda = new OCAPReportExpedientesOT();
			datosBusqueda.setListaExpedientesBusqueda(((OCAPExpedientesForm) form).getListaExpedientes());

			Utilidades.escribirMemoriaLog(OCAPConfigApp.logger);

			ArrayList reports = reportExpedientesLN.crearListadoReportsExpedientesInfMotivado(datosBusqueda,
					((OCAPExpedientesForm) form).getCEstadoId(), rutaRaiz, jcylUsuario);

			Utilidades.escribirMemoriaLog(OCAPConfigApp.logger);

			ServletOutputStream out = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename=\"informesMotivados.pdf\"");
			response.setContentType("application/pdf");

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
}
