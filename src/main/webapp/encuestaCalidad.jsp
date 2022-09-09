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
			<!-- CABECERA Y MENÚ -->
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

<!-- CONTENIDO -->
<div class="cuadroContenedor">
	<div class="contenidoDCP1A espQuirurgicas">
		<form name="OCAPEncuestaCalidad" method="post" action="">
			<h3 class="TituloContenido">Encuesta de calidad</h3>
			<h3 class="subTituloContenido">Proceso de evaluacion de carrera
				profesional</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<span>
				<p>
					La encuesta, que a continuaci&oacute;n le proponemos, pretende
					conocer su opini&oacute;n y recoger su percepci&oacute;n acerca de
					algunos de los aspectos m&aacute;s relevantes del procedimiento
					t&eacute;cnico de evaluaci&oacute;n de profesionales. El citado
					procedimiento constituye el elemento central de la
					evaluaci&oacute;n del &aacute;rea asistencial para el
					reconocimiento de grado de Carrera Profesional de los trabajadores
					de centros sanitarios del sistema sanitario p&uacute;blico de la
					Comunidad de Castilla y Le&oacute;n.<br /> Gracias a su
					colaboraci&oacute;n, podremos mejorar el m&eacute;todo de
					evaluaci&oacute;n de profesionales, con el fin de conseguir un
					sistema m&aacute;s objetivo y transparente. Asimismo, queremos
					mejorar la accesibilidad de la aplicaci&oacute;n inform&aacute;tica
					que lo soporta, para facilitar su manejo todo lo posible. <br />
					Las escalas de valoraci&oacute;n son (seg&uacute;n las preguntas)
					dos:<br />
					<ul>
						<li>Una escala de Likert (cualitativa de 5 opciones) que
							comprende desde "Completamente de acuerdo" , a la izquierda,
							hasta "En completo desacuerdo", a la derecha.</li>
						<li>Una escala cuantitativa que va desde el m&aacute;ximo
							valor "5", o favorable, a la izquierda, hasta el valor
							m&iacute;nimo, o desfavorable, "1", a la derecha.</li>
					</ul>
				</p>
				<p>Agradecemos de antemano su colaboraci&oacute;n y sinceridad,
					que nos permitir&aacute; seguir mejorando.</p> <!--
               <ul class="especialidades">
                  <li><span>Evidencia Documental nº 1</span> <input class="tipo1" type="checkbox" id="evidencia1"/></li>
                   <li><span>Evidencia Documental nº 2</span> <input class="tipo1" type="checkbox" id="evidencia2"/></li>
                   <li><span>Evidencia Documental nº 3</span> <input class="tipo1" type="checkbox" id="evidencia3"/></li>
                   <li><span>Evidencia Documental nº 4</span> <input class="tipo1" type="checkbox" id="evidencia4"/></li>
                   <li><span>Evidencia Documental nº 5</span> <input class="tipo1" type="checkbox" id="evidencia5"/></li>
                   <li><span>Evidencia Documental nº 6</span> <input class="tipo1" type="checkbox" id="evidencia6"/></li>
                   <li><span>Evidencia Documental nº 7</span> <input class="tipo1" type="checkbox" id="evidencia7"/></li>
                   <li><span>Evidencia Documental nº 8</span> <input class="tipo1" type="checkbox" id="evidencia8"/></li>
                   <li><span>Evidencia Documental nº 9</span> <input class="tipo1" type="checkbox" id="evidencia9"/></li>
                   <li><span>Evidencia Documental nº 10</span> <input class="tipo2" type="checkbox" id="evidencia10"/></li>
                   <li><span>Evidencia Documental nº 11</span> <input class="tipo2" type="checkbox" id="evidencia11"/></li>
                   <li><span>Evidencia Documental nº 12</span> <input class="tipo2" type="checkbox" id="evidencia12"/></li>
                   <li><span>Evidencia Documental nº 13</span> <input class="tipo2" type="checkbox" id="evidencia13"/></li>
                   <li><span>Evidencia Documental nº 14</span> <input class="tipo2" type="checkbox" id="evidencia14"/></li>
               </ul>
               -->
			</span> <br />
			<br />
			<table class="tablaECP tablaECP2">
				<tr class="titulosTablaECP">
					<td width="20" class="textoCentrado">Nº</td>
					<td class="textoCentrado">CUESTIONES</td>
					<td width="80" class="textoCentrado">Completamente de acuerdo
					</td>
					<td width="80" class="textoCentrado">De acuerdo</td>
					<td width="80" class="textoCentrado">Ni de acuerdo ni en
						desacuerdo</td>
					<td width="80" class="textoCentrado">En desacuerdo</td>
					<td width="80" class="textoCentrado">En completo desacuerdo</td>
				</tr>
				<tr>
					<td colspan="7">&Aacute;REA: INFORMACI&Oacute;N</td>
				</tr>
				<tr>
					<td>1</td>
					<td>&iquest;Se le ha informado adecuadamente sobre la
						autoevaluaci&oacute;n en la aplicaci&oacute;n inform&aacute;tica?</td>
					<td><input type="radio" name="pregunta_1" value="5"
						title="Completamente de acuerdo"></td>
					<td><input type="radio" name="pregunta_1" value="4"
						title="De acuerdo"></td>
					<td><input type="radio" name="pregunta_1" value="3"
						title="Ni de acuerdo ni en desacuerdo"></td>
					<td><input type="radio" name="pregunta_1" value="2"
						title="En desacuerdo"></td>
					<td><input type="radio" name="pregunta_1" value="1"
						title="En completo desacuerdo"></td>
				</tr>
				<tr>
					<td>2</td>
					<td>&iquest;Las instrucciones facilitadas le han sido
						&uacute;tiles para llevar a cabo el proceso de
						autoevaluaci&oacute;n?</td>
					<td><input type="radio" name="pregunta_2" value="5"
						title="Completamente de acuerdo"></td>
					<td><input type="radio" name="pregunta_2" value="4"
						title="De acuerdo"></td>
					<td><input type="radio" name="pregunta_2" value="3"
						title="Ni de acuerdo ni en desacuerdo"></td>
					<td><input type="radio" name="pregunta_2" value="2"
						title="En desacuerdo"></td>
					<td><input type="radio" name="pregunta_2" value="1"
						title="En completo desacuerdo"></td>
				</tr>
				<tr>
					<td>3</td>
					<td>Si en alg&uacute;n momento usted ha necesitado ayuda,
						&iquest;le ha resultado sencillo acceder a ella a trav&eacute;s de
						la plataforma inform&aacute;tica?</td>
					<td><input type="radio" name="pregunta_3" value="5"
						title="Completamente de acuerdo"></td>
					<td><input type="radio" name="pregunta_3" value="4"
						title="De acuerdo"></td>
					<td><input type="radio" name="pregunta_3" value="3"
						title="Ni de acuerdo ni en desacuerdo"></td>
					<td><input type="radio" name="pregunta_3" value="2"
						title="En desacuerdo"></td>
					<td><input type="radio" name="pregunta_3" value="1"
						title="En completo desacuerdo"></td>
				</tr>
				<tr>
					<td>4</td>
					<td>Valore de 1 a 5 su satisfacci&oacute;n con los sistemas de
						informaci&oacute;n y la ayuda de la aplicaci&oacute;n.</td>
					<td><input type="radio" name="pregunta_4" value="5"
						title="Completamente de acuerdo"></td>
					<td><input type="radio" name="pregunta_4" value="4"
						title="De acuerdo"></td>
					<td><input type="radio" name="pregunta_4" value="3"
						title="Ni de acuerdo ni en desacuerdo"></td>
					<td><input type="radio" name="pregunta_4" value="2"
						title="En desacuerdo"></td>
					<td><input type="radio" name="pregunta_4" value="1"
						title="En completo desacuerdo"></td>
				</tr>
				<tr>
					<td colspan="7">&Aacute;REA: FUNCIONAMIENTO</td>
				</tr>
				<tr>
					<td rowspan="6">5</td>
					<td colspan="6">Valore de 1 a 5 los siguientes conceptos
						relativos a la aplicaci&oacute;n inform&aacute;tica:*<br /> *Le
						recordamos que la puntuaci&oacute;n 5 ser&aacute; la m&aacute;xima
						frente al 1, considerada como m&iacute;nima.
					</td>
				</tr>
				<tr>
					<td>Acceso</td>
					<td><input type="radio" name="pregunta_5_1" value="5"
						title="Valor 5 (Muy favorable)"></td>
					<td><input type="radio" name="pregunta_5_1" value="4"
						title="Valor 4 (Favorable)"></td>
					<td><input type="radio" name="pregunta_5_1" value="3"
						title="Valor 3 (Normal)"></td>
					<td><input type="radio" name="pregunta_5_1" value="2"
						title="Valor 2 (Poco favorable)"></td>
					<td><input type="radio" name="pregunta_5_1" value="1"
						title="Valor 1 (Muy poco favorable)"></td>
				</tr>
				<tr>
					<td>Navegabilidad</td>
					<td><input type="radio" name="pregunta_5_2" value="5"
						title="Valor 5 (Muy favorable)"></td>
					<td><input type="radio" name="pregunta_5_2" value="4"
						title="Valor 4 (Favorable)"></td>
					<td><input type="radio" name="pregunta_5_2" value="3"
						title="Valor 3 (Normal)"></td>
					<td><input type="radio" name="pregunta_5_2" value="2"
						title="Valor 2 (Poco favorable)"></td>
					<td><input type="radio" name="pregunta_5_2" value="1"
						title="Valor 1 (Muy poco favorable)"></td>
				</tr>
				<tr>
					<td>Interfaz (dise&ntilde;o)</td>
					<td><input type="radio" name="pregunta_5_3" value="5"
						title="Valor 5 (Muy favorable)"></td>
					<td><input type="radio" name="pregunta_5_3" value="4"
						title="Valor 4 (Favorable)"></td>
					<td><input type="radio" name="pregunta_5_3" value="3"
						title="Valor 3 (Normal)"></td>
					<td><input type="radio" name="pregunta_5_3" value="2"
						title="Valor 2 (Poco favorable)"></td>
					<td><input type="radio" name="pregunta_5_3" value="1"
						title="Valor 1 (Muy poco favorable)"></td>
				</tr>
				<tr>
					<td>Legibilidad</td>
					<td><input type="radio" name="pregunta_5_4" value="5"
						title="Valor 5 (Muy favorable)"></td>
					<td><input type="radio" name="pregunta_5_4" value="4"
						title="Valor 4 (Favorable)"></td>
					<td><input type="radio" name="pregunta_5_4" value="3"
						title="Valor 3 (Normal)"></td>
					<td><input type="radio" name="pregunta_5_4" value="2"
						title="Valor 2 (Poco favorable)"></td>
					<td><input type="radio" name="pregunta_5_4" value="1"
						title="Valor 1 (Muy poco favorable)"></td>
				</tr>
				<tr>
					<td>Descarga de documentos</td>
					<td><input type="radio" name="pregunta_5_5" value="5"
						title="Valor 5 (Muy favorable)"></td>
					<td><input type="radio" name="pregunta_5_5" value="4"
						title="Valor 4 (Favorable)"></td>
					<td><input type="radio" name="pregunta_5_5" value="3"
						title="Valor 3 (Normal)"></td>
					<td><input type="radio" name="pregunta_5_5" value="2"
						title="Valor 2 (Poco favorable)"></td>
					<td><input type="radio" name="pregunta_5_5" value="1"
						title="Valor 1 (Muy poco favorable)"></td>
				</tr>
				<tr>
					<td>6</td>
					<td>Cuando usted ha necesitado ayuda individualizada para
						resolver problemas t&eacute;cnicos/te&oacute;ricos de la
						aplicaci&oacute;n, el personal de la FQS se lo ha resuelto con
						rapidez y eficacia.</td>
					<td><input type="radio" name="pregunta_6" value="5"
						title="Completamente de acuerdo"></td>
					<td><input type="radio" name="pregunta_6" value="4"
						title="De acuerdo"></td>
					<td><input type="radio" name="pregunta_6" value="3"
						title="Ni de acuerdo ni en desacuerdo"></td>
					<td><input type="radio" name="pregunta_6" value="2"
						title="En desacuerdo"></td>
					<td><input type="radio" name="pregunta_6" value="1"
						title="En completo desacuerdo"></td>
				</tr>
				<tr>
					<td>7</td>
					<td colspan="6">&iquest;Considera mejorable alg&uacute;n
						aspecto t&eacute;cnico de la aplicaci&oacute;n?<br /> Por favor,
						detalle su opini&oacute;n: <br />
					<br /> <textarea name="pregunta_7" cols="110" rows="4"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="7">&Aacute;REA: TIEMPO</td>
				</tr>
				<tr>
					<td>8</td>
					<td>&iquest;Cu&aacute;nto tiempo ha empleado en realizar la
						parte telem&aacute;tica de la autoevaluaci&oacute;n?</td>
					<td align="right" colspan="3"><strong>Nº de horas:</strong></td>
					<td colspan="2"><input type="textinput" name="pregunta_8" /></td>
				</tr>
				<tr>
					<td>9</td>
					<td>&iquest;C&oacute;mo de adecuado le ha parecido el tiempo?</td>
					<td><input type="radio" name="pregunta_9" value="5"
						title="Completamente de acuerdo"></td>
					<td><input type="radio" name="pregunta_9" value="4"
						title="De acuerdo"></td>
					<td><input type="radio" name="pregunta_9" value="3"
						title="Ni de acuerdo ni en desacuerdo"></td>
					<td><input type="radio" name="pregunta_9" value="2"
						title="En desacuerdo"></td>
					<td><input type="radio" name="pregunta_9" value="1"
						title="En completo desacuerdo"></td>
				</tr>
				<tr>
					<td>10</td>
					<td>En cuanto a todo el proceso de acceso a grado de Carrera
						Profesional &iquest;le han parecido suficientes los plazos
						marcados?</td>
					<td><input type="radio" name="pregunta_10" value="5"
						title="Completamente de acuerdo"></td>
					<td><input type="radio" name="pregunta_10" value="4"
						title="De acuerdo"></td>
					<td><input type="radio" name="pregunta_10" value="3"
						title="Ni de acuerdo ni en desacuerdo"></td>
					<td><input type="radio" name="pregunta_10" value="2"
						title="En desacuerdo"></td>
					<td><input type="radio" name="pregunta_10" value="1"
						title="En completo desacuerdo"></td>
				</tr>
				<tr>
					<td>11</td>
					<td colspan="6">Explique c&oacute;mo mejorar&iacute;a los
						plazos establecidos para cada una de las fases: <br />
					<br /> <textarea name="pregunta_11" cols="110" rows="4"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="7">&Aacute;REA: IMAGEN PERCIBIDA</td>
				</tr>
				<tr>
					<td rowspan="5">12</td>
					<td colspan="6">De los siguientes organismos, se&ntilde;ale
						cu&aacute;les considera usted que intervienen en el proceso de
						evaluaci&oacute;n de la Carrera Profesional:</td>
				</tr>
				<tr>
					<td>S&oacute;lo Fundaci&oacute;n Centro Regional de Calidad y
						Acreditaci&oacute;n Sanitaria de Castilla y Le&oacute;n (FQS)</td>
					<td colspan="5"><input type="radio" name="pregunta_12"
						value="1"
						title="S&oacute;lo Fundaci&oacute;n Centro Regional de Calidad y Acreditaci&oacute;n Sanitaria de Castilla y Le&oacute;n (FQS)"></td>
				</tr>
				<tr>
					<td>FQS y Consejer&iacute;a de Sanidad</td>
					<td colspan="5"><input type="radio" name="pregunta_12"
						value="2" title="FQS y Consejer&iacute;a de Sanidad"></td>
				</tr>
				<tr>
					<td>FQS y Gerencia Regional de Salud</td>
					<td colspan="5"><input type="radio" name="pregunta_12"
						value="3" title="FQS y Gerencia Regional de Salud"></td>
				</tr>
				<tr>
					<td>Todas las respuestas anteriores</td>
					<td colspan="5"><input type="radio" name="pregunta_12"
						value="4" title="Todas las respuestas anteriores"></td>
				</tr>
				<tr>
					<td>13</td>
					<td>Indique su grado de satisfacci&oacute;n con la
						coordinaci&oacute;n entre los diferentes organismos que participan
						en el proceso de evaluaci&oacute;n de la Carrera Profesional.</td>
					<td><input type="radio" name="pregunta_13" value="5"
						title="Valor 5 (Muy favorable)"></td>
					<td><input type="radio" name="pregunta_13" value="4"
						title="Valor 4 (Favorable)"></td>
					<td><input type="radio" name="pregunta_13" value="3"
						title="Valor 3 (Normal)"></td>
					<td><input type="radio" name="pregunta_13" value="2"
						title="Valor 2 (Poco favorable)"></td>
					<td><input type="radio" name="pregunta_13" value="1"
						title="Valor 1 (Muy poco favorable)"></td>
				</tr>
				<tr>
					<td>14</td>
					<td>Desde su punto de vista &iquest;la FQS ha realizado
						adecuadamente la labor que esperaba y contando con su confianza?</td>
					<td><input type="radio" name="pregunta_14" value="5"
						title="Completamente de acuerdo"></td>
					<td><input type="radio" name="pregunta_14" value="4"
						title="De acuerdo"></td>
					<td><input type="radio" name="pregunta_14" value="3"
						title="Ni de acuerdo ni en desacuerdo"></td>
					<td><input type="radio" name="pregunta_14" value="2"
						title="En desacuerdo"></td>
					<td><input type="radio" name="pregunta_14" value="1"
						title="En completo desacuerdo"></td>
				</tr>
				<tr>
					<td colspan="7">&Aacute;REA: GLOBAL</td>
				</tr>
				<tr>
					<td>15</td>
					<td colspan="6">&iquest;Cree que la autoevaluaci&oacute;n
						realizada se corresponde con su perfil profesional?<br>
							<p align="center">
								S&iacute; <input type="radio" name="pregunta_15" value="0"
									title="Si"> &nbsp;&nbsp;&nbsp; No <input type="radio"
									name="pregunta_15" value="1" title="No">
							</p>
							<p>
								En caso de que su respuesta sea negativa, por favor, justifique
								la respuesta: <br />
								<textarea name="pregunta_15_no" cols="110" rows="4"></textarea>
							</p>
					</td>
				</tr>
				<tr>
					<td>16</td>
					<td>&iquest;Considera que la autoevaluaci&oacute;n realizada
						por usted puede aportar aspectos positivos aplicables a su trabajo
						diario?</td>
					<td><input type="radio" name="pregunta_16" value="5"
						title="Completamente de acuerdo"></td>
					<td><input type="radio" name="pregunta_16" value="4"
						title="De acuerdo"></td>
					<td><input type="radio" name="pregunta_16" value="3"
						title="Ni de acuerdo ni en desacuerdo"></td>
					<td><input type="radio" name="pregunta_16" value="2"
						title="En desacuerdo"></td>
					<td><input type="radio" name="pregunta_16" value="1"
						title="En completo desacuerdo"></td>
				</tr>
				<tr>
					<td rowspan="5">17</td>
					<td colspan="6">Valore de 1 a 5 el proceso de
						autoevaluaci&oacute;n de acuerdo con:*<br /> *Le recordamos que
						la puntuaci&oacute;n 5 ser&aacute; la m&aacute;xima frente al 1,
						considerada como m&iacute;nima.
					</td>
				</tr>
				<tr>
					<td>Contenido</td>
					<td><input type="radio" name="pregunta_17_1" value="5"
						title="Valor 5 (Muy favorable)"></td>
					<td><input type="radio" name="pregunta_17_1" value="4"
						title="Valor 4 (Favorable)"></td>
					<td><input type="radio" name="pregunta_17_1" value="3"
						title="Valor 3 (Normal)"></td>
					<td><input type="radio" name="pregunta_17_1" value="2"
						title="Valor 2 (Poco favorable)"></td>
					<td><input type="radio" name="pregunta_17_1" value="1"
						title="Valor 1 (Muy poco favorable)"></td>
				</tr>
				<tr>
					<td>Grado de dificultad</td>
					<td><input type="radio" name="pregunta_17_2" value="5"
						title="Valor 5 (Muy favorable)"></td>
					<td><input type="radio" name="pregunta_17_2" value="4"
						title="Valor 4 (Favorable)"></td>
					<td><input type="radio" name="pregunta_17_2" value="3"
						title="Valor 3 (Normal)"></td>
					<td><input type="radio" name="pregunta_17_2" value="2"
						title="Valor 2 (Poco favorable)"></td>
					<td><input type="radio" name="pregunta_17_2" value="1"
						title="Valor 1 (Muy poco favorable)"></td>
				</tr>
				<tr>
					<td>Tiempo invertido</td>
					<td><input type="radio" name="pregunta_17_3" value="5"
						title="Valor 5 (Muy favorable)"></td>
					<td><input type="radio" name="pregunta_17_3" value="4"
						title="Valor 4 (Favorable)"></td>
					<td><input type="radio" name="pregunta_17_3" value="3"
						title="Valor 3 (Normal)"></td>
					<td><input type="radio" name="pregunta_17_3" value="2"
						title="Valor 2 (Poco favorable)"></td>
					<td><input type="radio" name="pregunta_17_3" value="1"
						title="Valor 1 (Muy poco favorable)"></td>
				</tr>
				<tr>
					<td>Presentaci&oacute;n de la misma</td>
					<td><input type="radio" name="pregunta_17_4" value="5"
						title="Valor 5 (Muy favorable)"></td>
					<td><input type="radio" name="pregunta_17_4" value="4"
						title="Valor 4 (Favorable)"></td>
					<td><input type="radio" name="pregunta_17_4" value="3"
						title="Valor 3 (Normal)"></td>
					<td><input type="radio" name="pregunta_17_4" value="2"
						title="Valor 2 (Poco favorable)"></td>
					<td><input type="radio" name="pregunta_17_4" value="1"
						title="Valor 1 (Muy poco favorable)"></td>
				</tr>
				<tr>
					<td>18</td>
					<td colspan="6">El m&eacute;todo empleado para el acceso a
						grado de Carrera Profesional en Castilla y Le&oacute;n, &iquest;ha
						respondido a las expectativas que ten&iacute;a depositadas en
						&eacute;l?<br>
							<p align="center">
								S&iacute; <input type="radio" name="pregunta_18" value="0"
									title="Si"> &nbsp;&nbsp;&nbsp; No <input type="radio"
									name="pregunta_18" value="1" title="No">
							</p>
							<p>
								En caso de que su respuesta sea negativa, por favor,
								justif&iacute;quela: <br />
								<textarea name="pregunta_18_no" cols="110" rows="4"></textarea>
							</p>
					</td>
				</tr>
				<tr>
					<td>19</td>
					<td>Valore de 1 a 5 la confianza que usted deposita en el
						sistema de evaluaci&oacute;n del Grado de Carrera Profesional en
						Castilla y Le&oacute;n.</td>
					<td><input type="radio" name="pregunta_19" value="5"
						title="Valor 5 (Muy favorable)"></td>
					<td><input type="radio" name="pregunta_19" value="4"
						title="Valor 4 (Favorable)"></td>
					<td><input type="radio" name="pregunta_19" value="3"
						title="Valor 3 (Normal)"></td>
					<td><input type="radio" name="pregunta_19" value="2"
						title="Valor 2 (Poco favorable)"></td>
					<td><input type="radio" name="pregunta_19" value="1"
						title="Valor 1 (Muy poco favorable)"></td>
				</tr>
				<tr>
					<td>20</td>
					<td colspan="6">En nuestro af&aacute;n por seguir mejorando
						nuestro servicio hacia los solicitantes de grado de Carrera
						Profesional, nos gustar&iacute;a contar con su colaboraci&oacute;n
						y opini&oacute;n. Por este motivo y si as&iacute; lo desea, puede
						a continuaci&oacute;n, detallarnos sus sugerencias: <br />
					<br /> <textarea name="pregunta_20" cols="110" rows="10"></textarea>
					</td>
				</tr>
			</table>
			<input type="button" onclick="valida()"
				title="Pulse aqui para enviar la encuesta" value="Enviar encuesta" />

			<!--
               <fieldset>     
                  
                  <legend class="tituloLegend"> Mis datos de identificación son: </legend>            
                  <div class="cuadroFieldset">
                  
                     
                        <label for="idVApell1"> Apellido1: 
                           <input type="text" name="DApellido1" maxlength="60" tabindex="1" value="" class="recuadroM colocaApellidosEvaluadores">
                        </label>
                        <label for="idVNombre"> Nombre: 
                           <input type="text" name="DNombre" maxlength="30" tabindex="2" value="" class="recuadroM colocaNombreEvaluadores">
                        </label>                   
                        <br /><br />
                        <label for="idVApell1"> Apellido2: 
                           <input type="text" name="DApellido2" maxlength="60" tabindex="1" value="" class="recuadroM colocaApellidosEvaluadores">
                        </label>
                   <br /><br />                    
                        <label for="idVDNI"> NIF/NIE: 
                           <input type="text" name="CDni" maxlength="10" tabindex="3" value="" class="recuadroM colocaDNIEvaluadores">
                        </label>
                        <br /><br />
                  <label for="idVApell1"> Categoria: 
                           <input type="text" name="DCategoria" maxlength="60" tabindex="1" value="" class="recuadroM colocaApellidosEvaluadores">
                        </label>
                        <label for="idVNombre"> Centro: 
                           <input type="text" name="DCentro" maxlength="30" tabindex="2" value="" class="recuadroM colocaNombreEvaluadores">
                        </label>                   
                        <br /><br />
                        <label for="idVDNI"> Fecha:&nbsp;
                           <input type="text" name="CFecha" maxlength="10" tabindex="3" value="" class="recuadroM colocaDNIEvaluadores">
                        </label>
                        <br /><br />                                       
                      
   
                                          
                  </div>            
               </fieldset>  
               -->
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