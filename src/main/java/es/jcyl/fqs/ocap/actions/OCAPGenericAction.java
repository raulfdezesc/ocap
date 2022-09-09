 package es.jcyl.fqs.ocap.actions;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.error.EAutorizacionException;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLUsuario;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.struts.action.ActionForm;
 import org.apache.struts.action.ActionForward;
 import org.apache.struts.action.ActionMapping;
 import org.apache.struts.actions.DispatchAction;
 
 public class OCAPGenericAction extends DispatchAction
 {
   public ActionForward validarAccion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws EAutorizacionException, Exception
   {
     try
     {
       JCYLUsuario usuario = (JCYLUsuario)request.getSession().getAttribute("JCYLUsuario");
       if (usuario == null) {
         throw new EAutorizacionException("Usuario no autorizado a acceder a la aplicación....");
       }
 
       request.setAttribute("usuario", usuario.getUsuario().getC_usr_id());
       request.setAttribute("rol", usuario.getUsuario().getRol());
       request.setAttribute("uri_acceso", request.getRequestURI());
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return mapping.findForward("exito");
   }
 
   public String obtenerConvocatoria(HttpServletRequest request, String cConvocatoriaIdForm)
     throws Exception
   {
	   
	   
     String cConvocatoriaId = null;
     JCYLUsuario usuario = (JCYLUsuario)request.getSession().getAttribute("JCYLUsuario");
     try
     {
      
       cConvocatoriaId = (String)usuario.getParametrosUsuario().get("PARAM_CONVOCATORIA_USUARIO");
       
       
       OCAPConfigApp.loggerTrazas.warn ("TRAZA PROD- obtenerConvocatoria (desde PARAM_CONVOCATORIA_USUARIO) valor cConvocatoriaId= " + cConvocatoriaId  + " para usuario con parametros: " + usuario.getParametrosUsuario().toString() +  " DNI: " +usuario.getUser().getC_usr_id());
       
       
       
       if ((cConvocatoriaId == null) || ("".equals(cConvocatoriaId)) || (Long.parseLong(cConvocatoriaId) == 0L)) {
         if ((cConvocatoriaIdForm != null) && (!"".equals(cConvocatoriaIdForm)))
           cConvocatoriaId = cConvocatoriaIdForm;
         else
           cConvocatoriaId = "0";
       }
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }     
     OCAPConfigApp.loggerTrazas.warn ("TRAZA PROD- Saliendo de obtenerConvocatoria valor cConvocatoriaId=" + cConvocatoriaId  +  " DNI: " +usuario.getUser().getC_usr_id()) ;     
     
     return cConvocatoriaId;
   }
 }