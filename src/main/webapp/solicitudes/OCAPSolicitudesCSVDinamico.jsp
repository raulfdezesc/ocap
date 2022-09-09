<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT"%>
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


<script language="JavaScript">


function validarCampos(){
   var  formu = document.forms[0];
  <% if (Constantes.LISTADOGRS.equals( request.getParameter("opcion")) || Constantes.LISTADOFQS.equals( request.getParameter("opcion"))){ %>
      if (document.forms[0].BNifNie.checked==false && document.forms[0].BApellidos.checked==false && 
              document.forms[0].BNombre.checked==false && document.forms[0].BCSVSexo.checked==false &&
              document.forms[0].BCategoria.checked==false && document.forms[0].BEspecialidad.checked==false &&
              document.forms[0].BSituacionAdm.checked==false && document.forms[0].BProcedimiento.checked==false && 
              document.forms[0].BGerencia.checked==false && document.forms[0].BEstado.checked==false&&
              document.forms[0].BCausas.checked==false && document.forms[0].BConvocatoria.checked==false&&
              document.forms[0].BFechaSolic.checked==false && document.forms[0].BJuridico.checked==false&&
              document.forms[0].BAnios.checked==false && document.forms[0].BCentro.checked==false&& 
              document.forms[0].BServicio.checked==false && document.forms[0].BPuesto.checked==false &&
              document.forms[0].BCategoria.checked==false && document.forms[0].BEspecialidad.checked==false &&
              document.forms[0].BSituacionAdm.checked==false && document.forms[0].BProcedimiento.checked==false && 
              document.forms[0].BGerencia.checked==false && document.forms[0].BEstado.checked==false&&
              document.forms[0].BConvocatoria.checked==false&& document.forms[0].BJuridico.checked==false&&
              document.forms[0].BCentro.checked==false && document.forms[0].BServicio.checked==false&&
              document.forms[0].BPuesto.checked==false && document.forms[0].BEpr.checked==false ) {   
       alert('Debe seleccionar al menos alguna casilla de los campos que se van mostrar en el Informe');
       document.forms[0].BNifNie.focus();
       return false;
     }else if(document.forms[0].CEstadoFiltro.selectedIndex!=0 && document.forms[0].CEstadoHistFiltro.selectedIndex!=0) {
    	 alert('Los filtros Estado e Estado Hist\u00f3rico son excluyentes. Para generar el CSV uno de ellos debe seleccionar la opci\u00f3n por defecto.');
    	 document.forms[0].CEstadoFiltro.focus();
    	 return false;
     }else
       return true;
  <% }else if (Constantes.LISTADOLDAP.equals(request.getParameter("opcion"))){ %>
      if (document.forms[0].BNifNie.checked==false && document.forms[0].BApellidos.checked==false && 
              document.forms[0].BNombre.checked==false && document.forms[0].BCSVSexo.checked==false &&
              document.forms[0].BCategoria.checked==false && document.forms[0].BEspecialidad.checked==false &&
              document.forms[0].BSituacionAdm.checked==false && document.forms[0].BProcedimiento.checked==false && 
              document.forms[0].BGerencia.checked==false && document.forms[0].BEstado.checked==false&&
              document.forms[0].BCausas.checked==false && document.forms[0].BConvocatoria.checked==false&&
              document.forms[0].BFechaSolic.checked==false && document.forms[0].BJuridico.checked==false&&
              document.forms[0].BAnios.checked==false && document.forms[0].BCentro.checked==false&& 
              document.forms[0].BServicio.checked==false&& document.forms[0].BPuesto.checked==false) {   
       alert('Debe seleccionar al menos alguna casilla de los campos que se van mostrar en el Informe');
       document.forms[0].BNifNie.focus();
       return false;
     }else if(document.forms[0].CEstadoFiltro.selectedIndex!=0 && document.forms[0].CEstadoHistFiltro.selectedIndex!=0) {
    	 alert('Los filtros Estado e Estado Hist\u00f3rico son excluyentes. Para generar el CSV uno de ellos debe seleccionar la opci\u00f3n por defecto.');
    	 document.forms[0].CEstadoFiltro.focus();
    	 return false;
     }else
       return true;
       
   <% } %>
  
}

function cargarComboEspecialidades(){
   enviar('OCAPSolicitudes.do?accion=cargarCombosEspecialidadesPuestos&CProfesional_id='+ document.forms[0].CProfesional_id.value +'&jspVuelta=irCSVDinamico&opcion=<%= request.getParameter("opcion") %>');
}



function limpiar(){  
   if(document.forms[0].CGrado_id)
    document.forms[0].CGrado_id.selectedIndex=0;
   if(document.forms[0].CConvocatoriaId)
    document.forms[0].CConvocatoriaId.selectedIndex=0;
   if(document.forms[0].CEstado)
    document.forms[0].CEstado.selectedIndex=0;
   if(document.forms[0].CJuridico)
    document.forms[0].CJuridico.selectedIndex=0;
   if(document.forms[0].BNifNie)
    document.forms[0].BNifNie.checked=false;
   if(document.forms[0].BApellidos)
    document.forms[0].BApellidos.checked=false;
   if(document.forms[0].BNombre)
    document.forms[0].BNombre.checked=false;
   if(document.forms[0].BCSVSexo)
    document.forms[0].BCSVSexo.checked=false;
   if(document.forms[0].BCategoria)
    document.forms[0].BCategoria.checked=false;
   if(document.forms[0].BEspecialidad)
    document.forms[0].BEspecialidad.checked=false;
   if(document.forms[0].BSituacionAdm)
    document.forms[0].BSituacionAdm.checked=false;
   if(document.forms[0].BProcedimiento)
    document.forms[0].BProcedimiento.checked=false;
   if(document.forms[0].BGerencia)
    document.forms[0].BGerencia.checked=false;
   if(document.forms[0].BEstado)
    document.forms[0].BEstado.checked=false;
   if(document.forms[0].BModalidad)
    document.forms[0].BModalidad.checked=false;
   if(document.forms[0].BServicio)
    document.forms[0].BServicio.checked=false;
   if(document.forms[0].BPuesto)
    document.forms[0].BPuesto.checked=false;
   if(document.forms[0].BCausas)
    document.forms[0].BCausas.checked=false;
   if(document.forms[0].BConvocatoria)
    document.forms[0].BConvocatoria.checked=false;
   if(document.forms[0].BFechaSolic)
    document.forms[0].BFechaSolic.checked=false;
   if(document.forms[0].BJuridico)
    document.forms[0].BJuridico.checked=false;
   if(document.forms[0].BAnios)
    document.forms[0].BAnios.checked=false;   
   if(document.forms[0].BCentro)
    document.forms[0].BCentro.checked=false; 
   if(document.forms[0].BSexo)
    document.forms[0].BSexo.selectedIndex=0;
   if(document.forms[0].BEpr)
    document.forms[0].BEpr.checked=false; 
   if(document.forms[0].CProfesional_id)
    document.forms[0].CProfesional_id.selectedIndex=0;
   if(document.forms[0].CEspec_id)
    document.forms[0].CEspec_id.selectedIndex=0;
   if(document.forms[0].CSitAdministrativaAuxId)
    document.forms[0].CSitAdministrativaAuxId.selectedIndex=0;
   if(document.forms[0].CGerencia_id)
    document.forms[0].CGerencia_id.selectedIndex=0;
   if(document.forms[0].CGerenciaFiltro_id)
    document.forms[0].CGerenciaFiltro_id.selectedIndex=0;
   if(document.forms[0].CEstadoFiltro)
    document.forms[0].CEstadoFiltro.selectedIndex=0;
   if(document.forms[0].CEstadoHistFiltro)
	document.forms[0].CEstadoHistFiltro.selectedIndex=0;
   if(document.forms[0].CProcedimientoFiltro)
    document.forms[0].CProcedimientoFiltro.selectedIndex=0;
   if(document.forms[0].CProcedimientoId)
    document.forms[0].CProcedimientoId.selectedIndex=0;
   
}

function generarCSVDinamico(){
   if(validarCampos()){
      enviar("OCAPSolicitudes.do?accion=generarPDFDinamicoGrs&jspAccion=OCAPSolicitudesLs&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>&opcion=<%=request.getParameter("opcion")%>&CSV=<%=Constantes.SI%>");
   }
}

</script>

<div class="contenido">
	<div class="contenidoListadoDinamico">

		<html:form action="/OCAPSolicitudes.do">

			<h2 class="tituloContenido">Listados Din&aacute;micos</h2>

			<fieldset>
				<legend class="tituloLegend"> Generaci&oacute;n Listados
					Din&aacute;micos</legend>
				<div class="espaciador"></div>
				<span class="subTituloListado">Seleccione una opci&oacute;n
					en los siguientes campos</span>

				<div class="cuadroFieldset">

					<!-- GRADO -->
					<label for="idVGrado"> Grado: <html:select
							property="CGrado_id" styleClass="cbCuadros colocaGradoBuscCB"
							size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_GRADOS_CONSULTA%>"
								property="CGradoId" labelProperty="DDescripcion" />
						</html:select>
					</label>

					<!-- CONVOCATORIA -->
					<label for="idVConvocatoria"> Convocatoria: <html:select
							property="CConvocatoriaId"
							styleClass="cbCuadros colocaConvocatoriaCB" size="1">
							<html:option value="">Todas</html:option>
							<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
								property="CConvocatoriaId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br />

					<!-- GERENCIA -->
					<% if(jcylUsuario.getUser().getRol() != null && !(jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE))){ %>
					<label for="idVGerencia"> Gerencia: <html:select
							property="CGerenciaFiltro_id"
							styleClass="cbCuadros colocaGerencia" size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_GERENCIA%>"
								property="CGerenciaId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br />
					<%}%>

					<!-- REGIMEN JURIDICO -->
					<label for="idVCategoria">R&eacute;gimen jur&iacute;dico: <html:select
							property="CJuridico"
							styleClass="cbCuadros colocaRegimenJuridicoCSV">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_REGIMEN%>"
								property="CJuridicoId" labelProperty="DDescripcion" />
						</html:select>
					</label>


					<!-- SITUACION ADMINISTRATIVA -->
					<label for="idVCategoria">Situaci&oacute;n Administrativa:
						<html:select property="CSitAdministrativaAuxId"
							styleClass="cbCuadros colocaSituacionAdministrativaCSV">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_SIT_ADMIN%>"
								property="CSit_AdministrativaId" labelProperty="DDescripcion" />
						</html:select>
					</label> <br />
					<br />

					<!-- ESTADO -->
					<label for="idVEstado"> Estado: <html:select
							property="CEstadoFiltro" styleClass="cbCuadros colocaEstadoCSV">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_ESTADOS_EXTENDIDO%>"
								property="cEstadoId" labelProperty="DNombre" />
						</html:select>
					</label>
					
					<!-- ESTADO HISTORICO -->
					<label for="idVEstado"> Estado Hist&oacute;rico: <html:select
							property="CEstadoHistFiltro" styleClass="cbCuadros colocaEstadoHistCSV">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_ESTADOS%>"
								property="cEstadoId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br />

					<!-- PROCEDIMIENTO DE EVALUACION -->
					<label for="idVCategoria">Procedimiento de
						evaluaci&oacute;n por el que opta: <html:select
							property="CProcedimientoFiltro"
							styleClass="cbCuadros colocaProcEvaluaCSV2" size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_PERSONAL%>"
								property="CProcedimientoId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br />
				</div>

				<span class="subTituloListado">A continuaci&oacute;n
					seleccione las casillas de los campos que desea mostrar en el CSV:
				</span>

				<div class="cuadroFieldset">


					<!-- NIF / NIE -->
					<html:checkbox property="BNifNie" />
					<span class="negrita colocaNifCSVDinamico">NIF/NIE</span>

					<!-- APELLIDOS -->
					<html:checkbox property="BApellidos" />
					<span class="negrita colocaApellidosCSVDinamico">Apellidos</span>

					<!-- NOMBRE -->
					<html:checkbox property="BNombre" />
					<span class="negrita colocaNombreCSVDinamico">Nombre</span>

					<!-- SEXO -->
					<html:checkbox property="BCSVSexo" />
					<label for="idVCorreo">Sexo: * <html:select
							styleClass="cbCuadros colocaSexoCB" name="OCAPSolicitudesForm"
							property="BSexo" tabindex="4">
							<html:option value="">Seleccione...</html:option>
							<html:option value="<%=Constantes.SEXO_HOMBRE_VALUE%>"><%=Constantes.SEXO_HOMBRE%></html:option>
							<html:option value="<%=Constantes.SEXO_MUJER_VALUE%>"><%=Constantes.SEXO_MUJER%></html:option>
						</html:select>
					</label> <br />
					<br />


					<!-- CATEGORIA -->
					<html:checkbox property="BCategoria" />
					<label for="idVCategoria"> Categor&iacute;a: <html:select
							property="CProfesional_id"
							styleClass="cbCuadros colocaCategoriaCB" size="1"
							onchange="javascript:cargarComboEspecialidades();">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
								property="CProfesionalId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br />

					<!-- ESPECIALIDAD -->
					<html:checkbox property="BEspecialidad" />
					<label for="idVEspecialidad"> Especialidad: <html:select
							property="CEspec_id" styleClass="cbCuadros colocaEspecialidadCSV"
							size="1">
							<html:option value="">Seleccione...</html:option>
							<html:options collection="<%=Constantes.COMBO_ESPECIALIDAD%>"
								property="CEspecId" labelProperty="DNombre" />
						</html:select>
					</label> <br />
					<br />

					<!-- SITUACION ADMINISTRATIVA -->
					<html:checkbox property="BSituacionAdm" />
					<label class="colocaSitAdminCSV" for="idVSituacionAdm">Situaci&oacute;n
						Administrativa</label>

					<!-- MODALIDAD -->
					<html:checkbox property="BModalidad" />
					<label class="colocaModalidadCSV" for="idVModalidad">Modalidad</label>

					<!-- ESTADO -->
					<html:checkbox property="BEstado" styleClass="checkEstadoCSV" />
					<label for="idVEstado"> Estado </label> <br />
					<br />

					<!-- PROCEDIMIENTO DE EVALUACION -->
					<html:checkbox property="BProcedimiento" />
					<label class="colocaProcEvalCSV" for="idVCategoria">Procedimiento
						de evaluaci&oacute;n por el que opta </label>


					<!-- GERENCIA -->
					<html:checkbox property="BGerencia" />
					<label for="idVCategoria"> Gerencia </label> <br />
					<br />

					<!-- CONVOCATORIA -->
					<html:checkbox property="BConvocatoria" />
					<label class="colocaConvocatoriaCSV" for="idVCategoria">
						Convocatoria </label>

					<!-- FECHA DE SOLICITUD -->
					<html:checkbox property="BFechaSolic" />
					<label class="colocaFechaSolicitudCSV" for="idVCategoria">
						Fecha de solicitud </label>


					<!-- REGIMEN JURIDICO -->
					<html:checkbox property="BJuridico" />
					<label for="idVSituacionAdm"> R&eacute;gimen
						jur&iacute;dico</label> <br />
					<br />

					<!-- CENTRO -->
					<html:checkbox property="BCentro" />
					<label class="colocaCentroCSV" for="idVSituacionAdm">
						Centro</label>

					<!-- SERVICIO -->
					<html:checkbox property="BServicio" />
					<label class="colocaServicioCSV" for="idVServicio">Servicio</label>

					<!-- PUESTO -->
					<html:checkbox property="BPuesto" />
					<label class="colocaPuestoCSV" for="idVPuesto">Puesto</label> <br />
					<br />


					<!-- A�OS DE EJERCICIO -->
					<html:checkbox property="BAnios" />
					<label class="colocaAniosEjercicioCSV" for="idVSituacionAdm">
						A&ntilde;os de ejercicio</label>



					<!-- CAUSAS DE EXCLUSION -->
					<html:checkbox property="BCausas" />
					<label class="colocaCausaExclusionCSV" for="idVSituacionAdm">
						Causas de exclusi&oacute;n</label>



					<!-- EPR -->
					<html:checkbox property="BEpr" />
					<label class="colocaEprCSV" for="idVEpr">EPR</label> <br />
					<br />



				</div>

				<div class="botonesPagina colocaBotonBusc">

					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:generarCSVDinamico();"> <img
								src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Generar listado CSV" /> <span> Generar CSV </span>
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

		</html:form>

	</div>
	<!-- /fin de ContenidoListadoAspirantesGCP -->
</div>
<!-- /Fin de Contenido -->