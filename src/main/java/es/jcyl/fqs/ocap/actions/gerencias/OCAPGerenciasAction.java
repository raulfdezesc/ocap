 package es.jcyl.fqs.ocap.actions.gerencias;
 
 import es.jcyl.fqs.ocap.actionforms.gerencias.OCAPGerenciasForm;
 import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
 import es.jcyl.fqs.ocap.util.Constantes;
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ln.gerencias.OCAPGerenciasLN;
 import es.jcyl.fqs.ocap.ln.provincias.OCAPProvinciasLN;
 import es.jcyl.fqs.ocap.ln.tipoGerencias.OCAPTipoGerenciasLN;
 import es.jcyl.fqs.ocap.ot.gerencias.OCAPGerenciasOT;
 import es.jcyl.fqs.ocap.util.Cadenas;
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
 
 public class OCAPGerenciasAction extends OCAPGenericAction
 {
   public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- BAJA DE OCAP_GERENCIAS --------- ");
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPGerenciasLN oCAPGerenciasLN = new OCAPGerenciasLN(jcylUsuario);
 
       String cGerenciaIdS = request.getParameter("cGerenciaIdS");
       int cGerenciaId;
       if ((cGerenciaIdS != null) && (!cGerenciaIdS.equals(""))) {
         cGerenciaId = Integer.parseInt(cGerenciaIdS);
         OCAPConfigApp.logger.info("cGerenciaId: " + cGerenciaId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
       int result = oCAPGerenciasLN.bajaOCAPGerencias(cGerenciaId);
 
       if (result == 0)
       {
         OCAPConfigApp.logger.info("Se ha producido un error al borrar el registro");
         sig = "error";
         request.setAttribute("mensaje", "Se ha producido un error al borrar el registro");
       }
       else {
         OCAPConfigApp.logger.info("El registro se ha eliminado con éxito");
         request.setAttribute("mensaje", "El registro se ha eliminado con éxito");
         request.setAttribute("rutaVuelta", "OCAPGerencias.do?accion=buscar&recuperarBusquedaAnterior=Y");
 
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
       OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
       Collection listadoProv = provinciasLN.listarProvinciasComunidad("CL");
       Object[] listadoProvincias = listadoProv.toArray();
 
       OCAPTipoGerenciasLN tipoGerenciasLN = new OCAPTipoGerenciasLN(jcylUsuario);
       Collection listadoTip = tipoGerenciasLN.listadoOCAPTipoGerencias();
       Object[] listadoTipos = listadoTip.toArray();
 
       session.setAttribute(Constantes.COMBO_PROVINCIAS, utilidades.ArrayObjectToArrayList(listadoProvincias));
       session.setAttribute(Constantes.COMBO_TIPOS_GERENCIAS, utilidades.ArrayObjectToArrayList(listadoTipos));
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
     try
     {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- CONSULTA DE OCAP_GERENCIAS --------- ");
       ActionErrors errores = new ActionErrors();
       request.setAttribute("primeraConsulta", "false");
 
       if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR))) && (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
         form = (OCAPGerenciasForm)request.getSession().getAttribute("OCAPGerenciasFormBuscador");
         request.setAttribute("OCAPGerenciasForm", form);
       } else {
         request.getSession().setAttribute("OCAPGerenciasFormBuscador", form);
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
       OCAPGerenciasForm formulario = (OCAPGerenciasForm)form;
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPGerenciasLN oCAPGerenciasLN = new OCAPGerenciasLN(jcylUsuario);
 
       long cGerenciaId = 0L;
       String cProvinciaId = null;
       long cTipogerenciaId = 0L;
       String dNombre = null;
       String dNombreCorto = null;
       String aCodldap = null;
       String dGerente = null;
       String dDirector = null;
       String fCreacion = null;
       String fModificacion = null;
 
       cGerenciaId = formulario.getCGerencia_id();
 
       if ((formulario.getCProvincia_id() != null) && (!formulario.getCProvincia_id().equals(""))) {
         cProvinciaId = formulario.getCProvincia_id();
       }
 
       cTipogerenciaId = formulario.getCTipogerencia_id();
 
       if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
         dNombre = formulario.getDNombre();
       }
 
       if ((formulario.getDNombre_corto() != null) && (!formulario.getDNombre_corto().equals(""))) {
         dNombre = formulario.getDNombre_corto();
       }
 
       if ((formulario.getACodldap() != null) && (!formulario.getACodldap().equals(""))) {
         aCodldap = formulario.getACodldap();
       }
 
       dGerente = formulario.getDGerente();
       dDirector = formulario.getDDirector();
 
       if ((formulario.getACreacion() != null) && (!formulario.getACreacion().equals("")) && (formulario.getMCreacion() != null) && (!formulario.getMCreacion().equals("")) && (formulario.getDCreacion() != null) && (!formulario.getDCreacion().equals("")))
       {
         fCreacion = formulario.getDCreacion() + '/' + formulario.getMCreacion() + '/' + formulario.getACreacion();
       }
 
       if ((formulario.getAModificacion() != null) && (!formulario.getAModificacion().equals("")) && (formulario.getMModificacion() != null) && (!formulario.getMModificacion().equals("")) && (formulario.getDModificacion() != null) && (!formulario.getDModificacion().equals("")))
       {
         fModificacion = formulario.getDModificacion() + '/' + formulario.getMModificacion() + '/' + formulario.getAModificacion();
       }
 
       Collection elementos = oCAPGerenciasLN.consultaOCAPGerencias(cGerenciaId, cProvinciaId, cTipogerenciaId, dNombre, dNombreCorto, aCodldap, dGerente, dDirector, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
 
       if (elementos != null) {
         OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Gerencias para la consulta");
         if (elementos.size() == 0)
         {
           request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
           request.removeAttribute("conDatos");
           request.getSession().removeAttribute("paginaOCAPGerenciasOT");
         }
         else {
           Object[] listadoelementos = elementos.toArray();
 
           OCAPTipoGerenciasLN tipoGerenciasLN = new OCAPTipoGerenciasLN(jcylUsuario);
           OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
 
           int reg = elementos.size();
 
           elementos.removeAll(elementos);
 
           for (int i = 0; i < reg; i++) {
             ((OCAPGerenciasOT)listadoelementos[i]).setDProvinciaNombre(provinciasLN.buscarProvincia(((OCAPGerenciasOT)listadoelementos[i]).getCProvinciaId()).getDProvincia());
             ((OCAPGerenciasOT)listadoelementos[i]).setDTipogerenciaNombre(tipoGerenciasLN.buscarOCAPTipoGerencias(((OCAPGerenciasOT)listadoelementos[i]).getCTipogerenciaId()).getDNombre());
 
             elementos.add(listadoelementos[i]);
           }
 
           int nListado = 0;
           nListado = oCAPGerenciasLN.listadoOCAPGerenciasCuenta(cGerenciaId, cProvinciaId, cTipogerenciaId, dNombre, dNombreCorto, aCodldap, dGerente, dDirector, fCreacion, fModificacion);
           request.removeAttribute("sinDatos");
           Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
           pagina.setElementos(elementos);
           pagina.setRegistroActual(primerRegistro);
           pagina.setNRegistros(nListado);
           pagina.setRegistrosPorPagina(registrosPorPagina);
           request.setAttribute("paginaOCAPGerenciasOT", pagina);
           request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
           request.removeAttribute("menu");
           request.getSession().removeAttribute("menu");
         }
       }
       else {
         request.setAttribute("errorConsultando", "Error consultando en la base de datos");
       }
       OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAPGERENCIAS ------- ");
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
     ActionErrors errores = new ActionErrors();
     try
     {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- DETALLE OCAP_GERENCIAS --------- ");
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPGerenciasLN oCAPGerenciasLN = new OCAPGerenciasLN(jcylUsuario);
       OCAPGerenciasOT datos = new OCAPGerenciasOT();
 
       OCAPGerenciasForm formulario = (OCAPGerenciasForm)form;
 
       String cGerenciaIdS = request.getParameter("cGerenciaIdS");
       long cGerenciaId;
       if ((cGerenciaIdS != null) && (!cGerenciaIdS.equals(""))) {
         cGerenciaId = Long.parseLong(cGerenciaIdS);
         OCAPConfigApp.logger.info("cGerenciaId: " + cGerenciaId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }datos = oCAPGerenciasLN.buscarOCAPGerencias(cGerenciaId);
 
       if (datos.getCGerenciaId() == 0L)
       {
         OCAPConfigApp.logger.info("No encontramos OCAPGerenciasOT");
         sig = "error";
         request.getSession().removeAttribute("conDatosDetalle");
         request.setAttribute("mensaje", "No se encuentra el registro");
       }
       else {
         OCAPTipoGerenciasLN tipoGerenciasLN = new OCAPTipoGerenciasLN(jcylUsuario);
         OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
 
         formulario.setCGerencia_id(datos.getCGerenciaId());
         formulario.setCProvincia_id(datos.getCProvinciaId());
         formulario.setDProvincia_nombre(provinciasLN.buscarProvincia(datos.getCProvinciaId()).getDProvincia());
         formulario.setDTipogerenciaNombre(tipoGerenciasLN.buscarOCAPTipoGerencias(datos.getCTipogerenciaId()).getDNombre());
         formulario.setDNombre(datos.getDNombre());
         formulario.setDNombre_corto(datos.getDNombreCorto());
         formulario.setACodldap(datos.getACodldap());
         formulario.setDGerente(datos.getDGerente());
         formulario.setDDirector(datos.getDDirector());
 
         OCAPConfigApp.logger.info("Se ha encontrado OcapGerencias");
         request.getSession().setAttribute("conDatosDetalle", "");
         request.getSession().removeAttribute("sinDatosDetalle");
         request.getSession().setAttribute("OCAPGerenciasOT", datos);
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
     String mensajeError = "Se ha producido un error";
     HttpSession session = request.getSession();
     Utilidades utilidades = new Utilidades();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Inicio");
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
       Collection listadoProv = provinciasLN.listarProvinciasComunidad("CL");
       Object[] listadoProvincias = listadoProv.toArray();
 
       OCAPTipoGerenciasLN tipoGerenciasLN = new OCAPTipoGerenciasLN(jcylUsuario);
       Collection listadoTip = tipoGerenciasLN.listadoOCAPTipoGerencias();
       Object[] listadoTipos = listadoTip.toArray();
 
       session.setAttribute(Constantes.COMBO_PROVINCIAS, utilidades.ArrayObjectToArrayList(listadoProvincias));
       session.setAttribute(Constantes.COMBO_TIPOS_GERENCIAS, utilidades.ArrayObjectToArrayList(listadoTipos));
 
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
     HttpSession session = request.getSession();
     try
     {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- ALTA OCAP_GERENCIAS --------- ");
 
       ActionErrors errores = new ActionErrors();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPGerenciasLN oCAPGerenciasLN = new OCAPGerenciasLN(jcylUsuario);
       OCAPGerenciasOT datos = new OCAPGerenciasOT();
 
       OCAPGerenciasForm formulario = (OCAPGerenciasForm)form;
       OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
 
       if ((formulario.getCProvincia_id() != null) && (!formulario.getCProvincia_id().equals(""))) {
         datos.setCProvinciaId(formulario.getCProvincia_id());
         OCAPConfigApp.logger.info("cProvinciaId: " + datos.getCProvinciaId());
       }
 
       if ((formulario.getCProvincia_id() == null) || (formulario.getCProvincia_id().equals(""))) {
         mensajeError = "El campo Provincia es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       datos.setCTipogerenciaId(formulario.getCTipogerencia_id());
       OCAPConfigApp.logger.info("cTipogerenciaId: " + datos.getCTipogerenciaId());
 
       if (formulario.getCTipogerencia_id() == 0L) {
         mensajeError = "El campo Tipo de Gerencia es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
         datos.setDNombre(formulario.getDNombre());
         OCAPConfigApp.logger.info("dNombre: " + datos.getDNombre());
       }
       if ((formulario.getDNombre() == null) || (formulario.getDNombre().equals(""))) {
         mensajeError = "El campo Nombre de Gerencia es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       if ((formulario.getDNombre_corto() != null) && (!formulario.getDNombre_corto().equals(""))) {
         datos.setDNombreCorto(formulario.getDNombre_corto());
         OCAPConfigApp.logger.info("dNombreCorto: " + datos.getDNombreCorto());
       }
       if ((formulario.getDNombre_corto() == null) || (formulario.getDNombre_corto().equals(""))) {
         mensajeError = "El campo Nombre Corto es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       if ((formulario.getACodldap() != null) && (!formulario.getACodldap().equals(""))) {
         datos.setACodldap(formulario.getACodldap());
         OCAPConfigApp.logger.info("aCodldap: " + datos.getACodldap());
       }
       if ((formulario.getACodldap() == null) || (formulario.getACodldap().equals(""))) {
         mensajeError = "El campo Codldap es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       datos.setDGerente(formulario.getDGerente());
       datos.setDDirector(formulario.getDDirector());
 
       datos.setACreadoPor(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
       int result = oCAPGerenciasLN.altaOCAPGerencias(datos);
       if (result == 1)
       {
         request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
         request.setAttribute("rutaVuelta", "OCAPGerencias.do?accion=irInsertar");
         sig = "exito";
       }
       else {
         request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
         sig = "error";
       }
       OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA OcapGerencias --------- ");
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
     HttpSession session = request.getSession();
     try
     {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- MODIFICACION OCAP_GERENCIAS --------- ");
       ActionErrors errores = new ActionErrors();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPGerenciasLN oCAPGerenciasLN = new OCAPGerenciasLN(jcylUsuario);
       OCAPGerenciasOT datos = new OCAPGerenciasOT();
 
       OCAPGerenciasForm formulario = (OCAPGerenciasForm)form;
 
       String cGerenciaIdS = request.getParameter("cGerenciaIdS");
       long cGerenciaId;
       if ((cGerenciaIdS != null) && (!cGerenciaIdS.equals(""))) {
         cGerenciaId = Long.parseLong(cGerenciaIdS);
         OCAPConfigApp.logger.info("cGerenciaId: " + cGerenciaId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
       datos = oCAPGerenciasLN.buscarOCAPGerencias(cGerenciaId);
 
       if (datos.getCGerenciaId() == 0L)
       {
         OCAPConfigApp.logger.info("No encontramos OCAPGerenciasOT");
         sig = "error";
         request.getSession().removeAttribute("conDatosEdicion");
         request.setAttribute("mensaje", "No se encuentra el registro");
       }
       else {
         OCAPConfigApp.logger.info("Se ha encontrado OCAPGerenciasOT");
         request.getSession().setAttribute("conDatosEdicion", "");
         request.getSession().removeAttribute("sinDatosEdicion");
         request.setAttribute("OCAPGerenciasOT", datos);
 
         formulario.setCGerencia_id(datos.getCGerenciaId());
 
         formulario.setCProvincia_id(datos.getCProvinciaId());
         if (datos.getCProvinciaId() == null) formulario.setCProvincia_id("");
 
         formulario.setCTipogerencia_id(datos.getCTipogerenciaId());
         formulario.setDNombre(datos.getDNombre());
         formulario.setDNombre_corto(datos.getDNombreCorto());
         formulario.setACodldap(datos.getACodldap());
         formulario.setDGerente(datos.getDGerente());
         formulario.setDDirector(datos.getDDirector());
 
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
       OCAPConfigApp.logger.info("---------- GRABAR OCAP_GERENCIAS --------- ");
       ActionErrors errores = new ActionErrors();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPGerenciasLN oCAPGerenciasLN = new OCAPGerenciasLN(jcylUsuario);
       OCAPGerenciasOT datos = new OCAPGerenciasOT();
 
       OCAPGerenciasForm formulario = (OCAPGerenciasForm)form;
 
       String cGerenciaIdS = request.getParameter("cGerenciaIdS");
       if ((cGerenciaIdS != null) && (!cGerenciaIdS.equals(""))) {
         long cGerenciaId = Long.parseLong(cGerenciaIdS);
         OCAPConfigApp.logger.info("cGerenciaId: " + cGerenciaId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
       OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
 
       datos.setCGerenciaId(formulario.getCGerencia_id());
       OCAPConfigApp.logger.info("cGerenciaId: " + datos.getCGerenciaId());
 
       if ((formulario.getCProvincia_id() != null) && (!formulario.getCProvincia_id().equals(""))) {
         datos.setCProvinciaId(formulario.getCProvincia_id());
         OCAPConfigApp.logger.info("cProvinciaId: " + datos.getCProvinciaId());
       }
 
       if ((formulario.getCProvincia_id() == null) || (formulario.getCProvincia_id().equals(""))) {
         mensajeError = "El campo Provincia es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       datos.setCTipogerenciaId(formulario.getCTipogerencia_id());
       OCAPConfigApp.logger.info("cTipogerenciaId: " + datos.getCTipogerenciaId());
 
       if (formulario.getCTipogerencia_id() == 0L) {
         mensajeError = "El campo Tipo de Gerencia es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       if ((formulario.getDNombre() != null) && (!formulario.getDNombre().equals(""))) {
         datos.setDNombre(formulario.getDNombre());
         OCAPConfigApp.logger.info("dNombre: " + datos.getDNombre());
       }
 
       if ((formulario.getDNombre() == null) || (formulario.getDNombre().equals(""))) {
         mensajeError = "El campo Nombre de Gerencia es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       if ((formulario.getDNombre_corto() != null) && (!formulario.getDNombre_corto().equals(""))) {
         datos.setDNombreCorto(formulario.getDNombre_corto());
         OCAPConfigApp.logger.info("dNombreCorto: " + datos.getDNombreCorto());
       }
       if ((formulario.getDNombre_corto() == null) || (formulario.getDNombre_corto().equals(""))) {
         mensajeError = "El campo Nombre Corto es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       if ((formulario.getACodldap() != null) && (!formulario.getACodldap().equals(""))) {
         datos.setACodldap(formulario.getACodldap());
         OCAPConfigApp.logger.info("dNombreCorto: " + datos.getACodldap());
       }
       if ((formulario.getACodldap() == null) || (formulario.getACodldap().equals(""))) {
         mensajeError = "El campo Codldap es obligatorio.";
         throw new Exception("Campo obligatorio no informado");
       }
 
       datos.setDGerente(formulario.getDGerente());
       datos.setDDirector(formulario.getDDirector());
 
       int result = oCAPGerenciasLN.modificacionOCAPGerencias(datos);
 
       if (result == 1)
       {
         request.setAttribute("mensaje", "El registro se ha modificado con éxito");
 
         request.setAttribute("rutaVuelta", "OCAPGerencias.do?accion=detalle&cGerenciaIdS=" + datos.getCGerenciaId());
 
         sig = "exito";
       }
       else {
         request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
         sig = "error";
       }
       OCAPConfigApp.logger.info("---------- FIN GRABAR MODIFICACION OcapGerencias --------- ");
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       sig = "error";
       request.setAttribute("mensaje", mensajeError);
     }
 
     return mapping.findForward(sig);
   }
 }

