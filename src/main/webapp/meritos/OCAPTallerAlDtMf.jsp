<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.meritosActividad.OCAPMeractividadOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.meritosModalidad.OCAPMerModalidadOT"%>

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
function mostrarModalidades(tipo) {
   if(tipo=='<%=Constantes.SI%>') {
      document.getElementById('dModalidad').style.display='none';
      document.getElementById('dModalidad').style.visibility='hidden';

      document.getElementById('dHoras').style.display='none';
      document.getElementById('dHoras').style.visibility='hidden';
      
      document.getElementById('dCreditos').style.display='';
      document.getElementById('dCreditos').style.visibility='visible';
   } else {
      document.getElementById('dModalidad').style.display='';
      document.getElementById('dModalidad').style.visibility='visible';
      
      document.getElementById('dHoras').style.display='';
      document.getElementById('dHoras').style.visibility='visible';
      
      document.getElementById('dCreditos').style.display='none';
      document.getElementById('dCreditos').style.visibility='hidden';      
   }
}
</script>

<div class="ocultarCampo">
	<div class="contenido">
		<div class="contenidoAltaMC TallerMC">
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
			<html:form action="/OCAPMeritos.do?accion=controlAccionTaller">
				<div class="mensajeErrorMC">
					<html:messages id="bAcreditado" property="bAcreditado"
						message="true">
						<bean:write name="bAcreditado" />
						<br />
					</html:messages>
					<html:messages id="cModalidadId" property="cModalidadId"
						message="true">
						<bean:write name="cModalidadId" />
						<br />
					</html:messages>
					<html:messages id="dTitulo" property="dTitulo" message="true">
						<bean:write name="dTitulo" />
						<br />
					</html:messages>
					<html:messages id="fInicio" property="fInicio" message="true">
						<bean:write name="fInicio" />
						<br />
					</html:messages>
					<html:messages id="fFinalizacion" property="fFinalizacion"
						message="true">
						<bean:write name="fFinalizacion" />
						<br />
					</html:messages>
					<html:messages id="fFechas" property="fFechas" message="true">
						<bean:write name="fFechas" />
						<br />
					</html:messages>
					<html:messages id="nHoras" property="nHoras" message="true">
						<bean:write name="nHoras" />
						<br />
					</html:messages>
					<html:messages id="nDias" property="nDias" message="true">
						<bean:write name="nDias" />
						<br />
					</html:messages>
					<html:messages id="nCreditosCurso" property="nCreditosCurso"
						message="true">
						<bean:write name="nCreditosCurso" />
						<br />
					</html:messages>
					<html:messages id="aOrganizador" property="aOrganizador"
						message="true">
						<bean:write name="aOrganizador" />
						<br />
					</html:messages>
				</div>
				<fieldset>
					<!-- <legend class="tituloLegend"> <bean:write name="OCAPMeritosForm" property="DNombreMerito"/> </legend> -->
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
							<div class="tituloEnCuadro flotaIzq">
								<span> Tipo de Actividad Formativa: *</span>
							</div>
							<logic:iterate id="lActividades" name="OCAPMeritosForm"
								property="listaActividades" type="OCAPMeractividadOT">
								<div class="radioEnCuadro flotaIzq">
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
									<br />
									<br />
								</div>
							</logic:iterate>
							<br />
							<br />
						</logic:notEqual>
						<label for="idVTitulo"> Acreditaci&oacute;n: *
							<div class="radiosAcreditacionesMC">
								<div class="radioHorizontal">
									<logic:equal name="OCAPMeritosForm" property="BAcreditado"
										value="<%=Constantes.SI%>">
										<input type="radio" name="BAcreditado" checked="checked"
											value="<%=Constantes.SI%>"
											onclick="mostrarModalidades('<%=Constantes.SI%>');" />
										<span>Acreditado</span>
									</logic:equal>
									<logic:notEqual name="OCAPMeritosForm" property="BAcreditado"
										value="<%=Constantes.SI%>">
										<input type="radio" name="BAcreditado"
											value="<%=Constantes.SI%>"
											onclick="mostrarModalidades('<%=Constantes.SI%>');" />
										<span>Acreditado</span>
									</logic:notEqual>
								</div>
								<div class="radioHorizontal">
									<logic:equal name="OCAPMeritosForm" property="BAcreditado"
										value="<%=Constantes.NO%>">
										<input type="radio" name="BAcreditado" checked="checked"
											value="<%=Constantes.NO%>"
											onclick="mostrarModalidades('<%=Constantes.NO%>');" />
										<span>No acreditado</span>
									</logic:equal>
									<logic:notEqual name="OCAPMeritosForm" property="BAcreditado"
										value="<%=Constantes.NO%>">
										<input type="radio" name="BAcreditado"
											value="<%=Constantes.NO%>"
											onclick="mostrarModalidades('<%=Constantes.NO%>');" />
										<span>No acreditado</span>
									</logic:notEqual>
								</div>
							</div>
						</label> <span id="dModalidad" style="display: none; visibility: hidden;">
							<bean:size id="tamanoListaModalidades" name="OCAPMeritosForm"
								property="listaModalidades" /> <logic:notEqual
								name="tamanoListaModalidades" value="0">
								<span> Modalidad: *</span>
								<div class="radiosAcreditacionesMC">
									<logic:iterate id="lModalidades" name="OCAPMeritosForm"
										property="listaModalidades" type="OCAPMerModalidadOT">
										<div class="radioHorizontal">
											<logic:equal name="OCAPMeritosForm" property="CModalidadId"
												value="<%=lModalidades.getCModalidadId().toString()%>">
												<input type="radio" name="CModalidadId" checked="checked"
													value="<bean:write name="lModalidades" property="CModalidadId"/>" />
											</logic:equal>
											<logic:notEqual name="OCAPMeritosForm"
												property="CModalidadId"
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
						</span> <label for="idVTitulo"> T&iacute;tulo: * <html:text
								property="DTitulo" tabindex="1"
								styleClass="recuadroM colocaTitTallerMC" maxlength="150" />
						</label> <br />
						<br /> <label for="idVFechaI"> Fecha de Inicio:
							(dd/mm/aaaa) * <html:text property="FInicio" tabindex="2"
								styleClass="recuadroM colocaFechaTallerMC" maxlength="10" /> <logic:notPresent
								name="OCAPMeritosForm" property="BDetalle">
								<a class="iconoCalendario" id="calFPlaza"
									href='javascript:show_Calendario("OCAPMeritosForm", "FInicio", document.forms[0].FInicio.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</logic:notPresent> <logic:present name="OCAPMeritosForm" property="BDetalle">
								<logic:notEqual name="OCAPMeritosForm" property="BDetalle"
									value="<%=Constantes.SI%>">
									<a class="iconoCalendario" id="calFPlaza"
										href='javascript:show_Calendario("OCAPMeritosForm", "FInicio", document.forms[0].FInicio.value);'><html:img
											src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
								</logic:notEqual>
							</logic:present>
						</label> <label for="idVFechaF"> Fecha de Finalizaci&oacute;n:
							(dd/mm/aaaa) * <html:text property="FFinalizacion" tabindex="2"
								styleClass="recuadroM colocaFechaTallerMC" maxlength="10" /> <logic:notPresent
								name="OCAPMeritosForm" property="BDetalle">
								<a class="iconoCalendario" id="calFPlaza"
									href='javascript:show_Calendario("OCAPMeritosForm", "FFinalizacion", document.forms[0].FFinalizacion.value);'><html:img
										src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
							</logic:notPresent> <logic:present name="OCAPMeritosForm" property="BDetalle">
								<logic:notEqual name="OCAPMeritosForm" property="BDetalle"
									value="<%=Constantes.SI%>">
									<a class="iconoCalendario" id="calFPlaza"
										href='javascript:show_Calendario("OCAPMeritosForm", "FFinalizacion", document.forms[0].FFinalizacion.value);'><html:img
											src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
								</logic:notEqual>
							</logic:present>
						</label> <br />
						<br /> <span id="dHoras"
							style="display: none; visibility: hidden;"> <label
							class="infoAdicional" for="idVNHoras"> N&ordm; Horas: * <html:text
									property="NHoras" tabindex="2"
									styleClass="recuadroM colocaNHorasTAllerMC" maxlength="5" />
						</label> <label for="idVNDias"> N&ordm; D&iacute;as: <html:text
									property="NDias" tabindex="2"
									styleClass="recuadroM colocaNumTallerMC" maxlength="3" />
						</label> <br />
						<br />
						</span> <span id="dCreditos" style="display: none; visibility: hidden;">
							<label class="infoAdicional" for="idVNDias"> N&ordm; de
								Cr&eacute;ditos de la actividad formativa: * <html:text
									property="NCreditosCurso" tabindex="2"
									styleClass="recuadroM colocaNumTallerMC" maxlength="4"
									onkeypress="return soloNumerosUnDecimal(event);" />
						</label> <br />
						<br />
						</span> <label for="idVImpartido"> Organizado por: * <html:text
								property="AOrganizador" tabindex="3"
								styleClass="recuadroM colocaOrganizaTallerMC" maxlength="100" />
						</label>
					</div>
					<logic:equal name="OCAPMeritosForm" property="BAcreditado"
						value="<%=Constantes.NO%>">
						<script language="javascript" type="text/javascript">mostrarModalidades('<%=Constantes.NO%>');</script>
					</logic:equal>
					<logic:equal name="OCAPMeritosForm" property="BAcreditado"
						value="<%=Constantes.SI%>">
						<script language="javascript" type="text/javascript">mostrarModalidades('<%=Constantes.SI%>');</script>
					</logic:equal>
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
										alt="Cancelar m&eacute;rito" /> <span> Cancelar </span>
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
				<html:hidden property="CPersonalId" />
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
