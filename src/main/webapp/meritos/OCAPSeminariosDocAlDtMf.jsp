<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.meritosActividad.OCAPMeractividadOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.meritosModalidad.OCAPMerModalidadOT"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>


<script language="javascript">
function activarNumeroHoras(){
   /*
   var bAcreditada = false;
   if (document.forms[0].CModalidadId != undefined) {
      for (var i=0; i < document.forms[0].CModalidadId.length && !bAcreditada; i++) {
         if (document.forms[0].CModalidadId[i].checked == true && document.forms[0].CModalidadId[i].value == '<%=Constantes.MOD_ACREDITADA%>'){
            document.forms[0].NHoras.disabled = false;
            document.getElementById('numeroHoras').style.display='';
            document.getElementById('numeroHoras').style.visibility='visible';
            bAcreditada = true;
         }
      }
   }
   if (!bAcreditada) {
      document.forms[0].NHoras.disabled = true;
      document.forms[0].NHoras.value = '';
      document.getElementById('numeroHoras').style.display='none';
      document.getElementById('numeroHoras').style.visibility='hidden';
   }
   */
   if (document.forms[0].CModalidadId.value == '<%=Constantes.MOD_ACREDITADA%>'){
      document.forms[0].NHoras.disabled = false;
      document.getElementById('numeroHoras').style.display='';
      document.getElementById('numeroHoras').style.visibility='visible';
   } else {
      document.forms[0].NHoras.disabled = true;
      document.forms[0].NHoras.value = '';
      document.getElementById('numeroHoras').style.display='none';
      document.getElementById('numeroHoras').style.visibility='hidden';
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

			<html:form action="/OCAPMeritos.do?accion=controlAccionSeminariosDoc">
				<div class="mensajeErrorMC">
					<html:messages id="cActividadId" property="cActividadId"
						message="true">
						<bean:write name="cActividadId" />
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
					<html:messages id="fExpedicion" property="fExpedicion"
						message="true">
						<bean:write name="fExpedicion" />
						<br />
					</html:messages>
					<html:messages id="nHoras" property="nHoras" message="true">
						<bean:write name="nHoras" />
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

						<bean:size id="tamanoListaModalidades" name="OCAPMeritosForm"
							property="listaModalidades" />
						<%--logic:greaterThan name="tamanoListaModalidades" value="1" >
                        <span> Modalidad: * </span>
                        <div class="radiosAcreditacionesMC">
                           <logic:iterate id="lModalidades" name="OCAPMeritosForm" property="listaModalidades" type="OCAPMerModalidadOT">
                              <div class="radioHorizontal">
                                 <logic:equal name="OCAPMeritosForm" property="CModalidadId" value="<%=lModalidades.getCModalidadId().toString()%>">                                    
                                    <input type="radio" name="CModalidadId" checked="checked" value="<bean:write name="lModalidades" property="CModalidadId"/>"  onclick="javascript:activarNumeroHoras();" />
                                 </logic:equal>
                                 <logic:notEqual name="OCAPMeritosForm" property="CModalidadId" value="<%=lModalidades.getCModalidadId().toString()%>">                                    
                                    <input type="radio" name="CModalidadId" value="<bean:write name="lModalidades" property="CModalidadId"/>"  onclick="javascript:activarNumeroHoras();" />
                                 </logic:notEqual>
                                 <span> <bean:write name="lModalidades" property="DNombre"/> <bean:write name="lModalidades" property="DDescripcion"/></span>
                              </div>                             
                           </logic:iterate>
                           </div>                        
                        </logic:greaterThan--%>

						<logic:equal name="tamanoListaModalidades" value="1">
							<logic:iterate id="lModalidad" name="OCAPMeritosForm"
								property="listaModalidades" type="OCAPMerModalidadOT">
								<input type="hidden" name="CModalidadId"
									value="<bean:write name="lModalidad" property="CModalidadId"/>" />
							</logic:iterate>
						</logic:equal>

						<bean:size id="tamanoListaActividades" name="OCAPMeritosForm"
							property="listaActividades" />
						<logic:notEqual name="tamanoListaActividades" value="0">
							<span> &Aacute;mbito: * </span>
							<br />
							<br />
							<logic:iterate id="lActividades" name="OCAPMeritosForm"
								property="listaActividades" type="OCAPMeractividadOT">
								<div class="radioEnCuadro flotaIzq radiosMC">
									<div class="flotaIzq ajustarSeminarios">
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

						<label for="idVTitulo"> T&iacute;tulo Seminario/Jornada: *
							<html:text property="DTitulo" tabindex="1"
								styleClass="recuadroM colocaTitSeminariosMC" maxlength="150" />
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
						<div id="numeroHoras" style="display: none; visibility: hidden;">
							<br /> <label for="idVNHoras"> N&ordm; Horas: * <html:text
									property="NHoras" tabindex="2"
									styleClass="recuadroM colocaNHorasSeminariosDocMC"
									maxlength="5" disabled="true" />
							</label>
						</div>
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

	<script language="JavaScript">
activarNumeroHoras();
</script>
</div>
<!-- /Fin de ocultarCampo -->