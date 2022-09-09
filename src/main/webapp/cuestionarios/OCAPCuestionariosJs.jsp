<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="JavaScript" type="text/javascript">
var listaPreguntasJS = new Array();
</script>

<% int numPreguntaArray = 0; %>
<logic:iterate id="listaPreguntas" name="OCAPCuestionariosForm"
	property="listadoPreguntas" type="OCAPCuestionariosOT">
	<script language="JavaScript" type="text/javascript">
      var tipo = '<bean:write name="listaPreguntas" property="CTipoPregunta"/>';
      var nElementos = eval('<bean:write name="listaPreguntas" property="NElementos"/>');
      var nSubElementos = eval('<bean:write name="listaPreguntas" property="NSubElementos"/>');
      <% int nElementos=  listaPreguntas.getNElementos(); %>
      <% int nSubElementos=  listaPreguntas.getNSubElementos(); %>
       if (tipo == '<%=Constantes.RADIO%>') {
          var aux = new Array(nElementos);
          <% for (int n = 0; n < nElementos; n++) { %>
            aux[<%=n%>] = new Array(1);
            <% if (request.getAttribute("pregunta"+numPreguntaArray+"_"+n+"_0") != null) {%>
               aux[<%=n%>][0] = '<%= request.getAttribute("pregunta"+numPreguntaArray+"_"+n+"_0").toString().replaceAll("\n",Constantes.SALTO_LINEA).replaceAll("\r",Constantes.RETORNO_CARRO)%>'; 
            <% } %>
          <% } %> 
       }
       if (tipo == '<%=Constantes.RADIO_TEXT%>') {
          var aux = new Array(nElementos);
          <% for (int n = 0; n < nElementos; n++) { %>
            aux[<%=n%>] = new Array(2);
            <% if (request.getAttribute("pregunta"+numPreguntaArray+"_"+n+"_0") != null) {%>
               aux[<%=n%>][0] = '<%= request.getAttribute("pregunta"+numPreguntaArray+"_"+n+"_0").toString().replaceAll("\n",Constantes.SALTO_LINEA).replaceAll("\r",Constantes.RETORNO_CARRO)%>'; 
            <% } %>
            <% if (request.getAttribute("pregunta"+numPreguntaArray+"_"+n+"_1") != null) {%>
               aux[<%=n%>][1] = '<%= request.getAttribute("pregunta"+numPreguntaArray+"_"+n+"_1").toString().replaceAll("\n",Constantes.SALTO_LINEA).replaceAll("\r",Constantes.RETORNO_CARRO)%>'; 
            <% } %>
          <% } %>
       }
       if (tipo=='<%=Constantes.TEXT%>' || tipo=='<%=Constantes.TEXTAREA%>' || tipo=='<%=Constantes.FECHA%>' || tipo=='<%=Constantes.TEXT_NUMERO%>' ||
            tipo=='<%=Constantes.NUMERO%>' || tipo=='<%=Constantes.NUMERO_DECIMAL%>' || tipo=='<%=Constantes.CREDITOS%>') {
          var aux = new Array(nElementos);
          <% for (int n = 0; n < nElementos; n++) { %>
            aux[<%=n%>] = new Array(nSubElementos);
            <% for (int m = 0; m < nSubElementos; m++) { %>
               <% if (request.getAttribute("pregunta"+numPreguntaArray+"_"+n+"_"+m) != null) {%>
                  aux[<%=n%>][<%=m%>] = '<%= request.getAttribute("pregunta"+numPreguntaArray+"_"+n+"_"+m).toString().replaceAll("\n",Constantes.SALTO_LINEA).replaceAll("\r",Constantes.RETORNO_CARRO)%>'; 
               <% } %>
            <% } %>
          <% } %>
       }
       if (tipo == '<%=Constantes.CHECK%>' || tipo == '<%=Constantes.CHECKLINEA%>') { //checkbox
         var aux = new Array(nElementos);
         <% for (int n = 0; n < nElementos; n++) { %>
            aux[<%=n%>] = new Array(nSubElementos);
            <% for (int m = 0; m < nSubElementos; m++) { %>
               aux[<%=n%>][<%=m%>]='N';
               <% if (request.getAttribute("pregunta"+numPreguntaArray+"_"+n+"_"+m) != null) {%>
                  aux[<%=n%>][<%=m%>] = '<%= request.getAttribute("pregunta"+numPreguntaArray+"_"+n+"_"+m).toString().replaceAll("\n",Constantes.SALTO_LINEA).replaceAll("\r",Constantes.RETORNO_CARRO)%>'; 
               <% } %>
             <% } %>
         <% } %>
       }
       if (tipo != '<%=Constantes.VACIO%>' && tipo != '<%=Constantes.COMENTARIO%>') {
         listaPreguntasJS[listaPreguntasJS.length] = aux;
      }
      <% if (!listaPreguntas.getCTipoPregunta().equals(Constantes.VACIO) && !listaPreguntas.getCTipoPregunta().equals(Constantes.COMENTARIO)) {
         numPreguntaArray++;
      }
      %>
   </script>
</logic:iterate>

<script language="JavaScript" type="text/javascript">

function guardarRespuesta(nPregunta, tipo, nRespuesta, nSubRespuesta, valor){
   var longListaPreguntas = listaPreguntasJS.length;
   if (tipo=='<%=Constantes.RADIO%>') {
      /*
      var numRespuestas=(document.getElementsByName('pregunta'+nPregunta+'_'+nRespuesta+'_'+nSubRespuesta)).length;
      for (i=0; i<numRespuestas; i++) {
         if (document.getElementsByName('pregunta'+nPregunta+'_'+nRespuesta+'_'+nSubRespuesta)[i].checked) {
            listaPreguntasJS[nPregunta][nRespuesta][nSubRespuesta]=document.getElementsByName('pregunta'+nPregunta+'_'+nRespuesta+'_'+nSubRespuesta)[i].value;
         }
      }
      */
      listaPreguntasJS[nPregunta][nRespuesta][nSubRespuesta]= valor;
   } else if (tipo=='<%=Constantes.RADIO_TEXT%>') {
      if (nSubRespuesta=='0') {
         /*
         var numRespuestas=(document.getElementsByName('pregunta'+nPregunta+'_'+nRespuesta+'_'+nSubRespuesta)).length;
         for (i=0; i<numRespuestas; i++) {
            if (document.getElementsByName('pregunta'+nPregunta+'_'+nRespuesta+'_'+nSubRespuesta)[i].checked) {
               listaPreguntasJS[nPregunta][nRespuesta][nSubRespuesta]=document.getElementsByName('pregunta'+nPregunta+'_'+nRespuesta+'_'+nSubRespuesta)[i].value;
            }
         }
         */
         listaPreguntasJS[nPregunta][nRespuesta][nSubRespuesta]= valor;
         if (listaPreguntasJS[nPregunta][nRespuesta][nSubRespuesta]=='<%=Constantes.OTRA%>') {
            document.getElementById('pregunta'+nPregunta+'_'+nRespuesta+'_1').disabled=false;
            document.getElementById('pregunta'+nPregunta+'_'+nRespuesta+'_1').readOnly=false;
         } else {
            document.getElementById('pregunta'+nPregunta+'_'+nRespuesta+'_1').disabled=true;
            document.getElementById('pregunta'+nPregunta+'_'+nRespuesta+'_1').readOnly=true;
            document.getElementById('pregunta'+nPregunta+'_'+nRespuesta+'_1').value='';
            listaPreguntasJS[nPregunta][nRespuesta][1]='';
         }
      } else {
         listaPreguntasJS[nPregunta][nRespuesta][nSubRespuesta]=formatearRespuesta(document.getElementById('pregunta'+nPregunta+'_'+nRespuesta+'_'+nSubRespuesta).value);
      }
   } else if (tipo=='<%=Constantes.CHECK%>' || tipo=='<%=Constantes.CHECKLINEA%>') {
      if ((document.getElementById('pregunta'+nPregunta+'_'+nRespuesta+'_'+nSubRespuesta)).checked) {
         listaPreguntasJS[nPregunta][nRespuesta][nSubRespuesta]='S';
      } else listaPreguntasJS[nPregunta][nRespuesta][nSubRespuesta]='N';
   } else if (tipo!= '<%=Constantes.VACIO%>' && tipo!= '<%=Constantes.COMENTARIO%>'){
      listaPreguntasJS[nPregunta][nRespuesta][nSubRespuesta]=formatearRespuesta(document.getElementById('pregunta'+nPregunta+'_'+nRespuesta+'_'+nSubRespuesta).value);
   }
   
   //alert(listaPreguntasJS[nPregunta][nRespuesta][nSubRespuesta]);
}

function formatearRespuesta(parametro){
   var cadena = parametro.replace(new RegExp('\'','g'),'\"');
   cadena = cadena.replace(new RegExp('\´','g'),'\"');
   cadena = cadena.replace(new RegExp('\`','g'),'\"');
   cadena = cadena.replace(new RegExp('\&','g'),'\y');
   return cadena;
}

function enviarFormulario(obligatorio, fin) {
   if (obligatorio == '<%=Constantes.NO%>' || (obligatorio == '<%=Constantes.SI%>' && comprobarFormularioRelleno()) ) {
      var cadenaParam='';
      //cadenaParam='&numPreguntas='+listaPreguntasJS.length;
      for (i=0; i < listaPreguntasJS.length; i++) {
         var aux = listaPreguntasJS[i];
         for (j=0; j < aux.length; j++) {
            var aux2= aux[j];
            for(k=0; k < aux2.length; k++) {
               //Reemplazar saltos de linea para pasarlos por request
               if (aux2[k] == null) {
                  aux2[k]='';
               } else {
                  if (aux2[k].indexOf('\n') != -1 || aux2[k].indexOf('\r') != -1) {
                     aux2[k] = aux2[k].replace(new RegExp('\\n','g'),'<%=Constantes.SALTO_LINEA%>').replace(new RegExp('\\r','g'),'<%=Constantes.RETORNO_CARRO%>');
                  }
                  if (aux2[k] != null && aux2[k].indexOf('+') != -1) {
                     aux2[k] = aux2[k].replace(new RegExp('\\+','g'),'<%=Constantes.MAS%>');
                  }
                  if (aux2[k] != null && aux2[k].indexOf('=') != -1) {
                     aux2[k] = aux2[k].replace(new RegExp('\=','g'),'<%=Constantes.IGUAL%>');
                  }
               }
               cadenaParam = cadenaParam +'&'+ 'pregunta'+i+'_'+j+'_'+k+'='+aux2[k];
            }//for
         }//for
      }//for

      var cadenaCuestionariosIntermedios = '';
      <% int cont = 1; 
         while (request.getAttribute("idCuestionarioDespuesIntermedio"+cont)!=null) { %>
            cadenaCuestionariosIntermedios += '&idCuestionarioDespuesIntermedio<%=cont%>=<%=request.getAttribute("idCuestionarioDespuesIntermedio"+cont)%>';
            <% cont++; %>
      <% } %>
      document.forms[0].cadenaRespuestas.value=cadenaParam;
      enviar('OCAPCuestionarios.do?accion=insertar&numPreguntas='+listaPreguntasJS.length+'&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioId" />&idRepeticion=<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />&nEvidencia=<bean:write name="OCAPCuestionariosForm" property="NEvidencia" />&idPadreEvidencia=<bean:write name="OCAPCuestionariosForm" property="CPadreEvidenciaId" />'+cadenaCuestionariosIntermedios+'&finalizar='+fin);
   }
}

function volverListado(continuar) {
var cadenaParam='';
      cadenaParam='&numPreguntas='+listaPreguntasJS.length;
      for (i=0; i < listaPreguntasJS.length; i++) {
         var aux = listaPreguntasJS[i];
         for (j=0; j < aux.length; j++) {
            var aux2= aux[j];
            for(k=0; k < aux2.length; k++) {
               //Reemplazar saltos de linea para pasarlos por request
               if (aux2[k] == null) {
                  aux2[k]='';
               } else {
                  if (aux2[k].indexOf('\n') != -1 || aux2[k].indexOf('\r') != -1) {
                     aux2[k] = aux2[k].replace(new RegExp('\\n','g'),'<%=Constantes.SALTO_LINEA%>').replace(new RegExp('\\r','g'),'<%=Constantes.RETORNO_CARRO%>');
                  }
                  if (aux2[k]!= null && aux2[k].indexOf('+') != -1) {
                     aux2[k] = aux2[k].replace(new RegExp('\\+','g'),'<%=Constantes.MAS%>');
                  }
               }
               cadenaParam = cadenaParam +'&'+ 'pregunta'+i+'_'+j+'_'+k+'='+aux2[k];
            }
         }
      }
   var cadenaCuestionariosIntermedios = '';
   <% int contador = 1; 
      while (request.getAttribute("idCuestionarioDespuesIntermedio"+contador)!=null) { %>
         <% if (contador == 1) {%>
            cadenaCuestionariosIntermedios += '&idCuestionario=<%=request.getAttribute("idCuestionarioDespuesIntermedio1")%>';
         <% } else { %>
            cadenaCuestionariosIntermedios += '&idCuestionarioDespuesIntermedio<%=contador%>=<%=request.getAttribute("idCuestionarioDespuesIntermedio"+contador)%>';
         <% } %>
         <% contador++; %>
   <% } %>
   if ((continuar =='<%=Constantes.SI%>') || (('<%=Constantes.VER_DETALLE%>' == '<%=request.getAttribute("accion")%>') && (continuar !='<%=Constantes.NO%>'))) {
   <% if (contador > 1) { %>
      enviar('OCAPCuestionarios.do?accion=verCuestionario'+cadenaCuestionariosIntermedios+'&saltarIntermedio<%=contador-1%>=<%=Constantes.SI%>');
   <% } else if (request.getAttribute("verPadre")!=null && !"".equals(request.getAttribute("verPadre"))){ %>
      enviar('OCAPCuestionarios.do?accion=verCuestionario&idCuestionario=<%=request.getAttribute("verPadre")%>&saltarIntermedio1=<%=Constantes.SI%>');
   <% } else { %>
      <% if (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())) { %>
         <% if (request.getParameter("idPadreEvidencia")!=null && !"0".equals(request.getParameter("idPadreEvidencia")) && !"".equals(request.getParameter("idPadreEvidencia"))){%>
            enviar('OCAPCuestionarios.do?accion=verCuestionario&idCuestionario=<%=request.getParameter("idPadreEvidencia")%>&saltarIntermedio1=<%=Constantes.SI%>');
         <% } else { %>
            enviar('OCAPCuestionarios.do?accion=irListarPruebas');
         <% } %>
      <%} else { %>
         enviar('OCAPCuestionarios.do?accion=irListar');
      <% } %>
   <% } %>
   } else {
      <% if (request.getAttribute("verPadre")!=null && !"".equals(request.getAttribute("verPadre"))){ %>
         enviar('OCAPCuestionarios.do?accion=verCuestionario&idCuestionario=<%=request.getAttribute("verPadre")%>&saltarIntermedio1=<%=Constantes.SI%>');
      <% } else { %>
         <% if (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())) { %>
            <% if (request.getParameter("idPadreEvidencia")!=null && !"0".equals(request.getParameter("idPadreEvidencia")) && !"".equals(request.getParameter("idPadreEvidencia"))){%>
               enviar('OCAPCuestionarios.do?accion=verCuestionario&idCuestionario=<%=request.getParameter("idPadreEvidencia")%>&saltarIntermedio1=<%=Constantes.SI%>');
            <% } else { %>
               enviar('OCAPCuestionarios.do?accion=irListarPruebas');
            <% } %>
         <%} else { %>
            enviar('OCAPCuestionarios.do?accion=irListar');
         <% } %>
      <% } %>   
   }
}

function verHijoSubdivision(idCuestionarioHijo){
   enviar('OCAPCuestionarios.do?accion=verCuestionario&idCuestionario='+idCuestionarioHijo+'&idRepeticion=0');
}

function comprobarFormularioRelleno(){
   var relleno=true;
   for (i=0; i < listaPreguntasJS.length && relleno; i++) {
         var aux = listaPreguntasJS[i];
         for (j=0; j < aux.length; j++) {
            var aux2= aux[j];
            for(k=0; k < aux2.length; k++)
               if (aux2[k] == undefined) {
                  relleno = false;
               }
         }
      }
   if (!relleno)
      alert('Debe contestar todas las preguntas del formulario');
   return relleno;
}

function validarFecha(campo) {
   if (campo.value.length > 0) {
      ValidarFechaConAlerta(campo,'');
   }
}

function soloNumeros(evt) {
   //se admiten: numeros . ,
   var nav4 = window.Event ? true : false;
   var key = nav4 ? evt.which : evt.keyCode;
   if (key == null)
      key = nav4 ? evt.keyCode : evt.which;
   return (key <= 13 || (key >= 48 && key <= 57));
}

function soloNumerosDecimales(evt) {
   //se admiten: numeros y "." 
   var nav4 = window.Event ? true : false;
   var key = nav4 ? evt.which : evt.keyCode; 
   if (key == null)
      key = nav4 ? evt.keyCode : evt.which;
   return (key <= 13 || (key >= 48 && key <= 57) || key==46);
}

function esNumeroDecimal(campo){
   var aux=campo.value;
   var contador = 0;
   var aux2='';
   while (aux.indexOf('.')!=-1) {
      if (contador<2) {
         aux2= aux2+''+aux.substring(0,aux.indexOf('.')+1);
      }
      aux=aux.substring(aux.indexOf('.')+1);
      contador++;
   }
   if (contador > 1) {
      alert('El n\u00famero decimal s\u00f3lo puede contener un \'.\'');
      campo.value=aux2.substring(0,aux2.length-1);
      return false;
   } else return true;
}

function pedirConfirmacion(obligatorio) {
   if (obligatorio == '<%=Constantes.NO%>' || (obligatorio == '<%=Constantes.SI%>' && comprobarFormularioRelleno()) ) {
      deshabilitarFormulario();
      document.getElementById('botonSiguiente').style.display='none';
      document.getElementById('botonSiguiente').style.visibility='hidden';
      document.getElementById('botonAtras').style.display='';
      document.getElementById('botonAtras').style.visibility='visible';
      document.getElementById('botonGuardar').style.display='';
      document.getElementById('botonGuardar').style.visibility='visible';
      document.getElementById('mensajeAvisoConfirmacion').style.display='';
      document.getElementById('mensajeAvisoConfirmacion').style.visibility='visible';
      /*
      var e=document.getElementsByTagName("span");
      for(var i=0;i<e.length;i++){
         if (e[i].name == 'stComentario' || e[i].className == 'stComentario'){
            e[i].style.display='none';
            e[i].style.visibility='hidden';
         }
      }
      */
      location.href='#verificar';
   }
}

function volverEditarFormulario() {
   habilitarFormulario();
   document.getElementById('botonSiguiente').style.display='';
   document.getElementById('botonSiguiente').style.visibility='visible';
   document.getElementById('botonAtras').style.display='none';
   document.getElementById('botonAtras').style.visibility='hidden';
   document.getElementById('botonGuardar').style.display='none';
   document.getElementById('botonGuardar').style.visibility='hidden';
   document.getElementById('mensajeAvisoConfirmacion').style.display='none';
   document.getElementById('mensajeAvisoConfirmacion').style.visibility='hidden';
/*
   var e=document.getElementsByTagName("span");
   for(var i=0;i<e.length;i++){
      if (e[i].name == 'stComentario' || e[i].className == 'stComentario'){
         e[i].style.display='';
         e[i].style.visibility='visible';
      }
   }
*/
   location.href='#verificar';
}

function deshabilitarFormulario() {
   var elementos=document.getElementsByTagName('input');
   for (i=0; i < elementos.length; i++) {
      if (elementos[i].type!='hidden')
         elementos[i].disabled=true;
   }
   var elementos=document.getElementsByTagName('textarea');
   for (i=0; i < elementos.length; i++) {
         elementos[i].disabled=true;
   }
   var elementos=document.getElementsByTagName('span');
   var RegExPattern = /<%=Constantes.FECHA%>\d/;
   for (i=0; i < elementos.length; i++) {
      if (elementos[i].id.match(RegExPattern)) {
         elementos[i].style.display='none';
         elementos[i].style.visibility='hidden';
      }
   }
}

function habilitarFormulario() {
   for (i=0; i < document.forms[0].elements.length; i++) {
      document.forms[0].elements[i].disabled=false;
   }
   var elementos=document.getElementsByTagName('span');
   var RegExPattern = /<%=Constantes.FECHA%>\d/;
   for (i=0; i < elementos.length; i++) {
      if (elementos[i].id.match(RegExPattern)) {
         elementos[i].style.display='';
         elementos[i].style.visibility='visible';
      }
   }
}

function limpiarFormulario() {
   for (i=0; i < document.forms[0].elements.length; i++) {
      document.forms[0].elements[i].value='';
      if (document.forms[0].elements[i].checked==true)
         document.forms[0].elements[i].checked=false;
   }
}

function isArray(obj) {
   if (obj.constructor.toString().indexOf("Array") == -1)
      return false;
   else
      return true;
}

/*
var puntuacion = 0;
function sumaPuntos() {
   puntuacion = 0;
   var e=document.getElementsByTagName("input");
   for(var i=0;i<e.length;i++){
      if ((e[i].type == "radio" || e[i].type == "checkbox") && e[i].checked == true){
         puntuacion = puntuacion + eval(e[i].className);
      }
      if (e[i].type == "text" && e[i].value != '' && e[i].title != ''){
         puntuacion = puntuacion + eval(e[i].title);
      }
   }
   var e2 = document.getElementsByTagName("textarea");
   for(var i=0;i<e2.length;i++){
      if (e2[i].value != '' && e2[i].title != ''){
         puntuacion = puntuacion + eval(e2[i].title);
      }
   }
   document.forms[0].puntos.value = puntuacion;
}
*/

function irPonderacion(){
   enviar('OCAPCuestionarios.do?accion=verPonderacion&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioId" />&idRepeticion=<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />&idPadreEvidencia=<bean:write name="OCAPCuestionariosForm" property="CPadreEvidenciaId" />');
}

var LONG_MAX_PREGUNTAS= <%=Constantes.LONG_PREGUNTAS%>;
function longMaxTextarea(campo) {
   if(campo.value.length <= LONG_MAX_PREGUNTAS) {
      return true;
   } else {
      campo.value = campo.value.substring(0,LONG_MAX_PREGUNTAS);
      return false;
   }
}

function verEvidencia(idEvidencia, nEvidencia, idPadreEvidencia, idRepeticion){
   enviar('OCAPCuestionarios.do?accion=verCuestionario&idCuestionario='+idEvidencia+'&nEvidencia='+nEvidencia+'&idPadreEvidencia='+idPadreEvidencia+'&idRepeticion='+idRepeticion);
}

function volverAdtvo(){
   enviar('OCAPNuevaSolicitud.do?accion=irDetalleFqs&CExp_id=<bean:write name="OCAPCuestionariosForm" property="CExpId" />&fase=<%=Constantes.FASE_CA_ESCANEADA%>');
}

function volverEval(){ 
   <% if (request.getAttribute("verPadre")!=null && !"".equals(request.getAttribute("verPadre"))){ %>
      enviar('OCAPCuestionarios.do?accion=verCuestionario&idCuestionario=<%=request.getAttribute("verPadre")%>&saltarIntermedio1=<%=Constantes.SI%>');
   <% } else { %>
      enviar('OCAPCuestionarios.do?accion=irListar&expId=<bean:write name="OCAPCuestionariosForm" property="CExpId" />');
   <% } %>
}

function simular(){
   enviar('OCAPCuestionarios.do?accion=verCuestionario&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioId" />&idRepeticion=<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />&simular=<%=Constantes.SI%>&verPreguntas=<%=Constantes.SI%>');
}

</script>
