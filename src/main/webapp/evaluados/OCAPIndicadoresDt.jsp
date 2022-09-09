<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>


<div class="contenido contenidoFaseIII">
	<div class="contenidoListadoAspirantesGCP">

		<html:form action="/OCAPEvaluadores.do">
			<h3 class="subTituloContenido">Estad&iacute;sticas de Evaluados</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>


			<fieldset>
				<legend class="tituloLegend"> Estad&iacute;sticas: </legend>

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
						N&uacute;mero de evaluados en Fase III: <span
						class="indexar negrita"><bean:write
								name="OCAPComponentesfqsForm" property="totalEvaluados" /></span>
					</label>
					<br />

					<label for="idVApell1" class="colocaDatosVis indexar">
						Evaluados OK(% sobre el total): <span class="negrita2"><bean:write
								name="OCAPComponentesfqsForm" property="totalIndicadoresOK" /></span>
						<span class="noMargin">(<bean:write
								name="OCAPComponentesfqsForm" property="porcIndicadoresOK" />%)
					</span>
					</label>
					<br />

					<label for="idVApell1" class="colocaDatosVis indexar">
						Evaluados KO(% sobre el total): <span class="negrita2"><bean:write
								name="OCAPComponentesfqsForm" property="totalIndicadoresKO" /></span>
						<span class="noMargin">(<bean:write
								name="OCAPComponentesfqsForm" property="porcIndicadoresKO" />%)
					</span>
					</label>
					<br />
					<div class="separador"></div>
					<logic:equal name="opcion" value="<%=Constantes.IND_EVAL%>">
						<label for="idVApell1"
							class="colocaDatosVis textoNegrita textoSubrayado">
							N&uacute;mero de evaluadores: <span class="indexar negrita"><bean:write
									name="OCAPComponentesfqsForm" property="totalIndicadores" /></span>
						</label>
						<br />
					</logic:equal>
					<div class="separador"></div>
					<logic:equal name="opcion" value="<%=Constantes.IND_EVAL%>">
						<label class="textoNegrita textoSubrayado" for="idVApell1">
							N&uacute;mero de evaluados por Evaluador (% sobre el total) / OK
							(% sobre los que ha evaluado) / KO (% sobre los que ha evaluado)</label>
						<br />
					</logic:equal>

					<logic:equal name="opcion" value="<%=Constantes.IND_ITIN%>">
						<label class="textoNegrita textoSubrayado" for="idVApell1">
							N&uacute;mero de evaluados por Itinerario (% sobre el total) / OK
							(% sobre los que ha evaluado) / KO (% sobre los que ha evaluado)</label>
						<br />
					</logic:equal>

					<logic:equal name="opcion" value="<%=Constantes.IND_GERE%>">
						<label class="textoNegrita textoSubrayado" for="idVApell1">
							N&uacute;mero de evaluados por Gerencia (% sobre el total) / OK
							(% sobre el total de Gerencia) / KO (% sobre el total de
							Gerencia)</label>
						<br />
					</logic:equal>

					<logic:iterate id="elemento" name="OCAPComponentesfqsForm"
						property="listadoAct">
						<div class="indexar">
							<logic:equal name="opcion" value="<%=Constantes.IND_EVAL%>">
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
										name="elemento" property="totalIndicador" /></span> (<bean:write
									name="elemento" property="porcIndicador" />%) / <span
								class="negrita"><bean:write name="elemento"
										property="totalIndicadorOK" /></span> (<bean:write name="elemento"
									property="porcIndicadorOK" />%) / <span class="negrita"><bean:write
										name="elemento" property="totalIndicadorKO" /></span> (<bean:write
									name="elemento" property="porcIndicadorKO" />%)</span>
							<div class="separador2"></div>
						</div>
					</logic:iterate>
				</logic:notPresent>
			</fieldset>
		</html:form>
	</div>
</div>
<!-- /Fin de ocultarcampo -->

