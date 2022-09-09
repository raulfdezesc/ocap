<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.actas.OCAPActasOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.actas.OCAPActasTiposOT"%>
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
function limpiarActas(){
	   
	   document.forms[0].CGradoId.options[0].selected='selected';
	   document.forms[0].CConvocatoriaId.options[0].selected='selected';
	   document.forms[0].CProfesionalId.options[0].selected='selected';
	   document.forms[0].CActaTipo.options[0].selected='selected';
	}

	function validarBuscador(formu){

	   // CONVOCATORIA
	   var cadena = formu.CConvocatoriaId.value;
	   
	   if(cadena!='') {
	      cadena = trim(cadena);
	      if(cadena=='') {
	         alert('Debe rellenar el campo \'Convocatoria\'.');
	         return false;
	      }
	   } else {
		   alert('Debe rellenar el campo \'Convocatoria\'.');
		   return false;
	   }
	   
	   // CATEGORIA
	   var cadena = formu.CProfesionalId.value;
	   
	   if(cadena!='') {
	      cadena = trim(cadena);
	      if(cadena=='') {
	         alert('Debe rellenar el campo \'Categoría\'.');
	         return false;
	      }
	   } else {
		   alert('Debe rellenar el campo \'Categoría\'.');
		   return false;
	   }	
	   // TIPO ACTA
	   var cadena = formu.CActaTipo.value;
	   
	   if(cadena!='') {
	      cadena = trim(cadena);
	      if(cadena=='') {
	         alert('Debe rellenar el campo \'Tipo Acta\'.');
	         return false;
	      }
	   } else {
		   alert('Debe rellenar el campo \'Tipo Acta\'.');
		   return false;
	   }		   


	   return true;
	}

	function buscarActas(){
	   if(validarBuscador(document.forms[0])){
	      enviar('OCAPActas.do?accion=buscarActas');
	   }
	}

</script>

<div class="contenido generarActas">
	<html:form action="/OCAPActas.do">
		<html:hidden name="OCAPActasForm" property="CGerenciaId" />
		<h2 class="tituloContenido">CONSULTA DE ACTAS</h2>

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
				</label> <br />
				<br /> <label for="idVTipo"> Tipo Acta: * <html:select
						property="CActaTipo"
						styleClass="cbCuadros colocaCategoriaGenActas" size="1"
						onchange="javascript:cargarTipos();">
						<html:option value="">Seleccione...</html:option>
						<html:options collection="<%=Constantes.COMBO_TIPO%>"
							property="CActaTipo" labelProperty="DNombre" />
					</html:select>
				</label> <br />				
			</div>
				<div class="botonesPagina colocaBotonBusc">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:buscarActas();" tabindex="61"> <img
								src="imagenes/imagenes_ocap/dobleFlecha.gif"
								class="colocaImgPrint" alt="Buscar" /> <span> Buscar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <a
							href="javascript:limpiarActas();" tabindex="61"> <img
								src="imagenes/imagenes_ocap/aspa_roja.gif"
								class="colocaImgPrint" alt="Limpiar" /> <span> Limpiar </span>
						</a>
						</span> <span class="derBoton"></span>
					</div>
				</div>			
		</fieldset>
		
			<logic:equal name="OCAPActasForm" property="jspInicio"
				value="false">
				<fieldset>
					<bean:size id="tamano" name="listados" property="elementos" />
					<legend class="tituloLegend"> Listado de Actas </legend>
					<div class="cuadroFieldset cuadroInferior">
						<div>
							<logic:equal name="tamano" value="0">
								<table border="0" cellpadding="2" cellspacing="0" width="82%"
									align="center">
									<tr>
										<td bgcolor="#FFFFFF" colspan="3" align="center"
											class="textoNegro"><bean:message
												key="documento.noActas" /></td>
									</tr>
								</table>
							</logic:equal>
							<logic:notEqual name="tamano" value="0">
								
									<table class="tablaConsultas tabla5">
										<tr class="cabeceraTabla">
											<th class="col1" id="grado">Grado</th>
											<th class="col2" id="fsesion">Fecha de Sesi&oacute;n</th>
											<th class="col3" id="fgeneracion" colspan="2">Fecha de Generaci&oacute;n</th>
											<th class="col5" id="cEstat"></th>
											
										</tr>
										<logic:iterate id="listaTotal" name="listados"
											property="elementos" type="OCAPActasOT">
											<html:hidden name="listaTotal" property="CActaId" />
											<tr class="cuerpoTabla">
												<td class="col1"><bean:write name="listaTotal"
														property="DGrado" /></td>
												<td class="col2"><bean:write name="listaTotal"
														property="FSesionImprimible" /></td>														
												<td class="col3" colspan="2"><bean:write name="listaTotal"
														property="FGeneracion" /></td>																												
												<td class="imagen lateralImagen"><a
													href="OCAPActas.do?accion=generarPDFActa&cActaId=<bean:write name="listaTotal" property="CActaId"/>">Ver</a>
												</td>
											</tr>
										</logic:iterate>
									</table>

								
							</logic:notEqual>
							<!-- tamano != 0 -->
						</div>
					</div>
				<logic:present name="listados" property="elementos">
					<div class=""><%@ include file="/comun/paginacion.jsp"%></div>
				</logic:present>					
				</fieldset>
				
			</logic:equal>		
		
		
	</html:form>
</div>
<!-- /Fin de Contenido -->