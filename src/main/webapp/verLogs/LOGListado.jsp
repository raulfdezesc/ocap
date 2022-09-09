<%@ page contentType="text/html;charset=utf-8"
	pageEncoding="windows-1252"%>

<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="bean.tld" prefix="bean"%>

<!--Logs aplicación/subdirectorios -->
<div class="ocultarCampo">
	<div class="cuadroContenedorConsultas">
		<h2 class="tituloContenido">Ver Logs:</h2>
		<div class="titulocajaformulario">
			<bean:write name="_LOG_nombreDir" />
		</div>
		<logic:notEmpty name="_LOG_listado">
			<% request.setAttribute("_LOG_lista", request.getAttribute("_LOG_listado")); %>
			<jsp:include page="LOGListadoEsqueleto.jsp" flush="true" />
		</logic:notEmpty>
		<logic:equal name="_LOG_entorno" value="inicial">
			<!--Logs contenedor OAS-->
			<logic:notEmpty name="_LOG_listadoSer">
				<div class="titulocajaformulario">Listado de logs del
					contenedor</div>
				<% request.setAttribute("_LOG_lista", request.getAttribute("_LOG_listadoSer"));
               request.setAttribute("contenedor", "si");
            %>
				<jsp:include page="LOGListadoEsqueleto.jsp" flush="true" />
			</logic:notEmpty>
		</logic:equal>

		<!-- Botón volver -->
		<logic:notEqual name="_LOG_entorno" value="inicial">
			<div style="margin: 20px 30px 20px 30px; font-size: 1.2em;"
				align="right">
				<logic:notPresent name="_LOG_top">
					<a
						href="Log.do?_LOG_inicial=no&amp;_LOG_dir=<bean:write name="_LOG_directorio"/>/..&amp;_LOG_nombreDir=<bean:write name="_LOG_directorio"/>"><b>Volver</b></a>
				</logic:notPresent>
				<logic:present name="_LOG_top">
					<a href="Log.do"><b>Volver</b></a>
				</logic:present>
			</div>
		</logic:notEqual>
	</div>
</div>