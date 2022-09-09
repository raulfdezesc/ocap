<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.documentos.OCAPDocumentosOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.areasItinerarios.OCAPAreasItinerariosOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.creditosCuestionarios.OCAPCreditosCuestionariosOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/fechas.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>

<script language="javascript">
function generarInforme2PDF(){
   enviar('OCAPCuestionarios.do?accion=generarInforme2PDF&expId='+document.forms[0].CExpId.value)
}

function solicitarAuditoria() {
   if (validar()) {
      enviar('OCAPCuestionarios.do?accion=guardarAuditoria');
   }
}

function validar(){
   if (document.forms[0].FAuditoriaPropuesta.value=='' || document.forms[0].HAuditoriaPropuesta.value=='') {
      alert("La Fecha y Horario propuestos son obligatorios.");
      return false; 
   }
   if (document.forms[0].FAuditoriaPropuesta.value.length > 0) {
      if (!ValidarFechaConAlerta(document.forms[0].FAuditoriaPropuesta,''))
      return false;
   }
   if (!(esHoraMinutosCorrecta(document.forms[0].HAuditoriaPropuesta.value))){
      alert("El formato de \"Horario\" no es correcto.");
      return false;  
   }
   return true;
}

function volverEvaluacion(){
   <% if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CTE)){%>
      enviar('OCAPCuestionarios.do?accion=irListar&expId='+document.forms[0].CExpId.value);
   <%}else{%>
      enviar('OCAPCuestionarios.do?accion=irListar&cCompfqsIdS=<%=request.getAttribute("cCompfqsIdS")%>&expId='+document.forms[0].CExpId.value);
   <%}%>
}

function volverListado(){
   enviar('OCAPEvaluadores.do?accion=listarEvaluados&cCompfqsIdS=<%=session.getAttribute("cCompfqsIdS")%>&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
}

function verItinerario(){
   enviar('OCAPCuestionarios.do?accion=irListar&expId='+document.forms[0].CExpId.value);
}

</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoAutoevaluacion">
		<div class="evaluacionEvaluador">
			<html:form action="/OCAPCuestionarios.do">
				<h2 class="tituloContenido">SOLICITUD DE AUDITOR&Iacute;A</h2>
				<% if (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)) {%>
				<a href="javascript:window.print();"><img
					src="imagenes/imagenes_ocap/imprimir.gif" alt="Imprimir"
					class="flotaIzq" /></a>
				<% }%>
				<!--<h3 class="subTituloContenido"> Evaluaci&oacute;n/Pruebas de buena pr&aacute;ctica asignados a cada &Aacute;REA </h3>-->
				<h3 class="subTituloContenido">&nbsp;</h3>
				<div class="lineaBajaM"></div>
				<div class="espaciador"></div>

				<fieldset>
					<legend class="tituloLegend">DATOS</legend>
					<div class="cuadroFieldset">
						<span>C&oacute;digo de Itinerario: </span>
						<bean:write name="OCAPCuestionariosForm"
							property="DNombreItinerario" />
						<br />
						<br /> <span>Centro de procedencia del profesional
							evaluado: </span>
						<bean:write name="OCAPCuestionariosForm"
							property="DCentrotrabajo_nombre" />
						<br />
						<br /> <span>Servicio de referencia: </span>
						<bean:write name="OCAPCuestionariosForm"
							property="DCategoriaEspecialidadEvaluado" />
						<br />
						<br />
						<div class="espaciador"></div>
						<div class="lineaBajaM"></div>
						<br />
						<logic:iterate id="elemento" name="OCAPCuestionariosForm"
							property="listadoAuditorias" type="OCAPCreditosCuestionariosOT">
							<span>Tipo de registro:</span>
							<bean:write name="elemento" property="ATipoRegistro" />
							<br />
							<br />
							<span>Documento:</span>
							<bean:write name="elemento" property="ADocumento" />
							<br />
							<br />
							<span>N&ordm; H&ordf; Cl. de referencia</span>
							<span>1</span>
							<span class="historiaClinic">&nbsp;<bean:write
									name="elemento" property="ANHistoria1" /></span>
							<span>2</span>
							<span class="historiaClinic">&nbsp;<bean:write
									name="elemento" property="ANHistoria2" /></span>
							<span>3</span>
							<span class="historiaClinic">&nbsp;<bean:write
									name="elemento" property="ANHistoria3" /></span>
							<br />
							<br />
							<div class="lineaBajaM"></div>
							<br />
						</logic:iterate>

						<p class="indexar">
							Fecha propuesta:
							<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
								<span class="bordeCuadroFecha2"><bean:write
										name="OCAPCuestionariosForm" property="FAuditoriaPropuesta" /></span>
							</logic:equal>
							<logic:notEqual name="informeGenerado" value="<%=Constantes.SI%>">
								<html:text styleClass="bordeCuadroFecha"
									name="OCAPCuestionariosForm" property="FAuditoriaPropuesta"
									maxlength="10" />
								<a
									href='javascript:show_Calendario("OCAPCuestionariosForm", "FAuditoriaPropuesta", document.forms[0].FAuditoriaPropuesta.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
								<span class="horario">Horario: (hh:mm)</span>
								<html:text styleClass="bordeCuadroFecha"
									name="OCAPCuestionariosForm" property="HAuditoriaPropuesta"
									maxlength="5" />
							</logic:notEqual>
						</p>
						<br />
						<br />

					</div>
				</fieldset>

				<html:hidden property="CExpId" />

				<div class="limpiadora"></div>
				<div class="espaciador"></div>

				<div class="botonesPagina">
					<logic:notEqual name="informeGenerado" value="<%=Constantes.SI%>">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:solicitarAuditoria();"> <img
									src="imagenes/imagenes_ocap/action_forward.gif"
									class="colocaImgPrint" alt="Guardar Solicitud Auditoria" /> <span>
										Solicitar Auditor&iacute;a </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverEvaluacion();"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Cancelar" /> <span>
										Cancelar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</logic:notEqual>

					<logic:equal name="informeGenerado" value="<%=Constantes.SI%>">
						<% if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL)) {%>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverEvaluacion();"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Volver" /> <span> Volver </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<% }else{ %>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverListado();"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Volver" /> <span> Volver </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<% } %>
					</logic:equal>
				</div>
			</html:form>
		</div>
	</div>
	<!-- /fin de ContenidoMC -->
</div>
<!-- /Fin de Contenido -->

<div class="limpiadora"></div>
