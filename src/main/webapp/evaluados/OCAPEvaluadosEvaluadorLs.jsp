<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT"%>
<%@ page import="java.util.ArrayList"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>


<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>

<script language="JavaScript">
function buscar(){
      enviar('OCAPEvaluadores.do?accion=listarEvaluadosEvaluador&cCompfqsIdS='+ document.forms[0].CCompfqs_id.value);
}
</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoListadoAspirantesGCP">

		<html:form action="/OCAPEvaluadores.do">
			<h3 class="subTituloContenido">Listado de Evaluados</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<html:hidden property="CCompfqs_id" />

			<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL))){%>
			<fieldset>
				<legend class="tituloLegend"> Buscador </legend>

				<div class="cuadroFieldset">
					<label for="idVApell1"> C&oacute;digo: <html:text
							property="codigo_id" styleClass="cbCuadros colocaCodigo"
							maxlength="9" tabindex="1" />
					</label> <br />
					<br /> <label for="idVEspecialidad">Itinerario: <html:select
							property="CItinerario_id" styleClass="cbCuadros colocaItinerario"
							size="1" tabindex="2">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_ITINERARIOS%>"
								property="CItinerarioId" labelProperty="DItinerarioNombre" />
						</html:select>
					</label> <br />
					<br /> <label for="idVCategoria">Categor&iacute;a: <html:select
							property="CProfesional_id" styleClass="cbCuadros colocaCategoria"
							size="1" tabindex="3"
							onchange="javascript:cargarComboEspecialidades();">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
								property="CProfesionalId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br />
					<%if(((ArrayList) session.getAttribute(Constantes.COMBO_ESPECIALIDAD)).size() > 0 ){%>
					<label for="idVEspecialidad">Especialidad: <html:select
							property="CEspec_id" styleClass="cbCuadros colocaEspecialidad"
							size="1" tabindex="4">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
								property="CEspecId" labelProperty="DNombre" />
						</html:select>
					</label>
					<% } %>
				</div>

				<div class="botonesPagina colocaBotonBusc">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:buscar();" tabindex="5"> <img
								src="imagenes/imagenes_ocap/dobleFlecha.gif"
								class="colocaImgPrint" alt="Buscar" /> <span> Buscar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</fieldset>
			<%}%>

			<logic:equal name="OCAPComponentesfqsForm" property="jspInicio"
				value="false">
				<fieldset>
					<legend class="tituloLegend"> Listado </legend>

					<logic:present name="sinDatos">
						<div class="textoCaracteres">
							<div class="espaciador"></div>
							<bean:message key="listado.noDatos" />
						</div>
					</logic:present>
					<logic:notPresent name="sinDatos">
						<table class="tablaEvaluadores evaluados">

							<!-- Profesionales pendientes, sin auditorias -->
							<bean:define id="numProfesionalesPendientes"
								name="OCAPComponentesfqsForm"
								property="listaProfesionalesPendientes" />
							<logic:notEqual name="numProfesionalesPendientes" value="0">
								<tr>
									<th class="tituloSeccion" colspan="3">Listado de
										profesionales pendientes de realizar la evaluaci&oacute;n</th>
								</tr>
								<logic:iterate id="grados" name="OCAPComponentesfqsForm"
									property="listaProfesionalesPendientes" type="OCAPUsuariosOT">
									<tr>
										<th class="tituloGrado" colspan="3"><bean:write
												name="grados" property="DGrado_des" /></th>
									</tr>
									<logic:iterate id="categorias" name="grados"
										property="listaCategorias" type="OCAPUsuariosOT">
										<tr>
											<th class="tituloCategoria" colspan="3"><bean:write
													name="categorias" property="DProfesional_nombre" /></th>
										</tr>
										<tr>
											<th class="cabeceraColumna">C&oacute;digo Profesional</th>
											<th class="cabeceraColumna">Itinerario</th>
											<th></th>
										</tr>
										<logic:iterate id="elemento" name="categorias"
											property="listaProfesionales" type="OCAPUsuariosOT">
											<tr>
												<td><bean:write name="elemento" property="codigoId" /></td>
												<td><a
													href="OCAPCuestionarios.do?accion=irListar&expId=<bean:write name="elemento" property="CExpId" />&cCompfqsIdS=<bean:write name="OCAPComponentesfqsForm" property="CCompfqs_id" />&opcion=<%=request.getParameter("opcion")%>&codigo=<bean:write name="elemento" property="codigoId"/>&tipo=<%=Constantes.INF_LISTADO_FASE_III%>">
														<img src="imagenes/imagenes_ocap/lupaPeq.GIF"
														alt="ver itinerario" />
												</a></td>
												<td></td>
										</logic:iterate>
									</logic:iterate>
								</logic:iterate>
							</logic:notEqual>

							<!-- Profesionales pendientes, con auditorias -->
							<bean:define id="numProfesionalesPendientesAuditoria"
								name="OCAPComponentesfqsForm"
								property="listaProfesionalesPendientesAuditoria" />
							<logic:notEqual name="numProfesionalesPendientesAuditoria"
								value="0">
								<tr>
									<th class="tituloSeccion" colspan="3">Listado de
										profesionales pendientes de auditor&iacute;a</th>
								</tr>
								<logic:iterate id="grados" name="OCAPComponentesfqsForm"
									property="listaProfesionalesPendientesAuditoria"
									type="OCAPUsuariosOT">
									<tr>
										<th class="tituloGrado" colspan="3"><bean:write
												name="grados" property="DGrado_des" /></th>
									</tr>
									<logic:iterate id="categorias" name="grados"
										property="listaCategorias" type="OCAPUsuariosOT">
										<tr>
											<th class="tituloCategoria" colspan="3"><bean:write
													name="categorias" property="DProfesional_nombre" /></th>
										</tr>
										<tr>
											<th class="cabeceraColumna">C&oacute;digo Profesional</th>
											<th class="cabeceraColumna">Itinerario</th>
											<th class="cabeceraColumna">Gesti&oacute;n
												auditor&iacute;a</th>
										</tr>
										<logic:iterate id="elemento" name="categorias"
											property="listaProfesionales" type="OCAPUsuariosOT">
											<tr>
												<td><bean:write name="elemento" property="codigoId" /></td>
												<td><a
													href="OCAPCuestionarios.do?accion=irListar&expId=<bean:write name="elemento" property="CExpId" />&cCompfqsIdS=<bean:write name="OCAPComponentesfqsForm" property="CCompfqs_id" />&opcion=<%=request.getParameter("opcion")%>&codigo=<bean:write name="elemento" property="codigoId"/>&tipo=<%=Constantes.INF_LISTADO_FASE_III%>">
														<img src="imagenes/imagenes_ocap/lupaPeq.GIF"
														alt="ver itinerario" />
												</a></td>
												<td><a
													href="javascript:alert('gestion auditorias...');"> <img
														src="imagenes/imagenes_ocap/lupaAuditoria.gif"
														alt="ver auditoría" />
												</a></td>
										</logic:iterate>
									</logic:iterate>
								</logic:iterate>
							</logic:notEqual>

							<!-- Profesionales evaluados -->
							<bean:define id="numProfesionalesEvaluados"
								name="OCAPComponentesfqsForm"
								property="listaProfesionalesEvaluados" />
							<logic:notEqual name="numProfesionalesEvaluados" value="0">
								<tr>
									<th class="tituloSeccion" colspan="3">Listado de
										profesionales con evaluaci&oacute;n finalizada</th>
								</tr>
								<logic:iterate id="grados" name="OCAPComponentesfqsForm"
									property="listaProfesionalesEvaluados" type="OCAPUsuariosOT">
									<tr>
										<th class="tituloGrado" colspan="3"><bean:write
												name="grados" property="DGrado_des" /></th>
									</tr>
									<logic:iterate id="categorias" name="grados"
										property="listaCategorias" type="OCAPUsuariosOT">
										<tr>
											<th class="tituloCategoria" colspan="3"><bean:write
													name="categorias" property="DProfesional_nombre" /></th>
										</tr>
										<tr>
											<th class="cabeceraColumna">C&oacute;digo Profesional</th>
											<th class="cabeceraColumna">Itinerario</th>
											<th class="cabeceraColumna">Informe de evaluaci&oacute;n</th>
										</tr>
										<logic:iterate id="elemento" name="categorias"
											property="listaProfesionales" type="OCAPUsuariosOT">
											<tr>
												<td><bean:write name="elemento" property="codigoId" /></td>
												<td><a
													href="OCAPCuestionarios.do?accion=irListar&expId=<bean:write name="elemento" property="CExpId" />&cCompfqsIdS=<bean:write name="OCAPComponentesfqsForm" property="CCompfqs_id" />&opcion=<%=request.getParameter("opcion")%>&codigo=<bean:write name="elemento" property="codigoId"/>&tipo=<%=Constantes.INF_LISTADO_FASE_III%>">
														<img src="imagenes/imagenes_ocap/lupaPeq.GIF"
														alt="ver itinerario" />
												</a></td>
												<td><a
													href="OCAPCuestionarios.do?accion=generarInforme&expId=<bean:write name="elemento" property="CExpId" />&tipo=<%=Constantes.INF_LISTADO_FASE_III%>">
														<img src="imagenes/imagenes_ocap/lupaE.gif"
														alt="ver informe" />
												</a></td>
										</logic:iterate>
									</logic:iterate>
								</logic:iterate>
							</logic:notEqual>

						</table>
					</logic:notPresent>

				</fieldset>
			</logic:equal>

			<div class="espaciador"></div>
			<input type="hidden" name="cCompfqsIdS" value="" />
			<%if(jcylUsuario.getUser().getRol() != null && !(jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL)) && !(jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE))){%>
			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span>
					<logic:notEqual name="opcion" value="<%=Constantes.FQS_CTE%>">
						<span class="cenBoton"> <a
							href="javascript:enviar('OCAPEvaluadores.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=request.getParameter("opcion")%>');">
								<img src="imagenes/imagenes_ocap/aspa_roja.gif"
								class="colocaImgPrint" alt="Volver" /> <span> Volver </span>
						</a>
						</span>
					</logic:notEqual>
					<logic:equal name="opcion" value="<%=Constantes.FQS_CTE%>">
						<span class="cenBoton"> <a
							href="javascript:enviar('OCAPEvaluadores.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=request.getParameter("opcion")%>');">
								<img src="imagenes/imagenes_ocap/aspa_roja.gif"
								class="colocaImgPrint" alt="Volver" /> <span> Volver </span>
						</a>
						</span>
					</logic:equal>
					<span class="derBoton"></span>
				</div>
			</div>
			<%}%>
		</html:form>
	</div>
</div>
<!-- /Fin de ocultarcampo -->

