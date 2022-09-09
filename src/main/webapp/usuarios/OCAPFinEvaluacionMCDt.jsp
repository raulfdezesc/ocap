<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ taglib uri="html.tld" prefix="html"%>

<div class="contenido">
	<div class="contenidoAltaMC">
		<h2 class="tituloContenido">FINAL DE AUTOEVALUACI&Oacute;N DE
			M&Eacute;RITOS CURRICULARES</h2>
		<h3 class="subTituloContenido">&nbsp;</h3>
		<div class="lineaBajaM"></div>
		<div class="espaciador"></div>
		<html:form action="/OCAPUsuarios.do?accion=verCV&marcaAgua=N">
			<fieldset>
				<legend class="tituloLegend"> Fin de Auto-evaluaci&oacute;n
				</legend>
				<div class="cuadroFieldsetEv">
					<label for="idVTitulo">
						<p>
							Para finalizar el procedimiento de Auto-evaluaci&oacute;n de
							M&eacute;ritos Curriculares deber&aacute; pulsar el bot&oacute;n
							<span class="textoCursiva textoNegrita">Generar documento
								de CV</span> para general el documento CV. <br />
							<br /> Posteriormente deber&aacute; imprimirlo y entregarlo en
							cualquier registro oficial. Este registro se encargar&aacute; de
							enviar el documento al Punto de Gesti&oacute;n Perif&eacute;rica
							correspondiente.
						</p>
					</label>
				</div>
			</fieldset>
			<div class="limpiadora"></div>
			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <input
						type="submit" name="accionBoton" tabindex="17"
						value="Generar documento de CV"
						alt="Bot&oacute;n para generar su CV" />
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
