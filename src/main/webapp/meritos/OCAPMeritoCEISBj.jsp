<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript">
function invalidar(invalida) {
   //si invalida=Y, invalida, si invalida=NO, readmite
   enviar("OCAPMeritos.do?accion=invalidarMerito&invalidar="+invalida+'&estado=<%=request.getAttribute("estado")%>');
}
</script>

<div class="ocultarCampo">

	<div class="contenido">
		<div class="contenidoAltaMC">
			<logic:equal name="invalidar" value="<%=Constantes.SI%>">
				<h2 class="tituloContenido">INVALIDAR M&Eacute;RITO CURRICULAR</h2>
			</logic:equal>
			<logic:notEqual name="invalidar" value="<%=Constantes.SI%>">
				<h2 class="tituloContenido">READMITIR M&Eacute;RITO CURRICULAR</h2>
			</logic:notEqual>

			<h3 class="subTituloContenido">
				<bean:write name="OCAPMeritosForm" property="DNombreMerito" />
			</h3>

			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<html:form action="/OCAPMeritos.do">
				<fieldset>
					<!-- <legend class="tituloLegend"> <bean:write name="OCAPMeritosForm" property="DNombreMerito" /> </legend> -->
					<div class="cuadroFieldset">
						<label for="idVTitulo" class="textoJustificado"> <logic:equal
								name="invalidar" value="<%=Constantes.SI%>">
                           &iquest;Est&aacute; seguro de que desea invalidar el m&eacute;rito <bean:write
									name="OCAPMeritosForm" property="DTitulo" />?
                        </logic:equal> <logic:notEqual name="invalidar"
								value="<%=Constantes.SI%>">
                           &iquest;Est&aacute; seguro de que desea readmitir el m&eacute;rito <bean:write
									name="OCAPMeritosForm" property="DTitulo" />?
                        </logic:notEqual>
						</label>
					</div>
				</fieldset>

				<div class="espaciador"></div>

				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="OCAPUsuarios.do?accion=irInsertar&pestana=<bean:write name="OCAPMeritosForm" property="pestanaSeleccionada"/>&cDni=<bean:write name="OCAPMeritosForm" property="CDni" />">
								<logic:equal name="invalidar" value="<%=Constantes.SI%>">
									<img src="imagenes/imagenes_ocap/aspa_roja.gif"
										alt="Cancelar Invalidar Mérito" />
								</logic:equal> <logic:notEqual name="invalidar" value="<%=Constantes.SI%>">
									<img src="imagenes/imagenes_ocap/aspa_roja.gif"
										alt="Cancelar Readmitir Mérito" />
								</logic:notEqual> <span> <%=Constantes.NO_TEXTO%>
							</span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <logic:equal
								name="invalidar" value="<%=Constantes.SI%>">
								<a href="javascript:invalidar('<%=Constantes.SI%>');"> <img
									src="imagenes/imagenes_ocap/flecha_correcto.gif"
									alt="Aceptar Invalidar Mérito" />
							</logic:equal> <logic:notEqual name="invalidar" value="<%=Constantes.SI%>">
								<a href="javascript:invalidar('<%=Constantes.NO%>');"> <img
									src="imagenes/imagenes_ocap/flecha_correcto.gif"
									alt="Aceptar Readmitir Mérito" />
							</logic:notEqual> <span> <%=Constantes.SI_TEXTO%>
						</span> <%-- <input type="submit" name="accionBoton" class="textoBotonInputAncho" tabindex="17" value="<%=Constantes.SI_TEXTO%>" alt="Botón para invalidar el mérito"/> --%>
							</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
				<html:hidden property="pestanaSeleccionada" />
				<html:hidden property="CExpmcId" />
				<html:hidden property="CExpId" />
				<html:hidden property="DTipoMerito" />
				<html:hidden property="CEstatutId" />
				<html:hidden property="CDefMeritoId" />
				<html:hidden property="CDni" />
			</html:form>

		</div>
		<!-- /fin de ContenidoDCP1A -->
	</div>
	<!-- /Fin de Contenido -->

</div>
<!-- /Fin de ocultarCampo -->