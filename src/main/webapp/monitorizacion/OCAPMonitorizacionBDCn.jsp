<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>



<div class="contenido">
	<div
		class="contenidoListadoAspirantesGCP solicitudesLs buscadorSolicitudes">
		<html:form action="/OCAPMonitorizacion.do?accion=monitorizarBD">
			<h2 class="tituloContenido">Monitorizaci&oacute;n del sistema</h2>
			<h3 class="subTituloContenido">Gesti&oacute;n de permisos</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<fieldset>
				<legend class="tituloLegend"> Formulario </legend>
				<div class="cuadroFieldset">
					<label for="idPropietarioObjeton"> Propietario: * <html:text
							property="propietarioObjeto" styleId="idPropietarioObjeto"
							styleClass="recuadroM colocaNombreAlta textoNormal"
							maxlength="30" /> <html:errors property="propietarioObjeto" />
					</label>
				</div>

				<div class="botonesPagina colocaBotonBusc">

					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviar('OCAPMonitorizacion.do?accion=monitorizarBD');"
							tabindex="61"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								class="colocaImgPrint" alt="Aceptar" /> <span> Aceptar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</fieldset>
			<p>Los campos se&ntilde;alados con * son obligatorios</p>
		</html:form>

	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->