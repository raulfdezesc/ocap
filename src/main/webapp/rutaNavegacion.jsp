
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>


<logic:present name="apartado">
	<div id="bordeAzul">
		<html:link action="PaginaInicio.do" tabindex="4"
			title="Ir al Inicio de la Aplicación">
			<bean:message key="navegacion.inicio" />
		</html:link>
		<span>&gt;</span>
		<bean:write name="apartado" />
		<logic:present name="subapartado">
			<span>&gt;</span>
			<bean:write name="subapartado" />
		</logic:present>
		<logic:present name="accion">
			<span>&gt;</span>
			<bean:write name="accion" />
		</logic:present>
	</div>
</logic:present>
