<%@ page contentType="text/html;charset=utf-8"
	pageEncoding="windows-1252"%>

<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<div class="ocultarCampo">
	<div class="cuadroContenedorConsultas">
		<h2 class="tituloContenido">Cambiar Nivel de Logs:</h2>
		<logic:notEmpty name="_LOG_listaCategorias">
			<div class="titulocajaformulario">Listado de Categor&iacute;as
				de Logs definidas</div>
			<div class="cajaformulario">
				<fieldset>
					<table border="1" rules="rows" cellspacing="10em"
						cellpadding="10em" width="100%" align="center"
						summary="Listado de Categorias">
						<colgroup span="2"></colgroup>
						<colgroup span="7" width="0*"></colgroup>
						<thead>
							<tr>
								<td colspan="9">&nbsp;</td>
							</tr>
							<tr>
								<th width="35%">Categor&iacute;a</th>
								<th width="10%">Nivel de log</th>
								<th colspan="7">Modificar al nivel</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td colspan="9">&nbsp;</td>
							</tr>
							<logic:iterate id="elemento" name="_LOG_listaCategorias">
								<tr style="padding-bottom: 2px">
									<td align="center"><b><bean:write name="elemento"
												property="categoria" /></b></td>
									<td align="center" style="color: red;"><bean:write
											name="elemento" property="nivel" /></td>
									<td><a
										href="CambiaNivelLog.do?_LOG_categoria=<bean:write name="elemento" property="categoria"/>&amp;_LOG_nivel=OFF">Off</a></td>
									<td><a
										href="CambiaNivelLog.do?_LOG_categoria=<bean:write name="elemento" property="categoria"/>&amp;_LOG_nivel=FATAL">Fatal</a></td>
									<td><a
										href="CambiaNivelLog.do?_LOG_categoria=<bean:write name="elemento" property="categoria"/>&amp;_LOG_nivel=ERROR">Error</a></td>
									<td><a
										href="CambiaNivelLog.do?_LOG_categoria=<bean:write name="elemento" property="categoria"/>&amp;_LOG_nivel=WARN">Warning</a></td>
									<td><a
										href="CambiaNivelLog.do?_LOG_categoria=<bean:write name="elemento" property="categoria"/>&amp;_LOG_nivel=INFO">Info</a></td>
									<td><a
										href="CambiaNivelLog.do?_LOG_categoria=<bean:write name="elemento" property="categoria"/>&amp;_LOG_nivel=DEBUG">Debug</a></td>
									<td><a
										href="CambiaNivelLog.do?_LOG_categoria=<bean:write name="elemento" property="categoria"/>&amp;_LOG_nivel=ALL">All</a></td>
								</tr>
								<tr style="color: #0000FF; font-size: 8pt">
									<td colspan="2">&nbsp;&nbsp;&nbsp;<em>Appenders:&nbsp;&nbsp;<bean:write
												name="elemento" property="logs" /></em></td>
									<td colspan="7"></td>
								</tr>
							</logic:iterate>
							<tr>
								<td colspan="9">&nbsp;</td>
							</tr>
						</tbody>
					</table>
				</fieldset>
			</div>
		</logic:notEmpty>
		<div class="espaciador"></div>
		<div class="cuadroTextoNotas">
			<span><b>Nota:</b></span>
			<p class="textoNormal">Estos valores tendr&aacute;n efecto hasta
				que se reinicie el contenedor correspondiente a la
				aplicaci&oacute;n, entonces se tomar&aacute;n los valores definidos
				en log.xml y log4jfw.properties</p>
		</div>
	</div>
</div>