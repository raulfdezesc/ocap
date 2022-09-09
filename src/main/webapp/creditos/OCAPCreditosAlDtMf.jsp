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
   if (document.OCAPCreditosForm.NCreditos.value != "" ){   
      document.OCAPCreditosForm.NCreditos.value = document.OCAPCreditosForm.NCreditos.value.replace(",",".");   
      if (!validarImporte(document.OCAPCreditosForm.NCreditos, 2, 2)){
         alert('Debe introducir correctamente el Cr\u00e9dito');
         validacion=1;   
      }
   }
      
   if  (validacion ==0){
      if (tipoAccion == '<%=Constantes.INSERTAR%>') {
         enviar('OCAPCreditos.do?accion=insertar');    
      }else{
         enviar('OCAPCreditos.do?accion=grabar&cCreditoIdS='+ document.OCAPCreditosForm.CCredito_id.value);
      }
   }
}

function volverConsulta() {   
   enviar('OCAPCreditos.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
}

</script>

<div class="ocultarCampo">

	<div class="cuadroContenedor">
		<html:form action="/OCAPCreditos.do">
			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<div class="titulocajaformulario">Alta de Cr&eacute;ditos</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
				<div class="titulocajaformulario">Detalle de Cr&eacute;ditos</div>
			</logic:equal>
			<logic:equal name="tipoAccion" value="<%=Constantes.MODIFICAR%>">
				<div class="titulocajaformulario">Editar Cr&eacute;ditos</div>
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
						<html:hidden property="CCredito_id" />
					</logic:equal>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> Estatuto:</label>
						<html:text property="DEstatut_nombre" tabindex="18"
							styleClass="inputColor colocaEstatCreditCB" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idEstatuto" class="obligado">* Estatuto: <html:select
								property="CEstatut_id"
								styleClass="cbCuadros colocaEstatCreditCB" size="1"
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
						<label class="obligado"> Grado:</label>
						<html:text property="DGrado_nombre" tabindex="18"
							styleClass="inputColor colocaGradoCreditCB" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idGrado" class="obligado">* Grado: <html:select
								property="CGrado_id" styleClass="cbCuadros colocaGradoCreditCB"
								size="1" tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
									property="CGradoId" labelProperty="DNombre" />
							</html:select>
						</label>
					</logic:notEqual>
					<html:errors property="CGrado_id" />

					<br />
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<label class="obligado"> M&eacute;rito:</label>
						<html:text property="DDefmerito_nombre" tabindex="18"
							styleClass="inputColor colocaMeritoCreditCB" size="60"
							maxlength="100" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<label for="idDefmerito" class="obligado">* M&eacute;rito:
							<html:select property="CDefmerito_id"
								styleClass="cbCuadros colocaMeritoCreditCB" size="1"
								tabindex="18">
								<html:option value="">Seleccione...</html:option>
								<html:options
									collection="<%=Constantes.COMBO_DEF_MERITOSCURRICULARES%>"
									property="CDefmeritoId" labelProperty="DNombre" />
							</html:select>
						</label>
					</logic:notEqual>
					<html:errors property="CDefmerito_id" />

					<br /> <label class="obligado"> Cr&eacute;dito:</label>
					<logic:equal name="tipoAccion" value="<%=Constantes.VER_DETALLE%>">
						<html:text property="NCreditos" tabindex="18"
							styleClass="inputColor colocaCreditoCreditDet" size="5"
							maxlength="5" readonly="true" />&nbsp;                  
</logic:equal>
					<logic:notEqual name="tipoAccion"
						value="<%=Constantes.VER_DETALLE%>">
						<html:text property="NCreditos" tabindex="18"
							styleClass="inputColor colocaCreditoCredit" size="5"
							maxlength="5" />&nbsp;                  
</logic:notEqual>
					<html:errors property="NCreditos" />


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
