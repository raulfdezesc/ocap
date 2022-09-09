<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>

<html>
<head>
<title>..: <%=es.jcyl.framework.JCYLConfiguracion.getValor("TITULO_TITLE")%>
	:..
</title>
<meta http-equiv="Content-Type"
	content="application/xhtml+xml; charset=ISO-8859-1" />
<link href="css/EstilosJCYLFW.css" rel="stylesheet" type="text/css"
	media="all" />
<link href="css/ocap.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/ocap_print.css" rel="stylesheet" type="text/css"
	media="print" />

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

</head>
<body>

	<div class="ocultarCampo">

		<div class="encuadre">

			<jsp:include page="cabecera.jsp" flush="true" />

			<div class="cuadroContenedor">
				<div class="titulocajaformulario">Cerrar Sesi&oacute;n</div>
				<div class="cajaformulario">
					<p class="mensajeAviso">
						<bean:message key="mensaje.sesionCerrada" />
						.
					</p>
					<!-- Enlace siguiente no es accesible, se puede eliminar -->
					<div class="botonAccion colocarBotonLogout">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="inicio.jsp"> <span> Inicio </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="espaciador"></div>
					<div class="espaciador"></div>
				</div>
			</div>
			<div class="espaciador"></div>

			<!-- FIN TABLA DEL CONTENIDO -->

		</div>

		<div id="footer">
			<html:link href="http://www.salud.jcyl.es" target="_blank">Salud Castilla y Le&oacute;n </html:link>
			<html:img page="/imagenes/puntitoM.png" alt="" /><bean:message key="version" />
		</div>

	</div>
	<!-- /Fin de ocultarCampo -->

</body>
</html>
