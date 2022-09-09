<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<div class="ocultarCampo">

	<div class="cuadroContenedor contenidoFaseIII">
		<html:form action="/OCAPGestionCtes.do">
			<div class="titulocajaformulario">DESACTIVACI&Oacute;N DE
				CONVOCATORIA</div>

			<div class="cajaformulario">
				<div class="cuadroFieldset">
					<logic:equal name="evaluadores" value="<%=Constantes.SI%>">
						<label for="idVTitulo" class="textoJustificado"> Existen
							evaluadores asignados a este CTE en esta convocatoria. Para
							desactivarla deber&aacute; cambiarlos previamente a otra
							convocatoria o CTE. </label>
					</logic:equal>
					<logic:notEqual name="evaluadores" value="<%=Constantes.SI%>">
						<label for="idVTitulo" class="textoJustificado">
							&iquest;Est&aacute; seguro de que desea desactivar la
							convocatoria de este CTE? </label>
					</logic:notEqual>
				</div>

				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="OCAPGestionCtes.do?accion=irActivarConvocatoria&cCteIdS=<bean:write name="OCAPGestionCtesForm" property="CCte_id"/>">
								<img src="imagenes/imagenes_ocap/aspa_roja.gif"
								alt="Cancelar Desactivar Convocatoria/CTE" /> <logic:notEqual
									name="evaluadores" value="<%=Constantes.SI%>">
									<span> <%=Constantes.NO_TEXTO%>
									</span>
								</logic:notEqual> <logic:equal name="evaluadores" value="<%=Constantes.SI%>">
									<span> Volver </span>
								</logic:equal>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<logic:notEqual name="evaluadores" value="<%=Constantes.SI%>">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="OCAPGestionCtes.do?accion=eliminarConvo&cCteConvoIdS=<bean:write name="OCAPGestionCtesForm" property="CCteConvoId"/>&cCteIdS=<bean:write name="OCAPGestionCtesForm" property="CCte_id"/>">
									<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
									alt="Aceptar Desactivar Convocatoria/CTE" /> <span> <%=Constantes.SI_TEXTO%>
								</span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</logic:notEqual>
				</div>
				<html:hidden property="CCte_id" />
				<html:hidden property="CCteConvoId" />
				<div class="espaciador"></div>
			</div>
		</html:form>
	</div>

</div>
<!-- /Fin de ocultarCampo -->
