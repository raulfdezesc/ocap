<div class="noImprimir">
	<fieldset>
		<legend class="tituloLegend"> Buscador </legend>

		

		<div class="botonesPagina colocaBotonBusc">
			<div class="botonAccion">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="javascript:buscar();" tabindex="61"> <img
						src="imagenes/imagenes_ocap/dobleFlecha.gif"
						class="colocaImgPrint" alt="Buscar" /> <span> Buscar </span>
				</a>
				</span> <span class="derBoton"></span>
			</div>
			<div class="botonAccion">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="javascript:limpiar();" tabindex="61"> <img
						src="imagenes/imagenes_ocap/aspa_roja.gif"
						class="colocaImgPrint" alt="Limpiar" /> <span> Limpiar
					</span>
				</a>
				</span> <span class="derBoton"></span>
			</div>
			<div class="botonAccion">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="javascript:seleccionaTodosChecks();" tabindex="61"> <img
						src="imagenes/imagenes_ocap/flecha_correcto.gif"
						class="colocaImgPrint" alt="Seleccionar todas las preguntas" />
						<span> Seleccionar Todas </span>
				</a>
				</span> <span class="derBoton"></span>
			</div>

			<div class="botonAccion">
				<span class="izqBoton"></span> <span class="cenBoton"> <a
					href="javascript:verEncuesta();" tabindex="61"> <img
						src="imagenes/imagenes_ocap/action_forward.gif"
						class="colocaImgPrint" alt="Ver encuesta vacía" /> <span>
							Ver encuesta </span>
				</a>
				</span> <span class="derBoton"></span>
			</div>
		<div class="cuadroFieldset">

			<div class="formulario">
				<div class="unMedio">
					<label for="idVCategoria">Categor&iacute;a:</label>
					<html:select property="CProfesional_id" size="1" onchange="borraItinerario();">
						<html:option value="">Seleccione...</html:option>
						<html:options collection="<%=Constantes.COMBO_CATEGORIA%>" property="CEstatutId" labelProperty="DNombre" />
					</html:select>
				</div>

				<div class="todo">
					<label for="idVEspecialidad">Itinerario: </label>
					<html:select property="CItinerario_id" styleClass="cuadroTodoG" size="1" onchange="borraCategoria();">
						<html:option value="">Seleccione...</html:option>
						<html:option value="-1">Todos</html:option>
						<html:options collection="<%=Constantes.COMBO_ITINERARIOS%>" property="CItinerarioId" labelProperty="DDescripcion" />
					</html:select>
				</div>
			</div>
			<br>
			<br>
			<br> <span class="textoNegrita">Seleccione las preguntas
				de las que desee visualizar el resultado (Las preguntas 7, 8, 11,
				15, 18 y 20 no son tratadas en este apartado):</span> <br>
			<br>
			<html:checkbox property="respuesta_0" value="<%=Constantes.SI%>" />
			1- &iquest;Se le ha informado adecuadamente sobre la
			autoevaluaci&oacute;n en la aplicaci&oacute;n inform&aacute;tica?
			<br>
			<br>
			<html:checkbox property="respuesta_1" value="<%=Constantes.SI%>" />
			2- &iquest;Las instrucciones facilitadas le han sido &uacute;tiles
			para llevar a cabo el proceso de autoevaluaci&oacute;n? <br>
			<br>
			<html:checkbox property="respuesta_2" value="<%=Constantes.SI%>" />
			3- Si en alg&uacute;n momento usted ha necesitado ayuda,
			&iquest;le ha resultado sencillo acceder a ella a trav&eacute;s de
			la plataforma inform&aacute;tica? <br>
			<br>
			<html:checkbox property="respuesta_3" value="<%=Constantes.SI%>" />
			4- Valore de 1 a 5 su satisfacci&oacute;n con los sistemas de
			informaci&oacute;n y la ayuda de la aplicaci&oacute;n. <br>
			<br>
			<div class="baja">&nbsp;</div>
			<span class="separaIzqTit">5- Valore de 1 a 5 los
				siguientes conceptos relativos a la aplicaci&oacute;n
				inform&aacute;tica:</span> <br>
			<br> <span class="separaIzq"><html:checkbox property="respuesta_4" value="<%=Constantes.SI%>" /></span> 5.a -
			Acceso <br>
			<br> <span class="separaIzq"><html:checkbox property="respuesta_5" value="<%=Constantes.SI%>" /></span> 5.b -
			Navegabilidad <br>
			<br> <span class="separaIzq"><html:checkbox property="respuesta_6" value="<%=Constantes.SI%>" /></span> 5.c -
			Interfaz(dise&ntilde;o) <br>
			<br> <span class="separaIzq"><html:checkbox property="respuesta_7" value="<%=Constantes.SI%>" /></span> 5.d -
			Legibilidad <br>
			<br> <span class="separaIzq"><html:checkbox property="respuesta_8" value="<%=Constantes.SI%>" /></span> 5.e -
			Descarga de documentos <br>
			<br>
			<html:checkbox property="respuesta_9" value="<%=Constantes.SI%>" />
			6- &iquest;Cuando usted ha necesitado ayuda individualizada para
			resolver problemas t&eacute;cnicos/te&oacute;ricos de la
			aplicaci&oacute;n, el personal de la FQS se lo ha resuelto con
			rapidez y eficacia.? <br>
			<br>
			<html:checkbox property="respuesta_12" value="<%=Constantes.SI%>" />
			9- &iquest;C&oacute;mo de adecuado le ha parecido el tiempo? <br>
			<br>
			<html:checkbox property="respuesta_13" value="<%=Constantes.SI%>" />
			10- En cuanto a todo el proceso de acceso a grado de Carrera
			Profesional &iquest;le han parecido suficientes los plazos
			marcados? <br>
			<br>
			<html:checkbox property="respuesta_15" value="<%=Constantes.SI%>" />
			12- De los siguientes organismos, se&ntilde;ale cu&aacute;les
			considera usted que intervienen en el proceso de evaluaci&oacute;n
			de la Carrera Profesional: <br>
			<br>
			<html:checkbox property="respuesta_16" value="<%=Constantes.SI%>" />
			13- Indique su grado de satisfacci&oacute;n con la
			coordinaci&oacute;n entre los diferentes organismos que participan
			en el proceso de evaluaci&oacute;n de la Carrera Profesional. <br>
			<br>
			<html:checkbox property="respuesta_17" value="<%=Constantes.SI%>" />
			14- Desde su punto de vista &iquest;la FQS ha realizado
			adecuadamente la labor que esperaba y contando con su confianza? <br>
			<br>
			<html:checkbox property="respuesta_18" value="<%=Constantes.SI%>" />
			15- &iquest;Cree que la autoevaluaci&oacute;n realizada se
			corresponde con su perfil profesional? <br>
			<br>
			<html:checkbox property="respuesta_19" value="<%=Constantes.SI%>" />
			16- &iquest;Considera que la autoevaluaci&oacute;n realizada por
			usted puede aportar aspectos positivos aplicables a su trabajo
			diario? <br>
			<br>
			<div class="baja">&nbsp;</div>
			<span class="separaIzqTit">17- Valore de 1 a 5 el proceso
				de autoevaluaci&oacute;n de acuerdo con:</span> <br>
			<br> <span class="separaIzq"><html:checkbox property="respuesta_20" value="<%=Constantes.SI%>" /></span> 17.a -
			Contenido <br>
			<br> <span class="separaIzq"><html:checkbox property="respuesta_21" value="<%=Constantes.SI%>" /></span> 17.b -
			Grado de dificultad <br>
			<br> <span class="separaIzq"><html:checkbox property="respuesta_22" value="<%=Constantes.SI%>" /></span> 17.c -
			Tiempo invertido <br>
			<br> <span class="separaIzq"><html:checkbox property="respuesta_23" value="<%=Constantes.SI%>" /></span> 17.d -
			Presentaci&oacute;n de la misma <br>
			<br>
			<html:checkbox property="respuesta_24" value="<%=Constantes.SI%>" />
			18- El m&eacute;todo empleado para el acceso a grado de Carrera
			Profesional en Castilla y Le&oacute;n, &iquest;ha respondido a las
			expectativas que ten&iacute;a depositadas en &eacute;l? <br>
			<br>
			<html:checkbox property="respuesta_25" value="<%=Constantes.SI%>" />
			19- Valore de 1 a 5 la confianza que usted deposita en el sistema
			de evaluaci&oacute;n del Grado de Carrera Profesional en Castilla
			y Le&oacute;n. <br>
			<br>
		</div></div>
	</fieldset>
</div>