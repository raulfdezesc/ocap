 package es.jcyl.fqs.ocap.ot.solicitudes;
 
 import java.util.ArrayList;
import java.util.Date;

import es.jcyl.fqs.ocap.util.Utilidades;
 
 public class OCAPSolicitudesOT implements Comparable<OCAPSolicitudesOT>
 {
   private long cUsr_id;
   private String cUsuAlta;
   private String dApellido1;
   private String dApellido2;
   private String dNombre;
   private String cDni;
   private String cDniReal;
   private String cDniEnmascarado;
   private String aPassword;
   private String dCorreoelectronico;
   private long cModAnterior_id;
   private String dModAnterior;
   private String bLopdSolicitud;
   private long cCentrotrabajo_id;
   private long cEspec_id;
   private long cPerfil_id;
   private long cEstatutId;
   private long cEstatutarioId;
   private long cProfesional_id;
   private long cEspecActual_id;
   private long cEstatutActualId;
   private long cProfesionalActual_id;
   private long cGerencia_id;
   private Date fPlazapropiedad;
   private String nTelefono1;
   private String nTelefono2;
   private String dDomicilio;
   private String fModicacion;
   private String bSexo;
   private String dLocalidad;
   private String cProvinciaUsu_id;
   private String dProvinciaUsu;
   private String dLocalidadUsu;
   private String cPostalUsu;
   private String dNombreApellidos;
   private long cExp_id;
   private String codigoId;
   private Date fCreacion_exp;
   private String fRegistro_solic;
   private Date fAceptac_solic;
   private Date fNegacion_solic;
   private String dMotivo_neg;
   private String dMotivo_neg_tmc;
   private String dMotivo_neg_mc;
   private String dObserv_neg_solic;
   private String dObserv_neg_tmc;
   private String dObserv_neg_mc;
   private Date fInconf_solic;
   private String dMotivo_inconf_solic;
   private String dDocum_inconf;
   private Date fRespuesta_inconf_solic;
   private Date fRespuestaInconf_Tmc;
   private Date fRespuestaInconf_MC;
   private Date fRespuestaInconf_CA;
   private Date fInicio_mc;
   private Date fFin_mc;
   private Integer nRes_mc;
   private Date fInicio_eval_mc;
   private Date fFin_eval_mc;
   private Date fInicio_ca;
   private Date fAceptacionceis;
   private Date fAceptacionCC;
   private Date fFin_ca;
   private Integer nRes_ca;
   private Date fInicio_cc;
   private Date fFin_cc;
   private String bCertificado;
   private long cGrado_id;
   private long cConvocatoriaId;
   private long nAniosantiguedad;
   private long nMesesantiguedad;
   private long nDiasantiguedad;
   private Date fNegacion_tmc;
   private Date fNegacion_mc;
   private Date fInconf_tmc;
   private Date fInconf_mc;
   private String dMotivo_inconf_tmc;
   private String dMotivo_inconf_mc;
   private String aPuesto;
   private String aServicio;
   private String bVerificaDatosFaseIII;
   private Date fAclaraciones;
   private Date fMotivadoAcep;
   private Date fMotivadoNeg;
   private Date fDesistidoMC;
   private long cItinerario_id;
   private String bOtroServicio;
   private String aDondeServicio;
   private String cJuridico;
   private String cJuridicoId;
   private String cProcedimientoId;
   private String dProcedimiento;
   private String cProcedimientoFiltro;
   private Date fRegDocEscaneados;
   private Date fRegEvidenciasConf;
   private String dRespuestaInconfSolic;
   private String dRespuestaInconfTmc;
   private String dRespuestaInconfMC;
   private String dRespuestaInconfCA;
   private String dMotivoNegRespuesta_Tmc;
   private String dMotivoNegRespuesta_MC;
   private String dMotivoNegRespuesta_CA;
   private String dConvocatoria_nombre;
   private String cProvincia_id;
   private String cProvinciaCarrera_id;
   private String dProvincia;
   private String aCiudad;
   private String fInicio_mc_str;
   private String fFin_mc_str;
   private String fRegistro_oficial;
   private String fRegistro_oficialMC;
   private String cSitAdministrativaAuxId;
   private String cSitAdministrativaFiltro;
   private String dSitAdministrativaOtros;
   private String dSitAdministrativaAuxOtros;
   private String dSitAdministrativaAux_nombre;
   private String dModalidad;
   private String dRegimenJuridico;
   private String resumenCentros;
   private long cCompfqs_id;
   private long cCompfqs_id2;
   private String bValidacionCC;
   private String dRegimenJuridicoOtros;
   private long cEstadoId;
   private String bModPESFU;
   private String bModPESFP;
   private String bModPEGSFU;
   private String bModPEGSFP;
   private String bModPFSFU;
   private String bModPFSFP;
   private String dGrado_des;
   private String bGradoI;
   private String bGradoII;
   private String bGradoIII;
   private String bGradoIV;
   private String dEstatut_nombre;
   private String dEstatutActual_nombre;
   private String dProfesional_nombre;
   private String dProfesionalActual_nombre;
   private String dEspec_nombre;
   private String dEspecActual_nombre;
   private long cCertificado_id;
   private String dTitulo;
   private String dDesc;
   private String dDocAsociado;
   private String dGerencia_nombre;
   private String dDirector;
   private String dGerente;
   private String codGerencia;
   private String cTipogerencia_id;
   private String dTipogerencia_desc;
   private String dCentrotrabajo_nombre;
   private long cExpmc_id;
   private String fConvocatoria;
   private String dConvocatoria;
   private Date fListado;
   private Date fRecGrado;
   private long cCuestionarioId;
   private String dObservacionesEvidencia;
   private long cSolicitudId;
   private String codigoSolicitud;
   private long cEstado_id;
   private long numSolicitudes;
   private String dSolicitud;
   private String rSolicitud_acceso;
   private String rConstancia_antiguedad;
   private String rConstancia_categoria;
   private String rFase_inicio;
   private String rContinuidad_proceso;
   private String rFase_tramitacion;
   private String rContinuidad_proceso_tramitacion;
   private String rFase_validacion;
   private String rContinuidad_proceso_validacion;
   private String rFase_ca;
   private String rContinuidad_proceso_ca;
   private String bInconf_plazaprop;
   private String bInconf_antiguedad;
   private String bPersonales;
   private String bOtrosCentros;
   private String bPlazo;
   private Date fSubsanacion;
   private ArrayList listaSolicitudes;
   private ArrayList listaCertificados;
   private ArrayList listaCreditos;
   private ArrayList listaOtrosCentros;
   private String cFase;
   private String cEstado;
   private String cEstadoHist;
   private String cTipo;
   protected String dFase;
   protected String fFechaRegistro;
   private String dMensajeInforme;
   private String aModificadoPor;
   private String aCreadoPor;
   private boolean BNifNie;
   private boolean BApellidos;
   private boolean BNombre;
   private boolean BCSVSexo;
   private boolean BCategoria;
   private boolean BEspecialidad;
   private boolean BSituacionAdm;
   private boolean BProcedimiento;
   private boolean BGerencia;
   private boolean BEstado;
   private boolean BCausas;
   private boolean BConvocatoria;
   private boolean BFechaSolic;
   private boolean BJuridico;
   private boolean BAnios;
   private boolean BCentro;
   private boolean BModalidad;
   private boolean BServicio;
   private boolean BPuesto;
   private boolean BEpr;
   private long cGerenciaFiltro_id;
   private String dNombreApellidosPresi1;
   private String dNombreApellidosPresi2;
   private String dNombreApellidosVocal;
   private ArrayList listadoVocales;
   private ArrayList listadoVocalesTitulares;
   private ArrayList listadoVocalesSuplentes;
   private String dNombreApellidosSecre1;
   private String dNombreApellidosSecre2;
   private String bSexoPresi1;
   private String bSexoPresi2;
   private String bSexoVocal;
   private String bSexoSecre1;
   private String bSexoSecre2;
   private String bEnCalidadPresi1;
   private String bEnCalidadPresi2;
   private String bEnCalidadVocal;
   private String bEnCalidadSecre1;
   private String bEnCalidadSecre2;
   private String dMiembrosAusentes;
   private String dAsuntosTramite;
   private String dRuegosPreguntas;
   private Date fSesion;
   private String fSesionImprimible;
   private String nHoraInicio;
   private String nMinutosInicio;
   private String nHoraFin;
   private String nMinutosFin;
   private Date fInformeEval;
   private Date fInformeCE;
   private String bReconocimientoGrado;
   private String dNombreApellidosDtor;
   private String bSexoDtor;
   private String dAcreditaCumplimiento;
   private String dSituacionAdmin;
   private Date fConvocato;
   private String fConvocatoImprimible;
   private long cSitAdmonActualId;
   private String dSitAdmonActual;
   private String aOtraSitAdmonActual;
   private long cGerenciaActualId;
   private String dGerenciaActual;
   private long cCentroTrabActualId;
   private String dCentroTrabActual;
   private String cProvCarreraActualId;
   private String dProvCarreraActual;
   private String orden;
   private String fRenuncia;
   private Long plazoDiasCC;
   private String dAnioConvocatoria;
   private String diaActual;
   private String mesActual;
   private String anyoActual;
   
   
   private String dConvocatoriaNombreCorto;
   
   private String meritosBloqueados;
   private boolean eliminarEvaluacion;
   private Date fechaFinPgp;
   
   
  
 
   public String getDAnioConvocatoria() {
		return dAnioConvocatoria;
	}
	
	public void setDAnioConvocatoria(String dAnioConvocatoria) {
		this.dAnioConvocatoria = dAnioConvocatoria;
	}

	public void setCUsr_id(long cUsr_id)
   {
     this.cUsr_id = cUsr_id;
   }
 
   public long getCUsr_id()
   {
     return this.cUsr_id;
   }
 
   public void setCUsuAlta(String cUsuAlta)
   {
     this.cUsuAlta = cUsuAlta;
   }
 
   public String getCUsuAlta()
   {
     return this.cUsuAlta;
   }
 
   public void setDApellido1(String dApellido1)
   {
     this.dApellido1 = dApellido1;
   }
 
   public String getDApellido1()
   {
     return this.dApellido1;
   }
 
   public void setDApellido2(String dApellido2)
   {
     this.dApellido2 = dApellido2;
   }
 
   public String getDApellido2()
   {
     return this.dApellido2;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setCDni(String cDni)
   {
     this.cDni = cDni;
   }
 
   public String getCDni()
   {
     return this.cDni;
   }
 
   public void setCDniReal(String cDniReal)
   {
     this.cDniReal = cDniReal;
   }
 
   public String getCDniReal()
   {
     return this.cDniReal;
   }
 
   public void setAPassword(String aPassword)
   {
     this.aPassword = aPassword;
   }
 
   public String getAPassword()
   {
     return this.aPassword;
   }
 
   public void setDCorreoelectronico(String dCorreoelectronico)
   {
     this.dCorreoelectronico = dCorreoelectronico;
   }
 
   public String getDCorreoelectronico()
   {
     return this.dCorreoelectronico;
   }
 
   public void setCModAnterior_id(long cModAnterior_id)
   {
     this.cModAnterior_id = cModAnterior_id;
   }
 
   public long getCModAnterior_id()
   {
     return this.cModAnterior_id;
   }
 
   public void setBLopdSolicitud(String bLopdSolicitud)
   {
     this.bLopdSolicitud = bLopdSolicitud;
   }
 
   public String getBLopdSolicitud()
   {
     return this.bLopdSolicitud;
   }
 
   public void setCCentrotrabajo_id(long cCentrotrabajo_id)
   {
     this.cCentrotrabajo_id = cCentrotrabajo_id;
   }
 
   public long getCCentrotrabajo_id()
   {
     return this.cCentrotrabajo_id;
   }
 
   public void setCEspec_id(long cEspec_id)
   {
     this.cEspec_id = cEspec_id;
   }
 
   public long getCEspec_id()
   {
     return this.cEspec_id;
   }
 
   public void setCPerfil_id(long cPerfil_id)
   {
     this.cPerfil_id = cPerfil_id;
   }
 
   public long getCPerfil_id()
   {
     return this.cPerfil_id;
   }
 
   public void setCEstatutId(long cEstatutId)
   {
     this.cEstatutId = cEstatutId;
   }
 
   public long getCEstatutId()
   {
     return this.cEstatutId;
   }
 
   public void setCEstatutarioId(long cEstatutarioId)
   {
     this.cEstatutarioId = cEstatutarioId;
   }
 
   public long getCEstatutarioId()
   {
     return this.cEstatutarioId;
   }
 
   public void setCProfesional_id(long cProfesional_id)
   {
     this.cProfesional_id = cProfesional_id;
   }
 
   public long getCProfesional_id()
   {
     return this.cProfesional_id;
   }
 
   public void setCEspecActual_id(long cEspecActual_id)
   {
     this.cEspecActual_id = cEspecActual_id;
   }
 
   public long getCEspecActual_id()
   {
     return this.cEspecActual_id;
   }
 
   public void setCEstatutActualId(long cEstatutActualId)
   {
     this.cEstatutActualId = cEstatutActualId;
   }
 
   public long getCEstatutActualId()
   {
     return this.cEstatutActualId;
   }
 
   public void setCProfesionalActual_id(long cProfesionalActual_id)
   {
     this.cProfesionalActual_id = cProfesionalActual_id;
   }
 
   public long getCProfesionalActual_id()
   {
     return this.cProfesionalActual_id;
   }
 
   public void setCGerencia_id(long cGerencia_id)
   {
     this.cGerencia_id = cGerencia_id;
   }
 
   public long getCGerencia_id()
   {
     return this.cGerencia_id;
   }
 
   public void setFPlazapropiedad(Date fPlazapropiedad)
   {
     this.fPlazapropiedad = fPlazapropiedad;
   }
 
   public Date getFPlazapropiedad()
   {
     return this.fPlazapropiedad;
   }
 
   public void setNTelefono1(String nTelefono1)
   {
     this.nTelefono1 = nTelefono1;
   }
 
   public String getNTelefono1()
   {
     return this.nTelefono1;
   }
 
   public void setNTelefono2(String nTelefono2)
   {
     this.nTelefono2 = nTelefono2;
   }
 
   public String getNTelefono2()
   {
     return this.nTelefono2;
   }
 
   public void setDDomicilio(String dDomicilio)
   {
     this.dDomicilio = dDomicilio;
   }
 
   public String getDDomicilio()
   {
     return this.dDomicilio;
   }
 
   public void setFModicacion(String fModicacion)
   {
     this.fModicacion = fModicacion;
   }
 
   public String getFModicacion()
   {
     return this.fModicacion;
   }
 
   public void setBSexo(String bSexo)
   {
     this.bSexo = bSexo;
   }
 
   public String getBSexo()
   {
     return this.bSexo;
   }
 
   public void setDLocalidad(String dLocalidad)
   {
     this.dLocalidad = dLocalidad;
   }
 
   public String getDLocalidad()
   {
     return this.dLocalidad;
   }
 
   public void setCProvinciaUsu_id(String cProvinciaUsu_id)
   {
     this.cProvinciaUsu_id = cProvinciaUsu_id;
   }
 
   public String getCProvinciaUsu_id()
   {
     return this.cProvinciaUsu_id;
   }
 
   public void setDProvinciaUsu(String dProvinciaUsu)
   {
     this.dProvinciaUsu = dProvinciaUsu;
   }
 
   public String getDProvinciaUsu()
   {
     return this.dProvinciaUsu;
   }
 
   public void setDLocalidadUsu(String dLocalidadUsu)
   {
     this.dLocalidadUsu = dLocalidadUsu;
   }
 
   public String getDLocalidadUsu()
   {
     return this.dLocalidadUsu;
   }
 
   public void setCPostalUsu(String cPostalUsu)
   {
     this.cPostalUsu = cPostalUsu;
   }
 
   public String getCPostalUsu()
   {
     return this.cPostalUsu;
   }
 
   public void setDNombreApellidos(String dNombreApellidos)
   {
     this.dNombreApellidos = dNombreApellidos;
   }
 
   public String getDNombreApellidos()
   {
     return this.dNombreApellidos;
   }
 
   public void setCExp_id(long cExp_id)
   {
     this.cExp_id = cExp_id;
   }
 
   public long getCExp_id()
   {
     return this.cExp_id;
   }
 
   public void setCodigoId(String codigoId)
   {
     this.codigoId = codigoId;
   }
 
   public String getCodigoId()
   {
     return this.codigoId;
   }
 
   public void setFCreacion_exp(Date fCreacion_exp)
   {
     this.fCreacion_exp = fCreacion_exp;
   }
 
   public Date getFCreacion_exp()
   {
     return this.fCreacion_exp;
   }
 
   public void setFRegistro_solic(String fRegistro_solic)
   {
     this.fRegistro_solic = fRegistro_solic;
   }
 
   public String getFRegistro_solic()
   {
     return this.fRegistro_solic;
   }
 
   public void setFAceptac_solic(Date fAceptac_solic)
   {
     this.fAceptac_solic = fAceptac_solic;
   }
 
   public Date getFAceptac_solic()
   {
     return this.fAceptac_solic;
   }
 
   public void setFNegacion_solic(Date fNegacion_solic)
   {
     this.fNegacion_solic = fNegacion_solic;
   }
 
   public Date getFNegacion_solic()
   {
     return this.fNegacion_solic;
   }
 
   public void setDMotivo_neg(String dMotivo_neg)
   {
     this.dMotivo_neg = dMotivo_neg;
   }
 
   public String getDMotivo_neg()
   {
     return this.dMotivo_neg;
   }
 
   public void setDMotivo_neg_tmc(String dMotivo_neg_tmc)
   {
     this.dMotivo_neg_tmc = dMotivo_neg_tmc;
   }
 
   public String getDMotivo_neg_tmc()
   {
     return this.dMotivo_neg_tmc;
   }
 
   public void setDMotivo_neg_mc(String dMotivo_neg_mc)
   {
     this.dMotivo_neg_mc = dMotivo_neg_mc;
   }
 
   public String getDMotivo_neg_mc()
   {
     return this.dMotivo_neg_mc;
   }
 
   public void setDObserv_neg_solic(String dObserv_neg_solic)
   {
     this.dObserv_neg_solic = dObserv_neg_solic;
   }
 
   public String getDObserv_neg_solic()
   {
     return this.dObserv_neg_solic;
   }
 
   public void setDObserv_neg_tmc(String dObserv_neg_tmc)
   {
     this.dObserv_neg_tmc = dObserv_neg_tmc;
   }
 
   public String getDObserv_neg_tmc()
   {
     return this.dObserv_neg_tmc;
   }
 
   public void setDObserv_neg_mc(String dObserv_neg_mc)
   {
     this.dObserv_neg_mc = dObserv_neg_mc;
   }
 
   public String getDObserv_neg_mc()
   {
     return this.dObserv_neg_mc;
   }
 
   public void setFInconf_solic(Date fInconf_solic)
   {
     this.fInconf_solic = fInconf_solic;
   }
 
   public Date getFInconf_solic()
   {
     return this.fInconf_solic;
   }
 
   public void setDMotivo_inconf_solic(String dMotivo_inconf_solic)
   {
     this.dMotivo_inconf_solic = dMotivo_inconf_solic;
   }
 
   public String getDMotivo_inconf_solic()
   {
     return this.dMotivo_inconf_solic;
   }
 
   public void setDDocum_inconf(String dDocum_inconf)
   {
     this.dDocum_inconf = dDocum_inconf;
   }
 
   public String getDDocum_inconf()
   {
     return this.dDocum_inconf;
   }
 
   public void setFRespuesta_inconf_solic(Date fRespuesta_inconf_solic)
   {
     this.fRespuesta_inconf_solic = fRespuesta_inconf_solic;
   }
 
   public Date getFRespuesta_inconf_solic()
   {
     return this.fRespuesta_inconf_solic;
   }
 
   public void setFRespuestaInconf_Tmc(Date fRespuestaInconf_Tmc)
   {
     this.fRespuestaInconf_Tmc = fRespuestaInconf_Tmc;
   }
 
   public Date getFRespuestaInconf_Tmc()
   {
     return this.fRespuestaInconf_Tmc;
   }
 
   public void setFRespuestaInconf_MC(Date fRespuestaInconf_MC)
   {
     this.fRespuestaInconf_MC = fRespuestaInconf_MC;
   }
 
   public Date getFRespuestaInconf_MC()
   {
     return this.fRespuestaInconf_MC;
   }
 
   public void setFRespuestaInconf_CA(Date fRespuestaInconf_CA)
   {
     this.fRespuestaInconf_CA = fRespuestaInconf_CA;
   }
 
   public Date getFRespuestaInconf_CA()
   {
     return this.fRespuestaInconf_CA;
   }
 
   public void setFInicio_mc(Date fInicio_mc)
   {
     this.fInicio_mc = fInicio_mc;
   }
 
   public Date getFInicio_mc()
   {
     return this.fInicio_mc;
   }
 
   public void setFFin_mc(Date fFin_mc)
   {
     this.fFin_mc = fFin_mc;
   }
 
   public Date getFFin_mc()
   {
     return this.fFin_mc;
   }
 
   public void setNRes_mc(Integer nRes_mc)
   {
     this.nRes_mc = nRes_mc;
   }
 
   public Integer getNRes_mc()
   {
     return this.nRes_mc;
   }
 
   public void setFInicio_eval_mc(Date fInicio_eval_mc)
   {
     this.fInicio_eval_mc = fInicio_eval_mc;
   }
 
   public Date getFInicio_eval_mc()
   {
     return this.fInicio_eval_mc;
   }
 
   public void setFFin_eval_mc(Date fFin_eval_mc)
   {
     this.fFin_eval_mc = fFin_eval_mc;
   }
 
   public Date getFFin_eval_mc()
   {
     return this.fFin_eval_mc;
   }
 
   public void setFInicio_ca(Date fInicio_ca)
   {
     this.fInicio_ca = fInicio_ca;
   }
 
   public Date getFInicio_ca()
   {
     return this.fInicio_ca;
   }
 
   public void setFAceptacionceis(Date fAceptacionceis)
   {
     this.fAceptacionceis = fAceptacionceis;
   }
 
   public Date getFAceptacionceis()
   {
     return this.fAceptacionceis;
   }
 
   public void setFAceptacionCC(Date fAceptacionCC)
   {
     this.fAceptacionCC = fAceptacionCC;
   }
 
   public Date getFAceptacionCC()
   {
     return this.fAceptacionCC;
   }
 
   public void setFFin_ca(Date fFin_ca)
   {
     this.fFin_ca = fFin_ca;
   }
 
   public Date getFFin_ca()
   {
     return this.fFin_ca;
   }
 
   public void setNRes_ca(Integer nRes_ca)
   {
     this.nRes_ca = nRes_ca;
   }
 
   public Integer getNRes_ca()
   {
     return this.nRes_ca;
   }
 
   public void setFInicio_cc(Date fInicio_cc)
   {
     this.fInicio_cc = fInicio_cc;
   }
 
   public Date getFInicio_cc()
   {
     return this.fInicio_cc;
   }
 
   public void setFFin_cc(Date fFin_cc)
   {
     this.fFin_cc = fFin_cc;
   }
 
   public Date getFFin_cc()
   {
     return this.fFin_cc;
   }
 
   public void setBCertificado(String bCertificado)
   {
     this.bCertificado = bCertificado;
   }
 
   public String getBCertificado()
   {
     return this.bCertificado;
   }
 
   public void setCGrado_id(long cGrado_id)
   {
     this.cGrado_id = cGrado_id;
   }
 
   public long getCGrado_id()
   {
     return this.cGrado_id;
   }
 
   public void setCConvocatoriaId(long cConvocatoriaId)
   {
     this.cConvocatoriaId = cConvocatoriaId;
   }
 
   public long getCConvocatoriaId()
   {
     return this.cConvocatoriaId;
   }
 
   public void setNAniosantiguedad(long nAniosantiguedad)
   {
     this.nAniosantiguedad = nAniosantiguedad;
   }
 
   public long getNAniosantiguedad()
   {
     return this.nAniosantiguedad;
   }
 
   public void setNMesesantiguedad(long nMesesantiguedad)
   {
     this.nMesesantiguedad = nMesesantiguedad;
   }
 
   public long getNMesesantiguedad()
   {
     return this.nMesesantiguedad;
   }
 
   public void setNDiasantiguedad(long nDiasantiguedad)
   {
     this.nDiasantiguedad = nDiasantiguedad;
   }
 
   public long getNDiasantiguedad()
   {
     return this.nDiasantiguedad;
   }
 
   public void setFNegacion_tmc(Date fNegacion_tmc)
   {
     this.fNegacion_tmc = fNegacion_tmc;
   }
 
   public Date getFNegacion_tmc()
   {
     return this.fNegacion_tmc;
   }
 
   public void setFNegacion_mc(Date fNegacion_mc)
   {
     this.fNegacion_mc = fNegacion_mc;
   }
 
   public Date getFNegacion_mc()
   {
     return this.fNegacion_mc;
   }
 
   public void setFInconf_tmc(Date fInconf_tmc)
   {
     this.fInconf_tmc = fInconf_tmc;
   }
 
   public Date getFInconf_tmc()
   {
     return this.fInconf_tmc;
   }
 
   public void setFInconf_mc(Date fInconf_mc)
   {
     this.fInconf_mc = fInconf_mc;
   }
 
   public Date getFInconf_mc()
   {
     return this.fInconf_mc;
   }
 
   public void setDMotivo_inconf_tmc(String dMotivo_inconf_tmc)
   {
     this.dMotivo_inconf_tmc = dMotivo_inconf_tmc;
   }
 
   public String getDMotivo_inconf_tmc()
   {
     return this.dMotivo_inconf_tmc;
   }
 
   public void setDMotivo_inconf_mc(String dMotivo_inconf_mc)
   {
     this.dMotivo_inconf_mc = dMotivo_inconf_mc;
   }
 
   public String getDMotivo_inconf_mc()
   {
     return this.dMotivo_inconf_mc;
   }
 
   public void setAPuesto(String aPuesto)
   {
     this.aPuesto = aPuesto;
   }
 
   public String getAPuesto()
   {
     return this.aPuesto;
   }
 
   public void setAServicio(String aServicio)
   {
     this.aServicio = aServicio;
   }
 
   public String getAServicio()
   {
     return this.aServicio;
   }
 
   public void setBVerificaDatosFaseIII(String bVerificaDatosFaseIII)
   {
     this.bVerificaDatosFaseIII = bVerificaDatosFaseIII;
   }
 
   public String getBVerificaDatosFaseIII()
   {
     return this.bVerificaDatosFaseIII;
   }
 
   public void setFAclaraciones(Date fAclaraciones)
   {
     this.fAclaraciones = fAclaraciones;
   }
 
   public Date getFAclaraciones()
   {
     return this.fAclaraciones;
   }
 
   public void setFMotivadoAcep(Date fMotivadoAcep)
   {
     this.fMotivadoAcep = fMotivadoAcep;
   }
 
   public Date getFMotivadoAcep()
   {
     return this.fMotivadoAcep;
   }
 
   public void setFMotivadoNeg(Date fMotivadoNeg)
   {
     this.fMotivadoNeg = fMotivadoNeg;
   }
 
   public Date getFMotivadoNeg()
   {
     return this.fMotivadoNeg;
   }
 
   public void setFDesistidoMC(Date fDesistidoMC)
   {
     this.fDesistidoMC = fDesistidoMC;
   }
 
   public Date getFDesistidoMC()
   {
     return this.fDesistidoMC;
   }
 
   public void setCItinerario_id(long cItinerario_id)
   {
     this.cItinerario_id = cItinerario_id;
   }
 
   public long getCItinerario_id()
   {
     return this.cItinerario_id;
   }
 
   public void setBOtroServicio(String bOtroServicio)
   {
     this.bOtroServicio = bOtroServicio;
   }
 
   public String getBOtroServicio()
   {
     return this.bOtroServicio;
   }
 
   public void setADondeServicio(String aDondeServicio)
   {
     this.aDondeServicio = aDondeServicio;
   }
 
   public String getADondeServicio()
   {
     return this.aDondeServicio;
   }
 
   public void setCJuridico(String cJuridico)
   {
     this.cJuridico = cJuridico;
   }
 
   public String getCJuridico()
   {
     return this.cJuridico;
   }
 
   public void setCJuridicoId(String cJuridicoId)
   {
     this.cJuridicoId = cJuridicoId;
   }
 
   public String getCJuridicoId()
   {
     return this.cJuridicoId;
   }
 
   public void setDProcedimiento(String dProcedimiento)
   {
     this.dProcedimiento = dProcedimiento;
   }
 
   public String getDProcedimiento()
   {
     return this.dProcedimiento;
   }
 
   public void setCProcedimientoFiltro(String cProcedimientoFiltro)
   {
     this.cProcedimientoFiltro = cProcedimientoFiltro;
   }
 
   public String getCProcedimientoFiltro()
   {
     return this.cProcedimientoFiltro;
   }
 
   public void setFRegDocEscaneados(Date fRegDocEscaneados)
   {
     this.fRegDocEscaneados = fRegDocEscaneados;
   }
 
   public Date getFRegDocEscaneados()
   {
     return this.fRegDocEscaneados;
   }
 
   public void setFRegEvidenciasConf(Date fRegEvidenciasConf)
   {
     this.fRegEvidenciasConf = fRegEvidenciasConf;
   }
 
   public Date getFRegEvidenciasConf()
   {
     return this.fRegEvidenciasConf;
   }
 
   public void setDRespuestaInconfSolic(String dRespuestaInconfSolic)
   {
     this.dRespuestaInconfSolic = dRespuestaInconfSolic;
   }
 
   public String getDRespuestaInconfSolic()
   {
     return this.dRespuestaInconfSolic;
   }
 
   public void setDRespuestaInconfTmc(String dRespuestaInconfTmc)
   {
     this.dRespuestaInconfTmc = dRespuestaInconfTmc;
   }
 
   public String getDRespuestaInconfTmc()
   {
     return this.dRespuestaInconfTmc;
   }
 
   public void setDRespuestaInconfMC(String dRespuestaInconfMC)
   {
     this.dRespuestaInconfMC = dRespuestaInconfMC;
   }
 
   public String getDRespuestaInconfMC()
   {
     return this.dRespuestaInconfMC;
   }
 
   public void setDRespuestaInconfCA(String dRespuestaInconfCA)
   {
     this.dRespuestaInconfCA = dRespuestaInconfCA;
   }
 
   public String getDRespuestaInconfCA()
   {
     return this.dRespuestaInconfCA;
   }
 
   public void setDMotivoNegRespuesta_Tmc(String dMotivoNegRespuesta_Tmc)
   {
     this.dMotivoNegRespuesta_Tmc = dMotivoNegRespuesta_Tmc;
   }
 
   public String getDMotivoNegRespuesta_Tmc()
   {
     return this.dMotivoNegRespuesta_Tmc;
   }
 
   public void setDMotivoNegRespuesta_MC(String dMotivoNegRespuesta_MC)
   {
     this.dMotivoNegRespuesta_MC = dMotivoNegRespuesta_MC;
   }
 
   public String getDMotivoNegRespuesta_MC()
   {
     return this.dMotivoNegRespuesta_MC;
   }
 
   public void setDMotivoNegRespuesta_CA(String dMotivoNegRespuesta_CA)
   {
     this.dMotivoNegRespuesta_CA = dMotivoNegRespuesta_CA;
   }
 
   public String getDMotivoNegRespuesta_CA()
   {
     return this.dMotivoNegRespuesta_CA;
   }
 
   public void setDConvocatoria_nombre(String dConvocatoria_nombre)
   {
     this.dConvocatoria_nombre = dConvocatoria_nombre;
   }
 
   public String getDConvocatoria_nombre()
   {
     return this.dConvocatoria_nombre;
   }
 
   public void setCProvincia_id(String cProvincia_id)
   {
     this.cProvincia_id = cProvincia_id;
   }
 
   public String getCProvincia_id()
   {
     return this.cProvincia_id;
   }
 
   public void setCProvinciaCarrera_id(String cProvinciaCarrera_id)
   {
     this.cProvinciaCarrera_id = cProvinciaCarrera_id;
   }
 
   public String getCProvinciaCarrera_id()
   {
     return this.cProvinciaCarrera_id;
   }
 
   public void setDProvincia(String dProvincia)
   {
     this.dProvincia = dProvincia;
   }
 
   public String getDProvincia()
   {
     return this.dProvincia;
   }
 
   public void setACiudad(String aCiudad)
   {
     this.aCiudad = aCiudad;
   }
 
   public String getACiudad()
   {
     return this.aCiudad;
   }
 
   public void setFInicio_mc_str(String fInicio_mc_str)
   {
     this.fInicio_mc_str = fInicio_mc_str;
   }
 
   public String getFInicio_mc_str()
   {
     return this.fInicio_mc_str;
   }
 
   public void setFFin_mc_str(String fFin_mc_str)
   {
     this.fFin_mc_str = fFin_mc_str;
   }
 
   public String getFFin_mc_str()
   {
     return this.fFin_mc_str;
   }
 
   public void setFRegistro_oficial(String fRegistro_oficial)
   {
     this.fRegistro_oficial = fRegistro_oficial;
   }
 
   public String getFRegistro_oficial()
   {
     return this.fRegistro_oficial;
   }
 
   public void setFRegistro_oficialMC(String fRegistro_oficialMC)
   {
     this.fRegistro_oficialMC = fRegistro_oficialMC;
   }
 
   public String getFRegistro_oficialMC()
   {
     return this.fRegistro_oficialMC;
   }
 
   public void setCSitAdministrativaAuxId(String cSitAdministrativaAuxId)
   {
     this.cSitAdministrativaAuxId = cSitAdministrativaAuxId;
   }
 
   public String getCSitAdministrativaAuxId()
   {
     return this.cSitAdministrativaAuxId;
   }
 
   public void setCSitAdministrativaFiltro(String cSitAdministrativaFiltro)
   {
     this.cSitAdministrativaFiltro = cSitAdministrativaFiltro;
   }
 
   public String getCSitAdministrativaFiltro()
   {
     return this.cSitAdministrativaFiltro;
   }
 
   public void setDSitAdministrativaOtros(String dSitAdministrativaOtros)
   {
     this.dSitAdministrativaOtros = dSitAdministrativaOtros;
   }
 
   public String getDSitAdministrativaOtros()
   {
     return this.dSitAdministrativaOtros;
   }
 
   public void setDSitAdministrativaAuxOtros(String dSitAdministrativaAuxOtros)
   {
     this.dSitAdministrativaAuxOtros = dSitAdministrativaAuxOtros;
   }
 
   public String getDSitAdministrativaAuxOtros()
   {
     return this.dSitAdministrativaAuxOtros;
   }
 
   public void setDSitAdministrativaAux_nombre(String dSitAdministrativaAux_nombre)
   {
     this.dSitAdministrativaAux_nombre = dSitAdministrativaAux_nombre;
   }
 
   public String getDSitAdministrativaAux_nombre()
   {
     return this.dSitAdministrativaAux_nombre;
   }
 
   public void setResumenCentros(String resumenCentros)
   {
     this.resumenCentros = resumenCentros;
   }
 
   public String getResumenCentros()
   {
     return this.resumenCentros;
   }
 
   public void setCCompfqs_id(long cCompfqs_id)
   {
     this.cCompfqs_id = cCompfqs_id;
   }
 
   public long getCCompfqs_id()
   {
     return this.cCompfqs_id;
   }
 
   public void setCCompfqs_id2(long cCompfqs_id2)
   {
     this.cCompfqs_id2 = cCompfqs_id2;
   }
 
   public long getCCompfqs_id2()
   {
     return this.cCompfqs_id2;
   }
 
   public void setBValidacionCC(String bValidacionCC)
   {
     this.bValidacionCC = bValidacionCC;
   }
 
   public String getBValidacionCC()
   {
     return this.bValidacionCC;
   }
 
   public void setDGrado_des(String dGrado_des)
   {
     this.dGrado_des = dGrado_des;
   }
 
   public String getDGrado_des()
   {
     return this.dGrado_des;
   }
 
   public void setDEstatut_nombre(String dEstatut_nombre)
   {
     this.dEstatut_nombre = dEstatut_nombre;
   }
 
   public String getDEstatut_nombre()
   {
     return this.dEstatut_nombre;
   }
 
   public void setDEstatutActual_nombre(String dEstatutActual_nombre)
   {
     this.dEstatutActual_nombre = dEstatutActual_nombre;
   }
 
   public String getDEstatutActual_nombre()
   {
     return this.dEstatutActual_nombre;
   }
 
   public void setDProfesional_nombre(String dProfesional_nombre)
   {
     this.dProfesional_nombre = dProfesional_nombre;
   }
 
   public String getDProfesional_nombre()
   {
     return this.dProfesional_nombre;
   }
 
   public void setDProfesionalActual_nombre(String dProfesionalActual_nombre)
   {
     this.dProfesionalActual_nombre = dProfesionalActual_nombre;
   }
 
   public String getDProfesionalActual_nombre()
   {
     return this.dProfesionalActual_nombre;
   }
 
   public void setDEspec_nombre(String dEspec_nombre)
   {
     this.dEspec_nombre = dEspec_nombre;
   }
 
   public String getDEspec_nombre()
   {
     return this.dEspec_nombre;
   }
 
   public void setDEspecActual_nombre(String dEspecActual_nombre)
   {
     this.dEspecActual_nombre = dEspecActual_nombre;
   }
 
   public String getDEspecActual_nombre()
   {
     return this.dEspecActual_nombre;
   }
 
   public void setCCertificado_id(long cCertificado_id)
   {
     this.cCertificado_id = cCertificado_id;
   }
 
   public long getCCertificado_id()
   {
     return this.cCertificado_id;
   }
 
   public void setDTitulo(String dTitulo)
   {
     this.dTitulo = dTitulo;
   }
 
   public String getDTitulo()
   {
     return this.dTitulo;
   }
 
   public void setDDesc(String dDesc)
   {
     this.dDesc = dDesc;
   }
 
   public String getDDesc()
   {
     return this.dDesc;
   }
 
   public void setDDocAsociado(String dDocAsociado)
   {
     this.dDocAsociado = dDocAsociado;
   }
 
   public String getDDocAsociado()
   {
     return this.dDocAsociado;
   }
 
   public void setDGerencia_nombre(String dGerencia_nombre)
   {
     this.dGerencia_nombre = dGerencia_nombre;
   }
 
   public String getDGerencia_nombre()
   {
     return this.dGerencia_nombre;
   }
 
   public void setDDirector(String dDirector)
   {
     this.dDirector = dDirector;
   }
 
   public String getDDirector()
   {
     return this.dDirector;
   }
 
   public void setDGerente(String dGerente)
   {
     this.dGerente = dGerente;
   }
 
   public String getDGerente()
   {
     return this.dGerente;
   }
 
   public void setCodGerencia(String codGerencia)
   {
     this.codGerencia = codGerencia;
   }
 
   public String getCodGerencia()
   {
     return this.codGerencia;
   }
 
   public void setCTipogerencia_id(String cTipogerencia_id)
   {
     this.cTipogerencia_id = cTipogerencia_id;
   }
 
   public String getCTipogerencia_id()
   {
     return this.cTipogerencia_id;
   }
 
   public void setDTipogerencia_desc(String dTipogerencia_desc)
   {
     this.dTipogerencia_desc = dTipogerencia_desc;
   }
 
   public String getDTipogerencia_desc()
   {
     return this.dTipogerencia_desc;
   }
 
   public void setDCentrotrabajo_nombre(String dCentrotrabajo_nombre)
   {
     this.dCentrotrabajo_nombre = dCentrotrabajo_nombre;
   }
 
   public String getDCentrotrabajo_nombre()
   {
     return this.dCentrotrabajo_nombre;
   }
 
   public void setCExpmc_id(long cExpmc_id)
   {
     this.cExpmc_id = cExpmc_id;
   }
 
   public long getCExpmc_id()
   {
     return this.cExpmc_id;
   }
 
   public void setFConvocatoria(String fConvocatoria)
   {
     this.fConvocatoria = fConvocatoria;
   }
 
   public String getFConvocatoria()
   {
     return this.fConvocatoria;
   }
 
   public void setDConvocatoria(String dConvocatoria)
   {
     this.dConvocatoria = dConvocatoria;
   }
 
   public String getDConvocatoria()
   {
     return this.dConvocatoria;
   }
 
   public void setFListado(Date fListado)
   {
     this.fListado = fListado;
   }
 
   public Date getFListado()
   {
     return this.fListado;
   }
 
   public void setCCuestionarioId(long cCuestionarioId)
   {
     this.cCuestionarioId = cCuestionarioId;
   }
 
   public long getCCuestionarioId()
   {
     return this.cCuestionarioId;
   }
 
   public void setDObservacionesEvidencia(String dObservacionesEvidencia)
   {
     this.dObservacionesEvidencia = dObservacionesEvidencia;
   }
 
   public String getDObservacionesEvidencia()
   {
     return this.dObservacionesEvidencia;
   }
 
   public void setCSolicitudId(long cSolicitudId)
   {
     this.cSolicitudId = cSolicitudId;
   }
 
   public long getCSolicitudId()
   {
     return this.cSolicitudId;
   }
 
   public void setCEstado_id(long cEstado_id)
   {
     this.cEstado_id = cEstado_id;
   }
 
   public long getCEstado_id()
   {
     return this.cEstado_id;
   }
 
   public void setDSolicitud(String dSolicitud)
   {
     this.dSolicitud = dSolicitud;
   }
 
   public String getDSolicitud()
   {
     return this.dSolicitud;
   }
 
   public void setRSolicitud_acceso(String rSolicitud_acceso)
   {
     this.rSolicitud_acceso = rSolicitud_acceso;
   }
 
   public String getRSolicitud_acceso()
   {
     return this.rSolicitud_acceso;
   }
 
   public void setRConstancia_antiguedad(String rConstancia_antiguedad)
   {
     this.rConstancia_antiguedad = rConstancia_antiguedad;
   }
 
   public String getRConstancia_antiguedad()
   {
     return this.rConstancia_antiguedad;
   }
 
   public void setRConstancia_categoria(String rConstancia_categoria)
   {
     this.rConstancia_categoria = rConstancia_categoria;
   }
 
   public String getRConstancia_categoria()
   {
     return this.rConstancia_categoria;
   }
 
   public void setRFase_inicio(String rFase_inicio)
   {
     this.rFase_inicio = rFase_inicio;
   }
 
   public String getRFase_inicio()
   {
     return this.rFase_inicio;
   }
 
   public void setRContinuidad_proceso(String rContinuidad_proceso)
   {
     this.rContinuidad_proceso = rContinuidad_proceso;
   }
 
   public String getRContinuidad_proceso()
   {
     return this.rContinuidad_proceso;
   }
 
   public void setRFase_tramitacion(String rFase_tramitacion)
   {
     this.rFase_tramitacion = rFase_tramitacion;
   }
 
   public String getRFase_tramitacion()
   {
     return this.rFase_tramitacion;
   }
 
   public void setRContinuidad_proceso_tramitacion(String rContinuidad_proceso_tramitacion)
   {
     this.rContinuidad_proceso_tramitacion = rContinuidad_proceso_tramitacion;
   }
 
   public String getRContinuidad_proceso_tramitacion()
   {
     return this.rContinuidad_proceso_tramitacion;
   }
 
   public void setRFase_validacion(String rFase_validacion)
   {
     this.rFase_validacion = rFase_validacion;
   }
 
   public String getRFase_validacion()
   {
     return this.rFase_validacion;
   }
 
   public void setRContinuidad_proceso_validacion(String rContinuidad_proceso_validacion)
   {
     this.rContinuidad_proceso_validacion = rContinuidad_proceso_validacion;
   }
 
   public String getRContinuidad_proceso_validacion()
   {
     return this.rContinuidad_proceso_validacion;
   }
 
   public void setRFase_ca(String rFase_ca)
   {
     this.rFase_ca = rFase_ca;
   }
 
   public String getRFase_ca()
   {
     return this.rFase_ca;
   }
 
   public void setRContinuidad_proceso_ca(String rContinuidad_proceso_ca)
   {
     this.rContinuidad_proceso_ca = rContinuidad_proceso_ca;
   }
 
   public String getRContinuidad_proceso_ca()
   {
     return this.rContinuidad_proceso_ca;
   }
 
   public void setBInconf_plazaprop(String bInconf_plazaprop)
   {
     this.bInconf_plazaprop = bInconf_plazaprop;
   }
 
   public String getBInconf_plazaprop()
   {
     return this.bInconf_plazaprop;
   }
 
   public void setBInconf_antiguedad(String bInconf_antiguedad)
   {
     this.bInconf_antiguedad = bInconf_antiguedad;
   }
 
   public String getBInconf_antiguedad()
   {
     return this.bInconf_antiguedad;
   }
 
   public void setBPersonales(String bPersonales)
   {
     this.bPersonales = bPersonales;
   }
 
   public String getBPersonales()
   {
     return this.bPersonales;
   }
 
   public void setBOtrosCentros(String bOtrosCentros)
   {
     this.bOtrosCentros = bOtrosCentros;
   }
 
   public String getBOtrosCentros()
   {
     return this.bOtrosCentros;
   }
 
   public void setBPlazo(String bPlazo)
   {
     this.bPlazo = bPlazo;
   }
 
   public String getBPlazo()
   {
     return this.bPlazo;
   }
 
   public void setFSubsanacion(Date fSubsanacion)
   {
     this.fSubsanacion = fSubsanacion;
   }
 
   public Date getFSubsanacion()
   {
     return this.fSubsanacion;
   }
 
   public void setListaSolicitudes(ArrayList listaSolicitudes)
   {
     this.listaSolicitudes = listaSolicitudes;
   }
 
   public ArrayList getListaSolicitudes()
   {
     return this.listaSolicitudes;
   }
 
   public void setListaCertificados(ArrayList listaCertificados)
   {
     this.listaCertificados = listaCertificados;
   }
 
   public ArrayList getListaCertificados()
   {
     return this.listaCertificados;
   }
 
   public void setListaCreditos(ArrayList listaCreditos)
   {
     this.listaCreditos = listaCreditos;
   }
 
   public ArrayList getListaCreditos()
   {
     return this.listaCreditos;
   }
 
   public void setListaOtrosCentros(ArrayList listaOtrosCentros)
   {
     this.listaOtrosCentros = listaOtrosCentros;
   }
 
   public ArrayList getListaOtrosCentros()
   {
     return this.listaOtrosCentros;
   }
 
   public void setCFase(String cFase)
   {
     this.cFase = cFase;
   }
 
   public String getCFase()
   {
     return this.cFase;
   }
 
   public void setCEstado(String cEstado)
   {
     this.cEstado = cEstado;
   }
 
   public String getCEstado()
   {
     return this.cEstado;
   }
   
   public void setCEstadoHist(String cEstadoHist)
   {
     this.cEstadoHist = cEstadoHist;
   }
 
   public String getCEstadoHist()
   {
     return this.cEstadoHist;
   }
 
   public void setDFase(String dFase)
   {
     this.dFase = dFase;
   }
 
   public String getDFase()
   {
     return this.dFase;
   }
 
   public void setFFechaRegistro(String fFechaRegistro)
   {
     this.fFechaRegistro = fFechaRegistro;
   }
 
   public String getFFechaRegistro()
   {
     return this.fFechaRegistro;
   }
 
   public void setDMensajeInforme(String dMensajeInforme)
   {
     this.dMensajeInforme = dMensajeInforme;
   }
 
   public String getDMensajeInforme()
   {
     return this.dMensajeInforme;
   }
 
   public void setAModificadoPor(String aModificadoPor)
   {
     this.aModificadoPor = aModificadoPor;
   }
 
   public String getAModificadoPor()
   {
     return this.aModificadoPor;
   }
 
   public void setACreadoPor(String aCreadoPor)
   {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 
   public void setBNifNie(boolean BNifNie)
   {
     this.BNifNie = BNifNie;
   }
 
   public boolean getBNifNie()
   {
     return this.BNifNie;
   }
 
   public void setBApellidos(boolean BApellidos)
   {
     this.BApellidos = BApellidos;
   }
 
   public boolean getBApellidos()
   {
     return this.BApellidos;
   }
 
   public void setBNombre(boolean BNombre)
   {
     this.BNombre = BNombre;
   }
 
   public boolean getBNombre()
   {
     return this.BNombre;
   }
 
   public void setBCSVSexo(boolean BCSVSexo)
   {
     this.BCSVSexo = BCSVSexo;
   }
 
   public boolean getBCSVSexo()
   {
     return this.BCSVSexo;
   }
 
   public void setBCategoria(boolean BCategoria)
   {
     this.BCategoria = BCategoria;
   }
 
   public boolean getBCategoria()
   {
     return this.BCategoria;
   }
 
   public void setBEspecialidad(boolean BEspecialidad)
   {
     this.BEspecialidad = BEspecialidad;
   }
 
   public boolean getBEspecialidad()
   {
     return this.BEspecialidad;
   }
 
   public void setBSituacionAdm(boolean BSituacionAdm)
   {
     this.BSituacionAdm = BSituacionAdm;
   }
 
   public boolean getBSituacionAdm()
   {
     return this.BSituacionAdm;
   }
 
   public void setBProcedimiento(boolean BProcedimiento)
   {
     this.BProcedimiento = BProcedimiento;
   }
 
   public boolean getBProcedimiento()
   {
     return this.BProcedimiento;
   }
 
   public void setBGerencia(boolean BGerencia)
   {
     this.BGerencia = BGerencia;
   }
 
   public boolean getBGerencia()
   {
     return this.BGerencia;
   }
 
   public void setBEstado(boolean BEstado)
   {
     this.BEstado = BEstado;
   }
 
   public boolean getBEstado()
   {
     return this.BEstado;
   }
 
   public void setBCausas(boolean BCausas)
   {
     this.BCausas = BCausas;
   }
 
   public boolean getBCausas()
   {
     return this.BCausas;
   }
 
   public void setBConvocatoria(boolean BConvocatoria)
   {
     this.BConvocatoria = BConvocatoria;
   }
 
   public boolean getBConvocatoria()
   {
     return this.BConvocatoria;
   }
 
   public void setBFechaSolic(boolean BFechaSolic)
   {
     this.BFechaSolic = BFechaSolic;
   }
 
   public boolean getBFechaSolic()
   {
     return this.BFechaSolic;
   }
 
   public void setBJuridico(boolean BJuridico)
   {
     this.BJuridico = BJuridico;
   }
 
   public boolean getBJuridico()
   {
     return this.BJuridico;
   }
 
   public void setBAnios(boolean BAnios)
   {
     this.BAnios = BAnios;
   }
 
   public boolean getBAnios()
   {
     return this.BAnios;
   }
 
   public void setBCentro(boolean BCentro)
   {
     this.BCentro = BCentro;
   }
 
   public boolean getBCentro()
   {
     return this.BCentro;
   }
 
   public void setCGerenciaFiltro_id(long cGerenciaFiltro_id)
   {
     this.cGerenciaFiltro_id = cGerenciaFiltro_id;
   }
 
   public long getCGerenciaFiltro_id()
   {
     return this.cGerenciaFiltro_id;
   }
 
   public void setDNombreApellidosPresi1(String dNombreApellidosPresi1)
   {
     this.dNombreApellidosPresi1 = dNombreApellidosPresi1;
   }
 
   public String getDNombreApellidosPresi1()
   {
     return this.dNombreApellidosPresi1;
   }
 
   public void setDNombreApellidosPresi2(String dNombreApellidosPresi2)
   {
     this.dNombreApellidosPresi2 = dNombreApellidosPresi2;
   }
 
   public String getDNombreApellidosPresi2()
   {
     return this.dNombreApellidosPresi2;
   }
 
   public void setDNombreApellidosVocal(String dNombreApellidosVocal)
   {
     this.dNombreApellidosVocal = dNombreApellidosVocal;
   }
 
   public String getDNombreApellidosVocal()
   {
     return this.dNombreApellidosVocal;
   }
 
   public void setListadoVocales(ArrayList listadoVocales)
   {
     this.listadoVocales = listadoVocales;
   }
 
   public ArrayList getListadoVocales()
   {
     return this.listadoVocales;
   }
 
   public void setListadoVocalesTitulares(ArrayList listadoVocalesTitulares)
   {
     this.listadoVocalesTitulares = listadoVocalesTitulares;
   }
 
   public ArrayList getListadoVocalesTitulares()
   {
     return this.listadoVocalesTitulares;
   }
 
   public void setListadoVocalesSuplentes(ArrayList listadoVocalesSuplentes)
   {
     this.listadoVocalesSuplentes = listadoVocalesSuplentes;
   }
 
   public ArrayList getListadoVocalesSuplentes()
   {
     return this.listadoVocalesSuplentes;
   }
 
   public void setDNombreApellidosSecre1(String dNombreApellidosSecre1)
   {
     this.dNombreApellidosSecre1 = dNombreApellidosSecre1;
   }
 
   public String getDNombreApellidosSecre1()
   {
     return this.dNombreApellidosSecre1;
   }
 
   public void setDNombreApellidosSecre2(String dNombreApellidosSecre2)
   {
     this.dNombreApellidosSecre2 = dNombreApellidosSecre2;
   }
 
   public String getDNombreApellidosSecre2()
   {
     return this.dNombreApellidosSecre2;
   }
 
   public void setBSexoPresi1(String bSexoPresi1)
   {
     this.bSexoPresi1 = bSexoPresi1;
   }
 
   public String getBSexoPresi1()
   {
     return this.bSexoPresi1;
   }
 
   public void setBSexoPresi2(String bSexoPresi2)
   {
     this.bSexoPresi2 = bSexoPresi2;
   }
 
   public String getBSexoPresi2()
   {
     return this.bSexoPresi2;
   }
 
   public void setBSexoVocal(String bSexoVocal)
   {
     this.bSexoVocal = bSexoVocal;
   }
 
   public String getBSexoVocal()
   {
     return this.bSexoVocal;
   }
 
   public void setBSexoSecre1(String bSexoSecre1)
   {
     this.bSexoSecre1 = bSexoSecre1;
   }
 
   public String getBSexoSecre1()
   {
     return this.bSexoSecre1;
   }
 
   public void setBSexoSecre2(String bSexoSecre2)
   {
     this.bSexoSecre2 = bSexoSecre2;
   }
 
   public String getBSexoSecre2()
   {
     return this.bSexoSecre2;
   }
 
   public void setBEnCalidadPresi1(String bEnCalidadPresi1)
   {
     this.bEnCalidadPresi1 = bEnCalidadPresi1;
   }
 
   public String getBEnCalidadPresi1()
   {
     return this.bEnCalidadPresi1;
   }
 
   public void setBEnCalidadPresi2(String bEnCalidadPresi2)
   {
     this.bEnCalidadPresi2 = bEnCalidadPresi2;
   }
 
   public String getBEnCalidadPresi2()
   {
     return this.bEnCalidadPresi2;
   }
 
   public void setBEnCalidadVocal(String bEnCalidadVocal)
   {
     this.bEnCalidadVocal = bEnCalidadVocal;
   }
 
   public String getBEnCalidadVocal()
   {
     return this.bEnCalidadVocal;
   }
 
   public void setBEnCalidadSecre1(String bEnCalidadSecre1)
   {
     this.bEnCalidadSecre1 = bEnCalidadSecre1;
   }
 
   public String getBEnCalidadSecre1()
   {
     return this.bEnCalidadSecre1;
   }
 
   public void setBEnCalidadSecre2(String bEnCalidadSecre2)
   {
     this.bEnCalidadSecre2 = bEnCalidadSecre2;
   }
 
   public String getBEnCalidadSecre2()
   {
     return this.bEnCalidadSecre2;
   }
 
   public void setDMiembrosAusentes(String dMiembrosAusentes)
   {
     this.dMiembrosAusentes = dMiembrosAusentes;
   }
 
   public String getDMiembrosAusentes()
   {
     return this.dMiembrosAusentes;
   }
 
   public void setDAsuntosTramite(String dAsuntosTramite)
   {
     this.dAsuntosTramite = dAsuntosTramite;
   }
 
   public String getDAsuntosTramite()
   {
     return this.dAsuntosTramite;
   }
 
   public void setDRuegosPreguntas(String dRuegosPreguntas)
   {
     this.dRuegosPreguntas = dRuegosPreguntas;
   }
 
   public String getDRuegosPreguntas()
   {
     return this.dRuegosPreguntas;
   }
 
   public void setFSesion(Date fSesion)
   {
     this.fSesion = fSesion;
   }
 
   public Date getFSesion()
   {
     return this.fSesion;
   }
 
   public void setFSesionImprimible(String fSesionImprimible)
   {
     this.fSesionImprimible = fSesionImprimible;
   }
 
   public String getFSesionImprimible()
   {
     return this.fSesionImprimible;
   }
 
   public void setNHoraInicio(String nHoraInicio)
   {
     this.nHoraInicio = nHoraInicio;
   }
 
   public String getNHoraInicio()
   {
     return this.nHoraInicio;
   }
 
   public void setNMinutosInicio(String nMinutosInicio)
   {
     this.nMinutosInicio = nMinutosInicio;
   }
 
   public String getNMinutosInicio()
   {
     return this.nMinutosInicio;
   }
 
   public void setNHoraFin(String nHoraFin)
   {
     this.nHoraFin = nHoraFin;
   }
 
   public String getNHoraFin()
   {
     return this.nHoraFin;
   }
 
   public void setNMinutosFin(String nMinutosFin)
   {
     this.nMinutosFin = nMinutosFin;
   }
 
   public String getNMinutosFin()
   {
     return this.nMinutosFin;
   }
 
   public void setFInformeEval(Date fInformeEval)
   {
     this.fInformeEval = fInformeEval;
   }
 
   public Date getFInformeEval()
   {
     return this.fInformeEval;
   }
 
   public void setFInformeCE(Date fInformeCE)
   {
     this.fInformeCE = fInformeCE;
   }
 
   public Date getFInformeCE()
   {
     return this.fInformeCE;
   }
 
   public void setBReconocimientoGrado(String bReconocimientoGrado)
   {
     this.bReconocimientoGrado = bReconocimientoGrado;
   }
 
   public String getBReconocimientoGrado()
   {
     return this.bReconocimientoGrado;
   }
 
   public void setDNombreApellidosDtor(String dNombreApellidosDtor)
   {
     this.dNombreApellidosDtor = dNombreApellidosDtor;
   }
 
   public String getDNombreApellidosDtor()
   {
     return this.dNombreApellidosDtor;
   }
 
   public void setBSexoDtor(String bSexoDtor)
   {
     this.bSexoDtor = bSexoDtor;
   }
 
   public String getBSexoDtor()
   {
     return this.bSexoDtor;
   }
 
   public void setDAcreditaCumplimiento(String dAcreditaCumplimiento)
   {
     this.dAcreditaCumplimiento = dAcreditaCumplimiento;
   }
 
   public String getDAcreditaCumplimiento()
   {
     return this.dAcreditaCumplimiento;
   }
 
   public void setDSituacionAdmin(String dSituacionAdmin)
   {
     this.dSituacionAdmin = dSituacionAdmin;
   }
 
   public String getDSituacionAdmin()
   {
     return this.dSituacionAdmin;
   }
 
   public void setFConvocato(Date fConvocato)
   {
     this.fConvocato = fConvocato;
   }
 
   public Date getFConvocato()
   {
     return this.fConvocato;
   }
 
   public void setFConvocatoImprimible(String fConvocatoImprimible)
   {
     this.fConvocatoImprimible = fConvocatoImprimible;
   }
 
   public String getFConvocatoImprimible()
   {
     return this.fConvocatoImprimible;
   }
 
   public void setDModalidad(String dModalidad)
   {
     this.dModalidad = dModalidad;
   }
 
   public String getDModalidad()
   {
     return this.dModalidad;
   }
 
   public void setBModalidad(boolean BModalidad)
   {
     this.BModalidad = BModalidad;
   }
 
   public boolean getBModalidad()
   {
     return this.BModalidad;
   }
 
   public void setDRegimenJuridico(String dRegimenJuridico)
   {
     this.dRegimenJuridico = dRegimenJuridico;
   }
 
   public String getDRegimenJuridico()
   {
     return this.dRegimenJuridico;
   }
 
   public void setBPuesto(boolean BPuesto)
   {
     this.BPuesto = BPuesto;
   }
 
   public boolean getBPuesto()
   {
     return this.BPuesto;
   }
 
   public void setBServicio(boolean BServicio)
   {
     this.BServicio = BServicio;
   }
 
   public boolean getBServicio()
   {
     return this.BServicio;
   }
 
   public void setDRegimenJuridicoOtros(String dRegimenJuridicoOtros)
   {
     this.dRegimenJuridicoOtros = dRegimenJuridicoOtros;
   }
 
   public String getDRegimenJuridicoOtros()
   {
     return this.dRegimenJuridicoOtros;
   }
 
   public void setCEstadoId(long cEstadoId)
   {
     this.cEstadoId = cEstadoId;
   }
 
   public long getCEstadoId()
   {
     return this.cEstadoId;
   }
 
   public void setBEpr(boolean BEpr)
   {
     this.BEpr = BEpr;
   }
 
   public boolean getBEpr()
   {
     return this.BEpr;
   }
 
   public void setCodigoSolicitud(String codigoSolicitud)
   {
     this.codigoSolicitud = codigoSolicitud;
   }
 
   public String getCodigoSolicitud()
   {
     return this.codigoSolicitud;
   }
 
   public void setDModAnterior(String dModAnterior)
   {
     this.dModAnterior = dModAnterior;
   }
 
   public String getDModAnterior()
   {
     return this.dModAnterior;
   }
 
   public void setBGradoI(String bGradoI)
   {
     this.bGradoI = bGradoI;
   }
 
   public String getBGradoI()
   {
     return this.bGradoI;
   }
 
   public void setBGradoII(String bGradoII)
   {
     this.bGradoII = bGradoII;
   }
 
   public String getBGradoII()
   {
     return this.bGradoII;
   }
 
   public void setBGradoIII(String bGradoIII)
   {
     this.bGradoIII = bGradoIII;
   }
 
   public String getBGradoIII()
   {
     return this.bGradoIII;
   }
 
   public void setBGradoIV(String bGradoIV)
   {
     this.bGradoIV = bGradoIV;
   }
 
   public String getBGradoIV()
   {
     return this.bGradoIV;
   }
 
   public void setBModPESFU(String bModPESFU)
   {
     this.bModPESFU = bModPESFU;
   }
 
   public String getBModPESFU()
   {
     return this.bModPESFU;
   }
 
   public void setBModPESFP(String bModPESFP)
   {
     this.bModPESFP = bModPESFP;
   }
 
   public String getBModPESFP()
   {
     return this.bModPESFP;
   }
 
   public void setBModPEGSFU(String bModPEGSFU)
   {
     this.bModPEGSFU = bModPEGSFU;
   }
 
   public String getBModPEGSFU()
   {
     return this.bModPEGSFU;
   }
 
   public void setBModPEGSFP(String bModPEGSFP)
   {
     this.bModPEGSFP = bModPEGSFP;
   }
 
   public String getBModPEGSFP()
   {
     return this.bModPEGSFP;
   }
 
   public void setBModPFSFU(String bModPFSFU)
   {
     this.bModPFSFU = bModPFSFU;
   }
 
   public String getBModPFSFU()
   {
     return this.bModPFSFU;
   }
 
   public void setBModPFSFP(String bModPFSFP)
   {
     this.bModPFSFP = bModPFSFP;
   }
 
   public String getBModPFSFP()
   {
     return this.bModPFSFP;
   }
 
   public void setFRecGrado(Date fRecGrado)
   {
     this.fRecGrado = fRecGrado;
   }
 
   public Date getFRecGrado()
   {
     return this.fRecGrado;
   }
 
   public void setCTipo(String cTipo)
   {
     this.cTipo = cTipo;
   }
 
   public String getCTipo()
   {
     return this.cTipo;
   }
 
   public void setCProcedimientoId(String cProcedimientoId)
   {
     this.cProcedimientoId = cProcedimientoId;
   }
 
   public String getCProcedimientoId()
   {
     return this.cProcedimientoId;
   }
 
   public void setNumSolicitudes(long numSolicitudes)
   {
     this.numSolicitudes = numSolicitudes;
   }
 
   public long getNumSolicitudes()
   {
     return this.numSolicitudes;
   }
 
   public void setCSitAdmonActualId(long cSitAdmonActualId) {
     this.cSitAdmonActualId = cSitAdmonActualId;
   }
 
   public long getCSitAdmonActualId()
   {
     return this.cSitAdmonActualId;
   }
 
   public void setDSitAdmonActual(String dSitAdmonActual) {
     this.dSitAdmonActual = dSitAdmonActual;
   }
 
   public String getDSitAdmonActual()
   {
     return this.dSitAdmonActual;
   }
 
   public void setAOtraSitAdmonActual(String aOtraSitAdmonActual) {
     this.aOtraSitAdmonActual = aOtraSitAdmonActual;
   }
 
   public String getAOtraSitAdmonActual()
   {
     return this.aOtraSitAdmonActual;
   }
 
   public void setCGerenciaActualId(long cGerenciaActualId) {
     this.cGerenciaActualId = cGerenciaActualId;
   }
 
   public long getCGerenciaActualId()
   {
     return this.cGerenciaActualId;
   }
 
   public void setDGerenciaActual(String dGerenciaActual) {
     this.dGerenciaActual = dGerenciaActual;
   }
 
   public String getDGerenciaActual()
   {
     return this.dGerenciaActual;
   }
 
   public void setCCentroTrabActualId(long cCentroTrabActualId) {
     this.cCentroTrabActualId = cCentroTrabActualId;
   }
 
   public long getCCentroTrabActualId()
   {
     return this.cCentroTrabActualId;
   }
 
   public void setDCentroTrabActual(String dCentroTrabActual) {
     this.dCentroTrabActual = dCentroTrabActual;
   }
 
   public String getDCentroTrabActual()
   {
     return this.dCentroTrabActual;
   }
 
   public void setCProvCarreraActualId(String cProvCarreraActualId) {
     this.cProvCarreraActualId = cProvCarreraActualId;
   }
 
   public String getCProvCarreraActualId()
   {
     return this.cProvCarreraActualId;
   }
 
   public void setDProvCarreraActual(String dProvCarreraActual) {
     this.dProvCarreraActual = dProvCarreraActual;
   }
 
   public String getDProvCarreraActual()
   {
     return this.dProvCarreraActual;
   }

/**
 * @return the orden
 */
public String getOrden() {
	return orden;
}

/**
 * @param orden the orden to set
 */
public void setOrden(String orden) {
	this.orden = orden;
}

	@Override
	/**
	 * Ordenacion alfabética
	 */
	public int compareTo(OCAPSolicitudesOT o) {
		if (o.getDApellido1() != null && this.getDApellido1() != null) {
			if (this.getDApellido1().toUpperCase().compareTo(o.getDApellido1().toUpperCase()) == 0) {
				return compararSegundoApellido(o);
			} else {
				return this.getDApellido1().toUpperCase().compareTo(o.getDApellido1().toUpperCase());
			}
		} else {
			if (Utilidades.isNullOrIsEmpty(this.getDApellido1())
					&& Utilidades.notNullAndNotEmpty(o.getDApellido1())) {
				return 1;
			} else if (Utilidades.isNullOrIsEmpty(o.getDApellido1())
					&& Utilidades.notNullAndNotEmpty(this.getDApellido1())) {
				return -1;
			} else {
				return compararSegundoApellido(o);
			}
		}
	}

	private int compararSegundoApellido(OCAPSolicitudesOT o) {
		if (o.getDApellido2() != null && this.getDApellido2() != null) {
			if (this.getDApellido2().toUpperCase().compareTo(o.getDApellido2().toUpperCase()) == 0) {
				return compararNombre(o);
			} else {
				return this.getDApellido2().toUpperCase().compareTo(o.getDApellido2().toUpperCase());
			}
		}else{
			if (Utilidades.isNullOrIsEmpty(this.getDApellido2())
					&& Utilidades.notNullAndNotEmpty(o.getDApellido2())) {
				return 1;
			} else if (Utilidades.isNullOrIsEmpty(o.getDApellido2())
					&& Utilidades.notNullAndNotEmpty(this.getDApellido2())) {
				return -1;
			} else {
				return compararNombre(o);
			}
		}
	}

	private int compararNombre(OCAPSolicitudesOT o) {
		if (o.getDNombre() != null && this.getDNombre() != null) {
			if (this.getDNombre().toUpperCase().compareTo(o.getDNombre().toUpperCase()) == 0) {
				return 1;
			} else {
				return this.getDNombre().toUpperCase().compareTo(o.getDNombre().toUpperCase());
			}
		} else {
			if (Utilidades.isNullOrIsEmpty(this.getDNombre())
					&& Utilidades.notNullAndNotEmpty(o.getDNombre())) {
				return 1;
			} else if (Utilidades.isNullOrIsEmpty(o.getDNombre())
					&& Utilidades.notNullAndNotEmpty(this.getDNombre())) {
				return -1;
			} else {
				return 1;
			}
		}
	}

	/**
	 * @return the fRenuncia
	 */
	public String getFRenuncia() {
		return fRenuncia;
	}

	/**
	 * @param fRenuncia the fRenuncia to set
	 */
	public void setFRenuncia(String fRenuncia) {
		this.fRenuncia = fRenuncia;
	}

	/**
	 * @return the plazoDiasCC
	 */
	public Long getPlazoDiasCC() {
		return plazoDiasCC;
	}

	/**
	 * @param plazoDiasCC the plazoDiasCC to set
	 */
	public void setPlazoDiasCC(Long plazoDiasCC) {
		this.plazoDiasCC = plazoDiasCC;
	}

	public String getDConvocatoriaNombreCorto() {
		return dConvocatoriaNombreCorto;
	}

	public void setDConvocatoriaNombreCorto(String dConvocatoriaNombreCorto) {
		this.dConvocatoriaNombreCorto = dConvocatoriaNombreCorto;
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

	/**
	 * @return the cDniEnmascarado
	 */
	public String getcDniEnmascarado() {
		return cDniEnmascarado;
	}

	/**
	 * @param cDniEnmascarado the cDniEnmascarado to set
	 */
	public void setcDniEnmascarado(String cDniEnmascarado) {
		this.cDniEnmascarado = cDniEnmascarado;
	}

	public String getDiaActual() {
		return diaActual;
	}

	public void setDiaActual(String diaActual) {
		this.diaActual = diaActual;
	}

	public String getMesActual() {
		return mesActual;
	}

	public void setMesActual(String mesActual) {
		this.mesActual = mesActual;
	}

	public String getAnyoActual() {
		return anyoActual;
	}

	public void setAnyoActual(String annoActual) {
		this.anyoActual = annoActual;
	}

	public boolean isEliminarEvaluacion() {
		return eliminarEvaluacion;
	}

	public void setEliminarEvaluacion(boolean eliminarEvaluacion) {
		this.eliminarEvaluacion = eliminarEvaluacion;
	}

	public Date getFechaFinPgp() {
		return fechaFinPgp;
	}

	public void setFechaFinPgp(Date fechaFinPgp) {
		this.fechaFinPgp = fechaFinPgp;
	}


	
}

