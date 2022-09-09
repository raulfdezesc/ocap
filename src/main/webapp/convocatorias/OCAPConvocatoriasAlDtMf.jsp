<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<script type="text/javascript"  src="javascript/validate.js" ></script>
<script type="text/javascript"	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script type="text/javascript"	src="<html:rewrite page='/javascript/formularios.js'/>"></script>
<script type="text/javascript" 	src="<html:rewrite page='/javascript/fechas.js'/>"></script>
<script type="text/javascript" 	src="<html:rewrite page='/javascript/date.js'/>"></script>
<script>

function calculoFechaFinSolicitud(){ 
  var dias = document.OCAPConvocatoriasForm.NDias_regsolicitud.value;
  var fecha = document.OCAPConvocatoriasForm.DInicioSolicitud.value + '/' + document.OCAPConvocatoriasForm.MInicioSolicitud.value + '/' + document.OCAPConvocatoriasForm.AInicioSolicitud.value;
 
//   if(dias == 0){
// 	  alert("\u25b6 Plazo de solicitud debe ser mayor que 0");
// 	  return;
//   }else{	  
	  if (esFechaCorrecta(fecha)){
		  if(dias == 0){
			  alert("\u25b6 Plazo de solicitud debe ser mayor que 0");
		  }else{
			  var fechaFin =  annadirDiaFecha(fecha,(dias-1));
			  document.OCAPConvocatoriasForm.DFinSolicitud.value = fechaFin.format('dd');
			  document.OCAPConvocatoriasForm.MFinSolicitud.value = fechaFin.format('MM');
			  document.OCAPConvocatoriasForm.AFinSolicitud.value = fechaFin.format('yyyy');
		  }
		  
	  }else{
		  alert("\u25b6 Antes de rellenar Plazo de solicitud debe rellenar la Fecha Inicio de solicitudes ");
		  document.OCAPConvocatoriasForm.NDias_regsolicitud.value = "0";		  
	  }
//   }
	
}

function calculoFechaFinMC(){
	var dias = document.OCAPConvocatoriasForm.NDias_auto_mc.value;
	
	if ((document.OCAPConvocatoriasForm.DInicioMC.value != "" ) && (document.OCAPConvocatoriasForm.MInicioMC.value != "" ) && (document.OCAPConvocatoriasForm.AInicioMC.value != "")){
	
		var fecha = document.OCAPConvocatoriasForm.DInicioMC.value + '/' + document.OCAPConvocatoriasForm.MInicioMC.value + '/' + document.OCAPConvocatoriasForm.AInicioMC.value;			 		
		//  if(dias == 0){
		//	  alert("\u25b6 Plazo de autoevaluaci\u00F3n de MC debe ser mayor que 0");
		//	  return;
		//  }else{	
			  if (esFechaCorrecta(fecha)){
				  if(dias == 0){
					alert("\u25b6 Plazo de autoevaluaci\u00F3n de MC debe ser mayor que 0");					
				  }else{
					var fechaFin =  annadirDiaFecha(fecha,dias-1);		
					document.OCAPConvocatoriasForm.DFinMC.value = fechaFin.format('dd');
					document.OCAPConvocatoriasForm.MFinMC.value = fechaFin.format('MM');
					document.OCAPConvocatoriasForm.AFinMC.value = fechaFin.format('yyyy');
				  }
				  
			  }else{
				 alert("\u25b6 Antes de rellenar Plazo de autoevaluaci\u00F3n de MC debe rellenar la Fecha Inicio de MC ");
				  document.OCAPConvocatoriasForm.NDias_auto_mc.value = "0";				  
			  }
		//  }		
  	}
}

function calculoFechaFinValoracionMC(){
	
	var dias = document.OCAPConvocatoriasForm.NDias_val_mc.value;
	
	if ((document.OCAPConvocatoriasForm.DInicioValoracionMC.value != "" ) && (document.OCAPConvocatoriasForm.MInicioValoracionMC.value != "" ) && (document.OCAPConvocatoriasForm.AInicioValoracionMC.value != "")){
		var fecha = document.OCAPConvocatoriasForm.DInicioValoracionMC.value + '/' + document.OCAPConvocatoriasForm.MInicioValoracionMC.value + '/' + document.OCAPConvocatoriasForm.AInicioValoracionMC.value;
			 
		  		
			  if (esFechaCorrecta(fecha)){
				  if(dias == 0){
					  alert("\u25b6 Plazo de valoraci\u00F3n de MC debe ser mayor que 0");					  
				  }else{
					  var fechaFin =  annadirDiaFecha(fecha,dias-1);		
					  document.OCAPConvocatoriasForm.DFinValoracionMC.value = fechaFin.format('dd');
					  document.OCAPConvocatoriasForm.MFinValoracionMC.value = fechaFin.format('MM');
					  document.OCAPConvocatoriasForm.AFinValoracionMC.value = fechaFin.format('yyyy');
				  }				 
			  }else{
				  alert("\u25b6 Antes de rellenar Plazo de valoraci\u00F3n de MC debe rellenar la Fecha Inicio de valoraci\u00F3n de MC ");
				  document.OCAPConvocatoriasForm.NDias_val_mc.value = "0";				  
			  }
		  //}
	}
		
}

function calculoFechaFinValoracionCC(){
	var dias = document.OCAPConvocatoriasForm.NDias_val_cc.value;
	  
	
	if ((document.OCAPConvocatoriasForm.DInicioValoracionCC.value != "" ) && (document.OCAPConvocatoriasForm.MInicioValoracionCC.value != "" ) && (document.OCAPConvocatoriasForm.AInicioValoracionCC.value != "")){

		var fecha = document.OCAPConvocatoriasForm.DInicioValoracionCC.value + '/' + document.OCAPConvocatoriasForm.MInicioValoracionCC.value + '/' + document.OCAPConvocatoriasForm.AInicioValoracionCC.value;
		
		 
				
			  if (esFechaCorrecta(fecha)){
				  if(dias == 0){
					  alert("\u25b6 Plazo de valoraci\u00F3n de CC debe ser mayor que 0");					  
				  }else{
					  var fechaFin =  annadirDiaFecha(fecha,dias-1);		
					  document.OCAPConvocatoriasForm.DFinValoracionCC.value = fechaFin.format('dd');
					  document.OCAPConvocatoriasForm.MFinValoracionCC.value = fechaFin.format('MM');
					  document.OCAPConvocatoriasForm.AFinValoracionCC.value = fechaFin.format('yyyy');
				  }
				  
			  }else{
				  alert("\u25b6 Antes de rellenar Plazo de valoraci\u00F3n de CC debe rellenar la Fecha Inicio de valoraci\u00F3n de CC ");
				  document.OCAPConvocatoriasForm.NDias_val_cc.value = "0";				  
			  }
		//  }
	}
}

function calculoFechaFinCA(){
	var dias = document.OCAPConvocatoriasForm.NDias_auto_ca.value;
	
	if ((document.OCAPConvocatoriasForm.DInicioCA.value != "" ) && (document.OCAPConvocatoriasForm.MInicioCA.value != "" ) && (document.OCAPConvocatoriasForm.AInicioCA.value != "")){
		var fecha = document.OCAPConvocatoriasForm.DInicioCA.value + '/' + document.OCAPConvocatoriasForm.MInicioCA.value + '/' + document.OCAPConvocatoriasForm.AInicioCA.value;
			 
		 	
			  if (esFechaCorrecta(fecha)){
				  if(dias == 0){
					  alert("\u25b6 Plazo de autoevaluci\u00F3n de CA debe ser mayor que 0");					  
				  }else{
					  var fechaFin =  annadirDiaFecha(fecha,dias-1);		 
					  document.OCAPConvocatoriasForm.DFinCA.value = fechaFin.format('dd');
					  document.OCAPConvocatoriasForm.MFinCA.value = fechaFin.format('MM');
					  document.OCAPConvocatoriasForm.AFinCA.value = fechaFin.format('yyyy');
				  }
				  
			  }else{
				  alert("\u25b6 Antes de rellenar Plazo de autoevaluci\u00F3n de CA debe rellenar la Fecha Inicio de CA ");
				  document.OCAPConvocatoriasForm.NDias_auto_ca.value = "0";				  
			  }	
		  //}
	}
}


function calculoFechaFinValoracionCA(){
	var dias = document.OCAPConvocatoriasForm.NDias_val_ca.value;
	  
	if ((document.OCAPConvocatoriasForm.DInicioValCA.value != "" ) && (document.OCAPConvocatoriasForm.MInicioValCA.value != "" ) && (document.OCAPConvocatoriasForm.AInicioValCA.value != "")){	
		var fecha = document.OCAPConvocatoriasForm.DInicioValCA.value + '/' + document.OCAPConvocatoriasForm.MInicioValCA.value + '/' + document.OCAPConvocatoriasForm.AInicioValCA.value;
		 		  		  	 
		  if (esFechaCorrecta(fecha)){
			  if(dias == 0){
				  alert("\u25b6 Plazo de valoraci\u00F3n de CA debe ser mayor que 0");			
			  }else{
				  var fechaFin =  annadirDiaFecha(fecha,dias-1);		 
				  document.OCAPConvocatoriasForm.DFinValCA.value = fechaFin.format('dd');
				  document.OCAPConvocatoriasForm.MFinValCA.value = fechaFin.format('MM');
				  document.OCAPConvocatoriasForm.AFinValCA.value = fechaFin.format('yyyy');
			  }
			  
		  }else{
			  alert("\u25b6 Antes de rellenar Plazo de valoraci\u00F3n de CA debe rellenar la Fecha Inicio de valoraci\u00F3n de CA ");
			  document.OCAPConvocatoriasForm.NDias_val_ca.value = 0;			  
		  }
	//  }
	}
		
}


function calculoFechaInicioSolicitudes()
{
 
   // Calculo Fecha Inicio solicitudes
   
   if ((document.OCAPConvocatoriasForm.DPublicacion.value != "" ) && (document.OCAPConvocatoriasForm.MPublicacion.value != "" ) && (document.OCAPConvocatoriasForm.APublicacion.value != "")){
      var fecha = document.OCAPConvocatoriasForm.DPublicacion.value + '/' + document.OCAPConvocatoriasForm.MPublicacion.value + '/' + document.OCAPConvocatoriasForm.APublicacion.value;      
     
      if (esFechaCorrecta(fecha)){
         
         var fechaInicioSolicitudes = annadirDiaFecha(fecha,1);
         document.OCAPConvocatoriasForm.DInicioSolicitud.value = fechaInicioSolicitudes.format('dd');
         document.OCAPConvocatoriasForm.MInicioSolicitud.value = fechaInicioSolicitudes.format('MM');
         document.OCAPConvocatoriasForm.AInicioSolicitud.value = fechaInicioSolicitudes.format('yyyy');
         calculoFechaFinSolicitud();
         //calculoPlazoSolicitudes();
      }else{      
         document.OCAPConvocatoriasForm.DInicioSolicitud.value ="";
         document.OCAPConvocatoriasForm.MInicioSolicitud.value ="";
         document.OCAPConvocatoriasForm.AInicioSolicitud.value ="";
      }
   } 
}

function calculoPlazoSolicitudes()

{

  if ((document.OCAPConvocatoriasForm.DInicioSolicitud.value != "" ) && (document.OCAPConvocatoriasForm.MInicioSolicitud.value != "" ) && (document.OCAPConvocatoriasForm.AInicioSolicitud.value != "")
  && (document.OCAPConvocatoriasForm.DFinSolicitud.value != "" ) && (document.OCAPConvocatoriasForm.MFinSolicitud.value != "" ) && (document.OCAPConvocatoriasForm.AFinSolicitud.value != ""))
  {
      var fechaInicio = document.OCAPConvocatoriasForm.DInicioSolicitud.value + '/' + document.OCAPConvocatoriasForm.MInicioSolicitud.value + '/' + document.OCAPConvocatoriasForm.AInicioSolicitud.value;      
      var fechaFin = document.OCAPConvocatoriasForm.DFinSolicitud.value + '/' + document.OCAPConvocatoriasForm.MFinSolicitud.value + '/' + document.OCAPConvocatoriasForm.AFinSolicitud.value;
     
      if (esFechaCorrecta(fechaInicio) && esFechaCorrecta(fechaFin))
      {
           var plazo = diasEntreFechas(fechaInicio,fechaFin);
           if  (isNaN(plazo) || plazo==null || plazo <=0) {plazo="";} 

           document.OCAPConvocatoriasForm.NDias_regsolicitud.value= plazo;           
      }
      else
      {
       document.OCAPConvocatoriasForm.NDias_regsolicitud.value= "0";
      }
 }
}


// function calculoPlazoAutoEvaluacionMC()

// {

//   if ((document.OCAPConvocatoriasForm.DInicioMC.value != "" ) && (document.OCAPConvocatoriasForm.MInicioMC.value != "" ) && (document.OCAPConvocatoriasForm.AInicioMC.value != "")
//   && (document.OCAPConvocatoriasForm.DFinMC.value != "" ) && (document.OCAPConvocatoriasForm.MFinMC.value != "" ) && (document.OCAPConvocatoriasForm.AFinMC.value != ""))
//   {
//       var fechaInicio = document.OCAPConvocatoriasForm.DInicioMC.value + '/' + document.OCAPConvocatoriasForm.MInicioMC.value + '/' + document.OCAPConvocatoriasForm.AInicioMC.value;      
//       var fechaFin = document.OCAPConvocatoriasForm.DFinMC.value + '/' + document.OCAPConvocatoriasForm.MFinMC.value + '/' + document.OCAPConvocatoriasForm.AFinMC.value;
     
//       if (esFechaCorrecta(fechaInicio) && esFechaCorrecta(fechaFin))
//       {
//            var plazo = diasEntreFechas(fechaInicio,fechaFin);
//            if  (isNaN(plazo) || plazo==null || plazo <=0) {plazo="";}
//            document.OCAPConvocatoriasForm.NDias_auto_mc.value= plazo;           
//       }
//       else
//       {
//       document.OCAPConvocatoriasForm.NDias_auto_mc.value= "";  
//       }
//      }
// }

// function calculoPlazoValoracionMC()

// {

//   if ((document.OCAPConvocatoriasForm.DInicioValoracionMC.value != "" ) && (document.OCAPConvocatoriasForm.MInicioValoracionMC.value != "" ) && (document.OCAPConvocatoriasForm.AInicioValoracionMC.value != "")
//   && (document.OCAPConvocatoriasForm.DFinValoracionMC.value != "" ) && (document.OCAPConvocatoriasForm.MFinValoracionMC.value != "" ) && (document.OCAPConvocatoriasForm.AFinValoracionMC.value != ""))
//   {
//       var fechaInicio = document.OCAPConvocatoriasForm.DInicioValoracionMC.value + '/' + document.OCAPConvocatoriasForm.MInicioValoracionMC.value + '/' + document.OCAPConvocatoriasForm.AInicioValoracionMC.value;      
//       var fechaFin = document.OCAPConvocatoriasForm.DFinValoracionMC.value + '/' + document.OCAPConvocatoriasForm.MFinValoracionMC.value + '/' + document.OCAPConvocatoriasForm.AFinValoracionMC.value;
     
//       if (esFechaCorrecta(fechaInicio) && esFechaCorrecta(fechaFin))
//       {
//            var plazo = diasEntreFechas(fechaInicio,fechaFin);
//            if  (isNaN(plazo) || plazo==null || plazo <=0) {plazo="";}
//            document.OCAPConvocatoriasForm.NDias_val_mc.value= plazo;           
//       }
//       else
//       {
//       document.OCAPConvocatoriasForm.NDias_val_mc.value= "";  
//       }

// }
// }


// function calculoPlazoValoracionCC()

// {

//   if ((document.OCAPConvocatoriasForm.DInicioValoracionCC.value != "" ) && (document.OCAPConvocatoriasForm.MInicioValoracionCC.value != "" ) && (document.OCAPConvocatoriasForm.AInicioValoracionCC.value != "")
//   && (document.OCAPConvocatoriasForm.DFinValoracionCC.value != "" ) && (document.OCAPConvocatoriasForm.MFinValoracionCC.value != "" ) && (document.OCAPConvocatoriasForm.AFinValoracionCC.value != ""))
//   {
//       var fechaInicio = document.OCAPConvocatoriasForm.DInicioValoracionCC.value + '/' + document.OCAPConvocatoriasForm.MInicioValoracionCC.value + '/' + document.OCAPConvocatoriasForm.AInicioValoracionCC.value;      
//       var fechaFin = document.OCAPConvocatoriasForm.DFinValoracionCC.value + '/' + document.OCAPConvocatoriasForm.MFinValoracionCC.value + '/' + document.OCAPConvocatoriasForm.AFinValoracionCC.value;
     
//       if (esFechaCorrecta(fechaInicio) && esFechaCorrecta(fechaFin))
//       {
//            var plazo = diasEntreFechas(fechaInicio,fechaFin);
//            if  (isNaN(plazo) || plazo==null || plazo <=0) {plazo="";}
//            document.OCAPConvocatoriasForm.NDias_val_cc.value= plazo;           
//       }
//       else
//       {
//       document.OCAPConvocatoriasForm.NDias_val_cc.value= "";  
//       }
     
// }
// }


// function calculoPlazoAutoEvaluacionCA()

// {

//   if ((document.OCAPConvocatoriasForm.DInicioCA.value != "" ) && (document.OCAPConvocatoriasForm.MInicioCA.value != "" ) && (document.OCAPConvocatoriasForm.AInicioCA.value != "")
//   && (document.OCAPConvocatoriasForm.DFinCA.value != "" ) && (document.OCAPConvocatoriasForm.MFinCA.value != "" ) && (document.OCAPConvocatoriasForm.AFinCA.value != ""))
//   {
//       var fechaInicio = document.OCAPConvocatoriasForm.DInicioCA.value + '/' + document.OCAPConvocatoriasForm.MInicioCA.value + '/' + document.OCAPConvocatoriasForm.AInicioCA.value;      
//       var fechaFin = document.OCAPConvocatoriasForm.DFinCA.value + '/' + document.OCAPConvocatoriasForm.MFinCA.value + '/' + document.OCAPConvocatoriasForm.AFinCA.value;
     
//       if (esFechaCorrecta(fechaInicio) && esFechaCorrecta(fechaFin))
//       {
//            var plazo = diasEntreFechas(fechaInicio,fechaFin);
//            if  (isNaN(plazo) || plazo==null || plazo <=0) {plazo="";}
//            document.OCAPConvocatoriasForm.NDias_auto_ca.value= plazo;           
//       }
//       else
//       {
//       document.OCAPConvocatoriasForm.NDias_auto_ca.value= "";  
//       }
     
// }
// }


// function calculoPlazoValoracionCA()

// {

//   if ((document.OCAPConvocatoriasForm.DInicioValCA.value != "" ) && (document.OCAPConvocatoriasForm.MInicioValCA.value != "" ) && (document.OCAPConvocatoriasForm.AInicioValCA.value != "")
//   && (document.OCAPConvocatoriasForm.DFinValCA.value != "" ) && (document.OCAPConvocatoriasForm.MFinValCA.value != "" ) && (document.OCAPConvocatoriasForm.AFinValCA.value != ""))
//   {
//       var fechaInicio = document.OCAPConvocatoriasForm.DInicioValCA.value + '/' + document.OCAPConvocatoriasForm.MInicioValCA.value + '/' + document.OCAPConvocatoriasForm.AInicioValCA.value;      
//       var fechaFin = document.OCAPConvocatoriasForm.DFinValCA.value + '/' + document.OCAPConvocatoriasForm.MFinValCA.value + '/' + document.OCAPConvocatoriasForm.AFinValCA.value;
     
//       if (esFechaCorrecta(fechaInicio) && esFechaCorrecta(fechaFin))
//       {
//            var plazo = diasEntreFechas(fechaInicio,fechaFin);
//            if  (isNaN(plazo) || plazo==null || plazo <=0) {plazo="";}
//            document.OCAPConvocatoriasForm.NDias_val_ca.value= plazo;           
//       }
//       else
//       {
//       document.OCAPConvocatoriasForm.NDias_val_ca.value= "";  
//       }
     
// }
// }



function validar(tipoAccion){
   
   
   var validacion = 0;   
   var mensajeError="Rellene correctamente los siguientes campos:" + "\n\n";
   
    
   if ( (document.OCAPConvocatoriasForm.DNombre.value == "") || (document.OCAPConvocatoriasForm.DNombre.value != "" && !LetrasYNumeros(document.OCAPConvocatoriasForm.DNombre))){
         
         mensajeError = mensajeError + "\u25b6 Convocatoria" + "\n";
         validacion=1;   
   }
   
   
    if (document.OCAPConvocatoriasForm.DAnioConvocatoria.value == ""){
         
         mensajeError = mensajeError + "\u25b6 A\u00F1o de Convocatoria" + "\n";
         validacion=1;   
   }
   
   if ( (document.OCAPConvocatoriasForm.DNombreCorto.value == "") || (document.OCAPConvocatoriasForm.DNombreCorto.value != "" && !LetrasYNumeros(document.OCAPConvocatoriasForm.DNombreCorto))){
         
         mensajeError = mensajeError + "\u25b6 Nombre Corto" + "\n";
         validacion=1;   
   }
   
   if (document.OCAPConvocatoriasForm.CGrado_id.value == "" && tipoAccion == '<%=Constantes.INSERTAR%>'){
         
         mensajeError = mensajeError + "\u25b6 Grado de la convocatoria" + "\n";
         validacion=1;   
   }   
   
 
	// Fecha resolucion  
      var fecha = document.OCAPConvocatoriasForm.DResolucion.value + '/' + document.OCAPConvocatoriasForm.MResolucion.value + '/' + document.OCAPConvocatoriasForm.AResolucion.value;      
     
      if (!esFechaCorrecta(fecha)){
         mensajeError = mensajeError + "\u25b6 Fecha de Resoluci\u00f3n" +"\n";
         validacion=1;   
      }
    
   // Fecha de publicacion
      fecha = document.OCAPConvocatoriasForm.DPublicacion.value + '/' + document.OCAPConvocatoriasForm.MPublicacion.value + '/' + document.OCAPConvocatoriasForm.APublicacion.value;           
      if (!esFechaCorrecta(fecha)){
         mensajeError = mensajeError + "\u25b6 Fecha de Publicaci\u00f3n" + "\n";
         validacion=1;   
      }

   // Fecha Ini Solicitudes < Fecha Fin Solicitudes
//       var fechaIniSol = document.OCAPConvocatoriasForm.DInicioSolicitud.value + '/' + document.OCAPConvocatoriasForm.MInicioSolicitud.value + '/' + document.OCAPConvocatoriasForm.AInicioSolicitud.value;
//       var fechaFinSol = document.OCAPConvocatoriasForm.DFinSolicitud.value + '/' + document.OCAPConvocatoriasForm.MFinSolicitud.value + '/' + document.OCAPConvocatoriasForm.AFinSolicitud.value;
      
//       var dif = diferenciaFechas(fechaIniSol,fechaFinSol);
//       if( dif >= 0 ) {
//     	  mensajeError = mensajeError + "\u25b6 La Fecha de Fin de Solicitudes debe ser posterior a la de Inicio" + "\n";
//           validacion=1;   
//       } 
   
   // Fecha Fin Solicitudes
       
      fecha = document.OCAPConvocatoriasForm.DFinSolicitud.value + '/' + document.OCAPConvocatoriasForm.MFinSolicitud.value + '/' + document.OCAPConvocatoriasForm.AFinSolicitud.value;      
     
      if (!esFechaCorrecta(fecha)){
         mensajeError = mensajeError + "\u25b6 Fecha de Fin de Solicitudes" + "\n";
         validacion=1;   
      }
    
       //Fecha de Fin PGP
       if(document.OCAPConvocatoriasForm.DFinPgp.value != '' || document.OCAPConvocatoriasForm.MFinPgp.value != '' || document.OCAPConvocatoriasForm.AFinPgp.value != ''  ){
       		  fecha = document.OCAPConvocatoriasForm.DFinPgp.value + '/' + document.OCAPConvocatoriasForm.MFinPgp.value + '/' + document.OCAPConvocatoriasForm.AFinPgp.value;      
     
		      if (!esFechaCorrecta(fecha)){
		         mensajeError = mensajeError + "\u25b6 Fecha Fin gesti\u00f3n PGP" + "\n";
		         validacion=1;   
		      }
      }
    
    // Fecha Publicacion Listado Provisional e inicio alegaciones
   
       fecha = document.OCAPConvocatoriasForm.DInicioAlega.value + '/' + document.OCAPConvocatoriasForm.MInicioAlega.value + '/' + document.OCAPConvocatoriasForm.AInicioAlega.value;      
     
      if (!esFechaCorrecta(fecha)){
          mensajeError = mensajeError + "\u25b6 Fecha de Publicaci\u00F3n de Listado Provisional e Inicio de Alegaciones" + "\n";
         validacion=1;   
      }
    

   //Fecha de Inicio de MC
     fecha = document.OCAPConvocatoriasForm.DInicioMC.value + '/' + document.OCAPConvocatoriasForm.MInicioMC.value + '/' + document.OCAPConvocatoriasForm.AInicioMC.value;      
     
      if (!esFechaCorrecta(fecha)){
         mensajeError = mensajeError + "\u25b6 Fecha de Inicio de MC" + "\n";
         validacion=1;   
      }
    
   
   // Fecha Fin MC
      fecha = document.OCAPConvocatoriasForm.DFinMC.value + '/' + document.OCAPConvocatoriasForm.MFinMC.value + '/' + document.OCAPConvocatoriasForm.AFinMC.value;      
     
      if (!esFechaCorrecta(fecha)){
          mensajeError = mensajeError + "\u25b6 Fecha de Fin de MC"+ "\n";
         validacion=1;   
      }
      
   // Fecha Ini MC < Fecha Fin MC
//       var fechaIniMC = document.OCAPConvocatoriasForm.DInicioMC.value + '/' + document.OCAPConvocatoriasForm.MInicioMC.value + '/' + document.OCAPConvocatoriasForm.AInicioMC.value;
//       var fechaFinMC = document.OCAPConvocatoriasForm.DFinMC.value + '/' + document.OCAPConvocatoriasForm.MFinMC.value + '/' + document.OCAPConvocatoriasForm.AFinMC.value;
      
//       var dif = diferenciaFechas(fechaIniMC,fechaFinMC);
//       if( dif >= 0 ) {
//     	  mensajeError = mensajeError + "\u25b6 La Fecha de Fin de MC debe ser posterior a la de Inicio" + "\n";
//           validacion=1;   
//       } 
      
   
   // Fecha inicio valoracion MC   
     fecha = document.OCAPConvocatoriasForm.DInicioValoracionMC.value + '/' + document.OCAPConvocatoriasForm.MInicioValoracionMC.value + '/' + document.OCAPConvocatoriasForm.AInicioValoracionMC.value;      
     
      if (!esFechaCorrecta(fecha)){
         mensajeError = mensajeError + "\u25b6 Fecha de Inicio de valoraci\u00F3n de MC"+ "\n";
         validacion=1;   
      }
    
   
   // Fecha Fin  valoracion MC
    
     fecha = document.OCAPConvocatoriasForm.DFinValoracionMC.value + '/' + document.OCAPConvocatoriasForm.MFinValoracionMC.value + '/' + document.OCAPConvocatoriasForm.AFinValoracionMC.value;      
     
      if (!esFechaCorrecta(fecha)){
         mensajeError = mensajeError + "\u25b6 Fecha de Fin de valoraci\u00F3n de MC"+ "\n";
         validacion=1;   
    
   }
      
   // Fecha Ini Valoracion MC < Fecha Fin Valoracion MC
//       var fechaIniValMC = document.OCAPConvocatoriasForm.DInicioValoracionMC.value + '/' + document.OCAPConvocatoriasForm.MInicioValoracionMC.value + '/' + document.OCAPConvocatoriasForm.AInicioValoracionMC.value;
//       var fechaFinValMC = document.OCAPConvocatoriasForm.DFinValoracionMC.value + '/' + document.OCAPConvocatoriasForm.MFinValoracionMC.value + '/' + document.OCAPConvocatoriasForm.AFinValoracionMC.value;
      
//       var dif = diferenciaFechas(fechaIniValMC,fechaFinValMC);
//       if( dif >= 0 ) {
//     	  mensajeError = mensajeError + "\u25b6 La Fecha de Fin de valoraci\u00F3n MC debe ser posterior a la de Inicio" + "\n";
//           validacion=1;   
//       } 
   
   // Fecha inicio valoracion CC
   
  
     fecha = document.OCAPConvocatoriasForm.DInicioValoracionCC.value + '/' + document.OCAPConvocatoriasForm.MInicioValoracionCC.value + '/' + document.OCAPConvocatoriasForm.AInicioValoracionCC.value;      
     
      if (!esFechaCorrecta(fecha)){
         mensajeError = mensajeError + "\u25b6 Fecha de Inicio de valoraci\u00F3n de CC"+ "\n";
         validacion=1;   
      }
    
   
    // Fecha Fin  valoracion CC
   
     fecha = document.OCAPConvocatoriasForm.DFinValoracionCC.value + '/' + document.OCAPConvocatoriasForm.MFinValoracionCC.value + '/' + document.OCAPConvocatoriasForm.AFinValoracionCC.value;      
     
      if (!esFechaCorrecta(fecha)){
         mensajeError = mensajeError + "\u25b6 Fecha de Fin de valoraci\u00F3n de CC"+ "\n";
         validacion=1;   
      }
      
    // Fecha Ini Valoracion CC < Fecha Fin Valoracion CC
//       var fechaIniValCC = document.OCAPConvocatoriasForm.DInicioValoracionCC.value + '/' + document.OCAPConvocatoriasForm.MInicioValoracionCC.value + '/' + document.OCAPConvocatoriasForm.AInicioValoracionCC.value;
//       var fechaFinValCC = document.OCAPConvocatoriasForm.DFinValoracionCC.value + '/' + document.OCAPConvocatoriasForm.MFinValoracionCC.value + '/' + document.OCAPConvocatoriasForm.AFinValoracionCC.value;
      
//       var dif = diferenciaFechas(fechaIniValCC,fechaFinValCC);
//       if( dif >= 0 ) {
//     	  mensajeError = mensajeError + "\u25b6 La Fecha de Fin de valoraci\u00F3n CC debe ser posterior a la de Inicio" + "\n";
//           validacion=1;   
//       }    
   
   // Fecha Inicio CA
   
     fecha = document.OCAPConvocatoriasForm.DInicioCA.value + '/' + document.OCAPConvocatoriasForm.MInicioCA.value + '/' + document.OCAPConvocatoriasForm.AInicioCA.value;      
     
      if (!esFechaCorrecta(fecha)){
         mensajeError = mensajeError + "\u25b6 Fecha de Inicio de CA"+ "\n";
         validacion=1;   
      }
    
   
   // Fecha Fin CA
    
     fecha = document.OCAPConvocatoriasForm.DFinCA.value + '/' + document.OCAPConvocatoriasForm.MFinCA.value + '/' + document.OCAPConvocatoriasForm.AFinCA.value;      
     
      if (!esFechaCorrecta(fecha)){
         mensajeError = mensajeError + "\u25b6 Fecha de Fin de CA"+ "\n";
         validacion=1;   
      }
   
   // Fecha Ini CA < Fecha Fin CA
//       var fechaIniCA = document.OCAPConvocatoriasForm.DInicioCA.value + '/' + document.OCAPConvocatoriasForm.MInicioCA.value + '/' + document.OCAPConvocatoriasForm.AInicioCA.value;
//       var fechaFinCA = document.OCAPConvocatoriasForm.DFinCA.value + '/' + document.OCAPConvocatoriasForm.MFinCA.value + '/' + document.OCAPConvocatoriasForm.AFinCA.value;
      
//       var dif = diferenciaFechas(fechaIniCA,fechaFinCA);
//       if( dif >= 0 ) {
//     	  mensajeError = mensajeError + "\u25b6 La Fecha de Fin de CA debe ser posterior a la de Inicio" + "\n";
//           validacion=1;   
//       }  
   
    // Fecha inicio valoracion CA
  
   
     fecha = document.OCAPConvocatoriasForm.DInicioValCA.value + '/' + document.OCAPConvocatoriasForm.MInicioValCA.value + '/' + document.OCAPConvocatoriasForm.AInicioValCA.value;      
     
      if (!esFechaCorrecta(fecha)){
         mensajeError = mensajeError + "\u25b6 Fecha de Inicio de valoraci\u00F3n de CA"+ "\n";
         validacion=1;   
      }
      
    // Fecha Fin  valoracion CA
    
     fecha = document.OCAPConvocatoriasForm.DFinValCA.value + '/' + document.OCAPConvocatoriasForm.MFinValCA.value + '/' + document.OCAPConvocatoriasForm.AFinValCA.value;      
     
      if (!esFechaCorrecta(fecha)){
         mensajeError = mensajeError + "\u25b6 Fecha de Fin de valoraci\u00F3n de CA"+ "\n";
         validacion=1;   
      }
     
   // Fecha Ini Val CA < Fecha Fin Val CA
//       var fechaIniValCA = document.OCAPConvocatoriasForm.DInicioValCA.value + '/' + document.OCAPConvocatoriasForm.MInicioValCA.value + '/' + document.OCAPConvocatoriasForm.AInicioValCA.value;
//       var fechaFinValCA = document.OCAPConvocatoriasForm.DFinValCA.value + '/' + document.OCAPConvocatoriasForm.MFinValCA.value + '/' + document.OCAPConvocatoriasForm.AFinValCA.value;
      
//       var dif = diferenciaFechas(fechaIniValCA,fechaFinValCA);
//       if( dif >= 0 ) {
//     	  mensajeError = mensajeError + "\u25b6 La Fecha de Fin de valoraci\u00F3n CA debe ser posterior a la de Inicio" + "\n";
//           validacion=1;   
//       }  
      
   //Fecha de listado provisional de MC
 
     fecha = document.OCAPConvocatoriasForm.DInicioCC.value + '/' + document.OCAPConvocatoriasForm.MInicioCC.value + '/' + document.OCAPConvocatoriasForm.AInicioCC.value;      
     
      if (!esFechaCorrecta(fecha)){
         mensajeError = mensajeError + "\u25b6 Fecha de listado provisional de MC"+ "\n";
         validacion=1;   
      }
 
 
    //echa de listado definitivo de MC
     fecha = document.OCAPConvocatoriasForm.DFinCC.value + '/' + document.OCAPConvocatoriasForm.MFinCC.value + '/' + document.OCAPConvocatoriasForm.AFinCC.value;      
     
      if (!esFechaCorrecta(fecha)){
         mensajeError = mensajeError + "\u25b6 Fecha de listado definitivo de MC"+ "\n";
         validacion=1;   
      }
    
      
   //Fecha de Inicio de CA
     fecha = document.OCAPConvocatoriasForm.DInicioCA.value + '/' + document.OCAPConvocatoriasForm.MInicioCA.value + '/' + document.OCAPConvocatoriasForm.AInicioCA.value;      
     
      if (!esFechaCorrecta(fecha)){
         mensajeError = mensajeError + "\u25b6 Fecha de Inicio de CA"+ "\n";
         validacion=1;
      }
    

  //Fecha de Fin de Convocatoria
     fecha = document.OCAPConvocatoriasForm.DFinCp.value + '/' + document.OCAPConvocatoriasForm.MFinCp.value + '/' + document.OCAPConvocatoriasForm.AFinCp.value;      
     
      if (!esFechaCorrecta(fecha)){
         mensajeError = mensajeError + "\u25b6 Fecha de Fin de Convocatoria"+ "\n";
         validacion=1;   
      }
    
   
//    //Fecha de Reconocimiento de Grado
//      fecha = document.OCAPConvocatoriasForm.DRecGrado.value + '/' + document.OCAPConvocatoriasForm.MRecGrado.value + '/' + document.OCAPConvocatoriasForm.ARecGrado.value;      
     
//       if (!esFechaCorrecta(fecha)){
//          mensajeError = mensajeError + "\u25b6 Fecha de Reconocimiento de Grado"+ "\n";
//          validacion=1;   
//       }
   
   
   if ((document.OCAPConvocatoriasForm.NDias_revsolicitud.value =="" || document.OCAPConvocatoriasForm.NDias_revsolicitud.value == 0 || !soloNumeros(document.OCAPConvocatoriasForm.NDias_revsolicitud)) ||
       (document.OCAPConvocatoriasForm.NDias_regsolicitud.value =="" || document.OCAPConvocatoriasForm.NDias_regsolicitud.value ==0 || !soloNumeros(document.OCAPConvocatoriasForm.NDias_regsolicitud)) ||
       (document.OCAPConvocatoriasForm.NDias_publistado.value =="" || document.OCAPConvocatoriasForm.NDias_publistado.value ==0 || !soloNumeros(document.OCAPConvocatoriasForm.NDias_publistado)) ||
       (document.OCAPConvocatoriasForm.NDias_auto_mc.value =="" || document.OCAPConvocatoriasForm.NDias_auto_mc.value ==0 || !soloNumeros(document.OCAPConvocatoriasForm.NDias_auto_mc)) ||
       (document.OCAPConvocatoriasForm.NDias_val_mc.value =="" ||  document.OCAPConvocatoriasForm.NDias_val_mc.value ==0 ||!soloNumeros(document.OCAPConvocatoriasForm.NDias_val_mc)) ||
       (document.OCAPConvocatoriasForm.NDias_inconf_mc.value =="" || document.OCAPConvocatoriasForm.NDias_inconf_mc.value ==0 || !soloNumeros(document.OCAPConvocatoriasForm.NDias_inconf_mc)) ||
       (document.OCAPConvocatoriasForm.NDias_auto_ca.value =="" ||document.OCAPConvocatoriasForm.NDias_auto_ca.value ==0 ||  !soloNumeros(document.OCAPConvocatoriasForm.NDias_auto_ca)) ||
       (document.OCAPConvocatoriasForm.NDias_val_ca.value =="" || document.OCAPConvocatoriasForm.NDias_val_ca.value ==0 || !soloNumeros(document.OCAPConvocatoriasForm.NDias_val_ca)) ||             
       (document.OCAPConvocatoriasForm.NDias_val_cc.value =="" || document.OCAPConvocatoriasForm.NDias_val_cc.value ==0 ||  !soloNumeros(document.OCAPConvocatoriasForm.NDias_val_cc)) ||
       (document.OCAPConvocatoriasForm.NDias_inconf_ca.value != "" && !soloNumeros(document.OCAPConvocatoriasForm.NDias_inconf_ca)) ||       
       (document.OCAPConvocatoriasForm.NDias_respinconf_mc.value != "" && !soloNumeros(document.OCAPConvocatoriasForm.NDias_respinconf_mc)) ||       
       (document.OCAPConvocatoriasForm.NDias_respinconf_ca.value != "" && !soloNumeros(document.OCAPConvocatoriasForm.NDias_respinconf_ca)) ||       
       (document.OCAPConvocatoriasForm.NDias_respinconf_cc.value != "" && !soloNumeros(document.OCAPConvocatoriasForm.NDias_respinconf_cc))){       
         mensajeError = mensajeError + "\u25b6 Plazos obligatorios"+ "\n";
         validacion=1;   
      }
      
      
   if (document.OCAPConvocatoriasForm.BCierreSo.value == "" || document.OCAPConvocatoriasForm.BCierreSo.value.toUpperCase() != "<%=Constantes.SI_ESP%>" && document.OCAPConvocatoriasForm.BCierreSo.value.toUpperCase() != "<%=Constantes.NO%>"){
         mensajeError = mensajeError + "\u25b6 Cierre de convocatoria debe ser S o N"+ "\n";
         validacion=1;   
      } 
      
   if (document.OCAPConvocatoriasForm.AObservaciones.value != "" && !LetrasYNumeros(document.OCAPConvocatoriasForm.AObservaciones)){
         mensajeError = mensajeError + "\u25b6 Observaciones debe contener texto v\340lido"+ "\n";
         validacion=1;   
      }           
  
   if  (validacion ==0){
	      if (tipoAccion == '<%=Constantes.INSERTAR%>') {
	         enviar('OCAPConvocatorias.do?accion=insertar');    
	      }else{
    
      enviar('OCAPConvocatorias.do?accion=grabar&cConvovatoriaIdS='+ document.OCAPConvocatoriasForm.CConvocatoria_id.value);
   }
}else{
  alert (mensajeError);
}

}

function volverConsulta() {   
	   enviar('OCAPConvocatorias.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
		}

function completeFieldWithZero(fieldSize,nameField) {
	// Resolución
	if(nameField=='DResolucion' && document.OCAPConvocatoriasForm.DResolucion.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DResolucion.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DResolucion.value,
					fieldSize-document.OCAPConvocatoriasForm.DResolucion.value.length);
		return;
	}
	if(nameField=='MResolucion' && document.OCAPConvocatoriasForm.MResolucion.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MResolucion.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MResolucion.value,
					fieldSize-document.OCAPConvocatoriasForm.MResolucion.value.length);
		return;
	}
	// Publicación
	if(nameField=='DPublicacion' && document.OCAPConvocatoriasForm.DPublicacion.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DPublicacion.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DPublicacion.value,
					fieldSize-document.OCAPConvocatoriasForm.DPublicacion.value.length);
		return;
	}
	if(nameField=='MPublicacion' && document.OCAPConvocatoriasForm.MPublicacion.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MPublicacion.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MPublicacion.value,
					fieldSize-document.OCAPConvocatoriasForm.MPublicacion.value.length);
		return;
	}
	// Inicio Solicitud
	if(nameField=='DInicioSolicitud' && document.OCAPConvocatoriasForm.DInicioSolicitud.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DInicioSolicitud.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DInicioSolicitud.value,
					fieldSize-document.OCAPConvocatoriasForm.DInicioSolicitud.value.length);
		return;
	}
	if(nameField=='MInicioSolicitud' && document.OCAPConvocatoriasForm.MInicioSolicitud.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MInicioSolicitud.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MInicioSolicitud.value,
					fieldSize-document.OCAPConvocatoriasForm.MInicioSolicitud.value.length);
		return;
	}
	// Fin Solicitud
	if(nameField=='DFinSolicitud' && document.OCAPConvocatoriasForm.DFinSolicitud.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DFinSolicitud.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DFinSolicitud.value,
					fieldSize-document.OCAPConvocatoriasForm.DFinSolicitud.value.length);
		return;
	}
	if(nameField=='MFinSolicitud' && document.OCAPConvocatoriasForm.MFinSolicitud.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MFinSolicitud.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MFinSolicitud.value,
					fieldSize-document.OCAPConvocatoriasForm.MFinSolicitud.value.length);
		return;
	}
	// Inicio Alegaciones
	if(nameField=='DInicioAlega' && document.OCAPConvocatoriasForm.DInicioAlega.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DInicioAlega.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DInicioAlega.value,
					fieldSize-document.OCAPConvocatoriasForm.DInicioAlega.value.length);
		return;
	}
	if(nameField=='MInicioAlega' && document.OCAPConvocatoriasForm.MInicioAlega.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MInicioAlega.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MInicioAlega.value,
					fieldSize-document.OCAPConvocatoriasForm.MInicioAlega.value.length);
		return;
	}
	// Inicio MC
	if(nameField=='DInicioMC' && document.OCAPConvocatoriasForm.DInicioMC.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DInicioMC.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DInicioMC.value,
					fieldSize-document.OCAPConvocatoriasForm.DInicioMC.value.length);
		return;
	}
	if(nameField=='MInicioMC' && document.OCAPConvocatoriasForm.MInicioMC.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MInicioMC.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MInicioMC.value,
					fieldSize-document.OCAPConvocatoriasForm.MInicioMC.value.length);
		return;
	}
	// Fin MC
	if(nameField=='DFinMC' && document.OCAPConvocatoriasForm.DFinMC.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DFinMC.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DFinMC.value,
					fieldSize-document.OCAPConvocatoriasForm.DFinMC.value.length);
		return;
	}
	if(nameField=='MFinMC' && document.OCAPConvocatoriasForm.MFinMC.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MFinMC.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MFinMC.value,
					fieldSize-document.OCAPConvocatoriasForm.MFinMC.value.length);
		return;
	}
	// Inicio Valoración MC
	if(nameField=='DInicioValoracionMC' && document.OCAPConvocatoriasForm.DInicioValoracionMC.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DInicioValoracionMC.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DInicioValoracionMC.value,
					fieldSize-document.OCAPConvocatoriasForm.DInicioValoracionMC.value.length);
		return;
	}
	if(nameField=='MInicioValoracionMC' && document.OCAPConvocatoriasForm.MInicioValoracionMC.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MInicioValoracionMC.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MInicioValoracionMC.value,
					fieldSize-document.OCAPConvocatoriasForm.MInicioValoracionMC.value.length);
		return;
	}
	// Fin Valoración MC
	if(nameField=='DFinValoracionMC' && document.OCAPConvocatoriasForm.DFinValoracionMC.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DFinValoracionMC.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DFinValoracionMC.value,
					fieldSize-document.OCAPConvocatoriasForm.DFinValoracionMC.value.length);
		return;
	}
	if(nameField=='MFinValoracionMC' && document.OCAPConvocatoriasForm.MFinValoracionMC.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MFinValoracionMC.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MFinValoracionMC.value,
					fieldSize-document.OCAPConvocatoriasForm.MFinValoracionMC.value.length);
		return;
	}
	// Inicio Valoración CC
	if(nameField=='DInicioValoracionCC' && document.OCAPConvocatoriasForm.DInicioValoracionCC.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DInicioValoracionCC.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DInicioValoracionCC.value,
					fieldSize-document.OCAPConvocatoriasForm.DInicioValoracionCC.value.length);
		return;
	}
	if(nameField=='MInicioValoracionCC' && document.OCAPConvocatoriasForm.MInicioValoracionCC.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MInicioValoracionCC.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MInicioValoracionCC.value,
					fieldSize-document.OCAPConvocatoriasForm.MInicioValoracionCC.value.length);
		return;
	}
	// Fin Valoración CC
	if(nameField=='DFinValoracionCC' && document.OCAPConvocatoriasForm.DFinValoracionCC.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DFinValoracionCC.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DFinValoracionCC.value,
					fieldSize-document.OCAPConvocatoriasForm.DFinValoracionCC.value.length);
		return;
	}
	if(nameField=='MFinValoracionCC' && document.OCAPConvocatoriasForm.MFinValoracionCC.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MFinValoracionCC.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MFinValoracionCC.value,
					fieldSize-document.OCAPConvocatoriasForm.MFinValoracionCC.value.length);
		return;
	}
	// Inicio CC
	if(nameField=='DInicioCC' && document.OCAPConvocatoriasForm.DInicioCC.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DInicioCC.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DInicioCC.value,
					fieldSize-document.OCAPConvocatoriasForm.DInicioCC.value.length);
		return;
	}
	if(nameField=='MInicioCC' && document.OCAPConvocatoriasForm.MInicioCC.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MInicioCC.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MInicioCC.value,
					fieldSize-document.OCAPConvocatoriasForm.MInicioCC.value.length);
		return;
	}
	// Fin CC
	if(nameField=='DFinCC' && document.OCAPConvocatoriasForm.DFinCC.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DFinCC.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DFinCC.value,
					fieldSize-document.OCAPConvocatoriasForm.DFinCC.value.length);
		return;
	}
	if(nameField=='MFinCC' && document.OCAPConvocatoriasForm.MFinCC.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MFinCC.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MFinCC.value,
					fieldSize-document.OCAPConvocatoriasForm.MFinCC.value.length);
		return;
	}
	// Inicio CA
	if(nameField=='DInicioCA' && document.OCAPConvocatoriasForm.DInicioCA.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DInicioCA.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DInicioCA.value,
					fieldSize-document.OCAPConvocatoriasForm.DInicioCA.value.length);
		return;
	}
	if(nameField=='MInicioCA' && document.OCAPConvocatoriasForm.MInicioCA.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MInicioCA.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MInicioCA.value,
					fieldSize-document.OCAPConvocatoriasForm.MInicioCA.value.length);
		return;
	}
	// Fin CA
	if(nameField=='DFinCA' && document.OCAPConvocatoriasForm.DFinCA.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DFinCA.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DFinCA.value,
					fieldSize-document.OCAPConvocatoriasForm.DFinCA.value.length);
		return;
	}
	if(nameField=='MFinCA' && document.OCAPConvocatoriasForm.MFinCA.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MFinCA.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MFinCA.value,
					fieldSize-document.OCAPConvocatoriasForm.MFinCA.value.length);
		return;
	}
	// Inicio Val CA
	if(nameField=='DInicioValCA' && document.OCAPConvocatoriasForm.DInicioValCA.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DInicioValCA.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DInicioValCA.value,
					fieldSize-document.OCAPConvocatoriasForm.DInicioValCA.value.length);
		return;
	}
	if(nameField=='MInicioValCA' && document.OCAPConvocatoriasForm.MInicioValCA.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MInicioValCA.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MInicioValCA.value,
					fieldSize-document.OCAPConvocatoriasForm.MInicioValCA.value.length);
		return;
	}
	// Fin Val CA
	if(nameField=='DFinValCA' && document.OCAPConvocatoriasForm.DFinValCA.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DFinValCA.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DFinValCA.value,
					fieldSize-document.OCAPConvocatoriasForm.DFinValCA.value.length);
		return;
	}
	if(nameField=='MFinValCA' && document.OCAPConvocatoriasForm.MFinValCA.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MFinValCA.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MFinValCA.value,
					fieldSize-document.OCAPConvocatoriasForm.MFinValCA.value.length);
		return;
	}
	// Fin Convocatoria
	if(nameField=='DFinCp' && document.OCAPConvocatoriasForm.DFinCp.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DFinCp.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DFinCp.value,
					fieldSize-document.OCAPConvocatoriasForm.DFinCp.value.length);
		return;
	}
	if(nameField=='MFinCp' && document.OCAPConvocatoriasForm.MFinCp.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MFinCp.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MFinCp.value,
					fieldSize-document.OCAPConvocatoriasForm.MFinCp.value.length);
		return;
	}
	// Fin Reconocimiento de Grado
	if(nameField=='DRecGrado' && document.OCAPConvocatoriasForm.DRecGrado.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.DRecGrado.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.DRecGrado.value,
					fieldSize-document.OCAPConvocatoriasForm.DRecGrado.value.length);
		return;
	}
	if(nameField=='MRecGrado' && document.OCAPConvocatoriasForm.MRecGrado.value.length<fieldSize) {
		document.OCAPConvocatoriasForm.MRecGrado.value = 
			fillZerosAtLeft(document.OCAPConvocatoriasForm.MRecGrado.value,
					fieldSize-document.OCAPConvocatoriasForm.MRecGrado.value.length);
		return;
	}
}
function fillZerosAtLeft(stringToFill,nZeros){
	if(stringToFill.length!=0){
		var i = 0;
		for (i = 0; i < nZeros; i++) { 
			stringToFill = '0'+stringToFill;
		}
	}
	return stringToFill;
}
</script>

<div class="ocultarCampo">

	<div class="cuadroContenedor">
		<html:form action="/OCAPConvocatorias.do">
			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<div class="titulocajaformulario">Alta de Convocatoria</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<div class="titulocajaformulario">Detalle de Convocatoria</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
				<div class="titulocajaformulario">Editar Convocatoria</div>
			</logic:equal>
			<div class="cajaformulario">
				<fieldset class="normales">
					<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<legend class="normalesLegend">Datos a insertar</legend>
					</logic:equal>
					<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
						<legend class="normalesLegend">Datos a editar</legend>
					</logic:equal>
					<hr class="hrblanco"></hr>
					<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
						<html:hidden property="CConvocatoria_id" />&nbsp;                  
</logic:equal>

					<br/>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Convocatoria:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaConvocaConvo" size="60"
							maxlength="200" readonly="true" />&nbsp;                  
					</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Convocatoria:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaConvocaConvo" size="60"
							maxlength="200" />&nbsp;                  
					</logic:notEqual>
					<html:errors property="DNombre" />

					
					
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;&nbsp;&nbsp;&nbsp;A&ntildeo (aaaa):</label>
						<html:text property="DAnioConvocatoria" tabindex="18"
							styleClass="inputColor colocaConvocaConvo" size="4"
							maxlength="4" readonly="true" />&nbsp;                  
					</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;&nbsp;&nbsp;&nbsp;* A&ntildeo (aaaa):</label>
						<html:text property="DAnioConvocatoria" tabindex="18"
							styleClass="inputColor colocaConvocaConvo"size="4" maxlength="4" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
					</logic:notEqual>
					<html:errors property="DAnioConvocatoria" />

					<br />
					
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Nombre Corto:</label>
						<html:text property="DNombreCorto" tabindex="18"
							styleClass="inputColor colocaConvocaConvo" size="50"
							maxlength="50" readonly="true" />&nbsp;                  
					</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Nombre Corto:</label>
						<html:text property="DNombreCorto" tabindex="18"
							styleClass="inputColor colocaConvocaConvo" size="50"
							maxlength="50" />&nbsp;                  
					</logic:notEqual>
					<html:errors property="DNombreCorto" />					
					
					
					<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label class="obligado" style="margin-left:77px;">&nbsp;Grado:</label>
						<html:text property="CGrado_id" tabindex="18" 
							styleClass="inputColor colocaConvocaConvo" size="5" style="margin-left: 5px !important; background-color:lightgray;"
							maxlength="5" readonly="true" />&nbsp;                  
					</logic:notEqual>
					<logic:equal name="tipoAccion"	value="<%=Constantes.INSERTAR%>">
						<label class="obligado" for="idVGrado" style="margin-left:77px;">* Grado: <html:select
							property="CGrado_id" tabindex="18" styleClass="cbCuadros colocaGradoBuscCB" style="margin-left: 5px !important;"
							size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
								property="CGradoId" labelProperty="DDescripcion" />
							</html:select>
						</label>            
					</logic:equal>
					<html:errors property="CGrado_id" />					
					
					<br/>
					
					
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Resoluci&oacute;n:</label>
						<html:text property="FResolucion" tabindex="18"
							styleClass="inputColor" size="9" maxlength="10" readonly="true" />&nbsp;                  
					</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Fecha de Resoluci&oacute;n
							(dd/mm/aaaa):</label>
						<html:text property="DResolucion" tabindex="18"
							styleClass="inputColor colocaFechaConvo" size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'DResolucion');" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="MResolucion" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'MResolucion');" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="AResolucion" tabindex="18"
							styleClass="inputColor" size="4" maxlength="4" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
</logic:notEqual>
					<html:errors property="FResolucion" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Publicaci&oacute;n:</label>
						<html:text property="FPublicacion" tabindex="18"
							styleClass="inputColor" size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Fecha de Publicaci&oacute;n
							(dd/mm/aaaa):</label>
						<html:text property="DPublicacion" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2"  
							onblur="completeFieldWithZero(2,'DPublicacion');calculoFechaInicioSolicitudes();"
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="MPublicacion" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'MPublicacion');calculoFechaInicioSolicitudes();" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="APublicacion" tabindex="18"
							styleClass="inputColor" size="4" maxlength="4" 
							onblur="calculoFechaInicioSolicitudes();" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
</logic:notEqual>
					<html:errors property="FPublicacion" />

				
					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Inicio de Solicitudes:</label>
						<html:text property="FInicioSolicitud" tabindex="18"
							styleClass="inputColor" size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Fecha de Inicio de Solicitudes
							(dd/mm/aaaa):</label>
						<html:text property="DInicioSolicitud" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" readonly="true" 
							onblur="completeFieldWithZero(2,'DInicioSolicitud');" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="MInicioSolicitud" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" readonly="true" 
							onblur="completeFieldWithZero(2,'MInicioSolicitud');" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="AInicioSolicitud" tabindex="18"
							styleClass="inputColor" size="4" maxlength="4" readonly="true"
							onkeypress="return permitirSoloNumeros(event);" 
							 />&nbsp;      
							            
</logic:notEqual>
					<html:errors property="FInicioSolicitud" />
					
					<label class="obligado">&nbsp;&nbsp;&nbsp;&nbsp;Plazo de solicitud:</label>
					<html:text property="NDias_regsolicitud" tabindex="18"
						styleClass="inputColor" size="2" maxlength="2"
						onblur="calculoFechaFinSolicitud();"
						 />					
					<html:errors property="NDias_regsolicitud" />
					<br />
					

					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Fin de Solicitudes:</label>
						<html:text property="FFinSolicitud" tabindex="18"
							styleClass="inputColor" size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Fecha de Fin de Solicitudes
							(dd/mm/aaaa):</label>
						<html:text property="DFinSolicitud" 
							styleClass="inputColor" size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'DFinSolicitud');" 
							onkeypress="return permitirSoloNumeros(event);" 
							readonly="true" />&nbsp;                  
   						<html:text property="MFinSolicitud"
							styleClass="inputColor" size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'MFinSolicitud');" 
							onkeypress="return permitirSoloNumeros(event);" 
							readonly="true" />&nbsp;                  
   						<html:text property="AFinSolicitud" 
							styleClass="inputColor" size="4" maxlength="4" 							
							onkeypress="return permitirSoloNumeros(event);"
							readonly="true"  />
					</logic:notEqual>
					<html:errors property="FFinSolicitud" />
					
					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Publicaci&oacute;n de
							Listado Provisional e Inicio de Alegaciones:</label>
						<html:text property="FInicioAlega" tabindex="18"
							styleClass="inputColor" size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Fecha de Publicaci&oacute;n de
							Listado Provisional e Inicio de Alegaciones (dd/mm/aaaa):</label>
						<html:text property="DInicioAlega" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'DInicioAlega');" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="MInicioAlega" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'MInicioAlega');" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="AInicioAlega" tabindex="18"
							styleClass="inputColor" size="4" maxlength="4" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
</logic:notEqual>
					<html:errors property="FInicioAlega" />


			<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Fin de gesti&oacute;n PGP:</label>
						<html:text property="FFinPgp" tabindex="18"
							styleClass="inputColor" size="9" maxlength="10" readonly="true" />&nbsp;                  
					</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Fecha de Fin de gesti&oacute;n PGP
							(dd/mm/aaaa):</label>
						<html:text property="DFinPgp" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'DFinPgp');" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="MFinPgp" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'MFinPgp');" 
							onkeypress="return permitirSoloNumeros(event);"  />&nbsp;                  
   						<html:text property="AFinPgp" tabindex="18"
							styleClass="inputColor" size="4" maxlength="4" 							
							onkeypress="return permitirSoloNumeros(event);" />
					</logic:notEqual>
					<html:errors property="FFinPgp" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Inicio de MC:</label>
						<html:text property="FInicioMC" tabindex="18"
							styleClass="inputColor" size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Fecha de Inicio de MC
							(dd/mm/aaaa):</label>
						<html:text property="DInicioMC" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'DInicioMC');" 
							onchange="calculoFechaFinMC();"
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="MInicioMC" tabindex="18" styleClass="inputColor"
							size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'MInicioMC');" 
							onchange="calculoFechaFinMC();"
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="AInicioMC" tabindex="18" styleClass="inputColor"
							size="4" maxlength="4" 							
							onchange="calculoFechaFinMC();" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;  
							                
</logic:notEqual>
					<html:errors property="FFinMC" />
					
					<label class="obligado">&nbsp;&nbsp;&nbsp;&nbsp;Plazo de
						autoevaluaci&oacute;n de MC:</label>
					<html:text property="NDias_auto_mc" tabindex="18"
						styleClass="inputColor" size="2" maxlength="2" 
						onchange="calculoFechaFinMC();" />
						<html:errors property="NDias_auto_mc" />

					<br />
					
					
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Fin de MC:</label>
						<html:text property="FFinMC" tabindex="18" styleClass="inputColor"
							size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Fecha de Fin de MC
							(dd/mm/aaaa):</label>
						<html:text property="DFinMC"  styleClass="inputColor"
							size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'DFinMC');calculoPlazoAutoEvaluacionMC();" 
							onkeypress="return permitirSoloNumeros(event);" 
							 readonly="true"/>&nbsp;                  
   						<html:text property="MFinMC"  styleClass="inputColor"
							size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'MFinMC');calculoPlazoAutoEvaluacionMC();" 
							onkeypress="return permitirSoloNumeros(event);" 
							 readonly="true"/>&nbsp;                  
   						<html:text property="AFinMC"  styleClass="inputColor"
							size="4" maxlength="4" 
							onblur="calculoPlazoAutoEvaluacionMC();" 
							onkeypress="return permitirSoloNumeros(event);" 
							 readonly="true"/>&nbsp;                  
</logic:notEqual>
					<html:errors property="FFinMC" />
					
					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Inicio de valoraci&oacute;n MC:</label>
						<html:text property="FInicioValoracionMC" tabindex="18"
							styleClass="inputColor" size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Fecha de Inicio de valoraci&oacute;n MC
							(dd/mm/aaaa):</label>
						<html:text property="DInicioValoracionMC" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2"  
							onblur="completeFieldWithZero(2,'DInicioValoracionMC');" 
							onchange="calculoFechaFinValoracionMC();"
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="MInicioValoracionMC" tabindex="18" styleClass="inputColor"
							size="2" maxlength="2"  
							onblur="completeFieldWithZero(2,'MInicioValoracionMC');"
							onchange="calculoFechaFinValoracionMC();" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="AInicioValoracionMC" tabindex="18" styleClass="inputColor"
							size="4" maxlength="4"  							
							onchange="calculoFechaFinValoracionMC();"
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;          
							        
</logic:notEqual>
					<html:errors property="FInicioValoracionMC" />
					
					
					<label class="obligado">&nbsp;Plazo de valoraci&oacute;n de MC:</label>
						<html:text property="NDias_val_mc" tabindex="18"
							styleClass="inputColor" size="3" maxlength="3"
							onchange="calculoFechaFinValoracionMC();"  />&nbsp;                  

					<html:errors property="NDias_val_mc" />

					<br />
					
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Fin de valoraci&oacute;n MC:</label>
						<html:text property="FFinValoracionMC" tabindex="18" styleClass="inputColor"
							size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Fecha de Fin de valoraci&oacute;n MC
							(dd/mm/aaaa):</label>
						<html:text property="DFinValoracionMC"  styleClass="inputColor"
							size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'DFinValoracionMC');calculoPlazoValoracionMC();" 
							onkeypress="return permitirSoloNumeros(event);" 
							 readonly="true"/>&nbsp;                  
   						<html:text property="MFinValoracionMC"  styleClass="inputColor"
							size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'MFinValoracionMC');calculoPlazoValoracionMC();" 
							onkeypress="return permitirSoloNumeros(event);" 
							 readonly="true"/>&nbsp;                  
   						<html:text property="AFinValoracionMC"  styleClass="inputColor"
							size="4" maxlength="4" 
							onblur="calculoPlazoValoracionMC();" 
							onkeypress="return permitirSoloNumeros(event);" 
							 readonly="true"/>&nbsp;                  
</logic:notEqual>
					<html:errors property="FFinValoracionMC" />
					
					 <br />
					
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Inicio de valoraci&oacute;n CC:</label>
						<html:text property="FInicioValoracionCC" tabindex="18"
							styleClass="inputColor" size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Fecha de Inicio de valoraci&oacute;n CC (dd/mm/aaaa):</label>
						<html:text property="DInicioValoracionCC" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'DInicioValoracionCC');" 
							onchange="calculoFechaFinValoracionCC();"
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="MInicioValoracionCC" tabindex="18" styleClass="inputColor"
							size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'MInicioValoracionCC');"
							onchange="calculoFechaFinValoracionCC();" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="AInicioValoracionCC" tabindex="18" styleClass="inputColor"
							size="4" maxlength="4" 							
							onchange="calculoFechaFinValoracionCC();"
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;          
							        
</logic:notEqual>
					<html:errors property="FInicioValoracionCC" />
					
					<label class="obligado">&nbsp;Plazo de valoraci&oacute;n de CC:</label>
						<html:text property="NDias_val_cc" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2"
							onchange="calculoFechaFinValoracionCC();" />&nbsp;                  

					<html:errors property="NDias_val_cc" />
					<br />
					
					
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Fin de valoraci&oacute;n CC:</label>
						<html:text property="FFinValoracionCC" tabindex="18" styleClass="inputColor"
							size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Fecha de Fin de valoraci&oacute;n CC
							(dd/mm/aaaa):</label>
						<html:text property="DFinValoracionCC"  styleClass="inputColor"
							size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'DFinValoracionCC');calculoPlazoValoracionCC();" 
							onkeypress="return permitirSoloNumeros(event);" 
							 readonly="true"/>&nbsp;                  
   						<html:text property="MFinValoracionCC" styleClass="inputColor"
							size="2" maxlength="2"  
							onblur="completeFieldWithZero(2,'MFinValoracionCC');calculoPlazoValoracionCC();" 
							onkeypress="return permitirSoloNumeros(event);" 
							 readonly="true"/>&nbsp;                  
   						<html:text property="AFinValoracionCC"  styleClass="inputColor"
							size="4" maxlength="4" 
							onblur="calculoPlazoValoracionCC();"
							onkeypress="return permitirSoloNumeros(event);" 
							 readonly="true"/>&nbsp;                  
</logic:notEqual>
					<html:errors property="FFinValoracionCC" />
			
					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de listado provisional de MC:</label>
						<html:text property="FInicioEstudioCC" tabindex="18"
							styleClass="inputColor" size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						
							<label class="obligado">* Fecha de listado provisional de MC (dd/mm/aaaa):</label>
						
						<html:text property="DInicioCC" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'DInicioCC');" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="MInicioCC" tabindex="18" styleClass="inputColor"
							size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'MInicioCC');" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="AInicioCC" tabindex="18" styleClass="inputColor"
							size="4" maxlength="4" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
</logic:notEqual>
					<html:errors property="FInicioEstudioCC" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de listado definitivo de MC:</label>
						<html:text property="FFinEstudioCC" tabindex="18"
							styleClass="inputColor" size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						
							<label class="obligado">* Fecha de listado definitivo de
								MC (dd/mm/aaaa):</label>
						
						<html:text property="DFinCC" tabindex="18" styleClass="inputColor"
							size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'DFinCC');" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="MFinCC" tabindex="18" styleClass="inputColor"
							size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'MFinCC');" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="AFinCC" tabindex="18" styleClass="inputColor"
							size="4" maxlength="4" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
</logic:notEqual>
					<html:errors property="FFinEstudioCC" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Inicio de CA:</label>
						<html:text property="FInicioCA" tabindex="18"
							styleClass="inputColor" size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Fecha de Inicio de CA
							(dd/mm/aaaa):</label>
						<html:text property="DInicioCA" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'DInicioCA');"
							onchange="calculoFechaFinCA();" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="MInicioCA" tabindex="18" styleClass="inputColor"
							size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'MInicioCA');"
							onchange="calculoFechaFinCA();" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="AInicioCA" tabindex="18" styleClass="inputColor"
							size="4" maxlength="4"  
							onchange="calculoFechaFinCA();"
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;   
							               
</logic:notEqual>
					<html:errors property="FInicioCA" />
					
					
					
					<label class="obligado">&nbsp;Plazo de autoevaluaci&oacute;n de
							CA:</label>
					<html:text property="NDias_auto_ca" tabindex="18"
							styleClass="inputColor colocaPAutConvo" size="2" maxlength="2"
							onchange="calculoFechaFinCA();" />&nbsp;                  

					<html:errors property="NDias_auto_ca" />


					<br />
					

<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Fin de CA:</label>
						<html:text property="FFinCA" tabindex="18"
							styleClass="inputColor" size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Fecha de Fin de CA	(dd/mm/aaaa):</label>
						<html:text property="DFinCA"
							styleClass="inputColor" size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'DFinCA');calculoPlazoAutoEvaluacionCA();" 
							onkeypress="return permitirSoloNumeros(event);" 
							 readonly="true"/>&nbsp;                  
   						<html:text property="MFinCA"  styleClass="inputColor"
							size="2" maxlength="2"  
							onblur="completeFieldWithZero(2,'MFinCA');calculoPlazoAutoEvaluacionCA();" 
							onkeypress="return permitirSoloNumeros(event);" 
							 readonly="true"/>&nbsp;                  
   						<html:text property="AFinCA" styleClass="inputColor"
							size="4" maxlength="4" 
							onblur="calculoPlazoAutoEvaluacionCA();" 
							onkeypress="return permitirSoloNumeros(event);" 
							 readonly="true"/>&nbsp;      
							            
</logic:notEqual>
					<html:errors property="FFinCA" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Inicio de valoraci&oacute;n CA:</label>
						<html:text property="FInicioValCA" tabindex="18"
							styleClass="inputColor" size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Fecha de Inicio de valoraci&oacute;n CA
							(dd/mm/aaaa):</label>
						<html:text property="DInicioValCA" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'DInicioValCA');" 
							onchange="calculoFechaFinValoracionCA();"
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="MInicioValCA" tabindex="18" styleClass="inputColor"
							size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'MInicioValCA');" 
							onchange="calculoFechaFinValoracionCA();"
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="AInicioValCA" tabindex="18" styleClass="inputColor"
							size="4" maxlength="4" 							
							onchange="calculoFechaFinValoracionCA();"
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;   
							               
</logic:notEqual>
					<html:errors property="FInicioValCA" />
					
					<label class="obligado"> Plazo de valoraci&oacute;n de CA:</label>
						<html:text property="NDias_val_ca" tabindex="18"
							styleClass="inputColor" size="3" maxlength="3"
							onchange="calculoFechaFinValoracionCA();" />&nbsp;                  
					<html:errors property="NDias_val_ca" />
					<br/>
					

<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Fin de valoraci&oacute;n CA:</label>
						<html:text property="FFinValCA" tabindex="18"
							styleClass="inputColor" size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Fecha de Fin de valoraci&oacute;n CA	(dd/mm/aaaa):</label>
						<html:text property="DFinValCA" 
							styleClass="inputColor" size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'DFinValCA');calculoPlazoValoracionCA();" 
							onkeypress="return permitirSoloNumeros(event);" 
							 readonly="true"/>&nbsp;                  
   						<html:text property="MFinValCA"  styleClass="inputColor"
							size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'MFinValCA');calculoPlazoValoracionCA();" 
							onkeypress="return permitirSoloNumeros(event);" 
							 readonly="true"/>&nbsp;                  
   						<html:text property="AFinValCA"  styleClass="inputColor"
							size="4" maxlength="4" 
							onblur="calculoPlazoValoracionCA();" 
							onkeypress="return permitirSoloNumeros(event);" 
							 readonly="true"/>&nbsp;      
							            
</logic:notEqual>
					<html:errors property="FFinValCA" />
					
					
						<br />
					
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Fin de Convocatoria:</label>
						<html:text property="FFinCp" tabindex="18" styleClass="inputColor"
							size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Fecha de Fin de Convocatoria
							(dd/mm/aaaa):</label>
						<html:text property="DFinCp" tabindex="18" styleClass="inputColor"
							size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'DFinCp');" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="MFinCp" tabindex="18" styleClass="inputColor"
							size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'MFinCp');" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="AFinCp" tabindex="18" styleClass="inputColor"
							size="4" maxlength="4" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
</logic:notEqual>
					<html:errors property="FFinCp" />
					<br />

					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Reconocimiento de Grado:</label>
						<html:text property="FRecGrado" tabindex="18"
							styleClass="inputColor" size="9" maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Fecha de Reconocimiento de Grado (dd/mm/aaaa):</label>
						<html:text property="DRecGrado" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'DRecGrado');" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="MRecGrado" tabindex="18" styleClass="inputColor"
							size="2" maxlength="2" 
							onblur="completeFieldWithZero(2,'MRecGrado');" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
   						<html:text property="ARecGrado" tabindex="18" styleClass="inputColor"
							size="4" maxlength="4" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
</logic:notEqual>
					<html:errors property="FRecGrado" />
						
					<br />


					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Plazo de revisi&oacute;n:</label>
						<html:text property="NDias_revsolicitud" tabindex="18"
							styleClass="inputColor colocaPRevConvo" size="2" maxlength="2"
							readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Plazo de revisi&oacute;n:</label>
						<html:text property="NDias_revsolicitud" tabindex="18"
							styleClass="inputColor colocaPRevConvo" size="2" maxlength="2" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
</logic:notEqual>
					<html:errors property="NDias_revsolicitud" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Plazo de publicaci&oacute;n:</label>
						<html:text property="NDias_publistado" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Plazo de publicaci&oacute;n:</label>
						<html:text property="NDias_publistado" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
</logic:notEqual>
					<html:errors property="NDias_publistado" />

					<br />

					
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Plazo de alegaciones de MC:</label>
						<html:text property="NDias_inconf_mc" tabindex="18"
							styleClass="inputColor colocaPIncConvo" size="2" maxlength="2"
							readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Plazo de alegaciones de MC:</label>
						<html:text property="NDias_inconf_mc" tabindex="18"
							styleClass="inputColor colocaPIncConvo" size="2" maxlength="2" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
</logic:notEqual>
					<html:errors property="NDias_inconf_mc" />
<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Plazo de alegaciones de CA:</label>
						<html:text property="NDias_inconf_ca" tabindex="18"
							styleClass="inputColor colocaPIncCAConvo" size="2" maxlength="2"
							readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Plazo de alegaciones de CA:</label>
						<html:text property="NDias_inconf_ca" tabindex="18"
							styleClass="inputColor colocaPIncCAConvo" size="2" maxlength="2" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
</logic:notEqual>
					<html:errors property="NDias_inconf_ca" />

					<br />
					
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Plazo de respuesta de alegaciones	de MC:</label>
						<html:text property="NDias_respinconf_mc" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Plazo de respuesta de	alegaciones de MC:</label>
						<html:text property="NDias_respinconf_mc" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
</logic:notEqual>
					<html:errors property="NDias_respinconf_mc" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Plazo de respuesta de alegaciones	de CA:</label>
						<html:text property="NDias_respinconf_ca" tabindex="18"
							styleClass="inputColor colocaPResCAConvo" size="2" maxlength="2"
							readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Plazo de respuesta de	alegaciones de CA:</label>
						<html:text property="NDias_respinconf_ca" tabindex="18"
							styleClass="inputColor colocaPResCAConvo" size="2" maxlength="2" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
</logic:notEqual>
					<html:errors property="NDias_respinconf_ca" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Plazo de respuesta de alegaciones	de CC:</label>
						<html:text property="NDias_respinconf_cc" tabindex="18"
							styleClass="inputColor colocaPResCCConvo" size="2" maxlength="2"
							readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Plazo de respuesta de	alegaciones de CC:</label>
						<html:text property="NDias_respinconf_cc" tabindex="18"
							styleClass="inputColor colocaPResCCConvo" size="2" maxlength="2" 
							onkeypress="return permitirSoloNumeros(event);" />&nbsp;                  
</logic:notEqual>
					<html:errors property="NDias_respinconf_cc" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">&nbsp;Convocatoria cerrada:</label>
						<html:text property="BCierreSo" tabindex="18"
							styleClass="inputColor colocaPResCCConvo" size="1" maxlength="1"
							readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Convocatoria cerrada(S/N):</label>
						<html:text property="BCierreSo" tabindex="18"
							styleClass="inputColor colocaPResCCConvo" size="1" maxlength="1" />&nbsp;                  
</logic:notEqual>
					<html:errors property="BCierreSo" />

					<br /> <label class="obligado">&nbsp;Observaciones:</label>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:text property="AObservaciones" tabindex="18"
							styleClass="inputColor colocaObserva" size="60" maxlength="500"
							readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<html:text property="AObservaciones" tabindex="18"
							styleClass="inputColor colocaObserva" size="60" maxlength="500" />&nbsp;                  
</logic:notEqual>
					<html:errors property="AObservaciones" />
					<br />
					<div class="botonera">
						<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:validar('<bean:write name="tipoAccion" />');">
										<span> Dar Alta </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</logic:equal>
						<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:validar('<bean:write name="tipoAccion" />');">
										<span> Grabar </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</logic:equal>
						<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:volverConsulta();"> <span> Volver </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</logic:notEqual>

					</div>

				</fieldset>
			</div>
			<div id="divTexto">
				<p>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligadoTexto">Los campos se&ntilde;alados con * son obligatorios</label>
					</logic:notEqual>

				</p>
			</div>
		</html:form>
	</div>

</div>
<!-- /Fin de ocultarCampo -->
