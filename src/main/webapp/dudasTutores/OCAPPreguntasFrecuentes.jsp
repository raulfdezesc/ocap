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
						<p>En la informaci�n general sobre el proceso de evaluaci�n de
							m�ritos asistenciales, no est�n reflejadas las fechas exactas del
							proceso, simplemente queda reflejado como 30 d�as naturales.</p>
					</div>
					<div class="respuesta">
						<p>Se dispone de 30 naturales para realizar la autoevaluaci�n,
							contados a partir del d�a de comienzo de la misma</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>�Cu�l es el significado de las siglas PGP?</p>
					</div>
					<div class="respuesta">
						<p>Son las correspondientes a Punto de Gesti�n Perif�rica. Es
							la unidad de apoyo para tramitaci�n y admisi�n. Se localiza en
							los departamentos de personal de cada Gerencia de Atenci�n
							Primaria, Atenci�n Especializada y Emergencias Sanitarias, as�
							como en la Gerencia Regional de Salud de Castilla y Le�n.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>�Qu� significa que un formulario requiere evidencia de
							conformidad del superior? �C�mo certifica el formulario el
							superior?</p>
					</div>
					<div class="respuesta">
						<p>En aquellas pruebas de buena pr�ctica que requieran
							evidencia de conformidad del superior se permitir� imprimirlas.
							Dicha impresi�n deber� ser entregada a su Superior Jer�rquico que
							ser� quien certifique dicha evidencia. El superior certifica el
							formulario cumplimentando la evidencia de conformidad
							correspondiente. Posteriormente, le entregar� la evidencia de
							conformidad metida en el sobre que usted le habr� facilitado.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>�Se puede contar con profesionales de otras categor�as como
							compa�eros a la hora de elegir a las personas que se necesitan en
							el �rea segunda respecto a la valoraci�n de los compa�eros de
							trabajo, o solo pueden ser de mi misma categor�a?</p>
					</div>
					<div class="respuesta">
						<p>Puede elegir los compa�eros que usted considere.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>�Puedo entrar y salir las veces que quiera sin que se me
							grabe?</p>
					</div>
					<div class="respuesta">
						<p>S�, puede entrar y salir siempre que quiera, lo que es
							definitivo es utilizar las teclas Finalizar y Guardar.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>�Qu� evidencias hay que enviar, de qu� partes, de todos los
							puntos de las �reas o s�lo de algunos? �Qu� documentaci�n debe
							imprimirse para enviar como evidencia documental?</p>
					</div>
					<div class="respuesta">
						<p>En las distintas �reas de la evaluaci�n, vienen
							especificadas las evidencias que debe enviar y los documentos que
							debe incluir en cada uno de los sobres.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>Mis compa�eros y superior ya me han entregado algunos
							cuestionarios cumplimentados y, aunque todav�a no los he
							entregado, no me aparece la ponderaci�n de cr�ditos
							correspondiente en la aplicaci�n.</p>
					</div>
					<div class="respuesta">
						<p>La evaluaci�n que depende de terceros no se pondera de
							forma autom�tica, sino que la ponderaci�n se realiza cuando los
							cuestionarios se incorporan a su autoevaluaci�n mediante el
							escaneo desde el �rgano evaluador.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>He realizado los cuestionarios, y me gustar�a saber cu�les
							son las evidencias documentales que debo aportar. �Las tengo que
							imprimir y ponerlas en un sobre cada una?</p>
					</div>
					<div class="respuesta">
						<p>En cada una de las pruebas de Buena Pr�ctica se especifica
							si deben incluirse evidencias documentales y de qu� tipo, as�
							como el modo de guardarlas en el sobre correspondiente.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>En la evaluaci�n del superior jer�rquico sobre trabajo en
							equipo, �tengo que llevar un cuestionario a mi superior?</p>
					</div>
					<div class="respuesta">
						<p>Se le indica que debe entregar el cuestionario a su
							superior para que lo cumplimente, introducirlo en el sobre que
							usted tambi�n le ha entregado y devolv�rselo.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>�Cada sobre es para guardar una evidencia?</p>
					</div>
					<div class="respuesta">
						<p>Cada evidencia tiene un c�digo que es el que debe coincidir
							con el sobre.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>�D�nde tengo que recoger los sobres de las evidencias
							documentales? �Y los que debo dar a pacientes y compa�eros para
							la evaluaci�n?</p>
					</div>
					<div class="respuesta">
						<p>
							Los sobres de evidencias documentales puede recogerlos en los
							Puntos de Gesti�n Perif�rica (P.G.P.) de su centro.<br /> Los
							sobres para las evidencias documentales que dependen de terceros
							(pacientes/compa�eros) ser�n entregados por usted a la persona
							seleccionada, junto con la prueba de buena pr�ctica en la que se
							demande su colaboraci�n. Su compa�ero/paciente debe entregarle el
							sobre cerrado, y usted se encargar� de llevarlo junto con las
							otras evidencias documentales al P.G.P.
						</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>�Cu�ndo hay que utilizar el dossier?</p>
					</div>
					<div class="respuesta">
						<p>Puede utilizar el dossier cuando le venga bien, s�lo se
							trata de chequear todas las evidencias documentales que va a
							presentar para su autoevaluaci�n y que llevar� al punto de
							gesti�n de carrera de su centro.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>Dentro de las preguntas de las distintas autoevaluaciones
							sobre mis competencias, hay muchas de ellas que, estando dentro
							de las funciones de mi categor�a, no abarco, ya que en mi
							Servicio no se encuentran dichas instalaciones. Dejo dichas
							cuestiones sin contestar o respondo lo que har�a si estuviera en
							esa situaci�n?</p>
					</div>
					<div class="respuesta">
						<p>Las pruebas de buenas pr�cticas est�n dise�adas para
							analizar la competencia profesional, independientemente de la
							tarea o funci�n desempe�ada.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>�Qu� significa "con evidencia de conformidad"?</p>
					</div>
					<div class="respuesta">
						<p>La evidencia de conformidad es el documento que
							cumplimentar� y firmar� su superior; es un criterios de
							aceptaci�n sobre la informaci�n aportada por usted.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>�Debo identificar de alguna manera los cuestionarios de
							opini�n de pacientes?</p>
					</div>
					<div class="respuesta">
						<p>Cada uno de los cuestionarios tiene un c�digo asignado que
							coincide con la relaci�n que usted incluye en la plantilla en su
							autoevaluaci�n.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>�Pueden los usuarios/pacientes evaluadores quedarse con la
							carta que se les entrega para explicar el proceso?</p>
					</div>
					<div class="respuesta">
						<p>S�, los usuarios/pacientes evaluadores se pueden quedar con
							la carta que se les entrega para explicar el proceso, es una
							carta informativa, no se necesita para nada m�s.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>Se pueden imprimir las evidencias de conformidad en
							cualquier momento del Proceso de Evaluaci�n o debe hacerse al
							final?</p>
					</div>
					<div class="respuesta">
						<p>Las evidencias de conformidad pueden imprimirse cuando se
							haya finalizado de cumplimentar el formulario correspondiente.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>�D�nde se obtienen los documentos de evidencia de
							conformidad del superior?</p>
					</div>
					<div class="respuesta">
						<p>Los documentos de evidencia de conformidad del superior
							aparecen en el programa una vez cumplimentados las memorias o
							formularios correspondientes y pulsado el bot�n Finalizar.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>Los sobres que hay que entregar en el PGP tienen que
							entregarse cerrados o abiertos para escanear las evidencias y
							posteriormente los cierran all�?</p>
					</div>
					<div class="respuesta">
						<p>Los sobres se entregan cerrados. En la parte superior
							derecha hay un c�digo de barras donde se incluye su c�digo de
							profesional y el n�mero de evidencia. El �rgano evaluador es el
							encargado de escanear la evidencia.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>�En los sobres de los compa�eros debe incluirse tambi�n la
							carta de presentaci�n que les he entregado o solo el formulario?
						</p>
					</div>
					<div class="respuesta">
						<p>Debe incluirse solo el formulario cumplimentado.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>�Debo contestar obligatoriamente a todos los cuestionarios?
						</p>
					</div>
					<div class="respuesta">
						<p>Se le indica, a modo de informaci�n, que puede elegir las
							pruebas de buena pr�ctica que considere, siempre teniendo en
							cuenta los cr�ditos m�nimos que debe conseguir.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>Seg�n la disposici�n de la documentaci�n fotocopiada y
							aportada en los sobres, no se llegar�an a utilizar todos los
							sobres que me han entregado. En los otros sobres �hay que meter
							documentaci�n?</p>
					</div>
					<div class="respuesta">
						<p>En su proceso de autoevaluaci�n deber� emplear los sobres
							de aquellas pruebas que requieran evidencias y que haya decidido
							llevar a cabo en su proceso de evaluaci�n. Los sobres de aquellas
							pruebas de buena pr�ctica que incluyan documentaci�n y que haya
							decidido no realizar no deber� entregarlos.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>Al entrar en la aplicaci�n no veo correctamente la
							informaci�n.</p>
					</div>
					<div class="respuesta">
						<p>Esta aplicaci�n est� dise�ada para que sea visualizada con
							Firefox 3.5 e Internet Explorer 6, 7 y 8. En cualquier otro
							navegador no se garantiza la correcta visualizaci�n de los
							cuestionarios.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>En un cuestionario he pulsado el bot�n "Finalizar" y ahora
							quiero volver a reabrir este cuestionario.</p>
					</div>
					<div class="respuesta">
						<p>Usted podr� abrir el cuestionario tantas veces como desee
							siempre y cuando utilice el bot�n "Pausar" para ese cuestionario.
							En el momento en el que utilice el bot�n Finalizar ya no podr�
							modificar el cuestionario.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>�Que significa el tic verde que aparece al lado de alguno
							de los cuestionarios que realizo?</p>
					</div>
					<div class="respuesta">
						<p>El tic verde significa que usted ha finalizado el
							cuestionario y por tanto ya no podr� modificarlo.</p>
					</div>
				</li>

				<li>
					<div class="pregunta">
						<p>[*] Al finalizar un cuestionario he visto en la pantalla de
							�reas de Evaluaci�n que aparece su ponderaci�n con una "?" al
							lado. �Qu� significa?</p>
					</div>
					<div class="respuesta">
						<p>Cuando aparece una "?" al lado de la ponderaci�n significa
							dicho cuestionario tiene asociado evidencias que debe usted
							aportar y que no se sumar�n los cr�ditos obtenidos hasta que no
							se a�ada la evidencia a su expediente.</p>
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
