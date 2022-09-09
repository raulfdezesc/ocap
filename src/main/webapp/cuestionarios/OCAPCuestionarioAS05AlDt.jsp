<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.respuestas.OCAPRespuestasOT"%>
<%@ page import="java.util.ArrayList"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/fechas.js'/>"></script>

<script language="javascript">

function enviarFormulario(obligatorio, fin) {
   if (comprobarFormularioRelleno(fin)) {
      var cadenaParam='';
      cadenaParam += '&creditos1='+document.getElementById('creditos1').value;
      cadenaParam += '&creditos2='+document.getElementById('creditos2').value;
      cadenaParam += '&creditos3='+document.getElementById('creditos3').value;
      cadenaParam += '&creditos4='+document.getElementById('creditos4').value;
      enviar('OCAPCuestionarios.do?accion=insertarCuestionarioAS05'+cadenaParam+'&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioId" />&idRepeticion=<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />&finalizar='+fin);
   }
}

function volverListado() {
   enviar('OCAPCuestionarios.do?accion=irListar');
}

function comprobarFormularioRelleno(fin){
   var relleno=true;
   var todasPuntuaciones = true;
   var e=document.getElementsByTagName("input");
   for(var j=0;j<e.length;j++){
      if (e[j].type == 'text'){
         if (e[j].id.indexOf('creditos') != -1 && e[j].value=='')
            relleno = false;
         if (e[j].id.indexOf('puntos') != -1 && e[j].value=='')
            todasPuntuaciones=false;
      }
   }
   if (!todasPuntuaciones) {
      alert('Debe contestar primero a todos los formularios de este \u00e1rea.');
      enviar('OCAPCuestionarios.do?accion=irListar');
   } else if (!relleno && fin=='<%=Constantes.EST_CUEST_FINALIZAR%>')
      alert('Debe indicar los cr\u00e9ditos de todos los apartados.');
   if (!relleno && fin!='<%=Constantes.EST_CUEST_FINALIZAR%>')
      return true;
   else return relleno;
}

function soloNumerosDecimales(evt) {
   //se admiten: numeros y "." 
   var nav4 = window.Event ? true : false;
   var key = nav4 ? evt.which : evt.keyCode; 
   return (key <= 13 || (key >= 48 && key <= 57) || key==46);
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
   if (obligatorio == '<%=Constantes.NO%>' || (obligatorio == '<%=Constantes.SI%>' && comprobarFormularioRelleno()) ) {
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
   for (i=0; i < document.forms[0].elements.length; i++) {
      document.forms[0].elements[i].disabled=true;
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

</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoProcedimiento">

		<html:form action="/OCAPCuestionarios.do">

			<h2 class="tituloContenido">
				<bean:write name="OCAPCuestionariosForm" property="DArea" />
				PROCEDIMIENTO DE EVALUACI&Oacute;N DEL
				<bean:write name="OCAPCuestionariosForm" property="DAreaLargo" />
				DE LAS COMPETENCIAS PROFESIONALES DE LA CARRERA PROFESIONAL
			</h2>

			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<logic:notEqual name="OCAPCuestionariosForm" property="DEvidencia"
					value="">
					<div class="botonesPagina">
						<a href="javascript:window.print();"><img
							src="imagenes/imagenes_ocap/imprimir.gif" alt="Imprimir"
							class="flotaIzq" /></a>
					</div>
				</logic:notEqual>
			</logic:equal>

			<h3 class="subTituloContenido">
				<bean:write name="OCAPCuestionariosForm" property="DTitulo" />
			</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<html:hidden property="CExpId" />

			<logic:notEqual name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<logic:notEqual name="OCAPCuestionariosForm" property="DMensaje"
					value="">
					<div class="textoComentario">
						<bean:write name="OCAPCuestionariosForm" property="DMensaje"
							filter="false" />
					</div>
				</logic:notEqual>
			</logic:notEqual>

			<h4 class="subTituloContenidoItinerario">
				<bean:write name="OCAPCuestionariosForm" property="DArea" />
				<bean:write name="OCAPCuestionariosForm" property="DNombre" />
				-
				<bean:write name="OCAPCuestionariosForm" property="DNombreLargo" />
			</h4>
			<!-- Preguntas -->
			<fieldset>
				<div class="cuadroProcedimiento">
					<div class="espaciador"></div>
					<div id="mensajeAvisoConfirmacion"
						style="display: none; visibility: hidden;">
						<p>
							Verifique que la informaci&oacute;n introducida en el formulario
							"
							<bean:write name="OCAPCuestionariosForm" property="DNombreLargo" />
							" es la que realmente ha querido usted reflejar. <br />
							<br /> Si est&aacute; de acuerdo pinche en el bot&oacute;n
							"Finalizar". Al pulsar este bot&oacute;n ya no podr&aacute;
							modificar ning&uacute;n dato de este apartado. <br /> Si desea
							modificar alguno de los datos pinche en el bot&oacute;n
							"Modificar". Volver&aacute; a ver el formulario y podr&aacute;
							cambiar todos aquellos aspectos que usted considere necesarios.
						</p>
					</div>

					<span name="stComentario" class="stComentario">
						Cumplim�ntelo una vez evaluadas las 10 historias cl�nicas. <br />
					<br /> Para asignar y correlacionar los "cr&eacute;ditos
						obtenidos" con los cr&eacute;ditos que le corresponde a Carrera
						Profesional utilice este criterio: <br />
					<br />
						<ul class="listadoSangrado">
							<li>a) Para los 3 informes de alta, 3 informes
								quir&uacute;rgicos, y 3 informes de consultas externas realice
								una media aritm�tica a cada grupo. <br />
							<br /> <span class="listadoSangrado">Ejemplo informe =
									*cr&eacute;ditos + ?cr&eacute;ditos +*cr&eacute;ditos/3= z</span> <br />
							<br />
							</li>
							<li>b) Para la evaluaci&oacute;n integral de la H�
								CL./episodio asistencial al ser una sola el valor obtenido se
								corresponder&aacute;: <br />
							<br /> <span class="listadoSangrado"> De 0-500: 5
									cr&eacute;ditos de carrera<br /> De 501-799: 10
									cr&eacute;ditos de carrera<br /> De 800-1000: 15
									cr&eacute;ditos de carrera<br />
							</span> <br />
							<br />
							</li>
							<li>c) La correspondencia para el apartado a) ser&aacute;: <br />
							<br />
								<ul class="listadoSangrado">
									<li>c-1 Informe de alta: <br />
									<br />
										<ul class="listadoSangrado">
											<li>de 0-50 = 5 cr&eacute;ditos de carrera</li>
											<li>de 51-79 = 10 cr&eacute;ditos de carrera</li>
											<li>de 80-100 = 15 cr&eacute;ditos de carrera</li>
										</ul> <br />
									<br />
									</li>
									<li>c-2 Informe quir�rgico: <br />
									<br />
										<ul class="listadoSangrado">
											<li>de 0-50 = 5 cr&eacute;ditos de carrera</li>
											<li>de 51-79 = 7 cr&eacute;ditos de carrera</li>
											<li>de 80-100 = 9 cr&eacute;ditos de carrera</li>
										</ul> <br />
									<br />
									</li>
									<li>c-3 Informe C. Externas: <br />
									<br />
										<ul class="listadoSangrado">
											<li>de 0-50 = 4 cr&eacute;ditos de carrera</li>
											<li>de 51-79 = 6 cr&eacute;ditos de carrera</li>
											<li>de 80-100 = 8 cr&eacute;ditos de carrera</li>
										</ul> <br />
									<br />
									</li>
								</ul> <br />
							<br />
							</li>
						</ul>
					</span>

					<% ArrayList listaHistorias = (ArrayList) request.getAttribute("listaHistorias"); %>
					<% ArrayList listaPuntos = (ArrayList) request.getAttribute("listaPuntos"); %>
					<% ArrayList listaCreditos = (ArrayList) request.getAttribute("listaCreditos"); %>
					<table class="tablaAS05" cellpadding="2" cellspacing="2">
						<tr>
							<th class="col1"></th>
							<th class="col2">H� Cl&iacute;nica</th>
							<th class="col3">Cr&eacute;ditos obtenidos en
								autoevaluaci&oacute;n</th>
							<th class="col4">Correspondecia con cr&eacute;ditos de
								Carrera Profesional</th>
						</tr>
						<tr>
							<td rowspan="3" class="col1">AS.01</td>
							<td class="col2"><input type="text"
								value="<%=listaHistorias.get(0)%>" readonly="readonly" /></td>
							<td class="col3"><input type="text" id="puntos1"
								value="<%=listaPuntos.get(0)%>" readonly="readonly" /></td>
							<td rowspan="3" class="col4"><logic:equal name="tipoAccion"
									value="<%=Constantes.VER_DETALLE%>">
									<input type="text" id="creditos1" name="creditos1"
										onkeypress="return soloNumerosDecimales(event);" maxlength="4"
										value="<%=listaCreditos.get(0)%>" />
								</logic:equal> <logic:notEqual name="tipoAccion"
									value="<%=Constantes.VER_DETALLE%>">
									<input type="text" id="creditos1" name="creditos1"
										onkeypress="return soloNumerosDecimales(event);" maxlength="4" />
								</logic:notEqual></td>
						</tr>
						<tr>
							<td class="col2"><input type="text"
								value="<%=listaHistorias.get(1)%>" readonly="readonly" /></td>
							<td class="col3"><input type="text" id="puntos2"
								value="<%=listaPuntos.get(1)%>" readonly="readonly" /></td>
						</tr>
						<tr>
							<td class="col2"><input type="text"
								value="<%=listaHistorias.get(2)%>" readonly="readonly" /></td>
							<td class="col3"><input type="text" id="puntos3"
								value="<%=listaPuntos.get(2)%>" readonly="readonly" /></td>
						</tr>
						<tr>
							<td rowspan="3" class="col1">AS.02</td>
							<td class="col2"><input type="text"
								value="<%=listaHistorias.get(3)%>" readonly="readonly" /></td>
							<td class="col3"><input type="text" id="puntos4"
								value="<%=listaPuntos.get(3)%>" readonly="readonly" /></td>
							<td rowspan="3" class="col4"><logic:equal name="tipoAccion"
									value="<%=Constantes.VER_DETALLE%>">
									<input type="text" id="creditos2" name="creditos2"
										onkeypress="return soloNumerosDecimales(event);" maxlength="4"
										value="<%=listaCreditos.get(1)%>" />
								</logic:equal> <logic:notEqual name="tipoAccion"
									value="<%=Constantes.VER_DETALLE%>">
									<input type="text" id="creditos2" name="creditos2"
										onkeypress="return soloNumerosDecimales(event);" maxlength="4" />
								</logic:notEqual></td>
						</tr>
						<tr>
							<td class="col2"><input type="text"
								value="<%=listaHistorias.get(4)%>" readonly="readonly" /></td>
							<td class="col3"><input type="text" id="puntos5"
								value="<%=listaPuntos.get(4)%>" readonly="readonly" /></td>
						</tr>
						<tr>
							<td class="col2"><input type="text"
								value="<%=listaHistorias.get(5)%>" readonly="readonly" /></td>
							<td class="col3"><input type="text" id="puntos6"
								value="<%=listaPuntos.get(5)%>" readonly="readonly" /></td>
						</tr>
						<tr>
							<td rowspan="3" class="col1">AS.03</td>
							<td class="col2"><input type="text"
								value="<%=listaHistorias.get(6)%>" readonly="readonly" /></td>
							<td class="col3"><input type="text" id="puntos7"
								value="<%=listaPuntos.get(6)%>" readonly="readonly" /></td>
							<td rowspan="3" class="col4"><logic:equal name="tipoAccion"
									value="<%=Constantes.VER_DETALLE%>">
									<input type="text" id="creditos3" name="creditos3"
										onkeypress="return soloNumerosDecimales(event);" maxlength="4"
										value="<%=listaCreditos.get(2)%>" />
								</logic:equal> <logic:notEqual name="tipoAccion"
									value="<%=Constantes.VER_DETALLE%>">
									<input type="text" id="creditos3" name="creditos3"
										onkeypress="return soloNumerosDecimales(event);" maxlength="4" />
								</logic:notEqual></td>
						</tr>
						<tr>
							<td class="col2"><input type="text"
								value="<%=listaHistorias.get(7)%>" readonly="readonly" /></td>
							<td class="col3"><input type="text" id="puntos8"
								value="<%=listaPuntos.get(7)%>" readonly="readonly" /></td>
						</tr>
						<tr>
							<td class="col2"><input type="text"
								value="<%=listaHistorias.get(8)%>" readonly="readonly" /></td>
							<td class="col3"><input type="text" id="puntos9"
								value="<%=listaPuntos.get(8)%>" readonly="readonly" /></td>
						</tr>
						<tr>
							<td class="col1">AS.04</td>
							<td class="col2"><input type="text"
								value="<%=listaHistorias.get(9)%>" readonly="readonly" /></td>
							<td class="col3"><input type="text" id="puntos10"
								value="<%=listaPuntos.get(9)%>" readonly="readonly" /></td>
							<td class="col4"><logic:equal name="tipoAccion"
									value="<%=Constantes.VER_DETALLE%>">
									<input type="text" id="creditos4" name="creditos4"
										onkeypress="return soloNumerosDecimales(event);" maxlength="4"
										value="<%=listaCreditos.get(3)%>" />
								</logic:equal> <logic:notEqual name="tipoAccion"
									value="<%=Constantes.VER_DETALLE%>">
									<input type="text" id="creditos4" name="creditos4"
										onkeypress="return soloNumerosDecimales(event);" maxlength="4" />
								</logic:notEqual></td>
						</tr>
					</table>
					<div class="espaciador"></div>
				</div>
			</fieldset>

			<div class="espaciador"></div>
			<div class="botonesPagina">

				<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
					<div class="botonAccion" id="botonSiguiente">
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
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverListado();"> <img
									src="imagenes/imagenes_ocap/flecha_correcto.gif"
									class="colocaImgPrint" alt="Volver a la p�gian anterior" />
									<span> Volver </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<script language="javascript">
               deshabilitarFormulario();
            </script>
					</logic:equal>
				</logic:equal>

			</div>
		</html:form>
	</div>
	<!-- /fin de ContenidoTextoProcedimiento -->
</div>
<!-- /Fin de Contenido -->

<div class="limpiadora"></div>