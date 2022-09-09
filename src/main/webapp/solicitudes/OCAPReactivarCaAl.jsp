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
	src="<html:rewrite page='/javascript/formularios.js'/>"
	charset="windows-1252"></script>

<script language="JavaScript" type="text/javascript">
function limpiar(){
   document.forms[0].codigoId.value = '';
}

function validarBuscador(formu){
   var cadena = formu.codigoId.value;
   cadena = trim(cadena);
   if(cadena=='') {
      alert('Debe rellenar el campo \'C\u00F3digo\'.');
      return false;
   }else{
      if(LetrasYNumeros(formu.codigoId)){
         formu.codigoId.value = formu.codigoId.value.toUpperCase();
         if (formu.codigoId.value.length < 9) {
            alert('El C\u00F3digo debe tener el formato <%=Constantes.CODIGO_EVALUADO%>xxxxxx donde xxxxxx son 6 d\u00EDgitos.');
            return false;
         } else if (formu.codigoId.value.toUpperCase().indexOf('<%=Constantes.CODIGO_EVALUADO%>') != 0) {
            alert('El C\u00F3digo debe tener el formato <%=Constantes.CODIGO_EVALUADO%>xxxxxx donde xxxxxx son 6 d\u00EDgitos.');
            return false;
         } else {
            formu.codigoId.value = formu.codigoId.value.substring('<%=Constantes.CODIGO_EVALUADO%>'.length);
            if (!soloNumeros(formu.codigoId)) {
               formu.codigoId.value = cadena;
               alert('El C\u00F3digo debe tener el formato <%=Constantes.CODIGO_EVALUADO%>xxxxxx donde xxxxxx son 6 d\u00EDgitos.');
               return false;
            } else
               formu.codigoId.value = cadena;
         }
      } else{
         alert('El campo \'C\u00F3digo\' contiene caracteres no permitidos.');
         return false;
      }
   }
   return true;
}

function buscar(){
   if(validarBuscador(document.forms[0])){
      enviar('OCAPSolicitudes.do?accion=reactivarCA');
   }
}

function noIntro(evt) {
   //se admiten: numeros y "." 
   var nav4 = window.Event ? true : false;
   var key = nav4 ? evt.which : evt.keyCode;
   if (key == null)
      key = nav4 ? evt.keyCode : evt.which;
   if (key ==13 )
      return false;
   else return true;
}

</script>

<%--div class="contenido contenidoFaseIII"--%>
<div class="contenido">
	<div class="contenidoListadoAspirantesGCP listadoFaseIII">

		<html:form action="/OCAPSolicitudes.do">

			<h2 class="tituloContenido">USUARIOS CON OPCI&Oacute;N A CARRERA
				PROFESIONAL</h2>
			<h3 class="subTituloContenido">Reactivar Fase de
				Desempe&ntilde;o</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<fieldset>
				<legend class="tituloLegend"> Buscador </legend>

				<div class="cuadroFieldset">
					<label for="idVApell1"> C&oacute;digo: <html:text
							property="codigoId" styleClass="recuadroM colocaApellidos"
							maxlength="9" onkeypress="return noIntro(event);" />
					</label>
				</div>

				<div class="botonesPagina colocaBotonBusc">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:buscar();" tabindex="61"> <img
								src="imagenes/imagenes_ocap/dobleFlecha.gif"
								class="colocaImgPrint" alt="Reactivar" /> <span>
									Reactivar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:limpiar();" tabindex="61"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif"
								class="colocaImgPrint" alt="Limpiar" /> <span> Limpiar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</fieldset>

			<logic:present name="noExisteExpediente">
				<logic:equal name="noExisteExpediente" value="<%=Constantes.SI%>">
					<fieldset>
						<div class="cuadroFieldset">
							<div class="cuadroListadoAspirantesFase">
								<bean:message key="documento.noExpedientes" />
							</div>
						</div>
					</fieldset>
				</logic:equal>
			</logic:present>

			<logic:present name="expedienteNoAceptadoMC">
				<logic:equal name="expedienteNoAceptadoMC"
					value="<%=Constantes.SI%>">
					<fieldset>
						<div class="cuadroFieldset">
							<div class="cuadroListadoAspirantesFase">
								<bean:message key="documento.expedienteNoAceptadoMC" />
							</div>
						</div>
					</fieldset>
				</logic:equal>
			</logic:present>

			<logic:present name="expedienteItinerarioNoElegido">
				<logic:equal name="expedienteItinerarioNoElegido"
					value="<%=Constantes.SI%>">
					<fieldset>
						<div class="cuadroFieldset">
							<div class="cuadroListadoAspirantesFase">
								<bean:message key="documento.expedienteItinerarioNoElegido" />
							</div>
						</div>
					</fieldset>
				</logic:equal>
			</logic:present>

			<div class="espaciador"></div>
		</html:form>

	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->
