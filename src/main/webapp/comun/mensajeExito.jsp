<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<%@ page import="java.util.StringTokenizer"%>

<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="bean.tld" prefix="bean"%>

<script language="javaScript" type="text/javascript"
	src="javascript/comun.js" charset="windows-1252"></script>

<% String rutaVuelta = (String)request.getAttribute("rutaVuelta");   
   String usuarioDeSesion = es.jcyl.framework.JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO;
   //la variable alta informa si se viene de una pantalla de alta
   String alta=(String) request.getAttribute("Alta");
   
   //Si no se viene desde una pantalla e alta, la variable no se informa.
   if(alta == null) {alta="";}
   //System.out.println("alta"+alta);
   StringTokenizer tokenIn = null;
   String formulario = null;
   
   if(rutaVuelta!= null){
     tokenIn  = new StringTokenizer(rutaVuelta, "?");
     formulario = tokenIn.nextToken().trim();
     if (formulario.substring(0, 5).equals(request.getContextPath()))
         formulario = "/OCAPSolicitudes.do";
   }
%>

<div class="ocultarCampo">
	<div class="cuadroContenedorConsultas">
		<div class="titulocajaformulario">
			<bean:message key="general.exitoTit" />
		</div>
		<div class="cajaformulario">
			<p class="mensajeAviso">
				<bean:message key="general.exito" />
			</p>
			<div class="colocarMensajeExito">
				<html:form action="<%=formulario%>">
					<br />
					<% if (formulario.equals("/OCAPSolicitudes.do")) { %>
					<script language="JavaScript" type="text/javascript">
                  function volver() {
                     document.location='<%=rutaVuelta%>';
                  }
                  </script>
					<div class="espaciador"></div>
					<div class="limpiadora"></div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <input
							type="button" name="boton" class="formBoton" onclick="volver()"
							value="<bean:message key="button.aceptar"/>" />
						</span> <span class="derBoton"></span>
					</div>
					<div class="espaciador"></div>
					<div class="limpiadora"></div>
					<script language="JavaScript" type="text/javascript">
                     document.forms[0].boton.focus();
                  </script>
					<% } else { %>
					<div class="espaciador"></div>
					<div class="limpiadora"></div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <input
							type="button" name="boton" class="formBoton"
							onclick="enviar('<%=rutaVuelta%>');"
							value="<bean:message key="button.volver"/>" />
						</span> <span class="derBoton"></span>
					</div>
					<div class="espaciador"></div>
					<div class="limpiadora"></div>
					<script language="JavaScript" type="text/javascript">
                     document.forms[0].boton.focus();
                  </script>
					<script language="JavaScript" type="text/javascript">
                     function volver() {
                        document.location='<%=rutaVuelta%>';
                     }
                  </script>
					<br>
					<br>
					<%--solo cuando venga de un alta, si viene de modificar, baja, etc...no debe salir este boton--%>
					<% if (alta.equals("Alta")) { %>
					<% if (formulario.equals("OCAPSolicitudes.do")) { %>
					<div class="espaciador"></div>
					<div class="limpiadora"></div>
					<div class="botonAccion">
						<span class="izqBoton"></span> <span class="cenBoton"> <input
							type="button" name="botonB" class="formBoton"
							onclick="javascript:enviar('OCAPSolicitudes.do?accion=irInsertar');"
							property="" value="<bean:message key="button.nuevaAlta"/>" />
						</span> <span class="derBoton"></span>
					</div>
					<div class="espaciador"></div>
					<div class="limpiadora"></div>
					<html:hidden property="cUsr_id" />
					<% } %>
					<% } %>
					<% } %>
				</html:form>
			</div>
		</div>
	</div>
</div>
<!-- /Fin de ocultarCampo -->
