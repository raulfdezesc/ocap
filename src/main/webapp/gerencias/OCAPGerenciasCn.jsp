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
     
   if (document.OCAPGerenciasForm.DNombre.value != "" && !LetrasYNumeros(document.OCAPGerenciasForm.DNombre)){
         alert('Debe introducir correctamente el Nombre');
         validacion=1;   
      }

   if (document.OCAPGerenciasForm.DNombre_corto.value != "" && !LetrasYNumeros(document.OCAPGerenciasForm.DNombre_corto)){
         alert('Debe introducir correctamente el Nombre Corto');
         validacion=1;   
      }
      
   if (document.OCAPGerenciasForm.ACodldap.value != "" && !LetrasYNumeros(document.OCAPGerenciasForm.ACodldap)){
         alert('Debe introducir correctamente el C\u00f3digo Ldap');
         validacion=1;   
      } 
      
   if ((document.OCAPGerenciasForm.DCreacion.value != "" ) || (document.OCAPGerenciasForm.MCreacion.value != "" ) || (document.OCAPGerenciasForm.ACreacion.value != "")){
      var fecha = document.OCAPGerenciasForm.DCreacion.value + '/' + document.OCAPGerenciasForm.MCreacion.value + '/' + document.OCAPGerenciasForm.ACreacion.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Creaci\u00f3n');
         validacion=1;   
      }
   }
      
   if ((document.OCAPGerenciasForm.DModificacion.value != "" ) || (document.OCAPGerenciasForm.MModificacion.value != "" ) || (document.OCAPGerenciasForm.AModificacion.value != "")){
      var fecha = document.OCAPGerenciasForm.DModificacion.value + '/' + document.OCAPGerenciasForm.MModificacion.value + '/' + document.OCAPGerenciasForm.AModificacion.value; 
   
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Modificaci\u00f3n');
         validacion=1;   
      }
   } 
      
   if (validacion==0) document.forms[0].submit();    
}   
function bajasClave(cGerenciaId)
{
   if (confirm('Va a ELIMINAR el registro selecionado')){
      document.forms[0].action = 'OCAPGerencias.do?accion=borrar';
      document.forms[0].cGerenciaIdS.value = cGerenciaId;

      document.forms[0].submit(); 
   }else{
      return false;
   }
}

function limpiar() {
   document.forms[0].CProvincia_id.options[0].selected='selected';
   document.forms[0].CTipogerencia_id.options[0].selected='selected';
   document.forms[0].DNombre.value='';
   document.forms[0].DNombre_corto.value='';
   document.forms[0].DGerente.value='';
   document.forms[0].DDirector.value='';
   document.forms[0].ACodldap.value='';
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
<html:form action="/OCAPGerencias.do?accion=buscar">

	<div class="ocultarCampo">

		<div class="cuadroContenedorConsultas">
			<div class="titulocajaformulario">Consulta de Gerencias</div>
			<logic:present name="errorConsultando">
				<p>
					<label><bean:write name="errorConsultando" filter="false" /></label>
				</p>
			</logic:present>
			<logic:notPresent name="errorConsultando">
				<div class="cajaformulario">
					<fieldset class="normales">

						<label for="idVProvincia" class="obligado"> Provincia: <html:select
								property="CProvincia_id"
								styleClass="cbCuadros colocaGerenciaProvinciaCB" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_PROVINCIAS%>"
									property="CProvinciaId" labelProperty="DProvincia" />
							</html:select>
						</label>
						<html:errors property="CProvincia_id" />
						<br /> <label for="idTGerencia" class="obligado"> Tipo de
							Gerencia: <html:select property="CTipogerencia_id"
								styleClass="cbCuadros colocaGerenciaTipo" size="1" tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_TIPOS_GERENCIAS%>"
									property="CTipogerenciaId" labelProperty="DNombre" />
							</html:select>
						</label>

						<html:errors property="CTipogerencia_id" />
						<br /> <label class="obligado">Nombre de Gerencia:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaNombreGerencia" size="60"
							maxlength="200" />
						&nbsp;
						<html:errors property="DNombre" />
						<br /> <label class="obligado"> Nombre Corto:</label>
						<html:text property="DNombre_corto" tabindex="18"
							styleClass="inputColor colocaNomCortoGerencia" size="60"
							maxlength="100" />
						<html:errors property="DNombre_corto" />
						<br /> <label class="obligado"> C&oacute;digo Ldap:</label>
						<html:text property="ACodldap" tabindex="18"
							styleClass="inputColor colocaLdapGerencia" size="6" maxlength="6" />
						<html:errors property="ACodldap" />
						<br /> <label class="obligado"> Gerente:</label>
						<html:text property="DGerente" tabindex="18"
							styleClass="inputColor colocaGerenteGerencia" size="60"
							maxlength="100" />
						<html:errors property="DGerente" />
						<br /> <label class="obligado"> Director:</label>
						<html:text property="DDirector" tabindex="18"
							styleClass="inputColor colocaDirectorGerencia" size="60"
							maxlength="100" />
						<html:errors property="DDirector" />
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
				<table border="0" class="tablaConsultas tabla10">
					<logic:notPresent name="sinDatos">
						<logic:present name="primeraConsulta">
							<tr>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th class="col1" id="cProvincia">Provincia</th>
								<th class="col2" id="cTipo">Tipo</th>
								<th class="col3" id="cNombre">Nombre</th>
								<th class="col4" id="cNombreC">Nombre Corto</th>
								<th class="col5" id="cCodigoLdap">C&oacute;digo LDAP</th>
								<th class="col6" id="dGerente">Gerente</th>
								<th class="col7" id="dDirector">Director</th>
								<th class="col8" id="cFechaC">Fecha creaci&oacute;n</th>
								<th class="col9" id="cFechaM">Fecha modificaci&oacute;n</th>

							</tr>
						</logic:present>
						<logic:present name="paginaOCAPGerenciasOT" property="elementos">
							<logic:iterate id="elemento" name="paginaOCAPGerenciasOT"
								property="elementos">
								<tr>
									<td class="campoIcono"><a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CGerenciaId"/>');
return false;"><img
											src="imagenes/eliminar.gif" title="Eliminar registro"
											alt="Eliminar registro"></a>&nbsp;<a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CGerenciaId"/>');return false;"></a>
									</td>
									<td class="campoIcono"><a
										href="OCAPGerencias.do?accion=irEditar&cGerenciaIdS=<bean:write name="elemento" property="CGerenciaId"/>">
											<img src="imagenes/editar.gif" title="Editar registro"
											alt="Editar registro">
									</a></td>

									<td class="col1" headers="cProvincia"><a
										href="OCAPGerencias.do?accion=detalle&cGerenciaIdS=<bean:write name="elemento" property="CGerenciaId"/>">
											<bean:write name="elemento" property="DProvinciaNombre" />
									</a></td>
									<td class="col2" headers="cTipo"><a
										href="OCAPGerencias.do?accion=detalle&cGerenciaIdS=<bean:write name="elemento" property="CGerenciaId"/>">
											<bean:write name="elemento" property="DTipogerenciaNombre" />
									</a></td>
									<td class="col3" headers="cNombre"><a
										href="OCAPGerencias.do?accion=detalle&cGerenciaIdS=<bean:write name="elemento" property="CGerenciaId"/>">
											<bean:write name="elemento" property="DNombre" />
									</a></td>
									<td class="col4" headers="cNombreC"><a
										href="OCAPGerencias.do?accion=detalle&cGerenciaIdS=<bean:write name="elemento" property="CGerenciaId"/>">
											<bean:write name="elemento" property="DNombreCorto" />
									</a></td>
									<td class="col5" headers="cCodigoLdap"><a
										href="OCAPGerencias.do?accion=detalle&cGerenciaIdS=<bean:write name="elemento" property="CGerenciaId"/>">
											<bean:write name="elemento" property="ACodldap" />
									</a></td>
									<td class="col6" headers="dGerente"><a
										href="OCAPGerencias.do?accion=detalle&cGerenciaIdS=<bean:write name="elemento" property="CGerenciaId"/>">
											<bean:write name="elemento" property="DGerente" />
									</a></td>
									<td class="col7" headers="dDirector"><a
										href="OCAPGerencias.do?accion=detalle&cGerenciaIdS=<bean:write name="elemento" property="CGerenciaId"/>">
											<bean:write name="elemento" property="DDirector" />
									</a></td>
									<td class="col8" headers="cFechaC"><a
										href="OCAPGerencias.do?accion=detalle&cGerenciaIdS=<bean:write name="elemento" property="CGerenciaId"/>">
											<bean:write name="elemento" property="FCreacion"
												format="dd/MM/yyyy" />
									</a></td>
									<td class="col9" headers="cFechaM"><a
										href="OCAPGerencias.do?accion=detalle&cGerenciaIdS=<bean:write name="elemento" property="CGerenciaId"/>">
											<bean:write name="elemento" property="FModificacion"
												format="dd/MM/yyyy" />
									</a></td>
								</tr>

							</logic:iterate>

							<tr>
								<td colspan="9">

									<table class="paginacionConsultas">
										<tr>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPGerenciasOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Primera"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPGerenciasOT" property="URLPrimeraPagina"/>"' />
												</logic:equal></td>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPGerenciasOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Anterior"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPGerenciasOT" property="URLPaginaAnterior"/>"' />
												</logic:equal></td>
											<td class="textoTituloGris parteCen">Registro: [<bean:write
													name="paginaOCAPGerenciasOT" property="registroActual" />/<bean:write
													name="paginaOCAPGerenciasOT" property="NRegistros" />]&nbsp;&nbsp;
												P&aacute;gina: [<bean:write name="paginaOCAPGerenciasOT"
													property="paginaActual" />/<bean:write
													name="paginaOCAPGerenciasOT" property="NPaginas" />]
											</td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPGerenciasOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Siguiente"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPGerenciasOT" property="URLPaginaSiguiente"/>"' />
												</logic:equal></td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPGerenciasOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="&Uacute;ltima"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPGerenciasOT" property="URLUltimaPagina"/>"' />
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
		<input type="hidden" name="cGerenciaIdS" value="" />

	</div>
	<!-- /Fin de ocultarCampo -->

</html:form>
