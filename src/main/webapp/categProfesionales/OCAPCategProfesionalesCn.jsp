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
     
   if (document.OCAPCategProfesionalesForm.DNombre.value != "" && !LetrasYNumeros(document.OCAPCategProfesionalesForm.DNombre)){
         alert('Debe introducir correctamente el Nombre');
         validacion=1;   
      }
       
   if (document.OCAPCategProfesionalesForm.DDescripcion.value != "" && !LetrasYNumeros(document.OCAPCategProfesionalesForm.DDescripcion)){
         alert('Debe introducir correctamente la Descripci\u00f3n');
         validacion=1;   
      }
              
   if ((document.OCAPCategProfesionalesForm.DCreacion.value != "" ) || (document.OCAPCategProfesionalesForm.MCreacion.value != "" ) || (document.OCAPCategProfesionalesForm.ACreacion.value != "")){
      var fecha = document.OCAPCategProfesionalesForm.DCreacion.value + '/' + document.OCAPCategProfesionalesForm.MCreacion.value + '/' + document.OCAPCategProfesionalesForm.ACreacion.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Creaci\u00f3n');
         validacion=1;   
      }
   }
      
   if ((document.OCAPCategProfesionalesForm.DModificacion.value != "" ) || (document.OCAPCategProfesionalesForm.MModificacion.value != "" ) || (document.OCAPCategProfesionalesForm.AModificacion.value != "")){
      var fecha = document.OCAPCategProfesionalesForm.DModificacion.value + '/' + document.OCAPCategProfesionalesForm.MModificacion.value + '/' + document.OCAPCategProfesionalesForm.AModificacion.value; 
   
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Modificaci\u00f3n');
         validacion=1;   
      }
   } 
      
   if (validacion==0) document.forms[0].submit();    
}   
function bajasClave(cProfesionalId)
{
   if (confirm('Va a ELIMINAR el registro selecionado')){
      document.forms[0].action = 'OCAPCategProfesionales.do?accion=borrar';
      document.forms[0].cProfesionalIdS.value = cProfesionalId;

      document.forms[0].submit(); 
   }else{
      return false;
   }
}

function limpiar() {
   document.forms[0].CEstatut_id.options[0].selected='selected';
   document.forms[0].DNombre.value='';
   document.forms[0].DDescripcion.value='';
   document.forms[0].ACreacion.value='';
   document.forms[0].MCreacion.value='';
   document.forms[0].DCreacion.value='';
   document.forms[0].AModificacion.value='';  
   document.forms[0].MModificacion.value='';
   document.forms[0].DModificacion.value='';
   document.forms[0].CModalidad_id.options[0].selected='selected';
}
</script>
<%
int iRow=0;
%>
<html:form action="/OCAPCategProfesionales.do?accion=buscar">

	<div class="ocultarCampo">

		<div class="cuadroContenedorConsultas">
			<div class="titulocajaformulario">Consulta de Categor&iacute;as</div>
			<logic:present name="errorConsultando">
				<p>
					<label><bean:write name="errorConsultando" filter="false" /></label>
				</p>
			</logic:present>
			<logic:notPresent name="errorConsultando">
				<div class="cajaformulario">
					<fieldset class="normales">

						<label for="idTGerencia" class="obligado"> Estatuto: <html:select
								property="CEstatut_id"
								styleClass="cbCuadros colocaEstatutoCategCon" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ESTATUTARIO%>"
									property="CEstatutId" labelProperty="DNombre" />
							</html:select>
						</label>

						<html:errors property="CEstatut_id" />
						<br /> <label class="obligado">Nombre:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaNombreCategCon" size="60"
							maxlength="100" />
						&nbsp;
						<html:errors property="DNombre" />
						<br /> <label class="obligado">Descripci&oacute;n:</label>
						<html:text property="DDescripcion" tabindex="18"
							styleClass="inputColor colocaDescProfesionalcon" size="60"
							maxlength="200" />
						&nbsp;
						<html:errors property="DDescripcion" />
						<br /> <label for="idTGerencia" class="obligado">
							Modalidad: <html:select property="CModalidad_id"
								styleClass="cbCuadros colocaEstatutoCategCon" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_MODALIDAD%>"
									property="CModalidadId" labelProperty="DNombre" />
							</html:select>
						</label>

						<html:errors property="CModalidad_id" />
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
								<th class="col2" id="cNombre">Nombre</th>
								<th class="col3" id="cDescri">Descripci&oacute;n</th>
								<th class="col4" id="cFechaC">Fecha creaci&oacute;n</th>
								<th class="col5" id="cFechaM">Fecha modificaci&oacute;n</th>

							</tr>
						</logic:present>
						<logic:present name="paginaOCAPCategProfesionalesOT"
							property="elementos">
							<logic:iterate id="elemento"
								name="paginaOCAPCategProfesionalesOT" property="elementos">
								<tr>
									<td class="campoIcono"><a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CProfesionalId"/>');
return false;"><img
											src="imagenes/eliminar.gif" title="Eliminar registro"
											alt="Eliminar registro"></a>&nbsp;<a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CProfesionalId"/>');return false;"></a>
									</td>
									<td class="campoIcono"><a
										href="OCAPCategProfesionales.do?accion=irEditar&cProfesionalIdS=<bean:write name="elemento" property="CProfesionalId"/>">
											<img src="imagenes/editar.gif" title="Editar registro"
											alt="Editar registro">
									</a></td>
									<td class="col1" headers="cEstat"><a
										href="OCAPCategProfesionales.do?accion=detalle&cProfesionalIdS=<bean:write name="elemento" property="CProfesionalId"/>">
											<bean:write name="elemento" property="DEstatutNombre" />
									</a></td>
									<td class="col2" headers="cNombre"><a
										href="OCAPCategProfesionales.do?accion=detalle&cProfesionalIdS=<bean:write name="elemento" property="CProfesionalId"/>">
											<bean:write name="elemento" property="DNombre" />
									</a></td>
									<td class="col3" headers="cDescri"><a
										href="OCAPCategProfesionales.do?accion=detalle&cProfesionalIdS=<bean:write name="elemento" property="CProfesionalId"/>">
											<bean:write name="elemento" property="DDescripcion" />
									</a></td>
									<td class="col4" headers="cFechaC"><a
										href="OCAPCategProfesionales.do?accion=detalle&cProfesionalIdS=<bean:write name="elemento" property="CProfesionalId"/>">
											<bean:write name="elemento" property="FCreacion"
												format="dd/MM/yyyy" />
									</a></td>
									<td class="col5" headers="cFechaM"><a
										href="OCAPCategProfesionales.do?accion=detalle&cProfesionalIdS=<bean:write name="elemento" property="CProfesionalId"/>">
											<bean:write name="elemento" property="FModificacion"
												format="dd/MM/yyyy" />
									</a></td>
								</tr>
								<tr>
									<td colspan="50">
										<hr size="1" noshade />
									</td>
								</tr>
							</logic:iterate>
							<tr>
								<td colspan="7">

									<table class="paginacionConsultas">
										<tr>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPCategProfesionalesOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Primera"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPCategProfesionalesOT" property="URLPrimeraPagina"/>"' />
												</logic:equal></td>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPCategProfesionalesOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Anterior"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPCategProfesionalesOT" property="URLPaginaAnterior"/>"' />
												</logic:equal></td>
											<td class="textoTituloGris parteCen">Registro: [<bean:write
													name="paginaOCAPCategProfesionalesOT"
													property="registroActual" />/<bean:write
													name="paginaOCAPCategProfesionalesOT" property="NRegistros" />]&nbsp;&nbsp;
												P&aacute;gina: [<bean:write
													name="paginaOCAPCategProfesionalesOT"
													property="paginaActual" />/<bean:write
													name="paginaOCAPCategProfesionalesOT" property="NPaginas" />]
											</td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPCategProfesionalesOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Siguiente"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPCategProfesionalesOT" property="URLPaginaSiguiente"/>"' />
												</logic:equal></td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPCategProfesionalesOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="&Uacute;ltima"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPCategProfesionalesOT" property="URLUltimaPagina"/>"' />
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
		<input type="hidden" name="cProfesionalIdS" value="" />

	</div>
	<!-- /Fin de ocultarCampo -->

</html:form>
