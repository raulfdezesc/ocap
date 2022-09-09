<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.dudasTutores.OCAPDudasTutoresOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="java.util.ArrayList"%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="JavaScript">
function cargarComboTemas(){
   enviar('OCAPDudasTutores.do?accion=cargarComboTemas&CTipoDuda='+ document.forms[0].CTipoDuda.value +'&vuelta=Dudas');
}
</script>

<div class="contenido contenidoFaseIII">

	<html:form action="/OCAPDudasTutores.do">
		<h2 class="tituloContenido">evalu@netFQS (tutor - gu&iacute;a)</h2>
		<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
			<h3 class="subTituloContenido">Nueva duda</h3>
		</logic:equal>
		<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
			<h3 class="subTituloContenido">Respuesta</h3>
		</logic:equal>
		<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
			<h3 class="subTituloContenido">Consulta</h3>
		</logic:equal>

		<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
			<html:hidden property="CDudaId" />
			<html:hidden property="CTemaId" />
			<html:hidden property="DCorreoElectronico" />
			<html:hidden property="DDuda" />
			<html:hidden property="DTema" />
			<html:hidden property="CTipoDuda" />
		</logic:notEqual>
		<html:hidden property="CExpId" />
		<html:hidden property="CTutorId" />
		<html:hidden property="codigoEPR" />

		<fieldset>
			<div class="cuadroFieldset formulario formularioDudas">
				<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
					<div class="todo">
						<p>
							Para poder asegurar una respuesta r&aacute;pida a su pregunta,
							por favor t&oacute;mese un momento para mirar nuestras <a
								class="enlaceDudaFrec"
								href="javascript:enviar('OCAPDudasTutores.do?accion=irPreguntasFrecuentes');">Preguntas
								m&aacute;s frecuentes</a> donde encontrar&aacute; respuestas a las
							preguntas m&aacute;s comunes recibidas en la FQS.
						</p>
						<p>Si a&uacute;n as&iacute; no ha encontrado una respuesta
							satisfactoria, complete la informaci&oacute;n solicitada en el
							siguiente formulario y un tutor-gu&iacute;a le ayudar&aacute;:</p>
						<p>Existen dos tipos de dudas:
						<ul class="tiposDudas">
							<li>Si usted tiene problemas con el propio proceso de
								evaluaci&oacute;n su duda es de tipo metodol&oacute;gica.</li>
							<li>Si usted tiene problemas con la aplicaci&oacute;n
								inform&aacute;tica, problemas de impresi&oacute;n, problemas de
								acceso a cualquier formulario la duda es de tipo t&eacute;cnica.
							</li>
						</ul>
						</p>
					</div>
					<div class="todo">
						<label class="nombreLargo" for="idVApell1"> C&oacute;digo
							EPR: *</label> <span class="resultadoP dudaCodigoEPR corto"><bean:write
								name="OCAPDudasTutoresForm" property="codigoEPR" /></span>
					</div>
					<div class="todo">
						<label class="nombreLargo" for="idVTipoDuda"> Tipo de
							duda: *</label>
						<html:select styleClass="cuadroTodoP" name="OCAPDudasTutoresForm"
							property="CTipoDuda" onchange="cargarComboTemas();">
							<html:option value="">Seleccione...</html:option>
							<html:option value="<%=Constantes.TIPO_DUDA_METODOLOGICA%>">Metodol&oacute;gica</html:option>
							<html:option value="<%=Constantes.TIPO_DUDA_TECNICA%>">T&eacute;cnica</html:option>
						</html:select>
						<logic:messagesPresent property="cTipoDuda">
							<span class="nombreLargo"><html:errors
									property="cTipoDuda" /></span>
						</logic:messagesPresent>
					</div>
					<%if(((ArrayList) session.getAttribute(Constantes.COMBO_TEMAS)).size() > 0 ){%>
					<div class="todo">
						<label class="nombreLargo" for="idVTema">Tema: *</label>
						<html:select styleClass="cuadroTodoP" name="OCAPDudasTutoresForm"
							property="CTemaId">
							<html:option value="Seleccione">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_TEMAS%>"
								property="CTemaId" labelProperty="DTema" />
						</html:select>
						<logic:messagesPresent property="cTemaId">
							<span class="nombreLargo"><html:errors property="cTemaId" /></span>
						</logic:messagesPresent>
					</div>
					<%}%>
					<div class="todo">
						<label class="nombreLargo" for="idVApell1"> Correo
							electr&oacute;nico: *</label>
						<html:text styleClass="resultadoP ajusteIE"
							name="OCAPDudasTutoresForm" property="DCorreoElectronico"
							maxlength="100" />
						<logic:messagesPresent property="dCorreoElectronico">
							<span class="nombreLargo"><html:errors
									property="dCorreoElectronico" /></span>
						</logic:messagesPresent>
					</div>
					<div class="todo">
						<label class="nombreLargo" for="idVApell1">
							Descripci&oacute;n del problema: *</label>
					</div>
					<div class="todo">
						<logic:messagesPresent property="dDuda">
							<span class="nombreLargo"><html:errors property="dDuda" /></span>
						</logic:messagesPresent>
						<html:textarea name="OCAPDudasTutoresForm" property="DDuda"></html:textarea>
					</div>
				</logic:equal>

				<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
					<div class="todo">
						<label class="nombreLargo" for="idVTipoDuda"> Tipo de
							duda: </label>
						<logic:equal name="OCAPDudasTutoresForm" property="CTipoDuda"
							value="<%=Constantes.TIPO_DUDA_METODOLOGICA%>">
							<span class="resultadoP">Metodol&oacute;gica</span>
						</logic:equal>
						<logic:equal name="OCAPDudasTutoresForm" property="CTipoDuda"
							value="<%=Constantes.TIPO_DUDA_TECNICA%>">
							<span class="resultadoP">T&eacute;cnica</span>
						</logic:equal>
					</div>
					<div class="todo">
						<label class="nombreLargo" for="idVTema"> Tema: </label> <span
							class="resultadoP"><bean:write name="OCAPDudasTutoresForm"
								property="DTema" /></span>
					</div>
					<div class="todo">
						<label class="nombreLargo" for="idVApell1"> Correo
							electr&oacute;nico: </label> <span class="resultadoP"><bean:write
								name="OCAPDudasTutoresForm" property="DCorreoElectronico" /></span>
					</div>

					<div class="todo">
						<label class="nombreLargo" for="idVApell1"> C&oacute;digo
							EPR: </label> <span class="resultadoP"><bean:write
								name="OCAPDudasTutoresForm" property="codigoEPR" /></span>
					</div>
					<div class="lineaDivision"></div>
					<div class="todo">
						<label class="nombreLargo" for="idVApell1">
							Descripci&oacute;n del problema: </label>
					</div>
					<div class="todo">
						<span class="resultadoG consultaDudas"><bean:write
								name="OCAPDudasTutoresForm" property="DDuda" filter="false" /></span>
					</div>
					<div class="todo">
						<label class="nombreLargo" for="idVApell1"> Respuesta: *</label> <span>
							<logic:equal name="OCAPDudasTutoresForm" property="CTipoDuda"
								value="<%=Constantes.TIPO_DUDA_METODOLOGICA%>">
								<a class="enlaceTipoDuda"
									href="javascript:if (confirm('La duda ser\u00E1 asignada a un tutor de dudas t\u00E9cnicas. \u00BFDesea continuar?')) {enviar('OCAPDudasTutores.do?accion=cambiarTipoDuda');}">
									<%--img src="imagenes/imagenes_ocap/dobleFlecha.gif" alt="Cambiar tipo de duda" /--%>
									<span> Cambiar a duda t&eacute;cnica </span>
								</a>
							</logic:equal> <logic:equal name="OCAPDudasTutoresForm" property="CTipoDuda"
								value="<%=Constantes.TIPO_DUDA_TECNICA%>">
								<a class="enlaceTipoDuda"
									href="javascript:if (confirm('La duda ser\u00E1 asignada a un tutor de dudas metodol\u00F3gicas. \u00BFDesea continuar?')) {enviar('OCAPDudasTutores.do?accion=cambiarTipoDuda');}">
									<%--img src="imagenes/imagenes_ocap/dobleFlecha.gif" alt="Cambiar tipo de duda" /--%>
									<span> Cambiar a duda metodol&oacute;gica </span>
								</a>
							</logic:equal>
						</span>
						<logic:messagesPresent property="dRespuesta">
							<br />
							<span class="nombreLargo"><html:errors
									property="dRespuesta" /></span>
						</logic:messagesPresent>
					</div>
					<div class="todo">
						<html:textarea styleClass="consultaDudas"
							name="OCAPDudasTutoresForm" property="DRespuesta"></html:textarea>
					</div>
				</logic:equal>

				<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
					<div class="todo">
						<label class="nombreLargo" for="idVTipoDuda"> Tipo de
							duda: </label>
						<logic:equal name="OCAPDudasTutoresForm" property="CTipoDuda"
							value="<%=Constantes.TIPO_DUDA_METODOLOGICA%>">
							<span class="resultadoP">Metodol&oacute;gica</span>
						</logic:equal>
						<logic:equal name="OCAPDudasTutoresForm" property="CTipoDuda"
							value="<%=Constantes.TIPO_DUDA_TECNICA%>">
							<span class="resultadoP">T&eacute;cnica</span>
						</logic:equal>
					</div>
					<div class="todo">
						<label class="nombreLargo" for="idVApell1"> Tema: </label> <span
							class="resultadoP"><bean:write name="OCAPDudasTutoresForm"
								property="DTema" /></span>
					</div>
					<!-- Mostrar código EPR sólo si es tutor (no mostrar al usuario)-->
					<%if(jcylUsuario.getUser().getRol() != null && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIO_FASE_III) && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIOS)) { %>
					<div class="todo">
						<label class="nombreLargo" for="idVApell1"> C&oacute;digo
							EPR: </label> <span class="resultadoP"><bean:write
								name="OCAPDudasTutoresForm" property="codigoEPR" /></span>
					</div>
					<% } %>
					<div class="todo">
						<label class="nombreLargo" for="idVApell1"> Correo
							electr&oacute;nico: </label> <span class="resultadoP"><bean:write
								name="OCAPDudasTutoresForm" property="DCorreoElectronico" /></span>
					</div>
					<div class="lineaDivision"></div>
					<div class="todo">
						<label class="nombreLargo" for="idVApell1">
							Descripci&oacute;n del problema: </label>
					</div>
					<div class="todo">
						<span class="resultadoG consultaDudas"><bean:write
								name="OCAPDudasTutoresForm" property="DDuda" filter="false" /></span>
					</div>
					<logic:notEmpty name="OCAPDudasTutoresForm"
						property="FEnvioContestacion">
						<div class="todo">
							<label for="idVApell1"> Respuesta: </label>
						</div>
						<div class="todo">
							<span class="resultadoG consultaDudas"><bean:write
									name="OCAPDudasTutoresForm" property="DRespuesta"
									filter="false" /></span>
						</div>
					</logic:notEmpty>
				</logic:equal>
			</div>
		</fieldset>

		<div class="espaciador"></div>
		<logic:notEqual name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
			<div id="divTexto">
				<p>
					<label class="obligadoTexto">Los campos se&ntilde;alados
						con * son obligatorios</label>
				<div class="espaciador"></div>
				</p>
			</div>
		</logic:notEqual>
		<div class="espaciador"></div>

		<div class="botonesPagina">
			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="OCAPCuestionarios.do?accion=irExplicacionAreas"> <img
							src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" /> <span>
								Cancelar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:enviar('OCAPDudasTutores.do?accion=insertarDuda');">
							<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
							alt="Alta duda" /> <span> Aceptar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</logic:equal>

			<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="OCAPDudasTutores.do?accion=buscarDudasTutores&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>">
							<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Volver" />
							<span> Volver </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:enviar('OCAPDudasTutores.do?accion=contestarDuda');">
							<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
							alt="Modificar" /> <span> Enviar Respuestas </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:enviar('OCAPCuestionarios.do?accion=irListar&expId=<bean:write name="OCAPDudasTutoresForm" property="CExpId" />&cDudaId=<bean:write name="OCAPDudasTutoresForm" property="CDudaId" />');">
							<span> Ver itinerario </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</logic:equal>

			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIO_FASE_III) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIOS))) { %>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="OCAPDudasTutores.do?accion=buscarDudasUsuario&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>">
							<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Volver" />
							<span> Volver </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
				<% } else { %>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <logic:equal
							name="buscarDudas" value="<%=Constantes.SI%>">
							<a
								href="OCAPDudasTutores.do?accion=buscarDudas&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>">
						</logic:equal> <logic:notEqual name="buscarDudas" value="<%=Constantes.SI%>">
							<a
								href="OCAPDudasTutores.do?accion=buscarDudasTutores&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>">
						</logic:notEqual> <img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Volver" />
						<span> Volver </span> </a>
					</span> <span class="derBoton"></span>
				</div>
				<% } %>
			</logic:equal>
		</div>


	</html:form>
</div>

