<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script>

function validar(tipoAccion){
   var validacion = 0;
   // Validar numéricos
    
   if (document.OCAPEspecialidadesForm.DNombre.value != "" && !LetrasYNumeros(document.OCAPEspecialidadesForm.DNombre)){
         alert('Debe introducir correctamente el Nombre');
         validacion=1;   
      }
      
   if (document.OCAPEspecialidadesForm.DDescripcion.value != "" && !LetrasYNumeros(document.OCAPEspecialidadesForm.DDescripcion)){
         alert('Debe introducir correctamente la Descripci\u00f3n');
         validacion=1;   
      }      
        
   if  (validacion ==0){
      if (tipoAccion == '<%=Constantes.INSERTAR%>') {
         enviar('OCAPEspecialidades.do?accion=insertar');    
      }else{
         enviar('OCAPEspecialidades.do?accion=grabar&cEspecIdS='+ document.OCAPEspecialidadesForm.CEspec_id.value);
      }
   }
}

function volverConsulta() {   
   enviar('OCAPEspecialidades.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
}

</script>

<div class="ocultarCampo">

	<div class="cuadroContenedor">
		<html:form action="/OCAPEspecialidades.do">
			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<div class="titulocajaformulario">Alta de Especialidades</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<div class="titulocajaformulario">Detalle de Especialidades</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
				<div class="titulocajaformulario">Editar Especialidades</div>
			</logic:equal>
			<div class="cajaformulario">
				<fieldset class="normales">
					<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<legend class="normalesLegend">Datos a insertar</legend>
					</logic:equal>
					<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
						<legend class="normalesLegend">Datos a editar</legend>
					</logic:equal>
					<hr class="hrblanco"></hr>
					<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
						<html:hidden property="CEspec_id" />&nbsp;                  
</logic:equal>

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Nombre de Profesional:</label>
						<html:text property="DProfesional_nombre" tabindex="18"
							styleClass="inputColor colocaNombreDetEspe" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idProfesional" class="obligado">* Nombre de
							Profesional: <html:select property="CProfesional_id"
								styleClass="cbCuadros colocaNombreEspe" size="1" tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_PROFESIONAL%>"
									property="CProfesionalId" labelProperty="DNombre" />
							</html:select>
						</label>
					</logic:notEqual>
					<html:errors property="CProfesional_id" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Nombre de Especialidad:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaEspecEspecDet" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Nombre de Especialidad:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaEspecEspec" size="60"
							maxlength="100" />&nbsp;                  
</logic:notEqual>
					<html:errors property="DNombre" />

					<br /> <label class="obligado"> Descripci&oacute;n:</label>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:text property="DDescripcion" tabindex="18"
							styleClass="inputColor colocaDescEspecVis" size="60"
							maxlength="200" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<html:text property="DDescripcion" tabindex="18"
							styleClass="inputColor colocaDescEspec" size="60" maxlength="200" />&nbsp;                  
</logic:notEqual>
					<html:errors property="DDescripcion" />

					<div class="espaciador"></div>

					<div class="botonera">
						<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:validar('<bean:write name="tipoAccion" />');">
										<span> Dar Alta </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</logic:equal>
						<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:validar('<bean:write name="tipoAccion" />');">
										<span> Grabar </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</logic:equal>
						<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:volverConsulta();"> <span> Volver </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</logic:notEqual>
					</div>
				</fieldset>
			</div>
			<div id="divTexto">
				<p>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligadoTexto">Los campos se&ntilde;alados
							con * son obligatorios</label>
					</logic:notEqual>

				</p>
			</div>
		</html:form>
	</div>

</div>
<!-- /Fin de ocultarCampo -->
