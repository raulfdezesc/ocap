<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.documentos.OCAPDocumentosOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.areasItinerarios.OCAPAreasItinerariosOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/fechas.js'/>"></script>

<script language="javascript">
function guardarInforme() {
   if (ValidarFechaConAlerta(document.forms[0].FReunion, 'Reuni\u00F3n')) {   
      if (validarInformeCTE()) {       
         enviar('OCAPCuestionarios.do?accion=guardarInformeCTE');
      }
   }
}

function validarInformeCTE(){
   if (!document.forms[0].BConformidadCTE[0].checked && !document.forms[0].BConformidadCTE [1].checked) {
      alert('Debe seleccionar si est\u00E1 o no est\u00E1 conforme con la evaluaci\u00F3n del evaluador.');
      return false;
   }
  
  
   if (document.forms[0].AEspecificaciones.value =='') {
      alert('Debe indicar las especificaciones que realiza para el informe.');
      return false;
   }
   
   	if (!document.forms[0].NCreditosEvaluadosCTE.value == '') {
			var valor = document.forms[0].NCreditosEvaluadosCTE.value;
				if (!valor.match(/^\d+(\.\d{1,9})?$/)) {
					alert('El n\u00FAmero de cr\u00E9ditos tiene que ser num\u00E9rico utilizando el punto como separador decimal');
					return false;
				} 
		} 
   return true;
   
}

function habilitarRevision(bVer){
   if (bVer != 'Y') {
      document.getElementById('nuevaRevision').style.display='';
      document.getElementById('nuevaRevision').style.visibility='visible';
       document.forms[0].NCreditosEvaluadosCTE.disabled=false;
   } else {
      document.getElementById('nuevaRevision').style.display='none';
      document.getElementById('nuevaRevision').style.visibility='hidden';
      document.forms[0].NCreditosEvaluadosCTE.disabled=true;
   }
}

function habilitarDiscrepancias(num){
   if (num != '1') {
      document.getElementById('discr_2').style.display='';
      document.getElementById('discr_2').style.visibility='visible';
      document.getElementById('discr_1').style.display='none';
      document.getElementById('discr_1').style.visibility='hidden';
      document.forms[0].discrepancias1.value='';
   } else {
      document.getElementById('discr_1').style.display='';
      document.getElementById('discr_1').style.visibility='visible';
      document.getElementById('discr_2').style.display='none';
      document.getElementById('discr_2').style.visibility='hidden';
      document.forms[0].discrepancias2.value='';
   }
}

function deshabilitar(){
   if (document.forms[0].BConformidadCTE[1].checked)
      habilitarRevision('N');
   document.forms[0].BConformidadCTE[0].disabled = true;
   document.forms[0].BConformidadCTE[1].disabled = true;
   if (document.forms[0].BNuevaRevision[0].checked) {
      habilitarDiscrepancias(1);
      document.forms[0].discrepancias1.value=document.forms[0].ADiscrepancias.value;
      document.forms[0].discrepancias1.disabled=true;
   }
   if (document.forms[0].BNuevaRevision[1].checked) {
      habilitarDiscrepancias(2);
      document.forms[0].discrepancias2.value=document.forms[0].ADiscrepancias.value;
      document.forms[0].discrepancias2.disabled=true;
   }
   document.forms[0].BNuevaRevision[0].disabled = true;
   document.forms[0].BNuevaRevision[1].disabled = true;
}

function volverEvaluacion(){
   enviar('OCAPCuestionarios.do?accion=irListar&cCompfqsIdS=<%=request.getAttribute("cCompfqsIdS")%>&expId='+document.forms[0].CExpId.value);
}

function volverListado(){
   enviar('OCAPEvaluadores.do?accion=listarEvaluados&cCompfqsIdS=<%=session.getAttribute("cCompfqsIdS")%>&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
}

function volverListadoPorCTE(){
   enviar('OCAPComponentesCtes.do?accion=listarEvaluados&cCteIdS=<%=session.getAttribute("cCteIdS")%>&nombre=<%=session.getAttribute("nombreCTE")%>');
}

function informeEval(){
   enviar('OCAPCuestionarios.do?accion=generarInforme&expId='+document.forms[0].CExpId.value);
}

var LONG_MAX_ESPECIFICACIONES= <%=Constantes.LONG_ESPECIFICACIONES_EVALUADOR%>;
function longMaxTextarea(campo) {
   if(campo.value.length <= LONG_MAX_ESPECIFICACIONES) {
      return true;
   } else {
      campo.value = campo.value.substring(0,LONG_MAX_ESPECIFICACIONES);
      return false;
   }
}
</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoAutoevaluacion">
		<div class="evaluacionEvaluador">
			<html:form action="/OCAPCuestionarios.do">
				<h2 class="tituloContenido">INFORME DE CONFORMIDAD DEL
					COMIT&Eacute; T&Eacute;CNICO DE EVALUACI&Oacute;N</h2>
				<% if (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)) {%>
				<a href="javascript:window.print();"><img
					src="imagenes/imagenes_ocap/imprimir.gif" alt="Imprimir"
					class="flotaIzq" /></a>
				<% }%>
				<!--<h3 class="subTituloContenido"> Evaluaci&oacute;n/Pruebas de buena pr&aacute;ctica asignados a cada &Aacute;REA </h3>-->
				<h3 class="subTituloContenido">&nbsp;</h3>
				<div class="lineaBajaM"></div>
				<div class="espaciador"></div>
				<html:hidden name="OCAPCuestionariosForm" property="CItinerarioId" />
				<html:hidden name="OCAPCuestionariosForm"
					property="NCreditosEvaluados" />
				<fieldset>
					<legend class="tituloLegend">DATOS DE
						IDENTIFICACI&Oacute;N DEL COMIT&Eacute;</legend>
					<div class="cuadroFieldset">
						<span>Nombre del Comit&eacute;: </span>
						<bean:write name="OCAPCuestionariosForm"
							property="DNombrePresidente" />
					</div>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend">PROFESIONAL EVALUADO</legend>
					<div class="cuadroFieldset">
						<span>C&oacute;digo del Evaluado: </span>
						<bean:write name="OCAPCuestionariosForm" property="codigoId" />
						<br />
						<br /><span>Nombre y apellidos del Evaluado: </span>
						<bean:write name="OCAPCuestionariosForm" property="DNombreUsuario" />
						<bean:write name="OCAPCuestionariosForm" property="DApellido1" />
						<br />
						<br /><span>NIF/NIE del Evaluado: </span>
						<bean:write name="OCAPCuestionariosForm" property="CDni" />
						<br />
						<br /> <span>Especialidad/Categor&iacute;a/Desempe&ntilde;o:
						</span>
						<bean:write name="OCAPCuestionariosForm"
							property="DCategoriaEspecialidadEvaluado" />
						.<br />
						<br /> <span>Itinerario de Evaluaci&oacute;n Competencial
							(IEC) evaluado: </span>
						<bean:write name="OCAPCuestionariosForm"
							property="DNombreItinerario" />
						<br />
						<br /> <span>Grado de Carrera Profesional solicitado: </span>
						<bean:write name="OCAPCuestionariosForm" property="DGrado_des" />
					</div>
					<html:hidden property="CExpId" />
				</fieldset>

				<fieldset>
					<legend class="tituloLegend">EVALUADOR</legend>
					<div class="cuadroFieldset">
						<span>C&oacute;digo de Evaluador: </span>
						<bean:write name="OCAPCuestionariosForm"
							property="codigoEvaluadorId" />
						<br />
						<br /> <span>Fecha de inicio Evaluaci&oacute;n Externa: </span>
						<bean:write name="OCAPCuestionariosForm"
							property="FInicioEvaluacion" />
						<br />
						<br /> <span>Fecha de finalizaci&oacute;n
							Evaluaci&oacute;n Externa: </span>
						<bean:write name="OCAPCuestionariosForm" property="FFinEvaluacion" />
						<br />
						<br />
					</div>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend">INFORME DE CONFORMIDAD DEL
						COMIT&Eacute; T&Eacute;CNICO DE EVALUACI&Oacute;N </legend>
					<div class="cuadroFieldset informesConformidad">

						<p>
							Como Presidente del Comit&eacute; Espec&iacute;fico de
							Instituciones Sanitarias de
							<bean:write name="OCAPCuestionariosForm" property="DNombreCTE" />
							del Complejo Asistencial
							<bean:write name="OCAPCuestionariosForm" property="DProvincia" />
							creado para el proceso de valoraci&oacute;n de m&eacute;ritos
							para el reconocimiento de <bean:write name="OCAPCuestionariosForm" property="DGrado_des" />  de Carrera Profesional en el
							&aacute;mbito del servicio de Salud de Castilla y Le&oacute;n, y
							tras la reuni&oacute;n celebrada el
							<logic:notEqual name="informeGenerado" value="<%=Constantes.SI%>">
								<html:text name="OCAPCuestionariosForm" property="FReunion"
									maxlength="10" />
							</logic:notEqual>
							<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
								<bean:write name="OCAPCuestionariosForm" property="FReunion" />
							</logic:equal>
							INFORMO:
						</p>
						<p>
							<span class="textoNegrita">1&ordm;/</span> Que el profesional
							evaluado D/D&ntilde;a <span class="negrita"><bean:write
									name="OCAPCuestionariosForm" property="DNombreUsuario" /> <bean:write
									name="OCAPCuestionariosForm" property="DApellido1" /></span>, ha
							realizado la Evaluaci&oacute;n del Desempe&ntilde;o de la
							Competencia seg&uacute;n el Itinerario de Evaluaci&oacute;n
							Competencial que le corresponde en funci&oacute;n de su
							categor&iacute;a profesional para el reconocimiento de <bean:write name="OCAPCuestionariosForm" property="DGrado_des" /> 
							de Carrera Profesional, obteniendo los siguientes cr&eacute;ditos
							<span class="nota"><bean:write
									name="OCAPCuestionariosForm" property="NCreditosItinerario" /></span>
							sobre <span class="nota"><bean:write
									name="OCAPCuestionariosForm" property="NCreditosNecesarios" /></span>
						</p>
						<p>
							<span class="textoNegrita">2&ordm;/</span> Que el an&aacute;lisis
							de Evaluaci&oacute;n realizado por el evaluador superior
							jer&aacute;rquico ha dado como resultado cr&eacute;ditos: <span
								class="nota"><bean:write name="OCAPCuestionariosForm"
									property="NCreditosEvaluados" /></span> sobre los <span class="nota"><bean:write
									name="OCAPCuestionariosForm" property="NCreditosNecesarios" /></span>
							necesarios.
						</p>
						<p>
							<span class="textoNegrita">3&ordm;/</span> Por lo cual, , el
							C.E.I.S
							<bean:write name="OCAPCuestionariosForm" property="DNombreCTE" />
							acuerda:
						</p>
						<ul>
							<li>
								<p>
									<html:radio name="OCAPCuestionariosForm"
										property="BConformidadCTE" value="<%=Constantes.SI%>"
										onclick="javascript:habilitarRevision('Y');" />
									<span class="negrita">CONFORMIDAD</span> con la
									evaluaci&oacute;n realizada por el Evaluador y validar su
									actuaci&oacute;n, en la que se otorgan al Profesional Evaluado
									<span class="nota"><bean:write
											name="OCAPCuestionariosForm" property="NCreditosEvaluados" /></span>
									cr&eacute;ditos.
								</p>
							</li>
							<li>
								<p>
									<html:radio name="OCAPCuestionariosForm"
										property="BConformidadCTE" value="<%=Constantes.NO%>"
										onclick="javascript:habilitarRevision('N');" />
									<span class="negrita">NO CONFORMIDAD</span> con la
									evaluaci&oacute;n realizada por el Evaluador y otorgar los
									cr&eacute;ditos siguientes:
									<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
										<span class="nota"><bean:write
												name="OCAPCuestionariosForm"
												property="NCreditosEvaluadosCTE" /></span> cr&eacute;ditos.
								</p> </logic:equal> <logic:equal name="informeGenerado" value="<%=Constantes.NO%>">
									<span class="notaCredito" id="nCreditosCTE"><html:text
											property="NCreditosEvaluadosCTE" size="4" maxlength="5"
											disabled="true" /></span> cr&eacute;ditos.</p>
								</logic:equal>
							</li>

							<html:hidden name="OCAPCuestionariosForm"
								property="ADiscrepancias" />
							<html:hidden name="OCAPCuestionariosForm"
								property="NCreditosNecesarios" />
							<div id="nuevaRevision"
								style="display: none; visibility: hidden;"></div>
							</li>
						</ul>
						<p>
							<span class="textoNegrita">4&ordm;/</span> El C.E.I.S remite este
							informe a la Comisi&oacute;n Central.
						</p>
						<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
							<div class="bordeCuadro ajusteBordeCuadro">
								<bean:write name="OCAPCuestionariosForm"
									property="AEspecificaciones" />
							</div>
						</logic:equal>
						<logic:notEqual name="informeGenerado" value="<%=Constantes.SI%>">
							<html:textarea styleClass="bordeCuadro"
								name="OCAPCuestionariosForm" property="AEspecificaciones"
								rows="5%" cols="95%"
								onkeypress="javascript:return longMaxTextarea(this);"
								onclick="javascript:return longMaxTextarea(this);"
								onkeydown="javascript:return longMaxTextarea(this);"
								onkeyup="javascript:return longMaxTextarea(this);"></html:textarea>
						</logic:notEqual>
						<br />
						<br /> Fecha:
						<bean:write name="OCAPCuestionariosForm" property="FEvaluacion" />
					</div>
				</fieldset>

				<div class="limpiadora"></div>
				<div class="espaciador"></div>

				<div class="botonesPagina">
					<logic:notEqual name="informeGenerado" value="<%=Constantes.SI%>">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:guardarInforme();"> <img
									src="imagenes/imagenes_ocap/action_forward.gif"
									class="colocaImgPrint" alt="Guardar Informe Evaluador" /> <span>
										Guardar Informe </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverEvaluacion();"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Cancelar" /> <span>
										Cancelar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</logic:notEqual>
					<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
						<%if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE)) { %>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviar('OCAPCuestionarios.do?accion=generarInformeCE&cCompfqsIdS=<%=session.getAttribute("cCompfqsIdS")%>');">
									<img src="imagenes/imagenes_ocap/action_forward.gif"
									class="colocaImgPrint" alt="Informe CE" /> <span>
										Informe CE</span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<% } %>
						<% if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR)) {%>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverListado();"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Cancelar" /> <span> Volver
								</span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<%}else if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE)) {%>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverListadoPorCTE();"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Volver" /> <span> Volver
										Listado </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<%}else{%>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverEvaluacion();"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Volver" /> <span> Volver </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<%}%>
						<% if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE)) {%>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:informeEval();"> <img
									src="imagenes/imagenes_ocap/action_forward.gif"
									class="colocaImgPrint" alt="Ver informe Evaluador" /> <span>
										Ver informe Evaluador </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<%}%>
						<script language="javascript">
               deshabilitar();
            </script>
					</logic:equal>
				</div>
			</html:form>
		</div>
	</div>
	<!-- /fin de ContenidoMC -->
</div>
<!-- /Fin de Contenido -->

<div class="limpiadora"></div>
