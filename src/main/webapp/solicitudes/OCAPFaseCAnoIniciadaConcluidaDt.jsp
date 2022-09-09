<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="bean.tld" prefix="bean"%>

<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="java.util.StringTokenizer"%>


<% String rutaVuelta = (String)request.getAttribute("rutaVuelta");   
   StringTokenizer tokenIn = null;
   String formulario = null;
   
   if(rutaVuelta!= null){
     tokenIn  = new StringTokenizer(rutaVuelta, "?");
     formulario = tokenIn.nextToken().trim();
     if (formulario.substring(0, 5).equals(request.getContextPath()))
         formulario = "/OCAPSolicitudes.do";
   }
%>
<script language="javaScript" src="javascript/comun.js"></script>

<div class="ocultarCampo">

	<div class="contenido contenidoFaseIII">
		<div class="ayudaDCP1A_PGP">

			<h2 class="tituloContenido">ACCESO DENEGADO</h2>
			<h3 class="subTituloContenido">Aviso</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<html:form action="<%=formulario%>">
				<p>
					<!-- La fase de auto-evaluaci&oacute;n de Desempe&ntilde;o de la Competencia a&uacute;n no ha comenzado. -->
					La fase de evalu@net a&uacute;n no ha comenzado o ya ha concluido.
				</p>

				<div class="espaciador"></div>
				<div class="limpiadora"></div>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <input
						type="button" name="boton" class="formBoton"
						onclick="enviar('<%=rutaVuelta%>');" value="Volver" />
					</span> <span class="derBoton"></span>
				</div>
				<div class="espaciador"></div>
				<div class="limpiadora"></div>
			</html:form>

		</div>
		<!-- /fin de ContenidoDCP1A_PGP -->
	</div>
	<!-- /Fin de Contenido -->

</div>
<!-- /Fin de ocultarCampo -->
