<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>

<script language="JavaScript">
function limpiar(){
   document.forms[0].DApellidos.value = '';
   document.forms[0].DNombre.value='';
   document.forms[0].CDni.value='';
   <% if (!request.getAttribute("opcion").equals(Constantes.FQS_EVAL_CE))%>
      document.forms[0].ATitulacion.value='';
      document.forms[0].NAniosConv.value='';  
   <%%>
   <% if (request.getAttribute("opcion").equals(Constantes.FQS_EVAL_CE) || request.getAttribute("opcion").equals(Constantes.FQS_CP))%>
      document.forms[0].CCte_id.value=0;
   <%%>   
   <% if (!request.getAttribute("opcion").equals(Constantes.FQS_REGISTRO))%>
         document.forms[0].CConvocatoria_id.value=0; 
   <%%>      
}

</script>

<div class="contenido listadoGestionEvaluados">
	<div class="contenidoListadoAspirantesGCP listadoEvaluadoresCTE">

		<html:form action="/OCAPEvaluadores.do">
			<h2 class="tituloContenido">GESTI&Oacute;N DE EVALUADORES</h2>
			<logic:equal name="opcion" value="<%=Constantes.FQS_CP%>">
				<h3 class="subTituloContenido">Listado de Evaluadores</h3>
			</logic:equal>
			<logic:equal name="opcion" value="<%=Constantes.FQS_CTE%>">
				<h3 class="subTituloContenido">Listado de Evaluadores del CTE</h3>
				<html:hidden property="CCte_id" />
			</logic:equal>
			<logic:equal name="opcion" value="<%=Constantes.FQS_REGISTRO%>">
				<h3 class="subTituloContenido">Listado de Evaluadores sin
					convocatoria</h3>
			</logic:equal>
			<logic:equal name="opcion" value="<%=Constantes.FQS_EVAL_CE%>">
				<h3 class="subTituloContenido">Listado de Evaluadores por CTE</h3>
			</logic:equal>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<logic:notEqual name="opcion" value="<%=Constantes.FQS_CTE%>">
				<fieldset>
					<legend class="tituloLegend"> Buscador </legend>

					<div class="cuadroFieldset">
						<label for="idVApell2"> Apellidos: <html:text
								property="DApellidos" tabindex="2"
								styleClass="recuadroM colocaApellidosEvaluadores" maxlength="60" />
						</label> <label for="idVNombre"> Nombre: <html:text
								property="DNombre" tabindex="1"
								styleClass="recuadroM colocaNombreEvaluadores" maxlength="30" />
						</label> <br />
						<br /> <label for="idVDni"> NIF/NIE:</label>
						<html:text property="CDni" tabindex="1"
							styleClass="recuadroM colocaDNIEvaluadores3" maxlength="10" />

						<br />
						<br />

						<logic:notEqual name="opcion" value="<%=Constantes.FQS_EVAL_CE%>">
							<label for="idVDni"> Titulaci&oacute;n: <html:text
									property="ATitulacion" tabindex="4"
									styleClass="recuadroM colocaTitulacionBuscadorEvaluadores"
									maxlength="200" />
							</label>
							<br />
							<br />
						</logic:notEqual>

						<logic:notEqual name="opcion" value="<%=Constantes.FQS_REGISTRO%>">
							<label for="idVCategoria">Convocatoria: <html:select
									property="CConvocatoria_id"
									styleClass="cbCuadros colocaConvocatoriaCBEvaluadoresLs"
									size="1" tabindex="5">
									<html:option value="0">Seleccione...</html:option>
									<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
										property="CConvocatoriaId" labelProperty="DNombre" />
								</html:select>
							</label>
							<br />
							<br />
						</logic:notEqual>
						<logic:notEqual name="opcion" value="<%=Constantes.FQS_EVAL_CE%>">
							<label for="idVDni"> A&ntilde;o: <html:text
									property="NAniosConv" tabindex="6"
									styleClass="recuadroM colocaAnioBuscadorEvaluadores"
									maxlength="4" />
							</label>
							<br />
							<br />
						</logic:notEqual>
						<logic:equal name="opcion" value="<%=Constantes.FQS_CP%>">
							<label for="idVCategoria">Comit&eacute: <html:select
									property="CCte_id"
									styleClass="cbCuadros colocaComiteCBEvaluadoresLs" size="1"
									tabindex="6">
									<html:option value="0">Seleccione...</html:option>
									<html:options collection="<%=Constantes.COMBO_CTES%>"
										property="CCteId" labelProperty="DNombre" />
								</html:select>
							</label>
							<br />
							<br />
						</logic:equal>
						<logic:equal name="opcion" value="<%=Constantes.FQS_EVAL_CE%>">
							<label for="idVCategoria">Comit&eacute: <html:select
									property="CCte_id"
									styleClass="cbCuadros colocaComiteCBEvaluadoresLs" size="1"
									tabindex="6">
									<html:option value="0">Seleccione...</html:option>
									<html:options collection="<%=Constantes.COMBO_CTES%>"
										property="CCteId" labelProperty="DNombre" />
								</html:select>
							</label>
						</logic:equal>
					</div>

					<div class="botonesPagina colocaBotonBusc">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviar('OCAPEvaluadores.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>');">
									<img src="imagenes/imagenes_ocap/dobleFlecha.gif"
									class="colocaImgPrint" alt="Buscar" /> <span> Buscar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:limpiar();" tabindex="61"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Limpiar" /> <span> Limpiar
								</span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</div>
				</fieldset>
			</logic:notEqual>
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

					<logic:notEqual name="opcion" value="<%=Constantes.FQS_CTE%>">
						<table border="0" class="tablaEvaluadores">
							<logic:notPresent name="sinDatos">
								<logic:present name="primeraConsulta">
									<tr>
										<th class="col1" id="dApellidos">Apellidos</th>
										<th class="col2" id="dNombre">Nombre</th>
										<th class="col3" id="aTit">Titulaci&oacute;n</th>
										<th class="col4" id="cConv">Especialidad</th>
										<th>&nbsp;</th>
									</tr>
								</logic:present>
								<logic:present name="listados" property="elementos">
									<logic:iterate id="elemento" name="listados"
										property="elementos">
										<tr>
											<td class="col1" headers="dApellidos"><bean:write
													name="elemento" property="DApellidos" /></td>
											<td class="col2" headers="dNombre"><bean:write
													name="elemento" property="DNombre" /></td>
											<td class="col3" headers="aTit"><bean:write
													name="elemento" property="ATitulacion" /></td>
											<td class="col4" headers="aEspec"><bean:write
													name="elemento" property="AEspecialidad" /></td>
											<td class="colIcono"><logic:equal name="opcion"
													value="<%=Constantes.FQS_REGISTRO%>">
													<a
														href="OCAPEvaluadores.do?accion=irEditar&cCompfqsIdS=<bean:write name="elemento" property="CCompfqsId"/>&opcion=<%=request.getParameter("opcion")%>">
														<img class="imagenIzda" src="imagenes/editar.gif"
														title="Modificar" alt="Modificar">
													</a>
												</logic:equal> <logic:equal name="opcion" value="<%=Constantes.FQS_CP%>">
													<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR))){%>
													<logic:equal name="elemento" property="BEvaluacion2"
														value="<%=Constantes.SI%>">
														<a
															href="OCAPEvaluadores.do?accion=activarSegunda&activar=<%=Constantes.NO%>&cCompfqsIdS=<bean:write name="elemento" property="CCompfqsId"/>&opcion=<%=request.getParameter("opcion")%>&tarea=<%=Constantes.ACTIVAR%>">
															Activado <img class="imagenIzda"
															src="imagenes/imagenes_ocap/icono_eliminar.gif"
															title="Desactivar para Segunda Evaluaci�n"
															alt="Desactivar para Segunda Evaluaci�n">
														</a>
													</logic:equal>
													<logic:notEqual name="elemento" property="BEvaluacion2"
														value="<%=Constantes.SI%>">
														<a
															href="OCAPEvaluadores.do?accion=activarSegunda&activar=<%=Constantes.SI%>&cCompfqsIdS=<bean:write name="elemento" property="CCompfqsId"/>&opcion=<%=request.getParameter("opcion")%>&tarea=<%=Constantes.ACTIVAR%>">
															Activar <!-- <img class="imagenIzda" src="imagenes/imagenes_ocap/activar2.gif" title="A�adir Convocatoria" alt="A�adir Convocatoria"> -->
														</a>
													</logic:notEqual>
													<% } else { %>
													<a
														href="OCAPEvaluadores.do?accion=irEditar&cCompfqsIdS=<bean:write name="elemento" property="CCompfqsId"/>&opcion=<%=request.getParameter("opcion")%>&tarea=<%=Constantes.ACTIVAR%>">
														<img class="imagenIzda"
														src="imagenes/imagenes_ocap/activar2.gif"
														title="A&ntilde;adir Convocatoria"
														alt="A�adir Convocatoria">
													</a>
													<a
														href="OCAPEvaluadores.do?accion=irEditar&cCompfqsIdS=<bean:write name="elemento" property="CCompfqsId"/>&opcion=<%=request.getParameter("opcion")%>&tarea=<%=Constantes.MODIFICAR%>">
														<img class="imagenIzda"
														src="imagenes/imagenes_ocap/edit.gif"
														title="Modificar Convocatoria"
														alt="Modificar Convocatoria">
													</a>
													<a
														href="OCAPEvaluadores.do?accion=irEditar&cCompfqsIdS=<bean:write name="elemento" property="CCompfqsId"/>&opcion=<%=request.getParameter("opcion")%>&tarea=<%=Constantes.REGISTRAR%>">
														<img class="imagenIzda" src="imagenes/editar.gif"
														title="Modificar datos" alt="Modificar datos">
													</a>

													<% } %>
												</logic:equal> <logic:notEqual name="opcion"
													value="<%=Constantes.FQS_REGISTRO%>">
													<%if(jcylUsuario.getUser().getRol() != null && (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR))){%>
													<a
														href="OCAPEvaluadores.do?accion=listarEvaluados&cCompfqsIdS=<bean:write name="elemento" property="CCompfqsId"/>&nombre=<bean:write name="elemento" property="DNombre"/>&apell=<bean:write name="elemento" property="DApellidos"/>&opcion=<%=request.getParameter("opcion")%>">
														<img class="imagenIzda"
														src="imagenes/imagenes_ocap/icono_modificar.gif"
														alt="listar evaluados" />
													</a>
													<% } %>
												</logic:notEqual> <logic:notEqual name="elemento" property="DCteNombre"
													value="">
													<logic:notEqual name="opcion"
														value="<%=Constantes.FQS_REGISTRO%>">
														<a
															href="OCAPEvaluadores.do?accion=irAsignarEvaluados&cCompfqsIdS=<bean:write name="elemento" property="CCompfqsId"/>">
															<img class="imagenIzda"
															src="imagenes/imagenes_ocap/icono_opcionales.gif"
															title="Asignar Evaluados" alt="Asignar Evaluados">
														</a>
													</logic:notEqual>
												</logic:notEqual></td>
										</tr>
									</logic:iterate>
								</logic:present>
							</logic:notPresent>
						</table>
					</logic:notEqual>

					<%if ( (String)request.getAttribute("opcion") != null && ((String)request.getAttribute("opcion")).equals(Constantes.FQS_CTE)){%>
					<table border="0" class="tablaEvaluadores">
						<logic:notPresent name="sinDatos">
							<tr>
								<th class="col1">C&oacute;digo</th>
								<th class="col2" id="dApellidos">Apellidos</th>
								<th class="col3" id="dNombre">Nombre</th>
								<th class="col4" id="cDni">NIF/NIE</th>
							</tr>
							<logic:present name="listados" property="elementos">
								<logic:iterate id="elemento" name="listados"
									property="elementos">
									<tr>

										<td class="col1" headers="cCodigo"
											style="vertical-align: middle;"><bean:write
												name="elemento" property="codigoId" /></td>
										<td class="col2" headers="dNombre"
											style="vertical-align: middle;"><bean:write
												name="elemento" property="DNombre" /></td>
										<td class="col3" headers="dApellidos"
											style="vertical-align: middle;"><bean:write
												name="elemento" property="DApellidos" /></td>
										<td class="col4" headers="cDni"
											style="vertical-align: middle;"><bean:write
												name="elemento" property="CDni" /></td>
										<td><a
											href="OCAPEvaluadores.do?accion=listarEvaluados&cCompfqsIdS=<bean:write name="elemento" property="CCompfqsId"/>&opcion=<%=request.getAttribute("opcion")%>&cte=<bean:write name="OCAPComponentesfqsForm" property="CCte_id"/>&codigo=<bean:write name="elemento" property="codigoId"/>">
												<img class="imagenIzda"
												src="imagenes/imagenes_ocap/icono_modificar.gif"
												alt="listar evaluados" />
										</a></td>
									</tr>
								</logic:iterate>
							</logic:present>
						</logic:notPresent>
					</table>
					<%}%>
					<logic:present name="listados" property="elementos">
						<div class="paginacionEvaluadores"><%@ include
								file="/comun/paginacion.jsp"%></div>
					</logic:present>

				</fieldset>
			</logic:equal>

			<input type="hidden" name="cCompfqsIdS" value="" />

		</html:form>
	</div>

</div>
<!-- /Fin de ocultarCampo -->
