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
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>

<script language="javascript">
function generarInforme2PDF(){
   enviar('OCAPCuestionarios.do?accion=generarInforme2PDF&expId='+document.forms[0].CExpId.value)
}

function guardarInforme() {
   if (validar()) {
      document.forms[0].BAuditoria.disabled = false;
      enviar('OCAPCuestionarios.do?accion=guardarInforme2');
   }
}

function validar(){
   if (document.forms[0].AMotivoAuditoria.value=='') {
      alert('Debe indicar el motivo de la auditor\u00EDa espec\u00EDfica elegida.');
      return false;
   }
   if (document.forms[0].FAuditoria.value.length > 0) {
      if (!ValidarFechaConAlerta(document.forms[0].FAuditoria,'Auditoría'))
      return false;
   }
   if (!document.forms[0].BCumplimiento[0].checked && !document.forms[0].BCumplimiento [1].checked) {
      alert('Debe indicar si existe cumplimiento o incumplimiento con los criterios establecidos en el itinerario.');
      return false;
   }
   if (document.forms[0].BCumplimiento [1].checked && !document.forms[0].BCategorizacion[0].checked && !document.forms[0].BCategorizacion[1].checked && !document.forms[0].BCategorizacion[2].checked) {
      alert('Si existe incumplimiento con los criterios establecidos en el itinerario, debe indicar la categorizaci\u00F3n de dicho incumplimiento.');
      return false;
   }
   if (document.forms[0].ARecomendaciones.value=='') {
      alert('Debe indicar las recomendaciones.');
      return false;
   }
   if (document.forms[0].AConclusiones.value=='') {
      alert('Debe indicar las conclusiones de la auditor\u00EDa.');
      return false;
   }
   return true;
}

function habilitarIncumplimiento(bVer){
   if (bVer != 'Y') {
      document.getElementById('incumplimiento').style.display='';
      document.getElementById('incumplimiento').style.visibility='visible';
   } else {
      document.getElementById('incumplimiento').style.display='none';
      document.getElementById('incumplimiento').style.visibility='hidden';
   }
}


function habilitarCreditos(bHabilitar){
/*
   if (bHabilitar=='Y') {
      document.forms[0].NCreditosEvaluados.readOnly=false;
   } else {
      document.forms[0].NCreditosEvaluados.readOnly=true;
   }
*/
}

function volverEvaluacion(){
   <% if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CTE)){%>
      enviar('OCAPCuestionarios.do?accion=irListar&expId='+document.forms[0].CExpId.value);
   <%}else{%>
      enviar('OCAPCuestionarios.do?accion=irListar&cCompfqsIdS=<%=request.getAttribute("cCompfqsIdS")%>&expId='+document.forms[0].CExpId.value);
   <%}%>
}

function volverListado(){
   enviar('OCAPEvaluadores.do?accion=listarEvaluados&cCompfqsIdS=<%=session.getAttribute("cCompfqsIdS")%>&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
}

function verItinerario(){
   enviar('OCAPCuestionarios.do?accion=irListar&expId='+document.forms[0].CExpId.value);
}

var LONG_CAMPOS_EVALUADOR_1000= <%=Constantes.LONG_CAMPOS_EVALUADOR_1000%>;
function longMaxTextarea(campo) {
   if(campo.value.length <= LONG_CAMPOS_EVALUADOR_1000) {
      return true;
   } else {
      campo.value = campo.value.substring(0,LONG_CAMPOS_EVALUADOR_1000);
      return false;
   }
}

var LONG_CAMPOS_EVALUADOR_2000= <%=Constantes.LONG_CAMPOS_EVALUADOR_2000%>;
function longMaxTextarea2(campo) {
   if(campo.value.length <= LONG_CAMPOS_EVALUADOR_2000) {
      return true;
   } else {
      campo.value = campo.value.substring(0,LONG_CAMPOS_EVALUADOR_2000);
      return false;
   }
}

</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoAutoevaluacion">
		<div class="evaluacionEvaluador">
			<html:form action="/OCAPCuestionarios.do">
				<h2 class="tituloContenido">INFORME DE EVALUACI&Oacute;N</h2>

				<!--<h3 class="subTituloContenido"> Evaluaci&oacute;n/Pruebas de buena pr&aacute;ctica asignados a cada &Aacute;REA </h3>-->
				<h3 class="subTituloContenido">&nbsp;</h3>
				<div class="lineaBajaM"></div>



				<fieldset>
					<legend class="tituloLegend">EVALUADOR</legend>
					<div class="cuadroFieldset informesConformidad">
						<% if ( jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION)) {%>
						<span>Nombre y Apellidos: </span>
						<bean:write name="OCAPCuestionariosForm"
							property="DNombreEvaluador" />
						<bean:write name="OCAPCuestionariosForm"
							property="DApellidosEvaluador" />
						<br />
						<br /> <span>DNI: </span>
						<bean:write name="OCAPCuestionariosForm" property="CDniEvaluador" />
						<br />
						<br /> <span>Tel&eacute;fono de contacto: </span>
						<bean:write name="OCAPCuestionariosForm"
							property="NTelefonoEvaluador" />
						<br />
						<br /> <span>Correo electr&oacute;nico: </span>
						<bean:write name="OCAPCuestionariosForm"
							property="ACorreoelectronicoEvaluador" />
						<br />
						<br /> <span>Evaluador acreditado por la FQS para
							categor&iacute;a/especialidad de: </span>
						<bean:write name="OCAPCuestionariosForm"
							property="DCategoriaEspecialidadEvaluado" />
						<!--bean:write name="OCAPCuestionariosForm" property="DCategoriaEspecialidadEvaluador" /-->
						<br />
						<br /> <span>Fecha de Inicio Evaluaci&oacute;n: </span>
						<bean:write name="OCAPCuestionariosForm"
							property="FInicioEvaluacion" />
						<html:hidden name="OCAPCuestionariosForm"
							property="FInicioEvaluacion" />
						<% } else { %>
						<span>C&oacute;digo de Evaluador: </span>
						<bean:write name="OCAPCuestionariosForm"
							property="codigoEvaluadorId" />
						<% } %>
					</div>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend">AUTO-EVALUACI&Oacute;N</legend>
					<div class="cuadroFieldset">
						<span>C&oacute;digo del Evaluado: </span>
						<bean:write name="OCAPCuestionariosForm" property="codigoId" />
						<br />
						<br /> <span>Categor&iacute;a/Especialidad: </span>
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
				</fieldset>

				<html:hidden property="CExpId" />

				<fieldset class="informeConformidad">
					<legend class="tituloLegend">INFORME DE EVALUACI&Oacute;N</legend>
					<div class="cuadroFieldset">
						<% if ( jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION)) {%>
						<p>
							Don / Do&ntilde;a
							<bean:write name="OCAPCuestionariosForm"
								property="DNombreEvaluador" />
							<bean:write name="OCAPCuestionariosForm"
								property="DApellidosEvaluador" />
						</p>
						<% } else { %>
						<p>
							<bean:write name="OCAPCuestionariosForm"
								property="codigoEvaluadorId" />
						</p>
						<% } %>
						<p>Como Evaluador acreditado por la FQS en el proceso de
							Evaluaci&oacute;n del Desempe&ntilde;o de la Competencia (EDC)
							para el reconocimiento de grado de Carrera Profesional de la
							Consejer&iacute;a de Sanidad – SACYL de la Junta de Castilla y
							Le&oacute;n INFORMO:</p>
						<div class="numeracion">
							<p>
								<html:checkbox name="OCAPCuestionariosForm"
									property="BAutoEvaluacion" value="<%=Constantes.SI%>" />
								Que el Profesional Evaluado con c&oacute;digo
								<bean:write name="OCAPCuestionariosForm" property="codigoId" />
								ha realizado la Auto-evaluaci&oacute;n del Desempe&ntilde;o de
								la Competencia seg&uacute;n el Itinerario de Evaluaci&oacute;n
								Competencial que le corresponde para el reconocimiento de grado
								de Carrera Profesional.
							</p>
							<p>
								<html:checkbox name="OCAPCuestionariosForm"
									property="b2Evaluacion" value="<%=Constantes.SI%>" />
								Que se ha realizado el an&aacute;lisis de 2&ordf;
								Evaluaci&oacute;n conforme a los criterios y metodolog&iacute;a
								establecida por la FQS (Fundaci&oacute;n Centro Regional de
								Calidad y Acreditaci&oacute;n Sanitaria), obteniendo los
								siguientes resultados globales:<br />
								<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
									<div class="contenidoTextoProcedimiento">
										<div class="cuadroFinCuestionario">
											<bean:write name="OCAPCuestionariosForm"
												property="AResultados" filter="false" />
										</div>
									</div>
								</logic:equal>
								<logic:notEqual name="informeGenerado"
									value="<%=Constantes.SI%>">
									<html:textarea styleClass="bordeCuadro cuadroResultados"
										name="OCAPCuestionariosForm" property="AResultados" rows="5%"
										cols="95%"
										onkeypress="javascript:return longMaxTextarea2(this);"
										onclick="javascript:return longMaxTextarea2(this);"
										onkeydown="javascript:return longMaxTextarea2(this);"
										onkeyup="javascript:return longMaxTextarea2(this);"></html:textarea>
								</logic:notEqual>
							</p>
							<br />
							<p>
								<html:checkbox name="OCAPCuestionariosForm" property="BAnalisis"
									value="<%=Constantes.SI%>" />
								Que se ha realizado la auditor&iacute;a/an&aacute;lisis de
								registros propuesta conforme a los criterios y
								metodolog&iacute;a establecida por la FQS (Fundaci&oacute;n
								Centro Regional de Calidad y Acreditaci&oacute;n Sanitaria).
							</p>
						</div>
						<br />
						<h3>INFORME DE AN&Aacute;LISIS Y AUDITOR&Iacute;A</h3>
						<p class="analisisAuditoria">
							<html:radio name="OCAPCuestionariosForm" property="BAuditoria"
								value="<%=Constantes.SI%>" disabled="true" />
							An&aacute;lisis 2&ordf; evaluaci&oacute;n
							&nbsp;&nbsp;&nbsp;&nbsp;
							<html:radio name="OCAPCuestionariosForm" property="BAuditoria"
								value="<%=Constantes.NO%>" disabled="true" />
							An&aacute;lisis + Auditor&iacute;a
						</p>

						<div>
							<span>Motivo de la auditor&iacute;a espec&iacute;fica
								elegida: </span>
							<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
								<div class="contenidoTextoProcedimiento">
									<div class="cuadroFinCuestionario">
										<bean:write name="OCAPCuestionariosForm"
											property="AMotivoAuditoria" filter="false" />
									</div>
								</div>
							</logic:equal>
							<logic:notEqual name="informeGenerado" value="<%=Constantes.SI%>">
								<html:textarea styleClass="bordeCuadro"
									name="OCAPCuestionariosForm" property="AMotivoAuditoria"
									rows="5%" cols="95%"
									onkeypress="javascript:return longMaxTextarea(this);"
									onclick="javascript:return longMaxTextarea(this);"
									onkeydown="javascript:return longMaxTextarea(this);"
									onkeyup="javascript:return longMaxTextarea(this);"></html:textarea>
							</logic:notEqual>
						</div>
						<br />
						<br />
						<br />
						<p>
							<span>Descripci&oacute;n del desarrollo de la
								auditor&iacute;a: </span>
						</p>
						<div class="indexar">
							<span class="italic">Lugar de realizaci&oacute;n:</span>
							<div class="contenidoTextoProcedimiento">
								<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
									<div class="cuadroFinCuestionario">
										<bean:write name="OCAPCuestionariosForm"
											property="ALugarRealizacion" filter="false" />
									</div>
								</logic:equal>
								<logic:notEqual name="informeGenerado"
									value="<%=Constantes.SI%>">
									<html:text styleClass="bordeCuadro"
										name="OCAPCuestionariosForm" property="ALugarRealizacion"
										maxlength="100" />
								</logic:notEqual>
							</div>
						</div>
						<br />
						<br />
						<div class="indexar">
							<span class="italic">Fecha:</span>
							<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
								<bean:write name="OCAPCuestionariosForm" property="FAuditoria" />
							</logic:equal>
							<logic:notEqual name="informeGenerado" value="<%=Constantes.SI%>">
								<html:text styleClass="bordeCuadroFecha"
									name="OCAPCuestionariosForm" property="FAuditoria"
									maxlength="10" />
								<a
									href='javascript:show_Calendario("OCAPCuestionariosForm", "FAuditoria", document.forms[0].FAuditoria.value);'><html:img
										styleClass="iconoCalendario" src="imagenes/calendario.gif"
										border="0" alt="Calendario" /></a>
							</logic:notEqual>
						</div>
						<br />
						<br />
						<div class="indexar">
							<span class="italic">Responsable/superior implicado:</span>
							<div class="contenidoTextoProcedimiento">
								<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
									<div class="cuadroFinCuestionario">
										<bean:write name="OCAPCuestionariosForm" property="ASuperior"
											filter="false" />
									</div>
								</logic:equal>
								<logic:notEqual name="informeGenerado"
									value="<%=Constantes.SI%>">
									<html:text styleClass="bordeCuadro"
										name="OCAPCuestionariosForm" property="ASuperior"
										maxlength="1000" />
								</logic:notEqual>
							</div>
						</div>
						<br />
						<br />
						<div class="indexar">
							<span class="italic">Anotaci&oacute;n de los hallazgos de
								auditor&iacute;a:</span>
							<div class="contenidoTextoProcedimiento">
								<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
									<div class="cuadroFinCuestionario">
										<bean:write name="OCAPCuestionariosForm" property="AHallazgos"
											filter="false" />
									</div>
								</logic:equal>
								<logic:notEqual name="informeGenerado"
									value="<%=Constantes.SI%>">
									<html:textarea styleClass="bordeCuadro"
										name="OCAPCuestionariosForm" property="AHallazgos" rows="5%"
										cols="95%"
										onkeypress="javascript:return longMaxTextarea2(this);"
										onclick="javascript:return longMaxTextarea2(this);"
										onkeydown="javascript:return longMaxTextarea2(this);"
										onkeyup="javascript:return longMaxTextarea2(this);"></html:textarea>
								</logic:notEqual>
							</div>
						</div>
						<br />
						<br />
						<br /> <span>Por todo ello: </span> <br />
						<br />
						<p>
							<html:radio name="OCAPCuestionariosForm" property="BCumplimiento"
								value="<%=Constantes.SI%>"
								onclick="javascript:habilitarIncumplimiento('Y');" />
							Existe cumplimiento con respecto a los criterios establecidos en
							el itinerario de evaluaci&oacute;n.
						</p>
						<p>
							<html:radio name="OCAPCuestionariosForm" property="BCumplimiento"
								value="<%=Constantes.NO%>"
								onclick="javascript:habilitarIncumplimiento('N');" />
							Existe incumplimiento con respecto a los criterios establecidos
							en el itinerario de evaluaci&oacute;n.
						</p>
						<div id="incumplimiento"
							style="display: none; visibility: hidden;">
							<br />
							<div class="indexarMas">Categorizaci&oacute;n:</div>
							<br />
							<div class="indexarMas">
								<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
									<html:radio name="OCAPCuestionariosForm"
										property="BCategorizacion"
										value="<%=Constantes.PROPOSICION_EVALUADOR_NO_CONFORMIDAD%>"
										disabled="true" />&nbsp;&nbsp;&nbsp;&nbsp;<span
										class="textoNegrita">No conformidad</span> 
                        &nbsp;&nbsp;(Incumplimiento de las especificaciones que aparecen en el Manual de evaluaci&oacute;n/itinerario a la hora de hacer la autoevaluaci&oacute;n y/o seleccionar e incluir las evidencias y que afecta o condiciona el resultado.) <br />
									<br />
									<html:radio name="OCAPCuestionariosForm"
										property="BCategorizacion"
										value="<%=Constantes.PROPOSICION_EVALUADOR_DESVIACION%>"
										disabled="true" />&nbsp;&nbsp;&nbsp;&nbsp;<span
										class="textoNegrita">Desviaci&oacute;n</span> 
                        &nbsp;&nbsp;(Incumplimiento de las especificaciones que aparecen en el Manual de evaluaci&oacute;n/itinerario a la hora de hacer la autoevaluaci&oacute;n y/o seleccionar e incluir las evidencias y que no afectan o condicionan el resultado. Son fallos aislados.) <br />
									<br />
									<html:radio name="OCAPCuestionariosForm"
										property="BCategorizacion"
										value="<%=Constantes.PROPOSICION_EVALUADOR_OBSERVACION%>"
										disabled="true" />&nbsp;&nbsp;&nbsp;&nbsp;<span
										class="textoNegrita">Observaci&oacute;n</span> 
                        &nbsp;&nbsp;(Hallazgo que no incumple especificaciones o al menos no tiene evidencia objetiva de ello, pero ser&iacute;a un indicativo de malas pr&aacute;cticas. EJ... se evidencia un bajo n&ordm; de env&iacute;os de encuestas o formularios de opini&oacute;n de usuarios que no concuerda con lo pedido aunque las que se han enviado se han enviado bien.))
                  </logic:equal>
								<logic:notEqual name="informeGenerado"
									value="<%=Constantes.SI%>">
									<html:radio name="OCAPCuestionariosForm"
										property="BCategorizacion"
										value="<%=Constantes.PROPOSICION_EVALUADOR_NO_CONFORMIDAD%>" />&nbsp;&nbsp;&nbsp;&nbsp;<span
										class="textoNegrita">No conformidad</span> &nbsp;&nbsp;(Incumplimiento de las especificaciones que aparecen en el Manual de evaluaci&oacute;n/itinerario a la hora de hacer la autoevaluaci&oacute;n y/o seleccionar e incluir las evidencias y que afecta o condiciona el resultado.) <br />
									<html:radio name="OCAPCuestionariosForm"
										property="BCategorizacion"
										value="<%=Constantes.PROPOSICION_EVALUADOR_DESVIACION%>" />&nbsp;&nbsp;&nbsp;&nbsp;<span
										class="textoNegrita">Desviaci&oacute;n</span> &nbsp;&nbsp;(Incumplimiento de las especificaciones que aparecen en el Manual de evaluaci&oacute;n/itinerario a la hora de hacer la autoevaluaci&oacute;n y/o seleccionar e incluir las evidencias y que no afectan o condicionan el resultado. Son fallos aislados.) <br />
									<html:radio name="OCAPCuestionariosForm"
										property="BCategorizacion"
										value="<%=Constantes.PROPOSICION_EVALUADOR_OBSERVACION%>" />&nbsp;&nbsp;&nbsp;&nbsp;<span
										class="textoNegrita">Observaci&oacute;n</span> &nbsp;&nbsp;(Hallazgo que no incumple especificaciones o al menos no tiene evidencia objetiva de ello, pero ser&iacute;a un indicativo de malas pr&aacute;cticas. EJ... se evidencia un bajo n&ordm; de env&iacute;os de encuestas o formularios de opini&oacute;n de usuarios que no concuerda con lo pedido aunque las que se han enviado se han enviado bien.)
                  </logic:notEqual>
							</div>
						</div>
						<br />
						<br />

						<p>
							<span>Recomendaciones: </span>
						</p>
						<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
							<div class="contenidoTextoProcedimiento">
								<div class="cuadroFinCuestionario">
									<bean:write name="OCAPCuestionariosForm"
										property="ARecomendaciones" filter="false" />
								</div>
							</div>
						</logic:equal>
						<logic:notEqual name="informeGenerado" value="<%=Constantes.SI%>">
							<html:textarea styleClass="bordeCuadro"
								name="OCAPCuestionariosForm" property="ARecomendaciones"
								rows="5%" cols="95%"
								onkeypress="javascript:return longMaxTextarea2(this);"
								onclick="javascript:return longMaxTextarea2(this);"
								onkeydown="javascript:return longMaxTextarea2(this);"
								onkeyup="javascript:return longMaxTextarea2(this);"></html:textarea>
						</logic:notEqual>

						<br />
						<br />
						<br />
						<p>
							<span>Conclusiones de la auditor&iacute;a:</span>
						</p>
						<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
							<div class="contenidoTextoProcedimiento">
								<div class="cuadroFinCuestionario">
									<bean:write name="OCAPCuestionariosForm"
										property="AConclusiones" filter="false" />
								</div>
							</div>
						</logic:equal>
						<logic:notEqual name="informeGenerado" value="<%=Constantes.SI%>">
							<html:textarea styleClass="bordeCuadro"
								name="OCAPCuestionariosForm" property="AConclusiones" rows="8%"
								cols="95%"
								onkeypress="javascript:return longMaxTextarea2(this);"
								onclick="javascript:return longMaxTextarea2(this);"
								onkeydown="javascript:return longMaxTextarea2(this);"
								onkeyup="javascript:return longMaxTextarea2(this);"></html:textarea>
						</logic:notEqual>

						<br />
						<br /> <span>Fecha: </span>
						<bean:write name="OCAPCuestionariosForm" property="FEvaluacion" />
					</div>
				</fieldset>

				<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
					<script language="javascript">
            if (document.forms[0].BCumplimiento[1].checked) {
               document.forms[0].BCategorizacion[0].disabled = true;
               document.forms[0].BCategorizacion[1].disabled = true;
               document.forms[0].BCategorizacion[2].disabled = true;
               habilitarIncumplimiento('N');
            }
            document.forms[0].BCumplimiento[0].disabled = true;
            document.forms[0].BCumplimiento[1].disabled = true;
            document.forms[0].BAutoEvaluacion.disabled = true;
            document.forms[0].b2Evaluacion.disabled = true;
            document.forms[0].BAnalisis.disabled = true;
         </script>
				</logic:equal>

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
						<% if (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC) && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION) && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL) && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE)) {%>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverEvaluacion();"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Volver" /> <span> Volver </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<% } else if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE)) {%>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviar('OCAPCuestionarios.do?accion=generarInformeCTE&cCompfqsIdS=<%=session.getAttribute("cCompfqsIdS")%>');">
									<img src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Informe CTE" /> <span>
										Volver </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<% }else{ %>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverListado();"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Volver" /> <span> Volver </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<% } %>
						<% if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CTE)) {%>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviar('OCAPCuestionarios.do?accion=generarInformeCTE&cCompfqsIdS=<%=session.getAttribute("cCompfqsIdS")%>');">
									<img src="imagenes/imagenes_ocap/action_forward.gif"
									class="colocaImgPrint" alt="Informe CTE" /> <span>
										Informe CTE </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<% }
            if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC) ||jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE)) {%>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:verItinerario();"> <img
									src="imagenes/imagenes_ocap/action_forward.gif"
									class="colocaImgPrint" alt="Ver itinerario" /> <span>
										Ver itinerario </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<%}%>
						<%-- if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION)) {%>
               <div class="botonAccion" >
                  <span class="izqBoton"></span>
                  <span class="cenBoton">
                  <a href="javascript:generarInforme2PDF();">
                     <img src="imagenes/imagenes_ocap/action_forward.gif" class="colocaImgPrint" alt="Generar informe en PDF" />
                     <span> Generar PDF </span>
                  </a>
                  </span>
                  <span class="derBoton"></span>
               </div>
            <%}--%>
					</logic:equal>

					<% if (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)) {%>
					<div class="botonAccion">
						<div class="flotaDerBotones">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:window.print();"> <img
									src="imagenes/imagenes_ocap/impresion.gif" alt="Imprimir"
									class="colocaImgPrint" /> <span> Imprimir </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</div>
					<% }%>


				</div>
			</html:form>
		</div>
	</div>
	<!-- /fin de ContenidoMC -->
</div>
<!-- /Fin de Contenido -->

<div class="limpiadora"></div>
