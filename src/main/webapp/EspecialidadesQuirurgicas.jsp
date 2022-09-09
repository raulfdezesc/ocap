<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<title>..: <%=es.jcyl.framework.JCYLConfiguracion.getValor("TITULO_TITLE")%>
	:..
</title>
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
			<jsp:include page="cabeceraItinerarios.jsp" flush="true" />
			<div class="cuadroInfo">
				<jsp:include page="consejeria.jsp" flush="true" />
			</div>
			<div class="marcoContenidoAccesible">
				<div class="menuAccesible">
					<jsp:include page="/menuHTML.jsp" />
				</div>
				<div class="contenidoAccesible">
</logic:equal>

<div class="cuadroContenedor">
	<div class="contenidoDCP1A espQuirurgicas">
		<form name="OCAPEspQuirurgicas" method="post" action="">
			<h3 class="subTituloContenido">Dossier Personal de Carrera
				Profesional: Relación de documentos</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<span>
				<p>Muy señor mío:</p>
				<p>De acuerdo a sus instrucciones adjunto las evidencias
					documentales que han de incluirse en mi evaluación de competencias
					profesionales para la obtención/reconocimiento del grado de Carrera
					Profesional solicitado.</p>
				<p>La relación de documentos es (marque las evidencias
					documentales que aporta):</p>
				<ul class="especialidades">
					<li><span>Evidencia Documental nº 1</span> <input
						class="tipo1" type="checkbox" id="evidencia1" /></li>
					<li><span>Evidencia Documental nº 2</span> <input
						class="tipo1" type="checkbox" id="evidencia2" /></li>
					<li><span>Evidencia Documental nº 3</span> <input
						class="tipo1" type="checkbox" id="evidencia3" /></li>
					<li><span>Evidencia Documental nº 4</span> <input
						class="tipo1" type="checkbox" id="evidencia4" /></li>
					<li><span>Evidencia Documental nº 5</span> <input
						class="tipo1" type="checkbox" id="evidencia5" /></li>
					<li><span>Evidencia Documental nº 6</span> <input
						class="tipo1" type="checkbox" id="evidencia6" /></li>
					<li><span>Evidencia Documental nº 7</span> <input
						class="tipo1" type="checkbox" id="evidencia7" /></li>
					<li><span>Evidencia Documental nº 8</span> <input
						class="tipo1" type="checkbox" id="evidencia8" /></li>
					<li><span>Evidencia Documental nº 9</span> <input
						class="tipo1" type="checkbox" id="evidencia9" /></li>
					<li><span>Evidencia Documental nº 10</span> <input
						class="tipo2" type="checkbox" id="evidencia10" /></li>
					<li><span>Evidencia Documental nº 11</span> <input
						class="tipo2" type="checkbox" id="evidencia11" /></li>
					<li><span>Evidencia Documental nº 12</span> <input
						class="tipo2" type="checkbox" id="evidencia12" /></li>
					<li><span>Evidencia Documental nº 13</span> <input
						class="tipo2" type="checkbox" id="evidencia13" /></li>
					<li><span>Evidencia Documental nº 14</span> <input
						class="tipo2" type="checkbox" id="evidencia14" /></li>
				</ul>

			</span>
			<fieldset>

				<legend class="tituloLegend"> Mis datos de identificación
					son: </legend>
				<div class="cuadroFieldset">


					<label for="idVApell1"> Apellido1: <input type="text"
						name="DApellido1" maxlength="60" tabindex="1" value=""
						class="recuadroM colocaApellidosEvaluadores"></label> <label
						for="idVNombre"> Nombre: <input type="text" name="DNombre"
						maxlength="30" tabindex="2" value=""
						class="recuadroM colocaNombreEvaluadores"></label> <br />
					<br /> <label for="idVApell1"> Apellido2: <input
						type="text" name="DApellido2" maxlength="60" tabindex="1" value=""
						class="recuadroM colocaApellidosEvaluadores"></label> <br />
					<br /> <label for="idVDNI"> NIF/NIE: <input type="text"
						name="CDni" maxlength="10" tabindex="3" value=""
						class="recuadroM colocaDNIEvaluadores"></label> <br />
					<br /> <label for="idVApell1"> Categoria: <input
						type="text" name="DCategoria" maxlength="60" tabindex="1" value=""
						class="recuadroM colocaApellidosEvaluadores"></label> <label
						for="idVNombre"> Centro: <input type="text" name="DCentro"
						maxlength="30" tabindex="2" value=""
						class="recuadroM colocaNombreEvaluadores"></label> <br />
					<br /> <label for="idVDNI"> Fecha:&nbsp; <input
						type="text" name="CFecha" maxlength="10" tabindex="3" value=""
						class="recuadroM colocaDNIEvaluadores"></label> <br />
					<br />



				</div>
			</fieldset>
		</form>
	</div>
	<!-- contenidoDCP1A -->
</div>
</div>
<!-- contenidoAccesible -->
</div>
<!-- marcoContenidoAccesible -->
<div id="footer">
	<jsp:include page="/bottom.jsp" flush="true" />
</div>
</div>
<!-- encuadre -->
</body>
</html>
