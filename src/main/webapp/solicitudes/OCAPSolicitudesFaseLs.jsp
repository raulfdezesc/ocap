<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"
	charset="windows-1252"></script>

<script language="JavaScript" type="text/javascript">
function cargarComboConvocatorias(){
   enviar('OCAPSolicitudes.do?accion=cargarComboConvocatorias&jspVuelta=listarFase&fase=<%=request.getParameter("fase")%>');
}

function limpiar(){
   <% if(Constantes.OCAP_DIRECCION.equals(jcylUsuario.getUser().getRol())){ %>
      document.forms[0].codigoId.value = '';
   <%}%>
   document.forms[0].DApellido1.value = '';
   document.forms[0].DNombre.value = '';
   document.forms[0].CDniReal.value = '';
   document.forms[0].CGrado_id.value='';
   document.forms[0].CConvocatoriaId.value='';
}

function validarBuscador(formu){
<% if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION))){ %>
   //CODIGO
   var cadena = formu.codigoId.value;
   cadena = trim(cadena);
   if(cadena!='') {
      if(LetrasYNumeros(formu.codigoId)){
         if (formu.codigoId.value.length < 9) {
            alert('El C\u00F3digo debe tener el formato <%=Constantes.CODIGO_EVALUADO%>xxxxxx donde xxxxxx son 6 d\u00EDgitos.');
            return false;
         } else if (formu.codigoId.value.toUpperCase().indexOf('<%=Constantes.CODIGO_EVALUADO%>') != 0) {
            alert('El C\u00F3digo debe tener el formato <%=Constantes.CODIGO_EVALUADO%>xxxxxx donde xxxxxx son 6 d\u00EDgitos.');
            return false;
         } else {
            formu.codigoId.value = formu.codigoId.value.substring('<%=Constantes.CODIGO_EVALUADO%>'.length);
            if (!soloNumeros(formu.codigoId)) {
               formu.codigoId.value = cadena;
               alert('El C\u00F3digo debe tener el formato <%=Constantes.CODIGO_EVALUADO%>xxxxxx donde xxxxxx son 6 d\u00EDgitos.');
               return false;
            } else
               formu.codigoId.value = cadena;
         }
      } else{
         alert('El campo \'C\u00F3digo\' contiene caracteres no permitidos.');
         return false;
      }
   }
<% } %>
   // PRIMER APELLIDO
   var cadena = formu.DApellido1.value;
   if(cadena!='') {
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'Apellidos\'.');
         return false;
      }else{
         if(LetrasYNumeros(formu.DApellido1)){
            formu.DApellido1.value = cadena;
         } else{
            alert('El campo \'Apellidos\' contiene caracteres no permitidos.');
            return false;
         }
      }
   }

   // NOMBRE
   var cadena = formu.DNombre.value;
   if(cadena!='') {
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'Nombre\'.');
         return false;
      }else{
         if(LetrasYNumeros(formu.DNombre)){
            formu.DNombre.value = cadena;
         } else{
            alert('El campo \'Nombre\' contiene caracteres no permitidos.');
            return false;
         }
      }
   }

   // NIF/NIE
   var cadena = formu.CDniReal.value;
   if(cadena!='') {
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'NIF/NIE\'.');
         return false;
      }else{
         if(LetrasYNumeros(formu.CDniReal)){
            formu.CDniReal.value = cadena;
         } else{
            alert('El campo \'NIF/NIE\' contiene caracteres no permitidos.');
            return false;
         }
      }
   }

   return true;
}

function buscar(){
   if(validarBuscador(document.forms[0])){
      enviar('OCAPSolicitudes.do?accion=listarFase&fase=<%=request.getParameter("fase")%>');
   }
}
</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoListadoAspirantesGCP">
		<html:form action="/OCAPSolicitudes.do">
			<h2 class="tituloContenido">USUARIOS CON OPCI&Oacute;N A CARRERA
				PROFESIONAL</h2>
			<h3 class="subTituloContenido">Listado Solicitudes Fase III</h3>
			<div class="lineaBajaM"></div>
			<fieldset>
				<legend class="tituloLegend"> Buscador </legend>
				<div class="cuadroFieldset formulario">
					<% if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION))){ %>
					<div class="todo">
						<label class="buscador" for="idVApell1"> C&oacute;digo:</label>
						<html:text property="codigoId" maxlength="9" />
					</div>
					<% } %>
					<div class="unMedio">
						<label class="buscador" for="idVApell1"> Apellidos:</label>
						<html:text property="DApellido1" maxlength="30" />
					</div>
					<div class="unMedio">
						<label for="idVNombre"> Nombre:</label>
						<html:text property="DNombre" maxlength="30" />
					</div>
					<div class="unMedio">
						<label class="buscador" for="idVDNI"> NIF/NIE:</label>
						<html:text property="CDniReal" maxlength="10" />
					</div>
					<div class="unMedio">
						<label for="idVGrado"> Grado:</label>
						<html:select property="CGrado_id" size="1" styleClass="buscador">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
								property="CGradoId" labelProperty="DDescripcion" />
						</html:select>
					</div>
					<div class="dosTercios">
						<label class="buscadorCon" for="idVConvocatoria">
							Convocatoria:</label>
						<html:select property="CConvocatoriaId" size="1">
							<html:option value="">Todas</html:option>
							<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
								property="CConvocatoriaId" labelProperty="DNombre" />
						</html:select>
					</div>
				</div>
				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:buscar();" tabindex="61"> <img
								src="imagenes/imagenes_ocap/dobleFlecha.gif"
								class="colocaImgPrint" alt="Buscar" /> <span> Buscar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:limpiar();" tabindex="61"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif"
								class="colocaImgPrint" alt="Limpiar" /> <span> Limpiar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</fieldset>
			<logic:present name="listados" property="elementos">
				<fieldset>
					<bean:size id="tamano" name="listados" property="elementos" />
					<div class="cuadroFieldset">
						<%--div class="cuadroListadoAspirantesFase"--%>
						<div class="cuadroListadoResultadosFormulario">
							<logic:equal name="tamano" value="0">
								<table border="0" cellpadding="2" cellspacing="0" width="82%"
									align="center">
									<tr>
										<td bgcolor="#FFFFFF" colspan="3" align="center"
											class="textoNegro"><bean:message
												key="documento.noSolicitudes" /></td>
									</tr>
								</table>
							</logic:equal>
							<logic:notEqual name="tamano" value="0">
								<logic:iterate id="listaTotal" name="listados"
									property="elementos" indexId="index">
									<bean:define id="profesiones" name="listaTotal"
										type="OCAPSolicitudesOT" />
									<div class="tituloGrado">
										<bean:write name="profesiones" property="DGrado_des" />
									</div>
									<logic:iterate id="listaProfeEspec" name="profesiones"
										property="listaSolicitudes" type="OCAPSolicitudesOT">
										<bean:define id="solicitud" name="listaProfeEspec"
											type="OCAPSolicitudesOT" />
										<span class="subTituloListado"> <bean:write
												name="solicitud" property="DProfesional_nombre" /> / <bean:write
												name="solicitud" property="DEspec_nombre" />
										</span>
										<div class="lineaBajaM"></div>
										<table class="resultadosFaseIII listadoNuevaEvaluacion">
											<tr>
												<% if(Constantes.OCAP_DIRECCION.equals(jcylUsuario.getUser().getRol())){ %>
												<th class="tituloCodigo">C&oacute;digo</th>
												<% }%>
												<th class="tituloNIF">NIF/NIE</th>
												<th class="tituloApellido">Apellidos</th>
												<th class="tituloNombre">Nombre</th>
												<th class="tituloIcono"></th>
											</tr>
											<logic:iterate id="solicitudes" name="solicitud"
												property="listaSolicitudes" type="OCAPSolicitudesOT">
												<html:hidden name="solicitudes" property="CExp_id" />
												<%if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE)){%>
												<tr>
													<td><bean:write name="solicitudes" property="CDniReal" /></td>
													<td><bean:write name="solicitudes"
															property="DApellido1" /></td>
													<td><bean:write name="solicitudes" property="DNombre" /></td>
													<td><a
														href="OCAPDocumentos.do?accion=irAltaDocumento&tarea=actualizar&tareaDocu=alta&cExpId=<bean:write name="solicitudes" property="CExp_id"/>">
															<img src="imagenes/imagenes_ocap/copy.gif"
															alt="Insertar documento escaneado" />
													</a></td>
												</tr>
												<% } else { %>
												<%-- <%if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_FQS_GEST)){%> --%>
												<tr>
													<% if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION))){ %>
													<td><bean:write name="solicitudes" property="codigoId" /></td>
													<% }%>
													<td><bean:write name="solicitudes" property="CDniReal" /></td>
													<td><bean:write name="solicitudes"
															property="DApellido1" /></td>
													<td><bean:write name="solicitudes" property="DNombre" /></td>
													<td class="campoIcono"><a
														href="OCAPNuevaSolicitud.do?accion=irDetalleFqs&CExp_id=<bean:write name="solicitudes" property="CExp_id"/>&fase=<%=request.getParameter("fase")%>">
															<img class="imagenDcha"
															src="imagenes/imagenes_ocap/lupa.gif" alt="Ver detalle" />
													</a></td>
												</tr>
												<% } %>
											</logic:iterate>
										</table>
									</logic:iterate>
								</logic:iterate>
								<%@ include file="/comun/paginacion.jsp"%>
							</logic:notEqual>
						</div>
					</div>
				</fieldset>
			</logic:present>
			<div class="espaciador"></div>
		</html:form>
	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->
