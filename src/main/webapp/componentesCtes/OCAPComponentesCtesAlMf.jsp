<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT"%>

<% 
  String convocaAux = "";
  String convocaAct = "";  
%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>
<script>
       
function cargarComboLocalidadesIns(){
      enviar('OCAPComponentesCtes.do?accion=cargarComboLocalidades&CProv_id='+ document.forms[0].CProv_id.value+'&jspVuelta=<%=request.getAttribute("tipoAccion")%>&opcion=<%=request.getAttribute("opcion")%>');
}  

function cargarComboLocalidadesMod(){
      enviar('OCAPComponentesCtes.do?accion=cargarComboLocalidades&CProv_id='+ document.forms[0].CProv_id.value+'&jspVuelta=<%=request.getAttribute("tipoAccion")%>&nombre=<%=request.getAttribute("nombre")%>&opcion=<%=request.getAttribute("opcion")%>&tarea=<%=request.getAttribute("tarea")%>');
} 

function cargarComboProvincias(){
      enviar('OCAPComponentesCtes.do?accion=cargarComboProvincias&jspVuelta=<%=request.getAttribute("tipoAccion")%>&opcion=<%=request.getAttribute("opcion")%>&tarea=<%=request.getAttribute("tarea")%>&opcion=<%=request.getAttribute("opcion")%>&nombre=<%=request.getAttribute("nombre")%>');
}

</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoDCP1A">
		<html:form action="/OCAPEvaluadores.do">
			<logic:equal name="opcion" value="<%=Constantes.FQS_CTE%>">
				<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
					<h3 class="subTituloContenido">Alta de Componente de CTE</h3>
				</logic:equal>
				<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
					<h3 class="subTituloContenido">Modificaci&oacute;n de
						Componente de CTE</h3>
				</logic:equal>
			</logic:equal>

			<logic:equal name="opcion" value="<%=Constantes.FQS_CE%>">
				<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
					<h3 class="subTituloContenido">Alta de Componente de CE</h3>
				</logic:equal>
				<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
					<h3 class="subTituloContenido">Modificaci&oacute;n de
						Componente de CE</h3>
				</logic:equal>
			</logic:equal>

			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<html:hidden property="CCompfqs_id" />&nbsp;
            </logic:notEqual>

			<logic:equal name="opcion" value="<%=Constantes.FQS_CTE%>">
				<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
					<html:hidden property="CCte_id_ant" />&nbsp;                  
                 <!-- <html:hidden property="CCte_id" />&nbsp;  -->
					<html:hidden property="CCompfqs_convo_id" />&nbsp;   
               </logic:equal>
			</logic:equal>

			<div class="textoRojo">
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
				<html:messages id="aCategoria" property="aCategoria" message="true">
					<bean:write name="aCategoria" />
					<br />
				</html:messages>
				<html:messages id="cCteId" property="cCteId" message="true">
					<bean:write name="cCteId" />
					<br />
				</html:messages>
				<html:messages id="aCargo" property="aCargo" message="true">
					<bean:write name="aCargo" />
					<br />
				</html:messages>
				<html:messages id="fVinculacion" property="fVinculacion"
					message="true">
					<bean:write name="fVinculacion" />
					<br />
				</html:messages>
				<html:messages id="fFinalizacion" property="fFinalizacion"
					message="true">
					<bean:write name="fFinalizacion" />
					<br />
				</html:messages>
				<html:messages id="Componente" property="Componente" message="true">
					<bean:write name="Componente" />
					<br />
				</html:messages>
			</div>

			<fieldset>
				<legend class="tituloLegend"> Datos Personales </legend>
				<div class="cuadroFieldset formulario modificacionEvaluadores">
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

					<!-- EVALUADOR REGISTRO (SIN CONVOCATORIA) Y COMPONENTE CTE-->
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

					<!-- DIRECCION POSTAL (EVALUADORES REGISTRO) Y COMPONENTES -->
					<fieldset class="normales">
						<legend class="tituloLegend"> Direcci&oacute;n Postal </legend>
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
									onchange="javascript:cargarComboProvincias();">
									<html:option value="">Seleccione...</html:option>
									<html:options collection="<%=Constantes.COMBO_COMUNIDADES%>"
										property="CComuniId" labelProperty="DComuni" />
								</html:select>
							</div>

							<%if(((ArrayList) session.getAttribute(Constantes.COMBO_PROVINCIAS)).size() > 0 ){%>
							<div class="todo">
								<label class="nombreLargo" for="idVCategoria">
									Provincia: </label>
								<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
									<html:select property="CProv_id" styleClass="cuadroTodoP"
										size="1" tabindex="13"
										onchange="javascript:cargarComboLocalidadesMod();">
										<html:option value="">Seleccione...</html:option>
										<html:options collection="<%=Constantes.COMBO_PROVINCIAS%>"
											property="CProvinciaId" labelProperty="DProvincia" />
									</html:select>
								</logic:equal>
								<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
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
				</div>
			</fieldset>

			<fieldset class="formulario">
				<legend class="tituloLegend"> Datos Profesionales </legend>
				<div class="cuadroFieldset">
					<div class="todo">
						<label class="nombreMediano" for="idVCategoria"> Cargo: </label>
						<html:text property="ACategoria" tabindex="21"
							styleClass="cuadroTodoM" maxlength="200" />
					</div>
					<label class="nombreLinea" for="idFacredita">Experiencia
						Profesional:</label>
					<div class="todo">
						<html:textarea property="ADatosprofesionales" tabindex="31"
							cols="" rows="" />
					</div>
				</div>
			</fieldset>

			<fieldset class="formulario">
				<logic:equal name="opcion" value="<%=Constantes.FQS_CTE%>">
					<legend class="tituloLegend"> Datos del CTE </legend>
				</logic:equal>
				<logic:equal name="opcion" value="<%=Constantes.FQS_CE%>">
					<legend class="tituloLegend"> Datos del CE </legend>
				</logic:equal>
				<div class="cuadroFieldset">
					<logic:equal name="opcion" value="<%=Constantes.FQS_CTE%>">
						<div class="todo">
							<label for="idVCategoria" class="nombreLargo">
								Comit&eacute; asignado: * </label>
							<html:select property="CCte_id" styleClass="cuadroTodoP" size="1"
								tabindex="51">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_CTES%>"
									property="CCteId" labelProperty="DNombre" />
							</html:select>
						</div>
						<div class="todo">
							<label for="idVCategoria" class="nombreLargo"> Cargo
								dentro del CTE: * </label>
							<html:select property="ACargo" styleClass="cuadroTodoP" size="1"
								tabindex="51">
								<html:option value="">Seleccione...</html:option>
								<html:optionsCollection name="<%=Constantes.COMBO_CARGOS%>" />
							</html:select>
						</div>
					</logic:equal>
					<logic:equal name="opcion" value="<%=Constantes.FQS_CE%>">
						<div class="todo">
							<label for="idVCategoria" class="nombreLargo"> Cargo
								dentro del CE: * </label>
							<html:select property="ACargo" styleClass="cuadroTodoP" size="1"
								tabindex="51">
								<html:option value="">Seleccione...</html:option>
								<html:optionsCollection name="<%=Constantes.COMBO_CARGOS%>" />
							</html:select>
						</div>
					</logic:equal>
					<div class="todoFecha">
						<label class="nombreFechaActiva" for="idVFechaC"> Fecha de
							vinculaci&oacute;n: (dd/mm/aaaa) * </label>
						<html:text property="FVinculacion" tabindex="52" maxlength="10" />
						<a id="calFCons"
							href='javascript:show_Calendario("OCAPComponentesfqsForm", "FVinculacion", document.forms[0].FVinculacion.value);'><html:img
								src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
					</div>
					<div class="todoFecha">
						<label class="nombreFechaActiva" for="idVFechaC"> Fecha de
							finalizaci&oacute;n: (dd/mm/aaaa) </label>
						<html:text property="FFinalizacion" tabindex="53" maxlength="10" />
						<a id="calFCons"
							href='javascript:show_Calendario("OCAPComponentesfqsForm", "FFinalizacion", document.forms[0].FFinalizacion.value);'><html:img
								src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
					</div>
				</div>
			</fieldset>

			<div class="espaciador"></div>
			<div class="botonesPagina">
				<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviar('OCAPComponentesCtes.do?accion=insertar&opcion=<%=request.getParameter("opcion")%>');">
								<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Alta componente" /> <span> Aceptar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:equal>

				<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="OCAPComponentesCtes.do?accion=listarComponentes&cCteIdS=<bean:write name="OCAPComponentesfqsForm" property="CCte_id"/>&nombre=<bean:write name="nombre"/>">
								<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Volver" />
								<span> Volver </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:notEqual>

				<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviar('OCAPComponentesCtes.do?accion=grabar&nombre=<bean:write name="nombre"/>&opcion=<%=request.getParameter("opcion")%>');">
								<img src="imagenes/imagenes_ocap/icono_editar.gif"
								alt="Modificar" /> <span> Grabar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:equal>
			</div>

			<div id="divTexto">
				<p>
					<label class="obligadoTexto">Los campos se&ntilde;alados
						con * son obligatorios</label>
				<div class="espaciador"></div>
				</p>
			</div>
		</html:form>
	</div>
</div>
