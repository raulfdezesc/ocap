
<%@page import="es.jcyl.fqs.ocap.util.Pagina"%>

<% Pagina pagina = (Pagina)request.getAttribute("listados"); %>

<div class="paginacionJSP">
	<div class="primeraLinea">
		P&aacute;gina &nbsp;<b><bean:write name="listados"
				property="paginaActual" /></b> de <b><bean:write name="listados"
				property="NPaginas" /> </b>
	</div>
	<div class="segundaLinea">
		Registros Totales: <b><%=Integer.toString(pagina.getNRegistros())%></b>
	</div>

	<%if (pagina.getNRegistros() >= pagina.DEFAULT_REGISTROS_POR_PAGINA) {%>
	<div class="terceraLinea">
		<span class="anteriores"> <logic:notEqual name="listados"
				property="paginaActual" value="1">
               &lt;&lt;<a
					href="javascript:document.location.href='<html:rewrite page=""/><bean:write name="listados" property="URLPrimeraPagina"/>&bPagina=<%=es.jcyl.fqs.ocap.util.Constantes.SI%>';">Primera</a>
			</logic:notEqual> <logic:equal name="listados" property="anterior" value="true">
               &lt;&lt;<a
					href="javascript:document.location.href='<html:rewrite page=""/><bean:write name="listados" property="URLPaginaAnterior"/>&bPagina=<%=es.jcyl.fqs.ocap.util.Constantes.SI%>';">Anterior</a>
			</logic:equal>
		</span> <span class="posteriores"> <logic:equal name="listados"
				property="siguiente" value="true">
				<a
					href="javascript:document.location.href='<html:rewrite page=""/><bean:write name="listados" property="URLPaginaSiguiente"/>&bPagina=<%=es.jcyl.fqs.ocap.util.Constantes.SI%>';">Siguiente</a>&gt;&gt;
           </logic:equal> <logic:notEqual name="listados" property="NPaginas"
				value="<%=Integer.toString(pagina.getPaginaActual())%>">
				<a
					href="javascript:document.location.href='<html:rewrite page=""/><bean:write name="listados" property="URLUltimaPagina"/>&bPagina=<%=es.jcyl.fqs.ocap.util.Constantes.SI%>';">&Uacute;ltima</a>&gt;&gt;
           </logic:notEqual>
		</span>
	</div>
	<%}%>

</div>
