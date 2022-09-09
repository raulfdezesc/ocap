<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>


<div class="contenido contenidoFaseIII">
	<div class="contenidoListadoAspirantesGCP">

		<html:form action="/OCAPIndicadores.do">
			<h3 class="subTituloContenido">Estad&iacute;sticas de Evaluados</h3>
			<div class="indicadores">
				<fieldset>
					<legend class="tituloLegend"> Estad&iacute;sticas de
						Secretar&iacute;a T&eacute;cnica: </legend>

					<logic:present name="sinDatos">
						<div class="textoCaracteres">
							<div class="espaciador"></div>
							<bean:message key="listado.noDatos" />
						</div>
					</logic:present>
					<div class="espacioLabel">
						<logic:notPresent name="sinDatos">
							<label> <span class="textoNegrita textoSubrayado">
									N&uacute;mero de Informes FQS enviados a GRS:</span> <span><bean:write
										name="OCAPComponentesfqsForm" property="totalIndicador" /></span>
							</label>
						</logic:notPresent>
					</div>
				</fieldset>
			</div>
		</html:form>
	</div>
</div>
<!-- /Fin de ocultarcampo -->

