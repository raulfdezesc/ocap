<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<%@ page import="es.jcyl.fqs.ocap.util.Constantes"%>
<%@ page import="es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT"%>
<%@ page
	import="es.jcyl.fqs.ocap.ot.areasItinerarios.OCAPAreasItinerariosOT"%>

<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/javascript/comun.js'/>"></script>

<div class="contenido contenidoFaseIII">
	<div class="contenidoTextoAlgoritmo">

		<html:form action="/OCAPCuestionarios.do">
			<h2 class="tituloContenido">
				ALGORITMO DE ITINERARIOS PARA LA EVALUACI&Oacute;N DE LA COMPETENCIA<br />
				EN CARRERA PROFESIONAL
			</h2>

			<h3 class="subTituloContenido">Licenciados Sanitarios</h3>
			<div class="lineaBajaM"></div>
			<div class="espaciador"></div>

			<div class="cuadroAlgoLargo">
				<div class="tituloCuadroAlgo">PRINCIPIOS</div>
				<div class="datosCuadroAlgo">
					<div>La Consejer&iacute;a de Sanidad - SACYL de la Junta de
						Castilla y Le&oacute;n mediante el Decreto "........" establece la
						Carrera Profesional por medio de evaluaci&oacute;n curricular y de
						COMPETENCIAS PROFESIONALES.</div>
				</div>
			</div>
			<div class="limpiadora"></div>

			<div class="cuadroAlgo">
				<div class="tituloCuadroAlgo">CRITERIOS</div>
				<div class="datosCuadroAlgo">
					<ol>
						<li><span> Las Competencias Profesionales tienen 4
								GRADOS. </span></li>
						<li><span> Cada grado se obtiene a trav&eacute;s de la
								obtenci&oacute;n de cr&eacute;ditos. </span></li>
						<li><span> La solicitud es libre y voluntaria. </span></li>
						<li><span> Para el PERSONAL SANITARIO LICENCIADO que
								se agrupa en Licenciados Especialistas Sanitarios y/o
								Licenciados Sanitarios. </span></li>
					</ol>
				</div>
			</div>
			<div class="cuadroAlgo">
				<div class="tituloCuadroAlgo">PROCEDIMIENTO</div>
				<div class="datosCuadroAlgo">
					<ol>
						<li><span> Por medio de AUTOEVALUACI&Oacute;N
								certificada por evaluaciones externas. </span></li>
						<li><span> Siguiendo criterios de afinidad y adaptado
								a cada ITINERARIO DE COMPETENCIAS. </span></li>
						<li><span> Realizar en plazo de 30 d&iacute;as
								naturales. </span></li>
					</ol>
				</div>
			</div>
			<div class="limpiadora"></div>

			<div class="cuadroAlgoLargo">
				<div class="tituloCuadroAlgo">ITINERARIOS DE EVALUACI&Oacute;N
					COMPETENCIAL</div>
				<div class="datosCuadroAlgo">
					<ol>
						<li><span> Cada Licenciado Especialista
								Sanitario/Licenciado Sanitario desarrolla su competencia
								profesional dentro de su PERFIL DE SU PUESTO DE TRABAJO en su
								especialidad. </span></li>
						<li><span> Las especialidades/PERFIL PROFESIONAL
								tienen un tronco com&uacute;n de competencias con ciertas
								especialidades. </span></li>
						<li><span> Para poder realizar la
								AUTOEVALUACI&Oacute;N ha sido necesario agrupar las
								categor&iacute;as profesionales y sus PERFILES COMPETENCIALES en
								ITINERARIOS DE EVALUACI&Oacute;N COMPETENCIAL. </span></li>
					</ol>
				</div>
			</div>

			<div class="romperAfter"></div>

			<div class="cuadroAlgoLargo">
				<div class="tituloCuadroAlgo">CLASIFICACI&Oacute;N PERFILES
					COMPETENCIALES</div>
				<div class="datosCuadroDoble">
					<ul>
						<li><span class="primero">ACL</span> <span>An&aacute;lisis
								Cl&iacute;nico</span></li>
						<li><span class="primero">ACV</span> <span>Angiolog&iacute;a
								y C. Vascular</span></li>
						<li><span class="primero">ALG</span> <span>Alergolog&iacute;a</span>
						</li>
						<li><span class="primero">ANR</span> <span>Anestesia y
								Reanimaci&oacute;n</span></li>
						<li><span class="primero">APA</span> <span>Anatom&iacute;a
								y Patol&oacute;gica</span></li>
						<li><span class="primero">BIO</span> <span>Bioqu&iacute;mica
								Cl&iacute;nica</span></li>
						<li><span class="primero">CAR</span> <span>Cardiolog&iacute;a</span>
						</li>
						<li><span class="primero">CCA</span> <span>Cirug&iacute;a
								Cardiovascular</span></li>
						<li><span class="primero">CGD</span> <span>Cirug&iacute;a
								General y A. Digestivo</span></li>
						<li><span class="primero">CMF</span> <span>Cirug&iacute;a
								Oral y Maxilofacial</span></li>
						<li><span class="primero">CPE</span> <span>Cirug&iacute;a
								Pedi&aacute;trica</span></li>
						<li><span class="primero">CPL</span> <span>Cirug&iacute;a
								Pl&aacute;stica, Est&eacute;tica y Rep.</span></li>
						<li><span class="primero">CTO</span> <span>Cirug&iacute;a
								Tor&aacute;cica</span></li>
						<li><span class="primero">DER</span> <span>Dermatolog&iacute;a
								M-Q y V.</span></li>
						<li><span class="primero">DIG</span> <span>Digestivo</span></li>
						<li><span class="primero">END</span> <span>Endocrinolog&iacute;a
								y Nutrici&oacute;n</span></li>
						<li><span class="primero">FAR</span> <span>Farmacia
								Hospitalaria</span></li>
						<li><span class="primero">FAC</span> <span>Farmacolog&iacute;a
								Cl&iacute;nica</span></li>
						<li><span class="primero">GRT</span> <span>Geriatr&iacute;a</span>
						</li>
						<li><span class="primero">HEM</span> <span>Hematolog&iacute;a
								y Hemoterapia</span></li>
						<li><span class="primero">INM</span> <span>Inmunolog&iacute;a</span>
						</li>
						<li><span class="primero">MIN</span> <span>Medicina
								Intensiva</span></li>
						<li><span class="primero">MI</span> <span>Medicina
								Interna</span></li>
						<li><span class="primero">MNU</span> <span>Medicina
								Nuclear</span></li>
						<li><span class="primero">MPR</span> <span>Medicina
								Preventiva y S.P.</span></li>
						<li><span class="primero">MIC</span> <span>Microbiolog&iacute;a
								y Parasitolog&iacute;a</span></li>
						<li><span class="primero">NEF</span> <span>Nefrologia</span>
						</li>
					</ul>
				</div>
				<div class="datosCuadroDoble">
					<ul>
						<li><span class="primero">OFT</span> <span>Oftalmolog&iacute;a</span>
						</li>
						<li><span class="primero">ONC</span> <span>Oncolog&iacute;a
								M&eacute;dica</span></li>
						<li><span class="primero">ONR</span> <span>Oncolog&iacute;a
								Radioter&aacute;pica</span></li>
						<li><span class="primero">ORL</span> <span>Otorrinolaringolog&iacute;a</span>
						</li>
						<li><span class="primero">PED</span> <span>Pediatr&iacute;a
								y sus Areas Esp.</span></li>
						<li><span class="primero">PSC</span> <span>Psicolog&iacute;a
								Cl&iacute;nica</span></li>
						<li><span class="primero">PSQ</span> <span>Psiquiatr&iacute;a</span>
						</li>
						<li><span class="primero">RAD</span> <span>Radiodiagn&oacute;stico</span>
						</li>
						<li><span class="primero">RDF</span> <span>Radiofarmacia</span>
						</li>
						<li><span class="primero">RFH</span> <span>Radiofisica
								Hospitalaria</span></li>
						<li><span class="primero">REH</span> <span>Med.
								F&iacute;sica y Rehabilitaci&oacute;n</span></li>
						<li><span class="primero">REU</span> <span>Reumatolog&iacute;a</span>
						</li>
						<li><span class="primero">TRA</span> <span>Ciruggia
								Ortopedica y Trumat.</span></li>
						<li><span class="primero">URO</span> <span>Urolog&iacute;a</span>
						</li>
						<li class="textoAlgo">Los profesionales que prestan sus
							servicios en HAD/UCP (Hospitalizaciones en domicilio/cuidados
							paliativos) se evaluar&aacute; su perfil competencial en su
							&aacute;rea espec&iacute;fica. Itinerario particular.</li>
						<li><span class="primero">HAD</span> <span>Hospitalizaci&oacute;n
								a Domicilio</span></li>
						<li><span class="primero">UCP</span> <span>Unidad de
								Cuidados Paliativos</span></li>
						<li><span class="primero">URG</span> <span>Urgencias
								Hospitalarias</span></li>
						<li><span class="primero">ADC</span> <span>Admisi&oacute;n
								y Control</span></li>
					</ul>
				</div>
				<div class="limpiadora"></div>

				<div class="itinerarios">
					<div class="tituloCuadroAlgo">ITINERARIOS DE
						EVALUACI&Oacute;N</div>
					<div class="columna primero flotaIzq">
						<span class="titulo"> I - 1 </span>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="ACV">
							<span class="sel"> ACV </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="ACV">
							<span> ACV </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="CCA">
							<span class="sel"> CCA </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="CCA">
							<span> CCA </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="CGD">
							<span class="sel"> CGD </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="CGD">
							<span> CGD </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="CMF">
							<span class="sel"> CMF </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="CMF">
							<span> CMF </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="CPL">
							<span class="sel"> CPL </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="CPL">
							<span> CPL </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="CTO">
							<span class="sel"> CTO </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="CTO">
							<span> CTO </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="DER">
							<span class="sel"> DER </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="DER">
							<span> DER </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="OBG">
							<span class="sel"> OBG </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="OBG">
							<span> OBG </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="NRC">
							<span class="sel"> NRC </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="NRC">
							<span> NRC </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="OFT">
							<span class="sel"> OFT </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="OFT">
							<span> OFT </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="ORL">
							<span class="sel"> ORL </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="ORL">
							<span> ORL </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="TRA">
							<span class="sel"> TRA </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="TRA">
							<span> TRA </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="URO">
							<span class="sel"> URO </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="URO">
							<span> URO </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="CPE">
							<span class="sel"> CPE </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="CPE">
							<span> CPE </span>
						</logic:notEqual>
					</div>
					<div class="columna flotaIzq">
						<span class="titulo"> I - 2 </span>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="CAR">
							<span class="sel"> CAR </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="CAR">
							<span> CAR </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="DIG">
							<span class="sel"> DIG </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="DIG">
							<span> DIG </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="INM">
							<span class="sel"> INM </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="INM">
							<span> INM </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="ONC">
							<span class="sel"> ONC </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="ONC">
							<span> ONC </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="MI">
							<span class="sel"> MI </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="MI">
							<span> MI </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="NML">
							<span class="sel"> NML </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="NML">
							<span> NML </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="NRL">
							<span class="sel"> NRL </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="NRL">
							<span> NRL </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="PED">
							<span class="sel"> PED </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="PED">
							<span> PED </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="REU">
							<span class="sel"> REU </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="REU">
							<span> REU </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="GRT">
							<span class="sel"> GRT </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="GRT">
							<span> GRT </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="PSQ">
							<span class="sel"> PSQ </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="PSQ">
							<span> PSQ </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="REH">
							<span class="sel"> REH </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="REH">
							<span> REH </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="PSC">
							<span class="sel"> PSC </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="PSC">
							<span> PSC </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="END">
							<span class="sel"> END </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="END">
							<span> END </span>
						</logic:notEqual>
					</div>
					<div class="columna flotaIzq">
						<span class="titulo"> I - 3 </span>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="ANR">
							<span class="sel"> ANR </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="ANR">
							<span> ANR </span>
						</logic:notEqual>
						<span class="titulo"> I - 4 </span>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="ADC">
							<span class="sel"> ADC </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="ADC">
							<span> ADC </span>
						</logic:notEqual>
						<span class="titulo"> I - 5 </span>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="URG">
							<span class="sel"> URG </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="URG">
							<span> URG </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="UME">
							<span class="sel"> UME </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="UME">
							<span> UME </span>
						</logic:notEqual>
						<span class="titulo"> I - 6 </span>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="NEF">
							<span class="sel"> NEF </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="NEF">
							<span> NEF </span>
						</logic:notEqual>
						<span class="titulo"> I - 7 </span>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="ACL">
							<span class="sel"> ACL </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="ACL">
							<span> ACL </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="MIC">
							<span class="sel"> MIC </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="MIC">
							<span> MIC </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="BIO">
							<span class="sel"> BIO </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="BIO">
							<span> BIO </span>
						</logic:notEqual>
					</div>
					<div class="columna flotaIzq">
						<span class="titulo"> I - 8 </span>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="ONR">
							<span class="sel"> ONR </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="ONR">
							<span> ONR </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="RDF">
							<span class="sel"> RDF </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="RDF">
							<span> RDF </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="RFH">
							<span class="sel"> RFH </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="RFH">
							<span> RFH </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="MNU">
							<span class="sel"> MNU </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="MNU">
							<span> MNU </span>
						</logic:notEqual>
						<span class="titulo"> I - 9 </span>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="MIV">
							<span class="sel"> MIV </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="MIV">
							<span> MIV </span>
						</logic:notEqual>
						<span class="titulo"> I - 10 </span>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="MFyC">
							<span class="sel"> MFyC </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="MFyC">
							<span> MFyC </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="PAP">
							<span class="sel"> PAP </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="PAP">
							<span> PAP </span>
						</logic:notEqual>
						<span class="titulo"> I - 11 </span>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="HEM">
							<span class="sel"> HEM </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="HEM">
							<span> HEM </span>
						</logic:notEqual>
					</div>
					<div class="columna flotaIzq">
						<span class="titulo"> I - 12 </span>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="ALG">
							<span class="sel"> ALG </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="ALG">
							<span> ALG </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="REU">
							<span class="sel"> REU </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="REU">
							<span> REU </span>
						</logic:notEqual>
						<span class="titulo"> I - 13 </span>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="UCP">
							<span class="sel"> UCP </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="UCP">
							<span> UCP </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="HAD">
							<span class="sel"> HAD </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="HAD">
							<span> HAD </span>
						</logic:notEqual>
						<span class="titulo"> I - 14 </span>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="RAD">
							<span class="sel"> RAD </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="RAD">
							<span> RAD </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="NFL">
							<span class="sel"> NFL </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="NFL">
							<span> NFL </span>
						</logic:notEqual>
						<span class="titulo"> I - 15 </span>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="APA">
							<span class="sel"> APA </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="APA">
							<span> APA </span>
						</logic:notEqual>
					</div>
					<div class="columna flotaIzq">
						<span class="titulo"> I - 16 </span>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="FAC">
							<span class="sel"> FAC </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="FAC">
							<span> FAC </span>
						</logic:notEqual>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="FAR">
							<span class="sel"> FAR </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="FAR">
							<span> FAR </span>
						</logic:notEqual>
						<span class="titulo"> I - 17 </span>
						<logic:equal name="OCAPCuestionariosForm" property="DServicio"
							value="MPR">
							<span class="sel"> MPR </span>
						</logic:equal>
						<logic:notEqual name="OCAPCuestionariosForm" property="DServicio"
							value="MPR">
							<span> MPR </span>
						</logic:notEqual>
					</div>
				</div>

				<div class="espaciador"></div>
				<div class="limpiadora"></div>

			</div>
			<!-- cuadroAlgoLargo-->

			<div class="espaciador"></div>
			<div class="limpiadora"></div>

			<div class="botonesPagina">
				<div class="botonAccion">
					<span class="izqBoton"></span> <span class="cenBoton"> <a
						href="OCAPCuestionarios.do?accion=irExplicacionAreas"> <img
							src="imagenes/imagenes_ocap/action_forward.gif"
							class="colocaImgPrint"
							alt="Ver lista de formularios de este itinerario agrupados por áreas" />
							<span> Continuar </span>
					</a>
					</span> <span class="derBoton"></span>
				</div>
			</div>

		</html:form>
	</div>
	<!-- /fin de ContenidoTextoAlgoritmo -->
</div>
<!-- /Fin de Contenido -->
