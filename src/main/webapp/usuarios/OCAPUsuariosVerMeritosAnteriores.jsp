<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.ot.expedienteMC.OCAPExpedientemcOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.meritosCurriculares.OCAPMeritoscurricularesOT"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<% JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO); %>

		
	<div class="contenidoMC" style="padding-left: 10px !important;">			
		<h3 style="margin-top:0px !important;">M&eacute;ritos validados en convocatorias anteriores</h3>
		<div class="recuadroM" id="n001">
			<div class="limpiadora"></div>
			
			<span class="tituloMC"> 
				<bean:write	name="OCAPMeritosAnterioresForm" property="descCateg" /> 
			</span>
			
			<!-- Lista de Meritos -->
			<logic:notEmpty name="OCAPMeritosAnterioresForm" property="listaMeritos">
 				<logic:iterate id="tiposMeritos" name="OCAPMeritosAnterioresForm" property="listaMeritos" type="OCAPMeritoscurricularesOT">
 				<bean:define id="requestTiposMeritos" name="tiposMeritos" toScope="request" type="OCAPMeritoscurricularesOT" />
 						<% String tipoMeritoAux="";
                           if (tiposMeritos.getDNombre().indexOf("+")!=-1) {
                              tipoMeritoAux = tiposMeritos.getDNombre().replaceAll("\\+",Constantes.MAS);
                           } else {
                              tipoMeritoAux = tiposMeritos.getDNombre();
                         }                       
                        %>
                        
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
						
							<% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())){%>
							<td class="icono">
										<logic:equal name="meritoExpmc" property="BInvalidado" value="<%=Constantes.SI%>">
										<div class="botonAccion" style="margin-top: 5px !important;">
											<span>Invalidado</span>
										</div>
										</logic:equal>
								</td>
							<td class="icono">
								<div class="botonAccion">
									<span> 
									<logic:notEqual name="tiposMeritos" property="CTipoMerito" value="<%=Constantes.MC_DOCENCIA_POST%>">
											<a
												href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmc" property="CExpmcId"/>&pestana=<bean:write name="OCAPMeritosAnterioresForm" property="tipoMerito"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="OCAPMeritosAnterioresForm" property="tipoMerito"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=&meritosAnteriores=Y&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
												<img src="imagenes/imagenes_ocap/icono_modificar.gif"
												alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
											</a>
									</logic:notEqual> 
									<logic:equal name="tiposMeritos" property="CTipoMerito"	value="<%=Constantes.MC_DOCENCIA_POST%>">
											<logic:equal name="meritoExpmc" property="BOpcional" value="<%=Constantes.SI%>">
												<a
													href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="meritoExpmc" property="CExpId"/>&pestana=5&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="meritoExpmc" property="CEstatutId"/>&cDefMeritoId=<bean:write name="OCAPMeritosAnterioresForm" property="tipoMerito"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=&meritosAnteriores=Y&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
													<img src="imagenes/imagenes_ocap/icono_modificar.gif"
													alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
												</a>
											</logic:equal>
											
											<logic:notEqual name="meritoExpmc" property="BOpcional" value="<%=Constantes.SI%>">
												<a
													href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="meritoExpmc" property="CExpId"/>&pestana=<bean:write name="OCAPMeritosAnterioresForm" property="tipoMerito"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="meritoExpmc" property="CEstatutId"/>&cDefMeritoId=<bean:write name="OCAPMeritosAnterioresForm" property="tipoMerito"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=&meritosAnteriores=Y&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
													<img src="imagenes/imagenes_ocap/icono_modificar.gif"
													alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
												</a>
											</logic:notEqual>
									</logic:equal>
									</span>
									<logic:equal name="meritoExpmc" property="BPdteAclarar"	value="<%=Constantes.SI%>">
										<span><img src="imagenes/imagenes_ocap/icono_aclaracion.gif" alt="Pendiente de Aclaraci&oacute;n" /></span>
									</logic:equal>
								</div>
							</td>
							<td class="icono" nowrap="nowrap">
								</td>
							<% }%>

						<td class="primero">
							<div class="botonAccion">
							     
							     <div class="bloqueDato">
										<span class="titulo">Usuario: <bean:write name="meritoExpmc" property="NCreditos" /></span>
									</div>
									<div class="bloqueDato">
										<span class="titulo">CEIS: <bean:write name="meritoExpmc" property="NCredCeis" /></span>
									</div>
									<div class="bloqueDato">
											<span class="titulo">CC: <bean:write name="meritoExpmc"	property="NCredCc" /></span>
									</div>
							</div>
						</td>
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
						
							<% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())){%>
							<td class="icono">								
									<logic:equal name="meritoExpmcCTSP" property="BInvalidado"	value="<%=Constantes.SI%>">
										<div class="botonAccion" style="margin-top: 5px !important;">
											<span>Invalidado</span>
										</div>
									</logic:equal>
								</td>
							<td class="icono">
								<div class="botonAccion">
									<span> <a href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmcCTSP" property="CExpmcId"/>&pestana=<bean:write name="OCAPMeritosAnterioresForm" property="tipoMerito"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="OCAPMeritosAnterioresForm" property="tipoMerito"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=&meritosAnteriores=Y&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
											<img src="imagenes/imagenes_ocap/icono_modificar.gif"	alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
									</a>
									</span>
									<logic:equal name="meritoExpmcCTSP" property="BPdteAclarar"	value="<%=Constantes.SI%>">
										<span><img src="imagenes/imagenes_ocap/icono_aclaracion.gif" alt="Pendiente de Aclaraci&oacute;n" /></span>
									</logic:equal>
								</div>
							</td>
							<% } %>

						<td class="primero">
							<div class="botonAccion">
								<span class="creditos"><bean:write name="meritoExpmcCTSP"	property="NHoras" /> horas</span>
							</div>
						</td>
					</tr>
				</logic:iterate>
			</table>			
			
			
			<div style="width: 80%; margin-left: 50px;" align="right">
				<div class="lineaBajaM"></div>
				<br />
				<span>Total Cr&eacute;ditos Usuario: &nbsp;</span>
			<bean:write name="requestTiposMeritos" property="creditosCTSP" />
			<br />
			
				<span>Total Cr&eacute;ditos CEIS: &nbsp;</span>
				<bean:write name="requestTiposMeritos"
					property="creditosValidadosCeisCTSP" />
				<br />
					<span>Total Cr&eacute;ditos CC: &nbsp;</span>
					<bean:write name="requestTiposMeritos" property="creditosValidadosCcCTSP" />
			</div>
		<div class="espaciador"></div>
							
		</logic:notEmpty>		
		<logic:notEmpty name="tiposMeritos" property="listaExpmcCTSNP">
			<div class="lineaBajaM"	style="width: 80%; margin-left: 50px; margin-bottom: 10px;">
				<span>No Acreditados - Modalidad No Presencial</span>
			</div>
			<table>
				<logic:iterate id="meritoExpmcCTSNP" name="tiposMeritos" property="listaExpmcCTSNP" type="OCAPExpedientemcOT">
					<tr class="tablaMC">
						<td class="primero"><span><bean:write name="meritoExpmcCTSNP" property="DTitulo" /></span></td>
						
							<% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())){%>
							<td class="icono">
									<logic:equal name="meritoExpmcCTSNP" property="BInvalidado"	value="<%=Constantes.SI%>">
										<div class="botonAccion" style="margin-top: 5px !important;">
											<span>Invalidado</span>
										</div>
									</logic:equal>
								</td>
							<td class="icono">
								<div class="botonAccion">
									<span> <a
										href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmcCTSNP" property="CExpmcId"/>&pestana=<bean:write name="OCAPMeritosAnterioresForm" property="tipoMerito"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="OCAPMeritosAnterioresForm" property="tipoMerito"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=&meritosAnteriores=Y&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
											<img src="imagenes/imagenes_ocap/icono_modificar.gif"
											alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
									</a>
									</span>
									<logic:equal name="meritoExpmcCTSNP" property="BPdteAclarar" value="<%=Constantes.SI%>">
										<span><img	src="imagenes/imagenes_ocap/icono_aclaracion.gif"	alt="Pendiente de Aclaraci&oacute;n" /></span>
									</logic:equal>
								</div>
							</td>
							<td class="icono">
							</td>
							<% }%>

						<td class="primero">
							<div class="botonAccion">
								<span class="creditos"><bean:write	name="meritoExpmcCTSNP" property="NHoras" /> horas</span>
							</div>
						</td>
					</tr>
				</logic:iterate>
			</table>			
			
			<div style="width: 80%; margin-left: 50px;" align="right">
				<div class="lineaBajaM"></div>
				<br />	
				
				<span>Total Cr&eacute;ditos Usuario: &nbsp;</span>
				<bean:write name="requestTiposMeritos" property="creditosCTSNP" />
				<br />
				
				<span>Total Cr&eacute;ditos CEIS: &nbsp;</span>
				<bean:write name="requestTiposMeritos"
					property="creditosValidadosCeisCTSNP" />
				<br />
					
				<span>Total Cr&eacute;ditos CC: &nbsp;</span>
				<bean:write name="requestTiposMeritos" property="creditosValidadosCcCTSNP" />
					
				
				
				
				</div>
				<div class="espaciador"></div>
		</logic:notEmpty>		
		<logic:notEmpty name="tiposMeritos" property="listaExpmcCTSM">
			<div class="lineaBajaM"	style="width: 80%; margin-left: 50px; margin-bottom: 10px;">
				<span>No Acreditados - Modalidad Mixta</span>
			</div>
			<table>
				<logic:iterate id="meritoExpmcCTSM" name="tiposMeritos"	property="listaExpmcCTSM" type="OCAPExpedientemcOT">
					<tr class="tablaMC">
						<td class="primero"><span><bean:write name="meritoExpmcCTSM" property="DTitulo" /></span></td>
						
							<% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())){%>
							<td class="icono">
							
									<logic:equal name="meritoExpmcCTSM" property="BInvalidado" value="<%=Constantes.SI%>">
										<div class="botonAccion" style="margin-top: 5px !important;">
											<span>Invalidado</span>
										</div>
									</logic:equal>
							</td>
							<td class="icono">
								<div class="botonAccion">
									<span> <a
										href="OCAPMeritos.do?accion=irModificar<bean:write name="tiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="meritoExpmcCTSM" property="CExpmcId"/>&pestana=<bean:write name="OCAPMeritosAnterioresForm" property="tipoMerito"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="OCAPMeritosAnterioresForm" property="tipoMerito"/>&detalle=<%=Constantes.SI%>&cDni=<bean:write name="OCAPUsuariosForm" property="CDni"/>&estado=&meritosAnteriores=Y&tipo=<bean:write name="tiposMeritos" property="CTipoMerito"/>">
											<img src="imagenes/imagenes_ocap/icono_modificar.gif"
											alt="Ver  M&eacute;rito" /> <span>Ver m&eacute;rito</span>
									</a>
									</span>
									<logic:equal name="meritoExpmcCTSM" property="BPdteAclarar" value="<%=Constantes.SI%>">
										<span><img src="imagenes/imagenes_ocap/icono_aclaracion.gif" alt="Pendiente de Aclaraci&oacute;n" /></span>
									</logic:equal>
								</div>
							</td>
							<td class="icono">
							</td>
							<% } %>

						<td class="primero">
							<div class="botonAccion">
								<span class="creditos"><bean:write name="meritoExpmcCTSM"
										property="NHoras" /> horas</span>
							</div>
						</td>
					</tr>
				</logic:iterate>
			</table>
			

			<div style="width: 80%; margin-left: 50px;" align="right">
				<div class="lineaBajaM"></div>
				<br />
				<span>Total Cr&eacute;ditos Usuario: &nbsp;</span>
				<bean:write name="requestTiposMeritos" property="creditosCTSM" />
				<br />
				
				<span>Total Cr&eacute;ditos CEIS: &nbsp;</span>
				<bean:write name="requestTiposMeritos"
					property="creditosValidadosCeisCTSM" />
				<br />
				
				<span>Total Cr&eacute;ditos CC: &nbsp;</span>
				<bean:write name="requestTiposMeritos" property="creditosValidadosCcCTSM" />
		
				
			</div>
			<div class="espaciador"></div>
			
		</logic:notEmpty>	
 				
 				</logic:iterate>
 			</logic:notEmpty>
	
		</div>
	</div>