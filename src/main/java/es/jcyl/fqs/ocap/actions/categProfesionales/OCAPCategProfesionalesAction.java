package es.jcyl.fqs.ocap.actions.categProfesionales;

import es.jcyl.fqs.ocap.actionforms.categProfesionales.OCAPCategProfesionalesForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
import es.jcyl.fqs.ocap.ln.estatutario.OCAPEstatutarioLN;
import es.jcyl.fqs.ocap.ln.modalidades.OCAPModalidadesLN;
import es.jcyl.fqs.ocap.ot.categProfesionales.OCAPCategProfesionalesOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Pagina;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLUsuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OCAPCategProfesionalesAction extends OCAPGenericAction {
	public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- BAJA DE OCAP_CATEG_PROFESIONALES --------- ");
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPCategProfesionalesLN oCAPCategProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);

			String cProfesionalIdS = request.getParameter("cProfesionalIdS");
			int cProfesionalId;
			if ((cProfesionalIdS != null) && (!cProfesionalIdS.equals(""))) {
				cProfesionalId = Integer.parseInt(cProfesionalIdS);
				OCAPConfigApp.logger.info("cProfesionalId: " + cProfesionalId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			int result = oCAPCategProfesionalesLN.bajaOCAPCategProfesionales(cProfesionalId);

			if (result == 0) {
				OCAPConfigApp.logger.info("Se ha producido un error al borrar el registro");
				sig = "error";
				request.setAttribute("mensaje", "Se ha producido un error al borrar el registro");
			} else {
				OCAPConfigApp.logger.info("El registro se ha eliminado con éxito");
				request.setAttribute("mensaje", "El registro se ha eliminado con éxito");
				request.setAttribute("rutaVuelta",
						"OCAPCategProfesionales.do?accion=buscar&recuperarBusquedaAnterior=Y");

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
		ArrayList listadoModalidades = new ArrayList();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irBuscar: Inicio");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPEstatutarioLN EstatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
			Collection listadoEstatuts = EstatutarioLN.listadoOCAPEstatutario();
			Object[] listadoEstatut = listadoEstatuts.toArray();

			session.setAttribute(Constantes.COMBO_ESTATUTARIO, utilidades.ArrayObjectToArrayList(listadoEstatut));

			OCAPModalidadesLN modalidadesLN = new OCAPModalidadesLN(jcylUsuario);
			listadoModalidades = modalidadesLN.listarModalidades();
			session.setAttribute(Constantes.COMBO_MODALIDAD, listadoModalidades);
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
			OCAPConfigApp.logger.info("---------- CONSULTA DE OCAP_ESTATUTARIO --------- ");
			request.setAttribute("primeraConsulta", "false");

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPCategProfesionalesForm) request.getSession()
						.getAttribute("OCAPCategProfesionalesFormBuscador");
				request.setAttribute("OCAPCategProfesionalesForm", form);
			} else {
				request.getSession().setAttribute("OCAPCategProfesionalesFormBuscador", form);
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
			OCAPCategProfesionalesForm formulario = (OCAPCategProfesionalesForm) form;

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPCategProfesionalesLN oCAPCategProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);

			long cProfesionalId = 0L;
			long cEstatutId = 0L;
			String dDescripcion = null;
			String dNombre = null;
			String fCreacion = null;
			String fModificacion = null;
			long cModalidadId = 0L;

			cProfesionalId = formulario.getCProfesional_id();

			cEstatutId = formulario.getCEstatut_id();

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

			cModalidadId = formulario.getCModalidad_id();

			Collection elementos = oCAPCategProfesionalesLN.consultaOCAPCategProfesionales(cProfesionalId, dNombre,
					cEstatutId, 0L, dDescripcion, fCreacion, fModificacion, cModalidadId, primerRegistro,
					registrosPorPagina);

			if (elementos != null) {
				OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Profesionales para la consulta");
				if (elementos.size() == 0) {
					request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
					request.removeAttribute("conDatos");
					request.getSession().removeAttribute("paginaOCAPCategProfesionalesOT");
				} else {
					Object[] listadoelementos = elementos.toArray();

					OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);

					int reg = elementos.size();

					elementos.removeAll(elementos);

					for (int i = 0; i < reg; i++) {
						((OCAPCategProfesionalesOT) listadoelementos[i]).setDEstatutNombre(estatutarioLN
								.buscarOCAPEstatutario(((OCAPCategProfesionalesOT) listadoelementos[i]).getCEstatutId())
								.getDNombre());

						elementos.add(listadoelementos[i]);
					}

					int nListado = 0;
					nListado = oCAPCategProfesionalesLN.listadoOCAPCategProfesionalesCuenta(cProfesionalId, dNombre,
							cEstatutId, dDescripcion, fCreacion, fModificacion, cModalidadId);
					request.removeAttribute("sinDatos");
					Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
					pagina.setElementos(elementos);
					pagina.setRegistroActual(primerRegistro);
					pagina.setNRegistros(nListado);
					pagina.setRegistrosPorPagina(registrosPorPagina);
					request.setAttribute("paginaOCAPCategProfesionalesOT", pagina);
					request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
					request.removeAttribute("menu");
					request.getSession().removeAttribute("menu");
				}
			} else {
				request.setAttribute("errorConsultando", "Error consultando en la base de datos");
			}
			OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAP_CATEG_PROFESIONALES ------- ");
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
			OCAPConfigApp.logger.info("---------- DETALLE OCAP_CATEG_PROFESIONALES --------- ");
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPCategProfesionalesLN oCAPCategProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			OCAPCategProfesionalesOT datos = new OCAPCategProfesionalesOT();

			OCAPCategProfesionalesForm formulario = (OCAPCategProfesionalesForm) form;

			String cProfesionalIdS = request.getParameter("cProfesionalIdS");
			long cProfesionalId;
			if ((cProfesionalIdS != null) && (!cProfesionalIdS.equals(""))) {
				cProfesionalId = Long.parseLong(cProfesionalIdS);
				OCAPConfigApp.logger.info("cProfesionalId: " + cProfesionalId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			datos = oCAPCategProfesionalesLN.buscarOCAPCategProfesionales(cProfesionalId);

			if (datos.getCProfesionalId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPCategProfesionalesOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosDetalle");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				OCAPEstatutarioLN EstatutarioLN = new OCAPEstatutarioLN(jcylUsuario);

				formulario.setCProfesional_id(datos.getCProfesionalId());
				formulario.setDEstatut_nombre(EstatutarioLN.buscarOCAPEstatutario(datos.getCEstatutId()).getDNombre());

				OCAPModalidadesLN modalidadLN = new OCAPModalidadesLN(jcylUsuario);
				formulario.setDModalidad_nombre(modalidadLN.buscarModalidad(datos.getCModalidadId()).getDNombre());

				formulario.setDDescripcion(datos.getDDescripcion());

				if (datos.getDDescripcion() == null)
					formulario.setDDescripcion("");
				else {
					formulario.setDDescripcion(datos.getDDescripcion());
				}
				formulario.setDNombre(datos.getDNombre());

				OCAPConfigApp.logger.info("Se ha encontrado OCAPCategProfesionales");
				request.getSession().setAttribute("conDatosDetalle", "");
				request.getSession().removeAttribute("sinDatosDetalle");
				request.getSession().setAttribute("OCAPCategProfesionalesOT", datos);
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
		ArrayList listadoModalidades = new ArrayList();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Inicio");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
			Collection listadoPers = estatutarioLN.listadoOCAPEstatutario();
			Object[] listadoEstatut = listadoPers.toArray();

			session.setAttribute(Constantes.COMBO_ESTATUTARIO, utilidades.ArrayObjectToArrayList(listadoEstatut));

			OCAPModalidadesLN modalidadesLN = new OCAPModalidadesLN(jcylUsuario);
			listadoModalidades = modalidadesLN.listarModalidades();
			session.setAttribute(Constantes.COMBO_MODALIDAD, listadoModalidades);

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
			OCAPConfigApp.logger.info("---------- ALTA OCAP_CATEG_PROFESIONALES --------- ");

			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPCategProfesionalesLN oCAPCategProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			OCAPCategProfesionalesOT datos = new OCAPCategProfesionalesOT();

			OCAPCategProfesionalesForm formulario = (OCAPCategProfesionalesForm) form;
			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

			datos.setCEstatutId(formulario.getCEstatut_id());
			OCAPConfigApp.logger.info("cEstatutid : " + datos.getCEstatutId());

			if (formulario.getCEstatut_id() == 0L) {
				mensajeError = "El campo Estatuto es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if (formulario.getCModalidad_id() == 0L) {
				mensajeError = "El campo Modalidad es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
				datos.setDNombre(formulario.getDNombre());
				OCAPConfigApp.logger.info("dNombre: " + datos.getDNombre());
			}
			if ((formulario.getDNombre() == null) || (formulario.getDNombre().equals(""))) {
				mensajeError = "El campo Nombre de Categoría es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getDDescripcion() != null) && (!formulario.getDDescripcion().equals(""))) {
				datos.setDDescripcion(formulario.getDDescripcion());
				OCAPConfigApp.logger.info("dDescripcion: " + datos.getDDescripcion());
			}

			datos.setCModalidadId(formulario.getCModalidad_id());

			datos.setACreadoPor(
					((JCYLUsuario) request.getSession().getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO)).getUsuario().getC_usr_id());
			int result = oCAPCategProfesionalesLN.altaOCAPCategProfesionales(datos);
			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
				request.setAttribute("rutaVuelta", "OCAPCategProfesionales.do?accion=irInsertar");
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA OCAPCategProfesionales --------- ");
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
			OCAPCategProfesionalesLN oCAPCategProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			OCAPCategProfesionalesOT datos = new OCAPCategProfesionalesOT();
			OCAPCategProfesionalesForm formulario = (OCAPCategProfesionalesForm) form;

			String cProfesionalIdS = request.getParameter("cProfesionalIdS");
			long cProfesionalId;
			if ((cProfesionalIdS != null) && (!cProfesionalIdS.equals(""))) {
				cProfesionalId = Long.parseLong(cProfesionalIdS);
				OCAPConfigApp.logger.info("cProfesionalId: " + cProfesionalId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			datos = oCAPCategProfesionalesLN.buscarOCAPCategProfesionales(cProfesionalId);

			if (datos.getCProfesionalId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPCategProfesionalesOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosEdicion");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				OCAPConfigApp.logger.info("Se ha encontrado OCAPCategProfesionalesOT");
				request.getSession().setAttribute("conDatosEdicion", "");
				request.getSession().removeAttribute("sinDatosEdicion");
				request.setAttribute("OCAPCategProfesionalesOT", datos);

				formulario.setCProfesional_id(datos.getCProfesionalId());
				formulario.setCEstatut_id(datos.getCEstatutId());

				if (datos.getDDescripcion() == null)
					formulario.setDDescripcion("");
				else {
					formulario.setDDescripcion(datos.getDDescripcion());
				}
				formulario.setDNombre(datos.getDNombre());
				formulario.setCModalidad_id(datos.getCModalidadId());

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
			OCAPConfigApp.logger.info("---------- GRABAR OCAP_CATEG_PROFESIONALES --------- ");
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPCategProfesionalesLN oCAPCategProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			OCAPCategProfesionalesOT datos = new OCAPCategProfesionalesOT();

			OCAPCategProfesionalesForm formulario = (OCAPCategProfesionalesForm) form;

			if (formulario.getCProfesional_id() != 0L) {
				OCAPConfigApp.logger.info("cProfesionalId: " + formulario.getCProfesional_id());
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			datos.setCProfesionalId(formulario.getCProfesional_id());
			datos.setCEstatutId(formulario.getCEstatut_id());

			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
			if ((formulario.getDDescripcion() != null) && (!formulario.getDDescripcion().equals(""))) {
				datos.setDDescripcion(formulario.getDDescripcion());
				OCAPConfigApp.logger.info("dDescripcion: " + datos.getDDescripcion());
			}

			if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
				datos.setDNombre(formulario.getDNombre());
				OCAPConfigApp.logger.info("dNombre: " + datos.getDNombre());
			}

			if (formulario.getCEstatut_id() == 0L) {
				mensajeError = "El campo Nombre de Estatuto es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if (formulario.getCModalidad_id() == 0L) {
				mensajeError = "El campo Modalidad es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getDNombre() == null) || (formulario.getDNombre().equals(""))) {
				mensajeError = "El campo Nombre de Profesional es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setCModalidadId(formulario.getCModalidad_id());

			int result = oCAPCategProfesionalesLN.modificacionOCAPCategProfesionales(datos);

			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha modificado con éxito");

				request.setAttribute("rutaVuelta",
						"OCAPCategProfesionales.do?accion=detalle&cProfesionalIdS=" + datos.getCProfesionalId());
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("---------- FIN GRABAR MODIFICACION OCAPCategProfesionales --------- ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";
			request.setAttribute("mensaje", mensajeError);
		}

		return mapping.findForward(sig);
	}
}
