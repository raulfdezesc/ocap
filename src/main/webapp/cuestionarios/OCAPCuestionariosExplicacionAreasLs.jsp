<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="es.jcyl.fqs.ocap.ot.creditos.OCAPCreditosOT"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.areasItinerarios.OCAPAreasItinerariosOT"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"
	charset="windows-1252"></script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoECP">
		<html:form action="/OCAPCuestionarios.do">
			<h2 class="tituloContenido">INFORMACI&Oacute;N GENERAL SOBRE EL
				PROCESO DE EVALUACI&Oacute;N</h2>
			<h3 class="subTituloContenido">
				<bean:write name="OCAPCuestionariosForm" property="DTitulo" />
				<br /> <br />
				<bean:write name="OCAPCuestionariosForm"
					property="DNombreItinerario" />
				<br />
				<br /> <span><bean:write name="OCAPCuestionariosForm"
						property="codigoId" /></span>
			</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<fieldset>
				<div class="cuadroFieldset">

					<p class="textoNivel1">En esta INTRODUCCI&Oacute;N usted
						conocer&aacute; el procedimiento de evaluaci&oacute;n de sus
						competencias profesionales asistenciales para la obtenci&oacute;n
						de reconocimiento de grado solicitado.</p>

					<!--	<div class="indiceTexto">					
								<div class="tituloIndice"> Tabla de Contenidos </div>
								<ul>
									<li class="nivel1">
										<a href="#punto1">
											<span class="numeracion"> 1. </span>
											<span class="texto"> Procedimiento de Autoevaluaci&oacute;n </span>
										</a>
									</li>
									<li class="nivel1">
										<a href="#punto2"> 
											<span class="numeracion"> 2. </span>
											<span class="texto"> &Aacute;reas de la Autoevaluaci&oacute;n </span>
										</a>
									</li>
									<div class="limpiadora"></div>
									<li class="nivel1">
										<ul>
											<li class="nivel2">
												<a href="#punto21">
													<span class="numeracion"> 2.1. </span>
													<span class="texto"> &Aacute;rea Primera (A.P.) </span>
												</a>
											</li>
											<li class="nivel2">
												<a href="#punto22"> 
													<span class="numeracion"> 2.2. </span>
													<span class="texto"> &Aacute;rea Segunda (A.S.) </span>
												</a>
											</li>
											<li class="nivel2">
												<a href="#punto23"> 
													<span class="numeracion"> 2.3. </span>
													<span class="texto"> &Aacute;rea Tercera (A.T.) </span>
												</a>
											</li>
											<li class="nivel2">
												<a href="#punto24"> 
													<span class="numeracion"> 2.4. </span>
													<span class="texto"> &Aacute;rea Cuarta (A.C.) </span>
												</a>
											</li>
										</ul>
									</li>
									<div class="limpiadora"></div>
									<li class="nivel1">
										<a href="#punto3"> 
											<span class="numeracion"> 3. </span>
											<span class="texto"> Resumen del Proceso de Autoevaluaci&oacute;n y Asignaci&oacute;n de los Cr&eacute;ditos m&iacute;nimos en el &aacute;rea de evaluaci&oacute;n "Asistencia"</span>
										</a>
									</li>
								</ul>
								<div class="limpiadora"></div>
							</div>
                     -->
					<p class="textoNivel1">
						La evaluaci&oacute;n de las competencias profesionales
						asistenciales para el acceso a reconocimiento de grado de carrera
						profesional, est&aacute; dise&ntilde;ada como un sistema de
						AUTOEVALUACI&Oacute;N, apoyado en evidencias documentales que
						demuestren la competencia alcanzada dentro del marco de la carrera
						profesional.<br />
						<br /> Usted ha solicitado de<span class="textoSubrayado">forma
							voluntaria</span> el acceso al grado I de carrera profesional; en este
						momento va a proceder a realizar la evaluaci&oacute;n de sus
						competencias asistenciales. Para ello deber&aacute; seguir el
						procedimiento que a continuaci&oacute;n se indica:
					</p>

					<a name="punto1" id="punto1"></a>
					<h4>1. PROCEDIMIENTO DE EVALUACI&Oacute;N</h4>
					<p class="textoNivel2">Deber&aacute; cumplimentar los
						diferentes modelos de formularios/informes aportando la
						documentaci&oacute;n y evidencias que se soliciten.</p>
					<p class="textoNivel2">
						El procedimiento para la evaluaci&oacute;n se estructura en<span
							class="textoSubrayado">cuatro &aacute;reas.</span> Se ha
						dise&ntilde;ado cada &aacute;rea de manera que el procedimiento a
						seguir sea sencillo para que pueda ir realizando la
						autoevaluaci&oacute;n al ritmo y en el orden que usted mismo
						decida. (Recuerde que tiene<span class="textoSubrayado"><bean:write
								name="OCAPCuestionariosForm" property="NDiasEvaluacion" />
							d&iacute;as naturales</span> para concluirlo).
					</p>
					<p class="textoNivel2">No olvide pulsar el bot&oacute;n
						'Finalizar' cuando se le indique para dar por finalizada para dar
						por finalizada cada parte de la evaluaci&oacute;n; a partir de ese
						momento no ser&aacute; posible hacer ya modificaciones.</p>
					<div class="recuadroM">El procedimiento de evaluaci&oacute;n
						del desempe&ntilde;o de la competencia no es un examen de
						conocimientos, sino un sistema que va a permitir conocer
						personalmente el grado de desarrollo profesional conseguido de
						acuerdo con el modelo de Carrera Profesional de la
						Consejer&iacute;a de Sanidad - SACYL de la Junta de Castilla y
						Le&oacute;n.</div>

					<a name="punto2" id="punto2"></a>
					<h4>2. &Aacute;REAS DE EVALUACI&Oacute;n</h4>
					<p class="textoNivel2">
						El procedimiento se basa en las competencias de su<span
							class="textoSubrayado">perfil profesional</span> y de su puesto
						de trabajo en el &aacute;rea asistencial y sobre la<span
							class="textoSubrayado">atenci&oacute;n al paciente</span> al ser
						la competencia profesional esencial de todo sanitario, junto con
						la realaci&oacute;n inter-profesional, el trabajo en equipo, la
						participaci&oacute;n en actividades del servicio, grupos de mejora
						e implicaci&oacute;n en objetivos comunes del centro/servicio. <br />
						<br /> A continuaci&oacute;n lea con detenimiento las &aacute;reas
						de evaluaci&oacute;n para as&iacute; preparar y efectuar
						posteriormente su autoevaluaci&oacute;n.
					</p>

					<a name="punto21" id="punto21"></a>
					<h5>2.1. &Aacute;REA PRIMERA (A.P.)</h5>
					<p class="textoNivel3">
						Consiste en el<span class="textoSubrayado">Autoan&aacute;lisis
							de su puesto de trabajo</span> dando respuestas estructuradas al
						protocolo establecido y aportando certificados y evidencias
						documentales que ayuden a conocer el nivel o grado de competencia.<br />
						<br /> Esta &aacute;rea est&aacute; estructurada en tres pruebas
						de buena pr&aacute;ctica. Cada una tiene asignados unos
						cr&eacute;ditos sumatorios. Puede elegir para su evaluaci&oacute;n
						las pruebas de Buena Pr&aacute;ctica que considere es competente.
						No olvide que al menos debe seleccionar una de ellas, teniendo
						siempre en cuenta el m&iacute;nimo de cr&eacute;ditos exigido para
						obtener el Grado de Carrera Profesional que le corresponda. Son:
					</p>
					<ul class="listadoAreas">
						<li>Memoria de su puesto de trabajo</li>
						<li>Cartera de servicios de su perfil profesional</li>
						<li>Memoria de procesos cl&iacute;nicos m&aacute;s frecuentes</li>
					</ul>
					<a name="punto22" id="punto22"></a>
					<h5>2.2. &Aacute;REA SEGUNDA (A.S.)</h5>
					<p class="textoNivel3">
						Consistente en la<span class="textoSubrayado">Autoevaluaci&oacute;n
							de la atenci&oacute;n al paciente</span>, cumplimentandopara ello unos
						formularios que van a evaluar unos documentos, contenidos en la Hª
						Cl&iacute;nica del paciente, como prueba de buena pr&aacute;ctica,
						documentada en la bibliograf&iacute;a cient&iacute;fica actual y
						validada por las Sociedades Cient&iacute;ficas. Las historias
						cl&iacute;nicas ser&aacute;n elegidas al azar, de entre los
						pacientes por usted atendidos en los &uacute;ltimos doce meses. <br />
						<br /> Puede elegir las pruebas de Buena Pr&aacute;ctica que
						considere es competente. No olvide que al menos debe seleccionar
						una de ellas, teniendo siempre en cuenta el m&iacute;nimo de
						cr&eacute;ditos exigido para obtener el Grado de Carrera
						Profesional que le corresponda. Son:
					</p>
					<ul class="listadoAreas">
						<li>Autoevaluaci&oacute;n informe de alta.</li>
						<li>Autoevaluaci&oacute;n informe quir&uacute;rgico.</li>
						<li>Autoevaluaci&oacute;n informe de consulta externa.</li>
						<li>Autoevaluaci&oacute;n integral de la historia
							cl&iacute;nica.</li>
					</ul>
					<a name="punto23" id="punto23"></a>
					<h5>2.3. &Aacute;REA TERCERA (A.T.)</h5>
					<p class="textoNivel3">
						Consiste en que usted pueda descubrir y aportar a su carrera
						profesional el grado de desarrollo conseguido en la<span
							class="textoSubrayado">ejecuci&oacute;n de procesos
							asistenciales</span>. Para ello deber&aacute; presentar unas evidencias
						que las Sociedades Cient&iacute;ficas han considerado esenciales,
						junto con la cumplimentaci&oacute;n de<span
							class="textoCursiva textoNormal">tres cuestionarios</span>. Cada
						uno de ellos evaluado junto con la evidencia documentada asigna
						unos cr&eacute;ditos que son sumatorios para la obtenci&oacute;n
						de los cr&eacute;ditos necesarios para el reconocimiento de grado
						en Carrera Profesional.<br />
						<br /> Puede elegir las pruebas de Buena Pr&aacute;ctica que
						considere es competente. No olvide que al menos debe seleccionar
						una de ellas, teniendo siempre en cuenta el m&iacute;nimo de
						cr&eacute;ditos exigido para obtener el Grado de Carrera
						Profesional que le corresponda. Son:
					</p>
					<ul class="listadoAreas">
						<li>An&aacute;lisis de gu&iacute;as de pr&aacute;ctica
							cl&iacute;nica o protocolos de asistencia (GPC/P), que utiliza en
							su servicio m&eacute;dico hospitalario.</li>
						<li>Autoevaluaci&oacute;n seg&uacute;n AEETS
							(Asociaci&oacute;n Espa&ntilde;ola de Evaluaci&oacute;n de
							Tecnolog&iacute;as Sanitarias) de las gu&iacute;as de
							pr&aacute;ctica cl&iacute;nica/protocolos asistenciales (GPC/P),
							utilizados por usted en los &uacute;ltimos doce meses.</li>
						<li>Evaluaci&oacute;n de proceso de interconsulta.</li>
					</ul>
					<a name="punto24" id="punto24"></a>
					<h5>2.4. &Aacute;REA CUARTA (A.C.)</h5>
					<p class="textoNivel3">
						Consiste en la autoevaluaci&oacute;n de la competencia en el
						&aacute;mbito de la relaci&oacute;n<span class="textoSubrayado">m&eacute;dico-paciente</span>,
						de la participaci&oacute;n en el servicio en objetivos comunes, de
						la relaci&oacute;n interprofesional y trabajo en equipo. <br /> <br />
						Puede elegir las pruebas de Buena Pr&aacute;ctica que considere es
						competente. No olvide que al menos debe seleccionar una de ellas,
						teniendo siempre en cuenta el m&iacute;nimo de cr&eacute;ditos
						exigido para obtener el Grado de Carrera Profesional que le
						corresponda. Son:
					</p>
					<ul class="listadoAreas">
						<li>Evaluaci&oacute;n del informe de consentimiento informado
							(C.I.), que le permitir&aacute; evaluar 5 documentos de
							consentimiento informado (C.I.) realizados por usted en los
							&uacute;ltimos 12 meses.</li>
						<li>Cuestionario de opini&oacute;n de usuarios, donde se
							evaluar&aacute;n aspectos del proceso de informaci&oacute;n,
							confidencialidad y comunicaci&oacute;, relacionados con la
							autonom&iacute;a del paciente.</li>
						<li>Evaluaci&oacute;n de compa&ntilde;eros/superior,
							(entendiendo por superior la persona con nombramiento de
							jefatura), sobre trabajo en equipo, relaci&oacute;n
							interprofesional y objetivos comunes. En los casos en que no haya
							una jefatura tendr&aacute; que certificarlo el Director
							M&eacute;dico y persona en quien delegue perteneciente a la
							Direcci&oacute;n M&eacute;dica). Se pretende valorar aspectos de
							la relaci&oacute;n interprofesional, trabajo en equipo,
							participaci&oacute;n en actividades del servicio, grupos de
							mejora, implicaci&oacute;n en objetivos comunes del
							centro/servicio</li>
						<li>Formulario de autoevaluaci&oacute;n de Buena
							Pr&aacute;ctica, que le ayudar&aacute; a evaluar su eficacia en
							aquellos comportamientos que se consideran fundamentales en las
							buenas pr&aacute;cticas.</li>
						<li>Indicadores de actividad asistencial realizada en los
							&uacute;ltimos doce meses.</li>
					</ul>


					<a name="punto3" id="punto3"></a>
					<h4>3. RESUMEN DEL PROCESO DE EVALUACI&Oacute;N Y
						ASIGNACI&Oacute;N DE LOS CR&Eacute;DITOS M&Iacute;NIMOS EN EL
						&Aacute;REA DE EVALUACI&Oacute;N ASISTENCIAL - ESPECIALIDADES
						QUIR&Uacute;RGICAS</h4>

					<bean:define id="tamanoListaCreditosNecesarios"
						name="OCAPCuestionariosForm" property="listaCreditos" />
					<logic:notEqual name="tamanoListaCreditosNecesarios" value="0">
						<p class="textoNivel2">Le recordamos que los cr&eacute;ditos
							m&iacute;nimos necesarios a conseguir en el &aacute;rea
							asistencial son:</p>
						<table class="tablaECP tablaECP2">
							<logic:iterate id="listaCreditosNecesarios"
								name="OCAPCuestionariosForm" property="listaCreditos"
								type="OCAPCreditosOT">
								<logic:equal name="OCAPCuestionariosForm" property="CGradoId"
									value="<%=Long.toString(listaCreditosNecesarios.getCGradoId())%>">
									<tr class="titulosTablaECP">
										<td><span><bean:write
													name="listaCreditosNecesarios" property="DGrado" /></span></td>
									</tr>
									<tr>
										<td class="textoCentrado"><bean:write
												name="listaCreditosNecesarios" property="NCreditos" /></td>
									</tr>
								</logic:equal>
							</logic:iterate>
						</table>
					</logic:notEqual>

					<bean:size id="numAreas" name="OCAPCuestionariosForm"
						property="listadoAreas" />
					<logic:notEqual name="numAreas" value="0">
						<p class="textoNivel2">El n&uacute;mero m&aacute;ximo de
							cr&eacute;ditos que podr&aacute; asignarse por cada &aacute;rea
							de evaluaci&oacute;n en relaci&oacute;n a las pruebas de buenas
							pr&aacute;cticas es el siguiente:</p>
						<table class="tablaECP">
							<tr class="titulosTablaECP">
								<td><span>&Aacute;REA</span></td>
								<td><span>EVALUACI&Oacute;N/PRUEBAS DE BUENA
										PR&Aacute;CTICA</span></td>
								<td><span>CR&Eacute;DITOS</span></td>
							</tr>
							<logic:iterate id="listaAreas" name="OCAPCuestionariosForm"
								property="listadoAreas" type="OCAPAreasItinerariosOT">
								<tr class="datosTablaECP">
									<td class="textoCentrado"><span><bean:write
												name="listaAreas" property="DNombreLargo" /></span></td>
									<td class="parteCentro"><logic:iterate id="lCuestionarios"
											name="listaAreas" property="listaCuestionarios"
											type="OCAPCuestionariosOT">
											<div>
												<bean:write name="listaAreas" property="DNombre" />
												<bean:write name="lCuestionarios" property="DNombre" />
												-
												<bean:write name="lCuestionarios" property="DNombreLargo"
													filter="false" />
											</div>
										</logic:iterate></td>
									<td class="textoCentrado"><logic:iterate
											id="lCuestionarios" name="listaAreas"
											property="listaCuestionarios" type="OCAPCuestionariosOT">
											<div>
												<bean:write name="lCuestionarios" property="NCreditos" />
											</div>
										</logic:iterate></td>
								</tr>
							</logic:iterate>
						</table>
					</logic:notEqual>

					<p class="textoNivel2">
						Recuerde que estos cr&eacute;ditos son acumulativos para el
						reconocimiento de Grado I de Carrera Profesional. el n&uacute;mero
						de cr&eacute;ditos asignado a cada formulario est&aacute; pensado
						para que usted pueda alcanzar los necesarios de forma suficiente.
						Puede elegir los formularios en los que se considere que es
						m&aacute;s competente, teniendo en cuenta que la competencia es la
						suma de todo ello, por lo que es <b>imprescindible evaluarse
							de alguna de las pruebas de cada &aacute;rea</b>, y siempre teniendo
						en cuenta el n&uacute;mero de cr&eacute;ditos a conseguir. <br />
						<br /> Finalmente y muy importante, no olvide que esta
						autoevaluaci&oacute;n ser&aacute; seguida por un Evaluador Externo
						que adem&aacute;s analizar&aacute; las evidencias y
						certificaciones aportadas, y si es necesario realizar&aacute;
						alguna revisi&oacute;n de cualquiera de las &aacute;reas
						evaluadas. <br /> <br /> Le recordamos que a lo largo de todo el
						proceso se le ir&aacute;, indicando que conserve diferentes
						documentos que van a completar su autoevaluaci&oacute;n.
						Deber&aacute; guardarlos y al final del proceso se le
						indicar&aacute; el procedimiento de env&iacute;o. <br /> <br /> En
						cualquier momento puede volver a la <b><U>INTRODUCCI&Oacute;N
						</U></b> si lo considera necesario. <br /> <br /> Asimismo, le indicamos
						que puede enviar sus dudas a la siguiente direcci&oacute;n de
						correo electr&oacute;nico: <b>carreraprofesional.grs@saludcastillayleon.es</b>.
					</p>
					<p class="textoNivel2">
						Por &uacute;ltimo, una vez finalizada toda su evaluaci&oacute;n
						pulse <U><b>ENCUESTA DE SATISFACCI&Oacute;N</b></U> y
						tendr&aacute; la posibilidad de participar en el proceso de mejora
						de la Aplicaci&oacute;n Inform&aacute;tica y del propio modelo de
						evaluaci&oacute;n. <br /> Si ha comprendido bien el proceso haga
						clic en &Aacute;reas de Evaluaci&oacute;, acceder&aacute; al
						Men&uacute; General de &Aacute;reas.
					</p>

				</div>
			</fieldset>

			<div class="espaciador"></div>
			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="OCAPCuestionarios.do?accion=irProteccionDatos"> <img
							src="imagenes/imagenes_ocap/action_forward.gif"
							class="colocaImgPrint"
							alt="Ver lista de formularios de este itinerario agrupados por &aacute;reas" />
							<span> &Aacute;reas de Evaluaci&oacute;n </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>
			<div class="espaciador"></div>
			<div class="limpiadora"></div>
		</html:form>
	</div>
	<!-- /fin de ContenidoTextoECP -->
</div>
<!-- /Fin de Contenido -->
<div class="limpiadora"></div>
