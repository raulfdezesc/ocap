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
   
   if (document.OCAPMensajesForm.DDescripcion.value != "" && !LetrasYNumeros(document.OCAPMensajesForm.DDescripcion)){
         alert('Debe introducir correctamente la descripci\u00f3n');
         validacion=1;   
      }
      
   if ((document.OCAPMensajesForm.DCreacion.value != "" ) || (document.OCAPMensajesForm.MCreacion.value != "" ) || (document.OCAPMensajesForm.ACreacion.value != "")){
      var fecha = document.OCAPMensajesForm.DCreacion.value + '/' + document.OCAPMensajesForm.MCreacion.value + '/' + document.OCAPMensajesForm.ACreacion.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Creaci\u00f3n');
         validacion=1;   
      }
   }
      
   if ((document.OCAPMensajesForm.DModificacion.value != "" ) || (document.OCAPMensajesForm.MModificacion.value != "" ) || (document.OCAPMensajesForm.AModificacion.value != "")){
      var fecha = document.OCAPMensajesForm.DModificacion.value + '/' + document.OCAPMensajesForm.MModificacion.value + '/' + document.OCAPMensajesForm.AModificacion.value; 
   
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Modificaci\u00f3n');
         validacion=1;   
      }
   } 
      
   if (validacion==0) document.forms[0].submit();    
}   
function bajasClave(cMensajeId)
{
   if (confirm('Va a ELIMINAR el registro selecionado')){
      document.forms[0].action = 'OCAPMensajes.do?accion=borrar';
      document.forms[0].cMensajeIdS.value = cMensajeId;

      document.forms[0].submit(); 
   }else{
      return false;
   }
}

function limpiar() {
   document.forms[0].CEstatut_id.options[0].selected='selected';
   document.forms[0].CGrado_id.options[0].selected='selected';
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
<html:form action="/OCAPMensajes.do?accion=buscar">

	<div class="ocultarCampo">

		<div class="cuadroContenedorConsultas">
			<div class="titulocajaformulario">Consulta de Mensajes</div>
			<logic:present name="errorConsultando">
				<p>
					<label><bean:write name="errorConsultando" filter="false" /></label>
				</p>
			</logic:present>
			<logic:notPresent name="errorConsultando">
				<div class="cajaformulario">
					<fieldset class="normales">

						<label for="idEstatuto" class="obligado"> Estatuto: <html:select
								property="CEstatut_id"
								styleClass="cbCuadros colocaEstatMensajes" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ESTATUTARIO%>"
									property="CEstatutId" labelProperty="DNombre" />
							</html:select>
						</label>
						<html:errors property="CEstatut_id" />
						<br /> <label for="idGrado" class="obligado"> Grado: <html:select
								property="CGrado_id" styleClass="cbCuadros colocaGradoMensajes"
								size="1" tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
									property="CGradoId" labelProperty="DNombre" />
							</html:select>
						</label>
						<html:errors property="CGrado_id" />
						<br /> <label class="obligado"> Descripci&oacute;n:</label>
						<html:text property="DDescripcion" tabindex="18"
							styleClass="inputColor colocaDescMensajes" size="60"
							maxlength="4000" />
						&nbsp;
						<html:errors property="NMensaje_Mensajes" />
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
								<th class="col1" id="cEstat">Estatuto</th>
								<th class="col2" id="cGrado">Grado</th>
								<th class="col3" id="cDescri">Descripci&oacute;n</th>
								<th class="col4" id="cFechaC">Fecha creaci&oacute;n</th>
								<th class="col5" id="cFechaM">Fecha modificaci&oacute;n</th>

							</tr>
						</logic:present>
						<logic:present name="paginaOCAPMensajesOT" property="elementos">
							<logic:iterate id="elemento" name="paginaOCAPMensajesOT"
								property="elementos">
								<tr>
									<td class="campoIcono"><a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CMensajeId"/>');
return false;"><img
											src="imagenes/eliminar.gif" title="Eliminar registro"
											alt="Eliminar registro"></a>&nbsp;<a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CMensajeId"/>');return false;"></a>
									</td>
									<td class="campoIcono"><a
										href="OCAPMensajes.do?accion=irEditar&cMensajeIdS=<bean:write name="elemento" property="CMensajeId"/>">
											<img src="imagenes/editar.gif" title="Editar registro"
											alt="Editar registro">
									</a></td>

									<td class="col1" headers="cEstat"><a
										href="OCAPMensajes.do?accion=detalle&cMensajeIdS=<bean:write name="elemento" property="CMensajeId"/>">
											<bean:write name="elemento" property="DEstatutNombre" />
									</a></td>
									<td class="col2" headers="cGrado"><a
										href="OCAPMensajes.do?accion=detalle&cMensajeIdS=<bean:write name="elemento" property="CMensajeId"/>">
											<bean:write name="elemento" property="DGradoNombre" />
									</a></td>
									<td class="col3" headers="cDescri"><a
										href="OCAPMensajes.do?accion=detalle&cMensajeIdS=<bean:write name="elemento" property="CMensajeId"/>">
											<bean:write name="elemento" property="DDescripcion"
												filter="false" />
									</a></td>
									<td class="col4" headers="cFechaC"><a
										href="OCAPMensajes.do?accion=detalle&cMensajeIdS=<bean:write name="elemento" property="CMensajeId"/>">
											<bean:write name="elemento" property="FCreacion"
												format="dd/MM/yyyy" />
									</a></td>
									<td class="col5" headers="cFechaM"><a
										href="OCAPMensajes.do?accion=detalle&cMensajeIdS=<bean:write name="elemento" property="CMensajeId"/>">
											<bean:write name="elemento" property="FModificacion"
												format="dd/MM/yyyy" />
									</a></td>
								</tr>

							</logic:iterate>

							<tr>
								<td colspan="50">

									<table width="90%" class="paginacionConsultas">
										<tr>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPMensajesOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Primera"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPMensajesOT" property="URLPrimeraPagina"/>"' />
												</logic:equal></td>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPMensajesOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Anterior"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPMensajesOT" property="URLPaginaAnterior"/>"' />
												</logic:equal></td>
											<td width="100%" align="center"
												class="textoTituloGris parteCen">Registro: [<bean:write
													name="paginaOCAPMensajesOT" property="registroActual" />/<bean:write
													name="paginaOCAPMensajesOT" property="NRegistros" />]&nbsp;&nbsp;
												P&aacute;gina: [<bean:write name="paginaOCAPMensajesOT"
													property="paginaActual" />/<bean:write
													name="paginaOCAPMensajesOT" property="NPaginas" />]
											</td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPMensajesOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Siguiente"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPMensajesOT" property="URLPaginaSiguiente"/>"' />
												</logic:equal></td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPMensajesOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="&Uacute;ltima"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPMensajesOT" property="URLUltimaPagina"/>"' />
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
		<input type="hidden" name="cMensajeIdS" value="" />

	</div>
	<!-- /Fin de ocultarCampo -->

</html:form>
