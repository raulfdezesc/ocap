<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.actas.OCAPMiembrosComitesOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT"%>
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

   if (document.forms[0].CConvocatoriaId.value ==''){
      alert('Debe seleccionar una Convocatoria.');
      return false;
   }
   //if (tipoActa != 'C'){ //En el Acta de Constitucion (C) la Categor�a no es obligatoria
      if (document.forms[0].CProfesionalId.value ==''){
         alert('Debe seleccionar una Categor\u00EDa.');
         return false;
      }
   //}
   //presidentes: un titular y un suplente
   var contPresiTitular=0;
   var contPresiSuplente=0;
   var puestosPresidentes = document.getElementsByName('BEnCalidadPresi');
   for (var i=0; i < puestosPresidentes.length; i++){
      if(puestosPresidentes[i].value == '<%=Constantes.EN_CALIDAD_TITULAR_VALUE%>')
         contPresiTitular++;
      if(puestosPresidentes[i].value == '<%=Constantes.EN_CALIDAD_SUPLENTE_VALUE%>')
         contPresiSuplente++;
   }
   var mensajePresi='';
   if (contPresiTitular != 1) 
      mensajePresi='Debe elegir un presidente titular.\n';
  /* if (contPresiSuplente != 1)
      mensajePresi += 'Debe elegir un presidente suplente.';
   if (contPresiTitular != 1 || contPresiSuplente != 1) {*/
   if (contPresiTitular != 1) {
      alert(mensajePresi);
      return(false);
   }
   //los presidentes que est�n seleccionados pero no tengan "en caldiad de...", deseleccionarlos
   var presidentes = document.getElementsByName('BPresidente');
   for (var i=0; i < presidentes.length; i++){
      if(presidentes[i].checked==true && puestosPresidentes[i].value==''){
         presidentes[i].checked = false;
         presidentes[i].value = '';
         puestosPresidentes[i].disabled=true;
      }
   }
   
   //vocales: al menos uno titular y uno suplente
   var contVocalTitular=0;
   var contVocalSuplente=0;
   var puestosVocales = document.getElementsByName('BEnCalidadVocal');
   for (var i=0; i < puestosVocales.length; i++){
      if(puestosVocales[i].value == '<%=Constantes.EN_CALIDAD_TITULAR_VALUE%>')
         contVocalTitular++;
      if(puestosVocales[i].value == '<%=Constantes.EN_CALIDAD_SUPLENTE_VALUE%>')
         contVocalSuplente++;
   }
   var mensajeVocal='';
   if (contVocalTitular<1) 
      mensajeVocal='Debe elegir al menos un vocal titular.\n';
  /* if (contVocalSuplente<1)
      mensajeVocal += 'Debe elegir al menos un vocal suplente.';
   if (contVocalTitular<1 || contVocalSuplente<1) {*/
   if (contVocalTitular<1 ) {
      alert(mensajeVocal);
      return(false);
   }
   //comprobar que para todos los vocales elegidos se ha seleccionado un "puesto" (en calidad de...)
   var vocales = document.getElementsByName('BVocal');
   var contVocales=0;
   for (var i=0; i < vocales.length; i++){
      if(vocales[i].checked==true)
         contVocales++;
   }
   if ((contVocalTitular + contVocalSuplente) > 80){
      alert('No puede seleccionar m\u00E1s de 80 vocales.');
      return (false);
   }
   if ((contVocalTitular + contVocalSuplente) != contVocales){
      alert('Para todos los vocales elegidos es necesario seleccionar en calidad de qu\u00E9 act\u00FAan.');
      return (false);
   }
   //secretarios: un titular y un suplente
   var secretarioTitular=false;
   var secretarioSuplente=false;
   var puestosSecretarios = document.getElementsByName('BEnCalidadSecre');
   for (var i=0; i < puestosSecretarios.length; i++){
      if(puestosSecretarios[i].value == '<%=Constantes.EN_CALIDAD_TITULAR_VALUE%>')
         secretarioTitular = true;
      if(puestosSecretarios[i].value == '<%=Constantes.EN_CALIDAD_SUPLENTE_VALUE%>')
         secretarioSuplente = true;
   }
   var mensaje='';
   if (!secretarioTitular) 
      mensaje='Debe elegir un secretario titular.\n';
   /*if (!secretarioSuplente)
      mensaje += 'Debe elegir un secretario suplente.';
   if (!secretarioTitular || !secretarioSuplente) {*/
   if (!secretarioTitular) {
      alert(mensaje);
      return(false);
   }
   //los secretarios que est�n seleccionados pero no tengan "en calidad de...", deseleccionarlos
   var secretarios = document.getElementsByName('BSecretario');
   for (var i=0; i < secretarios.length; i++){
      if(secretarios[i].checked==true && puestosSecretarios[i].value==''){
         secretarios[i].checked = false;
         secretarios[i].value = '';
         puestosSecretarios[i].disabled=true;
      }
   }
   
   //Si el acta NO es de constitucion, comprobar que se selecciona alg�n usuario
   if (tipoActa != '<%=Constantes.ACTA_CONSTITUCION%>'){
      var usuarios = document.getElementsByName('BUsuario');
      var contUsuariosSeleccionados=0;
      for (var i=0; i < usuarios.length; i++){
         if(usuarios[i].checked==true)
            contUsuariosSeleccionados++;
      }
      if (contUsuariosSeleccionados == 0  && tipoActa != '<%=Constantes.ACTA_CONSTITUCION%>'){
         alert('Debe seleccionar alg\u00FAn profesional.');
         return false;
      }
      //comprobar que para todos los usuarios elegidos se ha seleccionado un "tipo de informe"
      var contUsuarios=0;
      var informesUsuarios = document.getElementsByName('BTipoInformeUsuario');
      for (var i=0; i < informesUsuarios.length; i++){
         if(informesUsuarios[i].value != '')
            contUsuarios++;
      }
      if (contUsuariosSeleccionados != contUsuarios){
         alert('Para todos los profesionales seleccionados es necesario elegir el tipo de acci\u00F3n que se ha realizado.');
         return (false);
      }
   }
   
   if (document.forms[0].FInicio.value ==''){
      alert('Debe indicar la Fecha de sesi\u00F3n');
      return false;
   } else if(!esFechaCorrecta(document.forms[0].FInicio.value)){
      alert('El campo \'Fecha de sesi\u00F3n\' no es una fecha correcta.');
      return false;
   }
   if (document.forms[0].NHoraInicio.value =='' || document.forms[0].NMinutosInicio.value ==''){
      alert('Debe indicar la hora y minutos de inicio de la sesi\u00F3n (hh:mm)');
      return false;
   }
   if (document.forms[0].NHoraFin.value =='' || document.forms[0].NMinutosFin.value ==''){
      alert('Debe indicar la hora y minutos de fin de la sesi\u00F3n (hh:mm)');
      return false;
   }
   if (document.forms[0].NHoraInicio.value.length == 1)
      document.forms[0].NHoraInicio.value = '0'+document.forms[0].NHoraInicio.value;
   if (document.forms[0].NMinutosInicio.value.length == 1)
      document.forms[0].NMinutosInicio.value = '0'+document.forms[0].NMinutosInicio.value;
   if (document.forms[0].NHoraFin.value.length == 1)
      document.forms[0].NHoraFin.value = '0'+document.forms[0].NHoraFin.value;
   if (document.forms[0].NMinutosFin.value.length == 1)
      document.forms[0].NMinutosFin.value = '0'+document.forms[0].NMinutosFin.value;
   if (document.forms[0].NHoraInicio.value > 23 || document.forms[0].NMinutosInicio.value > 59){
      alert('La hora de inicio de la sesi\u00F3n debe estar comprendida entre las 00:00 y las 23:59.');
      return false;
   }
   if (document.forms[0].NHoraFin.value > 23 || document.forms[0].NMinutosFin.value > 59){
      alert('La hora de fin de la sesi\u00F3n debe estar comprendida entre las 00:00 y las 23:59.');
      return false;
   }
   if ((document.forms[0].NHoraFin.value < document.forms[0].NHoraInicio.value) || 
      (document.forms[0].NHoraFin.value == document.forms[0].NHoraInicio.value && document.forms[0].NMinutosFin.value < document.forms[0].NMinutosInicio.value)){
      alert('La hora de fin de la sesi\u00F3n no puede ser anterior a la hora de inicio.');
      return false;
   }
   return true;
}

function cargarMiembros(){
   if (document.forms[0].CConvocatoriaId.value != '' && document.forms[0].CProfesionalId.value != '')
      enviar('OCAPActas.do?accion=cargarMiembros');
}

function comprobarPresidentes(campo){
   var contPresidenteTitular=0;
   var contPresidenteSuplente=0;
   var puestosPresidentes = document.getElementsByName(' ');
   for (var i=0; i < puestosPresidentes.length; i++){
      if(puestosPresidentes[i].value == '<%=Constantes.EN_CALIDAD_TITULAR_VALUE%>')
         contPresidenteTitular++;
      if(puestosPresidentes[i].value == '<%=Constantes.EN_CALIDAD_SUPLENTE_VALUE%>')
         contPresidenteSuplente++;
   }
   if (campo.value=='<%=Constantes.EN_CALIDAD_TITULAR_VALUE%>') {
      if (contPresidenteTitular>1) {
         alert('Ya existe un presidente titular. Desmarque el primero o modifique el puesto del actual.');
         campo.value='';
      }
   }
   if (campo.value=='<%=Constantes.EN_CALIDAD_SUPLENTE_VALUE%>') {
      if (contPresidenteSuplente>1) {
         alert('Ya existe un presidente suplente. Desmarque el primero o modifique el puesto del actual.');
         campo.value='';
      }
   }
}

function activarEnCalidadPresiUnico (){
   if (document.forms[0].BPresidente.checked == true)
      document.forms[0].BEnCalidadPresi.disabled=false;
   else {
      document.forms[0].BEnCalidadPresi.value='';
      document.forms[0].BEnCalidadPresi.disabled=true;
   }
}

function activarEnCalidadVocalUnico(){
   if (document.forms[0].BVocal.checked == true)
      document.forms[0].BEnCalidadVocal.disabled=false;
   else {
      document.forms[0].BEnCalidadVocal.value='';
      document.forms[0].BEnCalidadVocal.disabled=true;
   }
}

function activarEnCalidadSecreUnico(){
   if (document.forms[0].BSecretario.checked == true)
      document.forms[0].BEnCalidadSecre.disabled=false;
   else {
      document.forms[0].BEnCalidadSecre.value='';
      document.forms[0].BEnCalidadSecre.disabled=true;
   }
}

function activarEnCalidadVocal(i){
   if (document.forms[0].BVocal[i].checked == true)
      document.forms[0].BEnCalidadVocal[i].disabled=false;
   else {
      document.forms[0].BEnCalidadVocal[i].value='';
      document.forms[0].BEnCalidadVocal[i].disabled=true;
   }
}

function activarEnCalidadPresi(i){
   if (document.forms[0].BPresidente[i].checked == true)
      document.forms[0].BEnCalidadPresi[i].disabled=false;
   else {
      document.forms[0].BEnCalidadPresi[i].value='';
      document.forms[0].BEnCalidadPresi[i].disabled=true;
   }
}

function comprobarSecretarios(campo){
   var contSecretarioTitular=false;
   var contSecretarioSuplente=false;
   var puestosSecretarios = document.getElementsByName('BEnCalidadSecre');
   for (var i=0; i < puestosSecretarios.length; i++){
      if(puestosSecretarios[i].value == '<%=Constantes.EN_CALIDAD_TITULAR_VALUE%>')
         contSecretarioTitular++;
      if(puestosSecretarios[i].value == '<%=Constantes.EN_CALIDAD_SUPLENTE_VALUE%>')
         contSecretarioSuplente++;
   }
   if (campo.value=='<%=Constantes.EN_CALIDAD_TITULAR_VALUE%>') {
      if (contSecretarioTitular>1) {
         alert('Ya existe un secretario titular. Desmarque el primero o modifique el puesto del actual.');
         campo.value='';
      }
   }
   if (campo.value=='<%=Constantes.EN_CALIDAD_SUPLENTE_VALUE%>') {
      if (contSecretarioSuplente>1) {
         alert('Ya existe un secretario suplente. Desmarque el primero o modifique el puesto del actual.');
         campo.value='';
      }
   }
}

function activarEnCalidadSecre(i){
   if (document.forms[0].BSecretario[i].checked == true)
      document.forms[0].BEnCalidadSecre[i].disabled=false;
   else {
      document.forms[0].BEnCalidadSecre[i].value='';
      document.forms[0].BEnCalidadSecre[i].disabled=true;
   }
}

function activarTipoInformeUsuario(i){
   if(document.getElementsByName('BUsuario').length > 1){
      if (document.forms[0].BUsuario[i].checked == true)
         document.forms[0].BTipoInformeUsuario[i].disabled=false;
      else {
         document.forms[0].BTipoInformeUsuario[i].value='';
         document.forms[0].BTipoInformeUsuario[i].disabled=true;
      }
   } else {
      if (document.forms[0].BUsuario.checked == true)
         document.forms[0].BTipoInformeUsuario.disabled=false;
      else {
         document.forms[0].BTipoInformeUsuario.value='';
         document.forms[0].BTipoInformeUsuario.disabled=true;
      }
   }
}

function inicializarCampos(){
   var puestosPresidentes = document.getElementsByName('BEnCalidadPresi');
   for(var i=0; i< puestosPresidentes.length; i++){
      if (puestosPresidentes[i].value == '')
         puestosPresidentes[i].disabled=true;
   }
   var puestosVocales = document.getElementsByName('BEnCalidadVocal');
   for(var i=0; i< puestosVocales.length; i++){
      if (puestosVocales[i].value == '')
         puestosVocales[i].disabled=true;
   }
   var puestosSecretarios = document.getElementsByName('BEnCalidadSecre');
   for(var i=0; i< puestosSecretarios.length; i++){
      if (puestosSecretarios[i].value == '')
         puestosSecretarios[i].disabled=true;
   }
   var informesUsuarios = document.getElementsByName('BTipoInformeUsuario');
   for(var i=0; i< informesUsuarios.length; i++){
      if (informesUsuarios[i].value == '')
         informesUsuarios[i].disabled=true;
   }
}

function guardarActa(){
   if (validarObligatorios('<bean:write name="tipoActa" />'))
      enviar("OCAPActas.do?accion=guardarActa");
}


function longMax(campo, max) {
   if(campo.value.length <= max) {
      return true;
   } else {
      campo.value = campo.value.substring(0, max);
      return false;
   }
}

</script>

<div class="contenido generarActas">
	<html:form action="/OCAPActas.do">
		<html:hidden name="OCAPActasForm" property="CGerenciaId" />
		<h2 class="tituloContenido">GENERACI&Oacute;N DE ACTAS</h2>
		<logic:equal name="tipoActa" value="<%=Constantes.ACTA_CONSTITUCION%>">
			<h3 class="subTituloContenido">Acta de constituci&oacute;n</h3>
		</logic:equal>
		<logic:equal name="tipoActa" value="<%=Constantes.ACTA_REUNION%>">
			<h3 class="subTituloContenido">Acta de reuni&oacute;n</h3>
		</logic:equal>
		<logic:equal name="tipoActa"
			value="<%=Constantes.ACTA_SOLI_ACLARACION%>">
			<h3 class="subTituloContenido">Acta de solicitud de aclaraciones
			</h3>
		</logic:equal>
		<div class="lineaBajaM"></div>
		<div class="espaciador"></div>
		<fieldset>
			<legend class="tituloLegend"> Convocatoria </legend>
			<div class="cuadroFieldset">
				<label for="idVConvocatoria"> Grado: <html:select
						name="OCAPActasForm" property="CGradoId"
						styleClass="cbCuadros colocaGradoGenActas" size="1"
						onchange="javascript:cargarMiembros();">
						<html:option value="">Todos</html:option>
						<html:options collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
							property="CGradoId" labelProperty="DNombre" />
					</html:select>
				</label> <br />
				<br /> <label for="idVConvocatoria"> Convocatoria: * <html:select
						property="CConvocatoriaId"
						styleClass="cbCuadros colocaConvocatoriaGenActas" size="1"
						onchange="javascript:cargarMiembros();">
						<html:option value="">Seleccione...</html:option>
						<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
							property="CConvocatoriaId" labelProperty="DNombre" />
					</html:select>
				</label> <br />
				<br /> <label for="idVCategoria"> Categor&iacute;a: * <html:select
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
						<div class="cuadroFieldset asistentes">
							<bean:size id="numPresidentes" name="OCAPActasForm"
								property="listaPresidentes" />
							<label for="idVPresi1"> Presidentes: * <br />
								<div class="bloqueEnvolturaDatos">
									<logic:equal name="numPresidentes" value="0">
										<div>No existen presidentes dados de alta en su Gerencia
											para la categor&iacute;a y convocatoria seleccionadas. Debe
											darlos de alta antes de generar el acta.</div>
									</logic:equal>
									<!-- Ahora ya no es obligatorio que haya más de uno: <logic:equal name="numPresidentes" value="1">
                           <div>No existen suficientes presidentes dados de alta en su Gerencia para la categor&iacute;a y convocatoria seleccionadas. Debe darlos de alta antes de generar el acta.</div>
                        </logic:equal>-->
									<logic:greaterThan name="numPresidentes" value="1">
										<table>
											<logic:iterate id="presi" name="OCAPActasForm"
												property="listaPresidentes" type="OCAPMiembrosComitesOT"
												indexId="i">
												<tr>
													<td><html:checkbox name="OCAPActasForm"
															property="BPresidente"
															onclick='<%="activarEnCalidadPresi("+i+")"%>'
															value="<%=Long.toString(presi.getCMiembroId())%>" /></td>
													<td><span> <bean:write name="presi"
																property="DApellidos" />, <bean:write name="presi"
																property="DNombre" />
													</span></td>
													<td><html:select styleClass="cbCuadros"
															name="OCAPActasForm" property="BEnCalidadPresi"
															onchange="comprobarPresidentes(this);">
															<html:option value="">En calidad de ...</html:option>
															<html:option
																value="<%=Constantes.EN_CALIDAD_TITULAR_VALUE%>"><%=Constantes.EN_CALIDAD_TITULAR%></html:option>
															<html:option
																value="<%=Constantes.EN_CALIDAD_SUPLENTE_VALUE%>"><%=Constantes.EN_CALIDAD_SUPLENTE%></html:option>
														</html:select></td>
												</tr>
											</logic:iterate>
										</table>
									</logic:greaterThan>
									<logic:equal name="numPresidentes" value="1">
										<table>
											<logic:iterate id="presi" name="OCAPActasForm"
												property="listaPresidentes" type="OCAPMiembrosComitesOT"
												indexId="i">
												<tr>
													<td><html:checkbox name="OCAPActasForm"
															property="BPresidente"
															onclick='<%="activarEnCalidadPresiUnico()"%>'
															value="<%=Long.toString(presi.getCMiembroId())%>" /></td>
													<td><span> <bean:write name="presi"
																property="DApellidos" />, <bean:write name="presi"
																property="DNombre" />
													</span></td>
													<td><html:select styleClass="cbCuadros"
															name="OCAPActasForm" property="BEnCalidadPresi">
															<html:option value="">En calidad de ...</html:option>
															<html:option
																value="<%=Constantes.EN_CALIDAD_TITULAR_VALUE%>"><%=Constantes.EN_CALIDAD_TITULAR%></html:option>
														</html:select></td>
												</tr>
											</logic:iterate>
										</table>
									</logic:equal>
								</div>
							</label> <br />
							<br />
							<bean:size id="numVocales" name="OCAPActasForm"
								property="listaVocales" />
							<label for="idVPresi1"> Vocales: *<br />
								<div class="bloqueEnvolturaDatos">
									<logic:equal name="numVocales" value="0">
										<div>No existen vocales dados de alta en su Gerencia
											para la categor&iacute;a y convocatoria seleccionadas. Debe
											darlos de alta antes de generar el acta.</div>
									</logic:equal>
									<!-- Ahora ya no es obligatorio que haya más de uno: <logic:equal name="numVocales" value="1">
                           <div>No existen suficientes vocales dados de alta en su Gerencia para la categor&iacute;a y convocatoria seleccionadas. Debe darlos de alta antes de generar el acta.</div>
                        </logic:equal>-->
									<logic:greaterThan name="numVocales" value="1">
										<table>
											<logic:iterate id="vocal" name="OCAPActasForm"
												property="listaVocales" type="OCAPMiembrosComitesOT"
												indexId="i">
												<tr>
													<td><html:checkbox name="OCAPActasForm"
															property="BVocal"
															onclick='<%="activarEnCalidadVocal("+i+")"%>'
															value="<%=Long.toString(vocal.getCMiembroId())%>" /></td>
													<td><span> <bean:write name="vocal"
																property="DApellidos" />, <bean:write name="vocal"
																property="DNombre" />
													</span></td>
													<td><html:select styleClass="cbCuadros"
															name="OCAPActasForm" property="BEnCalidadVocal">
															<html:option value="">En calidad de ...</html:option>
															<html:option
																value="<%=Constantes.EN_CALIDAD_TITULAR_VALUE%>"><%=Constantes.EN_CALIDAD_TITULAR%></html:option>
															<html:option
																value="<%=Constantes.EN_CALIDAD_SUPLENTE_VALUE%>"><%=Constantes.EN_CALIDAD_SUPLENTE%></html:option>
														</html:select></td>
												</tr>
											</logic:iterate>
										</table>
									</logic:greaterThan>

									<logic:equal name="numVocales" value="1">
										<table>
											<logic:iterate id="vocal" name="OCAPActasForm"
												property="listaVocales" type="OCAPMiembrosComitesOT"
												indexId="i">
												<tr>
													<td><html:checkbox name="OCAPActasForm"
															property="BVocal"
															onclick='<%="activarEnCalidadVocalUnico()"%>'
															value="<%=Long.toString(vocal.getCMiembroId())%>" /></td>
													<td><span> <bean:write name="vocal"
																property="DApellidos" />, <bean:write name="vocal"
																property="DNombre" />
													</span></td>
													<td><html:select styleClass="cbCuadros"
															name="OCAPActasForm" property="BEnCalidadVocal">
															<html:option value="">En calidad de ...</html:option>
															<html:option
																value="<%=Constantes.EN_CALIDAD_TITULAR_VALUE%>"><%=Constantes.EN_CALIDAD_TITULAR%></html:option>
														</html:select></td>
												</tr>
											</logic:iterate>
										</table>
									</logic:equal>
								</div>
							</label> <br />
							<br />
							<bean:size id="numSecretarios" name="OCAPActasForm"
								property="listaSecretarios" />
							<label for="idVPresi1"> Secretarios: *<br />
								<div class="bloqueEnvolturaDatos">
									<logic:equal name="numSecretarios" value="0">
										<div>No existen secretarios dados de alta en su Gerencia
											para la categor&iacute;a y convocatoria seleccionadas. Debe
											darlos de alta antes de generar el acta.</div>
										<br />
									</logic:equal>
									<!-- Ahora ya no es obligatorio que haya más de uno:  <logic:equal name="numSecretarios" value="1">
                           <div>No existen suficientes secretarios dados de alta en su Gerencia para la categor&iacute;a y convocatoria seleccionadas. Debe darlos de alta antes de generar el acta.</div>
                        </logic:equal>-->
									<logic:greaterThan name="numSecretarios" value="1">
										<table>
											<logic:iterate id="secre" name="OCAPActasForm"
												property="listaSecretarios" type="OCAPMiembrosComitesOT"
												indexId="i">
												<tr>
													<td><html:checkbox name="OCAPActasForm"
															property="BSecretario"
															onclick='<%="activarEnCalidadSecre("+i+")"%>'
															value="<%=Long.toString(secre.getCMiembroId())%>" /></td>
													<td><span> <bean:write name="secre"
																property="DApellidos" />, <bean:write name="secre"
																property="DNombre" />
													</span></td>
													<td><html:select styleClass="cbCuadros"
															name="OCAPActasForm" property="BEnCalidadSecre"
															onchange="comprobarSecretarios(this);">
															<html:option value="">En calidad de ...</html:option>
															<html:option
																value="<%=Constantes.EN_CALIDAD_TITULAR_VALUE%>"><%=Constantes.EN_CALIDAD_TITULAR%></html:option>
															<html:option
																value="<%=Constantes.EN_CALIDAD_SUPLENTE_VALUE%>"><%=Constantes.EN_CALIDAD_SUPLENTE%></html:option>
														</html:select></td>
												</tr>
											</logic:iterate>
										</table>
									</logic:greaterThan>
									<logic:equal name="numSecretarios" value="1">
										<table>
											<logic:iterate id="secre" name="OCAPActasForm"
												property="listaSecretarios" type="OCAPMiembrosComitesOT"
												indexId="i">
												<tr>
													<td><html:checkbox name="OCAPActasForm"
															property="BSecretario"
															onclick='<%="activarEnCalidadSecreUnico()"%>'
															value="<%=Long.toString(secre.getCMiembroId())%>" /></td>
													<td><span> <bean:write name="secre"
																property="DApellidos" />, <bean:write name="secre"
																property="DNombre" />
													</span></td>
													<td><html:select styleClass="cbCuadros"
															name="OCAPActasForm" property="BEnCalidadSecre"
															onchange="comprobarSecretarios(this);">
															<html:option value="">En calidad de ...</html:option>
															<html:option
																value="<%=Constantes.EN_CALIDAD_TITULAR_VALUE%>"><%=Constantes.EN_CALIDAD_TITULAR%></html:option>
														</html:select></td>
												</tr>
											</logic:iterate>
										</table>
									</logic:equal>
								</div>
							</label> <br />
							<br /> <label for="idVMiembrosAusentes"><span
								class="miembrosAusentes">Miembros que excusan su
									ausencia:</span> <br />
							<br /> <html:textarea property="DMiembrosAusentes"
									styleClass="recuadroM colocaMiembrosAusentes textoNormal"
									rows="5" cols="80"
									onkeypress="javascript:return longMax(this,4000);"
									onclick="javascript:return longMax(this,4000);"
									onkeydown="javascript:return longMax(this,4000);"
									onkeyup="javascript:return longMax(this,4000);"></html:textarea>
							</label>
						</div>
					</fieldset>
				</logic:present>
			</logic:present>
		</logic:present>

		<logic:present name="OCAPActasForm"
			property="listaUsuariosInformeMotivado">
			<fieldset>
				<legend class="tituloLegend"> Profesionales </legend>
				<div class="cuadroFieldset">
					<bean:size id="numUsuariosInfMotivado" name="OCAPActasForm"
						property="listaUsuariosInformeMotivado" />
					<logic:equal name="numUsuariosInfMotivado" value="0">
						<div>No hay ning&uacute;n profesional en esta fase para la
							convocatoria y categor&iacute;a profesional seleccionadas.</div>
					</logic:equal>
					<logic:notEqual name="numUsuariosInfMotivado" value="0">
						<table class="tablaProfesionales">
							<logic:iterate id="usuInforme" name="OCAPActasForm"
								property="listaUsuariosInformeMotivado" type="OCAPUsuariosOT"
								indexId="i">
								<!--div class="informeMotivado"-->
								<tr>
									<td width="5%"><html:checkbox name="OCAPActasForm"
											property="BUsuario"
											value="<%=Long.toString(usuInforme.getCExpId().longValue())%>"
											onclick='<%="activarTipoInformeUsuario("+i+")"%>' /></td>
									<td width="50%"><bean:write name="usuInforme"
											property="CDniReal" /> &nbsp; <bean:write name="usuInforme"
											property="DApellido1" />, <bean:write name="usuInforme"
											property="DNombre" /></td>
									<td width="45%"><html:select styleClass="cbCuadros"
											name="OCAPActasForm" property="BTipoInformeUsuario">
											<html:option value="">Seleccione la acci&oacute;n realizada...</html:option>
											<html:option value="<%=Constantes.MC_MOTIVADO_VALUE%>"><%=Constantes.MC_MOTIVADO%></html:option>
											<html:option value="<%=Constantes.MC_ACLARACIONES_VALUE%>"><%=Constantes.MC_ACLARACIONES%></html:option>
										</html:select></td>
									<!--/div-->
								</tr>
							</logic:iterate>
						</table>
					</logic:notEqual>
				</div>
			</fieldset>
		</logic:present>
		<%--
         <logic:present name="OCAPActasForm" property="listaUsuariosAclaraciones">
            <fieldset>
               <legend class="tituloLegend"> Profesionales con petici&oacute;n de aclaraciones </legend>
               <div class="cuadroFieldset">
                  <bean:size id="numUsuariosAclaraciones" name="OCAPActasForm" property="listaUsuariosAclaraciones" />
                  <logic:equal name="numUsuariosAclaraciones" value="0">
                     <div>No hay ning&uacute;n usuario con aclaraciones solicitadas en la convocatoria y categor&iacute;a profesional seleccionadas.</div>
                  </logic:equal>
                  <logic:iterate id="usuAclaracion" name="OCAPActasForm" property="listaUsuariosAclaraciones" type="OCAPUsuariosOT" indexId="i" >
                     <div class="informeMotivado">   
                        <html:checkbox name="OCAPActasForm" property="BUsuarioAclaraciones" value="<%=Long.toString(usuAclaracion.getCExpId().longValue())%>" />
                        <bean:write name="usuAclaracion" property="CDniReal" /> &nbsp; <bean:write name="usuAclaracion" property="DApellido1" />, <bean:write name="usuAclaracion" property="DNombre" />
                     </div>
                  </logic:iterate>
               </div>
            </fieldset>
         </logic:present>
         --%>
		<fieldset>
			<legend class="tituloLegend"> Puntos </legend>
			<div class="cuadroFieldset">
				<label for="idVAsuntosTramite"><span
					class="miembrosAusentes">Asuntos de tr&aacute;mite:</span> <br />
				<br /> <html:textarea property="DAsuntosTramite"
						styleClass="recuadroM colocaMiembrosAusentes textoNormal" rows="5"
						cols="80"></html:textarea> </label>
				<div class="limpiadora separador"></div>
				<label for="idVMiembrosAusentes"><span
					class="miembrosAusentes">Ruegos y preguntas: </span> <br />
				<br /> <html:textarea property="DRuegosPreguntas"
						styleClass="recuadroM colocaMiembrosAusentes textoNormal" rows="5"
						cols="80" onkeypress="javascript:return longMax(this,4000);"
						onclick="javascript:return longMax(this,4000);"
						onkeydown="javascript:return longMax(this,4000);"
						onkeyup="javascript:return longMax(this,4000);"></html:textarea> </label>
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
				<label for="idVFechaSesion"> Fecha sesi&oacute;n:
					*(dd/mm/aaaa) <html:text name="OCAPActasForm" property="FInicio"
						maxlength="10" styleClass="recuadroM colocaFechaAlta textoNormal" />
					<span> <a class="iconoCalendario"
						href='javascript:show_Calendario("OCAPActasForm", "FInicio", document.forms[0].FInicio.value);'><html:img
								src="imagenes/calendario.gif" border="0" alt="Calendario" /></a>
				</span>
				</label> <label for="idVHoraInicio"> Hora inicio sesi&oacute;n: * <html:text
						name="OCAPActasForm" property="NHoraInicio" maxlength="2"
						styleClass="recuadroM colocaHoras textoNormal"
						onkeypress="return permitirSoloNumeros(event);" /> : <html:text
						name="OCAPActasForm" property="NMinutosInicio" maxlength="2"
						styleClass="recuadroM colocaHoras textoNormal"
						onkeypress="return permitirSoloNumeros(event);" />
				</label> <label for="idVHoraFin"> Hora finalizaci&oacute;n
					sesi&oacute;n: * <html:text name="OCAPActasForm"
						property="NHoraFin" maxlength="2"
						styleClass="recuadroM colocaHoras textoNormal"
						onkeypress="return permitirSoloNumeros(event);" /> : <html:text
						name="OCAPActasForm" property="NMinutosFin" maxlength="2"
						styleClass="recuadroM colocaHoras textoNormal"
						onkeypress="return permitirSoloNumeros(event);" />
				</label>

			</div>
		</fieldset>
		<div class="espaciador"></div>
		<div class="limpiadora"></div>
		<div class="botonesPagina">
			<%--
            <div class="botonAccion">
               <span class="izqBoton"></span>
               <span class="cenBoton">
                  <a href="javascript:generarPDFActaSoliAclaracion();">
                     <img src="imagenes/imagenes_ocap/flecha_correcto.gif" alt="Generar listado CSV" />
                     <span> Generar Acta Solicitud Aclaraci�n </span>
                  </a>
               </span>
               <span class="derBoton"></span>
            </div>
            <div class="botonAccion">
               <span class="izqBoton"></span>
               <span class="cenBoton">
                  <a href="javascript:generarPDFActaReunion();">
                     <img src="imagenes/imagenes_ocap/flecha_correcto.gif" alt="Generar listado CSV" />
                     <span> Generar Acta Reuni�n </span>
                  </a>
               </span>
               <span class="derBoton"></span>
            </div>
            <br/><br/><br/><br/><br/><br/>
            <div class="botonAccion">
               <span class="izqBoton"></span>
               <span class="cenBoton">
                  <a href="javascript:generarPDFActaConstitucion();">
                     <img src="imagenes/imagenes_ocap/flecha_correcto.gif" alt="Generar listado PDF" />
                     <span> Generar Acta Constituci�n del Comit� </span>
                  </a>
               </span>
               <span class="derBoton"></span>
            </div>
--%>
			<div class="botonAccion">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="javascript:guardarActa();"> <img
						src="imagenes/imagenes_ocap/flecha_correcto.gif"
						alt="Guardar datos del Acta" /> <span> Guardar Acta </span>
				</a>
				</span> <span class="derBoton"></span>
			</div>
		</div>
		<!-- /Fin de botonesPagina -->

		<div id="divTexto">
			<p>
				<label class="obligadoTexto">Los campos se&ntilde;alados con
					* son obligatorios</label>
			<div class="espaciador"></div>
			</p>
		</div>

		<script language="javaScript">
               inicializarCampos();
            </script>

	</html:form>
</div>
<!-- /Fin de Contenido -->