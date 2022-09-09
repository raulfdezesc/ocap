/* Fichero para mostrar un calendario en una ventana flotante
*/
    /* definimos los dias festivos por el indice en la matriz: sabado y domingo, y les asignamos un color de fondo a sus celdas */
    var festivos = [5,6];
    var festivosColor = "#99b7d5";

    /* definimos el tamaño y familia de las fuentes */
    var familia_fuente = "Arial, Verdana";
    var size_fuente = 3;

    /* definimos el path imagenes */
    var pathImg = "../img/";

    /* declaramos las variables globales ahora, que va a contener la fecha del sistema del usuario 
    * y calculo, que usaremos luego */
    var ahora = new Date();
    var calculo;
    var miFormulario;
    
    /* averiguamos el navegador del usuario y lo asignamos a una veriable especifica */
    if (document.layers)
        isNav = true;
    else if (document.all)
        isIE = true;

    /* declaramos la matriz de meses del Calendario, como una propiedad del objeto Calendario */
    Calendario.Meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];

    /* definimos los dias de cada mes para año normal */
    Calendario.DiasMes = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

    /* definimos los dias de cada mes para año bisiesto */
    Calendario.BisiestoDiasMes = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

    /* Funcion principal de definicion del objeto Calendario */
    function Calendario(p_item, p_WinCal, mes, anyo, formato)
    {
      /* si no se elige fecha, no hace nada  */
      if ((mes == null) && (anyo == null))
         return;
	    if (p_WinCal == null)
   	     this.gWinCal = calculo;
	    else
		     this.gWinCal = p_WinCal;

	    if (mes == null){
	        this.dameMes = null;
            this.dameNumeroMes = null;
            this.dameAnyo = true;
      } else {
         this.dameMes = Calendario.get_month(mes);
         this.dameNumeroMes = new Number(mes);
         this.dameAnyo = false;
      }

	    /* Configuramos el formato del calendario */
      this.gYear = anyo;
	    this.gFormat = formato;
	    this.gBGColor = "red";
	    this.gFGColor = "black";
	    this.gTextColor = "black";
	    this.gHeaderColor = "black";
	    this.gReturnp_item = p_item;
    }

    Calendario.get_month = Calendario_get_month;
    Calendario.get_diasdelmes = Calendario_get_diasdelmes;
    Calendario.calcula_mes_anyo = Calendario_calcula_mes_anyo;
    Calendario.print = Calendario_print;

    /* Obtenemos el numero del mes */
    function Calendario_get_month(monthNo)
    {
	    return Calendario.Meses[monthNo];
    }

    /* Vemos si el año es bisiesto o no, para asignar los dias correspondientes a febrero */
    function Calendario_get_diasdelmes(monthNo, anyo)
    {
        if ((anyo % 4) == 0) {
           if ((anyo % 100) == 0 && (anyo % 400) != 0)
               return Calendario.DiasMes[monthNo];
		       return Calendario.BisiestoDiasMes[monthNo];
	      }
        else
		       return Calendario.DiasMes[monthNo];
    }

    /* Funcion para incrementar o decrementar 1 mes o 1 año al pulsar las dobles flechas
     * la variable incremento establece el aumento o disminucion en 1 unidad (se puede cambiar) */
    function Calendario_calcula_mes_anyo(mes, anyo, incremento)
    {
	    var ret_arr = new Array();
	    if (incremento == -1) {
		    /**
         * hacia atras
        */
	        if (mes == 0) {
             ret_arr[0] = 11;
		    	   ret_arr[1] = parseInt(anyo) - 1;
	    	  }
          else {
	    		   ret_arr[0] = parseInt(mes) - 1;
	    		   ret_arr[1] = parseInt(anyo);
		      }
	    }
      /* hacia adelante */
      else if (incremento == 1){
		     if (mes == 11) {
			      ret_arr[0] = 0;
    	      ret_arr[1] = parseInt(anyo) + 1;
    	   }
    		 else{
			      ret_arr[0] = parseInt(mes) + 1;
    		    ret_arr[1] = parseInt(anyo);
    		 }
    	}
	    return ret_arr;
    }

    /* Funcion para imprimir el calendario */
    function Calendario_print()
    {
	    calculo.print();
    }

    /* Añadimos propiedades al objeto Calendario mediante el metodo prototype */
    Calendario.prototype.getMonthlyCalendarioCode = function()
    {
	    var vCode = "";
	    var vHeader_Code = "";
	    var vData_Code = "";

	    /* dibujamos la tabla del calendario en la ventana flotante, Se rellena con filas que definimos luego */
	    vCode = vCode + "<TABLE width='100%' BORDER=0 cellpadding='0' cellspacing=0 BGCOLOR=\"" + this.gBGColor + "\">";
	    vHeader_Code = this.cal_header(); 
	    vData_Code = this.cal_data();
	    vCode = vCode + vHeader_Code + vData_Code;
	    vCode = vCode + "</TABLE>";
	    return vCode;
    }

    Calendario.prototype.show = function()
    {
      var strIframe = (window.name=='main')  ? '.main' : ''; // determina si la ventana padre (yo) tiene o no iframe llamado main
	   var vCode = "";
    	this.gWinCal.document.open();
      
	    /* definimos la cadena que nos pintara la pagina dentro de la ventana flotante */
	    this.wwrite("<html>");
	    this.wwrite("<head><title>Calendario</title>");
    	this.wwrite("<style type='text/css'>\n<!--");
      this.wwriteA(".textoTituloGris {");
 	    this.wwriteA(" FONT-SIZE: 8pt; COLOR: #000000; FONT-FAMILY: arial, verdana, helvetica, geneva, sans-serif");
      this.wwrite("}");
      this.wwriteA(".tablaFondoConsej {");
	    this.wwriteA(" BACKGROUND-COLOR: #FFFFFF");
      this.wwrite("}");      
      this.wwriteA(".celdaMenu {");
     	this.wwriteA(" FONT-WEIGHT: normal; FONT-SIZE: 11px; COLOR: #FFFFFF; FONT-FAMILY: arial, verdana, helvetica, geneva, sans-serif; BACKGROUND-COLOR: #99b7d5; TEXT-DECORATION: none");
      this.wwrite("}");
  	  this.wwrite("-->\n</style>");      
    	this.wwrite("<script type='text/javascript'>\n<!--");
      this.wwrite("function devuelveValor(nombreForm,nombreCampo,valor){");
      this.wwrite("if (nombreForm==''){");
      this.wwrite("    eval('top.opener.main.document.'+nombreCampo+'.value='+\"valor\");"); 
      this.wwrite("    }");
      this.wwrite("else {");
      this.wwrite("   if (eval('top.opener.document.forms[\"'+nombreForm+'\"]')!=undefined) {");
      this.wwrite("     var aux2 = nombreCampo; ");
      this.wwrite("   if (aux2.indexOf('[') != -1) {");
      this.wwrite("     var auxIndice = aux2.substring(aux2.indexOf('['));  ");
      this.wwrite("     var auxCampo = aux2.substring(0,aux2.indexOf('['));  ");
      this.wwrite("     objeto=eval('top.opener.document.forms[\"'+nombreForm+'\"][\"'+auxCampo+'\"]['+auxIndice+']');");
      this.wwrite("  } else { " );
      this.wwrite("      objeto=eval('top.opener.document.forms[\"'+nombreForm+'\"][\"'+nombreCampo+'\"]');");
      this.wwrite("  } ");
      this.wwrite("      if (objeto!=null) objeto.value=valor; ");
      this.wwrite("   }");
      this.wwrite("   else if (eval('top.opener.main.document.forms[\"'+nombreForm+'\"]')!=undefined) {");
      this.wwrite("   objeto=eval('top.opener.main.document.forms[\"'+nombreForm+'\"][\"'+nombreCampo+'\"]');");
      this.wwrite("   if (objeto!=null) objeto.value=valor;");
      this.wwrite("   }");
      this.wwrite("}");
      this.wwrite("}");
  	  this.wwrite("//-->\n</script>");
	    this.wwrite("</head>");
	    this.wwrite("<body onload=\"if (window.opener.setDirtyFlag) window.onunload=new function() { window.opener.setDirtyFlag() }\"" +
 		              "link=\"" + this.gLinkColor + "\" " +
 		              "vlink=\"" + this.gLinkColor + "\" " +
		              "alink=\"" + this.gLinkColor + "\" " +
 		              "text=\"" + this.gTextColor + "\">");
	    this.wwrite("<FONT FACE='" + familia_fuente + "' SIZE=2>");

	    var prevMMYYYY = Calendario.calcula_mes_anyo(this.dameNumeroMes, this.gYear, -1);
    	var prevMM = prevMMYYYY[0];
    	var prevYYYY = prevMMYYYY[1];
	    var nextMMYYYY = Calendario.calcula_mes_anyo(this.dameNumeroMes, this.gYear, 1);
	    var nextMM = nextMMYYYY[0];
	    var nextYYYY = nextMMYYYY[1];

      this.wwrite("<form>");

      this.wwrite("<TABLE WIDTH='100%' BORDER=0 CELLSPACING=0 CELLPADDING=0><tr><td colspan='5' height='1' bgcolor='#FFFFFF'><img src='" + pathImg +
          "pix.gif' width=1 height=1 border=0></td></tr><TR><TD class='celdaMenu' ALIGN=center>");
      this.wwrite("<A HREF=\"" +
          "javascript:window.opener"+strIframe+".Build(" +
          "'" + this.gReturnp_item + "', '" + this.dameNumeroMes + "', '" + (parseInt(this.gYear)-1) + "', '" + this.gFormat + "'" +
		      ");" +
		      "\" title='Año previo'><font color='#FFFFFF'><<<\/font><\/A></TD><TD class='celdaMenu' ALIGN=center>");
      this.wwrite("<A HREF=\"" +
		      "javascript:window.opener"+strIframe+".Build(" +
		      "'" + this.gReturnp_item + "', '" + prevMM + "', '" + prevYYYY + "', '" + this.gFormat + "'" +
		      ");" +
		      "\" title='Mes previo'><font color='#FFFFFF'><<\/font><\/A></TD><TD class='celdaMenu' ALIGN=center >");

		 this.wwriteA("<b><font color='#FFFFFF'><select name=\"mes\" class='textoTituloGris' onChange=\"window.opener"+strIframe+".Build('" 
		  + this.gReturnp_item + "', this.selectedIndex, '" + this.gYear + "', '" + this.gFormat + "');\">");

		 for (x=0; x<Calendario.Meses.length; x++) 
			{ 
		  	if(Calendario.Meses[x]!=this.dameMes)
			  	this.wwriteA("<option  value=\"" +  Calendario.Meses[x] +  "\">" +  Calendario.Meses[x] +  "</option> ");
			else
			  this.wwriteA("<option  value=\"" +  Calendario.Meses[x] +  "\" selected=\"true\">" +  Calendario.Meses[x] +  "</option> ");
			
			} 

		  this.wwriteA("</select> ");
      this.wwriteA("<INPUT TYPE='TEXT' NAME='year' class='textoTituloGris' VALUE='" + this.gYear + "' SIZE='4' MAXLENGTH='4'");
   	  if (navigator.appName == 'Netscape') {
    	  	this.wwriteA(" OnKeyDown="+"\""+"javascript:if(event.which==13) window.opener"+strIframe+".Build("+ "'" + this.gReturnp_item + "', '" + this.dameNumeroMes + "', this.value , '" + this.gFormat + "');"+"\""+">");
      } else {
		      this.wwriteA(" OnKeyDown="+"\""+"javascript:if(event.keyCode==13) window.opener"+strIframe+".Build("+ "'" + this.gReturnp_item + "', '" +  this.dameNumeroMes + "', this.value , '" + this.gFormat + "');" + "\""+">");
   	  }
      this.wwrite("<\/font></b></TD><TD class='celdaMenu' ALIGN=center>");
      this.wwrite("<A HREF=\"" +
		              "javascript:window.opener"+strIframe+".Build(" +
		              "'" + this.gReturnp_item + "', '" + nextMM + "', '" + nextYYYY + "', '" + this.gFormat + "'" +
		              ");" +
		              "\" title='Mes posterior'><font color='#FFFFFF'>><\/font><\/A></TD><TD class='celdaMenu' ALIGN=center>");
	    this.wwrite("<A HREF=\"" +
		              "javascript:window.opener"+strIframe+".Build(" + 
		              "'" + this.gReturnp_item + "', '" + this.dameNumeroMes + "', '" + (parseInt(this.gYear)+1) + "', '" + this.gFormat + "'" +
		              ");" +
		              "\" title='Año posterior'><font color='#FFFFFF'>>><\/font><\/A></TD></TR><tr><td colspan='5' height='1' bgcolor='#FFFFFF'><img src='" + pathImg +
                  "pix.gif' width=1 height=1 border=0></td></tr></TABLE>");

      vCode = this.getMonthlyCalendarioCode();
	    this.wwrite(vCode);

      this.wwrite("</form>");

      this.wwrite("</font></body></html>");
	    this.gWinCal.document.close();
    }

    /* Funcion que define las propiedades de la ventana flotante, escribe dentro el codigo inicial y la abre */
    Calendario.prototype.showY = function() 
    {
      var vCode = "";
    	var i;
    	var vr, vc, vx, vy;
    	var vxf = 285;		
    	var vyf = 200;		
    	var vxm = 10;		
    	var vym;
    	if (isIE)	
            vym = 75;
    	else if (isNav)	
            vym = 25;	
    	this.gWinCal.document.open();
    	this.wwrite("<html>");
    	this.wwrite("<head><title>Calendario</title>");
    	this.wwrite("<style type='text/css'>\n<!--");
     	for (i=0; i<12; i++) {
        vc = i % 3;
    		if (i>=0 && i<= 2)	vr = 0;
    		if (i>=3 && i<= 5)	vr = 1;
    		if (i>=6 && i<= 8)	vr = 2;
    		if (i>=9 && i<= 11)	vr = 3;		
    		vx = parseInt(vxf * vc) + vxm;
    		vy = parseInt(vyf * vr) + vym;
    		this.wwrite(".lclass" + i + " {position:absolute;top:" + vy + ";left:" + vx + ";}");
    	}
	    this.wwrite("-->\n</style>");
      this.wwrite("<script type='text/javascript' language='JavaScript' src='javascript/devuelveFechaCalendario.js'></script>");                  
    	this.wwrite("</head>");
    	this.wwrite("<body " + 
		              "link=\"" + this.gLinkColor + "\" " + 
		              "vlink=\"" + this.gLinkColor + "\" " +
		              "alink=\"" + this.gLinkColor + "\" " +
		              "text=\"" + this.gTextColor + "\">");
    	this.wwrite("<FONT FACE='" + familia_fuente + "' SIZE=1><B>");
    	this.wwrite("Year: " + this.gYear);
    	this.wwrite("</B><BR>");
	    var prevYYYY = parseInt(this.gYear) - 1;
    	var nextYYYY = parseInt(this.gYear) + 1;	
    	this.wwrite("<TABLE WIDTH='100%' BORDER=1 CELLSPACING=0 CELLPADDING=0><TR><TD ALIGN=center>");
    	this.wwrite("[<A HREF=\"" +
		              "javascript:window.opener.main.Build(" + 
		              "'" + this.gReturnp_item + "', null, '" + prevYYYY + "', '" + this.gFormat + "'" +
		              ");" +
		              "\" alt='Prev Year'><<<\/A>]</TD><TD ALIGN=center>");
	    this.wwrite("[<A HREF=\"javascript:window.print();\">Print</A>]</TD><TD ALIGN=center>");
    	this.wwrite("[<A HREF=\"" +
    	            "javascript:window.opener.main.Build(" + 
		              "'" + this.gReturnp_item + "', null, '" + nextYYYY + "', '" + this.gFormat + "'" +
		              ");" +
		              "\">>><\/A>]</TD></TABLE>zzz");
    	var j;

    	for (i=11; i>=0; i--) {
    		if (isIE)
    			this.wwrite("<DIV ID=\"layer" + i + "\" CLASS=\"lclass" + i + "\">");
    		else if (isNav)
    			this.wwrite("<LAYER ID=\"layer" + i + "\" CLASS=\"lclass" + i + "\">");
    		this.dameNumeroMes = i;
    		this.dameMes = Calendario.get_month(this.dameNumeroMes); 
    		vCode = this.getMonthlyCalendarioCode();
    		this.wwrite(this.dameMes + "/" + this.gYear + "<BR>");
    		this.wwrite(vCode);
    		if (isIE)
     			this.wwrite("</DIV>");
    		else if (isNav)
    			this.wwrite("</LAYER>");
    	}
    	this.wwrite("</font><BR></body></html>");
    	this.gWinCal.document.close();
    }

    /* Funciones que pintan el string de las filas y celdas */
    Calendario.prototype.wwrite = function(wtext) 
    {
	    this.gWinCal.document.writeln(wtext);
    }

    Calendario.prototype.wwriteA = function(wtext) 
    {
	    this.gWinCal.document.write(wtext);
    }

    /* Funcion que crea el string con las diferentes filas y celdas del calendario en la ventana flotante */
    Calendario.prototype.cal_header = function() 
    {
	    var vCode = "";	
	    vCode = vCode + "</TR><tr><td class='textoTituloGris' colspan='7' height='1' align='right'><A HREF='#' onClick=\"javascript:devuelveValor('"+ miFormulario + "','" + this.gReturnp_item + "','');window.close();\"><FONT COLOR='#000000'>Borrar</font>" + "</A></td></tr>";
	    vCode = vCode + "<tr><td colspan='7' height='1' bgcolor='#FFFFFF'><img src='" + pathImg +"pix.gif' width=1 height=1 border=0></td></tr>"
      vCode = vCode + "<TR>";
    	vCode = vCode + "<TD class='celdaMenu' WIDTH='14%' ALIGN='center'><FONT COLOR='#FFFFFF'><B>Lun</B></FONT></TD>";
    	vCode = vCode + "<TD class='celdaMenu' WIDTH='14%' ALIGN='center'><FONT COLOR='#FFFFFF'><B>Mar</B></FONT></TD>";
    	vCode = vCode + "<TD class='celdaMenu' WIDTH='14%' ALIGN='center'><FONT COLOR='#FFFFFF'><B>Mi&eacute;</B></FONT></TD>";
    	vCode = vCode + "<TD class='celdaMenu' WIDTH='14%' ALIGN='center'><FONT COLOR='#FFFFFF'><B>Jue</B></FONT></TD>";
    	vCode = vCode + "<TD class='celdaMenu' WIDTH='14%' ALIGN='center'><FONT COLOR='#FFFFFF'><B>Vie</B></FONT></TD>";
    	vCode = vCode + "<TD class='celdaMenu' WIDTH='16%' ALIGN='center'><FONT COLOR='#FFFFFF'><B>S&aacute;b</B></FONT></TD>";
      vCode = vCode + "<TD class='celdaMenu' WIDTH='14%' ALIGN='center'><FONT COLOR='#FFFFFF'><B>Dom</B></FONT></TD>";
      vCode = vCode + "</TR>";	
      vCode = vCode + "<tr><td colspan='7' height='1' bgcolor='#FFFFFF'><img src='" + pathImg +"pix.gif' width=1 height=1 border=0></td></tr>"
    	return vCode;
    }

    /* Funcion que calcula las partes de la fecha actual y crea las celdas con los dias */
    Calendario.prototype.cal_data = function()
    {
	    var vDate = new Date();
    	vDate.setDate(1);
    	vDate.setMonth(this.dameNumeroMes);
    	vDate.setFullYear(this.gYear);
    	var vFirstDay=vDate.getDay()-1;
    	var vDay=1;
    	var vLastDay=Calendario.get_diasdelmes(this.dameNumeroMes, this.gYear);
    	var vOnLastDay=0;
    	var vCode = "";
    	vCode = vCode + "<TR ALIGN= center >"; 
        /* primera semana del mes */
        /* si el primer dia de la semana cae en domingo */
        if(vFirstDay==-1){
            for (i=0; i<6; i++) {
		           vCode = vCode + "<TD WIDTH='14%'" + this.write_festivos_string(i) + "><FONT SIZE='2' FACE='" + familia_fuente + "'>&nbsp;</FONT></TD>";
	          }
            for (j=6; j<7; j++) {
    		       vCode = vCode + "<TD WIDTH='14%'" + this.write_festivos_string(j) + "><FONT SIZE='2' FACE='" + familia_fuente + "'>" + 
 			             "<A HREF='#' " + "onClick=\"javascript:devuelveValor('"+ miFormulario + "','" + this.gReturnp_item + "','" + this.format_data(vDay) + "');window.close();\">"+
			             this.format_day(vDay) + 
			             "</A>" + 
			             "</FONT></TD>";
    		       vDay=vDay+1;
    	      }
        }
        /* si no cae en domingo */
        else {
           for (i=0; i<vFirstDay; i++) {
		          vCode = vCode + "<TD WIDTH='14%'" + this.write_festivos_string(i) + " class='textoTituloGris'>&nbsp;</TD>";
	         }
     	     for (j=vFirstDay; j<7; j++) {
     		      vCode = vCode + "<TD WIDTH='14%'" + this.write_festivos_string(j) + " class='textoTituloGris'>" + 
			         "<A HREF='#' " + 
               "onClick=\"javascript:devuelveValor('"+ miFormulario + "','" + this.gReturnp_item + "','" + this.format_data(vDay) + "');window.close();\">" +
			         this.format_day(vDay) + 
			         "</A>" + 
			         "</TD>";
    		       vDay=vDay+1;
    	     }
        }

    	  vCode = vCode + "</TR>";
    	  for (k=2; k<7; k++) {
 		       vCode = vCode + "<TR ALIGN= center BGCOLOR='#FFFFFF'>";
           for (j=0; j<7; j++) {
			         vCode = vCode + "<TD WIDTH='14%'" + this.write_festivos_string(j) + " class='textoTituloGris'>" + 
               "<A HREF='#' class='textoTituloGris'" + 
               "onClick=\"javascript:devuelveValor('"+ miFormulario + "','" + this.gReturnp_item + "','" + this.format_data(vDay)+"');window.close();\">"+
	    		     this.format_day(vDay) + 
		    	     "</A>" + 
			         "</TD>";
			         vDay=vDay + 1;
        		   if (vDay > vLastDay) {
				           vOnLastDay = 1;
        			     break;
	             }
		       }
		       if (j == 6)
			     vCode = vCode + "</TR>";
           if (vOnLastDay == 1)
	        	break;
       }
	     for (m=1; m<(7-j); m++) {
		     if (this.dameAnyo)
			     vCode = vCode + "<TD WIDTH='14%'" + this.write_festivos_string(j+m) + 
			     " class='textoTituloGris'><FONT COLOR='gray'> </FONT></TD>";
		    else
			    vCode = vCode + "<TD WIDTH='14%'" + this.write_festivos_string(j+m) + 
			    " class='textoTituloGris'><FONT COLOR='gray'>" + m + "</FONT></TD>";
	     }	
	     return vCode; 
     }

    /* Metodo para formatear el dia actual */
    Calendario.prototype.format_day = function(vday) 
    {
	    var vNowDay = ahora.getDate();
    	var vNowMonth = ahora.getMonth();
    	var vNowYear = ahora.getFullYear();
    	if (vday == vNowDay && this.dameNumeroMes == vNowMonth && this.gYear == vNowYear)
	    	return ("<B><FONT COLOR='#000000'>" + vday + "</font></B>");
    	else
    		return (vday);
    }

    /* Metodo para formatear los dias festivos */
    Calendario.prototype.write_festivos_string = function(vday) 
    {
	    var i; 
	    for (i=0; i<festivos.length; i++) {
		    if (vday == festivos[i])
			    return (" BGCOLOR=\"" + festivosColor + "\"");
	    }	
	    return "";
    }

    /* Metodo para formatear el resto de los dias en las diferentes formas posibles */
    Calendario.prototype.format_data = function(p_day) 
    {
    	var vData;
    	var vMonth = 1 + this.dameNumeroMes;
    	vMonth = (vMonth.toString().length < 2) ? "0" + vMonth : vMonth;
    	var vMon = Calendario.get_month(this.dameNumeroMes).substr(0,3).toUpperCase();
    	var vFMon = Calendario.get_month(this.dameNumeroMes).toUpperCase(); 
    	var vY4 = new String(this.gYear);
    	var vY2 = new String(this.gYear.substr(2,2));
    	var vDD = (p_day.toString().length < 2) ? "0" + p_day : p_day;
    	switch (this.gFormat) {
		    case "MM\/DD\/YYYY" :
		    	vData = vMonth + "\/" + vDD + "\/" + vY4;
			    break;
		    case "MM\/DD\/YY" :
			   vData = vMonth + "\/" + vDD + "\/" + vY2;
			    break;
		    case "MM-DD-YYYY" :
			    vData = vMonth + "-" + vDD + "-" + vY4;
			    break;
		    case "MM-DD-YY" :
			   vData = vMonth + "-" + vDD + "-" + vY2;
               break;
		    case "DD\/MON\/YYYY" :
			    vData = vDD + "\/" + vMon + "\/" + vY4;
    			break;
    		case "DD\/MON\/YY" :
    			vData = vDD + "\/" + vMon + "\/" + vY2;
    			break;
    		case "DD-MON-YYYY" :
    			vData = vDD + "-" + vMon + "-" + vY4;
    			break;
    		case "DD-MON-YY" :
    			vData = vDD + "-" + vMon + "-" + vY2;
    			break;
    		case "DD\/MONTH\/YYYY" :
    			vData = vDD + "\/" + vFMon + "\/" + vY4;
    			break;
    		case "DD\/MONTH\/YY" :
    			vData = vDD + "\/" + vFMon + "\/" + vY2;
    			break;
    		case "DD-MONTH-YYYY" :
    			vData = vDD + "-" + vFMon + "-" + vY4;
    			break;
    		case "DD-MONTH-YY" :
    			vData = vDD + "-" + vFMon + "-" + vY2;
    			break;
    		case "DD\/MM\/YYYY" :
    			vData = vDD + "\/" + vMonth + "\/" + vY4;
    			break;
    		case "DD\/MM\/YY" :
    			vData = vDD + "\/" + vMonth + "\/" + vY2;
    			break;
    		case "DD-MM-YYYY" :
        		vData = vDD + "-" + vMonth + "-" + vY4;
    			break;
    		case "DD-MM-YY" :
    			vData = vDD + "-" + vMonth + "-" + vY2;
    			break;
    		default :
			vData = vMonth + "\/" + vDD + "\/" + vY4;
	    }
	    return vData;
    }

    /* Funcion que formatea los colores de los textos y fondo del calendario */
    function Build(p_item, mes, anyo, formato) 
    {
	    var p_WinCal = calculo;
	    gCal = new Calendario(p_item, p_WinCal, mes, anyo, formato);
    	gCal.gBGColor="#FFFFFF";
    	gCal.gLinkColor="#000000";
    	gCal.gTextColor="#000000";
    	gCal.gHeaderColor="black"; 
    	if (gCal.dameAnyo)	
            gCal.showY();
    	else
            gCal.show();
    }

    /* Funcion que muestra el calendario en la ventana flotante */
    function show_Calendario() 
    {
	    /*
 		   * mes : 0-11 para Enero-Diciembre; 12para todos los meses.
		   * anyo	: con 4 digitos
		   * formato:formato de fechas (mm/dd/yyyy, dd/mm/yy, ...)
		   * item	: devuelve el Item.
	    */
      var fecha=''; 
      /*Si solo hay un argumento, podrá ser la ruta completa del campo texto, "forms[0].campo" */
      if (arguments.length==1) {
          miFormulario = '';
          p_item = arguments[0];
      }
      /*Si solo hay dos argumentos, podrá ser la ruta completa del campo texto, "forms[0].campo" y el valor de la fecha
      o el nombre de formulario y el nombre del campo fecha */
      if (arguments.length==2) {
          //Comprobamos que contiene el 2º parametro, si tiene aspecto de fecha
          if (arguments[1].indexOf("/")==-1) {//No es una fecha
        	    miFormulario = arguments[0];
        	    p_item = arguments[1];
              }
          else {//Es una fecha    
              miFormulario = '';
              p_item = arguments[0];
              fecha=arguments[1];
          }
      } 
      /*Si hay tres argumentos, son: el nombre de formulario, el nombre del campo fecha y el valor de la fecha */
      if (arguments.length==3) {
     	    miFormulario = arguments[0];
     	    p_item = arguments[1];
          fecha=arguments[2];
      }
      
   	  if (fecha==''){
         this.ahora = new Date();
         mes = new String(ahora.getMonth());
         anyo = new String(ahora.getFullYear().toString());
      }
      else{
         //calculo la fecha que recibo. La descompongo en un array 
         var array_fecha = fecha.split("/"); 
         //si el array no tiene tres partes, la fecha es incorrecta, mostramos la fecha de hoy
         if (array_fecha.length!=3) {
            alert('La fecha no es correcta.');
            this.ahora = new Date();
            mes = new String(ahora.getMonth());
            anyo = new String(ahora.getFullYear().toString());
         } else {
            if (isNaN(Number(array_fecha[0])) || isNaN(Number(array_fecha[1])) || isNaN(Number(array_fecha[2])) ||
               Number(array_fecha[0])<0 || Number(array_fecha[1])<1 || Number(array_fecha[1])>12 || Number(array_fecha[2])<0) {
               alert('La fecha no es correcta.');
               this.ahora = new Date();
               mes = new String(ahora.getMonth());
               anyo = new String(ahora.getFullYear().toString());
            }
            else {
               anyo = new String(Number(array_fecha[2])); 
               mes = new String((Number(array_fecha[1])-1)%12);
               var dia = new String(Number(array_fecha[0])); 
               this.ahora = new Date(anyo,mes,dia);
            }
         }
      }
 		  formato = "DD/MM/YYYY";
            
            /* OJO: CONFIGURAR AQUI EL TAMAÑO DE LA VENTANA FLOTANTE */
      vWinCal = window.open("", "Calendario", "width=280,height=175,status=no,resizable=no,top=200,left=200");
	    vWinCal.opener = top;
	    calculo = vWinCal;
	    Build(p_item, mes, anyo, formato);
    }
   
    /* OJO: CONFIGURAR AQUI EL FORMATO DE LA FECHA */
    function show_yearly_Calendario(p_item, anyo, formato) 
    {
	    /* formato por defecto */
	    if (anyo == null || anyo == "")
		    anyo = new String(ahora.getFullYear().toString());
	    if (formato == null || formato == "")
		    formato = "DD/MM/YYYY";
	    var vWinCal = window.open("", "Calendario", "scrollbars=no");
	    vWinCal.opener = top;
	    calculo = vWinCal;
	    Build(p_item, null, anyo, formato);
    }

/* Fin del fichero */

