<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>

<script src="javascript/validate.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoDCP1A">
		<html:form action="/OCAPGestionCtes.do">
			<h3 class="subTituloContenido">Listado de Comit&eacute;s
				T&eacute;cnicos de Evaluaci&oacute;n</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<fieldset>
				<logic:present name="sinDatos">
					<div id="divSinDatos">
						<table cellpadding="2" cellspacing="0">
							<tr>
								<td><bean:message key="listado.noDatos" /></td>
							</tr>
						</table>
					</div>
				</logic:present>

				<table border="0" class="tablaCTES">
					<logic:notPresent name="sinDatos">
						<%if(jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE)){%>
						<logic:present name="listados" property="elementos">
							<logic:iterate id="elemento" name="listados" property="elementos">
								<tr>
									<td class="col1" headers="dNombre"><bean:write
											name="elemento" property="DNombre" /></td>
									<td class="campoIcono"><a
										href="OCAPComponentesCtes.do?accion=listarEvaluados&cCteIdS=<bean:write name="elemento" property="CCteId"/>&nombre=<bean:write name="elemento" property="DNombre"/>">
											<img src="imagenes/imagenes_ocap/lupa.gif"
											alt="Detalle de CTE" />
									</a></td>
								</tr>
							</logic:iterate>
						</logic:present>
						<%}else{%>
						<tr>
							<th class="col1" id="dNombre">Nombre <a
								href="OCAPGestionCtes.do?accion=irInsertar" class="colocaAnadir">
									<img src="imagenes/imagenes_ocap/anadir.gif"
									alt="A&ntilde;adir CTE" /> <span> A&ntilde;adir </span>
							</a>
							</th>
						</tr>
						<logic:present name="listados" property="elementos">
							<logic:iterate id="elemento" name="listados" property="elementos">
								<tr >
									<td style="border-top: solid 1px #004B98;" class="col1" headers="dNombre"><bean:write
											name="elemento" property="DNombre" /></td>
									<td style="border-top: solid 1px #004B98;" class="campoIcono"><a
										href="OCAPGestionCtes.do?accion=detalle&cCteIdS=<bean:write name="elemento" property="CCteId"/>">
											<img src="imagenes/imagenes_ocap/lupa.gif"
											alt="Detalle de CTE" />
									</a></td>
									<td style="border-top: solid 1px #004B98;" class="campoIcono"><a
										href="OCAPGestionCtes.do?accion=irEditar&cCteIdS=<bean:write name="elemento" property="CCteId"/>">
											<img src="imagenes/imagenes_ocap/activar2.gif"
											title="Activar Convocatoria" alt="Activar Convocatoria">
									</a></td>
									<td style="border-top: solid 1px #004B98;" class="campoIcono"><a
										href="OCAPComponentesCtes.do?accion=listarComponentes&cCteIdS=<bean:write name="elemento" property="CCteId"/>&nombre=<bean:write name="elemento" property="DNombre"/>">
											<img src="imagenes/imagenes_ocap/icono_modificar.gif"
											alt="Listar Componentes CTE" />
									</a></td>
								</tr>
								<hr />

							</logic:iterate>
						</logic:present>
						<%}%>
					</logic:notPresent>
				</table>

				<div class="espaciador"></div>

			</fieldset>
			<input type="hidden" name="cCteIdS" value="" />
		</html:form>
	</div>

</div>
<!-- /Fin de ocultarCampo -->
