<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="java.util.ArrayList"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>



<div class="contenido">
	<div
		class="contenidoListadoAspirantesGCP solicitudesLs gestionSolicitudes">

		<html:form action="/OCAPUsuarios.do?accion=listarUsuariosDNI">

			<h2 class="tituloContenido">Gesti&oacute;n de UIDs</h2>
			<h3 class="subTituloContenido">Listado de Usuarios</h3>

			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<fieldset>
				<legend class="tituloLegend"> Buscador </legend>

				<div class="cuadroFieldset">
					<label for="idVDNI"> NIF/NIE: <html:text
							property="CDniReal_Busqueda" styleClass="recuadroM colocaDNI"
							maxlength="10" /> <html:errors property="cDniReal_Busqueda" />
					</label>
				</div>
				<!--
               <div class="espaciadorPeq"></div>               
                <p>Los campos se&ntilde;alados con  *  son obligatorios</p>
               <div class="limpiadora"></div>
-->

				<div class="botonesPagina colocaBotonBusc">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviar('OCAPUsuarios.do?accion=listarUsuariosDNI');"
							tabindex="61"> <img
								src="imagenes/imagenes_ocap/dobleFlecha.gif"
								class="colocaImgPrint" alt="Buscar" /> <span> Buscar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviar('OCAPUsuarios.do?accion=irBuscadorUsuariosDNI');"
							tabindex="61"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif"
								class="colocaImgPrint" alt="Limpiar" /> <span> Limpiar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>

			</fieldset>

			<logic:present name="listadoUsuariosDNI">
				<fieldset>
					<bean:size id="tamano" name="listadoUsuariosDNI" />
					<legend class="tituloLegend"> Listado de Usuarios </legend>
					<div class="cuadroFieldset cuadroInferior">
						<div>

							<logic:equal name="tamano" value="0">
								<table border="0" cellpadding="2" cellspacing="0" width="82%"
									align="center">
									<tr>
										<td bgcolor="#FFFFFF" colspan="3" align="center"
											class="textoNegro"><bean:message
												key="documento.noRegistros" arg0="usuarios" /></td>
									</tr>
								</table>
							</logic:equal>

							<logic:notEqual name="tamano" value="0">

								<table class="tablaListado">
									<tr class="cabeceraTabla">
										<td class="tituloAsp tituloApellidos">Apellidos</td>
										<td class="tituloAsp tituloNombre">Nombre</td>
										<td class="tituloAsp tituloNIF">NIF/NIE</td>
										<td class="tituloAsp tituloUID">UID</td>
										<td class="tituloLupa"></td>
									</tr>
									<logic:iterate id="lista" name="listadoUsuariosDNI"
										type="es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT">
										<tr class="cuerpoTabla">
											<td class="apellido"><bean:write name="lista"
													property="DApellido1" /></td>
											<td class="nombre"><bean:write name="lista"
													property="DNombre" /></td>
											<td class="dni"><bean:write name="lista"
													property="CDniReal" /></td>
											<td class="uid"><bean:write name="lista" property="CDni" /></td>
											<td class="imagen lateralImagen"><a
												href="OCAPUsuarios.do?accion=irModificarUsuarioDNI&cUsrId=<bean:write name="lista" property="CUsrId" />">Modificar</a>
											</td>
										</tr>
									</logic:iterate>
								</table>

							</logic:notEqual>
							<!-- tamano != 0 -->

						</div>
					</div>

				</fieldset>

			</logic:present>


		</html:form>

	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->
