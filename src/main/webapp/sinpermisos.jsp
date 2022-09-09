<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>

<div class="ocultarCampo">

	<jsp:include page="/top.jsp" flush="true" />

	<div class="cuadroContenedor">
		<div class="titulocajaformulario">&nbsp;Sin Permisos</div>
		<div class="cajaformulario">
			<p class="mensajeAviso">
				<bean:message key="mensaje.sinPermisos" />
			</p>
			<div class="espaciador"></div>
			<div class="limpiadora"></div>
			<div class="botonAccion">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="javascript:history.back()"> <span><bean:message
								key="mensaje.volverAplicacion" /></span>
				</a>
				</span> <span class="derBoton"></span>
			</div>
			<div class="espaciador"></div>
			<div class="limpiadora"></div>
		</div>
	</div>

	<jsp:include page="/bottom.jsp" flush="true" />
	<script>
  alert("No tiene permisos para realizar la acci\u00f3n solicitada");
</script>

</div>
<!-- /Fin de ocultarCampo -->
