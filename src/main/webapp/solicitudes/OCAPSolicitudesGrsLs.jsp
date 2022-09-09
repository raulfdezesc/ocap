<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT"%>
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
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"
	charset="windows-1252"></script>

<script language="JavaScript" type="text/javascript">
function seleccionarTodos(source) {
  checkboxes = document.forms[0].listaExpedientes;
  if (checkboxes.length != undefined){
	  	for(var i=0, n=checkboxes.length;i<n;i++) {          
	    	checkboxes[i].checked = source.checked;
	  	}
  }else{
  		checkboxes.checked = source.checked;
  }
  
}

function  generarInformesSeleccionados(){
   chck = document.forms[0].listaExpedientes;
   var marcados = 0;
   if(chck != null && chck.length != undefined) {
    for (var i = 0; chck.length-1 >= i; i++) {
        
           if ( chck[i].checked )
           {
             marcados =marcados +1;
            }
           
     }
      if (marcados == 0){
      	alert('Es necesario seleccionar al menos un informe')
      }else{
      	enviar('OCAPSolicitudes.do?accion=generarInformesReconocimientoSeleccionados');
      }
   } else if (chck != null && !chck.checked){
   		alert('Es necesario seleccionar al menos un informe')
   }else if (chck != null && chck.checked){
   		enviar('OCAPSolicitudes.do?accion=generarInformesReconocimientoSeleccionados');
   }
}

function limpiar(){
   document.forms[0].CGerencia_id.value = '';
   <%if(!request.getParameter("opcion").equals(Constantes.GRS_RECON)){%>
      document.forms[0].CEstado.value='';
   <%}%>      
      
   document.forms[0].CGrado_id.value='';
   document.forms[0].CConvocatoriaId.value='';   
}

function buscar(){
   enviar('OCAPSolicitudes.do?accion=buscarGrs&opcion=<%=request.getParameter("opcion")%>');
}

function generarPDF(){
   enviar("OCAPSolicitudes.do?accion=generarPDFGrs&opcion=<%=request.getParameter("opcion")%>&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
}

function generarCSV(){
   enviar("OCAPSolicitudes.do?accion=generarPDFGrs&opcion=<%=request.getParameter("opcion")%>&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&CSV=<%=Constantes.SI%>");
}

function cargarComboConvocatorias(){
   enviar('OCAPSolicitudes.do?accion=cargarComboConvocatorias&jspVuelta=buscadorGrs&opcion=<%=request.getParameter("opcion")%>');
}

function aceptaMasiva(){
   enviar('OCAPSolicitudes.do?accion=insertarAceptacionMasiva&opcion=<%=request.getParameter("opcion")%>');
}
</script>

<div class="contenido">
	<div class="contenidoListadoAspirantesGCP">
		<html:form action="/OCAPSolicitudes.do">
			<h2 class="tituloContenido">USUARIOS CON OPCI&Oacute;N A CARRERA
				PROFESIONAL</h2>
			<logic:equal name="opcion" value="<%=Constantes.GRS_SOLIC%>">
				<h3 class="subTituloContenido">Listado de Solicitudes por
					Gerencias</h3>
			</logic:equal>
			<logic:equal name="opcion" value="<%=Constantes.GRS_MERIT%>">
				<h3 class="subTituloContenido">Listado de Usuarios con
					M&eacute;ritos Curriculares por Gerencias</h3>
			</logic:equal>
			<logic:equal name="opcion" value="<%=Constantes.GRS_PROCC%>">
				<%if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_GRS)){%>
				<h3 class="subTituloContenido">Listado de Fase de
					M&eacute;ritos Curriculares por Gerencias</h3>
				<%}else{ %>
				<h3 class="subTituloContenido">Listado de Fase de
					M&eacute;ritos Curriculares</h3>
				<%} %>
			</logic:equal>
			<logic:equal name="opcion" value="<%=Constantes.GRS_RECON%>">
				<h3 class="subTituloContenido">Listado de Usuarios con Grado
					Reconocido por la CC</h3>
			</logic:equal>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<fieldset>
				<legend class="tituloLegend"> Buscador </legend>
				<div class="cuadroFieldset">
					<%if (jcylUsuario.getUser().getRol() != null && 
					jcylUsuario.getUser().getRol().equals(Constantes.OCAP_GRS)){%>
								<label for="idVCategoria"> Gerencia: <html:select
							property="CGerencia_id" styleClass="cbCuadros colocaCategoriaCB"
							size="1" tabindex="21">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
								property="CGerenciaId" labelProperty="DNombre" />
						</html:select>
					</label>
					<%} else if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE)){ %>
					
							<label for="idVGerencia"> Gerencia: <html:hidden
								name="OCAPSolicitudesForm" property="CGerencia_id" /> <span
							class="colocaGerenciaExpedientesCB"><bean:write
									name="OCAPSolicitudesForm" property="DNombre" /></span>
						</label>
					
					<%} %>
					<br />
					<br />
					<logic:notEqual name="opcion" value="<%=Constantes.GRS_RECON%>">
						<label for="idVSolicitud"> Estado: <html:select
								property="CEstado" styleClass="cbCuadros colocaEstadoGCP"
								tabindex="22">
								<html:option value="">Seleccione...</html:option>
								<html:optionsCollection name="<%=Constantes.COMBO_ESTADOS%>" />
							</html:select>
						</label>
						<br />
						<br />
					</logic:notEqual>
					<label for="idVGrado"> Grado: <html:select
							property="CGrado_id" styleClass="cbCuadros colocaGradoBuscGPC"
							size="1" tabindex="23">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
								property="CGradoId" labelProperty="DDescripcion" />
						</html:select>
					</label> <label for="idVConvocatoria"> Convocatoria: <html:select
							property="CConvocatoriaId"
							styleClass="cbCuadros colocaConvocatoriaGPC" size="1"
							tabindex="41">
							<html:option value="">Todas</html:option>
							<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
								property="CConvocatoriaId" labelProperty="DNombre" />
						</html:select>
					</label>
				</div>
				<div class="botonesPagina colocaBotonBusc">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:buscar();" tabindex="61"> <img
								src="imagenes/imagenes_ocap/dobleFlecha.gif"
								class="colocaImgPrint" alt="Buscar" /> <span> Buscar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:limpiar();" tabindex="61"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif"
								class="colocaImgPrint" alt="Limpiar" /> <span> Limpiar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</fieldset>
			<logic:equal name="OCAPSolicitudesForm" property="jspInicio"
				value="false">
				<fieldset>
					<bean:size id="tamano" name="listados" property="elementos" />
					<legend class="tituloLegend"> Listado de Solicitudes </legend>
					<div class="cuadroFieldset">
						<div class="cuadroListadoAspirantesBis">
							<logic:equal name="tamano" value="0">
								<table border="0" cellpadding="2" cellspacing="0" width="82%"
									align="center">
									<tr>
										<td bgcolor="#FFFFFF" colspan="3" align="center"
											class="textoNegro"><bean:message
												key="documento.noSolicitudes" /></td>
									</tr>
								</table>
							</logic:equal>
							<logic:notEqual name="tamano" value="0">
								<logic:iterate id="listaTotal" name="listados"
									property="elementos" indexId="index">
									<bean:define id="profesiones" name="listaTotal"
										type="OCAPSolicitudesOT" />
									<div class="tituloGrado">
										<bean:write name="profesiones" property="DGerencia_nombre" />
									</div>
									<logic:iterate id="listaProfeEspec" name="profesiones"
										property="listaSolicitudes" type="OCAPSolicitudesOT">
										<bean:define id="solicitud" name="listaProfeEspec"
											type="OCAPSolicitudesOT" />
										<span class="subTituloListado"> <bean:write
												name="solicitud" property="DGrado_des" /></span>
										<div class="lineaBajaM"></div>
										<table class="tablaListadoCSV">
											<tr>
												<logic:equal name="opcion" value="<%=Constantes.GRS_SOLIC%>">
													
													<td class="tituloAsp" width=20%>Categor&iacute;a</td>
													<td class="tituloAsp" width=15%>Especialidad</td>
													<td class="tituloAsp" width=10%>NIF/NIE</td>
													<td class="tituloAsp" width=15%>Apellidos</td>
													<td class="tituloAsp" width=15%>Nombre</td>
													<%
														if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
													%>
													<td class="tituloConvocatoria" width=15%>Convocatoria</td>
													<%}	%>														
													<td class="tituloAsp" width=10%>Estado</td>
												
												</logic:equal>
												<logic:notEqual name="opcion"
													value="<%=Constantes.GRS_SOLIC%>">
													<% if (Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())) { %>
														<logic:equal name="opcion" value="<%=Constantes.GRS_RECON%>">
															<td class="tituloAsp" width=10%> </td>
														</logic:equal>
													<% } %>
													<td class="tituloAsp" width=20%>NIF/NIE</td>
													<td class="tituloAsp" width=20%>Apellidos</td>
													<td class="tituloAsp" width=20%>Nombre</td>
													<%
														if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
													%>
													<td class="tituloConvocatoria" width=20%>Convocatoria</td>
													<%}	%> 													
													<td class="tituloAsp" width=20%>
														<logic:notEqual name="opcion" value="<%=Constantes.GRS_RECON%>">
                                    						Estado 
                                 						</logic:notEqual>
                                 					</td>
                                					
												</logic:notEqual>
												<td></td>
											</tr>
											<logic:iterate id="solicitudes" name="solicitud"
												property="listaSolicitudes" type="OCAPSolicitudesOT">
												<tr class="datosAsp">
													<logic:equal name="opcion"
														value="<%=Constantes.GRS_SOLIC%>">
														<td width=20%><bean:write name="solicitudes"
																property="DProfesional_nombre" /></td>
														<td width=15%><bean:write name="solicitudes"
																property="DEspec_nombre" /></td>
													</logic:equal>
													<% if (Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())) { %>
													<logic:equal name="opcion" value="<%=Constantes.GRS_RECON%>">
														<td class="check"><html:multibox property="listaExpedientes">
																<bean:write name="solicitudes" property="CExp_id" />
														</html:multibox></td>
													</logic:equal>
													<% } %>
													<td width=10%><bean:write name="solicitudes"
															property="CDniReal" /></td>
													<td width=15%><bean:write name="solicitudes"
															property="DApellido1" /></td>
													<td width=15%><bean:write name="solicitudes"
															property="DNombre" /></td>
													<td width=15%><bean:write name="solicitudes"
															property="DConvocatoriaNombreCorto" /></td>																
													<td width=10%><logic:notEqual name="opcion"
															value="<%=Constantes.GRS_RECON%>">
															<bean:write name="solicitudes" property="CEstado" />
														</logic:notEqual></td>
													
													<logic:notEqual name="opcion"
														value="<%=Constantes.GRS_SOLIC%>">
														<logic:notEqual name="opcion"
															value="<%=Constantes.GRS_RECON%>">
															<%if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_GRS)){%>
															<td><a
																href="OCAPUsuarios.do?accion=irInsertar&CExp_id=<bean:write name="solicitudes" property="CExp_id" />&opcion=<%=request.getParameter("opcion")%>"><img
																	src="imagenes/imagenes_ocap/lupa.gif"
																	alt="ver Meritos Curriculares" /></a></td>
															<%}%>
														</logic:notEqual>
														<logic:equal name="opcion"
															value="<%=Constantes.GRS_RECON%>">
															<td><a
																href="OCAPSolicitudes.do?accion=generarPDFReconociGrado&expId=<bean:write name="solicitudes" property="CExp_id" />"><img
																	src="imagenes/imagenes_ocap/lupa.gif"
																	alt="Generar informe" /></a></td>
														</logic:equal>
													</logic:notEqual>
												</tr>
											</logic:iterate>
										</table>
									</logic:iterate>
								</logic:iterate>
								<% if (Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())) { %>
									<logic:equal name="opcion" value="<%=Constantes.GRS_RECON%>">
										<input type="checkbox" style="margin-top:2%;" class="tituloAsp" onClick="seleccionarTodos(this)" /> <p style="display: inline;" class="tituloAsp">Seleccionar todos</p>
										<div class="botonAccion" style="margin-top:3%;">
											<span class="izqBoton" style="display: none;"></span> <span class="cenBoton" style="margin-left: 0px !important;"> <a
												href="javascript:generarInformesSeleccionados();" tabindex="61">
													<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
													alt="Generar listado PDF" /> <span> Generar Informes
												</span>
											</a>
											</span> <span class="derBoton" style="margin-left: 0px !important;"></span>
										</div>
									</logic:equal>
								<% } %>
								<%@ include file="/comun/paginacion.jsp"%>
							</logic:notEqual>
						</div>
					</div>
				</fieldset>
				<logic:notEqual name="tamano" value="0">
					<logic:equal name="opcion" value="<%=Constantes.GRS_MERIT%>">
						<div class="espaciador"></div>
						<div class="botonesPagina">
							<logic:equal name="masiva" value="<%=Constantes.SI%>">
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="javascript:aceptaMasiva();"> <img
											src="imagenes/imagenes_ocap/flecha_correcto.gif"
											alt="Generar listado PDF" /> <span> Aceptaci&oacute;n
												masiva </span>
									</a>
									</span> <span class="derBoton"></span>
								</div>
							</logic:equal>
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:generarPDF();"> <img
										src="imagenes/imagenes_ocap/flecha_correcto.gif"
										alt="Generar listado PDF" /> <span> Generar Listado </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</div>
					</logic:equal>
					<logic:equal name="opcion" value="<%=Constantes.GRS_PROCC%>">
						<div class="espaciador"></div>
						<div class="botonesPagina">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:generarPDF();"> <img
										src="imagenes/imagenes_ocap/flecha_correcto.gif"
										alt="Generar listado PDF" /> <span> Generar Listado </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</div>
					</logic:equal>
					<!-- Acceso GRS a listados de solicitudes -->
					<logic:equal name="opcion" value="<%=Constantes.GRS_SOLIC%>">
						<logic:notEqual name="OCAPSolicitudesForm"
							property="CConvocatoriaId" value="">
							<logic:equal name="OCAPSolicitudesForm" property="CEstado"
								value="<%=Constantes.ESTADO_ACEPTADO_VALUE%>">
								<div class="espaciador"></div>
								<div class="botonesPagina">
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a href="javascript:generarPDF();"> <img
												src="imagenes/imagenes_ocap/flecha_correcto.gif"
												alt="Generar listado PDF" /> <span> Generar Listado
											</span>
										</a>
										</span> <span class="derBoton"></span>
									</div>
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a href="javascript:generarCSV();"> <img
												src="imagenes/imagenes_ocap/flecha_correcto.gif"
												alt="Generar listado CSV" /> <span> Generar CSV </span>
										</a>
										</span> <span class="derBoton"></span>
									</div>
								</div>
							</logic:equal>
							<logic:equal name="OCAPSolicitudesForm" property="CEstado"
								value="<%=Constantes.ESTADO_EXCLUIDO_VALUE%>">
								<div class="espaciador"></div>
								<div class="botonesPagina">
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a href="javascript:generarPDF();"> <img
												src="imagenes/imagenes_ocap/flecha_correcto.gif"
												alt="Generar listado PDF" /> <span> Generar Listado
											</span>
										</a>
										</span> <span class="derBoton"></span>
									</div>
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a href="javascript:generarCSV();"> <img
												src="imagenes/imagenes_ocap/flecha_correcto.gif"
												alt="Generar listado CSV" /> <span> Generar CSV </span>
										</a>
										</span> <span class="derBoton"></span>
									</div>
								</div>
							</logic:equal>
						</logic:notEqual>
					</logic:equal>
				</logic:notEqual>
			</logic:equal>
		</html:form>
	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->
