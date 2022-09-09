
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>


<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript">

function generarPDF(){
   enviar('OCAPActas.do?accion=generarPDFActas&cActaId='+document.forms[0].CActaId.value);
}
</script>

<div class="contenido">
	<div class="contenidoAltaMC">
		<h2 class="tituloContenido">GENERACI&Oacute;N DE ACTAS</h2>

		<h3 class="subTituloContenido">&nbsp;</h3>

		<div class="lineaBajaM"></div>
		<div class="espaciador"></div>

		<html:form action="/OCAPActas.do">
			<html:hidden name="OCAPActasForm" property="CActaId" />
			<fieldset>
				<logic:equal name="tipoActa"
					value="<%=Constantes.ACTA_CONSTITUCION%>">
					<legend class="tituloLegend"> Acta de constituci&oacute;n
					</legend>
				</logic:equal>
				<logic:equal name="tipoActa" value="<%=Constantes.ACTA_REUNION%>">
					<legend class="tituloLegend"> Acta de reuni&oacute;n </legend>
				</logic:equal>
				<logic:equal name="tipoActa"
					value="<%=Constantes.ACTA_SOLI_ACLARACION%>">
					<legend class="tituloLegend"> Acta de solicitud de
						aclaraciones </legend>
				</logic:equal>

				<div class="cuadroFieldsetEv">
					<label for="idVTitulo">
						<p>
							Pulse el bot&oacute;n <span class="textoCursiva textoNegrita">Generar
								Acta</span> para obtener el acta en pdf.
					</label>
				</div>
			</fieldset>

			<div class="limpiadora"></div>
			<div class="espaciador"></div>

			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:generarPDF();"> <img
							src="imagenes/imagenes_ocap/flecha_correcto.gif"
							alt="Botón para generar el PDF del acta" /> <span>
								Generar Acta </span>
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

