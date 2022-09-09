<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="java.util.ArrayList"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"></script>

<script language="JavaScript">
function cargarComboEspecialidades(){
   enviar('OCAPEvaluadores.do?accion=cargarComboEspecialidades&opcion=<%=Constantes.ENCUESTA%>&tipo=<%=Constantes.CONSULTAR%>');
}

function limpiar(){
   document.forms[0].codigo_id.value = '';
   document.forms[0].DApellidos.value = '';
   document.forms[0].DNombre.value = '';
   document.forms[0].CProfesional_id.value = '';
   <%if(((ArrayList) session.getAttribute(Constantes.COMBO_ESPECIALIDAD)).size() > 0 ){%>
      document.forms[0].CEspec_id.value = '';
   <%}%>
   document.forms[0].CItinerario_id.value = '';
   document.forms[0].BContestado.value = '';
   cargarComboEspecialidades();
}

function buscar(){
   enviar('OCAPEvaluadores.do?accion=listarEvaluados&opcion=<%=Constantes.ENCUESTA%>');
}

</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoListadoAspirantesGCP buscadorEvaluados">

		<html:form action="/OCAPEvaluadores.do">

			<h3 class="subTituloContenido">Listado de Encuestas de Evaluados
			</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<html:hidden property="CCompfqs_id" />

			<div class="textoRojo">
				<html:messages id="codigo_id" property="codigo_id" message="true">
					<bean:write name="codigo_id" />
					<br />
				</html:messages>
			</div>

			<fieldset>
				<legend class="tituloLegend"> Buscador </legend>

				<div class="cuadroFieldset formulario">
					<div class="unTercio saltoL">
						<label for="idVApell1"> C&oacute;digo:</label>
						<html:text property="codigo_id" maxlength="9" tabindex="1" />
					</div>
					<div class="unMedio">
						<label for="idVApell1"> Apellidos:</label>
						<html:text property="DApellidos" tabindex="2" maxlength="30" />
					</div>
					<div class="unMedio">
						<label for="idVNombre"> Nombre: </label>
						<html:text property="DNombre" tabindex="3" maxlength="30" />
					</div>
					<div class="todo">
						<label for="idVCategoria">Categor&iacute;a:</label>
						<html:select property="CProfesional_id" styleClass="cuadroTodoG"
							size="1" tabindex="3"
							onchange="javascript:cargarComboEspecialidades();">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
								property="CProfesionalId" labelProperty="DNombre" />
						</html:select>
					</div>

					<%if(((ArrayList) session.getAttribute(Constantes.COMBO_ESPECIALIDAD)).size() > 0 ){%>
					<div class="todo">
						<label for="idVEspecialidad">Especialidad:</label>
						<html:select property="CEspec_id" styleClass="cuadroTodoG"
							size="1" tabindex="4">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
								property="CEspecId" labelProperty="DNombre" />
						</html:select>
					</div>
					<%}%>
					<div class="todo">
						<label for="idVEspecialidad">Itinerario:</label>
						<html:select property="CItinerario_id" styleClass="cuadroTodoG"
							size="1" tabindex="2">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_ITINERARIOS%>"
								property="CItinerarioId" labelProperty="DDescripcion" />
						</html:select>
					</div>
					<div class="unTercio">
						<label for="idVCategoria">Contestado:</label>
						<html:select property="BContestado" size="1" tabindex="5">
							<html:option value="">Seleccione...</html:option>
							<html:option value="Y">Si</html:option>
							<html:option value="N">No</html:option>
						</html:select>
					</div>
				</div>

				<div class="botonesPagina colocaBotonBusc">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:buscar();" tabindex="61"> <img
								src="imagenes/imagenes_ocap/dobleFlecha.gif"
								class="colocaImgPrint" alt="Buscar" /> <span> Buscar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:limpiar();" tabindex="61"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif"
								class="colocaImgPrint" alt="Limpiar" /> <span> Limpiar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</fieldset>

			<logic:equal name="OCAPComponentesfqsForm" property="jspInicio"
				value="false">
				<div class="cuadroListadoResultadosFormulario">
					<fieldset>
						<legend class="tituloLegend"> Listado </legend>

						<logic:present name="sinDatos">
							<div class="textoCaracteres textoNegro textoNormal">
								<div class="espaciador"></div>
								<bean:message key="listado.noDatos" />
								<br />
								<br />
							</div>
						</logic:present>

						<logic:notPresent name="sinDatos">
							<logic:iterate id="listaTotal" name="listados"
								property="elementos" indexId="index">
								<bean:define id="categ" name="listaTotal" type="OCAPUsuariosOT" />
								<div class="tituloGrado">
									<bean:write name="categ" property="DProfesional_nombre" />
									<logic:notEqual name="categ" property="DEspec_nombre" value="">
                           / <bean:write name="categ"
											property="DEspec_nombre" />
									</logic:notEqual>
								</div>
								<logic:iterate id="listaItinerarios" name="categ"
									property="listaItinerarios" type="OCAPUsuariosOT">
									<div class="subTituloListado">
										<bean:write name="listaItinerarios" property="DItinerario" />
									</div>
									<div class="lineaBajaM"></div>
									<table border="0"
										class="resultadosFaseIII listadoResultadosParticulares">
										<tr>
											<th class="colApellidos" id="dApellidos">Apellidos</th>
											<th class="colNombre" id="dNombre">Nombre</th>
											<th class="colCodigo" id="cCodigo">C&oacute;digo</th>
											<th class="icono">&nbsp;</th>
										</tr>
										<logic:iterate id="listaEvaluados" name="listaItinerarios"
											property="listaCategorias" type="OCAPUsuariosOT">
											<bean:define id="categoria" name="listaEvaluados"
												type="OCAPUsuariosOT" />
											<tr>
												<td><span><bean:write name="categoria"
															property="DApellido1" /></span></td>
												<td><span><bean:write name="categoria"
															property="DNombre" /></span></td>
												<td><span><bean:write name="categoria"
															property="codigoId" /></span></td>
												<td class="campoIcono"><logic:equal name="categoria"
														property="BContestado" value="<%=Constantes.SI%>">
														<a
															href="OCAPEncuestaCalidad.do?accion=irRellenar&CExp_id=<bean:write name="categoria" property="CExpId" />">
															<img src="imagenes/imagenes_ocap/lupaPeq.GIF"
															alt="ver encuesta" />
														</a>
													</logic:equal></td>
											</tr>
										</logic:iterate>
									</table>
								</logic:iterate>
							</logic:iterate>
						</logic:notPresent>

						<logic:present name="listados" property="elementos">
							<div class="paginacionEvaluadores"><%@ include
									file="/comun/paginacion.jsp"%></div>
						</logic:present>

					</fieldset>
				</div>
			</logic:equal>
			<div class="espaciador"></div>
		</html:form>

	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->
