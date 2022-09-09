
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<script language="javascript">
function verificarPuestoNoVacio(){
   if (document.forms[0].APuesto.value == ''){
      alert('No puede dejar el campo vac\u00EDo.');
      return false;
   } else return true;
}
</script>

<div class="contenido">
	<div class="contenidoAltaMC">
		<h2 class="tituloContenido">M&Eacute;RITOS CURRICULARES</h2>

		<h3 class="subTituloContenido">&nbsp;</h3>

		<div class="lineaBajaM"></div>
		<div class="espaciador"></div>

		<html:form action="/OCAPUsuarios.do?accion=guardarPuesto"
			onsubmit="return verificarPuestoNoVacio();">
			<fieldset>
				<legend class="tituloLegend"> Unidad de Adscripci&oacute;n
					/ Servicio actual </legend>
				<div class="cuadroFieldsetEv">
					<label for="idVTitulo">
						<p>Escriba a continuaci&oacute;n la Unidad de
							Adscripci&oacute;n Hospitalaria o Servicio en el que est&aacute;
							trabajando actualmente.</p> <br /> <!-- <p>: </p> --> <html:text
							name="OCAPUsuariosForm" property="APuesto" maxlength="250"
							styleClass="cajaPuesto" /> <html:hidden name="OCAPUsuariosForm"
							property="CExpId" />
					</label>
				</div>
			</fieldset>

			<div class="limpiadora"></div>

			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <input
						type="submit" name="accionBoton" tabindex="17" value="Continuar"
						alt="Guardar datos y continuar" />
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

