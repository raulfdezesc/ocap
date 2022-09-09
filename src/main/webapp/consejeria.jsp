<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%
// Este Script obtiene el Rol del usuario. Se usa el el campo siguiente
java.lang.String usuarioDeSesion = es.jcyl.framework.JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO;
es.jcyl.framework.JCYLUsuario miusr= (es.jcyl.framework.JCYLUsuario)session.getAttribute(es.jcyl.framework.JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
String rol = null;
String nombreUsuario = null;
if (miusr != null) {
   rol = miusr.getUser().getRol();
   nombreUsuario = miusr.getUser().getC_usr_id();
}
%>
<span class="ocultar">Nombre del usuario:</span>
<div class="conexion">
	<logic:present name="<%=usuarioDeSesion%>">
		<bean:write name="<%=usuarioDeSesion%>" property="usuario.DNom"
			ignore="true" />
		<bean:write name="<%=usuarioDeSesion%>" property="usuario.DApell"
			ignore="true" />
		<% String perfil ="";
         if (rol.indexOf("OCAP_")!= -1){
            perfil = "- "+rol.substring(rol.indexOf("OCAP_")+5,rol.length());
            if (perfil.equals("- FQS_GEST")) {
                perfil = "- GEST";
            }else if (perfil.equals("- FQS_ADTVO")){
                perfil = "- ADTVO";
            }
         }else
            perfil = "- "+rol;
            
         if (perfil.indexOf("USUARIO")!=-1)
            perfil = "";
         %>
		<%=perfil%>
	</logic:present>
</div>
<span class="ocultar">Botones de servicios:</span>
<div class="botoneraCS">
	<logic:present
		name="<%=es.jcyl.framework.JCYLConfiguracion.NOMBRE_ATRIBUTO_FUNCIONES%>">
		<logic:match
			name="<%=es.jcyl.framework.JCYLConfiguracion.NOMBRE_ATRIBUTO_FUNCIONES%>"
			property="<%=rol%>" value="verCambiarPasswordAction">
			<logic:equal name="MENU_ACCESIBLE" value="NO">
				<a href="verCambiarPasswordAction.do" target=main>Cambio de
					contrase&ntilde;a</a>
			</logic:equal>
			<logic:equal name="MENU_ACCESIBLE" value="SI">
				<a href="verCambiarPasswordAction.do">Cambio de
					contrase&ntilde;a</a>
			</logic:equal>
		</logic:match>
	</logic:present>
	<% if(nombreUsuario!=null && !nombreUsuario.equals(es.jcyl.framework.JCYLConfiguracion.getValor("USUARIO_PUBLICO"))) { %>
	<logic:equal
		name="<%= es.jcyl.framework.JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO%>"
		property="usuario.rol" value="<%=Constantes.OCAP_USUARIOS%>">
		<html:link href="ayuda/manualUsuarioMC_PR_GRS_OCAP.pdf"
			title="Abre el Manual de Usuario en una nueva ventana."
			target="_blank">
			<bean:message key="cabecera.etiqueta.manualUsuario" />
		</html:link>
	</logic:equal>
	<logic:equal
		name="<%= es.jcyl.framework.JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO%>"
		property="usuario.rol" value="<%=Constantes.OCAP_USUARIO_FASE_III%>">
		<html:link href="ayuda/manualUsuarioMC_PR_GRS_OCAP.pdf"
			title="Abre el Manual de Usuario en una nueva ventana."
			target="_blank">
			<bean:message key="cabecera.etiqueta.manualUsuario" />
		</html:link>
	</logic:equal>
	<a href="CerrarSesion.do">Cerrar sesión</a>
	<% } %>
</div>
