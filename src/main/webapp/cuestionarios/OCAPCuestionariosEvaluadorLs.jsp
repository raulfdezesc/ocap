<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.areasItinerarios.OCAPAreasItinerariosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.documentos.OCAPDocumentosOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<% int contadorPruebasNoEvaluadas = 0; %>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript">
function finalizarCA(permiso) {
   if (permiso=='<%=Constantes.SI%>') {
      if (confirm('Si finaliza el proceso no podr\u00E1 contestar ning\u00FAn formulario m\u00E1s.\n\n\Aseg\u00FArese de que ha adjuntado el fichero de evidencias y recuerde que una vez finalizada su autoevaluación se cierra el proceso impidiendo hacer modificaciones.\n\n\u00BFDesea finalizar?'))
         enviar('OCAPCuestionarios.do?accion=finalizarProceso');
   } else 
      alert('No puede finalizar el proceso hasta que no haya contestado y finalizado al menos un formulario de cada una de las \u00E1reas.');
}

function volverListado() {
   <%if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CTE)){%> 
      enviar('OCAPEvaluadores.do?accion=listarEvaluadosGerenciaCTE&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=session.getAttribute("opcion")%>&codigo=<%=session.getAttribute("codigo")%>&cte=<%=session.getAttribute("cte")%>');
   <%} else if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_FQS_ADTVO)){%> 
      enviar('OCAPNuevaSolicitud.do?accion=irDetalleFqs&CExp_id='+document.forms[0].CExpId.value+'&fase=FASE_CA_ESCANEADA');
   <%} else if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_FQS_GEST)){%> 
      enviar('OCAPDudasTutores.do?accion=detalleDuda&cDudaIdS=<%=session.getAttribute("cDudaId")%>');
   <%}else{%>
      enviar('OCAPEvaluadores.do?accion=listarEvaluados&cCompfqsIdS=<%=session.getAttribute("cCompfqsIdS")%>&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
   <%}%>
}

function generarInforme(contPruebasNoEvaluadas) {
   if (contPruebasNoEvaluadas > 0)
      alert('Debe evaluar todas las pruebas antes de generar el informe.');
   else
      enviar('OCAPCuestionarios.do?accion=generarInforme&cCompfqsIdS=<%=session.getAttribute("cCompfqsIdS")%>');
}

function generarInforme2(contPruebasNoEvaluadas) {
   if (contPruebasNoEvaluadas > 0)
      alert('Debe analizar todas las pruebas antes de generar el informe.');
   else if(parseInt(document.forms[0].contadorAuditorias.value) > 0 && document.forms[0].FAuditoriaPropuesta.value == '')
      alert('Debe solicitar la auditor\u00EDa antes de generar el informe.');
   else
      enviar('OCAPCuestionarios.do?accion=generarInforme2&cCompfqsIdS=<%=session.getAttribute("cCompfqsIdS")%>');
}

function solicitarAuditorias(contPruebasNoEvaluadas) {
   if (contPruebasNoEvaluadas > 0)
      alert('Debe analizar todas las pruebas antes de solicitar la auditor\u00EDa.');
   else
      enviar('OCAPCuestionarios.do?accion=generarInformeAuditoria&cCompfqsIdS=<%=session.getAttribute("cCompfqsIdS")%>');
}

function asignarMaxCreditos(cuestionario, creditos) {
   if(cuestionario != null && cuestionario != '' && creditos != null && creditos != '') {
      document.forms[0].CCuestionarioId.value = cuestionario;
      document.forms[0].NCreditosEvaluados.value = creditos;
      enviar('OCAPCuestionarios.do?accion=asignarMaxCreditos');
   }
   
}   
   
function obtenerDocumentoEvidencia(expediente) {

        document.forms[0].CExpId.value = expediente;
        enviar('OCAPDocumentos.do?accion=abrirDocumentoEvidencia');

}
</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoAutoevaluacion">
		<div class="evaluacionEvaluador">
			<html:form action="/OCAPCuestionarios.do">
				<h2 class="tituloContenido">PROCESO DE LA AUTOEVALUACI&Oacute;N
				</h2>
				<h3 class="subTituloContenido">Evaluaci&oacute;n/Pruebas de
					buena pr&aacute;ctica asignados a cada &Aacute;REA</h3>
				<div class="lineaBajaM"></div>
				<div class="espaciador"></div>
				<bean:size id="numAreas" name="OCAPCuestionariosForm"
					property="listadoAreas" />
				<logic:notEqual name="numAreas" value="0">
					<logic:iterate id="listaAreas" name="OCAPCuestionariosForm"
						property="listadoAreas" type="OCAPAreasItinerariosOT">
						<div class="areasAuto">
							<div class="tituloAreasAuto">
								<span class="titulo"><bean:write name="listaAreas"
										property="DNombreLargo" /></span>
								<%-- <a href="OCAPCuestionarios.do?accion=irInformacionArea&cAreaId=<bean:write name="listaAreas" property="CAreaId"/>"><img src="imagenes/imagenes_ocap/icono_informacion.gif" alt="Informacion" class="colocaImgInfo"/></a> --%>
							</div>
							<div class="datosAreasAuto">
								<bean:size id="numCuestionarios" name="listaAreas"
									property="listaCuestionarios" />
								<logic:notEqual name="numCuestionarios" value="0">
									<table boder="1">
										<tr>
											<th width="53%" colspan="3">Nombre de la Prueba</th>
											<th width="9%">Cr&eacute;ditos Fin Autoevaluaci&oacute;n</th>
											<th width="9%">Cr&eacute;ditos M&aacute;ximos</th>
											<%if (!(Constantes.SI.equals(session.getAttribute("bSegundaEvaluacion")) && Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol()) ))  { %>
											<th width="9%">Cr&eacute;ditos Asignados</th>
											<%}%>
											<th width="11%"></th>
										</tr>
										<logic:iterate id="lCuestionarios" name="listaAreas"
											property="listaCuestionarios" type="OCAPCuestionariosOT">
											<tr>

												<logic:equal name="lCuestionarios" property="BContestado"
													value="<%=Constantes.SI%>">
													<td class="imagen"><img
														src="imagenes/imagenes_ocap/icono_editar_check.gif"
														alt="cuestionario finalizado" /></td>
												</logic:equal>
												<logic:equal name="lCuestionarios" property="BContestado"
													value="<%=Constantes.NO%>">
													<logic:equal name="lCuestionarios"
														property="BParcialmenteContestado"
														value="<%=Constantes.SI%>">
														<td class="imagen"><img
															src="imagenes/imagenes_ocap/icono_editar_check2.gif"
															alt="cuestionario parcialmente finalizado" /></td>
													</logic:equal>
													<logic:equal name="lCuestionarios"
														property="BParcialmenteContestado"
														value="<%=Constantes.NO%>">
														<td class="imagen"><img
															src="imagenes/imagenes_ocap/icono_editar.gif"
															alt="cuestionario sin finalizar" /></td>
													</logic:equal>
												</logic:equal>
												<bean:size id="tamanoListaDocs" name="lCuestionarios"
													property="listaDocumentos" />
												<logic:equal name="tamanoListaDocs" value="0">
													<td class="imagen">&nbsp;</td>
												</logic:equal>
												<logic:notEqual name="tamanoListaDocs" value="0">
													<td width="5%" class="imagen"><logic:iterate
															id="lDocumentos" name="lCuestionarios"
															property="listaDocumentos" type="OCAPDocumentosOT">
															<a
																href="OCAPDocumentos.do?accion=abrirDocumento&expId=<bean:write name="OCAPCuestionariosForm" property="CExpId"/>&documentoId=<bean:write name="lDocumentos" property="CDocumento_id"/>"><img
																src="imagenes/imagenes_ocap/icono_registrar_aceptar.gif"
																alt="ver datos" /></a>
														</logic:iterate></td>
												</logic:notEqual>

												<td class="enlace"><a
													href="OCAPCuestionarios.do?accion=verCuestionario&idCuestionario=<bean:write name="lCuestionarios" property="CCuestionarioId"/>&nRepeticiones=<bean:write name="lCuestionarios" property="NRepeticiones"/>&nSubCuest=<bean:write name="lCuestionarios" property="NSubCuestionarios"/>">
														<div class="numeracion">
															<bean:write name="listaAreas" property="DNombre" />
															<bean:write name="lCuestionarios" property="DNombre"
																filter="false" />
															&nbsp;-&nbsp;
														</div>
														<div class="enunciado">
															<bean:write name="lCuestionarios" property="DNombreLargo"
																filter="false" />
														</div>
												</a></td>
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
															name="lCuestionarios" property="NCreditos" /></span></td>
												<%if (!(Constantes.SI.equals(session.getAttribute("bSegundaEvaluacion")) && Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol()) ))  { %>
												<td class="centrar"><span class="textoNegrita"><bean:write
															name="lCuestionarios" property="NCreditosEvaluados" /></span></td>
												<%}%>
												<td>
													<% if (Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_DIRECCION.equals(jcylUsuario.getUser().getRol())) { %>
													<%if (!Constantes.SI.equals(session.getAttribute("bSegundaEvaluacion"))) { %>
													<logic:equal name="lCuestionarios" property="BEvaluado"
														value="<%=Constantes.SI%>">
														<a
															href="OCAPCuestionarios.do?accion=verEvaluacionCuestionario&idCuestionario=<bean:write name="lCuestionarios" property="CCuestionarioId"/>&expId=<bean:write name="OCAPCuestionariosForm" property="CExpId"/>&evaluado=<bean:write name="lCuestionarios" property="BEvaluado"/>&fEvaluacion=<bean:write name="OCAPCuestionariosForm" property="FEvaluacion"/>"
															title="Revisar Evaluaci&oacute;n"> Evaluado </a>
													</logic:equal> <% if (Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol())) { %>
													<logic:notEqual name="lCuestionarios" property="BEvaluado"
														value="<%=Constantes.SI%>">
														<logic:equal name="lCuestionarios" property="BContestado"
															value="<%=Constantes.NO%>">
															<logic:equal name="lCuestionarios"
																property="BParcialmenteContestado"
																value="<%=Constantes.NO%>">
																<a
																	href="OCAPCuestionarios.do?accion=verEvaluacionCuestionario&idCuestionario=<bean:write name="lCuestionarios" property="CCuestionarioId"/>&expId=<bean:write name="OCAPCuestionariosForm" property="CExpId"/>&evaluado=<bean:write name="lCuestionarios" property="BEvaluado"/>"
																	title="Ir Evaluar Prueba"> Evaluar </a>
																<span class="imagen"> <a
																	href="javascript:asignarMaxCreditos('<bean:write name="lCuestionarios" property="CCuestionarioId"/>','<bean:write  name="lCuestionarios" property="NCreditos"/>');">
																		<img src="imagenes/imagenes_ocap/checkOK.gif"
																		alt="Asignar M&aacute;xima Puntuaci&oacute;n" />
																</a>
																</span>
															</logic:equal>
														</logic:equal>
														<logic:equal name="lCuestionarios" property="BContestado"
															value="<%=Constantes.SI%>">
															<a
																href="OCAPCuestionarios.do?accion=verEvaluacionCuestionario&idCuestionario=<bean:write name="lCuestionarios" property="CCuestionarioId"/>&expId=<bean:write name="OCAPCuestionariosForm" property="CExpId"/>&evaluado=<bean:write name="lCuestionarios" property="BEvaluado"/>"
																title="Ir Evaluar Prueba"> Evaluar </a>
															<%contadorPruebasNoEvaluadas++;%>

															<span class="imagen"> <a
																href="javascript:asignarMaxCreditos('<bean:write name="lCuestionarios" property="CCuestionarioId"/>','<bean:write  name="lCuestionarios" property="NCreditos"/>');">
																	<img src="imagenes/imagenes_ocap/checkOK.gif"
																	alt="Asignar M&aacute;xima Puntuaci&oacute;n" />
															</a>
															</span>
														</logic:equal>
														<logic:equal name="lCuestionarios"
															property="BParcialmenteContestado"
															value="<%=Constantes.SI%>">
															<a
																href="OCAPCuestionarios.do?accion=verEvaluacionCuestionario&idCuestionario=<bean:write name="lCuestionarios" property="CCuestionarioId"/>&expId=<bean:write name="OCAPCuestionariosForm" property="CExpId"/>&evaluado=<bean:write name="lCuestionarios" property="BEvaluado"/>"
																title="Ir Evaluar Prueba"> Evaluar </a>
															<%contadorPruebasNoEvaluadas++;%>

															<span class="imagen"> <a
																href="javascript:asignarMaxCreditos('<bean:write name="lCuestionarios" property="CCuestionarioId"/>','<bean:write  name="lCuestionarios" property="NCreditos"/>');">
																	<img src="imagenes/imagenes_ocap/checkOK.gif"
																	alt="Asignar M&aacute;xima Puntuaci&oacute;n" />
															</a>
															</span>
														</logic:equal>
													</logic:notEqual> <% } %> <% } else { %> <logic:equal name="lCuestionarios"
														property="BAnalizado" value="<%=Constantes.SI%>">
														<a
															href="OCAPCuestionarios.do?accion=verAnalisisCuestionario&idCuestionario=<bean:write name="lCuestionarios" property="CCuestionarioId"/>&expId=<bean:write name="OCAPCuestionariosForm" property="CExpId"/>&evaluado=<bean:write name="lCuestionarios" property="BAnalizado"/>">
															Analizado </a>
													</logic:equal> <logic:notEqual name="lCuestionarios"
														property="BAnalizado" value="<%=Constantes.SI%>">
														<% if (!Constantes.OCAP_DIRECCION.equals(jcylUsuario.getUser().getRol())) { %>
														<logic:equal name="lCuestionarios" property="BContestado"
															value="<%=Constantes.NO%>">
															<logic:equal name="lCuestionarios"
																property="BParcialmenteContestado"
																value="<%=Constantes.NO%>">
                                                         &nbsp;
                                                      </logic:equal>
														</logic:equal>
														<logic:equal name="lCuestionarios" property="BContestado"
															value="<%=Constantes.SI%>">
															<a
																href="OCAPCuestionarios.do?accion=verAnalisisCuestionario&idCuestionario=<bean:write name="lCuestionarios" property="CCuestionarioId"/>&expId=<bean:write name="OCAPCuestionariosForm" property="CExpId"/>&evaluado=<bean:write name="lCuestionarios" property="BAnalizado"/>">
																An&aacute;lisis </a>
															<%contadorPruebasNoEvaluadas++;%>
														</logic:equal>
														<logic:equal name="lCuestionarios"
															property="BParcialmenteContestado"
															value="<%=Constantes.SI%>">
															<a
																href="OCAPCuestionarios.do?accion=verAnalisisCuestionario&idCuestionario=<bean:write name="lCuestionarios" property="CCuestionarioId"/>&expId=<bean:write name="OCAPCuestionariosForm" property="CExpId"/>&evaluado=<bean:write name="lCuestionarios" property="BAnalizado"/>">
																An&aacute;lisis </a>
															<%contadorPruebasNoEvaluadas++;%>
														</logic:equal>
														<% } else { %>
                                                      &nbsp;
                                                <% } %>
													</logic:notEqual> <% } %> <% } else { %> &nbsp; <% } %>
												</td>
											</tr>
										</logic:iterate>
									</table>
								</logic:notEqual>
							</div>
						</div>
					</logic:iterate>
					<div class="areasAuto">
						<div class="tituloAreasAuto">
							<span class="titulo">RESUMEN</span>
						</div>
						<div class="datosAreasAuto">
							<table boder="1">
								<tr>
									<th width="55%"></th>
									<th width="11%">Cr&eacute;ditos Fin Autoevaluaci&oacute;n</th>
									<th width="11%">Cr&eacute;ditos Necesarios</th>
									<%if (!(Constantes.SI.equals(session.getAttribute("bSegundaEvaluacion")) && Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol()) ))  { %>
									<th width="11%">Cr&eacute;ditos Asignados</th>
									<%}%>
									<th width="10%"></th>
								</tr>
								<tr>
									<td width="55%" class="centrar textoNegrita">Cr&eacute;ditos
										Totales</td>
									<td width="11%" class="centrar"><span class="textoNegrita"><bean:write
												name="OCAPCuestionariosForm" property="NCreditosItinerario" /></span></td>
									<td width="11%" class="centrar"><span class="textoNegrita"><bean:write
												name="OCAPCuestionariosForm" property="NCreditosNecesarios" /></span></td>
									<%if (!(Constantes.SI.equals(session.getAttribute("bSegundaEvaluacion")) && Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol()) ))  { %>
									<td width="11%" class="centrar"><span class="textoNegrita"><bean:write
												name="OCAPCuestionariosForm" property="NCreditosEvaluados" /></span></td>
									<%}%>
									<td width="10%"></td>
								</tr>
							</table>
						</div>
					</div>
				</logic:notEqual>
				<html:hidden property="CExpId" />
				<html:hidden property="CCuestionarioId" />
				<html:hidden property="NCreditosEvaluados" />
				<html:hidden property="contadorAuditorias" />
				<html:hidden property="FAuditoriaPropuesta" />
				<div class="limpiadora"></div>
				<div class="espaciador"></div>
				<%--
            <span class="totalCreditosAuto"> Total Cr&eacute;ditos Obtenidos:</span>
            <span class="numTotal recuadroM"> <bean:write name="OCAPCuestionariosForm" property="NCreditosItinerario"/></span>
            --%>
				<div class="botonesPagina">
					<%if(!Constantes.OCAP_CE.equals(jcylUsuario.getUser().getRol())) { %>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:volverListado();"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif"
								class="colocaImgPrint" alt="Volver al Listado" /> <span>
									Volver </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<% } 
               
               
               if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL) ){%>

					<%if (!Constantes.SI.equals(session.getAttribute("bSegundaEvaluacion"))) { %>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:generarInforme('<%=contadorPruebasNoEvaluadas%>');">
								<img src="imagenes/imagenes_ocap/action_forward.gif"
								class="colocaImgPrint" alt="Generar Informe Evaluador" /> <span>
									Informe evaluador</span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<%if (Constantes.SI.equals(session.getAttribute("tieneDocumentoEvidencia"))) { %>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:obtenerDocumentoEvidencia('<bean:write name="OCAPCuestionariosForm" property="CExpId"/>')">
								<img src="imagenes/imagenes_ocap/icono_registrar_aceptar.gif"
								class="colocaImgPrint" alt="Ver Informe Evidencia" /> <span>
									Evidencia</span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<%}%>
					<% } else { %>


					<logic:notEqual name="OCAPCuestionariosForm"
						property="contadorAuditorias" value="0">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:solicitarAuditorias('<%=contadorPruebasNoEvaluadas%>');">
									<img src="imagenes/imagenes_ocap/action_forward.gif"
									class="colocaImgPrint"
									alt="Generar Informe Segunda Evaluaci&oacute;n" /> <span>
										Solicitar Auditor&iacute;a</span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</logic:notEqual>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:generarInforme2('<%=contadorPruebasNoEvaluadas%>');">
								<img src="imagenes/imagenes_ocap/action_forward.gif"
								class="colocaImgPrint"
								alt="Generar Informe Segunda Evaluaci&oacute;n" /> <span>
									Informe de Auditor&iacute;a</span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<% } %>
					<%} else if(Constantes.OCAP_CTE.equals(jcylUsuario.getUser().getRol())){%>
					<%if (Constantes.SI.equals(session.getAttribute("tieneDocumentoEvidencia"))) { %>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:obtenerDocumentoEvidencia('<bean:write name="OCAPCuestionariosForm" property="CExpId"/>')">
								<img src="imagenes/imagenes_ocap/icono_registrar_aceptar.gif"
								class="colocaImgPrint" alt="Ver Informe Evidencia" /> <span>
									Evidencia</span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<%}%>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:generarInforme('<%=contadorPruebasNoEvaluadas%>');">
								<img src="imagenes/imagenes_ocap/action_forward.gif"
								class="colocaImgPrint" alt="Generar Informe Evaluador" /> <span>
									Informe evaluador</span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<%} else if(Constantes.OCAP_CE.equals(jcylUsuario.getUser().getRol())){%>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviar('OCAPCuestionarios.do?accion=generarInformeCTE&cCompfqsIdS=<%=session.getAttribute("cCompfqsIdS")%>');">
								<img src="imagenes/imagenes_ocap/aspa_roja.gif"
								class="colocaImgPrint" alt="Volver al Listado" /> <span>
									Volver </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<% }else if (Constantes.SI.equals(session.getAttribute("tieneDocumentoEvidencia")) && jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION) ) { %>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:obtenerDocumentoEvidencia('<bean:write name="OCAPCuestionariosForm" property="CExpId"/>')">
								<img src="imagenes/imagenes_ocap/icono_registrar_aceptar.gif"
								class="colocaImgPrint" alt="Ver Informe Evidencia" /> <span>
									Evidencia</span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<%}%>
				</div>
			</html:form>
		</div>
	</div>
	<!-- /fin de ContenidoMC -->
	<div class="espaciador"></div>
</div>
<!-- /Fin de Contenido -->
<div class="espaciador"></div>
<div class="limpiadora"></div>
<div class="espaciador"></div>
