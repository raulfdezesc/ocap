package es.jcyl.fqs.ocap.actions;

import es.jcyl.cf.ejb.autenticacion.Autenticacion;
import es.jcyl.cf.ejb.autorizacion.Autorizacion;
import es.jcyl.cf.seguridad.util.EjbFactoryAutenticacion;
import es.jcyl.cf.seguridad.util.EjbFactoryAutorizacion;
import es.jcyl.cf.seguridad.util.Usuario;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLInsertarRoles;
import es.jcyl.framework.JCYLUsuario;
import es.jcyl.framework.utiles.HEXUtils;
import es.jcyl.framework.utiles.JCYLDatosCertificadoOT;
import es.jcyl.framework.utiles.JCYLLectorCertificadoDigital;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

public final class OCAPLoginCertAction extends Action
{
  private static final byte[] TRIPLEDESKEY = generaClaveDES(JCYLConfiguracion.getValorArray("TRIPLEDES"));

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String poolSegu = JCYLConfiguracion.getValor("SEGU_POOL");

    JCYLInsertarRoles.inserta(getServlet().getServletContext(), Boolean.valueOf(JCYLConfiguracion.getValor("SEGU_REFRESCO")).booleanValue(), poolSegu);

    ActionErrors errors = new ActionErrors();
    String mappingForward = "exito";

    EjbFactoryAutenticacion ejb = null;
    EjbFactoryAutorizacion ejb_aut = null;
    try
    {
      if (TRIPLEDESKEY == null)
      {
        errors.add("APP_ERROR_LOGIN", new ActionError("error.acceso.autenticacion"));
        saveErrors(request, errors);
        throw new Exception("ERROR de acceso por Certificado. Valor TRIPLEDESKEY erroneo : " + TRIPLEDESKEY);
      }
      String nif = null;
      String dni = null;
      try
      {
        JCYLLectorCertificadoDigital lectorCert = new JCYLLectorCertificadoDigital();

        JCYLDatosCertificadoOT lector = lectorCert.leerCertificado(request, TRIPLEDESKEY);
        lectorCert.validar();

        nif = lector.getNif();
        dni = nif.substring(0, nif.length() - 1);
      }
      catch (Exception e)
      {
        errors.add("APP_ERROR_LOGIN", new ActionError("error.acceso.certificados"));
        saveErrors(request, errors);
        throw new Exception("ERROR en el tratamiento del Certificado Digital :" + e.getMessage());
      }

      String nombreAplicacion = JCYLConfiguracion.getValor("SISTEMA");

      String host = JCYLConfiguracion.getValor("SEGU_HOST");

      String port = JCYLConfiguracion.getValor("SEGU_PORT");

      String componente = JCYLConfiguracion.getValor("SEGU_APP_SEGURIDAD");

      String user = JCYLConfiguracion.getValor("SEGU_USER");

      String password = JCYLConfiguracion.getValor("SEGU_PASSWORD");

      if ((nombreAplicacion == null) || (nombreAplicacion.length() == 0) || (host == null) || (host.length() == 0) || (port == null) || (port.length() == 0) || (componente == null) || (componente.length() == 0) || (user == null) || (user.length() == 0) || (password == null) || (password.length() == 0))
      {
        errors.add("APP_ERROR_LOGIN", new ActionError("error.parametros.vacios"));
        saveErrors(request, errors);
        throw new Exception("Falta definir valores el fichero de parametros , Revise las propiedades app-config");
      }

      ejb = new EjbFactoryAutenticacion(host, port, componente, user, password, poolSegu);
      if (ejb.getAutenticacion() == null) {
        errors.add("APP_ERROR_LOGIN", new ActionError("error.acceso.autenticacion"));
        saveErrors(request, errors);
        throw new Exception("Error acceso componente AUTENTICACION ubicado en " + host + " " + port + " " + componente + " " + user + " " + password);
      }
      OCAPConfigApp.logger.info("Conectado al componente AUTENTICACION ");

      String usuarioAplicacion = ejb.getAutenticacion().getUsuarioPorDNI(dni);

      Usuario usuarioSEGU = ejb.getAutenticacion().getDatosUsuario(usuarioAplicacion, nombreAplicacion);

      if (usuarioSEGU == null) {
        errors.add("APP_ERROR_LOGIN", new ActionError("error.datos.acceso.incorrectos"));
        saveErrors(request, errors);
        throw new Exception("Error Validacion Usuario=" + usuarioAplicacion + " Aplicacion=" + nombreAplicacion);
      }

      OCAPConfigApp.logger.info("Validado Correctamente Usuario=" + usuarioSEGU.getC_usr_id() + " Rol=" + usuarioSEGU.getRol());

      ejb.getAutenticacion().setFechaConexion(nombreAplicacion, usuarioSEGU.getC_usr_id(), null);

      JCYLUsuario usuario = new JCYLUsuario(usuarioSEGU);

      usuario.setIpRemota(request.getRemoteAddr());

      HttpSession session = request.getSession();

      session.setAttribute("JCYLUsuario", usuario);

      JCYLUsuario usu = new JCYLUsuario();
      usu.setUser(usuarioSEGU);

      if (mapping.getAttribute() != null) {
        if ("request".equals(mapping.getScope()))
          request.removeAttribute(mapping.getAttribute());
        else {
          session.removeAttribute(mapping.getAttribute());
        }
      }
      ejb.getAutenticacion().remove();

      ejb_aut = new EjbFactoryAutorizacion(host, port, componente, user, password, poolSegu);
      if (ejb_aut.getAutorizacion() == null) {
        errors.add("APP_ERROR_LOGIN", new ActionError("error.acceso.autenticacion"));
        saveErrors(request, errors);
        throw new Exception("Error acceso componente AUTORIZACION ubicado en " + host + " " + port + " " + componente + " " + user + " " + password);
      }
      OCAPConfigApp.logger.info("Conectado al componente AUTORIZACION ");

      HashMap hm_pu = ejb_aut.getAutorizacion().getValoresParametros(usuarioSEGU.getC_usr_id(), nombreAplicacion);
      usuario.setParametrosUsuario(hm_pu);

      ArrayList FuncionesMenu = ejb_aut.getAutorizacion().getEstructura(nombreAplicacion, usuarioSEGU.getRol());
      session.setAttribute("JCYLArrayFunciones", FuncionesMenu);

      OCAPConfigApp.logger.info("Entrando IP =" + request.getRemoteAddr() + " EQUIPO=" + request.getRemoteHost());
      OCAPConfigApp.logger.info("ACCION=" + getClass().getName() + " RESULTADO=" + mappingForward);

      ejb_aut.getAutorizacion().remove();
    }
    catch (Exception e) {
      request.getSession().removeAttribute("JCYLUsuario");
      mappingForward = "fallo";
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
    }
    finally {
      if (ejb != null) {
        ejb.cerrar();
      }
      if (ejb_aut != null) {
        ejb_aut.cerrar();
      }

    }

    return mapping.findForward(mappingForward);
  }

  private static byte[] generaClaveDES(ArrayList lista)
  {
    int numeroEntradas = lista.size();
    byte[] claveDES = new byte[numeroEntradas];
    try
    {
      for (int i = 0; i < numeroEntradas; i++)
      {
        String valor = lista.get(i).toString().substring(2);
        claveDES[i] = HEXUtils.string2Byte(valor);
      }
      return claveDES;
    }
    catch (Exception e)
    {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      return null;
    }
  }

  private static byte[] generaClaveDES_ori(String clave)
  {
    String[] entradas = clave.split(",");
    int numeroEntradas = entradas.length;
    byte[] claveDES = new byte[numeroEntradas];
    for (int i = 0; i < numeroEntradas; i++)
    {
      claveDES[i] = HEXUtils.string2Byte(entradas[i].substring(2));
    }

    return claveDES;
  }
}
