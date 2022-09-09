<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>..: <%=es.jcyl.framework.JCYLConfiguracion.getValor("TITULO_TITLE")%>
	:..
</title>
<meta http-equiv="Content-Type"
	content="application/xhtml+xml; charset=ISO-8859-1" />
<link href="css/casa.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/casa_print.css" rel="stylesheet" type="text/css"
	media="print" />
<!--[if IE 6]>
      <link rel="stylesheet" type="text/css" href="css/casa_ie6.css" media="all" title="Hoja de estilos especifica para IE 6" ></link>
   <![endif]-->
<!--[if IE 7]>
      <link rel="stylesheet" type="text/css" href="css/casa_ie7.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
   <![endif]-->
<!--[if gt IE 7]>
      <link rel="stylesheet" type="text/css" href="css/casa_ie7.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
   <![endif]-->
<script type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"
	charset="windows-1252"></script>
</head>
<body>
	<div class="contenido">
		<div class="contenidoDCP1B">
			<html:form action="/OCAPSolicitudes.do">

				<h2 class="tituloContenido">NOTIFICACI&Oacute;N DE
					ACEPTACI&Oacute;N DE SOLICITUD</h2>
				<a href="#"><img src="imagenes/imprimir.gif" alt="Imprimir"
					class="flotaIzq" /></a>
				<h3 class="subTituloContenido">DCP1B</h3>
				<div class="lineaBajaM"></div>
				<div class="espaciador"></div>

				<div class="recuadroM">
					<div class="datosFechaComite">
						<label for="idVFecha"> Fecha: <input id="idVFecha"
							type="text" name="vFecha" value="" class="recuadroM"
							disabled="disabled" />
						</label>
					</div>
				</div>

				<fieldset>
					<legend class="tituloLegend"> Datos Personales </legend>
					<div class="cuadroFieldset">
						<label for="idVApell1"> Primer Apellido: <input
							id="idVApell1" type="text" name="vApell1" value=""
							class="recuadroM colocaApellido1" disabled="disabled" />
						</label> <label for="idVApell2"> Segundo Apellido: <input
							id="idVApell2" type="text" name="vApell2" value=""
							class="recuadroM colocaApellido2" disabled="disabled" />
						</label><br />
						<br /> <label for="idVNombre"> Nombre: <input
							id="idVNombre" type="text" name="vNombre" value=""
							class="recuadroM colocaNombre" disabled="disabled" />
						</label>
					</div>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> Datos Profesionales Actuales</legend>
					<div class="cuadroFieldset">
						<label for="idVCategoria"> Categor&iacute;a: <input
							id="idVCategoria" type="text" name="vCategoria" value=""
							class="recuadroM colocaCategoria" disabled="disabled" />
						</label> <label for="idVEspecialidad"> Especialidad: <input
							id="idVEspecialidad" type="text" name="vEspecialidad" value=""
							class="recuadroM colocaEspecialidad" disabled="disabled" />
						</label><br />
						<br /> <label for="idVProvincia"> Provincia: <input
							id="idVProvincia" type="text" name="vProvincia" value=""
							class="recuadroM colocaProvincia" disabled="disabled" />
						</label> <label for="idVGerencia"> Gerencia: <input
							id="idVGerencia" type="text" name="vGerencia" value=""
							class="recuadroM colocaGerencia" disabled="disabled" />
						</label><br />
						<br /> <label for="idVCentro"> Centro de Trabajo: <input
							id="idVCentro" type="text" name="vCentro" value=""
							class="recuadroM colocaCentro" disabled="disabled" />
						</label><br />
						<br /> <label for="idVUnidad"> Unidad de
							Adscripci&oacute;n / Servicio Actual: <input id="idVUnidad"
							type="text" name="vUnidad" value=""
							class="recuadroM colocaUnidad" disabled="disabled" />
						</label> <label for="idVPuesto"> Puesto: <input id="idVPuesto"
							type="text" name="vPuesto" value=""
							class="recuadroM colocaPuesto" disabled="disabled" />
						</label>
					</div>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> Grado </legend>
					<div class="cuadroFieldset">
						<label> <span class="radioGrado"> Grado I <input
								type="radio" name="vGrado" checked="checked" />
						</span> <span class="radioGrado"> Grado II <input type="radio"
								name="vGrado" />
						</span> <span class="radioGrado"> Grado III <input type="radio"
								name="vGrado" />
						</span> <span class="radioGrado"> Grado IV <input type="radio"
								name="vGrado" />
						</span>
						</label>
					</div>
				</fieldset>

				<p>
					En respuesta a su solicitud de inicio de procedimiento para el
					reconocimiento del Grado <input type="text" name="vGrado" value=""
						class="recuadroM colocaGradoTexto" disabled="disabled" /> de
					Carrera profesional y cumpliendo el requisito inicial de acceso
					seg&uacute;n Convocatoria de <input type="text" name="vConvo"
						value="" class="recuadroM colocaConvoTexto" disabled="disabled" />
					le informamos que puede iniciar la primera fase de
					evaluaci&oacute;n que corresponde a la presentaci&oacute;n de
					M&eacute;ritos y Competencias Profesionales. Tiene un plazo de 4
					semanas para realizar esta evaluaci&oacute;n y presentar la
					documentaci&oacute;n acreditativa y cotejada en su Gerencia.
				</p>
				<p>
					Para poder acceder al programa deber&aacute; escribir en su
					navegador la direcci&oacute;n siguiente: &nbsp; <span
						class="textoSubrayado">www.fqscyl.com</span> <br /> y sus claves
					ser&aacute;n: <br />
					<br /> <span> usuario: nif/nie </span><br /> <span>
						password: la que se genere. </span><br />
					<br /> A partir de ese momento podr&aacute; registrar en la
					aplicaci&oacute;n sus m&eacute;ritos curriculares:
					Formaci&oacute;n, Docencia, Investigaci&oacute;n y Gesti&oacute;n
					Cl&iacute;nica <br /> No obstante para poder acceder al programa
					ver&aacute; suficientemente explicitado el procedimiento de
					cumplimetanci&oacute;n de la auto-evaluaci&oacute;n. Pero, ante
					cualquier duda puede contactar con el servicio de gesti&oacute;n de
					Carrera Profesional de su Gerencia correspondiente.
				</p>

				<div class="limpiadora"></div>
				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="#"> <img src="imagenes/aspa_roja.gif"
								alt="Cancelar Solicitud" /> <span> Cancelar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="listadoAspirantesGCP.html"> <img
								src="imagenes/flecha_correcto.gif" alt="Aceptar Solicitud" /> <span>
									Aceptar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
				<div class="limpiadora"></div>
				<div class="espaciador"></div>


			</html:form>
		</div>
		<!-- /fin de ContenidoDCP1B -->
	</div>
</body>
</html>
