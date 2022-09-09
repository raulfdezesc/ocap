<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.ot.expedienteMC.OCAPExpedientemcOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.meritosCurriculares.OCAPMeritoscurricularesOT"%>

<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<% JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO); %>

<div style="width: 80%; margin-left: 50px;" align="right">
	<div class="lineaBajaM"></div>
	<br />
	<% if(Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) { %>
	<span>Total Cr&eacute;ditos Usuario: &nbsp;</span>
	<bean:write name="requestTiposMeritos" property="creditosCTSM" />
	<logic:notEqual name="periodoMC" value="<%=Constantes.SI%>">
		<br /> <span>Total Cr&eacute;ditos CEIS: &nbsp;</span>
		<bean:write name="requestTiposMeritos" property="creditosValidadosCeisCTSM" />
	</logic:notEqual>
	<% } else if(Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) { %>
		<span>Total Cr&eacute;ditos Usuario: &nbsp;</span>
		<bean:write name="requestTiposMeritos" property="creditosCTSM" />
		<br />
		<logic:notEqual name="periodoMC" value="<%=Constantes.SI%>">
			<span>Total Cr&eacute;ditos CEIS: &nbsp;</span>
			<bean:write name="requestTiposMeritos"
				property="creditosValidadosCeisCTSM" />
			<br />
			<logic:notEqual name="OCAPUsuariosForm" property="enPeriodoValMc" value="<%=Constantes.SI%>">
				<span>Total Cr&eacute;ditos CC: &nbsp;</span>
				<bean:write name="requestTiposMeritos" property="creditosValidadosCcCTSM" />
			</logic:notEqual>
		</logic:notEqual>
	<% } else if(Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) { %>
	<logic:equal name="OCAPUsuariosForm" property="CTipo"
		value="<%=Constantes.FASE_VALIDACION%>">
		<span>Total Cr&eacute;ditos Usuario: &nbsp;</span>
		<bean:write name="requestTiposMeritos" property="creditosCTSM" />
	</logic:equal>
	<logic:equal name="OCAPUsuariosForm" property="CTipo"
		value="<%=Constantes.FASE_VALIDACION_CC%>">
		<span>Total Cr&eacute;ditos CEIS: &nbsp;</span>
		<bean:write name="requestTiposMeritos"
			property="creditosValidadosCeisCTSM" />
	</logic:equal>
	<logic:equal name="OCAPUsuariosForm" property="CTipo"
		value="<%=Constantes.FASE_CA%>">
		<span>Total Cr&eacute;ditos CC: &nbsp;</span>
		<bean:write name="requestTiposMeritos" property="creditosValidadosCcCTSM" />
	</logic:equal>
	<% } else { %>
	<logic:equal name="permisoUsuarioVerCreditos"	value="<%=Constantes.SI%>">
		<span>Total Cr&eacute;ditos Usuario: &nbsp;</span>
		<bean:write name="requestTiposMeritos" property="creditosCTSM" />
		<br />
		<span>Total Cr&eacute;ditos CEIS: &nbsp;</span>
		<bean:write name="requestTiposMeritos"
			property="creditosValidadosCeisCTSM" />
		<br />
		<span>Total Cr&eacute;ditos CC: &nbsp;</span>
		<bean:write name="requestTiposMeritos" property="creditosValidadosCcCTSM" />
	</logic:equal>
	<logic:equal name="permisoUsuarioVerCreditos"	value="<%=Constantes.NO%>">
		<span>Total Cr&eacute;ditos: &nbsp;</span>
		<bean:write name="requestTiposMeritos" property="creditosCTSM" />
	</logic:equal>
	<% } %>
</div>
<div class="espaciador"></div>