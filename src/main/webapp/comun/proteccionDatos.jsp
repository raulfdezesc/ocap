<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"
	charset="windows-1252"></script>
<script language="JavaScript" type="text/javascript">

function activarAceptar(){
   if (document.forms[0].acepto.checked) {
      //document.getElementById('ocultarAceptar').style.display='';
      document.getElementById('ocultarAceptar').style.visibility='visible';
   } else {
      //document.getElementById('ocultarAceptar').style.display='none';
      document.getElementById('ocultarAceptar').style.visibility='hidden';
   }
}

</script>

<div class="contenido">
	<div class="contenidoDCP1A">
		<html:form action="/OCAPUsuarios.do">
			<fieldset>
				<legend class="tituloLegend"> Protecci&oacute;n de Datos </legend>
				<div class="cuadroFieldset">
					<p class="textoNormal ajusteLOPD">
						De conformidad con lo establecido en la ley Org&aacute;nica
						3/2018, del 5 de Diciembre de Protecci&oacute;n de Datos Personales y Garant&iacute;a
						de los derechos digitales, le informamos que sus datos
						quedar&aacute;n incorporados en un fichero automatizado legalmente
						inscrito en el Registro General de Protecci&oacute;n de Datos de
						la Agencia Espa&ntilde;ola de Protecci&oacute;n de Datos cuyo
						responsable es la Gerencia Regional de Salud (en adelante GRS). <br />
						<br /> La GRS garantiza la adopci&oacute;n de medidas oportunas
						para asegurar el tratamiento confidencial de dichos datos. <br />
						<br /> Se autoriza con el presente apartado la inclusi&oacute;n
						de datos en el fichero. En caso de negarse a comunicar datos
						ser&iacute;a imposible la prestaci&oacute;n de dicho servicio.
					</p>
					<div class="textoCentrado">
						<input type="checkbox" name="acepto" value="N"
							onclick="activarAceptar()" /> <span
							class="textoNegrita textoColorM"> Est&aacute; de acuerdo </span>
						<div class="espaciadorPeq"></div>
					</div>
				</div>
			</fieldset>

			<div id="ocultarAceptar" style="visibility: hidden;">
				<div class="espaciadorPeq"></div>
				<div class="botonesPagina posicionLOPD">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <logic:equal
								name="fase" value="<%=Constantes.INICIO_MC%>">
								<a href="javascript:enviar('OCAPUsuarios.do?accion=irProteccionDatos&LOPD=Y');">
									<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
									alt="Aceptar LOPD" /> <span> Aceptar </span>
								</a>
							</logic:equal> 
							<logic:equal name="fase" value="<%=Constantes.INICIO_CA%>">
								<%-- <a href="javascript:enviar('OCAPCuestionarios.do?accion=irListar&fase=<%=Constantes.INICIO_CA%>');"> --%>
								<a	href="javascript:enviar('OCAPCuestionarios.do?accion=cambiarPerfil');">
									<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
									alt="Aceptar LOPD" /> <span> Aceptar </span>
								</a>
							</logic:equal>
						</span> <span class="derBoton"></span>
					</div>
				</div>
			</div>
		</html:form>

	</div>
	<!-- /fin de ContenidoDCP1A_PGP -->
</div>
<!-- /Fin de Contenido -->