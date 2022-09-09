<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="java.util.StringTokenizer"%>


<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>

<script language="javaScript" src="javascript/comun.js"></script>

<% String rutaVuelta = (String)request.getAttribute("rutaVuelta");   
   String usuarioDeSesion = es.jcyl.framework.JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO;

   StringTokenizer tokenIn = null;
   String formulario = null;
   
   if(rutaVuelta!= null){
     tokenIn  = new StringTokenizer(rutaVuelta, "?");
     formulario = tokenIn.nextToken().trim();
   }
%>

<div class="ocultarCampo">

	<div class="cuadroContenedorConsultas">

		<div class="titulocajaformulario">
			<bean:message key="general.exitoTit" />
		</div>
		<div class="cajaformulario">
			<p class="mensajeAviso">
				<bean:message key="dudas.exito" />
			</p>
			<div class="colocarMensajeExito">
				<html:form action="<%=formulario%>">
					<br />
					<div class="espaciador"></div>
					<div class="limpiadora"></div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <input
							type="button" name="boton" class="formBoton"
							onclick="enviar('<%=rutaVuelta%>');"
							value="<bean:message key="button.volver"/>" />
						</span> <span class="derBoton"></span>
					</div>
					<div class="espaciador"></div>
					<div class="limpiadora"></div>

					<script language="JavaScript">
            document.forms[0].boton.focus();
         </script>
					<script>
            function volver() {
               document.location='<%=rutaVuelta%>';
            }
         </script>
					<br>
					<br>
				</html:form>
			</div>
		</div>
	</div>

</div>
<!-- /Fin de ocultarCampo -->
