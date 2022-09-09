 package es.jcyl.fqs.ocap.actions;
 
 import es.jcyl.framework.JCYLInsertarRoles;
 import java.io.IOException;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.struts.action.Action;
 import org.apache.struts.action.ActionForm;
 import org.apache.struts.action.ActionForward;
 import org.apache.struts.action.ActionMapping;
 import org.apache.struts.action.ActionServlet;
 
 public class OCAPReloadSeguridad extends Action
 {
   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     JCYLInsertarRoles.inserta(getServlet().getServletContext(), false);
 
     request.setAttribute("mensaje", "Seguridad recargada correctamente");
 
     return mapping.findForward("mensaje");
   }
 }

