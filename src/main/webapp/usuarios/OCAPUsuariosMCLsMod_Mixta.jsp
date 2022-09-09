<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.ot.expedienteMC.OCAPExpedientemcOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.meritosCurriculares.OCAPMeritoscurricularesOT"%>


<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<% JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO); %>

<% String tipoMeritoAux="";
  	OCAPMeritoscurricularesOT meritoOriginal = (OCAPMeritoscurricularesOT)request.getAttribute("requestTiposMeritos");
                           if (meritoOriginal.getDNombre().indexOf("+")!=-1) {
                              tipoMeritoAux = meritoOriginal.getDNombre().replaceAll("\\+",Constantes.MAS);
                           } else {
                              tipoMeritoAux = meritoOriginal.getDNombre();
                         }                       
                        %>
			<table>
				<logic:iterate id="meritoExpmcCTSM" name="requestTiposMeritos"	property="listaExpmcCTSM" type="OCAPExpedientemcOT">
					<tr class="tablaMC">
						<td class="primero"><span><bean:write name="meritoExpmcCTSM" property="DTitulo" /></span></td>
						<logic:equal name="permisoModificar" value="<%=Constantes.SI%>">
							<td class="icono">
								<div class="botonAccion">
									<span><a
										href="OCAPMeritos.do?accion=irEliminarMerito&idMer=<bean:write name="meritoExpmcCTSM" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nombre=<bean:write name="meritoExpmcCTSM" property="DTituloCodificado"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>">
											<img src="imagenes/imagenes_ocap/icono_eliminar.gif" alt="Eliminar  M&eacute;rito" /> <span>Eliminar</span>
									</a>
									</span>
								</div>
							</td>
							<td class="icono">
								<div class="botonAccion">
									<span> <a href="OCAPMeritos.do?accion=irModificar<bean:write name="requestTiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmcCTSM" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipo=<bean:write name="requestTiposMeritos" property="CTipoMerito"/>&personal=<bean:write name="OCAPUsuariosForm" property="CPersonalId"/>">
											<img src="imagenes/imagenes_ocap/icono_modificar.gif"	alt="Modificar  M&eacute;rito" /> <span>Modificar</span>
									</a>
									</span>
								</div>
							</td>
						</logic:equal>
						<logic:notEqual name="permisoModificar" value="<%=Constantes.SI%>">
							<% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())){%>
							<td class="icono">
							<logic:equal name="estado" value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
									<logic:notEqual name="meritoExpmcCTSM" property="BInvalidado" value="<%=Constantes.SI%>">
										<div class="botonAccion">
											<span>
											 	<a href="OCAPMeritos.do?accion=irEliminarMerito&idMer=<bean:write name="meritoExpmcCTSM" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nombre=<bean:write name="meritoExpmcCTSM" property="DTituloCodificado"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&CEIS=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
													<span>Invalidar M&eacute;rito</span>
												</a>
											</span>
										</div>
									</logic:notEqual>
									<logic:equal name="meritoExpmcCTSM" property="BInvalidado"	value="<%=Constantes.SI%>">
										<div class="botonAccion">
											<span>
												 <a	href="OCAPMeritos.do?accion=irEliminarMerito&idMer=<bean:write name="meritoExpmcCTSM" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nombre=<bean:write name="meritoExpmcCTSM" property="DTituloCodificado"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&CEIS=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
													<img src="imagenes/imagenes_ocap/icono_eliminar.gif" alt="Volver a Admitir  M&eacute;rito" />
													 <span title="Volver a Admitir  M&eacute;rito"> Invalidado</span>
												</a>
											</span>
										</div>
									</logic:equal>
							 </logic:equal>
							<logic:notEqual name="estado" value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
									<logic:equal name="meritoExpmcCTSM" property="BInvalidado" value="<%=Constantes.SI%>">
										<div class="botonAccion">
											<span>Invalidado</span>
										</div>
									</logic:equal>
							</logic:notEqual></td>
							<td class="icono">
								<div class="botonAccion">
									<span> <a
										href="OCAPMeritos.do?accion=irModificar<bean:write name="requestTiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmcCTSM" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=<bean:write name="estado"/>&tipo=<bean:write name="requestTiposMeritos" property="CTipoMerito"/>">
											<img src="imagenes/imagenes_ocap/icono_modificar.gif"
											alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
									</a>
									</span>
									<logic:equal name="meritoExpmcCTSM" property="BPdteAclarar" value="<%=Constantes.SI%>">
										<span><img src="imagenes/imagenes_ocap/icono_aclaracion.gif" alt="Pendiente de Aclaraci&oacute;n" /></span>
									</logic:equal>
								</div>
							</td>
							
							<td class="icono" nowrap="nowrap">
								<logic:equal name="estado"	value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
									<% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) ) { %>
										<logic:equal name="OCAPUsuariosForm" property="enPeriodoValMc" value="<%=Constantes.SI%>">
											<logic:notEqual name="meritoExpmcCTSM" property="BInvalidado" value="<%=Constantes.SI%>">
									
												<div class="botonAccion">
												
													<span> <a title="<%= meritoExpmcCTSM.getDMotivosCeis()%>" 
													 href="OCAPMeritos.do?accion=irModificarCreditoMerito&idMer=<bean:write name="meritoExpmcCTSM" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nHoras=<bean:write name="meritoExpmcCTSM" property="NHoras"/>&tipoMerito=<%=tipoMeritoAux%>&nombre=<bean:write name="meritoExpmcCTSM" property="DTituloCodificado"/>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&CTipo=<bean:write name="OCAPUsuariosForm" property="CTipo"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
															<img src="imagenes/editar.gif" 
															alt="Modificar cr&eacute;ditos"> <span>Modificar horas</span>
													</a>
													</span>
												</div>
											</logic:notEqual>
										</logic:equal>
									<% } %>
									<% if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()) ) { %>
										<logic:equal name="OCAPUsuariosForm" property="enPeriodoValCc" value="<%=Constantes.SI%>">
											<logic:notEqual name="meritoExpmcCTSM" property="BInvalidado" value="<%=Constantes.SI%>">
												<div class="botonAccion">
													<span>   <a title="<%= meritoExpmcCTSM.getDMotivosCc()%>" 
														href="OCAPMeritos.do?accion=irModificarCreditoMerito&idMer=<bean:write name="meritoExpmcCTSM" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nHoras=<bean:write name="meritoExpmcCTSM" property="NHoras"/>&tipoMerito=<%=tipoMeritoAux%>&nombre=<bean:write name="meritoExpmcCTSM" property="DTituloCodificado"/>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&CTipo=<bean:write name="OCAPUsuariosForm" property="CTipo"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
															<img src="imagenes/editar.gif"
															alt="Modificar cr&eacute;ditos"><span>Modificar horas</span>
													</a>
													</span>
												</div>
											</logic:notEqual>
										</logic:equal>
									<% } %>
								</logic:equal></td>
							
							
							
							<logic:equal name="estado" value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
									<logic:notEqual name="meritoExpmcCTSM" property="BInvalidado" value="<%=Constantes.SI%>">
										<div class="botonAccion"><span></span></div>
									</logic:notEqual>
							</logic:equal></td>
							<% } else if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){%>
							<td class="icono">
							<logic:equal name="OCAPUsuariosForm" property="CTipo" value="<%=Constantes.FASE_VALIDACION_CC%>">
									<logic:equal name="meritoExpmcCTSM" property="BInvalidadoCeis" value="<%=Constantes.SI%>">
										<div class="botonAccion"><span>Invalidado</span></div>
									</logic:equal>
								</logic:equal> 
							<logic:equal name="OCAPUsuariosForm" property="CTipo" value="<%=Constantes.FASE_CA%>">
									<logic:equal name="meritoExpmcCTSM" property="BInvalidadoCc"value="<%=Constantes.SI%>">
										<div class="botonAccion"><span>Invalidado</span></div>
									</logic:equal>
							</logic:equal>
							</td>
							<td class="icono">
								<div class="botonAccion">
									<span> <a
										href="OCAPMeritos.do?accion=irModificar<bean:write name="requestTiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmcCTSM" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=<bean:write name="estado"/>&tipo=<bean:write name="requestTiposMeritos" property="CTipoMerito"/>">
											<img src="imagenes/imagenes_ocap/icono_modificar.gif"
											alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
									</a>
									</span>
								</div>
							</td>
							<% } %>
						</logic:notEqual>
						<td class="primero">
							<div class="botonAccion">
								<span class="creditos"><bean:write name="meritoExpmcCTSM"
										property="NHoras" /> horas</span>
							</div>
						</td>
						<logic:notEqual name="permisoModificar" value="<%=Constantes.SI%>">
							<%if (Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol() )){%>
							<logic:equal name="permisoVerCeis" value="<%=Constantes.SI%>">
								<logic:notEqual name="estado" value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
									<td class="icono">
										<div class="botonAccion">
											<logic:equal name="meritoExpmcCTSM" property="BPdteAclarar" value="<%=Constantes.SI%>">
												<span><img src="imagenes/imagenes_ocap/icono_aclaracion.gif" alt="Pendiente de Aclaraci&oacute;n" /></span>
											</logic:equal>
											<logic:equal name="meritoExpmcCTSM" property="BInvalidadoCc" value="<%=Constantes.SI%>">
												<span>Invalidado</span>
											</logic:equal>
										</div>
									</td>
								</logic:notEqual>
							</logic:equal>
							<%} else if (!Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) && !Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()) && !Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){%>
							<logic:equal name="permisoVerCeis" value="<%=Constantes.SI%>">
								<td class="icono">
									<div class="botonAccion">
										<logic:equal name="meritoExpmcCTSM" property="BPdteAclarar" value="<%=Constantes.SI%>">
											<span><img	src="imagenes/imagenes_ocap/icono_aclaracion.gif"	alt="Pendiente de Aclaraci&oacute;n" /></span>
										</logic:equal>
										<logic:equal name="meritoExpmcCTSM" property="BInvalidadoCc" value="<%=Constantes.SI%>">
											<span>Invalidado</span>
										</logic:equal>
									</div>
								</td>
							</logic:equal>
							<% } %>
						</logic:notEqual>
					</tr>
				</logic:iterate>
			</table>