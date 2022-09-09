<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT"%>

<% 
  String convocaAux = "";
  String convocaAct = "";  
%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>
<script>
       
function cargarComboCategorias(){
   enviar('OCAPEvaluadores.do?accion=cargarComboCategorias&opcion=<%=request.getParameter("opcion")%>&tipo=<%=Constantes.MODIFICAR%>');
}

function cargarComboEspecialidades(){
   enviar('OCAPEvaluadores.do?accion=cargarComboEspecialidades&opcion=<%=request.getParameter("opcion")%>&tipo=<%=Constantes.MODIFICAR%>');
}

function cargarComboItinerarios(){
   enviar('OCAPEvaluadores.do?accion=cargarComboItinerarios&opcion=<%=request.getParameter("opcion")%>&tipo=<%=Constantes.MODIFICAR%>');
}

</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoDCP1A altaEvaluador">
		<html:form action="/OCAPEvaluadores.do">
			<h3 class="subTituloContenido">Modificaci&oacute;n de
				Convocatoria de Evaluador</h3>

			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<div class="textoRojo">
				<html:messages id="cCteId" property="cCteId" message="true">
					<bean:write name="cCteId" />
					<br />
				</html:messages>
				<html:messages id="CConvocatoriaId" property="CConvocatoriaId"
					message="true">
					<bean:write name="CConvocatoriaId" />
					<br />
				</html:messages>
				<html:messages id="dCategoria" property="dCategoria" message="true">
					<bean:write name="dCategoria" />
					<br />
				</html:messages>
				<html:messages id="cEspec" property="cEspec" message="true">
					<bean:write name="cEspec" />
					<br />
				</html:messages>
				<html:messages id="cItinerario" property="cItinerario"
					message="true">
					<bean:write name="cItinerario" />
					<br />
				</html:messages>
				<html:messages id="CGradoId" property="CGradoId" message="true">
					<bean:write name="CGradoId" />
					<br />
				</html:messages>
			</div>

			<fieldset class="formulario">
				<html:hidden property="CCompfqs_convo_id" />
				<html:hidden property="CCompfqs_id" />
				<legend class="tituloLegend"> Datos de convocatoria </legend>
				<div class="cuadroFieldset">
					<div class="todo">
						<label for="idConv" class="nombreLargo"> Convocatoria: * </label>
						<html:select property="CConvocatoria_id" styleClass="cuadroTodoP"
							size="1" tabindex="10">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
								property="CConvocatoriaId" labelProperty="DNombre" />
						</html:select>
					</div>
					<div class="todo">
						<label for="idConv" class="nombreLargo"> Grado: *</label>
						<html:select property="CGrado_id" styleClass="cuadroTodoP"
							size="1" tabindex="10"
							onchange="javascript:cargarComboCategorias();">
							<html:option value="0">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
								property="CGradoId" labelProperty="DNombre" />
						</html:select>
					</div>
					<%if(((ArrayList) session.getAttribute(Constantes.COMBO_CATEGORIA)).size() > 0 ){%>
					<div class="todo">
						<label for="idVCategoria" class="nombreLargo">Categor&iacute;a:
							*</label>
						<html:select property="CProfesional_id" styleClass="cuadroTodoP"
							size="1" onchange="javascript:cargarComboEspecialidades();">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
								property="CProfesionalId" labelProperty="DNombre" />
						</html:select>
					</div>
					<%}%>
					<%if(((ArrayList) session.getAttribute(Constantes.COMBO_ESPECIALIDAD)).size() > 0 ){%>
					<div class="todo">
						<label for="idVEspecialidad" class="nombreLargo">Especialidad:
							*</label>
						<html:select property="CEspec_id" styleClass="cuadroTodoP"
							size="1" onchange="javascript:cargarComboItinerarios();">
							<html:option value="Seleccione">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
								property="CEspecId" labelProperty="DNombre" />
						</html:select>
					</div>
					<%}%>
					<%if(((ArrayList) session.getAttribute(Constantes.COMBO_ITINERARIOS)).size() > 0 ){%>
					<div class="todo">
						<label for="idVEspecialidad" class="nombreLargo">Manual: *</label>
						<html:select property="CItinerario_id" styleClass="cuadroTodoP"
							size="1">
							<html:option value="Seleccione">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_ITINERARIOS%>"
								property="CItinerarioId" labelProperty="DDescripcion" />
						</html:select>
					</div>
					<%}%>
					<div class="todo">
						<label for="idVCategoria" class="nombreLargo">
							Comit&eacute; al que pertenece: * </label>
						<html:select property="CCte_id" styleClass="cuadroTodoP" size="1"
							tabindex="51">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CTES%>"
								property="CCteId" labelProperty="DNombre" />
						</html:select>
					</div>
				</div>
			</fieldset>

			<div class="limpiadora"></div>
			<div class="espaciador"></div>
			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:enviar('OCAPEvaluadores.do?accion=irEditar&cCompfqsIdS=<bean:write name="OCAPComponentesfqsForm" property="CCompfqs_id"/>&opcion=<%=request.getParameter("opcion")%>&tarea=<%=Constantes.MODIFICAR%>');">
							<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Volver" />
							<span> Volver </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:enviar('OCAPEvaluadores.do?accion=modificarConv&opcion=<%=request.getParameter("opcion")%>&tarea=<%=Constantes.MODIFICAR%>');">
							<img src="imagenes/imagenes_ocap/icono_editar.gif"
							alt="Modificar" /> <span> Grabar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>

			<div id="divTexto">
				<p>
					<label class="obligadoTexto">Los campos se&ntilde;alados
						con * son obligatorios</label>
				<div class="espaciador"></div>
				</p>
			</div>
		</html:form>
	</div>
</div>
