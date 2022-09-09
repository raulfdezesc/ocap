 package config;
 
 import java.io.InputStream;
 import java.io.PrintStream;
 import java.util.HashMap;
 import java.util.Properties;
 
 public class Propiedades
 {
   private static final String CONFIGURATION_FILE = "config" + System.getProperty("file.separator") + "WSConfig.properties";
   private static HashMap propiedades;
 
   static
   {
     try
     {
       Class almacenPropiedadesClass = Propiedades.class;
       ClassLoader classLoader = almacenPropiedadesClass.getClassLoader();
 
       InputStream inputStream = classLoader.getResourceAsStream(CONFIGURATION_FILE);
 
       Properties propiedadesTemporales = new Properties();
       propiedadesTemporales.load(inputStream);
       inputStream.close();
 
       propiedades = new HashMap(propiedadesTemporales);
 
       System.out.println(propiedades);
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
 
   public static String getPropiedad(String nombre)
     throws Exception
   {
     String valor = (String)propiedades.get(nombre);
 
     if (valor == null) {
       throw new Exception("Falta el parametro " + nombre);
     }
 
     return valor;
   }
 }

