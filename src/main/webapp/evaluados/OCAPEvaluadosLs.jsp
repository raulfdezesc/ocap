<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT"%>


<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>


<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>

<script language="JavaScript">
function buscar(){
      enviar('OCAPEvaluadores.do?accion=listarEvaluados&cCompfqsIdS='+ document.forms[0].CCompfqs_id.value);
}
</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoListadoAspirantesGCP">

		<html:form action="/OCAPEvaluadores.do">
			<h3 class="subTituloContenido">Listado de Evaluados</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<html:hidden property="CCompfqs_id" />

			<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL))){%>
			<fieldset>
				<legend class="tituloLegend"> Buscador </legend>

				<div class="cuadroFieldset">
					<label for="idVApell1"> Apellidos: <html:text
							property="DApellidos" tabindex="1"
							styleClass="recuadroM colocaApellidos" maxlength="30" />
					</label> <label for="idVNombre"> Nombre: <html:text
							property="DNombre" tabindex="3"
							styleClass="recuadroM colocaNombre" maxlength="30" />
					</label> <br />
					<br /> <label for="idVDNI"> NIF/NIE: <html:text
							property="CDni" tabindex="4" styleClass="recuadroM colocaDNI"
							maxlength="10" />
					</label> <br />
					<br />
					<br />
				</div>

				<div class="botonesPagina colocaBotonBusc">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:buscar();" tabindex="5"> <img
								src="imagenes/imagenes_ocap/dobleFlecha.gif"
								class="colocaImgPrint" alt="Buscar" /> <span> Buscar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</fieldset>
			<%}%>

			<logic:equal name="OCAPComponentesfqsForm" property="jspInicio"
				value="false">
				<fieldset>
					<%if(jcylUsuario.getUser().getRol() != null && !(jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL)) && !(jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE))){
                     if(!(jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CTE))){%>
					<legend class="tituloLegend">
						Evaluador:
						<bean:write name="nombre" />
						<bean:write name="apell" />
					</legend>
					<%}else {%>
					<legend class="tituloLegend">
						Evaluador:
						<bean:write name="codigo" />
					</legend>
					<%}%>
					<%}else {%>
					<legend class="tituloLegend"> Listado </legend>
					<%}%>

					<logic:present name="sinDatos">
						<div class="textoCaracteres">
							<div class="espaciador"></div>
							<bean:message key="listado.noDatos" />
						</div>
					</logic:present>

					<div class="espaciador"></div>

					<table class="tablaEvaluadores evaluados">
						<logic:notPresent name="sinDatos">
							<%if(jcylUsuario.getUser().getRol() != null && !(jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CTE)) && !(jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE))){%>
							<tr>
								<th class="col1" id="dNombre">Nombre</th>
								<th class="col2" id="dApellidos">Apellidos</th>
								<th class="col3" id="cDni">NIF/NIE</th>
							</tr>

							<logic:present name="listados" property="elementos">
								<logic:iterate id="elemento" name="listados"
									property="elementos">
									<tr>
										<td class="col1" headers="dNombre"><bean:write
												name="elemento" property="DNombre" /></td>
										<td class="col2" headers="dApellidos"><bean:write
												name="elemento" property="DApellido1" /></td>
										<td class="col3" headers="cDni"><bean:write
												name="elemento" property="CDni" /></td>
										<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL))){%>
										<td><a
											href="OCAPNuevaSolicitud.do?accion=irDetalleFqs&CExp_id=<bean:write name="elemento" property="CExpId" />&CCompfqs_id=<bean:write name="OCAPComponentesfqsForm" property="CCompfqs_id" />"><img
												src="imagenes/imagenes_ocap/lupa.gif" alt="ver datos" /></a></td>
										<%}%>
									</tr>
								</logic:iterate>
							</logic:present>
							<%}else {%>
							<tr>
								<th class="col1" id="cCodigo">C&oacute;digo</th>
								<% if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CTE)){ %>
								<th class="col2" id="dApellidos">Apellidos</th>
								<th class="col3" id="dNombre">Nombre</th>
								<th class="col4" id="cDni">NIF/NIE</th>
								<%}%>
							</tr>

							<logic:present name="listados" property="elementos">
								<logic:iterate id="elemento" name="listados"
									property="elementos" type="OCAPUsuariosOT">
									<tr>
										<td class="col1" headers="cCodigo"><bean:write
												name="elemento" property="codigoId" /></td>
										<% if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CTE)){ %>
										<td class="col2" headers="dNombre"><bean:write
												name="elemento" property="DNombre" /></td>
										<td class="col3" headers="dApellidos"><bean:write
												name="elemento" property="DApellido1" /></td>
										<td class="col4" headers="cDni"><bean:write
												name="elemento" property="CDni" /></td>
										<%}%>
										<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE))){%>
										<td>
											<!--<a href="OCAPCuestionarios.do?accion=irListar&expId=<bean:write name="elemento" property="CExpId" />&tipo=<%=Constantes.INF_LISTADO_FASE_III%>">-->
											<a
											href="OCAPCuestionarios.do?accion=generarInformeCTE&expId=<bean:write name="elemento" property="CExpId" />&tipo=<%=Constantes.INF_LISTADO_FASE_III%>">
												<%if (elemento.getFInformeCe() != null){%> <img
												src="imagenes/imagenes_ocap/icono_editar_check2.gif"
												alt="ver datos" />
										</a> <%}else{%> <img src="imagenes/imagenes_ocap/icono_editar.gif"
											alt="ver datos" /></a> <%}%>
										</td>
										<%}else{%>
										<td><a
											href="OCAPCuestionarios.do?accion=irListar&expId=<bean:write name="elemento" property="CExpId" />&cCompfqsIdS=<bean:write name="OCAPComponentesfqsForm" property="CCompfqs_id" />&opcion=<%=request.getParameter("opcion")%>&cte=<bean:write name="cte"/>&codigo=<bean:write name="codigo"/>&tipo=<%=Constantes.INF_LISTADO_FASE_III%>">
												<%if (elemento.getFInformeCte() != null){%> <img
												src="imagenes/imagenes_ocap/icono_editar_check2.gif"
												alt="ver datos" />
										</a> <%}else{%> <img src="imagenes/imagenes_ocap/icono_editar.gif"
											alt="ver datos" /></a> <%}%></td>
										<%}%>
									</tr>
								</logic:iterate>
							</logic:present>
							<%}%>
						</logic:notPresent>
					</table>

					<logic:present name="listados" property="elementos">
						<div class="paginacionEvaluadores"><%@ include
								file="/comun/paginacion.jsp"%></div>
					</logic:present>

				</fieldset>
			</logic:equal>

			<div class="espaciador"></div>
			<input type="hidden" name="cCompfqsIdS" value="" />
			<%if(jcylUsuario.getUser().getRol() != null && !(jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL)) && !(jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE))){%>
			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span>
					<logic:notEqual name="opcion" value="<%=Constantes.FQS_CTE%>">
						<span class="cenBoton"> <a
							href="javascript:enviar('OCAPEvaluadores.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=request.getParameter("opcion")%>&vuelta=<%=Constantes.SI%>');">
								<img src="imagenes/imagenes_ocap/aspa_roja.gif"
								class="colocaImgPrint" alt="Volver" /> <span> Volver </span>
						</a>
						</span>
					</logic:notEqual>
					<logic:equal name="opcion" value="<%=Constantes.FQS_CTE%>">
						<span class="cenBoton"> <a
							href="javascript:enviar('OCAPEvaluadores.do?accion=buscar&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=request.getParameter("opcion")%>&cte=<bean:write name="cte"/>');">
								<img src="imagenes/imagenes_ocap/aspa_roja.gif"
								class="colocaImgPrint" alt="Volver" /> <span> Volver </span>
						</a>
						</span>
					</logic:equal>
					<span class="derBoton"></span>
				</div>
			</div>
			<%}%>
		</html:form>
	</div>
</div>
<!-- /Fin de ocultarcampo -->

