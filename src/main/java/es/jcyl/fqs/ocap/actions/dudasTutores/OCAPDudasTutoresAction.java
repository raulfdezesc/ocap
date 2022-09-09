package es.jcyl.fqs.ocap.actions.dudasTutores;

import es.jcyl.fqs.ocap.actionforms.dudasTutores.OCAPDudasTutoresForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
import es.jcyl.fqs.ocap.ln.dudasTutores.OCAPDudasTutoresLN;
import es.jcyl.fqs.ocap.ln.especialidades.OCAPEspecialidadesLN;
import es.jcyl.fqs.ocap.ln.gerencias.OCAPGerenciasLN;
import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
import es.jcyl.fqs.ocap.ot.dudasTutores.OCAPDudasTutoresOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Pagina;
import es.jcyl.fqs.ocap.util.TrataError;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class OCAPDudasTutoresAction extends OCAPGenericAction {
	public ActionForward bajaTutor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		try {
			OCAPConfigApp.logger.info(" Inicio ");
			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);

			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;

			String cTutorIdS = request.getParameter("cTutorIdS");
			int cTutorId;
			if ((cTutorIdS != null) && (!cTutorIdS.equals(""))) {
				cTutorId = Integer.parseInt(cTutorIdS);
				OCAPConfigApp.logger.info(" cTutorId: " + cTutorId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			int result = dudasTutoresLN.bajaTutor(cTutorId);

			if (result == 0) {
				OCAPConfigApp.logger.error("Se han actualizado cero registros");
				sig = "error";
				request.setAttribute("mensaje", "Se ha producido un error al borrar el registro");
			} else {
				OCAPConfigApp.logger.info("El registro se ha eliminado con éxito");
				request.setAttribute("mensaje", "El registro se ha eliminado con éxito");
				request.setAttribute("rutaVuelta",
						"OCAPDudasTutores.do?accion=buscarTutores&recuperarBusquedaAnterior=Y");
				sig = "exito";
			}
			OCAPConfigApp.logger.info(" Fin ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";
			request.setAttribute("mensaje", "Se ha producido un error");
		}

		return mapping.findForward(sig);
	}

	public ActionForward irBuscarTutores(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		HttpSession session = request.getSession();
		try {
			OCAPConfigApp.logger.info(" Inicio");
			((OCAPDudasTutoresForm) form).setJspInicio(true);
			request.getSession().setAttribute("iRegistro", Integer.toString(1));
			session.removeAttribute(Constantes.ORDENACION);
			OCAPConfigApp.logger.info(" fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			request.setAttribute("mensaje", mensajeError);
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irTutoresLs");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward buscarTutores(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		ActionForward actionForward = null;
		HttpSession session = request.getSession();
		OCAPDudasTutoresOT datos = null;
		try {
			OCAPConfigApp.logger.info(" Inicio ");
			ActionErrors errores = new ActionErrors();
			request.setAttribute("primeraConsulta", "false");
			((OCAPDudasTutoresForm) form).setJspInicio(false);

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPDudasTutoresForm) request.getSession().getAttribute("OCAPDudasTutoresFormBuscador");
				request.setAttribute("OCAPDudasTutoresForm", form);
			} else {
				request.getSession().setAttribute("OCAPDudasTutoresFormBuscador", form);
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
					: session.getAttribute("iRegistro").toString()) != null)
				try {
					primerRegistro = Integer.parseInt(registro);
				} catch (NumberFormatException ne) {
				}
			if ((!Constantes.SI.equals(request.getParameter("bPagina")))
					&& (!Constantes.SI.equals(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))) {
				primerRegistro = 1;
			}

			if ((registro = request.getParameter("nRegistrosP")) != null) {
				try {
					registrosPorPagina = Integer.parseInt(registro);
				} catch (NumberFormatException ne) {
				}
			}
			String ordenacion = "";
			String tipoOrdenacion = "ASC";
			
			if(session.getAttribute(Constantes.ORDENACION_CONTADOR) != null){
				tipoOrdenacion = ((Integer) session.getAttribute(Constantes.ORDENACION_CONTADOR)).intValue() == 0 ? "DESC"
						: session.getAttribute(Constantes.ORDENACION_CONTADOR) == null ? "ASC" : "ASC";
			}

			if (request.getParameter(Constantes.ORDENACION) != null) {
				ordenacion = request.getParameter(Constantes.ORDENACION);
				if (!Constantes.SI.equals(request.getParameter("bPagina")))
					if ((session.getAttribute(Constantes.ORDENACION) != null)
							&& (ordenacion.equals(session.getAttribute(Constantes.ORDENACION)))) {
						if (session.getAttribute(Constantes.ORDENACION_CONTADOR) != null) {
							if (((Integer) session.getAttribute(Constantes.ORDENACION_CONTADOR)).intValue() == 0) {
								session.setAttribute(Constantes.ORDENACION_CONTADOR, new Integer(1));
								tipoOrdenacion = "ASC";
							} else {
								session.setAttribute(Constantes.ORDENACION_CONTADOR, new Integer(0));
								tipoOrdenacion = "DESC";
							}
						} else {
							session.setAttribute(Constantes.ORDENACION_CONTADOR, new Integer(1));
							tipoOrdenacion = "ASC";
						}
					} else {
						session.setAttribute(Constantes.ORDENACION, ordenacion);
						session.setAttribute(Constantes.ORDENACION_CONTADOR, new Integer(1));
						tipoOrdenacion = "ASC";
					}
			} else if (session.getAttribute(Constantes.ORDENACION) != null) {
				ordenacion = session.getAttribute(Constantes.ORDENACION).toString();
			}
			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);

			datos = new OCAPDudasTutoresOT();
			if ((formulario.getDApellidosTutor() != null) && (!formulario.getDApellidosTutor().equals("")))
				datos.setDApellidosTutor(formulario.getDApellidosTutor());
			if ((formulario.getDNombreTutor() != null) && (!formulario.getDNombreTutor().equals("")))
				datos.setDNombreTutor(formulario.getDNombreTutor());
			if ((formulario.getCDni() != null) && (!formulario.getCDni().equals("")))
				datos.setCDni(formulario.getCDni());
			if ((formulario.getCTipoTutor() != null) && (!formulario.getCTipoTutor().equals("")))
				datos.setCTipoTutor(formulario.getCTipoTutor());
			if ((formulario.getCTipoDuda() != null) && (!formulario.getCTipoDuda().equals(""))) {
				datos.setCTipoDuda(formulario.getCTipoDuda());
			}
			Collection elementos = dudasTutoresLN.buscarTutores(datos, primerRegistro, registrosPorPagina, ordenacion,
					tipoOrdenacion);
			if (elementos != null) {
				OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " tutores para la consulta");
				if (elementos.size() == 0) {
					request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
					request.removeAttribute("conDatos");
				} else {
					int nListado = 0;
					nListado = dudasTutoresLN.buscarTutoresCuenta(datos);
					request.removeAttribute("sinDatos");
					Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
					pagina.setElementos(elementos);
					pagina.setRegistroActual(primerRegistro);
					pagina.setNRegistros(nListado);
					pagina.setRegistrosPorPagina(registrosPorPagina);
					request.setAttribute("listados", pagina);
					request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
				}
			} else {
				request.setAttribute("errorConsultando", "Error consultando en la base de datos");
			}
			OCAPConfigApp.logger.info(" Fin ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			request.setAttribute("mensaje", mensajeError);
			return mapping.findForward("error");
		}

		return mapping.findForward("irTutoresLs");
	}

	public ActionForward detalleTutor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		try {
			OCAPConfigApp.logger.info(" Inicio ");
			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);
			OCAPDudasTutoresOT datos = new OCAPDudasTutoresOT();

			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;

			String cTutorIdS = request.getParameter("cTutorIdS");
			long cTutorId;
			if ((cTutorIdS != null) && (!cTutorIdS.equals(""))) {
				cTutorId = Long.parseLong(cTutorIdS);
				OCAPConfigApp.logger.info("cTutorId: " + cTutorId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			datos = dudasTutoresLN.buscarDatosTutor(cTutorId);

			if (datos.getCTutorId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPDudasTutoresOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosDetalle");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				formulario.setCTutorId(datos.getCTutorId());
				formulario.setDNombreTutor(datos.getDNombreTutor());
				formulario.setDApellidosTutor(datos.getDApellidosTutor());
				formulario.setCDni(datos.getCDni());
				formulario.setDCorreoElectronicoTutor(datos.getDCorreoElectronicoTutor());
				formulario.setCTipoTutor(datos.getCTipoTutor());
				formulario.setCTipoDuda(datos.getCTipoDuda());
				formulario.setNDudasRecibidas(datos.getNDudasRecibidas());
				formulario.setNDudasContestadas(datos.getNDudasContestadas());
				formulario.setNControl(datos.getNControl());
				formulario.setBActivado(Constantes.SI.equals(datos.getBActivado()) ? Constantes.SI_TEXTO : Constantes.NO_TEXTO);

				OCAPConfigApp.logger.debug("Se ha encontrado tutor");
				request.getSession().setAttribute("conDatosDetalle", "");
				request.getSession().removeAttribute("sinDatosDetalle");
				request.getSession().setAttribute("OCAPDudasTutoresOT", datos);
				sig = "irTutor";
				request.setAttribute("tipoAccion", Constantes.VER_DETALLE);
				request.setAttribute("volverBuscador", request.getParameter("volverBuscador"));
			}
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";
			request.getSession().removeAttribute("conDatosDetalle");
		}

		return mapping.findForward(sig);
	}

	public ActionForward irPreguntasFrecuentes(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		try {
			OCAPConfigApp.logger.info(" Inicio Preguntas Frecuentes");

			OCAPConfigApp.logger.info(" Fin ");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			request.setAttribute("mensaje", mensajeError);
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irPreguntasFrecuentes");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irInsertarTutor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		try {
			OCAPConfigApp.logger.info(" Inicio ");

			request.setAttribute("tipoAccion", Constantes.INSERTAR);
			OCAPConfigApp.logger.info(" Fin ");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			request.setAttribute("mensaje", mensajeError);
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irTutor");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward insertarTutor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		try {
			OCAPConfigApp.logger.info(" Inicio ");

			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			if (request.getAttribute("erroresAction") != null) {
				saveErrors(request, (ActionErrors) request.getAttribute("erroresAction"));
				request.setAttribute("tipoAccion", Constantes.INSERTAR);
				return mapping.findForward("irTutor");
			}

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);
			OCAPDudasTutoresOT datos = new OCAPDudasTutoresOT();

			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;
			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

			datos.setDNombreTutor(formulario.getDNombreTutor());
			datos.setDApellidosTutor(formulario.getDApellidosTutor());
			datos.setCDni(formulario.getCDni().toUpperCase());
			datos.setCTipoTutor(formulario.getCTipoTutor());
			datos.setCTipoDuda(formulario.getCTipoDuda());
			datos.setDCorreoElectronicoTutor(formulario.getDCorreoElectronicoTutor());
			datos.setACreadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());

			long cTutorId = dudasTutoresLN.altaOCAPTutores(datos);
			if (cTutorId != 0L) {
				request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
				request.setAttribute("rutaVuelta",
						"OCAPDudasTutores.do?accion=detalleTutor&cTutorIdS=" + cTutorId + "&volverBuscador=" + Constantes.NO);
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info(" Fin ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";
			if (ex.getMessage().startsWith("ORA-00001")) {
				mensajeError = "Tutor ya dado de alta";
			}
			request.setAttribute("mensaje", mensajeError);
		}

		return mapping.findForward(sig);
	}

	public ActionForward irEditarTutor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irEditar: Inicio");
			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);
			OCAPDudasTutoresOT datos = new OCAPDudasTutoresOT();
			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;

			String cTutorIdS = request.getParameter("cTutorIdS");
			long cTutorId;
			if ((cTutorIdS != null) && (!cTutorIdS.equals(""))) {
				cTutorId = Long.parseLong(cTutorIdS);
				OCAPConfigApp.logger.info(" cTutorId: " + cTutorId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			datos = dudasTutoresLN.buscarDatosTutor(cTutorId);

			if (datos.getCTutorId() == 0L) {
				OCAPConfigApp.logger.error("El id del tutor es cero");
				sig = "error";
				request.getSession().removeAttribute("conDatosEdicion");
			} else {
				OCAPConfigApp.logger.info("Se ha encontrado OCAPDudasTutoresOT");
				request.getSession().setAttribute("conDatosEdicion", "");
				request.getSession().removeAttribute("sinDatosEdicion");

				formulario.setCTutorId(datos.getCTutorId());
				formulario.setDNombreTutor(datos.getDNombreTutor());
				formulario.setDApellidosTutor(datos.getDApellidosTutor());
				formulario.setCDni(datos.getCDni());
				formulario.setDCorreoElectronicoTutor(datos.getDCorreoElectronicoTutor());
				formulario.setCTipoTutor(datos.getCTipoTutor());
				formulario.setCTipoDuda(datos.getCTipoDuda());

				sig = "irTutor";
				request.setAttribute("tipoAccion", Constantes.MODIFICAR);
			}
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
		}

		return mapping.findForward(sig);
	}

	public ActionForward modificarTutor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		try {
			OCAPConfigApp.logger.info("Inicio");
			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			if (request.getAttribute("erroresAction") != null) {
				saveErrors(request, (ActionErrors) request.getAttribute("erroresAction"));
				request.setAttribute("tipoAccion", Constantes.MODIFICAR);
				return mapping.findForward("irTutor");
			}

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);
			OCAPDudasTutoresOT datos = new OCAPDudasTutoresOT();

			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;

			if (formulario.getCTutorId() != 0L) {
				OCAPConfigApp.logger.debug("cTutorId: " + formulario.getCTutorId());
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			datos.setCTutorId(formulario.getCTutorId());
			datos.setDNombreTutor(formulario.getDNombreTutor());
			datos.setDApellidosTutor(formulario.getDApellidosTutor());
			datos.setCDni(formulario.getCDni().toUpperCase());
			datos.setCTipoTutor(formulario.getCTipoTutor());

			datos.setDCorreoElectronicoTutor(formulario.getDCorreoElectronicoTutor());
			datos.setACreadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());

			int result = dudasTutoresLN.modificarTutor(datos);

			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha modificado con éxito");
				request.setAttribute("rutaVuelta",
						"OCAPDudasTutores.do?accion=buscarTutores&recuperarBusquedaAnterior=Y");
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("Fin");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";
			request.setAttribute("mensaje", mensajeError);
		}

		return mapping.findForward(sig);
	}

	public ActionForward activarTutor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		try {
			OCAPConfigApp.logger.info("Inicio");
			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);
			OCAPDudasTutoresOT datos = new OCAPDudasTutoresOT();

			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;

			String cTutorIdS = request.getParameter("cTutorIdS");
			int cTutorId;
			if ((cTutorIdS != null) && (!cTutorIdS.equals(""))) {
				cTutorId = Integer.parseInt(cTutorIdS);
				OCAPConfigApp.logger.info(" cTutorId: " + cTutorId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			datos.setCTutorId(cTutorId);
			datos.setBActivado(request.getParameter("activar"));
			datos.setACreadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
			if (Constantes.SI.equals(request.getParameter("activar"))) {
				String cTipoDuda = request.getParameter("tipoDuda");
				datos.setNControl(dudasTutoresLN.buscarMinNControlTutoresActivos(cTipoDuda));
			} else {
				OCAPDudasTutoresOT auxTutorOT = new OCAPDudasTutoresOT();
				OCAPDudasTutoresOT auxTutorOT2 = dudasTutoresLN.buscarDatosTutor(datos.getCTutorId());
				auxTutorOT.setCTipoTutor(auxTutorOT2.getCTipoTutor());
				auxTutorOT.setCTipoDuda(auxTutorOT2.getCTipoDuda());
				auxTutorOT.setBActivado(Constantes.SI);
				int contadorTutores = dudasTutoresLN.buscarTutoresCuenta(auxTutorOT);
				if (contadorTutores <= 1) {
					mensajeError = "Es el único tutor activado de su mismo tipo en este momento y no puede desactivarse. Primero debe activar otro tutor.";
					throw new Exception();
				}
			}
			int result = dudasTutoresLN.modificarTutor(datos);

			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha modificado con éxito");
				request.setAttribute("rutaVuelta",
						"OCAPDudasTutores.do?accion=buscarTutores&recuperarBusquedaAnterior=Y");
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("Fin");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";
			request.setAttribute("mensaje", mensajeError);
		}

		return mapping.findForward(sig);
	}

	public ActionForward irInsertarDuda(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		OCAPUsuariosLN usuariosLN = null;
		OCAPUsuariosOT usuarioOT = null;
		try {
			OCAPConfigApp.logger.info(" Inicio ");
			HttpSession session = request.getSession();
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);

			session.setAttribute(Constantes.COMBO_TEMAS, new ArrayList());
			String usuId = jcylUsuario.getUser().getC_usr_id();
			usuariosLN = new OCAPUsuariosLN(jcylUsuario);
			usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);
			String correo = dudasTutoresLN.buscarCorreoExp(usuarioOT.getCExpId().longValue());
			if ((correo == null) || ("".equals(correo)))
				correo = usuarioOT.getDCorreoelectronico();
			((OCAPDudasTutoresForm) form).setDCorreoElectronico(correo);
			DecimalFormat formato = new DecimalFormat("000000");
			((OCAPDudasTutoresForm) form).setCodigoEPR("EPR" + formato.format(usuarioOT.getCExpId().longValue()));
			((OCAPDudasTutoresForm) form).setCExpId(usuarioOT.getCExpId().longValue());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(" Fin ");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			request.setAttribute("mensaje", mensajeError);
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irDuda");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward cargarComboTemas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listado = new ArrayList();
		String sig = "error";
		try {
			OCAPConfigApp.logger.info(" Inicio ");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);

			if (request.getParameter("CTipoDuda") != null) {
				listado = dudasTutoresLN.buscarTemas(request.getParameter("CTipoDuda"));
			}
			session.setAttribute(Constantes.COMBO_TEMAS, listado);

			if ("CSV".equals(request.getParameter("vuelta"))) {
				sig = "irCSVDudas";
			} else if ("DudasUsuario".equals(request.getParameter("vuelta"))) {
				sig = "irDudasUsuarioLs";
			} else {
				request.setAttribute("tipoAccion", Constantes.INSERTAR);
				sig = "irDuda";
			}

			OCAPConfigApp.logger.info("Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(sig);
		}
		saveErrors(request, errors);

		return mapping.findForward("error");
	}

	public ActionForward insertarDuda(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		try {
			OCAPConfigApp.logger.info(" Inicio ");

			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);
			OCAPDudasTutoresOT datos = new OCAPDudasTutoresOT();

			if (request.getAttribute("erroresAction") != null) {
				saveErrors(request, (ActionErrors) request.getAttribute("erroresAction"));
				request.setAttribute("tipoAccion", Constantes.INSERTAR);
				ArrayList listado = dudasTutoresLN.buscarTemas(request.getParameter("tipoDuda"));
				((OCAPDudasTutoresForm) form).setListaTemas(listado);
				return mapping.findForward("irDuda");
			}

			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;
			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

			datos.setCExpId(formulario.getCExpId());
			datos.setCTemaId(formulario.getCTemaId());
			datos.setCTipoDuda(formulario.getCTipoDuda());
			datos.setDCorreoElectronico(formulario.getDCorreoElectronico());
			datos.setDDuda(formulario.getDDuda());
			datos.setBContestado(Constantes.NO);
			datos.setBCambio(Constantes.NO);
			datos.setBProcedeCambio(Constantes.NO);
			datos.setACreadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());

			long cDudaId = dudasTutoresLN.altaDuda(datos, false);
			if (cDudaId != 0L) {
				request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
				request.setAttribute("rutaVuelta", "OCAPCuestionarios.do?accion=irExplicacionAreas");
				sig = "exitoDuda";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info(" Fin ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";
			if ("sinTutor".equals(ex.getMessage()))
				request.setAttribute("mensaje", "No hay tutor al que asignar la duda. Consulte con el administrador.");
			else {
				request.setAttribute("mensaje", mensajeError);
			}
		}
		return mapping.findForward(sig);
	}

	public ActionForward irBuscarDudas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		ArrayList listado = null;
		OCAPUsuariosLN usuariosLN = null;
		OCAPUsuariosOT usuarioOT = null;
		try {
			OCAPConfigApp.logger.info(" Inicio");
			HttpSession session = request.getSession();
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);
			if ((Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol()))
					|| (Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol()))) {
				listado = dudasTutoresLN.buscarTemas(null);
				((OCAPDudasTutoresForm) form).setListaTemas(listado);
				String usuId = jcylUsuario.getUser().getC_usr_id();
				usuariosLN = new OCAPUsuariosLN(jcylUsuario);
				usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);
				if ((usuarioOT == null) || (usuarioOT.getCExpId() == null) || (usuarioOT.getCExpId().longValue() == 0L))
					throw new Exception("No se encuentra su usuario.");
				((OCAPDudasTutoresForm) form).setCExpId(usuarioOT.getCExpId().longValue());
				session.removeAttribute(Constantes.ORDENACION);
				sig = "irDudasUsuarioLs";
			} else {
				((OCAPDudasTutoresForm) form).setBContestado(request.getParameter("contestadas"));
				OCAPDudasTutoresOT datos = dudasTutoresLN.buscarDatosTutor(jcylUsuario.getUser().getC_usr_id());
				if ((datos != null) && (datos.getCTutorId() != 0L)) {
					if ((("" + 2).equals(datos.getCTipoTutor())) && (Constantes.NO.equals(datos.getBActivado()))) {
						messages.add("cTutorId", new ActionMessage("errors.tutorDesactivado"));
						mensajeError = "Actualmente está desactivado como tutor.";
					} else {
						((OCAPDudasTutoresForm) form).setCTipoDuda(datos.getCTipoDuda());
						if ((1 + "").equals(datos.getCTipoTutor()))
							listado = dudasTutoresLN.buscarTemas(datos.getCTipoDuda());
						else
							listado = dudasTutoresLN.buscarTemas(null);
						((OCAPDudasTutoresForm) form).setListaTemas(listado);
						session.setAttribute("tutorOT", datos);
						((OCAPDudasTutoresForm) form).setCTipoTutor(datos.getCTipoTutor());
						session.removeAttribute(Constantes.ORDENACION);
						sig = "irDudasTutorLs";
					}
				} else {
					messages.add("cTutorId", new ActionMessage("errors.tutorNoExiste"));
					mensajeError = "Tutor no dado de alta.";
				}
			}
			((OCAPDudasTutoresForm) form).setJspInicio(true);
			request.getSession().setAttribute("iRegistro", Integer.toString(1));
			request.getSession().setAttribute("buscarDudas", Constantes.NO);
			OCAPConfigApp.logger.info(" fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			request.setAttribute("mensaje", mensajeError);
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty()))) {
			return mapping.findForward(sig);
		}
		if ((errors != null) && (!errors.isEmpty())) {
			saveErrors(request, errors);

			return mapping.findForward(sig);
		}
		saveMessages(request, messages);
		saveErrors(request, errors);
		request.setAttribute("mensaje", mensajeError);

		return mapping.findForward(sig);
	}

	public ActionForward buscarDudasTutores(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		ActionForward actionForward = null;
		HttpSession session = request.getSession();
		OCAPDudasTutoresOT datos = null;
		try {
			OCAPConfigApp.logger.info(" Inicio ");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);

			ArrayList listado = null;
			if ((1 + "").equals(((OCAPDudasTutoresForm) form).getCTipoTutor()))
				listado = dudasTutoresLN.buscarTemas(((OCAPDudasTutoresForm) form).getCTipoDuda());
			else
				listado = dudasTutoresLN.buscarTemas(null);
			((OCAPDudasTutoresForm) form).setListaTemas(listado);

			if (request.getAttribute("erroresAction") != null) {
				saveErrors(request, (ActionErrors) request.getAttribute("erroresAction"));
				((OCAPDudasTutoresForm) form).setJspInicio(true);
				return mapping.findForward("irDudasTutorLs");
			}

			ActionErrors errores = new ActionErrors();
			request.setAttribute("primeraConsulta", "false");
			((OCAPDudasTutoresForm) form).setJspInicio(false);

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPDudasTutoresForm) request.getSession().getAttribute("OCAPDudasTutoresFormBuscador");
				request.setAttribute("OCAPDudasTutoresForm", form);
			} else {
				request.getSession().setAttribute("OCAPDudasTutoresFormBuscador", form);
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
					: session.getAttribute("iRegistro").toString()) != null)
				try {
					primerRegistro = Integer.parseInt(registro);
				} catch (NumberFormatException ne) {
				}
			if ((!Constantes.SI.equals(request.getParameter("bPagina")))
					&& (!Constantes.SI.equals(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))) {
				primerRegistro = 1;
			}

			if ((registro = request.getParameter("nRegistrosP")) != null) {
				try {
					registrosPorPagina = Integer.parseInt(registro);
				} catch (NumberFormatException ne) {
				}
			}
			String ordenacion = "";
			String tipoOrdenacion = ((Integer) session.getAttribute(Constantes.ORDENACION_CONTADOR)).intValue() == 0 ? "DESC"
					: session.getAttribute(Constantes.ORDENACION_CONTADOR) == null ? "ASC" : "ASC";

			if (request.getParameter(Constantes.ORDENACION) != null) {
				ordenacion = request.getParameter(Constantes.ORDENACION);
				if (!Constantes.SI.equals(request.getParameter("bPagina")))
					if ((session.getAttribute(Constantes.ORDENACION) != null)
							&& (ordenacion.equals(session.getAttribute(Constantes.ORDENACION)))) {
						if (session.getAttribute(Constantes.ORDENACION_CONTADOR) != null) {
							if (((Integer) session.getAttribute(Constantes.ORDENACION_CONTADOR)).intValue() == 0) {
								session.setAttribute(Constantes.ORDENACION_CONTADOR, new Integer(1));
								tipoOrdenacion = "ASC";
							} else {
								session.setAttribute(Constantes.ORDENACION_CONTADOR, new Integer(0));
								tipoOrdenacion = "DESC";
							}
						} else {
							session.setAttribute(Constantes.ORDENACION_CONTADOR, new Integer(1));
							tipoOrdenacion = "ASC";
						}
					} else {
						session.setAttribute(Constantes.ORDENACION, ordenacion);
						session.setAttribute(Constantes.ORDENACION_CONTADOR, new Integer(1));
						tipoOrdenacion = "ASC";
					}
			} else if (session.getAttribute(Constantes.ORDENACION) != null) {
				ordenacion = session.getAttribute(Constantes.ORDENACION).toString();
			}
			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;

			datos = new OCAPDudasTutoresOT();
			if ((formulario.getCodigoEPR() != null) && (!formulario.getCodigoEPR().equals(""))) {
				String auxExpId = formulario.getCodigoEPR().toUpperCase().substring("EPR".length());
				datos.setCExpId(Long.parseLong(auxExpId));
			}
			if (formulario.getCTemaId() != 0L)
				datos.setCTemaId(formulario.getCTemaId());
			if ((formulario.getBCambioTipo() != null) && (!formulario.getBCambioTipo().equals("")))
				datos.setBCambio(formulario.getBCambioTipo());
			if ((formulario.getFInicio() != null) && (!formulario.getFInicio().equals("")))
				datos.setFInicio(formulario.getFInicio());
			if ((formulario.getFFin() != null) && (!formulario.getFFin().equals("")))
				datos.setFFin(formulario.getFFin());
			if ((formulario.getFInicioRespuesta() != null) && (!formulario.getFInicioRespuesta().equals("")))
				datos.setFInicioRespuesta(formulario.getFInicioRespuesta());
			if ((formulario.getFFinRespuesta() != null) && (!formulario.getFFinRespuesta().equals("")))
				datos.setFFinRespuesta(formulario.getFFinRespuesta());
			if ((formulario.getCTipoDuda() != null) && (!formulario.getCTipoDuda().equals("")))
				datos.setCTipoDuda(formulario.getCTipoDuda());
			if ((formulario.getBContestado() != null) && (!formulario.getBContestado().equals(""))) {
				datos.setBContestado(formulario.getBContestado());
			} else if (request.getParameter("contestadas") != null) {
				formulario.setBContestado(request.getParameter("contestadas"));
				datos.setBContestado(formulario.getBContestado());
			}

			if (session.getAttribute("tutorOT") != null) {
				OCAPDudasTutoresOT tutorOT = (OCAPDudasTutoresOT) session.getAttribute("tutorOT");
				formulario.setCTipoTutor(tutorOT.getCTipoTutor());
				if ((1 + "").equals(tutorOT.getCTipoTutor())) {
					datos.setCTutorId(tutorOT.getCTutorId());
				} else if ("1".equals(formulario.getCTipoDuda())) {
					datos.setCTipoDuda(null);
				}
			}

			Collection elementos = dudasTutoresLN.buscarDudasTutores(datos, primerRegistro, registrosPorPagina,
					ordenacion, tipoOrdenacion);
			if (elementos != null) {
				OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " tutores para la consulta");
				if (elementos.size() == 0) {
					request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
					request.removeAttribute("conDatos");
				} else {
					int nListado = 0;
					nListado = dudasTutoresLN.buscarDudasTutoresCuenta(datos);
					request.removeAttribute("sinDatos");
					Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
					pagina.setElementos(elementos);
					pagina.setRegistroActual(primerRegistro);
					pagina.setNRegistros(nListado);
					pagina.setRegistrosPorPagina(registrosPorPagina);
					request.setAttribute("listados", pagina);
					request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
				}
			} else {
				request.setAttribute("errorConsultando", "Error consultando en la base de datos");
			}

			if ((formulario.getFInicio() != null) && (!formulario.getFInicio().equals("")))
				formulario.setFInicio(formulario.getFInicio().substring(0, 16));
			if ((formulario.getFFin() != null) && (!formulario.getFFin().equals("")))
				formulario.setFFin(formulario.getFFin().substring(0, 16));
			if ((formulario.getFInicioRespuesta() != null) && (!formulario.getFInicioRespuesta().equals("")))
				formulario.setFInicioRespuesta(formulario.getFInicioRespuesta().substring(0, 16));
			if ((formulario.getFFinRespuesta() != null) && (!formulario.getFFinRespuesta().equals("")))
				formulario.setFFinRespuesta(formulario.getFFinRespuesta().substring(0, 16));
			OCAPConfigApp.logger.info(" Fin ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			request.setAttribute("mensaje", mensajeError);
			return mapping.findForward("error");
		}

		return mapping.findForward("irDudasTutorLs");
	}

	public ActionForward buscarDudasUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		ActionForward actionForward = null;
		HttpSession session = request.getSession();
		OCAPDudasTutoresOT datos = null;
		try {
			OCAPConfigApp.logger.info(" Inicio ");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);

			ArrayList listado = null;
			listado = dudasTutoresLN.buscarTemas(null);
			((OCAPDudasTutoresForm) form).setListaTemas(listado);

			if (request.getAttribute("erroresAction") != null) {
				((OCAPDudasTutoresForm) form).setJspInicio(true);
				saveErrors(request, (ActionErrors) request.getAttribute("erroresAction"));
				return mapping.findForward("irDudasUsuarioLs");
			}

			ActionErrors errores = new ActionErrors();
			request.setAttribute("primeraConsulta", "false");
			((OCAPDudasTutoresForm) form).setJspInicio(false);

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPDudasTutoresForm) request.getSession().getAttribute("OCAPDudasTutoresFormBuscador");
				request.setAttribute("OCAPDudasTutoresForm", form);
			} else {
				request.getSession().setAttribute("OCAPDudasTutoresFormBuscador", form);
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
					: session.getAttribute("iRegistro").toString()) != null)
				try {
					primerRegistro = Integer.parseInt(registro);
				} catch (NumberFormatException ne) {
				}
			if ((!Constantes.SI.equals(request.getParameter("bPagina")))
					&& (!Constantes.SI.equals(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))) {
				primerRegistro = 1;
			}

			if ((registro = request.getParameter("nRegistrosP")) != null) {
				try {
					registrosPorPagina = Integer.parseInt(registro);
				} catch (NumberFormatException ne) {
				}
			}
			String ordenacion = "";
			String tipoOrdenacion = ((Integer) session.getAttribute(Constantes.ORDENACION_CONTADOR)).intValue() == 0 ? "DESC"
					: session.getAttribute(Constantes.ORDENACION_CONTADOR) == null ? "ASC" : "ASC";

			if (request.getParameter(Constantes.ORDENACION) != null) {
				ordenacion = request.getParameter(Constantes.ORDENACION);
				if (!Constantes.SI.equals(request.getParameter("bPagina")))
					if ((session.getAttribute(Constantes.ORDENACION) != null)
							&& (ordenacion.equals(session.getAttribute(Constantes.ORDENACION)))) {
						if (session.getAttribute(Constantes.ORDENACION_CONTADOR) != null) {
							if (((Integer) session.getAttribute(Constantes.ORDENACION_CONTADOR)).intValue() == 0) {
								session.setAttribute(Constantes.ORDENACION_CONTADOR, new Integer(1));
								tipoOrdenacion = "ASC";
							} else {
								session.setAttribute(Constantes.ORDENACION_CONTADOR, new Integer(0));
								tipoOrdenacion = "DESC";
							}
						} else {
							session.setAttribute(Constantes.ORDENACION_CONTADOR, new Integer(1));
							tipoOrdenacion = "ASC";
						}
					} else {
						session.setAttribute(Constantes.ORDENACION, ordenacion);
						session.setAttribute(Constantes.ORDENACION_CONTADOR, new Integer(1));
						tipoOrdenacion = "ASC";
					}
			} else if (session.getAttribute(Constantes.ORDENACION) != null) {
				ordenacion = session.getAttribute(Constantes.ORDENACION).toString();
			}

			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;

			datos = new OCAPDudasTutoresOT();
			datos.setCExpId(formulario.getCExpId());
			if (formulario.getCTemaId() != 0L)
				datos.setCTemaId(formulario.getCTemaId());
			if ((formulario.getFInicio() != null) && (!formulario.getFInicio().equals("")))
				datos.setFInicio(formulario.getFInicio());
			if ((formulario.getFFin() != null) && (!formulario.getFFin().equals("")))
				datos.setFFin(formulario.getFFin());
			if ((formulario.getFInicioRespuesta() != null) && (!formulario.getFInicioRespuesta().equals("")))
				datos.setFInicioRespuesta(formulario.getFInicioRespuesta());
			if ((formulario.getFFinRespuesta() != null) && (!formulario.getFFinRespuesta().equals("")))
				datos.setFFinRespuesta(formulario.getFFinRespuesta());
			if ((formulario.getBContestado() != null) && (!formulario.getBContestado().equals("")))
				datos.setBContestado(formulario.getBContestado());
			datos.setBCambio(Constantes.NO);

			Collection elementos = dudasTutoresLN.buscarDudasUsuario(datos, primerRegistro, registrosPorPagina,
					ordenacion, tipoOrdenacion);
			if (elementos != null) {
				OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " tutores para la consulta");
				if (elementos.size() == 0) {
					request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
					request.removeAttribute("conDatos");
				} else {
					int nListado = 0;
					nListado = dudasTutoresLN.buscarDudasUsuarioCuenta(datos);
					request.removeAttribute("sinDatos");
					Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
					pagina.setElementos(elementos);
					pagina.setRegistroActual(primerRegistro);
					pagina.setNRegistros(nListado);
					pagina.setRegistrosPorPagina(registrosPorPagina);
					request.setAttribute("listados", pagina);
					request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
				}
			} else {
				request.setAttribute("errorConsultando", "Error consultando en la base de datos");
			}

			if ((formulario.getFInicio() != null) && (!formulario.getFInicio().equals("")))
				formulario.setFInicio(formulario.getFInicio().substring(0, 16));
			if ((formulario.getFFin() != null) && (!formulario.getFFin().equals("")))
				formulario.setFFin(formulario.getFFin().substring(0, 16));
			if ((formulario.getFInicioRespuesta() != null) && (!formulario.getFInicioRespuesta().equals("")))
				formulario.setFInicioRespuesta(formulario.getFInicioRespuesta().substring(0, 16));
			if ((formulario.getFFinRespuesta() != null) && (!formulario.getFFinRespuesta().equals("")))
				formulario.setFFinRespuesta(formulario.getFFinRespuesta().substring(0, 16));
			OCAPConfigApp.logger.info(" Fin ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			request.setAttribute("mensaje", mensajeError);
			return mapping.findForward("error");
		}

		return mapping.findForward("irDudasUsuarioLs");
	}

	public ActionForward detalleDuda(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		try {
			OCAPConfigApp.logger.info(" Inicio ");
			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);
			OCAPDudasTutoresOT datos = new OCAPDudasTutoresOT();

			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;

			String cDudaIdS = request.getParameter("cDudaIdS");
			long cDudaId;
			if ((cDudaIdS != null) && (!cDudaIdS.equals(""))) {
				cDudaId = Long.parseLong(cDudaIdS);
				OCAPConfigApp.logger.info("cDudaId: " + cDudaId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			datos = dudasTutoresLN.buscarDatosDuda(cDudaId);

			if (datos.getCDudaId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPDudasTutoresOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosDetalle");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				formulario.setCDudaId(datos.getCDudaId());
				formulario.setCTutorId(datos.getCTutorId());
				formulario.setCTipoDuda(datos.getCTipoDuda());
				formulario.setCTemaId(datos.getCTemaId());
				formulario.setDTema(datos.getDTema());
				formulario.setCExpId(datos.getCExpId());
				formulario.setCodigoEPR(datos.getCodigoEPR());
				formulario.setDCorreoElectronico(datos.getDCorreoElectronico());
				formulario.setDDuda(Cadenas.formatearTexto(datos.getDDuda()));
				formulario.setFRecepcion(datos.getFRecepcion());
				formulario.setDRespuesta(
						datos.getDRespuesta() == null ? "" : Cadenas.formatearTexto(datos.getDRespuesta()));
				formulario.setFEnvioContestacion(datos.getFEnvioContestacion());
				formulario.setBContestado(datos.getBContestado());
				formulario.setBLeido(datos.getBLeido());
				formulario.setBCambioTipo(datos.getBCambio());

				if ((datos.getFEnvioContestacion() != null) && (Constantes.NO.equals(datos.getBLeido()))
						&& ((Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol()))
								|| (Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())))) {
					datos.setBLeido(Constantes.SI);
					datos.setACreadoPor(jcylUsuario.getUser().getC_usr_id());
					dudasTutoresLN.marcarDudaLeida(datos);
				}

				OCAPConfigApp.logger.debug("Se ha encontrado duda");
				request.getSession().setAttribute("conDatosDetalle", "");
				request.getSession().removeAttribute("sinDatosDetalle");
				request.getSession().setAttribute("OCAPDudasTutoresOT", datos);
				sig = "irDuda";

				if (session.getAttribute("tutorOT") != null) {
					OCAPDudasTutoresOT tutorOT = (OCAPDudasTutoresOT) session.getAttribute("tutorOT");
					formulario.setCTipoTutor(tutorOT.getCTipoTutor());
				}

				if ((Constantes.SI.equals(datos.getBContestado()))
						|| (Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol())))
					request.setAttribute("tipoAccion", Constantes.VER_DETALLE);
				else {
					request.setAttribute("tipoAccion", Constantes.MODIFICAR);
				}
				request.setAttribute("volverBuscador", request.getParameter("volverBuscador"));
			}
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";
			request.getSession().removeAttribute("conDatosDetalle");
		}

		return mapping.findForward(sig);
	}

	public ActionForward contestarDuda(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		try {
			OCAPConfigApp.logger.info("Inicio");
			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			if (request.getAttribute("erroresAction") != null) {
				saveErrors(request, (ActionErrors) request.getAttribute("erroresAction"));
				request.setAttribute("tipoAccion", Constantes.MODIFICAR);
				return mapping.findForward("irDuda");
			}

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);
			OCAPDudasTutoresOT datos = new OCAPDudasTutoresOT();

			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;

			if (formulario.getCDudaId() != 0L) {
				OCAPConfigApp.logger.debug("cDudaId: " + formulario.getCDudaId());
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			datos.setCDudaId(formulario.getCDudaId());
			datos.setBContestado(Constantes.SI);
			datos.setDRespuesta(formulario.getDRespuesta());
			datos.setDCorreoElectronico(formulario.getDCorreoElectronico());

			long idTutorAsignado = formulario.getCTutorId();

			if (session.getAttribute("tutorOT") != null) {
				OCAPDudasTutoresOT tutorOT = (OCAPDudasTutoresOT) session.getAttribute("tutorOT");
				datos.setCTutorId(tutorOT.getCTutorId());
			} else {
				datos.setCTutorId(formulario.getCTutorId());
			}
			datos.setCTipoTutor(formulario.getCTipoTutor());
			datos.setACreadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());

			int result = dudasTutoresLN.contestarDuda(datos, idTutorAsignado, false);

			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha modificado con éxito");
				request.setAttribute("rutaVuelta",
						"OCAPDudasTutores.do?accion=buscarDudasTutores&recuperarBusquedaAnterior=Y");
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("Fin");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";
			request.setAttribute("mensaje", mensajeError);
		}

		return mapping.findForward(sig);
	}

	public ActionForward cambiarTipoDuda(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		try {
			OCAPConfigApp.logger.info("Inicio");
			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);
			OCAPDudasTutoresOT datos = new OCAPDudasTutoresOT();

			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;

			if (formulario.getCDudaId() != 0L) {
				OCAPConfigApp.logger.debug("cDudaId: " + formulario.getCDudaId());
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			datos.setCDudaId(formulario.getCDudaId());
			datos.setCExpId(formulario.getCExpId());
			datos.setCTemaId(formulario.getCTemaId());
			datos.setCTipoDuda(formulario.getCTipoDuda());
			datos.setDCorreoElectronico(formulario.getDCorreoElectronico());
			datos.setBContestado(Constantes.SI);
			datos.setDRespuesta("");

			long idTutorAsignado = formulario.getCTutorId();

			if (session.getAttribute("tutorOT") != null) {
				OCAPDudasTutoresOT tutorOT = (OCAPDudasTutoresOT) session.getAttribute("tutorOT");
				datos.setCTutorId(tutorOT.getCTutorId());
			} else {
				datos.setCTutorId(formulario.getCTutorId());
			}
			datos.setCTipoTutor(formulario.getCTipoTutor());
			datos.setACreadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
			datos.setBCambio(Constantes.SI);
			int result = dudasTutoresLN.cambiarTipoDuda(datos, idTutorAsignado);
			if (result != 0) {
				request.setAttribute("mensaje", "El registro se ha modificado con éxito");
				request.setAttribute("rutaVuelta",
						"OCAPDudasTutores.do?accion=buscarDudasTutores&recuperarBusquedaAnterior=Y");
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("Fin");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";
			request.setAttribute("mensaje", mensajeError);
		}

		return mapping.findForward(sig);
	}

	public ActionForward reasignarDuda(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		OCAPUsuariosLN usuariosLN = null;
		OCAPUsuariosOT usuarioOT = null;
		try {
			OCAPConfigApp.logger.info(" Inicio ");
			HttpSession session = request.getSession();
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);
			OCAPDudasTutoresOT datos = new OCAPDudasTutoresOT();

			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;

			String cDudaIdS = request.getParameter("cDudaIdS");
			long cDudaId;
			if ((cDudaIdS != null) && (!cDudaIdS.equals(""))) {
				cDudaId = Long.parseLong(cDudaIdS);
				OCAPConfigApp.logger.info("cDudaId: " + cDudaId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			datos = dudasTutoresLN.buscarDatosDuda(cDudaId);
			datos.setACreadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());

			int result = dudasTutoresLN.reasignarDuda(datos);

			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha modificado con éxito");
				request.setAttribute("rutaVuelta",
						"OCAPDudasTutores.do?accion=buscarDudasTutores&recuperarBusquedaAnterior=Y");
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info(" Fin ");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			request.setAttribute("mensaje", mensajeError);
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(sig);
		}
		saveErrors(request, errors);

		return mapping.findForward(sig);
	}

	public ActionForward irCSVDudas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		ArrayList listadoGerencias = new ArrayList();
		ArrayList listadoCategorias = new ArrayList();
		ArrayList listadoTemas = new ArrayList();
		try {
			OCAPConfigApp.logger.info(" Inicio ");
			HttpSession session = request.getSession();
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;
			formulario.setCodigoEPR("");
			formulario.setCGerenciaId(0L);
			formulario.setCProfesionalId(0L);
			formulario.setCTemaId(0L);
			formulario.setFInicio("");
			formulario.setFFin("");
			formulario.setFInicioRespuesta("");
			formulario.setFFinRespuesta("");

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			listadoGerencias = gerenciasLN.listadoOCAPGerencias();
			session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();
			session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
			session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());

			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);
			listadoTemas = dudasTutoresLN.buscarTemas(null);
			session.setAttribute(Constantes.COMBO_TEMAS, listadoTemas);

			OCAPConfigApp.logger.info(" Fin ");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			request.setAttribute("mensaje", mensajeError);
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irCSVDudas");
		}
		saveErrors(request, errors);

		return mapping.findForward(sig);
	}

	public ActionForward cargarComboEspecialidades(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoEspecialidades = new ArrayList();
		String sig = "error";
		ArrayList listadoTemas = null;
		try {
			OCAPConfigApp.logger.info(" Inicio ");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);

			if (((OCAPDudasTutoresForm) form).getCProfesionalId() != 0L) {
				listadoEspecialidades = especialidadesLN
						.listarEspecialidades(((OCAPDudasTutoresForm) form).getCProfesionalId());
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
			} else {
				session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
			}
			if ((1 + "").equals(((OCAPDudasTutoresForm) form).getCTipoTutor()))
				listadoTemas = dudasTutoresLN.buscarTemas(((OCAPDudasTutoresForm) form).getCTipoDuda());
			else
				listadoTemas = dudasTutoresLN.buscarTemas(null);
			((OCAPDudasTutoresForm) form).setListaTemas(listadoTemas);

			if ("CSV".equals(request.getParameter("vuelta")))
				sig = "irCSVDudas";
			else
				sig = "irDudasLs";

			((OCAPDudasTutoresForm) form).setJspInicio(true);

			OCAPConfigApp.logger.info("Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(sig);
		}
		saveErrors(request, errors);

		return mapping.findForward("error");
	}

	public ActionForward generarCSVDudas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		ActionForward actionForward = null;
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		OCAPDudasTutoresOT datos = null;
		String pathBase = null;
		ArrayList listadoDudas = null;
		String cadena = "";
		try {
			OCAPConfigApp.logger.info(" Inicio ");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);

			if (request.getAttribute("erroresAction") != null) {
				saveErrors(request, (ActionErrors) request.getAttribute("erroresAction"));
				return mapping.findForward("irCSVDudas");
			}

			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;

			datos = new OCAPDudasTutoresOT();
			if ((formulario.getCodigoEPR() != null) && (!formulario.getCodigoEPR().equals(""))) {
				String auxExpId = formulario.getCodigoEPR().toUpperCase().substring("EPR".length());
				datos.setCExpId(Long.parseLong(auxExpId));
			}
			if ((formulario.getFInicio() != null) && (!formulario.getFInicio().equals("")))
				datos.setFInicio(formulario.getFInicio());
			if ((formulario.getFFin() != null) && (!formulario.getFFin().equals("")))
				datos.setFFin(formulario.getFFin());
			if ((formulario.getFInicioRespuesta() != null) && (!formulario.getFInicioRespuesta().equals("")))
				datos.setFInicioRespuesta(formulario.getFInicioRespuesta());
			if ((formulario.getFFinRespuesta() != null) && (!formulario.getFFinRespuesta().equals("")))
				datos.setFFinRespuesta(formulario.getFFinRespuesta());
			if (formulario.getCGerenciaId() != 0L)
				datos.setCGerenciaId(formulario.getCGerenciaId());
			if (formulario.getCProfesionalId() != 0L)
				datos.setCProfesionalId(formulario.getCProfesionalId());
			if (formulario.getCEspecialidadId() != 0L)
				datos.setCEspecialidadId(formulario.getCEspecialidadId());
			if (formulario.getCTemaId() != 0L)
				datos.setCTemaId(formulario.getCTemaId());
			if ((formulario.getCTipoDuda() != null) && (!formulario.getCTipoDuda().equals("")))
				datos.setCTipoDuda(formulario.getCTipoDuda());
			if ((formulario.getBContestado() != null) && (!formulario.getBContestado().equals(""))) {
				datos.setBContestado(formulario.getBContestado());
			}
			pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");

			listadoDudas = dudasTutoresLN.buscarDudasCSV(datos);

			if (listadoDudas.size() == 0) {
				request.setAttribute("mensaje", "No hay dudas coincidentes con los filtros introducidos.");

				if ((formulario.getFInicio() != null) && (!formulario.getFInicio().equals("")))
					formulario.setFInicio(formulario.getFInicio().substring(0, 16));
				if ((formulario.getFFin() != null) && (!formulario.getFFin().equals("")))
					formulario.setFFin(formulario.getFFin().substring(0, 16));
				if ((formulario.getFInicioRespuesta() != null) && (!formulario.getFInicioRespuesta().equals("")))
					formulario.setFInicioRespuesta(formulario.getFInicioRespuesta().substring(0, 16));
				if ((formulario.getFFinRespuesta() != null) && (!formulario.getFFinRespuesta().equals("")))
					formulario.setFFinRespuesta(formulario.getFFinRespuesta().substring(0, 16));
			} else {
				cadena = dudasTutoresLN.crearCSVDudas(listadoDudas);

				OutputStream out = response.getOutputStream();

				response.setHeader("Content-disposition", "attachment; filename=\"informeDudas.csv\"");
				response.setContentType("text/csv;charset=ISO_8859_1");
				response.setHeader("Pragma", "no-cache");
				cadena = cadena.replaceAll(Constantes.SALTO_LINEA, "\n");
				byte[] bytes = cadena.getBytes(StandardCharsets.ISO_8859_1);
				response.setContentLength(bytes.length);

				OutputStreamWriter outWriter = new OutputStreamWriter(out, StandardCharsets.ISO_8859_1);
				outWriter.write(cadena, 0, cadena.length());
				outWriter.flush();
				outWriter.close();
				out.close();
			}
			OCAPConfigApp.logger.info("Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irCSVDudas");
		}
		saveErrors(request, errors);

		return mapping.findForward(sig);
	}

	public ActionForward irBuscadorDudas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		ArrayList listadoGerencias = new ArrayList();
		ArrayList listadoCategorias = new ArrayList();
		ArrayList listadoTemas = new ArrayList();

		OCAPUsuariosLN usuariosLN = null;
		OCAPUsuariosOT usuarioOT = null;
		try {
			OCAPConfigApp.logger.info(" Inicio");
			HttpSession session = request.getSession();
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);

			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;

			formulario.setBContestado(Constantes.SI);

			OCAPDudasTutoresOT datos = dudasTutoresLN.buscarDatosTutor(jcylUsuario.getUser().getC_usr_id());
			if ((datos != null) && (datos.getCTutorId() != 0L)) {
				if ((("" + 2).equals(datos.getCTipoTutor())) && (Constantes.NO.equals(datos.getBActivado()))) {
					messages.add("cTutorId", new ActionMessage("errors.tutorDesactivado"));
					mensajeError = "Actualmente está desactivado como tutor.";
				} else {
					((OCAPDudasTutoresForm) form).setCTipoDuda(datos.getCTipoDuda());
					if ((1 + "").equals(datos.getCTipoTutor()))
						listadoTemas = dudasTutoresLN.buscarTemas(datos.getCTipoDuda());
					else
						listadoTemas = dudasTutoresLN.buscarTemas(null);
					((OCAPDudasTutoresForm) form).setListaTemas(listadoTemas);

					session.setAttribute("tutorOT", datos);
					((OCAPDudasTutoresForm) form).setCTipoTutor(datos.getCTipoTutor());
					session.removeAttribute(Constantes.ORDENACION);
					sig = "irDudasLs";
				}
			} else {
				messages.add("cTutorId", new ActionMessage("errors.tutorNoExiste"));
				mensajeError = "Tutor no dado de alta.";
			}

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			listadoGerencias = gerenciasLN.listadoOCAPGerencias();
			session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();
			session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
			session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());

			((OCAPDudasTutoresForm) form).setJspInicio(true);
			request.getSession().setAttribute("iRegistro", Integer.toString(1));
			request.getSession().setAttribute("buscarDudas", Constantes.SI);
			OCAPConfigApp.logger.info(" Fin ");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			request.setAttribute("mensaje", mensajeError);
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty()))) {
			return mapping.findForward(sig);
		}
		if ((errors != null) && (!errors.isEmpty())) {
			saveErrors(request, errors);

			return mapping.findForward(sig);
		}
		saveMessages(request, messages);
		saveErrors(request, errors);
		request.setAttribute("mensaje", mensajeError);

		return mapping.findForward(sig);
	}

	public ActionForward buscarDudas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		ActionForward actionForward = null;
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		OCAPDudasTutoresOT datos = null;
		String pathBase = null;
		ArrayList listadoDudas = null;
		ArrayList listadoTemas = null;
		String cadena = "";
		try {
			OCAPConfigApp.logger.info(" Inicio ");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPDudasTutoresLN dudasTutoresLN = new OCAPDudasTutoresLN(jcylUsuario);

			if ((1 + "").equals(((OCAPDudasTutoresForm) form).getCTipoTutor()))
				listadoTemas = dudasTutoresLN.buscarTemas(((OCAPDudasTutoresForm) form).getCTipoDuda());
			else
				listadoTemas = dudasTutoresLN.buscarTemas(null);
			((OCAPDudasTutoresForm) form).setListaTemas(listadoTemas);

			if (request.getAttribute("erroresAction") != null) {
				((OCAPDudasTutoresForm) form).setJspInicio(true);
				saveErrors(request, (ActionErrors) request.getAttribute("erroresAction"));
				return mapping.findForward("irDudasLs");
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
					: session.getAttribute("iRegistro").toString()) != null)
				try {
					primerRegistro = Integer.parseInt(registro);
				} catch (NumberFormatException ne) {
				}
			if ((!Constantes.SI.equals(request.getParameter("bPagina")))
					&& (!Constantes.SI.equals(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))) {
				primerRegistro = 1;
			}

			if ((registro = request.getParameter("nRegistrosP")) != null) {
				try {
					registrosPorPagina = Integer.parseInt(registro);
				} catch (NumberFormatException ne) {
				}
			}
			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPDudasTutoresForm) request.getSession().getAttribute("OCAPDudasTutoresFormBuscador");
				request.setAttribute("OCAPDudasTutoresForm", form);
			} else {
				request.getSession().setAttribute("OCAPDudasTutoresFormBuscador", form);
				session.setAttribute("iRegistro", 1 + "");
			}

			String ordenacion = "";
			String tipoOrdenacion = ((Integer) session.getAttribute(Constantes.ORDENACION_CONTADOR)).intValue() == 0 ? "DESC"
					: session.getAttribute(Constantes.ORDENACION_CONTADOR) == null ? "ASC" : "ASC";

			if (request.getParameter(Constantes.ORDENACION) != null) {
				ordenacion = request.getParameter(Constantes.ORDENACION);
				if (!Constantes.SI.equals(request.getParameter("bPagina")))
					if ((session.getAttribute(Constantes.ORDENACION) != null)
							&& (ordenacion.equals(session.getAttribute(Constantes.ORDENACION)))) {
						if (session.getAttribute(Constantes.ORDENACION_CONTADOR) != null) {
							if (((Integer) session.getAttribute(Constantes.ORDENACION_CONTADOR)).intValue() == 0) {
								session.setAttribute(Constantes.ORDENACION_CONTADOR, new Integer(1));
								tipoOrdenacion = "ASC";
							} else {
								session.setAttribute(Constantes.ORDENACION_CONTADOR, new Integer(0));
								tipoOrdenacion = "DESC";
							}
						} else {
							session.setAttribute(Constantes.ORDENACION_CONTADOR, new Integer(1));
							tipoOrdenacion = "ASC";
						}
					} else {
						session.setAttribute(Constantes.ORDENACION, ordenacion);
						session.setAttribute(Constantes.ORDENACION_CONTADOR, new Integer(1));
						tipoOrdenacion = "ASC";
					}
			} else if (session.getAttribute(Constantes.ORDENACION) != null) {
				ordenacion = session.getAttribute(Constantes.ORDENACION).toString();
			}

			OCAPDudasTutoresForm formulario = (OCAPDudasTutoresForm) form;

			datos = new OCAPDudasTutoresOT();
			if ((formulario.getCodigoEPR() != null) && (!formulario.getCodigoEPR().equals(""))) {
				String auxExpId = formulario.getCodigoEPR().toUpperCase().substring("EPR".length());
				datos.setCExpId(Long.parseLong(auxExpId));
			}
			if ((formulario.getFInicio() != null) && (!formulario.getFInicio().equals("")))
				datos.setFInicio(formulario.getFInicio());
			if ((formulario.getFFin() != null) && (!formulario.getFFin().equals("")))
				datos.setFFin(formulario.getFFin());
			if ((formulario.getFInicioRespuesta() != null) && (!formulario.getFInicioRespuesta().equals("")))
				datos.setFInicioRespuesta(formulario.getFInicioRespuesta());
			if ((formulario.getFFinRespuesta() != null) && (!formulario.getFFinRespuesta().equals("")))
				datos.setFFinRespuesta(formulario.getFFinRespuesta());
			if (formulario.getCTemaId() != 0L)
				datos.setCTemaId(formulario.getCTemaId());
			if (formulario.getCGerenciaId() != 0L)
				datos.setCGerenciaId(formulario.getCGerenciaId());
			if (formulario.getCProfesionalId() != 0L)
				datos.setCProfesionalId(formulario.getCProfesionalId());
			if (formulario.getCEspecialidadId() != 0L)
				datos.setCEspecialidadId(formulario.getCEspecialidadId());
			if ((formulario.getCTipoDuda() != null) && (!formulario.getCTipoDuda().equals(""))) {
				datos.setCTipoDuda(formulario.getCTipoDuda());
			}
			if ((formulario.getBContestado() != null) && (!formulario.getBContestado().equals(""))) {
				datos.setBContestado(formulario.getBContestado());
			}
			Collection elementos = dudasTutoresLN.buscarDudas(datos, primerRegistro, registrosPorPagina, ordenacion,
					tipoOrdenacion);
			if (elementos != null) {
				OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " tutores para la consulta");
				if (elementos.size() == 0) {
					request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
					request.removeAttribute("conDatos");
				} else {
					int nListado = 0;
					nListado = dudasTutoresLN.buscarDudasCuenta(datos);
					request.removeAttribute("sinDatos");
					Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
					pagina.setElementos(elementos);
					pagina.setRegistroActual(primerRegistro);
					pagina.setNRegistros(nListado);
					pagina.setRegistrosPorPagina(registrosPorPagina);
					request.setAttribute("listados", pagina);
					request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
				}
			} else {
				request.setAttribute("errorConsultando", "Error consultando en la base de datos");
			}

			if ((formulario.getFInicio() != null) && (!formulario.getFInicio().equals("")))
				formulario.setFInicio(formulario.getFInicio().substring(0, 16));
			if ((formulario.getFFin() != null) && (!formulario.getFFin().equals("")))
				formulario.setFFin(formulario.getFFin().substring(0, 16));
			if ((formulario.getFInicioRespuesta() != null) && (!formulario.getFInicioRespuesta().equals("")))
				formulario.setFInicioRespuesta(formulario.getFInicioRespuesta().substring(0, 16));
			if ((formulario.getFFinRespuesta() != null) && (!formulario.getFFinRespuesta().equals(""))) {
				formulario.setFFinRespuesta(formulario.getFFinRespuesta().substring(0, 16));
			}
			((OCAPDudasTutoresForm) form).setJspInicio(false);
			ArrayList listado = null;
			if ((1 + "").equals(((OCAPDudasTutoresForm) form).getCTipoTutor()))
				listado = dudasTutoresLN.buscarTemas(((OCAPDudasTutoresForm) form).getCTipoDuda());
			else
				listado = dudasTutoresLN.buscarTemas(null);
			((OCAPDudasTutoresForm) form).setListaTemas(listado);

			OCAPConfigApp.logger.info("Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("mensaje", "Se ha producido un error al borrar el registro");
			sig = "error";
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irDudasLs");
		}
		saveErrors(request, errors);

		return mapping.findForward(sig);
	}
}
