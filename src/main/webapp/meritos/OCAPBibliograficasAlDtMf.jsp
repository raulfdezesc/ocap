<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.meritosActividad.OCAPMeractividadOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.meritosModalidad.OCAPMerModalidadOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.expedienteMC.OCAPExpedientemcOT"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<script language="JavaScript" type="text/javascript"
	src="<html:rewrite page='/javascript/combos1.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/fechas.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>

<% 
  ArrayList resumenCertificados = (ArrayList) request.getAttribute("resumenCertificados");
%>

<script>
function aceptarMerito(){
   var correcto = true;
   var algunTipoDocencia = false;
   document.forms[0].resumenCertificados.value='';
   if (document.getElementById('jspCadenaCertificado') != null && correcto){
      if(valorCombo(document.getElementById('jspCadenaCertificado')) != ''){         
         document.forms[0].resumenCertificados.value= valorCombo(document.getElementById('jspCadenaCertificado'));
         algunTipoDocencia = true;
      }else if(valorCombo(document.getElementById('jspCadenaCertificado')) == '') {
         correcto=false;
      }else{
         document.forms[0].resumenCertificados.value+= "%";
      }
   }
   if (algunTipoDocencia)
      enviar('OCAPMeritos.do?accion=controlAccionBibliograficas');        
   else if (!algunTipoDocencia)
      alert('Debe a\u00f1adir alg\u00fan t\u00edtulo a insertar. Si no desea insertar ninguno, pulse Cancelar.');
}

function anhCertificado() {
   formu = document.forms[0];
   comboCertificados = document.getElementById('jspCadenaCertificado');
   fechaConvocatoria = '<bean:write name="OCAPMeritosForm" property="FComprobMeritos" />';
    if (formu.DTitulo.value == ''){
      alert('Debe introducir un T\u00edtulo.');
   }else{
      if(formu.FInicio.value == ''){
         alert('Debe introducir una fecha.');
      }else if(formu.FInicio.value != '' && !esFechaCorrecta(formu.FInicio.value)){
         alert('El campo Fecha no tiene el formato correcto.');
         }else if(comprobarFechaNoFutura(formu.FInicio.value)) {
         alert("La \" Fecha\" no puede ser futura.");      
      }else if(diferenciaFechas(fechaConvocatoria,formu.FInicio.value)<0) {
         alert("La \" Fecha\" debe ser anterior o igual a la fecha de la convocatoria "+ fechaConvocatoria + ".");      
      }else if (formu.NHoras.value == ''){
         alert('Debe introducir las horas.');
      }else if (formu.NHoras.value != '' && !esNumero(formu.NHoras.value)){
         alert('El campo Horas debe ser num\u00e9rico.');
      }else{                  
            var valor = formu.DTitulo.value + '$' +  formu.FInicio.value + '&' + formu.NHoras.value;
      
            var texto = formu.DTitulo.value + '---' +  formu.FInicio.value + '---' + formu.NHoras.value;

   
         var aux = valorCombo(document.getElementById('jspCadenaCertificado'));
         if ( aux.indexOf('$' + formu.DTitulo.value + '$') == -1 ) {            
            var campo = formu.jspCadenaCertificado;
            nueva_opcion(comboCertificados,texto,valor);
            nuevoCertificado();
         } else  {
            alert('No se puede a\u00f1adir el Certificado porque ya existe');
         }
      }
   }
} //Fin anhCertificado

function nuevoCertificado() {
	formu = document.forms[0];
	formu.FInicio.value='';
   formu.DTitulo.value='';  
   formu.NHoras.value='';  
}

function modCertificado() {
	formu = document.forms[0];

   comboCertificados=objeto_combo('document.forms[0].jspCadenaCertificado');

   if (comprueba_seleccion(document.getElementById('jspCadenaCertificado'))) {
    indice = document.getElementById('jspCadenaCertificado').selectedIndex;
    var valor = comboCertificados.options[indice].value;
    var valor_aux = valor;     // variable para operar
    var DTitulo = valor_aux.substring(valor_aux.indexOf('<%=Constantes.SEPARADOR_7%>')+1, valor_aux.indexOf('<%=Constantes.SEPARADOR_2%>'));
    var FFecha = valor_aux.substring(valor_aux.indexOf('<%=Constantes.SEPARADOR_2%>')+1, valor_aux.indexOf('<%=Constantes.SEPARADOR_8%>'));
    var NHoras = valor_aux.substring(valor_aux.indexOf('<%=Constantes.SEPARADOR_8%>') + 1);
    
     
    formu.FInicio.value = FFecha;
    formu.DTitulo.value = DTitulo;
    formu.NHoras.value  = NHoras;

	 combos_borrar(document.getElementById('jspCadenaCertificado'));
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

			<html:form
				action="/OCAPMeritos.do?accion=controlAccionBibliograficas">
				<html:hidden property="resumenCertificados" />
				<div class="mensajeErrorMC">
				
					<html:messages id="fInicio" property="fInicio" message="true">
						<bean:write name="fInicio" />
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
							<span> Tipo de Actividad Formativa: </span>
							<logic:iterate id="lActividades" name="OCAPMeritosForm"
								property="listaActividades" type="OCAPMeractividadOT">
								<div class="radioEnCuadro flotaIzq radiosMC">
									<div class="flotaIzq">
										<logic:equal name="OCAPMeritosForm" property="CActividadId"
											value="<%=lActividades.getCActividadId().toString()%>">
											<input type="radio" name="CActividadId" checked="checked"
												value="<bean:write name="lActividades" property="CActividadId"/>"
												onclick="javascript:activarNumeroHoras();" />
										</logic:equal>
										<logic:notEqual name="OCAPMeritosForm" property="CActividadId"
											value="<%=lActividades.getCActividadId().toString()%>">
											<input type="radio" name="CActividadId"
												value="<bean:write name="lActividades" property="CActividadId"/>"
												onclick="javascript:activarNumeroHoras();" />
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

						<bean:size id="tamanoListaModalidades" name="OCAPMeritosForm"
							property="listaModalidades" />
						<logic:notEqual name="tamanoListaModalidades" value="0">
							<div class="tituloEnCuadro flotaIzq">
								<span> Modalidad: </span>
							</div>
							<logic:iterate id="lModalidades" name="OCAPMeritosForm"
								property="listaModalidades" type="OCAPMerModalidadOT">
								<div class="radioEnCuadro flotaIzq">
									<div class="flotaIzq">
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
								</div>
							</logic:iterate>
							<div class="limpiadora"></div>
							<div class="espaciador"></div>
						</logic:notEqual>

						<label for="idVTitulo"> T&iacute;tulo de la Sesi&oacute;n:
							* <html:text property="DTitulo" tabindex="1"
								styleClass="recuadroM colocaTituloBibliograficasMC"
								maxlength="100" />
						</label> <br />
						<br /> <label for="idVFechaI"> Fecha: (dd/mm/aaaa) ** <html:text
								property="FInicio" tabindex="2"
								styleClass="recuadroM colocaFechasMC" maxlength="10" /> <logic:notPresent
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
						</label> <br />
						<br /> <label for="idVNHoras"> N&ordm; Horas: * <html:text
								property="NHoras" tabindex="2"
								styleClass="recuadroM colocaHorasBibliograficasMC" maxlength="5" />
						</label> <br />
						<br /> <label for="idVTitulo"> (** Esta fecha será
							requisito imprescindible para sucesivas convocatorias de grado.)</label><br />


						<div
							class="botonesBibliograficas botonesCertif2 botonesCertif2Docencia">

							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <input
									type="button" id="anadir" tabindex="63" value=" a&ntilde;adir"
									property="anadir" onclick="anhCertificado();" />
								</span> <span class="derBoton"></span>
							</div>

							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <input
									type="button" id="modificar" tabindex="64" value="modificar"
									property="modificar" onclick="modCertificado();" />
								</span> <span class="derBoton"></span>
							</div>

							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <input
									type="button" id="eliminar" tabindex="65" value="eliminar"
									property="eliminar"
									onclick="combos_borrar(document.getElementById('jspCadenaCertificado'));" />
								</span> <span class="derBoton"></span>
							</div>

						</div>

						<!--tabla con los datos -->
						<table border="0" cellpadding="2" cellspacing="0" width="570"
							class="colocaTablaCertif">
							<tr>
								<td width="30%" class="tituloCertificados" NOWRAP>Titulo--Fecha--Horas</td>
								<td width="30%" class="tituloCertificados" NOWRAP></td>
							</tr>
							<tr>
								<td colspan="3" height="1" NOWRAP><html:img
										page="/imagenes/pix.gif" width="1" height="1" /></td>
							</tr>
							<tr class="recuadroM">
								<td colspan="3"><select size="5" id="jspCadenaCertificado"
									name="jspCadenaCertificado" class="textoTituloGris">
										<logic:equal name="tipoAccion"
											value="<%=Constantes.MODIFICAR%>">
											<%
                                       String valor = "";
                                       for (int g=0;g<resumenCertificados.size();g++){                                            
                                             valor = ((OCAPExpedientemcOT)(resumenCertificados.get(g))).getDTituloCodificado() + "$" +	
                                                      ((OCAPExpedientemcOT)(resumenCertificados.get(g))).getFInicio()+ "&" +
                                                      ((OCAPExpedientemcOT)(resumenCertificados.get(g))).getNHoras();
                                          %>
											<option value="<%=valor%>"><%=((OCAPExpedientemcOT)(resumenCertificados.get(g))).getDTitulo()%>---<%=((OCAPExpedientemcOT)(resumenCertificados.get(g))).getFInicio()%>---<%=((OCAPExpedientemcOT)(resumenCertificados.get(g))).getNHoras()%>
											</option>
											<%
                                       } // fin for (g)%>
										</logic:equal>
								</select></td>
							</tr>
							<div class="espaciador"></div>
						</table>
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
									<input class="textoBotonInput" type="button" name="accionBoton"
										tabindex="17" value="<%=Constantes.INSERTAR%>"
										alt="Botón para guardar el mérito" onclick="aceptarMerito()" />
								</logic:equal> <logic:equal name="tipoAccion"
									value="<%=Constantes.VER_DETALLE%>">
									<img src="imagenes/imagenes_ocap/icon_accept.gif"
										alt="Imagen Ver Detalle Merito" />
									<input class="textoBotonInput" type="button" name="accionBoton"
										tabindex="17" value="<%=Constantes.VER_DETALLE%>"
										alt="Botón para volver a la pantalla anterior"
										onclick="aceptarMerito()" />
								</logic:equal> <logic:equal name="tipoAccion"
									value="<%=Constantes.MODIFICAR%>">
									<img src="imagenes/imagenes_ocap/icon_accept.gif"
										alt="Imagen Modificar Merito" />
									<input class="textoBotonInput" type="button" name="accionBoton"
										tabindex="17" value="<%=Constantes.MODIFICAR%>"
										alt="Botón para modificar el mérito" onclick="aceptarMerito()" />
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