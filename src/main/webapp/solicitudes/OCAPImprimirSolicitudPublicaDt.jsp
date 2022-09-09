<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">

<head>
<title>..: Obtenci&oacute;n Carrera Profesional :..</title>
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
				<script type="text/javascript"
					src="<html:rewrite page='/javascript/comun.js'/>"
					charset="windows-1252"></script>
				<script type="text/javascript">
   function generarPDF() {
      enviar('OCAPNuevaSolicitud.do?accion=generarPDFSolic&cSolicitudId='+document.forms[0].CSolicitudId.value);
   }
   </script>
</head>
<body>
	<div class="encuadre">
		<jsp:include page="/cabecera.jsp" flush="true" />
		<div class="marcoContenidoAccesible">
			<div class="contenidoAccesible">
				<div class="cuadroContenedor">
					<div class="contenido">
						<div class="contenidoAltaMC">
							<h2 class="tituloContenido">SOLICITUD DE ACCESO A GRADO DE
								CARRERA PROFESIONAL</h2>
							<h3 class="subTituloContenido">&nbsp;</h3>
							<div class="lineaBajaM"></div>
							<div class="espaciador"></div>
							<html:form action="/OCAPNuevaSolicitud.do">
								<html:hidden name="OCAPNuevaSolicitudForm"
									property="CSolicitudId" />
								<fieldset>
									<legend class="tituloLegend"> Generaci&oacute;n de
										Solicitud </legend>
									<div class="cuadroFieldsetEv">
										<label for="idVTitulo">
											<p>
												A continuaci&oacute;n, deber&aacute; pulsar el bot&oacute;n
												<span class="textoCursiva textoNegrita">Generar
													Solicitud</span> para obtener su solicitud en pdf. <br />
												<br /> Posteriormente deber&aacute; imprimir el documento
												generado y entregarlo en cualquier Registro Oficial. <br />
												<br /> Finalmente, si su solicitud se ha generado
												correctamente, pulse <span class="textoCursiva textoNegrita">Cerrar</span>.
											</p>
										</label>
									</div>
								</fieldset>
								<div class="limpiadora"></div>
								<div class="espaciador"></div>
								<div class="botonesPagina">
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a href="inicio.jsp"> <img
												src="imagenes/imagenes_ocap/aspa_roja.gif"
												alt="Bot&oacute;n para generar su solicitud en PDF" /> <span>
													Cerrar </span>
										</a>
										</span> <span class="derBoton"></span>
									</div>
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a href="javascript:generarPDF();"> <img
												src="imagenes/imagenes_ocap/flecha_correcto.gif"
												alt="Bot&oacute;n para generar su solicitud en PDF" /> <span>
													Generar Solicitud </span>
										</a>
										</span> <span class="derBoton"></span>
									</div>
								</div>
							</html:form>
							<div class="espaciador"></div>
							<div class="limpiadora"></div>
						</div>
						<!-- /fin de ContenidoDCP1A -->
					</div>
					<!-- /Fin de Contenido -->
				</div>
				<!-- Del marco cuadroContenedor -->
			</div>
			<!-- Del marco contenidoAccesible -->
		</div>
		<!-- Del marco marcoContenidoAccesible -->
	</div>
	<!-- Del marco encuadre -->
	<div id="footer">
		<html:link href="http://www.salud.jcyl.es" target="_blank">Salud Castilla y Le&oacute;n </html:link>
	</div>
</body>
</html>
