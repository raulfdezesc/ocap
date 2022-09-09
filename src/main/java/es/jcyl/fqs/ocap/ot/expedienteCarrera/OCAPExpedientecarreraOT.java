 package es.jcyl.fqs.ocap.ot.expedienteCarrera;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Date;
 
 public class OCAPExpedientecarreraOT
   implements Serializable
 {
   protected Long nResMc;
   protected Date fRespuestaInconf_solic;
   protected Date fRespuestaInconf_Tmc;
   protected Date fRespuestaInconf_MC;
   protected Date fRespuestaInconf_CA;
   protected String dMotivoNegRespuesta_Tmc;
   protected String dMotivoNegRespuesta_MC;
   protected String dMotivoNegRespuesta_CA;
   protected String dDocumInconf;
   protected Long cUsrId;
   protected String dMotivoInconf_solic;
   protected Date fModificacion;
   protected Integer cGradoId;
   protected long cConvocatoriaId;
   protected long cModAnteriorId;
   protected Date fFinMc;
   protected String bBorrado;
   protected Date fInicioCc;
   protected Date fInicioCa;
   protected Date fAceptacionceis;
   protected Date fAceptacionCC;
   protected Date fAceptacSolic;
   protected String dMotivoNeg;
   protected Date fInconf_solic;
   protected String dObservNeg_solic;
   protected String bCertificado;
   protected Date fFinEvalMc;
   protected Date fRegistroSolic;
   protected Long nResCa;
   protected Date fInicioMc;
   protected Date fInicioEvalMc;
   protected Long cExpId;
   protected Date fFinCc;
   protected Date fFinCa;
   protected Date fNegacionSolic;
   protected Date fCreacion;
   protected long nAniosAntiguedad;
   protected long nMesesAntiguedad;
   protected long nDiasAntiguedad;
   protected String bInconf_plazaprop;
   protected String bInconf_antiguedad;
   protected String bPersonales;
   protected String bOtrosCentros;
   protected String bPlazo;
   protected Date fSubsanacion;
   protected String aPeticionSubsanacion;
   protected Date fRegistroOficial;
   protected long cCompfqs_id;
   protected Date fRegistroOficial_Ca;
   protected Date fRegistroOficial_Mc;
   protected Date fInconfTmc;
   protected Date fInconfMC;
   protected Date fInconfCa;
   protected Date fNegacionTmc;
   protected Date fNegacionMC;
   protected Date fNegacionCa;
   protected String dMotivoInconfTmc;
   protected String dMotivoInconfMC;
   protected String dObservNegTmc;
   protected String dObservNegMC;
   protected String dMotivoNegTmc;
   protected String dMotivoNegMC;
   protected String dRespuestaInconfSolic;
   protected String dRespuestaInconfTmc;
   protected String dRespuestaInconfMC;
   protected String dRespuestaInconfCA;
   protected String aModificadoPor;
   protected String aCreadoPor;
   protected String cProcedimientoId;
   protected String cSitAdministrativaAuxId;
   protected String dSitAdministrativaAuxOtros;
   protected long cEspecActual_id;
   protected long cEstatutActualId;
   protected long cProfesionalActual_id;
   protected long cEstatutId;
   protected String bLopdMc;
   protected String bLopdCd;
   protected Date fRegEvidenciasConf;
   protected Date fRegDocEscaneados;
   protected String bOtroServicio;
   protected String aDondeServicio;
   protected String cJuridico;
   protected String bValidacioncc;
   protected String aServicio;
   protected String aPuesto;
   protected String bVerificaDatosFaseIII;
   protected Date fAclaraciones;
   protected Date fMotivadoAcep;
   protected Date fMotivadoNeg;
   protected Date fDesistidoMC;
   protected Date fDesistido;
   protected Long cServicioId;
   protected long cItinerarioId;
   protected long cSolicitudId;
   protected long cProfesionalId;
   protected long cEspecId;
   protected String DRegimenJuridicoOtros;
   protected long cEstadoId;
   protected Date fInicioEvaluacion;
   protected String aEspecificacionesEval;
   protected Date fInformeEval;
   protected String bConformidadCTE;
   protected String bNuevaRevision;
   protected String aDiscrepanciasCTE;
   protected String aEspecificacionesCTE;
   protected Date fInformeCTE;
   protected Date fReunionCTE;
   protected String bDecisionCE;
   protected String aDiscrepanciasCE;
   protected String aEspecificacionesCE;
   protected Date fInformeCE;
   protected Date fReunionCE;
   protected Date fInformeCC;
   protected String bReconocimientoGrado;
   protected String aEspecificacionesCC;
   protected Date fRecGrado;
   protected Date fPublicacion;
   protected String dMotivosCambio;
   protected String bExclusion;
   protected ArrayList motivosExclusion;
   protected double NCreditosEvaluadosCTE;
   protected String meritosBloqueados;
   protected String usrNoMeritosCurriculares;
   protected String usrNoMeritosAsistenciales;
   protected Date fRenuncia;
   protected String cItinerario_id;
   protected boolean eliminarEvaluacion;
   protected boolean desbloqueoAsistenciales;
   protected String dniReal;
   protected Date fFin_ca_convocatoria;
 
   public void setNResMc(Long nResMc)
   {
     this.nResMc = nResMc;
   }
 
   public Long getNResMc()
   {
     return this.nResMc;
   }
 
   public void setFRespuestaInconf_solic(Date fRespuestaInconf_solic) {
     this.fRespuestaInconf_solic = fRespuestaInconf_solic;
   }
 
   public Date getFRespuestaInconf_solic()
   {
     return this.fRespuestaInconf_solic;
   }
 
   public void setFRespuestaInconf_Tmc(Date fRespuestaInconf_Tmc) {
     this.fRespuestaInconf_Tmc = fRespuestaInconf_Tmc;
   }
 
   public Date getFRespuestaInconf_Tmc()
   {
     return this.fRespuestaInconf_Tmc;
   }
 
   public void setFRespuestaInconf_MC(Date fRespuestaInconf_MC) {
     this.fRespuestaInconf_MC = fRespuestaInconf_MC;
   }
 
   public Date getFRespuestaInconf_MC()
   {
     return this.fRespuestaInconf_MC;
   }
 
   public void setFRespuestaInconf_CA(Date fRespuestaInconf_CA) {
     this.fRespuestaInconf_CA = fRespuestaInconf_CA;
   }
 
   public Date getFRespuestaInconf_CA()
   {
     return this.fRespuestaInconf_CA;
   }
 
   public void setDMotivoNegRespuesta_Tmc(String dMotivoNegRespuesta_Tmc) {
     this.dMotivoNegRespuesta_Tmc = dMotivoNegRespuesta_Tmc;
   }
 
   public String getDMotivoNegRespuesta_Tmc()
   {
     return this.dMotivoNegRespuesta_Tmc;
   }
 
   public void setDMotivoNegRespuesta_MC(String dMotivoNegRespuesta_MC) {
     this.dMotivoNegRespuesta_MC = dMotivoNegRespuesta_MC;
   }
 
   public String getDMotivoNegRespuesta_MC()
   {
     return this.dMotivoNegRespuesta_MC;
   }
 
   public void setDMotivoNegRespuesta_CA(String dMotivoNegRespuesta_CA) {
     this.dMotivoNegRespuesta_CA = dMotivoNegRespuesta_CA;
   }
 
   public String getDMotivoNegRespuesta_CA()
   {
     return this.dMotivoNegRespuesta_CA;
   }
 
   public void setDDocumInconf(String dDocumInconf) {
     this.dDocumInconf = dDocumInconf;
   }
 
   public String getDDocumInconf()
   {
     return this.dDocumInconf;
   }
 
   public void setCUsrId(Long cUsrId) {
     this.cUsrId = cUsrId;
   }
 
   public Long getCUsrId()
   {
     return this.cUsrId;
   }
 
   public void setDMotivoInconf_solic(String dMotivoInconf_solic) {
     this.dMotivoInconf_solic = dMotivoInconf_solic;
   }
 
   public String getDMotivoInconf_solic()
   {
     return this.dMotivoInconf_solic;
   }
 
   public void setFModificacion(Date fModificacion) {
     this.fModificacion = fModificacion;
   }
 
   public Date getFModificacion()
   {
     return this.fModificacion;
   }
 
   public void setCGradoId(Integer cGradoId) {
     this.cGradoId = cGradoId;
   }
 
   public Integer getCGradoId()
   {
     return this.cGradoId;
   }
 
   public void setCConvocatoriaId(long cConvocatoriaId) {
     this.cConvocatoriaId = cConvocatoriaId;
   }
 
   public long getCConvocatoriaId()
   {
     return this.cConvocatoriaId;
   }
 
   public void setCModAnteriorId(long cModAnteriorId) {
     this.cModAnteriorId = cModAnteriorId;
   }
 
   public long getCModAnteriorId()
   {
     return this.cModAnteriorId;
   }
 
   public void setFFinMc(Date fFinMc) {
     this.fFinMc = fFinMc;
   }
 
   public Date getFFinMc()
   {
     return this.fFinMc;
   }
 
   public void setBBorrado(String bBorrado) {
     this.bBorrado = bBorrado;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setFInicioCc(Date fInicioCc) {
     this.fInicioCc = fInicioCc;
   }
 
   public Date getFInicioCc()
   {
     return this.fInicioCc;
   }
 
   public void setFInicioCa(Date fInicioCa) {
     this.fInicioCa = fInicioCa;
   }
 
   public Date getFInicioCa()
   {
     return this.fInicioCa;
   }
 
   public void setFAceptacionceis(Date fAceptacionceis) {
     this.fAceptacionceis = fAceptacionceis;
   }
 
   public Date getFAceptacionceis()
   {
     return this.fAceptacionceis;
   }
 
   public void setFAceptacionCC(Date fAceptacionCC) {
     this.fAceptacionCC = fAceptacionCC;
   }
 
   public Date getFAceptacionCC()
   {
     return this.fAceptacionCC;
   }
 
   public void setFAceptacSolic(Date fAceptacSolic) {
     this.fAceptacSolic = fAceptacSolic;
   }
 
   public Date getFAceptacSolic()
   {
     return this.fAceptacSolic;
   }
 
   public void setDMotivoNeg(String dMotivoNeg) {
     this.dMotivoNeg = dMotivoNeg;
   }
 
   public String getDMotivoNeg()
   {
     return this.dMotivoNeg;
   }
 
   public void setFInconf_solic(Date fInconf_solic) {
     this.fInconf_solic = fInconf_solic;
   }
 
   public Date getFInconf_solic()
   {
     return this.fInconf_solic;
   }
 
   public void setDObservNeg_solic(String dObservNeg_solic) {
     this.dObservNeg_solic = dObservNeg_solic;
   }
 
   public String getDObservNeg_solic()
   {
     return this.dObservNeg_solic;
   }
 
   public void setBCertificado(String bCertificado) {
     this.bCertificado = bCertificado;
   }
 
   public String getBCertificado()
   {
     return this.bCertificado;
   }
 
   public void setFFinEvalMc(Date fFinEvalMc) {
     this.fFinEvalMc = fFinEvalMc;
   }
 
   public Date getFFinEvalMc()
   {
     return this.fFinEvalMc;
   }
 
   public void setFRegistroSolic(Date fRegistroSolic) {
     this.fRegistroSolic = fRegistroSolic;
   }
 
   public Date getFRegistroSolic()
   {
     return this.fRegistroSolic;
   }
 
   public void setNResCa(Long nResCa) {
     this.nResCa = nResCa;
   }
 
   public Long getNResCa()
   {
     return this.nResCa;
   }
 
   public void setFInicioMc(Date fInicioMc) {
     this.fInicioMc = fInicioMc;
   }
 
   public Date getFInicioMc()
   {
     return this.fInicioMc;
   }
 
   public void setFInicioEvalMc(Date fInicioEvalMc) {
     this.fInicioEvalMc = fInicioEvalMc;
   }
 
   public Date getFInicioEvalMc()
   {
     return this.fInicioEvalMc;
   }
 
   public void setCExpId(Long cExpId) {
     this.cExpId = cExpId;
   }
 
   public Long getCExpId()
   {
     return this.cExpId;
   }
 
   public void setFFinCc(Date fFinCc) {
     this.fFinCc = fFinCc;
   }
 
   public Date getFFinCc()
   {
     return this.fFinCc;
   }
 
   public void setFFinCa(Date fFinCa) {
     this.fFinCa = fFinCa;
   }
 
   public Date getFFinCa()
   {
     return this.fFinCa;
   }
 
   public void setFNegacionSolic(Date fNegacionSolic) {
     this.fNegacionSolic = fNegacionSolic;
   }
 
   public Date getFNegacionSolic()
   {
     return this.fNegacionSolic;
   }
 
   public void setFCreacion(Date fCreacion) {
     this.fCreacion = fCreacion;
   }
 
   public Date getFCreacion()
   {
     return this.fCreacion;
   }
 
   public void setNAniosAntiguedad(long nAniosAntiguedad) {
     this.nAniosAntiguedad = nAniosAntiguedad;
   }
 
   public long getNAniosAntiguedad()
   {
     return this.nAniosAntiguedad;
   }
 
   public void setNMesesAntiguedad(long nMesesAntiguedad) {
     this.nMesesAntiguedad = nMesesAntiguedad;
   }
 
   public long getNMesesAntiguedad()
   {
     return this.nMesesAntiguedad;
   }
 
   public void setNDiasAntiguedad(long nDiasAntiguedad) {
     this.nDiasAntiguedad = nDiasAntiguedad;
   }
 
   public long getNDiasAntiguedad()
   {
     return this.nDiasAntiguedad;
   }
 
   public void setBInconf_plazaprop(String bInconf_plazaprop) {
     this.bInconf_plazaprop = bInconf_plazaprop;
   }
 
   public String getBInconf_plazaprop()
   {
     return this.bInconf_plazaprop;
   }
 
   public void setBInconf_antiguedad(String bInconf_antiguedad) {
     this.bInconf_antiguedad = bInconf_antiguedad;
   }
 
   public String getBInconf_antiguedad()
   {
     return this.bInconf_antiguedad;
   }
 
   public void setBPersonales(String bPersonales) {
     this.bPersonales = bPersonales;
   }
 
   public String getBPersonales()
   {
     return this.bPersonales;
   }
 
   public void setBOtrosCentros(String bOtrosCentros) {
     this.bOtrosCentros = bOtrosCentros;
   }
 
   public String getBOtrosCentros()
   {
     return this.bOtrosCentros;
   }
 
   public void setBPlazo(String bPlazo) {
     this.bPlazo = bPlazo;
   }
 
   public String getBPlazo()
   {
     return this.bPlazo;
   }
 
   public void setFSubsanacion(Date fSubsanacion) {
     this.fSubsanacion = fSubsanacion;
   }
 
   public Date getFSubsanacion()
   {
     return this.fSubsanacion;
   }
 
   public void setAPeticionSubsanacion(String aPeticionSubsanacion) {
     this.aPeticionSubsanacion = aPeticionSubsanacion;
   }
 
   public String getAPeticionSubsanacion()
   {
     return this.aPeticionSubsanacion;
   }
 
   public void setFRegistroOficial(Date fRegistroOficial) {
     this.fRegistroOficial = fRegistroOficial;
   }
 
   public Date getFRegistroOficial()
   {
     return this.fRegistroOficial;
   }
 
   public void setCCompfqs_id(long cCompfqs_id) {
     this.cCompfqs_id = cCompfqs_id;
   }
 
   public long getCCompfqs_id()
   {
     return this.cCompfqs_id;
   }
 
   public void setFRegistroOficial_Ca(Date fRegistroOficial_Ca) {
     this.fRegistroOficial_Ca = fRegistroOficial_Ca;
   }
 
   public Date getFRegistroOficial_Ca()
   {
     return this.fRegistroOficial_Ca;
   }
 
   public void setFRegistroOficial_Mc(Date fRegistroOficial_Mc) {
     this.fRegistroOficial_Mc = fRegistroOficial_Mc;
   }
 
   public Date getFRegistroOficial_Mc()
   {
     return this.fRegistroOficial_Mc;
   }
 
   public void setFInconfTmc(Date fInconfTmc) {
     this.fInconfTmc = fInconfTmc;
   }
 
   public Date getFInconfTmc()
   {
     return this.fInconfTmc;
   }
 
   public void setFInconfMC(Date fInconfMC) {
     this.fInconfMC = fInconfMC;
   }
 
   public Date getFInconfMC()
   {
     return this.fInconfMC;
   }
 
   public void setFInconfCa(Date fInconfCa) {
     this.fInconfCa = fInconfCa;
   }
 
   public Date getFInconfCa()
   {
     return this.fInconfCa;
   }
 
   public void setFNegacionTmc(Date fNegacionTmc) {
     this.fNegacionTmc = fNegacionTmc;
   }
 
   public Date getFNegacionTmc()
   {
     return this.fNegacionTmc;
   }
 
   public void setFNegacionMC(Date fNegacionMC) {
     this.fNegacionMC = fNegacionMC;
   }
 
   public Date getFNegacionMC()
   {
     return this.fNegacionMC;
   }
 
   public void setFNegacionCa(Date fNegacionCa) {
     this.fNegacionCa = fNegacionCa;
   }
 
   public Date getFNegacionCa()
   {
     return this.fNegacionCa;
   }
 
   public void setDMotivoInconfTmc(String dMotivoInconfTmc) {
     this.dMotivoInconfTmc = dMotivoInconfTmc;
   }
 
   public String getDMotivoInconfTmc()
   {
     return this.dMotivoInconfTmc;
   }
 
   public void setDMotivoInconfMC(String dMotivoInconfMC) {
     this.dMotivoInconfMC = dMotivoInconfMC;
   }
 
   public String getDMotivoInconfMC()
   {
     return this.dMotivoInconfMC;
   }
 
   public void setDObservNegTmc(String dObservNegTmc) {
     this.dObservNegTmc = dObservNegTmc;
   }
 
   public String getDObservNegTmc()
   {
     return this.dObservNegTmc;
   }
 
   public void setDObservNegMC(String dObservNegMC) {
     this.dObservNegMC = dObservNegMC;
   }
 
   public String getDObservNegMC()
   {
     return this.dObservNegMC;
   }
 
   public void setDMotivoNegTmc(String dMotivoNegTmc) {
     this.dMotivoNegTmc = dMotivoNegTmc;
   }
 
   public String getDMotivoNegTmc()
   {
     return this.dMotivoNegTmc;
   }
 
   public void setDMotivoNegMC(String dMotivoNegMC) {
     this.dMotivoNegMC = dMotivoNegMC;
   }
 
   public String getDMotivoNegMC()
   {
     return this.dMotivoNegMC;
   }
 
   public void setDRespuestaInconfSolic(String dRespuestaInconfSolic) {
     this.dRespuestaInconfSolic = dRespuestaInconfSolic;
   }
 
   public String getDRespuestaInconfSolic()
   {
     return this.dRespuestaInconfSolic;
   }
 
   public void setDRespuestaInconfTmc(String dRespuestaInconfTmc) {
     this.dRespuestaInconfTmc = dRespuestaInconfTmc;
   }
 
   public String getDRespuestaInconfTmc()
   {
     return this.dRespuestaInconfTmc;
   }
 
   public void setDRespuestaInconfMC(String dRespuestaInconfMC) {
     this.dRespuestaInconfMC = dRespuestaInconfMC;
   }
 
   public String getDRespuestaInconfMC()
   {
     return this.dRespuestaInconfMC;
   }
 
   public void setDRespuestaInconfCA(String dRespuestaInconfCA) {
     this.dRespuestaInconfCA = dRespuestaInconfCA;
   }
 
   public String getDRespuestaInconfCA()
   {
     return this.dRespuestaInconfCA;
   }
 
   public void setAModificadoPor(String aModificadoPor) {
     this.aModificadoPor = aModificadoPor;
   }
 
   public String getAModificadoPor()
   {
     return this.aModificadoPor;
   }
 
   public void setACreadoPor(String aCreadoPor) {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 
   public void setCProcedimientoId(String cProcedimientoId) {
     this.cProcedimientoId = cProcedimientoId;
   }
 
   public String getCProcedimientoId()
   {
     return this.cProcedimientoId;
   }
 
   public void setCSitAdministrativaAuxId(String cSitAdministrativaAuxId) {
     this.cSitAdministrativaAuxId = cSitAdministrativaAuxId;
   }
 
   public String getCSitAdministrativaAuxId()
   {
     return this.cSitAdministrativaAuxId;
   }
 
   public void setDSitAdministrativaAuxOtros(String dSitAdministrativaAuxOtros) {
     this.dSitAdministrativaAuxOtros = dSitAdministrativaAuxOtros;
   }
 
   public String getDSitAdministrativaAuxOtros()
   {
     return this.dSitAdministrativaAuxOtros;
   }
 
   public void setCEspecActual_id(long cEspecActual_id) {
     this.cEspecActual_id = cEspecActual_id;
   }
 
   public long getCEspecActual_id()
   {
     return this.cEspecActual_id;
   }
 
   public void setCEstatutActualId(long cEstatutActualId) {
     this.cEstatutActualId = cEstatutActualId;
   }
 
   public long getCEstatutActualId()
   {
     return this.cEstatutActualId;
   }
 
   public void setCProfesionalActual_id(long cProfesionalActual_id) {
     this.cProfesionalActual_id = cProfesionalActual_id;
   }
 
   public long getCProfesionalActual_id()
   {
     return this.cProfesionalActual_id;
   }
 
   public void setCEstatutId(long cEstatutId) {
     this.cEstatutId = cEstatutId;
   }
 
   public long getCEstatutId()
   {
     return this.cEstatutId;
   }
 
   public void setBLopdMc(String bLopdMc) {
     this.bLopdMc = bLopdMc;
   }
 
   public String getBLopdMc()
   {
     return this.bLopdMc;
   }
 
   public void setBLopdCd(String bLopdCd) {
     this.bLopdCd = bLopdCd;
   }
 
   public String getBLopdCd()
   {
     return this.bLopdCd;
   }
 
   public void setFRegEvidenciasConf(Date fRegEvidenciasConf) {
     this.fRegEvidenciasConf = fRegEvidenciasConf;
   }
 
   public Date getFRegEvidenciasConf()
   {
     return this.fRegEvidenciasConf;
   }
 
   public void setFRegDocEscaneados(Date fRegDocEscaneados) {
     this.fRegDocEscaneados = fRegDocEscaneados;
   }
 
   public Date getFRegDocEscaneados()
   {
     return this.fRegDocEscaneados;
   }
 
   public void setBOtroServicio(String bOtroServicio) {
     this.bOtroServicio = bOtroServicio;
   }
 
   public String getBOtroServicio()
   {
     return this.bOtroServicio;
   }
 
   public void setADondeServicio(String aDondeServicio) {
     this.aDondeServicio = aDondeServicio;
   }
 
   public String getADondeServicio()
   {
     return this.aDondeServicio;
   }
 
   public void setCJuridico(String cJuridico) {
     this.cJuridico = cJuridico;
   }
 
   public String getCJuridico()
   {
     return this.cJuridico;
   }
 
   public void setBValidacioncc(String bValidacioncc) {
     this.bValidacioncc = bValidacioncc;
   }
 
   public String getBValidacioncc()
   {
     return this.bValidacioncc;
   }
 
   public void setAServicio(String aServicio) {
     this.aServicio = aServicio;
   }
 
   public String getAServicio()
   {
     return this.aServicio;
   }
 
   public void setAPuesto(String aPuesto) {
     this.aPuesto = aPuesto;
   }
 
   public String getAPuesto()
   {
     return this.aPuesto;
   }
 
   public void setBVerificaDatosFaseIII(String bVerificaDatosFaseIII) {
     this.bVerificaDatosFaseIII = bVerificaDatosFaseIII;
   }
 
   public String getBVerificaDatosFaseIII()
   {
     return this.bVerificaDatosFaseIII;
   }
 
   public void setFAclaraciones(Date fAclaraciones) {
     this.fAclaraciones = fAclaraciones;
   }
 
   public Date getFAclaraciones()
   {
     return this.fAclaraciones;
   }
 
   public void setFMotivadoAcep(Date fMotivadoAcep) {
     this.fMotivadoAcep = fMotivadoAcep;
   }
 
   public Date getFMotivadoAcep()
   {
     return this.fMotivadoAcep;
   }
 
   public void setFMotivadoNeg(Date fMotivadoNeg) {
     this.fMotivadoNeg = fMotivadoNeg;
   }
 
   public Date getFMotivadoNeg()
   {
     return this.fMotivadoNeg;
   }
 
   public void setFDesistidoMC(Date fDesistidoMC) {
     this.fDesistidoMC = fDesistidoMC;
   }
 
   public Date getFDesistidoMC()
   {
     return this.fDesistidoMC;
   }
 
   public void setFDesistido(Date fDesistido) {
     this.fDesistido = fDesistido;
   }
 
   public Date getFDesistido()
   {
     return this.fDesistido;
   }
 
   public void setCServicioId(Long cServicioId) {
     this.cServicioId = cServicioId;
   }
 
   public Long getCServicioId()
   {
     return this.cServicioId;
   }
 
   public void setCItinerarioId(long cItinerarioId) {
     this.cItinerarioId = cItinerarioId;
   }
 
   public long getCItinerarioId()
   {
     return this.cItinerarioId;
   }
 
   public void setCSolicitudId(long cSolicitudId) {
     this.cSolicitudId = cSolicitudId;
   }
 
   public long getCSolicitudId()
   {
     return this.cSolicitudId;
   }
 
   public void setCProfesionalId(long cProfesionalId) {
     this.cProfesionalId = cProfesionalId;
   }
 
   public long getCProfesionalId()
   {
     return this.cProfesionalId;
   }
 
   public void setCEspecId(long cEspecId) {
     this.cEspecId = cEspecId;
   }
 
   public long getCEspecId()
   {
     return this.cEspecId;
   }
 
   public void setDRegimenJuridicoOtros(String dRegimenJuridicoOtros) {
     this.DRegimenJuridicoOtros = dRegimenJuridicoOtros;
   }
 
   public String getDRegimenJuridicoOtros()
   {
     return this.DRegimenJuridicoOtros;
   }
 
   public void setCEstadoId(long cEstadoId) {
     this.cEstadoId = cEstadoId;
   }
 
   public long getCEstadoId()
   {
     return this.cEstadoId;
   }
 
   public void setFInicioEvaluacion(Date fInicioEvaluacion) {
     this.fInicioEvaluacion = fInicioEvaluacion;
   }
 
   public Date getFInicioEvaluacion()
   {
     return this.fInicioEvaluacion;
   }
 
   public void setAEspecificacionesEval(String aEspecificacionesEval) {
     this.aEspecificacionesEval = aEspecificacionesEval;
   }
 
   public String getAEspecificacionesEval()
   {
     return this.aEspecificacionesEval;
   }
 
   public void setFInformeEval(Date fInformeEval) {
     this.fInformeEval = fInformeEval;
   }
 
   public Date getFInformeEval()
   {
     return this.fInformeEval;
   }
 
   public void setBConformidadCTE(String bConformidadCTE) {
     this.bConformidadCTE = bConformidadCTE;
   }
 
   public String getBConformidadCTE()
   {
     return this.bConformidadCTE;
   }
 
   public void setBNuevaRevision(String bNuevaRevision) {
     this.bNuevaRevision = bNuevaRevision;
   }
 
   public String getBNuevaRevision()
   {
     return this.bNuevaRevision;
   }
 
   public void setADiscrepanciasCTE(String aDiscrepanciasCTE) {
     this.aDiscrepanciasCTE = aDiscrepanciasCTE;
   }
 
   public String getADiscrepanciasCTE()
   {
     return this.aDiscrepanciasCTE;
   }
 
   public void setAEspecificacionesCTE(String aEspecificacionesCTE) {
     this.aEspecificacionesCTE = aEspecificacionesCTE;
   }
 
   public String getAEspecificacionesCTE()
   {
     return this.aEspecificacionesCTE;
   }
 
   public void setFInformeCTE(Date fInformeCTE) {
     this.fInformeCTE = fInformeCTE;
   }
 
   public Date getFInformeCTE()
   {
     return this.fInformeCTE;
   }
 
   public void setFReunionCTE(Date fReunionCTE) {
     this.fReunionCTE = fReunionCTE;
   }
 
   public Date getFReunionCTE()
   {
     return this.fReunionCTE;
   }
 
   public void setBDecisionCE(String bDecisionCE) {
     this.bDecisionCE = bDecisionCE;
   }
 
   public String getBDecisionCE()
   {
     return this.bDecisionCE;
   }
 
   public void setADiscrepanciasCE(String aDiscrepanciasCE) {
     this.aDiscrepanciasCE = aDiscrepanciasCE;
   }
 
   public String getADiscrepanciasCE()
   {
     return this.aDiscrepanciasCE;
   }
 
   public void setAEspecificacionesCE(String aEspecificacionesCE) {
     this.aEspecificacionesCE = aEspecificacionesCE;
   }
 
   public String getAEspecificacionesCE()
   {
     return this.aEspecificacionesCE;
   }
 
   public void setFInformeCE(Date fInformeCE) {
     this.fInformeCE = fInformeCE;
   }
 
   public Date getFInformeCE()
   {
     return this.fInformeCE;
   }
 
   public void setFReunionCE(Date fReunionCE) {
     this.fReunionCE = fReunionCE;
   }
 
   public Date getFReunionCE()
   {
     return this.fReunionCE;
   }
 
   public void setFInformeCC(Date fInformeCC) {
     this.fInformeCC = fInformeCC;
   }
 
   public Date getFInformeCC()
   {
     return this.fInformeCC;
   }
 
   public void setBReconocimientoGrado(String bReconocimientoGrado) {
     this.bReconocimientoGrado = bReconocimientoGrado;
   }
 
   public String getBReconocimientoGrado()
   {
     return this.bReconocimientoGrado;
   }
 
   public void setAEspecificacionesCC(String aEspecificacionesCC) {
     this.aEspecificacionesCC = aEspecificacionesCC;
   }
 
   public String getAEspecificacionesCC()
   {
     return this.aEspecificacionesCC;
   }
 
   public void setFRecGrado(Date fRecGrado) {
     this.fRecGrado = fRecGrado;
   }
 
   public Date getFRecGrado()
   {
     return this.fRecGrado;
   }
 
   public void setFPublicacion(Date fPublicacion) {
     this.fPublicacion = fPublicacion;
   }
 
   public Date getFPublicacion()
   {
     return this.fPublicacion;
   }
 
   public void setDMotivosCambio(String dMotivosCambio) {
     this.dMotivosCambio = dMotivosCambio;
   }
 
   public String getDMotivosCambio()
   {
     return this.dMotivosCambio;
   }
 
   public void setBExclusion(String bExclusion) {
     this.bExclusion = bExclusion;
   }
 
   public String getBExclusion()
   {
     return this.bExclusion;
   }
 
   public void setMotivosExclusion(ArrayList motivosExclusion) {
     this.motivosExclusion = motivosExclusion;
   }
 
   public ArrayList getMotivosExclusion()
   {
     return this.motivosExclusion;
   }
 
   public void setNCreditosEvaluadosCTE(double nCreditosEvaluadosCTE) {
     this.NCreditosEvaluadosCTE = nCreditosEvaluadosCTE;
   }
 
   public double getNCreditosEvaluadosCTE()
   {
     return this.NCreditosEvaluadosCTE;
   }

/**
 * @return the meritosBloqueados
 */
public String getMeritosBloqueados() {
	return meritosBloqueados;
}

/**
 * @param meritosBloqueados the meritosBloqueados to set
 */
public void setMeritosBloqueados(String meritosBloqueados) {
	this.meritosBloqueados = meritosBloqueados;
}

public String getUsrNoMeritosCurriculares() {
	return usrNoMeritosCurriculares;
}

public void setUsrNoMeritosCurriculares(String usrNoMeritosCurriculares) {
	this.usrNoMeritosCurriculares = usrNoMeritosCurriculares;
}

public String getUsrNoMeritosAsistenciales() {
	return usrNoMeritosAsistenciales;
}

public void setUsrNoMeritosAsistenciales(String usrNoMeritosAsistenciales) {
	this.usrNoMeritosAsistenciales = usrNoMeritosAsistenciales;
}

public Date getFRenuncia() {
	return fRenuncia;
}

public void setFRenuncia(Date fRenuncia) {
	this.fRenuncia = fRenuncia;
}

public String getcItinerario_id() {
	return cItinerario_id;
}

public void setcItinerario_id(String cItinerario_id) {
	this.cItinerario_id = cItinerario_id;
}

public boolean getEliminarEvaluacion() {
	return eliminarEvaluacion;
}

public void setEliminarEvaluacion(boolean eliminarEvaluacion) {
	this.eliminarEvaluacion = eliminarEvaluacion;
}

public boolean isDesbloqueoAsistenciales() {
	return desbloqueoAsistenciales;
}

public void setDesbloqueoAsistenciales(boolean desbloqueoAsistenciales) {
	this.desbloqueoAsistenciales = desbloqueoAsistenciales;
}

public String getbBorrado() {
	return bBorrado;
}

public void setbBorrado(String bBorrado) {
	this.bBorrado = bBorrado;
}

public String getDniReal() {
	return dniReal;
}

public void setDniReal(String dniReal) {
	this.dniReal = dniReal;
}

public Date getfFin_ca_convocatoria() {
	return fFin_ca_convocatoria;
}

public void setfFin_ca_convocatoria(Date fFin_ca_convocatoria) {
	this.fFin_ca_convocatoria = fFin_ca_convocatoria;
}




}

