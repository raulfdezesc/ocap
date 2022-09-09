<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/fechas.js'/>"></script>



<html:form action="/OCAPAsistenciales">

	<div class="ocultarCampo">
		<div class="cuadroContenedorConsultas">
			<div class="titulocajaformulario">Desbloquear m&eacute;ritos asistenciales</div>
			<logic:present name="errorConsultando">
				<p>
					<label><bean:write name="errorConsultando" filter="false" /></label>
				</p>
			</logic:present>
			<div class="cajaformulario">
				<fieldset class="normales">
					<br /> <br /> <label class="obligado">Pulsando el bot&oacute;n Aceptar, se reabrir&aacute; 
					el itinerario que tiene asignado y sus &aacute;reas de forma que podr&aacute; volver a cumplimentarlas. 
					S&oacute;lo podr&aacute; desbloquear sus m&eacute;ritos asistenciales, si el plazo para introducir m&eacute;ritos asistenciales
					 est&aacute; vigente en el momento actual.<br />
					<br /> Si desea  continuar con el proceso, pulse en el
						bot&oacute;n Aceptar. En caso contrario pulse Cancelar.
					</label> <br /> <br /> <br />
				</fieldset>
			</div>

			<div class="botonesPagina botonesRenuncia">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="PaginaInicio.do"> <img
							src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" /> <span>
								Cancelar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>

				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="OCAPAsistenciales.do?accion=desbloquear"> <img
							src="imagenes/imagenes_ocap/icon_accept.gif"
							alt="Desbloquear" /> <span> Aceptar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>
		</div>
	</div>
	<!-- /Fin de ocultarCampo -->

</html:form>
