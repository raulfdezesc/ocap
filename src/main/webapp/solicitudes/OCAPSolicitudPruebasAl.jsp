<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.modalidadAnterior.OCAPModalidadAnteriorOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>

<%
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="JavaScript" type="text/javascript"
	src="<html:rewrite page='/javascript/combos1.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/solicitudes/validacionesNuevaSolicitud.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/fechas.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/ventanas.js'/>"></script>

<script language="JavaScript">
function cargarItinerarios(){
   if (document.forms[0].CGrado_id.value!='')
   	document.forms[0].DGrado_des.value = document.forms[0].CGrado_id.options[document.forms[0].CGrado_id.selectedIndex].text;
   if (document.forms[0].CProfesional_id.value!='')
   	document.forms[0].DProfesional_nombre.value = document.forms[0].CProfesional_id.options[document.forms[0].CProfesional_id.selectedIndex].text;
   if (document.forms[0].CEspec_id && document.forms[0].CEspec_id.value!='')
   	document.forms[0].DEspec_nombre.value = document.forms[0].CEspec_id.options[document.forms[0].CEspec_id.selectedIndex].text;
   if (document.forms[0].CProfesional_id.value != '')
      enviar('OCAPNuevaSolicitud.do?accion=irInsertarUsuarioPruebas');
}

function aceptarItinerario(){
   if(document.forms[0].CGrado_id.value=='')
      alert('Debe elegir un \'Grado\'.');
   else if(document.forms[0].CProcedimientoId.value=='')
      alert('Debe elegir un \'Procedimiento\'.');
   else if (document.forms[0].CProfesional_id.value == '')
      alert('Debe elegir una \'Categor\u00EDa\'.');
   else if(document.forms[0].CItinerarioId.value=='')
      alert('Debe elegir un \'Manual\'.');
   else
      enviar('OCAPNuevaSolicitud.do?accion=guardarItinerarioUsuarioPruebas');
}

function limpiar(){
   document.forms[0].CGrado_id.value='';
   document.forms[0].CProcedimientoId.value='';
   document.forms[0].CProfesional_id.value='';
   if (document.forms[0].CItinerarioId)
      document.forms[0].CItinerarioId.value='';
   enviar('OCAPNuevaSolicitud.do?accion=irInsertarUsuarioPruebas');
}
</script>

<div class="contenido ">
	<div class="altaPruebaItinerario">

		<html:form action="/OCAPNuevaSolicitud.do">
			<html:hidden property="CExp_id" />

			<h2 class="tituloContenido">Prueba de un manual de
				evaluaci&oacute;n</h2>

			<!-- ELEGIR MANUAL -->
			<fieldset>
				<legend class="tituloLegend"> Manual </legend>
				<div class="cuadroFieldset">

					<!-- GRADO -->
					<label for="idVGrado">Grado: * <html:select
							property="CGrado_id" styleClass="cbCuadros grado" size="1"
							onchange="javascript:cargarItinerarios();">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_GRADOS_ALTA%>"
								property="CGradoId" labelProperty="DDescripcion" />
						</html:select>
					</label>
					<html:hidden property="DGrado_des" />
					<br />
					<br />

					<!-- PROCEDIMIENTO DE EVALUACION -->
					<label for="idVCategoria">Procedimiento de
						evaluaci&oacute;n: * <html:select property="CProcedimientoId"
							styleClass="cbCuadros procedimiento" size="1"
							onchange="javascript:cargarItinerarios();">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_PERSONAL%>"
								property="CProcedimientoId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br />

					<!-- CATEGORIA -->
					<label for="idVCategoria">Categor&iacute;a: * <html:select
							property="CProfesional_id" styleId="CProfesional_id"
							styleClass="cbCuadros categoria"
							onchange="javascript:cargarItinerarios();">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
								property="CProfesionalId" labelProperty="DNombre" />
						</html:select>
					</label>
					<html:hidden property="DProfesional_nombre" />
					<br />
					<br />

					<!-- ESPECIALIDAD -->
					<%if(((ArrayList) session.getAttribute(Constantes.COMBO_ESPECIALIDAD)).size() > 0 ){%>
					<label for="idVEspecialidad">Especialidad: * <html:select
							property="CEspec_id" styleId="CEspec_id"
							styleClass="cbCuadros especialidad"
							onchange="javascript:cargarItinerarios();">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
								property="CEspecId" labelProperty="DNombre" />
						</html:select>
					</label>
					<html:hidden property="DEspec_nombre" />
					<br />
					<br />
					<%}%>

					<!-- ITINERARIO -->
					<%if(((ArrayList) session.getAttribute(Constantes.COMBO_ITINERARIOS)).size() > 0 ){%>
					<label for="idVEspecialidad">Manual: * <html:select
							property="CItinerarioId" styleClass="cbCuadros manual">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_ITINERARIOS%>"
								property="CItinerarioId" labelProperty="DDescripcion" />
						</html:select>
					</label> <br />
					<br />
					<%}%>
				</div>
			</fieldset>

			<div class="espaciadorPeq"></div>
			<p>Los campos se&ntilde;alados con * son obligatorios</p>
			<div class="limpiadora"></div>
			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:limpiar();"> <img
							src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" /> <span>
								Limpiar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:aceptarItinerario();"> <img
							src="imagenes/imagenes_ocap/flecha_correcto.gif"
							alt="Generar PDF" /> <span> Aceptar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>

			<div class="espaciadorPeq"></div>

		</html:form>

	</div>
	<!-- /fin de ContenidoDCP1A_PGP -->
</div>
<!-- /Fin de Contenido -->

