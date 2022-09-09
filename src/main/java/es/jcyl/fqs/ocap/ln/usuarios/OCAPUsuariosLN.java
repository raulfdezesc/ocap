 package es.jcyl.fqs.ocap.ln.usuarios;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
 import es.jcyl.fqs.ocap.ln.centroTrabajo.OCAPCentroTrabajoLN;
 import es.jcyl.fqs.ocap.ln.creditos.OCAPCreditosLN;
 import es.jcyl.fqs.ocap.ln.defMeritosCurriculares.OCAPDefMeritoscurricularesLN;
 import es.jcyl.fqs.ocap.ln.especialidades.OCAPEspecialidadesLN;
 import es.jcyl.fqs.ocap.ln.expedienteCarrera.OCAPExpedienteCarreraLN;
 import es.jcyl.fqs.ocap.ln.expedienteMC.OCAPExpedientemcLN;
 import es.jcyl.fqs.ocap.ln.gerencias.OCAPGerenciasLN;
 import es.jcyl.fqs.ocap.ln.grado.OCAPGradoLN;
 import es.jcyl.fqs.ocap.ln.mensajes.OCAPMensajesLN;
 import es.jcyl.fqs.ocap.ln.mensajesMc.OCAPMensajesMcLN;
 import es.jcyl.fqs.ocap.ln.meritosCurriculares.OCAPMeritoscurricularesLN;
 import es.jcyl.fqs.ocap.ln.personalEstatutario.OCAPPersEstatutarioLN;
 import es.jcyl.fqs.ocap.oad.provincias.OCAPProvinciasOAD;
 import es.jcyl.fqs.ocap.oad.usuarios.OCAPUsuariosOAD;
 import es.jcyl.fqs.ocap.ot.categProfesionales.OCAPCategProfesionalesOT;
 import es.jcyl.fqs.ocap.ot.centroTrabajo.OCAPCentroTrabajoOT;
 import es.jcyl.fqs.ocap.ot.creditos.OCAPCreditosOT;
 import es.jcyl.fqs.ocap.ot.defMeritosCurriculares.OCAPDefMeritoscurricularesOT;
 import es.jcyl.fqs.ocap.ot.especialidades.OCAPEspecialidadesOT;
 import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;
 import es.jcyl.fqs.ocap.ot.expedienteMC.OCAPExpedientemcOT;
 import es.jcyl.fqs.ocap.ot.gerencias.OCAPGerenciasOT;
 import es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT;
 import es.jcyl.fqs.ocap.ot.mensajes.OCAPMensajesOT;
 import es.jcyl.fqs.ocap.ot.meritosCurriculares.OCAPMeritoscurricularesOT;
 import es.jcyl.fqs.ocap.ot.personalEstatutario.OCAPPersEstatutarioOT;
 import es.jcyl.fqs.ocap.ot.provincias.OCAPProvinciasOT;
 import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.fqs.ocap.util.reports.conceptos.ConceptoReport;
 import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.io.File;
 import java.io.OutputStream;
 import java.sql.Connection;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.Hashtable;
 import javax.servlet.http.HttpServletResponse;
 import net.sf.jasperreports.engine.JREmptyDataSource;
 import net.sf.jasperreports.engine.JasperExportManager;
 import net.sf.jasperreports.engine.JasperFillManager;
 import net.sf.jasperreports.engine.JasperPrint;
 import net.sf.jasperreports.engine.JasperReport;
 import net.sf.jasperreports.engine.util.JRLoader;
 import org.apache.log4j.Logger;
 
 public class OCAPUsuariosLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPUsuariosLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public OCAPUsuariosOT buscarOCAPUsuariosPorNIF(String usuarioNif)
     throws SQLException, Exception
   {
     OCAPUsuariosOT usuOT = null;
     try
     {
       OCAPUsuariosOAD usuarioOAD = OCAPUsuariosOAD.getInstance();
       usuOT = usuarioOAD.buscarOCAPUsuariosPorNIF(usuarioNif);
 
       if ((usuOT != null) && (usuOT.getCUsrId() != null) && (usuOT.getCUsrId().longValue() != 0L)) {
         OCAPProvinciasOAD provinciasOAD = OCAPProvinciasOAD.getInstance();
         OCAPProvinciasOT provinciasOT = provinciasOAD.buscarProvincia(usuOT.getCProvinciaUsu_id());
         usuOT.setDProvinciaUsu(provinciasOT.getDProvincia());
       }
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return usuOT;
   }
 
   public void modificacionOCAPUsuarios(OCAPUsuariosOT datos)
     throws Exception
   {
     Connection con = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
     finally
     {
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
   }
 
   public OCAPUsuariosOT datosPersonalesUsuario(String usuarioNif, long cExpId, JCYLUsuario jcylUsuario)
     throws SQLException, Exception
   {
     OCAPUsuariosOT usuariosOT = null;
     try
     {
       OCAPConfigApp.logger.debug("datosPersonalesUsuario: Inicio");
 
       usuariosOT = buscarOCAPUsuariosPorNIF(usuarioNif);
 
       if ((usuariosOT == null) || (usuariosOT.getCUsrId() == null)) {
         return null;
       }
 
       OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
       OCAPExpedientecarreraOT expedienteCarreraOT = null;
       if (cExpId != 0L) {
         expedienteCarreraOT = expedienteCarreraLN.buscarOCAPExpedienteCarrera(Long.valueOf(cExpId));
       } else {
         Long cConvocatoriaId = Long.valueOf(jcylUsuario.getParametrosUsuario().get("PARAM_CONVOCATORIA_USUARIO") != null ? Long.valueOf((String)jcylUsuario.getParametrosUsuario().get("PARAM_CONVOCATORIA_USUARIO")).longValue() : 0L);
         expedienteCarreraOT = expedienteCarreraLN.buscarExpedienteCarreraPorUsuarioConvocatoria(usuariosOT.getCUsrId(), cConvocatoriaId);
       }
 
       if ((expedienteCarreraOT == null) || (expedienteCarreraOT.getCExpId() == null) || (expedienteCarreraOT.getCExpId().longValue() == 0L)) {
         return null;
       }
       usuariosOT.setCExpId(expedienteCarreraOT.getCExpId());
       usuariosOT.setCEstadoExpId(expedienteCarreraOT.getCEstadoId());
       usuariosOT.setFAceptacSolic(expedienteCarreraOT.getFAceptacSolic());
       usuariosOT.setFIncioMC(expedienteCarreraOT.getFInicioMc());
       usuariosOT.setFFinMC(expedienteCarreraOT.getFFinMc());
       usuariosOT.setFAceptacionCEIS(expedienteCarreraOT.getFAceptacionceis());
       usuariosOT.setFAceptacionCC(expedienteCarreraOT.getFAceptacionCC());
       usuariosOT.setFNegacionMC(expedienteCarreraOT.getFNegacionMC());
       usuariosOT.setFRespuestainconfMC(expedienteCarreraOT.getFRespuestaInconf_MC());
       usuariosOT.setFInicioEvaluacion(expedienteCarreraOT.getFInicioEvaluacion());
       usuariosOT.setFInicioEvalMC(expedienteCarreraOT.getFInicioEvalMc());
       usuariosOT.setFFinEvalMC(expedienteCarreraOT.getFFinEvalMc());
       usuariosOT.setFIncioCA(expedienteCarreraOT.getFInicioCa());
       usuariosOT.setFFinCA(expedienteCarreraOT.getFFinCa());
       usuariosOT.setFRegDocEscaneados(expedienteCarreraOT.getFRegDocEscaneados());
       usuariosOT.setFRegEvidenciasConf(expedienteCarreraOT.getFRegEvidenciasConf());
 
       usuariosOT.setCItinerarioId(expedienteCarreraOT.getCItinerarioId());
       usuariosOT.setCProcedimientoId(expedienteCarreraOT.getCProcedimientoId());
       usuariosOT.setBValidacionCC(expedienteCarreraOT.getBValidacioncc());
       usuariosOT.setFInformeCC(expedienteCarreraOT.getFInformeCC());
       usuariosOT.setFInformeCe(expedienteCarreraOT.getFInformeCE());
       usuariosOT.setFInformeCte(expedienteCarreraOT.getFInformeCTE());
       usuariosOT.setFInformeEval(expedienteCarreraOT.getFInformeEval());
       usuariosOT.setAEspecificacionesCC(expedienteCarreraOT.getAEspecificacionesCC());
       usuariosOT.setAEspecificacionesCE(expedienteCarreraOT.getAEspecificacionesCE());
       usuariosOT.setAEspecificacionesCTE(expedienteCarreraOT.getAEspecificacionesCTE());
       usuariosOT.setAEspecificacionesEval(expedienteCarreraOT.getAEspecificacionesEval());
       usuariosOT.setFReunionCE(expedienteCarreraOT.getFReunionCE());
       usuariosOT.setFReunionCTE(expedienteCarreraOT.getFReunionCTE());
       usuariosOT.setADiscrepanciasCE(expedienteCarreraOT.getADiscrepanciasCE());
       usuariosOT.setADiscrepanciasCTE(expedienteCarreraOT.getADiscrepanciasCTE());
       usuariosOT.setBNuevaRevision(expedienteCarreraOT.getBNuevaRevision());
       usuariosOT.setBConformidadCTE(expedienteCarreraOT.getBConformidadCTE());
       usuariosOT.setBDecisionCEStr(expedienteCarreraOT.getBDecisionCE());
       usuariosOT.setNCreditosEvaluadosCTE(expedienteCarreraOT.getNCreditosEvaluadosCTE());
 
       OCAPPersEstatutarioLN personalLN = new OCAPPersEstatutarioLN(jcylUsuario);
       OCAPPersEstatutarioOT personalOT = personalLN.buscarOCAPPersEstatutario(expedienteCarreraOT.getCEstatutId());
       if (personalOT != null)
         usuariosOT.setDEstatut_nombre(personalOT.getDNombre());
       personalLN = null;
       personalOT = null;
 
       OCAPCategProfesionalesLN categLN = new OCAPCategProfesionalesLN(jcylUsuario);
       OCAPCategProfesionalesOT categOT = categLN.buscarOCAPCategProfesionales(expedienteCarreraOT.getCProfesionalId());
       usuariosOT.setCEstatutId(Integer.valueOf((int)categOT.getCEstatutId()));
       categLN = null;
 
       OCAPEspecialidadesLN especialidadesLN = new OCAPEspecialidadesLN(jcylUsuario);
       OCAPEspecialidadesOT especialidadesOT = especialidadesLN.buscarOCAPEspecialidades(expedienteCarreraOT.getCEspecId());
       especialidadesLN = null;
 
       OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
       OCAPGerenciasOT gerenciasOT = gerenciasLN.buscarOCAPGerencias(Long.parseLong(String.valueOf(usuariosOT.getCGerenciaId())));
       gerenciasLN = null;
 
       OCAPProvinciasOAD provinciasOAD = OCAPProvinciasOAD.getInstance();
       OCAPProvinciasOT provinciasOT = provinciasOAD.buscarProvincia(gerenciasOT.getCProvinciaId());
 
       OCAPCentroTrabajoLN centroTrabajoLN = new OCAPCentroTrabajoLN(jcylUsuario);
       OCAPCentroTrabajoOT centroTrabajoOT = centroTrabajoLN.buscarOCAPCentroTrabajo(usuariosOT.getCCentrotrabajoId().longValue());
       centroTrabajoLN = null;
 
       OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
       OCAPGradoOT gradoOT = gradoLN.buscarOCAPGrado(expedienteCarreraOT.getCGradoId().longValue());
       usuariosOT.setCGrado_id(expedienteCarreraOT.getCGradoId().longValue());
       usuariosOT.setDGrado_des(gradoOT.getDDescripcion());
       gradoLN = null;
       gradoOT = null;
 
       usuariosOT.setCConvocatoriaId(expedienteCarreraOT.getCConvocatoriaId());
       usuariosOT.setCEspecId(Integer.valueOf(String.valueOf(expedienteCarreraOT.getCEspecId())));
 
       usuariosOT.setDProfesional_nombre(categOT.getDNombre() == null ? "-" : categOT.getDNombre());
       usuariosOT.setDEspec_nombre(especialidadesOT.getDNombre());
       usuariosOT.setDEspec_nombre(especialidadesOT.getDNombre() == null ? "-" : especialidadesOT.getDNombre());
       usuariosOT.setDProvincia(provinciasOT.getDProvincia() == null ? "-" : provinciasOT.getDProvincia());
 
       usuariosOT.setDGerencia_nombre(gerenciasOT.getDNombre() == null ? "-" : gerenciasOT.getDNombre());
       usuariosOT.setDCentrotrabajo_nombre(centroTrabajoOT.getDNombre() == null ? "-" : centroTrabajoOT.getDNombre());
       usuariosOT.setCProfesionalId(Integer.valueOf((int)expedienteCarreraOT.getCProfesionalId()));
 
       categOT = null;
       gerenciasOT = null;
       provinciasOT = null;
       centroTrabajoOT = null;
       especialidadesOT = null;
       expedienteCarreraOT = null;
 
       OCAPConfigApp.logger.info(" datosPersonalesUsuario: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return usuariosOT;
   }
 
   public OCAPUsuariosOT datosMCUsuario(OCAPUsuariosOT usuariosOT, String meritosSeleccionados)
     throws Exception
   {
     OCAPExpedientemcLN expmcLN = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " datosMCUsuario: Inicio");
 
       OCAPMensajesLN mensajeLN = new OCAPMensajesLN(this.jcylUsuario);
       OCAPMensajesOT mensajeOT = mensajeLN.buscarOCAPMensajesPorGradoIdYEstatutId(Integer.valueOf((int)usuariosOT.getCGrado_id()), usuariosOT.getCEstatutId());
       if ((mensajeOT != null) && (mensajeOT.getDDescripcion() != null))
         usuariosOT.setDMensaje(mensajeOT.getDDescripcion());
       else usuariosOT.setDMensaje("");
 
       expmcLN = new OCAPExpedientemcLN(this.jcylUsuario);
       if (expmcLN.buscarPdtesAclararPorExpId(usuariosOT.getCExpId().longValue(), this.jcylUsuario, null) > 0L)
         usuariosOT.setBPdtesAclarar(Constantes.SI);
       else usuariosOT.setBPdtesAclarar(Constantes.NO);
 
       OCAPCreditosLN creditosLN = new OCAPCreditosLN(this.jcylUsuario);
       ArrayList listaCreditos = creditosLN.buscarOCAPCreditosPorCategProfesional(usuariosOT, Integer.valueOf((int)usuariosOT.getCGrado_id()), meritosSeleccionados, this.jcylUsuario);
       meritosSeleccionados = (String)listaCreditos.get(0);
 
       listaCreditos.remove(0);
 
       if (listaCreditos.size() == 0) { OCAPUsuariosOT localOCAPUsuariosOT = null;
 
         return localOCAPUsuariosOT;
       }
       usuariosOT.setListaCreditos(listaCreditos);
 
       OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(this.jcylUsuario);
       OCAPMeritoscurricularesOT mcOTaux = null;
 
       if ((!Constantes.DEF_MERITO_OPCIONALES.equals(meritosSeleccionados)) && (!Constantes.SIN_DEF_MERITO.equals(meritosSeleccionados)))
       {
         mcOTaux = mcLN.buscarOCAPMeritoscurricularesPorUsuarioOT(usuariosOT, Long.valueOf(meritosSeleccionados), Constantes.NO, this.jcylUsuario, false);
         ArrayList listaMeritos = mcOTaux.getListaMeritosUsuario();
         usuariosOT.setListaMeritos(listaMeritos);
         usuariosOT.setListaMeritosOpcionales(new ArrayList());
       }
       else if (Constantes.DEF_MERITO_OPCIONALES.equals(meritosSeleccionados))
       {
         ArrayList listaMeritosOpcionales = new ArrayList();
         OCAPDefMeritoscurricularesOT defMCOT = null;
         OCAPDefMeritoscurricularesLN defMCLN = new OCAPDefMeritoscurricularesLN(this.jcylUsuario);
         ArrayList listaDefMeritos = defMCLN.listadoOCAPDefMeritoscurriculares();
 
         for (int j = 0; j < listaDefMeritos.size(); j++) {
           defMCOT = (OCAPDefMeritoscurricularesOT)listaDefMeritos.get(j);
 
           if ((!Constantes. DEF_MERITO_FORMACION.equals(String.valueOf(defMCOT.getCDefmeritoId()))) && (!Constantes.DEF_MERITO_OPCIONALES.equals(String.valueOf(defMCOT.getCDefmeritoId()))))
           {
             mcOTaux = mcLN.buscarOCAPMeritoscurricularesPorUsuarioOT(usuariosOT, Long.valueOf(defMCOT.getCDefmeritoId()), Constantes.SI, this.jcylUsuario, false);
             ArrayList listaMeritos = mcOTaux.getListaMeritosUsuario();
             OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
             mcOT.setDNombre(defMCOT.getDNombre().toUpperCase());
 
             OCAPMensajesMcLN mensajesMcLN = new OCAPMensajesMcLN(this.jcylUsuario);
             mcOT.setDDescripcion(mensajesMcLN.buscarOCAPMensajesPorEstatutIdDefMeritoId(usuariosOT.getCEstatutId().longValue(), defMCOT.getCDefmeritoId()).getDDescripcion());
             mcOT.setCDefmeritoId(defMCOT.getCDefmeritoId());
             mcOT.setListaMeritosUsuario(listaMeritos);
             listaMeritosOpcionales.add(mcOT);
           }
         }
 
         usuariosOT.setListaMeritosOpcionales(listaMeritosOpcionales);
         usuariosOT.setListaMeritos(new ArrayList());
       }
       else
       {
         ArrayList listaMeritos = new ArrayList();
         ArrayList listaMeritosOpcionales = new ArrayList();
         OCAPDefMeritoscurricularesOT defMCOT = null;
         OCAPDefMeritoscurricularesLN defMCLN = new OCAPDefMeritoscurricularesLN(this.jcylUsuario);
         ArrayList listaDefMeritos = defMCLN.listadoOCAPDefMeritoscurriculares();
 
         for (int j = 0; j < listaDefMeritos.size(); j++) {
           defMCOT = (OCAPDefMeritoscurricularesOT)listaDefMeritos.get(j);
           if (!Constantes.DEF_MERITO_OPCIONALES.equals(String.valueOf(defMCOT.getCDefmeritoId()))) {
             mcOTaux = mcLN.buscarOCAPMeritoscurricularesPorUsuarioOT(usuariosOT, Long.valueOf(defMCOT.getCDefmeritoId()), Constantes.NO, this.jcylUsuario, true);
             if ((mcOTaux.getListaMeritosUsuario() != null) && (mcOTaux.getListaMeritosUsuario().size() != 0)) {
               mcOTaux.setDNombre(defMCOT.getDNombre().toUpperCase());
               mcOTaux.setCDefmeritoId(defMCOT.getCDefmeritoId());
               listaMeritos.add(mcOTaux);
             }
           } else {
             OCAPMeritoscurricularesOT mcOTaux2 = null;
             for (int k = 0; k < listaDefMeritos.size(); k++) {
               OCAPDefMeritoscurricularesOT defMCOTaux = (OCAPDefMeritoscurricularesOT)listaDefMeritos.get(k);
               if ((!Constantes. DEF_MERITO_FORMACION.equals(String.valueOf(defMCOTaux.getCDefmeritoId()))) && (!Constantes.DEF_MERITO_OPCIONALES.equals(String.valueOf(defMCOTaux.getCDefmeritoId()))))
               {
                 mcOTaux2 = mcLN.buscarOCAPMeritoscurricularesPorUsuarioOT(usuariosOT, Long.valueOf(defMCOTaux.getCDefmeritoId()), Constantes.SI, this.jcylUsuario, true);
                 if ((mcOTaux2.getListaMeritosUsuario() != null) && (mcOTaux2.getListaMeritosUsuario().size() != 0)) {
                   mcOTaux2.setDNombre(defMCOTaux.getDNombre().toUpperCase());
                   mcOTaux2.setCDefmeritoId(defMCOTaux.getCDefmeritoId());
                   listaMeritosOpcionales.add(mcOTaux2);
                 }
               }
             }
           }
         }
 
         usuariosOT.setListaMeritosOpcionales(listaMeritosOpcionales);
         usuariosOT.setListaMeritos(listaMeritos);
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " datosMCUsuario: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
     finally
     {
     }
 
     return usuariosOT;
   }
 
   public void generarCVenPDF(HttpServletResponse response, OCAPUsuariosOT usuarioOT, String pathBase, String marcaAgua)
     throws Exception
   {
     String nombreReportPadre = null;
     String nombreSubreport = null;
     Hashtable parametros = null;
     JasperReport jasperReport = null;
     JasperReport jasperSubReport = null;
     JasperPrint jasperPrint = null;
     ConceptoReport matrizDatosReport = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " generarCVenPDF: Inicio");
 
       nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator + "informeCV" + ".jasper";
       if ((marcaAgua != null) && (marcaAgua.equals(Constantes.SI)))
         nombreSubreport = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator + "subInformeCVprovisional" + ".jasper";
       else if ((marcaAgua != null) && (marcaAgua.equals(Constantes.NO))) {
         nombreSubreport = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator + "subInformeCV" + ".jasper";
       }
 
       File fichReportPadre = new File(nombreReportPadre);
       jasperReport = (JasperReport)JRLoader.loadObject(fichReportPadre);
 
       File fichSubreport = new File(nombreSubreport);
       jasperSubReport = (JasperReport)JRLoader.loadObject(fichSubreport);
 
       parametros = new Hashtable();
       parametros.put("ruta", pathBase);
       parametros.put("datosDocu", usuarioOT);
       parametros.put("SUB_REPORT", jasperSubReport);
       parametros.put("fechaHoy", DateUtil.convertDateToStringLarga(Constantes.FECHA_LETRA, new Date()));
 
       matrizDatosReport = new ConceptoReport();
       matrizDatosReport = generarTablaCV(usuarioOT);
 
       if (matrizDatosReport == null) {
         parametros.put("reportVacio", "NO HAY DATOS COINCIDENTES");
       }
       else {
         parametros.put("datosReport", matrizDatosReport);
       }
 
       jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
 
       byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
 
       OutputStream out = response.getOutputStream();
       response.setHeader("Content-disposition", "attachment; filename=\"CV.pdf\"");
       response.setContentType("application/pdf");
       response.setContentLength(bytes.length);
       out.write(bytes);
       out.flush();
       out.close();
 
       OCAPConfigApp.logger.info(getClass().getName() + " generarCVenPDF: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw new Exception("Informe NO generado");
     }
   }
 
   public ConceptoReport generarTablaCV(OCAPUsuariosOT usuOT)
     throws Exception
   {
     ConceptoReport report = null;
 
     OCAPCreditosOT creditoOT = null;
     OCAPMeritoscurricularesOT mcOT = null;
     OCAPMeritoscurricularesOT mcOT2 = null;
     OCAPExpedientemcOT expmcOT = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " generarTablaCV: Inicio");
 
       if (usuOT.getListaCreditos().size() != 0) report = new ConceptoReport();
 
       ArrayList listaDefMeritos = usuOT.getListaMeritos();
       ArrayList listaCreditos = usuOT.getListaCreditos();
       ArrayList listaDefMeritosOpcionales = usuOT.getListaMeritosOpcionales();
 
       int tamanoListaCreditos = listaCreditos != null ? listaCreditos.size() : 0;
 
       for (int i = 0; i < tamanoListaCreditos; i++) {
         creditoOT = (OCAPCreditosOT)listaCreditos.get(i);
         if (!Constantes. DEF_MERITO_OPCIONALES.equals(String.valueOf(creditoOT.getCDefmeritoId()))) {
           int tamanoListaDefMeritos = listaDefMeritos != null ? listaDefMeritos.size() : 0;
           for (int j = 0; j < tamanoListaDefMeritos; j++) {
             mcOT = (OCAPMeritoscurricularesOT)listaDefMeritos.get(j);
             if (mcOT.getCDefmeritoId() == creditoOT.getCDefmeritoId()) {
               report.addRow();
               report.putElement("dMerito", mcOT.getDNombre());
               report.putElement("cDefMeritoId", String.valueOf(creditoOT.getCDefmeritoId()));
               ArrayList listaTipoMeritos = mcOT.getListaMeritosUsuario();
               int tamanoListaTipoMeritos = listaTipoMeritos != null ? listaTipoMeritos.size() : 0;
               for (int l = 0; l < tamanoListaTipoMeritos; l++) {
                 mcOT2 = (OCAPMeritoscurricularesOT)listaTipoMeritos.get(l);
                 report.addRow();
                 report.putElement("cDefMeritoId", String.valueOf(creditoOT.getCDefmeritoId()));
                 report.putElement("guionDMeritoNombre", "· ");
                 report.putElement("dMeritoNombre", mcOT2.getDNombre());
 
                 int tamanoListaMeritos = mcOT2.getListaExpmc() != null ? mcOT2.getListaExpmc().size() : 0;
                 for (int k = 0; k < tamanoListaMeritos; k++) {
                   expmcOT = (OCAPExpedientemcOT)mcOT2.getListaExpmc().get(k);
                   report.addRow();
                   report.putElement("cDefMeritoId", String.valueOf(creditoOT.getCDefmeritoId()));
                   report.putElement("dTitulo", expmcOT.getDTitulo());
                   if ((expmcOT.getFExpedicion() != null) && (!"".equals(expmcOT.getFExpedicion())))
                     report.putElement("fRealizacion", expmcOT.getFExpedicion());
                   else if ((expmcOT.getFAnnio() != null) && (expmcOT.getFAnnio().intValue() != 0))
                     report.putElement("fRealizacion", String.valueOf(expmcOT.getFAnnio()));
                   else if ((expmcOT.getFInicio() != null) && (!"".equals(expmcOT.getFInicio())))
                     report.putElement("fRealizacion", expmcOT.getFInicio() + "-" + (expmcOT.getFFinalizacion() == null ? "" : expmcOT.getFFinalizacion()));
                   else report.putElement("fRealizacion", "");
                   report.putElement("dLugarExpedicion", expmcOT.getALugarExpedicion() == null ? "" : expmcOT.getALugarExpedicion());
                   report.putElement("nCreditos", expmcOT.getNCreditos() + " créditos");
                 }
 
                 int tamanoListaMeritosCTSP = mcOT2.getListaExpmcCTSP() != null ? mcOT2.getListaExpmcCTSP().size() : 0;
                 for (int k = 0; k < tamanoListaMeritosCTSP; k++) {
                   expmcOT = (OCAPExpedientemcOT)mcOT2.getListaExpmcCTSP().get(k);
                   report.addRow();
                   report.putElement("cDefMeritoId", String.valueOf(creditoOT.getCDefmeritoId()));
                   report.putElement("dTitulo", expmcOT.getDTitulo());
                   if ((expmcOT.getFExpedicion() != null) && (!"".equals(expmcOT.getFExpedicion())))
                     report.putElement("fRealizacion", expmcOT.getFExpedicion());
                   else if ((expmcOT.getFAnnio() != null) && (expmcOT.getFAnnio().intValue() != 0))
                     report.putElement("fRealizacion", String.valueOf(expmcOT.getFAnnio()));
                   else if ((expmcOT.getFInicio() != null) && (!"".equals(expmcOT.getFInicio())))
                     report.putElement("fRealizacion", expmcOT.getFInicio() + "-" + (expmcOT.getFFinalizacion() == null ? "" : expmcOT.getFFinalizacion()));
                   else report.putElement("fRealizacion", "");
                   report.putElement("dLugarExpedicion", expmcOT.getALugarExpedicion() == null ? "" : expmcOT.getALugarExpedicion());
                   report.putElement("nCreditos", expmcOT.getNCreditos() + " créditos");
                 }
                 int tamanoListaMeritosCTSNP = mcOT2.getListaExpmcCTSNP() != null ? mcOT2.getListaExpmcCTSNP().size() : 0;
                 for (int k = 0; k < tamanoListaMeritosCTSNP; k++) {
                   expmcOT = (OCAPExpedientemcOT)mcOT2.getListaExpmcCTSNP().get(k);
                   report.addRow();
                   report.putElement("cDefMeritoId", String.valueOf(creditoOT.getCDefmeritoId()));
                   report.putElement("dTitulo", expmcOT.getDTitulo());
                   if ((expmcOT.getFExpedicion() != null) && (!"".equals(expmcOT.getFExpedicion())))
                     report.putElement("fRealizacion", expmcOT.getFExpedicion());
                   else if ((expmcOT.getFAnnio() != null) && (expmcOT.getFAnnio().intValue() != 0))
                     report.putElement("fRealizacion", String.valueOf(expmcOT.getFAnnio()));
                   else if ((expmcOT.getFInicio() != null) && (!"".equals(expmcOT.getFInicio())))
                     report.putElement("fRealizacion", expmcOT.getFInicio() + "-" + (expmcOT.getFFinalizacion() == null ? "" : expmcOT.getFFinalizacion()));
                   else report.putElement("fRealizacion", "");
                   report.putElement("dLugarExpedicion", expmcOT.getALugarExpedicion() == null ? "" : expmcOT.getALugarExpedicion());
                   report.putElement("nCreditos", expmcOT.getNCreditos() + " créditos");
                 }
                 int tamanoListaMeritosCTSM = mcOT2.getListaExpmcCTSM() != null ? mcOT2.getListaExpmcCTSM().size() : 0;
                 for (int k = 0; k < tamanoListaMeritosCTSM; k++) {
                   expmcOT = (OCAPExpedientemcOT)mcOT2.getListaExpmcCTSM().get(k);
                   report.addRow();
                   report.putElement("cDefMeritoId", String.valueOf(creditoOT.getCDefmeritoId()));
                   report.putElement("dTitulo", expmcOT.getDTitulo());
                   if ((expmcOT.getFExpedicion() != null) && (!"".equals(expmcOT.getFExpedicion())))
                     report.putElement("fRealizacion", expmcOT.getFExpedicion());
                   else if ((expmcOT.getFAnnio() != null) && (expmcOT.getFAnnio().intValue() != 0))
                     report.putElement("fRealizacion", String.valueOf(expmcOT.getFAnnio()));
                   else if ((expmcOT.getFInicio() != null) && (!"".equals(expmcOT.getFInicio())))
                     report.putElement("fRealizacion", expmcOT.getFInicio() + "-" + (expmcOT.getFFinalizacion() == null ? "" : expmcOT.getFFinalizacion()));
                   else report.putElement("fRealizacion", "");
                   report.putElement("dLugarExpedicion", expmcOT.getALugarExpedicion() == null ? "" : expmcOT.getALugarExpedicion());
                   report.putElement("nCreditos", expmcOT.getNCreditos() + " créditos");
                 }
               }
             }
           }
 
           if (report.size() == 0)
             report.addRow();
           report.putElement("dMerito", creditoOT.getDMerito().toUpperCase());
           report.putElement("nCreditoCreditos", String.valueOf(creditoOT.getNCreditos()));
           report.putElement("nCreditosConseguidos", String.valueOf(creditoOT.getNCreditosConseguidos()));
         }
         else
         {
           report.addRow();
           report.putElement("dMerito", creditoOT.getDMerito().toUpperCase());
           report.putElement("cDefMeritoId", String.valueOf(creditoOT.getCDefmeritoId()));
           int tamanoListaDefMeritosOpcionales = listaDefMeritosOpcionales != null ? listaDefMeritosOpcionales.size() : 0;
           for (int j = 0; j < tamanoListaDefMeritosOpcionales; j++) {
             mcOT = (OCAPMeritoscurricularesOT)listaDefMeritosOpcionales.get(j);
             report.addRow();
             report.putElement("dMeritoOpc", mcOT.getDNombre());
             report.putElement("cDefMeritoId", String.valueOf(creditoOT.getCDefmeritoId()));
             ArrayList listaTipoMeritos = mcOT.getListaMeritosUsuario();
             int tamanoListaTipoMeritos = listaTipoMeritos != null ? listaTipoMeritos.size() : 0;
             for (int l = 0; l < tamanoListaTipoMeritos; l++) {
               mcOT2 = (OCAPMeritoscurricularesOT)listaTipoMeritos.get(l);
               report.addRow();
               report.putElement("cDefMeritoId", String.valueOf(creditoOT.getCDefmeritoId()));
               report.putElement("guionDMeritoNombre", "· ");
               report.putElement("dMeritoNombre", mcOT2.getDNombre());
               ArrayList listaMeritos = mcOT2.getListaExpmc();
               int tamanoListaMeritos = listaMeritos != null ? listaMeritos.size() : 0;
               for (int k = 0; k < tamanoListaMeritos; k++) {
                 expmcOT = (OCAPExpedientemcOT)listaMeritos.get(k);
                 report.addRow();
                 report.putElement("cDefMeritoId", String.valueOf(creditoOT.getCDefmeritoId()));
                 report.putElement("dTitulo", expmcOT.getDTitulo());
                 if ((expmcOT.getFExpedicion() != null) && (!"".equals(expmcOT.getFExpedicion())))
                   report.putElement("fRealizacion", expmcOT.getFExpedicion());
                 else if ((expmcOT.getFAnnio() != null) && (expmcOT.getFAnnio().intValue() != 0))
                   report.putElement("fRealizacion", String.valueOf(expmcOT.getFAnnio()));
                 else if ((expmcOT.getFInicio() != null) && (!"".equals(expmcOT.getFInicio())))
                   report.putElement("fRealizacion", expmcOT.getFInicio() + " - " + (expmcOT.getFFinalizacion() == null ? "" : expmcOT.getFFinalizacion()));
                 else report.putElement("fRealizacion", "");
                 report.putElement("dLugarExpedicion", expmcOT.getALugarExpedicion() == null ? "" : expmcOT.getALugarExpedicion());
                 report.putElement("nCreditos", expmcOT.getNCreditos() + " créditos");
               }
             }
           }
           report.putElement("dMerito", creditoOT.getDMerito().toUpperCase());
           report.putElement("nCreditoCreditos", creditoOT.getNCreditos() + "");
           report.putElement("nCreditosConseguidos", creditoOT.getNCreditosConseguidos() + "");
         }
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " generarTablaCV: Fin");
       return report;
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
   }
 
   public ArrayList listarEvaluados(OCAPUsuariosOT datos, JCYLUsuario jcylUsuario, int primerRegistro, int registrosPorPagina) throws Exception {
     ArrayList array = null;
     try {
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
       array = usuariosOAD.listarEvaluados(datos, jcylUsuario, primerRegistro, registrosPorPagina, 0);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public ArrayList buscarOCAPUsuariosPorNIF_a(String usuarioNifNie)
     throws SQLException, Exception
   {
     ArrayList listado = null;
     try
     {
       OCAPUsuariosOAD usuarioOAD = OCAPUsuariosOAD.getInstance();
       listado = usuarioOAD.buscarOCAPUsuariosPorNIF_a(usuarioNifNie);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e);
       throw e;
     }
 
     return listado;
   }
   
   public ArrayList buscarOCAPUsuariosPorDniReal(String usuarioNifNie)
		     throws SQLException, Exception
		   {
		     ArrayList listado = null;
		     try
		     {
		       OCAPUsuariosOAD usuarioOAD = OCAPUsuariosOAD.getInstance();
		       listado = usuarioOAD.buscarOCAPUsuariosPorDniReal(usuarioNifNie);
		     }
		     catch (SQLException exSQL) {
		       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
		       throw exSQL;
		     } catch (Exception e) {
		       this.logger.error("[ERROR] " + e);
		       throw e;
		     }
		 
		     return listado;
		   }
 
   public int bajaOCAPUsuarios(Long cUsrId)
     throws Exception
   {
     int resultado = 0;
     try {
       OCAPUsuariosOAD usuarioOAD = OCAPUsuariosOAD.getInstance();
       resultado = usuarioOAD.bajaOCAPUsuarios(cUsrId);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return resultado;
   }
 
   public ArrayList reemplazarDNIRealPorConvocatoriaGradoYEstado(long cConvocatoriaId, long cGradoId, long cEstadoId)
     throws SQLException, Exception
   {
     OCAPUsuariosOT usuOT = new OCAPUsuariosOT();
     ArrayList listaRepetidos = null;
     try
     {
       OCAPUsuariosOAD usuarioOAD = OCAPUsuariosOAD.getInstance();
       ArrayList listado = usuarioOAD.listadoUsuariosPorConvocatoriaGradoYEstado(cConvocatoriaId, cGradoId, cEstadoId);
 
       JCYLGestionTransacciones.open(false);
 
       if ((listado != null) && (listado.size() > 0)) {
         listaRepetidos = usuarioOAD.reemplazarUIDPorReal(listado);
       }
 
       if ((listaRepetidos != null) && (listaRepetidos.size() != 0))
         JCYLGestionTransacciones.rollback();
       else
         JCYLGestionTransacciones.commit(true);
     }
     catch (SQLException exSQL) {
       JCYLGestionTransacciones.rollback();
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       this.logger.error("[ERROR] " + e);
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
 
     return listaRepetidos;
   }
 
   public OCAPUsuariosOT obtenerUsuario(OCAPUsuariosOT datosBusqueda)
     throws SQLException, Exception
   {
     OCAPUsuariosOT dato = null;
     try
     {
       OCAPUsuariosOAD usuarioOAD = OCAPUsuariosOAD.getInstance();
       dato = usuarioOAD.obtenerUsuario(datosBusqueda);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e);
       throw e;
     }
 
     return dato;
   }
 
   public int actualizarUID(OCAPUsuariosOT datos)
     throws SQLException, Exception
   {
     int filas = 0;
     try
     {
       OCAPUsuariosOAD usuarioOAD = OCAPUsuariosOAD.getInstance();
       datos.setAModificadoPor(this.jcylUsuario.getUsuario().getC_usr_id());
 
       JCYLGestionTransacciones.open(false);
 
       filas = usuarioOAD.modificarUID(datos);
 
       JCYLGestionTransacciones.commit(true);
     }
     catch (SQLException exSQL) {
       JCYLGestionTransacciones.rollback();
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       this.logger.error("[ERROR] " + e);
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
 
     return filas;
   }
 
   public ArrayList buscarUsuariosConInformeMotivado(Date fInforme, long cGradoId, long cConvocatoriaId, long cGerenciaId, long cProfesionalId, JCYLUsuario jcylUsuario)
     throws SQLException, Exception
   {
     ArrayList listado = new ArrayList();
     try
     {
       OCAPUsuariosOAD usuarioOAD = OCAPUsuariosOAD.getInstance();
       listado = usuarioOAD.buscarOCAPUsuariosConInformeMotivado(fInforme, cGradoId, cConvocatoriaId, cGerenciaId, cProfesionalId, jcylUsuario);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e);
       throw e;
     }
 
     return listado;
   }
 
   public ArrayList buscarUsuariosConAclaraciones(Date fAclaraciones, long cGradoId, long cConvocatoriaId, long cGerenciaId, long cProfesionalId, JCYLUsuario jcylUsuario)
     throws SQLException, Exception
   {
     ArrayList listado = new ArrayList();
     try
     {
       OCAPUsuariosOAD usuarioOAD = OCAPUsuariosOAD.getInstance();
       listado = usuarioOAD.buscarOCAPUsuariosConAclaraciones(fAclaraciones, cGradoId, cConvocatoriaId, cGerenciaId, cProfesionalId, jcylUsuario);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e);
       throw e;
     }
 
     return listado;
   }
 
   public OCAPUsuariosOT buscarUsuariosPorID(Long cUsrId)
     throws SQLException, Exception
   {
     OCAPUsuariosOT usuOT = null;
     try
     {
       OCAPUsuariosOAD usuarioOAD = OCAPUsuariosOAD.getInstance();
       usuOT = usuarioOAD.buscarOCAPUsuarios(cUsrId);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return usuOT;
   }
 
   public OCAPUsuariosOT buscarUsuarioPorExpId(long cExpId)
     throws SQLException, Exception
   {
     OCAPUsuariosOT usuOT = new OCAPUsuariosOT();
     try {
       OCAPConfigApp.logger.info("Inicio");
       OCAPUsuariosOAD usuarioOAD = OCAPUsuariosOAD.getInstance();
       usuOT = usuarioOAD.buscarUsuarioPorExpId(cExpId);
       OCAPConfigApp.logger.info("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return usuOT;
   }
 
   public int altaUsuario(OCAPUsuariosOT datos)
     throws SQLException, Exception
   {
     int idUsuario = 0;
     try {
       OCAPConfigApp.logger.info("Inicio");
       OCAPUsuariosOAD usuarioOAD = OCAPUsuariosOAD.getInstance();
       idUsuario = usuarioOAD.altaOCAPUsuarios(datos);
       OCAPConfigApp.logger.info("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return idUsuario;
   }
 }

