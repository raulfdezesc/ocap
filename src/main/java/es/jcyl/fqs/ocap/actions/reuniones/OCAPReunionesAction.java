 package es.jcyl.fqs.ocap.actions.reuniones;
 
 import es.jcyl.cf.seguridad.util.Usuario;
 import es.jcyl.fqs.ocap.actionforms.reuniones.OCAPReunionesForm;
 import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ln.evaluadores.OCAPEvaluadoresLN;
 import es.jcyl.fqs.ocap.ln.gestionCtes.OCAPGestionCtesLN;
 import es.jcyl.fqs.ocap.ln.reuniones.OCAPReunionesLN;
 import es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT;
 import es.jcyl.fqs.ocap.ot.reuniones.OCAPReunionesOT;
 import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.TrataError;
 import es.jcyl.fqs.ocap.util.Utilidades;
 import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.io.IOException;
 import java.sql.Connection;
 import java.text.ParsePosition;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.Date;
 import java.util.StringTokenizer;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import org.apache.log4j.Logger;
 import org.apache.struts.action.ActionError;
 import org.apache.struts.action.ActionErrors;
 import org.apache.struts.action.ActionForm;
 import org.apache.struts.action.ActionForward;
 import org.apache.struts.action.ActionMapping;
 import org.apache.struts.action.ActionMessage;
 import org.apache.struts.action.ActionMessages;
 
 public class OCAPReunionesAction extends OCAPGenericAction
 {
   public ActionForward irInsertar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errors = new ActionErrors();
     String sig = "error";
     String mensajeError = "Se ha producido un error";
     HttpSession session = request.getSession();
     Utilidades utilidades = new Utilidades();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Inicio");
       validarAccion(mapping, form, request, response);
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       if (request.getParameter("opcion").equals("CTE")) {
         OCAPGestionCtesLN ctesLN = new OCAPGestionCtesLN(jcylUsuario);
         Collection listadoCte = ctesLN.consultaOCAPCtesAct();
         Object[] listadoCtes = listadoCte.toArray();
 
         session.setAttribute(Constantes.COMBO_CTES, utilidades.ArrayObjectToArrayList(listadoCtes));
       }
 
       ((OCAPReunionesForm)form).setjspInicio(true);
 
       request.setAttribute("opcion", request.getParameter("opcion"));
 
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: ERROR: " + e.getMessage());
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward("irInsertar");
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward insertar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException, Exception
   {
     String sig = "error";
     String mensajeError = "Se ha producido un error";
     ActionMessages messages = new ActionMessages();
     ActionErrors errors = new ActionErrors();
     ArrayList resumen = new ArrayList();
     ArrayList listadoEval = new ArrayList();
 
     Connection con = null;
     try
     {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- ALTA REUNION --------- ");
       validarAccion(mapping, form, request, response);
 
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       messages = validarReunion(form, request.getParameter("opcion"));
 
       request.setAttribute("opcion", request.getParameter("opcion"));
 
       if ((messages == null) || (messages.isEmpty())) {
         con = JCYLGestionTransacciones.getConnection();
         con.setAutoCommit(false);
 
         SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_COMPLETA_HHMM);
         ParsePosition pos = new ParsePosition(0);
 
         JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
         OCAPReunionesLN oCAPReunionesLN = new OCAPReunionesLN(jcylUsuario);
 
         OCAPReunionesOT reunionesOT = null;
         OCAPReunionesOT evaluadoresOT = null;
 
         reunionesOT = new OCAPReunionesOT();
 
         reunionesOT.setCCteId(((OCAPReunionesForm)form).getCCte_id());
         reunionesOT.setFFecha(df.parse(((OCAPReunionesForm)form).getFReunion() + " " + ((OCAPReunionesForm)form).getNHora(), pos));
         reunionesOT.setDOrdendia(((OCAPReunionesForm)form).getDOrdendia());
         reunionesOT.setDDecisiones(((OCAPReunionesForm)form).getDDecisiones());
         reunionesOT.setNInformestotales(((OCAPReunionesForm)form).getNInformestotales());
         reunionesOT.setACreadoPor(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
 
         long reunionId = oCAPReunionesLN.altaOCAPReuniones(reunionesOT, con);
 
         resumen = Cadenas.obtenerArrayListTokens(((OCAPReunionesForm)form).getResumenEval(), "#");
 
         for (int i = 0; i < resumen.size(); i++) {
           ArrayList campos = new ArrayList();
           String cadena = (String)resumen.get(i);
           StringTokenizer token = new StringTokenizer(cadena, "$");
 
           while (token.hasMoreTokens()) {
             campos.add(token.nextToken());
           }
 
           evaluadoresOT = new OCAPReunionesOT();
           evaluadoresOT.setCEvaluadorId(Long.parseLong((String)campos.get(0)));
           evaluadoresOT.setCReunionId(reunionId);
           evaluadoresOT.setNEvaluacionesevaluador(Long.parseLong((String)campos.get(1)));
           evaluadoresOT.setNInformesrecibidos(Long.parseLong((String)campos.get(2)));
           evaluadoresOT.setNInformespositivos(Long.parseLong((String)campos.get(3)));
           evaluadoresOT.setNInformesnegativos(Long.parseLong((String)campos.get(4)));
           evaluadoresOT.setACreadoPor(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
 
           int result = oCAPReunionesLN.altaOCAPEvaluadoresTratados(evaluadoresOT, con);
 
           if (result == 1)
           {
             sig = "exito";
           }
           else sig = "error";
 
         }
 
         if (sig.equals("exito")) {
           con.commit();
           request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
           request.setAttribute("rutaVuelta", "OCAPReuniones.do?accion=irInsertar&opcion=" + request.getParameter("opcion"));
         } else {
           request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
         }
 
         OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA REUNION--------- ");
       }
       else if (((OCAPReunionesForm)form).getResumenEval() != null) {
         cargarListaEvaluadores(mapping, form, request, response);
       } else {
         ((OCAPReunionesForm)form).setjspInicio(true);
       }
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(ex, "error.general")));
       sig = "error";
       con.rollback();
       request.setAttribute("mensaje", mensajeError);
     }
     finally
     {
       con.setAutoCommit(true);
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
     {
       return mapping.findForward("exito");
     }if ((errors != null) && (!errors.isEmpty())) {
       saveErrors(request, errors);
 
       return mapping.findForward("fallo");
     }
     saveMessages(request, messages);
     request.setAttribute("tipoAccion", Constantes.INSERTAR);
 
     return mapping.findForward("irInsertar");
   }
 
   public ActionForward cargarListaEvaluadores(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     HttpSession session = request.getSession();
     ActionErrors errors = new ActionErrors();
     ArrayList listadoEval = new ArrayList();
     Utilidades utilidades = new Utilidades();
     String salida = "";
     long cCteId = 0L;
     ArrayList resumen = new ArrayList();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " cargarListaEvaluadores: Inicio");
       validarAccion(mapping, form, request, response);
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPComponentesfqsOT componente = new OCAPComponentesfqsOT();
       OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
       if (request.getParameter("opcion").equals("CTE")) {
         String cCteIdS = request.getParameter("cte");
         if ((cCteIdS != null) && (!cCteIdS.equals(""))) {
           cCteId = Long.parseLong(cCteIdS);
         }
         else {
           cCteId = ((OCAPReunionesForm)form).getCCte_id();
         }
       }
 
       componente.setCCteId(cCteId);
 
       listadoEval = oCAPEvaluadoresLN.consultaOCAPEvaluadores(componente, request.getParameter("opcion"), 1, -1);
 
       if ((((OCAPReunionesForm)form).getResumenEval() != null) && (!((OCAPReunionesForm)form).getResumenEval().equals(""))) {
         Object[] listadoelementos = listadoEval.toArray();
 
         int reg = listadoEval.size();
 
         listadoEval.removeAll(listadoEval);
 
         resumen = Cadenas.obtenerArrayListTokens(((OCAPReunionesForm)form).getResumenEval(), "#");
 
         for (int i = 0; i < resumen.size(); i++) {
           ArrayList campos = new ArrayList();
           String cadena = (String)resumen.get(i);
           StringTokenizer token = new StringTokenizer(cadena, "$");
 
           while (token.hasMoreTokens()) {
             campos.add(token.nextToken());
           }
           ((OCAPComponentesfqsOT)listadoelementos[i]).setNEvaluacionesevaluador(Long.parseLong((String)campos.get(1)));
           ((OCAPComponentesfqsOT)listadoelementos[i]).setNInformesrecibidos(Long.parseLong((String)campos.get(2)));
           ((OCAPComponentesfqsOT)listadoelementos[i]).setNInformespositivos(Long.parseLong((String)campos.get(3)));
           ((OCAPComponentesfqsOT)listadoelementos[i]).setNInformesnegativos(Long.parseLong((String)campos.get(4)));
 
           listadoEval.add(listadoelementos[i]);
         }
       }
 
       ((OCAPReunionesForm)form).setListadoEval(listadoEval);
       ((OCAPReunionesForm)form).setResumenEval("");
       ((OCAPReunionesForm)form).setjspInicio(false);
       request.setAttribute("opcion", request.getParameter("opcion"));
 
       OCAPConfigApp.logger.info(getClass().getName() + " cargarListaEvaluadores: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " cargarListaEvaluadores: ERROR: " + e.getMessage());
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward("irInsertar");
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionMessages validarReunion(ActionForm form, String opcion)
     throws Exception
   {
     ActionMessages messages = new ActionMessages();
     Utilidades utilidades = new Utilidades();
     Date dFReunion = null;
     Date nHoras = null;
     try
     {
       SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
       SimpleDateFormat dfHora = new SimpleDateFormat("HH:mm");
       ParsePosition pos = new ParsePosition(0);
       ParsePosition posHora = new ParsePosition(0);
 
       if ((((OCAPReunionesForm)form).getFReunion() == null) || (((OCAPReunionesForm)form).getFReunion().equals(""))) {
         messages.add("fFecha", new ActionMessage("errors.required", "Fecha"));
       } else if (((OCAPReunionesForm)form).getFReunion().length() < 10) {
         messages.add("fFecha", new ActionMessage("error.maskmsg", "Fecha"));
       } else {
         dFReunion = df.parse(((OCAPReunionesForm)form).getFReunion(), pos);
         if (dFReunion == null) {
           messages.add("fFecha", new ActionMessage("error.maskmsg", "Fecha"));
         }
       }
       if ((((OCAPReunionesForm)form).getNHora() == null) || (((OCAPReunionesForm)form).getNHora().equals(""))) {
         messages.add("nHora", new ActionMessage("errors.required", "Hora"));
       } else if (((OCAPReunionesForm)form).getNHora().length() < 5) {
         messages.add("nHora", new ActionMessage("error.maskmsg", "Hora"));
       } else {
         nHoras = dfHora.parse(((OCAPReunionesForm)form).getNHora(), posHora);
         if (nHoras == null) {
           messages.add("nHora", new ActionMessage("error.maskmsg", "Hora"));
         }
       }
 
       if ((((OCAPReunionesForm)form).getDOrdendia() == null) || (((OCAPReunionesForm)form).getDOrdendia().equals(""))) {
         messages.add("dOrden", new ActionMessage("errors.required", "Orden del día"));
       }
 
       if ((((OCAPReunionesForm)form).getDDecisiones() == null) || (((OCAPReunionesForm)form).getDDecisiones().equals(""))) {
         messages.add("dDecisiones", new ActionMessage("errors.required", "Decisiones"));
       }
 
       if (opcion.equals("CTE"))
       {
         if (((OCAPReunionesForm)form).getCCte_id() == 0L)
           messages.add("cCteId", new ActionMessage("errors.required", "Comité"));
       }
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.info(getClass().getName() + " validarEvaluadorReg: ERROR: " + e.getMessage());
       throw e;
     }
 
     return messages;
   }
 }

