package es.jcyl.framework;

import es.jcyl.framework.configuracion.GestorConfiguracion;
import es.jcyl.framework.configuracion.repository.ConfigurationFileElement;
import es.jcyl.framework.configuracion.repository.ConfigurationRepository;
import es.jcyl.framework.configuracion.repository.ContextElement;
import es.jcyl.framework.utiles.JCYLEnvironment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationConverter;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

public class JCYLConfiguracion
  implements JCYLGlobals
{
  private static ServletConfig servletConfig;
  private static String pathBase;
  static Logger fwlogger;
  private static Properties propiedades;
  static JCYLConfigApp appConfig;

  private static void initializeComponente()
  {
    fwlogger.debug("inicializando componente... ");
    try
    {
      fwlogger.info("Cargando ruta del repositorio de configuraciones de la aplicación de gestión");

      String CONFIGURATION_REPOSITORY_PATH = pathBase + JCYLEnvironment.getEnvEntry("configurationRepository");
      fwlogger.info("Ruta del repositorio de configuraciones ---> " + CONFIGURATION_REPOSITORY_PATH);

      fwlogger.info("Instanciando gestor de configuraciones");

      GestorConfiguracion.initialize(CONFIGURATION_REPOSITORY_PATH);
    }
    catch (Exception e)
    {
      if ((e instanceof FileNotFoundException)) fwlogger.error("Se ha producido un error instanciando el repositorio de configuraciones -> El archivo del repositorio no ha sido encontrado");
      else if ((e instanceof IOException)) fwlogger.error("Se ha producido un error instanciando el repositorio de configuraciones -> Error de entrada / salida"); else
        fwlogger.error("Se ha producido un error instanciando el repositorio de configuraciones -> Excepción: \n" + e);
    }
  }

  private static void initializeComponente(boolean bLogs)
  {
    if (bLogs)
      fwlogger.debug("inicializando componente... ");
    try
    {
      if (bLogs) {
        fwlogger.info("Cargando ruta del repositorio de configuraciones de la aplicación de gestión");
      }
      String CONFIGURATION_REPOSITORY_PATH = pathBase + JCYLEnvironment.getEnvEntry("configurationRepository");
      if (bLogs)
        fwlogger.info("Ruta del repositorio de configuraciones ---> " + CONFIGURATION_REPOSITORY_PATH);
      if (bLogs) {
        fwlogger.info("Instanciando gestor de configuraciones");
      }
      GestorConfiguracion.initialize(CONFIGURATION_REPOSITORY_PATH);
    }
    catch (Exception e) {
      if ((e instanceof FileNotFoundException)) {
        if (bLogs)
          fwlogger.error("Se ha producido un error instanciando el repositorio de configuraciones -> El archivo del repositorio no ha sido encontrado");
      } else if ((e instanceof IOException)) {
        if (bLogs)
          fwlogger.error("Se ha producido un error instanciando el repositorio de configuraciones -> Error de entrada / salida");
      }
      else if (bLogs)
        fwlogger.error("Se ha producido un error instanciando el repositorio de configuraciones -> Excepción: \n" + e);
    }
  }

  
  public static String getString(String propiedad)
  {
    String valor = null;
    try
    {
      if (!GestorConfiguracion.isInitialized()) {
        initializeComponente();
      }
      Configuration configuration = GestorConfiguracion.getConfiguration("default");

      if (configuration != null) {
        valor = configuration.getString(propiedad);
      }

      configuration = null;
    }
    catch (Exception e) {
      fwlogger.info(e);
    }

    return valor;
  }

  
  public static Integer getInteger(String propiedad)
  {
    String valor = null;
  Integer valortmp;
    try
    {
      valor = getString(propiedad);

      if (valor != null)
        try {
          valortmp = new Integer(valor);
        }
        catch (NumberFormatException e)
        {
          fwlogger.error("La propiedad accedida no tiene un valor numerico");
        }
    }
    catch (Exception e)
    {
      fwlogger.info(e);
    }

    return Integer.valueOf(valor);
  }

  public static String getValor(String propiedad)
  {
    Object valor = null;
    Configuration configuration = null;
    try
    {
      if (!GestorConfiguracion.isInitialized()) {
        initializeComponente();
      }
      configuration = GestorConfiguracion.getConfiguration("default");

      if (configuration != null) {
        valor = configuration.getProperty(propiedad);
      }
      if ((valor instanceof String)) {
        String str1 = valor.toString();

        return str1;
      }
      if ((valor instanceof ArrayList))
      {
        Iterator it = ((Collection)valor).iterator();
        String cadena = "";
        while (it.hasNext())
        {
          cadena = cadena + it.next().toString() + ",";
        }

        cadena = cadena.substring(0, cadena.length() - 1);

        String str2 = cadena;

        return str2;
      }
    }
    catch (Exception e)
    {
      fwlogger.error(e);
    } finally {
      configuration = null;
    }

    return valor.toString();
  }

  public static String getValor(String propiedad, boolean bLogs)
  {
    Object valor = null;
    Configuration configuration = null;
    try
    {
      if (!GestorConfiguracion.isInitialized()) {
        initializeComponente(bLogs);
      }
      configuration = GestorConfiguracion.getConfiguration("default");

      if (configuration != null) {
        valor = configuration.getProperty(propiedad);
      }
      if ((valor instanceof String)) {
        String str1 = valor.toString();

        return str1;
      }
      if ((valor instanceof ArrayList))
      {
        Iterator it = ((Collection)valor).iterator();
        String cadena = "";
        while (it.hasNext())
        {
          cadena = cadena + it.next().toString() + ",";
        }

        cadena = cadena.substring(0, cadena.length() - 1);

        String str2 = cadena;

        return str2;
      }
    }
    catch (Exception e)
    {
      if (bLogs)
        fwlogger.error(e);
    } finally {
      configuration = null;
    }

    return valor.toString();
  }

  public static Logger getLogger(String nombreLog)
  {
    return Logger.getLogger(nombreLog);
  }

  
  public static final ServletConfig getServletConfig()
  {
    return servletConfig;
  }

  public static void inicializar(Servlet servlet)
    throws Exception
  {
    servletConfig = servlet.getServletConfig();

    if (servletConfig == null)
    {
      throw new Exception("ERROR: al arrancar la aplicacion no existe servlet de inicialización");
    }

    ServletContext application = servletConfig.getServletContext();
    pathBase = application.getRealPath("");

    if (!pathBase.endsWith(JCYLGlobals.SEPARADOR)) {
      pathBase += JCYLGlobals.SEPARADOR;
    }

    String pathToLogFiles = pathBase + "WEB-INF" + JCYLGlobals.SEPARADOR + "logs" + JCYLGlobals.SEPARADOR;
    System.setProperty("web-inf.directory", pathBase + "WEB-INF");

    String rutaLogs = getValor("PATH_LOGS", false);
    if ((rutaLogs != null) && (!"".equals(rutaLogs)))
      System.setProperty("log.directory", rutaLogs);
    else {
      System.setProperty("log.directory", pathToLogFiles);
    }

    String Fichero = pathBase + JCYLGlobals.FICHERO_CONFIGURACION_LOG_FW;
    File file = new File(Fichero);
    if (!file.exists())
    {
      throw new Exception("ERROR: No existe fichero de definicion del log interno " + Fichero);
    }

    PropertyConfigurator.configure(Fichero);
    fwlogger = Logger.getLogger("Configuration");

    fwlogger.info("DEFINIDO en contexto aplicacion web-inf.directory=" + pathBase + "WEB-INF");
    fwlogger.info("DEFINIDO en contexto aplicacion log.directory=" + pathToLogFiles);
    try
    {
      if (!GestorConfiguracion.isInitialized()) {
        initializeComponente();
      }
      Configuration configuration = GestorConfiguracion.getConfiguration("default");
      int valorRefresco = 60;
      if (configuration != null) {
        try
        {
          valorRefresco = configuration.getInt("TIEMPO_REFRESCO_LOGS_SEGUNDOS");
        }
        catch (Exception e)
        {
          valorRefresco = 60;
        }
      }
      iniciarSistemaLogs("log", "ficheroLog4j", valorRefresco);

      initConfigEspecifica(application);
    }
    catch (Exception e)
    {
      throw e;
    }
  }

  
  private static void initConfigEspecifica(ServletContext application)
  {
    String configAppClass = getValor("ClaseConfiguracion");
    fwlogger.info("Se procede a EJECUTAR el codigo [" + configAppClass + "]");
    if (configAppClass != null) {
      try
      {
        appConfig = (JCYLConfigApp)Class.forName(configAppClass).newInstance();
      }
      catch (Exception e)
      {
        fwlogger.error(e.toString());
        fwlogger.error("No se ha encontrado la clase " + configAppClass);
      }
    }
    if (appConfig != null)
      try
      {
        appConfig.inicializar(application);
        fwlogger.info("Inicializada la configuracion específica de aplicacion");
      }
      catch (Exception e)
      {
        fwlogger.error("Se ha producido un error al iniciar la aplicacion : appConfig.inicializar()");
        fwlogger.error(e.toString());
      }
  }

  
  public static void leerPropiedades()
    throws Exception
  {
    try
    {
      if (!GestorConfiguracion.isInitialized()) {
        initializeComponente();
      }
      Configuration configuration = GestorConfiguracion.getConfiguration("default");
      propiedades = ConfigurationConverter.getProperties(configuration);
    }
    catch (Exception e) {
      fwlogger.info(e);
    }
  }

  
  public static Properties getPropiedades()
  {
    try
    {
      if (!GestorConfiguracion.isInitialized()) {
        initializeComponente();
      }
      Configuration configuration = GestorConfiguracion.getConfiguration("default");

      return ConfigurationConverter.getProperties(configuration);
    }
    catch (Exception e) {
      fwlogger.info(e);
    }

    return new Properties();
  }

  static void finalizar()
    throws Exception
  {
    if (appConfig != null)
      appConfig.finalizar();
  }

  
  public static int getValorEntero(String clave, int defecto)
  {
    int valor = defecto;
    try
    {
      valor = getInteger(clave).intValue();
    }
    catch (Exception e) {
      fwlogger.info(e);
    }

    return valor;
  }

  public static ArrayList getValorArray(String clave)
  {
    Object valor = null;
    Configuration configuration = null;
    try
    {
      if (!GestorConfiguracion.isInitialized()) {
        initializeComponente();
      }
      configuration = GestorConfiguracion.getConfiguration("default");

      if (configuration != null) {
        valor = configuration.getProperty(clave);
        if ((valor instanceof ArrayList)) {
          ArrayList localArrayList = (ArrayList)valor;

          return localArrayList;
        }
      }
    }
    catch (Exception e)
    {
      fwlogger.error(e);
    } finally {
      configuration = null;
    }

    return null;
  }

  
  public static void setValor(String clave, String valor)
  {
    if (!GestorConfiguracion.isInitialized()) {
      initializeComponente();
    }
    Configuration configuration = GestorConfiguracion.getConfiguration("default");

    configuration.setProperty(clave, valor);
  }

  
  public static String getPathBase()
  {
    return pathBase;
  }

  
  public static String getPathToWebInf()
  {
    if (pathBase == null)
    {
      return null;
    }

    return pathBase + "WEB-INF" + JCYLGlobals.SEPARADOR;
  }

  
  private static void iniciarSistemaLogs(String idContexto, String idFichero, int recargaCadaSgs)
    throws Exception
  {
    fwlogger.info("INICIANDO Logger de configuration :Contexto=" + idContexto + " idFichero=" + idFichero);
    try
    {
      ContextElement contextElement = GestorConfiguracion.configurationRepository.getContextElement(idContexto);
      if (contextElement != null) {
        ArrayList lista = contextElement.getConfigurationFileElements();

        Iterator iterator = contextElement.getConfigurationFileElements().iterator();
        String pathToLogConfig = null;
        while (iterator.hasNext())
        {
          ConfigurationFileElement configurationFileElement = (ConfigurationFileElement)iterator.next();
          if (configurationFileElement.getId().equals(idFichero))
          {
            pathToLogConfig = configurationFileElement.getLocation();
            break;
          }
        }

        long refresco = recargaCadaSgs * 1000;

        DOMConfigurator.configureAndWatch(pathToLogConfig, refresco);
        fwlogger.info("INICIALIZADO segun configuracion extraida en -> [" + pathToLogConfig + "]");
      }
      else
      {
        fwlogger.error("ERROR: No se pueden iniciar los Logs definidos en :Contexto=" + idContexto + " idFichero=" + idFichero);
      }
    }
    catch (Exception e)
    {
      fwlogger.error("ERROR: No puedo encontrar la ruta de logs en el contexto " + idContexto);
      throw e;
    }
  }
}

