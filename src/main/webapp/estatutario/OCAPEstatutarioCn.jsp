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
     
   if (document.OCAPEstatutarioForm.DNombre.value != "" && !LetrasYNumeros(document.OCAPEstatutarioForm.DNombre)){
         alert('Debe introducir correctamente el Nombre');
         validacion=1;   
      }
      
   if (document.OCAPEstatutarioForm.DDescripcion.value != "" && !LetrasYNumeros(document.OCAPEstatutarioForm.DDescripcion)){
         alert('Debe introducir correctamente la Descripci\u00f3n');
         validacion=1;   
      }      
       
   if ((document.OCAPEstatutarioForm.DCreacion.value != "" ) || (document.OCAPEstatutarioForm.MCreacion.value != "" ) || (document.OCAPEstatutarioForm.ACreacion.value != "")){
      var fecha = document.OCAPEstatutarioForm.DCreacion.value + '/' + document.OCAPEstatutarioForm.MCreacion.value + '/' + document.OCAPEstatutarioForm.ACreacion.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Creaci\u00f3n');
         validacion=1;   
      }
   }
      
   if ((document.OCAPEstatutarioForm.DModificacion.value != "" ) || (document.OCAPEstatutarioForm.MModificacion.value != "" ) || (document.OCAPEstatutarioForm.AModificacion.value != "")){
      var fecha = document.OCAPEstatutarioForm.DModificacion.value + '/' + document.OCAPEstatutarioForm.MModificacion.value + '/' + document.OCAPEstatutarioForm.AModificacion.value; 
   
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Modificaci\u00f3n');
         validacion=1;   
      }
   } 
      
   if (validacion==0) document.forms[0].submit();    
}   
function bajasClave(cEstatutId)
{
   if (confirm('Va a ELIMINAR el registro selecionado')){
      document.forms[0].action = 'OCAPEstatutario.do?accion=borrar';
      document.forms[0].cEstatutIdS.value = cEstatutId;

      document.forms[0].submit(); 
   }else{
      return false;
   }
}

function limpiar() {
   document.forms[0].CPersonal_id.options[0].selected='selected';
   document.forms[0].DNombre.value='';
   document.forms[0].DDescripcion.value='';
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
<html:form action="/OCAPEstatutario.do?accion=buscar">

	<div class="ocultarCampo">

		<div class="cuadroContenedorConsultas">
			<div class="titulocajaformulario">Consulta de Estatutos</div>
			<logic:present name="errorConsultando">
				<p>
					<label><bean:write name="errorConsultando" filter="false" /></label>
				</p>
			</logic:present>
			<logic:notPresent name="errorConsultando">
				<div class="cajaformulario">
					<fieldset class="normales">
						<label for="idPersonal" class="obligado"> Nombre de
							Personal: <html:select property="CPersonal_id"
								styleClass="cbCuadros colocaNombrePerEstat" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_PERSONAL%>"
									property="CPersonalId" labelProperty="DNombre" />
							</html:select>
						</label>

						<html:errors property="CPersonal_id" />
						<br /> <label class="obligado">Nombre de Estatuto:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaNombreEstEstat" size="60"
							maxlength="100" />
						&nbsp;
						<html:errors property="DNombre" />
						<br /> <label class="obligado">Descripci&oacute;n:</label>
						<html:text property="DDescripcion" tabindex="18"
							styleClass="inputColor colocaDescEstatDet" size="60"
							maxlength="200" />
						&nbsp;
						<html:errors property="DDescripcion" />
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
				<table border="0" class="tablaConsultas tabla5">
					<logic:notPresent name="sinDatos">
						<logic:present name="primeraConsulta">
							<tr>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th class="col1" id="cPerson">Personal</th>
								<th class="col2" id="cNombre">Nombre</th>
								<th class="col3" id="cDescri">Descripci&oacute;n</th>
								<th class="col4" id="cFechaC">Fecha creaci&oacute;n</th>
								<th class="col5" id="cFechaM">Fecha modificaci&oacute;n</th>

							</tr>
						</logic:present>
						<logic:present name="paginaOCAPEstatutarioOT" property="elementos">
							<logic:iterate id="elemento" name="paginaOCAPEstatutarioOT"
								property="elementos">
								<tr>
									<td class="campoIcono"><a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CEstatutId"/>');
return false;"><img
											src="imagenes/eliminar.gif" title="Eliminar registro"
											alt="Eliminar registro"></a>&nbsp;<a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CEstatutId"/>');return false;"></a>
									</td>
									<td class="campoIcono"><a
										href="OCAPEstatutario.do?accion=irEditar&cEstatutIdS=<bean:write name="elemento" property="CEstatutId"/>">
											<img src="imagenes/editar.gif" title="Editar registro"
											alt="Editar registro">
									</a></td>

									<td class="col1" headers="cPerson"><a
										href="OCAPEstatutario.do?accion=detalle&cEstatutIdS=<bean:write name="elemento" property="CEstatutId"/>">
											<bean:write name="elemento" property="DPersonalNombre" />
									</a></td>
									<td class="col2" headers="cNombre"><a
										href="OCAPEstatutario.do?accion=detalle&cEstatutIdS=<bean:write name="elemento" property="CEstatutId"/>">
											<bean:write name="elemento" property="DNombre" />
									</a></td>
									<td class="col3" headers="cDescri"><a
										href="OCAPEstatutario.do?accion=detalle&cEstatutIdS=<bean:write name="elemento" property="CEstatutId"/>">
											<bean:write name="elemento" property="DDescripcion" />
									</a></td>
									<td class="col4" headers="cFechaC"><a
										href="OCAPEstatutario.do?accion=detalle&cEstatutIdS=<bean:write name="elemento" property="CEstatutId"/>">
											<bean:write name="elemento" property="FCreacion"
												format="dd/MM/yyyy" />
									</a></td>
									<td class="col5" headers="cFechaM"><a
										href="OCAPEstatutario.do?accion=detalle&cEstatutIdS=<bean:write name="elemento" property="CEstatutId"/>">
											<bean:write name="elemento" property="FModificacion"
												format="dd/MM/yyyy" />
									</a></td>
								</tr>

							</logic:iterate>

							<tr>
								<td colspan="7">

									<table class="paginacionConsultas">
										<tr>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPEstatutarioOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Primera"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPEstatutarioOT" property="URLPrimeraPagina"/>"' />
												</logic:equal></td>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPEstatutarioOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Anterior"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPEstatutarioOT" property="URLPaginaAnterior"/>"' />
												</logic:equal></td>
											<td class="textoTituloGris parteCen">Registro: [<bean:write
													name="paginaOCAPEstatutarioOT" property="registroActual" />/<bean:write
													name="paginaOCAPEstatutarioOT" property="NRegistros" />]&nbsp;&nbsp;
												P&aacute;gina: [<bean:write name="paginaOCAPEstatutarioOT"
													property="paginaActual" />/<bean:write
													name="paginaOCAPEstatutarioOT" property="NPaginas" />]
											</td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPEstatutarioOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Siguiente"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPEstatutarioOT" property="URLPaginaSiguiente"/>"' />
												</logic:equal></td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPEstatutarioOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="&Uacute;ltima"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPEstatutarioOT" property="URLUltimaPagina"/>"' />
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
		<input type="hidden" name="cEstatutIdS" value="" />

	</div>
	<!-- /Fin de ocultarCampo -->

</html:form>
