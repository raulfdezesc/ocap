<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="java.util.StringTokenizer"%>


<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>

<script language="javaScript" src="javascript/comun.js"></script>

<% String rutaVuelta = (String)request.getAttribute("rutaVuelta");%>

<div class="ocultarCampo">

	<div class="cuadroContenedorConsultas">
		<div class="titulocajaformulario">
			<bean:message key="general.exitoTit" />
		</div>
		<div class="cajaformulario">
			<p class="mensajeAviso">
				<bean:message key="general.exito" />
			</p>
			<html:form action="<%=rutaVuelta%>">
				<br>
				<div class="espaciador"></div>
				<div class="limpiadora"></div>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <input
						type="submit" name="boton" class="formBoton"
						value="<bean:message key="button.volver"/>">
					</span> <span class="derBoton"></span>
				</div>
				<div class="espaciador"></div>
				<div class="limpiadora"></div>
				<br>
				<br>
			</html:form>
		</div>
	</div>

</div>
<!-- /Fin de ocultarCampo -->
