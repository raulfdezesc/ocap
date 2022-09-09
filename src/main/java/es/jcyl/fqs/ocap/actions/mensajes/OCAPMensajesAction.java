package es.jcyl.fqs.ocap.actions.mensajes;

import es.jcyl.fqs.ocap.actionforms.mensajes.OCAPMensajesForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.estatutario.OCAPEstatutarioLN;
import es.jcyl.fqs.ocap.ln.grado.OCAPGradoLN;
import es.jcyl.fqs.ocap.ln.mensajes.OCAPMensajesLN;
import es.jcyl.fqs.ocap.ot.mensajes.OCAPMensajesOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Pagina;
import es.jcyl.fqs.ocap.util.Utilidades;
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

public class OCAPMensajesAction extends OCAPGenericAction {
	public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- BAJA DE OCAP_MENSAJES --------- ");
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPMensajesLN oCAPMensajesLN = new OCAPMensajesLN(jcylUsuario);

			String cMensajeIdS = request.getParameter("cMensajeIdS");
			int cMensajeId;
			if ((cMensajeIdS != null) && (!cMensajeIdS.equals(""))) {
				cMensajeId = Integer.parseInt(cMensajeIdS);
				OCAPConfigApp.logger.info("cMensajeId: " + cMensajeId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			int result = oCAPMensajesLN.bajaOCAPMensajes(cMensajeId);

			if (result == 0) {
				OCAPConfigApp.logger.info("Se ha producido un error al borrar el registro");
				sig = "error";
				request.setAttribute("mensaje", "Se ha producido un error al borrar el registro");
			} else {
				OCAPConfigApp.logger.info("El registro se ha eliminado con éxito");
				request.setAttribute("mensaje", "El registro se ha eliminado con éxito");
				request.setAttribute("rutaVuelta", "OCAPMensajes.do?accion=buscar&recuperarBusquedaAnterior=Y");

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
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		HttpSession session = request.getSession();
		Utilidades utilidades = new Utilidades();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irBuscar: Inicio");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
			Collection listadoEstat = estatutarioLN.listadoOCAPEstatutario();
			Object[] listadoEstatutario = listadoEstat.toArray();

			OCAPGradoLN GradoLN = new OCAPGradoLN(jcylUsuario);
			Collection listadoGra = GradoLN.listadoOCAPGrado();
			Object[] listadoGrados = listadoGra.toArray();

			session.setAttribute(Constantes.COMBO_ESTATUTARIO, utilidades.ArrayObjectToArrayList(listadoEstatutario));
			session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, utilidades.ArrayObjectToArrayList(listadoGrados));
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
			OCAPConfigApp.logger.info("---------- CONSULTA DE OCAP_MENSAJES --------- ");
			request.setAttribute("primeraConsulta", "false");

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPMensajesForm) request.getSession().getAttribute("OCAPMensajesFormBuscador");
				request.setAttribute("OCAPMensajesForm", form);
			} else {
				request.getSession().setAttribute("OCAPMensajesFormBuscador", form);
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
			OCAPMensajesForm formulario = (OCAPMensajesForm) form;

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPMensajesLN oCAPMensajesLN = new OCAPMensajesLN(jcylUsuario);

			long cMensajeId = 0L;
			long cEstatutId = 0L;
			long cGradoId = 0L;
			String dDescripcion = null;
			String fCreacion = null;
			String fModificacion = null;

			cMensajeId = formulario.getCMensaje_id();
			cEstatutId = formulario.getCEstatut_id();
			cGradoId = formulario.getCGrado_id();

			if ((formulario.getDDescripcion() != null) && (!formulario.getDDescripcion().equals(""))) {
				dDescripcion = formulario.getDDescripcion();
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

			Collection elementos = oCAPMensajesLN.consultaOCAPMensajes(cMensajeId, cEstatutId, cGradoId, dDescripcion,
					fCreacion, fModificacion, primerRegistro, registrosPorPagina);

			if (elementos != null) {
				OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Gerencias para la consulta");
				if (elementos.size() == 0) {
					request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
					request.removeAttribute("conDatos");
					request.getSession().removeAttribute("paginaOCAPMensajesOT");
				} else {
					Object[] listadoelementos = elementos.toArray();

					OCAPGradoLN GradoLN = new OCAPGradoLN(jcylUsuario);
					OCAPEstatutarioLN EstatutarioLN = new OCAPEstatutarioLN(jcylUsuario);

					int reg = elementos.size();

					elementos.removeAll(elementos);

					for (int i = 0; i < reg; i++) {
						((OCAPMensajesOT) listadoelementos[i]).setDEstatutNombre(EstatutarioLN
								.buscarOCAPEstatutario(((OCAPMensajesOT) listadoelementos[i]).getCEstatutId())
								.getDNombre());
						((OCAPMensajesOT) listadoelementos[i]).setDGradoNombre(GradoLN
								.buscarOCAPGrado(((OCAPMensajesOT) listadoelementos[i]).getCGradoId()).getDNombre());

						elementos.add(listadoelementos[i]);
					}

					int nListado = 0;
					nListado = oCAPMensajesLN.listadoOCAPMensajesCuenta(cMensajeId, cEstatutId, cGradoId, dDescripcion,
							fCreacion, fModificacion);
					request.removeAttribute("sinDatos");
					Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
					pagina.setElementos(elementos);
					pagina.setRegistroActual(primerRegistro);
					pagina.setNRegistros(nListado);
					pagina.setRegistrosPorPagina(registrosPorPagina);
					request.setAttribute("paginaOCAPMensajesOT", pagina);
					request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
					request.removeAttribute("menu");
					request.getSession().removeAttribute("menu");
				}
			} else {
				request.setAttribute("errorConsultando", "Error consultando en la base de datos");
			}
			OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAPMensajes ------- ");
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
			OCAPConfigApp.logger.info("---------- DETALLE OCAP_MENSAJES --------- ");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPMensajesLN oCAPMensajesLN = new OCAPMensajesLN(jcylUsuario);
			OCAPMensajesOT datos = new OCAPMensajesOT();

			OCAPMensajesForm formulario = (OCAPMensajesForm) form;

			String cMensajeIdS = request.getParameter("cMensajeIdS");
			long cMensajeId;
			if ((cMensajeIdS != null) && (!cMensajeIdS.equals(""))) {
				cMensajeId = Long.parseLong(cMensajeIdS);
				OCAPConfigApp.logger.info("cMensajeId: " + cMensajeId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			datos = oCAPMensajesLN.buscarOCAPMensajes(cMensajeId);

			if (datos.getCMensajeId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPMensajesOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosDetalle");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				OCAPGradoLN GradoLN = new OCAPGradoLN(jcylUsuario);
				OCAPEstatutarioLN EstatutarioLN = new OCAPEstatutarioLN(jcylUsuario);

				formulario.setCMensaje_id(datos.getCMensajeId());
				formulario.setCEstatut_id(datos.getCEstatutId());
				formulario.setCGrado_id(datos.getCGradoId());
				formulario.setDDescripcion(datos.getDDescripcion());
				formulario.setDEstatut_nombre(EstatutarioLN.buscarOCAPEstatutario(datos.getCEstatutId()).getDNombre());
				formulario.setDGrado_nombre(GradoLN.buscarOCAPGrado(datos.getCGradoId()).getDNombre());

				OCAPConfigApp.logger.info("Se ha encontrado OCAPMensajes");
				request.getSession().setAttribute("conDatosDetalle", "");
				request.getSession().removeAttribute("sinDatosDetalle");
				request.getSession().setAttribute("OCAPMensajesOT", datos);
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
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		HttpSession session = request.getSession();
		Utilidades utilidades = new Utilidades();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Inicio");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
			Collection listadoEstat = estatutarioLN.listadoOCAPEstatutario();
			Object[] listadoEstatutario = listadoEstat.toArray();

			OCAPGradoLN GradoLN = new OCAPGradoLN(jcylUsuario);
			Collection listadoGra = GradoLN.listadoOCAPGrado();
			Object[] listadoGrados = listadoGra.toArray();

			session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, utilidades.ArrayObjectToArrayList(listadoGrados));
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
			OCAPConfigApp.logger.info("---------- ALTA OCAP_MENSAJES --------- ");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPMensajesLN oCAPMensajesLN = new OCAPMensajesLN(jcylUsuario);
			OCAPMensajesOT datos = new OCAPMensajesOT();

			OCAPMensajesForm formulario = (OCAPMensajesForm) form;
			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

			if (formulario.getCEstatut_id() == 0L) {
				mensajeError = "El campo Estatuto es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if (formulario.getCGrado_id() == 0L) {
				mensajeError = "El campo Grado es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getDDescripcion() != null) && (!formulario.getDDescripcion().equals(""))) {
				datos.setDDescripcion(formulario.getDDescripcion());
				OCAPConfigApp.logger.info("dDescripcion: " + datos.getDDescripcion());
			}

			if ((formulario.getDDescripcion() == null) || (formulario.getDDescripcion().equals(""))) {
				mensajeError = "El campo Descripción es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setCMensajeId(formulario.getCMensaje_id());
			OCAPConfigApp.logger.info("cMensajeId: " + datos.getCMensajeId());

			datos.setCEstatutId(formulario.getCEstatut_id());
			OCAPConfigApp.logger.info("cEstatutId: " + datos.getCEstatutId());

			datos.setCGradoId(formulario.getCGrado_id());
			OCAPConfigApp.logger.info("cGradoId: " + datos.getCGradoId());

			datos.setACreadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
			int result = oCAPMensajesLN.altaOCAPMensajes(datos);
			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
				request.setAttribute("rutaVuelta", "OCAPMensajes.do?accion=irInsertar");
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA OCAP_MENSAJES --------- ");
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
			OCAPConfigApp.logger.info("---------- MODIFICACION OCAP_MENSAJES --------- ");
			ActionErrors errores = new ActionErrors();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPMensajesLN oCAPMensajesLN = new OCAPMensajesLN(jcylUsuario);
			OCAPMensajesOT datos = new OCAPMensajesOT();

			OCAPMensajesForm formulario = (OCAPMensajesForm) form;

			String cMensajeIdS = request.getParameter("cMensajeIdS");
			long cMensajeId;
			if ((cMensajeIdS != null) && (!cMensajeIdS.equals(""))) {
				cMensajeId = Long.parseLong(cMensajeIdS);
				OCAPConfigApp.logger.info("cMensajeId: " + cMensajeId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			datos = oCAPMensajesLN.buscarOCAPMensajes(cMensajeId);

			if (datos.getCMensajeId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPMensajesOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosEdicion");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				OCAPConfigApp.logger.info("Se ha encontrado OCAPMensajesOT");
				request.getSession().setAttribute("conDatosEdicion", "");
				request.getSession().removeAttribute("sinDatosEdicion");
				request.setAttribute("OCAPMensajesOT", datos);

				formulario.setCMensaje_id(datos.getCMensajeId());
				formulario.setCEstatut_id(datos.getCEstatutId());
				formulario.setCGrado_id(datos.getCGradoId());
				formulario.setDDescripcion(datos.getDDescripcion());

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
			OCAPConfigApp.logger.info("---------- GRABAR OCAP_MENSAJES --------- ");
			ActionErrors errores = new ActionErrors();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPMensajesLN oCAPMensajesLN = new OCAPMensajesLN(jcylUsuario);
			OCAPMensajesOT datos = new OCAPMensajesOT();

			OCAPMensajesForm formulario = (OCAPMensajesForm) form;

			String cMensajeIdS = request.getParameter("cMensajeIdS");
			if ((cMensajeIdS != null) && (!cMensajeIdS.equals(""))) {
				long cMensajeId = Long.parseLong(cMensajeIdS);
				OCAPConfigApp.logger.info("cMensajeId: " + cMensajeId);
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

			if ((formulario.getDDescripcion() != null) && (!formulario.getDDescripcion().equals(""))) {
				datos.setDDescripcion(formulario.getDDescripcion());
				OCAPConfigApp.logger.info("dDescripcion: " + datos.getDDescripcion());
			}

			if ((formulario.getDDescripcion() == null) || (formulario.getDDescripcion().equals(""))) {
				mensajeError = "El campo Descripción es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setCMensajeId(formulario.getCMensaje_id());
			OCAPConfigApp.logger.info("cMensajeId: " + datos.getCMensajeId());

			datos.setCEstatutId(formulario.getCEstatut_id());
			OCAPConfigApp.logger.info("cEstatutId: " + datos.getCEstatutId());

			datos.setCGradoId(formulario.getCGrado_id());
			OCAPConfigApp.logger.info("cGradoId: " + datos.getCGradoId());

			int result = oCAPMensajesLN.modificacionOCAPMensajes(datos);

			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha modificado con éxito");
				request.setAttribute("rutaVuelta",
						"OCAPMensajes.do?accion=detalle&cMensajeIdS=" + datos.getCMensajeId());
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("---------- FIN GRABAR MODIFICACION OCAPMensajes --------- ");
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
