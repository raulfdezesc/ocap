<%@ taglib uri="bean.tld" prefix="bean"%>

<div class="ocultarCampo">

	<jsp:include page="/top.jsp" />

	<div class="cuadroContenedor">
		<div class="titulocajaformulario">Informaci&oacute;n</div>
		<div class="cajaformulario">
			<p class="mensajeAviso">
				<bean:write name="mensaje" ignore="true" />
			</p>
			<div class="espaciador"></div>
			<div class="botonAccion">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="javascript:history.back()"> <span><bean:message
								key="mensaje.volverAplicacion" /></span>
				</a>
				</span> <span class="derBoton"></span>
			</div>
			<div class="botonAccion">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="PaginaInicio.do"> <span>P&aacute;gina de inicio</span>
				</a>
				</span> <span class="derBoton"></span>
			</div>
			<br />&nbsp;<br />&nbsp;<br />
		</div>
	</div>

	<jsp:include page="/bottom.jsp" flush="true" />

</div>
<!-- /Fin de ocultarCampo -->
