<%@ page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="windows-1252"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<title>..: Obtenci&oacute;n Carrera Profesional :..</title>
<meta http-equiv="Content-Type"
	content="application/xhtml+xml; charset=ISO-8859-1" />
<script type="text/javascript">
        //Completa los datos de usuario y password
        function submite()  {
            document.forms[0].submit();
        }
    </script>
</head>
<body onload="submite();">
	<form method="post" action="OCAPNuevaSolicitud.do?accion=irSolicitud"></form>
</body>
</html>
