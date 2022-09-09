<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/fechas.js'/>"></script>
<script>

function validar(){
   var validacion = 0;
   // Validar numéricos
               
   if (document.OCAPServiciosForm.DNombre_corto.value != "" && !LetrasYNumeros(document.OCAPServiciosForm.DNombre_corto)){
         alert('Debe introducir correctamente el Nombre corto');
         validacion=1;   
      }
      
   if (document.OCAPServiciosForm.DNombre_largo.value != "" && !LetrasYNumeros(document.OCAPServiciosForm.DNombre_largo)){
         alert('Debe introducir correctamente el Nombre largo');
         validacion=1;   
      }
       
   if (document.OCAPServiciosForm.DObservaciones.value != "" && !LetrasYNumeros(document.OCAPServiciosForm.DObservaciones)){
         alert('Debe introducir correctamente las Observaciones');
         validacion=1;   
      }
    
   if ((document.OCAPServiciosForm.DCreacion.value != "" ) || (document.OCAPServiciosForm.MCreacion.value != "" ) || (document.OCAPServiciosForm.ACreacion.value != "")){
      var fecha = document.OCAPServiciosForm.DCreacion.value + '/' + document.OCAPServiciosForm.MCreacion.value + '/' + document.OCAPServiciosForm.ACreacion.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Creaci\u00f3n');
         validacion=1;   
      }
   }
      
   if ((document.OCAPServiciosForm.DModificacion.value != "" ) || (document.OCAPServiciosForm.MModificacion.value != "" ) || (document.OCAPServiciosForm.AModificacion.value != "")){
      var fecha = document.OCAPServiciosForm.DModificacion.value + '/' + document.OCAPServiciosForm.MModificacion.value + '/' + document.OCAPServiciosForm.AModificacion.value; 
   
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Modificaci\u00f3n');
         validacion=1;   
      }
   } 
      
   if (validacion==0) document.forms[0].submit();    
}   
function bajasClave(cServicioId)
{
   if (confirm('Va a ELIMINAR el registro selecionado')){
      document.forms[0].action = 'OCAPServicios.do?accion=borrar';
      document.forms[0].cServicioIdS.value = cServicioId;

      document.forms[0].submit(); 
   }else{
      return false;
   }
}

function limpiar() {
   document.forms[0].CProfesional_id.options[0].selected='selected';
   document.forms[0].CEspec_id.options[0].selected='selected';
   document.forms[0].CItinerario_id.options[0].selected='selected';
   document.forms[0].DNombre_corto.value='';
   document.forms[0].DNombre_largo.value='';
   document.forms[0].DObservaciones.value='';
   document.forms[0].ACreacion.value='';
   document.forms[0].MCreacion.value='';
   document.forms[0].DCreacion.value='';
   document.forms[0].AModificacion.value='';  
   document.forms[0].MModificacion.value='';
   document.forms[0].DModificacion.value='';
}
</script>
<%
int iRow=0;
%>
<html:form action="/OCAPServicios.do?accion=buscar">

	<div class="ocultarCampo">

		<div class="cuadroContenedorConsultas">
			<div class="titulocajaformulario">Consulta de Servicio</div>
			<logic:present name="errorConsultando">
				<p>
					<label><bean:write name="errorConsultando" filter="false" /></label>
				</p>
			</logic:present>
			<logic:notPresent name="errorConsultando">
				<div class="cajaformulario">
					<fieldset class="normales">

						<br /> <label for="idProfesional" class="obligado">
							Profesional: <html:select property="CProfesional_id"
								styleClass="cbCuadros colocaProfeServiciosCon" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_PROFESIONAL%>"
									property="CProfesionalId" labelProperty="DNombre" />
							</html:select>
						</label>
						<html:errors property="CProfesional_id" />

						<br /> <label for="idEspecialidad" class="obligado">
							Especialidad: <html:select property="CEspec_id"
								styleClass="cbCuadros colocaEspecServiciosCon" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
									property="CEspecId" labelProperty="DDescripcion" />
							</html:select>
						</label> <br /> <label for="idItinerario" class="obligado">
							Itinerario: <html:select property="CItinerario_id"
								styleClass="cbCuadros colocaItineServiciosCon" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ITINERARIOS%>"
									property="CItinerarioId" labelProperty="CItinerarioId" />
							</html:select>
						</label>
						<html:errors property="CItinerario_id" />
						<br /> <label class="obligado">Nombre corto:</label>
						<html:text property="DNombre_corto" tabindex="18"
							styleClass="inputColor colocaNomCServiciosCon" size="4"
							maxlength="4" />
						&nbsp;
						<html:errors property="DNombre_corto" />
						<br /> <label class="obligado">Nombre largo:</label>
						<html:text property="DNombre_largo" tabindex="18"
							styleClass="inputColor colocaNomLServiciosCon" size="60"
							maxlength="100" />
						&nbsp;
						<html:errors property="DNombre_largo" />
						<br /> <label class="obligado">Observaciones:</label>
						<html:text property="DObservaciones" tabindex="18"
							styleClass="inputColor colocaObsServiciosCon" size="60"
							maxlength="200" />
						&nbsp;
						<html:errors property="DObservaciones" />
						<br /> <label class="obligado">Fecha de
							creaci&oacute;n(dd/mm/aaaa):</label>
						<html:text property="DCreacion" tabindex="18"
							styleClass="inputColor colocaFechaArea" size="2" maxlength="2" />
						&nbsp;
						<html:text property="MCreacion" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="ACreacion" tabindex="18"
							styleClass="inputColor" size="4" maxlength="4" />
						&nbsp;
						<html:errors property="FCreacion" />
						<br /> <label class="obligado">Fecha de
							modificaci&oacute;n(dd/mm/aaaa):</label>
						<html:text property="DModificacion" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="MModificacion" tabindex="18"
							styleClass="inputColor" size="2" maxlength="2" />
						&nbsp;
						<html:text property="AModificacion" tabindex="18"
							styleClass="inputColor" size="4" maxlength="4" />
						&nbsp;
						<html:errors property="FModificacion" />
						<br />
						<div class="botonera">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:validar();"> <span> Buscar </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:limpiar();"> <span> Limpiar </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</div>
					</fieldset>
				</div>
				<logic:present name="sinDatos">
					<div id="divSinDatos">
						<p>
							<label><bean:write name="sinDatos" /></label>
						</p>
					</div>
				</logic:present>
				<table border="0" class="tablaConsultas tabla8">
					<logic:notPresent name="sinDatos">
						<logic:present name="primeraConsulta">
							<tr>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th class="col1" id="cProfes">Profesional</th>
								<th class="col2" id="cEspec">Especialidad</th>
								<th class="col3" id="cItiner">Itinerario</th>
								<th class="col4" id="cNombre">Nombre</th>
								<th class="col5" id="cDescri">Descripci&oacute;n</th>
								<th class="col6" id="cObserv">Observac.</th>
								<th class="col7" id="cFechaC">Fecha creaci&oacute;n</th>
								<th class="col8" id="cFechaM">Fecha modif.</th>

							</tr>
						</logic:present>
						<logic:present name="paginaOCAPServiciosOT" property="elementos">
							<logic:iterate id="elemento" name="paginaOCAPServiciosOT"
								property="elementos">
								<tr>
									<td class="campoIcono"><a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CServicioId"/>');
return false;"><img
											src="imagenes/eliminar.gif" title="Eliminar registro"
											alt="Eliminar registro"></a>&nbsp;<a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CServicioId"/>');return false;"></a>
									</td>
									<td class="campoIcono"><a
										href="OCAPServicios.do?accion=irEditar&cServicioIdS=<bean:write name="elemento" property="CServicioId"/>">
											<img src="imagenes/editar.gif" title="Editar registro"
											alt="Editar registro">
									</a></td>
									<td class="col1" headers="cProfes"><a
										href="OCAPServicios.do?accion=detalle&cServicioIdS=<bean:write name="elemento" property="CServicioId"/>">
											<bean:write name="elemento" property="DProfesionalNombre" />
									</a></td>
									<td class="col2" headers="cEspec"><a
										href="OCAPServicios.do?accion=detalle&cServicioIdS=<bean:write name="elemento" property="CServicioId"/>">
											<bean:write name="elemento" property="DEspecNombre" />
									</a></td>
									<td class="col3" headers="cItiner"><a
										href="OCAPServicios.do?accion=detalle&cServicioIdS=<bean:write name="elemento" property="CServicioId"/>">
											<bean:write name="elemento" property="CItinerarioId" />
									</a></td>
									<td class="col4" headers="cNombre"><a
										href="OCAPServicios.do?accion=detalle&cServicioIdS=<bean:write name="elemento" property="CServicioId"/>">
											<bean:write name="elemento" property="DNombreCorto" />
									</a></td>
									<td class="col5" headers="cDescri"><a
										href="OCAPServicios.do?accion=detalle&cServicioIdS=<bean:write name="elemento" property="CServicioId"/>">
											<bean:write name="elemento" property="DNombreLargo" />
									</a></td>
									<td class="col6" headers="cObserv"><a
										href="OCAPServicios.do?accion=detalle&cServicioIdS=<bean:write name="elemento" property="CServicioId"/>">
											<bean:write name="elemento" property="DObservaciones" />
									</a></td>
									<td class="col7" headers="cFechaC"><a
										href="OCAPServicios.do?accion=detalle&cServicioIdS=<bean:write name="elemento" property="CServicioId"/>">
											<bean:write name="elemento" property="FCreacion"
												format="dd/MM/yyyy" />
									</a></td>
									<td class="col8" headers="cFechaM"><a
										href="OCAPServicios.do?accion=detalle&cServicioIdS=<bean:write name="elemento" property="CServicioId"/>">
											<bean:write name="elemento" property="FModificacion"
												format="dd/MM/yyyy" />
									</a></td>
								</tr>

							</logic:iterate>

							<tr>
								<td colspan="10">

									<table width="90%" class="paginacionConsultas">
										<tr>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPServiciosOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Primera"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPServiciosOT" property="URLPrimeraPagina"/>"' />
												</logic:equal></td>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPServiciosOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Anterior"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPServiciosOT" property="URLPaginaAnterior"/>"' />
												</logic:equal></td>
											<td class="textoTituloGris parteCen">Registro: [<bean:write
													name="paginaOCAPServiciosOT" property="registroActual" />/<bean:write
													name="paginaOCAPServiciosOT" property="NRegistros" />]&nbsp;&nbsp;
												P&aacute;gina: [<bean:write name="paginaOCAPServiciosOT"
													property="paginaActual" />/<bean:write
													name="paginaOCAPServiciosOT" property="NPaginas" />]
											</td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPServiciosOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Siguiente"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPServiciosOT" property="URLPaginaSiguiente"/>"' />
												</logic:equal></td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPServiciosOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="&Uacute;ltima"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPServiciosOT" property="URLUltimaPagina"/>"' />
												</logic:equal></td>
										</tr>
									</table>
								</td>
							</tr>
						</logic:present>
					</logic:notPresent>
				</table>
			</logic:notPresent>
		</div>
		<input type="hidden" name="cServicioIdS" value="" />

	</div>
	<!-- /Fin de ocultarCampo -->

</html:form>
