<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<% 
   int contador = 1; 
   while (request.getAttribute("idCuestionarioDespuesIntermedio"+contador)!=null) { %>
<% contador++; %>
<% } %>
<% String idCuestionarioHijoSubdivision = ""; %>

<logic:equal name="accion" value="<%=Constantes.VER_DETALLE%>">
	<logic:equal name="OCAPCuestionariosForm" property="BSubdivision"
		value="<%=Constantes.SI%>">
		<% idCuestionarioHijoSubdivision = request.getAttribute("pregunta0_0_0")==null?"":(String)request.getAttribute("pregunta0_0_0"); %>
	</logic:equal>
</logic:equal>

<!-- Mostramos los botones, solo si hay preguntas -->
<logic:notEqual name="tamanoPreguntas" value="0">
	<div class="espaciadorPeq"></div>
	<div class="botonesPagina">
		<%if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIO_FASE_III) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PRUEBA_ITINERARIO) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CTE) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_FQS_GEST)) { %>
		<logic:equal name="accion" value="<%=Constantes.INSERTAR%>">
			<div class="botonAccion" id="botonSiguiente">
				<div class="margenBackTexto">
					<logic:notEqual name="OCAPCuestionariosForm"
						property="BSubdivision" value="<%=Constantes.SI%>">
						<logic:notEqual name="OCAPCuestionariosForm"
							property="BIndicadores" value="<%=Constantes.SI%>">
							<span><span class="textoNegrita">Finalizar:</span>
								guardar&aacute; definitivamente este formulario.
								Aparecer&aacute; una pantalla donde usted puede validar los
								datos que ha introducido en la prueba de buena pr&aacute;ctica.</span>
							<br />
							<span><span class="textoNegrita">Grabar:</span>
								guardar&aacute; las respuestas actuales de este formulario y
								podr&aacute; continuar contest&aacute;ndolo en otro momento.</span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="BIndicadores"
							value="<%=Constantes.SI%>">
							<span><span class="textoNegrita">Continuar:</span>
								guardar&aacute; definitivamente este formulario y
								continuar&aacute; con las opciones elegidas.</span>
							<br />
							<span>No puede <span class="textoNegrita">grabar</span>
								este formulario. Deber&aacute; contestarlo una &uacute;nica vez.
							</span>
						</logic:equal>
					</logic:notEqual>
					<logic:equal name="OCAPCuestionariosForm" property="BSubdivision"
						value="<%=Constantes.SI%>">
						<span><span class="textoNegrita">Continuar:</span>
							guardar&aacute; definitivamente este formulario y
							continuar&aacute; con la opci&oacute;n elegida.</span>
						<br />
						<span>No puede <span class="textoNegrita">grabar</span>
							este formulario. Deber&aacute; contestarlo una &uacute;nica vez.
						</span>
					</logic:equal>
				</div>
				<div class="flotaDerBotones">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:volverListado('<%=Constantes.NO%>');"> <img
							src="imagenes/imagenes_ocap/aspa_roja.gif" class="colocaImgPrint"
							alt="Volver a la lista de formularios" /> <span> Cancelar
						</span>
					</a>
					</span> <span class="derBoton"></span> <span class="izqBoton"></span> <span
						class="cenBoton"> <a
						href="javascript:pedirConfirmacion('<bean:write name="OCAPCuestionariosForm" property="BObligatorio" />');">
							<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
							class="colocaImgPrint"
							alt="Siguiente paso del Formulario para finalizarlo" /> <logic:notEqual
								name="OCAPCuestionariosForm" property="BSubdivision"
								value="<%=Constantes.SI%>">
								<logic:notEqual name="OCAPCuestionariosForm"
									property="BIndicadores" value="<%=Constantes.SI%>">
									<span> Finalizar </span>
								</logic:notEqual>
								<logic:equal name="OCAPCuestionariosForm"
									property="BIndicadores" value="<%=Constantes.SI%>">
									<span> Continuar </span>
								</logic:equal>
							</logic:notEqual> <logic:equal name="OCAPCuestionariosForm"
								property="BSubdivision" value="<%=Constantes.SI%>">
								<span> Continuar </span>
							</logic:equal>
					</a>
					</span> <span class="derBoton"></span>
					<logic:notEqual name="OCAPCuestionariosForm"
						property="BSubdivision" value="<%=Constantes.SI%>">
						<logic:notEqual name="OCAPCuestionariosForm"
							property="BIndicadores" value="<%=Constantes.SI%>">
							<span class="izqBoton"></span>
							<span class="cenBoton"> <a
								href="javascript:enviarFormulario('N','');"> <img
									src="imagenes/imagenes_ocap/pause.gif" class="colocaImgPrint"
									alt="Grabar el Formulario guardando los datos actuales" /> <span>
										Grabar </span>
							</a>
							</span>
							<span class="derBoton"></span>
						</logic:notEqual>
					</logic:notEqual>
				</div>
			</div>
			<div class="botonAccion" id="botonGuardar"
				style="display: none; visibility: hidden;">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="javascript:enviarFormulario('<bean:write name="OCAPCuestionariosForm" property="BObligatorio" />','<%=Constantes.EST_CUEST_FINALIZAR%>');">
						<img src="imagenes/imagenes_ocap/icon_accept.gif"
						class="colocaImgPrint" alt="Guardar Formulario" /> <span>
							Finalizar </span>
				</a>
				</span> <span class="derBoton"></span>
			</div>
			<div class="botonAccion" id="botonAtras"
				style="display: none; visibility: hidden;">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="javascript:volverEditarFormulario();"> <img
						src="imagenes/imagenes_ocap/icono_modificar.gif"
						class="colocaImgPrint" alt="Volver a editar Formulario" /> <span>
							Modificar </span>
				</a>
				</span> <span class="derBoton"></span>
			</div>
		</logic:equal>
		<logic:equal name="accion" value="<%=Constantes.VER_DETALLE%>">
			<logic:notEqual name="OCAPCuestionariosForm" property="BFinalizado"
				value="<%=Constantes.EST_CUEST_FINALIZAR%>">
				<logic:notEqual name="BFinalizado"
					value="<%=Constantes.EST_CUEST_FINALIZAR%>">
					<div class="botonAccion" id="botonSiguiente">
						<div class="margenBackTexto">
							<span><span class="textoNegrita">Finalizar:</span>
								guardar&aacute; definitivamente este formulario.
								Aparecer&aacute; una pantalla donde usted puede validar los
								datos que ha introducido en la prueba de buena pr&aacute;ctica.</span><br />
							<% if (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL)) { %>
							<span><span class="textoNegrita">Grabar:</span>
								guardar&aacute; las respuestas actuales de este formulario y
								podr&aacute; continuar contest&aacute;ndolo en otro momento.</span>
							<% } %>
						</div>
						<div class="flotaDerBotones">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverListado('<%=Constantes.NO%>');"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Volver a la lista de formularios" />
									<span> Cancelar </span>
							</a>
							</span> <span class="derBoton"></span> <span class="izqBoton"></span> <span
								class="cenBoton"> <a
								href="javascript:pedirConfirmacion('<bean:write name="OCAPCuestionariosForm" property="BObligatorio" />');">
									<img src="imagenes/imagenes_ocap/action_forward.gif"
									class="colocaImgPrint"
									alt="Siguiente paso del Formulario para finalizarlo" /> <span>
										Finalizar </span>
							</a>
							</span> <span class="derBoton"></span>
							<% if (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL)) { %>
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviarFormulario('N','');"> <img
									src="imagenes/imagenes_ocap/pause.gif" class="colocaImgPrint"
									alt="Grabar el Formulario guardando los datos actuales" /> <span>
										Grabar </span>
							</a>
							</span> <span class="derBoton"></span>
							<% } %>

						</div>
					</div>
					<div class="botonAccion" id="botonGuardar"
						style="display: none; visibility: hidden;">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviarFormulario('<bean:write name="OCAPCuestionariosForm" property="BObligatorio" />','<%=Constantes.EST_CUEST_FINALIZAR%>');">
								<img src="imagenes/imagenes_ocap/icon_accept.gif"
								class="colocaImgPrint" alt="Guardar Formulario" /> <span>
									Finalizar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion" id="botonAtras"
						style="display: none; visibility: hidden;">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:volverEditarFormulario();"> <img
								src="imagenes/imagenes_ocap/icono_modificar.gif"
								class="colocaImgPrint" alt="Volver a editar Formulario" /> <span>
									Modificar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:notEqual>
			</logic:notEqual>
		</logic:equal>
		<logic:equal name="accion" value="<%=Constantes.VER_DETALLE%>">
			<logic:equal name="OCAPCuestionariosForm" property="BFinalizado"
				value="<%=Constantes.EST_CUEST_FINALIZAR%>">
				<div class="botonAccion" id="botonVolver">
					<logic:equal name="OCAPCuestionariosForm" property="BMostrarBtnVolver" value="<%=Constantes.SI%>">
					<span class="izqBoton"></span> <span class="cenBoton">
					<a
						href="javascript:volverListado('<%=Constantes.NO%>');"> <img
							src="imagenes/imagenes_ocap/aspa_roja.gif" class="colocaImgPrint"
							alt="Volver a la lista de formularios" /> <span> Volver
						</span>
					</a>
					</span><span class="derBoton"></span>
					</logic:equal>
				
				
				
					<span class="izqBoton"></span> <span class="cenBoton"> <% if (contador > 1) { %>
						<a href="javascript:volverListado('<%=Constantes.SI%>');"> <img
							src="imagenes/imagenes_ocap/flecha_correcto.gif"
							class="colocaImgPrint" alt="Ir a siguiente formulario" /> <span>
								Continuar </span>
					</a> <% } else { %> <logic:equal name="ponderacion"
							value="<%=Constantes.SI%>">
							<% if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIO_FASE_III) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PRUEBA_ITINERARIO)) {%>
							<logic:notEqual name="OCAPCuestionariosForm"
								property="BContestarUsuario" value="<%=Constantes.NO%>">
								<a href="javascript:irPonderacion();"> <img
									src="imagenes/imagenes_ocap/flecha_correcto.gif"
									class="colocaImgPrint"
									alt="Ver la ponderaci&oacute;n de este formulario" /> <span>
										Ver Ponderaci&oacute;n </span>
								</a>
							</logic:notEqual>
							<logic:equal name="OCAPCuestionariosForm"
								property="BContestarUsuario" value="<%=Constantes.NO%>">
								<a href="javascript:volverListado();"> <img
									src="imagenes/imagenes_ocap/flecha_correcto.gif"
									class="colocaImgPrint" alt="Volver al listado de formularios" />
									<span> Volver </span>
								</a>
							</logic:equal>
							<% } else { %>
							<a href="javascript:irPonderacion();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								class="colocaImgPrint"
								alt="Ver la ponderaci&oacute;n de este formulario" /> <span>
									Ver Ponderaci&oacute;n </span>
							</a>
							<% } %>
						</logic:equal> <logic:notEqual name="ponderacion" value="<%=Constantes.SI%>">
							<logic:equal name="OCAPCuestionariosForm" property="BSubdivision"
								value="<%=Constantes.SI%>">
								<% if (!"".equals(idCuestionarioHijoSubdivision)) { %>
								<a
									href="javascript:verHijoSubdivision('<%=idCuestionarioHijoSubdivision%>');">
									<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
									class="colocaImgPrint" alt="Ir a siguiente formulario" /> <span>
										Continuar </span>
								</a>
								<% } else { %>
								<% if ( !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIO_FASE_III) && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PRUEBA_ITINERARIO)) {%>
								<logic:equal name="OCAPCuestionariosForm"
									property="CEvidenciaId" value="0">
									<a href="javascript:volverEval();"> <img
										src="imagenes/imagenes_ocap/flecha_correcto.gif"
										class="colocaImgPrint" alt="Volver al listado de formularios" />
										<span> Volver </span>
									</a>
								</logic:equal>
								<logic:notEqual name="OCAPCuestionariosForm"
									property="CEvidenciaId" value="0">
									<a
										href="javascript:verEvidencia('<bean:write  name="OCAPCuestionariosForm" property="CEvidenciaId" />','<bean:write  name="OCAPCuestionariosForm" property="NEvidencia" />','<bean:write  name="OCAPCuestionariosForm" property="CCuestionarioId" />','<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />');">
										<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
										class="colocaImgPrint" alt="Volver al listado de formularios" />
										<span> Ver Evidencia </span>
									</a>
								</logic:notEqual>
								<% } else { %>
								<a href="javascript:volverListado();"> <img
									src="imagenes/imagenes_ocap/flecha_correcto.gif"
									class="colocaImgPrint" alt="Volver al listado de formularios" />
									<span> Volver </span>
								</a>
								<% } %>
								<% } %>
							</logic:equal>
							<logic:notEqual name="OCAPCuestionariosForm"
								property="BSubdivision" value="<%=Constantes.SI%>">
								<% if ( !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIO_FASE_III) && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PRUEBA_ITINERARIO)) {%>
								<logic:equal name="OCAPCuestionariosForm"
									property="CEvidenciaId" value="0">
									<a href="javascript:volverEval();"> <img
										src="imagenes/imagenes_ocap/flecha_correcto.gif"
										class="colocaImgPrint" alt="Volver al listado de formularios" />
										<span> Volver </span>
									</a>
								</logic:equal>
								<logic:notEqual name="OCAPCuestionariosForm"
									property="CEvidenciaId" value="0">
									<a
										href="javascript:verEvidencia('<bean:write  name="OCAPCuestionariosForm" property="CEvidenciaId" />','<bean:write  name="OCAPCuestionariosForm" property="NEvidencia" />','<bean:write  name="OCAPCuestionariosForm" property="CCuestionarioId" />','<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />');">
										<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
										class="colocaImgPrint" alt="Volver al listado de formularios" />
										<span> Ver Evidencia </span>
									</a>
								</logic:notEqual>
								<% } else { %>
								<a href="javascript:volverListado();"> <img
									src="imagenes/imagenes_ocap/flecha_correcto.gif"
									class="colocaImgPrint" alt="Volver al listado de formularios" />
									<span> Volver </span>
								</a>
								<% } %>
							</logic:notEqual>
						</logic:notEqual> <% } %>
					</span> <span class="derBoton"></span>
				</div>
				<script language="javascript" type="text/javascript">
                  deshabilitarFormulario();
               </script>
			</logic:equal>
			<% if ( jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL)) {%>
			<% if (contador <= 1) { %>
			<logic:equal name="OCAPCuestionariosForm" property="BSimulacion"
				value="<%=Constantes.SI%>">
				<logic:equal name="finalizadoPorUsuario" value="<%=Constantes.SI%>">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:simular();"> <img
								src="imagenes/imagenes_ocap/icono_modificar.gif"
								class="colocaImgPrint" alt="Simular formulario" /> <span>
									Simulaci&oacute;n </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:equal>
			</logic:equal>
			<% } %>
			<% } %>
			<logic:notEqual name="OCAPCuestionariosForm" property="BFinalizado"
				value="<%=Constantes.EST_CUEST_FINALIZAR%>">
				<logic:equal name="BFinalizado"
					value="<%=Constantes.EST_CUEST_FINALIZAR%>">
					<div class="botonAccion" id="botonVolver">
						<span class="izqBoton"></span> <span class="cenBoton"> <% if (contador > 1) { %>
							<a href="javascript:volverListado('<%=Constantes.SI%>');"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								class="colocaImgPrint" alt="Ir a siguiente formulario" /> <span>
									Continuar </span>
						</a> <% } else { %> <logic:equal name="ponderacion"
								value="<%=Constantes.SI%>">
								<% if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIO_FASE_III) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PRUEBA_ITINERARIO)) {%>
								<logic:notEqual name="OCAPCuestionariosForm"
									property="BContestarUsuario" value="<%=Constantes.NO%>">
									<a href="javascript:irPonderacion();"> <img
										src="imagenes/imagenes_ocap/flecha_correcto.gif"
										class="colocaImgPrint"
										alt="Ver la ponderaci&oacute;n de este formulario" /> <span>
											Ver Ponderaci&oacute;n </span>
									</a>
								</logic:notEqual>
								<logic:equal name="OCAPCuestionariosForm"
									property="BContestarUsuario" value="<%=Constantes.NO%>">
									<a href="javascript:volverListado();"> <img
										src="imagenes/imagenes_ocap/flecha_correcto.gif"
										class="colocaImgPrint" alt="Volver al listado de formularios" />
										<span> Volver </span>
									</a>
								</logic:equal>
								<% } else { %>
								<a href="javascript:irPonderacion();"> <img
									src="imagenes/imagenes_ocap/flecha_correcto.gif"
									class="colocaImgPrint"
									alt="Ver la ponderaci&oacute;n de este formulario" /> <span>
										Ver Ponderaci&oacute;n </span>
								</a>
								<% } %>
							</logic:equal> <logic:notEqual name="ponderacion" value="<%=Constantes.SI%>">
								<% if ( !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIO_FASE_III) && !jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PRUEBA_ITINERARIO)) {%>
								<logic:equal name="OCAPCuestionariosForm"
									property="CEvidenciaId" value="0">
									<a href="javascript:volverEval();"> <img
										src="imagenes/imagenes_ocap/flecha_correcto.gif"
										class="colocaImgPrint" alt="Volver al listado de formularios" />
										<span> Volver </span>
									</a>
								</logic:equal>
								<logic:notEqual name="OCAPCuestionariosForm"
									property="CEvidenciaId" value="0">
									<a
										href="javascript:verEvidencia('<bean:write  name="OCAPCuestionariosForm" property="CEvidenciaId" />','<bean:write  name="OCAPCuestionariosForm" property="NEvidencia" />','<bean:write  name="OCAPCuestionariosForm" property="CCuestionarioId" />','<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />');">
										<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
										class="colocaImgPrint" alt="Volver al listado de formularios" />
										<span> Ver Evidencia </span>
									</a>
								</logic:notEqual>
								<% } else { %>
								<a href="javascript:volverListado();"> <img
									src="imagenes/imagenes_ocap/flecha_correcto.gif"
									class="colocaImgPrint" alt="Volver al listado de formularios" />
									<span> Volver </span>
								</a>
								<% } %>
							</logic:notEqual> <% } %>
						</span> <span class="derBoton"></span>
					</div>
					<script language="javascript" type="text/javascript">
                     deshabilitarFormulario();
                  </script>
				</logic:equal>
			</logic:notEqual>
		</logic:equal>
		<% } else if(jcylUsuario.getUser().getRol() != null && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_FQS_ADTVO))) { %>
		<logic:equal name="accion" value="<%=Constantes.INSERTAR%>">
			<div class="botonAccion" id="botonSiguiente">
				<div class="margenBackTexto">
					<span><span class="textoNegrita">Finalizar:</span>
						guardar&aacute; definitivamente este formulario. Aparecer&aacute;
						una pantalla donde usted puede validar los datos que ha
						introducido en la prueba de buena pr&aacute;ctica.</span><br /> <span><span
						class="textoNegrita">Grabar:</span> guardar&aacute; las respuestas
						actuales de este formulario y podr&aacute; continuar
						contest&aacute;ndolo en otro momento.</span>
				</div>
				<div class="flotaDerBotones">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:volverAdtvo();"> <img
							src="imagenes/imagenes_ocap/aspa_roja.gif" class="colocaImgPrint"
							alt="Volver a la lista de formularios" /> <span> Cancelar
						</span>
					</a>
					</span> <span class="derBoton"></span> <span class="izqBoton"></span> <span
						class="cenBoton"> <a
						href="javascript:pedirConfirmacion('<bean:write name="OCAPCuestionariosForm" property="BObligatorio" />');">
							<img src="imagenes/imagenes_ocap/action_forward.gif"
							class="colocaImgPrint"
							alt="Siguiente paso del Formulario para finalizarlo" /> <span>
								Finalizar </span>
					</a>
					</span> <span class="derBoton"></span> <span class="izqBoton"></span> <span
						class="cenBoton"> <a
						href="javascript:enviarFormulario('N','');"> <img
							src="imagenes/imagenes_ocap/pause.gif" class="colocaImgPrint"
							alt="Grabar el Formulario guardando los datos actuales" /> <span>
								Grabar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>
			<div class="botonAccion" id="botonGuardar"
				style="display: none; visibility: hidden;">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="javascript:enviarFormulario('<bean:write name="OCAPCuestionariosForm" property="BObligatorio" />','<%=Constantes.EST_CUEST_FINALIZAR%>');">
						<img src="imagenes/imagenes_ocap/icon_accept.gif"
						class="colocaImgPrint" alt="Guardar Formulario" /> <span>
							Finalizar </span>
				</a>
				</span> <span class="derBoton"></span>
			</div>
			<div class="botonAccion" id="botonAtras"
				style="display: none; visibility: hidden;">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="javascript:volverEditarFormulario();"> <img
						src="imagenes/imagenes_ocap/icono_modificar.gif"
						class="colocaImgPrint" alt="Volver a editar Formulario" /> <span>
							Modificar </span>
				</a>
				</span> <span class="derBoton"></span>
			</div>
		</logic:equal>
		<logic:equal name="accion" value="<%=Constantes.VER_DETALLE%>">
			<%-- Cuestionario pausado --%>
			<logic:notEqual name="OCAPCuestionariosForm" property="BFinalizado"
				value="<%=Constantes.EST_CUEST_FINALIZAR%>">
				<logic:notEqual name="BFinalizado"
					value="<%=Constantes.EST_CUEST_FINALIZAR%>">
					<div class="botonAccion" id="botonSiguiente">
						<div class="margenBackTexto">
							<span><span class="textoNegrita">Finalizar:</span>
								guardar&aacute; definitivamente este formulario.
								Aparecer&aacute; una pantalla donde usted puede validar los
								datos que ha introducido en la prueba de buena pr&aacute;ctica.</span><br />
							<span><span class="textoNegrita">Grabar:</span>
								guardar&aacute; las respuestas actuales de este formulario y
								podr&aacute; continuar contest&aacute;ndolo en otro momento.</span>
						</div>
						<div class="flotaDerBotones">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:volverAdtvo();"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Volver a la lista de formularios" />
									<span> Cancelar </span>
							</a>
							</span> <span class="derBoton"></span> <span class="izqBoton"></span> <span
								class="cenBoton"> <a
								href="javascript:pedirConfirmacion('<bean:write name="OCAPCuestionariosForm" property="BObligatorio" />');">
									<img src="imagenes/imagenes_ocap/action_forward.gif"
									class="colocaImgPrint"
									alt="Siguiente paso del Formulario para finalizarlo" /> <span>
										Finalizar </span>
							</a>
							</span> <span class="derBoton"></span> <span class="izqBoton"></span> <span
								class="cenBoton"> <a
								href="javascript:enviarFormulario('N','');"> <img
									src="imagenes/imagenes_ocap/pause.gif" class="colocaImgPrint"
									alt="Grabar el Formulario guardando los datos actuales" /> <span>
										Grabar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</div>
					<div class="botonAccion" id="botonGuardar"
						style="display: none; visibility: hidden;">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:enviarFormulario('<bean:write name="OCAPCuestionariosForm" property="BObligatorio" />','<%=Constantes.EST_CUEST_FINALIZAR%>');">
								<img src="imagenes/imagenes_ocap/icon_accept.gif"
								class="colocaImgPrint" alt="Guardar Formulario" /> <span>
									Finalizar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion" id="botonAtras"
						style="display: none; visibility: hidden;">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:volverEditarFormulario();"> <img
								src="imagenes/imagenes_ocap/icono_modificar.gif"
								class="colocaImgPrint" alt="Volver a editar Formulario" /> <span>
									Modificar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</logic:notEqual>
			</logic:notEqual>
			<logic:equal name="OCAPCuestionariosForm" property="BFinalizado"
				value="<%=Constantes.EST_CUEST_FINALIZAR%>">
				<div class="botonAccion">
					<div class="flotaDerBotones">
						<span class="izqBoton"></span> <span class="cenBoton"> <logic:equal
								name="OCAPCuestionariosForm" property="CEvidenciaId" value="0">
								<a href="javascript:volverAdtvo();"> <img
									src="imagenes/imagenes_ocap/flecha_correcto.gif"
									class="colocaImgPrint" alt="Volver al listado de formularios" />
									<span> Volver </span>
								</a>
							</logic:equal> <logic:notEqual name="OCAPCuestionariosForm"
								property="CEvidenciaId"
								value="<%=Constantes.EST_CUEST_FINALIZAR%>">
								<logic:notEqual name="OCAPCuestionariosForm"
									property="BContestarUsuario" value="<%=Constantes.DOCUMENTAL%>">
									<a
										href="javascript:verEvidencia('<bean:write  name="OCAPCuestionariosForm" property="CEvidenciaId" />','<bean:write  name="OCAPCuestionariosForm" property="NEvidencia" />','<bean:write  name="OCAPCuestionariosForm" property="CCuestionarioId" />','<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />');">
										<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
										class="colocaImgPrint" alt="Volver al listado de formularios" />
										<span> Rellenar Evidencia </span>
									</a>
								</logic:notEqual>
							</logic:notEqual>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</logic:equal>
			<logic:equal name="BFinalizado"
				value="<%=Constantes.EST_CUEST_FINALIZAR%>">
				<div class="botonAccion">
					<div class="flotaDerBotones">
						<span class="izqBoton"></span> <span class="cenBoton"> <logic:equal
								name="OCAPCuestionariosForm" property="CEvidenciaId" value="0">
								<a href="javascript:volverAdtvo();"> <img
									src="imagenes/imagenes_ocap/flecha_correcto.gif"
									class="colocaImgPrint" alt="Volver al listado de formularios" />
									<span> Volver </span>
								</a>
							</logic:equal> <logic:notEqual name="OCAPCuestionariosForm"
								property="CEvidenciaId" value="0">
								<logic:notEqual name="OCAPCuestionariosForm"
									property="BContestarUsuario" value="<%=Constantes.DOCUMENTAL%>">
									<a
										href="javascript:verEvidencia('<bean:write  name="OCAPCuestionariosForm" property="CEvidenciaId" />','<bean:write  name="OCAPCuestionariosForm" property="NEvidencia" />','<bean:write  name="OCAPCuestionariosForm" property="CCuestionarioId" />','<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />');">
										<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
										class="colocaImgPrint" alt="Volver al listado de formularios" />
										<span> Rellenar Evidencia </span>
									</a>
								</logic:notEqual>
							</logic:notEqual>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</logic:equal>
		</logic:equal>
		<% } %>
		<% if(Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol())) { %>
		<logic:equal name="accion" value="<%=Constantes.VER_DETALLE%>">
			<logic:notEqual name="OCAPCuestionariosForm" property="CEvidenciaId"
				value="0">
				<logic:equal name="OCAPCuestionariosForm" property="BFinalizado"
					value="<%=Constantes.EST_CUEST_FINALIZAR%>">
					<logic:notEqual name="OCAPCuestionariosForm"
						property="BContestarUsuario" value="<%=Constantes.DOCUMENTAL%>">
						<div class="botonAccion">
							<!--<div class="flotaDerBotones">-->
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:verEvidencia('<bean:write  name="OCAPCuestionariosForm" property="CEvidenciaId" />','<bean:write  name="OCAPCuestionariosForm" property="NEvidencia" />','<bean:write  name="OCAPCuestionariosForm" property="CCuestionarioId" />','<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />');">
									<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
									class="colocaImgPrint" alt="Ir a rellenar la evidencia" /> <span>
										Rellenar Evidencia </span>
							</a>
							</span> <span class="derBoton"></span>
							<!--</div>-->
						</div>
					</logic:notEqual>
				</logic:equal>
				<logic:notEqual name="OCAPCuestionariosForm" property="BFinalizado"
					value="<%=Constantes.EST_CUEST_FINALIZAR%>">
					<logic:equal name="BFinalizado"
						value="<%=Constantes.EST_CUEST_FINALIZAR%>">
						<logic:notEqual name="OCAPCuestionariosForm"
							property="BContestarUsuario" value="<%=Constantes.DOCUMENTAL%>">
							<div class="botonAccion">
								<!--<div class="flotaDerBotones">-->
								<span class="izqBoton"></span> <span class="cenBoton"> <a
									href="javascript:verEvidencia('<bean:write  name="OCAPCuestionariosForm" property="CEvidenciaId" />','<bean:write  name="OCAPCuestionariosForm" property="NEvidencia" />','<bean:write  name="OCAPCuestionariosForm" property="CCuestionarioId" />','<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />');">
										<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
										class="colocaImgPrint" alt="Ir a rellenar la evidencia" /> <span>
											Rellenar Evidencia </span>
								</a>
								</span> <span class="derBoton"></span>
								<!--</div>-->
							</div>
						</logic:notEqual>
					</logic:equal>
				</logic:notEqual>
			</logic:notEqual>
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
		</logic:equal>
		<% } %>
		<%-- BOTON DE IMPRIMIR --%>
		<%if(jcylUsuario.getUser().getRol().equals(Constantes.OCAP_USUARIO_FASE_III) || jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PRUEBA_ITINERARIO)) { %>
		
		<!-- OCAP-771 -->
		<logic:equal name="OCAPCuestionariosForm" property="BMostrarBtnImprimir" value="<%=Constantes.SI%>">
			<div class="botonAccion">
				<span class="izqBoton"></span> <span class="cenBoton"> <logic:equal
						name="OCAPCuestionariosForm" property="BEraRepe0"
						value="<%=Constantes.SI%>">
						<a
							href="javascript:enviar('OCAPCuestionarios.do?accion=generarPDFCuestionario&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioId" />&idRepeticion=0');">
							<img src="imagenes/imagenes_ocap/impresion.gif" alt="PDF"
							class="flotaIzq" /><span> IMPRIMIR </span>
						</a>
					</logic:equal> <logic:equal name="OCAPCuestionariosForm" property="BEraRepe0"
						value="<%=Constantes.NO%>">
						<a
							href="javascript:enviar('OCAPCuestionarios.do?accion=generarPDFCuestionario&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioId" />&idRepeticion=<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />');">
							<img src="imagenes/imagenes_ocap/impresion.gif" alt="PDF"
							class="flotaIzq" /><span> IMPRIMIR </span>
						</a>
					</logic:equal>
				</span> <span class="derBoton"></span>
			</div>
		</logic:equal>
		
		<logic:notEqual name="OCAPCuestionariosForm" property="BMostrarBtnImprimir" value="<%=Constantes.SI%>">
			<logic:equal name="accion" value="<%=Constantes.VER_DETALLE%>">
				<logic:equal name="OCAPCuestionariosForm" property="BFinalizado"
					value="<%=Constantes.EST_CUEST_FINALIZAR%>">
					<logic:notEqual name="OCAPCuestionariosForm" property="CEvidenciaId"
						value="0">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <logic:equal
									name="OCAPCuestionariosForm" property="BEraRepe0"
									value="<%=Constantes.SI%>">
									<a
										href="javascript:enviar('OCAPCuestionarios.do?accion=generarPDFCuestionario&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioId" />&idRepeticion=0&idEvidencia=<bean:write name="OCAPCuestionariosForm" property="CEvidenciaId" />&nEvidencia=<bean:write name="OCAPCuestionariosForm" property="NEvidencia" />&idPadreEvidencia=<bean:write name="OCAPCuestionariosForm" property="CPadreEvidenciaId" />');">
										<img src="imagenes/imagenes_ocap/impresion.gif" alt="Imprimir"
										class="flotaIzq" /><span> IMPRIMIR </span>
									</a>
								</logic:equal> <logic:equal name="OCAPCuestionariosForm" property="BEraRepe0"
									value="<%=Constantes.NO%>">
									<a
										href="javascript:enviar('OCAPCuestionarios.do?accion=generarPDFCuestionario&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioId" />&idRepeticion=<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />&idEvidencia=<bean:write name="OCAPCuestionariosForm" property="CEvidenciaId" />&nEvidencia=<bean:write name="OCAPCuestionariosForm" property="NEvidencia" />&idPadreEvidencia=<bean:write name="OCAPCuestionariosForm" property="CPadreEvidenciaId" />');">
										<img src="imagenes/imagenes_ocap/impresion.gif" alt="Imprimir"
										class="flotaIzq" /><span> IMPRIMIR </span>
									</a>
								</logic:equal>
							</span> <span class="derBoton"></span>
						</div>
					</logic:notEqual>
					<%--
	               <logic:equal name="OCAPCuestionariosForm" property="CEvidenciaId" value="0">
	                  <logic:equal name="OCAPCuestionariosForm" property="BContestarUsuario" value="<%=Constantes.NO%>" >                    
	                     <div class="botonAccion">
	                         <div class="flotaDerBotones">
	                           <span class="izqBoton"></span>
	                           <span class="cenBoton">
	                              <a href="javascript:enviar('OCAPCuestionarios.do?accion=generarPDFCuestionario&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioId" />&idRepeticion=<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />');">
	                                 <img src="imagenes/imagenes_ocap/impresion.gif" alt="PDF" class="flotaIzq" />
	                                 <span> PDF </span>
	                              </a>
	                           </span>                        
	                           <span class="derBoton"></span>
	                        </div>
	                     </div>                     
	                  </logic:equal>                     
	               </logic:equal>
	               --%>
	
				</logic:equal>
				<!--FIXME -->
				<logic:notEqual name="OCAPCuestionariosForm" property="BFinalizado"
					value="<%=Constantes.EST_CUEST_FINALIZAR%>">
					<logic:equal name="OCAPCuestionariosForm" property="CPlantillaId"
						value="15">
						<logic:notEqual name="OCAPCuestionariosForm"
							property="CEvidenciaId" value="0">
							<logic:equal name="OCAPCuestionariosForm"
								property="BContestarUsuario" value="<%=Constantes.NO%>">
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="javascript:enviar('OCAPCuestionarios.do?accion=generarPDFCuestionario&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioId" />&idRepeticion=<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />&idEvidencia=<bean:write name="OCAPCuestionariosForm" property="CEvidenciaId" />');">
											<img src="imagenes/imagenes_ocap/impresion.gif" alt="PDF"
											class="flotaIzq" /><span> IMPRIMIR </span>
									</a>
	
									</span> <span class="derBoton"></span>
								</div>
							</logic:equal>
						</logic:notEqual>
					</logic:equal>
				</logic:notEqual>
	
	
	
			</logic:equal>
			<logic:notEqual name="accion" value="<%=Constantes.VER_DETALLE%>">
				<logic:equal name="OCAPCuestionariosForm"
					property="BContestarUsuario" value="<%=Constantes.NO%>">
					<logic:notEqual name="OCAPCuestionariosForm" property="CEvidenciaId"
						value="0">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <logic:equal
									name="OCAPCuestionariosForm" property="BEraRepe0"
									value="<%=Constantes.SI%>">
									<a
										href="javascript:enviar('OCAPCuestionarios.do?accion=generarPDFCuestionario&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioId" />&idRepeticion=0');">
										<img src="imagenes/imagenes_ocap/impresion.gif" alt="PDF"
										class="flotaIzq" /><span> IMPRIMIR </span>
									</a>
								</logic:equal> <logic:equal name="OCAPCuestionariosForm" property="BEraRepe0"
									value="<%=Constantes.NO%>">
									<a
										href="javascript:enviar('OCAPCuestionarios.do?accion=generarPDFCuestionario&idCuestionario=<bean:write name="OCAPCuestionariosForm" property="CCuestionarioId" />&idRepeticion=<bean:write name="OCAPCuestionariosForm" property="idRepeticion" />');">
										<img src="imagenes/imagenes_ocap/impresion.gif" alt="PDF"
										class="flotaIzq" /><span> IMPRIMIR </span>
									</a>
								</logic:equal>
							</span> <span class="derBoton"></span>
						</div>
					</logic:notEqual>
				</logic:equal>
			</logic:notEqual>
		</logic:notEqual>
		<% } %>
	</div>
	<div class="limpiadora"></div>
</logic:notEqual>
<br />
&nbsp;
<br />
&nbsp;
