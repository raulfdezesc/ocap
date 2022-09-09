<!-- Entramos en permisoModificar -->
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.meritosCurriculares.OCAPMeritoscurricularesOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.expedienteMC.OCAPExpedientemcOT" %>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>


<% String tipoMeritoAux="";
  	OCAPMeritoscurricularesOT meritoOriginal = (OCAPMeritoscurricularesOT)request.getAttribute("requestTiposMeritos");
                           if (meritoOriginal.getDNombre().indexOf("+")!=-1) {
                              tipoMeritoAux = meritoOriginal.getDNombre().replaceAll("\\+",Constantes.MAS);
                           } else {
                              tipoMeritoAux = meritoOriginal.getDNombre();
                         }                       
                        %>

	<span> 
				<logic:equal name="requestTiposMeritos" property="CTipoMerito" value="<%=Constantes.MC_MERITO_SINO%>">
						<logic:empty name="requestTiposMeritos" property="listaExpmc">
							<a class="colocaAnadir"
								href="OCAPMeritos.do?accion=irInsertar<bean:write name="requestTiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipo=<bean:write name="requestTiposMeritos" property="CTipoMerito"/>">
								<img src="imagenes/imagenes_ocap/anadir.gif" 
								alt="A&ntilde;adir M&eacute;rito" /> <span>A&ntilde;adir</span>
							</a>
						</logic:empty>
				 </logic:equal>
				<logic:equal name="requestTiposMeritos" property="CTipoMerito" value="<%=Constantes.MC_CENTINELA%>">
						<logic:empty name="requestTiposMeritos" property="listaExpmc">
							<a class="colocaAnadir"
								href="OCAPMeritos.do?accion=irInsertar<bean:write name="requestTiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipo=<bean:write name="requestTiposMeritos" property="CTipoMerito"/>">
								<img src="imagenes/imagenes_ocap/anadir.gif"
								alt="A&ntilde;adir M&eacute;rito" /> <span>A&ntilde;adir</span>
							</a>
						</logic:empty>
				</logic:equal>
				<logic:equal name="requestTiposMeritos" property="CTipoMerito"	value="<%=Constantes.MC_FORMA_NUEVO_PERSONAL%>">
						<a class="colocaAnadir"
							href="OCAPMeritos.do?accion=irInsertar<bean:write name="requestTiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipo=<bean:write name="requestTiposMeritos" property="CTipoMerito"/>">
							<img src="imagenes/imagenes_ocap/anadir.gif"
							alt="A&ntilde;adir M&eacute;rito" /> <span>A&ntilde;adir</span>
						</a>
				</logic:equal>
				<logic:equal name="requestTiposMeritos" property="CTipoMerito"	value="<%=Constantes.MC_DOCENCIA_POST%>">
						<logic:notEmpty name="requestTiposMeritos" property="listaExpmc">
							<a class="colocaAnadir"
								href="OCAPMeritos.do?accion=irModificar<bean:write name="requestTiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipo=<bean:write name="requestTiposMeritos" property="CTipoMerito"/>">
								<img src="imagenes/imagenes_ocap/icono_modificar.gif"
								alt="Modificar  M&eacute;rito" /> <span>Modificar</span>
							</a>
						</logic:notEmpty>
						<logic:empty name="requestTiposMeritos" property="listaExpmc">
							<a class="colocaAnadir"
								href="OCAPMeritos.do?accion=irInsertar<bean:write name="requestTiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipo=<bean:write name="requestTiposMeritos" property="CTipoMerito"/>">
								<img src="imagenes/imagenes_ocap/anadir.gif"
								alt="A&ntilde;adir M&eacute;rito" /> <span>A&ntilde;adir</span>
							</a>
						</logic:empty>
				</logic:equal>
				<logic:equal name="requestTiposMeritos" property="CTipoMerito" value="<%=Constantes.MC_BIBLIOGRAFICAS%>">
						
						<logic:iterate id="lista" name="requestTiposMeritos"	property="listaExpmc" type="OCAPExpedientemcOT">
							<logic:notEmpty name="requestTiposMeritos" property="listaExpmc">
								<a class="colocaAnadir"
									href="OCAPMeritos.do?accion=irModificar<bean:write name="requestTiposMeritos" property="CTipoMerito"/>&idMer=<bean:write name="lista" property="CExpmcId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipo=<bean:write name="requestTiposMeritos" property="CTipoMerito"/>">
									<img src="imagenes/imagenes_ocap/icono_modificar.gif"
									alt="Modificar  M&eacute;rito" /> <span>Modificar</span>
								</a>
							</logic:notEmpty>
						</logic:iterate>
						<logic:empty name="requestTiposMeritos" property="listaExpmc">
							<a class="colocaAnadir"
								href="OCAPMeritos.do?accion=irInsertar<bean:write name="requestTiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipo=<bean:write name="requestTiposMeritos" property="CTipoMerito"/>">
								<img src="imagenes/imagenes_ocap/anadir.gif"
								alt="A&ntilde;adir M&eacute;rito" /> <span>A&ntilde;adir</span>
							</a>
						</logic:empty>
				</logic:equal>
				<logic:notEqual name="requestTiposMeritos" property="CTipoMerito"	value="<%=Constantes.MC_MERITO_SINO%>">
						<logic:notEqual name="requestTiposMeritos" property="CTipoMerito"	value="<%=Constantes.MC_CENTINELA%>">
							<logic:notEqual name="requestTiposMeritos" property="CTipoMerito"	value="<%=Constantes.MC_FORMA_NUEVO_PERSONAL%>">
								<logic:notEqual name="requestTiposMeritos" property="CTipoMerito"	value="<%=Constantes.MC_DOCENCIA_POST%>">
									<logic:notEqual name="requestTiposMeritos" property="CTipoMerito"	value="<%=Constantes.MC_BIBLIOGRAFICAS%>">
										<a class="colocaAnadir"
											href="OCAPMeritos.do?accion=irInsertar<bean:write name="requestTiposMeritos" property="CTipoMerito"/>&expId=<bean:write name="OCAPUsuariosForm" property="CExpId"/>&pestana=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipoMerito=<%=tipoMeritoAux%>&cEstatutId=<bean:write name="OCAPUsuariosForm" property="CEstatutId"/>&cDefMeritoId=<bean:write name="meritoSelecc" property="CDefmeritoId"/>&tipo=<bean:write name="requestTiposMeritos" property="CTipoMerito"/>&personal=<bean:write name="OCAPUsuariosForm" property="CPersonalId"/>">
											<img src="imagenes/imagenes_ocap/anadir.gif"
											alt="A&ntilde;adir M&eacute;rito" /> <span>A&ntilde;adir</span>
										</a>
									</logic:notEqual>
								</logic:notEqual>
							</logic:notEqual>
						</logic:notEqual>
				</logic:notEqual>
				</span>