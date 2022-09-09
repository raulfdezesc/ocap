package es.jcyl.fqs.ocap.actions.mensajesMc;

import es.jcyl.fqs.ocap.actionforms.mensajesMc.OCAPMensajesMcForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.defMeritosCurriculares.OCAPDefMeritoscurricularesLN;
import es.jcyl.fqs.ocap.ln.estatutario.OCAPEstatutarioLN;
import es.jcyl.fqs.ocap.ln.mensajesMc.OCAPMensajesMcLN;
import es.jcyl.fqs.ocap.ot.mensajesMc.OCAPMensajesMcOT;
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

public class OCAPMensajesMcAction extends OCAPGenericAction {
	public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		Logger log = null;
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- BAJA DE OCAP_MENSAJESMC --------- ");
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPMensajesMcLN oCAPMensajesMcLN = new OCAPMensajesMcLN(jcylUsuario);

			String cMensajeIdS = request.getParameter("cMensajeIdS");
			int cMensajeId;
			if ((cMensajeIdS != null) && (!cMensajeIdS.equals(""))) {
				cMensajeId = Integer.parseInt(cMensajeIdS);
				OCAPConfigApp.logger.info("cMensajeId: " + cMensajeId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			int result = oCAPMensajesMcLN.bajaOCAPMensajesMc(cMensajeId);

			if (result == 0) {
				OCAPConfigApp.logger.info("Se ha producido un error al borrar el registro");
				sig = "error";
				request.setAttribute("mensaje", "Se ha producido un error al borrar el registro");
			} else {
				OCAPConfigApp.logger.info("El registro se ha eliminado con éxito");
				request.setAttribute("mensaje", "El registro se ha eliminado con éxito");
				request.setAttribute("rutaVuelta", "OCAPMensajesMc.do?accion=buscar&recuperarBusquedaAnterior=Y");

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

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPDefMeritoscurricularesLN meritosLN = new OCAPDefMeritoscurricularesLN(jcylUsuario);
			Collection listadoMer = meritosLN.listadoOCAPDefMeritoscurriculares();
			Object[] listadoMeritos = listadoMer.toArray();

			OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
			Collection listadoEstat = estatutarioLN.listadoOCAPEstatutario();
			Object[] listadoEstatuto = listadoEstat.toArray();

			session.setAttribute(Constantes.COMBO_ESTATUTARIO, utilidades.ArrayObjectToArrayList(listadoEstatuto));
			session.setAttribute(Constantes.COMBO_DEF_MERITOSCURRICULARES, utilidades.ArrayObjectToArrayList(listadoMeritos));
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
			OCAPConfigApp.logger.info("---------- CONSULTA DE OCAP_MENSAJESMC --------- ");
			request.setAttribute("primeraConsulta", "false");

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPMensajesMcForm) request.getSession().getAttribute("OCAPMensajesMcFormBuscador");
				request.setAttribute("OCAPMensajesMcForm", form);
			} else {
				request.getSession().setAttribute("OCAPMensajesMcFormBuscador", form);
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
			OCAPMensajesMcForm formulario = (OCAPMensajesMcForm) form;

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPMensajesMcLN oCAPMensajesMcLN = new OCAPMensajesMcLN(jcylUsuario);

			long cMensajeId = 0L;
			long cDefmeritoId = 0L;
			long cEstatutId = 0L;
			String dDescripcion = null;
			String fCreacion = null;
			String fModificacion = null;

			cMensajeId = formulario.getCMensaje_id();
			cDefmeritoId = formulario.getCDefmerito_id();
			cEstatutId = formulario.getCEstatut_id();

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

			Collection elementos = oCAPMensajesMcLN.consultaOCAPMensajesMc(cMensajeId, cDefmeritoId, cEstatutId,
					dDescripcion, fCreacion, fModificacion, primerRegistro, registrosPorPagina);

			if (elementos != null) {
				OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Personas para la consulta");
				if (elementos.size() == 0) {
					request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
					request.removeAttribute("conDatos");
					request.getSession().removeAttribute("paginaOCAPMensajesMcOT");
				} else {
					Object[] listadoelementos = elementos.toArray();

					OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
					OCAPDefMeritoscurricularesLN meritosLN = new OCAPDefMeritoscurricularesLN(jcylUsuario);

					int reg = elementos.size();

					elementos.removeAll(elementos);

					for (int i = 0; i < reg; i++) {
						((OCAPMensajesMcOT) listadoelementos[i]).setDEstatutNombre(estatutarioLN
								.buscarOCAPEstatutario(((OCAPMensajesMcOT) listadoelementos[i]).getCEstatutId())
								.getDNombre());
						((OCAPMensajesMcOT) listadoelementos[i])
								.setDDefmeritoNombre(meritosLN
										.buscarOCAPDefMeritoscurriculares(
												((OCAPMensajesMcOT) listadoelementos[i]).getCDefmeritoId())
										.getDNombre());

						elementos.add(listadoelementos[i]);
					}

					int nListado = 0;
					nListado = oCAPMensajesMcLN.listadoOCAPMensajesMcCuenta(cMensajeId, cDefmeritoId, cEstatutId,
							dDescripcion, fCreacion, fModificacion);
					request.removeAttribute("sinDatos");
					Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
					pagina.setElementos(elementos);
					pagina.setRegistroActual(primerRegistro);
					pagina.setNRegistros(nListado);
					pagina.setRegistrosPorPagina(registrosPorPagina);
					request.setAttribute("paginaOCAPMensajesMcOT", pagina);
					request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
					request.removeAttribute("menu");
					request.getSession().removeAttribute("menu");
				}
			} else {
				request.setAttribute("errorConsultando", "Error consultando en la base de datos");
			}
			OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAP_MENSAJESMC ------- ");
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
			OCAPConfigApp.logger.info("---------- DETALLE OCAP_MENSAJESMC --------- ");
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPMensajesMcLN oCAPMensajesMcLN = new OCAPMensajesMcLN(jcylUsuario);
			OCAPMensajesMcOT datos = new OCAPMensajesMcOT();

			OCAPMensajesMcForm formulario = (OCAPMensajesMcForm) form;

			String cMensajeIdS = request.getParameter("cMensajeIdS");
			long cMensajeId;
			if ((cMensajeIdS != null) && (!cMensajeIdS.equals(""))) {
				cMensajeId = Long.parseLong(cMensajeIdS);
				OCAPConfigApp.logger.info("cMensajeId: " + cMensajeId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			datos = oCAPMensajesMcLN.buscarOCAPMensajesMc(cMensajeId);

			if (datos.getCMensajeId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPMensajesMcOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosDetalle");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
				OCAPDefMeritoscurricularesLN meritosLN = new OCAPDefMeritoscurricularesLN(jcylUsuario);

				formulario.setCMensaje_id(datos.getCMensajeId());
				formulario.setDEstatut_nombre(estatutarioLN.buscarOCAPEstatutario(datos.getCEstatutId()).getDNombre());
				formulario.setDDefmerito_nombre(
						meritosLN.buscarOCAPDefMeritoscurriculares(datos.getCDefmeritoId()).getDNombre());
				formulario.setDDescripcion(datos.getDDescripcion());

				OCAPConfigApp.logger.info("Se ha encontrado OCAPMensajesMc");
				request.getSession().setAttribute("conDatosDetalle", "");
				request.getSession().removeAttribute("sinDatosDetalle");
				request.getSession().setAttribute("OCAPMensajesMcOT", datos);
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

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPDefMeritoscurricularesLN meritosLN = new OCAPDefMeritoscurricularesLN(jcylUsuario);
			Collection listadoMer = meritosLN.listadoOCAPDefMeritoscurriculares();
			Object[] listadoMeritos = listadoMer.toArray();

			OCAPEstatutarioLN EstatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
			Collection listadoPers = EstatutarioLN.listadoOCAPEstatutario();
			Object[] listadoEstatuto = listadoPers.toArray();

			session.setAttribute(Constantes.COMBO_ESTATUTARIO, utilidades.ArrayObjectToArrayList(listadoEstatuto));
			session.setAttribute(Constantes.COMBO_DEF_MERITOSCURRICULARES, utilidades.ArrayObjectToArrayList(listadoMeritos));

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
			OCAPConfigApp.logger.info("---------- ALTA OCAP_MENSAJESMC --------- ");

			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPMensajesMcLN oCAPMensajesMcLN = new OCAPMensajesMcLN(jcylUsuario);
			OCAPMensajesMcOT datos = new OCAPMensajesMcOT();

			OCAPMensajesMcForm formulario = (OCAPMensajesMcForm) form;
			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

			datos.setCDefmeritoId(formulario.getCDefmerito_id());
			OCAPConfigApp.logger.info("cDefmeritoid : " + datos.getCDefmeritoId());

			if (formulario.getCDefmerito_id() == 0L) {
				mensajeError = "El campo Nombre de Merito es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setCEstatutId(formulario.getCEstatut_id());
			OCAPConfigApp.logger.info("cEstatutid : " + datos.getCEstatutId());

			if (formulario.getCEstatut_id() == 0L) {
				mensajeError = "El campo Nombre de Estatuto es obligatorio.";
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
			datos.setBBorrado(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
			int result = oCAPMensajesMcLN.altaOCAPMensajesMc(datos);
			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
				request.setAttribute("rutaVuelta", "OCAPMensajesMc.do?accion=irInsertar");
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA OCAPMensajesMc --------- ");
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
			OCAPMensajesMcLN oCAPMensajesMcLN = new OCAPMensajesMcLN(jcylUsuario);
			OCAPMensajesMcOT datos = new OCAPMensajesMcOT();
			OCAPMensajesMcForm formulario = (OCAPMensajesMcForm) form;

			String cMensajeIdS = request.getParameter("cMensajeIdS");
			long cMensajeId;
			if ((cMensajeIdS != null) && (!cMensajeIdS.equals(""))) {
				cMensajeId = Long.parseLong(cMensajeIdS);
				OCAPConfigApp.logger.info("cMensajeId: " + cMensajeId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			datos = oCAPMensajesMcLN.buscarOCAPMensajesMc(cMensajeId);

			if (datos.getCMensajeId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPMensajesMcOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosEdicion");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				OCAPConfigApp.logger.info("Se ha encontrado OCAPMensajesMcOT");
				request.getSession().setAttribute("conDatosEdicion", "");
				request.getSession().removeAttribute("sinDatosEdicion");
				request.setAttribute("OCAPMensajesMcOT", datos);

				formulario.setCMensaje_id(datos.getCMensajeId());
				formulario.setCDefmerito_id(datos.getCDefmeritoId());
				formulario.setCEstatut_id(datos.getCEstatutId());
				formulario.setDDescripcion(datos.getDDescripcion());

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
			OCAPConfigApp.logger.info("---------- GRABAR OCAP_MENSAJESMC --------- ");
			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPMensajesMcLN oCAPMensajesMcLN = new OCAPMensajesMcLN(jcylUsuario);
			OCAPMensajesMcOT datos = new OCAPMensajesMcOT();

			OCAPMensajesMcForm formulario = (OCAPMensajesMcForm) form;

			if (formulario.getCMensaje_id() != 0L) {
				OCAPConfigApp.logger.info("cMensajeId: " + formulario.getCMensaje_id());
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			datos.setCMensajeId(formulario.getCMensaje_id());
			datos.setCDefmeritoId(formulario.getCDefmerito_id());
			datos.setCEstatutId(formulario.getCEstatut_id());

			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

			if ((formulario.getDDescripcion() != null) && (!formulario.getDDescripcion().equals(""))) {
				datos.setDDescripcion(formulario.getDDescripcion());
				OCAPConfigApp.logger.info("dDescripcion: " + datos.getDDescripcion());
			}

			if (formulario.getCDefmerito_id() == 0L) {
				mensajeError = "El campo Nombre de Merito es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if (formulario.getCEstatut_id() == 0L) {
				mensajeError = "El campo Nombre de Estatuto es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getDDescripcion() == null) || (formulario.getDDescripcion().equals(""))) {
				mensajeError = "El campo Descripción es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			int result = oCAPMensajesMcLN.modificacionOCAPMensajesMc(datos);

			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha modificado con éxito");
				request.setAttribute("rutaVuelta",
						"OCAPMensajesMc.do?accion=detalle&cMensajeIdS=" + datos.getCMensajeId());

				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("---------- FIN GRABAR MODIFICACION OCAPMensajesMc --------- ");
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
