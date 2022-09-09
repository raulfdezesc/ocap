////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
//   ///////////////////////                                                      //
//   ///// L A N Z A R /////                                                      //
//   ///////////////////////                                                      //
//                                                                                //
//   Definición:                                                                  //
//       abre una ventana con los parámetros pasados                              //
//   Entra:                                                                       //
//       local: La URL de la ventana a emerger                                    //
//       nvent: El nombre de la ventana a emerger                                 //
//       ancho: El width de la ventana a emerger                                  //
//       alto: El height de la ventana a emerger                                  //
//       pX: La posición left de la ventana a emerger                             //
//       pY: La posición top de la ventana a emerger                              //
//       res: si vale 1 es resizable si vale 0 no es resizable                    //
//       scro: si vale 1 tiene scrollbars si vale 0 no tiene scrollbars           //
//   Retorna:                                                                     //
//       valor: el valor que tenga el atributo value de la opción selected        //
//                                                                                //
////////////////////////////////////////////////////////////////////////////////////

function lanzar(local,nvent,ancho,alto,pX,pY,res,scro) {
    var str;
    str = "width="+ ancho + ",height=" + alto  + ",left=" + pX  + ",top=" + pY + ",menubar=0,toolbar=0,directories=0,location=0,status=0";

    if (res==1){
        str = str + "resizable=1";
    } else {
        str = str + "resizable=0";
    }
    
    if (scro==1){
        str = str + ",scrollbars=1";
    } else {
        str = str + ",scrollbars=0";
    }

    eval(nvent + "=window.open ('"+local+"','"+nvent+"','"+str+"')"); 
    eval(nvent + ".focus()");
}

function llamaVentanaPadre() {
  ahora = new Date();
  ventana = "padre" + parseInt( Math.random()*1000 ) + ahora.getDate() + ahora.getMonth() + ahora.getFullYear();
  
  return ventana;
}

