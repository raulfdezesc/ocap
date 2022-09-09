<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="es.jcyl.fqs.ocap.ot.gestionCtes.OCAPGestionCtesOT"%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoDCP1A">
		<html:form action="/OCAPGestionCtes.do">
			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<h3 class="subTituloContenido">Alta de CTE</h3>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<h3 class="subTituloContenido">Detalle de CTE</h3>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.ACTIVAR%>">
				<h3 class="subTituloContenido">Activaci&oacute;n de CTE</h3>
			</logic:equal>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<html:hidden property="CCte_id" />
			</logic:notEqual>

			<div class="textoRojo">
				<html:messages id="CConvocatoriaId" property="CConvocatoriaId"
					message="true">
					<bean:write name="CConvocatoriaId" />
					<br />
				</html:messages>
				<html:messages id="dNombre" property="dNombre" message="true">
					<bean:write name="dNombre" />
					<br />
				</html:messages>
				<html:messages id="fConstitucion" property="fConstitucion"
					message="true">
					<bean:write name="fConstitucion" />
					<br />
				</html:messages>
				<html:messages id="cGerenciaId" property="cGerenciaId"
					message="true">
					<bean:write name="cGerenciaId" />
					<br />
				</html:messages>
			</div>
			<fieldset class="formulario">
				<legend class="tituloLegend"> Datos </legend>
				<div class="cuadroFieldset">
					<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<div class="todo">
							<label for="idVNombre">* Nombre: </label>
							<html:text property="DNombre" tabindex="1"
								styleClass="cuadroTodoG" maxlength="100" />
						</div>
						<div class="unMedio" style="width: 39.2%;">
							<label class="completo" for="idVFechaC">* Fecha de
								constituci&oacute;n: (dd/mm/aaaa)</label>
						</div>
						<div class="unTercio">
							<html:text property="FConstitucion" tabindex="2" maxlength="10" />
							<a id="calFCons"
								href='javascript:show_Calendario("OCAPGestionCtesForm", "FConstitucion", document.forms[0].FConstitucion.value);'><html:img
									src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
						</div>
						<div class="cuadroFieldset">
							<label for="idVGerencia">* Gerencia: </label>
							<html:select property="CGerenciaId"
								styleClass="cbCuadros colocaCategoriaGenActas" size="1">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
									property="CGerenciaId" labelProperty="DNombre" />
							</html:select>

						</div>

					</logic:equal>

					<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<div class="todo">
							<label for="idVNombre"> Nombre:</label>
							<html:text property="DNombre" tabindex="1"
								styleClass="cuadroTodoActiva" maxlength="100" readonly="true" />
						</div>
						<div class="todoFecha">
							<label class="nombreFechaActiva" for="idVCorreo"> Fecha
								de constituci&oacute;n (dd/mm/aaaa):</label>
							<html:text property="FConstitucion" tabindex="2" size="10"
								maxlength="10" readonly="true" />
							&nbsp;
						</div>
						<div class="todo">
							<label for="idVGerencia" class="nombreFechaActiva">
								Gerencia:</label>
							<html:text property="DNombreGerencia" tabindex="1"
								styleClass="cuadroTodoActiva" maxlength="100" readonly="true" />
						</div>



					</logic:notEqual>

					<logic:equal name="tipoAccion" value="<%=Constantes.ACTIVAR%>">
						<logic:notEqual name="tipoAccion"
							value="<%=Constantes.VER_DETALLE%>">
							<div class="todo">
								<label for="idConv" class="nombreMediano"> Convocatoria:
									*</label>
								<html:select property="CConvocatoria_id"
									styleClass="cuadroTodoP" size="1" tabindex="5">
									<html:option value="">Seleccione...</html:option>
									<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
										property="CConvocatoriaId" labelProperty="DNombre" />
								</html:select>
							</div>
						</logic:notEqual>
					</logic:equal>

					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<fieldset>
							<legend class="tituloLegend">Convocatorias activas</legend>
							<bean:size id="tamanoLista" name="OCAPGestionCtesForm"
								property="listadoConv" />
							<div class="textoCaracteres">
								<logic:equal name="tamanoLista" value="0">
									<br />
									<label class="nombreLineaCompleta"><span>El CTE
											no tiene convocatorias activas</span></label>
									<br />
									<br />
								</logic:equal>
							</div>
							<logic:notEqual name="tamanoLista" value="0">
								<div class="cuadroFieldset">
									<table class="tablaCertif">
										<logic:iterate id="elemento" name="OCAPGestionCtesForm"
											property="listadoConv" type="OCAPGestionCtesOT">
											<tr>
												<td class="textoTabla" headers="dConv"><bean:write
														name="elemento" property="DConvocNombre" /></td>
											</tr>
										</logic:iterate>
									</table>
								</div>
							</logic:notEqual>
						</fieldset>
					</logic:equal>
				</div>
			</fieldset>

			<div class="espaciador"></div>
			<div class="botonesPagina">
				<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviar('OCAPGestionCtes.do?accion=insertar');">
								<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Alta CTE" /> <span> Aceptar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:equal>
				<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviar('OCAPGestionCtes.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');">
								<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Volver" />
								<span> Volver </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<logic:equal name="tipoAccion" value="<%=Constantes.ACTIVAR%>">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviar('OCAPGestionCtes.do?accion=grabar');">
									<img src="imagenes/imagenes_ocap/icono_editar.gif"
									alt="Modificar" /> <span> Grabar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</logic:equal>
				</logic:notEqual>
			</div>

			<div id="divTexto">
				<p>
					<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label class="obligadoTexto">Los campos se&ntilde;alados
							con * son obligatorios</label>
						<div class="espaciador"></div>
					</logic:equal>
				</p>
			</div>
		</html:form>
	</div>

</div>
<!-- /Fin de ocultarCampo -->