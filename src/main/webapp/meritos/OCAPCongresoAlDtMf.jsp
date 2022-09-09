<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>

<script language="JavaScript">
function mostrarModalidades(tipo) {
   if(tipo=='<%=Constantes.SI%>'){    
      document.getElementById('dCreditos').style.display='';
      document.getElementById('dCreditos').style.visibility='visible';
   }else{
      document.getElementById('dCreditos').style.display='none';
      document.getElementById('dCreditos').style.visibility='hidden';      
   }
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

			<html:form action="/OCAPMeritos.do?accion=controlAccionCongreso">

				<div class="mensajeErrorMC">
					<html:messages id="bAcreditado" property="bAcreditado"
						message="true">
						<bean:write name="bAcreditado" />
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
					<!-- <legend class="tituloLegend"> <bean:write name="OCAPMeritosForm" property="DNombreMerito" /> </legend> -->
					<div class="cuadroFieldset">
						<logic:notEqual name="OCAPMeritosForm"
							property="DDescripcionMerito" value="">
							<label for="idVTitulo" class="textoJustificado"> <span
								class="textoCursiva"><bean:write name="OCAPMeritosForm"
										property="DDescripcionMerito" filter="false" /></span>
							</label>
							<br />
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
						</label> <label for="idVTitulo"> T&iacute;tulo: * <html:text
								property="DTitulo" tabindex="1"
								styleClass="recuadroM colocaTitCongresoMC" maxlength="150" />
						</label> <br />
						<br /> <label for="idVFechaI"> Fecha de Inicio:
							(dd/mm/aaaa) * <html:text property="FInicio" tabindex="2"
								styleClass="recuadroM colocaFechasMC" maxlength="10" /> <logic:notPresent
								name="OCAPMeritosForm" property="BDetalle">
								<a class="iconoCalendario" id="calFPlaza"
									href='javascript:show_Calendario("OCAPMeritosForm", "FInicio", document.forms[0].FInicio.value);'><html:img
										src="imagenes/calendario.gif"
										styleClass="colocaImagenCalendario" border="0"
										alt="Calendario" /></a>
							</logic:notPresent> <logic:present name="OCAPMeritosForm" property="BDetalle">
								<logic:notEqual name="OCAPMeritosForm" property="BDetalle"
									value="<%=Constantes.SI%>">
									<a class="iconoCalendario" id="calFPlaza"
										href='javascript:show_Calendario("OCAPMeritosForm", "FInicio", document.forms[0].FInicio.value);'><html:img
											src="imagenes/calendario.gif"
											styleClass="colocaImagenCalendario" border="0"
											alt="Calendario" /></a>
								</logic:notEqual>
							</logic:present>
						</label> <label for="idVFechaF"> Fecha de Finalizaci&oacute;n:
							(dd/mm/aaaa) * <html:text property="FFinalizacion" tabindex="2"
								styleClass="recuadroM colocaFechasMC" maxlength="10" /> <logic:notPresent
								name="OCAPMeritosForm" property="BDetalle">
								<a class="iconoCalendario" id="calFPlaza"
									href='javascript:show_Calendario("OCAPMeritosForm", "FFinalizacion", document.forms[0].FFinalizacion.value);'><html:img
										src="imagenes/calendario.gif"
										styleClass="colocaImagenCalendario" border="0"
										alt="Calendario" /></a>
							</logic:notPresent> <logic:present name="OCAPMeritosForm" property="BDetalle">
								<logic:notEqual name="OCAPMeritosForm" property="BDetalle"
									value="<%=Constantes.SI%>">
									<a class="iconoCalendario" id="calFPlaza"
										href='javascript:show_Calendario("OCAPMeritosForm", "FFinalizacion", document.forms[0].FFinalizacion.value);'><html:img
											src="imagenes/calendario.gif"
											styleClass="colocaImagenCalendario" border="0"
											alt="Calendario" /></a>
								</logic:notEqual>
							</logic:present>
						</label> <br />
						<br /> <span id="dCreditos"
							style="display: none; visibility: hidden;"> <label
							class="infoAdicional" for="idVNDias"> N&ordm; de
								Cr&eacute;ditos de la actividad formativa: * <html:text
									property="NCreditosCurso" tabindex="2"
									styleClass="recuadroM colocaNumTallerMC" maxlength="5"
									onkeypress="return soloNumerosUnDecimal(event);" />
						</label> <br />
						<br />
						</span> <label for="idVImpartido"> Organizado por: * <html:text
								property="AOrganizador" tabindex="3"
								styleClass="recuadroM colocaOrganCongresoMC" maxlength="100" />
						</label>
					</div>

					<logic:equal name="OCAPMeritosForm" property="BAcreditado"
						value="<%=Constantes.NO%>">
						<script language="javascript">mostrarModalidades('<%=Constantes.NO%>');</script>
					</logic:equal>
					<logic:equal name="OCAPMeritosForm" property="BAcreditado"
						value="<%=Constantes.SI%>">
						<script language="javascript">mostrarModalidades('<%=Constantes.SI%>');</script>
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