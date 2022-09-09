<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script>
function limpiar(){
   document.forms[0].DApellidos.value = '';
   document.forms[0].DNombre.value='';
   document.forms[0].CDni.value='';

   <% if (request.getAttribute("opcion").equals(Constantes.FQS_CE))%>
      document.forms[0].CConvocatoria_id.value='0'; 
   <%%>  
}

</script>


<div class="contenido contenidoFaseIII">
	<div class="contenidoListadoAspirantesGCP">

		<html:form action="/OCAPComponentesCtes.do">
			<logic:equal name="opcion" value="<%=Constantes.FQS_CTE%>">
				<h3 class="subTituloContenido">Listado de Componentes de CTE</h3>
			</logic:equal>
			<logic:equal name="opcion" value="<%=Constantes.FQS_CE%>">
				<h3 class="subTituloContenido">Listado de Componentes de CE</h3>
			</logic:equal>
			<div class="lineaBajaM"></div>

			<fieldset>
				<legend class="tituloLegend"> Buscador </legend>
				<div class="cuadroFieldset formulario">
					<div class="dosTercios saltoL">
						<label for="idVApell2"> Apellidos: </label>
						<html:text property="DApellidos" tabindex="2" styleClass=""
							maxlength="60" />
					</div>
					<div class="dosTercios saltoL">
						<label for="idVNombre"> Nombre: </label>
						<html:text property="DNombre" tabindex="1" styleClass=""
							maxlength="30" />
					</div>
					<div class="unTercio saltoL">
						<label for="idVDni"> NIF/NIE: </label>
						<html:text property="CDni" tabindex="3" styleClass=""
							maxlength="10" />
					</div>
					<logic:equal name="opcion" value="<%=Constantes.FQS_CE%>">
						<div class="dosTercios saltoL">
							<label for="idVCategoria">Convocatoria:</label>
							<html:select property="CConvocatoria_id" size="1">
								<html:option value="0">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
									property="CConvocatoriaId" labelProperty="DNombre" />
							</html:select>
						</div>
					</logic:equal>
					<br />
					<br />

					<div class="botonesPagina colocaBotonBusc">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviar('OCAPComponentesCtes.do?accion=buscarComponentes&opcion=<bean:write name="opcion" />');">
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

					<table class="tablaListadoCTE">
						<logic:notPresent name="sinDatos">
							<tr>
								<th class="apellidos" id="dApellidos">Apellidos</th>
								<th class="nombre" id="dNombre">Nombre</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
							</tr>

							<logic:present name="listados" property="elementos">
								<logic:iterate id="elemento" name="listados"
									property="elementos">
									<tr>
										<td class="" headers="dApellidos"><bean:write
												name="elemento" property="DApellidos" /></td>
										<td class="" headers="dNombre"><bean:write
												name="elemento" property="DNombre" /></td>
										<td class="campoIcono"><logic:equal name="opcion"
												value="<%=Constantes.FQS_CTE%>">
												<a
													href="OCAPComponentesCtes.do?accion=irEditar&cCompfqsIdS=<bean:write name="elemento" property="CCompfqsId"/>&opcion=<bean:write name="opcion" />">
													<img src="imagenes/editar.gif" title="Editar componente"
													alt="Editar componente">
												</a>
											</logic:equal> <logic:equal name="opcion" value="<%=Constantes.FQS_CE%>">
												<logic:equal name="elemento" property="DPerfil"
													value="<%=Constantes.FQS_CE%>">
													<a
														href="OCAPComponentesCtes.do?accion=irEditar&cCompfqsIdS=<bean:write name="elemento" property="CCompfqsId"/>&opcion=<bean:write name="opcion" />">
														<img src="imagenes/editar.gif" title="Editar componente"
														alt="Editar componente">
													</a>
												</logic:equal>
											</logic:equal></td>
										<td class="campoIcono"><logic:equal name="opcion"
												value="<%=Constantes.FQS_CTE%>">
												<a
													href="OCAPComponentesCtes.do?accion=irBorrar&cCompfqsIdS=<bean:write name="elemento" property="CCompfqsId"/>&opcion=<bean:write name="opcion" />">
													<img src="imagenes/imagenes_ocap/icono_eliminar.gif"
													title="Eliminar componente" alt="Eliminar componente">
												</a>
											</logic:equal> <logic:equal name="opcion" value="<%=Constantes.FQS_CE%>">
												<logic:equal name="elemento" property="DPerfil"
													value="<%=Constantes.FQS_CE%>">
													<a
														href="OCAPComponentesCtes.do?accion=irBorrar&cCompfqsIdS=<bean:write name="elemento" property="CCompfqsId"/>&opcion=<bean:write name="opcion" />">
														<img src="imagenes/imagenes_ocap/icono_eliminar.gif"
														title="Eliminar componente" alt="Eliminar componente">
													</a>
												</logic:equal>
											</logic:equal></td>
										<td class="campoIcono"><a
											href="OCAPComponentesCtes.do?accion=irActivar&cCompfqsIdS=<bean:write name="elemento" property="CCompfqsId"/>&opcion=<bean:write name="opcion" />">
												<img src="imagenes/imagenes_ocap/activar2.gif"
												title="Activar convocatoria" alt="Activar convocatoria">
										</a></td>
									</tr>
								</logic:iterate>
							</logic:present>

						</logic:notPresent>

					</table>

					<logic:present name="listados" property="elementos">
						<div class="paginacionEvaluadores"><%@ include
								file="/comun/paginacion.jsp"%></div>
					</logic:present>

					<div class="espaciador"></div>
					<input type="hidden" name="cCompfqsIdS" value="" /> <input
						type="hidden" name="cCteIdS" value="" />
				</fieldset>
			</logic:equal>
		</html:form>
	</div>
</div>
<!-- /Fin de ocultarCampo -->
