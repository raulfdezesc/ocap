package es.jcyl.fqs.ocap.actions.areas;

import es.jcyl.fqs.ocap.actionforms.areas.OCAPAreasForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.areas.OCAPAreasLN;
import es.jcyl.fqs.ocap.ot.areas.OCAPAreasOT;
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

public class OCAPAreasAction extends OCAPGenericAction {
	public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- BAJA DE OCAP_Areas --------- ");
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPAreasLN oCAPAreasLN = new OCAPAreasLN(jcylUsuario);

			String cAreasIdS = request.getParameter("cAreaIdS");
			int cAreasId;
			if ((cAreasIdS != null) && (!cAreasIdS.equals(""))) {
				cAreasId = Integer.parseInt(cAreasIdS);
				OCAPConfigApp.logger.info("cAreasId: " + cAreasId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			int result = oCAPAreasLN.bajaOCAPAreas(cAreasId);

			if (result == 0) {
				OCAPConfigApp.logger.info("Se ha producido un error al borrar el registro");
				sig = "error";
				request.setAttribute("mensaje", "Se ha producido un error al borrar el registro");
			} else {
				OCAPConfigApp.logger.info("El registro se ha eliminado con éxito");
				request.setAttribute("mensaje", "El registro se ha eliminado con éxito");
				request.setAttribute("rutaVuelta", "OCAPAreas.do?accion=buscar&recuperarBusquedaAnterior=Y");

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
		String mensajeError = "Se ha producido un error";
		HttpSession session = request.getSession();
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- CONSULTA DE OCAP_Areas --------- ");
			request.setAttribute("primeraConsulta", "false");

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPAreasForm) request.getSession().getAttribute("OCAPAreasFormBuscador");
				request.setAttribute("OCAPAreasForm", form);
			} else {
				request.getSession().setAttribute("OCAPAreasFormBuscador", form);
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
			OCAPAreasForm formulario = (OCAPAreasForm) form;

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPAreasLN oCAPAreasLN = new OCAPAreasLN(jcylUsuario);

			long cAreaId = 0L;
			String dDescripcion = null;
			String dNombre = null;
			String dObservaciones = null;
			String fCreacion = null;
			String fModificacion = null;

			cAreaId = formulario.getCArea_id();

			if ((formulario.getDDescripcion() != null) && (!formulario.getDDescripcion().equals(""))) {
				dDescripcion = formulario.getDDescripcion();
			}

			if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
				dNombre = formulario.getDNombre();
			}

			if ((formulario.getDObservaciones() != null) && (!formulario.getDObservaciones().equals(""))) {
				dObservaciones = formulario.getDObservaciones();
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

			Collection elementos = oCAPAreasLN.consultaOCAPAreas(cAreaId, dNombre, dDescripcion, dObservaciones,
					fCreacion, fModificacion, primerRegistro, registrosPorPagina);
			if (elementos != null) {
				OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Areas para la consulta");
				if (elementos.size() == 0) {
					request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
					request.removeAttribute("conDatos");
					request.getSession().removeAttribute("paginaOCAPAreaOT");
				} else {
					int nListado = 0;
					nListado = oCAPAreasLN.listadoOCAPAreasCuenta(cAreaId, dNombre, dDescripcion, dObservaciones,
							fCreacion, fModificacion);
					request.removeAttribute("sinDatos");
					Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
					pagina.setElementos(elementos);
					pagina.setRegistroActual(primerRegistro);
					pagina.setNRegistros(nListado);
					pagina.setRegistrosPorPagina(registrosPorPagina);
					request.setAttribute("paginaOCAPAreaOT", pagina);
					request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
					request.removeAttribute("menu");
					request.getSession().removeAttribute("menu");
				}
			} else {
				request.setAttribute("errorConsultando", "Error consultando en la base de datos");
			}
			OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAPAreas ------- ");
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
			OCAPConfigApp.logger.info("---------- DETALLE OCAP_Areas --------- ");
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPAreasLN oCAPAreasLN = new OCAPAreasLN(jcylUsuario);
			OCAPAreasOT datos = new OCAPAreasOT();

			OCAPAreasForm formulario = (OCAPAreasForm) form;

			String cAreaIdS = request.getParameter("cAreaIdS");
			long cAreaId;
			if ((cAreaIdS != null) && (!cAreaIdS.equals(""))) {
				cAreaId = Long.parseLong(cAreaIdS);
				OCAPConfigApp.logger.info("cAreaId: " + cAreaId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			datos = oCAPAreasLN.buscarOCAPAreas(cAreaId);

			if (datos.getCAreaId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPAreasOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosDetalle");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				formulario.setCArea_id(datos.getCAreaId());

				if (datos.getDDescripcion() == null)
					formulario.setDDescripcion("");
				else {
					formulario.setDDescripcion(datos.getDDescripcion());
				}
				formulario.setDNombre(datos.getDNombre());

				if (datos.getDObservaciones() == null)
					formulario.setDObservaciones("");
				else {
					formulario.setDObservaciones(datos.getDObservaciones());
				}
				OCAPConfigApp.logger.info("Se ha encontrado OCAPAreas");
				request.getSession().setAttribute("conDatosDetalle", "");
				request.getSession().removeAttribute("sinDatosDetalle");
				request.getSession().setAttribute("OCAPAreasOT", datos);
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
			OCAPConfigApp.logger.info("---------- ALTA OCAP_Areas --------- ");

			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPAreasLN oCAPAreasLN = new OCAPAreasLN(jcylUsuario);
			OCAPAreasOT datos = new OCAPAreasOT();

			OCAPAreasForm formulario = (OCAPAreasForm) form;
			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

			if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
				datos.setDNombre(formulario.getDNombre());
				OCAPConfigApp.logger.info("dNombre : " + datos.getDNombre());
			}

			if ((formulario.getDNombre() == null) || (formulario.getDNombre().equals(""))) {
				mensajeError = "El campo Nombre de Area es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getDDescripcion() != null) && (!formulario.getDDescripcion().equals(""))) {
				datos.setDDescripcion(formulario.getDDescripcion());
				OCAPConfigApp.logger.info("dDescripcion: " + datos.getDDescripcion());
			}

			if ((formulario.getDObservaciones() != null) && (!formulario.getDObservaciones().equals(""))) {
				datos.setDObservaciones(formulario.getDObservaciones());
				OCAPConfigApp.logger.info("dObservaciones: " + datos.getDObservaciones());
			}
			datos.setACreadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
			int result = oCAPAreasLN.altaOCAPAreas(datos);
			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
				request.setAttribute("rutaVuelta", "OCAPAreas.do?accion=irInsertar");
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA OCAPAreas --------- ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";
			request.setAttribute("mensaje", mensajeError);
		}

		return mapping.findForward(sig);
	}

	public ActionForward irEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irEditar: Inicio");
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPAreasLN oCAPAreasLN = new OCAPAreasLN(jcylUsuario);
			OCAPAreasOT datos = new OCAPAreasOT();
			OCAPAreasForm formulario = (OCAPAreasForm) form;

			String cAreaIdS = request.getParameter("cAreaIdS");
			long cAreaId;
			if ((cAreaIdS != null) && (!cAreaIdS.equals(""))) {
				cAreaId = Long.parseLong(cAreaIdS);
				OCAPConfigApp.logger.info("cAreaId: " + cAreaId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			datos = oCAPAreasLN.buscarOCAPAreas(cAreaId);

			if (datos.getCAreaId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPAreasOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosEdicion");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				OCAPConfigApp.logger.info("Se ha encontrado OCAPAreasOT");
				request.getSession().setAttribute("conDatosEdicion", "");
				request.getSession().removeAttribute("sinDatosEdicion");
				request.setAttribute("OCAPAreasOT", datos);

				formulario.setCArea_id(datos.getCAreaId());

				if (datos.getDDescripcion() == null)
					formulario.setDDescripcion("");
				else {
					formulario.setDDescripcion(datos.getDDescripcion());
				}
				formulario.setDNombre(datos.getDNombre());

				if (datos.getDObservaciones() == null)
					formulario.setDObservaciones("");
				else {
					formulario.setDObservaciones(datos.getDObservaciones());
				}
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
			OCAPConfigApp.logger.info("---------- GRABAR OCAP_Areas --------- ");
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPAreasLN oCAPAreasLN = new OCAPAreasLN(jcylUsuario);
			OCAPAreasOT datos = new OCAPAreasOT();

			OCAPAreasForm formulario = (OCAPAreasForm) form;

			if (formulario.getCArea_id() != 0L) {
				OCAPConfigApp.logger.info("cAreaId: " + formulario.getCArea_id());
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			datos.setCAreaId(formulario.getCArea_id());
			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
			if ((formulario.getDDescripcion() != null) && (!formulario.getDDescripcion().equals(""))) {
				datos.setDDescripcion(formulario.getDDescripcion());
				OCAPConfigApp.logger.info("dDescripcion: " + datos.getDDescripcion());
			}

			if ((formulario.getDObservaciones() != null) && (!formulario.getDObservaciones().equals(""))) {
				datos.setDObservaciones(formulario.getDObservaciones());
				OCAPConfigApp.logger.info("dObservaciones: " + datos.getDObservaciones());
			}

			if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
				datos.setDNombre(formulario.getDNombre());
				OCAPConfigApp.logger.info("dNombre : " + datos.getDNombre());
			}
			if ((formulario.getDNombre() == null) || (formulario.getDNombre().equals(""))) {
				mensajeError = "El campo Nombre de Area es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			int result = oCAPAreasLN.modificacionOCAPAreas(datos);

			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha modificado con éxito");
				request.setAttribute("rutaVuelta", "OCAPAreas.do?accion=detalle&cAreaIdS=" + datos.getCAreaId());
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("---------- FIN GRABAR MODIFICACION OCAPAreas --------- ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";
			request.setAttribute("mensaje", mensajeError);
		}

		return mapping.findForward(sig);
	}
}
