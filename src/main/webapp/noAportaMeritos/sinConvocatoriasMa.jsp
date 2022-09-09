<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"
	charset="windows-1252"></script>

<%--div class="contenido contenidoFaseIII"--%>
<div class="contenido">
	<div class="contenidoListadoAspirantesGCP listadoFaseIII">

		<html:form action="/OCAPSolicitudes.do">

			<h2 class="tituloContenido">SOLICITUDES QUE NO APORTAN M&Eacute;RITOS ASISTENCIALES</h2>
			<h3 class="subTituloContenido">Procedimiento para marcar solicitudes en estado "No aporta m&eacute;ritos asistenciales"</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<fieldset>
				<legend class="tituloLegend"> Aviso </legend>
				<div class="cuadroFieldset">
					<p>Este procedimiento s&oacute;lo se puede ejecutar tras haber finalizado la fase de m&eacute;ritos asistenciales. Como no hay ninguna convocatoria activa que cumpla este requisito, no es posible lanzar el procedimiento.</p>
				</div>
			</fieldset>

			<div class="espaciador"></div>
		</html:form>

	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->
