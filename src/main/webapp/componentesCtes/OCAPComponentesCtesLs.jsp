<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>
<%@page pageEncoding="ISO-8859-1"%>
<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoDCP1A">

		<html:form action="/OCAPComponentesCtes.do">
			<h3 class="subTituloContenido">Listado de Componentes de CTE</h3>
			<div class="lineaBajaM"></div>
			<fieldset>
				<legend class="tituloLegend">
					Componente:
					<bean:write name="Dnombre" />
				</legend>
				<div class="cuadroListadoResultadosFormulario">

					<logic:present name="sinDatos">
						<div class="textoCaracteres">
							<div class="espaciador"></div>
							<bean:message key="listado.noDatos" />
						</div>
					</logic:present>

					<logic:notPresent name="sinDatos">
						<table class="resultadosFaseIII">
							<tr>
								<th>Apellidos</th>
								<th>Nombre</th>
								<th>Fecha de Alta</th>
								<th>Fecha de Baja</th>
							</tr>

							<logic:present name="listados" property="elementos">
								<logic:iterate id="elemento" name="listados"
									property="elementos">
									<tr>
										<td><bean:write name="elemento" property="DApellidos" /></td>
										<td><bean:write name="elemento" property="DNombre" /></td>
										<td><bean:write name="elemento" property="FVinculacion" /></td>
										<td><bean:write name="elemento" property="FFinalizacion" /></td>
										<td class="campoIcono"><a
											href="OCAPComponentesCtes.do?accion=irEditar&cCompfqsIdS=<bean:write name="elemento" property="CCompfqsId"/>&cCteIdS=<bean:write name="elemento" property="CCteId"/>&nombre=<bean:write name="nombre"/>&opcion=<%=Constantes.FQS_CTE%>">
												<img src="imagenes/editar.gif" title="Editar registro"
												alt="Editar registro">
										</a></td>
									</tr>
								</logic:iterate>
							</logic:present>
						</table>
					</logic:notPresent>

					<logic:present name="listados" property="elementos">
						<div class="paginacionEvaluadores"><%@ include
								file="/comun/paginacion.jsp"%></div>
					</logic:present>

					<div class="espaciador"></div>
					<input type="hidden" name="cCompfqsIdS" value="" /> <input
						type="hidden" name="cCteIdS" value="" />

					<div class="botonesPagina">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviar('OCAPGestionCtes.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');">
									<img src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Volver" /> <span> Volver </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</div>
				</div>
			</fieldset>
		</html:form>
	</div>
</div>
<!-- /Fin de ocultarCampo -->
