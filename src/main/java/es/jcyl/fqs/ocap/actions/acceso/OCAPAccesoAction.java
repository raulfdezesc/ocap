package es.jcyl.fqs.ocap.actions.acceso;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.jcyl.fqs.ocap.actionforms.acceso.OCAPAccesoForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.acceso.OCAPAccesoLN;
import es.jcyl.fqs.ocap.ot.acceso.OCAPAccesoOT;
import es.jcyl.fqs.ocap.util.Seguridad;
import es.jcyl.fqs.ocap.util.UtilCorreo;
import es.jcyl.fqs.ocap.util.Utilidades;

public final class OCAPAccesoAction extends OCAPGenericAction {

	public static final int LOGIN_CORRECTO = 0;

	public ActionForward irInsertarAcceso(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String mappingForward = "irInsertar";

		return mapping.findForward(mappingForward);

	}

	public ActionForward guardarDatosAcceso(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String mappingForward = "exito";
		try {
			String passwordEncriptado = Seguridad.encriptar(((OCAPAccesoForm) form).getAPassword());

			OCAPAccesoOT entrada = new OCAPAccesoOT();
			entrada.setCDni(((OCAPAccesoForm) form).getADni().toUpperCase());
			entrada.setAContrasenia(passwordEncriptado);
			entrada.setACorreoRecupera(((OCAPAccesoForm) form).getAEmailRecuerdo());
			OCAPAccesoLN ocapAccesoLN = new OCAPAccesoLN();
			int resultado = ocapAccesoLN.insertarAcceso(entrada);

			if (resultado < 0) {
				ActionErrors errors = new ActionErrors();
				errors.add("APP_ERROR_CREAR_ACCESO", new ActionError("error.grabar.acceso"));
				saveErrors(request, errors);
				mappingForward = "fallo";
			} else {
				request.setAttribute("rutaVuelta", "inicio.jsp");
				request.setAttribute("textoAdicional",
						" Al pulsar en el botón Aceptar, volverá a la página de inicio de sesión. Podrá usar las credenciales introducidas para acceder");

				HttpSession session = request.getSession(false);
				session = request.getSession(true);
				session.setAttribute("JCYLUsuario", null);
			}

		} catch (Exception ex) {
			mappingForward = "fallo";
		}

		return mapping.findForward(mappingForward);

	}

	public ActionForward enviarMailRecuperacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String dni = "";
		Map<String, Object> params = request.getParameterMap();
		Object obj = params.get("login");
		if (obj != null) {
			String[] values = (String[]) obj;
			dni = values.length > 0 ? values[0] : null;
		}

		try {
			if (dni != null) {

				OCAPAccesoLN ocapAccesoLN = new OCAPAccesoLN();
				OCAPAccesoOT resultado = ocapAccesoLN.buscarAcceso(dni);
				StringBuilder contenido = new StringBuilder("");
				String ulrOrigen = this.getURL(request);
				
				if (resultado!=null)
				{
			
				contenido.append("<html>").append("<head>").append("</head>").append("<body>")
						.append("<table bgcolor=\"#fffff\" width=\"100%\">")					
						.append("<tr><td><h3><font color=\"#010101\" ><b>Recordatorio de contrase&ntilde;a para OCAP</b></font></h3></td></tr>")
						.append("</table>").append("<br>").append("<table bgcolor=\"#CED8F6\" width=\"80%\">")
						.append("<tr>").append("Estimado usuario ").append(resultado.getACorreoRecupera())
						.append("<td>").append("La contrase&ntilde;a utilizada para acceder a OCAP es <b> ")
						.append("<font color=\"#ff6600\">").append(  StringEscapeUtils.escapeHtml(Seguridad.desencriptar(resultado.getAContrasenia())))
						.append("</font></b> ").append("</td>").append("</tr>").append("<tr><td><br/></td></tr>")
						.append("<tr><td>Acceda a <b>OCAP</b>,<a href=\"").append(ulrOrigen)
						.append("\">pulsando aqu&iacute;</a></td></tr>").append("</table><br/>")					
						.append("</body>").append("</html>");

				UtilCorreo.enviar(resultado.getACorreoRecupera(), "OCAP: Recordatorio de contraseña", contenido.toString());

				request.setAttribute("rutaVuelta", "inicio.jsp");
				request.setAttribute("textoAdicional",
						" Al pulsar en el botón Aceptar, volverá a la página de inicio de sesión. Revise su correo para consultar la contraseña");

				return mapping.findForward("exito");
				} else
					{
					OCAPConfigApp.logger.error(getClass().getName() +"Error al enviar Mail de Recuperación de contraseña. Sin datos de dirección de correo o DNI no existe" );
					
					request.setAttribute("rutaVuelta", "login.jsp");
					request.setAttribute("textoAdicional",	"El usuario introducido no existe o no tiene registrada una dirección de correo");
					
					
					HttpSession session = request.getSession(false);
					session = request.getSession(true);
					session.setAttribute("JCYLUsuario", null);
					return mapping.findForward("error");
					}
					
			} else {
				OCAPConfigApp.logger.error(getClass().getName() +"Error al enviar Mail de Recuperación de contraseña DNI es nulo" );
				
				request.setAttribute("rutaVuelta", "login.jsp");
				request.setAttribute("textoAdicional",	" Se ha producido un error al intentar enviar el correo con la contraseña. El DNI es nulo");
				
				
				HttpSession session = request.getSession(false);
				session = request.getSession(true);
				session.setAttribute("JCYLUsuario", null);
				return mapping.findForward("error");
			}
		}

		catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			
			request.setAttribute("rutaVuelta", "login.jsp");
			request.setAttribute("textoAdicional",	" Se ha producido un error al intentar enviar el correo con la contraseña." + ex.getMessage());
			
			HttpSession session = request.getSession(false);
			session = request.getSession(true);
			session.setAttribute("JCYLUsuario", null);
			return mapping.findForward("error");
		}

	}

	/**
	 * Funcion para obtener la URL raiz de la aplicación
	 * 
	 * @param request
	 * @return
	 */
	private String getURL(HttpServletRequest request) {

		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";

		return base;

	}

}