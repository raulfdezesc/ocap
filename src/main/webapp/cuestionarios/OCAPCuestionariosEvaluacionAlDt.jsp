<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.documentos.OCAPDocumentosOT"%>
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

<script language="javascript" type="text/javascript">
function verProposicion(bVer) {
   if (bVer=='Y') {
      document.getElementById('proposicion').style.display='';
      document.getElementById('proposicion').style.visibility='visible';
      document.forms[0].BProposicion.checked = true;
      habilitarCreditos('Y');
   } else {
      document.getElementById('proposicion').style.display='none';
      document.getElementById('proposicion').style.visibility='hidden';
       habilitarCreditos('N');
   }
}

function habilitarCreditos(bHabilitar) {
   if (bHabilitar=='Y') {
      document.forms[0].NCreditosEvaluados.readOnly=false;
   } else {
      document.forms[0].NCreditosEvaluados.readOnly=true;
   }
}

function esNumeroDecimal(campo) {
   var aux=campo.value;
   var contador = 0;
   var aux2='';
   while (aux.indexOf('.')!=-1) {
      if (contador<2) {
         aux2= aux2+''+aux.substring(0,aux.indexOf('.')+1);
      }
      aux=aux.substring(aux.indexOf('.')+1);
      contador++;
   }
   if (contador > 1) {
      alert('El n\u00famero decimal s\u00f3lo puede contener un \'.\'');
      campo.value=aux2.substring(0,aux2.length-1);
      return false;
   } else return true;
}

function volver() {
   enviar('OCAPCuestionarios.do?accion=irListar&expId=<bean:write name="OCAPCuestionariosForm" property="CExpId" />');
}

var LONG_MAX_RAZON= <%=Constantes.LONG_RAZON_EVALUADOR%>;
function longMaxRazon(campo) {
   if(campo.value.length <= LONG_MAX_RAZON) {
      return true;
   } else {
      campo.value = campo.value.substring(0,LONG_MAX_RAZON);
      return false;
   }
}
var LONG_MAX_COMENTARIOS= <%=Constantes.LONG_COMENTARIOS_EVALUADOR%>;
function longMaxComentarios(campo) {
   if(campo.value.length <= LONG_MAX_COMENTARIOS) {
      return true;
   } else {
      campo.value = campo.value.substring(0,LONG_MAX_COMENTARIOS);
      return false;
   }
}

function guardarEvaluacion() {
   if (validar()) {
      //if(confirm('Ya no podr\u00E1 modificar su evaluaci\u00F3n. \u00BFDesea continuar?')){
         enviar('OCAPCuestionarios.do?accion=insertarEvaluacionCuestionario');
      //}
   }
}

function validar() {
   if (!document.forms[0].BAcuerdo[0].checked && !document.forms[0].BAcuerdo [1].checked) {
      alert('Debe indicar si est\u00E1 o no de acuerdo con la auto-evaluaci\u00F3n.');
      return false;
   }
  
   if (document.forms[0].ARazon.value == ''){
      alert('Debe especificar la raz\u00F3n de su decisi\u00F3n.');
      return false;
   }
   if (document.forms[0].NCreditosEvaluados.value == ''){
      alert('Debe indicar la ponderaci\u00F3n final otorgada.');
      return false;
   }
   if (!esNumeroDecimal(document.forms[0].NCreditosEvaluados)) {
      return false;
   }
   if (eval(document.forms[0].NCreditosEvaluados.value) > eval(document.forms[0].NCreditosNecesarios.value)){
      alert('Los cr\u00E9ditos asignados en la Ponderaci\u00F3n Final no pueden ser superiores a los cr\u00E9ditos m\u00E1ximos.');
      return false;
   }
   return true;
}

function verDesacuerdo() {
   if (document.forms[0].BAcuerdo[1].checked)
      verProposicion('Y');
}

function deshabilitar() {
   verDesacuerdo();
   document.forms[0].BAcuerdo[0].disabled = true;
   document.forms[0].BAcuerdo[1].disabled = true;
   document.forms[0].BProposicion[0].disabled = true;
   document.forms[0].BProposicion[1].disabled = true;
   document.forms[0].ARazon.disabled = true;
   document.forms[0].AComentarios.disabled = true;
}

</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoProcedimiento evaluacionIEC">
		<html:form action="/OCAPCuestionarios.do">
			<html:hidden name="OCAPCuestionariosForm" property="cadenaRespuestas" />
			<h2 class="tituloContenido">
				<bean:write name="OCAPCuestionariosForm" property="DArea" />
				EVALUACI&Oacute;N DEL IEC PARA EL
				<bean:write name="OCAPCuestionariosForm" property="DAreaLargo" />
			</h2>
			<h3 class="subTituloContenido">
				<bean:write name="OCAPCuestionariosForm" property="DTitulo" />
				<br /> <br />
				<bean:write name="OCAPCuestionariosForm"
					property="DNombreItinerario" />
				<br />
				<br /> <span><bean:write name="OCAPCuestionariosForm"
						property="codigoId" /></span>
			</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<html:hidden property="CExpId" />
			<html:hidden property="CCuestionarioId" />
			<h4 class="subTituloContenidoItinerario">
				<bean:write name="OCAPCuestionariosForm" property="DArea" />
				<bean:write name="OCAPCuestionariosForm" property="DNombre" />
				-
				<bean:write name="OCAPCuestionariosForm" property="DNombreLargo" />
			</h4>
			<%-- Preguntas --%>
			<fieldset>
				<div class="cuadroProcedimiento">
					<div class="espaciador"></div>
					<ul class="listadoResumen">
						<li><span>Ponderaci&oacute;n m&aacute;xima posible: </span><span
							class="ponderacionMaxima"><bean:write
									name="OCAPCuestionariosForm" property="NCreditosNecesarios" /></span></li>
						<html:hidden name="OCAPCuestionariosForm"
							property="NCreditosNecesarios" />
						<li><span>Ponderaci&oacute;n obtenida en la
								auto-evaluaci&oacute;n:</span> <span class="ponderacionObtenida"><bean:write
									name="OCAPCuestionariosForm" property="NCreditosObtenidos" /></span></li>
						<li><span>Evidencia aportada:</span> <bean:size
								id="tamanoListaDocs" name="OCAPCuestionariosForm"
								property="listaDocumentos" /> <logic:equal
								name="tamanoListaDocs" value="0">
                        No hay evidencias.
                     </logic:equal> <logic:notEqual name="tamanoListaDocs" value="0">
								<logic:iterate id="lDocumentos" name="OCAPCuestionariosForm"
									property="listaDocumentos" type="OCAPDocumentosOT">
									<a
										href="OCAPDocumentos.do?accion=abrirDocumento&expId=<bean:write name="OCAPCuestionariosForm" property="CExpId"/>&documentoId=<bean:write name="lDocumentos" property="CDocumento_id"/>"><span>Evidencia
											documental n&ordm; <bean:write name="lDocumentos"
												property="DTitulo" />
									</span></a>&nbsp;&nbsp;
                        </logic:iterate>
							</logic:notEqual></li>
						<li><span>Resultado de la evaluaci&oacute;n. Tras el
								an&aacute;lisis estoy:</span>
							<ul>
								<li><span>De acuerdo con la auto-evaluaci&oacute;n
										de este apartado</span> <html:radio styleClass="acuerdo"
										name="OCAPCuestionariosForm" property="BAcuerdo"
										value="<%=Constantes.SI%>"
										onclick="javascript:verProposicion('N');" /></li>
								<li><span>En desacuerdo con la
										auto-evaluaci&oacute;n de este apartado</span> <html:radio
										styleClass="desacuerdo" name="OCAPCuestionariosForm"
										property="BAcuerdo" value="<%=Constantes.NO%>"
										onclick="javascript:verProposicion('Y');" />
									<div id="proposicion"
										style="display: none; visibility: hidden;">
										<span>Por ello propongo:</span><br />
										<ul>
											<li><span>Modificar la ponderaci&oacute;n de
													cr&eacute;ditos de este apartado</span> <html:radio
													styleClass="modificarPonderacion"
													name="OCAPCuestionariosForm" property="BProposicion"
													value="<%=Constantes.PROPOSICION_EVALUADOR_MODIFICAR%>"
													disabled="true" /></li>
										</ul>
									</div></li>
							</ul></li>
						<li><span>Especificar raz&oacute;n:</span><br /> <html:textarea
								styleClass="cajaTexto" name="OCAPCuestionariosForm"
								property="ARazon" rows="3%" cols="95%"
								onkeypress="javascript:return longMaxRazon(this);"
								onclick="javascript:return longMaxRazon(this);"
								onkeydown="javascript:return longMaxRazon(this);"
								onkeyup="javascript:return longMaxRazon(this);"></html:textarea>
						</li>
						<li><span>Comentarios, si procede:</span><br /> <html:textarea
								styleClass="cajaTexto" name="OCAPCuestionariosForm"
								property="AComentarios" rows="3%" cols="95%"
								onkeypress="javascript:return longMaxComentarios(this);"
								onclick="javascript:return longMaxComentarios(this);"
								onkeydown="javascript:return longMaxComentarios(this);"
								onkeyup="javascript:return longMaxComentarios(this);"></html:textarea>
						</li>
					</ul>
					<div>
						<span class="textoNegrita">PONDERACI&Oacute;N FINAL
							OTORGADA POR EL EVALUADOR: </span>
						<html:text name="OCAPCuestionariosForm" styleClass="nota"
							property="NCreditosEvaluados" readonly="true" maxlength="5"
							onkeypress="return soloNumerosDecimales(event);" />
					</div>
				</div>
			</fieldset>
			<div class="limpiadora"></div>
			<div class="espaciador"></div>
			<logic:notEqual name="OCAPCuestionariosForm" property="FEvaluacion"
				value="">
				<script language="javascript" type="text/javascript">
               deshabilitar();
            </script>
				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:volver();"> <img
								src="imagenes/imagenes_ocap/action_forward.gif"
								class="colocaImgPrint" alt="Volver" /> <span> Volver </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</logic:notEqual>
			<logic:equal name="OCAPCuestionariosForm" property="FEvaluacion"
				value="">
				<script language="javascript" type="text/javascript">
               verDesacuerdo();
            </script>
				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:guardarEvaluacion();"> <img
								src="imagenes/imagenes_ocap/action_forward.gif"
								class="colocaImgPrint" alt="Guardar Evaluaci&oacute;n" /> <span>
									Aceptar </span>
						</a>
						</span> <span class="derBoton"></span> <span class="izqBoton"></span> <span
							class="cenBoton"> <a href="javascript:volver();"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif"
								class="colocaImgPrint" alt="Cancelar" /> <span> Cancelar
							</span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</logic:equal>
		</html:form>
	</div>
	<%-- /fin de ContenidoTextoProcedimiento --%>
	<div class="espaciador"></div>
</div>
<%-- /Fin de Contenido --%>
<div class="espaciador"></div>
<div class="limpiadora"></div>
<div class="espaciador"></div>
