<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>

<script language="JavaScript">
function validarFormulario(formu) {
   if(formu.CConvocatoriaId.value == '') {
         alert('Debe rellenar la convocatoria la cual desea gestionar permisos');
         return false;
   }
   return true;
}

function aceptarCopiarDNI() {
   if(validarFormulario(document.forms[0])) {
      enviar('OCAPNuevaSolicitud.do?accion=copiarDNI');
   }
}
</script>

<div class="contenido">
	<div
		class="contenidoListadoAspirantesGCP solicitudesLs buscadorSolicitudes">
		<html:form action="/OCAPNuevaSolicitud.do">
			<h2 class="tituloContenido">GESTIÓN DE PERMISOS DE DIRECTORIO
				CORPORATIVO</h2>
			<h3 class="subTituloContenido">GESTIÓN DE PERMISOS</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<fieldset>
				<legend class="tituloLegend"> Formulario </legend>
				<div class="cuadroFieldset">
					<label for="idVConvocatoria"> Convocatoria: <html:select
							property="CConvocatoriaId"
							styleClass="cbCuadros colocaConvocatoria" size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
								property="CConvocatoriaId" labelProperty="DNombre" />
						</html:select>
					</label>
				</div>

				<div class="botonesPagina colocaBotonBusc">

					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:aceptarCopiarDNI();" tabindex="61"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								class="colocaImgPrint" alt="Aceptar" /> <span> Aceptar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</fieldset>

			<logic:present name="OCAPNuevaSolicitudForm" property="listaUsuarios">
				<bean:size id="numDNIRepe" name="OCAPNuevaSolicitudForm"
					property="listaUsuarios" />
				<logic:notEqual name="numDNIRepe" value="0">
					<span class="colorError">No se puede dar acceso a
						M&eacute;ritos por que existen los siguientes NIF/NIE repetidos:</span>
					<br />
					<br />
					<logic:iterate id="usuario" name="OCAPNuevaSolicitudForm"
						property="listaUsuarios" type="OCAPUsuariosOT">
						<bean:write name="usuario" property="CDniReal" />
						<br />
					</logic:iterate>
				</logic:notEqual>
			</logic:present>

		</html:form>

	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->