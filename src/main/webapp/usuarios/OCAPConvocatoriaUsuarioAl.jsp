<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<%@ taglib uri="html.tld" prefix="html"%>
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
      <![endif]-->
			<!--[if IE 7]>
         <link rel="stylesheet" type="text/css" href="css/EstilosJCYLFW_ie7.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
         <link rel="stylesheet" type="text/css" href="css/ocap_ie7.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <![endif]-->
			<!--[if gt IE 7]>
         <link rel="stylesheet" type="text/css" href="css/EstilosJCYLFW_ie8.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
         <link rel="stylesheet" type="text/css" href="css/ocap_ie8.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <![endif]-->

			<script type="text/javascript"
				src="<html:rewrite page='/javascript/comun.js'/>"
				charset="windows-1252"></script>
</head>
<body>
	<div class="ocultarCampo">
		<div class="encuadre">
			<jsp:include page="/cabecera.jsp" flush="true" />
			<div class="cuadroContenedor ajustarCajaLogin">
				<html:form action="OCAPUsuarios.do?accion=elegirConvocatoria"
					method="POST">
					<div class="titulocajaformulario">Acceso a Carrera
						Profesional</div>
					<div class="cajaformulario">
						<fieldset class="normalesLoginPerfil">
							<legend class="normalesLegendPerfil">Convocatoria</legend>
							<p>Seleccione la Convocatoria con la que desea acceder al
								sistema:</p>
							<div class="contenedorPerfiles">
								<html:select property="CConvocatoriaId"
									styleClass="cbCuadros colocaConvocatoriaCB" size="1"
									tabindex="1" styleId="convocatoria">
									<html:option value="0">Seleccione una Convocatoria...</html:option>
									<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
										property="CConvocatoriaId" labelProperty="DNombre" />
								</html:select>
							</div>
						</fieldset>
						<div class="botonLogin">
							<div class="limpiadora"></div>
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <html:submit
										value="Aceptar" styleClass="botonNoLink" />
								</span> <span class="derBoton"></span>
							</div>
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <html:button
										value="Cancelar" property="Cancelar" styleClass="botonNoLink"
										onclick="enviar('CerrarSesion.do');" />
								</span> <span class="derBoton"></span>
							</div>
							<div class="limpiadora"></div>
							<div class="espaciador"></div>
						</div>
						<div>
							<html:messages id="mensaje" message="true">
								<span class="colorError"><bean:write name="mensaje"
										ignore="true" /></span>
							</html:messages>
						</div>
						<div>
							<br />&nbsp;<br />&nbsp;
						</div>
					</div>
				</html:form>
			</div>
			<!-- marco Contenedor -->
			<div>
				<br />&nbsp;<br />&nbsp;
			</div>
		</div>
		<!-- Encuadre General -->
		<div id="footer">
			<html:link href="http://www.salud.jcyl.es" target="_blank">Salud Castilla y Le&oacute;n </html:link>
		</div>
		<div>
			<br />&nbsp;<br />
		</div>
	</div>
	<!-- /Fin de ocultarCampo -->
</body>
</html>
