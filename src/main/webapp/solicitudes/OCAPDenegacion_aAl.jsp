<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>

<script language="JavaScript">

var LONG_MAX_DOBSERVACIONES = <%=Constantes.LONG_OBSERV_NEG%>;
var LONG_MAX_DMOTIVOS = <%=Constantes.LONG_MOTIV_NEG%>;
//comprobar long max de textareas
function longMaxObservaciones(campo) {
   if(campo.value.length <= LONG_MAX_DOBSERVACIONES) {
      document.getElementById("longObserv").innerHTML=campo.value.length;
      return true;
   } else {
      campo.value = campo.value.substring(0,LONG_MAX_DOBSERVACIONES);
      document.getElementById("longObserv").innerHTML=campo.value.length;
      return false;
   }
}
function longMaxMotivo(campo) {
   if(campo.value.length <= LONG_MAX_DMOTIVOS) {
      document.getElementById("longMotivo").innerHTML=campo.value.length;
      return true;
   } else {
      campo.value = campo.value.substring(0,LONG_MAX_DMOTIVOS);
      document.getElementById("longMotivo").innerHTML=campo.value.length;
      return false;
   }
}

function aceptarDenegacion(){
   if (document.forms[0].DMotivo_neg != null )
      var aux = longMaxMotivo(document.forms[0].DMotivo_neg);
   else aux=true;
   var aux2=longMaxObservaciones(document.forms[0].DObserv_neg_solic);
   if(aux && aux2){
      <% String cadena="";
         if (request.getParameter("numCreditos") != null) {
         cadena = "&numCreditos="+request.getParameter("numCreditos");
         int numCreditos= new Integer(request.getParameter("numCreditos")).intValue();
         for (int i = 0; i < numCreditos; i++) {
            cadena += "&creditoValidado"+i+"="+request.getParameter("creditoValidado"+i);
         }
      } %>
      var cadenaParam='<%=cadena%>';
      enviar('OCAPSolicitudes.do?accion=insertarDenegacion&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPDenegacionAl&fase=<%=request.getAttribute("fase")%>&denegacion=<%=request.getAttribute("denegacion")%>'+cadenaParam);
   } else if (!aux2){
      alert("Ha superado el n\u00famero m\u00e1ximo de caracteres en las Observaciones");
   } else if (!aux){
      alert("Ha superado el n\u00famero m\u00e1ximo de caracteres en los Motivos");
   }
}

</script>

<div class="contenido">
	<div class="contenidoDCP1C">

		<html:form action="/OCAPSolicitudes.do">
			<html:hidden name="OCAPSolicitudesForm" property="CExp_id" />
			<html:hidden name="OCAPSolicitudesForm" property="DApellido1" />
			<html:hidden name="OCAPSolicitudesForm" property="DNombre" />
			<html:hidden name="OCAPSolicitudesForm" property="CDni" />
			<html:hidden name="OCAPSolicitudesForm" property="CDniReal" />
			<html:hidden name="OCAPSolicitudesForm" property="BSexo" />
			<html:hidden name="OCAPSolicitudesForm" property="NTelefono1" />
			<html:hidden name="OCAPSolicitudesForm" property="NTelefono2" />
			<html:hidden name="OCAPSolicitudesForm" property="DCorreoelectronico" />
			<html:hidden name="OCAPSolicitudesForm"
				property="DProfesional_nombre" />
			<html:hidden name="OCAPSolicitudesForm" property="DEspec_nombre" />
			<html:hidden name="OCAPSolicitudesForm" property="DProvincia" />
			<html:hidden name="OCAPSolicitudesForm" property="DGerencia_nombre" />
			<html:hidden name="OCAPSolicitudesForm"
				property="DCentrotrabajo_nombre" />
			<html:hidden name="OCAPSolicitudesForm" property="DGrado_des" />
			<html:hidden name="OCAPSolicitudesForm" property="FRegistro_solic" />
			<html:hidden name="OCAPSolicitudesForm"
				property="FRegistro_oficialMC" />
			<html:hidden name="OCAPSolicitudesForm" property="BValidacion_cc" />
			<html:hidden name="OCAPSolicitudesForm" property="CTipo" />

			<h2 class="tituloContenido">NOTIFICACI&Oacute;N DE NO
				VALIDACI&Oacute;N DE M&Eacute;RITOS CURRICULARES</h2>
			<h3 class="subTituloContenido">DCP5A</h3>
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
				<legend class="tituloLegend"> Motivo de la no
					validaci&oacute;n de M&eacute;ritos Curriculares </legend>

				<br />
				<br /> <span class="textoCaracteres colocaCarTADen">(Texto <label
					id="longMotivo">0</label> / <%=Constantes.LONG_MOTIV_NEG%>
					caracteres)
				</span>
				<html:textarea property="DMotivo_neg" cols="" rows=""
					styleClass="taMediano colocaTADen"
					onkeypress="javascript:return longMaxMotivo(this);"
					onclick="javascript:return longMaxMotivo(this);"
					onkeydown="javascript:return longMaxMotivo(this);"
					onkeyup="javascript:return longMaxMotivo(this);" />
				<script language="javascript">longMaxMotivo(document.forms[0].DMotivo_neg);</script>

				<h4>OBSERVACIONES:</h4>
				<span class="textoCaracteres colocaCarTADen">(Texto <label
					id="longObserv">0</label> / <%=Constantes.LONG_OBSERV_NEG%>
					caracteres)
				</span>
				<div class="limpiadora"></div>
				<html:textarea property="DObserv_neg_solic" cols="" rows=""
					styleClass="taMediano colocaTADen"
					onkeypress="javascript:return longMaxObservaciones(this);"
					onclick="javascript:return longMaxObservaciones(this);"
					onkeydown="javascript:return longMaxObservaciones(this);"
					onkeyup="javascript:return longMaxObservaciones(this);" />
				<div class="espaciador"></div>
				<script language="javascript">longMaxObservaciones(document.forms[0].DObserv_neg_solic);</script>
			</fieldset>
			<div class="limpiadora"></div>
			<div class="espaciador"></div>

			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <logic:equal
							name="registrandoDenegando" value="<%=Constantes.SI%>">
							<a href="OCAPSolicitudes.do?accion=irInsertar"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif"
								alt="Cancelar Desestimación" /> <span> Cancelar </span>
							</a>
						</logic:equal> <logic:notEqual name="registrandoDenegando"
							value="<%=Constantes.SI%>">
							<a href="javascript:history.back();"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif"
								alt="Cancelar Desestimación" /> <span> Cancelar </span>
							</a>
						</logic:notEqual>
					</span> <span class="derBoton"></span>
				</div>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:aceptarDenegacion();"> <img
							src="imagenes/imagenes_ocap/flecha_correcto.gif"
							alt="Aceptar Desestimación" /> <span> Aceptar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>

			<div class="limpiadora"></div>
			<div class="espaciador"></div>

		</html:form>

	</div>
	<!-- /fin de ContenidoDCP2A -->
</div>
<!-- /Fin de Contenido -->