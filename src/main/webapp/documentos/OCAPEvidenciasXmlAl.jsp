
<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="bean.tld" prefix="bean"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.documentos.OCAPDocumentosOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.areasItinerarios.OCAPAreasItinerariosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.creditosCeis.OCAPCreditosCeisOT"%>
<%@ page import="java.util.ArrayList"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>

<script language="javascript">
   function guardar(){
/*      if(document.forms[0].ADatos.value == ""){
         alert("Debe seleccionar un fichero XML");
         document.forms[0].ADatos.focus();
      } else if(! document.forms[0].ADatos.value.toUpperCase().match(".\\.XML$")){
         alert("El fichero debe estar en formato XML");
         document.forms[0].ADatos.focus();
      } else
      */         
      enviar('OCAPDocumentos.do?accion=cargarXML');
}
</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoDCP2A">
		<html:form action="OCAPDocumentos.do" enctype="multipart/form-data">

			<h2 class="tituloContenido">CARGAR XML</h2>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<fieldset>
				<legend class="tituloLegend"> Evidencias </legend>
				<div class="cuadroFieldset listadoCuestionariosEscaneados">
					<!--         <div class="tablaInterior">
            <label for="ADatos"> Archivo: 
               <html:file property="ADatos" onkeydown="alert('Presione en examinar');" styleClass="campoReadOnly colocaArchivoEscaneado" onkeypress="alert('Presione en examinar');"  />
            </label>
         </div> -->
					<div class="cuadroFieldset">
						<p class="textoNormal ajusteLOPD">Seleccionar la casilla si se
							quiere que los datos que se van a tratar prevalezcan sobre los
							existentes en caso de que así ocurra.</p>
						<div class="textoCentrado">
							<html:checkbox name="OCAPDocumentosForm" property="BTratar"
								value="S" />
							<div class="espaciadorPeq"></div>
						</div>
					</div>
					<div class="espaciadorPeq"></div>
					<div class="botonesPagina">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:guardar();"> <img
									src="imagenes/imagenes_ocap/icon_accept.gif"
									class="colocaImgPrint" alt="Guardar Formulario" /> <span>
										Guardar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</div>

					<div class="limpiadora"></div>
				</div>
			</fieldset>

		</html:form>
	</div>
</div>
