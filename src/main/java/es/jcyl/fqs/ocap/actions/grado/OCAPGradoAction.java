package es.jcyl.fqs.ocap.actions.grado;

import es.jcyl.fqs.ocap.actionforms.grado.OCAPGradoForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.grado.OCAPGradoLN;
import es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT;
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
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OCAPGradoAction extends OCAPGenericAction {
	public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		Logger log = null;
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- BAJA DE OCAP_GRADO --------- ");
			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPGradoLN oCAPGradoLN = new OCAPGradoLN(jcylUsuario);

			OCAPGradoForm formulario = (OCAPGradoForm) form;

			String cGradoIdS = request.getParameter("cGradoIdS");
			int cGradoId;
			if ((cGradoIdS != null) && (!cGradoIdS.equals(""))) {
				cGradoId = Integer.parseInt(cGradoIdS);
				OCAPConfigApp.logger.info("cGradoId: " + cGradoId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			int result = oCAPGradoLN.bajaOCAPGrado(cGradoId);

			if (result == 0) {
				OCAPConfigApp.logger.info("Se ha producido un error al borrar el registro");
				sig = "error";
				request.setAttribute("mensaje", "Se ha producido un error al borrar el registro");
			} else {
				OCAPConfigApp.logger.info("El registro se ha eliminado con éxito");
				request.setAttribute("mensaje", "El registro se ha eliminado con éxito");
				request.setAttribute("rutaVuelta", "OCAPGrado.do?accion=buscar&recuperarBusquedaAnterior=Y");

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
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irBuscar: Inicio");
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
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		Logger log = null;
		ActionForward actionForward = null;
		HttpSession session = request.getSession();
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- CONSULTA DE OCAP_GRADO --------- ");
			ActionErrors errores = new ActionErrors();
			request.setAttribute("primeraConsulta", "false");

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPGradoForm) request.getSession().getAttribute("OCAPGradoFormBuscador");
				request.setAttribute("OCAPGradoForm", form);
			} else {
				request.getSession().setAttribute("OCAPGradoFormBuscador", form);
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
			OCAPGradoForm formulario = (OCAPGradoForm) form;

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPGradoLN oCAPGradoLN = new OCAPGradoLN(jcylUsuario);

			long cGradoId = 0L;
			String dDescripcion = null;
			String dNombre = null;
			String fCreacion = null;
			String fModificacion = null;
			String nAniosejercicio = null;

			cGradoId = formulario.getCGrado_id();

			if ((formulario.getDDescripcion() != null) && (!formulario.getDDescripcion().equals(""))) {
				dDescripcion = formulario.getDDescripcion();
			}

			if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
				dNombre = formulario.getDNombre();
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

			if ((formulario.getNAniosejercicio() != null) && (!formulario.getNAniosejercicio().equals(""))) {
				nAniosejercicio = formulario.getNAniosejercicio();
			}

			Collection elementos = oCAPGradoLN.consultaOCAPGrado(cGradoId, dNombre, dDescripcion, fCreacion,
					fModificacion, nAniosejercicio, primerRegistro, registrosPorPagina);
			if (elementos != null) {
				OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " grado para la consulta");
				if (elementos.size() == 0) {
					request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
					request.removeAttribute("conDatos");
					request.getSession().removeAttribute("paginaOCAPGradoOT");
				} else {
					int nListado = 0;
					nListado = oCAPGradoLN.listadoOCAPGradoCuenta(cGradoId, dNombre, dDescripcion, fCreacion,
							fModificacion, nAniosejercicio);
					request.removeAttribute("sinDatos");
					Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
					pagina.setElementos(elementos);
					pagina.setRegistroActual(primerRegistro);
					pagina.setNRegistros(nListado);
					pagina.setRegistrosPorPagina(registrosPorPagina);
					request.setAttribute("paginaOCAPGradoOT", pagina);
					request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
					request.removeAttribute("menu");
					request.getSession().removeAttribute("menu");
				}
			} else {
				request.setAttribute("errorConsultando", "Error consultando en la base de datos");
			}
			OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAPGrado ------- ");
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
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- DETALLE OCAP_GRADO --------- ");
			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPGradoLN oCAPGradoLN = new OCAPGradoLN(jcylUsuario);
			OCAPGradoOT datos = new OCAPGradoOT();

			OCAPGradoForm formulario = (OCAPGradoForm) form;

			String cGradoIdS = request.getParameter("cGradoIdS");
			long cGradoId;
			if ((cGradoIdS != null) && (!cGradoIdS.equals(""))) {
				cGradoId = Long.parseLong(cGradoIdS);
				OCAPConfigApp.logger.info("cGradoId: " + cGradoId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			datos = oCAPGradoLN.buscarOCAPGrado(cGradoId);
			if (datos.getCGradoId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPGradoOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosDetalle");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				formulario.setCGrado_id(datos.getCGradoId());
				formulario.setDDescripcion(datos.getDDescripcion());
				formulario.setDNombre(datos.getDNombre());
				formulario.setNAniosejercicio(String.valueOf(datos.getNAniosejercicio()));

				OCAPConfigApp.logger.info("Se ha encontrado OCAPGrado");
				request.getSession().setAttribute("conDatosDetalle", "");
				request.getSession().removeAttribute("sinDatosDetalle");
				request.getSession().setAttribute("OCAPGradoOT", datos);
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
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Inicio");

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
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- ALTA OCAP_GRADO --------- ");

			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPGradoLN oCAPGradoLN = new OCAPGradoLN(jcylUsuario);
			OCAPGradoOT datos = new OCAPGradoOT();

			OCAPGradoForm formulario = (OCAPGradoForm) form;
			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

			if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
				datos.setDNombre(formulario.getDNombre());
				OCAPConfigApp.logger.info("dNombre: " + datos.getDNombre());
			}
			if ((formulario.getDNombre() == null) || (formulario.getDNombre().equals(""))) {
				mensajeError = "El campo Nombre es obligatorio.";
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

			if ((formulario.getNAniosejercicio() != null) && (!formulario.getNAniosejercicio().equals(""))) {
				datos.setNAniosejercicio(Integer.valueOf(formulario.getNAniosejercicio()));
				OCAPConfigApp.logger.info("nAniosejercicio : " + datos.getNAniosejercicio());
			} else {
				datos.setNAniosejercicio(new Integer(0));
			}
			datos.setACreadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
			int result = oCAPGradoLN.altaOCAPGrado(datos);
			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
				request.setAttribute("rutaVuelta", "OCAPGrado.do?accion=irInsertar");
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA OCAPGrado --------- ");
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
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irEditar: Inicio");
			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPGradoLN oCAPGradoLN = new OCAPGradoLN(jcylUsuario);
			OCAPGradoOT datos = new OCAPGradoOT();
			OCAPGradoForm formulario = (OCAPGradoForm) form;

			String cGradoIdS = request.getParameter("cGradoIdS");
			long cGradoId;
			if ((cGradoIdS != null) && (!cGradoIdS.equals(""))) {
				cGradoId = Long.parseLong(cGradoIdS);
				OCAPConfigApp.logger.info("cGradoId: " + cGradoId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			datos = oCAPGradoLN.buscarOCAPGrado(cGradoId);

			if (datos.getCGradoId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPGradoOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosEdicion");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				OCAPConfigApp.logger.info("Se ha encontrado OCAPGradoOT");
				request.getSession().setAttribute("conDatosEdicion", "");
				request.getSession().removeAttribute("sinDatosEdicion");
				request.setAttribute("OCAPGradoOT", datos);

				formulario.setCGrado_id(datos.getCGradoId());
				formulario.setDDescripcion(datos.getDDescripcion());
				formulario.setDNombre(datos.getDNombre());
				formulario.setNAniosejercicio(String.valueOf(datos.getNAniosejercicio()));

				sig = "irModificar";
				request.setAttribute("tipoAccion", Constantes.MODIFICAR);
			}
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
		}
		ActionForward actionForward = mapping.findForward(sig);
		OCAPConfigApp.logger.info("forward--> " + actionForward.getPath());
		OCAPConfigApp.logger.info("...........se sale del Action");
		OCAPConfigApp.logger.info(getClass().getName() + " irEditar: Fin");

		return actionForward;
	}

	public ActionForward grabar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- GRABAR OCAP_GRADO --------- ");
			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPGradoLN oCAPGradoLN = new OCAPGradoLN(jcylUsuario);
			OCAPGradoOT datos = new OCAPGradoOT();

			OCAPGradoForm formulario = (OCAPGradoForm) form;

			if (formulario.getCGrado_id() != 0L) {
				OCAPConfigApp.logger.info("cGradoId: " + formulario.getCGrado_id());
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			datos.setCGradoId(formulario.getCGrado_id());

			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

			if ((formulario.getDDescripcion() != null) && (!formulario.getDDescripcion().equals(""))) {
				datos.setDDescripcion(formulario.getDDescripcion());
				OCAPConfigApp.logger.info("dDescripcion: " + datos.getDDescripcion());
			}

			if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
				datos.setDNombre(formulario.getDNombre());
				OCAPConfigApp.logger.info("dNombre: " + datos.getDNombre());
			}

			if ((formulario.getDNombre() == null) || (formulario.getDNombre().equals(""))) {
				mensajeError = "El campo Nombre es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getDDescripcion() == null) || (formulario.getDDescripcion().equals(""))) {
				mensajeError = "El campo Descripción es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getNAniosejercicio() != null) && (!formulario.getNAniosejercicio().equals(""))) {
				datos.setNAniosejercicio(Integer.valueOf(formulario.getNAniosejercicio()));
				OCAPConfigApp.logger.info("nAniosejercicio : " + datos.getNAniosejercicio());
			} else {
				datos.setNAniosejercicio(new Integer(0));
			}

			int result = oCAPGradoLN.modificacionOCAPGrado(datos);

			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha modificado con éxito");

				request.setAttribute("rutaVuelta", "OCAPGrado.do?accion=detalle&cGradoIdS=" + datos.getCGradoId());

				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("---------- FIN GRABAR MODIFICACION OCAPGrado --------- ");
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
