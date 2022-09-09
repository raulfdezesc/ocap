<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<title>..: <%=es.jcyl.framework.JCYLConfiguracion.getValor("TITULO_TITLE")%>
	:..
</title>
<meta http-equiv="Content-Type"
	content="application/xhtml+xml; charset=ISO-8859-1" />
<link href="css/EstilosJCYLFW.css" rel="stylesheet" media="screen">
	<link href="css/ocap.css" rel="stylesheet" media="screen">
		<!--[if IE 6]>
         <link rel="stylesheet" type="text/css" href="css/ocap_ie6.css" media="all" title="Hoja de estilos especifica para IE 6" ></link>
      <![endif]-->
		<!--[if IE 7]>
         <link rel="stylesheet" type="text/css" href="css/ocap_ie7.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <![endif]-->
		<!--[if gt IE 7]>
         <link rel="stylesheet" type="text/css" href="css/ocap_ie7.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
         <link rel="stylesheet" type="text/css" href="css/publico_ie8.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <![endif]-->
		<link href="css/ocap_print.css" rel="stylesheet" media="print">
			<link href="css/publico.css" rel="stylesheet" media="screen">
</head>
<body>

	<div class="ocultarCampo">
		<div class="encuadre">
			<jsp:include page="cabecera.jsp" flush="true" />
			<div class="cuadroContenedor">
				<div class="titulocajaformulario">Mensajes</div>
				<div class="cajaformulario">
					<div class="espaciador"></div>
					<html:messages id="men" message="true">
						<p class="mensajeAviso">
							<bean:write name="men" ignore="true" />
						</p>
					</html:messages>
					<div class="espaciadorPeq"></div>
					<div class="limpiadora"></div>
					<div align="center" style="width: 52%;">
						<div class="botoneraCentrada">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="<html:rewrite page="/" />"> <span><bean:message
											key="button.volver" /></span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</div>
					<div class="limpiadora"></div>
					<div class="espaciador"></div>
					<div class="espaciador"></div>
				</div>
			</div>
			<div class="espaciador"></div>
			<!-- FIN TABLA DEL CONTENIDO -->
		</div>
		<div id="footer">
			<html:link href="http://www.salud.jcyl.es" target="_blank">Salud Castilla y Le&oacute;n </html:link>
		</div>
	</div>
	<!-- /Fin de ocultarCampo -->
</body>
</html>
