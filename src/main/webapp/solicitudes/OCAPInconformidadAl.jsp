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
					</label>
					<html:hidden property="CDni" />
					<label for="idVTelefono" class="colocaDatosVis">
						Tel&eacute;fono: <span><bean:write
								name="OCAPSolicitudesForm" property="NTelefono1" /></span>
					</label> <br />
					<br /> <label for="idVCorreo" class="colocaDatosVis">
						Correo Electr&oacute;nico: <span><bean:write
								name="OCAPSolicitudesForm" property="DCorreoelectronico" /></span>
					</label> <br />
					<br />
				</div>
			</fieldset>

			<fieldset>
				<legend class="tituloLegend"> Datos Profesionales Actuales</legend>
				<div class="cuadroFieldset">
					<label for="idVCategoria" class="colocaDatosVisGrande">R&eacute;gimen
						jur&iacute;dico: <logic:equal name="OCAPSolicitudesForm"
							property="CJuridico"
							value="<%=Constantes.C_JURIDICO_ESTATUTARIO_COD%>">
							<span><%=Constantes.C_JURIDICO_ESTATUTARIO%></span>
						</logic:equal> <logic:equal name="OCAPSolicitudesForm" property="CJuridico"
							value="<%=Constantes.C_JURIDICO_FUNCIONARIO_COD%>">
							<span><%=Constantes.C_JURIDICO_FUNCIONARIO%></span>
						</logic:equal> <logic:equal name="OCAPSolicitudesForm" property="CJuridico"
							value="<%=Constantes.C_JURIDICO_OTROS_COD%>">
							<span><%=Constantes.C_JURIDICO_OTROS%></span>
						</logic:equal>
						<logic:equal name="OCAPSolicitudesForm"
												property="CJuridico"
												value="<%=Constantes.C_JURIDICO_INTFUNC_COD%>">
												<span><%=Constantes.C_JURIDICO_INTFUNC%></span>
											</logic:equal>
											<logic:equal name="OCAPSolicitudesForm"
												property="CJuridico"
												value="<%=Constantes.C_JURIDICO_INTEST_COD%>">
												<span><%=Constantes.C_JURIDICO_INTEST%></span>
											</logic:equal>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisMuyGrande">Situaci&oacute;n
						Administrativa: <logic:equal name="OCAPSolicitudesForm"
							property="CSitAdministrativaAuxId"
							value="<%=Constantes.C_SIT_ADM_AUX_ACTIVO_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_ACTIVO%></span>
						</logic:equal> <logic:equal name="OCAPSolicitudesForm"
							property="CSitAdministrativaAuxId"
							value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_OTROS%>: <bean:write
									name="OCAPSolicitudesForm"
									property="DSitAdministrativaAuxOtros" /></span>
						</logic:equal>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">
						Personal: <span><bean:write name="OCAPSolicitudesForm"
								property="DEstatutarioActual_nombre" /></span>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">
						Categor&iacute;a: <span><bean:write
								name="OCAPSolicitudesForm" property="DProfesionalActual_nombre" /></span>
					</label><br />
					<br /> <label for="idVEspecialidad" class="colocaDatosVisGrande">
						Especialidad: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DEspecActual_nombre">
								<bean:write name="OCAPSolicitudesForm"
									property="DEspecActual_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DEspecActual_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br /> <label for="idVProvincia" class="colocaDatosVis">
						Provincia: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DProvincia">
								<bean:write name="OCAPSolicitudesForm" property="DProvincia" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm" property="DProvincia">-</logic:empty>
					</span>
					</label> <label for="idVProvincia" class="colocaDatosVis">
						Localidad: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="ACiudad">
								<bean:write name="OCAPSolicitudesForm" property="ACiudad" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm" property="ACiudad">-</logic:empty>
							<html:hidden name="OCAPSolicitudesForm" property="ACiudad" />
					</span>
					</label> <br />
					<br /> <label for="idVTipoGerencia" class="colocaDatosVis">
						Tipo Gerencia: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DTipogerencia_desc">
								<bean:write name="OCAPSolicitudesForm"
									property="DTipogerencia_desc" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DTipogerencia_desc">-</logic:empty>
					</span>
					</label> <label for="idVGerencia" class="colocaDatosVis"> Gerencia:
						<span> <logic:notEmpty name="OCAPSolicitudesForm"
								property="DGerencia_nombre">
								<bean:write name="OCAPSolicitudesForm"
									property="DGerencia_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DGerencia_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br /> <label for="idVCentro" class="colocaDatosVisGrande">
						Centro de Trabajo: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DCentrotrabajo_nombre">
								<bean:write name="OCAPSolicitudesForm"
									property="DCentrotrabajo_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DCentrotrabajo_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">
						Procedimiento de evaluaci&oacute;n por el que opta: <span><bean:write
								name="OCAPSolicitudesForm" property="DSitAdministrativa_nombre" /></span>
					</label>
				</div>
			</fieldset>

			<fieldset>
				<legend class="tituloLegend"> Datos por los que opta a
					Carrera Profesional </legend>
				<div class="cuadroFieldset">
					<label for="idVGrado" class="colocaDatosVis">Grado: <span><bean:write
								name="OCAPSolicitudesForm" property="DGrado_des" /></span>
					</label>
					<html:hidden property="CConvocatoriaId" />
					<html:hidden name="OCAPSolicitudesForm"
						property="FRegistro_oficial" />
					<html:hidden name="OCAPSolicitudesForm" property="FRegistro_solic" />
					<br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">
						Personal: <span><bean:write name="OCAPSolicitudesForm"
								property="DEstatutario_nombre" /></span>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">
						Categor&iacute;a: <span><bean:write
								name="OCAPSolicitudesForm" property="DProfesional_nombre" /></span>
					</label><br />
					<br /> <label for="idVEspecialidad" class="colocaDatosVisGrande">
						Especialidad: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DEspec_nombre">
								<bean:write name="OCAPSolicitudesForm" property="DEspec_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm" property="DEspec_nombre">-</logic:empty>
					</span>
					</label>
					<div class="espaciador"></div>
				</div>
			</fieldset>

			<fieldset>
				<legend class="tituloLegend"> Alegaciones </legend>
				<div class="cuadroFieldset">
					<div>
						<label for="idVFecha" class="colocaDatosVisGrande"> Fecha
							de Registro de las Alegaciones: * (dd/mm/aaaa) <html:text
								property="FInconf_solic"
								styleClass="recuadroM textoNormal margenIzdaAlta" maxlength="19" />
						</label>
					</div>
					<br />
					<br />
					<br />
					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:textarea property="DMotivo_inconf_solic"
							styleClass="taMediano" cols="" rows="" readonly="true">
							<bean:write name="OCAPSolicitudesForm"
								property="DMotivo_inconf_solic" />
						</html:textarea>
					</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
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

			<p>* En el campo Fecha de registro de las alegaciones
				deber&aacute; introducir, en el formato indicado, la fecha en la que
				las alegaciones fueron recepcionadas y registradas en el registro
				oficial correspondiente.</p>

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