<!-- items structure. used for 2 menus on this page -->
<!--script language="JavaScript" type="text/javascript" src="javascript/funMenuGIS.js">     /*para awareness*/ </script-->
<link rel="stylesheet" type="text/css" href="css/EstiloJCYLFW.css">

<!-- menu script itself. you should not modify this file -->
<script language="JavaScript" src="javascript/menu.js"></script>

<!-- ** Recorte de codigo JAVASCRIPT para personalizar la colocación del menu ** -->
<script language="JavaScript" type="text/javascript">


<%
  // Serializamos el menu SEGU para que lo entienda TigraMenu
  String menuActualizado = null;
  int numMenusUsuario = 0;
  try {
     java.util.ArrayList FuncionesMenu = (java.util.ArrayList)request.getSession().getAttribute("JCYLArrayFunciones");
     menuActualizado = es.jcyl.framework.utiles.JCYLSeguTigra.serializarATigraMenu2_0(FuncionesMenu, "main");
     numMenusUsuario = es.jcyl.framework.utiles.JCYLSeguTigra.getNMenusPrimerNivel(FuncionesMenu);  
  } catch (Exception e) {
      
  }
  
%>
 
 var MENU_ITEMS = <%=menuActualizado%>;
  
  // Variable que establece el numero de menus de primer nivel que existe
  var numMenusPri = <%=numMenusUsuario%>;

  var anchoMenu = 774;  // Espacio donde repartir los elementos de menu. Preferentemente un numero par
  
  if (isNaN(anchoVentana))
    var anchoVentana =document.body.clientWidth;
  if (isNaN(postleft))  
    var postleft = (anchoVentana/2) - (anchoMenu/2);
  // Posiciones relativas al menu
  var leftCelda = postleft;   // Coordenada x del origen de la fila de menus
  var topCelda = 115;   // Coordenada y del origen de la fila de menus
  var altoCelda = 24;   // Altura en pixels de los elementos de menu
  if (numMenusPri < 4)
     numMenusPri = 4;  // Valor ha de ser de 4 a N para su ajuste correcto en la siguiente instruccion
  var anchoCelda = parseInt((anchoMenu -(numMenusPri+4))/numMenusPri);

   
var MENU_POS = [{
	// item sizes
	'height': altoCelda,
	'width': anchoCelda,
	// menu block offset from the origin:
	//	for root level origin is upper left corner of the page
	//	for other levels origin is upper left corner of parent item
	'block_top': topCelda,
	'block_left': leftCelda,
	// offsets between items of the same level
	'top': 0,
	'left': anchoCelda + 3,
	// time in milliseconds before menu is hidden after cursor has gone out
	// of any items
	'hide_delay': 200,
	'expd_delay': 200,
	'css' : {
		'outer' : ['m0l0oout', 'm0l0oover'],
		'inner' : ['m0l0iout', 'm0l0iover']
	}
},{
	'height': altoCelda,
	'width': anchoCelda,
	'block_top': 25,
	'block_left': 0,
	'top': 23,
	'left': 0,
	'css' : {
		'outer' : ['m0l1oout', 'm0l1oover'],
		'inner' : ['m0l1iout', 'm0l1iover']
	}
},{
	'height': altoCelda,
  'block_top': 5,
  'width': anchoCelda,
	'block_left': anchoCelda - 10
}
]
   //new menu (MENU_ITEMS, MENU_POS1, MENU_STYLES1);
   new menu (MENU_ITEMS, MENU_POS);
</script>
<!-- ** Fin Colocaci?n del men? *** -->

