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
			<html:hidden name="OCAPSolicitudesForm" property="CJuridico" />
			<html:hidden name="OCAPSolicitudesForm"
				property="DSitAdministrativa_nombre" />
			<html:hidden name="OCAPSolicitudesForm"
				property="CSitAdministrativaAuxId" />
			<html:hidden name="OCAPSolicitudesForm"
				property="DSitAdministrativaAuxOtros" />
			<html:hidden name="OCAPSolicitudesForm"
				property="DEstatutarioActual_nombre" />
			<html:hidden name="OCAPSolicitudesForm"
				property="DProfesionalActual_nombre" />
			<html:hidden name="OCAPSolicitudesForm"
				property="DEspecActual_nombre" />
			<html:hidden name="OCAPSolicitudesForm"
				property="DEstatutario_nombre" />
			<html:hidden name="OCAPSolicitudesForm"
				property="DProfesional_nombre" />
			<html:hidden name="OCAPSolicitudesForm" property="DEspec_nombre" />
			<html:hidden name="OCAPSolicitudesForm" property="DProvincia" />
			<html:hidden name="OCAPSolicitudesForm" property="DTipogerencia_desc" />
			<html:hidden name="OCAPSolicitudesForm" property="DGerencia_nombre" />
			<html:hidden name="OCAPSolicitudesForm"
				property="DCentrotrabajo_nombre" />
			<html:hidden name="OCAPSolicitudesForm" property="DGrado_des" />
			<html:hidden name="OCAPSolicitudesForm" property="FRegistro_solic" />
			<html:hidden name="OCAPSolicitudesForm"
				property="FRegistro_oficialMC" />
			<html:hidden name="OCAPSolicitudesForm" property="BValidacion_cc" />

			<logic:equal name="fase" value="<%=Constantes.FASE_INICIACION%>">
				<h2 class="tituloContenido">REQUERIMIENTO DE SUBSANACI&Oacute;N
					DE SOLICITUD DE ACCESO A GRADO DE C.P.</h2>
			</logic:equal>
			<logic:equal name="fase" value="<%=Constantes.FASE_TRAMITACION%>">
				<h2 class="tituloContenido">NOTIFICACI&Oacute;N DE NO
					RECEPCI&Oacute;N DE M&Eacute;RITOS CURRICULARES</h2>
			</logic:equal>
			<logic:equal name="fase" value="<%=Constantes.FASE_VALIDACION%>">
				<h2 class="tituloContenido">NOTIFICACI&Oacute;N DE NO
					VALIDACI&Oacute;N DE M&Eacute;RITOS CURRICULARES</h2>
			</logic:equal>

			<logic:equal name="fase" value="<%=Constantes.FASE_INICIACION%>">
				<h3 class="subTituloContenido">DCP1C</h3>
			</logic:equal>
			<logic:equal name="fase" value="<%=Constantes.FASE_TRAMITACION%>">
				<h3 class="subTituloContenido">DCP5A</h3>
			</logic:equal>
			<logic:equal name="fase" value="<%=Constantes.FASE_VALIDACION%>">
				<h3 class="subTituloContenido">DCP5A</h3>
			</logic:equal>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<fieldset>
				<legend class="tituloLegend"> Datos Personales </legend>
				<div class="cuadroFieldset">
					<label for="idVApell1" class="colocaDatosVis"> Apellidos: <span><bean:write
								name="OCAPSolicitudesForm" property="DApellido1" /></span>
					</label> <label for="idVNombre" class="colocaDatosVis"> Nombre: <span><bean:write
								name="OCAPSolicitudesForm" property="DNombre" /></span>
					</label><br />
					<br /> <label for="idVDNI" class="colocaDatosVis">
						NIF/NIE: <span><bean:write name="OCAPSolicitudesForm"
								property="CDniReal" /></span>
					</label>
					<html:hidden property="CDni" />
					<html:hidden name="OCAPSolicitudesForm" property="BSexo" />
				</div>
			</fieldset>

			<fieldset>
				<legend class="tituloLegend"> Datos Profesionales Actuales
				</legend>
				<div class="cuadroFieldset denegacion">
					<div class="ajustaRegJuridico">
						<label for="idVCategoria" class="colocaDatosVis">R&eacute;gimen
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
						</label><br />
						<br />
					</div>

					<div class="ajustaSitAdmin">
						<label for="idVCategoria" class="colocaDatosVisGrande">Situaci&oacute;n
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
						</label><br />
						<br />
					</div>

					<div class="ajustaPersonal">
						<label for="idVCategoria" class="colocaDatosVisGrande">
							Personal: <span><bean:write name="OCAPSolicitudesForm"
									property="DEstatutarioActual_nombre" /></span>
						</label><br />
						<br />
					</div>

					<div class="ajustaCategoria">
						<label for="idVCategoria" class="colocaDatosVisGrande">
							Categor&iacute;a: <span><bean:write
									name="OCAPSolicitudesForm" property="DProfesionalActual_nombre" /></span>
						</label><br />
						<br />
					</div>

					<div class="ajustaEspecialidad">
						<label for="idVEspecialidad" class="colocaDatosVisGrande">
							Especialidad: <span><bean:write name="OCAPSolicitudesForm"
									property="DEspecActual_nombre" /></span>
						</label><br />
						<br />
					</div>

					<div class="ajustaProvincia">
						<label for="idVProvincia" class="colocaDatosVis">
							Provincia: <span><bean:write name="OCAPSolicitudesForm"
									property="DProvincia" /></span>
						</label> <label for="idVProvincia" class="colocaDatosVis">
							Localidad: <span> <logic:notEmpty
									name="OCAPSolicitudesForm" property="ACiudad">
									<bean:write name="OCAPSolicitudesForm" property="ACiudad" />
								</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm" property="ACiudad">-</logic:empty>
						</span>
						</label><br />
						<br />
					</div>

					<div class="ajustaGerencia">
						<label for="idVProvincia" class="colocaDatosVis"> Tipo
							Gerencia: <span><bean:write name="OCAPSolicitudesForm"
									property="DTipogerencia_desc" /></span>
						</label> <label for="idVGerencia" class="colocaDatosVis">
							Gerencia: <span><bean:write name="OCAPSolicitudesForm"
									property="DGerencia_nombre" /></span>
						</label><br />
						<br />
					</div>

					<div class="ajustaTrabajo">
						<label for="idVCentroTrabajo" class="colocaDatosVisGrande">
							Centro de Trabajo: <span><bean:write
									name="OCAPSolicitudesForm" property="DCentrotrabajo_nombre" /></span>
						</label><br />
						<br />
					</div>

					<div class="ajustaProcedimiento">
						<label for="idVCategoria" class="colocaDatosVisMuyGrande">
							Procedimiento de evaluaci&oacute;n por el que opta: <span><bean:write
									name="OCAPSolicitudesForm" property="DSitAdministrativa_nombre" /></span>
						</label><br />
						<br />
					</div>
				</div>
			</fieldset>

			<fieldset>
				<legend class="tituloLegend"> Datos por los que opta a
					Carrera Profesional </legend>
				<div class="cuadroFieldset ajustarCampos">
					<label for="idVGrado" class="colocaDatosVis"> Grado: <span><bean:write
								name="OCAPSolicitudesForm" property="DGrado_des" /></span> <html:hidden
							name="OCAPSolicitudesForm" property="DGrado_des" />
					</label>
					<html:hidden name="OCAPSolicitudesForm" property="FRegistro_solic" />
					<html:hidden name="OCAPSolicitudesForm"
						property="FRegistro_oficial" />
					<!--
               <label for="idVFecha" class="colocaDatosVis">Fecha de registro de solicitud:
                  <html:text property="FRegistro_solic"  tabindex="1" styleClass=" " size="19" maxlength="19" readonly="true" />
               </label>
               <br /><br />
               <span class="colocaDatosVis"><span class="textoDatos">&nbsp;</span></span>
               <label for="idVFecha" class="colocaDatosVis">Fecha de registro oficial:
                  <html:text property="FRegistro_oficial"  tabindex="1" styleClass=" " size="19" maxlength="19" readonly="true" />
               </label>
               -->
					<br />
					<br />
					<div class="ajustaPersonal">
						<label for="idVCategoria" class="colocaDatosVisGrande">
							Personal: <span><bean:write name="OCAPSolicitudesForm"
									property="DEstatutario_nombre" /></span>
						</label><br />
						<br />
					</div>

					<div class="ajustaCategoria">
						<label for="idVCategoria" class="colocaDatosVisMuyGrande">
							Categor&iacute;a: <span><bean:write
									name="OCAPSolicitudesForm" property="DProfesional_nombre" /></span>
						</label><br />
						<br />
					</div>

					<div class="ajustaEspecialidad">
						<label for="idVEspecialidad" class="colocaDatosVisGrande">
							Especialidad: <span><html:text name="OCAPSolicitudesForm"
									property="DEspec_nombre" tabindex="1" styleClass="textoNormal"
									readonly="true" /></span>
						</label><br />
						<br />
					</div>
				</div>
			</fieldset>

			<fieldset>
				<logic:equal name="fase" value="<%=Constantes.FASE_INICIACION%>">
					<legend class="tituloLegend"> Motivo de la no
						aceptaci&oacute;n de solicitud de acceso </legend>
				</logic:equal>
				<logic:equal name="fase" value="<%=Constantes.FASE_TRAMITACION%>">
					<legend class="tituloLegend"> Motivo de la no
						recepci&oacute;n de M&eacute;ritos Curriculares </legend>
				</logic:equal>
				<logic:equal name="fase" value="<%=Constantes.FASE_VALIDACION%>">
					<legend class="tituloLegend"> Motivo de la no
						validaci&oacute;n de M&eacute;ritos Curriculares </legend>
				</logic:equal>

				<logic:equal name="fase" value="<%=Constantes.FASE_INICIACION%>">
					<div class="espaciador"></div>
					<div class="listadoRespuesta">
						<span class="colocaTitRadio2 colocaTitRadioDCP1C"> SI </span> <span
							class="colocaTitRadioDCP1C"> NO </span> <br />
						<br />
						<ul>
							<li><span> Los datos personales y profesionales son
									correctos </span> <span> <html:radio name="OCAPSolicitudesForm"
										property="BPersonales"
										styleClass="opcionRadioSeparados radio11" value="N"
										disabled="true" /> <html:radio name="OCAPSolicitudesForm"
										property="BPersonales" styleClass="opcionRadioSeparados"
										value="Y" disabled="true" />
							</span> <html:hidden name="OCAPSolicitudesForm" property="BPersonales" />
							</li>
							<li><span> Cumple con el requisito de antig&uuml;edad
									requerida </span> <span> <html:radio name="OCAPSolicitudesForm"
										property="BInconf_antiguedad"
										styleClass="opcionRadioSeparados radio12" value="N"
										disabled="true" /> <html:radio name="OCAPSolicitudesForm"
										property="BInconf_antiguedad"
										styleClass="opcionRadioSeparados" value="Y" disabled="true" />
							</span> <html:hidden name="OCAPSolicitudesForm"
									property="BInconf_antiguedad" /></li>
							<li><span> Tiene la condici&oacute;n de personal fijo
							</span> <span> <html:radio name="OCAPSolicitudesForm"
										property="BInconf_plazaprop"
										styleClass="opcionRadioSeparados radio13" value="N"
										disabled="true" /> <html:radio name="OCAPSolicitudesForm"
										property="BInconf_plazaprop" styleClass="opcionRadioSeparados"
										value="Y" disabled="true" />
							</span> <html:hidden name="OCAPSolicitudesForm"
									property="BInconf_plazaprop" /></li>
							<li><span> Se verifican los servicios prestados en
									otros centros </span> <span> <html:radio
										name="OCAPSolicitudesForm" property="BOtrosCentros"
										styleClass="opcionRadioSeparados radio14" value="N"
										disabled="true" /> <html:radio name="OCAPSolicitudesForm"
										property="BOtrosCentros" styleClass="opcionRadioSeparados"
										value="Y" disabled="true" />
							</span> <html:hidden name="OCAPSolicitudesForm" property="BOtrosCentros" />
							</li>
							<li><span> Solicitud registrada en plazo </span> <span>
									<html:radio name="OCAPSolicitudesForm" property="BPlazo"
										styleClass="opcionRadioSeparados radio15" value="N"
										disabled="true" /> <html:radio name="OCAPSolicitudesForm"
										property="BPlazo" styleClass="opcionRadioSeparados" value="Y"
										disabled="true" />
							</span> <html:hidden name="OCAPSolicitudesForm" property="BPlazo" /></li>
						</ul>
					</div>
				</logic:equal>
				<logic:notEqual name="fase" value="<%=Constantes.FASE_INICIACION%>">
					<br />
					<br />
					<span class="textoCaracteres colocaCarTADen">(Texto <label
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
				</logic:notEqual>

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

			<!--         <p>
            <%if( (request.getParameter("denegacion") != null && !request.getParameter("denegacion").equals(Constantes.DENEGADO_CON_INCONF))
               || (request.getAttribute("denegacion") != null && !request.getAttribute("denegacion").equals(Constantes.DENEGADO_CON_INCONF))
               ){%>
            Frente a esta decisi&oacute;n tiene 10 d&iacite;as naturales para enviar sus alegaciones seg&uacute;n modelo establecido, si lo considera oportuno, junto con los justificantes correspondientes.
            <%}%>
            <div class="limpiadora"></div>
         </p> -->
		</html:form>

	</div>
	<!-- /fin de ContenidoDCP2A -->
</div>
<!-- /Fin de Contenido -->