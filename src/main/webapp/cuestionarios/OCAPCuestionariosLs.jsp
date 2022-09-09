<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.areasItinerarios.OCAPAreasItinerariosOT"%>
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
<script language="javascript" type="text/javascript">
function finalizarCA(permiso) {
   if (permiso=='<%=Constantes.SI%>') {
      if (confirm('Si finaliza el proceso no podr\u00E1 contestar ning\u00FAn formulario m\u00E1s.\n\n\Aseg\u00FArese de que ha adjuntado el fichero de evidencias y recuerde que una vez finalizada su autoevaluación se cierra el proceso impidiendo hacer modificaciones.\n\n\u00BFDesea finalizar?'))
         enviar('OCAPCuestionarios.do?accion=finalizarProceso');
   } else 
      alert('No puede finalizar el proceso hasta que no haya contestado y finalizado al menos un formulario de cada una de las \u00E1reas.');
}

function abrirCuestionarios(cuestionario) {
   if(cuestionario != null && cuestionario != '') {
      document.forms[0].CCuestionarioId.value = cuestionario;
      if (confirm('Se abrir\u00E1n todos los Cuestionarios asociados a esta Prueba y deber\u00E1 volver a finalizarlos. \n\u00BFDesea continuar?'))
         enviar('OCAPCuestionarios.do?accion=abrirCuestionarios');
   }
}
</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoAutoevaluacion">
		<html:form action="/OCAPCuestionarios.do">
			<h2 class="tituloContenido">Evaluaci&oacute;n/Pruebas de buena
				pr&aacute;ctica asignados a cada &Aacute;REA</h2>
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

			<div class="cuadroFieldset">
				<p>Se encuentra usted en la pantalla donde aparecen las pruebas
					de buena pr&aacute;ctica que contiene cada &aacute;rea de
					evaluaci&oacute;n. Usted podr&aacute; seleccionar el icono que
					aparece a la derecha del nombre del &aacute;rea para obtener
					m&aacute;s informaci&oacute;n acerca de la misma.</p>
				<p>En cada una de las&nbsp; &aacute;reas aparecen las pruebas
					de buena pr&aacute;ctica que configuran su evaluaci&oacute;n.&nbsp;
					Cuando realice cada una de las pruebas de buena pr&aacute;ctica que
					usted elija ir&aacute;n apareciendo los cr&eacute;ditos obtenidos y
					que tienen car&aacute;cter provisional hasta el fin del proceso de
					evaluaci&oacute;n.</p>
				<p>En algunas de las pruebas de buena pr&aacute;ctica
					aparecer&aacute; junto a los cr&eacute;ditos una &ldquo;?&rdquo;.
					Quiere decir que no se suman los cr&eacute;ditos obtenidos hasta
					que no se a&ntilde;ade la evidencia que usted debe aportar.</p>
				<p>A la derecha&nbsp; aparece un bot&oacute;n de FIN DE
					PROCESO. Deber&aacute; pulsar dicho bot&oacute;n cuando haya
					finalizado todas las pruebas de buena pr&aacute;ctica que desee
					realizar. Le recordamos que es obligatoria al menos una prueba por
					&aacute;rea. Una vez finalizado el proceso ya no podr&aacute;
					modificar ninguna prueba de su itinerario.</p>
			</div>

			<bean:size id="numAreas" name="OCAPCuestionariosForm"
				property="listadoAreas" />
			<logic:notEqual name="numAreas" value="0">
				<logic:iterate id="listaAreas" name="OCAPCuestionariosForm"
					property="listadoAreas" type="OCAPAreasItinerariosOT">
					<div class="areasAuto">
						<div class="tituloAreasAuto">
							<span class="titulo"><bean:write name="listaAreas"
									property="DNombreLargo" /></span> <a
								href="OCAPCuestionarios.do?accion=irInformacionArea&cAreaId=<bean:write name="listaAreas" property="CAreaId"/>"><img
								src="imagenes/imagenes_ocap/icono_informacion.gif"
								alt="Informacion" class="colocaImgInfo" /></a>
						</div>
						<div class="datosAreasAuto">
							<bean:size id="numCuestionarios" name="listaAreas"
								property="listaCuestionarios" />
							<logic:notEqual name="numCuestionarios" value="0">
								<table boder="1">
									<tr>
										<th colspan="2" class="titulo" width="50%">Nombre de la
											Prueba</th>
										<logic:equal name="verDefinitivos" value="<%=Constantes.NO%>">
											<th class="titulo" width="12%">Cred. provisionales
												obtenidos</th>
										</logic:equal>
										<logic:equal name="verDefinitivos" value="<%=Constantes.SI%>">
											<th class="titulo" width="12%">Cr&eacute;ditos
												definitivos</th>
										</logic:equal>
										<th class="titulo" width="12%">Cr&eacute;ditos
											m&aacute;ximos</th>
										<th class="titulo" width="6%">&nbsp;</th>
									</tr>
									<logic:iterate id="lCuestionarios" name="listaAreas"
										property="listaCuestionarios" type="OCAPCuestionariosOT">
										<tr>
											<logic:equal name="lCuestionarios" property="BContestado"
												value="<%=Constantes.SI%>">
												<td class="imagen"><img
													src="imagenes/imagenes_ocap/icono_editar_check.gif"
													alt="icono editar" /></td>
											</logic:equal>
											<logic:equal name="lCuestionarios" property="BContestado"
												value="<%=Constantes.NO%>">
												<td class="imagen"><img
													src="imagenes/imagenes_ocap/icono_editar.gif"
													alt="icono editar" /></td>
											</logic:equal>
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
											<td class="centrar"><logic:equal name="verDefinitivos"
													value="<%=Constantes.NO%>">
													<logic:equal name="lCuestionarios" property="BContestado"
														value="<%=Constantes.SI%>">
														<logic:equal name="lCuestionarios" property="BMostrar"
															value="<%=Constantes.SI%>">
															<span class="textoNegrita"> <bean:write
																	name="lCuestionarios" property="NCreditosConseguidos" />
																?
															</span>
														</logic:equal>
														<logic:equal name="lCuestionarios" property="BMostrar"
															value="<%=Constantes.NO%>">
															<span class="textoNegrita"> <bean:write
																	name="lCuestionarios" property="NCreditosConseguidos" /></span>
														</logic:equal>
													</logic:equal>
													<logic:equal name="lCuestionarios" property="BContestado"
														value="<%=Constantes.NO%>">
														<span class="textoNegrita"><bean:write
																name="lCuestionarios" property="NCreditosConseguidos" /></span>
													</logic:equal>
												</logic:equal> <logic:equal name="verDefinitivos"
													value="<%=Constantes.SI%>">
													<span class="textoNegrita"><bean:write
															name="lCuestionarios" property="NCreditosFinales" /></span>
												</logic:equal></td>
											<td class="centrar"><span class="textoNegrita"><bean:write
														name="lCuestionarios" property="NCreditos" /></span></td>
											<td class="centrar"><logic:equal name="verFinProceso"
													value="<%=Constantes.SI%>">
													<logic:equal name="lCuestionarios" property="BContestado"
														value="<%=Constantes.SI%>">
														<span class="imagen"> <a
															href="javascript:abrirCuestionarios('<bean:write name="lCuestionarios" property="CCuestionarioId"/>');">
																<img src="imagenes/imagenes_ocap/lock-unlock.png"
																alt="Abrir Cuestionarios Finalizados" />
														</a>
														</span>
													</logic:equal>
												</logic:equal></td>
										</tr>
									</logic:iterate>
								</table>
							</logic:notEqual>
						</div>
					</div>
				</logic:iterate>
			</logic:notEqual>
			<html:hidden property="CExpId" />
			<html:hidden property="CCuestionarioId" />
			<div class="limpiadora"></div>
			<div class="espaciador"></div>
			<logic:equal name="verDefinitivos" value="<%=Constantes.NO%>">
				<span class="totalCreditosAuto"> Total Cr&eacute;ditos
					Provisionales Obtenidos:</span>
				<span class="numTotal recuadroM"> <bean:write
						name="OCAPCuestionariosForm" property="NCreditosItinerario" /></span>
			</logic:equal>
			<logic:equal name="verDefinitivos" value="<%=Constantes.SI%>">
				<span class="totalCreditosAuto"> Total Cr&eacute;ditos
					Definitivos Obtenidos:</span>
				<span class="numTotal recuadroM"> <bean:write
						name="OCAPCuestionariosForm" property="NCreditosEvaluados" /></span>
			</logic:equal>
			<span class="totalCreditosAuto"> Total Cr&eacute;ditos
				Necesarios:</span>
			<span class="numTotal recuadroM"> <bean:write
					name="OCAPCuestionariosForm" property="NCreditosNecesarios" />
			</span>
			<logic:equal name="verFinProceso" value="<%=Constantes.SI%>">
				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:finalizarCA('<bean:write name="OCAPCuestionariosForm" property="BFinalizadoUnoPorArea"/>');">
								<img src="imagenes/imagenes_ocap/action_forward.gif"
								class="colocaImgPrint"
								alt="Finalizar el proceso de auto-evaluaci&oacute;n de Desempe&ntilde;o de la Competencia" />
								<span> FIN DEL PROCESO </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</logic:equal>

			<!--Boton para insertar documentos-->

			<%if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIO_FASE_III)){%>
			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="OCAPDocumentos.do?accion=irAltaDocumentoEvidencia&tarea=actualizar&tareaDocu=alta&cExpId=<bean:write name="OCAPCuestionariosForm" property="CExpId"/>">
							<img src="imagenes/imagenes_ocap/action_forward.gif"
							class="colocaImgPrint"
							alt="Accede a la pantalla para adjuntar el fichero de evidencia" />
							<span>ADJUNTAR FICHERO EVIDENCIAS</span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>

			<% }%>

			<!--
         <logic:notEqual name="verFinProceso" value="<%=Constantes.SI%>">
            <logic:equal name="verEncuesta" value="<%=Constantes.SI%>">
               <div class="botonesPagina">
                  <div class="botonAccion" >
                     <span class="izqBoton"></span>
                     <span class="cenBoton">
                     <a href="OCAPEncuestaCalidad.do?accion=irRellenar">
                        <img src="imagenes/imagenes_ocap/action_forward.gif" class="colocaImgPrint" alt="Ir a la Encuesta de Calidad"/>
                        <span> ENCUESTA DE SATISFACCI&Oacute;N </span>
                     </a>
                     </span>
                     <span class="derBoton"></span>
                  </div>
               </div>
            </logic:equal>
         </logic:notEqual>
         -->
		</html:form>
	</div>
	<!-- /fin de ContenidoMC -->
</div>
<!-- /Fin de Contenido -->
<div class="limpiadora"></div>
<br />
&nbsp;
<br />
&nbsp;
<br />
