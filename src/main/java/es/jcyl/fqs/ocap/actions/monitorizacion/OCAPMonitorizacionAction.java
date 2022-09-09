 package es.jcyl.fqs.ocap.actions.monitorizacion;
 
 import es.jcyl.fqs.ocap.actionforms.monitorizacion.OCAPMonitorizacionForm;
 import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ln.monitorizacion.OCAPMonitorizacionLN;
 import es.jcyl.fqs.ocap.ot.monitorizacion.OCAPMonitorizacionOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import org.apache.commons.beanutils.PropertyUtils;
 import org.apache.log4j.Logger;
 import org.apache.struts.action.ActionError;
 import org.apache.struts.action.ActionErrors;
 import org.apache.struts.action.ActionForm;
 import org.apache.struts.action.ActionForward;
 import org.apache.struts.action.ActionMapping;
 
 public class OCAPMonitorizacionAction extends OCAPGenericAction
 {
   public ActionForward irMonitorizarBD(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
   {
     ActionErrors errores = new ActionErrors();
     HttpSession session = request.getSession();
 
     String destinoMapeo = "irMonitorizarBD";
     try
     {
       validarAccion(mapping, form, request, response);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       errores.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
     }
 
     if (errores.size() != 0) {
       saveErrors(request, errores);
 
       return mapping.findForward("error");
     }
 
     return mapping.findForward(destinoMapeo);
   }
 
   public ActionForward monitorizarBD(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
   {
     ActionErrors errores = new ActionErrors();
     HttpSession session = request.getSession();
 
     String destinoMapeo = "monitorizarBD";
     try
     {
       validarAccion(mapping, form, request, response);
 
       OCAPMonitorizacionOT monitorizacionOT = new OCAPMonitorizacionOT();
       PropertyUtils.copyProperties(monitorizacionOT, (OCAPMonitorizacionForm)form);
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPMonitorizacionLN monitorizacionLN = new OCAPMonitorizacionLN(jcylUsuario);
 
       ArrayList listadoMonitorizacion = monitorizacionLN.monitorizarBD(monitorizacionOT);
       if ((listadoMonitorizacion != null) && (listadoMonitorizacion.size() > 0))
         request.setAttribute("listadoMonitorizacion", listadoMonitorizacion);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       errores.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.monitorizacionBD", e.getMessage()));
     }
 
     if (errores.size() != 0) {
       saveErrors(request, errores);
 
       return mapping.findForward("monitorizarBD");
     }
 
     return mapping.findForward(destinoMapeo);
   }
 }

