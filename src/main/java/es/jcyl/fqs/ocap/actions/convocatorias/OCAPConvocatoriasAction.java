package es.jcyl.fqs.ocap.actions.convocatorias;

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

import es.jcyl.fqs.ocap.actionforms.convocatorias.OCAPConvocatoriasForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
import es.jcyl.fqs.ocap.ln.grado.OCAPGradoLN;
import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.Pagina;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLUsuario;

public class OCAPConvocatoriasAction extends OCAPGenericAction {
	public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- BAJA DE OCAP_CONVOCATORIAS --------- ");

			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPConvocatoriasLN oCAPConvocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);

			String cConvocatoriaIdS = request.getParameter("cConvocatoriaIdS");
			int cConvocatoriaId;
			if ((cConvocatoriaIdS != null) && (!cConvocatoriaIdS.equals(""))) {
				cConvocatoriaId = Integer.parseInt(cConvocatoriaIdS);
				OCAPConfigApp.logger.info("cConvocatoriaId: " + cConvocatoriaId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			int result = oCAPConvocatoriasLN.bajaOCAPConvocatorias(cConvocatoriaId);

			if (result == 0) {
				OCAPConfigApp.logger.info("Se ha producido un error al borrar el registro");
				sig = "error";
				request.setAttribute("mensaje", "Se ha producido un error al borrar el registro");
			} else {
				OCAPConfigApp.logger.info("El registro se ha eliminado con éxito");
				request.setAttribute("mensaje", "El registro se ha eliminado con éxito");
				request.setAttribute("rutaVuelta", "OCAPConvocatorias.do?accion=buscar&recuperarBusquedaAnterior=Y");
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
			OCAPConfigApp.logger.info("---------- CONSULTA DE OCAP_CONVOCATORIAS --------- ");
			request.setAttribute("primeraConsulta", "false");

			if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
					&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
				form = (OCAPConvocatoriasForm) request.getSession().getAttribute("OCAPConvocatoriasFormBuscador");
				request.setAttribute("OCAPConvocatoriasForm", form);
			} else {
				request.getSession().setAttribute("OCAPConvocatoriasFormBuscador", form);
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
			OCAPConvocatoriasForm formulario = (OCAPConvocatoriasForm) form;

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPConvocatoriasLN oCAPConvocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);

			OCAPConvocatoriasOT convocatoriaOT = new OCAPConvocatoriasOT();

			convocatoriaOT.setCConvocatoriaId(formulario.getCConvocatoria_id());

			if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
				convocatoriaOT.setDNombre(formulario.getDNombre());
			}

			if ((formulario.getAResolucion() != null) && (!formulario.getAResolucion().equals(""))
					&& (formulario.getMResolucion() != null) && (!formulario.getMResolucion().equals(""))
					&& (formulario.getDResolucion() != null) && (!formulario.getDResolucion().equals(""))) {
				convocatoriaOT.setFResolucion(formulario.getDResolucion() + '/' + formulario.getMResolucion() + '/'
						+ formulario.getAResolucion());
			}

			if ((formulario.getAPublicacion() != null) && (!formulario.getAPublicacion().equals(""))
					&& (formulario.getMPublicacion() != null) && (!formulario.getMPublicacion().equals(""))
					&& (formulario.getDPublicacion() != null) && (!formulario.getDPublicacion().equals(""))) {
				convocatoriaOT.setFPublicacion(formulario.getDPublicacion() + '/' + formulario.getMPublicacion() + '/'
						+ formulario.getAPublicacion());
			}

			if ((formulario.getAInicioMC() != null) && (!formulario.getAInicioMC().equals(""))
					&& (formulario.getMInicioMC() != null) && (!formulario.getMInicioMC().equals(""))
					&& (formulario.getDInicioMC() != null) && (!formulario.getDInicioMC().equals(""))) {
				convocatoriaOT.setFInicioMC(
						formulario.getDInicioMC() + '/' + formulario.getMInicioMC() + '/' + formulario.getAInicioMC());
			}

			if ((formulario.getAInicioAlega() != null) && (!formulario.getAInicioAlega().equals(""))
					&& (formulario.getMInicioAlega() != null) && (!formulario.getMInicioAlega().equals(""))
					&& (formulario.getDInicioAlega() != null) && (!formulario.getDInicioAlega().equals(""))) {
				convocatoriaOT.setFAlegsolicitud(formulario.getDInicioAlega() + '/' + formulario.getMInicioAlega() + '/'
						+ formulario.getAInicioAlega());
			}

			if ((formulario.getAInicioCC() != null) && (!formulario.getAInicioCC().equals(""))
					&& (formulario.getMInicioCC() != null) && (!formulario.getMInicioCC().equals(""))
					&& (formulario.getDInicioCC() != null) && (!formulario.getDInicioCC().equals(""))) {
				convocatoriaOT.setFInicioEstudioCC(
						formulario.getDInicioCC() + '/' + formulario.getMInicioCC() + '/' + formulario.getAInicioCC());
			}

			if ((formulario.getAFinCC() != null) && (!formulario.getAFinCC().equals(""))
					&& (formulario.getMFinCC() != null) && (!formulario.getMFinCC().equals(""))
					&& (formulario.getDFinCC() != null) && (!formulario.getDFinCC().equals(""))) {
				convocatoriaOT.setFFinEstudioCC(
						formulario.getDFinCC() + '/' + formulario.getMFinCC() + '/' + formulario.getAFinCC());
			}

			if ((formulario.getAInicioCA() != null) && (!formulario.getAInicioCA().equals(""))
					&& (formulario.getMInicioCA() != null) && (!formulario.getMInicioCA().equals(""))
					&& (formulario.getDInicioCA() != null) && (!formulario.getDInicioCA().equals(""))) {
				convocatoriaOT.setFInicioCA(
						formulario.getDInicioCA() + '/' + formulario.getMInicioCA() + '/' + formulario.getAInicioCA());
			}

			if ((formulario.getAFinCp() != null) && (!formulario.getAFinCp().equals(""))
					&& (formulario.getMFinCp() != null) && (!formulario.getMFinCp().equals(""))
					&& (formulario.getDFinCp() != null) && (!formulario.getDFinCp().equals(""))) {
				convocatoriaOT.setFFinCp(
						formulario.getDFinCp() + '/' + formulario.getMFinCp() + '/' + formulario.getAFinCp());
			}
			
			
			/* OCAP-10 Nuevos campos de convocatoria   no están en el formulario de búsqueda PTE de ver si van o no faltaria modificar el jsp*/   
			
			if ((formulario.getAInicioSolicitud() != null) && (!formulario.getAInicioSolicitud().equals(""))
					&& (formulario.getMInicioSolicitud() != null) && (!formulario.getMInicioSolicitud().equals(""))
					&& (formulario.getDInicioSolicitud() != null) && (!formulario.getDInicioSolicitud().equals(""))) {
				convocatoriaOT.setFInicioSolicitud(
						formulario.getDInicioSolicitud() + '/' + formulario.getMInicioSolicitud() + '/' + formulario.getAInicioSolicitud());
			}
			
			if ((formulario.getAFinSolicitud() != null) && (!formulario.getAFinSolicitud().equals(""))
					&& (formulario.getMFinSolicitud() != null) && (!formulario.getMFinSolicitud().equals(""))
					&& (formulario.getDFinSolicitud() != null) && (!formulario.getDFinSolicitud().equals(""))) {
				convocatoriaOT.setFFinSolicitud(
						formulario.getDFinSolicitud() + '/' + formulario.getMFinSolicitud() + '/' + formulario.getAFinSolicitud());
			}
			
			
			
			if ((formulario.getAFinMC() != null) && (!formulario.getAFinMC().equals(""))
					&& (formulario.getMFinMC() != null) && (!formulario.getMFinMC().equals(""))
					&& (formulario.getDFinMC() != null) && (!formulario.getDFinMC().equals(""))) {
				convocatoriaOT.setFFinMC(
						formulario.getDFinMC() + '/' + formulario.getMFinMC() + '/' + formulario.getAFinMC());
			}
			
			
			if ((formulario.getAInicioValoracionMC() != null) && (!formulario.getAInicioValoracionMC().equals(""))
					&& (formulario.getMInicioValoracionMC() != null) && (!formulario.getMInicioValoracionMC().equals(""))
					&& (formulario.getDInicioValoracionMC() != null) && (!formulario.getDInicioValoracionMC().equals(""))) {
				convocatoriaOT.setFInicioValoracionMC(
						formulario.getDInicioValoracionMC() + '/' + formulario.getMInicioValoracionMC() + '/' + formulario.getAInicioValoracionMC());
			}
			
			if ((formulario.getAFinValoracionMC() != null) && (!formulario.getAFinValoracionMC().equals(""))
					&& (formulario.getMFinValoracionMC() != null) && (!formulario.getMFinValoracionMC().equals(""))
					&& (formulario.getDFinValoracionMC() != null) && (!formulario.getDFinValoracionMC().equals(""))) {
				convocatoriaOT.setFFinValoracionMC(
						formulario.getDFinValoracionMC() + '/' + formulario.getMFinValoracionMC() + '/' + formulario.getAFinValoracionMC());
			}

			if ((formulario.getAInicioValoracionCC() != null) && (!formulario.getAInicioValoracionCC().equals(""))
					&& (formulario.getMInicioValoracionCC() != null) && (!formulario.getMInicioValoracionCC().equals(""))
					&& (formulario.getDInicioValoracionCC() != null) && (!formulario.getDInicioValoracionCC().equals(""))) {
				convocatoriaOT.setFInicioValoracionCC(
						formulario.getDInicioValoracionCC() + '/' + formulario.getMInicioValoracionCC() + '/' + formulario.getAInicioValoracionCC());
			}
			
			if ((formulario.getAFinValoracionCC() != null) && (!formulario.getAFinValoracionCC().equals(""))
					&& (formulario.getMFinValoracionCC() != null) && (!formulario.getMFinValoracionCC().equals(""))
					&& (formulario.getDFinValoracionCC() != null) && (!formulario.getDFinValoracionCC().equals(""))) {
				convocatoriaOT.setFFinValoracionCC(
						formulario.getDFinValoracionCC() + '/' + formulario.getMFinValoracionCC() + '/' + formulario.getAFinValoracionCC());
			}
			
			if ((formulario.getAFinCA() != null) && (!formulario.getAFinCA().equals(""))
					&& (formulario.getMFinCA() != null) && (!formulario.getMFinCA().equals(""))
					&& (formulario.getDFinCA() != null) && (!formulario.getDFinCA().equals(""))) {
				convocatoriaOT.setFFinCA(
						formulario.getDFinCA() + '/' + formulario.getMFinCA() + '/' + formulario.getAFinCA());
			}
			
			if ((formulario.getAInicioValCA() != null) && (!formulario.getAInicioValCA().equals(""))
					&& (formulario.getMInicioValCA() != null) && (!formulario.getMInicioValCA().equals(""))
					&& (formulario.getDInicioValCA() != null) && (!formulario.getDInicioValCA().equals(""))) {
				convocatoriaOT.setFInicioValCA(
						formulario.getDInicioValCA() + '/' + formulario.getMInicioValCA() + '/' + formulario.getAInicioValCA());
			}
			
			if ((formulario.getAFinValCA() != null) && (!formulario.getAFinValCA().equals(""))
					&& (formulario.getMFinValCA() != null) && (!formulario.getMFinValCA().equals(""))
					&& (formulario.getDFinValCA() != null) && (!formulario.getDFinValCA().equals(""))) {
				convocatoriaOT.setFFinValCA(
						formulario.getDFinValCA() + '/' + formulario.getMFinValCA() + '/' + formulario.getAFinValCA());
			}
			
			
			
			convocatoriaOT.setNDiasRegsolicitud(formulario.getNDias_regsolicitud());
			convocatoriaOT.setNDiasRevsolicitud(formulario.getNDias_revsolicitud());
			convocatoriaOT.setNDiasPublistado(formulario.getNDias_publistado());
			convocatoriaOT.setNDiasAutoMc(formulario.getNDias_auto_mc());
			convocatoriaOT.setNDiasValMc(formulario.getNDias_val_mc());
			convocatoriaOT.setNDiasInconfMc(formulario.getNDias_inconf_mc());
			convocatoriaOT.setNDiasAutoCa(formulario.getNDias_auto_ca());
			convocatoriaOT.setNDiasValCa(formulario.getNDias_val_ca());
			convocatoriaOT.setNDiasInconfCa(formulario.getNDias_inconf_ca());
			convocatoriaOT.setNDiasValCc(formulario.getNDias_val_cc());
			convocatoriaOT.setNDiasRespinconfMc(formulario.getNDias_respinconf_mc());
			convocatoriaOT.setNDiasRespinconfCa(formulario.getNDias_respinconf_ca());
			convocatoriaOT.setNDiasRespinconfCc(formulario.getNDias_respinconf_cc());

			if ((formulario.getAObservaciones() != null) && (!formulario.getAObservaciones().equals(""))) {
				convocatoriaOT.setAObservaciones(formulario.getAObservaciones());
			}

			if ((formulario.getACreacion() != null) && (!formulario.getACreacion().equals(""))
					&& (formulario.getMCreacion() != null) && (!formulario.getMCreacion().equals(""))
					&& (formulario.getDCreacion() != null) && (!formulario.getDCreacion().equals(""))) {
				convocatoriaOT.setFCreacion(
						formulario.getDCreacion() + '/' + formulario.getMCreacion() + '/' + formulario.getACreacion());
			}

			if ((formulario.getAModificacion() != null) && (!formulario.getAModificacion().equals(""))
					&& (formulario.getMModificacion() != null) && (!formulario.getMModificacion().equals(""))
					&& (formulario.getDModificacion() != null) && (!formulario.getDModificacion().equals(""))) {
				convocatoriaOT.setFModificacion(formulario.getDModificacion() + '/' + formulario.getMModificacion()
						+ '/' + formulario.getAModificacion());
			}

			if ((formulario.getBCierreSo() != null) && (!formulario.getBCierreSo().equals(""))) {
				if (Constantes.SI_ESP.equals(formulario.getBCierreSo().toUpperCase()))
					convocatoriaOT.setBCierreSo(Constantes.SI);
				else {
					convocatoriaOT.setBCierreSo(Constantes.NO);
				}
			}
			if ((formulario.getARecGrado() != null) && (!formulario.getARecGrado().equals(""))
					&& (formulario.getMRecGrado() != null) && (!formulario.getMRecGrado().equals(""))
					&& (formulario.getDRecGrado() != null) && (!formulario.getDRecGrado().equals(""))) {
				convocatoriaOT.setFRecGrado(
						formulario.getDRecGrado() + '/' + formulario.getMRecGrado() + '/' + formulario.getARecGrado());
			}

			Collection elementos = oCAPConvocatoriasLN.consultaOCAPConvocatorias(convocatoriaOT, primerRegistro,
					registrosPorPagina);

			if (elementos != null) {
				OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Convocatoriaes para la consulta");
				if (elementos.size() == 0) {
					request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
					request.removeAttribute("conDatos");
					request.getSession().removeAttribute("paginaOCAPConvocatoriasOT");
				} else {
					Object[] listadoelementos = elementos.toArray();

					int reg = elementos.size();

					elementos.removeAll(elementos);

					for (int i = 0; i < reg; i++) {
						elementos.add(listadoelementos[i]);
					}

					int nListado = 0;

					nListado = oCAPConvocatoriasLN.listadoOCAPConvocatoriasCuenta(convocatoriaOT);
					request.removeAttribute("sinDatos");
					Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
					pagina.setElementos(elementos);
					pagina.setRegistroActual(primerRegistro);
					pagina.setNRegistros(nListado);
					pagina.setRegistrosPorPagina(registrosPorPagina);
					request.setAttribute("paginaOCAPConvocatoriasOT", pagina);
					request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
					request.removeAttribute("menu");
					request.getSession().removeAttribute("menu");
				}
			} else {
				request.setAttribute("errorConsultando", "Error consultando en la base de datos");
			}

			OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAP_CONVOCATORIAS ------- ");
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
			OCAPConfigApp.logger.info("---------- DETALLE OCAP_CONVOCATORIAS --------- ");

			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPConvocatoriasLN oCAPConvocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT datos = new OCAPConvocatoriasOT();

			OCAPConvocatoriasForm formulario = (OCAPConvocatoriasForm) form;

			String cConvocatoriaIdS = request.getParameter("cConvocatoriaIdS");
			long cConvocatoriaId;
			if ((cConvocatoriaIdS != null) && (!cConvocatoriaIdS.equals(""))) {
				cConvocatoriaId = Long.parseLong(cConvocatoriaIdS);
				OCAPConfigApp.logger.info("cConvocatoriaId: " + cConvocatoriaId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			datos = oCAPConvocatoriasLN.buscarOCAPConvocatorias(cConvocatoriaId);

			if (datos.getCConvocatoriaId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPConvocatoriasOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosDetalle");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				formulario.setCConvocatoria_id(datos.getCConvocatoriaId());
				formulario.setDNombreCorto(datos.getDNombreCorto());
				formulario.setDNombre(datos.getDNombre());
				formulario.setFResolucion(datos.getFResolucion());
				formulario.setFPublicacion(datos.getFPublicacion());
				formulario.setFInicioMC(datos.getFInicioMC());
				formulario.setFInicioAlega(datos.getFAlegsolicitud());
				formulario.setFInicioEstudioCC(datos.getFInicioEstudioCC());
				formulario.setFFinEstudioCC(datos.getFFinEstudioCC());
				formulario.setFInicioCA(datos.getFInicioCA());
				formulario.setFFinCp(datos.getFFinCp());
				formulario.setNDias_regsolicitud(datos.getNDiasRegsolicitud());
				formulario.setNDias_revsolicitud(datos.getNDiasRevsolicitud());
				formulario.setNDias_publistado(datos.getNDiasPublistado());
				formulario.setNDias_auto_mc(datos.getNDiasAutoMc());
				formulario.setNDias_val_mc(datos.getNDiasValMc());
				formulario.setNDias_inconf_mc(datos.getNDiasInconfMc());
				formulario.setNDias_auto_ca(datos.getNDiasAutoCa());
				formulario.setNDias_val_ca(datos.getNDiasValCa());
				formulario.setNDias_inconf_ca(datos.getNDiasInconfCa());
				formulario.setNDias_val_cc(datos.getNDiasValCc());
				formulario.setNDias_respinconf_mc(datos.getNDiasRespinconfMc());
				formulario.setNDias_respinconf_ca(datos.getNDiasRespinconfCa());
				formulario.setNDias_respinconf_cc(datos.getNDiasRespinconfCc());
				formulario.setFRecGrado(datos.getFRecGrado());
				
				/*OCAP-10*/
								
			   formulario.setFInicioSolicitud(datos.getFInicioSolicitud());
			   formulario.setFFinSolicitud(datos.getFFinSolicitud());
			   formulario.setFInicioValoracionMC(datos.getFInicioValoracionMC());
			   formulario.setFFinValoracionMC(datos.getFFinValoracionMC());
			   formulario.setFInicioValoracionCC(datos.getFInicioValoracionCC());
			   formulario.setFFinValoracionCC(datos.getFFinValoracionCC());
			   formulario.setFFinMC(datos.getFFinMC());
			   formulario.setFFinCA(datos.getFFinCA());
			   formulario.setFInicioValCA(datos.getFInicioValCA());
			   formulario.setFFinValCA(datos.getFFinValCA());
			   
			   /*OCAP-101*/
			   formulario.setDAnioConvocatoria(datos.getDAnioConvocatoria());
			   
				if (Constantes.SI.equals(datos.getBCierreSo()))
					formulario.setBCierreSo(Constantes.SI_ESP);
				else {
					formulario.setBCierreSo(datos.getBCierreSo());
				}
				if (datos.getAObservaciones() == null)
					formulario.setAObservaciones(Constantes.EMPTY);
				else {
					formulario.setAObservaciones(datos.getAObservaciones());
				}
				
				//OCAP-1528
				if (datos.getCGradoId() == null || datos.getCGradoId() == 0L) {
					formulario.setCGrado_id("");
				} else {
					formulario.setCGrado_id(datos.getCGradoId().toString());
				}
				
				
				//OCAP-1866				
				if (datos.getFFinPGP() == null) {
					formulario.setFFinPgp(Constantes.EMPTY);
				} else {
					formulario.setFFinPgp(datos.getFFinPGP());
					formulario.setDFinPgp(datos.getFFinPGP().substring(0, 2));
					formulario.setMFinPgp(datos.getFFinPGP().substring(3, 5));
					formulario.setAFinPgp(datos.getFFinPGP().substring(6, 10));
				}
				//FIN OCAP-1866
				
				
				OCAPConfigApp.logger.info("Se ha encontrado OCAPConvocatorias");
				request.getSession().setAttribute("conDatosDetalle", Constantes.EMPTY);
				request.getSession().removeAttribute("sinDatosDetalle");
				request.getSession().setAttribute("OCAPConvocatoriasOT", datos);
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
		ArrayList listadoGrados = null;
		HttpSession session = request.getSession();

		String mensajeError = "Se ha producido un error";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Inicio");

			request.setAttribute("tipoAccion", Constantes.INSERTAR);
			
			//OCAP-1528
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			
			OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
			listadoGrados = gradoLN.listarGrados();
			session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, listadoGrados);
			//FIN OCAP-1528

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
		String fResolucion = null;
		String fPublicacion = null;
		String fInicioMC = null;
		String fInicioAlega = null;
		String fInicioCC = null;
		String fFinCC = null;
		String fInicioCA = null;
		String fFinCp = null;
		String fRecGrado = null;
		String fFinPGP = null;
		
		try {
			OCAPConfigApp.logger.info(Constantes.EMPTY);
			OCAPConfigApp.logger.info("---------- ALTA OCAP_CONVOCATORIAS --------- ");

			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPConvocatoriasLN oCAPConvocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT datos = new OCAPConvocatoriasOT();

			OCAPConvocatoriasForm formulario = (OCAPConvocatoriasForm) form;
			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

			
			if ((formulario.getDNombre() == null) || (formulario.getDNombre().equals(Constantes.EMPTY))) {
				mensajeError = "El campo Nombre es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(Constantes.EMPTY))) {
				datos.setDNombre(formulario.getDNombre());
				OCAPConfigApp.logger.info("dNombre : " + datos.getDNombre());
			}
			
			if ((formulario.getDNombreCorto() == null) || (formulario.getDNombreCorto().equals(Constantes.EMPTY))) {
				mensajeError = "El campo Nombre Corto es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getDNombreCorto() != null) && (!formulario.getDNombreCorto().equals(Constantes.EMPTY))) {
				datos.setDNombreCorto(formulario.getDNombreCorto());
				OCAPConfigApp.logger.info("dNombreCorto : " + datos.getDNombreCorto());
			}			

			if ((formulario.getAResolucion() != null) && (!formulario.getAResolucion().equals(Constantes.EMPTY))
					&& (formulario.getMResolucion() != null) && (!formulario.getMResolucion().equals(Constantes.EMPTY))
					&& (formulario.getDResolucion() != null) && (!formulario.getDResolucion().equals(Constantes.EMPTY))) {
				fResolucion = formulario.getDResolucion() + '/' + formulario.getMResolucion() + '/'
						+ formulario.getAResolucion();
				datos.setFResolucion(fResolucion);
			} else {
				mensajeError = "La fecha de resolución es obligatoria.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getAPublicacion() != null) && (!formulario.getAPublicacion().equals(Constantes.EMPTY))
					&& (formulario.getMPublicacion() != null) && (!formulario.getMPublicacion().equals(Constantes.EMPTY))
					&& (formulario.getDPublicacion() != null) && (!formulario.getDPublicacion().equals(Constantes.EMPTY))) {
				fPublicacion = formulario.getDPublicacion() + '/' + formulario.getMPublicacion() + '/'
						+ formulario.getAPublicacion();
				datos.setFPublicacion(fPublicacion);
			} else {
				mensajeError = "La fecha de publicación es obligatoria.";
				throw new Exception("Campo obligatorio no informado");
			}
			
			//OCAP-1528
			if (!Utilidades.isNullOrIsEmpty(formulario.getCGrado_id())) {
				datos.setCGradoId(Long.parseLong(formulario.getCGrado_id()));
			}else {
				datos.setCGradoId(0L);
			}
			//FIN OCAP-1528
			

			//OCAP-1866			
			if ((formulario.getAFinPgp() != null) && (!formulario.getAFinPgp().equals(Constantes.EMPTY))
					&& (formulario.getMFinPgp() != null) && (!formulario.getMFinPgp().equals(Constantes.EMPTY))
					&& (formulario.getDFinPgp() != null) && (!formulario.getDFinPgp().equals(Constantes.EMPTY))) {
				fFinPGP = formulario.getDFinPgp() + '/' + formulario.getMFinPgp() + '/' + formulario.getAFinPgp();
				datos.setFFinPGP(fFinPGP);
			}
			//FIN OCAP-1866
			
			
			if ((formulario.getAInicioAlega() != null) && (!formulario.getAInicioAlega().equals(Constantes.EMPTY))
					&& (formulario.getMInicioAlega() != null) && (!formulario.getMInicioAlega().equals(Constantes.EMPTY))
					&& (formulario.getDInicioAlega() != null) && (!formulario.getDInicioAlega().equals(Constantes.EMPTY))) {
				fInicioAlega = formulario.getDInicioAlega() + '/' + formulario.getMInicioAlega() + '/'
						+ formulario.getAInicioAlega();
				datos.setFAlegsolicitud(fInicioAlega);
			}

			if ((formulario.getAInicioMC() != null) && (!formulario.getAInicioMC().equals(Constantes.EMPTY))
					&& (formulario.getMInicioMC() != null) && (!formulario.getMInicioMC().equals(Constantes.EMPTY))
					&& (formulario.getDInicioMC() != null) && (!formulario.getDInicioMC().equals(Constantes.EMPTY))) {
				fInicioMC = formulario.getDInicioMC() + '/' + formulario.getMInicioMC() + '/'
						+ formulario.getAInicioMC();
				datos.setFInicioMC(fInicioMC);
			} else {
				mensajeError = "La fecha de inicio de MC es obligatoria.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getAInicioCC() != null) && (!formulario.getAInicioCC().equals(Constantes.EMPTY))
					&& (formulario.getMInicioCC() != null) && (!formulario.getMInicioCC().equals(Constantes.EMPTY))
					&& (formulario.getDInicioCC() != null) && (!formulario.getDInicioCC().equals(Constantes.EMPTY))) {
				fInicioCC = formulario.getDInicioCC() + '/' + formulario.getMInicioCC() + '/'
						+ formulario.getAInicioCC();
				datos.setFInicioEstudioCC(fInicioCC);
			}

			if ((formulario.getAFinCC() != null) && (!formulario.getAFinCC().equals(Constantes.EMPTY))
					&& (formulario.getMFinCC() != null) && (!formulario.getMFinCC().equals(Constantes.EMPTY))
					&& (formulario.getDFinCC() != null) && (!formulario.getDFinCC().equals(Constantes.EMPTY))) {
				fFinCC = formulario.getDFinCC() + '/' + formulario.getMFinCC() + '/' + formulario.getAFinCC();
				datos.setFFinEstudioCC(fFinCC);
			}

			if ((formulario.getAInicioCA() != null) && (!formulario.getAInicioCA().equals(Constantes.EMPTY))
					&& (formulario.getMInicioCA() != null) && (!formulario.getMInicioCA().equals(Constantes.EMPTY))
					&& (formulario.getDInicioCA() != null) && (!formulario.getDInicioCA().equals(Constantes.EMPTY))) {
				fInicioCA = formulario.getDInicioCA() + '/' + formulario.getMInicioCA() + '/'
						+ formulario.getAInicioCA();
				datos.setFInicioCA(fInicioCA);
			}

			if ((formulario.getAFinCp() != null) && (!formulario.getAFinCp().equals(Constantes.EMPTY))
					&& (formulario.getMFinCp() != null) && (!formulario.getMFinCp().equals(Constantes.EMPTY))
					&& (formulario.getDFinCp() != null) && (!formulario.getDFinCp().equals(Constantes.EMPTY))) {
				fFinCp = formulario.getDFinCp() + '/' + formulario.getMFinCp() + '/' + formulario.getAFinCp();
				datos.setFFinCp(fFinCp);
			} else {
				mensajeError = "La fecha de fin de convocatoria es obligatoria.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getARecGrado() != null) && (!formulario.getARecGrado().equals(Constantes.EMPTY))
					&& (formulario.getMRecGrado() != null) && (!formulario.getMRecGrado().equals(Constantes.EMPTY))
					&& (formulario.getDRecGrado() != null) && (!formulario.getDRecGrado().equals(Constantes.EMPTY))) {
				fRecGrado = formulario.getDRecGrado() + '/' + formulario.getMRecGrado() + '/'
						+ formulario.getARecGrado();
				datos.setFRecGrado(fRecGrado);
			}

			datos.setNDiasRegsolicitud(formulario.getNDias_regsolicitud());
			OCAPConfigApp.logger.info("nDiasRegSolicitud : " + datos.getNDiasRegsolicitud());

			if (formulario.getNDias_regsolicitud() == 0L) {
				mensajeError = "El campo Plazo de Solicitud es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasRevsolicitud(formulario.getNDias_revsolicitud());
			OCAPConfigApp.logger.info("nDiasRevSolicitud : " + datos.getNDiasRevsolicitud());

			if (formulario.getNDias_revsolicitud() == 0L) {
				mensajeError = "El campo Plazo de Revisión es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasPublistado(formulario.getNDias_publistado());
			OCAPConfigApp.logger.info("nDiasPublistado : " + datos.getNDiasPublistado());

			if (formulario.getNDias_publistado() == 0L) {
				mensajeError = "El campo Plazo de Publicación es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasAutoMc(formulario.getNDias_auto_mc());
			OCAPConfigApp.logger.info("nDiasAutoMc : " + datos.getNDiasAutoMc());

			if (formulario.getNDias_auto_mc() == 0L) {
				mensajeError = "El campo Plazo de Autoevaluación de MC es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasValMc(formulario.getNDias_val_mc());
			OCAPConfigApp.logger.info("nDiasValMc : " + datos.getNDiasValMc());

			if (formulario.getNDias_val_mc() == 0L) {
				mensajeError = "El campo Plazo de Valoración de MC es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasInconfMc(formulario.getNDias_inconf_mc());
			OCAPConfigApp.logger.info("nDiasInconfMc : " + datos.getNDiasInconfMc());

			if (formulario.getNDias_inconf_mc() == 0L) {
				mensajeError = "El campo Plazo de Alegaciones de MC es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasAutoCa(formulario.getNDias_auto_ca());
			OCAPConfigApp.logger.info("nDiasAutoCa : " + datos.getNDiasAutoCa());

			if (formulario.getNDias_auto_ca() == 0L) {
				mensajeError = "El campo Plazo de Autoevaluación de CA es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasValCa(formulario.getNDias_val_ca());
			OCAPConfigApp.logger.info("nDiasValCa : " + datos.getNDiasValCa());

			if (formulario.getNDias_val_ca() == 0L) {
				mensajeError = "El campo Plazo de Valoración de CA es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasInconfCa(formulario.getNDias_inconf_ca());
			OCAPConfigApp.logger.info("nDiasInconfCa: " + datos.getNDiasInconfCa());

			/*
			 * OCAP-10 if (formulario.getNDias_inconf_ca() == 0L) { mensajeError
			 * = "El campo Plazo de Alegaciones de CA es obligatorio."; throw
			 * new Exception("Campo obligatorio no informado"); }
			 */
			datos.setNDiasValCc(formulario.getNDias_val_cc());
			OCAPConfigApp.logger.info("nDiasValCc : " + datos.getNDiasValCc());

			if (formulario.getNDias_val_cc() == 0L) {
				mensajeError = "El campo Plazo de Valoración de CC es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasRespinconfMc(formulario.getNDias_respinconf_mc());
			OCAPConfigApp.logger.info("nDiasRespinconfMc : " + datos.getNDiasRespinconfMc());
			/*
			 * OCAP-10 if (formulario.getNDias_respinconf_mc() == 0L) {
			 * mensajeError =
			 * "El campo Plazo de Respuesta de Alegaciones de MC es obligatorio."
			 * ; throw new Exception("Campo obligatorio no informado"); }
			 */
			datos.setNDiasRespinconfCa(formulario.getNDias_respinconf_ca());
			OCAPConfigApp.logger.info("nDiasRespinconfCa : " + datos.getNDiasRespinconfCa());
			/*
			 * OCAP-10 if (formulario.getNDias_respinconf_ca() == 0L) {
			 * mensajeError =
			 * "El campo Plazo de Respuesta de Alegaciones de CA es obligatorio."
			 * ; throw new Exception("Campo obligatorio no informado"); }
			 */
			datos.setNDiasRespinconfCc(formulario.getNDias_respinconf_cc());
			OCAPConfigApp.logger.info("nDiasRespinconfCc : " + datos.getNDiasRespinconfCc());
			/*
			 * OCAP-10 if (formulario.getNDias_respinconf_cc() == 0L) {
			 * mensajeError =
			 * "El campo Plazo de Respuesta de Alegaciones de CC es obligatorio."
			 * ; throw new Exception("Campo obligatorio no informado"); }
			 */
			if ((formulario.getAObservaciones() != null) && (!formulario.getAObservaciones().equals(Constantes.EMPTY))) {
				datos.setAObservaciones(formulario.getAObservaciones());
				OCAPConfigApp.logger.info("aObservaciones: " + datos.getAObservaciones());
			}

			if (Constantes.SI_ESP.equals(formulario.getBCierreSo().toUpperCase()))
				datos.setBCierreSo(Constantes.SI);
			else {
				datos.setBCierreSo(Constantes.NO);
			}
			
			
			// OCAP-101
			
			datos.setDAnioConvocatoria(formulario.getDAnioConvocatoria());
			if (formulario.getDAnioConvocatoria()==null || formulario.getDAnioConvocatoria().equals(Constantes.EMPTY)) {
				mensajeError = "El campo Año de Convocatoria es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}
			
			
			/* OCAP-10*/
			
			if ((formulario.getAInicioSolicitud() != null) && (!formulario.getAInicioSolicitud().equals(Constantes.EMPTY))
					&& (formulario.getMInicioSolicitud() != null) && (!formulario.getMInicioSolicitud().equals(Constantes.EMPTY))
					&& (formulario.getDInicioSolicitud() != null) && (!formulario.getDInicioSolicitud().equals(Constantes.EMPTY))) {
				datos.setFInicioSolicitud(formulario.getDInicioSolicitud() + '/' + formulario.getMInicioSolicitud() + '/' + formulario.getAInicioSolicitud());
			}
			
 
			
			if ((formulario.getAFinSolicitud() != null) && (!formulario.getAFinSolicitud().equals(Constantes.EMPTY))
					&& (formulario.getMFinSolicitud() != null) && (!formulario.getMFinSolicitud().equals(Constantes.EMPTY))
					&& (formulario.getDFinSolicitud() != null) && (!formulario.getDFinSolicitud().equals(Constantes.EMPTY))) {
				datos.setFFinSolicitud(
						formulario.getDFinSolicitud() + '/' + formulario.getMFinSolicitud() + '/' + formulario.getAFinSolicitud());
			}
						
			
			if ((formulario.getAFinMC() != null) && (!formulario.getAFinMC().equals(Constantes.EMPTY))
					&& (formulario.getMFinMC() != null) && (!formulario.getMFinMC().equals(Constantes.EMPTY))
					&& (formulario.getDFinMC() != null) && (!formulario.getDFinMC().equals(Constantes.EMPTY))) {
				datos.setFFinMC(
						formulario.getDFinMC() + '/' + formulario.getMFinMC() + '/' + formulario.getAFinMC());
			}
			
			
			if ((formulario.getAInicioValoracionMC() != null) && (!formulario.getAInicioValoracionMC().equals(Constantes.EMPTY))
					&& (formulario.getMInicioValoracionMC() != null) && (!formulario.getMInicioValoracionMC().equals(Constantes.EMPTY))
					&& (formulario.getDInicioValoracionMC() != null) && (!formulario.getDInicioValoracionMC().equals(Constantes.EMPTY))) {
				datos.setFInicioValoracionMC(
						formulario.getDInicioValoracionMC() + '/' + formulario.getMInicioValoracionMC() + '/' + formulario.getAInicioValoracionMC());
			}
			
			if ((formulario.getAFinValoracionMC() != null) && (!formulario.getAFinValoracionMC().equals(Constantes.EMPTY))
					&& (formulario.getMFinValoracionMC() != null) && (!formulario.getMFinValoracionMC().equals(Constantes.EMPTY))
					&& (formulario.getDFinValoracionMC() != null) && (!formulario.getDFinValoracionMC().equals(Constantes.EMPTY))) {
				datos.setFFinValoracionMC(
						formulario.getDFinValoracionMC() + '/' + formulario.getMFinValoracionMC() + '/' + formulario.getAFinValoracionMC());
			}

			if ((formulario.getAInicioValoracionCC() != null) && (!formulario.getAInicioValoracionCC().equals(Constantes.EMPTY))
					&& (formulario.getMInicioValoracionCC() != null) && (!formulario.getMInicioValoracionCC().equals(Constantes.EMPTY))
					&& (formulario.getDInicioValoracionCC() != null) && (!formulario.getDInicioValoracionCC().equals(Constantes.EMPTY))) {
				datos.setFInicioValoracionCC(
						formulario.getDInicioValoracionCC() + '/' + formulario.getMInicioValoracionCC() + '/' + formulario.getAInicioValoracionCC());
			}
			
			if ((formulario.getAFinValoracionCC() != null) && (!formulario.getAFinValoracionCC().equals(Constantes.EMPTY))
					&& (formulario.getMFinValoracionCC() != null) && (!formulario.getMFinValoracionCC().equals(Constantes.EMPTY))
					&& (formulario.getDFinValoracionCC() != null) && (!formulario.getDFinValoracionCC().equals(Constantes.EMPTY))) {
				datos.setFFinValoracionCC(
						formulario.getDFinValoracionCC() + '/' + formulario.getMFinValoracionCC() + '/' + formulario.getAFinValoracionCC());
			}
			
			if ((formulario.getAFinCA() != null) && (!formulario.getAFinCA().equals(Constantes.EMPTY))
					&& (formulario.getMFinCA() != null) && (!formulario.getMFinCA().equals(Constantes.EMPTY))
					&& (formulario.getDFinCA() != null) && (!formulario.getDFinCA().equals(Constantes.EMPTY))) {
				datos.setFFinCA(
						formulario.getDFinCA() + '/' + formulario.getMFinCA() + '/' + formulario.getAFinCA());
			}
			
			if ((formulario.getAInicioValCA() != null) && (!formulario.getAInicioValCA().equals(Constantes.EMPTY))
					&& (formulario.getMInicioValCA() != null) && (!formulario.getMInicioValCA().equals(Constantes.EMPTY))
					&& (formulario.getDInicioValCA() != null) && (!formulario.getDInicioValCA().equals(Constantes.EMPTY))) {
				datos.setFInicioValCA(
						formulario.getDInicioValCA() + '/' + formulario.getMInicioValCA() + '/' + formulario.getAInicioValCA());
			}
			
			if ((formulario.getAFinValCA() != null) && (!formulario.getAFinValCA().equals(Constantes.EMPTY))
					&& (formulario.getMFinValCA() != null) && (!formulario.getMFinValCA().equals(Constantes.EMPTY))
					&& (formulario.getDFinValCA() != null) && (!formulario.getDFinValCA().equals(Constantes.EMPTY))) {
				datos.setFFinValCA(
						formulario.getDFinValCA() + '/' + formulario.getMFinValCA() + '/' + formulario.getAFinValCA());
			}
			
								
			
			datos.setACreadoPor(
					((JCYLUsuario) request.getSession().getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO)).getUsuario().getC_usr_id());
			int result = oCAPConvocatoriasLN.altaOCAPConvocatorias(datos);
			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
				request.setAttribute("rutaVuelta", "OCAPConvocatorias.do?accion=irInsertar");
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
				sig = "error";
			}

			OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA OCAPConvocatorias --------- ");
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

			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPConvocatoriasLN oCAPConvocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT datos = new OCAPConvocatoriasOT();
			OCAPConvocatoriasForm formulario = (OCAPConvocatoriasForm) form;

			String cConvocatoriaIdS = request.getParameter("cConvocatoriaIdS");
			long cConvocatoriaId;
			if ((cConvocatoriaIdS != null) && (!cConvocatoriaIdS.equals(Constantes.EMPTY))) {
				cConvocatoriaId = Long.parseLong(cConvocatoriaIdS);
				OCAPConfigApp.logger.info("cConvocatoriaId: " + cConvocatoriaId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			datos = oCAPConvocatoriasLN.buscarOCAPConvocatorias(cConvocatoriaId);

			if (datos.getCConvocatoriaId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPConvocatoriasOT");
				sig = "error";
				request.getSession().removeAttribute("conDatosEdicion");
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				OCAPConfigApp.logger.info("Se ha encontrado OCAPConvocatoriasOT");
				request.getSession().setAttribute("conDatosEdicion", Constantes.EMPTY);
				request.getSession().removeAttribute("sinDatosEdicion");
				request.setAttribute("OCAPConvocatoriasOT", datos);

				formulario.setCConvocatoria_id(datos.getCConvocatoriaId());
				formulario.setDNombreCorto(datos.getDNombreCorto());
				formulario.setDNombre(datos.getDNombre());
				formulario.setDResolucion(datos.getFResolucion().substring(0, 2));
				formulario.setMResolucion(datos.getFResolucion().substring(3, 5));
				formulario.setAResolucion(datos.getFResolucion().substring(6, 10));
				formulario.setFPublicacion(datos.getFPublicacion());
				formulario.setDPublicacion(datos.getFPublicacion().substring(0, 2));
				formulario.setMPublicacion(datos.getFPublicacion().substring(3, 5));
				formulario.setAPublicacion(datos.getFPublicacion().substring(6, 10));

				//OCAP-1866				
				if (datos.getFFinPGP() == null) {
					formulario.setFFinPgp(Constantes.EMPTY);
				} else {
					formulario.setFFinPgp(datos.getFFinPGP());
					formulario.setDFinPgp(datos.getFFinPGP().substring(0, 2));
					formulario.setMFinPgp(datos.getFFinPGP().substring(3, 5));
					formulario.setAFinPgp(datos.getFFinPGP().substring(6, 10));
				}
				
				//FIN OCAP-1866
				
				if (datos.getFInicioMC() == null) {
					formulario.setFInicioMC(Constantes.EMPTY);
				} else {
					formulario.setFInicioMC(datos.getFInicioMC());
					formulario.setDInicioMC(datos.getFInicioMC().substring(0, 2));
					formulario.setMInicioMC(datos.getFInicioMC().substring(3, 5));
					formulario.setAInicioMC(datos.getFInicioMC().substring(6, 10));
				}

				if (datos.getFAlegsolicitud() == null) {
					formulario.setFInicioAlega(Constantes.EMPTY);
				} else {
					formulario.setFInicioAlega(datos.getFAlegsolicitud());
					formulario.setDInicioAlega(datos.getFAlegsolicitud().substring(0, 2));
					formulario.setMInicioAlega(datos.getFAlegsolicitud().substring(3, 5));
					formulario.setAInicioAlega(datos.getFAlegsolicitud().substring(6, 10));
				}

				if (datos.getFInicioEstudioCC() == null) {
					formulario.setFInicioEstudioCC(Constantes.EMPTY);
				} else {
					formulario.setFInicioEstudioCC(datos.getFInicioEstudioCC());
					formulario.setDInicioCC(datos.getFInicioEstudioCC().substring(0, 2));
					formulario.setMInicioCC(datos.getFInicioEstudioCC().substring(3, 5));
					formulario.setAInicioCC(datos.getFInicioEstudioCC().substring(6, 10));
				}

				if (datos.getFFinEstudioCC() == null) {
					formulario.setFFinEstudioCC(Constantes.EMPTY);
				} else {
					formulario.setFFinEstudioCC(datos.getFFinEstudioCC());
					formulario.setDFinCC(datos.getFFinEstudioCC().substring(0, 2));
					formulario.setMFinCC(datos.getFFinEstudioCC().substring(3, 5));
					formulario.setAFinCC(datos.getFFinEstudioCC().substring(6, 10));
				}

				if (datos.getFInicioCA() == null) {
					formulario.setFInicioCA(Constantes.EMPTY);
				} else {
					formulario.setFInicioCA(datos.getFInicioCA());
					formulario.setDInicioCA(datos.getFInicioCA().substring(0, 2));
					formulario.setMInicioCA(datos.getFInicioCA().substring(3, 5));
					formulario.setAInicioCA(datos.getFInicioCA().substring(6, 10));
				}

				if (datos.getFFinCp() == null) {
					formulario.setFFinCp(Constantes.EMPTY);
				} else {
					formulario.setFFinCp(datos.getFFinCp());
					formulario.setDFinCp(datos.getFFinCp().substring(0, 2));
					formulario.setMFinCp(datos.getFFinCp().substring(3, 5));
					formulario.setAFinCp(datos.getFFinCp().substring(6, 10));
				}

				if (datos.getFRecGrado() == null) {
					formulario.setFRecGrado(Constantes.EMPTY);
				} else {
					formulario.setFRecGrado(datos.getFRecGrado());
					formulario.setDRecGrado(datos.getFRecGrado().substring(0, 2));
					formulario.setMRecGrado(datos.getFRecGrado().substring(3, 5));
					formulario.setARecGrado(datos.getFRecGrado().substring(6, 10));
				}

				formulario.setNDias_regsolicitud(datos.getNDiasRegsolicitud());
				formulario.setNDias_revsolicitud(datos.getNDiasRevsolicitud());
				formulario.setNDias_publistado(datos.getNDiasPublistado());
				formulario.setNDias_auto_mc(datos.getNDiasAutoMc());
				formulario.setNDias_val_mc(datos.getNDiasValMc());
				formulario.setNDias_inconf_mc(datos.getNDiasInconfMc());
				formulario.setNDias_auto_ca(datos.getNDiasAutoCa());
				formulario.setNDias_val_ca(datos.getNDiasValCa());
				formulario.setNDias_inconf_ca(datos.getNDiasInconfCa());
				formulario.setNDias_val_cc(datos.getNDiasValCc());
				formulario.setNDias_respinconf_mc(datos.getNDiasRespinconfMc());
				formulario.setNDias_respinconf_ca(datos.getNDiasRespinconfCa());
				formulario.setNDias_respinconf_cc(datos.getNDiasRespinconfCc());
				
				if (datos.getFInicioSolicitud() == null) {
					formulario.setFInicioSolicitud(Constantes.EMPTY);
				} else {
					formulario.setFInicioSolicitud(datos.getFInicioSolicitud());
					formulario.setDInicioSolicitud(datos.getFInicioSolicitud().substring(0, 2));
					formulario.setMInicioSolicitud(datos.getFInicioSolicitud().substring(3, 5));
					formulario.setAInicioSolicitud(datos.getFInicioSolicitud().substring(6, 10));
				}
				
				if (datos.getFFinSolicitud() == null) {
					formulario.setFFinSolicitud(Constantes.EMPTY);
				} else {
					formulario.setFFinSolicitud(datos.getFFinSolicitud());
					formulario.setDFinSolicitud(datos.getFFinSolicitud().substring(0, 2));
					formulario.setMFinSolicitud(datos.getFFinSolicitud().substring(3, 5));
					formulario.setAFinSolicitud(datos.getFFinSolicitud().substring(6, 10));
				}
				
				if (datos.getFInicioValoracionMC() == null) {
					formulario.setFInicioValoracionMC(Constantes.EMPTY);
				} else {
					formulario.setFInicioValoracionMC(datos.getFInicioValoracionMC());
					formulario.setDInicioValoracionMC(datos.getFInicioValoracionMC().substring(0, 2));
					formulario.setMInicioValoracionMC(datos.getFInicioValoracionMC().substring(3, 5));
					formulario.setAInicioValoracionMC(datos.getFInicioValoracionMC().substring(6, 10));
				}
				
				if (datos.getFFinValoracionMC() == null) {
					formulario.setFFinValoracionMC(Constantes.EMPTY);
				} else {
					formulario.setFFinValoracionMC(datos.getFFinValoracionMC());
					formulario.setDFinValoracionMC(datos.getFFinValoracionMC().substring(0, 2));
					formulario.setMFinValoracionMC(datos.getFFinValoracionMC().substring(3, 5));
					formulario.setAFinValoracionMC(datos.getFFinValoracionMC().substring(6, 10));
				}
				
				if (datos.getFInicioValoracionCC() == null) {
					formulario.setFInicioValoracionCC(Constantes.EMPTY);
				} else {
					formulario.setFInicioValoracionCC(datos.getFInicioValoracionCC());
					formulario.setDInicioValoracionCC(datos.getFInicioValoracionCC().substring(0, 2));
					formulario.setMInicioValoracionCC(datos.getFInicioValoracionCC().substring(3, 5));
					formulario.setAInicioValoracionCC(datos.getFInicioValoracionCC().substring(6, 10));
				}
				
				if (datos.getFFinValoracionCC() == null) {
					formulario.setFFinValoracionCC(Constantes.EMPTY);
				} else {
					formulario.setFFinValoracionCC(datos.getFFinValoracionCC());
					formulario.setDFinValoracionCC(datos.getFFinValoracionCC().substring(0, 2));
					formulario.setMFinValoracionCC(datos.getFFinValoracionCC().substring(3, 5));
					formulario.setAFinValoracionCC(datos.getFFinValoracionCC().substring(6, 10));
				}
				
				if (datos.getFFinMC() == null) {
					formulario.setFFinMC(Constantes.EMPTY);
				} else {
					formulario.setFFinMC(datos.getFFinMC());
					formulario.setDFinMC(datos.getFFinMC().substring(0, 2));
					formulario.setMFinMC(datos.getFFinMC().substring(3, 5));
					formulario.setAFinMC(datos.getFFinMC().substring(6, 10));
				}
				
				if (datos.getFFinCA() == null) {
					formulario.setFFinCA(Constantes.EMPTY);
				} else {
					formulario.setFFinCA(datos.getFFinCA());
					formulario.setDFinCA(datos.getFFinCA().substring(0, 2));
					formulario.setMFinCA(datos.getFFinCA().substring(3, 5));
					formulario.setAFinCA(datos.getFFinCA().substring(6, 10));
				}
				
				if (datos.getFInicioValCA() == null) {
					formulario.setFInicioValCA(Constantes.EMPTY);
				} else {
					formulario.setFInicioValCA(datos.getFInicioValCA());
					formulario.setDInicioValCA(datos.getFInicioValCA().substring(0, 2));
					formulario.setMInicioValCA(datos.getFInicioValCA().substring(3, 5));
					formulario.setAInicioValCA(datos.getFInicioValCA().substring(6, 10));
				}
				
				if (datos.getFFinValCA() == null) {
					formulario.setFFinValCA(Constantes.EMPTY);
				} else {
					formulario.setFFinValCA(datos.getFFinValCA());
					formulario.setDFinValCA(datos.getFFinValCA().substring(0, 2));
					formulario.setMFinValCA(datos.getFFinValCA().substring(3, 5));
					formulario.setAFinValCA(datos.getFFinValCA().substring(6, 10));
				}

				if (datos.getAObservaciones() == null)
					formulario.setAObservaciones(Constantes.EMPTY);
				else {
					formulario.setAObservaciones(datos.getAObservaciones());
				}
				if (Constantes.SI.equals(datos.getBCierreSo()))
					formulario.setBCierreSo(Constantes.SI_ESP);
				else {
					formulario.setBCierreSo(Constantes.NO);
				}
				
				
				if (datos.getDAnioConvocatoria() == null) {
					formulario.setDAnioConvocatoria(Constantes.EMPTY);
				} else {
					formulario.setDAnioConvocatoria(datos.getDAnioConvocatoria());
				}
				
				if (datos.getCGradoId() == null || datos.getCGradoId() == 0L) {
					formulario.setCGrado_id("");
				} else {
					formulario.setCGrado_id(datos.getCGradoId().toString());
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
		String fResolucion = null;
		String fPublicacion = null;
		String fInicioMC = null;
		String fInicioAlega = null;
		String fInicioCC = null;
		String fFinCC = null;
		String fInicioCA = null;
		String fFinCp = null;
		String fRrecGrado = null;
		String fFinPGP = null;
		try {
			OCAPConfigApp.logger.info(Constantes.EMPTY);
			OCAPConfigApp.logger.info("---------- GRABAR OCAP_CONVOCATORIAS --------- ");

			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPConvocatoriasLN oCAPConvocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT datos = new OCAPConvocatoriasOT();

			OCAPConvocatoriasForm formulario = (OCAPConvocatoriasForm) form;

			if (formulario.getCConvocatoria_id() != 0L) {
				OCAPConfigApp.logger.info("cConvocatoriaId: " + formulario.getCConvocatoria_id());
				datos.setCConvocatoriaId(formulario.getCConvocatoria_id());
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

			if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(Constantes.EMPTY))) {
				datos.setDNombre(formulario.getDNombre());
				OCAPConfigApp.logger.info("dNombre : " + datos.getDNombre());
			}

			if ((formulario.getDNombre() == null) || (formulario.getDNombre().equals(Constantes.EMPTY))) {
				mensajeError = "El campo Nombre es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(Constantes.EMPTY))) {
				datos.setDNombre(formulario.getDNombre());
				OCAPConfigApp.logger.info("dNombre : " + datos.getDNombre());
			}
			
			if ((formulario.getDNombreCorto() != null) && (!formulario.getDNombreCorto().equals(Constantes.EMPTY))) {
				datos.setDNombreCorto(formulario.getDNombreCorto());
				OCAPConfigApp.logger.info("dNombreCorto : " + datos.getDNombreCorto());
			}

			if ((formulario.getDNombreCorto() == null) || (formulario.getDNombreCorto().equals(Constantes.EMPTY))) {
				mensajeError = "El campo Nombre Corto es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getDNombreCorto() != null) && (!formulario.getDNombreCorto().equals(Constantes.EMPTY))) {
				datos.setDNombreCorto(formulario.getDNombreCorto());
				OCAPConfigApp.logger.info("dNombreCorto : " + datos.getDNombreCorto());
			}			
			

			if ((formulario.getAResolucion() != null) && (!formulario.getAResolucion().equals(Constantes.EMPTY))
					&& (formulario.getMResolucion() != null) && (!formulario.getMResolucion().equals(Constantes.EMPTY))
					&& (formulario.getDResolucion() != null) && (!formulario.getDResolucion().equals(Constantes.EMPTY))) {
				fResolucion = formulario.getDResolucion() + '/' + formulario.getMResolucion() + '/'
						+ formulario.getAResolucion();
				datos.setFResolucion(fResolucion);
			} else {
				mensajeError = "La fecha de resolución es obligatoria.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getAPublicacion() != null) && (!formulario.getAPublicacion().equals(Constantes.EMPTY))
					&& (formulario.getMPublicacion() != null) && (!formulario.getMPublicacion().equals(Constantes.EMPTY))
					&& (formulario.getDPublicacion() != null) && (!formulario.getDPublicacion().equals(Constantes.EMPTY))) {
				fPublicacion = formulario.getDPublicacion() + '/' + formulario.getMPublicacion() + '/'
						+ formulario.getAPublicacion();
				datos.setFPublicacion(fPublicacion);
			} else {
				mensajeError = "La fecha de publicación es obligatoria.";
				throw new Exception("Campo obligatorio no informado");
			}
			
			//OCAP-1866			
			if ((formulario.getAFinPgp() != null) && (!formulario.getAFinPgp().equals(Constantes.EMPTY))
					&& (formulario.getMFinPgp() != null) && (!formulario.getMFinPgp().equals(Constantes.EMPTY))
					&& (formulario.getDFinPgp() != null) && (!formulario.getDFinPgp().equals(Constantes.EMPTY))) {
				fFinPGP = formulario.getDFinPgp() + '/' + formulario.getMFinPgp() + '/' + formulario.getAFinPgp();
				datos.setFFinPGP(fFinPGP);
			}

			if ((formulario.getAInicioMC() != null) && (!formulario.getAInicioMC().equals(Constantes.EMPTY))
					&& (formulario.getMInicioMC() != null) && (!formulario.getMInicioMC().equals(Constantes.EMPTY))
					&& (formulario.getDInicioMC() != null) && (!formulario.getDInicioMC().equals(Constantes.EMPTY))) {
				fInicioMC = formulario.getDInicioMC() + '/' + formulario.getMInicioMC() + '/'
						+ formulario.getAInicioMC();
				datos.setFInicioMC(fInicioMC);
			}

			if ((formulario.getAInicioAlega() != null) && (!formulario.getAInicioAlega().equals(Constantes.EMPTY))
					&& (formulario.getMInicioAlega() != null) && (!formulario.getMInicioAlega().equals(Constantes.EMPTY))
					&& (formulario.getDInicioAlega() != null) && (!formulario.getDInicioAlega().equals(Constantes.EMPTY))) {
				fInicioAlega = formulario.getDInicioAlega() + '/' + formulario.getMInicioAlega() + '/'
						+ formulario.getAInicioAlega();
				datos.setFAlegsolicitud(fInicioAlega);
			}

			if ((formulario.getAInicioCC() != null) && (!formulario.getAInicioCC().equals(Constantes.EMPTY))
					&& (formulario.getMInicioCC() != null) && (!formulario.getMInicioCC().equals(Constantes.EMPTY))
					&& (formulario.getDInicioCC() != null) && (!formulario.getDInicioCC().equals(Constantes.EMPTY))) {
				fInicioCC = formulario.getDInicioCC() + '/' + formulario.getMInicioCC() + '/'
						+ formulario.getAInicioCC();
				datos.setFInicioEstudioCC(fInicioCC);
			} else {
				mensajeError = "La fecha de listado provisional de MC es obligatoria.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getAFinCC() != null) && (!formulario.getAFinCC().equals(Constantes.EMPTY))
					&& (formulario.getMFinCC() != null) && (!formulario.getMFinCC().equals(Constantes.EMPTY))
					&& (formulario.getDFinCC() != null) && (!formulario.getDFinCC().equals(Constantes.EMPTY))) {
				fFinCC = formulario.getDFinCC() + '/' + formulario.getMFinCC() + '/' + formulario.getAFinCC();
				datos.setFFinEstudioCC(fFinCC);
			} else {
				mensajeError = "La fecha de listado definitivo de MC es obligatoria.";
				throw new Exception("Campo obligatorio no informado");
			}

			if (DateUtil.convertStringToDate(fFinCC).before(DateUtil.convertStringToDate(fInicioCC))) {
				mensajeError = "La fecha de listado definitivo de MC no puede ser inferior a la del listado provisional de MC.";
				throw new Exception("Fechas de listados erróneas");
			}

			if ((formulario.getAInicioCA() != null) && (!formulario.getAInicioCA().equals(Constantes.EMPTY))
					&& (formulario.getMInicioCA() != null) && (!formulario.getMInicioCA().equals(Constantes.EMPTY))
					&& (formulario.getDInicioCA() != null) && (!formulario.getDInicioCA().equals(Constantes.EMPTY))) {
				fInicioCA = formulario.getDInicioCA() + '/' + formulario.getMInicioCA() + '/'
						+ formulario.getAInicioCA();
				datos.setFInicioCA(fInicioCA);
			}

			if ((formulario.getAFinCp() != null) && (!formulario.getAFinCp().equals(Constantes.EMPTY))
					&& (formulario.getMFinCp() != null) && (!formulario.getMFinCp().equals(Constantes.EMPTY))
					&& (formulario.getDFinCp() != null) && (!formulario.getDFinCp().equals(Constantes.EMPTY))) {
				fFinCp = formulario.getDFinCp() + '/' + formulario.getMFinCp() + '/' + formulario.getAFinCp();
				datos.setFFinCp(fFinCp);
			} else {
				mensajeError = "La fecha de fin de convocatoria es obligatoria.";
				throw new Exception("Campo obligatorio no informado");
			}

			if ((formulario.getARecGrado() != null) && (!formulario.getARecGrado().equals(Constantes.EMPTY))
					&& (formulario.getMRecGrado() != null) && (!formulario.getMRecGrado().equals(Constantes.EMPTY))
					&& (formulario.getDRecGrado() != null) && (!formulario.getDRecGrado().equals(Constantes.EMPTY))) {
				fRrecGrado = formulario.getDRecGrado() + '/' + formulario.getMRecGrado() + '/'
						+ formulario.getARecGrado();
				datos.setFRecGrado(fRrecGrado);
			}

			datos.setNDiasRegsolicitud(formulario.getNDias_regsolicitud());
			OCAPConfigApp.logger.info("nDiasRegSolicitud : " + datos.getNDiasRegsolicitud());

			if (formulario.getNDias_regsolicitud() == 0L) {
				mensajeError = "El campo Plazo de Solicitud es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasRevsolicitud(formulario.getNDias_revsolicitud());
			OCAPConfigApp.logger.info("nDiasRevSolicitud : " + datos.getNDiasRevsolicitud());

			if (formulario.getNDias_revsolicitud() == 0L) {
				mensajeError = "El campo Plazo de Revisión es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasPublistado(formulario.getNDias_publistado());
			OCAPConfigApp.logger.info("nDiasPublistado : " + datos.getNDiasPublistado());

			if (formulario.getNDias_publistado() == 0L) {
				mensajeError = "El campo Plazo de Publicación es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasAutoMc(formulario.getNDias_auto_mc());
			OCAPConfigApp.logger.info("nDiasAutoMc : " + datos.getNDiasAutoMc());

			if (formulario.getNDias_auto_mc() == 0L) {
				mensajeError = "El campo Plazo de Autoevaluación de MC es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasValMc(formulario.getNDias_val_mc());
			OCAPConfigApp.logger.info("nDiasValMc : " + datos.getNDiasValMc());

			if (formulario.getNDias_val_mc() == 0L) {
				mensajeError = "El campo Plazo de Valoración de MC es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasInconfMc(formulario.getNDias_inconf_mc());
			OCAPConfigApp.logger.info("nDiasInconfMc : " + datos.getNDiasInconfMc());

			if (formulario.getNDias_inconf_mc() == 0L) {
				mensajeError = "El campo Plazo de Alegaciones de MC es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasAutoCa(formulario.getNDias_auto_ca());
			OCAPConfigApp.logger.info("nDiasAutoCa : " + datos.getNDiasAutoCa());

			if (formulario.getNDias_auto_ca() == 0L) {
				mensajeError = "El campo Plazo de Autoevaluación de CA es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasValCa(formulario.getNDias_val_ca());
			OCAPConfigApp.logger.info("nDiasValCa : " + datos.getNDiasValCa());

			if (formulario.getNDias_val_ca() == 0L) {
				mensajeError = "El campo Plazo de Valoración de CA es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasInconfCa(formulario.getNDias_inconf_ca());
			OCAPConfigApp.logger.info("nDiasInconfCa: " + datos.getNDiasInconfCa());

			/*
			 * OCAP-10 if (formulario.getNDias_inconf_ca() == 0L) { mensajeError
			 * = "El campo Plazo de Alegaciones de CA es obligatorio."; throw
			 * new Exception("Campo obligatorio no informado"); }
			 */

			datos.setNDiasValCc(formulario.getNDias_val_cc());
			OCAPConfigApp.logger.info("nDiasValCc : " + datos.getNDiasValCc());

			if (formulario.getNDias_val_cc() == 0L) {
				mensajeError = "El campo Plazo de Valoración de CC es obligatorio.";
				throw new Exception("Campo obligatorio no informado");
			}

			datos.setNDiasRespinconfMc(formulario.getNDias_respinconf_mc());
			OCAPConfigApp.logger.info("nDiasRespinconfMc : " + datos.getNDiasRespinconfMc());

			/*
			 * OCAP-10 if (formulario.getNDias_respinconf_mc() == 0L) {
			 * mensajeError =
			 * "El campo Plazo de Respuesta de Alegaciones de MC es obligatorio."
			 * ; throw new Exception("Campo obligatorio no informado"); }
			 */

			datos.setNDiasRespinconfCa(formulario.getNDias_respinconf_ca());
			OCAPConfigApp.logger.info("nDiasRespinconfCa : " + datos.getNDiasRespinconfCa());

			/*
			 * OCAP-10 if (formulario.getNDias_respinconf_ca() == 0L) {
			 * mensajeError =
			 * "El campo Plazo de Respuesta de Alegaciones de CA es obligatorio."
			 * ; throw new Exception("Campo obligatorio no informado"); }
			 */

			datos.setNDiasRespinconfCc(formulario.getNDias_respinconf_cc());
			OCAPConfigApp.logger.info("nDiasRespinconfCc : " + datos.getNDiasRespinconfCc());

			/*
			 * OCAP-10 if (formulario.getNDias_respinconf_cc() == 0L) {
			 * mensajeError =
			 * "El campo Plazo de Respuesta de Alegaciones de CC es obligatorio."
			 * ; throw new Exception("Campo obligatorio no informado"); }
			 */

			if ((formulario.getAObservaciones() != null) && (!formulario.getAObservaciones().equals(Constantes.EMPTY))) {
				datos.setAObservaciones(formulario.getAObservaciones());
				OCAPConfigApp.logger.info("aObservaciones: " + datos.getAObservaciones());
			}

			if (Constantes.SI_ESP.equals(formulario.getBCierreSo().toUpperCase()))
				datos.setBCierreSo(Constantes.SI);
			else {
				datos.setBCierreSo(Constantes.NO);
			}
			
			/* OCAP-10*/
			
			if ((formulario.getAInicioSolicitud() != null) && (!formulario.getAInicioSolicitud().equals(Constantes.EMPTY))
					&& (formulario.getMInicioSolicitud() != null) && (!formulario.getMInicioSolicitud().equals(Constantes.EMPTY))
					&& (formulario.getDInicioSolicitud() != null) && (!formulario.getDInicioSolicitud().equals(Constantes.EMPTY))) {
				datos.setFInicioSolicitud(formulario.getDInicioSolicitud() + '/' + formulario.getMInicioSolicitud() + '/' + formulario.getAInicioSolicitud());
			}
			
 
			
			if ((formulario.getAFinSolicitud() != null) && (!formulario.getAFinSolicitud().equals(Constantes.EMPTY))
					&& (formulario.getMFinSolicitud() != null) && (!formulario.getMFinSolicitud().equals(Constantes.EMPTY))
					&& (formulario.getDFinSolicitud() != null) && (!formulario.getDFinSolicitud().equals(Constantes.EMPTY))) {
				datos.setFFinSolicitud(
						formulario.getDFinSolicitud() + '/' + formulario.getMFinSolicitud() + '/' + formulario.getAFinSolicitud());
			}
						
			
			if ((formulario.getAFinMC() != null) && (!formulario.getAFinMC().equals(Constantes.EMPTY))
					&& (formulario.getMFinMC() != null) && (!formulario.getMFinMC().equals(Constantes.EMPTY))
					&& (formulario.getDFinMC() != null) && (!formulario.getDFinMC().equals(Constantes.EMPTY))) {
				datos.setFFinMC(
						formulario.getDFinMC() + '/' + formulario.getMFinMC() + '/' + formulario.getAFinMC());
			}
			
			
			if ((formulario.getAInicioValoracionMC() != null) && (!formulario.getAInicioValoracionMC().equals(Constantes.EMPTY))
					&& (formulario.getMInicioValoracionMC() != null) && (!formulario.getMInicioValoracionMC().equals(Constantes.EMPTY))
					&& (formulario.getDInicioValoracionMC() != null) && (!formulario.getDInicioValoracionMC().equals(Constantes.EMPTY))) {
				datos.setFInicioValoracionMC(
						formulario.getDInicioValoracionMC() + '/' + formulario.getMInicioValoracionMC() + '/' + formulario.getAInicioValoracionMC());
			}
			
			if ((formulario.getAFinValoracionMC() != null) && (!formulario.getAFinValoracionMC().equals(Constantes.EMPTY))
					&& (formulario.getMFinValoracionMC() != null) && (!formulario.getMFinValoracionMC().equals(Constantes.EMPTY))
					&& (formulario.getDFinValoracionMC() != null) && (!formulario.getDFinValoracionMC().equals(Constantes.EMPTY))) {
				datos.setFFinValoracionMC(
						formulario.getDFinValoracionMC() + '/' + formulario.getMFinValoracionMC() + '/' + formulario.getAFinValoracionMC());
			}

			if ((formulario.getAInicioValoracionCC() != null) && (!formulario.getAInicioValoracionCC().equals(Constantes.EMPTY))
					&& (formulario.getMInicioValoracionCC() != null) && (!formulario.getMInicioValoracionCC().equals(Constantes.EMPTY))
					&& (formulario.getDInicioValoracionCC() != null) && (!formulario.getDInicioValoracionCC().equals(Constantes.EMPTY))) {
				datos.setFInicioValoracionCC(
						formulario.getDInicioValoracionCC() + '/' + formulario.getMInicioValoracionCC() + '/' + formulario.getAInicioValoracionCC());
			}
			
			if ((formulario.getAFinValoracionCC() != null) && (!formulario.getAFinValoracionCC().equals(Constantes.EMPTY))
					&& (formulario.getMFinValoracionCC() != null) && (!formulario.getMFinValoracionCC().equals(Constantes.EMPTY))
					&& (formulario.getDFinValoracionCC() != null) && (!formulario.getDFinValoracionCC().equals(Constantes.EMPTY))) {
				datos.setFFinValoracionCC(
						formulario.getDFinValoracionCC() + '/' + formulario.getMFinValoracionCC() + '/' + formulario.getAFinValoracionCC());
			}
			
			if ((formulario.getAFinCA() != null) && (!formulario.getAFinCA().equals(Constantes.EMPTY))
					&& (formulario.getMFinCA() != null) && (!formulario.getMFinCA().equals(Constantes.EMPTY))
					&& (formulario.getDFinCA() != null) && (!formulario.getDFinCA().equals(Constantes.EMPTY))) {
				datos.setFFinCA(
						formulario.getDFinCA() + '/' + formulario.getMFinCA() + '/' + formulario.getAFinCA());
			}
			
			if ((formulario.getAInicioValCA() != null) && (!formulario.getAInicioValCA().equals(Constantes.EMPTY))
					&& (formulario.getMInicioValCA() != null) && (!formulario.getMInicioValCA().equals(Constantes.EMPTY))
					&& (formulario.getDInicioValCA() != null) && (!formulario.getDInicioValCA().equals(Constantes.EMPTY))) {
				datos.setFInicioValCA(
						formulario.getDInicioValCA() + '/' + formulario.getMInicioValCA() + '/' + formulario.getAInicioValCA());
			}
			
			if ((formulario.getAFinValCA() != null) && (!formulario.getAFinValCA().equals(Constantes.EMPTY))
					&& (formulario.getMFinValCA() != null) && (!formulario.getMFinValCA().equals(Constantes.EMPTY))
					&& (formulario.getDFinValCA() != null) && (!formulario.getDFinValCA().equals(Constantes.EMPTY))) {
				datos.setFFinValCA(
						formulario.getDFinValCA() + '/' + formulario.getMFinValCA() + '/' + formulario.getAFinValCA());
			}
			
			//OCAP-101			
			datos.setDAnioConvocatoria(formulario.getDAnioConvocatoria());
			
			
			//OCAP-1528
			if (!Utilidades.isNullOrIsEmpty(formulario.getCGrado_id())) {
				datos.setCGradoId(Long.parseLong(formulario.getCGrado_id()));
			}
			//FIN OCAP-1528
			
			int result = oCAPConvocatoriasLN.modificacionOCAPConvocatorias(datos);

			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha modificado con éxito");

				request.setAttribute("rutaVuelta",
						"OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=" + datos.getCConvocatoriaId());
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
				sig = "error";
			}

			OCAPConfigApp.logger.info("---------- FIN GRABAR MODIFICACION OCAPConvocatorias --------- ");
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
