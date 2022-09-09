<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<title>..: Obtenci&oacute;n Carrera Profesional :..</title>
<meta http-equiv="Content-Type"
	content="application/xhtml+xml; charset=ISO-8859-1" />
<link href="css/EstilosJCYLFW.css" rel="stylesheet" media="screen">
	<link href="css/ocap.css" rel="stylesheet" media="screen">
		<link href="css/ocap_print.css" rel="stylesheet" media="print">

			<!--[if IE 6]>
      <link rel="stylesheet" type="text/css" href="css/EstilosJCYLFW_ie6.css" media="all" title="Hoja de estilos especifica para IE 6" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_ie6.css" media="all" title="Hoja de estilos especifica para IE 6" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_print_ie6.css" media="print" title="Hoja de estilos especifica para IE 6" ></link>
   <![endif]-->
			<!--[if IE 7]>
      <link rel="stylesheet" type="text/css" href="css/EstilosJCYLFW_ie7.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_ie7.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_print_ie7.css" media="print" title="Hoja de estilos especifica para IE 7" ></link>
   <![endif]-->
			<!--[if gt IE 8]>
      <link rel="stylesheet" type="text/css" href="css/EstilosJCYLFW_ie8.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_ie8.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
      <link rel="stylesheet" type="text/css" href="css/ocap_print_ie8.css" media="print" title="Hoja de estilos especifica para IE 7" ></link>
   <![endif]-->

			<script language="javascript" type="text/javascript"
				src="<html:rewrite page='/javascript/comun.js'/>"></script>
			<script language="JavaScript" type="text/javascript"
				src="<html:rewrite page='/javascript/combos1.js'/>"></script>
			<script language="javascript" type="text/javascript"
				src="<html:rewrite page='/javascript/calendario.js'/>"></script>
			<script language="javascript" type="text/javascript"
				src="<html:rewrite page='/javascript/solicitudes/validacionesSolicitudes.js'/>"></script>
			<script language="javascript" type="text/javascript"
				src="<html:rewrite page='/javascript/formularios.js'/>"></script>
			<script language="javascript" type="text/javascript"
				src="<html:rewrite page='/javascript/fechas.js'/>"></script>
			<script language="javascript" type="text/javascript"
				src="<html:rewrite page='/javascript/ventanas.js'/>"></script>

			<script language="JavaScript">
   
   function cargarComboLocalidades(){
      var aceptado = 'N'
      if (document.forms[0].acepto.checked) {
         aceptado = 'S';
      }
      if(document.forms[0].DLocalidadUsu != undefined)
         document.forms[0].DLocalidadUsu.value = '';
      enviar('OCAPSolicitudes.do?accion=cargarComboLocalidades&jspVuelta=solicitud&aceptado='+aceptado);
   }
   
   function cargarComboCategorias(actual){
      var aceptado = 'N'
      if (document.forms[0].acepto.checked) {
         aceptado = 'S';
      }
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
      enviar('OCAPSolicitudes.do?accion=cargarComboCategorias&jspVuelta=solicitud&aceptado='+aceptado+'&actual='+actual);
   }
   
   function cargarCombosEspecialidadesPuestos(actual){
      var aceptado = 'N'
      if (document.forms[0].acepto.checked) {
         aceptado = 'S';
      }
      if (actual == '<%=Constantes.SI%>') {
         if(document.forms[0].CEspecActual_id != undefined)
            document.forms[0].CEspecActual_id.value = '';
      } else {
         if(document.forms[0].CEspec_id != undefined)
            document.forms[0].CEspec_id.value = '';
      }
      enviar('OCAPSolicitudes.do?accion=cargarCombosEspecialidadesPuestos&jspVuelta=solicitud&aceptado='+aceptado+'&actual='+actual);
   }
   
   function cargarComboGerencias(){
      var aceptado = 'N'
      if (document.forms[0].acepto.checked) {
         aceptado = 'S';
      }
      if(document.forms[0].CGerencia_id != undefined)
         document.forms[0].CGerencia_id.value='';
      if(document.forms[0].CCentrotrabajo_id != undefined)
         document.forms[0].CCentrotrabajo_id.value='';
      enviar('OCAPSolicitudes.do?accion=cargarComboGerencias&CProvincia_id='+ document.forms[0].CProvincia_id.value +'&cTipogerencia_id='+ document.forms[0].CTipogerencia_id.value+'&jspVuelta=solicitud&aceptado='+aceptado);
   }
   
   function cargarComboCentroTrabajo(){  
      var aceptado = 'N'
      if (document.forms[0].acepto.checked) {
         aceptado = 'S';
      }
      if(document.forms[0].CCentrotrabajo_id != undefined)
         document.forms[0].CCentrotrabajo_id.value='';
      enviar('OCAPSolicitudes.do?accion=cargarComboCentroTrabajo&CGerencia_id='+ document.forms[0].CGerencia_id.value+'&jspVuelta=solicitud&aceptado='+aceptado);
   }
   
   function cargarComboCiudad(){
      var aceptado = 'N'
      if (document.forms[0].acepto.checked) {
         aceptado = 'S';
      }
      if(document.forms[0].ACiudad != undefined)
         document.forms[0].ACiudad.value = '';
      enviar('OCAPSolicitudes.do?accion=cargarComboCiudad&CCentroTrabajo_id='+ document.forms[0].CCentrotrabajo_id.value+'&jspVuelta=solicitud&aceptado='+aceptado);
   }
   
   var listaGradosJS = new Array();
</script>
			<logic:present name="<%=Constantes.COMBO_GRADOS_ALTA%>">
				<logic:iterate id="grados" name="<%=Constantes.COMBO_GRADOS_ALTA%>"
					type="OCAPGradoOT">
					<script>
         linea = new Array();
         linea[0] = '<bean:write name="grados" property="CGradoId"/>';
         linea[1] = '<bean:write name="grados" property="NAniosejercicio"/>';
         listaGradosJS[listaGradosJS.length] = linea;
      </script>
				</logic:iterate>
			</logic:present>
			<script>
   function obtenerAniosGrado(){
      var longListaGrados = listaGradosJS.length;
      for (i=0; i < longListaGrados; i++) {
         if (document.forms[0].CGrado_id.value == listaGradosJS[i][0]) {
            document.forms[0].NAniosejercicio.value = listaGradosJS[i][1];
         }
      }
   }
   
   function aceptarSolicitud(){
      if(validarFormulario(document.forms[0], 'S')) {
         var mesAnt = obtenerCifra(document.forms[0].NAniosantiguedad.value);
         var mesEjer = obtenerCifra(document.forms[0].NAniosejercicio.value);    
         document.forms[0].resumenCentros.value=valorCombo(document.getElementById('jspCadenaCentro'));
         if(parseInt(mesAnt) < parseInt(mesEjer)){
            // No cumple la condicion de años minimos de antiguedad
            if(confirm(" El n\u00famero de a\u00f1os elegido es inferior al m\u00ednimo \u00BFest\u00e1 seguro de querer registrar la solicitud?")){
               enviar('OCAPSolicitudes.do?accion=generarPDFSolic'); 
            }
         }else{
               enviar('OCAPSolicitudes.do?accion=generarPDFSolic'); 
         }
      }
   }
     
   function activarAceptar(){
      if (document.forms[0].acepto.checked) {
         document.getElementById('ocultarAceptar').style.display='';
         document.getElementById('ocultarAceptar').style.visibility='visible';
      } else {
         document.getElementById('ocultarAceptar').style.display='none';
         document.getElementById('ocultarAceptar').style.visibility='hidden';
      }
   }

function mostrarBotones(param) {
   if (param == '<%=Constantes.SI%>') {
      document.getElementById('ocultarBotones').style.display='';
      document.getElementById('ocultarBotones').style.visibility='visible';
   } else {
      document.getElementById('ocultarBotones').style.display='none';
      document.getElementById('ocultarBotones').style.visibility='hidden';
   }
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

function anhCertificado() {
   formu = document.forms[0];
   //comboCertificados=objeto_combo('document.forms[0].jspCadenaCentro' + index);
   comboCertificados = document.getElementById('jspCadenaCentro');
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
      /* var texto = formu.DNombreCentro.value +'---'+ formu.AProvinciaCentro.value +'---'+ 
                  formu.ACategoria.value +'---'+ formu.ASituacion.value +'---'+ formu.AVinculo.value +'---'+
                  formu.FInicio.value + '---' +  formu.FFin.value;
      */
      var aux = valorCombo(document.getElementById('jspCadenaCentro'));
//      var aux = valorCombo(formu.jspCadenaCentro);

//      if ( aux.indexOf('$' + formu.FInicio[index].value + '$') == -1 ) {
         //comboCertificados.nueva_opcion(texto,valor);
         var campo = formu.jspCadenaCentro;
         nueva_opcion(comboCertificados,texto,valor);
         limpiarCamposCentros();
//      } else  {
//         alert('No se puede a\u00f1adir el Certificado porque ya existe');
//      }
   }
} //Fin anhCertificado

function limpiarCamposCentros() {
   formu.DNombreCentro.value = '';
   formu.AProvinciaCentro.value = '';
   formu.ACategoria.value = '';
   formu.ASituacion.value = '';
   formu.AVinculo.value = '';
   formu.FInicio.value = '';
   formu.FFin.value = '';
}

function modCertificado() {
	formu = document.forms[0];

   comboCertificados=objeto_combo('document.forms[0].jspCadenaCentro');

//	if (comprueba_seleccion(formu.jspCadenaCentro)) {
   if (comprueba_seleccion(document.getElementById('jspCadenaCentro'))) {
    indice = document.getElementById('jspCadenaCentro').selectedIndex;
    var valor = comboCertificados.options[indice].value;
    var valor_aux = valor;     // variable para operar
    var cadenaTokenizado = valor_aux.split('<%=Constantes.SEPARADOR_2%>');
   formu.DNombreCentro.value = cadenaTokenizado[0];
   formu.AProvinciaCentro.value =  cadenaTokenizado[1];
   formu.ACategoria.value =  cadenaTokenizado[2];
   formu.ASituacion.value =  cadenaTokenizado[3];
   formu.AVinculo.value =  cadenaTokenizado[4];
   formu.FInicio.value =  cadenaTokenizado[5];
   formu.FFin.value =  cadenaTokenizado[6];

	 combos_borrar(document.getElementById('jspCadenaCentro'));
	}
}
</script>
</head>
<body>
	<div class="encuadre">
		<jsp:include page="/cabecera.jsp" flush="true" />
		<div class="marcoContenidoAccesible">
			<div class="menuAccesible">
				<div class="imagenLateralIzquierda"></div>
			</div>
			<div class="contenidoAccesible">
				<div class="cuadroContenedor">
					<div class="contenido">
						<div class="contenidoDCP1A">

							<html:form action="/OCAPSolicitudes.do">
								<html:hidden property="NAniosejercicio" />
								<h2 class="tituloContenido">SOLICITUD DE ACCESO A GRADO DE
									CARRERA PROFESIONAL</h2>
								<div class="lineaBajaM"></div>
								<div class="espaciador"></div>

								<fieldset>
									<legend class="tituloLegend"> Datos Personales </legend>
									<div class="cuadroFieldset">
										<label for="idVApell1"> Primer Apellido: * <html:text
												property="DApellido1"
												styleClass="recuadroM colocaApellidos1Sol textoNormal"
												maxlength="30" />
										</label> <label for="idVApell1"> Segundo Apellido: * <html:text
												property="DApellido2"
												styleClass="recuadroM colocaApellidos2Sol textoNormal"
												maxlength="30" />
										</label> <br />
										<br /> <label for="idVNombre"> Nombre: * <html:text
												property="DNombre"
												styleClass="recuadroM colocaNombreSol textoNormal"
												maxlength="30" />
										</label> <br />
										<br /> <label for="idVDNI"> NIF/NIE: * <html:text
												name="OCAPSolicitudesForm" property="CDniReal"
												styleClass="recuadroM colocaDNISol textoNormal"
												maxlength="10" />
										</label> <label for="idVCorreo">Sexo: * <html:select
												styleClass="cbCuadros colocaSexoSol"
												name="OCAPSolicitudesForm" property="BSexo">
												<html:option value="">Seleccione...</html:option>
												<html:option value="<%=Constantes.SEXO_HOMBRE_VALUE%>"><%=Constantes.SEXO_HOMBRE%></html:option>
												<html:option value="<%=Constantes.SEXO_MUJER_VALUE%>"><%=Constantes.SEXO_MUJER%></html:option>
											</html:select>
										</label> <br />
										<br /> <label for="idVTelefono">Tel&eacute;fono 1: *<html:text
												property="NTelefono1"
												styleClass="recuadroM colocaTelefono1Sol textoNormal"
												maxlength="9" />
										</label> <label for="idVTelefono">Tel&eacute;fono 2: <html:text
												property="NTelefono2"
												styleClass="recuadroM colocaTelefono2Sol textoNormal"
												maxlength="9" />
										</label> <br />
										<br /> <label for="idVCorreo">Correo
											Electr&oacute;nico: *<html:text property="DCorreoelectronico"
												styleClass="recuadroM colocaCorreoSol textoNormal"
												maxlength="50" />
										</label> <br />
										<br /> <label for="idVCorreo">Domicilio, Calle o
											Plaza y N&ordm;: <html:text property="DDomicilio"
												styleClass="recuadroM colocaDireccionSol textoNormal"
												maxlength="100" />
										</label> <br />
										<br /> <label for="idVProvincia"> Provincia: <html:select
												property="CProvinciaUsu_id"
												styleClass="cbCuadros colocaProvinciaSol" size="1"
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
												property="DLocalidadUsu"
												styleClass="cbCuadros colocaLocalidadSol" size="1">
												<html:option value="">Seleccione...</html:option>
												<html:options collection="<%=Constantes.COMBO_LOCALIDADES%>"
													property="DLocalidad" />
											</html:select>
										</label>
										<%}%>
										<label for="idVTelefono">C&oacute;digo Postal: <html:text
												property="CPostalUsu"
												styleClass="recuadroM colocaCPSol textoNormal" maxlength="5" />
										</label>

									</div>
								</fieldset>

								<fieldset>
									<legend class="tituloLegend"> Datos Profesionales
										Actuales </legend>
									<div class="cuadroFieldset solicitudAltaGrado">
										<span>R&eacute;gimen jur&iacute;dico: * </span> <span
											class="colocaSINO"> <html:radio
												name="OCAPSolicitudesForm" property="CJuridico"
												value="<%=Constantes.C_JURIDICO_ESTATUTARIO_COD%>"
												styleClass="altoRadios" /> <%=Constantes.C_JURIDICO_ESTATUTARIO%></span>
										<span> <html:radio name="OCAPSolicitudesForm"
												property="CJuridico"
												value="<%=Constantes.C_JURIDICO_FUNCIONARIO_COD%>"
												styleClass="altoRadios" /> <%=Constantes.C_JURIDICO_FUNCIONARIO%></span><br />
										<br /> <label for="idVCategoria">Situaci&oacute;n
											Administrativa: * <html:radio name="OCAPSolicitudesForm"
												property="CSitAdministrativaAuxId"
												styleClass="opcionRadioAlta"
												value="<%=Constantes.C_SIT_ADM_AUX_ACTIVO_COD%>"
												onclick="javascript:mostrarDonde();" /><%=Constantes.C_SIT_ADM_AUX_ACTIVO%>
											<html:radio name="OCAPSolicitudesForm"
												property="CSitAdministrativaAuxId"
												styleClass="opcionRadioAlta"
												value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>"
												onclick="javascript:mostrarDonde();" /><%=Constantes.C_SIT_ADM_AUX_OTROS%>
											<span id="serviciosDonde"
											style="display: none; visibility: hidden;"> <html:text
													name="OCAPSolicitudesForm" styleClass="cuadroOtras"
													property="DSitAdministrativaAuxOtros" maxlength="50" />
										</span>
										</label> <br />
										<br /> <label for="idVCategoria">Personal: * <html:select
												property="CEstatutarioActualId"
												styleClass="cbCuadros colocaSituacionCBSol" size="1"
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
												styleClass="cbCuadros colocaCategoriaCBSol" size="1"
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
												styleClass="cbCuadros colocaEspecialidadCBSol" size="1">
												<html:option value="Seleccione">Seleccione...</html:option>
												<html:options
													collection="<%=Constantes.COMBO_ESPECIALIDAD_ACTUAL%>"
													property="CEspecId" labelProperty="DNombre" />
											</html:select>
										</label> <br />
										<br />
										<%}%>
										<!-- PROVINCIA, TIPO GERENCIA Y GERENCIA -> PARA LA VISTA DE LA GESTION PERIODICA-->
										<label for="idVProvincia"> Provincia: * <html:select
												property="CProvincia_id"
												styleClass="cbCuadros colocaProvinciaCBSol" size="1"
												onchange="javascript:cargarComboGerencias();">
												<html:option value="">Seleccione...</html:option>
												<html:options collection="<%=Constantes.COMBO_PROVINCIAS%>"
													property="CProvinciaId" labelProperty="DProvincia" />
											</html:select>
										</label> <label for="idVCorreo" class="colocaDatoLocAlta">
											Localidad: <span><bean:write
													name="OCAPSolicitudesForm" property="ACiudad" /></span>
										</label>
										<html:hidden name="OCAPSolicitudesForm" property="ACiudad" />
										<br />
										<br /> <label for="idVProvincia"> Tipo Gerencia: * <html:select
												property="CTipogerencia_id"
												styleClass="cbCuadros colocaTipoGerenciaCBSol" size="1"
												onchange="javascript:cargarComboGerencias();">
												<html:option value="">Seleccione...</html:option>
												<html:options
													collection="<%=Constantes.COMBO_TIPOS_GERENCIAS%>"
													property="CTipogerenciaId" labelProperty="DNombre" />
											</html:select>
										</label> <br />
										<br />
										<%if(((ArrayList) session.getAttribute(Constantes.COMBO_GERENCIA)).size() > 0 ){%>
										<label for="idVGerencia"> Gerencia: * <html:select
												property="CGerencia_id"
												styleClass="cbCuadros colocaGerenciaCBSol" size="1"
												onchange="javascript:cargarComboCentroTrabajo();">
												<html:option value="">Seleccione...</html:option>
												<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
													property="CGerenciaId" labelProperty="DNombre" />
											</html:select>
										</label> <br />
										<br />
										<%}%>
										<%if(((ArrayList) session.getAttribute(Constantes.COMBO_CENTRO_TRABAJO)).size() > 0 ){%>
										<label for="idVCentroTrabajo">Centro de Trabajo: * <html:select
												property="CCentrotrabajo_id"
												styleClass="cbCuadros colocaCentroCBSol" size="1"
												onchange="javascript:cargarComboCiudad();">
												<html:option value="">Seleccione...</html:option>
												<html:options
													collection="<%=Constantes.COMBO_CENTRO_TRABAJO%>"
													property="CCentrotrabajoId" labelProperty="DNombre" />
											</html:select>
										</label> <br />
										<br />
										<%}%>
										<span>Procedimiento de evaluaci&oacute;n por el que
											opta: * </span><br /> <span> <html:radio
												name="OCAPSolicitudesForm" property="CProcedimientoId"
												value="<%=Constantes.PROC_GENERAL%>" styleClass="altoRadios" />
											General
										</span> <span class="spanEspecial"> Especial: </span> <br /> <span><html:radio
												name="OCAPSolicitudesForm" property="CProcedimientoId"
												value="<%=Constantes.PROC_ESPECIAL_DIREC%>"
												styleClass="altoRadios radioEspecial" /> Puestos de
											car&aacute;cter directivo</span><br /> <span><html:radio
												name="OCAPSolicitudesForm" property="CProcedimientoId"
												value="<%=Constantes.PROC_ESPECIAL_EXCED%>"
												styleClass="altoRadios radioEspecial" /> Excedencia por
											cuidado de familiares</span><br /> <span><html:radio
												name="OCAPSolicitudesForm" property="CProcedimientoId"
												value="<%=Constantes.PROC_ESPECIAL_LIBER%>"
												styleClass="altoRadios radioEspecial" /> Liberado sindical</span><br />
										<span><html:radio name="OCAPSolicitudesForm"
												property="CProcedimientoId"
												value="<%=Constantes.PROC_ESPECIAL_ADMIN%>"
												styleClass="altoRadios radioEspecial" /> Estructura
											administrativa y de gesti&oacute;n</span><br /> <br />
									</div>
								</fieldset>

								<fieldset>
									<legend class="tituloLegend"> Datos por los que opta a
										Carrera Profesional </legend>
									<div class="cuadroFieldset">
										<label for="idVGrado">Grado al que opta: * <html:select
												property="CGrado_id" styleClass="cbCuadros radioGradoSol"
												size="1" onchange="javascript:obtenerAniosGrado();">
												<html:option value="">Seleccione...</html:option>
												<html:options collection="<%=Constantes.COMBO_GRADOS_ALTA%>"
													property="CGradoId" labelProperty="DDescripcion" />
											</html:select>
										</label> <br />
										<br /> <label for="idVCategoria">Personal: * <html:select
												property="CEstatutarioId"
												styleClass="cbCuadros colocaSituacionCBSol" size="1"
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
												styleClass="cbCuadros colocaCategoriaCBSol" size="1"
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
												styleClass="cbCuadros colocaEspecialidadCBSol" size="1">
												<html:option value="Seleccione">Seleccione...</html:option>
												<html:options
													collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
													property="CEspecId" labelProperty="DNombre" />
											</html:select>
										</label> <br />
										<br />
										<%}%>
									</div>
								</fieldset>

								<fieldset>
									<legend class="tituloLegend"> Datos de Servicios
										Prestados </legend>
									<div class="cuadroFieldset altaUsuario">
										<label for="idVAnt"> <span class="colocaNumAlta">A&ntilde;os
												de ejercicio en la categor&iacute;a profesional por la que
												se accede: * </span> <br />
										<br /> <label>N&ordm; A&ntilde;os: <html:text
													property="NAniosantiguedad"
													styleClass="recuadroM colocaAniosAlta textoNormal"
													maxlength="2" /></label> <label>N&ordm; Meses: <html:text
													property="NMesesantiguedad"
													styleClass="recuadroM colocaMesesAlta textoNormal"
													maxlength="2" /></label> <label>N&ordm; D&iacute;as: <html:text
													property="NDiasantiguedad"
													styleClass="recuadroM colocaDiasAlta textoNormal"
													maxlength="2" /></label>
										</label> <br />
										<br />
										<!--
                  <label for="idVPlaza" class="solicitudAlta">
                     Fecha en la que obtiene la plaza en propiedad: *
                     <html:text property="FPlazapropiedad" styleClass="recuadroM colocaPlaza textoNormal" maxlength="10" />
                     <a id ="calFPlaza" href='javascript:show_Calendario("OCAPSolicitudesForm", "FPlazapropiedad", document.forms[0].FPlazapropiedad.value);'><html:img src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
                  </label>
                  <div class="espaciador"></div>
                  -->
										<br />
										<br /> <label for="idVServicios"> Tiene servicios
											prestados en otro centro de trabajo distinto al actual: </label> <br />
										<br /> <label> Centro de Trabajo: <html:text
												property="DNombreCentro" maxlength="100"
												styleClass="recuadroM colocaServiciosCentroCBSol textoNormal" />
										</label> <br />
										<br /> <label> Categor&iacute;a/Especialidad/Cuerpo:
											<html:text property="ACategoria" maxlength="200"
												styleClass="recuadroM colocaCategoriaSol textoNormal" />
										</label> <br />
										<br /> <label> Provincia: <html:select
												property="AProvinciaCentro"
												styleClass="cbCuadros colocaProvinciaSol">
												<html:option value="">Seleccione...</html:option>
												<html:options
													collection="<%=Constantes.COMBO_TODAS_PROVINCIAS%>"
													property="DProvincia" labelProperty="DProvincia" />
											</html:select>
										</label> <br />
										<br /> <label> Situaci&oacute;n: <html:select
												property="ASituacion"
												styleClass="cbCuadros colocaSituacionSol">
												<html:option value="">Seleccione...</html:option>
												<html:optionsCollection
													name="<%=Constantes.COMBO_SITUACIONES%>" />
											</html:select>
										</label> <label> V&iacute;nculo: <html:select
												property="AVinculo" styleClass="cbCuadros colocaVinculoSol">
												<html:option value="">Seleccione...</html:option>
												<html:optionsCollection
													name="<%=Constantes.COMBO_VINCULOS%>" />
											</html:select>
										</label> <br />
										<br /> <label> Fecha Inicio: (dd/mm/aaaa) <html:text
												property="FInicio" maxlength="10"
												styleClass="recuadroM colocaFecha1Sol textoNormal" /> <span>
												<a
												href='javascript:show_Calendario("OCAPSolicitudesForm", "FInicio", document.forms[0].FInicio.value);'><html:img
														src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
										</span>
										</label> <label class="fechaFinSol"> Fecha Fin: (dd/mm/aaaa) <html:text
												property="FFin" maxlength="10"
												styleClass="recuadroM colocaFecha2Sol textoNormal" /> <span>
												<a
												href='javascript:show_Calendario("OCAPSolicitudesForm", "FFin", document.forms[0].FFin.value);'><html:img
														src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
										</span>
										</label> <br />
										<br />
										<div
											class="botonesCertif2 botonesCertif2Docencia colocaBotonesSol">
											<div class="botonAccion">
												<span class="izqBoton"></span> <span class="cenBoton">
													<input type="button" id="anadir" value=" A&ntilde;adir"
													property="anadir" onclick="anhCertificado();" />
												</span> <span class="derBoton"></span>
											</div>
											<div class="botonAccion">
												<span class="izqBoton"></span> <span class="cenBoton">
													<input type="button" id="modificar" value="Modificar"
													property="modificar" onclick="modCertificado();" />
												</span> <span class="derBoton"></span>
											</div>
											<div class="botonAccion">
												<span class="izqBoton"></span> <span class="cenBoton">
													<input type="button" id="eliminar" value="Eliminar"
													property="eliminar"
													onclick="combos_borrar(document.getElementById('jspCadenaCentro'));" />
												</span> <span class="derBoton"></span>
											</div>
										</div>
										<html:hidden property="resumenCentros" />
										<select size="5" id="jspCadenaCentro" name="jspCadenaCentro"
											class="textoTituloGrisSol"></select>

									</div>
								</fieldset>

								<fieldset>
									<div class="cuadroFieldsetLOPD">
										<p class="textoNormal margenDcho10">
										    AUTORIZA a la Gerencia Regional de Salud de Castilla y Le&oacute;n 
							    para obtener directamente y/o por medios telem&aacute;ticos la 
							    certificaci&oacute;n de los servicios prestados necesarios para el 
							    pronunciamiento de la correspondiente resolución.<br /><br />
 								En caso de no prestar la autorizaci&oacute;n ser&aacute; necesario 
 								aportar el certificado de servicios prestados conforme a los modelos 
 								previstos en los anexos III y IV.<br />
								<br />
											Doy mi CONSENTIMIENTO para que los datos e informaciones
											aportados en el presente procedimiento, sean almacenados y
											codificados con la finalidad de su posterior
											utilizaci&oacute;n en los procedimientos administrativos para
											Carrera Profesional en el que fueran requeridos, de
											conformidad con lo establecido en el art&iacute;culo 6.2.b)
											de la Ley 11/2007, de 22 de junio, de acceso
											electr&oacute;nico de los ciudadanos a los Servicios
											P&uacute;blicos y en los art&iacute;culos 11.1 y 41.2 de la
											Ley Org&aacute;nica 3/2018, de 5 de diciembre, de
											Protecci&oacute;n de Datos Personales y Garant&iacute;a de los derechos digitales <br />
											<br /> De conformidad con lo establecido en el
											art&iacute;culo 5 de la ley Org&aacute;nica 3/2018, de 5 de
											Diciembre, de Protecci&oacute;n de Datos
											Personales y Garant&iacute;a de los Derechos Digitales , la Gerencia Regional de Salud de Castilla y
											Le&oacute;n le informa que los datos aportados a este
											formulario ser&aacute;n incorporados a su fichero para su
											tratamiento automatizado. Le comunicamos que podr&aacute;
											ejercer los derechos de acceso, rectificaci&oacute;n,
											cancelaci&oacute;n y oposici&oacute;n, previstos por la Ley,
											mediante escrito, seg&uacute;n modelos normalizados por Orden
											PAT/175/2003, de 20 de febrero, dirigido a la Gerencia
											Regional de Salud de Castilla y Le&oacute;n, Pº Zorrilla 1,
											47007-Valladolid <br />
											<br />
											<div class="textoCentrado">
												<input type="checkbox" name="acepto" value="N"
													onclick="activarAceptar()" /> <span
													class="textoNegrita textoColorM"> Est&aacute; de
													acuerdo </span>
												<div class="espaciadorPeq"></div>
											</div>
										</p>
									</div>
								</fieldset>


								<p>Los campos se&ntilde;alados con * son obligatorios</p>
								<div class="capaPieSol"></div>

								<div id="ocultarAceptar"
									style="display: none; visibility: hidden;">
									<div class="botonesPagina posicionLOPD">
										<div class="botonAccion">
											<span class="izqBoton"></span> <span class="cenBoton">
												<a href="javascript:aceptarSolicitud();"> <img
													src="imagenes/imagenes_ocap/flecha_correcto.gif"
													alt="Generar PDF" /> <span> Aceptar </span>
											</a>
											</span> <span class="derBoton"></span>
										</div>
									</div>
								</div>
							</html:form>
						</div>
						<!-- /fin de ContenidoDCP1A -->
					</div>
					<!-- /Fin de Contenido -->


				</div>
				<!-- Del marco contenidoAccesible -->
			</div>
			<!-- Del marco marcoContenidoAccesible -->

		</div>
		<!-- Del marco encuadre -->


		<div id="footer">
			<html:link href="http://www.salud.jcyl.es" target="_blank">Salud Castilla y Le&oacute;n </html:link>
		</div>
</body>
</html>

<script language="JavaScript">
<%if ( (String)request.getAttribute("primeraSolic") != null && ((String)request.getAttribute("primeraSolic")).equals(Constantes.NO)){%>
      document.forms[0].acepto.checked = true;      
      document.getElementById('ocultarAceptar').style.display='';
      document.getElementById('ocultarAceptar').style.visibility='visible';     
<%}%>   
mostrarDonde();
</script>
