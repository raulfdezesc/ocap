<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>

<%
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>

<div class="contenido">
	<div class="contenidoDCP1A altaPGP">

		<html:form action="/OCAPUsuarios.do">
			<html:hidden property="CUsr_id" />

			<h2 class="tituloContenido">Gesti&oacute;n de UIDs</h2>
			<h3 class="subTituloContenido">Listado de Usuarios</h3>

			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<fieldset>
				<legend class="tituloLegend"> Datos Personales </legend>

				<div class="cuadroFieldset">
					<label for="idVApell1" class="colocaDatosVis"> Apellidos: <span><bean:write
								name="OCAPUsuariosForm" property="DApellido1" /></span>
					</label> <label for="idVNombre" class="colocaDatosVis"> Nombre: <span><bean:write
								name="OCAPUsuariosForm" property="DNombre" /></span>
					</label> <br />
					<br /> <label for="idVDNI" class="colocaDatosVis">
						NIF/NIE: <span><bean:write name="OCAPUsuariosForm"
								property="CDniReal" /></span>
					</label> <label for="idVLetraUID"> Letra UID*: <html:text
							property="letraUID" styleClass="recuadroM colocaLetraUID"
							maxlength="1" /> <html:errors property="letraUID" />
					</label>
				</div>
			</fieldset>

			<div class="espaciadorPeq"></div>
			<p>Los campos se&ntilde;alados con * son obligatorios</p>
			<div class="limpiadora"></div>

			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:enviar('OCAPUsuarios.do?accion=listarUsuariosDNI');">
							<img src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" />
							<span> Cancelar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>

				<div class="botonAccion">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviar('OCAPUsuarios.do?accion=modificarUsuarioDNI');">
								<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Generar PDF" /> <span> Aceptar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</div>
		</html:form>

	</div>
	<!-- /fin de ContenidoDCP1A_PGP -->
</div>
<!-- /Fin de Contenido -->
