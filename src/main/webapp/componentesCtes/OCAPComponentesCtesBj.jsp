<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<div class="ocultarCampo">

	<div class="cuadroContenedor contenidoFaseIII">
		<html:form action="/OCAPComponentesCtes.do">
			<logic:equal name="opcion" value="<%=Constantes.FQS_CTE%>">
				<div class="titulocajaformulario">BAJA DE COMPONENTE DE CTE</div>
			</logic:equal>
			<logic:equal name="opcion" value="<%=Constantes.FQS_CE%>">
				<div class="titulocajaformulario">BAJA DE COMPONENTE DE CE</div>
			</logic:equal>

			<div class="cajaformulario">
				<div class="cuadroFieldset">
					<logic:equal name="convocatorias" value="<%=Constantes.SI%>">
						<label for="idVTitulo" class="textoJustificado"> Este
							componente est&aacute; activo en alguna convocatoria. Para
							borrarlo deber&aacute; desactivarlo. </label>
					</logic:equal>
					<logic:notEqual name="convocatorias" value="<%=Constantes.SI%>">
						<label for="idVTitulo" class="textoJustificado">
							&iquest;Est&aacute; seguro de que desea dar de baja el
							componente? </label>
					</logic:notEqual>
				</div>

				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="OCAPComponentesCtes.do?accion=buscarComponentes&opcion=<bean:write name="opcion"/>&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>">
								<img src="imagenes/imagenes_ocap/aspa_roja.gif"
								alt="Cancelar Eliminar Componente" /> <logic:notEqual
									name="convocatorias" value="<%=Constantes.SI%>">
									<span> <%=Constantes.NO_TEXTO%>
									</span>
								</logic:notEqual> <logic:equal name="convocatorias" value="<%=Constantes.SI%>">
									<span> Volver </span>
								</logic:equal>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<logic:notEqual name="convocatorias" value="<%=Constantes.SI%>">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="OCAPComponentesCtes.do?accion=borrar&cCompfqsIdS=<bean:write name="OCAPComponentesfqsForm" property="CCompfqs_id"/>&opcion=<bean:write name="opcion"/>">
									<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
									alt="Aceptar Eliminar Componente" /> <span> <%=Constantes.SI_TEXTO%>
								</span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</logic:notEqual>
				</div>
				<html:hidden property="CCompfqs_id" />
				<html:hidden property="CCte_id" />

				<div class="espaciador"></div>

			</div>
		</html:form>
	</div>

</div>
<!-- /Fin de ocultarCampo -->

