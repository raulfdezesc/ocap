<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.creditos.OCAPCreditosOT"%>
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
	src="<html:rewrite page='/javascript/fechas.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"
	charset="windows-1252"></script>

<script language="JavaScript" type="text/javascript">
function volverAtras() {
   //history.back();
   <% if (request.getParameter("opcion") == null || "".equals(request.getParameter("opcion"))) { %>
      <% if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE) ) { %>
         limpiarCampos();
         enviar("OCAPSolicitudes.do?accion=irInsertar");
      <% } else { %>
         enviar("PaginaInicio.do");
      <% } %>
   <% } else if (request.getParameter("opcion").equals(Constantes.OCAP_DIRECCION)) { %>
         enviar("OCAPEvaluadores.do?accion=listarEvaluados&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
   <% } else if (request.getParameter("opcion").equals(Constantes.OCAP_CC)) { %>
         enviar("OCAPEvaluadores.do?accion=listarEvaluados&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
   <% }else{ %>
         enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");      
   <% } %>
}

function limpiarCampos() {
   document.forms[0].DApellido1.value ='';
   document.forms[0].DNombre.value = '';
   document.forms[0].CDniReal.value = '';
   document.forms[0].BSexo.value = '';
   document.forms[0].NTelefono1.value = '';
   document.forms[0].DCorreoelectronico.value='';
   document.forms[0].FRegistro_oficial.value='';
}

function aceptar(){
   if (aclaracionesPendientes){
      alert('Hay aclaraciones de M\u00E9ritos Curriculares pendientes de aclararse.');

      enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");

   }else if (creditosValidadosSuficientes){
      if(confirm("Se va a ACEPTAR la Evaluaci\u00f3n de M\u00e9ritos Curriculares. \u00BFDesea continuar?")){
         enviar('OCAPSolicitudes.do?accion=insertarAceptacionEvaluacionMC&opcion=<%=request.getParameter("opcion")%>');
      }
   } else {
      // Validacion No aceptada
      if(confirm("Se va a DESESTIMAR la Evaluaci\u00f3n de M\u00e9ritos Curriculares, \u00BFDesea continuar?")){
         enviar('OCAPSolicitudes.do?accion=insertarDenegacion&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesDt&jspDenegacion=detalle&fase=<%=request.getAttribute("fase")%>&denegacion=<%=request.getAttribute("denegacion")%>');
      }
   } 
}

function aceptarGradoCC(){
   if (document.OCAPSolicitudesForm.BReconocimientoGrado[0].checked == false && document.OCAPSolicitudesForm.BReconocimientoGrado[1].checked == false){
      alert('Debe rellenar el campo de Reconocimiento de Grado.');
   } else {
      enviar("OCAPSolicitudes.do?accion=aceptarGrado&opcion=<%=request.getParameter("opcion")%>");
   }
}

var listaCreditosJS = new Array();
var creditosValidadosSuficientes = true;
var aclaracionesPendientes = false;
   
</script>
<!-- En un Array de JS metemos los creditos necesarios y conseguidos -->
<logic:present name="OCAPSolicitudesForm" property="listaCreditos">
	<bean:size id="cuantosListaCreditos" name="OCAPSolicitudesForm"
		property="listaCreditos" />
	<logic:equal name="cuantosListaCreditos" value="0">
		<script language="javascript" type="text/javascript">creditosValidadosSuficientes=false;</script>
	</logic:equal>
	<logic:iterate id="meritos" name="OCAPSolicitudesForm"
		property="listaCreditos" type="OCAPCreditosOT" indexId="indice">
		<script language="javascript" type="text/javascript">
         linea = new Array();
         linea[0] = '<bean:write name="meritos" property="DMerito"/>';
         linea[1] = '<bean:write name="meritos" property="NCreditos"/>';
         linea[2] = '<bean:write name="meritos" property="NCreditosValidadosCc"/>';
         linea[3] = '<bean:write name="meritos" property="NCreditosConseguidos"/>';
         linea[4] = '<bean:write name="meritos" property="NCreditosValidadosCeis"/>';
         listaCreditosJS[listaCreditosJS.length] = linea;
         <% if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS)) { %>
         		if (eval(linea[4]) < eval(linea[1])) {
            		creditosValidadosSuficientes=false;
         		}
         <% }else if ((jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC))){%>
         		if (eval(linea[2]) < eval(linea[1])) {
            		creditosValidadosSuficientes=false;
         		}
         <% }%>
      </script>
	</logic:iterate>
</logic:present>
<!-- Si tiene aclaraciones pendientes no puede ni acpetar ni denegar fase eval MC -->
<logic:equal name="pendientesAclaracion" value="<%=Constantes.SI%>">
	<script language="javascript" type="text/javascript">aclaracionesPendientes=true;</script>
</logic:equal>

<div class="contenido">
	<div class="contenidoDCP2A">
		<html:form action="/OCAPSolicitudes.do">
			<html:hidden name="OCAPSolicitudesForm" property="CExp_id" />
			<html:hidden name="OCAPSolicitudesForm" property="DApellido1" />
			<html:hidden name="OCAPSolicitudesForm" property="DNombre" />
			<html:hidden name="OCAPSolicitudesForm" property="CDniReal" />
			<html:hidden name="OCAPSolicitudesForm" property="CDni" />
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
			<html:hidden name="OCAPSolicitudesForm" property="DDomicilio" />
			<html:hidden name="OCAPSolicitudesForm" property="DModAnterior" />
			<html:hidden name="OCAPSolicitudesForm" property="DProcedimiento" />
			<html:hidden name="OCAPSolicitudesForm"
				property="DRegimenJuridicoOtros" />
			<html:hidden name="OCAPSolicitudesForm" property="CJuridicoCombo" />
			<html:hidden name="OCAPSolicitudesForm"
				property="CSitAdministrativaAuxId" />
			<html:hidden name="OCAPSolicitudesForm"
				property="DSitAdministrativaOtros" />
			<html:hidden name="OCAPSolicitudesForm" property="DProvinciaCarrera" />
			<html:hidden name="OCAPSolicitudesForm" property="DServicio" />
			<html:hidden name="OCAPSolicitudesForm" property="DPuesto" />
			<html:hidden name="OCAPSolicitudesForm" property="CJuridicoId" />
			<html:hidden name="OCAPSolicitudesForm" property="NAniosantiguedad" />
			<html:hidden name="OCAPSolicitudesForm" property="NMesesantiguedad" />
			<html:hidden name="OCAPSolicitudesForm" property="NDiasantiguedad" />
			<html:hidden name="OCAPSolicitudesForm" property="CProfesional_id" />
			<html:hidden name="OCAPSolicitudesForm" property="DDirector" />
			<html:hidden name="OCAPSolicitudesForm" property="DGerente" />
			<html:hidden name="OCAPSolicitudesForm"
				property="RContinuidad_proceso_validacion" />
			<html:hidden name="OCAPSolicitudesForm" property="CTipo" />
			<h2 class="tituloContenido">HISTORIAL DE CARRERA PROFESIONAL</h2>
			<h3 class="subTituloContenido">DCP2A</h3>
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
				<div class="cuadroFieldset colocaDatosVisGrande">
					<html:hidden property="CConvocatoriaId" />
					
							<% if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION))){ %>
							
							<label for="idVConvocatoria">Convocatoria: <span><bean:write name="OCAPSolicitudesForm"
								property="DNombreConvocatoria" /></span>
							</label> <br />
							<br />							
							
							
							

							
							<%} %>					
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
						&Aacute;rea / Unidad / Puesto: <span> <logic:notEmpty
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
				<legend class="tituloLegend">
					<logic:equal name="fase" value="<%=Constantes.FASE_VALIDACION%>">                                 
                     Validaci&oacute;n del CEIS 
               </logic:equal>
					<logic:equal name="fase"
						value="<%=Constantes.FASE_VALIDACION_CEIS%>">                                 
                     Validaci&oacute;n del CEIS/CC  
               </logic:equal>
					<logic:equal name="fase"
						value="<%=Constantes.FASE_VALIDACION_CEIS_CC%>">                                 
                     Validaci&oacute;n de la CC  
               </logic:equal>
					<logic:equal name="fase" value="<%=Constantes.FASE_VALIDACION_CC%>">                                 
                     Validaci&oacute;n de la CC  
               </logic:equal>
				</legend>
				<div class="cuadroFieldset">
					<table class="tablaFaseEvaluacion">
						<tr>
							<td></td>
							<td>Cr&eacute;ditos Necesarios</td>
							<td>Cr&eacute;ditos Obtenidos</td>

							<logic:equal name="fase" value="<%=Constantes.FASE_VALIDACION%>">
								<td>Cr&eacute;ditos Validados CEIS</td>
							</logic:equal>
							<logic:equal name="fase"
								value="<%=Constantes.FASE_VALIDACION_CEIS%>">
								<td>Cr&eacute;ditos Validados CEIS/CC</td>
							</logic:equal>
							<logic:equal name="fase"
								value="<%=Constantes.FASE_VALIDACION_CEIS_CC%>">
								<td>Cr&eacute;ditos Validados CEIS/CC</td>
								<td>Cr&eacute;ditos Validados CC</td>
							</logic:equal>
							<logic:equal name="fase"
								value="<%=Constantes.FASE_VALIDACION_CC%>">
								<td>Cr&eacute;ditos Validados CEIS</td>
								<td>Cr&eacute;ditos Validados CC</td>
							</logic:equal>
						</tr>
						<logic:iterate id="meritos" name="OCAPSolicitudesForm"
							property="listaCreditos" type="OCAPCreditosOT" indexId="indice">
							<tr>
								<td class="tit"><bean:write name="meritos"
										property="DMerito" /></td>
								<td><bean:write name="meritos" property="NCreditos" /></td>
								<td><bean:write name="meritos"
										property="NCreditosConseguidos" /></td>

								<logic:equal name="fase" value="<%=Constantes.FASE_VALIDACION%>">
									<td><bean:write name="meritos"
											property="NCreditosValidadosCeis" /></td>
								</logic:equal>
								<logic:equal name="fase"
									value="<%=Constantes.FASE_VALIDACION_CEIS%>">
									<td><bean:write name="meritos"
											property="NCreditosValidadosCeis" /></td>
								</logic:equal>
								<logic:equal name="fase"
									value="<%=Constantes.FASE_VALIDACION_CEIS_CC%>">
									<td><bean:write name="meritos"
											property="NCreditosValidadosCeis" /></td>
									<td><bean:write name="meritos"
											property="NCreditosValidadosCc" /></td>
								</logic:equal>
								<logic:equal name="fase"
									value="<%=Constantes.FASE_VALIDACION_CC%>">
									<td><bean:write name="meritos"
											property="NCreditosValidadosCeis" /></td>
									<td><bean:write name="meritos"
											property="NCreditosValidadosCc" /></td>
								</logic:equal>
							</tr>
						</logic:iterate>
					</table>
				</div>
			</fieldset>

			<!-- FASE_CC -->
			<logic:equal name="faseIII" value="<%=Constantes.FASE_CC%>">
				<% if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) { %>
				<fieldset>
					<legend class="tituloLegend"> Validaci&oacute;n de la
						Comisi&oacute;n Central </legend>
					<div class="cuadroFieldset">
						<div class="listadoRespuesta">
							<span class="colocaTitRadio"> SI </span> <span> NO </span> <br />
							<br />
							<div class="titulosRadio">
								<div>Reconocimiento de Grado</div>
							</div>
							<div class="radioTitulos">
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="BReconocimientoGrado" styleClass="opcionRadio"
										value="Y" />
									<html:radio name="OCAPSolicitudesForm"
										property="BReconocimientoGrado" styleClass="opcionRadio"
										value="N" />
								</div>
							</div>
						</div>
					</div>
				</fieldset>
				<% } %>
			</logic:equal>

			<!-- FASE_FIN -->
			<logic:equal name="faseIII" value="<%=Constantes.FASE_FIN%>">
				<fieldset>
					<legend class="tituloLegend"> Validaci&oacute;n de la
						Comisi&oacute;n Central </legend>
					<div class="cuadroFieldset">
						<div class="listadoRespuesta">
							<span class="colocaTitRadio"> SI </span> <span> NO </span> <br />
							<br />
							<div class="titulosRadio">
								<div>Reconocimiento de Grado</div>
							</div>
							<div class="radioTitulos">
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="BReconocimientoGrado" styleClass="opcionRadio"
										value="Y" disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="BReconocimientoGrado" styleClass="opcionRadio"
										value="N" disabled="true" />
								</div>
							</div>
						</div>
					</div>
				</fieldset>
			</logic:equal>

			<div class="espaciador"></div>
			<div class="limpiadora"></div>

			<div class="botonesPagina">
				<logic:equal name="estado"
					value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:aceptar();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Aceptar Solicitud" /> <span> Aceptar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:equal>

				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:volverAtras();"> <img
							src="imagenes/imagenes_ocap/aspa_roja.gif"
							alt="Cancelar Solicitud" /> <span> Cancelar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>

				<logic:equal name="faseIII" value="<%=Constantes.FASE_CC%>">
					<% if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) { %>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:aceptarGradoCC();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Aceptar Validaci&oacute;n CC" /> <span> Aceptar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<%}%>
				</logic:equal>
			</div>

			<div class="limpiadora"></div>

			<logic:equal name="faseIII" value="<%=Constantes.FASE_FIN%>">
				<% if (Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())) { %>
				<div class="limpiadora"></div>
				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviar('OCAPSolicitudes.do?accion=generarPDFIndiviAccesoGrado');">
								<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Generar Informe Individualizado" /> <span> Informe
									Individualizado </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
				<% } %>
			</logic:equal>
		</html:form>
	</div>
	<!-- /fin de ContenidoDCP2A -->
</div>
<!-- /Fin de Contenido -->