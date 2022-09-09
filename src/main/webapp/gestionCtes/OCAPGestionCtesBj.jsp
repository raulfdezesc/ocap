<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<div class="ocultarCampo">

	<div class="cuadroContenedor contenidoFaseIII">
		<html:form action="/OCAPGestionCtes.do">
			<div class="titulocajaformulario">BAJA DE CTE</div>
			<% boolean bPermisoBorrar = false; %>
			<div class="cajaformulario">
				<div class="cuadroFieldset">
					<logic:equal name="componentes" value="<%=Constantes.SI%>">
						<label for="idVTitulo" class="textoJustificado"> Este CTE
							tiene componentes asignados. Para borrarlo deber&aacute;
							cambiarlos previamente a otro CTE. </label>
					</logic:equal>
					<logic:equal name="evaluadores" value="<%=Constantes.SI%>">
						<label for="idVTitulo" class="textoJustificado"> Existen
							evaluadores asignados a este CTE. Para borrarlo deber&aacute;
							cambiarlos previamente a otro CTE. </label>
					</logic:equal>
					<logic:equal name="convocatorias" value="<%=Constantes.SI%>">
						<label for="idVTitulo" class="textoJustificado"> Existen
							convocatorias activas para este CTE. Para borrarlo deber&aacute;
							desactivarlas. </label>
					</logic:equal>
					<logic:notEqual name="componentes" value="<%=Constantes.SI%>">
						<logic:notEqual name="evaluadores" value="<%=Constantes.SI%>">
							<logic:notEqual name="convocatorias" value="<%=Constantes.SI%>">
								<label for="idVTitulo" class="textoJustificado">
									&iquest;Est&aacute; seguro de que desea dar de baja el CTE? </label>
								<% bPermisoBorrar = true; %>
							</logic:notEqual>
						</logic:notEqual>
					</logic:notEqual>
				</div>

				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="OCAPGestionCtes.do?accion=buscar"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif"
								alt="Cancelar Eliminar Cte" /> <logic:notEqual
									name="componentes" value="<%=Constantes.SI%>">
									<logic:notEqual name="evaluadores" value="<%=Constantes.SI%>">
										<logic:notEqual name="convocatorias"
											value="<%=Constantes.SI%>">
											<span> <%=Constantes.NO_TEXTO%>
											</span>
										</logic:notEqual>
									</logic:notEqual>
								</logic:notEqual> <% if (!bPermisoBorrar){ %> <span> Volver </span> <% } %>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<logic:notEqual name="componentes" value="<%=Constantes.SI%>">
						<logic:notEqual name="evaluadores" value="<%=Constantes.SI%>">
							<logic:notEqual name="convocatorias" value="<%=Constantes.SI%>">
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="OCAPGestionCtes.do?accion=borrar&cCteIdS=<bean:write name="OCAPGestionCtesForm" property="CCte_id"/>">
											<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
											alt="Aceptar Eliminar Cte" /> <span> <%=Constantes.SI_TEXTO%>
										</span>
									</a>
									</span> <span class="derBoton"></span>
								</div>
							</logic:notEqual>
						</logic:notEqual>
					</logic:notEqual>
				</div>
				<html:hidden property="CCte_id" />
				<div class="espaciador"></div>
			</div>
		</html:form>
	</div>

</div>
<!-- /Fin de ocultarCampo -->
