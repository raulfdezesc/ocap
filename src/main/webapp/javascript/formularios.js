////////////////////////////////////////////////////////////////////////////////////
//   contarSinEspacios ///////////////////////////////////////////////////////////////////
// Devuelve la longitud de la cadena sin tener en cuenta ni el espacio ni el retorno 
// de carro


function contarSinEspacios(cadena) {
   var aux='';
   var retorno=0;
   for(i=0; i<cadena.length; i++) {
    if (cadena.charAt(i)!=' ')
         aux+=cadena.charAt(i);
    if (cadena.charAt(i)=="\u000D")
      retorno+=2;
   }
   return aux.length-retorno;
}

////////////////////////////////////////////////////////////////////////////////

//   T R I M ///////////////////////////////////////////////////////////////////
// Borra los espacios en blanco que se encuentren al principio y al final de una
// cadena
function trim(cadena) {
   nCadena = rightTrim(cadena);
   nCadena = leftTrim(nCadena)
   return nCadena;
}

//   R I G H T  T R I M ////////////////////////////////////////////////////////
// Borra los espacios en blanco que se encuentren al principio de una cadena
function rightTrim(cadena) {
   while( (''+cadena.charAt(cadena.length-1)==' ') 
         || (''+cadena.charAt(cadena.length-1)=="\n"))
      cadena=cadena.substring(0,cadena.length-1);
   return cadena;
}

//   L E F T  T R I M //////////////////////////////////////////////////////////
// Borra los espacios en blanco que se encuentren al final de una cadena
function leftTrim(cadena) {
   while( (''+cadena.charAt(0)==' ')
         || (''+cadena.charAt(cadena.length-1)=="\n"))
      cadena=cadena.substring(1,cadena.length);
   return cadena;
}
//   LVALORPAD //////////////////////////////////////////////////////////
// Rellena con valores's por la izq
function lvalorPad(cadena, longitud,valor) {
   nCadena = cadena;
   while (nCadena.length < longitud ){
       nCadena = valor+nCadena;
       }
   return nCadena; 
}


////////////////////////////////////////////////////////////////////////////////

//   G E T  I N P  T E X T /////////////////////////////////////////////////////
// Recupera el value de un objeto input text.                                     
function getInpText(caja) {
   return caja.value;
}

//   G E T  L O N G  I N P  T E X T ////////////////////////////////////////////
// Recupera la longitud de la cadena de un value de un objeto input text.
function getLongInpText(caja) {
   nCadena = trim(caja.value);
   return nCadena.length;
}
//   S E T  I N P  T E X T /////////////////////////////////////////////////////
// Incializa el value de un objeto input text.
function setInpText(caja,cadena) {
   caja.value = cadena;
}

////////////////////////////////////////////////////////////////////////////////

//   G E T  H I D D E N ////////////////////////////////////////////////////////
// Recupera el value de un objeto input hidden.                                     
function getHidden(ocul) {
   return ocul.value;
}

//   G E T  L O N G  H I D D E N ///////////////////////////////////////////////
// Recupera la longitud de la cadena de un value de un objeto input hidden.
function getLongHidden(ocul) {
   nCadena = trim(ocul.value);
   return nCadena.length;
}

//   S E T  H I D D E N ////////////////////////////////////////////////////////
// Incializa el value de un objeto input hidden.
function setHidden(ocul,cadena) {
   ocul.value = cadena;
}

////////////////////////////////////////////////////////////////////////////////

//   G E T  P A S S W O R D ////////////////////////////////////////////////////
// Recupera el value de un objeto input password.                                     
function getPassword(contra) {
   return contra.value;
}

//   G E T  L O N G  P A S S W O R D ///////////////////////////////////////////
// Recupera la longitud de la cadena de un value de un objeto input password.
function getLongPassword(contra) {
   nCadena = trim(contra.value);
   return nCadena.length;
}
//   VAL  L O N G  P A S S W O R D ///////////////////////////////////////////
// Valida minima la longitud de la cadena de un value de un objeto input password.
function valLongPassword() {
  var obj = eval(arguments[0]+'.'+arguments[1]);
  var numero = eval(arguments[2]);
   nClave = trim(obj.value);
   if (nClave.length == (numero) )
      return (true);
   else
      return (false);
   
}
//   S E T  P A S S W O R D ////////////////////////////////////////////////////
// Incializa el value de un objeto input password.
function setPassword(contra,cadena) {
   contra.value = cadena;
}

////////////////////////////////////////////////////////////////////////////////

//   G E T  T E X T A R E A ////////////////////////////////////////////////////
// Recupera el value de un objeto textarea.                                     
function getTextarea(txt) {
   return Textarea.value;
}

//   G E T  L O N G  T E X T A R E A ///////////////////////////////////////////
// Recupera la longitud de la cadena de un value de un objeto textarea.
function getLongTextarea(txt) {
   nCadena = trim(txt.value);
   return nCadena.length;
}
//   S E T  T E X T A R E A ////////////////////////////////////////////////////
// Incializa el value de un objeto textarea.
function setTextarea(txt,cadena) {
   txt.value = cadena;
}

////////////////////////////////////////////////////////////////////////////////

//   G E T  R A D I O //////////////////////////////////////////////////////////
// Recupera el value de un objeto input radio.                                     
function getRadio(rad) {
  return rad.value;
}
function getRadio(rad,num) {
  return rad[num].value;
}
//   G E T  S I G N  R A D I O /////////////////////////////////////////////////
// marca o desmarca un objeto input radio.
function getSignRadio(rad) {
   return rad.checked;
}
function getSignRadio(rad,num) {
   return rad[num].checked;
}
//   S E T  S I G N  R A D I O /////////////////////////////////////////////////
// marca o desmarca un objeto input radio.
function setSignRadio(rad,chcked) {
   rad.checked = chcked;
}
function setSignRadio(rad,chcked,nun) {
   rad[num].checked = chcked;
}
//   S E T  R A D I O //////////////////////////////////////////////////////////
// Incializa el value de un objeto input radio.
function setRadio(rad,cadena) {
   rad.value = cadena;
}
function setRadio(rad,cadena,num) {
   rad.value = cadena;
}
//   G E T  V A L U E  R A D I O S /////////////////////////////////////////////
// Recupera el value de unos objetos input radio con el mismo nombre.                                     
function getValueRadios(rad) {
   var valor;
   for (var i = 0; rad.length-1 >= i; i++){
   alert(i)
      if (rad[i].checked == true)
         valor = rad[i].value;
   }
   return valor;
}


////////////////////////////////////////////////////////////////////////////////
//   G E T  C H E C K E D  R A D I O S 
// Recupera el número del checked de un objeto input radio con el mismo nombre.                                     
////////////////////////////////////////////////////////////////////////////////
function getCheckedRadios(rad) {
   var valor;

   for (var i = 0; rad.length-1 >= i; i++){
      if (rad[i].checked == true) {
         valor = i;
      }
   }

   return valor;
}



////////////////////////////////////////////////////////////////////////////////
//   L I M P I A R   R A D I O S
// Pone a false un radio o un grupo de radios
////////////////////////////////////////////////////////////////////////////////
function limpiarRadios (radio) {
   if (radio.length != null){
      for (var i = 0; radio.length-1 >= i; i++) {
         if (radio[i].checked == true) {
            radio[i].checked = false;
            break;
         }
      }
   } else { // Es una matriz de radios
      radio.checked = false;
   }
}



////////////////////////////////////////////////////////////////////////////////

//   G E T  C H E C K B O X ////////////////////////////////////////////////////
// Recupera el value de un objeto input radio.                                     
function getCheckbox(chck) {
  return chck.value;
}
function getCheckbox(chck,num) {
  return chck[num].value;
}
//   G E T  S I G N  C H E C K B O X ///////////////////////////////////////////
// marca o desmarca un objeto input radio.
function getSignCheckbox(chck) {
   return chck.checked;
}
function getSignCheckbox(chck,num) {
   return chck[num].checked;
}
//   S E T  S I G N  C H E C K B O X ///////////////////////////////////////////
// marca o desmarca un objeto input radio.
function setSignCheckbox(chck,chcked) {
   chck.checked = chcked;
}
function setSignCheckbox(chck,chcked,nun) {
   chck[num].checked = chcked;
}
//   S E T  C H E C K B O X ////////////////////////////////////////////////////
// Incializa el value de un objeto input radio.
function setCheckbox(chck,cadena) {
   rad.value = cadena;
}
function setCheckbox(chck,cadena,num) {
   rad.value = cadena;
}
//   G E T  V A L U E  C H E C K B O X E S//////////////////////////////////////
// Recupera el value de unos objetos input radio con el mismo nombre.                                     
function getValueCheckboxes(chck) {
   var valor = '';
   for (var i = 0; chck.length-1 >= i; i++){
      if (chck[i].checked == true) {
         valor += chck[i].value + '#'
      } else{
         valor += '*#'
      }
   }
   
   return valor.substring(0,valor.length-1);
}
//   G E T  C H E C K E D  C H E C K B O X E S /////////////////////////////////
// Recupera el número del checked de un objeto input radio con el mismo nombre.                                     
function getCheckedCheckboxes(chck) {
   var valor = '';
   for (var i = 0; chck.length-1 >= i; i++){
     if (chck[i].checked == true) {
         valor += '' + i + '#'
      } else{
         valor += '*#'
      }
   }
   return valor.substring(0,valor.length-1);
}

////////////////////////////////////////////////////////////////////////////////

//   G E T  V A L U E  S E L E C T /////////////////////////////////////////////
// Recupera el value del option seleccionado de un objeto select.
function getValueSelect(slct) {
   return slct.options[slct.selectedIndex].value;
}

//   G E T  T E X T  S E L E C T /////////////////////////////////////////////
// Recupera el text del option seleccionado de un objeto select.
function getTextSelect(slct) {
   return slct.options[slct.selectedIndex].text;
}

//   G E T  V A L U E  S E L E C T  M U L T I P L E ////////////////////////////
// Recupera el value del option seleccionado de un objeto select.
function getValueSelectMultiple(slct) {
   var valor = ''; 
   for (var i = 0; slct.options.length-1 >= i; i++){
     if (slct.options[i].selected == true) {
         valor += '' + slct.options[i].value + '#'
      } else{
         valor += '*#'
      }
   }
   return valor.substring(0,valor.length-1);
}

//   G E T  S E L E C T E D  S E L E C T ///////////////////////////////////////
// Recupera el value del option seleccionado de un objeto select.
function getSelectedSelect(slct) {
   return slct.selectedIndex;
}
//   G E T  S E L E C T E D  S E L E C T  M U L T I P L E //////////////////////
// Recupera el value del option seleccionado de un objeto select.
function getSelectedSelectMultiple(slct) {
   var valor = ''; 
   for (var i = 0; slct.options.length-1 >= i; i++){
     if (slct.options[i].selected == true) {
         valor += '' + i + '#'
      } else{
         valor += '*#'
      }
   }
   return valor.substring(0,valor.length-1);
}

//   S E T  S E L E C T ////////////////////////////////////////////////////////
// Inicializa un objeto option a un objeto select.
function setSelect(slct,descrip,cod) {
   var lon = slct.options.length;
   if (lon == 0) {lon = 1;}
   slct.options[lon-1].value = cod;
   slct.options[lon-1].text = descrip;
}


///////////////////////////////////////////////////////////////////////////////////

//   G E T  N U M  A R  C H E C K  B O X E S ///////////////////////////////////
// Recupera el número de checkbox en el array
function getNumArCheckboxes(formu,nombre) {
   var chck = eval(formu +'.'+ nombre +'1');
   var num = 0;
	while (chck!=null) {
		num++;
		chck = eval(formu +'.'+ nombre +num);
	}
   return num-1;
}

//   G E T  N U M  S E L E C T E D  A R  C H E C K  B O X E S //////////////////
// Recupera el número de checkbox señalados en el array
function getNumSelectedArCheckboxes(formu,nombre) {
   var numCheck = 0;
   var count = getNumArCheckboxes(formu,nombre);
	for (var i=1 ; i<=count; i++) {
      chck = eval(formu +'.'+ nombre + i);
      if (chck.checked == true) {
         numCheck++;
      }
	}
	return numCheck;
}

//   S E T  A R  C H E C K  B O X E S //////////////////////////////////////////
// Recupera el número de checkbox señalados en el array
function setArCheckboxes(formu,nombre,num,chcked) {
   chck = eval(formu +'.'+ nombre + num);
   chck.checked = chcked;
}

//   G E T  N U M  S E L E C T E D  A R  C H E C K  B O X E S //////////////////
// Recupera el value del array del checkbox
function getValueArCheckboxes(formu,nombre) {
	count = getNumArCheckboxes(formu,nombre);
	valor = ''; 
	for (var i=1 ; i<=count; i++) {
	   chck = eval(formu +'.'+ nombre + i);
      if (chck.checked == true) {
        valor += '' + chck.value + '#';
      } else {
         valor += '*#';
      }
	}
	return valor.substring(0,valor.length-1);
}


////////////////////////////////////////////////////////////////////////////////

//   G E T  N U L L F O R M ////////////////////////////////////////////////////
// Recupera false si algún objeto de los argumentos es nulo o vacio. Primer argumento
// es el formulario y los restantes son los objetos del formulario.
function getNullForm() {
   var nulo = true;
   for (var i = 1; arguments.length > i ;i++) {
      var obj = eval(arguments[0]+'.'+arguments[i]);
      if (obj.type == 'text') 
         if (trim(obj.value) == '')
            nulo = false;
      if (obj.type == 'hidden') 
         if (trim(obj.value) == '')
            nulo = false;      
      if (obj.type == 'password') 
         if (trim(obj.value) == '')
            nulo = false;     
      if (obj.type == 'radio') 
         if (!obj.checked)
            nulo = false;          
      if (obj.type == 'checkbox') 
         if (!obj.checked)
            nulo = false;    
      if (obj.type == 'file') 
         if (trim(obj.value) == '')
            nulo = false;    
      if (obj.type == 'select-one') 
         if (obj.options[obj.selectedIndex].value == '')
            nulo = false;
      if (obj.type == 'select-multiple') 
         if (obj.selectedIndex == -1)
            nulo = false;
      if (obj.type == 'textarea') 
         if (trim(obj.value) == '')
            nulo = false;     
   }
   if (!nulo)
      alert('Debe rellenar TODOS los campos obligatorios.\n\n * Recuerde que los campos obligatorios son aquellos que se encuentran subrayados. ');
   return nulo;
}


////////////////////////////////////////////////////////////////////////////////

//   C O M P R O B A R   C A M P O S   O B L I G A T O R I O S  ////////////////
// Muestra un mensaje avisando que campo obligatorio no este relleno, y 
// devuelve false. 
// Primer argumento es el formulario y los restantes son pares los objetos del 
// formulario indicando el nombre del campo, el nombre del campo al cual dar el
// foco y el nombre del campo a mostrar en el mensaje.
function comprobarCamposObligatorios() {
   var nulo = true;
   for (var i = 1; arguments.length > i ; i=i+3) {
      var obj = eval(arguments[0] + '.' + arguments[i]);

      if (obj.type == 'text') 
         if (trim(obj.value) == '') {
            nulo = false;
            break;
         }
      if (obj.type == 'hidden') 
         if (trim(obj.value) == '')  {
            nulo = false;      
            break;
         }
      if (obj.type == 'password') 
         if (trim(obj.value) == '') {
            nulo = false;     
            break;
         }
      if (obj.type == 'radio')
         if (!obj.checked) {
            nulo = false;          
            break;
         }
      if (obj.type == 'checkbox') 
         if (!obj.checked) {
            nulo = false;    
            break;
         }
      if (obj.type == 'file')  
         if (trim(obj.value) == '') {
            nulo = false;    
            break;
         }
      if (obj.type == 'select-one')  
         if (obj.options[obj.selectedIndex].value == '') {
            nulo = false;
            break;
         }
      if (obj.type == 'select-multiple')  
         if (obj.selectedIndex == -1) {
            nulo = false;
            break;
         }
      if (obj.type == 'textarea')  
         if (trim(obj.value) == '') {
            nulo = false;     
            break;
         }
   }  // Fin del for

   if (!nulo) {
      alert('Debe rellenar TODOS los campos obligatorios.\n\n * Recuerde que los campos obligatorios son aquellos que se encuentran subrayados.\n\n * El campo "'+arguments[i+2]+'" es obligatorio. Por favor rellenelo. ');
      var obj2 = eval(arguments[0] + '.' + arguments[i+1]);
      obj2.focus();
   }
   return nulo;
}


////////////////////////////////////////////////////////////////////////////////

//   G E T  R E C O M E N D E D  F O R M ///////////////////////////////////////
// Recupera false si algún objeto de los argumentos es nulo o vacio. Primer argumento
// es el formulario y los restantes son los objetos del formulario.
function getRecomendedForm(){
   var nulo = true;
   for (var i = 1; arguments.length > i ;i++) {
      var obj = eval(arguments[0]+'.'+arguments[i]);
      if (obj.type == 'text') 
         if (trim(obj.value) == '')
            nulo = false;
      if (obj.type == 'hidden') 
         if (trim(obj.value) == '')
            nulo = false;      
      if (obj.type == 'password') 
         if (trim(obj.value) == '')
            nulo = false;     
      if (obj.type == 'radio') 
         if (!obj.checked)
            nulo = false;          
      if (obj.type == 'checkbox') 
         if (!obj.checked)
            nulo = false;    
      if (obj.type == 'file') 
         if (trim(obj.value) == '')
            nulo = false;    
      if (obj.type == 'select-one') 
         if (obj.options[obj.selectedIndex].value == '')
            nulo = false;
      if (obj.type == 'select-multiple') 
         if (obj.selectedIndex == -1)
            nulo = false;
      if (obj.type == 'textarea') 
         if (trim(obj.value) == '')
            nulo = false;     
   }
   return nulo;
}






////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   ///////////////////////////////////////////                                  //
//   ///// V A L I D A _ T O D O S _ C A D /////                                  //
//   ///////////////////////////////////////////                                  //
//                                                                                //
//   Definición:                                                                  //
//       Comprueba los campos tipo TEXT y TEXTAREA de un formulario si contiene   //
//       algún carácter usado por el formato CGI.                                 //
//   Entra:                                                                       //
//       formu: Formulario a comprobar si algún campo de tipo TEXT o TEXTAREA     //
//              contiene algún carácter utilizado por el formato CGI.             //
//   Retorna:                                                                     //
//       - Da un alert con la cadena que tiene un carácter usado por el CGI.      //
//       false: Si la cadena contiene algún carácter usado por el formato CGI.    //
//       true: Si la cadena no contiene algún carácter usado por el formato CGI.  //
//                                                                                //
////////////////////////////////////////////////////////////////////////////////////

function Valida_Todos_Cad(formula) {
    for (i = 0 ; i < formula.length ; i++) {
        tempobj = formula.elements[i];
        if (tempobj.type == "text") {
            if (ValidaCad(tempobj.value) == false) {
                alert("La cadena de caracteres " + tempobj.value + " contiene caracteres no válidos.");
                return false;
            }
        }
    }                       
    return true;
}



////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   /////////////////////////////////////                                        //
//   ///// C A M B I A A C E N T O S /////                                        //
//   /////////////////////////////////////                                        //
//                                                                                //
//   Definición:                                                                  //
//       Sustituye caracteres acentuados                                          //
//                                                                                //
//   Entra:                                                                       //
//       cadena: La cadena a sustituir                                            //
//                                                                                //
//   Retorna:                                                                     //
//       cadena: La cadena a sustituida                                           //
//                                                                                //
////////////////////////////////////////////////////////////////////////////////////

function CambiaAcentos ( cadena ) {

  return ncadena;
  
}


////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   ///////////////////////////////////////                                      //
//   ///// C A M B I A C O M I L L A S /////                                      //
//   ///////////////////////////////////////                                      //
//                                                                                //
//   Definición:                                                                  //
//       Sustituye comillas simples y dobles                                      //
//                                                                                //
//   Entra:                                                                       //
//       cadena: La cadena a sustituir                                            //
//                                                                                //
//   Retorna:                                                                     //
//       cadena: La cadena a sustituida                                           //
//                                                                                //
////////////////////////////////////////////////////////////////////////////////////

function CambiaComillas ( cadena ) {
  var ncadena="";
  for ( i = 0; i < cadena.length; i++ ) {
    caracter = cadena.charAt ( i );
    if ( caracter == "'" ) {
      ncadena += '&lsquo;';
    }
    else if ( caracter == '"' ) {
      ncadena += '&quot;';
    }
    else {
      ncadena += caracter;
    }
  }
  
  return ncadena;
  
}




////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   /////////////////////////////////////////                                    //
//   ///// V A L I D A C A D N U M E R O /////                                    //
//   /////////////////////////////////////////                                    //
//                                                                                //
//   Definición:                                                                  //
//        Comprueba una cadena si tiene algún carácter diferente a un numero      //
//   Entra:                                                                       //
//        cadena: Cadena a comprobar si tiene algún carácter diferente a un       //
//                número.                                                         //
//   Retorna:                                                                     //
//        false: Si la cadena contiene algún carácter diferente a un número.      //
//        true: Si la cadena no contiene algún carácter diferente a un número.    //
//                                                                                //
////////////////////////////////////////////////////////////////////////////////////

function ValidaCadNumero (cadena){
    if (cadena != "") {
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
    } else {
        return true;
    }
}

////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   ///////////////////////////////////                                          //
//   ///// S U S T I T U Y E C A D /////                                          //
//   ///////////////////////////////////                                          //
//                                                                                //
//   Definición:                                                                  //
//        Sustituye en una cadena los espacios en blanco en %27                   //
//   Entra:                                                                       //
//        cadena: La cadena a sustituir los espacios en blanco                    //
//    Retorna:                                                                    //
//        ncadena: La cadena sustituida los espacios en %27                       //
//                                                                                //
////////////////////////////////////////////////////////////////////////////////////

function SustituyeCad(cadena) {
	
    if (cadena.length == 0){
        return (cadena);
    }
    ncadena = cadena.replace(cadena, "%27");
    return (ncadena);                          
}

////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   /////////////////////////////////////////////////                            //
//   ///// S U S T I T U Y E _ T O D O S _ C A D /////                            //
//   /////////////////////////////////////////////////                            //
//                                                                                //
//   Definición:                                                                  //
//       Sustituye en los campos tipo TEXT y TEXTAREA de un formulario los        //
//       espacios en blanco por %27.                                              //
//   Entra:                                                                       //
//       formu: Formulario a sustituir los espacios en blanco en los campos TEXT  //
//              y TEXTAREA por %27.                                               //
//   Retorna:                                                                     //
//                                                                                //
////////////////////////////////////////////////////////////////////////////////////

function Sustituye_Todos_Cad(formu) {
    for (i = 0 ; i < formu.length ; i++) {
        tempobj = formu.elements[i];
        if (tempobj.type == "text") {
            tempobj.value = SustituyeCad(tempobj.value)
        }
    }                       
}




////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   ///////////////////////////////////////////                                  //
//   ///// V A L I D A _ T O D O S _ C A D /////                                  //
//   ///////////////////////////////////////////                                  //
//                                                                                //
//   Definición:                                                                  //
//       Comprueba los campos tipo TEXT y TEXTAREA de un formulario si contiene   //
//       algún carácter usado por el formato CGI.                                 //
//   Entra:                                                                       //
//       formu: Formulario a comprobar si algún campo de tipo TEXT o TEXTAREA     //
//              contiene algún carácter utilizado por el formato CGI.             //
//   Retorna:                                                                     //
//       - Da un alert con la cadena que tiene un carácter usado por el CGI.      //
//       false: Si la cadena contiene algún carácter usado por el formato CGI.    //
//       true: Si la cadena no contiene algún carácter usado por el formato CGI.  //
//                                                                                //
////////////////////////////////////////////////////////////////////////////////////

function Valida_buscar() {
    var obj = eval(arguments[0]+'.'+arguments[1]);
    texto = trim(obj.value);
   
     for ( i = 0; i < texto.length; i++ ) {
          caracter = texto.charAt ( i );
          if ( caracter == "'" || caracter == "%" ) {
            alert("La cadena introducida: " + texto + " contiene caracteres no válidos para la búsqueda.");
            return false;
          }     
      }
    return true;
}

// Valida numeros 
function valNumero (campo,descripcion) {
  if (campo != parseFloat( campo )) {  
    alert("Introduzca en el campo " + descripcion+"  un valor numérico");
  }else
   return true;
}

// Valida numeros enteros positivos
function valNumeroInt (campo,descripcion) {
  if (campo != parseInt( campo ) || campo<0 || campo==0 || campo.indexOf(".")!=-1 ) {  
    alert("Introduzca en el campo " + descripcion+"  un valor numérico positivo");
  }else
   return true;
}

// mayorFecha 
function mayorFecha( fecha1 ,fecha2) {

  if ( fecha1 != '' && fecha2 != '' ) {
    fecha1Day = parseFloat(fecha1.substr(0,2));
    fecha1Month = parseFloat(fecha1.substr(3,2)) ;
    fecha1Year = parseFloat(fecha1.substr(6,4));
    fecha2Day = parseFloat(fecha2.substr(0,2));
    fecha2Month = parseFloat(fecha2.substr(3,2)) ;
    fecha2Year = parseFloat(fecha2.substr(6,4));
    date1 = new Date( fecha1Year , fecha1Month , fecha1Day );
    date2 = new Date( fecha2Year , fecha2Month , fecha2Day );
    if ( date1.getTime() > date2.getTime() ) {
      return true;
    }
    else { 
     return false ;}
  }
}

// Función que gestiona el cambio       
function cambio(formulario,campo,texto,fila){

   var tamCampo = eval("document."+formulario+"."+campo+".length");
  
   if(tamCampo == undefined)
     nuevo_valor = eval("document."+formulario+"."+campo);
   else 
      nuevo_valor = eval("document."+formulario+"."+campo+"["+fila+"]");
 
    if(!valNumeroInt(nuevo_valor.value,texto))
       nuevo_valor.value = "0";   
     
}

// Función que limpia un formulario      
function limpiar(){

   for (var i = 1; arguments.length > i ;i++) {
      var obj = eval(arguments[0]+'.'+arguments[i]);
      obj.value = '';
   }
     
}

////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   ///////////////////////////////////////////                                  //
//   /////      FUNCIONES DE CADENA        /////                                  //
//   ///////////////////////////////////////////                                  //
//                                                                                //
//   Definición:                                                                  //
//       Obtiene los n primeros caracteres por la derecha o izquierda.            //
//   Entra:                                                                       //
//       str => cadena origen                                                     //
//       n   => número de caracteres                                              //
//   Retorna:                                                                     //
//       Subcadena .                                                              //
////////////////////////////////////////////////////////////////////////////////////

// Función que devuelve los n primeros caracteres por la izquierda
function obtenerCaracteresIzda(str, n){
	if (n <= 0)
	    return "";
	else if (n > String(str).length)
	    return str;
	else
	    return String(str).substring(0,n);
}

// Función que devuelve los n primeros caracteres por la derecha
function obtenerCaracteresDrcha(str, n){
    if (n <= 0)
       return "";
    else if (n > String(str).length)
       return str;
    else {
       var iLen = String(str).length;
       return String(str).substring(iLen, iLen - n);
    }
}