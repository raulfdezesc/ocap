<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.meritosActividad.OCAPMeractividadOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.meritosModalidad.OCAPMerModalidadOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.factoresImpacto.OCAPFactoresImpactoOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.tiposFirmante.OCAPTiposFirmanteOT"%>

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

function activarLibro(){
   document.forms[0].AOrganizador.disabled = true;
}

function activarCapitulo(){
   var bCapitulo = false;
   if (document.forms[0].CModalidadId != undefined) {
      for (var i=0; i < document.forms[0].CModalidadId.length && !bCapitulo; i++) {
         if (document.forms[0].CModalidadId[i].checked == true && document.forms[0].CModalidadId[i].value == '<%=Constantes.MOD_CAPITULO%>'){
            document.forms[0].AOrganizador.disabled = false;
            document.getElementById('tituloCapitulo').style.display='';
            document.getElementById('tituloCapitulo').style.visibility='visible';
            bCapitulo = true;
         }
      }
   }
   if (!bCapitulo) {
      document.forms[0].AOrganizador.disabled = true;
      document.forms[0].AOrganizador.value = '';
      document.getElementById('tituloCapitulo').style.display='none';
      document.getElementById('tituloCapitulo').style.visibility='hidden';
      if (document.forms[0].CTipoId != undefined) {
        if (document.getElementById('tipo_<%=Constantes.TIPO_COAUTOR%>')) {
         document.getElementById('tipo_<%=Constantes.TIPO_COAUTOR%>').style.display='';
         document.getElementById('tipo_<%=Constantes.TIPO_COAUTOR%>').style.visibility='visible';
        }
      }
   } else {
      if (document.forms[0].CTipoId != undefined) {
        if (document.getElementById('tipo_<%=Constantes.TIPO_COAUTOR%>')) {
         document.getElementById('tipo_<%=Constantes.TIPO_COAUTOR%>').style.display='none';
         document.getElementById('tipo_<%=Constantes.TIPO_COAUTOR%>').style.visibility='hidden';
         for (var i=0; i < document.forms[0].CTipoId.length; i++) {
            if (document.forms[0].CTipoId[i].value == '<%=Constantes.TIPO_COAUTOR%>') {
               document.forms[0].CTipoId[i].checked = false;
            }
         }
        }
      }
   }
   
/*
   if (document.forms[0].CModalidadId.value == '<%=Constantes.MOD_CAPITULO%>') {
      document.forms[0].CModalidadId.checked= true;
      document.forms[0].AOrganizador.disabled = false;
      document.getElementById('tituloCapitulo').style.display='';
      document.getElementById('tituloCapitulo').style.visibility='visible';
   } else {
      document.forms[0].AOrganizador.disabled = true;
      document.getElementById('tituloCapitulo').style.display='none';
      document.getElementById('tituloCapitulo').style.visibility='hidden';
   }
*/
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

			<html:form
				action="/OCAPMeritos.do?accion=controlAccionOtraPublicacionISBN">
				<div class="mensajeErrorMC">
					<html:messages id="cModalidadId" property="cModalidadId"
						message="true">
						<bean:write name="cModalidadId" />
						<br />
					</html:messages>
					<html:messages id="dTitulo" property="dTitulo" message="true">
						<bean:write name="dTitulo" />
						<br />
					</html:messages>
					<html:messages id="aOrganizador" property="aOrganizador"
						message="true">
						<bean:write name="aOrganizador" />
						<br />
					</html:messages>
					<html:messages id="nISBN" property="nISBN" message="true">
						<bean:write name="nISBN" />
						<br />
					</html:messages>
					<html:messages id="dEditorial" property="dEditorial" message="true">
						<bean:write name="dEditorial" />
						<br />
					</html:messages>
					<html:messages id="fAnnio" property="fAnnio" message="true">
						<bean:write name="fAnnio" />
						<br />
					</html:messages>
					<html:messages id="aLugarExpedicion" property="aLugarExpedicion"
						message="true">
						<bean:write name="aLugarExpedicion" />
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
						<logic:notEqual name="tamanoListaModalidades" value="0">
							<span> Clase de Publicaci&oacute;n * </span>
							<br />
							<br />
							<logic:iterate id="lModalidades" name="OCAPMeritosForm"
								property="listaModalidades" type="OCAPMerModalidadOT">
								<div class="radioEnCuadro flotaIzq radiosMC">
									<div class="flotaIzq">
										<logic:equal name="OCAPMeritosForm" property="CModalidadId"
											value="<%=lModalidades.getCModalidadId().toString()%>">
											<input type="radio" name="CModalidadId"
												styleClass="altoRadios" checked="checked"
												value="<bean:write name="lModalidades" property="CModalidadId"/>"
												onclick="javascript:activarCapitulo();" />
										</logic:equal>
										<logic:notEqual name="OCAPMeritosForm" property="CModalidadId"
											value="<%=lModalidades.getCModalidadId().toString()%>">
											<input type="radio" name="CModalidadId"
												styleClass="altoRadios"
												value="<bean:write name="lModalidades" property="CModalidadId"/>"
												onclick="javascript:activarCapitulo();" />
										</logic:notEqual>
										<span> <bean:write name="lModalidades"
												property="DNombre" /> <bean:write name="lModalidades"
												property="DDescripcion" /></span>
									</div>
								</div>
								<br />
								<br />
							</logic:iterate>
							<br />
						</logic:notEqual>

						<label for="idVTitulo"> T&iacute;tulo del libro: * <html:text
								property="DTitulo" styleClass="recuadroM colocaTitLibISBN"
								maxlength="150" />
						</label> <br />
						<br />
						<div id="tituloCapitulo"
							style="display: none; visibility: hidden;">
							<label for="idVTitulo"> T&iacute;tulo del
								cap&iacute;tulo: * <html:text property="AOrganizador"
									styleClass="recuadroM colocaTitCapISBN" maxlength="150"
									disabled="true" />
							</label> <br />
							<br />
						</div>
						<label for="idVTitulo"> ISBN: * <html:text
								property="NISBN" styleClass="recuadroM colocaIsbnISBN"
								maxlength="13" onkeypress="return permitirSoloNumeros(event);" />
						</label> <label for="idVFechaI"> A&ntilde;o de Publicaci&oacute;n
							(aaaa): * <logic:equal name="OCAPMeritosForm" property="FAnnio"
								value="0">
								<html:text property="FAnnio"
									styleClass="recuadroM colocaFechasMC" maxlength="4" value=""
									onkeypress="return permitirSoloNumeros(event);" />
							</logic:equal> <logic:notEqual name="OCAPMeritosForm" property="FAnnio"
								value="0">
								<html:text property="FAnnio"
									styleClass="recuadroM colocaFechasMC" maxlength="4"
									onkeypress="return permitirSoloNumeros(event);" />
							</logic:notEqual>
						</label> <br />
						<br /> <label for="idVTitulo"> Editorial: * <html:text
								property="DEditorial" styleClass="recuadroM colocaEditorialISBN"
								maxlength="100" />
						</label> <br />
						<br /> <label for="idVTitulo"> Lugar de
							publicaci&oacute;n: * <html:text property="ALugarExpedicion"
								styleClass="recuadroM colocaLugarISBN" maxlength="150" />
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
							<span> Tipo de Firmante: *</span>
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
							<span> Factor de impacto: </span>
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
						</logic:notEqual>
					</div>
				</fieldset>

				<p>Los campos se&ntilde;alados con * son obligatorios</p>

				<div class="espaciador"></div>

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
										value="<%=Constantes.INSERTAR%>"
										alt="Bot&oacute;n para guardar el m&eacute;rito" />
								</logic:equal> <logic:equal name="tipoAccion"
									value="<%=Constantes.VER_DETALLE%>">
									<img src="imagenes/imagenes_ocap/icon_accept.gif"
										alt="Imagen Ver Detalle Merito" />
									<input class="textoBotonInput" type="submit" name="accionBoton"
										value="<%=Constantes.VER_DETALLE%>"
										alt="Bot&oacute;n para volver a la pantalla anterior" />
								</logic:equal> <logic:equal name="tipoAccion"
									value="<%=Constantes.MODIFICAR%>">
									<img src="imagenes/imagenes_ocap/icon_accept.gif"
										alt="Imagen Modificar Merito" />
									<input class="textoBotonInput" type="submit" name="accionBoton"
										value="<%=Constantes.MODIFICAR%>"
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
											name="accionBoton" value="<%=Constantes.INSERTAR%>"
											alt="Bot&oacute;n para guardar el m&eacute;rito" />
									</logic:equal> <logic:equal name="tipoAccion"
										value="<%=Constantes.VER_DETALLE%>">
										<img src="imagenes/imagenes_ocap/icon_accept.gif"
											alt="Imagen Ver Detalle Merito" />
										<input class="textoBotonInput" type="submit"
											name="accionBoton" value="<%=Constantes.VER_DETALLE%>"
											alt="Bot&oacute;n para volver a la pantalla anterior" />
									</logic:equal> <logic:equal name="tipoAccion"
										value="<%=Constantes.MODIFICAR%>">
										<img src="imagenes/imagenes_ocap/icon_accept.gif"
											alt="Imagen Modificar Merito" />
										<input class="textoBotonInput" type="submit"
											name="accionBoton" value="<%=Constantes.MODIFICAR%>"
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

			<div class="espaciador"></div>
			<div class="limpiadora"></div>

		</div>
		<!-- /fin de ContenidoDCP1A -->
	</div>
	<!-- /Fin de Contenido -->

</div>
<!-- /Fin de ocultarCampo -->

<script language="JavaScript" type="text/javascript">
/*
if(!document.forms[0].BParticipacion[0].checked && !document.forms[0].BParticipacion[1].checked && document.forms[0].AOrganizador.value == ''){
   document.forms[0].BParticipacion[0].checked = true;
}else if(!document.forms[0].BParticipacion[0].checked && !document.forms[0].BParticipacion[1].checked && document.forms[0].AOrganizador.value != ''){
   document.forms[0].BParticipacion[1].checked = true;
}

if(document.forms[0].BParticipacion[0].checked && !document.forms[0].BParticipacion[1].checked){
   document.forms[0].AOrganizador.disabled = true;
}else if(!document.forms[0].BParticipacion[0].checked && document.forms[0].BParticipacion[1].checked){
   document.forms[0].AOrganizador.disabled = false;
}
*/
activarCapitulo();
</script>

