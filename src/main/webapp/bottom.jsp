<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="bean.tld" prefix="bean"%>

<bean:define id="accesible">
	<% String menu = es.jcyl.framework.JCYLConfiguracion.getValor("MENU_ACCESIBLE"); 
    if (menu!=null && menu.equals("SI")) {
        out.print("SI");
    } else {
        out.print("NO");
    }
    %>
</bean:define>

<logic:equal name="MENU_ACCESIBLE" value="SI">
	<html:link href="http://www.salud.jcyl.es" target="_blank">Salud Castilla y Le&oacute;n </html:link>
	<logic:present
		name="<%=es.jcyl.framework.JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO%>">
		<html:img page="/imagenes/puntitoM.png" alt="" />
		<a href="PaginaInicio.do">P&aacute;gina de inicio</a>
	</logic:present>	
     <html:img page="/imagenes/puntitoM.png" alt="" /><bean:message key="version" />
	<%--
   JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
   if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIO_FASE_III) )) {
   %>
   <br />
   <span>Para cualquier problema t&eacute;cnico relacionado con la aplicaci&oacute;n, seleccione este enlace: <a href="OCAPDudasTutores.do?accion=irInsertarDuda&tipoDuda=2">Contacto webmaster</a></span>
   <% } --%>

</logic:equal>
