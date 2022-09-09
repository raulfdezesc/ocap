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
    
   if (document.OCAPUnidadesForm.DNombre.value != "" && !LetrasYNumeros(document.OCAPUnidadesForm.DNombre)){
         alert('Debe introducir correctamente el Nombre');
         validacion=1;   
      }

   if (document.OCAPUnidadesForm.ANombre_corto.value != "" && !LetrasYNumeros(document.OCAPUnidadesForm.ANombre_corto)){
         alert('Debe introducir correctamente el Nombre Corto');
         validacion=1;   
      }
      
   if (document.OCAPUnidadesForm.DDescripcion.value != "" && !LetrasYNumeros(document.OCAPUnidadesForm.DDescripcion)){
         alert('Debe introducir correctamente la Descripci\u00f3n');
         validacion=1;   
      }

   if (document.OCAPUnidadesForm.DObservaciones.value != "" && !LetrasYNumeros(document.OCAPUnidadesForm.DObservaciones)){
         alert('Debe introducir correctamente las Observaciones');
         validacion=1;   
      }
      
   if  (validacion ==0){
      if (tipoAccion == '<%=Constantes.INSERTAR%>') {
         enviar('OCAPUnidades.do?accion=insertar');    
      }else{
         enviar('OCAPUnidades.do?accion=grabar&cUnidadIdS='+ document.OCAPUnidadesForm.CUnidad_id.value);
      }
   }
}

function volverConsulta() {   
   enviar('OCAPUnidades.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
}

</script>

<div class="ocultarCampo">

	<div class="cuadroContenedor">
		<html:form action="/OCAPUnidades.do">
			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<div class="titulocajaformulario">Alta de Unidades</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<div class="titulocajaformulario">Detalle de Unidades</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
				<div class="titulocajaformulario">Editar Unidades</div>
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
						<html:hidden property="CUnidad_id" />
					</logic:equal>

					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Area:</label>
						<html:text property="DArea_nombre" tabindex="18"
							styleClass="inputColor colocaAreaUnidad" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idVArea" class="obligado">* Area: <html:select
								property="CArea_id" styleClass="cbCuadros colocaAreaUnidad"
								size="1" tabindex="18">
								<html:option value="">Seleccione...</html:option>

							</html:select>
						</label>
					</logic:notEqual>
					<html:errors property="CArea_id" />

					<br />
					<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
							<label class="obligado"> Profesional:</label>
							<html:text property="DProfesional_nombre" tabindex="18"
								styleClass="inputColor colocaProfUnidadEdit" size="60"
								maxlength="100" readonly="true" />&nbsp;      
   </logic:equal>
						<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
							<label class="obligado"> Profesional:</label>
							<html:text property="DProfesional_nombre" tabindex="18"
								styleClass="inputColor colocaProfUnidad" size="60"
								maxlength="100" readonly="true" />&nbsp;      
   </logic:equal>
					</logic:notEqual>
					<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label for="idProfesional" class="obligado">* Profesional:
							<html:select property="CProfesional_id"
								styleClass="cbCuadros colocaProfUnidad" size="1" tabindex="18">
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
							styleClass="inputColor colocaItineUnidad" size="6" maxlength="6"
							readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idItinerario" class="obligado">* Itinerario: <html:select
								property="CItinerario_id"
								styleClass="cbCuadros colocaItineUnidad" size="1" tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ITINERARIOS%>"
									property="CItinerarioId" labelProperty="CItinerarioId" />
							</html:select>
						</label>
					</logic:notEqual>
					<html:errors property="CItinerario_id" />

					<br />
					<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
							<label class="obligado"> Nombre de Unidad:</label>
							<html:text property="DNombre" tabindex="18"
								styleClass="inputColor colocaNomUnidadEdit" size="60"
								maxlength="100" readonly="true" />&nbsp;      
   </logic:equal>
						<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
							<label class="obligado"> Nombre de Unidad:</label>
							<html:text property="DNombre" tabindex="18"
								styleClass="inputColor colocaNomUnidadDet" size="60"
								maxlength="100" readonly="true" />&nbsp;      
   </logic:equal>
					</logic:notEqual>
					<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label class="obligado">* Nombre de Unidad:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaNomUnidad" size="60" maxlength="100" />&nbsp;                  
</logic:equal>
					<html:errors property="DNombre" />

					<br />
					<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
							<label class="obligado"> Nombre Corto:</label>
							<html:text property="ANombre_corto" tabindex="18"
								styleClass="inputColor colocaNomCortoUnidadEdit" size="60"
								maxlength="100" readonly="true" />&nbsp;      
   </logic:equal>
						<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
							<label class="obligado"> Nombre Corto:</label>
							<html:text property="ANombre_corto" tabindex="18"
								styleClass="inputColor colocaNomCortoUnidad" size="60"
								maxlength="100" readonly="true" />&nbsp;      
   </logic:equal>
					</logic:notEqual>
					<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label class="obligado">* Nombre Corto:</label>
						<html:text property="ANombre_corto" tabindex="18"
							styleClass="inputColor colocaNomCortoUnidad" size="5"
							maxlength="5" />&nbsp;                  
</logic:equal>
					<html:errors property="ANombre_corto" />

					<br /> <label class="obligado"> Descripci&oacute;n:</label>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:text property="DDescripcion" tabindex="18"
							styleClass="inputColor colocaDescUnidadDet" size="60"
							maxlength="200" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<html:text property="DDescripcion" tabindex="18"
							styleClass="inputColor colocaDescUnidad" size="60"
							maxlength="200" />&nbsp;                  
</logic:notEqual>
					<html:errors property="DDescripcion" />

					<br /> <label class="obligado"> Observaciones:</label>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:text property="DObservaciones" tabindex="18"
							styleClass="inputColor colocaObsUnidadDet" size="60"
							maxlength="200" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<html:text property="DObservaciones" tabindex="18"
							styleClass="inputColor colocaObsUnidad" size="60" maxlength="200" />&nbsp;                  
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