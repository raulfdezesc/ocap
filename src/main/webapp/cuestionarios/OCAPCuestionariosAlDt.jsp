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
	src="<html:rewrite page='/javascript/fechas.js'/>"
	charset="windows-1252"></script>

<jsp:include page="OCAPCuestionariosJs.jsp" />

<a name="verificar"></a>
<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoProcedimiento">
		<html:form action="/OCAPCuestionarios.do">
			<html:hidden name="OCAPCuestionariosForm" property="cadenaRespuestas" />
			<h2 class="tituloContenido">
				<bean:write name="OCAPCuestionariosForm" property="DArea" />
				PROCEDIMIENTO DE EVALUACI&Oacute;N DEL
				<bean:write name="OCAPCuestionariosForm" property="DAreaLargo" />
				DE LAS COMPETENCIAS PROFESIONALES DE LA CARRERA PROFESIONAL
			</h2>
			<h3 class="categoria2">
				<bean:write name="OCAPCuestionariosForm" property="DTitulo" />
			</h3>
			<h3 class="categoria1">
				<bean:write name="OCAPCuestionariosForm"
					property="DNombreItinerario" />
			</h3>
			<div class="codigoEPR">
				<bean:write name="OCAPCuestionariosForm" property="codigoId" />
			</div>
			<html:hidden property="CPlantillaId" />

			<html:hidden property="CExpId" />
			<logic:notEqual name="OCAPCuestionariosForm" property="DMensaje"
				value="">
				<logic:notEqual name="accion" value="<%=Constantes.VER_DETALLE%>">
					<span name="stComentario">
						<div class="textoComentario">
							<bean:write name="OCAPCuestionariosForm" property="DMensaje"
								filter="false" />
						</div>
					</span>
				</logic:notEqual>
				<logic:equal name="accion" value="<%=Constantes.VER_DETALLE%>">
					<logic:notEqual name="OCAPCuestionariosForm" property="BFinalizado"
						value="<%=Constantes.EST_CUEST_FINALIZAR%>">
						<span name="stComentario">
							<div class="textoComentario">
								<bean:write name="OCAPCuestionariosForm" property="DMensaje"
									filter="false" />
							</div>
						</span>
					</logic:notEqual>
				</logic:equal>
			</logic:notEqual>

			<%-- 2009-10-08: permitir entregar evidencias en cualquier momento --%>
			<%if(Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())) { %>
			<logic:equal name="accion" value="<%=Constantes.VER_DETALLE%>">
				<logic:equal name="OCAPCuestionariosForm" property="BFinalizado"
					value="<%=Constantes.EST_CUEST_FINALIZAR%>">
					<logic:notEqual name="OCAPCuestionariosForm"
						property="CEvidenciaId" value="0">
						<div class="cuadroProcedimiento ocultarCampo">
							
						</div>
						<p class="textoNormal" style="line-height: 1em;">En este proceso de auto-evaluación, usted irá recopilando diferentes evidencias que al finalizar deberá <span class="textoNegrita">escanear y subir a la aplicación</span>. El escaneado se hará teniendo en cuenta lo siguiente:</p>
							<ul style="list-style: circle !important; text-align: left; margin-left: 5%; margin-bottom: 2%; margin-top: 1%;">
								<li>Se escanearán todas las evidencias en un <span class="textoNegrita">único documento</span> en formato <span class="textoNegrita">PDF</span></li>
								<li>El <span class="textoNegrita">tamaño máximo</span> del documento no podrá ser superior a los <span class="textoNegrita">10 MB.</span></li>
							</ul>
					</logic:notEqual>
				</logic:equal>

				<logic:equal name="OCAPCuestionariosForm" property="CEvidenciaId"
					value="0">
					<logic:equal name="OCAPCuestionariosForm"
						property="BContestarUsuario" value="<%=Constantes.NO%>">
						<logic:notEqual name="falsoContestarUsuarioNo"
							value="<%=Constantes.SI%>">
							<div class="cuadroProcedimiento ocultarCampo">
								
							</div>
							<p class="textoNormal" style="line-height: 1em;">En este proceso de auto-evaluación, usted irá recopilando diferentes evidencias que al finalizar deberá <span class="textoNegrita">escanear y subir a la aplicación</span>. El escaneado se hará teniendo en cuenta lo siguiente:</p>
							<ul style="list-style: circle !important; text-align: left; margin-left: 5%; margin-bottom: 2%; margin-top: 1%;">
								<li>Se escanearán todas las evidencias en un <span class="textoNegrita">único documento</span> en formato <span class="textoNegrita">PDF</span></li>
								<li>El <span class="textoNegrita">tamaño máximo</span> del documento no podrá ser superior a los <span class="textoNegrita">10 MB.</span></li>
							</ul>
						</logic:notEqual>
					</logic:equal>
				</logic:equal>
			</logic:equal>

			<logic:equal name="accion" value="<%=Constantes.VER_DETALLE%>">
				<logic:equal name="OCAPCuestionariosForm" property="BFinalizado"
					value="<%=Constantes.EST_CUEST_FINALIZAR%>">
					<logic:notEqual name="OCAPCuestionariosForm"
						property="DObservacionesEvidencia" value="">
						<div class="cuadroProcedimiento ocultarCampo">
							<bean:write name="OCAPCuestionariosForm"
								property="DObservacionesEvidencia" filter="false" />
						</div>
					</logic:notEqual>
				</logic:equal>
			</logic:equal>
			<% } %>
			<%--
         <logic:equal name="accion" value="<%=Constantes.VER_DETALLE%>">
            <logic:equal name="OCAPCuestionariosForm" property="BFinalizado" value="<%=Constantes.EST_CUEST_FINALIZAR%>" >
            <bean:define id="idCuestionario" name="OCAPCuestionariosForm" property="CCuestionarioId" />
            <% /* if (Constantes.CUEST_AP01_EVIDENCIA.equals(idCuestionario.toString()) || Constantes.CUEST_AP02_EVIDENCIA.equals(idCuestionario.toString())  ||
                  Constantes.CUEST_AC05_EVIDENCIA.equals(idCuestionario.toString())  || Constantes.CUEST_AC05B_EVIDENCIA.equals(idCuestionario.toString()) ) { */%>
            <logic:notEqual name="OCAPCuestionariosForm" property="CEvidenciaId" value="0" >
               <fieldset>
                  <legend class="tituloLegend"> Datos Personales </legend>
                  <div class="cuadroFieldset">		
                     <label for="idVNombre" class="colocaDatosVis"> Nombre:
                        <span><bean:write name="OCAPCuestionariosForm" property="DNombreUsuario" /></span>
                     </label>
                     <label for="idVDNI" class="colocaDatosVis"> NIF/NIE:
                        <span><bean:write name="OCAPCuestionariosForm" property="CDniReal" /></span>
                     </label>
                     <br /><br />
                     <label for="idVEspecialidad" class="colocaDatosVis"> Especialidad:
                        <span>
                           <logic:notEmpty name="OCAPCuestionariosForm" property="DEspec_nombre">
                              <bean:write name="OCAPCuestionariosForm" property="DEspec_nombre" />
                           </logic:notEmpty>
                           <logic:empty name="OCAPCuestionariosForm" property="DEspec_nombre">-</logic:empty>
                        </span>
                     </label>
                     <br /><br />
                     <label for="idVGerencia" class="colocaDatosVisGrande"> Gerencia:
                        <span>
                           <logic:notEmpty name="OCAPCuestionariosForm" property="DGerencia_nombre">
                              <bean:write name="OCAPCuestionariosForm" property="DGerencia_nombre" />
                           </logic:notEmpty>
                           <logic:empty name="OCAPCuestionariosForm" property="DGerencia_nombre">-</logic:empty>
                        </span>
                     </label>
                     <br /><br />
                     <label for="idVCentro" class="colocaDatosVisGrande"> Centro de Trabajo:
                        <span>
                           <logic:notEmpty name="OCAPCuestionariosForm" property="DCentrotrabajo_nombre">
                              <bean:write name="OCAPCuestionariosForm" property="DCentrotrabajo_nombre" />
                           </logic:notEmpty>
                           <logic:empty name="OCAPCuestionariosForm" property="DCentrotrabajo_nombre">-</logic:empty>
                        </span>
                     </label>
                     <br /><br />
                     <label for="idVCentro" class="colocaDatosVisGrande"> Unidad de Adscripci&oacute;n/Servicio Actual:
                        <span>
                           <logic:notEmpty name="OCAPCuestionariosForm" property="DAreaUsuario">
                              <bean:write name="OCAPCuestionariosForm" property="DAreaUsuario" />
                           </logic:notEmpty>
                           <logic:empty name="OCAPCuestionariosForm" property="DAreaUsuario">-</logic:empty>
                           /
                           <logic:notEmpty name="OCAPCuestionariosForm" property="DUnidad">
                              <bean:write name="OCAPCuestionariosForm" property="DUnidad" />
                           </logic:notEmpty>
                           <logic:empty name="OCAPCuestionariosForm" property="DUnidad">-</logic:empty>
                        </span>
                     </label>
                     <br /><br />
                  </div>
               </fieldset>
            <% /* } */%>
            </logic:notEqual>
            </logic:equal>
         </logic:equal>
--%>
			<h4 class="subTituloContenidoItinerario">
				<bean:write name="OCAPCuestionariosForm" property="DArea" />
				<bean:write name="OCAPCuestionariosForm" property="DNombre" />
				-
				<bean:write name="OCAPCuestionariosForm" property="DNombreLargo" />
			</h4>
			<%-- Preguntas --%>
			<fieldset>
				<div class="cuadroProcedimiento">
					<bean:size id="tamanoPreguntas" name="OCAPCuestionariosForm"
						property="listadoPreguntas" />
					<logic:equal name="tamanoPreguntas" value="0">
						<span>Su perfil no tiene preguntas para este cuestionario</span>
					</logic:equal>
					<logic:notEqual name="tamanoPreguntas" value="0">

						<%-- Mensaje de confirmacion que mostraremos cuando pinche en Siguiente--%>
						<div id="mensajeAvisoConfirmacion"
							style="display: none; visibility: hidden;">
							<logic:notEqual name="OCAPCuestionariosForm"
								property="BSubdivision" value="<%=Constantes.SI%>">
								<p>
									Verifique que la informaci&oacute;n introducida en el
									formulario
									<logic:notEqual name="OCAPCuestionariosForm"
										property="DNombreLargo" value=""> "<bean:write
											name="OCAPCuestionariosForm" property="DNombreLargo" />"</logic:notEqual>
									es la que realmente ha querido usted reflejar.
								</p>
								<p>Si est&aacute; de acuerdo pinche en el bot&oacute;n
									"Finalizar". Al pulsar este bot&oacute;n ya no podr&aacute;
									modificar ning&uacute;n dato de este apartado.</p>
								<p>Si desea modificar alguno de los datos pinche en el
									bot&oacute;n "Modificar". Volver&aacute; a ver el formulario y
									podr&aacute; cambiar todos aquellos aspectos que usted
									considere necesarios.</p>
							</logic:notEqual>
							<logic:equal name="OCAPCuestionariosForm" property="BSubdivision"
								value="<%=Constantes.SI%>">
								<p>Si est&aacute; de acuerdo con el puesto elegido pinche en
									el bot&oacute;n "Guardar/Finalizar". Al pulsar este bot&oacute;n ya no
									podr&aacute; modificar la opci&oacute;n elegida.</p>
								<p>Si desea modificar su opci&oacute;n pinche en el
									bot&oacute;n "Volver/Modificar". Volver&aacute; a ver el formulario y
									podr&aacute; cambiar la opci&oacute;n seleccionada.</p>
							</logic:equal>
						</div>

						<% request.setAttribute("deshabilitadoSinPreguntas", Constantes.SI); %>
						<jsp:include page="OCAPCuestionariosAl.jsp" />

						<logic:equal name="OCAPCuestionariosForm" property="BFinalizado"
							value="<%=Constantes.EST_CUEST_FINALIZAR%>">
							<jsp:include page="OCAPCuestionariosFinalizadoDt.jsp" />
						</logic:equal>
						<logic:equal name="BFinalizado"
							value="<%=Constantes.EST_CUEST_FINALIZAR%>">
							<logic:notEqual name="deshabilitadoSinPreguntas"
								value="<%=Constantes.SI%>">
								<jsp:include page="OCAPCuestionariosFinalizadoDt.jsp" />
							</logic:notEqual>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm"
							property="BFinalizado"
							value="<%=Constantes.EST_CUEST_FINALIZAR%>">
							<logic:notEqual name="BFinalizado"
								value="<%=Constantes.EST_CUEST_FINALIZAR%>">
								<jsp:include page="OCAPCuestionariosDt.jsp" />
							</logic:notEqual>
						</logic:notEqual>

					</logic:notEqual>
					<%-- tamanoPreguntas != 0 --%>
				</div>

			</fieldset>

			<jsp:include page="OCAPCuestionariosAlDtBotones.jsp" />

			<html:hidden name="OCAPCuestionariosForm"
				property="CPadreEvidenciaId" />

		</html:form>
	</div>
	<%-- /fin de ContenidoTextoProcedimiento --%>
</div>
<%-- /Fin de Contenido --%>

<div class="limpiadora"></div>
<logic:equal name="accion" value="<%=Constantes.INSERTAR%>">
	<script language="javascript" type="text/javascript">habilitarFormulario(); limpiarFormulario();</script>
</logic:equal>
