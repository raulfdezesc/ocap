<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.meritosActividad.OCAPMeractividadOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.meritosModalidad.OCAPMerModalidadOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.expedienteMC.OCAPExpedientemcOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="java.util.ArrayList"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
  int i=0;
  ArrayList resumenCertificados = (ArrayList) request.getAttribute("resumenCertificados");
  int u=0;
%>

<script language="JavaScript" type="text/javascript" src="<html:rewrite page='/javascript/combos1.js'/>"></script>
<script language="javascript" type="text/javascript" src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript" src="<html:rewrite page='/javascript/fechas.js'/>"></script>
<script language="javascript" type="text/javascript" src="<html:rewrite page='/javascript/calendario.js'/>"></script>
<script language="javascript" type="text/javascript" src="<html:rewrite page='/javascript/formularios.js'/>"></script>


<script>
var acreditado = false;
  
function aceptarDocencia(){
   var index = 0;
   var correcto = true;
   var algunTipoDocencia = false;
   document.forms[0].resumenCertificados.value='';
   while (document.getElementById('jspCadenaCertificado' + index) != null && correcto){
      if(valorCombo(document.getElementById('jspCadenaCertificado' + index)) != '' && document.getElementById('CActividadId' + index).checked){
         document.forms[0].resumenCertificados.value=document.forms[0].resumenCertificados.value + document.getElementById('CActividadId' + index).value + "#" + valorCombo(document.getElementById('jspCadenaCertificado' + index)) + "%";
         algunTipoDocencia = true;
      }else if(valorCombo(document.getElementById('jspCadenaCertificado' + index)) == '' && document.getElementById('CActividadId' + index).checked) {
         correcto=false;
      }else{
         document.forms[0].resumenCertificados.value+= "%";
      }
      index = index + 1;
   }
   if (correcto && algunTipoDocencia)
      enviar('OCAPMeritos.do?accion=controlAccionDocenciaPost');
   else if (!correcto){
      if (acreditado)
         alert('Debe a\u00f1adir datos para todos los tipos de Docencia seleccionados');
      else
         alert('Debe a\u00f1adir fechas para todos los tipos de Docencia seleccionados');
   }else if (!algunTipoDocencia)
      alert('Debe seleccionar alg\u00fan tipo de Docencia a insertar. Si no desea insertar ninguno, pulse Cancelar.');

}

var NS4 = (navigator.appName == "Netscape" && parseInt(navigator.appVersion) >= 4);

function anhCertificado(index, actividadId) {
   formu = document.forms[0];  
   comboCertificados = document.getElementById('jspCadenaCertificado' + index);
   
   
   fechaConvocatoria = '<bean:write name="OCAPMeritosForm" property="FComprobMeritos" />'
   
   
   annioConvocatoria  =  fechaConvocatoria.substring(6, 10);
   
   if (acreditado && formu.DTitulo[index].value == ''){
      alert('Debe introducir una Unidad/Servicio/Centro.');
   }else{
   		var y = new Date().getFullYear();
      if(formu.FAnnio[index].value == ''){
         alert('Debe introducir un A\u00f1o de Inicio.');
      }else if(formu.FAnnio[index].value != '' && !esNumero(formu.FAnnio[index].value)){
         alert('El campo A\u00f1o de Inicio no tiene el formato correcto.');
      }else if(formu.FAnnio[index].value != '' && !comprobarAnioNoFuturo(formu.FAnnio[index].value)){
         alert('El campo A\u00f1o de Inicio debe ser inferior al a\u00f1o en curso.');      
      }else if (formu.FAnnio[index].value!='' && formu.FAnnio[index].value > annioConvocatoria){
     	alert('El campo A\u00f1o de Inicio debe ser inferior o igual al a\u00f1o de la convocatoria: ' +annioConvocatoria + '.' );  
      }else if(formu.FAnnio[index].value.length < 4){
      	//http://xamp.sacyl.es/jira/browse/OCAP-1477
      	 alert('El campo A\u00f1o de Inicio no tiene la longitud correcta.');
      }else if (formu.FAnnio[index].value.length == 4 && formu.FAnnio[index].value < y-100){
      	 alert("El campo A\u00f1o no puede tener una diferencia de más de 100 año respecto de la actual.")
      }
      
      else{
         var anioFin = 0;
         
         if (parseInt(document.forms[0].FAnnio[index].value))
            anioFin = parseInt(document.forms[0].FAnnio[index].value) + parseInt(1);

         if (acreditado){            
            var valor = formu.FAnnio[index].value + '$' + formu.DTitulo[index].value;
      
            var texto = formu.FAnnio[index].value + '---' +  formu.DTitulo[index].value;
         }else{
            if (actividadId == '<%=Constantes.ACT_TUTOR_ASOCIADO%>' || actividadId == '<%=Constantes.ACT_COLABORADOR_DOCENTE%>'){
               if (validarMesesDias(formu, index)){
                  var valor = formu.FAnnio[index].value + '$' + anioFin + '$' + formu.NMeses[index].value + '$' + formu.NDias[index].value;
                  var texto = formu.FAnnio[index].value + '---' +  anioFin + '---' + formu.NMeses[index].value + '---' + formu.NDias[index].value;
               } else
                  return;
            } else {
               var valor = formu.FAnnio[index].value + '$' + anioFin;
               var texto = formu.FAnnio[index].value + '---' +  anioFin; 
            }
         }
   
         var aux = '#' + valorCombo(document.getElementById('jspCadenaCertificado' + index));

         if ( aux.indexOf('#' + formu.FAnnio[index].value + '$') == -1 ) {          
            var campo = formu.jspCadenaCertificado;
            nueva_opcion(comboCertificados,texto,valor);
            nuevoCertificado(index, actividadId);
         } else  {
            alert('No se puede a\u00f1adir el a\u00f1o; porque ya existe');
         }
      }
   }
} //Fin anhCertificado

function validarMesesDias(formu, indice){
   // MESES ANTIGUEDAD
   var cadena = formu.NMeses[indice].value;
   cadena = trim(cadena);
   if(cadena=='') {
    formu.NMeses[indice].value = '0';
    cadena = '0';
   }
   
   if(!soloNumeros(formu.NMeses[indice])){
      alert('El campo \'Meses\' es err\u00f3neo.');
      return false;
   } else if (parseInt(cadena) > 12) {
      alert('El campo \'Meses\' no puede ser mayor que 12.');
      return false;
   }

   // DIAS ANTIGUEDAD
   var cadena = formu.NDias[indice].value;
   cadena = trim(cadena);
   if(cadena=='') {
    formu.NDias[indice].value = '0';
    cadena = '0';
   }
   if(!soloNumeros(formu.NDias[indice])){
      alert('El campo \'D\u00edas\' es err\u00f3neo.');
      return false;
   } else if (parseInt(cadena) > 30) {
      alert('El campo \'D\u00edas\' no puede ser mayor que 30.');
      return false;
   }
   if (formu.NMeses[indice].value == '0' && formu.NDias[indice].value == '0'){
      alert('El \'tiempo efectivo de tutor\u00EDa\' no puede ser nulo.');
      return false;
   } else if (formu.NMeses[indice].value == '12' && parseInt(cadena)>0){
      alert('El \'tiempo efectivo de tutor\u00EDa\' no puede ser mayor de 1 a\u00F1o.');
      return false;
   } else 
      return true;
}


function nuevoCertificado(index, actividadId) {
	formu = document.forms[0];
	formu.FAnnio[index].value='';
   if (acreditado)
      formu.DTitulo[index].value='';
   else if (actividadId == '<%=Constantes.ACT_TUTOR_ASOCIADO%>' || actividadId == '<%=Constantes.ACT_COLABORADOR_DOCENTE%>'){
      formu.NMeses[index].value = '';
      formu.NDias[index].value = '';
    }
}

function modCertificado(index, actividadId) {
	formu = document.forms[0];

   comboCertificados=objeto_combo('document.forms[0].jspCadenaCertificado' + index);

   if (comprueba_seleccion(document.getElementById('jspCadenaCertificado' + index))) {
    indice = document.getElementById('jspCadenaCertificado' + index).selectedIndex;
    var valor = comboCertificados.options[indice].value;
    var valor_aux = valor;     // variable para operar
    var DTitulo = valor_aux.substring(0, valor_aux.indexOf('<%=Constantes.SEPARADOR_2%>'));
    valor_aux = valor_aux.substring(valor_aux.indexOf('<%=Constantes.SEPARADOR_2%>') + 1);
    
    if (!acreditado && (actividadId == '<%=Constantes.ACT_TUTOR_ASOCIADO%>' || actividadId == '<%=Constantes.ACT_COLABORADOR_DOCENTE%>')){
      valor_aux = valor_aux.substring(valor_aux.indexOf('<%=Constantes.SEPARADOR_2%>') + 1);
      var NMeses = valor_aux.substring(0, valor_aux.indexOf('<%=Constantes.SEPARADOR_2%>'));
      valor_aux = valor_aux.substring(valor_aux.indexOf('<%=Constantes.SEPARADOR_2%>') + 1);
      var NDias = valor_aux;
    } else 
      var DDesc = valor_aux;
    
    formu.FAnnio[index].value = DTitulo;
    
    if (acreditado)
      formu.DTitulo[index].value = DDesc;
    else if (actividadId == '<%=Constantes.ACT_TUTOR_ASOCIADO%>' || actividadId == '<%=Constantes.ACT_COLABORADOR_DOCENTE%>'){
      formu.NMeses[index].value = NMeses;
      formu.NDias[index].value = NDias;
    }

	 combos_borrar(document.getElementById('jspCadenaCertificado' + index));
	}
}

function seleccionCertificados(){
   var seleccion = comprobarCheckCertificados();

   if(seleccion == '<%=Constantes.NO%>'){
      document.forms[0].FAnnio.disabled = true;
      if (acreditado)
         document.forms[0].DTitulo.disabled = true;
      document.forms[0].anadir.disabled = true;
      document.forms[0].modificar.disabled = true;
      document.forms[0].eliminar.disabled = true;
   }else if(seleccion == '<%=Constantes.SI%>'){
      document.forms[0].FAnnio.disabled = false;
      if (acreditado)
         document.forms[0].DTitulo.disabled = false;
      document.forms[0].anadir.disabled = false;
      document.forms[0].modificar.disabled = false;
      document.forms[0].eliminar.disabled = false;
   }
}

function comprobarCheckCertificados() {
   var numRadioSeleccionado;
   var accionEnviar='';
   if (document.forms[0].BCertificado.length>0) {
      numRadioSeleccionado=getCheckedRadios(document.forms[0].BCertificado);
      if (numRadioSeleccionado>=0)
         accionEnviar=getRadio(document.forms[0].BCertificado,numRadioSeleccionado);  
   } else if (document.forms[0].BCertificado.checked) {
      accionEnviar=document.forms[0].BCertificado.value;
   }
   return accionEnviar;
}

function activarCamposFecha(index){
   if(document.getElementById('CActividadId' + index).checked){
      document.forms[0].FAnnio[index].disabled = false;
      if (acreditado)
         document.forms[0].DTitulo[index].disabled = false;
      else if(document.getElementById('CActividadId' + index).value == '<%=Constantes.ACT_TUTOR_ASOCIADO%>' || document.getElementById('CActividadId' + index).value == '<%=Constantes.ACT_COLABORADOR_DOCENTE%>'){
         document.forms[0].NMeses[index].disabled = false;
         document.forms[0].NDias[index].disabled = false;
      }
      
      document.getElementById('jspCadenaCertificado' + index).disabled = false;
      document.getElementById('anadir' + index).disabled = false;
      document.getElementById('modificar' + index).disabled = false;
      document.getElementById('eliminar' + index).disabled = false;

   }else{
      document.forms[0].FAnnio[index].value = '';
      document.forms[0].FAnnio[index].disabled = true;
      if (acreditado){
         document.forms[0].DTitulo[index].disabled = true;
         document.forms[0].DTitulo[index].value = '';
      }
      if(document.getElementById('CActividadId' + index).value == '<%=Constantes.ACT_TUTOR_ASOCIADO%>' || document.getElementById('CActividadId' + index).value == '<%=Constantes.ACT_COLABORADOR_DOCENTE%>'){
         document.forms[0].NMeses[index].disabled = true;
         document.forms[0].NMeses[index].value = '';
         document.forms[0].NDias[index].disabled = true;
         document.forms[0].NDias[index].value = '';
      }
      
      document.getElementById('jspCadenaCertificado' + index).disabled = true;
      document.getElementById('anadir' + index).disabled = true;
      document.getElementById('modificar' + index).disabled = true;
      document.getElementById('eliminar' + index).disabled = true;    
   }
}

function activarMerito(index){
   document.getElementById('CActividadId' + index).checked = true;
   document.getElementById('jspCadenaCertificado' + index).disabled = false;
   document.getElementById('anadir' + index).disabled = false;
   document.getElementById('modificar' + index).disabled = false;
   document.getElementById('eliminar' + index).disabled = false;
}


//OCAP-34
function deshabilitarFormulario() {

   formulario = document.forms[0];
   for (i=0; i < formulario.elements.length; i++) {
   
    if (formulario.elements[i].nodeName !='SELECT' && formulario.elements[i].nodeName !='FIELDSET')
      formulario.elements[i].disabled=true;
   }
}

function habilitarSelect(formulario){
 
   for (i=0; i<formulario.elements.length; i++) {
   
      if (formulario.elements[i].nodeName=='SELECT'){               
          formulario.elements[i].disabled=false;          
      }
      
   }
}

 

function inicializar(){
   for (var i = 0; i < document.forms[0].FAnnio.length; i++) {
      if (document.getElementById('CActividadId' + i).checked == true) {
         document.forms[0].FAnnio[i].disabled = false;
         if (acreditado)
            document.forms[0].DTitulo[i].disabled = false;
         else if(document.getElementById('CActividadId' + i).value == '<%=Constantes.ACT_TUTOR_ASOCIADO%>' || document.getElementById('CActividadId' + i).value == '<%=Constantes.ACT_COLABORADOR_DOCENTE%>'){
            document.forms[0].NMeses[i].disabled = false;
            document.forms[0].NDias[i].disabled = false;
         }
      }
   }
}    
</script>

<div class="ocultarCampo">

	<div class="contenido">
		<div class="contenidoAltaMC">
			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<h2 class="tituloContenido">NUEVO M&Eacute;RITO CURRICULAR</h2>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<h2 class="tituloContenido">DETALLE DE M&Eacute;RITO CURRICULAR</h2>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
				<h2 class="tituloContenido">MODIFICAR M&Eacute;RITO CURRICULAR</h2>
			</logic:equal>

			<h3 class="subTituloContenidoMC">
				<bean:write name="OCAPMeritosForm" property="DNombreMerito" />
			</h3>
			<div class="lineaBajaM"></div>
			<br />
			<br />
			<br />

			<html:form action="/OCAPMeritos.do?accion=controlAccionDocenciaPost">
				<html:hidden property="resumenCertificados" />
				<div class="mensajeErrorMC">
					<html:messages id="meritoRepetido" property="meritoRepetido"
						message="true">
						<bean:write name="meritoRepetido" />
						<br />
					</html:messages>
					<html:messages id="cActividadId" property="cActividadId"
						message="true">
						<bean:write name="cActividadId" />
						<br />
					</html:messages>
					<html:messages id="FAnnio" property="FAnnio" message="true">
						<bean:write name="FAnnio" />
						<br />
					</html:messages>
				</div>
				<fieldset>				
					<div class="cuadroFieldset">
						<logic:notEqual name="OCAPMeritosForm"
							property="DDescripcionMerito" value="">
							<label for="idVTitulo" class="textoJustificado"> <span
								class="textoCursivaMC"><bean:write name="OCAPMeritosForm"
										property="DDescripcionMerito" filter="false" /></span>
							</label>
							<br />
							<br />
							<br />
						</logic:notEqual>

						<bean:size id="tamanoListaActividades" name="OCAPMeritosForm"
							property="listaActividades" />
						<logic:notEqual name="tamanoListaActividades" value="0">

							<logic:iterate id="lActividades" name="OCAPMeritosForm"
								property="listaActividades" type="OCAPMeractividadOT"
								indexId="index">
								<div class="radioEnCuadro flotaIzq">
									<div class="flotaIzq">
										<logic:equal name="OCAPMeritosForm" property="CActividadId"
											value="<%=lActividades.getCActividadId().toString()%>">
											<input type="checkbox" id="CActividadId<%=index%>"
												name="CActividadId<%=index%>" checked="checked"
												value="<bean:write name="lActividades" property="CActividadId"/>" />
										</logic:equal>
										<logic:notEqual name="OCAPMeritosForm" property="CActividadId"
											value="<%=lActividades.getCActividadId().toString()%>">
											<input type="checkbox" id="CActividadId<%=index%>"
												name="CActividadId<%=index%>"
												value="<bean:write name="lActividades" property="CActividadId"/>"
												onclick="activarCamposFecha('<%=index%>')" />
										</logic:notEqual>
										<span> <bean:write name="lActividades"
												property="DNombre" /> <bean:write name="lActividades"
												property="DObservaciones" /></span>
									</div>
								</div>
								<logic:equal name="OCAPMeritosForm" property="DTipoMerito"
									value="<%=Constantes.MER_DOC_POS_ACRED%>">
									<script language="javascript">
                                    acreditado = true;
                                 </script>
									<br />
									<br />
									<label for="idVTitulo"> Unidad / Servicio / Centro: * <html:text
											property="DTitulo"
											styleClass="recuadroM colocaUnidadResponsableDoceMC"
											maxlength="150" disabled="true" />
									</label>
								</logic:equal>
								<br />
								<br />
								<div class="flotaIzq">
									<label for="idVNAnnios"> A&ntildeo de inicio: *</label>
									<html:text property="FAnnio"
										styleClass="recuadroM colocaFechaDocenciaPostMC" maxlength="4"
										disabled="true" />
								</div>
								<logic:notEqual name="OCAPMeritosForm" property="DTipoMerito"
									value="<%=Constantes.MER_DOC_POS_ACRED%>">
									<% if (lActividades.getCActividadId().intValue() == Constantes.ACT_TUTOR_ASOCIADO || lActividades.getCActividadId().intValue() == Constantes.ACT_COLABORADOR_DOCENTE) {%>
									<br />
									<br />
									<div class="flotaIzq">
										<label for="idVNAnnios"> Tiempo efectivo de
											tutor&iacute;a: * </label>
									</div>
									<br />
									<br />
									<div class="flotaIzq tabula">
										<label>Meses: </label>
										<html:text property="NMeses"
											styleClass="recuadroM colocaMesesDocenciaPostMC"
											maxlength="2" disabled="true" />
										<label>D&iacute;as: </label>
										<html:text property="NDias"
											styleClass="recuadroM colocaDiasDocenciaPostMC" maxlength="2"
											disabled="true" />
									</div>
									<% } else {%>
									<html:hidden property="NMeses" />
									<html:hidden property="NDias" />
									<% } %>
								</logic:notEqual>

								<div
									class="botonesDocencia botonesCertif2 botonesCertif2Docencia">

									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<input type="button" id="anadir<%=index%>" tabindex="63"
											value=" a&ntilde;adir" property="anadir"
											onclick="anhCertificado('<%=index%>','<%=lActividades.getCActividadId().intValue()%>');"
											disabled="true" />
										</span> <span class="derBoton"></span>
									</div>

									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<input type="button" id="modificar<%=index%>" tabindex="64"
											value="modificar" property="modificar"
											onclick="modCertificado('<%=index%>','<%=lActividades.getCActividadId().intValue()%>');"
											disabled="true" />
										</span> <span class="derBoton"></span>
									</div>

									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<input type="button" id="eliminar<%=index%>" tabindex="65"
											value="eliminar" property="eliminar"
											onclick="combos_borrar(document.getElementById('jspCadenaCertificado' + '<%=index%>'));"
											disabled="true" />
										</span> <span class="derBoton"></span>
									</div>

								</div>

								<!--tabla con los datos -->
								<table border="0" cellpadding="2" cellspacing="0" width="570"
									class="colocaTablaCertif">
									<tr>
										<logic:notEqual name="OCAPMeritosForm" property="DTipoMerito"
											value="<%=Constantes.MER_DOC_POS_ACRED%>">
											<td width="30%" class="tituloCertificados" NOWRAP>A&ntilde;o
												Docencia</td>
										</logic:notEqual>
										<logic:equal name="OCAPMeritosForm" property="DTipoMerito"
											value="<%=Constantes.MER_DOC_POS_ACRED%>">
											<td width="30%" class="tituloCertificados" NOWRAP>A&ntilde;o
												Inicio--Unidad / Servicio / Centro</td>
										</logic:equal>
										<td width="30%" class="tituloCertificados" NOWRAP></td>
									</tr>
									<tr>
										<td colspan="3" height="1" NOWRAP><html:img
												page="/imagenes/pix.gif" width="1" height="1" /></td>
									</tr>
									<tr>
										</br>
										<table border="0" cellpadding="2" cellspacing="0" width="300"
											class="colocaTablaCertif" margin-top="5px">
											<td class="recuadroM">											
											<select size="5"
												id="jspCadenaCertificado<%=index%>"
												name="jspCadenaCertificado<%=index%>"
												class="textoTituloGris" >
													<logic:equal name="tipoAccion"
														value="<%=Constantes.MODIFICAR%>">
														<%
                                           String valor = "";
                                           for (int g=0;g<resumenCertificados.size();g++){
                                              for (int gg=0;gg<((ArrayList) resumenCertificados.get(g)).size();gg++){
                                                 if(((OCAPExpedientemcOT)((ArrayList)resumenCertificados.get(g)).get(gg)).getCActividadId().intValue() == ((OCAPMeractividadOT)lActividades).getCActividadId().intValue()){                                                
                                                    if (request.getParameter("tipoMerito").equals(Constantes.MER_DOC_POS_ACRED)){
                                                       valor = ((OCAPExpedientemcOT)((ArrayList) resumenCertificados.get(g)).get(gg)).getFAnnio() + "$" +	
                                                                ((OCAPExpedientemcOT)((ArrayList) resumenCertificados.get(g)).get(gg)).getDTitulo();
                                                    }else{
                                                       if (((OCAPExpedientemcOT)((ArrayList)resumenCertificados.get(g)).get(gg)).getCActividadId().intValue() == Constantes.ACT_TUTOR_ASOCIADO  ||
                                                           ((OCAPExpedientemcOT)((ArrayList)resumenCertificados.get(g)).get(gg)).getCActividadId().intValue() == Constantes.ACT_COLABORADOR_DOCENTE){
                                                          valor = ((OCAPExpedientemcOT)((ArrayList) resumenCertificados.get(g)).get(gg)).getFAnnio() + "$" +	
                                                                   ((OCAPExpedientemcOT)((ArrayList) resumenCertificados.get(g)).get(gg)).getFAnnioFin() + "$" +
                                                                   ((OCAPExpedientemcOT)((ArrayList) resumenCertificados.get(g)).get(gg)).getNMeses() + "$" +
                                                                   ((OCAPExpedientemcOT)((ArrayList) resumenCertificados.get(g)).get(gg)).getNDias();                                                   
                                                       }else{
                                                          valor = ((OCAPExpedientemcOT)((ArrayList) resumenCertificados.get(g)).get(gg)).getFAnnio() + "$" +	
                                                                   ((OCAPExpedientemcOT)((ArrayList) resumenCertificados.get(g)).get(gg)).getFAnnioFin();
                                                       }
                                                                                   
                                                    }%>
														<logic:equal name="OCAPMeritosForm" property="DTipoMerito"
															value="<%=Constantes.MER_DOC_POS_ACRED%>">
															<option  value="<%=valor%>"><%=((OCAPExpedientemcOT)((ArrayList) resumenCertificados.get(g)).get(gg)).getFAnnio()%>---<%=((OCAPExpedientemcOT)((ArrayList) resumenCertificados.get(g)).get(gg)).getDTitulo()%>
															</option>
														</logic:equal>
														<logic:notEqual name="OCAPMeritosForm"	property="DTipoMerito"
															value="<%=Constantes.MER_DOC_POS_ACRED%>">
															<% if (lActividades.getCActividadId().intValue() == Constantes.ACT_TUTOR_ASOCIADO || lActividades.getCActividadId().intValue() == Constantes.ACT_COLABORADOR_DOCENTE) {%>
															<option value="<%=valor%>"><%=((OCAPExpedientemcOT)((ArrayList) resumenCertificados.get(g)).get(gg)).getFAnnio()%>---<%=((OCAPExpedientemcOT)((ArrayList) resumenCertificados.get(g)).get(gg)).getFAnnioFin()%>---<%=((OCAPExpedientemcOT)((ArrayList) resumenCertificados.get(g)).get(gg)).getNMeses()%>---<%=((OCAPExpedientemcOT)((ArrayList) resumenCertificados.get(g)).get(gg)).getNDias()%>
																<%} else {%>
																<option value="<%=valor%>"><%=((OCAPExpedientemcOT)((ArrayList) resumenCertificados.get(g)).get(gg)).getFAnnio()%>---<%=((OCAPExpedientemcOT)((ArrayList) resumenCertificados.get(g)).get(gg)).getFAnnioFin()%>
																	<%}%>
																</option>
														</logic:notEqual>
														<script>
                                                 activarMerito('<%=index%>');
                                              </script>
														<%} // fin if (g)
                                              } // fin for (gg)
                                           } // fin for (g)%>
													</logic:equal>
											</select></td>
											<td colspan="2"></td>
										</table>
									</tr>
									<div class="espaciador"></div>
								</table>
								<br />
								<br />
							</logic:iterate>
							<div class="limpiadora"></div>
							<div class="espaciador"></div>
						</logic:notEqual>

					</div>
				</fieldset>

				<p>Los campos se&ntilde;alados con * son obligatorios</p>

				<div class="espaciadorPeq"></div>

				<div class="botonesPagina">
					<logic:notPresent name="OCAPMeritosForm" property="BDetalle">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="OCAPUsuarios.do?accion=irInsertar&pestana=<bean:write name="OCAPMeritosForm" property="pestanaSeleccionada"/>#<bean:write name="OCAPMeritosForm" property="DTipoMerito"/>">
									<img src="imagenes/imagenes_ocap/aspa_roja.gif"
									alt="Cancelar M&eacute;rito" /> <span> Cancelar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <logic:equal
									name="tipoAccion" value="<%=Constantes.INSERTAR%>">
									<img src="imagenes/imagenes_ocap/icon_accept.gif"
										alt="Imagen Insertar Merito" />
									<input class="textoBotonInput" type="button" name="accionBoton"
										tabindex="17" value="<%=Constantes.INSERTAR%>"
										alt="Bot&oacute;n para guardar el m&eacute;rito"
										onclick="aceptarDocencia()" />
								</logic:equal> <logic:equal name="tipoAccion"
									value="<%=Constantes.VER_DETALLE%>">
									<img src="imagenes/imagenes_ocap/icon_accept.gif"
										alt="Imagen Ver Detalle Merito" />
									<input class="textoBotonInput" type="submit" name="accionBoton"
										tabindex="17" value="<%=Constantes.VER_DETALLE%>"
										alt="Bot&oacute;n para volver a la pantalla anterior"
										onclick="aceptarDocencia()" />
								</logic:equal> <logic:equal name="tipoAccion"
									value="<%=Constantes.MODIFICAR%>">
									<img src="imagenes/imagenes_ocap/icon_accept.gif"
										alt="Imagen Modificar Merito" />
									<input class="textoBotonInput" type="button" name="accionBoton"
										tabindex="17" value="<%=Constantes.MODIFICAR%>"
										alt="Bot&oacute;n para modificar el m&eacute;rito"
										onclick="aceptarDocencia()" />
								</logic:equal>
							</span> <span class="derBoton"></span>
						</div>
					</logic:notPresent>
					<logic:present name="OCAPMeritosForm" property="BDetalle">
						<logic:notEqual name="OCAPMeritosForm" property="BDetalle"
							value="<%=Constantes.SI%>">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="OCAPUsuarios.do?accion=irInsertar&pestana=<bean:write name="OCAPMeritosForm" property="pestanaSeleccionada"/>#<bean:write name="OCAPMeritosForm" property="DTipoMerito"/>">
										<img src="imagenes/imagenes_ocap/aspa_roja.gif"
										alt="Cancelar M&eacute;rito" /> <span> Cancelar </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <logic:equal
										name="tipoAccion" value="<%=Constantes.INSERTAR%>">
										<img src="imagenes/imagenes_ocap/icon_accept.gif"
											alt="Imagen Insertar Merito" />
										<input class="textoBotonInput" type="button"
											name="accionBoton" tabindex="17"
											value="<%=Constantes.INSERTAR%>"
											alt="Bot&oacute;n para guardar el m&eacute;rito"
											onclick="aceptarDocencia()" />
									</logic:equal> <logic:equal name="tipoAccion"
										value="<%=Constantes.VER_DETALLE%>">
										<img src="imagenes/imagenes_ocap/icon_accept.gif"
											alt="Imagen Ver Detalle Merito" />
										<input class="textoBotonInput" type="submit"
											name="accionBoton" tabindex="17"
											value="<%=Constantes.VER_DETALLE%>"
											alt="Bot&oacute;n para volver a la pantalla anterior"
											onclick="aceptarDocencia()" />
									</logic:equal> <logic:equal name="tipoAccion"
										value="<%=Constantes.MODIFICAR%>">
										<img src="imagenes/imagenes_ocap/icon_accept.gif"
											alt="Imagen Modificar Merito" />
										<input class="textoBotonInput" type="button"
											name="accionBoton" tabindex="17"
											value="<%=Constantes.MODIFICAR%>"
											alt="Bot&oacute;n para modificar el m&eacute;rito"
											onclick="aceptarDocencia()" />
									</logic:equal>
								</span> <span class="derBoton"></span>
							</div>
						</logic:notEqual>
						<logic:equal name="OCAPMeritosForm" property="BDetalle"
							value="<%=Constantes.SI%>">
							<jsp:include page="OCAPMeritosJS.jsp" /><!-- Boton pedir aclaraciones -->
							<script language="javascript">
                              deshabilitarFormulario();                              
                           </script>
						</logic:equal>
					</logic:present>
				</div>
				<html:hidden property="pestanaSeleccionada" />
				<html:hidden property="CExpmcId" />
				<html:hidden property="CExpId" />
				<html:hidden property="DTipoMerito" />
				<html:hidden property="CTipoMerito" />
				<html:hidden property="CEstatutId" />
				<html:hidden property="CDefMeritoId" />
				<html:hidden property="CTipoMerito" />
				<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
					<html:hidden property="accionBoton"
						value="<%=Constantes.INSERTAR%>" />
				</logic:equal>
				<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
					<html:hidden property="accionBoton"
						value="<%=Constantes.VER_DETALLE%>" />
				</logic:equal>
				<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
					<html:hidden property="accionBoton"
						value="<%=Constantes.MODIFICAR%>" />
				</logic:equal>
			</html:form>

		</div>
		<!-- /fin de ContenidoDCP1A -->
	</div>
	<!-- /Fin de Contenido -->

</div>
<!-- /Fin de ocultarCampo -->

<logic:notEqual name="OCAPMeritosForm" property="BDetalle"
	value="<%=Constantes.SI%>">
	<script>

inicializar();
</script>
</logic:notEqual>
