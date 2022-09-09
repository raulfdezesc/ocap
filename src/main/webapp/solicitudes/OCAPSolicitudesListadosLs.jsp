<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"
	charset="windows-1252"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"
	charset="windows-1252"></script>

<script language="JavaScript" type="text/javascript">

function limpiar(){
   document.forms[0].CGrado_id.value='';
   document.forms[0].CConvocatoriaId.value='';
   document.forms[0].CFase.value='';
   document.forms[0].CEstado.value='';
   <% if(Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())){ %>   
      document.forms[0].CGerencia_id.value='';
   <%}%>
}

function generarPDF(){
   if(document.forms[0].CGrado_id.value=='')
      alert("El campo \'Grado\' es obligatorio.");
   else if(document.forms[0].CFase.value=='')
      alert("El campo \'Fase\' es obligatorio.");
   else if(document.forms[0].CEstado.value=='')
      alert("El campo \'Estado\' es obligatorio.");
   else {
      document.getElementById('mensajeSinDatos').style.visibility='hidden';
      document.getElementById('mensajeSinDatos').style.display='none';
      enviar("OCAPNuevaSolicitud.do?accion=generarPDFListado&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=request.getParameter("opcion")%>");
   }
}

function generarCSV(){
   enviar("OCAPSolicitudes.do?accion=generarPDF&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=request.getParameter("opcion")%>&CSV=<%=Constantes.SI%>");
}

</script>
<div class="contenido">
	<div class="contenidoListadoAspirantesGCP solicitudesLs">
		<html:form action="/OCAPNuevaSolicitud.do">
			<h2 class="tituloContenido">USUARIOS CON OPCI&Oacute;N A CARRERA
				PROFESIONAL</h2>
			<h3 class="subTituloContenido">Generaci&oacute;n de Listados</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<fieldset>
				<legend class="tituloLegend"> Buscador </legend>
				<div class="cuadroFieldset">
					<label for="idVConvocatoria"> Convocatoria: &nbsp; <html:select
							property="CConvocatoriaId"
							styleClass="cbCuadros colocaConvocatoriaLF1" size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
								property="CConvocatoriaId" labelProperty="DNombre" />
						</html:select>
					</label> <label for="idVGrado"> Grado: * <html:select
							property="CGrado_id" styleClass="cbCuadros colocaGradoBuscLF1"
							size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
								property="CGradoId" labelProperty="DDescripcion" />
						</html:select>
					</label> <br /> <br /> <label for="idVSolicitud"> Fase: * <html:select
							property="CFase" styleClass="cbCuadros colocaFaseLLF1">
							<html:option value="">Seleccione...</html:option>
							<html:optionsCollection name="<%=Constantes.COMBO_FASES%>" />
						</html:select>
					</label> <label for="idVSolicitud"> Estado: * <html:select
							property="CEstado" styleClass="cbCuadros colocaEstadoLF1">
							<html:option value="">Seleccione...</html:option>
							<html:optionsCollection name="<%=Constantes.COMBO_ESTADOS%>" />
							<%--<html:options collection="<%=Constantes.COMBO_ESTADOS%>"  property="cEstadoId" labelProperty="DNombre"/>--%>
						</html:select>
					</label>
					<% if(jcylUsuario.getUser().getRol() != null && !(jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE))){ %>
					<br /> <br /> <label for="idVSolicitud"> Gerencia: <html:select
							property="CGerencia_id" styleClass="cbCuadros colocaGerenciaLF1">
							<html:option value="">Todas</html:option>
							<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
								property="CGerenciaId" labelProperty="DNombre" />
						</html:select>
					</label>
					<%}%>
				</div>
				<div class="cuadroFieldset" style="padding-top: 0;">
				
					<label>Orden:</label> <label style="display: block;margin-top: 5px;"><html:radio name="OCAPNuevaSolicitudForm"
										property="ordenListado" styleClass="opcionRadio"
										value="G" />&nbsp;Gerencia</label> <label
						style="display: block; margin-top: 5px;"><html:radio name="OCAPNuevaSolicitudForm"
										property="ordenListado" styleClass="opcionRadio"
										value="A" />&nbsp;Alfab&eacute;tico</label>
				</div>
				<div class="botonesPagina colocaBotonBusc">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:generarPDF();" tabindex="61"> <img
								src="imagenes/imagenes_ocap/dobleFlecha.gif"
								class="colocaImgPrint" alt="Buscar" /> <span> Generar
									Listado </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:limpiar();" tabindex="61"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif"
								class="colocaImgPrint" alt="Limpiar" /> <span> Limpiar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</fieldset>
			<div id="mensajeSinDatos">
				<logic:present name="sinDatos">
					<fieldset>
						<div class="cuadroFieldset">
							<div class="cuadroListadoAspirantes">
								<table border="0" cellpadding="2" cellspacing="0" width="82%"
									align="center">
									<tr>
										<td bgcolor="#FFFFFF" colspan="3" align="center"
											class="textoNegro"><bean:message
												key="documento.noSolicitudes" /></td>
									</tr>
								</table>
							</div>
						</div>
					</fieldset>
				</logic:present>
			</div>
		</html:form>
	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->
