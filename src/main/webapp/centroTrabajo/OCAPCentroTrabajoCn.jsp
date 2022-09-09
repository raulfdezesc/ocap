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
     
   if (document.OCAPCentroTrabajoForm.DNombre.value != "" && !LetrasYNumeros(document.OCAPCentroTrabajoForm.DNombre)){
         alert('Debe introducir correctamente el Nombre');
         validacion=1;   
      }
      
   if (document.OCAPCentroTrabajoForm.DDescripcion.value != "" && !LetrasYNumeros(document.OCAPCentroTrabajoForm.DDescripcion)){
         alert('Debe introducir correctamente la Descripci\u00f3n');
         validacion=1;   
      }      

   if (document.OCAPCentroTrabajoForm.AObservaciones.value != "" && !LetrasYNumeros(document.OCAPCentroTrabajoForm.AObservaciones)){
         alert('Debe introducir correctamente las Observaciones');
         validacion=1;   
      }      
          
   if (document.OCAPCentroTrabajoForm.ADireccion.value != "" && !LetrasYNumeros(document.OCAPCentroTrabajoForm.ADireccion)){
         alert('Debe introducir correctamente la Direcci\u00f3n');
         validacion=1;   
      }      

   if (document.OCAPCentroTrabajoForm.ACodigopostal.value != "" && !soloNumeros(document.OCAPCentroTrabajoForm.ACodigopostal)){
         alert('Debe introducir correctamente el C\u00f3digo Postal');
         validacion=1;   
      }      
                                         
   if (document.OCAPCentroTrabajoForm.ACiudad.value != "" && !LetrasYNumeros(document.OCAPCentroTrabajoForm.ACiudad)){
         alert('Debe introducir correctamente la Ciudad');
         validacion=1;   
      }         
       
   if ((document.OCAPCentroTrabajoForm.DCreacion.value != "" ) || (document.OCAPCentroTrabajoForm.MCreacion.value != "" ) || (document.OCAPCentroTrabajoForm.ACreacion.value != "")){
      var fecha = document.OCAPCentroTrabajoForm.DCreacion.value + '/' + document.OCAPCentroTrabajoForm.MCreacion.value + '/' + document.OCAPCentroTrabajoForm.ACreacion.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Creaci\u00f3n');
         validacion=1;   
      }
   }
      
   if ((document.OCAPCentroTrabajoForm.DModificacion.value != "" ) || (document.OCAPCentroTrabajoForm.MModificacion.value != "" ) || (document.OCAPCentroTrabajoForm.AModificacion.value != "")){
      var fecha = document.OCAPCentroTrabajoForm.DModificacion.value + '/' + document.OCAPCentroTrabajoForm.MModificacion.value + '/' + document.OCAPCentroTrabajoForm.AModificacion.value; 
   
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Modificaci\u00f3n');
         validacion=1;   
      }
   } 
      
   if (validacion==0) document.forms[0].submit();    
}   
function bajasClave(cCentrotrabajoId)
{
   if (confirm('Va a ELIMINAR el registro selecionado')){
      document.forms[0].action = 'OCAPCentroTrabajo.do?accion=borrar';
      document.forms[0].cCentrotrabajoIdS.value = cCentrotrabajoId;

      document.forms[0].submit(); 
   }else{
      return false;
   }
}

function limpiar() {
   document.forms[0].CGerencia_id.options[0].selected='selected';   
   document.forms[0].DNombre.value='';
   document.forms[0].DDescripcion.value='';
   document.forms[0].AObservaciones.value='';
   document.forms[0].ADireccion.value='';
   document.forms[0].ACodigopostal.value='';
   document.forms[0].ATelefono.value='';   
   document.forms[0].ACiudad.value='';   
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
<html:form action="/OCAPCentroTrabajo.do?accion=buscar">

	<div class="ocultarCampo">

		<div class="cuadroContenedorConsultas">
			<div class="titulocajaformulario">Consulta de Centros de
				trabajo</div>
			<logic:present name="errorConsultando">
				<p>
					<label><bean:write name="errorConsultando" filter="false" /></label>
				</p>
			</logic:present>
			<logic:notPresent name="errorConsultando">
				<div class="cajaformulario">
					<fieldset class="normales">
						<label for="idGerencia" class="obligado"> Gerencia: <html:select
								property="CGerencia_id"
								styleClass="cbCuadros colocaGerenciaCTCon" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
									property="CGerenciaId" labelProperty="DNombre" />
							</html:select>
						</label>

						<html:errors property="CGerencia_id" />
						<br /> <label class="obligado">Centro:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaCentroCTCon" size="60"
							maxlength="100" />
						&nbsp;
						<html:errors property="DNombre" />

						<br /> <label class="obligado">Descripci&oacute;n:</label>
						<html:text property="DDescripcion" tabindex="18"
							styleClass="inputColor colocaDescCT" size="60" maxlength="200" />
						&nbsp;
						<html:errors property="DDescripcion" />
						<br /> <label class="obligado"> Observaciones:</label>
						<html:text property="AObservaciones" tabindex="18"
							styleClass="inputColor colocaObservacionesCT" size="60"
							maxlength="200" />
						&nbsp;
						<html:errors property="AObservaciones" />

						<br /> <label class="obligado"> Direcci&oacute;n:</label>
						<html:text property="ADireccion" tabindex="18"
							styleClass="inputColor colocaDireccionCT" size="60"
							maxlength="100" />
						&nbsp;
						<html:errors property="ADireccion" />

						<br /> <label class="obligado"> C&oacute;digo Postal:</label>
						<html:text property="ACodigopostal" tabindex="18"
							styleClass="inputColor colocaCodPostalCT" size="5" maxlength="5" />
						&nbsp;
						<html:errors property="ACodigopostal" />

						<br /> <label class="obligado"> Tel&eacute;fono:</label>
						<html:text property="ATelefono" tabindex="18"
							styleClass="inputColor colocaTelefonoCT" size="9" maxlength="9" />
						&nbsp;
						<html:errors property="ATelefono" />

						<br /> <label class="obligado"> Ciudad:</label>
						<html:text property="ACiudad" tabindex="18"
							styleClass="inputColor colocaCiudadCT" size="50" maxlength="50" />
						&nbsp;
						<html:errors property="ACiudad" />

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
				<table border="0" class="tablaConsultas">
					<logic:notPresent name="sinDatos">
						<logic:present name="primeraConsulta">
							<tr>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th align="center" valign="middle"><label>Gerencia&nbsp;</label></th>
								<th align="center" valign="middle"><label>Nombre&nbsp;</label></th>
								<th align="center" valign="middle"><label>Descripci&oacute;n&nbsp;</label></th>
								<th align="center" valign="middle"><label>Observaciones&nbsp;</label></th>
								<th align="center" valign="middle"><label>Direcci&oacute;n&nbsp;</label></th>
								<th align="center" valign="middle"><label>CP&nbsp;</label></th>
								<th align="center" valign="middle"><label>Tel&eacute;fono&nbsp;</label></th>
								<th align="center" valign="middle"><label>Ciudad&nbsp;</label></th>
								<th align="center" valign="middle"><label>Fecha
										creaci&oacute;n&nbsp;</label></th>
								<th align="center" valign="middle"><label>Fecha
										modificaci&oacute;n&nbsp;</label></th>

							</tr>
						</logic:present>
						<logic:present name="paginaOCAPCentroTrabajoOT"
							property="elementos">
							<logic:iterate id="elemento" name="paginaOCAPCentroTrabajoOT"
								property="elementos">
								<tr>
									<td><a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CCentrotrabajoId"/>');
return false;"><img
											src="imagenes/eliminar.gif" width="17" height="17" border="0"
											align="absmiddle" title="Eliminar registro"
											alt="Eliminar registro"></a>&nbsp;<a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CCentrotrabajoId"/>');return false;"></a>
									</td>
									<td><a
										href="OCAPCentroTrabajo.do?accion=irEditar&cCentrotrabajoIdS=<bean:write name="elemento" property="CCentrotrabajoId"/>">
											<img src="imagenes/editar.gif" width="17" height="17"
											border="0" align="absmiddle" title="Editar registro"
											alt="Editar registro">
									</a></td>

									<td align="center"><a
										href="OCAPCentroTrabajo.do?accion=detalle&cCentrotrabajoIdS=<bean:write name="elemento" property="CCentrotrabajoId"/>"
										style="text-decoration: none, color:blue"> <bean:write
												name="elemento" property="DGerenciaNombre" />
									</a></td>
									<td align="right"><a
										href="OCAPCentroTrabajo.do?accion=detalle&cCentrotrabajoIdS=<bean:write name="elemento" property="CCentrotrabajoId"/>"
										style="text-decoration: none, color:blue"> <bean:write
												name="elemento" property="DNombre" />
									</a></td>
									<td align="center"><a
										href="OCAPCentroTrabajo.do?accion=detalle&cCentrotrabajoIdS=<bean:write name="elemento" property="CCentrotrabajoId"/>"
										style="text-decoration: none, color:blue"> <bean:write
												name="elemento" property="DDescripcion" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPCentroTrabajo.do?accion=detalle&cCentrotrabajoIdS=<bean:write name="elemento" property="CCentrotrabajoId"/>"
										style="text-decoration: none, color:blue"> <bean:write
												name="elemento" property="AObservaciones" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPCentroTrabajo.do?accion=detalle&cCentrotrabajoIdS=<bean:write name="elemento" property="CCentrotrabajoId"/>"
										style="text-decoration: none, color:blue"> <bean:write
												name="elemento" property="ADireccion" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPCentroTrabajo.do?accion=detalle&cCentrotrabajoIdS=<bean:write name="elemento" property="CCentrotrabajoId"/>"
										style="text-decoration: none, color:blue"> <bean:write
												name="elemento" property="ACodigopostal" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPCentroTrabajo.do?accion=detalle&cCentrotrabajoIdS=<bean:write name="elemento" property="CCentrotrabajoId"/>"
										style="text-decoration: none, color:blue"> <bean:write
												name="elemento" property="ATelefono" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPCentroTrabajo.do?accion=detalle&cCentrotrabajoIdS=<bean:write name="elemento" property="CCentrotrabajoId"/>"
										style="text-decoration: none, color:blue"> <bean:write
												name="elemento" property="ACiudad" />&nbsp;&nbsp;
									</a></td>
									<td align="center"><a
										href="OCAPCentroTrabajo.do?accion=detalle&cCentrotrabajoIdS=<bean:write name="elemento" property="CCentrotrabajoId"/>"
										style="text-decoration: none, color:blue"> <bean:write
												name="elemento" property="FCreacion" format="dd/MM/yyyy" />
									</a></td>
									<td align="center"><a
										href="OCAPCentroTrabajo.do?accion=detalle&cCentrotrabajoIdS=<bean:write name="elemento" property="CCentrotrabajoId"/>"
										style="text-decoration: none, color:blue"> <bean:write
												name="elemento" property="FModificacion" format="dd/MM/yyyy" />
									</a></td>
								</tr>
								<tr>
									<td colspan="50">
										<hr size="1" noshade />
									</td>
								</tr>

							</logic:iterate>
							<tr>
								<td colspan="12">

									<table class="paginacionConsultas">
										<tr>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPCentroTrabajoOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Primera"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPCentroTrabajoOT" property="URLPrimeraPagina"/>"' />
												</logic:equal></td>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPCentroTrabajoOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Anterior"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPCentroTrabajoOT" property="URLPaginaAnterior"/>"' />
												</logic:equal></td>
											<td class="textoTituloGris parteCen">Registro: [<bean:write
													name="paginaOCAPCentroTrabajoOT" property="registroActual" />/<bean:write
													name="paginaOCAPCentroTrabajoOT" property="NRegistros" />]&nbsp;&nbsp;
												P&aacute;gina: [<bean:write name="paginaOCAPCentroTrabajoOT"
													property="paginaActual" />/<bean:write
													name="paginaOCAPCentroTrabajoOT" property="NPaginas" />]
											</td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPCentroTrabajoOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Siguiente"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPCentroTrabajoOT" property="URLPaginaSiguiente"/>"' />
												</logic:equal></td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPCentroTrabajoOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="&Uacute;ltima"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPCentroTrabajoOT" property="URLUltimaPagina"/>"' />
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
		<input type="hidden" name="cCentrotrabajoIdS" value="" />

	</div>
	<!-- /Fin de ocultarCampo -->

</html:form>
