var insertar = 'INSERTAR';
var modificar = 'MODIFICAR';

////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   //////////////////////////////////////////////////////////                   //
//   /////                 E N V I A R                    /////                   //
//   //////////////////////////////////////////////////////////                   //
//                                                                                //
//   Definición:                                                                  //
//       realiza un submit con el action que se le pasa por parametro             //
//   Entra:                                                                       //
//       parametro: cadena con el action del formulario                           //
////////////////////////////////////////////////////////////////////////////////////
function enviar(parametro){
      document.forms[0].action = parametro;
      document.forms[0].submit();
}

// función para enviar el segundo formulario 
function enviarForm1(parametro){
      document.forms[1].action = parametro;
      document.forms[1].submit();
}

//funcion que realiza el submit del formulario si el campo NIF es correcto.
//Solo aplica a altaAcceso.jsp
function enviarValidadoNIF(){
	
	 var cadena = document.forms[0].ADni.value; 
	 
	 if (validar_nif(cadena))
		 {
	
	  
      document.forms[0].submit();
		 }
	
	 else{
		 alert ('Formato de NIF o NIE incorrecto. Debe modificarse');
	 }
      
}


 


////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   //////////////////////////////////////////////////////////////////////////   //
//   /////                         A V I S O L U P A                      /////   //
//   //////////////////////////////////////////////////////////////////////////   //
//                                                                                //
//   Definición:                                                                  //
//       lanza la alerta de aviso de la imagen de la lupa                         //
////////////////////////////////////////////////////////////////////////////////////
function avisoLupa() {
  blur();
  alert('Este campo no es editable.\nPulse sobre la lupa adyacente.');
}

////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   //////////////////////////////////////////////////////////////////////////   //
//   /////                    A V I S O C A L E N D A R I O               /////   //
//   //////////////////////////////////////////////////////////////////////////   //
//                                                                                //
//   Definición:                                                                  //
//       lanza la alerta de aviso de la imagen del calendario                     //
////////////////////////////////////////////////////////////////////////////////////
function avisoCalendario() {
  blur();
  alert('Este campo no es editable.\nPulse sobre el calendario adyacente.');
}


////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   //////////////////////////////////////////////////////////////////////////   //
//   /////                         E S T A D O                            /////   //
//   //////////////////////////////////////////////////////////////////////////   //
//                                                                                //
//   Definición:                                                                  //
//       retorna la definicion del estado del objetivo                            //
////////////////////////////////////////////////////////////////////////////////////

function estado(estado) {
   switch(estado) {
      case 'N':
         return 'No Programado';
         break;
      case 'I':
         return 'Iniciado';
         break;
      case 'F':
         return 'Finalizado';
         break;
      default:
         return '';
         break;
      
   }
}

////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   //////////////////////////////////////////////////////////////////////////   //
//   /////                  S I T U A C I O N                             /////   //
//   //////////////////////////////////////////////////////////////////////////   //
//                                                                                //
//   Definición:                                                                  //
//       retorna la definicion de la situacion del objetivo                       //
////////////////////////////////////////////////////////////////////////////////////

function situacion(situacion) {
   switch(situacion) {
        case 'C':
            return 'Critico';
            break;
         case 'H':
            return 'Holgura';
            break;
         case 'R':
            return 'Retraso';
            break;
         default:
            if (situacion=="CR")
               return 'Critico-Retraso';
            else
               return '';
            break;
               
   }            
}


function LetrasYNumeros(campo){
   //var checkOK = " ABCDEFGHIJKLMÑNOPQRSTUVWXYZÁÉÍÓÚÄËÏÖÜ" + "abcdefghijklmnñopqrstuvwxyzáéíóúäëèäëïöü" + "0123456789" + "&$#!¡?¿+-*/<>.:,;@|ºª\[]{}_()";
   var checkOK = " ABCDEFGHIJKLM\u00d1NOPQRSTUVWXYZ\u00c1\u00c9\u00cd\u00d3\u00da\u00c4\u00cb\u00cf\u00d6\u00dc\u00c0\u00c8\u00cc\u00d2\u00d9" + "abcdefghijklmn\u00f1opqrstuvwxyz\u00e1\u00e9\u00ed\u00f3\u00fa\u00e4\u00eb\u00ef\u00f6\u00fc\u00e0\u00e8\u00ec\u00f2\u00f9" + "0123456789" + "&$#!¡?¿+-*/<>.:,;@|ºª\[]{}_()";
   var checkStr = campo.value;
   code = checkStr.charCodeAt(0);
   for (i = 0; i < checkStr.length; i++) {
      ch = checkStr.charAt(i);
      for (j = 0; j < checkOK.length; j++)
         if (ch == checkOK.charAt(j))
            break;
      if (j == checkOK.length) {
         //alert("Escriba sólo números y letras.");
         return false;
         break;
      }
   }
   return true;
}


function soloNumeros (campo){
   var checkOK = "0123456789";
   var checkStr = campo.value;
   var allValid = true;
   var decPoints = 0;
   var allNum = "";
   for (i = 0; i < checkStr.length; i++) {
      ch = checkStr.charAt(i);
      for (j = 0; j < checkOK.length; j++){
         if (ch == checkOK.charAt(j))
            break;
      }
      if (j == checkOK.length) {
         allValid = false;
         break;
      }
      allNum += ch;
   }
   if (!allValid) {
      return false;
   }else{
      return true;
   }
}


function esNumeroDecimal(fieldValue, decimales) {
   if (isNaN(fieldValue) || fieldValue == "") {
      return false;
   } else {
      if (fieldValue.indexOf('.') == -1) fieldValue += ".";
      dectext = fieldValue.substring(fieldValue.indexOf('.')+1, fieldValue.length);
      if (dectext.length > decimales)
         return false;
      else
         return true;
   }
}


function esDireccionCorreo (campo){
   var s = campo.value;
   var filter=/^[A-Za-z0-9][A-Za-z0-9_.-]*@[A-Za-z0-9_]+\.[A-Za-z0-9_.]+[A-za-z]$/;
   if (s.length == 0 ) return true;
   if (filter.test(s)) return true;
   return false;
}

function telefonos (campo){
   var number = campo.value;
   if( (number.length<9) || (number.length>9)){
      //alert("El teléfono debe ser de 9 dígitos");
      return false;
   }
   for(i=0;i<number.length;i++){
      code = number.charCodeAt(i);
      
      if (code<48 || code>57){
      //alert("Teléfono inválido");
         return false;         
      }

   }
   return true;   
}

// La función valida  que el nif tenga un formato correcto. Devuelve como valor el NIF/NIE 
// con el formato validado.Si no es correcto en formato devuelve NULL.
// V_TIPO = 1 ES UN nif, = 2 un NIE, = 0 es erroneo 

function validar_nif ( nif ) {
  var v_nif = trim(nif);
  var v_tipo = 0;
  var v_valido = 0;
  var v_Aux = "";
  //Por el primer caracter, comprobamos si se trata de un NIF o un NIE
  // En v_nif hay que quitar los espacios por la izquierda.
  while ( v_nif.charAt(0) == " ") {
    v_nif = v_nif.substr ( 1 , v_nif.length - 1 );
  }
  v_Aux = v_nif.charAt( 0 );
  if ( v_Aux >= 0 && v_Aux <= 9 ) {
    // Es un NIF
    v_tipo = 1;
  }
  else if ( v_Aux >= "X" ) {
    // Es un NIE
    v_tipo = 2;
  }
  else {
    v_tipo = 0;
    v_valido = 1;
  }

  //Tratamos los NIF
  if ( v_tipo == 1 ) {
    var v_Tabla_Letras = "TRWAGMYFPDXBNJZSQVHLCKE";
    var v_Letra_NIF = null;
    var v_Numero_NIF = "";
  
    // Este bucle separa el número del NIF (v_Numero_NIF) y la letra (v_Letra_NIF).
    // También quita todos los caracteres que no son numéricos, y se queda con la
    // última letra, si existe.
    for ( i = 0; i < v_nif.length-1; i++ ) {
      v_Aux = v_nif.charAt( i );
    
      if ( v_Aux >= 0 && v_Aux <= 9 ) {
        v_Numero_NIF += "" + v_Aux;
      }else {
        v_valido = 2;
      }
    }
    v_Aux =  v_nif.charAt(v_nif.length-1).toUpperCase();
   
    if ( v_Aux >= "A" && v_Aux <= "Z" ) {
          v_Letra_NIF = v_Aux;
    }
  
    if ( v_Numero_NIF.length > 8 ) {
      v_valido = 2;
    }
    else {
      if ( v_Numero_NIF.length < 8 ) {
       v_valido = 2;
      }
      if ( v_Letra_NIF != null ) {
        v_nif = v_Numero_NIF + "" + v_Letra_NIF;
      }
      else {
        v_valido = 2;
      }
    }
  }
  
  //Tratamos los NIE
  if ( v_tipo == 2 ) {
  
	num = new Array();                
	nif = nif.toUpperCase();                
	for (i = 0; i < nif.length; i ++) {                    
		num[i] = nif.substr(i, 1);                
	}     
  	//comprobacion de NIEs                
	//T                
	if (nif.match('^[T]{1}')) {  
		if (num[8] == nif.match('^[T]{1}[A-Z0-9]{8}$')) {                        
			return 3;                    
		} else {               
			return 0;                    
		}                
	}               

	//XYZ                
	if (nif.match('^[XYZ]{1}')) {                    
	tmpstr = nif.replace('X', '0');                    
	tmpstr = tmpstr.replace('Y', '1');                    
	tmpstr = tmpstr.replace('Z', '2');   
	
	
	 if (num[nif.length-1] == 'TRWAGMYFPDXBNJZSQVHLCKE'.substr( tmpstr.substr(0, nif.length-1) % 23, 1)) {                        
			return 3;                    
	 } else {                        
			return 0;                    
		}                
		
  }
    
  }
  
  if ( v_tipo == 0 ) {
//    alert("ERROR EN EL TIPO DE NIF/NIE");
    return false;
  }
  else {
    if ( v_valido == 2 ) {
//      alert("ERROR EN EL FORMATO DE NIF");
      return false;
    }
    else {
      if ( v_valido == 3 ) {
//        alert("ERROR EN EL FORMATO DE NIE");
        return false;
      }
      else {
      //La validacion de formato es correcta, validamos ahora la letra del NIF
        if ( v_tipo == 1 ) {
          // Ahora, en v_Aux, metemos la letra correcta del número V_Numero_NIF.
          v_Aux = v_Tabla_Letras.charAt ( v_Numero_NIF % 23 );
          if ( v_Letra_NIF != v_Aux ) {
//            alert("LA LETRA DEL NIF NO ES VALIDA");
            return false;
          }
          else {
            return true;
          }
        }
        else {
          return true;
        }
      }
    }
  }
}


function volver (){
   document.forms[0].target= "_top";
   enviar('PaginaInicio.do');
}

function volverLogin (){
   document.forms[0].target= "_top";
   enviar('CerrarSesion.do');
}

function permitirSoloNumeros(evt) {
   //se admiten: numeros
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
   return (key < 13 || (key >= 48 && key <= 57) || key==46);
}

function deshabilitarFormulario(formulario) {
   for (i=0; i < formulario.elements.length; i++) {
      formulario.elements[i].disabled=true;
   }
}
   
function obtenerCifra(valor) {
   cifraPrimera = valor.substring(0,1);
   if (cifraPrimera == '0' && valor.length >1)
      var cifra = parseInt(valor.substring(1));
   else 
      var cifra = parseInt(valor);
   return cifra;      
}

function soloNumerosUnDecimal(evt) {
   //se admiten: numeros y "." 
   var nav4 = window.Event ? true : false;
   var key = nav4 ? evt.which : evt.keyCode; 

   if (key == null)
      key = nav4 ? evt.keyCode : evt.which;
      
   if (key < 13 || (key >= 48 && key <= 57) || key==46){
      var aux=document.forms[0].NCreditosCurso.value + String.fromCharCode(key);
      
      if (aux.indexOf('.')!=-1) {
         aux=aux.substring(aux.indexOf('.')+1);
         
         if (aux.length == 2)
            return false;
      }
      
      return true;
   }else
      return false
   
}

//Comprueba si es un DNI correcto (entre 5 y 8 letras seguidas de la letra que corresponda).
//Acepta NIEs (Extranjeros con X, Y o Z al principio)
function validateDNI(dni) {
 var numero, letraIni, letra;
 var expresion_regular_dni = /^[XYZ]?\d{5,8}[A-Z]$/;

 dni = dni.toUpperCase();

 if(expresion_regular_dni.test(dni) === true){
     numero = dni.substr(0,dni.length-1);
     numero = numero.replace('X', 0);
     numero = numero.replace('Y', 1);
     numero = numero.replace('Z', 2);
     letraIni = dni.substr(dni.length-1, 1);
     numero = numero % 23;
     letra = 'TRWAGMYFPDXBNJZSQVHLCKET';
     letra = letra.substring(numero, numero+1);
     if (letra != letraIni) {
       
         return false;
     }else{
       
         return true;
     }
 }else{

     return false;
 }
}