<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.areasItinerarios.OCAPAreasItinerariosOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>

<script language="javascript">
function volverAdtvo(){
   enviar('OCAPNuevaSolicitud.do?accion=irDetalleFqs&CExp_id=<bean:write name="OCAPCuestionariosForm" property="CExpId" />&fase=<%=Constantes.FASE_CA_ESCANEADA%>');
}
</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoAutoevaluacion">
		<html:form action="/OCAPCuestionarios.do">
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

			<h4 class="subTituloContenidoItinerario">
				<bean:write name="OCAPCuestionariosForm" property="DArea" />
				<bean:write name="OCAPCuestionariosForm" property="DNombre" />
				-
				<bean:write name="OCAPCuestionariosForm" property="DNombreLargo" />
			</h4>
			<fieldset>

				<div class="cuadroFieldset">
					<logic:notEqual name="OCAPCuestionariosForm" property="DMensaje"
						value="">
						<p>
							<bean:write name="OCAPCuestionariosForm" property="DMensaje"
								filter="false" />
						</p>
					</logic:notEqual>

					<div class="limpiadora"></div>

					<logic:notEqual name="OCAPCuestionariosForm"
						property="NRepeticiones" value="1">
						<div class="listadoEvaluacion">
							<logic:iterate id="lSubCuestionarios"
								name="OCAPCuestionariosForm" property="listadoSubCuestionarios"
								type="OCAPCuestionariosOT" indexId="indice">
								<div>
									<logic:equal name="lSubCuestionarios" property="BContestado"
										value="<%=Constantes.SI%>">
										<img src="imagenes/imagenes_ocap/icono_editar_check.gif"
											alt="icono editar" />
									</logic:equal>
									<logic:equal name="lSubCuestionarios" property="BContestado"
										value="<%=Constantes.NO%>">
										<img src="imagenes/imagenes_ocap/icono_editar.gif"
											alt="icono editar" />
									</logic:equal>
									<a
										href="OCAPCuestionarios.do?accion=verCuestionario&idCuestionario=<bean:write name="lSubCuestionarios" property="CCuestionarioId" />&verPreguntas=<bean:write name="lSubCuestionarios" property="BVerPreguntas" />&idRepeticion=<%=indice.intValue()+1%>&idPadreEvidencia=<bean:write name="OCAPCuestionariosForm" property="CPadreEvidenciaId" />">
										<span class="lineaListado"> <bean:write
												name="lSubCuestionarios" property="DArea" /> <bean:write
												name="lSubCuestionarios" property="DNombre" /> - <bean:write
												name="lSubCuestionarios" property="DNombreLargo" /> (<bean:write
												name="lSubCuestionarios" property="DRepeticion" /> <%=indice.intValue()+1%>)
									</span>
									</a>
								</div>
							</logic:iterate>
						</div>
					</logic:notEqual>

					<logic:equal name="OCAPCuestionariosForm" property="NRepeticiones"
						value="1">
						<div class="listadoEvaluacion">
							<logic:iterate id="lSubCuestionarios"
								name="OCAPCuestionariosForm" property="listadoSubCuestionarios"
								type="OCAPCuestionariosOT">
								<div>
									<logic:equal name="lSubCuestionarios" property="BContestado"
										value="<%=Constantes.SI%>">
										<img src="imagenes/imagenes_ocap/icono_editar_check.gif"
											alt="icono editar" />
									</logic:equal>
									<logic:equal name="lSubCuestionarios" property="BContestado"
										value="<%=Constantes.NO%>">
										<img src="imagenes/imagenes_ocap/icono_editar.gif"
											alt="icono editar" />
									</logic:equal>
									<a
										href="OCAPCuestionarios.do?accion=verCuestionario&idCuestionario=<bean:write name="lSubCuestionarios" property="CCuestionarioId" />&verPreguntas=<bean:write name="lSubCuestionarios" property="BVerPreguntas" />&idPadreEvidencia=<bean:write name="OCAPCuestionariosForm" property="CPadreEvidenciaId" />">
										<span class="lineaListado"> <bean:write
												name="lSubCuestionarios" property="DArea" /> <bean:write
												name="lSubCuestionarios" property="DNombre" /> - <bean:write
												name="lSubCuestionarios" property="DNombreLargo" />
									</span>
									</a>
								</div>
							</logic:iterate>
						</div>
					</logic:equal>
				</div>

			</fieldset>

			<div class="espaciadorPeq"></div>

			<bean:size id="numEviDocumentales" name="OCAPCuestionariosForm"
				property="listaEviDocumentales" />
			<logic:notEqual name="numEviDocumentales" value="0">
				<logic:iterate id="eviDocu" name="OCAPCuestionariosForm"
					property="listaEviDocumentales" type="OCAPCuestionariosOT">
                  Aportar Evidencia Documental N&ordm; <bean:write
						name="eviDocu" property="NEvidencia" />: <bean:write
						name="eviDocu" property="NCreditos" /> cr&eacute;ditos.<br />
				</logic:iterate>
			</logic:notEqual>

			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <% if(Constantes.OCAP_FQS_ADTVO.equals(jcylUsuario.getUser().getRol())) { %>
						<a href="javascript:volverAdtvo();"> <img
							src="imagenes/imagenes_ocap/volver.gif" class="colocaImgPrint"
							alt="Ver lista de formularios de este itinerario agrupados por �reas" />
							<span> Volver </span>
					</a> <% } else if (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())){ %>
						<logic:equal name="OCAPCuestionariosForm"
							property="CCuestionarioPadreId" value="0">
							<a href="OCAPCuestionarios.do?accion=irListarPruebas">
						</logic:equal> <logic:notEqual name="OCAPCuestionariosForm"
							property="CCuestionarioPadreId" value="0">
							<a
								href="OCAPCuestionarios.do?accion=verCuestionario&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioPadreId" />&saltarIntermedio1=<%=Constantes.SI%>">
						</logic:notEqual> <img src="imagenes/imagenes_ocap/volver.gif"
						class="colocaImgPrint"
						alt="Ver lista de formularios de este itinerario agrupados por �reas" />
						<span> Volver </span> </a> <% } else { %> <logic:equal
							name="OCAPCuestionariosForm" property="CCuestionarioPadreId"
							value="0">
							<a href="OCAPCuestionarios.do?accion=irListar">
						</logic:equal> <logic:notEqual name="OCAPCuestionariosForm"
							property="CCuestionarioPadreId" value="0">
							<a
								href="OCAPCuestionarios.do?accion=verCuestionario&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioPadreId" />&saltarIntermedio1=<%=Constantes.SI%>">
						</logic:notEqual> <img src="imagenes/imagenes_ocap/volver.gif"
						class="colocaImgPrint"
						alt="Ver lista de formularios de este itinerario agrupados por �reas" />
						<span> Volver </span> </a> <% } %>
					</span> <span class="derBoton"></span>
				</div>
			</div>

			<html:hidden property="CExpId" />

		</html:form>
	</div>
	<!-- /fin de ContenidoMC -->
</div>
<!-- /Fin de Contenido -->

<div class="limpiadora"></div>