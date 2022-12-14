<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
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
               
   if (document.OCAPTipoGerenciasForm.DNombre.value != "" && !LetrasYNumeros(document.OCAPTipoGerenciasForm.DNombre)){
         alert('Debe introducir correctamente el Nombre');
         validacion=1;   
      }
      
   if (document.OCAPTipoGerenciasForm.DDescripcion.value != "" && !LetrasYNumeros(document.OCAPTipoGerenciasForm.DDescripcion)){
         alert('Debe introducir correctamente la Descripci\u00f3n');
         validacion=1;   
      }
      
    if ((document.OCAPTipoGerenciasForm.DCreacion.value != "" ) || (document.OCAPTipoGerenciasForm.MCreacion.value != "" ) || (document.OCAPTipoGerenciasForm.ACreacion.value != "")){
      var fecha = document.OCAPTipoGerenciasForm.DCreacion.value + '/' + document.OCAPTipoGerenciasForm.MCreacion.value + '/' + document.OCAPTipoGerenciasForm.ACreacion.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Creaci\u00f3n');
         validacion=1;   
      }
   }
      
   if ((document.OCAPTipoGerenciasForm.DModificacion.value != "" ) || (document.OCAPTipoGerenciasForm.MModificacion.value != "" ) || (document.OCAPTipoGerenciasForm.AModificacion.value != "")){
      var fecha = document.OCAPTipoGerenciasForm.DModificacion.value + '/' + document.OCAPTipoGerenciasForm.MModificacion.value + '/' + document.OCAPTipoGerenciasForm.AModificacion.value; 
   
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Modificaci\u00f3n');
         validacion=1;   
      }
   }
          
   if (validacion==0) document.forms[0].submit();    
   }   
      function bajasClave(cTipogerenciaId)
   {
      if (confirm('Va a ELIMINAR el registro selecionado')){
         document.forms[0].action = 'OCAPTipoGerencias.do?accion=borrar';
         document.forms[0].cTipogerenciaIdS.value = cTipogerenciaId;

         document.forms[0].submit(); 
      }else{
         return false;
      }
   }

   function limpiar() {
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
<html:form action="/OCAPTipoGerencias.do?accion=buscar">

	<div class="ocultarCampo">

		<div class="cuadroContenedorConsultas">
			<div class="titulocajaformulario">Consulta de Tipo de Gerencia</div>
			<logic:present name="errorConsultando">
				<p>
					<label><bean:write name="errorConsultando" filter="false" /></label>
				</p>
			</logic:present>
			<logic:notPresent name="errorConsultando">
				<div class="cajaformulario">
					<fieldset class="normales">

						<label class="obligado">Nombre:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaNombreTipoGCon" size="60"
							maxlength="100" />
						&nbsp;
						<html:errors property="DNombre" />
						<br /> <label class="obligado">Descripci&oacute;n:</label>
						<html:text property="DDescripcion" tabindex="18"
							styleClass="inputColor" size="60" maxlength="200" />
						&nbsp;
						<html:errors property="DDescripcion" />
						<br /> <label class="obligado">Fecha de creaci&oacute;n
							(dd/mm/aaaa):</label>
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
							modificaci&oacute;n (dd/mm/aaaa):</label>
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
				<table border="0" class="tablaConsultas tabla4">
					<logic:notPresent name="sinDatos">
						<logic:present name="primeraConsulta">
							<tr>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th class="col1" id="cNombre">Nombre&nbsp;</th>
								<th class="col2" id="cDescri">Descripci&oacute;n&nbsp;</th>
								<th class="col3" id="CFechaC">Fecha creaci&oacute;n&nbsp;</th>
								<th class="col4" id="cFechaM">Fecha
									modificaci&oacute;n&nbsp;</th>

							</tr>
						</logic:present>
						<logic:present name="paginaOCAPTipoGerenciasOT"
							property="elementos">
							<logic:iterate id="elemento" name="paginaOCAPTipoGerenciasOT"
								property="elementos">
								<tr>
									<td class="campoIcono"><a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CTipogerenciaId"/>');
return false;"><img
											src="imagenes/eliminar.gif" title="Eliminar registro"
											alt="Eliminar registro"></a>&nbsp;<a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CTipogerenciaId"/>');return false;"></a>
									</td>
									<td class="campoIcono"><a
										href="OCAPTipoGerencias.do?accion=irEditar&cTipogerenciaIdS=<bean:write name="elemento" property="CTipogerenciaId"/>">
											<img src="imagenes/editar.gif" title="Editar registro"
											alt="Editar registro">
									</a></td>
									<td class="col1" headers="cNombre"><a
										href="OCAPTipoGerencias.do?accion=detalle&cTipogerenciaIdS=<bean:write name="elemento" property="CTipogerenciaId"/>"
										style="text-decoration: none, color:blue"> <bean:write
												name="elemento" property="DNombre" />
									</a></td>
									<td class="col2" headers="cDescri"><a
										href="OCAPTipoGerencias.do?accion=detalle&cTipogerenciaIdS=<bean:write name="elemento" property="CTipogerenciaId"/>"
										style="text-decoration: none, color:blue"> <bean:write
												name="elemento" property="DDescripcion" />
									</a></td>
									<td class="col3" headers="cFechaC"><a
										href="OCAPTipoGerencias.do?accion=detalle&cTipogerenciaIdS=<bean:write name="elemento" property="CTipogerenciaId"/>"
										style="text-decoration: none, color:blue"> <bean:write
												name="elemento" property="FCreacion" format="dd/MM/yyyy" />
									</a></td>
									<td class="col4" headers="cFechaM"><a
										href="OCAPTipoGerencias.do?accion=detalle&cTipogerenciaIdS=<bean:write name="elemento" property="CTipogerenciaId"/>"
										style="text-decoration: none, color:blue"> <bean:write
												name="elemento" property="FModificacion" format="dd/MM/yyyy" />
									</a></td>

								</tr>

							</logic:iterate>

							<tr>
								<td colspan="6">

									<table class="paginacionConsultas">
										<tr>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPTipoGerenciasOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Primera"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPTipoGerenciasOT" property="URLPrimeraPagina"/>"' />
												</logic:equal></td>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPTipoGerenciasOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Anterior"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPTipoGerenciasOT" property="URLPaginaAnterior"/>"' />
												</logic:equal></td>
											<td class="textoTituloGris parteCen">Registro: [<bean:write
													name="paginaOCAPTipoGerenciasOT" property="registroActual" />/<bean:write
													name="paginaOCAPTipoGerenciasOT" property="NRegistros" />]&nbsp;&nbsp;
												P&aacute;gina: [<bean:write name="paginaOCAPTipoGerenciasOT"
													property="paginaActual" />/<bean:write
													name="paginaOCAPTipoGerenciasOT" property="NPaginas" />]
											</td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPTipoGerenciasOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Siguiente"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPTipoGerenciasOT" property="URLPaginaSiguiente"/>"' />
												</logic:equal></td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPTipoGerenciasOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="&Uacute;ltima"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPTipoGerenciasOT" property="URLUltimaPagina"/>"' />
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
		<input type="hidden" name="cTipogerenciaIdS" value="" />

	</div>
	<!-- /Fin de ocultarCampo -->

</html:form>
