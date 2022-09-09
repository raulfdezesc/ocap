<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.util.Utilidades"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.documentos.OCAPDocumentosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.respuestas.OCAPRespuestasOT"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/fechas.js'/>"></script>

<script language="javascript">

function habilitarCreditos(bHabilitar){
   if (bHabilitar=='Y') {
      document.forms[0].NCreditosEvaluados.readOnly=false;
   } else {
      document.forms[0].NCreditosEvaluados.readOnly=true;
   }
}

function esNumeroDecimal(campo){
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

function volver(){
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

function guardarEvaluacion(){
   if (validar()){
      if(confirm('Ya no podr\u00E1 modificar su an\u00E1lisis. \u00BFDesea continuar?')){
         enviar('OCAPCuestionarios.do?accion=insertarAnalisisCuestionario');
      }
   }
}

function validar(){
   if (!document.forms[0].BAcuerdo[0].checked && !document.forms[0].BAcuerdo [1].checked) {
      alert('Debe indicar si est\u00E1 o no de acuerdo con la auto-evaluaci\u00F3n.');
      return false;
   }
   if (document.forms[0].BAcuerdo [1].checked && document.forms[0].ARazon.value == ''){
      alert('Debe especificar la raz\u00F3n de su decisi\u00F3n.');
      return false;
   }
   if (document.forms[0].BAuditable.value=='<%=Constantes.SI%>' && !document.forms[0].BAuditoria[0].checked && !document.forms[0].BAuditoria[1].checked){
      alert('Debe indicar si propone Auditor\u00EDa de Registro o no.');
      return false;
   }
   if (document.forms[0].BAuditable.value=='<%=Constantes.SI%>' && document.forms[0].BAuditoria[0].checked &&
      document.forms[0].ATipoRegistro.value=='' ){
      alert('Debe rellenar el campo \"Tipo de Registro\".');
      return false;
   }
   if (document.forms[0].BAuditable.value=='<%=Constantes.SI%>' && document.forms[0].BAuditoria[0].checked &&
      document.forms[0].ADocumento.value=='' ){
      alert('Debe rellenar el campo \"Documento\".');
      return false;
   }
   return true;
}

function deshabilitar(){
   document.forms[0].BAcuerdo[0].disabled = true;
   document.forms[0].BAcuerdo[1].disabled = true;
   document.forms[0].ARazon.disabled = true;
   document.forms[0].AComentarios.disabled = true;
   if (document.forms[0].BAcuerdo[1].checked)
      mostrarRazones('Y');
   if (document.forms[0].BAuditable.value=='Y'){
      document.forms[0].BAuditoria[0].disabled = true;
      document.forms[0].BAuditoria[1].disabled = true;
      if (document.forms[0].BAuditoria[0].checked) 
         mostrarAuditoria('Y');
      document.forms[0].ATipoRegistro.disabled = true;
      document.forms[0].ADocumento.disabled = true;
      document.forms[0].ANHistoria1.disabled = true;
      document.forms[0].ANHistoria2.disabled = true;
      document.forms[0].ANHistoria3.disabled = true;
   }
}


function mostrarRazones(param) {
   if (param=='Y'){
      document.getElementById("razones").style.display='';
      document.getElementById("razones").style.visibility='visible';
   } else {
      document.getElementById("razones").style.display='none';
      document.getElementById("razones").style.visibility='hidden';
   }
}

function mostrarAuditoria(param) {
   if (param=='Y'){
      document.getElementById("auditorias").style.display='';
      document.getElementById("auditorias").style.visibility='visible';
   } else {
      document.getElementById("auditorias").style.display='none';
      document.getElementById("auditorias").style.visibility='hidden';
   }
}

</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoProcedimiento evaluacionIEC">

		<html:form action="/OCAPCuestionarios.do">
			<html:hidden name="OCAPCuestionariosForm" property="cadenaRespuestas" />
			<h2 class="tituloContenido">
				<bean:write name="OCAPCuestionariosForm" property="DArea" />
				AN&Aacute;LISIS DEL
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
                        No.
                     </logic:equal> <logic:notEqual name="tamanoListaDocs" value="0">
                        S&iacute;.
                     </logic:notEqual></li>
						<li><span>Resultado de la evaluaci&oacute;n. Tras el
								an&aacute;lisis estoy:</span>
							<ul>
								<li><span>De acuerdo con la auto-evaluaci&oacute;n
										de este apartado</span> <html:radio styleClass="acuerdo"
										name="OCAPCuestionariosForm" property="BAcuerdo"
										value="<%=Constantes.SI%>" onclick="mostrarRazones('N');" /></li>
								<li><span>En desacuerdo con la
										auto-evaluaci&oacute;n de este apartado</span> <html:radio
										styleClass="desacuerdo" name="OCAPCuestionariosForm"
										property="BAcuerdo" value="<%=Constantes.NO%>"
										onclick="mostrarRazones('Y');" /></li>
							</ul></li>
						<li id="razones" style="display: none; visibility: hidden"><span>Especificar
								raz&oacute;n:</span><br /> <html:textarea styleClass="cajaTexto"
								name="OCAPCuestionariosForm" property="ARazon" rows="3%"
								cols="95%" onkeypress="javascript:return longMaxRazon(this);"
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
						<!-- si no es celador ni aux.administrativo-->
						<html:hidden name="OCAPCuestionariosForm" property="BAuditable" />
						<logic:equal name="OCAPCuestionariosForm" property="BAuditable"
							value="<%=Constantes.SI%>">
							<li><span>Propuesta de Auditor&iacute;a de Registro:</span><br />
								<div class="propuestaAuditoria">
									<span>S&iacute;</span>
									<html:radio name="OCAPCuestionariosForm" property="BAuditoria"
										value="<%=Constantes.SI%>" onclick="mostrarAuditoria('Y');" />
									&nbsp;&nbsp; <span>No</span>
									<html:radio name="OCAPCuestionariosForm" property="BAuditoria"
										value="<%=Constantes.NO%>" onclick="mostrarAuditoria('N');" />
									<br />
									<div id="auditorias" style="display: none; visibility: hidden">
										<span>Tipo de registro:</span>
										<html:text name="OCAPCuestionariosForm"
											property="ATipoRegistro" maxlength="300"
											styleClass="recuadroAudito tipoRegistro" />
										<br /> <span>Documento:</span> <br />
										<html:text name="OCAPCuestionariosForm" property="ADocumento"
											maxlength="300" styleClass="recuadroAudito tipoRegistro" />
										<br />
										<br /> <span>N&ordm; H&ordf; Cl. de referencia 1</span>
										<html:text name="OCAPCuestionariosForm" property="ANHistoria1"
											maxlength="50" styleClass="recuadroAudito historialClinic1" />
										<span>2</span>
										<html:text name="OCAPCuestionariosForm" property="ANHistoria2"
											maxlength="50" styleClass="recuadroAudito historialClinic" />
										<span>3</span>
										<html:text name="OCAPCuestionariosForm" property="ANHistoria3"
											maxlength="50" styleClass="recuadroAudito historialClinic" />
									</div>
								</div></li>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="BAuditable"
							value="<%=Constantes.SI%>">
							<html:hidden name="OCAPCuestionariosForm" property="BAuditoria"
								value="<%=Constantes.NO%>" />
						</logic:notEqual>
					</ul>
				</div>
			</fieldset>

			<div class="limpiadora"></div>
			<div class="espaciador"></div>

			<logic:equal name="OCAPCuestionariosForm" property="BEvaluado"
				value="<%=Constantes.SI%>">
				<script language="javascript">
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
			</logic:equal>
			<logic:notEqual name="OCAPCuestionariosForm" property="BEvaluado"
				value="<%=Constantes.SI%>">
				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:guardarEvaluacion();"> <img
								src="imagenes/imagenes_ocap/action_forward.gif"
								class="colocaImgPrint" alt="Guardar Evaluación" /> <span>
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
			</logic:notEqual>

		</html:form>
	</div>
	<%-- /fin de ContenidoTextoProcedimiento --%>
</div>
<%-- /Fin de Contenido --%>

<div class="limpiadora"></div>
