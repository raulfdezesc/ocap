<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="JavaScript">
	function pedirAclaraciones(pedir){
		enviar("OCAPMeritos.do?accion=aclararMerito&aclarar="+pedir+'&estado=<%=request.getAttribute("estado")%>&cDNI=<bean:write name="OCAPMeritosForm" property="CDni" />');
	}
</script>

<%if (!Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){%>
<logic:equal name="OCAPMeritosForm" property="CEstado"
	value="<%=Constantes.ESTADO_PENDIENTE_VALUE%>">
	<html:hidden name="OCAPMeritosForm" property="CDni" />
	<% if ( jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS) ) { %>
		<logic:equal name="OCAPUsuariosForm" property="enPeriodoValMc" value="<%=Constantes.SI%>">
			<logic:notEqual name="OCAPMeritosForm" property="BPdteAclarar"
				value="<%=Constantes.SI%>">
				<logic:notEqual name="OCAPMeritosForm" property="BInvalidado"
					value="<%=Constantes.SI%>">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:pedirAclaraciones('<%=Constantes.SI%>');"> <img
								src="imagenes/imagenes_ocap/icono_aclaracion.gif"
								alt="Pedir aclaraciones" /> <span> Pedir aclaraciones </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:notEqual>
			</logic:notEqual>
		</logic:equal>
	<% } %>
	<% if ( jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC) ) { %>
		<logic:equal name="OCAPUsuariosForm" property="enPeriodoValCc" value="<%=Constantes.SI%>">
			<logic:notEqual name="OCAPMeritosForm" property="BPdteAclarar"
				value="<%=Constantes.SI%>">
				<logic:notEqual name="OCAPMeritosForm" property="BInvalidado"
					value="<%=Constantes.SI%>">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:pedirAclaraciones('<%=Constantes.SI%>');"> <img
								src="imagenes/imagenes_ocap/icono_aclaracion.gif"
								alt="Pedir aclaraciones" /> <span> Pedir aclaraciones </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:notEqual>
			</logic:notEqual>
		</logic:equal>
	<% } %>
	<% if (! (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC) ) ) { %>
		<logic:notEqual name="OCAPMeritosForm" property="BPdteAclarar"
			value="<%=Constantes.SI%>">
			<logic:notEqual name="OCAPMeritosForm" property="BInvalidado"
				value="<%=Constantes.SI%>">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:pedirAclaraciones('<%=Constantes.SI%>');"> <img
							src="imagenes/imagenes_ocap/icono_aclaracion.gif"
							alt="Pedir aclaraciones" /> <span> Pedir aclaraciones </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</logic:notEqual>
		</logic:notEqual>
	<% } %>

												
	
	<logic:equal name="OCAPMeritosForm" property="BPdteAclarar"
		value="<%=Constantes.SI%>">
		<div class="botonAccion">
			<span class="izqBoton"></span> <span class="cenBoton"> <a
				href="javascript:pedirAclaraciones('<%=Constantes.NO%>');"> <img
					src="imagenes/imagenes_ocap/icono_aclaracion.gif"
					alt="Aclaraciones presentadas" /> <span> Aclaraciones
						presentadas </span>
			</a>
			</span> <span class="derBoton"></span>
		</div>
	</logic:equal>
</logic:equal>
<% } %>

<% if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC) ) { %>
	<logic:notEqual name="OCAPMeritosForm" property="meritosAnteriores" value="<%=Constantes.SI%>">
		<div class="botonAccion">
			<span class="izqBoton"></span> <span class="cenBoton"> <a
				href="OCAPUsuarios.do?accion=irInsertar&pestana=<bean:write name="OCAPMeritosForm" property="pestanaSeleccionada"/>&cDni=<bean:write name="OCAPMeritosForm" property="CDni" />#<bean:write name="OCAPMeritosForm" property="DTipoMerito"/>">
					<img src="imagenes/imagenes_ocap/icon_accept.gif" alt="Volver" />
					<span> Volver </span>
			</a>
			</span> <span class="derBoton"></span>
		</div>
	</logic:notEqual>
	<logic:equal name="OCAPMeritosForm" property="meritosAnteriores"  value="<%=Constantes.SI%>">
	<div class="botonAccion">
			<span class="izqBoton"></span> <span class="cenBoton"> <a
				href="javascript:history.back()">
					<img src="imagenes/imagenes_ocap/icon_accept.gif" alt="Volver" />
					<span> Volver </span>
			</a>
			</span> <span class="derBoton"></span>
		</div>
	</logic:equal>
<% } %>