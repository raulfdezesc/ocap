/**
 * 
 */
package es.jcyl.fqs.ocap.actions;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import sacylda.interfaz.biblioteca.ConsultaPersonas;
import sacylda.interfaz.biblioteca.ConsultaPersonasImpl;
import sacylda.modelo.DatosBasicosAplicacion;
import sacylda.modelo.Estado;
import sacylda.modelo.Respuesta;
import sacylda.recursos.Atributos;
import sacylda.recursos.Codigos;

/**
 * Clase que implementa la logica de negocio de las operaciones del servicio web
 * <code>ConsultaPersonas</code> del directorio corporativo.
 * 
 * @author Lorena Velez Fernandez
 *
 */
public class OCAPConsultaPersonasControlador {
	 

	private static ConsultaPersonas consulta = new ConsultaPersonasImpl();

	/**
	 * Constructor
	 */
	private OCAPConsultaPersonasControlador() {
	}

	/**
	 * Obtiene los datos básicos de un usuario a través del DA
	 * 
	 * @param usuario
	 * @param clave
	 * @return
	 */
	public static Respuesta<DatosBasicosAplicacion> busquedaBasicaDA(String usuario, String clave) {
		OCAPConfigApp.loggerDA.info("Inicio del método busquedaBasicaDA() que obtiene la información básica del usuario " + usuario);
		Properties configuracion = OCAPDirectorio.getProps();
		if (configuracion!=null && configuracion.getProperty(Constantes.LOG_PROPS).equals(Constantes.SI_ESP))	OCAPConfigApp.loggerDA.info("[PROPS]:" + configuracion.toString());
		consulta = new ConsultaPersonasImpl();
		Respuesta<DatosBasicosAplicacion> respuesta = consulta.buscaBasicaAplicacion(usuario, clave, configuracion);
		OCAPConfigApp.loggerDA.info("Fin del método busquedaBasicaDA()");
		return respuesta;
	}

	public static Estado validaUsuarioLDAP(String usuario, String clave) {
		OCAPConfigApp.loggerDA.info("Inicio del método validaUsuarioLDAP() que valida la información del usuario " + usuario);
		ConsultaPersonas consulta = new ConsultaPersonasImpl();
		Properties configuracion = OCAPDirectorio.getProps();
		
		if (configuracion!=null && configuracion.getProperty(Constantes.LOG_PROPS).equals(Constantes.SI_ESP))	OCAPConfigApp.loggerDA.info("[PROPS]:" + configuracion.toString());
		
		Estado respuesta = null;
		if (Utilidades.notNullAndNotEmpty(clave)) {
			respuesta = consulta.validaUsuario(usuario, clave, configuracion);
			
			if (respuesta.getCodigo()!=0  )
			{
				String textoLog = "Usuario de conexión: [" + usuario + "] Resultado: " + respuesta.getDescripcion();
				
		      OCAPConfigApp.loggerDA.error (textoLog);
		
			}
			
			
		} else {
			respuesta = new Estado();
			respuesta.setCodigo(Codigos.ERROR_CONECTAR_DIRECTORIO);
			respuesta.setDescripcion(Codigos.TEXTO_ERROR_USUARIO_INVALIDO);

		}

		OCAPConfigApp.loggerDA.info("Fin del método validaUsuarioLDAP()");
		return respuesta;
	}

	public static Respuesta<Map<String, List<String>>> buscaAtributosPropios(String usuarioAplicacion,
			String passwordAplicacion, String[] atributos) {
		OCAPConfigApp.loggerDA.info("Inicio del método buscaAtributosPropios() que obtiene la información por atributos del usuario "
				+ usuarioAplicacion);
		Properties configuracion = OCAPDirectorio.getProps();
		if (configuracion!=null && configuracion.getProperty(Constantes.LOG_PROPS).equals(Constantes.SI_ESP))	OCAPConfigApp.loggerDA.info("[PROPS]:" + configuracion.toString());
		consulta = new ConsultaPersonasImpl();
		Respuesta<Map<String, List<String>>> respuesta = consulta.buscaAtributosPropios(usuarioAplicacion,
				passwordAplicacion, configuracion);
		OCAPConfigApp.loggerDA.info("Fin del método buscaAtributosPropios()");
		return respuesta;
	}
	
	/**
	 * Funcion para indicar si el usuario existe o no en D.A.
	 * @param usuarioAplicacion Código de usuario (NIF) por el que se busca
	 * @return Valor entero: -1 (Error en la consulta al D.A),  0 (Usuario no existe)  1 (Usuario Existe)
	 */
	public static int existeUsuario(String usuarioAplicacion) {

		OCAPConfigApp.loggerDA.info("Inicio del método existeUsuario() que nos permite validar si  existe en D.A. el usuario: "
				+ usuarioAplicacion);
		Properties configuracion = OCAPDirectorio.getProps();
		if (configuracion!=null && configuracion.getProperty(Constantes.LOG_PROPS).equals(Constantes.SI_ESP))	OCAPConfigApp.loggerDA.info("[PROPS]:" + configuracion.toString());
		String[] atributos = { Atributos.NOMBRE_COMPLETO };
		consulta = new ConsultaPersonasImpl();
		int resultado = 0;

		Respuesta<Map<String, List<String>>> respuesta = consulta.buscaAtributosPorUid(usuarioAplicacion, configuracion,
				atributos);
		
		if (respuesta.getDatos() == null)
		{
			
			if (respuesta.getEstado().getCodigo()== sacylda.recursos.Codigos.ERROR_CONECTAR_DIRECTORIO)
			{
				resultado= -1;
			}
			
			String textoLog = "Usuario de conexión: [" + configuracion.getProperty(Constantes.PROP_DA_USUARIO) + "] Resultado: " + respuesta.getEstado().getDescripcion();			
	        OCAPConfigApp.loggerDA.error (textoLog);
	        
	        
	
		} else	if (respuesta.getDatos() != null
				&& (respuesta.getEstado().getCodigo() == 0 || respuesta.getEstado().getCodigo() == 111)) {
			for (Map<String, List<String>> map : respuesta.getDatos()) {

				for (Entry<String, List<String>> entry : map.entrySet()) {
					String key = entry.getKey();
					if (key.equals(Atributos.NOMBRE_COMPLETO)) {
						List<String> value = entry.getValue();
						try {
							String temp = value.get(0);
							resultado = 1;
							break;
						} catch (Exception e) {
							OCAPConfigApp.loggerDA.error("Error al obtener Nombre completo: ", e);
						}
					}
				}
				if (resultado==1)
					break;
			}

		}
		else
		{
			String textoLog = "Usuario de conexión: [" + configuracion.getProperty(Constantes.PROP_DA_USUARIO) + "] Resultado: " + respuesta.getEstado().getDescripcion();
			
		      OCAPConfigApp.loggerDA.info (textoLog);
		}

		return resultado;
	}

}
