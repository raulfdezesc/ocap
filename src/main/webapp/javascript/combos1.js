//
//
// Fichero :              combos1.js 
// Descripción:           Contiene funciones para el tratamiento de listbox multiples
// Fecha de inicio:       17/07/2000
//
// Funciones que contiene:
//        . combos_anadir (origen, destino): incluye en el combo destino los elemento seleccionados
//                        en el combo origen. Ordenando el contenido del combo destino por el
//                        atributo text de sus opciones
//        . combos_borrar (origen): elimina del combo las opciones seleccionadas, ordenando el 
//                        contenido del combo por el atributo text de sus opciones
//        . combos_bump (combo): Elimina de un desplegable las opciones "vacías"
//        . combos_sort (combo): ordena las opciones de un desplegable por su atributo text 
//                        alfabéticamente de forma ascendente
//


var NS = (navigator.appName == "Netscape");
var IE4 = (navigator.appName == "Microsoft Internet Explorer");

    ordenar = "1"; // Por defecto se ordena el desplegable tras añadir o borrar elementos
    texto = "1";         // Por defecto se pasa al slct_destino el texto

//
// Descripción:
//     función que incluye en el combo destino los elemento seleccionados en el combo
//     origen. Ordenando el contenido del combo destino por el atributo text de sus opciones
// Parámetros:
//     slct_origen: desplegable del que se copian las opciones seleccionadas
//     slct_destino: desplegable al que se copian las opciones 
function combos_anadir(slct_origen, slct_destino,elcombo)
{
    var existe;
    var idx = -1;
    for (j=0;j<slct_origen.length;j++)
    {
    	if(slct_origen[j].type=="checkbox" && slct_origen[j].checked){idx=1}
    }
    if ( idx == -1 ) return;

    for (j=0;j<slct_origen.length;j++)
    {
        if(slct_origen[j].type=="checkbox" && slct_origen[j].checked && slct_origen[j].value != "") 
        {
            existe = 0;
            for (i=0;i<slct_destino.length;i++)
            {
                if (slct_destino.options[i].value == slct_origen[j].value)
                    existe = 1;
            }
            if (existe == 0)
            {
                if (texto == "1")
                {
                    elcombo.nueva_opcion(slct_origen[j].name, slct_origen[j].value)
                }
                else
                {
                    elcombo.nueva_opcion(slct_origen[j].value, +slct_origen[j].value)
                }
            }
        }
    }
    if (ordenar == "1")
    {    combos_sort(slct_destino);    }
    texto = "1";                // Se vuelve a establecer por defecto que se pasa el texto de un combo a otro
}


//
// Descripción:
//   modificacion de la  función que incluye en el combo destino los elemento seleccionados en el combo
//     origen. Ordenando el contenido del combo destino por el atributo text de sus opciones
// Parámetros:
//     slct_origen: desplegable del que se copian las opciones seleccionadas
//     slct_destino: desplegable al que se copian las opciones 

function combos_anadir2(slctCodigos, slctDescripciones, slct_destino, elcombo)
{
    var existe;
    for (j=0;j<slctCodigos.length;j++)
    {
        if(slctCodigos[j] != "") 
        {
            existe = 0;
            for (i=0;i<slct_destino.length;i++)
            {
                if (slct_destino.options[i].value == slctCodigos[j])
                    existe = 1;
            }
            if (existe == 0)
            {
                if (texto == "1")
                {
                    elcombo.nueva_opcion(slctDescripciones[j], slctCodigos[j]);
                }
                else
                {
                    elcombo.nueva_opcion(slctCodigos[j], +slctCodigos[j]);
                }
            }
        }
    }
    if (ordenar == "1")
    {    combos_sort(slct_destino);    }
    texto = "1";                // Se vuelve a establecer por defecto que se pasa el texto de un combo a otro
}


//
//Descripción:
//        función que elimina del combo los elementos seleccionados, ordenando el contenido 
//                        del combo por el atributo text de sus opciones.
//        Se realizará de un modo u otro según el navegador
//Parámetros:
//        slct: desplegable del que se eliminan las opciones seleccionadas
//
function combos_borrar(slct)
{
    if (NS)
        combos_borrarNS(slct);
    else
        combos_borrarIE(slct);
}



//
//Descripción:
//        función que elimina del combo los elementos seleccionados, ordenando el contenido 
//                        del combo por el atributo text de sus opciones
// Parámetros:
//        slct: desplegable del que se eliminan las opciones seleccionadas
//
// Deletes from the destination list.
function combos_borrarIE(slct) 

{
    var destList  = slct;
    var len = destList.options.length;
    var con = 0;
    var siz = destList.size;

    for(var i = (len-1); i >= 0; i--) {
        if ((destList.options[i] != null) && (destList.options[i].selected == true)) 
        {
            con = con + 1;
        }
    }
    if (con > siz)
    {
        destList.size=1;
        alert("Elementos desasignados");
    }
    for(var i = (len-1); i >= 0; i--) {
        if ((destList.options[i] != null) && (destList.options[i].selected == true)) 
        {
            destList.options[i] = null;
        }
    }
    if (con > siz)
    {
        destList.size=siz;
    }
    if (ordenar == "1")
        combos_sort(destList);

    destList.selectedIndex = -1;

}

//
// Descripción:
//        función que elimina del combo los elementos seleccionados, ordenando el contenido 
//                        del combo por el atributo text de sus opciones
// Parámetros:
//        slct: desplegable del que se eliminan las opciones seleccionadas
//
function combos_borrarNS(slct)
{
        idx = slct.selectedIndex;
        if ( idx == -1 )
                 return;

       for (j=0;j<slct.length;j++)
        {
           if (slct.options[j].selected && slct.options[j].value != "") 
           {
                slct.options[j].text="";
                slct.options[j].value="";
           }
        }
        combos_bump(slct);

        if (ordenar == "1")
            combos_sort(slct);

        slct.selectedIndex = -1;
}

function combos_borrarTodos(slct)
{
    if (NS)
        combos_borrarTodosNS(slct);
    else
        combos_borrarTodosIE(slct);
}

function combos_borrarTodosIE(slct){
   var destList  = slct;
   var len = destList.options.length;
   var siz = destList.size;
   for(var i = (len-1); i >= 0; i--) {
        if (destList.options[i] != null)
        {
            destList.options[i] = null;
        }
    }
}

function combos_borrarTodosNS(slct)
{
       for (j=0;j<slct.length;j++)
        {
           slct.options[j].text="";
           slct.options[j].value="";
           
        }
        
}
//
// Descripción:
//        función que elimina de un desplegable las opciones "vacías"
// Parámetros:
//        slct: desplegable del que se eliminan las opciones 
//
function combos_bump(slct)  
{
    for(var i=0; i<slct.options.length; i++) 
    {
        if(slct.options[i].value == "")  
        {
            for(var j=i; j<slct.options.length-1; j++)  
            {
                slct.options[j].value = slct.options[j+1].value;
                slct.options[j].text = slct.options[j+1].text;
            }
            var ln = i;
            break;
        }
    }
    if(ln < slct.options.length)  
    {
        slct.options.length -= 1;
        combos_bump(slct);
   }
}



//
// Descripción:
//        función que ordena las opciones de un desplegable por su atributo text 
//        alfabéticamente de forma ascendente
// Parámetros:
//        slct: desplegable del que se ordenan las opciones alfabéticamente de forma ascendente
//
function combos_sort(slct)  
{
    var i;
    var temp_optsVal = new Array();
    var temp_optsTxt = new Array();
    var texto_tmp;
    var valor_tmp;

    for(i=0; i<slct.options.length; i++)  
    {
        temp_optsVal[i] = slct.options[i].value;
        temp_optsTxt[i] = slct.options[i].text;
    }

    for(var x=0; x<temp_optsTxt.length-1; x++)  
    {
        for(var y=(x+1); y<temp_optsTxt.length; y++)  
        {
            if(temp_optsTxt[x] > temp_optsTxt[y])  
            {
                texto_tmp = temp_optsTxt[x];
                valor_tmp = temp_optsVal[x];
                temp_optsTxt[x] = temp_optsTxt[y];
                temp_optsVal[x] = temp_optsVal[y];
                temp_optsTxt[y] = texto_tmp;
                temp_optsVal[y] = valor_tmp;
            }
        }
    } 

    for(i=0; i<slct.length; i++)  
    {
        slct.options[i].value = temp_optsVal[i];
        slct.options[i].text = temp_optsTxt[i];
    }

}

function objeto_combo(par){
    var objeto = eval(par);
    //objeto.nombre
    objeto.nueva_opcion = nueva_opcion;
    return objeto;
}


function nueva_opcion(texto,valor){
    oNewOption = new Option(texto, valor);
    this.options[this.length]=oNewOption;
}

//Nueva version porque la otra no funciona en firefox
function nueva_opcion(campo,texto,valor){
   if ( campo.options.length == 0 )  {
		campo.options[0] = new Option(texto);
		campo.options[0].value = valor;
	} else {
		campo.options[campo.options.length] = new Option(texto);
		campo.options[campo.options.length-1].value = valor;
   }
}

function valorCombo(slct){
	var longitud = slct.length;
	var cad = "";
	if(longitud>0) {
		for(i=0;i<longitud;i++) {
			cad = cad + slct.options[i].value + "#";
		}
	}
	cad = cad.substring(0,cad.length-1);
	return cad;   
}

function textoCombo(slct){
	var longitud = slct.length;
	var cad = "";
	if(longitud>0) {
		for(i=0;i<longitud;i++) {
			cad = cad + slct.options[i].text + "#";
		}
	}
	cad = cad.substring(0,cad.length-1);
	return cad;   
}

function comprueba_seleccion(slct)
{
  idx = slct.selectedIndex;
  if ( idx == -1 )
    return false;
  
  con=0;
  for (j=0;j<slct.length;j++)
  {
    if (slct.options[j].selected) 
    {
      con++
    }
    if (con>1){alert("Seleccione un \u00fanico elemento para modificar");return (false)}
  }
  return (true);
}
  	