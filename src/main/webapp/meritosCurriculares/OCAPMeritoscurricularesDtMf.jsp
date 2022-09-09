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
     
   if (document.OCAPMeritoscurricularesForm.DNombrecorto.value != "" && !LetrasYNumeros(document.OCAPMeritoscurricularesForm.DNombrecorto)){
         alert('Debe introducir correctamente el Nombre corto');
         validacion=1;   
      }      
      
   if (document.OCAPMeritoscurricularesForm.DNombre.value != "" && !LetrasYNumeros(document.OCAPMeritoscurricularesForm.DNombre)){
         alert('Debe introducir correctamente el Nombre');
         validacion=1;   
      }

   if (document.OCAPMeritoscurricularesForm.DDescripcion.value != "" && !LetrasYNumeros(document.OCAPMeritoscurricularesForm.DDescripcion)){
         alert('Debe introducir correctamente la Descripci\u00f3n');
         validacion=1;   
      }

   if (document.OCAPMeritoscurricularesForm.DObservaciones.value != "" && !LetrasYNumeros(document.OCAPMeritoscurricularesForm.DObservaciones)){
         alert('Debe introducir correctamente las Observaciones');
         validacion=1;   
      }
      
   if (document.OCAPMeritoscurricularesForm.NCreditos.value != "" ){
      document.OCAPMeritoscurricularesForm.NCreditos.value = document.OCAPMeritoscurricularesForm.NCreditos.value.replace(",",".");
      if (!validarImporte(document.OCAPMeritoscurricularesForm.NCreditos, 2, 2)){
         alert('Debe introducir correctamente el Cr\u00e9dito');
         validacion=1;   
      }    
   }

   if  (validacion ==0){
      if (tipoAccion == '<%=Constantes.INSERTAR%>') {
         enviar('OCAPMeritoscurriculares.do?accion=insertar');    
      }else{
         enviar('OCAPMeritoscurriculares.do?accion=grabar&cMeritoIdS='+ document.OCAPMeritoscurricularesForm.CMerito_id.value);
      }
   }
}

function volverConsulta() {   
   enviar('OCAPMeritoscurriculares.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
}

</script>

<div class="ocultarCampo">

	<div class="cuadroContenedor">
		<html:form action="/OCAPMeritoscurriculares.do">
			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<div class="titulocajaformulario">Detalle de M&eacute;ritos
					Curriculares</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
				<div class="titulocajaformulario">Editar M&eacute;ritos
					Curriculares</div>
			</logic:equal>
			<div class="cajaformulario">
				<fieldset class="normales">
					<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
						<legend class="normalesLegend">Datos a editar</legend>
					</logic:equal>
					<hr class="hrblanco"></hr>
					<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
						<html:hidden property="CMerito_id" />&nbsp;                  
</logic:equal>

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> M&eacute;rito:</label>
						<html:text property="DDefmerito_nombre" tabindex="18"
							styleClass="inputColor colocaMeritosMeritCBDet" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idDefmerito" class="obligado">* M&eacute;rito:
							<html:select property="CDefmerito_id"
								styleClass="cbCuadros colocaMeritosMeritCBEdit" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options
									collection="<%=Constantes.COMBO_DEF_MERITOSCURRICULARES%>"
									property="CDefmeritoId" labelProperty="DNombre" />
							</html:select>
						</label>
					</logic:notEqual>
					<html:errors property="CDefmerito_id" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Estatuto:</label>
						<html:text property="DEstatut_nombre" tabindex="18"
							styleClass="inputColor colocaMeritosEstatCBCon" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idEstatutario" class="obligado">* Estatuto: <html:select
								property="CEstatut_id"
								styleClass="cbCuadros colocaMeritosEstatCBEdit" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ESTATUTARIO%>"
									property="CEstatutId" labelProperty="DNombre" />
							</html:select>
						</label>
					</logic:notEqual>
					<html:errors property="CEstatut_id" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Actividad:</label>
						<html:text property="DActividad_nombre" tabindex="18"
							styleClass="inputColor colocaMeritosActivCBCon" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idActividad" class="obligado"> Actividad: <html:select
								property="CActividad_id"
								styleClass="cbCuadros colocaMeritosActivCBEdit" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_ACTIVIDAD%>"
									property="CActividadId" labelProperty="DNombre" />
							</html:select>
						</label>
					</logic:notEqual>
					<html:errors property="CActividad_id" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Modalidad:</label>
						<html:text property="DModalidad_nombre" tabindex="18"
							styleClass="inputColor colocaMeritosModalCBCon" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idModalidad" class="obligado"> Modalidad: <html:select
								property="CModalidad_id"
								styleClass="cbCuadros colocaMeritosModalCBEdit" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_MODALIDAD%>"
									property="CModalidadId" labelProperty="DNombre" />
							</html:select>
						</label>
					</logic:notEqual>
					<html:errors property="CModalidad_id" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Factor:</label>
						<html:text property="DFactor_nombre" tabindex="18"
							styleClass="inputColor colocaMeritosFactorCBCon" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idFactor" class="obligado"> Factor: <html:select
								property="CFactor_id"
								styleClass="cbCuadros colocaMeritosFactorCBEdit" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_FACTORESIMPACTO%>"
									property="CFactorId" labelProperty="DNombre" />
							</html:select>
						</label>
					</logic:notEqual>
					<html:errors property="CFactor_id" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Tipo de firmante:</label>
						<html:text property="DTipo_nombre" tabindex="18"
							styleClass="inputColor colocaMeritosTipoFCBCon" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idTipo" class="obligado"> Tipo de firmante: <html:select
								property="CTipo_id"
								styleClass="cbCuadros colocaMeritosTipoFCBEdit" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_TIPOSFIRMANTE%>"
									property="CTipoId" labelProperty="DNombre" />
							</html:select>
						</label>
					</logic:notEqual>
					<html:errors property="CTipo_id" />

					<br /> <label class="obligado"> Tipo de m&eacute;rito:</label>
					<html:text property="CTipo_merito" tabindex="18"
						styleClass="inputColor colocaMeritosTipoMCBCon2" size="30"
						maxlength="30" readonly="true" />
					&nbsp;
					<html:errors property="CTipo_merito" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Nombre corto:</label>
						<html:text property="DNombrecorto" tabindex="18"
							styleClass="inputColor colocaMeritosNomCCBCon2" size="10"
							maxlength="10" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Nombre corto:</label>
						<html:text property="DNombrecorto" tabindex="18"
							styleClass="inputColor colocaMeritosNomCCBEdit" size="10"
							maxlength="10" />&nbsp;                  
</logic:notEqual>
					<html:errors property="DNombrecorto" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Nombre:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaMeritosNombreCBCon" size="60"
							maxlength="200" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Nombre:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaMeritosNombreCBEdit" size="60"
							maxlength="200" />&nbsp;                  
</logic:notEqual>
					<html:errors property="DNombre" />

					<br /> <label class="obligado"> Descripci&oacute;n:</label>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:text property="DDescripcion" tabindex="18"
							styleClass="inputColor colocaMeritosDescCBCon" size="60"
							maxlength="300" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<html:text property="DDescripcion" tabindex="18"
							styleClass="inputColor colocaMeritosDescCBEdit" size="60"
							maxlength="300" />&nbsp;                  
</logic:notEqual>
					<html:errors property="DDescripcion" />

					<br /> <label class="obligado"> Observaciones:</label>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:text property="DObservaciones" tabindex="18"
							styleClass="inputColor colocaMeritosObserCBCon" size="60"
							maxlength="300" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<html:text property="DObservaciones" tabindex="18"
							styleClass="inputColor colocaMeritosObserCBEdit" size="60"
							maxlength="300" />&nbsp;                  
</logic:notEqual>
					<html:errors property="DObservaciones" />

					<br /> <label class="obligado"> Cr&eacute;dito:</label>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:text property="NCreditos" tabindex="18"
							styleClass="inputColor colocaMeritosCreditCBCon" size="5"
							maxlength="5" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<html:text property="NCreditos" tabindex="18"
							styleClass="inputColor colocaMeritosCreditCBEdit" size="5"
							maxlength="5" />&nbsp;                  
</logic:notEqual>
					<html:errors property="NCreditos" />

					<div class="botonera">
						<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
							<div class="botonAccion">
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:validar('<bean:write name="tipoAccion" />');">
										<span> Grabar </span>
								</a>
								</span> <span class="derBoton"></span>
							</div>
						</logic:equal>

						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverConsulta();"> <span> Volver </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</div>
				</fieldset>
			</div>
			<div id="divTexto">
				<p>
					<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
						<label class="obligadoTexto">Los campos se&ntilde;alados
							con * son obligatorios</label>
					</logic:equal>
				</p>
			</div>
		</html:form>
	</div>

</div>
<!-- /Fin de ocultarCampo -->
