<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.modalidadAnterior.OCAPModalidadAnteriorOT"%>
	
<%@ page
	import="es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT"%>

<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="x-ua-compatible" content="IE=11">
<title>..: Obtenci&oacute;n Carrera Profesional :..</title>
</head>
<meta http-equiv="Content-Type"
	content="application/xhtml+xml; charset=ISO-8859-1" />
<link href="css/EstilosJCYLFW.css" rel="stylesheet" media="screen">
	<link href="css/ocap.css" rel="stylesheet" media="screen">
		<!--[if IE 6]>
        <link rel="stylesheet" type="text/css" href="css/ocap_ie6.css" media="all" title="Hoja de estilos especifica para IE 6" ></link>
    <![endif]-->
		<!--[if IE 7]>
        <link rel="stylesheet" type="text/css" href="css/ocap_ie7.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
    <![endif]-->
		<!--[if gt IE 7]>
        <link rel="stylesheet" type="text/css" href="css/ocap_ie7.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
        <link rel="stylesheet" type="text/css" href="css/publico_ie8.css" media="all" title="Hoja de estilos especifica para IE 7" ></link>
    <![endif]-->
		<link href="css/ocap_print.css" rel="stylesheet" media="print">
			<link href="css/publico.css" rel="stylesheet" media="screen">
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
				<script language="JavaScript" type="text/javascript"
				src="javascript/XHConn.js"></script>
				<script type="text/javascript">


function construirCombos(){


	cargarAnioConvocatoria('<bean:write name="OCAPNuevaSolicitudForm" property="CConvocatoriaId"/>');
   <%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>
         
         mostrarDonde('S');
         mostrarDondeActual('S');
         mostrarRegimen('S');
         
         //document.getElementById('CProfesional_id').disabled=true;
         //document.getElementById('CEspec_id').disabled=true;
         deshabilitar();
         //document.getElementById('CGerencia_id').disabled=true;
         //document.getElementById('CCentrotrabajo_id').disabled=true;
         deshabilitarGCT();
         deshabilitarGCTActual();
         //document.getElementById('CLocalidad_id').disabled=true;
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
    
    
    //OCAP-1528
     $(document).ready(function () {
		   $("#CGrado_id").cascade("#CConvocatoriaId",{        
			    ajax: {
			        url: 'OCAPNuevaSolicitud.do?accion=cargarGradoConvocatoria'
			    },
			    template: commonTemplate,
			    match: commonMatch
		  	})
  	  })
  	//FIN OCAP-1528



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
                //	url: 'OCAPNuevaSolicitud.do?accion=cargarLocalidadesAjax',
                	//data: "val=" + $('#CProvincia_id').val(),
                	//success: function(html) {
	                  // $("#CLocalidad_id").html(html);
                	//},
                	//error: function (e) {
	                 //   alert('Error Received: ' + e);
    	             // },
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
					<logic:iterate id="grados"
						name="<%=Constantes.COMBO_MOD_ANTERIOR%>"
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

function obtenerAniosGrado(){
   var longListaGrados = listaGradosJS.length;
   for (i=0; i < longListaGrados; i++) {
      if (document.forms[0].CGrado_id.value == listaGradosJS[i][0]) {
         document.forms[0].NAniosejercicio.value = listaGradosJS[i][1];
         //document.forms[0].CConvocatoriaId.value = listaGradosJS[i][2];
         document.forms[0].FFinRegistro.value = listaGradosJS[i][3];
      }
   }
//   enviar('OCAPNuevaSolicitud.do?accion=obtenerAniosGrado');
}   

function obtenerGradoModAnterior(){
   var longListaGradosAnt = listaGradosAnterioresJS.length;
   for (i=0; i < longListaGradosAnt; i++) {
      if (document.forms[0].CModAnteriorId.value == listaGradosAnterioresJS[i][0]) {
         document.forms[0].CGradoAnteriorId.value = listaGradosAnterioresJS[i][1];
      }
   }
}

function aceptarSolicitud(){
      if(validarFormulario(document.forms[0], 'S') && validarCaptcha()) {
         var aniosAnt = obtenerCifra(document.forms[0].NAniosantiguedad.value);
         var aniosEjer = obtenerCifra(document.forms[0].NAniosejercicio.value);    
         document.forms[0].resumenCentros.value=valorCombo(document.getElementById('jspCadenaCentro'));
         var mensaje ='';
         // No cumple la condicion de años minimos de antiguedad
         if(parseInt(aniosAnt) < parseInt(aniosEjer))
            mensaje ="El n\u00famero de a\u00f1os elegido es inferior al m\u00ednimo.\n";
         // El grado que solicita no se corresponde con el siguiente al que tiene
         if( (parseInt(document.forms[0].CGradoAnteriorId.value)+1) != parseInt(document.forms[0].CGrado_id.value))
            mensaje += "El grado que posee no es correcto para el grado que solicita.\n";
         if (mensaje != '') {
            mensaje +="\u00BFest\u00e1 seguro de querer registrar la solicitud?";
            if(confirm(mensaje)){
               //enviar('OCAPNuevaSolicitud.do?accion=insertar&irDetalle=NU');
               enviar('OCAPNuevaSolicitud.do?accion=irDetalleAltaPublico&irDetalle=NU');
            }
         }else{
            //enviar('OCAPNuevaSolicitud.do?accion=insertar&irDetalle=NU');
            enviar('OCAPNuevaSolicitud.do?accion=irDetalleAltaPublico&irDetalle=NU');
         }
      }
   }

function registrarSolicitud(){
   document.forms[0].resumenCentros.value = valorCombo(document.getElementById('jspCadenaCentro'))
   if(document.forms[0].bExisteSolicParaNifConv.value=="true"){
	   var confirmar =
			confirm('Existe una solicitud registrada para este NIF/NIE y convocatoria. ¿Desea sustituirla por la actual?');
	   if(confirmar){
		   enviar('OCAPNuevaSolicitud.do?accion=insertar&irDetalle=NU');
	   } else {
		   alert('La solicitud actual NO ha sido grabada.')
	   }
	} else {
	   enviar('OCAPNuevaSolicitud.do?accion=insertar&irDetalle=NU');
   }
}

function irAltaSolicitud(){
   enviar('OCAPNuevaSolicitud.do?accion=irSolicitud');
}

function validarCaptcha(){
   /*
      if (document.forms[0].codigoCaptcha.value==''){
         alert('Debe rellenar el c\u00F3digo de seguridad.');
         return false;
      }
      if (document.forms[0].codigoCaptcha.value!='<%=(String) session.getAttribute("key")%>'){
         alert('El  c\u00F3digo de seguridad no es correcto.');
         return false;
      }
   */
      return true;
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

function mostrarBotones(param) {
   if (param == '<%=Constantes.SI%>') {
      document.getElementById('ocultarBotones').style.display='';
      document.getElementById('ocultarBotones').style.visibility='visible';
   } else {
      document.getElementById('ocultarBotones').style.display='none';
      document.getElementById('ocultarBotones').style.visibility='hidden';
   }
}

function mostrarDonde(param) {
    if(document.forms[0].CSitAdministrativaId.value=='6') {
        document.getElementById('serviciosDonde').style.display='';
        document.getElementById('serviciosDonde').style.visibility='visible';
        if(param=='N'){
            //document.getElementById('DSitAdministrativaOtros').value='';
            document.forms[0].DSitAdministrativaOtros.value ='';
        }
    } else {
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

function limpiarCamposCentros() {
   formu.DNombreCentro.value = '';
   formu.AProvinciaCentro.value = '';
   formu.ACategoria.value = '';
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
$(document).ready(function() {
	construirCombos();
	 $.ajaxSetup({
	      cache: false
	    });

	    var timestamp = (new Date()).getTime();


	    $("#captchaRef").click(function() {

	        var timestamp = (new Date()).getTime();
	        var newSrc = $("#captcha_image").attr("src").split("?");
	        newSrc = newSrc[0] + "?" + timestamp;
	        $("#captcha_image").attr("src", newSrc);
	        $("#captcha_image").slideDown("fast");

	     });
	 });
	 
	 
var listaConvocatoriasAltaJS = new Array();
</script>

<logic:present name="<%=Constantes.COMBO_CONVOCATORIAS_ALTA%>">
		<logic:iterate id="convocatoria" name="<%=Constantes.COMBO_CONVOCATORIAS_ALTA%>" type="OCAPConvocatoriasOT">
			<script type="text/javascript">
         linea = new Array();
         linea[0] = '<bean:write name="convocatoria" property="CConvocatoriaId"/>';
         linea[1] = '<bean:write name="convocatoria" property="DAnioConvocatoria"/>';
         listaConvocatoriasAltaJS[listaConvocatoriasAltaJS.length] = linea;
      </script>
		</logic:iterate>
</logic:present> 

<script type="text/javascript">

function cargarAnioConvocatoria(idAnio){
	
	var anioConvocatoria ="";
	var longListaConvocatorias = listaConvocatoriasAltaJS.length;
        
   for (i=0; i < longListaConvocatorias; i++) {
		    
      if (idAnio == listaConvocatoriasAltaJS[i][0]) {    	    	         
    	  document.forms[0].anioConvocatoria.value = listaConvocatoriasAltaJS[i][1];    	      	      	 
      }else if(idAnio == -1){
    	  document.forms[0].anioConvocatoria.value ="";     
      }
   }
  
   anioConvocatoria = document.forms[0].anioConvocatoria.value;   
   document.getElementById("anio22").innerHTML = " 2.2.- Situaci&oacute;n a 31 de diciembre de "+anioConvocatoria+" (Seg&uacute;n Convocatoria)" ;  
   document.getElementById("anio24").innerHTML = " 2.4.- A&ntilde;os de ejercicio profesional como personal en el Sistema Nacional de Salud hasta el 31/12/"+anioConvocatoria+" (Seg&uacute;n Convocatoria)";

}

</script>

<body onload="construirCombos();">
	<div class="encuadre">
		<jsp:include page="/cabecera.jsp" flush="true" />
		<div class="marcoContenidoAccesible">
			<div class="contenidoAccesible">
				<div class="cuadroContenedor">
					<div class="contenido">
						<div class="contenidoDCP1A formularioPublico">
							<html:form action="/OCAPNuevaSolicitud.do">
								<html:hidden property="NAniosejercicio" />
								<html:hidden property="anioConvocatoria" />
								<%-- <html:hidden property="CConvocatoriaId" /> --%>
								<html:hidden property="FFinRegistro" />
								<html:hidden property="CGradoAnteriorId" />
								<html:hidden property="FPlazapropiedad" />
								<h2 class="tituloContenido">Formulario de Solicitud de
									Acceso a Grado de Carrera Profesional</h2>
								<div class="lineaBajaM"></div>
								<br />
								<html:errors />

								<logic:notEqual name="tipoAccion"
									value="<%=Constantes.IR_INSERTAR%>">
									<logic:equal name="tipoAccionBis" value="<%=Constantes.SI%>">
										<p class="ocultarCampo">
											Va a proceder a registrar la solicitud. Por favor, compruebe
											que los datos introducidos en la misma son correctos. Si
											alguno es err&oacute;neo deber&aacute; pulsar el bot&oacute;n
											Modificar. En caso contrario deber&aacute; pulsar el
											bot&oacute;n Registrar.<br />
										</p>
									</logic:equal>
								</logic:notEqual>
								
								<% 	if( session.getAttribute(Constantes.COMBO_CONVOCATORIAS_ALTA)==null || 
											((ArrayList)session.getAttribute(Constantes.COMBO_CONVOCATORIAS_ALTA)).size()==0 
											&& (!Constantes.VER_DETALLE.equals(request.getParameter("opcion"))) )
									{ %>
										<script type="text/javascript">alert("No hay ninguna convocatoria en plazo de Alta de Solicitudes.");</script>
										<legend class="tituloLegend"> No existe ninguna convocatoria en plazo de Alta de Solicitudes. </legend>
								<% 	} else { %>

								<h3 class="subTituloContenidoMC">1.- DATOS PERSONALES:</h3>
								<fieldset>
									<legend class="tituloLegend"> 1.1.- Datos personales </legend>

									<!-- IR INSERTAR -->
									<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>

									<div class="cuadroFieldset">
										<label for="idVApell1">Apellidos: * <html:text
												property="DApellido1"
												styleClass="recuadroM colocaApellidos1 textoNormal"
												maxlength="61" />
										</label>
										<!--
                  <label for="idVApell1"> Segundo Apellido: *
                     <html:text property="DApellido2"  styleClass="recuadroM colocaApellidos2 textoNormal" maxlength="30"/>
                  </label> 
                  <br/><br/>
                  -->
										<label for="idVNombre">Nombre: * <html:text
												property="DNombre"
												styleClass="recuadroM colocaNombre textoNormal"
												maxlength="30" />
										</label> <br />
										<br /> <label for="idVDNI">NIF/NIE: * <html:text
												name="OCAPNuevaSolicitudForm" property="CDniReal"
												styleClass="recuadroM colocaDNI textoNormal" maxlength="10" />
										</label> <label for="idVCorreo">Sexo: * <html:select
												styleClass="cbCuadros colocaSexo"
												name="OCAPNuevaSolicitudForm" property="BSexo">
												<html:option value="">Seleccione...</html:option>
												<html:option value="<%=Constantes.SEXO_HOMBRE_VALUE%>"><%=Constantes.SEXO_HOMBRE%></html:option>
												<html:option value="<%=Constantes.SEXO_MUJER_VALUE%>"><%=Constantes.SEXO_MUJER%></html:option>
											</html:select>
										</label> <br />
										<br /> <label for="idVTelefono">Tel&eacute;fono 1: *<html:text
												property="NTelefono1"
												styleClass="recuadroM colocaTelefono1Asterisco textoNormal"
												maxlength="9" />
										</label> <label for="idVTelefono">Tel&eacute;fono 2: <html:text
												property="NTelefono2"
												styleClass="recuadroM colocaTelefono2 textoNormal"
												maxlength="9" />
										</label> <br />
										<br /> <label for="idVCorreo">Correo
											Electr&oacute;nico: *<html:text property="DCorreoelectronico"
												styleClass="recuadroM colocaCorreoAsterisco textoNormal"
												maxlength="50" />
										</label> <br />
										<br /> <label for="idVCorreo">Domicilio, Calle o Plaza
											y N&ordm;: * <html:text property="DDomicilio"
												styleClass="recuadroM colocaDireccion textoNormal"
												maxlength="100" />
										</label> <br />
										<br /> <label for="idVProvincia">Provincia: * <html:select
												property="CProvincia_id" styleId="CProvincia_id"
												styleClass="cbCuadros colocaProv" size="1"
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
										</label> <label for="idVTelefono">C&oacute;digo Postal: * <html:text
												property="CPostalUsu"
												styleClass="recuadroM colocaCP textoNormal" maxlength="5" />
										</label>
									</div>
									<%}%>

									<!-- DETALLE -->
									<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)){%>

									<html:hidden name="OCAPNuevaSolicitudForm"
										property="DApellido1" />
									<html:hidden name="OCAPNuevaSolicitudForm" property="DNombre" />
									<html:hidden name="OCAPNuevaSolicitudForm" property="CDni" />
									<html:hidden name="OCAPNuevaSolicitudForm" property="CDniReal" />
									<html:hidden name="OCAPNuevaSolicitudForm"
										property="NTelefono1" />
									<html:hidden name="OCAPNuevaSolicitudForm"
										property="NTelefono2" />
									<html:hidden name="OCAPNuevaSolicitudForm"
										property="DCorreoelectronico" />
									<html:hidden name="OCAPNuevaSolicitudForm" property="BSexo" />
									<html:hidden name="OCAPNuevaSolicitudForm"
										property="DDomicilio" />
									<html:hidden name="OCAPNuevaSolicitudForm"
										property="CProvincia_id" />
									<html:hidden name="OCAPNuevaSolicitudForm"
										property="DProvincia" />
									<html:hidden name="OCAPNuevaSolicitudForm"
										property="CLocalidad_id" />
									<html:hidden name="OCAPNuevaSolicitudForm"
										property="DLocalidad" />
									<html:hidden name="OCAPNuevaSolicitudForm"
										property="CPostalUsu" />
									<html:hidden name="OCAPNuevaSolicitudForm"
										property="CSolicitudId" />
									<html:hidden name="OCAPNuevaSolicitudForm" property="CUsr_id" />
									<html:hidden name="OCAPNuevaSolicitudForm"
										property="CEstatutId" />
									<html:hidden name="OCAPNuevaSolicitudForm" property="bExisteSolicParaNifConv" />

									<div class="cuadroFieldset">
										<label for="idVApell1" class="colocaDatosVis">Apellidos:
											<span><bean:write name="OCAPNuevaSolicitudForm"
													property="DApellido1" /></span>
										</label> <label for="idVNombre" class="colocaDatosVis">Nombre:
											<span><bean:write name="OCAPNuevaSolicitudForm"
													property="DNombre" /></span>
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
													<bean:write name="OCAPNuevaSolicitudForm"
														property="DProvincia" />
												</logic:notEmpty>
										</span>
										</label> <label for="idVTelefono" class="colocaDatosVis">C&oacute;digo
											Postal: <span><bean:write
													name="OCAPNuevaSolicitudForm" property="CPostalUsu" /></span>
										</label> <br />
										<br /> <label for="idVLocalidades" class="colocaDatosVis">Localidad:
											<span> <logic:notEmpty name="OCAPNuevaSolicitudForm"
													property="DLocalidad">
													<bean:write name="OCAPNuevaSolicitudForm"
														property="DLocalidad" />
												</logic:notEmpty>
										</span>
										</label>
									</div>
									<%}%>
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

									<!-- IR INSERTAR -->
									<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>

									<div class="cuadroFieldset solicitudAltaGrado">
										<% 	if( session.getAttribute(Constantes.COMBO_CONVOCATORIAS_ALTA)==null || 
												((ArrayList)session.getAttribute(Constantes.COMBO_CONVOCATORIAS_ALTA)).size()==0)
											{ %>
												<script type="text/javascript">alert("No hay ninguna convocatoria en plazo de Alta de Solicitudes.");</script>
										<% 	} else { %>
												<!-- CONVOCATORIA -->
												<label class="labelReg" for="idVConvocatoria">
													Convocatoria: * <html:select property="CConvocatoriaId" styleId="CConvocatoriaId" onchange="javascript:cargarAnioConvocatoria(this.value);"
														styleClass="cbCuadros colocaConvocatoriaCBAl2" size="1">
														<html:option value="-1">Seleccione...</html:option>
														<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS_ALTA%>"
															property="CConvocatoriaId" labelProperty="DNombre" />
													</html:select>
												</label> <br />
												<br />
										<% 	} %>
										
										<!-- REGIMEN JURIDICO -->
										<label class="labelReg" for="idVCategoria">R&eacute;gimen
											jur&iacute;dico: * <html:select name="OCAPNuevaSolicitudForm"
												property="CJuridicoId" styleId="CJuridicoId"
												styleClass="cbCuadros colocaRegJuric"
												onchange="mostrarProfesional(); mostrarRegimen('N'); setTimeout('deshabilitar()',500);">
												<html:option value="">Seleccione...</html:option>
												<html:options
													collection="<%=Constantes.COMBO_REGIMEN_JURIDICO%>"
													property="CJuridicoId" labelProperty="DNombre" />
											</html:select> <span class="mostrarCampoOculto" id="dRegimenOtros"
											style="display: none; visibility: hidden;"> <span>&iquest;Cu&aacute;l?:</span>
											<html:text name="OCAPNuevaSolicitudForm"
													styleClass="cuadroOculto regJuricCual textoNormal"
													property="DRegimenJuridicoOtros" maxlength="100" />
										</span> <span class="mostrarCampoOculto" id="cajaEstatutarioFijo"
											style="display: none; visibility: hidden;"> <label>
													Tipo: * </label> <html:select name="OCAPNuevaSolicitudForm"
													property="CJuridicoCombo" styleId="CJuridicoCombo"
													styleClass="cbCuadros regJuridic"
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
												styleClass="cbCuadros colocaCategoria"
												style="display:;visibility:visible;">
												<html:option value="">Seleccione...</html:option>
												<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
													property="CProfesionalId" labelProperty="DNombre" />
											</html:select> <html:select property="CProfesionalFijo_id"
												styleId="CProfesionalFijo_id"
												styleClass="cbCuadros colocaCategoria"
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
												styleClass="cbCuadros colocaEspecialidad">
												<html:option value="">Seleccione...</html:option>
												<html:options
													collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
													property="CEspecId" labelProperty="DNombre" />
											</html:select> <html:select property="CEspecFijo_id"
												styleId="CEspecFijo_id"
												styleClass="cbCuadros colocaEspecialidad"
												style="display:none;visibility:hidden;">
												<html:option value="">Seleccione...</html:option>
												<html:options
													collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
													property="CEspecId" labelProperty="DNombre" />
											</html:select>
										</label> <br />
										<br />

										<!-- SERVICIO CARRERA -->
										<label for="idVEspecialidad">Servicio: <html:text
												property="DServicio" maxlength="250"
												styleClass="recuadroM colocaServicios textoNormal" />
										</label> <br />
										<br />

										<!-- AREA / UNIDAD / PUESTO -->
										<label for="idVEspecialidad">&Aacute;rea / Unidad /
											Puesto: <html:text property="DPuesto" maxlength="250"
												styleClass="recuadroM colocaAreaUnidad textoNormal" />
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
									<html:hidden property="DConvocatoria_nombre" />
									<html:hidden property="CConvocatoriaId" />

									<div class="cuadroFieldset solicitudAltaGrado">
									
										<label class="labelReg" for="idVConvocatoria">
											Convocatoria: <span><bean:write
													name="OCAPNuevaSolicitudForm" property="DConvocatoria_nombre" /></span>
										</label> <br />
										<br />
						
										<!-- REGIMEN JURIDICO DETALLE -->
										<label for="idVCategoria" class="colocaDatosVisGrande">R&eacute;gimen
											jur&iacute;dico: <logic:equal name="OCAPNuevaSolicitudForm"
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
													<bean:write name="OCAPNuevaSolicitudForm"
														property="DServicio" />
												</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
													property="DServicio">-</logic:empty>
										</span>
										</label> <br />
										<br />

										<!-- AREA / UNIDAD / PUESTO -->
										<label for="idVEspecialidad" class="colocaDatosVisGrande">&Aacute;rea
											/ Unidad / Puesto: <span> <logic:notEmpty
													name="OCAPNuevaSolicitudForm" property="DPuesto">
													<bean:write name="OCAPNuevaSolicitudForm"
														property="DPuesto" />
												</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
													property="DPuesto">-</logic:empty>
										</span>
										</label> <br />
										<br />
									</div>
									<% } %>
								</fieldset>

								<fieldset>
								<legend class="tituloLegend"  id="anio22" > 2.2.- Situaci&oacute;n a 31
										de diciembre de (Seg&uacute;n Convocatoria)</legend>
									

									<!-- IR INSERTAR -->
									<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>

									<div class="cuadroFieldset solicitudAltaGrado">
										<!-- GRADO -->
										<label for="idVGrado">Grado al que opta: * <html:select
												property="CGrado_id" styleId="CGrado_id" styleClass="cbCuadros colocaGrado"
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
												styleClass="cbCuadros colocaProcedimientoAlta" size="1">
												<html:option value="">Seleccione...</html:option>
												<html:options collection="<%=Constantes.COMBO_PERSONAL%>"
													property="CProcedimientoId" labelProperty="DNombre" />
											</html:select>
										</label> <br />
										<br />

										<!-- SITUACION ADMINISTRATIVA -->
										<label for="idVCategoria">Situaci&oacute;n
											Administrativa: * <html:select name="OCAPNuevaSolicitudForm"
												property="CSitAdministrativaId"
												styleClass="cbCuadros situAdmin"
												onchange="mostrarDonde('N');">
												<html:option value="">Seleccione...</html:option>
												<html:options collection="<%=Constantes.COMBO_SIT_ADMIN%>"
													property="CSit_AdministrativaId" labelProperty="DNombre" />
											</html:select> <span class="mostrarCampoOculto" id="serviciosDonde"
											style="display: none; visibility: hidden;"> <span>&iquest;Cu&aacute;l?:</span>
											<html:text name="OCAPNuevaSolicitudForm"
													styleClass="cuadroOculto regSituAdmCual textoNormal"
													property="DSitAdministrativaOtros" maxlength="300" />
										</span>
										</label> <br />
										<br />

										<!-- PROVINCIA -->
										<label for="idVProvincia">Provincia: * <html:select
												property="CProvinciaCarrera_id"
												styleId="CProvinciaCarrera_id"
												styleClass="cbCuadros colocaProvinciaCarrera" size="1"
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
												styleClass="cbCuadros colocaGerencia" size="1">
												<html:option value="">Seleccione...</html:option>
												<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
													property="CGerenciaId" labelProperty="DNombre" />
											</html:select>
										</label> <br />
										<br />

										<!-- CENTRO DE TRABAJO -->
										<label for="idVCentroTrabajo">Centro de Trabajo: * <html:select
												property="CCentrotrabajo_id" styleId="CCentrotrabajo_id"
												styleClass="cbCuadros colocaCentro" size="1">
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

									<div class="cuadroFieldset solicitudAltaGrado">
										<!-- GRADO DETALLE -->
										<label for="idVGrado" class="colocaDatosVis">Grado: <span><bean:write
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
													name="OCAPNuevaSolicitudForm"
													property="DCentrotrabajo_nombre">
													<bean:write name="OCAPNuevaSolicitudForm"
														property="DCentrotrabajo_nombre" />
												</logic:notEmpty> <logic:empty name="OCAPNuevaSolicitudForm"
													property="DCentrotrabajo_nombre">-</logic:empty>
										</span>
										</label> <br />
										<br />
									</div>
									<% } %>
								</fieldset>

								<fieldset>
									<legend class="tituloLegend"> 2.3.- Situaci&oacute;n a
										fecha de publicaci&oacute;n de convocatoria </legend>

									<!-- IR INSERTAR -->
									<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>

									<div class="cuadroFieldset solicitudAltaGrado">
										<!-- SITUACION ADMINISTRATIVA -->
										<label for="idVCategoria">Situaci&oacute;n
											Administrativa: * <html:select name="OCAPNuevaSolicitudForm"
												property="CSitAdmonActualId"
												styleClass="cbCuadros situAdmin"
												onchange="mostrarDondeActual('N');">
												<html:option value="">Seleccione...</html:option>
												<html:options collection="<%=Constantes.COMBO_SIT_ADMIN%>"
													property="CSit_AdministrativaId" labelProperty="DNombre" />
											</html:select> <span class="mostrarCampoOculto" id="serviciosDondeActual"
											style="display: none; visibility: hidden;"> <span>&iquest;Cu&aacute;l?:</span>
											<html:text name="OCAPNuevaSolicitudForm"
													styleClass="cuadroOculto regSituAdmCual textoNormal"
													property="AOtraSitAdmonActual" maxlength="300" />
										</span>
										</label> <br />
										<br />

										<!-- PROVINCIA -->
										<label for="idVProvincia">Provincia: * <html:select
												property="CProvCarreraActualId"
												styleId="CProvCarreraActualId"
												styleClass="cbCuadros colocaProvinciaCarrera" size="1"
												onchange="setTimeout('deshabilitarGCTActual()',500);">
												<html:option value="">Seleccione...</html:option>
												<html:options collection="<%=Constantes.COMBO_PROVINCIAS%>"
													property="CProvinciaId" labelProperty="DProvincia" />
											</html:select>
										</label> <br />
										<br />

										<!-- GERENCIA -->
										<label for="idVGerencia">Gerencia: * <html:select
												property="CGerenciaActualId" styleId="CGerenciaActualId"
												styleClass="cbCuadros colocaGerencia" size="1">
												<html:option value="">Seleccione...</html:option>
												<html:options
													collection="<%=Constantes.COMBO_GERENCIA_ACTUAL%>"
													property="CGerenciaId" labelProperty="DNombre" />
											</html:select>
										</label> <br />
										<br />

										<!-- CENTRO DE TRABAJO -->
										<label for="idVCentroTrabajo">Centro de Trabajo: * <html:select
												property="CCentroTrabActualId" styleId="CCentroTrabActualId"
												styleClass="cbCuadros colocaCentro" size="1">
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

									<div class="cuadroFieldset solicitudAltaGrado">
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
									<% } %>
								</fieldset>

								<fieldset>
																
									<legend class="tituloLegend" id="anio24" > 2.4.- A&ntilde;os de
										ejercicio profesional como personal en el Sistema Nacional de
										Salud hasta el 31/12/ (Seg&uacute;n Convocatoria) </legend>	
										

									<!-- IR INSERTAR -->
									<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>

									<div class="cuadroFieldset">
										<!-- ANIOS DE EJERCICIO -->
										<div class="tiempoEjercicio">
											<label for="idVAnt"> <span class="colocaNumAlta">A&ntilde;os
													de ejercicio en la categor&iacute;a profesional por la que
													se accede: * </span> <br />
											<br /> <label class="fechaPrimera">N&ordm;
													A&ntilde;os: <html:text property="NAniosantiguedad"
														styleClass="recuadroM colocaAniosAlta textoNormal"
														maxlength="2" />
											</label> <label>N&ordm; Meses: <html:text
														property="NMesesantiguedad"
														styleClass="recuadroM colocaMesesAlta textoNormal"
														maxlength="2" /></label> <label>N&ordm; D&iacute;as: <html:text
														property="NDiasantiguedad"
														styleClass="recuadroM colocaDiasAlta textoNormal"
														maxlength="2" /></label>
											</label>
										</div>
										<br />
										<div class="lineaBajaM"></div>
										<br /> <label for="idVServicios"> Tiene servicios
											prestados en otro centro de trabajo distinto al actual: </label> <br />
										<br />
										<!-- CENTRO DE TRABAJO -->
										<label>Centro de Trabajo: * <html:text
												property="DNombreCentro" maxlength="100"
												styleClass="recuadroM colocaCentroTrabajo textoNormal" />
										</label> <br />
										<br />

										<!-- CATEGORIA / ESPECIALIDAD / CUERPO -->
										<label>Categor&iacute;a / Especialidad / Cuerpo: <html:text
												property="ACategoria" maxlength="200"
												styleClass="recuadroM colocaCategoriaEspecialidad textoNormal" />
										</label> <br />
										<br />

										<!-- PROVINCIA -->
										<label>Provincia: <html:select
												property="AProvinciaCentro"
												styleClass="cbCuadros colocaProvinciaServiciosPrestados">
												<html:option value="">Seleccione...</html:option>
												<html:options
													collection="<%=Constantes.COMBO_TODAS_PROVINCIAS%>"
													property="DProvincia" labelProperty="DProvincia" />
											</html:select>
										</label>

										<!-- VINCULO -->
										<label>V&iacute;nculo: <html:select
												property="AVinculo" styleClass="cbCuadros colocaVinculo">
												<html:option value="">Seleccione...</html:option>
												<html:optionsCollection
													name="<%=Constantes.COMBO_VINCULOS%>" />
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

										<!-- BOTONES ANIADIR / MODIFICAR / ELIMINAR -->
										<!--<div class="colocaIE">-->
										<div
											class="botonesCertif2 botonesCertif2Docencia colocaBotonesAlta">
											<div class="botonAccion">
												<span class="izqBoton"></span> <span class="cenBoton">
													<input type="button" id="anadir" value=" A&ntilde;adir"
													property="anadir" onclick="anhCentros();" />
												</span> <span class="derBoton"></span>
											</div>
											<div class="botonAccion">
												<span class="izqBoton"></span> <span class="cenBoton">
													<input type="button" id="modificar" value="Modificar"
													property="modificar" onclick="modCentros();" />
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
										<!--</div>-->
										<select size="5" id="jspCadenaCentro" name="jspCadenaCentro"
											class="textoTituloGrisSol"></select>
										<html:hidden property="resumenCentros" />
										<script type="text/javascript">inicializarCentros();</script>
									</div>
									<%}%>

									<!-- IR DETALLE -->
									<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)){%>

									<div class="cuadroFieldset">
										<!-- AÑOS DE EJECICIO DETALLE -->
										<label for="idVAnt"> <span class="colocaNumAlta">A&ntilde;os
												de ejercicio en la categor&iacute;a profesional por la que
												se accede:</span> <label>N&ordm; A&ntilde;os: <span
												class="textoDatos"><bean:write
														name="OCAPNuevaSolicitudForm" property="NAniosantiguedad" /></span></label>
											<label>N&ordm; Meses: <span class="textoDatos"><bean:write
														name="OCAPNuevaSolicitudForm" property="NMesesantiguedad" /></span></label>
											<label>N&ordm; D&iacute;as: <span class="textoDatos"><bean:write
														name="OCAPNuevaSolicitudForm" property="NDiasantiguedad" /></span></label>
										</label> <br />
										<div class="lineaBajaM"></div>
										<br /> <label for="idVServicios"> Tiene servicios
											prestados en otro centro de trabajo distinto al actual: </label><br />
										<select size="5" id="jspCadenaCentro" name="jspCadenaCentro"
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

									<div class="cuadroFieldset">
										<!-- GRADO QUE POSEE (MODALIDAD ANTERIOR) -->
										<label for="idVCategoria">Grado que posee: * <html:select
												name="OCAPNuevaSolicitudForm" property="CModAnteriorId"
												styleClass="cbCuadros colocaGradoPosee"
												onchange="javascript:obtenerGradoModAnterior();">
												<html:option value="">Seleccione...</html:option>
												<html:options
													collection="<%=Constantes.COMBO_MOD_ANTERIOR%>"
													property="CModAnteriorId" labelProperty="DNombre" />
											</html:select>
										</label> <br />
										<br />
									</div>
									<%}%>

									<!-- DETALLE -->
									<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)){%>

									<div class="cuadroFieldset">
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

								<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_INSERTAR)){%>
								<fieldset>
									<div class="cuadroFieldsetLOPD">
										<p class="textoNoegrita margenDcho10">DECLARO
											RESPONSABLEMENTE:</p>
										<p class="textoNormal margenDcho10">
											<input type="checkbox" name="checkRequisitos"
												onclick="activarAceptar()" />&nbsp;&nbsp;&nbsp;Reunir los
											requisitos para optar al Grado de Carrera solicitado. <br />
											<input
									type="checkbox" name="checkDatos" onclick="activarAceptar()" />&nbsp;&nbsp;&nbsp;Que los datos consignados en la presente solicitud y documentos
								que se acompa&ntilde;an son ciertos. <br />
								<input
									type="checkbox" name="checkCompromiso" onclick="activarAceptar()" />&nbsp;&nbsp;&nbsp;El compromiso de facilitar cuantos documentos y colaboraci&oacute;n me sea requerida por el Comit&eacute; Espec&iacute;fico de 
   Instituciones Sanitarias a lo largo del proceso de evaluaci&oacute;n. <br /><br />
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
 								previstos en los anexos III y IV.
								<br /><br />
											Doy mi CONSENTIMIENTO para que los datos e informaciones aportados en el  presente 
								procedimiento, sean almacenados  y codificados con la finalidad de su posterior utilizaci&oacute;n
								 en los procedimientos administrativos para Carrera Profesional en el que fueran requeridos,
								  de conformidad con lo establecido en el art&iacute;culo 6 y siguientes del Reglamento (UE) 2016/679
								   del Parlamento Europeo y del Consejo, de 27 de abril de 2016, relativo a la protecci&oacute;n de 
								   las personas f&iacute;sicas en lo que respecta al tratamiento de datos personales y a la libre 
								   circulaci&oacute;n de estos datos (RGPD).  <br />
											<br />
										</p>
										<div class="textoCentrado">
											<input type="checkbox" name="acepto" value="N"
												onclick="activarAceptar()" /> <span
												class="textoNegrita textoColorM"> Est&aacute; de
												acuerdo </span>
											<div class="espaciadorPeq"></div>
										</div>
									</div>
								</fieldset>

								<p>Los campos se&ntilde;alados con * son obligatorios</p>
								<!-- CAPTCHA -->
								<p>
									Introduzca el siguiente c&oacute;digo de seguridad:
									<img src="jcaptcha.jpg" id="captcha_image" />
									<html:text name="OCAPNuevaSolicitudForm"
											property="codigoCaptcha" maxlength="15"
											styleClass="recuadroM campoCaptcha textoNormal" />
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a id="captchaRef"> 
												<img src="imagenes/imagenes_ocap/action_refresh.gif" alt="Otro Capcha" />
												<span> Generar otro c&oacute;digo de seguridad diferente </span>
											</a>
										</span> <span class="derBoton"></span>
									</div>
								</p>
								<span class="textoRojo"> <html:messages
										id="codigoCaptcha" property="codigoCaptcha" message="true">
										<bean:write name="codigoCaptcha" />
										<br />
									</html:messages>
								</span>

								<div class="capaPieSol"></div>
								<div class="botonesPagina">
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a href="inicio.jsp"> <img
												src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" />
												<span> Cancelar </span>
										</a>
										</span> <span class="derBoton"></span>
									</div>
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
								</div>
								<% } %>
								<%if ( (String)request.getAttribute("tipoAccion") != null && ((String)request.getAttribute("tipoAccion")).equals(Constantes.IR_DETALLE)){%>
								<div class="capaPieSol"></div>
								<div class="botonesPagina">
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a href="javascript:registrarSolicitud();"> <img
												src="imagenes/imagenes_ocap/flecha_correcto.gif"
												alt="Registrar Solicitud" /> <span> Registrar </span>
										</a>
										</span> <span class="derBoton"></span>
									</div>
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a href="javascript:irAltaSolicitud();"> <img
												src="imagenes/imagenes_ocap/aspa_roja.gif"
												alt="Modificar Solicitud" /> <span> Modificar </span>
										</a>
										</span> <span class="derBoton"></span>
									</div>
								</div>
								<% } %>
								<% } %>
							</html:form>
						</div>
						<!-- /fin de ContenidoDCP1A -->
					</div>
					<!-- /Fin de Contenido -->
				</div>
				<!-- Del marco cuadroContenedor -->
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

<script type="text/javascript">
<%if ((String)request.getAttribute("primeraSolic") != null && ((String)request.getAttribute("primeraSolic")).equals(Constantes.NO)){%>
    document.forms[0].acepto.checked = true;      
    document.getElementById('ocultarAceptar').style.display='';
    document.getElementById('ocultarAceptar').style.visibility='visible';     
<%}%>

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

<logic:equal name="tipoAccion" value="<%=Constantes.IR_INSERTAR%>">
	<script type="text/javascript">
      mostrarDonde();
      mostrarDondeActual();
   </script>
</logic:equal>
