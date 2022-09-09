<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="java.util.StringTokenizer"%>


<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<script language="javaScript" src="javascript/comun.js"></script>

<%
 String rutaVuelta = null;
 String textoAdicional=null;
   if (request.getAttribute("rutaVuelta") != null)
      rutaVuelta = (String) request.getAttribute("rutaVuelta");   
      
      if (request.getAttribute("textoAdicional") != null)
      textoAdicional = (String) request.getAttribute("textoAdicional");   
 
    
  
%>

<div class="ocultarCampo">

	<div class="cuadroContenedorConsultas">

		<div class="titulocajaformulario">
			<bean:message key="general.errorTit" />
		</div>
		<div class="cajaformulario">
			<p class="mensajeAviso">
				<bean:message key="general.errorMsg" />
			</p>
			<p class="mensajeAviso">
					<%=textoAdicional%>
				</p>
			<logic:present name="errorXml">
				<p class="mensajeAviso">
					<bean:message key="error.xml" />
				</p>
				
					
			</logic:present>

			<div class="colocarMensajeExito">
				<br />
				<% if (rutaVuelta != null) {%>
				<script>
                  function volver() {
                     document.location='<%=rutaVuelta%>';
                  }
                 </script>
				<% } %>
				<div class="espaciador"></div>
				<div class="limpiadora"></div>
				<% if (rutaVuelta != null) {%>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <input
						type="button" name="boton" class="formBoton" onclick="volver();"
						value="<bean:message key="button.aceptar"/>" />
					</span> <span class="derBoton"></span>
				</div>
				<% } %>
				<div class="espaciador"></div>
				<div class="limpiadora"></div>
			</div>
		</div>
	</div>

</div>
<!-- /Fin de ocultarCampo -->
