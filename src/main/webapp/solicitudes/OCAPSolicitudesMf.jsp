<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"
	charset="windows-1252"></script>
<script language="JavaScript" type="text/javascript"
	src="<html:rewrite page='/javascript/combos1.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/solicitudes/validacionesSolicitudes.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/fechas.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/ventanas.js'/>"
	charset="windows-1252"></script>

<script language="JavaScript" type="text/javascript">

function volverAtras() {
   enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
}

function cargarComboLocalidades(){
   document.forms[0].resumenCentros.value=valorCombo(document.getElementById('jspCadenaCentro'));
   if(document.forms[0].DLocalidadUsu != undefined)
      document.forms[0].DLocalidadUsu.value = '';
   enviar('OCAPSolicitudes.do?accion=cargarComboLocalidades&jspVuelta=modif&opcion=<%=request.getParameter("opcion")%>');
}
   
function cargarComboCategorias(actual){
   document.forms[0].resumenCentros.value=valorCombo(document.getElementById('jspCadenaCentro'));
      if (actual == '<%=Constantes.SI%>') {
         if(document.forms[0].CProfesionalActual_id != undefined)
            document.forms[0].CProfesionalActual_id.value = '';
         if(document.forms[0].CEspecActual_id != undefined)
            document.forms[0].CEspecActual_id.value = '';
      } else {
         if(document.forms[0].CProfesional_id != undefined)
            document.forms[0].CProfesional_id.value = '';
         if(document.forms[0].CEspec_id != undefined)
            document.forms[0].CEspec_id.value = '';
      }
   //enviar('OCAPSolicitudes.do?accion=cargarComboCategorias&CEstatutarioId='+ document.forms[0].CEstatutarioId.value +'&jspVuelta=modif&opcion=<%=request.getParameter("opcion")%>');
   enviar('OCAPSolicitudes.do?accion=cargarComboCategorias&jspVuelta=modif&opcion=<%=request.getParameter("opcion")%>&actual='+actual);
}

function cargarCombosEspecialidadesPuestos(actual){
   document.forms[0].resumenCentros.value=valorCombo(document.getElementById('jspCadenaCentro'));
   //enviar('OCAPSolicitudes.do?accion=cargarCombosEspecialidadesPuestos&CProfesional_id='+ document.forms[0].CProfesional_id.value +'&jspVuelta=modif&opcion=<%=request.getParameter("opcion")%>');
   if (actual == '<%=Constantes.SI%>') {
      if(document.forms[0].CEspecActual_id != undefined)
         document.forms[0].CEspecActual_id.value = '';
   } else {
      if(document.forms[0].CEspec_id != undefined)
         document.forms[0].CEspec_id.value = '';
   }
   enviar('OCAPSolicitudes.do?accion=cargarCombosEspecialidadesPuestos&jspVuelta=modif&opcion=<%=request.getParameter("opcion")%>&actual='+actual);
}

function cargarComboGerencias(){
   if(document.forms[0].CGerencia_id != undefined)
      document.forms[0].CGerencia_id.value='';
   if(document.forms[0].CCentrotrabajo_id != undefined)
      document.forms[0].CCentrotrabajo_id.value='';
   enviar('OCAPSolicitudes.do?accion=cargarComboGerencias&CProvincia_id='+ document.forms[0].CProvincia_id.value +'&cTipogerencia_id='+ document.forms[0].CTipogerencia_id.value+'&jspVuelta=modif&opcion=<%=request.getParameter("opcion")%>');

}

function cargarComboCentroTrabajo(){
   document.forms[0].resumenCentros.value=valorCombo(document.getElementById('jspCadenaCentro'));
   if(document.forms[0].CCentrotrabajo_id != undefined)
      document.forms[0].CCentrotrabajo_id.value='';
   enviar('OCAPSolicitudes.do?accion=cargarComboCentroTrabajo&CGerencia_id='+ document.forms[0].CGerencia_id.value+'&jspVuelta=modif&opcion=<%=request.getParameter("opcion")%>');
}

function cargarComboCiudad(){
   document.forms[0].resumenCentros.value=valorCombo(document.getElementById('jspCadenaCentro'));
   if(document.forms[0].ACiudad != undefined)
      document.forms[0].ACiudad.value = '';
   enviar('OCAPSolicitudes.do?accion=cargarComboCiudad&CCentroTrabajo_id='+ document.forms[0].CCentrotrabajo_id.value+'&jspVuelta=modif&opcion=<%=request.getParameter("opcion")%>');
}
   
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
function obtenerAniosGrado(){
   var longListaGrados = listaGradosJS.length;
   for (i=0; i < longListaGrados; i++) {
      if (document.forms[0].CGrado_id.value == listaGradosJS[i][0]) {
         document.forms[0].NAniosejercicio.value = listaGradosJS[i][1];
         document.forms[0].CConvocatoriaId.value = listaGradosJS[i][2];
         document.forms[0].FFinRegistro.value = listaGradosJS[i][3];
      }
   }
//   enviar('OCAPSolicitudes.do?accion=obtenerAniosGrado');
}


function aceptarSolicitud(){
   if(validarFormulario(document.forms[0],'')) {
      var aniosAnt = obtenerCifra(document.forms[0].NAniosantiguedad.value);
      var aniosEjer = obtenerCifra(document.forms[0].NAniosejercicio.value);
      document.forms[0].resumenCentros.value=valorCombo(document.getElementById('jspCadenaCentro'));
      if(parseInt(aniosAnt) < parseInt(aniosEjer)){
         // No cumple la condicion de años minimos de antiguedad
         if(confirm(" El n\u00famero de a\u00f1os elegido es inferior al m\u00ednimo \u00BFest\u00e1 seguro de querer registrar la solicitud?")){
            enviar('OCAPSolicitudes.do?accion=modificar&opcion=<%=request.getParameter("opcion")%>');
         }
      }else{
            enviar('OCAPSolicitudes.do?accion=modificar&opcion=<%=request.getParameter("opcion")%>');  
      }
   }
}

function ocultar(){
   document.getElementById('informacionImprimible').style.display='';
   document.getElementById('informacionImprimible').style.visibility='visible';   
}

function mostrar(){
   document.getElementById('informacionImprimible').style.display='none';
   document.getElementById('informacionImprimible').style.visibility='hidden';
}

function mostrarDonde() {
   if (document.forms[0].CSitAdministrativaAuxId[1].checked) {
      document.getElementById('serviciosDonde').style.display='';
      document.getElementById('serviciosDonde').style.visibility='visible';
   } else {
      document.getElementById('serviciosDonde').style.display='none';
      document.getElementById('serviciosDonde').style.visibility='hidden';
   }
}

function anhCentros() {
   formu = document.forms[0];
   comboCentros = document.getElementById('jspCadenaCentro');
   if (formu.DNombreCentro.value == ''){
      alert('Debe introducir el nombre del Centro de Trabajo.');
   }else if(formu.AProvinciaCentro.value == ''){
      alert('Debe introducir la Provincia a la cual pertenece el Centro de Trabajo.');
   }else if(formu.ACategoria.value == ''){
      alert('Debe introducir una Categor\u00EDa/Especialidad/Cuerpo.');
   }else if(formu.ASituacion.value == ''){
      alert('Debe elegir una Situaci\u00F3n.');
   }else if(formu.AVinculo.value == ''){
      alert('Debe elegir un V\u00EDnculo.');
   }else if(formu.FInicio.value == ''){
      alert('Debe introducir una Fecha de Inicio.');
   }else if(formu.FInicio.value != '' && !esFechaCorrecta(formu.FInicio.value)){
      alert('El campo Fecha de Inicio no tiene el formato correcto.');
   }else if(formu.FFin.value == ''){
      alert('Debe introducir una Fecha de Fin.');
   }else if(formu.FFin.value != '' && !esFechaCorrecta(formu.FFin.value)){
      alert('El campo Fecha de Fin no tiene el formato correcto.');
   }else if(esFecha1MayorQueFecha2(formu.FInicio.value,'00:00:00', formu.FFin.value,'00:00:00')){
      alert('La Fecha de Inicio no puede ser posterior a la Fecha de Fin.');
   } else{
      var valor = formu.DNombreCentro.value +'$'+ formu.AProvinciaCentro.value +'$'+ 
                  formu.ACategoria.value +'$'+ formu.ASituacion.value +'$'+ formu.AVinculo.value +'$'+ 
                  formu.FInicio.value +'$'+ formu.FFin.value;
      var texto = formu.DNombreCentro.value;
      /*
      var texto = formu.DNombreCentro.value +'---'+ formu.AProvinciaCentro.value +'---'+ 
                  formu.ACategoria.value +'---'+ formu.ASituacion.value +'---'+ formu.AVinculo.value +'---'+
                  formu.FInicio.value + '---' +  formu.FFin.value;
      */
//      var aux = valorCombo(document.getElementById('jspCadenaCentro'));
//      var aux = valorCombo(formu.jspCadenaCentro);

//      if ( aux.indexOf('$' + formu.FInicio[index].value + '$') == -1 ) {
         //comboCentros.nueva_opcion(texto,valor);
//         var campo = formu.jspCadenaCentro;
         nueva_opcion(comboCentros,texto,valor);
         limpiarCamposCentros();
//      } else  {
//         alert('No se puede a\u00f1adir el Certificado porque ya existe');
//      }
   }
} //Fin anhCentros

function limpiarCamposCentros() {
   formu.DNombreCentro.value = '';
   formu.AProvinciaCentro.value = '';
   formu.ACategoria.value = '';
   formu.ASituacion.value = '';
   formu.AVinculo.value = '';
   formu.FInicio.value = '';
   formu.FFin.value = '';
}

function modCentros() {
	formu = document.forms[0];

   comboCentros=objeto_combo('document.forms[0].jspCadenaCentro');

//	if (comprueba_seleccion(formu.jspCadenaCentro)) {
   if (comprueba_seleccion(document.getElementById('jspCadenaCentro'))) {
    indice = document.getElementById('jspCadenaCentro').selectedIndex;
    var valor = comboCentros.options[indice].value;
    var valor_aux = valor;     // variable para operar
    var cadenaTokenizado = valor_aux.split('<%=Constantes.SEPARADOR_2%>');
    if(cadenaTokenizado.length==6){
        formu.DNombreCentro.value = cadenaTokenizado[0];
    	   formu.AProvinciaCentro.value =  cadenaTokenizado[1];
    	   formu.ACategoria.value =  cadenaTokenizado[2];
    	   formu.AVinculo.value =  cadenaTokenizado[3];
    	   formu.FInicio.value =  cadenaTokenizado[4];
    	   formu.FFin.value =  cadenaTokenizado[5];
     } else if( cadenaTokenizado.length>6){
    	   formu.DNombreCentro.value = cadenaTokenizado[0];
    	   formu.AProvinciaCentro.value =  cadenaTokenizado[1];
    	   formu.ACategoria.value =  cadenaTokenizado[2];
    	   formu.AVinculo.value =  cadenaTokenizado[4];
    	   formu.FInicio.value =  cadenaTokenizado[5];
    	   formu.FFin.value =  cadenaTokenizado[6];
     }

	 combos_borrar(document.getElementById('jspCadenaCentro'));
	}
}

function inicializarCentros () {
   var resumenCentros = document.forms[0].resumenCentros.value;
   if (resumenCentros != '') {
      var cadenaCentro = resumenCentros.split('#');
      var comboCentros = document.getElementById('jspCadenaCentro');
      for (var i=0; i < cadenaCentro.length; i++) {
         var aux =  cadenaCentro[i];
         var aux2 = cadenaCentro[i]; 
         var datosCentro = aux.split('<%=Constantes.SEPARADOR_2%>');
         var valor = aux2;
         var texto = datosCentro[0];
         nueva_opcion(comboCentros,texto,valor);
      }
   }
}
</script>

<div class="ocultarCampo">
	<div class="contenido">
		<div class="contenidoDCP1A">

			<html:form action="/OCAPSolicitudes.do">
				<html:hidden property="NAniosejercicio" />
				<html:hidden property="CExp_id" />

				<h2 class="tituloContenido">REGISTRO DE LA SOLICITUD DE ACCESO
					A GRADO DE CARRERA PROFESIONAL</h2>
				<h3 class="subTituloContenido">DCP1A</h3>
				<div class="lineaBajaM"></div>
				<div class="espaciador"></div>

				<fieldset>
					<legend class="tituloLegend"> Datos Personales </legend>
					<div class="cuadroFieldset">
						<label for="idVApell1"> Apellidos: <html:text
								property="DApellido1"
								styleClass="recuadroM colocaApellidosMf textoNormal"
								maxlength="61" />
						</label> <label for="idVNombre"> Nombre: <html:text
								property="DNombre"
								styleClass="recuadroM colocaNombreMf textoNormal" maxlength="30" />
						</label> <br />
						<br /> <label for="idVDNI"> NIF/NIE: <html:text
								name="OCAPSolicitudesForm" property="CDniReal"
								styleClass="recuadroM colocaDNIDes textoNormal" maxlength="10"
								readonly="true" />
						</label>
						<html:hidden name="OCAPSolicitudesForm" property="CDni" />
						<label for="idVCorreo">Sexo: * <html:select
								styleClass="cbCuadros colocaSexoMf" name="OCAPSolicitudesForm"
								property="BSexo">
								<html:option value="">Seleccione...</html:option>
								<html:option value="<%=Constantes.SEXO_HOMBRE_VALUE%>"><%=Constantes.SEXO_HOMBRE%></html:option>
								<html:option value="<%=Constantes.SEXO_MUJER_VALUE%>"><%=Constantes.SEXO_MUJER%></html:option>
							</html:select>
						</label> <br />
						<br /> <label for="idVTelefono">Tel&eacute;fono 1: <html:text
								property="NTelefono1"
								styleClass="recuadroM colocaTelefono1Mf textoNormal"
								maxlength="9" />
						</label> <label for="idVTelefono">Tel&eacute;fono 2: <html:text
								property="NTelefono2"
								styleClass="recuadroM colocaTelefono2Mf textoNormal"
								maxlength="9" />
						</label> <br />
						<br /> <label for="idVCorreo">Correo Electr&oacute;nico:
							<html:text property="DCorreoelectronico"
								styleClass="recuadroM colocaCorreoMf textoNormal" maxlength="50" />
						</label> <br />
						<br /> <label for="idVCorreo">Domicilio, Calle o Plaza y
							N&ordm;: <html:text property="DDomicilio"
								styleClass="recuadroM colocaDireccionAlta textoNormal"
								maxlength="100" />
						</label> <br />
						<br /> <label for="idVProvincia"> Provincia: <html:select
								property="CProvinciaUsu_id"
								styleClass="cbCuadros colocaProvAlta" size="1"
								onchange="javascript:cargarComboLocalidades();">
								<html:option value="">Seleccione...</html:option>
								<html:options
									collection="<%=Constantes.COMBO_TODAS_PROVINCIAS%>"
									property="CProvinciaId" labelProperty="DProvincia" />
							</html:select>
						</label>
						<%if(((ArrayList) session.getAttribute(Constantes.COMBO_LOCALIDADES)).size() > 0 ){%>
						<br />
						<br /> <label for="idVLocalidades"> Localidad: <html:select
								property="DLocalidadUsu" styleClass="cbCuadros colocaLocMf"
								size="1">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_LOCALIDADES%>"
									property="DLocalidad" />
							</html:select>
						</label>
						<%}%>
						<label for="idVTelefono">C&oacute;digo Postal: <html:text
								property="CPostalUsu"
								styleClass="recuadroM colocaCPMf textoNormal" maxlength="5" />
						</label>
					</div>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> Datos Profesionales Actuales</legend>
					<div class="cuadroFieldset">
						<label for="idVCategoria">R&eacute;gimen jur&iacute;dico:
							* <html:radio name="OCAPSolicitudesForm" property="CJuridico"
								styleClass="opcionRadioAlta"
								value="<%=Constantes.C_JURIDICO_ESTATUTARIO_COD%>" /><%=Constantes.C_JURIDICO_ESTATUTARIO%>
							<html:radio name="OCAPSolicitudesForm" property="CJuridico"
								styleClass="opcionRadioAlta"
								value="<%=Constantes.C_JURIDICO_FUNCIONARIO_COD%>" /><%=Constantes.C_JURIDICO_FUNCIONARIO%>
							<html:radio name="OCAPSolicitudesForm" property="CJuridico"
								styleClass="opcionRadioAlta"
								value="<%=Constantes.C_JURIDICO_OTROS_COD%>" /><%=Constantes.C_JURIDICO_OTROS%>
						</label> <br />
						<br /> <label for="idVCategoria">Situaci&oacute;n
							Administrativa: <html:radio name="OCAPSolicitudesForm"
								property="CSitAdministrativaAuxId" styleClass="opcionRadioAlta"
								value="<%=Constantes.C_SIT_ADM_AUX_ACTIVO_COD%>"
								onclick="javascript:mostrarDonde();" /><%=Constantes.C_SIT_ADM_AUX_ACTIVO%>
							<html:radio name="OCAPSolicitudesForm"
								property="CSitAdministrativaAuxId" styleClass="opcionRadioAlta"
								value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>"
								onclick="javascript:mostrarDonde();" /><%=Constantes.C_SIT_ADM_AUX_OTROS%>
							<span id="serviciosDonde"
							style="display: none; visibility: hidden;"> <html:text
									name="OCAPSolicitudesForm" styleClass="cuadroDondeMf"
									property="DSitAdministrativaAuxOtros" maxlength="50" />
						</span>
						</label> <br />
						<br /> <label for="idVCategoria">Personal: * <html:select
								property="CEstatutarioActualId"
								styleClass="cbCuadros colocaSituacionCBAlta" size="1"
								onchange="javascript:cargarComboCategorias('Y');">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ESTATUTARIO%>"
									property="CPersonalId" labelProperty="DNombre" />
							</html:select>
						</label> <br />
						<br />
						<%if(((ArrayList) session.getAttribute(Constantes.COMBO_CATEGORIA_ACTUAL)).size() > 0 ){%>
						<label for="idVCategoria">Categor&iacute;a: * <html:select
								property="CProfesionalActual_id"
								styleClass="cbCuadros colocaCategoriaAlta" size="1"
								onchange="javascript:cargarCombosEspecialidadesPuestos('Y');">
								<html:option value="">Seleccione...</html:option>
								<html:options
									collection="<%=Constantes.COMBO_CATEGORIA_ACTUAL%>"
									property="CProfesionalId" labelProperty="DNombre" />
							</html:select>
						</label> <br />
						<br />
						<% } %>
						<%if(((ArrayList) session.getAttribute(Constantes.COMBO_ESPECIALIDAD_ACTUAL)).size() > 0 ){%>
						<label for="idVEspecialidad">Especialidad: * <html:select
								property="CEspecActual_id"
								styleClass="cbCuadros colocaEspecialidadAlta" size="1">
								<html:option value="Seleccione">Seleccione...</html:option>
								<html:options
									collection="<%=Constantes.COMBO_ESPECIALIDAD_ACTUAL%>"
									property="CEspecId" labelProperty="DNombre" />
							</html:select>
						</label> <br />
						<br />
						<%}%>
						<!-- PROVINCIA, TIPO GERENCIA Y GERENCIA -->
						<!-- el Administrador puede cambiar la gerencia -->
						<%if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR)){%>
						<label for="idVProvincia"> Provincia: * <html:select
								property="CProvincia_id"
								styleClass="cbCuadros colocaProvinciaAdm" size="1"
								onchange="javascript:cargarComboGerencias();">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_PROVINCIAS%>"
									property="CProvinciaId" labelProperty="DProvincia" />
							</html:select>
						</label> <label for="idVCorreo"> Localidad: <span><bean:write
									name="OCAPSolicitudesForm" property="ACiudad" /></span>
						</label>
						<html:hidden name="OCAPSolicitudesForm" property="ACiudad" />
						<br />
						<br /> <label for="idVProvincia"> Tipo Gerencia: * <html:select
								property="CTipogerencia_id"
								styleClass="cbCuadros colocaTipoGerenciaAdm" size="1"
								onchange="javascript:cargarComboGerencias();">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_TIPOS_GERENCIAS%>"
									property="CTipogerenciaId" labelProperty="DNombre" />
							</html:select>
						</label> <br />
						<br /> <label for="idVGerencia"> Gerencia: * <html:select
								property="CGerencia_id" styleClass="cbCuadros colocaGerenciaAdm"
								size="1" onchange="javascript:cargarComboCentroTrabajo();">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
									property="CGerenciaId" labelProperty="DNombre" />
							</html:select>
						</label>
						<% } else { %>
						<!-- el PGP no puede cambiar la gerencia, solo ve la suya -->
						<label for="idVProvincia"> Provincia: <html:select
								property="CProvincia_id"
								styleClass="cbCuadros colocaProvinciaCBMf" size="1"
								onchange="javascript:cargarComboGerencias();" disabled="true">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_PROVINCIAS%>"
									property="CProvinciaId" labelProperty="DProvincia" />
							</html:select> <html:hidden property="CProvincia_id" />
						</label> <label for="idVCorreo"> Localidad: <span><bean:write
									name="OCAPSolicitudesForm" property="ACiudad" /></span>
						</label>
						<html:hidden name="OCAPSolicitudesForm" property="ACiudad" />
						<br />
						<br /> <label for="idVProvincia"> Tipo Gerencia: <html:select
								property="CTipogerencia_id"
								styleClass="cbCuadros colocaTipoGerenciaMf" size="1"
								onchange="javascript:cargarComboGerencias();" disabled="true">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_TIPOS_GERENCIAS%>"
									property="CTipogerenciaId" labelProperty="DNombre" />
							</html:select> <html:hidden property="CTipogerencia_id" />
						</label> <br />
						<br /> <label for="idVGerencia"> Gerencia: <html:select
								property="CGerencia_id"
								styleClass="cbCuadros colocaGerenciaCBMf" size="1"
								onchange="javascript:cargarComboCentroTrabajo();"
								disabled="true">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
									property="CGerenciaId" labelProperty="DNombre" />
							</html:select> <html:hidden property="CGerencia_id" />
						</label>
						<% } %>
						<br />
						<br /> <label for="idVCentroTrabajo">Centro de Trabajo: *
							<html:select property="CCentrotrabajo_id"
								styleClass="cbCuadros colocaCentroMf" size="1"
								onchange="javascript:cargarComboCiudad();">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_CENTRO_TRABAJO%>"
									property="CCentrotrabajoId" labelProperty="DNombre" />
							</html:select>
						</label> <br />
						<br /> <label for="idVCategoria">Procedimiento de
							evaluaci&oacute;n por el que opta: * <html:select
								property="CProcedimientoId"
								styleClass="cbCuadros colocaSituacionAlta" size="1">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_PERSONAL%>"
									property="CProcedimientoId" labelProperty="DNombre" />
							</html:select>
						</label>
					</div>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> Datos por los que opta a
						Carrera Profesional </legend>
					<div class="cuadroFieldset">
						<label for="idVGrado">Grado: * <html:select
								property="CGrado_id" styleClass="cbCuadros radioGradoAlta"
								size="1" onchange="javascript:obtenerAniosGrado();">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_GRADOS_ALTA%>"
									property="CGradoId" labelProperty="DDescripcion" />
							</html:select>
						</label>
						<html:hidden property="CConvocatoriaId" />
						<html:hidden property="FFinRegistro" />
						<html:hidden name="OCAPSolicitudesForm" property="FRegistro_solic" />
						<!--
                  <div class="fechaGradoAlta">
                     <span> Fecha de realizaci&oacute;n y env&iacute;o de solicitud: </span>
                     <html:hidden property="FRegistro_solic"/>
                     <bean:write name="OCAPSolicitudesForm" property="FRegistro_solic" />
                  </div>
                  -->
						<div class="fechaGradoAlta">
							<span>Fecha de registro oficial: * (dd/mm/aaaa) </span>
							<html:text property="FRegistro_oficial"
								styleClass="recuadroM textoNormal margenIzdaFechaAlta"
								maxlength="19" />
						</div>
						<br />
						<br /> <label for="idVCategoria">Personal: * <html:select
								property="CEstatutarioId"
								styleClass="cbCuadros colocaSituacionCBAlta" size="1"
								onchange="javascript:cargarComboCategorias('N');">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ESTATUTARIO%>"
									property="CPersonalId" labelProperty="DNombre" />
							</html:select>
						</label> <br />
						<br />
						<%if(((ArrayList) session.getAttribute(Constantes.COMBO_CATEGORIA)).size() > 0 ){%>
						<label for="idVCategoria">Categor&iacute;a: * <html:select
								property="CProfesional_id"
								styleClass="cbCuadros colocaCategoriaAlta" size="1"
								onchange="javascript:cargarCombosEspecialidadesPuestos('N');">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
									property="CProfesionalId" labelProperty="DNombre" />
							</html:select>
						</label> <br />
						<br />
						<% } %>
						<%if(((ArrayList) session.getAttribute(Constantes.COMBO_ESPECIALIDAD)).size() > 0 ){%>
						<label for="idVEspecialidad">Especialidad: * <html:select
								property="CEspec_id"
								styleClass="cbCuadros colocaEspecialidadAlta" size="1">
								<html:option value="Seleccione">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
									property="CEspecId" labelProperty="DNombre" />
							</html:select>
						</label> <br />
						<br />
						<%}%>
					</div>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> Datos de Servicios Prestados
					</legend>
					<div class="cuadroFieldset">
						<label for="idVAnt"> <span class="colocaNumAlta">A&ntilde;os
								de ejercicio en la categor&iacute;a profesional por la que se
								accede: * </span> <br />
						<br /> <label>N&ordm; A&ntilde;os: <html:text
									property="NAniosantiguedad"
									styleClass="recuadroM colocaAniosAlta textoNormal"
									maxlength="2" /></label> <label>N&ordm; Meses: <html:text
									property="NMesesantiguedad"
									styleClass="recuadroM colocaAniosAlta textoNormal"
									maxlength="2" /></label> <label>N&ordm; D&iacute;as: <html:text
									property="NDiasantiguedad"
									styleClass="recuadroM colocaAniosAlta textoNormal"
									maxlength="2" /></label>
						</label> <br />
						<br />
						<!--                  
                  <label for="idVPlaza">
                     Fecha en la que obtiene la plaza en propiedad: *
                     <html:text property="FPlazapropiedad" styleClass="recuadroM colocaPlaza textoNormal" maxlength="10" />
                     <a id ="calFPlaza" href='javascript:show_Calendario("OCAPSolicitudesForm", "FPlazapropiedad", document.forms[0].FPlazapropiedad.value);'><html:img src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
                  </label>
                  <br /><br />
                  -->
						<br />
						<br /> <label for="idVServicios"> Tiene servicios
							prestados en otro centro de trabajo distinto al actual: </label> <br />
						<br /> <label> Centro de Trabajo: <html:text
								property="DNombreCentro" maxlength="100"
								styleClass="recuadroM colocaCentroAlta2 textoNormal" />
						</label> <br />
						<br /> <label> Categor&iacute;a/Especialidad/Cuerpo: <html:text
								property="ACategoria" maxlength="200"
								styleClass="recuadroM colocaCategoriaEspecialidadAlta textoNormal" />
						</label> <br />
						<br /> <label> Provincia: <html:select
								property="AProvinciaCentro"
								styleClass="cbCuadros colocaProvinciaAlta2">
								<html:option value="">Seleccione...</html:option>
								<html:options
									collection="<%=Constantes.COMBO_TODAS_PROVINCIAS%>"
									property="DProvincia" labelProperty="DProvincia" />
							</html:select>
						</label> <br />
						<br /> <label> Situaci&oacute;n: <html:select
								property="ASituacion" styleClass="cbCuadros colocaEstadoAlta">
								<html:option value="">Seleccione...</html:option>
								<html:optionsCollection name="<%=Constantes.COMBO_SITUACIONES%>" />
							</html:select>
						</label> <label> V&iacute;nculo: <html:select property="AVinculo"
								styleClass="cbCuadros colocaVinculoAlta">
								<html:option value="">Seleccione...</html:option>
								<html:optionsCollection name="<%=Constantes.COMBO_VINCULOS%>" />
							</html:select>
						</label> <br />
						<br /> <label for="idVNAnnios"> Fecha Inicio:
							(dd/mm/aaaa) <html:text property="FInicio" maxlength="10"
								styleClass="recuadroM colocaFechaAlta textoNormal" /> <span>
								<a
								href='javascript:show_Calendario("OCAPSolicitudesForm", "FInicio", document.forms[0].FInicio.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
						</span>
						</label> <label class="fechaFinAlta" for="idVNAnnios"> Fecha Fin:
							(dd/mm/aaaa) <html:text property="FFin" maxlength="10"
								styleClass="recuadroM colocaFechaAlta textoNormal" /> <span>
								<a
								href='javascript:show_Calendario("OCAPSolicitudesForm", "FFin", document.forms[0].FFin.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
						</span>
						</label> <br />
						<br />
						<div
							class="botonesCertif2 botonesCertif2Docencia colocaBotonesAlta">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <input
									type="button" id="anadir" value=" A&ntilde;adir"
									property="anadir" onclick="anhCentros();" />
								</span> <span class="derBoton"></span>
							</div>
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <input
									type="button" id="modificar" value="Modificar"
									property="modificar" onclick="modCentros();" />
								</span> <span class="derBoton"></span>
							</div>
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <input
									type="button" id="eliminar" value="Eliminar"
									property="eliminar"
									onclick="combos_borrar(document.getElementById('jspCadenaCentro'));" />
								</span> <span class="derBoton"></span>
							</div>
						</div>

						<select size="5" id="jspCadenaCentro" name="jspCadenaCentro"
							class="textoTituloGrisAlta"></select>
						<html:hidden property="resumenCentros" />
						<script language="javascript" type="text/javascript">inicializarCentros();</script>
					</div>
				</fieldset>

				<p>Los campos se&ntilde;alados con * son obligatorios</p>

				<div class="espaciador"></div>

				<div id="informacionImprimible" class="ocultarDatos">
					<p>Certifico que los datos consignados son ciertos.</p>
					<p>Firma:</p>

					<div class="espaciador"></div>

					<p>
						A la atenci&oacute;n de:
						<bean:write name="OCAPSolicitudesForm" property="DGerencia_nombre" />
					</p>
				</div>
				<div class="limpiadora"></div>
				<div class="espaciador"></div>
				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:aceptarSolicitud();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Modificar Solicitud" /> <span> Modificar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:volverAtras();"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" /> <span>
									Cancelar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</html:form>

		</div>
		<!-- /fin de ContenidoDCP1A_PGP -->
	</div>
	<!-- /Fin de Contenido -->

</div>
<!-- /Fin de ocultarCampo -->

<script language="javascript" type="text/javascript">mostrarDonde();</script>
