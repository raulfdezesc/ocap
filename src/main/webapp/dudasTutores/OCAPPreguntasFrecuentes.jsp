<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="es.jcyl.fqs.ocap.ot.dudasTutores.OCAPDudasTutoresOT"%>
<%@ page import="es.jcyl.framework.JCYLUsuario"%>
<%@ page import="es.jcyl.framework.JCYLConfiguracion"%>

<% 
  JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
%>


<div class="contenido contenidoFaseIII">

	<html:form action="/OCAPDudasTutores.do">
		<h2 class="tituloContenido">PREGUNTAS FRECUENTES</h2>
		<h3 class="subTituloContenido">Listado de Preguntas Frecuentes</h3>
		<fieldset class="listadoPregFrec">
			<ul>
				<li>
					<div class="pregunta">
						<p>En la información general sobre el proceso de evaluación de
							méritos asistenciales, no están reflejadas las fechas exactas del
							proceso, simplemente queda reflejado como 30 días naturales.</p>
					</div>
					<div class="respuesta">
						<p>Se dispone de 30 naturales para realizar la autoevaluación,
							contados a partir del día de comienzo de la misma</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>¿Cuál es el significado de las siglas PGP?</p>
					</div>
					<div class="respuesta">
						<p>Son las correspondientes a Punto de Gestión Periférica. Es
							la unidad de apoyo para tramitación y admisión. Se localiza en
							los departamentos de personal de cada Gerencia de Atención
							Primaria, Atención Especializada y Emergencias Sanitarias, así
							como en la Gerencia Regional de Salud de Castilla y León.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>¿Qué significa que un formulario requiere evidencia de
							conformidad del superior? ¿Cómo certifica el formulario el
							superior?</p>
					</div>
					<div class="respuesta">
						<p>En aquellas pruebas de buena práctica que requieran
							evidencia de conformidad del superior se permitirá imprimirlas.
							Dicha impresión deberá ser entregada a su Superior Jerárquico que
							será quien certifique dicha evidencia. El superior certifica el
							formulario cumplimentando la evidencia de conformidad
							correspondiente. Posteriormente, le entregará la evidencia de
							conformidad metida en el sobre que usted le habrá facilitado.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>¿Se puede contar con profesionales de otras categorías como
							compañeros a la hora de elegir a las personas que se necesitan en
							el área segunda respecto a la valoración de los compañeros de
							trabajo, o solo pueden ser de mi misma categoría?</p>
					</div>
					<div class="respuesta">
						<p>Puede elegir los compañeros que usted considere.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>¿Puedo entrar y salir las veces que quiera sin que se me
							grabe?</p>
					</div>
					<div class="respuesta">
						<p>Sí, puede entrar y salir siempre que quiera, lo que es
							definitivo es utilizar las teclas Finalizar y Guardar.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>¿Qué evidencias hay que enviar, de qué partes, de todos los
							puntos de las áreas o sólo de algunos? ¿Qué documentación debe
							imprimirse para enviar como evidencia documental?</p>
					</div>
					<div class="respuesta">
						<p>En las distintas áreas de la evaluación, vienen
							especificadas las evidencias que debe enviar y los documentos que
							debe incluir en cada uno de los sobres.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>Mis compañeros y superior ya me han entregado algunos
							cuestionarios cumplimentados y, aunque todavía no los he
							entregado, no me aparece la ponderación de créditos
							correspondiente en la aplicación.</p>
					</div>
					<div class="respuesta">
						<p>La evaluación que depende de terceros no se pondera de
							forma automática, sino que la ponderación se realiza cuando los
							cuestionarios se incorporan a su autoevaluación mediante el
							escaneo desde el órgano evaluador.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>He realizado los cuestionarios, y me gustaría saber cuáles
							son las evidencias documentales que debo aportar. ¿Las tengo que
							imprimir y ponerlas en un sobre cada una?</p>
					</div>
					<div class="respuesta">
						<p>En cada una de las pruebas de Buena Práctica se especifica
							si deben incluirse evidencias documentales y de qué tipo, así
							como el modo de guardarlas en el sobre correspondiente.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>En la evaluación del superior jerárquico sobre trabajo en
							equipo, ¿tengo que llevar un cuestionario a mi superior?</p>
					</div>
					<div class="respuesta">
						<p>Se le indica que debe entregar el cuestionario a su
							superior para que lo cumplimente, introducirlo en el sobre que
							usted también le ha entregado y devolvérselo.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>¿Cada sobre es para guardar una evidencia?</p>
					</div>
					<div class="respuesta">
						<p>Cada evidencia tiene un código que es el que debe coincidir
							con el sobre.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>¿Dónde tengo que recoger los sobres de las evidencias
							documentales? ¿Y los que debo dar a pacientes y compañeros para
							la evaluación?</p>
					</div>
					<div class="respuesta">
						<p>
							Los sobres de evidencias documentales puede recogerlos en los
							Puntos de Gestión Periférica (P.G.P.) de su centro.<br /> Los
							sobres para las evidencias documentales que dependen de terceros
							(pacientes/compañeros) serán entregados por usted a la persona
							seleccionada, junto con la prueba de buena práctica en la que se
							demande su colaboración. Su compañero/paciente debe entregarle el
							sobre cerrado, y usted se encargará de llevarlo junto con las
							otras evidencias documentales al P.G.P.
						</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>¿Cuándo hay que utilizar el dossier?</p>
					</div>
					<div class="respuesta">
						<p>Puede utilizar el dossier cuando le venga bien, sólo se
							trata de chequear todas las evidencias documentales que va a
							presentar para su autoevaluación y que llevará al punto de
							gestión de carrera de su centro.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>Dentro de las preguntas de las distintas autoevaluaciones
							sobre mis competencias, hay muchas de ellas que, estando dentro
							de las funciones de mi categoría, no abarco, ya que en mi
							Servicio no se encuentran dichas instalaciones. Dejo dichas
							cuestiones sin contestar o respondo lo que haría si estuviera en
							esa situación?</p>
					</div>
					<div class="respuesta">
						<p>Las pruebas de buenas prácticas están diseñadas para
							analizar la competencia profesional, independientemente de la
							tarea o función desempeñada.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>¿Qué significa "con evidencia de conformidad"?</p>
					</div>
					<div class="respuesta">
						<p>La evidencia de conformidad es el documento que
							cumplimentará y firmará su superior; es un criterios de
							aceptación sobre la información aportada por usted.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>¿Debo identificar de alguna manera los cuestionarios de
							opinión de pacientes?</p>
					</div>
					<div class="respuesta">
						<p>Cada uno de los cuestionarios tiene un código asignado que
							coincide con la relación que usted incluye en la plantilla en su
							autoevaluación.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>¿Pueden los usuarios/pacientes evaluadores quedarse con la
							carta que se les entrega para explicar el proceso?</p>
					</div>
					<div class="respuesta">
						<p>Sí, los usuarios/pacientes evaluadores se pueden quedar con
							la carta que se les entrega para explicar el proceso, es una
							carta informativa, no se necesita para nada más.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>Se pueden imprimir las evidencias de conformidad en
							cualquier momento del Proceso de Evaluación o debe hacerse al
							final?</p>
					</div>
					<div class="respuesta">
						<p>Las evidencias de conformidad pueden imprimirse cuando se
							haya finalizado de cumplimentar el formulario correspondiente.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>¿Dónde se obtienen los documentos de evidencia de
							conformidad del superior?</p>
					</div>
					<div class="respuesta">
						<p>Los documentos de evidencia de conformidad del superior
							aparecen en el programa una vez cumplimentados las memorias o
							formularios correspondientes y pulsado el botón Finalizar.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>Los sobres que hay que entregar en el PGP tienen que
							entregarse cerrados o abiertos para escanear las evidencias y
							posteriormente los cierran allí?</p>
					</div>
					<div class="respuesta">
						<p>Los sobres se entregan cerrados. En la parte superior
							derecha hay un código de barras donde se incluye su código de
							profesional y el número de evidencia. El órgano evaluador es el
							encargado de escanear la evidencia.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>¿En los sobres de los compañeros debe incluirse también la
							carta de presentación que les he entregado o solo el formulario?
						</p>
					</div>
					<div class="respuesta">
						<p>Debe incluirse solo el formulario cumplimentado.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>¿Debo contestar obligatoriamente a todos los cuestionarios?
						</p>
					</div>
					<div class="respuesta">
						<p>Se le indica, a modo de información, que puede elegir las
							pruebas de buena práctica que considere, siempre teniendo en
							cuenta los créditos mínimos que debe conseguir.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>Según la disposición de la documentación fotocopiada y
							aportada en los sobres, no se llegarían a utilizar todos los
							sobres que me han entregado. En los otros sobres ¿hay que meter
							documentación?</p>
					</div>
					<div class="respuesta">
						<p>En su proceso de autoevaluación deberá emplear los sobres
							de aquellas pruebas que requieran evidencias y que haya decidido
							llevar a cabo en su proceso de evaluación. Los sobres de aquellas
							pruebas de buena práctica que incluyan documentación y que haya
							decidido no realizar no deberá entregarlos.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>Al entrar en la aplicación no veo correctamente la
							información.</p>
					</div>
					<div class="respuesta">
						<p>Esta aplicación está diseñada para que sea visualizada con
							Firefox 3.5 e Internet Explorer 6, 7 y 8. En cualquier otro
							navegador no se garantiza la correcta visualización de los
							cuestionarios.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>En un cuestionario he pulsado el botón "Finalizar" y ahora
							quiero volver a reabrir este cuestionario.</p>
					</div>
					<div class="respuesta">
						<p>Usted podrá abrir el cuestionario tantas veces como desee
							siempre y cuando utilice el botón "Pausar" para ese cuestionario.
							En el momento en el que utilice el botón Finalizar ya no podrá
							modificar el cuestionario.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>¿Que significa el tic verde que aparece al lado de alguno
							de los cuestionarios que realizo?</p>
					</div>
					<div class="respuesta">
						<p>El tic verde significa que usted ha finalizado el
							cuestionario y por tanto ya no podrá modificarlo.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>[*] Al finalizar un cuestionario he visto en la pantalla de
							Áreas de Evaluación que aparece su ponderación con una "?" al
							lado. ¿Qué significa?</p>
					</div>
					<div class="respuesta">
						<p>Cuando aparece una "?" al lado de la ponderación significa
							dicho cuestionario tiene asociado evidencias que debe usted
							aportar y que no se sumarán los créditos obtenidos hasta que no
							se añada la evidencia a su expediente.</p>
					</div>
				</li>
			</ul>
		</fieldset>

		<div class="espaciador"></div>

		<div class="botonesPagina">

			<div class="botonAccion">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="javascript: history.back()"> <img
						src="imagenes/imagenes_ocap/aspa_roja.gif" alt="Volver" /> <span>
							Volver </span>
				</a>
				</span> <span class="derBoton"></span>
			</div>



		</div>

	</html:form>
</div>
