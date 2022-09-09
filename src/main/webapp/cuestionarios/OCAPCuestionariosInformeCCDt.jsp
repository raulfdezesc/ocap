<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript">
   function volverListado(){
      enviar('OCAPEvaluadores.do?accion=listarEvaluados&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>');
   }
   
   function informeCE(){
      enviar('OCAPCuestionarios.do?accion=generarInformeCE&expId='+document.forms[0].CExpId.value);
   } 
      
   function informeIFQS(){
      enviar('OCAPCuestionarios.do?accion=guardarInformeCC&expId='+document.forms[0].CExpId.value);
   }  
   
   var LONG_MAX_ESPECIFICACIONES= <%=Constantes.LONG_ESPECIFICACIONES_EVALUADOR%>;
   function longMaxTextarea(campo) {
      if(campo.value.length <= LONG_MAX_ESPECIFICACIONES) {
         return true;
      } else {
         campo.value = campo.value.substring(0,LONG_MAX_ESPECIFICACIONES);
         return false;
      }
   }
   
function aceptarGradoCC(){
   if (document.OCAPCuestionariosForm.BReconocimientoGrado[0].checked == false && document.OCAPCuestionariosForm.BReconocimientoGrado[1].checked == false){
      alert('Debe rellenar el campo de Reconocimiento de Grado.');
   } else {
      enviar("OCAPCuestionarios.do?accion=aceptarGrado&opcion=<%=request.getParameter("opcion")%>");
   }
}
   
</script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoAutoevaluacion">
		<div class="evaluacionEvaluador">
			<html:form action="/OCAPCuestionarios.do">
				<h2 class="tituloContenido">CERTIFICACI&Oacute;N DE LA
					FUNDACI&Oacute;N CENTRO REGIONAL DE CALIDAD Y ACREDITACI&Oacute;N
					SANITARIA DE CASTILLA Y LE&Oacute;N</h2>

				<!--<h3 class="subTituloContenido"> Evaluaci&oacute;n/Pruebas de buena pr&aacute;ctica asignados a cada &Aacute;REA </h3>-->
				<h3 class="subTituloContenido">&nbsp;</h3>
				<div class="lineaBajaM"></div>
				<div class="espaciador"></div>

				<fieldset class="informeConformidad">
					<html:hidden property="CExpId" />
					<html:hidden property="FInformeCC" />
					<div class="cuadroFieldset informesConformidad">
						<span>C&oacute;digo del Evaluado: </span>
						<bean:write name="OCAPCuestionariosForm" property="codigoId" />
						<br />
						<br />
						<% if (!Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) { %>
						<span>Nombre: </span>
						<bean:write name="OCAPCuestionariosForm" property="DNombreUsuario" />
						<span>Apellidos: </span>
						<bean:write name="OCAPCuestionariosForm" property="DApellido1" />
						<br />
						<br /> <span>DNI: </span>
						<bean:write name="OCAPCuestionariosForm" property="CDni" />
						<% } %>
						<span>Categor&iacute;a Profesional: </span>
						<bean:write name="OCAPCuestionariosForm"
							property="DCategoriaEspecialidadEvaluado" />
						<br />
						<br />
						<% if (!Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) { %>
						<span>Centro de Trabajo: </span>
						<bean:write name="OCAPCuestionariosForm"
							property="DCentrotrabajo_nombre" />
						<br />
						<br />
						<% } %>

						<p>Ha realizado el proceso de autoevaluaci&oacute;n del
							desempe&ntilde;o de la competencia profesional coordinado por la
							Fundaci&oacute;n Centro Regional de Calidad y Acreditaci&oacute;n
							Sanitaria de Castilla y Le&oacute;n (F.Q.S.), constituida como
							&oacute;rgano de evaluaci&oacute;n externa del Sistema Sanitario
							de Castilla y Le&oacute;n.</p>
						<br />
						<p>
							Ha obtenido un total de <span class="nota"><bean:write
									name="OCAPCuestionariosForm" property="NCreditosEvaluados" /></span>
							cr&eacute;ditos para la obtenci&oacute;n del Grado I solicitado
							de carrera profesional en el &aacute;rea de evaluaci&oacute;n del
							desempe&ntilde;o de la competencia profesional.
						</p>
						<p class="textoCursiva2">
							Han participado los &oacute;rganos t&eacute;cnicos de
							evaluaci&oacute;n (OTEP) de la FQS: <br />La revisi&oacute;n y
							an&aacute;lisis de la autoevaluaci&oacute;n ha sido realizada por
							un evaluador acreditado, ratificada por el Comit&eacute;
							T&eacute;cnico de Evaluaci&oacute;n correspondiente y validada
							por la Comisi&oacute;n de Evaluaci&oacute;n que realiza la
							propuesta de certificaci&oacute;n.
						</p>
						<br />
						<p>Observaciones:</p>
						<logic:notPresent name="generar">
							<div class="bordeCuadro">
								<bean:write name="OCAPCuestionariosForm"
									property="AEspecificaciones" />
							</div>
						</logic:notPresent>
						<logic:present name="generar">
							<html:textarea styleClass="bordeCuadro"
								name="OCAPCuestionariosForm" property="AEspecificaciones"
								rows="5%" cols="95%"
								onkeypress="javascript:return longMaxTextarea(this);"
								onclick="javascript:return longMaxTextarea(this);"
								onkeydown="javascript:return longMaxTextarea(this);"
								onkeyup="javascript:return longMaxTextarea(this);"></html:textarea>
						</logic:present>
						<br />
						<br />
						<p>A los efectos oportunos elevamos informe de
							certificaci&oacute;n a la Comisi&oacute;n Central de Carrera
							Profesional de SACYL (Servicio Regional de Salud de Castilla y
							Le&oacute;n).</p>
						<br />
						<br />
						<p>Secretar&iacute;a T&eacute;cnica OTEP.</p>
						<br />
						<br />
						<p>
							Fecha
							<bean:write name="OCAPCuestionariosForm" property="FInformeCC" />
						</p>
						<br />
						<br />
					</div>
				</fieldset>


				<% if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) { %>
				<fieldset class="informeConformidad">
					<legend class="tituloLegend">Validaci&oacute;n de la
						Comisi&oacute;n Central</legend>
					<div class="cuadroFieldset">
						<div class="titulosRadio titulosRadioPequenios">
							<div>Reconocimiento de Grado</div>
						</div>
						<div class="flotaIzq radioTitulos">
							<div>
								<logic:equal name="gradoCCdado" value="<%=Constantes.SI%>">
                                 S&iacute; <html:radio
										name="OCAPCuestionariosForm" property="BReconocimientoGrado"
										styleClass="opcionRadio" value="Y" disabled="true" />
                                 No <html:radio
										name="OCAPCuestionariosForm" property="BReconocimientoGrado"
										styleClass="opcionRadio" value="N" disabled="true" />
								</logic:equal>
								<logic:equal name="gradoCCdado" value="<%=Constantes.NO%>">
                                 S&iacute; <html:radio
										name="OCAPCuestionariosForm" property="BReconocimientoGrado"
										styleClass="opcionRadio" value="Y" />
                                 No <html:radio
										name="OCAPCuestionariosForm" property="BReconocimientoGrado"
										styleClass="opcionRadio" value="N" />
								</logic:equal>
							</div>
						</div>
						<div class="espaciador" />
					</div>
				</fieldset>
				<% } %>

				<div class="limpiadora"></div>
				<div class="espaciador"></div>

				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:volverListado();"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif"
								class="colocaImgPrint" alt="Volver" /> <span> Volver </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<% if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)) {%>
					<logic:equal name="gradoCCdado" value="<%=Constantes.NO%>">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:aceptarGradoCC();"> <img
									src="imagenes/imagenes_ocap/action_forward.gif"
									class="colocaImgPrint" alt="Aceptar Reconocimiento de Grado" />
									<span> Aceptar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</logic:equal>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:informeCE();"> <img
								src="imagenes/imagenes_ocap/action_forward.gif"
								class="colocaImgPrint" alt="Ver informe CE" /> <span>
									Ver informe CE </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<%}%>
					<%if(jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION)){%>
					<logic:present name="generar">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:informeIFQS();"> <img
									src="imagenes/imagenes_ocap/action_forward.gif"
									class="colocaImgPrint" alt="Generar informe IFQS" /> <span>
										Generar informe IFQS </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</logic:present>
					<%}%>
				</div>
			</html:form>
		</div>
	</div>
	<!-- /fin de ContenidoMC -->
</div>
<!-- /Fin de Contenido -->
