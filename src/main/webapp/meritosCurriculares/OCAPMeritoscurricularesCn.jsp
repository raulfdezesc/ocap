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
     
   if (document.OCAPMeritoscurricularesForm.CTipo_merito.value != "" && !LetrasYNumeros(document.OCAPMeritoscurricularesForm.CTipo_merito)){
         alert('Debe introducir correctamente el Tipo de m\u00e9rito');
         validacion=1;   
      }
      
   if (document.OCAPMeritoscurricularesForm.DNombrecorto.value != "" && !LetrasYNumeros(document.OCAPMeritoscurricularesForm.DNombrecorto)){
         alert('Debe introducir correctamente el Nombre corto');
         validacion=1;   
      }      
      
   if (document.OCAPMeritoscurricularesForm.DNombre.value != "" && !LetrasYNumeros(document.OCAPMeritoscurricularesForm.DNombre)){
         alert('Debe introducir correctamente el Nombre');
         validacion=1;   
      }

   if (document.OCAPMeritoscurricularesForm.DDescripcion.value != "" && !LetrasYNumeros(document.OCAPMeritoscurricularesForm.DDescripcion)){
         alert('Debe introducir correctamente la Descripci\u00f3n');
         validacion=1;   
      }

   if (document.OCAPMeritoscurricularesForm.DObservaciones.value != "" && !LetrasYNumeros(document.OCAPMeritoscurricularesForm.DObservaciones)){
         alert('Debe introducir correctamente las Observaciones');
         validacion=1;   
      }
      
   if (document.OCAPMeritoscurricularesForm.NCreditos.value != "" ){
      document.OCAPMeritoscurricularesForm.NCreditos.value = document.OCAPMeritoscurricularesForm.NCreditos.value.replace(",",".");
      if (!validarImporte(document.OCAPMeritoscurricularesForm.NCreditos, 2, 2)){
         alert('Debe introducir correctamente el Cr\u00e9dito');
         validacion=1;   
      }    
   }
      
   if ((document.OCAPMeritoscurricularesForm.DCreacion.value != "" ) || (document.OCAPMeritoscurricularesForm.MCreacion.value != "" ) || (document.OCAPMeritoscurricularesForm.ACreacion.value != "")){
      var fecha = document.OCAPMeritoscurricularesForm.DCreacion.value + '/' + document.OCAPMeritoscurricularesForm.MCreacion.value + '/' + document.OCAPMeritoscurricularesForm.ACreacion.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Creaci\u00f3n');
         validacion=1;   
      }
   }
      
   if ((document.OCAPMeritoscurricularesForm.DModificacion.value != "" ) || (document.OCAPMeritoscurricularesForm.MModificacion.value != "" ) || (document.OCAPMeritoscurricularesForm.AModificacion.value != "")){
      var fecha = document.OCAPMeritoscurricularesForm.DModificacion.value + '/' + document.OCAPMeritoscurricularesForm.MModificacion.value + '/' + document.OCAPMeritoscurricularesForm.AModificacion.value; 
   
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Modificaci\u00f3n');
         validacion=1;   
      }
   } 
      
   if (validacion==0) document.forms[0].submit();    
}   
function bajasClave(cMeritoId)
{
   if (confirm('Va a ELIMINAR el registro selecionado')){
      document.forms[0].action = 'OCAPMeritoscurriculares.do?accion=borrar';
      document.forms[0].cMeritoIdS.value = cMeritoId;

      document.forms[0].submit(); 
   }else{
      return false;
   }
}

function limpiar() {
   document.forms[0].CEstatutario_id.options[0].selected='selected';
   document.forms[0].CDefmerito_id.options[0].selected='selected';
   document.forms[0].CActividad_id.options[0].selected='selected';
   document.forms[0].CModalidad_id.options[0].selected='selected';
   document.forms[0].CFactor_id.options[0].selected='selected';
   document.forms[0].CTipo_id.options[0].selected='selected';   
   document.forms[0].CTipo_merito.value='';
   document.forms[0].DNombrecorto.value='';
   document.forms[0].DNombre.value='';
   document.forms[0].DDescripcion.value='';
   document.forms[0].DObservaciones.value='';
   document.forms[0].NCreditos.value='';
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
<html:form action="/OCAPMeritoscurriculares.do?accion=buscar">

	<div class="ocultarCampo">

		<div class="cuadroContenedorConsultas">
			<div class="titulocajaformulario">Consulta de M&eacute;ritos
				Curriculares</div>
			<logic:present name="errorConsultando">
				<p>
					<label><bean:write name="errorConsultando" filter="false" /></label>
				</p>
			</logic:present>
			<logic:notPresent name="errorConsultando">
				<div class="cajaformulario">
					<fieldset class="normales">

						<label for="idDefmerito" class="obligado"> M&eacute;rito:
							<html:select property="CDefmerito_id"
								styleClass="cbCuadros colocaMeritosMeritCBCon" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options
									collection="<%=Constantes.COMBO_DEF_MERITOSCURRICULARES%>"
									property="CDefmeritoId" labelProperty="DNombre" />
							</html:select>
						</label>
						<html:errors property="CDefmerito_id" />

						<br /> <label for="idEstatutario" class="obligado">
							Estatuto: <html:select property="CEstatut_id"
								styleClass="cbCuadros colocaMeritosEstatCBCon" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ESTATUTARIO%>"
									property="CEstatutId" labelProperty="DNombre" />
							</html:select>
						</label>
						<html:errors property="CEstatut_id" />

						<br /> <label for="idActividad" class="obligado">
							Actividad: <html:select property="CActividad_id"
								styleClass="cbCuadros colocaMeritosActivCBCon" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ACTIVIDAD%>"
									property="CActividadId" labelProperty="DNombre" />
							</html:select>
						</label>
						<html:errors property="CActividad_id" />

						<br /> <label for="idModalidad" class="obligado">
							Modalidad: <html:select property="CModalidad_id"
								styleClass="cbCuadros colocaMeritosModalCBCon" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_MODALIDAD%>"
									property="CModalidadId" labelProperty="DNombre" />
							</html:select>
						</label>
						<html:errors property="CModalidad_id" />

						<br /> <label for="idFactores" class="obligado"> Factor:
							<html:select property="CFactor_id"
								styleClass="cbCuadros colocaMeritosFactorCBCon" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_FACTORESIMPACTO%>"
									property="CFactorId" labelProperty="DNombre" />
							</html:select>
						</label>
						<html:errors property="CActividad_id" />

						<br /> <label for="idTipos" class="obligado"> Tipo de
							firmante: <html:select property="CTipo_id"
								styleClass="cbCuadros colocaMeritosTipoFCBCon" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_TIPOSFIRMANTE%>"
									property="CTipoId" labelProperty="DNombre" />
							</html:select>
						</label>
						<html:errors property="CTipo_id" />

						<br /> <label class="obligado"> Tipo de m&eacute;rito:</label>
						<html:text property="CTipo_merito" tabindex="18"
							styleClass="inputColor colocaMeritosTipoMCBCon" size="30"
							maxlength="30" />
						&nbsp;
						<html:errors property="CTipo_merito" />

						<br /> <label class="obligado"> Nombre corto:</label>
						<html:text property="DNombrecorto" tabindex="18"
							styleClass="inputColor colocaMeritosNomCCBCon" size="10"
							maxlength="10" />
						&nbsp;
						<html:errors property="DNombrecorto" />

						<br /> <label class="obligado"> Nombre:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaMeritosNombreCBCon" size="60"
							maxlength="200" />
						&nbsp;
						<html:errors property="DNombre" />

						<br /> <label class="obligado"> Descripci&oacute;n:</label>
						<html:text property="DDescripcion" tabindex="18"
							styleClass="inputColor colocaMeritosDescCBCon" size="60"
							maxlength="300" />
						&nbsp;
						<html:errors property="DDescripcion" />

						<br /> <label class="obligado"> Observaciones:</label>
						<html:text property="DObservaciones" tabindex="18"
							styleClass="inputColor colocaMeritosObserCBCon" size="60"
							maxlength="300" />
						&nbsp;
						<html:errors property="DObservaciones" />

						<br /> <label class="obligado"> Cr&eacute;dito:</label>
						<html:text property="NCreditos" tabindex="18"
							styleClass="inputColor colocaMeritosCreditCBCon" size="5"
							maxlength="5" />
						&nbsp;
						<html:errors property="NCreditos" />

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
								<th class="col1" id="cMerito">M&eacute;rito</th>
								<th class="col2" id="cEstat">Estatuto</th>
								<th class="col3" id="cActivi">Actividad</th>
								<th class="col4" id="cModali">Modalidad</th>
								<th class="col5" id="cTipoM">Tipo M&eacute;rito</th>
								<th class="col6" id="cNombreC">Nombre corto</th>
								<th class="col7" id="cNombre">Nombre</th>
								<th class="col8" id="cCredito">Cr&eacute;dito</th>

							</tr>
						</logic:present>
						<logic:present name="paginaOCAPMeritoscurricularesOT"
							property="elementos">
							<logic:iterate id="elemento"
								name="paginaOCAPMeritoscurricularesOT" property="elementos">
								<tr>
									<td class="campoIcono"><a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CMeritoId"/>');
return false;"><img
											src="imagenes/eliminar.gif" title="Eliminar registro"
											alt="Eliminar registro"></a>&nbsp;<a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CMeritoId"/>');return false;"></a>
									</td>
									<td class="campoIcono"><a
										href="OCAPMeritoscurriculares.do?accion=irEditar&cMeritoIdS=<bean:write name="elemento" property="CMeritoId"/>">
											<img src="imagenes/editar.gif" title="Editar registro"
											alt="Editar registro">
									</a></td>
									<td class="col1" headers="cMerito"><a
										href="OCAPMeritoscurriculares.do?accion=detalle&cMeritoIdS=<bean:write name="elemento" property="CMeritoId"/>">
											<bean:write name="elemento" property="DDefmeritoNombre" />
									</a></td>
									<td class="col2" headers="cEstat"><a
										href="OCAPMeritoscurriculares.do?accion=detalle&cMeritoIdS=<bean:write name="elemento" property="CMeritoId"/>">
											<bean:write name="elemento" property="DEstatutNombre" />
									</a></td>
									<td class="col3" headers="cActivi"><a
										href="OCAPMeritoscurriculares.do?accion=detalle&cMeritoIdS=<bean:write name="elemento" property="CMeritoId"/>">
											<bean:write name="elemento" property="DNombre" />
									</a></td>
									<td class="col4" headers="cModali"><a
										href="OCAPMeritoscurriculares.do?accion=detalle&cMeritoIdS=<bean:write name="elemento" property="CMeritoId"/>">
											<bean:write name="elemento" property="DNombre" />
									</a></td>
									<td class="col5" headers="cTipoM"><a
										href="OCAPMeritoscurriculares.do?accion=detalle&cMeritoIdS=<bean:write name="elemento" property="CMeritoId"/>">
											<bean:write name="elemento" property="CTipoMerito" />
									</a></td>
									<td class="col6" headers="cNombreC"><a
										href="OCAPMeritoscurriculares.do?accion=detalle&cMeritoIdS=<bean:write name="elemento" property="CMeritoId"/>">
											<bean:write name="elemento" property="DNombrecorto" />
									</a></td>
									<td class="col7" headers="cNombre"><a
										href="OCAPMeritoscurriculares.do?accion=detalle&cMeritoIdS=<bean:write name="elemento" property="CMeritoId"/>">
											<bean:write name="elemento" property="DNombre" />
									</a></td>
									<td class="col8" headers="cCredito"><a
										href="OCAPMeritoscurriculares.do?accion=detalle&cMeritoIdS=<bean:write name="elemento" property="CMeritoId"/>">
											<bean:write name="elemento" property="NCreditos" />
									</a></td>
								</tr>

							</logic:iterate>

							<tr>
								<td colspan="10">

									<table class="paginacionConsultas">
										<tr>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPMeritoscurricularesOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Primera"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPMeritoscurricularesOT" property="URLPrimeraPagina"/>"' />
												</logic:equal></td>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPMeritoscurricularesOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Anterior"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPMeritoscurricularesOT" property="URLPaginaAnterior"/>"' />
												</logic:equal></td>
											<td class="textoTituloGris parteCen">Registro: [<bean:write
													name="paginaOCAPMeritoscurricularesOT"
													property="registroActual" />/<bean:write
													name="paginaOCAPMeritoscurricularesOT"
													property="NRegistros" />]&nbsp;&nbsp; P&aacute;gina: [<bean:write
													name="paginaOCAPMeritoscurricularesOT"
													property="paginaActual" />/<bean:write
													name="paginaOCAPMeritoscurricularesOT" property="NPaginas" />]
											</td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPMeritoscurricularesOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Siguiente"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPMeritoscurricularesOT" property="URLPaginaSiguiente"/>"' />
												</logic:equal></td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPMeritoscurricularesOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="&Uacute;ltima"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPMeritoscurricularesOT" property="URLUltimaPagina"/>"' />
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
		<input type="hidden" name="cMeritoIdS" value="" />

	</div>
	<!-- /Fin de ocultarCampo -->

</html:form>
