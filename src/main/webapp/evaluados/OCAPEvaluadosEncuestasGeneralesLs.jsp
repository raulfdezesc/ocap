<%@ taglib uri="html.tld" prefix="html"%>
<%@taglib uri="bean.tld" prefix="bean"%><%@ taglib uri="logic.tld" prefix="logic"%>

<%@page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.encuestaCalidad.OCAPEncuestaCalidadOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@page import="java.util.ArrayList"%>
<%JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);%>
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

<div class="contenido contenidoFaseIII">
	<div class="contenidoListadoAspirantesGCP buscadorEvaluados">

		<html:form action="/OCAPEncuestaCalidad.do">

			<h3 class="subTituloContenido">Estad&iacute;sticas de Encuestas	de Evaluados</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<jsp:include page="OCAPEvaluadosEncuestasGeneralesLsP1.jsp" />

			<logic:equal name="OCAPEncuestaCalidadForm" property="jspInicio"
				value="false">
				<bean:size id="tamanoListado" name="OCAPEncuestaCalidadForm"
					property="listaItinerariosPreguntas" />

				<logic:notEqual name="tamanoListado" value="0">
					<div class="botonesPagina">
						<a href="javascript:window.print();"><img
							src="imagenes/imagenes_ocap/imprimir.gif" alt="Imprimir"
							class="flotaIzq" /></a>
					</div>
					<div class="subTituloItin letraGrande flotaCentro">
						<span>N&uacute;mero de personas que han realizado la
							evaluaci&oacute;n: </span> <br /> <span>N&uacute;mero de
							personas que han contestado la encuesta: </span>
					</div>
					<div class="subTituloItin letraGrande flotaCentro">
						<bean:write name="OCAPEncuestaCalidadForm"
							property="numTotalEvaluados" />
						<br />
						<bean:write name="OCAPEncuestaCalidadForm"
							property="numTotalEncuestas" />
					</div>
					<div class="limpiadora"></div>
					<div class="espaciador"></div>
				</logic:notEqual>

				<fieldset>
					<legend class="tituloLegend"> Listado </legend>

					<logic:equal name="tamanoListado" value="0">
						<div class="textoCaracteres textoNegro textoNormal">
							<div class="espaciador"></div>
							<bean:message key="listado.noDatos" />
							<br />
							<br />
						</div>
					</logic:equal>

					<logic:notEqual name="tamanoListado" value="0">
						
						<jsp:include page="OCAPEvaluadosEncuestasGeneralesLsP2.jsp" />

						<br />
					</logic:notEqual>

				</fieldset>
			</logic:equal>

			<div class="espaciador"></div>
		</html:form>

	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->
