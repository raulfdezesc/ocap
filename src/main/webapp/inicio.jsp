<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>
<%@ taglib uri="html.tld" prefix="html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<title>..: Carrera Profesional :..</title>
<meta http-equiv="Content-Type"
	content="application/xhtml+xml; charset=ISO-8859-1" />
<link href="css/EstilosJCYLFW.css" rel="stylesheet" media="screen">
	<link href="css/ocap.css" rel="stylesheet" media="screen">

		<!--[if IE 6]>
      <link rel="stylesheet" type="text/css" href="css/EstilosJCYLFW_ie6.css" media="all" title="Hoja de estilos especifica para IE 6" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_ie6.css" media="all" title="Hoja de estilos especifica para IE 6" ></link>
   <![endif]-->
		<!--[if IE 7]>
      <link rel="stylesheet" type="text/css" href="css/EstilosJCYLFW_ie7.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_ie7.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
   <![endif]-->
		<!--[if gt IE 7]>
      <link rel="stylesheet" type="text/css" href="css/EstilosJCYLFW_ie8.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_ie8.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
   <![endif]-->

		<script language="javascript" type="text/javascript">
    //Completa los datos de usuario y password
    function submite()  {
      document.forms[0].submit();
    }
  </script>
</head>

<body onload="submite();">
	<% 
   java.lang.String tipoLogin = request.getParameter("acceso");
   if (org.apache.commons.validator.GenericValidator.isBlankOrNull(tipoLogin)) 
            { tipoLogin = es.jcyl.framework.JCYLConfiguracion.getValor("TIPO_LOGIN_DEFECTO");/* Establecer cual es el valor por defecto */}
   if(tipoLogin.equals("password") || tipoLogin.equals("certificado") || tipoLogin.equals("passcert")) 
            { %>
	<jsp:forward page="login.jsp" />
	<% }
   else if(tipoLogin.equals("autcert")) 
            {      
             /* Creamos una nueva sesion (sino nos puede saltar un aviso de seguridad) */
             request.getSession(true).invalidate();
             String sessionID = request.getSession().getId();
             String urlAutentificacionCertificado = es.jcyl.framework.JCYLConfiguracion.getValor("URL_AUTENTIFICACION_CERTIFICADO"); 
               
             urlAutentificacionCertificado = urlAutentificacionCertificado + "?ap=" +
             es.jcyl.framework.JCYLConfiguracion.getValor("SISTEMA") + "&sesion="+sessionID;               
             response.sendRedirect(urlAutentificacionCertificado);  %>
	} else if(tipoLogin.equals("publico")) { /* El evento onload de la
	página hará el login publico */ } else { %>
	<jsp:forward page="login.jsp" />
	<%
   }
%>

	<div class="encuadre">
		<div class="marcoContenidoAccesible">
			<jsp:include page="cabecera.jsp" flush="true" />
			<h2>Entrada en la aplicaci&oacute;n de forma an&oacute;nima</h2>
			<form method="post" action="Login.do">
				<input type="hidden" name="login"
					value="<%= es.jcyl.framework.JCYLConfiguracion.getValor("USUARIO_PUBLICO") %>" />
				<input type="hidden" name="psswd"
					value="<%= es.jcyl.framework.JCYLConfiguracion.getValor("PASSWORD_PUBLICO") %>" />
				<input type="submit" value="Aceptar" />
			</form>
		</div>
	</div>

	<div id="footer">
		<html:link href="http://www.salud.jcyl.es" target="_blank">Salud Castilla y Le&oacute;n </html:link>
		<html:img page="/imagenes/puntitoM.png" alt="" /><bean:message key="version" />
	</div>

</body>
</html>
