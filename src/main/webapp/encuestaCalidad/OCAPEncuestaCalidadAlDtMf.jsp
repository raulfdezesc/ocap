<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.creditos.OCAPCreditosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.encuestaCalidad.OCAPEncuestaCalidadOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>
<script>

   /*
      Envia los datos de la encuesta si se ha realizado correctamente
   */
   function enviaEncuesta(numeroPreguntas){
      if (validaEncuesta(numeroPreguntas))
         enviar('OCAPEncuestaCalidad.do?accion=rellenar');
   }

   /*
      Comprueba que se respondan a las preguntas "obligatorias", sino salta un aviso y devuelve false
   */

function validaEncuesta(numeroPreguntas){      
	//Indica si se ha rellenado correctamente el formulario
	var valido = true;

   var mensaje = '';
   var opcionNo= false;
   var algunNoVacio=false;
   for (var i=0; i < eval(numeroPreguntas); i++){
      var elementos=document.getElementsByName('respuesta['+i+']');
      if (elementos.length == 1 && opcionNo && elementos[0].value.length==0){
         if (!algunNoVacio) //Para añadir el mensaje sólo una vez
            mensaje += 'Si su respuesta es negativa, debe indicar los motivos.\n'
         algunNoVacio = true;
         elementos[0].style.backgroundColor = "#F8E0E0";
         elementos[0].focus();
         valido = false;
      }
      if (elementos.length == 2 && elementos[1].checked)
         opcionNo = true;
      else 
         opcionNo = false;
      if (elementos.length == 1 && elementos[0].maxLength == 6 && elementos[0].value!=''){ //la pregunta de horas ¿es obligatoria?
         if (!esNumeroDecimal(elementos[0].value, 2)){
            mensaje += 'El n\u00BA de horas debe ser un n\u00FAmero, como m\u00E1ximo con 2 decimales.\n';
            elementos[0].style.backgroundColor = "#F8E0E0";
            elementos[0].focus();
            valido = false;
         }
      }
   }
   if (mensaje != '')
      alert(mensaje);

   return valido;
}


	/*
      Devuelve el valor que se ha marcado en un radioButton
      devuelve -1 si no se ha seleccionado ninguna opcion
   */
	function getValorRadio(item){
	  //Valor del radio button seleccionado
     var valor = -1
     //Formulario
     var  formu = document.forms[0];
     //Recorremos todas las opciones 
	  for ( i = 0; i < formu[item].length; i++ ) {
	    if ( formu[item][i].checked ){
	        valor = i
	    }
	  }
	  return valor;
	}
   
   
var LONG_MAX= <%=Constantes.LONG_RESPUESTAS_ENCUESTA%>;
function longMax(campo) {
   if(campo.value.length <= LONG_MAX) {
      return true;
   } else {
      campo.value = campo.value.substring(0,LONG_MAX);
      return false;
   }
}


function volverListado(){
   //enviar('OCAPEvaluadores.do?accion=irListarEncuesta&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
   history.back();
}

function deshabilitarTextArea() {
   var elementos=document.getElementsByTagName('textarea');
   for (i=0; i < elementos.length; i++) {
         elementos[i].disabled=false;
         elementos[i].readOnly=true;
   }
}
</script>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoECP">
		<html:form action="/OCAPEncuestaCalidad.do">
			<h2 class="tituloContenido">ENCUESTA DE CALIDAD</h2>
			<h3 class="subTituloContenido">Proceso de evaluacion de carrera
				profesional</h3>
			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<div class="encuestaCalidad">
					<p>La encuesta, que a continuaci&oacute;n le proponemos,
						pretende conocer su opini&oacute;n y recoger su percepci&oacute;n
						acerca de algunos de los aspectos m&aacute;s relevantes del
						procedimiento t&eacute;cnico de evaluaci&oacute;n de
						profesionales. El citado procedimiento constituye el elemento
						central de la evaluaci&oacute;n del &aacute;rea asistencial para
						el reconocimiento de grado de Carrera Profesional de los
						trabajadores de centros sanitarios del sistema sanitario
						p&uacute;blico de la Comunidad de Castilla y Le&oacute;n.</p>
					<p>Gracias a su colaboraci&oacute;n, podremos mejorar el
						m&eacute;todo de evaluaci&oacute;n de profesionales, con el fin de
						conseguir un sistema m&aacute;s objetivo y transparente. Asimismo,
						queremos mejorar la accesibilidad de la aplicaci&oacute;n
						inform&aacute;tica que lo soporta, para facilitar su manejo todo
						lo posible.</p>
					<p>
						Las escalas de valoraci&oacute;n son (seg&uacute;n las preguntas)
						dos:<br />
					<ul class="listadoEncuestaCalidad">
						<li>Una escala de Likert (cualitativa de 5 opciones) que
							comprende desde "Completamente de acuerdo" , a la izquierda,
							hasta "En completo desacuerdo", a la derecha.</li>
						<li>Una escala cuantitativa que va desde el m&aacute;ximo
							valor "5", o favorable, a la izquierda, hasta el valor
							m&iacute;nimo, o desfavorable, "1", a la derecha.</li>
					</ul>
					</p>
					<p>Agradecemos de antemano su colaboraci&oacute;n y sinceridad,
						que nos permitir&aacute; seguir mejorando.</p>
				</div>
			</logic:equal>
			<% int num = 1;%>
			<% int numPregunta = 0;%>
			<table class="tablaEncuestaCalidad">
				<tr>
					<th width="20" class="textoCentrado"><span>Nº</span></th>
					<th class="textoCentrado"><span>CUESTIONES </span></th>
					<th width="80" class="textoCentrado"><span>Completamente
							de acuerdo </span></th>
					<th width="80" class="textoCentrado"><span>De acuerdo </span></th>
					<th width="80" class="textoCentrado"><span>Ni de
							acuerdo ni en desacuerdo</span></th>
					<th width="80" class="textoCentrado"><span>En
							desacuerdo</span></th>
					<th width="80" class="textoCentrado"><span>En completo
							desacuerdo</span></th>
				</tr>
				<logic:iterate id="area" name="OCAPEncuestaCalidadForm"
					property="listaAreas" type="OCAPEncuestaCalidadOT" indexId="nArea">
					<tr>
						<td class="tituloApartado" colspan="7"><bean:write
								name="area" property="DArea" /></td>
					</tr>
					<logic:iterate id="pregunta" name="area" property="listaPreguntas"
						type="OCAPEncuestaCalidadOT" indexId="nPreg">
						<%-- Preguntas de 5 radio button: Completamente de acuerdo ... En completo desacuerdo --%>
						<% if (pregunta.getCTipoRespuesta()==0){ %>
						<logic:equal name="pregunta" property="CPreguntaPadreId" value="0">
							<logic:equal name="pregunta" property="numSubPreguntas" value="0">
								<tr>
									<td><%=num++%></td>
									<td class="enunciado"><bean:write name="pregunta"
											property="DPregunta" /> <logic:notEqual name="pregunta"
											property="AObservaciones" value="">
											<br />
											<bean:write name="pregunta" property="AObservaciones" />
										</logic:notEqual></td>
									<logic:equal name="pregunta" property="CTipoRespuesta"
										value="0">
										<logic:iterate id="respuestaIt" name="pregunta"
											property="listaRespuestas" type="OCAPEncuestaCalidadOT"
											indexId="nResp">
											<%--<td><html:radio name="OCAPEncuestaCalidadForm" property="respuestaRadio" tabindex="<%=nPreg%>" value="<%=respuestaIt.getNPonderacionRespuesta()%>" title="<%=respuestaIt.getDRespuesta()%>" /></td>--%>
											<td><html:radio name="OCAPEncuestaCalidadForm"
													property="<%=\"respuesta[\"+numPregunta+\"]\"%>"
													value="<%=Long.toString(respuestaIt.getCRespuestaId())%>"
													title="<%=respuestaIt.getDRespuesta()%>" /></td>
										</logic:iterate>
										<%numPregunta++;%>
									</logic:equal>
								</tr>
							</logic:equal>
							<logic:notEqual name="pregunta" property="numSubPreguntas"
								value="0">
								<tr>
									<td rowspan="<%=(pregunta.getNumSubPreguntas()+1)%>"><%=num++%></td>
									<td colspan="6"><bean:write name="pregunta"
											property="DPregunta" /> <logic:notEqual name="pregunta"
											property="AObservaciones" value="">
											<br />
											<bean:write name="pregunta" property="AObservaciones" />
										</logic:notEqual></td>
								</tr>
							</logic:notEqual>
						</logic:equal>
						<logic:notEqual name="pregunta" property="CPreguntaPadreId"
							value="0">
							<tr>
								<td class="enunciado"><bean:write name="pregunta"
										property="DPregunta" /> <logic:notEqual name="pregunta"
										property="AObservaciones" value="">
										<br />
										<bean:write name="pregunta" property="AObservaciones" />
									</logic:notEqual></td>
								<logic:equal name="pregunta" property="CTipoRespuesta" value="0">
									<logic:iterate id="respuesta" name="pregunta"
										property="listaRespuestas" type="OCAPEncuestaCalidadOT">
										<td><html:radio name="OCAPEncuestaCalidadForm"
												property="<%=\"respuesta[\"+numPregunta+\"]\"%>"
												value="<%=Long.toString(respuesta.getCRespuestaId())%>"
												title="<%=respuesta.getDRespuesta()%>" /></td>
									</logic:iterate>
									<%numPregunta++;%>
								</logic:equal>
							</tr>
						</logic:notEqual>
						<% } else if (pregunta.getCTipoRespuesta()==1 || pregunta.getCTipoRespuesta()==2){ %>
						<tr>
							<td rowspan="<%=(pregunta.getNumSubPreguntas()+1)%>"><%=num++%></td>
							<td colspan="6"><bean:write name="pregunta"
									property="DPregunta" /> <logic:notEqual name="pregunta"
									property="AObservaciones" value="">
									<br />
									<bean:write name="pregunta" property="AObservaciones" />
								</logic:notEqual> <br />
							<br /> <logic:iterate id="respuesta" name="pregunta"
									property="listaRespuestas" type="OCAPEncuestaCalidadOT">
									<bean:write name="respuesta" property="DRespuesta" />
									<html:radio name="OCAPEncuestaCalidadForm"
										property="<%=\"respuesta[\"+numPregunta+\"]\"%>"
										value="<%=Long.toString(respuesta.getCRespuestaId())%>"
										title="<%=respuesta.getDRespuesta()%>" />
                              &nbsp;&nbsp;&nbsp;
                           </logic:iterate> <%numPregunta++;%></td>
						</tr>
						<% } else if (pregunta.getCTipoRespuesta()==3){ %>
						<tr>
							<logic:equal name="pregunta" property="CPreguntaPadreId"
								value="0">
								<td><%=num++%></td>
							</logic:equal>
							<td colspan="6"><bean:write name="pregunta"
									property="DPregunta" /> <logic:notEqual name="pregunta"
									property="AObservaciones" value="">
									<br />
									<bean:write name="pregunta" property="AObservaciones" />
								</logic:notEqual> <br />
							<br /> <html:textarea styleClass="centrarCaja2"
									name="OCAPEncuestaCalidadForm"
									property="<%=\"respuesta[\"+numPregunta+\"]\"%>" cols="110"
									rows="10" onkeypress="javascript:return longMax(this);"
									onclick="javascript:return longMax(this);"
									onkeydown="javascript:return longMax(this);"
									onkeyup="javascript:return longMax(this);"></html:textarea> <%numPregunta++;%>
						</tr>
						<% } else if (pregunta.getCTipoRespuesta()==4){ %>
						<tr>
							<td><%=num++%></td>
							<td class="enunciado"><bean:write name="pregunta"
									property="DPregunta" /></td>
							<td align="right" colspan="3"><strong><bean:write
										name="pregunta" property="AObservaciones" /></strong></td>
							<td colspan="2"><html:text name="OCAPEncuestaCalidadForm"
									property="<%=\"respuesta[\"+numPregunta+\"]\"%>" maxlength="6" /></td>
							<%numPregunta++;%>
						</tr>
						<% } else if (pregunta.getCTipoRespuesta()==5){ %>
						<bean:size id="numResp" name="pregunta" property="listaRespuestas" />
						<tr>
							<td rowspan="<%=(numResp.intValue()+1)%>"><%=num++%></td>
							<td colspan="6"><bean:write name="pregunta"
									property="DPregunta" /> <logic:notEqual name="pregunta"
									property="AObservaciones" value="">
									<br />
									<bean:write name="pregunta" property="AObservaciones" />
								</logic:notEqual></td>
						</tr>
						<logic:iterate id="respuesta" name="pregunta"
							property="listaRespuestas" type="OCAPEncuestaCalidadOT">
							<tr>
								<td class="enunciado"><bean:write name="respuesta"
										property="DRespuesta" /></td>
								<td colspan="5"><html:radio name="OCAPEncuestaCalidadForm"
										property="<%=\"respuesta[\"+numPregunta+\"]\"%>"
										value="<%=Long.toString(respuesta.getCRespuestaId())%>"
										title="<%=respuesta.getDRespuesta()%>" /></td>
							</tr>
						</logic:iterate>
						<%numPregunta++;%>
						<% } %>
					</logic:iterate>
				</logic:iterate>
			</table>

			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<div class="botonesPagina colocaBotonBusc">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviaEncuesta('<%=numPregunta%>');"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Generar listado CSV" /> <span> Enviar encuesta </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:alert('Podr\u00E1 contestar la encuesta en otro momento.');enviar('OCAPCuestionarios.do?accion=irListar');">
								<img src="imagenes/imagenes_ocap/aspa_roja.gif"
								alt="Generar listado CSV" /> <span> Cancelar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</logic:equal>

			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<% if (!Constantes.OCAP_DIRECCION.equals(jcylUsuario.getUser().getRol())) { %>
				<div class="botonesPagina colocaBotonBusc">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:volverListado();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Volver listado" /> <span> Volver </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
				<% } else {%>
				<% if (request.getParameter("CExp_id")!= null) { %>
				<div class="botonesPagina colocaBotonBusc">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:volverListado();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Volver listado" /> <span> Volver </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
				<% } else { %>
				<div class="botonesPagina colocaBotonBusc">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:window.close();"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cerrar" /> <span>
									Cerrar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
				<% } %>
				<% } %>
				<script language="javaScript">
            deshabilitarFormulario(document.forms[0]);
            deshabilitarTextArea();
         </script>
			</logic:equal>
		</html:form>
	</div>
	<!-- /fin de ContenidoTextoECP -->
</div>
<!-- /Fin de Contenido -->
<div class="limpiadora"></div>