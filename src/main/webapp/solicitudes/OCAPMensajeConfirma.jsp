<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="java.util.StringTokenizer"%>


<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>

<script language="javaScript" src="javascript/comun.js"></script>

<% String rutaVuelta = (String)request.getAttribute("rutaVuelta");   
   String usuarioDeSesion = es.jcyl.framework.JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO;
   //la variable alta informa si se viene de una pantalla de alta
   
   //Si no se viene desde una pantalla e alta, la variable no se informa.

   StringTokenizer tokenIn = null;
   String formulario = null;
   
   if(rutaVuelta!= null){
     tokenIn  = new StringTokenizer(rutaVuelta, "?");
     formulario = tokenIn.nextToken().trim();
     if (formulario.substring(0, 5).equals(request.getContextPath()))
         formulario = "/OCAPSolicitudes.do";
   }
%>

<div class="ocultarCampo">
	<div class="cuadroContenedorConsultas">

		<div class="titulocajaformulario">
			<bean:message key="general.exitoTit" />
		</div>
		<div class="cajaformulario">
			<p class="mensajeAviso"><%=(String)request.getAttribute("mensaje")%></p>

			<html:form action="/OCAPSolicitudes.do">
				<br>
				<script>
               function volver() {
                  document.location='<%=rutaVuelta%>';
               }
            </script>
				<div class="espaciador"></div>
				<div class="limpiadora"></div>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <input
						type="button" name="boton" class="formBoton" onclick="volver()"
						value="<bean:message key="button.aceptar"/>" />
					</span> <span class="derBoton"></span>
				</div>
				<div class="espaciador"></div>
				<div class="limpiadora"></div>
				<script language="JavaScript">
               document.forms[0].boton.focus();
            </script>
			</html:form>

		</div>
	</div>
</div>
<!-- /Fin de ocultarCampo -->