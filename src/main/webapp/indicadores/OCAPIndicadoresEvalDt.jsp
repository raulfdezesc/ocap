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
						relacionadas con Evaluadores: </legend>
					<logic:present name="sinDatos">
						<div class="textoCaracteres">
							<div class="espaciador"></div>
							<bean:message key="listado.noDatos" />
						</div>
					</logic:present>

					<logic:notPresent name="sinDatos">
						<label class="espacioLabel"> <span
							class="textoNegrita textoSubrayado"> N&uacute;mero de
								evaluadores de FQS con asignaci&oacute;n de evaluados:</span> <!-- N&uacute;mero de evaluadores de FQS con asignaci&oacute;n de Evaluados para la evaluaci&oacute;n del Desempe&ntilde;o de la Competencia (EDC): -->
							<span><bean:write name="OCAPComponentesfqsForm"
									property="totalEvaluadoresFQS" /></span>
						</label>

						<div class="espacioLabel">
							<label> <span class="textoNegrita textoSubrayado">
									N&uacute;mero de evaluadores dados de baja sin realizar
									evaluaciones</span>
							</label>&nbsp;(porcentaje sobre el total): <label> <span><bean:write
										name="OCAPComponentesfqsForm"
										property="totalEvaluadoresBajaFQS" /></span>
							</label>&nbsp;<span>(<bean:write name="OCAPComponentesfqsForm"
									property="porcEvaluadoresBajaFQS" />)
							</span>
						</div>

						<div class="espacioLabel">
							<label> <span class="textoNegrita textoSubrayado">
									N&uacute;mero de evaluadores que finalizaron sus evaluaciones</span>
							</label>&nbsp;(porcentaje sobre el total): <label> <span><bean:write
										name="OCAPComponentesfqsForm"
										property="totalEvaluadoresFinFQS" /></span>
							</label>&nbsp;<span class="noMargin">(<bean:write
									name="OCAPComponentesfqsForm" property="porcEvaluadoresFinFQS" />)
							</span>

							<div class="subCategoria">
								<bean:size id="numEstatutarios" name="OCAPComponentesfqsForm"
									property="listaCategorias" />
								<logic:equal name="numEstatutarios" value="0">
									<label class="espacioLabel negrita textoSubrayado">No
										hay resultados por categor&iacute;as.</label>
								</logic:equal>
								<logic:notEqual name="numEstatutarios" value="0">
									<label class="espacioLabel negrita textoSubrayado">Resultados
										por Categor&iacute;as:</label>
								</logic:notEqual>

								<logic:iterate id="elemento" name="OCAPComponentesfqsForm"
									property="listaCategorias">
									<div class="subCategoria espacioLabel">
										<span class="negrita textoSubrayado"><bean:write
												name="elemento" property="DEstatut_nombre" /></span>
										<div class="subCategoria espacioLabel">
											<span class="negrita">Evaluadores (porcentaje sobre el
												total): </span>
											<bean:write name="elemento" property="totalIndicadores" />
											(
											<bean:write name="elemento" property="porcentajeIndicadores" />
											)<br />
										</div>
										<logic:iterate id="elemento2" name="elemento"
											property="listaCategorias">
											<div class="subCategoria espacioLabel">
												<span class="indexar negrita">- <bean:write
														name="elemento2" property="DProfesional_nombre" /></span><br />
												<div class="subCategoria">
													<span>Evaluadores: </span>
													<bean:write name="elemento2" property="totalIndicadores" />
													(
													<bean:write name="elemento2"
														property="porcentajeIndicadores" />
													)<br /> <span>Evaluados: </span>
													<bean:write name="elemento2" property="totalIndicador" />
													<br /> <span>OK: </span>
													<bean:write name="elemento2" property="totalIndicadorOK" />
													(
													<bean:write name="elemento2"
														property="porcentajeIndicadoresOK" />
													)<br /> <span>KO: </span>
													<bean:write name="elemento2" property="totalIndicadorKO" />
													(
													<bean:write name="elemento2"
														property="porcentajeIndicadoresKO" />
													)<br /> <span>Conforme: </span>
													<bean:write name="elemento2"
														property="totalIndicadoresModOK" />
													(
													<bean:write name="elemento2"
														property="porcentajeIndicadoresModOK" />
													)<br /> <span>No Conforme: </span>
													<bean:write name="elemento2"
														property="totalIndicadoresModKO" />
													(
													<bean:write name="elemento2"
														property="porcentajeIndicadoresModKO" />
													)<br />
												</div>
											</div>
										</logic:iterate>
									</div>
								</logic:iterate>
							</div>

							<div class="subCategoria espacioLabel">

								<bean:size id="numEvaluadores" name="OCAPComponentesfqsForm"
									property="listaEvaluadores" />
								<logic:equal name="numEvaluadores" value="0">
									<label class="textoSubrayado">No hay resultados por
										evaluadores.</label>
								</logic:equal>
								<logic:notEqual name="numEvaluadores" value="0">
									<label class="negrita textoSubrayado">Resultados por
										Evaluadores:</label>
								</logic:notEqual>
								<logic:iterate id="elemento" name="OCAPComponentesfqsForm"
									property="listaEvaluadores">
									<div class="subCategoria espacioLabel">
										<span class="negrita textoSubrayado"><bean:write
												name="elemento" property="codigoId" /></span><br />
										<logic:iterate id="elemento2" name="elemento"
											property="listaCategorias">
											<div class="subCategoria espacioLabel">
												<span class="indexar negrita">- <bean:write
														name="elemento2" property="DItinerario" /></span><br />
												<div class="subCategoria">
													<span>Evaluados: </span>
													<bean:write name="elemento2" property="totalIndicadores" />
													(
													<bean:write name="elemento2"
														property="porcentajeIndicadores" />
													)<br /> <span>OK: </span>
													<bean:write name="elemento2" property="totalIndicadoresOK" />
													(
													<bean:write name="elemento2"
														property="porcentajeIndicadoresOK" />
													)<br /> <span>KO: </span>
													<bean:write name="elemento2" property="totalIndicadoresKO" />
													(
													<bean:write name="elemento2"
														property="porcentajeIndicadoresKO" />
													)<br /> <span>Conforme: </span>
													<bean:write name="elemento2"
														property="totalIndicadoresModOK" />
													(
													<bean:write name="elemento2"
														property="porcentajeIndicadoresModOK" />
													)<br /> <span>No Conforme: </span>
													<bean:write name="elemento2"
														property="totalIndicadoresModKO" />
													(
													<bean:write name="elemento2"
														property="porcentajeIndicadoresModKO" />
													)<br />
												</div>
											</div>
										</logic:iterate>
									</div>
								</logic:iterate>
							</div>
						</div>

						<div class="espacioLabel">
							<label class="negrita textoSubrayado">Resultados
								Generales:</label>
							<div class="espacioLabel subCategoria">
								<div>
									<label class="textoNegrita"> Total analizados y
										revisados CONFORMES:</label> <span><bean:write
											name="OCAPComponentesfqsForm" property="totalConformes" /></span>
								</div>
								<div>
									<label class="textoNegrita"> Total analizados y
										revisados NO CONFORMES:</label> <span><bean:write
											name="OCAPComponentesfqsForm" property="totalNoConformes" /></span>
								</div>
								<div>
									<label class="textoNegrita"> Total auditor&iacute;as:</label> <span><bean:write
											name="OCAPComponentesfqsForm" property="totalAuditorias" /></span>
								</div>
							</div>
						</div>

						<div class="espacioLabel">
							<bean:size id="numCategoriasAuditoria"
								name="OCAPComponentesfqsForm" property="listaAuditorias" />
							<logic:equal name="numCategoriasAuditoria" value="0">
								<label class="indexar">No hay datos por
									Categor&iacute;as.</label>
							</logic:equal>
							<logic:notEqual name="numCategoriasAuditoria" value="0">
								<label class="textoSubrayado negrita">Auditor&iacute;as
									por Categor&iacute;as:</label>
								<br />
							</logic:notEqual>
							<logic:iterate id="elemento" name="OCAPComponentesfqsForm"
								property="listaAuditorias">
								<div class="espacioLabel subCategoria">
									<span class="negrita"><bean:write name="elemento"
											property="DProfesional_nombre" />: </span><span class="indexar"><bean:write
											name="elemento" property="totalIndicadores" /> (<bean:write
											name="elemento" property="porcentajeIndicadores" />)</span><br />
								</div>
							</logic:iterate>
						</div>
					</logic:notPresent>
				</fieldset>
			</div>
		</html:form>
	</div>
</div>

