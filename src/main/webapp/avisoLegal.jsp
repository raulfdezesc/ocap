<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>
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
			<!--[if gt IE 7]>
      <link rel="stylesheet" type="text/css" href="css/EstilosJCYLFW_ie8.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_ie8.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_print_ie7.css" media="print" title="Hoja de estilos especifica para IE 7" ></link>
   <![endif]-->

			<script language="JavaScript" type="text/javascript"
				src="javascript/menu.js"></script>
			<script language="JavaScript" type="text/javascript">

function recolocarMenu()
{
  var anchoVentana = document.body.clientWidth;
   anchoMenu = 774;
  var postleft = (anchoVentana/2) - (anchoMenu/2);

}
window.onresize=recolocarMenu;
</script>
</head>

<body>

	<div class="encuadre">
		<jsp:include page="cabecera.jsp" flush="true" />
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

				<div class="marcoContenidoAccesible">
					<div class="menuAccesible">
						<jsp:include page="/menuHTML.jsp" />
					</div>
					<div class="contenidoAccesible">

						<div class="contenido">
							<a href="javascript:window.print();"><img
								src="imagenes/imagenes_ocap/imprimir.gif" alt="Imprimir"
								class="flotaIzq" /></a>
							<h3 class="subTituloContenido"></h3>
							<div class="lineaBajaM ocultarCampo"></div>
						</div>

						<div class="cuadroContenedorConsultas">

							<div class="titulocajaformulario">Aviso Legal sobre la
								Recogida de Datos</div>
							<div class="cajaformulario">
								<p>" En el cumplimiento de lo que dispone el art&iacute;culo
									5 de la Ley Org&aacute;nica 3/2018 de 5 de Diciembre de
									Protecci&oacute;n de Datos Personales y Garant&iacute; de 
									los Derechos Digitales, la Direcci&oacute;n General de Recursos
									Humanos de la Gerencia Regional de Salud de Castilla y Le&oacute;n,
									informa de que los	datos de car&aacute;cter personal que nos
									proporcione, cuando	rellena los formularios de autoevaluaci&oacute;n
									contenidos en esta aplicaci&oacute;n, se recoger&aacute;n en un
									archivo cuyo
									titular es dicha Direcci&oacute;n General, y ser&aacute;n
									tratados para la finalidad propia de los formularios, es decir,
									para la evaluaci&oacute;n del acceso a grado de carrera
									profesional.</p>
								<p>El hecho de rellenar estos formularios, implica que el
									usuario reconoce que la informaci&oacute;n y los datos
									personales que nos indica son suyos, exactos y ciertos. Los
									usuarios tienen reconocidos y podr&aacute;n ejercitar los
									derechos de acceso, rectificaci&oacute;n, cancelaci&oacute;n y
									oposici&oacute;n al tratamiento, uso y cesi&oacute;n de sus
									datos para lo cual deber&aacute;n enviar una carta dirigida al
									Servicio de Formaci&oacute;n de la Direcci&oacute;n General de
									Recursos Humanos de la Gerencia Regional de Salud, sita en el
									Paseo Zorrilla n&deg;1, 47007 Valladolid. "</p>
								<br />
								<br />
							</div>
							<!-- /Fin de cajaFormulario -->

						</div>
						<!-- /Fin de cuadroContenedorConsultas -->
					</div>
					<!-- /Fin de contenidoAccesible -->
					<div class="limpiadora"></div>
				</div>

			</logic:equal>
		</logic:present>
	</div>

	<div id="footer">
		<html:link href="http://www.salud.jcyl.es" target="_blank">Salud Castilla y Le&oacute;n </html:link>
		<logic:present
			name="<%=es.jcyl.framework.JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO%>">
			<a href="PaginaInicio.do">P&aacute;gina de inicio</a>
		</logic:present>
	</div>


</body>
</html>
