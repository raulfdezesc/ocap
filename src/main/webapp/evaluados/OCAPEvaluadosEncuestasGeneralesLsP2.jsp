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

<% String titulo5= Constantes.NO; 
   String titulo17= Constantes.NO; 
   int contEnBlanco = 0;
%>
<bean:define id="contEncuestas" name="OCAPEncuestaCalidadForm"
	property="numTotalEncuestas" />
<logic:iterate id="listaTotal" name="OCAPEncuestaCalidadForm"
	property="listaItinerariosPreguntas" type="OCAPEncuestaCalidadOT"
	indexId="index">
	<logic:iterate id="pregunta" name="listaTotal"
		property="listaPreguntas" type="OCAPEncuestaCalidadOT">
		<div class="tituloGrado">
			<bean:write name="listaTotal" property="DItinerario" />
		</div>
		<logic:equal name="pregunta" property="CPreguntaId" value="0">
			<div class="subTituloItin letraGrande">1- &iquest;Se le
				ha informado adecuadamente sobre la autoevaluaci&oacute;n en
				la aplicaci&oacute;n inform&aacute;tica?</div>
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
		<logic:iterate id="listaTotalCuenta1"
			name="OCAPEncuestaCalidadForm"
			property="listaItinerariosPreguntasCuenta"
			type="OCAPEncuestaCalidadOT" indexId="index1">
			<logic:equal name="pregunta" property="CPreguntaId" value="0">
				<logic:iterate id="preguntaTotal1" name="listaTotalCuenta1"
					property="listaPreguntas" type="OCAPEncuestaCalidadOT">
					<logic:equal name="preguntaTotal1" property="CPreguntaId"
						value="0">
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">Total Respuestas: </span>
							<bean:write name="preguntaTotal1"
								property="totalRespuestas" />
						</div>
						<bean:define id="numeroRespuestas0" name="preguntaTotal1"
							property="totalRespuestas" />
						<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas0.toString());%>
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
						<br />
						<br />
					</logic:equal>
				</logic:iterate>
			</logic:equal>
		</logic:iterate>
		<logic:equal name="pregunta" property="CPreguntaId" value="1">
			<div class="subTituloItin letraGrande">2- &iquest;Las
				instrucciones facilitadas le han sido &uacute;tiles para
				llevar a cabo el proceso de autoevaluaci&oacute;n?</div>
			<br />
			<div>
				<span class="separaMasIzq letraGrande">Completamente de
					acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total5" /> / <bean:write
						name="pregunta" property="porc5" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">De
					acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total4" /> / <bean:write
						name="pregunta" property="porc4" /> %</span><br /> <br /> <span
					class="separaMasIzq letraGrande">Ni de acuerdo ni en
					desacuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
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
		<logic:iterate id="listaTotalCuenta2"
			name="OCAPEncuestaCalidadForm"
			property="listaItinerariosPreguntasCuenta"
			type="OCAPEncuestaCalidadOT" indexId="index2">
			<logic:equal name="pregunta" property="CPreguntaId" value="1">
				<logic:iterate id="preguntaTotal2" name="listaTotalCuenta2"
					property="listaPreguntas" type="OCAPEncuestaCalidadOT">
					<logic:equal name="preguntaTotal2" property="CPreguntaId"
						value="1">
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">Total Respuestas: </span>
							<bean:write name="preguntaTotal2"
								property="totalRespuestas" />
						</div>
						<bean:define id="numeroRespuestas1" name="preguntaTotal2"
							property="totalRespuestas" />
						<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas1.toString());%>
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
						<br />
						<br />
					</logic:equal>
				</logic:iterate>
			</logic:equal>
		</logic:iterate>
		<logic:equal name="pregunta" property="CPreguntaId" value="2">
			<div class="subTituloItin letraGrande">3- Si en
				alg&uacute;n momento usted ha necesitado ayuda, &iquest;le ha
				resultado sencillo acceder a ella a trav&eacute;s de la
				plataforma inform&aacute;tica?</div>
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
		<logic:iterate id="listaTotalCuenta3"
			name="OCAPEncuestaCalidadForm"
			property="listaItinerariosPreguntasCuenta"
			type="OCAPEncuestaCalidadOT" indexId="index3">
			<logic:equal name="pregunta" property="CPreguntaId" value="2">
				<logic:iterate id="preguntaTotal3" name="listaTotalCuenta3"
					property="listaPreguntas" type="OCAPEncuestaCalidadOT">
					<logic:equal name="preguntaTotal3" property="CPreguntaId"
						value="2">
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">Total Respuestas: </span>
							<bean:write name="preguntaTotal3"
								property="totalRespuestas" />
						</div>
						<bean:define id="numeroRespuestas2" name="preguntaTotal3"
							property="totalRespuestas" />
						<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas2.toString());%>
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
						<br />
						<br />
					</logic:equal>
				</logic:iterate>
			</logic:equal>
		</logic:iterate>
		<logic:equal name="pregunta" property="CPreguntaId" value="3">
			<div class="subTituloItin letraGrande">4- Valore de 1 a 5
				su satisfacci&oacute;n con los sistemas de informaci&oacute;n
				y la ayuda de la aplicaci&oacute;n.</div>
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
		<logic:iterate id="listaTotalCuenta4"
			name="OCAPEncuestaCalidadForm"
			property="listaItinerariosPreguntasCuenta"
			type="OCAPEncuestaCalidadOT" indexId="index4">
			<logic:equal name="pregunta" property="CPreguntaId" value="3">
				<logic:iterate id="preguntaTotal4" name="listaTotalCuenta4"
					property="listaPreguntas" type="OCAPEncuestaCalidadOT">
					<logic:equal name="preguntaTotal4" property="CPreguntaId"
						value="3">
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">Total Respuestas: </span>
							<bean:write name="preguntaTotal4"
								property="totalRespuestas" />
						</div>
						<bean:define id="numeroRespuestas3" name="preguntaTotal4"
							property="totalRespuestas" />
						<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas3.toString());%>
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
						<br />
						<br />
					</logic:equal>
				</logic:iterate>
			</logic:equal>
		</logic:iterate>
		<logic:equal name="pregunta" property="CPreguntaId" value="4">
			<% if (Constantes.NO.equals(titulo5)) { %>
			<div class="subTituloItin letraGrande">5- Valore de 1 a 5
				los siguientes conceptos relativos a la aplicaci&oacute;n
				inform&aacute;tica:</div>
			<br />
			<%   titulo5 = Constantes.SI;
		                   } %>
			<span class="subTituloItin separaIzqSub letraGrande">5.a-
				Acceso</span>
			<br />
			<br />
			<div>
				<span class="separaMasIzq letraGrande">(5)
					Completamente de acuerdo:</span> <span
					class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total5" /> / <bean:write
						name="pregunta" property="porc5" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(4) De
					acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total4" /> / <bean:write
						name="pregunta" property="porc4" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(3) Ni
					de acuerdo ni en desacuerdo:</span> <span
					class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total3" /> / <bean:write
						name="pregunta" property="porc3" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(2) En
					desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total2" /> / <bean:write
						name="pregunta" property="porc2" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(1) En
					completo desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total1" /> / <bean:write
						name="pregunta" property="porc1" /> %</span><br />
				<br />
			</div>
		</logic:equal>
		<logic:iterate id="listaTotalCuenta5"
			name="OCAPEncuestaCalidadForm"
			property="listaItinerariosPreguntasCuenta"
			type="OCAPEncuestaCalidadOT" indexId="index5">
			<logic:equal name="pregunta" property="CPreguntaId" value="4">
				<logic:iterate id="preguntaTotal5" name="listaTotalCuenta5"
					property="listaPreguntas" type="OCAPEncuestaCalidadOT">
					<logic:equal name="preguntaTotal5" property="CPreguntaId"
						value="4">
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">Total Respuestas: </span>
							<bean:write name="preguntaTotal5"
								property="totalRespuestas" />
						</div>
						<bean:define id="numeroRespuestas4" name="preguntaTotal5"
							property="totalRespuestas" />
						<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas4.toString());%>
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
						<br />
						<br />
					</logic:equal>
				</logic:iterate>
			</logic:equal>
		</logic:iterate>
		<logic:equal name="pregunta" property="CPreguntaId" value="5">
			<% if (Constantes.NO.equals(titulo5)) { %>
			<div class="subTituloItin letraGrande">5- Valore de 1 a 5
				los siguientes conceptos relativos a la aplicaci&oacute;n
				inform&aacute;tica:</div>
			<br />
			<%   titulo5 = Constantes.SI;
		                   } %>
			<span class="subTituloItin separaIzqSub letraGrande">5.b-
				Navegabilidad</span>
			<br />
			<br />
			<div>
				<span class="separaMasIzq letraGrande">(5)
					Completamente de acuerdo:</span> <span
					class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total5" /> / <bean:write
						name="pregunta" property="porc5" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(4) De
					acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total4" /> / <bean:write
						name="pregunta" property="porc4" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(3) Ni
					de acuerdo ni en desacuerdo:</span> <span
					class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total3" /> / <bean:write
						name="pregunta" property="porc3" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(2) En
					desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total2" /> / <bean:write
						name="pregunta" property="porc2" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(1) En
					completo desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total1" /> / <bean:write
						name="pregunta" property="porc1" /> %</span><br />
				<br />
			</div>
		</logic:equal>
		<logic:iterate id="listaTotalCuenta6"
			name="OCAPEncuestaCalidadForm"
			property="listaItinerariosPreguntasCuenta"
			type="OCAPEncuestaCalidadOT" indexId="index6">
			<logic:equal name="pregunta" property="CPreguntaId" value="5">
				<logic:iterate id="preguntaTotal6" name="listaTotalCuenta6"
					property="listaPreguntas" type="OCAPEncuestaCalidadOT">
					<logic:equal name="preguntaTotal6" property="CPreguntaId"
						value="5">
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">Total Respuestas: </span>
							<bean:write name="preguntaTotal6"
								property="totalRespuestas" />
						</div>
						<bean:define id="numeroRespuestas5" name="preguntaTotal6"
							property="totalRespuestas" />
						<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas5.toString());%>
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
						<br />
						<br />
					</logic:equal>
				</logic:iterate>
			</logic:equal>
		</logic:iterate>
		<logic:equal name="pregunta" property="CPreguntaId" value="6">
			<% if (Constantes.NO.equals(titulo5)) { %>
			<div class="subTituloItin letraGrande">5- Valore de 1 a 5
				los siguientes conceptos relativos a la aplicaci&oacute;n
				inform&aacute;tica:</div>
			<br />
			<%   titulo5 = Constantes.SI;
		                   } %>
			<span class="subTituloItin separaIzqSub letraGrande">5.c-
				Interfaz (dise&ntilde;o)</span>
			<br />
			<br />
			<div>
				<span class="separaMasIzq letraGrande">(5)
					Completamente de acuerdo:</span> <span
					class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total5" /> / <bean:write
						name="pregunta" property="porc5" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(4) De
					acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total4" /> / <bean:write
						name="pregunta" property="porc4" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(3) Ni
					de acuerdo ni en desacuerdo:</span> <span
					class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total3" /> / <bean:write
						name="pregunta" property="porc3" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(2) En
					desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total2" /> / <bean:write
						name="pregunta" property="porc2" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(1) En
					completo desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total1" /> / <bean:write
						name="pregunta" property="porc1" /> %</span><br />
				<br />
			</div>
		</logic:equal>
		<logic:iterate id="listaTotalCuenta7"
			name="OCAPEncuestaCalidadForm"
			property="listaItinerariosPreguntasCuenta"
			type="OCAPEncuestaCalidadOT" indexId="index7">
			<logic:equal name="pregunta" property="CPreguntaId" value="6">
				<logic:iterate id="preguntaTotal7" name="listaTotalCuenta7"
					property="listaPreguntas" type="OCAPEncuestaCalidadOT">
					<logic:equal name="preguntaTotal7" property="CPreguntaId"
						value="6">
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">Total Respuestas: </span>
							<bean:write name="preguntaTotal7"
								property="totalRespuestas" />
						</div>
						<bean:define id="numeroRespuestas6" name="preguntaTotal7"
							property="totalRespuestas" />
						<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas6.toString());%>
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
						<br />
						<br />
					</logic:equal>
				</logic:iterate>
			</logic:equal>
		</logic:iterate>
		<logic:equal name="pregunta" property="CPreguntaId" value="7">
			<% if (Constantes.NO.equals(titulo5)) { %>
			<div class="subTituloItin letraGrande">5- Valore de 1 a 5
				los siguientes conceptos relativos a la aplicaci&oacute;n
				inform&aacute;tica:</div>
			<br />
			<%   titulo5 = Constantes.SI;
		                   } %>
			<span class="subTituloItin separaIzqSub letraGrande">5.d-
				Legibilidad </span>
			<br />
			<br />
			<div>
				<span class="separaMasIzq letraGrande">(5)
					Completamente de acuerdo:</span> <span
					class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total5" /> / <bean:write
						name="pregunta" property="porc5" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(4) De
					acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total4" /> / <bean:write
						name="pregunta" property="porc4" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(3) Ni
					de acuerdo ni en desacuerdo:</span> <span
					class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total3" /> / <bean:write
						name="pregunta" property="porc3" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(2) En
					desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total2" /> / <bean:write
						name="pregunta" property="porc2" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(1) En
					completo desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total1" /> / <bean:write
						name="pregunta" property="porc1" /> %</span><br />
				<br />
			</div>
		</logic:equal>
		<logic:iterate id="listaTotalCuenta8"
			name="OCAPEncuestaCalidadForm"
			property="listaItinerariosPreguntasCuenta"
			type="OCAPEncuestaCalidadOT" indexId="index8">
			<logic:equal name="pregunta" property="CPreguntaId" value="7">
				<logic:iterate id="preguntaTotal8" name="listaTotalCuenta8"
					property="listaPreguntas" type="OCAPEncuestaCalidadOT">
					<logic:equal name="preguntaTotal8" property="CPreguntaId"
						value="7">
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">Total Respuestas: </span>
							<bean:write name="preguntaTotal8"
								property="totalRespuestas" />
						</div>
						<bean:define id="numeroRespuestas7" name="preguntaTotal8"
							property="totalRespuestas" />
						<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas7.toString());%>
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
						<br />
						<br />
					</logic:equal>
				</logic:iterate>
			</logic:equal>
		</logic:iterate>
		<logic:equal name="pregunta" property="CPreguntaId" value="8">
			<% if (Constantes.NO.equals(titulo5)) { %>
			<div class="subTituloItin">5- Valore de 1 a 5 los
				siguientes conceptos relativos a la aplicaci&oacute;n
				inform&aacute;tica:</div>
			<br />
			<%   titulo5 = Constantes.SI;
		                   } %>
			<span class="subTituloItin separaIzqSub letraGrande">5.e-
				Descarga de Documentos</span>
			<br />
			<br />
			<div>
				<span class="separaMasIzq letraGrande">(5)
					Completamente de acuerdo:</span> <span
					class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total5" /> / <bean:write
						name="pregunta" property="porc5" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(4) De
					acuerdo:</span> <span class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total4" /> / <bean:write
						name="pregunta" property="porc4" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(3) Ni
					de acuerdo ni en desacuerdo:</span> <span
					class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total3" /> / <bean:write
						name="pregunta" property="porc3" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(2) En
					desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total2" /> / <bean:write
						name="pregunta" property="porc2" /> %</span><br />
				<br /> <span class="separaMasIzq letraGrande">(1) En
					completo desacuerdo: </span><span class="textoNegrita alineaDcha"><bean:write
						name="pregunta" property="total1" /> / <bean:write
						name="pregunta" property="porc1" /> %</span><br />
				<br />
			</div>
		</logic:equal>
		<logic:iterate id="listaTotalCuenta9"
			name="OCAPEncuestaCalidadForm"
			property="listaItinerariosPreguntasCuenta"
			type="OCAPEncuestaCalidadOT" indexId="index9">
			<logic:equal name="pregunta" property="CPreguntaId" value="8">
				<logic:iterate id="preguntaTotal9" name="listaTotalCuenta9"
					property="listaPreguntas" type="OCAPEncuestaCalidadOT">
					<logic:equal name="preguntaTotal9" property="CPreguntaId"
						value="8">
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">Total Respuestas: </span>
							<bean:write name="preguntaTotal9"
								property="totalRespuestas" />
						</div>
						<bean:define id="numeroRespuestas8" name="preguntaTotal9"
							property="totalRespuestas" />
						<% contEnBlanco = Integer.parseInt(contEncuestas.toString()) - Integer.parseInt(numeroRespuestas8.toString());%>
						<div class="contadores letraGrande flotaIzq">
							<span class="letraGrande">En blanco: </span><%=contEnBlanco%></div>
						<br />
						<br />
					</logic:equal>
				</logic:iterate>
			</logic:equal>
		</logic:iterate>
		
		<jsp:include page="OCAPEvaluadosEncuestasGeneralesLsP3.jsp" />
		
	</logic:iterate>
</logic:iterate>