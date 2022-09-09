<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/formularios.js'/>"></script>
<script>

function eliminarEspacios(cadena){
   for(i=0; i<cadena.length; ){ 
     if(cadena.charAt(i)==" ") 
         cadena=cadena.substring(i+1, cadena.length); 
     else 
         break; 
   }
   return cadena;
}

function elmiminarEspaciosAlaDerecha(cadena){ 
var temp = "";
for( var i=cadena.length - 1; i >= 0; i-- ){ 
   if ( cadena.charAt(i) != ' ' ){ 
   temp = cadena.substring(0,i); 
   break; 
   }
}
return temp; 
} 

function validar() {

    var validacion = 0;

    var datosMiembro = document.OCAPMiembrosComitesForm.DDatosMiembro.value;

    datosMiembro = datosMiembro.replace('\r', '');
    datosMiembro = datosMiembro.replace('\n', '');

    if (datosMiembro != "") {
        var numeroPuntos = 0;
        // OBTENEMOS EL NUMERO DE ';' QUE SE HAN INTRODUCIDO Y COMPROBAMOS QUE SU NUMERO SEA CORRECTO
        for (var j = 0; j < datosMiembro.length; j++) {
            if (datosMiembro.charAt(j) == ';')
                numeroPuntos = numeroPuntos + 1;
        }
        if (numeroPuntos % 4 != 0) {
            alert('Debe introducir los Datos del Miembro con el formato correcto');
            validacion = 1;
        }
    }

    if (validacion == 0) {
        if (datosMiembro.indexOf(";") != -1) {
            for (var i = 0; i < 100; i++) {
                for (var z = 0; z < 4; z++) {
                    var posicion = datosMiembro.indexOf(";");
                    var tamano = datosMiembro.length;
                    if (posicion != -1) {
                        if (z == 0) {
                            // NOMBRE
                            if (trim(datosMiembro.substr(0, posicion)) == '') {
                                alert('Debe introducir los Datos del Miembro con el formato correcto');
                                validacion = 1;
                                z = 4;
                                i = 100;
                            }
                        } else if (z == 1) {
                            // APELLIDOS
                            if (trim(datosMiembro.substr(0, posicion)) == '') {
                                alert('Debe introducir los Datos del Miembro con el formato correcto');
                                validacion = 1;
                                z = 4;
                                i = 100;
                            }
                        } else if (z == 2) {
                            // TIPO - QUITAMOS LOS ESPACION EN CASO DE QUE HAYA
                            var cadena = eliminarEspacios(datosMiembro.substr(0, posicion));
                            if (cadena == '' || cadena.length > 1 || (cadena != '<%=Constantes.PRESIDENTE%>' &&
                                    cadena != '<%=Constantes.VOCAL%>' &&
                                    cadena != '<%=Constantes.SECRETARIO%>')) {
                                alert('Debe introducir el caracter del Tipo de Miembro en el formato correcto');
                                validacion = 1;
                                z = 4;
                                i = 100;
                            }
                        } else if (z == 3) {
                            // SEXO - QUITAMOS LOS ESPACION EN CASO DE QUE HAYA
                            var cadena = eliminarEspacios(datosMiembro.substr(0, posicion));
                            if (cadena == '' || cadena.length > 1 || (cadena != '<%=Constantes.SEXO_HOMBRE_VALUE%>' && cadena != '<%=Constantes.SEXO_MUJER_VALUE%>')) {
                                alert('Debe introducir el caracter del Sexo del Miembro en el formato correcto.');
                                validacion = 1;
                                z = 4;
                                i = 100;
                            }
                        }
                        datosMiembro = datosMiembro.substring(posicion + 1, tamano);
                    } else {
                        // SI datosMiembro NO TIENE ';' ROMPEMOS EL BUCLE                           
                        datosMiembro = elmiminarEspaciosAlaDerecha(datosMiembro);

                        while (datosMiembro.indexOf('\r') != -1) {
                            datosMiembro = datosMiembro.replace('\r', '');
                            datosMiembro = datosMiembro.replace('\n', '');
                        }

                        if (datosMiembro != '') {
                            alert('Debe introducir los Datos del Miembro con el formato correcto');
                            validacion = 1;
                        }
                        z = 4;
                        i = 100;
                    }
                } // for
            } // for
        } // if
    } // if

    datosMiembro = document.OCAPMiembrosComitesForm.DDatosMiembro.value;

        if (validacion == 0) {
            // Validacion de los datos del textArea   
            if (datosMiembro == "") {
                alert('Debe introducir los Datos del Miembro');
                validacion = 1;
            } else if (datosMiembro.indexOf(';') == -1) {
                alert('Debe introducir los Datos del Miembro con el formato correcto');
                validacion = 1;
            } else if (datosMiembro == "") {
                alert('Debe seleccionar un valor en el campo Categoría');
                validacion = 1;
            } else if (document.OCAPMiembrosComitesForm.CProfesionalId.value == '') {
                alert('Debe seleccionar un valor en el campo Categoría');
                validacion = 1;
            } else if (document.OCAPMiembrosComitesForm.CConvocatoria.value == '') {
                alert('Debe seleccionar un valor en el campo Convocatoria');
                validacion = 1;
            }
        

        if (validacion == 0) {
            enviar('OCAPMiembrosComites.do?accion=insertar');
        }
    }
}

	function eliminar(cMiembroId) {

		if (confirm('Va a ELIMINAR el registro selecionado')) {
			enviar('OCAPMiembrosComites.do?accion=eliminar&cMiembroIdS='
					+ cMiembroId);
		}

	}
	


	function ficheroExcel() {
			 document.OCAPMiembrosComitesForm.DDatosMiembro.value="";
			 
	
		var fullPath = document.getElementById('fileExcel').value;
		if (fullPath!="") {
		        enviar('OCAPMiembrosComites.do?accion=file');
		}else{
		document.OCAPMiembrosComitesForm.DDatosMiembro.value="";
		}
	}
	
	
</script>

<div class="contenido">
	<div class="cuadroContenedor cargaMiembros">
		<html:form action="/OCAPMiembrosComites.do" method="post"
			enctype="multipart/form-data">
			<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
				<h3 class="subTituloContenido">Carga Miembros</h3>
				<div class="lineaBajaM"></div>
				<div class="espaciador"></div>
			</logic:equal>

			<fieldset>
				<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
					<legend class="tituloLegend">Datos a insertar</legend>
				</logic:equal>
				<br /> <label class="obligado">Introduzca los datos del
					miembro que desea cargar (Formato: Nombre; Apellidos; Tipo [P,V,S];
					Sexo [H, M]; ) *</label> <br /> <br />
				<html:textarea styleClass="cajaTexto" property="DDatosMiembro"
					tabindex="18" cols="128" rows="9" />
				&nbsp; <br /> <br /> <label for="idVCategoria" class="obligado">
					Categor&iacute;a: * <html:select property="CProfesionalId"
						styleClass="cbCuadros colocaCategoriaCBCEIS" size="1" 
						onchange="enviar('OCAPMiembrosComites.do?accion=cargarMiembros');">
						<html:option value="">Seleccione...</html:option>
						<html:options collection="<%=Constantes.COMBO_CATEGORIA%>"
							property="CProfesionalId" labelProperty="DNombre" />
					</html:select>
				</label> <br /> <br /> <label for="idVConvocatoria" class="obligado">
					Convocatoria: * <html:select property="CConvocatoria"
						styleClass="cbCuadros colocaConvocatoriaCB" size="1"
						onchange="enviar('OCAPMiembrosComites.do?accion=cargarMiembros');">
						<html:option value="">Seleccione...</html:option>
						<html:options collection="<%=Constantes.COMBO_CONVOCATORIAS%>"
							property="CConvocatoriaId" labelProperty="DNombre" />
					</html:select>
				</label> <br /> <br /> <br /> <label class="obligado">Los campos
					se&ntilde;alados con * son obligatorios</label> <br /> <br />

				<div class="botonesPagina">
					<logic:equal name="tipoAccion" value="<%=Constantes.INSERTAR%>">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="PaginaInicio.do"> <img class="colocaImgPrint"
									src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Cancelar" /> <span>
										Cancelar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <a
								href="javascript:validar();"> <img class="colocaImgPrint"
									src="imagenes/imagenes_ocap/icon_accept.gif" alt="Aceptar" />
									<span> Aceptar </span>
							</a>
							</span> <span class="derBoton"></span>
						</div>

						<div class="hiddenInput">
							<label for="fileExcel" class="botonAccion" style="cursor: pointer;">
							 <span class="izqBoton">
							 </span> 
							 <span class="cenBoton"> <img
									style="margin-bottom: 4px;width: 14px;" class="colocaImgPrint"
									src="imagenes/imagenes_ocap/activacion.gif" alt="Aceptar" /> <span>
										Importar Miembros</span>
							</span>
							 <span class="derBoton">
							 
							 </span>
							</label>
								<html:file property="archivoExcel" styleId="fileExcel"
								onchange='javascript:ficheroExcel();'  accept=".csv,.xls"/>
						</div>
						<html:text value="Seleccione el fichero con formato *.csv o *.xls" property="dFichero" styleClass="textoNombre" readonly="true"></html:text>

					</logic:equal>
				</div>

				<br /> <br /> <br /> <br />

			</fieldset>

			<fieldset>
				<bean:size id="tamano" name="listadoMiembros" />
				<legend class="tituloLegend"> Listado de Miembros </legend>
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
							<table class="tablaCarga">
								<tr class="datosTitulo lineaBajaM">
									<th class="tituloApellidos">Apellidos</th>
									<th class="tituloNombre">Nombre</th>
									<th class="tituloNombre">Tipo
									</td>
									<th class="tituloApellidos">Sexo</th>
									<th class="tituloNombre">Convocatoria</th>
									<th class="tituloNombre">Categor&iacute;a</th>
									<th></th>
								</tr>

								<logic:iterate id="miembro" name="listadoMiembros">
									<tr class="datosAsp">
										<td class="barraLateral"><bean:write name="miembro"
												property="DApellidos" /></td>
										<td class="barraLateral"><bean:write name="miembro"
												property="DNombre" /></td>
										<td class="barraLateral"><bean:write name="miembro"
												property="CTipo" /></td>
										<td class="barraLateral"><bean:write name="miembro"
												property="CSexo" /></td>
										<td class="barraLateral"><bean:write name="miembro"
												property="DConvocatoria" /></td>
										<td class="barraLateral"><bean:write name="miembro"
												property="DProfesional" /></td>
										<td class="separador" headers="bActivado"><a
											href="javascript:eliminar('<bean:write name="miembro" property="CMiembroId"/>');">
												<img class="imagenIzda" src="imagenes/eliminar.gif"
												title="Eliminar Miembro" alt="Eliminar Miembro">
										</a></td>
									</tr>
								</logic:iterate>
							</table>
							<%@ include file="/comun/paginacion.jsp"%>
						</logic:notEqual>
						<!-- tamano != 0 -->

					</div>
				</div>

			</fieldset>

		</html:form>
	</div>
</div>
<!-- /Fin de ocultarCampo -->
