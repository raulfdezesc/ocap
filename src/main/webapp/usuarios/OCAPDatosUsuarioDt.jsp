<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<fieldset>
	<legend class="tituloLegend"> Datos Personales </legend>
	<div class="cuadroFieldset">
		<label for="idVApell1" class="colocaDatosVis"> Apellidos: <span><bean:write
					name="OCAPUsuariosForm" property="DApellido1" /></span>
		</label> <label for="idVNombre" class="colocaDatosVis"> Nombre: <span><bean:write
					name="OCAPUsuariosForm" property="DNombre" /></span>
		</label> <br />
		<br /> <label for="idVDNI" class="colocaDatosVis"> NIF/NIE: <span><bean:write
					name="OCAPUsuariosForm" property="CDniReal" /></span>
		</label> <label for="idVCorreo" class="colocaDatosVis"> Sexo: <logic:equal
				name="OCAPUsuariosForm" property="BSexo" value="H">
				<span>Hombre</span>
			</logic:equal> <logic:equal name="OCAPUsuariosForm" property="BSexo" value="M">
				<span>Mujer</span>
			</logic:equal>
		</label> <br />
		<br /> <label for="idVTelefono" class="colocaDatosVis">
			Tel&eacute;fono 1: <span><bean:write name="OCAPUsuariosForm"
					property="NTelefono1" /></span>
		</label> <label for="idVTelefono" class="colocaDatosVis">
			Tel&eacute;fono 2: <span><bean:write name="OCAPUsuariosForm"
					property="NTelefono2" /></span>
		</label> <br />
		<br /> <label for="idVCorreo" class="colocaDatosVis"> Correo
			Electr&oacute;nico: <span><bean:write name="OCAPUsuariosForm"
					property="DCorreoelectronico" /></span>
		</label> <br />
		<br /> <label for="idVCorreo" class="colocaDatosVisGrande">
			Domicilio, Calle o Plaza y N&ordm;: <span><bean:write
					name="OCAPUsuariosForm" property="ADomicilio" /></span>
		</label> <br />
		<br /> <label for="idVProvincia" class="colocaDatosVis">
			Provincia: <span> <logic:notEmpty name="OCAPUsuariosForm"
					property="DProvincia">
					<bean:write name="OCAPUsuariosForm" property="DProvincia" />
				</logic:notEmpty>
		</span>
		</label> <label for="idVTelefono" class="colocaDatosVis">C&oacute;digo
			Postal: <span><bean:write name="OCAPUsuariosForm"
					property="CPostalUsu" /></span>
		</label> <br />
		<br /> <label for="idVLocalidades" class="colocaDatosVis">
			Localidad: <span> <logic:notEmpty name="OCAPUsuariosForm"
					property="DLocalidad">
					<bean:write name="OCAPUsuariosForm" property="DLocalidad" />
				</logic:notEmpty>
		</span>
		</label>
	</div>
</fieldset>

<fieldset>
	<legend class="tituloLegend"> Datos por los que opta a Carrera
		Profesional</legend>
	<div class="cuadroFieldset colocaDatosVisGrande">

		<label for="idVConvocatoria"> Convocatoria: <span class="textoDatos"><bean:write
					name="OCAPUsuariosForm" property="DConvocatoriaNombre" /></span>
		</label> <br />	
		<br />	

		<label for="idVGrado"> Grado: <span class="textoDatos"><bean:write
					name="OCAPUsuariosForm" property="DGrado_des" /></span>
		</label> <br />
		<br /> <label for="idVCategoria" class="colocaDatosVisGrande">Grado
			que posee: <span> <logic:notEmpty name="OCAPUsuariosForm"
					property="DModAnterior">
					<bean:write name="OCAPUsuariosForm" property="DModAnterior" />
				</logic:notEmpty> <logic:empty name="OCAPUsuariosForm" property="DModAnterior">-</logic:empty>
		</span>
		</label> <br />
		<br /> <label for="idVCategoria" class="colocaDatosVisGrande">Procedimiento
			de evaluaci&oacute;n por el que opta: <span> <logic:notEmpty
					name="OCAPUsuariosForm" property="DProcedimiento">
					<bean:write name="OCAPUsuariosForm" property="DProcedimiento" />
				</logic:notEmpty> <logic:empty name="OCAPUsuariosForm" property="DProcedimiento">-</logic:empty>
		</span>
		</label> <br />
		<br /> <label for="idVCategoria" class="colocaDatosVisGrande">R&eacute;gimen
			jur&iacute;dico: <logic:equal name="OCAPUsuariosForm"
				property="CJuridico"
				value="<%=Constantes.C_JURIDICO_ESTATUTARIO_COD%>">
				<span><%=Constantes.C_JURIDICO_ESTATUTARIO%></span>
			</logic:equal> <logic:equal name="OCAPUsuariosForm" property="CJuridico"
				value="<%=Constantes.C_JURIDICO_FUNCIONARIO_COD%>">
				<span><%=Constantes.C_JURIDICO_FUNCIONARIO%></span>
			</logic:equal> <logic:equal name="OCAPUsuariosForm" property="CJuridico"
				value="<%=Constantes.C_JURIDICO_OTROS_COD%>">
				<span><%=Constantes.C_JURIDICO_OTROS%></span>
			</logic:equal> <span> <logic:equal name="OCAPUsuariosForm"
					property="CJuridico" value="<%=Constantes.C_JURIDICO_OTROS_COD%>">
					<span><bean:write name="OCAPUsuariosForm"
							property="DRegimenJuridicoOtros" /></span>
				</logic:equal>
				<logic:equal name="OCAPUsuariosForm"
												property="CJuridico"
												value="<%=Constantes.C_JURIDICO_INTFUNC_COD%>">
												<span><%=Constantes.C_JURIDICO_INTFUNC%></span>
											</logic:equal>
											<logic:equal name="OCAPUsuariosForm"
												property="CJuridico"
												value="<%=Constantes.C_JURIDICO_INTEST_COD%>">
												<span><%=Constantes.C_JURIDICO_INTEST%></span>
											</logic:equal>
		</span>
		</label>

		<logic:equal name="OCAPUsuariosForm" property="CJuridicoCombo"
			value="<%=Constantes.C_JURIDICO_SANITARIO_FIJO_COD%>">
			<br />
			<br />
			<label for="idVCategoria" class="colocaDatosVisGrande">Tipo:
				<span><%=Constantes.C_JURIDICO_SANITARIO_FIJO%></span>
			</label>
		</logic:equal>
		<logic:equal name="OCAPUsuariosForm" property="CJuridicoCombo"
			value="<%=Constantes.C_JURIDICO_GS_FIJO_COD%>">
			<br />
			<br />
			<label for="idVCategoria" class="colocaDatosVisGrande">Tipo:
				<span><%=Constantes.C_JURIDICO_GS_FIJO%></span>
			</label>
		</logic:equal>
		<br />
		<br /> <label for="idVCategoria" class="colocaDatosVisGrande">Situaci&oacute;n
			Administrativa: <logic:equal name="OCAPUsuariosForm"
				property="CSitAdministrativaAuxId"
				value="<%=Constantes.C_SIT_ADM_AUX_ACTIVO_COD%>">
				<span><%=Constantes.C_SIT_ADM_AUX_ACTIVO%></span>
			</logic:equal> <logic:equal name="OCAPUsuariosForm"
				property="CSitAdministrativaAuxId"
				value="<%=Constantes.C_SIT_ADM_AUX_SE_COD%>">
				<span><%=Constantes.C_SIT_ADM_AUX_SE%></span>
			</logic:equal> <logic:equal name="OCAPUsuariosForm"
				property="CSitAdministrativaAuxId"
				value="<%=Constantes.C_SIT_ADM_AUX_ESSP_COD%>">
				<span><%=Constantes.C_SIT_ADM_AUX_ESSP%></span>
			</logic:equal> <logic:equal name="OCAPUsuariosForm"
				property="CSitAdministrativaAuxId"
				value="<%=Constantes.C_SIT_ADM_AUX_EV_COD%>">
				<span><%=Constantes.C_SIT_ADM_AUX_EV%></span>
			</logic:equal> <logic:equal name="OCAPUsuariosForm"
				property="CSitAdministrativaAuxId"
				value="<%=Constantes.C_SIT_ADM_AUX_ECF_COD%>">
				<span><%=Constantes.C_SIT_ADM_AUX_ECF%></span>
			</logic:equal> <logic:equal name="OCAPUsuariosForm"
				property="CSitAdministrativaAuxId"
				value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>">
				<span><%=Constantes.C_SIT_ADM_AUX_OTROS%></span>
			</logic:equal> <span> <logic:equal name="OCAPUsuariosForm"
					property="CSitAdministrativaAuxId"
					value="<%=Constantes.C_SIT_ADM_AUX_OTROS_COD%>">
					<span><bean:write name="OCAPUsuariosForm"
							property="DSitAdministrativaAuxOtros" /></span>
				</logic:equal>
		</span>
		</label> <br />
		<br /> <label for="idVCategoria" class="colocaDatosVisGrande">
			Categor&iacute;a: <span> <logic:notEmpty
					name="OCAPUsuariosForm" property="DProfesional_nombre">
					<bean:write name="OCAPUsuariosForm" property="DProfesional_nombre" />
				</logic:notEmpty> <logic:empty name="OCAPUsuariosForm" property="DProfesional_nombre">-</logic:empty>
		</span>
		</label><br />
		<br /> <label for="idVEspecialidad" class="colocaDatosVisGrande">
			Especialidad: <span> <logic:notEmpty name="OCAPUsuariosForm"
					property="DEspec_nombre">
					<bean:write name="OCAPUsuariosForm" property="DEspec_nombre" />
				</logic:notEmpty> <logic:empty name="OCAPUsuariosForm" property="DEspec_nombre">-</logic:empty>
		</span>
		</label> <br />
		<br /> <label for="idVProvincia" class="colocaDatosVis">
			Provincia: <span> <logic:notEmpty name="OCAPUsuariosForm"
					property="DProvinciaCarrera">
					<bean:write name="OCAPUsuariosForm" property="DProvinciaCarrera" />
				</logic:notEmpty> <logic:empty name="OCAPUsuariosForm" property="DProvinciaCarrera">-</logic:empty>
		</span>
		</label> <br />
		<br /> <label for="idVGerencia" class="colocaDatosVisGrande">
			Gerencia: <span> <logic:notEmpty name="OCAPUsuariosForm"
					property="DGerencia_nombre">
					<bean:write name="OCAPUsuariosForm" property="DGerencia_nombre" />
				</logic:notEmpty> <logic:empty name="OCAPUsuariosForm" property="DGerencia_nombre">-</logic:empty>
		</span>
		</label> <br />
		<br /> <label for="idVCentroTrabajo" class="colocaDatosVisGrande">
			Centro de Trabajo: <span> <logic:notEmpty
					name="OCAPUsuariosForm" property="DCentrotrabajo_nombre">
					<bean:write name="OCAPUsuariosForm"
						property="DCentrotrabajo_nombre" />
				</logic:notEmpty> <logic:empty name="OCAPUsuariosForm"
					property="DCentrotrabajo_nombre">-</logic:empty>
		</span>
		</label> <br />
		<br /> <label for="idVEspecialidad" class="colocaDatosVisGrande">
			Servicio: <span> <logic:notEmpty name="OCAPUsuariosForm"
					property="DServicio" scope="session">
					<bean:write name="OCAPUsuariosForm" property="DServicio" />
				</logic:notEmpty> <logic:empty name="OCAPUsuariosForm" property="DServicio"
					scope="session">-</logic:empty>
		</span>
		</label> <br />
		<br /> <label for="idVEspecialidad" class="colocaDatosVisGrande">
			&Aacute;rea / Unidad / Puesto: <span> <logic:notEmpty
					name="OCAPUsuariosForm" property="DPuesto" scope="session">
					<bean:write name="OCAPUsuariosForm" property="DPuesto" />
				</logic:notEmpty> <logic:empty name="OCAPUsuariosForm" property="DPuesto"
					scope="session">-</logic:empty>
		</span>
		</label> <br />
		<br /> <label for="idVAnt"> <span class="colocaNumAlta">A&ntilde;os
				de ejercicio en la categor&iacute;a profesional por la que se
				accede:</span> <label>N&ordm; A&ntilde;os: <span class="textoDatos"><bean:write
						name="OCAPUsuariosForm" property="NAniosantiguedad" /></span></label> <label>N&ordm;
				Meses: <span class="textoDatos"><bean:write
						name="OCAPUsuariosForm" property="NMesesantiguedad" /></span>
		</label> <label>N&ordm; D&iacute;as: <span class="textoDatos"><bean:write
						name="OCAPUsuariosForm" property="NDiasantiguedad" /></span></label>
		</label>
	</div>
</fieldset>
