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
   document.forms[0].BContestado[0].checked=false;
   document.forms[0].BContestado[1].checked=false;
   document.forms[0].CTipoDuda[0].checked=false;
   document.forms[0].CTipoDuda[1].checked=false;
   //document.forms[0].codigoEPR.value='';
   //document.forms[0].FInicio.value='';
   //document.forms[0].FFin.value='';
   //document.forms[0].FInicioRespuesta.value='';
   //document.forms[0].FFinRespuesta.value='';
   enviar('OCAPDudasTutores.do?accion=irCSVDudas');
}

function cargarComboEspecialidades(){
   enviar('OCAPDudasTutores.do?accion=cargarComboEspecialidades&CProfesionalId='+ document.forms[0].CProfesionalId.value+'&vuelta=CSV');
}

function cargarComboTemas(tipoDuda){
   enviar('OCAPDudasTutores.do?accion=cargarComboTemas&CTipoDuda='+ tipoDuda + '&vuelta=CSV');
}

function generarCSV(){
   document.getElementById('mensajeSinDatos').style.visibility='hidden';
   document.getElementById('mensajeSinDatos').style.display='none';
   enviar('OCAPDudasTutores.do?accion=generarCSVDudas');
}
</script>

<div class="contenido contenidoFaseIII">

	<html:form action="/OCAPDudasTutores.do">
		<h2 class="tituloContenido">CUADRO DE MANDO DE DUDAS</h2>
		<h3 class="subTituloContenido">CSV</h3>

		<div class="lineaBajaM"></div>
		<div class="espaciador"></div>

		<fieldset>
			<legend class="tituloLegend"> Buscador </legend>

			<div class="cuadroFieldset formulario">
				<div class="dosTercios saltoL">
					<label class="nombreLargoCSV" for="idVApell1">
						C&oacute;digo EPR: </label>
					<html:text styleClass="corto" name="OCAPDudasTutoresForm"
						property="codigoEPR" maxlength="9" />
					<html:errors property="codigoEPR" />
				</div>

				<div class="todo">
					<label class="nombreLargoCSV" for="idVGerencia"> Gerencia:
					</label>
					<html:select styleClass="cuadroTodoP" property="CGerenciaId"
						size="1">
						<html:option value="">Seleccione...</html:option>
						<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
							property="CGerenciaId" labelProperty="DNombre" />
					</html:select>
				</div>
				<div class="todo">
					<label class="nombreLargoCSV" for="idVCProfesional">
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
					<label class="nombreLargoCSV" for="idVEspecialidad">Especialidad:
					</label>
					<html:select styleClass="cuadroTodoP" property="CEspecialidadId"
						size="1">
						<html:option value="Seleccione">Seleccione...</html:option>
						<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
							property="CEspecId" labelProperty="DNombre" />
					</html:select>
				</div>
				<%}%>
				<div class="dosTercios">
					<label class="nombreLargoCSV" for="idVApell1"> Tipo de
						dudas: </label>
					<html:errors property="CTipoDuda" />
					<html:radio styleClass="radio" name="OCAPDudasTutoresForm"
						property="CTipoDuda"
						value="<%=Constantes.TIPO_DUDA_METODOLOGICA%>"
						onclick="cargarComboTemas('1');" />
					Metodol&oacute;gicas
					<html:radio styleClass="radio" name="OCAPDudasTutoresForm"
						property="CTipoDuda" value="<%=Constantes.TIPO_DUDA_TECNICA%>"
						onclick="cargarComboTemas('2');" />
					T&eacute;cnicas
				</div>
				<div class="todo">
					<label class="nombreLargoCSV" for="idVTema"> Tema: </label>
					<html:select styleClass="cuadroTodoP" name="OCAPDudasTutoresForm"
						property="CTemaId">
						<html:option value="">Seleccione...</html:option>
						<%--html:options collection="<%=Constantes.COMBO_TEMAS%>" property="CTemaId" labelProperty="DTema"/--%>
						<% String grupoTipoDudaAnt = "";%>
						<logic:iterate id="tema" name="<%=Constantes.COMBO_TEMAS%>"
							type="OCAPDudasTutoresOT">
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
				<div class="dosTercios">
					<label class="nombreLargoCSV" for="idVApell1">Estado: </label>
					<html:radio styleClass="radio" name="OCAPDudasTutoresForm"
						property="BContestado" value="<%=Constantes.NO%>" />
					Pendientes
					<html:radio styleClass="radio" name="OCAPDudasTutoresForm"
						property="BContestado" value="<%=Constantes.SI%>" />
					Resueltas
				</div>

				<div class="todoFecha">
					<label class="nombreLargoCSV" for="idVApell1"> Fecha de
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
					<html:errors property="fInicio" />
					<html:errors property="fFin" />
				</div>
				<div class="todoFecha">
					<label class="nombreLargoCSV" for="idVFRespueta1"> Fecha de
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
					<html:errors property="fInicioRespuesta" />
					<html:errors property="fFinRespuesta" />
				</div>

				<div class="todo formatoFecha">Las fechas deben estar en
					formato dd/mm/aaaa [hh:mm]</div>
			</div>


			<div class="botonesPagina colocaBotonBusc">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:generarCSV();"> <img
							src="imagenes/imagenes_ocap/dobleFlecha.gif"
							class="colocaImgPrint" alt="Buscar" /> <span> Generar CSV
						</span>
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
		<div id="mensajeSinDatos">
			<p class="mensajeAviso">
				<bean:write name="mensaje" ignore="true" />
			</p>
		</div>
	</html:form>
</div>
