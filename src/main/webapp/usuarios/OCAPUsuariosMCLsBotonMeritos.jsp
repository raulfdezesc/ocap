<%@ page contentType="text/html;charset=ISO-8859-1"	pageEncoding="windows-1252"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<% JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO); %>

	<script type="text/javascript">
		function ventanaMeritosValidados(identificadorExpediente,CTipoMerito,DNombre,CDefmeritoId,meritosOpcionalesFlag) {
			var url = 'OCAPMeritosAnteriores.do?accion=verMeritosAnteriores&idExpediente='+identificadorExpediente+'&idCateg='+encodeURIComponent(CTipoMerito)+'&descCateg='+encodeURIComponent(DNombre)+'&tipoMerito='+CDefmeritoId+'&opcional='+meritosOpcionalesFlag;
			window.open(url, '_blank', 'scrollbars=1,resizable=1,height=600,width=1024');
		}
	</script>

	<span class="tituloMC"> <bean:write name="requestTiposMeritos"	property="DNombre" /></span>
	
	<a name="<bean:write name="requestTiposMeritos" property="DNombre"/>"></a>
		
	<% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())){ %>
		<logic:equal name="requestTiposMeritos" property="meritosAnterioresValidados" value="<%=Constantes.SI_ESP%>">
			<button type="button" onClick="javascript:ventanaMeritosValidados('<bean:write name="identificadorExpediente"/>','<bean:write name="requestTiposMeritos" property="CTipoMerito"/>','<bean:write name="requestTiposMeritos" property="DNombre"/>','<bean:write name="requestTiposMeritos" property="CDefmeritoId"/>','<bean:write name="meritosOpcionalesFlag"/>');"
			style="background-color: #004B98; border-radius: 3px; font-family: Verdana, Arial, Helvetica, sans-serif; color: #FFFFFF; font-size: 1em; padding: 3px; cursor: pointer;">
			Ver m&eacute;ritos anteriores</button>
		</logic:equal>
	<% } %>
		
	