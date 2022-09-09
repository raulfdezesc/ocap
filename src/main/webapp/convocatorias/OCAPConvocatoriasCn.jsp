<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/fechas.js'/>"></script>
<script>

function validar(){
   var validacion = 0;
   // Validar numéricos
     
   if (document.OCAPConvocatoriasForm.DNombre.value != "" && !LetrasYNumeros(document.OCAPConvocatoriasForm.DNombre)){
         alert('Debe introducir correctamente el Nombre');
         validacion=1;   
      }

   if ((document.OCAPConvocatoriasForm.DResolucion.value != "" ) || (document.OCAPConvocatoriasForm.MResolucion.value != "" ) || (document.OCAPConvocatoriasForm.AResolucion.value != "")){
      var fecha = document.OCAPConvocatoriasForm.DResolucion.value + '/' + document.OCAPConvocatoriasForm.MResolucion.value + '/' + document.OCAPConvocatoriasForm.AResolucion.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Resoluci\u00f3n');
         validacion=1;   
      }
   } 
   
   if ((document.OCAPConvocatoriasForm.DPublicacion.value != "" ) || (document.OCAPConvocatoriasForm.MPublicacion.value != "" ) || (document.OCAPConvocatoriasForm.APublicacion.value != "")){
      var fecha = document.OCAPConvocatoriasForm.DPublicacion.value + '/' + document.OCAPConvocatoriasForm.MPublicacion.value + '/' + document.OCAPConvocatoriasForm.APublicacion.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Publicaci\u00f3n');
         validacion=1;   
      }
   }

   if ((document.OCAPConvocatoriasForm.DInicioMC.value != "" ) || (document.OCAPConvocatoriasForm.MInicioMC.value != "" ) || (document.OCAPConvocatoriasForm.AInicioMC.value != "")){
      var fecha = document.OCAPConvocatoriasForm.DInicioMC.value + '/' + document.OCAPConvocatoriasForm.MInicioMC.value + '/' + document.OCAPConvocatoriasForm.AInicioMC.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Inicio de MC');
         validacion=1;   
      }
   }

   if ((document.OCAPConvocatoriasForm.DInicioAlega.value != "" ) || (document.OCAPConvocatoriasForm.MInicioAlega.value != "" ) || (document.OCAPConvocatoriasForm.AInicioAlega.value != "")){
      var fecha = document.OCAPConvocatoriasForm.DInicioAlega.value + '/' + document.OCAPConvocatoriasForm.MInicioAlega.value + '/' + document.OCAPConvocatoriasForm.AInicioAlega.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Inicio de Alegaciones');
         validacion=1;   
      }
   }
   
   if ((document.OCAPConvocatoriasForm.DInicioCC.value != "" ) || (document.OCAPConvocatoriasForm.MInicioCC.value != "" ) || (document.OCAPConvocatoriasForm.AInicioCC.value != "")){
      var fecha = document.OCAPConvocatoriasForm.DInicioCC.value + '/' + document.OCAPConvocatoriasForm.MInicioCC.value + '/' + document.OCAPConvocatoriasForm.AInicioCC.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de listado provisional de MC');
         validacion=1;   
      }
   }

   if ((document.OCAPConvocatoriasForm.DFinCC.value != "" ) || (document.OCAPConvocatoriasForm.MFinCC.value != "" ) || (document.OCAPConvocatoriasForm.AFinCC.value != "")){
      var fecha = document.OCAPConvocatoriasForm.DFinCC.value + '/' + document.OCAPConvocatoriasForm.MFinCC.value + '/' + document.OCAPConvocatoriasForm.AFinCC.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de listado definitivo de MC');
         validacion=1;   
      }
   }
   
   if ((document.OCAPConvocatoriasForm.DInicioCA.value != "" ) || (document.OCAPConvocatoriasForm.MInicioCA.value != "" ) || (document.OCAPConvocatoriasForm.AInicioCA.value != "")){
      var fecha = document.OCAPConvocatoriasForm.DInicioCA.value + '/' + document.OCAPConvocatoriasForm.MInicioCA.value + '/' + document.OCAPConvocatoriasForm.AInicioCA.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Inicio de CA');
         validacion=1;   
      }
   }

   if ((document.OCAPConvocatoriasForm.DFinCp.value != "" ) || (document.OCAPConvocatoriasForm.MFinCp.value != "" ) || (document.OCAPConvocatoriasForm.AFinCp.value != "")){
      var fecha = document.OCAPConvocatoriasForm.DFinCp.value + '/' + document.OCAPConvocatoriasForm.MFinCp.value + '/' + document.OCAPConvocatoriasForm.AFinCp.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Fin de Convocatoria');
         validacion=1;   
      }
   }

   if ((document.OCAPConvocatoriasForm.DRecGrado.value != "" ) || (document.OCAPConvocatoriasForm.MRecGrado.value != "" ) || (document.OCAPConvocatoriasForm.ARecGrado.value != "")){
      var fecha = document.OCAPConvocatoriasForm.DRecGrado.value + '/' + document.OCAPConvocatoriasForm.MRecGrado.value + '/' + document.OCAPConvocatoriasForm.ARecGrado.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Reconocimiento de Grado');
         validacion=1;   
      }
   }
   
   if ((document.OCAPConvocatoriasForm.NDias_revsolicitud.value != "" && !soloNumeros(document.OCAPConvocatoriasForm.NDias_revsolicitud)) ||
       (document.OCAPConvocatoriasForm.NDias_regsolicitud.value != "" && !soloNumeros(document.OCAPConvocatoriasForm.NDias_regsolicitud)) ||
       (document.OCAPConvocatoriasForm.NDias_publistado.value != "" && !soloNumeros(document.OCAPConvocatoriasForm.NDias_publistado)) ||
       (document.OCAPConvocatoriasForm.NDias_auto_mc.value != "" && !soloNumeros(document.OCAPConvocatoriasForm.NDias_auto_mc)) ||
       (document.OCAPConvocatoriasForm.NDias_val_mc.value != "" && !soloNumeros(document.OCAPConvocatoriasForm.NDias_val_mc)) ||
       (document.OCAPConvocatoriasForm.NDias_inconf_mc.value != "" && !soloNumeros(document.OCAPConvocatoriasForm.NDias_inconf_mc)) ||
       (document.OCAPConvocatoriasForm.NDias_auto_ca.value != "" && !soloNumeros(document.OCAPConvocatoriasForm.NDias_auto_ca)) ||
       (document.OCAPConvocatoriasForm.NDias_val_ca.value != "" && !soloNumeros(document.OCAPConvocatoriasForm.NDias_val_ca)) ||
       (document.OCAPConvocatoriasForm.NDias_inconf_mc.value != "" && !soloNumeros(document.OCAPConvocatoriasForm.NDias_inconf_ca)) ||       
       (document.OCAPConvocatoriasForm.NDias_val_cc.value != "" && !soloNumeros(document.OCAPConvocatoriasForm.NDias_val_cc)) ||       
       (document.OCAPConvocatoriasForm.NDias_respinconf_mc.value != "" && !soloNumeros(document.OCAPConvocatoriasForm.NDias_respinconf_mc)) ||       
       (document.OCAPConvocatoriasForm.NDias_respinconf_ca.value != "" && !soloNumeros(document.OCAPConvocatoriasForm.NDias_respinconf_ca)) ||       
       (document.OCAPConvocatoriasForm.NDias_respinconf_cc.value != "" && !soloNumeros(document.OCAPConvocatoriasForm.NDias_respinconf_cc))){       
         alert('Debe introducir correctamente los plazos');
         validacion=1;   
      }   

   if (document.OCAPConvocatoriasForm.BCierreSo.value != "" && document.OCAPConvocatoriasForm.BCierreSo.value.toUpperCase() != "<%=Constantes.SI_ESP%>" && document.OCAPConvocatoriasForm.BCierreSo.value.toUpperCase() != "<%=Constantes.NO%>"){
         alert('El campo Cierre de convocatoria debe ser S o N');
         validacion=1;   
      }
      
   if (document.OCAPConvocatoriasForm.AObservaciones.value != "" && !LetrasYNumeros(document.OCAPConvocatoriasForm.AObservaciones)){
         alert('Debe introducir correctamente las Observaciones');
         validacion=1;   
      }  

       
   if ((document.OCAPConvocatoriasForm.DCreacion.value != "" ) || (document.OCAPConvocatoriasForm.MCreacion.value != "" ) || (document.OCAPConvocatoriasForm.ACreacion.value != "")){
      var fecha = document.OCAPConvocatoriasForm.DCreacion.value + '/' + document.OCAPConvocatoriasForm.MCreacion.value + '/' + document.OCAPConvocatoriasForm.ACreacion.value;      
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Creaci\u00f3n');
         validacion=1;   
      }
   }
   
   if ((document.OCAPConvocatoriasForm.DModificacion.value != "" ) || (document.OCAPConvocatoriasForm.MModificacion.value != "" ) || (document.OCAPConvocatoriasForm.AModificacion.value != "")){
      var fecha = document.OCAPConvocatoriasForm.DModificacion.value + '/' + document.OCAPConvocatoriasForm.MModificacion.value + '/' + document.OCAPConvocatoriasForm.AModificacion.value; 
   
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Modificaci\u00f3n');
         validacion=1;   
      }
   }       
   if (validacion==0) document.forms[0].submit();    
}   
function bajasClave(cConvocatoriaId)
{
   if (confirm('Va a ELIMINAR el registro selecionado')){
      document.forms[0].action = 'OCAPConvocatorias.do?accion=borrar';
      document.forms[0].cConvocatoriaIdS.value = cConvocatoriaId;

      document.forms[0].submit(); 
   }else{
      return false;
   }
}

function limpiar() {
   //document.forms[0].CGrado_id.options[0].selected='selected'; 
   document.forms[0].DNombre.value='';
   document.forms[0].AResolucion.value='';
   document.forms[0].MResolucion.value='';
   document.forms[0].DResolucion.value='';
   document.forms[0].APublicacion.value='';  
   document.forms[0].MPublicacion.value='';
   document.forms[0].DPublicacion.value='';  
   document.forms[0].AInicioMC.value='';  
   document.forms[0].MInicioMC.value='';
   document.forms[0].DInicioMC.value='';  
   document.forms[0].AInicioAlega.value='';  
   document.forms[0].MInicioAlega.value='';
   document.forms[0].DInicioAlega.value='';
   document.forms[0].AInicioCC.value='';  
   document.forms[0].MInicioCC.value='';
   document.forms[0].DInicioCC.value=''; 
   document.forms[0].AFinCC.value='';  
   document.forms[0].MFinCC.value='';
   document.forms[0].DFinCC.value='';    
   document.forms[0].AInicioCA.value='';  
   document.forms[0].MInicioCA.value='';
   document.forms[0].DInicioCA.value='';
   document.forms[0].AFinCp.value='';  
   document.forms[0].MFinCp.value='';
   document.forms[0].DFinCp.value=''; 
   document.forms[0].ARecGrado.value='';  
   document.forms[0].MRecGrado.value='';
   document.forms[0].DRecGrado.value='';   
   document.forms[0].NDias_regsolicitud.value=0;
   document.forms[0].NDias_revsolicitud.value=0;   
   document.forms[0].NDias_publistado.value=0;   
   document.forms[0].NDias_auto_mc.value=0;   
   document.forms[0].NDias_val_mc.value=0;   
   document.forms[0].NDias_inconf_mc.value=0; 
   document.forms[0].NDias_auto_ca.value=0;   
   document.forms[0].NDias_val_ca.value=0;   
   document.forms[0].NDias_inconf_ca.value=0;
   document.forms[0].NDias_val_cc.value=0;    
   document.forms[0].NDias_respinconf_mc.value=0; 
   document.forms[0].NDias_respinconf_ca.value=0;
   document.forms[0].NDias_respinconf_cc.value=0;
   document.forms[0].AObservaciones.value='';
   document.forms[0].ACreacion.value='';
   document.forms[0].MCreacion.value='';
   document.forms[0].DCreacion.value='';
   document.forms[0].AModificacion.value='';  
   document.forms[0].MModificacion.value='';
   document.forms[0].DModificacion.value='';
   document.forms[0].BCierreSo.value='';
}
</script>
<%
int iRow=0;
%>
<html:form action="/OCAPConvocatorias.do?accion=buscar">

	<div class="ocultarCampo">

		<div class="cuadroContenedorConsultas">
			<div class="titulocajaformulario">Consulta de Convocatorias</div>
			<logic:present name="errorConsultando">
				<p>
					<label><bean:write name="errorConsultando" filter="false" /></label>
				</p>
			</logic:present>
			<logic:notPresent name="errorConsultando">
				<div class="cajaformulario">
					<fieldset class="normales">



						<label class="obligado">Convocatoria:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaConvocaConvo" size="60"
							maxlength="200" />
						&nbsp;
						<html:errors property="DNombre" />
						<br /> <label class="obligado">Fecha de resoluci&oacute;n
							(dd/mm/aaaa):</label>
						<html:text property="DResolucion" tabindex="18"
							styleClass="inputColor colocaFechaConConvo" size="2"
							maxlength="2" />
						&nbsp;
						<html:text property="MResolucion" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="AResolucion" tabindex="18"
							styleClass="inputColor" size="4" maxlength="4" />
						&nbsp;
						<html:errors property="FResolucion" />
						<br /> <label class="obligado">Fecha de
							publicaci&oacute;n (dd/mm/aaaa):</label>
						<html:text property="DPublicacion" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="MPublicacion" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="APublicacion" tabindex="18"
							styleClass="inputColor" size="4" maxlength="4" />
						&nbsp;
						<html:errors property="FPublicacion" />
						<br /> <label class="obligado">Plazo de solicitud:</label>
						<html:text property="NDias_regsolicitud" tabindex="18"
							styleClass="inputColor colocaPSolConvo" size="2" maxlength="2" />
						&nbsp;
						<html:errors property="NDias_regsolicitud" />
						<br /> <label class="obligado">Plazo de revisi&oacute;n:</label>
						<html:text property="NDias_revsolicitud" tabindex="18"
							styleClass="inputColor colocaPRevConvo" size="2" maxlength="2" />
						&nbsp;
						<html:errors property="NDias_revsolicitud" />
						<br /> <label class="obligado">Plazo de
							publicaci&oacute;n:</label>
						<html:text property="NDias_publistado" tabindex="18"
							styleClass="inputColor colocaPlazo" size="2" maxlength="2" />
						&nbsp;
						<html:errors property="NDias_publistado" />
						<br /> <label class="obligado">Fecha de Inicio de
							autoevaluaci&oacute;n de MC (dd/mm/aaaa):</label>
						<html:text property="DInicioMC" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="MInicioMC" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="AInicioMC" tabindex="18"
							styleClass="inputColor" size="4" maxlength="4" />
						&nbsp;
						<html:errors property="FInicioMC" />
						<br /> <label class="obligado">Fecha de Inicio de
							Alegaciones (dd/mm/aaaa):</label>
						<html:text property="DInicioAlega" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="MInicioAlega" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="AInicioAlega" tabindex="18"
							styleClass="inputColor" size="4" maxlength="4" />
						&nbsp;
						<html:errors property="FInicioAlega" />
						<br /> <label class="obligado">Fecha de listado
							provisional de MC (dd/mm/aaaa):</label>
						<html:text property="DInicioCC" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="MInicioCC" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="AInicioCC" tabindex="18"
							styleClass="inputColor" size="4" maxlength="4" />
						&nbsp;
						<html:errors property="FInicioEstudioCC" />
						<br /> <label class="obligado">Fecha de listado
							definitivo de MC (dd/mm/aaaa):</label>
						<html:text property="DFinCC" tabindex="18" styleClass="inputColor"
							size="2" maxlength="2" />
						&nbsp;
						<html:text property="MFinCC" tabindex="18" styleClass="inputColor"
							size="2" maxlength="2" />
						&nbsp;
						<html:text property="AFinCC" tabindex="18" styleClass="inputColor"
							size="4" maxlength="4" />
						&nbsp;
						<html:errors property="FFinEstudioCC" />
						<br /> <label class="obligado">Fecha de Inicio de
							autoevaluaci&oacute;n de CA (dd/mm/aaaa):</label>
						<html:text property="DInicioCA" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="MInicioCA" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="AInicioCA" tabindex="18"
							styleClass="inputColor" size="4" maxlength="4" />
						&nbsp;
						<html:errors property="FInicioCA" />
						<br /> <label class="obligado">Fecha de Fin de
							Convocatoria (dd/mm/aaaa):</label>
						<html:text property="DFinCp" tabindex="18" styleClass="inputColor"
							size="2" maxlength="2" />
						&nbsp;
						<html:text property="MFinCp" tabindex="18" styleClass="inputColor"
							size="2" maxlength="2" />
						&nbsp;
						<html:text property="AFinCp" tabindex="18" styleClass="inputColor"
							size="4" maxlength="4" />
						&nbsp;
						<html:errors property="FFinCp" />
						<br /> <label class="obligado">Fecha de Reconocimiento de
							Grado (dd/mm/aaaa):</label>
						<html:text property="DRecGrado" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="MRecGrado" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="ARecGrado" tabindex="18"
							styleClass="inputColor" size="4" maxlength="4" />
						&nbsp;
						<html:errors property="FRecGrado" />
						<br /> <label class="obligado">Plazo de
							autoevaluaci&oacute;n de MC:</label>
						<html:text property="NDias_auto_mc" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:errors property="NDias_auto_mc" />
						<br /> <label class="obligado">Plazo de valoraci&oacute;n
							de MC:</label>
						<html:text property="NDias_val_mc" tabindex="18"
							styleClass="inputColor colocaPValConvo" size="2" maxlength="2" />
						&nbsp;
						<html:errors property="NDias_val_mc" />
						<br /> <label class="obligado">Plazo de alegaciones de
							MC:</label>
						<html:text property="NDias_inconf_mc" tabindex="18"
							styleClass="inputColor colocaPIncConvo" size="2" maxlength="2" />
						&nbsp;
						<html:errors property="NDias_inconf_mc" />
						<br /> <label class="obligado">Plazo de
							autoevaluaci&oacute;n de CA:</label>
						<html:text property="NDias_auto_ca" tabindex="18"
							styleClass="inputColor colocaPAutConvo" size="2" maxlength="2" />
						&nbsp;
						<html:errors property="NDias_auto_ca" />
						<br /> <label class="obligado">Plazo de valoraci&oacute;n
							de CA:</label>
						<html:text property="NDias_val_ca" tabindex="18"
							styleClass="inputColor colocaPValCAConvo" size="2" maxlength="2" />
						&nbsp;
						<html:errors property="NDias_val_ca" />
						<br /> <label class="obligado">Plazo de alegaciones de
							CA:</label>
						<html:text property="NDias_inconf_ca" tabindex="18"
							styleClass="inputColor colocaPIncCAConvo" size="2" maxlength="2" />
						&nbsp;
						<html:errors property="NDias_inconf_ca" />
						<br /> <label class="obligado">Plazo de valoraci&oacute;n
							de CC:</label>
						<html:text property="NDias_val_cc" tabindex="18"
							styleClass="inputColor colocaPValCCConvo" size="2" maxlength="2" />
						&nbsp;
						<html:errors property="NDias_val_cc" />
						<br /> <label class="obligado">Plazo de respuesta de
							alegaciones de MC:</label>
						<html:text property="NDias_respinconf_mc" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:errors property="NDias_respinconf_mc" />
						<br /> <label class="obligado">Plazo de respuesta de
							alegaciones de CA:</label>
						<html:text property="NDias_respinconf_ca" tabindex="18"
							styleClass="inputColor colocaPResCAConvo" size="2" maxlength="2" />
						&nbsp;
						<html:errors property="NDias_respinconf_ca" />
						<br /> <label class="obligado">Plazo de respuesta de
							alegaciones de CC:</label>
						<html:text property="NDias_respinconf_cc" tabindex="18"
							styleClass="inputColor colocaPResCCConvo" size="2" maxlength="2" />
						&nbsp;
						<html:errors property="NDias_respinconf_cc" />
						<br /> <label class="obligado"> Convocatoria
							cerrada(S/N):</label>
						<html:text property="BCierreSo" tabindex="18"
							styleClass="inputColor colocaPResCCConvo" size="1" maxlength="1" />
						&nbsp;
						<html:errors property="BCierreSo" />
						<br /> <label class="obligado">Observaciones:</label>
						<html:text property="AObservaciones" tabindex="18"
							styleClass="inputColor" size="60" maxlength="500" />
						&nbsp;
						<html:errors property="AObservaciones" />
						<br /> <label class="obligado">Fecha de
							creaci&oacute;n(dd/mm/aaaa):</label>
						<html:text property="DCreacion" tabindex="18"
							styleClass="inputColor colocaFechaArea" size="2" maxlength="2" />
						&nbsp;
						<html:text property="MCreacion" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="ACreacion" tabindex="18"
							styleClass="inputColor" size="4" maxlength="4" />
						&nbsp;
						<html:errors property="FCreacion" />
						<br /> <label class="obligado">Fecha de
							modificaci&oacute;n(dd/mm/aaaa):</label>
						<html:text property="DModificacion" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="MModificacion" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="AModificacion" tabindex="18"
							styleClass="inputColor" size="4" maxlength="4" />
						&nbsp;
						<html:errors property="FModificacion" />
						<br />
						<div class="botonera">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:validar();"> <span> Buscar </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:limpiar();"> <span> Limpiar </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</div>
					</fieldset>
				</div>
				<logic:present name="sinDatos">
					<div id="divSinDatos">
						<p>
							<label><bean:write name="sinDatos" /></label>
						</p>
					</div>
				</logic:present>
				<table border="0" class="tablaConsultas">
					<logic:notPresent name="sinDatos">
						<logic:present name="primeraConsulta">
							<tr>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th align="center" valign="middle"><label>Plazos </label></th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
							</tr>
							<tr>
								<th>&nbsp;</th>
								<th>&nbsp;</th>

								<th align="center" valign="middle"><label>Nombre&nbsp;</label></th>
								<th align="center" valign="middle"><label>Fecha
										resol.&nbsp;</label></th>
								<th align="center" valign="middle"><label>Fecha
										publica.&nbsp;</label></th>
								<th align="center" valign="middle"><label>Fecha
										inicio MC</label></th>
								<th align="center" valign="middle"><label>Sol.&nbsp;</label></th>
								<th align="center" valign="middle"><label>Revis.&nbsp;</label></th>
								<th align="center" valign="middle"><label>Publ.&nbsp;</label></th>
								<th align="center" valign="middle"><label>Auto.
										MC&nbsp;</label></th>
								<th align="center" valign="middle"><label>Val.
										MC&nbsp;</label></th>
								<th align="center" valign="middle"><label>Inc.
										MC&nbsp;</label></th>
								<th align="center" valign="middle"><label>Auto.
										CA&nbsp;</label></th>
								<th align="center" valign="middle"><label>Val.
										CA&nbsp;</label></th>
								<th align="center" valign="middle"><label>Inc.
										CA&nbsp;</label></th>
								<th align="center" valign="middle"><label>Val.
										CC&nbsp;</label></th>
								<th align="center" valign="middle"><label>Resp.
										incon. MC&nbsp;</label></th>
								<th align="center" valign="middle"><label>Resp.
										incon. CA&nbsp;</label></th>
								<th align="center" valign="middle"><label>Resp.
										incon. CC&nbsp;</label></th>
							</tr>
						</logic:present>
						<logic:present name="paginaOCAPConvocatoriasOT"
							property="elementos">
							<logic:iterate id="elemento" name="paginaOCAPConvocatoriasOT"
								property="elementos">
								<tr>
									<td><a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CConvocatoriaId"/>');
return false;"><img
											src="imagenes/eliminar.gif" width="17" height="17" border="0"
											align="middle" title="Eliminar registro"
											alt="Eliminar registro"></a>&nbsp;<a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CConvocatoriaId"/>');return false;"></a>
									</td>
									<td><a
										href="OCAPConvocatorias.do?accion=irEditar&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>">
											<img src="imagenes/editar.gif" width="17" height="17"
											border="0" align="middle" title="Editar registro"
											alt="Editar registro">
									</a></td>

									<td align="right"><a
										href="OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>"
										style="text-decoration: none"> <bean:write name="elemento"
												property="DNombre" />
									</a></td>
									<td align="center"><a
										href="OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>"
										style="text-decoration: none"> <bean:write name="elemento"
												property="FResolucion" format="dd/MM/yyyy" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>"
										style="text-decoration: none"> <bean:write name="elemento"
												property="FPublicacion" format="dd/MM/yyyy" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>"
										style="text-decoration: none"> <bean:write name="elemento"
												property="FInicioMC" format="dd/MM/yyyy" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>"
										style="text-decoration: none"> <bean:write name="elemento"
												property="NDiasRegsolicitud" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>"
										style="text-decoration: none"> <bean:write name="elemento"
												property="NDiasRevsolicitud" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>"
										style="text-decoration: none"> <bean:write name="elemento"
												property="NDiasPublistado" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>"
										style="text-decoration: none"> <bean:write name="elemento"
												property="NDiasAutoMc" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>"
										style="text-decoration: none"> <bean:write name="elemento"
												property="NDiasValMc" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>"
										style="text-decoration: none"> <bean:write name="elemento"
												property="NDiasInconfMc" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>"
										style="text-decoration: none"> <bean:write name="elemento"
												property="NDiasAutoCa" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>"
										style="text-decoration: none"> <bean:write name="elemento"
												property="NDiasValCa" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>"
										style="text-decoration: none"> <bean:write name="elemento"
												property="NDiasInconfCa" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>"
										style="text-decoration: none"> <bean:write name="elemento"
												property="NDiasValCc" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>"
										style="text-decoration: none"> <bean:write name="elemento"
												property="NDiasRespinconfMc" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>"
										style="text-decoration: none"> <bean:write name="elemento"
												property="NDiasRespinconfCa" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPConvocatorias.do?accion=detalle&cConvocatoriaIdS=<bean:write name="elemento" property="CConvocatoriaId"/>"
										style="text-decoration: none"> <bean:write name="elemento"
												property="NDiasRespinconfCc" />&nbsp;&nbsp;
									</a></td>
							</logic:iterate>
							<tr>
								<td colspan="19">

									<table class="paginacionConsultas">
										<tr>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPConvocatoriasOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Primera"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPConvocatoriasOT" property="URLPrimeraPagina"/>"' />
												</logic:equal></td>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPConvocatoriasOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Anterior"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPConvocatoriasOT" property="URLPaginaAnterior"/>"' />
												</logic:equal></td>
											<td class="textoTituloGris parteCen">Registro: [<bean:write
													name="paginaOCAPConvocatoriasOT" property="registroActual" />/<bean:write
													name="paginaOCAPConvocatoriasOT" property="NRegistros" />]&nbsp;&nbsp;
												P&aacute;gina: [<bean:write name="paginaOCAPConvocatoriasOT"
													property="paginaActual" />/<bean:write
													name="paginaOCAPConvocatoriasOT" property="NPaginas" />]
											</td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPConvocatoriasOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Siguiente"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPConvocatoriasOT" property="URLPaginaSiguiente"/>"' />
												</logic:equal></td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPConvocatoriasOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="&Uacute;ltima"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPConvocatoriasOT" property="URLUltimaPagina"/>"' />
												</logic:equal></td>
										</tr>
									</table>

								</td>
							</tr>

						</logic:present>
					</logic:notPresent>
				</table>
			</logic:notPresent>
		</div>
		<input type="hidden" name="cConvocatoriaIdS" value="" />

	</div>
	<!-- /Fin de ocultarCampo -->

</html:form>