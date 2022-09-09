
<%
  // Serializamos el menu SEGU para generar una estructura HTML
  String menuHTML = null;
  try {
      java.util.ArrayList FuncionesMenu = (java.util.ArrayList)request.getSession().getAttribute("JCYLArrayFunciones");
      menuHTML = es.jcyl.framework.utiles.JCYLSeguHTML.getMenuHTML(FuncionesMenu);
      out.println(menuHTML);
  } catch (Exception e) {
      out.println("<ul></ul>");
  }
     
%>
