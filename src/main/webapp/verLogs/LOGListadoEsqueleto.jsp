<%@ page contentType="text/html;charset=utf-8"
	pageEncoding="windows-1252"%>

<%@ taglib uri="bean.tld" prefix="bean"%>
<%@ taglib uri="logic.tld" prefix="logic"%>

<logic:notEmpty name="_LOG_lista">
	<div class="cajaformulario">
		<fieldset class="normales">
			<table border="1" frame="void" rules="groups" cellspacing="10em"
				cellpadding="10em" width="100%" align="center"
				summary="Listado de ficheros de log">
				<thead>
					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<logic:notEqual name="_LOG_entorno" value="inicial">
							<th><b>Nombre</b> (<a
								href="Log.do?_LOG_dir=<bean:write name="_LOG_directorio"/>&amp;_LOG_inicial=no&amp;orden=nombre"><font
									size="-1">&#8743;</font></a>) (<a
								href="Log.do?_LOG_dir=<bean:write name="_LOG_directorio"/>&amp;_LOG_inicial=no&amp;_LOG_orden=nombreD"><font
									size="-1">&#8744;</font></a>)</th>
							<th><b>Fecha de modificaci&oacute;n</b> (<a
								href="Log.do?_LOG_dir=<bean:write name="_LOG_directorio"/>&amp;_LOG_inicial=no&amp;_LOG_orden=fecha"><font
									size="-1">&#8743;</font></a>) (<a
								href="Log.do?_LOG_dir=<bean:write name="_LOG_directorio"/>&amp;_LOG_inicial=no&amp;_LOG_orden=fechaD"><font
									size="-1">&#8744;</font></a>)</th>
							<th><b>Tama&ntilde;o</b> (<a
								href="Log.do?_LOG_dir=<bean:write name="_LOG_directorio"/>&amp;_LOG_inicial=no&amp;_LOG_orden=longitud"><font
									size="-1">&#8743;</font></a>) (<a
								href="Log.do?_LOG_dir=<bean:write name="_LOG_directorio"/>&amp;_LOG_inicial=no&amp;_LOG_orden=longitudD"><font
									size="-1">&#8744;</font></a>)</th>
						</logic:notEqual>
						<logic:equal name="_LOG_entorno" value="inicial">
							<logic:notPresent name="contenedor">
								<th><b>Nombre</b> (<a href="Log.do?_LOG_orden=nombre"><font
										size="-1">&#8743;</font></a>) (<a href="Log.do?_LOG_orden=nombreD"><font
										size="-1">&#8744;</font></a>)</th>
								<th><b>Fecha de modificaci&oacute;n</b> (<a
									href="Log.do?_LOG_orden=fecha"><font size="-1">&#8743;</font></a>)
									(<a href="Log.do?_LOG_orden=fechaD"><font size="-1">&#8744;</font></a>)</th>
								<th><b>Tama&ntilde;o</b> (<a
									href="Log.do?_LOG_orden=longitud"><font size="-1">&#8743;</font></a>)
									(<a href="Log.do?_LOG_orden=longitudD"><font size="-1">&#8744;</font></a>)</th>
							</logic:notPresent>
							<logic:present name="contenedor">
								<th><b>Nombre</b> (<a href="Log.do?_LOG_ordenS=nombre"><font
										size="-1">&#8743;</font></a>) (<a
									href="Log.do?_LOG_ordenS=nombreD"><font size="-1">&#8744;</font></a>)</th>
								<th><b>Fecha de modificaci&oacute;n</b> (<a
									href="Log.do?_LOG_ordenS=fecha"><font size="-1">&#8743;</font></a>)
									(<a href="Log.do?_LOG_ordenS=fechaD"><font size="-1">&#8744;</font></a>)</th>
								<th><b>Tama&ntilde;o</b> (<a
									href="Log.do?_LOG_ordenS=longitud"><font size="-1">&#8743;</font></a>)
									(<a href="Log.do?_LOG_ordenS=longitudD"><font size="-1">&#8744;</font></a>)</th>
							</logic:present>
						</logic:equal>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
					<logic:iterate id="elemento" name="_LOG_lista">
						<tr>
							<td align="left"><logic:equal name="elemento"
									property="tipo" value="f">
									<a
										href="DescargaLog.do?_LOG_log=<bean:write name="elemento" property="ruta"/>/<bean:write name="elemento" property="nombre"/>&amp;_LOG_nombre=<bean:write name="elemento" property="nombre"/>">
										<b><bean:write name="elemento" property="nombre" /></b>
									</a>
								</logic:equal> <logic:equal name="elemento" property="tipo" value="d">
									<a
										href="Log.do?_LOG_dir=<bean:write name="elemento" property="ruta"/>/<bean:write name="elemento" property="nombre"/>&amp;_LOG_nombre=<bean:write name="elemento" property="nombre"/>&amp;_LOG_inicial=no">
										<b><bean:write name="elemento" property="nombre" /></b>
									</a>
                              &nbsp;<b>(DIR)</b>
								</logic:equal></td>
							<td align="center"><bean:write name="elemento"
									property="fecha" format="dd-MMM-yyyy HH:mm" /></td>
							<td align="right"><bean:write name="elemento"
									property="slongitud" /></td>
						</tr>
					</logic:iterate>
					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
	</div>
</logic:notEmpty>
