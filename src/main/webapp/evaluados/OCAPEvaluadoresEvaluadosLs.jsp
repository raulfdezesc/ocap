<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>

<script language="JavaScript">

</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoListadoAspirantesGCP listadoEvaluadoresEvaluados">

		<html:form action="/OCAPEvaluadores.do">
			<h3 class="subTituloContenido">Listado de Evaluadores y
				Evaluados</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<html:hidden property="CCompfqs_id" />

			<logic:equal name="OCAPComponentesfqsForm" property="jspInicio"
				value="false">
				<fieldset>
					<legend class="tituloLegend">
						CTE:
						<bean:write name="OCAPComponentesfqsForm" property="DNombreCte" />
					</legend>

					<logic:present name="sinDatos">
						<div class="textoCaracteres">
							<div class="espaciador"></div>
							Este comit&eacute; no tiene evaluadores. <br /> <br />
						</div>
					</logic:present>
					<logic:notPresent name="sinDatos">
						<table class="tablaEvaluadores">
							<tr>
								<td class="titulo">Evaluador</td>
								<td class="titulo">Evaluado</td>
							</tr>
							<logic:notPresent name="sinDatos">
								<logic:present name="listados" property="elementos">
									<logic:iterate id="elemento" name="listados"
										property="elementos" type="OCAPComponentesfqsOT">
										<tr>
											<td class="col1" headers="cCodigo"><a
												href="OCAPEvaluadores.do?accion=irEditar&cCompfqsIdS=<bean:write name="elemento" property="CCompfqsId" />&opcion=<%=Constantes.FQS_REGISTRO%>">
													<bean:write name="elemento" property="codigoId" />
											</a></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td><bean:size id="contEvaluados" name="elemento"
													property="listaEvaluados" /> <logic:equal
													name="contEvaluados" value="0">
													<span>No tiene evaluados asignados.</span>
												</logic:equal> <logic:notEqual name="contEvaluados" value="0">
													<table>
														<logic:iterate id="evaluado" name="elemento"
															property="listaEvaluados" type="OCAPComponentesfqsOT">
															<tr>
																<td class="col1" headers="cCodigo"><bean:write
																		name="evaluado" property="codigoEvalId" /></td>
																<td><a
																	href="OCAPCuestionarios.do?accion=generarInformeCTE&expId=<bean:write name="evaluado" property="CExptecompfqsId" />&tipo=<%=Constantes.INF_LISTADO_FASE_III%>">
																		<%if (evaluado.getFInformeCE() != null){%> <img
																		src="imagenes/imagenes_ocap/icono_editar_check2.gif"
																		alt="ver datos" />
																</a> <%}else{%> <img
																	src="imagenes/imagenes_ocap/icono_editar.gif"
																	alt="ver datos" /></a> <%}%></td>
															</tr>
														</logic:iterate>
													</table>
												</logic:notEqual></td>
										</tr>
									</logic:iterate>
								</logic:present>
							</logic:notPresent>
						</table>
					</logic:notPresent>

					<logic:present name="listados" property="elementos">
						<div class="paginacionEvaluadores"><%@ include
								file="/comun/paginacion.jsp"%></div>
					</logic:present>

				</fieldset>
			</logic:equal>

			<div class="espaciador"></div>
			<input type="hidden" name="cCompfqsIdS" value="" />
			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:enviar('OCAPGestionCtes.do?accion=buscar');">
							<img src="imagenes/imagenes_ocap/aspa_roja.gif"
							class="colocaImgPrint" alt="Volver" /> <span> Volver </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>
		</html:form>
	</div>
</div>
<!-- /Fin de ocultarcampo -->

