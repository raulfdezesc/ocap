<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<%@ taglib uri="html.tld" prefix="html"%>
<% String anio = null; 
      
      if (request.getAttribute("anio") != null)
      anio = (String) request.getAttribute("anio");   
 
    
  
%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"
	charset="windows-1252"></script>


<div class="contenido">
	<div class="contenidoDCP1A">
		<html:form action="/OCAPCuestionarios.do">
			<fieldset>
				<legend class="tituloLegend">&Aacute;lerta Areas
					Evaluaci&oacute;n </legend>
				<div class="cuadroFieldset" style="font-weight: bold;">
					<%=Constantes.MENSAJE_ALERTA_AUTOEVALUACION%>
						<%=anio%><%=Constantes.MENSAJE_ALERTA_AUTOEVALUACION2%>
				</div>
			</fieldset>

			<div class="espaciadorPeq"></div>
			<div class="botonesPagina posicionLOPD">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:enviar('OCAPCuestionarios.do?accion=irProteccionDatos');">
							<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
							alt="Acepta" /> <span> Aceptar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>

		</html:form>

	</div>
	<!-- /fin de ContenidoDCP1A_PGP -->
</div>
<!-- /Fin de Contenido -->