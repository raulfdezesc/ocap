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

<script language="JavaScript">
function limpiar(){
   document.forms[0].DApellidosTutor.value = '';
   document.forms[0].DNombreTutor.value='';
   document.forms[0].CDni.value='';
   document.forms[0].CTipoTutor.value='';
   document.forms[0].CTipoTutor[0].checked=false;
   document.forms[0].CTipoTutor[1].checked=false;
   document.forms[0].CTipoDuda.value='';
   document.forms[0].CTipoDuda[0].checked=false;
   document.forms[0].CTipoDuda[1].checked=false;
}

function eliminarTutor(nombre, cTutorId, nDudasPendientes){
   if (nDudasPendientes>0){
      alert(nombre+' no puede eliminarse por tener dudas pendientes. Las dudas deben ser resultas o reasignarse a otro tutor.');
   } else {
      if (confirm('Va a eliminar permanentemente a '+nombre+'. \u00BFDesea continuar?'))
         enviar('OCAPDudasTutores.do?accion=bajaTutor&cTutorIdS='+cTutorId);
   }
}

function buscar(campo){
   enviar('OCAPDudasTutores.do?accion=buscarTutores&<%=Constantes.ORDENACION%>='+campo+'&<%=Pagina.PRIMER_REGISTRO_PARAMETER_NAME%>=<%=Pagina.DEFAULT_PRIMER_REGISTRO%>');
}
</script>

<div class="contenido contenidoFaseIII">

	<html:form action="/OCAPDudasTutores.do">
		<h2 class="tituloContenido">GESTI&Oacute;N DE TUTORES GU&Iacute;A
		</h2>
		<h3 class="subTituloContenido">Listado de tutores - gu&iacute;a</h3>

		<fieldset>
			<legend class="tituloLegend"> Buscador </legend>

			<div class="cuadroFieldset formulario">
				<div class="unMedio">
					<label for="idVApell1"> Apellidos: </label>
					<html:text name="OCAPDudasTutoresForm" property="DApellidosTutor"
						maxlength="30" />
					<html:errors property="dNombreTutor" />
				</div>
				<div class="unMedio">
					<label for="idVNombre"> Nombre: </label>
					<html:text name="OCAPDudasTutoresForm" property="DNombreTutor"
						maxlength="30" />
					<html:errors property="dApellidosTutor" />
				</div>
				<div class="unMedio saltoL">
					<label for="idVApell1"> NIF/NIE: </label>
					<html:text name="OCAPDudasTutoresForm" property="CDni"
						maxlength="11" />
				</div>
				<div class="dosTercios">
					<label for="idVApell1"> Tipo: </label>
					<html:errors property="cTipoDuda" />
					<html:radio styleClass="radio" name="OCAPDudasTutoresForm"
						property="CTipoTutor"
						value="<%=Integer.toString(Constantes.TIPO_TUTOR_GUIA_1)%>" />
					Tutor-gu&iacute;a Est&aacute;ndar
					<html:radio styleClass="radio" name="OCAPDudasTutoresForm"
						property="CTipoTutor"
						value="<%=Integer.toString(Constantes.TIPO_TUTOR_GUIA_2)%>" />
					Tutor-gu&iacute;a Organizador
				</div>
				<div class="todo">
					<label class="nombreLargo" for="idVApell1"> Dudas que va a
						resolver: </label>
					<html:errors property="cTipoDuda" />
					<html:radio styleClass="radio" name="OCAPDudasTutoresForm"
						property="CTipoDuda"
						value="<%=Constantes.TIPO_DUDA_METODOLOGICA%>" />
					Metodol&oacute;gicas
					<html:radio styleClass="radio" name="OCAPDudasTutoresForm"
						property="CTipoDuda" value="<%=Constantes.TIPO_DUDA_TECNICA%>" />
					T&eacute;cnicas
				</div>
			</div>

			<div class="botonesPagina colocaBotonBusc">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="javascript:enviar('OCAPDudasTutores.do?accion=buscarTutores');">
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
					<div class="textoCaracteres">
						<div class="espaciador"></div>
						<bean:message key="listado.noDatos" />
					</div>
				</logic:present>

				<table border="0" class="resultadoFormularioTutoresGuia">
					<logic:notPresent name="sinDatos">
						<tr>
							<th id="dApellidos"><a
								href="javascript:buscar('d_apellidos');"
								title="Ordenar por apellidos del tutor">Apellidos <logic:equal
										name="<%=Constantes.ORDENACION%>" value="d_apellidos">
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
							<th id="dNombre"><a href="javascript:buscar('d_nombre');"
								title="Ordenar por nombre del tutor">Nombre <logic:equal
										name="<%=Constantes.ORDENACION%>" value="d_nombre">
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
							<th class="tipoTutor" id="cTipotutor"><a
								href="javascript:buscar('c_tipo_tutor');"
								title="Ordenar por tipo de tutor">Tipo tutor <logic:equal
										name="<%=Constantes.ORDENACION%>" value="c_tipo_tutor">
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
							<th class="tipoDuda" id="cTipoDuda"><a
								href="javascript:buscar('c_tipo_duda');"
								title="Ordenar por tipo de dudas que resuelve el tutor">Tipo
									dudas <logic:equal name="<%=Constantes.ORDENACION%>"
										value="c_tipo_duda">
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
							<th class="estado" id="bActivado">Estado</th>
							<th class="accion" colspan="3">Acci&oacute;n</th>
						</tr>
						<logic:present name="listados" property="elementos">
							<% int contador =0; %>
							<logic:iterate id="elemento" name="listados" property="elementos">
								<% contador++; %>
								<% if ((contador%2)==0) { %>
								<tr class="fondo1">
									<% } else { %>
								
								<tr class="fondo2">
									<% } %>
									<td class="datos separador" headers="dApellidos"><a
										href="OCAPDudasTutores.do?accion=detalleTutor&cTutorIdS=<bean:write name="elemento" property="CTutorId"/>&volverBuscador=<%=Constantes.SI%>">
											<bean:write name="elemento" property="DApellidosTutor" />
									</a></td>
									<td class="datos separador" headers="dNombre"><a
										href="OCAPDudasTutores.do?accion=detalleTutor&cTutorIdS=<bean:write name="elemento" property="CTutorId"/>&volverBuscador=<%=Constantes.SI%>">
											<bean:write name="elemento" property="DNombreTutor" />
									</a></td>
									<td class="separador" headers="cTipoTutor"><logic:equal
											name="elemento" property="CTipoTutor"
											value="<%=Integer.toString(Constantes.TIPO_TUTOR_GUIA_1)%>"> 
                                       Est&aacute;ndar
                                    </logic:equal> <logic:notEqual name="elemento"
											property="CTipoTutor"
											value="<%=Integer.toString(Constantes.TIPO_TUTOR_GUIA_1)%>"> 
                                       Organizador
                                    </logic:notEqual></td>
									<td class="separador" headers="cTipoDuda"><logic:equal
											name="elemento" property="CTipoDuda"
											value="<%=Constantes.TIPO_DUDA_METODOLOGICA%>">
                                       Metodol&oacute;gicas
                                    </logic:equal> <logic:equal name="elemento"
											property="CTipoDuda"
											value="<%=Constantes.TIPO_DUDA_TECNICA%>">
                                       T&eacute;cnicas
                                    </logic:equal></td>
									<td class="separador" headers="bActivado"><logic:equal
											name="elemento" property="BActivado"
											value="<%=Constantes.SI%>">                                       
                                       Activado                                        
                                    </logic:equal> <logic:notEqual name="elemento"
											property="BActivado" value="<%=Constantes.SI%>">                                       
                                       Desactivado                                       
                                    </logic:notEqual></td>
									<td class="icono"><logic:equal name="elemento"
											property="BActivado" value="<%=Constantes.SI%>">
											<a
												href="OCAPDudasTutores.do?accion=activarTutor&activar=<%=Constantes.NO%>&cTutorIdS=<bean:write name="elemento" property="CTutorId"/>">
												<img src="imagenes/imagenes_ocap/desactivacion.gif"
												title="Desactivar tutor - guía" alt="Desactivar tutor-guía">
											</a>
										</logic:equal> <logic:notEqual name="elemento" property="BActivado"
											value="<%=Constantes.SI%>">
											<a
												href="OCAPDudasTutores.do?accion=activarTutor&activar=<%=Constantes.SI%>&cTutorIdS=<bean:write name="elemento" property="CTutorId"/>&tipoDuda=<bean:write name="elemento" property="CTipoDuda" />">
												<img src="imagenes/imagenes_ocap/activacion.gif"
												title="Activar tutor - guía" alt="Activar tutor-guía">
											</a>
										</logic:notEqual></td>
									<td class="icono"><a
										href="OCAPDudasTutores.do?accion=irEditarTutor&cTutorIdS=<bean:write name="elemento" property="CTutorId"/>">
											<img src="imagenes/imagenes_ocap/icono_modificar.gif"
											alt="Modificar" title="Modificar" />
									</a></td>
									<td class="icono"><a
										href="javascript:eliminarTutor('<bean:write name="elemento" property="DNombreTutor"/> <bean:write name="elemento" property="DApellidosTutor"/>', '<bean:write name="elemento" property="CTutorId"/>', '<bean:write name="elemento" property="NDudasPendientes"/>');">
											<img class="imagenIzda"
											src="imagenes/imagenes_ocap/icono_eliminar.gif"
											title="Eliminar" alt="Eliminar">
									</a></td>
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
