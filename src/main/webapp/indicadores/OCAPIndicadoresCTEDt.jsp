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
						relacionadas con CTE's: </legend>
					<logic:present name="sinDatos">
						<div class="textoCaracteres">
							<div class="espaciador"></div>
							<bean:message key="listado.noDatos" />
						</div>
					</logic:present>
					<div class="separador"></div>
					<logic:notPresent name="sinDatos">
						<%--
                     <label class="">Categor&iacute;as incluidas en cada CTE:</label><br />
                     <bean:size id="numCTEs" name="OCAPComponentesfqsForm" property="listaCTE" />
                     <logic:equal name="numCTEs" value="0">
                        <label class="textoSubrayado">No hay resultados por CTE's.</label>
                     </logic:equal>
                     <logic:iterate id="elemento" name="OCAPComponentesfqsForm" property="listaCTE">
                        <span class="negrita textoSubrayado"><bean:write name="elemento" property="DNombreCte" /></span><br />
                        <logic:iterate id="elemento2" name="elemento" property="listaCategorias">
                           <span class="indexar negrita">- <bean:write name="elemento2" property="DProfesional_nombre" /></span><br />
                           <span class="indexarMas negrita">Evaluados: </span><bean:write name="elemento2" property="totalIndicadores" />  (<bean:write name="elemento2" property="porcentajeIndicadores" />)<br />
                           <span class="indexarMas negrita">OK: </span><bean:write name="elemento2" property="totalIndicadoresOK" /> (<bean:write name="elemento2" property="porcentajeIndicadoresOK" />)<br />
                           <span class="indexarMas negrita">KO: </span><bean:write name="elemento2" property="totalIndicadoresKO" /> (<bean:write name="elemento2" property="porcentajeIndicadoresKO" />)<br />
                        </logic:iterate>
                        <span class="textoSubrayado indexar negrita">Profesionales evaluados: </span><bean:write name="elemento" property="totalIndicadores" /><br />
                        <span class="indexarMas negrita">OK: </span><bean:write name="elemento" property="totalIndicadoresOK" /> (<bean:write name="elemento" property="porcentajeIndicadoresOK" />)<br />
                        <span class="indexarMas negrita">KO: </span><bean:write name="elemento" property="totalIndicadoresKO" /> (<bean:write name="elemento" property="porcentajeIndicadoresKO" />)<br />
                     </logic:iterate>
--%>
						<div class="espacioLabel">
							<label class="textoNegrita textoSubrayado">Resultados por
								CTE:</label>

							<div class="espacioLabel subCategoria">
								<bean:size id="numCTEs2" name="OCAPComponentesfqsForm"
									property="listaCTE2" />
								<logic:equal name="numCTEs2" value="0">
									<label class="textoSubrayado">No hay resultados por
										CTE's.</label>
								</logic:equal>
								<logic:iterate id="elemento" name="OCAPComponentesfqsForm"
									property="listaCTE2">
									<span class="negrita"><bean:write name="elemento"
											property="DNombreCte" /></span>
									<div class="subCategoria">
										<span>Evaluadores: </span>
										<bean:write name="elemento" property="totalIndicadores" />
										(
										<bean:write name="elemento" property="porcentajeIndicadores" />
										)<br />
										<bean:size id="numCategorias" name="elemento"
											property="listaCategorias" />
										<span>Categor&iacute;as: </span>
										<bean:write name="numCategorias" />
										<br /> <span>Evaluados: </span>
										<bean:write name="elemento" property="totalIndicador" />
										<%-- (<bean:write name="elemento" property="porcentajeIndicadores" />) --%>
										<br /> <span>OK: </span>
										<bean:write name="elemento" property="totalIndicadoresOK" />
										(
										<bean:write name="elemento" property="porcentajeIndicadoresOK" />
										)<br /> <span>KO: </span>
										<bean:write name="elemento" property="totalIndicadoresKO" />
										(
										<bean:write name="elemento" property="porcentajeIndicadoresKO" />
										)<br /> <span>Conforme: </span>
										<bean:write name="elemento" property="totalIndicadoresModOK" />
										(
										<bean:write name="elemento"
											property="porcentajeIndicadoresModOK" />
										)<br /> <span>No Conforme: </span>
										<bean:write name="elemento" property="totalIndicadoresModKO" />
										(
										<bean:write name="elemento"
											property="porcentajeIndicadoresModKO" />
										)<br />
									</div>
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

