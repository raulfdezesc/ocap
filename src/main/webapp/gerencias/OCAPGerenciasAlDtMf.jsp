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
    
   if (document.OCAPGerenciasForm.DNombre.value != "" && !LetrasYNumeros(document.OCAPGerenciasForm.DNombre)){
         alert('Debe introducir correctamente el Nombre');
         validacion=1;   
      }

   if (document.OCAPGerenciasForm.DNombre_corto.value != "" && !LetrasYNumeros(document.OCAPGerenciasForm.DNombre_corto)){
         alert('Debe introducir correctamente el Nombre Corto');
         validacion=1;   
      }
      
   if (document.OCAPGerenciasForm.ACodldap.value != "" && !LetrasYNumeros(document.OCAPGerenciasForm.ACodldap)){
         alert('Debe introducir correctamente el C\u00f3digo Ldap');
         validacion=1;   
      }      
      
   if  (validacion ==0){
      if (tipoAccion == '<%=Constantes.INSERTAR%>') {
         enviar('OCAPGerencias.do?accion=insertar');    
      }else{
         enviar('OCAPGerencias.do?accion=grabar&cGerenciaIdS='+ document.OCAPGerenciasForm.CGerencia_id.value);
      }
   }
}

function volverConsulta() {   
   enviar('OCAPGerencias.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
}

</script>

<div class="ocultarCampo">

	<div class="cuadroContenedor">
		<html:form action="/OCAPGerencias.do">
			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<div class="titulocajaformulario">Alta de Gerencias</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<div class="titulocajaformulario">Detalle de Gerencias</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
				<div class="titulocajaformulario">Editar Gerencias</div>
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
						<html:hidden property="CGerencia_id" />
					</logic:equal>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Provincia:</label>
						<html:text property="DProvincia_nombre" tabindex="18"
							styleClass="inputColor colocaNomGerenciaDet" size="20"
							maxlength="20" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idVProvincia" class="obligado">* Provincia: <html:select
								property="CProvincia_id"
								styleClass="cbCuadros colocaGerenciaProvinciaCB" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_PROVINCIAS%>"
									property="CProvinciaId" labelProperty="DProvincia" />
							</html:select>
						</label>
					</logic:notEqual>
					<html:errors property="CProvincia_id" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Tipo de Gerencia:</label>
						<html:text property="DTipogerenciaNombre" tabindex="18"
							styleClass="inputColor colocaTipoGerenciaDet" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idTGerencia" class="obligado">* Tipo de
							Gerencia: <html:select property="CTipogerencia_id"
								styleClass="cbCuadros colocaGerenciaTipo" size="1" tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_TIPOS_GERENCIAS%>"
									property="CTipogerenciaId" labelProperty="DNombre" />
							</html:select>
						</label>
					</logic:notEqual>
					<html:errors property="CTipogerencia_id" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Nombre de Gerencia:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaNombreGerencia" size="80"
							maxlength="200" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Nombre de Gerencia:</label>
						<html:text property="DNombre" tabindex="18"
							styleClass="inputColor colocaNombreGerencia" size="60"
							maxlength="200" />&nbsp;                  
</logic:notEqual>
					<html:errors property="DNombre" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Nombre Corto:</label>
						<html:text property="DNombre_corto" tabindex="18"
							styleClass="inputColor colocaNomCortoGerencia" size="80"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* Nombre Corto:</label>
						<html:text property="DNombre_corto" tabindex="18"
							styleClass="inputColor colocaNomCortoGerencia" size="60"
							maxlength="100" />&nbsp;                  
</logic:notEqual>
					<html:errors property="DNombre_corto" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> C&oacute;digo Ldap:</label>
						<html:text property="ACodldap" tabindex="18"
							styleClass="inputColor colocaLdapGerencia" size="6" maxlength="6"
							readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado">* C&oacute;digo Ldap:</label>
						<html:text property="ACodldap" tabindex="18"
							styleClass="inputColor colocaLdapGerencia" size="6" maxlength="6" />&nbsp;                  
</logic:notEqual>
					<html:errors property="ACodldap" />

					<br />

					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Gerente:</label>
						<html:text property="DGerente" tabindex="18"
							styleClass="inputColor colocaGerenteGerencia" size="60"
							maxlength="60" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="Noobligado"> Gerente:</label>
						<html:text property="DGerente" tabindex="18"
							styleClass="inputColor colocaLdapGerencia" size="60"
							maxlength="60" />&nbsp;                  
</logic:notEqual>
					<html:errors property="DGerente" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Director:</label>
						<html:text property="DDirector" tabindex="18"
							styleClass="inputColor colocaDirectorGerencia" size="60"
							maxlength="60" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label class="Noobligado2"> Director:</label>
						<html:text property="DDirector" tabindex="18"
							styleClass="inputColor colocaLdapGerencia" size="60"
							maxlength="60" />&nbsp;                  
</logic:notEqual>
					<html:errors property="DDirector" />

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
