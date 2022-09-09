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
      if (validarInformeCE()) {
         if (document.forms[0].BDecisionCE[1].checked)
            document.forms[0].ADiscrepancias.value=document.forms[0].discrepancias.value;
                     
         enviar('OCAPCuestionarios.do?accion=guardarInformeCE');
      }
   }
}

function validarInformeCE(){
   if (!document.forms[0].BDecisionCE[0].checked && !document.forms[0].BDecisionCE [1].checked) {
      alert('Debe seleccionar una opci\u00F3n en el punto 4.');
      return false;
   }

   if (document.forms[0].BDecisionCE[1].checked && document.forms[0].discrepancias.value==''){
      alert('Debe indicar en qu\u00E9 aspectos discrepa de la evaluaci\u00F3n.');
      return false;
   }

   if (document.forms[0].AEspecificaciones.value =='') {
      alert('Debe indicar las especificaciones que realiza para el informe.');
      return false;
   }
   
   return true;
}

function habilitarDiscrepancias(){
      document.getElementById('nuevaRevision').style.display='';
      document.getElementById('nuevaRevision').style.visibility='visible';
      document.forms[0].discrepancias.value='';
}

function deshabilitarDiscrepancias(){
      document.getElementById('nuevaRevision').style.display='none';
      document.getElementById('nuevaRevision').style.visibility='hidden';
      document.forms[0].discrepancias.value='';
}

function deshabilitar(){
   if (document.forms[0].BDecisionCE[1].checked){
      habilitarDiscrepancias();
      document.forms[0].discrepancias.value=document.forms[0].ADiscrepancias.value;
      document.forms[0].discrepancias.disabled=true;
   }
   
   document.forms[0].BDecisionCE[0].disabled = true;
   document.forms[0].BDecisionCE[1].disabled = true;
}

function volverEvaluacion(){
   enviar('OCAPCuestionarios.do?accion=irListar&cCompfqsIdS=<%=request.getAttribute("cCompfqsIdS")%>&expId='+document.forms[0].CExpId.value);
}

function volverListado(){
   enviar('OCAPEvaluadores.do?accion=listarEvaluados&cCompfqsIdS=<%=session.getAttribute("cCompfqsIdS")%>&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
}

function informeCTE(){
   enviar('OCAPCuestionarios.do?accion=generarInformeCTE&expId='+document.forms[0].CExpId.value);
}

function informeIFQS(){
   enviar('OCAPCuestionarios.do?accion=irInformeCC&expId='+document.forms[0].CExpId.value);
}

function volverListadoPorCTE(){
   enviar('OCAPComponentesCtes.do?accion=listarEvaluados&cCteIdS=<%=session.getAttribute("cCteIdS")%>&nombre=<%=session.getAttribute("nombreCTE")%>');
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
				<h2 class="tituloContenido">INFORME DE PROPUESTA DE
					CERTIFICACI&Oacute;N DE LA COMISI&Oacute;N DE EVALUACI&Oacute;N</h2>
				<% if (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)) {%>
				<a href="javascript:window.print();"><img
					src="imagenes/imagenes_ocap/imprimir.gif" alt="Imprimir"
					class="flotaIzq" /></a>
				<% }%>
				<!--<h3 class="subTituloContenido"> Evaluaci&oacute;n/Pruebas de buena pr&aacute;ctica asignados a cada &Aacute;REA </h3>-->
				<h3 class="subTituloContenido">&nbsp;</h3>
				<div class="lineaBajaM"></div>
				<div class="espaciador"></div>

				<fieldset class="informeConformidad">
					<legend class="tituloLegend"> INFORME DE PROPUESTA DE
						CERTIFICACI&Oacute;N DE LA COMISI&Oacute;N DE EVALUACI&Oacute;N </legend>
					<html:hidden property="CExpId" />
					<div class="cuadroFieldset informesConformidad">
						<p>
							La Comisi&oacute;n de Evaluaci&oacute;n creada por la
							Fundaci&oacute;n Centro Regional de Calidad y Acreditaci&oacute;n
							Sanitaria para el proceso de Evaluaci&oacute;n del
							Desempe&ntilde;o de la Competencia (EDC) para el reconocimiento
							de grado de Carrera Profesional de la Consejer&iacute;a de
							Sanidad – SACYL de la Junta de Castilla y Le&oacute;n, y tras la
							reuni&oacute;n celebrada el
							<html:text styleClass="fechaInforme" name="OCAPCuestionariosForm"
								property="FReunion" maxlength="10" />
							INFORMA:
						</p>
						<p>
							<span class="textoNegrita">1º/</span> Que el Profesional Evaluado
							con c&oacute;digo <span class="textoNegrita"><bean:write
									name="OCAPCuestionariosForm" property="codigoId" /></span> ha
							realizado la Evaluaci&oacute;n del Desempe&ntilde;o de la
							Competencia seg&uacute;n el Itinerario de Evaluaci&oacute;n
							Competencial que le corresponde para el reconocimiento de grado
							de Carrera Profesional, obteniendo <span class="nota"><bean:write
									name="OCAPCuestionariosForm" property="NCreditosItinerario" /></span>
							cr&eacute;ditos.
						</p>

						<p>
							<span class="textoNegrita">2º/</span> Que el an&aacute;lisis de
							Evaluaci&oacute;n Externa realizado por el Evaluador <span
								class="textoNegrita"><bean:write
									name="OCAPCuestionariosForm" property="codigoEvaluadorId" /></span>
							seg&uacute;n los criterios y metodolog&iacute;a acreditada por la
							FQS (Fundaci&oacute;n Centro Regional de Calidad y
							Acreditaci&oacute;n Sanitaria) ha dado como resultado <span
								class="nota"><bean:write name="OCAPCuestionariosForm"
									property="NCreditosEvaluados" /></span> cr&eacute;ditos.
						</p>

						<p>
							<span class="textoNegrita">3º/</span> Que el informe del C.T.E.
							<bean:write name="OCAPCuestionariosForm" property="DNombreCTE" />
							ha dado como resultado:
						</p>
						<p>
							<logic:equal name="OCAPCuestionariosForm"
								property="BConformidadCTE" value="<%=Constantes.SI%>">
								<span><%=Constantes.CTE_CONFORMIDAD%></span>
							</logic:equal>
							<logic:equal name="OCAPCuestionariosForm"
								property="BConformidadCTE" value="<%=Constantes.NO%>">
								<span><%=Constantes.CTE_INCONFORMIDAD%></span>
							</logic:equal>
						</p>
						<p>
							<span class="textoNegrita">4º/</span> Por lo expuesto, esta CE:
						</p>
						<ul>
							<li>
								<p>
									<html:radio name="OCAPCuestionariosForm" property="BDecisionCE"
										value="<%=Constantes.CTE_DECISION_CE_C%>"
										onclick="javascript:deshabilitarDiscrepancias();" />
									<span class="textoNegrita">RATIFICA</span> el resultado de esta
									evaluaci&oacute;n, y <span class="textoNegrita">PROPONE:</span>
								</p>
							</li>
							<li>
								<p class="respuestaFormulario">
									La <span class="textoNegrita">CERTIFICACION</span> del Evaluado
									habiendo alcanzado <span class="textoNegrita"><bean:write
											name="OCAPCuestionariosForm" property="NCreditosEvaluados" /></span>
									cr&eacute;ditos, siendo <span class="textoNegrita"><bean:write
											name="OCAPCuestionariosForm" property="NCreditosNecesarios" /></span>
									cr&eacute;ditos los necesarios para el reconocimiento del
									<bean:write name="OCAPCuestionariosForm" property="DGrado_des" />
									de Carrera Profesional para su categor&iacute;a.
								</p>
							</li>
							<li>
								<p>
									<html:radio name="OCAPCuestionariosForm" property="BDecisionCE"
										value="<%=Constantes.CTE_DECISION_CE_N%>"
										onclick="javascript:habilitarDiscrepancias();" />
									Solicita la revisi&oacute;n de la evaluaci&oacute;n realizada
									al encontrar discrepancias en los siguientes aspectos:
								</p> <html:hidden name="OCAPCuestionariosForm"
									property="ADiscrepancias" />
								<div id="nuevaRevision"
									style="display: none; visibility: hidden;">
									<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
										<div class="bordeCuadro ajusteBorde1">
											<bean:write name="OCAPCuestionariosForm"
												property="ADiscrepancias" />
										</div>
										<input type="hidden" name="discrepancias" />
									</logic:equal>
									<logic:notEqual name="informeGenerado"
										value="<%=Constantes.SI%>">
										<textarea name="discrepancias" rows="3%" cols="95%"
											onkeypress="javascript:return longMaxTextarea(this);"
											onclick="javascript:return longMaxTextarea(this);"
											onkeydown="javascript:return longMaxTextarea(this);"
											onkeyup="javascript:return longMaxTextarea(this);"></textarea>
									</logic:notEqual>
								</div>
							</li>
						</ul>
						<br />
						<p>
							<span class="textoNegrita">5º/</span> Asimismo se realizan las
							siguientes especificaciones:
						</p>
						<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
							<div class="bordeCuadro ajusteBorde2">
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
								href="javascript:informeCTE();"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Cancelar" /> <span>
										Cancelar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</logic:notEqual>
					<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
						<% if (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC) && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION) && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR) && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE)) {%>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverEvaluacion();"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Volver" /> <span> Volver </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<% } else if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE)) { %>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverListadoPorCTE();"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Volver" /> <span> Volver </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<%}else{%>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverListado();"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Volver" /> <span> Volver </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<%}%>
						<% if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)) {%>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:informeCTE();"> <img
									src="imagenes/imagenes_ocap/action_forward.gif"
									class="colocaImgPrint" alt="Ver informe CTE" /> <span>
										Ver informe CTE </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<%}%>
						<% if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION)){%>
						<logic:equal name="OCAPCuestionariosForm" property="FInformeCC"
							value="">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:informeIFQS();"> <img
										src="imagenes/imagenes_ocap/action_forward.gif"
										class="colocaImgPrint" alt="Ver informe IFQS" /> <span>
											Ver informe IFQS </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</logic:equal>
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