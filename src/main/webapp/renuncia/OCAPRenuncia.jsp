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

<script>

function renunciarSolicitud(){

      var mensaje = "Si renuncia a continuar en el proceso, no podr\u00e1 volver a \u00e9l en la convocatoria en curso. Pulse Aceptar si desea Renunciar, Cancelar en caso de No Renunciar.";

      if(confirm(mensaje)){
         enviar('OCAPRenuncia.do?accion=renunciar'); 
      }
      
}

</script>

<html:form action="/OCAPAreasTrabajo.do?accion=buscar">

	<div class="ocultarCampo">
		<div class="cuadroContenedorConsultas">
			<div class="titulocajaformulario">Renunciar a la Carrera
				Profesional</div>
			<logic:present name="errorConsultando">
				<p>
					<label><bean:write name="errorConsultando" filter="false" /></label>
				</p>
			</logic:present>
			<div class="cajaformulario">
				<fieldset class="normales">
					<br /> <br /> <label class="obligado">Usted est&aacute;
						inmerso en un procedimiento administrativo y a trav&eacute;s de
						esta funcionalidad podr&aacute; renunciar a seguir en &eacute;l.
						La renuncia implica que usted NO ser&aacute; penalizado con 2
						a&ntilde;os durante los cuales no podr&iacute;a volver a solicitar
						la obtenci&oacute;n de grado en SACYL.<br />
					<br /> Una vez realizada la renuncia, no pondr&aacute; volver a
						acceder al proceso en la convocatoria en curso.<br />
					<br /> Si desea renunciar a continuar en el proceso, pulse en el
						bot&oacute;n Renunciar. En caso contrario pulse Cancelar.
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
						href="javascript:renunciarSolicitud();"> <img
							src="imagenes/imagenes_ocap/icon_accept.gif"
							alt="Registrar Solicitud" /> <span> Renunciar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>
		</div>
	</div>
	<!-- /Fin de ocultarCampo -->

</html:form>
