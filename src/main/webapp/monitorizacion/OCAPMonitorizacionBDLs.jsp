<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<div class="contenido">
	<div class="contenidoListadoAspirantesGCP monitorizacionBD">
		<html:form action="/OCAPNuevaSolicitud.do">
			<h2 class="tituloContenido">Monitorizaci&oacute;n</h2>
			<h3 class="subTituloContenido">Base de Datos</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<html:errors />

			<fieldset>
				<legend class="tituloLegend"> Listado de objetos de la Base
					de datos </legend>
				<div class="cuadroFieldset listadoSolicitudes">
					<div>
						<logic:notPresent name="listadoMonitorizacion" scope="request">
							<table border="0" cellpadding="2" cellspacing="0" width="82%"
								align="center">
								<tr>
									<td bgcolor="#FFFFFF" colspan="3" align="center"
										class="textoNegro reducirTexto2"><bean:message
											key="documento.noRegistros" arg0="Objetos" /></td>
								</tr>
							</table>
						</logic:notPresent>
						<logic:present name="listadoMonitorizacion" scope="request">
							<table>
								<tr class="datosTitulo lineaBajaM">
									<td class="tituloNombre">Nombre</td>
									<td class="tituloTipo">Tipo</td>
									<td class="tituloMonitorizacion">Monitorizacion</td>
								</tr>
								<logic:iterate id="tabla" name="listadoMonitorizacion"
									type="es.jcyl.fqs.ocap.ot.monitorizacion.OCAPMonitorizacionOT">
									<tr class="datosAsp">
										<td><bean:write name="tabla" property="nombreObjeto" /></td>
										<td class="tipo"><bean:write name="tabla"
												property="tipoObjeto" /></td>
										<td class="monitorizacion"><bean:write name="tabla"
												property="monitorizacion" /></td>
									</tr>
								</logic:iterate>

							</table>
						</logic:present>
						<!-- tamano != 0 -->
					</div>
				</div>
			</fieldset>
		</html:form>

	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->