 package es.jcyl.fqs.ocap.actions.gestionCtes;
 
 import es.jcyl.cf.seguridad.util.Usuario;
 import es.jcyl.fqs.ocap.actionforms.gestionCtes.OCAPGestionCtesForm;
 import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
 import es.jcyl.fqs.ocap.ln.componentesCtes.OCAPComponentesCtesLN;
 import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
 import es.jcyl.fqs.ocap.ln.evaluadores.OCAPEvaluadoresLN;
 import es.jcyl.fqs.ocap.ln.gerencias.OCAPGerenciasLN;
 import es.jcyl.fqs.ocap.ln.gestionCtes.OCAPGestionCtesLN;
 import es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT;
 import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
 import es.jcyl.fqs.ocap.ot.gerencias.OCAPGerenciasOT;
 import es.jcyl.fqs.ocap.ot.gestionCtes.OCAPGestionCtesOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Pagina;
 import es.jcyl.fqs.ocap.util.TrataError;
 import es.jcyl.fqs.ocap.util.Utilidades;
 import es.jcyl.framework.JCYLUsuario;
 import java.io.IOException;
 import java.text.ParsePosition;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.Date;
 import javax.servlet.ServletException;
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
 
 public class OCAPGestionCtesAction extends OCAPGenericAction
 {
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
       validarAccion(mapping, form, request, response);
 
       request.setAttribute("tipoAccion", Constantes.INSERTAR);
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       HttpSession sesion = request.getSession();
 
       OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
       ArrayList listadoGerencias = gerenciasLN.listadoOCAPGerencias();
       if (sesion.getAttribute(Constantes.COMBO_GERENCIA) != null)
         sesion.removeAttribute(Constantes.COMBO_GERENCIA);
       sesion.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);
 
       OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
       ArrayList listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();
       if (sesion.getAttribute(Constantes.COMBO_GERENCIA) != null) {
         session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: ERROR: " + e.getMessage());
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
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
     ActionMessages messages = new ActionMessages();
     ActionErrors errors = new ActionErrors();
     try
     {
       OCAPConfigApp.logger.info("Inicio");
       validarAccion(mapping, form, request, response);
 
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       messages = validarCte(form);
 
       if ((messages == null) || (messages.isEmpty())) {
         OCAPGestionCtesLN oCAPGestionCtesLN = new OCAPGestionCtesLN(jcylUsuario);
         OCAPGestionCtesOT componente = new OCAPGestionCtesOT();
 
         OCAPGestionCtesForm formulario = (OCAPGestionCtesForm)form;
         OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
 
         componente.setDNombre(formulario.getDNombre());
 
         componente.setFConstitucion(formulario.getFConstitucion());
 
         componente.setACreadoPorCte(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
         componente.setACreadoPorCac(componente.getACreadoPorCte());
 
         componente.setCGerenciaId(formulario.getCGerenciaId());
 
         long result = oCAPGestionCtesLN.altaOCAPCtes(componente);
 
         if (result > 0L)
         {
           request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
           request.setAttribute("rutaVuelta", "OCAPGestionCtes.do?accion=irInsertar");
           sig = "exito";
         }
         else {
           request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
           sig = "error";
         }
 
         OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA Cte--------- ");
       }
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(ex, "error.general")));
       sig = "error";
       request.setAttribute("mensaje", mensajeError);
     }
 
     if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
     {
       return mapping.findForward("exito");
     }if ((errors != null) && (!errors.isEmpty())) {
       saveErrors(request, errors);
 
       return mapping.findForward("fallo");
     }
     saveMessages(request, messages);
 
     request.setAttribute("tipoAccion", Constantes.INSERTAR);
 
     return mapping.findForward("irInsertar");
   }
 
   public ActionForward detalle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     ActionErrors errors = new ActionErrors();
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- DETALLE --------- ");
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPGestionCtesLN oCAPGestionCtesLN = new OCAPGestionCtesLN(jcylUsuario);
       OCAPGestionCtesOT datos = new OCAPGestionCtesOT();
 
       OCAPGestionCtesForm formulario = (OCAPGestionCtesForm)form;
 
       String cCteIdS = request.getParameter("cCteIdS");
       long cCteId;
       if ((cCteIdS != null) && (!cCteIdS.equals(""))) {
         cCteId = Long.parseLong(cCteIdS);
         OCAPConfigApp.logger.info("cCteId: " + cCteId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }datos = oCAPGestionCtesLN.buscarOCAPCte(cCteId);
 
       if (datos.getCCteId() == 0L)
       {
         OCAPConfigApp.logger.info("No encontramos OCAPGestionCtesOT");
         sig = "error";
         request.getSession().removeAttribute("conDatosDetalle");
         request.setAttribute("mensaje", "No se encuentra el registro");
       }
       else {
         formulario.setDNombre(datos.getDNombre());
         formulario.setFConstitucion(datos.getFConstitucion());
 
         if (datos.getCGerenciaId() != 0L)
         {
           OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
           OCAPGerenciasOT gerenciasOT = gerenciasLN.buscarOCAPGerencias(datos.getCGerenciaId());
           formulario.setDNombreGerencia(gerenciasOT.getDNombre());
           formulario.setCGerenciaId(datos.getCGerenciaId());
         }
 
         ArrayList activas = oCAPGestionCtesLN.buscarOCAPCteConv(cCteId);
         ArrayList histConv = null;
         histConv = new ArrayList();
         OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
 
         for (int j = 0; j < activas.size(); j++) {
           OCAPGestionCtesOT convocaOT = new OCAPGestionCtesOT();
 
           if (((OCAPGestionCtesOT)activas.get(j)).getCConvocatoriaId() != 0L) {
             OCAPConvocatoriasOT convocatoriasOT = new OCAPConvocatoriasOT();
 
             convocaOT.setDConvocNombre(convocatoriasLN.buscarOCAPConvocatorias(((OCAPGestionCtesOT)activas.get(j)).getCConvocatoriaId()).getDNombre());
           } else {
             convocaOT.setDConvocNombre("-");
           }
 
           histConv.add(convocaOT);
         }
 
         formulario.setListadoConv(histConv);
 
         OCAPConfigApp.logger.info("Se ha encontrado OCAPGestionCtes");
         request.getSession().setAttribute("conDatosDetalle", "");
         request.getSession().removeAttribute("sinDatosDetalle");
         request.getSession().setAttribute("OCAPGestionCtesOT", datos);
         sig = "verDetalle";
         request.setAttribute("tipoAccion", Constantes.VER_DETALLE);
       }
       OCAPConfigApp.logger.info("...........se sale del Action");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: ERROR: " + e.getMessage());
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward(sig);
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward irEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     ActionErrors errors = new ActionErrors();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irEditar: Inicio");
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
       Utilidades utilidades = new Utilidades();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPGestionCtesLN oCAPGestionCtesLN = new OCAPGestionCtesLN(jcylUsuario);
       OCAPGestionCtesOT datos = new OCAPGestionCtesOT();
       OCAPGestionCtesForm formulario = (OCAPGestionCtesForm)form;
 
       String cCteIdS = request.getParameter("cCteIdS");
       long cCteId;
       if ((cCteIdS != null) && (!cCteIdS.equals(""))) {
         cCteId = Long.parseLong(cCteIdS);
         OCAPConfigApp.logger.info("cCteId: " + cCteId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
 
       datos = oCAPGestionCtesLN.buscarOCAPCte(cCteId);
 
       if (datos.getCCteId() == 0L) {
         OCAPConfigApp.logger.info("No encontramos OCAPGestionCtesOT");
         sig = "error";
         request.setAttribute("mensaje", "No se encuentra el registro");
       } else {
         OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
         Collection listadoConv = convocatoriasLN.listarConvocatoriasActivas();
         Object[] listadoConvocatorias = listadoConv.toArray();
 
         session.setAttribute(Constantes.COMBO_CONVOCATORIAS, utilidades.ArrayObjectToArrayList(listadoConvocatorias));
 
         formulario.setCCte_id(datos.getCCteId());
         formulario.setDNombre(datos.getDNombre());
         formulario.setFConstitucion(datos.getFConstitucion());
 
         if (datos.getCGerenciaId() != 0L)
         {
           OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
           OCAPGerenciasOT gerenciasOT = gerenciasLN.buscarOCAPGerencias(datos.getCGerenciaId());
           formulario.setDNombreGerencia(gerenciasOT.getDNombre());
           formulario.setCGerenciaId(datos.getCGerenciaId());
         }
 
         OCAPConfigApp.logger.info("Se ha encontrado OCAPGestionCtes");
         sig = "irModificar";
 
         request.setAttribute("tipoAccion", Constantes.ACTIVAR);
       }
       OCAPConfigApp.logger.info("...........se sale del Action");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: ERROR: " + e.getMessage());
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward(sig);
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward grabar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     String mensajeError = "Se ha producido un error";
     ActionMessages messages = new ActionMessages();
     ActionErrors errors = new ActionErrors();
     try
     {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- GRABAR Cte --------- ");
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPGestionCtesLN oCAPGestionCtesLN = new OCAPGestionCtesLN(jcylUsuario);
       OCAPGestionCtesOT datos = new OCAPGestionCtesOT();
       OCAPGestionCtesForm formulario = (OCAPGestionCtesForm)form;
 
       if (formulario.getCCte_id() != 0L) {
         OCAPConfigApp.logger.info("cCteId: " + formulario.getCCte_id());
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
       datos.setCCteId(formulario.getCCte_id());
       OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
 
       if (((OCAPGestionCtesForm)form).getCConvocatoria_id() == 0L) {
         messages.add("CConvocatoriaId", new ActionMessage("errors.required", "Convocatoria"));
       }
 
       if ((messages == null) || (messages.isEmpty()))
       {
         datos.setCConvocatoriaId(formulario.getCConvocatoria_id());
         int result = 0;
 
         datos.setACreadoPorCac(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
 
         result = oCAPGestionCtesLN.activaOCAPCtes(datos);
 
         if (result > 0)
         {
           request.setAttribute("mensaje", "El registro se ha modificado con éxito");
           request.setAttribute("rutaVuelta", "OCAPGestionCtes.do?accion=detalle&cCteIdS=" + datos.getCCteId());
           sig = "exito";
         }
         else {
           request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
           sig = "error";
         }
         OCAPConfigApp.logger.info("---------- FIN GRABAR MODIFICACION Cte --------- ");
       }
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(ex, "error.general")));
     }
 
     if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
     {
       return mapping.findForward("exito");
     }if ((errors != null) && (!errors.isEmpty())) {
       saveErrors(request, errors);
 
       return mapping.findForward("fallo");
     }
     saveMessages(request, messages);
 
     request.setAttribute("tipoAccion", Constantes.ACTIVAR);
 
     return mapping.findForward("irModificar");
   }
 
   public ActionForward irBorrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errors = new ActionErrors();
     String mensajeError = "Se ha producido un error";
     HttpSession session = request.getSession();
     Collection componentes = null;
     Collection evaluadores = null;
     ArrayList convocatorias = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irBorrar: Inicio");
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPGestionCtesForm formulario = (OCAPGestionCtesForm)form;
       OCAPComponentesCtesLN oCAPComponentesCtesLN = new OCAPComponentesCtesLN(jcylUsuario);
       OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
       OCAPGestionCtesLN oCAPGestionCtesLN = new OCAPGestionCtesLN(jcylUsuario);
       OCAPComponentesfqsOT componente = new OCAPComponentesfqsOT();
 
       String cCteIdS = request.getParameter("cCteIdS");
       int cCteId;
       if ((cCteIdS != null) && (!cCteIdS.equals(""))) {
         cCteId = Integer.parseInt(cCteIdS);
         OCAPConfigApp.logger.info("cCteId: " + cCteId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
       formulario.setCCte_id(cCteId);
 
       componente.setCCteId(cCteId);
       componentes = oCAPComponentesCtesLN.listarComponentes(componente, "CTE", 1, 1);
 
       if (componentes.size() != 0) {
         request.setAttribute("componentes", Constantes.SI);
       } else {
         evaluadores = oCAPEvaluadoresLN.consultaOCAPEvaluadores(componente, "cp", 1, 1);
 
         if (evaluadores.size() != 0) {
           request.setAttribute("evaluadores", Constantes.SI);
         } else {
           convocatorias = oCAPGestionCtesLN.buscarOCAPCteConv(cCteId);
           if (convocatorias.size() != 0)
             request.setAttribute("convocatorias", Constantes.SI);
         }
       }
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " irBorrar: ERROR: " + e.getMessage());
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward("irBorrar");
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
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- CONSULTA DE CTE --------- ");
       ActionErrors errores = new ActionErrors();
 
       OCAPGestionCtesForm formulario = (OCAPGestionCtesForm)form;
 
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPGestionCtesLN oCAPGestionCtesLN = new OCAPGestionCtesLN(jcylUsuario);
 
       Collection elementos = oCAPGestionCtesLN.consultaOCAPCtes();
 
       if (elementos != null) {
         OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Evaluadores para la consulta");
         if (elementos.size() == 0)
         {
           request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
           request.removeAttribute("conDatos");
           request.getSession().removeAttribute("listados");
         } else {
           Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
           pagina.setElementos(elementos);
           request.setAttribute("listados", pagina);
         }
       }
       else {
         request.setAttribute("errorConsultando", "Error consultando en la base de datos");
       }
       OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAPEvaluadores ------- ");
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       request.setAttribute("mensaje", mensajeError);
 
       return mapping.findForward("error");
     }
 
     return mapping.findForward("irListar");
   }
 
   public ActionMessages validarCte(ActionForm form)
     throws Exception
   {
     ActionMessages messages = new ActionMessages();
     Date dFInicio = null;
     try
     {
       SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
       df.setLenient(false);
       ParsePosition pos = new ParsePosition(0);
 
       if ((((OCAPGestionCtesForm)form).getDNombre() == null) || (((OCAPGestionCtesForm)form).getDNombre().equals(""))) {
         messages.add("dNombre", new ActionMessage("errors.required", "Nombre"));
       }
 
       if ((((OCAPGestionCtesForm)form).getFConstitucion() == null) || (((OCAPGestionCtesForm)form).getFConstitucion().equals(""))) {
         messages.add("fConstitucion", new ActionMessage("errors.required", "Fecha de Constitución"));
       } else if (((OCAPGestionCtesForm)form).getFConstitucion().length() < 10) {
         messages.add("fConstitucion", new ActionMessage("error.maskmsg", "Fecha de Constitución"));
       }
       else if (!((OCAPGestionCtesForm)form).getFConstitucion().matches("\\d{2}/\\d{2}/\\d{4}")) {
         messages.add("fConstitucion", new ActionMessage("error.maskmsg", "Fecha de Constitución"));
       } else {
         dFInicio = df.parse(((OCAPGestionCtesForm)form).getFConstitucion(), pos);
         if (dFInicio == null) {
           messages.add("fConstitucion", new ActionMessage("error.maskmsg", "Fecha de Constitución"));
         }
       }
 
       if (((OCAPGestionCtesForm)form).getCGerenciaId() == 0L) {
         messages.add("cGerenciaId", new ActionMessage("errors.required", "Gerencia"));
       }
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.info(getClass().getName() + " validarEvaluador: ERROR: " + e.getMessage());
       throw e;
     }
 
     return messages;
   }
 
   public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     Logger log = null;
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- BAJA DE CTE --------- ");
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPGestionCtesLN oCAPGestionCtesLN = new OCAPGestionCtesLN(jcylUsuario);
       OCAPGestionCtesOT dato = new OCAPGestionCtesOT();
       OCAPGestionCtesForm formulario = (OCAPGestionCtesForm)form;
 
       String cCteIdS = request.getParameter("cCteIdS");
       int cCteId;
       if ((cCteIdS != null) && (!cCteIdS.equals(""))) {
         cCteId = Integer.parseInt(cCteIdS);
         OCAPConfigApp.logger.info("cCteId: " + cCteId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
         return mapping.findForward("error");
       }
       dato.setCCteId(cCteId);
       dato.setDNombre(formulario.getDNombre());
       dato.setAModificadoPorCte(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
       dato.setAModificadoPorCac(dato.getAModificadoPorCte());
 
       oCAPGestionCtesLN.bajaOCAPCtes(dato);
 
       OCAPConfigApp.logger.info("El registro se ha eliminado con éxito");
       request.setAttribute("mensaje", "El registro se ha eliminado con éxito");
       request.setAttribute("rutaVuelta", "OCAPGestionCtes.do?accion=buscar");
 
       sig = "exito";
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       sig = "error";
       request.setAttribute("mensaje", "Se ha producido un error");
     }
 
     return mapping.findForward(sig);
   }
 }

