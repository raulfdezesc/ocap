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
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>


			<fieldset>
				<logic:equal name="opcion" value="<%=Constantes.IND_CTE%>">
					<legend class="tituloLegend"> Estad&iacute;sticas
						Comit&eacute T&eacute;cnico de Evaluaci&oacute;n: </legend>
				</logic:equal>
				<logic:equal name="opcion" value="<%=Constantes.IND_CE%>">
					<legend class="tituloLegend"> Estad&iacute;sticas
						Comisi&oacute;n de Evaluaci&oacute;n: </legend>
				</logic:equal>
				<logic:equal name="opcion" value="<%=Constantes.IND_EVALFQS%>">
					<legend class="tituloLegend"> Estad&iacute;sticas
						Evaluadores: </legend>
				</logic:equal>

				<logic:present name="sinDatos">
					<div class="textoCaracteres">
						<div class="espaciador"></div>
						<bean:message key="listado.noDatos" />
					</div>
				</logic:present>
				<div class="separador"></div>
				<logic:notPresent name="sinDatos">
					<label for="idVApell1"
						class="colocaDatosVis textoNegrita textoSubrayado">
						N&uacute;mero de evaluados en Fase III por la GRS: <span
						class="indexar negrita"><bean:write
								name="OCAPComponentesfqsForm" property="totalEvaluadosGRS" /></span>
					</label>
					<br />

					<label for="idVApell1"
						class="colocaDatosVis textoNegrita textoSubrayado">
						N&uacute;mero de evaluados en Fase III por la FQS: <span
						class="indexar negrita"><bean:write
								name="OCAPComponentesfqsForm" property="totalEvaluados" /></span>
					</label>
					<br />

					<label for="idVApell1" class="colocaDatosVis indexar">
						Evaluados OK (% sobre el total): <span class="negrita2"><bean:write
								name="OCAPComponentesfqsForm" property="totalIndicadoresOK" /></span>
						<span class="noMargin">(<bean:write
								name="OCAPComponentesfqsForm" property="porcIndicadoresOK" />%)
					</span>
					</label>
					<br />

					<label for="idVApell1" class="colocaDatosVis indexar">
						Evaluados KO (% sobre el total): <span class="negrita2"><bean:write
								name="OCAPComponentesfqsForm" property="totalIndicadoresKO" /></span>
						<span class="noMargin">(<bean:write
								name="OCAPComponentesfqsForm" property="porcIndicadoresKO" />%)
					</span>
					</label>
					<br />
					<div class="separador"></div>
					<logic:equal name="opcion" value="<%=Constantes.IND_EVALFQS%>">
						<label for="idVApell1"
							class="colocaDatosVis textoNegrita textoSubrayado">
							N&uacute;mero de evaluadores: <span class="indexar negrita"><bean:write
									name="OCAPComponentesfqsForm" property="totalIndicadores" /></span>
						</label>
						<br />
					</logic:equal>

					<div class="separador"></div>
					<!-- <logic:equal name="opcion" value="<%=Constantes.IND_EVALFQS%>" >   
                        <label class="textoNegrita" for="idVApell1"> N&uacute;mero de evaluados por cada evaluador (% sobre el total de evaluados) / OK (% sobre los que ha evaluado) / KO (% sobre los que ha evaluado)</label><br/> 
                     </logic:equal>                        
                     -->
					<logic:equal name="opcion" value="<%=Constantes.IND_ITIN%>">
						<label class="textoNegrita textoSubrayado" for="idVApell1">
							N&uacute;mero de evaluados por itinerario (% sobre el total de
							evaluados) / OK (% sobre los que ha evaluado) / KO (% sobre los
							que ha evaluado)</label>
						<br />
					</logic:equal>

					<logic:equal name="opcion" value="<%=Constantes.IND_GERE%>">
						<label class="textoNegrita textoSubrayado" for="idVApell1">
							N&uacute;mero de evaluados por gerencia (% sobre el total de
							evaluados) / OK (% sobre el total de Gerencia) / KO (% sobre el
							total de Gerencia)</label>
						<br />
					</logic:equal>

					<!-- <logic:iterate id="elemento" name="OCAPComponentesfqsForm" property="listadoAct">
                        <div class="indexar">
                        <logic:equal name="opcion" value="<%=Constantes.IND_EVALFQS%>" >
                           <label class="ajusteAncho"><bean:write name="elemento" property="DNombre" /> <bean:write name="elemento" property="DApellido1" />:</label>
                        </logic:equal>
                        <logic:equal name="opcion" value="<%=Constantes.IND_ITIN%>" >
                           <label class="ajusteAncho"><bean:write name="elemento" property="DDescripcion" />:</label>
                        </logic:equal>
                        <logic:equal name="opcion" value="<%=Constantes.IND_GERE%>" >
                           <label class="ajusteAncho"><bean:write name="elemento" property="DNombre" />:</label>
                        </logic:equal>                        
                        <span class="textoNegro"><span class="negrita"><bean:write name="elemento" property="totalIndicador" /></span>
                        (<bean:write name="elemento" property="porcIndicador" />%) /
                        <span class="negrita"><bean:write name="elemento" property="totalIndicadorOK" /></span>
                        (<bean:write name="elemento" property="porcIndicadorOK" />%) /                            
                        <span class="negrita"><bean:write name="elemento" property="totalIndicadorKO" /></span>
                        (<bean:write name="elemento" property="porcIndicadorKO" />%)</span>
                        <div class="separador2"></div>
                        </div>
                      </logic:iterate> -->
					<logic:equal name="opcion" value="<%=Constantes.IND_EVALFQS%>">
						<label class="textoNegrita textoSubrayado" for="idVApell1">Analizados,
							revisados y NO CONFORME con los cr&eacute;ditos de la
							autoevaluaci&oacute;n</label>
						<label class="textoNormal" for="idVApell1"> ( Total /
							Superan / No Superan ) </label>
						<br />
					</logic:equal>
					<logic:equal name="opcion" value="<%=Constantes.IND_CTE%>">
						<label class="textoNegrita textoSubrayado" for="idVApell1">
							CONFORME con el an&aacute;lisis</label>
						<label class="textoNormal" for="idVApell1"> ( Total /
							Superan / No Superan ) </label>
						<br />
					</logic:equal>
					<logic:equal name="opcion" value="<%=Constantes.IND_CE%>">
						<label class="textoNegrita textoSubrayado" for="idVApell1">
							RATIFICA el resultado de la evaluaci&oacute;n</label>
						<label class="textoNormal" for="idVApell1"> ( Total /
							Superan / No Superan ) </label>
						<br />
					</logic:equal>
					<logic:present name="sinDatos">
						<div class="textoCaracteres">
							<div class="espaciador"></div>
							<bean:message key="listado.noDatos" />
						</div>
					</logic:present>
					<logic:notPresent name="sinDatosMod">
						<logic:iterate id="elemento" name="OCAPComponentesfqsForm"
							property="listadoActMod">

							<div class="indexar">
								<logic:equal name="opcion" value="<%=Constantes.IND_CE%>">
									<label class="ajusteAncho"><bean:write name="elemento"
											property="DNombre" /> :</label>
								</logic:equal>
								<logic:equal name="opcion" value="<%=Constantes.IND_CTE%>">
									<label class="ajusteAncho"><bean:write name="elemento"
											property="DNombre" /> :</label>
								</logic:equal>
								<logic:equal name="opcion" value="<%=Constantes.IND_EVALFQS%>">
									<label class="ajusteAncho"><bean:write name="elemento"
											property="DNombre" /> <bean:write name="elemento"
											property="DApellido1" />:</label>
								</logic:equal>
								<logic:equal name="opcion" value="<%=Constantes.IND_ITIN%>">
									<label class="ajusteAncho"><bean:write name="elemento"
											property="DDescripcion" />:</label>
								</logic:equal>
								<logic:equal name="opcion" value="<%=Constantes.IND_GERE%>">
									<label class="ajusteAncho"><bean:write name="elemento"
											property="DNombre" />:</label>
								</logic:equal>
								<span class="textoNegro"><span class="negrita"><bean:write
											name="elemento" property="totalIndicadorMod" /></span> / <span
									class="negrita"><bean:write name="elemento"
											property="totalIndicadorModOK" /></span> (<bean:write
										name="elemento" property="porcIndicadorModOK" />%) / <span
									class="negrita"><bean:write name="elemento"
											property="totalIndicadorModKO" /></span> (<bean:write
										name="elemento" property="porcIndicadorModKO" />%)</span>
								<div class="separador2"></div>
							</div>

						</logic:iterate>
					</logic:notPresent>
					<logic:equal name="opcion" value="<%=Constantes.IND_EVALFQS%>">
						<label class="textoNegrita textoSubrayado" for="idVApell1">
							Analizados, revisados y CONFORME con los cr&eacute;ditos de la
							autoevaluaci&oacute;n</label>
						<label class="textoNormal" for="idVApell1"> ( Total /
							Superan / No Superan ) </label>
						<br />
					</logic:equal>
					<logic:equal name="opcion" value="<%=Constantes.IND_CTE%>">
						<label class="textoNegrita textoSubrayado" for="idVApell1">
							NO CONFORME con el an&aacute;lisis</label>
						<label class="textoNormal" for="idVApell1"> ( Total /
							Superan / No Superan ) </label>
						<br />
					</logic:equal>
					<logic:equal name="opcion" value="<%=Constantes.IND_CE%>">
						<label class="textoNegrita textoSubrayado" for="idVApell1">
							SOLICITA REVISI&Oacute;N de la evaluaci&oacute;n</label>
						<label class="textoNormal" for="idVApell1"> ( Total /
							Superan / No Superan ) </label>
						<br />
					</logic:equal>
					<logic:present name="sinDatosNoMod">
						<div>

							<bean:message key="listado.noDatos" />
						</div>
					</logic:present>
					<logic:notPresent name="sinDatosNoMod">
						<logic:iterate id="elemento" name="OCAPComponentesfqsForm"
							property="listadoActNoMod">

							<div class="indexar">
								<logic:equal name="opcion" value="<%=Constantes.IND_CE%>">
									<label class="ajusteAncho"><bean:write name="elemento"
											property="DNombre" /> :</label>
								</logic:equal>
								<logic:equal name="opcion" value="<%=Constantes.IND_CTE%>">
									<label class="ajusteAncho"><bean:write name="elemento"
											property="DNombre" /> :</label>
								</logic:equal>
								<logic:equal name="opcion" value="<%=Constantes.IND_EVALFQS%>">
									<label class="ajusteAncho"><bean:write name="elemento"
											property="DNombre" /> <bean:write name="elemento"
											property="DApellido1" />:</label>
								</logic:equal>
								<logic:equal name="opcion" value="<%=Constantes.IND_ITIN%>">
									<label class="ajusteAncho"><bean:write name="elemento"
											property="DDescripcion" />:</label>
								</logic:equal>
								<logic:equal name="opcion" value="<%=Constantes.IND_GERE%>">
									<label class="ajusteAncho"><bean:write name="elemento"
											property="DNombre" />:</label>
								</logic:equal>
								<span class="textoNegro"><span class="negrita"><bean:write
											name="elemento" property="totalIndicadorNoMod" /></span> / <span
									class="negrita"><bean:write name="elemento"
											property="totalIndicadorNoModOK" /></span> (<bean:write
										name="elemento" property="porcIndicadorNoModOK" />%) / <span
									class="negrita"><bean:write name="elemento"
											property="totalIndicadorNoModKO" /></span> (<bean:write
										name="elemento" property="porcIndicadorNoModKO" />%)</span>
								<div class="separador2"></div>
							</div>

						</logic:iterate>
					</logic:notPresent>
				</logic:notPresent>
			</fieldset>
		</html:form>
	</div>
</div>
<!-- /Fin de ocultarcampo -->

