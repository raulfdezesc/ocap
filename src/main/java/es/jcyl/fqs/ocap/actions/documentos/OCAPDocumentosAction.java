package es.jcyl.fqs.ocap.actions.documentos;

import es.jcyl.cf.seguridad.util.Usuario;
import es.jcyl.fqs.ocap.actionforms.documentos.OCAPDocumentosForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.creditosCuestionarios.OCAPCreditosCuestionariosLN;
import es.jcyl.fqs.ocap.ln.cuestionarios.OCAPCuestionariosLN;
import es.jcyl.fqs.ocap.ln.documentos.OCAPDocumentosLN;
import es.jcyl.fqs.ocap.ln.estadosCuestionario.OCAPEstadosCuestionarioLN;
import es.jcyl.fqs.ocap.ln.expedienteCarrera.OCAPExpedienteCarreraLN;
import es.jcyl.fqs.ocap.ln.itinerarios.OCAPItinerariosLN;
import es.jcyl.fqs.ocap.ln.solicitudes.OCAPSolicitudesLN;
import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
import es.jcyl.fqs.ocap.ot.areasItinerarios.OCAPAreasItinerariosOT;
import es.jcyl.fqs.ocap.ot.creditosCuestionarios.OCAPCreditosCuestionariosOT;
import es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT;
import es.jcyl.fqs.ocap.ot.documentos.OCAPDocumentosOT;
import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;
import es.jcyl.fqs.ocap.ot.itinerarios.OCAPItinerariosOT;
import es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.TrataError;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.fqs.ocap.util.xml.GestorXML;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLUsuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.upload.FormFile;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class OCAPDocumentosAction extends OCAPGenericAction {
	Logger logger;
	Logger loggerXML;

	private void $init$() {
		this.logger = OCAPConfigApp.logger;
		this.loggerXML = OCAPConfigApp.loggerXML;
	}

	public ActionForward irAltaDocumento(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		OCAPUsuariosLN usuariosLN = null;
		OCAPUsuariosOT usuarioOT = null;

		ActionErrors errors = new ActionErrors();
		OCAPDocumentosLN docuLN = null;
		OCAPDocumentosOT docuOT = null;

		ArrayList listado = null;
		int tamanoListado = 0;
		OCAPCuestionariosLN cuestionariosLN = null;

		ArrayList listaAreas = null;
		OCAPSolicitudesOT solicitudesOT = null;
		OCAPSolicitudesLN solicitudesLN = null;
		ArrayList listadoCuestionarios = null;
		long cExpId = 0L;
		OCAPItinerariosOT itinerarioOT = null;
		OCAPItinerariosLN itinerariosLN = null;
		String siguientePagina = "mostrar";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irAltaDocumento: Inicio");

			if ((request.getParameter("pagina") != null) && (request.getParameter("pagina").equals("evidenciaDoc"))) {
				siguientePagina = "mostrarDocEvidencia";
			}
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			solicitudesOT = new OCAPSolicitudesOT();
			solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);

			if ((((OCAPDocumentosForm) form).getCExpId() != null)
					&& (((OCAPDocumentosForm) form).getCExpId().longValue() != 0L))
				cExpId = ((OCAPDocumentosForm) form).getCExpId().longValue();
			else if (request.getParameter("cExpId") != null)
				cExpId = Long.parseLong(request.getParameter("cExpId"));
			else
				cExpId = Long.parseLong(session.getAttribute("idExpediente").toString());

			if (request.getParameter("tarea") != null)
				((OCAPDocumentosForm) form).setTarea(request.getParameter("tarea"));
			if (request.getParameter("tareaDocu") != null) {
				((OCAPDocumentosForm) form).setTareaDocu(request.getParameter("tareaDocu"));
			}
			if (request.getAttribute("rutaVuelta")!=null && request.getAttribute("rutaVuelta").toString().contains("docAdjuntado")){
				((OCAPDocumentosForm) form).setNuevoDocumento("S");
			} else {
				((OCAPDocumentosForm) form).setNuevoDocumento("N");	
			}
			solicitudesOT.setCExp_id(cExpId);
			solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);

			usuariosLN = new OCAPUsuariosLN(jcylUsuario);
			usuarioOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(), cExpId, jcylUsuario);

			if (usuarioOT == null) {
				return mapping.findForward("irNoExisteSolicitud");
			}

			DecimalFormat formato = new DecimalFormat("000000");
			((OCAPDocumentosForm) form).setCodigoId("EPR" + formato.format(cExpId));

			if ((usuarioOT.getFFinCA() != null) && (new Date().after(usuarioOT.getFFinCA())))
				request.setAttribute("permisoFinalizar", Constantes.SI);
			else {
				request.setAttribute("permisoFinalizar", Constantes.NO);
			}
			((OCAPDocumentosForm) form).setDNombre(solicitudesOT.getDNombre());
			((OCAPDocumentosForm) form).setDApellido1(solicitudesOT.getDApellido1());
			((OCAPDocumentosForm) form).setDCorreoelectronico(
					solicitudesOT.getDCorreoelectronico() == null ? "-" : solicitudesOT.getDCorreoelectronico());
			((OCAPDocumentosForm) form).setCDni(solicitudesOT.getCDni());
			((OCAPDocumentosForm) form).setCDniReal(solicitudesOT.getCDniReal());
			if (solicitudesOT.getNTelefono1().equals("0"))
				((OCAPDocumentosForm) form).setNTelefono1("-");
			else {
				((OCAPDocumentosForm) form).setNTelefono1(String.valueOf(solicitudesOT.getNTelefono1()));
			}
			if (solicitudesOT.getNTelefono2().equals("0"))
				((OCAPDocumentosForm) form).setNTelefono2("-");
			else {
				((OCAPDocumentosForm) form).setNTelefono2(String.valueOf(solicitudesOT.getNTelefono2()));
			}
			((OCAPDocumentosForm) form).setBSexo(solicitudesOT.getBSexo());
			((OCAPDocumentosForm) form)
					.setDDomicilio(solicitudesOT.getDDomicilio() == null ? "-" : solicitudesOT.getDDomicilio());
			((OCAPDocumentosForm) form).setDLocalidadUsu(
					solicitudesOT.getDLocalidadUsu() == null ? "-" : solicitudesOT.getDLocalidadUsu());
			((OCAPDocumentosForm) form).setCProvinciaUsu_id(solicitudesOT.getCProvinciaUsu_id());
			((OCAPDocumentosForm) form).setDProvinciaUsu(
					solicitudesOT.getDProvinciaUsu() == null ? "-" : solicitudesOT.getDProvinciaUsu());
			formato = new DecimalFormat("00000");
			((OCAPDocumentosForm) form).setCPostalUsu(solicitudesOT.getCPostalUsu() == null ? "-"
					: formato.format(Long.parseLong(solicitudesOT.getCPostalUsu())));

			((OCAPDocumentosForm) form).setCJuridico(solicitudesOT.getCJuridico());

			((OCAPDocumentosForm) form).setDProcedimientoNombre(solicitudesOT.getDProcedimiento());
			((OCAPDocumentosForm) form).setCSitAdministrativaAuxId(solicitudesOT.getCSitAdministrativaAuxId());
			((OCAPDocumentosForm) form).setDSitAdministrativaAuxOtros(solicitudesOT.getDSitAdministrativaAuxOtros());
			((OCAPDocumentosForm) form).setDEstatutarioActual_nombre(solicitudesOT.getDEstatutActual_nombre());
			((OCAPDocumentosForm) form).setDProfesionalActual_nombre(solicitudesOT.getDProfesionalActual_nombre());
			((OCAPDocumentosForm) form).setDEspecActual_nombre(solicitudesOT.getDEspecActual_nombre());
			((OCAPDocumentosForm) form).setCEspecActual_id(String.valueOf(solicitudesOT.getCEspecActual_id()));
			((OCAPDocumentosForm) form).setDEstatutario_nombre(solicitudesOT.getDEstatut_nombre());
			((OCAPDocumentosForm) form).setDProfesional_nombre(solicitudesOT.getDProfesional_nombre());
			((OCAPDocumentosForm) form).setCProfesional_id(String.valueOf(solicitudesOT.getCProfesional_id()));
			((OCAPDocumentosForm) form).setDEspec_nombre(solicitudesOT.getDEspec_nombre());
			((OCAPDocumentosForm) form).setDProvincia(Cadenas.capitalizar(solicitudesOT.getDProvincia()));
			((OCAPDocumentosForm) form).setACiudad(Cadenas.capitalizar(solicitudesOT.getACiudad()));
			((OCAPDocumentosForm) form).setDTipogerencia_desc(solicitudesOT.getDTipogerencia_desc());
			((OCAPDocumentosForm) form).setDGerencia_nombre(solicitudesOT.getDGerencia_nombre());
			((OCAPDocumentosForm) form).setDCentrotrabajo_nombre(solicitudesOT.getDCentrotrabajo_nombre());
			((OCAPDocumentosForm) form).setDGrado_des(solicitudesOT.getDGrado_des());
			((OCAPDocumentosForm) form).setCConvocatoriaId(String.valueOf(solicitudesOT.getCConvocatoriaId()));
			((OCAPDocumentosForm) form).setBValidacion_cc(solicitudesOT.getBValidacionCC());
			((OCAPDocumentosForm) form).setListaCreditos(solicitudesOT.getListaCreditos());

			cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
			listaAreas = cuestionariosLN.listadoOCAPCuestionariosPorItinerario(usuarioOT, jcylUsuario);
			((OCAPDocumentosForm) form).setListadoAreas(listaAreas);
			if ((listaAreas != null) && (listaAreas.size() != 0))
				((OCAPDocumentosForm) form)
						.setCItinerarioId(((OCAPAreasItinerariosOT) listaAreas.get(0)).getCItinerarioId());
			else
				return mapping.findForward("irNoExisteItinerario");

			if ((((OCAPDocumentosForm) form).getCAreaId() != null)
					&& (!"".equals(((OCAPDocumentosForm) form).getCAreaId()))) {
				listadoCuestionarios = cuestionariosLN.listadoOCAPCuestionariosAreaDocumentos(
						((OCAPDocumentosForm) form).getCExpId(), ((OCAPDocumentosForm) form).getCItinerarioId(),
						((OCAPDocumentosForm) form).getCAreaId());
				((OCAPDocumentosForm) form).setListadoCuestionarios(listadoCuestionarios);
			} else {
				((OCAPDocumentosForm) form).setListadoCuestionarios(new ArrayList());
			}
			docuLN = new OCAPDocumentosLN();
			if ("modificacion".equals(((OCAPDocumentosForm) form).getTareaDocu())) {
				docuOT = docuLN.buscarDocumento(((OCAPDocumentosForm) form).getCDocumento_id());

				((OCAPDocumentosForm) form).setCExpId(docuOT.getCExpId());
				((OCAPDocumentosForm) form).setDTitulo(docuOT.getDTitulo());
				((OCAPDocumentosForm) form).setDDescripcion(docuOT.getDDescripcion());
				((OCAPDocumentosForm) form).setAExt(docuOT.getAExt());
				((OCAPDocumentosForm) form).setCUsuario_id(docuOT.getCUsuario_id());
			}

			if (cExpId != 0L) {
				((OCAPDocumentosForm) form).setCExpId(Long.valueOf(cExpId));
				listado = docuLN.buscarDocumentos(((OCAPDocumentosForm) form).getCExpId().longValue());
				((OCAPDocumentosForm) form).setListaDocumentos(listado);
				tamanoListado = listado.size();
			}

			ArrayList listadoEvidenciasUsadas = new ArrayList();
			OCAPDocumentosOT docuOTAux = null;
			for (int nElem = 0; nElem < tamanoListado; nElem++) {
				docuOTAux = (OCAPDocumentosOT) listado.get(nElem);
				listadoEvidenciasUsadas.add(docuOTAux.getDTitulo());
			}

			itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
			itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());
			((OCAPDocumentosForm) form).setNEvidencias(String.valueOf(itinerarioOT.getNEvidencias()));

			ArrayList listadoEvidenciaSinUsar = new ArrayList();

			for (int contEvi = 1; contEvi <= 99; contEvi++) {
				if (!listadoEvidenciasUsadas.contains(Integer.toString(contEvi)))
					listadoEvidenciaSinUsar.add(Integer.toString(contEvi));
			}
			((OCAPDocumentosForm) form).setListadoEvidenciasNoUsadas(listadoEvidenciaSinUsar);

			OCAPConfigApp.logger.info(getClass().getName() + " irAltaDocumento: Fin");
		} catch (SQLException e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(siguientePagina);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward listarFormulariosArea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoCuestionarios = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " listarFormulariosArea: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPCuestionariosLN cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
			listadoCuestionarios = cuestionariosLN.listadoOCAPCuestionariosAreaDocumentos(
					((OCAPDocumentosForm) form).getCExpId(), ((OCAPDocumentosForm) form).getCItinerarioId(),
					((OCAPDocumentosForm) form).getCAreaId());
			((OCAPDocumentosForm) form).setListadoCuestionarios(listadoCuestionarios);

			OCAPConfigApp.logger.info(getClass().getName() + " listarFormulariosArea: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mostrar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward guardarDocumento(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OCAPConfigApp.logger.info(getClass().getName() + " guardarDocumento: Inicio");

		ActionErrors errors = new ActionErrors();
		OCAPDocumentosOT documentosOT = new OCAPDocumentosOT();
		OCAPDocumentosLN documentosLN = new OCAPDocumentosLN();

		ActionMessages messages = new ActionMessages();
		try {
			request.setAttribute("verFinProcesoDocumento", Constantes.NO);
			int bytesFichero = ((OCAPDocumentosForm) form).getADatos().getFileSize();

			if (bytesFichero > 1048576 * Integer.valueOf(JCYLConfiguracion.getValor("TAMNO_FICHERO")).intValue()) {
				messages.add("errorTamanoFichero",
						new ActionMessage("errors.tamanoFichero.noAdmintido",
								((OCAPDocumentosForm) form).getADatos().getFileName(),
								JCYLConfiguracion.getValor("TAMNO_FICHERO")));
				saveMessages(request, messages);
				return mapping.findForward("mostrarDocEvidencia");
			}

			documentosOT.setCExpId(((OCAPDocumentosForm) form).getCExpId());

			if (request.getParameter("expId") != null) {
				documentosOT.setCExpId(Long.valueOf(request.getParameter("expId")));
			}

			documentosOT.setADatos(((OCAPDocumentosForm) form).getADatos().getInputStream());
			documentosOT.setATipo_documento(((OCAPDocumentosForm) form).getADatos().getContentType());
			String nombre = ((OCAPDocumentosForm) form).getADatos().getFileName();
			String ext = nombre.substring(nombre.lastIndexOf("."));
			documentosOT.setAExt(ext.substring(1));

			if (request.getParameter("cEvidenciaID") != null)
				documentosOT.setDTitulo(request.getParameter("cEvidenciaID"));
			else {
				documentosOT.setDTitulo(((OCAPDocumentosForm) form).getDTitulo());
			}
			if (request.getParameter("nDocumento") != null)
				documentosOT.setNDocumentos(Integer.valueOf(request.getParameter("nDocumento")));
			else {
				documentosOT.setNDocumentos(Integer.valueOf(((OCAPDocumentosForm) form).getNDocumentos()));
			}

			documentosOT.setDDescripcion(nombre);

			if (request.getParameter("cuestionarioId") != null)
				documentosOT.setCCuestionarioId(Long.valueOf(request.getParameter("cuestionarioId")));
			else {
				documentosOT.setCCuestionarioId(Long.valueOf(((OCAPDocumentosForm) form).getCCuestionarioId()));
			}

			documentosOT.setACreadoPor(
					((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());

			documentosLN.guardarDocumento(documentosOT);

			if ((request.getParameter("pagina") != null) && (request.getParameter("pagina").equals("evidenciaDoc")))
				request.setAttribute("rutaVuelta", "OCAPDocumentos.do?accion=irAltaDocumento&pagina=evidenciaDoc&docAdjuntado=S");
			else {
				request.setAttribute("rutaVuelta", "OCAPDocumentos.do?accion=irAltaDocumento");
			}
			OCAPConfigApp.logger.info(getClass().getName() + " guardarDocumento: Fin");
		} catch (SQLException e) {
			this.logger.error(e);
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.insertar"));
		} catch (Exception e) {
			this.logger.error(e);
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		}

		if (errors.size() != 0) {
			saveErrors(request, errors);

			return mapping.findForward("fallo");
		}
		
		return irAltaDocumento(mapping, form, request, response);
	}

	public ActionForward modificarDocumento(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OCAPConfigApp.logger.info(getClass().getName() + " modificarDocumento: Inicio");

		ActionErrors errors = new ActionErrors();
		OCAPDocumentosOT documentosOT = new OCAPDocumentosOT();
		OCAPDocumentosLN documentosLN = new OCAPDocumentosLN();
		try {
			if (!((OCAPDocumentosForm) form).getADatos().getFileName().equals("")) {
				documentosOT.setADatos(((OCAPDocumentosForm) form).getADatos().getInputStream());
				documentosOT.setATipo_documento(((OCAPDocumentosForm) form).getADatos().getContentType());
				String nombre = ((OCAPDocumentosForm) form).getADatos().getFileName();
				String ext = nombre.substring(nombre.lastIndexOf("."));
				documentosOT.setAExt(ext.substring(1));
			}
			documentosOT.setDTitulo(((OCAPDocumentosForm) form).getDTitulo());
			documentosOT.setDDescripcion(((OCAPDocumentosForm) form).getDDescripcion());

			documentosOT.setCDocumento_id(((OCAPDocumentosForm) form).getCDocumento_id());

			documentosLN.modificarDocumento(documentosOT);

			OCAPConfigApp.logger.info(getClass().getName() + " modificarDocumento: Fin");
		} catch (SQLException e) {
			this.logger.error(e);
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.insertar"));
		} catch (Exception e) {
			this.logger.error(e);
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		}

		if (errors.size() != 0) {
			saveErrors(request, errors);

			return mapping.findForward("lovError");
		}

		return mapping.findForward("lovExito");
	}

	public ActionForward abrirDocumento(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OCAPConfigApp.logger.info(getClass().getName() + " abrirDocumento: Inicio");

		ActionErrors errors = new ActionErrors();

		OCAPDocumentosLN documentosLN = new OCAPDocumentosLN();
		OCAPDocumentosOT documentosOT = new OCAPDocumentosOT();

		OutputStream documento = response.getOutputStream();
		try {
			((OCAPDocumentosForm) form).setCExpId(Long.valueOf(request.getParameter("expId")));
			((OCAPDocumentosForm) form).setCDocumento_id(Long.parseLong(request.getParameter("documentoId")));
			documentosOT = documentosLN.buscarDocumento(((OCAPDocumentosForm) form).getCDocumento_id());

			response.setContentType(documentosOT.getATipo_documento());
			response.setHeader("Content-Disposition",
					"attachment;filename=" + documentosOT.getDTitulo() + "." + documentosOT.getAExt());

			InputStream fichero = documentosOT.getADatos();
			int length = -1;
			byte[] buffer = new byte[1024];

			while ((length = fichero.read(buffer)) > -1) {
				documento.write(buffer, 0, length);
			}

			fichero.close();
			documento.flush();
			documento.close();

			OCAPConfigApp.logger.info(getClass().getName() + " abrirDocumento: Fin");
		} catch (SQLException e) {
			this.logger.error(e);
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		} catch (Exception e) {
			this.logger.error(e);
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		}

		if (errors.size() != 0) {
			saveErrors(request, errors);

			return mapping.findForward("fallo");
		}

		return mapping.findForward("");
	}

	public ActionForward borrarDocumento(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OCAPConfigApp.logger.info(getClass().getName() + " borrarDocumento: Inicio");
		ActionErrors errors = new ActionErrors();

		OCAPDocumentosLN documentosLN = new OCAPDocumentosLN();
		long docuId = 0L;
		try {
			request.setAttribute("verFinProcesoDocumento", Constantes.NO);

			if (((OCAPDocumentosForm) form).getCDocumento_id() == 0L)
				docuId = Long.parseLong(request.getParameter("cDocId"));
			else {
				docuId = ((OCAPDocumentosForm) form).getCDocumento_id();
			}
			documentosLN.borrarDocumento(docuId);

			if (((OCAPDocumentosForm) form).getCExpId() != null)
				request.setAttribute("cExpId", ((OCAPDocumentosForm) form).getCExpId());
			else if (request.getParameter("cExpId") != null)
				request.setAttribute("cExpId", Long.valueOf(request.getParameter("cExpId")));

			OCAPConfigApp.logger.info(getClass().getName() + " borrarDocumento: Fin");
		} catch (SQLException e) {
			this.logger.error(e);
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.borrar"));
		} catch (Exception e) {
			this.logger.error(e);
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		}

		if (errors.size() != 0) {
			saveErrors(request, errors);

			return mapping.findForward("fallo");
		}

		this.logger.info("Saliendo de borrarDocumento");

		return irAltaDocumento(mapping, form, request, response);
	}

	public ActionForward finalizarSubidaEscaneados(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		OCAPSolicitudesOT solicitudOT = new OCAPSolicitudesOT();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " finalizarSubidaEscaneados: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			solicitudOT.setCExp_id(Long.parseLong(request.getParameter("cExpId")));
			OCAPSolicitudesLN solicitudLN = new OCAPSolicitudesLN(jcylUsuario);
			solicitudLN.finalizarSubidaEscaneados(solicitudOT, jcylUsuario);

			request.setAttribute("rutaVuelta",
					"OCAPSolicitudes.do?accion=listarFase&fase=FASE_CA_TERMINADA&recuperarBusquedaAnterior=N");

			OCAPConfigApp.logger.info(getClass().getName() + " finalizarSubidaEscaneados: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("exito");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irCargarXML(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		OCAPConfigApp.logger.info(getClass().getName() + " irCargarXML Inicio");

		return mapping.findForward("cargarXML");
	}

	public NamedNodeMap ejecuta(Node nodo) throws Exception {
		NamedNodeMap atrib_dato = null;
		Node dato = null;
		try {
			dato = GestorXML.extrae_hijo(nodo, "PREGUNTAS");
			if (dato != null)
				atrib_dato = dato.getAttributes();
		} catch (Exception e) {
			System.out.println("Error en carga de operación");
		}

		return atrib_dato;
	}

	public ActionForward cargarXML(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		boolean errorXml = false;
		boolean siTratar = false;
		String rutaXml = "";
		String directorioPdfs = "";
		try {
			this.loggerXML.info("____________________________________________________________________");
			this.loggerXML.info("cargarXML INI");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			if ((JCYLConfiguracion.getValor("PATH_DOCS_EVIDENCIAS") != null)
					&& (!"".equals(JCYLConfiguracion.getValor("PATH_DOCS_EVIDENCIAS"))))
				rutaXml = JCYLConfiguracion.getValor("PATH_DOCS_EVIDENCIAS");
			else
				rutaXml = this.servlet.getServletConfig().getServletContext().getRealPath("") + File.separator
						+ JCYLConfiguracion.getValor("PATH_DOCS_EVIDENCIAS_DEFECTO");
			this.loggerXML.info("cargarXML - ruta ficheros de Evidencias: " + rutaXml);

			File fichero = new File(rutaXml);

			if ((JCYLConfiguracion.getValor("NOMBRE_DIRECTORIO_PDF_EVIDENCIAS") != null)
					&& (!"".equals(JCYLConfiguracion.getValor("NOMBRE_DIRECTORIO_PDF_EVIDENCIAS"))))
				directorioPdfs = JCYLConfiguracion.getValor("NOMBRE_DIRECTORIO_PDF_EVIDENCIAS");
			else {
				directorioPdfs = "escaneados";
			}
			this.loggerXML.info("cargarXML - nombre directorio ficheros pdfs de Evidencias: " + directorioPdfs);

			if (Constantes.SI_ESP.equals(((OCAPDocumentosForm) form).getBTratar()))
				siTratar = true;
			errorXml = buscarFichero(fichero, directorioPdfs, siTratar, false, jcylUsuario);
			if (errorXml)
				request.setAttribute("errorXml", "Error");

			this.loggerXML.info("cargarXML FIN");
			this.loggerXML.info("____________________________________________________________________");
		} catch (Exception e) {
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mensajeExitoSinFormulario");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public boolean buscarFichero(File directorio, String directorioPdfs, boolean bTratar, boolean siError,
			JCYLUsuario jcylUsuario) throws Exception {
		File[] ficheros = directorio.listFiles();
		int tratados = 0;
		try {
			for (int x = 0; x < ficheros.length; x++) {
				if (ficheros[x].isDirectory()) {
					siError = buscarFichero(ficheros[x], directorioPdfs, bTratar, siError, jcylUsuario);
				}

				if (ficheros[x].getName().endsWith(".xml")) {
					tratados = tratarXml(ficheros[x], directorioPdfs, bTratar, jcylUsuario);
					if (tratados == 0)
						siError = true;
				}
			}
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return siError;
	}

	public int tratarXml(File fichero, String directorioPdfs, boolean bTratar, JCYLUsuario jcylUsuario)
			throws Exception {
		Utilidades utilidades = null;
		OCAPExpedienteCarreraLN expCarreraLN = null;
		OCAPExpedientecarreraOT expCarreraOT = null;
		OCAPEstadosCuestionarioLN estCuesLN = null;
		OCAPCuestionariosLN cuestionariosLN = null;

		OCAPDocumentosOT documentosOT = null;
		OCAPCuestionariosOT cuestionarioOT = null;
		OCAPCreditosCuestionariosOT creditosCuestOT = null;

		long idCuestionario = 0L;
		long idExpediente = 0L;
		long idPadreEvidencia = 0L;
		long idItinerario = 0L;
		int nEvidencia = 0;
		int numRepe = 0;
		int cuentaPreguntasNoVacias = 0;

		String epr = "";
		ArrayList cadenaRespuestas = null;

		String valor = null;
		String ext = null;
		GestorXML gestor = null;
		Node nodo = null;

		boolean sinCuestionario = false;
		InputStream flinst = null;
		String rutaPdfs = null;
		try {
			this.loggerXML.info("--------------------------------------------------------------------");
			this.loggerXML.info("fichero: " + fichero);

			InputStream ficheroXml = new FileInputStream(fichero);
			gestor = new GestorXML(ficheroXml);

			int tope = gestor.nodos_tipo("ITINERARIO");
			if (tope == 0) {
				this.loggerXML.error("XML Erróneo. Falta la etiqueta ITINERARIO");
				return 0;
			}
			nodo = gestor.get_element_lista();
			nodo = nodo.getFirstChild();
			try {
				idItinerario = Long.parseLong(nodo.getNodeValue());
			} catch (Exception e) {
				this.loggerXML.error("XML Erróneo. El ITINERARIO debe ser numérico.");
				return 0;
			}

			gestor.reset_lista_tipo();

			tope = gestor.nodos_tipo("CUESTIONARIO");
			if (tope == 0) {
				this.loggerXML.error("XML Erróneo. Falta la etiqueta CUESTIONARIO");
				return 0;
			}
			nodo = gestor.get_element_lista();
			NamedNodeMap atributos = nodo.getAttributes();

			if (atributos.getNamedItem("cuestionarioId") != null)
				try {
					idCuestionario = Long.parseLong(atributos.getNamedItem("cuestionarioId").getNodeValue());
				} catch (Exception e) {
					this.loggerXML.error("XML Erróneo. El atributo cuestionarioId debe ser numérico.");
					return 0;
				}
			else {
				sinCuestionario = true;
			}
			utilidades = new Utilidades();
			try {
				epr = atributos.getNamedItem("eprId").getNodeValue();

				if (epr.trim().length() == 0) {
					this.loggerXML.error("XML Erróneo. Falta el atributo eprId");
					return 0;
				}
				if ((epr.length() != 9) || (!epr.startsWith("EPR"))
						|| (!utilidades.esNumerico(epr.substring("EPR".length())))) {
					this.loggerXML.error("XML Erróneo. Formato erróneo del atributo eprId");
					return 0;
				}

				idExpediente = Long.parseLong(epr.substring("EPR".length()));
				expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
				expCarreraOT = new OCAPExpedientecarreraOT();
				expCarreraOT.setCExpId(Long.valueOf(idExpediente));
				expCarreraOT = expCarreraLN.buscarOCAPExpedienteCarrera(expCarreraOT);
				if ((expCarreraOT.getCExpId() == null) || (expCarreraOT.getCExpId().longValue() == 0L)) {
					this.loggerXML.error("XML Erróneo. Expediente inexistente");
					return 0;
				}
				if (expCarreraOT.getCItinerarioId() != idItinerario) {
					this.loggerXML.error("XML Erróneo. Itinerario no perteneciente a este expediente");
					return 0;
				}
			} catch (Exception e) {
				this.loggerXML.error("XML Erróneo. Falta el atributo eprId");
				return 0;
			}

			cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
			try {
				try {
					nEvidencia = Integer.parseInt(atributos.getNamedItem("evidencia").getNodeValue());
				} catch (Exception e) {
					this.loggerXML.error("XML Erróneo. El atributo evidencia debe ser numérico.");
					return 0;
				}

				idPadreEvidencia = cuestionariosLN.buscarCuestionarioAsociado(idItinerario, nEvidencia,null);
				cuestionarioOT = cuestionariosLN.buscarCuestionario(idPadreEvidencia);

				if (cuestionarioOT.getCItinerarioId() != idItinerario) {
					this.loggerXML.error("XML Erróneo. Cuestionario no perteneciente a este itinerario");
					return 0;
				}
			} catch (Exception e) {
				this.loggerXML.error("XML Erróneo. Falta el atributo evidencia");
				return 0;
			}

			gestor.reset_lista_tipo();

			tope = gestor.nodos_tipo("PDF");
			if (tope > 0) {
				nodo = gestor.get_element_lista();
				nodo = nodo.getFirstChild();

				if ((nodo != null) && (!"".equals(nodo))) {
					try {
						valor = nodo.getNodeValue();
						ext = valor.substring(valor.lastIndexOf("."));
						if (!"pdf".equals(ext.substring(1))) {
							this.loggerXML.error("XML Erróneo. Extensión de fichero debe ser .pdf ");
							return 0;
						}

						String rutaXml = fichero.getParent();
						rutaPdfs = rutaXml.substring(0, rutaXml.lastIndexOf(File.separator) + 1) + directorioPdfs;
						this.loggerXML.info("ruta fichero pdf de evidencia asociado: " + rutaPdfs);

						flinst = new FileInputStream(rutaPdfs + File.separator + valor);
						documentosOT = new OCAPDocumentosOT();
						documentosOT.setCExpId(Long.valueOf(idExpediente));
						documentosOT.setADatos(flinst);
						documentosOT.setATipo_documento("application/pdf");
						documentosOT.setAExt(ext.substring(1));
						documentosOT.setDTitulo(String.valueOf(nEvidencia));

						documentosOT.setDDescripcion(valor);
						documentosOT.setCCuestionarioId(Long.valueOf(idPadreEvidencia));
						documentosOT.setACreadoPor(jcylUsuario.getUsuario().getC_usr_id());
					} catch (Exception e) {
						this.loggerXML
								.error("XML Erróneo. No se encuentra el PDF: " + rutaPdfs + File.separator + valor);
						return 0;
					}
				} else if (sinCuestionario) {
					this.loggerXML.error("XML Erróneo. Etiqueta PDF vacia");
					return 0;
				}
			} else if (sinCuestionario) {
				this.loggerXML.error("XML Erróneo. Falta la etiqueta PDF");
				return 0;
			}

			gestor.reset_lista_tipo();

			tope = gestor.nodos_tipo("REPETICION");
			if ((tope == 0) && (!sinCuestionario)) {
				this.loggerXML.error("XML Erróneo. Falta la etiqueta REPETICION");
				return 0;
			}

			if (tope > 0) {
				int i = 1;
				estCuesLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
				String estado = null;
				Map respuestas = new HashMap();

				while (i <= tope) {
					nodo = gestor.get_element_lista();
					atributos = nodo.getAttributes();
					try {
						try {
							numRepe = Integer.parseInt(atributos.getNamedItem("Id").getNodeValue());
						} catch (Exception e) {
							this.loggerXML.error("XML Erróneo. El atributo id debe ser numérico.");
							return 0;
						}
						if (numRepe > cuestionarioOT.getNRepeticiones().intValue()) {
							this.loggerXML.error("XML Erróneo. El id. de repetición no puede ser mayor que "
									+ cuestionarioOT.getNRepeticiones().intValue());
							return 0;
						}
					} catch (Exception e) {
						this.loggerXML.error("XML Erróneo. Falta el atributo id. de repetición");
						return 0;
					}

					this.loggerXML.info("Expediente:   " + idExpediente);
					this.loggerXML.info("Cuestionario: " + idCuestionario);
					this.loggerXML.info("Repetición:   " + numRepe);

					estado = estCuesLN.buscarEstadoCuestionario(idExpediente, idCuestionario, numRepe,
							idPadreEvidencia);

					if (("F".equals(estado)) && (!bTratar)) {
						this.loggerXML.info("Cuestionario ya finalizado");
					} else {
						ArrayList hijos = null;
						hijos = GestorXML.extrae_hijos(nodo);
						cadenaRespuestas = new ArrayList();
						int j = 0;
						Node nodoHijo = null;
						while (j < hijos.size()) {
							nodoHijo = (Node) hijos.get(j);
							atributos = nodoHijo.getAttributes();
							if (atributos.getNamedItem("valor") != null) {
								if (utilidades.esNumerico(atributos.getNamedItem("valor").getNodeValue())) {
									cadenaRespuestas.add(atributos.getNamedItem("valor").getNodeValue());
								} else {
									if (!atributos.getNamedItem("valor").getNodeValue().trim().equals("")) {
										this.loggerXML.error("XML Erróneo. Atributo valor debe ser numérico ");
										return 0;
									}
									cadenaRespuestas.add(atributos.getNamedItem("valor").getNodeValue());
								}
							} else if (atributos.getNamedItem("texto") != null) {
								valor = atributos.getNamedItem("texto").getNodeValue();
								try {
									ext = valor.substring(valor.lastIndexOf("."));
									if (!"pdf".equals(ext.substring(1))) {
										this.loggerXML.error("XML Erróneo. Extensión de fichero debe ser .pdf ");
										return 0;
									}
									flinst = new FileInputStream(rutaPdfs + File.separator + valor);
									cadenaRespuestas.add(flinst);
								} catch (Exception e) {
									this.loggerXML.error("XML Erróneo. No se encuentra el PDF: " + rutaPdfs
											+ File.separator + valor);
									return 0;
								}
							} else {
								this.loggerXML.error("XML Erróneo. Falta el atributo valor o texto de la respuesta");
								return 0;
							}

							j++;
						}

						respuestas.put(Integer.valueOf(numRepe), cadenaRespuestas);
					}

					i++;
				}

				cuentaPreguntasNoVacias = cuestionariosLN.insertarXML(idExpediente, idCuestionario, respuestas,
						nEvidencia, idPadreEvidencia, bTratar, idItinerario, documentosOT, jcylUsuario);
				if (cuentaPreguntasNoVacias == 0)
					this.loggerXML.info("XML Erróneo: No Insertado");
				else
					this.loggerXML.info("XML Insertado");
			} else {
				cuestionarioOT = cuestionariosLN.buscarEvidenciaDocumental(idExpediente, idItinerario, nEvidencia);

				if (cuestionarioOT.getNCreditos() != 0.0F) {
					OCAPCreditosCuestionariosLN creditosCuestLN = new OCAPCreditosCuestionariosLN(jcylUsuario);
					creditosCuestOT = new OCAPCreditosCuestionariosOT();
					creditosCuestOT.setCExpId(idExpediente);
					creditosCuestOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioId());
					creditosCuestOT.setNRepeticion(1);
					creditosCuestOT.setCPadreEvidenciaId(idPadreEvidencia);
					creditosCuestOT.setNEvidencia(nEvidencia);

					if (cuestionarioOT.getNElementos() != 0) {
						creditosCuestOT.setNCreditos(cuestionarioOT.getNCreditos());
						creditosCuestOT.setNPuntuacion(cuestionarioOT.getNCreditos());
					} else {
						ArrayList listaCreditosCuestAsociado = creditosCuestLN
								.buscarOCAPCreditosCuestionario(idPadreEvidencia, 0, idExpediente);
						float creditosPadre = 0.0F;
						if ((listaCreditosCuestAsociado != null) && (listaCreditosCuestAsociado.size() > 0)) {
							OCAPCreditosCuestionariosOT creditosPadreOT = (OCAPCreditosCuestionariosOT) listaCreditosCuestAsociado
									.get(0);
							creditosPadre = creditosPadreOT.getNCreditos();
						}
						creditosCuestOT.setNCreditos(cuestionarioOT.getNCreditos() + creditosPadre);
						creditosCuestOT.setNPuntuacion(cuestionarioOT.getNCreditos() + creditosPadre);
					}
					creditosCuestOT.setACreadoPor(jcylUsuario.getUsuario().getC_usr_id());

					cuentaPreguntasNoVacias = cuestionariosLN.insertarEvidenciaDocumental(creditosCuestOT,
							documentosOT);
					this.loggerXML.info("XML Insertado");
				} else {
					this.loggerXML.info("Evidencia documental sin créditos -> Expediente: " + idExpediente
							+ " Itinerario: " + idItinerario + " Evidencia: " + nEvidencia);
					cuentaPreguntasNoVacias = cuestionariosLN.insertarEvidenciaDocumental(null, documentosOT);
					this.loggerXML.info("XML Insertado");
				}
			}
		} catch (Exception e) {
			this.loggerXML.error("XML Erróneo: " + e.getMessage());
			this.loggerXML.error("XML Erróneo: No Insertado");
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			return 0;
		}

		return cuentaPreguntasNoVacias;
	}

	public ActionForward irAltaDocumentoEvidencia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		OCAPUsuariosLN usuariosLN = null;
		OCAPUsuariosOT usuarioOT = null;

		ActionErrors errors = new ActionErrors();
		OCAPDocumentosLN docuLN = null;
		OCAPDocumentosOT docuOT = null;

		ArrayList listado = null;
		int tamanoListado = 0;
		OCAPCuestionariosLN cuestionariosLN = null;

		ArrayList listaAreas = null;
		OCAPSolicitudesOT solicitudesOT = null;
		OCAPSolicitudesLN solicitudesLN = null;
		ArrayList listadoCuestionarios = null;
		long cExpId = 0L;
		OCAPItinerariosOT itinerarioOT = null;
		OCAPItinerariosLN itinerariosLN = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irAltaDocumentoEvidencia: Inicio");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			solicitudesOT = new OCAPSolicitudesOT();
			solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);

			if ((((OCAPDocumentosForm) form).getCExpId() != null)
					&& (((OCAPDocumentosForm) form).getCExpId().longValue() != 0L))
				cExpId = ((OCAPDocumentosForm) form).getCExpId().longValue();
			else if (request.getParameter("cExpId") != null)
				cExpId = Long.parseLong(request.getParameter("cExpId"));
			else
				cExpId = Long.parseLong(session.getAttribute("idExpediente").toString());

			if (request.getParameter("tarea") != null)
				((OCAPDocumentosForm) form).setTarea(request.getParameter("tarea"));
			if (request.getParameter("tareaDocu") != null) {
				((OCAPDocumentosForm) form).setTareaDocu(request.getParameter("tareaDocu"));
			}

			((OCAPDocumentosForm) form).setCCuestionarioId("0");

			((OCAPDocumentosForm) form).setDTitulo("0");

			solicitudesOT.setCExp_id(cExpId);
			solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);

			usuariosLN = new OCAPUsuariosLN(jcylUsuario);
			usuarioOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(), cExpId, jcylUsuario);
			if ((usuarioOT.getFFinCA() != null) && (usuarioOT.getFFinCA().before(new Date()))) {
				request.setAttribute("verFinProcesoDocumento", Constantes.SI);
			} else
				request.setAttribute("verFinProcesoDocumento", Constantes.NO);

			if (usuarioOT == null) {
				return mapping.findForward("irNoExisteSolicitud");
			}

			DecimalFormat formato = new DecimalFormat("000000");
			((OCAPDocumentosForm) form).setCodigoId("EPR" + formato.format(cExpId));

			((OCAPDocumentosForm) form).setDNombre(solicitudesOT.getDNombre());
			((OCAPDocumentosForm) form).setDApellido1(solicitudesOT.getDApellido1());
			((OCAPDocumentosForm) form).setDCorreoelectronico(
					solicitudesOT.getDCorreoelectronico() == null ? "-" : solicitudesOT.getDCorreoelectronico());
			((OCAPDocumentosForm) form).setCDni(solicitudesOT.getCDni());
			((OCAPDocumentosForm) form).setCDniReal(solicitudesOT.getCDniReal());
			if (solicitudesOT.getNTelefono1().equals("0"))
				((OCAPDocumentosForm) form).setNTelefono1("-");
			else {
				((OCAPDocumentosForm) form).setNTelefono1(String.valueOf(solicitudesOT.getNTelefono1()));
			}
			if (solicitudesOT.getNTelefono2().equals("0"))
				((OCAPDocumentosForm) form).setNTelefono2("-");
			else {
				((OCAPDocumentosForm) form).setNTelefono2(String.valueOf(solicitudesOT.getNTelefono2()));
			}
			((OCAPDocumentosForm) form).setBSexo(solicitudesOT.getBSexo());
			((OCAPDocumentosForm) form)
					.setDDomicilio(solicitudesOT.getDDomicilio() == null ? "-" : solicitudesOT.getDDomicilio());
			((OCAPDocumentosForm) form).setDLocalidadUsu(
					solicitudesOT.getDLocalidadUsu() == null ? "-" : solicitudesOT.getDLocalidadUsu());
			((OCAPDocumentosForm) form).setCProvinciaUsu_id(solicitudesOT.getCProvinciaUsu_id());
			((OCAPDocumentosForm) form).setDProvinciaUsu(
					solicitudesOT.getDProvinciaUsu() == null ? "-" : solicitudesOT.getDProvinciaUsu());
			formato = new DecimalFormat("00000");
			((OCAPDocumentosForm) form).setCPostalUsu(solicitudesOT.getCPostalUsu() == null ? "-"
					: formato.format(Long.parseLong(solicitudesOT.getCPostalUsu())));

			((OCAPDocumentosForm) form).setCJuridico(solicitudesOT.getCJuridico());

			((OCAPDocumentosForm) form).setDProcedimientoNombre(solicitudesOT.getDProcedimiento());
			((OCAPDocumentosForm) form).setCSitAdministrativaAuxId(solicitudesOT.getCSitAdministrativaAuxId());
			((OCAPDocumentosForm) form).setDSitAdministrativaAuxOtros(solicitudesOT.getDSitAdministrativaAuxOtros());
			((OCAPDocumentosForm) form).setDEstatutarioActual_nombre(solicitudesOT.getDEstatutActual_nombre());
			((OCAPDocumentosForm) form).setDProfesionalActual_nombre(solicitudesOT.getDProfesionalActual_nombre());
			((OCAPDocumentosForm) form).setDEspecActual_nombre(solicitudesOT.getDEspecActual_nombre());
			((OCAPDocumentosForm) form).setCEspecActual_id(String.valueOf(solicitudesOT.getCEspecActual_id()));
			((OCAPDocumentosForm) form).setDEstatutario_nombre(solicitudesOT.getDEstatut_nombre());
			((OCAPDocumentosForm) form).setDProfesional_nombre(solicitudesOT.getDProfesional_nombre());
			((OCAPDocumentosForm) form).setCProfesional_id(String.valueOf(solicitudesOT.getCProfesional_id()));
			((OCAPDocumentosForm) form).setDEspec_nombre(solicitudesOT.getDEspec_nombre());
			((OCAPDocumentosForm) form).setDProvincia(Cadenas.capitalizar(solicitudesOT.getDProvincia()));
			((OCAPDocumentosForm) form).setACiudad(Cadenas.capitalizar(solicitudesOT.getACiudad()));
			((OCAPDocumentosForm) form).setDTipogerencia_desc(solicitudesOT.getDTipogerencia_desc());
			((OCAPDocumentosForm) form).setDGerencia_nombre(solicitudesOT.getDGerencia_nombre());
			((OCAPDocumentosForm) form).setDCentrotrabajo_nombre(solicitudesOT.getDCentrotrabajo_nombre());
			((OCAPDocumentosForm) form).setDGrado_des(solicitudesOT.getDGrado_des());
			((OCAPDocumentosForm) form).setCConvocatoriaId(String.valueOf(solicitudesOT.getCConvocatoriaId()));
			((OCAPDocumentosForm) form).setBValidacion_cc(solicitudesOT.getBValidacionCC());
			((OCAPDocumentosForm) form).setListaCreditos(solicitudesOT.getListaCreditos());

			cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
			listaAreas = cuestionariosLN.listadoOCAPCuestionariosPorItinerario(usuarioOT, jcylUsuario);
			((OCAPDocumentosForm) form).setListadoAreas(listaAreas);
			if ((listaAreas != null) && (listaAreas.size() != 0))
				((OCAPDocumentosForm) form)
						.setCItinerarioId(((OCAPAreasItinerariosOT) listaAreas.get(0)).getCItinerarioId());
			else
				return mapping.findForward("irNoExisteItinerario");

			if ((((OCAPDocumentosForm) form).getCAreaId() != null)
					&& (!"".equals(((OCAPDocumentosForm) form).getCAreaId()))) {
				listadoCuestionarios = cuestionariosLN.listadoOCAPCuestionariosAreaDocumentos(
						((OCAPDocumentosForm) form).getCExpId(), ((OCAPDocumentosForm) form).getCItinerarioId(),
						((OCAPDocumentosForm) form).getCAreaId());
				((OCAPDocumentosForm) form).setListadoCuestionarios(listadoCuestionarios);
			} else {
				((OCAPDocumentosForm) form).setListadoCuestionarios(new ArrayList());
			}
			docuLN = new OCAPDocumentosLN();
			if ("modificacion".equals(((OCAPDocumentosForm) form).getTareaDocu())) {
				docuOT = docuLN.buscarDocumento(((OCAPDocumentosForm) form).getCDocumento_id());

				((OCAPDocumentosForm) form).setCExpId(docuOT.getCExpId());
				((OCAPDocumentosForm) form).setDTitulo(docuOT.getDTitulo());
				((OCAPDocumentosForm) form).setDDescripcion(docuOT.getDDescripcion());
				((OCAPDocumentosForm) form).setAExt(docuOT.getAExt());
				((OCAPDocumentosForm) form).setCUsuario_id(docuOT.getCUsuario_id());
			}

			if (cExpId != 0L) {
				((OCAPDocumentosForm) form).setCExpId(Long.valueOf(cExpId));
				listado = docuLN.buscarDocumentos(((OCAPDocumentosForm) form).getCExpId().longValue());
				((OCAPDocumentosForm) form).setListaDocumentos(listado);
				tamanoListado = listado.size();
			}

			ArrayList listadoEvidenciasUsadas = new ArrayList();
			OCAPDocumentosOT docuOTAux = null;
			for (int nElem = 0; nElem < tamanoListado; nElem++) {
				docuOTAux = (OCAPDocumentosOT) listado.get(nElem);
				listadoEvidenciasUsadas.add(docuOTAux.getDTitulo());
			}

			itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
			itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());
			((OCAPDocumentosForm) form).setNEvidencias(String.valueOf(itinerarioOT.getNEvidencias()));

			ArrayList listadoEvidenciaSinUsar = new ArrayList();

			for (int contEvi = 1; contEvi <= 99; contEvi++) {
				if (!listadoEvidenciasUsadas.contains(Integer.toString(contEvi)))
					listadoEvidenciaSinUsar.add(Integer.toString(contEvi));
			}
			((OCAPDocumentosForm) form).setListadoEvidenciasNoUsadas(listadoEvidenciaSinUsar);

			OCAPConfigApp.logger.info(getClass().getName() + " irAltaDocumentoEvidencia: Fin");
		} catch (SQLException e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("mostrarDocEvidencia");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward abrirDocumentoEvidencia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OCAPConfigApp.logger.info(getClass().getName() + " abrirDocumentoEvidencia: Inicio");

		ActionErrors errors = new ActionErrors();

		OCAPDocumentosLN documentosLN = new OCAPDocumentosLN();
		OCAPDocumentosOT documentosOT = new OCAPDocumentosOT();
		try {
			documentosOT = documentosLN.buscarDocumentoEvidencia(((OCAPDocumentosForm) form).getCExpId().longValue());

			OutputStream documento = response.getOutputStream();

			response.setContentType(documentosOT.getATipo_documento());
			response.setHeader("Content-Disposition",
					"attachment;filename=" + documentosOT.getDDescripcion() + "." + documentosOT.getAExt());

			InputStream fichero = documentosOT.getADatos();

			int length = -1;
			byte[] buffer = new byte[1024];

			while ((length = fichero.read(buffer)) > -1) {
				documento.write(buffer, 0, length);
			}

			fichero.close();
			documento.flush();
			documento.close();

			OCAPConfigApp.logger.info(getClass().getName() + " abrirDocumentoEvidencia: Fin");
		} catch (SQLException e) {
			this.logger.error(e);
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		} catch (Exception e) {
			this.logger.error(e);
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
		}

		if (errors.size() != 0) {
			saveErrors(request, errors);

			return mapping.findForward("fallo");
		}

		return mapping.findForward("");
	}

	public OCAPDocumentosAction() {
		$init$();
	}
}
