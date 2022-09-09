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

<script language="javascript">
function guardarInforme() {

   if (document.forms[0].AEspecificaciones.value!='')
      enviar('OCAPCuestionarios.do?accion=guardarInforme');
   else 
      alert('Debe indicar las especificaciones que realiza para el informe.');
}



function verProposicion(num, bVer){
   if (bVer=='Y') {
      document.getElementById('proposicion'+num).style.display='';
      document.getElementById('proposicion'+num).style.visibility='visible';
   } else {
      document.getElementById('proposicion'+num).style.display='none';
      document.getElementById('proposicion'+num).style.visibility='hidden';
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
	<div class="contenidoTextoAutoevaluacion contenidoTextoProcedimiento">
		<div class="evaluacionEvaluador evaluacionIEC">
			<html:form action="/OCAPCuestionarios.do">
				<h2 class="tituloContenido">INFORME DE EVALUACI&Oacute;N</h2>
				<% if (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)) {%>
				<a href="javascript:window.print();"><img
					src="imagenes/imagenes_ocap/imprimir.gif" alt="Imprimir"
					class="flotaIzq" /></a>
				<% }%>
				<!--<h3 class="subTituloContenido"> Evaluaci&oacute;n/Pruebas de buena pr&aacute;ctica asignados a cada &Aacute;REA </h3>-->
				<h3 class="subTituloContenido">&nbsp;</h3>
				<div class="lineaBajaM"></div>

				<div class="espaciador"></div>


				<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
					<div class="botonesPagina">
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
					</div>
				</logic:equal>
				<logic:notEqual name="informeGenerado" value="<%=Constantes.SI%>">
					<div class="botonesPagina">
						<% if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CTE)) {%>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverEvaluacion();"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Volver" /> <span> Volver </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviar('OCAPCuestionarios.do?accion=generarInformeCTE&cCompfqsIdS=<%=session.getAttribute("cCompfqsIdS")%>');">
									<img src="imagenes/imagenes_ocap/action_forward.gif"
									class="colocaImgPrint" alt="Informe CTE" /> <span>
										Informe CTE </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>

						<% }%>
					</div>
				</logic:notEqual>

				<fieldset>
					<legend class="tituloLegend">EVALUADOR</legend>
					<div class="cuadroFieldset">
						<% if ( jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL)) {%>
						<span>Nombre y Apellidos: </span>
						<bean:write name="OCAPCuestionariosForm"
							property="DNombreEvaluador" />
						<bean:write name="OCAPCuestionariosForm"
							property="DApellidosEvaluador" />
						<br />
						<br /> <span>DNI: </span>
						<bean:write name="OCAPCuestionariosForm" property="CDniEvaluador" />
						<br />
						<br /> <span>Fecha de Inicio Evaluaci&oacute;n: </span>
						<bean:write name="OCAPCuestionariosForm" property="FEvaluacion" />
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
					<% if ( Constantes.OCAP_DIRECCION.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_CTE.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol()) ) {%>
						<span>Nombre y apellidos: </span>
						<bean:write name="OCAPCuestionariosForm" property="nombreApellidos" />
						<br />
						<br />
						<span>DNI: </span>
						<bean:write name="OCAPCuestionariosForm" property="dni" />
						<br />
						<br />
					<% }  %>
						<span>C&oacute;digo del Evaluado: </span>
						<bean:write name="OCAPCuestionariosForm" property="codigoId" />
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
				</fieldset>

				<bean:size id="numAreas" name="OCAPCuestionariosForm"
					property="listadoAreas" />
				<logic:notEqual name="numAreas" value="0">
					<logic:iterate id="listaAreas" name="OCAPCuestionariosForm"
						property="listadoAreas" type="OCAPAreasItinerariosOT">
						<div class="areasAuto">
							<div class="tituloAreasAuto">
								<span class="titulo"><bean:write name="listaAreas"
										property="DNombreLargo" /></span>
								<%-- <a href="OCAPCuestionarios.do?accion=irInformacionArea&cAreaId=<bean:write name="listaAreas" property="CAreaId" />"><img src="imagenes/imagenes_ocap/icono_informacion.gif" alt="Informacion" class="colocaImgInfo" /></a> --%>
							</div>
							<div class="datosAreasAuto">
								<bean:size id="numCuestionarios" name="listaAreas"
									property="listaCuestionarios" />
								<logic:notEqual name="numCuestionarios" value="0">

									<table width="98%">
										<tr>
											<th width="3%"></th>
											<th width="57%">Prueba de Buena Pr&aacute;ctica</th>
											<th width="18%">Cr. Auto-evaluaci&oacute;n</th>
											<th width="18%">Cr. Evaluador</th>
											<!-- <th width="14%">Observaciones</th> -->
										</tr>
										<logic:iterate id="lCuestionarios" name="listaAreas"
											property="listaCuestionarios" type="OCAPCuestionariosOT">
											<tr>
												<logic:equal name="lCuestionarios" property="BContestado"
													value="<%=Constantes.SI%>">
													<td><img
														src="imagenes/imagenes_ocap/icono_editar_check.gif"
														alt="icono editar" /></td>
												</logic:equal>
												<logic:equal name="lCuestionarios" property="BContestado"
													value="<%=Constantes.NO%>">
													<td><img src="imagenes/imagenes_ocap/icono_editar.gif"
														alt="icono editar" /></td>
												</logic:equal>
												<td class="enlace"><span><bean:write
															name="listaAreas" property="DNombre" /> <bean:write
															name="lCuestionarios" property="DNombre" filter="false" />
														- <bean:write name="lCuestionarios"
															property="DNombreLargo" filter="false" /></span></td>
												<td class="centrar"><logic:equal name="lCuestionarios"
														property="BContestado" value="<%=Constantes.SI%>">
														<logic:notEqual name="lCuestionarios"
															property="CEvidenciaId" value="0">
															<span class="textoNegrita"> <bean:write
																	name="lCuestionarios" property="NCreditosConseguidos" /></span>
														</logic:notEqual>
														<logic:equal name="lCuestionarios" property="CEvidenciaId"
															value="0">
															<span class="textoNegrita"><bean:write
																	name="lCuestionarios" property="NCreditosConseguidos" /></span>
														</logic:equal>
													</logic:equal> <logic:equal name="lCuestionarios" property="BContestado"
														value="<%=Constantes.NO%>">
														<span class="textoNegrita"><bean:write
																name="lCuestionarios" property="NCreditosConseguidos" /></span>
													</logic:equal></td>
												<td class="centrar"><span class="textoNegrita"><bean:write
															name="lCuestionarios" property="NCreditosEvaluados" /></span></td>
											</tr>
										</logic:iterate>

									</table>

								</logic:notEqual>
							</div>
						</div>
					</logic:iterate>
					<div class="areasAuto">
						<div class="datosAreasAuto">
							<table width="98%">
								<tr>
									<td width="3%">&nbsp;</td>
									<td width="57%" class="derecha"><span class="textoNegrita">CR&Eacute;DITOS
											TOTALES:</span></td>
									<td class="centrar" width="18%"><bean:write
											name="OCAPCuestionariosForm" property="NCreditosItinerario" />
									</td>
									<td class="centrar" width="18%"><bean:write
											name="OCAPCuestionariosForm" property="NCreditosEvaluados" />
									</td>
								</tr>
							</table>
						</div>
					</div>
				</logic:notEqual>
				<html:hidden property="CExpId" />
      &nbsp;
      <% int nCuestionarios = 0; %>
				<logic:iterate id="listaAreas" name="OCAPCuestionariosForm"
					property="listadoAreas" type="OCAPAreasItinerariosOT">
					<logic:iterate id="lCuestionarios" name="listaAreas"
						property="listaCuestionarios" type="OCAPCuestionariosOT">
						<h4>
							<bean:write name="listaAreas" property="DNombre" />
							<bean:write name="lCuestionarios" property="DNombre"
								filter="false" />
							-
							<bean:write name="lCuestionarios" property="DNombreLargo"
								filter="false" />
						</h4>
						<fieldset>
							<div class="cuadroProcedimiento">
								<div class="espaciador"></div>
								<ul class="listadoResumen">
									<li><span>Ponderaci&oacute;n m&aacute;xima posible:
									</span><span class="ponderacionMaxima"><bean:write
												name="lCuestionarios" property="NCreditos" /></span></li>
									<li><span>Ponderaci&oacute;n obtenida en la
											auto-evaluaci&oacute;n:</span> <span class="ponderacionObtenida"><bean:write
												name="lCuestionarios" property="NCreditosConseguidos" /></span></li>
									<li><span>Evidencia aportada:</span> <bean:size
											id="tamanoListaDocs" name="lCuestionarios"
											property="listaDocumentos" /> <logic:equal
											name="tamanoListaDocs" value="0">
                           No hay evidencias.
                        </logic:equal> <logic:notEqual name="tamanoListaDocs"
											value="0">
											<logic:iterate id="lDocumentos" name="lCuestionarios"
												property="listaDocumentos" type="OCAPDocumentosOT">
												<span>Evidencia documental n&ordm; <bean:write
														name="lDocumentos" property="DTitulo" /></span>&nbsp;&nbsp;
                           </logic:iterate>
										</logic:notEqual></li>
									<li><span>Resultado de la evaluaci&oacute;n. Tras
											el an&aacute;lisis estoy:</span>
										<ul>
											<%++nCuestionarios;%>
											<logic:equal name="lCuestionarios" property="BAcuerdo"
												value="<%=Constantes.SI%>">
												<li><span>De acuerdo con la
														auto-evaluaci&oacute;n de este apartado</span> <input
													type="radio" class="acuerdo"
													id="BAcuerdo<%=nCuestionarios%>" value="<%=Constantes.NO%>"
													disabled="disabled" checked="checked" /></li>
												<li><span>En desacuerdo con la
														auto-evaluaci&oacute;n de este apartado</span> <input
													type="radio" class="desacuerdo"
													id="BAcuerdo<%=nCuestionarios%>" value="<%=Constantes.SI%>"
													disabled="disabled" /></li>
											</logic:equal>
											<logic:equal name="lCuestionarios" property="BAcuerdo"
												value="<%=Constantes.NO%>">
												<li><span>De acuerdo con la
														auto-evaluaci&oacute;n de este apartado</span> <input
													type="radio" class="acuerdo"
													id="BAcuerdo<%=nCuestionarios%>" value="<%=Constantes.NO%>"
													disabled="disabled" /></li>
												<li><span>En desacuerdo con la
														auto-evaluaci&oacute;n de este apartado</span> <input
													type="radio" class="desacuerdo"
													id="BAcuerdo<%=nCuestionarios%>" value="<%=Constantes.SI%>"
													disabled="disabled" checked="checked" />
													<div>
														<span>Por ello propongo:</span><br />
														<ul>
															<li><span>Modificar la ponderaci&oacute;n de
																	cr&eacute;ditos de este apartado</span> <input type="radio"
																class="modificarPonderacion"
																id="BProposicion<%=nCuestionarios%>" value="M"
																disabled="disabled" checked="checked" /></li>

														</ul>
													</div></li>
											</logic:equal>
											<logic:notEqual name="lCuestionarios" property="BAcuerdo"
												value="<%=Constantes.SI%>">
												<logic:notEqual name="lCuestionarios" property="BAcuerdo"
													value="<%=Constantes.NO%>">
													<li><span>De acuerdo con la
															auto-evaluaci&oacute;n de este apartado</span> <input
														type="radio" class="acuerdo"
														id="BAcuerdo<%=nCuestionarios%>"
														value="<%=Constantes.NO%>" disabled="disabled" /></li>
													<li><span>En desacuerdo con la
															auto-evaluaci&oacute;n de este apartado</span> <input
														type="radio" class="desacuerdo"
														id="BAcuerdo<%=nCuestionarios%>"
														value="<%=Constantes.SI%>" disabled="disabled" /></li>
												</logic:notEqual>
											</logic:notEqual>
										</ul></li>
									<li><span>Especificar raz&oacute;n:</span><br />
										<div class="cajaTextoAuto">
											<bean:write name="lCuestionarios" property="ARazon" />
											&nbsp;
										</div></li>
									<li><span>Comentarios, si procede:</span><br />
										<div class="cajaTextoAuto">
											<bean:write name="lCuestionarios" property="AComentarios" />
											&nbsp;
										</div></li>
								</ul>
								<div>
									<span class="textoNegrita">PONDERACI&Oacute;N FINAL
										OTORGADA POR EL EVALUADOR: </span> <span class="nota"><bean:write
											name="lCuestionarios" property="NCreditosEvaluados" /></span>
								</div>
							</div>
						</fieldset>
					</logic:iterate>
				</logic:iterate>
				<script language="javascript">
               //deshabilitarTodos('<%=nCuestionarios%>');
            </script>
				<html:hidden property="DNombreEvaluador" />
				<html:hidden property="codigoId" />
				<html:hidden property="NCreditosItinerario" />
				<html:hidden property="NCreditosEvaluados" />
				<html:hidden property="NCreditosNecesarios" />
				<html:hidden property="CItinerarioId" />
				<!--<html:hidden property="AEspecificaciones" />      -->
				<fieldset class="informeConformidad">
					<legend class="tituloLegend">INFORME DE EVALUACI&Oacute;N</legend>
					<div class="cuadroFieldset">
						<% if ( jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL)) {%>
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
						<p>Como Evaluador en el proceso de Evaluaci&oacute;n del
							Desempe&ntilde;o de la Competencia para el reconocimiento de
							grado de Carrera Profesional de la Consejer&iacute;a de Sanidad
							� SACYL de la Junta de Castilla y Le&oacute;n INFORMO:</p>
						<p>
							<span class="textoNegrita">1&ordm;/</span> Que el Profesional
							Evaluado con c&oacute;digo
							<bean:write name="OCAPCuestionariosForm" property="codigoId" />
							ha realizado la Auto-evaluaci&oacute;n del Desempe&ntilde;o de la
							Competencia seg&uacute;n el Itinerario de Evaluaci&oacute;n
							Competencial que le corresponde para el reconocimiento de grado
							de Carrera Profesional.
						</p>
						<p>
							<span class="textoNegrita">2&ordm;/</span> Que se ha realizado el
							an&aacute;lisis de Evaluaci&oacute;n obteniendo los siguientes
							resultados globales:
						</p>
						<table class="informeTabla" width="95%" cellpadding="2"
							cellspacing="2">
							<tr>
								<td width="70%"><span>Cr&eacute;ditos obtenidos en
										la autoevaluaci&oacute;n: </span></td>
								<td width="30%"><span><bean:write
											name="OCAPCuestionariosForm" property="NCreditosItinerario" /></span></td>
							</tr>
							<tr>
								<td width="70%"><span>Cr&eacute;ditos obtenidos en
										el an&aacute;lisis de la autoevaluaci&oacute;n: </span></td>
								<td width="30%"><span><bean:write
											name="OCAPCuestionariosForm" property="NCreditosEvaluados" /></span></td>
							</tr>
						</table>
						<br />
						<p>
							<span class="textoNegrita">3&ordm;/</span> Finalmente realizo las
							siguientes especificaciones:
						</p>
						<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
							<div class="bordeCuadro">
								<bean:write name="OCAPCuestionariosForm"
									property="AEspecificaciones" />
							</div>
						</logic:equal>
						<logic:notEqual name="informeGenerado" value="<%=Constantes.SI%>">
							<div class="bordeCuadro">
								<html:textarea name="OCAPCuestionariosForm"
									property="AEspecificaciones" rows="5%" cols="97%"
									onkeypress="javascript:return longMaxTextarea(this);"
									onclick="javascript:return longMaxTextarea(this);"
									onkeydown="javascript:return longMaxTextarea(this);"
									onkeyup="javascript:return longMaxTextarea(this);"></html:textarea>
							</div>
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
						<% if (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CTE)) {%>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:guardarInforme();"> <img
									src="imagenes/imagenes_ocap/action_forward.gif"
									class="colocaImgPrint" alt="Guardar Informe Evaluador" /> <span>
										Guardar Informe </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<%}%>
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

					</logic:equal>
				</div>
			</html:form>
		</div>
	</div>
	<!-- /fin de ContenidoMC -->
</div>
<!-- /Fin de Contenido -->

<div class="limpiadora"></div>