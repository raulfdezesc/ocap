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
						relacionadas con Profesionales: </legend>
					<logic:present name="sinDatos">
						<div class="textoCaracteres">
							<div class="espaciador"></div>
							<bean:message key="listado.noDatos" />
						</div>
					</logic:present>

					<logic:notPresent name="sinDatos">
						<label class="espacioLabel"> <span
							class="textoNegrita textoSubrayado"> N&uacute;mero total
								de Profesionales remitidos por la GRS para inicio de EDC:</span> <span
							class="indexar"><bean:write name="OCAPComponentesfqsForm"
									property="totalEvaluadosEnFaseIII" /></span>
						</label>

						<label class="espacioLabel"> <span
							class="textoNegrita textoSubrayado"> N&uacute;mero de
								Profesionales excluidos en Fase III:</span> <span class="indexar"><bean:write
									name="OCAPComponentesfqsForm"
									property="totalEvaluadosExcluidos" /></span>
						</label>

						<div class="espacioLabel">
							<label> <span class="textoNegrita textoSubrayado">
									N&uacute;mero de evaluados en fase III por &oacute;rganos de
									evaluaci&oacute;n de la FQS </span>
							</label>&nbsp;(porcentaje sobre el total): <label> <span><bean:write
										name="OCAPComponentesfqsForm" property="totalEvaluados" /></span>
							</label>&nbsp;<span>(<bean:write name="OCAPComponentesfqsForm"
									property="porcEvaluadosFQS" />)
							</span>

							<div class="subCategoria espacioSuperior">
								<label class="indexar negrita"> Evaluados OK (% sobre el
									total): <span><bean:write name="OCAPComponentesfqsForm"
											property="totalIndicadoresOK" /></span> <span>(<bean:write
											name="OCAPComponentesfqsForm" property="porcEvaluadosFQSOK" />)
								</span>
								</label> <br /> <label class="indexar negrita"> Evaluados KO (%
									sobre el total): <span><bean:write
											name="OCAPComponentesfqsForm" property="totalIndicadoresKO" /></span>
									<span>(<bean:write name="OCAPComponentesfqsForm"
											property="porcEvaluadosFQSKO" />)
								</span>
								</label>
							</div>
							<bean:size id="numCategoriasFQS" name="OCAPComponentesfqsForm"
								property="listaFQS" />
							<logic:equal name="numCategoriasFQS" value="0">
								<label class="indexar">No hay datos por
									Categor&iacute;as.</label>
							</logic:equal>
						</div>
						<div class="subCategoria">
							<logic:notEqual name="numCategoriasFQS" value="0">
								<label class="espacioLabel textoSubrayado indexar negrita">Resultados
									por Categor&iacute;as:</label>
							</logic:notEqual>

							<logic:iterate id="elemento" name="OCAPComponentesfqsForm"
								property="listaFQS">
								<div class="espacioLabel subCategoria">
									<span class="indexar negrita"><bean:write
											name="elemento" property="DProfesional_nombre" /></span><br />
									<div class="subCategoria">
										<span>Evaluados: </span>
										<bean:write name="elemento" property="totalIndicadores" />
										(
										<bean:write name="elemento" property="porcentajeIndicadores" />
										)<br /> <span>OK: </span>
										<bean:write name="elemento" property="totalIndicadoresOK" />
										(
										<bean:write name="elemento" property="porcentajeIndicadoresOK" />
										)<br /> <span>KO: </span>
										<bean:write name="elemento" property="totalIndicadoresKO" />
										(
										<bean:write name="elemento" property="porcentajeIndicadoresKO" />
										)<br />
									</div>
								</div>
							</logic:iterate>
						</div>
						<div class="espacioLabel">
							<label> <span class="textoNegrita textoSubrayado">
									N&uacute;mero de evaluados en fase III por la Comisi&oacute;n
									Central de la GRS</span>
							</label>&nbsp;(porcentaje sobre el total): <label> <span><bean:write
										name="OCAPComponentesfqsForm" property="totalEvaluadosGRS" /></span>
							</label>&nbsp;<span>(<bean:write name="OCAPComponentesfqsForm"
									property="porcEvaluadosGRS" />)
							</span>
							<div class="subCategoria espacioSuperior">
								<label class="negrita"> Evaluados OK (% sobre el total):
									<span><bean:write name="OCAPComponentesfqsForm"
											property="totalIndicadorOK" /></span> <span>(<bean:write
											name="OCAPComponentesfqsForm" property="porcEvaluadosGRSOK" />)
								</span>
								</label> <br /> <label class="negrita"> Evaluados KO (% sobre el
									total): <span><bean:write name="OCAPComponentesfqsForm"
											property="totalIndicadorKO" /></span> <span>(<bean:write
											name="OCAPComponentesfqsForm" property="porcEvaluadosGRSKO" />)
								</span>
								</label>

								<bean:size id="numCategoriasGRS" name="OCAPComponentesfqsForm"
									property="listaGRS" />
								<logic:equal name="numCategoriasGRS" value="0">
									<label class="indexar">No hay datos por
										Categor&iacute;as.</label>
								</logic:equal>
							</div>
						</div>
						<div class="subCategoria">
							<logic:notEqual name="numCategoriasGRS" value="0">
								<label class="espacioLabel textoSubrayado negrita">Resultados
									por Categor&iacute;as:</label>
							</logic:notEqual>

							<logic:iterate id="elemento" name="OCAPComponentesfqsForm"
								property="listaGRS">
								<div class="espacioLabel subCategoria">
									<span class="indexar negrita"><bean:write
											name="elemento" property="DProfesional_nombre" /></span><br />
									<div class="subCategoria">
										<span class="indexarMas">Evaluados: </span>
										<bean:write name="elemento" property="totalIndicadores" />
										(
										<bean:write name="elemento" property="porcentajeIndicadores" />
										)<br /> <span class="indexarMas">OK: </span>
										<bean:write name="elemento" property="totalIndicadoresOK" />
										(
										<bean:write name="elemento" property="porcentajeIndicadoresOK" />
										)<br /> <span class="indexarMas">KO: </span>
										<bean:write name="elemento" property="totalIndicadoresKO" />
										(
										<bean:write name="elemento" property="porcentajeIndicadoresKO" />
										)<br />
									</div>
								</div>
							</logic:iterate>
						</div>
					</logic:notPresent>
				</fieldset>
			</div>
			<%-- indicadores --%>
		</html:form>
	</div>
</div>
