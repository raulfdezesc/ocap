<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="bean.tld" prefix="bean"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>

<script language="javascript">

function validar(){
   var validacion = 0;
   // Validar numéricos
     
   if (document.OCAPSugerenciasForm.DCorreoElectronico.value == ""){
         alert('Debe introducir la dirección Correo Electrónico');
         document.OCAPSugerenciasForm.DCorreoElectronico.focus();
         validacion=1;   
   }else if (!esDireccionCorreo(document.OCAPSugerenciasForm.DCorreoElectronico)){
         alert('Debe introducir correctamente la dirección de Correo Electrónico');
         document.OCAPSugerenciasForm.DCorreoElectronico.select();
         validacion=1;   
   }else if (document.OCAPSugerenciasForm.DTelefono.value == ""){
         alert('Debe introducir un Número de Telefono');
         document.OCAPSugerenciasForm.DTelefono.focus();
         validacion=1;   
   }else if (!soloNumeros(document.OCAPSugerenciasForm.DTelefono)){
         alert('Debe introducir correctamente el Número de Telefono');
         document.OCAPSugerenciasForm.DTelefono.select();
         validacion=1;   
   }else if (document.OCAPSugerenciasForm.DSugerencia.value == ""){
         alert('Debe introducir el texto de la Sugerencia');
         document.OCAPSugerenciasForm.DSugerencia.select();
         validacion=1;   
   }   
      
   if (validacion==0) enviar('OCAPSugerencias.do?accion=enviarSugerencia');    
}

</script>
<body>
	<div class="contenido contenidoFaseIII">
		<div class="contenidoDCP1A sugerencias">
			<html:form action="/OCAPSugerencias.do">
				<h3 class="subTituloContenido">Sugerencias</h3>
				<div class="lineaBajaM"></div>
				<div class="espaciador"></div>
				<div class="cabeceraTexto">
					<p>Con el objeto de hacer más fluida la comunicación en el
						proceso de Evaluación de Carrera Profesional, la Fundación de
						Calidad Sanitaria de Castilla y León, crea un Buzón de
						Sugerencias. A él podrá dirigirse si quiere proponer, sugerir,
						debatir o puntualizar algún tema de interés. Además puede
						realizarnos la consulta que le interese, relacionada con el
						proceso de Evaluación.</p>
				</div>
				<div class="cuadroContenedorConsultas">
					<div class="cajaformulario">
						<fieldset>
							<legend class="tituloLegend"> Buzón de Sugerencias </legend>
							<br /> <label class="obligado">Correo Electrónico</label>&nbsp;
							<html:text property="DCorreoElectronico" tabindex="18"
								styleClass="inputColor colocaCorreo" size="60" maxlength="100" />
							&nbsp;
							<html:errors property="DCorreoElectronico" />
							<br /> <br /> <label class="obligado">Telefono</label>&nbsp;
							<html:text property="DTelefono" tabindex="18"
								styleClass="inputColor colocaTelefono" size="20" maxlength="9" />
							&nbsp;
							<html:errors property="DTelefono" />
							<br /> <br /> <br /> <label for="idGrado" class="obligado">
								Texto de la Sugerencia:</label> <br />
							<html:textarea property="DSugerencia" tabindex="18"
								styleClass="inputColor colocaConvocaConvo" rows="10" cols="110" />
							&nbsp;
							<html:errors property="DSugerencia" />
							<br /> <br />
							<div class="espaciador"></div>
							<div class="botonera">
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="PaginaInicio.do"> <img
											src="imagenes/imagenes_ocap/aspa_roja.gif"
											class="colocaImgPrint" alt="Limpiar" /> <span>
												Cancelar </span>
									</a>
									</span> <span class="derBoton"></span>
								</div>
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="javascript:validar();"> <img
											src="imagenes/imagenes_ocap/icon_accept.gif"
											class="colocaImgPrint" alt="Enviar Sugerencia" /> <span>
												Aceptar </span>
									</a>
									</span> <span class="derBoton"></span>
								</div>
							</div>

						</fieldset>
					</div>
				</div>
			</html:form>
		</div>
		<!-- contenidoDCP1A -->
	</div>
</body>
