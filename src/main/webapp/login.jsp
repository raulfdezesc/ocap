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

	<script type="text/javascript"		src="<html:rewrite page='/javascript/comun.js'/>"	charset="windows-1252"></script>
	
	 
</head>
<body>
	<div class="ocultarCampo">
		<div class="encuadre">
			<jsp:include page="cabecera.jsp" flush="true" />
			<div class="cuadroContenedor ajustarCajaLogin">
				<html:form action="Login.do" focus="login" method="POST">
					<% 
               java.lang.String tipoLogin = request.getParameter("acceso");
               if (org.apache.commons.validator.GenericValidator.isBlankOrNull(tipoLogin))
                  {tipoLogin = es.jcyl.framework.JCYLConfiguracion.getValor("TIPO_LOGIN_DEFECTO"); /* Establecer cual es el valor por defecto */ }
               if(tipoLogin.equals("password") || tipoLogin.equals("passcert")) {
               %>
					<div class="titulocajaformulario">Acceso a Carrera
						Profesional</div>
					<div class="cajaformulario">
						<% if (request.getAttribute("listaPerfiles") == null ) { %>
						<fieldset class="normalesLogin">
							<legend class="normalesLegend">Datos Obligatorios</legend>
							<!-- Propiedad "login" -->
							<p>
								<label for="campologin" class="tituloLogin"><bean:message
										key="login.etiqueta.usuario" /></label> <input id="campologin"
									type="text" name="login" class="inputColor colocaUsuario"
									value="<%=request.getAttribute("login")==null?"":request.getAttribute("login")%>" />&nbsp;
								<br />
								<html:errors property="login" />
							</p>
							<!-- Propiedad "password" -->
							<p>
								<label for="campopw" class="tituloLogin"><bean:message
										key="login.etiqueta.pw" /></label> <input id="campopw" type="password"
									name="psswd" class="inputColor colocaPassword"
									value="<%=request.getAttribute("password")==null?"":request.getAttribute("password")%>" />&nbsp;
								<br />
								<html:errors property="psswd" />
							</p>
						</fieldset>
						<% } %>
												
						<% if (request.getAttribute("listaPerfiles") != null) { %>
						<% String[] listaPerfiles = (String[])request.getAttribute("listaPerfiles");%>
						<fieldset class="normalesLoginPerfil">
							<legend class="normalesLegendPerfil">Perfil</legend>
							<p>Seleccione el perfil con el que desea acceder al sistema:
							</p>
							<div class="contenedorPerfiles">
								<% for (int indice = 0; indice < listaPerfiles.length; indice++) { 
                                 String perfil = listaPerfiles[indice];
                                 if (listaPerfiles[indice].indexOf("OCAP_")!= -1) {
                                    perfil = listaPerfiles[indice].substring(listaPerfiles[indice].indexOf("OCAP_")+5,listaPerfiles[indice].length());
                                 }
                                 
                                 //JACOBO
                                 if(perfil.equals("FQS_GEST")) { %>
								<div class="envuelto">
									<div class="radio">
										<input type="radio" name="perfil"
											value="<%=listaPerfiles[indice]%>" />
									</div>
									<div class="perfil">GRS_GEST</div>
								</div>
								<% } else if(perfil.equals("FQS_ADTVO")) { %>
								<div class="envuelto">
									<div class="radio">
										<input type="radio" name="perfil"
											value="<%=listaPerfiles[indice]%>" />
									</div>
									<div class="perfil">GRS_ADTVO</div>
								</div>
								<% } else {%>
								<div class="envuelto">
									<div class="radio">
										<input type="radio" name="perfil"
											value="<%=listaPerfiles[indice]%>" />
									</div>
									<div class="perfil"><%=perfil%></div>
								</div>
								<% }
                              }
                           %>
								<input type="hidden" name="login"
									value="<%=request.getAttribute("login")==null?"":request.getAttribute("login")%>" />
								<input type="hidden" name="psswd"
									value="<%=request.getAttribute("password")==null?"":request.getAttribute("password")%>" />
							</div>
						</fieldset>
						<% } %>

					<% if (request.getAttribute("enviarMail") != null) { 					
					String direccionMail = (String)request.getAttribute("enviarMail");								
					 %>
					<p class="textoInferiorPortada">
					<label style="color: red;">Contrase&ntilde;a incorrecta&nbsp;</label><label>Si desea que se le env&iacute;e al correo electr&oacute;nico que indic&oacute; al registrarse, pulse en &quot;Enviar Contrase&ntilde;a&quot;</label>
					</p>								
						<div class="botonLogin">
							<div class="limpiadora"></div>
							
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <html:submit
										value="Aceptar" styleClass="botonNoLink" />
								</span> <span class="derBoton"></span>
							</div>
													
							
								<div class="botonAccion">	
									<span class="izqBoton"></span> 
								 <span class="cenBoton">  								 
								 <a	href="inicio.jsp"> <span>&nbsp;Cancelar&nbsp;</span>	</a>
							
									</span> 
								<span class="derBoton"></span>
								</div>		
								
								<div class="botonAccion">		
							
							 	<span class="izqBoton"></span> 
								 <span class="cenBoton">  
									<html:button
										value="Enviar contraseña" property="enviarCorreo"
										styleClass="botonNoLink" onclick="enviar('OCAPAcceso.do?accion=enviarMailRecuperacion');" />									 
								</span> 
								<span class="derBoton"></span>
								</div>
								<div class="limpiadora"></div>
							<div class="espaciador"></div>
						  
					      
					</div>	
					<% } else {%>

						<div class="botonLogin">
							<div class="limpiadora"></div>
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <html:submit
										value="Aceptar" styleClass="botonNoLink" />
								</span> <span class="derBoton"></span>
							</div>
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <% if (request.getAttribute("listaPerfiles") == null) { %>
									<html:reset value="Cancelar" property="Cancelar"
										styleClass="botonNoLink" /> <% } else { %> <html:button
										value="Cerrar Sesi&oacute;n" property="Cancelar"
										styleClass="botonNoLink" onclick="enviar('CerrarSesion.do');" />
									<% } %>
								</span> <span class="derBoton"></span>
							</div>
							<div class="limpiadora"></div>
							<div class="espaciador"></div>
						</div>
						<% if (request.getAttribute("listaPerfiles") == null) { %>
						<p class="textoInferiorPortada">
							<a class="enlacePortada" href="publico.jsp">Rellenar Solicitud</a>
						</p>
						<% } %>
					</div>
					<% }}          
               if(tipoLogin.equals("certificado") || tipoLogin.equals("passcert")) { %>
					<div class="titulocajaformulario">Acceso al Sistema mediante
						Certificado Digital</div>
					<div>
						<fieldset class="normales">
							<html:link href="inicio.jsp?acceso=autcert">Entrar con Certificado</html:link>
						</fieldset>
					</div>
					<% } %>
				</html:form>
				<div>
					<html:javascript formName="OCAPLoginForm" />
					<br />
					<html:errors property="APP_ERROR_LOGIN" />
					<html:errors property="datosAccesoIncorrectos" />
				</div>
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
