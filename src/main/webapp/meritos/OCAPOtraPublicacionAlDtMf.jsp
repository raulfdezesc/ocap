
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.meritosActividad.OCAPMeractividadOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.meritosModalidad.OCAPMerModalidadOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.factoresImpacto.OCAPFactoresImpactoOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.tiposFirmante.OCAPTiposFirmanteOT"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
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

			<html:form
				action="/OCAPMeritos.do?accion=controlAccionOtraPublicacion">
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
					<html:messages id="cActividadId" property="cActividadId"
						message="true">
						<bean:write name="cActividadId" />
						<br />
					</html:messages>
					<html:messages id="cTipoId" property="cTipoId" message="true">
						<bean:write name="cTipoId" />
						<br />
					</html:messages>
					<html:messages id="cFactorId" property="cFactorId" message="true">
						<bean:write name="cFactorId" />
						<br />
					</html:messages>
					<html:messages id="cModalidadId" property="cModalidadId"
						message="true">
						<bean:write name="cModalidadId" />
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
						<logic:match name="OCAPMeritosForm" property="DNombreMerito"
							value="Póster">
							<label for="idVTitulo"> T&iacute;tulo del p&oacute;ster:
								* <html:text property="DTitulo" tabindex="1"
									styleClass="recuadroM colocaTitOtraPMC" maxlength="150" />
							</label>
							<br />
							<br />
						</logic:match>
						<label for="idVTitulo"> Nombre del Congreso: * <html:text
								property="AOrganizador" tabindex="1"
								styleClass="recuadroM colocaNombreOtraPMC" maxlength="150" />
						</label> <br />
						<br /> <label for="idVFechaI"> Fecha de
							Celebraci&oacute;n: (dd/mm/aaaa) * <html:text
								property="FExpedicion" tabindex="2"
								styleClass="recuadroM colocaFechasMC" maxlength="10" /> <logic:notPresent
								name="OCAPMeritosForm" property="BDetalle">
								<a class="iconoCalendario" id="calFPlaza"
									href='javascript:show_Calendario("OCAPMeritosForm", "FExpedicion", document.forms[0].FExpedicion.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</logic:notPresent> <logic:present name="OCAPMeritosForm" property="BDetalle">
								<logic:notEqual name="OCAPMeritosForm" property="BDetalle"
									value="<%=Constantes.SI%>">
									<a class="iconoCalendario" id="calFPlaza"
										href='javascript:show_Calendario("OCAPMeritosForm", "FExpedicion", document.forms[0].FExpedicion.value);'><html:img
											src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
								</logic:notEqual>
							</logic:present>
						</label> <br />
						<br />

						<bean:size id="tamanoListaActividades" name="OCAPMeritosForm"
							property="listaActividades" />
						<logic:notEqual name="tamanoListaActividades" value="0">
							<span> Tipo de Publicaci&oacute;n: </span>
							<br />
							<br />
							<logic:iterate id="lActividades" name="OCAPMeritosForm"
								property="listaActividades" type="OCAPMeractividadOT">
								<div class="radioEnCuadro flotaIzq radiosMC">
									<div class="flotaIzq">
										<logic:equal name="OCAPMeritosForm" property="CActividadId"
											value="<%=lActividades.getCActividadId().toString()%>">
											<input type="radio" name="CActividadId" checked="checked"
												value="<bean:write name="lActividades" property="CActividadId"/>" />
										</logic:equal>
										<logic:notEqual name="OCAPMeritosForm" property="CActividadId"
											value="<%=lActividades.getCActividadId().toString()%>">
											<input type="radio" name="CActividadId"
												value="<bean:write name="lActividades" property="CActividadId"/>" />
										</logic:notEqual>
										<span> <bean:write name="lActividades"
												property="DNombre" /> <bean:write name="lActividades"
												property="DObservaciones" /></span>
									</div>
								</div>
								<br />
								<br />
							</logic:iterate>
							<br />
						</logic:notEqual>

						<bean:size id="tamanoListaFirmantes" name="OCAPMeritosForm"
							property="listaTiposFirmante" />
						<logic:notEqual name="tamanoListaFirmantes" value="0">
							<span> Tipo de Firmante: * </span>
							<div class="radiosAcreditacionesMC">
								<logic:iterate id="lTiposFirmante" name="OCAPMeritosForm"
									property="listaTiposFirmante" type="OCAPTiposFirmanteOT">
									<div class="radioHorizontal">
										<logic:equal name="OCAPMeritosForm" property="CTipoId"
											value="<%=lTiposFirmante.getCTipoId().toString()%>">
											<input type="radio" name="CTipoId" checked="checked"
												value="<bean:write name="lTiposFirmante" property="CTipoId"/>" />
										</logic:equal>
										<logic:notEqual name="OCAPMeritosForm" property="CTipoId"
											value="<%=lTiposFirmante.getCTipoId().toString()%>">
											<input type="radio" name="CTipoId"
												value="<bean:write name="lTiposFirmante" property="CTipoId"/>" />
										</logic:notEqual>
										<span> <bean:write name="lTiposFirmante"
												property="DNombre" /> <bean:write name="lTiposFirmante"
												property="DObservaciones" /></span>
									</div>
								</logic:iterate>
							</div>
						</logic:notEqual>

						<bean:size id="tamanoListaFactores" name="OCAPMeritosForm"
							property="listaFactores" />
						<logic:notEqual name="tamanoListaFactores" value="0">
							<span> Factor de impacto: * </span>
							<br />
							<br />
							<logic:iterate id="lFactores" name="OCAPMeritosForm"
								property="listaFactores" type="OCAPFactoresImpactoOT">
								<div class="radioEnCuadro flotaIzq radiosMC">
									<div class="flotaIzq">
										<logic:equal name="OCAPMeritosForm" property="CFactorId"
											value="<%=lFactores.getCFactorId().toString()%>">
											<input type="radio" name="CFactorId" checked="checked"
												value="<bean:write name="lFactores" property="CFactorId"/>" />
										</logic:equal>
										<logic:notEqual name="OCAPMeritosForm" property="CFactorId"
											value="<%=lFactores.getCFactorId().toString()%>">
											<input type="radio" name="CFactorId"
												value="<bean:write name="lFactores" property="CFactorId"/>" />
										</logic:notEqual>
										<span> <bean:write name="lFactores" property="DNombre" />
											<bean:write name="lFactores" property="DObservaciones" /></span>
									</div>
								</div>
								<br />
								<br />
							</logic:iterate>
							<br />
							<br />
						</logic:notEqual>


						<bean:size id="tamanoListaModalidades" name="OCAPMeritosForm"
							property="listaModalidades" />
						<logic:notEqual name="tamanoListaModalidades" value="0">
							<span> &Aacute;mbito del Congreso: * </span>
							<div class="radiosAcreditacionesMC">
								<logic:iterate id="lModalidades" name="OCAPMeritosForm"
									property="listaModalidades" type="OCAPMerModalidadOT">
									<div class="radioHorizontal">
										<logic:equal name="OCAPMeritosForm" property="CModalidadId"
											value="<%=lModalidades.getCModalidadId().toString()%>">
											<input type="radio" name="CModalidadId" checked="checked"
												value="<bean:write name="lModalidades" property="CModalidadId"/>" />
										</logic:equal>
										<logic:notEqual name="OCAPMeritosForm" property="CModalidadId"
											value="<%=lModalidades.getCModalidadId().toString()%>">
											<input type="radio" name="CModalidadId"
												value="<bean:write name="lModalidades" property="CModalidadId"/>" />
										</logic:notEqual>
										<span> <bean:write name="lModalidades"
												property="DNombre" /> <bean:write name="lModalidades"
												property="DDescripcion" /></span>
									</div>
								</logic:iterate>
							</div>
						</logic:notEqual>
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