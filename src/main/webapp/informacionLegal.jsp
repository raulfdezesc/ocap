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
			<!--Si la variable MENU_ACCESIBLE=SI, se muestra el menu accesible-->
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
							<div class="titulocajaformulario">Informaci&oacute;n Legal
							</div>
							<div class="cajaformulario">
								<p class="fuenteComic">En la actualidad, el acceso a los
									datos de salud es un tema especialmente sensible, que exige
									tener muy presentes determinados aspectos, como el derecho a la
									intimidad de los pacientes, la confidencialidad de la
									informaci&oacute;n sanitaria, etc. Pero por otra parte, resulta
									de vital importancia conjugar y compaginar el acceso a la
									historia cl&iacute;nica con el debido respeto a los derechos
									fundamentales de la persona (intimidad, confidencialidad, etc.)
									y con las debidas garant&iacute;as para que &eacute;stos no
									sean menoscabados.</p>									
								<p>El acceso y tratamiento de datos de car&aacute;cter
									personal se rige por la Ley Org&aacute;nica 3/2018 de 5 de
									Diciembre de Protecci&oacute;n de Datos Personales, y Garant&iacute; de
									los Derechos Digitales,  concretamente los datos relacionados con la
									Historia Cl&iacute;nica, se hace necesario adem&aacute;s la
									observancia de la Ley 41/2002 de 14 de Noviembre B&aacute;sica
									Reguladora del Paciente y de los Derechos y Obligaciones en
									materia de Informaci&oacute;n y Documentaci&oacute;n
									Cl&iacute;nica.</p>
								<p>Esta &uacute;ltima norma, establece en lo referente a los
									Usos de la Historia Cl&iacute;nica que "el personal sanitario
									debidamente acreditado que ejerza funciones de
									inspecci&oacute;n, evaluaci&oacute;n, acreditaci&oacute;n y
									planificaci&oacute;n, tienen acceso a las historias
									cl&iacute;nicas en el cumplimiento de sus funciones de calidad
									de la asistencia", a&ntilde;adiendo tambi&eacute;n que el
									personal que accede a los datos de la historia cl&iacute;nica
									en el ejercicio de sus funciones queda sujeto al deber de
									secreto profesional y a la obligaci&oacute;n de preservar los
									datos de identificaci&oacute;n personal del paciente,
									asegurando as&iacute; el anonimato.</p>
								<p>A su vez, la Ley 41/2002, de 14 de Noviembre,
									B&aacute;sica Reguladora del Paciente y de los Derechos y
									Obligaciones en materia de Informaci&oacute;n y
									Documentaci&oacute;n Cl&iacute;nica, establece que ser&aacute;n
									las Comunidades Aut&oacute;nomas las que regular&aacute;n el
									procedimiento para que quede constancia del acceso a la
									Historia Cl&iacute;nica y de su uso.</p>
								<p>En la Comunidad de Castilla y Le&oacute;n, se regula la
									intimidad y el respeto a la confidencialidad de la
									informaci&oacute;n sobre la salud, protecci&oacute;n de los
									derechos relativos a la documentaci&oacute;n sanitaria y la
									importancia de la Historia Cl&iacute;nica, mediante la Ley
									8/2003 de 8 de Abril, Sobre Derechos y Deberes de las personas
									en relaci&oacute;n con la salud y el Decreto 101/2005 de 22 de
									Diciembre relativo a la historia Cl&iacute;nica y el acceso a
									la misma.</p>
								<p>Se establece lo siguiente:</p>
								<div class="listadoLogin">
									<ul>
										<li>El personal No Sanitario s&oacute;lo podr&aacute;
											acceder a los datos de la Historia Cl&iacute;nica
											imprescindibles para realizar las funciones que tiene
											encomendadas.</li>
										<div class="espaciador"></div>
										<li>Igualmente el personal al servicio de la
											Administraci&oacute;n Sanitaria que lleve a cabo funciones de
											Inspecci&oacute;n, evaluaci&oacute;n, acreditaci&oacute;n y
											planificaci&oacute;n podr&aacute; acceder a los datos
											necesarios de la Historia Cl&iacute;nica para el ejercicio de
											sus funciones.</li>
									</ul>
								</div>
								<p>Esto en definitiva supone como regla general, preservar
									el anonimato de la Historia Cl&iacute;nica,
									facilit&aacute;ndose la documentaci&oacute;n necesaria para el
									caso y s&oacute;lo se podr&aacute; utilizar con esos fines.</p>
								<p>Por todo lo mencionado se establecer&aacute;n los
									mecanismos necesarios para salvaguardar el anonimato de los
									titulares de las Historias Cl&iacute;nicas.</p>
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
		<html:img page="/imagenes/puntitoM.png" alt="" />
		<logic:present
			name="<%=es.jcyl.framework.JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO%>">
			<a href="PaginaInicio.do">P&aacute;gina de inicio</a>
		</logic:present>
	</div>

</body>
</html>
