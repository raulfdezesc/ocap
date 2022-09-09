function cuantos(){
	var numRows = document.forms[0].numRows.value;
	var c = 0;
  
	for(k=0;k<numRows;k++){
  
		if (document.forms[0].elements["checkList["+k+"]"].checked==true) 
			{c=c+1;}
	}
	return c;
}

function isNumberMayorCero(x){
y = parseInt(x,10);
if ( (!isNaN(y)) && (x==y) ){if (y>0){return true;}else{return false;}}
return false;
}


function validar_fecha(dia,mes,anio){
var fecha=dia+"/"+mes+"/"+anio;
var er_mes31dias=/^([1-3]0|[0-2][1-9]|31|[0-9])\/(1|01|3|03|5|05|7|07|8|08|10|12)\/(1999|20[0-1][0-9]|20[2-4][0-9])$/
var er_mes30dias=/^([1-3]0|[0-2][1-9]|[0-9])\/(4|04|6|06|9|09|11)\/(1999|20[0-1][0-9]|20[2-4][0-9])$/
var er_mes28dias=/^([1-2]0|[0-2][1-8]|[0-1]9|[0-9])\/(02|2)\/(1999|200[1-3]|200[5-7]|2009|201[0-1]|201[3-5]|201[7-9]|202[1-3]|202[5-7]|2029|2030|2031|203[3-5]|203[7-9]|204[1-3]|204[5-7])$/
var er_mes29dias=/^([1-2]0|[0-2][1-9]|[0-9])\/(02|2)\/(2000|2004|2008|2012|2016|2020|2024|2028|2032|2036|2040|2044|2048)$/
if(fecha=="//")return false;
if(!(er_mes31dias.test(fecha)||er_mes30dias.test(fecha)||er_mes29dias.test(fecha)||er_mes28dias.test(fecha)))
return false;
return true;
}

function esNegativo(importe){
var longitud=importe.length;
var posComa=importe.indexOf(',');
if(posComa !=-1)
importe=importe.substring(0,posComa)+"."+importe.substring(posComa+1,longitud);
var valorImporte=parseFloat(importe,10);
if(valorImporte <=0){return true;}
return false;
}

function validaInputFecha(diaFech,mesFech,anyoFech,field) {
if ((diaFech.value==""&& mesFech.value==""&& anyoFech.value=="") && (mesFech.value==""&& mesFech.value==""&& mesFech.value=="") && (anyoFech.value==""&& anyoFech.value==""&& anyoFech.value=="")){
	return true;}
else{	
	if ((diaFech.value==""&& mesFech.value==""&& anyoFech.value=="") || (mesFech.value==""&& mesFech.value==""&& mesFech.value=="") || (anyoFech.value==""&& anyoFech.value==""&& anyoFech.value==""))
	{
		alert("Debe introducir la fecha completa");
		return false;
	}else{
		var bisiesto;
		var days = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
		var fecha = diaFech.value.toString();
		var dia = parseInt(diaFech.value,10);
		
		if (isNaN(dia)) {alert("El día introducido no es un número. Por favor, vuelva a rellenarlo");diaFech.focus();return false;}
		var mes = parseInt(mesFech.value,10);
		if (isNaN(mes)){alert("El mes introducido no es un número. Por favor, vuelva a rellenarlo");mesFech.focus();return false;}
		var ano = parseInt(anyoFech.value,10);
		if (isNaN(ano)) {alert("El año introducido no es un número. Por favor, vuelva a rellenarlo");anyoFech.focus();return false;}
		
		var limite=0 ;
		var indice;
		var fecha1 = new Date();
		var actual = Date.parse(fecha1);
		if (mes>12 || mes<1) {alert("El mes debe estar comprendido entre 1 y 12, por favor, introdúzcalo de nuevo.");mesFech.focus();return false;}
		indice = mes--;
		(ano % 4 == 0) ? ((ano %100 ==0)?((ano % 400 ==0)? bisiesto = true : bisiesto = false) : bisiesto = true) : bisiesto = false;
		if ((bisiesto== true) && (indice ==2)){limite = 29;} else {limite = days[mes];}
		if (dia<0 || dia>limite) { alert ("El dia debe estar comprendido entre 1 y "+limite+", por favor, introdúzcalo de nuevo."); diaFech.focus();return false;}
		field.value = diaFech.value+mesFech.value+anyoFech.value;
		return true;
		
		
	}
	}
}


function isNumberMayorCero(x){
y = parseInt(x,10);
if ( (!isNaN(y)) && (x==y) ){
	if (y>0){return true;}else{return false;}
}
return false;
}

function esMenorFecha(diaFech,mesFech,anyoFech){
var fechaActual=new Date();
var annioActual=fechaActual.getFullYear();
var diaActual=fechaActual.getDate();
var mesActual=fechaActual.getMonth()+1;
if(anyoFech.value > annioActual){anyoFech.focus();return false;
}else if(anyoFech.value < annioActual){return true;
}else if(anyoFech.value==annioActual){
if(mesFech.value > mesActual){
mesFech.focus();
return false;
}else if(mesFech.value < mesActual){
return true;
}else if(mesFech.value==mesActual){
if(diaFech.value > diaActual){diaFech.focus();return false;}else if(diaFech.value <=diaActual){return true;}
}}}

function esMenorAnnio(anyoFech){
var fechaActual=new Date();
var annioActual=fechaActual.getFullYear();
if(anyoFech.value > annioActual){
anyoFech.focus();
return false;
}else if(anyoFech.value < annioActual){return true;}
}

// patron que se utiliza al validar el correo en los jsp
// Algo seguido de @ seguido de algo
var patron_correoElectronico=/.+@.+/  


function validar(s,patron){
	var s1;
	var reg1 = /(@.*@)|(\.\.)|(@\.)|(\.@)|(^\.)/;
	s1=s.match(patron);
	return(((s1 !=null)&(s==s1))& (!reg1.test(s)));
}

function removeBlanks(theinput){
while(theinput.value.indexOf(" ")==0){theinput.value=theinput.value.slice(1,theinput.value.length);}
if(theinput.value.length!=0){
while(theinput.value.lastIndexOf(" ")==theinput.value.length-1){theinput.value=theinput.value.slice(0,theinput.value.length-1);}
}}


function autoCompleta(campo){
campoValue=campo.value;
if(isNaN(campoValue)){
return false;
}else if(campoValue.length==1){
campo.value="0"+campoValue;
}}

function autoCompletaYear(year){
var yearValue=year.value;
if(isNaN(yearValue))
return false;
if(yearValue.length==2){
if(yearValue <=10){
year.value="20"+yearValue;
}else{
year.value="19"+yearValue;
}
}else if(yearValue.length==1){
year.value="200"+yearValue;
}else if(yearValue.length==3){
if(yearValue <=10){
year.value="2"+yearValue;
}else{
year.value="1"+yearValue;
}}}

function isNumerico(cadena){
		 var valid = "0123456789";
         var cadenaValue = cadena.value;
     	 cadenaValue =cadenaValue.replace(",","");
     	 cadenaValue =cadenaValue.replace(".","");

		 var caracterTexto;
		 var validacion; 		   
	 	 for (var i=0; i<cadenaValue.length; i++){
           
			caracterTexto = cadenaValue.substring(i,i+1);
			validacion = valid.indexOf(caracterTexto);
            if (validacion == -1 ){ 
				return false;
 				} 		
		 }

		 return true;
	}
    
function validarImporte(campo, longitudEntero, longitudDecimal) {
    if (isNumerico(campo))
    {
        var importe = campo.value;
        var decimales ="0";
        
                
        if (importe.length >=0) {
            importe = importe.replace(",",".");
            var punto =-1;
            punto = importe.indexOf(".");
            
            //tiene almenos un .
            if (punto != -1)
            {
             
              
              //Comprobar si tiene más de un .
               var posicion =  punto;
               punto = importe.substring(punto+1, importe.length).indexOf(".");
              
              if (punto != -1)
              {
                return false;
              }
           
             //Si no tiene más de un punto mirar la longitud de la parte entera y la decimal
              var entero = importe.substring(0,posicion);
              
              if (entero.length > longitudEntero)
              {
                return false;
              }else{
                decimal = importe.substring(posicion+1, importe.length);
                
                //Si la longitud de la parte decimal es menor que el tamaño añadir 0
                if ( decimal.length > longitudDecimal)
                {
                  
                  return false;
                }else{
                   
                    return true;
                   
                }
              }
            }else{
              
              if (importe.length > longitudEntero)
              {
                return false;
              }else{
                
                return true;             
                
              }
            }
            
        }else{
          return false;
        }
        }else{
            return false;
        }
     
   
}

