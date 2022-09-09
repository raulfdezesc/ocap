<%-- esto es un include --%>

<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<div class="ocultarCampo">

	<div class="contenido">
		<div class="contenidoAltaMC">
			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<h2 class="tituloContenido">NUEVO M&Eacute;RITO CURRICULAR</h2>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<h2 class="tituloContenido">DETALLE DE M&Eacute;RITO CURRICULAR</h2>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
				<h2 class="tituloContenido">MODIFICAR M&Eacute;RITO CURRICULAR</h2>
			</logic:equal>

			<h3 class="subTituloContenidoMC">
				<bean:write name="OCAPMeritosForm" property="DNombreMerito" />
			</h3>
			<div class="lineaBajaM"></div>
			<br />
			<br />
			<br />

			<html:form action="/OCAPMeritos.do?accion=controlAccionTesis">
				<div class="mensajeErrorMC">
					<html:messages id="dTitulo" property="dTitulo" message="true">
						<bean:write name="dTitulo" />
						<br />
					</html:messages>
					<html:messages id="aOrganizador" property="aOrganizador"
						message="true">
						<bean:write name="aOrganizador" />
						<br />
					</html:messages>
					<html:messages id="fExpedicion" property="fExpedicion"
						message="true">
						<bean:write name="fExpedicion" />
						<br />
					</html:messages>
					<html:messages id="fAnnio" property="fAnnio" message="true">
						<bean:write name="fAnnio" />
						<br />
					</html:messages>
				</div>

				<fieldset>
					<!-- <legend class="tituloLegend"> <bean:write name="OCAPMeritosForm" property="DNombreMerito" /> </legend> -->
					<div class="cuadroFieldset">
						<logic:notEqual name="OCAPMeritosForm"
							property="DDescripcionMerito" value="">
							<label for="idVTitulo" class="textoJustificado"> <span
								class="textoCursivaMC"><bean:write name="OCAPMeritosForm"
										property="DDescripcionMerito" filter="false" /></span>
							</label>
							<br />
							<br />
							<br />
						</logic:notEqual>

						<label for="idVTitulo"> T&iacute;tulo: * <html:text
								property="DTitulo" tabindex="1"
								styleClass="recuadroM colocaTitTesisMC" maxlength="150" />
						</label> <br />
						<br /> <label for="idVTitulo"> Universidad : * <html:text
								property="AOrganizador" tabindex="1"
								styleClass="recuadroM colocaUniversidadTesisMC" maxlength="150" />
						</label><br /> <br /> <label for="idVFechaI"> <logic:equal
								name="OCAPMeritosForm" property="DTipoMerito"
								value="<%=Constantes.MER_TESIS_DOCTORAL%>">
                           Fecha de defensa de la Tesis: (dd/mm/aaaa) *
                           <html:text property="FExpedicion"
									tabindex="2" styleClass="recuadroM colocaFechasMC"
									maxlength="10" />
								<a class="iconoCalendario" id="calFPlaza"
									href='javascript:show_Calendario("OCAPMeritosForm", "FExpedicion", document.forms[0].FExpedicion.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</logic:equal> <logic:equal name="OCAPMeritosForm" property="DTipoMerito"
								value="<%=Constantes.MER_DIRECCION_TESIS_DOCTORAL%>">
                              Año de la dirección: (aaaa) *
                              <logic:equal name="OCAPMeritosForm"
									property="FAnnio" value="0">
									<html:text property="FAnnio" tabindex="2"
										styleClass="recuadroM colocaFechaIGestMC" maxlength="4"
										value="" onkeypress="return permitirSoloNumeros(event);" />
								</logic:equal>
								<logic:notEqual name="OCAPMeritosForm" property="FAnnio"
									value="0">
									<html:text property="FAnnio" tabindex="2"
										styleClass="recuadroM colocaFechaIGestMC" maxlength="4"
										onkeypress="return permitirSoloNumeros(event);" />
								</logic:notEqual>
							</logic:equal> <logic:equal name="OCAPMeritosForm" property="DTipoMerito"
								value="<%=Constantes.MER_CODIRECCION_TESIS_DOCTORAL%>">
                              Año de la dirección: (aaaa) *
                              <logic:equal name="OCAPMeritosForm"
									property="FAnnio" value="0">
									<html:text property="FAnnio" tabindex="2"
										styleClass="recuadroM colocaFechaIGestMC" maxlength="4"
										value="" onkeypress="return permitirSoloNumeros(event);" />
								</logic:equal>
								<logic:notEqual name="OCAPMeritosForm" property="FAnnio"
									value="0">
									<html:text property="FAnnio" tabindex="2"
										styleClass="recuadroM colocaFechaIGestMC" maxlength="4"
										onkeypress="return permitirSoloNumeros(event);" />
								</logic:notEqual>
							</logic:equal> <logic:equal name="OCAPMeritosForm" property="DTipoMerito"
								value="<%=Constantes.MER_SUFICIENCIA_INVESTIGADORA%>">
                              Año de defensa: (aaaa) *
                              <logic:equal name="OCAPMeritosForm"
									property="FAnnio" value="0">
									<html:text property="FAnnio" tabindex="2"
										styleClass="recuadroM colocaFechaIGestMC" maxlength="4"
										value="" onkeypress="return permitirSoloNumeros(event);" />
								</logic:equal>
								<logic:notEqual name="OCAPMeritosForm" property="FAnnio"
									value="0">
									<html:text property="FAnnio" tabindex="2"
										styleClass="recuadroM colocaFechaIGestMC" maxlength="4"
										onkeypress="return permitirSoloNumeros(event);" />
								</logic:notEqual>
							</logic:equal>
						</label> <br />
					</div>
				</fieldset>

				<p>Los campos se&ntilde;alados con * son obligatorios</p>

				<div class="espaciadorPeq"></div>

				<div class="botonesPagina">
					<logic:notPresent name="OCAPMeritosForm" property="BDetalle">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="OCAPUsuarios.do?accion=irInsertar&pestana=<bean:write name="OCAPMeritosForm" property="pestanaSeleccionada"/>#<bean:write name="OCAPMeritosForm" property="DTipoMerito"/>">
									<img src="imagenes/imagenes_ocap/aspa_roja.gif"
									alt="Cancelar Mérito" /> <span> Cancelar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <logic:equal
									name="tipoAccion" value="<%=Constantes.INSERTAR%>">
									<img src="imagenes/imagenes_ocap/icon_accept.gif"
										alt="Imagen Insertar Merito" />
									<input class="textoBotonInput" type="submit" name="accionBoton"
										tabindex="17" value="<%=Constantes.INSERTAR%>"
										alt="Botón para guardar el mérito" />
								</logic:equal> <logic:equal name="tipoAccion"
									value="<%=Constantes.VER_DETALLE%>">
									<img src="imagenes/imagenes_ocap/icon_accept.gif"
										alt="Imagen Ver Detalle Merito" />
									<input class="textoBotonInput" type="submit" name="accionBoton"
										tabindex="17" value="<%=Constantes.VER_DETALLE%>"
										alt="Botón para volver a la pantalla anterior" />
								</logic:equal> <logic:equal name="tipoAccion"
									value="<%=Constantes.MODIFICAR%>">
									<img src="imagenes/imagenes_ocap/icon_accept.gif"
										alt="Imagen Modificar Merito" />
									<input class="textoBotonInput" type="submit" name="accionBoton"
										tabindex="17" value="<%=Constantes.MODIFICAR%>"
										alt="Botón para modificar el mérito" />
								</logic:equal>
							</span> <span class="derBoton"></span>
						</div>
					</logic:notPresent>
					<logic:present name="OCAPMeritosForm" property="BDetalle">
						<logic:notEqual name="OCAPMeritosForm" property="BDetalle"
							value="<%=Constantes.SI%>">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="OCAPUsuarios.do?accion=irInsertar&pestana=<bean:write name="OCAPMeritosForm" property="pestanaSeleccionada"/>#<bean:write name="OCAPMeritosForm" property="DTipoMerito"/>">
										<img src="imagenes/imagenes_ocap/aspa_roja.gif"
										alt="Cancelar Mérito" /> <span> Cancelar </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <logic:equal
										name="tipoAccion" value="<%=Constantes.INSERTAR%>">
										<img src="imagenes/imagenes_ocap/icon_accept.gif"
											alt="Imagen Insertar Merito" />
										<input class="textoBotonInput" type="submit"
											name="accionBoton" tabindex="17"
											value="<%=Constantes.INSERTAR%>"
											alt="Botón para guardar el mérito" />
									</logic:equal> <logic:equal name="tipoAccion"
										value="<%=Constantes.VER_DETALLE%>">
										<img src="imagenes/imagenes_ocap/icon_accept.gif"
											alt="Imagen Ver Detalle Merito" />
										<input class="textoBotonInput" type="submit"
											name="accionBoton" tabindex="17"
											value="<%=Constantes.VER_DETALLE%>"
											alt="Botón para volver a la pantalla anterior" />
									</logic:equal> <logic:equal name="tipoAccion"
										value="<%=Constantes.MODIFICAR%>">
										<img src="imagenes/imagenes_ocap/icon_accept.gif"
											alt="Imagen Modificar Merito" />
										<input class="textoBotonInput" type="submit"
											name="accionBoton" tabindex="17"
											value="<%=Constantes.MODIFICAR%>"
											alt="Botón para modificar el mérito" />
									</logic:equal>
								</span> <span class="derBoton"></span>
							</div>
						</logic:notEqual>
						<logic:equal name="OCAPMeritosForm" property="BDetalle"
							value="<%=Constantes.SI%>">
							<jsp:include page="OCAPMeritosJS.jsp" /><!-- Boton pedir aclaraciones -->
							<script language="javascript">
                                 deshabilitarFormulario(document.forms[0]);
                              </script>
						</logic:equal>
					</logic:present>
				</div>
				<html:hidden property="pestanaSeleccionada" />
				<html:hidden property="CExpmcId" />
				<html:hidden property="CExpId" />
				<html:hidden property="DTipoMerito" />
				<html:hidden property="CEstatutId" />
				<html:hidden property="CDefMeritoId" />
				<html:hidden property="CTipoMerito" />
				<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
					<html:hidden property="accionBoton"
						value="<%=Constantes.INSERTAR%>" />
				</logic:equal>
				<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
					<html:hidden property="accionBoton"
						value="<%=Constantes.VER_DETALLE%>" />
				</logic:equal>
				<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
					<html:hidden property="accionBoton"
						value="<%=Constantes.MODIFICAR%>" />
				</logic:equal>
			</html:form>

		</div>
		<!-- /fin de ContenidoDCP1A -->
	</div>
	<!-- /Fin de Contenido -->

</div>
<!-- /Fin de ocultarCampo -->