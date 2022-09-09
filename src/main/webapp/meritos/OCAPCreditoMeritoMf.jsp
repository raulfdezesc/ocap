<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.util.Utilidades"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>
<script language="JavaScript">
function longMax(campo, max) {
   if(campo.value.length <= max) {
      return true;
   } else {
      campo.value = campo.value.substring(0, max);
      return false;
   }
}
</script>
<div class="ocultarCampo">

	<div class="contenido">
		<div class="contenidoAltaMC">
		<% if (!Utilidades.isNullOrIsEmpty(request.getParameter("nHoras"))){%>
				<h2 class="tituloContenido">MODIFICACI&Oacute;N DE
				HORAS</h2>
		<% }else{ %>
				<h2 class="tituloContenido">MODIFICACI&Oacute;N DE
				CR&Eacute;DITOS</h2>
		<% } %>

			<h3 class="subTituloContenidoMC">
				<bean:write name="OCAPMeritosForm" property="DTipoMerito" />
			</h3>
			<div class="lineaBajaM"></div>
			<br />
			<br />
			<br />

			<html:form action="/OCAPMeritos.do?accion=modificarCreditoMerito">
				<html:hidden name="OCAPMeritosForm" property="NHorasUsuario"/>
				<html:hidden name="OCAPMeritosForm" property="NCredUsuario"/>
				<logic:notEmpty name="OCAPMeritosForm" property="NCredUsuario">
					<div class="mensajeErrorMC">
						<html:messages id="nCredModif" property="nCredModif" message="true">
							<bean:write name="nCredModif" />
							<br />
						</html:messages>
					</div>
				</logic:notEmpty>
				<logic:notEmpty name="OCAPMeritosForm" property="NHorasUsuario">
					<div class="mensajeErrorMC">
						<html:messages id="nHorasModif" property="nHorasModif" message="true">
							<bean:write name="nHorasModif" />
							<br />
						</html:messages>
					</div>
				</logic:notEmpty>
				
				
				<fieldset>
					<!-- <legend class="tituloLegend"> <bean:write name="OCAPMeritosForm" property="DNombreMerito" /> </legend> -->
					<div class="cuadroFieldset">
						<label for="idVTitulo" class="textoJustificado"> <bean:write
								name="OCAPMeritosForm" property="DTitulo" />
						</label> <br />
						
				<logic:notEmpty name="OCAPMeritosForm" property="NHorasUsuario">
							<br /> <label for="idVNDias"> Horas usuario: <span><bean:write name="OCAPMeritosForm" property="NHorasUsuario" /></span> </label> <br />
													<br /> <label for="idVNDias"> Horas modificadas: *<html:text
								property="NHorasModif" styleClass="recuadroM colocaNumTallerMC"
								maxlength="6" />
						</label>
						<br />
						<br />
						<p style="vertical-align: middle;">
						 	<label for="idJustificacion"> Justificaci&oacute;n:   
								<html:textarea name="OCAPMeritosForm" styleId="idJustificacion" style="vertical-align: middle;"
									property="DMotivoCredModif"  styleClass="recuadroM colocaOrganizaTallerMC"
									 rows="5" cols="100"
										onkeypress="javascript:return longMax(this,999);"
										onclick="javascript:return longMax(this,999);"
										onkeydown="javascript:return longMax(this,999);"
										onkeyup="javascript:return longMax(this,999);"/>
							</label>
						</p>
				</logic:notEmpty>
				<logic:notEmpty name="OCAPMeritosForm" property="NCredUsuario">
							<br /> <label for="idVNDias"> Cr&eacute;ditos usuario: <span><bean:write name="OCAPMeritosForm" property="NCredUsuario" /></span> </label> <br />
													<br /> <label for="idVNDias"> <% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())){%>
							Cr&eacute;ditos CEIS: *<%}else{ %> Cr&eacute;ditos CC: *<%}%> <html:text
								property="NCredModif" styleClass="recuadroM colocaNumTallerMC"
								maxlength="6" />
						</label>
						<br />
						<br />
						<p style="vertical-align: middle;">
						 	<label for="idJustificacion"> <% if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())){%>
								Justificaci&oacute;n CEIS:  <%}else{ %> Justificaci&oacute;n CC:  <%}%> 
								<html:textarea name="OCAPMeritosForm" styleId="idJustificacion" style="vertical-align: middle;"
									property="DMotivoCredModif"  styleClass="recuadroM colocaOrganizaTallerMC"
									 rows="5" cols="100"
										onkeypress="javascript:return longMax(this,999);"
										onclick="javascript:return longMax(this,999);"
										onkeydown="javascript:return longMax(this,999);"
										onkeyup="javascript:return longMax(this,999);"/>
							</label>
						</p>
			</logic:notEmpty>
						
					</div>
				</fieldset>

				<p>Los campos se&ntilde;alados con * son obligatorios</p>

				<div class="espaciador"></div>

				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="OCAPUsuarios.do?accion=irInsertar&pestana=<bean:write name="OCAPMeritosForm" property="pestanaSeleccionada"/>&cDni=<bean:write name="OCAPMeritosForm" property="CDni" />#<bean:write name="OCAPMeritosForm" property="DTipoMerito"/>">
								<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" />
								<span> Cancelar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <img
							src="imagenes/imagenes_ocap/flecha_correcto.gif" alt="Aceptar" />
							<input type="submit" name="accionBoton"
							class="textoBotonInputAncho" tabindex="17" value="Aceptar"
							alt="Aceptar" />
						</span> <span class="derBoton"></span>
					</div>
				</div>
				<html:hidden property="pestanaSeleccionada" />
				<html:hidden property="CExpmcId" />
				<html:hidden property="CExpId" />
				<html:hidden property="DTipoMerito" />
				<html:hidden property="CTipoMerito" />
				<html:hidden property="NCredUsuario" />
				<html:hidden property="DTitulo" />
				<html:hidden property="CDni" />
			</html:form>

		</div>
		<!-- /fin de ContenidoDCP1A -->
	</div>
	<!-- /Fin de Contenido -->

</div>

<!-- /Fin de ocultarCampo -->