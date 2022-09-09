<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT"%>
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
	src="<html:rewrite page='/javascript/solicitudes/validacionesSolicitudes.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/fechas.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/ventanas.js'/>"></script>

<script language="JavaScript">

function cargarComboLocalidades(){
   if(document.forms[0].DLocalidadUsu != undefined)
      document.forms[0].DLocalidadUsu.value = '';
   enviar('OCAPSolicitudes.do?accion=cargarComboLocalidades&jspVuelta=alta');
}

function cargarComboCategorias(actual){
   //enviar('OCAPSolicitudes.do?accion=cargarComboCategorias&CEstatutarioId='+ document.forms[0].CEstatutarioId.value +'&jspVuelta=alta');
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
   enviar('OCAPSolicitudes.do?accion=cargarComboCategorias&jspVuelta=alta&actual='+actual);
}

function cargarCombosEspecialidadesPuestos(actual){
   //enviar('OCAPSolicitudes.do?accion=cargarCombosEspecialidadesPuestos&CProfesional_id='+ document.forms[0].CProfesional_id.value +'&jspVuelta=alta');
   if (actual == '<%=Constantes.SI%>') {
      if(document.forms[0].CEspecActual_id != undefined)
         document.forms[0].CEspecActual_id.value = '';
   } else {
      if(document.forms[0].CEspec_id != undefined)
         document.forms[0].CEspec_id.value = '';
   }
   enviar('OCAPSolicitudes.do?accion=cargarCombosEspecialidadesPuestos&jspVuelta=alta&actual='+actual);
}

function cargarComboGerencias(){
   if(document.forms[0].CGerencia_id != undefined)
      document.forms[0].CGerencia_id.value='';
   if(document.forms[0].CCentrotrabajo_id != undefined)
      document.forms[0].CCentrotrabajo_id.value='';
   enviar('OCAPSolicitudes.do?accion=cargarComboGerencias&CProvincia_id='+ document.forms[0].CProvincia_id.value +'&cTipogerencia_id='+ document.forms[0].CTipogerencia_id.value+'&jspVuelta=alta');
}

function cargarComboCentroTrabajo(){
   if(document.forms[0].CCentrotrabajo_id != undefined)
      document.forms[0].CCentrotrabajo_id.value='';
   enviar('OCAPSolicitudes.do?accion=cargarComboCentroTrabajo&CGerencia_id='+ document.forms[0].CGerencia_id.value+'&jspVuelta=alta');
}

function cargarComboCiudad(){
   if(document.forms[0].ACiudad != undefined)
      document.forms[0].ACiudad.value = '';
   enviar('OCAPSolicitudes.do?accion=cargarComboCiudad&CCentroTrabajo_id='+ document.forms[0].CCentrotrabajo_id.value+'&jspVuelta=alta');
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
         linea[2] = '<bean:write name="grados" property="CConvocatoriaId"/>';
         linea[3] = '<bean:write name="grados" property="FFinRegistro"/>';
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
         document.forms[0].CConvocatoriaId.value = listaGradosJS[i][2];
         document.forms[0].FFinRegistro.value = listaGradosJS[i][3];
      }
   }
//   enviar('OCAPSolicitudes.do?accion=obtenerAniosGrado');
}

function registrarSolicitud(irDetalle){
   document.forms[0].resumenCentros.value = valorCombo(document.getElementById('jspCadenaCentro'))
   if(document.forms[0].bExisteSolicParaNifConv.value=="true"){
	   var confirmar =
	   		confirm('Existe una solicitud registrada para este NIF/NIE y convocatoria. ¿Desea sustituirla por la actual?');
	   if(confirmar){
		   enviar('OCAPSolicitudes.do?accion=insertar&irDetalle='+irDetalle);
	   } else {
		   alert('La solicitud actual NO ha sido grabada.')
	   }
   } else {
	   enviar('OCAPSolicitudes.do?accion=insertar&irDetalle='+irDetalle);
   }
}

function irAltaSolicitud(){
   enviar('OCAPSolicitudes.do?accion=irInsertar');
}

function nuevoUsuario(){
   enviar('OCAPSolicitudes.do?accion=insertarNuevo_a');
}

function asociar(usuario){
   enviar('OCAPSolicitudes.do?accion=asignarUsuario_a&cUsuarioId='+usuario+'&cSolicitudId='+document.forms[0].CSolicitudId);
}


function aceptarSolicitud(){
   if(validarFormulario(document.forms[0],'')) {
      var aniosAnt = obtenerCifra(document.forms[0].NAniosantiguedad.value);
      var aniosEjer = obtenerCifra(document.forms[0].NAniosejercicio.value);
      document.forms[0].resumenCentros.value=valorCombo(document.getElementById('jspCadenaCentro'));
      if(parseInt(aniosAnt) < parseInt(aniosEjer)){
         // No cumple la condiciï¿½n de aï¿½os mï¿½nimos de antigï¿½edad
         if(confirm(" El n\u00famero de a\u00f1os elegido es inferior al m\u00ednimo \u00BFest\u00e1 seguro de querer registrar la solicitud?")){
            enviar('OCAPSolicitudes.do?accion=irDetalleAlta'); 
         }
      }else{
            enviar('OCAPSolicitudes.do?accion=irDetalleAlta'); 
      }
   }
}

function ayuda(){ 
    str = '<%=request.getContextPath()%>/OCAPSolicitudes.do';
    str = str + '?accion=ayuda';
    lanzar(str,'Ayuda',502,376,150,150,1,1)
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

function activarAceptar(){
   if (document.forms[0].acepto.checked) {
      document.getElementById('ocultarAceptar').style.display='';
      document.getElementById('ocultarAceptar').style.visibility='visible';
   } else {
      document.getElementById('ocultarAceptar').style.display='none';
      document.getElementById('ocultarAceptar').style.visibility='hidden';
   }
}

function generarPDF(){
   enviar('OCAPSolicitudes.do?accion=generarPDF&jspAccion=OCAPSolicitudesAl');
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

<logic:equal name="tipoAccion" value="<%=Constantes.IR_INSERTAR%>">
	<div class="ocultarCampo">
</logic:equal>

<div class="contenido">
	<div class="contenidoDCP1A">

		<html:form action="/OCAPSolicitudes.do">
			<html:hidden property="NAniosejercicio" />
			<html:hidden property="CExp_id" />

			<h2 class="tituloContenido">REGISTRO DE LA SOLICITUD DE ACCESO A
				GRADO DE CARRERA PROFESIONAL</h2>
			<logic:notEqual name="tipoAccion" value="<%=Constantes.IR_INSERTAR%>">
				<logic:notEqual name="tipoAccionBis" value="<%=Constantes.SI%>">
					<a href="javascript:generarPDF();"><img
						src="imagenes/imagenes_ocap/imprimir.gif" alt="Imprimir"
						class="flotaIzq" /></a>
				</logic:notEqual>
			</logic:notEqual>
			<h3 class="subTituloContenido">DCP1A</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<logic:notEqual name="tipoAccion" value="<%=Constantes.IR_INSERTAR%>">
				<logic:equal name="tipoAccionBis" value="<%=Constantes.SI%>">
					<p class="ocultarCampo">
						Va a proceder a registrar la solicitud. Por favor, compruebe que
						los datos introducidos en la misma son correctos. Si alguno es
						err&oacute;neo deber&aacute; pulsar el bot&oacute;n Modificar. En
						caso contrario deber&aacute; pulsar el bot&oacute;n Registrar.<br />
						<br />
						<%--                  No olvide que esta solicitud deber&aacute; imprimirla y entregar personalmente en cualquier Registro Oficial dentro de los 15 dï¿½as siguientes a la publicaciï¿½n de la convocatoria de acceso. 
--%>
					</p>
				</logic:equal>
			</logic:notEqual>


			<fieldset>
				<legend class="tituloLegend"> Datos Personales </legend>
				<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>
				<div class="cuadroFieldset">
					<label for="idVApell1"> Apellidos: * <html:text
							property="DApellido1"
							styleClass="recuadroM colocaApellidosAlta textoNormal"
							maxlength="61" />
					</label> <label for="idVNombre"> Nombre: * <html:text
							property="DNombre"
							styleClass="recuadroM colocaNombreAlta textoNormal"
							maxlength="30" />
					</label> <br />
					<br /> <label for="idVDNI"> NIF/NIE: * <html:text
							name="OCAPSolicitudesForm" property="CDniReal"
							styleClass="recuadroM colocaDNIAlta textoNormal" maxlength="10" />
					</label>
					<html:hidden name="OCAPSolicitudesForm" property="CDni" />
					<label for="idVCorreo">Sexo: * <html:select
							styleClass="cbCuadros colocaSexoAlta" name="OCAPSolicitudesForm"
							property="BSexo">
							<html:option value="">Seleccione...</html:option>
							<html:option value="<%=Constantes.SEXO_HOMBRE_VALUE%>"><%=Constantes.SEXO_HOMBRE%></html:option>
							<html:option value="<%=Constantes.SEXO_MUJER_VALUE%>"><%=Constantes.SEXO_MUJER%></html:option>
						</html:select>
					</label> <br />
					<br /> <label for="idVTelefono">Tel&eacute;fono 1: *<html:text
							property="NTelefono1"
							styleClass="recuadroM colocaTelefono1AltaAsterisco textoNormal"
							maxlength="9" />
					</label> <label for="idVTelefono">Tel&eacute;fono 2: <html:text
							property="NTelefono2"
							styleClass="recuadroM colocaTelefono2Alta textoNormal"
							maxlength="9" />
					</label> <br />
					<br /> <label for="idVCorreo">Correo Electr&oacute;nico: *<html:text
							property="DCorreoelectronico"
							styleClass="recuadroM colocaCorreoAltaAsterisco textoNormal"
							maxlength="50" />
					</label> <br />
					<br /> <label for="idVCorreo">Domicilio, Calle o Plaza y
						N&ordm;: <html:text property="DDomicilio"
							styleClass="recuadroM colocaDireccionAlta textoNormal"
							maxlength="100" />
					</label> <br />
					<br /> <label for="idVProvincia"> Provincia: <html:select
							property="CProvinciaUsu_id" styleClass="cbCuadros colocaProvAlta"
							size="1" onchange="javascript:cargarComboLocalidades();">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_TODAS_PROVINCIAS%>"
								property="CProvinciaId" labelProperty="DProvincia" />
						</html:select>
					</label>
					<%if(((ArrayList) session.getAttribute(Constantes.COMBO_LOCALIDADES)).size() > 0 ){%>
					<br />
					<br /> <label for="idVLocalidades"> Localidad: <html:select
							property="DLocalidadUsu" styleClass="cbCuadros colocaLocAlta"
							size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_LOCALIDADES%>"
								property="DLocalidad" />
						</html:select>
					</label>
					<%}%>
					<label for="idVTelefono">C&oacute;digo Postal: <html:text
							property="CPostalUsu"
							styleClass="recuadroM colocaCPAlta textoNormal" maxlength="5" />
					</label>
				</div>
				<%}%>
				<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)){%>
				<html:hidden name="OCAPSolicitudesForm" property="DApellido1" />
				<html:hidden name="OCAPSolicitudesForm" property="DNombre" />
				<html:hidden name="OCAPSolicitudesForm" property="CDni" />
				<html:hidden name="OCAPSolicitudesForm" property="CDniReal" />
				<html:hidden name="OCAPSolicitudesForm" property="NTelefono1" />
				<html:hidden name="OCAPSolicitudesForm" property="NTelefono2" />
				<html:hidden name="OCAPSolicitudesForm"
					property="DCorreoelectronico" />
				<html:hidden name="OCAPSolicitudesForm" property="BSexo" />
				<html:hidden name="OCAPSolicitudesForm" property="DDomicilio" />
				<html:hidden name="OCAPSolicitudesForm" property="CProvinciaUsu_id" />
				<html:hidden name="OCAPSolicitudesForm" property="DProvinciaUsu" />
				<html:hidden name="OCAPSolicitudesForm" property="DLocalidadUsu" />
				<html:hidden name="OCAPSolicitudesForm" property="CPostalUsu" />
				<html:hidden name="OCAPNuevaSolicitudForm" property="bExisteSolicParaNifConv" />
				<div class="cuadroFieldset">
					<label for="idVApell1" class="colocaDatosVis"> Apellidos: <span><bean:write
								name="OCAPSolicitudesForm" property="DApellido1" /></span>
					</label> <label for="idVNombre" class="colocaDatosVis"> Nombre: <span><bean:write
								name="OCAPSolicitudesForm" property="DNombre" /></span>
					</label> <br />
					<br /> <label for="idVDNI" class="colocaDatosVis">
						NIF/NIE: <span><bean:write name="OCAPSolicitudesForm"
								property="CDniReal" /></span>
					</label> <label for="idVCorreo" class="colocaDatosVis"> Sexo: <logic:equal
							name="OCAPSolicitudesForm" property="BSexo" value="H">
							<span>Hombre</span>
						</logic:equal> <logic:equal name="OCAPSolicitudesForm" property="BSexo"
							value="M">
							<span>Mujer</span>
						</logic:equal>
					</label> <br />
					<br /> <label for="idVTelefono" class="colocaDatosVis">
						Tel&eacute;fono 1: <span><bean:write
								name="OCAPSolicitudesForm" property="NTelefono1" /></span>
					</label> <label for="idVTelefono" class="colocaDatosVis">
						Tel&eacute;fono 2: <span><bean:write
								name="OCAPSolicitudesForm" property="NTelefono2" /></span>
					</label> <br />
					<br /> <label for="idVCorreo" class="colocaDatosVis">
						Correo Electr&oacute;nico: <span><bean:write
								name="OCAPSolicitudesForm" property="DCorreoelectronico" /></span>
					</label> <br />
					<br /> <label for="idVCorreo" class="colocaDatosVisGrande">
						Domicilio, Calle o Plaza y N&ordm;: <span><bean:write
								name="OCAPSolicitudesForm" property="DDomicilio" /></span>
					</label> <br />
					<br /> <label for="idVProvincia" class="colocaDatosVis">
						Provincia: <span><bean:write name="OCAPSolicitudesForm"
								property="DProvinciaUsu" /></span>
					</label> <label for="idVLocalidades" class="colocaDatosVis">
						Localidad: <span><bean:write name="OCAPSolicitudesForm"
								property="DLocalidadUsu" /></span>
					</label> <br />
					<br /> <label for="idVTelefono" class="colocaDatosVis">C&oacute;digo
						Postal: <span><bean:write name="OCAPSolicitudesForm"
								property="CPostalUsu" /></span>
					</label>
				</div>
				<%}%>
			</fieldset>

			<fieldset>
				<legend class="tituloLegend"> Datos Profesionales Actuales
				</legend>
				<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>
				<div class="cuadroFieldset">
					<label for="idVCategoria">R&eacute;gimen jur&iacute;dico: *
						<html:radio name="OCAPSolicitudesForm" property="CJuridico"
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
						Administrativa: * <html:radio name="OCAPSolicitudesForm"
							property="CSitAdministrativaAuxId" styleClass="opcionRadioAlta"
							value="<%=Constantes.C_SIT_ADM_AUX_ACTIVO_COD%>"
							onclick="javascript:mostrarDonde();" /><%=Constantes.C_SIT_ADM_AUX_ACTIVO%>
						<html:radio name="OCAPSolicitudesForm"
							property="CSitAdministrativaAuxId" styleClass="opcionRadioAlta"
							value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>"
							onclick="javascript:mostrarDonde();" /><%=Constantes.C_SIT_ADM_AUX_OTROS%>
						<span id="serviciosDonde"
						style="display: none; visibility: hidden;"> <html:text
								name="OCAPSolicitudesForm" styleClass="cuadroDondeAlta"
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
							<html:options collection="<%=Constantes.COMBO_CATEGORIA_ACTUAL%>"
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
					<!-- PROVINCIA, TIPO GERENCIA Y GERENCIA -> PARA LA VISTA DE LA GESTIï¿½N PERIFï¿½RICA-->
					<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIOS) ||
                  jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE))){%>
					<label for="idVProvincia"> Provincia: <html:select
							property="CProvincia_id"
							styleClass="cbCuadros colocaProvinciaAlta" size="1"
							onchange="javascript:cargarComboGerencias();" disabled="true">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_PROVINCIAS%>"
								property="CProvinciaId" labelProperty="DProvincia" />
						</html:select> <html:hidden property="CProvincia_id" />
					</label> <label for="idVCorreo" class="colocaDatoLocAlta">
						Localidad: <span><bean:write name="OCAPSolicitudesForm"
								property="ACiudad" /></span>
					</label>
					<html:hidden name="OCAPSolicitudesForm" property="ACiudad" />
					<br />
					<br /> <label for="idVProvincia"> Tipo Gerencia: <html:select
							property="CTipogerencia_id"
							styleClass="cbCuadros colocaTipoGerenciaAlta" size="1"
							onchange="javascript:cargarComboGerencias();" disabled="true">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_TIPOS_GERENCIAS%>"
								property="CTipogerenciaId" labelProperty="DNombre" />
						</html:select> <html:hidden property="CTipogerencia_id" />
					</label> <br />
					<br /> <label for="idVGerencia"> Gerencia: <html:select
							property="CGerencia_id" styleClass="cbCuadros colocaGerenciaAlta"
							size="1" onchange="javascript:cargarComboCentroTrabajo();"
							disabled="true">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
								property="CGerenciaId" labelProperty="DNombre" />
						</html:select> <html:hidden property="CGerencia_id" />
					</label> <br />
					<br />
					<!-- PROVINCIA, TIPO GERENCIA Y GERENCIA -> PARA LA VISTA DEL ADMINISTRADOR-->
					<%}else if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR)){%>
					<label for="idVProvincia"> Provincia: <html:select
							property="CProvincia_id"
							styleClass="cbCuadros colocaProvinciaCBDt" size="1"
							onchange="javascript:cargarComboGerencias();">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_PROVINCIAS%>"
								property="CProvinciaId" labelProperty="DProvincia" />
						</html:select>
					</label> <label for="idVCorreo" class="colocaDatosVis"> Localidad:
						<span><bean:write name="OCAPSolicitudesForm"
								property="ACiudad" /></span>
					</label>
					<html:hidden name="OCAPSolicitudesForm" property="ACiudad" />
					<br />
					<br /> <label for="idVProvincia"> Tipo Gerencia: <html:select
							property="CTipogerencia_id"
							styleClass="cbCuadros colocaTipoGerenciaCBDt" size="1"
							onchange="javascript:cargarComboGerencias();">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_TIPOS_GERENCIAS%>"
								property="CTipogerenciaId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br /> <label for="idVGerencia"> Gerencia: <html:select
							property="CGerencia_id" styleClass="cbCuadros colocaGerenciaCBDt"
							size="1" onchange="javascript:cargarComboCentroTrabajo();">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
								property="CGerenciaId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br />
					<%}%>
					<label for="idVCentroTrabajo">Centro de Trabajo: * <html:select
							property="CCentrotrabajo_id"
							styleClass="cbCuadros colocaCentroAlta" size="1"
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
				<%}%>
				<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)){%>
				<html:hidden property="CProcedimientoId" />
				<html:hidden property="CSitAdministrativaAuxId" />
				<html:hidden property="DSitAdministrativaAuxOtros" />
				<html:hidden property="CEstatutarioActualId" />
				<html:hidden property="CProfesionalActual_id" />
				<html:hidden property="CEspecActual_id" />
				<html:hidden property="CProvincia_id" />
				<html:hidden property="CTipogerencia_id" />
				<html:hidden property="CGerencia_id" />
				<html:hidden property="CCentrotrabajo_id" />
				<html:hidden property="DSitAdministrativa_nombre" />
				<html:hidden property="DEstatutario_nombre" />
				<html:hidden property="DProfesional_nombre" />
				<html:hidden property="DEspec_nombre" />
				<html:hidden property="DProvincia" />
				<html:hidden property="DTipogerencia_desc" />
				<html:hidden property="DGerencia_nombre" />
				<html:hidden property="DCentrotrabajo_nombre" />
				<html:hidden property="ACiudad" />
				<html:hidden property="BOtroServicio" />
				<html:hidden property="ADondeServicio" />
				<html:hidden property="CJuridico" />
				<div class="cuadroFieldset">
					<label for="idVCategoria">R&eacute;gimen jur&iacute;dico: <html:radio
							name="OCAPSolicitudesForm" property="CJuridico"
							styleClass="opcionRadioAlta"
							value="<%=Constantes.C_JURIDICO_ESTATUTARIO_COD%>"
							disabled="true" /><%=Constantes.C_JURIDICO_ESTATUTARIO%> <html:radio
							name="OCAPSolicitudesForm" property="CJuridico"
							styleClass="opcionRadioAlta"
							value="<%=Constantes.C_JURIDICO_FUNCIONARIO_COD%>"
							disabled="true" /><%=Constantes.C_JURIDICO_FUNCIONARIO%> <html:radio
							name="OCAPSolicitudesForm" property="CJuridico"
							styleClass="opcionRadioAlta"
							value="<%=Constantes.C_JURIDICO_OTROS_COD%>" disabled="true" /><%=Constantes.C_JURIDICO_OTROS%>
					</label> <br />
					<br /> <label for="idVCategoria">Situaci&oacute;n
						Administrativa: <html:radio name="OCAPSolicitudesForm"
							property="CSitAdministrativaAuxId" styleClass="opcionRadioAlta"
							value="<%=Constantes.C_SIT_ADM_AUX_ACTIVO_COD%>" disabled="true" /><%=Constantes.C_SIT_ADM_AUX_ACTIVO%>
						<html:radio name="OCAPSolicitudesForm"
							property="CSitAdministrativaAuxId" styleClass="opcionRadioAlta"
							value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>" disabled="true" /><%=Constantes.C_SIT_ADM_AUX_OTROS%>
						<logic:equal name="OCAPSolicitudesForm"
							property="CSitAdministrativaAuxId"
							value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>">
							<span id="serviciosDonde"> <html:text
									name="OCAPSolicitudesForm" styleClass="cuadroDondeAlta"
									property="DSitAdministrativaAuxOtros" maxlength="50"
									readonly="true" />
							</span>
						</logic:equal>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">Personal:
						<span> <logic:notEmpty name="OCAPSolicitudesForm"
								property="DEstatutarioActual_nombre">
								<bean:write name="OCAPSolicitudesForm"
									property="DEstatutarioActual_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DEstatutarioActual_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">
						Categor&iacute;a: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DProfesionalActual_nombre">
								<bean:write name="OCAPSolicitudesForm"
									property="DProfesionalActual_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DProfesionalActual_nombre">-</logic:empty>
					</span>
					</label><br />
					<br /> <label for="idVEspecialidad" class="colocaDatosVisGrande">
						Especialidad: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DEspecActual_nombre">
								<bean:write name="OCAPSolicitudesForm"
									property="DEspecActual_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DEspecActual_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br />
					<!-- PROVINCIA, TIPO GERENCIA Y GERENCIA -> PARA LA VISTA DE LA GESTIï¿½N PERIFï¿½RICA-->
					<label for="idVProvincia" class="colocaDatosVis">
						Provincia: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DProvincia">
								<bean:write name="OCAPSolicitudesForm" property="DProvincia" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm" property="DProvincia">-</logic:empty>
					</span>
					</label> <label for="idVCorreo" class="colocaDatosVis"> Localidad:
						<span><bean:write name="OCAPSolicitudesForm"
								property="ACiudad" /></span>
					</label> <br />
					<br /> <label for="idVProvincia" class="colocaDatosVis">
						Tipo Gerencia: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DTipogerencia_desc">
								<bean:write name="OCAPSolicitudesForm"
									property="DTipogerencia_desc" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DTipogerencia_desc">-</logic:empty>
					</span>
					</label> <label for="idVGerencia" class="colocaDatosVis"> Gerencia:
						<span> <logic:notEmpty name="OCAPSolicitudesForm"
								property="DGerencia_nombre">
								<bean:write name="OCAPSolicitudesForm"
									property="DGerencia_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DGerencia_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br /> <label for="idVCentroTrabajo" class="colocaDatosVisGrande">
						Centro de Trabajo: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DCentrotrabajo_nombre">
								<bean:write name="OCAPSolicitudesForm"
									property="DCentrotrabajo_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DCentrotrabajo_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">Procedimiento
						de evaluaci&oacute;n por el que opta: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DSitAdministrativa_nombre">
								<bean:write name="OCAPSolicitudesForm"
									property="DSitAdministrativa_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DSitAdministrativa_nombre">-</logic:empty>
					</span>
					</label> <br />
				</div>
				<%}%>
			</fieldset>

			<fieldset>
				<legend class="tituloLegend"> Datos por los que opta a
					Carrera Profesional </legend>
				<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>
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
					<!--
                  <div class="fechaGradoAlta">
                     <span> Fecha de realizaci&oacute;n y env&iacute;o de solicitud: </span>
                     <html:hidden property="FRegistro_solic"/>
                     <bean:write name="OCAPSolicitudesForm" property="FRegistro_solic" />
                  </div>
                  -->
					<html:hidden name="OCAPSolicitudesForm" property="FRegistro_solic" />
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
				<%}%>
				<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)){%>
				<html:hidden property="CGrado_id" />
				<html:hidden property="FRegistro_solic" />
				<html:hidden property="DGrado_des" />
				<html:hidden property="CEstatutarioId" />
				<html:hidden property="CProfesional_id" />
				<html:hidden property="CEspec_id" />
				<div class="cuadroFieldset">
					<label for="idVGrado"> Grado: <span class="textoDatos"><bean:write
								name="OCAPSolicitudesForm" property="DGrado_des" /></span>
					</label>
					<html:hidden property="CConvocatoriaId" />
					<html:hidden property="FFinRegistro" />
					<html:hidden property="FRegistro_oficial" />
					<!--
                  <div class="fechaGradoAlta">
                     <span> Fecha de realizaci&oacute;n y env&iacute;o de solicitud: </span>
                     <span class="textoDatos"><bean:write name="OCAPSolicitudesForm" property="FRegistro_solic" /></span>
                  </div>
                  -->
					<html:hidden name="OCAPSolicitudesForm" property="FRegistro_solic" />
					<div class="fechaGradoAltaConf">
						<span>Fecha de registro oficial: </span> <span class="textoDatos"><bean:write
								name="OCAPSolicitudesForm" property="FRegistro_oficial" /></span>
					</div>
					<br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">Personal:
						<span> <logic:notEmpty name="OCAPSolicitudesForm"
								property="DEstatutario_nombre">
								<bean:write name="OCAPSolicitudesForm"
									property="DEstatutario_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DEstatutario_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">
						Categor&iacute;a: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DProfesional_nombre">
								<bean:write name="OCAPSolicitudesForm"
									property="DProfesional_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DProfesional_nombre">-</logic:empty>
					</span>
					</label><br />
					<br /> <label for="idVEspecialidad" class="colocaDatosVis">
						Especialidad: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DEspec_nombre">
								<bean:write name="OCAPSolicitudesForm" property="DEspec_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm" property="DEspec_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br />
				</div>
				<%}%>
			</fieldset>

			<fieldset>
				<legend class="tituloLegend"> Datos de Servicios Prestados
				</legend>
				<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>
				<div class="cuadroFieldset altaUsuario">
					<label for="idVAnt"> <span class="colocaNumAlta">A&ntilde;os
							de ejercicio en la categor&iacute;a profesional por la que se
							accede: * </span> <br />
					<br /> <label>N&ordm; A&ntilde;os: <html:text
								property="NAniosantiguedad"
								styleClass="recuadroM colocaAniosAlta textoNormal" maxlength="2" /></label>
						<label>N&ordm; Meses: <html:text
								property="NMesesantiguedad"
								styleClass="recuadroM colocaMesesAlta textoNormal" maxlength="2" /></label>
						<label>N&ordm; D&iacute;as: <html:text
								property="NDiasantiguedad"
								styleClass="recuadroM colocaDiasAlta textoNormal" maxlength="2" /></label>
					</label> <br />
					<br />
					<!--
                  <label for="idVPlaza">
                     Fecha en la que obtiene la plaza en propiedad: *
                     <html:text property="FPlazapropiedad" styleClass="recuadroM colocaPlaza textoNormal" maxlength="10" />
                     <a id ="calFPlaza" href='javascript:show_Calendario("OCAPSolicitudesForm", "FPlazapropiedad", document.forms[0].FPlazapropiedad.value);'><html:img styleClass="imagencalendarioAlta" src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
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
							<html:options collection="<%=Constantes.COMBO_TODAS_PROVINCIAS%>"
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
					<br /> <label for="idVNAnnios"> Fecha Inicio: (dd/mm/aaaa)
						<html:text property="FInicio" maxlength="10"
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
								type="button" id="eliminar" value="Eliminar" property="eliminar"
								onclick="combos_borrar(document.getElementById('jspCadenaCentro'));" />
							</span> <span class="derBoton"></span>
						</div>
					</div>

					<select size="5" id="jspCadenaCentro" name="jspCadenaCentro"
						class="textoTituloGrisAlta"></select>
					<html:hidden property="resumenCentros" />
					<script language="javascript">inicializarCentros();</script>
				</div>
				<%}%>
				<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)){%>
				<html:hidden property="NAniosantiguedad" />
				<html:hidden property="NMesesantiguedad" />
				<html:hidden property="NDiasantiguedad" />
				<html:hidden property="FPlazapropiedad" />
				<div class="cuadroFieldset">
					<label for="idVAnt"> <span class="colocaNumAlta">A&ntilde;os
							de ejercicio en la categor&iacute;a profesional por la que se
							accede:</span> <br /> <label>N&ordm; A&ntilde;os: <span
							class="textoDatos"><bean:write name="OCAPSolicitudesForm"
									property="NAniosantiguedad" /></span></label> <label>N&ordm; Meses: <span
							class="textoDatos"><bean:write name="OCAPSolicitudesForm"
									property="NMesesantiguedad" /></span></label> <label>N&ordm;
							D&iacute;as: <span class="textoDatos"><bean:write
									name="OCAPSolicitudesForm" property="NDiasantiguedad" /></span>
					</label>
					</label> <br />
					<br />
					<!--
                  <label for="idVPlaza">
                     Fecha en la que obtiene la plaza en propiedad:
                     <span class="textoDatos"><bean:write name="OCAPSolicitudesForm" property="FPlazapropiedad" /></span>
                  </label>
                  <br /><br />
                  -->
					<label for="idVServicios"> Tiene servicios prestados en
						otro centro de trabajo distinto al actual: </label><br /> <select
						size="5" id="jspCadenaCentro" name="jspCadenaCentro"
						class="textoTituloGrisAlta2" readonly="true"></select>
					<html:hidden property="resumenCentros" />
					<script language="javascript">inicializarCentros();</script>
				</div>
				<%}%>
			</fieldset>

			<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)){%>
			<fieldset>
				<legend class="tituloLegend"> Solicitantes existentes </legend>

				<bean:size id="tamano" name="OCAPSolicitudesForm"
					property="listaUsuarios" />

				<logic:notEqual name="tamano" value="0">
					<logic:iterate id="listaTotal" name="OCAPSolicitudesForm"
						property="listaUsuarios">
						<div class="lineaBajaM"></div>
						<table>
							<tr class="datosAsp">
								<td><bean:write name="listaTotal" property="DNombre" /></td>
								<td><bean:write name="listaTotal" property="DApellido1" /></td>
								<td><a
									href="javascript:asociar(<bean:write name="listaTotal" property="CUsrId"/>);"><img
										src="imagenes/imagenes_ocap/icono_registrar.gif" alt="Asociar solicitante" /></a></td>
							</tr>
						</table>
					</logic:iterate>
				</logic:notEqual>
				<logic:equal name="tamano" value="0">
					<br />
					<tr class="datosAsp">
						<td>Nuevo solicitante</td>
						<td><a href="javascript:nuevoUsuario();"><img
							src="imagenes/imagenes_ocap/icono_registrar.gif" alt="Asociar solicitante" /></a></td>
					</tr>
				</logic:equal>
			</fieldset>
			<%}%>

			<logic:equal name="tipoAccion" value="<%=Constantes.IR_INSERTAR%>">
				<p>Los campos se&ntilde;alados con * son obligatorios</p>
				<p>En el campo Fecha de registro oficial deber&aacute;
					introducir, en el formato indicado, la fecha en la que la solicitud
					fue recepcionada y registrada en el registro oficial
					correspondiente.</p>
			</logic:equal>

			<div class="espaciador"></div>
			<%--
         <logic:equal name="tipoAccion" value="<%=Constantes.IR_INSERTAR%>" >
         <logic:notEqual name="tipoAccionBis" value="<%=Constantes.SI%>" >                          
            <h3> Constancia de Informaci&oacute;n </h3>
               <div id="informacionNoImprimible" style="display:;visibility:visible">
                  <p>
                     <%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>
                        El procedimiento para el reconocimiento individual del grado se inicia con la entrega de la solicitud  del interesado en cualquier Registro Oficial dentro del plazo establecido en la convocatoria.
                     <%} else { %>
                        El procedimiento para el reconocimiento individual del grado se inicia con la entrega de la solicitud  del interesado en cualquier Registro Oficial dentro de <bean:write name="OCAPSolicitudesForm" property="NDiasPresentarSolicitud" /> a la publicaci&oacute;n de la convocatoria de acceso.
                     <% } %>
                  </p>
                  <p>
                     La solicitud dar&aacute; lugar a la apertura del proceso para acceder a reconocimiento de grado, siempre y cuando el interesado cumpla con los requisitos iniciales:
                  </p>	
                  <ul>
                     <li class="listadoP"> Antig&uuml;edad requerida en el Sistema Nacional de Salud. </li>
                     <li class="listadoP"> Plaza en propiedad </li>
                  </ul>
                  <p>
                     Si se acepta la solicitud, recibir&aacute; a trav&eacute;s de correo electr&oacute;nico y correo interno la notificaci&oacute;n de aceptaci&oacute;n de dicha solicitud.
                  </p>        
               </div>
               
               <div class="espaciador"></div>
               <div class="recuadroM">
                  <div class="cuadroFieldsetLOPD">
                     <p class="textoNormal">
                        De conformidad con lo establecido en la ley Orgánica 3/2018, del 5 de Diciembre de Protección de Datos de Personales y Garantía de los Derechos Digitales, le informamos que sus datos quedarï¿½n incorporados en un fichero automatizado legalmente inscrito en el Registro General de Protecciï¿½n de Datos de la Agencia Espaï¿½ola de Protecciï¿½n de Datos cuyo responsable es la Gerencia Regional de Salud (en adelante GRS).
                        <br /><br />
                        La GRS garantiza la adopción de medidas oportunas para asegurar el tratamiento confidencial de dichos datos. 
                        <br /><br />
                        Se autoriza con el presente apartado la inclusión de datos en el fichero. En caso de negarse a comunicar datos sería imposible la prestación de dicho servicio. 
                        <br /><br />
                        <div class="textoCentrado">
                           <input type="checkbox" name="acepto" value="N" onclick="activarAceptar()" /> 
                           <span class="textoNegrita textoColorM"> Est&aacute; de acuerdo </span>
                           <div class="espaciadorPeq"></div>
                        </div>
                     </p>
                  </div>
               </div>
         </logic:notEqual>
         </logic:equal>
         
         <div id="informacionImprimible" class="ocultarDatos">
            <p>
               Certifico que los datos consignados son ciertos.
            </p>  
            <p>
               Firma:
            </p>
            
            <div class="espaciador"></div>
            
            <p>
               A la atenci&oacute;n de: <bean:write name="OCAPSolicitudesForm" property="DGerencia_nombre" />
            </p>
         </div>
--%>
			<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>
			<%-- <div id="ocultarAceptar" style="display: none; visibility: hidden;"> --%>
			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:aceptarSolicitud();"> <img
							src="imagenes/imagenes_ocap/flecha_correcto.gif"
							alt="Aceptar Solicitud" /> <span> Aceptar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:enviar('PaginaInicio.do');"> <img
							src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" /> <span>
								Cancelar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>
			<%-- </div> --%>
			<%}%>
			<%if ( ((String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE))
            &&  ((String)request.getAttribute("tipoAccionBis") != null && ((String)request.getAttribute("tipoAccionBis")).equals(Constantes.SI))){%>
			<div class="botonesPagina">
				<!--
            <div class="botonAccion">
               <span class="izqBoton"></span>
               <span class="cenBoton">
                  <a href="javascript:registrarSolicitud('<%=Constantes.SI%>');">
                     <img src="imagenes/imagenes_ocap/flecha_correcto.gif" alt="Registrar Solicitud y Gestionar su continuidad" />
                     <span> Registrar y Gestionar</span>
                  </a>
               </span>
               <span class="derBoton"></span>
            </div>
            -->
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:registrarSolicitud('<%=Constantes.NO%>');"> <img
							src="imagenes/imagenes_ocap/flecha_correcto.gif"
							alt="Registrar Solicitud" /> <span> Registrar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:irAltaSolicitud();"> <img
							src="imagenes/imagenes_ocap/aspa_roja.gif"
							alt="Modificar Solicitud" /> <span> Modificar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>
			<%}%>
		</html:form>

	</div>
	<!-- /fin de ContenidoDCP1A_PGP -->
</div>
<!-- /Fin de Contenido -->

<logic:equal name="tipoAccion" value="<%=Constantes.IR_INSERTAR%>">
	</div>
	<!-- /Fin de ocultarCampo -->
	<script language="javascript">mostrarDonde();</script>
</logic:equal>