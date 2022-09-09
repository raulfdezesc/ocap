<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.calculoCredCuestionarios.OCAPCalculoCredCuestionariosOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/fechas.js'/>"
	charset="windows-1252"></script>

<script language="javascript" type="text/javascript">

function enviarFormulario(obligatorio, fin) {
   if (comprobarFormularioRelleno(fin, '<%=Constantes.NO%>')) {
      habilitarFormulario();
      enviar('OCAPCuestionarios.do?accion=guardarPonderacion&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioId" />&idRepeticion=<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />&autoponderacion='+document.getElementById("autoponderacion").value+'&finalizar='+fin);
   }
}

function volverListado() {
   <% if (request.getAttribute("verPadre")!=null && !"".equals(request.getAttribute("verPadre"))){ %>  
    
           if ( document.forms[0].CPlantillaId != null && document.forms[0].CPlantillaId.value == 11){ 
             enviar('OCAPCuestionarios.do?accion=verCuestionario&idCuestionario=<%=request.getAttribute("verPadre")%>&idRepeticion=<%=request.getAttribute("verPadreRepe")==null?"1":request.getAttribute("verPadreRepe")%>&saltarIntermedio1=<%=Constantes.SI%>&idPadreEvidencia=<bean:write name="OCAPCuestionariosForm" property="CPadreEvidenciaId" />');
         }else { 
            enviar('OCAPCuestionarios.do?accion=verCuestionario&idCuestionario=<%=request.getAttribute("verPadre")%>&idRepeticion=<%=request.getAttribute("verPadreRepe")==null?"1":request.getAttribute("verPadreRepe")%>&saltarIntermedio1=<%=Constantes.SI%>" />');
       } 
    
   <% } else { %>
      <% if(Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())){%>
         enviar('OCAPCuestionarios.do?accion=irListarPruebas');
      <% } else { %>
         enviar('OCAPCuestionarios.do?accion=irListar');
      <% } %>      
   <% } %>
}

function comprobarFormularioRelleno(fin, comprobar0){
   var creditosObtenidos = eval(document.forms[0].NCreditosItinerario.value);
   var creditosMaximos = eval(document.forms[0].NCreditosPosiblesItinerario.value);
   //Si el campo creditos esta vacio, consideramos que son 0
   if (document.forms[0].NCreditosItinerario.value == ''){
      document.forms[0].NCreditosItinerario.value =0;
      creditosObtenidos=0;
   }
   if (creditosObtenidos > creditosMaximos) {
      alert('Los cr\u00E9ditos asignados no pueden superar los cr\u00E9ditos m\u00E1ximos.');
      return false;
   }
   
   if (comprobar0=='<%=Constantes.SI%>' && fin == '<%=Constantes.EST_CUEST_FINALIZAR%>' && creditosObtenidos==0 ) {
     <% if (request.getAttribute("autoCalcularTexto")!=null && Constantes.NO.equals(request.getAttribute("autoCalcularTexto")))
        { %>
           if (!confirm('Recuerde que usted es el que tiene que evaluarse, en caso contrario se le va a asignar 0 cr\u00E9ditos. \u00bfDesea continuar?'))
             return false;
     <% } else { %>
            if(!confirm('Se va a asignar 0 cr\u00E9ditos. \u00bfDesea continuar?'))
              return false;
      <% } %>
   }
   return true;
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

function pedirConfirmacion(obligatorio) {
   if (obligatorio == '<%=Constantes.NO%>' || (obligatorio == '<%=Constantes.SI%>' && comprobarFormularioRelleno('<%=Constantes.EST_CUEST_FINALIZAR%>','<%=Constantes.SI%>')) ) {
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
         if (e[i].name == 'stComentario'){
            e[i].style.display='none';
            e[i].style.visibility='hidden';
         }
      }

   }
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
      if (e[i].name == 'stComentario'){
         e[i].style.display='';
         e[i].style.visibility='visible';
      }
   }
}

function deshabilitarFormulario() {
   var e=document.getElementsByTagName("input");
   for (i=0; i < e.length; i++) {
      if (e[i].type != 'hidden')
         e[i].disabled=true;
   }
}

function habilitarFormulario() {
   for (i=0; i < document.forms[0].elements.length; i++) {
      document.forms[0].elements[i].disabled=false;
   }
}

function isArray(obj) {
   if (obj.constructor.toString().indexOf("Array") == -1)
      return false;
   else
      return true;
}

function verCuestionarioAsociado(){
   enviar('OCAPCuestionarios.do?accion=verCuestionario&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioId" />&idRepeticion=<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />');
}

function verCuestionarioEvidencia(){
   enviar('OCAPCuestionarios.do?accion=verCuestionario&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioId" />&idRepeticion=<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />&saltarPonderacion=<%=Constantes.SI%>');
}

function verEvidencia(idEvidencia, nEvidencia, idPadreEvidencia, idRepeticion){
   enviar('OCAPCuestionarios.do?accion=verCuestionario&idCuestionario='+idEvidencia+'&nEvidencia='+nEvidencia+'&idPadreEvidencia='+idPadreEvidencia+'&idRepeticion='+idRepeticion);
}

function volverEval(){
   <% if (request.getAttribute("verPadre")!=null && !"".equals(request.getAttribute("verPadre"))){ %>
         enviar('OCAPCuestionarios.do?accion=verCuestionario&idCuestionario=<%=request.getAttribute("verPadre")%>&idRepeticion=<%=request.getAttribute("verPadreRepe")==null?"1":request.getAttribute("verPadreRepe")%>&saltarIntermedio1=<%=Constantes.SI%>');
   <% } else { %>
         enviar('OCAPCuestionarios.do?accion=irListar&expId=<bean:write name="OCAPCuestionariosForm" property="CExpId" />');
   <% } %>   
}
</script>
<input type="hidden" id="autoponderacion" value="N"/>
<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoProcedimiento">
		<html:form action="/OCAPCuestionarios.do">
			<h2 class="tituloContenido">
				<bean:write name="OCAPCuestionariosForm" property="DArea" />
				PROCEDIMIENTO DE EVALUACI&Oacute;N DEL
				<bean:write name="OCAPCuestionariosForm" property="DAreaLargo" />
				DE LAS COMPETENCIAS PROFESIONALES DE LA CARRERA PROFESIONAL
			</h2>
			<h3 class="categoria2">
				<bean:write name="OCAPCuestionariosForm" property="DTitulo" />
			</h3>
			<h3 class="categoria1">
				<bean:write name="OCAPCuestionariosForm"
					property="DNombreItinerario" />
			</h3>
			<div class="codigoEPR">
				<bean:write name="OCAPCuestionariosForm" property="codigoId" />
			</div>

			<html:hidden property="CExpId" />
			<html:hidden property="CCuestionarioId" />
			<html:hidden property="idRepeticion" />
			<html:hidden property="CPlantillaId" />
			<logic:notEqual name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<logic:notEqual name="OCAPCuestionariosForm" property="DMensaje"
					value="">
					<div class="textoComentario">
						<bean:write name="OCAPCuestionariosForm" property="DMensaje"
							filter="false" />
					</div>
				</logic:notEqual>
			</logic:notEqual>

			<!-- Preguntas -->
			<h4 class="subTituloContenidoItinerario">
				<bean:write name="OCAPCuestionariosForm" property="DArea" />
				<bean:write name="OCAPCuestionariosForm" property="DNombre" />
				-
				<bean:write name="OCAPCuestionariosForm" property="DNombreLargo" />
			</h4>
			<fieldset>
				<% if (request.getAttribute("autoCalcularTexto")!=null && Constantes.NO.equals(request.getAttribute("autoCalcularTexto")))
                { %>
                <script>
                document.getElementById('autoponderacion').value = 'S';
                </script>
                
				<span class="negrita" style="color: #B40404;"><br />USTED ES
					QUIEN TIENE QUE CUMPLIMENTAR LOS CR&Eacute;DITOS OBTENIDOS SEGÚN LA
					INFORMACIÓN INDICADA, EN CASO CONTRARIO SE LE VA A ASIGNAR 0
					CR&Eacute;DITOS. </span>
				<%}%>
				<div class="cuadroProcedimiento ponderaciones">
					<div class="espaciador"></div>
					<div id="mensajeAvisoConfirmacion"
						style="display: none; visibility: hidden;">
						<p>
							Verifique que la informaci&oacute;n introducida en la
							Ponderaci&oacute;n de Cr&eacute;ditos es la que realmente ha
							querido usted reflejar. <br />
							<br /> Si est&aacute; de acuerdo pinche en el bot&oacute;n
							"Guardar". Al pulsar este bot&oacute;n ya no podr&aacute;
							modificar ning&uacute;n dato de este apartado. <br /> Si desea
							modificar los cr&eacute;ditos pinche en el bot&oacute;n "Volver".
							Volver&aacute; a ver la ponderaci&oacute;n y podr&aacute;
							modificar los cr&eacute;ditos como usted considere oportuno.
						</p>
					</div>

					<bean:write name="OCAPCuestionariosForm" property="DPonderacion"
						filter="false" />



					<logic:equal name="OCAPCuestionariosForm" property="BFinalizado"
						value="<%=Constantes.EST_CUEST_FINALIZAR%>">
						<span name="stComentario" class="stComentario">
							<div class="creditosTotales">
								<br />OBTENIDOS <span class="negrita total"><bean:write
										name="OCAPCuestionariosForm" property="NCreditosItinerario" /></span>
								CR&Eacute;DITOS DE <span class="negrita total"><bean:write
										name="OCAPCuestionariosForm"
										property="NCreditosPosiblesItinerario" /></span> CR&Eacute;DITOS
								M&Aacute;XIMOS.<br />
							</div>
						</span>
					</logic:equal>
					<logic:notEqual name="OCAPCuestionariosForm" property="BFinalizado"
						value="<%=Constantes.EST_CUEST_FINALIZAR%>">
						<div class="creditosTotales">
							<br />TOTAL CR&Eacute;DITOS OBTENIDOS SOBRE <span
								class="negrita total"><bean:write
									name="OCAPCuestionariosForm"
									property="NCreditosPosiblesItinerario" /></span> : <span
								class="negrita total"><html:text
									name="OCAPCuestionariosForm" property="NCreditosItinerario"
									onkeypress="return soloNumerosDecimales(event);"
									onchange="javascript:esNumeroDecimal(this);" maxlength="5" /></span><br />
						</div>
						<html:hidden name="OCAPCuestionariosForm"
							property="NCreditosPosiblesItinerario" />
					</logic:notEqual>

					<div class="espaciador"></div>
				</div>
			</fieldset>

			<div class="espaciador"></div>
			<div class="botonesPagina">
				<%if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIO_FASE_III) ) { %>
				<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
					<div class="botonAccion" id="botonSiguiente">
						<div class="margenBackTexto">
							<span><span class="textoNegrita">Finalizar:</span>
								guardar&aacute; definitivamente esta ponderaci&oacute;n.</span><br />
							<span><span class="textoNegrita">Pausar:</span>
								guardar&aacute; los cr&eacute;ditos actuales de esta
								ponderaci&oacute;n y podr&aacute; modificarlos en otro momento.</span><br />
						</div>
						<div class="flotaDerBotones">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverListado();"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Volver a la lista de formularios" />
									<span> Cancelar </span>
							</a>
							</span> <span class="derBoton"></span> <span class="izqBoton"></span> <span
								class="cenBoton"> <a
								href="javascript:pedirConfirmacion('<bean:write name="OCAPCuestionariosForm" property="BObligatorio" />');">
									<img src="imagenes/imagenes_ocap/action_forward.gif"
									class="colocaImgPrint"
									alt="Siguiente paso del Formulario para finalizarlo" /> <span>
										Finalizar </span>
							</a>
							</span> <span class="derBoton"></span> <span class="izqBoton"></span> <span
								class="cenBoton"> <a
								href="javascript:enviarFormulario('N','');"> <img
									src="imagenes/imagenes_ocap/action_forward.gif"
									class="colocaImgPrint"
									alt="Pausar el Formulario guardando los datos actuales" /> <span>
										Pausar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>

						<logic:notEqual name="OCAPCuestionariosForm"
							property="BFinalizado"
							value="<%=Constantes.EST_CUEST_FINALIZAR%>">
							<span class="izqBoton"></span>
							<span class="cenBoton"> <a
								href="javascript:verCuestionarioAsociado();"> <img
									src="imagenes/imagenes_ocap/action_refresh.gif"
									class="colocaImgPrint"
									alt="Volver a ver el formulario contestado" /> <span>
										Volver a ver formulario contestado </span>
							</a>
							</span>
							<span class="derBoton"></span>
						</logic:notEqual>
					</div>

					<div class="botonAccion" id="botonGuardar"
						style="display: none; visibility: hidden;">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviarFormulario('<bean:write name="OCAPCuestionariosForm" property="BObligatorio" />','<%=Constantes.EST_CUEST_FINALIZAR%>');">
								<img src="imagenes/imagenes_ocap/icon_accept.gif"
								class="colocaImgPrint" alt="Guardar Formulario" /> <span>
									Guardar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion" id="botonAtras"
						style="display: none; visibility: hidden;">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:volverEditarFormulario();"> <img
								src="imagenes/imagenes_ocap/icono_modificar.gif"
								class="colocaImgPrint" alt="Volver a editar Formulario" /> <span>
									Volver </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:equal>

				<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
					<logic:notEqual name="OCAPCuestionariosForm" property="BFinalizado"
						value="<%=Constantes.EST_CUEST_FINALIZAR%>">
						<div class="botonAccion" id="botonSiguiente">
							<div class="margenBackTexto">
								<span><span class="textoNegrita">Finalizar:</span>
									guardar&aacute; definitivamente esta ponderaci&oacute;n.</span><br />
								<span><span class="textoNegrita">Pausar:</span>
									guardar&aacute; los cr&eacute;ditos actuales de esta
									ponderaci&oacute;n y podr&aacute; modificarlos en otro momento.</span><br />
							</div>
							<div class="flotaDerBotones">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:volverListado();"> <img
										src="imagenes/imagenes_ocap/aspa_roja.gif"
										class="colocaImgPrint" alt="Volver a la lista de formularios" />
										<span> Cancelar </span>
								</a>
								</span> <span class="derBoton"></span> <span class="izqBoton"></span> <span
									class="cenBoton"> <a
									href="javascript:pedirConfirmacion('<bean:write name="OCAPCuestionariosForm" property="BObligatorio" />');">
										<img src="imagenes/imagenes_ocap/action_forward.gif"
										class="colocaImgPrint"
										alt="Siguiente paso del Formulario para finalizarlo" /> <span>
											Finalizar </span>
								</a>
								</span> <span class="derBoton"></span> <span class="izqBoton"></span> <span
									class="cenBoton"> <a
									href="javascript:enviarFormulario('N','');"> <img
										src="imagenes/imagenes_ocap/action_forward.gif"
										class="colocaImgPrint"
										alt="Pausar el Formulario guardando los datos actuales" /> <span>
											Pausar </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
							<logic:notEqual name="OCAPCuestionariosForm"
								property="BFinalizado"
								value="<%=Constantes.EST_CUEST_FINALIZAR%>">
								<span class="izqBoton"></span>
								<span class="cenBoton"> <a
									href="javascript:verCuestionarioAsociado();"> <img
										src="imagenes/imagenes_ocap/action_refresh.gif"
										class="colocaImgPrint"
										alt="Volver a ver el formulario contestado" /> <span>
											Volver a ver formulario contestado </span>
								</a>
								</span>
								<span class="derBoton"></span>
							</logic:notEqual>
						</div>

						<div class="botonAccion" id="botonGuardar"
							style="display: none; visibility: hidden;">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviarFormulario('<bean:write name="OCAPCuestionariosForm" property="BObligatorio" />','<%=Constantes.EST_CUEST_FINALIZAR%>');">
									<img src="imagenes/imagenes_ocap/icon_accept.gif"
									class="colocaImgPrint" alt="Guardar Formulario" /> <span>
										Guardar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<div class="botonAccion" id="botonAtras"
							style="display: none; visibility: hidden;">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverEditarFormulario();"> <img
									src="imagenes/imagenes_ocap/icono_modificar.gif"
									class="colocaImgPrint" alt="Volver a editar Formulario" /> <span>
										Volver </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</logic:notEqual>
				</logic:equal>

				<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
					<logic:equal name="OCAPCuestionariosForm" property="BFinalizado"
						value="<%=Constantes.EST_CUEST_FINALIZAR%>">
						<div class="botonAccion" id="botonVolver">
							<span class="izqBoton"></span> <span class="cenBoton"> <%-- <logic:equal name="OCAPCuestionariosForm" property="DEvidencia" value="" > --%>
								<a href="javascript:volverListado();"> <img
									src="imagenes/imagenes_ocap/flecha_correcto.gif"
									class="colocaImgPrint" alt="Volver a la p?gina anterior" /> <span>
										Volver </span>
							</a> <%-- 
                  </logic:equal>
                  <logic:notEqual name="OCAPCuestionariosForm" property="DEvidencia" value="" >
                     <logic:equal name="saltarPonderacion" value="<%=Constantes.SI%>" >
                        <a href="javascript:volverListado();">
                           <img src="imagenes/imagenes_ocap/flecha_correcto.gif" class="colocaImgPrint" alt="Volver a la p?gina anterior" />
                           <span> Volver </span>
                        </a>
                     </logic:equal>
                     <logic:notEqual name="saltarPonderacion" value="<%=Constantes.SI%>" >
                        <a href="javascript:verCuestionarioEvidencia();">
                           <img src="imagenes/imagenes_ocap/flecha_correcto.gif" class="colocaImgPrint" alt="Volver a la p?gina anterior" />
                           <span> Continuar </span>
                        </a>
                     </logic:notEqual>
                  </logic:notEqual>
                  --%>
							</span> <span class="derBoton"></span>
						</div>
						<script language="javascript" type="text/javascript">
               deshabilitarFormulario();
            </script>
					</logic:equal>
				</logic:equal>
				<%} else if(Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol()) ) { %>
				<div class="botonAccion" id="botonVolver">
					<span class="izqBoton"></span> <span class="cenBoton"> <logic:equal
							name="OCAPCuestionariosForm" property="CEvidenciaId" value="0">
							<a href="javascript:volverListado();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								class="colocaImgPrint" alt="Volver al listado de formularios" />
								<span> Volver </span>
							</a>
						</logic:equal> <logic:notEqual name="OCAPCuestionariosForm"
							property="CEvidenciaId" value="0">
							<a
								href="javascript:verEvidencia('<bean:write  name="OCAPCuestionariosForm" property="CEvidenciaId" />','<bean:write  name="OCAPCuestionariosForm" property="NEvidencia" />','<bean:write  name="OCAPCuestionariosForm" property="CCuestionarioId" />','<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />');">
								<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
								class="colocaImgPrint" alt="Volver al listado de formularios" />
								<span> Ver Evidencia </span>
							</a>
						</logic:notEqual>
					</span> <span class="derBoton"></span>
				</div>
				<% } else { %>
				<div class="botonAccion" id="botonVolver">
					<span class="izqBoton"></span> <span class="cenBoton"> <logic:equal
							name="OCAPCuestionariosForm" property="CEvidenciaId" value="0">
							<a href="javascript:volverEval();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								class="colocaImgPrint" alt="Volver al listado de formularios" />
								<span> Volver </span>
							</a>
						</logic:equal> <logic:notEqual name="OCAPCuestionariosForm"
							property="CEvidenciaId" value="0">
							<a
								href="javascript:verEvidencia('<bean:write  name="OCAPCuestionariosForm" property="CEvidenciaId" />','<bean:write  name="OCAPCuestionariosForm" property="NEvidencia" />','<bean:write  name="OCAPCuestionariosForm" property="CCuestionarioId" />','<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />');">
								<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
								class="colocaImgPrint" alt="Volver al listado de formularios" />
								<span> Ver Evidencia </span>
							</a>
						</logic:notEqual>
					</span> <span class="derBoton"></span>
				</div>
				<% } %>
			</div>
		</html:form>
	</div>
	<!-- /fin de ContenidoTextoProcedimiento -->
</div>
<!-- /Fin de Contenido -->
<div class="limpiadora"></div>
