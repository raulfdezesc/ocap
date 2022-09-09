/**
 * 
 */
package es.jcyl.fqs.ocap.actions;

import java.io.IOException;
import java.util.Properties;
import javax.naming.ConfigurationException;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.util.Utilidades;

/**
 * Clase que implementa la logica de negocio de la comunicacion con el
 * directorio corporativo.
 *
 * @author Lorena Velez Fernandez
 *
 */
public class OCAPDirectorio {

	/**
	 * Constructor
	 */
	private OCAPDirectorio() {
	}

	
	private static final Properties PROPS;

 
	static {
		Properties parametrosConfiguracion = new Properties();
		try {

			parametrosConfiguracion = OCAPConfiguracion.cargarPropiedades();
		} catch (ConfigurationException | IOException ex) {
			// Error al cargar el fichero
			
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			 
		}
		PROPS = parametrosConfiguracion;
	}

	
	public static Properties getProps() {
		return PROPS;
	}
}
