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
    
   if (document.OCAPCategProfesionalesForm.DNombre.value != "" && !LetrasYNumeros(document.OCAPCategProfesionalesForm.DNombre)){
         alert('Debe introducir correctamente el Nombre');
         validacion=1;   
      }

   if (document.OCAPCategProfesionalesForm.DDescripcion.value != "" && !LetrasYNumeros(document.OCAPCategProfesionalesForm.DDescripcion)){
         alert('Debe introducir correctamente la Descripci\u00f3n');
         validacion=1;   
      }
   
   if  (validacion ==0){
      if (tipoAccion == '<%=Constantes.INSERTAR%>') {
         enviar('OCAPCategProfesionales.do?accion=insertar');    
      }else{
         enviar('OCAPCategProfesionales.do?accion=grabar&cProfesionalIdS='+ document.OCAPCategProfesionalesForm.CProfesional_id.value);
      }
   }
}

function volverConsulta() {   
   enviar('OCAPCategProfesionales.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
}

</script>

<div class="ocultarCampo">

	<div class="cuadroContenedor">
		<html:form action="/OCAPCategProfesionales.do">
			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<div class="titulocajaformulario">Alta de Categor&iacute;a</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<div class="titulocajaformulario">Detalle de Categor&iacute;a</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
				<div class="titulocajaformulario">Editar Categor&iacute;a</div>
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
						<html:hidden property="CProfesional_id" />
					</logic:equal>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">Estatuto:</label>
						<html:text property="DEstatut_nombre" tabindex="18"
							styleClass="inputColor colocaEstatutoCategDet" size="60"
							maxlength="100" readonly="true" />&nbsp;
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idVEstatut" class="obligado">* Estatuto: <html:select
								property="CEstatut_id"
								styleClass="cbCuadros colocaEstatutoCateg" size="1"
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
						<label class="obligado"> Modalidad:</label>
						<html:text property="DModalidad_nombre" tabindex="18"
							styleClass="inputColor colocaEstatutoCategDet" size="60"
							maxlength="100" readonly="true" />&nbsp;
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idVEstatut" class="obligado">* Modalidad: <html:select
								property="CModalidad_id"
								styleClass="cbCuadros colocaEstatutoCateg" size="1"
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
						<label class="obligado"> Nombre:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaNombreCategCon" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Nombre:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaNombreCateg" size="60"
							maxlength="100" />&nbsp;                  
</logic:notEqual>
					<html:errors property="DNombre" />

					<br /> <label class="obligado"> Descripci&oacute;n:</label>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:text property="DDescripcion" tabindex="18"
							styleClass="inputColor" size="60" maxlength="200" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<html:text property="DDescripcion" tabindex="18"
							styleClass="inputColor" size="60" maxlength="200" />&nbsp;                  
</logic:notEqual>
					<html:errors property="DDescripcion" />

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
