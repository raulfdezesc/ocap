<%@ page contentType="text/html;charset=ISO-8859-1" pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.util.DateUtil"%>
<%@ page import="es.jcyl.fqs.ocap.ot.creditosCeis.OCAPCreditosCeisOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.otrosCentros.OCAPOtrosCentrosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/fechas.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"
	charset="windows-1252"></script>

<script language="JavaScript" type="text/javascript">

function volverAtras() {
   //history.back();
   <% if (request.getParameter("opcion") == null || "".equals(request.getParameter("opcion"))) { %>
      <% if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE) ) { %>
         limpiarCampos();
         enviar("OCAPSolicitudes.do?accion=irInsertar");
      <% } else { %>
         enviar("PaginaInicio.do");
      <% } %>
   <% } else if (request.getParameter("opcion").equals(Constantes.GRS_MERIT) || request.getParameter("opcion").equals(Constantes.GRS_PROCC)) { %>
         enviar("OCAPSolicitudes.do?accion=buscarGrs&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
   <% } else if (request.getParameter("opcion").equals(Constantes.OCAP_DIRECCION)) { %>
         enviar("OCAPEvaluadores.do?accion=listarEvaluados&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
   <% } else if (request.getParameter("opcion").equals(Constantes.OCAP_CC)) { %>
         enviar("OCAPEvaluadores.do?accion=listarEvaluados&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
   <% }else{ %>
         enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");      
   <% } %>
}

function reabrirMC(){
   if (confirm('Va a reactivar la fase de auto-evaluaci\u00F3n de M\u00E9ritos Curriculares de este usuario. \u00BFDesea continuar?'))
      enviar("OCAPSolicitudes.do?accion=reabrirMC&opcion=<%=request.getParameter("opcion")%>");
}

function limpiarCampos() {
   document.forms[0].DApellido1.value ='';
   document.forms[0].DNombre.value = '';
   document.forms[0].CDniReal.value = '';
   document.forms[0].CDni.value = '';
   document.forms[0].BSexo.value = '';
   document.forms[0].NTelefono1.value = '';
   document.forms[0].DCorreoelectronico.value='';
   document.forms[0].CJuridico.value = '';
   document.forms[0].FRegistro_oficial.value='';
}

function aceptar(){
//   debugger;
   if (validarFaseCC()){
    
   //Fase de iniciacion
      <%if(request.getAttribute("fase") != null && request.getAttribute("fase").equals(Constantes.FASE_INICIACION)){%>
         if (document.OCAPSolicitudesForm.RContinuidad_proceso[0].checked == true ){
            <%if(request.getAttribute("inconformidadFueraPlazo").equals(Constantes.SI)){%>
               document.OCAPSolicitudesForm.RContinuidad_proceso[1].checked = true;
               alert('Las alegaciones se registraron fuera de plazo. La solicitud no puede aceptarse.');
            <% } else { %>
               // Aceptada Fase Iniciacion
               if(confirm("Se va a ACEPTAR la solicitud de acceso a Carrera Profesional. \u00BFDesea continuar?")){
                  enviar('OCAPSolicitudes.do?accion=insertarAceptacionIniciacion&opcion=<%=request.getParameter("opcion")==null?"":request.getParameter("opcion")%>');
               }
            <% } %>
         } else if (document.OCAPSolicitudesForm.RContinuidad_proceso[1].checked == true && radiosIniciacionRellenos()) {
            // Solicitud No aceptada
            <%if(request.getAttribute("denegacion") != null && 
               request.getAttribute("denegacion").equals(Constantes.REDENEGADO)){%>
               alert('Ya ha excluido definitivamente esta solicitud. La solicitud permanecer\u00e1 excluida sin registrarse ning\u00fan cambio.');
        
               enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
            <%} else if (request.getAttribute("denegacion") != null && 
               request.getAttribute("denegacion").equals(Constantes.DENEGADO_SIN_INCONF)) { %>
                  //si se pueden registrar inconformidades
                  <% if (request.getAttribute("inconformidad").equals(Constantes.SI)) { %>
                     alert('No se volver\u00e1 a desestimar la solicitud hasta que no se registren alegaciones.\n La solicitud permanecer\u00e1 excluida sin registrarse ning\u00fan cambio.');
                     <% if (request.getParameter("opcion") == null || "".equals(request.getParameter("opcion"))) { %>
                        limpiarCampos();
                        enviar("OCAPSolicitudes.do?accion=irInsertar");
                     <% } else { %>
                        enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
                     <% } %>
                  //si no se pueden registrar inconformidades, guardamos los cambios y no hacemos nada mas
                  <% } else { %>
                     enviar('OCAPSolicitudes.do?accion=insertarDenegacion&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesDt&jspDenegacion=detalle&fase=<%=request.getAttribute("fase")%>&denegacion=<%=request.getAttribute("denegacion")%>&subsanacion=<%=Constantes.SI%>');
                  <% } %>
            <%} else if (request.getAttribute("denegacion") != null && 
               request.getAttribute("denegacion").equals(Constantes.DENEGADO_CON_INCONF)) { %>
               if(confirm("Se va a EXCLUIR DEFINITIVAMENTE la solicitud de acceso a Carrera Profesional. \u00BFDesea continuar?")){
                  enviar('OCAPSolicitudes.do?accion=insertarDenegacion&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesDt&jspDenegacion=detalle&fase=<%=request.getAttribute("fase")%>&denegacion=<%=request.getAttribute("denegacion")%>');
               }
            <%} else { %>
                      enviar('OCAPSolicitudes.do?accion=insertarDenegacion&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesDt&jspDenegacion=detalle&fase=<%=request.getAttribute("fase")%>&denegacion=<%=request.getAttribute("denegacion")%>');
                    <%}%>
         } else {
            //Si no esta ni aceptada, ni denegada, sigue pendiente. No hacemos nada
            alert('Deber\u00e1 seleccionar SI o NO en las opciones de: \n - Datos personales y profesionales correctos \n - Constancia de la antigüedad requerida \n - Constancia de plaza en propiedad \n - Constancia de servicios prestados en otros centros \n - Solicitud registrada en plazo');           
         }
      <%}%>
      //Fase de autoevaluacion MC, el PGP no puede hacer nada
      <%if(request.getAttribute("fase") != null && request.getAttribute("fase").equals(Constantes.FASE_MC)){%>
      alert('El usuario se encuentra auto-evaluando sus M\u00e9ritos Curriculares. No puede modificar su expediente.');
         <% if (request.getParameter("opcion") == null || "".equals(request.getParameter("opcion"))) { %>
            limpiarCampos();
            enviar("OCAPSolicitudes.do?accion=irInsertar");
         <% } else if (request.getParameter("opcion").equals(Constantes.GRS_MERIT) || request.getParameter("opcion").equals(Constantes.GRS_PROCC)){ %>    
               enviar("OCAPSolicitudes.do?accion=buscarGrs&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
         <% }else{ %>     
               enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
         <% } %>
      <%}%>
   /*
      //Fase de tramitacion
      <%if(request.getAttribute("fase") != null && request.getAttribute("fase").equals(Constantes.FASE_TRAMITACION)){%>
   //ToDo: validar fecha registro oficial MC
         if (validarFechaRegOficialMc()){
            if (document.OCAPSolicitudesForm.RTramitacion_correcta[0].checked == true ){
               <%if(request.getAttribute("inconformidadFueraPlazo").equals(Constantes.SI)){%>
                  document.OCAPSolicitudesForm.RTramitacion_correcta[1].checked = true;
                  alert('Las alegaciones se registraron fuera de plazo. La Fase de Recepci\u00f3n de M\u00e9ritos Curriculares no puede aceptarse.');
               <% } else { %>
                  // Aceptada Fase Tramitacion
                  if(confirm("Se va a ACEPTAR la Recepci\u00f3n de M\u00e9ritos Curriculares. \u00BFDesea continuar?")){
                     enviar('OCAPSolicitudes.do?accion=insertarAceptacionTramitacion&opcion=<%=request.getParameter("opcion")%>');
                  }
               <% } %>
            } else if (document.OCAPSolicitudesForm.RTramitacion_correcta[1].checked == true ){
               // Tramitacion No acpetada
               <%if(request.getAttribute("denegacion") != null && 
                  request.getAttribute("denegacion").equals(Constantes.REDENEGADO)){%>
                  alert('Ya ha desestimado la Recepci\u00f3n de esta solicitud 2 veces. La solicitud permanecer\u00e1 denegada sin registrarse ning\u00fan cambio.');
                  //history.back();
                  enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
               <%} else if (request.getAttribute("denegacion") != null && 
                  request.getAttribute("denegacion").equals(Constantes.DENEGADO_SIN_INCONF)) { %>
                  alert('No se volver\u00e1 a desestimar la Recepci\u00f3n hasta que no se registren alegaciones.\n La solicitud permanecer\u00e1 desestimada sin registrarse ning\u00fan cambio.');
                  //history.back();
                  enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
               <%} else if (request.getAttribute("denegacion") != null && 
                  request.getAttribute("denegacion").equals(Constantes.DENEGADO_CON_INCONF)) { %>
                  if(confirm("Se va a DESESTIMAR DEFINITIVAMENTE la Recepci\u00f3n de M\u00e9ritos Curriculares, \u00BFDesea continuar?")){
                     enviar('OCAPSolicitudes.do?accion=insertarDenegacion&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesDt&jspDenegacion=detalle&fase=<%=request.getAttribute("fase")%>&denegacion=<%=request.getAttribute("denegacion")%>');
                  }
               <%} else { %>
                  if(confirm("Se va a DESESTIMAR la Recepci\u00f3n de M\u00e9ritos Curriculares, \u00BFDesea continuar?")){
                     enviar('OCAPSolicitudes.do?accion=insertarDenegacion&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesDt&jspDenegacion=detalle&fase=<%=request.getAttribute("fase")%>&denegacion=<%=request.getAttribute("denegacion")%>');
                  }
               <%}%>
            } else {
            //Si tramitacion (si/no) esta en blanco, no hacemos nada
               alert('Debe seleccionar S\u00ed o No en la Recepci\u00f3n correcta de M\u00e9ritos Curriculares. Si no desea hacer ning\u00fan cambio, pulse en Cancelar.');
               //enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
            }
         }
      <%}%>
   */
      //Fase de validacion
      <%if(request.getAttribute("fase") != null && request.getAttribute("fase").equals(Constantes.FASE_VALIDACION)){%>
         <% if (jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR)) ) { %>
            alert('Los M\u00e9ritos Curriculares del usuario est\u00E1n siendo evaluados por otras comisiones. No puede modificar su expediente.');
            <% if (request.getParameter("opcion") == null || "".equals(request.getParameter("opcion"))) { %>
               limpiarCampos();
               enviar("OCAPSolicitudes.do?accion=irInsertar");
            <% } else { %>
               enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
            <% } %>
         <% } else if (jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_GRS)) ) { %>
            var cadenaParam='';
            var longListaCreditos = listaCreditosJS.length;
            cadenaParam='&numCreditos='+longListaCreditos;
            for (var i=0; i < longListaCreditos; i++) {
               var aux = document.getElementById('creditoValidado'+i).value;
               if (aux =='') aux='0';
               var aux= aux.replace(/,/g,'.');
               cadenaParam = cadenaParam +'&'+ 'creditoValidado'+i+'='+aux;
            }
            
            if (document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[0].checked == true && !aclaracionesPendientes){
               <%if(request.getAttribute("inconformidadFueraPlazo").equals(Constantes.SI)){%>
                  document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[1].checked = true;
                  alert('Las alegaciones se registraron fuera de plazo. La Fase de Evaluaci\u00f3n de M\u00e9ritos Curriculares no puede aceptarse.');
                  window.location.reload();
               <% } else { %>
                  //Si es CEIS solo puede aceptar mientras no tenga alegaciones o no este excluido definitivamente
                  <% if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS) &&
                  request.getAttribute("denegacion") != null && 
                  (request.getAttribute("denegacion").equals(Constantes.REDENEGADO) || request.getAttribute("denegacion").equals(Constantes.DENEGADO_CON_INCONF)) ) { %>
                     alert('Los M\u00e9ritos Curriculares del usuario est\u00E1n siendo evaluados por la Comisi\u00F3n Central. No puede modificar su expediente.');
                     enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
                  <% } else if (Constantes.SI.equals(request.getAttribute("aceptadoCeis")) && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS)){ %>
                     alert('Los M\u00e9ritos Curriculares del usuario est\u00E1n siendo evaluados por la Comisi\u00F3n Central. No puede modificar su expediente.');
                     enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
                  <% } else { %>
                     if(confirm("Se va a ACEPTAR la Evaluaci\u00f3n de M\u00e9ritos Curriculares. \u00BFDesea continuar?")){
                        enviar('OCAPSolicitudes.do?accion=insertarAceptacionEvaluacionMC'+cadenaParam+'&opcion=<%=request.getParameter("opcion")%>');
                     }
                  <% } %>
               <% } %>
            } else if (!aclaracionesPendientes) {
               // Validacion No aceptada
            //Si es CEIS solo puede desestimarla una vez
               <% if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS) ) { %>
               <% if  (request.getAttribute("denegacion") == null || request.getAttribute("denegacion").equals(Constantes.SIN_DENEGAR)) { %> 
                     if(confirm("Se va a DESESTIMAR la Evaluaci\u00f3n de M\u00e9ritos Curriculares, \u00BFDesea continuar?")){
                        enviar('OCAPSolicitudes.do?accion=insertarDenegacion&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesDt&jspDenegacion=detalle&fase=<%=request.getAttribute("fase")%>&denegacion=<%=request.getAttribute("denegacion")%>'+cadenaParam);
                     }
                  <% } else { %>
                  alert('Ya ha evaluado este expediente. \nLos M\u00e9ritos Curriculares del usuario ser\u00E1n validados por la Comisi\u00F3n Central.');
                     enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
                  <% } %>
               <% } else { %>
                  <%if(request.getAttribute("denegacion") != null && 
                     request.getAttribute("denegacion").equals(Constantes.REDENEGADO)){%>
                     alert('Ya ha desestimado la Evaluaci\u00f3n de M\u00e9ritos Curriculares. La solicitud permanecer\u00e1 denegada sin registrarse ning\u00fan cambio.');
                     //history.back();
                     //enviar('OCAPSolicitudes.do?accion=irBuscar');
                     <%if (request.getParameter("opcion").equals(Constantes.GRS_MERIT) || request.getParameter("opcion").equals(Constantes.GRS_PROCC)){ %>    
                        enviar("OCAPSolicitudes.do?accion=buscarGrs&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
                     <% } else { %>   
                        enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
                     <% } %>                     
                  <%} else if (request.getAttribute("denegacion") != null && 
                     request.getAttribute("denegacion").equals(Constantes.DENEGADO_SIN_INCONF)) { %>
                     //history.back();
                     //enviar('OCAPSolicitudes.do?accion=irBuscar');
                     <%if (request.getParameter("opcion").equals(Constantes.GRS_MERIT) || request.getParameter("opcion").equals(Constantes.GRS_PROCC)){ %>    
                        if(confirm("Se va a DESESTIMAR DEFINITIVAMENTE la Evaluaci\u00f3n de M\u00e9ritos Curriculares, \u00BFDesea continuar?")){
                           enviar('OCAPSolicitudes.do?accion=insertarDenegacion&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesDt&jspDenegacion=detalle&fase=<%=request.getAttribute("fase")%>&denegacion=<%=request.getAttribute("denegacion")%>'+cadenaParam);
                        }                        
                     <% } else { %> 
                        alert('No se volver\u00e1 a desestimar la Evaluaci\u00f3n de M\u00e9ritos Curriculares hasta que no se registren las alegaciones.\n La solicitud permanecer\u00e1 desestimada sin registrarse ning\u00fan cambio.');
                        enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
                     <% } %>                
                  <%} else if (request.getAttribute("denegacion") != null && 
                     request.getAttribute("denegacion").equals(Constantes.DENEGADO_CON_INCONF)) { %>
                     if(confirm("Se va a DESESTIMAR DEFINITIVAMENTE la Evaluaci\u00f3n de M\u00e9ritos Curriculares, \u00BFDesea continuar?")){
                        enviar('OCAPSolicitudes.do?accion=insertarDenegacion&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesDt&jspDenegacion=detalle&fase=<%=request.getAttribute("fase")%>&denegacion=<%=request.getAttribute("denegacion")%>'+cadenaParam);
                     }
                  <%} else { %>
                     if(confirm("Se va a DESESTIMAR la Evaluaci\u00f3n de M\u00e9ritos Curriculares, \u00BFDesea continuar?")){
                        enviar('OCAPSolicitudes.do?accion=insertarDenegacion&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesDt&jspDenegacion=detalle&fase=<%=request.getAttribute("fase")%>&denegacion=<%=request.getAttribute("denegacion")%>'+cadenaParam);
                     }
                  <%}%>
               <%}%>
            } else { //si tiene aclaraciones pendientes
               alert('Hay aclaraciones de M\u00E9ritos Curriculares pendientes de aclararse.');
               <%if (request.getParameter("opcion").equals(Constantes.GRS_MERIT) || request.getParameter("opcion").equals(Constantes.GRS_PROCC)){ %>    
                  enviar("OCAPSolicitudes.do?accion=buscarGrs&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
               <% } else { %>       
                  enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
               <%}%>
            }
         <% } %>
      <%} else if(request.getAttribute("fase") != null && request.getAttribute("fase").equals(Constantes.FASE_CA)){%>
         //alert('Esta en fase CA. No hecho desarrollo... Fase III');
         //history.back();
         //enviar('OCAPSolicitudes.do?accion=irBuscar');
         <%if (request.getParameter("opcion").equals(Constantes.GRS_MERIT) || request.getParameter("opcion").equals(Constantes.GRS_PROCC)){ %>    
            enviar("OCAPSolicitudes.do?accion=buscarGrs&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
         <% } else { %>       
            enviar("OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>");
         <%}%>
      <%}%>
   }
}

function validarFaseCC(){
   <% if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_GRS) &&
      request.getAttribute("fase").equals(Constantes.FASE_VALIDACION)) {%>
      if (document.OCAPSolicitudesForm.BValidacion_cc[0].checked == false && document.OCAPSolicitudesForm.BValidacion_cc[1].checked == false){
         alert('Debe rellenar el campo de Validaci\u00F3n CC de M\u00E9ritos Curriculares.');
         return false;
      }
   <%}%>
   
   return true;
}

function aceptarGradoCC(){
   if (document.OCAPSolicitudesForm.BReconocimientoGrado[0].checked == false && document.OCAPSolicitudesForm.BReconocimientoGrado[1].checked == false){
      alert('Debe rellenar el campo de Reconocimiento de Grado.');
   } else {
      enviar("OCAPSolicitudes.do?accion=aceptarGrado&opcion=<%=request.getParameter("opcion")%>");
   }
}

function radiosIniciacionRellenos() {
   if (document.OCAPSolicitudesForm.RSolicitud_acceso[0].checked == false && 
   document.OCAPSolicitudesForm.RSolicitud_acceso[1].checked == false)
      return false;
   if (document.OCAPSolicitudesForm.BPersonales[0].checked == false && 
   document.OCAPSolicitudesForm.BPersonales[1].checked == false)
      return false;
   if (document.OCAPSolicitudesForm.BInconf_antiguedad[0].checked == false && 
   document.OCAPSolicitudesForm.BInconf_antiguedad[1].checked == false)
      return false;
   if (document.OCAPSolicitudesForm.BInconf_plazaprop[0].checked == false && 
   document.OCAPSolicitudesForm.BInconf_plazaprop[1].checked == false)
      return false;
   if (document.OCAPSolicitudesForm.BPlazo[0].checked == false && 
   document.OCAPSolicitudesForm.BPlazo[1].checked == false)
      return false;       
   if (document.OCAPSolicitudesForm.BOtrosCentros[0].checked == false && 
   document.OCAPSolicitudesForm.BOtrosCentros[1].checked == false && 
   (document.OCAPSolicitudesForm.BOtrosCentros[0].disabled != true ||
   document.OCAPSolicitudesForm.BOtrosCentros[1].disabled != true) ) {
      return false;
      }
   return true;
}

function continuidadProceso(){   
   if (document.OCAPSolicitudesForm.BValidacion_cc[0].checked == true){
      document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[0].checked = true;
      document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[1].checked = false;      
   } else {
      document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[0].checked = false;
      document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[1].checked = true;    
   }
}

function aceptarSolicitud() {
   if (document.OCAPSolicitudesForm.RSolicitud_acceso[0].checked == true && 
      document.OCAPSolicitudesForm.BPersonales[0].checked == true && 
      document.OCAPSolicitudesForm.BInconf_antiguedad[0].checked == true && 
      document.OCAPSolicitudesForm.BInconf_plazaprop[0].checked == true &&
      document.OCAPSolicitudesForm.BPlazo[0].checked == true &&            
      (document.OCAPSolicitudesForm.BOtrosCentros[0].checked == true ||
        (document.OCAPSolicitudesForm.BOtrosCentros[0].checked == false && 
        document.OCAPSolicitudesForm.BOtrosCentros[1].checked == false &&
        document.OCAPSolicitudesForm.BOtrosCentros[0].disabled == true &&
        document.OCAPSolicitudesForm.BOtrosCentros[1].disabled == true) ) ){
      document.OCAPSolicitudesForm.RContinuidad_proceso[0].checked = true;
   } else document.OCAPSolicitudesForm.RContinuidad_proceso[1].checked = true;
}

function aceptarContinuaSolicitud() {
      if (document.OCAPSolicitudesForm.RSolicitud_acceso[1].checked == true ||
      document.OCAPSolicitudesForm.BPersonales[1].checked == true || 
      document.OCAPSolicitudesForm.BInconf_antiguedad[1].checked == true || 
      document.OCAPSolicitudesForm.BInconf_plazaprop[1].checked == true ||
      document.OCAPSolicitudesForm.BPlazo[1].checked == true ||            
      document.OCAPSolicitudesForm.BOtrosCentros[1].checked == true ){
      document.OCAPSolicitudesForm.RContinuidad_proceso[1].checked = true;
   }
}

function aceptarTramitacion() {
   if (document.OCAPSolicitudesForm.RTramitacion_correcta[0].checked == true){
      document.OCAPSolicitudesForm.RContinuidad_proceso_tramitacion[0].checked = true;
   } else document.OCAPSolicitudesForm.RContinuidad_proceso_tramitacion[1].checked = true;
}

function validarFechaRegOficialMc (){
   var cadena = document.forms[0].FRegistro_oficialMC.value;
   cadena = trim(cadena);
   if(cadena == ''){
      alert("Debe rellenar el campo \" Fecha de Registro Oficial de M\u00e9ritos Curriculares\".");
      return false;
   }
   //Si no introduce la hora, ponemos por defecto las 00:00:00
   if (cadena.length < 19 && cadena.length >= 10) {
      cadena = cadena.substring(0,10)+' 00:00:00';
      document.forms[0].FRegistro_oficialMC.value = cadena;
   }
   if (!(esTimestampCorrecto(cadena))){
      alert("Formato de \" Fecha de Registro Oficial de M\u00e9ritos Curriculares\" no es correcto.");
      return false;
   }
   if(comprobarFechaNoFutura(cadena)){
      alert("La \" Fecha de Registro Oficial de M\u00e9ritos Curriculares\" no es correcta.");
      return false;
   }
   return true;
}

var listaCreditosJS = new Array();
var creditosValidadosSuficientes = true;
var aclaracionesPendientes = false;

</script>

<!-- En un Array de JS metemos los creditos necesarios y conseguidos -->
<logic:present name="OCAPSolicitudesForm" property="listaCreditos">
	<bean:size id="cuantosListaCreditos" name="OCAPSolicitudesForm"
		property="listaCreditos" />
	<logic:equal name="cuantosListaCreditos" value="0">
		<script language="javascript" type="text/javascript">creditosValidadosSuficientes=false;</script>
	</logic:equal>
	<logic:iterate id="meritos" name="OCAPSolicitudesForm"
		property="listaCreditos" type="OCAPCreditosCeisOT" indexId="indice">
		<script language="javascript" type="text/javascript">
         linea = new Array();
         linea[0] = '<bean:write name="meritos" property="DDefMeritoNombre"/>';
         linea[1] = '<bean:write name="meritos" property="NCreditosNecesarios"/>';
         linea[2] = '<bean:write name="meritos" property="NCreditosValidados"/>';
         linea[3] = '<bean:write name="meritos" property="NCreditos"/>';
         listaCreditosJS[listaCreditosJS.length] = linea;
         if (eval(linea[2]) < eval(linea[1])) {
            creditosValidadosSuficientes=false;
         }
      </script>
	</logic:iterate>
</logic:present>
<!-- Si tiene aclaraciones pendientes no puede ni acpetar ni denegar fase eval MC -->
<logic:equal name="pendientesAclaracion" value="<%=Constantes.SI%>">
	<script language="javascript" type="text/javascript">aclaracionesPendientes=true;</script>
</logic:equal>

<script language="javascript" type="text/javascript">
function aceptarValidacion(){
   var longListaCreditos = listaCreditosJS.length;
   var creditosSuficientes = true;
   for (i=0; i < longListaCreditos && creditosSuficientes; i++) {
      var aux = document.getElementById('creditoValidado'+i).value;
      if (aux =='') aux='0';
      var aux= aux.replace(/,/g,'.');
      var creditosValidados = eval(aux);
      if (creditosValidados < listaCreditosJS[i][1])
         creditosSuficientes=false;
   }
   <% if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_GRS) &&
      request.getAttribute("fase").equals(Constantes.FASE_VALIDACION)) {%>
      if (document.OCAPSolicitudesForm.BValidacion_cc[0].checked == false && document.OCAPSolicitudesForm.BValidacion_cc[1].checked == false){   
         if (creditosSuficientes) {
            document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[0].checked = true;
            document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[1].checked = false;
         } else {
            document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[0].checked = false;
            document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[1].checked = true;
         }
      }else{
          continuidadProceso();
      }
   <%}else{%>
      if (creditosSuficientes) {
         document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[0].checked = true;
         document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[1].checked = false;
      } else {
         document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[0].checked = false;
         document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[1].checked = true;
      }   
   <%}%>
}

function soloNumerosDecimales(evt) {
   //se admiten: numeros . ,
   var nav4 = window.Event ? true : false;
   var key = nav4 ? evt.which : evt.keyCode; 
   if (key == null)
      key = nav4 ? evt.keyCode : evt.which;
   return (key <= 13 || (key >= 48 && key <= 57) || key==44 || key==46);
}

function registrarInconformidad(){
   enviar('OCAPSolicitudes.do?accion=irInconformidad&opcion=<%=request.getParameter("opcion")%>&fase=<%=request.getAttribute("fase")%>');
}

function generarPDF(){
   enviar('OCAPSolicitudes.do?accion=generarPDF&jspAccion=OCAPSolicitudesDt&fase=<%=request.getAttribute("fase")%>&denegacion=<%=request.getAttribute("denegacion")%>&opcion=<%=request.getParameter("opcion")%>');
}
</script>

<div class="contenido">
	<div class="contenidoDCP2A">
		<html:form action="/OCAPSolicitudes.do">
			<html:hidden name="OCAPSolicitudesForm" property="CExp_id" />
			<html:hidden name="OCAPSolicitudesForm" property="DApellido1" />
			<html:hidden name="OCAPSolicitudesForm" property="DNombre" />
			<html:hidden name="OCAPSolicitudesForm" property="CDni" />
			<html:hidden name="OCAPSolicitudesForm" property="CDniReal" />
			<html:hidden name="OCAPSolicitudesForm" property="BSexo" />
			<html:hidden name="OCAPSolicitudesForm" property="NTelefono1" />
			<html:hidden name="OCAPSolicitudesForm" property="NTelefono2" />
			<html:hidden name="OCAPSolicitudesForm" property="DCorreoelectronico" />
			<html:hidden name="OCAPSolicitudesForm" property="CJuridico" />
			<html:hidden name="OCAPSolicitudesForm"	property="DSitAdministrativa_nombre" />
			<html:hidden name="OCAPSolicitudesForm" property="CSitAdministrativaAuxId" />
			<html:hidden name="OCAPSolicitudesForm"	property="DSitAdministrativaAuxOtros" />
			<html:hidden name="OCAPSolicitudesForm"	property="DEstatutarioActual_nombre" />
			<html:hidden name="OCAPSolicitudesForm"	property="DProfesionalActual_nombre" />
			<html:hidden name="OCAPSolicitudesForm"	property="DEspecActual_nombre" />
			<html:hidden name="OCAPSolicitudesForm" property="DEstatutario_nombre" />
			<html:hidden name="OCAPSolicitudesForm" property="DProfesional_nombre" />
			<html:hidden name="OCAPSolicitudesForm" property="CProfesional_id" />
			<html:hidden name="OCAPSolicitudesForm" property="DEspec_nombre" />
			<html:hidden name="OCAPSolicitudesForm" property="DProvincia" />
			<html:hidden name="OCAPSolicitudesForm" property="DTipogerencia_desc" />
			<html:hidden name="OCAPSolicitudesForm" property="DGerencia_nombre" />
			<html:hidden name="OCAPSolicitudesForm"	property="DCentrotrabajo_nombre" />
			<html:hidden name="OCAPSolicitudesForm" property="DGrado_des" />
			<html:hidden name="OCAPSolicitudesForm" property="FRegistro_solic" />
			<html:hidden name="OCAPSolicitudesForm" property="DDirector" />
			<html:hidden name="OCAPSolicitudesForm" property="DGerente" />
			<h2 class="tituloContenido">HISTORIAL DE CARRERA PROFESIONAL</h2>
			<a href="javascript:window.print();"><img
				src="imagenes/imagenes_ocap/imprimir.gif" alt="Imprimir"
				class="flotaIzq" /></a>
			<h3 class="subTituloContenido">DCP2A</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<% if (!Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {%>
			<fieldset>
				<legend class="tituloLegend"> Datos Personales </legend>
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
					<br /> <label for="idVCorreo" class="colocaDatosVis">
						Correo Electr&oacute;nico: <span><bean:write
								name="OCAPSolicitudesForm" property="DCorreoelectronico" /></span>
					</label> <br />
					<br /> <label for="idVTelefono" class="colocaDatosVis">
						Tel&eacute;fono 1: <span><bean:write
								name="OCAPSolicitudesForm" property="NTelefono1" /></span>
					</label> <label for="idVTelefono" class="colocaDatosVis">
						Tel&eacute;fono 2: <span><bean:write
								name="OCAPSolicitudesForm" property="NTelefono2" /></span>
					</label> <br />
					<br /> <label for="idVTelefono" class="colocaDatosVisMuyGrande">
						Domicilio: <span><bean:write name="OCAPSolicitudesForm"
								property="DDomicilio" /></span>
					</label> <br />
					<br /> <label for="idVTelefono" class="colocaDatosVis">
						C&oacute;digo Postal: <span><bean:write
								name="OCAPSolicitudesForm" property="CPostalUsu" /></span>
					</label> <br />
					<br /> <label for="idVTelefono" class="colocaDatosVis">
						Localidad: <span><bean:write name="OCAPSolicitudesForm"
								property="DLocalidadUsu" /></span>
					</label> <label for="idVTelefono" class="colocaDatosVis">
						Provincia: <span><bean:write name="OCAPSolicitudesForm"
								property="DProvinciaUsu" /></span>
					</label>
				</div>
			</fieldset>

			<fieldset>
				<legend class="tituloLegend"> Datos Profesionales Actuales
				</legend>
				<div class="cuadroFieldset modificarInteralineado">
					<label for="idVCategoria" class="colocaDatosVisGrande">R&eacute;gimen
						jur&iacute;dico: <logic:equal name="OCAPSolicitudesForm"
							property="CJuridico"
							value="<%=Constantes.C_JURIDICO_ESTATUTARIO_COD%>">
							<span><%=Constantes.C_JURIDICO_ESTATUTARIO%></span>
						</logic:equal> <logic:equal name="OCAPSolicitudesForm" property="CJuridico"
							value="<%=Constantes.C_JURIDICO_FUNCIONARIO_COD%>">
							<span><%=Constantes.C_JURIDICO_FUNCIONARIO%></span>
						</logic:equal> <logic:equal name="OCAPSolicitudesForm" property="CJuridico"
							value="<%=Constantes.C_JURIDICO_OTROS_COD%>">
							<span><%=Constantes.C_JURIDICO_OTROS%></span>
						</logic:equal>
						<logic:equal name="OCAPSolicitudesForm"
												property="CJuridico"
												value="<%=Constantes.C_JURIDICO_INTFUNC_COD%>">
												<span><%=Constantes.C_JURIDICO_INTFUNC%></span>
											</logic:equal>
											<logic:equal name="OCAPSolicitudesForm"
												property="CJuridico"
												value="<%=Constantes.C_JURIDICO_INTEST_COD%>">
												<span><%=Constantes.C_JURIDICO_INTEST%></span>
											</logic:equal>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisMuyGrande">Situaci&oacute;n
						Administrativa: <logic:equal name="OCAPSolicitudesForm"
							property="CSitAdministrativaAuxId"
							value="<%=Constantes.C_SIT_ADM_AUX_ACTIVO_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_ACTIVO%></span>
						</logic:equal> <logic:equal name="OCAPSolicitudesForm"
							property="CSitAdministrativaAuxId"
							value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_OTROS%>: <bean:write
									name="OCAPSolicitudesForm"
									property="DSitAdministrativaAuxOtros" /></span>
						</logic:equal>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">
						Personal: <span><bean:write name="OCAPSolicitudesForm"
								property="DEstatutarioActual_nombre" /></span>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">
						Categor&iacute;a: <span><bean:write
								name="OCAPSolicitudesForm" property="DProfesionalActual_nombre" /></span>
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
					<br /> <label for="idVProvincia" class="colocaDatosVis">
						Provincia: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DProvincia">
								<bean:write name="OCAPSolicitudesForm" property="DProvincia" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm" property="DProvincia">-</logic:empty>
					</span>
					</label> <label for="idVProvincia" class="colocaDatosVis">
						Localidad: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="ACiudad">
								<bean:write name="OCAPSolicitudesForm" property="ACiudad" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm" property="ACiudad">-</logic:empty>
							<html:hidden name="OCAPSolicitudesForm" property="ACiudad" />
					</span>
					</label> <br />
					<br /> <label for="idVTipoGerencia" class="colocaDatosVis">
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
					<br /> <label for="idVCentro" class="colocaDatosVisGrande">
						Centro de Trabajo: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DCentrotrabajo_nombre">
								<bean:write name="OCAPSolicitudesForm"
									property="DCentrotrabajo_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm"
								property="DCentrotrabajo_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">
						Procedimiento de evaluaci&oacute;n por el que opta: <span><bean:write
								name="OCAPSolicitudesForm" property="DSitAdministrativa_nombre" /></span>
					</label>
				</div>
			</fieldset>

			<fieldset>
				<legend class="tituloLegend"> Datos por los que opta a
					Carrera Profesional </legend>
				<div class="cuadroFieldset">
					<label for="idVGrado" class="colocaDatosVis">Grado: <span><bean:write
								name="OCAPSolicitudesForm" property="DGrado_des" /></span>
					</label>
					<!--
               <label for="idVFecha" class="colocaDatosVis">Fecha de alta de solicitud:
                  <span class="textoDatos"><bean:write name="OCAPSolicitudesForm" property="FRegistro_solic" /></span>
               </label>
               <br /><br />
               -->
					<html:hidden property="CConvocatoriaId" />
					<!--
               <span class="colocaDatosVis"><span class="textoDatos">&nbsp;</span></span>
               <label class="colocaDatosVis ">Fecha de registro oficial:
                  <span class="textoDatos"><bean:write name="OCAPSolicitudesForm" property="FRegistro_oficial" /></span>
               </label>
               -->
					<html:hidden name="OCAPSolicitudesForm"
						property="FRegistro_oficial" />
					<br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">
						Personal: <span><bean:write name="OCAPSolicitudesForm"
								property="DEstatutario_nombre" /></span>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">
						Categor&iacute;a: <span><bean:write
								name="OCAPSolicitudesForm" property="DProfesional_nombre" /></span>
					</label><br />
					<br /> <label for="idVEspecialidad" class="colocaDatosVisGrande">
						Especialidad: <span> <logic:notEmpty
								name="OCAPSolicitudesForm" property="DEspec_nombre">
								<bean:write name="OCAPSolicitudesForm" property="DEspec_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPSolicitudesForm" property="DEspec_nombre">-</logic:empty>
					</span>
					</label>
					<div class="espaciador"></div>
				</div>
			</fieldset>

			<bean:size id="tamanoListaOtrosCentros" name="OCAPSolicitudesForm"
				property="listaOtrosCentros" />
			<logic:notEqual name="tamanoListaOtrosCentros" value="0">
				<fieldset>
					<legend class="tituloLegend"> Datos de Servicios Prestados
					</legend>
					<div class="cuadroFieldset">
						<table class="tablaCertif">
							<tr>
								<td class="tituloTabla" headers="dNombre">Centro de Trabajo
								</td>
								<td class="tituloTabla" headers="aProvincia">Provincia</td>
								<td class="tituloTabla" headers="aCategoria">
									Categor&iacute;a</td>
								<td class="tituloTabla" headers="aSituacion">
									Situaci&oacute;n</td>
								<td class="tituloTabla" headers="aVinculo">V&iacute;nculo</td>
								<td class="tituloTabla" headers="fInicio">Fecha Inicio</td>
								<td class="tituloTabla" headers="fFin">Fecha Fin</td>
							</tr>
							<logic:iterate id="listadoOtrosCentros"
								name="OCAPSolicitudesForm" property="listaOtrosCentros"
								type="OCAPOtrosCentrosOT" indexId="indice">
								<tr>
									<td class="tit"><bean:write name="listadoOtrosCentros"
											property="DNombre" /></td>
									<td><bean:write name="listadoOtrosCentros"
											property="AProvincia" /></td>
									<td><bean:write name="listadoOtrosCentros"
											property="ACategoria" /></td>
									<td><logic:equal name="listadoOtrosCentros"
											property="ASituacion"
											value="<%=Constantes.SITUACION_ACTIVO_COD%>">
											<%=Constantes.SITUACION_ACTIVO%>
										</logic:equal> <logic:equal name="listadoOtrosCentros" property="ASituacion"
											value="<%=Constantes.SITUACION_SERV_ESP_COD%>">
											<%=Constantes.SITUACION_SERV_ESP%>
										</logic:equal> <logic:equal name="listadoOtrosCentros" property="ASituacion"
											value="<%=Constantes.SITUACION_EXCEDENCIA_SERV_COD%>">
											<%=Constantes.SITUACION_EXCEDENCIA_SERV%>
										</logic:equal> <logic:equal name="listadoOtrosCentros" property="ASituacion"
											value="<%=Constantes.SITUACION_EXCEDENCIA_FAM_COD%>">
											<%=Constantes.SITUACION_EXCEDENCIA_FAM%>
										</logic:equal> <logic:equal name="listadoOtrosCentros" property="ASituacion"
											value="<%=Constantes.SITUACION_OTRAS_COD%>">
											<%=Constantes.SITUACION_OTRAS%>
										</logic:equal></td>
									<td><logic:equal name="listadoOtrosCentros"
											property="AVinculo" value="<%=Constantes.VINCULO_EP_COD%>">
											<%=Constantes.VINCULO_EP%>
										</logic:equal> <logic:equal name="listadoOtrosCentros" property="AVinculo"
											value="<%=Constantes.VINCULO_ET_COD%>">
											<%=Constantes.VINCULO_ET%>
										</logic:equal> <logic:equal name="listadoOtrosCentros" property="AVinculo"
											value="<%=Constantes.VINCULO_FC_COD%>">
											<%=Constantes.VINCULO_FC%>
										</logic:equal> <logic:equal name="listadoOtrosCentros" property="AVinculo"
											value="<%=Constantes.VINCULO_FT_COD%>">
											<%=Constantes.VINCULO_FT%>
										</logic:equal> <logic:equal name="listadoOtrosCentros" property="AVinculo"
											value="<%=Constantes.VINCULO_LF_COD%>">
											<%=Constantes.VINCULO_LF%>
										</logic:equal> <logic:equal name="listadoOtrosCentros" property="AVinculo"
											value="<%=Constantes.VINCULO_LT_COD%>">
											<%=Constantes.VINCULO_LT%>
										</logic:equal></td>
									<td><%=DateUtil.convertDateToString(listadoOtrosCentros.getFInicio())%></td>
									<td><%=DateUtil.convertDateToString(listadoOtrosCentros.getFFin())%></td>
								</tr>
							</logic:iterate>
						</table>
					</div>
				</fieldset>
			</logic:notEqual>

			<logic:present name="faseHist">
				<fieldset>
					<legend class="tituloLegend"> Historial de convocatoria	actual </legend>
					<div class="cuadroFieldset">
						<table class="tablaCertif centrarTablaCertif2">
							<tr>
								<td class="tituloTabla" headers="dFase">Fase</td>
								<td class="tituloTabla" headers="fFecha">Fecha</td>
							</tr>
							<logic:iterate id="elemento" name="faseHist"
								type="OCAPSolicitudesOT">
								<tr>
									<td class="textoTabla" headers="dFase"><bean:write
											name="elemento" property="DFase" /> <logic:notEqual
											name="fase" value="<%=Constantes.FASE_USUARIO%>">
											<% if (Constantes.SOLIC_INCONF_REGIS.equals(elemento.getDFase().toString())) {  %>
											<br />
											<a class="ocultarCampo"
												href="OCAPSolicitudes.do?accion=verInconformidad&expId=<bean:write name="OCAPSolicitudesForm" property="CExp_id"/>&fase=<bean:write name="elemento" property="DFase" />">Ver
												alegaciones</a>
											<% } %>
											<% if(Constantes.TMC_INCONF_REGISTRADA.equals(elemento.getDFase().toString())) {  %>
											<br />
											<a class="ocultarCampo"
												href="OCAPSolicitudes.do?accion=verInconformidad&expId=<bean:write name="OCAPSolicitudesForm" property="CExp_id"/>&fase=<bean:write name="elemento" property="DFase" />">Ver
												alegaciones</a>
											<% } %>
											<% if(Constantes.MC_INCONF_REGISTRADA.equals(elemento.getDFase().toString())) {  %>
											<br />
											<a class="ocultarCampo"
												href="OCAPSolicitudes.do?accion=verInconformidad&expId=<bean:write name="OCAPSolicitudesForm" property="CExp_id"/>&fase=<bean:write name="elemento" property="DFase" />">Ver
												alegaciones</a>
											<% } %>
										</logic:notEqual></td>
									<td class="textoTabla" headers="fFecha"><bean:write
											name="elemento" property="FFechaRegistro" /></td>
								</tr>
							</logic:iterate>
						</table>
					</div>
				</fieldset>
			</logic:present>

			<logic:present name="faseCredValidados">
				<logic:equal name="faseCredValidados" value="<%=Constantes.SI%>">
					<bean:size id="tamanoCreditosValidados" name="OCAPSolicitudesForm"
						property="listaCreditos" />
					<logic:notEqual name="tamanoCreditosValidados" value="0">
						<fieldset>
							<legend class="tituloLegend"> Validaci&oacute;n de
								M&eacute;ritos Curriculares </legend>
							<div class="cuadroFieldset">
								<table class="tablaFaseEvaluacion">
									<tr class="titulos">
										<td></td>
										<td>Cr&eacute;ditos Necesarios</td>
										<td>Cr&eacute;ditos Obtenidos</td>
										<td>Cr&eacute;ditos Validados</td>
									</tr>
									<logic:iterate id="meritos" name="OCAPSolicitudesForm"
										property="listaCreditos" type="OCAPCreditosCeisOT"
										indexId="indice">
										<tr>
											<td class="tit"><bean:write name="meritos"	property="DDefMeritoNombre" /></td>
											<td><bean:write name="meritos"	property="NCreditosNecesarios" /></td>
											<td><%=meritos.getNCreditos()%></td>
											<td><%=meritos.getNCreditosValidados()%></td>
										</tr>
									</logic:iterate>
								</table>
							</div>
						</fieldset>
					</logic:notEqual>
				</logic:equal>
			</logic:present>
			<% } else { %><%-- Fin de IF No es Perfil CC --%>
			<fieldset>
				<legend class="tituloLegend"> Datos del Evaluado </legend>
				<div class="cuadroFieldset">
					<label for="idVCategoria" class="colocaDatosVisGrande">	C&oacute;digo de Evaluado: <span><bean:write name="OCAPSolicitudesForm" property="codigoId" /></span>
					</label> <br />
					<br />
				</div>
			</fieldset>
			<% } %>
			<%-- Fin de Else No es Perfil CC --%>


			<!-- FASE INICIACION -->
			<logic:equal name="fase" value="<%=Constantes.FASE_INICIACION%>">
				<fieldset>
					<legend class="tituloLegend"> Iniciaci&oacute;n </legend>
					<div class="cuadroFieldset">
						<div class="listadoRespuesta">
							<span class="colocaTitRadio"> SI </span> <span> NO </span> <br />
							<br />

							<div class="titulosRadio">
								<div>1. Solicitud de Acceso</div>
								<div>2. Datos personales y profesionales correctos</div>
								<div>3. Constancia de la antig&uuml;edad requerida</div>
								<div>4. Constancia de plaza en propiedad</div>
								<div>5. Constancia de servicios prestados en otros centros
								</div>
								<div>6. Solicitud de registro en plazo</div>
							</div>
							<div class="radioTitulos">
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="RSolicitud_acceso" styleClass="opcionRadio"
										value="Y" onclick="javascript:aceptarSolicitud();" />
									<html:radio name="OCAPSolicitudesForm"
										property="RSolicitud_acceso" styleClass="opcionRadio"
										value="N" onclick="javascript:aceptarSolicitud();" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm" property="BPersonales"
										styleClass="opcionRadio" value="N"
										onclick="javascript:aceptarSolicitud();" />
									<html:radio name="OCAPSolicitudesForm" property="BPersonales"
										styleClass="opcionRadio" value="Y"
										onclick="javascript:aceptarSolicitud();" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="BInconf_antiguedad" styleClass="opcionRadio"
										value="N" onclick="javascript:aceptarSolicitud();" />
									<html:radio name="OCAPSolicitudesForm"
										property="BInconf_antiguedad" styleClass="opcionRadio"
										value="Y" onclick="javascript:aceptarSolicitud();" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="BInconf_plazaprop" styleClass="opcionRadio"
										value="N" onclick="javascript:aceptarSolicitud();" />
									<html:radio name="OCAPSolicitudesForm"
										property="BInconf_plazaprop" styleClass="opcionRadio"
										value="Y" onclick="javascript:aceptarSolicitud();" />
								</div>
								<logic:notEqual name="tamanoListaOtrosCentros" value="0">
									<div>
										<html:radio name="OCAPSolicitudesForm"
											property="BOtrosCentros" styleClass="opcionRadio" value="N"
											onclick="javascript:aceptarSolicitud();" />
										<html:radio name="OCAPSolicitudesForm"
											property="BOtrosCentros" styleClass="opcionRadio" value="Y"
											onclick="javascript:aceptarSolicitud();" />
									</div>
								</logic:notEqual>
								<logic:equal name="tamanoListaOtrosCentros" value="0">
									<div>
										<html:radio name="OCAPSolicitudesForm"
											property="BOtrosCentros" styleClass="opcionRadio" value="N"
											disabled="true" />
										<html:radio name="OCAPSolicitudesForm"
											property="BOtrosCentros" styleClass="opcionRadio" value="Y"
											disabled="true" />
									</div>
								</logic:equal>
								<div>
									<html:radio name="OCAPSolicitudesForm" property="BPlazo"
										styleClass="opcionRadio" value="N"
										onclick="javascript:aceptarSolicitud();" />
									<html:radio name="OCAPSolicitudesForm" property="BPlazo"
										styleClass="opcionRadio" value="Y"
										onclick="javascript:aceptarSolicitud();" />
								</div>
							</div>
							<div class="limpiadora"></div>

						</div>
					</div>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> Alegaciones </legend>
					<div class="cuadroFieldset">
						<div class="listadoRespuesta">
							<span class="colocaTitRadio"> SI </span> <span> NO </span> <br />
							<br />

							<div class="titulosRadio">
								<div>En fase de Inicio</div>
								<div>Continuidad en el Proceso</div>
							</div>
							<div class="radioTitulos">
								<div>
									<html:radio name="OCAPSolicitudesForm" property="RFase_inicio"
										styleClass="opcionRadio" value="Y" disabled="true" />
									<html:radio name="OCAPSolicitudesForm" property="RFase_inicio"
										styleClass="opcionRadio" value="N" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="RContinuidad_proceso" styleClass="opcionRadio"
										value="Y" disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="RContinuidad_proceso" styleClass="opcionRadio"
										value="N" disabled="true" />
								</div>
							</div>
							<div class="limpiadora"></div>

						</div>
					</div>
				</fieldset>
			</logic:equal>

			<!-- FASE MC: el usuario esta evaluando sus meritos, no podemos hacer nada -->
			<logic:equal name="fase" value="<%=Constantes.FASE_MC%>">
				<fieldset>
					<legend class="tituloLegend"> Iniciaci&oacute;n </legend>
					<div class="cuadroFieldset">
						<div class="listadoRespuesta">
							<span class="colocaTitRadio"> SI </span> <span> NO </span> <br />
							<br />

							<div class="titulosRadio">
								<div>1. Solicitud de Acceso</div>
								<div>2. Datos personales y profesionales correctos</div>
								<div>3. Constancia de la antig&uuml;edad requerida</div>
								<div>4. Constancia de plaza en propiedad</div>
								<div>5. Constancia de servicios prestados en otros centros
								</div>
								<div>6. Solicitud de registro en plazo</div>
							</div>
							<div class="radioTitulos">
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="RSolicitud_acceso" styleClass="opcionRadio"
										value="Y" disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="RSolicitud_acceso" styleClass="opcionRadio"
										value="N" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm" property="BPersonales"
										styleClass="opcionRadio" value="N" disabled="true" />
									<html:radio name="OCAPSolicitudesForm" property="BPersonales"
										styleClass="opcionRadio" value="Y" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="BInconf_antiguedad" styleClass="opcionRadio"
										value="N" disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="BInconf_antiguedad" styleClass="opcionRadio"
										value="Y" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="BInconf_plazaprop" styleClass="opcionRadio"
										value="N" disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="BInconf_plazaprop" styleClass="opcionRadio"
										value="Y" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm" property="BOtrosCentros"
										styleClass="opcionRadio" value="N" disabled="true" />
									<html:radio name="OCAPSolicitudesForm" property="BOtrosCentros"
										styleClass="opcionRadio" value="Y" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm" property="BPlazo"
										styleClass="opcionRadio" value="N" disabled="true" />
									<html:radio name="OCAPSolicitudesForm" property="BPlazo"
										styleClass="opcionRadio" value="Y" disabled="true" />
								</div>
							</div>
							<div class="limpiadora"></div>
						</div>
					</div>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> Alegaciones </legend>
					<div class="cuadroFieldset">
						<div class="listadoRespuesta">
							<span class="colocaTitRadio"> SI </span> <span> NO </span> <br />
							<br />

							<div class="titulosRadio">
								<div>En fase de Inicio</div>
								<div>Continuidad en el Proceso</div>
							</div>
							<div class="radioTitulos">
								<div>
									<html:radio name="OCAPSolicitudesForm" property="RFase_inicio"
										styleClass="opcionRadio" value="Y" disabled="true" />
									<html:radio name="OCAPSolicitudesForm" property="RFase_inicio"
										styleClass="opcionRadio" value="N" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="RContinuidad_proceso" styleClass="opcionRadio"
										value="Y" disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="RContinuidad_proceso" styleClass="opcionRadio"
										value="N" disabled="true" />
								</div>
							</div>
							<div class="limpiadora"></div>

						</div>
					</div>
				</fieldset>
			</logic:equal>

			
			<!-- FASE VALIDACION -->
			<logic:equal name="fase" value="<%=Constantes.FASE_VALIDACION%>">
				<fieldset>
					<legend class="tituloLegend"> Iniciaci&oacute;n </legend>
					<div class="cuadroFieldset">
						<div class="listadoRespuesta">
							<span class="colocaTitRadio"> SI </span> <span> NO </span> <br />
							<br />

							<div class="titulosRadio">
								<div>1. Solicitud de Acceso</div>
								<div>2. Datos personales y profesionales correctos</div>
								<div>3. Constancia de la antig&uuml;edad requerida</div>
								<div>4. Constancia de plaza en propiedad</div>
								<div>5. Constancia de servicios prestados en otros centros
								</div>
								<div>6. Solicitud de registro en plazo</div>
							</div>
							<div class="radioTitulos">
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="RSolicitud_acceso" styleClass="opcionRadio"
										value="Y" disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="RSolicitud_acceso" styleClass="opcionRadio"
										value="N" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm" property="BPersonales"
										styleClass="opcionRadio" value="N" disabled="true" />
									<html:radio name="OCAPSolicitudesForm" property="BPersonales"
										styleClass="opcionRadio" value="Y" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="BInconf_antiguedad" styleClass="opcionRadio"
										value="N" disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="BInconf_antiguedad" styleClass="opcionRadio"
										value="Y" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="BInconf_plazaprop" styleClass="opcionRadio"
										value="N" disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="BInconf_plazaprop" styleClass="opcionRadio"
										value="Y" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm" property="BOtrosCentros"
										styleClass="opcionRadio" value="N" disabled="true" />
									<html:radio name="OCAPSolicitudesForm" property="BOtrosCentros"
										styleClass="opcionRadio" value="Y" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm" property="BPlazo"
										styleClass="opcionRadio" value="N" disabled="true" />
									<html:radio name="OCAPSolicitudesForm" property="BPlazo"
										styleClass="opcionRadio" value="Y" disabled="true" />
								</div>
							</div>
							<div class="limpiadora"></div>
						</div>
					</div>
				</fieldset>
				
				<fieldset>
					<legend class="tituloLegend"> Validaci&oacute;n del CEIS </legend>
					<div class="cuadroFieldset">
						<bean:size id="tamanoCreditos" name="OCAPSolicitudesForm"
							property="listaCreditos" />
						<logic:equal name="tamanoCreditos" value="0">
							<span>Este profesional no finaliz&oacute; su
								auto-evaluaci&oacute;n de M&eacute;ritos Curriculares dentro del
								plazo.</span>
						</logic:equal>
						<logic:notEqual name="tamanoCreditos" value="0">
							<table class="tablaFaseEvaluacion">
								<tr>
									<td></td>
									<td>Cr&eacute;ditos Necesarios</td>
									<td>Cr&eacute;ditos Obtenidos</td>
									<td>Cr&eacute;ditos Validados</td>
								</tr>
								<logic:iterate id="meritos" name="OCAPSolicitudesForm"
									property="listaCreditos" type="OCAPCreditosCeisOT"
									indexId="indice">
									<tr>
										<td class="tit"><bean:write name="meritos"
												property="DDefMeritoNombre" /></td>
										<td><bean:write name="meritos"
												property="NCreditosNecesarios" /></td>
										<td><bean:write name="meritos" property="NCreditos" /></td>
										<% if (jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE) ||
                         (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS) && (request.getAttribute("denegacion") != null && !request.getAttribute("denegacion").equals(Constantes.SIN_DENEGAR)) || (Constantes.SI.equals(request.getAttribute("aceptadoCeis")) )) || 
                         (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_GRS) && request.getAttribute("permisoGRS") != null && !request.getAttribute("permisoGRS").equals(Constantes.SI))) ) { %>
										
										<% } %>
										<td><bean:write name="meritos"
												property="NCreditosValidados" /></td>
										<input type="hidden" id="creditoValidado<%=indice%>" value="<%=meritos.getNCreditosValidados()%>" />
									</tr>
								</logic:iterate>
							</table>
						</logic:notEqual>
					</div>
				</fieldset>

				<% if (jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_GRS)) ) { %>
				<logic:equal name="permisoGRS" value="<%=Constantes.SI%>">
					<fieldset>
						<legend class="tituloLegend"> Validaci&oacute;n del CC </legend>
						<div class="cuadroFieldset">
							<div class="listadoRespuesta">
								<span class="colocaTitRadio"> SI </span> <span> NO </span> <br />
								<br />
								<div class="titulosRadio">
									<div>Continuidad en el Proceso</div>
								</div>
								<div class="radioTitulos">
									<div>
										<html:radio name="OCAPSolicitudesForm"
											property="BValidacion_cc" styleClass="opcionRadio" value="Y"
											onclick="javascript:continuidadProceso();" />
										<html:radio name="OCAPSolicitudesForm"
											property="BValidacion_cc" styleClass="opcionRadio" value="N"
											onclick="javascript:continuidadProceso();" />
									</div>
								</div>
							</div>
						</div>
					</fieldset>
				</logic:equal>
				<% } %>

				<fieldset>
					<legend class="tituloLegend"> Alegaciones </legend>
					<div class="cuadroFieldset">
						<div class="listadoRespuesta">
							<span class="colocaTitRadio"> SI </span> <span> NO </span> <br />
							<br />

							<div class="titulosRadio">
								<div>En fase de Inicio</div>
								<div>Continuidad en el Proceso</div>
								<div>En fase de Validaci&oacute;n</div>
								<div>Continuidad en el Proceso</div>
							</div>
							<div class="radioTitulos">
								<div>
									<html:radio name="OCAPSolicitudesForm" property="RFase_inicio"
										styleClass="opcionRadio" value="Y" disabled="true" />
									<html:radio name="OCAPSolicitudesForm" property="RFase_inicio"
										styleClass="opcionRadio" value="N" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="RContinuidad_proceso" styleClass="opcionRadio"
										value="Y" disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="RContinuidad_proceso" styleClass="opcionRadio"
										value="N" disabled="true" />
								</div>
								
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="RFase_validacion" styleClass="opcionRadio" value="Y"
										disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="RFase_validacion" styleClass="opcionRadio" value="N"
										disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="RContinuidad_proceso_validacion"
										styleClass="opcionRadio" value="Y" disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="RContinuidad_proceso_validacion"
										styleClass="opcionRadio" value="N" disabled="true" />
								</div>
							</div>
							<div class="limpiadora"></div>

						</div>
					</div>
				</fieldset>
				<logic:present name="OCAPSolicitudesForm" property="listaCreditos">
					<script language="javascript" type="text/javascript">
            if (creditosValidadosSuficientes){
               document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[0].checked = true;
               document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[1].checked = false;
            } else {
               document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[0].checked = false;
               document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[1].checked = true;
            }
         </script>
				</logic:present>
			</logic:equal>

			<!-- FASE CA -->
			<logic:equal name="fase" value="<%=Constantes.FASE_CA%>">
				<fieldset>
					<legend class="tituloLegend"> Iniciaci&oacute;n </legend>
					<div class="cuadroFieldset">
						<div class="listadoRespuesta">
							<span class="colocaTitRadio"> SI </span> <span> NO </span> <br />
							<br />

							<div class="titulosRadio">
								<div>1. Solicitud de Acceso</div>
								<div>2. Datos personales y profesionales correctos</div>
								<div>3. Constancia de la antig&uuml;edad requerida</div>
								<div>4. Constancia de plaza en propiedad</div>
								<div>5. Constancia de servicios prestados en otros centros
								</div>
								<div>6. Solicitud de registro en plazo</div>
							</div>
							<div class="radioTitulos">
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="RSolicitud_acceso" styleClass="opcionRadio"
										value="Y" disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="RSolicitud_acceso" styleClass="opcionRadio"
										value="N" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm" property="BPersonales"
										styleClass="opcionRadio" value="N" disabled="true" />
									<html:radio name="OCAPSolicitudesForm" property="BPersonales"
										styleClass="opcionRadio" value="Y" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="BInconf_antiguedad" styleClass="opcionRadio"
										value="N" disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="BInconf_antiguedad" styleClass="opcionRadio"
										value="Y" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="BInconf_plazaprop" styleClass="opcionRadio"
										value="N" disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="BInconf_plazaprop" styleClass="opcionRadio"
										value="Y" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm" property="BOtrosCentros"
										styleClass="opcionRadio" value="N" disabled="true" />
									<html:radio name="OCAPSolicitudesForm" property="BOtrosCentros"
										styleClass="opcionRadio" value="Y" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm" property="BPlazo"
										styleClass="opcionRadio" value="N" disabled="true" />
									<html:radio name="OCAPSolicitudesForm" property="BPlazo"
										styleClass="opcionRadio" value="Y" disabled="true" />
								</div>
							</div>
							<div class="limpiadora"></div>
						</div>
					</div>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> Validaci&oacute;n del CEIS </legend>
					<div class="cuadroFieldset">
						<bean:size id="tamanoCreditosCA" name="OCAPSolicitudesForm"
							property="listaCreditos" />
						<logic:equal name="tamanoCreditosCA" value="0">
							<span>Esta persona no finaliz&oacute; su
								auto-evaluaci&oacute;n de M&eacute;ritos Curriculares dentro del
								plazo.</span>
						</logic:equal>
						<logic:notEqual name="tamanoCreditosCA" value="0">
							<table class="tablaFaseEvaluacion">
								<tr>
									<td></td>
									<td>Cr&eacute;ditos Necesarios</td>
									<td>Cr&eacute;ditos Obtenidos</td>
									<td>Cr&eacute;ditos Validados</td>
								</tr>
								<logic:iterate id="meritos" name="OCAPSolicitudesForm"
									property="listaCreditos" type="OCAPCreditosCeisOT"
									indexId="indice">
									<tr>
										<td class="tit"><bean:write name="meritos"
												property="DDefMeritoNombre" /></td>
										<td><bean:write name="meritos"
												property="NCreditosNecesarios" /></td>
										<td><bean:write name="meritos" property="NCreditos" /></td>
										<td><bean:write name="meritos"
												property="NCreditosValidados" /></td>
										<input type="hidden" id="creditoValidado<%=indice%>"
											value="<%=meritos.getNCreditosValidados()%>" />
									</tr>
								</logic:iterate>
							</table>
						</logic:notEqual>
					</div>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> Validaci&oacute;n del CC de
						M&eacute;ritos Curriculares </legend>
					<div class="cuadroFieldset">
						<div class="listadoRespuesta">
							<span class="colocaTitRadio"> SI </span> <span> NO </span> <br />
							<br />
							<div class="titulosRadio">
								<div>Continuidad en el Proceso</div>
							</div>
							<div class="radioTitulos">
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="BValidacion_cc" styleClass="opcionRadio" value="Y"
										disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="BValidacion_cc" styleClass="opcionRadio" value="N"
										disabled="true" />
								</div>
							</div>
						</div>
					</div>
				</fieldset>

				<fieldset>
					<legend class="tituloLegend"> Alegaciones </legend>
					<div class="cuadroFieldset">
						<div class="listadoRespuesta">
							<span class="colocaTitRadio"> SI </span> <span> NO </span> <br />
							<br />

							<div class="titulosRadio">
								<div>En fase de Inicio</div>
								<div>Continuidad en el Proceso</div>

								<div>En fase de Validaci&oacute;n</div>
								<div>Continuidad en el Proceso</div>
							</div>
							<div class="radioTitulos">
								<div>
									<html:radio name="OCAPSolicitudesForm" property="RFase_inicio"
										styleClass="opcionRadio" value="Y" disabled="true" />
									<html:radio name="OCAPSolicitudesForm" property="RFase_inicio"
										styleClass="opcionRadio" value="N" disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="RContinuidad_proceso" styleClass="opcionRadio"
										value="Y" disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="RContinuidad_proceso" styleClass="opcionRadio"
										value="N" disabled="true" />
								</div>

								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="RFase_validacion" styleClass="opcionRadio" value="Y"
										disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="RFase_validacion" styleClass="opcionRadio" value="N"
										disabled="true" />
								</div>
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="RContinuidad_proceso_validacion"
										styleClass="opcionRadio" value="Y" disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="RContinuidad_proceso_validacion"
										styleClass="opcionRadio" value="N" disabled="true" />
								</div>
							</div>
							<div class="limpiadora"></div>
						</div>
					</div>
				</fieldset>
				<logic:present name="OCAPSolicitudesForm" property="listaCreditos">
					<script language="javascript" type="text/javascript">
               if (creditosValidadosSuficientes){
                  document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[0].checked = true;
                  document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[1].checked = false;
               } else {
                  document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[0].checked = false;
                  document.OCAPSolicitudesForm.RContinuidad_proceso_validacion[1].checked = true;
               }
            </script>
				</logic:present>
			</logic:equal>

			<!-- FASE_CC -->
			<logic:equal name="fase" value="<%=Constantes.FASE_CC%>">
				
				<% if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) { %>
				<fieldset>
					<legend class="tituloLegend"> Validaci&oacute;n del CC </legend>
					<div class="cuadroFieldset">
						<div class="listadoRespuesta">
							<span class="colocaTitRadio"> SI </span> <span> NO </span> <br />
							<br />
							<div class="titulosRadio">
								<div>Reconocimiento de Grado</div>
							</div>
							<div class="radioTitulos">
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="BReconocimientoGrado" styleClass="opcionRadio"
										value="Y" />
									<html:radio name="OCAPSolicitudesForm"
										property="BReconocimientoGrado" styleClass="opcionRadio"
										value="N" />
								</div>
							</div>
						</div>
					</div>
				</fieldset>
				<% } %>
			</logic:equal>

			<!-- FASE_FIN -->
			<logic:equal name="fase" value="<%=Constantes.FASE_FIN%>">
				
				<fieldset>
					<legend class="tituloLegend"> Validaci&oacute;n del CC </legend>
					<div class="cuadroFieldset">
						<div class="listadoRespuesta">
							<span class="colocaTitRadio"> SI </span> <span> NO </span> <br />
							<br />
							<div class="titulosRadio">
								<div>Reconocimiento de Grado</div>
							</div>
							<div class="radioTitulos">
								<div>
									<html:radio name="OCAPSolicitudesForm"
										property="BReconocimientoGrado" styleClass="opcionRadio"
										value="Y" disabled="true" />
									<html:radio name="OCAPSolicitudesForm"
										property="BReconocimientoGrado" styleClass="opcionRadio"
										value="N" disabled="true" />
								</div>
							</div>
						</div>
					</div>
				</fieldset>
			</logic:equal>

			<logic:equal name="fase" value="<%=Constantes.FASE_INICIACION%>">
				<logic:equal name="detalle" value="<%=Constantes.NO%>">
					<p>Los campos se&ntilde;alados con * son obligatorios</p>
				</logic:equal>
			</logic:equal>

			<div class="espaciador"></div>
			<div class="limpiadora"></div>

			<logic:notEqual name="fase" value="<%=Constantes.FASE_USUARIO%>">
				<logic:notEqual name="fase" value="<%=Constantes.FASE_FIN%>">
					<div class="botonesPagina">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverAtras();"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									alt="Cancelar Solicitud" /> <span> Cancelar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<logic:notEqual name="opcion" value="<%=Constantes.OCAP_DIRECCION%>">
						
							<% if ((!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_GRS) && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)) || 
                  (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_GRS) && request.getAttribute("permisoGRS") != null && request.getAttribute("permisoGRS").equals(Constantes.SI) )) { %>
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:aceptar();"> <img
										src="imagenes/imagenes_ocap/flecha_correcto.gif"
										alt="Aceptar Solicitud" /> <span> Aceptar </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
							<% } else if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {%>
							<logic:equal name="fase" value="<%=Constantes.FASE_CC%>">
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="javascript:aceptarGradoCC();"> <img
											src="imagenes/imagenes_ocap/flecha_correcto.gif"
											alt="Aceptar Validacion CC" /> <span> Aceptar </span>
									</a>
									</span> <span class="derBoton"></span>
								</div>
							</logic:equal>
							<% } %>
						</logic:notEqual>
					</div>
					<div class="limpiadora"></div>
					<% if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE) ) { %>
					<div class="botonesPagina">
						<logic:equal name="inconformidad" value="<%=Constantes.SI%>">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:registrarInconformidad();"> <img
										src="imagenes/imagenes_ocap/flecha_correcto.gif"
										alt="Registrar Alegaciones" /> <span> Registrar
											Alegaciones </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</logic:equal>						
						<% if (!Constantes.FASE_MC.equals(request.getAttribute("fase"))) { %>
						<logic:equal name="generarPDF" value="<%=Constantes.SI%>">
							<% if (Constantes.FASE_INICIACION.equals(request.getAttribute("fase"))) { %>
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:generarPDF();"> <img
										src="imagenes/imagenes_ocap/flecha_correcto.gif"
										alt="Generar Documento de Requerimiento de Subsanacion" /> <span>
											Generar documento de requerimiento de subsanaciones </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
							<% } %>
						</logic:equal>
						<% } %>
						<logic:equal name="reabrirMC" value="<%=Constantes.SI%>">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:reabrirMC();"> <img
										src="imagenes/imagenes_ocap/icono_modificar.gif"
										alt="Reabrir Auto-Evaluacion de Meritos Curriculares" /> <span>
											Reactivar fase de auto-evaluaci&oacute;n de M&eacute;ritos
											Curriculares </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</logic:equal>
					</div>
					<div class="limpiadora"></div>
					<% } //fin PGP 
            //Si es CEIS: ahora genera el las cartas
            else if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS) ) {%>
					<% if (!Constantes.FASE_MC.equals(request.getAttribute("fase")) && !Constantes.FASE_INICIACION.equals(request.getAttribute("fase"))) { %>
					<logic:equal name="generarPDF" value="<%=Constantes.SI%>">
						<div class="botonesPagina">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:generarPDF();"> <img
										src="imagenes/imagenes_ocap/flecha_correcto.gif"
										alt="Generar Documento Subsanaciones" /> <span>	Generar Informe Motivado </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</div>
					</logic:equal>
					<% } %>
					<% } %>
				</logic:notEqual>
			</logic:notEqual>

			<logic:equal name="fase" value="<%=Constantes.FASE_FIN%>">
				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:volverAtras();"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif"
								alt="Cancelar Solicitud" /> <span> Cancelar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
				<% if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE) ) { %>
				<div class="limpiadora"></div>
				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviar('OCAPSolicitudes.do?accion=generarPDFIndiviAccesoGrado');">
								<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Generar Informe Individualizado" /> <span> Informe Individualizado </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
				<% } %>
			</logic:equal>
		</html:form>
	</div>
	<!-- /fin de ContenidoDCP2A -->
</div>
<!-- /Fin de Contenido -->
