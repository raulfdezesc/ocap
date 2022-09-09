<%@ page contentType="text/html;charset=ISO-8859-1"	pageEncoding="windows-1252"%>
<%@ page import="es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.modalidadAnterior.OCAPModalidadAnteriorOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<script type="text/javascript"
		src="<html:rewrite page='/javascript/jquery/jquery.js'/>"
		charset="windows-1252"></script>
	<script type="text/javascript"
		src="<html:rewrite page='/javascript/jquery/jquery.cascade.js'/>"
		charset="windows-1252"></script>
	<script type="text/javascript"
		src="<html:rewrite page='/javascript/jquery/jquery.cascade.ext.js'/>"
		charset="windows-1252"></script>
	<script type="text/javascript"
		src="<html:rewrite page='/javascript/jquery/jquery.templating.js'/>"
		charset="windows-1252"></script>

<script type="text/javascript">
$(document).ready(function() {
	desactivarEstado();
});

function desactivarEstado(){
		if( document.forms[0].eliminarEvaluacion.checked || document.forms[0].eliminarEvaluacion.checked== "true"){
		document.forms[0].CEstadoFiltro.value = 9;
		document.forms[0].eliminarEvaluacion.value = true;
		document.forms[0].eliminarEvaluacion.checked = true;
		document.forms[0].CEstadoFiltro.disabled = true;
		}else{
			document.forms[0].CEstadoFiltro.disabled=false;
			document.forms[0].eliminarEvaluacion.value = false;
			document.forms[0].eliminarEvaluacion.checked = false;
		}
	}
	
	function bloquearAsistenciales() {
		if( document.forms[0].bloqueoAsistenciales.checked || document.forms[0].bloqueoAsistenciales.checked== "true"){
			document.forms[0].bloqueoAsistenciales.value = true;
			document.forms[0].bloqueoAsistenciales.checked = true;
		}else{
				document.forms[0].bloqueoAsistenciales.value = true;
			document.forms[0].bloqueoAsistenciales.checked = true;
		}
	}

</script>


	<%JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);%>

	<script type="text/javascript"
		src="<html:rewrite page='/javascript/comun.js'/>"
		charset="windows-1252"></script>
	<script type="text/javascript"
		src="<html:rewrite page='/javascript/combos1.js'/>"
		charset="windows-1252"></script>
	<script type="text/javascript"
		src="<html:rewrite page='/javascript/calendario.js'/>"
		charset="windows-1252"></script>
	<script type="text/javascript"
		src="<html:rewrite page='/javascript/solicitudes/validacionesNuevaSolicitud.js'/>"
		charset="windows-1252"></script>
	<script type="text/javascript"
		src="<html:rewrite page='/javascript/formularios.js'/>"
		charset="windows-1252"></script>
	<script type="text/javascript"
		src="<html:rewrite page='/javascript/fechas.js'/>"
		charset="windows-1252"></script>
	<script type="text/javascript"
		src="<html:rewrite page='/javascript/ventanas.js'/>"
		charset="windows-1252"></script>

	<script type="text/javascript"
		src="<html:rewrite page='/javascript/jquery/jquery.js'/>"
		charset="windows-1252"></script>
	<script type="text/javascript"
		src="<html:rewrite page='/javascript/jquery/jquery.cascade.js'/>"
		charset="windows-1252"></script>
	<script type="text/javascript"
		src="<html:rewrite page='/javascript/jquery/jquery.cascade.ext.js'/>"
		charset="windows-1252"></script>
	<script type="text/javascript"
		src="<html:rewrite page='/javascript/jquery/jquery.templating.js'/>"
		charset="windows-1252"></script>

	<script type="text/javascript">
	
	
	
	

    var listaGradosJS = new Array();
   </script>

	<logic:present name="<%=Constantes.COMBO_GRADOS_ALTA%>">
		<logic:iterate id="grados" name="<%=Constantes.COMBO_GRADOS_ALTA%>"
			type="OCAPGradoOT">
			<script type="text/javascript">
         linea = new Array();
         linea[0] = '<bean:write name="grados" property="CGradoId"/>';
         linea[1] = '<bean:write name="grados" property="NAniosejercicio"/>';
         linea[2] = '<bean:write name="grados" property="CConvocatoriaId"/>';
         linea[3] = '<bean:write name="grados" property="FFinRegistro"/>';
         listaGradosJS[listaGradosJS.length] = linea;
      </script>
		</logic:iterate>
	</logic:present>

	<script type="text/javascript">
   var listaGradosAnterioresJS = new Array();
</script>

	<logic:present name="<%=Constantes.COMBO_MOD_ANTERIOR%>">
		<logic:iterate id="grados" name="<%=Constantes.COMBO_MOD_ANTERIOR%>"
			type="OCAPModalidadAnteriorOT">
			<script type="text/javascript">
         linea = new Array();
         linea[0] = '<bean:write name="grados" property="CModAnteriorId"/>';
         linea[1] = '<bean:write name="grados" property="CGradoId"/>';
         listaGradosAnterioresJS[listaGradosAnterioresJS.length] = linea;
      </script>
		</logic:iterate>
	</logic:present>

	<script type="text/javascript">

var LONG_MAX_DOBSERVACIONES = <%=Constantes.LONG_SUBSANACIONES%>;
var LONG_MAX_DMOTIVOS = <%=Constantes.LONG_MOTIV_INCONF%>;
var LONG_MAX_DMOTIVOSCAMBIO = <%=Constantes.LONG_MOTIVOS_CAMBIO%>;
var LONG_MAX_DOTROSMOTIVOSEXCLUSION = <%=Constantes.LONG_OTROS_MOTIVOS_EXCLUSION%>;

function isNumerico(cadena){
		 var valid = "0123456789";
         var cadenaValue = cadena.value;

		 var caracterTexto;
		 var validacion; 		   
	 	 for (var i=0; i<cadenaValue.length; i++){
           
			caracterTexto = cadenaValue.substring(i,i+1);
			validacion = valid.indexOf(caracterTexto);
            if (validacion == -1 ){ 
				return false;
 				} 		
		 }

		 return true;
	}


function aceptarSolicitud(){
	   if(validarFechas()) {    
	         var mensaje ="\u00BFest\u00e1 seguro de querer modificar la solicitud?";
	         if(confirm(mensaje)){
	            enviar('OCAPNuevaSolicitud.do?accion=modificarFechasExpediente'); 
	         }
	   }
}

function aceptarDesbloquearSolicitud(){   
		 document.forms[0].desbloqueoAsistenciales.value = true;
         if((document.forms[0].fFin_ca_convocatoria.value == null || document.forms[0].fFin_ca_convocatoria.value == "") &&
         (document.forms[0].FFin_ca.value == null || document.forms[0].FFin_ca.value == "")) 
       
         	alert("Es obligatorio rellenar la fecha de fin de CA");
         	else{
        			aceptarSolicitud();
 				 }
}

function validarFechas(){
      var cadena1;
      var cadena2;
      var cadena3;
      var cadena4;
      var cadena5;
      var cadena6;
      var cadena7;
      var cadena8;
      var cadena9;
      var cadena10;

      cadena1 = trim(document.forms[0].FInicio_mc.value);
      cadena2 = trim(document.forms[0].FFin_mc.value);
      cadena3 = trim(document.forms[0].FInicio_eval_mc.value);
      cadena4 = trim(document.forms[0].FNegacion_mc.value);
      cadena5 = trim(document.forms[0].FAceptacionceis.value);
      cadena6 = trim(document.forms[0].FInicio_ca.value);
      cadena7 = trim(document.forms[0].FFin_ca.value);
      cadena8 = trim(document.forms[0].FInformeEval.value);
      cadena9 = trim(document.forms[0].FRenuncia.value);
      
      document.forms[0].cItineraio_id.value = trim(document.forms[0].cItineraio_id.value);
      cadena10 = document.forms[0].cItineraio_id.value;


      if (cadena1 != ''){
        if (!(esFechaCorrecta(cadena1))){
            alert("Formato de \" Fecha de inicio de autoevaluaci\u00f3n de m\u00e9ritos \" no es correcto.");
            return false;
         }
       }   
         
      if (cadena2 != ''){
         if (!(esFechaCorrecta(cadena2))){
            alert("Formato de \" Fecha de fin de autoevaluaci\u00f3n de m\u00e9ritos \" no es correcto.");
            return false;
         }
      }   

      if (cadena3 != '') {
         if (!(esFechaCorrecta(cadena3))){
            alert("Formato de \" Fecha de inicio de valoraci\u00f3n de MC \" no es correcto.");
            return false;
         }
      }

      if (cadena4 != '') {
        if (!(esFechaCorrecta(cadena4))){
            alert("Formato de \" Fecha de negaci\u00f3n de MC \" no es correcto.");
            return false;
         }
      }
      
       if (cadena5 != '') {
            if (!(esFechaCorrecta(cadena5))){
                alert("Formato de \" Fecha de aceptaci\u00f3n de CEIS \" no es correcto.");
                return false;
             }         
        }
        
         if (cadena6 != '') {
            if (!(esFechaCorrecta(cadena6))){
                alert("Formato de \" Fecha de inicio asistencial \" no es correcto.");
                return false;
             }
        }
         
         if (cadena7 != '') {
            if (!(esFechaCorrecta(cadena7))){
                alert("Formato de \" Fecha de fin asistencial \" no es correcto.");
                return false;
             }
        }
        
         if (cadena8 != '') { 
              if (!(esFechaCorrecta(cadena8))){
                alert("Formato de \" Fecha de informe de evaluaci\u00f3n \" no es correcto.");
                return false;
             }
        }
        
        if (cadena9 != ''){
	        if (!(esFechaCorrecta(cadena9))){
	            alert("Formato de \" Fecha de renuncia \" no es correcto.");
	            return false;
	         }
       }else if (cadena9 == '' && document.forms[0].CEstadoFiltro.value == '15'){
       	        alert("Es necesario cumplimentar el campo \" Fecha de renuncia \" ");
	            return false;
       }
       
      if (cadena10 != ''){
        if (!(isNumerico(document.forms[0].cItineraio_id))){
            alert("El itinerario introducido no es correcto.");
            return false;
         }
      } 

     
         
   return true;      
}


</script>



	<div class="contenido">
		<div class="contenidoDCP1A altaPGP">
			<html:form action="/OCAPNuevaSolicitud.do">
				<html:hidden property="NAniosejercicio" />
				<!-- <html:hidden property="CConvocatoriaId"/>-->
				<html:hidden property="FFinRegistro" />
				<html:hidden property="CGradoAnteriorId" />
				<html:hidden property="FPlazapropiedad" />
				<html:hidden property="CExp_id" />
				<html:hidden property="CExp_id" />
				<html:hidden property="fFin_ca_convocatoria" />
				<html:hidden property="desbloqueoAsistenciales"/>

				<h2 class="tituloContenido">Datos de Solicitud de Acceso a
					Grado de Carrera Profesional</h2>


				<h3 class="subTituloContenidoMC">1.- DATOS PERSONALES:</h3>
				<fieldset>
					<legend class="tituloLegend"> 1.1.- Datos personales </legend>



					<html:hidden name="OCAPNuevaSolicitudForm" property="DApellido1" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="DNombre" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="CDni" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="CDniReal" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="NTelefono1" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="NTelefono2" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="DCorreoelectronico" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="BSexo" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="DDomicilio" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="CProvincia_id" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="DProvincia" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="CLocalidad_id" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="DLocalidad" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="CPostalUsu" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="CSolicitudId" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="FRegistro_solic" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="CUsr_id" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="CEstatutId" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="FNegacion_solic" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="BCierreSo" />

					<div class="cuadroFieldset">
						<label for="idVApell1" class="colocaDatosVis">Apellidos: <span><bean:write
									name="OCAPNuevaSolicitudForm" property="DApellido1" /></span>
						</label> <label for="idVNombre" class="colocaDatosVis">Nombre: <span><bean:write
									name="OCAPNuevaSolicitudForm" property="DNombre" /></span>
						</label> <br />
						<br /> <label for="idVDNI" class="colocaDatosVis">NIF/NIE:
							<span><bean:write name="OCAPNuevaSolicitudForm"
									property="CDniReal" /></span>
						</label> <label for="idVCorreo" class="colocaDatosVis">Sexo: <logic:equal
								name="OCAPNuevaSolicitudForm" property="BSexo" value="H">
								<span>Hombre</span>
							</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm" property="BSexo"
								value="M">
								<span>Mujer</span>
							</logic:equal>
						</label> <br />
						<br /> <label for="idVTelefono" class="colocaDatosVis">Tel&eacute;fono
							1: <span><bean:write name="OCAPNuevaSolicitudForm"
									property="NTelefono1" /></span>
						</label> <label for="idVTelefono" class="colocaDatosVis">Tel&eacute;fono
							2: <span><bean:write name="OCAPNuevaSolicitudForm"
									property="NTelefono2" /></span>
						</label> <br />
						<br /> <label for="idVCorreo" class="colocaDatosVis">Correo
							Electr&oacute;nico: <span><bean:write
									name="OCAPNuevaSolicitudForm" property="DCorreoelectronico" /></span>
						</label> <br />
						<br /> <label for="idVCorreo" class="colocaDatosVisGrande">Domicilio,
							Calle o Plaza y N&ordm;: <span><bean:write
									name="OCAPNuevaSolicitudForm" property="DDomicilio" /></span>
						</label> <br />
						<br /> <label for="idVProvincia" class="colocaDatosVis">Provincia:
							<span> <logic:notEmpty name="OCAPNuevaSolicitudForm"
									property="DProvincia">
									<bean:write name="OCAPNuevaSolicitudForm" property="DProvincia" />
								</logic:notEmpty>
						</span>
						</label> <label for="idVTelefono" class="colocaDatosVis">C&oacute;digo
							Postal: <span><bean:write name="OCAPNuevaSolicitudForm"
									property="CPostalUsu" /></span>
						</label> <br />
						<br /> <label for="idVLocalidades" class="colocaDatosVis">Localidad:
							<span> <logic:notEmpty name="OCAPNuevaSolicitudForm"
									property="DLocalidad">
									<bean:write name="OCAPNuevaSolicitudForm" property="DLocalidad" />
								</logic:notEmpty>
						</span>
						</label>
					</div>

				</fieldset>

				<h3 class="subTituloContenidoMC">2.- DATOS PROFESIONALES:</h3>
				<fieldset>
					<legend class="tituloLegend"> 2.1.- Datos condici&oacute;n personal estatutario o funcionario sanitario de carrera </legend>

					<div class="espaciador"></div>
					<span class="informacionSituacion textoCursiva textoNegrita">
						<bean:message key="solicitudes.infoComisionServicio" />
					</span>


					<html:hidden property="CGrado_id" />
					<html:hidden property="DGrado_des" />
					<html:hidden property="CModAnteriorId" />
					<html:hidden property="CProcedimientoId" />
					<html:hidden property="DProcedimiento" />
					<html:hidden property="CJuridicoId" />
					<html:hidden property="CJuridicoCombo" />
					<html:hidden property="DRegimenJuridicoOtros" />
					<html:hidden property="CSitAdministrativaId" />
					<html:hidden property="DSitAdministrativaOtros" />
					<html:hidden property="CProfesional_id" />
					<html:hidden property="CProfesionalFijo_id" />
					<html:hidden property="DProfesional_nombre" />
					<html:hidden property="CEspec_id" />
					<html:hidden property="DEspec_nombre" />
					<html:hidden property="CEspecFijo_id" />
					<html:hidden property="CProvinciaCarrera_id" />
					<html:hidden property="DProvinciaCarrera" />
					<html:hidden property="CGerencia_id" />
					<html:hidden property="DGerencia_nombre" />
					<html:hidden property="CCentrotrabajo_id" />
					<html:hidden property="DCentrotrabajo_nombre" />
					<html:hidden property="NAniosantiguedad" />
					<html:hidden property="NMesesantiguedad" />
					<html:hidden property="NDiasantiguedad" />
					<html:hidden property="DServicio" />
					<html:hidden property="DPuesto" />
					<html:hidden property="CEstadoId" />
					<html:hidden property="DModAnterior" />
					<html:hidden property="CSitAdmonActualId" />
					<html:hidden property="AOtraSitAdmonActual" />
					<html:hidden property="CProvCarreraActualId" />
					<html:hidden property="DProvCarreraActual" />
					<html:hidden property="CGerenciaActualId" />
					<html:hidden property="DGerenciaActual" />
					<html:hidden property="CCentroTrabActualId" />
					<html:hidden property="DCentroTrabActual" />
					<html:hidden property="DConvocatoria_nombre" />
					<html:hidden property="CConvocatoriaId" />

					<div class="cuadroFieldset colocaDatosVisGrande">

						<label class="labelReg" for="idVConvocatoria">
							Convocatoria: <span><bean:write
									name="OCAPNuevaSolicitudForm" property="DConvocatoria_nombre" /></span>
						</label> <br />
						<br />
						<!-- REGIMEN JURIDICO DETALLE -->
						<label for="idVCategoria" class="colocaDatosVisGrande">R&eacute;gimen
							Jur&iacute;dico: <logic:equal name="OCAPNuevaSolicitudForm"
								property="CJuridicoId"
								value="<%=Constantes.C_JURIDICO_ESTATUTARIO_COD%>">
								<span><%=Constantes.C_JURIDICO_ESTATUTARIO%></span>
							</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm"
								property="CJuridicoId"
								value="<%=Constantes.C_JURIDICO_FUNCIONARIO_COD%>">
								<span><%=Constantes.C_JURIDICO_FUNCIONARIO%></span>
							</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm"
								property="CJuridicoId"
								value="<%=Constantes.C_JURIDICO_OTROS_COD%>">
								<span><%=Constantes.C_JURIDICO_OTROS%></span>
							</logic:equal> <span> <logic:equal name="OCAPNuevaSolicitudForm"
									property="CJuridicoId"
									value="<%=Constantes.C_JURIDICO_OTROS_COD%>">
									<span><bean:write name="OCAPNuevaSolicitudForm"
											property="DRegimenJuridicoOtros" /></span>
								</logic:equal>
								<logic:equal name="OCAPNuevaSolicitudForm"
												property="CJuridicoId"
												value="<%=Constantes.C_JURIDICO_INTFUNC_COD%>">
												<span><%=Constantes.C_JURIDICO_INTFUNC%></span>
											</logic:equal>
											<logic:equal name="OCAPNuevaSolicitudForm"
												property="CJuridicoId"
												value="<%=Constantes.C_JURIDICO_INTEST_COD%>">
												<span><%=Constantes.C_JURIDICO_INTEST%></span>
											</logic:equal>
						</span>
						</label>

						<logic:equal name="OCAPNuevaSolicitudForm"
							property="CJuridicoCombo"
							value="<%=Constantes.C_JURIDICO_SANITARIO_FIJO_COD%>">
							<br />
							<br />
							<label for="idVCategoria" class="colocaDatosVisGrande">Tipo:
								<span><%=Constantes.C_JURIDICO_SANITARIO_FIJO%></span>
							</label>
						</logic:equal>
						<logic:equal name="OCAPNuevaSolicitudForm"
							property="CJuridicoCombo"
							value="<%=Constantes.C_JURIDICO_GS_FIJO_COD%>">
							<br />
							<br />
							<label for="idVCategoria" class="colocaDatosVisGrande">Tipo:
								<span><%=Constantes.C_JURIDICO_GS_FIJO%></span>
							</label>
						</logic:equal>
						<br />
						<br />

						<!-- CATEGORIA DETALLE -->
						<label for="idVCategoria" class="colocaDatosVisGrande">Categor&iacute;a:
							<span> <logic:notEmpty name="OCAPNuevaSolicitudForm"
									property="DProfesional_nombre">
									<bean:write name="OCAPNuevaSolicitudForm"
										property="DProfesional_nombre" />
								</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
									property="DProfesional_nombre">-</logic:empty>
						</span>
						</label><br />
						<br />

						<!-- ESPECIALIDAD DETALLE -->
						<label for="idVEspecialidad" class="colocaDatosVisGrande">Especialidad:
							<span> <logic:notEmpty name="OCAPNuevaSolicitudForm"
									property="DEspec_nombre">
									<bean:write name="OCAPNuevaSolicitudForm"
										property="DEspec_nombre" />
								</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
									property="DEspec_nombre">-</logic:empty>
						</span>
						</label> <br />
						<br />

						<!-- SERVICIO CARRERA DETALLE -->
						<label for="idVEspecialidad" class="colocaDatosVisGrande">Servicio:
							<span> <logic:notEmpty name="OCAPNuevaSolicitudForm"
									property="DServicio">
									<bean:write name="OCAPNuevaSolicitudForm" property="DServicio" />
								</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm" property="DServicio">-</logic:empty>
						</span>
						</label> <br />
						<br />

						<!-- AREA / UNIDAD / PUESTO -->
						<label for="idVEspecialidad" class="colocaDatosVisGrande">&Aacute;rea
							/ Unidad / Puesto: <span> <logic:notEmpty
									name="OCAPNuevaSolicitudForm" property="DPuesto">
									<bean:write name="OCAPNuevaSolicitudForm" property="DPuesto" />
								</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm" property="DPuesto">-</logic:empty>
						</span>
						</label> <br />
						<br />
					</div>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> 2.2.- Situaci&oacute;n a 31
						de diciembre de <bean:write name="OCAPNuevaSolicitudForm"
										property="anioConvocatoria" /> (Seg&uacute;n Convocatoria) </legend>


					<div class="cuadroFieldset colocaDatosVisGrande">
						<!-- GRADO DETALLE -->
						<label for="idVGrado">Grado: <span class="textoDatos"><bean:write
									name="OCAPNuevaSolicitudForm" property="DGrado_des" /></span>
						</label> <br />
						<br />

						<!-- PROCEDIMIENTO DE EVALUACION DETALLE -->
						<label for="idVCategoria" class="colocaDatosVisGrande">Procedimiento
							de evaluaci&oacute;n por el que opta: <span> <logic:notEmpty
									name="OCAPNuevaSolicitudForm" property="DProcedimiento">
									<bean:write name="OCAPNuevaSolicitudForm"
										property="DProcedimiento" />
								</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
									property="DProcedimiento">-</logic:empty>
						</span>
						</label> <br />
						<br />

						<!-- SITUACION ADMINISTRATIVA DETALLE -->
						<label for="idVCategoria" class="colocaDatosVisGrande">Situaci&oacute;n
							Administrativa: <logic:equal name="OCAPNuevaSolicitudForm"
								property="CSitAdministrativaId"
								value="<%=Constantes.C_SIT_ADM_AUX_ACTIVO_COD%>">
								<span><%=Constantes.C_SIT_ADM_AUX_ACTIVO%></span>
							</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm"
								property="CSitAdministrativaId"
								value="<%=Constantes.C_SIT_ADM_AUX_SE_COD%>">
								<span><%=Constantes.C_SIT_ADM_AUX_SE%></span>
							</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm"
								property="CSitAdministrativaId"
								value="<%=Constantes.C_SIT_ADM_AUX_ESSP_COD%>">
								<span><%=Constantes.C_SIT_ADM_AUX_ESSP%></span>
							</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm"
								property="CSitAdministrativaId"
								value="<%=Constantes.C_SIT_ADM_AUX_EV_COD%>">
								<span><%=Constantes.C_SIT_ADM_AUX_EV%></span>
							</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm"
								property="CSitAdministrativaId"
								value="<%=Constantes.C_SIT_ADM_AUX_ECF_COD%>">
								<span><%=Constantes.C_SIT_ADM_AUX_ECF%></span>
							</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm"
								property="CSitAdministrativaId"
								value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>">
								<span><%=Constantes.C_SIT_ADM_AUX_OTROS%></span>
							</logic:equal> <span> <logic:equal name="OCAPNuevaSolicitudForm"
									property="CSitAdministrativaId"
									value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>">
									<span><bean:write name="OCAPNuevaSolicitudForm"
											property="DSitAdministrativaOtros" /></span>
								</logic:equal>
						</span>
						</label> <br />
						<br />

						<!-- PROVINCIA CARRERA DETALLE -->
						<label for="idVProvincia" class="colocaDatosVis">Provincia:
							* <span> <logic:notEmpty name="OCAPNuevaSolicitudForm"
									property="DProvinciaCarrera">
									<bean:write name="OCAPNuevaSolicitudForm"
										property="DProvinciaCarrera" />
								</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
									property="DProvinciaCarrera">-</logic:empty>
						</span>
						</label> <br />
						<br />

						<!-- GERENCIA CARRERA DETALLE-->
						<label for="idVGerencia" class="colocaDatosVisGrande">Gerencia:
							* <span> <logic:notEmpty name="OCAPNuevaSolicitudForm"
									property="DGerencia_nombre">
									<bean:write name="OCAPNuevaSolicitudForm"
										property="DGerencia_nombre" />
								</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
									property="DGerencia_nombre">-</logic:empty>
						</span>
						</label> <br />
						<br />

						<!-- CENTRO DE TRABAJO DETALLE-->
						<label for="idVCentroTrabajo" class="colocaDatosVisGrande">Centro de Trabajo: * <span> <logic:notEmpty
									name="OCAPNuevaSolicitudForm" property="DCentrotrabajo_nombre">
									<bean:write name="OCAPNuevaSolicitudForm"
										property="DCentrotrabajo_nombre" />
								</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
									property="DCentrotrabajo_nombre">-</logic:empty>
						</span>
						</label> <br />
						<br />
					</div>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> 2.3.- Situaci&oacute;n a fecha de publicaci&oacute;n de convocatoria </legend>

					<div class="cuadroFieldset colocaDatosVisGrande">
						<!-- SITUACION ADMINISTRATIVA DETALLE -->
						<label for="idVCategoria" class="colocaDatosVisGrande">Situaci&oacute;n
							Administrativa: <logic:equal name="OCAPNuevaSolicitudForm"
								property="CSitAdmonActualId"
								value="<%=Constantes.C_SIT_ADM_AUX_ACTIVO_COD%>">
								<span><%=Constantes.C_SIT_ADM_AUX_ACTIVO%></span>
							</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm"
								property="CSitAdmonActualId"
								value="<%=Constantes.C_SIT_ADM_AUX_SE_COD%>">
								<span><%=Constantes.C_SIT_ADM_AUX_SE%></span>
							</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm"
								property="CSitAdmonActualId"
								value="<%=Constantes.C_SIT_ADM_AUX_ESSP_COD%>">
								<span><%=Constantes.C_SIT_ADM_AUX_ESSP%></span>
							</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm"
								property="CSitAdmonActualId"
								value="<%=Constantes.C_SIT_ADM_AUX_EV_COD%>">
								<span><%=Constantes.C_SIT_ADM_AUX_EV%></span>
							</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm"
								property="CSitAdmonActualId"
								value="<%=Constantes.C_SIT_ADM_AUX_ECF_COD%>">
								<span><%=Constantes.C_SIT_ADM_AUX_ECF%></span>
							</logic:equal> <logic:equal name="OCAPNuevaSolicitudForm"
								property="CSitAdmonActualId"
								value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>">
								<span><%=Constantes.C_SIT_ADM_AUX_OTROS%></span>
							</logic:equal> <span> <logic:equal name="OCAPNuevaSolicitudForm"
									property="CSitAdmonActualId"
									value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>">
									<span><bean:write name="OCAPNuevaSolicitudForm"
											property="AOtraSitAdmonActual" /></span>
								</logic:equal>
						</span>
						</label> <br />
						<br />

						<!-- PROVINCIA CARRERA DETALLE -->
						<label for="idVProvincia" class="colocaDatosVis">Provincia:
							<span> <logic:notEmpty name="OCAPNuevaSolicitudForm"
									property="DProvCarreraActual">
									<bean:write name="OCAPNuevaSolicitudForm"
										property="DProvCarreraActual" />
								</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
									property="DProvCarreraActual">-</logic:empty>
						</span>
						</label> <br />
						<br />

						<!-- GERENCIA CARRERA DETALLE-->
						<label for="idVGerencia" class="colocaDatosVisGrande">Gerencia:
							<span> <logic:notEmpty name="OCAPNuevaSolicitudForm"
									property="DGerenciaActual">
									<bean:write name="OCAPNuevaSolicitudForm"
										property="DGerenciaActual" />
								</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
									property="DGerenciaActual">-</logic:empty>
						</span>
						</label> <br />
						<br />

						<!-- CENTRO DE TRABAJO DETALLE-->
						<label for="idVCentroTrabajo" class="colocaDatosVisGrande">Centro
							de Trabajo: <span> <logic:notEmpty
									name="OCAPNuevaSolicitudForm" property="DCentroTrabActual">
									<bean:write name="OCAPNuevaSolicitudForm"
										property="DCentroTrabActual" />
								</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
									property="DCentroTrabActual">-</logic:empty>
						</span>
						</label> <br />
						<br />
					</div>

				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> 2.4.- A&ntilde;os de ejercicio profesional como personal en el Sistema Nacional de Salud hasta el 31/12/<bean:write name="OCAPNuevaSolicitudForm"
										property="anioConvocatoria" /> (Seg&uacute;n Convocatoria) </legend>
					<div class="cuadroFieldset">
						<!-- AÑOS DE EJECICIO DETALLE -->
						<div>
							<span class="colocaNumAlta">A&ntilde;os de ejercicio en la
								categor&iacute;a profesional por la que se accede:</span> <label
								for="idVAnt">N&ordm; A&ntilde;os: </label><span
								class="textoDatos"><bean:write
									name="OCAPNuevaSolicitudForm" property="NAniosantiguedad" /></span> <label>N&ordm;
								Meses: </label><span class="textoDatos"><bean:write
									name="OCAPNuevaSolicitudForm" property="NMesesantiguedad" /></span> <label>N&ordm;
								D&iacute;as: </label><span class="textoDatos"><bean:write
									name="OCAPNuevaSolicitudForm" property="NDiasantiguedad" /></span>
						</div>
						<br />
						<div class="lineaBajaM"></div>
						<br /> <label for="idVServicios"> Tiene servicios prestados en otro centro de trabajo distinto al actual: </label><br /> <select
							size="5" id="jspCadenaCentro" name="jspCadenaCentro"
							class="textoTituloGrisAlta2" readonly="true"></select>
						<html:hidden property="resumenCentros" />
						<!-- script type="text/javascript">inicializarCentros();</script-->
					</div>

				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> 2.5.- Grado de carrera
						profesional reconocido </legend>


					<div class="cuadroFieldset colocaDatosVisGrande">
						<!-- GRADO QUE POSEE (MODALIDAD ANTERIOR) -->
						<label for="idVCategoria" class="colocaDatosVisGrande">Grado
							que posee: <span> <logic:notEmpty
									name="OCAPNuevaSolicitudForm" property="DModAnterior">
									<bean:write name="OCAPNuevaSolicitudForm"
										property="DModAnterior" />
								</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
									property="DModAnterior">-</logic:empty>
						</span>
						</label> <br />
						<br />
					</div>

				</fieldset>

				<fieldset>
				<table class="cuadroFieldset" style="vertical-align:middle;width:100%; border-spacing:5px;border-collapse:separate;padding-left:15px !important;padding:20px;">
					<tr>
						<td>
							<span class="letraMarron"> Fecha de inicio de
								autoevaluaci&oacute;n de m&eacute;ritos: (dd/mm/aaaa) </span>
						</td>
						<td>
							<html:text property="FInicio_mc" maxlength="10" style="vertical-align:super;width: 75%;"
								styleClass="recuadroM textoNormal margenF8" />
							<span> <a
								href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FInicio_mc", document.forms[0].FInicio_mc.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</span>
						</td>
					</tr>
					<tr>
						<td>
							<span class="letraMarron"> Fecha de fin de
								autoevaluaci&oacute;n de m&eacute;ritos: (dd/mm/aaaa) </span>
						</td>
						<td>
							<html:text property="FFin_mc" maxlength="10"  style="vertical-align:super;width: 75%;"
								styleClass="recuadroM textoNormal margenF1" />
							<span> <a
								href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FFin_mc", document.forms[0].FFin_mc.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</span>
						</td>
					</tr>
					<tr style="height:17.6px;">
						<td>
							<span class="letraMarron"> Méritos bloqueados al usuario </span>
						</td>
						<td>
							<html:checkbox property="meritosBloqueados" value="S" />
						</td>
					</tr>
					<tr style="height:10px;">
						<td>
						</td>
						<td>
						</td>
					</tr>
					<tr>
						<td>
							<span class="letraMarron"> Fecha de inicio de
								valoraci&oacute;n de MC: (dd/mm/aaaa) </span>
						</td>
						<td>
							<html:text property="FInicio_eval_mc" maxlength="10" style="vertical-align:super;width: 75%;"
								styleClass="recuadroM textoNormal margenF2" />
							<span> <a
								href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FInicio_eval_mc", document.forms[0].FInicio_eval_mc.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</span>
						</td>
					</tr>
					<tr>
						<td>
							<span class="letraMarron"> Fecha de inicio de valoraci&oacute;n CC: (dd/mm/aaaa) </span>
						</td>
						<td>
							<html:text property="FIniciocc" maxlength="10" style="vertical-align:super;width: 75%;"
								styleClass="recuadroM textoNormal margenF20" />
							<span> <a
								href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FIniciocc", document.forms[0].FIniciocc.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</span>
						</td>
					</tr>
					
					<tr>
						<td>
							<span class="letraMarron"> Fecha de aceptaci&oacute;n de
								CEIS: (dd/mm/aaaa) </span>
						</td>
						<td>
							<html:text property="FAceptacionceis" maxlength="10" style="vertical-align:super;width: 75%;"
								styleClass="recuadroM textoNormal margenF5" />
							<span> <a
								href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FAceptacionceis", document.forms[0].FAceptacionceis.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</span>
						</td>
					</tr>
					
					<tr>
						<td>
							<span class="letraMarron"> Fecha de aceptaci&oacute;n de
								CC: (dd/mm/aaaa) </span>
						</td>
						<td>
							<html:text property="FAceptacionCC" maxlength="10" style="vertical-align:super;width: 75%;"
								styleClass="recuadroM textoNormal margenF5" />
							<span> <a
								href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FAceptacionCC", document.forms[0].FAceptacionCC.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</span>
						</td>
					</tr>
					
					<tr>
						<td>
							<span class="letraMarron"> Fecha de negaci&oacute;n de MC:
								(dd/mm/aaaa) </span>
						</td>
						<td>
							<html:text property="FNegacion_mc" maxlength="10" style="vertical-align:super;width: 75%;"
								styleClass="recuadroM textoNormal margenF4" />
							<span> <a
								href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FNegacion_mc", document.forms[0].FNegacion_mc.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</span>
						</td>
					</tr>
					
					<tr>
						<td>
							<span class="letraMarron"> Fecha de presentaci&oacute;n de inconformidad por negaci&oacute;n de MC:
								(dd/mm/aaaa) </span>
						</td>
						<td>
							<html:text property="FInconf_mc" maxlength="10" style="vertical-align:super;width: 75%;"
								styleClass="recuadroM textoNormal margenF4" />
							<span> <a
								href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FInconf_mc", document.forms[0].FInconf_mc.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</span>
						</td>
					</tr>
					
					<tr>
						<td>
							<span class="letraMarron"> Fecha de respuesta de inconformidad de MC:
								(dd/mm/aaaa) </span>
						</td>
						<td>
							<html:text property="FRespuesta_inconf_mc" maxlength="10" style="vertical-align:super;width: 75%;"
								styleClass="recuadroM textoNormal margenF4" />
							<span> <a
								href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FRespuesta_inconf_mc", document.forms[0].FRespuesta_inconf_mc.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</span>
						</td>
					</tr>
					
					<tr style="height:10px;">
						<td>
						</td>
						<td>
						</td>
					</tr>
					<tr>
						<td>
							<span class="letraMarron"> Fecha de inicio asistencial:
								(dd/mm/aaaa) </span>
						</td>
						<td>
							<html:text property="FInicio_ca" maxlength="10" style="vertical-align:super;width: 75%;"
								styleClass="recuadroM textoNormal margenF9" />
							<span> <a
								href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FInicio_ca", document.forms[0].FInicio_ca.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</span>
						</td>
					</tr>
					<tr>
						<td>
							<span class="letraMarron"> Fecha de fin asistencial:
								(dd/mm/aaaa) </span>
						</td>
						<td>
							<html:text property="FFin_ca" maxlength="10" style="vertical-align:super;width: 75%;"
								styleClass="recuadroM textoNormal margenF6" />
							<span> <a
								href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FFin_ca", document.forms[0].FFin_ca.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</span>
						</td>
					</tr>
					
					<tr>
						<td>
							<span class="letraMarron"> Fecha de informe de
								evaluaci&oacute;n: (dd/mm/aaaa) </span>
						</td>
						<td>
							<html:text property="FInformeEval" maxlength="10" style="vertical-align:super;width: 75%;"
								styleClass="recuadroM textoNormal margenF7" />
							<span> <a
								href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FInformeEval", document.forms[0].FInformeEval.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</span>
						</td>
					</tr>
					
					<tr>
						<td>
							<span class="letraMarron"> Fecha renuncia: (dd/mm/aaaa) </span>
						</td>
						<td>
							<html:text property="FRenuncia" maxlength="10"  style="vertical-align:super;width: 75%;"
								styleClass="recuadroM textoNormal margenF1" />
							<span> <a
								href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FRenuncia", document.forms[0].FRenuncia.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</span>
						</td>
					</tr>
					
					<tr>
						<td>
							<span class="letraMarron"> Estado: </span>
						</td>
						<td>
							<html:select property="CEstadoFiltro" style="width:90%;" 
								styleClass="cbCuadros colocaEstadoCSV margenF10" >
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ESTADOS%>"
									property="cEstadoId" labelProperty="DNombre" />
							</html:select>
						</td>
					</tr>
					<tr>
						<td>
							<span class="letraMarron"> Itinerario: </span>
						</td>
						<td>
							<html:text property="cItineraio_id" maxlength="6"  style="vertical-align:super;width: 75%;"
								styleClass="recuadroM textoNormal margenF1" />
						</td>
					</tr>
					<tr>
						<td>
							<span class="letraMarron"> Eliminar evaluaci&oacute;n: </span>
						</td>
						<td>
							<html:checkbox property="eliminarEvaluacion" name="OCAPNuevaSolicitudForm" onclick="javascript:desactivarEstado();"></html:checkbox>
						</td>
					</tr>
					
					</table>
				</fieldset>

				<div class="espaciadorPeq"></div>

				<div class="limpiadora"></div>
				<div class="botonesPagina">

					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviar('PaginaInicio.do');"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" /> <span>
									Cancelar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:aceptarSolicitud();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Aceptar" /> <span> Aceptar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:aceptarDesbloquearSolicitud();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Desbloquar" /> <span> Guardar y desbloquear m&eacute;ritos asistenciales </span>
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

</body>
