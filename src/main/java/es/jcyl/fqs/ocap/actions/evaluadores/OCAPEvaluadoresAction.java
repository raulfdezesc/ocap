 package es.jcyl.fqs.ocap.actions.evaluadores;
 
 import es.jcyl.fqs.ocap.actionforms.componentesfqs.OCAPComponentesfqsForm;
import es.jcyl.fqs.ocap.actionforms.solicitudes.OCAPNuevaSolicitudForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
 import es.jcyl.fqs.ocap.ln.componentesCtes.OCAPComponentesCtesLN;
 import es.jcyl.fqs.ocap.ln.comunidades.OCAPComunidadesLN;
 import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
 import es.jcyl.fqs.ocap.ln.especialidades.OCAPEspecialidadesLN;
 import es.jcyl.fqs.ocap.ln.evaluadores.OCAPEvaluadoresLN;
 import es.jcyl.fqs.ocap.ln.expedientes.OCAPExpedientesLN;
 import es.jcyl.fqs.ocap.ln.gerencias.OCAPGerenciasLN;
 import es.jcyl.fqs.ocap.ln.gestionCtes.OCAPGestionCtesLN;
 import es.jcyl.fqs.ocap.ln.grado.OCAPGradoLN;
 import es.jcyl.fqs.ocap.ln.itinerarios.OCAPItinerariosLN;
 import es.jcyl.fqs.ocap.ln.localidades.OCAPLocalidadesLN;
 import es.jcyl.fqs.ocap.ln.provincias.OCAPProvinciasLN;
 import es.jcyl.fqs.ocap.ot.categProfesionales.OCAPCategProfesionalesOT;
 import es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT;
 import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
 import es.jcyl.fqs.ocap.ot.especialidades.OCAPEspecialidadesOT;
 import es.jcyl.fqs.ocap.ot.expedientes.OCAPExpedientesOT;
 import es.jcyl.fqs.ocap.ot.gerencias.OCAPGerenciasOT;
 import es.jcyl.fqs.ocap.ot.gestionCtes.OCAPGestionCtesOT;
 import es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT;
 import es.jcyl.fqs.ocap.ot.itinerarios.OCAPItinerariosOT;
 import es.jcyl.fqs.ocap.ot.provincias.OCAPProvinciasOT;
 import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
 import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Pagina;
 import es.jcyl.fqs.ocap.util.TrataError;
 import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLUsuario;
 import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
 import java.util.ArrayList;
 import java.util.Collection;
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
 
 public class OCAPEvaluadoresAction extends OCAPGenericAction
 {
   public ActionForward irInsertar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errors = new ActionErrors();
     HttpSession session = request.getSession();
     Utilidades utilidades = new Utilidades();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Inicio");
 
       validarAccion(mapping, form, request, response);
 
       request.setAttribute("tipoAccion", Constantes.INSERTAR);
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPComunidadesLN comunidadesLN = new OCAPComunidadesLN(jcylUsuario);
 
       Collection listadoComu = comunidadesLN.listarComunidades();
       Object[] listadoComunidades = listadoComu.toArray();
 
       session.setAttribute(Constantes.COMBO_COMUNIDADES_PROF, utilidades.ArrayObjectToArrayList(listadoComunidades));
 
       session.setAttribute(Constantes.COMBO_COMUNIDADES, utilidades.ArrayObjectToArrayList(listadoComunidades));
 
       OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
       Collection listadoGra = gradoLN.listadoOCAPGrado();
       Object[] listadoGrado = listadoGra.toArray();
 
       session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, utilidades.ArrayObjectToArrayList(listadoGrado));
 
       session.setAttribute(Constantes.COMBO_LOCALIDADES, new ArrayList());
 
       session.setAttribute(Constantes.COMBO_PROVINCIAS_PROF, new ArrayList());
 
       session.setAttribute(Constantes.COMBO_PROVINCIAS, new ArrayList());
 
       request.setAttribute("opcion", "registro");
 
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Fin");
     }
     catch (Exception e)
     {
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
 
   public ActionForward cargarComboProvincias(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errors = new ActionErrors();
     HttpSession session = request.getSession();
     Utilidades utilidades = new Utilidades();
     String sig = "";
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboProvincias: Inicio");
 
       validarAccion(mapping, form, request, response);
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm)form;
 
       if (request.getParameter("tipo").equals("datosPersonales")) {
         if ((formulario.getCComunidad_id() != null) && (!formulario.getCComunidad_id().equals("")))
         {
           OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
 
           Collection listadoProv = provinciasLN.listarProvinciasComunidad(formulario.getCComunidad_id());
 
           Object[] listadoProvincias = listadoProv.toArray();
 
           session.setAttribute(Constantes.COMBO_PROVINCIAS, utilidades.ArrayObjectToArrayList(listadoProvincias));
         }
         else {
           session.setAttribute(Constantes.COMBO_PROVINCIAS, new ArrayList());
         }
 
         session.setAttribute(Constantes.COMBO_LOCALIDADES, new ArrayList());
       }
       else if ((formulario.getCComuni_id() != null) && (!formulario.getCComuni_id().equals("")))
       {
         OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
 
         Collection listadoProv = provinciasLN.listarProvinciasComunidad(formulario.getCComuni_id());
 
         Object[] listadoProvincias = listadoProv.toArray();
 
         session.setAttribute(Constantes.COMBO_PROVINCIAS_PROF, utilidades.ArrayObjectToArrayList(listadoProvincias));
       }
       else {
         session.setAttribute(Constantes.COMBO_PROVINCIAS_PROF, new ArrayList());
       }
 
       if (request.getParameter("jspVuelta").equals(Constantes.INSERTAR)) {
         request.setAttribute("tipoAccion", Constantes.INSERTAR);
       }
       else {
         request.setAttribute("tipoAccion", Constantes.MODIFICAR);
 
         request.setAttribute("nombre", request.getParameter("nombre"));
       }
 
       request.setAttribute("opcion", request.getParameter("opcion"));
 
       request.setAttribute("tarea", request.getParameter("tarea"));
 
       sig = "irInsertar";
 
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboProvincias: Fin");
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboProvincias: ERROR: " + e.getMessage());
 
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward(sig);
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward insertar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     ActionMessages messages = new ActionMessages();
     ActionErrors errors = new ActionErrors();
     try
     {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- ALTA EVALUADOR --------- ");
       validarAccion(mapping, form, request, response);
 
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       messages = validarEvaluadorReg(form, request);
 
       if ((messages == null) || (messages.isEmpty())) {
         OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
         OCAPComponentesfqsOT componente = new OCAPComponentesfqsOT();
 
         OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm)form;
 
         OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
 
         componente.setDNombre(formulario.getDNombre());
         componente.setDApellidos(formulario.getDApellidos());
         componente.setCDni(formulario.getCDni());
         if ((formulario.getBSexo() != null) && (!formulario.getBSexo().equals("")))
         {
           componente.setBSexo(formulario.getBSexo());
         }
 
         if ((formulario.getNTelefono1() != null) && (!formulario.getNTelefono1().equals("")))
         {
           componente.setNTelefono1(formulario.getNTelefono1());
         }
 
         if ((formulario.getACorreoelectronico() != null) && (!formulario.getACorreoelectronico().equals("")))
         {
           componente.setACorreoelectronico(formulario.getACorreoelectronico());
         }
 
         if ((formulario.getADir_nombre() != null) && (!formulario.getADir_nombre().equals("")))
         {
           componente.setADirNombre(formulario.getADir_nombre());
         }
 
         if ((formulario.getADir_num() != null) && (!formulario.getADir_num().equals("")))
         {
           componente.setADirNum(formulario.getADir_num());
         }
 
         if ((formulario.getADir_escalera() != null) && (!formulario.getADir_escalera().equals("")))
         {
           componente.setADirEscalera(formulario.getADir_escalera());
         }
 
         if ((formulario.getADir_piso() != null) && (!formulario.getADir_piso().equals("")))
         {
           componente.setADirPiso(formulario.getADir_piso());
         }
 
         if ((formulario.getADir_letra() != null) && (!formulario.getADir_letra().equals("")))
         {
           componente.setADirLetra(formulario.getADir_letra());
         }
 
         if ((formulario.getCCp() != null) && (!formulario.getCCp().equals("")))
         {
           componente.setCCp(formulario.getCCp());
         }
 
         if ((formulario.getCComunidad_id() != null) && (!formulario.getCComunidad_id().equals("")))
         {
           componente.setCComunidadId(formulario.getCComunidad_id());
         }
 
         if ((formulario.getCProv_id() != null) && (!formulario.getCProv_id().equals("")))
         {
           componente.setCProvId(formulario.getCProv_id());
         }
 
         if ((formulario.getCLocalidad_id() != null) && (!formulario.getCLocalidad_id().equals("")))
         {
           componente.setCLocalidadId(formulario.getCLocalidad_id());
         }
 
         if ((formulario.getATitulacion() != null) && (!formulario.getATitulacion().equals("")))
         {
           componente.setATitulacion(formulario.getATitulacion());
         }
 
         if ((formulario.getAEspecialidad() != null) && (!formulario.getAEspecialidad().equals("")))
         {
           componente.setAEspecialidad(formulario.getAEspecialidad());
         }
 
         if ((formulario.getACentrotrabajo() != null) && (!formulario.getACentrotrabajo().equals("")))
         {
           componente.setACentrotrabajo(formulario.getACentrotrabajo());
         }
 
         if (formulario.getCGrado_id() != 0L) {
           componente.setCGradoId(formulario.getCGrado_id());
         }
 
         if ((formulario.getCComuni_id() != null) && (!formulario.getCComuni_id().equals("")))
         {
           componente.setCComuniId(formulario.getCComuni_id());
         }
 
         if ((formulario.getCProvincia_id() != null) && (!formulario.getCProvincia_id().equals("")))
         {
           componente.setCProvinId(formulario.getCProvincia_id());
         }
 
         if ((formulario.getAForm_acreditacion() != null) && (!formulario.getAForm_acreditacion().equals("")))
         {
           componente.setAFormAcreditacion(formulario.getAForm_acreditacion());
         }
 
         if ((formulario.getAForm_gestion() != null) && (!formulario.getAForm_gestion().equals("")))
         {
           componente.setAFormGestion(formulario.getAForm_gestion());
         }
 
         if ((formulario.getAForm_evaluacion() != null) && (!formulario.getAForm_evaluacion().equals("")))
         {
           componente.setAFormEvaluacion(formulario.getAForm_evaluacion());
         }
 
         if ((formulario.getAExpprof_ss() != null) && (!formulario.getAExpprof_ss().equals("")))
         {
           componente.setAExpprofSs(formulario.getAExpprof_ss());
         }
 
         if ((formulario.getAExpcal_asistencia() != null) && (!formulario.getAExpcal_asistencia().equals("")))
         {
           componente.setAExpcalAsistencia(formulario.getAExpcal_asistencia());
         }
 
         if ((formulario.getAAct_docente() != null) && (!formulario.getAAct_docente().equals("")))
         {
           componente.setAActDocente(formulario.getAAct_docente());
         }
 
         componente.setaCreadoPorComponente(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
         componente.setACreadoPorExp(componente.getaCreadoPorComponente());
 
         int result = oCAPEvaluadoresLN.altaOCAPEvaluadores(componente);
 
         OCAPConfigApp.logger.info("RESULT: " + result);
         if (result == 1) {
           request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
 
           request.setAttribute("rutaVuelta", "OCAPEvaluadores.do?accion=irInsertar");
 
           sig = "exito";
         } else {
           request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
 
           sig = "error";
         }
 
         OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA Evaluador--------- ");
       }
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       if (ex.getMessage().startsWith("ORA-00001")) {
         messages.add("Evaluador", new ActionMessage("registro.duplicados", "Evaluador"));
       }
       else
       {
         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(ex, "error.general")));
       }
 
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
     request.setAttribute("opcion", "registro");
 
     return mapping.findForward("irInsertar");
   }
 
   public ActionForward irBuscar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errors = new ActionErrors();
     HttpSession session = request.getSession();
     Utilidades utilidades = new Utilidades();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irBuscar: Inicio");
 
       validarAccion(mapping, form, request, response);
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       if ((request.getParameter("opcion").equals("cp")) || (request.getParameter("opcion").equals("ce")))
       {
         OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
 
         Collection listadoConv = convocatoriasLN.listarConvocatorias();
 
         Object[] listadoConvocatorias = listadoConv.toArray();
 
         session.setAttribute(Constantes.COMBO_CONVOCATORIAS, utilidades.ArrayObjectToArrayList(listadoConvocatorias));
       }
 
       OCAPGestionCtesLN ctesLN = new OCAPGestionCtesLN(jcylUsuario);
 
       Collection listadoCte = ctesLN.consultaOCAPCtesAct();
       Object[] listadoCtes = listadoCte.toArray();
 
       session.setAttribute(Constantes.COMBO_CTES, utilidades.ArrayObjectToArrayList(listadoCtes));
 
       ((OCAPComponentesfqsForm)form).setjspInicio(true);
 
       request.setAttribute("opcion", request.getParameter("opcion"));
 
       session.setAttribute("iRegistro", Integer.toString(1));
 
       OCAPConfigApp.logger.info(getClass().getName() + " irBuscar: Fin");
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irBuscar: ERROR: " + e.getMessage());
 
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward("irBuscar");
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward buscar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String mensajeError = "Se ha producido un error";
     Logger log = null;
     ActionForward actionForward = null;
     Utilidades utilidades = new Utilidades();
     ActionMessages messages = new ActionMessages();
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- CONSULTA DE EVALUADORES --------- ");
       HttpSession session = request.getSession();
       ActionErrors errores = new ActionErrors();
       request.setAttribute("primeraConsulta", "false");
       request.setAttribute("opcion", request.getParameter("opcion"));
 
       if ((((OCAPComponentesfqsForm)form).getNAniosConv() != null) && (!((OCAPComponentesfqsForm)form).getNAniosConv().equals("")) && (!utilidades.esNumerico(((OCAPComponentesfqsForm)form).getNAniosConv())))
       {
         messages.add("nAniosConv", new ActionMessage("errors.nonumero", "Año"));
       }
 
       if ((messages == null) || (messages.isEmpty())) {
         if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR))) && (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI)))
         {
           form = (OCAPComponentesfqsForm)request.getSession().getAttribute("OCAPComponentesfqsFormBuscador");
           request.setAttribute("OCAPComponentesfqsForm", form);
         } else {
           request.getSession().setAttribute("OCAPComponentesfqsFormBuscador", form);
 
           session.setAttribute("iRegistro", 1 + "");
         }
 
         int primerRegistro = 1;
         int registrosPorPagina = 10;
 
         String registro = null;
 
         if ((registro = request.getParameter("iRegistro")) != null)
         {
           try
           {
             primerRegistro = Integer.parseInt(registro);
             session.setAttribute("iRegistro", primerRegistro + "");
           }
           catch (NumberFormatException ne) {
           }
         }
         else if ((registro = session.getAttribute("iRegistro") == null ? null : session.getAttribute("iRegistro").toString()) != null)
         {
           try
           {
             primerRegistro = Integer.parseInt(registro);
           }
           catch (NumberFormatException ne)
           {
           }
         }
 
         if ((!Constantes.SI.equals(request.getParameter("bPagina"))) && (!Constantes.SI.equals(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR))))
         {
           primerRegistro = 1;
         }
 
         if (Constantes.SI.equals(request.getParameter("vuelta"))) {
           primerRegistro = 1;
         }
         String s = null;
         s = request.getParameter("nRegistrosP");
 
         if (s != null) {
           registrosPorPagina = Integer.parseInt(s);
         }
         String opcion = request.getParameter("opcion");
 
         JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
         OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
         OCAPGestionCtesLN oCAPGestionCtesLN = new OCAPGestionCtesLN(jcylUsuario);
 
         OCAPComponentesfqsOT componente = new OCAPComponentesfqsOT();
 
         componente.setDNombre(((OCAPComponentesfqsForm)form).getDNombre());
         componente.setDApellidos(((OCAPComponentesfqsForm)form).getDApellidos());
         componente.setCDni(((OCAPComponentesfqsForm)form).getCDni());
         componente.setATitulacion(((OCAPComponentesfqsForm)form).getATitulacion());
 
         if (request.getParameter("opcion").equals("CTE"))
         {
           String cCteIdS = request.getParameter("cte");
 
           if ((cCteIdS != null) && (!cCteIdS.equals(""))) {
             int cteId = Integer.parseInt(cCteIdS);
             OCAPConfigApp.logger.info("cCteId: " + cteId);
             componente.setCCteId(cteId);
             request.setAttribute("cte", request.getParameter("cte"));
           }
           else {
             request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
 
             return mapping.findForward("error");
           }
         } else {
           componente.setCCteId(((OCAPComponentesfqsForm)form).getCCte_id());
         }
 
         componente.setNAniosConv(((OCAPComponentesfqsForm)form).getNAniosConv());
         componente.setCConvocatoriaId(((OCAPComponentesfqsForm)form).getCConvocatoria_id());
 
         Collection elementos = oCAPEvaluadoresLN.consultaOCAPEvaluadores(componente, opcion, primerRegistro, registrosPorPagina);
 
         if (elementos != null) {
           OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Evaluadores para la consulta");
 
           if (elementos.size() == 0) {
             request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
 
             request.getSession().removeAttribute("listados");
           } else {
             Object[] listadoelementos = elementos.toArray();
 
             OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
 
             int reg = elementos.size();
 
             elementos.removeAll(elementos);
 
             for (int i = 0; i < reg; i++)
             {
               if (((OCAPComponentesfqsOT)listadoelementos[i]).getCConvocatoriaId() != 0L)
               {
                 ((OCAPComponentesfqsOT)listadoelementos[i]).setDConvocNombre(convocatoriasLN.buscarOCAPConvocatorias(((OCAPComponentesfqsOT)listadoelementos[i]).getCConvocatoriaId()).getDNombre());
               }
               else ((OCAPComponentesfqsOT)listadoelementos[i]).setDConvocNombre("");
 
               if (((OCAPComponentesfqsOT)listadoelementos[i]).getCCteId() != 0L)
               {
                 ((OCAPComponentesfqsOT)listadoelementos[i]).setDCteNombre(oCAPGestionCtesLN.buscarOCAPCte(((OCAPComponentesfqsOT)listadoelementos[i]).getCCteId()).getDNombre());
               }
               else {
                 OCAPComponentesCtesLN componentesLN = new OCAPComponentesCtesLN(jcylUsuario);
 
                 OCAPComponentesfqsOT cte = componentesLN.buscarOCAPComponentescte(((OCAPComponentesfqsOT)listadoelementos[i]).getCCompfqsId());
 
                 if (cte.getCCteId() != 0L)
                   ((OCAPComponentesfqsOT)listadoelementos[i]).setDCteNombre(oCAPGestionCtesLN.buscarOCAPCte(cte.getCCteId()).getDNombre());
                 else {
                   ((OCAPComponentesfqsOT)listadoelementos[i]).setDCteNombre("");
                 }
               }
               elementos.add(listadoelementos[i]);
             }
 
             int nListado = 0;
             nListado = oCAPEvaluadoresLN.listadoOCAPEvaluadoresCuenta(componente, opcion);
 
             session.setAttribute("numEvaluadores", new Integer(nListado));
 
             request.removeAttribute("sinDatos");
             Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
 
             pagina.setElementos(elementos);
             pagina.setRegistroActual(primerRegistro);
             pagina.setNRegistros(((Integer)session.getAttribute("numEvaluadores")).intValue());
             pagina.setRegistrosPorPagina(registrosPorPagina);
             request.setAttribute("listados", pagina);
             request.removeAttribute("menu");
             request.getSession().removeAttribute("menu");
           }
         } else {
           request.setAttribute("errorConsultando", "Error consultando en la base de datos");
         }
       }
 
       OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAPEvaluadores ------- ");
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       request.setAttribute("mensaje", mensajeError);
       ((OCAPComponentesfqsForm)form).setjspInicio(false);
       return mapping.findForward("error");
     }
 
     if ((messages == null) || (messages.isEmpty()))
     {
       return mapping.findForward("irBuscar");
     }
     saveMessages(request, messages);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward listarEvaluados(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errors = new ActionErrors();
     ActionMessages messages = new ActionMessages();
     Utilidades utilidades = new Utilidades();
     String sig = "";
     ArrayList listadoItinerarios = new ArrayList();
     ArrayList listadoCategEspec = new ArrayList();
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- Listar evaluados --------- ");
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       request.setAttribute("opcion", request.getParameter("opcion"));
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
       OCAPUsuariosOT componentesOT = new OCAPUsuariosOT();
 
       if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR))) && (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI)))
       {
         form = (OCAPComponentesfqsForm)request.getSession().getAttribute("OCAPComponentesFormBuscador");
 
         request.setAttribute("OCAPComponentesfqsForm", form);
       } else {
         request.getSession().setAttribute("OCAPComponentesFormBuscador", form);
 
         session.setAttribute("iRegistro", 1 + "");
       }
 
       int primerRegistro = 1;
       int registrosPorPagina = 10;
 
       String registro = null;
 
       if ((registro = request.getParameter("iRegistro")) != null)
       {
         try
         {
           primerRegistro = Integer.parseInt(registro);
           session.setAttribute("iRegistro", primerRegistro + "");
         }
         catch (NumberFormatException ne) {
         }
       }
       else if ((registro = session.getAttribute("iRegistro") == null ? null : session.getAttribute("iRegistro").toString()) != null)
       {
         try
         {
           primerRegistro = Integer.parseInt(registro);
         }
         catch (NumberFormatException ne)
         {
         }
       }
       if ((!Constantes.SI.equals(request.getParameter("bPagina"))) && (!Constantes.SI.equals(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR))))
       {
         primerRegistro = 1;
       }
 
       String s = request.getParameter("nRegistrosP");
 
       if (s != null) {
         registrosPorPagina = Integer.parseInt(s);
       }
       if ((jcylUsuario.getUser().getRol() != null) && ((jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL)) || ((jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION)) && (!"cp".equals(request.getParameter("opcion")))) || (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR)) || (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC))))
       {
         sig = "irEvaluadosEpr";
         if ("ENCUESTA".equals(request.getParameter("opcion"))) {
           componentesOT.setOpcion("ENCUESTA");
           sig = "irBuscarEncuestaParticular";
 
           ((OCAPComponentesfqsForm)form).setDEstado("4");
         }
         if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
           sig = "irEvaluadosEprSacyl";
         if ((((OCAPComponentesfqsForm)form).getCodigo_id() != null) && (!((OCAPComponentesfqsForm)form).getCodigo_id().equals("")))
         {
           if (!((OCAPComponentesfqsForm)form).getCodigo_id().toUpperCase().startsWith("EPR")) {
             messages.add("codigo_id", new ActionMessage("errors.formaEpr", "Código"));
           }
           else if (!utilidades.esNumerico(((OCAPComponentesfqsForm)form).getCodigo_id().substring("EPR".length()))) {
             messages.add("codigo_id", new ActionMessage("errors.formaEpr", "Código"));
           }
 
         }
 
       }
 
       if ((messages == null) || (messages.isEmpty())) {
         long cCompfqsId = 0L;
         if (((jcylUsuario.getUser().getRol() != null) 
        		 && (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CE))
        		 && (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION)) 
        		 && (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR)) 
        		 && (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC))) || ((Constantes.OCAP_DIRECCION.equals(jcylUsuario.getUser().getRol())) 
        		 && ("cp".equals(request.getParameter("opcion")))))
         {
           String cCompfqsIdS = request.getParameter("cCompfqsIdS");
 
           if (cCompfqsIdS != null && !cCompfqsIdS.equals("")&&!cCompfqsIdS.trim().equals("null"))
           {
             cCompfqsId = Long.parseLong(cCompfqsIdS);
             OCAPConfigApp.logger.info("cCompfqsId: " + cCompfqsId);
           }
           else {
             OCAPComponentesfqsOT componente = new OCAPComponentesfqsOT();
 
             componente.setCDni(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
             oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
             Collection elementos = oCAPEvaluadoresLN.consultaOCAPEvaluadores(componente, "", 1, 1);
 
             if (!elementos.isEmpty()) {
               Object[] listadoelementos = elementos.toArray();
 
               cCompfqsId = ((OCAPComponentesfqsOT)listadoelementos[0]).getCCompfqsId();
 
               if (cCompfqsId == 0L) {
                 request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
 
                 return mapping.findForward("error");
               }
               listadoItinerarios = oCAPEvaluadoresLN.buscarOCAPConvocatoriasAct(cCompfqsId);
 
               if ((listadoItinerarios == null) || (listadoItinerarios.size() == 0))
               {
                 request.setAttribute("mensaje", "No tiene itinerarios asignados.");
 
                 return mapping.findForward("error");
               }
               Object[] listadoeval = listadoItinerarios.toArray();
 
               OCAPItinerariosLN oCAPItinerariosLN = new OCAPItinerariosLN(jcylUsuario);
 
               for (int i = 0; 
                 i < listadoItinerarios.size(); 
                 i++) {
                 ((OCAPComponentesfqsOT)listadoeval[i]).setDItinerarioNombre(oCAPItinerariosLN.buscarOCAPItinerarios(((OCAPComponentesfqsOT)listadoeval[i]).getCItinerarioId()).getDDescripcion());
               }
               listadoCategEspec = oCAPItinerariosLN.buscarOCAPCategEspecItinerarios(listadoItinerarios);
 
               session.setAttribute(Constantes.COMBO_ITINERARIOS, utilidades.ArrayObjectToArrayList(listadoeval));
 
               session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
 
               if (listadoCategEspec != null) {
                 session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategEspec);
               }
               else {
                 session.setAttribute(Constantes.COMBO_CATEGORIA, new ArrayList());
               }
             }
             else
             {
               request.setAttribute("mensaje", "No está dado de alta como evaluador");
 
               return mapping.findForward("error");
             }
           }
         }
         componentesOT.setDApellido1(((OCAPComponentesfqsForm)form).getDApellidos());
         componentesOT.setDNombre(((OCAPComponentesfqsForm)form).getDNombre());
         componentesOT.setCDni(((OCAPComponentesfqsForm)form).getCDni());
         componentesOT.setCCompfqsId(cCompfqsId);
         ((OCAPComponentesfqsForm)form).setCCompfqs_id(cCompfqsId);
 
         if ((((OCAPComponentesfqsForm)form).getCodigo_id() != null) && (!((OCAPComponentesfqsForm)form).getCodigo_id().equals("")))
         {
           String codigoEvaluado = ((OCAPComponentesfqsForm)form).getCodigo_id();
 
           codigoEvaluado = codigoEvaluado.substring("EPR".length());
 
           componentesOT.setCExpId(Long.valueOf(codigoEvaluado));
         }
 
         componentesOT.setCItinerarioId(((OCAPComponentesfqsForm)form).getCItinerario_id());
         componentesOT.setCProfesionalId(Integer.valueOf(String.valueOf(((OCAPComponentesfqsForm)form).getCProfesional_id())));
         componentesOT.setCEspecId(Integer.valueOf(String.valueOf(((OCAPComponentesfqsForm)form).getCEspec_id())));
         componentesOT.setDEstado(((OCAPComponentesfqsForm)form).getDEstado());
         componentesOT.setBContestado(((OCAPComponentesfqsForm)form).getBContestado());
         componentesOT.setCConvocatoriaId(((OCAPComponentesfqsForm)form).getCConvocatoria_id());
         componentesOT.setCGerenciaId((int)((OCAPComponentesfqsForm)form).getCGerenciaId());

 
         if (request.getParameter("eval") != null) {
           session.setAttribute("eval", request.getParameter("eval"));
 
           if (2 == Integer.parseInt(request.getParameter("eval")))
           {
             componentesOT.setBSegundaEvaluacion(Constantes.SI);
           }if (1 == Integer.parseInt(request.getParameter("eval")))
           {
             componentesOT.setBSegundaEvaluacion(Constantes.NO);
           } } else if ((jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL)) && (session.getAttribute("eval") != null))
         {
           if (2 == Integer.parseInt(session.getAttribute("eval").toString()))
           {
             componentesOT.setBSegundaEvaluacion(Constantes.SI);
           }if (1 == Integer.parseInt(session.getAttribute("eval").toString()))
           {
             componentesOT.setBSegundaEvaluacion(Constantes.NO);
           }
         }
         if ((request.getParameter("opcion") != null) && (request.getParameter("opcion").equals("CTE")))
         {
           request.setAttribute("cte", request.getParameter("cte"));
 
           request.setAttribute("codigo", request.getParameter("codigo"));
         }
 
         Collection elementos = null;
         if ("ENCUESTA".equals(request.getParameter("opcion"))) {
           elementos = oCAPEvaluadoresLN.listarEvaluadosEncuesta(componentesOT, jcylUsuario, primerRegistro, registrosPorPagina);
         }
         else
         {
           elementos = oCAPEvaluadoresLN.listarEvaluados(componentesOT, jcylUsuario, primerRegistro, registrosPorPagina, Integer.valueOf(String.valueOf(((OCAPComponentesfqsForm)form).getCConvocatoria_id())).intValue());
         }
 
         if (elementos != null) {
           OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Evaluadores para la consulta");
 
           if (elementos.size() == 0) {
             request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
 
             request.getSession().removeAttribute("listados");
           } else {
             if (!"ENCUESTA".equals(request.getParameter("opcion"))) {
               Object[] listadoelementos = elementos.toArray();
 
               OCAPCategProfesionalesLN categProfLN = new OCAPCategProfesionalesLN(jcylUsuario);
 
               OCAPCategProfesionalesOT categProfOT = new OCAPCategProfesionalesOT();
 
               int reg = elementos.size();
 
               elementos.removeAll(elementos);
 
               for (int i = 0; i < reg; i++) {
                 if (((OCAPUsuariosOT)listadoelementos[i]).getCProfesionalId().longValue() != 0L)
                 {
                   ((OCAPUsuariosOT)listadoelementos[i]).setDProfesional_nombre(categProfLN.buscarOCAPCategProfesionales(((OCAPUsuariosOT)listadoelementos[i]).getCProfesionalId().longValue()).getDNombre());
                 }
                 else ((OCAPUsuariosOT)listadoelementos[i]).setDProfesional_nombre("");
 
                 elementos.add(listadoelementos[i]);
               }
             }
             int nListado = 0;
             nListado = oCAPEvaluadoresLN.listarEvaluadosCuenta(componentesOT, jcylUsuario);
 
             session.setAttribute("numEvaluadores", new Integer(nListado));
 
             request.removeAttribute("sinDatos");
             Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
 
             pagina.setElementos(elementos);
             pagina.setRegistroActual(primerRegistro);
 
             pagina.setNRegistros(((Integer)session.getAttribute("numEvaluadores")).intValue());
             pagina.setRegistrosPorPagina(registrosPorPagina);
             request.setAttribute("listados", pagina);
             request.removeAttribute("menu");
             request.getSession().removeAttribute("menu");
           }
           if ((jcylUsuario.getUser().getRol() != null) && (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL)) && (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION)) && (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR)) && (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)))
           {
             request.setAttribute("nombre", request.getParameter("nombre"));
 
             request.setAttribute("apell", request.getParameter("apell"));
 
             sig = "irEvaluados";
           } else if ((Constantes.OCAP_DIRECCION.equals(jcylUsuario.getUser().getRol())) && ("cp".equals(request.getParameter("opcion"))))
           {
             request.setAttribute("nombre", request.getParameter("nombre"));
 
             request.setAttribute("apell", request.getParameter("apell"));
 
             sig = "irEvaluados";
           }
         } else {
           request.setAttribute("errorConsultando", "Error consultando en la base de datos");
         }
 
       }
 
       OCAPConfigApp.logger.info("...........se sale del Action");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: ERROR: " + e.getMessage());
 
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
     {
       return mapping.findForward(sig);
     }if ((errors != null) && (!errors.isEmpty())) {
       saveErrors(request, errors);
 
       return mapping.findForward("fallo");
     }
     saveMessages(request, messages);
     ((OCAPComponentesfqsForm)form).setjspInicio(true);
 
     return mapping.findForward(sig);
   }
 
   public ActionForward irEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     ActionErrors errors = new ActionErrors();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irEditar: Inicio");
 
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
       Utilidades utilidades = new Utilidades();
       request.setAttribute("opcion", request.getParameter("opcion"));
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
       OCAPComponentesfqsOT datos = new OCAPComponentesfqsOT();
       OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm)form;
 
       String cCompfqsIdS = request.getParameter("cCompfqsIdS");
       long cCompfqsId;
       if ((cCompfqsIdS != null) && (!cCompfqsIdS.equals(""))) {
         cCompfqsId = Long.parseLong(cCompfqsIdS);
         OCAPConfigApp.logger.info("cCompfqsId: " + cCompfqsId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
 
         return mapping.findForward("error");
       }
 
       datos = oCAPEvaluadoresLN.buscarOCAPEvaluadores(cCompfqsId);
 
       if (datos.getCCompfqsId() == 0L) {
         OCAPConfigApp.logger.info("No encontramos OCAPComponentesfqsOT");
         sig = "error";
         request.setAttribute("mensaje", "No se encuentra el registro");
       }
       else {
         OCAPConfigApp.logger.info("Se ha encontrado OCAPComponentesfqsOT");
         request.setAttribute("OCAPComponentesfqsOT", datos);
 
         formulario.setCCompfqs_id(datos.getCCompfqsId());
         formulario.setDNombre(datos.getDNombre());
         formulario.setDApellidos(datos.getDApellidos());
         formulario.setCDni(datos.getCDni());
         formulario.setBSexo(datos.getBSexo());
         formulario.setNTelefono1(datos.getNTelefono1());
         formulario.setACorreoelectronico(datos.getACorreoelectronico());
 
         if ((request.getParameter("opcion").equals("registro")) || ((request.getParameter("opcion").equals("cp")) && (request.getParameter("tarea").equals(Constantes.REGISTRAR))))
         {
           DecimalFormat formato = new DecimalFormat("000000");
 
           formulario.setCodigoEvaluador("REX" + formato.format(datos.getCCompfqsId()));
 
           formulario.setADir_nombre(datos.getADirNombre());
           formulario.setADir_num(datos.getADirNum());
           formulario.setADir_escalera(datos.getADirEscalera());
           formulario.setADir_piso(datos.getADirPiso());
           formulario.setADir_letra(datos.getADirLetra());
           formulario.setCCp(datos.getCCp());
           formulario.setCComunidad_id(datos.getCComunidadId());
           formulario.setCProv_id(datos.getCProvId());
           formulario.setCLocalidad_id(datos.getCLocalidadId());
 
           formulario.setATitulacion(datos.getATitulacion());
           formulario.setAEspecialidad(datos.getAEspecialidad());
           formulario.setACentrotrabajo(datos.getACentrotrabajo());
           formulario.setCGrado_id(datos.getCGradoId());
           formulario.setCComuni_id(datos.getCComuniId());
           formulario.setCProvincia_id(datos.getCProvinId());
           formulario.setAForm_acreditacion(datos.getAFormAcreditacion());
           formulario.setAForm_gestion(datos.getAFormGestion());
           formulario.setAForm_evaluacion(datos.getAFormEvaluacion());
           formulario.setAExpprof_ss(datos.getAExpprofSs());
           formulario.setAExpcal_asistencia(datos.getAExpcalAsistencia());
           formulario.setAAct_docente(datos.getAActDocente());
           formulario.setCConvocatoria_id(datos.getCConvocatoriaId());
 
           OCAPComunidadesLN comunidadesLN = new OCAPComunidadesLN(jcylUsuario);
 
           Collection listadoComu = comunidadesLN.listarComunidades();
 
           Object[] listadoComunidades = listadoComu.toArray();
 
           session.setAttribute(Constantes.COMBO_COMUNIDADES_PROF, utilidades.ArrayObjectToArrayList(listadoComunidades));
 
           session.setAttribute(Constantes.COMBO_COMUNIDADES, utilidades.ArrayObjectToArrayList(listadoComunidades));
 
           OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
 
           if ((formulario.getCComunidad_id() != null) && (!formulario.getCComunidad_id().equals("")))
           {
             Collection listadoProvProf = provinciasLN.listarProvinciasComunidad(formulario.getCComunidad_id());
 
             Object[] listadoProvinciasProf = listadoProvProf.toArray();
 
             session.setAttribute(Constantes.COMBO_PROVINCIAS, utilidades.ArrayObjectToArrayList(listadoProvinciasProf));
           }
           else {
             session.setAttribute(Constantes.COMBO_PROVINCIAS, new ArrayList());
           }
 
           if ((formulario.getCProv_id() != null) && (!formulario.getCProv_id().equals("")))
           {
             OCAPLocalidadesLN localidadesLN = new OCAPLocalidadesLN(jcylUsuario);
 
             Collection listadoLocal = localidadesLN.listarLocalidadesProvincia(formulario.getCProv_id());
 
             Object[] listadoLocalidades = listadoLocal.toArray();
 
             session.setAttribute(Constantes.COMBO_LOCALIDADES, utilidades.ArrayObjectToArrayList(listadoLocalidades));
           }
           else {
             session.setAttribute(Constantes.COMBO_LOCALIDADES, new ArrayList());
           }
 
           if ((formulario.getCComuni_id() != null) && (!formulario.getCComuni_id().equals("")))
           {
             Collection listadoProvProf = provinciasLN.listarProvinciasComunidad(formulario.getCComuni_id());
 
             Object[] listadoProvinciasProf = listadoProvProf.toArray();
 
             session.setAttribute(Constantes.COMBO_PROVINCIAS_PROF, utilidades.ArrayObjectToArrayList(listadoProvinciasProf));
           }
           else {
             session.setAttribute(Constantes.COMBO_PROVINCIAS_PROF, new ArrayList());
           }
 
           OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
           Collection listadoGra = gradoLN.listadoOCAPGrado();
           Object[] listadoGrado = listadoGra.toArray();
           session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, utilidades.ArrayObjectToArrayList(listadoGrado));
         }
 
         if (request.getParameter("opcion").equals("cp")) {
           request.setAttribute("tarea", request.getParameter("tarea"));
 
           formulario = obtenerConvocatorias(form, request);
 
           OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
 
           OCAPGestionCtesLN ctesLN = new OCAPGestionCtesLN(jcylUsuario);
 
           if (request.getParameter("tarea").equals(Constantes.ACTIVAR)) {
             Collection listadoConv = convocatoriasLN.listarConvocatoriasActivas();
 
             Object[] listadoConvocatorias = listadoConv.toArray();
 
             session.setAttribute(Constantes.COMBO_CONVOCATORIAS, utilidades.ArrayObjectToArrayList(listadoConvocatorias));
 
             OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
 
             Collection listadoGra = gradoLN.listadoOCAPGrado();
 
             Object[] listadoGrado = listadoGra.toArray();
 
             session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, utilidades.ArrayObjectToArrayList(listadoGrado));
 
             session.setAttribute(Constantes.COMBO_CATEGORIA, new ArrayList());
 
             session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
 
             session.setAttribute(Constantes.COMBO_ITINERARIOS, new ArrayList());
 
             Collection listadoCte = ctesLN.consultaOCAPCtesAct();
 
             Object[] listadoCtes = listadoCte.toArray();
             session.setAttribute(Constantes.COMBO_CTES, utilidades.ArrayObjectToArrayList(listadoCtes));
 
             request.setAttribute("tarea", Constantes.ACTIVAR);
           }
 
         }
 
         OCAPConfigApp.logger.info("Se ha encontrado OCAPEvaluadores");
         sig = "irModificar";
         if (Constantes.OCAP_CE.equals(jcylUsuario.getUser().getRol())) {
           request.setAttribute("tipoAccion", Constantes.VER_DETALLE);
         }
         else
           request.setAttribute("tipoAccion", Constantes.MODIFICAR);
       }
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: ERROR: " + e.getMessage());
 
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward(sig);
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward grabar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     ActionMessages messages = new ActionMessages();
     ActionErrors errors = new ActionErrors();
     try
     {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- GRABAR Evaluador --------- ");
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
       OCAPComponentesfqsOT datos = new OCAPComponentesfqsOT();
       OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm)form;
 
       if (formulario.getCCompfqs_id() != 0L) {
         OCAPConfigApp.logger.info("cCompfqsId: " + formulario.getCCompfqs_id());
       }
       else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
 
         return mapping.findForward("error");
       }
 
       datos.setCCompfqsId(formulario.getCCompfqs_id());
       OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
 
       if ((request.getParameter("opcion").equals("registro")) || ((request.getParameter("opcion").equals("cp")) && (request.getParameter("tarea").equals(Constantes.REGISTRAR))))
       {
         messages = validarEvaluadorReg(form, request);
       }
       else messages = validarEvaluadorCp(form, request);
 
       if ((messages == null) || (messages.isEmpty())) {
         if ((request.getParameter("opcion").equals("registro")) || ((request.getParameter("opcion").equals("cp")) && (request.getParameter("tarea").equals(Constantes.REGISTRAR))))
         {
           datos.setDNombre(formulario.getDNombre());
           datos.setDApellidos(formulario.getDApellidos());
           datos.setCDni(formulario.getCDni());
 
           if ((formulario.getBSexo() != null) && (!formulario.getBSexo().equals("")))
           {
             datos.setBSexo(formulario.getBSexo());
           }
 
           if ((formulario.getNTelefono1() != null) && (!formulario.getNTelefono1().equals("")))
           {
             datos.setNTelefono1(formulario.getNTelefono1());
           }
 
           if ((formulario.getACorreoelectronico() != null) && (!formulario.getACorreoelectronico().equals("")))
           {
             datos.setACorreoelectronico(formulario.getACorreoelectronico());
           }
 
           if ((formulario.getADir_nombre() != null) && (!formulario.getADir_nombre().equals("")))
           {
             datos.setADirNombre(formulario.getADir_nombre());
           }
 
           if ((formulario.getADir_num() != null) && (!formulario.getADir_num().equals("")))
           {
             datos.setADirNum(formulario.getADir_num());
           }
 
           if ((formulario.getADir_escalera() != null) && (!formulario.getADir_escalera().equals("")))
           {
             datos.setADirEscalera(formulario.getADir_escalera());
           }
 
           if ((formulario.getADir_piso() != null) && (!formulario.getADir_piso().equals("")))
           {
             datos.setADirPiso(formulario.getADir_piso());
           }
 
           if ((formulario.getADir_letra() != null) && (!formulario.getADir_letra().equals("")))
           {
             datos.setADirLetra(formulario.getADir_letra());
           }
 
           if ((formulario.getCCp() != null) && (!formulario.getCCp().equals("")))
           {
             datos.setCCp(formulario.getCCp());
           }
 
           if ((formulario.getCComunidad_id() != null) && (!formulario.getCComunidad_id().equals("")))
           {
             datos.setCComunidadId(formulario.getCComunidad_id());
           }
 
           if ((formulario.getCProv_id() != null) && (!formulario.getCProv_id().equals("")))
           {
             datos.setCProvId(formulario.getCProv_id());
           }
 
           if ((formulario.getCLocalidad_id() != null) && (!formulario.getCLocalidad_id().equals("")))
           {
             datos.setCLocalidadId(formulario.getCLocalidad_id());
           }
 
           if ((formulario.getATitulacion() != null) && (!formulario.getATitulacion().equals("")))
           {
             datos.setATitulacion(formulario.getATitulacion());
           }
 
           if ((formulario.getAEspecialidad() != null) && (!formulario.getAEspecialidad().equals("")))
           {
             datos.setAEspecialidad(formulario.getAEspecialidad());
           }
 
           if ((formulario.getACentrotrabajo() != null) && (!formulario.getACentrotrabajo().equals("")))
           {
             datos.setACentrotrabajo(formulario.getACentrotrabajo());
           }
 
           if (formulario.getCGrado_id() != 0L) {
             datos.setCGradoId(formulario.getCGrado_id());
           }
 
           if ((formulario.getCComuni_id() != null) && (!formulario.getCComuni_id().equals("")))
           {
             datos.setCComuniId(formulario.getCComuni_id());
           }
 
           if ((formulario.getCProvincia_id() != null) && (!formulario.getCProvincia_id().equals("")))
           {
             datos.setCProvinId(formulario.getCProvincia_id());
           }
 
           if ((formulario.getAForm_acreditacion() != null) && (!formulario.getAForm_acreditacion().equals("")))
           {
             datos.setAFormAcreditacion(formulario.getAForm_acreditacion());
           }
 
           if ((formulario.getAForm_gestion() != null) && (!formulario.getAForm_gestion().equals("")))
           {
             datos.setAFormGestion(formulario.getAForm_gestion());
           }
 
           if ((formulario.getAForm_evaluacion() != null) && (!formulario.getAForm_evaluacion().equals("")))
           {
             datos.setAFormEvaluacion(formulario.getAForm_evaluacion());
           }
 
           if ((formulario.getAExpprof_ss() != null) && (!formulario.getAExpprof_ss().equals("")))
           {
             datos.setAExpprofSs(formulario.getAExpprof_ss());
           }
 
           if ((formulario.getAExpcal_asistencia() != null) && (!formulario.getAExpcal_asistencia().equals("")))
           {
             datos.setAExpcalAsistencia(formulario.getAExpcal_asistencia());
           }
 
           if ((formulario.getAAct_docente() != null) && (!formulario.getAAct_docente().equals("")))
           {
             datos.setAActDocente(formulario.getAAct_docente());
           }
 
           datos.setCConvocatoriaId(formulario.getCConvocatoria_id());
         } else {
           datos.setCCompfqsId(formulario.getCCompfqs_id());
           datos.setCConvocatoriaId(formulario.getCConvocatoria_id());
           datos.setCProfesionalId(formulario.getCProfesional_id());
           datos.setCEspecId(formulario.getCEspec_id());
           datos.setCItinerarioId(formulario.getCItinerario_id());
           datos.setCCteId(formulario.getCCte_id());
         }
 
         datos.setAModificadoPorComponente(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
         datos.setAModificadoPorExp(datos.getAModificadoPorComponente());
 
         int result = oCAPEvaluadoresLN.modificacionOCAPEvaluadores(datos);
 
         request.setAttribute("opcion", request.getParameter("opcion"));
 
         if (result == 1) {
           request.setAttribute("mensaje", "El registro se ha modificado con éxito");
 
           request.setAttribute("rutaVuelta", "OCAPEvaluadores.do?accion=buscar&recuperarBusquedaAnterior=Y&opcion=" + request.getParameter("opcion"));
 
           sig = "exito";
         } else {
           request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
 
           sig = "error";
         }
         OCAPConfigApp.logger.info("---------- FIN GRABAR MODIFICACION Evaluadores --------- ");
       }
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(ex, "error.general")));
     }
 
     if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
     {
       return mapping.findForward("exito");
     }if ((errors != null) && (!errors.isEmpty())) {
       saveErrors(request, errors);
 
       return mapping.findForward("fallo");
     }
     saveMessages(request, messages);
 
     request.setAttribute("tipoAccion", Constantes.MODIFICAR);
     request.setAttribute("opcion", request.getParameter("opcion"));
 
     request.setAttribute("tarea", request.getParameter("tarea"));
 
     return mapping.findForward("irModificar");
   }
 
   public ActionMessages validarEvaluadorReg(ActionForm form, HttpServletRequest request)
     throws Exception
   {
     ActionMessages messages = new ActionMessages();
     Utilidades utilidades = new Utilidades();
     HttpSession session = request.getSession();
     try
     {
       if ((((OCAPComponentesfqsForm)form).getDApellidos() == null) || (((OCAPComponentesfqsForm)form).getDApellidos().equals("")))
       {
         messages.add("dApellidos", new ActionMessage("errors.required", "Apellidos"));
       }
 
       if ((((OCAPComponentesfqsForm)form).getDNombre() == null) || (((OCAPComponentesfqsForm)form).getDNombre().equals("")))
       {
         messages.add("dNombre", new ActionMessage("errors.required", "Nombre"));
       }
 
       if ((((OCAPComponentesfqsForm)form).getCDni() == null) || (((OCAPComponentesfqsForm)form).getCDni().equals("")))
       {
         messages.add("cDni", new ActionMessage("errors.required", "NIF/NIE"));
       }
       else if (!Utilidades.esDNI(((OCAPComponentesfqsForm)form).getCDni())) {
         messages.add("cDni", new ActionMessage("error.maskmsg", "NIF/NIE"));
       }
 
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.info(getClass().getName() + " validarEvaluadorReg: ERROR: " + e.getMessage());
 
       throw e;
     }
 
     return messages;
   }
 
   public ActionMessages validarEvaluadorCp(ActionForm form, HttpServletRequest request)
     throws Exception
   {
     ActionMessages messages = new ActionMessages();
     Utilidades utilidades = new Utilidades();
     HttpSession session = request.getSession();
     try
     {
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       if (((OCAPComponentesfqsForm)form).getCConvocatoria_id() == 0L)
       {
         messages.add("CConvocatoriaId", new ActionMessage("errors.required", "Convocatoria"));
       }
 
       if (((OCAPComponentesfqsForm)form).getCGrado_id() == 0L) {
         messages.add("CGradoId", new ActionMessage("errors.required", "Grado"));
       }
       else if (((OCAPComponentesfqsForm)form).getCProfesional_id() == 0L)
       {
         messages.add("dCategoria", new ActionMessage("errors.required", "Categoría"));
       }
       else
       {
         OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
 
         Collection listadoEspecialidades = especialidadesLN.listarEspecialidades(((OCAPComponentesfqsForm)form).getCProfesional_id());
 
         if (listadoEspecialidades.size() != 0) {
           if (((OCAPComponentesfqsForm)form).getCEspec_id() == 0L)
           {
             messages.add("cEspec", new ActionMessage("errors.required", "Especialidad"));
           }
           else if (((OCAPComponentesfqsForm)form).getCItinerario_id() == 0L)
           {
             messages.add("cItinerario", new ActionMessage("errors.required", "Manual"));
           }
 
         }
         else if (((OCAPComponentesfqsForm)form).getCItinerario_id() == 0L)
         {
           messages.add("cItinerario", new ActionMessage("errors.required", "Manual"));
         }
 
       }
 
       if (((OCAPComponentesfqsForm)form).getCCte_id() == 0L) {
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
 
   public ActionForward irListar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errors = new ActionErrors();
     HttpSession session = request.getSession();
     Utilidades utilidades = new Utilidades();
     ActionMessages messages = new ActionMessages();
     ArrayList listadoItinerarios = new ArrayList();
     ArrayList listadoCategEspec = new ArrayList();
	ArrayList listadoGerencias = new ArrayList();

     OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm)form;
 
     String sig = "irEvaluadosEpr";
     String mensajeError = "Se ha producido un error";
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " irListar: Inicio");
 
       validarAccion(mapping, form, request, response);
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
       OCAPComponentesfqsOT componente = new OCAPComponentesfqsOT();
 
       formulario.setjspInicio(true);
 
       if ((jcylUsuario.getUser().getRol() != null) && (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION)) && (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR)) && (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC)))
       {
         if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_EVAL))
         {
           OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
 
           ArrayList listadoConvocatorias = convocatoriasLN.listarConvocatorias();
 
           if (session.getAttribute(Constantes.COMBO_CONVOCATORIAS) != null)
           {
             session.removeAttribute(Constantes.COMBO_CONVOCATORIAS);
           }session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);
         }
         else {
           session.setAttribute(Constantes.COMBO_CONVOCATORIAS, new ArrayList());
         }
 
         OCAPItinerariosLN oCAPItinerariosLN = new OCAPItinerariosLN(jcylUsuario);
 
         componente.setCDni(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
 
         Collection elementos = oCAPEvaluadoresLN.consultaOCAPEvaluadores(componente, "", 1, 1);
 
         if (!elementos.isEmpty()) {
           Object[] listadoelementos = elementos.toArray();
 
           formulario.setCCompfqs_id(((OCAPComponentesfqsOT)listadoelementos[0]).getCCompfqsId());
 
           listadoItinerarios = oCAPEvaluadoresLN.buscarOCAPConvocatoriasAct(formulario.getCCompfqs_id());
 
           Object[] listadoeval = listadoItinerarios.toArray();
 
           for (int i = 0; i < listadoItinerarios.size(); 
             i++) {
             ((OCAPComponentesfqsOT)listadoeval[i]).setDItinerarioNombre(oCAPItinerariosLN.buscarOCAPItinerarios(((OCAPComponentesfqsOT)listadoeval[i]).getCItinerarioId()).getDDescripcion());
           }
 
           if (listadoItinerarios.size() != 0) {
             listadoCategEspec = oCAPItinerariosLN.buscarOCAPCategEspecItinerarios(listadoItinerarios);
           }
           else {
             messages.add("cCompfqsId", new ActionMessage("Evaluador no activo"));
 
             mensajeError = "Evaluador no activo.";
           }
 
           session.setAttribute(Constantes.COMBO_ITINERARIOS, utilidades.ArrayObjectToArrayList(listadoeval));
 
           session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
 
           if (request.getParameter("eval") != null)
             session.setAttribute("eval", request.getParameter("eval"));
         }
         else {
           messages.add("cCompfqsId", new ActionMessage("errors.evaluadorNoExiste"));
 
           mensajeError = "Evaluador no dado de alta.";
         }
       } else {
         OCAPCategProfesionalesLN categorias = new OCAPCategProfesionalesLN(jcylUsuario);
         

		OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
		listadoGerencias = gerenciasLN.listadoOCAPGerencias();
		session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);
         
         listadoCategEspec = categorias.listadoOCAPCategProfesionales(-1, -1);
 
         session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
 
         Collection listadoEst = oCAPEvaluadoresLN.listadoEstados();
 
         Object[] listadoEstados = listadoEst.toArray();
 
         session.setAttribute(Constantes.COMBO_ESTADOS, listadoEstados);
 
         OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
 
         ArrayList listadoConvocatorias = convocatoriasLN.listarConvocatorias();
 
         if (session.getAttribute(Constantes.COMBO_CONVOCATORIAS) != null)
         {
           session.removeAttribute(Constantes.COMBO_CONVOCATORIAS);
         }session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);
       }
 
       if (listadoCategEspec != null) {
         session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategEspec);
       }
       else {
         session.setAttribute(Constantes.COMBO_CATEGORIA, new ArrayList());
       }
 
       if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {
         sig = "irEvaluadosEprSacyl";
       }
       session.setAttribute("iRegistro", Integer.toString(1));
 
       OCAPConfigApp.logger.info(getClass().getName() + " irListar: Fin");
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irListar: ERROR: " + e.getMessage());
 
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
     {
       return mapping.findForward(sig);
     }if ((errors != null) && (!errors.isEmpty())) {
       saveErrors(request, errors);
 
       return mapping.findForward("fallo");
     }
     saveMessages(request, messages);
     request.setAttribute("mensaje", mensajeError);
 
     return mapping.findForward("error");
   }
 
   public ActionForward irListarEncuesta(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errors = new ActionErrors();
     HttpSession session = request.getSession();
     Utilidades utilidades = new Utilidades();
     ActionMessages messages = new ActionMessages();
     ArrayList listadoItinerarios = new ArrayList();
     ArrayList listadoCategEspec = new ArrayList();
     OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm)form;
 
     String sig = "irBuscarEncuestaParticular";
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " irListarEncuesta: Inicio");
 
       validarAccion(mapping, form, request, response);
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
       OCAPComponentesfqsOT componente = new OCAPComponentesfqsOT();
 
       formulario.setjspInicio(true);
 
       OCAPItinerariosLN oCAPItinerariosLN = new OCAPItinerariosLN(jcylUsuario);
 
       listadoItinerarios = oCAPItinerariosLN.listadoItinerarios();
 
       session.setAttribute(Constantes.COMBO_ITINERARIOS, listadoItinerarios);
 
       session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
 
       OCAPCategProfesionalesLN categorias = new OCAPCategProfesionalesLN(jcylUsuario);
 
       listadoCategEspec = categorias.listadoOCAPCategProfesionales(-1, -1);
 
       if (listadoCategEspec != null) {
         session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategEspec);
       }
       else {
         session.setAttribute(Constantes.COMBO_CATEGORIA, new ArrayList());
       }
 
       session.setAttribute("iRegistro", Integer.toString(1));
 
       OCAPConfigApp.logger.info(getClass().getName() + " irListarEncuesta: Fin");
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irListarEncuesta: ERROR: " + e.getMessage());
 
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
     {
       return mapping.findForward(sig);
     }if ((errors != null) && (!errors.isEmpty())) {
       saveErrors(request, errors);
 
       return mapping.findForward("fallo");
     }
     saveMessages(request, messages);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward asignarConvocatoria(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     ActionMessages messages = new ActionMessages();
     ActionErrors errors = new ActionErrors();
     OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm)form;
     try
     {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- ALTA CONVOCATORIA Y EVALUADOS --------- ");
       validarAccion(mapping, form, request, response);
 
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       if (((OCAPComponentesfqsForm)form).getCCompfqs_id() == 0L) {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
 
         return mapping.findForward("error");
       }
 
       messages = validarEvaluadorCp(form, request);
 
       if ((messages == null) || (messages.isEmpty())) {
         OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
         OCAPComponentesfqsOT datos = new OCAPComponentesfqsOT();
 
         OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
 
         datos.setCCompfqsId(formulario.getCCompfqs_id());
         datos.setCConvocatoriaId(formulario.getCConvocatoria_id());
         datos.setCProfesionalId(formulario.getCProfesional_id());
         datos.setCEspecId(formulario.getCEspec_id());
         datos.setCItinerarioId(formulario.getCItinerario_id());
         datos.setCCteId(formulario.getCCte_id());
         datos.setCGradoId(formulario.getCGrado_id());
 
         datos.setACreadoPorExp(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
 
         OCAPGerenciasLN gerenciaLN = new OCAPGerenciasLN(jcylUsuario);
 
         OCAPGerenciasOT gerencia = gerenciaLN.buscarOCAPGerenciasCodLDAP(jcylUsuario.getUsuario().getD_gerencia());
 
         int result = oCAPEvaluadoresLN.asignarConvocatoriaYEvaluados(datos, gerencia.getCGerenciaId(), jcylUsuario.getUsuario().getC_usr_id());
 
         if (result == 1) {
           request.setAttribute("mensaje", "El registro se ha modificado con éxito en el sistema y se han asociado evaluados ");
 
           request.setAttribute("rutaVuelta", "OCAPEvaluadores.do?accion=buscar&opcion=cp&recuperarBusquedaAnterior=Y");
 
           sig = "exito";
         } else {
           request.setAttribute("mensaje", "Se ha producido un error al insertar el registro y  asociar evaluados");
 
           sig = "error";
         }
 
         OCAPConfigApp.logger.info("---------- FIN asignarConvocatoria--------- ");
       }
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       if (ex.getMessage().startsWith("ORA-00001")) {
         messages.add("Evaluador", new ActionMessage("errors.compFqsconvo"));
       }
       else {
         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(ex, "error.general")));
       }
 
     }
 
     if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
     {
       return mapping.findForward("exito");
     }if ((errors != null) && (!errors.isEmpty())) {
       saveErrors(request, errors);
 
       return mapping.findForward("fallo");
     }
     try
     {
       formulario = obtenerConvocatorias(form, request);
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(ex, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty())) {
       saveMessages(request, messages);
       request.setAttribute("tipoAccion", Constantes.MODIFICAR);
 
       request.setAttribute("opcion", "cp");
       request.setAttribute("tarea", Constantes.ACTIVAR);
 
       return mapping.findForward("irModificar");
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward cargarComboCategorias(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     HttpSession session = request.getSession();
     ActionErrors errors = new ActionErrors();
     ArrayList listadoCategorias = new ArrayList();
     ArrayList listadoConv = new ArrayList();
     ArrayList listadoAct = new ArrayList();
     String sig = "error";
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboCategorias: Inicio");
 
       validarAccion(mapping, form, request, response);
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
 
       if (((OCAPComponentesfqsForm)form).getCGrado_id() != 0L) {
         listadoCategorias = categProfesionalesLN.consultaOCAPCategProfesionales(0L, null, 0L, 0L, null, null, null, 0L, 0, 0);
       }
 
       session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
 
       session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
 
       session.setAttribute(Constantes.COMBO_ITINERARIOS, new ArrayList());
 
       String tipo = request.getParameter("tipo");
 
       if (tipo.equals(Constantes.ACTIVAR)) {
         if (((OCAPComponentesfqsForm)form).getResumenConv() != null)
         {
           listadoConv = obtenerHistorial(((OCAPComponentesfqsForm)form).getResumenConv());
         }
 
         if (((OCAPComponentesfqsForm)form).getResumenAct() != null)
         {
           listadoAct = obtenerHistorial(((OCAPComponentesfqsForm)form).getResumenAct());
         }
 
         ((OCAPComponentesfqsForm)form).setListadoConv(listadoConv);
         ((OCAPComponentesfqsForm)form).setListadoAct(listadoAct);
 
         request.setAttribute("tipoAccion", Constantes.MODIFICAR);
 
         request.setAttribute("tarea", Constantes.ACTIVAR);
         sig = "irInsertar";
       }
       else
       {
         request.setAttribute("tarea", Constantes.MODIFICAR);
         sig = "irModificarConv";
       }
 
       request.setAttribute("opcion", "cp");
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboCategorias: Fin");
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboCategorias: ERROR: " + e.getMessage());
 
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward(sig);
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward cargarComboEspecialidades(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     HttpSession session = request.getSession();
     ActionErrors errors = new ActionErrors();
     ArrayList listadoEspecialidades = new ArrayList();
     ArrayList listadoConv = new ArrayList();
     ArrayList listadoAct = new ArrayList();
     ArrayList listadoItinerarios = new ArrayList();
     String sig = "error";
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboEspecialidades: Inicio");
 
       validarAccion(mapping, form, request, response);
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
 
       String tipo = request.getParameter("tipo");
 
       if (((OCAPComponentesfqsForm)form).getCProfesional_id() != 0L)
       {
         listadoEspecialidades = especialidadesLN.listarEspecialidades(((OCAPComponentesfqsForm)form).getCProfesional_id());
 
         session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
 
         if (!tipo.equals(Constantes.CONSULTAR))
           session.setAttribute(Constantes.COMBO_ITINERARIOS, new ArrayList());
       }
       else
       {
         session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
 
         session.setAttribute(Constantes.COMBO_ITINERARIOS, new ArrayList());
       }
 
       if (!tipo.equals(Constantes.CONSULTAR)) {
         if ((listadoEspecialidades.size() == 0) && (((OCAPComponentesfqsForm)form).getCProfesional_id() != 0L))
         {
           cargarComboItinerarios(mapping, form, request, response);
         }
 
         if (tipo.equals(Constantes.ACTIVAR)) {
           if (((OCAPComponentesfqsForm)form).getResumenConv() != null)
           {
             listadoConv = obtenerHistorial(((OCAPComponentesfqsForm)form).getResumenConv());
           }
 
           if (((OCAPComponentesfqsForm)form).getResumenAct() != null)
           {
             listadoAct = obtenerHistorial(((OCAPComponentesfqsForm)form).getResumenAct());
           }
 
           ((OCAPComponentesfqsForm)form).setListadoConv(listadoConv);
           ((OCAPComponentesfqsForm)form).setListadoAct(listadoAct);
 
           request.setAttribute("tipoAccion", Constantes.MODIFICAR);
 
           request.setAttribute("tarea", Constantes.ACTIVAR);
           sig = "irInsertar";
         } else {
           request.setAttribute("tarea", Constantes.MODIFICAR);
 
           sig = "irModificarConv";
         }
 
         request.setAttribute("opcion", "cp");
       } else {
         ((OCAPComponentesfqsForm)form).setjspInicio(true);
         if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
           sig = "irEvaluadosEprSacyl";
         else if ("ENCUESTA".equals(request.getParameter("opcion")))
           sig = "irBuscarEncuestaParticular";
         else {
           sig = "irEvaluadosEpr";
         }
       }
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboEspecialidades: Fin");
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboEspecialidades: ERROR: " + e.getMessage());
 
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward(sig);
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward cargarComboItinerarios(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     HttpSession session = request.getSession();
     ActionErrors errors = new ActionErrors();
     ArrayList listadoItinerarios = new ArrayList();
     ArrayList listadoConv = new ArrayList();
     ArrayList listadoAct = new ArrayList();
     String sig = "error";
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboItinerarios: Inicio");
 
       validarAccion(mapping, form, request, response);
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPItinerariosLN itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
 
       OCAPCategProfesionalesLN oCAPCategProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
 
       listadoItinerarios = itinerariosLN.listarItinerarios(((OCAPComponentesfqsForm)form).getCGrado_id(), ((OCAPComponentesfqsForm)form).getCProfesional_id(), ((OCAPComponentesfqsForm)form).getCEspec_id(), 0L);
 
       session.setAttribute(Constantes.COMBO_ITINERARIOS, listadoItinerarios);
 
       String tipo = request.getParameter("tipo");
 
       if (tipo.equals(Constantes.ACTIVAR)) {
         if (((OCAPComponentesfqsForm)form).getResumenConv() != null)
         {
           listadoConv = obtenerHistorial(((OCAPComponentesfqsForm)form).getResumenConv());
         }
 
         if (((OCAPComponentesfqsForm)form).getResumenAct() != null)
         {
           listadoAct = obtenerHistorial(((OCAPComponentesfqsForm)form).getResumenAct());
         }
 
         ((OCAPComponentesfqsForm)form).setListadoConv(listadoConv);
         ((OCAPComponentesfqsForm)form).setListadoAct(listadoAct);
 
         request.setAttribute("tipoAccion", Constantes.MODIFICAR);
 
         request.setAttribute("tarea", Constantes.ACTIVAR);
         sig = "irInsertar";
       } else {
         request.setAttribute("tarea", Constantes.MODIFICAR);
         sig = "irModificarConv";
       }
 
       request.setAttribute("opcion", "cp");
 
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboItinerarios: Fin");
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboItinerarios: ERROR: " + e.getMessage());
 
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward(sig);
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ArrayList obtenerHistorial(String historial)
     throws Exception
   {
     ArrayList cadenasConv = new ArrayList();
     ArrayList listaConv = new ArrayList();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " obtenerHistorial: Inicio");
 
       cadenasConv = Cadenas.obtenerArrayListTokens(historial, "#");
 
       OCAPComponentesfqsOT componentesOT = null;
 
       for (int i = 0; i < cadenasConv.size(); i++) {
         ArrayList campos = new ArrayList();
         String cadena = (String)cadenasConv.get(i);
         StringTokenizer token = new StringTokenizer(cadena, "$");
 
         while (token.hasMoreTokens()) {
           campos.add(token.nextToken());
         }
 
         componentesOT = new OCAPComponentesfqsOT();
         componentesOT.setDConvocNombre((String)campos.get(0));
         componentesOT.setDProfesionalNombre((String)campos.get(1));
         componentesOT.setDEspecNombre((String)campos.get(2));
         componentesOT.setDItinerarioNombre((String)campos.get(3));
         componentesOT.setDCteNombre((String)campos.get(4));
         listaConv.add(componentesOT);
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " obtenerHistorial: Fin");
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return listaConv;
   }
 
   public ActionForward irModificarConv(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errors = new ActionErrors();
     String sig = "error";
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irModificarConv: Inicio");
 
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
       Utilidades utilidades = new Utilidades();
       request.setAttribute("opcion", request.getParameter("opcion"));
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
       OCAPComponentesfqsOT datos = new OCAPComponentesfqsOT();
       OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm)form;
 
       String cConvoIdS = request.getParameter("cConvoIdS");
       long cConvoId;
       if ((cConvoIdS != null) && (!cConvoIdS.equals(""))) {
         cConvoId = Long.parseLong(cConvoIdS);
         OCAPConfigApp.logger.info("cCompfqsId: " + cConvoIdS);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
 
         return mapping.findForward("error");
       }
 
       datos = oCAPEvaluadoresLN.buscarOCAPConvEvaluador(cConvoId);
 
       if (datos.getCConvocatoriaId() == 0L) {
         OCAPConfigApp.logger.info("No encontramos OCAPComponentesfqsOT");
         sig = "error";
         request.setAttribute("mensaje", "No se encuentra el registro");
       }
       else {
         formulario.setCCompfqs_convo_id(cConvoId);
         formulario.setCCompfqs_id(datos.getCCompfqsId());
         formulario.setCConvocatoria_id(datos.getCConvocatoriaId());
         formulario.setCGrado_id(datos.getCGradoId());
 
         OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
         Collection listadoGra = gradoLN.listadoOCAPGrado();
         Object[] listadoGrado = listadoGra.toArray();
         session.setAttribute(Constantes.COMBO_GRADOS_CONSULTA, utilidades.ArrayObjectToArrayList(listadoGrado));
 
         if (datos.getCProfesionalId() != 0L) {
           formulario.setCProfesional_id(datos.getCProfesionalId());
 
           ArrayList listadoCategorias = new ArrayList();
           OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
 
           listadoCategorias = categProfesionalesLN.consultaOCAPCategProfesionales(0L, null, 0L, 0L, null, null, null, 0L, 0, 0);
 
           session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
         }
 
         if (datos.getCEspecId() != 0L) {
           formulario.setCEspec_id(datos.getCEspecId());
 
           ArrayList listadoEspecialidades = new ArrayList();
           OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
 
           listadoEspecialidades = especialidadesLN.listarEspecialidades(((OCAPComponentesfqsForm)form).getCProfesional_id());
 
           session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
         }
         else {
           session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
         }
 
         if (datos.getCItinerarioId() != 0L) {
           formulario.setCItinerario_id(datos.getCItinerarioId());
 
           ArrayList listadoItinerarios = new ArrayList();
           OCAPItinerariosLN itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
 
           OCAPConvocatoriasLN oCAPConvocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
 
           OCAPCategProfesionalesLN oCAPCategProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
 
           long estatut = oCAPCategProfesionalesLN.buscarOCAPCategProfesionales(((OCAPComponentesfqsForm)form).getCProfesional_id()).getCEstatutId();
 
           listadoItinerarios = itinerariosLN.listarItinerarios(datos.getCGradoId(), ((OCAPComponentesfqsForm)form).getCProfesional_id(), ((OCAPComponentesfqsForm)form).getCEspec_id(), 0L);
 
           session.setAttribute(Constantes.COMBO_ITINERARIOS, listadoItinerarios);
         }
 
         if (datos.getCCteId() != 0L) {
           formulario.setCCte_id(datos.getCCteId());
         }
 
         request.setAttribute("opcion", request.getParameter("opcion"));
 
         sig = "irModificarConv";
 
         OCAPConfigApp.logger.info("Se ha encontrado OCAPEvaluadores");
       }
       OCAPConfigApp.logger.info("...........se sale del Action");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: ERROR: " + e.getMessage());
 
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward(sig);
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward modificarConv(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionMessages messages = new ActionMessages();
     ActionErrors errors = new ActionErrors();
     String sig = "error";
     try
     {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- modificarConv --------- ");
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
       OCAPComponentesfqsOT datos = new OCAPComponentesfqsOT();
       OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm)form;
 
       messages = validarEvaluadorCp(form, request);
 
       if ((messages == null) || (messages.isEmpty())) {
         datos.setCCompfqsConvoId(formulario.getCCompfqs_convo_id());
         datos.setCCompfqsId(formulario.getCCompfqs_id());
         datos.setCConvocatoriaId(formulario.getCConvocatoria_id());
         datos.setCProfesionalId(formulario.getCProfesional_id());
         datos.setCEspecId(formulario.getCEspec_id());
         datos.setCItinerarioId(formulario.getCItinerario_id());
         datos.setCCteId(formulario.getCCte_id());
         datos.setAModificadoPorComponente(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
 
         int result = oCAPEvaluadoresLN.modificacionOCAPConvocatoria(datos);
 
         if (result == 1) {
           request.setAttribute("mensaje", "El registro se ha modificado con éxito");
 
           request.setAttribute("rutaVuelta", "OCAPEvaluadores.do?accion=irEditar&cCompfqsIdS=" + datos.getCCompfqsId() + "&opcion=" + request.getParameter("opcion") + "&tarea=" + request.getParameter("tarea"));
 
           sig = "exito";
         } else {
           request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
 
           sig = "error";
         }
         OCAPConfigApp.logger.info("---------- FIN GRABAR modificarConv --------- ");
       }
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(ex, "error.general")));
     }
 
     if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
     {
       return mapping.findForward(sig);
     }if ((errors != null) && (!errors.isEmpty())) {
       saveErrors(request, errors);
 
       return mapping.findForward("fallo");
     }
     saveMessages(request, messages);
 
     return mapping.findForward("irModificarConv");
   }
 
   public OCAPComponentesfqsForm obtenerConvocatorias(ActionForm form, HttpServletRequest request)
     throws Exception
   {
     HttpSession session = request.getSession();
 
     JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
     OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
     OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm)form;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " obtenerConvocatorias: Inicio");
 
       ArrayList antiguas = oCAPEvaluadoresLN.buscarOCAPConvAnt(formulario.getCCompfqs_id());
 
       ArrayList histConv = null;
       histConv = new ArrayList();
 
       OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
 
       OCAPGestionCtesLN ctesLN = new OCAPGestionCtesLN(jcylUsuario);
 
       for (int j = 0; j < antiguas.size(); j++) {
         OCAPComponentesfqsOT componentesOT = new OCAPComponentesfqsOT();
 
         if (((OCAPComponentesfqsOT)antiguas.get(j)).getCConvocatoriaId() != 0L)
         {
           OCAPConvocatoriasOT convocatoriasOT = new OCAPConvocatoriasOT();
 
           componentesOT.setDConvocNombre(convocatoriasLN.buscarOCAPConvocatorias(((OCAPComponentesfqsOT)antiguas.get(j)).getCConvocatoriaId()).getDNombre());
         } else {
           componentesOT.setDConvocNombre("-");
         }
 
         if (((OCAPComponentesfqsOT)antiguas.get(j)).getCProfesionalId() != 0L)
         {
           OCAPCategProfesionalesOT categProfOT = new OCAPCategProfesionalesOT();
 
           OCAPCategProfesionalesLN categProfLN = new OCAPCategProfesionalesLN(jcylUsuario);
 
           componentesOT.setDProfesionalNombre(categProfLN.buscarOCAPCategProfesionales(((OCAPComponentesfqsOT)antiguas.get(j)).getCProfesionalId()).getDNombre());
         } else {
           componentesOT.setDProfesionalNombre("-");
         }
 
         if (((OCAPComponentesfqsOT)antiguas.get(j)).getCEspecId() != 0L)
         {
           OCAPEspecialidadesOT especialidadesOT = new OCAPEspecialidadesOT();
 
           OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
 
           componentesOT.setDEspecNombre(especialidadesLN.buscarOCAPEspecialidades(((OCAPComponentesfqsOT)antiguas.get(j)).getCEspecId()).getDNombre());
         } else {
           componentesOT.setDEspecNombre("-");
         }
 
         if (((OCAPComponentesfqsOT)antiguas.get(j)).getCItinerarioId() != 0L)
         {
           OCAPItinerariosOT itinerariosOT = new OCAPItinerariosOT();
 
           OCAPItinerariosLN itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
 
           componentesOT.setDItinerarioNombre(itinerariosLN.buscarOCAPItinerarios(((OCAPComponentesfqsOT)antiguas.get(j)).getCItinerarioId()).getDDescripcion());
         } else {
           componentesOT.setDItinerarioNombre("-");
         }
 
         if (((OCAPComponentesfqsOT)antiguas.get(j)).getCCteId() != 0L)
         {
           OCAPGestionCtesOT ctesOT = new OCAPGestionCtesOT();
 
           componentesOT.setDCteNombre(ctesLN.buscarOCAPCte(((OCAPComponentesfqsOT)antiguas.get(j)).getCCteId()).getDNombre());
         } else {
           componentesOT.setDCteNombre("-");
         }
 
         histConv.add(componentesOT);
       }
 
       formulario.setListadoConv(histConv);
 
       ArrayList activas = oCAPEvaluadoresLN.buscarOCAPConvocatoriasAct(formulario.getCCompfqs_id());
 
       ArrayList histAct = null;
       histAct = new ArrayList();
 
       for (int j = 0; j < activas.size(); j++) {
         OCAPComponentesfqsOT componentesOT = new OCAPComponentesfqsOT();
 
         componentesOT.setCCompfqsConvoId(((OCAPComponentesfqsOT)activas.get(j)).getCCompfqsConvoId());
 
         if (((OCAPComponentesfqsOT)activas.get(j)).getCConvocatoriaId() != 0L)
         {
           OCAPConvocatoriasOT convocatoriasOT = new OCAPConvocatoriasOT();
 
           componentesOT.setDConvocNombre(convocatoriasLN.buscarOCAPConvocatorias(((OCAPComponentesfqsOT)activas.get(j)).getCConvocatoriaId()).getDNombre());
         } else {
           componentesOT.setDConvocNombre("-");
         }
 
         if (((OCAPComponentesfqsOT)activas.get(j)).getCProfesionalId() != 0L)
         {
           OCAPCategProfesionalesOT categProfOT = new OCAPCategProfesionalesOT();
 
           OCAPCategProfesionalesLN categProfLN = new OCAPCategProfesionalesLN(jcylUsuario);
 
           componentesOT.setDProfesionalNombre(categProfLN.buscarOCAPCategProfesionales(((OCAPComponentesfqsOT)activas.get(j)).getCProfesionalId()).getDNombre());
         } else {
           componentesOT.setDProfesionalNombre("-");
         }
 
         if (((OCAPComponentesfqsOT)activas.get(j)).getCEspecId() != 0L)
         {
           OCAPEspecialidadesOT especialidadesOT = new OCAPEspecialidadesOT();
 
           OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
 
           componentesOT.setDEspecNombre(especialidadesLN.buscarOCAPEspecialidades(((OCAPComponentesfqsOT)activas.get(j)).getCEspecId()).getDNombre());
         } else {
           componentesOT.setDEspecNombre("-");
         }
 
         if (((OCAPComponentesfqsOT)activas.get(j)).getCItinerarioId() != 0L)
         {
           OCAPItinerariosOT itinerariosOT = new OCAPItinerariosOT();
 
           OCAPItinerariosLN itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
 
           componentesOT.setDItinerarioNombre(itinerariosLN.buscarOCAPItinerarios(((OCAPComponentesfqsOT)activas.get(j)).getCItinerarioId()).getDDescripcion());
         } else {
           componentesOT.setDItinerarioNombre("-");
         }
 
         if (((OCAPComponentesfqsOT)activas.get(j)).getCCteId() != 0L)
         {
           OCAPGestionCtesOT ctesOT = new OCAPGestionCtesOT();
 
           componentesOT.setDCteNombre(ctesLN.buscarOCAPCte(((OCAPComponentesfqsOT)activas.get(j)).getCCteId()).getDNombre());
         } else {
           componentesOT.setDCteNombre("-");
         }
 
         histAct.add(componentesOT);
       }
 
       formulario.setListadoAct(histAct);
 
       OCAPConfigApp.logger.info(getClass().getName() + " obtenerConvocatorias: Fin");
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return formulario;
   }
 
   public ActionForward irDetalle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     ActionErrors errors = new ActionErrors();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irDetalle: Inicio");
 
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
       Utilidades utilidades = new Utilidades();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
       OCAPComponentesfqsOT datos = new OCAPComponentesfqsOT();
       OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm)form;
 
       String cCompfqsIdS = request.getParameter("cCompfqsIdS");
       long cCompfqsId;
       if ((cCompfqsIdS != null) && (!cCompfqsIdS.equals(""))) {
         cCompfqsId = Long.parseLong(cCompfqsIdS);
         OCAPConfigApp.logger.info("cCompfqsId: " + cCompfqsId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
 
         return mapping.findForward("error");
       }
 
       datos = oCAPEvaluadoresLN.buscarOCAPEvaluadores(cCompfqsId);
 
       if (datos.getCCompfqsId() == 0L) {
         OCAPConfigApp.logger.info("No encontramos OCAPComponentesfqsOT");
         sig = "error";
         request.setAttribute("mensaje", "No se encuentra el registro");
       }
       else {
         OCAPConfigApp.logger.info("Se ha encontrado OCAPComponentesfqsOT");
         request.setAttribute("OCAPComponentesfqsOT", datos);
 
         formulario.setCCompfqs_id(datos.getCCompfqsId());
         formulario.setDNombre(datos.getDNombre());
         formulario.setDApellidos(datos.getDApellidos());
         formulario.setCDni(datos.getCDni());
         formulario.setBSexo(datos.getBSexo());
         formulario.setNTelefono1(datos.getNTelefono1());
         formulario.setACorreoelectronico(datos.getACorreoelectronico());
 
         formulario.setADir_nombre(datos.getADirNombre());
         formulario.setADir_num(datos.getADirNum());
         formulario.setADir_escalera(datos.getADirEscalera());
         formulario.setADir_piso(datos.getADirPiso());
         formulario.setADir_letra(datos.getADirLetra());
         formulario.setCCp(datos.getCCp());
 
         OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
 
         formulario.setDProv_nombre(provinciasLN.buscarProvincia(datos.getCProvId()).getDProvincia());
         formulario.setCLocalidad_id(datos.getCLocalidadId());
 
         formulario.setATitulacion(datos.getATitulacion());
         formulario.setAEspecialidad(datos.getAEspecialidad());
 
         OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
         formulario.setDGrado_nombre(gradoLN.buscarOCAPGrado(datos.getCGradoId()).getDNombre());
         formulario.setACentrotrabajo(datos.getACentrotrabajo());
 
         OCAPComunidadesLN comunidadesLN = new OCAPComunidadesLN(jcylUsuario);
 
         formulario.setDComuni_nombre(comunidadesLN.buscarComunidad(datos.getCComuniId()));
 
         formulario.setDProv_nombre_post(provinciasLN.buscarProvincia(datos.getCProvinId()).getDProvincia());
         formulario.setAForm_acreditacion(datos.getAFormAcreditacion());
         formulario.setAForm_gestion(datos.getAFormGestion());
         formulario.setAForm_evaluacion(datos.getAFormEvaluacion());
         formulario.setAExpprof_ss(datos.getAExpprofSs());
         formulario.setAExpcal_asistencia(datos.getAExpcalAsistencia());
         formulario.setAAct_docente(datos.getAActDocente());
 
         OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
 
         formulario.setDConvoc_nombre(convocatoriasLN.buscarOCAPConvocatorias(datos.getCConvocatoriaId()).getDNombre());
 
         OCAPConfigApp.logger.info("Se ha encontrado OCAPEvaluadores");
         sig = "verDetalle";
         request.setAttribute("tipoAccion", Constantes.VER_DETALLE);
 
         request.setAttribute("tarea", Constantes.MODIFICAR);
       }
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: ERROR: " + e.getMessage());
 
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward(sig);
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward cargarComboLocalidades(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errors = new ActionErrors();
     String sig = "error";
     String mensajeError = "Se ha producido un error";
     HttpSession session = request.getSession();
     Utilidades utilidades = new Utilidades();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboEspecialidades: Inicio");
 
       validarAccion(mapping, form, request, response);
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm)form;
 
       if ((formulario.getCProv_id() != null) && (!formulario.getCProv_id().equals("")))
       {
         OCAPLocalidadesLN localidadesLN = new OCAPLocalidadesLN(jcylUsuario);
 
         Collection listadoLocal = localidadesLN.listarLocalidadesProvincia(formulario.getCProv_id());
 
         Object[] listadoLocalidades = listadoLocal.toArray();
 
         session.setAttribute(Constantes.COMBO_LOCALIDADES, utilidades.ArrayObjectToArrayList(listadoLocalidades));
       }
       else {
         session.setAttribute(Constantes.COMBO_LOCALIDADES, new ArrayList());
       }
 
       if (request.getParameter("jspVuelta").equals(Constantes.INSERTAR)) {
         request.setAttribute("tipoAccion", Constantes.INSERTAR);
       }
       else {
         request.setAttribute("tipoAccion", Constantes.MODIFICAR);
 
         request.setAttribute("nombre", request.getParameter("nombre"));
       }
 
       request.setAttribute("opcion", request.getParameter("opcion"));
 
       request.setAttribute("tarea", request.getParameter("tarea"));
 
       sig = "irInsertar";
 
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboLocalidades: Fin");
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboLocalidades: ERROR: " + e.getMessage());
 
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward(sig);
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward buscarIndicador(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String mensajeError = "Se ha producido un error";
     Logger log = null;
     ActionForward actionForward = null;
     Utilidades utilidades = new Utilidades();
     ActionMessages messages = new ActionMessages();
     OCAPComponentesfqsForm datosForm = (OCAPComponentesfqsForm)form;
 
     OCAPUsuariosOT datosArray = null;
     float porcInd = 0.0F;
     ArrayList listadoIndicador = new ArrayList();
     try
     {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- CONSULTA DE EVALUADORES --------- ");
       ActionErrors errores = new ActionErrors();
 
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
       OCAPGestionCtesLN oCAPGestionCtesLN = new OCAPGestionCtesLN(jcylUsuario);
 
       OCAPUsuariosOT datos = oCAPEvaluadoresLN.buscarIndicador(request.getParameter("opcion"));
 
       if (datos.getTotalIndicadores() != 0) {
         OCAPConfigApp.logger.info("Se han recuperado " + datos.getTotalEvaluados() + " Evaluados para la consulta");
 
         datosForm.setTotalEvaluados(datos.getTotalEvaluados());
         datosForm.setTotalIndicadores(datos.getTotalIndicadores());
         datosForm.setTotalIndicadoresOK(datos.getTotalIndicadoresOK());
         datosForm.setTotalIndicadoresKO(datos.getTotalIndicadoresKO());
         datosForm.setPorcIndicadoresOK(datos.getPorcIndicadoresOK());
         datosForm.setPorcIndicadoresKO(datos.getPorcIndicadoresKO());
 
         for (int i = 0; i < datos.getListaIndicadores().size(); 
           i++) {
           datosArray = (OCAPUsuariosOT)datos.getListaIndicadores().get(i);
 
           porcInd = (float)(100.0D * datosArray.getTotalIndicador() / datos.getTotalEvaluados());
 
           porcInd = (float)(Math.rint(porcInd * 100.0F) / 100.0D);
 
           datosArray.setPorcIndicador(porcInd);
 
           listadoIndicador.add(datosArray);
         }
 
         datosForm.setListadoAct(listadoIndicador);
 
         request.setAttribute("opcion", request.getParameter("opcion"));
       }
       else
       {
         request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
       }
 
       OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAPEvaluadores ------- ");
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       request.setAttribute("mensaje", mensajeError);
       ((OCAPComponentesfqsForm)form).setjspInicio(false);
       return mapping.findForward("error");
     }
 
     return mapping.findForward("irIndicadores");
   }
 
   public ActionForward activarSegunda(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errors = new ActionErrors();
     try
     {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- activarSegunda --------- ");
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
       OCAPComponentesfqsOT datos = new OCAPComponentesfqsOT();
       datos.setBEvaluacion2(request.getParameter("activar"));
       datos.setCCompfqsId(Long.parseLong(request.getParameter("cCompfqsIdS") == null ? "0" : request.getParameter("cCompfqsIdS")));
 
       oCAPEvaluadoresLN.activarEvaluadoresSegunda(datos);
 
       request.setAttribute("rutaVuelta", "OCAPEvaluadores.do?accion=buscar&opcion=cp");
 
       OCAPConfigApp.logger.info("---------- activarSegunda FIN --------- ");
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(ex, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward("exito");
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward irAsignarEvaluados(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     ActionErrors errors = new ActionErrors();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irAsignarEvaluados: Inicio");
 
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
       Utilidades utilidades = new Utilidades();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       String cCompfqsIdS = request.getParameter("cCompfqsIdS");
       long cCompfqsId;
       if ((cCompfqsIdS != null) && (!cCompfqsIdS.equals(""))) {
         cCompfqsId = Long.parseLong(cCompfqsIdS);
         OCAPConfigApp.logger.info("cCompfqsId: " + cCompfqsId);
       } else {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
 
         return mapping.findForward("error");
       }
 
       ((OCAPComponentesfqsForm)form).setCCompfqs_id(cCompfqsId);
 
       OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
 
       Collection listadoConv = convocatoriasLN.listarConvocatoriasActivasEvaluador(cCompfqsId);
 
       Object[] listadoConvocatorias = listadoConv.toArray();
 
       session.setAttribute(Constantes.COMBO_CONVOCATORIAS_EVAL, utilidades.ArrayObjectToArrayList(listadoConvocatorias));
 
       session.setAttribute(Constantes.COMBO_ITINERARIOS, new ArrayList());
 
       session.removeAttribute("listaPosiblesEvaluados");
 
       OCAPConfigApp.logger.info("Se ha encontrado OCAPEvaluadores");
       sig = "irAsignarEvaluados";
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irAsignarEvaluados: ERROR: " + e.getMessage());
 
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward(sig);
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward cargarComboItinerariosEvaluador(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     HttpSession session = request.getSession();
     ActionErrors errors = new ActionErrors();
     ArrayList listadoItinerarios = new ArrayList();
     String sig = "error";
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboItinerariosEvaluador: Inicio");
 
       validarAccion(mapping, form, request, response);
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       long cCompfqsId = ((OCAPComponentesfqsForm)form).getCCompfqs_id();
 
       if (cCompfqsId == 0L) {
         request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
 
         return mapping.findForward("error");
       }
 
       OCAPItinerariosLN itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
 
       listadoItinerarios = itinerariosLN.listarItinerariosConvocatoria(((OCAPComponentesfqsForm)form).getCConvocatoria_id(), cCompfqsId);
 
       session.setAttribute(Constantes.COMBO_ITINERARIOS, listadoItinerarios);
 
       sig = "irAsignarEvaluados";
 
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboItinerariosEvaluador: Fin");
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.info(getClass().getName() + " cargarComboItinerariosEvaluador: ERROR: " + e.getMessage());
 
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward(sig);
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward listarPosiblesEvaluados(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
   {
     HttpSession sesion = request.getSession();
     ActionErrors errores = new ActionErrors();
     try
     {
       JCYLUsuario jcylUsuario = (JCYLUsuario)sesion.getAttribute("JCYLUsuario");
 
       OCAPGerenciasLN gerenciaLN = new OCAPGerenciasLN(jcylUsuario);
 
       OCAPGerenciasOT gerencia = gerenciaLN.buscarOCAPGerenciasCodLDAP(jcylUsuario.getUsuario().getD_gerencia());
 
       OCAPExpedientesLN expedientesLN = new OCAPExpedientesLN(jcylUsuario);
 
       OCAPExpedientesOT busqueda = new OCAPExpedientesOT();
       busqueda.setCConvocatoriaId(((OCAPComponentesfqsForm)form).getCConvocatoria_id());
       busqueda.setCItinerarioId(((OCAPComponentesfqsForm)form).getCItinerario_id());
       busqueda.setCGerenciaId(gerencia.getCGerenciaId());
 
       ArrayList listaPosiblesEvaluados = expedientesLN.listarPosiblesEvaluados(busqueda);
 
       if (sesion.getAttribute("listaPosiblesEvaluados") != null)
         sesion.removeAttribute("listaPosiblesEvaluados");
       sesion.setAttribute("listaPosiblesEvaluados", listaPosiblesEvaluados);
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       errores.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
     }
 
     if (errores.size() != 0) {
       saveErrors(request, errores);
 
       return mapping.findForward("fallo");
     }
 
     return mapping.findForward("irAsignarEvaluados");
   }
 
   public ActionForward asignarEvaluados(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     ActionErrors errors = new ActionErrors();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irAsignarEvaluados: Inicio");
 
       HttpSession session = request.getSession();
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPEvaluadoresLN evaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
       int result = evaluadoresLN.asignarEvaluador(((OCAPComponentesfqsForm)form).getCCompfqs_id(), ((OCAPComponentesfqsForm)form).getListaPosiblesEvaluadosSelec(), jcylUsuario.getUsuario().getC_usr_id());
 
       if (result == 1) {
         request.setAttribute("mensaje", "El registro se ha modificado con éxito");
 
         request.setAttribute("rutaVuelta", "OCAPEvaluadores.do?accion=irAsignarEvaluados&cCompfqsIdS=" + ((OCAPComponentesfqsForm)form).getCCompfqs_id());
 
         sig = "exito";
       } else {
         request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
 
         sig = "error";
       }
 
       OCAPConfigApp.logger.info("Se ha encontrado OCAPEvaluadores");
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irAsignarEvaluados: ERROR: " + e.getMessage());
 
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward(sig);
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 
   public ActionForward listarEvaluadosGerenciaCTE(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     ActionErrors errors = new ActionErrors();
     ActionMessages messages = new ActionMessages();
     Utilidades utilidades = new Utilidades();
     String sig = "irListarEvaluadosGerenciaCTE";
     try {
       OCAPConfigApp.logger.info("");
       OCAPConfigApp.logger.info("---------- Listar evaluados Gerencia CTE --------- ");
       ActionErrors errores = new ActionErrors();
       HttpSession session = request.getSession();
 
       request.setAttribute("opcion", request.getParameter("opcion"));
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
 
       if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR))) && (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI)))
       {
         form = (OCAPComponentesfqsForm)request.getSession().getAttribute("OCAPComponentesFormBuscador");
 
         request.setAttribute("OCAPComponentesfqsForm", form);
       } else {
         request.getSession().setAttribute("OCAPComponentesFormBuscador", form);
 
         session.setAttribute("iRegistro", 1 + "");
       }
 
       int primerRegistro = 1;
       int registrosPorPagina = 10;
 
       String registro = null;
 
       if ((registro = request.getParameter("iRegistro")) != null)
       {
         try
         {
           primerRegistro = Integer.parseInt(registro);
           session.setAttribute("iRegistro", primerRegistro + "");
         }
         catch (NumberFormatException ne) {
         }
       }
       else if ((registro = session.getAttribute("iRegistro") == null ? null : session.getAttribute("iRegistro").toString()) != null)
       {
         try
         {
           primerRegistro = Integer.parseInt(registro);
         }
         catch (NumberFormatException ne)
         {
         }
       }
       if ((!Constantes.SI.equals(request.getParameter("bPagina"))) && (!Constantes.SI.equals(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR))))
       {
         primerRegistro = 1;
       }
 
       String s = request.getParameter("nRegistrosP");
 
       if (s != null) {
         registrosPorPagina = Integer.parseInt(s);
       }
 
       long gerencia = 0L;
 
       OCAPUsuariosOT usuarioOT = new OCAPUsuariosOT();
       OCAPComponentesCtesLN componentesCtesLN = new OCAPComponentesCtesLN(jcylUsuario);
       gerencia = componentesCtesLN.buscarGerenciaCte(jcylUsuario.getUsuario().getC_usr_id());
 
       if (gerencia != 0L) {
         usuarioOT.setCGerenciaId(Integer.valueOf((int)gerencia));
       }
       else {
         OCAPGerenciasLN gerenciaLN = new OCAPGerenciasLN(jcylUsuario);
 
         OCAPGerenciasOT gerenciaOT = gerenciaLN.buscaOCAPGerenciaLDAP(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getD_gerencia());
 
         usuarioOT.setCGerenciaId(Integer.valueOf((int)gerenciaOT.getCGerenciaId()));
       }
 
       if (((OCAPComponentesfqsForm)form).getCProfesional_id() != 0L) {
         usuarioOT.setCProfesionalId(Integer.valueOf((int)((OCAPComponentesfqsForm)form).getCProfesional_id()));
       }
 
       if (((OCAPComponentesfqsForm)form).getCConvocatoria_id() != 0L) {
         usuarioOT.setCConvocatoriaId((int)((OCAPComponentesfqsForm)form).getCConvocatoria_id());
       }
       
       if (((OCAPComponentesfqsForm)form).getCEspec_id() != 0L) {
           usuarioOT.setCEspecId((int)((OCAPComponentesfqsForm)form).getCEspec_id());
         }
       
       if (((OCAPComponentesfqsForm)form).getDEstado() != null) {
           usuarioOT.setDEstado(((OCAPComponentesfqsForm)form).getDEstado());

       }
         
       
 
       Collection elementos = null;
       elementos = oCAPEvaluadoresLN.listarEvaluadosGerenciaCTE(usuarioOT, jcylUsuario, primerRegistro, registrosPorPagina);
 
       if (elementos != null) {
         OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Evaluadores para la consulta");
 
         if (elementos.size() == 0) {
           request.setAttribute("sinDatos", "No existen datos con los parametros especificados");
 
           request.getSession().removeAttribute("listados");
         } else {
           if (!"ENCUESTA".equals(request.getParameter("opcion"))) {
             Object[] listadoelementos = elementos.toArray();
 
             OCAPCategProfesionalesLN categProfLN = new OCAPCategProfesionalesLN(jcylUsuario);
 
             OCAPCategProfesionalesOT categProfOT = new OCAPCategProfesionalesOT();
 
             int reg = elementos.size();
 
             elementos.removeAll(elementos);
 
             for (int i = 0; i < reg; i++) {
               if (((OCAPUsuariosOT)listadoelementos[i]).getCProfesionalId().longValue() != 0L)
               {
                 ((OCAPUsuariosOT)listadoelementos[i]).setDProfesional_nombre(categProfLN.buscarOCAPCategProfesionales(((OCAPUsuariosOT)listadoelementos[i]).getCProfesionalId().longValue()).getDNombre());
               }
               else ((OCAPUsuariosOT)listadoelementos[i]).setDProfesional_nombre("");
 
               elementos.add(listadoelementos[i]);
             }
           }
           int nListado = 0;
           nListado = oCAPEvaluadoresLN.listarEvaluadosCuentaGerenciaCTE(usuarioOT, jcylUsuario);
 
           session.setAttribute("numEvaluadores", new Integer(nListado));
 
           request.removeAttribute("sinDatos");
           Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
 
           pagina.setElementos(elementos);
           pagina.setRegistroActual(primerRegistro);
 
           pagina.setNRegistros(((Integer)session.getAttribute("numEvaluadores")).intValue());
           pagina.setRegistrosPorPagina(registrosPorPagina);
           request.setAttribute("listados", pagina);
           request.removeAttribute("menu");
           request.getSession().removeAttribute("menu");
           request.setAttribute("cte", request.getParameter("cte"));
 
           request.setAttribute("codigo", request.getParameter("codigo"));
         }
       }
       else
       {
         request.setAttribute("errorConsultando", "Error consultando en la base de datos");
       }
 
       OCAPConfigApp.logger.info("...........se sale del Action");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: ERROR: " + e.getMessage());
 
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
     HttpSession session = request.getSession();
     ArrayList listadoEspecialidades = new ArrayList();

   
 
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
 
 
       if (((OCAPComponentesfqsForm)form).getCProfesional_id() != 0L)
       {
         listadoEspecialidades = especialidadesLN.listarEspecialidades(((OCAPComponentesfqsForm)form).getCProfesional_id());
 
         session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);

       }
       else
       {
         session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());

       }

     if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
     {
       return mapping.findForward(sig);
     }if ((errors != null) && (!errors.isEmpty())) {
       saveErrors(request, errors);
 
       return mapping.findForward("fallo");
     }
     saveMessages(request, messages);
     ((OCAPComponentesfqsForm)form).setjspInicio(true);
 
     return mapping.findForward(sig);
   }
   public ActionForward cargarEspecialidades(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Type", "application/json;charset=ISO-8859-1");
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoEspecialidades = null;
		String textReturn = "";
		try {
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			String especialidadId = request.getParameter("val");

			if (especialidadId == null || especialidadId.equals("")||especialidadId.trim().equals("0")) {
				textReturn = getExceptionEspecialidades(especialidadId);
			} else {
				OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
				listadoEspecialidades = especialidadesLN.listarEspecialidades(Long.parseLong(especialidadId));
				textReturn = getCascadeEspecialidades(listadoEspecialidades, especialidadId);
			}

			PrintWriter out = response.getWriter();
			out.println(textReturn);
			out.flush();
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}
		return null;
	}
	private String getExceptionEspecialidades(String profesionalId) {
		StringBuffer textReturn = new StringBuffer();
		textReturn.append("[");
		textReturn.append("{'When':'").append(profesionalId).append("','Value':'").append("").append("','Text':'")
				.append("Seleccione...").append("'},").append("]");

		return textReturn.toString();
	}
	private String getCascadeEspecialidades(ArrayList listadoEspecialidades, String profesionalId) {
		StringBuffer textReturn = new StringBuffer();
		if ((listadoEspecialidades != null) && (listadoEspecialidades.size() > 0)) {
			OCAPEspecialidadesOT especialidadOT = null;
			textReturn.append("[");

			textReturn.append("{'When':'" + profesionalId + "','Value':'','Text':'Seleccione...'},");

			for (int i = 0; i < listadoEspecialidades.size(); i++) {
				especialidadOT = (OCAPEspecialidadesOT) listadoEspecialidades.get(i);

				textReturn.append("{'When':'").append(profesionalId).append("','Value':'")
						.append(especialidadOT.getCEspecId()).append("','Text':'")
						.append(formatCadena(especialidadOT.getDNombre())).append("'},");
			}
			textReturn.append("]");
		}else{
			textReturn.append("[");

			textReturn.append("{'When':'" + profesionalId + "','Value':'','Text':'Seleccione...'},");
			
			textReturn.append("]");
		}

		return textReturn.toString();
	}
	private String formatCadena(String cad) {
		cad = cad.replaceAll("'", "&rsquo;");

		return cad;
	}
   public ActionForward irBuscarEvaluadosGrenciaCTE(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     ActionErrors errors = new ActionErrors();
     HttpSession session = request.getSession();
     Utilidades utilidades = new Utilidades();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irBuscarEvaluadosGrenciaCTE: Inicio");
 
       validarAccion(mapping, form, request, response);
 
       JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
 
       OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
 
       Collection listadoConv = convocatoriasLN.listarConvocatorias();
 
       Object[] listadoConvocatorias = listadoConv.toArray();
 
       session.setAttribute(Constantes.COMBO_CONVOCATORIAS, utilidades.ArrayObjectToArrayList(listadoConvocatorias));
 
       OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
       ArrayList listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();
       session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);
       
       OCAPEvaluadoresLN EstadoLN = new OCAPEvaluadoresLN(jcylUsuario);
       ArrayList listadoEstados = EstadoLN.listadoEstadosCTE();
       session.setAttribute(Constantes.COMBO_ESTADOS, listadoEstados);
       
            
   	OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
	if ((((OCAPComponentesfqsForm) form).getCProfesional_id() !=0L)) {
		ArrayList listadoEspecialidades = especialidadesLN
				.listarEspecialidades((((OCAPComponentesfqsForm) form).getCProfesional_id()));
		session.setAttribute(Constantes.COMBO_ESPECIALIDAD, listadoEspecialidades);
		
	}  else {
		session.setAttribute(Constantes.COMBO_ESPECIALIDAD, new ArrayList());
	}

       ((OCAPComponentesfqsForm)form).setjspInicio(true);
 
       request.setAttribute("opcion", request.getParameter("opcion"));
 
       session.setAttribute("iRegistro", Integer.toString(1));
 
       OCAPConfigApp.logger.info(getClass().getName() + " irBuscarEvaluadosGrenciaCTE: Fin");
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.info(getClass().getName() + " irBuscarEvaluadosGrenciaCTE: ERROR: " + e.getMessage());
 
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward("irBuscarEvaluadosGrenciaCTE");
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 }

