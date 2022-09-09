<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.util.Utilidades"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.respuestas.OCAPRespuestasOT"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<logic:equal name="accion" value="<%=Constantes.VER_DETALLE%>">
	<logic:notEqual name="insertar" value="<%=Constantes.SI%>">
		<% int numPregunta = 0;
   int numPreguntasNivel0 = 0;
   int numPreguntasNivel1 = 0;
   int numPreguntasNivel2 = 0;
%>
		<%-- Dependiendo del tipo de la pregunta crearemos el elemento de formulario correspondiente, un radio button, un text, un textarea, un select y le damos el valor correspondiente--%>
		<logic:iterate id="listaPreguntas" name="OCAPCuestionariosForm"
			property="listadoPreguntas" type="OCAPCuestionariosOT">
			<logic:equal name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.VACIO%>">
				<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion())) {%>
				<span class="stPregunta"><label
					class="nivel<bean:write name="listaPreguntas" property="NNivel" />">
						<% if (Constantes.NUM_ROMANA.equals(listaPreguntas.getBNumeracion())) {%>
						<% if (listaPreguntas.getNNivel()==0) {%> <%=Utilidades.getNumeroRomano(++numPreguntasNivel0)%>
						<% numPreguntasNivel1= 0; numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==1) {%>
						<%=Utilidades.getNumeroRomano(numPreguntasNivel0)%>.<%=Utilidades.getNumeroRomano(++numPreguntasNivel1)%>
						<% numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==2) {%>
						<%=Utilidades.getNumeroRomano(numPreguntasNivel0)%>.<%=Utilidades.getNumeroRomano(numPreguntasNivel1)%>.<%=Utilidades.getNumeroRomano(++numPreguntasNivel2)%>
						<%}%> <% } else {%> <% if (listaPreguntas.getNNivel()==0) {%> <%=++numPreguntasNivel0%>
						<% numPreguntasNivel1= 0; numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==1) {%>
						<%=numPreguntasNivel0%>.<%=++numPreguntasNivel1%> <% numPreguntasNivel2 = 0; %>
						<%}%> <% if (listaPreguntas.getNNivel()==2) {%> <%=numPreguntasNivel0%>.<%=numPreguntasNivel1%>.<%=++numPreguntasNivel2%>
						<%}%> <%}%> - <% } else {%> <span class="stTitulo"><label
							class="nivel<bean:write name="listaPreguntas" property="NNivel" />">
								<% } %> <bean:write name="listaPreguntas" property="DPregunta"
									filter="false" />
						</label></span> <logic:notEqual name="listaPreguntas" property="DObservaciones"
							value="">
							<span class="observacionesPregunta"><bean:write
									name="listaPreguntas" property="DObservaciones" filter="false" /></span>
						</logic:notEqual>
			</logic:equal>
			<logic:present name="verComentarios">
				<logic:equal name="verComentarios" value="<%=Constantes.SI%>">
					<logic:equal name="listaPreguntas" property="CTipoPregunta"
						value="<%=Constantes.COMENTARIO%>">
						<span name="stComentario" class="stComentario"> <label
							class="nivel<bean:write name="listaPreguntas" property="NNivel" />"><bean:write
									name="listaPreguntas" property="DPregunta" filter="false" /></label>
						</span>
						<logic:notEqual name="listaPreguntas" property="DObservaciones"
							value="">
							<span class="observacionesPregunta"><bean:write
									name="listaPreguntas" property="DObservaciones" filter="false" /></span>
						</logic:notEqual>
					</logic:equal>
				</logic:equal>
			</logic:present>
			<logic:notPresent name="verComentarios">
				<logic:notEqual name="OCAPCuestionariosForm" property="BFinalizado"
					value="<%=Constantes.EST_CUEST_FINALIZAR%>">
					<logic:equal name="listaPreguntas" property="CTipoPregunta"
						value="<%=Constantes.COMENTARIO%>">
						<span name="stComentario" class="stComentario"> <label
							class="nivel<bean:write name="listaPreguntas" property="NNivel" />"><bean:write
									name="listaPreguntas" property="DPregunta" filter="false" /></label>
						</span>
						<logic:notEqual name="listaPreguntas" property="DObservaciones"
							value="">
							<span class="observacionesPregunta"><bean:write
									name="listaPreguntas" property="DObservaciones" filter="false" /></span>
						</logic:notEqual>
					</logic:equal>
				</logic:notEqual>
			</logic:notPresent>

			<logic:notEqual name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.VACIO%>">
				<logic:notEqual name="listaPreguntas" property="CTipoPregunta"
					value="<%=Constantes.COMENTARIO%>">
					<logic:notEqual name="listaPreguntas" property="CTipoPregunta"
						value="<%=Constantes.CHECKLINEA%>">
						<div
							class="stPregunta stPreguntaNivel<bean:write name="listaPreguntas" property="NNivel" />">
							<label
								class="nivel<bean:write name="listaPreguntas" property="NNivel" />">
								<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion())) {%>
								<% if (Constantes.NUM_ROMANA.equals(listaPreguntas.getBNumeracion())) {%>
								<% if (listaPreguntas.getNNivel()==0) {%> <%=Utilidades.getNumeroRomano(++numPreguntasNivel0)%>
								<% numPreguntasNivel1= 0; numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==1) {%>
								<%=Utilidades.getNumeroRomano(numPreguntasNivel0)%>.<%=Utilidades.getNumeroRomano(++numPreguntasNivel1)%>
								<% numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==2) {%>
								<%=Utilidades.getNumeroRomano(numPreguntasNivel0)%>.<%=Utilidades.getNumeroRomano(numPreguntasNivel1)%>.<%=Utilidades.getNumeroRomano(++numPreguntasNivel2)%>
								<%}%> <% } else {%> <% if (listaPreguntas.getNNivel()==0) {%> <%=++numPreguntasNivel0%>
								<% numPreguntasNivel1= 0; numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==1) {%>
								<%=numPreguntasNivel0%>.<%=++numPreguntasNivel1%> <% numPreguntasNivel2 = 0; %>
								<%}%> <% if (listaPreguntas.getNNivel()==2) {%> <%=numPreguntasNivel0%>.<%=numPreguntasNivel1%>.<%=++numPreguntasNivel2%>
								<%}%> <%}%> - <% } %> <bean:write name="listaPreguntas"
									property="DPregunta" filter="false" />
							</label>
							<logic:notEqual name="listaPreguntas" property="DObservaciones"
								value="">
								<div class="observacionesPregunta">
									<bean:write name="listaPreguntas" property="DObservaciones"
										filter="false" />
								</div>
							</logic:notEqual>
					</logic:notEqual>
				</logic:notEqual>
			</logic:notEqual>

			<logic:equal name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.RADIO%>">
				<div
					class="st<bean:write name="listaPreguntas" property="CTipoPregunta" />">
					<table class="tablaRespuestas" cellpadding="2" cellspacing="2">
						<% if (listaPreguntas.getNElementos()>1) {%>
						<% for (int i =0; i<listaPreguntas.getNElementos(); i++) {%>
						<tr>
							<td><span class="tamNumListado"> <%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
							</span></td>
							<logic:iterate id="lPosiblesRespuestas" name="listaPreguntas"
								property="listaPosiblesRespuestas" type="OCAPRespuestasOT"
								indexId="nRespuesta">
								<% if (lPosiblesRespuestas.getDValor().equals(request.getAttribute("pregunta"+numPregunta+"_"+i+"_0"))) {%>
								<td>
									<% if (Constantes.SI.equals(listaPreguntas.getBCorto())) {%> <input
									type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
									id="pregunta<%=numPregunta%>_<%=i%>_0" checked="checked"
									value="<%=lPosiblesRespuestas.getDValor()%>" /> <span
									class="textoRadioCuestionarioCorto"><bean:write
											name="lPosiblesRespuestas" property="DNombre" /></span> <% } else {%>
									<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
									id="pregunta<%=numPregunta%>_<%=i%>_0" checked="checked"
									value="<%=lPosiblesRespuestas.getDValor()%>" /> <span
									class="textoRadioCuestionarioLargo"><bean:write
											name="lPosiblesRespuestas" property="DNombre" /></span> <% } %>
								</td>
								<% } else {%>
								<td>
									<% if (Constantes.SI.equals(listaPreguntas.getBCorto())) {%> <input
									type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
									id="pregunta<%=numPregunta%>_<%=i%>_0"
									value="<%=lPosiblesRespuestas.getDValor()%>" /> <span
									class="textoRadioCuestionarioCorto"><bean:write
											name="lPosiblesRespuestas" property="DNombre" /></span> <% } else {%>
									<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
									id="pregunta<%=numPregunta%>_<%=i%>_0"
									value="<%=lPosiblesRespuestas.getDValor()%>" /> <span
									class="textoRadioCuestionarioLargo"><bean:write
											name="lPosiblesRespuestas" property="DNombre" /></span> <% } %>
								</td>
								<% } %>
							</logic:iterate>
						</tr>
						<% } %>
						<% } else {%>
						<% for (int i =0; i<listaPreguntas.getNElementos(); i++) {%>
						<tr>
							<td><logic:iterate id="lPosiblesRespuestas"
									name="listaPreguntas" property="listaPosiblesRespuestas"
									type="OCAPRespuestasOT" indexId="nRespuesta">
									<% if (lPosiblesRespuestas.getDValor().equals(request.getAttribute("pregunta"+numPregunta+"_"+i+"_0"))) {%>
									<% if (Constantes.SI.equals(listaPreguntas.getBCorto())) {%>
									<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
										id="pregunta<%=numPregunta%>_<%=i%>_0" checked="checked"
										value="<%=lPosiblesRespuestas.getDValor()%>" />
									<span class="textoRadioCuestionarioCorto"><bean:write
											name="lPosiblesRespuestas" property="DNombre" /></span>
									<% } else {%>
									<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
										id="pregunta<%=numPregunta%>_<%=i%>_0" checked="checked"
										value="<%=lPosiblesRespuestas.getDValor()%>" />
									<span class="textoRadioCuestionarioLargo"><bean:write
											name="lPosiblesRespuestas" property="DNombre" /></span>
									<% } %>
									<% } else {%>
									<% if (Constantes.SI.equals(listaPreguntas.getBCorto())) {%>
									<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
										id="pregunta<%=numPregunta%>_<%=i%>_0"
										value="<%=lPosiblesRespuestas.getDValor()%>" />
									<span class="textoRadioCuestionarioCorto"><bean:write
											name="lPosiblesRespuestas" property="DNombre" /></span>
									<% } else {%>
									<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
										id="pregunta<%=numPregunta%>_<%=i%>_0"
										value="<%=lPosiblesRespuestas.getDValor()%>" />
									<span class="textoRadioCuestionarioLargo"><bean:write
											name="lPosiblesRespuestas" property="DNombre" /></span>
									<% } %>
									<% } %>
								</logic:iterate></td>
						</tr>
						<% } %>
						<% } %>
					</table>
				</div>
			</logic:equal>

			<logic:equal name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.RADIO_TEXT%>">
				<div
					class="st<bean:write name="listaPreguntas" property="CTipoPregunta" />">
					<% if (listaPreguntas.getNElementos()>1) {%>
					<% for (int i =0; i<listaPreguntas.getNElementos(); i++) {%>
					<span class="tamNumListado"> <%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
					</span>
					<logic:iterate id="lPosiblesRespuestas" name="listaPreguntas"
						property="listaPosiblesRespuestas" type="OCAPRespuestasOT"
						indexId="nRespuesta">
						<% if (lPosiblesRespuestas.getDValor().equals(request.getAttribute("pregunta"+numPregunta+"_"+i+"_0"))) {%>
						<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
							id="pregunta<%=numPregunta%>_<%=i%>_0" checked="checked"
							onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');"
							value="<%=lPosiblesRespuestas.getDValor()%>" />
						<span class="textoRadioCuestionario"><bean:write
								name="lPosiblesRespuestas" property="DNombre" /></span>
						<% } else {%>
						<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
							id="pregunta<%=numPregunta%>_<%=i%>_0"
							onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');"
							value="<%=lPosiblesRespuestas.getDValor()%>" />
						<span class="textoRadioCuestionario"><bean:write
								name="lPosiblesRespuestas" property="DNombre" /></span>
						<% } %>
					</logic:iterate>
					<div class="cuadroTADetalle"><%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_1")==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_"+i+"_1")%></div>
					<% } %>
					<% } else {%>
					<% for (int i =0; i<listaPreguntas.getNElementos(); i++) {%>
					<logic:iterate id="lPosiblesRespuestas" name="listaPreguntas"
						property="listaPosiblesRespuestas" type="OCAPRespuestasOT"
						indexId="nRespuesta">
						<% if (lPosiblesRespuestas.getDValor().equals(request.getAttribute("pregunta"+numPregunta+"_"+i+"_0"))) {%>
						<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
							id="pregunta<%=numPregunta%>_<%=i%>_0"
							onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');"
							checked="checked" value="<%=lPosiblesRespuestas.getDValor()%>" />
						<span class="textoRadioLinea"><bean:write
								name="lPosiblesRespuestas" property="DNombre" /></span>
						<% } else {%>
						<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
							id="pregunta<%=numPregunta%>_<%=i%>_0"
							onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');"
							value="<%=lPosiblesRespuestas.getDValor()%>" />
						<span class="textoRadioLinea"><bean:write
								name="lPosiblesRespuestas" property="DNombre" /></span>
						<% } %>
					</logic:iterate>
					<div class="cuadroTADetalle"><%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_1")==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_"+i+"_1")%></div>
					<% } %>
					<% } %>
				</div>
			</logic:equal>

			<logic:equal name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.CHECKLINEA%>">
				<div
					class="st<bean:write name="listaPreguntas" property="CTipoPregunta" />">
					<ul>
						<% for (int i =0; i<listaPreguntas.getNElementos(); i++) {%>
						<li><span class="stPreguntaCheckLinea"> <% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
								<% if ("S".equals(request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j))) {%>
								<input type="checkbox"
								id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>" checked="checked" />
								<% } else {%> <input type="checkbox"
								id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>" /> <% } %> <% } %>
								<div>
									<label
										class="nivel<bean:write name="listaPreguntas" property="NNivel" />">
										<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion())) {%>
										<% if (!Constantes.NUM_ROMANA.equals(listaPreguntas.getBNumeracion())) {%>
										<% if (listaPreguntas.getNNivel()==0) {%> <%=++numPreguntasNivel0%>
										<% numPreguntasNivel1= 0; numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==1) {%>
										<%=numPreguntasNivel0%>.<%=++numPreguntasNivel1%> <% numPreguntasNivel2 = 0; %>
										<%}%> <% if (listaPreguntas.getNNivel()==2) {%> <%=numPreguntasNivel0%>.<%=numPreguntasNivel1%>.<%=++numPreguntasNivel2%>
										<%}%> <% } else {%> <% if (listaPreguntas.getNNivel()==0) {%> <%=Utilidades.getNumeroRomano(++numPreguntasNivel0)%>
										<% numPreguntasNivel1= 0; numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==1) {%>
										<%=Utilidades.getNumeroRomano(numPreguntasNivel0)%>.<%=Utilidades.getNumeroRomano(++numPreguntasNivel1)%>
										<% numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==2) {%>
										<%=Utilidades.getNumeroRomano(numPreguntasNivel0)%>.<%=Utilidades.getNumeroRomano(numPreguntasNivel1)%>.<%=Utilidades.getNumeroRomano(++numPreguntasNivel2)%>
										<%}%> <%}%> - <% } %> <bean:write name="listaPreguntas"
											property="DPregunta" filter="false" />
									</label>
								</div> <logic:notEqual name="listaPreguntas" property="DObservaciones"
									value="">
									<span class="observacionesPregunta"><bean:write
											name="listaPreguntas" property="DObservaciones"
											filter="false" /></span>
								</logic:notEqual>
						</span></li>
						<% } %>
					</ul>
				</div>
			</logic:equal>

			<logic:equal name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.CHECK%>">
				<div
					class="st<bean:write name="listaPreguntas" property="CTipoPregunta" />">
					<ul>
						<% for (int i =0; i<listaPreguntas.getNElementos(); i++) {%>
						<li><div>
								<span class="tamNumListadoCB"> <%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
								</span>
								<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
								<% if ("S".equals(request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j))) {%>
								<input type="checkbox"
									id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>" checked="checked" />
								<% } else {%>
								<input type="checkbox"
									id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>" />
								<% } %>
								<% } %>
							</div></li>
						<% } %>
					</ul>
				</div>
			</logic:equal>

			<logic:equal name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.TEXTAREA%>">
				<div
					class="st<bean:write name="listaPreguntas" property="CTipoPregunta" />">
					<% if (listaPreguntas.getNElementos()==1 && listaPreguntas.getNSubElementos()==1 && (Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) || Constantes.NUM_SIMPLE.equals(listaPreguntas.getBNumeracion()))) {%>
					<% if (Constantes.SI.equals(request.getAttribute("siPDF"+numPregunta+"_0_0"))) { %>
					<a
						href="javascript:enviar('OCAPCuestionarios.do?accion=abrirDocumentoRespuesta&expCaId=<%=request.getAttribute("pregunta"+numPregunta+"_0_0")%>');">Ver
						respuesta</a>
					<%} else { %>
					<div class="cuadroFinCuestionario"><%=request.getAttribute("pregunta"+numPregunta+"_0_0")==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_0_0").toString().trim().equals("")?"&nbsp;":((String)request.getAttribute("pregunta"+numPregunta+"_0_0")).replaceAll("\n","<br/>")%></div>
					<% } %>
					<% } else {%>
					<table class="tablaRespuestas" cellpadding="2" cellspacing="2">
						<bean:size id="tamanoPosiblesRespuestasTextareaDt"
							name="listaPreguntas" property="listaPosiblesRespuestas" />
						<logic:notEqual name="tamanoPosiblesRespuestasTextareaDt"
							value="0">
							<tr>
								<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {%>
								<th class="columnaNumeracion"></th>
								<% } %>
								<logic:iterate id="lPosiblesRespuestas" name="listaPreguntas"
									property="listaPosiblesRespuestas" type="OCAPRespuestasOT">
									<th colspan="2" class="cabTablaRespuestas"><bean:write
											name="lPosiblesRespuestas" property="DNombre" /></th>
								</logic:iterate>
							</tr>
						</logic:notEqual>
						<% for (int i =0; i<listaPreguntas.getNElementos(); i++) {%>
						<tr>
							<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {%>
							<td class="contenedorNumero">
								<div class="tamNumListado">
									<%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
								</div>
							</td>
							<% } %>
							<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
							<% if (Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {%>
							<td class="contenedorNumero">
								<div class="tamNumListado">
									<%=Utilidades.obtenerNumeracionTotal(listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
								</div>
							</td>
							<td>
								<% } else {%>
							
							<td colspan="2">
								<% } %> <% if (Constantes.SI.equals(request.getAttribute("siPDF"+numPregunta+"_"+i+"_"+j))) { %>
								<a
								href="javascript:enviar('OCAPCuestionarios.do?accion=abrirDocumentoRespuesta&expCaId=<%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%>');">Ver
									respuesta</a> <%} else { %>
								<div
									class="cuadroFinCuestionario<bean:write name="listaPreguntas" property="NSubElementos" />"><%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"&nbsp;":((String)request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)).replaceAll("\n","<br/>")%></div>
								<%}%>
							</td>
							<% } %>
						</tr>
						<% } %>
					</table>
					<% } %>
				</div>
			</logic:equal>

			<logic:equal name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.TEXT%>">
				<div
					class="st<bean:write name="listaPreguntas" property="CTipoPregunta" />">
					<% String estiloText = "cuadroListadoCuestionario"; %>
					<% if (listaPreguntas.getNElementos()==1 && listaPreguntas.getNSubElementos()==1 && (Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) || Constantes.NUM_SIMPLE.equals(listaPreguntas.getBNumeracion()))) {%>
					<% if (Constantes.SI.equals(request.getAttribute("siPDF"+numPregunta+"_0_0"))) { %>
					<a
						href="javascript:enviar('OCAPCuestionarios.do?accion=abrirDocumentoRespuesta&expCaId=<%=request.getAttribute("pregunta"+numPregunta+"_0_0")%>');">Ver
						respuesta</a>
					<%} else { %>
					<% if (Constantes.SI.equals(listaPreguntas.getBCorto())) {%>
					<div class="inputTextoCuestionarioC corto"><%=request.getAttribute("pregunta"+numPregunta+"_0_0")==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_0_0").toString().trim().equals("")?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_0_0")%></div>
					<% } else {%>
					<div class="inputTextoCuestionarioC"><%=request.getAttribute("pregunta"+numPregunta+"_0_0")==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_0_0").toString().trim().equals("")?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_0_0")%></div>
					<% } %>
					<% } %>
					<% } else {%>
					<table class="tablaRespuestas" cellpadding="2">
						<bean:size id="tamanoPosiblesRespuestasDt" name="listaPreguntas"
							property="listaPosiblesRespuestas" />
						<logic:notEqual name="tamanoPosiblesRespuestasDt" value="0">
							<tr>
								<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {%>
								<% estiloText = "cuadroListadoNCuestionario"; %>
								<th class="contenedorNumero"></th>
								<% } %>
								<%-- if (Constantes.CUESTIONARIO_INDICADORES_PREGUNTA!=listaPreguntas.getCPreguntaId()) {--%>
								<% if (!Constantes.SI.equals(listaPreguntas.getBPregIndicadores())) {%>
								<logic:iterate id="lPosiblesRespuestas" name="listaPreguntas"
									property="listaPosiblesRespuestas" type="OCAPRespuestasOT">
									<th colspan="2" class="cabTablaRespuestas"><bean:write
											name="lPosiblesRespuestas" property="DNombre" /></th>
								</logic:iterate>
								<%/*}else if(Constantes.CUESTIONARIO_INDICADORES_PREGUNTA==listaPreguntas.getCPreguntaId()) {*/
                     } else if (Constantes.SI.equals(listaPreguntas.getBPregIndicadores())) {
                     for (int cuentaRespuestas = 0; cuentaRespuestas < listaPreguntas.getListaPosiblesRespuestas().size(); cuentaRespuestas++) {
                        OCAPRespuestasOT lPosiblesRespuestas = (OCAPRespuestasOT)(listaPreguntas.getListaPosiblesRespuestas().get(cuentaRespuestas));
                        if (cuentaRespuestas != 1) {%>
								<th colspan="2"
									class="subtituloListadoCuestionarioIndicadoresCorto"><%=lPosiblesRespuestas.getDNombre()%>
								</th>
								<% } else {%>
								<th colspan="2"
									class="subtituloListadoCuestionarioIndicadoresLargo"><%=lPosiblesRespuestas.getDNombre()%>
								</th>
								<%}
                     }
                    } %>
							</tr>
						</logic:notEqual>
						<% for (int i =0; i<listaPreguntas.getNElementos(); i++) {%>
						<tr>
							<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {%>
							<td class="contenedorNumero">
								<% estiloText = "cuadroListadoNCuestionario"; %> <span
								class="tamNumListado"> <%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
							</span>
							</td>
							<% } %>
							<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
							<% if (Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {%>
							<td class="contenedorNumero">
								<% estiloText = "cuadroListadoNCuestionario"; %> <span
								class="tamNumListado"> <%=Utilidades.obtenerNumeracionTotal(listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
							</td>
							</td>
							<td>
								<% } else {%>
							
							<td colspan="2">
								<% } %> <%-- if (Constantes.CUESTIONARIO_INDICADORES_PREGUNTA!=listaPreguntas.getCPreguntaId()) {--%>
								<% if (!Constantes.SI.equals(listaPreguntas.getBPregIndicadores())) {%>
								<% if (Constantes.SI.equals(request.getAttribute("siPDF"+numPregunta+"_"+i+"_"+j))) { %>
								<a
								href="javascript:enviar('OCAPCuestionarios.do?accion=abrirDocumentoRespuesta&expCaId=<%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%>');">Ver
									respuesta</a> <%} else { %> <% if (Constantes.SI.equals(listaPreguntas.getBCorto())) {%>
								<div
									class="<%=estiloText%>C<bean:write name="listaPreguntas" property="NSubElementos" /> corto"
									style="border: 0.5px #004B98 solid; width: 95%;"><%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%></div>
								<% } else {%>
								<div
									class="<%=estiloText%>C<bean:write name="listaPreguntas" property="NSubElementos" />"
									style="border: 0.5px #004B98 solid; width: 95%;"><%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%></div>
								<% } %> <% } %> <%--}else if(Constantes.CUESTIONARIO_INDICADORES_PREGUNTA==listaPreguntas.getCPreguntaId()) {--%>
								<%} else if (Constantes.SI.equals(listaPreguntas.getBPregIndicadores())) { %>
								<logic:iterate id="lPosiblesRespuestas" name="listaPreguntas"
									property="listaPosiblesRespuestas" type="OCAPRespuestasOT"
									indexId="nPosible">
									<% if (nPosible.intValue() == j) {%>
									<% if (j==1) {%>
									<div class="cuadroListadoIndicadoresLargo"><%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%></div>
									<%} else {%>
									<div class="cuadroListadoIndicadoresCorto"><%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%></div>
									<%}%>
									<% } %>
								</logic:iterate> <%}%>
							</td>
							<% } %>
						</tr>
						<% } %>
					</table>
					<% } %>
				</div>
			</logic:equal>

			<logic:equal name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.TEXT_NUMERO%>">
				<div
					class="st<bean:write name="listaPreguntas" property="CTipoPregunta" />">
					<% if (listaPreguntas.getNElementos()==1 && listaPreguntas.getNSubElementos()==1 && (Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) || Constantes.NUM_SIMPLE.equals(listaPreguntas.getBNumeracion()))) {%>
					<% if (Constantes.SI.equals(request.getAttribute("siPDF"+numPregunta+"_0_0"))) { %>
					<a
						href="javascript:enviar('OCAPCuestionarios.do?accion=abrirDocumentoRespuesta&expCaId=<%=request.getAttribute("pregunta"+numPregunta+"_0_0")%>');">Ver
						respuesta</a>
					<%} else { %>
					<% if (Constantes.SI.equals(listaPreguntas.getBCorto())) {%>
					<div class="inputTextoCuestionario corto"><%=request.getAttribute("pregunta"+numPregunta+"_0_0")==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_0_0").toString().trim().equals("")?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_0_0")%></div>
					<% } else {%>
					<div class="inputTextoCuestionario"><%=request.getAttribute("pregunta"+numPregunta+"_0_0")==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_0_0").toString().trim().equals("")?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_0_0")%></div>
					<% } %>
					<%}%>
					<% } else {%>
					<table class="tablaRespuestas" cellpadding="2" cellspacing="2">
						<bean:size id="tamanoPosiblesRespuestasTextNumeroDt"
							name="listaPreguntas" property="listaPosiblesRespuestas" />
						<logic:notEqual name="tamanoPosiblesRespuestasTextNumeroDt"
							value="0">
							<tr>
								<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {%>
								<th class="columnaNumeracion"></th>
								<% } %>
								<% for (int cuentaRespuestas = 0; cuentaRespuestas < listaPreguntas.getListaPosiblesRespuestas().size(); cuentaRespuestas++) {
                        OCAPRespuestasOT lPosiblesRespuestas = (OCAPRespuestasOT)(listaPreguntas.getListaPosiblesRespuestas().get(cuentaRespuestas));
                        if (cuentaRespuestas == 0) {%>
								<th colspan="2" class="subtituloListadoCuestionarioLargo">
									<%=lPosiblesRespuestas.getDNombre()%>
								</th>
								<% } else if (cuentaRespuestas != listaPreguntas.getListaPosiblesRespuestas().size()-1){%>
								<th colspan="2" class="subtituloListadoCuestionarioCorto">
									<%=lPosiblesRespuestas.getDNombre()%>
								</th>
								<% } else {%>
								<th colspan="2" class="subtituloListadoCuestionarioMediano">
									<%=lPosiblesRespuestas.getDNombre()%>
								</th>
								<%}
                     } %>
							</tr>
						</logic:notEqual>
						<% for (int i =0; i<listaPreguntas.getNElementos(); i++) {%>
						<tr>
							<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {%>
							<td class="tamNumListado"><span> <%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
							</span></td>
							<% } %>
							<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
							<% if (Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {%>
							<td class="tamNumListado"><span> <%=Utilidades.obtenerNumeracionTotal(listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
							</span></td>
							<td>
								<% } else {%>
							
							<td colspan="2">
								<% } %> <% if (Constantes.SI.equals(request.getAttribute("siPDF"+numPregunta+"_"+i+"_"+j))) { %>
								<a
								href="javascript:enviar('OCAPCuestionarios.do?accion=abrirDocumentoRespuesta&expCaId=<%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%>');">Ver
									respuesta</a> <%} else { %> <% if (j==0) {%>
								<div class="cuadroListadoLargo"><%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%></div>
								<% } else {%>
								<div class="cuadroListadoCorto"><%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%></div>
								<% } %> <%}%>
							</td>
							<% } %>
						</tr>
						<% } %>
					</table>
					<% } %>
				</div>
			</logic:equal>

			<% if (Constantes.NUMERO.equals(listaPreguntas.getCTipoPregunta()) || Constantes.NUMERO_DECIMAL.equals(listaPreguntas.getCTipoPregunta()) )  { %>
			<div
				class="st<bean:write name="listaPreguntas" property="CTipoPregunta" />">
				<% if (listaPreguntas.getNElementos()==1 && listaPreguntas.getNSubElementos()==1 && (Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) || Constantes.NUM_SIMPLE.equals(listaPreguntas.getBNumeracion()))) {%>
				<% if (Constantes.SI.equals(request.getAttribute("siPDF"+numPregunta+"_0_0"))) { %>
				<a
					href="javascript:enviar('OCAPCuestionarios.do?accion=abrirDocumentoRespuesta&expCaId=<%=request.getAttribute("pregunta"+numPregunta+"_0_0")%>');">Ver
					respuesta</a>
				<%} else { %>
				<div
					class="cuadroListadoCuestionarioC<bean:write name="listaPreguntas" property="NSubElementos" /> numero"><%=request.getAttribute("pregunta"+numPregunta+"_0_0")==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_0_0").toString().trim().equals("")?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_0_0")%></div>
				<%}%>
				<% } else {%>
				<table class="tablaRespuestas" cellpadding="2" cellspacing="2">
					<bean:size id="tamanoPosiblesRespuestasNumeroDt"
						name="listaPreguntas" property="listaPosiblesRespuestas" />
					<logic:notEqual name="tamanoPosiblesRespuestasNumeroDt" value="0">
						<tr>
							<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {%>
							<th class="columnaNumeracion"></th>
							<% } %>
							<logic:iterate id="lPosiblesRespuestas" name="listaPreguntas"
								property="listaPosiblesRespuestas" type="OCAPRespuestasOT">
								<th colspan="2" class="cabTablaRespuestas"><bean:write
										name="lPosiblesRespuestas" property="DNombre" /></th>
							</logic:iterate>
						</tr>
					</logic:notEqual>
					<% for (int i =0; i<listaPreguntas.getNElementos(); i++) {%>
					<tr>
						<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {%>
						<td class="tamNumListado"><span> <%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
						</span></td>
						<% } %>
						<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
						<% if (Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {%>
						<td class="tamNumListado"><span> <%=Utilidades.obtenerNumeracionTotal(listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
						</span></td>
						<td>
							<% } else {%>
						
						<td colspan="2">
							<% } %> <% if (Constantes.SI.equals(request.getAttribute("siPDF"+numPregunta+"_"+i+"_"+j))) { %>
							<a
							href="javascript:enviar('OCAPCuestionarios.do?accion=abrirDocumentoRespuesta&expCaId=<%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%>');">Ver
								respuesta</a> <%} else { %>
							<div
								class="cuadroListadoCuestionarioC<bean:write name="listaPreguntas" property="NSubElementos" /> numero"><%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%></div>
							<%}%>
						</td>
						<% } %>
					</tr>
					<% } %>
				</table>
				<% } %>
			</div>
			<% } %>

			<logic:equal name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.CREDITOS%>">
				<div
					class="st<bean:write name="listaPreguntas" property="CTipoPregunta" />">
					<% if (listaPreguntas.getNElementos()==1 && listaPreguntas.getNSubElementos()==1 && (Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) || Constantes.NUM_SIMPLE.equals(listaPreguntas.getBNumeracion()))) {%>
					<div class="inputTextoCuestionario numero"><%=request.getAttribute("pregunta"+numPregunta+"_0_0")==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_0_0").toString().trim().equals("")?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_0_0")%></div>
					<% } else {%>
					<table cellpadding="2">
						<bean:size id="tamanoPosiblesRespuestasCreditosDt"
							name="listaPreguntas" property="listaPosiblesRespuestas" />
						<logic:notEqual name="tamanoPosiblesRespuestasCreditosDt"
							value="0">
							<tr>
								<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {%>
								<th class="columnaNumeracion"></th>
								<% } %>
								<logic:iterate id="lPosiblesRespuestas" name="listaPreguntas"
									property="listaPosiblesRespuestas" type="OCAPRespuestasOT">
									<th colspan="2" class="subtituloListadoCuestionario"><bean:write
											name="lPosiblesRespuestas" property="DNombre" /></th>
								</logic:iterate>
							</tr>
						</logic:notEqual>
						<% for (int i =0; i<listaPreguntas.getNElementos(); i++) {%>
						<tr>
							<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {%>
							<td><span class="tamNumListadoInput"> <%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
							</span></td>
							<% } %>
							<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
							<% if (Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {%>
							<td><%=Utilidades.obtenerNumeracionTotal(listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
							</td>
							<td>
								<% } else {%>
							
							<td colspan="2">
								<% } %>
								<div
									class="cuadroListadoCuestionarioC<bean:write name="listaPreguntas" property="NSubElementos" /> numero"><%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%></div>
							</td>
							<% } %>
						</tr>
						<% } %>
					</table>
					<% } %>
				</div>
			</logic:equal>

			<logic:equal name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.FECHA%>">
				<div
					class="st<bean:write name="listaPreguntas" property="CTipoPregunta" />">
					<% for (int i =0; i<listaPreguntas.getNElementos(); i++) {%>
					<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
					<% if (Constantes.SI.equals(request.getAttribute("siPDF"+numPregunta+"_"+i+"_"+j))) { %>
					<a
						href="javascript:enviar('OCAPCuestionarios.do?accion=abrirDocumentoRespuesta&expCaId=<%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%>');">Ver
						respuesta</a>
					<%} else { %>
					<input class="inputCalendarioCuestionarioC" type="text"
						id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>" maxlength="10"
						onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
						onblur="return validarFecha(this);"
						value="<%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"&nbsp;":request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%>" />
					<%}%>
					<% } %>
					<% } %>
				</div>
			</logic:equal>
			<logic:notEqual name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.COMENTARIO%>">
			</logic:notEqual>

			<% if (!listaPreguntas.getCTipoPregunta().equals(Constantes.VACIO) && !listaPreguntas.getCTipoPregunta().equals(Constantes.COMENTARIO)) {
         numPregunta++;
      } %>

			<logic:notEqual name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.VACIO%>">
				<logic:notEqual name="listaPreguntas" property="CTipoPregunta"
					value="<%=Constantes.COMENTARIO%>">
					<logic:notEqual name="listaPreguntas" property="CTipoPregunta"
						value="<%=Constantes.CHECKLINEA%>">
						</div>
					</logic:notEqual>
				</logic:notEqual>
			</logic:notEqual>

		</logic:iterate>

		<% 
   if (numPregunta == 0) {
      request.setAttribute("accion", Constantes.VER_DETALLE);
      request.setAttribute("BFinalizado", Constantes.EST_CUEST_FINALIZAR);
   }
%>
	</logic:notEqual>

	<logic:equal name="OCAPCuestionariosForm" property="BFinalizado"
		value="<%=Constantes.EST_CUEST_FINALIZAR%>">
		<script language="javascript" type="text/javascript">
         deshabilitarFormulario();
      </script>
	</logic:equal>
	<logic:equal name="BFinalizado"
		value="<%=Constantes.EST_CUEST_FINALIZAR%>">
		<script language="javascript" type="text/javascript">
         deshabilitarFormulario();
      </script>
	</logic:equal>

</logic:equal>
