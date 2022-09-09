<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="bean.tld" prefix="bean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<title>..: Carrera Profesional :..</title>
<meta http-equiv="Content-Type"
	content="application/xhtml+xml; charset=ISO-8859-1" />
<link href="css/EstilosJCYLFW.css" rel="stylesheet" media="screen">
	<link href="css/ocap.css" rel="stylesheet" media="screen">
		<link href="css/ocap_print.css" rel="stylesheet" media="print">

			<!--[if IE 6]>
      <link rel="stylesheet" type="text/css" href="css/EstilosJCYLFW_ie6.css" media="all" title="Hoja de estilos especifica para IE 6" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_ie6.css" media="all" title="Hoja de estilos especifica para IE 6" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_print_ie6.css" media="print" title="Hoja de estilos especifica para IE 6" ></link>
   <![endif]-->
			<!--[if IE 7]>
      <link rel="stylesheet" type="text/css" href="css/EstilosJCYLFW_ie7.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_ie7.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_print_ie7.css" media="print" title="Hoja de estilos especifica para IE 7" ></link>
   <![endif]-->
			<!--[if gt IE 8]>
      <link rel="stylesheet" type="text/css" href="css/EstilosJCYLFW_ie8.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_ie8.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_print_ie8.css" media="print" title="Hoja de estilos especifica para IE 7" ></link>
   <![endif]-->

			<script type="text/javascript" src="javascript/menu.js"
				charset="windows-1252"></script>
			<script type="text/javascript">
    
    function recolocarMenu()
    {
        var anchoVentana = document.body.clientWidth;
        anchoMenu = 774;
        var postleft = (anchoVentana/2) - (anchoMenu/2);
    }
    window.onresize=recolocarMenu;
    </script>
</head>

<%
// Este Script obtiene el Rol del usuario. Se usa el el campo siguiente
java.lang.String usuarioDeSesion = es.jcyl.framework.JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO;
es.jcyl.framework.JCYLUsuario miusr= (es.jcyl.framework.JCYLUsuario)session.getAttribute(es.jcyl.framework.JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
String rol = null;
if (miusr != null) {
   rol = miusr.getUser().getRol();
}
%>

<body>
	<div class="encuadre">
		<% if (rol != null && (rol.equals(Constantes.OCAP_DIRECCION) || rol.equals(Constantes.OCAP_CTE) || rol.equals(Constantes.OCAP_CE) || rol.equals(Constantes.OCAP_EVAL) || rol.equals(Constantes.OCAP_FQS_GEST) || rol.equals(Constantes.OCAP_FQS_ADTVO))){ %>
		<jsp:include page="cabeceraItinerarios.jsp" flush="true" />
		<%}else{%>
		<jsp:include page="cabecera.jsp" flush="true" />
		<%}%>
		<div class="cuadroInfo">
			<jsp:include page="consejeria.jsp" flush="true" />
		</div>

		<logic:notPresent name="MENU_ACCESIBLE">
			<div class="cuadroContenedor">
				<h1 class="tituloH1">
					<bean:message key="inicio.mensajeError" />
				</h1>
				<h2 class="tituloH2">
					<bean:message key="inicio.error1" />
				</h2>
			</div>
		</logic:notPresent>

		<logic:present name="MENU_ACCESIBLE">
			<logic:equal name="MENU_ACCESIBLE" value="NO">
				<jsp:include page="/menuActualizado.jsp" />
				<div class="contenidoNoAccesible">
					<iframe id="marcoContenido" name="main" src="OCAPPresentacion.jsp"
						frameborder="0">
						<p>El navegador actual no soporta la etiqueta IFRAMES</p>
					</iframe>
				</div>
			</logic:equal>
			<!--Si la variable MENU_ACCESIBLE=SI, se muestra el men&uacute; accesible-->
			<logic:equal name="MENU_ACCESIBLE" value="SI">
				<div class="marcoContenidoAccesible pantallaBienvenida">
					<div class="menuAccesible">
						<jsp:include page="/menuHTML.jsp" />
						<div class="imagenLateralIzquierda"></div>
					</div>
					<div class="contenidoAccesible">
						<div class="contenedor">
							<h3>Bienvenido</h3>
							<div class="lineaBajaM"></div>
							<p>
								Bienvenido al Sistema de Informaci&oacute;n Web para la
								Gesti&oacute;n de Carrera Profesional. <br /> Si desea Cerrar su
								sesi&oacute;n de trabajo, seleccione el enlace CERRAR situado en
								la parte superior derecha de su pantalla. Para acceder a las
								funcionalidades del Sistema, seleccione una opci&oacute;n del
								men&uacute; situado a la izquierda de su pantalla.
							</p>
							<div class="lineaBajaM"></div>
						</div>
					</div>
					<!-- /Fin de contenidoAccesible -->
					<div class="limpiadora"></div>
				</div>
			</logic:equal>
		</logic:present>
	</div>

	<div id="footer">
		<html:link href="http://www.salud.jcyl.es" target="_blank">Salud Castilla y Le&oacute;n</html:link>
		<html:img page="/imagenes/puntitoM.png" alt="" />
		<html:img page="/imagenes/puntitoM.png" alt="" />
		<logic:present
			name="<%=es.jcyl.framework.JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO%>">
			<a href="PaginaInicio.do">P&aacute;gina de inicio</a>
		</logic:present>
		<html:img page="/imagenes/puntitoM.png" alt="" /><bean:message key="version" />
	</div>

</body>
</html>
