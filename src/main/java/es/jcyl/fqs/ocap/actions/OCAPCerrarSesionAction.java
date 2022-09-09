 package es.jcyl.fqs.ocap.actions;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import java.io.IOException;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.struts.action.Action;
 import org.apache.struts.action.ActionForm;
 import org.apache.struts.action.ActionForward;
 import org.apache.struts.action.ActionMapping;
 
 public class OCAPCerrarSesionAction extends Action
 {
   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     OCAPConfigApp.logger.debug("Cerrando sesion : " + request.getSession().getId());
     
      request.getSession().setAttribute("JCYLUsuario", null);
      request.getSession().invalidate();
      return mapping.findForward("exito");
   }
 }

