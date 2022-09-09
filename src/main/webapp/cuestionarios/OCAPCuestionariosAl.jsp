<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.util.Utilidades"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.respuestas.OCAPRespuestasOT"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<logic:equal name="accion" value="<%=Constantes.INSERTAR%>">
	<% int numPregunta = 0;
   int numPreguntasNivel0 = 0; 
   int numPreguntasNivel1 = 0;
   int numPreguntasNivel2 = 0;
%>
	<!-- Dependiendo del tipo de la pregunta crearemos el elemento de formulario correspondiente, un radio button, un text, un textarea, un select...-->
	<logic:iterate id="listaPreguntas" name="OCAPCuestionariosForm"
		property="listadoPreguntas" type="OCAPCuestionariosOT">
		<logic:equal name="listaPreguntas" property="CTipoPregunta"
			value="<%=Constantes.VACIO%>">
			<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion())) {%>
			<span class="stPregunta"><label
				class="nivel<bean:write name="listaPreguntas" property="NNivel" />">
					<% if (!Constantes.NUM_ROMANA.equals(listaPreguntas.getBNumeracion())) {%>
					<% if (listaPreguntas.getNNivel()==0) { %> <%=++numPreguntasNivel0%>
					<% numPreguntasNivel1= 0; numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==1) { %>
					<%=numPreguntasNivel0%>.<%=++numPreguntasNivel1%> <% numPreguntasNivel2 = 0; %>
					<%}%> <% if (listaPreguntas.getNNivel()==2) { %> <%=numPreguntasNivel0%>.<%=numPreguntasNivel1%>.<%=++numPreguntasNivel2%>
					<%}%> <% } else { %> <% if (listaPreguntas.getNNivel()==0) { %> <%=Utilidades.getNumeroRomano(++numPreguntasNivel0)%>
					<% numPreguntasNivel1= 0; numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==1) { %>
					<%=Utilidades.getNumeroRomano(numPreguntasNivel0)%>.<%=Utilidades.getNumeroRomano(++numPreguntasNivel1)%>
					<% numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==2) { %>
					<%=Utilidades.getNumeroRomano(numPreguntasNivel0)%>.<%=Utilidades.getNumeroRomano(numPreguntasNivel1)%>.<%=Utilidades.getNumeroRomano(++numPreguntasNivel2)%>
					<%}%> <% } %> - <% } else { %> <span class="stTitulo"><label
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
							<% if (!Constantes.NUM_ROMANA.equals(listaPreguntas.getBNumeracion())) {%>
							<% if (listaPreguntas.getNNivel()==0) { %> <%=++numPreguntasNivel0%>
							<% numPreguntasNivel1= 0; numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==1) { %>
							<%=numPreguntasNivel0%>.<%=++numPreguntasNivel1%> <% numPreguntasNivel2 = 0; %>
							<%}%> <% if (listaPreguntas.getNNivel()==2) { %> <%=numPreguntasNivel0%>.<%=numPreguntasNivel1%>.<%=++numPreguntasNivel2%>
							<%}%> <% } else { %> <% if (listaPreguntas.getNNivel()==0) { %> <%=Utilidades.getNumeroRomano(++numPreguntasNivel0)%>
							<% numPreguntasNivel1= 0; numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==1) { %>
							<%=Utilidades.getNumeroRomano(numPreguntasNivel0)%>.<%=Utilidades.getNumeroRomano(++numPreguntasNivel1)%>
							<% numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==2) { %>
							<%=Utilidades.getNumeroRomano(numPreguntasNivel0)%>.<%=Utilidades.getNumeroRomano(numPreguntasNivel1)%>.<%=Utilidades.getNumeroRomano(++numPreguntasNivel2)%>
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
					<% if (listaPreguntas.getNElementos()>1) { %>
					<% for (int i =0; i<listaPreguntas.getNElementos(); i++) {%>
					<tr>
						<td><span class="tamNumListado"> <%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
						</span></td>
						<logic:iterate id="lPosiblesRespuestas" name="listaPreguntas"
							property="listaPosiblesRespuestas" type="OCAPRespuestasOT">
							<td>
								<% if (Constantes.SI.equals(listaPreguntas.getBCorto())) { %> <input
								type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
								id="pregunta<%=numPregunta%>_<%=i%>_0"
								value="<%=lPosiblesRespuestas.getDValor()%>"
								onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');" />
								<span class="textoRadioCuestionarioCorto"><bean:write
										name="lPosiblesRespuestas" property="DNombre" /></span> <% } else { %>
								<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
								id="pregunta<%=numPregunta%>_<%=i%>_0"
								value="<%=lPosiblesRespuestas.getDValor()%>"
								onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');" />
								<span class="textoRadioCuestionarioLargo"><bean:write
										name="lPosiblesRespuestas" property="DNombre" /></span> <% } %>
							</td>
						</logic:iterate>
					</tr>
					<% } %>
					<% } else { %>
					<% for (int i =0; i<listaPreguntas.getNElementos(); i++) {%>
					<tr>
						<td><logic:iterate id="lPosiblesRespuestas"
								name="listaPreguntas" property="listaPosiblesRespuestas"
								type="OCAPRespuestasOT">
								<% if (Constantes.SI.equals(listaPreguntas.getBCorto())) { %>
								<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
									value="<%=lPosiblesRespuestas.getDValor()%>"
									onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');" />
								<span class="textoRadioCuestionarioCorto"><bean:write
										name="lPosiblesRespuestas" property="DNombre" /></span>
								<% } else { %>
								<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
									value="<%=lPosiblesRespuestas.getDValor()%>"
									onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');" />
								<span class="textoRadioCuestionarioLargo"><bean:write
										name="lPosiblesRespuestas" property="DNombre" /></span>
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
				<% if (listaPreguntas.getNElementos()>1) { %>
				<% for (int i =0; i<listaPreguntas.getNElementos(); i++) {%>
				<span class="tamNumListado"> <%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
				</span>
				<logic:iterate id="lPosiblesRespuestas" name="listaPreguntas"
					property="listaPosiblesRespuestas" type="OCAPRespuestasOT">
					<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
						id="pregunta<%=numPregunta%>_<%=i%>_0"
						value="<%=lPosiblesRespuestas.getDValor()%>"
						onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');" />
					<span class="textoRadioCuestionario"><bean:write
							name="lPosiblesRespuestas" property="DNombre" /></span>
				</logic:iterate>
				<textarea disabled readonly class="cuadroTACuestionario"
					id="pregunta<%=numPregunta%>_<%=i%>_1" cols="90%" rows="3%"
					onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','1');"
					onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','1');"
					onkeypress="javascript:return longMaxTextarea(this);"
					onclick="javascript:return longMaxTextarea(this);"
					onkeydown="javascript:return longMaxTextarea(this);"
					onkeyup="javascript:return longMaxTextarea(this);"></textarea>
				<% } %>
				<% } else { %>
				<% for (int i =0; i<listaPreguntas.getNElementos(); i++) {%>
				<logic:iterate id="lPosiblesRespuestas" name="listaPreguntas"
					property="listaPosiblesRespuestas" type="OCAPRespuestasOT">
					<input type="radio" name="pregunta<%=numPregunta%>_<%=i%>_0"
						id="pregunta<%=numPregunta%>_<%=i%>_0"
						value="<%=lPosiblesRespuestas.getDValor()%>"
						onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','0','<%=lPosiblesRespuestas.getDValor()%>');" />
					<span class="textoRadioLinea"><bean:write
							name="lPosiblesRespuestas" property="DNombre" /></span>
				</logic:iterate>
				<textarea disabled readonly class="cuadroTACuestionario"
					id="pregunta<%=numPregunta%>_<%=i%>_1" cols="87%" rows="3%"
					onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','1');"
					onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','1');"
					onkeypress="javascript:return longMaxTextarea(this);"
					onclick="javascript:return longMaxTextarea(this);"
					onkeydown="javascript:return longMaxTextarea(this);"
					onkeyup="javascript:return longMaxTextarea(this);"></textarea>
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
							<input type="checkbox"
							id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
							onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');" />
							<% } %>
							<div>
								<label
									class="nivel<bean:write name="listaPreguntas" property="NNivel" />">
									<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion())) {%>
									<% if (!Constantes.NUM_ROMANA.equals(listaPreguntas.getBNumeracion())) {%>
									<% if (listaPreguntas.getNNivel()==0) { %> <%=++numPreguntasNivel0%>
									<% numPreguntasNivel1= 0; numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==1) { %>
									<%=numPreguntasNivel0%>.<%=++numPreguntasNivel1%> <% numPreguntasNivel2 = 0; %>
									<%}%> <% if (listaPreguntas.getNNivel()==2) { %> <%=numPreguntasNivel0%>.<%=numPreguntasNivel1%>.<%=++numPreguntasNivel2%>
									<%}%> <% } else { %> <% if (listaPreguntas.getNNivel()==0) { %> <%=Utilidades.getNumeroRomano(++numPreguntasNivel0)%>
									<% numPreguntasNivel1= 0; numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==1) { %>
									<%=Utilidades.getNumeroRomano(numPreguntasNivel0)%>.<%=Utilidades.getNumeroRomano(++numPreguntasNivel1)%>
									<% numPreguntasNivel2 = 0; %> <%}%> <% if (listaPreguntas.getNNivel()==2) { %>
									<%=Utilidades.getNumeroRomano(numPreguntasNivel0)%>.<%=Utilidades.getNumeroRomano(numPreguntasNivel1)%>.<%=Utilidades.getNumeroRomano(++numPreguntasNivel2)%>
									<%}%> <%}%> - <% } %> <bean:write name="listaPreguntas"
										property="DPregunta" filter="false" />
								</label>
							</div> <logic:notEqual name="listaPreguntas" property="DObservaciones"
								value="">
								<span class="observacionesPregunta"><bean:write
										name="listaPreguntas" property="DObservaciones" filter="false" /></span>
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
							<span class="tamNumListado"> <%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
							</span>
							<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
							<input type="checkbox"
								id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
								onclick="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');" />
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
				<% String estiloTextarea = "cuadroTACuestionario"; %>
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
					onkeyup="javascript:return longMaxTextarea(this);"></textarea>
				<% } %>
				<% } else { %>
				<table class="tablaRespuestas" cellpadding="2" cellspacing="2">
					<bean:size id="tamanoPosiblesRespuestasTextarea"
						name="listaPreguntas" property="listaPosiblesRespuestas" />
					<logic:notEqual name="tamanoPosiblesRespuestasTextarea" value="0">
						<tr>
							<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) { %>
							<th></th>
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
						<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) { 
                        estiloTextarea = "cuadroTANCuestionario";
                     %>
						<td class="contenedorNumero">
							<div class="tamNumListado">
								<%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
							</div>
						</td>
						<% } %>
						<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
						<% if (Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) { %>
						<td class="contenedorNumero">
							<div class="tamNumListado">
								<%=Utilidades.obtenerNumeracionTotal(listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
							</div>
						</td>
						<% } else { %>
						<td class="contenedorTexto" colspan="2">
							<% } %> <textarea
								class="<%=estiloTextarea%><bean:write name="listaPreguntas" property="NSubElementos" />"
								id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
								onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
								cols="45%" rows="3%"
								onkeypress="javascript:return longMaxTextarea(this);"
								onclick="javascript:return longMaxTextarea(this);"
								onkeydown="javascript:return longMaxTextarea(this);"
								onkeyup="javascript:return longMaxTextarea(this);"></textarea>
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
				<bean:size id="tamanoPosiblesRespuestas" name="listaPreguntas"
					property="listaPosiblesRespuestas" />
				<% if (listaPreguntas.getNElementos()==1 && listaPreguntas.getNSubElementos()==1 && (Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) || Constantes.NUM_SIMPLE.equals(listaPreguntas.getBNumeracion()))) {%>
				<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
				<% if (Constantes.SI.equals(listaPreguntas.getBCorto())) { %>
				<input class="inputTextoCuestionario corto" type="text"
					id="pregunta<%=numPregunta%>_0_<%=j%>"
					onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','<%=j%>');"
					onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','<%=j%>');"
					maxlength="500" />
				<% } else { %>
				<input class="inputTextoCuestionario" type="text"
					id="pregunta<%=numPregunta%>_0_<%=j%>"
					onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','<%=j%>');"
					onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','<%=j%>');"
					maxlength="500" />
				<% } %>
				<% } %>
				<% } else { %>
				<table class="tablaRespuestas" cellpadding="2" cellspacing="2">
					<logic:notEqual name="tamanoPosiblesRespuestas" value="0">
						<tr>
							<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) { %>
							<% estiloText = "cuadroListadoNCuestionario"; %>
							<th class="contenedorNumero"></th>
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
						<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) {  %>
						<% estiloText = "cuadroListadoNCuestionario"; %>
						<td class="contenedorNumero"><span class="tamNumListado">
								<%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
						</span></td>
						<% } %>
						<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
						<% if (Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) { %>
						<td class="contenedorNumero">
							<% estiloText = "cuadroListadoNCuestionario"; %> <span
							class="tamNumListado"> <%=Utilidades.obtenerNumeracionTotal(listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
						</span>
						</td>
						<td>
							<% } else { %>
						
						<td class="contenedorDato" colspan="2">
							<% } %> <% if (Constantes.SI.equals(listaPreguntas.getBCorto())) { %>
							<input
							class="<%=estiloText%><bean:write name="listaPreguntas" property="NSubElementos" /> corto"
							style="border: 0.5px #004B98 solid; width: 95%;" type="text"
							id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
							onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
							onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
							maxlength="500" /> <% } else { %> <input
							class="<%=estiloText%><bean:write name="listaPreguntas" property="NSubElementos" />"
							style="border: 0.5px #004B98 solid; width: 95%;" type="text"
							id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
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
			value="<%=Constantes.TEXT_NUMERO%>">
			<div
				class="st<bean:write name="listaPreguntas" property="CTipoPregunta" />">
				<bean:size id="tamanoPosiblesRespuestasTextNumero"
					name="listaPreguntas" property="listaPosiblesRespuestas" />
				<% if (listaPreguntas.getNElementos()==1 && listaPreguntas.getNSubElementos()==1 && (Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) || Constantes.NUM_SIMPLE.equals(listaPreguntas.getBNumeracion()))) {%>
				<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
				<% if (Constantes.SI.equals(listaPreguntas.getBCorto())) { %>
				<input class="inputTextoCuestionario corto" type="text"
					id="pregunta<%=numPregunta%>_0_<%=j%>"
					onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','<%=j%>');"
					onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','<%=j%>');"
					maxlength="500" />
				<% } else { %>
				<input class="inputTextoCuestionario" type="text"
					id="pregunta<%=numPregunta%>_0_<%=j%>"
					onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','<%=j%>');"
					onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','<%=j%>');"
					maxlength="500" />
				<% } %>
				<% } %>
				<% } else { %>
				<table class="tablaRespuestas" cellpadding="2" cellspacing="2">
					<logic:notEqual name="tamanoPosiblesRespuestasTextNumero" value="0">
						<tr>
							<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) { %>
							<th class="columnaNumeracion"></th>
							<% } %>
							<% for (int cuentaRespuestas = 0; cuentaRespuestas < listaPreguntas.getListaPosiblesRespuestas().size(); cuentaRespuestas++) {
                        OCAPRespuestasOT lPosiblesRespuestas = (OCAPRespuestasOT)(listaPreguntas.getListaPosiblesRespuestas().get(cuentaRespuestas)); 
                        if (cuentaRespuestas == 0) { %>
							<th colspan="2" class="subtituloListadoCuestionarioLargo"><%=lPosiblesRespuestas.getDNombre()%>
							</th>
							<% } else if (cuentaRespuestas != listaPreguntas.getListaPosiblesRespuestas().size()-1){ %>
							<th colspan="2" class="subtituloListadoCuestionarioCorto"><%=lPosiblesRespuestas.getDNombre()%>
							</th>
							<% } else { %>
							<th colspan="2" class="subtituloListadoCuestionarioMediano">
								<%=lPosiblesRespuestas.getDNombre()%>
							</th>
							<%}
                     } %>
						</tr>
					</logic:notEqual>
					<% for (int i =0; i<listaPreguntas.getNElementos(); i++) {%>
					<tr>
						<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) { %>
						<td class="tamNumListado"><span> <%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
						</span></td>
						<% } %>
						<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
						<% if (Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) { %>
						<td class="tamNumListado"><span> <%=Utilidades.obtenerNumeracionTotal(listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
						</span></td>
						<td>
							<% } else { %>
						
						<td colspan="2">
							<% } %> <% if (j==0) { %> <input class="cuadroListadoLargo"
							type="text" id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
							onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
							onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
							maxlength="500" /> <% } else { %> <input
							class="cuadroListadoCorto" type="text"
							id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
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
				<input type="text"
					class="cuadroListadoCuestionario<bean:write name="listaPreguntas" property="NSubElementos" /> numero"
					id="pregunta<%=numPregunta%>_0_0"
					onkeypress="return permitirSoloNumeros(event);"
					onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','0');"
					onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','0');"
					maxlength="500" />
				<% } %>
				<% } else { %>
				<table class="tablaRespuestas" cellpadding="2" cellspacing="2">
					<bean:size id="tamanoPosiblesRespuestasNumero"
						name="listaPreguntas" property="listaPosiblesRespuestas" />
					<logic:notEqual name="tamanoPosiblesRespuestasNumero" value="0">
						<tr>
							<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) { %>
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
						<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) { %>
						<td class="tamNumListado"><span> <%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
						</span></td>
						<% } %>
						<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
						<% if (Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) { %>
						<td><%=Utilidades.obtenerNumeracionTotal(listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
						</td>
						<td>
							<% } else { %>
						
						<td colspan="2">
							<% } %> <input type="text" class="numero"
							id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
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
				<input type="text"
					class="cuadroListadoCuestionario<bean:write name="listaPreguntas" property="NSubElementos" /> numero"
					id="pregunta<%=numPregunta%>_0_0"
					onkeypress="return soloNumerosDecimales(event);"
					onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','0');"
					onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','0');"
					maxlength="500" />
				<% } %>
				<% } else { %>
				<table class="tablaRespuestas" cellpadding="2" cellspacing="2">
					<bean:size id="tamanoPosiblesRespuestasNumeroDec"
						name="listaPreguntas" property="listaPosiblesRespuestas" />
					<logic:notEqual name="tamanoPosiblesRespuestasNumeroDec" value="0">
						<tr>
							<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) { %>
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
						<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) { %>
						<td class="tamNumListado"><span> <%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
						</span></td>
						<% } %>
						<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
						<% if (Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) { %>
						<td><%=Utilidades.obtenerNumeracionTotal(listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
						</td>
						<td>
							<% } else { %>
						
						<td colspan="2">
							<% } %> <input type="text"
							class="cuadroListadoCuestionario<bean:write name="listaPreguntas" property="NSubElementos" /> numero"
							id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
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
				<input type="text"
					class="cuadroListadoCuestionario<bean:write name="listaPreguntas" property="NSubElementos" /> numero"
					id="pregunta<%=numPregunta%>_0_0"
					onkeypress="return soloNumerosDecimales(event);"
					onchange="javascript:esNumeroDecimal(this);guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','0');"
					onblur="javascript:esNumeroDecimal(this);guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','0','0');"
					maxlength="5" />
				<% } else { %>
				<table class="tablaRespuestas" cellpadding="2" cellspacing="2">
					<bean:size id="tamanoPosiblesRespuestasCreditos"
						name="listaPreguntas" property="listaPosiblesRespuestas" />
					<logic:notEqual name="tamanoPosiblesRespuestasCreditos" value="0">
						<tr>
							<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) { %>
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
						<% if (!Constantes.NUM_NO.equals(listaPreguntas.getBNumeracion()) && !Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) { %>
						<td class="tamNumListado"><span> <%=Utilidades.obtenerNumeracion(listaPreguntas.getBNumeracion(), listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
						</span></td>
						<% } %>
						<% for (int j =0; j<listaPreguntas.getNSubElementos(); j++) {%>
						<% if (Constantes.NUM_TOTAL.equals(listaPreguntas.getBNumeracion())) { %>
						<td><%=Utilidades.obtenerNumeracionTotal(listaPreguntas.getNNivel(), listaPreguntas.getDTextoElementos(), i, i, numPreguntasNivel0, numPreguntasNivel1, numPreguntasNivel2)%>
						</td>
						<td>
							<% } else { %>
						
						<td colspan="2">
							<% } %> <input type="text"
							class="cuadroListadoCuestionario<bean:write name="listaPreguntas" property="NSubElementos" /> numero"
							id="pregunta<%=numPregunta%>_<%=i%>_<%=j%>"
							onkeypress="return soloNumerosDecimales(event);"
							onchange="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
							onblur="javascript:guardarRespuesta('<%=numPregunta%>','<%=listaPreguntas.getCTipoPregunta()%>','<%=i%>','<%=j%>');"
							maxlength="5" />
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
					onblur="return validarFecha(this);" /> <span
					id="<%=Constantes.FECHA%><%=numPregunta%>"> <a id="calFecha"
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
			<logic:notEqual name="listaPreguntas" property="CTipoPregunta"
				value="<%=Constantes.VACIO%>">
				<% numPregunta++;%>
			</logic:notEqual>
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

	</logic:iterate>
	<% if (numPregunta == 0) {
   request.setAttribute("accion", Constantes.VER_DETALLE);
   request.setAttribute("BFinalizado", Constantes.EST_CUEST_FINALIZAR);
   request.setAttribute("insertar", Constantes.SI);
   request.setAttribute("deshabilitadoSinPreguntas", Constantes.SI);
   }
%>
</logic:equal>
