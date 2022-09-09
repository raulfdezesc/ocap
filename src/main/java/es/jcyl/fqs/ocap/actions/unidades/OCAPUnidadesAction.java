 package es.jcyl.fqs.ocap.actions.unidades;
 
 import es.jcyl.cf.seguridad.util.Usuario;
 import es.jcyl.fqs.ocap.actionforms.unidades.OCAPUnidadesForm;
 import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ln.areas.OCAPAreasLN;
 import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
 import es.jcyl.fqs.ocap.ln.unidades.OCAPUnidadesLN;
 import es.jcyl.fqs.ocap.ot.areas.OCAPAreasOT;
 import es.jcyl.fqs.ocap.ot.categProfesionales.OCAPCategProfesionalesOT;
 import es.jcyl.fqs.ocap.ot.unidades.OCAPUnidadesOT;
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
 
 public class OCAPUnidadesAction extends OCAPGenericAction
 {
   public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     Logger log = null;
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- BAJA DE OCAP_UNIDADES --------- ");
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPUnidadesLN oCAPUnidadesLN = new OCAPUnidadesLN(jcylUsuario);
 
       OCAPUnidadesForm formulario = (OCAPUnidadesForm)form;
 
       String cUnidadIdS = request.getParameter("cUnidadIdS");
       int cUnidadId;
       if ((cUnidadIdS != null) && (!cUnidadIdS.equals(""))) {
         cUnidadId = Integer.parseInt(cUnidadIdS);
         OCAPConfigApp.logger.info("cUnidadId: " + cUnidadId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
       String usuario = ((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id();
 
       int result = oCAPUnidadesLN.bajaOCAPUnidades(cUnidadId, usuario);
 
       if (result == 0)
       {
         OCAPConfigApp.logger.info("Se ha producido un error al borrar el registro");
         sig = "error";
         request.setAttribute("mensaje", "Se ha producido un error al borrar el registro");
       }
       else {
         OCAPConfigApp.logger.info("El registro se ha eliminado con éxito");
         request.setAttribute("mensaje", "El registro se ha eliminado con éxito");
         request.setAttribute("rutaVuelta", "OCAPUnidades.do?accion=buscar&recuperarBusquedaAnterior=Y");
 
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
 
       OCAPAreasLN areasLN = new OCAPAreasLN(jcylUsuario);
       Collection listadoAreas = areasLN.listadoOCAPAreas();
       Object[] listadoArea = listadoAreas.toArray();
 
       OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
       Collection listadoProf = categProfesionalesLN.listarCategoriasProfesionales("Unidad");
       Object[] listadoProfesional = listadoProf.toArray();
 
       session.setAttribute(Constantes.COMBO_PROFESIONAL, utilidades.ArrayObjectToArrayList(listadoProfesional));
     }
     catch (Exception e)
     {
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
       OCAPConfigApp.logger.info("---------- CONSULTA DE OCAP_UNIDADES --------- ");
       ActionErrors errores = new ActionErrors();
       request.setAttribute("primeraConsulta", "false");
 
       if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR))) && (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
         form = (OCAPUnidadesForm)request.getSession().getAttribute("OCAPUnidadesFormBuscador");
         request.setAttribute("OCAPUnidadesForm", form);
       } else {
         request.getSession().setAttribute("OCAPUnidadesFormBuscador", form);
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
       OCAPUnidadesForm formulario = (OCAPUnidadesForm)form;
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPUnidadesLN oCAPUnidadesLN = new OCAPUnidadesLN(jcylUsuario);
 
       long cUnidadId = 0L;
       long cAreaId = 0L;
       long cProfesionalId = 0L;
       long cItinerarioId = 0L;
       String dDescripcion = null;
       String dObservaciones = null;
       String dNombre = null;
       String fCreacion = null;
       String fModificacion = null;
       String aNombreCorto = null;
 
       cUnidadId = formulario.getCUnidad_id();
 
       cAreaId = formulario.getCArea_id();
 
       cProfesionalId = formulario.getCProfesional_id();
 
       cItinerarioId = formulario.getCItinerario_id();
 
       if ((formulario.getDDescripcion() != null) && (!formulario.getDDescripcion().equals(""))) {
         dDescripcion = formulario.getDDescripcion();
       }
 
       if ((formulario.getDObservaciones() != null) && (!formulario.getDObservaciones().equals(""))) {
         dObservaciones = formulario.getDObservaciones();
       }
 
       if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
         dNombre = formulario.getDNombre();
       }
 
       if ((formulario.getANombre_corto() != null) && (!formulario.getANombre_corto().equals(""))) {
         aNombreCorto = formulario.getANombre_corto();
       }
 
       if ((formulario.getACreacion() != null) && (!formulario.getACreacion().equals("")) && (formulario.getMCreacion() != null) && (!formulario.getMCreacion().equals("")) && (formulario.getDCreacion() != null) && (!formulario.getDCreacion().equals("")))
       {
         fCreacion = formulario.getDCreacion() + '/' + formulario.getMCreacion() + '/' + formulario.getACreacion();
       }
 
       if ((formulario.getAModificacion() != null) && (!formulario.getAModificacion().equals("")) && (formulario.getMModificacion() != null) && (!formulario.getMModificacion().equals("")) && (formulario.getDModificacion() != null) && (!formulario.getDModificacion().equals("")))
       {
         fModificacion = formulario.getDModificacion() + '/' + formulario.getMModificacion() + '/' + formulario.getAModificacion();
       }
 
       Collection elementos = oCAPUnidadesLN.consultaOCAPUnidades(cUnidadId, cAreaId, dNombre, dDescripcion, dObservaciones, cProfesionalId, cItinerarioId, aNombreCorto, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
 
       if (elementos != null) {
         OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Unidades para la consulta");
         if (elementos.size() == 0)
         {
           request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
           request.removeAttribute("conDatos");
           request.getSession().removeAttribute("paginaOCAPUnidadesOT");
         } else {
           Object[] listadoelementos = elementos.toArray();
 
           OCAPAreasLN areasLN = new OCAPAreasLN(jcylUsuario);
           OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
 
           int reg = elementos.size();
 
           elementos.removeAll(elementos);
 
           for (int i = 0; i < reg; i++) {
             ((OCAPUnidadesOT)listadoelementos[i]).setDAreaNombre(areasLN.buscarOCAPAreas(((OCAPUnidadesOT)listadoelementos[i]).getCAreaId()).getDNombre());
             ((OCAPUnidadesOT)listadoelementos[i]).setDProfesionalNombre(categProfesionalesLN.buscarOCAPCategProfesionales(((OCAPUnidadesOT)listadoelementos[i]).getCProfesionalId()).getDNombre());
             elementos.add(listadoelementos[i]);
           }
 
           int nListado = 0;
           nListado = oCAPUnidadesLN.listadoOCAPUnidadesCuenta(cUnidadId, cAreaId, dNombre, dDescripcion, dObservaciones, cProfesionalId, cItinerarioId, aNombreCorto, fCreacion, fModificacion);
           request.removeAttribute("sinDatos");
           Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
           pagina.setElementos(elementos);
           pagina.setRegistroActual(primerRegistro);
           pagina.setNRegistros(nListado);
           pagina.setRegistrosPorPagina(registrosPorPagina);
           request.setAttribute("paginaOCAPUnidadesOT", pagina);
           request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
           request.removeAttribute("menu");
           request.getSession().removeAttribute("menu");
         }
       }
       else {
         request.setAttribute("errorConsultando", "Error consultando en la base de datos");
       }
       OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAP_UNIDADES ------- ");
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
       OCAPConfigApp.logger.info("---------- DETALLE OCAP_UNIDADES --------- ");
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPUnidadesLN oCAPUnidadesLN = new OCAPUnidadesLN(jcylUsuario);
       OCAPUnidadesOT datos = new OCAPUnidadesOT();
 
       OCAPUnidadesForm formulario = (OCAPUnidadesForm)form;
 
       String cUnidadIdS = request.getParameter("cUnidadIdS");
       long cUnidadId;
       if ((cUnidadIdS != null) && (!cUnidadIdS.equals(""))) {
         cUnidadId = Long.parseLong(cUnidadIdS);
         OCAPConfigApp.logger.info("cUnidadId: " + cUnidadId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }datos = oCAPUnidadesLN.buscarOCAPUnidades(cUnidadId);
 
       if (datos.getCUnidadId() == 0L)
       {
         OCAPConfigApp.logger.info("No encontramos OCAPUnidadesOT");
         sig = "error";
         request.getSession().removeAttribute("conDatosDetalle");
         request.setAttribute("mensaje", "No se encuentra el registro");
       }
       else {
         OCAPAreasLN areasLN = new OCAPAreasLN(jcylUsuario);
         OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
 
         formulario.setCUnidad_id(datos.getCUnidadId());
         formulario.setDArea_nombre(areasLN.buscarOCAPAreas(datos.getCAreaId()).getDNombre());
         formulario.setDProfesional_nombre(categProfesionalesLN.buscarOCAPCategProfesionales(datos.getCProfesionalId()).getDNombre());
         formulario.setCItinerario_id(datos.getCItinerarioId());
 
         if (datos.getDDescripcion() == null)
           formulario.setDDescripcion("");
         else {
           formulario.setDDescripcion(datos.getDDescripcion());
         }
         if (datos.getDObservaciones() == null)
           formulario.setDObservaciones("");
         else {
           formulario.setDObservaciones(datos.getDObservaciones());
         }
         formulario.setDNombre(datos.getDNombre());
         formulario.setANombre_corto(datos.getANombreCorto());
 
         OCAPConfigApp.logger.info("Se ha encontrado OCAPUnidades");
         request.getSession().setAttribute("conDatosDetalle", "");
         request.getSession().removeAttribute("sinDatosDetalle");
         request.getSession().setAttribute("OCAPUnidadesOT", datos);
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
 
       OCAPAreasLN areasLN = new OCAPAreasLN(jcylUsuario);
       Collection listadoAreas = areasLN.listadoOCAPAreas();
       Object[] listadoArea = listadoAreas.toArray();
 
       OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
       Collection listadoProf = categProfesionalesLN.listarCategoriasProfesionales("Unidad");
       Object[] listadoProfesional = listadoProf.toArray();
 
       session.setAttribute(Constantes.COMBO_PROFESIONAL, utilidades.ArrayObjectToArrayList(listadoProfesional));
 
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
       OCAPConfigApp.logger.info("---------- ALTA OCAP_UNIDADES --------- ");
 
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPUnidadesLN oCAPUnidadesLN = new OCAPUnidadesLN(jcylUsuario);
       OCAPUnidadesOT datos = new OCAPUnidadesOT();
 
       OCAPUnidadesForm formulario = (OCAPUnidadesForm)form;
       OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
 
       datos.setCAreaId(formulario.getCArea_id());
       OCAPConfigApp.logger.info("cAreaid : " + datos.getCAreaId());
 
       if (formulario.getCArea_id() == 0L) {
         mensajeError = "El campo Nombre de Area es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       datos.setCProfesionalId(formulario.getCProfesional_id());
 
       if (formulario.getCProfesional_id() == 0L) {
         mensajeError = "El campo Nombre de Profesional es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       datos.setCItinerarioId(formulario.getCItinerario_id());
 
       if (formulario.getCItinerario_id() == 0L) {
         mensajeError = "El campo Itinerario es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
         datos.setDNombre(formulario.getDNombre());
         OCAPConfigApp.logger.info("dNombre : " + datos.getDNombre());
       }
       if ((formulario.getDNombre() == null) || (formulario.getDNombre().equals(""))) {
         mensajeError = "El campo Nombre de Unidad es obligatorio.";
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
 
       if ((formulario.getANombre_corto() != null) && (!formulario.getANombre_corto().equals(""))) {
         datos.setANombreCorto(formulario.getANombre_corto());
         OCAPConfigApp.logger.info("aNombreCorto : " + datos.getANombreCorto());
       }
 
       if ((formulario.getANombre_corto() == null) || (formulario.getANombre_corto().equals(""))) {
         mensajeError = "El campo Nombre Corto es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       datos.setACreadoPor(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
 
       int result = oCAPUnidadesLN.altaOCAPUnidades(datos);
       if (result == 1)
       {
         request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
         request.setAttribute("rutaVuelta", "OCAPUnidades.do?accion=irInsertar");
         sig = "exito";
       }
       else {
         request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
         sig = "error";
       }
       OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA OCAPUnidades --------- ");
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
       OCAPUnidadesLN oCAPUnidadesLN = new OCAPUnidadesLN(jcylUsuario);
       OCAPUnidadesOT datos = new OCAPUnidadesOT();
       OCAPUnidadesForm formulario = (OCAPUnidadesForm)form;
 
       String cUnidadIdS = request.getParameter("cUnidadIdS");
       long cUnidadId;
       if ((cUnidadIdS != null) && (!cUnidadIdS.equals(""))) {
         cUnidadId = Long.parseLong(cUnidadIdS);
         OCAPConfigApp.logger.info("cUnidadId: " + cUnidadId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
 
       datos = oCAPUnidadesLN.buscarOCAPUnidades(cUnidadId);
 
       if (datos.getCUnidadId() == 0L) {
         OCAPConfigApp.logger.info("No encontramos OCAPUnidadesOT");
         sig = "error";
         request.getSession().removeAttribute("conDatosEdicion");
         request.setAttribute("mensaje", "No se encuentra el registro");
       } else {
         OCAPConfigApp.logger.info("Se ha encontrado OCAPUnidadesOT");
         request.getSession().setAttribute("conDatosEdicion", "");
         request.getSession().removeAttribute("sinDatosEdicion");
         request.setAttribute("OCAPUnidadesOT", datos);
 
         OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
 
         formulario.setCUnidad_id(datos.getCUnidadId());
         formulario.setCArea_id(datos.getCAreaId());
         formulario.setDProfesional_nombre(categProfesionalesLN.buscarOCAPCategProfesionales(datos.getCProfesionalId()).getDNombre());
         formulario.setDNombre(datos.getDNombre());
         formulario.setCItinerario_id(datos.getCItinerarioId());
         formulario.setANombre_corto(datos.getANombreCorto());
 
         if (datos.getDDescripcion() == null)
           formulario.setDDescripcion("");
         else {
           formulario.setDDescripcion(datos.getDDescripcion());
         }
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
       OCAPConfigApp.logger.info("---------- GRABAR OCAP_UNIDADES --------- ");
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPUnidadesLN oCAPUnidadesLN = new OCAPUnidadesLN(jcylUsuario);
       OCAPUnidadesOT datos = new OCAPUnidadesOT();
 
       OCAPUnidadesForm formulario = (OCAPUnidadesForm)form;
 
       if (formulario.getCUnidad_id() != 0L) {
         OCAPConfigApp.logger.info("cUnidadId: " + formulario.getCUnidad_id());
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
       OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
 
       datos.setCUnidadId(formulario.getCUnidad_id());
       datos.setCAreaId(formulario.getCArea_id());
       datos.setCItinerarioId(formulario.getCItinerario_id());
 
       if (formulario.getCItinerario_id() == 0L) {
         mensajeError = "El campo Itinerario es obligatorio.";
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
 
       if (formulario.getCArea_id() == 0L) {
         mensajeError = "El campo Nombre de Area es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       datos.setAModificadoPor(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
 
       int result = oCAPUnidadesLN.modificacionOCAPUnidades(datos);
 
       if (result == 1)
       {
         request.setAttribute("mensaje", "El registro se ha modificado con éxito");
 
         request.setAttribute("rutaVuelta", "OCAPUnidades.do?accion=detalle&cUnidadIdS=" + datos.getCUnidadId());
         sig = "exito";
       }
       else {
         request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
         sig = "error";
       }
       OCAPConfigApp.logger.info("---------- FIN GRABAR MODIFICACION OCAPUnidades --------- ");
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       sig = "error";
       request.setAttribute("mensaje", mensajeError);
     }
 
     return mapping.findForward(sig);
   }
 }

