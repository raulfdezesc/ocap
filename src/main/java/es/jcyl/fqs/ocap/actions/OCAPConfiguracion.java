package es.jcyl.fqs.ocap.actions;

import java.io.IOException;
import java.util.Properties;

import javax.naming.ConfigurationException;

import org.apache.log4j.Logger;

import es.jcyl.fqs.ocap.util.PropiedadesConfigurables;


/**
 * Clase de utilidad que contiene funciones cuya finalidad es gestionar el
 * contenido de los ficheros de configuración
 * 
 * @author Lorena Vélez Fernández
 */
public class OCAPConfiguracion {
	protected static final Logger LOGGER = Logger.getLogger(OCAPConfiguracion.class);

	private OCAPConfiguracion() {
	}

	/**
	 * Operación para cargar un fichero de configuración dentro de un objeto de
	 * tipo <code>Properties</code>
	 * 
	 * @param propiedadSistema
	 *            nombre de la propiedad del sistema que contendrá la ruta del
	 *            fichero.
	 * @param urlFicheroDefecto
	 *            ruta por defecto en caso de que la propiedad no está definida.
	 * @return <code>Properties</code> con el contenido de los parámetros de
	 *         configuración.
	 * @throws IOException 
	 */
	public static Properties cargarPropiedades() throws ConfigurationException, IOException {

		return PropiedadesConfigurables.getProperties();
	}

	
}
