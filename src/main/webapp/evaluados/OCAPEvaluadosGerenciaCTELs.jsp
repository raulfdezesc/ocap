<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT"%>


<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>


<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
		<script type="text/javascript"
		src="<html:rewrite page='/javascript/jquery/jquery.js'/>"
		charset="windows-1252"></script>
	<script type="text/javascript"
		src="<html:rewrite page='/javascript/jquery/jquery.cascade.js'/>"
		charset="windows-1252"></script>
	<script type="text/javascript"
		src="<html:rewrite page='/javascript/jquery/jquery.cascade.ext.js'/>"
		charset="windows-1252"></script>
	<script type="text/javascript"
		src="<html:rewrite page='/javascript/jquery/jquery.templating.js'/>"
		charset="windows-1252"></script>


<script language="JavaScript">
	function buscar() {
		enviar('OCAPEvaluadores.do?accion=listarEvaluadosGerenciaCTE');
	}

	$(document).ready(function() {

		$("#CEspec_id").cascade("#CProfesional_id", {
			ajax : {
				url : 'OCAPEvaluadores.do?accion=cargarEspecialidades'
			},
			template : commonTemplate,
			match : commonMatch
		})
	})


	function commonTemplate(item) {
		return "<option value='" + item.Value + "'>" + item.Text + "</option>";
	}

	function commonMatch(selectedValue) {
		return this.When == selectedValue;
	}
</script>



<div class="contenido contenidoFaseIII">
	<div class="contenidoListadoAspirantesGCP">
	

		<html:form action="/OCAPEvaluadores.do">
			<h3 class="subTituloContenido">Listado de Evaluados</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<html:hidden property="CCompfqs_id" />


			<fieldset>
				<legend class="tituloLegend"> Buscador </legend>
				<br />
				<br /> <label for="idVConvocatoria" style="padding-right: 1%;">
					Convocatoria: </label>
				<html:select property="CConvocatoria_id"
					styleClass="cbCuadros colocaConvocatoriaGenActas" size="1" >
					<html:option value="0">Seleccione...</html:option>
					<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
						property="CConvocatoriaId" labelProperty="DNombre" />
				</html:select>

				<br />
				<br /> <label for="idVCategoria" style="padding-right: 4%;">
					Categor&iacute;a: </label>
				<html:select property="CProfesional_id" styleId="CProfesional_id" 	
					styleClass="cbCuadros colocaCategoriaGenActas" size="1"
					style="width: 80%;">
					<html:option value="0">Seleccione...</html:option>
					<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
						property="CProfesionalId" labelProperty="DNombre" />
				</html:select>
			
				<br />
				<br /> <label for="idVEspecialidad" style="padding-right: 1.5%;">
					Especialidad: </label>
				<html:select property="CEspec_id" styleId="CEspec_id"
				styleClass="cbCuadros colocaCategoriaGenActas" size="1"
					style="width: 80%;">
					<html:option value="0">Seleccione...</html:option>
					<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
						property="CEspecId" labelProperty="DNombre" />
				</html:select>
				<br />
				<br />
						<label for="idVCategoria">Estado <html:select
							property="DEstado" styleClass="cbCuadros colocaEstado4" size="1"
							tabindex="5">
							<html:option value="">Todos</html:option>
							<html:optionsCollection name="<%=Constantes.COMBO_ESTADOS%>" />
						</html:select>
					</label>
						
				<div class="botonesPagina colocaBotonBusc"  							>
					<div class="botonAccion" 		>
						<span class="izqBoton"></span> <span class="cenBoton"> <a 
							href="javascript:buscar();" tabindex="5"> <img
								src="imagenes/imagenes_ocap/dobleFlecha.gif"
								class="colocaImgPrint" alt="Buscar" /> <span> Buscar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</fieldset>


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

					<div class="espaciador"></div>

					<table class="tablaEvaluadores evaluados">
						<logic:notPresent name="sinDatos">

							<tr>
								<%if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CTE)){%>
								<th class="col1" id="cConvocatoria">Convocatoria</th>
								<%}else {%>
								<th class="col1" id="cCodigo">C&oacute;digo</th>
								<%}%>
								

								<th class="col2" id="dApellidos">Nombre</th>
								<th class="col3" id="dNombre">Apellidos</th>
								<th class="col4" id="cDni">NIF/NIE</th>

							</tr>

							<logic:present name="listados" property="elementos">
								<logic:iterate id="elemento" name="listados"
									property="elementos" type="OCAPUsuariosOT">
									<tr>
									
										<%if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CTE)){%>
										<td class="col1" headers="cConvocatoria"><bean:write name="elemento" property="DConvocatoriaNombreCorto" /></td>
										<%} else { %>
											<td class="col1" headers="cCodigo"><bean:write
												name="elemento" property="codigoId" /></td>										
										<%}%>									
										

										<td class="col2" headers="dNombre"><bean:write
												name="elemento" property="DNombre" /></td>
										<td class="col3" headers="dApellidos" style="width: 35em">
											<bean:write name="elemento" property="DApellido1" />
										</td>
										<td class="col4" headers="cDni"><bean:write
												name="elemento" property="CDni" /></td>
												
												

										<logic:notEqual name="elemento" property="CCompfqsId"
											value="0">
											<td><a
												href="OCAPCuestionarios.do?accion=irListar&expId=<bean:write name="elemento" property="CExpId" />&cCompfqsIdS=<bean:write name="elemento" property="CCompfqsId" />&opcion=<%=request.getParameter("opcion")%>&cte=<%=request.getParameter("cte")%>&codigo=<%=request.getParameter("codigo")%>&tipo=<%=Constantes.INF_LISTADO_FASE_III%>">
													<%if (elemento.getFInformeCte() != null){%> <img
													src="imagenes/imagenes_ocap/icono_editar_check2.gif"
													alt="ver datos" />
											</a> <%}else{%> <img src="imagenes/imagenes_ocap/icono_editar.gif"
												alt="ver datos" /></a> <%}%></td>
										</logic:notEqual>
									</tr>
								</logic:iterate>
							</logic:present>

						</logic:notPresent>
					</table>

					<logic:present name="listados" property="elementos">
						<div class="paginacionEvaluadores"><%@ include
								file="/comun/paginacion.jsp"%></div>
					</logic:present>

				</fieldset>
			</logic:equal>

			<div class="espaciador"></div>

		</html:form>
	</div>
</div>
<!-- /Fin de ocultarcampo -->

