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
    
   if (document.OCAPCentroTrabajoForm.DNombre.value != "" && !LetrasYNumeros(document.OCAPCentroTrabajoForm.DNombre)){
         alert('Debe introducir correctamente el Nombre');
         validacion=1;   
      }
      
   if (document.OCAPCentroTrabajoForm.DDescripcion.value != "" && !LetrasYNumeros(document.OCAPCentroTrabajoForm.DDescripcion)){
         alert('Debe introducir correctamente la Descripci\u00f3n');
         validacion=1;   
      }      
      
   if (document.OCAPCentroTrabajoForm.AObservaciones.value != "" && !LetrasYNumeros(document.OCAPCentroTrabajoForm.AObservaciones)){
         alert('Debe introducir correctamente las Observaciones');
         validacion=1;   
      }      
          
   if (document.OCAPCentroTrabajoForm.ADireccion.value != "" && !LetrasYNumeros(document.OCAPCentroTrabajoForm.ADireccion)){
         alert('Debe introducir correctamente la Direcci\u00f3n');
         validacion=1;   
      }      

   if (document.OCAPCentroTrabajoForm.ACodigopostal.value != "" && 
      (!soloNumeros(document.OCAPCentroTrabajoForm.ACodigopostal) || document.OCAPCentroTrabajoForm.ACodigopostal.value.length != 5)){
         alert('Debe introducir correctamente el C\u00f3digo Postal');
         validacion=1;   
      }      
 
   if (document.OCAPCentroTrabajoForm.ATelefono.value != "" && !telefonos(document.OCAPCentroTrabajoForm.ATelefono)){
         alert('Debe introducir correctamente el Tel\u00e9fono');
         validacion=1;   
      }      
                                         

   if (document.OCAPCentroTrabajoForm.ACiudad.value != "" && !LetrasYNumeros(document.OCAPCentroTrabajoForm.ACiudad)){
         alert('Debe introducir correctamente la Ciudad');
         validacion=1;   
      }         

   if  (validacion ==0){
      if (tipoAccion == '<%=Constantes.INSERTAR%>') {
         enviar('OCAPCentroTrabajo.do?accion=insertar');    
      }else{
         enviar('OCAPCentroTrabajo.do?accion=grabar&cCentrotrabajoIdS='+ document.OCAPCentroTrabajoForm.CCentrotrabajo_id.value);
      }
   }
}

function volverConsulta() {   
   enviar('OCAPCentroTrabajo.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
}

</script>

<div class="ocultarCampo">

	<div class="cuadroContenedor">
		<html:form action="/OCAPCentroTrabajo.do">
			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<div class="titulocajaformulario">Alta de Centro de Trabajo</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<div class="titulocajaformulario">Detalle de Centro de Trabajo</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
				<div class="titulocajaformulario">Editar Centro de Trabajo</div>
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
						<html:hidden property="CCentrotrabajo_id" />&nbsp;                  
</logic:equal>

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Gerencia:</label>
						<html:text property="DGerencia_nombre" tabindex="18"
							styleClass="inputColor colocaGerenciaCTCon" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idGerencia" class="obligado">* Gerencia: <html:select
								property="CGerencia_id" styleClass="cbCuadros colocaGerenciaCT"
								size="1" tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
									property="CGerenciaId" labelProperty="DNombre" />
							</html:select>
						</label>
					</logic:notEqual>
					<html:errors property="CGerencia_id" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Centro:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaCentroCTCon" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Centro:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaCentroCT" size="60" maxlength="100" />&nbsp;                  
</logic:notEqual>
					<html:errors property="DNombre" />

					<br /> <label class="obligado"> Descripci&oacute;n:</label>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:text property="DDescripcion" tabindex="18"
							styleClass="inputColor colocaDescCT" size="60" maxlength="200"
							readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<html:text property="DDescripcion" tabindex="18"
							styleClass="inputColor colocaDescCT" size="60" maxlength="200" />&nbsp;                  
</logic:notEqual>
					<html:errors property="DDescripcion" />

					<br /> <label class="obligado"> Observaciones:</label>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:text property="AObservaciones" tabindex="18"
							styleClass="inputColor colocaObservacionesCT" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<html:text property="AObservaciones" tabindex="18"
							styleClass="inputColor colocaObservacionesCT" size="60"
							maxlength="100" />&nbsp;                  
</logic:notEqual>
					<html:errors property="AObservaciones" />

					<br /> <label class="obligado"> Direcci&oacute;n:</label>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:text property="ADireccion" tabindex="18"
							styleClass="inputColor colocaDireccionCT" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<html:text property="ADireccion" tabindex="18"
							styleClass="inputColor colocaDireccionCT" size="60"
							maxlength="100" />&nbsp;                  
</logic:notEqual>
					<html:errors property="ADireccion" />

					<br /> <label class="obligado"> C&oacute;digo Postal:</label>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:text property="ACodigopostal" tabindex="18"
							styleClass="inputColor colocaCodPostalCT" size="5" maxlength="5"
							readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<html:text property="ACodigopostal" tabindex="18"
							styleClass="inputColor colocaCodPostalCT" size="5" maxlength="5" />&nbsp;                  
</logic:notEqual>
					<html:errors property="ACodigopostal" />

					<br /> <label class="obligado"> Tel&eacute;fono:</label>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:text property="ATelefono" tabindex="18"
							styleClass="inputColor colocaTelefonoCT" size="9" maxlength="9"
							readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<html:text property="ATelefono" tabindex="18"
							styleClass="inputColor colocaTelefonoCT" size="9" maxlength="9" />&nbsp;                  
</logic:notEqual>
					<html:errors property="ATelefono" />

					<br /> <label class="obligado"> Ciudad:</label>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:text property="ACiudad" tabindex="18"
							styleClass="inputColor colocaCiudadCT" size="50" maxlength="50"
							readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<html:text property="ACiudad" tabindex="18"
							styleClass="inputColor colocaCiudadCT" size="50" maxlength="50" />&nbsp;                  
</logic:notEqual>
					<html:errors property="ACiudad" />

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
