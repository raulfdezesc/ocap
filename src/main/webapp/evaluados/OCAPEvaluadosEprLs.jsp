<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="java.util.ArrayList"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"></script>

<script language="JavaScript">
function cargarComboEspecialidades(){
   enviar('OCAPEvaluadores.do?accion=cargarComboEspecialidades&opcion=<%=Constantes.FQS_LI%>&tipo=<%=Constantes.CONSULTAR%>');
}

function limpiar(){
   <%--if(jcylUsuario.getUser().getRol() != null && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)){--%>
   <%--}--%>
   document.forms[0].CProfesional_id.value = '';
   <%if(jcylUsuario.getUser().getRol() != null && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION) && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR) && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)){%>
      document.forms[0].CItinerario_id.value = '';
      document.forms[0].CProfesional_id.value = '';
      <%if(((ArrayList) session.getAttribute(Constantes.COMBO_ESPECIALIDAD)).size() > 0 ){%>
         document.forms[0].CEspec_id.value = '';
      <%}%>
   <%}%>
   <%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR) )) {%>
      document.forms[0].DApellidos.value = '';
      document.forms[0].DNombre.value = '';
      document.forms[0].DEstado.value = '';
      document.forms[0].CGerenciaId.value = '';
      document.forms[0].CDni.value = '';
      document.forms[0].CConvocatoria_id.value = '';
   <%}%>
    <%if(jcylUsuario.getUser().getRol() != null &&  jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL) ) {%>
      document.forms[0].DApellidos.value = '';
      document.forms[0].DNombre.value = '';
      document.forms[0].CDni.value = '';
   <%}%>
   
   <%if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)) { %>
      document.forms[0].DEstado.value = '';
   <% } %>
   cargarComboEspecialidades();
}

function buscar(){
   enviar('OCAPEvaluadores.do?accion=listarEvaluados&cCompfqsIdS='+ document.forms[0].CCompfqs_id.value);
}



</script>

<div class="contenido listadoGestionEvaluados">
	<div class="contenidoListadoAspirantesGCP buscadorEvaluados">
		<html:form action="/OCAPEvaluadores.do">
			<% if (session.getAttribute("eval") != null ) {%>
			<% if (Constantes.EVAL_1 == Integer.parseInt(session.getAttribute("eval").toString()) ) {%>
			<h3 class="subTituloContenidoEval">Listado de evaluados</h3>
			<% } else { %>
			<h3 class="subTituloContenidoEval">Listado de Evaluados Segunda
				Evaluaci&oacute;n</h3>
			<% } %>
			<% } else { %>
			<h3 class="subTituloContenidoEval">Listado de Evaluados</h3>
			<% } %>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<html:hidden property="CCompfqs_id" />

			<div class="textoRojo">
				<html:messages id="codigo_id" property="codigo_id" message="true">
					<bean:write name="codigo_id" />
					<br />
				</html:messages>
			</div>

			<fieldset>
				<legend class="tituloLegend"> Buscador </legend>

				<div class="cuadroFieldset">
					<%-- if(jcylUsuario.getUser().getRol() != null && !(jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC))){ --%>
			

					<%-- } --%>

					<%if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL)){%>
					<br />
					<br /> <label for="idVApell1"> Apellidos: <html:text
							property="DApellidos" tabindex="2"
							styleClass="recuadroM colocaApellidosLE3" maxlength="30" />
					</label> <label for="idVNombre"> Nombre: <html:text
							property="DNombre" tabindex="3"
							styleClass="recuadroM colocaNombre" maxlength="30" />
					</label> <br />
					<br /> <label for="idVDni"> NIF/NIE: <html:text
							property="CDni" tabindex="3"
							styleClass="recuadroM colocaDNIEvaluadores2" maxlength="10" />
					</label> <br />
					<br /> <label for="idVEspecialidad">Itinerario: <html:select
							property="CItinerario_id" styleClass="cbCuadros colocaItinerario"
							size="1" tabindex="2">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_ITINERARIOS%>"
								property="CItinerarioId" labelProperty="DItinerarioNombre" />
						</html:select>
					</label> <br />
					<br /> <label for="idVCategoria">Categor&iacute;a: <html:select
							property="CProfesional_id" styleClass="cbCuadros colocaCategoria"
							size="1" tabindex="3"
							onchange="javascript:cargarComboEspecialidades();">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
								property="CProfesionalId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br />
					<%if(((ArrayList) session.getAttribute(Constantes.COMBO_ESPECIALIDAD)).size() > 0 ){%>
					<label for="idVEspecialidad">Especialidad: <html:select
							property="CEspec_id" styleClass="cbCuadros colocaEspecialidad"
							size="1" tabindex="4">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
								property="CEspecId" labelProperty="DNombre" />
						</html:select>
					</label>
					<%}%>
					<%}%>
					<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR) )){%>
					
					<%if(((ArrayList) session.getAttribute(Constantes.COMBO_GERENCIA)).size() > 0 ){%>
					<!-- GERENCIA -->
					<label for="idGerencia">Gerencia: <html:select
								property="CGerenciaId" styleClass="cbCuadros colocaGerenciaEV"
								size="1" tabindex="1">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
									property="CGerenciaId" labelProperty="DNombre" />
							</html:select>
						</label>
					<%}%>
					<br />
					<br /> <label for="idVApell1"> Apellidos: <html:text
							property="DApellidos" tabindex="2"
							styleClass="recuadroM colocaApellidosLE2" maxlength="30" />
					</label> <label for="idVNombre"> Nombre: <html:text
							property="DNombre" tabindex="3"
							styleClass="recuadroM colocaNombre" maxlength="30" />
					</label>
					<%}%>
					<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL))){%>
					<label for="idVConvocatoria"> Convocatoria: <html:select
							property="CConvocatoria_id"
							styleClass="cbCuadros colocaConvocatoria2" size="1" tabindex="4"
							style="margin-left: 0.5em;">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
								property="CConvocatoriaId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br />

					<%}%>
					<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC))){%>
					<br />
					<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION))){%>
					<br /><label for="idVDNI">NIF/NIE:<html:text
							property="CDni" styleClass="recuadroM colocaDNI"
							maxlength="10" style="margin-left: 69px !important"/> <br />
					<%} %>
					<br /> <label for="idVCategoria">Categor&iacute;a: <html:select
							property="CProfesional_id"
							styleClass="cbCuadros colocaCategoria2" size="1" tabindex="4"
							onchange="javascript:cargarComboEspecialidades();">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
								property="CProfesionalId" labelProperty="DNombre" />
						</html:select>
					</label><br />
					<br />


					<%if(((ArrayList) session.getAttribute(Constantes.COMBO_ESPECIALIDAD)).size() > 0 ){%>
					<label for="idVEspecialidad">Especialidad: <html:select
							property="CEspec_id" styleClass="cbCuadros colocaEspecialidad3"
							size="1" tabindex="4">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
								property="CEspecId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br />
					<%}%>

					<label for="idVConvocatoria"> Convocatoria:  <html:select
							property="CConvocatoria_id"
							styleClass="cbCuadros colocaConvocatoria3" size="1" tabindex="4">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
								property="CConvocatoriaId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br /> <label for="idVCategoria">Estado <html:select
							property="DEstado" styleClass="cbCuadros colocaEstado3" size="1"
							tabindex="5">
							<html:option value="">Seleccione...</html:option>
							<html:optionsCollection name="<%=Constantes.COMBO_ESTADOS%>" />
						</html:select>
					</label>
					<%}%>
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

			<logic:equal name="OCAPComponentesfqsForm" property="jspInicio"
				value="false">
				<fieldset>
					<legend class="tituloLegend"> Listado </legend>

					<logic:present name="sinDatos">
						<div class="textoCaracteres textoNegro textoNormal">
							<div class="espaciador"></div>
							<bean:message key="listado.noDatos" />
							<br />
							<br />
						</div>
					</logic:present>
					<%if(jcylUsuario.getUser().getRol() != null && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION) && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR) && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL) && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)){%>
					<table class="tablaEvaluadores">
						<logic:notPresent name="sinDatos">
							<tr>
								<th class="col1" id="cCodigo">C&oacute;digo</th>

							</tr>
							<logic:present name="listados" property="elementos">
								<logic:iterate id="elemento" name="listados"
									property="elementos">
									<tr>
										<td class="col1" headers="cCodigo"><bean:write
												name="elemento" property="codigoId" /></td>
										<td><a
											href="OCAPNuevaSolicitud.do?accion=irDetalleFqs&CCompfqs_id=<bean:write name="OCAPComponentesfqsForm" property="CCompfqs_id" />"><img
												src="imagenes/imagenes_ocap/lupa.gif" alt="ver datos" /></a></td>
									</tr>
								</logic:iterate>
							</logic:present>

						</logic:notPresent>
					</table>
					<%}%>
					<%if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL)){%>
					<table class="tablaEvaluadores">
						<logic:notPresent name="sinDatos">
							<tr>
								<th class="col2" id="dApellidos">Apellidos</th>
								<th class="col3" id="dNombre">Nombre</th>
								<th class="col4" id="dConvocatoriaCorto">Convocatoria</th>
								<th class="col4" id="cDni">NIF/NIE</th>
								<th class="col4" id="dGrado">Grado</th>
							</tr>
							<logic:present name="listados" property="elementos">
								<logic:iterate id="elemento" name="listados"
									property="elementos">
									<bean:define id="categoria" name="elemento"
									type="OCAPUsuariosOT" />
									<tr>
										<td class="col2" headers="dApellidos"
											style="vertical-align: bottom;"><bean:write
												name="elemento" property="DApellido1" /></td>
										<td class="col3" headers="dNombre"
											style="vertical-align: bottom;"><bean:write
												name="elemento" property="DNombre" /></td>
										<td class="col4" headers="dConvocatoriaCorto"
											style="vertical-align: bottom;"><bean:write
												name="elemento" property="DConvocatoriaNombreCorto" /></td>	
										<td class="col4" headers="cDni"
											style="vertical-align: bottom;"><bean:write
												name="elemento" property="CDni" /></td>
										<td class="col4" headers="dGrado"
											style="vertical-align: bottom;"><bean:write
												name="elemento" property="DGrado_des" /></td>												
											<td class="campoIcono"><a
											href="OCAPCuestionarios.do?accion=irListar&expId=<bean:write name="elemento" property="CExpId" />&cCompfqsIdS=<bean:write name="OCAPComponentesfqsForm" property="CCompfqs_id" />&tipo=<%=Constantes.INF_LISTADO_FASE_III%>&bSegundaEvaluacion=<bean:write name="elemento" property="BSegundaEvaluacion" />">
												<% if (session.getAttribute("eval") != null ) {%> <% if (Constantes.EVAL_1 == Integer.parseInt(session.getAttribute("eval").toString()) ) {%>
												<img src="imagenes/imagenes_ocap/lupaI.gif"
												alt="ver Itinerario" /> <% } else { %> <img
												src="imagenes/imagenes_ocap/lupaI2.gif" alt="ver Itinerario" />
												<% } %> <% } else { %> <img
												src="imagenes/imagenes_ocap/lupa.gif" alt="ver Itinerario" />
												<% } %>
										</a></td>

											<td class="campoIcono">
											<%if(categoria.getFInformeEval() != null){%> <a
											href="OCAPCuestionarios.do?accion=generarInforme&expId=<bean:write name="categoria" property="CExpId" />&cCompfqsIdS=<bean:write name="OCAPComponentesfqsForm" property="CCompfqs_id" />">
												<img src="imagenes/imagenes_ocap/lupaE.gif"
												alt="ver informe evaluador" />
										</a> <%}%>
										</td>
									</tr>
								</logic:iterate>
							</logic:present>

						</logic:notPresent>
					</table>
					<%}%>
					<%if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)){%>
					<logic:notPresent name="sinDatos">
						<logic:iterate id="listaTotal" name="listados"
							property="elementos" indexId="index">
							<bean:define id="categ" name="listaTotal" type="OCAPUsuariosOT" />
							<div class="tituloGrado">
								<bean:write name="categ" property="DProfesional_nombre" />
							</div>
							<table border="0" class="tablaEvaluadores listaEvaluados">
								<tr>
									<th class="colCodigo" id="cCodigo">C&oacute;digo</th>
									<th class="icono">&nbsp;</th>
									<th class="icono">&nbsp;</th>
								</tr>
								<logic:iterate id="listaEvaluados" name="categ"
									property="listaCategorias" type="OCAPUsuariosOT">
									<bean:define id="categoria" name="listaEvaluados"
										type="OCAPUsuariosOT" />
									<tr>
										<td><bean:write name="categoria" property="codigoId" /></td>
										<td class="campoIcono">
											<%if(categoria.getFInformeCC () != null){%> <a
											href="OCAPCuestionarios.do?accion=irInformeCC&expId=<bean:write name="categoria" property="CExpId" />">
												<img class="lupa2" src="imagenes/imagenes_ocap/lupaifqs.gif"
												alt="ver informe certificaci�n" />
										</a> <%} else { %> <a
											href="OCAPSolicitudes.do?accion=irDetalle&CExp_id=<bean:write name="categoria" property="CExpId" />&opcion=<%=Constantes.OCAP_CC%>">
												<img src="imagenes/imagenes_ocap/lupa.gif"
												alt="ver historial" />
										</a>
										<td>
											<!-- Solo ve informe evaluador si no tiene informe de CTE -->
											<% if (categoria.getFInformeCte () == null) { %> <%if(categoria.getFInformeEval() != null){%>
											<a
											href="OCAPCuestionarios.do?accion=irInformeCC&expId=<bean:write name="categoria" property="CExpId" />">
												<img src="imagenes/imagenes_ocap/lupaE.gif"
												alt="ver informe evaluador" />
										</a> <%}%> <% } %>
										</td>
										<% } %>
										</td>
									</tr>
								</logic:iterate>
							</table>
						</logic:iterate>
					</logic:notPresent>
					<%}%>
					<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR) )){%>
					<logic:notPresent name="sinDatos">
						<logic:iterate id="listaTotal" name="listados"
							property="elementos" indexId="index">
							<bean:define id="categ" name="listaTotal" type="OCAPUsuariosOT" />
							<div class="tituloGrado">
								<bean:write name="categ" property="DProfesional_nombre" />
							</div>
							<table border="0" class="tablaEvaluadores listaEvaluados">
								<tr>
									<%if(jcylUsuario.getUser().getRol() != null && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)){%>
									<th class="colCodigo" id="cCodigo">C&oacute;digo</th>
									<%}%>
									<th class="colNombre" id="dNombre">Nombre</th>
									<th class="colApellidos" id="dApellidos">Apellidos</th>
									<%if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION)){%>
									<th class="colNombre" id="cDni">NIF/NIE</th>
									<th class="colConvocatoria" id="cCodigo">Convocatoria</th>
									<%}%>									
									<th class="icono">&nbsp;</th>
									<th class="icono">&nbsp;</th>
									<th class="icono">&nbsp;</th>
									<th class="icono">&nbsp;</th>
									<th class="icono">&nbsp;</th>
									<th class="icono">&nbsp;</th>
									<th class="icono">&nbsp;</th>
									<th class="icono">&nbsp;</th>
									<th class="icono">&nbsp;</th>
								</tr>
								<logic:iterate id="listaEvaluados" name="categ"
									property="listaCategorias" type="OCAPUsuariosOT">
									<bean:define id="categoria" name="listaEvaluados"
										type="OCAPUsuariosOT" />
									<tr>
										<%if(jcylUsuario.getUser().getRol() != null && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)){%>
										<td><bean:write name="categoria" property="codigoId" /></td>
										<%}%>
										<td><bean:write name="categoria" property="DNombre" /></td>
										<td><bean:write name="categoria" property="DApellido1" /></td>
										<%if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION)){%>
										<td> <bean:write	name="categoria" property="CDni" /></td>
										<td><bean:write name="categoria" property="DConvocatoriaNombreCorto" /></td>
										<%}%>										
										
										
										<td class="campoIcono">
											<%if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)){%>
											<a
											href="OCAPSolicitudes.do?accion=irDetalle&CExp_id=<bean:write name="categoria" property="CExpId" />&opcion=<%=Constantes.OCAP_CC%>">
												<img src="imagenes/imagenes_ocap/lupa.gif"
												alt="ver historial" />
										</a> <% } else { %> <a
											href="OCAPSolicitudes.do?accion=irDetalle&CExp_id=<bean:write name="categoria" property="CExpId" />&opcion=<%=Constantes.OCAP_DIRECCION%>">
												<img src="imagenes/imagenes_ocap/lupa.gif"
												alt="ver historial" />
										</a> <% } %>
										</td>

										<%if(jcylUsuario.getUser().getRol() != null && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)){%>
										<td class="campoIcono">
											<%if(categoria.getCItinerarioId() != 0){%> <a
											href="OCAPCuestionarios.do?accion=irListar&expId=<bean:write name="categoria" property="CExpId" />&bSegundaEvaluacion=<%=Constantes.NO%>">
												<img src="imagenes/imagenes_ocap/lupaI.gif"
												alt="ver itinerario" />
										</a> <%}%>
										</td>
										<td class="campoIcono">
											<%if(categoria.getFInformeEval() != null){%> <a
											href="OCAPCuestionarios.do?accion=generarInforme&expId=<bean:write name="categoria" property="CExpId" />">
												<img src="imagenes/imagenes_ocap/lupaE.gif"
												alt="ver informe evaluador" />
										</a> <%}%>
										</td>
										<td class="campoIcono">
											<%if(categoria.getFInformeCte() != null){%> <a
											href="OCAPCuestionarios.do?accion=generarInformeCTE&expId=<bean:write name="categoria" property="CExpId" />">
												<img src="imagenes/imagenes_ocap/lupaCTE.gif"
												alt="ver informe CTE" />
										</a> <%}%>
										</td>
										<!-- Eliminado porque ahora es el CTE quien realiza los cambios de estado
                                       <td class="campoIcono">
                                          <%if(categoria.getFInformeCe() != null){%>                                        
                                             <a href="OCAPCuestionarios.do?accion=generarInformeCE&expId=<bean:write name="categoria" property="CExpId" />&opcion=<%=Constantes.OCAP_DIRECCION%>">
                                                <img class="lupa1" src="imagenes/imagenes_ocap/lupaCE.gif" alt="ver informe CE" />
                                             </a>                                                                               
                                          <%}%>
                                       </td>
                                       
                                       <%if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION) && categoria.getFInformeCC () != null){%> 
                                          <td class="campoIcono">
                                             <a href="OCAPCuestionarios.do?accion=irInformeCC&expId=<bean:write name="categoria" property="CExpId" />">
                                                <img class="lupa2" src="imagenes/imagenes_ocap/lupaifqs.gif" alt="ver informe certificaci�n" />
                                             </a>
                                          </td>
                                        <%}%>-->
										<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION) ||jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR))){%>
										<% if (Constantes.SI.equals(categoria.getBSegundaEvaluacion())) { %>
										<td class="campoIcono"><a
											href="OCAPCuestionarios.do?accion=irListar&expId=<bean:write name="categoria" property="CExpId" />&bSegundaEvaluacion=<bean:write name="categoria" property="BSegundaEvaluacion" />">
												<img src="imagenes/imagenes_ocap/lupaI2.gif"
												alt="ver itinerario" />
										</a></td>
										<% } %>
										<% if (categoria.getFAuditoriaPropuesta() != null) { %>
										<td class="campoIcono"><a
											href="OCAPCuestionarios.do?accion=generarInformeAuditoria&expId=<bean:write name="categoria" property="CExpId" />">
												<img class="lupa2" src="imagenes/imagenes_ocap/lupaA2.gif"
												alt="ver informe auditor�a" />
										</a></td>
										<% } %>
										<% if (categoria.getFInformeEval2() != null) { %>
										<td class="campoIcono"><a
											href="OCAPCuestionarios.do?accion=generarInforme2&expId=<bean:write name="categoria" property="CExpId" />">
												<img class="lupa2" src="imagenes/imagenes_ocap/lupaE2.gif"
												alt="ver informe segunda evaluaci�n" />
										</a></td>
										<% } %>

										<td class="campoIcono"><a
											href="OCAPCuestionarios.do?accion=generarInforme&cCompfqsIdS=<bean:write name="OCAPComponentesfqsForm" property="CCompfqs_id" />&expId=<bean:write name="categoria" property="CExpId" />">
												<img src="imagenes/imagenes_ocap/action_forward.gif"
												class="colocaImgPrint" alt="Generar Informe Evaluador" />

										</a></td>


										<%}%>
										<%}else{%>
										<!-- Eliminado porque ahora es el CTE quien realiza los cambios de estado
                                       <td class="campoIcono">
                                          <%if(categoria.getFInformeCC () != null  ){%>
                                             <a href="OCAPCuestionarios.do?accion=irInformeCC&expId=<bean:write name="categoria" property="CExpId" />">
                                                <img class="lupa2" src="imagenes/imagenes_ocap/lupaifqs.gif" alt="ver informe certificaci�n" />
                                             </a>
                                          <%}%>
                                       </td>
                                       -->
										<%}%>
									</tr>
								</logic:iterate>
							</table>
						</logic:iterate>
					</logic:notPresent>
					<%}%>
					<logic:present name="listados" property="elementos">
						<div class="paginacionEvaluadores"><%@ include
								file="/comun/paginacion.jsp"%></div>
					</logic:present>

				</fieldset>
			</logic:equal>

		</html:form>

	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->
