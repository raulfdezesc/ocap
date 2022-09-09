package es.jcyl.framework;

import es.jcyl.cf.ejb.autorizacion.Autorizacion;
import es.jcyl.cf.seguridad.util.EjbFactoryAutorizacion;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;

public class JCYLInsertarRoles
{
  public static void inserta(ServletContext application, boolean inteligente)
  {
    inserta(application, inteligente, null);
  }

  public static void inserta(ServletContext application, boolean inteligente, String poolSegu)
  {
    if ((inteligente) && (application.getAttribute("htFunciones") != null))
      return;
    JCYLConfiguracion.fwlogger.info("Refrescando los actions de seguridad para todos los Roles...");
    String nombreAplicacion = JCYLConfiguracion.getValor("SISTEMA");
    String host = JCYLConfiguracion.getValor("SEGU_HOST");
    String port = JCYLConfiguracion.getValor("SEGU_PORT");
    String componente = JCYLConfiguracion.getValor("SEGU_APP_SEGURIDAD");
    String user = JCYLConfiguracion.getValor("SEGU_USER");
    String password = JCYLConfiguracion.getValor("SEGU_PASSWORD");

    EjbFactoryAutorizacion ejb_aut = new EjbFactoryAutorizacion();
    if (ejb_aut.getAutorizacion() == null)
    {
      String txtError = "Error acceso componente AUTORIZACION ubicado en " + host + " " + port + " " + componente + " " + user + " " + password;
      JCYLConfiguracion.fwlogger.error(txtError);
    }
    else {
      String txtError;
      try {
        Hashtable htMenuCompleto = obtenerMenus(ejb_aut, nombreAplicacion);
        application.setAttribute("htFunciones", htMenuCompleto);
        JCYLConfiguracion.fwlogger.info("Actualizado en contexto Aplicacion =htFunciones");
        ejb_aut.getAutorizacion().remove();
      }
      catch (Exception ex)
      {
	  txtError = "Error acceso componente AUTORIZACION ubicado en " + host + " " + port + " " + componente + " " + user + " " + password;
        JCYLConfiguracion.fwlogger.error(txtError);
      }
      finally
      {
        try
        {
          if (ejb_aut != null)
            ejb_aut.cerrar();
        }
        catch (NamingException localNamingException1)
        {
          txtError = "Error acceso componente AUTORIZACION liberando contexto";
          JCYLConfiguracion.fwlogger.error(txtError);
        }
      }
    }
  }

  public static Hashtable obtenerMenus(EjbFactoryAutorizacion ejb_aut, String nombreAplicacion) throws Exception
  {
    Hashtable htMenuCompleto = new Hashtable();
    try
    {
      Hashtable htGrupos = ejb_aut.getAutorizacion().getGrupos(nombreAplicacion);
      String grupo;
      Hashtable ht_fm;
      for (Enumeration e = htGrupos.keys(); e.hasMoreElements(); htMenuCompleto.put(grupo, ht_fm))
      {
        grupo = "" + e.nextElement();
        ht_fm = ejb_aut.getAutorizacion().getFuncionesTodas(nombreAplicacion, grupo);
      }

    }
    catch (Exception e)
    {
      JCYLConfiguracion.fwlogger.error(e.getMessage());
    }

    return htMenuCompleto;
  }
}
