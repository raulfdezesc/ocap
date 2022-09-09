<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT"%>
<%@ page import="java.util.ArrayList"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="JavaScript" type="text/javascript"
	src="<html:rewrite page='/javascript/combos1.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/fechas.js'/>"></script>

<script language="JavaScript">

function validacionFormularioInconformidad(){

   if(document.forms[0].DMotivo_inconf_solic.value == ''){
      alert("Debe rellenar el campo \" Alegaciones \".");
      return false;
   }else{
      var aux = longMaxMotivo(document.forms[0].DMotivo_inconf_solic);
      if(!aux){
         alert("El campo \" Alegaciones \" no puede superar los <%=Constantes.LONG_MOTIV_INCONF%> caracteres. [El n\u00famero de caracteres introducidos son: " + document.forms[0].DMotivo_inconf_solic.value.length + "]");
         return false;
      }
   }

   if(document.forms[0].FInconf_solic.value == ''){
      alert("Debe rellenar el campo \" Fecha de Registro de las Alegaciones \".");
      return false;
   }else{
      if (document.forms[0].FInconf_solic.value.length < 19 && document.forms[0].FInconf_solic.value.length >= 10)
           document.forms[0].FInconf_solic.value = document.forms[0].FInconf_solic.value.substring(0,10)+' 00:00:00';
      if(!esTimestampCorrecto(document.forms[0].FInconf_solic.value)){
         alert("La \" Fecha de Registro de las Alegaciones \" no es correcta.");
         return false;
      }
   }

   return true;
}

var LONG_MAX_DMOTIVO = <%=Constantes.LONG_MOTIV_INCONF%>;
function longMaxMotivo(campo) {
   if(campo.value.length <= LONG_MAX_DMOTIVO) {
      document.getElementById("longMotivo").innerHTML=campo.value.length;
      return true;
   } else {
      campo.value = campo.value.substring(0,LONG_MAX_DMOTIVO);
      document.getElementById("longMotivo").innerHTML=campo.value.length;
      return false;
   }
}

function aceptarInconformidad(){
   if(validacionFormularioInconformidad()){
      enviar('OCAPSolicitudes.do?accion=insertarInconformidad&opcion=<%=request.getParameter("opcion")%>&fase=<%=request.getAttribute("fase")%>&denegacion=<%=request.getAttribute("denegacion")%>');
   }
}

</script>

<div class="contenido">
	<div class="contenidoDCP5B">

		<html:form action="/OCAPSolicitudes.do">
			<html:hidden name="OCAPSolicitudesForm" property="CExp_id" />

			<h2 class="tituloContenido">ALEGACIONES</h2>

			<h3 class="subTituloContenido">DCP5B</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<fieldset>
				<legend class="tituloLegend"> Datos Personales </legend>
				<div class="cuadroFieldset">
					<label for="idVApell1" class="colocaDatosVis"> Apellidos: <span><bean:write
								name="OCAPSolicitudesForm" property="DApellido1" /></span>
					</label> <label for="idVNombre" class="colocaDatosVis"> Nombre: <span><bean:write
								name="OCAPSolicitudesForm" property="DNombre" /></span>
					</label> <br />
					<br /> <label for="idVDNI" class="colocaDatosVis">
						NIF/NIE: <span><bean:write name="OCAPSolicitudesForm"
								property="CDniReal" /></span>
					</label> <label for="idVCorreo" class="colocaDatosVis"> Sexo: <logic:equal
							name="OCAPSolicitudesForm" property="BSexo" value="H">
							<span>Hombre</span>
						</logic:equal> <logic:equal name="OCAPSolicitudesForm" property="BSexo"
							value="M">
							<span>Mujer</span>
						</logic:equal>
					</label> <br />
					<br /> <label for="idVTelefono" class="colocaDatosVis">
						Tel&eacute;fono 1: <span><bean:write
								name="OCAPSolicitudesForm" property="NTelefono1" /></span>
					</label> <label for="idVTelefono" class="colocaDatosVis">
						Tel&eacute;fono 2: <span><bean:write
								name="OCAPSolicitudesForm" property="NTelefono2" /></span>
					</label> <br />
					<br /> <label for="idVCorreo" class="colocaDatosVis">
						Correo Electr&oacute;nico: <span><bean:write
								name="OCAPSolicitudesForm" property="DCorreoelectronico" /></span>
					</label> <br />
					<br /> <label for="idVCorreo" class="colocaDatosVisGrande">
						Domicilio, Calle o Plaza y N&ordm;: <span><bean:write
								name="OCAPSolicitudesForm" property="DDomicilio" /></span>
					</label> <br />
					<br /> <label for="idVProvincia" class="colocaDatosVis">
						Provincia: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DProvincia">
								<bean:write name="OCAPSolicitudesForm" property="DProvincia" />
							</logic:notEmpty>
					</span>
					</label> <label for="idVTelefono" class="colocaDatosVis">C&oacute;digo
						Postal: <span><bean:write name="OCAPSolicitudesForm"
								property="CPostalUsu" /></span>
					</label> <br />
					<br /> <label for="idVLocalidades" class="colocaDatosVis">
						Localidad: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DLocalidad">
								<bean:write name="OCAPSolicitudesForm" property="DLocalidad" />
							</logic:notEmpty>
					</span>
					</label>
				</div>
			</fieldset>

			<fieldset>
				<legend class="tituloLegend"> Datos por los que opta a
					Carrera Profesional </legend>
				<div class="cuadroFieldset" class="colocaDatosVisGrande">
					<!-- GRADO DETALLE -->
					<label for="idVGrado" class="colocaDatosVisGrande"> Grado:
						<span><bean:write name="OCAPSolicitudesForm"
								property="DGrado_des" /></span>
					</label> <br />
					<br />

					<!-- GRADO QUE POSEE (MODALIDAD ANTERIOR) -->
					<label for="idVCategoria" class="colocaDatosVisGrande">Grado
						que posee: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DModAnterior">
								<bean:write name="OCAPSolicitudesForm" property="DModAnterior" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm" property="DModAnterior">-</logic:empty>
					</span>
					</label> <br />
					<br />

					<!-- PROCEDIMIENTO DE EVALUACION DETALLE -->
					<label for="idVCategoria" class="colocaDatosVisGrande">Procedimiento
						de evaluaci&oacute;n por el que opta: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DProcedimiento">
								<bean:write name="OCAPSolicitudesForm" property="DProcedimiento" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DProcedimiento">-</logic:empty>
					</span>
					</label> <br />
					<br />

					<!-- REGIMEN JURIDICO DETALLE -->
					<label for="idVCategoria" class="colocaDatosVisGrande">R&eacute;gimen
						jur&iacute;dico: <logic:equal name="OCAPSolicitudesForm"
							property="CJuridicoId"
							value="<%=Constantes.C_JURIDICO_ESTATUTARIO_COD%>">
							<span><%=Constantes.C_JURIDICO_ESTATUTARIO%></span>
						</logic:equal> <logic:equal name="OCAPSolicitudesForm" property="CJuridicoId"
							value="<%=Constantes.C_JURIDICO_FUNCIONARIO_COD%>">
							<span><%=Constantes.C_JURIDICO_FUNCIONARIO%></span>
						</logic:equal> <logic:equal name="OCAPSolicitudesForm" property="CJuridicoId"
							value="<%=Constantes.C_JURIDICO_OTROS_COD%>">
							<span><%=Constantes.C_JURIDICO_OTROS%></span>
						</logic:equal> <span> <logic:equal name="OCAPSolicitudesForm"
								property="CJuridicoId"
								value="<%=Constantes.C_JURIDICO_OTROS_COD%>">
								<span><bean:write name="OCAPSolicitudesForm"
										property="DRegimenJuridicoOtros" /></span>
							</logic:equal>
							<logic:equal name="OCAPSolicitudesForm"
												property="CJuridicoId"
												value="<%=Constantes.C_JURIDICO_INTFUNC_COD%>">
												<span><%=Constantes.C_JURIDICO_INTFUNC%></span>
											</logic:equal>
											<logic:equal name="OCAPSolicitudesForm"
												property="CJuridicoId"
												value="<%=Constantes.C_JURIDICO_INTEST_COD%>">
												<span><%=Constantes.C_JURIDICO_INTEST%></span>
											</logic:equal>
					</span>
					</label>

					<logic:equal name="OCAPSolicitudesForm" property="CJuridicoCombo"
						value="<%=Constantes.C_JURIDICO_SANITARIO_FIJO_COD%>">
						<br />
						<br />
						<label for="idVCategoria" class="colocaDatosVisGrande">Tipo:
							<span><%=Constantes.C_JURIDICO_SANITARIO_FIJO%></span>
						</label>
					</logic:equal>
					<logic:equal name="OCAPSolicitudesForm" property="CJuridicoCombo"
						value="<%=Constantes.C_JURIDICO_GS_FIJO_COD%>">
						<br />
						<br />
						<label for="idVCategoria" class="colocaDatosVisGrande">Tipo:
							<span><%=Constantes.C_JURIDICO_GS_FIJO%></span>
						</label>
					</logic:equal>
					<br />
					<br />

					<!-- SITUACION ADMINISTRATIVA DETALLE -->
					<label for="idVCategoria" class="colocaDatosVisGrande">Situaci&oacute;n
						Administrativa: <logic:equal name="OCAPSolicitudesForm"
							property="CSitAdministrativaAuxId"
							value="<%=Constantes.C_SIT_ADM_AUX_ACTIVO_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_ACTIVO%></span>
						</logic:equal> <logic:equal name="OCAPSolicitudesForm"
							property="CSitAdministrativaAuxId"
							value="<%=Constantes.C_SIT_ADM_AUX_SE_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_SE%></span>
						</logic:equal> <logic:equal name="OCAPSolicitudesForm"
							property="CSitAdministrativaAuxId"
							value="<%=Constantes.C_SIT_ADM_AUX_ESSP_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_ESSP%></span>
						</logic:equal> <logic:equal name="OCAPSolicitudesForm"
							property="CSitAdministrativaAuxId"
							value="<%=Constantes.C_SIT_ADM_AUX_EV_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_EV%></span>
						</logic:equal> <logic:equal name="OCAPSolicitudesForm"
							property="CSitAdministrativaAuxId"
							value="<%=Constantes.C_SIT_ADM_AUX_ECF_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_ECF%></span>
						</logic:equal> <logic:equal name="OCAPSolicitudesForm"
							property="CSitAdministrativaAuxId"
							value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_OTROS%></span>
						</logic:equal> <span> <logic:equal name="OCAPSolicitudesForm"
								property="CSitAdministrativaAuxId"
								value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>">
								<span><bean:write name="OCAPSolicitudesForm"
										property="DSitAdministrativaOtros" /></span>
							</logic:equal>
					</span>
					</label> <br />
					<br />

					<!-- CATEGORIA DETALLE -->
					<label for="idVCategoria" class="colocaDatosVisGrande">
						Categor&iacute;a: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DProfesional_nombre">
								<bean:write name="OCAPSolicitudesForm"
									property="DProfesional_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DProfesional_nombre">-</logic:empty>
					</span>
					</label><br />
					<br />

					<!-- ESPECIALIDAD DETALLE -->
					<label for="idVEspecialidad" class="colocaDatosVisGrande">
						Especialidad: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DEspec_nombre">
								<bean:write name="OCAPSolicitudesForm" property="DEspec_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm" property="DEspec_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br />

					<!-- PROVINCIA CARRERA DETALLE -->
					<label for="idVProvincia" class="colocaDatosVis">
						Provincia: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DProvinciaCarrera">
								<bean:write name="OCAPSolicitudesForm"
									property="DProvinciaCarrera" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DProvinciaCarrera">-</logic:empty>
					</span>
					</label> <br />
					<br />

					<!-- GERENCIA CARRERA DETALLE-->
					<label for="idVGerencia" class="colocaDatosVisGrande">
						Gerencia: <span> <logic:notEmpty name="OCAPSolicitudesForm"
								property="DGerencia_nombre">
								<bean:write name="OCAPSolicitudesForm"
									property="DGerencia_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DGerencia_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br />

					<!-- CENTRO DE TRABAJO DETALLE-->
					<label for="idVCentroTrabajo" class="colocaDatosVisGrande">
						Centro de Trabajo: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DCentrotrabajo_nombre">
								<bean:write name="OCAPSolicitudesForm"
									property="DCentrotrabajo_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DCentrotrabajo_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br />

					<!-- SERVICIO CARRERA DETALLE -->
					<label for="idVEspecialidad" class="colocaDatosVisGrande">
						Servicio: <span> <logic:notEmpty name="OCAPSolicitudesForm"
								property="DServicio">
								<bean:write name="OCAPSolicitudesForm" property="DServicio" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm" property="DServicio">-</logic:empty>
					</span>
					</label> <br />
					<br />

					<!-- AREA / UNIDAD / PUESTO -->
					<label for="idVEspecialidad" class="colocaDatosVisGrande">
						Área / Unidad / Puesto: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DPuesto">
								<bean:write name="OCAPSolicitudesForm" property="DPuesto" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm" property="DPuesto">-</logic:empty>
					</span>
					</label> <br />
					<br />

					<!-- AÑOS DE EJECICIO DETALLE -->
					<label for="idVAnt"> <span class="colocaNumAlta">A&ntilde;os
							de ejercicio en la categor&iacute;a profesional por la que se
							accede:</span> <label>N&ordm; A&ntilde;os: <span
							class="textoDatos"><bean:write name="OCAPSolicitudesForm"
									property="NAniosantiguedad" /></span></label> <label>N&ordm; Meses: <span
							class="textoDatos"><bean:write name="OCAPSolicitudesForm"
									property="NMesesantiguedad" /></span></label> <label>N&ordm;
							D&iacute;as: <span class="textoDatos"><bean:write
									name="OCAPSolicitudesForm" property="NDiasantiguedad" /></span>
					</label>
					</label>
				</div>
			</fieldset>

			<fieldset>
				<legend class="tituloLegend"> Alegaciones </legend>
				<div class="cuadroFieldset">
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<div>
							<label for="idVFecha" class="colocaDatosVisGrande"> Fecha
								de Registro de las Alegaciones: (dd/mm/aaaa) <html:text
									property="FInconf_solic"
									styleClass="recuadroM textoNormal margenIzdaAlta"
									maxlength="19" readonly="true" />
							</label>
						</div>
						<br />
						<br />
						<br />
						<br />
						<html:textarea property="DMotivo_inconf_solic"
							styleClass="taMediano" cols="" rows="" readonly="true">
							<bean:write name="OCAPSolicitudesForm"
								property="DMotivo_inconf_solic" />
						</html:textarea>
					</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<div>
							<label for="idVFecha" class="colocaDatosVisGrande"> Fecha
								de Registro de las Alegaciones: * (dd/mm/aaaa) <html:text
									property="FInconf_solic"
									styleClass="recuadroM textoNormal margenIzdaAlta"
									maxlength="19" /> <a class="iconoCalendario"
								href='javascript:show_Calendario("OCAPSolicitudesForm", "FInconf_solic", document.forms[0].FInconf_solic.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</label>
						</div>
						<br />
						<br />
						<br />
						<br />
						<span class="textoCaracteresAl">(Texto <label
							id="longMotivo">0</label> / <%=Constantes.LONG_MOTIV_INCONF%>
							caracteres)
						</span>
						<div class="limpiadora"></div>
						<html:textarea property="DMotivo_inconf_solic"
							styleClass="taMediano margenCuadroTextoAlegaciones" cols=""
							rows="" onkeypress="javascript:return longMaxMotivo(this);"
							onclick="javascript:return longMaxMotivo(this);"
							onkeydown="javascript:return longMaxMotivo(this);"
							onkeyup="javascript:return longMaxMotivo(this);" />
					</logic:notEqual>
					<div class="espaciador"></div>
				</div>
			</fieldset>

			<logic:notEqual name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<p>* En el campo Fecha de registro de las alegaciones
					deber&aacute; introducir, en el formato indicado, la fecha en la
					que las alegaciones fueron recepcionadas y registradas en el
					registro oficial correspondiente.</p>
			</logic:notEqual>

			<div class="espaciador"></div>

			<div class="botonesPagina">
				<logic:notEqual name="tipoAccion"
					value="<%=Constantes.VER_DETALLE%>">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:history.back();"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif"
								alt="Cancelar Alegaciones" /> <span> Cancelar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:aceptarInconformidad();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Aceptar Alegaciones" /> <span> Aceptar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:notEqual>
				<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:history.back();"> <img
								src="imagenes/imagenes_ocap/icon_accept.gif" alt="Volver" /> <span>
									Volver </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:equal>

			</div>

		</html:form>

	</div>
	<!-- /fin de contenidoDCP5B -->
</div>
<!-- /Fin de Contenido -->