<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="es.jcyl.fqs.ocap.ot.dudasTutores.OCAPDudasTutoresOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>

<script language="JavaScript">
function limpiar(){
   document.forms[0].codigoEPR.value='';
   document.forms[0].FInicio.value='';
   document.forms[0].FFin.value='';
   document.forms[0].FInicioRespuesta.value='';
   document.forms[0].FFinRespuesta.value='';
   
   document.forms[0].CTemaId.value='';
   document.forms[0].CGerenciaId.value='';
   document.forms[0].CProfesionalId.value='';
   <%if(((ArrayList) session.getAttribute(Constantes.COMBO_ESPECIALIDAD)).size() > 0 ){%>
      cargarComboEspecialidades();
   <% } %>
}

function cargarComboEspecialidades(){
   enviar('OCAPDudasTutores.do?accion=cargarComboEspecialidades&CProfesionalId='+ document.forms[0].CProfesionalId.value+'&vuelta=buscador');
}

function buscar(campo){
   enviar('OCAPDudasTutores.do?accion=buscarDudas&<%=Constantes.ORDENACION%>='+campo+'&<%=Pagina.PRIMER_REGISTRO_PARAMETER_NAME%>=<%=Pagina.DEFAULT_PRIMER_REGISTRO%>');
}
</script>

<div class="contenido contenidoFaseIII">

	<html:form action="/OCAPDudasTutores.do">
		<h2 class="tituloContenido">BUSCADOR DE DUDAS</h2>

		<div class="lineaBajaM"></div>
		<div class="espaciador"></div>

		<html:hidden name="OCAPDudasTutoresForm" property="BContestado" />
		<html:hidden name="OCAPDudasTutoresForm" property="CTipoDuda" />
		<html:hidden name="OCAPDudasTutoresForm" property="CTipoTutor" />
		<fieldset>
			<legend class="tituloLegend"> Buscador </legend>
			<div class="cuadroFieldset formulario">
				<div class="dosTercios saltoL">
					<label class="nombreLargo" for="idVApell1"> C&oacute;digo
						EPR: </label>
					<html:text styleClass="corto" name="OCAPDudasTutoresForm"
						property="codigoEPR" maxlength="9" />
				</div>
				<html:errors property="codigoEPR" />

				<div class="todo">
					<label class="nombreLargo" for="idVGerencia"> Gerencia: </label>
					<html:select styleClass="cuadroTodoP" property="CGerenciaId"
						size="1">
						<html:option value="">Seleccione...</html:option>
						<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
							property="CGerenciaId" labelProperty="DNombre" />
					</html:select>
				</div>
				<div class="todo">
					<label class="nombreLargo" for="idVCProfesional">
						Categor&iacute;a profesional: </label>
					<html:select styleClass="cuadroTodoP" property="CProfesionalId"
						size="1" onchange="javascript:cargarComboEspecialidades();">
						<html:option value="">Seleccione...</html:option>
						<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
							property="CProfesionalId" labelProperty="DNombre" />
					</html:select>
				</div>
				<%if(((ArrayList) session.getAttribute(Constantes.COMBO_ESPECIALIDAD)).size() > 0 ){%>
				<div class="todo">
					<label class="nombreLargo" for="idVEspecialidad">Especialidad:
					</label>
					<html:select styleClass="cuadroTodoP" property="CEspecialidadId"
						size="1">
						<html:option value="Seleccione">Seleccione...</html:option>
						<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
							property="CEspecId" labelProperty="DNombre" />
					</html:select>
				</div>
				<%}%>
				<div class="todo">
					<label class="nombreLargo" for="idVTema"> Tema: </label>
					<html:select styleClass="cuadroTodoP" name="OCAPDudasTutoresForm"
						property="CTemaId">
						<html:option value="">Seleccione...</html:option>
						<% String grupoTipoDudaAnt = "";%>
						<logic:iterate id="tema" name="OCAPDudasTutoresForm"
							property="listaTemas" type="OCAPDudasTutoresOT">
							<%if (!grupoTipoDudaAnt.equals(tema.getCTipoDuda())) {
                              if (!"".equals(grupoTipoDudaAnt)) { %>
							</optgroup>
							<% } 
                                 grupoTipoDudaAnt = tema.getCTipoDuda();
                                 if (Constantes.TIPO_DUDA_METODOLOGICA.equals(grupoTipoDudaAnt)) { %>
							<optgroup label="Metodológicas">
								<% } else {%>
								<optgroup label="Técnicas">
									<% } %>
									<% } %>
									<html:option value="<%=Long.toString(tema.getCTemaId())%>">
										<bean:write name="tema" property="DTema" />
									</html:option>
						</logic:iterate>
						<% if (!"".equals(grupoTipoDudaAnt)) { %>
						</optgroup>
						<% } %>
					</html:select>
				</div>
				<div class="todoFecha">
					<label class="nombreLargo" for="idVFEnvio"> Fecha de
						env&iacute;o entre: </label>
					<html:text name="OCAPDudasTutoresForm" property="FInicio"
						maxlength="16" />
					<a class="iconoCalendario"
						href='javascript:show_Calendario("OCAPDudasTutoresForm", "FInicio", document.forms[0].FInicio.value);'><html:img
							src="imagenes/calendario.gif" border="0" alt="Calendario" /></a> <label
						class="claseY" for="idVApell1"> y : </label>
					<html:text name="OCAPDudasTutoresForm" property="FFin"
						maxlength="16" />
					<a class="iconoCalendario"
						href='javascript:show_Calendario("OCAPDudasTutoresForm", "FFin", document.forms[0].FFin.value);'><html:img
							src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
				</div>
				<html:errors property="fInicio" />
				<html:errors property="fFin" />
				<div class="todoFecha">
					<label class="nombreLargo" for="idVFRespueta1"> Fecha de
						respuesta entre: </label>
					<html:text name="OCAPDudasTutoresForm" property="FInicioRespuesta"
						maxlength="16" />
					<a class="iconoCalendario"
						href='javascript:show_Calendario("OCAPDudasTutoresForm", "FInicioRespuesta", document.forms[0].FInicioRespuesta.value);'><html:img
							src="imagenes/calendario.gif" border="0" alt="Calendario" /></a> <label
						class="claseY" for="idVApell1"> y : </label>
					<html:text name="OCAPDudasTutoresForm" property="FFinRespuesta"
						maxlength="16" />
					<a class="iconoCalendario"
						href='javascript:show_Calendario("OCAPDudasTutoresForm", "FFinRespuesta", document.forms[0].FFinRespuesta.value);'><html:img
							src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
				</div>
				<html:errors property="fInicioRespuesta" />
				<html:errors property="fFinRespuesta" />
				<div class="todo formatoFecha">Las fechas deben estar en
					formato dd/mm/aaaa [hh:mm]</div>
			</div>

			<div class="botonesPagina colocaBotonBusc">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:enviar('OCAPDudasTutores.do?accion=buscarDudas');">
							<img src="imagenes/imagenes_ocap/dobleFlecha.gif"
							class="colocaImgPrint" alt="Buscar" /> <span> Buscar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:limpiar();"> <img
							src="imagenes/imagenes_ocap/aspa_roja.gif" class="colocaImgPrint"
							alt="Limpiar" /> <span> Limpiar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>
		</fieldset>

		<div class="espaciador"></div>

		<logic:equal name="OCAPDudasTutoresForm" property="jspInicio"
			value="false">
			<div class="cuadroListadoResultadosFormulario">
				<fieldset>
					<legend class="tituloLegend"> Listado </legend>

					<logic:present name="sinDatos">
						<div class="textoCaracteres">
							<div class="espaciador"></div>
							<bean:message key="listado.noDatos" />
						</div>
					</logic:present>

					<table border="0" class="resultadosFaseIII buscadorDudas">
						<logic:notPresent name="sinDatos">
							<tr>
								<th id="codigoEPR"><a href="javascript:buscar('c_exp_id');"
									title="Ordenar por EPR"> EPR <logic:equal
											name="<%=Constantes.ORDENACION%>" value="c_exp_id">
											<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
												value="0">
												<html:img src="imagenes/imagenes_ocap/flechaBlancaDes.gif"
													border="0" alt="Descendente" />
											</logic:equal>
											<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
												value="1">
												<html:img src="imagenes/imagenes_ocap/flechaBlancaAsc.gif"
													border="0" alt="Ascendente" />
											</logic:equal>
										</logic:equal>
								</a></th>
								<th id="categEspec"><a
									href="javascript:buscar('c_profesional_id');"
									title="Ordenar por Categoría/Especialidad">Categor&iacute;a/Especialidad
										<logic:equal name="<%=Constantes.ORDENACION%>"
											value="c_profesional_id">
											<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
												value="0">
												<html:img src="imagenes/imagenes_ocap/flechaBlancaDes.gif"
													border="0" alt="Descendente" />
											</logic:equal>
											<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
												value="1">
												<html:img src="imagenes/imagenes_ocap/flechaBlancaAsc.gif"
													border="0" alt="Ascendente" />
											</logic:equal>
										</logic:equal>
								</a></th>
								<th id="cTemaId"><a href="javascript:buscar('d_tema');"
									title="Ordenar por tema">Tema <logic:equal
											name="<%=Constantes.ORDENACION%>" value="d_tema">
											<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
												value="0">
												<html:img src="imagenes/imagenes_ocap/flechaBlancaDes.gif"
													border="0" alt="Descendente" />
											</logic:equal>
											<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
												value="1">
												<html:img src="imagenes/imagenes_ocap/flechaBlancaAsc.gif"
													border="0" alt="Ascendente" />
											</logic:equal>
										</logic:equal>
								</a></th>
								<th id="fRecepcion"><a
									href="javascript:buscar('f_recepcion');"
									title="Ordenar por fecha de recepción de la duda">F.
										Recepci&oacute;n <logic:equal
											name="<%=Constantes.ORDENACION%>" value="f_recepcion">
											<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
												value="0">
												<html:img src="imagenes/imagenes_ocap/flechaBlancaDes.gif"
													border="0" alt="Descendente" />
											</logic:equal>
											<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
												value="1">
												<html:img src="imagenes/imagenes_ocap/flechaBlancaAsc.gif"
													border="0" alt="Ascendente" />
											</logic:equal>
										</logic:equal>
								</a></th>
								<th class="tiempoRespuesta" id="tiempoRespuesta"><a
									href="javascript:buscar('n_tiempo_respuesta');"
									title="Ordenar por tiempo de respuesta">T. Respuesta <logic:equal
											name="<%=Constantes.ORDENACION%>" value="n_tiempo_respuesta">
											<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
												value="0">
												<html:img src="imagenes/imagenes_ocap/flechaBlancaDes.gif"
													border="0" alt="Descendente" />
											</logic:equal>
											<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
												value="1">
												<html:img src="imagenes/imagenes_ocap/flechaBlancaAsc.gif"
													border="0" alt="Ascendente" />
											</logic:equal>
										</logic:equal>
								</a></th>
								<th id="tutor"><a href="javascript:buscar('d_nombre');"
									title="Ordenar por nombre del tutor">Tutor <logic:equal
											name="<%=Constantes.ORDENACION%>" value="d_nombre">
											<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
												value="0">
												<html:img src="imagenes/imagenes_ocap/flechaBlancaDes.gif"
													border="0" alt="Descendente" />
											</logic:equal>
											<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
												value="1">
												<html:img src="imagenes/imagenes_ocap/flechaBlancaAsc.gif"
													border="0" alt="Ascendente" />
											</logic:equal>
										</logic:equal>
								</a></th>
							</tr>
							<logic:present name="listados" property="elementos">
								<logic:iterate id="elemento" name="listados"
									property="elementos" type="OCAPDudasTutoresOT">
									<% if (Constantes.SI.equals(elemento.getBCambio())) { %>
									<tr class="fondoRojo">
										<% } else if (Constantes.SI.equals(elemento.getBProcedeCambio())) { %>
									
									<tr class="fondoNaranja">
										<% } else { %>
									
									<tr>
										<% } %>
										<td class="separador" headers="codigoEPR"><bean:write
												name="elemento" property="codigoEPR" /></td>
										<td class="separador" headers="categEspec"><bean:write
												name="elemento" property="DProfesional" /></td>
										<td class="separador" headers="cTemaId"><a
											href="OCAPDudasTutores.do?accion=detalleDuda&cDudaIdS=<bean:write name="elemento" property="CDudaId"/>">
												<bean:write name="elemento" property="DTema" />
										</a></td>
										<td class="separador" headers="fRecepcion"><bean:write
												name="elemento" property="FRecepcion" /></td>
										<td class="separador" headers="tiempoRespuesta"><bean:write
												name="elemento" property="tiempoRespuesta" /></td>
										<td headers="tutor"><bean:write name="elemento"
												property="DNombreTutor" /> <bean:write name="elemento"
												property="DApellidosTutor" /></td>
									</tr>
								</logic:iterate>
							</logic:present>
						</logic:notPresent>
					</table>

					<logic:present name="listados" property="elementos">
						<div class=""><%@ include file="/comun/paginacion.jsp"%></div>
					</logic:present>

				</fieldset>
			</div>
		</logic:equal>

	</html:form>
</div>
