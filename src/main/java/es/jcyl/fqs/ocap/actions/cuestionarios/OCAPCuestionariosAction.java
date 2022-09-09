package es.jcyl.fqs.ocap.actions.cuestionarios;

import es.jcyl.cf.seguridad.util.EjbFactoryAutenticacion;
import es.jcyl.cf.seguridad.util.EjbFactoryAutorizacion;
import es.jcyl.cf.seguridad.util.Usuario;
import es.jcyl.fqs.ocap.actionforms.cuestionarios.OCAPCuestionariosForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.calculoCredCuestionarios.OCAPCalculoCredCuestionariosLN;
import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
import es.jcyl.fqs.ocap.ln.componentesCtes.OCAPComponentesCtesLN;
import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
import es.jcyl.fqs.ocap.ln.creditosCuestionarios.OCAPCreditosCuestionariosLN;
import es.jcyl.fqs.ocap.ln.cuestionarios.OCAPCuestionariosLN;
import es.jcyl.fqs.ocap.ln.documentos.OCAPDocumentosLN;
import es.jcyl.fqs.ocap.ln.dossier.OCAPDossierLN;
import es.jcyl.fqs.ocap.ln.estadosCuestionario.OCAPEstadosCuestionarioLN;
import es.jcyl.fqs.ocap.ln.estatutario.OCAPEstatutarioLN;
import es.jcyl.fqs.ocap.ln.evaluadores.OCAPEvaluadoresLN;
import es.jcyl.fqs.ocap.ln.expedienteCarrera.OCAPExpedienteCarreraLN;
import es.jcyl.fqs.ocap.ln.expedientesCAs.OCAPExpedientesCAsLN;
import es.jcyl.fqs.ocap.ln.gerencias.OCAPGerenciasLN;
import es.jcyl.fqs.ocap.ln.itinerarios.OCAPItinerariosLN;
import es.jcyl.fqs.ocap.ln.modalidadAnterior.OCAPModalidadAnteriorLN;
import es.jcyl.fqs.ocap.ln.preguntas.OCAPPreguntasLN;
import es.jcyl.fqs.ocap.ln.procedimiento.OCAPProcedimientoLN;
import es.jcyl.fqs.ocap.ln.provincias.OCAPProvinciasLN;
import es.jcyl.fqs.ocap.ln.revisiones.OCAPRevisionesLN;
import es.jcyl.fqs.ocap.ln.solicitudes.OCAPNuevaSolicitudLN;
import es.jcyl.fqs.ocap.ln.solicitudes.OCAPSolicitudesLN;
import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
import es.jcyl.fqs.ocap.ot.areasItinerarios.OCAPAreasItinerariosOT;
import es.jcyl.fqs.ocap.ot.calculoCredCuestionarios.OCAPCalculoCredCuestionariosOT;
import es.jcyl.fqs.ocap.ot.categProfesionales.OCAPCategProfesionalesOT;
import es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT;
import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
import es.jcyl.fqs.ocap.ot.creditosCuestionarios.OCAPCreditosCuestionariosOT;
import es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT;
import es.jcyl.fqs.ocap.ot.cuestionarios.OCAPRepeticionesCuestionariosOT;
import es.jcyl.fqs.ocap.ot.documentos.OCAPDocumentosOT;
import es.jcyl.fqs.ocap.ot.encuestaCalidad.OCAPEncuestaCalidadOT;
import es.jcyl.fqs.ocap.ot.estatutario.OCAPEstatutarioOT;
import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;
import es.jcyl.fqs.ocap.ot.expedientesCAs.OCAPExpedientesCAsOT;
import es.jcyl.fqs.ocap.ot.gerencias.OCAPGerenciasOT;
import es.jcyl.fqs.ocap.ot.itinerarios.OCAPItinerariosOT;
import es.jcyl.fqs.ocap.ot.modalidadAnterior.OCAPModalidadAnteriorOT;
import es.jcyl.fqs.ocap.ot.procedimiento.OCAPProcedimientoOT;
import es.jcyl.fqs.ocap.ot.provincias.OCAPProvinciasOT;
import es.jcyl.fqs.ocap.ot.respuestas.OCAPRespuestasOT;
import es.jcyl.fqs.ocap.ot.revisiones.OCAPRevisionesOT;
import es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.TrataError;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.fqs.ocap.util.reports.Report;
import es.jcyl.fqs.ocap.util.reports.ot.ReportOT;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLUsuario;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OCAPCuestionariosAction extends OCAPGenericAction
{
  private JCYLUsuario jcylUsuario;
  public ActionForward irPresentacion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();
    String mensajeError = "Se ha producido un error";
    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " irPresentacion: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      String usuId = jcylUsuario.getUser().getC_usr_id();
      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);

      if (usuarioOT == null)
      {
        return mapping.findForward("irNoExisteSolicitud");
      }

      Date dHoy = new Date();

      if ((usuarioOT.getFIncioCA() == null) || (!dHoy.before(usuarioOT.getFIncioCA())))
      {
        if (!dHoy.after(usuarioOT.getFFinCA()));
      }

      OCAPConfigApp.logger.info(getClass().getName() + " irPresentacion: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      request.setAttribute("mensaje", mensajeError);
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward("irPresentacion");
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward confirmarDatos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPConvocatoriasLN convocatoriaLN = null;
    OCAPConvocatoriasOT convoOT = null;
    OCAPExpedienteCarreraLN expCarreraLN = null;
    OCAPExpedientecarreraOT expCarreraOT = null;
    OCAPItinerariosLN itinerariosLN = null;
    ArrayList listaItinerarios = null;
    try
    {
      OCAPConfigApp.logger.debug(getClass().getName() + " confirmarDatos: Inicio");

      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      String usuId = jcylUsuario.getUser().getC_usr_id();
      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);		

      if (usuarioOT == null)
      {
        return mapping.findForward("irNoExisteSolicitud");
      }

      OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
      OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();
      solicitudesOT.setCExp_id(usuarioOT.getCExpId().longValue());
      solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);

      //OCAP-630
      OCAPNuevaSolicitudLN nuevaSolicitudLN = new OCAPNuevaSolicitudLN(jcylUsuario);
      OCAPSolicitudesOT solicitudUsuarioOT = nuevaSolicitudLN.detalleSolicitud(solicitudesOT.getCSolicitudId());
      
      if ((solicitudesOT.getFAceptacionCC() == null) || ((solicitudesOT.getFFin_eval_mc() != null) && (solicitudesOT.getFFin_eval_mc().after(new Date())))) {
        return mapping.findForward("irFaseIInoAceptada");
      }

      convocatoriaLN = new OCAPConvocatoriasLN(jcylUsuario);
      convoOT = convocatoriaLN.buscarOCAPConvocatorias(solicitudesOT.getCConvocatoriaId());
      //OCAP-739
      
      ((OCAPCuestionariosForm)form).setNAnioConvocatoria(convoOT.getDAnioConvocatoria());
      
      if (convoOT.getFInicioCA() == null) {
        return mapping.findForward("irFaseCAnoIniciada");
      }
      
      //OCAP-1879
      if (solicitudesOT.getFInicio_ca() == null 
    		  && new Date().after(DateUtil.convertStringToDate(convoOT.getFFinCA()))) {
    	  return mapping.findForward("irFaseCAnoIniciada");
      }
      
      if ((usuarioOT.getFIncioCA() == null) || (usuarioOT.getFIncioCA().before(DateUtil.convertStringToDate(convoOT.getFInicioCA()))))
      {
        expCarreraOT = new OCAPExpedientecarreraOT();
        expCarreraOT.setCExpId(usuarioOT.getCExpId());

        expCarreraOT.setFInicioCa(new Date());
        expCarreraOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());
        expCarreraOT.setFFinCa(DateUtil.addDias(DateUtil.convertStringToDate(convoOT.getFInicioCA()), new Long(convoOT.getNDiasAutoCa()).intValue()));
        expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
        expCarreraLN.modificacionOCAPExpedientecarrera(expCarreraOT, false);
      }
      if (new Date().before(DateUtil.convertStringToDate(convoOT.getFInicioCA()))) {
        return mapping.findForward("irFaseCAnoIniciada");

        //OCAP-1514
      }else if (solicitudesOT.getFInicio_ca() != null 
    		  && solicitudesOT.getFInicio_ca().after(DateUtil.convertStringToDate(convoOT.getFInicioCA()))
    		  && new Date().before(solicitudesOT.getFInicio_ca())) {
    	  return mapping.findForward("irFaseCAnoIniciada");
      }

      if (usuarioOT.getCEstadoExpId() == Constantes.ESTADO_RENUNCIA) {
        return mapping.findForward("irRenunciaSolicitud");
      }

      if ((solicitudesOT.getBVerificaDatosFaseIII() != null) && (Constantes.SI.equals(solicitudesOT.getBVerificaDatosFaseIII()))) {
        return cambiarPerfil(mapping, form, request, response);
      }

      ((OCAPCuestionariosForm)form).setCExpId(Long.valueOf(solicitudesOT.getCExp_id()));
      DecimalFormat formato = new DecimalFormat("000000");
      ((OCAPCuestionariosForm)form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(solicitudesOT.getCExp_id()));
      ((OCAPCuestionariosForm)form).setDNombre(solicitudUsuarioOT.getDNombre());
      ((OCAPCuestionariosForm)form).setDApellido1(solicitudUsuarioOT.getDApellido1());
      ((OCAPCuestionariosForm)form).setDCorreoelectronico(solicitudUsuarioOT.getDCorreoelectronico() == null ? "-" : solicitudUsuarioOT.getDCorreoelectronico());
      ((OCAPCuestionariosForm)form).setCDni(solicitudesOT.getCDni());
      ((OCAPCuestionariosForm)form).setCDniReal(solicitudUsuarioOT.getCDniReal());

      if (solicitudUsuarioOT.getNTelefono1() == null || solicitudUsuarioOT.getNTelefono1().equals("0"))
        ((OCAPCuestionariosForm)form).setNTelefono1("-");
      else {
        ((OCAPCuestionariosForm)form).setNTelefono1(String.valueOf(solicitudUsuarioOT.getNTelefono1()));
      }
      if (solicitudUsuarioOT.getNTelefono2() == null || solicitudUsuarioOT.getNTelefono2().equals("0"))
        ((OCAPCuestionariosForm)form).setNTelefono2("-");
      else {
        ((OCAPCuestionariosForm)form).setNTelefono2(String.valueOf(solicitudUsuarioOT.getNTelefono2()));
      }
      ((OCAPCuestionariosForm)form).setBSexo(solicitudUsuarioOT.getBSexo());
      ((OCAPCuestionariosForm)form).setDDomicilio(solicitudUsuarioOT.getDDomicilio() == null ? "-" : solicitudUsuarioOT.getDDomicilio());
      ((OCAPCuestionariosForm)form).setDLocalidadUsu(solicitudUsuarioOT.getDLocalidad() == null ? "-" : solicitudUsuarioOT.getDLocalidad());
      ((OCAPCuestionariosForm)form).setCProvinciaUsu_id(solicitudUsuarioOT.getCProvincia_id());
      ((OCAPCuestionariosForm)form).setDProvinciaUsu(solicitudUsuarioOT.getDProvinciaUsu() == null ? "-" : solicitudUsuarioOT.getDProvinciaUsu());
      formato = new DecimalFormat("00000");
      ((OCAPCuestionariosForm)form).setCPostalUsu(solicitudUsuarioOT.getCPostalUsu() == null ? "-" : formato.format(Long.parseLong(solicitudUsuarioOT.getCPostalUsu())));

      OCAPModalidadAnteriorLN modalidadLN = new OCAPModalidadAnteriorLN(jcylUsuario);
      if (solicitudesOT.getCModAnterior_id() != 0L) {
        OCAPModalidadAnteriorOT modalidadOT = modalidadLN.buscarOCAPModalidad(solicitudesOT.getCModAnterior_id());
        ((OCAPCuestionariosForm)form).setDModAnterior(modalidadOT.getDDescripcion());
      }
      modalidadLN = null;

      OCAPProcedimientoLN procLN = new OCAPProcedimientoLN(jcylUsuario);
      if ((solicitudesOT.getCProcedimientoId() != null) && (!solicitudesOT.getCProcedimientoId().equals(""))) {
        OCAPProcedimientoOT procOT = procLN.buscarOCAPProcedimiento(Long.parseLong(solicitudesOT.getCProcedimientoId()));
        ((OCAPCuestionariosForm)form).setDProcedimiento(procOT.getDNombre());
      }
      procLN = null;

      OCAPCategProfesionalesLN categoriaLN = new OCAPCategProfesionalesLN(jcylUsuario);
      OCAPCategProfesionalesOT categoriaOT = categoriaLN.buscarOCAPCategProfesionales(solicitudesOT.getCProfesional_id());
      categoriaLN = null;

      OCAPEstatutarioLN estatutarioLN = new OCAPEstatutarioLN(jcylUsuario);
      OCAPEstatutarioOT estatutarioOT = estatutarioLN.buscarOCAPEstatutario(categoriaOT.getCEstatutId());
      ((OCAPCuestionariosForm)form).setCPersonalId(String.valueOf(estatutarioOT.getCPersonalId()));
      estatutarioLN = null;

      if (Constantes.C_JURIDICO_ESTATUTARIO_COD.equals(solicitudesOT.getCJuridicoId())) {
        ((OCAPCuestionariosForm)form).setCJuridicoCombo(String.valueOf(estatutarioOT.getCPersonalId()));
      }

      if (Constantes.C_JURIDICO_OTROS_COD.equals(solicitudesOT.getCJuridico())) {
        ((OCAPCuestionariosForm)form).setDRegimenJuridicoOtros(solicitudesOT.getDRegimenJuridicoOtros());
      }

      ((OCAPCuestionariosForm)form).setCSitAdministrativaAuxId(solicitudesOT.getCSitAdministrativaAuxId());
      ((OCAPCuestionariosForm)form).setDSitAdministrativaAuxOtros(solicitudesOT.getDSitAdministrativaAuxOtros());
      ((OCAPCuestionariosForm)form).setCJuridico(solicitudesOT.getCJuridicoId());
      ((OCAPCuestionariosForm)form).setDProfesional_nombre(solicitudesOT.getDProfesional_nombre());
      ((OCAPCuestionariosForm)form).setDEspec_nombre(solicitudesOT.getDEspec_nombre());
      ((OCAPCuestionariosForm)form).setACiudad(Cadenas.capitalizar(solicitudesOT.getACiudad()));
      ((OCAPCuestionariosForm)form).setDGerencia_nombre(solicitudUsuarioOT.getDGerencia_nombre());
      ((OCAPCuestionariosForm)form).setDCentrotrabajo_nombre(solicitudUsuarioOT.getDCentrotrabajo_nombre());
      ((OCAPCuestionariosForm)form).setDGrado_des(solicitudesOT.getDGrado_des());
      ((OCAPCuestionariosForm)form).setCConvocatoriaId(String.valueOf(solicitudesOT.getCConvocatoriaId()));

      OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
      OCAPGerenciasOT gerenciasOT = gerenciasLN.buscarOCAPGerencias(solicitudUsuarioOT.getCGerencia_id());
      gerenciasLN = null;

      OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
      OCAPProvinciasOT provinciasOT = provinciasLN.buscarProvincia(gerenciasOT.getCProvinciaId());
      ((OCAPCuestionariosForm)form).setDProvincia(provinciasOT.getDProvincia());
      provinciasLN = null;

      ((OCAPCuestionariosForm)form).setDServicio(solicitudesOT.getAServicio());
      ((OCAPCuestionariosForm)form).setDPuesto(solicitudesOT.getAPuesto());
      ((OCAPCuestionariosForm)form).setNAniosantiguedad(String.valueOf(solicitudesOT.getNAniosantiguedad()));
      ((OCAPCuestionariosForm)form).setNMesesantiguedad(String.valueOf(solicitudesOT.getNMesesantiguedad()));
      ((OCAPCuestionariosForm)form).setNDiasantiguedad(String.valueOf(solicitudesOT.getNDiasantiguedad()));

      itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      long cEspecId = 0L;
      if (usuarioOT.getCEspecId() != null) {
        cEspecId = usuarioOT.getCEspecId().longValue();
      }
      listaItinerarios = itinerariosLN.listarItinerarios(solicitudesOT.getCGrado_id(), solicitudesOT.getCProfesional_id(), cEspecId, Long.parseLong(solicitudesOT.getCProcedimientoId()));

      ((OCAPCuestionariosForm)form).setListaItinerarios(listaItinerarios);
      itinerariosLN = null;
      listaItinerarios = null;

      OCAPConfigApp.logger.debug(getClass().getName() + " confirmarDatos: Fin");
    }
    catch (Exception e)
    {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward("irDatosUsuario");
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward cambiarPerfil(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    EjbFactoryAutenticacion ejb = null;
    EjbFactoryAutorizacion ejb_aut = null;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " cambiarPerfil: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      String usuId = jcylUsuario.getUser().getC_usr_id();
      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);

      if (usuarioOT == null)
      {
        ActionForward localActionForward = mapping.findForward("irNoExisteSolicitud");

        return localActionForward;
      }
      OCAPSolicitudesLN solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
      OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();
      solicitudesOT.setCExp_id(usuarioOT.getCExpId().longValue());
      solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);

      jcylUsuario.getUser().setRol(Constantes.OCAP_USUARIO_FASE_III);

      String nombreAplicacion = JCYLConfiguracion.getValor("SISTEMA");

      String host = JCYLConfiguracion.getValor("SEGU_HOST");

      String port = JCYLConfiguracion.getValor("SEGU_PORT");

      String componente = JCYLConfiguracion.getValor("SEGU_APP_SEGURIDAD");

      String user = JCYLConfiguracion.getValor("SEGU_USER");
      String poolSegu = JCYLConfiguracion.getValor("SEGU_POOL");

      String password = JCYLConfiguracion.getValor("SEGU_PASSWORD");

      ejb = new EjbFactoryAutenticacion();
      if (ejb.getAutenticacion() == null) {
        errors.add("APP_ERROR_LOGIN", new ActionError("error.acceso.autenticacion"));
        saveErrors(request, errors);
        throw new Exception("Error acceso componente AUTENTICACION ubicado en " + host + " " + port + " " + componente + " " + user + " " + poolSegu);
      }
      OCAPConfigApp.logger.info("Conectado al componente AUTENTICACION ");

      Usuario usuarioSEGU = new Usuario();
      usuarioSEGU.setDNom(jcylUsuario.getUser().getDNom());
      usuarioSEGU.setDApell(jcylUsuario.getUser().getDApell());
      usuarioSEGU.setD_gerencia(jcylUsuario.getUser().getD_gerencia());
      usuarioSEGU.setC_usr_id(jcylUsuario.getUser().getC_usr_id());
      usuarioSEGU.setRol(jcylUsuario.getUser().getRol());

      ejb.getAutenticacion().setFechaConexion(nombreAplicacion, usuarioSEGU.getC_usr_id(), null);

      ejb.getAutenticacion().remove();

      ejb_aut = new EjbFactoryAutorizacion();
      if (ejb_aut.getAutorizacion() == null) {
        errors.add("APP_ERROR_LOGIN", new ActionError("error.acceso.autorizacion"));
        saveErrors(request, errors);
        throw new Exception("Error acceso componente AUTORIZACION ubicado en " + host + " " + port + " " + componente + " " + user + " " + poolSegu);
      }
      OCAPConfigApp.logger.info("Conectado al componente AUTORIZACION ");

      OCAPConfigApp.logger.info("Conectado al componente recuperando funciones del menú ");
      ArrayList FuncionesMenu = new ArrayList();
      FuncionesMenu = ejb_aut.getAutorizacion().getEstructura(nombreAplicacion, usuarioSEGU.getRol());

      if ((FuncionesMenu == null) || (FuncionesMenu.size() == 0)) {
        throw new Exception("Perfil de usuario incorrecto");
      }

      session.setAttribute("JCYLArrayFunciones", FuncionesMenu);

      ejb_aut.getAutorizacion().remove();

      ((OCAPCuestionariosForm)form).setCExpId(Long.valueOf(solicitudesOT.getCExp_id()));
      ((OCAPCuestionariosForm)form).setDNombre(solicitudesOT.getDNombre());
      ((OCAPCuestionariosForm)form).setDApellido1(solicitudesOT.getDApellido1());
      ((OCAPCuestionariosForm)form).setDCorreoelectronico(solicitudesOT.getDCorreoelectronico() == null ? "-" : solicitudesOT.getDCorreoelectronico());
      ((OCAPCuestionariosForm)form).setCDni(solicitudesOT.getCDni());
      ((OCAPCuestionariosForm)form).setCDniReal(solicitudesOT.getCDniReal());

      if (solicitudesOT.getNTelefono1().equals("0"))
        ((OCAPCuestionariosForm)form).setNTelefono1("-");
      else {
        ((OCAPCuestionariosForm)form).setNTelefono1(String.valueOf(solicitudesOT.getNTelefono1()));
      }
      if (solicitudesOT.getNTelefono2().equals("0"))
        ((OCAPCuestionariosForm)form).setNTelefono2("-");
      else {
        ((OCAPCuestionariosForm)form).setNTelefono2(String.valueOf(solicitudesOT.getNTelefono2()));
      }
      ((OCAPCuestionariosForm)form).setBSexo(solicitudesOT.getBSexo());
      ((OCAPCuestionariosForm)form).setDDomicilio(solicitudesOT.getDDomicilio() == null ? "-" : solicitudesOT.getDDomicilio());
      ((OCAPCuestionariosForm)form).setDLocalidadUsu(solicitudesOT.getDLocalidadUsu() == null ? "-" : solicitudesOT.getDLocalidadUsu());
      ((OCAPCuestionariosForm)form).setCProvinciaUsu_id(solicitudesOT.getCProvinciaUsu_id());
      ((OCAPCuestionariosForm)form).setDProvinciaUsu(solicitudesOT.getDProvinciaUsu() == null ? "-" : solicitudesOT.getDProvinciaUsu());
      DecimalFormat formato = new DecimalFormat("00000");
      ((OCAPCuestionariosForm)form).setCPostalUsu(solicitudesOT.getCPostalUsu() == null ? "-" : formato.format(Long.parseLong(solicitudesOT.getCPostalUsu())));
      ((OCAPCuestionariosForm)form).setCJuridico(solicitudesOT.getCJuridico());
      ((OCAPCuestionariosForm)form).setDProcedimiento(solicitudesOT.getDProcedimiento());
      ((OCAPCuestionariosForm)form).setCSitAdministrativaAuxId(solicitudesOT.getCSitAdministrativaAuxId());
      ((OCAPCuestionariosForm)form).setDSitAdministrativaAuxOtros(solicitudesOT.getDSitAdministrativaAuxOtros());
      ((OCAPCuestionariosForm)form).setDEstatutarioActual_nombre(solicitudesOT.getDEstatutActual_nombre());
      ((OCAPCuestionariosForm)form).setDProfesionalActual_nombre(solicitudesOT.getDProfesionalActual_nombre());
      ((OCAPCuestionariosForm)form).setDEspecActual_nombre(solicitudesOT.getDEspecActual_nombre());
      ((OCAPCuestionariosForm)form).setCEspecActual_id(String.valueOf(solicitudesOT.getCEspecActual_id()));
      ((OCAPCuestionariosForm)form).setDEstatutario_nombre(solicitudesOT.getDEstatut_nombre());
      ((OCAPCuestionariosForm)form).setDProfesional_nombre(solicitudesOT.getDProfesional_nombre());
      ((OCAPCuestionariosForm)form).setDEspec_nombre(solicitudesOT.getDEspec_nombre());
      ((OCAPCuestionariosForm)form).setDProvincia(Cadenas.capitalizar(solicitudesOT.getDProvincia()));
      ((OCAPCuestionariosForm)form).setACiudad(Cadenas.capitalizar(solicitudesOT.getACiudad()));
      ((OCAPCuestionariosForm)form).setDTipogerencia_desc(solicitudesOT.getDTipogerencia_desc());
      ((OCAPCuestionariosForm)form).setDGerencia_nombre(solicitudesOT.getDGerencia_nombre());
      ((OCAPCuestionariosForm)form).setDCentrotrabajo_nombre(solicitudesOT.getDCentrotrabajo_nombre());
      ((OCAPCuestionariosForm)form).setDGrado_des(solicitudesOT.getDGrado_des());
      ((OCAPCuestionariosForm)form).setCConvocatoriaId(String.valueOf(solicitudesOT.getCConvocatoriaId()));
    }
    catch (Exception e)
    {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    } finally {
      if (ejb != null)
        ejb.cerrar();
      if (ejb_aut != null) {
        ejb_aut.cerrar();
      }
    }
    if ((errors == null) || (errors.isEmpty()))
    {
      return irExplicacionAreas(mapping, form, request, response);
    }

    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward irExplicacionAreas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPCuestionariosLN cuestionariosLN = null;
    OCAPItinerariosOT itinerarioOT = null;
    ArrayList listaAreas = null;
    try
    {
      OCAPConfigApp.logger.debug(getClass().getName() + " irExplicacionAreas: Inicio");

      JCYLUsuario jcylUsuario = (JCYLUsuario)request.getSession().getAttribute("JCYLUsuario");
      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      usuarioOT = usuariosLN.datosPersonalesUsuario(jcylUsuario.getUser().getC_usr_id(), 0L, jcylUsuario);
      if (usuarioOT == null) {
        return mapping.findForward("irNoExisteSolicitud");
      }

      if ((usuarioOT.getDEspec_nombre() != null) && (!"-".equals(usuarioOT.getDEspec_nombre().trim())))
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre() + ". " + usuarioOT.getDEspec_nombre());
      else {
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre());
      }
      OCAPExpedienteCarreraLN expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
      OCAPExpedientecarreraOT expedienteCarreraOT = expCarreraLN.buscarExpedienteCarreraPorUsuarioConvocatoria(usuarioOT.getCUsrId(), Long.valueOf(usuarioOT.getCConvocatoriaId()));
      if (expedienteCarreraOT.getBLopdCd() == null) {
        expedienteCarreraOT.setAModificadoPor(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
        expedienteCarreraOT.setBLopdCd(Constantes.SI);
        expedienteCarreraOT.setBVerificaDatosFaseIII(Constantes.SI);
        expCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);
      }

      OCAPItinerariosLN itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      Long cEspecId = null;
      if (usuarioOT.getCEspecId() != null) {
        cEspecId = Long.valueOf(usuarioOT.getCEspecId().intValue());
      }

      itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());
      ((OCAPCuestionariosForm)form).setDIntroduccion(itinerarioOT.getDIntroduccion());
      ((OCAPCuestionariosForm)form).setNCreditosNecesarios(String.valueOf(itinerarioOT.getNCreditosNecesarios()));
      DecimalFormat formato = new DecimalFormat("000000");
      ((OCAPCuestionariosForm)form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(usuarioOT.getCExpId().longValue()));
      ((OCAPCuestionariosForm)form).setDNombreItinerario(itinerarioOT.getDDescripcion());

      if (usuarioOT.getCConvocatoriaId() != 0L) {
        OCAPConvocatoriasLN convoLN = new OCAPConvocatoriasLN(jcylUsuario);
        OCAPConvocatoriasOT convoOT = convoLN.buscarOCAPConvocatorias(usuarioOT.getCConvocatoriaId());
        if (convoOT.getNDiasAutoCa() != 0L)
          ((OCAPCuestionariosForm)form).setNDiasEvaluacion(convoOT.getNDiasAutoCa());
        else ((OCAPCuestionariosForm)form).setNDiasEvaluacion(30); 
      } else { ((OCAPCuestionariosForm)form).setNDiasEvaluacion(30); }

      ((OCAPCuestionariosForm)form).setCGradoId(String.valueOf(usuarioOT.getCGrado_id()));
      ((OCAPCuestionariosForm)form).setDGrado_des(usuarioOT.getDGrado_des().toUpperCase());

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      listaAreas = cuestionariosLN.listadoOCAPCuestionariosPorItinerario(usuarioOT, jcylUsuario);
      ((OCAPCuestionariosForm)form).setListadoAreas(listaAreas);
      listaAreas = null;

      OCAPConfigApp.logger.debug(getClass().getName() + " irExplicacionAreas: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward("irExplicacionAreas");
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward irExplicacionAreasPruebas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    OCAPUsuariosOT usuarioOT = null;
    OCAPCuestionariosLN cuestionariosLN = null;

    OCAPItinerariosOT itinerarioOT = null;

    ArrayList listaAreas = null;
    try
    {
      OCAPConfigApp.logger.debug(getClass().getName() + " irExplicacionAreasPruebas: Inicio");

      JCYLUsuario jcylUsuario = (JCYLUsuario)request.getSession().getAttribute("JCYLUsuario");
      usuarioOT = (OCAPUsuariosOT)request.getSession().getAttribute("UsuarioPruebaItinerario");
      if (usuarioOT == null) {
        return mapping.findForward("irElegirItinerario");
      }
      if ((usuarioOT.getDEspec_nombre() != null) && (!"-".equals(usuarioOT.getDEspec_nombre().trim())))
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre() + ". " + usuarioOT.getDEspec_nombre());
      else {
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre());
      }

      OCAPItinerariosLN itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      Long cEspecId = null;
      if (usuarioOT.getCEspecId() != null) {
        cEspecId = new Long(usuarioOT.getCEspecId().intValue());
      }

      itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());
      ((OCAPCuestionariosForm)form).setDIntroduccion(itinerarioOT.getDIntroduccion());
      ((OCAPCuestionariosForm)form).setNCreditosNecesarios(itinerarioOT.getNCreditosNecesarios() + "");

      ((OCAPCuestionariosForm)form).setCodigoId("EPR000000");
      ((OCAPCuestionariosForm)form).setDNombreItinerario(itinerarioOT.getDDescripcion());

      ((OCAPCuestionariosForm)form).setNDiasEvaluacion(30);

      ((OCAPCuestionariosForm)form).setCGradoId(usuarioOT.getCGrado_id() + "");
      ((OCAPCuestionariosForm)form).setDGrado_des(usuarioOT.getDGrado_des().toUpperCase());

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      listaAreas = cuestionariosLN.listadoOCAPCuestionariosPorItinerario(usuarioOT, jcylUsuario);
      ((OCAPCuestionariosForm)form).setListadoAreas(listaAreas);
      listaAreas = null;

      OCAPConfigApp.logger.debug(getClass().getName() + " irExplicacionAreasPruebas: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward("irExplicacionAreas");
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward irInformacionArea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();
    String mensajeError = "Se ha producido un error";
    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPCuestionariosLN cuestionariosLN = null;

    OCAPAreasItinerariosOT areaOT = null;
    OCAPItinerariosOT itinerarioOT = null;
    OCAPItinerariosLN itinerariosLN = null;
    long cAreaId = 0L;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " irInformacionArea: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      String usuId = jcylUsuario.getUser().getC_usr_id();
      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);

      if (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())) {
        usuarioOT = (OCAPUsuariosOT)session.getAttribute("UsuarioPruebaItinerario");
      }
      if (usuarioOT == null) {
        if (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())) {
          return mapping.findForward("irElegirItinerario");
        }
        return mapping.findForward("irNoExisteSolicitud");
      }

      if ((usuarioOT.getDEspec_nombre() != null) && (!"-".equals(usuarioOT.getDEspec_nombre().trim())))
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre() + ". " + usuarioOT.getDEspec_nombre());
      else
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre());
      try {
        cAreaId = Long.parseLong(request.getParameter("cAreaId"));
      } catch (NumberFormatException e) {
        request.setAttribute("mensaje", mensajeError + "Area erronea. ");
      }
      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      areaOT = cuestionariosLN.informacionArea(cAreaId, jcylUsuario);
      if (areaOT != null) {
        ((OCAPCuestionariosForm)form).setDInformacionArea(areaOT.getDInformacion());
      }
      itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());
      DecimalFormat formato = new DecimalFormat("000000");
      ((OCAPCuestionariosForm)form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(usuarioOT.getCExpId().longValue()));
      ((OCAPCuestionariosForm)form).setDNombreItinerario(itinerarioOT.getDDescripcion());

      OCAPConfigApp.logger.info(getClass().getName() + " irInformacionArea: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward("irInformacionArea");
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward irListar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();
    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPCuestionariosLN cuestionariosLN = null;
    OCAPCuestionariosOT cuestionarioAuxOT = null;
    OCAPItinerariosLN itinerariosLN = null;
    OCAPItinerariosOT itinerarioOT = null;
    OCAPAreasItinerariosOT areaOT = null;
    ArrayList listaAreas = null;
    ArrayList listaCuestionarios = null;
    OCAPEstadosCuestionarioLN estCuestionarioLN = null;
    String mappingForward = "irListar";
    OCAPCreditosCuestionariosLN creCuestLN = null;
    OCAPRevisionesLN revisionLN = null;
    OCAPRevisionesOT revisionOT = null;
    OCAPConvocatoriasLN convocatoriaLN = null;
    OCAPConvocatoriasOT convocatoriaOT = null;
    OCAPExpedienteCarreraLN expCarreraLN = null;
    try
    {
      OCAPConfigApp.logger.debug(getClass().getName() + " irListar: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);

      if (request.getParameter("expId") != null) {
        session.setAttribute("expedienteId", request.getParameter("expId"));
      }

      if (session.getAttribute("expedienteId") != null) {
        String dniUsuario = expCarreraLN.buscarDNIUsuarioExpediente(Long.parseLong(session.getAttribute("expedienteId").toString()));
        usuarioOT = usuariosLN.datosPersonalesUsuario(dniUsuario, Long.parseLong(session.getAttribute("expedienteId").toString()), jcylUsuario);
      } else {
        usuarioOT = usuariosLN.datosPersonalesUsuario(jcylUsuario.getUser().getC_usr_id(), 0L, jcylUsuario);
      }

      if (usuarioOT == null)
      {
        return mapping.findForward("irNoExisteSolicitud");
      }
      ((OCAPCuestionariosForm)form).setCExpId(usuarioOT.getCExpId());
      ((OCAPCuestionariosForm)form).setCItinerarioId(String.valueOf(usuarioOT.getCItinerarioId()));
      OCAPExpedientecarreraOT expedienteCarreraOT = expCarreraLN.buscarOCAPExpedienteCarrera(usuarioOT.getCExpId());

      if ((Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol())) && (expedienteCarreraOT.getFInicioEvaluacion() == null)) {
        expedienteCarreraOT.setAModificadoPor(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
        expedienteCarreraOT.setFInicioEvaluacion(new Date());
        expCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);
      }

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      listaAreas = cuestionariosLN.listadoOCAPCuestionariosPorItinerario(usuarioOT, jcylUsuario);
      ((OCAPCuestionariosForm)form).setListadoAreas(listaAreas);

      boolean bFinalizadoUnoPorArea = true;
      float nCreditosItinerario = 0.0F;
      float nCreditosConseguidos = 0.0F;
      float nCreditosEvaluados = 0.0F;
      estCuestionarioLN = new OCAPEstadosCuestionarioLN(jcylUsuario);

      for (int i = 0; i < listaAreas.size(); i++) {
        areaOT = (OCAPAreasItinerariosOT)listaAreas.get(i);
        nCreditosItinerario += areaOT.getNCreditosArea();
        listaCuestionarios = areaOT.getListaCuestionarios();
        for (int j = 0; j < listaCuestionarios.size(); j++) {
          cuestionarioAuxOT = (OCAPCuestionariosOT)listaCuestionarios.get(j);
          nCreditosConseguidos += cuestionarioAuxOT.getNCreditosConseguidos();
          nCreditosEvaluados += cuestionarioAuxOT.getNCreditosEvaluados();
        }

        int numFin = estCuestionarioLN.contarCuestionariosFinalizadosArea(usuarioOT.getCExpId().longValue(), areaOT.getCAreaId().longValue());
        if (numFin == 0) {
          bFinalizadoUnoPorArea = false;
        }
      }

      itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());

      if ((usuarioOT.getDEspec_nombre() != null) && (!"-".equals(usuarioOT.getDEspec_nombre().trim())))
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre() + ". " + usuarioOT.getDEspec_nombre());
      else {
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre());
      }
      DecimalFormat formato = new DecimalFormat("000000");
      ((OCAPCuestionariosForm)form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(usuarioOT.getCExpId().longValue()));
      ((OCAPCuestionariosForm)form).setDNombreItinerario(itinerarioOT.getDDescripcion());
      ((OCAPCuestionariosForm)form).setNCreditosNecesarios(itinerarioOT.getNCreditosNecesarios() + "");

      if ((usuarioOT.getFFinCA() != null) && (usuarioOT.getFFinCA().before(new Date()))) {
        request.setAttribute("verFinProceso", Constantes.NO);

        request.setAttribute("verEncuesta", Constantes.NO);
      }
      else {
        request.setAttribute("verFinProceso", Constantes.SI);

        if (bFinalizadoUnoPorArea) ((OCAPCuestionariosForm)form).setBFinalizadoUnoPorArea(Constantes.SI); else {
          ((OCAPCuestionariosForm)form).setBFinalizadoUnoPorArea(Constantes.NO);
        }
      }
      ((OCAPCuestionariosForm)form).setNCreditosPosiblesItinerario(nCreditosItinerario + "");
      nCreditosConseguidos = (float)(Math.rint(nCreditosConseguidos * 100.0F) / 100.0D);
      ((OCAPCuestionariosForm)form).setNCreditosItinerario(nCreditosConseguidos + "");
      nCreditosEvaluados = (float)(Math.rint(nCreditosEvaluados * 100.0F) / 100.0D);
      ((OCAPCuestionariosForm)form).setNCreditosEvaluados(nCreditosEvaluados + "");

      session.setAttribute(Constantes.COMBO_CUESTIONARIOS, new ArrayList());

      if (!Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol())) {
        mappingForward = "irListarEvaluador";
        if ((request.getParameter("tipo") != null) && (Constantes.INF_LISTADO_FASE_III.equals(request.getParameter("tipo")))) {
          session.setAttribute("cCompfqsIdS", request.getParameter("cCompfqsIdS"));
          if (Constantes.OCAP_CTE.equals(jcylUsuario.getUser().getRol())) {
            session.setAttribute("codigo", request.getParameter("codigo"));
            session.setAttribute("cte", request.getParameter("cte"));
            session.setAttribute("opcion", request.getParameter("opcion"));
          }
        }

        if (request.getParameter("bSegundaEvaluacion") != null)
          session.setAttribute("bSegundaEvaluacion", request.getParameter("bSegundaEvaluacion"));
        if (request.getParameter("cDudaId") != null)
          session.setAttribute("cDudaId", request.getParameter("cDudaId"));
        if (expedienteCarreraOT.getFInformeEval() != null) {
          ((OCAPCuestionariosForm)form).setFEvaluacion(DateUtil.convertDateToString(expedienteCarreraOT.getFInformeEval()));
        }

        OCAPDocumentosLN documentosLN = new OCAPDocumentosLN();
        OCAPDocumentosOT documentosOT = new OCAPDocumentosOT();
        documentosOT = documentosLN.buscarDocumentoEvidencia(expedienteCarreraOT.getCExpId().longValue());
        if ((documentosOT != null) && (documentosOT.getCDocumento_id() != 0L))
        {
          session.setAttribute("tieneDocumentoEvidencia", Constantes.SI);
        }
        else session.setAttribute("tieneDocumentoEvidencia", Constantes.NO);

      }
      else
      {
        convocatoriaLN = new OCAPConvocatoriasLN(jcylUsuario);
        convocatoriaOT = convocatoriaLN.buscarOCAPConvocatorias(expedienteCarreraOT.getCConvocatoriaId());
        if ((convocatoriaOT.getFRecGrado() != null) && (DateUtil.convertStringToDate(convocatoriaOT.getFRecGrado()).before(new Date())))
          session.setAttribute("verDefinitivos", Constantes.SI);
        else {
          session.setAttribute("verDefinitivos", Constantes.NO);
        }
      }

      creCuestLN = new OCAPCreditosCuestionariosLN(jcylUsuario);
      ((OCAPCuestionariosForm)form).setContadorAuditorias(creCuestLN.cuentaSegundasAuditorias(usuarioOT.getCExpId().longValue()));

      revisionLN = new OCAPRevisionesLN(jcylUsuario);
      revisionOT = revisionLN.buscarOCAPRevisiones(usuarioOT.getCExpId().longValue());
      if ((revisionOT != null) && (revisionOT.getFAuditoriaPropuesta() != null)) {
        ((OCAPCuestionariosForm)form).setFAuditoriaPropuesta(DateUtil.convertDateToString(revisionOT.getFAuditoriaPropuesta()));
      }
      OCAPConfigApp.logger.debug(getClass().getName() + " irListar: Fin");
    }
    catch (Exception e)
    {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(mappingForward);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward irListarPruebas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    OCAPUsuariosOT usuarioOT = null;
    OCAPCuestionariosLN cuestionariosLN = null;

    OCAPCuestionariosOT cuestionarioAuxOT = null;
    OCAPItinerariosLN itinerariosLN = null;
    OCAPItinerariosOT itinerarioOT = null;
    OCAPAreasItinerariosOT areaOT = null;
    ArrayList listaAreas = null;
    ArrayList listaCuestionarios = null;
    OCAPEstadosCuestionarioLN estCuestionarioLN = null;
    String mappingForward = "irListar";
    try
    {
      OCAPConfigApp.logger.info("Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      usuarioOT = (OCAPUsuariosOT)session.getAttribute("UsuarioPruebaItinerario");
      if (usuarioOT == null) {
        return mapping.findForward("irElegirItinerario");
      }
      ((OCAPCuestionariosForm)form).setCExpId(usuarioOT.getCExpId());

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      listaAreas = cuestionariosLN.listadoOCAPCuestionariosPorItinerario(usuarioOT, jcylUsuario);
      ((OCAPCuestionariosForm)form).setListadoAreas(listaAreas);

      boolean bFinalizadoUnoPorArea = true;
      float nCreditosItinerario = 0.0F;
      float nCreditosConseguidos = 0.0F;
      float nCreditosEvaluados = 0.0F;
      estCuestionarioLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
      for (int i = 0; i < listaAreas.size(); i++) {
        areaOT = (OCAPAreasItinerariosOT)listaAreas.get(i);
        nCreditosItinerario += areaOT.getNCreditosArea();
        listaCuestionarios = areaOT.getListaCuestionarios();
        for (int j = 0; j < listaCuestionarios.size(); j++) {
          cuestionarioAuxOT = (OCAPCuestionariosOT)listaCuestionarios.get(j);
          nCreditosConseguidos += cuestionarioAuxOT.getNCreditosConseguidos();
          nCreditosEvaluados += cuestionarioAuxOT.getNCreditosEvaluados();
        }

        int numFin = estCuestionarioLN.contarCuestionariosFinalizadosArea(usuarioOT.getCExpId().longValue(), areaOT.getCAreaId().longValue());
        if (numFin == 0) {
          bFinalizadoUnoPorArea = false;
        }
      }

      itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());

      if ((usuarioOT.getDEspec_nombre() != null) && (!"-".equals(usuarioOT.getDEspec_nombre().trim())))
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre() + ". " + usuarioOT.getDEspec_nombre());
      else {
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre());
      }
      ((OCAPCuestionariosForm)form).setCodigoId("EPR000000");

      ((OCAPCuestionariosForm)form).setDNombreItinerario(itinerarioOT.getDDescripcion());
      ((OCAPCuestionariosForm)form).setNCreditosNecesarios(itinerarioOT.getNCreditosNecesarios() + "");

      request.setAttribute("verFinProceso", Constantes.NO);
      request.setAttribute("verEncuesta", Constantes.NO);
      ((OCAPCuestionariosForm)form).setBFinalizadoUnoPorArea(Constantes.NO);

      ((OCAPCuestionariosForm)form).setNCreditosPosiblesItinerario(nCreditosItinerario + "");
      nCreditosConseguidos = (float)(Math.rint(nCreditosConseguidos * 100.0F) / 100.0D);
      ((OCAPCuestionariosForm)form).setNCreditosItinerario(nCreditosConseguidos + "");
      nCreditosEvaluados = (float)(Math.rint(nCreditosEvaluados * 100.0F) / 100.0D);
      ((OCAPCuestionariosForm)form).setNCreditosEvaluados(nCreditosEvaluados + "");

      session.setAttribute(Constantes.COMBO_CUESTIONARIOS, new ArrayList());

      session.setAttribute("verDefinitivos", Constantes.NO);

      OCAPConfigApp.logger.info("Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(mappingForward);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward generarInforme(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPCuestionariosLN cuestionariosLN = null;

    OCAPCuestionariosOT cuestionarioAuxOT = null;
    OCAPItinerariosLN itinerariosLN = null;
    OCAPItinerariosOT itinerarioOT = null;
    OCAPAreasItinerariosOT areaOT = null;
    ArrayList listaAreas = null;
    ArrayList listaCuestionarios = null;
    OCAPEstadosCuestionarioLN estCuestionarioLN = null;
    OCAPSolicitudesOT solicitudesOT = null;
    OCAPSolicitudesLN solicitudesLN = null;
    String mappingForward = "irGenerarInforme";
    OCAPEvaluadoresLN evaluadoresLN = null;
    OCAPComponentesfqsOT compFqsOT = null;
    OCAPCreditosCuestionariosLN credCuestLN = null;
    OCAPCreditosCuestionariosOT credCuestOT = null;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " generarInforme: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      if (request.getParameter("expId") != null) {
        session.setAttribute("expedienteId", request.getParameter("expId"));
      }
      if (session.getAttribute("expedienteId") != null) {
        solicitudesOT = new OCAPSolicitudesOT();
        solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
        solicitudesOT.setCExp_id(Long.parseLong(session.getAttribute("expedienteId").toString()));
        solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);
        usuarioOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(), solicitudesOT.getCExp_id(), jcylUsuario);
      } else {
        String usuId = jcylUsuario.getUser().getC_usr_id();
        usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);
      }

      if (usuarioOT == null)
      {
        return mapping.findForward("irNoExisteSolicitud");
      }

      long codigoEvaluador = 0L;
      if (Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol())) {
        evaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
        compFqsOT = evaluadoresLN.buscarOCAPEvaluadoresDNI(jcylUsuario.getUser().getC_usr_id(), usuarioOT.getCItinerarioId());
        ((OCAPCuestionariosForm)form).setDNombreEvaluador(compFqsOT.getDNombre());
        ((OCAPCuestionariosForm)form).setDApellidosEvaluador(compFqsOT.getDApellidos());
        ((OCAPCuestionariosForm)form).setCDniEvaluador(compFqsOT.getCDni());
        ((OCAPCuestionariosForm)form).setNTelefonoEvaluador(compFqsOT.getNTelefono1());
        ((OCAPCuestionariosForm)form).setACorreoelectronicoEvaluador(compFqsOT.getACorreoelectronico());
        ((OCAPCuestionariosForm)form).setDCategoriaEspecialidadEvaluador(compFqsOT.getDProfesionalNombre() + (compFqsOT.getDEspecNombre() == null ? "" : new StringBuilder().append(" / ").append(compFqsOT.getDEspecNombre()).toString()));
        codigoEvaluador = compFqsOT.getCCompfqsId();
      } else {
        codigoEvaluador = solicitudesOT.getCCompfqs_id();
      }

      ((OCAPCuestionariosForm)form).setDCategoriaEspecialidadEvaluado(usuarioOT.getDProfesional_nombre() + (usuarioOT.getDEspec_nombre() == null ? "" : new StringBuilder().append(" / ").append(usuarioOT.getDEspec_nombre()).toString()));
      ((OCAPCuestionariosForm)form).setDGrado_des(usuarioOT.getDGrado_des());
      ((OCAPCuestionariosForm)form).setCExpId(usuarioOT.getCExpId());
      ((OCAPCuestionariosForm)form).setCItinerarioId(String.valueOf(usuarioOT.getCItinerarioId()));

      DecimalFormat formato = new DecimalFormat("000000");
      ((OCAPCuestionariosForm)form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(usuarioOT.getCExpId().longValue()));

      ((OCAPCuestionariosForm)form).setCodigoEvaluadorId("REX" + formato.format(codigoEvaluador));

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      listaAreas = cuestionariosLN.listadoOCAPCuestionariosPorItinerario(usuarioOT, jcylUsuario);

      boolean bFinalizadoUnoPorArea = true;
      float nCreditosItinerario = 0.0F;
      float nCreditosConseguidos = 0.0F;
      float nCreditosEvaluados = 0.0F;
      estCuestionarioLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
      credCuestLN = new OCAPCreditosCuestionariosLN(jcylUsuario);
      ArrayList copiaListaAreas = new ArrayList();
      ArrayList listaAuditorias = new ArrayList();
      for (int i = 0; i < listaAreas.size(); i++) {
        areaOT = (OCAPAreasItinerariosOT)listaAreas.get(i);
        nCreditosItinerario += areaOT.getNCreditosArea();
        listaCuestionarios = areaOT.getListaCuestionarios();
        ArrayList copiaListaCuestionarios = new ArrayList();
        for (int j = 0; j < listaCuestionarios.size(); j++) {
          cuestionarioAuxOT = (OCAPCuestionariosOT)listaCuestionarios.get(j);
          nCreditosConseguidos += cuestionarioAuxOT.getNCreditosConseguidos();
          nCreditosEvaluados += cuestionarioAuxOT.getNCreditosEvaluados();
          ArrayList listaCreditos = credCuestLN.buscarOCAPCreditosCuestionario(cuestionarioAuxOT.getCCuestionarioId(), 1, usuarioOT.getCExpId().longValue());
          if ((listaCreditos != null) && (listaCreditos.size() > 0)) {
            credCuestOT = (OCAPCreditosCuestionariosOT)listaCreditos.get(0);
            cuestionarioAuxOT.setBAcuerdo(credCuestOT.getBAcuerdo());
            cuestionarioAuxOT.setBProposicion(credCuestOT.getBProposicion());
            cuestionarioAuxOT.setARazon(credCuestOT.getARazon());
            cuestionarioAuxOT.setAComentarios(credCuestOT.getAComentarios());
            if ("A".equals(credCuestOT.getBProposicion()))
              listaAuditorias.add(cuestionarioAuxOT);
          }
          copiaListaCuestionarios.add(cuestionarioAuxOT);
        }
        areaOT.setListaCuestionarios(copiaListaCuestionarios);
        copiaListaAreas.add(areaOT);

        int numFin = estCuestionarioLN.contarCuestionariosFinalizadosArea(usuarioOT.getCExpId().longValue(), areaOT.getCAreaId().longValue());
        if (numFin == 0)
          bFinalizadoUnoPorArea = false;
      }
      ((OCAPCuestionariosForm)form).setListadoAreas(copiaListaAreas);
      ((OCAPCuestionariosForm)form).setListadoAuditorias(listaAuditorias);

      itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());
      ((OCAPCuestionariosForm)form).setDNombreItinerario(itinerarioOT.getDDescripcion());

      ((OCAPCuestionariosForm)form).setNCreditosPosiblesItinerario(nCreditosItinerario + "");
      nCreditosConseguidos = (float)(Math.rint(nCreditosConseguidos * 100.0F) / 100.0D);
      ((OCAPCuestionariosForm)form).setNCreditosItinerario(nCreditosConseguidos + "");
      nCreditosEvaluados = (float)(Math.rint(nCreditosEvaluados * 100.0F) / 100.0D);
      ((OCAPCuestionariosForm)form).setNCreditosEvaluados(nCreditosEvaluados + "");

      ((OCAPCuestionariosForm)form).setNCreditosNecesarios(itinerarioOT.getNCreditosNecesarios() + "");

      session.setAttribute(Constantes.COMBO_CUESTIONARIOS, new ArrayList());

      if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {
        mappingForward = "irGenerarInformeSacyl";
      }
      if (usuarioOT.getFInformeEval() != null) {
        ((OCAPCuestionariosForm)form).setFEvaluacion(DateUtil.convertDateToString(usuarioOT.getFInformeEval()));
        ((OCAPCuestionariosForm)form).setAEspecificaciones(usuarioOT.getAEspecificacionesEval());
        request.setAttribute("informeGenerado", Constantes.SI);
      } else {
        ((OCAPCuestionariosForm)form).setFEvaluacion(DateUtil.convertDateToString(new Date()));
        request.setAttribute("informeGenerado", Constantes.NO);
      }

      request.setAttribute("cCompfqsIdS", request.getParameter("cCompfqsIdS"));

      OCAPConfigApp.logger.info(getClass().getName() + " generarInforme: Fin");
      
      //OCAP-1375
      if (Constantes.OCAP_DIRECCION.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_CTE.equals(jcylUsuario.getUser().getRol()) || Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol()) ) {
    	  datosPersonalesDireccion(form, solicitudesOT.getCSolicitudId(), solicitudesLN);
      }
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(mappingForward);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  private void datosPersonalesDireccion(ActionForm form, long cSolicitudId, OCAPSolicitudesLN solicitudesLN) {
	OCAPSolicitudesOT solicitud = solicitudesLN.buscarSolicitud(cSolicitudId);
	if (solicitud != null) {
		((OCAPCuestionariosForm)form).setNombreApellidos(solicitud.getDNombre()+" "+solicitud.getDApellido1());
		((OCAPCuestionariosForm)form).setDni(solicitud.getCDni());
	}
	
}

public ActionForward generarInforme2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;

    OCAPItinerariosLN itinerariosLN = null;
    OCAPItinerariosOT itinerarioOT = null;

    OCAPSolicitudesOT solicitudesOT = null;
    OCAPSolicitudesLN solicitudesLN = null;
    String mappingForward = "irGenerarInforme2";
    OCAPEvaluadoresLN evaluadoresLN = null;
    OCAPComponentesfqsOT compFqsOT = null;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " generarInforme2: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      if (request.getParameter("expId") != null) {
        session.setAttribute("expedienteId", request.getParameter("expId"));
      }
      if (session.getAttribute("expedienteId") != null) {
        solicitudesOT = new OCAPSolicitudesOT();
        solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
        solicitudesOT.setCExp_id(Long.parseLong(session.getAttribute("expedienteId").toString()));
        solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);
        usuarioOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(), solicitudesOT.getCExp_id(), jcylUsuario);
      } else {
        String usuId = jcylUsuario.getUser().getC_usr_id();
        usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);
      }

      if (usuarioOT == null)
      {
        return mapping.findForward("irNoExisteSolicitud");
      }

      long codigoEvaluador = 0L;
      evaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
      if (Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol())) {
        compFqsOT = evaluadoresLN.buscarOCAPEvaluadoresDNI(jcylUsuario.getUser().getC_usr_id(), usuarioOT.getCItinerarioId());
        ((OCAPCuestionariosForm)form).setDNombreEvaluador(compFqsOT.getDNombre());
        ((OCAPCuestionariosForm)form).setDApellidosEvaluador(compFqsOT.getDApellidos());
        ((OCAPCuestionariosForm)form).setCDniEvaluador(compFqsOT.getCDni());
        ((OCAPCuestionariosForm)form).setNTelefonoEvaluador(compFqsOT.getNTelefono1());
        ((OCAPCuestionariosForm)form).setACorreoelectronicoEvaluador(compFqsOT.getACorreoelectronico());
        ((OCAPCuestionariosForm)form).setDCategoriaEspecialidadEvaluador(compFqsOT.getDProfesionalNombre() + (compFqsOT.getDEspecNombre() == null ? "" : new StringBuilder().append(" / ").append(compFqsOT.getDEspecNombre()).toString()));
        codigoEvaluador = compFqsOT.getCCompfqsId();
      } else {
        codigoEvaluador = solicitudesOT.getCCompfqs_id2();
        compFqsOT = evaluadoresLN.buscarOCAPEvaluadores(codigoEvaluador);
        ((OCAPCuestionariosForm)form).setDNombreEvaluador(compFqsOT.getDNombre());
        ((OCAPCuestionariosForm)form).setDApellidosEvaluador(compFqsOT.getDApellidos());
        ((OCAPCuestionariosForm)form).setCDniEvaluador(compFqsOT.getCDni());
        ((OCAPCuestionariosForm)form).setNTelefonoEvaluador(compFqsOT.getNTelefono1());
        ((OCAPCuestionariosForm)form).setACorreoelectronicoEvaluador(compFqsOT.getACorreoelectronico());
        compFqsOT = evaluadoresLN.buscarOCAPEvaluadoresCategoria(compFqsOT, usuarioOT.getCItinerarioId());
        ((OCAPCuestionariosForm)form).setDCategoriaEspecialidadEvaluador(compFqsOT.getDProfesionalNombre() + (compFqsOT.getDEspecNombre() == null ? "" : new StringBuilder().append(" / ").append(compFqsOT.getDEspecNombre()).toString()));
      }

      ((OCAPCuestionariosForm)form).setDCategoriaEspecialidadEvaluado(usuarioOT.getDProfesional_nombre() + (usuarioOT.getDEspec_nombre() == null ? "" : new StringBuilder().append(" / ").append(usuarioOT.getDEspec_nombre()).toString()));
      ((OCAPCuestionariosForm)form).setDGrado_des(usuarioOT.getDGrado_des());
      ((OCAPCuestionariosForm)form).setCExpId(usuarioOT.getCExpId());

      DecimalFormat formato = new DecimalFormat("000000");
      ((OCAPCuestionariosForm)form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(usuarioOT.getCExpId().longValue()));

      ((OCAPCuestionariosForm)form).setCodigoEvaluadorId("REX" + formato.format(codigoEvaluador));

      itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());
      ((OCAPCuestionariosForm)form).setDNombreItinerario(itinerarioOT.getDDescripcion());

      session.setAttribute(Constantes.COMBO_CUESTIONARIOS, new ArrayList());

      if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {
        mappingForward = "irGenerarInformeSacyl2";
      }

      OCAPRevisionesLN revisionLN = new OCAPRevisionesLN(jcylUsuario);
      OCAPRevisionesOT revisionOT = revisionLN.buscarOCAPRevisiones(usuarioOT.getCExpId().longValue());
      if (revisionOT.getFInicioEval() != null)
        ((OCAPCuestionariosForm)form).setFInicioEvaluacion(DateUtil.convertDateToString(revisionOT.getFInicioEval()));
      else
        ((OCAPCuestionariosForm)form).setFInicioEvaluacion(DateUtil.convertDateToString(new Date()));
      if (revisionOT.getFInforme() != null) {
        ((OCAPCuestionariosForm)form).setFEvaluacion(DateUtil.convertDateToString(revisionOT.getFInforme()));
        ((OCAPCuestionariosForm)form).setBAuditoria(revisionOT.getBAuditoria());
        ((OCAPCuestionariosForm)form).setAMotivoAuditoria(Cadenas.formatearTexto(revisionOT.getAMotivoAuditoria()));
        ((OCAPCuestionariosForm)form).setALugarRealizacion(Cadenas.formatearTexto(revisionOT.getALugarRealizacion() == null ? "&nbsp;" : revisionOT.getALugarRealizacion()));
        if (revisionOT.getFAuditoria() != null)
          ((OCAPCuestionariosForm)form).setFAuditoria(DateUtil.convertDateToString(revisionOT.getFAuditoria()));
        else
          ((OCAPCuestionariosForm)form).setFAuditoria(" ");
        ((OCAPCuestionariosForm)form).setASuperior(Cadenas.formatearTexto(revisionOT.getASuperior() == null ? "&nbsp;" : revisionOT.getASuperior()));
        ((OCAPCuestionariosForm)form).setAHallazgos(Cadenas.formatearTexto(revisionOT.getAHallazgos() == null ? "&nbsp;" : revisionOT.getAHallazgos()));
        ((OCAPCuestionariosForm)form).setBCumplimiento(revisionOT.getBCumplimiento());
        ((OCAPCuestionariosForm)form).setBCategorizacion(revisionOT.getBCategorizacion());
        ((OCAPCuestionariosForm)form).setARecomendaciones(Cadenas.formatearTexto(revisionOT.getARecomendaciones()));
        ((OCAPCuestionariosForm)form).setAConclusiones(Cadenas.formatearTexto(revisionOT.getAConclusiones()));
        ((OCAPCuestionariosForm)form).setBAutoEvaluacion(revisionOT.getBAutoEvaluacion());
        ((OCAPCuestionariosForm)form).setB2Evaluacion(revisionOT.getB2Evaluacion());
        ((OCAPCuestionariosForm)form).setBAnalisis(revisionOT.getBAnalisis());
        ((OCAPCuestionariosForm)form).setAResultados(Cadenas.formatearTexto(revisionOT.getAResultados() == null ? "&nbsp;" : revisionOT.getAResultados()));
        request.setAttribute("informeGenerado", Constantes.SI);
      } else {
        ((OCAPCuestionariosForm)form).setFEvaluacion(DateUtil.convertDateToString(new Date()));
        if (revisionOT.getFAuditoriaPropuesta() != null) {
          ((OCAPCuestionariosForm)form).setFAuditoria(DateUtil.convertDateToString(revisionOT.getFAuditoriaPropuesta()));
        }
        if ((usuarioOT.getCProfesionalId().intValue() == 47) || (usuarioOT.getCProfesionalId().intValue() == 32))
          ((OCAPCuestionariosForm)form).setBAuditoria(Constantes.SI);
        else
          ((OCAPCuestionariosForm)form).setBAuditoria(Constantes.NO);
        request.setAttribute("informeGenerado", Constantes.NO);
      }

      request.setAttribute("cCompfqsIdS", request.getParameter("cCompfqsIdS"));

      OCAPConfigApp.logger.info(getClass().getName() + " generarInforme2: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(mappingForward);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward generarInformeAuditoria(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;

    OCAPItinerariosLN itinerariosLN = null;
    OCAPItinerariosOT itinerarioOT = null;

    OCAPSolicitudesOT solicitudesOT = null;
    OCAPSolicitudesLN solicitudesLN = null;
    String mappingForward = "irGenerarAuditoria";

    OCAPCreditosCuestionariosLN credCuestLN = null;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " generarInformeAuditoria: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      if (request.getParameter("expId") != null) {
        session.setAttribute("expedienteId", request.getParameter("expId"));
      }
      if (session.getAttribute("expedienteId") != null) {
        solicitudesOT = new OCAPSolicitudesOT();
        solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
        solicitudesOT.setCExp_id(Long.parseLong(session.getAttribute("expedienteId").toString()));
        solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);
        usuarioOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(), solicitudesOT.getCExp_id(), jcylUsuario);
      } else {
        String usuId = jcylUsuario.getUser().getC_usr_id();
        usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);
      }

      if (usuarioOT == null)
      {
        return mapping.findForward("irNoExisteSolicitud");
      }

      ((OCAPCuestionariosForm)form).setDCategoriaEspecialidadEvaluado(usuarioOT.getDProfesional_nombre() + (usuarioOT.getDEspec_nombre() == null ? "" : new StringBuilder().append(" / ").append(usuarioOT.getDEspec_nombre()).toString()));
      ((OCAPCuestionariosForm)form).setDGrado_des(usuarioOT.getDGrado_des());
      ((OCAPCuestionariosForm)form).setCExpId(usuarioOT.getCExpId());
      ((OCAPCuestionariosForm)form).setDCentrotrabajo_nombre(usuarioOT.getDCentrotrabajo_nombre());

      DecimalFormat formato = new DecimalFormat("000000");
      ((OCAPCuestionariosForm)form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(usuarioOT.getCExpId().longValue()));

      itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());
      ((OCAPCuestionariosForm)form).setDNombreItinerario(itinerarioOT.getDDescripcion());

      session.setAttribute(Constantes.COMBO_CUESTIONARIOS, new ArrayList());

      OCAPRevisionesLN revisionLN = new OCAPRevisionesLN(jcylUsuario);
      OCAPRevisionesOT revisionOT = revisionLN.buscarOCAPRevisiones(usuarioOT.getCExpId().longValue());
      if ((revisionOT != null) && (revisionOT.getFAuditoriaPropuesta() != null)) {
        ((OCAPCuestionariosForm)form).setFAuditoriaPropuesta(DateUtil.convertDateToStringLarga(Constantes.FECHA_COMPLETA_HHMM, revisionOT.getFAuditoriaPropuesta()));
        request.setAttribute("informeGenerado", Constantes.SI);
      } else {
        request.setAttribute("informeGenerado", Constantes.NO);
      }

      credCuestLN = new OCAPCreditosCuestionariosLN(jcylUsuario);
      ((OCAPCuestionariosForm)form).setListadoAuditorias(credCuestLN.buscarOCAPAuditoriasPorExpId(usuarioOT.getCExpId().longValue()));

      request.setAttribute("cCompfqsIdS", request.getParameter("cCompfqsIdS"));

      OCAPConfigApp.logger.info(getClass().getName() + " generarInformeAuditoria: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(mappingForward);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward generarInforme2PDF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;

    OCAPSolicitudesOT solicitudesOT = null;
    OCAPSolicitudesLN solicitudesLN = null;
    String mappingForward = "irGenerarInforme2";
    OCAPEvaluadoresLN evaluadoresLN = null;
    OCAPComponentesfqsOT compFqsOT = null;

    String nombreReportPadre = "";
    Hashtable parametros = null;
    JasperReport jasperReport = null;
    JasperPrint jasperPrint = null;
    DecimalFormat formato = new DecimalFormat("000000");
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " generarInforme2: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      if (request.getParameter("expId") != null) {
        solicitudesOT = new OCAPSolicitudesOT();
        solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
        solicitudesOT.setCExp_id(Long.parseLong(request.getParameter("expId").toString()));
        solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);
        usuariosLN = new OCAPUsuariosLN(jcylUsuario);
        usuarioOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(), solicitudesOT.getCExp_id(), jcylUsuario);
      } else {
        return mapping.findForward("fallo");
      }

      if (usuarioOT == null)
      {
        return mapping.findForward("irNoExisteSolicitud");
      }

      long codigoEvaluador = 0L;
      evaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
      codigoEvaluador = solicitudesOT.getCCompfqs_id2();
      compFqsOT = evaluadoresLN.buscarOCAPEvaluadores(codigoEvaluador);
      compFqsOT = evaluadoresLN.buscarOCAPEvaluadoresCategoria(compFqsOT, usuarioOT.getCItinerarioId());

      OCAPRevisionesLN revisionLN = new OCAPRevisionesLN(jcylUsuario);
      OCAPRevisionesOT revisionOT = revisionLN.buscarOCAPRevisiones(usuarioOT.getCExpId().longValue());
      if ((revisionOT != null) && (revisionOT.getFInforme() != null)) {
        revisionOT.setDNombreEvaluador(compFqsOT.getDNombre() + " " + compFqsOT.getDApellidos());
        revisionOT.setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(usuarioOT.getCExpId().longValue()));

        if (Constantes.SI.equals(revisionOT.getBAuditoria()))
          revisionOT.setBAuditoriaAE("X");
        else {
          revisionOT.setBAuditoriaAA("X");
        }
        if (Constantes.SI.equals(revisionOT.getBCumplimiento()))
          revisionOT.setBCumplimientoY("X");
        else {
          revisionOT.setBCumplimientoN("X");
        }
        if (Constantes.NO.equals(revisionOT.getBCategorizacion()))
          revisionOT.setBCategorizacionN("X");
        if ("D".equals(revisionOT.getBCategorizacion()))
          revisionOT.setBCategorizacionD("X");
        if ("O".equals(revisionOT.getBCategorizacion())) {
          revisionOT.setBCategorizacionO("X");
        }
        if (revisionOT.getFAuditoria() != null)
          revisionOT.setFAuditoriaPDF(DateUtil.convertDateToString(revisionOT.getFAuditoria()));
        else
          revisionOT.setFAuditoriaPDF("");
        if (revisionOT.getALugarRealizacion() == null)
          revisionOT.setALugarRealizacion("");
        if (revisionOT.getASuperior() == null) {
          revisionOT.setASuperior("");
        }
        revisionOT.setFInformePDF(DateUtil.convertDateToStringLargaMesMayus(Constantes.FECHA_LETRA_D, revisionOT.getFInforme()));

        String pathBase = this.servlet.getServletConfig().getServletContext().getRealPath("");
        nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator + "informeEval2" + ".jasper";

        OCAPConfigApp.logger.info(getClass().getName() + " generarInforme2: Cargar report padre");
        File fichReportPadre = new File(nombreReportPadre);
        jasperReport = (JasperReport)JRLoader.loadObject(fichReportPadre);

        parametros = new Hashtable();
        parametros.put("ruta", pathBase);

        parametros.put("datosDocu", revisionOT);

        jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

        byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
        OutputStream out = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment; filename=\"informe" + revisionOT.getCodigoId() + ".pdf\"");
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);
        out.write(bytes);
        out.flush();
        out.close();
      }
      else {
        return mapping.findForward("fallo");
      }
      OCAPConfigApp.logger.info(getClass().getName() + " generarInforme2PDF: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(mappingForward);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward generarInformeCTE(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPCuestionariosLN cuestionariosLN = null;

    OCAPCuestionariosOT cuestionarioAuxOT = null;
    OCAPItinerariosLN itinerariosLN = null;
    OCAPItinerariosOT itinerarioOT = null;
    OCAPAreasItinerariosOT areaOT = null;
    ArrayList listaAreas = null;
    ArrayList listaCuestionarios = null;
    OCAPEstadosCuestionarioLN estCuestionarioLN = null;
    OCAPSolicitudesOT solicitudesOT = null;
    OCAPSolicitudesLN solicitudesLN = null;
    String mappingForward = "irGenerarInformeCTE";
    OCAPEvaluadoresLN evaluadoresLN = null;
    OCAPComponentesfqsOT compFqsOT = null;
    OCAPCreditosCuestionariosLN credCuestLN = null;
    OCAPCreditosCuestionariosOT credCuestOT = null;
    OCAPComponentesCtesLN compCteLN = null;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " generarInformeCTE: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      if (request.getParameter("expId") != null) {
        session.setAttribute("expedienteId", request.getParameter("expId"));
      }
      if (session.getAttribute("expedienteId") != null) {
        solicitudesOT = new OCAPSolicitudesOT();
        solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
        solicitudesOT.setCExp_id(Long.parseLong(session.getAttribute("expedienteId").toString()));
        solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);
        usuarioOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(), solicitudesOT.getCExp_id(), jcylUsuario);
      } else {
        String usuId = jcylUsuario.getUser().getC_usr_id();
        usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);
      }

      if (usuarioOT == null)
      {
        return mapping.findForward("irNoExisteSolicitud");
      }
      ((OCAPCuestionariosForm)form).setDApellido1(usuarioOT.getDApellido1());
      ((OCAPCuestionariosForm)form).setDNombreUsuario(usuarioOT.getDNombre());
      ((OCAPCuestionariosForm)form).setCDni(usuarioOT.getCDni());

      OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
      OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
      OCAPGerenciasOT gerencia = gerenciasLN.buscarOCAPGerencias(usuarioOT.getCGerenciaId().longValue());
      OCAPProvinciasOT provinciasOT = provinciasLN.buscarProvincia(gerencia.getCProvinciaId());
      ((OCAPCuestionariosForm)form).setDProvincia(provinciasOT.getDProvincia());

      long codigoEvaluador = 0L;
      if (Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol())) {
        evaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
        compFqsOT = evaluadoresLN.buscarOCAPEvaluadoresDNI(jcylUsuario.getUser().getC_usr_id(), usuarioOT.getCItinerarioId());
        ((OCAPCuestionariosForm)form).setDNombreEvaluador(compFqsOT.getDNombre());
        ((OCAPCuestionariosForm)form).setDApellidosEvaluador(compFqsOT.getDApellidos());
        ((OCAPCuestionariosForm)form).setCDniEvaluador(compFqsOT.getCDni());
        ((OCAPCuestionariosForm)form).setNTelefonoEvaluador(compFqsOT.getNTelefono1());
        ((OCAPCuestionariosForm)form).setACorreoelectronicoEvaluador(compFqsOT.getACorreoelectronico());
        ((OCAPCuestionariosForm)form).setDCategoriaEspecialidadEvaluador(compFqsOT.getDProfesionalNombre() + (compFqsOT.getDEspecNombre() == null ? "" : new StringBuilder().append(" / ").append(compFqsOT.getDEspecNombre()).toString()));
        codigoEvaluador = compFqsOT.getCCompfqsId();
      } else {
        codigoEvaluador = solicitudesOT.getCCompfqs_id();
      }

      ((OCAPCuestionariosForm)form).setDCategoriaEspecialidadEvaluado(usuarioOT.getDProfesional_nombre() + (usuarioOT.getDEspec_nombre() == null ? "" : new StringBuilder().append(" / ").append(usuarioOT.getDEspec_nombre()).toString()));
      ((OCAPCuestionariosForm)form).setDGrado_des(usuarioOT.getDGrado_des());
      ((OCAPCuestionariosForm)form).setCExpId(usuarioOT.getCExpId());

      DecimalFormat formato = new DecimalFormat("000000");
      ((OCAPCuestionariosForm)form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(usuarioOT.getCExpId().longValue()));

      ((OCAPCuestionariosForm)form).setCodigoEvaluadorId("REX" + formato.format(codigoEvaluador));

      compCteLN = new OCAPComponentesCtesLN(jcylUsuario);
      compFqsOT = compCteLN.buscarCteUsuario(usuarioOT.getCExpId().longValue());
      ((OCAPCuestionariosForm)form).setDNombreCTE(compFqsOT.getDNombre());

      OCAPComponentesCtesLN componenteln = new OCAPComponentesCtesLN(jcylUsuario);
      String nombrePresidente = componenteln.buscarNombreCte(jcylUsuario.getUsuario().getC_usr_id());
      ((OCAPCuestionariosForm)form).setDNombrePresidente(nombrePresidente);

      if (usuarioOT.getFInicioEvaluacion() != null) {
        ((OCAPCuestionariosForm)form).setFInicioEvaluacion(DateUtil.convertDateToString(usuarioOT.getFInicioEvaluacion()));
      }

      if (usuarioOT.getFInformeEval() != null) {
        ((OCAPCuestionariosForm)form).setFFinEvaluacion(DateUtil.convertDateToString(usuarioOT.getFInformeEval()));
      }

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      listaAreas = cuestionariosLN.listadoOCAPCuestionariosPorItinerario(usuarioOT, jcylUsuario);

      boolean bFinalizadoUnoPorArea = true;
      float nCreditosItinerario = 0.0F;
      float nCreditosConseguidos = 0.0F;
      float nCreditosEvaluados = 0.0F;
      estCuestionarioLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
      credCuestLN = new OCAPCreditosCuestionariosLN(jcylUsuario);
      ArrayList copiaListaAreas = new ArrayList();
      ArrayList listaAuditorias = new ArrayList();
      for (int i = 0; i < listaAreas.size(); i++) {
        areaOT = (OCAPAreasItinerariosOT)listaAreas.get(i);
        nCreditosItinerario += areaOT.getNCreditosArea();
        listaCuestionarios = areaOT.getListaCuestionarios();
        ArrayList copiaListaCuestionarios = new ArrayList();
        for (int j = 0; j < listaCuestionarios.size(); j++) {
          cuestionarioAuxOT = (OCAPCuestionariosOT)listaCuestionarios.get(j);
          nCreditosConseguidos += cuestionarioAuxOT.getNCreditosConseguidos();
          nCreditosEvaluados += cuestionarioAuxOT.getNCreditosEvaluados();
          ArrayList listaCreditos = credCuestLN.buscarOCAPCreditosCuestionario(cuestionarioAuxOT.getCCuestionarioId(), 1, usuarioOT.getCExpId().longValue());
          if ((listaCreditos != null) && (listaCreditos.size() > 0)) {
            credCuestOT = (OCAPCreditosCuestionariosOT)listaCreditos.get(0);
            cuestionarioAuxOT.setBAcuerdo(credCuestOT.getBAcuerdo());
            cuestionarioAuxOT.setBProposicion(credCuestOT.getBProposicion());
            cuestionarioAuxOT.setARazon(credCuestOT.getARazon());
            cuestionarioAuxOT.setAComentarios(credCuestOT.getAComentarios());
            if ("A".equals(credCuestOT.getBProposicion()))
              listaAuditorias.add(cuestionarioAuxOT);
          }
          copiaListaCuestionarios.add(cuestionarioAuxOT);
        }
        areaOT.setListaCuestionarios(copiaListaCuestionarios);
        copiaListaAreas.add(areaOT);

        int numFin = estCuestionarioLN.contarCuestionariosFinalizadosArea(usuarioOT.getCExpId().longValue(), areaOT.getCAreaId().longValue());
        if (numFin == 0)
          bFinalizadoUnoPorArea = false;
      }
      ((OCAPCuestionariosForm)form).setListadoAreas(copiaListaAreas);
      ((OCAPCuestionariosForm)form).setListadoAuditorias(listaAuditorias);

      itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());
      ((OCAPCuestionariosForm)form).setDNombreItinerario(itinerarioOT.getDDescripcion());
      ((OCAPCuestionariosForm)form).setNCreditosNecesarios(itinerarioOT.getNCreditosNecesarios() + "");
      ((OCAPCuestionariosForm)form).setCItinerarioId(String.valueOf(itinerarioOT.getCItinerarioId()));

      ((OCAPCuestionariosForm)form).setNCreditosPosiblesItinerario(nCreditosItinerario + "");
      nCreditosConseguidos = (float)(Math.rint(nCreditosConseguidos * 100.0F) / 100.0D);
      ((OCAPCuestionariosForm)form).setNCreditosItinerario(nCreditosConseguidos + "");
      nCreditosEvaluados = (float)(Math.rint(nCreditosEvaluados * 100.0F) / 100.0D);
      ((OCAPCuestionariosForm)form).setNCreditosEvaluados(nCreditosEvaluados + "");

      if (usuarioOT.getNCreditosEvaluadosCTE() == 0.0D)
        ((OCAPCuestionariosForm)form).setNCreditosEvaluadosCTE(nCreditosEvaluados + "");
      else {
        ((OCAPCuestionariosForm)form).setNCreditosEvaluadosCTE(String.valueOf(usuarioOT.getNCreditosEvaluadosCTE()) + "");
      }

      session.setAttribute(Constantes.COMBO_CUESTIONARIOS, new ArrayList());

      if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {
        mappingForward = "irGenerarInformeCTESacyl";
      }
      if (usuarioOT.getFInformeCte() != null) {
        ((OCAPCuestionariosForm)form).setFEvaluacion(DateUtil.convertDateToString(usuarioOT.getFInformeCte()));
        ((OCAPCuestionariosForm)form).setFReunion(DateUtil.convertDateToString(usuarioOT.getFReunionCTE()));
        ((OCAPCuestionariosForm)form).setBConformidadCTE(usuarioOT.getBConformidadCTE());
        ((OCAPCuestionariosForm)form).setBNuevaRevision(usuarioOT.getBNuevaRevision());
        ((OCAPCuestionariosForm)form).setADiscrepancias(usuarioOT.getADiscrepanciasCTE());
        ((OCAPCuestionariosForm)form).setAEspecificaciones(usuarioOT.getAEspecificacionesCTE());
        //bug OFCA, grado no se corresponde con el que se solicita
        ((OCAPCuestionariosForm)form).setDGrado_des(usuarioOT.getDGrado_des());
        request.setAttribute("informeGenerado", Constantes.SI);
      } else {
        ((OCAPCuestionariosForm)form).setFEvaluacion(DateUtil.convertDateToString(new Date()));
        ((OCAPCuestionariosForm)form).setFReunion(DateUtil.convertDateToString(new Date()));
        request.setAttribute("informeGenerado", Constantes.NO);
      }

      request.setAttribute("cCompfqsIdS", request.getParameter("cCompfqsIdS"));

      OCAPConfigApp.logger.info(getClass().getName() + " generarInformeCTE: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(mappingForward);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward generarInformeCE(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPCuestionariosLN cuestionariosLN = null;

    OCAPCuestionariosOT cuestionarioAuxOT = null;
    OCAPItinerariosLN itinerariosLN = null;
    OCAPItinerariosOT itinerarioOT = null;
    OCAPAreasItinerariosOT areaOT = null;
    ArrayList listaAreas = null;
    ArrayList listaCuestionarios = null;
    OCAPEstadosCuestionarioLN estCuestionarioLN = null;
    OCAPSolicitudesOT solicitudesOT = null;
    OCAPSolicitudesLN solicitudesLN = null;
    String mappingForward = "irGenerarInformeCE";
    OCAPEvaluadoresLN evaluadoresLN = null;
    OCAPComponentesfqsOT compFqsOT = null;
    OCAPCreditosCuestionariosLN credCuestLN = null;
    OCAPCreditosCuestionariosOT credCuestOT = null;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " generarInformeCE: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      if (request.getParameter("expId") != null) {
        session.setAttribute("expedienteId", request.getParameter("expId"));
      }
      if (session.getAttribute("expedienteId") != null) {
        solicitudesOT = new OCAPSolicitudesOT();
        solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
        solicitudesOT.setCExp_id(Long.parseLong(session.getAttribute("expedienteId").toString()));
        solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);
        usuarioOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(), solicitudesOT.getCExp_id(), jcylUsuario);
      } else {
        String usuId = jcylUsuario.getUser().getC_usr_id();
        usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);
      }

      if (usuarioOT == null)
      {
        return mapping.findForward("irNoExisteSolicitud");
      }

      long codigoEvaluador = 0L;
      if (Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol())) {
        evaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
        compFqsOT = evaluadoresLN.buscarOCAPEvaluadoresDNI(jcylUsuario.getUser().getC_usr_id(), usuarioOT.getCItinerarioId());
        ((OCAPCuestionariosForm)form).setDNombreEvaluador(compFqsOT.getDNombre());
        ((OCAPCuestionariosForm)form).setDApellidosEvaluador(compFqsOT.getDApellidos());
        ((OCAPCuestionariosForm)form).setCDniEvaluador(compFqsOT.getCDni());
        ((OCAPCuestionariosForm)form).setNTelefonoEvaluador(compFqsOT.getNTelefono1());
        ((OCAPCuestionariosForm)form).setACorreoelectronicoEvaluador(compFqsOT.getACorreoelectronico());
        ((OCAPCuestionariosForm)form).setDCategoriaEspecialidadEvaluador(compFqsOT.getDProfesionalNombre() + (compFqsOT.getDEspecNombre() == null ? "" : new StringBuilder().append(" / ").append(compFqsOT.getDEspecNombre()).toString()));
        codigoEvaluador = compFqsOT.getCCompfqsId();
      } else {
        codigoEvaluador = solicitudesOT.getCCompfqs_id();
      }

      ((OCAPCuestionariosForm)form).setDCategoriaEspecialidadEvaluado(usuarioOT.getDProfesional_nombre() + (usuarioOT.getDEspec_nombre() == null ? "" : new StringBuilder().append(" / ").append(usuarioOT.getDEspec_nombre()).toString()));
      ((OCAPCuestionariosForm)form).setDGrado_des(usuarioOT.getDGrado_des());
      ((OCAPCuestionariosForm)form).setCExpId(usuarioOT.getCExpId());

      if (usuarioOT.getFInformeCC() != null)
        ((OCAPCuestionariosForm)form).setFInformeCC(DateUtil.convertDateToString(usuarioOT.getFInformeCC()));
      else {
        ((OCAPCuestionariosForm)form).setFInformeCC("");
      }

      DecimalFormat formato = new DecimalFormat("000000");
      ((OCAPCuestionariosForm)form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(usuarioOT.getCExpId().longValue()));

      ((OCAPCuestionariosForm)form).setCodigoEvaluadorId("REX" + formato.format(codigoEvaluador));

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      listaAreas = cuestionariosLN.listadoOCAPCuestionariosPorItinerario(usuarioOT, jcylUsuario);

      boolean bFinalizadoUnoPorArea = true;
      float nCreditosItinerario = 0.0F;
      float nCreditosConseguidos = 0.0F;
      float nCreditosEvaluados = 0.0F;
      estCuestionarioLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
      credCuestLN = new OCAPCreditosCuestionariosLN(jcylUsuario);
      ArrayList copiaListaAreas = new ArrayList();
      ArrayList listaAuditorias = new ArrayList();
      for (int i = 0; i < listaAreas.size(); i++) {
        areaOT = (OCAPAreasItinerariosOT)listaAreas.get(i);
        nCreditosItinerario += areaOT.getNCreditosArea();
        listaCuestionarios = areaOT.getListaCuestionarios();
        ArrayList copiaListaCuestionarios = new ArrayList();
        for (int j = 0; j < listaCuestionarios.size(); j++) {
          cuestionarioAuxOT = (OCAPCuestionariosOT)listaCuestionarios.get(j);
          nCreditosConseguidos += cuestionarioAuxOT.getNCreditosConseguidos();
          nCreditosEvaluados += cuestionarioAuxOT.getNCreditosEvaluados();
          ArrayList listaCreditos = credCuestLN.buscarOCAPCreditosCuestionario(cuestionarioAuxOT.getCCuestionarioId(), 1, usuarioOT.getCExpId().longValue());
          if ((listaCreditos != null) && (listaCreditos.size() > 0)) {
            credCuestOT = (OCAPCreditosCuestionariosOT)listaCreditos.get(0);
            cuestionarioAuxOT.setBAcuerdo(credCuestOT.getBAcuerdo());
            cuestionarioAuxOT.setBProposicion(credCuestOT.getBProposicion());
            cuestionarioAuxOT.setARazon(credCuestOT.getARazon());
            cuestionarioAuxOT.setAComentarios(credCuestOT.getAComentarios());
            if ("A".equals(credCuestOT.getBProposicion()))
              listaAuditorias.add(cuestionarioAuxOT);
          }
          copiaListaCuestionarios.add(cuestionarioAuxOT);
        }
        areaOT.setListaCuestionarios(copiaListaCuestionarios);
        copiaListaAreas.add(areaOT);

        int numFin = estCuestionarioLN.contarCuestionariosFinalizadosArea(usuarioOT.getCExpId().longValue(), areaOT.getCAreaId().longValue());
        if (numFin == 0)
          bFinalizadoUnoPorArea = false;
      }
      ((OCAPCuestionariosForm)form).setListadoAreas(copiaListaAreas);
      ((OCAPCuestionariosForm)form).setListadoAuditorias(listaAuditorias);

      itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());
      ((OCAPCuestionariosForm)form).setDNombreItinerario(itinerarioOT.getDDescripcion());
      ((OCAPCuestionariosForm)form).setNCreditosNecesarios(itinerarioOT.getNCreditosNecesarios() + "");

      ((OCAPCuestionariosForm)form).setNCreditosPosiblesItinerario(String.valueOf(nCreditosItinerario));
      nCreditosConseguidos = (float)(Math.rint(nCreditosConseguidos * 100.0F) / 100.0D);
      ((OCAPCuestionariosForm)form).setNCreditosItinerario(String.valueOf(nCreditosConseguidos));
      nCreditosEvaluados = (float)(Math.rint(nCreditosEvaluados * 100.0F) / 100.0D);
      ((OCAPCuestionariosForm)form).setNCreditosEvaluados(String.valueOf(nCreditosEvaluados));

      session.setAttribute(Constantes.COMBO_CUESTIONARIOS, new ArrayList());

      if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {
        mappingForward = "irGenerarInformeCESacyl";
      }
      if (usuarioOT.getFInformeCe() != null) {
        ((OCAPCuestionariosForm)form).setFEvaluacion(DateUtil.convertDateToString(usuarioOT.getFInformeCe()));
        ((OCAPCuestionariosForm)form).setAEspecificaciones(usuarioOT.getAEspecificacionesCE());
        ((OCAPCuestionariosForm)form).setFReunion(DateUtil.convertDateToString(usuarioOT.getFReunionCE()));
        ((OCAPCuestionariosForm)form).setBDecisionCE(usuarioOT.getBDecisionCEStr());
        ((OCAPCuestionariosForm)form).setADiscrepancias(usuarioOT.getADiscrepanciasCE());
        request.setAttribute("informeGenerado", Constantes.SI);
      } else {
        ((OCAPCuestionariosForm)form).setFEvaluacion(DateUtil.convertDateToString(new Date()));
        ((OCAPCuestionariosForm)form).setFReunion(DateUtil.convertDateToString(new Date()));
        request.setAttribute("informeGenerado", Constantes.NO);
      }

      ((OCAPCuestionariosForm)form).setBConformidadCTE(usuarioOT.getBConformidadCTE());

      request.setAttribute("cCompfqsIdS", request.getParameter("cCompfqsIdS"));

      OCAPConfigApp.logger.info(getClass().getName() + " generarInformeCE: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(mappingForward);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward guardarInforme(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    String mappingForward = "exito";
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " guardarInforme: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      OCAPExpedienteCarreraLN expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
      OCAPExpedientecarreraOT expCarreraOT = new OCAPExpedientecarreraOT();
      expCarreraOT.setCExpId(((OCAPCuestionariosForm)form).getCExpId());
      expCarreraOT.setAEspecificacionesEval(((OCAPCuestionariosForm)form).getAEspecificaciones());
      expCarreraOT.setFInformeEval(new Date());

      expCarreraOT.setFReunionCTE(new Date());
      expCarreraOT.setFReunionCE(new Date());

      expCarreraOT.setFInformeCE(new Date());
      expCarreraOT.setFInformeCC(new Date());
      expCarreraOT.setBConformidadCTE(Constantes.SI);
      expCarreraOT.setBDecisionCE("C");
      expCarreraOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());
      double creditosEvaluados = Double.parseDouble(((OCAPCuestionariosForm)form).getNCreditosEvaluados());
      double creditosNecesarios = Double.parseDouble(((OCAPCuestionariosForm)form).getNCreditosNecesarios());

      expCarreraLN.modificacionOCAPExpedientecarrera(expCarreraOT, false);

      if (jcylUsuario.getUsuario().getRol().equals(Constantes.OCAP_CTE)) {
        request.setAttribute("rutaVuelta", "OCAPEvaluadores.do?accion=listarEvaluadosGerenciaCTE&recuperarBusquedaAnterior=Y&opcion=" + session.getAttribute("opcion") + "&codigo=" + session.getAttribute("codigo") + "&cte=" + session.getAttribute("cte"));
      }
      else {
        request.setAttribute("rutaVuelta", "OCAPEvaluadores.do?accion=listarEvaluados&cCompfqsIdS=" + session.getAttribute("cCompfqsIdS") + "&" + Constantes.RECUPERAR_BUSQUEDA_ANTERIOR + "=" + Constantes.SI);
      }

      OCAPConfigApp.logger.info(getClass().getName() + " guardarInforme: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(mappingForward);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward guardarInforme2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    String mappingForward = "exito";
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPSolicitudesOT solicitudesOT = null;
    OCAPSolicitudesLN solicitudesLN = null;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " guardarInforme2: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      if (session.getAttribute("expedienteId") != null) {
        solicitudesOT = new OCAPSolicitudesOT();
        solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
        solicitudesOT.setCExp_id(Long.parseLong(session.getAttribute("expedienteId").toString()));
        solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);
        usuarioOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(), solicitudesOT.getCExp_id(), jcylUsuario);
      } else {
        String usuId = jcylUsuario.getUser().getC_usr_id();
        usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);
      }

      OCAPRevisionesLN revisionLN = new OCAPRevisionesLN(jcylUsuario);
      OCAPRevisionesOT revisionOT = new OCAPRevisionesOT();
      revisionOT.setCExpId(((OCAPCuestionariosForm)form).getCExpId());

      if ((usuarioOT.getCProfesionalId().intValue() == 47) || (usuarioOT.getCProfesionalId().intValue() == 32))
        revisionOT.setBAuditoria(Constantes.SI);
      else
        revisionOT.setBAuditoria(Constantes.NO);
      revisionOT.setFInicioEval(DateUtil.convertStringToDate(((OCAPCuestionariosForm)form).getFInicioEvaluacion()));
      revisionOT.setAMotivoAuditoria(((OCAPCuestionariosForm)form).getAMotivoAuditoria());
      revisionOT.setALugarRealizacion(((OCAPCuestionariosForm)form).getALugarRealizacion());
      revisionOT.setFAuditoria(DateUtil.convertStringToDate(((OCAPCuestionariosForm)form).getFAuditoria()));
      revisionOT.setASuperior(((OCAPCuestionariosForm)form).getASuperior());
      revisionOT.setAHallazgos(((OCAPCuestionariosForm)form).getAHallazgos());
      revisionOT.setBCumplimiento(((OCAPCuestionariosForm)form).getBCumplimiento());
      if (Constantes.NO.equals(((OCAPCuestionariosForm)form).getBCumplimiento()))
        revisionOT.setBCategorizacion(((OCAPCuestionariosForm)form).getBCategorizacion());
      revisionOT.setARecomendaciones(((OCAPCuestionariosForm)form).getARecomendaciones());
      revisionOT.setAConclusiones(((OCAPCuestionariosForm)form).getAConclusiones());
      revisionOT.setFInforme(new Date());
      revisionOT.setBAutoEvaluacion(((OCAPCuestionariosForm)form).getBAutoEvaluacion());
      revisionOT.setB2Evaluacion(((OCAPCuestionariosForm)form).getB2Evaluacion());
      revisionOT.setBAnalisis(((OCAPCuestionariosForm)form).getBAnalisis());
      revisionOT.setAResultados(((OCAPCuestionariosForm)form).getAResultados());
      revisionLN.modificaOCAPRevisiones(revisionOT);

      request.setAttribute("rutaVuelta", "OCAPEvaluadores.do?accion=listarEvaluados&cCompfqsIdS=" + session.getAttribute("cCompfqsIdS") + "&" + Constantes.RECUPERAR_BUSQUEDA_ANTERIOR + "=" + Constantes.SI);

      OCAPConfigApp.logger.info(getClass().getName() + " guardarInforme2: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(mappingForward);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward guardarAuditoria(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    String mappingForward = "exito";
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPSolicitudesOT solicitudesOT = null;
    OCAPSolicitudesLN solicitudesLN = null;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " guardarAuditoria: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      if (session.getAttribute("expedienteId") != null) {
        solicitudesOT = new OCAPSolicitudesOT();
        solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
        solicitudesOT.setCExp_id(Long.parseLong(session.getAttribute("expedienteId").toString()));
        solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);
        usuarioOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(), solicitudesOT.getCExp_id(), jcylUsuario);
      } else {
        String usuId = jcylUsuario.getUser().getC_usr_id();
        usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);
      }

      OCAPRevisionesLN revisionLN = new OCAPRevisionesLN(jcylUsuario);
      OCAPRevisionesOT revisionOT = new OCAPRevisionesOT();
      revisionOT.setCExpId(((OCAPCuestionariosForm)form).getCExpId());
      revisionOT.setFAuditoriaPropuesta(DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_HHMM, ((OCAPCuestionariosForm)form).getFAuditoriaPropuesta() + " " + ((OCAPCuestionariosForm)form).getHAuditoriaPropuesta()));
      revisionLN.modificaOCAPRevisiones(revisionOT);

      request.setAttribute("rutaVuelta", "OCAPCuestionarios.do?accion=irListar&cCompfqsIdS=" + session.getAttribute("cCompfqsIdS") + "&expId=" + ((OCAPCuestionariosForm)form).getCExpId());

      OCAPConfigApp.logger.info(getClass().getName() + " guardarAuditoria: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(mappingForward);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward guardarInformeCTE(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    String mappingForward = "exito";
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " guardarInformeCTE: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      OCAPExpedienteCarreraLN expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
      OCAPExpedientecarreraOT expCarreraOT = new OCAPExpedientecarreraOT();
      expCarreraOT.setCExpId(((OCAPCuestionariosForm)form).getCExpId());
      expCarreraOT.setFReunionCTE(DateUtil.convertStringToDate(((OCAPCuestionariosForm)form).getFReunion()));
      expCarreraOT.setBConformidadCTE(((OCAPCuestionariosForm)form).getBConformidadCTE());
      if (Constantes.NO.equals(expCarreraOT.getBConformidadCTE())) {
        expCarreraOT.setBNuevaRevision(((OCAPCuestionariosForm)form).getBNuevaRevision());
        expCarreraOT.setADiscrepanciasCTE(((OCAPCuestionariosForm)form).getADiscrepancias());
        double creditosCTE = Double.parseDouble(((OCAPCuestionariosForm)form).getNCreditosEvaluadosCTE());
        expCarreraOT.setNCreditosEvaluadosCTE(creditosCTE);
      }

      expCarreraOT.setAEspecificacionesCTE(((OCAPCuestionariosForm)form).getAEspecificaciones());
      expCarreraOT.setFInformeCTE(new Date());

      double creditosEvaluados = 0.0D;

      if (Constantes.NO.equals(expCarreraOT.getBConformidadCTE())) {
        creditosEvaluados = Double.parseDouble(((OCAPCuestionariosForm)form).getNCreditosEvaluadosCTE());
      }
      else if (((OCAPCuestionariosForm)form).getNCreditosEvaluados() != null)
        creditosEvaluados = Double.parseDouble(((OCAPCuestionariosForm)form).getNCreditosEvaluados());
      else {
        creditosEvaluados = 0.0D;
      }

      double creditosNecesarios = Double.parseDouble(((OCAPCuestionariosForm)form).getNCreditosNecesarios());

      if ((creditosEvaluados < creditosNecesarios) || (((OCAPCuestionariosForm)form).getCItinerarioId() == null) || (((OCAPCuestionariosForm)form).getCItinerarioId().equals("0"))) {
        expCarreraOT.setBReconocimientoGrado(Constantes.NO);
        expCarreraOT.setCEstadoId(11);
      }
      else {
        expCarreraOT.setBReconocimientoGrado(Constantes.SI);
        expCarreraOT.setCEstadoId(12);
      }
      expCarreraOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());
      expCarreraLN.modificacionOCAPExpedientecarrera(expCarreraOT, false);

      request.setAttribute("rutaVuelta", "OCAPEvaluadores.do?accion=listarEvaluadosGerenciaCTE&recuperarBusquedaAnterior=Y&opcion=" + session.getAttribute("opcion") + "&codigo=" + session.getAttribute("codigo") + "&cte=" + session.getAttribute("cte"));

      OCAPConfigApp.logger.info(getClass().getName() + " guardarInformeCTE: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(mappingForward);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward guardarInformeCE(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    String mappingForward = "exito";
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " guardarInformeCE: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      OCAPExpedienteCarreraLN expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
      OCAPExpedientecarreraOT expCarreraOT = new OCAPExpedientecarreraOT();
      expCarreraOT.setCExpId(((OCAPCuestionariosForm)form).getCExpId());
      expCarreraOT.setFReunionCE(DateUtil.convertStringToDate(((OCAPCuestionariosForm)form).getFReunion()));
      expCarreraOT.setBDecisionCE(((OCAPCuestionariosForm)form).getBDecisionCE());
      expCarreraOT.setADiscrepanciasCE(((OCAPCuestionariosForm)form).getADiscrepancias());
      expCarreraOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());
      expCarreraOT.setAEspecificacionesCE(((OCAPCuestionariosForm)form).getAEspecificaciones());
      expCarreraOT.setFInformeCE(new Date());
      expCarreraLN.modificacionOCAPExpedientecarrera(expCarreraOT, false);

      request.setAttribute("rutaVuelta", "OCAPComponentesCtes.do?accion=listarEvaluados&cCteIdS=" + session.getAttribute("cCteIdS") + "&nombre=" + session.getAttribute("nombreCTE"));

      OCAPConfigApp.logger.info(getClass().getName() + " guardarInformeCE: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(mappingForward);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward guardarInformeCC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    String mappingForward = "exito";
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " guardarInformeCC: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      OCAPExpedienteCarreraLN expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
      OCAPExpedientecarreraOT expCarreraOT = new OCAPExpedientecarreraOT();
      expCarreraOT.setCExpId(((OCAPCuestionariosForm)form).getCExpId());
      expCarreraOT.setFInformeCC(DateUtil.convertStringToDate(((OCAPCuestionariosForm)form).getFInformeCC()));
      expCarreraOT.setAEspecificacionesCC(((OCAPCuestionariosForm)form).getAEspecificaciones());
      expCarreraOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());
      expCarreraLN.modificacionOCAPExpedientecarrera(expCarreraOT, false);

      request.setAttribute("rutaVuelta", "OCAPEvaluadores.do?accion=listarEvaluados&cCompfqsIdS=" + session.getAttribute("cCompfqsIdS") + "&" + Constantes.RECUPERAR_BUSQUEDA_ANTERIOR + "=" + Constantes.SI);

      OCAPConfigApp.logger.info(getClass().getName() + " guardarInformeCC: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(mappingForward);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward verCuestionario(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();
    HttpSession session = request.getSession();

    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPCuestionariosLN cuestionariosLN = null;
    OCAPCuestionariosOT cuestionarioAuxOT = null;
    OCAPCuestionariosOT cuestionarioAux2OT = null;
    OCAPCuestionariosOT cuestionarioOT = null;
    OCAPRepeticionesCuestionariosOT repeCuestionarioOT = null;
    ArrayList listaPreguntas = null;
    OCAPExpedientesCAsOT expedienteCAOT = null;

    int idRepeticion = 0; int nEvidencia = 0;
    long idCuestionario = 0L; long idPadreEvidencia = 0L;
    int cuentaPreguntasNoVacias = 0;
    boolean verPreguntas = false;
    String redireccion = "";
    OCAPEstadosCuestionarioLN estCuesLN = null;
    OCAPItinerariosOT itinerarioOT = null;
    OCAPItinerariosLN itinerariosLN = null;
    String estadoSimulacion = "";
    String estado = null;
    try
    {
      OCAPConfigApp.logger.debug(getClass().getName() + " verCuestionario: Inicio");

      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      usuariosLN = new OCAPUsuariosLN(jcylUsuario);

      if (request.getParameter("expId") != null) {
        session.setAttribute("expedienteId", request.getParameter("expId"));
      }

      if (session.getAttribute("expedienteId") != null) {
        OCAPExpedienteCarreraLN expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
        String dniUsuario = expCarreraLN.buscarDNIUsuarioExpediente(Long.parseLong(session.getAttribute("expedienteId").toString()));
        usuarioOT = usuariosLN.datosPersonalesUsuario(dniUsuario, Long.parseLong(session.getAttribute("expedienteId").toString()), jcylUsuario);
      } else {
        String usuId = jcylUsuario.getUser().getC_usr_id();
        usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);
      }

      if (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())) {
        usuarioOT = (OCAPUsuariosOT)session.getAttribute("UsuarioPruebaItinerario");
      }
      if (usuarioOT == null) {
        if (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())) {
          return mapping.findForward("irElegirItinerario");
        }
        return mapping.findForward("irNoExisteSolicitud");
      }

      if (request.getAttribute("idCuestionario") == null)
        idCuestionario = Long.parseLong(request.getParameter("idCuestionario") == null ? "0" : request.getParameter("idCuestionario"));
      else idCuestionario = Long.parseLong(request.getAttribute("idCuestionario").toString());

      if (request.getAttribute("idRepeticion") == null)
        idRepeticion = Integer.parseInt(request.getParameter("idRepeticion") == null ? "0" : request.getParameter("idRepeticion"));
      else idRepeticion = Integer.parseInt((request.getAttribute("idRepeticion") == null) || ("".equals(request.getAttribute("idRepeticion"))) ? "0" : request.getAttribute("idRepeticion").toString());
      if ((request.getParameter("verPreguntas") != null) && (Constantes.SI.equals(request.getParameter("verPreguntas"))))
        verPreguntas = true;
      else verPreguntas = false;
      if (request.getAttribute("nEvidencia") == null)
        nEvidencia = Integer.parseInt(request.getParameter("nEvidencia") == null ? "0" : request.getParameter("nEvidencia"));
      else nEvidencia = Integer.parseInt(request.getAttribute("nEvidencia").toString());
      if (request.getAttribute("idPadreEvidencia") == null)
        idPadreEvidencia = Long.parseLong(request.getParameter("idPadreEvidencia") == null ? "0" : request.getParameter("idPadreEvidencia"));
      else idPadreEvidencia = Long.parseLong(request.getAttribute("idPadreEvidencia").toString());

      ((OCAPCuestionariosForm)form).setCPadreEvidenciaId(idPadreEvidencia + "");

      ArrayList listaAux = (ArrayList)session.getAttribute(Constantes.COMBO_CUESTIONARIOS);
      ArrayList listaAux2 = new ArrayList();
      if (listaAux != null) {
        for (int i = 0; i < listaAux.size(); i++) {
          cuestionarioAux2OT = (OCAPCuestionariosOT)listaAux.get(i);
          if (idCuestionario == cuestionarioAux2OT.getCCuestionarioId()) {
            listaAux2 = cuestionarioAux2OT.getListaRepeticiones();
            if (listaAux2 == null)
              listaAux2 = new ArrayList();
            break;
          }
        }
      }

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      cuestionarioOT = new OCAPCuestionariosOT();
      cuestionarioOT.setCCuestionarioId(idCuestionario);
      if (idRepeticion == -1)
        cuestionarioOT = cuestionariosLN.detalleCuestionario(usuarioOT, cuestionarioOT, 0, jcylUsuario);
      else {
        cuestionarioOT = cuestionariosLN.detalleCuestionario(usuarioOT, cuestionarioOT, idRepeticion, jcylUsuario);
      }

//      ((OCAPCuestionariosForm)form).setCPlantillaId(String.valueOf(cuestionarioOT.getCPlantillaId()));
//      if ((cuestionarioOT.getCPlantillaId() == 11) && (nEvidencia != 0)) {
//        idRepeticion = 0;
//      }
//
//      ((OCAPCuestionariosForm)form).setCPlantillaId(String.valueOf(cuestionarioOT.getCPlantillaId()));

      int contadorIntermedios = 1;
      while (((request.getParameter("saltarIntermedio" + contadorIntermedios) == null) || (!Constantes.SI.equals(request.getParameter("saltarIntermedio" + contadorIntermedios)))) && ((request.getAttribute("saltarIntermedio" + contadorIntermedios) == null) || (!Constantes.SI.equals(request.getAttribute("saltarIntermedio" + contadorIntermedios)))) && (cuestionarioOT.getCCuestionarioIntermedioId() != 0L) && (idRepeticion == 0) && (!Constantes.OCAP_FQS_ADTVO.equals(jcylUsuario.getUser().getRol())))
      {
        verPreguntas = true;
        request.setAttribute("idCuestionarioDespuesIntermedio" + contadorIntermedios, idCuestionario + "");
        idCuestionario = cuestionarioOT.getCCuestionarioIntermedioId();
        cuestionarioOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioIntermedioId());
        cuestionarioOT = cuestionariosLN.detalleCuestionario(usuarioOT, cuestionarioOT, idRepeticion, jcylUsuario);
        contadorIntermedios++;
      }

      if (idRepeticion == -1) idRepeticion = 0;

      ((OCAPCuestionariosForm)form).setBFinalizado("");

      if (!verPreguntas)
      {
        if (cuestionarioOT.getNMaxHijosContestados() != 0) {
          session.setAttribute("cuestionarioPadre" + cuestionarioOT.getCCuestionarioId(), cuestionarioOT);
        }

        if (idRepeticion == 0)
        {
          if (((cuestionarioOT.getNRepeticiones().intValue() == 1) || ((cuestionarioOT.getNRepeticiones().intValue() > 1) && (Constantes.NO.equals(cuestionarioOT.getBContestarUsuario())) && ((Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol())) || (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol()))))) && ((cuestionarioOT.getNSubCuestionarios().intValue() == 0) || (Constantes.SI.equals(cuestionarioOT.getBSubdivision()))))
          {
            verPreguntas = true;
          }
          if (((cuestionarioOT.getNRepeticiones().intValue() == 1) || ((cuestionarioOT.getNRepeticiones().intValue() > 1) && (Constantes.NO.equals(cuestionarioOT.getBContestarUsuario())) && ((Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol())) || (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol()))))) && (cuestionarioOT.getNSubCuestionarios().intValue() != 0) && (!Constantes.SI.equals(cuestionarioOT.getBSubdivision())))
          {
            ArrayList listaAuxiliar = new ArrayList();

            if (cuestionarioOT.getNPreguntas().intValue() != 0)
            {
              cuestionarioOT.setBVerPreguntas(Constantes.SI);
              boolean contestado = cuestionariosLN.estaContestado(cuestionarioOT, usuarioOT, 1, listaAux2, idPadreEvidencia, jcylUsuario);
              if (contestado)
                cuestionarioOT.setBContestado(Constantes.SI);
              else cuestionarioOT.setBContestado(Constantes.NO);
              listaAuxiliar.add(cuestionarioOT);
            }
            for (int i = 0; i < cuestionarioOT.getListaSubCuestionarios().size(); i++) {
              cuestionarioAuxOT = (OCAPCuestionariosOT)cuestionarioOT.getListaSubCuestionarios().get(i);

              ArrayList listaRepeticionesAux = cuestionarioAuxOT.getListaRepeticiones() == null ? new ArrayList() : cuestionarioAuxOT.getListaRepeticiones();
              repeCuestionarioOT = new OCAPRepeticionesCuestionariosOT();
              repeCuestionarioOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioId());
              repeCuestionarioOT.setNRepeticion(1);
              listaRepeticionesAux.add(repeCuestionarioOT);
              cuestionarioAuxOT.setListaRepeticiones(listaRepeticionesAux);
              boolean contestado = cuestionariosLN.estaContestado(cuestionarioAuxOT, usuarioOT, 0, listaRepeticionesAux, idPadreEvidencia, jcylUsuario);
              if (contestado)
                cuestionarioAuxOT.setBContestado(Constantes.SI);
              else cuestionarioAuxOT.setBContestado(Constantes.NO);
              listaAuxiliar.add(cuestionarioAuxOT);
            }
            cuestionarioOT.setListaSubCuestionarios(listaAuxiliar);

            redireccion = "irEnlaces";
            ((OCAPCuestionariosForm)form).setListadoSubCuestionarios(cuestionarioOT.getListaSubCuestionarios());
            session.setAttribute(Constantes.COMBO_CUESTIONARIOS, cuestionarioOT.getListaSubCuestionarios());
            ((OCAPCuestionariosForm)form).setNRepeticiones(cuestionarioOT.getNRepeticiones());
            ((OCAPCuestionariosForm)form).setDMensaje(cuestionarioOT.getDMensaje());

            ((OCAPCuestionariosForm)form).setDNombreLargo(cuestionarioOT.getDNombreLargo());
            if ((usuarioOT.getDEspec_nombre() != null) && (!"-".equals(usuarioOT.getDEspec_nombre().trim())))
              ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre() + ". " + usuarioOT.getDEspec_nombre());
            else
              ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre());
            ((OCAPCuestionariosForm)form).setDArea(cuestionarioOT.getDArea());
            ((OCAPCuestionariosForm)form).setDAreaLargo(cuestionarioOT.getDAreaLargo());
            ((OCAPCuestionariosForm)form).setCCuestionarioPadreId(cuestionarioOT.getCCuestionarioPadreId());
          }
          if (cuestionarioOT.getNRepeticiones().intValue() > 1) {
            ArrayList listaCuestionariosRepes = new ArrayList();
            for (int k = 1; k <= cuestionarioOT.getNRepeticiones().intValue(); k++) {
              OCAPCuestionariosOT cuestAuxOT = cuestionariosLN.clonarCuestionarioOT(cuestionarioOT);
              cuestAuxOT.setListaRepeticiones(listaAux2);
              boolean contestado = cuestionariosLN.estaContestado(cuestAuxOT, usuarioOT, k, listaAux2, idPadreEvidencia, jcylUsuario);
              if (contestado) {
                if ((Constantes.NO.equals(cuestAuxOT.getBContestarUsuario())) && (cuestAuxOT.getCEvidenciaId() != 0L) && (!Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol())) && (!Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol()))) {
                  estCuesLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
                  estado = estCuesLN.buscarEstadoCuestionario(usuarioOT.getCExpId().longValue(), cuestAuxOT.getCEvidenciaId(), k, cuestAuxOT.getCCuestionarioId());
                  if ("F".equals(estado))
                    cuestAuxOT.setBContestado(Constantes.SI);
                  else
                    cuestAuxOT.setBContestado(Constantes.NO); 
                } else { cuestAuxOT.setBContestado(Constantes.SI); }
              } else cuestAuxOT.setBContestado(Constantes.NO);
              listaCuestionariosRepes.add(cuestAuxOT);
            }
            ((OCAPCuestionariosForm)form).setListadoSubCuestionarios(listaCuestionariosRepes);
            session.setAttribute(Constantes.COMBO_CUESTIONARIOS, listaCuestionariosRepes);
            ((OCAPCuestionariosForm)form).setNRepeticiones(cuestionarioOT.getNRepeticiones());
            ((OCAPCuestionariosForm)form).setDRepeticion(cuestionarioOT.getDRepeticion());
            ((OCAPCuestionariosForm)form).setDMensaje(cuestionarioOT.getDMensaje());

            ((OCAPCuestionariosForm)form).setDNombreLargo(cuestionarioOT.getDNombreLargo());
            if ((usuarioOT.getDEspec_nombre() != null) && (!"-".equals(usuarioOT.getDEspec_nombre().trim())))
              ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre() + ". " + usuarioOT.getDEspec_nombre());
            else
              ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre());
            ((OCAPCuestionariosForm)form).setDArea(cuestionarioOT.getDArea());
            ((OCAPCuestionariosForm)form).setDAreaLargo(cuestionarioOT.getDAreaLargo());
            ((OCAPCuestionariosForm)form).setCCuestionarioPadreId(cuestionarioOT.getCCuestionarioPadreId());
            redireccion = "irEnlaces";
          }

        }
        else if (cuestionarioOT.getNSubCuestionarios().intValue() == 0) {
          verPreguntas = true;
        }
        else
        {
          ArrayList listaAuxiliar = new ArrayList();

          if (cuestionarioOT.getNPreguntas().intValue() != 0)
          {
            cuestionarioOT.setBVerPreguntas(Constantes.SI);
            boolean contestado = cuestionariosLN.estaContestado(cuestionarioOT, usuarioOT, idRepeticion, listaAux2, idPadreEvidencia, jcylUsuario);
            if (contestado)
              cuestionarioOT.setBContestado(Constantes.SI);
            else cuestionarioOT.setBContestado(Constantes.NO);
            listaAuxiliar.add(cuestionarioOT);
          }
          for (int i = 0; i < cuestionarioOT.getListaSubCuestionarios().size(); i++) {
            cuestionarioAuxOT = (OCAPCuestionariosOT)cuestionarioOT.getListaSubCuestionarios().get(i);

            ArrayList listaRepeticionesAux = cuestionarioAuxOT.getListaRepeticiones() == null ? new ArrayList() : cuestionarioAuxOT.getListaRepeticiones();
            repeCuestionarioOT = new OCAPRepeticionesCuestionariosOT();
            repeCuestionarioOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioId());
            repeCuestionarioOT.setNRepeticion(idRepeticion);
            listaRepeticionesAux.add(repeCuestionarioOT);
            cuestionarioAuxOT.setListaRepeticiones(listaRepeticionesAux);

            boolean contestado = cuestionariosLN.estaContestado(cuestionarioAuxOT, usuarioOT, 0, listaRepeticionesAux, idPadreEvidencia, jcylUsuario);
            if (contestado)
              cuestionarioAuxOT.setBContestado(Constantes.SI);
            else cuestionarioAuxOT.setBContestado(Constantes.NO);
            listaAuxiliar.add(cuestionarioAuxOT);
          }
          cuestionarioOT.setListaSubCuestionarios(listaAuxiliar);

          redireccion = "irEnlaces";
          ((OCAPCuestionariosForm)form).setListadoSubCuestionarios(cuestionarioOT.getListaSubCuestionarios());
          session.setAttribute(Constantes.COMBO_CUESTIONARIOS, cuestionarioOT.getListaSubCuestionarios());
          ((OCAPCuestionariosForm)form).setNRepeticiones(new Integer(1));
          ((OCAPCuestionariosForm)form).setDMensaje(cuestionarioOT.getDMensaje());

          ((OCAPCuestionariosForm)form).setDNombreLargo(cuestionarioOT.getDNombreLargo());
          if ((usuarioOT.getDEspec_nombre() != null) && (!"-".equals(usuarioOT.getDEspec_nombre().trim())))
            ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre() + ". " + usuarioOT.getDEspec_nombre());
          else
            ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre());
          ((OCAPCuestionariosForm)form).setDArea(cuestionarioOT.getDArea());
          ((OCAPCuestionariosForm)form).setDAreaLargo(cuestionarioOT.getDAreaLargo());
          ((OCAPCuestionariosForm)form).setCCuestionarioPadreId(cuestionarioOT.getCCuestionarioPadreId());
        }

      }

      ((OCAPCuestionariosForm)form).setCExpId(usuarioOT.getCExpId());
      ((OCAPCuestionariosForm)form).setDNombre(cuestionarioOT.getDNombre());
      ((OCAPCuestionariosForm)form).setCAreaId(cuestionarioOT.getCAreaId() + "");
      ((OCAPCuestionariosForm)form).setBObligatorio(cuestionarioOT.getBObligatorio());
      ((OCAPCuestionariosForm)form).setBContestarUsuario(cuestionarioOT.getBContestarUsuario());
      ((OCAPCuestionariosForm)form).setBSubdivision(cuestionarioOT.getBSubdivision());
      ((OCAPCuestionariosForm)form).setBIndicadores(cuestionarioOT.getBIndicadores());
      ((OCAPCuestionariosForm)form).setCEvidenciaId(cuestionarioOT.getCEvidenciaId() + "");
      ((OCAPCuestionariosForm)form).setCEvidenciaDocumentalId(cuestionarioOT.getCEvidenciaDocumentalId() + "");
      ((OCAPCuestionariosForm)form).setListaEviDocumentales(cuestionarioOT.getListaEviDocumentales());

      if (nEvidencia == 0)
        ((OCAPCuestionariosForm)form).setNEvidencia(cuestionarioOT.getNEvidencia());
      else {
        ((OCAPCuestionariosForm)form).setNEvidencia(nEvidencia + "");
      }
      boolean bFormularioRelleno = false;
      float creditos = 0.0F;
      if (verPreguntas)
      {
        ((OCAPCuestionariosForm)form).setDNombreUsuario(usuarioOT.getDNombre() + " " + usuarioOT.getDApellido1());

        ((OCAPCuestionariosForm)form).setDCorreoelectronico(usuarioOT.getDCorreoelectronico());
        ((OCAPCuestionariosForm)form).setCDni(usuarioOT.getCDni());
        ((OCAPCuestionariosForm)form).setCDniReal(usuarioOT.getCDniReal());
        ((OCAPCuestionariosForm)form).setNTelefono1(usuarioOT.getNTelefono1().toString());
        ((OCAPCuestionariosForm)form).setBSexo(usuarioOT.getBSexo());
        ((OCAPCuestionariosForm)form).setDObservacionesEvidencia(cuestionarioOT.getDObservacionesEvidencia());
        if ((usuarioOT.getDEspec_nombre() != null) && (!"-".equals(usuarioOT.getDEspec_nombre().trim())))
          ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre() + ". " + usuarioOT.getDEspec_nombre());
        else {
          ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre());
        }
        ((OCAPCuestionariosForm)form).setDProfesional_nombre(usuarioOT.getDProfesional_nombre());
        ((OCAPCuestionariosForm)form).setDEspec_nombre(usuarioOT.getDEspec_nombre());
        ((OCAPCuestionariosForm)form).setDCentrotrabajo_nombre(usuarioOT.getDCentrotrabajo_nombre());
        ((OCAPCuestionariosForm)form).setDProvincia(usuarioOT.getDProvincia());
        ((OCAPCuestionariosForm)form).setDTipogerencia_desc(usuarioOT.getDTipogerencia_desc());
        ((OCAPCuestionariosForm)form).setDGerencia_nombre(usuarioOT.getDGerencia_nombre());

        ((OCAPCuestionariosForm)form).setDMensaje(cuestionarioOT.getDMensaje());

        if (idRepeticion == 0) {
          idRepeticion = 1;
          ((OCAPCuestionariosForm)form).setBEraRepe0(Constantes.SI);
        } else {
          ((OCAPCuestionariosForm)form).setBEraRepe0(Constantes.NO);
        }
        long cCuestAnt = 0L;
        int nrepeAnt = 0;
        ArrayList listaAux3 = new ArrayList();
        for (int num = 0; num < listaAux2.size(); num++) {
          OCAPRepeticionesCuestionariosOT rcOT = (OCAPRepeticionesCuestionariosOT)listaAux2.get(num);
          if ((cCuestAnt != rcOT.getCCuestionarioId()) || (nrepeAnt != rcOT.getNRepeticion())) {
            listaAux3.add(rcOT);
            cCuestAnt = rcOT.getCCuestionarioId();
            nrepeAnt = rcOT.getNRepeticion();
          }
        }

        ((OCAPCuestionariosForm)form).setBSimulacion(cuestionarioOT.getBSimulacion());

        estCuesLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
        estadoSimulacion = estCuesLN.buscarEstadoSimulacion(usuarioOT.getCExpId().longValue(), idCuestionario, idRepeticion);
        if (Constantes.SI.equals(cuestionarioOT.getBSimulacion())) {
          if ((Constantes.SI.equals(request.getParameter("simular"))) && ("F".equals(estadoSimulacion)))
          {
            listaPreguntas = cuestionariosLN.buscarPreguntasCuestionarioPorUsuario(usuarioOT, idCuestionario, listaAux3, idRepeticion, true, false, nEvidencia, idPadreEvidencia, jcylUsuario);
          }
          else {
            listaPreguntas = cuestionariosLN.buscarPreguntasCuestionarioPorUsuario(usuarioOT, idCuestionario, listaAux3, idRepeticion, true, true, nEvidencia, idPadreEvidencia, jcylUsuario);
          }

        }
        else
        {
          //listaPreguntas = cuestionariosLN.buscarPreguntasCuestionarioPorUsuario(usuarioOT, idCuestionario, listaAux3, idRepeticion, false, false, nEvidencia, idPadreEvidencia, jcylUsuario);
        	if (Constantes.NO.equals(cuestionarioOT.getBContestarUsuario()) && Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol()))
                listaPreguntas = cuestionariosLN.buscarPreguntasCuestionarioPorUsuario(usuarioOT, idCuestionario, listaAux3, 0, false, false, nEvidencia, idPadreEvidencia, jcylUsuario);
             else
                listaPreguntas = cuestionariosLN.buscarPreguntasCuestionarioPorUsuario(usuarioOT, idCuestionario, listaAux3, idRepeticion, false, false, nEvidencia, idPadreEvidencia,  jcylUsuario);
        }
        ((OCAPCuestionariosForm)form).setListadoPreguntas(listaPreguntas);
        ((OCAPCuestionariosForm)form).setCCuestionarioId(idCuestionario);

        estado = estCuesLN.buscarEstadoCuestionario(usuarioOT.getCExpId().longValue(), idCuestionario, idRepeticion, idPadreEvidencia);
        if ((Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())) && (session.getAttribute("creditosPruebas" + idCuestionario + "_" + idRepeticion + "_" + idPadreEvidencia) != null))
          estado = "F";
        ((OCAPCuestionariosForm)form).setBFinalizado(estado);
        if ((Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())) && (session.getAttribute("puntosPruebas" + idCuestionario + "_" + idRepeticion + "_" + idPadreEvidencia) != null)) {
          ((OCAPCuestionariosForm)form).setBFinalizado("F");
          bFormularioRelleno = true;
        }

        ((OCAPCuestionariosForm)form).setIdRepeticion(idRepeticion + "");

        if (("F".equals(estado)) && (cuestionarioOT.getDPonderacion() != null) && (!Constantes.SI.equals(request.getParameter("simular"))))
        {
          request.setAttribute("ponderacion", Constantes.SI);
          if(cuestionarioOT != null){
        	  ((OCAPCuestionariosForm)form).setCPlantillaId(String.valueOf(cuestionarioOT.getCPlantillaId()));
          }
        }
        for (int k = 0; k < listaPreguntas.size(); k++) {
          cuestionarioAuxOT = (OCAPCuestionariosOT)listaPreguntas.get(k);
          if (cuestionarioOT.getNRepeticiones().intValue() > 1) {
            String nombreCuestionario = cuestionarioAuxOT.getDNombreLargo();
            if (nombreCuestionario.indexOf(".") == nombreCuestionario.length() - 1)
              nombreCuestionario = nombreCuestionario.substring(0, nombreCuestionario.length() - 1);
            if (Constantes.SI.equals(cuestionarioOT.getBContestarUsuario()))
              ((OCAPCuestionariosForm)form).setDNombreLargo(nombreCuestionario + " " + idRepeticion);
            else
              ((OCAPCuestionariosForm)form).setDNombreLargo(nombreCuestionario);
          } else {
            ((OCAPCuestionariosForm)form).setDNombreLargo(cuestionarioAuxOT.getDNombreLargo());
          }
          ((OCAPCuestionariosForm)form).setDArea(cuestionarioAuxOT.getDArea());
          ((OCAPCuestionariosForm)form).setDAreaLargo(cuestionarioAuxOT.getDAreaLargo());
          if ((cuestionarioAuxOT.getListaRespuestas() != null) && (cuestionarioAuxOT.getListaRespuestas().size() != 0)) {
            bFormularioRelleno = true;
            int numeroRespuestas = 0;
            for (int i = 0; i < cuestionarioAuxOT.getNElementos(); i++) {
              if (cuestionarioAuxOT.getCTipoPregunta().equals("radio")) {
                expedienteCAOT = (OCAPExpedientesCAsOT)cuestionarioAuxOT.getListaRespuestas().get(numeroRespuestas++);
                request.setAttribute("pregunta" + cuentaPreguntasNoVacias + "_" + i + "_0", expedienteCAOT.getDValor());
                for (int nRes = 0; nRes < cuestionarioAuxOT.getListaPosiblesRespuestas().size(); nRes++) {
                  OCAPRespuestasOT respuestaOT = (OCAPRespuestasOT)cuestionarioAuxOT.getListaPosiblesRespuestas().get(nRes);
                  if (respuestaOT.getDValor().equals(expedienteCAOT.getDValor()))
                    creditos += respuestaOT.getNCreditos();
                }
              }
              else if (cuestionarioAuxOT.getCTipoPregunta().equals("radioText")) {
                expedienteCAOT = (OCAPExpedientesCAsOT)cuestionarioAuxOT.getListaRespuestas().get(numeroRespuestas++);
                request.setAttribute("pregunta" + cuentaPreguntasNoVacias + "_" + i + "_0", expedienteCAOT.getDValor());
                for (int nRes = 0; nRes < cuestionarioAuxOT.getListaPosiblesRespuestas().size(); nRes++) {
                  OCAPRespuestasOT respuestaOT = (OCAPRespuestasOT)cuestionarioAuxOT.getListaPosiblesRespuestas().get(nRes);
                  if (respuestaOT.getDValor().equals(expedienteCAOT.getDValor())) {
                    creditos += respuestaOT.getNCreditos();
                  }
                }
                if ("O".equals(expedienteCAOT.getDValor())) {
                  expedienteCAOT = (OCAPExpedientesCAsOT)cuestionarioAuxOT.getListaRespuestas().get(numeroRespuestas++);
                  request.setAttribute("pregunta" + cuentaPreguntasNoVacias + "_" + i + "_1", expedienteCAOT.getDValor());
                }
              } else if ((!cuestionarioAuxOT.getCTipoPregunta().equals("vacio")) && (!cuestionarioAuxOT.getCTipoPregunta().equals("comentario"))) {
                for (int j = 0; j < cuestionarioAuxOT.getNSubElementos(); j++) {
                  expedienteCAOT = (OCAPExpedientesCAsOT)cuestionarioAuxOT.getListaRespuestas().get(numeroRespuestas++);
                  if (expedienteCAOT.getARespuestaescaneada() != null) {
                    request.setAttribute("pregunta" + cuentaPreguntasNoVacias + "_" + i + "_" + j, expedienteCAOT.getCExpCaId() + "");
                    request.setAttribute("siPDF" + cuentaPreguntasNoVacias + "_" + i + "_" + j, Constantes.SI);
                  } else {
                    request.setAttribute("pregunta" + cuentaPreguntasNoVacias + "_" + i + "_" + j, expedienteCAOT.getDValor());
                    request.setAttribute("siPDF" + cuentaPreguntasNoVacias + "_" + i + "_" + j, Constantes.NO);
                  }

                }

              }

            }

            cuentaPreguntasNoVacias++;
          }
        }
        redireccion = "irCuestionario";

        if ((cuestionarioAux2OT != null) && (idCuestionario == cuestionarioAux2OT.getCCuestionarioId())) {
          if ((cuestionarioOT.getNRepeticiones().intValue() > 1) && (Constantes.SI.equals(cuestionarioOT.getBContestarUsuario())))
            request.setAttribute("verPadre", cuestionarioAux2OT.getCCuestionarioId() + "");
          else if (cuestionarioAux2OT.getCCuestionarioPadreId() != 0L)
            request.setAttribute("verPadre", cuestionarioAux2OT.getCCuestionarioPadreId() + "");
        } else if ((cuestionarioAux2OT != null) && (idCuestionario == cuestionarioAux2OT.getCCuestionarioIntermedioId()) && (cuestionarioAux2OT.getCCuestionarioPadreId() != 0L))
          request.setAttribute("verPadre", cuestionarioAux2OT.getCCuestionarioPadreId() + "");
        else if ((cuestionarioAux2OT != null) && (cuestionarioAux2OT.getNRepeticiones().intValue() > 1) && (idPadreEvidencia != 0L))
          request.setAttribute("verPadre", idPadreEvidencia + "");
        else if ((cuestionarioAux2OT != null) && (cuestionarioAux2OT.getNRepeticiones().intValue() == 1) && (idPadreEvidencia != 0L) && (cuestionarioAux2OT.getCCuestionarioPadreId() != 0L)) {
          request.setAttribute("verPadre", cuestionarioAux2OT.getCCuestionarioPadreId() + "");
        }
      }

      if (cuestionarioOT.getNPreguntasContestables().intValue() == 0) {
        ((OCAPCuestionariosForm)form).setBContestarUsuario(Constantes.NO);
        request.setAttribute("falsoContestarUsuarioNo", Constantes.SI); } else {
        request.setAttribute("falsoContestarUsuarioNo", Constantes.NO);
      }

      if (bFormularioRelleno) {
        request.setAttribute("creditos", creditos + "");
        request.setAttribute("accion", Constantes.VER_DETALLE);
        request.setAttribute("verComentarios", Constantes.SI);
      }
      else if ((Constantes.NO.equals(cuestionarioOT.getBContestarUsuario())) && ((Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol())) || (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())))) {
        request.setAttribute("accion", Constantes.VER_DETALLE);
        request.setAttribute("verComentarios", Constantes.SI);
        ((OCAPCuestionariosForm)form).setBFinalizado("F");
        //((OCAPCuestionariosForm)form).setBContestarUsuario(cuestionarioOT.getBContestarUsuario());
      }
      else
      {
        request.setAttribute("accion", Constantes.INSERTAR);
      }

      if (((Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol())) && (new Date().after(usuarioOT.getFFinCA()))) || ((!Constantes.OCAP_FQS_ADTVO.equals(jcylUsuario.getUser().getRol())) && (!Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol())) && (!Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol()))))
      {
        request.setAttribute("accion", Constantes.VER_DETALLE);
        request.setAttribute("verComentarios", Constantes.SI);
        if ("F".equals(((OCAPCuestionariosForm)form).getBFinalizado()))
          request.setAttribute("finalizadoPorUsuario", Constantes.SI);
        else request.setAttribute("finalizadoPorUsuario", Constantes.NO);
        if (!Constantes.SI.equals(request.getParameter("simular"))) {
          ((OCAPCuestionariosForm)form).setBFinalizado("F");
        } else {
          ((OCAPCuestionariosForm)form).setBFinalizado(estadoSimulacion);
          request.setAttribute("finalizadoPorUsuario", Constantes.NO);
          request.setAttribute("simular", Constantes.SI);
        }
      }

      if ((Constantes.OCAP_FQS_ADTVO.equals(jcylUsuario.getUser().getRol())) && (Constantes.SI.equals(cuestionarioOT.getBContestarUsuario())) && (cuestionarioOT.getCTipoEvidencia() == null)) {
        request.setAttribute("accion", Constantes.VER_DETALLE);
        request.setAttribute("verComentarios", Constantes.SI);
        ((OCAPCuestionariosForm)form).setBFinalizado("F");
      }

      if (session.getAttribute("cuestionarioPadre" + cuestionarioOT.getCCuestionarioPadreId()) != null) {
        OCAPCuestionariosOT cuesTemporalOT = (OCAPCuestionariosOT)session.getAttribute("cuestionarioPadre" + cuestionarioOT.getCCuestionarioPadreId());
        if (cuesTemporalOT.getNMaxHijosContestados() <= cuesTemporalOT.getNHijosContestados()) {
          request.setAttribute("accion", Constantes.VER_DETALLE);
          request.setAttribute("verComentarios", Constantes.SI);
          ((OCAPCuestionariosForm)form).setBFinalizado("F");
        }

      }

      itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());
      DecimalFormat formato = new DecimalFormat("000000");
      ((OCAPCuestionariosForm)form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(usuarioOT.getCExpId().longValue()));
      ((OCAPCuestionariosForm)form).setDNombreItinerario(itinerarioOT.getDDescripcion());

      //OCAP-771
      ((OCAPCuestionariosForm)form).setBMostrarBtnImprimir(cuestionarioOT.getBMostrarBtnImprimir());
      
      //OCAP-1176
      ((OCAPCuestionariosForm)form).setBMostrarBtnVolver(cuestionarioOT.getbMostrarBtnVolver());
      
      OCAPConfigApp.logger.debug(getClass().getName() + " verCuestionario: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(redireccion);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward verEvaluacionCuestionario(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPCuestionariosLN cuestionariosLN = null;
    OCAPCuestionariosOT cuestionarioOT = null;
    String redireccion = "irEvalCuestionario";
    OCAPItinerariosOT itinerarioOT = null;
    OCAPItinerariosLN itinerariosLN = null;
    OCAPSolicitudesOT solicitudesOT = null;
    OCAPSolicitudesLN solicitudesLN = null;
    long idCuestionario = 0L;
    OCAPDocumentosLN docuLN = null;
    OCAPCreditosCuestionariosOT credCuestOT = null;
    OCAPCreditosCuestionariosLN credCuestLN = null;
    try
    {
      OCAPConfigApp.logger.debug(getClass().getName() + " verEvaluacionCuestionario: Inicio");

      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      usuariosLN = new OCAPUsuariosLN(jcylUsuario);

      if (request.getParameter("expId") != null) {
        session.setAttribute("expedienteId", request.getParameter("expId"));
      }

      if (session.getAttribute("expedienteId") != null) {
        OCAPExpedienteCarreraLN expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
        String dniUsuario = expCarreraLN.buscarDNIUsuarioExpediente(Long.parseLong(session.getAttribute("expedienteId").toString()));
        usuarioOT = usuariosLN.datosPersonalesUsuario(dniUsuario, Long.parseLong(session.getAttribute("expedienteId").toString()), jcylUsuario);
      } else {
        String usuId = jcylUsuario.getUser().getC_usr_id();
        usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);
      }

      if (usuarioOT == null) {
        return mapping.findForward("irNoExisteSolicitud");
      }
      if (request.getAttribute("idCuestionario") == null)
        idCuestionario = Long.parseLong(request.getParameter("idCuestionario") == null ? "0" : request.getParameter("idCuestionario"));
      else idCuestionario = Long.parseLong(request.getAttribute("idCuestionario").toString());

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      cuestionarioOT = new OCAPCuestionariosOT();
      cuestionarioOT.setCCuestionarioId(idCuestionario);
      cuestionarioOT = cuestionariosLN.detalleCuestionario(usuarioOT, cuestionarioOT, 0, jcylUsuario);

      ((OCAPCuestionariosForm)form).setDMensaje(cuestionarioOT.getDMensaje());
      ((OCAPCuestionariosForm)form).setDNombreLargo(cuestionarioOT.getDNombreLargo());
      if ((usuarioOT.getDEspec_nombre() != null) && (!"-".equals(usuarioOT.getDEspec_nombre().trim())))
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre() + ". " + usuarioOT.getDEspec_nombre());
      else
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre());
      ((OCAPCuestionariosForm)form).setDArea(cuestionarioOT.getDArea());
      ((OCAPCuestionariosForm)form).setDAreaLargo(cuestionarioOT.getDAreaLargo());

      ((OCAPCuestionariosForm)form).setCExpId(usuarioOT.getCExpId());
      ((OCAPCuestionariosForm)form).setDNombre(cuestionarioOT.getDNombre());
      ((OCAPCuestionariosForm)form).setCAreaId(String.valueOf(cuestionarioOT.getCAreaId()));
      ((OCAPCuestionariosForm)form).setBObligatorio(cuestionarioOT.getBObligatorio());
      ((OCAPCuestionariosForm)form).setBContestarUsuario(cuestionarioOT.getBContestarUsuario());
      ((OCAPCuestionariosForm)form).setBSubdivision(cuestionarioOT.getBSubdivision());
      ((OCAPCuestionariosForm)form).setBIndicadores(cuestionarioOT.getBIndicadores());
      ((OCAPCuestionariosForm)form).setCEvidenciaId(String.valueOf(cuestionarioOT.getCEvidenciaId()));
      ((OCAPCuestionariosForm)form).setCEvidenciaDocumentalId(String.valueOf(cuestionarioOT.getCEvidenciaDocumentalId()));
      ((OCAPCuestionariosForm)form).setListaEviDocumentales(cuestionarioOT.getListaEviDocumentales());
      ((OCAPCuestionariosForm)form).setNEvidencia(cuestionarioOT.getNEvidencia());

      ((OCAPCuestionariosForm)form).setNCreditosNecesarios(String.valueOf(cuestionarioOT.getNCreditos()));
      ((OCAPCuestionariosForm)form).setNCreditosObtenidos(String.valueOf(cuestionarioOT.getNCreditosConseguidos()));

      ((OCAPCuestionariosForm)form).setBEvaluado(request.getParameter("evaluado") == null ? Constantes.NO : request.getParameter("evaluado"));
      ((OCAPCuestionariosForm)form).setFEvaluacion(request.getParameter("fEvaluacion") == null ? "" : request.getParameter("fEvaluacion"));

      credCuestLN = new OCAPCreditosCuestionariosLN(jcylUsuario);
      if (Constantes.SI.equals(((OCAPCuestionariosForm)form).getBEvaluado())) {
        ArrayList listaCreditos = credCuestLN.buscarOCAPCreditosCuestionario(idCuestionario, 1, usuarioOT.getCExpId().longValue());
        if ((listaCreditos != null) && (listaCreditos.size() > 0)) {
          credCuestOT = (OCAPCreditosCuestionariosOT)listaCreditos.get(0);
          ((OCAPCuestionariosForm)form).setBAcuerdo(credCuestOT.getBAcuerdo());
          ((OCAPCuestionariosForm)form).setBProposicion(credCuestOT.getBProposicion());
          ((OCAPCuestionariosForm)form).setARazon(credCuestOT.getARazon());
          ((OCAPCuestionariosForm)form).setAComentarios(credCuestOT.getAComentarios());
          ((OCAPCuestionariosForm)form).setNCreditosEvaluados(String.valueOf(credCuestOT.getNCreditosEvaluador()));
        } else {
          ((OCAPCuestionariosForm)form).setNCreditosEvaluados(String.valueOf(cuestionarioOT.getNCreditosConseguidos()));
        }
      } else { ArrayList listaCreditos = credCuestLN.buscarOCAPCreditosCuestionario(idCuestionario, 0, usuarioOT.getCExpId().longValue());
        boolean bSimulados = false;
        float creditosSimulados = 0.0F;
        for (int nCred = 0; nCred < listaCreditos.size(); nCred++) {
          credCuestOT = (OCAPCreditosCuestionariosOT)listaCreditos.get(nCred);
          if (credCuestOT.getBSimulado()) {
            bSimulados = true;
            creditosSimulados += credCuestOT.getNCreditosEvaluador();
          } else {
            creditosSimulados += credCuestOT.getNCreditos();
          }
        }
        if (bSimulados)
          ((OCAPCuestionariosForm)form).setNCreditosEvaluados(String.valueOf(creditosSimulados));
        else {
          ((OCAPCuestionariosForm)form).setNCreditosEvaluados(String.valueOf(cuestionarioOT.getNCreditosConseguidos()));
        }
      }

      itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());
      DecimalFormat formato = new DecimalFormat("000000");
      ((OCAPCuestionariosForm)form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(usuarioOT.getCExpId().longValue()));
      ((OCAPCuestionariosForm)form).setDNombreItinerario(itinerarioOT.getDDescripcion());

      docuLN = new OCAPDocumentosLN();
      ArrayList listado = docuLN.buscarDocumentosCuestionario(usuarioOT.getCExpId().longValue(), idCuestionario, 0L);
      ((OCAPCuestionariosForm)form).setListaDocumentos(listado);

      ((OCAPCuestionariosForm)form).setCCuestionarioId(idCuestionario);

      OCAPConfigApp.logger.debug(getClass().getName() + " verEvaluacionCuestionario: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(redireccion);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward insertarEvaluacionCuestionario(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();
    HttpSession session = request.getSession();
    OCAPCreditosCuestionariosOT credCuestOT = null;
    OCAPCreditosCuestionariosLN credCuestLN = null;
    try
    {
      OCAPConfigApp.logger.debug(getClass().getName() + " insertarEvaluacionCuestionario: Inicio");

      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      credCuestOT = new OCAPCreditosCuestionariosOT();
      credCuestOT.setNCreditosEvaluador(Float.parseFloat(((OCAPCuestionariosForm)form).getNCreditosEvaluados()));
      credCuestOT.setNCreditosFinales(Float.parseFloat(((OCAPCuestionariosForm)form).getNCreditosEvaluados()));
      credCuestOT.setBAcuerdo(((OCAPCuestionariosForm)form).getBAcuerdo());
      if (Constantes.NO.equals(credCuestOT.getBAcuerdo()))
        credCuestOT.setBProposicion(((OCAPCuestionariosForm)form).getBProposicion());
      credCuestOT.setARazon(((OCAPCuestionariosForm)form).getARazon());
      credCuestOT.setAComentarios(((OCAPCuestionariosForm)form).getAComentarios());
      credCuestOT.setCExpId(((OCAPCuestionariosForm)form).getCExpId().longValue());
      credCuestOT.setCCuestionarioId(((OCAPCuestionariosForm)form).getCCuestionarioId());
      credCuestOT.setAModificadoPor(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
      credCuestOT.setACreadoPor(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());

      credCuestLN = new OCAPCreditosCuestionariosLN(jcylUsuario);
      int num = credCuestLN.altaEvaluacion(credCuestOT);
      if (num == 0) {
        throw new Exception();
      }
      request.setAttribute("rutaVuelta", "OCAPCuestionarios.do?accion=irListar");

      OCAPConfigApp.logger.debug(getClass().getName() + " insertarEvaluacionCuestionario: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward("exito");
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward verAnalisisCuestionario(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPCuestionariosLN cuestionariosLN = null;
    OCAPCuestionariosOT cuestionarioOT = null;
    String redireccion = "irAnalisisCuestionario";
    OCAPItinerariosOT itinerarioOT = null;
    OCAPItinerariosLN itinerariosLN = null;
    OCAPSolicitudesOT solicitudesOT = null;
    OCAPSolicitudesLN solicitudesLN = null;
    long idCuestionario = 0L;
    OCAPDocumentosLN docuLN = null;
    OCAPCreditosCuestionariosOT credCuestOT = null;
    OCAPCreditosCuestionariosLN credCuestLN = null;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " verAnalisisCuestionario: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      if (request.getParameter("expId") != null) {
        session.setAttribute("expedienteId", request.getParameter("expId"));
      }
      if (session.getAttribute("expedienteId") != null) {
        solicitudesOT = new OCAPSolicitudesOT();
        solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
        solicitudesOT.setCExp_id(Long.parseLong(session.getAttribute("expedienteId").toString()));
        solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);
        usuarioOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(), solicitudesOT.getCExp_id(), jcylUsuario);
      } else {
        String usuId = jcylUsuario.getUser().getC_usr_id();
        usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);
      }

      if (usuarioOT == null) {
        return mapping.findForward("irNoExisteSolicitud");
      }
      if (request.getAttribute("idCuestionario") == null)
        idCuestionario = Long.parseLong(request.getParameter("idCuestionario") == null ? "0" : request.getParameter("idCuestionario"));
      else idCuestionario = Long.parseLong(request.getAttribute("idCuestionario").toString());

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      cuestionarioOT = new OCAPCuestionariosOT();
      cuestionarioOT.setCCuestionarioId(idCuestionario);
      cuestionarioOT = cuestionariosLN.detalleCuestionario(usuarioOT, cuestionarioOT, 0, jcylUsuario);

      if ((usuarioOT.getCProfesionalId().intValue() == 47) || (usuarioOT.getCProfesionalId().intValue() == 32))
        ((OCAPCuestionariosForm)form).setBAuditable(Constantes.NO);
      else {
        ((OCAPCuestionariosForm)form).setBAuditable(Constantes.SI);
      }
      ((OCAPCuestionariosForm)form).setDMensaje(cuestionarioOT.getDMensaje());
      ((OCAPCuestionariosForm)form).setDNombreLargo(cuestionarioOT.getDNombreLargo());
      if ((usuarioOT.getDEspec_nombre() != null) && (!"-".equals(usuarioOT.getDEspec_nombre().trim())))
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre() + ". " + usuarioOT.getDEspec_nombre());
      else
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre());
      ((OCAPCuestionariosForm)form).setDArea(cuestionarioOT.getDArea());
      ((OCAPCuestionariosForm)form).setDAreaLargo(cuestionarioOT.getDAreaLargo());

      ((OCAPCuestionariosForm)form).setCExpId(usuarioOT.getCExpId());
      ((OCAPCuestionariosForm)form).setDNombre(cuestionarioOT.getDNombre());
      ((OCAPCuestionariosForm)form).setCAreaId(cuestionarioOT.getCAreaId() + "");
      ((OCAPCuestionariosForm)form).setBObligatorio(cuestionarioOT.getBObligatorio());
      ((OCAPCuestionariosForm)form).setBContestarUsuario(cuestionarioOT.getBContestarUsuario());
      ((OCAPCuestionariosForm)form).setBSubdivision(cuestionarioOT.getBSubdivision());
      ((OCAPCuestionariosForm)form).setBIndicadores(cuestionarioOT.getBIndicadores());
      ((OCAPCuestionariosForm)form).setCEvidenciaId(cuestionarioOT.getCEvidenciaId() + "");
      ((OCAPCuestionariosForm)form).setCEvidenciaDocumentalId(cuestionarioOT.getCEvidenciaDocumentalId() + "");
      ((OCAPCuestionariosForm)form).setListaEviDocumentales(cuestionarioOT.getListaEviDocumentales());
      ((OCAPCuestionariosForm)form).setNEvidencia(cuestionarioOT.getNEvidencia());

      ((OCAPCuestionariosForm)form).setNCreditosNecesarios(cuestionarioOT.getNCreditos() + "");
      ((OCAPCuestionariosForm)form).setNCreditosObtenidos(cuestionarioOT.getNCreditosConseguidos() + "");

      ((OCAPCuestionariosForm)form).setBEvaluado(request.getParameter("evaluado") == null ? Constantes.NO : request.getParameter("evaluado"));
      credCuestLN = new OCAPCreditosCuestionariosLN(jcylUsuario);
      if (Constantes.SI.equals(((OCAPCuestionariosForm)form).getBEvaluado())) {
        ArrayList listaCreditos = credCuestLN.buscarOCAPCreditosCuestionario(idCuestionario, 1, usuarioOT.getCExpId().longValue());
        if ((listaCreditos != null) && (listaCreditos.size() > 0)) {
          credCuestOT = (OCAPCreditosCuestionariosOT)listaCreditos.get(0);
          ((OCAPCuestionariosForm)form).setBAcuerdo(credCuestOT.getBAcuerdo2());
          ((OCAPCuestionariosForm)form).setARazon(credCuestOT.getARazon2());
          ((OCAPCuestionariosForm)form).setAComentarios(credCuestOT.getAComentarios2());
          ((OCAPCuestionariosForm)form).setNCreditosEvaluados(credCuestOT.getNCreditosEvaluador() + "");
          ((OCAPCuestionariosForm)form).setBAuditoria(credCuestOT.getBAuditoria());
          ((OCAPCuestionariosForm)form).setATipoRegistro(credCuestOT.getATipoRegistro());
          ((OCAPCuestionariosForm)form).setADocumento(credCuestOT.getADocumento());
          ((OCAPCuestionariosForm)form).setANHistoria1(credCuestOT.getANHistoria1());
          ((OCAPCuestionariosForm)form).setANHistoria2(credCuestOT.getANHistoria2());
          ((OCAPCuestionariosForm)form).setANHistoria3(credCuestOT.getANHistoria3());
        } else {
          ((OCAPCuestionariosForm)form).setNCreditosEvaluados(cuestionarioOT.getNCreditosConseguidos() + "");
        }
      } else { ArrayList listaCreditos = credCuestLN.buscarOCAPCreditosCuestionario(idCuestionario, 0, usuarioOT.getCExpId().longValue());
        boolean bSimulados = false;
        float creditosSimulados = 0.0F;
        for (int nCred = 0; nCred < listaCreditos.size(); nCred++) {
          credCuestOT = (OCAPCreditosCuestionariosOT)listaCreditos.get(nCred);
          if (credCuestOT.getBSimulado()) {
            bSimulados = true;
            creditosSimulados += credCuestOT.getNCreditosEvaluador();
          } else {
            creditosSimulados += credCuestOT.getNCreditos();
          }
        }
        if (bSimulados)
          ((OCAPCuestionariosForm)form).setNCreditosEvaluados(creditosSimulados + "");
        else {
          ((OCAPCuestionariosForm)form).setNCreditosEvaluados(cuestionarioOT.getNCreditosConseguidos() + "");
        }
      }

      itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());
      DecimalFormat formato = new DecimalFormat("000000");
      ((OCAPCuestionariosForm)form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(usuarioOT.getCExpId().longValue()));
      ((OCAPCuestionariosForm)form).setDNombreItinerario(itinerarioOT.getDDescripcion());

      docuLN = new OCAPDocumentosLN();
      ArrayList listado = docuLN.buscarDocumentosCuestionario(usuarioOT.getCExpId().longValue(), idCuestionario, 0L);
      ((OCAPCuestionariosForm)form).setListaDocumentos(listado);

      ((OCAPCuestionariosForm)form).setCCuestionarioId(idCuestionario);

      OCAPConfigApp.logger.info(getClass().getName() + " verAnalisisCuestionario: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(redireccion);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward insertarAnalisisCuestionario(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();

    OCAPCreditosCuestionariosOT credCuestOT = null;
    OCAPCreditosCuestionariosLN credCuestLN = null;
    OCAPRevisionesLN revisionLN = null;
    OCAPRevisionesOT revisionOT = null;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " insertarAnalisisCuestionario: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      revisionLN = new OCAPRevisionesLN(jcylUsuario);
      revisionOT = revisionLN.buscarOCAPRevisiones(((OCAPCuestionariosForm)form).getCExpId().longValue());
      if (revisionOT.getFInicioEval() == null) {
        revisionOT = new OCAPRevisionesOT();
        revisionOT.setFInicioEval(new Date());
        revisionOT.setCExpId(((OCAPCuestionariosForm)form).getCExpId());
        revisionLN.modificaOCAPRevisiones(revisionOT);
      }

      credCuestOT = new OCAPCreditosCuestionariosOT();
      credCuestOT.setBAcuerdo2(((OCAPCuestionariosForm)form).getBAcuerdo());
      credCuestOT.setARazon2(((OCAPCuestionariosForm)form).getARazon());
      credCuestOT.setAComentarios2(((OCAPCuestionariosForm)form).getAComentarios());
      credCuestOT.setBAuditoria(((OCAPCuestionariosForm)form).getBAuditoria());
      credCuestOT.setATipoRegistro(((OCAPCuestionariosForm)form).getATipoRegistro());
      credCuestOT.setADocumento(((OCAPCuestionariosForm)form).getADocumento());
      credCuestOT.setANHistoria1(((OCAPCuestionariosForm)form).getANHistoria1());
      credCuestOT.setANHistoria2(((OCAPCuestionariosForm)form).getANHistoria2());
      credCuestOT.setANHistoria3(((OCAPCuestionariosForm)form).getANHistoria3());
      credCuestOT.setCExpId(((OCAPCuestionariosForm)form).getCExpId().longValue());
      credCuestOT.setCCuestionarioId(((OCAPCuestionariosForm)form).getCCuestionarioId());
      credCuestOT.setAModificadoPor(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
      credCuestOT.setACreadoPor(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());

      credCuestLN = new OCAPCreditosCuestionariosLN(jcylUsuario);
      int num = credCuestLN.altaAnalisis(credCuestOT);
      if (num == 0) {
        throw new Exception();
      }
      request.setAttribute("rutaVuelta", "OCAPCuestionarios.do?accion=irListar");
      OCAPConfigApp.logger.info(getClass().getName() + " insertarAnalisisCuestionario: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward("exito");
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward verSintesis(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    String mensajeError = "Se ha producido un error";
    HttpSession session = request.getSession();

    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPCreditosCuestionariosLN creditosCuestLN = null;

    ArrayList listaCreditosCuestOT = null;
    OCAPCuestionariosLN cuestionariosLN = null;

    OCAPCuestionariosOT cuestionarioOT = null;

    int idRepeticion = 0;
    long idCuestionario = 0L;

    boolean verPreguntas = false;
    String redireccion = "";

    OCAPItinerariosOT itinerarioOT = null;
    OCAPItinerariosLN itinerariosLN = null;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " verCuestionario: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      String usuId = jcylUsuario.getUser().getC_usr_id();
      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);

      if (usuarioOT == null)
      {
        return mapping.findForward("irNoExisteSolicitud");
      }

      Date dHoy = new Date();

      if (((usuarioOT.getFIncioCA() != null) && (dHoy.before(usuarioOT.getFIncioCA()))) || 
        (!dHoy.after(usuarioOT.getFFinCA())) || 
        (request.getAttribute("idCuestionario") == null))
        idCuestionario = Long.parseLong(request.getParameter("idCuestionario") == null ? "0" : request.getParameter("idCuestionario"));
      else idCuestionario = Long.parseLong(request.getAttribute("idCuestionario").toString());
      if (request.getAttribute("idRepeticion") == null)
        idRepeticion = Integer.parseInt(request.getParameter("idRepeticion") == null ? "0" : request.getParameter("idRepeticion"));
      else idRepeticion = Integer.parseInt((request.getAttribute("idRepeticion") == null) || ("".equals(request.getAttribute("idRepeticion"))) ? "0" : request.getAttribute("idRepeticion").toString());
      if ((request.getParameter("verPreguntas") != null) && (Constantes.SI.equals(request.getParameter("verPreguntas"))))
        verPreguntas = true;
      else verPreguntas = false;

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      cuestionarioOT = new OCAPCuestionariosOT();
      cuestionarioOT.setCCuestionarioId(idCuestionario);
      if (idRepeticion == -1)
        cuestionarioOT = cuestionariosLN.detalleCuestionario(usuarioOT, cuestionarioOT, 0, jcylUsuario);
      else
        cuestionarioOT = cuestionariosLN.detalleCuestionario(usuarioOT, cuestionarioOT, idRepeticion, jcylUsuario);
      creditosCuestLN = new OCAPCreditosCuestionariosLN(jcylUsuario);
      listaCreditosCuestOT = creditosCuestLN.buscarOCAPPuntosCuestionariosArea(cuestionarioOT.getCAreaId(), usuarioOT.getCExpId().longValue());
      ((OCAPCuestionariosForm)form).setListaCreditos(listaCreditosCuestOT);

      ((OCAPCuestionariosForm)form).setCExpId(usuarioOT.getCExpId());
      ((OCAPCuestionariosForm)form).setDNombre(cuestionarioOT.getDNombre());
      ((OCAPCuestionariosForm)form).setCAreaId(cuestionarioOT.getCAreaId() + "");
      ((OCAPCuestionariosForm)form).setBObligatorio(cuestionarioOT.getBObligatorio());

      ((OCAPCuestionariosForm)form).setDMensaje(cuestionarioOT.getDMensaje());

      ((OCAPCuestionariosForm)form).setDNombreLargo(cuestionarioOT.getDNombreLargo());
      if ((usuarioOT.getDEspec_nombre() != null) && (!"-".equals(usuarioOT.getDEspec_nombre().trim())))
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre() + ". " + usuarioOT.getDEspec_nombre());
      else
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre());
      ((OCAPCuestionariosForm)form).setDArea(cuestionarioOT.getDArea());
      ((OCAPCuestionariosForm)form).setDAreaLargo(cuestionarioOT.getDAreaLargo());

      itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());
      DecimalFormat formato = new DecimalFormat("000000");
      ((OCAPCuestionariosForm)form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(usuarioOT.getCExpId().longValue()));
      ((OCAPCuestionariosForm)form).setDNombreItinerario(itinerarioOT.getDDescripcion());

      ((OCAPCuestionariosForm)form).setBFinalizado("F");
      request.setAttribute("accion", Constantes.VER_DETALLE);

      redireccion = "irSintesis";
    }
    catch (Exception e)
    {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      request.setAttribute("mensaje", mensajeError);
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(redireccion);
    }
    saveErrors(request, errors);

    return mapping.findForward("error");
  }

  public ActionForward insertar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    HttpSession session = request.getSession();
    ActionErrors errors = new ActionErrors();
    OCAPUsuariosLN usuariosLN = null;
    OCAPPreguntasLN preguntasLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPCuestionariosLN cuestionariosLN = null;
    OCAPCuestionariosOT cuestionarioOT = null;
    long idPadreEvidencia = 0L;
    int cuentaPreguntasNoVacias = 0; int nEvidencia = 0;
    OCAPExpedienteCarreraLN expCarreraLN = null;
    try
    {
      OCAPConfigApp.logger.info("Inicio");
      validarAccion(mapping, form, request, response);

      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);

      if (session.getAttribute("expedienteId") != null) {
        String dniUsuario = expCarreraLN.buscarDNIUsuarioExpediente(Long.parseLong(session.getAttribute("expedienteId").toString()));
        usuarioOT = usuariosLN.datosPersonalesUsuario(dniUsuario, Long.parseLong(session.getAttribute("expedienteId").toString()), jcylUsuario);
      } else {
        usuarioOT = usuariosLN.datosPersonalesUsuario(jcylUsuario.getUser().getC_usr_id(), 0L, jcylUsuario);
      }

      if (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())) {
        usuarioOT = (OCAPUsuariosOT)session.getAttribute("UsuarioPruebaItinerario");
      }
      preguntasLN = new OCAPPreguntasLN(jcylUsuario);

      long idCuestionario = Long.parseLong(request.getParameter("idCuestionario") == null ? "0" : request.getParameter("idCuestionario"));
      int idRepeticion = Integer.parseInt(request.getParameter("idRepeticion") == null ? "0" : request.getParameter("idRepeticion"));
      if (request.getAttribute("nEvidencia") == null)
        nEvidencia = Integer.parseInt(request.getParameter("nEvidencia") == null ? "0" : request.getParameter("nEvidencia"));
      else nEvidencia = Integer.parseInt(request.getAttribute("nEvidencia").toString());
      if (request.getAttribute("idPadreEvidencia") == null)
        idPadreEvidencia = Long.parseLong(request.getParameter("idPadreEvidencia") == null ? "0" : request.getParameter("idPadreEvidencia"));
      else idPadreEvidencia = Long.parseLong(request.getAttribute("idPadreEvidencia").toString());
      boolean bFinalizar = false;
      if ((request.getParameter("finalizar") != null) && ("F".equals(request.getParameter("finalizar"))))
        bFinalizar = true;
      if (idRepeticion < 0) {
        OCAPConfigApp.logger.debug(getClass().getName() + " insertar: IDRepet negativo!");
      }
      ((OCAPCuestionariosForm)form).setCPadreEvidenciaId(idPadreEvidencia + "");

      ArrayList listaAux = (ArrayList)session.getAttribute(Constantes.COMBO_CUESTIONARIOS);
      ArrayList listaAux2 = new ArrayList();
      if (listaAux != null) {
        for (int i = 0; i < listaAux.size(); i++) {
          cuestionarioOT = (OCAPCuestionariosOT)listaAux.get(i);
          if ((idCuestionario == cuestionarioOT.getCCuestionarioId()) || (idCuestionario == cuestionarioOT.getCCuestionarioIntermedioId())) {
            listaAux2 = cuestionarioOT.getListaRepeticiones();
            if (listaAux2 == null)
              listaAux2 = new ArrayList();
            break;
          }
        }
      }

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      if (!Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())) {
        cuentaPreguntasNoVacias = cuestionariosLN.insertarCuestionario(usuarioOT, idCuestionario, idRepeticion, listaAux2, ((OCAPCuestionariosForm)form).getCadenaRespuestas(), bFinalizar, request.getParameter("idCuestionarioDespuesIntermedio1"), nEvidencia, idPadreEvidencia, jcylUsuario, session);
      } else {
        cuentaPreguntasNoVacias = 1;
        cuestionariosLN.guardarCuestionarioPrueba(usuarioOT, idCuestionario, idRepeticion, listaAux2, ((OCAPCuestionariosForm)form).getCadenaRespuestas(), bFinalizar, request.getParameter("idCuestionarioDespuesIntermedio1"), nEvidencia, idPadreEvidencia, jcylUsuario, session);
      }

      cuestionarioOT = new OCAPCuestionariosOT();
      cuestionarioOT.setCCuestionarioId(idCuestionario);
      cuestionarioOT = cuestionariosLN.detalleCuestionario(usuarioOT, cuestionarioOT, idRepeticion, jcylUsuario);
      OCAPConfigApp.logger.info(getClass().getName() + " insertar: se consultan lso detalles de un cuestionario");
      ((OCAPCuestionariosForm)form).setCPlantillaId(String.valueOf(cuestionarioOT.getCPlantillaId()));

      Enumeration listaParametros = request.getParameterNames();
      int contadorPantallasIntermedias = 0;
      while (listaParametros.hasMoreElements()) {
        if (listaParametros.nextElement().toString().indexOf("idCuestionarioDespuesIntermedio") != -1) {
          contadorPantallasIntermedias++;
        }

      }

      if ((Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol())) || (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())))
      {
        if ((bFinalizar) && ((cuestionarioOT.getCEvidenciaId() != 0L) || ((cuestionarioOT.getListaEviDocumentales() != null) && (cuestionarioOT.getListaEviDocumentales().size() != 0)))) {
          request.setAttribute("idCuestionario", cuestionarioOT.getCCuestionarioId() + "");
          return verCuestionario(mapping, form, request, response);
        }
        if ((bFinalizar) && (!Constantes.SI.equals(request.getParameter("saltarPonderacion"))) && (cuestionarioOT.getDPonderacion() != null))
        {
          request.setAttribute("idCuestionario", cuestionarioOT.getCCuestionarioId() + "");

          request.setAttribute("idRepeticion", idRepeticion + "");

          return verPonderacion(mapping, form, request, response);
        }if (contadorPantallasIntermedias > 0)
        {
          request.setAttribute("idCuestionario", request.getParameter("idCuestionarioDespuesIntermedio1").toString());

          request.setAttribute("idRepeticion", "-1");
          for (int num = 1; num < contadorPantallasIntermedias; num++)
            request.setAttribute("idCuestionarioDespuesIntermedio" + (num + 1), request.getParameter("idCuestionarioDespuesIntermedio" + (num + 1)).toString());
          return verCuestionario(mapping, form, request, response);
        }

        if (Constantes.SI.equals(cuestionarioOT.getBSubdivision())) {
          if (((OCAPCuestionariosForm)form).getCadenaRespuestas() != null) {
            StringTokenizer token = new StringTokenizer(((OCAPCuestionariosForm)form).getCadenaRespuestas(), "&");
            StringTokenizer token2 = null;
            while (token.hasMoreTokens()) {
              token2 = new StringTokenizer(token.nextToken(), "=");
              String aux1 = "";
              String aux2 = "";
              if (token2.hasMoreTokens())
                aux1 = token2.nextToken();
              if (token2.hasMoreTokens())
                aux2 = token2.nextToken();
              request.setAttribute(aux1, aux2);
            }
          }
          request.setAttribute("idCuestionario", request.getAttribute("pregunta" + --cuentaPreguntasNoVacias + "_0_0").toString());
          request.setAttribute("idRepeticion", "0");
          return verCuestionario(mapping, form, request, response);
        }if (cuestionarioOT.getCCuestionarioPadreId() != 0L) {
          OCAPCuestionariosOT cuestionarioPadreOT = new OCAPCuestionariosOT();
          cuestionarioPadreOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioPadreId());
          cuestionarioPadreOT = cuestionariosLN.detalleCuestionario(usuarioOT, cuestionarioPadreOT, 0, jcylUsuario);

          if (Constantes.SI.equals(cuestionarioPadreOT.getBSubdivision())) {
            if (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol()))
            {
              return irListarPruebas(mapping, form, request, response);
            }

            return irListar(mapping, form, request, response);
          }
          if (cuestionarioOT.getNRepeticiones().intValue() > 1) {
            request.setAttribute("idCuestionario", cuestionarioOT.getCCuestionarioId() + "");
            request.setAttribute("saltarIntermedio1", Constantes.SI);
            request.setAttribute("idRepeticion", "0");
            return verCuestionario(mapping, form, request, response);
          }

          request.setAttribute("idCuestionario", cuestionarioOT.getCCuestionarioPadreId() + "");
          request.setAttribute("saltarIntermedio1", Constantes.SI);
          return verCuestionario(mapping, form, request, response);
        }

        if (cuestionarioOT.getNRepeticiones().intValue() > 1) {
          request.setAttribute("idCuestionario", cuestionarioOT.getCCuestionarioId() + "");
          request.setAttribute("saltarIntermedio1", Constantes.SI);
          request.setAttribute("idRepeticion", "0");
          return verCuestionario(mapping, form, request, response);
        }
        if (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol()))
        {
          return irListarPruebas(mapping, form, request, response);
        }

        return irListar(mapping, form, request, response);
      }

      if (Constantes.OCAP_FQS_ADTVO.equals(jcylUsuario.getUser().getRol()))
        request.setAttribute("rutaVuelta", "OCAPNuevaSolicitud.do?accion=irDetalleFqs&CExp_id=" + usuarioOT.getCExpId().longValue() + "&fase=" + Constantes.FASE_CA_ESCANEADA);
      else if (Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol())) {
        request.setAttribute("rutaVuelta", "OCAPCuestionarios.do?accion=irListar");
      }

      OCAPConfigApp.logger.info(getClass().getName() + " insertar: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward("exito");
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward irProteccionDatos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();
    OCAPUsuariosOT usuariosOT = null;
    OCAPExpedientecarreraOT expedienteCarreraOT = null;
    String fw = null;
    try
    {
      OCAPConfigApp.logger.debug(getClass().getName() + " irProteccionDatos: Inicio");
      validarAccion(mapping, form, request, response);

      JCYLUsuario jcylUsuario = (JCYLUsuario)request.getSession().getAttribute("JCYLUsuario");

      OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);

      usuariosOT = usuariosLN.buscarOCAPUsuariosPorNIF(jcylUsuario.getUser().getC_usr_id());

      if ((usuariosOT == null) || (usuariosOT.getCUsrId() == null) || (usuariosOT.getCUsrId().longValue() == 0L)) {
        fw = "irNoExisteSolicitud";
      }
      else
      {
        String cConvocatoriaId = obtenerConvocatoria(request, ((OCAPCuestionariosForm)form).getCConvocatoriaId());

        OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
        expedienteCarreraOT = expedienteCarreraLN.buscarExpedienteCarreraPorUsuarioConvocatoria(usuariosOT.getCUsrId(), Long.valueOf(cConvocatoriaId));

        if ((expedienteCarreraOT == null) || (expedienteCarreraOT.getCExpId() == null) || (expedienteCarreraOT.getCExpId().longValue() == 0L)) {
          fw = "irNoExisteSolicitud"; } else {
          if ((expedienteCarreraOT.getBLopdCd() != null) && (expedienteCarreraOT.getBLopdCd().equals(Constantes.SI))) {
            return irListar(mapping, form, request, response);
          }

          expedienteCarreraOT.setCItinerarioId(Long.parseLong(((OCAPCuestionariosForm)form).getCItinerarioId()));
          expedienteCarreraOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());
          expedienteCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);
          fw = "irProteccionDatos";
          request.setAttribute("fase", "Inicio Desempeño Competencia");
        }
      }

      OCAPConfigApp.logger.debug("irProteccionDatos: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(fw);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward verPonderacion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    String mensajeError = "Se ha producido un error";
    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPCuestionariosLN cuestionariosLN = null;
    OCAPCuestionariosOT cuestionarioOT = null;
    OCAPCuestionariosOT cuestionarioAuxEviOT = null;

    long idCuestionario = 0L;
    long idPadreEvidencia = 0L;
    int idRepeticion = 0;

    OCAPEstadosCuestionarioLN estCuesLN = null;

    ArrayList listaCalculos = null;
    OCAPItinerariosOT itinerarioOT = null;
    OCAPItinerariosLN itinerariosLN = null;
    OCAPSolicitudesOT solicitudesOT = null;
    OCAPSolicitudesLN solicitudesLN = null;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " verPonderacion: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      if (session.getAttribute("expedienteId") != null) {
        solicitudesOT = new OCAPSolicitudesOT();
        solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
        solicitudesOT.setCExp_id(Long.parseLong(session.getAttribute("expedienteId").toString()));
        solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);
        usuarioOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(), solicitudesOT.getCExp_id(), jcylUsuario);
      } else {
        String usuId = jcylUsuario.getUser().getC_usr_id();
        usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);
      }

      if (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())) {
        usuarioOT = (OCAPUsuariosOT)session.getAttribute("UsuarioPruebaItinerario");
      }
      if (usuarioOT == null) {
        if (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())) {
          return mapping.findForward("irElegirItinerario");
        }
        return mapping.findForward("irNoExisteSolicitud");
      }

      if (request.getAttribute("idCuestionario") != null)
        idCuestionario = Long.parseLong(request.getAttribute("idCuestionario").toString());
      else idCuestionario = Long.parseLong(request.getParameter("idCuestionario") == null ? "0" : request.getParameter("idCuestionario"));
      if (request.getAttribute("idRepeticion") != null)
        idRepeticion = Integer.parseInt((request.getAttribute("idRepeticion") == null) || ("".equals(request.getAttribute("idRepeticion"))) ? "0" : request.getAttribute("idRepeticion").toString());
      else idRepeticion = Integer.parseInt(request.getParameter("idRepeticion") == null ? "0" : request.getParameter("idRepeticion"));
      if (request.getAttribute("idPadreEvidencia") == null)
        idPadreEvidencia = Long.parseLong(request.getParameter("idPadreEvidencia") == null ? "0" : request.getParameter("idPadreEvidencia"));
      else idPadreEvidencia = Long.parseLong(request.getAttribute("idPadreEvidencia").toString());

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      cuestionarioOT = new OCAPCuestionariosOT();
      cuestionarioOT.setCCuestionarioId(idCuestionario);
      cuestionarioOT.setCPadreEvidenciaId(idPadreEvidencia);
      cuestionarioOT.setBVerPonderacion(true);
      cuestionarioOT = cuestionariosLN.detalleCuestionario(usuarioOT, cuestionarioOT, idRepeticion, jcylUsuario);

      ((OCAPCuestionariosForm)form).setCExpId(usuarioOT.getCExpId());
      ((OCAPCuestionariosForm)form).setDMensaje(cuestionarioOT.getDMensaje());

      ((OCAPCuestionariosForm)form).setCCuestionarioId(idCuestionario);
      ((OCAPCuestionariosForm)form).setDNombreLargo(cuestionarioOT.getDNombreLargo());
      ((OCAPCuestionariosForm)form).setDArea(cuestionarioOT.getDArea());
      ((OCAPCuestionariosForm)form).setDAreaLargo(cuestionarioOT.getDAreaLargo());
      ((OCAPCuestionariosForm)form).setBObligatorio(Constantes.SI);
      if (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol()))
        ((OCAPCuestionariosForm)form).setNPuntosConseguidos(session.getAttribute("puntosPruebas" + idCuestionario + "_" + idRepeticion + "_" + idPadreEvidencia).toString());
      else
        ((OCAPCuestionariosForm)form).setNPuntosConseguidos(Float.toString(cuestionarioOT.getNPuntosConseguidos()));
      if (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol()))
        ((OCAPCuestionariosForm)form).setNCreditosItinerario(session.getAttribute("creditosPruebas" + idCuestionario + "_" + idRepeticion + "_" + idPadreEvidencia).toString());
      else
        ((OCAPCuestionariosForm)form).setNCreditosItinerario(String.valueOf(cuestionarioOT.getNCreditosConseguidos()));
      ((OCAPCuestionariosForm)form).setNCreditosPosiblesItinerario(String.valueOf(cuestionarioOT.getNCreditos()));
      ((OCAPCuestionariosForm)form).setDPonderacion(cuestionarioOT.getDPonderacion());
      ((OCAPCuestionariosForm)form).setCEvidenciaId(Long.toString(cuestionarioOT.getCEvidenciaId()));
      ((OCAPCuestionariosForm)form).setCEvidenciaDocumentalId(String.valueOf(cuestionarioOT.getCEvidenciaDocumentalId()));
      ((OCAPCuestionariosForm)form).setListaEviDocumentales(cuestionarioOT.getListaEviDocumentales());
      ((OCAPCuestionariosForm)form).setNEvidencia(cuestionarioOT.getNEvidencia());
      String evidencia = "";
      if ((cuestionarioOT.getDEvidencia() != null) && (!"".equals(cuestionarioOT.getDEvidencia().trim())))
        evidencia = cuestionarioOT.getDEvidencia();
      ((OCAPCuestionariosForm)form).setDEvidencia(evidencia);

      OCAPCalculoCredCuestionariosLN calculoCredCuestLN = new OCAPCalculoCredCuestionariosLN(jcylUsuario);

      listaCalculos = calculoCredCuestLN.buscarOCAPCalculoCredCuestionario(idCuestionario);

      ((OCAPCuestionariosForm)form).setListaCalculos(listaCalculos);

      String estado = "";
      if (Constantes.OCAP_PRUEBA_ITINERARIO.equals(jcylUsuario.getUser().getRol())) {
        estado = "F";
      }
      else {
        estCuesLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
        estado = estCuesLN.buscarEstadoPonderacion(usuarioOT.getCExpId().longValue(), idCuestionario, idRepeticion);
      }
      ((OCAPCuestionariosForm)form).setBFinalizado(estado);

		if ((listaCalculos.size() > 1)
				|| ((listaCalculos.size() == 1)
						&& (((OCAPCalculoCredCuestionariosOT) listaCalculos.get(0)).getNCreditos() == null))
				|| (cuestionarioOT.getCPlantillaId() == 11) || (estado.equals("F"))) {
			request.setAttribute("autoCalcularTexto", Constantes.SI);
			if (estado.equals("F")) {
				if(cuestionarioOT.getNAutoponderacionMax() != 0) {
					((OCAPCuestionariosForm)form).setNCreditosPosiblesItinerario(String.valueOf(cuestionarioOT.getNAutoponderacionMax()));
				}
			}
		} else {
			//Esto es para que el usuario tenga que introducir su autoponderacion
			request.setAttribute("autoCalcularTexto", Constantes.NO);
			if(cuestionarioOT.getNAutoponderacionMax() != 0) {
				((OCAPCuestionariosForm)form).setNCreditosPosiblesItinerario(String.valueOf(cuestionarioOT.getNAutoponderacionMax()));
			}
		}

      OCAPCuestionariosOT cuestionarioAux2OT = null;
      ArrayList listaAux = (ArrayList)session.getAttribute(Constantes.COMBO_CUESTIONARIOS);
      ArrayList listaAux2 = new ArrayList();
      if (listaAux != null) {
        for (int i = 0; i < listaAux.size(); i++) {
          cuestionarioAux2OT = (OCAPCuestionariosOT)listaAux.get(i);
          if (idCuestionario == cuestionarioAux2OT.getCCuestionarioId()) {
            listaAux2 = cuestionarioAux2OT.getListaRepeticiones();
            if (listaAux2 == null)
              listaAux2 = new ArrayList();
            break;
          }if (idPadreEvidencia == cuestionarioAux2OT.getCCuestionarioId()) {
            cuestionarioAuxEviOT = new OCAPCuestionariosOT();
            cuestionarioAuxEviOT.setCCuestionarioId(cuestionarioAux2OT.getCCuestionarioId());
            cuestionarioAuxEviOT.setCEvidenciaId(cuestionarioAux2OT.getCEvidenciaId());
            cuestionarioAuxEviOT.setCCuestionarioPadreId(cuestionarioAux2OT.getCCuestionarioPadreId());
            cuestionarioAuxEviOT.setNRepeticiones(cuestionarioAux2OT.getNRepeticiones());
          }
        }
      }

      if (cuestionarioOT.getCCuestionarioPadreId() != 0L) {
        OCAPCuestionariosOT cuestionarioPadreOT = new OCAPCuestionariosOT();
        cuestionarioPadreOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioPadreId());
        cuestionarioPadreOT = cuestionariosLN.detalleCuestionario(usuarioOT, cuestionarioPadreOT, 0, jcylUsuario);

        if (!Constantes.SI.equals(cuestionarioPadreOT.getBSubdivision())) {
          if (cuestionarioOT.getNRepeticiones().intValue() > 1) {
            request.setAttribute("verPadre", String.valueOf(cuestionarioOT.getCCuestionarioId()));
            request.setAttribute("verPadreRepe", "0");
          }
          else if ((listaAux2 != null) && (listaAux2.size() != 0)) {
            OCAPRepeticionesCuestionariosOT repeCuesOT = (OCAPRepeticionesCuestionariosOT)listaAux2.get(0);
            request.setAttribute("verPadre", String.valueOf(repeCuesOT.getCCuestionarioId()));
            request.setAttribute("verPadreRepe", String.valueOf(repeCuesOT.getNRepeticion()));
          } else {
            request.setAttribute("verPadre", String.valueOf(cuestionarioOT.getCCuestionarioPadreId()));
            request.setAttribute("verPadreRepe", "1");
          }
        }
      }
      else if (cuestionarioOT.getNRepeticiones().intValue() > 1) {
        request.setAttribute("verPadre", String.valueOf(cuestionarioOT.getCCuestionarioId()));
        request.setAttribute("verPadreRepe", "0");
      } else if ((cuestionarioAuxEviOT != null) && (cuestionarioAuxEviOT.getCEvidenciaId() == cuestionarioOT.getCCuestionarioId())) {
        if (cuestionarioAuxEviOT.getNRepeticiones().intValue() > 1) {
          request.setAttribute("verPadre", String.valueOf(cuestionarioAuxEviOT.getCCuestionarioId()));
          request.setAttribute("verPadreRepe", "0");
        } else if (cuestionarioAuxEviOT.getCCuestionarioPadreId() != 0L) {
          request.setAttribute("verPadre", String.valueOf(cuestionarioAuxEviOT.getCCuestionarioPadreId()));
          request.setAttribute("verPadreRepe", "0");
        }
      }

      if ((usuarioOT.getDEspec_nombre() != null) && (!"-".equals(usuarioOT.getDEspec_nombre().trim())))
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre() + ". " + usuarioOT.getDEspec_nombre());
      else {
        ((OCAPCuestionariosForm)form).setDTitulo(usuarioOT.getDProfesional_nombre());
      }
      itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());
      DecimalFormat formato = new DecimalFormat("000000");
      ((OCAPCuestionariosForm)form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(usuarioOT.getCExpId().longValue()));
      ((OCAPCuestionariosForm)form).setDNombreItinerario(itinerarioOT.getDDescripcion());

      request.setAttribute("tipoAccion", Constantes.VER_DETALLE);

      if ((usuarioOT.getFFinCA() != null) && (new Date().after(usuarioOT.getFFinCA()))) {
        ((OCAPCuestionariosForm)form).setBFinalizado("F");
      }

      OCAPConfigApp.logger.info(getClass().getName() + " verPonderacion: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      request.setAttribute("mensaje", mensajeError);
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward("irPonderacion");
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward guardarPonderacion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPCuestionariosLN cuestionariosLN = null;
    OCAPCuestionariosOT cuestionarioOT = null;
    OCAPCreditosCuestionariosLN creditosCuestLN = null;
    OCAPCreditosCuestionariosOT creditosCuestOT = null;

    int idRepeticion = 0;

    OCAPExpedientesCAsOT expedienteCAOT = null;
    OCAPEstadosCuestionarioLN estCuesLN = null;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " guardarPonderacion: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      String usuId = jcylUsuario.getUser().getC_usr_id();
      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);

      if (usuarioOT == null) {
        return mapping.findForward("irNoExisteSolicitud");
      }
      boolean bFinalizar = false;
      if ((request.getParameter("finalizar") != null) && ("F".equals(request.getParameter("finalizar")))) {
        bFinalizar = true;
      }
      
      boolean bAutoponderacion = false;
      if ((request.getParameter("autoponderacion") != null) && ("S".equals(request.getParameter("autoponderacion")))) {
          bAutoponderacion = true;
        }

      creditosCuestLN = new OCAPCreditosCuestionariosLN(jcylUsuario);
      creditosCuestOT = new OCAPCreditosCuestionariosOT();
      creditosCuestOT.setCExpId(((OCAPCuestionariosForm)form).getCExpId().longValue());

      creditosCuestOT.setCCuestionarioId(((OCAPCuestionariosForm)form).getCCuestionarioId());

      creditosCuestOT.setNRepeticion(Integer.parseInt(((OCAPCuestionariosForm)form).getIdRepeticion()));

      creditosCuestOT.setNCreditos(Float.parseFloat(((OCAPCuestionariosForm)form).getNCreditosItinerario()));
      creditosCuestLN.modificacionOCAPCreditosCuestionarios(creditosCuestOT, bAutoponderacion);

      if (bFinalizar) {
        estCuesLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
        estCuesLN.modificacionOCAPEstadosCuestionario(usuarioOT.getCExpId().longValue(), ((OCAPCuestionariosForm)form).getCCuestionarioId(), Integer.parseInt(((OCAPCuestionariosForm)form).getIdRepeticion()), "F", jcylUsuario.getUsuario().getC_usr_id());
      }

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      cuestionarioOT = new OCAPCuestionariosOT();
      cuestionarioOT.setCCuestionarioId(((OCAPCuestionariosForm)form).getCCuestionarioId());
      cuestionarioOT = cuestionariosLN.detalleCuestionario(usuarioOT, cuestionarioOT, idRepeticion, jcylUsuario);
      if (cuestionarioOT.getNRepeticiones().intValue() > 1) {
        request.setAttribute("idCuestionario", cuestionarioOT.getCCuestionarioId() + "");
        request.setAttribute("saltarIntermedio1", Constantes.SI);
        request.setAttribute("idRepeticion", "0");
        return verCuestionario(mapping, form, request, response);
      }
      if (cuestionarioOT.getCCuestionarioPadreId() != 0L)
      {
        request.setAttribute("idCuestionario", cuestionarioOT.getCCuestionarioPadreId() + "");
        request.setAttribute("saltarIntermedio1", Constantes.SI);
        request.setAttribute("idRepeticion", "0");
        return verCuestionario(mapping, form, request, response);
      }

      return irListar(mapping, form, request, response);
    }
    catch (Exception e)
    {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));

      if ((errors == null) || (errors.isEmpty()))
      {
        return mapping.findForward("exito");
      }
      saveErrors(request, errors);
    }

    return mapping.findForward("fallo");
  }

  public ActionForward finalizarProceso(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPEstadosCuestionarioLN estCuestionarioLN = null;
    OCAPCuestionariosLN cuestionariosLN = null;

    OCAPAreasItinerariosOT areaOT = null;
    ArrayList listaAreas = null;
    boolean bFinalizadoUnoPorArea = true;
    OCAPExpedienteCarreraLN expCarreraLN = null;
    OCAPExpedientecarreraOT expedienteCarreraOT = null;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " finalizarProceso: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      String usuId = jcylUsuario.getUser().getC_usr_id();
      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);

      if (usuarioOT == null)
      {
        return mapping.findForward("irNoExisteSolicitud");
      }

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      listaAreas = cuestionariosLN.listadoOCAPCuestionariosPorItinerario(usuarioOT, jcylUsuario);
      ((OCAPCuestionariosForm)form).setListadoAreas(listaAreas);
      estCuestionarioLN = new OCAPEstadosCuestionarioLN(jcylUsuario);

      for (int i = 0; (i < listaAreas.size()) && (bFinalizadoUnoPorArea); i++) {
        areaOT = (OCAPAreasItinerariosOT)listaAreas.get(i);
        int numFin = estCuestionarioLN.contarCuestionariosFinalizadosArea(usuarioOT.getCExpId().longValue(), areaOT.getCAreaId().longValue());
        if (numFin == 0)
          bFinalizadoUnoPorArea = false;
      }
      if (bFinalizadoUnoPorArea)
      {
        expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
        expedienteCarreraOT = new OCAPExpedientecarreraOT();
        expedienteCarreraOT.setCExpId(usuarioOT.getCExpId());
        expedienteCarreraOT.setFFinCa(new Date());
        expedienteCarreraOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());
        expCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);
      }

      session.setAttribute(Constantes.COMBO_CUESTIONARIOS, new ArrayList());

      request.setAttribute("rutaVuelta", "OCAPCuestionarios.do?accion=irListar");

      OCAPConfigApp.logger.info(getClass().getName() + " finalizarProceso: Fin");
    }
    catch (Exception e)
    {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward("exito");
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward irDossier(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;

    OCAPItinerariosLN itinerariosLN = null;
    OCAPItinerariosOT itinerarioOT = null;

    ArrayList listaEvidencias = null;
    OCAPDocumentosLN docuLN = null;
    OCAPDossierLN oCAPDossierLN = null;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " irDossier: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      String usuId = jcylUsuario.getUser().getC_usr_id();
      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);

      if (usuarioOT == null)
      {
        return mapping.findForward("irNoExisteSolicitud");
      }
      ((OCAPCuestionariosForm)form).setDNombreUsuario(usuarioOT.getDNombre() + " " + usuarioOT.getDApellido1());

      ((OCAPCuestionariosForm)form).setCDni(usuarioOT.getCDni());
      ((OCAPCuestionariosForm)form).setCDniReal(usuarioOT.getCDniReal());

      ((OCAPCuestionariosForm)form).setDProfesional_nombre(usuarioOT.getDProfesional_nombre());
      if ((usuarioOT.getDEspec_nombre() != null) && (!"".equals(usuarioOT.getDEspec_nombre()))) {
        ((OCAPCuestionariosForm)form).setDProfesional_nombre(((OCAPCuestionariosForm)form).getDProfesional_nombre() + "/" + usuarioOT.getDEspec_nombre());
      }
      ((OCAPCuestionariosForm)form).setDCentrotrabajo_nombre(usuarioOT.getDCentrotrabajo_nombre());

      ((OCAPCuestionariosForm)form).setFModicacion(DateUtil.convertDateToString(new Date()));

      itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      Long cEspecId = null;
      if (usuarioOT.getCEspecId() != null) {
        cEspecId = new Long(usuarioOT.getCEspecId().intValue());
      }

      itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());
      ((OCAPCuestionariosForm)form).setNEvidencias(new Integer(itinerarioOT.getNEvidencias()));
      oCAPDossierLN = new OCAPDossierLN(jcylUsuario);
      listaEvidencias = oCAPDossierLN.buscarOCAPDossier(usuarioOT.getCExpId().longValue());

      if (listaEvidencias == null)
        listaEvidencias = new ArrayList();
      OCAPEncuestaCalidadOT encuestaOT = null;
      for (int i = listaEvidencias.size() + 1; i <= itinerarioOT.getNEvidencias(); i++) {
        encuestaOT = new OCAPEncuestaCalidadOT();
        encuestaOT.setCEncuestaId(2);
        encuestaOT.setCPreguntaId(i);
        encuestaOT.setBEntregado(Constantes.NO);
        listaEvidencias.add(encuestaOT);
      }

      OCAPDocumentosOT docuOT = null;
      ArrayList listaDefinitaEvidencias = new ArrayList();
      for (int i = 1; i <= listaEvidencias.size(); i++) {
        encuestaOT = (OCAPEncuestaCalidadOT)listaEvidencias.get(i - 1);

        docuLN = new OCAPDocumentosLN();
        docuOT = docuLN.buscarDocumentoExpedienteTitulo(usuarioOT.getCExpId().longValue(), Integer.toString(i));
        if (docuOT != null) {
          encuestaOT.setBEscaneado(Constantes.SI);
          encuestaOT.setNDocumentos(docuOT.getNDocumentos() != null ? docuOT.getNDocumentos().toString() : "0");
        } else {
          encuestaOT.setBEscaneado(Constantes.NO);
          encuestaOT.setNDocumentos("0");
        }
        listaDefinitaEvidencias.add(encuestaOT);
      }
      ((OCAPCuestionariosForm)form).setListaEvidencias(listaDefinitaEvidencias);

      DecimalFormat formato = new DecimalFormat("000000");
      ((OCAPCuestionariosForm)form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(usuarioOT.getCExpId().longValue()));

      if ((usuarioOT.getFFinCA() != null) && (usuarioOT.getFFinCA().before(new Date())))
        request.setAttribute("tipoAccion", Constantes.VER_DETALLE);
      else {
        request.setAttribute("tipoAccion", Constantes.INSERTAR);
      }
      OCAPConfigApp.logger.info(getClass().getName() + " irDossier: Fin");
    }
    catch (Exception e)
    {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward("irDossier");
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward guardarDossier(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPItinerariosLN itinerariosLN = null;
    OCAPItinerariosOT itinerarioOT = null;
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " guardarDossier: Inicio");
      HttpSession session = request.getSession();
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      String usuId = jcylUsuario.getUser().getC_usr_id();
      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);

      if (usuarioOT == null)
      {
        return mapping.findForward("irNoExisteSolicitud");
      }
      OCAPDossierLN oCAPDossierLN = new OCAPDossierLN(jcylUsuario);
      itinerariosLN = new OCAPItinerariosLN(jcylUsuario);
      Long cEspecId = null;
      if (usuarioOT.getCEspecId() != null) {
        cEspecId = new Long(usuarioOT.getCEspecId().intValue());
      }

      itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());

      oCAPDossierLN.guardarDossier(usuarioOT.getCExpId().longValue(), itinerarioOT.getNEvidencias(), usuId, ((OCAPCuestionariosForm)form).getIdEvidencia());

      OCAPConfigApp.logger.info(getClass().getName() + " guardarDossier: Fin");
    }
    catch (Exception e)
    {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return irListar(mapping, form, request, response);
    }

    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward irInformeCC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();
    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPCuestionariosLN cuestionariosLN = null;
    OCAPCuestionariosOT cuestionarioAuxOT = null;
    OCAPAreasItinerariosOT areaOT = null;
    ArrayList listaAreas = null;
    ArrayList listaCuestionarios = null;
    OCAPSolicitudesOT solicitudesOT = null;
    OCAPSolicitudesLN solicitudesLN = null;
    String mappingForward = "irGenerarInformeCC";
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " generarInforme: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      if (request.getParameter("expId") != null) {
        solicitudesOT = new OCAPSolicitudesOT();
        solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
        solicitudesOT.setCExp_id(Long.parseLong(request.getParameter("expId").toString()));
        solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);
        usuarioOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(), solicitudesOT.getCExp_id(), jcylUsuario);
      }
      else {
        return mapping.findForward("irNoExisteSolicitud");
      }

      DecimalFormat formato = new DecimalFormat("000000");
      ((OCAPCuestionariosForm)form).setCodigoId(Constantes.CODIGO_EVALUADO + formato.format(usuarioOT.getCExpId().longValue()));
      ((OCAPCuestionariosForm)form).setDCategoriaEspecialidadEvaluado(usuarioOT.getDProfesional_nombre());
      ((OCAPCuestionariosForm)form).setDGrado_des(usuarioOT.getDGrado_des());
      ((OCAPCuestionariosForm)form).setDNombreUsuario(usuarioOT.getDNombre());
      ((OCAPCuestionariosForm)form).setDApellido1(usuarioOT.getDApellido1());
      ((OCAPCuestionariosForm)form).setCDni(usuarioOT.getCDniReal());
      ((OCAPCuestionariosForm)form).setDCentrotrabajo_nombre(usuarioOT.getDCentrotrabajo_nombre());
      ((OCAPCuestionariosForm)form).setCExpId(usuarioOT.getCExpId());

      ((OCAPCuestionariosForm)form).setBReconocimientoGrado(solicitudesOT.getBReconocimientoGrado());
      if (solicitudesOT.getBReconocimientoGrado() != null)
        request.setAttribute("gradoCCdado", Constantes.SI);
      else {
        request.setAttribute("gradoCCdado", Constantes.NO);
      }
      if (usuarioOT.getFInformeCC() == null) {
        ((OCAPCuestionariosForm)form).setFInformeCC(DateUtil.convertDateToString(new Date()));
        request.setAttribute("generar", Constantes.SI);
      } else {
        ((OCAPCuestionariosForm)form).setFInformeCC(DateUtil.convertDateToString(usuarioOT.getFInformeCC()));
        ((OCAPCuestionariosForm)form).setAEspecificaciones(usuarioOT.getAEspecificacionesCC());
      }

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      listaAreas = cuestionariosLN.listadoOCAPCuestionariosPorItinerario(usuarioOT, jcylUsuario);

      float nCreditosEvaluados = 0.0F;

      for (int i = 0; i < listaAreas.size(); i++) {
        areaOT = (OCAPAreasItinerariosOT)listaAreas.get(i);
        listaCuestionarios = areaOT.getListaCuestionarios();
        for (int j = 0; j < listaCuestionarios.size(); j++) {
          cuestionarioAuxOT = (OCAPCuestionariosOT)listaCuestionarios.get(j);
          nCreditosEvaluados += cuestionarioAuxOT.getNCreditosEvaluados();
        }
      }

      nCreditosEvaluados = (float)(Math.rint(nCreditosEvaluados * 100.0F) / 100.0D);
      ((OCAPCuestionariosForm)form).setNCreditosEvaluados(nCreditosEvaluados + "");

      OCAPConfigApp.logger.info(getClass().getName() + " generarInforme: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(mappingForward);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward aceptarGrado(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    HttpSession session = request.getSession();
    ActionErrors errors = new ActionErrors();

    String fw = "";
    try
    {
      OCAPConfigApp.logger.info(getClass().getName() + " aceptarGrado: Inicio");
      validarAccion(mapping, form, request, response);

      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      OCAPExpedienteCarreraLN expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
      OCAPExpedientecarreraOT expCarreraOT = new OCAPExpedientecarreraOT();

      expCarreraOT.setCExpId(((OCAPCuestionariosForm)form).getCExpId());
      expCarreraOT.setBReconocimientoGrado(((OCAPCuestionariosForm)form).getBReconocimientoGrado());
      expCarreraOT.setFFinCc(new Date());
      expCarreraOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());

      if (Constantes.SI.equals(expCarreraOT.getBReconocimientoGrado()))
        expCarreraOT.setCEstadoId(12);
      else {
        expCarreraOT.setCEstadoId(11);
      }
      expCarreraLN.modificacionOCAPExpedientecarrera(expCarreraOT, false);

      request.setAttribute("rutaVuelta", "OCAPCuestionarios.do?accion=irInformeCC&expId=" + ((OCAPCuestionariosForm)form).getCExpId().longValue() + " ");
      fw = "exito";

      OCAPConfigApp.logger.info(getClass().getName() + " aceptarGrado: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(fw);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward generarPDFCuestionario(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();

    OCAPUsuariosLN usuariosLN = null;
    OCAPUsuariosOT usuarioOT = null;
    OCAPCuestionariosLN cuestionariosLN = null;

    OCAPCuestionariosOT cuestionarioAux2OT = null;
    OCAPCuestionariosOT cuestionarioOT = null;
    OCAPCuestionariosOT cuestionarioEviOT = null;

    ArrayList listaPreguntas = null;
    ArrayList listaPreguntasEvi = null;

    OCAPSolicitudesOT solicitudesOT = null;
    OCAPSolicitudesLN solicitudesLN = null;
    int idRepeticion = 0; int numEvidencia = 0;
    long idCuestionario = 0L; long idPadreEvidencia = 0L;
    long idEvidencia = 0L;

    String redireccion = "";
    String nEvidencia = "";
    try
    {
      OCAPConfigApp.logger.info("Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      usuariosLN = new OCAPUsuariosLN(jcylUsuario);
      if (request.getParameter("expId") != null)
        session.setAttribute("expedienteId", request.getParameter("expId"));
      if (session.getAttribute("expedienteId") != null) {
        solicitudesOT = new OCAPSolicitudesOT();
        solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
        solicitudesOT.setCExp_id(Long.parseLong(session.getAttribute("expedienteId").toString()));
        solicitudesOT = solicitudesLN.datosSolicitud(solicitudesOT, jcylUsuario);
        usuarioOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(), solicitudesOT.getCExp_id(), jcylUsuario);
      } else {
        String usuId = jcylUsuario.getUser().getC_usr_id();
        usuarioOT = usuariosLN.datosPersonalesUsuario(usuId, 0L, jcylUsuario);
      }

      if (usuarioOT == null) {
        usuarioOT = (OCAPUsuariosOT)session.getAttribute("UsuarioPruebaItinerario");
        if (usuarioOT == null) {
          return mapping.findForward("irNoExisteSolicitud");
        }
      }
      if (request.getAttribute("idCuestionario") == null)
        idCuestionario = Long.parseLong(request.getParameter("idCuestionario") == null ? "0" : request.getParameter("idCuestionario"));
      else idCuestionario = Long.parseLong(request.getAttribute("idCuestionario").toString());
      if (request.getAttribute("idRepeticion") == null)
        idRepeticion = Integer.parseInt(request.getParameter("idRepeticion") == null ? "0" : request.getParameter("idRepeticion"));
      else idRepeticion = Integer.parseInt((request.getAttribute("idRepeticion") == null) || ("".equals(request.getAttribute("idRepeticion"))) ? "0" : request.getAttribute("idRepeticion").toString());
      if (request.getAttribute("idEvidencia") == null)
        idEvidencia = Long.parseLong(request.getParameter("idEvidencia") == null ? "0" : request.getParameter("idEvidencia"));
      else idEvidencia = Long.parseLong(request.getAttribute("idEvidencia").toString());
      if (request.getAttribute("idPadreEvidencia") == null)
        idPadreEvidencia = Long.parseLong(request.getParameter("idPadreEvidencia") == null ? "0" : request.getParameter("idPadreEvidencia"));
      else idPadreEvidencia = Long.parseLong(request.getAttribute("idPadreEvidencia").toString());

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      cuestionarioOT = new OCAPCuestionariosOT();
      cuestionarioOT.setCCuestionarioId(idCuestionario);
      if (idRepeticion == -1)
        idRepeticion = 0;
      cuestionarioOT = cuestionariosLN.detalleCuestionario(usuarioOT, cuestionarioOT, idRepeticion, jcylUsuario);

      nEvidencia = cuestionarioOT.getNEvidencia();
      if (nEvidencia != null) {
        numEvidencia = Integer.parseInt(nEvidencia);
      }

      long cCuestAnt = 0L;
      int nrepeAnt = 0;

      ArrayList listaAux = (ArrayList)session.getAttribute(Constantes.COMBO_CUESTIONARIOS);
      ArrayList listaAux2 = new ArrayList();
      if (listaAux != null) {
        for (int i = 0; i < listaAux.size(); i++) {
          cuestionarioAux2OT = (OCAPCuestionariosOT)listaAux.get(i);
          if (idCuestionario == cuestionarioAux2OT.getCCuestionarioId()) {
            listaAux2 = cuestionarioAux2OT.getListaRepeticiones();
            if (listaAux2 == null)
              listaAux2 = new ArrayList();
            break;
          }
        }
      }

      ArrayList listaAux3 = new ArrayList();
      for (int num = 0; num < listaAux2.size(); num++) {
        OCAPRepeticionesCuestionariosOT rcOT = (OCAPRepeticionesCuestionariosOT)listaAux2.get(num);
        if ((cCuestAnt != rcOT.getCCuestionarioId()) || (nrepeAnt != rcOT.getNRepeticion())) {
          listaAux3.add(rcOT);
          cCuestAnt = rcOT.getCCuestionarioId();
          nrepeAnt = rcOT.getNRepeticion();
        }
      }

      listaPreguntas = cuestionariosLN.buscarPreguntasCuestionarioPorUsuario(usuarioOT, idCuestionario, listaAux3, idRepeticion, false, false, 0, 0L, jcylUsuario);

      String rutaRaiz = this.servlet.getServletConfig().getServletContext().getRealPath("");

      if (idRepeticion != 0)
        cuestionarioOT.setNRepeticiones(new Integer(1));
      ArrayList reports = cuestionariosLN.crearReportCuestionarios(listaPreguntas, cuestionarioOT, usuarioOT, rutaRaiz);

      if (idEvidencia != 0L) {
        cuestionarioEviOT = new OCAPCuestionariosOT();
        cuestionarioEviOT.setCCuestionarioId(idEvidencia);
        cuestionarioEviOT = cuestionariosLN.detalleCuestionario(usuarioOT, cuestionarioEviOT, 0, jcylUsuario);
        cuestionarioEviOT.setNEvidencia(nEvidencia);
        if (idRepeticion == 0)
          cuestionarioEviOT.setNRepeticiones(cuestionarioOT.getNRepeticiones());
        listaPreguntasEvi = cuestionariosLN.buscarPreguntasCuestionarioPorUsuario(usuarioOT, idEvidencia, listaAux3, 0, false, false, numEvidencia, idPadreEvidencia, jcylUsuario);
        ArrayList reportsEvidencia = cuestionariosLN.crearReportCuestionarios(listaPreguntasEvi, cuestionarioEviOT, usuarioOT, rutaRaiz);

        reports.addAll(reportsEvidencia);
      }

      ServletOutputStream out = response.getOutputStream();
      response.setContentType("application/pdf");
      response.setHeader("Content-disposition", "attachment; filename=\"" + cuestionarioOT.getDArea() + cuestionarioOT.getDNombre() + ".pdf\"");
      Report.reportToPDFCat(out, reports);
      out.flush();
      out.close();
    }
    catch (Exception e)
    {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(redireccion);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward generarPDFEval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();

    OCAPCuestionariosLN cuestionariosLN = null;
    OCAPCuestionariosOT cuestionarioOT = null;
    ReportOT reportOT = null;
    try
    {
      OCAPConfigApp.logger.info("Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      cuestionarioOT = new OCAPCuestionariosOT();
      cuestionarioOT.setDNombreEvaluador(((OCAPCuestionariosForm)form).getDNombreEvaluador());
      cuestionarioOT.setDCodigoEvaluadorId(((OCAPCuestionariosForm)form).getCodigoId());
      cuestionarioOT.setNCreditos(Float.parseFloat(((OCAPCuestionariosForm)form).getNCreditosItinerario()));
      cuestionarioOT.setNCreditosEvaluados(Float.parseFloat(((OCAPCuestionariosForm)form).getNCreditosEvaluados()));
      cuestionarioOT.setAEspecificaciones(((OCAPCuestionariosForm)form).getAEspecificaciones());

      String nombreReportPadre = "informeEval";
      String rutaRaiz = this.servlet.getServletConfig().getServletContext().getRealPath("");

      reportOT = cuestionariosLN.crearPDF(cuestionarioOT, rutaRaiz, nombreReportPadre);

      ServletOutputStream out = response.getOutputStream();
      response.setContentType("application/pdf");
      response.setHeader("Content-disposition", "attachment; filename=\"informeEval.pdf\"");
      Report.reportToPDF(out, reportOT);
      out.flush();
      out.close();
    }
    catch (Exception e)
    {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward("irGenerarInforme");
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward generarPDFCte(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    HttpSession session = request.getSession();

    OCAPCuestionariosLN cuestionariosLN = null;
    OCAPCuestionariosOT cuestionarioOT = null;
    ReportOT reportOT = null;
    try
    {
      OCAPConfigApp.logger.info("Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      cuestionarioOT = new OCAPCuestionariosOT();
      cuestionarioOT.setDNombreCte(((OCAPCuestionariosForm)form).getDNombreCTE());

      cuestionarioOT.setDCodigoId(((OCAPCuestionariosForm)form).getCodigoId());
      cuestionarioOT.setDCategoriaEspecialidadEvaluado(((OCAPCuestionariosForm)form).getDCategoriaEspecialidadEvaluado());
      cuestionarioOT.setDNombreItinerario(((OCAPCuestionariosForm)form).getDNombreItinerario());
      cuestionarioOT.setDGrado_des(((OCAPCuestionariosForm)form).getDGrado_des());

      cuestionarioOT.setDCodigoEvaluadorId(((OCAPCuestionariosForm)form).getCodigoEvaluadorId());
      cuestionarioOT.setFInicioEvaluacion(((OCAPCuestionariosForm)form).getFInicioEvaluacion());
      cuestionarioOT.setFFinEvaluacion(((OCAPCuestionariosForm)form).getFFinEvaluacion());

      cuestionarioOT.setDNombrePresidente(((OCAPCuestionariosForm)form).getDNombrePresidente());
      cuestionarioOT.setFReunion(((OCAPCuestionariosForm)form).getFReunion());
      cuestionarioOT.setNCreditos(Float.parseFloat(((OCAPCuestionariosForm)form).getNCreditosNecesarios()));
      cuestionarioOT.setNCreditosEvaluados(Float.parseFloat(((OCAPCuestionariosForm)form).getNCreditosEvaluados()));
      cuestionarioOT.setBConformidadCte(((OCAPCuestionariosForm)form).getBConformidadCTE());
      cuestionarioOT.setBNuevaRevision(((OCAPCuestionariosForm)form).getBNuevaRevision());
      cuestionarioOT.setADiscrepancias(((OCAPCuestionariosForm)form).getADiscrepancias());
      cuestionarioOT.setAEspecificaciones(((OCAPCuestionariosForm)form).getAEspecificaciones());
      cuestionarioOT.setFEvaluacion(((OCAPCuestionariosForm)form).getFEvaluacion());

      String nombreReportPadre = "informeCte";
      String rutaRaiz = this.servlet.getServletConfig().getServletContext().getRealPath("");

      reportOT = cuestionariosLN.crearPDF(cuestionarioOT, rutaRaiz, nombreReportPadre);

      ServletOutputStream out = response.getOutputStream();
      response.setContentType("application/pdf");
      response.setHeader("Content-disposition", "attachment; filename=\"informeCte.pdf\"");
      Report.reportToPDF(out, reportOT);
      out.flush();
      out.close();
    }
    catch (Exception e)
    {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward("irGenerarInforme");
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward generarPDFCe(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();

    String mensajeError = "Se ha producido un error";
    HttpSession session = request.getSession();

    OCAPCuestionariosLN cuestionariosLN = null;
    OCAPCuestionariosOT cuestionarioOT = null;
    ReportOT reportOT = null;
    try
    {
      OCAPConfigApp.logger.info("Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

      cuestionariosLN = new OCAPCuestionariosLN(jcylUsuario);
      cuestionarioOT = new OCAPCuestionariosOT();
      cuestionarioOT.setFReunion(((OCAPCuestionariosForm)form).getFReunion());
      cuestionarioOT.setDCodigoId(((OCAPCuestionariosForm)form).getCodigoId());
      cuestionarioOT.setDCodigoEvaluadorId(((OCAPCuestionariosForm)form).getCodigoEvaluadorId());
      cuestionarioOT.setDNombreCte(((OCAPCuestionariosForm)form).getDNombreCTE());
      cuestionarioOT.setBConformidadCte(((OCAPCuestionariosForm)form).getBConformidadCTE());

      if (Constantes.SI.equals(cuestionarioOT.getBConformidadCte()))
        cuestionarioOT.setDConformidadCte("CONFORMIDAD");
      else {
        cuestionarioOT.setDConformidadCte("NO CONFORMIDAD");
      }
      cuestionarioOT.setBDecisionCe(((OCAPCuestionariosForm)form).getBDecisionCE());
      cuestionarioOT.setNCreditosConseguidos(Float.parseFloat(((OCAPCuestionariosForm)form).getNCreditosItinerario()));
      cuestionarioOT.setNCreditosEvaluados(Float.parseFloat(((OCAPCuestionariosForm)form).getNCreditosEvaluados()));
      cuestionarioOT.setNCreditos(Float.parseFloat(((OCAPCuestionariosForm)form).getNCreditosNecesarios()));
      cuestionarioOT.setDGrado_des(((OCAPCuestionariosForm)form).getDGrado_des());
      cuestionarioOT.setADiscrepancias(((OCAPCuestionariosForm)form).getADiscrepancias());
      cuestionarioOT.setAEspecificaciones(((OCAPCuestionariosForm)form).getAEspecificaciones());
      cuestionarioOT.setFEvaluacion(((OCAPCuestionariosForm)form).getFEvaluacion());

      String nombreReportPadre = "informeCe";
      String rutaRaiz = this.servlet.getServletConfig().getServletContext().getRealPath("");

      reportOT = cuestionariosLN.crearPDF(cuestionarioOT, rutaRaiz, nombreReportPadre);

      ServletOutputStream out = response.getOutputStream();
      response.setContentType("application/pdf");
      response.setHeader("Content-disposition", "attachment; filename=\"informeCe.pdf\"");
      Report.reportToPDF(out, reportOT);
      out.flush();
      out.close();
    }
    catch (Exception e)
    {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward("irGenerarInformeCE");
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }

  public ActionForward abrirDocumentoRespuesta(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    HttpSession session = request.getSession();
    ActionErrors errors = new ActionErrors();
    OCAPExpedientesCAsLN expCaLN = null;

    OutputStream documento = response.getOutputStream();
    try
    {
      OCAPConfigApp.logger.info("abrirDocumentoRespuesta: Inicio");
      JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
      expCaLN = new OCAPExpedientesCAsLN(jcylUsuario);
      long cExpCaId = Long.parseLong(request.getParameter("expCaId") == null ? "0" : request.getParameter("expCaId"));
      InputStream fichero = expCaLN.buscarExpedientesCA(cExpCaId);

      response.setContentType("application/pdf");
      response.setHeader("Content-Disposition", "attachment;filename=temp.pdf");
      int cont;
      while ((cont = fichero.read()) != -1) {
        documento.write(cont);
      }
      fichero.close();
      documento.flush();
      documento.close();

      OCAPConfigApp.logger.info("abrirDocumentoRespuesta: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
    }

    if (errors.size() != 0) {
      saveErrors(request, errors);

      return mapping.findForward("fallo");
    }

    return mapping.findForward("");
  }

  public ActionForward asignarMaxCreditos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    HttpSession session = request.getSession();
    ActionErrors errors = new ActionErrors();
    try
    {
      OCAPConfigApp.logger.debug(getClass().getName() + " asignarMaxCreditos: Inicio");

      if ((((OCAPCuestionariosForm)form).getCExpId() != null) && (((OCAPCuestionariosForm)form).getCExpId().longValue() != 0L) && (((OCAPCuestionariosForm)form).getCCuestionarioId() != 0L) && (((OCAPCuestionariosForm)form).getNCreditosEvaluados() != null))
      {
        JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");

        OCAPCreditosCuestionariosOT creditosCuestionarioOT = new OCAPCreditosCuestionariosOT();
        creditosCuestionarioOT.setCExpId(((OCAPCuestionariosForm)form).getCExpId().longValue());
        creditosCuestionarioOT.setCCuestionarioId(((OCAPCuestionariosForm)form).getCCuestionarioId());
        creditosCuestionarioOT.setBAcuerdo(Constantes.SI);
        creditosCuestionarioOT.setAComentarios("Asignación Automática de Créditos Máximos por Evaluador");
        creditosCuestionarioOT.setNCreditosEvaluador(Float.valueOf(((OCAPCuestionariosForm)form).getNCreditosEvaluados()).floatValue());
        creditosCuestionarioOT.setNCreditosFinales(Float.valueOf(((OCAPCuestionariosForm)form).getNCreditosEvaluados()).floatValue());
        creditosCuestionarioOT.setACreadoPor(jcylUsuario.getUser().getC_usr_id());
        creditosCuestionarioOT.setAModificadoPor(jcylUsuario.getUser().getC_usr_id());

        OCAPCreditosCuestionariosLN creditosCuestionariosLN = new OCAPCreditosCuestionariosLN(jcylUsuario);
        creditosCuestionariosLN.altaEvaluacion(creditosCuestionarioOT);
      }

      OCAPConfigApp.logger.debug(getClass().getName() + " asignarMaxCreditos: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
    }

    if (errors.size() != 0) {
      saveErrors(request, errors);

      return mapping.findForward("fallo");
    }

    return irListar(mapping, form, request, response);
  }

  public ActionForward abrirCuestionarios(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();
    try
    {
      OCAPConfigApp.logger.debug(getClass().getName() + " abrirCuestionarios: Inicio");

      if ((((OCAPCuestionariosForm)form).getCExpId() != null) && (((OCAPCuestionariosForm)form).getCExpId().longValue() != 0L) && (((OCAPCuestionariosForm)form).getCCuestionarioId() != 0L))
      {
        JCYLUsuario jcylUsuario = (JCYLUsuario)request.getSession().getAttribute("JCYLUsuario");
        OCAPConfigApp.logger.info(getClass().getName() + " Peticion de reabrir Cuestionarios de la Prueba: " + ((OCAPCuestionariosForm)form).getCCuestionarioId() + " para el Expediente: " + ((OCAPCuestionariosForm)form).getCExpId());

        OCAPEstadosCuestionarioLN estadosCuestionarioLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
        estadosCuestionarioLN.abrirCuestionariosExp(((OCAPCuestionariosForm)form).getCExpId().longValue(), ((OCAPCuestionariosForm)form).getCCuestionarioId(), jcylUsuario.getUser().getC_usr_id());
      }

      OCAPConfigApp.logger.debug(getClass().getName() + " abrirCuestionarios: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
    }

    if (errors.size() != 0) {
      saveErrors(request, errors);

      return mapping.findForward("fallo");
    }

    return irListar(mapping, form, request, response);
  }

  public ActionForward irAlertaProteccionDatos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ActionErrors errors = new ActionErrors();
    OCAPUsuariosOT usuariosOT = null;
    OCAPExpedientecarreraOT expedienteCarreraOT = null;
    OCAPConvocatoriasLN convocatoriaLN = null;
    OCAPConvocatoriasOT convoOT = null;
    String fw = null;
    try
    {
      OCAPConfigApp.logger.debug(getClass().getName() + " irAlertaProteccionDatos: Inicio");
      validarAccion(mapping, form, request, response);

      JCYLUsuario jcylUsuario = (JCYLUsuario)request.getSession().getAttribute("JCYLUsuario");

      OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);

      usuariosOT = usuariosLN.buscarOCAPUsuariosPorNIF(jcylUsuario.getUser().getC_usr_id());

      if ((usuariosOT == null) || (usuariosOT.getCUsrId() == null) || (usuariosOT.getCUsrId().longValue() == 0L)) {
        fw = "irNoExisteSolicitud";
      }
      else
      {
        String cConvocatoriaId = obtenerConvocatoria(request, ((OCAPCuestionariosForm)form).getCConvocatoriaId());

        OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
        expedienteCarreraOT = expedienteCarreraLN.buscarExpedienteCarreraPorUsuarioConvocatoria(usuariosOT.getCUsrId(), Long.valueOf(cConvocatoriaId));
        
        if ((expedienteCarreraOT == null) || (expedienteCarreraOT.getCExpId() == null) || (expedienteCarreraOT.getCExpId().longValue() == 0L)) {
          fw = "irNoExisteSolicitud";
        }
        else {
          convocatoriaLN = new OCAPConvocatoriasLN(jcylUsuario);
          convoOT = convocatoriaLN.buscarOCAPConvocatorias(new Long(cConvocatoriaId));
          request.setAttribute("anio",convoOT.getDAnioConvocatoria().trim());
          fw = "irAlertaProteccionDatos";
        }

      }

      OCAPConfigApp.logger.debug("irAlertaProteccionDatos: Fin");
    }
    catch (Exception e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
    }

    if ((errors == null) || (errors.isEmpty()))
    {
      return mapping.findForward(fw);
    }
    saveErrors(request, errors);

    return mapping.findForward("fallo");
  }
}
