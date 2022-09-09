<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
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
	src="<html:rewrite page='/javascript/formularios.js'/>"
	charset="windows-1252"></script>

<script language="JavaScript" type="text/javascript">
function ejecuta(){
	if(document.forms[0].CConvocatoriaId.value != ""){
		if (confirm('Se va a ejecutar un procedimiento que establecerá en estado "No aporta méritos asistenciales" a todas las solicitudes que no hayan aportado méritos asistenciales. ¿Desea continuar?')){
     		enviar('OCAPNoAportaMeritos.do?accion=ejecutarMa');
     	}
  	}else{
   		alert('Tiene que seleccionar una convocatoria en la que ejecutar el procedimiento.');
  	}
}

</script>

<%--div class="contenido contenidoFaseIII"--%>
<div class="contenido">
	<div class="contenidoListadoAspirantesGCP listadoFaseIII">

		<html:form action="/OCAPSolicitudes.do">

			<h2 class="tituloContenido">SOLICITUDES QUE NO APORTAN M&Eacute;RITOS ASISTENCIALES</h2>
			<h3 class="subTituloContenido">Procedimiento para marcar solicitudes en estado "No aporta m&eacute;ritos asistenciales"</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<fieldset>
				<legend class="tituloLegend"> Seleccione convocatoria </legend>

				<div class="cuadroFieldset">
					<label> Convocatoria: <html:select
							property="CConvocatoriaId"
							styleClass="cbCuadros colocaConvocatoriaBuscCB" size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
								property="CConvocatoriaId" labelProperty="DNombre" />
						</html:select>
					</label>
					<div style="line-height: 3px; height: 3px; margin: 8px;"></div>
				</div>

				<div style="margin-left: auto; margin-right: auto;">
					<div class="botonAccion" style="margin-left: 2%;">
						<span class="izqBoton"></span> <span class="cenBoton" style="padding: 1px;"> <a
							href="javascript:ejecuta();" tabindex="61"> <img
								src="imagenes/imagenes_ocap/dobleFlecha.gif"
								class="colocaImgPrint" alt="Ejecutar Procedimiento" /> <span>
									Ejecutar procedimiento </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
				<div style="line-height: 3px; height: 3px; margin: 15px;"></div>
			</fieldset>

			<div class="espaciador"></div>
		</html:form>

	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->
