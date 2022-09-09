<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>

<div class="ocultarCampo">

	<div class="cuadroContenedor contenidoFaseIII">
		<html:form action="/OCAPComponentesCtes.do">
			<logic:notEqual name="eliminaConvocatoria" value="<%=Constantes.SI%>">
				<div class="titulocajaformulario">BAJA DE EVALUADOR</div>
			</logic:notEqual>
			<logic:equal name="eliminaConvocatoria" value="<%=Constantes.SI%>">
				<div class="titulocajaformulario">BAJA DE CONVOCATORIA/MANUAL
					DEL EVALUADOR</div>
			</logic:equal>

			<div class="cajaformulario">
				<div class="cuadroFieldset">
					<logic:equal name="evaluados" value="<%=Constantes.SI%>">
						<label for="idVTitulo" class="textoJustificado"> <logic:notEqual
								name="eliminaConvocatoria" value="<%=Constantes.SI%>">
                     Este evaluador tiene evaluados asignados. Para borrarlo deber&aacute; cambiarlos previamente a otro evaluador.
                  </logic:notEqual> <logic:equal name="eliminaConvocatoria"
								value="<%=Constantes.SI%>">
                     Este evaluador tiene evaluados asignados en esa convocatoria y manual. Para borrarlo deber&aacute; cambiarlos previamente a otro evaluador.
                  </logic:equal>
						</label>
					</logic:equal>
					<logic:notEqual name="evaluados" value="<%=Constantes.SI%>">
						<label for="idVTitulo" class="textoJustificado"> <logic:notEqual
								name="eliminaConvocatoria" value="<%=Constantes.SI%>">
                     &iquest;Est&aacute; seguro de que desea dar de baja el Evaluador?
                  </logic:notEqual> <logic:equal name="eliminaConvocatoria"
								value="<%=Constantes.SI%>">
                     &iquest;Est&aacute; seguro de que desea dar de baja esta convocatoria/manual?
                  </logic:equal>
						</label>
					</logic:notEqual>
				</div>

				<div class="botonesPagina">
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <logic:notEqual
								name="eliminaConvocatoria" value="<%=Constantes.SI%>">
								<a
									href="OCAPEvaluadores.do?accion=buscar&opcion=<bean:write name="opcion"/>&<%=Constantes.RECUPERAR_BUSQUEDA_ANTERIOR%>=<%=Constantes.SI%>">
							</logic:notEqual> <logic:equal name="eliminaConvocatoria"
								value="<%=Constantes.SI%>">
								<a
									href="OCAPEvaluadores.do?accion=irEditar&cCompfqsIdS=<bean:write name="OCAPComponentesfqsForm" property="CCompfqs_id"/>&opcion=<bean:write name="opcion"/>&tarea=<bean:write name="tarea"/>">
							</logic:equal> <img src="imagenes/imagenes_ocap/aspa_roja.gif"
							alt="Cancelar Eliminar Evaluador" /> <logic:notEqual
								name="evaluados" value="<%=Constantes.SI%>">
								<span> <%=Constantes.NO_TEXTO%>
								</span>
							</logic:notEqual> <logic:equal name="evaluados" value="<%=Constantes.SI%>">
								<span> Volver </span>
							</logic:equal> </a>
						</span> <span class="derBoton"></span>
					</div>
					<logic:notEqual name="evaluados" value="<%=Constantes.SI%>">
						<div class="botonAccion">
							<span class="izqBoton"></span> <span class="cenBoton"> <logic:notEqual
									name="eliminaConvocatoria" value="<%=Constantes.SI%>">
									<a
										href="OCAPEvaluadores.do?accion=borrar&cCompfqsIdS=<bean:write name="OCAPComponentesfqsForm" property="CCompfqs_id"/>&opcion=<bean:write name="opcion"/>">
								</logic:notEqual> <logic:equal name="eliminaConvocatoria"
									value="<%=Constantes.SI%>">
									<a
										href="OCAPEvaluadores.do?accion=eliminarConv&cCompfqsConvoIdS=<bean:write name="OCAPComponentesfqsForm" property="CCompfqs_convo_id"/>&opcion=<bean:write name="opcion"/>">
								</logic:equal> <img src="imagenes/imagenes_ocap/flecha_correcto.gif"
								alt="Aceptar Eliminar Evaluador" /> <span> <%=Constantes.SI_TEXTO%>
							</span> </a>
							</span> <span class="derBoton"></span>
						</div>
					</logic:notEqual>
				</div>
				<html:hidden property="CCompfqs_id" />

				<div class="espaciador"></div>

			</div>
		</html:form>
	</div>

</div>
<!-- /Fin de ocultarCampo -->

