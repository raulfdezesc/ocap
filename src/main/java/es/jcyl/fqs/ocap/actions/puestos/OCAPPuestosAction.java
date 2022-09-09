 package es.jcyl.fqs.ocap.actions.puestos;
 
 import es.jcyl.cf.seguridad.util.Usuario;
 import es.jcyl.fqs.ocap.actionforms.puestos.OCAPPuestosForm;
 import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
 import es.jcyl.fqs.ocap.ln.puestos.OCAPPuestosLN;
 import es.jcyl.fqs.ocap.ot.categProfesionales.OCAPCategProfesionalesOT;
 import es.jcyl.fqs.ocap.ot.puestos.OCAPPuestosOT;
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
 
 public class OCAPPuestosAction extends OCAPGenericAction
 {
   public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     Logger log = null;
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- BAJA DE OCAP_PUESTOS --------- ");
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPPuestosLN oCAPPuestosLN = new OCAPPuestosLN(jcylUsuario);
 
       OCAPPuestosForm formulario = (OCAPPuestosForm)form;
 
       String cPuestoIdS = request.getParameter("cPuestoIdS");
       int cPuestoId;
       if ((cPuestoIdS != null) && (!cPuestoIdS.equals(""))) {
         cPuestoId = Integer.parseInt(cPuestoIdS);
         OCAPConfigApp.logger.info("cPuestoId: " + cPuestoId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
       String usuario = ((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id();
 
       int result = oCAPPuestosLN.bajaOCAPPuestos(cPuestoId, usuario);
 
       if (result == 0)
       {
         OCAPConfigApp.logger.info("Se ha producido un error al borrar el registro");
         sig = "error";
         request.setAttribute("mensaje", "Se ha producido un error al borrar el registro");
       }
       else {
         OCAPConfigApp.logger.info("El registro se ha eliminado con éxito");
         request.setAttribute("mensaje", "El registro se ha eliminado con éxito");
         request.setAttribute("rutaVuelta", "OCAPPuestos.do?accion=buscar&recuperarBusquedaAnterior=Y");
 
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
       Collection listadoProf = categProfesionalesLN.listarCategoriasProfesionales("Puesto");
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
       OCAPConfigApp.logger.info("---------- CONSULTA DE OCAP_PUESTOS --------- ");
       ActionErrors errores = new ActionErrors();
       request.setAttribute("primeraConsulta", "false");
 
       if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR))) && (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
         form = (OCAPPuestosForm)request.getSession().getAttribute("OCAPPuestosFormBuscador");
         request.setAttribute("OCAPPuestosForm", form);
       } else {
         request.getSession().setAttribute("OCAPPuestosFormBuscador", form);
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
       OCAPPuestosForm formulario = (OCAPPuestosForm)form;
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPPuestosLN oCAPPuestosLN = new OCAPPuestosLN(jcylUsuario);
 
       long cPuestoId = 0L;
       long cProfesionalId = 0L;
       long cItinerarioId = 0L;
       String dObservaciones = null;
       String dNombre = null;
       String fCreacion = null;
       String fModificacion = null;
       String aNombreCorto = null;
 
       cPuestoId = formulario.getCPuesto_id();
 
       cProfesionalId = formulario.getCProfesional_id();
 
       cItinerarioId = formulario.getCItinerario_id();
 
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
 
       Collection elementos = oCAPPuestosLN.consultaOCAPPuestos(cPuestoId, cProfesionalId, dNombre, dObservaciones, cItinerarioId, aNombreCorto, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
 
       if (elementos != null) {
         OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Personas para la consulta");
         if (elementos.size() == 0)
         {
           request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
           request.removeAttribute("conDatos");
           request.getSession().removeAttribute("paginaOCAPPuestosOT");
         } else {
           Object[] listadoelementos = elementos.toArray();
 
           OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
 
           int reg = elementos.size();
 
           elementos.removeAll(elementos);
 
           for (int i = 0; i < reg; i++) {
             ((OCAPPuestosOT)listadoelementos[i]).setDProfesionalNombre(categProfesionalesLN.buscarOCAPCategProfesionales(((OCAPPuestosOT)listadoelementos[i]).getCProfesionalId()).getDNombre());
 
             elementos.add(listadoelementos[i]);
           }
 
           int nListado = 0;
           nListado = oCAPPuestosLN.listadoOCAPPuestosCuenta(cPuestoId, cProfesionalId, dNombre, dObservaciones, cItinerarioId, aNombreCorto, fCreacion, fModificacion);
           request.removeAttribute("sinDatos");
           Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
           pagina.setElementos(elementos);
           pagina.setRegistroActual(primerRegistro);
           pagina.setNRegistros(nListado);
           pagina.setRegistrosPorPagina(registrosPorPagina);
           request.setAttribute("paginaOCAPPuestosOT", pagina);
           request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
           request.removeAttribute("menu");
           request.getSession().removeAttribute("menu");
         }
       }
       else {
         request.setAttribute("errorConsultando", "Error consultando en la base de datos");
       }
       OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAP_PUESTOS ------- ");
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
       OCAPConfigApp.logger.info("---------- DETALLE OCAP_PUESTOS --------- ");
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPPuestosLN oCAPPuestosLN = new OCAPPuestosLN(jcylUsuario);
       OCAPPuestosOT datos = new OCAPPuestosOT();
 
       OCAPPuestosForm formulario = (OCAPPuestosForm)form;
 
       String cPuestoIdS = request.getParameter("cPuestoIdS");
       long cPuestoId;
       if ((cPuestoIdS != null) && (!cPuestoIdS.equals(""))) {
         cPuestoId = Long.parseLong(cPuestoIdS);
         OCAPConfigApp.logger.info("cPuestoId: " + cPuestoId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }datos = oCAPPuestosLN.buscarOCAPPuestos(cPuestoId);
 
       if (datos.getCPuestoId() == 0L)
       {
         OCAPConfigApp.logger.info("No encontramos OCAPPuestosOT");
         sig = "error";
         request.getSession().removeAttribute("conDatosDetalle");
         request.setAttribute("mensaje", "No se encuentra el registro");
       }
       else {
         OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
 
         formulario.setCPuesto_id(datos.getCPuestoId());
         formulario.setDProfesional_nombre(categProfesionalesLN.buscarOCAPCategProfesionales(datos.getCProfesionalId()).getDNombre());
         formulario.setCItinerario_id(datos.getCItinerarioId());
 
         if (datos.getDObservaciones() == null)
           formulario.setDObservaciones("");
         else {
           formulario.setDObservaciones(datos.getDObservaciones());
         }
         formulario.setDNombre(datos.getDNombre());
         formulario.setANombre_corto(datos.getANombreCorto());
 
         OCAPConfigApp.logger.info("Se ha encontrado OCAPPuestos");
         request.getSession().setAttribute("conDatosDetalle", "");
         request.getSession().removeAttribute("sinDatosDetalle");
         request.getSession().setAttribute("OCAPPuestosOT", datos);
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
       Collection listadoProf = categProfesionalesLN.listarCategoriasProfesionales("Puesto");
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
       OCAPConfigApp.logger.info("---------- ALTA OCAP_PUESTOS --------- ");
 
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPPuestosLN oCAPPuestosLN = new OCAPPuestosLN(jcylUsuario);
       OCAPPuestosOT datos = new OCAPPuestosOT();
 
       OCAPPuestosForm formulario = (OCAPPuestosForm)form;
       OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
 
       datos.setCProfesionalId(formulario.getCProfesional_id());
       OCAPConfigApp.logger.info("cProfesionalid : " + datos.getCProfesionalId());
 
       if (formulario.getCProfesional_id() == 0L) {
         mensajeError = "El campo Nombre de Profesional es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
         datos.setDNombre(formulario.getDNombre());
         OCAPConfigApp.logger.info("dNombre : " + datos.getDNombre());
       }
 
       if ((formulario.getDNombre() == null) || (formulario.getDNombre().equals(""))) {
         mensajeError = "El campo Nombre de Puesto es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       if ((formulario.getDObservaciones() != null) && (!formulario.getDObservaciones().equals(""))) {
         datos.setDObservaciones(formulario.getDObservaciones());
         OCAPConfigApp.logger.info("dObservaciones: " + datos.getDObservaciones());
       }
 
       datos.setCItinerarioId(formulario.getCItinerario_id());
 
       if (formulario.getCItinerario_id() == 0L) {
         mensajeError = "El campo Itinerario es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
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
 
       int result = oCAPPuestosLN.altaOCAPPuestos(datos);
       if (result == 1)
       {
         request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
         request.setAttribute("rutaVuelta", "OCAPPuestos.do?accion=irInsertar");
         sig = "exito";
       }
       else {
         request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
         sig = "error";
       }
       OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA OCAPPuestos --------- ");
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
       OCAPPuestosLN oCAPPuestosLN = new OCAPPuestosLN(jcylUsuario);
       OCAPPuestosOT datos = new OCAPPuestosOT();
       OCAPPuestosForm formulario = (OCAPPuestosForm)form;
 
       String cPuestoIdS = request.getParameter("cPuestoIdS");
       long cPuestoId;
       if ((cPuestoIdS != null) && (!cPuestoIdS.equals(""))) {
         cPuestoId = Long.parseLong(cPuestoIdS);
         OCAPConfigApp.logger.info("cPuestoId: " + cPuestoId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
 
       datos = oCAPPuestosLN.buscarOCAPPuestos(cPuestoId);
 
       if (datos.getCPuestoId() == 0L) {
         OCAPConfigApp.logger.info("No encontramos OCAPPuestosOT");
         sig = "error";
         request.getSession().removeAttribute("conDatosEdicion");
         request.setAttribute("mensaje", "No se encuentra el registro");
       } else {
         OCAPConfigApp.logger.info("Se ha encontrado OCAPPuestosOT");
         request.getSession().setAttribute("conDatosEdicion", "");
         request.getSession().removeAttribute("sinDatosEdicion");
         request.setAttribute("OCAPPuestosOT", datos);
 
         OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
 
         formulario.setCPuesto_id(datos.getCPuestoId());
         formulario.setDProfesional_nombre(categProfesionalesLN.buscarOCAPCategProfesionales(datos.getCProfesionalId()).getDNombre());
 
         if (datos.getDObservaciones() == null)
           formulario.setDObservaciones("");
         else {
           formulario.setDObservaciones(datos.getDObservaciones());
         }
         formulario.setDNombre(datos.getDNombre());
         formulario.setCItinerario_id(datos.getCItinerarioId());
         formulario.setANombre_corto(datos.getANombreCorto());
 
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
       OCAPConfigApp.logger.info("---------- GRABAR OCAP_PUESTOS --------- ");
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPPuestosLN oCAPPuestosLN = new OCAPPuestosLN(jcylUsuario);
       OCAPPuestosOT datos = new OCAPPuestosOT();
 
       OCAPPuestosForm formulario = (OCAPPuestosForm)form;
 
       if (formulario.getCPuesto_id() != 0L) {
         OCAPConfigApp.logger.info("cPuestoId: " + formulario.getCPuesto_id());
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
       OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
 
       datos.setCPuestoId(formulario.getCPuesto_id());
       datos.setCItinerarioId(formulario.getCItinerario_id());
 
       if (formulario.getCItinerario_id() == 0L) {
         mensajeError = "El campo Itinerario es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       if ((formulario.getDObservaciones() != null) && (!formulario.getDObservaciones().equals(""))) {
         datos.setDObservaciones(formulario.getDObservaciones());
         OCAPConfigApp.logger.info("dObservaciones: " + datos.getDObservaciones());
       }
 
       datos.setAModificadoPor(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
 
       int result = oCAPPuestosLN.modificacionOCAPPuestos(datos);
 
       if (result == 1)
       {
         request.setAttribute("mensaje", "El registro se ha modificado con éxito");
         request.setAttribute("rutaVuelta", "OCAPPuestos.do?accion=detalle&cPuestoIdS=" + datos.getCPuestoId());
         sig = "exito";
       }
       else {
         request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
         sig = "error";
       }
       OCAPConfigApp.logger.info("---------- FIN GRABAR MODIFICACION OCAPPuestos --------- ");
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       sig = "error";
       request.setAttribute("mensaje", mensajeError);
     }
 
     return mapping.findForward(sig);
   }
 }

