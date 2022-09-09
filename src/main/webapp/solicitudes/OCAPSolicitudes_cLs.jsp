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

function limpiar(){
   document.forms[0].CDni.value = '';
   document.forms[0].DApellido1.value = '';
   document.forms[0].DNombre.value = '';
   document.forms[0].CGrado_id.value='';
   document.forms[0].CGerencia_id.value='';
   document.forms[0].CConvocatoriaId.options[0].selected='selected';
   <%if(!Constantes.GRS_FECHAS.equals(request.getParameter("opcion"))){%>
      document.forms[0].DAccion.value = '';
   <%}%>
}

function validarBuscador(formu){

   // PRIMER APELLIDO
   var cadena = formu.DApellido1.value;
   if(cadena!='') {
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'Apellidos\'.');
         return false;
      }else{
         if(LetrasYNumeros(formu.DApellido1)){
            formu.DApellido1.value = cadena;
         } else{
            alert('El campo \'Apellidos\' contiene caracteres no permitidos.');
            return false;
         }
      }
   }

   // NOMBRE
   var cadena = formu.DNombre.value;
   if(cadena!='') {
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'Nombre\'.');
         return false;
      }else{
         if(LetrasYNumeros(formu.DNombre)){
            formu.DNombre.value = cadena;
         } else{
            alert('El campo \'Nombre\' contiene caracteres no permitidos.');
            return false;
         }
      }
   }

   // NIF/NIE
   var cadena = formu.CDni.value;
   if(cadena!='') {
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'NIF/NIE\'.');
         return false;
      }else{
         if(LetrasYNumeros(formu.CDni)){
            formu.CDni.value = cadena;
         } else{
            alert('El campo \'NIF/NIE\' contiene caracteres no permitidos.');
            return false;
         }
      }
   }

   return true;
}

function buscar(){
   if(validarBuscador(document.forms[0])){
      enviar('OCAPNuevaSolicitud.do?accion=buscarAdmGrs&opcion=<%=request.getAttribute("opcion")%>');
   }
}

</script>

<div class="contenido">
	<div class="contenidoListadoAspirantesGCP admFechas gestionSolicitudes">
		<html:form action="/OCAPNuevaSolicitud.do">
			<h2 class="tituloContenido">Administraci&oacute;n de fechas</h2>
			<!--<h3 class="subTituloContenido"> Listado de Solicitudes </h3>-->
			<!--<div class="lineaBajaM"></div>-->
			<div class="espaciador"></div>
			<fieldset>
				<legend class="tituloLegend"> Buscador </legend>
				<div class="cuadroFieldset">
					<label for="idVApell1"> Apellidos: <html:text
							property="DApellido1" styleClass="recuadroM colocaApellidos"
							maxlength="30" />
					</label> <label for="idVNombre"> Nombre: <html:text
							property="DNombre" styleClass="recuadroM colocaNombre"
							maxlength="30" />
					</label> <br />
					<br /> <label for="idVDNI"> NIF/NIE: <html:text
							property="CDni" styleClass="recuadroM colocaDNI" maxlength="10" />
					</label> <br />
					<br /> <label for="idVGerencia"> Gerencia: <html:select
							property="CGerencia_id" styleClass="cbCuadros colocaGerenciaAdmF"
							size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
								property="CGerenciaId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br /> <label for="idVGrado"> Grado: <html:select
							property="CGrado_id" styleClass="cbCuadros colocaGradoBuscCB"
							size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
								property="CGradoId" labelProperty="DDescripcion" />
						</html:select>
					</label> <label for="idVApell1"> Convocatoria: <html:select
							property="CConvocatoriaId"
							styleClass="cbCuadros colocaConvocatoriaBuscCB" size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
								property="CConvocatoriaId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br />
					<logic:notEqual name="opcion" value="<%=Constantes.GRS_FECHAS%>">
						<label for="idVApell1"> Acci&oacute;n: <html:select
								property="DAccion" styleClass="cbCuadros colocaAccionBuscCB"
								size="1">
								<html:option value="">Seleccione...</html:option>
								<html:optionsCollection name="<%=Constantes.COMBO_ACCIONES%>" />
							</html:select>
						</label>
					</logic:notEqual>
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
			<logic:equal name="OCAPNuevaSolicitudForm" property="jspInicio"
				value="false">
				<fieldset>
					<bean:size id="tamano" name="listados" property="elementos" />
					<legend class="tituloLegend"> Listado de Solicitudes </legend>
					<div class="cuadroFieldset cuadroInferior">
						<div>
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
								<logic:equal name="opcion" value="<%=Constantes.GRS_FECHAS%>">
									<table class="tablaListado">
										<tr class="cabeceraTabla">
											<td class="tituloAsp tituloNIF">NIF/NIE</td>
											<td class="tituloAsp tituloApellidos">Apellidos</td>
											<td class="tituloAsp tituloNombre">Nombre</td>
											<%
												if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
											%>
											<td class="tituloAsp tituloConvocatoria">Convocatoria</td>
											<%}	%> 												
											<td class="tituloLupa"></td>
										</tr>
										<logic:iterate id="listaTotal" name="listados"
											property="elementos" type="OCAPSolicitudesOT">
											<html:hidden name="listaTotal" property="CExp_id" />
											<tr class="cuerpoTabla">
												<td class="dni"><bean:write name="listaTotal"
														property="CDniReal" /></td>
												<td class="apellido"><bean:write name="listaTotal"
														property="DApellido1" /></td>
												<td class="nombre"><bean:write name="listaTotal"
														property="DNombre" /></td>
												<%
													if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
												%>
												<td class="nombre"><bean:write name="listaTotal"
														property="DConvocatoriaNombreCorto" /></td>
												
												<%}	%> 														
												<td class="imagen lateralImagen"><a
													href="OCAPNuevaSolicitud.do?accion=detalleSolicitudGrs&CExp_id=<bean:write name="listaTotal" property="CExp_id"/>&opcion=<%=Constantes.GRS_FECHAS%>">Ver</a>
												</td>
											</tr>
										</logic:iterate>
									</table>
								</logic:equal>
								<logic:notEqual name="opcion" value="<%=Constantes.GRS_FECHAS%>">
									<div class="cuadroListadoAspirantes">
										<logic:iterate id="listaTotal" name="listados"
											property="elementos" indexId="index">
											<bean:define id="profesiones" name="listaTotal"
												type="OCAPSolicitudesOT" />
											<div class="tituloGrado">
												<bean:write name="profesiones" property="DGrado_des" />
											</div>
											<logic:iterate id="listaProfeEspec" name="profesiones"
												property="listaSolicitudes" type="OCAPSolicitudesOT">
												<bean:define id="solicitud" name="listaProfeEspec"
													type="OCAPSolicitudesOT" />
												<span class="subTituloListado"> <bean:write
														name="solicitud" property="DProfesional_nombre" /> / <bean:write
														name="solicitud" property="DEspec_nombre" />
												</span>
												<div class="lineaBajaM"></div>
												<table class="tablaSolicitudes">
													<tr class="cabeceraTablaSolicitudesC">
														<td class="cabeceraNIF"><span>NIF/NIE</span></td>
														<td class="cabeceraApellidos"><span>Apellidos</span></td>
														<td class="cabeceraNombre"><span>Nombre</span></td>
														<%
															if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
														%>
														<td class="cabeceraNombre"><span>Convocatoria</span></td>
														<%}	%>														
														<td class="tituloIcono"></td>
													</tr>
													<logic:iterate id="solicitudes" name="solicitud"
														property="listaSolicitudes" type="OCAPSolicitudesOT">
														<tr class="cuerpoTablaSolicitudes">
															<td class="cuerpoNIF"><bean:write name="solicitudes"
																	property="CDniReal" /></td>
															<td class="cuerpoApellido"><bean:write
																	name="solicitudes" property="DApellido1" /></td>
															<td class="cuerpoNombre"><bean:write
																	name="solicitudes" property="DNombre" /></td>
														<%
															if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
														%>
														<td class="cuerpoNombre"><bean:write
																	name="solicitudes" property="DConvocatoriaNombreCorto" /></td>
														<%}	%>																	
																	
															<td class="campoIcono"><a
																href="OCAPNuevaSolicitud.do?accion=detalleSolicitudGrs&CExp_id=<bean:write name="solicitudes" property="CExp_id"/>&opcion=<%=Constantes.GRS_EDC%>">
																	<logic:equal name="solicitudes" property="CEstado_id"
																		value="<%=Long.toString(Constantes.ESTADO_ACEPTADA_MC)%>">
                                                Excluir
                                             </logic:equal> <logic:notEqual
																		name="solicitudes" property="CEstado_id"
																		value="<%=Long.toString(Constantes.ESTADO_ACEPTADA_MC)%>">
                                                Incluir 
                                             </logic:notEqual>
															</a></td>
														</tr>
													</logic:iterate>
												</table>
											</logic:iterate>
										</logic:iterate>
									</div>
								</logic:notEqual>
								<%@ include file="/comun/paginacion.jsp"%>
							</logic:notEqual>
							<!-- tamano != 0 -->
						</div>
					</div>
				</fieldset>
			</logic:equal>
		</html:form>
	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->
