<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.modalidadAnterior.OCAPModalidadAnteriorOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<body onload="construirCombos();">

	<%
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="JavaScript" type="text/javascript"
src="javascript/XHConn.js"></script>
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

function borrarForm() {
   //document.getElementById("OCAPNuevaSolicitudForm").reset();
   document.forms[0].reset();
   var e = document.getElementsByTagName("input");
      for(var i=0;i<e.length;i++){
         if (e[i].type == 'hidden'){
            e[i].value='';
         }
      }
}

$(document).ready(function() {
	construirCombos();
});

function construirCombos(){
   <%if((String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)) {%>
         mostrarDonde('S');
         mostrarRegimen('S');
         
         //document.getElementById('CProfesional_id').disabled=true;
         //document.getElementById('CEspec_id').disabled=true;
         
         if(document.forms[0].CJuridicoId.value=='1'){
            document.getElementById('CProfesional_id').style.display='none';
            document.getElementById('CProfesional_id').style.visibility='hidden';
      
            document.getElementById('CProfesionalFijo_id').style.display='';
            document.getElementById('CProfesionalFijo_id').style.visibility='visible';
            
            document.getElementById('CEspec_id').style.display='none';
            document.getElementById('CEspec_id').style.visibility='hidden';
      
            document.getElementById('CEspecFijo_id').style.display='';
            document.getElementById('CEspecFijo_id').style.visibility='visible';
            
            document.getElementById('cajaEstatutarioFijo').style.display='';
      		document.getElementById('cajaEstatutarioFijo').style.visibility='visible';
      
         }else{
            document.getElementById('CProfesional_id').style.display='';
            document.getElementById('CProfesional_id').style.visibility='visible';
      
            document.getElementById('CProfesionalFijo_id').style.display='none';
            document.getElementById('CProfesionalFijo_id').style.visibility='hidden';         
            
            document.getElementById('CEspec_id').style.display='';
            document.getElementById('CEspec_id').style.visibility='visible';
      
            document.getElementById('CEspecFijo_id').style.display='none';
            document.getElementById('CEspecFijo_id').style.visibility='hidden';
            
            document.getElementById('cajaEstatutarioFijo').style.display='none';
      		document.getElementById('cajaEstatutarioFijo').style.visibility='hidden';     
         }
  
         if(document.getElementById('CProfesional_id').value!='' && document.getElementById('CEspec_id').value ==''){
            document.getElementById('CEspec_id').disabled = true;
         }

         if(document.getElementById('CProfesionalFijo_id').value!='' && document.getElementById('CEspecFijo_id').value ==''){
            document.getElementById('CEspecFijo_id').disabled = true;
         }
  
    <% } %>
}

function deshabilitar() {
   if(document.forms[0].CJuridicoId.value==''){
         document.getElementById('CProfesional_id').disabled=true;
         document.getElementById('CEspec_id').disabled=true;
   }
   if(document.forms[0].CJuridicoId.value=='1'){
         document.getElementById('CProfesional_id').value='';
         document.getElementById('CEspec_id').value='';
         document.getElementById('CProfesional_id').disabled=true;
         document.getElementById('CEspec_id').disabled=true;
   }
}

function deshabilitarGCT() {
   // Deshabilita gerencia y centro de trabajo en caso de que se elija 'Seleccione' en combo Provincia de la Carrera Profesional
   if(document.forms[0].CProvinciaCarrera_id.value=='') {
      document.getElementById('CGerencia_id').disabled=true;
      document.getElementById('CCentrotrabajo_id').disabled=true;
   }
}

function deshabilitarGCTActual() {
   // Deshabilita gerencia y centro de trabajo en caso de que se elija 'Seleccione' en combo Provincia de la Carrera Profesional
   if(document.forms[0].CProvCarreraActualId.value=='') {
      document.getElementById('CGerenciaActualId').disabled=true;
      document.getElementById('CCentroTrabActualId').disabled=true;
   }
}

function deshabilitarLocalidad() {
   if(document.forms[0].CProvincia_id.value==''){
         document.getElementById('CLocalidad_id').disabled=true;
   }
}

function commonTemplate(item) {
   return "<option value='" + item.Value + "'>" + item.Text + "</option>";
}

function commonMatch(selectedValue) {
   return this.When == selectedValue;
}

    $(document).ready(function () {
      $("#CProfesionalFijo_id").cascade("#CJuridicoCombo",{
        ajax: {
            url: 'OCAPNuevaSolicitud.do?accion=cargarCategoriasEstaturioFijo'
        },
        template: commonTemplate,
        match: commonMatch
      })      
    })

    $(document).ready(function () {
    <%if ((String)request.getAttribute("tipoAccion") != null && ((((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)) || (((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)))){%>
if(document.getElementById('CProfesional_id').value!='' && document.getElementById('CEspec_id').value ==''){
            document.getElementById('CEspec_id').disabled = true;
         }
<% } %>
      $("#CProfesional_id").cascade("#CJuridicoId",{        
        ajax: {
            url: 'OCAPNuevaSolicitud.do?accion=cargarCategorias'
        },
        template: commonTemplate,
        match: commonMatch
      })

      $("#CEspec_id").cascade("#CProfesional_id",{
        ajax: {
            url: 'OCAPNuevaSolicitud.do?accion=cargarEspecialidades'
        },
        template: commonTemplate,
        match: commonMatch
      })
    })

    $(document).ready(function () {

      $("#CEspecFijo_id").cascade("#CProfesionalFijo_id",{
        ajax: {
            url: 'OCAPNuevaSolicitud.do?accion=cargarEspecialidadesEstatutarioFijo'
        },
        template: commonTemplate,
        match: commonMatch
      })
    })
    

//  $(document).ready(function () {
//
//      $("#CLocalidad_id").cascade("#CProvincia_id",{
//        ajax: {
//            url: 'OCAPNuevaSolicitud.do?accion=cargarLocalidadesMod'
//        },
//        template: commonTemplate,
//        match: commonMatch
//      })
//    })

    //OCAP-641
    $(document).ready(function () {
				$('#CProvincia_id').change(function() {
				ajaxLocalidad();
				  //$.ajax({
                	//url: 'OCAPNuevaSolicitud.do?accion=cargarLocalidadesAjax',
                	//data: "val=" + $('#CProvincia_id').val(),
                	//success: function(html) {
	                 //  $("#CLocalidad_id").html(html);
                	//},
                	//error: function (e) {
	                //    alert('Error Received: ' + e);
    	            //  },
        		    //});
				});
    })


    $(document).ready(function () {

      $("#CGerencia_id").cascade("#CProvinciaCarrera_id",{
        ajax: {
            url: 'OCAPNuevaSolicitud.do?accion=cargarGerencias'
        },
        template: commonTemplate,
        match: commonMatch
      })
    })

    $(document).ready(function () {

      $("#CCentrotrabajo_id").cascade("#CGerencia_id",{
        ajax: {
            url: 'OCAPNuevaSolicitud.do?accion=cargarCentroTrabajo'
        },
        template: commonTemplate,
        match: commonMatch
      })
    })
    
    $(document).ready(function () {

      $("#CGerenciaActualId").cascade("#CProvCarreraActualId",{
        ajax: {
            url: 'OCAPNuevaSolicitud.do?accion=cargarGerencias'
        },
        template: commonTemplate,
        match: commonMatch
      })
    })

    $(document).ready(function () {

      $("#CCentroTrabActualId").cascade("#CGerenciaActualId",{
        ajax: {
            url: 'OCAPNuevaSolicitud.do?accion=cargarCentroTrabajo'
        },
        template: commonTemplate,
        match: commonMatch
      })
    })

   var listaGradosJS = new Array();
   
   
//OCAP-641
	function ajaxLocalidad(){
		var ajax = new XHConn();	
		var result;
		if (!ajax) alert('Se requiere tener activado Ajax. Utilice un navegador moderno (Firefox o Chrome)');
		var fnWhenDone = function (oXML) {	
			var resultado  = oXML.responseText;		
			document.getElementById('CLocalidad_id').innerHTML = resultado;
		} 	
		ajax.connect("OCAPNuevaSolicitud.do?accion=cargarLocalidadesAjax&codProvincia="+document.forms[0].CProvincia_id.value, "POST", "", fnWhenDone, false);
		return result;
	}
   
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

//comprobar long max de textareas
function longMaxObservaciones(campo) {
   if(campo.value.length <= LONG_MAX_DOBSERVACIONES) {
      document.getElementById("longObserv").innerHTML=campo.value.length;
      return true;
   } else {
      campo.value = campo.value.substring(0,LONG_MAX_DOBSERVACIONES);
      document.getElementById("longObserv").innerHTML=campo.value.length;
      return false;
   }
}

function obtenerAniosGrado(){
   var longListaGrados = listaGradosJS.length;
   for (i=0; i < longListaGrados; i++) {
      if (document.forms[0].CGrado_id.value == listaGradosJS[i][0]) {
         document.forms[0].NAniosejercicio.value = listaGradosJS[i][1];
         //document.forms[0].CConvocatoriaId.value = listaGradosJS[i][2];
         document.forms[0].FFinRegistro.value = listaGradosJS[i][3];
      }
   }
}

function obtenerGradoModAnterior(){
   var longListaGradosAnt = listaGradosAnterioresJS.length;
   for (i=0; i < longListaGradosAnt; i++) {
      if (document.forms[0].CModAnteriorId.value == listaGradosAnterioresJS[i][0]) {
         document.forms[0].CGradoAnteriorId.value = listaGradosAnterioresJS[i][1];
      }
   }
}

function registrarSolicitud(irDetalle){
   document.forms[0].resumenCentros.value = valorCombo(document.getElementById('jspCadenaCentro'))
   if(document.forms[0].bExisteSolicParaNifConv.value=="true"){
	   var confirmar =
			confirm('Existe una solicitud registrada para este NIF/NIE y convocatoria. ¿Desea sustituirla por la actual?');
	   if(confirmar){
		   enviar('OCAPNuevaSolicitud.do?accion=insertar&irDetalle='+irDetalle);
	   } else {
		   alert('La solicitud actual NO ha sido grabada.')
	   }
	} else {
	   enviar('OCAPNuevaSolicitud.do?accion=insertar&irDetalle='+irDetalle);
   }
}

function irAltaSolicitud(){
   enviar('OCAPNuevaSolicitud.do?accion=irInsertar');
}

function modificarSolicitud(){
   if(validarFormularioModif(document.forms[0],'')) {
      var aniosAnt = obtenerCifra(document.forms[0].NAniosantiguedad.value);
      var aniosEjer = obtenerCifra(document.forms[0].NAniosejercicio.value);
      document.forms[0].resumenCentros.value=valorCombo(document.getElementById('jspCadenaCentro'));
      var mensaje ='';
      // No cumple la condicion de años minimos de antiguedad
      if(parseInt(aniosAnt) < parseInt(aniosEjer))
         mensaje ="El n\u00famero de a\u00f1os elegido es inferior al m\u00ednimo.\n";
      //El grado que solicita no se corresponde con el siguiente al que tiene
      if( (parseInt(document.forms[0].CGradoAnteriorId.value)+1) != parseInt(document.forms[0].CGrado_id.value))
         mensaje += "El grado que posee no es correcto para el grado que solicita.\n";
      if (mensaje != '') {
         mensaje +="\u00BFest\u00e1 seguro de querer modificar la solicitud?";
         if(confirm(mensaje)){
            enviar('OCAPNuevaSolicitud.do?accion=modificar'); 
         }
      }else{
         enviar('OCAPNuevaSolicitud.do?accion=modificar'); 
      }
   }
}

function ayuda(){ 
    str = '<%=request.getContextPath()%>/OCAPNuevaSolicitud.do';
    str = str + '?accion=ayuda';
    lanzar(str,'Ayuda',502,376,150,150,1,1)
}

function mostrarDonde(param) {
   if(document.forms[0].CSitAdministrativaId.value=='6'){
      document.getElementById('serviciosDonde').style.display='';
      document.getElementById('serviciosDonde').style.visibility='visible';
      if(param=='N'){
         document.forms[0].DSitAdministrativaOtros.value='';      
      }
   }else{
      document.getElementById('serviciosDonde').style.display='none';
      document.getElementById('serviciosDonde').style.visibility='hidden';
   }
}

function mostrarRegimen(param) {
   // El parametro es para borrar el campo solamente cuando el usuario esta en la pantalla y no al hacer el onload
   if(document.forms[0].CJuridicoId.value=='3'){
      document.getElementById('dRegimenOtros').style.display='';
      document.getElementById('dRegimenOtros').style.visibility='visible';
      if(param=='N'){
         document.forms[0].DRegimenJuridicoOtros.value='';
      }
   }else{
      document.getElementById('dRegimenOtros').style.display='none';
      document.getElementById('dRegimenOtros').style.visibility='hidden';
   }

   if(document.forms[0].CJuridicoId.value=='1'){
      document.getElementById('cajaEstatutarioFijo').style.display='';
      document.getElementById('cajaEstatutarioFijo').style.visibility='visible';
      if(param=='N'){
         document.forms[0].CJuridicoCombo.value='';
      }
   }else{
      document.getElementById('cajaEstatutarioFijo').style.display='none';
      document.getElementById('cajaEstatutarioFijo').style.visibility='hidden';
   }
}

function mostrarProfesional() {
      document.getElementById('CProfesional_id').style.display='';
      document.getElementById('CProfesional_id').style.visibility='visible';

      document.getElementById('CProfesionalFijo_id').style.display='none';
      document.getElementById('CProfesionalFijo_id').style.visibility='hidden';

      document.getElementById('CEspec_id').style.display='';
      document.getElementById('CEspec_id').style.visibility='visible';

      document.getElementById('CEspecFijo_id').style.display='none';
      document.getElementById('CEspecFijo_id').style.visibility='hidden';
}

function mostrarProfesionalFijo() {
      document.getElementById('CProfesional_id').style.display='none';
      document.getElementById('CProfesional_id').style.visibility='hidden';

      document.getElementById('CProfesionalFijo_id').style.display='';
      document.getElementById('CProfesionalFijo_id').style.visibility='visible';

      document.getElementById('CEspec_id').style.display='none';
      document.getElementById('CEspec_id').style.visibility='hidden';

      document.getElementById('CEspecFijo_id').style.display='';
      document.getElementById('CEspecFijo_id').style.visibility='visible';
}

function generarPDF(){
   enviar('OCAPNuevaSolicitud.do?accion=generarPDF&jspAccion=OCAPSolicitudesAl');
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
                  formu.ACategoria.value +'$'+ formu.AVinculo.value +'$'+ 
                  formu.FInicio.value +'$'+ formu.FFin.value;
      var texto = formu.DNombreCentro.value;
      nueva_opcion(comboCentros,texto,valor);
      limpiarCamposCentros();
   }
} //Fin anhCentros

function limpiarCamposCentros() {
   formu.DNombreCentro.value = '';
   formu.AProvinciaCentro.value = '';
   formu.ACategoria.value = '';
   formu.AVinculo.value = '';
   formu.FInicio.value = '';
   formu.FFin.value = '';
}

function modCentros() {
   formu = document.forms[0];
   comboCentros=objeto_combo('document.forms[0].jspCadenaCentro');
   //if (comprueba_seleccion(formu.jspCadenaCentro)) {
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

function validarFechaRegistro(){
      //FECHA REGISTRO OFICIAL
//      debugger;
      var cadena = document.forms[0].FRegistro_oficial.value;
      var cadena2 = document.forms[0].NHoraRegistro.value;
      cadena = trim(cadena);
      cadena2 = trim(cadena2);
      if(cadena == ''){
         alert("Debe rellenar el campo \" Fecha de Registro Oficial \".");
         return false;
      }
      //Si no introduce la hora, ponemos por defecto las 00:00:00
//      if (cadena.length < 19 && cadena.length >= 10) {
      if(cadena2 == ''){
         document.forms[0].NHoraRegistro.value = '00:00:00';
         cadena = cadena +' ' + document.forms[0].NHoraRegistro.value;
      }else if (!(esHoraCorrecta(cadena2))){
         alert("Formato de \" Hora de Registro Oficial \" no es correcto.");
         return false;      
      }else{
         cadena = cadena + ' ' + cadena2;
      }
      if (!(esTimestampCorrecto(cadena))){
         alert("Formato de \" Fecha de Registro Oficial \" no es correcto.");
         return false;
      }
      if(comprobarFechaNoFutura(cadena)){
         alert("La \" Fecha de Registro Oficial \" no es correcta.");
         return false;
      }
   return true;      
}

function validarObserva(){
   if(document.forms[0].APeticion.value == ''){
      alert("Debe rellenar el campo \" Observaciones \".");
      return false;
   }else{
      var aux = longMaxObservaciones(document.forms[0].APeticion);
      if(!aux){
         alert("El campo \" Observaciones \" no puede superar los <%=Constantes.LONG_SUBSANACIONES%> caracteres. [El n\u00famero de caracteres introducidos son: " + document.forms[0].APeticion.value.length + "]");
         return false;
      }
      return true;
   }
}

function mostrarOcultos() {
   if(document.forms[0].CEstadoFiltro.value=='4'){
      document.getElementById('dMotivos').style.display='';
      document.getElementById('dMotivos').style.visibility='visible';
      document.getElementById('dSubsana').style.display='none';
      document.getElementById('dSubsana').style.visibility='hidden';      
   }else if(document.forms[0].CEstadoFiltro.value=='6'){
      document.getElementById('dMotivos').style.display='none';
      document.getElementById('dMotivos').style.visibility='hidden';
      document.getElementById('dSubsana').style.display='';
      document.getElementById('dSubsana').style.visibility='visible';
   }else{
      document.getElementById('dMotivos').style.display='none';
      document.getElementById('dMotivos').style.visibility='hidden';
      document.getElementById('dSubsana').style.display='none';
      document.getElementById('dSubsana').style.visibility='hidden';
   }
}

</script>

	<div class="ocultarCampo">
		<div class="contenido">
			<div class="contenidoDCP1A modificarSolicitudAcceso">
				<html:form action="/OCAPNuevaSolicitud.do">
					<html:hidden property="NAniosejercicio" />
					<html:hidden property="CExp_id" />
					<html:hidden property="CUsr_id" />
					<html:hidden property="CSolicitudId" />
					<html:hidden property="CConvocatoriaId" />
					<html:hidden property="CGrado_id" />
					<html:hidden property="FFinRegistro" />
					<html:hidden property="CGradoAnteriorId" />

					<h2 class="tituloContenido">MODIFICACI&Oacute;N DE LA
						SOLICITUD DE ACCESO A GRADO DE CARRERA PROFESIONAL</h2>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.IR_INSERTAR%>">
						<logic:notEqual name="tipoAccionBis" value="<%=Constantes.SI%>">
							<logic:notEqual name="tipoAccionBis"
								value="<%=Constantes.IR_DETALLE%>">
								<a href="javascript:generarPDF();"><img
									src="imagenes/imagenes_ocap/imprimir.gif" alt="Imprimir"
									class="flotaIzq" /></a>
							</logic:notEqual>
						</logic:notEqual>
					</logic:notEqual>

					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.IR_INSERTAR%>">
						<logic:equal name="tipoAccionBis" value="<%=Constantes.SI%>">
							<p class="ocultarCampo">
								Va a proceder a registrar la solicitud. Por favor, compruebe que
								los datos introducidos en la misma son correctos. Si alguno es
								err&oacute;neo deber&aacute; pulsar el bot&oacute;n Modificar.
								En caso contrario deber&aacute; pulsar el bot&oacute;n
								Registrar.<br />
								<br />
							</p>
						</logic:equal>
					</logic:notEqual>

					<h3 class="subTituloContenidoMC">1.- DATOS PERSONALES:</h3>
					<fieldset>
						<legend class="tituloLegend"> 1.1.- Datos personales </legend>
						<div class="cuadroFieldset">
							<label for="idVApell1">Apellidos: * <html:text
									property="DApellido1"
									styleClass="recuadroM colocaApellidosAlta textoNormal"
									maxlength="61" />
							</label> <label for="idVNombre">Nombre: * <html:text
									property="DNombre"
									styleClass="recuadroM colocaNombreAlta textoNormal"
									maxlength="30" />
							</label> <br />
							<br /> <label for="idVDNI">NIF/NIE: * <input type="text"
								name="dniAux"
								value="<bean:write name='OCAPNuevaSolicitudForm' property='CDniReal'/>"
								class="recuadroM colocaDNIAlta textoNormal" disabled="disabled" />
							</label>
							<html:hidden name="OCAPNuevaSolicitudForm" property="CDniReal" />
							<html:hidden name="OCAPNuevaSolicitudForm" property="CDni" />
							<html:hidden name="OCAPNuevaSolicitudForm" property="bExisteSolicParaNifConv" />
							<label for="idVCorreo">Sexo: * <html:select
									styleClass="cbCuadros colocaSexoAlta"
									name="OCAPNuevaSolicitudForm" property="BSexo">
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
							</label>
							<script type="text/javascript">
                     if(document.forms[0].NTelefono1.value=='0')
                        document.forms[0].NTelefono1.value='';
                     if(document.forms[0].NTelefono2.value=='0')
                        document.forms[0].NTelefono2.value='';
                  </script>
							<br />
							<br /> <label for="idVCorreo">Correo Electr&oacute;nico: *
								<html:text property="DCorreoelectronico"
									styleClass="recuadroM colocaCorreoAltaAsterisco textoNormal"
									maxlength="50" />
							</label> <br />
							<br /> <label for="idVCorreo">Domicilio, Calle o Plaza y
								N&ordm;: * <html:text property="DDomicilio"
									styleClass="recuadroM colocaDireccionAlta textoNormal"
									maxlength="100" />
							</label> <br />
							<br /> <label for="idVProvincia">Provincia: * <html:select
									property="CProvincia_id" styleId="CProvincia_id"
									styleClass="cbCuadros colocaProvAlta" size="1"
									>
									<html:option value="">Seleccione...</html:option>
									<html:options
										collection="<%=Constantes.COMBO_TODAS_PROVINCIAS%>"
										property="CProvinciaId" labelProperty="DProvincia" />
								</html:select>
							</label> <br />
							<br /> <label for="idVLocalidades">Localidad: * <html:select
									property="CLocalidad_id" styleId="CLocalidad_id"
									styleClass="cbCuadros colocaLoc" size="1">
									<html:option value="">Seleccione...</html:option>
									<html:options collection="<%=Constantes.COMBO_LOCALIDADES%>"
										property="CLocalidadId" labelProperty="DLocalidad" />
								</html:select>
							</label>
							<html:hidden property="DLocalidad" />
							<label for="idVTelefono">C&oacute;digo Postal: * <html:text
									property="CPostalUsu"
									styleClass="recuadroM colocaCP textoNormal" maxlength="5" />
							</label>
						</div>
					</fieldset>

					<h3 class="subTituloContenidoMC">2.- DATOS PROFESIONALES:</h3>
					<fieldset>
						<legend class="tituloLegend"> 2.1.- Datos
							condici&oacute;n personal estatutario o funcionario
							sanitario de carrera </legend>

						<div class="espaciador"></div>
						<span class="informacionSituacion textoCursiva textoNegrita">
							<bean:message key="solicitudes.infoComisionServicio" />
						</span>

						<div class="cuadroFieldset altaUsuario">
							<!-- REGIMEN JURIDICO -->
							
							
							
							<label for="idVConvocatoria">Convocatoria: <html:text
									property="DConvocatoria_nombre" maxlength="250"
									styleClass="recuadroM colocaConvocatoria textoNormal" disabled="true" />
							</label> <br />
							<br />							
							
							
							

							
							
							<label class="altoReg" for="idVCategoria">R&eacute;gimen
								jur&iacute;dico: * <html:select name="OCAPNuevaSolicitudForm"
									property="CJuridicoId" styleId="CJuridicoId"
									styleClass="cbCuadros regJuridic"
									onchange="mostrarProfesional(); mostrarRegimen('N'); setTimeout('deshabilitar()',500);">
									<html:option value="">Seleccione...</html:option>
									<html:options
										collection="<%=Constantes.COMBO_REGIMEN_JURIDICO%>"
										property="CJuridicoId" labelProperty="DNombre" />
								</html:select> <span id="dRegimenOtros"
								style="display: none; visibility: hidden;"> <span>¿Cu&aacute;l?</span>
								<html:text name="OCAPNuevaSolicitudForm"
										styleClass="cuadroDondeAl ajusteCuadro"
										property="DRegimenJuridicoOtros" maxlength="100" />
							</span> <span id="cajaEstatutarioFijo"
								style="display: none; visibility: hidden;"> <label>Tipo:
										* </label> <html:select name="OCAPNuevaSolicitudForm"
										property="CJuridicoCombo" styleId="CJuridicoCombo"
										styleClass="cbCuadros cuadroDondeTipo colocaTipoRegJuric"
										onchange="mostrarProfesionalFijo();">
										<html:option value="">Seleccione...</html:option>
										<html:option
											value="<%=Constantes.C_JURIDICO_SANITARIO_FIJO_COD%>"><%=Constantes.C_JURIDICO_SANITARIO_FIJO%></html:option>
										<html:option value="<%=Constantes.C_JURIDICO_GS_FIJO_COD%>"><%=Constantes.C_JURIDICO_GS_FIJO%></html:option>
									</html:select>
							</span>
							</label> <br />
							<br />

							<!-- CATEGORIA -->
							<label for="idVCategoria">Categor&iacute;a: * <html:select
									property="CProfesional_id" styleId="CProfesional_id"
									styleClass="cbCuadros colocaCategoriaAlta"
									style="display:;visibility:visible;">
									<html:option value="">Seleccione...</html:option>
									<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
										property="CProfesionalId" labelProperty="DNombre" />
								</html:select> <html:select property="CProfesionalFijo_id"
									styleId="CProfesionalFijo_id"
									styleClass="cbCuadros colocaCategoriaAlta ajusteCategoria"
									style="display:none;visibility:hidden;">
									<html:option value="">Seleccione...</html:option>
									<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
										property="CProfesionalId" labelProperty="DNombre" />
								</html:select>
							</label> <br />
							<br />

							<!-- ESPECIALIDAD -->
							<label for="idVEspecialidad">Especialidad: * <html:select
									property="CEspec_id" styleId="CEspec_id"
									styleClass="cbCuadros colocaEspecialidadAlta">
									<html:option value="">Seleccione...</html:option>
									<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
										property="CEspecId" labelProperty="DNombre" />
								</html:select> <html:select property="CEspecFijo_id" styleId="CEspecFijo_id"
									styleClass="cbCuadros colocaEspecialidadAlta ajusteEspecialidad"
									style="display:none;visibility:hidden;">
									<html:option value="">Seleccione...</html:option>
									<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
										property="CEspecId" labelProperty="DNombre" />
								</html:select>
							</label> <br />
							<br />

							<!-- SERVICIO CARRERA -->
							<label for="idVEspecialidad">Servicio: <html:text
									property="DServicio" maxlength="250"
									styleClass="recuadroM colocaServicio textoNormal" />
							</label> <br />
							<br />

							<!-- AREA / UNIDAD / PUESTO -->
							<label for="idVEspecialidad">&Aacute;rea / Unidad /
								Puesto: <html:text property="DPuesto" maxlength="250"
									styleClass="recuadroM colocaArea textoNormal" />
							</label> <br />
							<br />
						</div>
					</fieldset>

					<fieldset>
						<legend class="tituloLegend"> 2.2.- Situaci&oacute;n a 31
							de diciembre de <bean:write name="OCAPNuevaSolicitudForm"
										property="anioConvocatoria" /> (Seg&uacute;n Convocatoria) </legend>

						<div class="cuadroFieldset altaUsuario">
							<!-- GRADO -->
							<label for="idVGrado">Grado al que opta: * 
							
							<html:text
									property="DGrado_des" maxlength="250"
									styleClass="recuadroM colocaConvocatoria textoNormal" disabled="true" />
							
							</label> <br />
							<br />

							<!-- PROCEDIMIENTO DE EVALUACION -->
							<label for="idVCategoria">Procedimiento de
								evaluaci&oacute;n por el que opta: * <html:select
									property="CProcedimientoId"
									styleClass="cbCuadros colocaSituacionMfNew" size="1">
									<html:option value="">Seleccione...</html:option>
									<html:options collection="<%=Constantes.COMBO_PERSONAL%>"
										property="CProcedimientoId" labelProperty="DNombre" />
								</html:select>
							</label> <br />
							<br />

							<!-- SITUACION ADMINISTRATIVA -->
							<label class="altoReg" for="idVCategoria">Situaci&oacute;n
								Administrativa: * <html:select name="OCAPNuevaSolicitudForm"
									property="CSitAdministrativaId"
									styleClass="cbCuadros situAdminMfNew"
									onchange="mostrarDonde('N');">
									<html:option value="">Seleccione...</html:option>
									<html:options collection="<%=Constantes.COMBO_SIT_ADMIN%>"
										property="CSit_AdministrativaId" labelProperty="DNombre" />
								</html:select> <span class="mostrarCampoOculto" id="serviciosDonde"
								style="display: none; visibility: hidden;"> <span>¿Cu&aacute;l?</span>
								<html:text name="OCAPNuevaSolicitudForm"
										styleClass="cuadroDondeAlNew"
										property="DSitAdministrativaOtros" maxlength="50" />
							</span>
							</label> <br />
							<br />
<!-- OCAP- 1327 ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- -->
							<%if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){%>
								<!-- PROVINCIA -->
								<label>Provincia: * <html:select
										property="CProvCarreraActualId" styleId="CProvCarreraActualId"
										styleClass="cbCuadros colocaProvinciaMfNew" size="1"
										onchange="setTimeout('deshabilitarGCTActual()',500);">
										<html:option value="">Seleccione...</html:option>
										<html:options collection="<%=Constantes.COMBO_PROVINCIAS%>"
											property="CProvinciaId" labelProperty="DProvincia" />
									</html:select>
								</label> <br />
								<br />
	
								<!-- GERENCIA -->
								<label>Gerencia: * <html:select
										property="CGerenciaActualId" styleId="CGerenciaActualId"
										styleClass="cbCuadros colocaGerenciaMfNew" size="1">
										<html:option value="">Seleccione...</html:option>
										<html:options collection="<%=Constantes.COMBO_GERENCIA_ACTUAL%>"
											property="CGerenciaId" labelProperty="DNombre" />
									</html:select>
								</label> <br />
								<br />
	
								<!-- CENTRO DE TRABAJO -->
								<label>Centro de Trabajo: * <html:select
										property="CCentroTrabActualId" styleId="CCentroTrabActualId"
										styleClass="cbCuadros colocaCentroMfNew" size="1">
										<html:option value="">Seleccione...</html:option>
										<html:options
											collection="<%=Constantes.COMBO_CENTRO_TRABAJO_CARRERA_ACTUAL%>"
											property="CCentrotrabajoId" labelProperty="DNombre" />
									</html:select>
								</label> <br />
							<% } else { %>
								<!-- PROVINCIA -->
								<label for="idVProvincia">Provincia: * <input type="text"
									name="provincia2010" value="<bean:write name='provincia2010'/>"
									class="recuadroM colocaProvinciaMfNew textoNormal"
									disabled="true" />
								</label> <br />
								<br />
	
								<!-- GERENCIA -->
								<label for="idVGerencia">Gerencia: * <input type="text"
									name="gerencia2010" value="<bean:write name='gerencia2010'/>"
									class="recuadroM colocaGerenciaMfNew textoNormal"
									disabled="true" />
								</label> <br />
								<br />
	
								<!-- CENTRO DE TRABAJO -->
								<label for="idVCentroTrabajo">Centro de Trabajo: * <input
									type="text" name="centro2010"
									value="<bean:write name='centro2010'/>"
									class="recuadroM colocaCentroMfNew textoNormal" disabled="true" />
								</label> <br />
							<% } %>
<!-- FIN OCAP-1327 ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- -->							
							<br />
						</div>
					</fieldset>

					<fieldset>
						<legend class="tituloLegend"> 2.3.- Situaci&oacute;n a
							fecha de publicaci&oacute;n de convocatoria </legend>

						<div class="cuadroFieldset altaUsuario">
							<!-- SITUACION ADMINISTRATIVA -->
							<label class="altoReg" for="idVCategoria">Situaci&oacute;n
								Administrativa: * 
								<%if(jcylUsuario.getUser().getRol().equals(Constantes.OCAP_GRS)){ %>
								<html:select name="OCAPNuevaSolicitudForm"
									property="CSitAdmonActualId"
									styleClass="cbCuadros situAdminMfNew"
									onchange="mostrarDonde('N');">
									<html:options collection="<%=Constantes.COMBO_SIT_ADMIN%>"
										property="CSit_AdministrativaId" labelProperty="DNombre" />
								</html:select> <span class="mostrarCampoOculto" id="serviciosDonde"
								style="display: none; visibility: hidden;"> <span>¿Cu&aacute;l?</span>
								<html:text name="OCAPNuevaSolicitudForm"
										styleClass="cuadroDondeAlNew"
										property="DSitAdministrativaOtros" maxlength="50" />
							</span>
								<%} else{ %> 
								<html:text name="OCAPNuevaSolicitudForm"
									property="DSitAdmonActual"
									styleClass="recuadroM situAdminMfNew textoNormal"
									disabled="true" />
								<logic:notEmpty
									name="OCAPNuevaSolicitudForm" property="AOtraSitAdmonActual">
									<span class="mostrarCampoOculto" id="serviciosDondeActual">
										<span>¿Cu&aacute;l?</span>
									<html:text name="OCAPNuevaSolicitudForm"
											property="AOtraSitAdmonActual" styleClass="cuadroDondeAlNew"
											disabled="true" />
									</span>
								</logic:notEmpty>
								<%} %>
							</label> <br />
							<br />

							<!-- PROVINCIA -->
							<label for="idVProvincia">Provincia: * <html:select
									property="CProvinciaCarrera_id" styleId="CProvinciaCarrera_id"
									styleClass="cbCuadros colocaProvinciaMfNew" size="1"
									onchange="setTimeout('deshabilitarGCT()',500);">
									<html:option value="">Seleccione...</html:option>
									<html:options collection="<%=Constantes.COMBO_PROVINCIAS%>"
										property="CProvinciaId" labelProperty="DProvincia" />
								</html:select>
							</label> <br />
							<br />

							<!-- GERENCIA -->
							<label for="idVGerencia">Gerencia: * <html:select
									property="CGerencia_id" styleId="CGerencia_id"
									styleClass="cbCuadros colocaGerenciaMfNew" size="1">
									<html:option value="">Seleccione...</html:option>
									<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
										property="CGerenciaId" labelProperty="DNombre" />
								</html:select>
							</label> <br />
							<br />

							<!-- CENTRO DE TRABAJO -->
							<label for="idVCentroTrabajo">Centro de Trabajo: * <html:select
									property="CCentrotrabajo_id" styleId="CCentrotrabajo_id"
									styleClass="cbCuadros colocaCentroMfNew" size="1">
									<html:option value="">Seleccione...</html:option>
									<html:options
										collection="<%=Constantes.COMBO_CENTRO_TRABAJO_CARRERA%>"
										property="CCentrotrabajoId" labelProperty="DNombre" />
								</html:select>
							</label> <br />
							<br />
						</div>
					</fieldset>

					<fieldset>
						<legend class="tituloLegend"> 2.4.- A&ntilde;os de
							ejercicio profesional como personal en el Sistema Nacional de
							Salud hasta el 31/12/<bean:write name="OCAPNuevaSolicitudForm"
										property="anioConvocatoria" /> (Seg&uacute;n Convocatoria) </legend>

						<div class="cuadroFieldset altaUsuario">
							<!-- AÑOS DE EJERCICIO -->
							<div class="tiempoEjercicioNew">
								<span class="colocaNumAlta">A&ntilde;os de ejercicio en
									la categor&iacute;a profesional por la que se accede: * </span> <br />
								<br /> <label for="idVAnt">N&ordm; A&ntilde;os: </label>
								<html:text property="NAniosantiguedad"
									styleClass="recuadroM colocaAniosMfNew textoNormal"
									maxlength="2" />
								<label>N&ordm; Meses: </label>
								<html:text property="NMesesantiguedad"
									styleClass="recuadroM colocaMesesMfNew textoNormal"
									maxlength="2" />
								<label>N&ordm; D&iacute;as: </label>
								<html:text property="NDiasantiguedad"
									styleClass="recuadroM colocaDiasMfNew textoNormal"
									maxlength="2" />
							</div>
							<br />
							<div class="lineaBajaM"></div>
							<br /> <label for="idVServicios"> Tiene servicios
								prestados en otro centro de trabajo distinto al actual: </label> <br />
							<br />

							<!-- CENTRO DE TRABAJO -->
							<label>Centro de Trabajo: <html:text
									property="DNombreCentro" maxlength="100"
									styleClass="recuadroM colocaCtrAlta textoNormal" />
							</label> <br />
							<br />

							<!-- CATEGORIA / ESPECIALIDAD / CUERPO -->
							<label> Categor&iacute;a / Especialidad / Cuerpo: <html:text
									property="ACategoria" maxlength="200"
									styleClass="recuadroM colocaCategoriaEspecialidadAlta textoNormal" />
							</label> <br />
							<br />

							<!-- PROVINCIA -->
							<label>Provincia: <html:select
									property="AProvinciaCentro"
									styleClass="cbCuadros colocaProvinciaServicios">
									<html:option value="">Seleccione...</html:option>
									<html:options
										collection="<%=Constantes.COMBO_TODAS_PROVINCIAS%>"
										property="DProvincia" labelProperty="DProvincia" />
								</html:select>
							</label>

							<!-- VINCULO -->
							<label>V&iacute;nculo: <html:select property="AVinculo"
									styleClass="cbCuadros colocaVinculoServicios">
									<html:option value="">Seleccione...</html:option>
									<html:optionsCollection name="<%=Constantes.COMBO_VINCULOS%>" />
								</html:select>
							</label> <br />
							<br />

							<!-- FECHA DE INICIO -->
							<label for="idVNAnnios">Fecha Inicio (dd/mm/aaaa): <html:text
									property="FInicio" maxlength="10"
									styleClass="recuadroM colocaFechaAlta textoNormal" /> <span>
									<a
									href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FInicio", document.forms[0].FInicio.value);'>
										<html:img src="imagenes/calendario.gif"
											styleClass="iconoCalendario" border="0" alt="Calendario" />
								</a>
							</span>
							</label>

							<!-- FECHA FIN -->
							<label class="fechaFinAlta" for="idVNAnnios">Fecha Fin
								(dd/mm/aaaa): <html:text property="FFin" maxlength="10"
									styleClass="recuadroM colocaFechaAlta textoNormal" /> <span>
									<a
									href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FFin", document.forms[0].FFin.value);'>
										<html:img src="imagenes/calendario.gif"
											styleClass="iconoCalendario" border="0" alt="Calendario" />
								</a>
							</span>
							</label> <br />
							<br />
							<br />
							<br />

							<!-- BOTONES AÑADIR / MODIFICAR / ELIMINAR -->
							<div
								class="botonesCertif2 botonesCertif2Docencia colocaBotonesAlta">
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <input
										type="button" id="anadir" value="A&ntilde;adir"
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
							<script type="text/javascript">inicializarCentros();</script>
						</div>
					</fieldset>

					<fieldset>
						<legend class="tituloLegend"> 2.5.- Grado de carrera
							profesional reconocido </legend>

						<div class="cuadroFieldset altaUsuario">
							<!-- GRADO QUE POSEE (MODALIDAD ANTERIOR) -->
							<label for="idVCategoria">Grado que posee: * <html:select
									name="OCAPNuevaSolicitudForm" property="CModAnteriorId"
									styleClass="cbCuadros colocaGradoPosee"
									onchange="javascript:obtenerGradoModAnterior();">
									<html:option value="">Seleccione...</html:option>
									<html:options collection="<%=Constantes.COMBO_MOD_ANTERIOR%>"
										property="CModAnteriorId" labelProperty="DNombre" />
								</html:select>
							</label> <br />
							<br />
						</div>
					</fieldset>

					<p>Los campos se&ntilde;alados con * son obligatorios</p>
					<div class="limpiadora"></div>
					<div class="espaciador"></div>

					<div class="botonesPagina">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:modificarSolicitud();"> <img
									src="imagenes/imagenes_ocap/flecha_correcto.gif"
									alt="Aceptar Solicitud" /> <span> Aceptar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:borrarForm();enviar('OCAPNuevaSolicitud.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');">
									<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" />
									<span> Cancelar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</div>
					<div class="limpiadora"></div>
					<div class="espaciador"></div>
				</html:form>

			</div>
			<!-- /fin de ContenidoDCP1A_PGP -->
		</div>
		<!-- /Fin de Contenido -->
	</div>
	<!-- /Fin de ocultarCampo -->
	<!--   <script type="text/javascript">mostrarDonde();mostrarRegimen('S');</script> -->
</body>
