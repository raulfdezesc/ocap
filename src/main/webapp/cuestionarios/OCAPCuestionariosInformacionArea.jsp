<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.areasItinerarios.OCAPAreasItinerariosOT"%>

<%
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoECP">
		<html:form action="/OCAPCuestionarios.do">
			<h2 class="tituloContenido">
				EVALUACI&Oacute;N DE COMPETENCIAS PROFESIONALES - ASISTENCIALES <br />CARRERA
				PROFESIONAL
			</h2>
			<h3 class="subTituloContenido">
				<bean:write name="OCAPCuestionariosForm" property="DTitulo" />
				<br /> <br />
				<bean:write name="OCAPCuestionariosForm"
					property="DNombreItinerario" />
				<br />
				<br /> <span><bean:write name="OCAPCuestionariosForm"
						property="codigoId" /></span>
			</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>



			<bean:write name="OCAPCuestionariosForm" property="DInformacionArea"
				filter="false" />


			<div class="espaciador"></div>
			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <% if(Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())){%>
						<a href="OCAPCuestionarios.do?accion=irListarPruebas"> <%} else {%>
							<a href="OCAPCuestionarios.do?accion=irListar"> <%}%> <img
								src="imagenes/imagenes_ocap/dobleFlecha.gif"
								alt="Volver a Areas de Evaluacion" /> <span> Volver </span>
						</a></span> <span class="derBoton"></span>
				</div>
			</div>

			<div class="limpiadora"></div>

		</html:form>
	</div>
	<!-- /fin de ContenidoTextoECP -->
</div>
<!-- /Fin de Contenido -->

<div class="limpiadora"></div>