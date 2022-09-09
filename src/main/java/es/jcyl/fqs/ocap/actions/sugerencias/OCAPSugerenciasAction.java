 package es.jcyl.fqs.ocap.actions.sugerencias;
 
 import es.jcyl.cf.seguridad.util.Usuario;
 import es.jcyl.fqs.ocap.actionforms.sugerencias.OCAPSugerenciasForm;
 import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ln.sugerencias.OCAPSugerenciasLN;
 import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
 import es.jcyl.fqs.ocap.ot.sugerencias.OCAPSugerenciasOT;
 import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
 import es.jcyl.fqs.ocap.util.TrataError;
 import es.jcyl.framework.JCYLUsuario;
 import javax.mail.SendFailedException;
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
 import org.apache.struts.action.ActionServlet;
 
 public class OCAPSugerenciasAction extends OCAPGenericAction
 {
   public ActionForward irSugerencias(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     ActionErrors errors = new ActionErrors();
     String mensajeError = "Se ha producido un error";
     HttpSession session = request.getSession();
     OCAPUsuariosLN usuariosLN = null;
     OCAPUsuariosOT usuarioOT = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irSugerencias: Inicio");
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       String usuId = jcylUsuario.getUser().getC_usr_id();
       usuariosLN = new OCAPUsuariosLN(jcylUsuario);
       usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);
 
       if (usuarioOT == null)
       {
         return mapping.findForward("irNoExisteSolicitud");
       }
       ((OCAPSugerenciasForm)form).setDCorreoElectronico(usuarioOT.getDCorreoelectronico());
       OCAPConfigApp.logger.info(getClass().getName() + " irSugerencias: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " irSugerencias: ERROR: " + e.getMessage());
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward("irSugerencias");
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward enviarSugerencia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     HttpSession session = request.getSession();
     ActionErrors errors = new ActionErrors();
     String pathBase = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " enviarSugerencia: Inicio");
       validarAccion(mapping, form, request, response);
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPSugerenciasLN sugerenciasLN = new OCAPSugerenciasLN(jcylUsuario);
       OCAPSugerenciasOT sugerenciasOT = new OCAPSugerenciasOT();
 
       sugerenciasOT.setDCorreoElectronico(((OCAPSugerenciasForm)form).getDCorreoElectronico());
       sugerenciasOT.setDTelefono(((OCAPSugerenciasForm)form).getDTelefono());
       sugerenciasOT.setDSugerencia(((OCAPSugerenciasForm)form).getDSugerencia());
 
       pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");
 
       sugerenciasLN.enviarSugerencia(response, sugerenciasOT);
 
       OCAPConfigApp.logger.info(getClass().getName() + " enviarSugerencia: Fin");
 
       request.setAttribute("rutaVuelta", "OCAPSugerencias.do?accion=inicio");
     }
     catch (SendFailedException e) {
       OCAPConfigApp.logger.info(getClass().getName() + " enviarSugerencia: ERROR envaindo email: " + e.getMessage());
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.envioEmail")));
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " enviarSugerencia: ERROR: " + e.getMessage());
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward("exito");
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward inicio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     return mapping.findForward("inicio");
   }
 }

