 package es.jcyl.fqs.ocap.actions.preguntas;
 
 import es.jcyl.fqs.ocap.actionforms.preguntas.OCAPPreguntasForm;
 import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ln.preguntas.OCAPPreguntasLN;
 import es.jcyl.fqs.ocap.ot.preguntas.OCAPPreguntasOT;
 import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Pagina;
 import es.jcyl.fqs.ocap.util.Utilidades;
 import es.jcyl.framework.JCYLUsuario;
 import java.io.IOException;
 import java.util.Collection;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import org.apache.log4j.Logger;
 import org.apache.struts.action.ActionErrors;
 import org.apache.struts.action.ActionForm;
 import org.apache.struts.action.ActionForward;
 import org.apache.struts.action.ActionMapping;
 
 public class OCAPPreguntasAction extends OCAPGenericAction
 {
			private JCYLUsuario jcylUsuario;
   public ActionForward irBuscar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errors = new ActionErrors();
     String sig = "error";
     String mensajeError = "Se ha producido un error";
     HttpSession session = request.getSession();
     Utilidades utilidades = new Utilidades();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irBuscar: Inicio");
 
       jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
     }
     catch (Exception e)
     {
       JCYLUsuario jcylUsuario;
       OCAPConfigApp.logger.info(getClass().getName() + " irBuscar: ERROR: " + e.getMessage());
       request.setAttribute("mensaje", mensajeError);
     }
 
     return mapping.findForward("irPreguntas");
   }
 
   public ActionForward buscar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     String mensajeError = "Se ha producido un error";
     Logger log = null;
     ActionForward actionForward = null;
     HttpSession session = request.getSession();
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- CONSULTA DE OCAP_PREGUNTAS --------- ");
       ActionErrors errores = new ActionErrors();
       request.setAttribute("primeraConsulta", "false");
 
       if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR))) && (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
         form = (OCAPPreguntasForm)request.getSession().getAttribute("OCAPPreguntasForm");
         request.setAttribute("OCAPPreguntasForm", form);
       } else {
         request.getSession().setAttribute("OCAPPreguntasForm", form);
         session.setAttribute("iRegistro", 1 + "");
       }
 
       int primerRegistro = 1;
       int registrosPorPagina = 10;
 
       String s = null;
       s = request.getParameter("iRegistro");
       if (s != null)
         primerRegistro = Integer.parseInt(s);
       s = request.getParameter("nRegistrosP");
       if (s != null) {
         registrosPorPagina = Integer.parseInt(s);
       }
       OCAPPreguntasForm formulario = (OCAPPreguntasForm)form;
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPPreguntasLN ocapPreguntasLN = new OCAPPreguntasLN(jcylUsuario);
 
       String dNombre = null;
       dNombre = formulario.getDNombre();
 
       long idPregunta = formulario.getCPreguntaId();
 
       Collection elementos = ocapPreguntasLN.buscarOCAPPreguntas(idPregunta, dNombre, primerRegistro, registrosPorPagina);
       if (elementos != null) {
         OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Preguntas para la consulta");
         if (elementos.size() == 0)
         {
           request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
           request.removeAttribute("conDatos");
           request.getSession().removeAttribute("paginaOCAPPreguntasOT");
         } else {
           Object[] listadoelementos = elementos.toArray();
 
           int reg = elementos.size();
 
           int nListado = 0;
           nListado = ocapPreguntasLN.listadoOCAPPreguntasCuenta(dNombre);
           request.removeAttribute("sinDatos");
           Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
           pagina.setElementos(elementos);
           pagina.setRegistroActual(primerRegistro);
           pagina.setNRegistros(nListado);
           pagina.setRegistrosPorPagina(registrosPorPagina);
           request.setAttribute("paginaOCAPPreguntasOT", pagina);
           request.getSession().setAttribute("conDatos", "Hemos recuperado datos");
           request.removeAttribute("menu");
           request.getSession().removeAttribute("menu");
         }
       }
       else {
         request.setAttribute("errorConsultando", "Error consultando en la base de datos");
       }
       OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAP_PREGUNTAS ------- ");
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       request.setAttribute("mensaje", mensajeError);
       return mapping.findForward("error");
     }
 
     return mapping.findForward("irPreguntas");
   }
 
   public ActionForward irEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irEditar: Inicio");
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPPreguntasLN ocapPreguntasLN = new OCAPPreguntasLN(jcylUsuario);
       OCAPPreguntasOT datos = new OCAPPreguntasOT();
       OCAPPreguntasForm formulario = (OCAPPreguntasForm)form;
 
       String cPreguntaIdS = request.getParameter("cPreguntaIdS");
       long cPreguntaId;
       if ((cPreguntaIdS != null) && (!cPreguntaIdS.equals(""))) {
         cPreguntaId = Long.parseLong(cPreguntaIdS);
         OCAPConfigApp.logger.info("cPreguntaId: " + cPreguntaId);
       }
       else {
         return mapping.findForward("irModificarPregunta");
       }
 
       datos = ocapPreguntasLN.buscarOCAPPreguntaId(cPreguntaId);
 
       if (datos.getCPreguntaId() == 0L) {
         OCAPConfigApp.logger.info("No encontramos OCAPPreguntasOT");
         sig = "error";
         request.getSession().removeAttribute("conDatosEdicion");
         request.setAttribute("mensaje", "No se encuentra el registro");
       } else {
         OCAPConfigApp.logger.info("Se ha encontrado OCAPPreguntasOT");
         request.getSession().setAttribute("conDatosEdicion", "");
         request.getSession().removeAttribute("sinDatosEdicion");
         request.setAttribute("OCAPPreguntasOT", datos);
 
         formulario.setCPreguntaId(datos.getCPreguntaId());
         formulario.setCPreguntaNuevoId(0L);
         formulario.setDNombre(datos.getDNombre());
         formulario.setDObservaciones(datos.getDObservaciones());
         formulario.setCTipoPregunta(datos.getCTipoPregunta());
         formulario.setFUsuAlta(datos.getFUsuAlta());
         formulario.setFUsuModi(datos.getFUsuModi());
         formulario.setNElementos(datos.getNElementos());
         formulario.setNNivel(datos.getNNivel());
         formulario.setNSubElementos(datos.getNSubElementos());
         formulario.setCUsuAlta(datos.getCUsuAlta());
         formulario.setCUsuModi(datos.getCUsuModi());
         formulario.setBNumeracion(datos.getBNumeracion());
         formulario.setBCorto(datos.getBCorto());
 
         sig = "irModificarPregunta";
         request.setAttribute("tipoAccion", Constantes.MODIFICAR);
       }
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
     }
     ActionForward actionForward = mapping.findForward(sig);
     OCAPConfigApp.logger.info("forward--> " + actionForward.getPath());
     OCAPConfigApp.logger.info("...........se sale del Action");
     OCAPConfigApp.logger.info(getClass().getName() + " irEditar: Fin");
 
     return actionForward;
   }
 
   public ActionForward insertar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     String mensajeError = "Se ha producido un error";
     String fResolucion = null;
     String fPublicacion = null;
     String fInicioMC = null;
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- ALTA OCAP_PREGUNTAS --------- ");
 
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPPreguntasLN oCAPPreguntasLN = new OCAPPreguntasLN(jcylUsuario);
       OCAPPreguntasOT datos = new OCAPPreguntasOT();
 
       OCAPPreguntasForm formulario = (OCAPPreguntasForm)form;
       OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
 
       datos.setCPreguntaId(formulario.getCPreguntaNuevoId());
       OCAPConfigApp.logger.info("cPreguntaid : " + datos.getCPreguntaNuevoId());
 
       datos.setDNombre(formulario.getDNombre());
       datos.setDObservaciones(formulario.getDObservaciones());
       datos.setCTipoPregunta(formulario.getCTipoPregunta());
       datos.setFUsuAlta(formulario.getFUsuAlta());
       datos.setFUsuModi(formulario.getFUsuModi());
       datos.setNElementos(formulario.getNElementos());
       datos.setNNivel(formulario.getNNivel());
       datos.setNSubElementos(formulario.getNSubElementos());
       datos.setCUsuAlta(formulario.getCUsuAlta());
       datos.setCUsuModi(formulario.getCUsuModi());
       datos.setBNumeracion(formulario.getBNumeracion());
       datos.setBCorto(formulario.getBCorto());
 
       int result = oCAPPreguntasLN.altaOCAPPreguntas(datos);
 
       if (result == 1)
       {
         request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
         request.setAttribute("rutaVuelta", "OCAPPreguntas.do?accion=irBuscar");
         sig = "exito";
       }
       else {
         request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
         sig = "error";
       }
       OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA OCAPPreguntas --------- ");
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       sig = "error";
 
       if (ex.getMessage().startsWith("ORA-00001")) {
         mensajeError = "Error: Registro duplicado";
       }
 
       request.setAttribute("mensaje", mensajeError);
     }
 
     return mapping.findForward("irModificarPregunta");
   }
 
   public ActionForward irAltaRespuestas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errors = new ActionErrors();
     String sig = "error";
     String mensajeError = "Se ha producido un error";
     HttpSession session = request.getSession();
     Utilidades utilidades = new Utilidades();
     try
     {
       form = (OCAPPreguntasForm)request.getSession().getAttribute("OCAPRespuestasForm");
 
       OCAPConfigApp.logger.info(getClass().getName() + " irAltaRespuestas: Inicio");
 
       jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
     }
     catch (Exception e)
     {
       JCYLUsuario jcylUsuario;
       OCAPConfigApp.logger.info(getClass().getName() + " irAltaRespuestas: ERROR: " + e.getMessage());
       request.setAttribute("mensaje", mensajeError);
     }
 
     return mapping.findForward("irAltaRespuestas");
   }
 
   public ActionForward insertarRespuesta(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     String mensajeError = "Se ha producido un error";
     String fResolucion = null;
     String fPublicacion = null;
     String fInicioMC = null;
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- ALTA OCAP_RESPUESTAS --------- ");
 
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
       OCAPPreguntasLN oCAPPreguntasLN = new OCAPPreguntasLN(jcylUsuario);
       OCAPPreguntasOT datos = new OCAPPreguntasOT();
 
       OCAPPreguntasForm formulario = (OCAPPreguntasForm)form;
       OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
 
       datos.setCRespuestaId(formulario.getCRespuestaId());
       datos.setCCuestionarioId(formulario.getCCuestionarioId());
       datos.setCPreguntaId(formulario.getCPreguntaId());
       datos.setDNombreRespuesta(formulario.getDNombreRespuesta());
       datos.setDValor(formulario.getDValor());
       datos.setNCreditos(formulario.getNCreditos());
       datos.setCUsuAlta(formulario.getCUsuAlta());
       datos.setCUsuModi(formulario.getCUsuModi());
 
       request.getSession().setAttribute("OCAPRespuestasForm", formulario);
 
       OCAPConfigApp.logger.info("cRespuestaId : " + datos.getCRespuestaId());
 
       int result = oCAPPreguntasLN.altaOCAPRespuestas(datos);
 
       if (result == 1)
       {
         request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
         request.setAttribute("rutaVuelta", "OCAPPreguntas.do?accion=irAltaRespuestas");
         sig = "exito";
       }
       else {
         request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
         sig = "error";
       }
       OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA OCAPPreguntas --------- ");
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       sig = "error";
 
       if (ex.getMessage().startsWith("ORA-00001")) {
         mensajeError = "Error: Registro duplicado";
       }
 
       request.setAttribute("mensaje", mensajeError);
     }
 
     return mapping.findForward("irAltaRespuestas");
   }
 
   public ActionForward limpiar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errores = new ActionErrors();
     HttpSession session = request.getSession();
 
     request.setAttribute("mensaje", null);
 
     return mapping.findForward("irAltaRespuestas");
   }
 }

