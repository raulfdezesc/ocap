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

function modificarCuestionarioObservEvidencia(){
   if(validarCampos()){
      enviar("OCAPSolicitudes.do?accion=modificarCuestionarioObservEvidencia");
   }
}

function cargarCuestionarioObservEvidencia(){
   if(validarCampos()){
      enviar("OCAPSolicitudes.do?accion=irModificarCuestionarioObservEvidencia");
   }
}

function validarCampos(){
   return validarCCuestionarioId();
}

function validarCCuestionarioId(){

   var validado = true;
   
   if (document.forms[0].CCuestionarioId.value == '') {

      alert("Por favor, introduzca un identificador de cuestionario válido.");
      validado = false;
   }
   
   return validado;
}

function limpiarCampos(){
   document.forms[0].CCuestionarioId.value ='';
   document.forms[0].DObservacionesEvidencia.value = '';
}

</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoModificarCuestionario">

		<html:form action="/OCAPSolicitudes.do">

			<h2 class="tituloContenido">Modificar campos cuestionario</h2>

			<fieldset>
				<legend class="tituloLegend"> Observaciones Evidencia </legend>
				<div class="espaciador"></div>

				<div class="cuadroFieldset">
					<!-- CUESTIONARIO -->
					<label for="idCuestionario"> Id Cuestionario: <html:text
							property="CCuestionarioId"
							styleClass="recuadroM colocaApellidosAlta textoNormal"
							maxlength="10"
							onchange="javascript:cargarCuestionarioObservEvidencia();" />
					</label> <br />
					<br />

					<!-- CAMPO OBSERVACIONES EVIDENCIA -->
					<label for="dObservacionesEvidencia"> Observaciones
						Evidencia: <br />
					<br /> <html:textarea property="DObservacionesEvidencia"
							styleClass="recuadroM colocaMiembrosAusentes textoNormal"
							rows="15" cols="120" />
					</label> <br />
					<br />
				</div>

				<div class="botonesPagina colocaBotonBusc">

					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:limpiarCampos();"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif" /> <span>
									Limpiar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>

					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:modificarCuestionarioObservEvidencia();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								class="colocaImgPrint" /> <span> Modificar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>

			</fieldset>

		</html:form>

	</div>
	<!-- /fin de contenidoModificarCuestionario -->
</div>
<!-- /Fin de Contenido -->