<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<% 
  String convocaAux = "";
  String convocaAct = "";  
  
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script>
       
function cargarComboLocalidadesIns(){
      enviar('OCAPEvaluadores.do?accion=cargarComboLocalidades&CProv_id='+ document.forms[0].CProv_id.value+'&jspVuelta=<%=request.getAttribute("tipoAccion")%>&opcion=<%=request.getAttribute("opcion")%>');
}  

function cargarComboLocalidadesMod(){
      enviar('OCAPEvaluadores.do?accion=cargarComboLocalidades&CProv_id='+ document.forms[0].CProv_id.value+'&jspVuelta=<%=request.getAttribute("tipoAccion")%>&nombre=<%=request.getAttribute("nombre")%>&opcion=<%=request.getAttribute("opcion")%>&tarea=<%=request.getAttribute("tarea")%>');
} 

function cargarComboProvinciasPers(){
      enviar('OCAPEvaluadores.do?accion=cargarComboProvincias&jspVuelta=<%=request.getAttribute("tipoAccion")%>&opcion=<%=request.getAttribute("opcion")%>&tarea=<%=request.getAttribute("tarea")%>&tipo=<%=Constantes.INF_DATOS_PERS%>&nombre=<%=request.getAttribute("nombre")%>');
}

function cargarComboProvinciasProf(){
      enviar('OCAPEvaluadores.do?accion=cargarComboProvincias&jspVuelta=<%=request.getAttribute("tipoAccion")%>&opcion=<%=request.getAttribute("opcion")%>&tarea=<%=request.getAttribute("tarea")%>&tipo=<%=Constantes.INF_DATOS_PROF%>');
}

function obtenerListado(convocaAux){
   document.forms[0].resumenConv.value=convocaAux;  
}

function obtenerListadoAct(convocaAct){
   document.forms[0].resumenAct.value=convocaAct;  
}

function cargarCtes(){
   enviar('OCAPEvaluadores.do?accion=irEditar&cCompfqsIdS='+document.forms[0].CCompfqs_id.value+'&opcion=<%=request.getParameter("opcion")%>&tarea=<%=Constantes.ACTIVAR%>&cConvocatoriaId='+document.forms[0].CConvocatoria_id.value);
}

function cargarComboCategorias(){
   enviar('OCAPEvaluadores.do?accion=cargarComboCategorias&opcion=<%=request.getParameter("opcion")%>&tipo=<%=Constantes.ACTIVAR%>');
}

function cargarComboEspecialidades(){
   enviar('OCAPEvaluadores.do?accion=cargarComboEspecialidades&opcion=<%=request.getParameter("opcion")%>&tipo=<%=Constantes.ACTIVAR%>');
}

function cargarComboItinerarios(){
   enviar('OCAPEvaluadores.do?accion=cargarComboItinerarios&opcion=<%=request.getParameter("opcion")%>&tipo=<%=Constantes.ACTIVAR%>');
}

</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoDCP1A">
		<html:form action="/OCAPEvaluadores.do">
			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<h3 class="subTituloContenido">Alta de Evaluadores</h3>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<h3 class="subTituloContenido">Detalle de Evaluadores</h3>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
				<logic:equal name="tarea" value="<%=Constantes.ACTIVAR%>">
					<h3 class="subTituloContenido">Activaci&oacute;n de
						convocatoria</h3>
				</logic:equal>
				<logic:notEqual name="tarea" value="<%=Constantes.ACTIVAR%>">
					<h3 class="subTituloContenido">Modificaci&oacute;n de
						Evaluadores</h3>
				</logic:notEqual>
			</logic:equal>

			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<html:hidden property="CCompfqs_id" />&nbsp;
         </logic:notEqual>

			<div class="textoRojo">
				<html:messages id="Evaluador" property="Evaluador" message="true">
					<bean:write name="Evaluador" />
					<br />
				</html:messages>
				<html:messages id="dApellidos" property="dApellidos" message="true">
					<bean:write name="dApellidos" />
					<br />
				</html:messages>
				<html:messages id="dNombre" property="dNombre" message="true">
					<bean:write name="dNombre" />
					<br />
				</html:messages>
				<html:messages id="cDni" property="cDni" message="true">
					<bean:write name="cDni" />
					<br />
				</html:messages>
				<html:messages id="aCorreoelectronico" property="aCorreoelectronico"
					message="true">
					<bean:write name="aCorreoelectronico" />
					<br />
				</html:messages>
				<html:messages id="nTelefono1" property="nTelefono1" message="true">
					<bean:write name="nTelefono1" />
					<br />
				</html:messages>
				<html:messages id="aDirNombre" property="aDirNombre" message="true">
					<bean:write name="aDirNombre" />
					<br />
				</html:messages>
				<html:messages id="cCp" property="cCp" message="true">
					<bean:write name="cCp" />
					<br />
				</html:messages>
				<html:messages id="cComunidadId" property="cComunidadId"
					message="true">
					<bean:write name="cComunidadId" />
					<br />
				</html:messages>
				<html:messages id="cProvId" property="cProvId" message="true">
					<bean:write name="cProvId" />
					<br />
				</html:messages>
				<html:messages id="cLocalidadId" property="cLocalidadId"
					message="true">
					<bean:write name="cLocalidadId" />
					<br />
				</html:messages>
				<html:messages id="aTitulacion" property="aTitulacion"
					message="true">
					<bean:write name="aTitulacion" />
					<br />
				</html:messages>
				<html:messages id="aCentrotrabajo" property="aCentrotrabajo"
					message="true">
					<bean:write name="aCentrotrabajo" />
					<br />
				</html:messages>
				<html:messages id="cComuniId" property="cComuniId" message="true">
					<bean:write name="cComuniId" />
					<br />
				</html:messages>
				<html:messages id="dProvNombre" property="dProvNombre"
					message="true">
					<bean:write name="dProvNombre" />
					<br />
				</html:messages>
				<html:messages id="aCategoria" property="aCategoria" message="true">
					<bean:write name="aCategoria" />
					<br />
				</html:messages>
				<html:messages id="CConvocatoriaId" property="CConvocatoriaId"
					message="true">
					<bean:write name="CConvocatoriaId" />
					<br />
				</html:messages>
				<html:messages id="CGradoId" property="CGradoId" message="true">
					<bean:write name="CGradoId" />
					<br />
				</html:messages>
				<html:messages id="Componente" property="Componente" message="true">
					<bean:write name="Componente" />
					<br />
				</html:messages>
				<html:messages id="dCategoria" property="dCategoria" message="true">
					<bean:write name="dCategoria" />
					<br />
				</html:messages>
				<html:messages id="cEspec" property="cEspec" message="true">
					<bean:write name="cEspec" />
					<br />
				</html:messages>
				<html:messages id="cItinerario" property="cItinerario"
					message="true">
					<bean:write name="cItinerario" />
					<br />
				</html:messages>
				<html:messages id="cCteId" property="cCteId" message="true">
					<bean:write name="cCteId" />
					<br />
				</html:messages>
				<html:messages id="nAniosConv" property="nAniosConv" message="true">
					<bean:write name="nAniosConv" />
					<br />
				</html:messages>
			</div>

			<fieldset>
				<legend class="tituloLegend"> Datos Personales </legend>
				<div class="cuadroFieldset formulario modificacionEvaluadores">
					<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<div class="dosTercios ajustarIE7">
							<label for="idVApell1"> Apellidos: * </label>
							<html:text property="DApellidos" tabindex="1" maxlength="60" />
						</div>
						<div class="unTercio ajustarIE7">
							<label for="idVNombre"> Nombre: * </label>
							<html:text property="DNombre" tabindex="2" maxlength="30" />
						</div>
						<div class="unTercio m0 saltoL">
							<label for="idVDNI"> NIF/NIE: *</label>
							<html:text styleClass="ajusteIE" property="CDni" tabindex="3"
								maxlength="10" />
						</div>
					</logic:equal>

					<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<% if (Constantes.OCAP_CE.equals(jcylUsuario.getUser().getRol())) { %>
						<div class="todo">
							<label for="idVDNI" class="nombreLargo"> C&oacute;digo
								Evaluador: <span><bean:write
										name="OCAPComponentesfqsForm" property="codigoEvaluador" /></span>
							</label>
						</div>
						<html:hidden property="CDni" />
						<% } %>
						<div class="dosTercios ajustarIE7">
							<label for="idVApell1"> Apellidos:</label> <span
								class="resultado"><bean:write
									name="OCAPComponentesfqsForm" property="DApellidos" /></span>
							<html:hidden property="DApellidos" />
						</div>
						<div class="unTercio">
							<label for="idVNombre"> Nombre: </label> <span class="resultado"><bean:write
									name="OCAPComponentesfqsForm" property="DNombre" /></span>
							<html:hidden property="DNombre" />
						</div>
						<% if (!Constantes.OCAP_CE.equals(jcylUsuario.getUser().getRol())) { %>
						<div class="dosTercios">
							<label for="idVDNI"> NIF/NIE:</label> <span
								class="resultado corto "><bean:write
									name="OCAPComponentesfqsForm" property="CDni" /></span>
							<html:hidden property="CDni" />
						</div>
						<% } %>
					</logic:notEqual>


					<!-- EVALUADOR REGISTRO (SIN CONVOCATORIA) Y COMPONENTE CTE-->
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<logic:notEqual name="opcion" value="<%=Constantes.FQS_CP%>">
							<div class="todo">
								<label class="nombreLargo" for="idVCorreo"> Correo
									Electr&oacute;nico: </label>
								<html:text property="ACorreoelectronico" tabindex="5"
									styleClass="cuadroTodoP" maxlength="50" />
							</div>
							<div class="dosTercios">
								<label class="nombreLargo" for="idVTelefono">
									Tel&eacute;fono: </label>
								<html:text styleClass="corto" property="NTelefono1" tabindex="6"
									maxlength="9" />
							</div>
						</logic:notEqual>

						<!-- EVALUADOR CP (CON CONVOCATORIA) -->
						<logic:equal name="opcion" value="<%=Constantes.FQS_CP%>">
							<logic:notEqual name="tarea" value="<%=Constantes.REGISTRAR%>">
								<div class="todo">
									<label for="idVCorreo" class="nombreLargo"
										style="width: 10.65em;"> Correo Electr&oacute;nico:</label> <span
										class="resultadoP" style="height: 0.9em;"><bean:write
											name="OCAPComponentesfqsForm" property="ACorreoelectronico" /></span>
								</div>
								<html:hidden property="ACorreoelectronico" />
								<div class="todo">
									<label for="idVTelefono" class="nombreLargo"
										style="width: 10.65em;"> Tel&eacute;fono:</label> <span
										class="resultadoP corto" style="height: 0.9em;"><bean:write
											name="OCAPComponentesfqsForm" property="NTelefono1" /></span>
								</div>
								<html:hidden property="NTelefono1" />
							</logic:notEqual>
							<logic:equal name="tarea" value="<%=Constantes.REGISTRAR%>">
								<div class="todo">
									<label class="nombreLargo" for="idVCorreo"
										style="width: 10.65em;"> Correo Electr&oacute;nico: </label>
									<html:text property="ACorreoelectronico" tabindex="5"
										styleClass="cuadroTodoP" maxlength="50" />
								</div>
								<div class="dosTercios">
									<label class="nombreLargo" for="idVTelefono"
										style="width: 10.65em;"> Tel&eacute;fono: </label>
									<html:text styleClass="corto" property="NTelefono1"
										tabindex="6" maxlength="9" />
								</div>
							</logic:equal>
						</logic:equal>
					</logic:notEqual>

					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<% if (!Constantes.OCAP_CE.equals(jcylUsuario.getUser().getRol())) { %>
						<label for="idVCorreo" class="colocaDatosVis"> Correo
							Electr&oacute;nico: <span><bean:write
									name="OCAPComponentesfqsForm" property="ACorreoelectronico" /></span>
						</label>
						<html:hidden property="ACorreoelectronico" />
						<label for="idVTelefono" class="colocaDatosVisDecha2">
							Tel&eacute;fono: <span><bean:write
									name="OCAPComponentesfqsForm" property="NTelefono1" /></span>
						</label>
						<html:hidden property="NTelefono1" />
						<% } %>
					</logic:equal>

					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<!-- DIRECCION POSTAL (EVALUADORES REGISTRO) Y COMPONENTES -->
						<logic:notEqual name="opcion" value="<%=Constantes.FQS_CP%>">
							<fieldset class="normales">
								<legend class="tituloLegend"> Direcci&oacute;n Postal </legend>
								<div class="cuadroFieldset">
									<div class="todo">
										<label for="idVNombre"> Calle: </label>
										<html:text property="ADir_nombre" tabindex="6"
											styleClass="cuadroTodoG" maxlength="40" />
									</div>
									<div class="unTercio m0">
										<label for="idVNombre"> N&uacute;mero:</label>
										<html:text property="ADir_num" tabindex="7" maxlength="5" />
									</div>
									<div class="unTercio">
										<label for="idVNombre"> Escalera:</label>
										<html:text property="ADir_escalera" tabindex="8" maxlength="5" />
									</div>
									<div class="unTercio ajusteIE ajustarIE7">
										<label for="idVNombre"> Piso: </label>
										<html:text property="ADir_piso" tabindex="9" maxlength="2" />
									</div>

									<div class="unTercio ajustarIE7">
										<label for="idVNombre"> Letra: </label>
										<html:text property="ADir_letra" tabindex="10" maxlength="2" />
									</div>

									<div class="dosTercios">
										<label class="nombreMediano" for="idVNombre">
											C&oacute;digo Postal: </label>
										<html:text property="CCp" tabindex="11" styleClass="corto"
											maxlength="5" />
									</div>

									<div class="todo">
										<label for="idGrado" class="nombreLargo">Comunidad
											aut&oacute;noma: </label>
										<html:select property="CComunidad_id" styleClass="cuadroTodoP"
											size="1" tabindex="12"
											onchange="javascript:cargarComboProvinciasPers();">
											<html:option value="">Seleccione...</html:option>
											<html:options collection="<%=Constantes.COMBO_COMUNIDADES%>"
												property="CComuniId" labelProperty="DComuni" />
										</html:select>
									</div>

									<%if(((ArrayList) session.getAttribute(Constantes.COMBO_PROVINCIAS)).size() > 0 ){%>
									<div class="todo">
										<label class="nombreLargo" for="idVCategoria">
											Provincia: </label>
										<logic:equal name="tipoAccion"
											value="<%=Constantes.MODIFICAR%>">
											<html:select property="CProv_id" styleClass="cuadroTodoP"
												size="1" tabindex="13"
												onchange="javascript:cargarComboLocalidadesMod();">
												<html:option value="">Seleccione...</html:option>
												<html:options collection="<%=Constantes.COMBO_PROVINCIAS%>"
													property="CProvinciaId" labelProperty="DProvincia" />
											</html:select>
										</logic:equal>
										<logic:equal name="tipoAccion"
											value="<%=Constantes.INSERTAR%>">
											<html:select property="CProv_id" styleClass="cuadroTodoP"
												size="1" tabindex="14"
												onchange="javascript:cargarComboLocalidadesIns();">
												<html:option value="">Seleccione...</html:option>
												<html:options collection="<%=Constantes.COMBO_PROVINCIAS%>"
													property="CProvinciaId" labelProperty="DProvincia" />
											</html:select>
										</logic:equal>
									</div>

									<%if(((ArrayList) session.getAttribute(Constantes.COMBO_LOCALIDADES)).size() > 0 ){%>
									<div class="todo ajusteIE ajustarIE7">
										<label class="nombreLargo" for="idVLocalidades">
											Localidad: </label>
										<html:select property="CLocalidad_id" styleClass="cuadroTodoP"
											size="1" tabindex="15">
											<html:option value="">Seleccione...</html:option>
											<html:options collection="<%=Constantes.COMBO_LOCALIDADES%>"
												property="DLocalidad" />
										</html:select>
									</div>
									<%}%>
									<%}%>
								</div>
							</fieldset>
						</logic:notEqual>
						<logic:equal name="opcion" value="<%=Constantes.FQS_CP%>">
							<logic:equal name="tarea" value="<%=Constantes.REGISTRAR%>">
								<fieldset class="normales">
									<legend class="tituloLegend"> Direcci&oacute;n Postal
									</legend>
									<div class="cuadroFieldset cuadroEspecial">
										<div class="todo">
											<label for="idVNombre"> Calle: </label>
											<html:text property="ADir_nombre" tabindex="6"
												styleClass="cuadroTodoG" maxlength="40" />
										</div>
										<div class="unTercio m0">
											<label for="idVNombre"> N&uacute;mero:</label>
											<html:text property="ADir_num" tabindex="7" maxlength="5" />
										</div>
										<div class="unTercio">
											<label for="idVNombre"> Escalera:</label>
											<html:text property="ADir_escalera" tabindex="8"
												maxlength="5" />
										</div>
										<div class="unTercio ajusteIE ajustarIE7">
											<label for="idVNombre"> Piso: </label>
											<html:text property="ADir_piso" tabindex="9" maxlength="2" />
										</div>

										<div class="unTercio ajustarIE7">
											<label for="idVNombre"> Letra: </label>
											<html:text property="ADir_letra" tabindex="10" maxlength="2" />
										</div>

										<div class="dosTercios">
											<label class="nombreMediano" for="idVNombre">
												C&oacute;digo Postal: </label>
											<html:text property="CCp" tabindex="11" styleClass="corto"
												maxlength="5" />
										</div>

										<div class="todo">
											<label for="idGrado" class="nombreLargo">Comunidad
												aut&oacute;noma: </label>
											<html:select property="CComunidad_id"
												styleClass="cuadroTodoP" size="1" tabindex="12"
												onchange="javascript:cargarComboProvinciasPers();">
												<html:option value="">Seleccione...</html:option>
												<html:options collection="<%=Constantes.COMBO_COMUNIDADES%>"
													property="CComuniId" labelProperty="DComuni" />
											</html:select>
										</div>

										<%if(((ArrayList) session.getAttribute(Constantes.COMBO_PROVINCIAS)).size() > 0 ){%>
										<div class="todo">
											<label class="nombreLargo" for="idVCategoria">
												Provincia: </label>
											<logic:equal name="tipoAccion"
												value="<%=Constantes.MODIFICAR%>">
												<html:select property="CProv_id" styleClass="cuadroTodoP"
													size="1" tabindex="13"
													onchange="javascript:cargarComboLocalidadesMod();">
													<html:option value="">Seleccione...</html:option>
													<html:options collection="<%=Constantes.COMBO_PROVINCIAS%>"
														property="CProvinciaId" labelProperty="DProvincia" />
												</html:select>
											</logic:equal>
											<logic:equal name="tipoAccion"
												value="<%=Constantes.INSERTAR%>">
												<html:select property="CProv_id" styleClass="cuadroTodoP"
													size="1" tabindex="14"
													onchange="javascript:cargarComboLocalidadesIns();">
													<html:option value="">Seleccione...</html:option>
													<html:options collection="<%=Constantes.COMBO_PROVINCIAS%>"
														property="CProvinciaId" labelProperty="DProvincia" />
												</html:select>
											</logic:equal>
										</div>
										<%}%>
										<%if(((ArrayList) session.getAttribute(Constantes.COMBO_LOCALIDADES)).size() > 0 ){%>
										<div class="todo ajustarIE7">
											<label class="nombreLargo modEvalLoc" for="idVLocalidades">
												Localidad: </label>
											<html:select property="CLocalidad_id"
												styleClass="cuadroTodoP" size="1" tabindex="15">
												<html:option value="">Seleccione...</html:option>
												<html:options collection="<%=Constantes.COMBO_LOCALIDADES%>"
													property="DLocalidad" />
											</html:select>
										</div>
										<%}%>
									</div>
								</fieldset>
							</logic:equal>
						</logic:equal>
					</logic:notEqual>

					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<% if (!Constantes.OCAP_CE.equals(jcylUsuario.getUser().getRol())) { %>
						<!-- DIRECCION POSTAL  -->
						<fieldset class="normales">
							<legend class="tituloLegend"> Direcci&oacute;n Postal </legend>
							<div class="cuadroFieldset cuadroEspecial">
								<label for="idVNombre" class="colocaDatosVisDecha">
									Calle: <span><bean:write name="OCAPComponentesfqsForm"
											property="ADir_nombre" /></span>
								</label> <br />
								<br /> <label for="idVNombre" class="colocaDatosVisDecha">
									N&uacute;mero: <span><bean:write
											name="OCAPComponentesfqsForm" property="ADir_num" /></span>
								</label> <label for="idVNombre" class="colocaDatosVisDecha">
									Escalera: <span><bean:write
											name="OCAPComponentesfqsForm" property="ADir_escalera" /></span>
								</label> <br />
								<br /> <label for="idVNombre" class="colocaDatosVisDecha">
									Piso: <span><bean:write name="OCAPComponentesfqsForm"
											property="ADir_piso" /></span>
								</label> <label for="idVNombre" class="colocaDatosVisDecha">
									Letra: <span><bean:write name="OCAPComponentesfqsForm"
											property="ADir_letra" /></span>
								</label> <br />
								<br /> <label for="idVNombre" class="colocaDatosVisDecha">
									C&oacute;digo Postal: <span><bean:write
											name="OCAPComponentesfqsForm" property="CCp" /></span>
								</label> <label for="idVCategoria" class="colocaDatosVisDecha">
									Provincia: <span><bean:write
											name="OCAPComponentesfqsForm" property="DProv_nombre" /></span>
								</label> <br />
								<br /> <label for="idVLocalidades" class="colocaDatosVisDecha">
									Localidad: <span><bean:write
											name="OCAPComponentesfqsForm" property="CLocalidad_id" /></span>
								</label>
							</div>
						</fieldset>
						<% } %>
					</logic:equal>
				</div>
			</fieldset>

			<logic:notEqual name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<logic:notEqual name="opcion" value="<%=Constantes.FQS_CP%>">
					<fieldset>
						<legend class="tituloLegend"> Datos Profesionales </legend>
						<div class="cuadroFieldset formulario modificacionEvaluadores">
							<div class="todo">
								<label class="nombreMediano" for="idVCategoria">
									Titulaci&oacute;n: </label>
								<html:text property="ATitulacion" tabindex="21"
									styleClass="cuadroTodoM" maxlength="60" />
							</div>

							<div class="todo">
								<label class="nombreMediano" for="idVEspecialidad">
									Especialidad:</label>
								<html:text property="AEspecialidad" tabindex="22"
									styleClass="cuadroTodoM" maxlength="60" />
							</div>

							<div class="dosTercios">
								<label for="idGrado" class="nombreLinea"> Grado de
									Carrera Profesional reconocido:</label>
							</div>
							<div class="unTercio">
								<html:select styleClass="completo" property="CGrado_id" size="1"
									tabindex="24">
									<html:option value="">Seleccione...</html:option>
									<html:options
										collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
										property="CGradoId" labelProperty="DNombre" />
								</html:select>
							</div>

							<div class="todo">
								<label class="nombreLargo" for="idVEspecialidad"> Centro
									de Trabajo: </label>
								<html:text property="ACentrotrabajo" tabindex="25"
									styleClass="cuadroTodoP" maxlength="60" />
							</div>

							<div class="dosTercios">
								<label for="idGrado" class="nombreLinea">Comunidad
									aut&oacute;noma de procedencia: </label>
							</div>
							<div class="unTercio">
								<html:select property="CComuni_id" styleClass="completo"
									size="1" tabindex="26"
									onchange="javascript:cargarComboProvinciasProf();">
									<html:option value="">Seleccione...</html:option>
									<html:options
										collection="<%=Constantes.COMBO_COMUNIDADES_PROF%>"
										property="CComuniId" labelProperty="DComuni" />
								</html:select>
							</div>
							<div class="dosTercios">
								<label for="idGrado" class="obligado">Provincia: </label>
							</div>
							<div class="unTercio">
								<html:select property="CProvincia_id" styleClass="completo"
									size="1" tabindex="27">
									<html:option value="">Seleccione...</html:option>
									<html:options
										collection="<%=Constantes.COMBO_PROVINCIAS_PROF%>"
										property="CProvinciaId" labelProperty="DProvincia" />
								</html:select>
							</div>
						</div>
					</fieldset>

					<fieldset class="formulario">
						<legend class="tituloLegend"> Actividad Profesional </legend>
						<div class="cuadroFieldset">
							<label class="nombreLinea" for="idFacredita">Formaci&oacute;n
								espec&iacute;fica en acreditaci&oacute;n / evaluaci&oacute;n:</label>
							<div class="todo">
								<html:textarea property="AForm_acreditacion" tabindex="31"
									cols="" rows="" />
							</div>
							<br />
							<br /> <label class="nombreLinea" for="idFgestion">Formaci&oacute;n
								espec&iacute;fica en gesti&oacute;n sanitaria:</label>
							<div class="todo">
								<html:textarea property="AForm_gestion" tabindex="32" cols=""
									rows="" />
							</div>
							<br />
							<br /> <label class="nombreLinea" for="idFacredita">Experiencia
								profesional en instituciones del sistema sanitario:</label>
							<div class="todo">
								<html:textarea property="AExpprof_ss" tabindex="33" cols=""
									rows="" />
							</div>
							<br />
							<br /> <label class="nombreLinea" for="idFcomunica">Experiencia
								en calidad asistencial: </label>
							<div class="todo">
								<html:textarea property="AExpcal_asistencia" tabindex="34"
									styleClass="taMedianoEvaluadores" cols="" rows="" />
							</div>
							<br />
							<br /> <label class="nombreLinea" for="idFcomunica">Actividades
								docentes y de investigaci&oacute;n realizada: </label>
							<div class="todo">
								<html:textarea property="AAct_docente" tabindex="35"
									styleClass="taMedianoEvaluadores" cols="" rows="" />
							</div>
							<br />
							<br /> <label class="nombreLinea" for="idFcomunica">Formaci&oacute;n
								espec&iacute;fica recibida como evaluador externo desde la FQS:
							</label>
							<div class="todo">
								<html:textarea property="AForm_evaluacion" tabindex="36"
									styleClass="taMedianoEvaluadores" cols="" rows="" />
							</div>
							<br />
							<br />
						</div>
					</fieldset>
				</logic:notEqual>

				<logic:equal name="opcion" value="<%=Constantes.FQS_CP%>">
					<logic:equal name="tarea" value="<%=Constantes.REGISTRAR%>">
						<fieldset class="formulario modificacionEvaluadores">
							<legend class="tituloLegend"> Datos Profesionales </legend>
							<div class="cuadroFieldset formulario modificacionEvaluadores">
								<div class="todo">
									<label class="nombreMediano" for="idVCategoria">
										Titulaci&oacute;n: </label>
									<html:text property="ATitulacion" tabindex="21"
										styleClass="cuadroTodoM" maxlength="60" />
								</div>

								<div class="todo">
									<label class="nombreMediano" for="idVEspecialidad">
										Especialidad:</label>
									<html:text property="AEspecialidad" tabindex="22"
										styleClass="cuadroTodoM" maxlength="60" />
								</div>

								<div class="dosTercios">
									<label for="idGrado" class="nombreLinea"> Grado de
										Carrera Profesional reconocido:</label>
								</div>
								<div class="unTercio">
									<html:select styleClass="completo" property="CGrado_id"
										size="1" tabindex="24">
										<html:option value="">Seleccione...</html:option>
										<html:options
											collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
											property="CGradoId" labelProperty="DNombre" />
									</html:select>
								</div>

								<div class="todo">
									<label class="nombreLargo" for="idVEspecialidad">
										Centro de Trabajo: *</label>
									<html:text property="ACentrotrabajo" tabindex="25"
										styleClass="cuadroTodoP" maxlength="60" />
								</div>

								<div class="dosTercios">
									<label for="idGrado" class="nombreLinea">Comunidad
										aut&oacute;noma de procedencia: </label>
								</div>
								<div class="unTercio">
									<html:select property="CComuni_id" styleClass="completo"
										size="1" tabindex="26"
										onchange="javascript:cargarComboProvinciasProf();">
										<html:option value="">Seleccione...</html:option>
										<html:options
											collection="<%=Constantes.COMBO_COMUNIDADES_PROF%>"
											property="CComuniId" labelProperty="DComuni" />
									</html:select>
								</div>
								<div class="dosTercios">
									<label for="idGrado" class="obligado">Provincia: </label>
								</div>
								<div class="unTercio">
									<html:select property="CProvincia_id" styleClass="completo"
										size="1" tabindex="27">
										<html:option value="">Seleccione...</html:option>
										<html:options
											collection="<%=Constantes.COMBO_PROVINCIAS_PROF%>"
											property="CProvinciaId" labelProperty="DProvincia" />
									</html:select>
								</div>
							</div>
						</fieldset>

						<fieldset class="formulario">
							<legend class="tituloLegend"> Actividad Profesional </legend>
							<div class="cuadroFieldset">
								<div class="todo">
									<label class="completo" for="idFacredita">Formaci&oacute;n
										espec&iacute;fica en acreditaci&oacute;n / evaluaci&oacute;n:</label>
								</div>
								<div class="todo">
									<html:textarea property="AForm_acreditacion" tabindex="31"
										styleClass="taMedianoEvaluadores" cols="" rows="" />
								</div>
								<div class="todo">
									<label class="completo" for="idFgestion">Formaci&oacute;n
										espec&iacute;fica en gesti&oacute;n sanitaria:</label>
								</div>
								<div class="todo">
									<html:textarea property="AForm_gestion" tabindex="32"
										styleClass="taMedianoEvaluadores" cols="" rows="" />
								</div>
								<div class="todo">
									<label class="completo" for="idFacredita">Experiencia
										profesional en instituciones del sistema sanitario:</label>
								</div>
								<div class="todo">
									<html:textarea property="AExpprof_ss" tabindex="33"
										styleClass="taMedianoEvaluadores" cols="" rows="" />
								</div>
								<div class="todo">
									<label class="completo" for="idFcomunica">Experiencia
										en calidad asistencial: </label>
								</div>
								<div class="todo">
									<html:textarea property="AExpcal_asistencia" tabindex="34"
										styleClass="taMedianoEvaluadores" cols="" rows="" />
								</div>
								<div class="todo">
									<label class="completo" for="idFcomunica">Actividades
										docentes y de investigaci&oacute;n realizada: </label>
								</div>
								<div class="todo">
									<html:textarea property="AAct_docente" tabindex="35"
										styleClass="taMedianoEvaluadores" cols="" rows="" />
								</div>
								<div class="todo">
									<label class="completo" for="idFcomunica">Formaci&oacute;n
										espec&iacute;fica recibida como evaluador externo desde la
										FQS: </label>
								</div>
								<div class="todo">
									<html:textarea property="AForm_evaluacion" tabindex="36"
										styleClass="taMedianoEvaluadores" cols="" rows="" />
								</div>

							</div>
						</fieldset>
					</logic:equal>
				</logic:equal>
			</logic:notEqual>

			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<fieldset>
					<legend class="tituloLegend"> Datos Profesionales </legend>
					<div class="cuadroFieldset formulario modificacionEvaluadores">
						<div class="todo ">
							<label class="nombreLargo" for="idVCategoria">
								Titulaci&oacute;n:</label> <span class="resultadoN"><bean:write
									name="OCAPComponentesfqsForm" property="ATitulacion" /></span>
						</div>

						<div class="todo">
							<label class="nombreLargo" for="idVEspecialidad">
								Especialidad:</label> <span class="resultadoN"><bean:write
									name="OCAPComponentesfqsForm" property="AEspecialidad" />&nbsp;</span>
						</div>

						<div class="dosTercios">
							<label for="idGrado" class="nombreLinea"> Grado de
								Carrera Profesional reconocido:</label>
						</div>

						<div class="unTercio">
							<html:select styleClass="completo" property="CGrado_id" size="1"
								tabindex="24" disabled="true">
								<html:options collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
									property="CGradoId" labelProperty="DNombre" />
							</html:select>
						</div>
						<div class="todo">
							<label class="nombreLargo" for="idVEspecialidad"> Centro
								de Trabajo:</label> <span class="resultadoN"><bean:write
									name="OCAPComponentesfqsForm" property="ACentrotrabajo" /></span>
						</div>

						<div class="dosTercios">
							<label for="idGrado" class="nombreLinea">Comunidad
								aut&oacute;noma de procedencia:</label>
						</div>
						<div class="unTercio">
							<html:select property="CComuni_id" styleClass="completo" size="1"
								tabindex="26" disabled="true">
								<html:options
									collection="<%=Constantes.COMBO_COMUNIDADES_PROF%>"
									property="CComuniId" labelProperty="DComuni" />
							</html:select>
						</div>

						<div class="dosTercios">
							<label for="idGrado" class="nombreLinea">Provincia: </label>
						</div>
						<div class="unTercio">
							<html:select property="CProvincia_id" styleClass="completo"
								size="1" tabindex="27" disabled="true">
								<html:options collection="<%=Constantes.COMBO_PROVINCIAS_PROF%>"
									property="CProvinciaId" labelProperty="DProvincia" />
							</html:select>
						</div>
					</div>
				</fieldset>
				<% if (!Constantes.OCAP_CE.equals(jcylUsuario.getUser().getRol())) { %>
				<fieldset>
					<legend class="tituloLegend"> Actividad Profesional </legend>
					<div class="cuadroFieldset">
						<label for="idVCategoria" class="colocaDatosVisGrande">Formaci&oacute;n
							espec&iacute;fica en acreditaci&oacute;n / evaluaci&oacute;n: <span><bean:write
									name="OCAPComponentesfqsForm" property="AForm_acreditacion" /></span>
						</label> <br />
						<br /> <label for="idVCategoria" class="colocaDatosVisGrande">Formaci&oacute;n
							espec&iacute;fica en gesti&oacute;n sanitaria: <span><bean:write
									name="OCAPComponentesfqsForm" property="AForm_gestion" /></span>
						</label> <br />
						<br /> <label for="idVCategoria" class="colocaDatosVisGrande">Experiencia
							profesional en instituciones del sistema sanitario: <span><bean:write
									name="OCAPComponentesfqsForm" property="AExpprof_ss" /></span>
						</label> <br />
						<br /> <label for="idVCategoria" class="colocaDatosVisGrande">Experiencia
							en calidad asistencial: <span><bean:write
									name="OCAPComponentesfqsForm" property="AExpcal_asistencia" /></span>
						</label> <br />
						<br /> <label for="idVCategoria" class="colocaDatosVisGrande">Actividades
							docentes y de investigaci&oacute;n realizada: <span><bean:write
									name="OCAPComponentesfqsForm" property="AAct_docente" /></span>
						</label> <br />
						<br /> <label for="idVCategoria" class="colocaDatosVisGrande">Formaci&oacute;n
							espec&iacute;fica recibida como evaluador externo desde la FQS: <span><bean:write
									name="OCAPComponentesfqsForm" property="AForm_evaluacion" /></span>
						</label> <br />
						<br />
					</div>
				</fieldset>
				<% } %>
			</logic:equal>
			<logic:equal name="opcion" value="<%=Constantes.FQS_CP%>">
				<logic:notEqual name="tarea" value="<%=Constantes.REGISTRAR%>">
					<fieldset>
						<legend class="tituloLegend"> Historial de convocatorias
							anteriores </legend>
						<bean:size id="tamanoLista" name="OCAPComponentesfqsForm"
							property="listadoConv" />
						<logic:equal name="tamanoLista" value="0">
							<br />
							<label class="obligadoTexto"><span class="textoNegro">Su
									perfil no tiene convocatorias previas</span></label>
							<br />
							<br />
						</logic:equal>
						<logic:notEqual name="tamanoLista" value="0">
							<html:hidden property="resumenConv" />
							<div class="cuadroFieldset modificarEvaluadores">
								<table class="tablaListadoCTE">
									<tr>
										<th class="colConvo" headers="dConv">Convocatoria</th>
										<th class="colCte" headers="dCat">Categor&iacute;a</th>
										<th class="colManual" headers="dEsp">Especialidad</th>
										<th class="colManual" headers="dMan">Manual</th>
										<th class="colCte" headers="dCte">CTE</th>
										<th></th>
									</tr>
									<logic:iterate id="elemento" name="OCAPComponentesfqsForm"
										property="listadoConv" type="OCAPComponentesfqsOT">
										<tr>
											<td class="colConvo" headers="dConv"><bean:write
													name="elemento" property="DConvocNombre" /></td>
											<td class="colCte" headers="dCat"><bean:write
													name="elemento" property="DProfesionalNombre" /></td>
											<td class="colManual" headers="dEsp"><bean:write
													name="elemento" property="DEspecNombre" /></td>
											<td class="colManual" headers="dMan"><bean:write
													name="elemento" property="DItinerarioNombre" /></td>
											<td class="colCte" headers="dCte"><bean:write
													name="elemento" property="DCteNombre" /></td>
											<td></td>
											<%
                                       convocaAux = convocaAux + elemento.getDConvocNombre()+ Constantes.SEPARADOR_2 + 
                                                   elemento.getDProfesionalNombre()+ Constantes.SEPARADOR_2 +
                                                   elemento.getDEspecNombre()+ Constantes.SEPARADOR_2 +                                       
                                                   elemento.getDItinerarioNombre()+ Constantes.SEPARADOR_2 +
                                                   elemento.getDCteNombre()+ Constantes.SEPARADOR; 
                                    %>
										</tr>
									</logic:iterate>
								</table>
							</div>
							<script>
                           obtenerListado('<%=convocaAux%>');
                        </script>
						</logic:notEqual>
					</fieldset>
					<fieldset>
						<legend class="tituloLegend"> Convocatorias activas </legend>
						<bean:size id="tamanoListaAct" name="OCAPComponentesfqsForm"
							property="listadoAct" />
						<logic:equal name="tamanoListaAct" value="0">
							<br />
							<label class="obligadoTexto textoNegro"><span>No
									tiene convocatorias activas</span></label>
							<br />
							<br />
						</logic:equal>
						<logic:notEqual name="tamanoListaAct" value="0">
							<html:hidden property="resumenAct" />
							<div class="cuadroFieldset">
								<table class="tablaListadoCTE">
									<tr>
										<th class="colConvo" headers="dConv">Convocatoria</th>
										<th class="colCte" headers="dCat">Categor&iacute;a</th>
										<th class="colManual" headers="dEsp">Especialidad</th>
										<th class="colManual" headers="dIti">Manual</th>
										<th class="colCte" headers="dCte">CTE</th>
										<th></th>
									</tr>
									<logic:iterate id="elemento" name="OCAPComponentesfqsForm"
										property="listadoAct" type="OCAPComponentesfqsOT">
										<tr>
											<td class="colConvo" headers="dConv"><bean:write
													name="elemento" property="DConvocNombre" /></td>
											<td class="colCte" headers="dCat"><bean:write
													name="elemento" property="DProfesionalNombre" /></td>
											<td class="colManual" headers="dEsp"><bean:write
													name="elemento" property="DEspecNombre" /></td>
											<td class="colManual" headers="dMan"><bean:write
													name="elemento" property="DItinerarioNombre" /></td>
											<td class="colCte" headers="dCte"><bean:write
													name="elemento" property="DCteNombre" /></td>
											<td class="campoIcono"><logic:equal name="tarea"
													value="<%=Constantes.MODIFICAR%>">
													<a
														href="OCAPEvaluadores.do?accion=irModificarConv&cConvoIdS=<bean:write name="elemento" property="CCompfqsConvoId"/>&opcion=<%=request.getParameter("opcion")%>">
														<img class="imagenIzda" src="imagenes/editar.gif"
														title="Modificar" alt="Modificar">
													</a>
												</logic:equal></td>
											<%
                                       convocaAct = convocaAct + elemento.getDConvocNombre()+ Constantes.SEPARADOR_2 +
                                                   elemento.getDProfesionalNombre()+ Constantes.SEPARADOR_2 +
                                                   elemento.getDEspecNombre()+ Constantes.SEPARADOR_2 +                                       
                                                   elemento.getDItinerarioNombre()+ Constantes.SEPARADOR_2 +
                                                   elemento.getDCteNombre()+ Constantes.SEPARADOR; 
                                    %>
										</tr>
									</logic:iterate>
								</table>
							</div>
							<script>
                           obtenerListadoAct('<%=convocaAct%>');
                        </script>
						</logic:notEqual>
					</fieldset>
					<logic:equal name="tarea" value="<%=Constantes.ACTIVAR%>">
						<fieldset class="formulario">
							<legend class="tituloLegend"> Activaci&oacute;n de
								convocatoria </legend>
							<div class="cuadroFieldset">
								<div class="todo">
									<label for="idConv" class="nombreMediano">
										Convocatoria: *</label>
									<html:select property="CConvocatoria_id"
										styleClass="cuadroTodoM" size="1" tabindex="10">
										<html:option value="0">Seleccione...</html:option>
										<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
											property="CConvocatoriaId" labelProperty="DNombre" />
									</html:select>
								</div>
								<div class="todo">
									<label for="idConv" class="nombreMediano"> Grado: *</label>
									<html:select property="CGrado_id" styleClass="cuadroTodoM"
										size="1" tabindex="10"
										onchange="javascript:cargarComboCategorias();">
										<html:option value="0">Seleccione...</html:option>
										<html:options
											collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
											property="CGradoId" labelProperty="DNombre" />
									</html:select>
								</div>
								<%if(((ArrayList) session.getAttribute(Constantes.COMBO_CATEGORIA)).size() > 0 ){%>
								<div class="todo">
									<label for="idVCategoria" class="nombreMediano">Categor&iacute;a:
										*</label>
									<html:select property="CProfesional_id"
										styleClass="cuadroTodoM" size="1"
										onchange="javascript:cargarComboEspecialidades();">
										<html:option value="">Seleccione...</html:option>
										<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
											property="CProfesionalId" labelProperty="DNombre" />
									</html:select>
								</div>
								<%}%>
								<%if(((ArrayList) session.getAttribute(Constantes.COMBO_ESPECIALIDAD)).size() > 0 ){%>
								<div class="todo">
									<label for="idVEspecialidad" class="nombreMediano">Especialidad:
										*</label>
									<html:select property="CEspec_id" styleClass="cuadroTodoM"
										size="1" onchange="javascript:cargarComboItinerarios();">
										<html:option value="Seleccione">Seleccione...</html:option>
										<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
											property="CEspecId" labelProperty="DNombre" />
									</html:select>
								</div>
								<%}%>
								<%if(((ArrayList) session.getAttribute(Constantes.COMBO_ITINERARIOS)).size() > 0 ){%>
								<div class="todo">
									<label class="nombreMediano" for="idVEspecialidad">Manual:
										*</label>
									<html:select property="CItinerario_id" styleClass="cuadroTodoM"
										size="1" tabindex="12">
										<html:option value="Seleccione">Seleccione...</html:option>
										<html:options collection="<%=Constantes.COMBO_ITINERARIOS%>"
											property="CItinerarioId" labelProperty="DDescripcion" />
									</html:select>
								</div>
								<%}%>
								<div class="todo">
									<label class="nombreLargo" for="idVCategoria">
										Comit&eacute; al que pertenece: *</label>
									<html:select property="CCte_id" styleClass="cuadroTodoP"
										size="1" tabindex="11">
										<html:option value="0">Seleccione...</html:option>
										<html:options collection="<%=Constantes.COMBO_CTES%>"
											property="CCteId" labelProperty="DNombre" />
									</html:select>
								</div>
							</div>
						</fieldset>
					</logic:equal>
				</logic:notEqual>
			</logic:equal>

			<div class="espaciador"></div>
			<div class="botonesPagina">
				<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviar('OCAPEvaluadores.do?accion=insertar');">
								<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Alta evaluador" /> <span> Aceptar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:equal>

				<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <logic:notEqual
								name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
								<a
									href="javascript:enviar('OCAPEvaluadores.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=request.getParameter("opcion")%>');">
									<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Volver" />
									<span> Volver </span>
								</a>
							</logic:notEqual> <logic:equal name="tipoAccion"
								value="<%=Constantes.VER_DETALLE%>">
								<% if (!Constantes.OCAP_CE.equals(jcylUsuario.getUser().getRol())) { %>
								<a
									href="javascript:enviar('OCAPEvaluadores.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=request.getParameter("opcion")%>&cte=<%=request.getParameter("cte")%>');">
									<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Volver" />
									<span> Volver </span>
								</a>
								<% } else {%>
								<a
									href="javascript:enviar('OCAPComponentesCtes.do?accion=listarEvaluados&cCteIdS=<%=session.getAttribute("cCteIdS")%>&nombre=<%=session.getAttribute("nombreCTE")%>');">
									<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Volver" />
									<span> Volver </span>
								</a>
								<% } %>
							</logic:equal>
						</span> <span class="derBoton"></span>
					</div>
				</logic:notEqual>

				<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
					<logic:equal name="tarea" value="<%=Constantes.ACTIVAR%>">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviar('OCAPEvaluadores.do?accion=asignarConvocatoria&opcion=<%=request.getParameter("opcion")%>');">
									<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
									alt="Aceptar" /> <span> Aceptar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</logic:equal>
					<logic:equal name="opcion" value="<%=Constantes.FQS_REGISTRO%>">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviar('OCAPEvaluadores.do?accion=grabar&opcion=<%=request.getParameter("opcion")%>');">
									<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
									alt="Aceptar" /> <span> Aceptar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</logic:equal>
					<logic:equal name="tarea" value="<%=Constantes.REGISTRAR%>">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviar('OCAPEvaluadores.do?accion=grabar&opcion=<%=request.getParameter("opcion")%>&tarea=<%=Constantes.REGISTRAR%>');">
									<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
									alt="Aceptar" /> <span> Aceptar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</logic:equal>
				</logic:equal>
			</div>

			<% if (!Constantes.OCAP_CE.equals(jcylUsuario.getUser().getRol())) { %>
			<logic:notEqual name="tarea" value="<%=Constantes.MODIFICAR%>">
				<div id="divTexto">
					<p>
						<label class="obligadoTexto">Los campos se&ntilde;alados
							con * son obligatorios</label>
					<div class="espaciador"></div>
					</p>
				</div>
			</logic:notEqual>
			<% } %>
		</html:form>
	</div>
</div>
