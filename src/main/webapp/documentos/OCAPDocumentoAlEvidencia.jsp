<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.documentos.OCAPDocumentosOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.areasItinerarios.OCAPAreasItinerariosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"
	charset="windows-1252"></script>

<script language="javascript" type="text/javascript">
   function isIE () {
      var myNav = navigator.userAgent.toLowerCase();
      return (myNav.indexOf('msie') != -1) ? parseInt(myNav.split('msie')[1]) : false;
    }

   function guardar() {
      var permitir= true;
     
      if  (isIE () == 8) 
     {

         if(permitir && document.forms(0).ADatos.value == ""){
             alert("Debe seleccionar un fichero antes de guardarlo");
             document.forms(0).ADatos.focus();        
          }else{
               
             enviar('OCAPDocumentos.do?accion=guardarDocumento&expId=<bean:write name="OCAPDocumentosForm" property="CExpId"/>&nDocumento=1&cuestionarioId=0&cEvidenciaID=0&pagina=evidenciaDoc');
          }
     }else{
          if(permitir && document.forms[0].ADatos.value == ""){
             alert("Debe seleccionar un fichero antes de guardarlo");
             document.forms[0].ADatos.focus();        
          }else{
               
             enviar('OCAPDocumentos.do?accion=guardarDocumento&expId=<bean:write name="OCAPDocumentosForm" property="CExpId"/>&nDocumento=1&cuestionarioId=0&cEvidenciaID=0&pagina=evidenciaDoc');
          }
    }

   }
      
   function abrir(documento,publico,usuario) {
      enviar('OCAPDocumentos.do?accion=abrirDocumento');
   }
  
   function volverListado() {
    enviar('OCAPCuestionarios.do?accion=irProteccionDatos');
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
			<h2 class="tituloContenido">DOCUMENTOS DE EVIDENCIAS</h2>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>


			<fieldset>

				<legend class="tituloLegend"> Documento de Evidencias </legend>
				<div class="cuadroFieldset listadoCuestionariosEscaneados">
					<div class="tablaInterior">
						<!-- sustituye a la tabla interior -->
						<div class="textoRojo">
							<html:messages id="errorTamanoFichero"
								property="errorTamanoFichero" message="true">
								<bean:write name="errorTamanoFichero" />
								<br />
							</html:messages>
						</div>
						<br />
						<br />
						<logic:equal name="verFinProcesoDocumento"
							value="<%=Constantes.NO%>">
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
									<br />&nbsp;<br />
									<span style="color: #0431B4; margin: 12%;">No se
										permiten guardar ficheros de más de <%=es.jcyl.framework.JCYLConfiguracion.getValor("TAMNO_FICHERO")%>Mb
									</span>
								</logic:equal>
							</logic:equal>
					</div>
					<!-- sustituye a la tabla interior -->
					<div class="espaciadorPeq"></div>
					<br />&nbsp;<br />

					<logic:empty name="OCAPDocumentosForm" property="listaDocumentos">
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
					</logic:empty>
					<logic:notEmpty name="OCAPDocumentosForm" property="listaDocumentos">
						<logic:equal name="OCAPDocumentosForm" property="nuevoDocumento" value="S">
						<script>
							alert("El fichero se ha adjuntado correctamente");
						</script>
						</logic:equal>
					</logic:notEmpty>
					</logic:equal>

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
										<logic:equal name="verFinProcesoDocumento"
											value="<%=Constantes.NO%>">
											<th width="95%">Documento</th>
											<th width="5%"></th>
										</logic:equal>
									</tr>
									<logic:iterate id="lDocumentos" name="OCAPDocumentosForm"
										property="listaDocumentos" type="OCAPDocumentosOT">
										<tr>
											<td class="normal"><a
												href="OCAPDocumentos.do?accion=abrirDocumento&expId=<bean:write name="OCAPDocumentosForm" property="CExpId"/>&documentoId=<bean:write name="lDocumentos" property="CDocumento_id"/>">
													<span><bean:write name="lDocumentos"
															property="DDescripcion" /></span>
											</a></td>
											<logic:equal name="verFinProcesoDocumento"
												value="<%=Constantes.NO%>">
												<td class="centrado"><a class="alIzq"
													href="javascript:if(confirm('\u00BFDesea eliminar este documento?')){enviar('OCAPDocumentos.do?accion=borrarDocumento&cExpId=<bean:write name="OCAPDocumentosForm" property="CExpId"/>&cDocId=<bean:write name="lDocumentos" property="CDocumento_id"/>&tarea=<bean:write name="OCAPDocumentosForm" property="tarea"/>&tareaDocu=<bean:write name="OCAPDocumentosForm" property="tareaDocu"/>&pagina=evidenciaDoc');}">
														<img src="imagenes/imagenes_ocap/aspa_roja.gif"
														alt="Eliminar este documento" />
												</a></td>
											</logic:equal>
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
				<%--   </logic:equal--%>
			</div>
		</html:form>
	</div>
</div>


