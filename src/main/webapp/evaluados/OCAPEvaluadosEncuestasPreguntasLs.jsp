<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.encuestaCalidad.OCAPEncuestaCalidadOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="java.util.ArrayList"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/ventanas.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"></script>

<script language="JavaScript">

function limpiar(){
   document.forms[0].CItinerario_id.value = '';
   document.forms[0].CProfesional_id.value = '';
}

function buscar(){
   if (document.forms[0].CItinerario_id.value != '' && document.forms[0].CProfesional_id.value != '')
      alert('S\u00F3lo puede filtrar por uno de los dos criterios.');
   else
      enviar('OCAPEncuestaCalidad.do?accion=listarPreguntas');
}

function verEncuesta(){
   //enviar('OCAPEncuestaCalidad.do?accion=irRellenar');
   lanzar('OCAPEncuestaCalidad.do?accion=irRellenar','encuesta',1280,1024,0,0,1,1);
}

function borraItinerario(){
   document.forms[0].CItinerario_id.value = '';
}

function borraCategoria(){
   document.forms[0].CProfesional_id.value = '';
}

</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoListadoAspirantesGCP buscadorEvaluados">

		<html:form action="/OCAPEncuestaCalidad.do">

			<h3 class="subTituloContenido">Estad&iacute;sticas de Encuestas
				de Evaluados</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<div class="noImprimir">
				<fieldset>
					<legend class="tituloLegend"> Buscador </legend>
					<div class="cuadroFieldset">
						<div class="formulario">
							<div class="unMedio">
								<label for="idVCategoria">Categor&iacute;a:</label>
								<html:select property="CProfesional_id" size="1"
									onchange="borraItinerario();">
									<html:option value="">Seleccione...</html:option>
									<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
										property="CEstatutId" labelProperty="DNombre" />
								</html:select>
							</div>
							<div class="todo">
								<label for="idVEspecialidad">Itinerario:</label>
								<html:select property="CItinerario_id" styleClass="cuadroTodoG"
									size="1" onchange="borraCategoria();">
									<html:option value="">Seleccione...</html:option>
									<html:options collection="<%=Constantes.COMBO_ITINERARIOS%>"
										property="CItinerarioId" labelProperty="DDescripcion" />
								</html:select>
							</div>
						</div>
					</div>
					<div class="botonesPagina colocaBotonBusc">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:buscar();" tabindex="61"> <img
									src="imagenes/imagenes_ocap/dobleFlecha.gif"
									class="colocaImgPrint" alt="Buscar" /> <span> Buscar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:limpiar();" tabindex="61"> <img
									src="imagenes/imagenes_ocap/aspa_roja.gif"
									class="colocaImgPrint" alt="Limpiar" /> <span> Limpiar
								</span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:verEncuesta();" tabindex="61"> <img
									src="imagenes/imagenes_ocap/action_forward.gif"
									class="colocaImgPrint" alt="Ver encuesta vacía" /> <span>
										Ver encuesta </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</div>
				</fieldset>
			</div>

			<logic:equal name="OCAPEncuestaCalidadForm" property="jspInicio"
				value="false">
				<bean:size id="tamanoListado" name="OCAPEncuestaCalidadForm"
					property="listaItinerariosPreguntas" />

				<%--            
            <logic:notEqual name="tamanoListado" value="0">               
               <div class="botonesPagina">
                  <a href="javascript:window.print();"><img src="imagenes/imagenes_ocap/imprimir.gif" alt="Imprimir" class="flotaIzq" /></a>
               </div>               
               <div class="limpiadora"></div>
               <div class="espaciador"></div>                
            </logic:notEqual>
            --%>

				<fieldset>
					<legend class="tituloLegend"> Listado </legend>

					<% if ("14".equals(((String)session.getAttribute("cPreguntaId")))) { %>
					<div class="tituloEstadisticas">11- Explique c&oacute;mo
						mejorar&iacute;a los plazos establecidos para cada una de las
						fases:</div>
					<br />
					<%}
               if ("26".equals(((String)session.getAttribute("cPreguntaId")))) { %>
					<div class="tituloEstadisticas">20- En nuestro af&aacute;n
						por seguir mejorando nuestro servicio hacia los solicitantes de
						grado de Carrera Profesional, nos gustar&iacute;a contar con su
						colaboraci&oacute;n y opini&oacute;n. Por este motivo y si
						as&iacute; lo desea, puede a continuaci&oacute;n, detallarnos sus
						sugerencias:</div>
					<br />
					<% } 
               if ("10".equals(((String)session.getAttribute("cPreguntaId")))) { %>
					<div class="tituloEstadisticas">7- &iquest;Considera
						mejorable alg&uacute;n aspecto t&eacute;cnico de la
						aplicaci&oacute;n? Por favor, detalle su opini&oacute;n:</div>
					<br />
					<% }
               if ("18".equals(((String)session.getAttribute("cPreguntaId")))) { %>
					<div class="tituloEstadisticas">15- &iquest;Cree que la
						autoevaluaci&oacute;n realizada se corresponde con su perfil
						profesional?</div>
					<br />
					<% }
               if ("24".equals(((String)session.getAttribute("cPreguntaId")))) { %>
					<div class="tituloEstadisticas">18- El m&eacute;todo empleado
						para el acceso a grado de Carrera Profesional en Castilla y
						Le&oacute;n, &iquest;ha respondido a las expectativas que
						ten&iacute;a depositadas en &eacute;l?</div>
					<br />
					<% }%>

					<% if (!"18".equals(((String)session.getAttribute("cPreguntaId"))) && !"24".equals(((String)session.getAttribute("cPreguntaId"))) && !"11".equals(((String)session.getAttribute("cPreguntaId")))) { %>
					<logic:equal name="tamanoListado" value="0">
						<div class="textoCaracteres textoNegro textoNormal">
							<div class="espaciador"></div>
							<bean:message key="listado.noDatos" />
							<br />
							<br />
						</div>
					</logic:equal>
					<% } %>

					<% if (!("11".equals(((String)session.getAttribute("cPreguntaId"))))) { %>
					<% if ("18".equals(((String)session.getAttribute("cPreguntaId")))) { %>
					<span class="posibleRespuesta">S&iacute;:</span> &nbsp;<span
						class="Textonormal"><bean:write
							name="OCAPEncuestaCalidadForm" property="NTotalSi" /></span><br />
					<br /> <span class="posibleRespuesta">No:</span> &nbsp;<span
						class="Textonormal"><bean:write
							name="OCAPEncuestaCalidadForm" property="NTotalNo" /></span><br />
					<logic:notEqual name="OCAPEncuestaCalidadForm" property="NTotalNo"
						value="0">
						<div class="tituloEstadisticas">En caso de que su respuesta
							sea negativa, por favor, justifique la respuesta:</div>
					</logic:notEqual>
					<% } %>
					<% if ("24".equals(((String)session.getAttribute("cPreguntaId")))) { %>
					<span class="posibleRespuesta">S&iacute;:</span> &nbsp;<span
						class="Textonormal"><bean:write
							name="OCAPEncuestaCalidadForm" property="NTotalSi" /></span><br />
					<br /> <span class="posibleRespuesta">No:</span> &nbsp;<span
						class="Textonormal"><bean:write
							name="OCAPEncuestaCalidadForm" property="NTotalNo" /></span><br />
					<logic:notEqual name="OCAPEncuestaCalidadForm" property="NTotalNo"
						value="0">
						<div class="tituloEstadisticas">En caso de que su respuesta
							sea negativa, por favor, justif&iacute;quela:</div>
					</logic:notEqual>
					<% } %>
					<logic:notEqual name="tamanoListado" value="0">
						<logic:iterate id="listaTotal" name="OCAPEncuestaCalidadForm"
							property="listaItinerariosPreguntas" type="OCAPUsuariosOT"
							indexId="index">
							<logic:notEqual name="listaTotal" property="DItinerario" value="">
								<!-- <div class="tituloGrado tituloItinerarioPreguntas"> <bean:write name="listaTotal" property="DItinerario" /></div> -->
							</logic:notEqual>
							<table class="tablaPreguntas" width="90%">
								<logic:iterate id="usuario" name="listaTotal"
									property="listaItinerarios" type="OCAPUsuariosOT">
									<!--
                           <tr>
                              <td width="20%"><div class="subTituloItin"><bean:write name="usuario" property="codigoId" /></div></td>
                              <td width="40%"><div class="subTituloItin"><bean:write name="usuario" property="DApellido1" /></div></td>
                              <td width="40%"><div class="subTituloItin"><bean:write name="usuario" property="DNombre" /></div></td>
                           </tr>
                           -->
									<tr>
										<!-- <td colspan="3"> -->
										<td>
											<div class="respuestasEncuesta">
												<img src="imagenes/imagenes_ocap/iconoLista.gif" class=""
													alt="" />
												<bean:write name="usuario" property="BContestado" />
											</div>
										</td>
									</tr>
								</logic:iterate>
							</table>
						</logic:iterate>
						<br />
					</logic:notEqual>
					<%}else{%>
					<div class="tituloEstadisticas">8 - &iquest;Cu&aacute;nto
						tiempo ha empleado en realizar la parte telem&aacute;tica de la
						autoevaluaci&oacute;n?(Horas)</div>
					<br />
					<logic:equal name="tamanoListado" value="0">
						<div class="textoCaracteres textoNegro textoNormal">
							<div class="espaciador"></div>
							<bean:message key="listado.noDatos" />
							<br />
							<br />
						</div>
					</logic:equal>

					<logic:iterate id="listaTotal" name="OCAPEncuestaCalidadForm"
						property="listaItinerariosPreguntas" type="OCAPEncuestaCalidadOT"
						indexId="index">
						<div class="porcentajesRespuesta">
							<div class="lineaRespPorcent">
								<span class="separaMasIzq textoNegrita">0-24 :</span> <span
									class="alineaDcha"><bean:write name="listaTotal"
										property="total1" /> / <bean:write name="listaTotal"
										property="porc1" /> %</span>
							</div>
							<div class="lineaRespPorcent">
								<span class="separaMasIzq textoNegrita">24-48 :</span> <span
									class="alineaDcha"><bean:write name="listaTotal"
										property="total2" /> / <bean:write name="listaTotal"
										property="porc2" /> %</span>
							</div>
							<div class="lineaRespPorcent">
								<span class="separaMasIzq textoNegrita">48-72 :</span> <span
									class="alineaDcha"><bean:write name="listaTotal"
										property="total3" /> / <bean:write name="listaTotal"
										property="porc3" /> %</span>
							</div>
							<div class="lineaRespPorcent">
								<span class="separaMasIzq textoNegrita">72-96 :</span> <span
									class="alineaDcha"><bean:write name="listaTotal"
										property="total4" /> / <bean:write name="listaTotal"
										property="porc4" /> %</span>
							</div>
							<div class="lineaRespPorcent">
								<span class="separaMasIzq textoNegrita">96-120 :</span> <span
									class="alineaDcha"><bean:write name="listaTotal"
										property="total5" /> / <bean:write name="listaTotal"
										property="porc5" /> %</span>
							</div>
							<div class="lineaRespPorcent">
								<span class="separaMasIzq textoNegrita">120-144 :</span> <span
									class="alineaDcha"><bean:write name="listaTotal"
										property="total6" /> / <bean:write name="listaTotal"
										property="porc6" /> %</span>
							</div>
							<div class="lineaRespPorcent">
								<span class="separaMasIzq textoNegrita">144-168 :</span> <span
									class="alineaDcha"><bean:write name="listaTotal"
										property="total7" /> / <bean:write name="listaTotal"
										property="porc7" /> %</span>
							</div>
							<div class="lineaRespPorcent">
								<span class="separaMasIzq textoNegrita">168-192 :</span> <span
									class="alineaDcha"><bean:write name="listaTotal"
										property="total8" /> / <bean:write name="listaTotal"
										property="porc8" /> %</span>
							</div>
							<div class="lineaRespPorcent">
								<span class="separaMasIzq textoNegrita">192-226 :</span> <span
									class="alineaDcha"><bean:write name="listaTotal"
										property="total9" /> / <bean:write name="listaTotal"
										property="porc9" /> %</span>
							</div>
							<div class="lineaRespPorcent">
								<span class="separaMasIzq textoNegrita">216-240 :</span> <span
									class="alineaDcha"><bean:write name="listaTotal"
										property="total10" /> / <bean:write name="listaTotal"
										property="porc10" /> %</span>
							</div>
							<div class="lineaRespPorcent">
								<span class="separaMasIzq textoNegrita">M&aacute;s de
									240:</span> <span class="alineaDcha"><bean:write
										name="listaTotal" property="total11" /> / <bean:write
										name="listaTotal" property="porc11" /> %</span>
							</div>
						</div>
						<div class="porcentajeResultadoTotal">
							<span class="tituloEstadisticas">Total Respuestas (Todos
								los Itinerarios): </span>
							<bean:write name="listaTotal" property="totalRespuestas" />
						</div>
						<br />
					</logic:iterate>
					<%}%>

				</fieldset>

				<logic:notEqual name="tamanoListado" value="0">
					<div class="botonAccion">
						<div class="flotaDerBotones">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:window.print();"> <img
									src="imagenes/imagenes_ocap/impresion.gif" alt="Imprimir"
									class="colocaImgPrint" /> <span> Imprimir </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</div>
				</logic:notEqual>

			</logic:equal>

			<div class="espaciador"></div>
		</html:form>

	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->
