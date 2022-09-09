<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.modalidadAnterior.OCAPModalidadAnteriorOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.historicoProfesionales.OCAPHistoricoProfesionalesOT"%>
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

function borrarForm(){
   //document.getElementById("OCAPNuevaSolicitudForm").reset();
   document.forms[0].reset();
   var e=document.getElementsByTagName("input");
      for(var i=0;i<e.length;i++){
         if (e[i].type == 'hidden'){
            e[i].value='';
         }
      }
}

function construirCombos(){

   <%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>
         
         mostrarDonde('S');
         mostrarDondeActual('S');
         mostrarRegimen('S');
         
         //document.getElementById('CProfesional_id').disabled=true;
         //document.getElementById('CEspec_id').disabled=true;
         //document.getElementById('CLocalidad_id').disabled=true;
         deshabilitar();
         deshabilitarGCT();
         deshabilitarGCTActual();
         //deshabilitarLocalidad();
         
         if(document.forms[0].CJuridicoId.value=='1'){
            document.getElementById('CProfesional_id').style.display='none';
            document.getElementById('CProfesional_id').style.visibility='hidden';
      
            document.getElementById('CProfesionalFijo_id').style.display='';
            document.getElementById('CProfesionalFijo_id').style.visibility='visible';
            
            document.getElementById('CEspec_id').style.display='none';
            document.getElementById('CEspec_id').style.visibility='hidden';
      
            document.getElementById('CEspecFijo_id').style.display='';
            document.getElementById('CEspecFijo_id').style.visibility='visible';
         }else{
            document.getElementById('CProfesional_id').style.display='';
            document.getElementById('CProfesional_id').style.visibility='visible';
      
            document.getElementById('CProfesionalFijo_id').style.display='none';
            document.getElementById('CProfesionalFijo_id').style.visibility='hidden';         
            
            document.getElementById('CEspec_id').style.display='';
            document.getElementById('CEspec_id').style.visibility='visible';
      
            document.getElementById('CEspecFijo_id').style.display='none';
            document.getElementById('CEspecFijo_id').style.visibility='hidden';            
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
   if(document.forms[0].CProvinciaCarrera_id.value==''){
         document.getElementById('CGerencia_id').disabled=true;
         document.getElementById('CCentrotrabajo_id').disabled=true;
   }
}

function deshabilitarGCTActual() {
   // Deshabilita gerencia y centro de trabajo Actual en caso de que se elija 'Seleccione' en combo Provincia de la Carrera Profesional
   if(document.forms[0].CProvCarreraActualId.value==''){
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

//    $(document).ready(function () {
//
//      $("#CLocalidad_id").cascade("#CProvincia_id",{
//        ajax: {
//            url: 'OCAPNuevaSolicitud.do?accion=cargarLocalidades'
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

function longMaxMotivos(campo) {
   if(campo.value.length <= LONG_MAX_DMOTIVOS) {
      document.getElementById("longMotiv").innerHTML=campo.value.length;
      return true;
   } else {
      campo.value = campo.value.substring(0,LONG_MAX_DMOTIVOS);
      document.getElementById("longMotiv").innerHTML=campo.value.length;
      return false;
   }
}

function longMaxMotivosCambio(campo) {
   if(campo.value.length <= LONG_MAX_DMOTIVOSCAMBIO) {
      document.getElementById("longMotivosCambio").innerHTML=campo.value.length;
      return true;
   } else {
      campo.value = campo.value.substring(0,LONG_MAX_DMOTIVOSCAMBIO);
      document.getElementById("longMotivosCambio").innerHTML=campo.value.length;
      return false;
   }
}

function longMaxOtrosMotivosExclusion(campo) {
   if(campo.value.length <= LONG_MAX_DOTROSMOTIVOSEXCLUSION) {
      document.getElementById("longOtrosMotivosExclusion").innerHTML=campo.value.length;
      return true;
   } else {
      campo.value = campo.value.substring(0,LONG_MAX_DOTROSMOTIVOSEXCLUSION);
      document.getElementById("longOtrosMotivosExclusion").innerHTML=campo.value.length;
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

function aceptarSolicitud(){
   if(validarFormulario(document.forms[0],'')) {
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
         mensaje +="\u00BFest\u00e1 seguro de querer registrar la solicitud?";
         if(confirm(mensaje)){
            enviar('OCAPNuevaSolicitud.do?accion=irDetalleAlta'); 
         }
      }else{
         enviar('OCAPNuevaSolicitud.do?accion=irDetalleAlta'); 
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

function mostrarDondeActual(param) {
    if(document.forms[0].CSitAdmonActualId.value=='6') {
        document.getElementById('serviciosDondeActual').style.display='';
        document.getElementById('serviciosDondeActual').style.visibility='visible';
        if(param=='N'){
            document.forms[0].AOtraSitAdmonActual.value ='';
        }
    } else {
        document.getElementById('serviciosDondeActual').style.display='none';
        document.getElementById('serviciosDondeActual').style.visibility='hidden';
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
   }else if (!LetrasYNumeros(formu.DNombreCentro)){
      alert('El campo \'Centro de Trabajo\' contiene caracteres no permitidos.');
   }else if(formu.ACategoria.value == ''){
      alert('Debe introducir una Categor\u00EDa / Especialidad / Cuerpo.');
   }else if (!LetrasYNumeros(formu.ACategoria)){
      alert('El campo \'Categor\u00EDa / Especialidad / Cuerpo\' contiene caracteres no permitidos.');
   }else if(formu.AProvinciaCentro.value == ''){
      alert('Debe introducir la Provincia a la cual pertenece el Centro de Trabajo.');
   }else if(formu.AVinculo.value == ''){
      alert('Debe elegir un V\u00EDnculo.');
   }else if(formu.FInicio.value == ''){
      alert('Debe introducir una Fecha de Inicio.');
   }else if(formu.FInicio.value != '' && !esFechaCorrecta(formu.FInicio.value)){
      alert('El campo Fecha de Inicio no tiene el formato correcto.');
   } else if(comprobarFechaNoFutura(formu.FInicio.value)) {
      alert("La \" Fecha de Inicio\" no puede ser futura.");
   }else if(formu.FFin.value == ''){
      alert('Debe introducir una Fecha de Fin.');
   }else if(formu.FFin.value != '' && !esFechaCorrecta(formu.FFin.value)){
      alert('El campo Fecha de Fin no tiene el formato correcto.');
   } else if(comprobarFechaNoFutura(formu.FFin.value)) {
      alert("La \" Fecha de Fin\" no puede ser futura.");
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

function validarFechaRegistro(tipo){
      //FECHA REGISTRO OFICIAL
      var cadena;
      var cadena2;
      if (tipo == 'registro') {
          cadena = trim(document.forms[0].FRegistro_oficial.value);
          cadena2 = trim(document.forms[0].NHoraRegistro.value);
      }else{
          cadena = trim(document.forms[0].FInconf_solic.value);
          cadena2 = trim(document.forms[0].NHoraAlega.value);      
      }
//      cadena = trim(cadena);
//      cadena2 = trim(cadena2);
      if(cadena == ''){
         alert("Debe rellenar el campo \" Fecha de Registro Oficial \".");
         return false;
      }
      //Si no introduce la hora, ponemos por defecto las 00:00:00
//      if (cadena.length < 19 && cadena.length >= 10) {
      if(cadena2 == ''){
         if (tipo == "registro") {
         document.forms[0].NHoraRegistro.value = '00:00:00';
         cadena = cadena +' ' + document.forms[0].NHoraRegistro.value;
         }else{
            document.forms[0].NHoraAlega.value = '00:00:00';
            cadena = cadena +' ' + document.forms[0].NHoraAlega.value;
         }
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

function nuevoUsuario(){
   if (validarFechaRegistro('registro'))
      enviar('OCAPNuevaSolicitud.do?accion=insertarNuevo&cSolicitudId='+document.forms[0].CSolicitudId.value+'&vuelta=<%=request.getParameter("vuelta")==null?request.getAttribute("vuelta"):request.getParameter("vuelta")%>');
}

function asociar(usuario){
   if (validarFechaRegistro('registro'))
      enviar('OCAPNuevaSolicitud.do?accion=asignarUsuario&cUsuarioId='+usuario+'&cSolicitudId='+document.forms[0].CSolicitudId.value+'&vuelta=<%=request.getParameter("vuelta")==null?request.getAttribute("vuelta"):request.getParameter("vuelta")%>');
}

function reasociar(usuario){
   enviar('OCAPNuevaSolicitud.do?accion=reasignarUsuario&cUsuarioId='+usuario+'&cSolicitudId='+document.forms[0].CSolicitudId.value+'&vuelta=<%=request.getParameter("vuelta")==null?request.getAttribute("vuelta"):request.getParameter("vuelta")%>');
}

function modificarFecha(){
   if (validarFechaMC(''))
      enviar('OCAPNuevaSolicitud.do?accion=modificarFechasMC&opcion=<%=request.getAttribute("opcion")%>');
}

function validarFechaMC(){
      var cadena1;
      var cadena2;
      var cadena3;

      cadena1 = trim(document.forms[0].FFin_mc.value);
      cadena2 = trim(document.forms[0].FInicio_eval_mc.value);
      cadena3 = trim(document.forms[0].FFin_eval_mc.value);
      
      if(cadena1 == '' && cadena2 == '' && cadena3 == ''){
         alert("Debe rellenar una de las tres fechas.");
         return false;
      }else if (cadena1 == ''){
         alert("Fecha de fin de autoevaluaci\u00f3n de MC es obligatoria.");
         return false;
      }else if (cadena2 == '' && fechaAlegaciones != null && fechaAlegaciones != ''){
         alert("Fecha de inicio de alegaciones de MC es obligatoria.");
         return false;
      }else if (cadena3 == '' && fechaDefinitiva != null && fechaDefinitiva != ''){
         alert("Fecha de listado definitivo de MC es obligatoria.");
         return false;      
      }
         
      if (cadena1 != ''){
         if (!(esFechaCorrecta(cadena1))){
            alert("Formato de \" Fecha de fin de autoevaluaci\u00f3n de m\u00e9ritos \" no es correcto.");
            return false;
         }else if(esFecha1MayorQueFecha2(document.forms[0].FInicio_mc.value,'00:00:00', cadena1,'00:00:00')){
            alert("Fecha de fin de autoevaluaci\u00f3n de m\u00e9ritos no puede ser menor que la fecha de la convocatoria " + document.forms[0].FInicio_mc.value);
            return false;
         } 
      }   

      if (cadena2 != '') {
         if (!(esFechaCorrecta(cadena2))){
            alert("Formato de \" Fecha de inicio de alegaciones de MC \" no es correcto.");
            return false;
         }else if(esFecha1MayorQueFecha2(document.forms[0].FInicio_cc.value,'00:00:00', cadena2,'00:00:00')){
            alert("Fecha de inicio de alegaciones de MC no puede ser menor que la fecha de la convocatoria " + document.forms[0].FInicio_cc.value);
            return false;
         }
      }

      if (cadena3 != '') {
        if (!(esFechaCorrecta(cadena3))){
            alert("Formato de \" Fecha de listado definitivo de MC \" no es correcto.");
            return false;
         }else if(esFecha1MayorQueFecha2(document.forms[0].FFin_cc.value,'00:00:00', cadena3,'00:00:00')){         
            alert("Fecha de listado definitivo de MC no puede ser menor que la fecha de la convocatoria " + document.forms[0].FFin_cc.value);
            return false;
         }
      }
      
      if (cadena2 != '' && cadena3 != ''){
         if(esFecha1MayorQueFecha2(cadena2,'00:00:00', cadena3,'00:00:00')){         
            alert("Fecha de listado definitivo de MC no puede ser menor que la fecha de inicio de alegaciones de MC" );
            return false;
         }
      }
         
   return true;      
}

function cambiarEstado() {
   if (document.forms[0].CEstadoFiltro.value == '')
      alert("Debe seleccionar un estado nuevo");
   else {
      if (document.forms[0].CEstadoFiltro.value == '4') {
         if (validarMotivos())
            enviar('OCAPNuevaSolicitud.do?accion=cambiarEstado&vuelta=<%=request.getParameter("vuelta")==null?request.getAttribute("vuelta"):request.getParameter("vuelta")%>');      
      } else if (document.forms[0].CEstadoFiltro.value == '6') {
         if (validarObserva('subsana'))
            enviar('OCAPNuevaSolicitud.do?accion=cambiarEstado&vuelta=<%=request.getParameter("vuelta")==null?request.getAttribute("vuelta"):request.getParameter("vuelta")%>');      
      } else if (document.forms[0].CEstadoFiltro.value == '7') {
         if (validarFechaRegistro('alega') && validarObserva('alega'))
            enviar('OCAPNuevaSolicitud.do?accion=cambiarEstado&vuelta=<%=request.getParameter("vuelta")==null?request.getAttribute("vuelta"):request.getParameter("vuelta")%>');      
      } else
         enviar('OCAPNuevaSolicitud.do?accion=cambiarEstado&vuelta=<%=request.getParameter("vuelta")==null?request.getAttribute("vuelta"):request.getParameter("vuelta")%>');      
   }
}

function validarObserva(tipo){
   if (tipo == 'subsana') {
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
   }else{
      if(document.forms[0].DMotivo_inconf_solic.value == ''){
         alert("Debe rellenar el campo \" Motivos \".");
         return false;
      }else{
         var aux = longMaxMotivos(document.forms[0].DMotivo_inconf_solic);
         if(!aux){
            alert("El campo \" Observaciones \" no puede superar los <%=Constantes.LONG_MOTIV_INCONF%> caracteres. [El n\u00famero de caracteres introducidos son: " + document.forms[0].DMotivo_inconf_solic.value.length + "]");
            return false;
         }
         return true;
      }   
   }
}

function validarMotivos() {
   var motivos = document.forms[0].CMotivosExclusion;
   if(motivos != null && motivos != '') {
      var cont = 0;
      for(i=0; i < motivos.length; i++) {
         if(motivos[i].checked == true) {
            // Comprobamos si el motivo seleccionado es del tipo 'Otros' para verificar en dicho caso, que ha rellenado los motivos
            var motivoSeleccionado = motivos[i].value.split("#");
            if(motivoSeleccionado[1] == 'Otros') {
               if(document.forms[0].DOtrosMotivosExclusion.value == 'undefined' || document.forms[0].DOtrosMotivosExclusion.value == '') {
                  alert("Si selecciona el tipo de motivo 'Otros', debe indicar cuales.");
                  return false;
               }
            }
            cont++;
         }
      }
      if(cont == 0 ) {
         alert("Debe indicar al menos un Motivo de Exclusi\u00f3n");
         return false;
      }
   }
   return true;
}

function mostrarOcultos() {
   
   if(document.forms[0].CEstadoId.value == '4') {
      var motivos = document.forms[0].CMotivosExclusion;
      if(motivos != null && motivos != '') {
         for(i=0; i < motivos.length; i++) {
            motivos[i].disabled = true;
         }
      }
      document.forms[0].DOtrosMotivosExclusion.disabled = true;
   }
   
   if(document.forms[0].BCierreSo.value == '<%=Constantes.NO%>') {
      
      if(document.forms[0].CEstadoFiltro.value == '4') {
         
         // EXCLUSION
         document.getElementById('dMotivos').style.display='';
         document.getElementById('dMotivos').style.visibility='visible';
         
         document.getElementById('dSubsana').style.display='none';
         document.getElementById('dSubsana').style.visibility='hidden';      
         
         document.getElementById('dAlega').style.display='none';
         document.getElementById('dAlega').style.visibility='hidden'; 
         
         var motivos = document.forms[0].CMotivosExclusion;
         if(motivos != null && motivos != '') {
            for(i=0; i < motivos.length; i++) {
               motivos[i].disabled = false;
            }
         }
         document.forms[0].DOtrosMotivosExclusion.disabled = false;
         
      } else if(document.forms[0].CEstadoFiltro.value == '6') {
         
         // SUBSANACION   
         document.getElementById('dMotivos').style.display='none';
         document.getElementById('dMotivos').style.visibility='hidden';
         
         document.getElementById('dSubsana').style.display='';
         document.getElementById('dSubsana').style.visibility='visible';
         
         document.getElementById('dAlega').style.display='none';
         document.getElementById('dAlega').style.visibility='hidden';
         
      } else if(document.forms[0].CEstadoFiltro.value == '7') {
         
         // ALEGACION  
         document.getElementById('dMotivos').style.display='none';
         document.getElementById('dMotivos').style.visibility='hidden';
         
         document.getElementById('dSubsana').style.display='none';
         document.getElementById('dSubsana').style.visibility='hidden';
         
         document.getElementById('dAlega').style.display='';
         document.getElementById('dAlega').style.visibility='visible';
         
      } else {
         
         document.getElementById('dMotivos').style.display='none';
         document.getElementById('dMotivos').style.visibility='hidden';
         
         document.getElementById('dSubsana').style.display='none';
         document.getElementById('dSubsana').style.visibility='hidden';
         
         document.getElementById('dAlega').style.display='none';
         document.getElementById('dAlega').style.visibility='hidden';       
      }
   }
   
   if(document.forms[0].CEstadoId.value == '4') {
      document.getElementById('dMotivos').style.display='';
      document.getElementById('dMotivos').style.visibility='visible';
   }
   
}

function activarAceptar(){
   if (document.forms[0].acepto.checked && document.forms[0].checkRequisitos.checked && document.forms[0].checkDatos.checked && document.forms[0].checkCompromiso.checked) {
      document.getElementById('ocultarAceptar').style.display='';
      document.getElementById('ocultarAceptar').style.visibility='visible';
   } else {
      document.getElementById('ocultarAceptar').style.display='none';
      document.getElementById('ocultarAceptar').style.visibility='hidden';
   }
}

function mostrarObserva(param) {
   if (param=='Y'){
      document.getElementById("observacion").style.display='';
      document.getElementById("observacion").style.visibility='visible';
      <%if (Constantes.NO.equals(request.getAttribute("excluir"))){%>
         document.getElementById("fechas").style.display='';
         document.getElementById("fechas").style.visibility='visible';         
      <%}%>
   } else {
      document.getElementById("observacion").style.display='none';
      document.getElementById("observacion").style.visibility='hidden';
      <%if (Constantes.NO.equals(request.getAttribute("excluir"))){%>
         document.getElementById("fechas").style.display='none';
         document.getElementById("fechas").style.visibility='hidden';         
      <%}%>      
   }
}

function excluir(){
   if (!document.forms[0].BRespuesta[0].checked && !document.forms[0].BRespuesta[1].checked){
      <%if (Constantes.SI.equals(request.getAttribute("excluir"))){%>
         alert('Debe indicar si excluir o no del proceso');
      <%}else{%>
         alert('Debe indicar si incluir o no en el proceso');
      <%}%>
   }else if (document.forms[0].BRespuesta[0].checked ){  
      <%if (Constantes.SI.equals(request.getAttribute("excluir"))){%>
            if (confirm ('El profesional ' + document.forms[0].DNombre.value + ' ' + document.forms[0].DApellido1.value + ' con DNI ' + document.forms[0].CDniReal.value + 
               ' va a quedar excluido del prodeso de evaluaci\u00f3n del desempe\u00f1o de la competencia. Si est\u00e1 usted seguro pulse aceptar. Pulse cancelar en caso contrario')){   
               enviar('OCAPNuevaSolicitud.do?accion=faseEDC&tipo=<%=Constantes.EDC_EXCLUIR_VALUE%>&opcion=<%=request.getAttribute("opcion")%>');
            } 
      <%}else{%>
            if (confirm ('El profesional ' + document.forms[0].DNombre.value + ' ' + document.forms[0].DApellido1.value + ' con DNI ' + document.forms[0].CDniReal.value + 
               ' va a poder autoevaluarse en el desempe\u00f1o de la competencia. Si est\u00e1 usted seguro pulse aceptar. Pulse cancelar en caso contrario')){   
               enviar('OCAPNuevaSolicitud.do?accion=faseEDC&tipo=<%=Constantes.EDC_INCLUIR_VALUE%>&opcion=<%=request.getAttribute("opcion")%>');
            }        
      <%}%>            
   }else 
      enviar('OCAPNuevaSolicitud.do?accion=buscarAdmGrs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=request.getAttribute("opcion")%>')
}
</script>

	<logic:equal name="tipoAccion" value="<%=Constantes.IR_INSERTAR%>">
		<div class="ocultarCampo">
	</logic:equal>

	<div class="contenido">
		<div class="contenidoDCP1A altaPGP">
			<html:form action="/OCAPNuevaSolicitud.do">
				<html:hidden property="NAniosejercicio" />
				<html:hidden property="CConvocatoriaId" />
				<html:hidden property="FFinRegistro" />
				<html:hidden property="CGradoAnteriorId" />
				<html:hidden property="FPlazapropiedad" />
				<html:hidden property="CExp_id" />

				<logic:notEqual name="tipoAccionBis" value="<%=Constantes.FASE_MC%>">
					<h2 class="tituloContenido">Formulario de Solicitud de Acceso
						a Grado de Carrera Profesional</h2>
				</logic:notEqual>
				<logic:equal name="tipoAccionBis" value="<%=Constantes.FASE_MC%>">
					<h2 class="tituloContenido">Datos de Solicitud de Acceso a
						Grado de Carrera Profesional</h2>
				</logic:equal>
				<logic:notEqual name="tipoAccion"
					value="<%=Constantes.IR_INSERTAR%>">
					<logic:notEqual name="tipoAccionBis" value="<%=Constantes.SI%>">
						<logic:notEqual name="tipoAccionBis"
							value="<%=Constantes.IR_DETALLE%>">
							<logic:notEqual name="tipoAccionBis"
								value="<%=Constantes.FASE_MC%>">
								<a href="javascript:generarPDF();"><img
									src="imagenes/imagenes_ocap/imprimir.gif" alt="Imprimir"
									class="flotaIzq" /></a>
							</logic:notEqual>
						</logic:notEqual>
					</logic:notEqual>
				</logic:notEqual>

				<logic:notEqual name="tipoAccion"
					value="<%=Constantes.IR_INSERTAR%>">
					<logic:equal name="tipoAccionBis" value="<%=Constantes.SI%>">
						<p class="ocultarCampo">Va a proceder a registrar la
							solicitud. Por favor, compruebe que los datos introducidos en la
							misma son correctos. Si alguno es err&oacute;neo deber&aacute;
							pulsar el bot&oacute;n Modificar. En caso contrario deber&aacute;
							pulsar el bot&oacute;n Registrar.</p>
					</logic:equal>
				</logic:notEqual>

				<h3 class="subTituloContenidoMC">1.- DATOS PERSONALES:</h3>
				<fieldset>
					<legend class="tituloLegend"> 1.1.- Datos personales </legend>

					<!-- INSERTAR -->
					<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>

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
						<br /> <label for="idVDNI">NIF/NIE: * <% if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIOS))){ %>
							<html:text name="OCAPNuevaSolicitudForm" property="CDniReal"
								styleClass="recuadroM colocaDNIAlta textoNormal" disabled="true" />
							<html:hidden name="OCAPNuevaSolicitudForm" property="CDniReal" />
							<% } else { %> <html:text name="OCAPNuevaSolicitudForm"
								property="CDniReal"
								styleClass="recuadroM colocaDNIAlta textoNormal" maxlength="10" />
							<% } %>
						</label>
						<html:hidden name="OCAPNuevaSolicitudForm" property="CDni" />
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
						</label> <br />
						<br /> <label for="idVCorreo">Correo Electr&oacute;nico: *<html:text
								property="DCorreoelectronico"
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
								styleClass="cbCuadros colocaLocAlta" size="1">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_LOCALIDADES%>"
									property="CLocalidadId" labelProperty="DLocalidad" />
							</html:select>
						</label> <label for="idVTelefono">C&oacute;digo Postal: * <html:text
								property="CPostalUsu"
								styleClass="recuadroM colocaCPAlta textoNormal" maxlength="5" />
						</label>
					</div>
					<%}%>

					<!-- DETALLE -->
					<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)){%>

					<html:hidden name="OCAPNuevaSolicitudForm" property="DApellido1" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="DNombre" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="CDni" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="CDniReal" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="NTelefono1" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="NTelefono2" />
					<html:hidden name="OCAPNuevaSolicitudForm"
						property="DCorreoelectronico" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="BSexo" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="DDomicilio" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="CProvincia_id" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="DProvincia" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="CLocalidad_id" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="DLocalidad" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="CPostalUsu" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="CSolicitudId" />
					<html:hidden name="OCAPNuevaSolicitudForm"
						property="FRegistro_solic" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="CUsr_id" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="CEstatutId" />
					<html:hidden name="OCAPNuevaSolicitudForm"
						property="FNegacion_solic" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="BCierreSo" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="bExisteSolicParaNifConv" />
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
					<%}%>
				</fieldset>

				<h3 class="subTituloContenidoMC">2.- DATOS PROFESIONALES:</h3>
				<fieldset>
					<legend class="tituloLegend"> 2.1.- Datos condici&oacute;n
						personal estatutario o funcionario sanitario de carrera </legend>

					<div class="espaciador"></div>
					<span class="informacionSituacion textoCursiva textoNegrita">
						<bean:message key="solicitudes.infoComisionServicio" />
					</span>

					<!-- INSERTAR -->
					<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>

					<div class="cuadroFieldset altaUsuario">
						<% if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE))){ %>
						<label class="labelReg" >Convocatoria: </label>
						<span><bean:write name="OCAPNuevaSolicitudForm"
											property="DConvocatoria_nombre" /></span>
						<br/>
						<br/>
						<%} %>
						<!-- REGIMEN JURIDICO -->
						<label class="labelReg" for="idVCategoria">R&eacute;gimen
							jur&iacute;dico: * <html:select name="OCAPNuevaSolicitudForm"
								property="CJuridicoId" styleId="CJuridicoId"
								styleClass="cbCuadros regJuridicNew"
								onchange="mostrarProfesional(); mostrarRegimen('N'); setTimeout('deshabilitar()',500);">
								<html:option value="">Seleccione...</html:option>
								<html:options
									collection="<%=Constantes.COMBO_REGIMEN_JURIDICO%>"
									property="CJuridicoId" labelProperty="DNombre" />
							</html:select> <span class="labelReg" id="dRegimenOtros"
							style="display: none; visibility: hidden;"> <span>¿Cu&aacute;l?:</span>
							<html:text name="OCAPNuevaSolicitudForm"
									styleClass="recuadroM cuadroDondeAl textoNormal"
									property="DRegimenJuridicoOtros" maxlength="100" />
						</span> <span class="mostrarCampoOculto" id="cajaEstatutarioFijo"
							style="display: none; visibility: hidden;"> <label>Tipo:
									*</label> <html:select name="OCAPNuevaSolicitudForm"
									property="CJuridicoCombo" styleId="CJuridicoCombo"
									styleClass="cbCuadros colocaTipoRegJuricNew"
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
								styleClass="cbCuadros colocaCategoriaAltaNew"
								style="display:;visibility:visible;">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
									property="CProfesionalId" labelProperty="DNombre" />
							</html:select> <html:select property="CProfesionalFijo_id"
								styleId="CProfesionalFijo_id"
								styleClass="cbCuadros colocaCategoriaAltaNew"
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
								styleClass="cbCuadros colocaEspecialidadAltaNew">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
									property="CEspecId" labelProperty="DNombre" />
							</html:select> <html:select property="CEspecFijo_id" styleId="CEspecFijo_id"
								styleClass="cbCuadros colocaEspecialidadAltaNew"
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
								styleClass="recuadroM colocaServicioAltaNew textoNormal" />
						</label> <br />
						<br />

						<!-- AREA / UNIDAD / PUESTO -->
						<label for="idVEspecialidad">&Aacute;rea / Unidad /
							Puesto: <html:text property="DPuesto" maxlength="250"
								styleClass="recuadroM colocaAreaUnidadAltaNew textoNormal" />
						</label> <br />
						<br />
					</div>
					<%}%>

					<!-- IR DETALLE -->
					<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)){%>

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

					<div class="cuadroFieldset colocaDatosVisGrande">
						<!-- REGIMEN JURIDICO DETALLE -->
						
						<% if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_GRS) )){ %>
						<label class="labelReg" >Convocatoria: </label>
						<span><bean:write name="OCAPNuevaSolicitudForm"
											property="DConvocatoria_nombre" /></span>
						<br/>
						<br/>
						<%} %>				
						
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
					<%}%>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> 2.2.- Situaci&oacute;n a 31
						de diciembre de <bean:write name="OCAPNuevaSolicitudForm"
										property="anioConvocatoria" />  (Seg&uacute;n Convocatoria) </legend>

					<!-- INSERTAR -->
					<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>

					<div class="cuadroFieldset altaUsuario">
						<!-- GRADO -->
						<label for="idVGrado">Grado al que opta: * <html:select
								property="CGrado_id" styleId="CGrado_id" styleClass="cbCuadros colocaGradoAltaNew"
								size="1" onchange="javascript:obtenerAniosGrado();">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_GRADOS_ALTA%>"
									property="CGradoId" labelProperty="DDescripcion" />
							</html:select>
						</label> <br />
						<br />

						<!-- PROCEDIMIENTO DE EVALUACION -->
						<label for="idVCategoria">Procedimiento de
							evaluaci&oacute;n por el que opta: * <html:select
								property="CProcedimientoId"
								styleClass="cbCuadros colocaSituacionAltaNew" size="1">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_PERSONAL%>"
									property="CProcedimientoId" labelProperty="DNombre" />
							</html:select>
						</label> <br />
						<br />

						<!-- SITUACION ADMINISTRATIVA -->
						<label class="labelReg" for="idVCategoria">Situaci&oacute;n
							Administrativa: * <html:select name="OCAPNuevaSolicitudForm"
								property="CSitAdministrativaId"
								styleClass="cbCuadros situAdminNew"
								onchange="mostrarDonde('N');">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_SIT_ADMIN%>"
									property="CSit_AdministrativaId" labelProperty="DNombre" />
							</html:select> <span class="labelReg" id="serviciosDonde"
							style="display: none; visibility: hidden;"> <span>¿Cu&aacute;l?:</span>
							<html:text name="OCAPNuevaSolicitudForm"
									styleClass="recuadroM cuadroDondeAlNew textoNormal"
									property="DSitAdministrativaOtros" maxlength="300" />
						</span>
						</label> <br />
						<br />

						<!-- PROVINCIA -->
						<label for="idVProvincia">Provincia: * <html:select
								property="CProvinciaCarrera_id" styleId="CProvinciaCarrera_id"
								styleClass="cbCuadros colocaProvinciaAltaNew" size="1"
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
								styleClass="cbCuadros colocaGerenciaAltaNew" size="1">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
									property="CGerenciaId" labelProperty="DNombre" />
							</html:select>
						</label> <br />
						<br />

						<!-- CENTRO DE TRABAJO -->
						<label for="idVCentroTrabajo">Centro de Trabajo: * <html:select
								property="CCentrotrabajo_id" styleId="CCentrotrabajo_id"
								styleClass="cbCuadros colocaCentroAltaNew" size="1">
								<html:option value="">Seleccione...</html:option>
								<html:options
									collection="<%=Constantes.COMBO_CENTRO_TRABAJO_CARRERA%>"
									property="CCentrotrabajoId" labelProperty="DNombre" />
							</html:select>
						</label> <br />
						<br />
					</div>
					<%}%>

					<!-- IR DETALLE -->
					<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)){%>

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
							* <span> <logic:notEmpty name="provincia2010">
									<bean:write name="provincia2010" />
								</logic:notEmpty> <logic:empty name="provincia2010">-</logic:empty>
						</span>
						</label> <br />
						<br />

						<!-- GERENCIA CARRERA DETALLE-->
						<label for="idVGerencia" class="colocaDatosVisGrande">Gerencia:
							* <span> <logic:notEmpty name="gerencia2010">
									<bean:write name="gerencia2010" />
								</logic:notEmpty> <logic:empty name="gerencia2010">-</logic:empty>
						</span>
						</label> <br />
						<br />

						<!-- CENTRO DE TRABAJO DETALLE-->
						<label for="idVCentroTrabajo" class="colocaDatosVisGrande">Centro
							de Trabajo: * <span> <logic:notEmpty name="centro2010">
									<bean:write name="centro2010" />
								</logic:notEmpty> <logic:empty name="centro2010">-</logic:empty>
						</span>
						</label> <br />
						<br />
					</div>
					<%}%>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> 2.3.- Situaci&oacute;n a
						fecha de publicaci&oacute;n de convocatoria </legend>

					<!-- INSERTAR -->
					<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>

					<div class="cuadroFieldset altaUsuario">
						<!-- SITUACION ADMINISTRATIVA -->
						<label class="labelReg" for="idVCategoria">Situaci&oacute;n
							Administrativa: * <html:select name="OCAPNuevaSolicitudForm"
								property="CSitAdmonActualId" styleClass="cbCuadros situAdminNew"
								onchange="mostrarDondeActual('N');">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_SIT_ADMIN%>"
									property="CSit_AdministrativaId" labelProperty="DNombre" />
							</html:select> <span class="labelReg" id="serviciosDondeActual"
							style="display: none; visibility: hidden;"> <span>¿Cu&aacute;l?:</span>
							<html:text name="OCAPNuevaSolicitudForm"
									styleClass="recuadroM cuadroDondeAlNew textoNormal"
									property="AOtraSitAdmonActual" maxlength="300" />
						</span>
						</label> <br />
						<br />

						<!-- PROVINCIA -->
						<label for="idVProvincia">Provincia: * <html:select
								property="CProvCarreraActualId" styleId="CProvCarreraActualId"
								styleClass="cbCuadros colocaProvinciaAltaNew" size="1"
								disabled="true">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_PROVINCIAS%>"
									property="CProvinciaId" labelProperty="DProvincia" />
							</html:select> <html:hidden property="CProvCarreraActualId" />
						</label> <br />
						<br />

						<!-- GERENCIA -->
						<label for="idVGerencia">Gerencia: * <html:select
								property="CGerenciaActualId" styleId="CGerenciaActualId"
								styleClass="cbCuadros colocaGerenciaAltaNew" size="1"
								disabled="true">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_GERENCIA_ACTUAL%>"
									property="CGerenciaId" labelProperty="DNombre" />
							</html:select> <html:hidden property="CGerenciaActualId" />
						</label> <br />
						<br />

						<!-- CENTRO DE TRABAJO -->
						<label for="idVCentroTrabajo">Centro de Trabajo: * <html:select
								property="CCentroTrabActualId" styleId="CCentroTrabActualId"
								styleClass="cbCuadros colocaCentroAltaNew" size="1">
								<html:option value="">Seleccione...</html:option>
								<html:options
									collection="<%=Constantes.COMBO_CENTRO_TRABAJO_CARRERA_ACTUAL%>"
									property="CCentrotrabajoId" labelProperty="DNombre" />
							</html:select>
						</label> <br />
						<br />
					</div>
					<%}%>

					<!-- IR DETALLE -->
					<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)){%>

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
							<span> <logic:notEmpty name="OCAPNuevaSolicitudForm"
									property="DGerencia_nombre">
									<bean:write name="OCAPNuevaSolicitudForm"
										property="DGerencia_nombre" />
								</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
									property="DGerencia_nombre">-</logic:empty>
						</span>
						</label> <br />
						<br />

						<!-- CENTRO DE TRABAJO DETALLE-->
						<label for="idVCentroTrabajo" class="colocaDatosVisGrande">Centro
							de Trabajo: <span> <logic:notEmpty
									name="OCAPNuevaSolicitudForm" property="DCentrotrabajo_nombre">
									<bean:write name="OCAPNuevaSolicitudForm"
										property="DCentrotrabajo_nombre" />
								</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
									property="DCentrotrabajo_nombre">-</logic:empty>
						</span>
						</label> <br />
						<br />
					</div>
					<%}%>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> 2.4.- A&ntilde;os de
						ejercicio profesional como personal en el Sistema Nacional de
						Salud hasta el 31/12/<bean:write name="OCAPNuevaSolicitudForm"
										property="anioConvocatoria" /> (Seg&uacute;n Convocatoria) </legend>

					<!-- IR INSERTAR -->
					<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>

					<div class="cuadroFieldset altaUsuario">
						<!-- AÑOS DE EJERCICIO -->
						<div class="tiempoEjercicioNew">
							<span class="colocaNumAlta">A&ntilde;os de ejercicio en la
								categor&iacute;a profesional por la que se accede: * </span> <br />
							<br /> <label for="idVAnt">N&ordm; A&ntilde;os: </label>
							<html:text property="NAniosantiguedad"
								styleClass="recuadroM colocaAniosAltaNew textoNormal"
								maxlength="2" />
							<label>N&ordm; Meses: </label>
							<html:text property="NMesesantiguedad"
								styleClass="recuadroM colocaMesesAltaNew textoNormal"
								maxlength="2" />
							<label>N&ordm; D&iacute;as: </label>
							<html:text property="NDiasantiguedad"
								styleClass="recuadroM colocaDiasAltaNew textoNormal"
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
								styleClass="recuadroM colocaCentroAlta2 textoNormal" />
						</label> <br />
						<br />

						<!-- CATEGORIA / ESPECIALIDAD / CUERPO -->
						<label>Categor&iacute;a / Especialidad / Cuerpo: <html:text
								property="ACategoria" maxlength="200"
								styleClass="recuadroM colocaCategoriaEspecialidadAlta textoNormal" />
						</label> <br />
						<br />

						<!-- PROVINCIA -->
						<label>Provincia: <html:select property="AProvinciaCentro"
								styleClass="cbCuadros colocaProvinciaAlta3">
								<html:option value="">Seleccione...</html:option>
								<html:options
									collection="<%=Constantes.COMBO_TODAS_PROVINCIAS%>"
									property="DProvincia" labelProperty="DProvincia" />
							</html:select>
						</label>

						<!-- VINCULO -->
						<label>V&iacute;nculo: <html:select property="AVinculo"
								styleClass="cbCuadros colocaVinculoAltaNew">
								<html:option value="">Seleccione...</html:option>
								<html:optionsCollection name="<%=Constantes.COMBO_VINCULOS%>" />
							</html:select>
						</label> <br />
						<br />

						<!-- FECHA DE INICIO -->
						<label for="idVNAnnios">Fecha Inicio (dd/mm/aaaa): <html:text
								property="FInicio" maxlength="10"
								styleClass="recuadroM colocaFechaInicioSP textoNormal" /> <span>
								<a
								href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FInicio", document.forms[0].FInicio.value);'>
									<html:img src="imagenes/calendario.gif"
										styleClass="iconoCalendario" border="0" alt="Calendario" />
							</a>
						</span>
						</label>

						<!-- FECHA FIN -->
						<label class="fechaFinSP" for="idVNAnnios">Fecha Fin
							(dd/mm/aaaa): <html:text property="FFin" maxlength="10"
								styleClass="recuadroM colocaFechaFinSP textoNormal" /> <span>
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
						<script type="text/javascript">inicializarCentros();</script>
					</div>
					<%}%>

					<!-- IR DETALLE -->
					<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)){%>

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
						<br /> <label for="idVServicios"> Tiene servicios
							prestados en otro centro de trabajo distinto al actual: </label><br /> <select
							size="5" id="jspCadenaCentro" name="jspCadenaCentro"
							class="textoTituloGrisAlta2" readonly="true"></select>
						<html:hidden property="resumenCentros" />
						<script type="text/javascript">inicializarCentros();</script>
					</div>
					<%}%>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> 2.5.- Grado de carrera
						profesional reconocido </legend>

					<!-- IR INSERTAR -->
					<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>

					<div class="cuadroFieldset altaUsuario">
						<!-- GRADO QUE POSEE (MODALIDAD ANTERIOR) -->
						<label for="idVCategoria">Grado que posee: * <html:select
								name="OCAPNuevaSolicitudForm" property="CModAnteriorId"
								styleClass="cbCuadros modDesdeOpta"
								onchange="javascript:obtenerGradoModAnterior();">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_MOD_ANTERIOR%>"
									property="CModAnteriorId" labelProperty="DNombre" />
							</html:select>
						</label> <br />
						<br />
					</div>
					<%}%>

					<!-- DETALLE -->
					<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)){%>

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
					<%}%>
				</fieldset>

				<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)){
               if ((String)request.getAttribute("tipoAccionBis") != null && ((String)request.getAttribute("tipoAccionBis")).equals(Constantes.IR_DETALLE)){%>
				<logic:equal name="opcion" value="<%=Constantes.ACCION_TRAMITAR%>">
					<logic:equal name="OCAPNuevaSolicitudForm" property="BCierreSo"
						value="<%=Constantes.SI%>">
						<p class="aviso">En estos momentos la convocatoria se
							encuentra cerrada y no se pueden asociar usuarios.</p>
					</logic:equal>
					<logic:equal name="OCAPNuevaSolicitudForm" property="BCierreSo"
						value="<%=Constantes.NO%>">
						<br />
						<br />
						<div class="fechaGradoAlta2">
							<span class="letraMarron">Fecha de registro oficial
								(dd/mm/aaaa):</span>
							<html:text property="FRegistro_oficial"
								styleClass="recuadroM margenIzdaFechaAlta textoNormal"
								maxlength="10" />
							<span> <a
								href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FRegistro_oficial", document.forms[0].FRegistro_oficial.value);'>
									<html:img src="imagenes/calendario.gif"
										styleClass="iconoCalendario" border="0" alt="Calendario" />
							</a>
							</span> <br />
							<br />
						</div>
						<div class="fechaGradoAlta2">
							<span class="letraMarron">Hora de registro oficial
								(hh:mm:ss):</span>
							<html:text property="NHoraRegistro"
								styleClass="recuadroM margenIzdaFechaAlta textoNormal"
								maxlength="8" />
						</div>

						<fieldset>
							<legend class="tituloLegend"> Solicitantes existentes </legend>
							<br />
							<br />
							<bean:size id="tamano" name="OCAPNuevaSolicitudForm"
								property="listaUsuarios" />
							<logic:notEqual name="tamano" value="0">
								<table class="tablaUsuarios">
									<tr class="datosTitulo lineaBajaM">
										<td class="tituloNombre">Nombre</td>
										<td class="tituloApellidos">Apellidos</td>
										<td class="tituloIcono"></td>
									</tr>
									<logic:iterate id="listaTotal" name="OCAPNuevaSolicitudForm"
										property="listaUsuarios">
										<tr class="datosAsp">
											<td><div class="campoNombre">
													<bean:write name="listaTotal" property="DNombre" />
												</div></td>
											<td><div class="campoApellidos">
													<bean:write name="listaTotal" property="DApellido1" />
												</div></td>
											<td><a
												href="javascript:asociar(<bean:write name="listaTotal" property="CUsrId"/>);"><img
													src="imagenes/imagenes_ocap/icono_registrar.gif"
													alt="Asociar solicitante" /></a></td>
										</tr>
									</logic:iterate>
								</table>
							</logic:notEqual>
							<logic:equal name="tamano" value="0">
								<br />
								<table class="tablaNuevoExpediente">
									<tr class="datosAsp">
										<td class="nuevoExp">Nuevo solicitante</td>
										<td><a href="javascript:nuevoUsuario();"><img
												src="imagenes/imagenes_ocap/icono_registrar.gif"
												alt="Asociar solicitante" /></a></td>
									</tr>
								</table>
							</logic:equal>
						</fieldset>
					</logic:equal>
				</logic:equal>

				<logic:equal name="opcion" value="<%=Constantes.ACCION_REASOCIAR%>">
					<logic:notEqual name="OCAPNuevaSolicitudForm"
						property="FRegistro_oficial" value="">
						<div class="cuadroFieldset">
							<label for="idVProvincia">Fecha de registro oficial: <span
								class="textoDatos"><bean:write
										name="OCAPNuevaSolicitudForm" property="FRegistro_oficial" /></span>
								<html:hidden name="OCAPNuevaSolicitudForm"
									property="FRegistro_oficial" />
							</label>
						</div>
					</logic:notEqual>

					<bean:size id="tamanoUsu" name="OCAPNuevaSolicitudForm"
						property="listaUsuarios" />
					<fieldset>
						<legend class="tituloLegend"> Solicitantes existentes </legend>
						<br />
						<br />
						<logic:greaterThan name="tamanoUsu" value="1">
							<table class="tablaUsuarios">
								<tr class="datosTitulo lineaBajaM">
									<td class="tituloNombre">Nombre</td>
									<td class="tituloApellidos">Apellidos</td>
									<td class="tituloNumSolic">N&ordm; Solicitudes</td>
									<td class="tituloIcono"></td>
								</tr>
								<logic:iterate id="listaTotal" name="OCAPNuevaSolicitudForm"
									property="listaUsuarios">
									<bean:define id="idUsuarioAsociado"
										name="OCAPNuevaSolicitudForm" property="CUsr_id"
										type="java.lang.String" />
									<logic:notEqual name="listaTotal" property="CUsrId"
										value="<%=idUsuarioAsociado%>">
										<tr class="datosAsp">
											<td><div class="campoNombre">
													<bean:write name="listaTotal" property="DNombre" />
												</div></td>
											<td><div class="campoApellidos">
													<bean:write name="listaTotal" property="DApellido1" />
												</div></td>
											<td><div class="campoNumSolic">
													<bean:write name="listaTotal" property="numSolicitudes" />
												</div></td>
											<td><a
												href="javascript:reasociar(<bean:write name="listaTotal" property="CUsrId"/>);"><img
													src="imagenes/imagenes_ocap/icono_registrar.gif"
													alt="Asociar solicitante" /></a></td>
										</tr>
									</logic:notEqual>
								</logic:iterate>
							</table>
							<br />
						</logic:greaterThan>

						<logic:lessEqual name="tamanoUsu" value="1">
							<br />
							<span>No hay otros usuarios a los que asociar este
								expediente.</span>
							<br />
							<br />
						</logic:lessEqual>
						<html:hidden name="OCAPNuevaSolicitudForm"
							property="FRegistro_oficial" />
					</fieldset>
				</logic:equal>

				<logic:notEqual name="OCAPNuevaSolicitudForm"
					property="FRegistro_oficial" value="">
					<logic:equal name="opcion" value="<%=Constantes.VER_DETALLE%>">
						<div class="cuadroFieldset">
							<label for="idVProvincia">Fecha de registro oficial: <span
								class="textoDatos"><bean:write
										name="OCAPNuevaSolicitudForm" property="FRegistro_oficial" /></span>
								<html:hidden name="OCAPNuevaSolicitudForm"
									property="FRegistro_oficial" />
							</label>
						</div>
						<br />
					</logic:equal>
				</logic:notEqual>

				<logic:equal name="opcion" value="<%=Constantes.ACCION_CAMBIAR%>">
					<div class="cuadroFieldset">
						<label for="idVProvincia">Fecha de registro oficial: <span
							class="textoDatos"><bean:write
									name="OCAPNuevaSolicitudForm" property="FRegistro_oficial" /></span>
						</label>
						<html:hidden name="OCAPNuevaSolicitudForm"
							property="FRegistro_oficial" />
					</div>

					<logic:notEmpty name="OCAPNuevaSolicitudForm"
						property="historialProfesional">
						<fieldset>
							<legend class="tituloLegend"> Historial Profesional </legend>
							<div class="historialProfesional">
								<table class="tablaHistorial" summary="Historial Profesional">
									<tr>
										<th width="15%" scope="col" class="titulo">Grado</th>
										<th width="20%" scope="col" class="titulo">Procedimiento
										</th>
										<th width="35%" scope="col" class="titulo" nowrap="nowrap">
											Categor&iacute;a / Especialidad</th>
										<th width="15%" scope="col" class="titulo">Estado</th>
										<th width="15%" scope="col" class="titulo">F.
											Resoluci&oacute;n</th>
									</tr>
									<logic:iterate id="historial" name="OCAPNuevaSolicitudForm"
										property="historialProfesional"
										type="OCAPHistoricoProfesionalesOT">
										<tr>
											<td><bean:write name="historial" property="DGrado" /></td>
											<td><bean:write name="historial"
													property="DProcedimiento" /></td>
											<td><bean:write name="historial" property="DCategoria" /><br />
											<bean:write name="historial" property="DEspecialidad" /></td>
											<td><bean:write name="historial" property="DEstado" /></td>
											<td><bean:write name="historial" property="FResolucion" /></td>
										</tr>
									</logic:iterate>
								</table>
							</div>
						</fieldset>
					</logic:notEmpty>

					<fieldset>
						<legend class="tituloLegend"> Cambio de Estado </legend>
						<div class="cuadroFieldset">
							<!-- ESTADO ACTUAL -->
							<label for="idVGrado">Estado Actual: <span
								class="textoDatos"><bean:write
										name="OCAPNuevaSolicitudForm" property="CEstado" /></span>
							</label> <br />
							<br />
							<logic:equal name="penaliza" value="<%=Constantes.SI%>">
								<p class="aviso">
									Solicitud con evaluaci&oacute;n negativa en convocatorias
									anteriores (fecha
									<bean:write name="OCAPNuevaSolicitudForm" property="FRecGrado" />
									). Debe ser excluida. <br />No podr&aacute; optar a grado hasta
									<bean:write name="OCAPNuevaSolicitudForm"
										property="FFin_penaliza" />
								</p>
							</logic:equal>
							<br />
							<br />
							<% if  ((jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE)))){ %>
								<logic:equal name="OCAPNuevaSolicitudForm" property="BFechaFinPgp" value="<%=Constantes.NO%>">
									<logic:equal name="OCAPNuevaSolicitudForm" property="BCierreSo"	value="<%=Constantes.NO%>">
									<label for="idVProvincia">Estado Nuevo: <html:select
											property="CEstadoFiltro"
											styleClass="cbCuadros colocaNuevoEstado"
											onchange="mostrarOcultos();">
											<html:option value="">Seleccione...</html:option>
											<html:options collection="<%=Constantes.COMBO_ESTADOS%>"
												property="cEstadoId" labelProperty="DNombre" />
										</html:select> <br />
										<br />
										</label>
									</logic:equal>
								</logic:equal>
							<% }else{ %>
							<logic:equal name="OCAPNuevaSolicitudForm" property="BCierreSo"	value="<%=Constantes.NO%>">
								<label for="idVProvincia">Estado Nuevo: <html:select
										property="CEstadoFiltro"
										styleClass="cbCuadros colocaNuevoEstado"
										onchange="mostrarOcultos();">
										<html:option value="">Seleccione...</html:option>
										<html:options collection="<%=Constantes.COMBO_ESTADOS%>"
											property="cEstadoId" labelProperty="DNombre" />
									</html:select> <br />
								<br />
								</label>
							</logic:equal>
							<% } %>
							<span id="dMotivos" style="display: none; visibility: hidden;">
								<fieldset>
									<br />
									<br /> <span>Motivos de Exclusi&oacute;n:</span> <br />
									<br />
									<div class="listaMotivos">
										<logic:iterate
											name="<%=Constantes.LISTADO_MOTIVOS_EXCLUSION%>"
											id="motivoExclusion"
											type="es.jcyl.fqs.ocap.ot.motExclusion.OCAPMotExclusionOT"
											indexId="numMot">
											<div class="listaMotivosRow">
												<div class="listaMotivosCheck">
													<html:multibox name="OCAPNuevaSolicitudForm"
														property="CMotivosExclusion">
														<bean:write name="motivoExclusion" property="CExclusionId" />#<bean:write
															name="motivoExclusion" property="DNombre" />
													</html:multibox>
												</div>
												<div class="listaMotivosDes">
													<bean:write name="motivoExclusion" property="DDescripcion" />
												</div>
											</div>
										</logic:iterate>
										<div class="otrosMotivosExclusion">
											<span>Si selecciona 'Otros', debe indicar cuales:</span>
											<div class="espaciadorPeq"></div>
											<html:textarea property="DOtrosMotivosExclusion" rows="4"
												styleClass="otrosMotivosExclusionTA"
												onkeypress="javascript:return longMaxOtrosMotivosExclusion(this);"
												onclick="javascript:return longMaxOtrosMotivosExclusion(this);"
												onkeydown="javascript:return longMaxOtrosMotivosExclusion(this);"
												onkeyup="javascript:return longMaxOtrosMotivosExclusion(this);"></html:textarea>
											<div class="espaciadorPeq"></div>
											<span>(Texto <label id="longOtrosMotivosExclusion">0</label>
												/ <%=Constantes.LONG_OTROS_MOTIVOS_EXCLUSION%> caracteres)
											</span>
											<script type="text/javascript">longMaxOtrosMotivosExclusion(document.forms[0].DOtrosMotivosExclusion);</script>
										</div>
									</div>
								</fieldset>
							</span> <span id="dSubsana" style="display: none; visibility: hidden;">
								<fieldset>
									<legend class="tituloLegend"> Subsanaciones </legend>
									<h4>OBSERVACIONES:</h4>
									<span class="textoCaracteres colocaCarTADen">(Texto <label
										id="longObserv">0</label> / <%=Constantes.LONG_SUBSANACIONES%>
										caracteres)
									</span>
									<div class="espaciadorPeq"></div>
									<html:textarea property="APeticion" cols="" rows=""
										styleClass="taMediano colocaTASub"
										onkeypress="javascript:return longMaxObservaciones(this);"
										onclick="javascript:return longMaxObservaciones(this);"
										onkeydown="javascript:return longMaxObservaciones(this);"
										onkeyup="javascript:return longMaxObservaciones(this);"></html:textarea>
									<div class="espaciador"></div>
									<script language="javascript">longMaxObservaciones(document.forms[0].APeticion);</script>
								</fieldset>
							</span> <span id="dAlega" style="display: none; visibility: hidden;">
								<fieldset>
									<legend class="tituloLegend"> Alegaciones </legend>
									<div class="cuadroFieldset">
										<span class="letraMarron">Fecha de registro oficial: *
											(dd/mm/aaaa) </span>
										<html:text property="FInconf_solic"
											styleClass="recuadroM textoNormal margenIzdaFechaAlta"
											maxlength="10" />
										<span> <a
											href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FInconf_solic", document.forms[0].FInconf_solic.value);'><html:img
													src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
										</span>
										<div class="espaciador"></div>
										<span class="letraMarron">Hora de registro oficial:
											(hh:mm:ss) </span>
										<html:text property="NHoraAlega"
											styleClass="recuadroM textoNormal margenIzdaFechaAlta3"
											maxlength="8" />

										<h4>MOTIVOS:</h4>
										<span class="textoCaracteres colocaCarTADen CarTADen2">(Texto
											<label id="longMotiv">0</label> / <%=Constantes.LONG_MOTIV_INCONF%>
											caracteres)
										</span>
										<div class="limpiadora"></div>
										<html:textarea property="DMotivo_inconf_solic" cols="" rows=""
											styleClass="taMediano colocaTADen"
											onkeypress="javascript:return longMaxMotivos(this);"
											onclick="javascript:return longMaxMotivos(this);"
											onkeydown="javascript:return longMaxMotivos(this);"
											onkeyup="javascript:return longMaxMotivos(this);"></html:textarea>
										<div class="espaciador"></div>
										<script language="javascript">longMaxMotivos(document.forms[0].DMotivo_inconf_solic);</script>
									</div>
								</fieldset>
							</span>
							<logic:equal name="OCAPNuevaSolicitudForm" property="CEstadoId"
								value="<%=Long.toString(Constantes.ESTADO_PENDIENTE_ALEGA) %>">
								<fieldset>
									<legend class="tituloLegend"> Alegaciones </legend>
									<div class="espaciador"></div>
									<label for="idVProvincia">Fecha de registro oficial: <span
										class="textoDatos"><bean:write
												name="OCAPNuevaSolicitudForm" property="FInconf_solic" /></span>
									</label> <br />
									<h4>MOTIVOS:</h4>
									<div class="textoMotivoAlegacion" for="idVProvincia">
										<div class="textoDatos">
											<bean:write name="OCAPNuevaSolicitudForm"
												property="DMotivo_inconf_solic" filter="false" />
										</div>
									</div>
									<br />
									<br />
								</fieldset>
							</logic:equal>
							<logic:equal name="OCAPNuevaSolicitudForm" property="BCierreSo"
								value="<%=Constantes.SI%>">
								<br />
								<p class="aviso">En estos momentos la convocatoria se
									encuentra cerrada y no se pueden realizar cambios de estado.</p>
							</logic:equal>
						</div>
					</fieldset>
					<script language="javascript" type="text/javascript">mostrarOcultos();</script>
				</logic:equal>
				<%} else if ((String)request.getAttribute("tipoAccionBis") != null && ((String)request.getAttribute("tipoAccionBis")).equals(Constantes.FASE_MC)){%>
				<logic:equal name="opcion" value="<%=Constantes.GRS_FECHAS%>">
					<html:hidden name="OCAPNuevaSolicitudForm" property="FInicio_cc" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="FFin_cc" />
					<html:hidden name="OCAPNuevaSolicitudForm" property="FInicio_mc" />
					<fieldset>
						<div class="cuadroFieldset">
							<div class="contenedorFechas">
								<span class="letraMarron"> Fecha de fin de
									autoevaluaci&oacute;n de m&eacute;ritos: (dd/mm/aaaa) </span>
								<html:text property="FFin_mc" maxlength="10"
									styleClass="recuadroM textoNormal margenF1" />
								<span> <a
									href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FFin_mc", document.forms[0].FFin_mc.value);'><html:img
											src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
								</span>
							</div>
							<div class="contenedorFechas">
								<span class="letraMarron"> Fecha de inicio de alegaciones
									de MC: (dd/mm/aaaa) </span>
								<html:text property="FInicio_eval_mc" maxlength="10"
									styleClass="recuadroM textoNormal margenF2" />
								<span> <a
									href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FInicio_eval_mc", document.forms[0].FInicio_eval_mc.value);'><html:img
											src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
								</span>
							</div>
							<div class="contenedorFechas">
								<span class="letraMarron"> Fecha de listado definitivo de
									MC: (dd/mm/aaaa) </span>
								<html:text property="FFin_eval_mc" maxlength="10"
									styleClass="recuadroM textoNormal margenF3" />
								<span> <a
									href='javascript:show_Calendario("OCAPNuevaSolicitudForm", "FFin_eval_mc", document.forms[0].FFin_eval_mc.value);'><html:img
											src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
								</span>
							</div>
						</div>
					</fieldset>
					<script type="text/javascript">
                        var fechaAlegaciones = document.forms[0].FInicio_eval_mc.value; 
                        var fechaDefinitiva = document.forms[0].FFin_eval_mc.value;
                     </script>
				</logic:equal>
				<logic:equal name="opcion" value="<%=Constantes.GRS_EDC%>">
					<fieldset>
						<legend class="tituloLegend"> Historial de Convocatoria </legend>
						<div class="cuadroFieldset">
							<label for="idVProvincia">Fecha de Registro de solicitud:
								<span class="textoDatos"><bean:write
										name="OCAPNuevaSolicitudForm" property="FRegistro_oficial" /></span>
							</label> <br />
							<br /> <label for="idVProvincia">Fecha de
								Aceptaci&oacute;n de solicitud: <span class="textoDatos"><bean:write
										name="OCAPNuevaSolicitudForm" property="FAceptac_solic" /></span>
							</label> <br />
							<br /> <label for="idVProvincia">Fecha de Inicio de MC: <span
								class="textoDatos"><bean:write
										name="OCAPNuevaSolicitudForm" property="FInicio_mc" /></span>
							</label> <br />
							<br /> <label for="idVProvincia">Fecha de Fin de MC: <span
								class="textoDatos"><bean:write
										name="OCAPNuevaSolicitudForm" property="FFin_mc" /></span>
							</label> <br />
							<br />
							<logic:equal name="excluir" value="<%=Constantes.SI%>">
								<label for="idVProvincia">Fecha de Aceptaci&oacute;n CC:
									<span class="textoDatos"><bean:write
											name="OCAPNuevaSolicitudForm" property="FAceptacionCC" /></span>
								</label>
							</logic:equal>
							<logic:equal name="excluir" value="<%=Constantes.NO%>">
								<label for="idVProvincia">Fecha de Negaci&oacute;n de
									MC: <span class="textoDatos"><bean:write
											name="OCAPNuevaSolicitudForm" property="FRespuesta_inconf_mc" /></span>
								</label>
							</logic:equal>
							<br />
							<br />
							<logic:equal name="excluir" value="<%=Constantes.SI%>">
								<label for="idVProvincia">Fecha de Inicio de EDC: <span
									class="textoDatos"><bean:write
											name="OCAPNuevaSolicitudForm" property="FInicio_ca" /></span>
								</label>
								<br />
								<br />
								<label for="idVProvincia">Fecha de Fin de EDC: <span
									class="textoDatos"><bean:write
											name="OCAPNuevaSolicitudForm" property="FFin_ca" /></span>
								</label>
								<br />
								<br />
								<span>Excluir del proceso EDC: </span>
							</logic:equal>
							<logic:equal name="excluir" value="<%=Constantes.NO%>">
								<span>Incluir en el proceso EDC: </span>
							</logic:equal>

							<span>S&iacute;</span>
							<html:radio name="OCAPNuevaSolicitudForm" property="BRespuesta"
								value="<%=Constantes.SI%>" onclick="mostrarObserva('Y');" />
							<span>No</span>
							<html:radio name="OCAPNuevaSolicitudForm" property="BRespuesta"
								value="<%=Constantes.NO%>" onclick="mostrarObserva('N');" />
							<div id="observacion" style="display: none; visibility: hidden">
								<h4>OBSERVACIONES:</h4>
								<span class="textoCaracteres colocaCarTADen">(Texto <label
									id="longMotivosCambio">0</label> / <%=Constantes.LONG_MOTIVOS_CAMBIO%>
									caracteres)
								</span>
								<div class="espaciadorPeq"></div>
								<html:textarea property="DMotivosCambio" cols="" rows=""
									styleClass="taMediano colocaTASub"
									onkeypress="javascript:return longMaxMotivosCambio(this);"
									onclick="javascript:return longMaxMotivosCambio(this);"
									onkeydown="javascript:return longMaxMotivosCambio(this);"
									onkeyup="javascript:return longMaxMotivosCambio(this);"></html:textarea>
								<div class="espaciador"></div>
								<script type="text/javascript">longMaxMotivosCambio(document.forms[0].DMotivosCambio);</script>
							</div>
							<div id="fechas" style="display: none; visibility: hidden">
								<html:hidden name="OCAPNuevaSolicitudForm" property="FInicio_ca" />
								<html:hidden name="OCAPNuevaSolicitudForm" property="FFin_ca" />
								<label for="idVProvincia">Fecha de Inicio de EDC: <span><bean:write
											name="OCAPNuevaSolicitudForm" property="FInicio_ca" /></span>
								</label> <br />
								<br /> <label for="idVProvincia">Fecha de Fin de EDC: <span><bean:write
											name="OCAPNuevaSolicitudForm" property="FFin_ca" /></span>
								</label>
							</div>
						</div>
					</fieldset>
				</logic:equal>
				<%}%>
				<%}%>
				<div class="limpiadora"></div>
				<%if ( ((String)request.getAttribute("tipoAccionBis") != null && !((String)request.getAttribute("tipoAccionBis")).equals(Constantes.IR_DETALLE) && !((String)request.getAttribute("tipoAccionBis")).equals(Constantes.FASE_MC))){%>
				<logic:equal name="esUsuario" value="<%=Constantes.SI%>">
					<fieldset>
						<div class="cuadroFieldsetLOPD">
							<p class="textoNoegrita margenDcho10">DECLARO
								RESPONSABLEMENTE:</p>
							<p class="textoNormal margenDcho10">
								<input type="checkbox" name="checkRequisitos"
									onclick="activarAceptar()" />&nbsp;&nbsp;&nbsp;Reunir los
								requisitos para optar al Grado de Carrera solicitado. <br /> <input
									type="checkbox" name="checkDatos" onclick="activarAceptar()" />&nbsp;&nbsp;&nbsp;Que los datos consignados en la presente solicitud y documentos
								que se acompa&ntilde;an son ciertos. <br />
								<input
									type="checkbox" name="checkCompromiso" onclick="activarAceptar()" />&nbsp;&nbsp;&nbsp;El compromiso de facilitar cuantos documentos y colaboraci&oacute;n me sea requerida por el Comit&eacute; Espec&iacute;fico de 
   Instituciones Sanitarias a lo largo del proceso de evaluaci&oacute;n. <br />
								<br />
							</p>
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
								Doy mi CONSENTIMIENTO para que los datos e informaciones aportados en el  presente 
								procedimiento, sean almacenados  y codificados con la finalidad de su posterior utilizaci&oacute;n
								 en los procedimientos administrativos para Carrera Profesional en el que fueran requeridos,
								  de conformidad con lo establecido en el art&iacute;culo 6 y siguientes del Reglamento (UE) 2016/679
								   del Parlamento Europeo y del Consejo, de 27 de abril de 2016, relativo a la protecci&oacute;n de 
								   las personas f&iacute;sicas en lo que respecta al tratamiento de datos personales y a la libre 
								   circulaci&oacute;n de estos datos (RGPD).  <br />
								<br />
							<div class="textoCentrado">
								<input type="checkbox" name="acepto" value="N"
									onclick="activarAceptar()" /> <span
									class="textoNegrita textoColorM"> Est&aacute; de acuerdo
								</span>
								<div class="espaciadorPeq"></div>
							</div>
							</p>
						</div>
					</fieldset>
				</logic:equal>
				<logic:notEqual name="esUsuario" value="<%=Constantes.SI%>">
					<fieldset>
						<div class="cuadroFieldsetLOPD">
							<p class="textoNormal margenDcho10">
								El solicitante ha indicado expresamente el CONSENTIMIENTO para que los datos e informaciones aportados en el  presente 
								procedimiento, sean almacenados  y codificados con la finalidad de su posterior utilizaci&oacute;n
								 en los procedimientos administrativos para Carrera Profesional en el que fueran requeridos,
								  de conformidad con lo establecido en el art&iacute;culo 6 y siguientes del Reglamento (UE) 2016/679
								   del Parlamento Europeo y del Consejo, de 27 de abril de 2016, relativo a la protecci&oacute;n de 
								   las personas f&iacute;sicas en lo que respecta al tratamiento de datos personales y a la libre 
								   circulaci&oacute;n de estos datos (RGPD). <br />
								<br />

								<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>
							
							<div class="textoCentrado">
								<html:radio name="OCAPNuevaSolicitudForm"
									property="BLopdSolicitud" value="<%=Constantes.SI%>" />
								<span class="textoNegrita textoColorM textoRadio"> Si </span>
								<html:radio name="OCAPNuevaSolicitudForm"
									property="BLopdSolicitud" value="<%=Constantes.NO%>" />
								<span class="textoNegrita textoColorM textoRadio"> No </span>
							</div>
							<%}%>

							<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)){%>
							<div class="textoCentrado">
								<html:radio name="OCAPNuevaSolicitudForm"
									property="BLopdSolicitud" value="<%=Constantes.SI%>"
									disabled="true" />
								<span class="textoNegrita textoColorM textoRadio"> Si </span>
								<html:radio name="OCAPNuevaSolicitudForm"
									property="BLopdSolicitud" value="<%=Constantes.NO%>"
									disabled="true" />
								<span class="textoNegrita textoColorM textoRadio"> No </span>
								<html:hidden property="BLopdSolicitud" />
							</div>
							<%}%>

							</p>
						</div>
					</fieldset>
				</logic:notEqual>
				<%}%>

				<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>
				<div class="espaciadorPeq"></div>
				<p>Los campos se&ntilde;alados con * son obligatorios</p>
				<div class="limpiadora"></div>
				<div class="botonesPagina">
					<logic:equal name="esUsuario" value="<%=Constantes.SI%>">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviar('PaginaInicio.do');"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" /> <span>
										Cancelar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<div id="ocultarAceptar"
							style="display: none; visibility: hidden;">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:aceptarSolicitud();"> <img
										src="imagenes/imagenes_ocap/flecha_correcto.gif"
										alt="Generar PDF" /> <span> Aceptar </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</div>
					</logic:equal>
					<logic:notEqual name="esUsuario" value="<%=Constantes.SI%>">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviar('OCAPNuevaSolicitud.do?accion=buscarSolicitudesDNI&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');">
									<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" />
									<span> Cancelar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:aceptarSolicitud();"> <img
									src="imagenes/imagenes_ocap/flecha_correcto.gif"
									alt="Aceptar Solicitud" /> <span> Aceptar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</logic:notEqual>
				</div>

				<%}%>

				<div class="espaciadorPeq"></div>

				<%if ((String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)) {
               if  ((String)request.getAttribute("tipoAccionBis") != null && ((String)request.getAttribute("tipoAccionBis")).equals(Constantes.SI)){%>
				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:registrarSolicitud('<%=Constantes.NO%>');">
								<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
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
				<%}else if  ((String)request.getAttribute("opcion") != null && ((String)request.getAttribute("opcion")).equals(Constantes.ACCION_CAMBIAR)){%>
				<div class="botonesPagina">
					<% if  ((jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE)))){ %>
						<logic:equal name="OCAPNuevaSolicitudForm" property="BFechaFinPgp" value="<%=Constantes.NO%>">
							<logic:equal name="OCAPNuevaSolicitudForm" property="BCierreSo"	value="<%=Constantes.NO%>">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:cambiarEstado();"> <img
									src="imagenes/imagenes_ocap/flecha_correcto.gif"
									alt="Cambiar Estado" /> <span> Cambiar Estado </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						</logic:equal>
					</logic:equal>
					<% }else{ %>
					<logic:equal name="OCAPNuevaSolicitudForm" property="BCierreSo"	value="<%=Constantes.NO%>">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:cambiarEstado();"> <img
									src="imagenes/imagenes_ocap/flecha_correcto.gif"
									alt="Cambiar Estado" /> <span> Cambiar Estado </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						</logic:equal>
					<% } %>
					<% if  ((String)request.getAttribute("vuelta") != null && ((String)request.getAttribute("vuelta")).equals(Constantes.VUELTA_SOL)){%>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviar('OCAPNuevaSolicitud.do?accion=buscarSolicitudesDNI&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');">
								<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" />
								<span> Volver </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<% } else if  ((String)request.getAttribute("vuelta") != null && ((String)request.getAttribute("vuelta")).equals(Constantes.VUELTA_ALTA)){%>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:borrarForm();enviar('OCAPNuevaSolicitud.do?accion=irInsertar');">
								<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" />
								<span> Volver </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<% } else { //Vuelta = Cosntantes.VUELTA_EXP %>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviar('OCAPNuevaSolicitud.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');">
								<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" />
								<span> Volver </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<% } %>
					<logic:equal name="OCAPNuevaSolicitudForm" property="CEstadoId"
						value="<%=Long.toString(Constantes.ESTADO_PENDIENTE_SUBSANA)%>">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviar('OCAPNuevaSolicitud.do?accion=generarPDFSubsanacion');">
									<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
									alt="PDF Subsanaci&oacute;n" /> <span> Generar PDF
										Subsanaci&oacute;n </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</logic:equal>
					<%}else{%>
					<div class="botonesPagina">
						<logic:notEqual name="opcion" value="<%=Constantes.GRS_FECHAS%>">
							<logic:notEqual name="opcion" value="<%=Constantes.GRS_EDC%>">
								<% if  ((String)request.getAttribute("vuelta") != null && ((String)request.getAttribute("vuelta")).equals(Constantes.VUELTA_ALTA)){%>
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="javascript:borrarForm();enviar('OCAPNuevaSolicitud.do?accion=irInsertar');">
											<img src="imagenes/imagenes_ocap/aspa_roja.gif"
											alt="Cancelar" /> <span> Cancelar </span>
									</a>
									</span> <span class="derBoton"></span>
								</div>
								<% } else if  ((String)request.getAttribute("vuelta") != null && ((String)request.getAttribute("vuelta")).equals(Constantes.VUELTA_EXP)){%>
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="javascript:enviar('OCAPNuevaSolicitud.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');">
											<img src="imagenes/imagenes_ocap/aspa_roja.gif"
											alt="Cancelar" /> <span> Cancelar </span>
									</a>
									</span> <span class="derBoton"></span>
								</div>
								<% } else { %>
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="javascript:enviar('OCAPNuevaSolicitud.do?accion=buscarSolicitudesDNI&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');">
											<img src="imagenes/imagenes_ocap/aspa_roja.gif"
											alt="Cancelar" /> <span> Cancelar </span>
									</a>
									</span> <span class="derBoton"></span>
								</div>
								<% } %>
							</logic:notEqual>
						</logic:notEqual>
						<logic:equal name="opcion" value="<%=Constantes.GRS_FECHAS%>">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:modificarFecha();"> <img
										src="imagenes/imagenes_ocap/flecha_correcto.gif"
										alt="Registrar Solicitud" /> <span> Aceptar </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:enviar('OCAPNuevaSolicitud.do?accion=buscarAdmGrs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=request.getAttribute("opcion")%>');">
										<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" />
										<span> Cancelar </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</logic:equal>
						<logic:equal name="opcion" value="<%=Constantes.GRS_EDC%>">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:excluir();"> <img
										src="imagenes/imagenes_ocap/flecha_correcto.gif"
										alt="Excluir profesional" /> <span> Aceptar </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:enviar('OCAPNuevaSolicitud.do?accion=buscarAdmGrs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=request.getAttribute("opcion")%>');">
										<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" />
										<span> Cancelar </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</logic:equal>
					</div>
					<%}%>
					<%}%>
				
			</html:form>

		</div>
		<!-- /fin de ContenidoDCP1A_PGP -->
	</div>
	<!-- /Fin de Contenido -->

	<logic:equal name="tipoAccion" value="<%=Constantes.IR_INSERTAR%>">
		</div>
		<!-- /Fin de ocultarCampo -->
		<script type="text/javascript">
      mostrarDonde();
      mostrarDondeActual();
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
	</logic:equal>
</body>
