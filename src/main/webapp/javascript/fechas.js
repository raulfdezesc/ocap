
//   V A L I D A F E C H A  //////////////////////////////////////////////////

// funcion que nos dice si una fecha es mayor que otra en formato DD/MM/YYYY HH:MI
// fechax=DD/MM/YYYY horax=HH:MI
function esFecha1MayorQueFecha2(fecha1, hora1, fecha2, hora2){
   if ( fecha1 != '' && fecha2 != '' && hora1 != '' && hora2 !='') {
      fecha1Day   = parseFloat(fecha1.substr(0,2));
      fecha1Month = parseFloat(fecha1.substr(3,2)) - 1;
      fecha1Year  = parseFloat(fecha1.substr(6,4));
      fecha1Hora  = parseFloat(hora1.substr(0,2));
      fecha1Minu  = parseFloat(hora1.substr(3,2));
      fecha2Day   = parseFloat(fecha2.substr(0,2));
      fecha2Month = parseFloat(fecha2.substr(3,2)) - 1;
      fecha2Year  = parseFloat(fecha2.substr(6,4));
      fecha2Hora  = parseFloat(hora2.substr(0,2));
      fecha2Minu  = parseFloat(hora2.substr(3,2));
      
      date1 = new Date( fecha1Year , fecha1Month , fecha1Day, fecha1Hora, fecha1Minu, 0);
      date2 = new Date( fecha2Year , fecha2Month , fecha2Day, fecha2Hora, fecha2Minu, 0);
      if ( date1.getTime() > date2.getTime() ) {
         return true;
      } else {
         return false;
      }
   }
}

//   V A L I D A F E C H A  //////////////////////////////////////////////////

// funcion que nos dice si una fecha es mayor que otra en formato DD/MM/YYYY HH:MI
// fechax=DD/MM/YYYY horax=HH:MI:SS
function esFechaHora1MayorQueFechaHora2(fecha1, hora1, fecha2, hora2){
   if ( fecha1 != '' && fecha2 != '' && hora1 != '' && hora2 !='') {
      fecha1Day   = parseFloat(fecha1.substr(0,2));
      fecha1Month = parseFloat(fecha1.substr(3,2)) - 1;
      fecha1Year  = parseFloat(fecha1.substr(6,4));
      fecha1Hora  = parseFloat(hora1.substr(0,2));
      fecha1Minu  = parseFloat(hora1.substr(3,2));
      fecha1Seg   = parseFloat(hora1.substr(6,2));
      fecha2Day   = parseFloat(fecha2.substr(0,2));
      fecha2Month = parseFloat(fecha2.substr(3,2)) - 1;
      fecha2Year  = parseFloat(fecha2.substr(6,4));
      fecha2Hora  = parseFloat(hora2.substr(0,2));
      fecha2Minu  = parseFloat(hora2.substr(3,2));
      fecha2Seg   = parseFloat(hora2.substr(6,2));
      date1 = new Date( fecha1Year , fecha1Month , fecha1Day, fecha1Hora, fecha1Minu, fecha1Seg);
      date2 = new Date( fecha2Year , fecha2Month , fecha2Day, fecha2Hora, fecha2Minu, fecha2Seg);
      
      if ( date1.getTime() > date2.getTime() ) {
         return true;
      } else {
         return false;
      }
   }
}
// Valida que la fecha1 sea menor que la fecha2
// En caso de que no sea asi, saca una alerta
function validaFecha( obj , fecha1 , fecha2 , texto_fecha1 , texto_fecha2 ) {
   return validaFecha( obj , fecha1 , fecha2 , texto_fecha1 , texto_fecha2, 'MayorQue');
}

function validaFecha( obj , fecha1 , fecha2 , texto_fecha1 , texto_fecha2, tipoTexto ) {
  
  if ( fecha1 != '' && fecha2 != '' ) {
    fecha1Day = parseFloat(fecha1.substr(0,2));
    fecha1Month = parseFloat(fecha1.substr(3,2)) - 1;
    fecha1Year = parseFloat(fecha1.substr(6,4));
    fecha2Day = parseFloat(fecha2.substr(0,2));
    fecha2Month = parseFloat(fecha2.substr(3,2)) - 1;
    fecha2Year = parseFloat(fecha2.substr(6,4));
    date1 = new Date( fecha1Year , fecha1Month , fecha1Day );
    date2 = new Date( fecha2Year , fecha2Month , fecha2Day );
    if ( date1.getTime() > date2.getTime() ) {
      if (tipoTexto == 'MenorQue') {
         alert("La " + texto_fecha1 + " debe ser menor que la " + texto_fecha2);
      } else {
         alert("La " + texto_fecha2 + " debe ser mayor que la " + texto_fecha1);
      }
      
      obj.value = '';
      return false;
    } else {
      return true;
    }
  } else {
    return false;
  }
}


////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   ////////////////////////////////////////////////////////////////             //
//   /////       V a l i d a r F e c h a C o n A l e r t a      /////             //
//   ////////////////////////////////////////////////////////////////             //
//                                                                                //
//   Definición:                                                                  //
//       Comprueba que el campo fecha es correcto, sino lo es de mensaje de       //
//       alerta y devuelve el foco al campo                                       //
//   Entra:                                                                       //
//       controlFecha:   control a comprobar                                      //
//       descripcion:    Nombre del campo a mostrar en la alerta                  //
//   Retorna:                                                                     //
//       boolean:					  																    //												// 
////////////////////////////////////////////////////////////////////////////////////

function ValidarFechaConAlerta (controlFecha, descripcion) {
   if (!esFechaCorrecta (controlFecha.value)) {
      if (descripcion != '')
         alert ("Formato incorrecto para la fecha de " + descripcion +
             "\n\n  El formato correcto es DD/MM/AAAA.");
      else alert ("Formato incorrecto para la fecha. "+
             "\n\n  El formato correcto es DD/MM/AAAA.");
      controlFecha.focus();
      return false;
   } else {
      return true;
   }
}


////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   ////////////////////////////////////////////////////////////////             //
//   /////           E S F E C H A C O R R E C T A              /////             //
//   ////////////////////////////////////////////////////////////////             //
//                                                                                //
//   Definición:                                                                  //
//       Comprueba que la cadena introducida sea una fecha correcta 				    //
//   Entra:                                                                       //
//       cadena:   cadena a comprobar                                             //
//   Retorna:                                                                     //
//       boolean:					  																    //
////////////////////////////////////////////////////////////////////////////////////

function esFechaCorrecta (fecha) {
   var dia, mes, anho;
   
	if (fecha.length != 10) {
        return false;
	}
   
	if (fecha.charAt(2)!='/' || fecha.charAt(5)!='/') {
        return false;
	}
   
	dia = fecha.substring(0,2);
	mes = fecha.substring(3,5);
	anho = fecha.substring(6,10);

	if(!(esNumero(dia) && esNumero(mes) && esNumero(anho))){
		return false;
	}
  
	if(!esFecha (dia, mes, anho)) {
		return false;
	}
	return true;
}


// Comprueba si es numero la cadena introducida
function esNumero (cadena){
	i = 0;
	e = 0;
	while (i < cadena.length) {
		ncadena = cadena.substring(i,i+1);      	
		if ((ncadena != "0") && (ncadena != "1") && (ncadena != "2") && (ncadena != "3") && (ncadena != "4") && (ncadena != "5") && (ncadena != "6") && (ncadena != "7") && (ncadena != "8") && (ncadena !="9")) { 
			e = 1;
		} 
		i = i + 1;
	}
	if (e == 1) {
		return false;
	}else{
		return true;
	}
}


// Comprueba si es una fecha correcta
function esFecha (dd, mm, aa){
   if ((mm == 4) || (mm == 6) ||  (mm == 9) || (mm == 11)) {
        if (dd > 30) {
            sal = 0;
        } else {
            sal = 1;
        }
    } else {
        if (mm == 2) {
        // Año bisiesto
            if ( ( (aa % 4 == 0) && (aa % 100 != 0) ) || (aa % 400 == 0) ){
                if (dd > 29) {
                    sal = 0;
                } else {
                    sal = 1;
                }
            } else {
	// Año no bisiesto
                if (dd > 28) {
                    sal = 0;
                } else {
                    sal = 1;
                }
            }
        } else {
            if (dd > 31) {
                sal = 0;
            } else {
                sal = 1;
            }
        }
    }
    if (dd == 0 && mm==0 && aa==0) {
        sal = 1;
    } else {     
    	if (dd==0 || mm==0 || aa==0) {
           sal = 0;
        } else {
	   		sal = sal;
		}
	}
	if (dd > 31 || mm >12) {
        sal = 0;
    }
	if (sal == 1) {
        return true;
    } else {     
        return false;
    }
}

////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   ////////////////////////////////////////////////////////////////             //
//   /////     C O M P R O B A R F E C H A N O F U T U R A      /////             //
//   ////////////////////////////////////////////////////////////////             //
//                                                                                //
//   Definición:                                                                  //
//       Comprueba que la fecha introducida no sea una fecha futura   			    //
//   Entra:                                                                       //
//       controlFecha:   cadena a comprobar                                             //
//   Retorna:                                                                     //
//       boolean:					  																    //
////////////////////////////////////////////////////////////////////////////////////
function comprobarFechaNoFutura(fechaControl){
   // Creamos una variable tipo Date con la fecha actual
   var fechaActual = new Date();
   var diaActual = fechaActual.getDate(); 
   var mesActual = fechaActual.getMonth();
   var anhoActual = fechaActual.getUTCFullYear();
   date1 = new Date( anhoActual , mesActual , diaActual );

   // Creamos una variable tipo Date con la fecha de control
   var diaControl = fechaControl.substring(0,2);
	var mesControl = fechaControl.substring(3,5)-1;
	var anhoControl = fechaControl.substring(6,10);
   date2 = new Date( anhoControl , mesControl , diaControl );

   // Comprobamos que la fecha actual sea mayor que la de control
   if ( date2.getTime() > date1.getTime() ) {
      return true;
   }else{
      return false;
   }
   
}

////////////////////////////////////////////////////////////////////////////////////
//                                     											          //
//   /////////////////////////////////////////////////////////////////////////    //
//   /////                    V A L I D A A N H O                        /////    //
//   /////////////////////////////////////////////////////////////////////////    //
//                                     							                      //          
//   Definición:                       											          //
//		  Valida una cadena numerica como un año									          //	
//   Entra:																					          //	
//      anho: año que se quiere verificar                                         //
//      limiteInferior: limite inferior del intervalo de años                     //
//      					 en los que queremos que se encuentre anho                   //
//      limiteSuperior: limite superior del intervalo de años                     //
//      					 en los que queremos que se encuentre anho                   //
//   Retorna:																			             //	
//      true: si anho se encuentra entre limiteInferior y limiteSuperior          //
//      false: caso contrario a true												          //	
////////////////////////////////////////////////////////////////////////////////////
function ValidaAnho(anho, limiteInferior, limiteSuperior)  {
   if (anho < limiteInferior || anho > limiteSuperior)
      return false;
   else return true;
}



////////////////////////////////////////////////////////////////////////////////////
//                                     											          //
//   /////////////////////////////////////////////////////////////////////////    //
//   /////                   diferenciaFechas                        /////    //
//   /////////////////////////////////////////////////////////////////////////    //
//                                     							                      //          
//   Definición:                       											          //
//		  Calcula la diferencia entre dos fechas									          //	
//   Entra:																					          //	
//      	Fecha Inicio y Fin                                       //
//   Retorna:																			             //	
//      La diferencia												          //	
////////////////////////////////////////////////////////////////////////////////////


function diferenciaFechas (CadenaFecha1,CadenaFecha2) {   

   var fecha1 = new fecha( CadenaFecha1 );
   var fecha2 = new fecha( CadenaFecha2 );  
      
   var miFecha1 = new Date( fecha1.anio, fecha1.mes, fecha1.dia );
   var miFecha2 = new Date( fecha2.anio, fecha2.mes, fecha2.dia );  
  
   var diferencia = miFecha1.getTime() - miFecha2.getTime();
   var dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));  
   var segundos = Math.floor(diferencia / 1000);
   //alert ('La diferencia es de ' + dias + ' dias,\no ' + segundos + ' segundos.')   
      
   return dias;   
}   
  
function fecha( cadena ) {   
  
   var separador = "/";
  
   if ( cadena.indexOf( separador ) != -1 ) {   
        var posi1 = 0;
        var posi2 = cadena.indexOf( separador, posi1 + 1 );
        var posi3 = cadena.indexOf( separador, posi2 + 1 );  
        this.dia = cadena.substring( posi1, posi2 );
        this.mes = cadena.substring( posi2 + 1, posi3 );
        this.anio = cadena.substring( posi3 + 1, cadena.length );
   } else {   
        this.dia = 0;
        this.mes = 0;  
        this.anio = 0;
   }   
}   
  
////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   ////////////////////////////////////////////////////////////////             //
//   /////           E S T I M E S T A M P C O R R E C T O      /////             //
//   ////////////////////////////////////////////////////////////////             //
//                                                                                //
//   Definición:                                                                  //
//       Comprueba que la cadena introducida sea una fecha correcta 				    //
//   Entra:                                                                       //
//       cadena:   cadena a comprobar                                             //
//   Retorna:                                                                     //
//       boolean:					  																    //
////////////////////////////////////////////////////////////////////////////////////

function esTimestampCorrecto (fecha) {
   var dia, mes, anho, hora, min, seg;
   
	if (fecha.length != 19) {
        return false;
	}
   
	if (fecha.charAt(2)!='/' || fecha.charAt(5)!='/') {
        return false;
	}

	if (fecha.charAt(13)!=':' || fecha.charAt(16)!=':') {
        return false;
	}
   
	dia = fecha.substring(0,2);
	mes = fecha.substring(3,5);
	anho = fecha.substring(6,10);
   hora = fecha.substring(11,13);
   min = fecha.substring(14,16);
   seg = fecha.substring(17,19);

	if(!(esNumero(dia) && esNumero(mes) && esNumero(anho) && esNumero(hora) && esNumero(min) && esNumero(seg))){
		return false;
	}
  
	if(!esFechaHora (dia, mes, anho, hora, min, seg)) {
		return false;
	}
	return true;
}

// Comprueba si es una fecha correcta
function esFechaHora (dd, mm, aa, hh, mi, ss){
   if ((mm == 4) || (mm == 6) ||  (mm == 9) || (mm == 11)) {
        if (dd > 30) {
            sal = 0;
        } else {
            sal = 1;
        }
    } else {
        if (mm == 2) {
        // Año bisiesto
            if ( ( (aa % 4 == 0) && (aa % 100 != 0) ) || (aa % 400 == 0) ){
                if (dd > 29) {
                    sal = 0;
                } else {
                    sal = 1;
                }
            } else {
	// Año no bisiesto
                if (dd > 28) {
                    sal = 0;
                } else {
                    sal = 1;
                }
            }
        } else {
            if (dd > 31) {
                sal = 0;
            } else {
                sal = 1;
            }
        }
    }
    if (dd == 0 && mm==0 && aa==0) {
        sal = 1;
    } else {     
    	if (dd==0 || mm==0 || aa==0) {
           sal = 0;
        } else {
	   		sal = sal;
		}
	}
	if (dd > 31 || mm >12) {
        sal = 0;
    }
    
   if (hh > 23 || mi >59 || ss >59){
      sal = 0;
   }
   
	if (sal == 1) {
        return true;
    } else {     
        return false;
    }
}  

function esHoraCorrecta (hora) {
	hh = hora.substring(0,2);
	min = hora.substring(3,5);
	seg = hora.substring(6,8);
   
	if (hora.charAt(2)!=':' || hora.charAt(5)!=':') {
        return false;
	}   
   
   if (isNaN(parseInt(hh,10)) || isNaN(parseInt(min,10)) ||  isNaN(parseInt(seg,10)))
      return false;
   
   if (parseInt(hh,10) > 23 || parseInt(min,10) >59 || parseInt(seg,10) >59){
      return false;
   }else{
      return true;
   }
}

function esHoraMinutosCorrecta (hora) {
	hh = hora.substring(0,2);
	min = hora.substring(3,5);
   
	if (hora.charAt(2)!=':') {
        return false;
	}   
   if (isNaN(parseInt(hh,10)) || isNaN(parseInt(min,10)))
      return false;
      
   if (parseInt(hh,10) > 23 || parseInt(min,10) >59 ){
      return false;
   }else{
      return true;
   }
}

////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   ////////////////////////////////////////////////////////////////             //
//   /////     C O M P R O B A R A N I O N O F U T U R O        /////             //
//   ////////////////////////////////////////////////////////////////             //
//                                                                                //
//   Definición:                                                                  //
//       Comprueba que el año introducido sea menor que el actual    			    //
//   Entra:                                                                       //
//       anioControl:   cadena a comprobar                                        //
//   Retorna:                                                                     //
//       boolean:					  																    //
////////////////////////////////////////////////////////////////////////////////////
function comprobarAnioNoFuturo(anioControl){
   // Creamos una variable tipo Date con la fecha actual
   var fechaActual = new Date();
   var anhoActual = fechaActual.getUTCFullYear();

   // Comprobamos que la fecha actual sea mayor que la de control
   if ( anhoActual > anioControl ) {
      return true;
   }else{
      return false;
   }
   
}


function annadirDiaFecha(fecha,numdias)
{	
	var fechaDate = Date.parseString(fecha,'dd/MM/yyyy');
	return fechaDate.add('d',numdias);
	 
}


function diasEntreFechas(f1,f2)
{
var aFecha1 = f1.split('/'); 
var aFecha2 = f2.split('/'); 
var fFecha1 = Date.UTC(aFecha1[2],aFecha1[1]-1,aFecha1[0]); 
var fFecha2 = Date.UTC(aFecha2[2],aFecha2[1]-1,aFecha2[0]); 
var dif = fFecha2 - fFecha1;
var dias = Math.floor(dif / (1000 * 60 * 60 * 24)); 
return dias;
}


