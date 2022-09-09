package es.jcyl.fqs.ocap.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import es.jcyl.framework.JCYLConfiguracion;

/**
 * Obtiene las propiedades del fichero de configuración externo al war
 * 
 * @author 71145686Q
 *
 */
public class PropiedadesConfigurables {

	/**
	 * Obtiene la propiedad del nombre recibido que debe situarse en la ruta indicada en la propiedad
	 * PROPERTY_FILE del app-config.properties que sí es interno al war
	 * 
	 * @param propertyname Nombre de la propiedad externa al war a la que queremos acceder
	 * @return Valor de la propiedad externa al war a la que queremos acceder
	 * @throws IOException
	 */
	public static final String getProperty(String propertyname) throws IOException{
	    Properties mainProperties = new Properties();
	    FileInputStream file = new FileInputStream(JCYLConfiguracion.getValor("PROPERTY_FILE"));
	    mainProperties.load(file);
	    file.close();
	    return mainProperties.getProperty(propertyname);		
	}
	
	public static final Properties getProperties() throws IOException{
	    Properties mainProperties = new Properties();
	    FileInputStream file = new FileInputStream(JCYLConfiguracion.getValor("PROPERTY_FILE"));
	    mainProperties.load(file);
	    return mainProperties;		
	}
	
}
