<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.creditos.OCAPCreditosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.areasItinerarios.OCAPAreasItinerariosOT"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"
	charset="windows-1252"></script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoECP">
		<html:form action="/OCAPCuestionarios.do">
			<h2 class="tituloContenido">INFORMACI&Oacute;N GENERAL SOBRE EL
				PROCESO DE EVALUACI&Oacute;N</h2>
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
			<fieldset>
				<div class="cuadroFieldset">
					<bean:write name="OCAPCuestionariosForm" property="DIntroduccion"
						filter="false" />
					<logic:empty name="OCAPCuestionariosForm" property="DIntroduccion">
						<p>Itinerario sin Introducci&oacute;n.</p>
					</logic:empty>
					<logic:notEmpty name="OCAPCuestionariosForm"
						property="DIntroduccion">
						<h4>3. Resumen: &Aacute;reas de
							Evaluaci&oacute;n&nbsp;y&nbsp;Asignaci&oacute;n
							de&nbsp;Cr&eacute;ditos</h4>
						<%--
                           <bean:define id="tamanoListaCreditosNecesarios" name="OCAPCuestionariosForm" property="listaCreditos" />
                           <logic:notEqual name="tamanoListaCreditosNecesarios" value="0">
                              <p class="textoNivel2">
                                 Le recordamos que los cr&eacute;ditos m&iacute;nimos necesarios a conseguir en el &aacute;rea asistencial son:
                              </p>
                              <table class="tablaECP tablaECP2">
                              <logic:iterate id="listaCreditosNecesarios" name="OCAPCuestionariosForm" property="listaCreditos" type="OCAPCreditosOT">
                                 <logic:equal name="OCAPCuestionariosForm" property="CGradoId" value="<%=Long.toString(listaCreditosNecesarios.getCGradoId())%>">
                                    <tr class="titulosTablaECP"> 
                                       <td><span><bean:write name="listaCreditosNecesarios" property="DGrado" /></span></td>
                                    </tr>
                                    <tr>
                                       <td class="textoCentrado"><bean:write name="listaCreditosNecesarios" property="NCreditos" /></td>
                                    </tr>
                                 </logic:equal>
                                 </logic:iterate>
                              </table>
                           </logic:notEqual>
                           --%>
						<p class="listadoLinkArea">Le recordamos que los
							cr&eacute;ditos m&iacute;nimos necesarios a conseguir en el
							&aacute;rea asistencial son:</p>
						<table class="tablaGrado">
							<tr class="tituloGrado">
								<th><span><bean:write name="OCAPCuestionariosForm"
											property="DGrado_des" /></span></th>
							</tr>
							<tr>
								<td class="textoCentrado"><bean:write
										name="OCAPCuestionariosForm" property="NCreditosNecesarios" /></td>
							</tr>
						</table>
						<bean:size id="numAreas" name="OCAPCuestionariosForm"
							property="listadoAreas" />
						<logic:notEqual name="numAreas" value="0">
							<p class="textoNivel2">El n&uacute;mero m&aacute;ximo de
								cr&eacute;ditos que podr&aacute; asignarse por cada &aacute;rea
								de evaluaci&oacute;n en relaci&oacute;n a las pruebas de buenas
								pr&aacute;cticas es el siguiente:</p>
							<table class="tablaECP">
								<tr class="titulosTablaECP">
									<td><span>&Aacute;REA</span></td>
									<td><span>EVALUACI&Oacute;N/PRUEBAS DE BUENA
											PR&Aacute;CTICA</span></td>
									<td><span>CR&Eacute;DITOS</span></td>
								</tr>
								<% int contador =0; %>
								<logic:iterate id="listaAreas" name="OCAPCuestionariosForm"
									property="listadoAreas" type="OCAPAreasItinerariosOT">
									<tr class="datosTablaECP">
										<bean:size id="numPruebasArea" name="listaAreas"
											property="listaCuestionarios" />
										<td class="textoCentrado" rowspan="<%=numPruebasArea%>">
											<span><bean:write name="listaAreas"
													property="DNombreLargo" /></span>
										</td>
										<logic:iterate id="lCuestionarios" name="listaAreas"
											property="listaCuestionarios" type="OCAPCuestionariosOT"
											indexId="i">
											<% contador++; %>
											<logic:notEqual name="i" value="0">
												<tr class="datosTablaECP">
											</logic:notEqual>
											<% if ((contador%2)==0) { %>
											<td>
												<% } else { %>
											
											<td class="parteCentro">
												<% } %>
												<div class="numeracion">
													<bean:write name="listaAreas" property="DNombre" />
													<bean:write name="lCuestionarios" property="DNombre" />
													-
												</div>
												<div class="indice">
													<bean:write name="lCuestionarios" property="DNombreLargo" />
												</div>
											</td>
											<% if ((contador%2)==0) { %>
											<td class="creditos">
												<% } else { %>
											
											<td class="creditos">
												<% } %>
												<div>
													<bean:write name="lCuestionarios" property="NCreditos" />
												</div>
											</td>
									</tr>
								</logic:iterate>
								</logic:iterate>
							</table>



						</logic:notEqual>
						<p class="textoNivel2">
							Recuerde que estos cr&eacute;ditos son acumulativos para el
							reconocimiento de <bean:write name="OCAPCuestionariosForm"
											property="DGrado_des" /> de Carrera Profesional. El
							n&uacute;mero de cr&eacute;ditos asignado a cada formulario
							est&aacute; pensado para que usted pueda alcanzar los necesarios
							de forma suficiente. Puede elegir las
							pruebas&nbsp;de&nbsp;buena&nbsp;pr&aacute;ctica que considere que
							es m&aacute;s competente, teniendo en cuenta que la competencia
							es la suma de todo ello, por lo que es <span class="textoNegrita">imprescindible
								evaluarse de alguna de las pruebas de cada &aacute;rea</span>, y
							siempre teniendo en cuenta el n&uacute;mero de cr&eacute;ditos a
							conseguir.
						</p>
						<p class="textoNivel2">Finalmente y muy importante, no olvide
							que esta autoevaluaci&oacute;n ser&aacute; seguida por un
							Evaluador Externo que adem&aacute;s analizar&aacute; las
							evidencias y certificaciones aportadas, y si es necesario
							realizar&aacute; alguna revisi&oacute;n de cualquiera de las
							&aacute;reas evaluadas.</p>
						<p class="textoNivel2">
							En cualquier momento puede volver a la <span
								class="textoSubrayado textoNegrita">INTRODUCCI&Oacute;N</span>
							si lo considera necesario.
						</p>
						<p class="textoNivel2">Si ha comprendido bien el proceso haga
							clic en &Aacute;reas de Evaluaci&oacute;n, acceder&aacute; al
							Men&uacute; General de &Aacute;reas.</p>
					</logic:notEmpty>
				</div>
			</fieldset>
			<div class="espaciador"></div>
			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <% if(Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())){%>
						<a href="OCAPCuestionarios.do?accion=irListarPruebas" /> <%} else {%>
						<a href="OCAPCuestionarios.do?accion=irAlertaProteccionDatos" />
						<%}%> <img src="imagenes/imagenes_ocap/action_forward.gif"
						class="colocaImgPrint"
						alt="Ver lista de formularios de este itinerario agrupados por &aacute;reas" />
						<span> &Aacute;reas de Evaluaci&oacute;n </span> </a>
					</span> <span class="derBoton"></span>
				</div>
			</div>
			<div class="limpiadora"></div>
		</html:form>
	</div>
	<!-- /fin de ContenidoTextoECP -->
</div>
<!-- /Fin de Contenido -->