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
   
   if (document.OCAPCreditosForm.NCreditos.value != "" ){
      document.OCAPCreditosForm.NCreditos.value = document.OCAPCreditosForm.NCreditos.value.replace(",",".");
      if (!validarImporte(document.OCAPCreditosForm.NCreditos, 2, 2)){
         alert('Debe introducir correctamente el Cr\u00e9dito');
         validacion=1;   
      }    
   }

      
   if ((document.OCAPCreditosForm.DCreacion.value != "" ) || (document.OCAPCreditosForm.MCreacion.value != "" ) || (document.OCAPCreditosForm.ACreacion.value != "")){
      var fecha = document.OCAPCreditosForm.DCreacion.value + '/' + document.OCAPCreditosForm.MCreacion.value + '/' + document.OCAPCreditosForm.ACreacion.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Creaci\u00f3n');
         validacion=1;   
      }
   }
      
   if ((document.OCAPCreditosForm.DModificacion.value != "" ) || (document.OCAPCreditosForm.MModificacion.value != "" ) || (document.OCAPCreditosForm.AModificacion.value != "")){
      var fecha = document.OCAPCreditosForm.DModificacion.value + '/' + document.OCAPCreditosForm.MModificacion.value + '/' + document.OCAPCreditosForm.AModificacion.value; 
   
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Modificaci\u00f3n');
         validacion=1;   
      }
   } 
      
   if (validacion==0) document.forms[0].submit();    
}   
function bajasClave(cCreditoId)
{
   if (confirm('Va a ELIMINAR el registro selecionado')){
      document.forms[0].action = 'OCAPCreditos.do?accion=borrar';
      document.forms[0].cCreditoIdS.value = cCreditoId;

      document.forms[0].submit(); 
   }else{
      return false;
   }
}

function limpiar() {
   document.forms[0].CEstatut_id.options[0].selected='selected'; 
   document.forms[0].CGrado_id.options[0].selected='selected';    
   document.forms[0].CDefmerito_id.options[0].selected='selected'; 
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
<html:form action="/OCAPCreditos.do?accion=buscar">

	<div class="ocultarCampo">

		<div class="cuadroContenedorConsultas">
			<div class="titulocajaformulario">Consulta de Cr&eacute;ditos</div>
			<logic:present name="errorConsultando">
				<p>
					<label><bean:write name="errorConsultando" filter="false" /></label>
				</p>
			</logic:present>
			<logic:notPresent name="errorConsultando">
				<div class="cajaformulario">
					<fieldset class="normales">

						<label for="idEstatuto" class="obligado"> Estatuto: <html:select
								property="CEstatut_id" styleClass="cbCuadros" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ESTATUTARIO%>"
									property="CEstatutId" labelProperty="DNombre" />
							</html:select>
						</label>
						<html:errors property="CEstatut_id" />
						<br /> <label for="idGrado" class="obligado"> Grado: <html:select
								property="CGrado_id" styleClass="cbCuadros colocaGradoCreditCB"
								size="1" tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
									property="CGradoId" labelProperty="DNombre" />
							</html:select>
						</label>
						<html:errors property="CGrado_id" />
						<br /> <label for="idDefmerito" class="obligado">
							M&eacute;rito:</label>
						<html:select property="CDefmerito_id"
							styleClass="cbCuadros colocaMeritoCreditCB" size="1"
							tabindex="18">
							<html:option value="">Seleccione...</html:option>
							<html:options
								collection="<%=Constantes.COMBO_DEF_MERITOSCURRICULARES%>"
								property="CDefmeritoId" labelProperty="DNombre" />
						</html:select>
						</label>
						<html:errors property="CDefmerito_id" />
						<br /> <label class="obligado"> Cr&eacute;dito:</label>
						<html:text property="NCreditos" tabindex="18"
							styleClass="inputColor colocaCredtoConCredit" size="5"
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
				<table border="0" class="tablaConsultas tabla6">
					<logic:notPresent name="sinDatos">
						<logic:present name="primeraConsulta">
							<tr>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th class="col1" id="cEstat">Estatuto</th>
								<th class="col2" id="cGrado">Grado</th>
								<th class="col3" id="cMerito">M&eacute;rito</th>
								<th class="col4" id="cCredit">Cr&eacute;dito</th>
								<th class="col5" id="cfechaC">Fecha creaci&oacute;n</th>
								<th class="col6" id="cFechaM">Fecha modificaci&oacute;n</th>

							</tr>
						</logic:present>
						<logic:present name="paginaOCAPCreditosOT" property="elementos">
							<logic:iterate id="elemento" name="paginaOCAPCreditosOT"
								property="elementos">
								<tr>
									<td class="campoIcono"><a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CCreditoId"/>');
return false;"><img
											src="imagenes/eliminar.gif" title="Eliminar registro"
											alt="Eliminar registro"></a>&nbsp;<a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CCreditoId"/>');return false;"></a>
									</td>
									<td class="campoIcono"><a
										href="OCAPCreditos.do?accion=irEditar&cCreditoIdS=<bean:write name="elemento" property="CCreditoId"/>">
											<img src="imagenes/editar.gif" title="Editar registro"
											alt="Editar registro">
									</a></td>

									<td class="col1" headers="cEstat"><a
										href="OCAPCreditos.do?accion=detalle&cCreditoIdS=<bean:write name="elemento" property="CCreditoId"/>">
											<bean:write name="elemento" property="DEstatutNombre" />
									</a></td>
									<td class="col2" headers="cGrado"><a
										href="OCAPCreditos.do?accion=detalle&cCreditoIdS=<bean:write name="elemento" property="CCreditoId"/>">
											<bean:write name="elemento" property="DGradoNombre" />
									</a></td>
									<td class="col3" headers="cMerito"><a
										href="OCAPCreditos.do?accion=detalle&cCreditoIdS=<bean:write name="elemento" property="CCreditoId"/>">
											<bean:write name="elemento" property="DDefmeritoNombre" />
									</a></td>
									<td class="col4" headers="cCredit"><a
										href="OCAPCreditos.do?accion=detalle&cCreditoIdS=<bean:write name="elemento" property="CCreditoId"/>">
											<bean:write name="elemento" property="NCreditos" />
									</a></td>
									<td class="col5" headers="cFechaC"><a
										href="OCAPCreditos.do?accion=detalle&cCreditoIdS=<bean:write name="elemento" property="CCreditoId"/>">
											<bean:write name="elemento" property="FCreacion"
												format="dd/MM/yyyy" />
									</a></td>
									<td class="col6" headers="cFechaM"><a
										href="OCAPCreditos.do?accion=detalle&cCreditoIdS=<bean:write name="elemento" property="CCreditoId"/>">
											<bean:write name="elemento" property="FModificacion"
												format="dd/MM/yyyy" />
									</a></td>
								</tr>

							</logic:iterate>
							<tr>
								<td colspan="8">

									<table class="paginacionConsultas">
										<tr>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPCreditosOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Primera"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPCreditosOT" property="URLPrimeraPagina"/>"' />
												</logic:equal></td>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPCreditosOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Anterior"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPCreditosOT" property="URLPaginaAnterior"/>"' />
												</logic:equal></td>
											<td width="100%" align="center"
												class="textoTituloGris parteCen">Registro: [<bean:write
													name="paginaOCAPCreditosOT" property="registroActual" />/<bean:write
													name="paginaOCAPCreditosOT" property="NRegistros" />]&nbsp;&nbsp;
												P&aacute;gina: [<bean:write name="paginaOCAPCreditosOT"
													property="paginaActual" />/<bean:write
													name="paginaOCAPCreditosOT" property="NPaginas" />]
											</td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPCreditosOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Siguiente"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPCreditosOT" property="URLPaginaSiguiente"/>"' />
												</logic:equal></td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPCreditosOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="&Uacute;ltima"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPCreditosOT" property="URLUltimaPagina"/>"' />
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
		<input type="hidden" name="cCreditoIdS" value="" />

	</div>
	<!-- /Fin de ocultarCampo -->

</html:form>
