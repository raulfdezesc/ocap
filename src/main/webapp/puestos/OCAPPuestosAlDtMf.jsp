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
    
   if (document.OCAPPuestosForm.DNombre.value != "" && !LetrasYNumeros(document.OCAPPuestosForm.DNombre)){
         alert('Debe introducir correctamente el Nombre');
         validacion=1;   
      }

   if (document.OCAPPuestosForm.ANombre_corto.value != "" && !LetrasYNumeros(document.OCAPPuestosForm.ANombre_corto)){
         alert('Debe introducir correctamente el Nombre Corto');
         validacion=1;   
      }
      
   if (document.OCAPPuestosForm.DObservaciones.value != "" && !LetrasYNumeros(document.OCAPPuestosForm.DObservaciones)){
         alert('Debe introducir correctamente las observaciones');
         validacion=1;   
      }      
        
   if  (validacion ==0){
         if (tipoAccion == '<%=Constantes.INSERTAR%>') {
         enviar('OCAPPuestos.do?accion=insertar');    
      }else{
         enviar('OCAPPuestos.do?accion=grabar&cEspecIdS='+ document.OCAPPuestosForm.CPuesto_id.value);
      }
   }
}

function volverConsulta() {   
   enviar('OCAPPuestos.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
}

</script>

<div class="ocultarCampo">

	<div class="cuadroContenedor">
		<html:form action="/OCAPPuestos.do">
			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<div class="titulocajaformulario">Alta de Puestos</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<div class="titulocajaformulario">Detalle de Puestos</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
				<div class="titulocajaformulario">Editar Puestos</div>
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
						<html:hidden property="CPuesto_id" />&nbsp;                  
</logic:equal>

					<br />
					<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label class="obligado"> Nombre de Profesional:</label>
						<html:text property="DProfesional_nombre" tabindex="18"
							styleClass="inputColor colocaProfDetPuestos" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:notEqual>
					<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label for="idProfesional" class="obligado">* Nombre de
							Profesional: <html:select property="CProfesional_id"
								styleClass="cbCuadros colocaProfPuestos" size="1" tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_PROFESIONAL%>"
									property="CProfesionalId" labelProperty="DNombre" />
							</html:select>
						</label>
					</logic:equal>
					<html:errors property="CProfesional_id" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Itinerario:</label>
						<html:text property="CItinerario_id" tabindex="18"
							styleClass="inputColor colocaItinerarioPuestosDet" size="6"
							maxlength="6" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
							<label class="obligado"> * Itinerario:</label>
							<html:select property="CItinerario_id"
								styleClass="cbCuadros colocaItinerarioPuestosEdit" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ITINERARIOS%>"
									property="CItinerarioId" labelProperty="CItinerarioId" />
							</html:select>
						</logic:equal>
						<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
							<label class="obligado"> * Itinerario:</label>
							<html:select property="CItinerario_id"
								styleClass="cbCuadros colocaItinerarioPuestos" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ITINERARIOS%>"
									property="CItinerarioId" labelProperty="CItinerarioId" />
							</html:select>
						</logic:equal>
					</logic:notEqual>
					<html:errors property="CItinerario_id" />

					<br />
					<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label class="obligado"> Nombre de Puesto:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaPuestoPuestos" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:notEqual>
					<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label class="obligado">* Nombre de Puesto:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaPuestoPuestos" size="60"
							maxlength="100" />&nbsp;                  
</logic:equal>
					<html:errors property="DNombre" />
					<br />
					<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label class="obligado"> Nombre Corto:</label>
						<html:text property="ANombre_corto" tabindex="18"
							styleClass="inputColor colocaNombreCPuestos" size="5"
							maxlength="5" readonly="true" />&nbsp;                  
</logic:notEqual>
					<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label class="obligado">* Nombre Corto:</label>
						<html:text property="ANombre_corto" tabindex="18"
							styleClass="inputColor colocaNombreCPuestos" size="5"
							maxlength="5" />&nbsp;                  
</logic:equal>
					<html:errors property="ANombre_corto" />

					<br /> <label class="obligado"> Observaciones:</label>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:text property="DObservaciones" tabindex="18"
							styleClass="inputColor colocaObsPuestosDet" size="60"
							maxlength="200" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
							<html:text property="DObservaciones" tabindex="18"
								styleClass="inputColor colocaObsPuestosEdit" size="60"
								maxlength="200" />&nbsp; 
   </logic:equal>
						<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
							<html:text property="DObservaciones" tabindex="18"
								styleClass="inputColor colocaObsPuestos" size="60"
								maxlength="200" />&nbsp; 
   </logic:equal>

					</logic:notEqual>
					<html:errors property="DObservaciones" />

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