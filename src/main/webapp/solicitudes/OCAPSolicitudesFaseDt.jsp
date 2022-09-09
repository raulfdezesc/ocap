<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.creditos.OCAPCreditosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.documentos.OCAPDocumentosOT"%>
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

<script language="JavaScript" type="text/javascript">

function aceptar(){
   enviar('OCAPNuevaSolicitud.do?accion=asignarEvaluador&fase=<%=request.getParameter("fase")%>');
}

function aceptarEval2(){
   enviar('OCAPNuevaSolicitud.do?accion=asignarSegundoEvaluador&fase=<%=request.getParameter("fase")%>');
}

function volverListado(){
   document.forms[0].DApellido1.value='';
   document.forms[0].DNombre.value='';
   //document.forms[0].CDni.value='';
   //document.forms[0].CDniReal.value='';
   enviar('OCAPSolicitudes.do?accion=listarFase&fase=<%=request.getParameter("fase")==null?(request.getAttribute("fase")==null?"":request.getAttribute("fase")):request.getParameter("fase")%>&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
}

function irItinerario(){
   enviar('OCAPCuestionarios.do?accion=irListar&expId=<bean:write name="OCAPNuevaSolicitudForm" property="CExp_id"/>');
}

var listaCreditosJS = new Array();
</script>
<!-- En un Array de JS metemos los creditos necesarios y conseguidos -->
<logic:present name="OCAPNuevaSolicitudForm" property="listaCreditos">
	<logic:iterate id="meritos" name="OCAPNuevaSolicitudForm"
		property="listaCreditos" type="OCAPCreditosOT" indexId="indice">
		<script language="JavaScript" type="text/javascript">
         linea = new Array();
         linea[0] = '<bean:write name="meritos" property="DMerito"/>';
         linea[1] = '<bean:write name="meritos" property="NCreditos"/>';
         linea[2] = '<bean:write name="meritos" property="NCreditosValidadosCc"/>';
         listaCreditosJS[listaCreditosJS.length] = linea;
      </script>
	</logic:iterate>
</logic:present>

<div class="contenido contenidoFaseIII">
	<div class="contenidoDCP3A">

		<html:form action="/OCAPNuevaSolicitud.do">
			<html:hidden property="CExp_id" />
			<html:hidden property="DApellido1" />
			<html:hidden property="DNombre" />
			<html:hidden property="BSexo" />
			<html:hidden property="DCorreoelectronico" />
			<font color="red"> <html:messages id="dEvaluador"
					property="CCompfqs_id" message="true">
					<bean:write name="dNombre" />
					<br />
				</html:messages>
			</font>


			<h2 class="tituloContenido">HISTORIAL DE CARRERA PROFESIONAL
				PARA LA FQS</h2>
			<%-- <a href="javascript:window.print();"><img src="imagenes/imagenes_ocap/imprimir.gif" alt="Imprimir" class="flotaIzq" /></a> --%>
			<h3 class="subTituloContenido">DCP3A</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<%if(jcylUsuario.getUser().getRol() != null && !(jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL))){%>
			<fieldset>
				<legend class="tituloLegend"> Datos Personales </legend>
				<div class="cuadroFieldset">
					<label for="idVApell1" class="colocaDatosVis"> Apellidos: <span><bean:write
								name="OCAPNuevaSolicitudForm" property="DApellido1" /></span>
					</label> <label for="idVNombre" class="colocaDatosVis"> Nombre: <span><bean:write
								name="OCAPNuevaSolicitudForm" property="DNombre" /></span>
					</label> <br />
					<br /> <label for="idVDNI" class="colocaDatosVis">
						NIF/NIE: <span><bean:write name="OCAPNuevaSolicitudForm"
								property="CDniReal" /></span>
					</label> <label for="idVCorreo" class="colocaDatosVis"> Sexo: <logic:equal
							name="OCAPNuevaSolicitudForm" property="BSexo" value="H">
							<span>Hombre</span>
						</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm" property="BSexo"
							value="M">
							<span>Mujer</span>
						</logic:equal>
					</label> <br />
					<br /> <label for="idVTelefono" class="colocaDatosVis">
						Tel&eacute;fono 1: <span><bean:write
								name="OCAPNuevaSolicitudForm" property="NTelefono1" /></span>
					</label> <label for="idVTelefono" class="colocaDatosVis">
						Tel&eacute;fono 2: <span><bean:write
								name="OCAPNuevaSolicitudForm" property="NTelefono2" /></span>
					</label> <br />
					<br /> <label for="idVCorreo" class="colocaDatosVis">
						Correo Electr&oacute;nico: <span><bean:write
								name="OCAPNuevaSolicitudForm" property="DCorreoelectronico" /></span>
					</label> <br />
					<br /> <label for="idVCorreo" class="colocaDatosVisGrande">
						Domicilio, Calle o Plaza y N&ordm;: <span><bean:write
								name="OCAPNuevaSolicitudForm" property="DDomicilio" /></span>
					</label> <br />
					<br /> <label for="idVProvincia" class="colocaDatosVis">
						Provincia: <span> <logic:notEmpty
								name="OCAPNuevaSolicitudForm" property="DProvincia">
								<bean:write name="OCAPNuevaSolicitudForm" property="DProvincia" />
							</logic:notEmpty>
					</span>
					</label> <label for="idVTelefono" class="colocaDatosVis">C&oacute;digo
						Postal: <span><bean:write name="OCAPNuevaSolicitudForm"
								property="CPostalUsu" /></span>
					</label> <br />
					<br /> <label for="idVLocalidades" class="colocaDatosVis">
						Localidad: <span> <logic:notEmpty
								name="OCAPNuevaSolicitudForm" property="DLocalidad">
								<bean:write name="OCAPNuevaSolicitudForm" property="DLocalidad" />
							</logic:notEmpty>
					</span>
					</label>
				</div>
			</fieldset>
			<%}%>

			<fieldset>
				<legend class="tituloLegend"> Datos por los que opta a
					Carrera Profesional </legend>
				<div class="cuadroFieldset colocaDatosVisGrande">
					<html:hidden property="CConvocatoriaId" />
					<!-- GRADO DETALLE -->
					<label for="idVGrado" class="colocaDatosVisGrande"> Grado:
						<span><bean:write name="OCAPNuevaSolicitudForm"
								property="DGrado_des" /></span>
					</label> <br />
					<br />

					<!-- GRADO QUE POSEE (MODALIDAD ANTERIOR) -->
					<label for="idVCategoria" class="colocaDatosVisGrande">Grado
						que posee: <span> <logic:notEmpty
								name="OCAPNuevaSolicitudForm" property="DModAnterior">
								<bean:write name="OCAPNuevaSolicitudForm"
									property="DModAnterior" />
							</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
								property="DModAnterior">-</logic:empty>
					</span>
					</label> <br />
					<br />

					<!-- PROCEDIMIENTO DE EVALUACION DETALLE -->
					<label for="idVCategoria" class="colocaDatosVisGrande">Procedimiento
						de evaluaci&oacute;n por el que opta: <span> <logic:notEmpty
								name="OCAPNuevaSolicitudForm" property="DProcedimiento">
								<bean:write name="OCAPNuevaSolicitudForm"
									property="DProcedimiento" />
							</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
								property="DProcedimiento">-</logic:empty>
					</span>
					</label> <br />
					<br />

					<!-- REGIMEN JURIDICO DETALLE -->
					<label for="idVCategoria" class="colocaDatosVisGrande">R&eacute;gimen
						jur&iacute;dico: <logic:equal name="OCAPNuevaSolicitudForm"
							property="CJuridicoId"
							value="<%=Constantes.C_JURIDICO_ESTATUTARIO_COD%>">
							<span><%=Constantes.C_JURIDICO_ESTATUTARIO%></span>
						</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm" property="CJuridicoId"
							value="<%=Constantes.C_JURIDICO_FUNCIONARIO_COD%>">
							<span><%=Constantes.C_JURIDICO_FUNCIONARIO%></span>
						</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm" property="CJuridicoId"
							value="<%=Constantes.C_JURIDICO_OTROS_COD%>">
							<span><%=Constantes.C_JURIDICO_OTROS%></span>
						</logic:equal> <span> <logic:equal name="OCAPNuevaSolicitudForm"
								property="CJuridicoId"
								value="<%=Constantes.C_JURIDICO_OTROS_COD%>">
								<span><bean:write name="OCAPNuevaSolicitudForm"
										property="DRegimenJuridicoOtros" /></span>
							</logic:equal>
							<logic:equal name="OCAPNuevaSolicitudForm"
												property="CJuridicoId"
												value="<%=Constantes.C_JURIDICO_INTFUNC_COD%>">
												<span><%=Constantes.C_JURIDICO_INTFUNC%></span>
											</logic:equal>
											<logic:equal name="OCAPNuevaSolicitudForm"
												property="CJuridicoId"
												value="<%=Constantes.C_JURIDICO_INTEST_COD%>">
												<span><%=Constantes.C_JURIDICO_INTEST%></span>
											</logic:equal>
					</span>
					</label>

					<logic:equal name="OCAPNuevaSolicitudForm"
						property="CJuridicoCombo"
						value="<%=Constantes.C_JURIDICO_SANITARIO_FIJO_COD%>">
						<br />
						<br />
						<label for="idVCategoria" class="colocaDatosVisGrande">Tipo:
							<span><%=Constantes.C_JURIDICO_SANITARIO_FIJO%></span>
						</label>
					</logic:equal>
					<logic:equal name="OCAPNuevaSolicitudForm"
						property="CJuridicoCombo"
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
						Administrativa: <logic:equal name="OCAPNuevaSolicitudForm"
							property="CSitAdministrativaId"
							value="<%=Constantes.C_SIT_ADM_AUX_ACTIVO_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_ACTIVO%></span>
						</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm"
							property="CSitAdministrativaId"
							value="<%=Constantes.C_SIT_ADM_AUX_SE_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_SE%></span>
						</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm"
							property="CSitAdministrativaId"
							value="<%=Constantes.C_SIT_ADM_AUX_ESSP_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_ESSP%></span>
						</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm"
							property="CSitAdministrativaId"
							value="<%=Constantes.C_SIT_ADM_AUX_EV_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_EV%></span>
						</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm"
							property="CSitAdministrativaId"
							value="<%=Constantes.C_SIT_ADM_AUX_ECF_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_ECF%></span>
						</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm"
							property="CSitAdministrativaId"
							value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_OTROS%></span>
						</logic:equal> <span> <logic:equal name="OCAPNuevaSolicitudForm"
								property="CSitAdministrativaId"
								value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>">
								<span><bean:write name="OCAPNuevaSolicitudForm"
										property="DSitAdministrativaOtros" /></span>
							</logic:equal>
					</span>
					</label> <br />
					<br />

					<!-- CATEGORIA DETALLE -->
					<label for="idVCategoria" class="colocaDatosVisGrande">
						Categor&iacute;a: <span> <logic:notEmpty
								name="OCAPNuevaSolicitudForm" property="DProfesional_nombre">
								<bean:write name="OCAPNuevaSolicitudForm"
									property="DProfesional_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
								property="DProfesional_nombre">-</logic:empty>
					</span>
					</label><br />
					<br />

					<!-- ESPECIALIDAD DETALLE -->
					<label for="idVEspecialidad" class="colocaDatosVisGrande">
						Especialidad: <span> <logic:notEmpty
								name="OCAPNuevaSolicitudForm" property="DEspec_nombre">
								<bean:write name="OCAPNuevaSolicitudForm"
									property="DEspec_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
								property="DEspec_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br />

					<!-- PROVINCIA CARRERA DETALLE -->
					<label for="idVProvincia" class="colocaDatosVis">
						Provincia: <span> <logic:notEmpty
								name="OCAPNuevaSolicitudForm" property="DProvinciaCarrera">
								<bean:write name="OCAPNuevaSolicitudForm"
									property="DProvinciaCarrera" />
							</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
								property="DProvinciaCarrera">-</logic:empty>
					</span>
					</label> <br />
					<br />

					<!-- GERENCIA CARRERA DETALLE-->
					<label for="idVGerencia" class="colocaDatosVisGrande">
						Gerencia: <span> <logic:notEmpty
								name="OCAPNuevaSolicitudForm" property="DGerencia_nombre">
								<bean:write name="OCAPNuevaSolicitudForm"
									property="DGerencia_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
								property="DGerencia_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br />

					<!-- CENTRO DE TRABAJO DETALLE-->
					<label for="idVCentroTrabajo" class="colocaDatosVisGrande">
						Centro de Trabajo: <span> <logic:notEmpty
								name="OCAPNuevaSolicitudForm" property="DCentrotrabajo_nombre">
								<bean:write name="OCAPNuevaSolicitudForm"
									property="DCentrotrabajo_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
								property="DCentrotrabajo_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br />

					<!-- SERVICIO CARRERA DETALLE -->
					<label for="idVEspecialidad" class="colocaDatosVisGrande">
						Servicio: <span> <logic:notEmpty
								name="OCAPNuevaSolicitudForm" property="DServicio">
								<bean:write name="OCAPNuevaSolicitudForm" property="DServicio" />
							</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm" property="DServicio">-</logic:empty>
					</span>
					</label> <br />
					<br />

					<!-- AREA / UNIDAD / PUESTO -->
					<label for="idVEspecialidad" class="colocaDatosVisGrande">
						&Aacute;rea / Unidad / Puesto: <span> <logic:notEmpty
								name="OCAPNuevaSolicitudForm" property="DPuesto">
								<bean:write name="OCAPNuevaSolicitudForm" property="DPuesto" />
							</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm" property="DPuesto">-</logic:empty>
					</span>
					</label> <br />
					<br />

					<!-- AÑOS DE EJECICIO DETALLE -->
					<label for="idVAnt"> <span class="colocaNumAlta">A&ntilde;os
							de ejercicio en la categor&iacute;a profesional por la que se
							accede:</span> <label>N&ordm; A&ntilde;os: <span
							class="textoDatos"><bean:write
									name="OCAPNuevaSolicitudForm" property="NAniosantiguedad" /></span></label> <label>N&ordm;
							Meses: <span class="textoDatos"><bean:write
									name="OCAPNuevaSolicitudForm" property="NMesesantiguedad" /></span>
					</label> <label>N&ordm; D&iacute;as: <span class="textoDatos"><bean:write
									name="OCAPNuevaSolicitudForm" property="NDiasantiguedad" /></span></label>
					</label>
				</div>
			</fieldset>

			<bean:size id="tamanoCreditosValidados" name="OCAPNuevaSolicitudForm"
				property="listaCreditos" />
			<logic:notEqual name="tamanoCreditosValidados" value="0">
				<fieldset>
					<legend class="tituloLegend"> Validaci&oacute;n de
						M&eacute;ritos Curriculares </legend>
					<div class="cuadroFieldset ajusteMargenTabla">
						<table class="tablaFaseEvaluacion">
							<tr>
								<td></td>
								<td>Cr&eacute;ditos Necesarios</td>
								<td>Cr&eacute;ditos Validados</td>
								<td>Cr&eacute;ditos Obtenidos</td>
							</tr>
							<logic:iterate id="meritos" name="OCAPNuevaSolicitudForm"
								property="listaCreditos" type="OCAPCreditosOT" indexId="indice">
								<tr>
									<td class="tit"><bean:write name="meritos"
											property="DMerito" /></td>
									<td><bean:write name="meritos" property="NCreditos" /></td>
									<td><%=meritos.getNCreditosConseguidos()%></td>
									<td><%=meritos.getNCreditosValidadosCc()%></td>
								</tr>
							</logic:iterate>
						</table>
					</div>
				</fieldset>
			</logic:notEqual>

			<%if(jcylUsuario.getUser().getRol() != null && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_FQS_ADTVO) && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION) ){%>
			<fieldset>
				<legend class="tituloLegend"> Evaluaci&oacute;n del
					Desempe&ntilde;o de la Competencia </legend>
				<div class="cuadroFieldset">
					<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_FQS_GEST))){%>
					<logic:equal name="OCAPNuevaSolicitudForm" property="FInformeEval"
						value="">
						<label for="idVCategoria">Evaluador: * <html:select
								property="CCompfqs_id"
								styleClass="cbCuadros colocaEvaluadorCBsolicitudFase" size="1">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_EVALUADORES%>"
									property="CCompfqsId" labelProperty="DApellNom" />
							</html:select>
						</label>
					</logic:equal>
					<logic:notEqual name="OCAPNuevaSolicitudForm"
						property="FInformeEval" value="">
						<label for="idVCategoria">Evaluador: * <html:select
								property="CCompfqs_id"
								styleClass="cbCuadros colocaEvaluadorCBsolicitudFase" size="1"
								disabled="true">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_EVALUADORES%>"
									property="CCompfqsId" labelProperty="DApellNom" />
							</html:select>
						</label>
					</logic:notEqual>
					<%} else {%>
					<label for="idVGrado" class="colocaDatosVis">Evaluador: <span
						class="textoDatos"><bean:write
								name="OCAPNuevaSolicitudForm" property="DApellNom" /></span>
					</label>
					<%}%>
					<br />
					<br />
					<!-- Comentado hata que se sepan posibles estados
               <label for="idVGrado" class="colocaDatosVis">Estado: 
                  <span class="textoDatos"><bean:write name="OCAPNuevaSolicitudForm" property="CFase" /></span>
               </label>
               -->
				</div>
			</fieldset>
			<% } %>
			<%if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION) ){%>
			<fieldset>
				<legend class="tituloLegend"> Evaluaci&oacute;n del
					Desempe&ntilde;o de la Competencia </legend>
				<div class="cuadroFieldset">
					<label for="idVGrado" class="colocaDatosVis">Evaluador: <span
						class="textoDatos"><bean:write
								name="OCAPNuevaSolicitudForm" property="DApellNom" /></span>
					</label> <br />
					<br />
					<br />
					<br /> <span>Se podr&aacute; realizar una segunda
						evaluaci&oacute;n. Para ello deber&aacute; elegir un evaluador de
						los disponibles:</span> <br />
					<br /> <label for="idVCategoria">Segundo Evaluador: <html:select
							property="CCompfqs_id"
							styleClass="cbCuadros colocaEvaluadorCBsolicitudFase" size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_EVALUADORES%>"
								property="CCompfqsId" labelProperty="DApellNom" />
						</html:select>
					</label> <br />
					<br />
					<!-- Comentado hata que se sepan posibles estados
               <label for="idVGrado" class="colocaDatosVis">Estado: 
                  <span class="textoDatos"><bean:write name="OCAPNuevaSolicitudForm" property="CFase" /></span>
               </label>
               -->
				</div>
			</fieldset>
			<% } %>

			<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_FQS_ADTVO))){%>
			<fieldset>
				<legend class="tituloLegend"> Listado de cuestionarios
					escaneados </legend>
				<div class="cuadroFieldset listCuestEscaneados">
					<bean:size id="tamanoListaDocumentos" name="OCAPNuevaSolicitudForm"
						property="listaDocumentos" />
					<logic:notEqual name="tamanoListaDocumentos" value="0">
						<br />
						<br />
						<table class="tablaFondoBlanco" border="0" cellpadding="1"
							cellspacing="1" width="98%" align="center">
							<tr class="cabecera">
								<th class="textoNegrita" width="20%">N&ordm; Evidencia</th>
								<th class="textoNegrita" width="80%">Cuestionario</th>
							</tr>
							<logic:iterate id="lDocumentos" name="OCAPNuevaSolicitudForm"
								property="listaDocumentos" type="OCAPDocumentosOT">
								<tr class="listado">
									<td><a
										href="OCAPDocumentos.do?accion=abrirDocumento&expId=<bean:write name="OCAPNuevaSolicitudForm" property="CExp_id"/>&documentoId=<bean:write name="lDocumentos" property="CDocumento_id"/>"><span><bean:write
													name="lDocumentos" property="DTitulo" /></span></a></td>
									<td><a
										href="OCAPCuestionarios.do?accion=verCuestionario&expId=<bean:write name="OCAPNuevaSolicitudForm" property="CExp_id"/>&idCuestionario=<bean:write name="lDocumentos" property="CCuestionarioId"/>&idRepeticion=<bean:write name="lDocumentos" property="NRepeticion"/>">
											<logic:equal name="lDocumentos" property="BFinalizado"
												value="<%=Constantes.SI%>">
												<span class="textoColorVerde"><bean:write
														name="lDocumentos" property="DCuestionario" /></span>
											</logic:equal> <logic:notEqual name="lDocumentos" property="BFinalizado"
												value="<%=Constantes.SI%>">
												<span><bean:write name="lDocumentos"
														property="DCuestionario" /></span>
											</logic:notEqual>
									</a></td>
								</tr>
							</logic:iterate>
						</table>
					</logic:notEqual>
					<logic:equal name="tamanoListaDocumentos" value="0">
						<span>No hay documentos escaneados en este expediente.</span>
					</logic:equal>
				</div>
			</fieldset>
			<% } %>
			<p>
				<label for="idVNombre" class="colocaDatosVis"> Fecha: <span><bean:write
							name="OCAPNuevaSolicitudForm" property="FRegistro_oficial" /></span>
				</label> <br />
				<html:hidden property="FRegistro_oficial" />
			</p>

			<%if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_FQS_GEST)){%>
			<p>Los campos se&ntilde;alados con * son obligatorios</p>
			<%}%>

			<div class="espaciador"></div>
			<div class="limpiadora"></div>

			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span>
					<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_FQS_GEST) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION))){%>
					<span class="cenBoton"> <a
						href="javascript:volverListado();"> <img
							src="imagenes/imagenes_ocap/aspa_roja.gif"
							alt="Cancelar Solicitud" /> <span> Cancelar </span>
					</a>
					</span>
					<% } else if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_FQS_ADTVO))){ %>
					<span class="cenBoton"> <a
						href="javascript:volverListado();"> <img
							src="imagenes/imagenes_ocap/aspa_roja.gif"
							alt="Volver al listado" /> <span> Cancelar </span>
					</a>
					</span>
					<%} else if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL))){%>
					<%-- OCAP_EVAL --%>
					<span class="cenBoton"> <a
						href="javascript:enviar('OCAPEvaluadores.do?accion=listarEvaluados&cCompfqsIdS=<bean:write name="OCAPNuevaSolicitudForm" property="CCompfqs_id"/>&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');">
							<img src="imagenes/imagenes_ocap/aspa_roja.gif"
							alt="Cancelar Solicitud" /> <span> Cancelar </span>
					</a>
					</span>
					<%}else{%>
					<span class="cenBoton"> <a
						href="javascript:enviar('OCAPEvaluadores.do?accion=listarEvaluados&cCompfqsIdS=<bean:write name="OCAPNuevaSolicitudForm" property="CCompfqs_id"/>&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&nombre=<%=request.getParameter("nombre")%>&apell=<%=request.getParameter("apell")%>&opcion=<%=request.getParameter("opcion")%>&cte=<%=request.getParameter("cte")%>');">
							<img src="imagenes/imagenes_ocap/aspa_roja.gif"
							alt="Cancelar Solicitud" /> <span> Cancelar </span>
					</a>
					</span>
					<%}%>
					<span class="derBoton"></span>
				</div>
				<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_FQS_GEST))){%>
				<logic:equal name="OCAPNuevaSolicitudForm" property="FInformeEval"
					value="">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:aceptar();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Aceptar Solicitud" /> <span> Aceptar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:equal>
				<%}%>
				<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION))){%>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:aceptarEval2();"> <img
							src="imagenes/imagenes_ocap/flecha_correcto.gif"
							alt="Aceptar Solicitud" /> <span> Aceptar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
				<%}%>
				<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_FQS_ADTVO) )){%>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:irItinerario();"> <img
							src="imagenes/imagenes_ocap/flecha_correcto.gif"
							alt="Ver Itinerario" /> <span> Ver Itinerario </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
				<%}%>
			</div>
			<div class="limpiadora"></div>
			<div class="botonesPagina">
				<logic:equal name="inconformidad" value="<%=Constantes.SI%>">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:registrarInconformidad();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Registrar Alegaciones" /> <span> Registrar
									Alegaciones </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:equal>
				<logic:equal name="generarPDF" value="<%=Constantes.SI%>">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:generarPDF();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Generar ultimo PDF" /> <span> Descargar
									&uacute;ltimo PDF enviado </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:equal>
			</div>
			<div class="limpiadora"></div>
		</html:form>

	</div>
	<!-- /fin de ContenidoDCP2A -->
</div>
<!-- /Fin de Contenido -->