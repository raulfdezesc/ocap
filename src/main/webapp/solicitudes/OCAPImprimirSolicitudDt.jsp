<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ taglib uri="html.tld" prefix="html"%>

<script type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"
	charset="windows-1252"></script>
<script type="text/javascript">

function generarPDF() {
   enviar('OCAPNuevaSolicitud.do?accion=generarPDFSolic&cSolicitudId='+document.forms[0].CSolicitudId.value);
}

function cerrar() {
   enviar('CerrarSesion.do');
}

</script>

<div class="contenido">
	<div class="contenidoAltaMC">
		<h2 class="tituloContenido">SOLICITUD DE ACCESO A GRADO DE
			CARRERA PROFESIONAL</h2>

		<h3 class="subTituloContenido">&nbsp;</h3>

		<div class="lineaBajaM"></div>
		<div class="espaciador"></div>

		<html:form action="/OCAPNuevaSolicitud.do">
			<html:hidden name="OCAPNuevaSolicitudForm" property="CSolicitudId" />
			<fieldset>
				<legend class="tituloLegend"> Generaci&oacute;n de
					Solicitud </legend>
				<div class="cuadroFieldsetEv">
					<label for="idVTitulo">
						<p>
							A continuaci&oacute;n, deber&aacute; pulsar el bot&oacute;n <span
								class="textoCursiva textoNegrita">Generar Solicitud</span> para
							obtener su solicitud en pdf. <br />
							<br /> Posteriormente deber&aacute; imprimir el documento
							generado y entregarlo en cualquier Registro Oficial. <br />
							<br /> Finalmente, si su solicitud se ha generado correctamente,
							pulse <span class="textoCursiva textoNegrita">Cerrar</span>.
						</p>
					</label>
				</div>
			</fieldset>

			<div class="limpiadora"></div>
			<div class="espaciador"></div>

			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:cerrar();"> <img
							src="imagenes/imagenes_ocap/aspa_roja.gif"
							alt="Bot&oacute;n para generar su solicitud en PDF" /> <span>
								Cerrar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:generarPDF();"> <img
							src="imagenes/imagenes_ocap/flecha_correcto.gif"
							alt="Bot&oacute;n para generar su solicitud en PDF" /> <span>
								Generar Solicitud </span>
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
