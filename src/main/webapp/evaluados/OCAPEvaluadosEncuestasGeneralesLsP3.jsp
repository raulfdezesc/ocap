<%@ taglib uri="html.tld" prefix="html"%>

<%@taglib uri="bean.tld" prefix="bean"%><%@ taglib uri="logic.tld" prefix="logic"%>

<%@page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.encuestaCalidad.OCAPEncuestaCalidadOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@page import="java.util.ArrayList"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/ventanas.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"></script>

<script language="JavaScript">

function limpiar(){
   document.forms[0].CItinerario_id.value = '';
   document.forms[0].CProfesional_id.value = '';
   document.forms[0].respuesta_0.checked = false;
   document.forms[0].respuesta_1.checked = false;
   document.forms[0].respuesta_2.checked = false;
   document.forms[0].respuesta_3.checked = false;
   document.forms[0].respuesta_4.checked = false;
   document.forms[0].respuesta_5.checked = false;
   document.forms[0].respuesta_6.checked = false;
   document.forms[0].respuesta_7.checked = false;
   document.forms[0].respuesta_8.checked = false;
   document.forms[0].respuesta_9.checked = false;
   document.forms[0].respuesta_12.checked = false;
   document.forms[0].respuesta_13.checked = false;
   document.forms[0].respuesta_15.checked = false;
   document.forms[0].respuesta_16.checked = false;
   document.forms[0].respuesta_17.checked = false;
   document.forms[0].respuesta_18.checked = false;
   document.forms[0].respuesta_19.checked = false;
   document.forms[0].respuesta_20.checked = false;
   document.forms[0].respuesta_21.checked = false;
   document.forms[0].respuesta_22.checked = false;
   document.forms[0].respuesta_23.checked = false;
   document.forms[0].respuesta_24.checked = false;
   document.forms[0].respuesta_25.checked = false;
}

function verEncuesta(){
   //enviar('OCAPEncuestaCalidad.do?accion=irRellenar');
   lanzar('OCAPEncuestaCalidad.do?accion=irRellenar','encuesta',1280,1024,0,0,1,1);
}

function verPregunta(numPreg){
   enviar('OCAPEncuestaCalidad.do?accion=listarPreguntas&pregId='+numPreg);
}

function buscar(){
   if (comprobarPreguntaSeleccionada()) {
      if (document.forms[0].CItinerario_id.value != '' && document.forms[0].CProfesional_id.value != '')
         alert('S\u00F3lo puede filtrar por uno de los dos criterios.');
      else
         enviar('OCAPEncuestaCalidad.do?accion=listar');
   } else
      alert('Debe seleccionar alguna pregunta.');
}

function comprobarPreguntaSeleccionada(){
   var e= document.getElementsByTagName('input');
   var preguntaSeleccionada = false;
   for(var i=0;i<e.length && !preguntaSeleccionada;i++){
      if (e[i].type == 'checkbox' && e[i].checked==true){
         preguntaSeleccionada = true;
      }
   }
   return preguntaSeleccionada;
}

function seleccionaTodosChecks(){
   var e= document.getElementsByTagName('input');
   for(var i=0;i<e.length;i++){
      if (e[i].type == 'checkbox'){
         e[i].checked='true';
      }
   }
}

function borraItinerario(){
   document.forms[0].CItinerario_id.value = '';
}

function borraCategoria(){
   document.forms[0].CProfesional_id.value = '';
}

</script>
<logic:equal name="pregunta" property="CPreguntaId" value="9">
	<div class="subTituloItin letraGrande">6- &iquest;Cuando
		usted ha necesitado ayuda individualizada para resolver
		problemas t&eacute;cnicos/te&oacute;ricos de la
		aplicaci&oacute;n, el personal de la FQS se lo ha resuelto con
		rapidez y eficacia.?</div>
	<br />
	<div>
		<span class="separaMasIzq letraGrande">Completamente de
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total5" /> / <bean:write
				name="pregunta" property="porc5" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">De
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total4" /> / <bean:write
				name="pregunta" property="porc4" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">Ni de
			acuerdo ni en desacuerdo:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total3" /> / <bean:write
				name="pregunta" property="porc3" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total2" /> / <bean:write
				name="pregunta" property="porc2" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			completo desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total1" /> / <bean:write
				name="pregunta" property="porc1" /> %</span><br />
		<br />
	</div>
</logic:equal>
<logic:iterate id="listaTotalCuenta10"
	name="OCAPEncuestaCalidadForm"
	property="listaItinerariosPreguntasCuenta"
	type="OCAPEncuestaCalidadOT" indexId="index10">
	<logic:equal name="pregunta" property="CPreguntaId" value="9">
		<logic:iterate id="preguntaTotal10" name="listaTotalCuenta10"
			property="listaPreguntas" type="OCAPEncuestaCalidadOT">
			<logic:equal name="preguntaTotal10" property="CPreguntaId"
				value="9">
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">Total Respuestas: </span>
					<bean:write name="preguntaTotal10"
						property="totalRespuestas" />
				</div>
				<bean:define id="numeroRespuestas9" name="preguntaTotal10"
					property="totalRespuestas" />
				<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas9.toString());%>
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
				<br />
				<br />
			</logic:equal>
		</logic:iterate>
	</logic:equal>
</logic:iterate>
<logic:equal name="pregunta" property="CPreguntaId" value="12">
	<div class="subTituloItin letraGrande">9-
		&iquest;C&oacute;mo de adecuado le ha parecido el tiempo?</div>
	<br />
	<div>
		<span class="separaMasIzq letraGrande">Completamente de
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total5" /> / <bean:write
				name="pregunta" property="porc5" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">De
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total4" /> / <bean:write
				name="pregunta" property="porc4" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">Ni de
			acuerdo ni en desacuerdo:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total3" /> / <bean:write
				name="pregunta" property="porc3" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total2" /> / <bean:write
				name="pregunta" property="porc2" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			completo desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total1" /> / <bean:write
				name="pregunta" property="porc1" /> %</span><br />
		<br />
	</div>
</logic:equal>
<logic:iterate id="listaTotalCuenta11"
	name="OCAPEncuestaCalidadForm"
	property="listaItinerariosPreguntasCuenta"
	type="OCAPEncuestaCalidadOT" indexId="index11">
	<logic:equal name="pregunta" property="CPreguntaId" value="12">
		<logic:iterate id="preguntaTotal11" name="listaTotalCuenta11"
			property="listaPreguntas" type="OCAPEncuestaCalidadOT">
			<logic:equal name="preguntaTotal11" property="CPreguntaId"
				value="12">
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">Total Respuestas: </span>
					<bean:write name="preguntaTotal11"
						property="totalRespuestas" />
				</div>
				<bean:define id="numeroRespuestas10" name="preguntaTotal11"
					property="totalRespuestas" />
				<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas10.toString());%>
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
				<br />
				<br />
			</logic:equal>
		</logic:iterate>
	</logic:equal>
</logic:iterate>
<logic:equal name="pregunta" property="CPreguntaId" value="13">
	<div class="subTituloItin letraGrande">10- En cuanto a
		todo el proceso de acceso a grado de Carrera Profesional
		&iquest;le han parecido suficientes los plazos marcados?</div>
	<br />
	<div>
		<span class="separaMasIzq letraGrande">Completamente de
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total5" /> / <bean:write
				name="pregunta" property="porc5" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">De
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total4" /> / <bean:write
				name="pregunta" property="porc4" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">Ni de
			acuerdo ni en desacuerdo:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total3" /> / <bean:write
				name="pregunta" property="porc3" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total2" /> / <bean:write
				name="pregunta" property="porc2" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			completo desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total1" /> / <bean:write
				name="pregunta" property="porc1" /> %</span><br />
		<br />
	</div>
</logic:equal>
<logic:iterate id="listaTotalCuenta12"
	name="OCAPEncuestaCalidadForm"
	property="listaItinerariosPreguntasCuenta"
	type="OCAPEncuestaCalidadOT" indexId="index12">
	<logic:equal name="pregunta" property="CPreguntaId" value="13">
		<logic:iterate id="preguntaTotal12" name="listaTotalCuenta12"
			property="listaPreguntas" type="OCAPEncuestaCalidadOT">
			<logic:equal name="preguntaTotal12" property="CPreguntaId"
				value="13">
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">Total Respuestas: </span>
					<bean:write name="preguntaTotal12"
						property="totalRespuestas" />
				</div>
				<bean:define id="numeroRespuestas11" name="preguntaTotal12"
					property="totalRespuestas" />
				<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas11.toString());%>
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
				<br />
				<br />
			</logic:equal>
		</logic:iterate>
	</logic:equal>
</logic:iterate>
<logic:equal name="pregunta" property="CPreguntaId" value="15">
	<div class="subTituloItin letraGrande">12- De los
		siguientes organismos, se&ntilde;ale cu&aacute;les considera
		usted que intervienen en el proceso de evaluaci&oacute;n de la
		Carrera Profesional:</div>
	<br />
	<div>
		<span class="separaMasIzq letraGrande">S&oacute;lo FQS:</span>
		<span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total1" /></span><br />
		<br /> <span class="separaMasIzq letraGrande">FQS y
			Consejer&iacute;a de Sanidad:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total2" /></span><br />
		<br /> <span class="separaMasIzq letraGrande">FQS y
			Gerencia Regional de Salud:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total3" /></span><br />
		<br /> <span class="separaMasIzq letraGrande">Todas
			las respuesta anteriores:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total4" /></span><br />
		<br />
	</div>
</logic:equal>
<logic:iterate id="listaTotalCuenta13"
	name="OCAPEncuestaCalidadForm"
	property="listaItinerariosPreguntasCuenta"
	type="OCAPEncuestaCalidadOT" indexId="index13">
	<logic:equal name="pregunta" property="CPreguntaId" value="15">
		<logic:iterate id="preguntaTotal13" name="listaTotalCuenta13"
			property="listaPreguntas" type="OCAPEncuestaCalidadOT">
			<logic:equal name="preguntaTotal13" property="CPreguntaId"
				value="15">
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">Total Respuestas: </span>
					<bean:write name="preguntaTotal13"
						property="totalRespuestas" />
				</div>
				<bean:define id="numeroRespuestas13" name="preguntaTotal13"
					property="totalRespuestas" />
				<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas13.toString());%>
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
				<br />
				<br />
			</logic:equal>
		</logic:iterate>
	</logic:equal>
</logic:iterate>
<logic:equal name="pregunta" property="CPreguntaId" value="16">
	<div class="subTituloItin letraGrande">13- Indique su
		grado de satisfacci&oacute;n con la coordinaci&oacute;n entre
		los diferentes organismos que participan en el proceso de
		evaluaci&oacute;n de la Carrera Profesional.</div>
	<br />
	<div>
		<span class="separaMasIzq letraGrande">Completamente de
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total5" /> / <bean:write
				name="pregunta" property="porc5" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">De
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total4" /> / <bean:write
				name="pregunta" property="porc4" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">Ni de
			acuerdo ni en desacuerdo:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total3" /> / <bean:write
				name="pregunta" property="porc3" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total2" /> / <bean:write
				name="pregunta" property="porc2" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			completo desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total1" /> / <bean:write
				name="pregunta" property="porc1" /> %</span><br />
		<br />
	</div>
</logic:equal>
<logic:iterate id="listaTotalCuenta14"
	name="OCAPEncuestaCalidadForm"
	property="listaItinerariosPreguntasCuenta"
	type="OCAPEncuestaCalidadOT" indexId="index14">
	<logic:equal name="pregunta" property="CPreguntaId" value="16">
		<logic:iterate id="preguntaTotal14" name="listaTotalCuenta14"
			property="listaPreguntas" type="OCAPEncuestaCalidadOT">
			<logic:equal name="preguntaTotal14" property="CPreguntaId"
				value="16">
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">Total Respuestas: </span>
					<bean:write name="preguntaTotal14"
						property="totalRespuestas" />
				</div>
				<bean:define id="numeroRespuestas14" name="preguntaTotal14"
					property="totalRespuestas" />
				<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas14.toString());%>
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
				<br />
				<br />
			</logic:equal>
		</logic:iterate>
	</logic:equal>
</logic:iterate>
<logic:equal name="pregunta" property="CPreguntaId" value="17">
	<div class="subTituloItin letraGrande">14- Desde su punto
		de vista &iquest;la FQS ha realizado adecuadamente la labor
		que esperaba y contando con su confianza?</div>
	<br />
	<div>
		<span class="separaMasIzq letraGrande">Completamente de
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total5" /> / <bean:write
				name="pregunta" property="porc5" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">De
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total4" /> / <bean:write
				name="pregunta" property="porc4" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">Ni de
			acuerdo ni en desacuerdo:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total3" /> / <bean:write
				name="pregunta" property="porc3" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total2" /> / <bean:write
				name="pregunta" property="porc2" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			completo desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total1" /> / <bean:write
				name="pregunta" property="porc1" /> %</span><br />
		<br />
	</div>
</logic:equal>
<logic:iterate id="listaTotalCuenta15"
	name="OCAPEncuestaCalidadForm"
	property="listaItinerariosPreguntasCuenta"
	type="OCAPEncuestaCalidadOT" indexId="index15">
	<logic:equal name="pregunta" property="CPreguntaId" value="17">
		<logic:iterate id="preguntaTotal15" name="listaTotalCuenta15"
			property="listaPreguntas" type="OCAPEncuestaCalidadOT">
			<logic:equal name="preguntaTotal15" property="CPreguntaId"
				value="17">
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">Total Respuestas: </span>
					<bean:write name="preguntaTotal15"
						property="totalRespuestas" />
				</div>
				<bean:define id="numeroRespuestas15" name="preguntaTotal15"
					property="totalRespuestas" />
				<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas15.toString());%>
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
				<br />
				<br />
			</logic:equal>
		</logic:iterate>
	</logic:equal>
</logic:iterate>
<logic:equal name="pregunta" property="CPreguntaId" value="18">
	<div class="subTituloItin letraGrande">15- &iquest;Cree
		que la autoevaluaci&oacute;n realizada se corresponde con su
		perfil profesional?</div>
	<br />
	<div>
		<span class="separaMasIzq letraGrande">S&iacute;:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="totalS" /></span><br />
		<br /> <span class="separaMasIzq letraGrande">No:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="totalN" /></span><br />
		<br />
	</div>
	<div class="contadores letraGrande flotaDer">
		<a href="javascript:verPregunta('18');"><span>Ver
				respuestas</span></a>
	</div>
</logic:equal>
<logic:iterate id="listaTotalCuenta16"
	name="OCAPEncuestaCalidadForm"
	property="listaItinerariosPreguntasCuenta"
	type="OCAPEncuestaCalidadOT" indexId="index16">
	<logic:equal name="pregunta" property="CPreguntaId" value="18">
		<logic:iterate id="preguntaTotal16" name="listaTotalCuenta16"
			property="listaPreguntas" type="OCAPEncuestaCalidadOT">
			<logic:equal name="preguntaTotal16" property="CPreguntaId"
				value="18">
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">Total Respuestas: </span>
					<bean:write name="preguntaTotal16"
						property="totalRespuestas" />
				</div>
				<bean:define id="numeroRespuestas16" name="preguntaTotal16"
					property="totalRespuestas" />
				<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas16.toString());%>
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
				<br />
				<br />
			</logic:equal>
		</logic:iterate>
	</logic:equal>
</logic:iterate>
<logic:equal name="pregunta" property="CPreguntaId" value="19">
	<div class="subTituloItin letraGrande">16-
		&iquest;Considera que la autoevaluaci&oacute;n realizada por
		usted puede aportar aspectos positivos aplicables a su trabajo
		diario?</div>
	<br />
	<div>
		<span class="separaMasIzq letraGrande">Completamente de
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total5" /> / <bean:write
				name="pregunta" property="porc5" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">De
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total4" /> / <bean:write
				name="pregunta" property="porc4" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">Ni de
			acuerdo ni en desacuerdo:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total3" /> / <bean:write
				name="pregunta" property="porc3" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total2" /> / <bean:write
				name="pregunta" property="porc2" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			completo desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total1" /> / <bean:write
				name="pregunta" property="porc1" /> %</span><br />
		<br />
	</div>
</logic:equal>
<logic:iterate id="listaTotalCuenta17"
	name="OCAPEncuestaCalidadForm"
	property="listaItinerariosPreguntasCuenta"
	type="OCAPEncuestaCalidadOT" indexId="index17">
	<logic:equal name="pregunta" property="CPreguntaId" value="19">
		<logic:iterate id="preguntaTotal17" name="listaTotalCuenta17"
			property="listaPreguntas" type="OCAPEncuestaCalidadOT">
			<logic:equal name="preguntaTotal17" property="CPreguntaId"
				value="19">
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">Total Respuestas: </span>
					<bean:write name="preguntaTotal17"
						property="totalRespuestas" />
				</div>
				<bean:define id="numeroRespuestas17" name="preguntaTotal17"
					property="totalRespuestas" />
				<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas17.toString());%>
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
				<br />
				<br />
			</logic:equal>
		</logic:iterate>
	</logic:equal>
</logic:iterate>
<logic:equal name="pregunta" property="CPreguntaId" value="20">
	<% if (Constantes.NO.equals(titulo17)) { %>
	<div class="subTituloItin letraGrande">17- Valore de 1 a
		5 el proceso de autoevaluaci&oacute;n de acuerdo con:</div>
	<br />
	<%   titulo17 = Constantes.SI;
                   } %>
	<span class="subTituloItin separaIzqSub letraGrande">17.a-
		Contenido</span>
	<br />
	<br />
	<div>
		<span class="separaMasIzq letraGrande">Completamente de
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total5" /> / <bean:write
				name="pregunta" property="porc5" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">De
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total4" /> / <bean:write
				name="pregunta" property="porc4" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">Ni de
			acuerdo ni en desacuerdo:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total3" /> / <bean:write
				name="pregunta" property="porc3" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total2" /> / <bean:write
				name="pregunta" property="porc2" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			completo desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total1" /> / <bean:write
				name="pregunta" property="porc1" /> %</span><br />
		<br />
	</div>
</logic:equal>
<logic:iterate id="listaTotalCuenta18"
	name="OCAPEncuestaCalidadForm"
	property="listaItinerariosPreguntasCuenta"
	type="OCAPEncuestaCalidadOT" indexId="index18">
	<logic:equal name="pregunta" property="CPreguntaId" value="20">
		<logic:iterate id="preguntaTotal18" name="listaTotalCuenta18"
			property="listaPreguntas" type="OCAPEncuestaCalidadOT">
			<logic:equal name="preguntaTotal18" property="CPreguntaId"
				value="20">
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">Total Respuestas: </span>
					<bean:write name="preguntaTotal18"
						property="totalRespuestas" />
				</div>
				<bean:define id="numeroRespuestas18" name="preguntaTotal18"
					property="totalRespuestas" />
				<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas18.toString());%>
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
				<br />
				<br />
			</logic:equal>
		</logic:iterate>
	</logic:equal>
</logic:iterate>
<logic:equal name="pregunta" property="CPreguntaId" value="21">
	<% if (Constantes.NO.equals(titulo17)) { %>
	<div class="subTituloItin letraGrande">17- Valore de 1 a
		5 el proceso de autoevaluaci&oacute;n de acuerdo con:</div>
	<br />
	<%   titulo17 = Constantes.SI;
                   } %>
	<span class="subTituloItin separaIzqSub letraGrande">17.b-
		Grado de dificultad</span>
	<br />
	<br />
	<div>
		<span class="separaMasIzq letraGrande">Completamente de
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total5" /> / <bean:write
				name="pregunta" property="porc5" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">De
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total4" /> / <bean:write
				name="pregunta" property="porc4" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">Ni de
			acuerdo ni en desacuerdo:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total3" /> / <bean:write
				name="pregunta" property="porc3" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total2" /> / <bean:write
				name="pregunta" property="porc2" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			completo desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total1" /> / <bean:write
				name="pregunta" property="porc1" /> %</span><br />
		<br />
	</div>
</logic:equal>
<logic:iterate id="listaTotalCuenta19"
	name="OCAPEncuestaCalidadForm"
	property="listaItinerariosPreguntasCuenta"
	type="OCAPEncuestaCalidadOT" indexId="index19">
	<logic:equal name="pregunta" property="CPreguntaId" value="21">
		<logic:iterate id="preguntaTotal19" name="listaTotalCuenta19"
			property="listaPreguntas" type="OCAPEncuestaCalidadOT">
			<logic:equal name="preguntaTotal19" property="CPreguntaId"
				value="21">
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">Total Respuestas: </span>
					<bean:write name="preguntaTotal19"
						property="totalRespuestas" />
				</div>
				<bean:define id="numeroRespuestas19" name="preguntaTotal19"
					property="totalRespuestas" />
				<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas19.toString());%>
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
				<br />
				<br />
			</logic:equal>
		</logic:iterate>
	</logic:equal>
</logic:iterate>
<logic:equal name="pregunta" property="CPreguntaId" value="22">
	<% if (Constantes.NO.equals(titulo17)) { %>
	<div class="subTituloItin letraGrande">17- Valore de 1 a
		5 el proceso de autoevaluaci&oacute;n de acuerdo con:</div>
	<br />
	<%   titulo17 = Constantes.SI;
                   } %>
	<span class="subTituloItin separaIzqSub letraGrande">17.c-
		Tiempo invertido</span>
	<br />
	<br />
	<div>
		<span class="separaMasIzq letraGrande">Completamente de
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total5" /> / <bean:write
				name="pregunta" property="porc5" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">De
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total4" /> / <bean:write
				name="pregunta" property="porc4" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">Ni de
			acuerdo ni en desacuerdo:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total3" /> / <bean:write
				name="pregunta" property="porc3" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total2" /> / <bean:write
				name="pregunta" property="porc2" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			completo desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total1" /> / <bean:write
				name="pregunta" property="porc1" /> %</span><br />
		<br />
	</div>
</logic:equal>
<logic:iterate id="listaTotalCuenta20"
	name="OCAPEncuestaCalidadForm"
	property="listaItinerariosPreguntasCuenta"
	type="OCAPEncuestaCalidadOT" indexId="index20">
	<logic:equal name="pregunta" property="CPreguntaId" value="22">
		<logic:iterate id="preguntaTotal20" name="listaTotalCuenta20"
			property="listaPreguntas" type="OCAPEncuestaCalidadOT">
			<logic:equal name="preguntaTotal20" property="CPreguntaId"
				value="22">
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">Total Respuestas: </span>
					<bean:write name="preguntaTotal20"
						property="totalRespuestas" />
				</div>
				<bean:define id="numeroRespuestas20" name="preguntaTotal20"
					property="totalRespuestas" />
				<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas20.toString());%>
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
				<br />
				<br />
			</logic:equal>
		</logic:iterate>
	</logic:equal>
</logic:iterate>
<logic:equal name="pregunta" property="CPreguntaId" value="23">
	<% if (Constantes.NO.equals(titulo17)) { %>
	<div class="subTituloItin letraGrande">17- Valore de 1 a
		5 el proceso de autoevaluaci&oacute;n de acuerdo con:</div>
	<br />
	<%   titulo17 = Constantes.SI;
                   } %>
	<span class="subTituloItin separaIzqSub letraGrande">17.d-
		Presentaci&oacute;n de la misma</span>
	<br />
	<br />
	<div>
		<span class="separaMasIzq letraGrande">Completamente de
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total5" /> / <bean:write
				name="pregunta" property="porc5" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">De
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total4" /> / <bean:write
				name="pregunta" property="porc4" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">Ni de
			acuerdo ni en desacuerdo:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total3" /> / <bean:write
				name="pregunta" property="porc3" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total2" /> / <bean:write
				name="pregunta" property="porc2" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			completo desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total1" /> / <bean:write
				name="pregunta" property="porc1" /> %</span><br />
		<br />
	</div>
</logic:equal>
<logic:iterate id="listaTotalCuenta21"
	name="OCAPEncuestaCalidadForm"
	property="listaItinerariosPreguntasCuenta"
	type="OCAPEncuestaCalidadOT" indexId="index21">
	<logic:equal name="pregunta" property="CPreguntaId" value="23">
		<logic:iterate id="preguntaTotal21" name="listaTotalCuenta21"
			property="listaPreguntas" type="OCAPEncuestaCalidadOT">
			<logic:equal name="preguntaTotal21" property="CPreguntaId"
				value="23">
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">Total Respuestas: </span>
					<bean:write name="preguntaTotal21"
						property="totalRespuestas" />
				</div>
				<bean:define id="numeroRespuestas21" name="preguntaTotal21"
					property="totalRespuestas" />
				<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas21.toString());%>
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
				<br />
				<br />
			</logic:equal>
		</logic:iterate>
	</logic:equal>
</logic:iterate>
<logic:equal name="pregunta" property="CPreguntaId" value="24">
	<div class="subTituloItin letraGrande">18- El
		m&eacute;todo empleado para el acceso a grado de Carrera
		Profesional en Castilla y Le&oacute;n, &iquest;ha respondido a
		las expectativas que ten&iacute;a depositadas en &eacute;l?</div>
	<br />
	<div>
		<span class="separaMasIzq letraGrande">S&iacute;:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="totalS" /></span><br />
		<br /> <span class="separaMasIzq letraGrande">No:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="totalN" /></span><br />
		<br />
	</div>
	<div class="contadores letraGrande flotaDer">
		<a href="javascript:verPregunta('24');"><span>Ver
				respuestas</span></a>
	</div>
</logic:equal>
<logic:iterate id="listaTotalCuenta22"
	name="OCAPEncuestaCalidadForm"
	property="listaItinerariosPreguntasCuenta"
	type="OCAPEncuestaCalidadOT" indexId="index22">
	<logic:equal name="pregunta" property="CPreguntaId" value="24">
		<logic:iterate id="preguntaTotal22" name="listaTotalCuenta22"
			property="listaPreguntas" type="OCAPEncuestaCalidadOT">
			<logic:equal name="preguntaTotal22" property="CPreguntaId"
				value="24">
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">Total Respuestas: </span>
					<bean:write name="preguntaTotal22"
						property="totalRespuestas" />
				</div>
				<bean:define id="numeroRespuestas22" name="preguntaTotal22"
					property="totalRespuestas" />
				<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas22.toString());%>
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
				<br />
				<br />
			</logic:equal>
		</logic:iterate>
	</logic:equal>
</logic:iterate>
<logic:equal name="pregunta" property="CPreguntaId" value="25">
	<div class="subTituloItin letraGrande">19- Valore de 1 a
		5 la confianza que usted deposita en el sistema de
		evaluaci&oacute;n del Grado de Carrera Profesional en Castilla
		y Le&oacute;n.</div>
	<br />
	<div>
		<span class="separaMasIzq letraGrande">Completamente de
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total5" /> / <bean:write
				name="pregunta" property="porc5" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">De
			acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total4" /> / <bean:write
				name="pregunta" property="porc4" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">Ni de
			acuerdo ni en desacuerdo:</span> <span
			class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total3" /> / <bean:write
				name="pregunta" property="porc3" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total2" /> / <bean:write
				name="pregunta" property="porc2" /> %</span><br />
		<br /> <span class="separaMasIzq letraGrande">En
			completo desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
				name="pregunta" property="total1" /> / <bean:write
				name="pregunta" property="porc1" /> %</span><br />
		<br />
	</div>
</logic:equal>
<logic:iterate id="listaTotalCuenta23"
	name="OCAPEncuestaCalidadForm"
	property="listaItinerariosPreguntasCuenta"
	type="OCAPEncuestaCalidadOT" indexId="index23">
	<logic:equal name="pregunta" property="CPreguntaId" value="25">
		<logic:iterate id="preguntaTotal23" name="listaTotalCuenta23"
			property="listaPreguntas" type="OCAPEncuestaCalidadOT">
			<logic:equal name="preguntaTotal23" property="CPreguntaId"
				value="25">
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">Total Respuestas: </span>
					<bean:write name="preguntaTotal23"
						property="totalRespuestas" />
				</div>
				<bean:define id="numeroRespuestas23" name="preguntaTotal23"
					property="totalRespuestas" />
				<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas23.toString());%>
				<div class="contadores letraGrande flotaIzq">
					<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
				<br />
				<br />
			</logic:equal>
		</logic:iterate>
	</logic:equal>
</logic:iterate>