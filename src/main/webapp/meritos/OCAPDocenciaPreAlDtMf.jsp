<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.meritosActividad.OCAPMeractividadOT"%>

<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"
	charset="windows-1252"></script>

<script language="JavaScript" type="text/javascript">
   function obtenerAnioFin() {
      if (parseInt(document.forms[0].FAnnio.value))
         document.forms[0].FAnnioFin.value = parseInt(document.forms[0].FAnnio.value) + parseInt(1);
      else
         document.forms[0].FAnnioFin.value = 0;
   }  
</script>

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

			<html:form action="/OCAPMeritos.do?accion=controlAccionDocenciaPre">
				<div class="mensajeErrorMC">
					<html:messages id="meritoRepetido" property="meritoRepetido"
						message="true">
						<bean:write name="meritoRepetido" />
						<br />
					</html:messages>
					<html:messages id="cActividadId" property="cActividadId"
						message="true">
						<bean:write name="cActividadId" />
						<br />
					</html:messages>
					<html:messages id="dTitulo" property="dTitulo" message="true">
						<bean:write name="dTitulo" />
						<br />
					</html:messages>
					<html:messages id="nAnnios" property="nAnnios" message="true">
						<bean:write name="nAnnios" />
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

						<bean:size id="tamanoListaActividades" name="OCAPMeritosForm"
							property="listaActividades" />
						<logic:notEqual name="tamanoListaActividades" value="0">
							<span> Tipo de Docencia: * </span>
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
						<bean:define id="nombreTipoMerito" name="OCAPMeritosForm"
							property="DNombreMerito" />
						<% if (nombreTipoMerito.toString().toUpperCase().indexOf(Constantes.MC_ASOCIADO) != -1) { %>
						<label for="idVTitulo"> Departamento: * <html:text
								property="DTitulo" tabindex="1"
								styleClass="recuadroM colocaServicioMaterialServMC"
								maxlength="150" />
						</label> <br />
						<br />
						<% } %>
						<% if (nombreTipoMerito.toString().toUpperCase().indexOf(Constantes.MC_VINCULADO) != -1) { %>
						<label for="idVTitulo"> Especialidad: * <html:text
								property="DTitulo" tabindex="1"
								styleClass="recuadroM colocaServicioMaterialServMC"
								maxlength="150" />
						</label> <br />
						<br />
						<% } %>
						<div class="flotaIzq">
							<label for="idVFechaI"> A&ntilde;o de inicio: * <html:text
									property="FAnnio" tabindex="2"
									styleClass="recuadroM colocaFechasMC" maxlength="4"
									onchange="javascript:obtenerAnioFin();" />
							</label> <label for="idVFechaF"> A&ntilde;o de fin: <html:text
									property="FAnnioFin" tabindex="2"
									styleClass="recuadroM colocaFechasMC" maxlength="4"
									readonly="true" />
							</label>

						</div>
						<br />
						<br />
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
									alt="Cancelar M&eacute;rito" /> <span> Cancelar </span>
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
										alt="Bot&oacute;n para guardar el m&eacute;rito" />
								</logic:equal> <logic:equal name="tipoAccion"
									value="<%=Constantes.VER_DETALLE%>">
									<img src="imagenes/imagenes_ocap/icon_accept.gif"
										alt="Imagen Ver Detalle Merito" />
									<input class="textoBotonInput" type="submit" name="accionBoton"
										tabindex="17" value="<%=Constantes.VER_DETALLE%>"
										alt="Bot&oacute;n para volver a la pantalla anterior" />
								</logic:equal> <logic:equal name="tipoAccion"
									value="<%=Constantes.MODIFICAR%>">
									<img src="imagenes/imagenes_ocap/icon_accept.gif"
										alt="Imagen Modificar Merito" />
									<input class="textoBotonInput" type="submit" name="accionBoton"
										tabindex="17" value="<%=Constantes.MODIFICAR%>"
										alt="Bot&oacute;n para modificar el m&eacute;rito" />
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
										alt="Cancelar M&eacute;rito" /> <span> Cancelar </span>
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
											alt="Bot&oacute;n para guardar el m&eacute;rito" />
									</logic:equal> <logic:equal name="tipoAccion"
										value="<%=Constantes.VER_DETALLE%>">
										<img src="imagenes/imagenes_ocap/icon_accept.gif"
											alt="Imagen Ver Detalle Merito" />
										<input class="textoBotonInput" type="submit"
											name="accionBoton" tabindex="17"
											value="<%=Constantes.VER_DETALLE%>"
											alt="Bot&oacute;n para volver a la pantalla anterior" />
									</logic:equal> <logic:equal name="tipoAccion"
										value="<%=Constantes.MODIFICAR%>">
										<img src="imagenes/imagenes_ocap/icon_accept.gif"
											alt="Imagen Modificar Merito" />
										<input class="textoBotonInput" type="submit"
											name="accionBoton" tabindex="17"
											value="<%=Constantes.MODIFICAR%>"
											alt="Bot&oacute;n para modificar el m&eacute;rito" />
									</logic:equal>
								</span> <span class="derBoton"></span>
							</div>
						</logic:notEqual>
						<logic:equal name="OCAPMeritosForm" property="BDetalle"
							value="<%=Constantes.SI%>">
							<jsp:include page="OCAPMeritosJS.jsp" /><!-- Boton pedir aclaraciones -->
							<script language="javascript" type="text/javascript">
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