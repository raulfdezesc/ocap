<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="java.util.ArrayList"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"></script>


<script language="JavaScript">
function cargarComboConvocatorias(){
   enviar('OCAPSolicitudes.do?accion=cargarComboConvocatorias&jspVuelta=adminFinalizaMC');
}

function limpiar(){
   document.forms[0].CGrado_id.selectedIndex=0;
   document.forms[0].CConvocatoriaId.selectedIndex=0;
   cargarComboConvocatorias();
}

function finalizarMC(){
   if(validarCampos()){
      enviar("OCAPSolicitudes.do?accion=finalizarEvaluacionFinPlazo");
   }
}

function validarCampos(){
   if (document.forms[0].CGrado_id.value==''){
      alert('Debe seleccionar un Grado');
      return false;
   }
   if (document.forms[0].CConvocatoriaId.value==''){
      alert('Debe seleccionar una Convocatoria');
      return false;
   }
   return true;
}

</script>

<div class="contenido">
	<div class="contenidoListadoSolicitudesGerencia">

		<html:form action="/OCAPSolicitudes.do">

			<h2 class="tituloContenido">Finalizar Auto-evaluaci&oacute;n de
				MC</h2>

			<fieldset>
				<legend class="tituloLegend"> Auto-evaluaci&oacute;n de MC
				</legend>
				<div class="espaciador"></div>
				<span class="subTituloListado">Seleccione un Grado y una
					Convocatoria</span><br />
				<br /> <span class="subTituloListado">Se finalizarán los MC
					para todos los usuarios de dicha convocatoria que no lo hayan hecho
					dentro del plazo.</span><br />

				<div class="cuadroFieldset">
					<!-- GRADO -->
					<label for="idVGrado"> Grado: <html:select
							property="CGrado_id" styleClass="cbCuadros colocaGradoBuscCB"
							size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
								property="CGradoId" labelProperty="DDescripcion" />
						</html:select>
					</label>

					<!-- CONVOCATORIA -->
					<label for="idVConvocatoria"> Convocatoria: <html:select
							property="CConvocatoriaId"
							styleClass="cbCuadros colocaConvocatoriaCB" size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
								property="CConvocatoriaId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br />
				</div>

				<div class="botonesPagina colocaBotonBusc">

					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:finalizarMC();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Finalizar MC a todos los usuarios" /> <span>
									Finalizar MC </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>

					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:limpiar();" tabindex="61"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif"
								class="colocaImgPrint" alt="Limpiar" /> <span> Limpiar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>

			</fieldset>

		</html:form>

	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->