<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="java.util.Date"%>

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
   enviar('OCAPSolicitudes.do?accion=cargarCombosEspecialidadesPuestos&CProfesional_id='+ document.forms[0].CProfesional_id.value +'&jspVuelta=buscador&opcion=<%=request.getParameter("opcion")%>&actual=<%=Constantes.NO%>');
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
   <%if (request.getParameter("opcion").equals(Constantes.PGP_TODO)){%>   
      document.forms[0].CFase.value='';
      document.forms[0].CEstado.value='';
      inicializaEstado();
   <%}%>
   <%if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())){ %>
      document.forms[0].CGerencia_id.value='';
   <%}%>
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
      enviar('OCAPSolicitudes.do?accion=buscar&opcion=<%=request.getParameter("opcion")%>');
   }
}

function generarPDF(){
   //enviar("OCAPSolicitudes.do?accion=generarPDF&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=request.getParameter("opcion")%>");
   enviar("OCAPNuevaSolicitud.do?accion=generarPDFListado&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=request.getParameter("opcion")%>");
}

function generarCSV(){
   enviar("OCAPSolicitudes.do?accion=generarPDF&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=request.getParameter("opcion")%>&CSV=<%=Constantes.SI%>");
}

<%if (request.getParameter("opcion").equals(Constantes.PGP_TODO)){%>
function habilitaEstado() {
   document.forms[0].CEstado.value='';
   if (document.forms[0].CFase.value == '' || document.forms[0].CFase.value =='<%=Constantes.FASE_MC%>') {
      document.forms[0].CEstado.disabled=true;
   } else {
      document.forms[0].CEstado.value='';
      document.forms[0].CEstado.disabled=false;
      enviar('OCAPSolicitudes.do?accion=cargarComboEstados&jspVuelta=buscador&opcion=<%=request.getParameter("opcion")%>');
   }
}

function inicializaEstado() {
   if (document.forms[0].CFase.value == '' || document.forms[0].CFase.value =='<%=Constantes.FASE_MC%>') {
      document.forms[0].CEstado.value='';
      document.forms[0].CEstado.disabled=true;
   } else document.forms[0].CEstado.disabled=false;
}

<%}%>

</script>
<div class="contenido">
	<div class="contenidoListadoAspirantesGCP">
		<html:form action="/OCAPSolicitudes.do">
			<h2 class="tituloContenido">USUARIOS CON OPCI&Oacute;N A CARRERA
				PROFESIONAL</h2>
			<logic:equal name="opcion" value="<%=Constantes.PGP_TODO%>">
				<h3 class="subTituloContenido">Listado de Solicitudes</h3>
			</logic:equal>
			<logic:equal name="opcion" value="<%=Constantes.PGP_COMPETENCIA%>">
				<h3 class="subTituloContenido">Listado de Usuarios en fase de
					Competencia del Desempe&ntilde;o</h3>
			</logic:equal>
			<logic:equal name="opcion" value="<%=Constantes.PGP_ALEGA%>">
				<h3 class="subTituloContenido">Listado de Usuarios rechazados
					por el CEIS</h3>
			</logic:equal>
			<logic:equal name="opcion" value="<%=Constantes.FASE_VALIDACION%>">
				<h3 class="subTituloContenido">Listado de Usuarios con
					M&eacute;ritos Curriculares en fase de Validaci&oacute;n</h3>
			</logic:equal>
			<div class="lineaBajaM"></div>
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
							styleClass="cbCuadros colocaCategoriaBuscCB" size="1"
							onchange="javascript:cargarComboEspecialidades();">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
								property="CProfesionalId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br /> <label for="idVEspecialidad"> Especialidad: <html:select
							property="CEspec_id"
							styleClass="cbCuadros colocaEspecialidadBuscCB" size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
								property="CEspecId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br /> <label for="idVGrado"> Grado: <html:select
							property="CGrado_id"
							styleClass="cbCuadros colocaGradoBuscCB ajusteGradoMC" size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
								property="CGradoId" labelProperty="DDescripcion" />
						</html:select>
					</label> <label for="idVConvocatoria"> Convocatoria: <html:select
							property="CConvocatoriaId"
							styleClass="cbCuadros colocaConvocatoriaCB" size="1">
							<html:option value="">Todas</html:option>
							<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
								property="CConvocatoriaId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br />
					<logic:present name="<%=Constantes.COMBO_GERENCIA%>">
						<label for="idVGerencia"> Gerencia: <html:select
								property="CGerencia_id"
								styleClass="cbCuadros colocaGerenciaBuscCB" size="1"
								tabindex="10">
								<html:option value="">Todas</html:option>
								<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
									property="CGerenciaId" labelProperty="DNombre" />
							</html:select>
						</label>
						<br />
						<br />
					</logic:present>
					<logic:equal name="opcion" value="<%=Constantes.PGP_TODO%>">
						<label for="idVSolicitud"> Fase del proceso: <html:select
								property="CFase" styleClass="cbCuadros colocaFaseProcesoCB"
								onchange="javascript:habilitaEstado();">
								<html:option value="">Seleccione...</html:option>
								<html:optionsCollection name="<%=Constantes.COMBO_FASES%>" />
							</html:select>
						</label>
						<label for="idVSolicitud"> Estado: <html:select
								property="CEstado" styleClass="cbCuadros colocaEstadoCBMC"
								disabled="true">
								<html:optionsCollection name="<%=Constantes.COMBO_ESTADOS%>" />
							</html:select>
						</label>
						<script language="javascript" type="text/javascript">
                     inicializaEstado();
                  </script>
					</logic:equal>
					<logic:equal name="opcion" value="<%=Constantes.FASE_VALIDACION%>">
						<label for="idVSolicitud"> Estado: <html:select
								property="CEstado" styleClass="cbCuadros colocaEstadoCBMC">
								<html:optionsCollection name="<%=Constantes.COMBO_ESTADOS%>" />
							</html:select>
						</label>
					</logic:equal>
					<logic:equal name="opcion" value="<%=Constantes.PGP_ALEGA%>">
						<html:hidden name="OCAPSolicitudesForm" property="CEstado" />
					</logic:equal>
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

			<logic:equal name="OCAPSolicitudesForm" property="jspInicio"
				value="false">
				<fieldset>
					<bean:size id="tamano" name="listados" property="elementos" />
					<legend class="tituloLegend"> Listado de Solicitudes </legend>
					<div class="cuadroFieldset">
						<div class="cuadroListadoAspirantes">
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
												name="solicitud" property="DProfesional_nombre" /> <logic:notEqual
												name="solicitud" property="DEspec_nombre" value=" ">
                                    / <bean:write name="solicitud"
													property="DEspec_nombre" />
											</logic:notEqual>
										</span>
										<div class="lineaBajaM"></div>
										<table class="tablaSolicitudes">
											<tr class="cabeceraTablaSolicitudes">
												<td class="cabeceraNIF"><span>NIF/NIE</span></td>
												<td class="cabeceraApellidos"><span>Apellidos</span></td>
												<td class="cabeceraNombre"><span>Nombre</span></td>
												<td class="cabeceraFecha"><span>Fecha Registro</span></td>
												<%if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR)) { %>
												<td class="cabeceraUnidad">Unidad/Servicio</td>
												<% } %>
												
												<%if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())  || Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol()) ){%>
													<td class="cabeceraNombre"><span>Convocatoria</span></td>
												<% } %>
												<%if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol()) ){%>
													<td></td>
												<%} else if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())){%>
													<td></td>
													<logic:equal name="OCAPSolicitudesForm" property="CEstado"
														value="<%=Constantes.ESTADO_EXCLUIDO_ALEGA_VALUE%>">
														<td></td>
													</logic:equal>
												<%} else if (Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())&& Constantes.PGP_ALEGA.equals(request.getParameter("opcion"))){%>
													<td></td>
												<% } else { %>
													<td></td>
												<% } %>
												<td></td>
											</tr>
											<logic:iterate id="solicitudes" name="solicitud"
												property="listaSolicitudes" type="OCAPSolicitudesOT">
												<html:hidden name="solicitudes" property="CExp_id" />
												<tr class="cuerpoTablaSolicitudes">
													<td class="cuerpoNIF"><span><bean:write
																name="solicitudes" property="CDniReal" /></span></td>
													<td class="cuerpoApellido"><span><bean:write
																name="solicitudes" property="DApellido1" /></span></td>
													<td class="cuerpoNombre"><span><bean:write
																name="solicitudes" property="DNombre" /></span></td>
													<td class="cuerpoFecha"><span><bean:write
																name="solicitudes" property="FRegistro_solic" /></span></td>
													<%if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR)) { %>
													<td><span><bean:write name="solicitudes"
																property="APuesto" /></span></td>
													<% } %>
													
													<%if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())  || Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol()) ){%>
														<td class="cuerpoConvocatoria"><span><bean:write
																name="solicitudes" property="DConvocatoriaNombreCorto" /></span></td>
													<% } %>													
													
													<%if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol()) ){%>
													<td><a
														href="OCAPUsuarios.do?accion=irInsertar&CExp_id=<bean:write name="solicitudes" property="CExp_id"/>&opcion=<%=request.getParameter("opcion")%>">Ver
															M&eacute;ritos</a></td>
													<%} else if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())){%>
													<td><a
														href="OCAPUsuarios.do?accion=irInsertar&CExp_id=<bean:write name="solicitudes" property="CExp_id"/>&opcion=<%=request.getParameter("opcion")%>">Ver
															M&eacute;ritos</a></td>
													<logic:equal name="OCAPSolicitudesForm" property="CEstado"
														value="<%=Constantes.ESTADO_EXCLUIDO_ALEGA_VALUE%>">
														<td><a
															href="OCAPSolicitudes.do?accion=irInconformidad&CExp_id=<bean:write name="solicitudes" property="CExp_id"/>&tipoAccion=<%=Constantes.VER_DETALLE%>">Ver
																Alegaci&oacute;n</a></td>
													</logic:equal>
													<%} else if (Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())&& Constantes.PGP_ALEGA.equals(request.getParameter("opcion"))){%>
													<%if (solicitudes.getFInconf_mc() == null && solicitudes.getFFin_eval_mc() != null && solicitudes.getFFin_eval_mc().after(new Date())){%>
													<td><a
														href="OCAPSolicitudes.do?accion=irInconformidad&CExp_id=<bean:write name="solicitudes" property="CExp_id"/>&opcion=<%=request.getParameter("opcion")%>&fase=<%=Constantes.FASE_VALIDACION%>">Alegar</a>
													</td>
													<%}else if (solicitudes.getFInconf_mc() != null ){%>
													<td><a
														href="OCAPSolicitudes.do?accion=irInconformidad&CExp_id=<bean:write name="solicitudes" property="CExp_id"/>&tipoAccion=<%=Constantes.VER_DETALLE%>">Ver
															Alegaci&oacute;n</a></td>
													<%}else{%>
													<td></td>
													<%}%>
													<%} else{ %>
													<td><a
														href="OCAPSolicitudes.do?accion=irDetalle&CExp_id=<bean:write name="solicitudes" property="CExp_id"/>&opcion=<%=request.getParameter("opcion")%>">Ver</a>
													</td>
													<% } %>
													<!-- Si es administrador, puede modificar solicitudes siempre -->
													<!-- Si es PGP solo puede modificarlas mientras no este aceptada o excluida definitivamente -->
													<%if (jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR) || (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE) && solicitudes.getFAceptac_solic() == null && solicitudes.getFRespuesta_inconf_solic() == null) ) ){%>
													<logic:notEqual name="opcion"
														value="<%=Constantes.PGP_ALEGA%>">
														<td><a
															href="OCAPSolicitudes.do?accion=irModificar&CExp_id=<bean:write name="solicitudes" property="CExp_id"/>&opcion=<%=request.getParameter("opcion")%>">Editar</a></td>
													</logic:notEqual>
													<logic:equal name="opcion"
														value="<%=Constantes.PGP_ALEGA%>">
														<td></td>
													</logic:equal>
													<% } else { %>
													<td></td>
													<% } %>
												</tr>
											</logic:iterate>
										</table>
									</logic:iterate>
								</logic:iterate>
								<%@ include file="/comun/paginacion.jsp"%>
							</logic:notEqual>
							<!-- tamano != 0 -->
						</div>
					</div>
				</fieldset>
				<div class="espaciador"></div>
				<logic:notEqual name="tamano" value="0">
					<logic:equal name="OCAPSolicitudesForm" property="CFase"
						value="<%=Constantes.FASE_INICIACION%>">
						<logic:notEqual name="OCAPSolicitudesForm"
							property="CConvocatoriaId" value="">
							<logic:equal name="OCAPSolicitudesForm" property="CEstado"
								value="<%=Constantes.ESTADO_ACEPTADO_VALUE%>">
								<div class="botonesPagina">
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a href="javascript:generarPDF();"> <img
												src="imagenes/imagenes_ocap/flecha_correcto.gif"
												alt="Generar listado PDF" /> <span> Generar Listado </span>
										</a>
										</span> <span class="derBoton"></span>
									</div>
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a href="javascript:generarCSV();"> <img
												src="imagenes/imagenes_ocap/flecha_correcto.gif"
												alt="Generar listado CSV" /> <span> Generar CSV </span>
										</a>
										</span> <span class="derBoton"></span>
									</div>
								</div>
							</logic:equal>
							<logic:equal name="OCAPSolicitudesForm" property="CEstado"
								value="<%=Constantes.ESTADO_DENEGADO_VALUE%>">
								<div class="botonesPagina">
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a href="javascript:generarPDF();"> <img
												src="imagenes/imagenes_ocap/flecha_correcto.gif"
												alt="Generar listado PDF" /> <span> Generar Listado </span>
										</a>
										</span> <span class="derBoton"></span>
									</div>
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a href="javascript:generarCSV();"> <img
												src="imagenes/imagenes_ocap/flecha_correcto.gif"
												alt="Generar listado CSV" /> <span> Generar CSV </span>
										</a>
										</span> <span class="derBoton"></span>
									</div>
								</div>
							</logic:equal>
							<logic:equal name="OCAPSolicitudesForm" property="CEstado"
								value="<%=Constantes.ESTADO_DESESTIMADO_VALUE%>">
								<div class="botonesPagina">
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a href="javascript:generarPDF();"> <img
												src="imagenes/imagenes_ocap/flecha_correcto.gif"
												alt="Generar listado PDF" /> <span> Generar Listado </span>
										</a>
										</span> <span class="derBoton"></span>
									</div>
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a href="javascript:generarCSV();"> <img
												src="imagenes/imagenes_ocap/flecha_correcto.gif"
												alt="Generar listado CSV" /> <span> Generar CSV </span>
										</a>
										</span> <span class="derBoton"></span>
									</div>
								</div>
							</logic:equal>
							<logic:equal name="OCAPSolicitudesForm" property="CEstado"
								value="<%=Constantes.ESTADO_EXCLUIDO_VALUE%>">
								<div class="botonesPagina">
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a href="javascript:generarPDF();"> <img
												src="imagenes/imagenes_ocap/flecha_correcto.gif"
												alt="Generar listado PDF" /> <span> Generar Listado </span>
										</a>
										</span> <span class="derBoton"></span>
									</div>
									<div class="botonAccion">
										<span class="izqBoton"></span> <span class="cenBoton">
											<a href="javascript:generarCSV();"> <img
												src="imagenes/imagenes_ocap/flecha_correcto.gif"
												alt="Generar listado CSV" /> <span> Generar CSV </span>
										</a>
										</span> <span class="derBoton"></span>
									</div>
								</div>
							</logic:equal>
						</logic:notEqual>
					</logic:equal>
				</logic:notEqual>
				<!-- tamano != 0 -->
			</logic:equal>
		</html:form>
	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->
