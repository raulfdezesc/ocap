<%@ page contentType="text/html;charset=ISO-8859-1"	pageEncoding="windows-1252"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.ot.expedienteMC.OCAPExpedientemcOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.meritosCurriculares.OCAPMeritoscurricularesOT"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<% JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO); %>

<!-- Inicio Iteracion Lista Meritos Opcionales -->
<logic:notEmpty name="OCAPUsuariosForm"	property="listaMeritosOpcionales">
	<logic:iterate id="tiposMeritosOpc" name="OCAPUsuariosForm" property="listaMeritosOpcionales" type="OCAPMeritoscurricularesOT">
		<logic:notEmpty name="tiposMeritosOpc" property="listaMeritosUsuario">
			<div class="tituloOpcionesTabla">
				<bean:write name="tiposMeritosOpc" property="DNombre" />
			</div>
			<bean:write name="tiposMeritosOpc" property="DDescripcion"	filter="false" />
		</logic:notEmpty>
		<logic:iterate id="tiposMeritos" name="tiposMeritosOpc"
			property="listaMeritosUsuario" type="OCAPMeritoscurricularesOT">
			<bean:define id="requestTiposMeritos" name="tiposMeritos" toScope="request" />
			<bean:define id="identificadorExpediente" name="OCAPUsuariosForm" property="CExpId" toScope="request" />
			<bean:define id="meritosOpcionalesFlag" value="Y" toScope="request" />
			<%
                              String tipoMeritoAuxOpc = "";
                              if(tiposMeritos.getDNombre().indexOf("+")!=-1) {
                                 tipoMeritoAuxOpc = tiposMeritos.getDNombre().replaceAll("\\+",Constantes.MAS);
                              } else if (tiposMeritos.getDNombre().indexOf("ñ")!=-1) {
                                 tipoMeritoAuxOpc = tiposMeritos.getDNombre().replaceAll("ñ","n");
                              }else{
                            	  tipoMeritoAuxOpc = tiposMeritos.getDNombre();
                              }
                           %>
                           
		<jsp:include page="OCAPUsuariosMCLsBotonMeritos.jsp" />


			<logic:equal name="permisoModificar" value="<%=Constantes.SI%>">
				<span class="botonAccion"> <span> <logic:equal
							name="tiposMeritos" property="CTipoMerito"
							value="<%=Constantes.MC_MERITO_SINO%>">
							<logic:empty name="tiposMeritos" property="listaExpmc">
								<a class="colocaAnadir"
									href="OCAPMeritos.do?accion=irInsertar<bean:write name="tiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="tiposMeritosOpc" property="CDefmeritoId"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
									<img src="imagenes/imagenes_ocap/anadir.gif"
									alt="A&ntilde;adir M&eacute;rito" /> <span>A&ntilde;adir</span>
								</a>
							</logic:empty>
						</logic:equal> <logic:equal name="tiposMeritos" property="CTipoMerito"
							value="<%=Constantes.MC_SESION_CLINICA%>">
							<logic:empty name="tiposMeritos" property="listaExpmc">
								<a class="colocaAnadir"
									href="OCAPMeritos.do?accion=irInsertar<bean:write name="tiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="tiposMeritosOpc" property="CDefmeritoId"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
									<img src="imagenes/imagenes_ocap/anadir.gif"
									alt="A&ntilde;adir M&eacute;rito" /> <span>A&ntilde;adir</span>
								</a>
							</logic:empty>
						</logic:equal> <logic:equal name="tiposMeritos" property="CTipoMerito"
							value="<%=Constantes.MC_CENTINELA%>">
							<logic:empty name="tiposMeritos" property="listaExpmc">
								<a class="colocaAnadir"
									href="OCAPMeritos.do?accion=irInsertar<bean:write name="tiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="tiposMeritosOpc" property="CDefmeritoId"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
									<img src="imagenes/imagenes_ocap/anadir.gif"
									alt="A&ntilde;adir M&eacute;rito" /> <span>A&ntilde;adir</span>
								</a>
							</logic:empty>
						</logic:equal> <logic:equal name="tiposMeritos" property="CTipoMerito"
							value="<%=Constantes.MC_DOCENCIA_POST%>">
							<logic:notEmpty name="tiposMeritos" property="listaExpmc">
								<a class="colocaAnadir"
									href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="tiposMeritosOpc" property="CDefmeritoId"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
									<img src="imagenes/imagenes_ocap/icono_modificar.gif"
									alt="Modificar  M&eacute;rito" /> <span>Modificar</span>
								</a>
							</logic:notEmpty>
							<logic:empty name="tiposMeritos" property="listaExpmc">
								<a class="colocaAnadir"
									href="OCAPMeritos.do?accion=irInsertar<bean:write name="tiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="tiposMeritosOpc" property="CDefmeritoId"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
									<img src="imagenes/imagenes_ocap/anadir.gif"
									alt="A&ntilde;adir M&eacute;rito" /> <span>A&ntilde;adir</span>
								</a>
							</logic:empty>
						</logic:equal> <logic:equal name="tiposMeritos" property="CTipoMerito"
							value="<%=Constantes.MC_BIBLIOGRAFICAS%>">
							<logic:iterate id="lista" name="tiposMeritos"
								property="listaExpmc" type="OCAPExpedientemcOT">
								<logic:notEmpty name="tiposMeritos" property="listaExpmc">
									<a
										href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="lista" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="tiposMeritosOpc" property="CDefmeritoId"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
										<img src="imagenes/imagenes_ocap/icono_modificar.gif"
										alt="Modificar  M&eacute;rito" /> <span>Modificar</span>
									</a>
								</logic:notEmpty>
							</logic:iterate>
							<logic:empty name="tiposMeritos" property="listaExpmc">
								<a class="colocaAnadir"
									href="OCAPMeritos.do?accion=irInsertar<bean:write name="tiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="tiposMeritosOpc" property="CDefmeritoId"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
									<img src="imagenes/imagenes_ocap/anadir.gif"
									alt="A&ntilde;adir M&eacute;rito" /> <span>A&ntilde;adir</span>
								</a>
							</logic:empty>
						</logic:equal> <logic:notEqual name="tiposMeritos" property="CTipoMerito"
							value="<%=Constantes.MC_MERITO_SINO%>">
							<logic:notEqual name="tiposMeritos" property="CTipoMerito"
								value="<%=Constantes.MC_SESION_CLINICA%>">
								<logic:notEqual name="tiposMeritos" property="CTipoMerito"
									value="<%=Constantes.MC_CENTINELA%>">
									<logic:notEqual name="tiposMeritos" property="CTipoMerito"
										value="<%=Constantes.MC_DOCENCIA_POST%>">
										<logic:notEqual name="tiposMeritos" property="CTipoMerito"
											value="<%=Constantes.MC_BIBLIOGRAFICAS%>">
											<a class="colocaAnadir"
												href="OCAPMeritos.do?accion=irInsertar<bean:write name="tiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="tiposMeritosOpc" property="CDefmeritoId"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>&personal=<bean:write name="OCAPUsuariosForm" property="CPersonalId"/>">
												<img src="imagenes/imagenes_ocap/anadir.gif"
												alt="A&ntilde;adir M&eacute;rito" /> <span>A&ntilde;adir</span>
											</a>
										</logic:notEqual>
									</logic:notEqual>
								</logic:notEqual>
							</logic:notEqual>
						</logic:notEqual>
				</span>
				</span>
			</logic:equal>
			<div class="limpiadora"></div>
			<logic:notEmpty name="tiposMeritos" property="listaExpmc">
				<table>
					<logic:iterate id="meritoExpmc" name="tiposMeritos"
						property="listaExpmc" type="OCAPExpedientemcOT">
						<tr class="tablaMC">
							<td class="primero"><span><bean:write
										name="meritoExpmc" property="DTitulo" /></span></td>
							<logic:equal name="permisoModificar" value="<%=Constantes.SI%>">
								<td class="icono">
									<div class="botonAccion">
										<span> <a
											href="OCAPMeritos.do?accion=irEliminarMerito&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nombre=<bean:write name="meritoExpmc" property="DTituloCodificado"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>">
												<img src="imagenes/imagenes_ocap/icono_eliminar.gif"
												alt="Eliminar  M&eacute;rito" /> <span>Eliminar</span>
										</a>
										</span>
									</div>
								</td>
								<td class="icono">
									<div class="botonAccion">
										<span> <logic:notEqual name="tiposMeritos"
												property="CTipoMerito"
												value="<%=Constantes.MC_DOCENCIA_POST%>">
												<logic:notEqual name="tiposMeritos" property="CTipoMerito"
													value="<%=Constantes.MC_BIBLIOGRAFICAS%>">
													<a
														href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="tiposMeritosOpc" property="CDefmeritoId"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>&personal=<bean:write name="OCAPUsuariosForm" property="CPersonalId"/>">
														<img src="imagenes/imagenes_ocap/icono_modificar.gif"
														alt="Modificar  M&eacute;rito" /> <span>Modificar</span>
													</a>
												</logic:notEqual>
											</logic:notEqual>
										</span>
									</div>
								</td>
							</logic:equal>
							<logic:notEqual name="permisoModificar"	value="<%=Constantes.SI%>">
								<% if (jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC))){%>
								<td class="icono"><logic:equal name="estado"
										value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
										<% if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS)) { %>
											<logic:equal name="OCAPUsuariosForm" property="enPeriodoValMc"
												value="<%=Constantes.SI%>">
												<logic:notEqual name="meritoExpmc" property="BInvalidado"
													value="<%=Constantes.SI%>">
													<div class="botonAccion">
														<span> <a
															href="OCAPMeritos.do?accion=irEliminarMerito&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nombre=<bean:write name="meritoExpmc" property="DTituloCodificado"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&CEIS=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
																<span>Invalidar M&eacute;rito</span>
														</a>
														</span>
													</div>
												</logic:notEqual>
											</logic:equal>
										<% } %>
										<% if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)) { %>
											<logic:equal name="OCAPUsuariosForm" property="enPeriodoValCc"
												value="<%=Constantes.SI%>">
												<logic:notEqual name="meritoExpmc" property="BInvalidado"	value="<%=Constantes.SI%>">
													<div class="botonAccion">
														<span> <a
															href="OCAPMeritos.do?accion=irEliminarMerito&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nombre=<bean:write name="meritoExpmc" property="DTituloCodificado"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&CEIS=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
																<span>Invalidar M&eacute;rito</span>
														</a>
														</span>
													</div>
												</logic:notEqual>
											</logic:equal>
										<% } %>
										<logic:equal name="meritoExpmc" property="BInvalidado"
											value="<%=Constantes.SI%>">
											<div class="botonAccion">
												<span> <a
													href="OCAPMeritos.do?accion=irEliminarMerito&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nombre=<bean:write name="meritoExpmc" property="DTituloCodificado"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&CEIS=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
														<img src="imagenes/imagenes_ocap/icono_eliminar.gif"
														alt="Volver a Admitir  M&eacute;rito" /> <span
														title="Volver a Admitir  M&eacute;rito"> Invalidado</span>
												</a>
												</span>
											</div>
										</logic:equal>
									</logic:equal> <logic:notEqual name="estado"
										value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
										<logic:equal name="meritoExpmc" property="BInvalidado"
											value="<%=Constantes.SI%>">
											<div class="botonAccion">
												<span>Invalidado</span>
											</div>
										</logic:equal>
									</logic:notEqual></td>
								<td class="icono">
									<div class="botonAccion">
										<logic:equal name="permisoVerCeis" value="<%=Constantes.SI%>">
											<logic:equal name="meritoExpmc" property="BPdteAclarar"
												value="<%=Constantes.SI%>">
												<span><img
													src="imagenes/imagenes_ocap/icono_aclaracion.gif"
													alt="Pendiente de Aclaraci&oacute;n" /></span>
											</logic:equal>
										</logic:equal>
										<span> <logic:notEqual name="tiposMeritos"
												property="CTipoMerito"
												value="<%=Constantes.MC_DOCENCIA_POST%>">
												<a
													href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="tiposMeritosOpc" property="CDefmeritoId"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=<bean:write name="estado"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
													<img src="imagenes/imagenes_ocap/icono_modificar.gif"
													alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
												</a>
											</logic:notEqual> <logic:equal name="tiposMeritos" property="CTipoMerito"
												value="<%=Constantes.MC_DOCENCIA_POST%>">
												<a
													href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="tiposMeritosOpc" property="CDefmeritoId"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=<bean:write name="estado"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
													<img src="imagenes/imagenes_ocap/icono_modificar.gif"
													alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
												</a>
											</logic:equal>
										</span>
									</div>
								</td>
								<td class="icono"><logic:equal name="estado"
										value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
										<% if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS)) { %>
											<logic:equal name="OCAPUsuariosForm" property="enPeriodoValMc"
												value="<%=Constantes.SI%>">
												<logic:notEqual name="meritoExpmc" property="BInvalidado"
													value="<%=Constantes.SI%>">
													<div class="botonAccion">
														<a 
														title="<%= meritoExpmc.getDMotivosCeis()%>" 
															href="OCAPMeritos.do?accion=irModificarCreditoMerito&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nCred=<bean:write name="meritoExpmc" property="NCreditos"/>&nCredCeis=<bean:write name="meritoExpmc" property="NCredCeis"/>&nCredCc=<bean:write name="meritoExpmc" property="NCredCc"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&nombre=<bean:write name="meritoExpmc" property="DTituloCodificado"/>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&CTipo=<bean:write name="OCAPUsuariosForm" property="CTipo"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
															<img src="imagenes/editar.gif" 
															alt="Modificar cr&eacute;ditos"> <span>Modificar
																cr&eacute;dito</span>
														</a>
													</div>
												</logic:notEqual>
											</logic:equal>
										<% } %>
										<% if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)) { %>
											<logic:equal name="OCAPUsuariosForm" property="enPeriodoValCc"
												value="<%=Constantes.SI%>">
												<logic:notEqual name="meritoExpmc" property="BInvalidado"
													value="<%=Constantes.SI%>">
													<div class="botonAccion">
														<a 
														title="<%= meritoExpmc.getDMotivosCc()%>" 
															href="OCAPMeritos.do?accion=irModificarCreditoMerito&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nCred=<bean:write name="meritoExpmc" property="NCreditos"/>&nCredCeis=<bean:write name="meritoExpmc" property="NCredCeis"/>&nCredCc=<bean:write name="meritoExpmc" property="NCredCc"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&nombre=<bean:write name="meritoExpmc" property="DTituloCodificado"/>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&CTipo=<bean:write name="OCAPUsuariosForm" property="CTipo"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
															<img src="imagenes/editar.gif" 
															alt="Modificar cr&eacute;ditos"> <span>Modificar
																cr&eacute;dito</span>
														</a>
													</div>
												</logic:notEqual>
											</logic:equal>
										<% } %>
									</logic:equal></td>
								<% } else if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){%>
								<td class="icono"><logic:equal name="OCAPUsuariosForm"
										property="CTipo" value="<%=Constantes.FASE_VALIDACION_CC%>">
										<logic:equal name="meritoExpmc" property="BInvalidadoCeis"
											value="<%=Constantes.SI%>">
											<div class="botonAccion">
												<span>Invalidado</span>
											</div>
										</logic:equal>
									</logic:equal> <logic:equal name="OCAPUsuariosForm" property="CTipo"
										value="<%=Constantes.FASE_CA%>">
										<logic:equal name="meritoExpmc" property="BInvalidadoCc"
											value="<%=Constantes.SI%>">
											<div class="botonAccion">
												<span>Invalidado</span>
											</div>
										</logic:equal>
									</logic:equal></td>
								<td class="icono">
									<div class="botonAccion">
										<span> <logic:notEqual name="tiposMeritos"
												property="CTipoMerito"
												value="<%=Constantes.MC_DOCENCIA_POST%>">
												<a
													href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="tiposMeritosOpc" property="CDefmeritoId"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=<bean:write name="estado"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
													<img src="imagenes/imagenes_ocap/icono_modificar.gif"
													alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
												</a>
											</logic:notEqual> <logic:equal name="tiposMeritos" property="CTipoMerito"
												value="<%=Constantes.MC_DOCENCIA_POST%>">
												<a
													href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAuxOpc%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="tiposMeritosOpc" property="CDefmeritoId"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=<bean:write name="estado"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
													<img src="imagenes/imagenes_ocap/icono_modificar.gif"
													alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
												</a>
											</logic:equal>
										</span>
									</div>
								</td>
								<% } %>
							</logic:notEqual>
							<td class="primero">
								<div class="botonAccion">
								
								<%--OCAP-80 --%>
								  <logic:equal name="permisoUsuarioVerCreditos"	value="<%=Constantes.SI%>">
							    
							     <div class="bloqueDato">
										<span class="titulo">Usuario: <bean:write name="meritoExpmc" property="NCreditos" /></span>
									</div>
									<div class="bloqueDato">
										<span class="titulo">CEIS: <bean:write name="meritoExpmc" property="NCredCeis" /></span>
									</div>
									<div class="bloqueDato">
										<span class="titulo">CC: <bean:write name="meritoExpmc"	property="NCredCc" /></span>
									</div>
							    </logic:equal>
							     
									<% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())){ %>
									<div class="bloqueDato">
										<span class="titulo">Usuario: <bean:write
												name="meritoExpmc" property="NCreditos" /></span>
									</div>
									<div class="bloqueDato">
										<span class="titulo">CEIS: <bean:write
												name="meritoExpmc" property="NCredCeis" /></span>
									</div>
									<%}else if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())){%>
									<logic:equal name="OCAPUsuariosForm" property="CTipo"
										value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
										<div class="bloqueDato">
											<span class="titulo">Usuario: <bean:write
													name="meritoExpmc" property="NCreditos" /></span>
										</div>
										<div class="bloqueDato">
											<span class="titulo">CEIS: <bean:write
													name="meritoExpmc" property="NCredCeis" /></span>
										</div>
										<logic:notEqual name="OCAPUsuariosForm" property="enPeriodoValMc" value="<%=Constantes.SI%>">
										<div class="bloqueDato">
											<span class="titulo">CC: <bean:write
													name="meritoExpmc" property="NCredCc" /></span>
										</div>
										</logic:notEqual>
									</logic:equal>
									<logic:notEqual name="OCAPUsuariosForm" property="CTipo"
										value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
										<div class="bloqueDato">
											<span class="titulo">Usuario: <bean:write
													name="meritoExpmc" property="NCreditos" /></span>
										</div>
										<div class="bloqueDato">
											<span class="titulo">CEIS: <bean:write
													name="meritoExpmc" property="NCredCeis" /></span>
										</div>
										<logic:notEqual name="OCAPUsuariosForm" property="enPeriodoValMc" value="<%=Constantes.SI%>">
										<div class="bloqueDato">
											<span class="titulo">CC: <bean:write
													name="meritoExpmc" property="NCredCc" /></span>
										</div>
										</logic:notEqual>
									</logic:notEqual>
									<%}else if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){%>
									<logic:equal name="OCAPUsuariosForm" property="CTipo"
										value="<%=Constantes.FASE_VALIDACION%>">
										<span class="creditos"><bean:write name="meritoExpmc"
												property="NCreditos" /> cr&eacute;ditos</span>
									</logic:equal>
									<logic:equal name="OCAPUsuariosForm" property="CTipo"
										value="<%=Constantes.FASE_VALIDACION_CC%>">
										<span class="creditos"><bean:write name="meritoExpmc"
												property="NCredCeis" /> cr&eacute;ditos</span>
									</logic:equal>
									<logic:equal name="OCAPUsuariosForm" property="CTipo"
										value="<%=Constantes.FASE_CA%>">
										<span class="creditos"><bean:write name="meritoExpmc"
												property="NCredCc" /> cr&eacute;ditos</span>
									</logic:equal>
									<%}else{%>
									<%--OCAP-80 --%>
									  <logic:equal name="permisoUsuarioVerCreditos"	value="<%=Constantes.NO%>">
									
									<span class="creditos"><bean:write name="meritoExpmc"
											property="NCreditos" /> cr&eacute;ditos</span>
											</logic:equal>
									<%}%>
								</div>
							</td>
							<logic:notEqual name="permisoModificar"
								value="<%=Constantes.SI%>">
								<% if (Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())){%>
								<logic:equal name="permisoVerCeis" value="<%=Constantes.SI%>">
									<logic:notEqual name="estado"
										value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
										<td class="icono">
											<div class="botonAccion">
												<logic:equal name="meritoExpmc" property="BPdteAclarar"
													value="<%=Constantes.SI%>">
													<span><img
														src="imagenes/imagenes_ocap/icono_aclaracion.gif"
														alt="Pendiente de Aclaraci&oacute;n" /></span>
												</logic:equal>
												<logic:equal name="meritoExpmc" property="BInvalidadoCc"
													value="<%=Constantes.SI%>">
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
											<logic:equal name="meritoExpmc" property="BPdteAclarar"
												value="<%=Constantes.SI%>">
												<span><img
													src="imagenes/imagenes_ocap/icono_aclaracion.gif"
													alt="Pendiente de Aclaraci&oacute;n" /></span>
											</logic:equal>
											<logic:equal name="meritoExpmc" property="BInvalidadoCc"
												value="<%=Constantes.SI%>">
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
				<div class="espaciadorPeq"></div>
			</logic:notEmpty>
			<!-- tamanoListaExpmcOpc != 0 -->
		</logic:iterate>
	</logic:iterate>
</logic:notEmpty>
<!-- Fin Iteracion Lista Meritos Opcionales -->