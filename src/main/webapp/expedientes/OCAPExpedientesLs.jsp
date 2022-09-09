<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>


<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"></script>

<%
	JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script language="javascript" type="text/javascript">
function cambiarSeleccion(){
	chck = document.forms[0].listaExpedientes;
   if(chck != null) {   
      if(chck.length == undefined){
         chck.checked = !chck.checked;
      } else {
         for (var i = 0; chck.length-1 >= i; i++) {
            chck[i].checked = !chck[i].checked;
         }
      }
   }
   
}

function comprobarEstado(){
   
   var correcto = 0;
   chck = document.forms[0].listaExpedientes;
   
   for (var i = 0; chck.length-1 >= i; i++) {
      if(chck[i].checked ==true){
         var estado = document.OCAPExpedientesForm.CEstadoId.value;           
         if (estado==13) enviar('OCAPExpedientes.do?accion=modificarEstadoAceptacion&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&estado='+estado);         
         if (estado==14) enviar('OCAPExpedientes.do?accion=modificarEstadoAceptacion&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&estado='+estado);      
         correcto = 1;
         break;
      }
   }

   if(correcto==0){
      alert('Debe seleccionar alg&uacute;nn usuario para poder cambiar el estado del expediente.');
   }   
}

function buscar(){
   if(validarBuscador(document.forms[0])){
     enviar('OCAPExpedientes.do?accion=listarExpedientes');
   }
}

function  generarCVS(){
   chck = document.forms[0].listaExpedientes;
   var marcados = 0;
   if(chck != null) {
    for (var i = 0; chck.length-1 >= i; i++) {
        
           if ( chck[i].checked )
           {
             marcados =marcados +1;
            }
           
     }
      
      if(marcados > <%=es.jcyl.framework.JCYLConfiguracion.getValor("NUMERO_CSV")%>) {
          alert('No puede seleccionar m\u00e0s de '+<%=es.jcyl.framework.JCYLConfiguracion.getValor("NUMERO_CSV")%>+' usuarios.');
         
       }else{
          enviar('OCAPExpedientes.do?accion=generarReportPDFExpedientesMeritosCurriculares');
     }
   }  
}

function  generarInformesMotivados(){
   chck = document.forms[0].listaExpedientes;
   var marcados = 0;
   if(chck != null) {
    for (var i = 0; chck.length-1 >= i; i++) {
        
           if ( chck[i].checked )
           {
             marcados =marcados +1;
            }
           
     }
      
      if(marcados > <%=es.jcyl.framework.JCYLConfiguracion.getValor("NUMERO_CSV")%>) {
          alert('No puede seleccionar m\u00e0s de '+<%=es.jcyl.framework.JCYLConfiguracion.getValor("NUMERO_CSV")%>+' usuarios.');
         
       }else{
          enviar('OCAPExpedientes.do?accion=generarReportPDFInfMotivado');
     }
   }  
}

function validarBuscador(formu){
	var cadena = formu.codigoId.value;
	cadena = trim(cadena);
	if(cadena !='') {
		if(LetrasYNumeros(formu.codigoId)) {
			var cad1 = formu.codigoId.value.toUpperCase();
			var cad2 = 'EPR';
			var result = cad1.indexOf(cad2);
			if (formu.codigoId.value.length < 9) {
				alert('El C\u00F3digo debe tener el formato EPRxxxxxx donde xxxxxx son 6 d\u00EDgitos.');
				return false;
			} else if ( result != 0) {
				alert('El C\u00F3digo debe tener el formato EPRxxxxxx donde xxxxxx son 6 d\u00EDgitos.');
				return false;
			} else {
				formu.codigoId.value = formu.codigoId.value.substring('EPR'.length);
				if (!soloNumeros(formu.codigoId)) {
					formu.codigoId.value = cadena;
					alert('El C\u00F3digo debe tener el formato EPRxxxxxx donde xxxxxx son 6 d\u00EDgitos.');
					return false;
				} else {
					formu.codigoId.value = cadena;
				}
			}
		} else {
			alert('El campo \'C\u00F3digo\' contiene caracteres no permitidos.');
			return false;
		}
		return true;
	}
	return true;
}

</script>

<div class="contenido">
	<div class="contenidoListadoAspirantesGCP solicitudesLs">

		<html:form action="/OCAPExpedientes.do">


			<h2 class="tituloContenido">USUARIOS CON OPCI&Oacute;N A CARRERA
				PROFESIONAL</h2>
			<h3 class="subTituloContenido">Listado de Usuarios</h3>

			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<div class="textoRojo">
				<html:messages id="erroresBuscador" property="erroresBuscador"
					message="false">
					<bean:write name="erroresBuscador" />
					<br />
				</html:messages>
			</div>

			<fieldset>
				<legend class="tituloLegend"> Buscador </legend>

				<div class="cuadroFieldset">
					<logic:equal name="modo" value="<%=Constantes.INF_FASEIII%>">
						<label for="idCodigo"> C&oacute;digo EPR: <html:text
								property="codigoId" styleClass="recuadroM colocaEPR"
								maxlength="9" tabindex="4" />
						</label>
						<br />
						<br />
					</logic:equal>
					<label for="idVApell1"> Apellidos: <html:text
							property="DApellidos" styleClass="recuadroM colocaApellidos"
							maxlength="30" tabindex="5" />
					</label> <label for="idVNombre"> Nombre: <html:text
							property="DNombre" styleClass="recuadroM colocaNombre"
							maxlength="30" tabindex="6" />
					</label> <br /> <br /> <label for="idVDNI"> NIF/NIE: <html:text
							property="CDNIReal" styleClass="recuadroM colocaDNI"
							maxlength="10" tabindex="7" />
					</label> <br /> <br /> <label for="idVCategoria">
						Categor&iacute;a: <html:select property="CCategProfesionalId"
							styleClass="cbCuadros colocaCategoriaCB" size="1" tabindex="8">
							<html:option value="">Todas</html:option>
							<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
								property="CProfesionalId" labelProperty="DNombre" />
						</html:select>
					</label> <br /> <br /> <label for="idVGrado"> Grado: <html:select
							property="CGradoId"
							styleClass="cbCuadros colocaGradoBuscCB ajusteGradoMC" size="1"
							tabindex="9">
							<html:option value="">Todos</html:option>
							<html:options collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
								property="CGradoId" labelProperty="DDescripcion" />
						</html:select>
					</label> <label for="idVGrado"> Convocatoria: <html:select
							property="CConvocatoriaId"
							styleClass="cbCuadros colocaConvocatoriaCB" size="1" tabindex="9">
							<html:option value="">Todas</html:option>
							<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
								property="CConvocatoriaId" labelProperty="DNombre" />
						</html:select>
					</label> <br /> <br />
					
					<%
					//Incidencia Carrera Profesional OCAP-78 Si no es GRS miramos filtro de Gerencia 
					if((!Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol()))){
					%>
					
						<logic:present name="<%=Constantes.COMBO_GERENCIA%>">
						<label for="idVGerencia"> Gerencia: <html:select
								property="CGerenciaId"
								styleClass="cbCuadros colocaGerenciaBuscCB" size="1"
								tabindex="10">
								<html:option value="">Todas</html:option>
								<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
									property="CGerenciaId" labelProperty="DNombre" />
							</html:select>
						</label>
					</logic:present>

					<logic:notPresent name="<%=Constantes.COMBO_GERENCIA%>">
						<label for="idVGerencia"> Gerencia: <html:hidden
								name="OCAPExpedientesForm" property="CGerenciaId" /> <span
							class="colocaGerenciaExpedientesCB"><bean:write
									name="OCAPExpedientesForm" property="DGerencia" /></span>
						</label>
					</logic:notPresent>

					<br /> <br />
					
					<%} %>
										
					<logic:present name="<%=Constantes.COMBO_ESTADOS%>">
						<label for="idVGerencia"> Estado: <html:select
								property="CEstadoId" styleClass="cbCuadros colocaEstadoBuscCB"
								size="1" tabindex="11">
								<logic:equal name="modo" value="<%=Constantes.INF_ETIQUETA%>">
									<html:option value="">Todos</html:option>
								</logic:equal>
								<html:options collection="<%=Constantes.COMBO_ESTADOS%>"
									property="cEstadoId" labelProperty="DNombre" />
							</html:select>
						</label>
						<br />
						<br />
					</logic:present>

					<%-- Si es el CC para imprimir CV, tiene que elegir si quiere ver los MC del usuario o los validados por CEIS--%>
					<logic:equal name="modo" value="<%=Constantes.INF_CREAR_CV%>">
						<%
							if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {
						%>
						<label for="idVGerencia"> Ver m&eacute;ritos: <html:select
								property="BMCUsuario" styleClass="cbCuadros colocaVerMeritos"
								size="1" tabindex="11">
								<html:option value="<%=Constantes.SI%>">Introducidos por el profesional</html:option>
								<html:option value="<%=Constantes.NO%>">Validados por CEIS</html:option>
							</html:select>
						</label>
						<br />
						<br />
						<%
							} else if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
						%>
						<label for="idVGerencia"> Ver m&eacute;ritos: <html:select
								property="BMCUsuario" styleClass="cbCuadros colocaVerMeritos"
								size="1" tabindex="11">
								<html:option value="<%=Constantes.SI%>">Introducidos por el profesional</html:option>
								<html:option value="<%=Constantes.NO%>">Validados por CEIS</html:option>
								<html:option value="<%=Constantes.CC%>">Validados por CC</html:option>
							</html:select>
						</label>
						<br />
						<br />
						<%
							} else {
						%>
						<html:hidden name="OCAPExpedientesForm" property="BMCUsuario" />
						<%
							}
						%>
					</logic:equal>
				</div>

				<div class="botonesPagina colocaBotonBusc">
					<!-- BOTON BUSCAR -->
					<div class="botonAccion">
						<span class="izqBoton"></span>
						<logic:notEqual name="modo" value="<%=Constantes.INF_FASEIII%>">
							<span class="cenBoton"> <a
								href="javascript:enviar('OCAPExpedientes.do?accion=listarExpedientes');"
								tabindex="11"> <img
									src="imagenes/imagenes_ocap/dobleFlecha.gif"
									class="colocaImgPrint" alt="Buscar" /> <span> Buscar </span>
							</a>
							</span>
						</logic:notEqual>
						<logic:equal name="modo" value="<%=Constantes.INF_FASEIII%>">
							<span class="cenBoton"> <a href="javascript:buscar();"
								tabindex="11"> <img
									src="imagenes/imagenes_ocap/dobleFlecha.gif"
									class="colocaImgPrint" alt="Buscar" /> <span> Buscar </span>
							</a>
							</span>
						</logic:equal>
						<span class="derBoton"></span>
					</div>

					<!-- BOTON LIMPIAR -->
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <logic:notPresent
								name="modo">
								<a
									href="javascript:enviar('OCAPExpedientes.do?accion=irBuscadorExpedientes');"
									tabindex="12">
							</logic:notPresent> <logic:present name="modo">
								<a
									href="javascript:enviar('OCAPExpedientes.do?accion=irBuscadorExpedientes&modo=<bean:write name="modo" />');"
									tabindex="12">
							</logic:present> <img src="imagenes/imagenes_ocap/aspa_roja.gif"
							class="colocaImgPrint" alt="Limpiar" /> <span> Limpiar </span> </a>
						</span> <span class="derBoton"></span>
					</div>
				</div>

			</fieldset>

			<logic:present name="listadoExpedientes">
				<fieldset>
					<bean:size id="tamano" name="listadoExpedientes" />
					<legend class="tituloLegend"> Listado de Usuarios </legend>

					<div class="textoRojo">
						<html:messages id="erroresListado" property="erroresListado"
							message="false">
							<bean:write name="erroresListado" />
							<br />
						</html:messages>
					</div>

					<div class="cuadroFieldset listadoSolicitudes">
						<div>
							<logic:equal name="tamano" value="0">
								<table border="0" cellpadding="2" cellspacing="0" width="82%"
									align="center">
									<tr>
										<td bgcolor="#FFFFFF" colspan="3" align="center"
											class="textoNegro reducirTexto2"><bean:message
												key="documento.noRegistros" arg0="Usuarios" /></td>
									</tr>
								</table>
							</logic:equal>

							<logic:notEqual name="tamano" value="0">
								<table class="tableInformesMotivados">
									<tr class="cabeceraTabla">
										<th class="tituloSolicitud"><a
											href="javascript:cambiarSeleccion();" tabindex="15">Cambiar</a>
										</th>
										<th class="tituloDNI">NIF/NIE</th>
										<th class="tituloNombre">Nombre</th>
										<th class="tituloApellidos">Apellidos</th>
										<%
											if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol()) ||Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
										%>
										<th class="tituloConvocatoria">Convocatoria</th>
										<%}	%>
									</tr>

									<logic:iterate id="expediente" name="listadoExpedientes">
										<tr class="cuerpoTabla">
											<td class="check"><html:multibox
													property="listaExpedientes">
													<bean:write name="expediente" property="CExpedienteId" />
												</html:multibox></td>
											<td class="barraLateral"><bean:write name="expediente"
													property="CDNIReal" /></td>
											<td class="barraLateral"><bean:write name="expediente"
													property="DNombre" /></td>
											<td class="barraLateral">
												<div class="campoApellido">
													<bean:write name="expediente" property="DApellidos" />
												</div>
											</td>
										<%
											if (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol()) ||Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
										%>
											<td class="barraLateral">
												<div class="campoConvocatoria">
													<bean:write name="expediente" property="DConvocatoria" />
												</div>
											</td>
										<%}	%>											
											
										</tr>
									</logic:iterate>
								</table>
							</logic:notEqual>
							<!-- tamano != 0 -->

						</div>
					</div>
					<br />

					<logic:notEqual name="tamano" value="0">
						<logic:equal name="modo" value="<%=Constantes.INF_CREAR_CV%>">
							<div class="botonesPagina colocaBotonBusc">
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="javascript:generarCVS();" tabindex="61"> <img
											src="imagenes/imagenes_ocap/flecha_correcto.gif"
											alt="Generar listado PDF" /> <span> Generar CV </span>
									</a>
									</span> <span class="derBoton"></span>
								</div>
							</div>
						</logic:equal>

						<logic:equal name="modo" value="<%=Constantes.INF_ETIQUETA%>">
							<div class="botonesPagina colocaBotonBusc">
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="javascript:enviar('OCAPExpedientes.do?accion=generarReportPDFExpedientesEtiquetas');"
										tabindex="61"> <img
											src="imagenes/imagenes_ocap/flecha_correcto.gif"
											alt="Generar listado PDF" /> <span> Generar Etiquetas
										</span>
									</a>
									</span> <span class="derBoton"></span>
								</div>
							</div>
						</logic:equal>

						<logic:equal name="modo" value="<%=Constantes.INF_FASEIII%>">
							<div class="botonesPagina colocaBotonBusc">
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="javascript:enviar('OCAPExpedientes.do?accion=generarReportPDFItinerariosEtiquetas');"
										tabindex="61"> <img
											src="imagenes/imagenes_ocap/flecha_correcto.gif"
											alt="Generar listado PDF" /> <span> Generar Etiquetas
										</span>
									</a>
									</span> <span class="derBoton"></span>
								</div>
							</div>
						</logic:equal>

						<logic:equal name="modo" value="<%=Constantes.INF_MOTIVADO%>">
							<div class="botonesPagina colocaBotonBusc">
								<div class="botonAccion">
									<span class="izqBoton"></span> <span class="cenBoton"> <a
										href="javascript:generarInformesMotivados();" tabindex="61">
											<img src="imagenes/imagenes_ocap/flecha_correcto.gif"
											alt="Generar listado PDF" /> <span> Generar Informes
										</span>
									</a>
									</span> <span class="derBoton"></span>
								</div>
							</div>
						</logic:equal>
					</logic:notEqual>
				</fieldset>

			</logic:present>

		</html:form>

	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->