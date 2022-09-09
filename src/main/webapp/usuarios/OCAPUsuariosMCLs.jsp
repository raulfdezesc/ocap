<%@ page contentType="text/html;charset=ISO-8859-1"	pageEncoding="windows-1252"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.ot.expedienteMC.OCAPExpedientemcOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.meritosCurriculares.OCAPMeritoscurricularesOT"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<% JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO); %>

<logic:notEmpty name="OCAPUsuariosForm" property="listaMeritos">

 <logic:iterate id="tiposMeritos" name="OCAPUsuariosForm" property="listaMeritos" type="OCAPMeritoscurricularesOT">

   <bean:define id="requestTiposMeritos" name="tiposMeritos" toScope="request" type="OCAPMeritoscurricularesOT" />
   <bean:define id="identificadorExpediente" name="OCAPUsuariosForm" property="CExpId" toScope="request" />
   <bean:define id="meritosOpcionalesFlag" value="N" toScope="request" />
	
		
		<% String tipoMeritoAux="";
                           if (tiposMeritos.getDNombre().indexOf("+")!=-1) {
                              tipoMeritoAux = tiposMeritos.getDNombre().replaceAll("\\+",Constantes.MAS);
                           } else {
                              tipoMeritoAux = tiposMeritos.getDNombre();
                         }                       
                        %>
                        
	
		<jsp:include page="OCAPUsuariosMCLsBotonMeritos.jsp" />

    	
		<logic:equal name="permisoModificar" value="<%=Constantes.SI%>">
				
				<jsp:include page="OCAPUsuariosMCLsPermisoModificar.jsp" />
					
		</logic:equal>
		
		</span>
		<div class="limpiadora"></div>
		<logic:notEmpty name="tiposMeritos" property="listaExpmc">
			<logic:equal name="tiposMeritos" property="CTipoMerito"	value="<%=Constantes.MC_TALLER%>">
				<div class="lineaBajaM"	style="width: 80%; margin-left: 50px; margin-bottom: 10px;">
					<span>Acreditados</span>
				</div>
			</logic:equal>
			<table>
				<logic:iterate id="meritoExpmc" name="tiposMeritos"	property="listaExpmc" type="OCAPExpedientemcOT">
					<tr class="tablaMC">
						<td class="primero"><span><bean:write name="meritoExpmc" property="DTitulo" /></span></td>
						<logic:equal name="permisoModificar" value="<%=Constantes.SI%>">
							<td class="icono">
								<div class="botonAccion">
									<span> <a
										href="OCAPMeritos.do?accion=irEliminarMerito&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nombre=<bean:write name="meritoExpmc" property="DTituloCodificado"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>">
											<img src="imagenes/imagenes_ocap/icono_eliminar.gif"
											alt="Eliminar  M&eacute;rito" /> <span>Eliminar</span>
									</a>
									</span>
								</div>
							</td>
							<td class="icono">
								<div class="botonAccion">
									<span> <logic:notEqual name="tiposMeritos" property="CTipoMerito"  value="<%=Constantes.MC_DOCENCIA_POST%>">
											<logic:notEqual name="tiposMeritos" property="CTipoMerito" value="<%=Constantes.MC_BIBLIOGRAFICAS%>">
												<a
													href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>&personal=<bean:write name="OCAPUsuariosForm" property="CPersonalId"/>">
													<img src="imagenes/imagenes_ocap/icono_modificar.gif"
													alt="Modificar  M&eacute;rito" /> <span>Modificar</span>
												</a>
											</logic:notEqual>
										</logic:notEqual>
									</span>
								</div>
							</td>
						</logic:equal>
						<logic:notEqual name="permisoModificar" value="<%=Constantes.SI%>">
							<% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())){%>
							<td class="icono">
									<logic:equal name="estado" value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>"> 
										<% if ( Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) ){%>
											<logic:equal name="OCAPUsuariosForm" property="enPeriodoValMc"	value="<%=Constantes.SI%>">
												<logic:notEqual name="meritoExpmc" property="BInvalidado"	value="<%=Constantes.SI%>">
													<div class="botonAccion">
														<span> <a
															href="OCAPMeritos.do?accion=irEliminarMerito&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nombre=<bean:write name="meritoExpmc" property="DTituloCodificado"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&CEIS=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
																<span>Invalidar M&eacute;rito</span>
														</a>
														</span>
													</div>
												</logic:notEqual>
											</logic:equal>
										<% } %>
										<% if ( Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()) ){%>
											<logic:equal name="OCAPUsuariosForm" property="enPeriodoValCc" value="<%=Constantes.SI%>">
												<logic:notEqual name="meritoExpmc" property="BInvalidado"  value="<%=Constantes.SI%>">
													<div class="botonAccion">
														<span> <a
															href="OCAPMeritos.do?accion=irEliminarMerito&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nombre=<bean:write name="meritoExpmc" property="DTituloCodificado"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&CEIS=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
																<span>Invalidar M&eacute;rito</span>
														</a>
														</span>
													</div>
												</logic:notEqual>
											</logic:equal>
										<% } %>
										<logic:equal name="meritoExpmc" property="BInvalidado"	value="<%=Constantes.SI%>">
											<div class="botonAccion">
												<span> <a
													href="OCAPMeritos.do?accion=irEliminarMerito&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nombre=<bean:write name="meritoExpmc" property="DTituloCodificado"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&CEIS=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
														<img src="imagenes/imagenes_ocap/icono_eliminar.gif"
														alt="Volver a Admitir  M&eacute;rito" /> <span
														title="Volver a Admitir  M&eacute;rito"> Invalidado</span>
												</a>
												</span>
											</div>
										</logic:equal>
									  </logic:equal>   
									<logic:notEqual name="estado" value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
										<logic:equal name="meritoExpmc" property="BInvalidado" value="<%=Constantes.SI%>">
										<div class="botonAccion">
											<span>Invalidado</span>
										</div>
										</logic:equal>
									</logic:notEqual></td>
							<td class="icono">
								<div class="botonAccion">
									<span> 
									<logic:notEqual name="tiposMeritos" property="CTipoMerito" value="<%=Constantes.MC_DOCENCIA_POST%>">
											<a
												href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=<bean:write name="estado"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
												<img src="imagenes/imagenes_ocap/icono_modificar.gif"
												alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
											</a>
									</logic:notEqual> 
									<logic:equal name="tiposMeritos" property="CTipoMerito"	value="<%=Constantes.MC_DOCENCIA_POST%>">
											<a
												href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=<bean:write name="estado"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
												<img src="imagenes/imagenes_ocap/icono_modificar.gif"
												alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
											</a>
									</logic:equal>
									</span>
									<logic:equal name="meritoExpmc" property="BPdteAclarar"	value="<%=Constantes.SI%>">
										<span><img src="imagenes/imagenes_ocap/icono_aclaracion.gif" alt="Pendiente de Aclaraci&oacute;n" /></span>
									</logic:equal>
								</div>
							</td>
							<td class="icono" nowrap="nowrap">
								<logic:equal name="estado"	value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
									<% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) ) { %>
										<logic:equal name="OCAPUsuariosForm" property="enPeriodoValMc" value="<%=Constantes.SI%>">
											<logic:notEqual name="meritoExpmc" property="BInvalidado" value="<%=Constantes.SI%>">
									
												<div class="botonAccion">
												
													<span> <a title="<%= meritoExpmc.getDMotivosCeis()%>" 
													 href="OCAPMeritos.do?accion=irModificarCreditoMerito&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nCred=<bean:write name="meritoExpmc" property="NCreditos"/>&nCredCeis=<bean:write name="meritoExpmc" property="NCredCeis"/>&nCredCc=<bean:write name="meritoExpmc" property="NCredCc"/>&tipoMerito=<%=tipoMeritoAux%>&nombre=<bean:write name="meritoExpmc" property="DTituloCodificado"/>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&CTipo=<bean:write name="OCAPUsuariosForm" property="CTipo"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
															<img src="imagenes/editar.gif" 
															alt="Modificar cr&eacute;ditos"> <span>Modificar cr&eacute;dito</span>
													</a>
													</span>
												</div>
											</logic:notEqual>
										</logic:equal>
									<% } %>
									<% if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()) ) { %>
										<logic:equal name="OCAPUsuariosForm" property="enPeriodoValCc" value="<%=Constantes.SI%>">
											<logic:notEqual name="meritoExpmc" property="BInvalidado" value="<%=Constantes.SI%>">
												<div class="botonAccion">
													<span>   <a title="<%= meritoExpmc.getDMotivosCc()%>" 
														href="OCAPMeritos.do?accion=irModificarCreditoMerito&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nCred=<bean:write name="meritoExpmc" property="NCreditos"/>&nCredCeis=<bean:write name="meritoExpmc" property="NCredCeis"/>&nCredCc=<bean:write name="meritoExpmc" property="NCredCc"/>&tipoMerito=<%=tipoMeritoAux%>&nombre=<bean:write name="meritoExpmc" property="DTituloCodificado"/>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&CTipo=<bean:write name="OCAPUsuariosForm" property="CTipo"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
															<img src="imagenes/editar.gif"
															alt="Modificar cr&eacute;ditos"><span>Modificar cr&eacute;dito</span>
													</a>
													</span>
												</div>
											</logic:notEqual>
										</logic:equal>
									<% } %>
								</logic:equal></td>
							<% } else if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){%>
							<td class="icono">
							<logic:equal name="OCAPUsuariosForm" property="CTipo" value="<%=Constantes.FASE_VALIDACION_CC%>">
								<logic:equal name="meritoExpmc" property="BInvalidadoCeis" value="<%=Constantes.SI%>">
										<div class="botonAccion">
											<span>Invalidado</span>
										</div>
								</logic:equal>
							</logic:equal>
							<logic:equal name="OCAPUsuariosForm" property="CTipo" value="<%=Constantes.FASE_CA%>">
									<logic:equal name="meritoExpmc" property="BInvalidadoCc" value="<%=Constantes.SI%>">
										<div class="botonAccion">
											<span>Invalidado</span>
										</div>
									</logic:equal>
							</logic:equal></td>
							<td class="icono">
							 <div class="botonAccion">
								<span>
									 <logic:notEqual name="tiposMeritos" property="CTipoMerito" value="<%=Constantes.MC_DOCENCIA_POST%>">
										<a href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=<bean:write name="estado"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
												<img src="imagenes/imagenes_ocap/icono_modificar.gif" alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
										</a>
									</logic:notEqual> 
									<logic:equal name="tiposMeritos" property="CTipoMerito" value="<%=Constantes.MC_DOCENCIA_POST%>">
										<a href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=<bean:write name="estado"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
												<img src="imagenes/imagenes_ocap/icono_modificar.gif" alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
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
							     
							
								<% if(Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) { %>
								<div class="bloqueDato">
										<span class="titulo">Usuario: <bean:write name="meritoExpmc" property="NCreditos" /></span>
								</div>
								<logic:notEqual name="periodoMC" value="<%=Constantes.SI%>">
									<div class="bloqueDato">
										<span class="titulo">CEIS: <bean:write name="meritoExpmc" property="NCredCeis" /></span>
									</div>
								</logic:notEqual>
								<% } else if(Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) { %>
									<div class="bloqueDato">
										<span class="titulo">Usuario: <bean:write name="meritoExpmc" property="NCreditos" /></span>
									</div>
								<logic:notEqual name="periodoMC" value="<%=Constantes.SI%>">
									<div class="bloqueDato">
										<span class="titulo">CEIS: <bean:write name="meritoExpmc" property="NCredCeis" /></span>
									</div>
									<logic:notEqual name="OCAPUsuariosForm" property="enPeriodoValMc" value="<%=Constantes.SI%>">
										<div class="bloqueDato">
											<span class="titulo">CC: <bean:write name="meritoExpmc"	property="NCredCc" /></span>
										</div>
									</logic:notEqual>
								</logic:notEqual>
								<% } else if(Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) { %>
								<logic:equal name="OCAPUsuariosForm" property="CTipo"value="<%=Constantes.FASE_VALIDACION%>">
									<span class="creditos"><bean:write name="meritoExpmc" property="NCreditos" /> cr&eacute;ditos</span>
								</logic:equal>
								<logic:equal name="OCAPUsuariosForm" property="CTipo" value="<%=Constantes.FASE_VALIDACION_CC%>">
									<span class="creditos"><bean:write name="meritoExpmc" property="NCredCeis" /> cr&eacute;ditos</span>
								</logic:equal>
								<logic:equal name="OCAPUsuariosForm" property="CTipo" value="<%=Constantes.FASE_CA%>">
									<span class="creditos"><bean:write name="meritoExpmc" property="NCredCc" /> cr&eacute;ditos</span>
								</logic:equal>
								<% } else { %>
								    <%--OCAP-80 --%>
								  <logic:equal name="permisoUsuarioVerCreditos"	value="<%=Constantes.NO%>">									
								<span class="creditos"><bean:write name="meritoExpmc" property="NCreditos" /> cr&eacute;ditos</span>
								</logic:equal>
								
								<% } %>
							</div>
						</td>
						<logic:notEqual name="permisoModificar" value="<%=Constantes.SI%>">
							<%if (Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol() )  ){%>
							<logic:equal name="permisoVerCeis" value="<%=Constantes.SI%>">
								<logic:notEqual name="estado"	value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
									<td class="icono">
										<div class="botonAccion">
											<logic:equal name="meritoExpmc" property="BPdteAclarar"
												value="<%=Constantes.SI%>">
												<span><img	src="imagenes/imagenes_ocap/icono_aclaracion.gif" alt="Pendiente de Aclaraci&oacute;n" /></span>
											</logic:equal>
											<logic:equal name="meritoExpmc" property="BInvalidadoCc" value="<%=Constantes.SI%>">
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
										<logic:equal name="meritoExpmc" property="BPdteAclarar"	value="<%=Constantes.SI%>">
											<span><img src="imagenes/imagenes_ocap/icono_aclaracion.gif" alt="Pendiente de Aclaraci&oacute;n" /></span>
										</logic:equal>
										<logic:equal name="meritoExpmc" property="BInvalidadoCc" value="<%=Constantes.SI%>">
											<span>Invalidado</span>
										</logic:equal>
									</div>
								</td>
							</logic:equal>
							<% } %>
						</logic:notEqual>
						<logic:equal name="permisoModificar" value="<%=Constantes.SI%>">
							<%-- enviar a opcionales, solo si no es formacion --%>
							<logic:notEqual name="meritoSelecc" property="CDefmeritoId"	value="<%=Constantes.DEF_MERITO_FORMACION%>">
								<td class="icono">
									<div class="botonAccion">
										<span><a
											href="OCAPMeritos.do?accion=enviarMeritoAOpcionales&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>">
												<img src="imagenes/imagenes_ocap/icono_opcionales.gif"
												alt="Enviar  M&eacute;rito a Opcionales" /> <span>A	Opcionales</span>
											</a>
										</span>
									</div>
								</td>
							</logic:notEqual>
						</logic:equal>
					</tr>
				</logic:iterate>
			</table>
			<div class="espaciador"></div>
			<div class="espaciador"></div>
		</logic:notEmpty>
		
		<logic:notEmpty name="tiposMeritos" property="listaExpmcCTSP">
			<div class="lineaBajaM"	style="width: 80%; margin-left: 50px; margin-bottom: 10px;">
				<span>No Acreditados - Modalidad Presencial</span>
			</div>
			<table>
				<logic:iterate id="meritoExpmcCTSP" name="tiposMeritos"	property="listaExpmcCTSP" type="OCAPExpedientemcOT" indexId="index">
					<tr class="tablaMC">
						<td class="primero"><span><bean:write name="meritoExpmcCTSP" property="DTitulo" /></span></td>
						<logic:equal name="permisoModificar" value="<%=Constantes.SI%>">
							<td class="icono">
								<div class="botonAccion">
									<span>
									<a href="OCAPMeritos.do?accion=irEliminarMerito&idMer=<bean:write name="meritoExpmcCTSP" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nombre=<bean:write name="meritoExpmcCTSP" property="DTituloCodificado"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>">
											<img src="imagenes/imagenes_ocap/icono_eliminar.gif" alt="Eliminar  M&eacute;rito" /> <span>Eliminar</span>
									</a>
									</span>
								</div>
							</td>
							<td class="icono">
								<div class="botonAccion">
									<span><a
										href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmcCTSP" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>&personal=<bean:write name="OCAPUsuariosForm" property="CPersonalId"/>">
											<img src="imagenes/imagenes_ocap/icono_modificar.gif" alt="Modificar  M&eacute;rito" /> <span>Modificar</span>
										</a>
									</span>
								</div>
							</td>
						</logic:equal>
						<logic:notEqual name="permisoModificar" value="<%=Constantes.SI%>">
							<% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())){%>
							<td class="icono">
							<logic:equal name="estado" value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>"> 
									<logic:notEqual name="meritoExpmcCTSP" property="BInvalidado" value="<%=Constantes.SI%>">
										<div class="botonAccion">
											<span><a
												href="OCAPMeritos.do?accion=irEliminarMerito&idMer=<bean:write name="meritoExpmcCTSP" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nombre=<bean:write name="meritoExpmcCTSP" property="DTituloCodificado"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&CEIS=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
													<span>Invalidar M&eacute;rito</span>
											</a>
											</span>
										</div>
									</logic:notEqual>
									<logic:equal name="meritoExpmcCTSP" property="BInvalidado"	value="<%=Constantes.SI%>">
										<div class="botonAccion">
											<span><a href="OCAPMeritos.do?accion=irEliminarMerito&idMer=<bean:write name="meritoExpmcCTSP" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nombre=<bean:write name="meritoExpmcCTSP" property="DTituloCodificado"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&CEIS=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
													<img src="imagenes/imagenes_ocap/icono_eliminar.gif" alt="Volver a Admitir  M&eacute;rito" />
													<span title="Volver a Admitir  M&eacute;rito"> Invalidado</span>
											</a>
											</span>
										</div>
									</logic:equal>
							</logic:equal> 
								
							 <logic:notEqual name="estado" value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
									<logic:equal name="meritoExpmcCTSP" property="BInvalidado"	value="<%=Constantes.SI%>">
										<div class="botonAccion">
											<span>Invalidado</span>
										</div>
									</logic:equal>
								</logic:notEqual>
								</td>
							<td class="icono">
								<div class="botonAccion">
									<span> <a href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmcCTSP" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=<bean:write name="estado"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
											<img src="imagenes/imagenes_ocap/icono_modificar.gif"	alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
									</a>
									</span>
									<logic:equal name="meritoExpmcCTSP" property="BPdteAclarar"	value="<%=Constantes.SI%>">
										<span><img src="imagenes/imagenes_ocap/icono_aclaracion.gif" alt="Pendiente de Aclaraci&oacute;n" /></span>
									</logic:equal>
								</div>
							</td>
							
							
							
							<td class="icono" nowrap="nowrap">
								<logic:equal name="estado"	value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
									<% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) ) { %>
										<logic:equal name="OCAPUsuariosForm" property="enPeriodoValMc" value="<%=Constantes.SI%>">
											<logic:notEqual name="meritoExpmcCTSP" property="BInvalidado" value="<%=Constantes.SI%>">
									
												<div class="botonAccion">
												
													<span> <a title="<%= meritoExpmcCTSP.getDMotivosCeis()%>" 
													 href="OCAPMeritos.do?accion=irModificarCreditoMerito&idMer=<bean:write name="meritoExpmcCTSP" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nHoras=<bean:write name="meritoExpmcCTSP" property="NHoras"/>&tipoMerito=<%=tipoMeritoAux%>&nombre=<bean:write name="meritoExpmcCTSP" property="DTituloCodificado"/>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&CTipo=<bean:write name="OCAPUsuariosForm" property="CTipo"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
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
											<logic:notEqual name="meritoExpmcCTSP" property="BInvalidado" value="<%=Constantes.SI%>">
												<div class="botonAccion">
													<span>   <a title="<%= meritoExpmcCTSP.getDMotivosCc()%>" 
														href="OCAPMeritos.do?accion=irModificarCreditoMerito&idMer=<bean:write name="meritoExpmcCTSP" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nHoras=<bean:write name="meritoExpmcCTSP" property="NHoras"/>&tipoMerito=<%=tipoMeritoAux%>&nombre=<bean:write name="meritoExpmcCTSP" property="DTituloCodificado"/>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&CTipo=<bean:write name="OCAPUsuariosForm" property="CTipo"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
															<img src="imagenes/editar.gif"
															alt="Modificar cr&eacute;ditos"><span>Modificar horas</span>
													</a>
													</span>
												</div>
											</logic:notEqual>
										</logic:equal>
									<% } %>
								</logic:equal></td>
								
								
								
								
							<% } else if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){%>
							<td class="icono">
								<logic:equal name="OCAPUsuariosForm" property="CTipo" value="<%=Constantes.FASE_VALIDACION_CC%>">
									<logic:equal name="meritoExpmcCTSP" property="BInvalidadoCeis"	value="<%=Constantes.SI%>">
										<div class="botonAccion">
											<span>Invalidado</span>
										</div>
									</logic:equal>
								</logic:equal> 
								<logic:equal name="OCAPUsuariosForm" property="CTipo" value="<%=Constantes.FASE_CA%>">
									<logic:equal name="meritoExpmcCTSP" property="BInvalidadoCc" value="<%=Constantes.SI%>">
										<div class="botonAccion">
											<span>Invalidado</span>
										</div>
									</logic:equal>
								</logic:equal></td>
							<td class="icono">
								<div class="botonAccion">
									<span> <a
										href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmcCTSP" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=<bean:write name="estado"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
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
								<span class="creditos"><bean:write name="meritoExpmcCTSP"	property="NHoras" /> horas</span>
							</div>
						</td>
						<logic:notEqual name="permisoModificar" value="<%=Constantes.SI%>">
							<%if (Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol() )){%>
							<logic:equal name="permisoVerCeis" value="<%=Constantes.SI%>">
								<logic:notEqual name="estado"	value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
									<td class="icono">
										<div class="botonAccion">
											<logic:equal name="meritoExpmcCTSP" property="BPdteAclarar"	value="<%=Constantes.SI%>">
												<span><img src="imagenes/imagenes_ocap/icono_aclaracion.gif"	alt="Pendiente de Aclaraci&oacute;n" /></span>
											</logic:equal>
											<logic:equal name="meritoExpmcCTSP" property="BInvalidadoCc" value="<%=Constantes.SI%>">
												<span>Invalidado</span>
											</logic:equal>
										</div>
									</td>
								</logic:notEqual>
							</logic:equal>
							<%} else if (!Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) && !Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()) && !Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){%>
							<td class="icono">
								<div class="botonAccion">
									<logic:equal name="meritoExpmcCTSP" property="BPdteAclarar"	value="<%=Constantes.SI%>">
										<span><img	src="imagenes/imagenes_ocap/icono_aclaracion.gif"	alt="Pendiente de Aclaraci&oacute;n" /></span>
									</logic:equal>
									<logic:equal name="meritoExpmcCTSP" property="BInvalidadoCc" value="<%=Constantes.SI%>">
										<span>Invalidado</span>
									</logic:equal>
								</div>
							</td>
							<% } %>
						</logic:notEqual>
					</tr>
				</logic:iterate>
			</table>			
			<jsp:include page="OCAPUsuariosMCLsP1.jsp" />					
		</logic:notEmpty>		
		<logic:notEmpty name="tiposMeritos" property="listaExpmcCTSNP">
			<div class="lineaBajaM"	style="width: 80%; margin-left: 50px; margin-bottom: 10px;">
				<span>No Acreditados - Modalidad No Presencial</span>
			</div>
			<table>
				<logic:iterate id="meritoExpmcCTSNP" name="tiposMeritos" property="listaExpmcCTSNP" type="OCAPExpedientemcOT">
					<tr class="tablaMC">
						<td class="primero"><span><bean:write name="meritoExpmcCTSNP" property="DTitulo" /></span></td>
						<logic:equal name="permisoModificar" value="<%=Constantes.SI%>">
							<td class="icono">
								<div class="botonAccion">
									<span> <a
										href="OCAPMeritos.do?accion=irEliminarMerito&idMer=<bean:write name="meritoExpmcCTSNP" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nombre=<bean:write name="meritoExpmcCTSNP" property="DTituloCodificado"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>">
											<img src="imagenes/imagenes_ocap/icono_eliminar.gif"
											alt="Eliminar  M&eacute;rito" /> <span>Eliminar</span>
									</a>
									</span>
								</div>
							</td>
							<td class="icono">
								<div class="botonAccion">
									<span> <a
										href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmcCTSNP" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>&personal=<bean:write name="OCAPUsuariosForm" property="CPersonalId"/>">
											<img src="imagenes/imagenes_ocap/icono_modificar.gif"
											alt="Modificar  M&eacute;rito" /> <span>Modificar</span>
									</a>
									</span>
								</div>
							</td>
						</logic:equal>
						<logic:notEqual name="permisoModificar" value="<%=Constantes.SI%>">
							<% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())){%>
							<td class="icono">
							<logic:equal name="estado" value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>"> 
									<logic:notEqual name="meritoExpmcCTSNP" property="BInvalidado" value="<%=Constantes.SI%>">
										<div class="botonAccion">
											<span>
												 <a href="OCAPMeritos.do?accion=irEliminarMerito&idMer=<bean:write name="meritoExpmcCTSNP" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nombre=<bean:write name="meritoExpmcCTSNP" property="DTituloCodificado"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&CEIS=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
													<span>Invalidar M&eacute;rito</span>
												</a>
											</span>
										</div>
									</logic:notEqual>
									<logic:equal name="meritoExpmcCTSNP" property="BInvalidado"	value="<%=Constantes.SI%>">
										<div class="botonAccion">
											<span> <a
												href="OCAPMeritos.do?accion=irEliminarMerito&idMer=<bean:write name="meritoExpmcCTSNP" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nombre=<bean:write name="meritoExpmcCTSNP" property="DTituloCodificado"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&CEIS=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
													<img src="imagenes/imagenes_ocap/icono_eliminar.gif"
													alt="Volver a Admitir  M&eacute;rito" /> <span
													title="Volver a Admitir  M&eacute;rito"> Invalidado</span>
											</a>
											</span>
										</div>
									</logic:equal>
								</logic:equal>
								 <logic:notEqual name="estado" value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
									<logic:equal name="meritoExpmcCTSNP" property="BInvalidado"	value="<%=Constantes.SI%>">
										<div class="botonAccion">
											<span>Invalidado</span>
										</div>
									</logic:equal>
								</logic:notEqual> 
								</td>
							<td class="icono">
								<div class="botonAccion">
									<span> <a
										href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmcCTSNP" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=<bean:write name="estado"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
											<img src="imagenes/imagenes_ocap/icono_modificar.gif"
											alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
									</a>
									</span>
									<logic:equal name="meritoExpmcCTSNP" property="BPdteAclarar" value="<%=Constantes.SI%>">
										<span><img	src="imagenes/imagenes_ocap/icono_aclaracion.gif"	alt="Pendiente de Aclaraci&oacute;n" /></span>
									</logic:equal>
								</div>
							</td>
							
	
							<td class="icono" nowrap="nowrap">
								<logic:equal name="estado"	value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
									<% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) ) { %>
										<logic:equal name="OCAPUsuariosForm" property="enPeriodoValMc" value="<%=Constantes.SI%>">
											<logic:notEqual name="meritoExpmcCTSNP" property="BInvalidado" value="<%=Constantes.SI%>">
									
												<div class="botonAccion">
												
													<span> <a title="<%= meritoExpmcCTSNP.getDMotivosCeis()%>" 
													 href="OCAPMeritos.do?accion=irModificarCreditoMerito&idMer=<bean:write name="meritoExpmcCTSNP" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nHoras=<bean:write name="meritoExpmcCTSNP" property="NHoras"/>&tipoMerito=<%=tipoMeritoAux%>&nombre=<bean:write name="meritoExpmcCTSNP" property="DTituloCodificado"/>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&CTipo=<bean:write name="OCAPUsuariosForm" property="CTipo"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
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
											<logic:notEqual name="meritoExpmcCTSNP" property="BInvalidado" value="<%=Constantes.SI%>">
												<div class="botonAccion">
													<span>   <a title="<%= meritoExpmcCTSNP.getDMotivosCc()%>" 
														href="OCAPMeritos.do?accion=irModificarCreditoMerito&idMer=<bean:write name="meritoExpmcCTSNP" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&nHoras=<bean:write name="meritoExpmcCTSNP" property="NHoras"/>&tipoMerito=<%=tipoMeritoAux%>&nombre=<bean:write name="meritoExpmcCTSNP" property="DTituloCodificado"/>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&CTipo=<bean:write name="OCAPUsuariosForm" property="CTipo"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>">
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
									<logic:notEqual name="meritoExpmcCTSNP" property="BInvalidado"	value="<%=Constantes.SI%>">
										<div class="botonAccion"><span></span></div>
									</logic:notEqual>
							</logic:equal></td>
							<% } else if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){%>
							<td class="icono">
							<logic:equal name="OCAPUsuariosForm" property="CTipo" value="<%=Constantes.FASE_VALIDACION_CC%>">
								<logic:equal name="meritoExpmcCTSNP" property="BInvalidadoCeis"	value="<%=Constantes.SI%>">
										<div class="botonAccion">
											<span>Invalidado</span>
										</div>
								</logic:equal>
							</logic:equal>
							<logic:equal name="OCAPUsuariosForm" property="CTipo" value="<%=Constantes.FASE_CA%>">
								<logic:equal name="meritoExpmcCTSNP" property="BInvalidadoCc" value="<%=Constantes.SI%>">
										<div class="botonAccion">
											<span>Invalidado</span>
										</div>
									</logic:equal>
								</logic:equal></td>
							<td class="icono">
								<div class="botonAccion">
									<span>
										<a	href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmcCTSNP" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=<bean:write name="estado"/>&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
											<img src="imagenes/imagenes_ocap/icono_modificar.gif" alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
										</a>
									</span>
								</div>
							</td>
							<% } %>
						</logic:notEqual>
						<td class="primero">
							<div class="botonAccion">
								<span class="creditos"><bean:write	name="meritoExpmcCTSNP" property="NHoras" /> horas</span>
							</div>
						</td>
						<logic:notEqual name="permisoModificar" value="<%=Constantes.SI%>">
							<%if (Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol() )){%>
							<logic:equal name="permisoVerCeis" value="<%=Constantes.SI%>">
								<logic:notEqual name="estado" value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
									<td class="icono">
										<div class="botonAccion">
											<logic:equal name="meritoExpmcCTSNP" property="BPdteAclarar"	value="<%=Constantes.SI%>">
												<span><img src="imagenes/imagenes_ocap/icono_aclaracion.gif" alt="Pendiente de Aclaraci&oacute;n" /></span>
											</logic:equal>
											<logic:equal name="meritoExpmcCTSNP" property="BInvalidadoCc"
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
										<logic:equal name="meritoExpmcCTSNP" property="BPdteAclarar"
											value="<%=Constantes.SI%>">
											<span><img
												src="imagenes/imagenes_ocap/icono_aclaracion.gif"
												alt="Pendiente de Aclaraci&oacute;n" /></span>
										</logic:equal>
										<logic:equal name="meritoExpmcCTSNP" property="BInvalidadoCc"
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
			<jsp:include page="OCAPUsuariosMCLsP2.jsp" />			
		</logic:notEmpty>		
		<logic:notEmpty name="tiposMeritos" property="listaExpmcCTSM">
			<div class="lineaBajaM"	style="width: 80%; margin-left: 50px; margin-bottom: 10px;">
				<span>No Acreditados - Modalidad Mixta</span>
			</div>
			<jsp:include page="OCAPUsuariosMCLsMod_Mixta.jsp" />
			<jsp:include page="OCAPUsuariosMCLsP3.jsp" />
		</logic:notEmpty>		
	</logic:iterate>
</logic:notEmpty>