package es.jcyl.fqs.ocap.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.jcyl.cf.seguridad.util.EjbFactoryAutorizacion;
import es.jcyl.cf.seguridad.util.Usuario;
import es.jcyl.fqs.ocap.actionforms.OCAPLoginForm;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.acceso.OCAPAccesoLN;
import es.jcyl.fqs.ocap.ln.gerencias.OCAPGerenciasLN;
import es.jcyl.fqs.ocap.ln.solicitudes.OCAPSolicitudesLN;
import es.jcyl.fqs.ocap.ot.acceso.OCAPAccesoOT;
import es.jcyl.fqs.ocap.ot.gerencias.OCAPGerenciasOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Seguridad;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLGestorIntentosFallidos;
import es.jcyl.framework.JCYLInsertarRoles;
import es.jcyl.framework.JCYLUsuario;
import sacylda.modelo.DatosBasicosAplicacion;
import sacylda.modelo.Estado;
import sacylda.modelo.Persona;
import sacylda.modelo.Respuesta;

public final class OCAPLoginAction extends Action {
	public static final int LOGIN_CORRECTO = 0;
	public static final String GERENCIA_REGIONAL_SALUD = "GRS";
	private static final String PREFIJO_OCAP = "OCAP_";

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionErrors errors = new ActionErrors();
		String mappingForward = "exito";
		String usuarioAplicacion = "";
		Usuario usuarioSEGU = new Usuario();
		//EjbFactoryAutenticacion ejb = null;
		EjbFactoryAutorizacion ejb_aut = null;

		try {

			String poolSegu = JCYLConfiguracion.getValor("SEGU_POOL");
			String nombreAplicacion = JCYLConfiguracion.getValor("SISTEMA");

			if ((nombreAplicacion == null) || (nombreAplicacion.length() == 0)) {
				errors.add("APP_ERROR_LOGIN", new ActionError("error.parametros.vacios"));
				saveErrors(request, errors);

				throw new Exception(
						"Falta definir valores el fichero de parametros , Revise las propiedades app-config");
			}

			JCYLInsertarRoles.inserta(getServlet().getServletContext(),
					Boolean.valueOf(JCYLConfiguracion.getValor("SEGU_REFRESCO")).booleanValue(), poolSegu);

			try {
				usuarioAplicacion = ((OCAPLoginForm) form).getLogin().toUpperCase();
				String passwordAplicacion = ((OCAPLoginForm) form).getPsswd();

				if (!usuarioAplicacion.equals(JCYLConfiguracion.getValor("USUARIO_PUBLICO"))) {
					if ((usuarioAplicacion == null) || (usuarioAplicacion.length() == 0)) {
						throw new Exception("Sin usuario/password, La Aplicacion requiere autenticacion");
					}

					if (JCYLGestorIntentosFallidos.cuentaBloqueada(usuarioAplicacion)) {
						errors.add("APP_ERROR_LOGIN",
								new ActionError("error.login.cuentaBloqueada", usuarioAplicacion));
						saveErrors(request, errors);
						throw new Exception(
								"Cuenta Bloqueada para el usuario " + usuarioAplicacion + " . Intentelo mas tarde");
					}
								
				 

					Estado estadoUsuario = OCAPConsultaPersonasControlador.validaUsuarioLDAP(usuarioAplicacion,
							passwordAplicacion);
					if (LOGIN_CORRECTO == estadoUsuario.getCodigo()) {
						Respuesta<DatosBasicosAplicacion> respuesta = OCAPConsultaPersonasControlador
								.busquedaBasicaDA(usuarioAplicacion, passwordAplicacion);
						usuarioSEGU = construirUsuarioFromLDAP(respuesta, usuarioAplicacion, form);
																		
						
						if (usuarioSEGU.getListaPerfiles() != null && !usuarioSEGU.getListaPerfiles().isEmpty()) {
							if (usuarioSEGU.getListaPerfiles().size() == 1) {
								if (usuarioSEGU.getListaPerfiles().get(0).startsWith(PREFIJO_OCAP)) {
									usuarioSEGU.setRol(usuarioSEGU.getListaPerfiles().get(0).trim());
								} else {
									usuarioSEGU.setRol(
											nombreAplicacion + "_" + usuarioSEGU.getListaPerfiles().get(0).trim());
								}
								OCAPConfigApp.logger.info(getClass().getName() + "El usuario:" + usuarioAplicacion
										+ " tiene solo un perfilde OCAP:" + nombreAplicacion + "_"
										+ usuarioSEGU.getListaPerfiles().get(0).trim());
							} else {
								OCAPConfigApp.logger.info(getClass().getName() + "El usuario:" + usuarioAplicacion
										+ " tiene un total de perfiles admitidos en OCAP :"
										+ usuarioSEGU.getListaPerfiles().size());
								request.setAttribute("login", usuarioAplicacion);
								request.setAttribute("password", passwordAplicacion);
								request.setAttribute("listaPerfiles",
										usuarioSEGU.getListaPerfiles().toArray(new String[0]));
								ActionForward localActionForward = mapping.findForward("elegirPerfil");

								return localActionForward;
							}
						} else if (usuarioSEGU.getRol() == null   || usuarioSEGU.getRol().equals("")) {
							OCAPConfigApp.logger.error(getClass().getName() + "El usuario:" + usuarioAplicacion
									+ " no tiene perfiles admitidos en OCAP.");
							throw new Exception(Constantes.SIN_PERMISOS);
						}
					} else {

						int resExisteUsuario = OCAPConsultaPersonasControlador.existeUsuario(usuarioAplicacion);

						if (resExisteUsuario == 0) {

							// Vemos si está en la tabla OCAP_ACCESO

							OCAPAccesoLN ocapAccesoLN = new OCAPAccesoLN();
							OCAPAccesoOT accesoOt = ocapAccesoLN.buscarAcceso(usuarioAplicacion);

							if (accesoOt == null) {

								OCAPSolicitudesLN ocapSolicitudesLN = new OCAPSolicitudesLN(null);
								accesoOt = ocapSolicitudesLN.usuarioTieneSolicitudAceptada(usuarioAplicacion);

								if (accesoOt != null) {

									usuarioSEGU = new Usuario();

									usuarioSEGU.setDNom(accesoOt.getANombre());
									usuarioSEGU.setDApell(accesoOt.getAApellidos());
									usuarioSEGU.setD_telefono(accesoOt.getNTelefono());

									usuarioSEGU.setRol(Constantes.OCAP_USUARIOS);
									ArrayList<String> perfiles = new ArrayList<String>();
									perfiles.add(Constantes.OCAP_USUARIOS);
									usuarioSEGU.setListaPerfiles(perfiles);

									usuarioSEGU.setC_usr_id(JCYLConfiguracion.getValor("USUARIO_PUBLICO"));
									usuarioSEGU.setAPassword(JCYLConfiguracion.getValor("PASSWORD_PUBLICO"));
									JCYLUsuario usuario = new JCYLUsuario(usuarioSEGU);

									HttpSession session = request.getSession(false);
									if (session != null) {
										session.invalidate();
									}
									session = request.getSession(true);
									session.setAttribute("JCYLUsuario", usuario);
									session.setAttribute("DNI", accesoOt.getCDni());

									ActionForward localActionForward = mapping.findForward("irRegistrarAcceso");
									return localActionForward;
								} else {
									errors.add("APP_ERROR_LOGIN", new ActionError("error.datos.acceso.noexiste"));
									saveErrors(request, errors);
									mappingForward = "fallo";
									return mapping.findForward(mappingForward);
								}

							} else {

								if ((passwordAplicacion).equals(Seguridad.desencriptar(accesoOt.getAContrasenia()))) {
									// contraseñas coinciden
									OCAPConfigApp.logger.info("Contraseña correcta");

									usuarioSEGU = new Usuario();
									usuarioSEGU.setRol(Constantes.OCAP_USUARIOS);
									usuarioSEGU.setC_usr_id(accesoOt.getCDni());

									OCAPSolicitudesLN ocapSolicitudesLN = new OCAPSolicitudesLN(null);
									accesoOt = ocapSolicitudesLN.usuarioTieneSolicitud(usuarioAplicacion);
									usuarioSEGU.setDNom(accesoOt.getANombre());
									usuarioSEGU.setDApell(accesoOt.getAApellidos());
									usuarioSEGU.setD_telefono(accesoOt.getNTelefono());

									JCYLUsuario usuario = new JCYLUsuario(usuarioSEGU);

									HttpSession session = request.getSession(false);
									if (session != null) {
										session.invalidate();
									}
									session = request.getSession(true);
									session.setAttribute("JCYLUsuario", usuario);					
									
									mappingForward = "elegirConvocatoria";

								} else {
									request.setAttribute("enviarMail", accesoOt.getACorreoRecupera());
									request.setAttribute("login", accesoOt.getCDni());
									usuarioSEGU = new Usuario();
									usuarioSEGU.setRol(Constantes.OCAP_USUARIOS);
									usuarioSEGU.setC_usr_id(JCYLConfiguracion.getValor("USUARIO_PUBLICO"));
									usuarioSEGU.setAPassword(JCYLConfiguracion.getValor("PASSWORD_PUBLICO"));
									JCYLUsuario usuario = new JCYLUsuario(usuarioSEGU);
									HttpSession session = request.getSession(false);
									if (session != null) {
										session.invalidate();
									}
									session = request.getSession(true);
									session.setAttribute("JCYLUsuario", usuario);

									mappingForward = "enviarMail";
									return mapping.findForward(mappingForward);
								}
							}
						} else

						if (resExisteUsuario == 1)

						{
							OCAPConfigApp.logger
									.info(getClass().getName() + "Las credenciales introducidas no son correctas");
							throw new Exception(Constantes.ACCESO_INCORRECTO);
						} else {
							OCAPConfigApp.logger.info(getClass().getName()
									+ "Error al consultar al directorio activo. Revisar configuración");
							throw new Exception(Constantes.DA_NO_DISPONIBLE);
						}
					}
				} else {
					usuarioSEGU = new Usuario();
					usuarioSEGU.setRol(Constantes.OCAP_PUNTO_GESTION_PERIFE);
					usuarioSEGU.setC_usr_id(usuarioAplicacion);
					JCYLUsuario usuario = new JCYLUsuario(usuarioSEGU);

					HttpSession session = request.getSession(false);
					if (session != null) {
						session.invalidate();
					}
					session = request.getSession(true);
					session.setAttribute("JCYLUsuario", usuario);
					mappingForward = "publico";
				}
			} catch (Exception e) {
				OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
				request.getSession().removeAttribute("JCYLUsuario");
				mappingForward = "fallo";
				
				
				switch (e.getMessage()) {  
				 case Constantes.SIN_PERMISOS:  
					 errors.add("APP_ERROR_LOGIN", new ActionError("error.acceso.sinPermiso"));
				   break;  
				case Constantes.DA_NO_DISPONIBLE:  
					errors.add("APP_ERROR_LOGIN", new ActionError("error.acceso.errorDA"));
				   break;  
				 default:  
					 errors.add("APP_ERROR_LOGIN", new ActionError("error.datos.acceso.incorrectos"));
				 }  
					
				
				saveErrors(request, errors);

				mappingForward = "fallo";
				return mapping.findForward(mappingForward);
			}
			JCYLUsuario usuario = new JCYLUsuario(usuarioSEGU);
			usuario.setIpRemota(request.getRemoteAddr());
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
			session = request.getSession(true);

			OCAPConfigApp.logger.info("USUARIO: " + usuario.getUser().getC_usr_id());
			OCAPConfigApp.logger.info("SESSION NUEVA: " + session.isNew());

			if (session != null) {
				session.removeAttribute("JSessionID");
				session.setAttribute("JSessionID", "");
			}
			if (mapping.getAttribute() != null) {
				if ("request".equals(mapping.getScope()))
					request.removeAttribute(mapping.getAttribute());
				else {
					session.removeAttribute(mapping.getAttribute());
				}
			}

			ejb_aut = new EjbFactoryAutorizacion();
			if (ejb_aut.getAutorizacion() == null) {
				errors.add("APP_ERROR_LOGIN", new ActionError("error.acceso.autorizacion"));
				saveErrors(request, errors);
			}
			OCAPConfigApp.logger.info("Conectado al componente AUTORIZACION ");

			HashMap hm_pu = new HashMap();
			OCAPConfigApp.logger.info(
					"Conectado al componente Consultar datos de la gerencia en OCAP del usuario:" + usuarioAplicacion);
			OCAPGerenciasLN gerenciaLN = new OCAPGerenciasLN(usuario);
			OCAPGerenciasOT gerenciaOT = gerenciaLN.buscaOCAPGerenciaLDAP(usuarioSEGU.getD_gerencia());

			if (gerenciaOT == null || gerenciaOT.getCProvinciaId() == null) {
				throw new Exception("Gerencia de usuario incorrecta: " + usuarioSEGU.getD_gerencia());
			}

		 

			hm_pu.put("PARAM_GERENCIA", gerenciaOT.getCGerenciaId() + "");
			hm_pu.put("PARAM_TIPO_GERENCIA", gerenciaOT.getCTipogerenciaId() + "");
			hm_pu.put("PARAM_PROV", gerenciaOT.getCProvinciaId());
			usuario.setParametrosUsuario(hm_pu);
			session.setAttribute("JCYLUsuario", usuario);
			
			OCAPConfigApp.logger.info("Conectado al componente recuperando funciones del menú ");
			ArrayList FuncionesMenu = new ArrayList();
			FuncionesMenu = ejb_aut.getAutorizacion().getEstructura(nombreAplicacion, usuarioSEGU.getRol());

			if ((FuncionesMenu == null) || (FuncionesMenu.size() == 0)) {
				throw new Exception("Perfil de usuario incorrecto: " + usuarioSEGU.getRol());
			}
			session.setAttribute("JCYLArrayFunciones", FuncionesMenu);
			ejb_aut.getAutorizacion().remove();

			if (Constantes.OCAP_PRUEBA_ITINERARIO.equals(usuarioSEGU.getRol())) {
				session.setAttribute(Constantes.OCAP_PRUEBA_ITINERARIO, Constantes.SI);
				mappingForward = "irInsertarUsuario";
			} else if ((Constantes.OCAP_USUARIOS.equals(usuarioSEGU.getRol()))
					|| (Constantes.OCAP_USUARIO_FASE_III.equals(usuarioSEGU.getRol()))) {
				mappingForward = "elegirConvocatoria";
			}

		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			request.getSession().removeAttribute("JCYLUsuario");
			mappingForward = "fallo";
			if (Constantes.SIN_PERMISOS.equals(e.getMessage()))
				errors.add("APP_ERROR_LOGIN", new ActionError("error.acceso.sinPermiso"));
			else
				errors.add("APP_ERROR_LOGIN", new ActionError("error.acceso.autenticacion"));
			saveErrors(request, errors);
		} finally {
			if (ejb_aut != null) {
				ejb_aut.cerrar();
			}
		}
		return mapping.findForward(mappingForward);
	}

	private Usuario construirUsuarioFromLDAP(Respuesta<DatosBasicosAplicacion> respuesta, String usuarioAplicacion,
			ActionForm form) {
		Usuario usuario = new Usuario();
		usuario.setC_usr_id(usuarioAplicacion);
		for (DatosBasicosAplicacion datosBasicos : respuesta.getDatos()) {
			usuario = convertPersonaToUser(datosBasicos.getDatosPersonales(), datosBasicos.getAtributosAplicacion(),
					usuarioAplicacion, form);
		}
		return usuario;
	}

	private Usuario convertPersonaToUser(Persona datosPersonales, Map<String, List<String>> atributosAplicacion,
			String usuarioAplicacion, ActionForm form) {
		String nombreAplicacion = JCYLConfiguracion.getValor("SISTEMA");
		ArrayList<String> perfiles = new ArrayList<String>();
		Usuario user = new Usuario();
		user.setC_usr_id(usuarioAplicacion);
		user.setDNom(datosPersonales.getNombre());
		user.setDApell(datosPersonales.getApellidos());
		user.setD_correo(datosPersonales.getEmail());
		user.setD_movil(datosPersonales.getMovil());
		user.setD_telefono(datosPersonales.getTelefono());

		if (null != datosPersonales.getGerencias() && !datosPersonales.getGerencias().isEmpty()) {
			user.setD_gerencia(datosPersonales.getGerencias().get(0));
		}
		user.setCDni("");
		user.setCNif("");
		user.setDatoSeguridad("");
		user.setAPassword("");

		if ((((OCAPLoginForm) form).getPerfil() != null) && (!"".equals(((OCAPLoginForm) form).getPerfil()))) {
			user.setRol(((OCAPLoginForm) form).getPerfil());
		} else {
			for (Map.Entry<String, List<String>> entry : atributosAplicacion.entrySet()) {
				for (String atributo : entry.getValue()) {
					if (atributo.contains("GEST") || (atributo.contains("ADTVO"))) {
						perfiles.add(nombreAplicacion + "_" + "FQS" + "_" + atributo.trim());
					} else if (atributo.contains("ITINERARIO")) {
						perfiles.add(nombreAplicacion + "_" + "PRUEBA" + "_" + atributo.trim());
					}

					else {
						perfiles.add(nombreAplicacion + "_" + atributo.trim());
					}
				}
			}
			Collections.sort(perfiles);
			user.setListaPerfiles(perfiles);
		}
		return user;
	}

}