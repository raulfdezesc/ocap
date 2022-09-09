<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.documentos.OCAPDocumentosOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.areasItinerarios.OCAPAreasItinerariosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript">
   
   function guardar() {
      var permitir= true;
      if(permitir && document.forms[0].DTitulo.value == "") {
         alert("Debe rellenar el campo 'Evidencia'");
         document.forms[0].DTitulo.focus();
         permitir = false;
      }
      if(permitir && document.forms[0].NDocumentos.value == "") {
         alert("Debe rellenar el campo 'Nº de Documentos'");
         document.forms[0].DTitulo.focus();
         permitir = false;
      }
      if (permitir && document.forms[0].CAreaId.value=="") {
         alert("Debe seleccionar un \u00c1rea.");
         document.forms[0].CAreaId.focus();
         permitir= false;
      }
      var hayCuestionariosEnArea = true;
      if(document.forms[0].CCuestionarioId == null || document.forms[0].CCuestionarioId == 'undefined' || document.forms[0].CCuestionarioId.length <= 1) hayCuestionariosEnArea = false;
      if(permitir){
         if (!hayCuestionariosEnArea) {
            alert("El \u00c1rea seleccionada no tiene cuestionarios a los que subir documentos. Seleccione otro \u00e1rea.");
            document.forms[0].CAreaId.focus();
            permitir= false;
         } else if (document.forms[0].CCuestionarioId.value=="") {
            alert("Debe seleccionar un Cuestionario");
            document.forms[0].CCuestionarioId.focus();
            permitir = false;
         }
      }
      /*
      if(permitir && document.forms[0].DDescripcion.value == ""){
         alert("Debe rellenar el campo 'Descripci\u00f3n'");
         document.forms[0].DDescripcion.focus();
         permitir = false;
      }
      */
      if(permitir && document.forms[0].ADatos.value == ""){
         alert("Debe seleccionar un fichero antes de guardarlo");
         document.forms[0].ADatos.focus();
         permitir = false;
      }
      if (permitir) {
         alert('Le recordamos que la informaci\u00F3n que est\u00E1 manejando es confidencial y seg\u00FAn la Ley 25.326 de Protecci\u00F3n de datos personales no se debe utilizar dicha informaci\u00F3n para finalidades distintas o incompatibles con aquellas que motivaron su obtenci\u00F3n. Por tanto los documentos que usted ha escaneado deben ser eliminados inmediatamente de su ordenador.');
         enviar('OCAPDocumentos.do?accion=guardarDocumento&expId=<bean:write name="OCAPDocumentosForm" property="CExpId"/>');
      }

   }
   
   function modificar() {
      if(document.forms[0].DTitulo.value == "") {
         alert("Debe rellenar el campo 'Título'");
         document.forms[0].DTitulo.focus();
         return false;
      }
      /*
      if(document.forms[0].DDescripcion.value == ""){
         alert("Debe rellenar el campo 'Descripción'");
         document.forms[0].DDescripcion.focus();
         return false;
      }
      */
      if(top.opener.document.forms[0].btnActualizar.disabled != true) {
         if(confirm("Antes de seguir debería guardar los cambios en el proyecto, ¿Desea continuar?")) {
            enviar('OCAPDocumentos.do?accion=modificarDocumento&CDocumento_id=' + document.forms[0].CDocumento_id.value);
         }
      } else {
         enviar('OCAPDocumentos.do?accion=modificarDocumento&CDocumento_id=' + document.forms[0].CDocumento_id.value);
      }
   }
   
   function abrir(documento,publico,usuario) {
      enviar('OCAPDocumentos.do?accion=abrirDocumento');
   }
   
   function cargarComboCuestionarios() {
      enviar('OCAPDocumentos.do?accion=irAltaDocumento');
   }
   
   function finalizar() {
      if (confirm('Ya no podr\u00e1 incluir m\u00e1s documentos a este expediente. \u00bfEst\u00e1 seguro de finalizar?')) {
         enviar('OCAPDocumentos.do?accion=finalizarSubidaEscaneados&cExpId=<bean:write name="OCAPDocumentosForm" property="CExpId" />');
      }
   }
   
   function volverListado() {
      alert('No olvide que debe pinchar en \'Finalizar\' cuando haya inclu\u00eddo todos los documentos de este expediente para que pueda continuar el proceso.');
      enviar('OCAPSolicitudes.do?accion=irListar&fase=<%=Constantes.FASE_CA_TERMINADA%>&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.NO%>');
   }
   
   function borrar(expId, docId, tarea, tareaDocu) {
      if (confirm('\u00BFDesea eliminar este documento?')) {
         enviar('OCAPDocumentos.do?accion=borrarDocumento&cExpId='+expId+'&cDocId='+docId+'&tarea='+tarea+'&tareaDocu='+tareaDocu);
      }
   }
   
</script>

<div class="contenido">
	<div class="contenidoDCP2A">
		<html:form action="OCAPDocumentos.do?accion=guardarDocumento"
			enctype="multipart/form-data">
			<html:hidden name="OCAPDocumentosForm" property="CProyecto_id" />
			<html:hidden name="OCAPDocumentosForm" property="CUsuario_id" />
			<html:hidden name="OCAPDocumentosForm" property="CDocumento_id" />
			<html:hidden name="OCAPDocumentosForm" property="tareaDocu" />
			<html:hidden name="OCAPDocumentosForm" property="tarea" />
			<html:hidden name="OCAPDocumentosForm" property="CExpId" />
			<html:hidden name="OCAPDocumentosForm" property="CItinerarioId" />
			<h2 class="tituloContenido">HISTORIAL DE CARRERA PROFESIONAL
				PARA LA FQS</h2>
			<h3 class="subTituloContenido">DCP3A</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<%--
         <fieldset>
            <legend class="tituloLegend"> Datos Personales </legend>
            <div class="cuadroFieldset">
               <label for="idVApell1" class="colocaDatosVis"> Apellidos:
                  <span><bean:write name="OCAPDocumentosForm" property="DApellido1" /></span>
               </label>				
               <label for="idVNombre" class="colocaDatosVis"> Nombre: 
                  <span><bean:write name="OCAPDocumentosForm" property="DNombre" /></span>
               </label>
               <br /><br />
               <label for="idVDNI" class="colocaDatosVis"> NIF/NIE: 
                  <span><bean:write name="OCAPDocumentosForm" property="CDniReal" /></span>
               </label>
               <label for="idVCorreo" class="colocaDatosVis"> Sexo:
                  <logic:equal name="OCAPDocumentosForm" property="BSexo" value="H">
                     <span>Hombre</span>
                  </logic:equal>
                  <logic:equal name="OCAPDocumentosForm" property="BSexo" value="M">
                     <span>Mujer</span>
                  </logic:equal>
               </label>
               <br /><br />							
               <label for="idVCorreo" class="colocaDatosVis"> Correo Electr&oacute;nico:
                  <span><bean:write name="OCAPDocumentosForm" property="DCorreoelectronico" /></span>
               </label>
               <label for="idVTelefono" class="colocaDatosVis"> Tel&eacute;fono:
                  <span><bean:write name="OCAPDocumentosForm" property="NTelefono1" /></span>
               </label>				
               <br /><br />
            </div>
         </fieldset>
         <fieldset>
            <legend class="tituloLegend"> Datos Profesionales Actuales</legend>
            <div class="cuadroFieldset modificarInteralineado">
               <label for="idVCategoria" class="colocaDatosVisGrande">R&eacute;gimen jur&iacute;dico:
                  <logic:equal name="OCAPDocumentosForm" property="CJuridico" value="<%=Constantes.C_JURIDICO_ESTATUTARIO_COD%>" >
                     <span><%=Constantes.C_JURIDICO_ESTATUTARIO%></span>
                  </logic:equal>
                  <logic:equal name="OCAPDocumentosForm" property="CJuridico" value="<%=Constantes.C_JURIDICO_FUNCIONARIO_COD%>" >
                     <span><%=Constantes.C_JURIDICO_FUNCIONARIO%></span>
                  </logic:equal>
                  <logic:equal name="OCAPDocumentosForm" property="CJuridico" value="<%=Constantes.C_JURIDICO_OTROS_COD%>" >
                     <span><%=Constantes.C_JURIDICO_OTROS%></span>
                  </logic:equal> 
               </label>
               <br /><br />
               <label for="idVCategoria" class="colocaDatosVisMuyGrande">Situaci&oacute;n Administrativa: 
                  <logic:equal name="OCAPDocumentosForm" property="CSitAdministrativaAuxId" value="<%=Constantes.C_SIT_ADM_AUX_ACTIVO_COD%>" >
                     <span><%=Constantes.C_SIT_ADM_AUX_ACTIVO%></span>
                  </logic:equal>
                  <logic:equal name="OCAPDocumentosForm" property="CSitAdministrativaAuxId" value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>" >
                     <span><%=Constantes.C_SIT_ADM_AUX_OTROS%>: <bean:write name="OCAPDocumentosForm" property="DSitAdministrativaAuxOtros" /></span>
                  </logic:equal>
               </label>
               <br /><br />
               <label for="idVCategoria" class="colocaDatosVisGrande"> Personal:
                  <span><bean:write name="OCAPDocumentosForm" property="DEstatutarioActual_nombre" /></span>
               </label>
               <br /><br />
               <label for="idVCategoria" class="colocaDatosVisGrande"> Categor&iacute;a: 
                  <span><bean:write name="OCAPDocumentosForm" property="DProfesionalActual_nombre" /></span>
               </label><br /><br />
               <label for="idVEspecialidad" class="colocaDatosVisGrande"> Especialidad: 
                  <span>
                     <logic:notEmpty name="OCAPDocumentosForm" property="DEspecActual_nombre">
                        <bean:write name="OCAPDocumentosForm" property="DEspecActual_nombre" />
                     </logic:notEmpty>
                     <logic:empty name="OCAPDocumentosForm" property="DEspecActual_nombre">-</logic:empty>
                  </span>
               </label>
               <br /><br />
               <label for="idVProvincia" class="colocaDatosVis"> Provincia: 
                  <span>
                     <logic:notEmpty name="OCAPDocumentosForm" property="DProvincia">
                        <bean:write name="OCAPDocumentosForm" property="DProvincia" />
                     </logic:notEmpty>
                     <logic:empty name="OCAPDocumentosForm" property="DProvincia">-</logic:empty>
                  </span>
               </label>
               <label for="idVProvincia" class="colocaDatosVis"> Localidad: 
                  <span>
                     <logic:notEmpty name="OCAPDocumentosForm" property="ACiudad">
                        <bean:write name="OCAPDocumentosForm" property="ACiudad" />
                     </logic:notEmpty>
                     <logic:empty name="OCAPDocumentosForm" property="ACiudad">-</logic:empty>
                     <html:hidden name="OCAPDocumentosForm" property="ACiudad" />
                  </span>
               </label>
               <br /><br />			
               <label for="idVTipoGerencia" class="colocaDatosVis"> Tipo Gerencia: 
                  <span>
                     <logic:notEmpty name="OCAPDocumentosForm" property="DTipogerencia_desc">
                        <bean:write name="OCAPDocumentosForm" property="DTipogerencia_desc" />
                     </logic:notEmpty>
                     <logic:empty name="OCAPDocumentosForm" property="DTipogerencia_desc">-</logic:empty>
                  </span>
               </label>
               <label for="idVGerencia" class="colocaDatosVis"> Gerencia: 
                  <span>
                     <logic:notEmpty name="OCAPDocumentosForm" property="DGerencia_nombre">
                        <bean:write name="OCAPDocumentosForm" property="DGerencia_nombre" />
                     </logic:notEmpty>
                     <logic:empty name="OCAPDocumentosForm" property="DGerencia_nombre">-</logic:empty>
                  </span>
               </label>
               <br /><br />
               <label for="idVCentro" class="colocaDatosVisGrande"> Centro de Trabajo: 
                  <span>
                     <logic:notEmpty name="OCAPDocumentosForm" property="DCentrotrabajo_nombre">
                        <bean:write name="OCAPDocumentosForm" property="DCentrotrabajo_nombre" />
                     </logic:notEmpty>
                     <logic:empty name="OCAPDocumentosForm" property="DCentrotrabajo_nombre">-</logic:empty>
                  </span>
               </label>
               <br /><br />
               <label for="idVCategoria" class="colocaDatosVisGrande"> Procedimiento de evaluaci&oacute;n por el que opta:
                  <span><bean:write name="OCAPDocumentosForm" property="DSitAdministrativa_nombre" /></span>
               </label>
            </div>	
         </fieldset>
         <fieldset>
            <legend class="tituloLegend">  Datos por los que opta a Carrera Profesional </legend>
            <div class="cuadroFieldset">
               <label for="idVGrado" class="colocaDatosVis">Grado:
                  <span><bean:write name="OCAPDocumentosForm" property="DGrado_des" /></span>
               </label>
               <br /><br />
               <label for="idVCategoria" class="colocaDatosVisGrande"> Personal:
                  <span><bean:write name="OCAPDocumentosForm" property="DEstatutario_nombre" /></span>
               </label>
               <br /><br />
               <label for="idVCategoria" class="colocaDatosVisGrande"> Categor&iacute;a: 
                  <span><bean:write name="OCAPDocumentosForm" property="DProfesional_nombre" /></span>
               </label><br /><br />
               <label for="idVEspecialidad" class="colocaDatosVisGrande"> Especialidad: 
                  <span>
                     <logic:notEmpty name="OCAPDocumentosForm" property="DEspec_nombre">
                        <bean:write name="OCAPDocumentosForm" property="DEspec_nombre" />
                     </logic:notEmpty>
                     <logic:empty name="OCAPDocumentosForm" property="DEspec_nombre">-</logic:empty>
                  </span>
               </label>
               <div class="espaciador"></div>               
            </div>
         </fieldset>
         <bean:size id="tamanoCreditosValidados" name="OCAPDocumentosForm" property="listaCreditos" />
         <logic:notEqual name="tamanoCreditosValidados" value="0" >
            <fieldset>
               <legend class="tituloLegend"> Validaci&oacute;n de M&eacute;ritos Curriculares </legend>
               <div class="cuadroFieldset">
                  <table class="tablaFaseEvaluacion">
                     <tr class="titulos">
                        <td></td>
                        <td> Cr&eacute;ditos Necesarios </td>
                        <td> Cr&eacute;ditos Obtenidos </td>
                        <td> Cr&eacute;ditos Validados </td>
                     </tr>
                     <logic:iterate id="meritos" name="OCAPDocumentosForm" property="listaCreditos" type="OCAPCreditosCeisOT" indexId="indice" >
                        <tr>
                           <td class="tit"><bean:write name="meritos" property="DDefMeritoNombre"/></td>
                           <td><bean:write name="meritos" property="NCreditosNecesarios"/></td>
                           <td><%=meritos.getNCreditos()%></td>
                           <td><%=meritos.getNCreditosValidados()%></td>
                        </tr>
                     </logic:iterate>
                  </table>
                  <br /><br />
                  <div class="listadoRespuesta">
                     <span class="colocaTitRadio2"> SI </span>
                     <span> NO </span>
                     <br /><br />
                     <div class="titulosRadio">
                        <div> Validaci&oacute;n de la Comisi&oacute;n Central </div>
                     </div>                        
                     <div class="radioTitulos">
                        <div>
                           <html:radio name="OCAPDocumentosForm" property="BValidacion_cc" styleClass="opcionRadio" value="Y" disabled="true"/>
                           <html:radio name="OCAPDocumentosForm" property="BValidacion_cc" styleClass="opcionRadio" value="N" disabled="true"/>
                        </div>  
                     </div>                      
                  </div>                   
               </div>
            </fieldset>
         </logic:notEqual>
         --%>
			<fieldset>
				<legend class="tituloLegend"> C&oacute;digo de Evaluado </legend>
				<div class="cuadroFieldset">
					<label>C&oacute;digo: <span><bean:write
								name="OCAPDocumentosForm" property="codigoId" /></span>
					</label>
				</div>
			</fieldset>
			<fieldset>
				<legend class="tituloLegend"> Listado de Cuestionarios
					Escaneados </legend>
				<div class="cuadroFieldset listadoCuestionariosEscaneados">
					<div class="tablaInterior">
						<!-- sustituye a la tabla interior -->
						<label for="DTitulo"> Evidencia: <html:select
								name="OCAPDocumentosForm" styleClass="colocaTituloEscaneado"
								property="DTitulo" size="1">
								<html:option value="">Seleccione...</html:option>
								<%--
                           <bean:define id="numEvidencias" name="OCAPDocumentosForm" property="NEvidencias" type="java.lang.String" />
                           <% for (int numEvi = 1; numEvi <= Integer.parseInt(numEvidencias); numEvi++) { %>
                           <html:option value="<%=Integer.toString(numEvi)%>"><%=numEvi%></html:option>
                           <% } %>
                        --%>
								<bean:size id="tamanoListadoEvidencias"
									name="OCAPDocumentosForm" property="listadoEvidenciasNoUsadas" />
								<logic:notEqual name="tamanoListadoEvidencias" value="0">
									<logic:iterate id="evidencia" name="OCAPDocumentosForm"
										property="listadoEvidenciasNoUsadas" type="java.lang.String">
										<html:option value="<%=evidencia%>">
											<bean:write name="evidencia" />
										</html:option>
									</logic:iterate>
								</logic:notEqual>
							</html:select>
						</label> <label for="DDescripcion"> N&ordm; de Documentos: <html:text
								name="OCAPDocumentosForm" property="NDocumentos" maxlength="2"
								styleClass="recuadroM colocaNumDocumentosEscaneado"
								onkeypress="if(event.keyCode==13) guardar()" />
						</label>
						<%--
                  <label for="DDescripcion"> Descripci&oacute;n:
                     <html:text name="OCAPDocumentosForm" property="DDescripcion" maxlength="50" styleClass="recuadroM colocaDescripcionEscaneado" onkeypress="if(event.keyCode==13) guardar()" />
                  </label>
                  --%>
						<br />
						<br />
						<%--
                  <logic:equal name="OCAPDocumentosForm" property="tareaDocu" value="modificacion">                           
                     <label for="ADatos">Archivo actual:                              
                        <acronym title="<bean:write name="OCAPDocumentosForm" property="DDescripcion"/>">
                           <logic:equal name="OCAPDocumentosForm" property="tarea" value="actualizar">
                              <a href="javascript:abrir('<bean:write name="OCAPDocumentosForm" property="CDocumento_id"/>','<bean:write name="OCAPDocumentosForm" property="APublic"/>','<bean:write name="OCAPDocumentosForm" property="CUsuario_id"/>')">
                           </logic:equal>
                           <bean:write name="OCAPDocumentosForm" property="DTitulo"/>.<bean:write name="OCAPDocumentosForm" property="AExt"/>
                           <logic:equal name="OCAPDocumentosForm" property="tarea" value="actualizar">
                              </a>
                           </logic:equal>
                        </acronym>
                        &nbsp;&nbsp;&nbsp;&nbsp;<a class="campoOpcional">Subido por: </a><a class="campoReadOnly"><bean:write name="OCAPDocumentosForm" property="ANombre_usuario"/></a>
                     </label>
                     <logic:equal name="OCAPDocumentosForm" property="tarea" value="actualizar">                           
                        <label for="ADatos">Archivo cambiar:                              
                           <html:file property="ADatos" onkeydown="alert('Presione en examinar');blur();" onkeypress="alert('Presione en examinar');blur();" style="width:450px;" />
                        </label>                           
                     </logic:equal>
                  </logic:equal>
                  --%>
						<html:hidden name="OCAPDocumentosForm" property="APublic" />
						<label for="CAreaId">&Aacute;rea: <html:select
								name="OCAPDocumentosForm" styleClass="colocaAreaEscaneado"
								property="CAreaId" size="1"
								onchange="javascript:cargarComboCuestionarios();">
								<html:option value="">Seleccione...</html:option>
								<logic:iterate id="area" name="OCAPDocumentosForm"
									property="listadoAreas" type="OCAPAreasItinerariosOT">
									<html:option value="<%=area.getCAreaId().toString()%>">
										<bean:write name="area" property="DNombreLargo" />
									</html:option>
								</logic:iterate>
							</html:select>
						</label> <br />
						<br /> <label for="CCuestionarioId"> Cuestionario: <html:select
								name="OCAPDocumentosForm"
								styleClass="colocaCuestionarioEscaneado"
								property="CCuestionarioId" styleId="CCuestionarioId" size="1">
								<html:option value="">Seleccione...</html:option>
								<logic:iterate id="cuestionario" name="OCAPDocumentosForm"
									property="listadoCuestionarios" type="OCAPCuestionariosOT">
									<html:option
										value="<%=Long.toString(cuestionario.getCCuestionarioId())%>">
										<bean:write name="cuestionario" property="DArea" />
										<bean:write name="cuestionario" property="DNombre" />&nbsp;-&nbsp;<bean:write
											name="cuestionario" property="DNombreLargo" />
									</html:option>
								</logic:iterate>
							</html:select>
						</label> <br />
						<br />
						<logic:equal name="OCAPDocumentosForm" property="tarea"
							value="actualizar">
							<logic:equal name="OCAPDocumentosForm" property="tareaDocu"
								value="alta">
								<label for="ADatos"> Archivo: <html:file
										property="ADatos"
										onkeydown="alert('Presione en examinar');blur();"
										styleClass="campoReadOnly colocaArchivoEscaneado"
										onkeypress="alert('Presione en examinar');blur();" />
								</label>
							</logic:equal>
						</logic:equal>
					</div>
					<!-- sustituye a la tabla interior -->
					<div class="espaciadorPeq"></div>
					<br />&nbsp;<br />
					<div class="botonesPagina">
						<div class="botonAccion">
							<logic:equal name="OCAPDocumentosForm" property="tarea"
								value="actualizar">
								<logic:equal name="OCAPDocumentosForm" property="tareaDocu"
									value="alta">
									<span class="izqBoton"></span>
									<span class="cenBoton"> <a href="javascript:guardar();">
											<img src="imagenes/imagenes_ocap/icon_accept.gif"
											class="colocaImgPrint" alt="Guardar Formulario" /> <span>
												Guardar </span>
									</a>
									</span>
									<span class="derBoton"></span>
								</logic:equal>
								<%--
                        <logic:equal name="OCAPDocumentosForm" property="tareaDocu" value="modificacion">
                           <input type="BUTTON" value="GUARDAR" name="" onclick="javascript:modificar();"/>
                        </logic:equal>
                        --%>
							</logic:equal>
						</div>
					</div>
					<div class="limpiadora"></div>
					<logic:present name="OCAPDocumentosForm" property="listaDocumentos">
						<bean:size id="tamanoListaDocumentos" name="OCAPDocumentosForm"
							property="listaDocumentos" />
						<logic:notEqual name="tamanoListaDocumentos" value="0">
							<br />
							<div class="tablaEvidenciasCentrada">
								<table class="tablaDocumentosEscaneados" border="0"
									cellpadding="1" cellspacing="1" width="95%" align="center">
									<tr>
										<th width="10%">N&uacute;mero de Evidencia</th>
										<th width="75%">Cuestionario</th>
										<th width="5%"></th>
									</tr>
									<logic:iterate id="lDocumentos" name="OCAPDocumentosForm"
										property="listaDocumentos" type="OCAPDocumentosOT">
										<tr>
											<td class="centrado">
												<%-- <a href="OCAPDocumentos.do?accion=abrirDocumento&expId=<bean:write name="OCAPDocumentosForm" property="CExpId"/>&documentoId=<bean:write name="lDocumentos" property="CDocumento_id"/>">
                                    --%> <bean:write name="lDocumentos"
													property="DTitulo" /> <%-- </a> --%>
											</td>
											<td class="normal"><a
												href="OCAPDocumentos.do?accion=abrirDocumento&expId=<bean:write name="OCAPDocumentosForm" property="CExpId"/>&documentoId=<bean:write name="lDocumentos" property="CDocumento_id"/>">
													<span><bean:write name="lDocumentos"
															property="DCuestionario" /></span>
											</a></td>
											<td class="centrado"><a class="alIzq"
												href="javascript:if(confirm('\u00BFDesea eliminar este documento?')){enviar('OCAPDocumentos.do?accion=borrarDocumento&cExpId=<bean:write name="OCAPDocumentosForm" property="CExpId"/>&cDocId=<bean:write name="lDocumentos" property="CDocumento_id"/>&tarea=<bean:write name="OCAPDocumentosForm" property="tarea"/>&tareaDocu=<bean:write name="OCAPDocumentosForm" property="tareaDocu"/>');}">
													<img src="imagenes/imagenes_ocap/aspa_roja.gif"
													alt="Eliminar este documento" />
											</a></td>
										</tr>
									</logic:iterate>
								</table>
							</div>
						</logic:notEqual>
					</logic:present>
				</div>
			</fieldset>
			<div class="espaciador"></div>
			<div class="limpiadora"></div>
			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:volverListado();"> <img
							src="imagenes/imagenes_ocap/action_stop.gif" alt="Volver" /> <span>
								Volver </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <logic:equal
							name="permisoFinalizar" value="<%=Constantes.SI%>">
							<a href="javascript:finalizar();"> <img
								src="imagenes/imagenes_ocap/icon_accept.gif" alt="Finalizar" /><span>
									Finalizar </span>
							</a>
						</logic:equal> <logic:notEqual name="permisoFinalizar"
							value="<%=Constantes.SI%>">
							<a
								href="javascript:alert('No puede finalizar la subida de documentos hasta que el usuario no finalice su itinerario. Pulse \'Volver\' para salir. ');">
								<img src="imagenes/imagenes_ocap/icon_accept.gif"
								alt="Finalizar" /><span> Finalizar </span>
							</a>
						</logic:notEqual>
					</span> <span class="derBoton"></span>
				</div>
				<%--   </logic:equal--%>
			</div>
		</html:form>
	</div>
</div>

<script language="javascript" type="text/javascript">
   if (document.forms[0].tareaDocu.value == 'alta') {
      document.forms[0].APublic.checked = true;
   }
   if (document.forms[0].tarea.value == 'leer') {
      document.forms[0].DTitulo.disabled = true;
      //document.forms[0].DDescripcion.disabled = true;
      document.forms[0].APublic.disabled = true;
   }
</script>
