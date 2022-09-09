package es.jcyl.fqs.ocap.actions.encuestaCalidad;

import es.jcyl.fqs.ocap.actionforms.encuestaCalidad.OCAPEncuestaCalidadForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
import es.jcyl.fqs.ocap.ln.encuestaCalidad.OCAPEncuestaCalidadLN;
import es.jcyl.fqs.ocap.ln.especialidades.OCAPEspecialidadesLN;
import es.jcyl.fqs.ocap.ln.estatutario.OCAPEstatutarioLN;
import es.jcyl.fqs.ocap.ln.evaluadores.OCAPEvaluadoresLN;
import es.jcyl.fqs.ocap.ln.itinerarios.OCAPItinerariosLN;
import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
import es.jcyl.fqs.ocap.ot.encuestaCalidad.OCAPEncuestaCalidadOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.TrataError;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OCAPEncuestaCalidadAction extends OCAPGenericAction {
	OCAPUsuariosOT usuarioOT;
	OCAPUsuariosLN usuariosLN;

	private void $init$() {
		this.usuarioOT = null;
		this.usuariosLN = null;
	}

	public ActionForward irListar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		ArrayList listadoItinerarios = null;
		ArrayList listadoCategorias = new ArrayList();
		HttpSession session = request.getSession();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irListar: Inicio");
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPItinerariosLN oCAPItinerariosLN = new OCAPItinerariosLN(jcylUsuario);
			listadoItinerarios = oCAPItinerariosLN.listadoItinerariosUsados();
			session.setAttribute(Constantes.COMBO_ITINERARIOS, listadoItinerarios);

			OCAPEstatutarioLN estatutLN = new OCAPEstatutarioLN(jcylUsuario);
			listadoCategorias = estatutLN.listadoOCAPEstatutariosUsados();
			session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);

			((OCAPEncuestaCalidadForm) form).setjspInicio(true);
			OCAPConfigApp.logger.info(getClass().getName() + " irListar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " irListar: ERROR: " + e.getMessage());
			request.setAttribute("mensaje", mensajeError);
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irBuscarEncuestaGeneral");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward cargarComboEspecialidades(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoEspecialidades = new ArrayList();
		ArrayList listadoConv = new ArrayList();
		ArrayList listadoAct = new ArrayList();
		ArrayList listadoItinerarios = new ArrayList();
		String sig = "error";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboEspecialidades: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);

			String tipo = request.getParameter("tipo");

			if (((OCAPEncuestaCalidadForm) form).getCProfesional_id() > 0L) {
				listadoEspecialidades = especialidadesLN
						.listarEspecialidades(((OCAPEncuestaCalidadForm) form).getCProfesional_id());
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
			} else {
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
			}
			cargarComboItinerarios(mapping, form, request, response);

			sig = "irBuscarEncuestaGeneral";

			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboEspecialidades: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboEspecialidades: ERROR: " + e.getMessage());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(sig);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward cargarComboItinerarios(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoItinerarios = new ArrayList();
		ArrayList listadoConv = new ArrayList();
		ArrayList listadoAct = new ArrayList();
		String sig = "error";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboItinerarios: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPItinerariosLN itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
			OCAPConvocatoriasLN oCAPConvocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPCategProfesionalesLN oCAPCategProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);

			long grado = 1L;
			if (((OCAPEncuestaCalidadForm) form).getCProfesional_id() > 0L) {
				long profId = ((OCAPEncuestaCalidadForm) form).getCProfesional_id() > 0L
						? ((OCAPEncuestaCalidadForm) form).getCProfesional_id() : 0L;
				listadoItinerarios = itinerariosLN.listarItinerarios(grado, profId,
						((OCAPEncuestaCalidadForm) form).getCEspec_id(), 0L);
			} else {
				listadoItinerarios = itinerariosLN.listadoItinerarios();
			}
			session.setAttribute(Constantes.COMBO_ITINERARIOS, listadoItinerarios);

			if (((OCAPEncuestaCalidadForm) form).getCProfesional_id() < 0L) {
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
			}
			sig = "irBuscarEncuestaGeneral";
			((OCAPEncuestaCalidadForm) form).setjspInicio(true);

			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboItinerarios: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboItinerarios: ERROR: " + e.getMessage());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(sig);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward listar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		ArrayList listadoItinerarios = null;
		ArrayList listadoDatos = null;
		ArrayList listadoCuentaDatos = null;
		HttpSession session = request.getSession();
		OCAPEncuestaCalidadOT datos = null;
		OCAPEvaluadoresLN evalLN = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " listar: Inicio");
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPEncuestaCalidadForm formulario = (OCAPEncuestaCalidadForm) form;

			OCAPEncuestaCalidadLN oCAPEncuestaCalidadLN = new OCAPEncuestaCalidadLN(jcylUsuario);
			OCAPEncuestaCalidadOT encOT = new OCAPEncuestaCalidadOT();

			encOT.setCItinerario_id(formulario.getCItinerario_id());
			encOT.setCProfesional_id(formulario.getCProfesional_id());

			listadoDatos = oCAPEncuestaCalidadLN.listarEstadisticas(encOT);

			listadoCuentaDatos = oCAPEncuestaCalidadLN.contarEstadisticas(encOT);

			evalLN = new OCAPEvaluadoresLN(jcylUsuario);
			OCAPUsuariosOT usuAuxOT = new OCAPUsuariosOT();
			usuAuxOT.setDEstado("4");
			usuAuxOT.setCItinerarioId(formulario.getCItinerario_id() == -1 ? 0L : formulario.getCItinerario_id());
			usuAuxOT.setCProfesionalId(new Integer((int) formulario.getCProfesional_id()));

			usuAuxOT.setCEspecId(new Integer(0));
			int numTotalEvaluadosFaseIII = evalLN.listarEvaluadosCuenta(usuAuxOT, jcylUsuario);
			formulario.setNumTotalEvaluados(Integer.toString(numTotalEvaluadosFaseIII));
			formulario.setNTotalEvaluados(numTotalEvaluadosFaseIII);

			String porItinerario = null;
			if (encOT.getCItinerario_id() != 0L)
				porItinerario = Constantes.SI;
			if (encOT.getCProfesional_id() != 0L)
				porItinerario = Constantes.NO;
			long paramFiltro = encOT.getCItinerario_id() != 0L ? encOT.getCItinerario_id() : encOT.getCProfesional_id();
			int numTotalEvaluadosConEncuesta = oCAPEncuestaCalidadLN.contarOCAPEncuesta(1L, paramFiltro, porItinerario);
			formulario.setNumTotalEncuestas(Integer.toString(numTotalEvaluadosConEncuesta));
			formulario.setNTotalEncuestas(numTotalEvaluadosConEncuesta);

			ArrayList listaPreg = new ArrayList();
			ArrayList listaPregCuenta = new ArrayList();

			OCAPEncuestaCalidadOT datosCuenta = (OCAPEncuestaCalidadOT) listadoCuentaDatos.get(0);
			listaPregCuenta = datosCuenta.getListaPreguntas();

			for (int i = 0; i < listadoDatos.size(); i++) {
				datos = (OCAPEncuestaCalidadOT) listadoDatos.get(i);

				listaPreg = datos.getListaPreguntas();
				for (int j = 0; j < listaPreg.size(); j++) {
					OCAPEncuestaCalidadOT datosCuentaTotal = (OCAPEncuestaCalidadOT) listaPregCuenta.get(j);
					OCAPEncuestaCalidadOT resp = (OCAPEncuestaCalidadOT) listaPreg.get(j);

					resp.setPorc1(100 * resp.getTotal1() / datosCuentaTotal.getTotalRespuestas());
					resp.setPorc2(100 * resp.getTotal2() / datosCuentaTotal.getTotalRespuestas());
					resp.setPorc3(100 * resp.getTotal3() / datosCuentaTotal.getTotalRespuestas());
					resp.setPorc4(100 * resp.getTotal4() / datosCuentaTotal.getTotalRespuestas());
					resp.setPorc5(100 * resp.getTotal5() / datosCuentaTotal.getTotalRespuestas());
				}

			}

			formulario.setListaItinerariosPreguntas(listadoDatos);
			formulario.setListaItinerariosPreguntasCuenta(listadoCuentaDatos);

			OCAPConfigApp.logger.info(getClass().getName() + " listar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " listar: ERROR: " + e.getMessage());
			request.setAttribute("mensaje", mensajeError);
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irBuscarEncuestaGeneral");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irPreguntas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		ArrayList listadoItinerarios = null;
		ArrayList listadoCategorias = null;
		HttpSession session = request.getSession();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irPreguntas: Inicio");
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPItinerariosLN oCAPItinerariosLN = new OCAPItinerariosLN(jcylUsuario);
			listadoItinerarios = oCAPItinerariosLN.listadoItinerariosUsados();
			session.setAttribute(Constantes.COMBO_ITINERARIOS, listadoItinerarios);

			OCAPEstatutarioLN estatutLN = new OCAPEstatutarioLN(jcylUsuario);
			listadoCategorias = estatutLN.listadoOCAPEstatutariosUsados();
			session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);

			((OCAPEncuestaCalidadForm) form).setjspInicio(true);

			session.setAttribute("cPreguntaId",
					request.getParameter("pregId") == null ? "" : request.getParameter("pregId").toString());
			OCAPConfigApp.logger.info(getClass().getName() + " irPreguntas: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " irPreguntas: ERROR: " + e.getMessage());
			request.setAttribute("mensaje", mensajeError);
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irBuscarEncuestaPreguntas");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward listarPreguntas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		ArrayList listadoItinerarios = null;
		ArrayList listadoDatos = null;
		ArrayList listadoRespuestas = null;
		HttpSession session = request.getSession();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " listarPreguntas: Inicio");
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPEncuestaCalidadForm formulario = (OCAPEncuestaCalidadForm) form;

			OCAPEncuestaCalidadLN oCAPEncuestaCalidadLN = new OCAPEncuestaCalidadLN(jcylUsuario);
			long pregId = 0L;
			if ((request.getParameter("pregId") == null) && (session.getAttribute("cPreguntaId") != null)) {
				pregId = Long.parseLong((String) session.getAttribute("cPreguntaId"));
			} else {
				pregId = Long.parseLong(request.getParameter("pregId"));
				session.setAttribute("cPreguntaId",
						request.getParameter("pregId") == null ? "" : request.getParameter("pregId").toString());
			}
			String porItinerario = null;
			if (formulario.getCItinerario_id() != 0L)
				porItinerario = Constantes.SI;
			if (formulario.getCProfesional_id() != 0L)
				porItinerario = Constantes.NO;
			long paramFiltro = formulario.getCItinerario_id() != 0L ? formulario.getCItinerario_id()
					: formulario.getCProfesional_id();
			listadoDatos = oCAPEncuestaCalidadLN.listarPreguntas(paramFiltro, pregId, porItinerario);

			formulario.setListaItinerariosPreguntas(listadoDatos);

			if ((pregId == 18) || (pregId == 24)) {
				OCAPEncuestaCalidadOT encOT = new OCAPEncuestaCalidadOT();
				if (pregId == 18)
					encOT.setRespuesta_18(Constantes.SI);
				if (pregId == 24)
					encOT.setRespuesta_24(Constantes.SI);
				encOT.setCItinerario_id(formulario.getCItinerario_id());
				encOT.setCProfesional_id(formulario.getCProfesional_id());

				listadoRespuestas = oCAPEncuestaCalidadLN.listarEstadisticas(encOT);
				if (listadoRespuestas.size() != 0) {
					OCAPEncuestaCalidadOT aux = (OCAPEncuestaCalidadOT) listadoRespuestas.get(0);
					if (aux.getListaPreguntas().size() != 0) {
						OCAPEncuestaCalidadOT aux2 = (OCAPEncuestaCalidadOT) aux.getListaPreguntas().get(0);
						formulario.setNTotalSi(aux2.getTotalS());
						formulario.setNTotalNo(aux2.getTotalN());
					}
				}
			}

			OCAPConfigApp.logger.info(getClass().getName() + " listarPreguntas: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " listarPreguntas: ERROR: " + e.getMessage());
			request.setAttribute("mensaje", mensajeError);
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irBuscarEncuestaPreguntas");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irRellenar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		OCAPEncuestaCalidadLN oCAPEncuestaCalidadLN = null;
		ArrayList listaRespuesta = null;
		ArrayList listaAreas = null;
		HttpSession session = request.getSession();
		String[] respuestas = new String[35];
		OCAPEncuestaCalidadOT datosRespuesta = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irRellenar: Inicio");
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			oCAPEncuestaCalidadLN = new OCAPEncuestaCalidadLN(jcylUsuario);

			listaAreas = oCAPEncuestaCalidadLN.buscarPreguntas();
			((OCAPEncuestaCalidadForm) form).setListaAreas(listaAreas);
			if (request.getParameter("CExp_id") == null) {
				if (Constantes.OCAP_DIRECCION.equals(jcylUsuario.getUser().getRol()))
					request.setAttribute("tipoAccion", Constantes.VER_DETALLE);
				else
					request.setAttribute("tipoAccion", Constantes.INSERTAR);
			} else {
				listaRespuesta = oCAPEncuestaCalidadLN
						.buscarOCAPEncuesta(Long.parseLong(request.getParameter("CExp_id")));
				for (int i = 0; i < listaRespuesta.size(); i++) {
					datosRespuesta = (OCAPEncuestaCalidadOT) listaRespuesta.get(i);
					respuestas[i] = datosRespuesta.getDRespuesta();
				}
				request.setAttribute("tipoAccion", Constantes.VER_DETALLE);
			}
			((OCAPEncuestaCalidadForm) form).setRespuesta(respuestas);

			OCAPConfigApp.logger.info(getClass().getName() + "Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " irRellenar: ERROR: " + e.getMessage());
			request.setAttribute("mensaje", mensajeError);
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irRellenar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward rellenar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- RELLENAR OCAPEncuestaCalidad --------- ");

			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			OCAPEncuestaCalidadForm formulario = (OCAPEncuestaCalidadForm) form;
			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			String usuId = jcylUsuario.getUser().getC_usr_id();
			this.usuariosLN = new OCAPUsuariosLN(jcylUsuario);
			this.usuarioOT = this.usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);

			OCAPEncuestaCalidadLN oCAPEncuestaCalidadLN = new OCAPEncuestaCalidadLN(jcylUsuario);
			OCAPEncuestaCalidadOT datos = new OCAPEncuestaCalidadOT();

			datos.setCExpId(this.usuarioOT.getCExpId().longValue());
			datos.setACreadoPor(jcylUsuario.getUsuario().getC_usr_id());

			datos.setListaRespuestas(new ArrayList(Arrays.asList(formulario.getRespuesta())));
			datos.setListaPreguntas(oCAPEncuestaCalidadLN.buscarPreguntas());

			int result = oCAPEncuestaCalidadLN.rellenaOCAPEncuestaCalidad(datos);

			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");

				request.setAttribute("rutaVuelta", "OCAPCuestionarios.do?accion=irListar");
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
				sig = "error";
			}

			OCAPConfigApp.logger.info("---------- FIN RELLENAR OCAPEncuestaCalidad --------- ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";
			request.setAttribute("mensaje", mensajeError);
		}

		return mapping.findForward(sig);
	}

	public OCAPEncuestaCalidadAction() {
		$init$();
	}
}
