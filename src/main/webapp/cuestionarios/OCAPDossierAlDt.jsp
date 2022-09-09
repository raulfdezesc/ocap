<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="bean.tld" prefix="bean"%>

<%@ page
	import="es.jcyl.fqs.ocap.ot.encuestaCalidad.OCAPEncuestaCalidadOT"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>

<script language="javascript">
function guardar(){
   enviar('OCAPCuestionarios.do?accion=guardarDossier');
}

function deshabilitarDossier(){
   for (i=0; i < document.forms[0].elements.length; i++) {
      if (document.forms[0].elements[i].type=='checkbox')
         document.forms[0].elements[i].disabled=true;
   }
}

function activarEvidencia(i){
   if (document.forms[0].idEvidencia[i].checked == true){
       document.forms[0].idEvidencia[i].value= i;
   }
}

</script>
<div class="contenido contenidoFaseIII">
	<div class="contenidoDCP1A dossier">
		<html:form action="/OCAPCuestionarios.do">
			<h3 class="subTituloContenido">Dossier Personal de Carrera
				Profesional: Relaci&oacute;n de documentos</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<span>
				<p>Muy se&ntilde;or m&iacute;o:</p>
				<p>De acuerdo a sus intrucciones adjunto las evidencias
					documentales que se han ido incluyendo en mi evaluaci&oacute;n de
					competencias profesionales para la obtenci&oacute;n/reconocimiento
					del grado de Carrera Profesional solicitado.</p>
				<p>La relaci&oacute;n de documentos es:</p> <bean:define
					id="nEvidencia" name="OCAPCuestionariosForm" property="NEvidencias" />
				<logic:notEqual name="nEvidencia" value="0">
					<table class="tablaDossier">
						<tr>
							<td class="introCabecera"></td>
							<td class="cabecera centrar"><span>Seleccione los que
									usted aporte</span></td>
							<td class="cabecera centrar"><span>Subidos al
									expediente profesional</span></td>
							<td class="cabecera centrar"><span>N&uacute;mero de
									documentos</span></td>
						<tr>
							<logic:iterate id="evidencia" name="OCAPCuestionariosForm"
								property="listaEvidencias" indexId="iEvidencia"
								type="OCAPEncuestaCalidadOT">
								<tr>
									<td><span>Evidencia Documental n&ordm; <%=iEvidencia.intValue()+1%></span></td>
									<td class="centrar"><html:checkbox
											name="OCAPCuestionariosForm" property="idEvidencia"
											onclick='<%="activarEvidencia("+iEvidencia+")"%>'
											value="<%=evidencia.getBEntregado()%>" /></td>
									<td class="centrar">
										<% if (Constantes.SI.equals(evidencia.getBEscaneado())) { %> <input
										class="tipo1" type="checkbox" checked disabled="true" /> <% } else { %>
										<input class="tipo1" type="checkbox" disabled="true" /> <% } %>
									</td>
									<td class="centrar"><bean:write name="evidencia"
											property="NDocumentos" /></td>
								</tr>
							</logic:iterate>
							<script language="javascript">
                           var respuestas = document.getElementsByName('idEvidencia');
                           for (var i=0; i < respuestas.length; i++){
                              if(respuestas[i].value=='<%=Constantes.SI%>'){
                                 respuestas[i].checked = true;
                                 respuestas[i].value = i;
                              }
                           }
                     </script>
					</table>
				</logic:notEqual>
			</span>
			<fieldset>
				<legend class="tituloLegend"> Mis datos de
					identificaci&oacute;n son: </legend>
				<div class="cuadroFieldset">
					<label for="idVNombre"> C&oacute;digo de Evaluado: <span><bean:write
								name="OCAPCuestionariosForm" property="codigoId" /></span>
					</label>

				</div>
			</fieldset>
			<div class="espaciador"></div>
			<div class="botonAccion ocultarCampo">
				<div class="botonAccion flotaDerBotones">
					<span class="izqBoton"></span>
					<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<span class="cenBoton"> <a
							href="javascript:window.print();guardar();"> <img
								src="imagenes/imagenes_ocap/icon_accept.gif"
								class="colocaImgPrint" alt="Guardar Dossier e Imprimirlo" /> <span>
									Guardar e Imprimir </span>
						</a>
						</span>
					</logic:equal>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<span class="cenBoton"> <a
							href="javascript:window.print();"> <img
								src="imagenes/imagenes_ocap/icon_accept.gif"
								class="colocaImgPrint" alt="Imprimir dossier" /> <span>
									Imprimir </span>
						</a>
						</span>
						<script language="javascript">
                        deshabilitarDossier();
                     </script>
					</logic:equal>
					<span class="derBoton"></span>
				</div>
			</div>
		</html:form>
	</div>
	<!-- contenidoDCP1A -->
</div>