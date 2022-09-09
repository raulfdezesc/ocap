<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<div class="ocultarCampo">

	<div class="contenido">
		<div class="contenidoAltaMC">
			<h2 class="tituloContenido">BAJA DE M&Eacute;RITO CURRICULAR</h2>
			<h3 class="subTituloContenido"><bean:write name="OCAPMeritosForm" property="DNombreMerito" /></h3>

			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<html:form action="/OCAPMeritos.do?accion=eliminarMerito">
				<fieldset>
					<div class="cuadroFieldset">
						<label for="idVTitulo" class="textoJustificado">
							&iquest;Est&aacute; seguro de que desea dar de baja el	m&eacute;rito <bean:write name="OCAPMeritosForm"
								property="DTitulo" />?
						</label>
					</div>
				</fieldset>
				<div class="espaciador"></div>
				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="OCAPUsuarios.do?accion=irInsertar&pestana=<bean:write name="OCAPMeritosForm" property="pestanaSeleccionada"/>">
								<img src="imagenes/imagenes_ocap/aspa_roja.gif"
								alt="Cancelar Eliminar Mérito" /> <span> <%=Constantes.NO_TEXTO%>
							</span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <img
							src="imagenes/imagenes_ocap/flecha_correcto.gif"
							alt="Aceptar Eliminar Mérito" /> <input type="submit"
							name="accionBoton" class="textoBotonInputAncho" tabindex="17"
							value="<%=Constantes.SI_TEXTO%>"
							alt="Botón para eliminar el mérito" />
						</span> <span class="derBoton"></span>
					</div>
				</div>
				<html:hidden property="pestanaSeleccionada" />
				<html:hidden property="CExpmcId" />
				<html:hidden property="CExpId" />
				<html:hidden property="DTipoMerito" />
				<html:hidden property="CEstatutId" />
				<html:hidden property="CDefMeritoId" />
			</html:form>

		</div>
		<!-- /fin de ContenidoDCP1A -->
	</div>
	<!-- /Fin de Contenido -->

</div>
<!-- /Fin de ocultarCampo -->