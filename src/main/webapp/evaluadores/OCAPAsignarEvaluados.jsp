<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>
<% 
  String convocaAux = "";
  String convocaAct = "";  
  
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>
<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script>

function cargarComboItinerarios(){
   enviar('OCAPEvaluadores.do?accion=cargarComboItinerariosEvaluador');
}

function buscar(){

   if (document.forms[0].CConvocatoria_id.value== 0) {
        alert('Debe seleeccionar una Convocatoria.');
   }else if (document.forms[0].CItinerario_id.value == 0) {
        alert('Debe seleeccionar un Manual.');
   }else{
     enviar('OCAPEvaluadores.do?accion=listarPosiblesEvaluados');
   }

}

</script>
<div class="contenido listadoGestionEvaluados">
	<div class="contenidoListadoAspirantesGCP listadoEvaluadoresCTE">
		<html:form action="/OCAPEvaluadores.do">
			<h3 class="subTituloContenido">Asignaci&oacute;n de Evaluados</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>
			<html:hidden property="CCompfqs_id" />
      &nbsp;
      
       <fieldset>
				<legend class="tituloLegend"> Buscador </legend>

				<div class="cuadroFieldset">
					<div class="todo">
						<label for="idConv" class="nombreMediano">Convocatoria: *</label>
						<html:select property="CConvocatoria_id"
							styleClass="cbCuadros colocaConvocatoriaCBEvaluadoresLs" size="1"
							tabindex="10" onchange="javascript:cargarComboItinerarios();">
							<html:option value="0">Seleccione...</html:option>
							<html:options
								collection="<%=Constantes.COMBO_CONVOCATORIAS_EVAL%>"
								property="CConvocatoriaId" labelProperty="DNombre" />
						</html:select>
					</div>

					<%if(((ArrayList) session.getAttribute(Constantes.COMBO_ITINERARIOS)).size() > 0 ){%>
					<br />
					<br />
					<div class="todo">
						<label class="nombreMediano" for="idVEspecialidad">Manual:
							*</label>

						<html:select property="CItinerario_id"
							styleClass="cbCuadros colocaComiteCBEvaluadoresLs" size="1"
							tabindex="12">
							<html:option value="0">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_ITINERARIOS%>"
								property="CItinerarioId" labelProperty="DDescripcion" />
						</html:select>
					</div>
					<%}%>
				</div>

				<div class="botonesPagina colocaBotonBusc">
					<!-- BOTON BUSCAR -->
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:buscar();" tabindex="11"> <img
								src="imagenes/imagenes_ocap/dobleFlecha.gif"
								class="colocaImgPrint" alt="Buscar" /> <span> Buscar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>

					<div class="botonesPagina colocaBotonBusc">
						<!-- BOTON BUSCAR -->
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviar('OCAPEvaluadores.do?accion=buscar&recuperarBusquedaAnterior=Y&opcion=cp');"
								tabindex="11"> <img
									src="imagenes/imagenes_ocap/dobleFlecha.gif"
									class="colocaImgPrint" alt="Buscar" /> <span> Volver </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</div>
				</div>
			</fieldset>
			<logic:present name="listaPosiblesEvaluados">
				<bean:size id="tamano" name="listaPosiblesEvaluados" />
				<fieldset>
					<legend class="tituloLegend"> Listado de Evaluados No
						Asignados</legend>



					<div class="cuadroFieldset listadoSolicitudes">
						<div>
							<logic:equal name="tamano" value="0">
								<table border="0" cellpadding="2" cellspacing="0" width="82%"
									align="center">
									<tr>
										<td bgcolor="#FFFFFF" colspan="3" align="center"
											class="textoNegro reducirTexto2"><bean:message
												key="documento.noRegistros" arg0="Evaluados No Asignados" /></td>
									</tr>
								</table>
							</logic:equal>

							<logic:notEqual name="tamano" value="0">
								<table class="tableInformesMotivados">
									<tr class="cabeceraTabla">
										<th class="tituloSolicitud"></th>
										<th class="tituloDNI">NIF/NIE</th>
										<th class="tituloNombre">Nombre</th>
										<th class="tituloApellidos">Apellidos</th>
									</tr>

									<logic:iterate id="evaluado" name="listaPosiblesEvaluados">
										<tr class="cuerpoTabla">
											<td class="check"><html:multibox
													property="listaPosiblesEvaluadosSelec">
													<bean:write name="evaluado" property="CExpId" />
												</html:multibox></td>
											<td class="barraLateral"><bean:write name="evaluado"
													property="CDni" /></td>
											<td class="barraLateral"><bean:write name="evaluado"
													property="DNombre" /></td>
											<td class="barraLateral"><bean:write name="evaluado"
													property="DApellido1" /></td>
										</tr>
									</logic:iterate>
								</table>
							</logic:notEqual>
							<!-- tamano != 0 -->

						</div>
					</div>
					<br />
				</fieldset>
				<logic:notEqual name="tamano" value="0">
					<div class="botonesPagina colocaBotonBusc">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:enviar('OCAPEvaluadores.do?accion=asignarEvaluados');"
								tabindex="61"> <img
									src="imagenes/imagenes_ocap/flecha_correcto.gif"
									alt="Asignar Evaluados " /> <span> Asignar Evaluados </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
					</div>
				</logic:notEqual>

			</logic:present>


		</html:form>
	</div>
</div>