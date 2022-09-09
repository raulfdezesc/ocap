<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script>

function cargarComboEspecialidades(){
   enviar('OCAPServicios.do?accion=cargarComboEspecialidades&CProfesional_id='+ document.forms[0].CProfesional_id.value);
}

function validar(tipoAccion){
   var validacion = 0;
   // Validar numéricos

   if (document.OCAPServiciosForm.DNombre_corto.value != "" && !LetrasYNumeros(document.OCAPServiciosForm.DNombre_corto)){
         alert('Debe introducir correctamente el Nombre corto');
         validacion=1;   
      }

   if (document.OCAPServiciosForm.DNombre_largo.value != "" && !LetrasYNumeros(document.OCAPServiciosForm.DNombre_largo)){
         alert('Debe introducir correctamente el Nombre largo');
         validacion=1;   
      }

   if (document.OCAPServiciosForm.DObservaciones.value != "" && !LetrasYNumeros(document.OCAPServiciosForm.DObservaciones)){
         alert('Debe introducir correctamente las Observaciones');
         validacion=1;   
      }
      
   if  (validacion ==0){
      if (tipoAccion == '<%=Constantes.INSERTAR%>') {
         enviar('OCAPServicios.do?accion=insertar');    
      }else{
         enviar('OCAPServicios.do?accion=grabar');
      }
   }
}

function volverConsulta() {   
   enviar('OCAPServicios.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
}

</script>

<div class="ocultarCampo">

	<div class="cuadroContenedor">
		<html:form action="/OCAPServicios.do">
			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<div class="titulocajaformulario">Alta de Servicios</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<div class="titulocajaformulario">Detalle de Servicios</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
				<div class="titulocajaformulario">Editar de Servicios</div>
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

					<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<html:hidden property="CServicio_id" />&nbsp;                  
</logic:notEqual>

					<br />
					<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label class="obligado"> Profesional:</label>
						<html:text property="DProfesional_nombre" tabindex="18"
							styleClass="inputColor colocaProfServiciosDet" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:notEqual>
					<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label for="idProfesional" class="obligado">* Profesional:
							<html:select property="CProfesional_id"
								styleClass="cbCuadros colocaProfServicios" size="1"
								tabindex="18" onchange="javascript:cargarComboEspecialidades();">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_PROFESIONAL%>"
									property="CProfesionalId" labelProperty="DNombre" />
							</html:select>
						</label>
					</logic:equal>
					<html:errors property="CProfesional_id" />

					<br />
					<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label class="obligado"> Especialidad:</label>
						<html:text property="DEspec_nombre" tabindex="18"
							styleClass="inputColor colocaEspecServiciosDet" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:notEqual>
					<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<%if(((ArrayList) session.getAttribute(Constantes.COMBO_ESPECIALIDAD)).size() > 0 ){%>
						<label for="idEspecialidad" class="obligado">
							Especialidad: <html:select property="CEspec_id"
								styleClass="cbCuadros colocaEspecServicios" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
									property="CEspecId" labelProperty="DDescripcion" />
							</html:select>
						</label>
						<%}%>
					</logic:equal>
					<html:errors property="CEspec_id" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Itinerario:</label>
						<html:text property="CItinerario_id" tabindex="18"
							styleClass="inputColor colocaItinerarioServiciosDet" size="6"
							maxlength="6" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idItinerario" class="obligado">* Itinerario: <html:select
								property="CItinerario_id"
								styleClass="cbCuadros colocaItinerarioServicios" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ITINERARIOS%>"
									property="CItinerarioId" labelProperty="CItinerarioId" />
							</html:select>
						</label>
					</logic:notEqual>
					<html:errors property="CItinerario_id" />

					<br />
					<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label class="obligado"> Nombre corto:</label>
						<html:text property="DNombre_corto" tabindex="18"
							styleClass="inputColor colocaNomCServiciosDet" size="4"
							maxlength="4" readonly="true" />&nbsp;                  
</logic:notEqual>
					<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label class="obligado">* Nombre corto:</label>
						<html:text property="DNombre_corto" tabindex="18"
							styleClass="inputColor colocaNomCServicios" size="4"
							maxlength="4" />&nbsp;                  
</logic:equal>
					<html:errors property="DNombre_corto" />

					<br />
					<logic:notEqual name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label class="obligado"> Nombre largo:</label>
						<html:text property="DNombre_largo" tabindex="18"
							styleClass="inputColor colocaNomLServiciosDet" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:notEqual>
					<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<label class="obligado">* Nombre largo:</label>
						<html:text property="DNombre_largo" tabindex="18"
							styleClass="inputColor colocaNomLServicios" size="60"
							maxlength="100" />&nbsp;                  
</logic:equal>
					<html:errors property="DNombre_largo" />

					<br /> <label class="obligado"> Observaciones:</label>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:text property="DObservaciones" tabindex="18"
							styleClass="inputColor colocaObsServicios" size="60"
							maxlength="200" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<html:text property="DObservaciones" tabindex="18"
							styleClass="inputColor colocaObsServicios" size="60"
							maxlength="200" />&nbsp;                  
</logic:notEqual>
					<html:errors property="DObservaciones" />

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