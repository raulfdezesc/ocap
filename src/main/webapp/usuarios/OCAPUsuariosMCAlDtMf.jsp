<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.creditos.OCAPCreditosOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<% JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO); %>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"
	charset="windows-1252"></script>
	
<script language="javascript" type="text/javascript">
   
   function finalizar() {
   	if(confirm('¿Desea concluir su participación en la fase de Méritos curriculares? \n Si pulsa el botón Aceptar, ya no podrá realizar ninguna modificación.')){
   		enviar('OCAPUsuarios.do?accion=finalizarFaseMeritos');
   	}

   }
   
   function imprimirCV() {
	   enviar('OCAPUsuarios.do?accion=imprimirCV');
   }
   
</script>
	

<div class="ocultarCampo">
	<div class="contenido">
		<div class="contenidoMC">
			<html:form action="/OCAPUsuarios.do">
				<h2 class="tituloContenido">M&Eacute;RITOS CURRICULARES</h2>
				<html:hidden property="CUsr_id" />
				<html:hidden property="CDni" />
				<html:hidden name="OCAPUsuariosForm" property="CExpId" />
				<html:hidden name="OCAPUsuariosForm" property="CExp_id" />
				<html:hidden name="OCAPUsuariosForm" property="CConvocatoriaId" />
				<html:hidden name="OCAPUsuariosForm" property="DGrado_des" />
				<html:hidden name="OCAPUsuariosForm" property="CProfesional_id" />
				<html:hidden name="OCAPUsuariosForm" property="BSexo" />
				<html:hidden name="OCAPUsuariosForm" property="DNombre" />
				<html:hidden name="OCAPUsuariosForm" property="DApellido1" />
				<html:hidden name="OCAPUsuariosForm" property="DProvincia" />
				<html:hidden name="OCAPUsuariosForm" property="DGerencia_nombre" />
				<html:hidden name="OCAPUsuariosForm" property="CPersonalId" />
				<html:hidden name="OCAPUsuariosForm" property="DProvinciaCarrera" />

				<!-- Datos personales -->
				<jsp:include page="OCAPDatosUsuarioDt.jsp" />

				<fieldset>
					<legend class="tituloLegend"> Cr&eacute;ditos Curriculares
					</legend>
					<div class="cuadroFieldset">
						<table class="cuadroMC">
							<tr> <%--Fila cabecera tabla resumen de creditos para MC --%>
								<td class="titulo recuadroM">Actividad</td>
								<td class="titulo recuadroM">N&ordm; Cr&eacute;ditos Necesarios</td>
								<td class="titulo recuadroM">N&ordm; Cr&eacute;ditos Alcanzados</td>
								
								<%-- Columnas opcionales de cabecera tabla resumen de creditos para MC --%>
								<logic:notEqual name="permisoModificar"	value="<%=Constantes.SI%>">
								 	<logic:notEqual name="permisoModificar"	value="<%=Constantes.SI_USUARIO%>"> 
										<%if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())){%>
										<logic:notEqual name="periodoMC" value="<%=Constantes.SI%>">
											<td class="titulo recuadroM">N&ordm; Cr&eacute;ditos Validados CEIS</td>
											<logic:notEqual name="OCAPUsuariosForm" property="enPeriodoValMc" value="<%=Constantes.SI%>">
												<td class="titulo recuadroM">N&ordm; Cr&eacute;ditos Validados CC</td>
											</logic:notEqual>
										</logic:notEqual>
										<% } else if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())){%>
										<logic:notEqual name="periodoMC" value="<%=Constantes.SI%>">
											<td class="titulo recuadroM">N&ordm; Cr&eacute;ditos Validados CEIS</td>
										</logic:notEqual>
										<% } else if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){%>
										<logic:notEqual name="OCAPUsuariosForm" property="CTipo"	value="<%=Constantes.FASE_VALIDACION%>">
											<td class="titulo recuadroM">N&ordm; Cr&eacute;ditos Validados</td>
										</logic:notEqual>
										<% } else {%>
										<logic:equal name="estado" value="<%=Constantes.FASE_CC%>">
											<td class="titulo recuadroM">N&ordm; Cr&eacute;ditos Validados CC</td>
										</logic:equal>
										<% } %>
									</logic:notEqual>
								 </logic:notEqual>
								 	<%--OCAP-80 --%>						 
								    <logic:equal name="permisoUsuarioVerCreditos"	value="<%=Constantes.SI%>">
										<td class="titulo recuadroM">N&ordm; Cr&eacute;ditos Validados CEIS</td>
										<td class="titulo recuadroM">N&ordm; Cr&eacute;ditos Validados CC</td>
								    </logic:equal>
							</tr>
							<logic:iterate id="meritos" name="OCAPUsuariosForm"
								property="listaCreditos" type="OCAPCreditosOT">
								<%--Filas detalle tabla resumen de creditos para MC --%>
								<tr>
									<td class="recuadroM textoNegrita"><bean:write	name="meritos" property="DMerito" /></td>
									<td class="recuadroM"><bean:write name="meritos" property="NCreditos" /></td>
									<%if(meritos.getNCreditosConseguidos().doubleValue() >= meritos.getNCreditos()){%>
									<td class="recuadroM" bgcolor="#E0FFE0"><bean:write	name="meritos" property="NCreditosConseguidos" /></td>
									<%}else{%>
									<td class="recuadroM" bgcolor="#FFBFBF"><bean:write	name="meritos" property="NCreditosConseguidos" /></td>
									<%}%>
									<logic:notEqual name="permisoModificar" value="<%=Constantes.SI%>">
										<logic:notEqual name="permisoModificar"	value="<%=Constantes.SI_USUARIO%>">
											<%if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())){%>
											<logic:notEqual name="periodoMC" value="<%=Constantes.SI%>">
												<%if(meritos.getNCreditosValidadosCeis().doubleValue() >= meritos.getNCreditos()){%>
												<td class="recuadroM" bgcolor="#E0FFE0"><bean:write	name="meritos" property="NCreditosValidadosCeis" /></td>
												<%}else{%>
												<td class="recuadroM" bgcolor="#FFBFBF"><bean:write
														name="meritos" property="NCreditosValidadosCeis" /></td>
												<%}%>
												<logic:notEqual name="OCAPUsuariosForm" property="enPeriodoValMc" value="<%=Constantes.SI%>">
													<%if(meritos.getNCreditosValidadosCc().doubleValue() >= meritos.getNCreditos()){%>
														<td class="recuadroM" bgcolor="#E0FFE0"><bean:write
															name="meritos" property="NCreditosValidadosCc" /></td>
													<%}else{%>
														<td class="recuadroM" bgcolor="#FFBFBF"><bean:write
															name="meritos" property="NCreditosValidadosCc" /></td>
													<%}%>
												</logic:notEqual>
											</logic:notEqual>
											<%} else if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) ){%>
												<logic:notEqual name="periodoMC" value="<%=Constantes.SI%>">
													<%if(meritos.getNCreditosValidadosCeis().doubleValue() >= meritos.getNCreditos()){%>
													<td class="recuadroM" bgcolor="#E0FFE0"><bean:write
														name="meritos" property="NCreditosValidadosCeis" /></td>
													<%}else{%>
													<td class="recuadroM" bgcolor="#FFBFBF"><bean:write
														name="meritos" property="NCreditosValidadosCeis" /></td>
													<%}%>
												</logic:notEqual>
											<%} else if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){%>
											<logic:equal name="OCAPUsuariosForm" property="CTipo"
												value="<%=Constantes.FASE_VALIDACION_CC%>">
												<%if(meritos.getNCreditosValidadosCeis().doubleValue() >= meritos.getNCreditos()){%>
												<td class="recuadroM" bgcolor="#E0FFE0"><bean:write
														name="meritos" property="NCreditosValidadosCeis" /></td>
												<%}else{%>
												<td class="recuadroM" bgcolor="#FFBFBF"><bean:write
														name="meritos" property="NCreditosValidadosCeis" /></td>
												<%}%>
											</logic:equal>
											<logic:equal name="OCAPUsuariosForm" property="CTipo"
												value="<%=Constantes.FASE_CA%>">
												<%if(meritos.getNCreditosValidadosCc().doubleValue() >= meritos.getNCreditos()){%>
												<td class="recuadroM" bgcolor="#E0FFE0"><bean:write
														name="meritos" property="NCreditosValidadosCc" /></td>
												<%}else{%>
												<td class="recuadroM" bgcolor="#FFBFBF"><bean:write
														name="meritos" property="NCreditosValidadosCc" /></td>
												<%}%>
											</logic:equal>
											<% } else {%>
											<logic:equal name="estado" value="<%=Constantes.FASE_CC%>">
												<%if(meritos.getNCreditosValidadosCc().doubleValue() >= meritos.getNCreditos()){%>
												<td class="recuadroM" bgcolor="#E0FFE0"><bean:write
														name="meritos" property="NCreditosValidadosCc" /></td>
												<%}else{%>
												<td class="recuadroM" bgcolor="#FFBFBF"><bean:write
														name="meritos" property="NCreditosValidadosCc" /></td>
												<%}%>
											</logic:equal>
											<% } %>
										</logic:notEqual>
									</logic:notEqual>
									<%--OCAP-80 --%>
									<logic:equal name="permisoUsuarioVerCreditos"	value="<%=Constantes.SI%>">
												<%if(meritos.getNCreditosValidadosCeis().doubleValue() >= meritos.getNCreditos()){%>
												<td class="recuadroM" bgcolor="#E0FFE0"><bean:write	name="meritos" property="NCreditosValidadosCeis" /></td>
												<%}else{%>
												<td class="recuadroM" bgcolor="#FFBFBF"><bean:write
														name="meritos" property="NCreditosValidadosCeis" /></td>
												<%}%>
												<%if(meritos.getNCreditosValidadosCc().doubleValue() >= meritos.getNCreditos()){%>
												<td class="recuadroM" bgcolor="#E0FFE0"><bean:write
														name="meritos" property="NCreditosValidadosCc" /></td>
												<%}else{%>
												<td class="recuadroM" bgcolor="#FFBFBF"><bean:write
														name="meritos" property="NCreditosValidadosCc" /></td>
												<%}%>
								     </logic:equal>
								</tr>
							</logic:iterate>
						</table>
					</div>
				</fieldset>
				<div class="listadoMeritos">
					<div class="limpiadora"></div>
					<div class="espaciador"></div>
					<%if (Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol()) ){%>
					<div class="recuadroM2 margenTexto cuadroMeritos">
						<bean:write name="OCAPUsuariosForm" property="DMensaje"
							filter="false" />
						<p>Los interesados dispondrán de un plazo de veinte días, contados desde el día siguiente al de la publicación
							de la resolución por la que se acuerda el inicio de la fase de autoevaluación de méritos curriculares correspondiente a la
 							convocatoria.</p> <!-- Se quita el texto de acceso al Grado III   Incidencia OCAP-88 --> 
					</div>
					<% } %>
					<h3>M&eacute;ritos Curriculares</h3>
					<bean:define id="pestaSeleccionada" name="OCAPUsuariosForm"
						property="pestanaSeleccionada" />
					<div class="pestanaPagina">
						<logic:iterate id="meritos" name="OCAPUsuariosForm"
							property="listaCreditos" type="OCAPCreditosOT">
							<div class="pestanaAccion">
								<logic:equal name="meritos" property="BMeritoSeleccionado"
									value="<%=Constantes.SI%>">
									<bean:define id="meritoSelecc" name="meritos"
										type="OCAPCreditosOT" toScope="request" />
									<span class="izqPestanaSel"></span>
									<span class="cenPestanaSel"> <a
										href="OCAPUsuarios.do?accion=irInsertar&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&pestana=<bean:write name="meritos" property="CDefmeritoId"/>&CExp_id=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
											<span> <bean:write name="meritos" property="DMerito" />
										</span>
									</a>
									</span>
									<span class="derPestanaSel"></span>
								</logic:equal>
								<logic:notEqual name="meritos" property="BMeritoSeleccionado"
									value="<%=Constantes.SI%>">
									<a
										href="OCAPUsuarios.do?accion=irInsertar&pestana=<bean:write name="meritos" property="CDefmeritoId"/>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=<%=request.getParameter("estado")%>&CExp_id=<bean:write name="OCAPUsuariosForm" property="CExpId"/>"
										class="pestanaHover"> <span class="izqPestana"></span> <span
										class="cenPestana"> <span> <bean:write
													name="meritos" property="DMerito" />
										</span>
									</span> <span class="derPestana"></span>
									</a>
								</logic:notEqual>
							</div>
						</logic:iterate>
					</div>
					<div class="recuadroM" id="n001">
						<div class="limpiadora"></div>
						<bean:write name="meritoSelecc" property="DDefMeritoDesc"
							filter="false" />
						<!-- Lista de Meritos -->
						<jsp:include page="OCAPUsuariosMCLs.jsp" />
						<!-- Lista de Meritos Opionales -->
						<jsp:include page="OCAPUsuariosMCOpLs.jsp" />
					</div>
					<div class="espaciador"></div>
					<div class="limpiadora"></div>
					<% if (!Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())){%>
					<% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()) ) { %>
					<logic:equal name="estado"
						value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
						<logic:equal name="pendientesAclaracion"
							value="<%=Constantes.SI%>">
							<div class="botonesPagina">
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="javascript:enviar('OCAPSolicitudes.do?accion=generarPDF&jspAccion=OCAPSolicitudesDt');">
											<img src="imagenes/imagenes_ocap/icono_aclaracion.gif"
											class="colocaImgPrint"
											alt="Generar Informe Solicitud de Aclaraciones" /> <span>
												Generar Solicitud de Aclaraciones </span>
									</a>
									</span> <span class="derBoton"></span>
								</div>
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="javascript:enviar('OCAPMeritos.do?accion=eliminarAclaracionesInvalidarMeritos&estado=<bean:write name="estado"/>');">
											<img src="imagenes/imagenes_ocap/icono_aclaracion.gif"
											class="colocaImgPrint"
											alt="Eliminar todas las Aclaraciones e Invalidar los M&eacute;ritos Curriculares que estaban pendientes de aclarar" />
											<span> Eliminar Aclaraciones e Invalidar </span>
									</a>
									</span> <span class="derBoton"></span>
								</div>
							</div>
							<div class="espaciador"></div>
							<div class="limpiadora"></div>
						</logic:equal>
					</logic:equal>
					<% } %>
					<div class="botonesPagina">
						<% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()) ) { %>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton">
								<a
								href="javascript:enviar('OCAPSolicitudes.do?accion=irDetalle&opcion=<%=session.getAttribute("opcion")%>');">
									<img src="imagenes/imagenes_ocap/icon_accept.gif"
									class="colocaImgPrint" alt="Ver Expediente" /> <span> Ir
										al Expediente </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<% } %>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> 
								<a
								href="javascript:enviar('OCAPSolicitudes.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=session.getAttribute("opcion")%>');">
									<img src="imagenes/imagenes_ocap/action_stop.gif"
									class="colocaImgPrint" alt="Volver al listado" /> <span>
										Volver al listado </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</div>
					<% } %>
					<div class="espaciador"></div>
					<div class="limpiadora"></div>
				</div>
			<%if (Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())){%>
			<div class="botonesPagina botonesRenuncia">
				<logic:equal name="permisoModificar" value="<%=Constantes.SI%>">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:finalizar();"> <img
							src="imagenes/imagenes_ocap/icon_accept.gif"
							alt="Finalizar" /> <span> Finalizar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
				</logic:equal>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:imprimirCV();"> <img
							src="imagenes/imagenes_ocap/icon_accept.gif"
							alt="Finalizar" /> <span> Imprimir CV </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>
			<%}%>

			</html:form>
		</div>
	</div>
	<div class="limpiadora"></div>
</div>
