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
     
   if (document.OCAPPerfilesForm.DNombre.value != "" && !LetrasYNumeros(document.OCAPPerfilesForm.DNombre)){
         alert('Debe introducir correctamente el Nombre');
         validacion=1;   
      }
       
   if (document.OCAPPerfilesForm.DDescripcion.value != "" && !LetrasYNumeros(document.OCAPPerfilesForm.DDescripcion)){
         alert('Debe introducir correctamente la Descripci\u00f3n');
         validacion=1;   
      }
              
   if ((document.OCAPPerfilesForm.DCreacion.value != "" ) || (document.OCAPPerfilesForm.MCreacion.value != "" ) || (document.OCAPPerfilesForm.ACreacion.value != "")){
      var fecha = document.OCAPPerfilesForm.DCreacion.value + '/' + document.OCAPPerfilesForm.MCreacion.value + '/' + document.OCAPPerfilesForm.ACreacion.value;      
     
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Creaci\u00f3n');
         validacion=1;   
      }
   }
      
   if ((document.OCAPPerfilesForm.DModificacion.value != "" ) || (document.OCAPPerfilesForm.MModificacion.value != "" ) || (document.OCAPPerfilesForm.AModificacion.value != "")){
      var fecha = document.OCAPPerfilesForm.DModificacion.value + '/' + document.OCAPPerfilesForm.MModificacion.value + '/' + document.OCAPPerfilesForm.AModificacion.value; 
   
      if (!esFechaCorrecta(fecha)){
         alert('Debe introducir correctamente el campo Fecha de Modificaci\u00f3n');
         validacion=1;   
      }
   } 
      
   if (validacion==0) document.forms[0].submit();    
}   
function bajasClave(cPerfilId)
{
   if (confirm('Va a ELIMINAR el registro selecionado')){
      document.forms[0].action = 'OCAPPerfiles.do?accion=borrar';
      document.forms[0].cPerfilIdS.value = cPerfilId;

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
<html:form action="/OCAPPerfiles.do?accion=buscar">

	<div class="ocultarCampo">

		<div class="cuadroContenedorConsultas">
			<div class="titulocajaformulario">Consulta de Perfiles</div>
			<logic:present name="errorConsultando">
				<p>
					<label><bean:write name="errorConsultando" filter="false" /></label>
				</p>
			</logic:present>
			<logic:notPresent name="errorConsultando">
				<div class="cajaformulario">
					<fieldset class="normales">

						<br /> <label class="obligado">Nombre de Perfil:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaNombreConPerfil" size="60"
							maxlength="100" />
						&nbsp;
						<html:errors property="DNombre" />
						<br /> <label class="obligado">Descripci&oacute;n:</label>
						<html:text property="DDescripcion" tabindex="18"
							styleClass="inputColor colocaDescPerfilcon" size="60"
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
				<table border="0" class="tablaConsultas tabla4">
					<logic:notPresent name="sinDatos">
						<logic:present name="primeraConsulta">
							<tr>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th class="col1" id="cNombre">Nombre</th>
								<th class="col2" id="cDescri">Descripci&oacute;n</th>
								<th class="col3" id="cFechaC">Fecha creaci&oacute;n</th>
								<th class="col4" id="cFechaM">Fecha modificaci&oacute;n</th>

							</tr>
						</logic:present>
						<logic:present name="paginaOCAPPerfilesOT" property="elementos">
							<logic:iterate id="elemento" name="paginaOCAPPerfilesOT"
								property="elementos">
								<tr>
									<td class="campoIcono"><a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CPerfilId"/>');
return false;"><img
											src="imagenes/eliminar.gif" title="Eliminar registro"
											alt="Eliminar registro"></a>&nbsp;<a href="javascript:;"
										onClick="javascript:bajasClave('<bean:write name="elemento" property="CPerfilId"/>');return false;"></a>
									</td>
									<td class="campoIcono"><a
										href="OCAPPerfiles.do?accion=irEditar&cPerfilIdS=<bean:write name="elemento" property="CPerfilId"/>">
											<img src="imagenes/editar.gif" title="Editar registro"
											alt="Editar registro">
									</a></td>
									<td class="col1" headers="cNombre"><a
										href="OCAPPerfiles.do?accion=detalle&cPerfilIdS=<bean:write name="elemento" property="CPerfilId"/>">
											<bean:write name="elemento" property="DNombre" />
									</a></td>
									<td class="col2" headers="cDescri"><a
										href="OCAPPerfiles.do?accion=detalle&cPerfilIdS=<bean:write name="elemento" property="CPerfilId"/>">
											<bean:write name="elemento" property="DDescripcion" />
									</a></td>
									<td class="col3" headers="cFechaC"><a
										href="OCAPPerfiles.do?accion=detalle&cPerfilIdS=<bean:write name="elemento" property="CPerfilId"/>">
											<bean:write name="elemento" property="FCreacion"
												format="dd/MM/yyyy" />
									</a></td>
									<td class="col4" headers="cFechaM"><a
										href="OCAPPerfiles.do?accion=detalle&cPerfilIdS=<bean:write name="elemento" property="CPerfilId"/>">
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
													name="paginaOCAPPerfilesOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Primera"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPPerfilesOT" property="URLPrimeraPagina"/>"' />
												</logic:equal></td>
											<td class="parteIzq"><logic:equal
													name="paginaOCAPPerfilesOT" property="anterior"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Anterior"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPPerfilesOT" property="URLPaginaAnterior"/>"' />
												</logic:equal></td>
											<td class="textoTituloGris parteCen">Registro: [<bean:write
													name="paginaOCAPPerfilesOT" property="registroActual" />/<bean:write
													name="paginaOCAPPerfilesOT" property="NRegistros" />]&nbsp;&nbsp;
												P&aacute;gina: [<bean:write name="paginaOCAPPerfilesOT"
													property="paginaActual" />/<bean:write
													name="paginaOCAPPerfilesOT" property="NPaginas" />]
											</td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPPerfilesOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="Siguiente"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPPerfilesOT" property="URLPaginaSiguiente"/>"' />
												</logic:equal></td>
											<td class="parteDer"><logic:equal
													name="paginaOCAPPerfilesOT" property="siguiente"
													value="true">
													<input class="botonPaginacion" type="button"
														value="&Uacute;ltima"
														onClick='document.location.href="<html:rewrite page=""/><bean:write name="paginaOCAPPerfilesOT" property="URLUltimaPagina"/>"' />
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
		<input type="hidden" name="cPerfilIdS" value="" />

	</div>
	<!-- /Fin de ocultarCampo -->

</html:form>
