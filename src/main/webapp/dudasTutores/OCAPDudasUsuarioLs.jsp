<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="es.jcyl.fqs.ocap.ot.dudasTutores.OCAPDudasTutoresOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>

<script language="JavaScript">
function limpiar(){
   document.forms[0].CTemaId.value='';
   document.forms[0].FInicio.value='';
   document.forms[0].FFin.value='';
   document.forms[0].FInicioRespuesta.value='';
   document.forms[0].FFinRespuesta.value='';
}

function buscar(campo){
   enviar('OCAPDudasTutores.do?accion=buscarDudasUsuario&<%=Constantes.ORDENACION%>='+campo+'&<%=Pagina.PRIMER_REGISTRO_PARAMETER_NAME%>=<%=Pagina.DEFAULT_PRIMER_REGISTRO%>');
}

function cargarComboTemas(){
   enviar('OCAPDudasTutores.do?accion=cargarComboTemas&CTipoDuda='+ document.forms[0].CTipoDuda.value +'&vuelta=DudasUsuario');
}
</script>

<div class="contenido contenidoFaseIII">

	<html:form action="/OCAPDudasTutores.do">
		<h2 class="tituloContenido">GESTI&Oacute;N DE DUDAS</h2>
		<h3 class="subTituloContenido">Listado de dudas</h3>

		<div class="lineaBajaM"></div>
		<div class="espaciador"></div>

		<html:hidden property="BContestado" />
		<html:hidden property="CExpId" />

		<fieldset>
			<legend class="tituloLegend"> Buscador </legend>

			<div class="cuadroFieldset formulario formularioDudasUsuario">
				<div class="todo">
					<label class="nombreLargo" for="idVTema"> Tema: </label>

					<html:select styleClass="cuadroTodoP" name="OCAPDudasTutoresForm"
						property="CTemaId">
						<html:option value="">Seleccione...</html:option>
						<% String grupoTipoDudaAnt = "";%>
						<logic:iterate id="tema" name="OCAPDudasTutoresForm"
							property="listaTemas" type="OCAPDudasTutoresOT">
							<%if (!grupoTipoDudaAnt.equals(tema.getCTipoDuda())) {
                              if (!"".equals(grupoTipoDudaAnt)) { %>
							</optgroup>
							<% } 
                                 grupoTipoDudaAnt = tema.getCTipoDuda();
                                 if (Constantes.TIPO_DUDA_METODOLOGICA.equals(grupoTipoDudaAnt)) { %>
							<optgroup label="Metodológicas">
								<% } else {%>
								<optgroup label="Técnicas">
									<% } %>
									<% } %>
									<html:option value="<%=Long.toString(tema.getCTemaId())%>">
										<bean:write name="tema" property="DTema" />
									</html:option>
						</logic:iterate>
						<% if (!"".equals(grupoTipoDudaAnt)) { %>
						</optgroup>
						<% } %>
					</html:select>
				</div>
				<div class="todoFecha">
					<label class="nombreLargo" for="idVApell1"> Fecha de
						env&iacute;o entre: </label>
					<html:text name="OCAPDudasTutoresForm" property="FInicio"
						maxlength="16" />
					<a class="iconoCalendario"
						href='javascript:show_Calendario("OCAPDudasTutoresForm", "FInicio", document.forms[0].FInicio.value);'><html:img
							src="imagenes/calendario.gif" border="0" alt="Calendario" /></a> <label
						class="claseY" for="idVApell1"> y : </label>
					<html:text name="OCAPDudasTutoresForm" property="FFin"
						maxlength="16" />
					<a class="iconoCalendario"
						href='javascript:show_Calendario("OCAPDudasTutoresForm", "FFin", document.forms[0].FFin.value);'><html:img
							src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
				</div>
				<html:errors property="fInicio" />
				<html:errors property="fFin" />
				<div class="todoFecha">
					<label class="nombreLargo" for="idVFRespueta1"> Fecha de
						respuesta entre: </label>
					<html:text name="OCAPDudasTutoresForm" property="FInicioRespuesta"
						maxlength="16" />
					<a class="iconoCalendario"
						href='javascript:show_Calendario("OCAPDudasTutoresForm", "FInicioRespuesta", document.forms[0].FInicioRespuesta.value);'><html:img
							src="imagenes/calendario.gif" border="0" alt="Calendario" /></a> <label
						class="claseY" for="idVApell1"> y : </label>
					<html:text name="OCAPDudasTutoresForm" property="FFinRespuesta"
						maxlength="16" />
					<a class="iconoCalendario"
						href='javascript:show_Calendario("OCAPDudasTutoresForm", "FFinRespuesta", document.forms[0].FFinRespuesta.value);'><html:img
							src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
				</div>
				<html:errors property="fInicioRespuesta" />
				<html:errors property="fFinRespuesta" />
				<div class="todo formatoFecha">Las fechas deben estar en
					formato dd/mm/aaaa [hh:mm]</div>
			</div>

			<div class="botonesPagina colocaBotonBusc">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:enviar('OCAPDudasTutores.do?accion=buscarDudasUsuario');">
							<img src="imagenes/imagenes_ocap/dobleFlecha.gif"
							class="colocaImgPrint" alt="Buscar" /> <span> Buscar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:limpiar();"> <img
							src="imagenes/imagenes_ocap/aspa_roja.gif" class="colocaImgPrint"
							alt="Limpiar" /> <span> Limpiar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>
		</fieldset>

		<div class="espaciador"></div>

		<logic:equal name="OCAPDudasTutoresForm" property="jspInicio"
			value="false">
			<fieldset class="normales">
				<legend class="tituloLegend"> Listado </legend>

				<logic:present name="sinDatos">
					<div class="textoNoResultados">
						<bean:message key="listado.noDudas" />
					</div>
				</logic:present>

				<table border="0" class="resultadosFaseIII tablaDudas">
					<logic:notPresent name="sinDatos">
						<tr>
							<th></th>
							<th id="cTemaId"><a href="javascript:buscar('d_tema');"
								title="Ordenar por tema">Tema <logic:equal
										name="<%=Constantes.ORDENACION%>" value="d_tema">
										<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
											value="0">
											<html:img src="imagenes/imagenes_ocap/flechaBlancaDes.gif"
												border="0" alt="Descendente" />
										</logic:equal>
										<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
											value="1">
											<html:img src="imagenes/imagenes_ocap/flechaBlancaAsc.gif"
												border="0" alt="Ascendente" />
										</logic:equal>
									</logic:equal>
							</a></th>
							<th id="fRecepcion"><a
								href="javascript:buscar('f_recepcion');"
								title="Ordenar por fecha de envío">Fecha env&iacute;o <logic:equal
										name="<%=Constantes.ORDENACION%>" value="f_recepcion">
										<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
											value="0">
											<html:img src="imagenes/imagenes_ocap/flechaBlancaDes.gif"
												border="0" alt="Descendente" />
										</logic:equal>
										<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
											value="1">
											<html:img src="imagenes/imagenes_ocap/flechaBlancaAsc.gif"
												border="0" alt="Ascendente" />
										</logic:equal>
									</logic:equal>
							</a></th>
							<th id="fEnvioContestacion"><a
								href="javascript:buscar('f_envio_respuesta');"
								title="Ordenar por fecha de respuesta">Fecha respuesta <logic:equal
										name="<%=Constantes.ORDENACION%>" value="f_envio_respuesta">
										<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
											value="0">
											<html:img src="imagenes/imagenes_ocap/flechaBlancaDes.gif"
												border="0" alt="Descendente" />
										</logic:equal>
										<logic:equal name="<%=Constantes.ORDENACION_CONTADOR%>"
											value="1">
											<html:img src="imagenes/imagenes_ocap/flechaBlancaAsc.gif"
												border="0" alt="Ascendente" />
										</logic:equal>
									</logic:equal>
							</a></th>
						</tr>
						<logic:present name="listados" property="elementos">
							<% int contador =0; %>
							<logic:iterate id="elemento" name="listados" property="elementos"
								type="OCAPDudasTutoresOT">
								<% contador++; %>
								<% if ((contador%2)==0) { %>
								<tr class="fondo1">
									<% } else { %>
								
								<tr class="fondo2">
									<% } %>
									<td><logic:notEmpty name="elemento"
											property="FEnvioContestacion">
											<logic:equal name="elemento" property="BLeido"
												value="<%=Constantes.NO%>">
												<img class="imagenSobre"
													src="imagenes/imagenes_ocap/sobre.gif" title="Nuevo"
													alt="Nuevo" />
											</logic:equal>
										</logic:notEmpty></td>
									<td class="separador" headers="cTemaId"><a
										href="OCAPDudasTutores.do?accion=detalleDuda&cDudaIdS=<bean:write name="elemento" property="CDudaId"/>">
											<bean:write name="elemento" property="DTema" />
									</a></td>
									<td class="separador" headers="fRecepcion"><bean:write
											name="elemento" property="FRecepcion" /></td>
									<td class="" headers="fEnvioContestacion"><logic:notEmpty
											name="elemento" property="FEnvioContestacion">
											<bean:write name="elemento" property="FEnvioContestacion" />
										</logic:notEmpty> <logic:empty name="elemento" property="FEnvioContestacion">
                                       Pendiente
                                    </logic:empty></td>
								</tr>
							</logic:iterate>
						</logic:present>
					</logic:notPresent>
				</table>

				<logic:present name="listados" property="elementos">
					<div class=""><%@ include file="/comun/paginacion.jsp"%></div>
				</logic:present>

			</fieldset>
		</logic:equal>
	</html:form>
</div>


