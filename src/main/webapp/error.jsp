<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<div class="ocultarCampo">

	<jsp:include page="/top.jsp" flush="true" />
	<bean:parameter id="errorRequest" name="error" value="" />
	<div class="cuadroContenedor">
		<div class="titulocajaformulario">&nbsp;Errores</div>
		<div class="cajaformulario">
			<p class="mensajeAviso">
				<bean:message key="error.texto1" />
				&nbsp;
				<bean:message key="error.texto2" />
			</p>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<html:errors />
			<html:messages id="men" message="true">
				<p class="mensajeAviso">
					<bean:write name="men" ignore="true" />
				</p>
			</html:messages>

			<div class="limpiadora"></div>
			<div class="espaciador"></div>
			<div class="botonAccion colocarBotonLogout">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="javascript:history.back()"> <span><bean:message
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


	<jsp:include page="/bottom.jsp" flush="true" />

</div>
<!-- /Fin de ocultarCampo -->
