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
     
   if (document.OCAPPuestosForm.DNombre.value != "" && !LetrasYNumeros(document.OCAPPuestosForm.DNombre)){
         alert('Debe introducir correctamente el Nombre');
         validacion=1;   
      }
      
   if (document.OCAPPuestosForm.ANombre_corto.value != "" && !LetrasYNumeros(document.OCAPPuestosForm.ANombre_corto)){
         alert('Debe introducir correctamente el Nombre Corto');
         validacion=1;   
      }
      
   if (document.OCAPPuestosForm.DObservaciones.value != "" && !LetrasYNumeros(document.OCAPPuestosForm.DObservaciones)){
         alert('Debe introducir correctamente las observaciones');
         validacion=1;   
      }      
       
   if ((document.OCAPPuestosForm.DCreacion.value != "" ) || (document.OCAPPuestosForm.MCreacion.value != "" ) || (document.OCAPPuestosForm.ACreacion.value != "")){
      var fecha = document.OCAPPuestosForm.DCreacion.value + '/' + document.OCAPPuestosForm.MCreacion.value + '/' + document.OCAPPuestosForm.ACreacion.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Creaci\u00f3n');
         validacion=1;   
      }
   }
      
   if ((document.OCAPPuestosForm.DModificacion.value != "" ) || (document.OCAPPuestosForm.MModificacion.value != "" ) || (document.OCAPPuestosForm.AModificacion.value != "")){
      var fecha = document.OCAPPuestosForm.DModificacion.value + '/' + document.OCAPPuestosForm.MModificacion.value + '/' + document.OCAPPuestosForm.AModificacion.value; 
   
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Modificaci\u00f3n');
         validacion=1;   
      }
   } 
      
   if (validacion==0) document.forms[0].submit();    
}   
function bajasClave(cPuestoId)
{
   if (confirm('Va a ELIMINAR el registro selecionado')){
      document.forms[0].action = 'OCAPPuestos.do?accion=borrar';
      document.forms[0].cPuestoIdS.value = cPuestoId;

      document.forms[0].submit(); 
   }else{
      return false;
   }
}

function limpiar() {
   document.forms[0].CProfesional_id.options[0].selected='selected';
   document.forms[0].CItinerario_id.options[0].selected='selected';
   document.forms[0].DNombre.value='';
   document.forms[0].ANombre_corto.value='';
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
<html:form action="/OCAPPuestos.do?accion=buscar">

	<div class="ocultarCampo">

		<div class="cuadroContenedorConsultas">
			<div class="titulocajaformulario">Consulta de Puestos</div>
			<logic:present name="errorConsultando">
				<p>
					<label><bean:write name="errorConsultando" filter="false" /></label>
				</p>
			</logic:present>
			<logic:notPresent name="errorConsultando">
				<div class="cajaformulario">
					<fieldset class="normales">
						<label for="idProfesional" class="obligado"> Profesional:
							<html:select property="CProfesional_id"
								styleClass="cbCuadros colocaProfConPuestos" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_PROFESIONAL%>"
									property="CProfesionalId" labelProperty="DNombre" />
							</html:select>
						</label>
						<html:errors property="CProfesional_id" />

						<br /> <label for="idItinerario" class="obligado">
							Itinerario: <html:select property="CItinerario_id"
								styleClass="cbCuadros colocaItinerarioPuestosCon" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ITINERARIOS%>"
									property="CItinerarioId" labelProperty="CItinerarioId" />
							</html:select>
						</label>
						<html:errors property="CItinerario_id" />

						<br /> <label class="obligado">Puesto:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaPuestoConPuestos" size="60"
							maxlength="100" />
						&nbsp;
						<html:errors property="DNombre" />
						<br /> <label class="obligado">Nombre corto:</label>
						<html:text property="ANombre_corto" tabindex="18"
							styleClass="inputColor colocaNombreCPuestosCon" size="5"
							maxlength="5" />
						&nbsp;
						<html:errors property="ANombre_corto" />
						<br /> <label class="obligado">Observaciones:</label>
						<html:text property="DObservaciones" tabindex="18"
							styleClass="inputColor colocaObsPuestosCon" size="60"
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
				<table border="0" class="tablaConsultas tabla7">
					<logic:notPresent name="sinDatos">
						<logic:present name="primeraConsulta">
							<tr>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th class="col1" id="cProfes">Profesional</th>
								<th class="col2" id="cItiner">Itinerario</th>
								<th class="col3" id="cNombre">Puesto</th>
								<th class="col4" id="cNomCor">Nombre corto</th>
								<th class="col5" id="cObserv">Observac.</th>
								<th class="col6" id="cFechaC">Fecha creaci&oacute;n</th>
								<th class="col7" id="cFechaM">Fecha modificaci&oacute;n</th>

							</tr>
						</logic:present>
						<logic:present name="paginaOCAPPuestosOT" property="elementos">
							<logic:iterate id="elemento" name="paginaOCAPPuestosOT"
								property="elementos">
								<tr>
									<td class="campoIcono"><a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CPuestoId"/>');
return false;"><img
											src="imagenes/eliminar.gif" title="Eliminar registro"
											alt="Eliminar registro"></a>&nbsp;<a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CPuestoId"/>');return false;"></a>
									</td>
									<td class="campoIcono"><a
										href="OCAPPuestos.do?accion=irEditar&cPuestoIdS=<bean:write name="elemento" property="CPuestoId"/>">
											<img src="imagenes/editar.gif" title="Editar registro"
											alt="Editar registro">
									</a></td>

									<td class="col1" headers="cProfes"><a
										href="OCAPPuestos.do?accion=detalle&cPuestoIdS=<bean:write name="elemento" property="CPuestoId"/>">
											<bean:write name="elemento" property="DProfesionalNombre" />
									</a></td>
									<td class="col2" headers="cItiner"><a
										href="OCAPPuestos.do?accion=detalle&cPuestoIdS=<bean:write name="elemento" property="CPuestoId"/>">
											<bean:write name="elemento" property="CItinerarioId" />
									</a></td>
									<td class="col3" headers="cNombre"><a
										href="OCAPPuestos.do?accion=detalle&cPuestoIdS=<bean:write name="elemento" property="CPuestoId"/>">
											<bean:write name="elemento" property="DNombre" />
									</a></td>
									<td class="col4" headers="cNomCor"><a
										href="OCAPPuestos.do?accion=detalle&cPuestoIdS=<bean:write name="elemento" property="CPuestoId"/>">
											<bean:write name="elemento" property="ANombreCorto" />
									</a></td>
									<td class="col5" headers="cObserv"><a
										href="OCAPPuestos.do?accion=detalle&cPuestoIdS=<bean:write name="elemento" property="CPuestoId"/>">
											<bean:write name="elemento" property="DObservaciones" />
									</a></td>
									<td class="col6" headers="cFechaC"><a
										href="OCAPPuestos.do?accion=detalle&cPuestoIdS=<bean:write name="elemento" property="CPuestoId"/>">
											<bean:write name="elemento" property="FCreacion"
												format="dd/MM/yyyy" />
									</a></td>
									<td class="col7" headers="cFechaM"><a
										href="OCAPPuestos.do?accion=detalle&cPuestoIdS=<bean:write name="elemento" property="CPuestoId"/>">
											<bean:write name="elemento" property="FModificacion"
												format="dd/MM/yyyy" />
									</a></td>
								</tr>

							</logic:iterate>

							<tr>
								<td colspan="50">

									<table class="paginacionConsultas">
										<tr>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPPuestosOT" property="anterior" value="true">
													<input class="botonPaginacion" type="button"
														value="Primera"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPPuestosOT" property="URLPrimeraPagina"/>"' />
												</logic:equal></td>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPPuestosOT" property="anterior" value="true">
													<input class="botonPaginacion" type="button"
														value="Anterior"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPPuestosOT" property="URLPaginaAnterior"/>"' />
												</logic:equal></td>
											<td class="textoTituloGris parteCen">Registro: [<bean:write
													name="paginaOCAPPuestosOT" property="registroActual" />/<bean:write
													name="paginaOCAPPuestosOT" property="NRegistros" />]&nbsp;&nbsp;
												P&aacute;gina: [<bean:write name="paginaOCAPPuestosOT"
													property="paginaActual" />/<bean:write
													name="paginaOCAPPuestosOT" property="NPaginas" />]
											</td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPPuestosOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Siguiente"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPPuestosOT" property="URLPaginaSiguiente"/>"' />
												</logic:equal></td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPPuestosOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="&Uacute;ltima"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPPuestosOT" property="URLUltimaPagina"/>"' />
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
		<input type="hidden" name="cPuestoIdS" value="" />

	</div>
	<!-- /Fin de ocultarCampo -->

</html:form>
