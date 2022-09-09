<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">

<head>
<title>..: Carrera Profesional :..</title>
<meta http-equiv="Content-Type"
	content="application/xhtml+xml; charset=ISO-8859-1" />
<link href="css/EstilosJCYLFW.css" rel="stylesheet" media="screen" />
<link href="css/ocap.css" rel="stylesheet" media="screen" />
<link href="css/ocap_print.css" rel="stylesheet" media="print" />

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

<logic:equal name="MENU_ACCESIBLE" value="NO">
	<body class="fondoBlanco">
</logic:equal>

<logic:equal name="MENU_ACCESIBLE" value="SI">
	<body>
		<div class="encuadre">
			<jsp:include page="cabecera.jsp" flush="true" />
			<div class="cuadroInfo">
				<jsp:include page="consejeria.jsp" flush="true" />
			</div>
			<div class="marcoContenidoAccesible">
				<div class="menuAccesible">
					<jsp:include page="/menuHTML.jsp" />
				</div>
				<div class="contenidoAccesible">
</logic:equal>