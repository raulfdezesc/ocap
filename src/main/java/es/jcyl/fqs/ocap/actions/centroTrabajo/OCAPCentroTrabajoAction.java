package es.jcyl.fqs.ocap.actions.centroTrabajo;

import es.jcyl.fqs.ocap.actionforms.centroTrabajo.OCAPCentroTrabajoForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.centroTrabajo.OCAPCentroTrabajoLN;
import es.jcyl.fqs.ocap.ln.gerencias.OCAPGerenciasLN;
import es.jcyl.fqs.ocap.ot.centroTrabajo.OCAPCentroTrabajoOT;
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

public class OCAPCentroTrabajoAction extends OCAPGenericAction {
	public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- BAJA DE OCAP_CENTROSTRABAJO --------- ");
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPCentroTrabajoLN oCAPCentroTrabajoLN = new OCAPCentroTrabajoLN(jcylUsuario);

			String cCentrotrabajoIdS = request.getParameter("cCentrotrabajoIdS");
			int cCentrotrabajoId;
			if ((cCentrotrabajoIdS != null) && (!cCentrotrabajoIdS.equals(""))) {
				cCentrotrabajoId = Integer.parseInt(cCentrotrabajoIdS);
				OCAPConfigApp.logger.info("cCentrotrabajoId: " + cCentrotrabajoId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			int result = oCAPCentroTrabajoLN.bajaOCAPCentroTrabajo(cCentrotrabajoId);

			if (result == 0) {
				OCAPConfigApp.logger.info("Se ha producido un error al borrar el registro");
				sig = "error";
				request.setAttribute("mensaje", "Se ha producido un error al borrar el registro");
			} else {
				OCAPConfigApp.logger.info("El registro se ha eliminado con éxito");
				request.setAttribute("mensaje", "El registro se ha eliminado con éxito");
				request.setAttribute("rutaVuelta", "OCAPCentroTrabajo.do?accion=buscar&recuperarBusquedaAnterior=Y");

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

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			Collection listadoGer = gerenciasLN.listadoOCAPGerencias();
			Object[] listadoGerencia = listadoGer.toArray();

			session.setAttribute(Constantes.COMBO_GERENCIA, utilidades.ArrayObjectToArrayList(listadoGerencia));
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
			OCAPConfigApp.logger.info("---------- CONSULTA DE OCAP_CENTROSTRABAJO --------- ");
			request.setAttribute("primeraConsulta", "false");

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPCentroTrabajoForm) request.getSession().getAttribute("OCAPCentroTrabajoFormBuscador");
				request.setAttribute("OCAPCentroTrabajoForm", form);
			} else {
				request.getSession().setAttribute("OCAPCentroTrabajoFormBuscador", form);
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
			OCAPCentroTrabajoForm formulario = (OCAPCentroTrabajoForm) form;

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPCentroTrabajoLN oCAPCentroTrabajoLN = new OCAPCentroTrabajoLN(jcylUsuario);

			long cCentrotrabajoId = 0L;
			long cGerenciaId = 0L;
			String dDescripcion = null;
			String dNombre = null;
			String aObservaciones = null;
			String aDireccion = null;
			String aCodigopostal = null;
			String aTelefono = null;
			String aCiudad = null;
			String fCreacion = null;
			String fModificacion = null;

			cCentrotrabajoId = formulario.getCCentrotrabajo_id();

			cGerenciaId = formulario.getCGerencia_id();

			if ((formulario.getDDescripcion() != null) && (!formulario.getDDescripcion().equals(""))) {
				dDescripcion = formulario.getDDescripcion();
			}

			if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
				dNombre = formulario.getDNombre();
			}

			if ((formulario.getAObservaciones() != null) && (!formulario.getAObservaciones().equals(""))) {
				aObservaciones = formulario.getAObservaciones();
			}

			if ((formulario.getADireccion() != null) && (!formulario.getADireccion().equals(""))) {
				aDireccion = formulario.getADireccion();
			}

			if ((formulario.getACodigopostal() != null) && (!formulario.getACodigopostal().equals(""))) {
				aCodigopostal = formulario.getACodigopostal();
			}

			if ((formulario.getATelefono() != null) && (!formulario.getATelefono().equals(""))) {
				aTelefono = formulario.getATelefono();
			}

			if ((formulario.getACiudad() != null) && (!formulario.getACiudad().equals(""))) {
				aCiudad = formulario.getACiudad();
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

			Collection elementos = oCAPCentroTrabajoLN.consultaOCAPCentroTrabajo(cCentrotrabajoId, cGerenciaId, dNombre,
					dDescripcion, aObservaciones, aDireccion, aCodigopostal, aTelefono, aCiudad, fCreacion,
					fModificacion, primerRegistro, registrosPorPagina);

			if (elementos != null) {
				OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Personas para la consulta");
				if (elementos.size() == 0) {
					request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
					request.removeAttribute("conDatos");
					request.getSession().removeAttribute("paginaOCAPCentroTrabajoOT");
				} else {
					Object[] listadoelementos = elementos.toArray();

					OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);

					int reg = elementos.size();

					elementos.removeAll(elementos);

					for (int i = 0; i < reg; i++) {
						((OCAPCentroTrabajoOT) listadoelementos[i]).setDGerenciaNombre(gerenciasLN
								.buscarOCAPGerencias(((OCAPCentroTrabajoOT) listadoelementos[i]).getCGerenciaId())
								.getDNombre());

						elementos.add(listadoelementos[i]);
					}

					int nListado = 0;
					nListado = oCAPCentroTrabajoLN.listadoOCAPCentroTrabajoCuenta(cCentrotrabajoId, cGerenciaId,
							dNombre, dDescripcion, aObservaciones, aDireccion, aCodigopostal, aTelefono, aCiudad,
							fCreacion, fModificacion);
					request.removeAttribute("sinDatos");
					Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
					pagina.setElementos(elementos);
					pagina.setRegistroActual(primerRegistro);
					pagina.setNRegistros(nListado);
					pagina.setRegistrosPorPagina(registrosPorPagina);
					request.setAttribute("paginaOCAPCentroTrabajoOT", pagina);
					request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
					request.removeAttribute("menu");
					request.getSession().removeAttribute("menu");
				}
			} else {
				request.setAttribute("errorConsultando", "Error consultando en la base de datos");
			}
			OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAP_CENTROSTRABAJO ------- ");
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
			OCAPConfigApp.logger.info("---------- DETALLE OCAP_CENTROSTRABAJO --------- ");
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPCentroTrabajoLN oCAPCentroTrabajoLN = new OCAPCentroTrabajoLN(jcylUsuario);
			OCAPCentroTrabajoOT datos = new OCAPCentroTrabajoOT();

			OCAPCentroTrabajoForm formulario = (OCAPCentroTrabajoForm) form;

			String cCentrotrabajoIdS = request.getParameter("cCentrotrabajoIdS");
			long cCentrotrabajoId;
			if ((cCentrotrabajoIdS != null) && (!cCentrotrabajoIdS.equals(""))) {
				cCentrotrabajoId = Long.parseLong(cCentrotrabajoIdS);
				OCAPConfigApp.logger.info("cCentrotrabajoId: " + cCentrotrabajoId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			datos = oCAPCentroTrabajoLN.buscarOCAPCentroTrabajo(cCentrotrabajoId);

			if (datos.getCCentrotrabajoId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPCentroTrabajoOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosDetalle");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);

				formulario.setCCentrotrabajo_id(datos.getCCentrotrabajoId());
				formulario.setDGerencia_nombre(gerenciasLN.buscarOCAPGerencias(datos.getCGerenciaId()).getDNombre());
				formulario.setDNombre(datos.getDNombre());

				if (datos.getDDescripcion() == null)
					formulario.setDDescripcion("");
				else {
					formulario.setDDescripcion(datos.getDDescripcion());
				}
				if (datos.getAObservaciones() == null)
					formulario.setAObservaciones("");
				else {
					formulario.setAObservaciones(datos.getAObservaciones());
				}
				if (datos.getADireccion() == null)
					formulario.setADireccion("");
				else {
					formulario.setADireccion(datos.getADireccion());
				}
				if (datos.getACodigopostal() == null)
					formulario.setACodigopostal("");
				else {
					formulario.setACodigopostal(datos.getACodigopostal());
				}
				if (datos.getATelefono() == null)
					formulario.setATelefono("");
				else {
					formulario.setATelefono(datos.getATelefono());
				}
				if (datos.getACiudad() == null)
					formulario.setACiudad("");
				else {
					formulario.setACiudad(datos.getACiudad());
				}
				OCAPConfigApp.logger.info("Se ha encontrado OCAPCentroTrabajo");
				request.getSession().setAttribute("conDatosDetalle", "");
				request.getSession().removeAttribute("sinDatosDetalle");
				request.getSession().setAttribute("OCAPCentroTrabajoOT", datos);
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
			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			Collection listadoGer = gerenciasLN.listadoOCAPGerencias();
			Object[] listadoGerencia = listadoGer.toArray();

			session.setAttribute(Constantes.COMBO_GERENCIA, utilidades.ArrayObjectToArrayList(listadoGerencia));

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
			OCAPConfigApp.logger.info("---------- ALTA OCAP_CENTROSTRABAJO --------- ");

			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPCentroTrabajoLN oCAPCentroTrabajoLN = new OCAPCentroTrabajoLN(jcylUsuario);
			OCAPCentroTrabajoOT datos = new OCAPCentroTrabajoOT();

			OCAPCentroTrabajoForm formulario = (OCAPCentroTrabajoForm) form;
			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

			datos.setCGerenciaId(formulario.getCGerencia_id());
			OCAPConfigApp.logger.info("cGerenciaid : " + datos.getCGerenciaId());

			if (formulario.getCGerencia_id() == 0L) {
				mensajeError = "El campo Gerencia es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
				datos.setDNombre(formulario.getDNombre());
				OCAPConfigApp.logger.info("dNombre : " + datos.getDNombre());
			}
			if ((formulario.getDNombre() == null) || (formulario.getDNombre().equals(""))) {
				mensajeError = "El Centro es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getDDescripcion() != null) && (!formulario.getDDescripcion().equals(""))) {
				datos.setDDescripcion(formulario.getDDescripcion());
				OCAPConfigApp.logger.info("dDescripcion: " + datos.getDDescripcion());
			}

			if ((formulario.getAObservaciones() != null) && (!formulario.getAObservaciones().equals(""))) {
				datos.setAObservaciones(formulario.getAObservaciones());
				OCAPConfigApp.logger.info("dObservaciones: " + datos.getAObservaciones());
			}

			if ((formulario.getADireccion() != null) && (!formulario.getADireccion().equals(""))) {
				datos.setADireccion(formulario.getADireccion());
				OCAPConfigApp.logger.info("dDireccion: " + datos.getADireccion());
			}

			if ((formulario.getACodigopostal() != null) && (!formulario.getACodigopostal().equals(""))) {
				datos.setACodigopostal(formulario.getACodigopostal());
				OCAPConfigApp.logger.info("dCodigopostal: " + datos.getACodigopostal());
			}

			if ((formulario.getATelefono() != null) && (!formulario.getATelefono().equals(""))) {
				datos.setATelefono(formulario.getATelefono());
				OCAPConfigApp.logger.info("dTelefono: " + datos.getATelefono());
			}

			if ((formulario.getACiudad() != null) && (!formulario.getACiudad().equals(""))) {
				datos.setACiudad(formulario.getACiudad());
				OCAPConfigApp.logger.info("dCiudad " + datos.getACiudad());
			}
			datos.setACreadoPor(
					((JCYLUsuario) request.getSession().getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO)).getUsuario().getC_usr_id());
			int result = oCAPCentroTrabajoLN.altaOCAPCentroTrabajo(datos);
			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
				request.setAttribute("rutaVuelta", "OCAPCentroTrabajo.do?accion=irInsertar");
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA OCAPCentroTrabajo --------- ");
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

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPCentroTrabajoLN oCAPCentroTrabajoLN = new OCAPCentroTrabajoLN(jcylUsuario);
			OCAPCentroTrabajoOT datos = new OCAPCentroTrabajoOT();
			OCAPCentroTrabajoForm formulario = (OCAPCentroTrabajoForm) form;

			String cCentrotrabajoIdS = request.getParameter("cCentrotrabajoIdS");
			long cCentrotrabajoId;
			if ((cCentrotrabajoIdS != null) && (!cCentrotrabajoIdS.equals(""))) {
				cCentrotrabajoId = Long.parseLong(cCentrotrabajoIdS);
				OCAPConfigApp.logger.info("cCentrotrabajoId: " + cCentrotrabajoId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			datos = oCAPCentroTrabajoLN.buscarOCAPCentroTrabajo(cCentrotrabajoId);

			if (datos.getCCentrotrabajoId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPCentroTrabajoOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosEdicion");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				OCAPConfigApp.logger.info("Se ha encontrado OCAPCentroTrabajoOT");
				request.getSession().setAttribute("conDatosEdicion", "");
				request.getSession().removeAttribute("sinDatosEdicion");
				request.setAttribute("OCAPCentroTrabajoOT", datos);

				formulario.setCCentrotrabajo_id(datos.getCCentrotrabajoId());
				formulario.setCGerencia_id(datos.getCGerenciaId());
				formulario.setDNombre(datos.getDNombre());

				if (datos.getDDescripcion() == null)
					formulario.setDDescripcion("");
				else {
					formulario.setDDescripcion(datos.getDDescripcion());
				}
				if (datos.getAObservaciones() == null)
					formulario.setAObservaciones("");
				else {
					formulario.setAObservaciones(datos.getAObservaciones());
				}
				if (datos.getADireccion() == null)
					formulario.setADireccion("");
				else {
					formulario.setADireccion(datos.getADireccion());
				}
				if (datos.getACodigopostal() == null)
					formulario.setACodigopostal("");
				else {
					formulario.setACodigopostal(datos.getACodigopostal());
				}
				if (datos.getATelefono() == null)
					formulario.setATelefono("");
				else {
					formulario.setATelefono(datos.getATelefono());
				}
				if (datos.getACiudad() == null)
					formulario.setACiudad("");
				else {
					formulario.setACiudad(datos.getACiudad());
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
			OCAPConfigApp.logger.info("---------- GRABAR OCAP_CENTROSTRABAJO --------- ");
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPCentroTrabajoLN oCAPCentroTrabajoLN = new OCAPCentroTrabajoLN(jcylUsuario);
			OCAPCentroTrabajoOT datos = new OCAPCentroTrabajoOT();

			OCAPCentroTrabajoForm formulario = (OCAPCentroTrabajoForm) form;

			if (formulario.getCCentrotrabajo_id() != 0L) {
				OCAPConfigApp.logger.info("cCentrotrabajoId: " + formulario.getCCentrotrabajo_id());
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			datos.setCCentrotrabajoId(formulario.getCCentrotrabajo_id());
			datos.setCGerenciaId(formulario.getCGerencia_id());

			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

			if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
				datos.setDNombre(formulario.getDNombre());
				OCAPConfigApp.logger.info("dNombre : " + datos.getDNombre());
			}

			if ((formulario.getDDescripcion() != null) && (!formulario.getDDescripcion().equals(""))) {
				datos.setDDescripcion(formulario.getDDescripcion());
				OCAPConfigApp.logger.info("dDescripcion: " + datos.getDDescripcion());
			}

			if ((formulario.getAObservaciones() != null) && (!formulario.getAObservaciones().equals(""))) {
				datos.setAObservaciones(formulario.getAObservaciones());
				OCAPConfigApp.logger.info("aObservaciones: " + datos.getAObservaciones());
			}

			if ((formulario.getADireccion() != null) && (!formulario.getADireccion().equals(""))) {
				datos.setADireccion(formulario.getADireccion());
				OCAPConfigApp.logger.info("aDireccion: " + datos.getADireccion());
			}

			if ((formulario.getACodigopostal() != null) && (!formulario.getACodigopostal().equals(""))) {
				datos.setACodigopostal(formulario.getACodigopostal());
				OCAPConfigApp.logger.info("aCodigopostal: " + datos.getACodigopostal());
			}

			if ((formulario.getATelefono() != null) && (!formulario.getATelefono().equals(""))) {
				datos.setATelefono(formulario.getATelefono());
				OCAPConfigApp.logger.info("aTelefono: " + datos.getATelefono());
			}

			if ((formulario.getACiudad() != null) && (!formulario.getACiudad().equals(""))) {
				datos.setACiudad(formulario.getACiudad());
				OCAPConfigApp.logger.info("aCiudad: " + datos.getACiudad());
			}

			if (formulario.getCGerencia_id() == 0L) {
				mensajeError = "El campo Nombre de Gerencia es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getDNombre() == null) || (formulario.getDNombre().equals(""))) {
				mensajeError = "El campo Nombre de Centro es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			int result = oCAPCentroTrabajoLN.modificacionOCAPCentroTrabajo(datos);

			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha modificado con éxito");
				request.setAttribute("rutaVuelta",
						"OCAPCentroTrabajo.do?accion=detalle&cCentrotrabajoIdS=" + datos.getCCentrotrabajoId());

				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");

				sig = "error";
			}
			OCAPConfigApp.logger.info("---------- FIN GRABAR MODIFICACION OCAPCentroTrabajo --------- ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";
			request.setAttribute("mensaje", mensajeError);
		}

		return mapping.findForward(sig);
	}
}
