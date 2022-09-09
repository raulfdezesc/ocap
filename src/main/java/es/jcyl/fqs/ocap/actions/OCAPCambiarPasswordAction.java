 package es.jcyl.fqs.ocap.actions;
 
 import es.jcyl.cf.seguridad.util.EjbFactoryAutenticacion;
 import es.jcyl.fqs.ocap.actionforms.OCAPCambioPasswordForm;
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLConfiguracion;
 import es.jcyl.framework.JCYLUsuario;
 import java.io.IOException;
 import java.sql.SQLException;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.struts.action.Action;
 import org.apache.struts.action.ActionError;
 import org.apache.struts.action.ActionErrors;
 import org.apache.struts.action.ActionForm;
 import org.apache.struts.action.ActionForward;
 import org.apache.struts.action.ActionMapping;
 
 public class OCAPCambiarPasswordAction extends Action
 {
   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     ActionErrors errores = new ActionErrors();
 
     OCAPCambioPasswordForm formulario = (OCAPCambioPasswordForm)form;
 
     String nombreAplicacion = JCYLConfiguracion.getValor("SISTEMA");
     String host = JCYLConfiguracion.getValor("SEGU_HOST");
     String port = JCYLConfiguracion.getValor("SEGU_PORT");
     String user = JCYLConfiguracion.getValor("SEGU_USER");
     String password = JCYLConfiguracion.getValor("SEGU_PASSWORD");
     String componente = JCYLConfiguracion.getValor("SEGU_APP_SEGURIDAD");
     String poolSegu = JCYLConfiguracion.getValor("SEGU_POOL");
     try
     {
       EjbFactoryAutenticacion ejb = new EjbFactoryAutenticacion(host, port, componente, user, password, poolSegu);
 
       JCYLUsuario usuario = (JCYLUsuario)request.getSession().getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
 
       if (ejb.getAutenticacion().validar(usuario.getUsuario().getC_usr_id(), formulario.getPasswordViejo(), nombreAplicacion) != null) {
         ejb.getAutenticacion().setPassword(usuario.getUsuario().getC_usr_id(), formulario.getPasswordNuevo());
 
         request.setAttribute("confirmacion", "Contrase&ntilde;a modificada correctamente");
       }
       else {
         errores.add("passwordViejo", new ActionError("error.password.no.valido"));
         sig = "input";
       }
 
       saveErrors(request, errores);
 
       sig = "exito";
     }
     catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
     }
 
     return mapping.findForward(sig);
   }
 }