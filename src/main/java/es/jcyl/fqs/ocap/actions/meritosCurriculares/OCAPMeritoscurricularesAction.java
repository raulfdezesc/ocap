 package es.jcyl.fqs.ocap.actions.meritosCurriculares;
 
 import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.jcyl.fqs.ocap.actionforms.meritosCurriculares.OCAPMeritoscurricularesForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
import es.jcyl.fqs.ocap.ln.defMeritosCurriculares.OCAPDefMeritoscurricularesLN;
import es.jcyl.fqs.ocap.ln.estadosCuestionario.OCAPEstadosCuestionarioLN;
import es.jcyl.fqs.ocap.ln.estatutario.OCAPEstatutarioLN;
import es.jcyl.fqs.ocap.ln.expedienteCarrera.OCAPExpedienteCarreraLN;
import es.jcyl.fqs.ocap.ln.factoresImpacto.OCAPFactoresImpactoLN;
import es.jcyl.fqs.ocap.ln.meritosActividad.OCAPMeractividadLN;
import es.jcyl.fqs.ocap.ln.meritosCurriculares.OCAPMeritoscurricularesLN;
import es.jcyl.fqs.ocap.ln.meritosModalidad.OCAPMerModalidadLN;
import es.jcyl.fqs.ocap.ln.tiposFirmante.OCAPTiposFirmanteLN;
import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;
import es.jcyl.fqs.ocap.ot.meritosCurriculares.OCAPMeritoscurricularesOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.Pagina;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 
 public class OCAPMeritoscurricularesAction extends OCAPGenericAction
 {
	 
		public ActionForward irDesbloqueoCurriculares(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			HttpSession session = request.getSession();
			ActionErrors errors = new ActionErrors();
			
			try {
				OCAPConfigApp.logger.debug(getClass().getName() + " irDesbloqueoCurriculares: Inicio");

				String cConvocatoriaId = obtenerConvocatoria(request, null);

				if (!Utilidades.isNullOrIsEmpty(cConvocatoriaId)) {
					JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
					String usuId = jcylUsuario.getUser().getC_usr_id();

					OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);
					OCAPUsuariosOT usuariosOT = usuariosLN.buscarOCAPUsuariosPorNIF(usuId);

					if ((usuariosOT == null) || (usuariosOT.getCUsrId() == null)) {
						return mapping.findForward("irErrorCurriculares");
					} else {
						return mapping.findForward("irOCAPDesbloqueoCurriculares");
					}
				} else {
					errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
				}

				OCAPConfigApp.logger.debug(getClass().getName() + " irDesbloquear: Fin");
			} catch (Exception e) {
				OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
				errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
			}

			saveErrors(request, errors);

			return mapping.findForward("fallo");
		}

		public ActionForward desbloquear(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws IOException, ServletException {
			String sig = "error";
			HttpSession session = request.getSession();
			ActionErrors errors = new ActionErrors();
			String cConvocatoriaId = null;
			OCAPExpedientecarreraOT expedienteOT = null;
			OCAPConvocatoriasOT convocatoriaOT = null;
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			OCAPConvocatoriasLN convocatoriaLN = new OCAPConvocatoriasLN(jcylUsuario);
			try {
				OCAPConfigApp.logger.debug(getClass().getName() + " desbloquear: Inicio");
				validarAccion(mapping, form, request, response);

				cConvocatoriaId = obtenerConvocatoria(request, null);

				if (!Utilidades.isNullOrIsEmpty(cConvocatoriaId)) {
					String usuId = jcylUsuario.getUser().getC_usr_id();
					OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);
					OCAPUsuariosOT usuariosOT = usuariosLN.buscarOCAPUsuariosPorNIF(usuId);
					OCAPConfigApp.logger.debug(getClass().getName() + " desbloquear: UsuarioId: " + usuId);
					expedienteOT = expedienteCarreraLN.buscarExpedienteCarreraPorUsuarioConvocatoria(
							usuariosOT.getCUsrId(), Long.valueOf(cConvocatoriaId));
					convocatoriaOT = convocatoriaLN.buscarOCAPConvocatorias(new Long(cConvocatoriaId));
					expedienteOT.setDniReal(usuId);
					if (null != convocatoriaOT.getFInicioMC() && null != convocatoriaOT.getFFinMC()) {
						if (Utilidades.fechaActualEntreMeritosCurriculares(expedienteOT, convocatoriaOT)) {
							// desbloqueamos
							int result = expedienteCarreraLN.desbloquearMeritosCurriculares(expedienteOT);
							if (result == 1) {
								request.setAttribute("rutaVuelta", "PaginaInicio.do");
								sig = "irOkCurriculares";
							} else {
								sig = "irErrorCurriculares";
							}
						} else {
							sig = "irOCAPAvisoCurriculares";
						}
					} else {
						sig = "irOCAPAvisoCurriculares";
					}

				} else {
					errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
				}

				OCAPConfigApp.logger.debug(getClass().getName() + " desbloquear: Fin");
			} catch (Exception ex) {
				OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
				errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
			}

			if ((errors == null) || (errors.isEmpty())) {
				return mapping.findForward(sig);
			}
			saveErrors(request, errors);

			return mapping.findForward("fallo");
		}
	 
	 
   public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- BAJA DE OCAP_MERITOSCURRICULARES --------- ");
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPMeritoscurricularesLN oCAPMeritoscurricularesLN = new OCAPMeritoscurricularesLN(jcylUsuario);
 
       String cMeritoIdS = request.getParameter("cMeritoIdS");
       int cMeritoId;
       if ((cMeritoIdS != null) && (!cMeritoIdS.equals(""))) {
         cMeritoId = Integer.parseInt(cMeritoIdS);
         OCAPConfigApp.logger.info("cMeritoId: " + cMeritoId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
       int result = oCAPMeritoscurricularesLN.bajaOCAPMeritoscurriculares(cMeritoId);
 
       if (result == 0)
       {
         OCAPConfigApp.logger.info("Se ha producido un error al borrar el registro");
         sig = "error";
         request.setAttribute("mensaje", "Se ha producido un error al borrar el registro");
       }
       else {
         OCAPConfigApp.logger.info("El registro se ha eliminado con éxito");
         request.setAttribute("mensaje", "El registro se ha eliminado con éxito");
         request.setAttribute("rutaVuelta", "OCAPMeritoscurriculares.do?accion=buscar&recuperarBusquedaAnterior=Y");
 
         sig = "exito";
       }
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       sig = "error";
       request.setAttribute("mensaje", "Se ha producido un error");
     }
 
     return mapping.findForward(sig);
   }
 
   public ActionForward irBuscar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errors = new ActionErrors();
     String mensajeError = "Se ha producido un error";
     HttpSession session = request.getSession();
     Utilidades utilidades = new Utilidades();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irBuscar: Inicio");
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPDefMeritoscurricularesLN defMeritosCurricularesLN = new OCAPDefMeritoscurricularesLN(jcylUsuario);
       Collection listadoMerCurr = defMeritosCurricularesLN.listadoOCAPDefMeritoscurriculares();
       Object[] listadoDefMerCurr = listadoMerCurr.toArray();
 
       OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
       Collection listadoEstat = estatutarioLN.listadoOCAPEstatutario();
       Object[] listadoEstatutario = listadoEstat.toArray();
 
       OCAPMeractividadLN meractividadLN = new OCAPMeractividadLN(jcylUsuario);
       Collection listadoAct = meractividadLN.listadoOCAPMeractividad();
       Object[] listadoActividad = listadoAct.toArray();
 
       OCAPMerModalidadLN merModalidadLN = new OCAPMerModalidadLN(jcylUsuario);
       Collection listadoMod = merModalidadLN.listadoOCAPMerModalidad();
       Object[] listadoModalidad = listadoMod.toArray();
 
       OCAPFactoresImpactoLN factoresLN = new OCAPFactoresImpactoLN(jcylUsuario);
       Collection listadoFact = factoresLN.listadoOCAPFactoresImpacto();
       Object[] listadoFactImpacto = listadoFact.toArray();
 
       OCAPTiposFirmanteLN tiposLN = new OCAPTiposFirmanteLN(jcylUsuario);
       Collection listadoTipos = tiposLN.listadoOCAPTiposFirmante();
       Object[] listadoTiposFirmante = listadoTipos.toArray();
 
       session.setAttribute(Constantes.COMBO_ESTATUTARIO, utilidades.ArrayObjectToArrayList(listadoEstatutario));
       session.setAttribute(Constantes.COMBO_DEF_MERITOSCURRICULARES, utilidades.ArrayObjectToArrayList(listadoDefMerCurr));
       session.setAttribute(Constantes.COMBO_ACTIVIDAD, utilidades.ArrayObjectToArrayList(listadoActividad));
       session.setAttribute(Constantes.COMBO_MODALIDAD, utilidades.ArrayObjectToArrayList(listadoModalidad));
       session.setAttribute(Constantes.COMBO_FACTORESIMPACTO, utilidades.ArrayObjectToArrayList(listadoFactImpacto));
       session.setAttribute(Constantes.COMBO_TIPOSFIRMANTE, utilidades.ArrayObjectToArrayList(listadoTiposFirmante));
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " irBuscar: ERROR: " + e.getMessage());
       request.setAttribute("mensaje", mensajeError);
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward("irBuscar");
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward buscar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String mensajeError = "Se ha producido un error";
     HttpSession session = request.getSession();
     try
     {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- CONSULTA DE OCAP_MERITOSCURRICULARES --------- ");
       request.setAttribute("primeraConsulta", "false");
 
       if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR))) && (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
         form = (OCAPMeritoscurricularesForm)request.getSession().getAttribute("OCAPMeritoscurricularesFormBuscador");
         request.setAttribute("OCAPMeritoscurricularesForm", form);
       } else {
         request.getSession().setAttribute("OCAPMeritoscurricularesFormBuscador", form);
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
       OCAPMeritoscurricularesForm formulario = (OCAPMeritoscurricularesForm)form;
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPMeritoscurricularesLN oCAPMeritoscurricularesLN = new OCAPMeritoscurricularesLN(jcylUsuario);
 
       long cMeritoId = 0L;
       long cDefmeritoId = 0L;
       long cEstatutId = 0L;
       long cActividadId = 0L;
       long cModalidadId = 0L;
       long cFactorId = 0L;
       long cTipoId = 0L;
       String cTipoMerito = null;
       String fCreacion = null;
       String fModificacion = null;
       String dNombrecorto = null;
       String dNombre = null;
       String dDescripcion = null;
       String dObservaciones = null;
       String nCreditos = null;
 
       cMeritoId = formulario.getCMerito_id();
       cDefmeritoId = formulario.getCDefmerito_id();
       cEstatutId = formulario.getCEstatut_id();
       cActividadId = formulario.getCActividad_id();
       cModalidadId = formulario.getCModalidad_id();
       cFactorId = formulario.getCFactor_id();
       cTipoId = formulario.getCTipo_id();
 
       if ((formulario.getCTipo_merito() != null) && (!formulario.getCTipo_merito().equals(""))) {
         cTipoMerito = formulario.getCTipo_merito();
       }
 
       if ((formulario.getDNombrecorto() != null) && (!formulario.getDNombrecorto().equals(""))) {
         dNombrecorto = formulario.getDNombrecorto();
       }
 
       if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
         dNombre = formulario.getDNombre();
       }
 
       if ((formulario.getDDescripcion() != null) && (!formulario.getDDescripcion().equals(""))) {
         dDescripcion = formulario.getDDescripcion();
       }
 
       if ((formulario.getDObservaciones() != null) && (!formulario.getDObservaciones().equals(""))) {
         dObservaciones = formulario.getDObservaciones();
       }
 
       if ((formulario.getNCreditos() != null) && (!formulario.getNCreditos().equals(""))) {
         nCreditos = formulario.getNCreditos();
       }
 
       if ((formulario.getACreacion() != null) && (!formulario.getACreacion().equals("")) && (formulario.getMCreacion() != null) && (!formulario.getMCreacion().equals("")) && (formulario.getDCreacion() != null) && (!formulario.getDCreacion().equals("")))
       {
         fCreacion = formulario.getDCreacion() + '/' + formulario.getMCreacion() + '/' + formulario.getACreacion();
       }
 
       if ((formulario.getAModificacion() != null) && (!formulario.getAModificacion().equals("")) && (formulario.getMModificacion() != null) && (!formulario.getMModificacion().equals("")) && (formulario.getDModificacion() != null) && (!formulario.getDModificacion().equals("")))
       {
         fModificacion = formulario.getDModificacion() + '/' + formulario.getMModificacion() + '/' + formulario.getAModificacion();
       }
 
       Collection elementos = oCAPMeritoscurricularesLN.consultaOCAPMeritoscurriculares(cMeritoId, cDefmeritoId, cEstatutId, cActividadId, cModalidadId, cFactorId, cTipoId, cTipoMerito, dNombrecorto, dNombre, dDescripcion, dObservaciones, nCreditos, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
 
       if (elementos != null) {
         OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Gerencias para la consulta");
         if (elementos.size() == 0)
         {
           request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
           request.removeAttribute("conDatos");
           request.getSession().removeAttribute("paginaOCAPMeritoscurricularesOT");
         }
         else {
           Object[] listadoelementos = elementos.toArray();
 
           OCAPDefMeritoscurricularesLN defMeritosCurricularesLN = new OCAPDefMeritoscurricularesLN(jcylUsuario);
           OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
           OCAPMeractividadLN meractividadLN = new OCAPMeractividadLN(jcylUsuario);
           OCAPMerModalidadLN merModalidadLN = new OCAPMerModalidadLN(jcylUsuario);
           OCAPFactoresImpactoLN factoresLN = new OCAPFactoresImpactoLN(jcylUsuario);
           OCAPTiposFirmanteLN tiposLN = new OCAPTiposFirmanteLN(jcylUsuario);
 
           int reg = elementos.size();
 
           elementos.removeAll(elementos);
 
           for (int i = 0; i < reg; i++) {
             ((OCAPMeritoscurricularesOT)listadoelementos[i]).setDDefmeritoNombre(defMeritosCurricularesLN.buscarOCAPDefMeritoscurriculares(((OCAPMeritoscurricularesOT)listadoelementos[i]).getCDefmeritoId()).getDNombre());
             ((OCAPMeritoscurricularesOT)listadoelementos[i]).setDEstatutNombre(estatutarioLN.buscarOCAPEstatutario(((OCAPMeritoscurricularesOT)listadoelementos[i]).getCEstatutId()).getDNombre());
             ((OCAPMeritoscurricularesOT)listadoelementos[i]).setDActividadNombre(meractividadLN.buscarOCAPMeractividad(((OCAPMeritoscurricularesOT)listadoelementos[i]).getCActividadId()).getDNombre());
             ((OCAPMeritoscurricularesOT)listadoelementos[i]).setDModalidadNombre(merModalidadLN.buscarOCAPMerModalidad(((OCAPMeritoscurricularesOT)listadoelementos[i]).getCModalidadId()).getDNombre());
             ((OCAPMeritoscurricularesOT)listadoelementos[i]).setDFactorNombre(factoresLN.buscarOCAPFactoresImpacto(((OCAPMeritoscurricularesOT)listadoelementos[i]).getCFactorId()).getDNombre());
             ((OCAPMeritoscurricularesOT)listadoelementos[i]).setDTipoNombre(tiposLN.buscarOCAPTiposFirmante(((OCAPMeritoscurricularesOT)listadoelementos[i]).getCTipoId()).getDNombre());
             elementos.add(listadoelementos[i]);
           }
 
           int nListado = 0;
           nListado = oCAPMeritoscurricularesLN.listadoOCAPMeritoscurricularesCuenta(cMeritoId, cDefmeritoId, cEstatutId, cActividadId, cModalidadId, cFactorId, cTipoId, cTipoMerito, dNombrecorto, dNombre, dDescripcion, dObservaciones, nCreditos, fCreacion, fModificacion);
           request.removeAttribute("sinDatos");
           Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
           pagina.setElementos(elementos);
           pagina.setRegistroActual(primerRegistro);
           pagina.setNRegistros(nListado);
           pagina.setRegistrosPorPagina(registrosPorPagina);
           request.setAttribute("paginaOCAPMeritoscurricularesOT", pagina);
           request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
           request.removeAttribute("menu");
           request.getSession().removeAttribute("menu");
         }
       }
       else {
         request.setAttribute("errorConsultando", "Error consultando en la base de datos");
       }
       OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAPMeritoscurriculares ------- ");
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       request.setAttribute("mensaje", mensajeError);
       return mapping.findForward("error");
     }
 
     return mapping.findForward("irBuscar");
   }
 
   public ActionForward detalle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     HttpSession session = request.getSession();
     try
     {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- DETALLE OCAP_MERITOSCURRICULARES --------- ");
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPMeritoscurricularesLN oCAPMeritoscurricularesLN = new OCAPMeritoscurricularesLN(jcylUsuario);
       OCAPMeritoscurricularesOT datos = new OCAPMeritoscurricularesOT();
 
       OCAPMeritoscurricularesForm formulario = (OCAPMeritoscurricularesForm)form;
 
       String cMeritoIdS = request.getParameter("cMeritoIdS");
       long cMeritoId;
       if ((cMeritoIdS != null) && (!cMeritoIdS.equals(""))) {
         cMeritoId = Long.parseLong(cMeritoIdS);
         OCAPConfigApp.logger.info("cMeritoId: " + cMeritoId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }datos = oCAPMeritoscurricularesLN.buscarOCAPMeritoscurriculares(cMeritoId);
 
       if (datos.getCMeritoId() == 0L)
       {
         OCAPConfigApp.logger.info("No encontramos OCAPMeritoscurricularesOT");
         sig = "error";
         request.getSession().removeAttribute("conDatosDetalle");
         request.setAttribute("mensaje", "No se encuentra el registro");
       }
       else {
         OCAPDefMeritoscurricularesLN defMeritosCurricularesLN = new OCAPDefMeritoscurricularesLN(jcylUsuario);
         OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
         OCAPMeractividadLN meractividadLN = new OCAPMeractividadLN(jcylUsuario);
         OCAPMerModalidadLN merModalidadLN = new OCAPMerModalidadLN(jcylUsuario);
         OCAPFactoresImpactoLN factoresLN = new OCAPFactoresImpactoLN(jcylUsuario);
         OCAPTiposFirmanteLN tiposLN = new OCAPTiposFirmanteLN(jcylUsuario);
 
         formulario.setCMerito_id(datos.getCMeritoId());
         formulario.setCDefmerito_id(datos.getCDefmeritoId());
         formulario.setCEstatut_id(datos.getCEstatutId());
         formulario.setCActividad_id(datos.getCActividadId());
         formulario.setCModalidad_id(datos.getCModalidadId());
         formulario.setCFactor_id(datos.getCFactorId());
         formulario.setCTipo_id(datos.getCTipoId());
         formulario.setDDefmerito_nombre(defMeritosCurricularesLN.buscarOCAPDefMeritoscurriculares(datos.getCDefmeritoId()).getDNombre());
         formulario.setDEstatut_nombre(estatutarioLN.buscarOCAPEstatutario(datos.getCEstatutId()).getDNombre());
         formulario.setDActividad_nombre(meractividadLN.buscarOCAPMeractividad(datos.getCActividadId()).getDNombre());
         formulario.setDModalidad_nombre(merModalidadLN.buscarOCAPMerModalidad(datos.getCModalidadId()).getDNombre());
         formulario.setDFactor_nombre(factoresLN.buscarOCAPFactoresImpacto(datos.getCFactorId()).getDNombre());
         formulario.setDTipo_nombre(tiposLN.buscarOCAPTiposFirmante(datos.getCTipoId()).getDNombre());
         formulario.setCTipo_merito(datos.getCTipoMerito());
         formulario.setDNombrecorto(datos.getDNombrecorto());
         formulario.setDNombre(datos.getDNombre());
         formulario.setDDescripcion(datos.getDDescripcion());
         formulario.setDObservaciones(datos.getDObservaciones());
         formulario.setNCreditos(String.valueOf(datos.getNCreditos()));
 
         OCAPConfigApp.logger.info("Se ha encontrado OCAPMeritoscurriculares");
         request.getSession().setAttribute("conDatosDetalle", "");
         request.getSession().removeAttribute("sinDatosDetalle");
         request.getSession().setAttribute("OCAPMeritoscurricularesOT", datos);
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
 
   public ActionForward irEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     HttpSession session = request.getSession();
     try
     {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- MODIFICACION OCAP_MERITOSCURRICULARES --------- ");
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPMeritoscurricularesLN oCAPMeritoscurricularesLN = new OCAPMeritoscurricularesLN(jcylUsuario);
       OCAPMeritoscurricularesOT datos = new OCAPMeritoscurricularesOT();
 
       OCAPMeritoscurricularesForm formulario = (OCAPMeritoscurricularesForm)form;
 
       String cMeritoIdS = request.getParameter("cMeritoIdS");
       long cMeritoId;
       if ((cMeritoIdS != null) && (!cMeritoIdS.equals(""))) {
         cMeritoId = Long.parseLong(cMeritoIdS);
         OCAPConfigApp.logger.info("cMeritoId: " + cMeritoId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
       datos = oCAPMeritoscurricularesLN.buscarOCAPMeritoscurriculares(cMeritoId);
 
       if (datos.getCMeritoId() == 0L)
       {
         OCAPConfigApp.logger.info("No encontramos OCAPMeritoscurricularesOT");
         sig = "error";
         request.getSession().removeAttribute("conDatosEdicion");
         request.setAttribute("mensaje", "No se encuentra el registro");
       }
       else {
         OCAPConfigApp.logger.info("Se ha encontrado OCAPMeritoscurricularesOT");
         request.getSession().setAttribute("conDatosEdicion", "");
         request.getSession().removeAttribute("sinDatosEdicion");
         request.setAttribute("OCAPMeritoscurricularesOT", datos);
 
         OCAPDefMeritoscurricularesLN defMeritosCurricularesLN = new OCAPDefMeritoscurricularesLN(jcylUsuario);
         OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
         OCAPMeractividadLN meractividadLN = new OCAPMeractividadLN(jcylUsuario);
         OCAPMerModalidadLN merModalidadLN = new OCAPMerModalidadLN(jcylUsuario);
         OCAPFactoresImpactoLN factoresLN = new OCAPFactoresImpactoLN(jcylUsuario);
         OCAPTiposFirmanteLN tiposLN = new OCAPTiposFirmanteLN(jcylUsuario);
 
         formulario.setCMerito_id(datos.getCMeritoId());
         formulario.setCDefmerito_id(datos.getCDefmeritoId());
         formulario.setCEstatut_id(datos.getCEstatutId());
         formulario.setCActividad_id(datos.getCActividadId());
         formulario.setCModalidad_id(datos.getCModalidadId());
         formulario.setCFactor_id(datos.getCFactorId());
         formulario.setCTipo_id(datos.getCTipoId());
         formulario.setDDefmerito_nombre(defMeritosCurricularesLN.buscarOCAPDefMeritoscurriculares(datos.getCDefmeritoId()).getDNombre());
         formulario.setDEstatut_nombre(estatutarioLN.buscarOCAPEstatutario(datos.getCEstatutId()).getDNombre());
         formulario.setDActividad_nombre(meractividadLN.buscarOCAPMeractividad(datos.getCActividadId()).getDNombre());
         formulario.setDModalidad_nombre(merModalidadLN.buscarOCAPMerModalidad(datos.getCModalidadId()).getDNombre());
         formulario.setDFactor_nombre(factoresLN.buscarOCAPFactoresImpacto(datos.getCFactorId()).getDNombre());
         formulario.setDTipo_nombre(tiposLN.buscarOCAPTiposFirmante(datos.getCTipoId()).getDNombre());
         formulario.setCTipo_merito(datos.getCTipoMerito());
         formulario.setDNombrecorto(datos.getDNombrecorto());
         formulario.setDNombre(datos.getDNombre());
         formulario.setDDescripcion(datos.getDDescripcion());
         formulario.setDObservaciones(datos.getDObservaciones());
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
 
   public ActionForward grabar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     String mensajeError = "Se ha producido un error";
     HttpSession session = request.getSession();
     try
     {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- GRABAR OCAP_MERITOSCURRICULARES --------- ");
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPMeritoscurricularesLN oCAPMeritoscurricularesLN = new OCAPMeritoscurricularesLN(jcylUsuario);
       OCAPMeritoscurricularesOT datos = new OCAPMeritoscurricularesOT();
 
       OCAPMeritoscurricularesForm formulario = (OCAPMeritoscurricularesForm)form;
 
       String cMeritoIdS = request.getParameter("cMeritoIdS");
       if ((cMeritoIdS != null) && (!cMeritoIdS.equals(""))) {
         long cMeritoId = Long.parseLong(cMeritoIdS);
         OCAPConfigApp.logger.info("cMeritoId: " + cMeritoId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
       OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
 
       if (formulario.getCDefmerito_id() == 0L) {
         mensajeError = "El campo Mérito es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       if (formulario.getCEstatut_id() == 0L) {
         mensajeError = "El campo Estatuto es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       if ((formulario.getDNombrecorto() == null) || (formulario.getDNombrecorto().equals(""))) {
         mensajeError = "El Nombre corto es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       if ((formulario.getDNombre() == null) || (formulario.getDNombre().equals(""))) {
         mensajeError = "El Nombre es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       datos.setCMeritoId(formulario.getCMerito_id());
       OCAPConfigApp.logger.info("cMeritoId: " + datos.getCMeritoId());
 
       datos.setCDefmeritoId(formulario.getCDefmerito_id());
       OCAPConfigApp.logger.info("cDefmeritoId: " + datos.getCDefmeritoId());
 
       datos.setCEstatutId(formulario.getCEstatut_id());
       OCAPConfigApp.logger.info("cEstatutId: " + datos.getCEstatutId());
 
       datos.setCActividadId(formulario.getCActividad_id());
       OCAPConfigApp.logger.info("cActividadId: " + datos.getCActividadId());
 
       datos.setCModalidadId(formulario.getCModalidad_id());
       OCAPConfigApp.logger.info("cModalidadId: " + datos.getCModalidadId());
 
       datos.setCFactorId(formulario.getCFactor_id());
       OCAPConfigApp.logger.info("cFactorId: " + datos.getCFactorId());
 
       datos.setCTipoId(formulario.getCTipo_id());
       OCAPConfigApp.logger.info("cTipoId: " + datos.getCTipoId());
 
       if ((formulario.getCTipo_merito() != null) && (!formulario.getCTipo_merito().equals(""))) {
         datos.setCTipoMerito(formulario.getCTipo_merito());
         OCAPConfigApp.logger.info("cTipoMerito: " + datos.getCTipoMerito());
       }
 
       if ((formulario.getDNombrecorto() != null) && (!formulario.getDNombrecorto().equals(""))) {
         datos.setDNombrecorto(formulario.getDNombrecorto());
         OCAPConfigApp.logger.info("dNombrecorto : " + datos.getDNombrecorto());
       }
 
       if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
         datos.setDNombre(formulario.getDNombre());
         OCAPConfigApp.logger.info("dNombre : " + datos.getDNombre());
       }
 
       if ((formulario.getDDescripcion() != null) && (!formulario.getDDescripcion().equals(""))) {
         datos.setDDescripcion(formulario.getDDescripcion());
         OCAPConfigApp.logger.info("dDescripcion : " + datos.getDDescripcion());
       }
 
       if ((formulario.getDObservaciones() != null) && (!formulario.getDObservaciones().equals(""))) {
         datos.setDObservaciones(formulario.getDObservaciones());
         OCAPConfigApp.logger.info("dObservaciones : " + datos.getDObservaciones());
       }
 
       if ((formulario.getNCreditos() != null) && (!formulario.getNCreditos().equals(""))) {
         datos.setNCreditos(Float.valueOf(formulario.getNCreditos()).floatValue());
       }
 
       int result = oCAPMeritoscurricularesLN.modificacionOCAPMeritoscurriculares(datos);
 
       if (result == 1)
       {
         request.setAttribute("mensaje", "El registro se ha modificado con éxito");
         request.setAttribute("rutaVuelta", "OCAPMeritoscurriculares.do?accion=detalle&cMeritoIdS=" + datos.getCMeritoId());
         sig = "exito";
       }
       else {
         request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
         sig = "error";
       }
       OCAPConfigApp.logger.info("---------- FIN GRABAR MODIFICACION OCAPMeritoscurriculares --------- ");
     }
     catch (Exception ex) {
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

