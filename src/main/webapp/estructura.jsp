<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>
<%@ taglib uri="tiles.tld" prefix="tiles"%>
<%@ taglib uri="logic.tld" prefix="logic"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=11"> 
<title>..: <%=es.jcyl.framework.JCYLConfiguracion.getValor("TITULO_TITLE")%>
	:..
</title>
<meta http-equiv="Content-Type"
	content="application/xhtml+xml; charset=ISO-8859-1" />
<link href="css/EstilosJCYLFW.css" rel="stylesheet" media="screen" />
<link href="css/ocap.css" rel="stylesheet" media="all" />
<link href="css/ocap_print.css" rel="stylesheet" media="print" />
<link href="css/ocap_faseIII.css" rel="stylesheet" media="all" />
<!--[if IE 6]>
      <link rel="stylesheet" type="text/css" href="css/EstilosJCYLFW_ie6.css" media="all" title="Hoja de estilos especifica para IE 6" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_ie6.css" media="all" title="Hoja de estilos especifica para IE 6" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_print_ie6.css" media="print" title="Hoja de estilos especifica para IE 6" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_faseIII_ie6.css" media="all" title="Hoja de estilos especifica para IE 6" ></link>
   <![endif]-->
<!--[if IE 7]>
      <link rel="stylesheet" type="text/css" href="css/EstilosJCYLFW_ie7.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_ie7.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_print_ie7.css" media="print" title="Hoja de estilos especifica para IE 7" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_faseIII_ie7.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
   <![endif]-->
<!--[if gt IE 8]>
      <link rel="stylesheet" type="text/css" href="css/EstilosJCYLFW_ie8.css" media="all" title="Hoja de estilos especifica para IE 8" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_ie8.css" media="all" title="Hoja de estilos especifica para IE 8" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_print_ie8.css" media="print" title="Hoja de estilos especifica para IE 8" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_faseIII_ie8.css" media="all" title="Hoja de estilos especifica para IE 8" ></link>
   <![endif]-->
</head>
<body onload="mostrarAlert();">
<script>
function mostrarAlert(){

				<logic:equal name="insercionExcel" value="true">
				setTimeout(function(){       alert('<%=request.getAttribute("mensajeExcel")%>');}, 200);
				      
					</logic:equal>

}
</script>

	<div class="encuadre">

		<!-- ********************** Cabecera ********************** -->
		<tiles:insert attribute="cabecera" />
		<!-- ********************** Fin Cabecera ********************** -->

		<div class="cuadroInfo">
			<!-- ********************** Consejeria-Apartado ********************** -->
			<tiles:insert attribute="consejApartado" />
			<!-- ********************** Fin Consejeria-Apartado ********************** -->
		</div>
		<!-- cuadroInfo -->

		<div class="marcoContenidoAccesible">
			<div class="menuAccesible">
				<tiles:insert attribute="menu" />
				<div class="imagenLateralIzquierda"></div>
			</div>
			<!-- menuAccesible -->
			<div class="contenidoAccesible">
				<div class="cuadroContenedor">
					<!-- ********************** Ruta Navegacion ********************** -->
					<tiles:insert attribute="rutaNavegacion" />
					<!-- ********************** Fin Ruta Navegacion ********************** -->
					<!-- ********************** Contenido ********************** -->
					<tiles:insert attribute="contenido" />
					<!-- ********************** fin Contenido ********************** -->
				</div>
				<!-- cuadroContenedor -->
			</div>
			<!-- contenidoAccesible -->
		</div>
		<!-- marcoContenidoAccesible -->

		<!-- ********************** Pie ********************** -->
		<div id="footer">
			<tiles:insert attribute="pie" />			
		</div>
		<!-- footer -->
		<!-- ********************** Fin Pie ********************** -->
	</div>
	<!-- encuadre -->
</body>
</html>
