<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<% String rutaVuelta = (String)request.getAttribute("rutaVuelta");%>
<bean:parameter id="errorRequest" name="error" value="" />

<div class="ocultarCampo">

	<div class="cuadroContenedor">
		<div class="titulocajaformulario">&nbsp;Errores</div>
		<div class="cajaformulario">
			<p class="mensajeAviso">
				<bean:message key="error.texto1" />
				&nbsp;
				<bean:message key="error.texto2" />
			</p>

			<html:errors />
			<html:messages id="men" message="true">
				<p class="mensajeAviso">
					<bean:write name="men" ignore="true" />
				</p>
			</html:messages>

			<div class="limpiadora"></div>
			<div class="espaciador"></div>
			<div class="botonAccion">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="<%=rutaVuelta%>"> <span><bean:message
								key="mensaje.volverAplicacion" /></span>
				</a>
				</span> <span class="derBoton"></span>
			</div>
			<div class="botonAccion">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="PaginaInicio.do"> <span> P&aacute;gina de inicio </span>
				</a>
				</span> <span class="derBoton"></span>
			</div>
			<div class="limpiadora"></div>
			<div class="espaciador"></div>
		</div>
	</div>

</div>
<!-- /Fin de ocultarCampo -->