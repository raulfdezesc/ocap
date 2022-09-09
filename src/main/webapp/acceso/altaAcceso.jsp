<%@ page contentType="text/html;charset=ISO-8859-1"	pageEncoding="windows-1252"%>
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

	
	<script src="javascript/comun.js?<%=System.currentTimeMillis()%>"></script>
	<script src="javascript/formularios.js?<%=System.currentTimeMillis()%>"></script>
</head>
<%
  String  dniUsuario = (String) session.getAttribute("DNI");
%>
<body>


	<div class="ocultarCampo">
		<div class="encuadre">
			<jsp:include page="/cabecera.jsp" flush="true" />
			<div class="cuadroContenedor ajustarCajaLogin">
		
				<html:form action="/OCAPAcceso.do?accion=guardarDatosAcceso" focus="APassword"  method="POST">
				 
					<div class="titulocajaformulario">Alta de usuario para acceso a Carrera Profesional</div>
										
					<div class="cajaformulario">
					<p class="ocultarCampo">No existe el usuario introducido. Si desea crear un usuario para poder acceder, rellene  todos los campos del formulario.</p>
					<br/>						
						<fieldset class="normalesLogin">
							<legend class="normalesLegend">Datos  de acceso que deben completarse</legend>
							<!-- Propiedad "login" -->
							  
							<p>
								<label for="campoemail">NIF / NIE: * </label>
							<html:text readonly="true" name="OCAPAccesoForm" property="ADni" value="<%=dniUsuario%>" maxlength="10"  styleClass="recuadroM  textoNormal" />        							 
								<br />
								<html:errors property="ADni" />
							</p>
							<!-- Propiedad "password" -->
							<p>
							<label for="campoemail">Contraseña * </label>
								<html:password name="OCAPAccesoForm" property="APassword"   maxlength="10"  styleClass="recuadroM  textoNormal" />   		
										<br />
								<html:errors property="APassword" />
							</p>
							<p>
							<label for="campoemail">Repita Contraseña * </label>
								<html:password name="OCAPAccesoForm" property="APasswordRepet"   maxlength="10"  styleClass="recuadroM  textoNormal" />   		
										<br />
								<html:errors property="APasswordRepet" />								 
							</p>
							<p>
							
							<label for="campoemail">Correo Electr&oacute;nico para recuerdo de contraseña: *</label> <html:text
								property="AEmailRecuerdo" name="OCAPAccesoForm"
								styleClass="recuadroM colocaCorreoAltaAsterisco textoNormal"
								maxlength="53" />
							
							<br />
								<html:errors property="AEmailRecuerdo" />											
							</p>
							 
						</fieldset>
					
					<br />					

						<div class="botonLogin">
							<div class="limpiadora"></div>
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> 
								<html:button
										value="Aceptar" property="Aceptar"
										styleClass="botonNoLink" onclick="enviarValidadoNIF();" />
								</span> <span class="derBoton"></span>
							</div>
							<div class="botonAccion">
				<span class="izqBoton"></span> <span class="cenBoton"> <a	href="CerrarSesion.do"> <span> P&aacute;gina de inicio </span>
				</a>
				</span> <span class="derBoton"></span>
			</div>
							<div class="limpiadora"></div>
							<div class="espaciador"></div>
						</div>
						
					</div>
					     
              
				</html:form>
				 
			</div>
			<!-- marco Contenedor -->
		</div>
		<!-- Encuadre General -->
		<div id="footer">
			<html:link href="http://www.salud.jcyl.es" target="_blank">Salud Castilla y Le&oacute;n </html:link><html:img page="/imagenes/puntitoM.png" alt="" /><bean:message key="version" />
		</div>
	</div>
	<!-- /Fin de ocultarCampo -->
</body>
</html>
