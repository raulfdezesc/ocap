package es.jcyl.fqs.ocap.actions.creditos;

import es.jcyl.fqs.ocap.actionforms.creditos.OCAPCreditosForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.creditos.OCAPCreditosLN;
import es.jcyl.fqs.ocap.ln.defMeritosCurriculares.OCAPDefMeritoscurricularesLN;
import es.jcyl.fqs.ocap.ln.estatutario.OCAPEstatutarioLN;
import es.jcyl.fqs.ocap.ln.grado.OCAPGradoLN;
import es.jcyl.fqs.ocap.ot.creditos.OCAPCreditosOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Pagina;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLUsuario;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OCAPCreditosAction extends OCAPGenericAction {
	public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- BAJA DE OCAP_CREDITOS --------- ");
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPCreditosLN oCAPCreditosLN = new OCAPCreditosLN(jcylUsuario);

			String cCreditoIdS = request.getParameter("cCreditoIdS");
			int cCreditoId;
			if ((cCreditoIdS != null) && (!cCreditoIdS.equals(""))) {
				cCreditoId = Integer.parseInt(cCreditoIdS);
				OCAPConfigApp.logger.info("cCreditoId: " + cCreditoId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			int result = oCAPCreditosLN.bajaOCAPCreditos(cCreditoId);

			if (result == 0) {
				OCAPConfigApp.logger.info("Se ha producido un error al borrar el registro");
				sig = "error";
				request.setAttribute("mensaje", "Se ha producido un error al borrar el registro");
			} else {
				OCAPConfigApp.logger.info("El registro se ha eliminado con éxito");
				request.setAttribute("mensaje", "El registro se ha eliminado con éxito");
				request.setAttribute("rutaVuelta", "OCAPCreditos.do?accion=buscar&recuperarBusquedaAnterior=Y");

				sig = "exito";
			}
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";
			request.setAttribute("mensaje", "Se ha producido un error");
		}

		return mapping.findForward(sig);
	}

	public ActionForward irBuscar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		String mensajeError = "Se ha producido un error";
		HttpSession session = request.getSession();
		Utilidades utilidades = new Utilidades();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irBuscar: Inicio");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
			Collection listadoEstat = estatutarioLN.listadoOCAPEstatutario();
			Object[] listadoEstatutario = listadoEstat.toArray();

			OCAPGradoLN GradoLN = new OCAPGradoLN(jcylUsuario);
			Collection listadoGra = GradoLN.listadoOCAPGrado();
			Object[] listadoGrados = listadoGra.toArray();

			OCAPDefMeritoscurricularesLN DefMeritosCurricularesLN = new OCAPDefMeritoscurricularesLN(jcylUsuario);
			Collection listadoMerCurr = DefMeritosCurricularesLN.listadoOCAPDefMeritoscurriculares();
			Object[] listadoDefMerCurr = listadoMerCurr.toArray();

			session.setAttribute(Constantes.COMBO_ESTATUTARIO, utilidades.ArrayObjectToArrayList(listadoEstatutario));
			session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, utilidades.ArrayObjectToArrayList(listadoGrados));
			session.setAttribute(Constantes.COMBO_DEF_MERITOSCURRICULARES, utilidades.ArrayObjectToArrayList(listadoDefMerCurr));
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " irBuscar: ERROR: " + e.getMessage());
			request.setAttribute("mensaje", mensajeError);
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irBuscar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward buscar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String mensajeError = "Se ha producido un error";
		HttpSession session = request.getSession();
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- CONSULTA DE OCAP_CREDITOS --------- ");
			request.setAttribute("primeraConsulta", "false");

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPCreditosForm) request.getSession().getAttribute("OCAPCreditosFormBuscador");
				request.setAttribute("OCAPCreditosForm", form);
			} else {
				request.getSession().setAttribute("OCAPCreditosFormBuscador", form);
				session.setAttribute("iRegistro", 1 + "");
			}

			int primerRegistro = 1;
			int registrosPorPagina = 10;

			String s = null;
			s = request.getParameter("iRegistro");
			if (s != null)
				primerRegistro = Integer.parseInt(s);
			s = request.getParameter("nRegistrosP");
			if (s != null) {
				registrosPorPagina = Integer.parseInt(s);
			}
			OCAPCreditosForm formulario = (OCAPCreditosForm) form;

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPCreditosLN oCAPCreditosLN = new OCAPCreditosLN(jcylUsuario);

			long cCreditoId = 0L;
			long cEstatutId = 0L;
			long cGradoId = 0L;
			long cDefmeritoId = 0L;
			String nCreditoCreditos = null;
			String fCreacion = null;
			String fModificacion = null;

			cCreditoId = formulario.getCCredito_id();
			cEstatutId = formulario.getCEstatut_id();
			cGradoId = formulario.getCGrado_id();
			cDefmeritoId = formulario.getCDefmerito_id();

			if ((formulario.getNCreditos() != null) && (!formulario.getNCreditos().equals(""))) {
				nCreditoCreditos = formulario.getNCreditos();
			}

			if ((formulario.getACreacion() != null) && (!formulario.getACreacion().equals(""))
					&& (formulario.getMCreacion() != null) && (!formulario.getMCreacion().equals(""))
					&& (formulario.getDCreacion() != null) && (!formulario.getDCreacion().equals(""))) {
				fCreacion = formulario.getDCreacion() + '/' + formulario.getMCreacion() + '/'
						+ formulario.getACreacion();
			}

			if ((formulario.getAModificacion() != null) && (!formulario.getAModificacion().equals(""))
					&& (formulario.getMModificacion() != null) && (!formulario.getMModificacion().equals(""))
					&& (formulario.getDModificacion() != null) && (!formulario.getDModificacion().equals(""))) {
				fModificacion = formulario.getDModificacion() + '/' + formulario.getMModificacion() + '/'
						+ formulario.getAModificacion();
			}

			Collection elementos = oCAPCreditosLN.consultaOCAPCreditos(cCreditoId, cEstatutId, cGradoId, cDefmeritoId,
					nCreditoCreditos, fCreacion, fModificacion, primerRegistro, registrosPorPagina);

			if (elementos != null) {
				OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Gerencias para la consulta");
				if (elementos.size() == 0) {
					request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
					request.removeAttribute("conDatos");
					request.getSession().removeAttribute("paginaOCAPCreditosOT");
				} else {
					Object[] listadoelementos = elementos.toArray();

					OCAPGradoLN GradoLN = new OCAPGradoLN(jcylUsuario);
					OCAPEstatutarioLN EstatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
					OCAPDefMeritoscurricularesLN DefMeritoscurricularesLN = new OCAPDefMeritoscurricularesLN(
							jcylUsuario);

					int reg = elementos.size();

					elementos.removeAll(elementos);

					for (int i = 0; i < reg; i++) {
						((OCAPCreditosOT) listadoelementos[i]).setDEstatutNombre(EstatutarioLN
								.buscarOCAPEstatutario(((OCAPCreditosOT) listadoelementos[i]).getCEstatutId())
								.getDNombre());
						((OCAPCreditosOT) listadoelementos[i]).setDGradoNombre(GradoLN
								.buscarOCAPGrado(((OCAPCreditosOT) listadoelementos[i]).getCGradoId()).getDNombre());
						((OCAPCreditosOT) listadoelementos[i])
								.setDDefmeritoNombre(
										DefMeritoscurricularesLN
												.buscarOCAPDefMeritoscurriculares(
														((OCAPCreditosOT) listadoelementos[i]).getCDefmeritoId())
												.getDNombre());

						elementos.add(listadoelementos[i]);
					}

					int nListado = 0;
					nListado = oCAPCreditosLN.listadoOCAPCreditosCuenta(cCreditoId, cEstatutId, cGradoId, cDefmeritoId,
							nCreditoCreditos, fCreacion, fModificacion);
					request.removeAttribute("sinDatos");
					Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
					pagina.setElementos(elementos);
					pagina.setRegistroActual(primerRegistro);
					pagina.setNRegistros(nListado);
					pagina.setRegistrosPorPagina(registrosPorPagina);
					request.setAttribute("paginaOCAPCreditosOT", pagina);
					request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
					request.removeAttribute("menu");
					request.getSession().removeAttribute("menu");
				}
			} else {
				request.setAttribute("errorConsultando", "Error consultando en la base de datos");
			}
			OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAPCreditos ------- ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			request.setAttribute("mensaje", mensajeError);
			return mapping.findForward("error");
		}

		return mapping.findForward("irBuscar");
	}

	public ActionForward detalle(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		HttpSession session = request.getSession();
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- DETALLE OCAP_CREDITOS --------- ");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPCreditosLN oCAPCreditosLN = new OCAPCreditosLN(jcylUsuario);
			OCAPCreditosOT datos = new OCAPCreditosOT();

			OCAPCreditosForm formulario = (OCAPCreditosForm) form;

			String cCreditoIdS = request.getParameter("cCreditoIdS");
			long cCreditoId;
			if ((cCreditoIdS != null) && (!cCreditoIdS.equals(""))) {
				cCreditoId = Long.parseLong(cCreditoIdS);
				OCAPConfigApp.logger.info("cCreditoId: " + cCreditoId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			datos = oCAPCreditosLN.buscarOCAPCreditos(cCreditoId);

			if (datos.getCCreditoId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPCreditosOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosDetalle");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				OCAPGradoLN GradoLN = new OCAPGradoLN(jcylUsuario);
				OCAPEstatutarioLN EstatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
				OCAPDefMeritoscurricularesLN DefMeritoscurricularesLN = new OCAPDefMeritoscurricularesLN(jcylUsuario);

				formulario.setCCredito_id(datos.getCCreditoId());
				formulario.setCEstatut_id(datos.getCEstatutId());
				formulario.setCDefmerito_id(datos.getCDefmeritoId());
				formulario.setCGrado_id(datos.getCGradoId());
				formulario.setDEstatut_nombre(EstatutarioLN.buscarOCAPEstatutario(datos.getCEstatutId()).getDNombre());
				formulario.setDDefmerito_nombre(DefMeritoscurricularesLN
						.buscarOCAPDefMeritoscurriculares(datos.getCDefmeritoId()).getDNombre());
				formulario.setDGrado_nombre(GradoLN.buscarOCAPGrado(datos.getCGradoId()).getDNombre());
				formulario.setNCreditos(String.valueOf(datos.getNCreditos()));

				OCAPConfigApp.logger.info("Se ha encontrado OCAPCreditos");
				request.getSession().setAttribute("conDatosDetalle", "");
				request.getSession().removeAttribute("sinDatosDetalle");
				request.getSession().setAttribute("OCAPCreditosOT", datos);
				sig = "verDetalle";
				request.setAttribute("tipoAccion", Constantes.VER_DETALLE);
			}
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
		}
		ActionForward actionForward = mapping.findForward(sig);
		OCAPConfigApp.logger.info("forward--> " + actionForward.getPath());
		OCAPConfigApp.logger.info("...........se sale del Action");

		return actionForward;
	}

	public ActionForward irInsertar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		String mensajeError = "Se ha producido un error";
		HttpSession session = request.getSession();
		Utilidades utilidades = new Utilidades();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Inicio");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
			Collection listadoEstat = estatutarioLN.listadoOCAPEstatutario();
			Object[] listadoEstatutario = listadoEstat.toArray();

			OCAPGradoLN GradoLN = new OCAPGradoLN(jcylUsuario);
			Collection listadoGra = GradoLN.listadoOCAPGrado();
			Object[] listadoGrados = listadoGra.toArray();

			OCAPDefMeritoscurricularesLN DefMeritosCurricularesLN = new OCAPDefMeritoscurricularesLN(jcylUsuario);
			Collection listadoMerCurr = DefMeritosCurricularesLN.listadoOCAPDefMeritoscurriculares();
			Object[] listadoDefMerCurr = listadoMerCurr.toArray();

			session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, utilidades.ArrayObjectToArrayList(listadoGrados));
			session.setAttribute(Constantes.COMBO_DEF_MERITOSCURRICULARES, utilidades.ArrayObjectToArrayList(listadoDefMerCurr));
			session.setAttribute(Constantes.COMBO_ESTATUTARIO, utilidades.ArrayObjectToArrayList(listadoEstatutario));

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: ERROR: " + e.getMessage());
			request.setAttribute("mensaje", mensajeError);
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irInsertar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward insertar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		HttpSession session = request.getSession();
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- ALTA OCAP_CREDITOS --------- ");

			ActionErrors errores = new ActionErrors();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPCreditosLN oCAPCreditosLN = new OCAPCreditosLN(jcylUsuario);
			OCAPCreditosOT datos = new OCAPCreditosOT();

			OCAPCreditosForm formulario = (OCAPCreditosForm) form;
			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

			if (formulario.getCEstatut_id() == 0L) {
				mensajeError = "El campo Estatuto es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if (formulario.getCGrado_id() == 0L) {
				mensajeError = "El campo Grado es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if (formulario.getCDefmerito_id() == 0L) {
				mensajeError = "El campo Mérito es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setCCreditoId(formulario.getCCredito_id());
			OCAPConfigApp.logger.info("cCreditoId: " + datos.getCCreditoId());

			datos.setCEstatutId(formulario.getCEstatut_id());
			OCAPConfigApp.logger.info("cEstatutId: " + datos.getCEstatutId());

			datos.setCGradoId(formulario.getCGrado_id());
			OCAPConfigApp.logger.info("cGradoId: " + datos.getCGradoId());

			datos.setCDefmeritoId(formulario.getCDefmerito_id());
			OCAPConfigApp.logger.info("cDefmeritoId: " + datos.getCDefmeritoId());

			if ((formulario.getNCreditos() != null) && (!formulario.getNCreditos().equals("")))
				datos.setNCreditos(Double.valueOf(formulario.getNCreditos()).doubleValue());
			else {
				datos.setNCreditos(0.0D);
			}

			OCAPConfigApp.logger.info("nCreditoCreditos: " + datos.getNCreditos());
			datos.setACreadoPor(
					((JCYLUsuario) request.getSession().getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO)).getUsuario().getC_usr_id());
			int result = oCAPCreditosLN.altaOCAPCreditos(datos);
			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
				request.setAttribute("rutaVuelta", "OCAPCreditos.do?accion=irInsertar");
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA OCAPCreditos --------- ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";

			if (ex.getMessage().startsWith("ORA-00001")) {
				mensajeError = "Error: Registro duplicado";
			}

			request.setAttribute("mensaje", mensajeError);
		}

		return mapping.findForward(sig);
	}

	public ActionForward irEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		HttpSession session = request.getSession();
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- MODIFICACION OCAP_CREDITOS --------- ");
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPCreditosLN oCAPCreditosLN = new OCAPCreditosLN(jcylUsuario);
			OCAPCreditosOT datos = new OCAPCreditosOT();

			OCAPCreditosForm formulario = (OCAPCreditosForm) form;

			String cCreditoIdS = request.getParameter("cCreditoIdS");
			long cCreditoId;
			if ((cCreditoIdS != null) && (!cCreditoIdS.equals(""))) {
				cCreditoId = Long.parseLong(cCreditoIdS);
				OCAPConfigApp.logger.info("cCreditoId: " + cCreditoId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			datos = oCAPCreditosLN.buscarOCAPCreditos(cCreditoId);

			if (datos.getCCreditoId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPCreditosOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosEdicion");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				OCAPConfigApp.logger.info("Se ha encontrado OCAPCreditosOT");
				request.getSession().setAttribute("conDatosEdicion", "");
				request.getSession().removeAttribute("sinDatosEdicion");
				request.setAttribute("OCAPCreditosOT", datos);

				formulario.setCCredito_id(datos.getCCreditoId());
				formulario.setCEstatut_id(datos.getCEstatutId());
				formulario.setCGrado_id(datos.getCGradoId());
				formulario.setCDefmerito_id(datos.getCDefmeritoId());
				formulario.setNCreditos(String.valueOf(datos.getNCreditos()));

				sig = "irModificar";
			}
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
		}
		ActionForward actionForward = mapping.findForward(sig);
		OCAPConfigApp.logger.info("forward--> " + actionForward.getPath());
		OCAPConfigApp.logger.info("...........se sale del Action");

		request.setAttribute("tipoAccion", Constantes.MODIFICAR);

		return actionForward;
	}

	public ActionForward grabar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		HttpSession session = request.getSession();
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- GRABAR OCAP_CREDITOS --------- ");
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPCreditosLN oCAPCreditosLN = new OCAPCreditosLN(jcylUsuario);
			OCAPCreditosOT datos = new OCAPCreditosOT();

			OCAPCreditosForm formulario = (OCAPCreditosForm) form;

			String cCreditoIdS = request.getParameter("cCreditoIdS");
			if ((cCreditoIdS != null) && (!cCreditoIdS.equals(""))) {
				long cCreditoId = Long.parseLong(cCreditoIdS);
				OCAPConfigApp.logger.info("cCreditoId: " + cCreditoId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

			if (formulario.getCEstatut_id() == 0L) {
				mensajeError = "El campo Estatuto es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if (formulario.getCGrado_id() == 0L) {
				mensajeError = "El campo Grado es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if (formulario.getCDefmerito_id() == 0L) {
				mensajeError = "El campo Mérito es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setCCreditoId(formulario.getCCredito_id());
			OCAPConfigApp.logger.info("cCreditoId: " + datos.getCCreditoId());

			datos.setCEstatutId(formulario.getCEstatut_id());
			OCAPConfigApp.logger.info("cEstatutId: " + datos.getCEstatutId());

			datos.setCGradoId(formulario.getCGrado_id());
			OCAPConfigApp.logger.info("cGradoId: " + datos.getCGradoId());

			datos.setCDefmeritoId(formulario.getCDefmerito_id());
			OCAPConfigApp.logger.info("cDefmeritoId: " + datos.getCDefmeritoId());

			if ((formulario.getNCreditos() != null) && (!formulario.getNCreditos().equals(""))) {
				datos.setNCreditos(Float.valueOf(formulario.getNCreditos()).floatValue());
			}

			OCAPConfigApp.logger.info("nCreditoCreditos: " + datos.getNCreditos());

			int result = oCAPCreditosLN.modificacionOCAPCreditos(datos);

			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha modificado con éxito");
				request.setAttribute("rutaVuelta",
						"OCAPCreditos.do?accion=detalle&cCreditoIdS=" + datos.getCCreditoId());
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("---------- FIN GRABAR MODIFICACION OCAPCreditos --------- ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";

			if (ex.getMessage().startsWith("ORA-00001")) {
				mensajeError = "Error: Registro duplicado";
			}
			request.setAttribute("mensaje", mensajeError);
		}

		return mapping.findForward(sig);
	}
}
