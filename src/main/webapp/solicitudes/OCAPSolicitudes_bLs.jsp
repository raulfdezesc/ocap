<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="java.util.Date"%>
<%@ page import="org.apache.commons.lang.time.DateUtils"%>

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
	src="<html:rewrite page='/javascript/calendario.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"
	charset="windows-1252"></script>
<script language="JavaScript" type="text/javascript">

function cargarComboEspecialidades(){
   enviar('OCAPNuevaSolicitud.do?accion=cargarCombosEspecialidadesPuestos&CProfesional_id='+ document.forms[0].CProfesional_id.value +'&jspVuelta=buscadorB&opcion=<%=request.getParameter("opcion")%>&actual=<%=Constantes.NO%>');
}

function cargarComboConvocatorias(){
   enviar('OCAPSolicitudes.do?accion=cargarComboConvocatorias&jspVuelta=buscador&opcion=<%=request.getParameter("opcion")%>');
}

function cambiarAceptado(expediente){
   if (confirm('Se va a cambiar el estado de esta solicitud al ESTADO ANTERIOR. ¿Está usted seguro?'))
      enviar('OCAPSolicitudes.do?accion=cambiarAceptado&jspVuelta=buscador&opcion=<%=request.getParameter("opcion")%>&expediente=' + expediente);
}

function limpiar(){
   document.forms[0].DApellido1.value = '';
   document.forms[0].DNombre.value = '';
   document.forms[0].CDniReal.value = '';
   document.forms[0].CProfesional_id.value='';
   document.forms[0].CEspec_id.value='';
   document.forms[0].CGrado_id.value='';
   document.forms[0].CConvocatoriaId.value='';
   document.forms[0].CEstado.value='';
}

function validarBuscador(formu){

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
      enviar('OCAPNuevaSolicitud.do?accion=buscar');
   }
}

function generarPDF(){
   enviar("OCAPNuevaSolicitud.do?accion=generarPDF&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=request.getParameter("opcion")%>");
}

function generarCSV(){
   enviar("OCAPSolicitudes.do?accion=generarPDF&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=request.getParameter("opcion")%>&CSV=<%=Constantes.SI%>");
}

</script>

<div class="contenido">
	<div
		class="contenidoListadoAspirantesGCP solicitudesLs gestionSolicitudes">
		<html:form action="/OCAPNuevaSolicitud.do">
			<h2 class="tituloContenido">Gesti&oacute;n de Solicitudes</h2>
			<!--<h3 class="subTituloContenido"> Listado de Solicitudes </h3>-->
			<!--<div class="lineaBajaM"></div>-->
			<div class="espaciador"></div>
			<fieldset>
				<legend class="tituloLegend"> Buscador </legend>
				<div class="cuadroFieldset">
					<label for="idVApell1"> Apellidos: <html:text
							property="DApellido1" styleClass="recuadroM colocaApellidos"
							maxlength="61" />
					</label> <label for="idVNombre"> Nombre: <html:text
							property="DNombre" styleClass="recuadroM colocaNombre"
							maxlength="30" />
					</label> <br />
					<br /> <label for="idVDNI"> NIF/NIE: <html:text
							property="CDniReal" styleClass="recuadroM colocaDNI"
							maxlength="10" />
					</label> <br />
					<br /> <label for="idVCategoria"> Categor&iacute;a: <html:select
							property="CProfesional_id"
							styleClass="cbCuadros colocaCategoriaCB" size="1"
							onchange="javascript:cargarComboEspecialidades();">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
								property="CProfesionalId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br /> <label for="idVEspecialidad"> Especialidad: <html:select
							property="CEspec_id" styleClass="cbCuadros colocaEspecialidadCB"
							size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
								property="CEspecId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br /> <label for="idVGrado"> Grado: <html:select
							property="CGrado_id" styleClass="cbCuadros colocaGradoBuscCB"
							size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
								property="CGradoId" labelProperty="DDescripcion" />
						</html:select>
					</label> <label for="idVConvocatoria"> Convocatoria: <html:select
							property="CConvocatoriaId"
							styleClass="cbCuadros colocaConvocatoriaBuscCB" size="1">
							<html:option value="">Todas</html:option>
							<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
								property="CConvocatoriaId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br /> <label for="idVSolicitud"> Estado: <html:select
							property="CEstado" styleClass="cbCuadros colocaEstadoCBMC2">
							<html:option value="">Todos</html:option>
							<html:options collection="<%=Constantes.COMBO_ESTADOS%>"
								property="cEstadoId" labelProperty="DNombre" />
						</html:select>
					</label>
				</div>
				<div class="botonesPagina colocaBotonBusc">
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
			<logic:equal name="OCAPNuevaSolicitudForm" property="jspInicio"
				value="false">
				<fieldset>
					<bean:size id="tamano" name="listados" property="elementos" />
					<legend class="tituloLegend"> Listado de Solicitudes </legend>
					<div class="cuadroFieldset cuadroInferior">
						<div>
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
								<table class="tablaListado">
									<tr class="cabeceraTabla">
										<%if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){%>
										<td class="tituloAsp tituloId">ID</td>
										<% } %>
										<td class="tituloAsp tituloNIF">NIF/NIE</td>
										<td class="tituloAsp tituloApellidos">Apellidos</td>
										<td class="tituloAsp tituloNombre">Nombre</td>
										<td class="tituloAsp tituloEstado">Estado</td>
										<%if (Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())){%>
										<td class="tituloAsp tituloDConvocatoria">Convocatoria</td>
										<% } %>										
										<%if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){%>
										<td class="tituloAsp tituloFRegistro">F. Registro</td>
										<td class="tituloAsp tituloNumSolic">N&ordm; Solicitudes</td>
										<% } %>
										<td class="tituloLupa"></td>
										<td class="tituloModificar"></td>
									</tr>
									<logic:iterate id="listaTotal" name="listados"
										property="elementos" type="OCAPSolicitudesOT">
										<html:hidden name="listaTotal" property="CExp_id" />
										<tr class="cuerpoTabla">
											<%if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){%>
											<td class="id"><bean:write name="listaTotal"
													property="CUsr_id" /></td>
											<% } %>
											<td class="dni"><bean:write name="listaTotal"
													property="CDniReal" /></td>
											<td class="apellido"><bean:write name="listaTotal"
													property="DApellido1" /></td>
											<td class="nombre"><bean:write name="listaTotal"
													property="DNombre" /></td>
											<td class="estado"><bean:write name="listaTotal"
													property="CEstado" /></td>
											<%if (Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())){%>
											<td class="nombre"><bean:write name="listaTotal"
													property="DConvocatoriaNombreCorto" /></td>
											<% } %>													
											<%if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){%>
											<td class="fRegistro"><bean:write name="listaTotal"
													property="FRegistro_solic" /></td>
											<td class="numSolic"><bean:write name="listaTotal"
													property="numSolicitudes" /></td>
											<% } %>
											<td class="imagen lateralImagen">
												<%if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){%>
												<logic:equal name="OCAPNuevaSolicitudForm" property="reasociarGrs"	value="false">
													<a
												href="OCAPNuevaSolicitud.do?accion=detalleExpediente&CExp_id=<bean:write name="listaTotal" property="CExp_id"/>&opcion=<%=Constantes.ACCION_CAMBIAR%>&vuelta=<%=Constantes.VUELTA_EXP%>">Ver</a>
												</logic:equal>
												<logic:equal name="OCAPNuevaSolicitudForm" property="reasociarGrs"	value="true">
													<a
												href="OCAPNuevaSolicitud.do?accion=detalleExpediente&CExp_id=<bean:write name="listaTotal" property="CExp_id"/>&opcion=<%=Constantes.ACCION_TRAMITAR%>&vuelta=<%=Constantes.VUELTA_EXP%>">Reasociar</a>
												</logic:equal>
												<% } else { %> <a
												href="OCAPNuevaSolicitud.do?accion=detalleExpediente&CExp_id=<bean:write name="listaTotal" property="CExp_id"/>&opcion=<%=Constantes.ACCION_CAMBIAR%>&vuelta=<%=Constantes.VUELTA_EXP%>">Ver</a>
												<% } %>
											</td>
											<!-- Si es administrador, puede modificar solicitudes siempre -->
											<!-- Si es PGP solo puede modificarlas mientras no este aceptada o excluida definitivamente -->
											<!-- 2010: PGP puede modificar siempre -->
											<!-- 2010/02/25: solo modificables las que no sean de convocatoria que ya termino(q ya tiene fecha de reconocimietno de grado) -->
											<%--if (jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR) || (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE) && solicitudes.getFAceptac_solic() == null && solicitudes.getFRespuesta_inconf_solic() == null) ) ){--%>
											<%if ((Constantes.OCAP_ADMINISTRADOR.equals(jcylUsuario.getUser().getRol())) || 
											 (Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol()) && listaTotal.getFRecGrado()==null && 
											 	(listaTotal.getFechaFinPgp() == null || (listaTotal.getFechaFinPgp() != null && !DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH).after(listaTotal.getFechaFinPgp()))))){%>
											<td class="imagen"><a
												href="OCAPNuevaSolicitud.do?accion=detalleExpediente&CExp_id=<bean:write name="listaTotal" property="CExp_id"/>&modificar=<%=Constantes.MODIFICAR%>">Modificar</a></td>
											<% } else if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){ %>
												<logic:equal name="OCAPNuevaSolicitudForm" property="reasociarGrs"	value="false">
													<td class="imagen"><a
												href="OCAPNuevaSolicitud.do?accion=detalleExpediente&CExp_id=<bean:write name="listaTotal" property="CExp_id"/>&modificar=<%=Constantes.MODIFICAR%>">Modificar</a></td>
												</logic:equal>
											<% } else { %>
											<td class="imagen"></td>
											<% } %>
										</tr>
									</logic:iterate>
								</table>
								<%@ include file="/comun/paginacion.jsp"%>
							</logic:notEqual>
							<!-- tamano != 0 -->
						</div>
					</div>
				</fieldset>
			</logic:equal>
			<html:hidden name="OCAPNuevaSolicitudForm" property="reasociarGrs"/>
		</html:form>
	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->
