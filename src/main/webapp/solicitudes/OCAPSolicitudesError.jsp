<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<% String rutaVuelta = (String)request.getAttribute("rutaVuelta");%>
<bean:parameter id="errorRequest" name="error" value="" />

<div class="ocultarCampo">

	<div class="cuadroContenedor">
		<div class="titulocajaformulario">&nbsp;Solicitud anulada</div>
		<div class="cajaformulario">
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
					href="<%=rutaVuelta%>"> <img
						src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" /> <span>Volver
					</span>
				</a>
				</span> <span class="derBoton"></span>
			</div>
			<div class="limpiadora"></div>
			<div class="espaciador"></div>
		</div>
	</div>

</div>
<!-- /Fin de ocultarCampo -->