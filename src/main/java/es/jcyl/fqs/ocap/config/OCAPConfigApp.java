package es.jcyl.fqs.ocap.config;

import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.framework.JCYLConfigApp;
import es.jcyl.framework.JCYLConfiguracion;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;

public class OCAPConfigApp
  implements JCYLConfigApp
{
  public static Logger logger;
  public static Logger loggerBD;
  public static Logger loggerXML;
  public static Logger loggerDA;
  
  public static Logger loggerTrazas;

  public void inicializar(ServletContext application)
    throws Exception
  {
    logger = JCYLConfiguracion.getLogger("Aplicacion");
    logger.debug(getClass().getName() + " inicializar() ");

    loggerBD = JCYLConfiguracion.getLogger(JCYLConfiguracion.getValor("LOG_BD"));
    loggerBD.debug(getClass().getName() + " inicializar() ");

    loggerXML = JCYLConfiguracion.getLogger(JCYLConfiguracion.getValor("LOG_XML"));

    String menu = JCYLConfiguracion.getValor("MENU_ACCESIBLE");
    if ((menu != null) && (menu.equals(Constantes.SI_TEXTO)))
      application.setAttribute("MENU_ACCESIBLE", Constantes.SI_TEXTO);
    else
      application.setAttribute("MENU_ACCESIBLE", Constantes.NO_TEXTO);
    
    loggerDA = JCYLConfiguracion.getLogger("DirectorioActivo");
    
    loggerTrazas = JCYLConfiguracion.getLogger("loggerTrazas");
    
     
  }

  public void finalizar()
    throws Exception
  {
    logger.debug(getClass().getName() + "  finalizar() ");
  }
}

