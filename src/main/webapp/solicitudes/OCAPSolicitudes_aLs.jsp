<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="org.apache.commons.lang.time.DateUtils"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

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

function asociar(solicitud){
   enviar('OCAPNuevaSolicitud.do?accion=detalleSolicitud&asociar=true&cSolicitudId='+solicitud+'&vuelta=<%=Constantes.VUELTA_SOL%>');
}

function limpiar(){
   document.forms[0].CDni.value = '';
   document.forms[0].DApellido1.value = '';
   document.forms[0].DNombre.value = '';
   document.forms[0].CGrado_id.value='';
   document.forms[0].CConvocatoriaId.value='';
}

function validarBuscador(formu){
   // NIF/NIE
   var cadena1 = formu.CDni.value;
   var cadena2 = formu.DApellido1.value;
   if(cadena1=='' && cadena2=='') {
      alert('Debe rellenar el \'NIF/NIE\' o los \'Apellidos\'.');
      return false;
   }else{
      cadena1 = trim(cadena1);
      cadena2 = trim(cadena2);
      if(cadena1=='' && cadena2=='') {
         alert('Debe rellenar el \'NIF/NIE\' o los \'Apellidos\'.');
         return false;
      }else{
         if(cadena2=='') {
            if(LetrasYNumeros(formu.CDni)){
               formu.CDni.value = cadena1;
            } else{
               alert('El campo \'NIF/NIE\' es erróneo.');
               return false;
            }
         }
         if(cadena1=='') {
            if(LetrasYNumeros(formu.DApellido1)){
               formu.DApellido1.value = cadena2;
            } else{
               alert('El campo \'Apellidos\' contiene caracteres no permitidos.');
               return false;
            }
         }
      }
   }

   return true;
}

function buscar(){
   if(validarBuscador(document.forms[0])){
      enviar('OCAPNuevaSolicitud.do?accion=buscarSolicitudesDNI');
   }
}

function alta(){
   limpiar();
   enviar('OCAPNuevaSolicitud.do?accion=irInsertar');
}

</script>

<div class="contenido">
	<div
		class="contenidoListadoAspirantesGCP solicitudesLs buscadorSolicitudes">
		<html:form action="/OCAPNuevaSolicitud.do">
			<h2 class="tituloContenido">Alta de Solicitudes</h2>
			<!--<h3 class="subTituloContenido"> Listado de Solicitudes </h3>-->
			<!--<div class="lineaBajaM"></div>-->
			<div class="espaciador"></div>
			<fieldset>
				<legend class="tituloLegend"> Buscador </legend>
				<div class="cuadroFieldset">
					<label for="idVDNI"> NIF/NIE: <html:text property="CDni"
							styleClass="recuadroM colocaDNI" maxlength="10" />
					</label> <br />
					<br /> <label for="idVApell1"> Apellidos: <html:text
							property="DApellido1" styleClass="recuadroM colocaApellidos"
							maxlength="30" />
					</label> <label for="idVNombre"> Nombre: <html:text
							property="DNombre" styleClass="recuadroM colocaNombre"
							maxlength="30" />
					</label> <br />
					<br /> <label for="idVConvocatoria"> Convocatoria: <html:select
							property="CConvocatoriaId"
							styleClass="cbCuadros colocaConvocatoria" size="1">
							<html:option value="">Todas</html:option>
							<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
								property="CConvocatoriaId" labelProperty="DNombre" />
						</html:select>
					</label> <label for="idVGrado"> Grado: <html:select
							property="CGrado_id" styleClass="cbCuadros colocaGradoBusc"
							size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
								property="CGradoId" labelProperty="DDescripcion" />
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
					<div class="cuadroFieldset listadoSolicitudes">
						<div>
							<logic:equal name="tamano" value="0">
								<table border="0" cellpadding="2" cellspacing="0" width="82%"
									align="center">
									<tr>
										<td bgcolor="#FFFFFF" colspan="3" align="center"
											class="textoNegro reducirTexto2"><bean:message
												key="documento.noSolicitudes" /></td>
									</tr>
								</table>
							</logic:equal>
							<logic:notEqual name="tamano" value="0">
								<table>
									<tr class="datosTitulo lineaBajaM">
										<td class="tituloSolicitud">Solicitud</td>
										<td class="tituloDNI">NIF/NIE</td>
										<td class="tituloNombre">Nombre</td>
										<td class="tituloApellidos">Apellidos</td>
										<td class="tituloGrado">Grado</td>
										<td class="tituloEstado">Estado</td>
										<%if (Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())){%>
										<td class="tituloEstado">Convocatoria</td>
										<% } %>		
										<td class="tituloIcono"></td>
									</tr>
									<% DecimalFormat formato = new DecimalFormat("000000"); %>
									<logic:iterate id="listaTotal" name="listados"
										property="elementos" type="OCAPSolicitudesOT">
										<tr class="datosAsp">
											<% if (listaTotal.getCEstado_id() == Constantes.ESTADO_ANULADA || listaTotal.getCEstado_id() == Constantes.ESTADO_PENDIENTE_ASOCIA) {%>
											<td><a
												href="OCAPNuevaSolicitud.do?accion=detalleSolicitud&cSolicitudId=<bean:write name="listaTotal" property="CSolicitudId"/>&opcion=<%=Constantes.VER_DETALLE%>">
													<%=formato.format(listaTotal.getCSolicitudId())%> <%--bean:write name="listaTotal" property="CSolicitudId"/--%>
											</a></td>
											<td class="barraLateral"><a
												href="OCAPNuevaSolicitud.do?accion=detalleSolicitud&cSolicitudId=<bean:write name="listaTotal" property="CSolicitudId"/>&opcion=<%=Constantes.VER_DETALLE%>">
													<bean:write name="listaTotal" property="CDni" />
											</a></td>
											<td class="barraLateral"><a
												href="OCAPNuevaSolicitud.do?accion=detalleSolicitud&cSolicitudId=<bean:write name="listaTotal" property="CSolicitudId"/>&opcion=<%=Constantes.VER_DETALLE%>">
													<bean:write name="listaTotal" property="DNombre" />
											</a></td>
											<td class="barraLateral">
												<div class="campoApellido">
													<a
														href="OCAPNuevaSolicitud.do?accion=detalleSolicitud&cSolicitudId=<bean:write name="listaTotal" property="CSolicitudId"/>&opcion=<%=Constantes.VER_DETALLE%>">
														<bean:write name="listaTotal" property="DApellido1" />
													</a>
												</div>
											</td>
											<td class="barraLateral"><a
												href="OCAPNuevaSolicitud.do?accion=detalleSolicitud&cSolicitudId=<bean:write name="listaTotal" property="CSolicitudId"/>&opcion=<%=Constantes.VER_DETALLE%>">
													<bean:write name="listaTotal" property="DGrado_des" />
											</a></td>
											<td class="barraLateral"><a
												href="OCAPNuevaSolicitud.do?accion=detalleSolicitud&cSolicitudId=<bean:write name="listaTotal" property="CSolicitudId"/>&opcion=<%=Constantes.VER_DETALLE%>">
													<bean:write name="listaTotal" property="CEstado" />
											</a></td>
											<%if (Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())){%>
											<td class="barraLateral"><a
												href="OCAPNuevaSolicitud.do?accion=detalleSolicitud&cSolicitudId=<bean:write name="listaTotal" property="CSolicitudId"/>&opcion=<%=Constantes.VER_DETALLE%>">
													<bean:write name="listaTotal" property="DConvocatoriaNombreCorto" />
											</a></td>											
											<% } %>
											<%}else{%>
											<td><a
												href="OCAPNuevaSolicitud.do?accion=detalleExpediente&CExp_id=<bean:write name="listaTotal" property="CExp_id"/>&opcion=<%=Constantes.ACCION_CAMBIAR%>&vuelta=<%=Constantes.VUELTA_SOL%>">
													<%=formato.format(listaTotal.getCSolicitudId())%> <%--bean:write name="listaTotal" property="CSolicitudId"/--%>
											</a></td>
											<td class="barraLateral"><a
												href="OCAPNuevaSolicitud.do?accion=detalleExpediente&CExp_id=<bean:write name="listaTotal" property="CExp_id"/>&opcion=<%=Constantes.ACCION_CAMBIAR%>&vuelta=<%=Constantes.VUELTA_SOL%>">
													<bean:write name="listaTotal" property="CDni" />
											</a></td>
											<td class="barraLateral"><a
												href="OCAPNuevaSolicitud.do?accion=detalleExpediente&CExp_id=<bean:write name="listaTotal" property="CExp_id"/>&opcion=<%=Constantes.ACCION_CAMBIAR%>&vuelta=<%=Constantes.VUELTA_SOL%>">
													<bean:write name="listaTotal" property="DNombre" />
											</a></td>
											<td class="barraLateral"><a
												href="OCAPNuevaSolicitud.do?accion=detalleExpediente&CExp_id=<bean:write name="listaTotal" property="CExp_id"/>&opcion=<%=Constantes.ACCION_CAMBIAR%>&vuelta=<%=Constantes.VUELTA_SOL%>">
													<bean:write name="listaTotal" property="DApellido1" />
											</a></td>
											<td class="barraLateral"><a
												href="OCAPNuevaSolicitud.do?accion=detalleExpediente&CExp_id=<bean:write name="listaTotal" property="CExp_id"/>&opcion=<%=Constantes.ACCION_CAMBIAR%>&vuelta=<%=Constantes.VUELTA_SOL%>">
													<bean:write name="listaTotal" property="DGrado_des" />
											</a></td>
											<td class="barraLateral"><a
												href="OCAPNuevaSolicitud.do?accion=detalleExpediente&CExp_id=<bean:write name="listaTotal" property="CExp_id"/>&opcion=<%=Constantes.ACCION_CAMBIAR%>&vuelta=<%=Constantes.VUELTA_SOL%>">
													<bean:write name="listaTotal" property="CEstado" />
											</a></td>
											<%if (Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())){%>
											<td class="barraLateral"><a
												href="OCAPNuevaSolicitud.do?accion=detalleExpediente&CExp_id=<bean:write name="listaTotal" property="CExp_id"/>&opcion=<%=Constantes.ACCION_CAMBIAR%>&vuelta=<%=Constantes.VUELTA_SOL%>">
													<bean:write name="listaTotal" property="DConvocatoriaNombreCorto" />
											</a></td>	
											<% } %>										
											<%}%>
											<% if (listaTotal.getCUsr_id() == 0  &&
													(listaTotal.getFechaFinPgp() == null || (listaTotal.getFechaFinPgp() != null && !DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH).after(listaTotal.getFechaFinPgp())))) {%>
											<td><a
												href="javascript:asociar(<bean:write name="listaTotal" property="CSolicitudId"/>);"><img
													src="imagenes/imagenes_ocap/icono_registrar.gif"
													alt="Asociar" title="Asociar solicitante" /></a></td>
											<%}%>
										</tr>
									</logic:iterate>
								</table>
								<%@ include file="/comun/paginacion.jsp"%>
							</logic:notEqual>
							<!-- tamano != 0 -->
						</div>
					</div>
					<br />
					<div class="botonesPagina colocaBotonBusc">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:alta();" tabindex="61"> <img
									src="imagenes/imagenes_ocap/dobleFlecha.gif"
									class="colocaImgPrint" alt="Alta" /> <span> Alta de
										solicitud </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</div>
				</fieldset>
			</logic:equal>
		</html:form>
	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->
