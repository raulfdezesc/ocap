<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT"%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>
<script>

var indice=-1;

function cargarListaEvaluadores(tipo){
  if (indice != -1){
      if (indice == "1") {
        document.forms[0].NEvaluacionesevaluador.value = 0; 
        document.forms[0].NInformesrecibidos.value = 0; 
        document.forms[0].NInformespositivos.value = 0; 
        document.forms[0].NInformesnegativos.value = 0; 
      }else{
         for (var i=0;i<indice;i++){           
           document.forms[0].NEvaluacionesevaluador[i].value = 0; 
           document.forms[0].NInformesrecibidos[i].value = 0; 
           document.forms[0].NInformespositivos[i].value = 0; 
           document.forms[0].NInformesnegativos[i].value = 0;                                          
         }
      }
   }

   if (tipo == "S")
         enviar('OCAPReuniones.do?accion=cargarListaEvaluadores&cte='+ document.forms[0].CCte_id.value+'&opcion=<%=request.getAttribute("opcion")%>');      
   else
         enviar('OCAPReuniones.do?accion=cargarListaEvaluadores&opcion=<%=request.getAttribute("opcion")%>');      
}

function obtenerResumen(){
   if (indice != -1){
      if (indice == "1") {
            document.forms[0].resumenEval.value = document.forms[0].resumenEval.value + document.forms[0].CEvaluadorId.value + '<%=Constantes.SEPARADOR_2%>' +
                                               document.forms[0].NEvaluacionesevaluador.value + '<%=Constantes.SEPARADOR_2%>' + 
                                               document.forms[0].NInformesrecibidos.value + '<%=Constantes.SEPARADOR_2%>' +
                                               document.forms[0].NInformespositivos.value + '<%=Constantes.SEPARADOR_2%>' +
                                               document.forms[0].NInformesnegativos.value + '<%=Constantes.SEPARADOR%>'; 
      }else{
         for (var i=0;i<indice;i++){
            document.forms[0].resumenEval.value = document.forms[0].resumenEval.value + document.forms[0].CEvaluadorId[i].value + '<%=Constantes.SEPARADOR_2%>' +
                                               document.forms[0].NEvaluacionesevaluador[i].value + '<%=Constantes.SEPARADOR_2%>' + 
                                               document.forms[0].NInformesrecibidos[i].value + '<%=Constantes.SEPARADOR_2%>' +
                                               document.forms[0].NInformespositivos[i].value + '<%=Constantes.SEPARADOR_2%>' +
                                               document.forms[0].NInformesnegativos[i].value + '<%=Constantes.SEPARADOR%>';                                           
         }
      }
   }
     
   enviar('OCAPReuniones.do?accion=insertar&opcion=<%=request.getAttribute("opcion")%>');
}
</script>

<%--div class="contenido contenidoFaseIII"--%>
<div class="contenido">
	<div class="contenidoDCP1A reuniones">
		<html:form action="/OCAPReuniones.do">
			<h3 class="subTituloContenido">Reuniones de Comit&eacute;s
				T&eacute;cnicos de Evaluaci&oacute;n</h3>

			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<div class="textoRojo">
				<html:messages id="fFecha" property="fFecha" message="true">
					<bean:write name="fFecha" />
					<br />
				</html:messages>
				<html:messages id="nHora" property="nHora" message="true">
					<bean:write name="nHora" />
					<br />
				</html:messages>
				<html:messages id="dOrden" property="dOrden" message="true">
					<bean:write name="dOrden" />
					<br />
				</html:messages>
				<html:messages id="dDecisiones" property="dDecisiones"
					message="true">
					<bean:write name="dDecisiones" />
					<br />
				</html:messages>
				<html:messages id="cCteId" property="cCteId" message="true">
					<bean:write name="cCteId" />
					<br />
				</html:messages>
			</div>

			<fieldset class="evaluadoresAsociados">
				<legend class="tituloLegend"> Datos de la reuni&oacute;n </legend>
				<div class="cuadroFieldset">
					<label for="idVFechaC"> Fecha: (dd/mm/aaaa) * <html:text
							property="FReunion" tabindex="1"
							styleClass="recuadroM colocaFecha1Ctes" maxlength="10" /> <a
						id="calFCons"
						href='javascript:show_Calendario("OCAPReunionesForm", "FReunion", document.forms[0].FReunion.value);'><html:img
								src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
					</label> <label for="idVHoraInicio"> Hora: (hh:mm) * <html:text
							property="NHora" maxlength="5" tabindex="3"
							styleClass="recuadroM colocaHoras textoNormal" />
					</label> <br />
					<br /> <label for="idOrden">Orden del d&iacute;a: * <html:textarea
							property="DOrdendia" tabindex="5"
							styleClass="taMedianoEvaluadores" cols="" rows="" />
					</label> <br />
					<br /> <label for="idDecisiones">Decisiones tomadas: * <html:textarea
							property="DDecisiones" tabindex="6"
							styleClass="taMedianoEvaluadores" cols="" rows="" />
					</label> <br />
					<br />
					<br /> <label for="idVEspecialidad"> N&uacute;mero de
						informes recibidos: <html:text property="NInformestotales"
							tabindex="7" styleClass="recuadroM colocaNumeroInformes"
							maxlength="3" onkeypress="return permitirSoloNumeros(event);" />
					</label>

					<logic:equal name="opcion" value="<%=Constantes.FQS_CTE%>">
						<label class="separaComite" for="idVCategoria">
							Comit&eacute; al que pertenece: * <html:select property="CCte_id"
								styleClass="cbCuadros colocaComitePertenece" size="1"
								tabindex="8" onchange="javascript:cargarListaEvaluadores('S');">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_CTES%>"
									property="CCteId" labelProperty="DNombre" />
							</html:select> <br />
						<br />
						</label>
					</logic:equal>
					<logic:equal name="opcion" value="<%=Constantes.FQS_CE%>">
						<logic:equal name="OCAPReunionesForm" property="jspInicio"
							value="true">
							<script>
                           cargarListaEvaluadores('N');
                        </script>
						</logic:equal>
					</logic:equal>

					<logic:equal name="OCAPReunionesForm" property="jspInicio"
						value="false">
						<fieldset class="evaluadoresAsociados2">
							<legend class="tituloLegend"> Evaluadores asociados </legend>
							<bean:size id="tamanoLista" name="OCAPReunionesForm"
								property="listadoEval" />
							<logic:equal name="tamanoLista" value="0">
								<br />
								<label class="obligadoTexto"><span class="textoNegro">Este
										CTE no tiene evaluadores asociados</span></label>
								<br />
								<br />
							</logic:equal>
							<logic:notEqual name="tamanoLista" value="0">
								<html:hidden property="resumenEval" />
								<script>
                             var indice = '<%=tamanoLista%>';
                           </script>
								<div class="cuadroFieldset modificacionEvaluadoresAsociados">
									<table class="tablaCertif reducirTexto">
										<tr>
											<label class="obligadoTexto"><th
												class="tituloTabla fijaAncho" headers="dConv">Evaluador
											</th></label>
											<th class="tituloTabla" headers="nEval">Num.
												Evaluaciones</th>
											<th class="tituloTabla" headers="nEvalA">Num.
												Evaluaciones analizadas</th>
											<th class="tituloTabla" headers="nEvalP">Num.
												Evaluaciones positivas</th>
											<th class="tituloTabla" headers="nEvalN">Num.
												Evaluaciones negativas</th>
										</tr>
										<logic:iterate id="elemento" name="OCAPReunionesForm"
											property="listadoEval" type="OCAPComponentesfqsOT"
											indexId="indice">
											<tr>
												<td class="textoTabla" headers="nEval"><bean:write
														name="elemento" property="DApellNom" /></td>
												<td class="textoTabla" headers="nEvalA"><html:text
														property="NEvaluacionesevaluador" tabindex="21"
														styleClass="recuadroM colocaNumeroInformes" maxlength="3"
														value="<%=Long.toString(elemento.getNEvaluacionesevaluador())%>"
														onkeypress="return permitirSoloNumeros(event);" /></td>
												<td class="textoTabla" headers="nEvalA"><html:text
														property="NInformesrecibidos" tabindex="22"
														styleClass="recuadroM colocaNumeroInformes" maxlength="3"
														value="<%=Long.toString(elemento.getNInformesrecibidos())%>"
														onkeypress="return permitirSoloNumeros(event);" /></td>
												<td class="textoTabla" headers="nEvalA"><html:text
														property="NInformespositivos" tabindex="23"
														styleClass="recuadroM colocaNumeroInformes" maxlength="3"
														value="<%=Long.toString(elemento.getNInformespositivos())%>"
														onkeypress="return permitirSoloNumeros(event);" /></td>
												<td class="textoTabla" headers="nEvalA"><html:text
														property="NInformesnegativos" tabindex="24"
														styleClass="recuadroM colocaNumeroInformes" maxlength="3"
														value="<%=Long.toString(elemento.getNInformesnegativos())%>"
														onkeypress="return permitirSoloNumeros(event);" /></td>

												<html:hidden property="CEvaluadorId"
													value="<%=Long.toString(elemento.getCCompfqsId())%>" />
											</tr>
										</logic:iterate>
									</table>
								</div>
							</logic:notEqual>
						</fieldset>
					</logic:equal>
				</div>
			</fieldset>

			<div class="limpiadora"></div>
			<div class="espaciador"></div>
			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:obtenerResumen();"> <img
							src="imagenes/imagenes_ocap/flecha_correcto.gif" alt="Aceptar" />
							<span> Aceptar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>

			<div id="divTexto">
				<p>
					<label class="obligadoTexto">Los campos se&ntilde;alados
						con * son obligatorios</label>
				<div class="espaciador"></div>
				</p>
			</div>
		</html:form>
	</div>
</div>
