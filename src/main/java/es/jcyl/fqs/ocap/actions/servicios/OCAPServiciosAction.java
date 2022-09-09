 package es.jcyl.fqs.ocap.actions.servicios;
 
 import es.jcyl.cf.seguridad.util.Usuario;
 import es.jcyl.fqs.ocap.actionforms.servicios.OCAPServiciosForm;
 import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
 import es.jcyl.fqs.ocap.ln.especialidades.OCAPEspecialidadesLN;
 import es.jcyl.fqs.ocap.ln.itinerarios.OCAPItinerariosLN;
 import es.jcyl.fqs.ocap.ln.servicios.OCAPServiciosLN;
 import es.jcyl.fqs.ocap.ot.categProfesionales.OCAPCategProfesionalesOT;
 import es.jcyl.fqs.ocap.ot.especialidades.OCAPEspecialidadesOT;
 import es.jcyl.fqs.ocap.ot.servicios.OCAPServiciosOT;
 import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Pagina;
 import es.jcyl.fqs.ocap.util.Utilidades;
 import es.jcyl.framework.JCYLUsuario;
 import java.io.IOException;
 import java.util.ArrayList;
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
 
 public class OCAPServiciosAction extends OCAPGenericAction
 {
   public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     Logger log = null;
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- BAJA DE OCAP_SERVICIO --------- ");
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPServiciosLN oCAPServiciosLN = new OCAPServiciosLN(jcylUsuario);
 
       OCAPServiciosForm formulario = (OCAPServiciosForm)form;
 
       String cServicioIdS = request.getParameter("cServicioIdS");
       int cServicioId;
       if ((cServicioIdS != null) && (!cServicioIdS.equals(""))) {
         cServicioId = Integer.parseInt(cServicioIdS);
         OCAPConfigApp.logger.info("cServicioId: " + cServicioId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
       String usuario = ((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id();
 
       int result = oCAPServiciosLN.bajaOCAPServicios(cServicioId, usuario);
 
       if (result == 0)
       {
         OCAPConfigApp.logger.info("Se ha producido un error al borrar el registro");
         sig = "error";
         request.setAttribute("mensaje", "Se ha producido un error al borrar el registro");
       }
       else {
         OCAPConfigApp.logger.info("El registro se ha eliminado con éxito");
         request.setAttribute("mensaje", "El registro se ha eliminado con éxito");
         request.setAttribute("rutaVuelta", "OCAPServicios.do?accion=buscar&recuperarBusquedaAnterior=Y");
 
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
     String sig = "error";
     String mensajeError = "Se ha producido un error";
     HttpSession session = request.getSession();
     Utilidades utilidades = new Utilidades();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irBuscar: Inicio");
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
       Collection listadoProf = categProfesionalesLN.listarCategoriasProfesionales("Servicio");
       Object[] listadoProfesional = listadoProf.toArray();
 
       OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
       Collection listadoEspec = especialidadesLN.listarEspecialidadesServicio();
       Object[] listadoEspecialidades = listadoEspec.toArray();
 
       session.setAttribute(Constantes.COMBO_PROFESIONAL, utilidades.ArrayObjectToArrayList(listadoProfesional));
 
       session.setAttribute(Constantes.COMBO_ESPECIALIDAD, utilidades.ArrayObjectToArrayList(listadoEspecialidades));
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
     String sig = "error";
     String mensajeError = "Se ha producido un error";
     Logger log = null;
     ActionForward actionForward = null;
     HttpSession session = request.getSession();
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- CONSULTA DE OCAP_SERVICIO --------- ");
       ActionErrors errores = new ActionErrors();
       request.setAttribute("primeraConsulta", "false");
 
       if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR))) && (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
         form = (OCAPServiciosForm)request.getSession().getAttribute("OCAPServiciosFormBuscador");
         request.setAttribute("OCAPServiciosForm", form);
       } else {
         request.getSession().setAttribute("OCAPServiciosFormBuscador", form);
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
       OCAPServiciosForm formulario = (OCAPServiciosForm)form;
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPServiciosLN oCAPServiciosLN = new OCAPServiciosLN(jcylUsuario);
 
       long cServicioId = 0L;
       long cProfesionalId = 0L;
       long cEspecId = 0L;
       long cItinerarioId = 0L;
       String dNombreLargo = null;
       String dNombreCorto = null;
       String dObservaciones = null;
       String fCreacion = null;
       String fModificacion = null;
 
       cServicioId = formulario.getCServicio_id();
       cProfesionalId = formulario.getCProfesional_id();
       cEspecId = formulario.getCEspec_id();
       cItinerarioId = formulario.getCItinerario_id();
 
       if ((formulario.getDNombre_largo() != null) && (!formulario.getDNombre_largo().equals(""))) {
         dNombreLargo = formulario.getDNombre_largo();
       }
 
       if ((formulario.getDNombre_corto() != null) && (!formulario.getDNombre_corto().equals(""))) {
         dNombreCorto = formulario.getDNombre_corto();
       }
 
       if ((formulario.getDObservaciones() != null) && (!formulario.getDObservaciones().equals(""))) {
         dObservaciones = formulario.getDObservaciones();
       }
 
       if ((formulario.getACreacion() != null) && (!formulario.getACreacion().equals("")) && (formulario.getMCreacion() != null) && (!formulario.getMCreacion().equals("")) && (formulario.getDCreacion() != null) && (!formulario.getDCreacion().equals("")))
       {
         fCreacion = formulario.getDCreacion() + '/' + formulario.getMCreacion() + '/' + formulario.getACreacion();
       }
 
       if ((formulario.getAModificacion() != null) && (!formulario.getAModificacion().equals("")) && (formulario.getMModificacion() != null) && (!formulario.getMModificacion().equals("")) && (formulario.getDModificacion() != null) && (!formulario.getDModificacion().equals("")))
       {
         fModificacion = formulario.getDModificacion() + '/' + formulario.getMModificacion() + '/' + formulario.getAModificacion();
       }
 
       Collection elementos = oCAPServiciosLN.consultaOCAPServicios(cServicioId, dNombreCorto, dNombreLargo, dObservaciones, fCreacion, fModificacion, primerRegistro, registrosPorPagina, cProfesionalId, cEspecId, cItinerarioId);
       if (elementos != null) {
         OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Servicio para la consulta");
         if (elementos.size() == 0)
         {
           request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
           request.removeAttribute("conDatos");
           request.getSession().removeAttribute("paginaOCAPServiciosOT");
         } else {
           Object[] listadoelementos = elementos.toArray();
 
           OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
           OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
           OCAPItinerariosLN itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
 
           int reg = elementos.size();
 
           elementos.removeAll(elementos);
 
           for (int i = 0; i < reg; i++) {
             ((OCAPServiciosOT)listadoelementos[i]).setDProfesionalNombre(categProfesionalesLN.buscarOCAPCategProfesionales(((OCAPServiciosOT)listadoelementos[i]).getCProfesionalId()).getDNombre());
             ((OCAPServiciosOT)listadoelementos[i]).setDEspecNombre(especialidadesLN.buscarOCAPEspecialidades(((OCAPServiciosOT)listadoelementos[i]).getCEspecId()).getDDescripcion());
             elementos.add(listadoelementos[i]);
           }
 
           int nListado = 0;
           nListado = oCAPServiciosLN.listadoOCAPServiciosCuenta(cServicioId, dNombreCorto, dNombreLargo, dObservaciones, cProfesionalId, cEspecId, cItinerarioId, fCreacion, fModificacion);
           request.removeAttribute("sinDatos");
           Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
           pagina.setElementos(elementos);
           pagina.setRegistroActual(primerRegistro);
           pagina.setNRegistros(nListado);
           pagina.setRegistrosPorPagina(registrosPorPagina);
           request.setAttribute("paginaOCAPServiciosOT", pagina);
           request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
           request.removeAttribute("menu");
           request.getSession().removeAttribute("menu");
         }
       }
       else {
         request.setAttribute("errorConsultando", "Error consultando en la base de datos");
       }
       OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAP_SERVICIO ------- ");
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
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- DETALLE OCAP_SERVICIO --------- ");
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPServiciosLN oCAPServiciosLN = new OCAPServiciosLN(jcylUsuario);
       OCAPServiciosOT datos = new OCAPServiciosOT();
 
       OCAPServiciosForm formulario = (OCAPServiciosForm)form;
 
       String cServicioIdS = request.getParameter("cServicioIdS");
       long cServicioId;
       if ((cServicioIdS != null) && (!cServicioIdS.equals(""))) {
         cServicioId = Long.parseLong(cServicioIdS);
         OCAPConfigApp.logger.info("cServicioId: " + cServicioId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
       datos = oCAPServiciosLN.buscarOCAPServicios(cServicioId);
       if (datos.getCServicioId() == 0L)
       {
         OCAPConfigApp.logger.info("No encontramos OCAPServiciosOT");
         sig = "error";
         request.getSession().removeAttribute("conDatosDetalle");
         request.setAttribute("mensaje", "No se encuentra el registro");
       }
       else {
         OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
         OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
 
         formulario.setCServicio_id(datos.getCServicioId());
         formulario.setDProfesional_nombre(categProfesionalesLN.buscarOCAPCategProfesionales(datos.getCProfesionalId()).getDNombre());
 
         if (datos.getCEspecId() == 0L)
           formulario.setDEspec_nombre("");
         else {
           formulario.setDEspec_nombre(especialidadesLN.buscarOCAPEspecialidades(datos.getCEspecId()).getDNombre());
         }
         formulario.setCItinerario_id(datos.getCItinerarioId());
 
         formulario.setDNombre_largo(datos.getDNombreLargo());
         formulario.setDNombre_corto(datos.getDNombreCorto());
 
         if (datos.getDObservaciones() == null)
           formulario.setDObservaciones("");
         else {
           formulario.setDObservaciones(datos.getDObservaciones());
         }
         OCAPConfigApp.logger.info("Se ha encontrado OCAPServicio");
         request.getSession().setAttribute("conDatosDetalle", "");
         request.getSession().removeAttribute("sinDatosDetalle");
         request.getSession().setAttribute("OCAPServiciosOT", datos);
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
 
   public ActionForward irInsertar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errors = new ActionErrors();
     String sig = "error";
     String mensajeError = "Se ha producido un error";
     HttpSession session = request.getSession();
     Utilidades utilidades = new Utilidades();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Inicio");
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
       Collection listadoProf = categProfesionalesLN.listarCategoriasProfesionales("Servicio");
       Object[] listadoProfesional = listadoProf.toArray();
 
       session.setAttribute(Constantes.COMBO_PROFESIONAL, utilidades.ArrayObjectToArrayList(listadoProfesional));
 
       session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
 
       request.setAttribute("tipoAccion", Constantes.INSERTAR);
 
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: ERROR: " + e.getMessage());
       request.setAttribute("mensaje", mensajeError);
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward("irInsertar");
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward insertar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     String mensajeError = "Se ha producido un error";
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- ALTA OCAP_SERVICIO --------- ");
 
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPServiciosLN oCAPServiciosLN = new OCAPServiciosLN(jcylUsuario);
       OCAPServiciosOT datos = new OCAPServiciosOT();
 
       OCAPServiciosForm formulario = (OCAPServiciosForm)form;
       OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
 
       datos.setCProfesionalId(formulario.getCProfesional_id());
 
       if (formulario.getCProfesional_id() == 0L) {
         mensajeError = "El campo Nombre de Profesional es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       datos.setCEspecId(formulario.getCEspec_id());
 
       datos.setCItinerarioId(formulario.getCItinerario_id());
 
       if (formulario.getCItinerario_id() == 0L) {
         mensajeError = "El campo Itinerario es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       if ((formulario.getDNombre_corto() != null) && (!formulario.getDNombre_corto().equals(""))) {
         datos.setDNombreCorto(formulario.getDNombre_corto());
         OCAPConfigApp.logger.info("dNombreCorto: " + datos.getDNombreCorto());
       }
       if ((formulario.getDNombre_corto() == null) || (formulario.getDNombre_corto().equals(""))) {
         mensajeError = "El campo Nombre corto es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       if ((formulario.getDNombre_largo() != null) && (!formulario.getDNombre_largo().equals(""))) {
         datos.setDNombreLargo(formulario.getDNombre_largo());
         OCAPConfigApp.logger.info("dNombreLargo: " + datos.getDNombreLargo());
       }
 
       if ((formulario.getDNombre_largo() == null) || (formulario.getDNombre_largo().equals(""))) {
         mensajeError = "El campo Nombre largo es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       if ((formulario.getDObservaciones() != null) && (!formulario.getDObservaciones().equals(""))) {
         datos.setDObservaciones(formulario.getDObservaciones());
         OCAPConfigApp.logger.info("dObservaciones : " + datos.getDObservaciones());
       }
 
       datos.setACreadoPor(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
 
       int result = oCAPServiciosLN.altaOCAPServicios(datos);
       if (result == 1)
       {
         request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
         request.setAttribute("rutaVuelta", "OCAPServicios.do?accion=irInsertar");
         sig = "exito";
       }
       else {
         request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
         sig = "error";
       }
 
       OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA OCAP_SERVICIO --------- ");
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       sig = "error";
       request.setAttribute("mensaje", mensajeError);
     }
 
     return mapping.findForward(sig);
   }
 
   public ActionForward irEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irEditar: Inicio");
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPServiciosLN oCAPServiciosLN = new OCAPServiciosLN(jcylUsuario);
       OCAPServiciosOT datos = new OCAPServiciosOT();
       OCAPServiciosForm formulario = (OCAPServiciosForm)form;
 
       String cServicioIdS = request.getParameter("cServicioIdS");
       long cServicioId;
       if ((cServicioIdS != null) && (!cServicioIdS.equals(""))) {
         cServicioId = Long.parseLong(cServicioIdS);
         OCAPConfigApp.logger.info("cServicioId: " + cServicioId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
 
       datos = oCAPServiciosLN.buscarOCAPServicios(cServicioId);
 
       if (datos.getCServicioId() == 0L) {
         OCAPConfigApp.logger.info("No encontramos OCAPServiciosOT");
         sig = "error";
         request.getSession().removeAttribute("conDatosEdicion");
         request.setAttribute("mensaje", "No se encuentra el registro");
       } else {
         OCAPConfigApp.logger.info("Se ha encontrado OCAPServiciosOT");
         request.getSession().setAttribute("conDatosEdicion", "");
         request.getSession().removeAttribute("sinDatosEdicion");
         request.setAttribute("OCAPServiciosOT", datos);
 
         OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
         OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
         OCAPItinerariosLN itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
 
         formulario.setCServicio_id(datos.getCServicioId());
         formulario.setDProfesional_nombre(categProfesionalesLN.buscarOCAPCategProfesionales(datos.getCProfesionalId()).getDNombre());
         formulario.setDEspec_nombre(especialidadesLN.buscarOCAPEspecialidades(datos.getCEspecId()).getDNombre());
         formulario.setCItinerario_id(datos.getCItinerarioId());
         formulario.setDNombre_largo(datos.getDNombreLargo());
         formulario.setDNombre_corto(datos.getDNombreCorto());
 
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
 
   public ActionForward grabar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     String mensajeError = "Se ha producido un error";
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- GRABAR OCAP_SERVICIO --------- ");
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPServiciosLN oCAPServiciosLN = new OCAPServiciosLN(jcylUsuario);
       OCAPServiciosOT datos = new OCAPServiciosOT();
 
       OCAPServiciosForm formulario = (OCAPServiciosForm)form;
 
       if (formulario.getCServicio_id() != 0L) {
         OCAPConfigApp.logger.info("cServicioId: " + formulario.getCServicio_id());
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
       OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
 
       datos.setCServicioId(formulario.getCServicio_id());
       datos.setCItinerarioId(formulario.getCItinerario_id());
 
       if (formulario.getCItinerario_id() == 0L) {
         mensajeError = "El campo Itinerario es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       if ((formulario.getDObservaciones() != null) && (!formulario.getDObservaciones().equals(""))) {
         datos.setDObservaciones(formulario.getDObservaciones());
         OCAPConfigApp.logger.info("dObservaciones : " + datos.getDObservaciones());
       }
 
       datos.setAModificadoPor(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
 
       int result = oCAPServiciosLN.modificacionOCAPServicios(datos);
 
       if (result == 1)
       {
         request.setAttribute("mensaje", "El registro se ha modificado con éxito");
 
         request.setAttribute("rutaVuelta", "OCAPServicios.do?accion=detalle&cServicioIdS=" + datos.getCServicioId());
         sig = "exito";
       }
       else {
         request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
         sig = "error";
       }
       OCAPConfigApp.logger.info("---------- FIN GRABAR MODIFICACION OCAP_SERVICIO --------- ");
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       sig = "error";
       request.setAttribute("mensaje", mensajeError);
     }
 
     return mapping.findForward(sig);
   }
 
   public ActionForward cargarComboEspecialidades(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errors = new ActionErrors();
     String sig = "error";
     String mensajeError = "Se ha producido un error";
     HttpSession session = request.getSession();
     Utilidades utilidades = new Utilidades();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboEspecialidades: Inicio");
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       if (((OCAPServiciosForm)form).getCProfesional_id() == 1L) {
         OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
         Collection listadoEspec = especialidadesLN.listarEspecialidadesServicio();
         Object[] listadoEspecialidades = listadoEspec.toArray();
 
         session.setAttribute(Constantes.COMBO_ESPECIALIDAD, utilidades.ArrayObjectToArrayList(listadoEspecialidades));
       } else {
         session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
       }
 
       request.setAttribute("tipoAccion", Constantes.INSERTAR);
 
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboEspecialidades: ERROR: " + e.getMessage());
       request.setAttribute("mensaje", mensajeError);
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward("irInsertar");
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 }

