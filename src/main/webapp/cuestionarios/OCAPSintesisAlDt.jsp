<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.util.Utilidades"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.creditosCuestionarios.OCAPCreditosCuestionariosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.respuestas.OCAPRespuestasOT"%>
<%@ page import="java.util.ArrayList"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/fechas.js'/>"></script>

<%-- <jsp:include page="OCAPCuestionariosJs.jsp" /> --%>
<% int numPregunta = 0;%>
<script language="JavaScript">
var listaPreguntasJS = new Array();
</script>
<% int numPreguntaArray = 0; %>
<logic:iterate id="listaPreguntas" name="OCAPCuestionariosForm"
	property="listaCreditos" type="OCAPCreditosCuestionariosOT">
	<script>
         var aux = new Array(1);
         aux[0] = new Array(2);
         <% if (request.getAttribute("pregunta"+numPreguntaArray+"_"+0+"_"+0) != null) {%>
            aux[0][0] = eval('<%= request.getAttribute("pregunta"+numPreguntaArray+"_"+0+"_"+0).toString().replaceAll("\n",Constantes.SALTO_LINEA).replaceAll("\r",Constantes.RETORNO_CARRO)%>'); 
         <% } else {%>
            aux[0][0] = '';
         <% } %>
         aux[0][1] = eval('<bean:write name="listaPreguntas" property="NCreditosPosibles"/>');
         listaPreguntasJS[listaPreguntasJS.length] = aux;
         <% numPreguntaArray++; %>
      </script>
</logic:iterate>

<script language="javascript">

function guardarRespuesta(nPregunta, nRespuesta, nSubRespuesta, valor){
   var longListaPreguntas = listaPreguntasJS.length;
   listaPreguntasJS[nPregunta][nRespuesta][nSubRespuesta]=document.getElementById('pregunta'+nPregunta+'_'+nRespuesta+'_'+nSubRespuesta).value;
}

function esNumeroDecimal(campo){
   var aux=campo.value;
   var contador = 0;
   var aux2='';
   while (aux.indexOf('.')!=-1) {
      if (contador<2) {
         aux2= aux2+''+aux.substring(0,aux.indexOf('.')+1);
      }
      aux=aux.substring(aux.indexOf('.')+1);
      contador++;
   }
   if (contador > 1) {
      alert('El n\u00famero decimal s\u00f3lo puede contener un \'.\'');
      campo.value=aux2.substring(0,aux2.length-1);
      return false;
   } else return true;
}

function enviarFormulario(obligatorio, fin) {
   if ((obligatorio == '<%=Constantes.NO%>' || (obligatorio == '<%=Constantes.SI%>' && comprobarFormularioRelleno()) ) && comprobarCreditosMaximos()) {
      var cadenaParam='';
      cadenaParam='&numPreguntas='+listaPreguntasJS.length;
      for (i=0; i < listaPreguntasJS.length; i++) {
         var aux = listaPreguntasJS[i];
         for (j=0; j < aux.length; j++) {
            var aux2= aux[j];
            //for(k=0; k < aux2.length; k++) {
               //Reemplazar saltos de linea para pasarlos por request
               k=0;
               if (aux2[k] == null) {
                  aux2[k]='';
               } else {
                  if (aux2[k].indexOf('\n') != -1){
                     aux2[k] = aux2[k].replace(new RegExp('\\n','g'),'<%=Constantes.SALTO_LINEA%>').replace(new RegExp('\\r','g'),'<%=Constantes.RETORNO_CARRO%>');
                  }
                  if (aux2[k]!= null && aux2[k].indexOf('+') != -1) {
                     aux2[k] = aux2[k].replace(new RegExp('\\+','g'),'<%=Constantes.MAS%>');
                  }
               }
               cadenaParam = cadenaParam +'&'+ 'pregunta'+i+'_'+j+'_'+k+'='+aux2[k];
            //}//for
         }//for
      }//for
      alert(cadenaParam);
      enviar('OCAPCuestionarios.do?accion=insertarSintesis'+cadenaParam+'&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioId" />&idRepeticion=<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />&finalizar='+fin);
   }
}

function comprobarCreditosMaximos () {
   for (i=0; i < listaPreguntasJS.length; i++) {
      var aux = listaPreguntasJS[i];
      for (j=0; j < aux.length; j++) {
         var aux2= aux[j];
         if (eval(aux2[0]) > eval(aux2[1])){
            alert('Los cr\u00E9ditos obtenidos no pueden ser m\u00E1s que los cr\u00E9ditos posibles.');
            return false;
         }
      }
   }
   return true;
}

function pedirConfirmacion(obligatorio) {
   if ((obligatorio == '<%=Constantes.NO%>' || (obligatorio == '<%=Constantes.SI%>' && comprobarFormularioRelleno()) ) && comprobarCreditosMaximos()) {
      deshabilitarFormulario();
      document.getElementById('botonSiguiente').style.display='none';
      document.getElementById('botonSiguiente').style.visibility='hidden';
      document.getElementById('botonAtras').style.display='';
      document.getElementById('botonAtras').style.visibility='visible';
      document.getElementById('botonGuardar').style.display='';
      document.getElementById('botonGuardar').style.visibility='visible';
      document.getElementById('mensajeAvisoConfirmacion').style.display='';
      document.getElementById('mensajeAvisoConfirmacion').style.visibility='visible';

      var e=document.getElementsByTagName("span");
      for(var i=0;i<e.length;i++){
         if (e[i].name == 'stComentario' || e[i].className == 'stComentario'){
            e[i].style.display='none';
            e[i].style.visibility='hidden';
         }
      }
      location.href='#verificar';
   }
}

function comprobarFormularioRelleno() {
   var e=document.getElementsByTagName("input");
   var relleno = true;
   for(var i=0; i<e.length && relleno; i++){
      if (e[i].type=='text' && e[i].value == ''){
         relleno = false;
      }
   }
   if (!relleno)
      alert('Debe contestar todas las preguntas del formulario');
   return relleno;
}

function volverEditarFormulario() {
   habilitarFormulario();
   document.getElementById('botonSiguiente').style.display='';
   document.getElementById('botonSiguiente').style.visibility='visible';
   document.getElementById('botonAtras').style.display='none';
   document.getElementById('botonAtras').style.visibility='hidden';
   document.getElementById('botonGuardar').style.display='none';
   document.getElementById('botonGuardar').style.visibility='hidden';
   document.getElementById('mensajeAvisoConfirmacion').style.display='none';
   document.getElementById('mensajeAvisoConfirmacion').style.visibility='hidden';

   var e=document.getElementsByTagName("span");
   for(var i=0;i<e.length;i++){
      if (e[i].name == 'stComentario' || e[i].className == 'stComentario'){
         e[i].style.display='';
         e[i].style.visibility='visible';
      }
   }
   location.href='#verificar';
}

function deshabilitarFormulario() {
   for (i=0; i < document.forms[0].elements.length; i++) {
      document.forms[0].elements[i].disabled=true;
   }
}

function habilitarFormulario() {
   for (i=0; i < document.forms[0].elements.length; i++) {
      document.forms[0].elements[i].disabled=false;
   }
}

function limpiarFormulario() {
   for (i=0; i < document.forms[0].elements.length; i++) {
      document.forms[0].elements[i].value='';
      if (document.forms[0].elements[i].checked==true)
         document.forms[0].elements[i].checked=false;
   }
}

function isArray(obj) {
   if (obj.constructor.toString().indexOf("Array") == -1)
      return false;
   else
      return true;
}

function volverListado (continuar) {
   enviar('OCAPCuestionarios.do?accion=irListar');
}
</script>


<body>
	<a name="verificar"></a>
	<div class="contenido contenidoFaseIII">
		<div class="contenidoTextoProcedimiento">

			<html:form action="/OCAPCuestionarios.do">

				<h2 class="tituloContenido">
					<bean:write name="OCAPCuestionariosForm" property="DArea" />
					PROCEDIMIENTO DE EVALUACI&Oacute;N DEL
					<bean:write name="OCAPCuestionariosForm" property="DAreaLargo" />
					DE LAS COMPETENCIAS PROFESIONALES DE LA CARRERA PROFESIONAL
				</h2>

				<logic:equal name="accion" value="<%=Constantes.VER_DETALLE%>">
					<logic:equal name="OCAPCuestionariosForm" property="BFinalizado"
						value="<%=Constantes.EST_CUEST_FINALIZAR%>">
						<logic:notEqual name="OCAPCuestionariosForm" property="DEvidencia"
							value="">
							<div class="botonesPagina">
								<a href="javascript:window.print();"><img
									src="imagenes/imagenes_ocap/imprimir.gif" alt="Imprimir"
									class="flotaIzq" /></a>
							</div>
						</logic:notEqual>
					</logic:equal>
				</logic:equal>

				<h3 class="subTituloContenido">
					<bean:write name="OCAPCuestionariosForm" property="DTitulo" />
					<br /> <br />
					<bean:write name="OCAPCuestionariosForm"
						property="DNombreItinerario" />
					<br />
					<br /> <span><bean:write name="OCAPCuestionariosForm"
							property="codigoId" /></span>
				</h3>
				<div class="lineaBajaM"></div>
				<div class="espaciador"></div>

				<html:hidden property="CExpId" />

				<logic:notEqual name="OCAPCuestionariosForm" property="DMensaje"
					value="">
					<logic:notEqual name="accion" value="<%=Constantes.VER_DETALLE%>">
						<span name="stComentario">
							<div class="textoComentario">
								<bean:write name="OCAPCuestionariosForm" property="DMensaje"
									filter="false" />
							</div>
						</span>
					</logic:notEqual>
					<logic:equal name="accion" value="<%=Constantes.VER_DETALLE%>">
						<logic:notEqual name="OCAPCuestionariosForm"
							property="BFinalizado"
							value="<%=Constantes.EST_CUEST_FINALIZAR%>">
							<span name="stComentario">
								<div class="textoComentario">
									<bean:write name="OCAPCuestionariosForm" property="DMensaje"
										filter="false" />
								</div>
							</span>
						</logic:notEqual>
					</logic:equal>
				</logic:notEqual>

				<logic:equal name="accion" value="<%=Constantes.VER_DETALLE%>">
					<logic:equal name="OCAPCuestionariosForm" property="BFinalizado"
						value="<%=Constantes.EST_CUEST_FINALIZAR%>">
						<logic:notEqual name="OCAPCuestionariosForm"
							property="DObservacionesEvidencia" value="">
							<div class="cuadroProcedimiento ocultarCampo">
								<div class="espaciador"></div>
								<bean:write name="OCAPCuestionariosForm"
									property="DObservacionesEvidencia" filter="false" />
							</div>
						</logic:notEqual>
					</logic:equal>
				</logic:equal>

				<logic:equal name="accion" value="<%=Constantes.VER_DETALLE%>">
					<logic:equal name="OCAPCuestionariosForm" property="BFinalizado"
						value="<%=Constantes.EST_CUEST_FINALIZAR%>">
						<bean:define id="idCuestionario" name="OCAPCuestionariosForm"
							property="CCuestionarioId" />
						<% /* if (Constantes.CUEST_AP01_EVIDENCIA.equals(idCuestionario.toString()) || Constantes.CUEST_AP02_EVIDENCIA.equals(idCuestionario.toString())  ||
                  Constantes.CUEST_AC05_EVIDENCIA.equals(idCuestionario.toString())  || Constantes.CUEST_AC05B_EVIDENCIA.equals(idCuestionario.toString()) ) { */%>
						<logic:notEqual name="OCAPCuestionariosForm" property="DEvidencia"
							value="">
							<fieldset>
								<legend class="tituloLegend"> Datos Personales </legend>
								<div class="cuadroFieldset">
									<label for="idVNombre" class="colocaDatosVis"> Nombre:
										<span><bean:write name="OCAPCuestionariosForm"
												property="DNombreUsuario" /></span>
									</label> <label for="idVDNI" class="colocaDatosVis"> NIF/NIE: <span><bean:write
												name="OCAPCuestionariosForm" property="CDniReal" /></span>
									</label> <br />
									<br /> <label for="idVEspecialidad" class="colocaDatosVis">
										Especialidad: <span> <logic:notEmpty
												name="OCAPCuestionariosForm" property="DEspec_nombre">
												<bean:write name="OCAPCuestionariosForm"
													property="DEspec_nombre" />
											</logic:notEmpty> <logic:empty name="OCAPCuestionariosForm"
												property="DEspec_nombre">-</logic:empty>
									</span>
									</label> <br />
									<br /> <label for="idVGerencia" class="colocaDatosVisGrande">
										Gerencia: <span> <logic:notEmpty
												name="OCAPCuestionariosForm" property="DGerencia_nombre">
												<bean:write name="OCAPCuestionariosForm"
													property="DGerencia_nombre" />
											</logic:notEmpty> <logic:empty name="OCAPCuestionariosForm"
												property="DGerencia_nombre">-</logic:empty>
									</span>
									</label> <br />
									<br /> <label for="idVCentro" class="colocaDatosVisGrande">
										Centro de Trabajo: <span> <logic:notEmpty
												name="OCAPCuestionariosForm"
												property="DCentrotrabajo_nombre">
												<bean:write name="OCAPCuestionariosForm"
													property="DCentrotrabajo_nombre" />
											</logic:notEmpty> <logic:empty name="OCAPCuestionariosForm"
												property="DCentrotrabajo_nombre">-</logic:empty>
									</span>
									</label> <br />
									<br /> <label for="idVCentro" class="colocaDatosVisGrande">
										Unidad de Adscripci&oacute;n/Servicio Actual: <span> <logic:notEmpty
												name="OCAPCuestionariosForm" property="DAreaUsuario">
												<bean:write name="OCAPCuestionariosForm"
													property="DAreaUsuario" />
											</logic:notEmpty> <logic:empty name="OCAPCuestionariosForm"
												property="DAreaUsuario">-</logic:empty> / <logic:notEmpty
												name="OCAPCuestionariosForm" property="DUnidad">
												<bean:write name="OCAPCuestionariosForm" property="DUnidad" />
											</logic:notEmpty> <logic:empty name="OCAPCuestionariosForm" property="DUnidad">-</logic:empty>
									</span>
									</label> <br />
									<br />
								</div>
							</fieldset>
							<% /* } */%>
						</logic:notEqual>
					</logic:equal>
				</logic:equal>

				<%-- Preguntas --%>
				<h4 class="subTituloContenidoItinerario">
					<bean:write name="OCAPCuestionariosForm" property="DArea" />
					<bean:write name="OCAPCuestionariosForm" property="DNombre" />
					-
					<bean:write name="OCAPCuestionariosForm" property="DNombreLargo" />
				</h4>
				<fieldset>
					<div class="cuadroProcedimiento">
						<div class="espaciador"></div>

						<bean:size id="tamanoPreguntas" name="OCAPCuestionariosForm"
							property="listaCreditos" />
						<logic:equal name="tamanoPreguntas" value="0">
							<span>Su perfil no tiene preguntas para este cuestionario</span>
						</logic:equal>
						<logic:notEqual name="tamanoPreguntas" value="0">

							<%-- Mensaje de confirmacion que mostraremos cuando pinche en Siguiente--%>
							<div id="mensajeAvisoConfirmacion"
								style="display: none; visibility: hidden;">
								<p>
									Verifique que la informaci&oacute;n introducida en el
									formulario
									<logic:notEqual name="OCAPCuestionariosForm"
										property="DNombreLargo" value=""> "<bean:write
											name="OCAPCuestionariosForm" property="DNombreLargo" />"</logic:notEqual>
									es la que realmente ha querido usted reflejar. <br />
									<br /> Si est&aacute; de acuerdo pinche en el bot&oacute;n
									"Finalizar". Al pulsar este bot&oacute;n ya no podr&aacute;
									modificar ning&uacute;n dato de este apartado. <br /> Si desea
									modificar alguno de los datos pinche en el bot&oacute;n
									"Modificar". Volver&aacute; a ver el formulario y podr&aacute;
									cambiar todos aquellos aspectos que usted considere necesarios.<br />
									<br />
								</p>
							</div>

							<logic:iterate id="listaPreguntas" name="OCAPCuestionariosForm"
								property="listaCreditos" type="OCAPCreditosCuestionariosOT">
								<div>
									<bean:write name="OCAPCuestionariosForm" property="DArea" />
									<bean:write name="listaPreguntas" property="DCuestionario" />
									<br />
								</div>
								<%-- 
                     <div>
                     Puntuaci&oacute;n: <bean:write name="listaPreguntas" property="NPuntuacion" />
                     </div>
                     --%>
								<div>
									Cr&eacute;ditos obtenidos:
									<bean:write name="listaPreguntas" property="NCreditos" />
									<%-- <input type="text" id="pregunta<%=numPregunta%>_0_0" value="<bean:write name="listaPreguntas" property="NCreditos" />" onkeypress="return soloNumerosDecimales(event);" onchange="javascript:esNumeroDecimal(this);guardarRespuesta('<%=numPregunta%>','0','0');" /> --%>
								</div>
								<div>
									Cr&eacute;ditos m&aacute;ximos:
									<bean:write name="listaPreguntas" property="NCreditosPosibles" />
								</div>
								<div class="espaciador"></div>
								<% numPregunta++;%>
							</logic:iterate>

						</logic:notEqual>
						<%-- tamanoPreguntas != 0 --%>
					</div>

				</fieldset>

				<logic:equal name="accion" value="<%=Constantes.VER_DETALLE%>">
					<logic:notEqual name="OCAPCuestionariosForm" property="BFinalizado"
						value="">
						<logic:equal name="OCAPCuestionariosForm" property="BFinalizado"
							value="<%=Constantes.EST_CUEST_FINALIZAR%>">
							<logic:notEqual name="OCAPCuestionariosForm"
								property="DEvidencia" value="">
								<fieldset class="romperBefore">
									<legend class="tituloLegend">Evidencia de conformidad</legend>
									<div class="cuadroProcedimiento">
										<div class="espaciador"></div>
										<bean:write name="OCAPCuestionariosForm" property="DEvidencia"
											filter="false" />
									</div>
								</fieldset>
							</logic:notEqual>
						</logic:equal>
					</logic:notEqual>
				</logic:equal>

				<jsp:include page="OCAPCuestionariosAlDtBotones.jsp" />

			</html:form>
		</div>
		<%-- /fin de ContenidoTextoProcedimiento --%>
	</div>
	<%-- /Fin de Contenido --%>

	<div class="limpiadora"></div>
	<logic:equal name="accion" value="<%=Constantes.INSERTAR%>">
		<script language="javascript">habilitarFormulario(); limpiarFormulario();</script>
	</logic:equal>
</body>
