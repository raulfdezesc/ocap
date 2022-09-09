<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>
<script>

</script>

<div class="contenido contenidoFaseIII">

	<html:form action="/OCAPDudasTutores.do">
		<h2 class="tituloContenido">GESTI&Oacute;N DE TUTORES GU&Iacute;A
		</h2>
		<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
			<h3 class="subTituloContenido">Alta de tutor - gu&iacute;a</h3>
		</logic:equal>
		<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
			<h3 class="subTituloContenido">Modificaci&oacute;n de tutor -
				gu&iacute;a</h3>
		</logic:equal>
		<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
			<h3 class="subTituloContenido">Detalle de tutor - gu&iacute;a</h3>
		</logic:equal>

		<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
			<html:hidden property="CTutorId" />
		</logic:notEqual>

		<fieldset>
			<legend class="tituloLegend"> Datos del tutor - gu&iacute;a
			</legend>
			<div class="cuadroFieldset formulario">
				<logic:notEqual name="tipoAccion"
					value="<%=Constantes.VER_DETALLE%>">
					<div class="dosTercios">
						<label for="idVApell1"> Apellidos: *</label>
						<html:text name="OCAPDudasTutoresForm" property="DApellidosTutor"
							maxlength="61" />
						<logic:messagesPresent property="dApellidosTutor">
							<span class="nombreLargo"><html:errors
									property="dApellidosTutor" /></span>
						</logic:messagesPresent>
						<logic:messagesNotPresent property="dApellidosTutor">
							<logic:messagesPresent property="dNombreTutor">
								<span class="nombreLargo">&nbsp;</span>
							</logic:messagesPresent>
						</logic:messagesNotPresent>
					</div>

					<div class="unTercio">
						<label for="idVNombre"> Nombre: *</label>
						<html:text name="OCAPDudasTutoresForm" property="DNombreTutor"
							maxlength="30" />
						<logic:messagesPresent property="dNombreTutor">
							<span class="nombreLargo"><html:errors
									property="dNombreTutor" /></span>
						</logic:messagesPresent>
						<logic:messagesNotPresent property="dNombreTutor">
							<logic:messagesPresent property="dApellidosTutor">
								<span class="nombreLargo">&nbsp;</span>
							</logic:messagesPresent>
						</logic:messagesNotPresent>
					</div>

					<div class="unMedio">
						<label for="idVApell1"> NIF/NIE: *</label>
						<html:text styleClass="cuadroDNI" name="OCAPDudasTutoresForm"
							property="CDni" maxlength="11" />
						<logic:messagesPresent property="cDni">
							<br />
							<span class="nombreLargo"><html:errors property="cDni" />&nbsp;</span>
						</logic:messagesPresent>
					</div>

					<div class="todo">
						<label class="nombreLargo" for="idVApell1"> Correo
							electr&oacute;nico: *</label>
						<html:text styleClass="cuadroTodoP" name="OCAPDudasTutoresForm"
							property="DCorreoElectronicoTutor" maxlength="100" />
						<logic:messagesPresent property="dCorreoElectronicoTutor">
							<span class="nombreLargo"><html:errors
									property="dCorreoElectronicoTutor" /></span>
						</logic:messagesPresent>
					</div>
					<div class="todo">
						<label for="idVApell1"> Tipo: *</label>
						<html:radio styleClass="radio" name="OCAPDudasTutoresForm"
							property="CTipoTutor"
							value="<%=Integer.toString(Constantes.TIPO_TUTOR_GUIA_1)%>" />
						Tutor-gu&iacute;a Estandar
						<html:radio styleClass="radio" name="OCAPDudasTutoresForm"
							property="CTipoTutor"
							value="<%=Integer.toString(Constantes.TIPO_TUTOR_GUIA_2)%>" />
						Tutor-gu&iacute;a Organizador
						<logic:messagesPresent property="cTipoTutor">
							<br />
							<span class="nombreLargo"><html:errors
									property="cTipoTutor" /></span>
						</logic:messagesPresent>
					</div>
					<div class="todo">
						<label class="nombreLargo" for="idVApell1"> Dudas que va a
							resolver: *</label>
						<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
							<html:radio styleClass="radio" name="OCAPDudasTutoresForm"
								property="CTipoDuda"
								value="<%=Constantes.TIPO_DUDA_METODOLOGICA%>" /> Metodol&oacute;gicas                                                   
                     <html:radio styleClass="radio"
								name="OCAPDudasTutoresForm" property="CTipoDuda"
								value="<%=Constantes.TIPO_DUDA_TECNICA%>" /> T&eacute;cnicas                                       
                  </logic:equal>
						<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
							<html:radio styleClass="radio" name="OCAPDudasTutoresForm"
								property="CTipoDuda"
								value="<%=Constantes.TIPO_DUDA_METODOLOGICA%>" disabled="true" /> Metodol&oacute;gicas
                     <html:radio styleClass="radio"
								name="OCAPDudasTutoresForm" property="CTipoDuda"
								value="<%=Constantes.TIPO_DUDA_TECNICA%>" disabled="true" /> T&eacute;cnicas                           
                  </logic:notEqual>
						<logic:messagesPresent property="cTipoDuda">
							<br />
							<span class="nombreLargo"><html:errors
									property="cTipoDuda" /></span>
						</logic:messagesPresent>
					</div>
				</logic:notEqual>

				<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
					<div class="unMedio">
						<label for="idVApell1"> Apellidos:</label> <span class="resultado"><bean:write
								name="OCAPDudasTutoresForm" property="DApellidosTutor" /></span>
					</div>
					<div class="unMedio">
						<label for="idVNombre"> Nombre:</label> <span class="resultado"><bean:write
								name="OCAPDudasTutoresForm" property="DNombreTutor" /></span>
					</div>
					<div class="unTercio">
						<label for="idVApell1"> NIF/NIE: </label> <span
							class="resultado ajusteIE"><bean:write
								name="OCAPDudasTutoresForm" property="CDni" /></span>
					</div>
					<div class="todo">
						<label class="nombreLargo" for="idVApell1"> Correo
							electr&oacute;nico:</label> <span class="resultadoP"><bean:write
								name="OCAPDudasTutoresForm" property="DCorreoElectronicoTutor" /></span>
					</div>
					<div class="todo">
						<label for="idVTipoTutor"> Tipo:</label>
						<html:radio styleClass="radio" name="OCAPDudasTutoresForm"
							property="CTipoTutor"
							value="<%=Integer.toString(Constantes.TIPO_TUTOR_GUIA_1)%>"
							disabled="true" />
						Tutor-gu&iacute;a Estandar
						<html:radio styleClass="radio" name="OCAPDudasTutoresForm"
							property="CTipoTutor"
							value="<%=Integer.toString(Constantes.TIPO_TUTOR_GUIA_2)%>"
							disabled="true" />
						Tutor-gu&iacute;a Organizador
					</div>
					<div class="todo">
						<label class="nombreLargo" for="idVTipoDuda"> Dudas que va
							a resolver:</label>
						<html:radio styleClass="radio" name="OCAPDudasTutoresForm"
							property="CTipoDuda"
							value="<%=Constantes.TIPO_DUDA_METODOLOGICA%>" disabled="true" />
						Metodol&oacute;gicas
						<html:radio styleClass="radio" name="OCAPDudasTutoresForm"
							property="CTipoDuda" value="<%=Constantes.TIPO_DUDA_TECNICA%>"
							disabled="true" />
						T&eacute;cnicas
					</div>
					<div class="unTercio saltoL">
						<label for="idVActivo"> Activado:</label> <span class="resultado"><bean:write
								name="OCAPDudasTutoresForm" property="BActivado" /></span>
					</div>
					<div class="unMedio">
						<label class="nombreLargo" for="idVNDudasRecibidas"> Dudas
							recibidas:</label> <span class="resultadoCorto"><bean:write
								name="OCAPDudasTutoresForm" property="NDudasRecibidas" /></span>
					</div>
					<div class="unMedio">
						<label class="nombreLargo" for="idVNDudasRecibidas"> Dudas
							contestadas:</label> <span class="resultadoCorto"><bean:write
								name="OCAPDudasTutoresForm" property="NDudasContestadas" /></span>
					</div>
				</logic:equal>
			</div>
		</fieldset>

		<div class="espaciador"></div>
		<div class="botonesPagina">
			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="PaginaInicio.do"> <img
							src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Volver" /> <span>
								Cancelar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:enviar('OCAPDudasTutores.do?accion=insertarTutor');">
							<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
							alt="Alta tutor" /> <span> Aceptar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</logic:equal>

			<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="OCAPDudasTutores.do?accion=buscarTutores&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>">
							<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Volver" />
							<span> Volver </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:document.forms[0].CTipoDuda[0].disabled=false;document.forms[0].CTipoDuda[1].disabled=false;enviar('OCAPDudasTutores.do?accion=modificarTutor');">
							<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
							alt="Aceptar" /> <span> Aceptar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</logic:equal>

			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<logic:equal name="volverBuscador" value="<%=Constantes.SI%>">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="OCAPDudasTutores.do?accion=buscarTutores&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>">
								<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Volver" />
								<span> Volver </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:equal>
				<logic:notEqual name="volverBuscador" value="<%=Constantes.SI%>">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="OCAPDudasTutores.do?accion=irBuscarTutores"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Volver" /> <span>
									Volver </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:notEqual>
			</logic:equal>
		</div>

		<logic:notEqual name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
			<div id="divTexto">
				<p>
					<label class="obligadoTexto">Los campos se&ntilde;alados
						con * son obligatorios</label>
				<div class="espaciador"></div>
				</p>
			</div>
		</logic:notEqual>
	</html:form>
</div>
