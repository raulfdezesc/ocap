<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.actas.OCAPMiembrosComitesOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="java.util.ArrayList"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/calendario.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"></script>
<script language="JavaScript" type="text/javascript"
	src="<html:rewrite page='/javascript/combos1.js'/>"></script>
<script language="JavaScript" type="text/javascript"
	src="<html:rewrite page='/javascript/fechas.js'/>"></script>

<script language="JavaScript">

function validarObligatorios(tipoActa){
   if (document.forms[0].CGrado_id.value ==''){
      alert('Debe seleccionar un Grado.');
      return false;
   }
   if (document.forms[0].CConvocatoriaId.value ==''){
      alert('Debe seleccionar una Convocatoria.');
      return false;
   }
   if (tipoActa != 'C'){ //En el Acta de Constitucion (C) la Categoría no es obligatoria
      if (document.forms[0].CProfesional_id.value ==''){
         alert('Debe seleccionar una Categor\u00EDa.');
         return false;
      }
   }
   if (document.forms[0].DNombreApellidosPresi1.value =='' || document.forms[0].BSexoPresi1.value =='' || document.forms[0].BEnCalidadPresi1.value ==''){
      alert('Debe seleccionar al menos un Presidente');
      return false;
   }
   /*if (valorCombo(document.getElementById('jspCadenaVocales')) == ''){
      alert('Debe seleccionar al menos un Vocal');
      return false;
   }*/
   if (document.forms[0].DNombreApellidosSecre1.value =='' || document.forms[0].BSexoSecre1.value =='' || document.forms[0].BEnCalidadSecre1.value ==''){
      alert('Debe seleccionar al menos un Secretario');
      return false;
   }
   if (document.forms[0].FInicio.value ==''){
      alert('Debe indicar la Fecha de Sesi\u00F3n');
      return false;
   } else if(!esFechaCorrecta(document.forms[0].FInicio.value)){
      alert('El campo \'Fecha de sesi\u00F3n\' no es una fecha correcta.');
      return false;
   }
   if (document.forms[0].NHoraInicio.value =='' || document.forms[0].NMinutosInicio.value ==''){
      alert('Debe indicar la hora y minutos de inicio de la Sesi\u00F3n (hh:mm)');
      return false;
   }
   if (document.forms[0].NHoraFin.value =='' || document.forms[0].NMinutosFin.value ==''){
      alert('Debe indicar la hora y minutos de fin de la Sesi\u00F3n (hh:mm)');
      return false;
   }
   return true;
}

function cargarMiembros(){
   if (document.forms[0].CConvocatoriaId.value != '' && document.forms[0].CProfesionalId.value != '')
      enviar('OCAPActas.do?accion=cargarMiembros');
}

function activarEnCalidadPresi(ind){
   if (document.forms[0].presidente[i].checked == true)
      document.forms[0].BEnCalidadPresi[i].disabled=false;
   else
      document.forms[0].BEnCalidadPresi[i].disabled=true;
}

function generarPDFActaConstitucion(){
   if (validarObligatorios('C')){
      construirDTodosVocales();
      enviar("OCAPSolicitudes.do?accion=generarPDFActas&tipo=CONSTITUCION");
   }
}

function generarPDFActaReunion(){
   if (validarObligatorios('R')){
      construirDTodosVocales();
      enviar("OCAPSolicitudes.do?accion=generarPDFActas&tipo=REUNION");
   }
}

function generarPDFActaSoliAclaracion(){
   if (validarObligatorios('A')){
      construirDTodosVocales();
      enviar("OCAPSolicitudes.do?accion=generarPDFActas&tipo=SOLI_ACLARACION");
   }
}

function construirDTodosVocales(){
   document.forms[0].resumenVocales.value = valorCombo(document.getElementById('jspCadenaVocales'))
}

function inicializarVocales () {
   var resumenVocales = document.forms[0].resumenVocales.value;
   if (resumenVocales != '') {
      var cadenaVocal = resumenVocales.split('#');
      var comboVocales = document.getElementById('jspCadenaVocales');
      for (var i=0; i < cadenaVocal.length; i++) {
         var aux =  cadenaVocal[i];
         var aux2 = cadenaVocal[i]; 
         var datosVocal = aux.split('<%=Constantes.SEPARADOR_2%>');
         var valor = aux2;
         var texto = datosVocales[0];
         nueva_opcion(comboVocales,texto,valor);
      }
   }
}

function anhadirVocal() {
   formu = document.forms[0];
   comboVocales = document.getElementById('jspCadenaVocales');
   if (formu.DNombreApellidosVocal.value == ''){
      alert('Debe introducir el nombre y apellidos del vocal.');
   }else if(formu.BSexoVocal.value == ''){
      alert('Debe seleccionar el sexo del vocal.');
   }else if(formu.BEnCalidadVocal.value == ''){
      alert('Debe seleccionar si el vocal actua en calidad de titular o suplente.');
   } else{
      var valor = formu.DNombreApellidosVocal.value +'$'+ formu.BSexoVocal.value +'$'+ formu.BEnCalidadVocal.value;
      var calidadSeleccionada = formu.BEnCalidadVocal.selectedIndex;
      var texto = formu.DNombreApellidosVocal.value+'---'+ formu.BEnCalidadVocal[calidadSeleccionada].text;
      nueva_opcion(comboVocales,texto,valor);
      limpiarCamposVocales();
   }
} //Fin anhCentros

function limpiarCamposVocales() {
   formu.DNombreApellidosVocal.value = '';
   formu.BSexoVocal.value = '';
   formu.BEnCalidadVocal.value = '';
}

function editarVocal() {
	formu = document.forms[0];
   comboVocales=objeto_combo('document.forms[0].jspCadenaVocales');
   
   if (comprueba_seleccion(document.getElementById('jspCadenaVocales'))) {
    indice = document.getElementById('jspCadenaVocales').selectedIndex;
    var valor = comboVocales.options[indice].value;
    var valor_aux = valor;     // variable para operar
    var cadenaTokenizado = valor_aux.split('<%=Constantes.SEPARADOR_2%>');
   formu.DNombreApellidosVocal.value = cadenaTokenizado[0];
   formu.BSexoVocal.value =  cadenaTokenizado[1];
   formu.BEnCalidadVocal.value =  cadenaTokenizado[2];

	 combos_borrar(document.getElementById('jspCadenaVocales'));
	}
}

function inicializarVocales () {
   var resumenVocales = document.forms[0].resumenVocales.value;
   if (resumenVocales != '') {
      var cadenaVocal = resumenVocales.split('#');
      var comboVocales = document.getElementById('jspCadenaVocales');
      for (var i=0; i < cadenaVocal.length; i++) {
         var aux =  cadenaVocal[i];
         var aux2 = cadenaVocal[i]; 
         var datosVocal = aux.split('<%=Constantes.SEPARADOR_2%>');
         var valor = aux2;
         var texto = datosVocal[0];
         nueva_opcion(comboVocales,texto,valor);
      }
   }
}
</script>

<div class="contenido generarActas">
	<html:form action="/OCAPActas.do">
		<html:hidden name="OCAPActasForm" property="CGerenciaID" />
		<h2 class="tituloContenido">GENERACIÓN DE ACTAS</h2>
		<h3 class="subTituloContenido">Datos Comunes a las Actas</h3>
		<div class="lineaBajaM"></div>
		<div class="espaciador"></div>
		<fieldset>
			<legend class="tituloLegend"> Convocatoria </legend>
			<div class="cuadroFieldset">
				<label for="idVConvocatoria"> Convocatoria: <html:select
						property="CConvocatoriaId"
						styleClass="cbCuadros colocaConvocatoriaGenActas" size="1"
						onchange="javascript:cargarMiembros();">
						<html:option value="">Seleccione...</html:option>
						<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
							property="CConvocatoriaId" labelProperty="DNombre" />
					</html:select>
				</label> <br />
				<br /> <label for="idVCategoria"> Categor&iacute;a: <html:select
						property="CProfesionalId"
						styleClass="cbCuadros colocaCategoriaGenActas" size="1"
						onchange="javascript:cargarMiembros();">
						<html:option value="">Seleccione...</html:option>
						<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
							property="CProfesionalId" labelProperty="DNombre" />
					</html:select>
				</label>
			</div>
		</fieldset>
		<logic:present name="OCAPActasForm" property="listaPresidentes">
			<logic:present name="OCAPActasForm" property="listaVocales">
				<logic:present name="OCAPActasForm" property="listaSecretarios">
					<fieldset>
						<legend class="tituloLegend"> Asistentes </legend>
						<div class="cuadroFieldset">
							<label for="idVPresi1"> Presidentes: <logic:iterate
									id="presi" name="OCAPActasForm" property="listaPresidentes"
									type="OCAPMiembrosComitesOT" indexId="i">
									<html:checkbox name="OCAPActasForm" property="presidente"
										onclick="activarEnCalidadPresi('<%=i%>')" />
									<bean:write name="presi" property="DApellidos" />, <bean:write
										name="presi" property="DNombre" />
									<html:select styleClass="cbCuadros" name="OCAPActasForm"
										property="BEnCalidadPresi" disabled="true">
										<html:option value="">En calidad de ...</html:option>
										<html:option value="<%=Constantes.EN_CALIDAD_TITULAR_VALUE%>"><%=Constantes.EN_CALIDAD_TITULAR%></html:option>
										<html:option value="<%=Constantes.EN_CALIDAD_SUPLENTE_VALUE%>"><%=Constantes.EN_CALIDAD_SUPLENTE%></html:option>
									</html:select>
								</logic:iterate>
							</label> <br />
							<br />
							<br /> <label for="idVVocal1"><span
								class="tituloVocales"> Vocales: </span>
								<table class="vocales">
									<tr>
										<td><html:text property="DNombreApellidosVocal"
												styleClass="recuadroM colocaApellidosAlta textoNormal"
												maxlength="61" /> <html:select styleClass="cbCuadros"
												name="OCAPSolicitudesForm" property="BSexoVocal">
												<html:option value="">Sexo ...</html:option>
												<html:option value="<%=Constantes.SEXO_HOMBRE_VALUE%>"><%=Constantes.SEXO_HOMBRE%></html:option>
												<html:option value="<%=Constantes.SEXO_MUJER_VALUE%>"><%=Constantes.SEXO_MUJER%></html:option>
											</html:select> <html:select styleClass="cbCuadros"
												name="OCAPSolicitudesForm" property="BEnCalidadVocal">
												<html:option value="">En calidad de ...</html:option>
												<html:option
													value="<%=Constantes.EN_CALIDAD_TITULAR_VALUE%>"><%=Constantes.EN_CALIDAD_TITULAR%></html:option>
												<html:option
													value="<%=Constantes.EN_CALIDAD_SUPLENTE_VALUE%>"><%=Constantes.EN_CALIDAD_SUPLENTE%></html:option>
											</html:select></td>
									</tr>
								</table> </label>
							<div class="colocaBoton">
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="javascript:anhadirVocal();"> <span> Añadir
												Vocal </span>
									</a>
									</span> <span class="derBoton"></span>
								</div>
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="javascript:editarVocal();"> <span> Editar
												Vocal </span>
									</a>
									</span> <span class="derBoton"></span>
								</div>
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="javascript:combos_borrar(document.getElementById('jspCadenaVocales'));">
											<span> Eliminar Vocal </span>
									</a>
									</span> <span class="derBoton"></span>
								</div>
							</div>
							<br /> <select size="5" id="jspCadenaVocales"
								name="jspCadenaVocales" class="textoTituloGrisAlta3"></select>
							<html:hidden property="resumenVocales" />
							<script language="javascript">inicializarVocales();</script>

							<br />
							<br />
							<br /> <label for="idVSecre1"> Secretarios/as: <html:text
									property="DNombreApellidosSecre1"
									styleClass="recuadroM colocaDatosGenActa textoNormal"
									maxlength="61" /> <html:select styleClass="cbCuadros"
									name="OCAPSolicitudesForm" property="BSexoSecre1">
									<html:option value="">Sexo ...</html:option>
									<html:option value="<%=Constantes.SEXO_HOMBRE_VALUE%>"><%=Constantes.SEXO_HOMBRE%></html:option>
									<html:option value="<%=Constantes.SEXO_MUJER_VALUE%>"><%=Constantes.SEXO_MUJER%></html:option>
								</html:select> <html:select styleClass="cbCuadros" name="OCAPSolicitudesForm"
									property="BEnCalidadSecre1">
									<html:option value="">En calidad de ...</html:option>
									<html:option value="<%=Constantes.EN_CALIDAD_TITULAR_VALUE%>"><%=Constantes.EN_CALIDAD_TITULAR%></html:option>
									<html:option value="<%=Constantes.EN_CALIDAD_SUPLENTE_VALUE%>"><%=Constantes.EN_CALIDAD_SUPLENTE%></html:option>
								</html:select>
							</label> <br /> <br /> <label for="idVSecre2" class="secre2"> <html:text
									property="DNombreApellidosSecre2"
									styleClass="recuadroM colocaDatosGenActa textoNormal"
									maxlength="61" /> <html:select styleClass="cbCuadros"
									name="OCAPSolicitudesForm" property="BSexoSecre2">
									<html:option value="">Sexo ...</html:option>
									<html:option value="<%=Constantes.SEXO_HOMBRE_VALUE%>"><%=Constantes.SEXO_HOMBRE%></html:option>
									<html:option value="<%=Constantes.SEXO_MUJER_VALUE%>"><%=Constantes.SEXO_MUJER%></html:option>
								</html:select> <html:select styleClass="cbCuadros" name="OCAPSolicitudesForm"
									property="BEnCalidadSecre2">
									<html:option value="">En calidad de ...</html:option>
									<html:option value="<%=Constantes.EN_CALIDAD_TITULAR_VALUE%>"><%=Constantes.EN_CALIDAD_TITULAR%></html:option>
									<html:option value="<%=Constantes.EN_CALIDAD_SUPLENTE_VALUE%>"><%=Constantes.EN_CALIDAD_SUPLENTE%></html:option>
								</html:select>
							</label> <br />
							<br />
							<br /> <label for="idVMiembrosAusentes"><span
								class="miembrosAusentes">Miembros que excusan su
									ausencia:</span> <br />
							<br /> <html:textarea property="DMiembrosAusentes"
									styleClass="recuadroM colocaMiembrosAusentes textoNormal"
									rows="5" cols="80" /> </label>
						</div>
					</fieldset>
				</logic:present>
			</logic:present>
		</logic:present>

		<fieldset>
			<legend class="tituloLegend"> Puntos </legend>
			<div class="cuadroFieldset">
				<label for="idVAsuntosTramite"><span
					class="miembrosAusentes">Asuntos de trámite:</span> <br />
				<br /> <html:textarea property="DAsuntosTramite"
						styleClass="recuadroM colocaMiembrosAusentes textoNormal" rows="5"
						cols="80" /> </label>
				<div class="limpiadora separador"></div>
				<label for="idVMiembrosAusentes"><span
					class="miembrosAusentes">Ruegos y preguntas: </span> <br />
				<br /> <html:textarea property="DRuegosPreguntas"
						styleClass="recuadroM colocaMiembrosAusentes textoNormal" rows="5"
						cols="80" /> </label>
			</div>
		</fieldset>
		<fieldset>
			<legend class="tituloLegend"> Fecha </legend>
			<div class="cuadroFieldset">
				<%--
                  <label for="idVGerencia"> Gerencia: 
                     <html:select property="CGerencia_id" styleClass="cbCuadros colocaCategoriaGenActas" size="1">
                        <html:option value="">Seleccione...</html:option>
                        <html:options collection="<%=Constantes.COMBO_GERENCIA%>" property="CGerenciaId" labelProperty="DNombre"/>
                     </html:select>
                  </label>
                  <br /><br />
               --%>
				<label for="idVFechaSesion"> Fecha sesión: (dd/mm/aaaa) <html:text
						property="FInicio" maxlength="10"
						styleClass="recuadroM colocaFechaAlta textoNormal" /> <span>
						<a
						href='javascript:show_Calendario("OCAPSolicitudesForm", "FInicio", document.forms[0].FInicio.value);'><html:img
								src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
				</span>
				</label> <label for="idVHoraInicio"> Hora inicio sesión: <html:text
						property="NHoraInicio" maxlength="2"
						styleClass="recuadroM colocaHoras textoNormal"
						onkeypress="return permitirSoloNumeros(event);" /> : <html:text
						property="NMinutosInicio" maxlength="2"
						styleClass="recuadroM colocaHoras textoNormal"
						onkeypress="return permitirSoloNumeros(event);" />
				</label> <label for="idVHoraFin"> Hora finalización sesión: <html:text
						property="NHoraFin" maxlength="2"
						styleClass="recuadroM colocaHoras textoNormal"
						onkeypress="return permitirSoloNumeros(event);" /> : <html:text
						property="NMinutosFin" maxlength="2"
						styleClass="recuadroM colocaHoras textoNormal"
						onkeypress="return permitirSoloNumeros(event);" />
				</label>

			</div>
		</fieldset>
		<div class="espaciador"></div>
		<div class="limpiadora"></div>
		<div class="botonesPagina">
			<div class="botonAccion">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="javascript:generarPDFActaSoliAclaracion();"> <img
						src="imagenes/imagenes_ocap/flecha_correcto.gif"
						alt="Generar listado CSV" /> <span> Generar Acta Solicitud
							Aclaración </span>
				</a>
				</span> <span class="derBoton"></span>
			</div>
			<div class="botonAccion">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="javascript:generarPDFActaReunion();"> <img
						src="imagenes/imagenes_ocap/flecha_correcto.gif"
						alt="Generar listado CSV" /> <span> Generar Acta Reunión </span>
				</a>
				</span> <span class="derBoton"></span>
			</div>
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<div class="botonAccion">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="javascript:generarPDFActaConstitucion();"> <img
						src="imagenes/imagenes_ocap/flecha_correcto.gif"
						alt="Generar listado PDF" /> <span> Generar Acta
							Constitución del Comité </span>
				</a>
				</span> <span class="derBoton"></span>
			</div>

		</div>
		<!-- /Fin de botonesPagina -->

	</html:form>
</div>
<!-- /Fin de Contenido -->