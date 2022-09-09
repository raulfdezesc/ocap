<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
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
	src="<html:rewrite page='/javascript/calendario.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/fechas.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"
	charset="windows-1252"></script>

<script language="JavaScript" type="text/javascript">

function volverAtras() {
   enviar("PaginaInicio.do");
}

function continuar() {
   if (document.forms[0].CItinerarioId.value=='')
      alert('Debe seleccionar una Actividad.');
   else
      enviar("OCAPCuestionarios.do?accion=irProteccionDatos");
}

</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoECP">
		<html:form action="/OCAPCuestionarios.do">
			<html:hidden name="OCAPCuestionariosForm" property="CExpId" />
			<html:hidden name="OCAPCuestionariosForm" property="DApellido1" />
			<html:hidden name="OCAPCuestionariosForm" property="DNombre" />
			<html:hidden name="OCAPCuestionariosForm" property="CDni" />
			<html:hidden name="OCAPCuestionariosForm" property="CDniReal" />
			<html:hidden name="OCAPCuestionariosForm" property="BSexo" />
			<html:hidden name="OCAPCuestionariosForm" property="NTelefono1" />
			<html:hidden name="OCAPCuestionariosForm" property="NTelefono2" />
			<html:hidden name="OCAPCuestionariosForm"
				property="DCorreoelectronico" />
			<html:hidden name="OCAPCuestionariosForm" property="CJuridico" />
			<html:hidden name="OCAPCuestionariosForm" property="DProcedimiento" />
			<html:hidden name="OCAPCuestionariosForm"
				property="CSitAdministrativaAuxId" />
			<html:hidden name="OCAPCuestionariosForm"
				property="DSitAdministrativaAuxOtros" />
			<html:hidden name="OCAPCuestionariosForm"
				property="DProfesional_nombre" />
			<html:hidden name="OCAPCuestionariosForm" property="DEspec_nombre" />
			<html:hidden name="OCAPCuestionariosForm" property="DProvincia" />
			<html:hidden name="OCAPCuestionariosForm" property="DGerencia_nombre" />
			<html:hidden name="OCAPCuestionariosForm"
				property="DCentrotrabajo_nombre" />
			<html:hidden name="OCAPCuestionariosForm" property="DGrado_des" />
			<h2 class="tituloContenido">HISTORIAL DE CARRERA PROFESIONAL</h2>
			<%--     <a href="javascript:window.print();"><img src="imagenes/imagenes_ocap/imprimir.gif" alt="Imprimir" class="flotaIzq" /></a> --%>
			<h3 class="subTituloContenido">DCP2A</h3>
			<div class="espaciador"></div>

			<fieldset>
				<div class="cuadroFieldset">
					<p>Verifique que los siguientes datos por los que va a ser
						evaluado son correctos.</p>
					<p>
						Si existe alg&uacute;n error o tiene cualquier duda,
						<%=es.jcyl.framework.JCYLConfiguracion.getValor("TEXTO_CAU")%>&nbsp;<span
							class="textoNegrita"><%=es.jcyl.framework.JCYLConfiguracion.getValor("CORREO_CAU")%></span>
					</p>
					<p>Si los datos son correctos seleccione su actividad y pulse
						el bot&oacute;n Continuar.</p>
				</div>
			</fieldset>

			<fieldset>
				<legend class="tituloLegend"> C&oacute;digo de Evaluado </legend>
				<div class="cuadroFieldset">
					<p>Este es su c&oacute;digo de evaluado. Ap&uacute;ntelo para
						utilizarlo durante todo el proceso.</p>
					<!-- p>Lo deber&aacute; utilizar a la hora de entregar las
						evidencias en su Punto de Gesti&oacute;n Perif&eacute;rica.</p-->
					<div class="formulario">
						<div class="todo">
							<label>C&oacute;digo:&nbsp;&nbsp;</label>
							<bean:write name="OCAPCuestionariosForm" property="codigoId" />
						</div>
					</div>
				</div>
			</fieldset>

			<fieldset>
				<legend class="tituloLegend"> Datos Personales </legend>
				<div class="cuadroFieldset">
					<label for="idVApell1" class="colocaDatosVis"> Apellidos: <span><bean:write
								name="OCAPCuestionariosForm" property="DApellido1" /></span>
					</label> <label for="idVNombre" class="colocaDatosVis"> Nombre: <span><bean:write
								name="OCAPCuestionariosForm" property="DNombre" /></span>
					</label> <br />
					<br /> <label for="idVDNI" class="colocaDatosVis"> NIF/NIE:
						<span><bean:write name="OCAPCuestionariosForm"
								property="CDniReal" /></span>
					</label> <label for="idVCorreo" class="colocaDatosVis"> Sexo: <logic:equal
							name="OCAPCuestionariosForm" property="BSexo" value="H">
							<span>Hombre</span>
						</logic:equal> <logic:equal name="OCAPCuestionariosForm" property="BSexo"
							value="M">
							<span>Mujer</span>
						</logic:equal>
					</label> <br />
					<br /> <label for="idVTelefono" class="colocaDatosVis">
						Tel&eacute;fono 1: <span><bean:write
								name="OCAPCuestionariosForm" property="NTelefono1" /></span>
					</label> <label for="idVTelefono" class="colocaDatosVis">
						Tel&eacute;fono 2: <span><bean:write
								name="OCAPCuestionariosForm" property="NTelefono2" /></span>
					</label> <br />
					<br /> <label for="idVCorreo" class="colocaDatosVis">
						Correo Electr&oacute;nico: <span><bean:write
								name="OCAPCuestionariosForm" property="DCorreoelectronico" /></span>
					</label> <br />
					<br /> <label for="idVCorreo" class="colocaDatosVisGrande">
						Domicilio, Calle o Plaza y N&ordm;: <span><bean:write
								name="OCAPCuestionariosForm" property="DDomicilio" /></span>
					</label> <br />
					<br /> <label for="idVProvincia" class="colocaDatosVis">
						Provincia: <span> <logic:notEmpty
								name="OCAPCuestionariosForm" property="DProvinciaUsu">
								<bean:write name="OCAPCuestionariosForm"
									property="DProvinciaUsu" />
							</logic:notEmpty>
					</span>
					</label> <label for="idVTelefono" class="colocaDatosVis">C&oacute;digo
						Postal: <span><bean:write name="OCAPCuestionariosForm"
								property="CPostalUsu" /></span>
					</label> <br />
					<br /> <label for="idVLocalidades" class="colocaDatosVis">
						Localidad: <span> <logic:notEmpty
								name="OCAPCuestionariosForm" property="DLocalidadUsu">
								<bean:write name="OCAPCuestionariosForm"
									property="DLocalidadUsu" />
							</logic:notEmpty>
					</span>
					</label>
				</div>
			</fieldset>

			<fieldset>
				<legend class="tituloLegend"> Datos por los que opta a
					Carrera Profesional</legend>
				<div class="cuadroFieldset colocaDatosVisGrande">
					<label for="idVGrado"> Grado: <span class="textoDatos"><bean:write
								name="OCAPCuestionariosForm" property="DGrado_des" /></span>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">Grado
						que posee: <span> <logic:notEmpty
								name="OCAPCuestionariosForm" property="DModAnterior">
								<bean:write name="OCAPCuestionariosForm" property="DModAnterior" />
							</logic:notEmpty> <logic:empty name="OCAPCuestionariosForm"
								property="DModAnterior">-</logic:empty>
					</span>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">Procedimiento
						de evaluaci&oacute;n por el que opta: <span> <logic:notEmpty
								name="OCAPCuestionariosForm" property="DProcedimiento">
								<bean:write name="OCAPCuestionariosForm"
									property="DProcedimiento" />
							</logic:notEmpty> <logic:empty name="OCAPCuestionariosForm"
								property="DProcedimiento">-</logic:empty>
					</span>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">R&eacute;gimen
						jur&iacute;dico: <logic:equal name="OCAPCuestionariosForm"
							property="CJuridico"
							value="<%=Constantes.C_JURIDICO_ESTATUTARIO_COD%>">
							<span><%=Constantes.C_JURIDICO_ESTATUTARIO%></span>
						</logic:equal> <logic:equal name="OCAPCuestionariosForm" property="CJuridico"
							value="<%=Constantes.C_JURIDICO_FUNCIONARIO_COD%>">
							<span><%=Constantes.C_JURIDICO_FUNCIONARIO%></span>
						</logic:equal> <logic:equal name="OCAPCuestionariosForm" property="CJuridico"
							value="<%=Constantes.C_JURIDICO_OTROS_COD%>">
							<span><%=Constantes.C_JURIDICO_OTROS%></span>
						</logic:equal> <span> <logic:equal name="OCAPCuestionariosForm"
								property="CJuridico"
								value="<%=Constantes.C_JURIDICO_OTROS_COD%>">
								<span><bean:write name="OCAPCuestionariosForm"
										property="DRegimenJuridicoOtros" /></span>
							</logic:equal>
							<logic:equal name="OCAPCuestionariosForm"
												property="CJuridico"
												value="<%=Constantes.C_JURIDICO_INTFUNC_COD%>">
												<span><%=Constantes.C_JURIDICO_INTFUNC%></span>
											</logic:equal>
											<logic:equal name="OCAPCuestionariosForm"
												property="CJuridico"
												value="<%=Constantes.C_JURIDICO_INTEST_COD%>">
												<span><%=Constantes.C_JURIDICO_INTEST%></span>
											</logic:equal>
					</span>
					</label>

					<logic:equal name="OCAPCuestionariosForm" property="CJuridicoCombo"
						value="<%=Constantes.C_JURIDICO_SANITARIO_FIJO_COD%>">
						<br />
						<br />
						<label for="idVCategoria" class="colocaDatosVisGrande">Tipo:
							<span><%=Constantes.C_JURIDICO_SANITARIO_FIJO%></span>
						</label>
					</logic:equal>
					<logic:equal name="OCAPCuestionariosForm" property="CJuridicoCombo"
						value="<%=Constantes.C_JURIDICO_GS_FIJO_COD%>">
						<br />
						<br />
						<label for="idVCategoria" class="colocaDatosVisGrande">Tipo:
							<span><%=Constantes.C_JURIDICO_GS_FIJO%></span>
						</label>
					</logic:equal>
					<br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">Situaci&oacute;n
						Administrativa: <logic:equal name="OCAPCuestionariosForm"
							property="CSitAdministrativaAuxId"
							value="<%=Constantes.C_SIT_ADM_AUX_ACTIVO_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_ACTIVO%></span>
						</logic:equal> <logic:equal name="OCAPCuestionariosForm"
							property="CSitAdministrativaAuxId"
							value="<%=Constantes.C_SIT_ADM_AUX_SE_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_SE%></span>
						</logic:equal> <logic:equal name="OCAPCuestionariosForm"
							property="CSitAdministrativaAuxId"
							value="<%=Constantes.C_SIT_ADM_AUX_ESSP_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_ESSP%></span>
						</logic:equal> <logic:equal name="OCAPCuestionariosForm"
							property="CSitAdministrativaAuxId"
							value="<%=Constantes.C_SIT_ADM_AUX_EV_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_EV%></span>
						</logic:equal> <logic:equal name="OCAPCuestionariosForm"
							property="CSitAdministrativaAuxId"
							value="<%=Constantes.C_SIT_ADM_AUX_ECF_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_ECF%></span>
						</logic:equal> <logic:equal name="OCAPCuestionariosForm"
							property="CSitAdministrativaAuxId"
							value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>">
							<span><%=Constantes.C_SIT_ADM_AUX_OTROS%></span>
						</logic:equal> <span> <logic:equal name="OCAPCuestionariosForm"
								property="CSitAdministrativaAuxId"
								value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>">
								<span><bean:write name="OCAPCuestionariosForm"
										property="DSitAdministrativaAuxOtros" /></span>
							</logic:equal>
					</span>
					</label> <br />
					<br /> <label for="idVCategoria" class="colocaDatosVisGrande">
						Categor&iacute;a: <span> <logic:notEmpty
								name="OCAPCuestionariosForm" property="DProfesional_nombre">
								<bean:write name="OCAPCuestionariosForm"
									property="DProfesional_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPCuestionariosForm"
								property="DProfesional_nombre">-</logic:empty>
					</span>
					</label><br />
					<br /> <label for="idVEspecialidad" class="colocaDatosVisGrande">
						Especialidad: <span> <logic:notEmpty
								name="OCAPCuestionariosForm" property="DEspec_nombre">
								<bean:write name="OCAPCuestionariosForm"
									property="DEspec_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPCuestionariosForm"
								property="DEspec_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br /> <label for="idVProvincia" class="colocaDatosVis">
						Provincia: <span> <logic:notEmpty
								name="OCAPCuestionariosForm" property="DProvincia">
								<bean:write name="OCAPCuestionariosForm"
									property="DProvincia" />
							</logic:notEmpty> <logic:empty name="OCAPCuestionariosForm"
								property="DProvincia">-</logic:empty>
					</span>
					</label> <br />
					<br /> <label for="idVGerencia" class="colocaDatosVisGrande">
						Gerencia: <span> <logic:notEmpty
								name="OCAPCuestionariosForm" property="DGerencia_nombre">
								<bean:write name="OCAPCuestionariosForm"
									property="DGerencia_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPCuestionariosForm"
								property="DGerencia_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br /> <label for="idVCentroTrabajo" class="colocaDatosVisGrande">
						Centro de Trabajo: <span> <logic:notEmpty
								name="OCAPCuestionariosForm" property="DCentrotrabajo_nombre">
								<bean:write name="OCAPCuestionariosForm"
									property="DCentrotrabajo_nombre" />
							</logic:notEmpty> <logic:empty name="OCAPCuestionariosForm"
								property="DCentrotrabajo_nombre">-</logic:empty>
					</span>
					</label> <br />
					<br /> <label for="idVEspecialidad" class="colocaDatosVisGrande">
						Servicio: <span> <logic:notEmpty
								name="OCAPCuestionariosForm" property="DServicio">
								<bean:write name="OCAPCuestionariosForm" property="DServicio" />
							</logic:notEmpty> <logic:empty name="OCAPCuestionariosForm" property="DServicio">-</logic:empty>
					</span>
					</label> <br />
					<br /> <label for="idVEspecialidad" class="colocaDatosVisGrande">
						&Aacute;rea / Unidad / Puesto: <span> <logic:notEmpty
								name="OCAPCuestionariosForm" property="DPuesto">
								<bean:write name="OCAPCuestionariosForm" property="DPuesto" />
							</logic:notEmpty> <logic:empty name="OCAPCuestionariosForm" property="DPuesto">-</logic:empty>
					</span>
					</label> <br />
					<br /> <label for="idVAnt"> <span class="colocaNumAlta">A&ntilde;os
							de ejercicio en la categor&iacute;a profesional por la que se
							accede:</span> <label>N&ordm; A&ntilde;os: <span
							class="textoDatos"><bean:write
									name="OCAPCuestionariosForm" property="NAniosantiguedad" /></span></label> <label>N&ordm;
							Meses: <span class="textoDatos"><bean:write
									name="OCAPCuestionariosForm" property="NMesesantiguedad" /></span>
					</label> <label>N&ordm; D&iacute;as: <span class="textoDatos"><bean:write
									name="OCAPCuestionariosForm" property="NDiasantiguedad" /></span></label>
					</label>
				</div>
			</fieldset>

			<bean:size id="listadoItinerarios" name="OCAPCuestionariosForm"
				property="listaItinerarios" />
			<bean:define id="listaItinerarios" name="OCAPCuestionariosForm"
				property="listaItinerarios" />
			<logic:notEqual name="listadoItinerarios" value="0">
				<fieldset>
					<legend class="tituloLegend"> Actividad </legend>
					<div class="cuadroFieldset formulario">
						<div class="todo">
							<label class="nombreLineaCompleta">A fecha de
							<span class="textoDatos"><bean:write
									name="OCAPCuestionariosForm" property="NAnioConvocatoria" /></span>
									estaba realizando su actividad en:</label>
						</div>
						<div class="todo">
							<html:select property="CItinerarioId" styleClass="completo"
								size="1">
								<logic:notEqual name="listadoItinerarios" value="1">
									<html:option value="">Seleccione...</html:option>
								</logic:notEqual>
								<html:options collection="listaItinerarios"
									property="CItinerarioId" labelProperty="DDescripcion" />
							</html:select>
						</div>
					</div>
				</fieldset>
			</logic:notEqual>
			<logic:equal name="listadoItinerarios" value="0">
				<html:hidden name="OCAPCuestionariosForm" property="CItinerarioId"
					value="0" />
			</logic:equal>
			<div class="espaciador"></div>
			<div class="limpiadora"></div>
			<div class="botonesPagina">
				<%--
            <div class="botonAccion">
               <span class="izqBoton"></span>
               <span class="cenBoton">
                  <a href="javascript:volverAtras();">
                     <img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar Solicitud" />
                     <span> Cancelar </span>
                  </a>
               </span>
               <span class="derBoton"></span>
            </div>
            --%>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:continuar();"> <img
							src="imagenes/imagenes_ocap/flecha_correcto.gif"
							alt="Aceptar datos" /> <span> Continuar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>
			<div class="limpiadora"></div>
		</html:form>
	</div>
	<!-- /fin de ContenidoDCP2A -->
</div>
<!-- /Fin de Contenido -->
