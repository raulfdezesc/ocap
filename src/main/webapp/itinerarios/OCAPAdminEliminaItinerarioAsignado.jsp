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
   enviar('OCAPSolicitudes.do?accion=cargarComboConvocatorias&jspVuelta=adminEliminaItinerario');
}

function limpiar(){
   document.forms[0].CGrado_id.selectedIndex=0;
   document.forms[0].CConvocatoriaId.selectedIndex=0;
   document.forms[0].CDni.value='';
   cargarComboConvocatorias();
}

function eliminarItinerarioAsignado(){
   if(validarCampos()){
      if (validar_nif(document.forms[0].CDni.value))
         enviar("OCAPSolicitudes.do?accion=eliminarItinerarioAsignadoUsuario");
      else 
         alert('El NIF/NIE no es correcto.');
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
   if (document.forms[0].CDni.value==''){
      alert('Debe indicar el NIF/NIE de un usuario');
      return false;
   }
   return true;
}

function eliminar(evt) {
   //se admiten: numeros . ,
   var nav4 = window.Event ? true : false;
   var key = nav4 ? evt.which : evt.keyCode;
   if (key == null)
      key = nav4 ? evt.keyCode : evt.which;
   if (key == 13)
      eliminarItinerarioAsignado();
}

</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoEliminaItinerario">

		<html:form
			action="/OCAPSolicitudes.do?accion=irEliminarItinerarioAsignadoUsuario">

			<h2 class="tituloContenido">Eliminar Itinerario Asignado</h2>

			<fieldset>
				<legend class="tituloLegend"> Itinerarios </legend>
				<div class="espaciador"></div>
				<span class="subTituloListado">Seleccione un Grado y una
					Convocatoria e indique el NIF/NIE del usuario</span><br />
				<br /> <span class="subTituloListado">Se eliminar&aacute; el
					Itinerario que dicho usuario tenga asignado o haya seleccionado.</span><br />

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

					<!-- CONVOCATORIA -->
					<label for="idVDni"> NIF/NIE: <html:text property="CDni"
							styleClass="cbCuadros colocaDniCB" maxlength="10"
							onkeypress="eliminar(event);" />
					</label> <br />
					<br />
				</div>

				<div class="botonesPagina colocaBotonBusc">

					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:eliminarItinerarioAsignado();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Eliminar el Itinerario asignado del usuario" /> <span>
									Eliminar Itinerario Asignado </span>
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