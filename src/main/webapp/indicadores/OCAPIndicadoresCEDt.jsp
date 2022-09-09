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
					<legend class="tituloLegend"> Estad&iacute;sticas
						relacionadas con la CE: </legend>

					<logic:present name="sinDatos">
						<div class="textoCaracteres">
							<div class="espaciador"></div>
							<bean:message key="listado.noDatos" />
						</div>
					</logic:present>
					<div class="separador"></div>
					<logic:notPresent name="sinDatos">
						<div class="espacioLabel">
							<label><span class="textoNegrita textoSubrayado">
									Informes de Certificaci&oacute;n remitidos a FQS:</span> <span><bean:write
										name="OCAPComponentesfqsForm" property="totalIndicadores" /></span>
							</label>
						</div>
						<div class="espacioLabel">
							<label><span class="textoNegrita textoSubrayado">
									Informes de Certificaci&oacute;n Positivos y remitidos a FQS:</span> <span><bean:write
										name="OCAPComponentesfqsForm" property="totalConformes" /></span>&nbsp;<span
								class="noMargin">(<bean:write
										name="OCAPComponentesfqsForm" property="porcConformes" />)
							</span> </label>
						</div>
						<div class="espacioLabel subCategoria">
							<bean:size id="numCTEConformes" name="OCAPComponentesfqsForm"
								property="listaCTE" />
							<logic:equal name="numCTEConformes" value="0">
								<label>No hay datos por CTEs.</label>
							</logic:equal>
							<logic:iterate id="elemento" name="OCAPComponentesfqsForm"
								property="listaCTE">
								<span class="negrita"><bean:write name="elemento"
										property="DNombreCte" />: </span>
								<span><bean:write name="elemento"
										property="totalIndicadores" /></span> (<bean:write name="elemento"
									property="porcentajeIndicadores" />)<br />
							</logic:iterate>
						</div>

						<div class="espacioLabel">
							<label><span class="textoNegrita textoSubrayado">
									Informes de Certificaci&oacute;n Negativos y remitidos a FQS:</span> <span><bean:write
										name="OCAPComponentesfqsForm" property="totalNoConformes" /></span>&nbsp;<span
								class="noMargin">(<bean:write
										name="OCAPComponentesfqsForm" property="porcNoConformes" />)
							</span> </label>

							<div class="espacioLabel subCategoria">
								<bean:size id="numCTENoConformes" name="OCAPComponentesfqsForm"
									property="listaCTE2" />
								<logic:equal name="numCTENoConformes" value="0">
									<label>No hay datos por CTEs.</label>
								</logic:equal>
								<logic:iterate id="elemento" name="OCAPComponentesfqsForm"
									property="listaCTE2">
									<span class="negrita"><bean:write name="elemento"
											property="DNombreCte" />: </span>
									<span><bean:write name="elemento"
											property="totalIndicadores" /></span> (<bean:write name="elemento"
										property="porcentajeIndicadores" />)<br />
								</logic:iterate>
							</div>
						</div>
					</logic:notPresent>
				</fieldset>
			</div>
		</html:form>
	</div>
</div>
<!-- /Fin de ocultarcampo -->

