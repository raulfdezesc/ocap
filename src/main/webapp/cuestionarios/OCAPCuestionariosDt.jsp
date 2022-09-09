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
								<span class="observacionesPregunta"><bean:write
										name="listaPreguntas" property="DObservaciones" filter="false" /></span>
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
							</span>
								</div> <logic:iterate id="lPosiblesRespuestas" name="listaPreguntas"
									property="listaPosiblesRespuestas" type="OCAPRespuestasOT"
									indexId="nRespuesta">
									<% if (lPosiblesRespuestas.getDValor().equals(request.getAttribute("pregunta"+numPregunta+"_"+i+"_0"))) {%>
									<td>
										<% if (Constantes.SI.equals(listaPreguntas.getBCorto())) {%> <input
										type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
										id="pregunta<%=numPregunta%>_<%=i%>_0" checked="checked"
										onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');"
										value="<%=lPosiblesRespuestas.getDValor()%>" /> <span
										class="textoRadioCuestionarioCorto"><bean:write
												name="lPosiblesRespuestas" property="DNombre" /></span> <% } else {%>
										<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
										id="pregunta<%=numPregunta%>_<%=i%>_0" checked="checked"
										onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');"
										value="<%=lPosiblesRespuestas.getDValor()%>" /> <span
										class="textoRadioCuestionarioLargo"><bean:write
												name="lPosiblesRespuestas" property="DNombre" /></span> <% } %>
									</td>
									<% } else {%>
									<td>
										<% if (Constantes.SI.equals(listaPreguntas.getBCorto())) {%> <input
										type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
										id="pregunta<%=numPregunta%>_<%=i%>_0"
										onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');"
										value="<%=lPosiblesRespuestas.getDValor()%>" /> <span
										class="textoRadioCuestionarioCorto"><bean:write
												name="lPosiblesRespuestas" property="DNombre" /></span> <% } else {%>
										<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
										id="pregunta<%=numPregunta%>_<%=i%>_0"
										onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');"
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
										value="<%=lPosiblesRespuestas.getDValor()%>"
										onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');" />
									<span class="textoRadioCuestionarioCorto"><bean:write
											name="lPosiblesRespuestas" property="DNombre" /></span>
									<% } else {%>
									<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
										id="pregunta<%=numPregunta%>_<%=i%>_0" checked="checked"
										value="<%=lPosiblesRespuestas.getDValor()%>"
										onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');" />
									<span class="textoRadioCuestionarioLargo"><bean:write
											name="lPosiblesRespuestas" property="DNombre" /></span>
									<% } %>
									<% } else {%>
									<% if (Constantes.SI.equals(listaPreguntas.getBCorto())) {%>
									<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
										id="pregunta<%=numPregunta%>_<%=i%>_0"
										value="<%=lPosiblesRespuestas.getDValor()%>"
										onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');" />
									<span class="textoRadioCuestionarioCorto"><bean:write
											name="lPosiblesRespuestas" property="DNombre" /></span>
									<% } else {%>
									<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
										id="pregunta<%=numPregunta%>_<%=i%>_0"
										value="<%=lPosiblesRespuestas.getDValor()%>"
										onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');" />
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
					<textarea disabled class="cuadroTACuestionario"
						id="pregunta<%=numPregunta%>_<%=i%>_1" cols="90%" rows="3%"
						onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','1');"
						onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','1');"
						onkeypress="javascript:return longMaxTextarea(this);"
						onclick="javascript:return longMaxTextarea(this);"
						onkeydown="javascript:return longMaxTextarea(this);"
						onkeyup="javascript:return longMaxTextarea(this);"><%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_1")==null?"":((String)request.getAttribute("pregunta"+numPregunta+"_"+i+"_1")).replaceAll("<br/>","\n")%></textarea>
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
					<textarea disabled readonly class="cuadroTACuestionario"
						id="pregunta<%=numPregunta%>_<%=i%>_1" cols="90%" rows="3%"
						onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','1');"
						onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','1');"
						onkeypress="javascript:return longMaxTextarea(this);"
						onclick="javascript:return longMaxTextarea(this);"
						onkeydown="javascript:return longMaxTextarea(this);"
						onkeyup="javascript:return longMaxTextarea(this);"><%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_1")==null?"":((String)request.getAttribute("pregunta"+numPregunta+"_"+i+"_1")).replaceAll("<br/>","\n")%></textarea>
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
								id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>" checked="checked"
								onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');" />
								<% } else {%> <input type="checkbox"
								id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
								onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');" />
								<% } %> <% } %>
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
									id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>" checked="checked"
									onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');" />
								<% } else {%>
								<input type="checkbox"
									id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
									onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');" />
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
					<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
					<textarea class="cuadroTACuestionario"
						id="pregunta<%=numPregunta%>_0_<%=j%>"
						onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','<%=j%>');"
						onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','<%=j%>');"
						cols="90%" rows="3%"
						onkeypress="javascript:return longMaxTextarea(this);"
						onclick="javascript:return longMaxTextarea(this);"
						onkeydown="javascript:return longMaxTextarea(this);"
						onkeyup="javascript:return longMaxTextarea(this);"><%=request.getAttribute("pregunta"+numPregunta+"_0_0")==null?"":((String)request.getAttribute("pregunta"+numPregunta+"_0_0")).replaceAll("<br/>","\n")%></textarea>
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
							<td><%=Utilidades.obtenerNumeracionTotal(listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
							</td>
							<td>
								<% } else {%>
							
							<td colspan="2">
								<% } %> <textarea
									class="cuadroTACuestionario<bean:write name="listaPreguntas" property="NSubElementos" />"
									id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
									onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
									onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
									cols="45%" rows="3%"
									onkeypress="javascript:return longMaxTextarea(this);"
									onclick="javascript:return longMaxTextarea(this);"
									onkeydown="javascript:return longMaxTextarea(this);"
									onkeyup="javascript:return longMaxTextarea(this);"><%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"":((String)request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)).replaceAll("<br/>","\n")%></textarea>
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
					<!-- PREGUNTA TIPO TEXTO DE UN ELEMENTO -->
					<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
					<% if (Constantes.SI.equals(listaPreguntas.getBCorto())) {%>
					<input class="inputTextoCuestionario corto" type="text"
						id="pregunta<%=numPregunta%>_0_0"
						value="<%=request.getAttribute("pregunta"+numPregunta+"_0_0")==null?"":((String)request.getAttribute("pregunta"+numPregunta+"_0_0")).replaceAll("\"","'")%>"
						onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','0');"
						onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','0');"
						maxlength="500" />
					<% } else {%>
					<input class="inputTextoCuestionario" type="text"
						id="pregunta<%=numPregunta%>_0_0"
						value="<%=request.getAttribute("pregunta"+numPregunta+"_0_0")==null?"":((String)request.getAttribute("pregunta"+numPregunta+"_0_0")).replaceAll("\"","'")%>"
						onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','0');"
						onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','0');"
						maxlength="500" />
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
							<% estiloText = "cuadroListadoNCuestionario"; %>
							<td class="contenedorNumero"><span class="tamNumListado">
									<%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
							</span></td>
							<% } %>
							<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
							<% if (Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {%>
							<td class="contenedorNumero">
								<% estiloText = "cuadroListadoNCuestionario"; %> <span
								class="tamNumListado"> <%=Utilidades.obtenerNumeracionTotal(listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
							</span>
							</td>
							<td>
								<% } else {%>
							
							<td class="contenedorDato" colspan="2">
								<% } %> <% if (!Constantes.SI.equals(listaPreguntas.getBPregIndicadores())) {%>
								<% if (Constantes.SI.equals(listaPreguntas.getBCorto())) {%> <input
								class="<%=estiloText%><bean:write name="listaPreguntas" property="NSubElementos" /> corto"
								style="border: 0.5px #004B98 solid; width: 95%;" type="text"
								id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
								value="<%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"":((String)request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)).replaceAll("\"","'")%>"
								onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								maxlength="500" /> <% } else {%> <input
								class="<%=estiloText%><bean:write name="listaPreguntas" property="NSubElementos" />"
								style="border: 0.5px #004B98 solid; width: 95%;" type="text"
								id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
								value="<%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"":((String)request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)).replaceAll("\"","'")%>"
								onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								maxlength="500" /> <%}%> <%--}else if(Constantes.CUESTIONARIO_INDICADORES_PREGUNTA==listaPreguntas.getCPreguntaId()) {--%>
								<%} else if (Constantes.SI.equals(listaPreguntas.getBPregIndicadores())) { %>
								<% if (j==1) {%> <input class="cuadroListadoIndicadoresLargo"
								style="border: 0.5px #004B98 solid; width: 95%;" type="text"
								id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
								value="<%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"":((String)request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)).replaceAll("\"","'")%>"
								onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								maxlength="500" /> <% } else {%> <input
								class="cuadroListadoIndicadoresCorto"
								style="border: 0.5px #004B98 solid; width: 95%;" type="text"
								id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
								value="<%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"":((String)request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)).replaceAll("\"","'")%>"
								onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								maxlength="500" /> <%}%> <% } %>
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
					<!-- PREGUNTA TIPO TEXTO DE UN ELEMENTO -->
					<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
					<% if (Constantes.SI.equals(listaPreguntas.getBCorto())) {%>
					<input class="inputTextoCuestionario corto" type="text"
						id="pregunta<%=numPregunta%>_0_<%=j%>"
						value="<%=request.getAttribute("pregunta"+numPregunta+"_0_"+j)==null?"":((String)request.getAttribute("pregunta"+numPregunta+"_0_"+j)).replaceAll("\"","'")%>"
						onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','<%=j%>');"
						onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','<%=j%>');"
						maxlength="500" />
					<% } else {%>
					<input class="inputTextoCuestionario" type="text"
						id="pregunta<%=numPregunta%>_0_<%=j%>"
						value="<%=request.getAttribute("pregunta"+numPregunta+"_0_"+j)==null?"":((String)request.getAttribute("pregunta"+numPregunta+"_0_"+j)).replaceAll("\"","'")%>"
						onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','<%=j%>');"
						onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','<%=j%>');"
						maxlength="500" />
					<% } %>
					<% } %>
					<% } else {%>
					<table class="tablaRespuestas" cellpadding="2">
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
							<td class="tamNumListado"><%=Utilidades.obtenerNumeracionTotal(listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
							</td>
							<td>
								<% } else {%>
							
							<td colspan="2">
								<% } %> <% if (j==0) {%> <input class="cuadroListadoLargo"
								type="text" id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
								value="<%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"":((String)request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)).replaceAll("\"","'")%>"
								onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								maxlength="500" /> <% } else {%> <input
								class="cuadroListadoCorto" type="text"
								id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
								value="<%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"":((String)request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)).replaceAll("\"","'")%>"
								onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								maxlength="500" /> <% } %>
							</td>
							<% } %>
						</tr>
						<% } %>
					</table>
					<% } %>
				</div>
			</logic:equal>

			<logic:equal name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.NUMERO%>">
				<div
					class="st<bean:write name="listaPreguntas" property="CTipoPregunta" />">
					<% if (listaPreguntas.getNElementos()==1 && listaPreguntas.getNSubElementos()==1 && (Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) || Constantes.NUM_SIMPLE.equals(listaPreguntas.getBNumeracion()))) {%>
					<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
					<input
						class="cuadroListadoCuestionario<bean:write name="listaPreguntas" property="NSubElementos" /> numero"
						type="text" id="pregunta<%=numPregunta%>_0_0"
						value="<%=request.getAttribute("pregunta"+numPregunta+"_0_0")==null?"":request.getAttribute("pregunta"+numPregunta+"_0_0")%>"
						onkeypress="return permitirSoloNumeros(event);"
						onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','0');"
						onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','0');"
						maxlength="500" />
					<% } %>
					<% } else {%>
					<table class="tablaRespuestas" cellpadding="2">
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
							<td><%=Utilidades.obtenerNumeracionTotal(listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
							</td>
							<td>
								<% } else {%>
							
							<td colspan="2">
								<% } %> <input
								class="cuadroListadoCuestionario<bean:write name="listaPreguntas" property="NSubElementos" /> numero"
								type="text" id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
								value="<%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"":request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%>"
								onkeypress="return permitirSoloNumeros(event);"
								onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								maxlength="500" />
							</td>
							<% } %>
						</tr>
						<% } %>
					</table>
					<% } %>
				</div>
			</logic:equal>

			<logic:equal name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.NUMERO_DECIMAL%>">
				<div
					class="st<bean:write name="listaPreguntas" property="CTipoPregunta" />">
					<% if (listaPreguntas.getNElementos()==1 && listaPreguntas.getNSubElementos()==1 && (Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) || Constantes.NUM_SIMPLE.equals(listaPreguntas.getBNumeracion()))) {%>
					<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
					<input
						class="cuadroListadoCuestionario<bean:write name="listaPreguntas" property="NSubElementos" /> numero"
						type="text" id="pregunta<%=numPregunta%>_0_0"
						value="<%=request.getAttribute("pregunta"+numPregunta+"_0_0")==null?"":request.getAttribute("pregunta"+numPregunta+"_0_0")%>"
						onkeypress="return soloNumerosDecimales(event);"
						onchange="javascript:esNumeroDecimal(this);guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','0');"
						onblur="javascript:esNumeroDecimal(this);guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','0');"
						maxlength="500" />
					<% } %>
					<% } else {%>
					<table class="tablaRespuestas" cellpadding="2" cellspacing="2">
						<bean:size id="tamanoPosiblesRespuestasNumeroDecDt"
							name="listaPreguntas" property="listaPosiblesRespuestas" />
						<logic:notEqual name="tamanoPosiblesRespuestasNumeroDecDt"
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
							<td class="tamNumListado"><span> <%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
							</span></td>
							<% } %>
							<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
							<% if (Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {%>
							<td><%=Utilidades.obtenerNumeracionTotal(listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
							</td>
							<td>
								<% } else {%>
							
							<td colspan="2">
								<% } %> <input
								class="cuadroListadoCuestionario<bean:write name="listaPreguntas" property="NSubElementos" /> numero"
								type="text" id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
								value="<%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"":request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%>"
								onkeypress="return soloNumerosDecimales(event);"
								onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								maxlength="500" />
							</td>
							<% } %>
						</tr>
						<% } %>
					</table>
					<% } %>
				</div>
			</logic:equal>

			<logic:equal name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.CREDITOS%>">
				<div
					class="st<bean:write name="listaPreguntas" property="CTipoPregunta" />">
					<% if (listaPreguntas.getNElementos()==1 && listaPreguntas.getNSubElementos()==1 && (Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) || Constantes.NUM_SIMPLE.equals(listaPreguntas.getBNumeracion()))) {%>
					<input class="inputTextoCuestionario numero" type="text"
						id="pregunta<%=numPregunta%>_0_0"
						value="<%=request.getAttribute("pregunta"+numPregunta+"_0_0")==null?"":request.getAttribute("pregunta"+numPregunta+"_0_0")%>"
						onkeypress="return soloNumerosDecimales(event);"
						onchange="javascript:esNumeroDecimal(this);guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','0');"
						onblur="javascript:esNumeroDecimal(this);guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','0');"
						maxlength="5" />
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
								<% } %> <input
								class="cuadroListadoCuestionario<bean:write name="listaPreguntas" property="NSubElementos" /> numero"
								type="text" id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
								value="<%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"":request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%>"
								maxlength="5" onkeypress="return soloNumerosDecimales(event);"
								onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');" />
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
					<input class="inputCalendarioCuestionario" type="text"
						id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>" maxlength="10"
						onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
						onblur="return validarFecha(this);"
						value="<%=request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)==null?"":request.getAttribute("pregunta"+numPregunta+"_"+i+"_"+j)%>" />
					<span id="<%=Constantes.FECHA%>"> <a id="calFecha"
						href='javascript:show_Calendario("0","pregunta<%=numPregunta%>_<%=i%>_<%=j%>", document.forms[0].pregunta<%=numPregunta%>_<%=i%>_<%=j%>.value);'
						onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"><html:img
								src="imagenes/calendario.gif" border="0" title="Calendario" /></a>
					</span>
					<% } %>
					<% } %>
				</div>
			</logic:equal>

			<logic:notEqual name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.COMENTARIO%>">
			</logic:notEqual>

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


			<% if (!listaPreguntas.getCTipoPregunta().equals(Constantes.VACIO) && !listaPreguntas.getCTipoPregunta().equals(Constantes.COMENTARIO)) {
         numPregunta++;
      } %>
		</logic:iterate>
		<% if (numPregunta == 0) {
   request.setAttribute("accion", Constantes.VER_DETALLE);
   request.setAttribute("BFinalizado", Constantes.EST_CUEST_FINALIZAR);
   }
%>
	</logic:notEqual>
</logic:equal>
