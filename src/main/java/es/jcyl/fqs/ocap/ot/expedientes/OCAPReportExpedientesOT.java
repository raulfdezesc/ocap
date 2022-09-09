 package es.jcyl.fqs.ocap.ot.expedientes;
 
 import java.io.Serializable;
 import java.text.DecimalFormat;
 import java.util.Date;

import es.jcyl.framework.JCYLUsuario;
 
 public class OCAPReportExpedientesOT
   implements Serializable
 {
   /**
	 * 
	 */
	private static final long serialVersionUID = 7170862419279850253L;
	
private long cExpedienteId;
   private long cUsuarioId;
   private String dNombre;
   private String dApellidos;
   private String bSexo;
   private String cDNI;
   private String cDNIReal;
   private long cGerenciaId;
   private String dGerencia;
   private String dGerente;
   private String dProvinciaGerencia;
   private long cCentroTrabajoId;
   private String dCentroTrabajo;
   private String aDomicilio;
   private String cProvinciaId;
   private String dProvincia;
   private String dLocalidad;
   private int nCodigopostal;
   private String aCorreoElectronico;
   private long nTelefono1;
   private long nTelefono2;
   private long cGradoId;
   private long cConvocatoriaId;
   private String dGrado;
   private long cModAnteriorId;
   private String dModAnterior;
   private long cRegJuridicoId;
   private String dRegJuridico;
   private long cTipo;
   private long cProcAdministrativoId;
   private String dProcAdministrativo;
   private long cSitAdministrativaId;
   private String dSitAdministrativa;
   private long cCategProfesionalId;
   private String dCategProfesional;
   private long cEspecialidadId;
   private String dEspecialidad;
   private String aServicio;
   private String aPuesto;
   private long nAniosAntiguedad;
   private long nMesesAntiguedad;
   private long nDiasAntiguedad;
   private long cEstatutarioId;
   private long cDefMeritoCurricularId;
   private String dDefMeritoCurricular;
   private String dMensajeDefMeritoCurricular;
   private float nCreditosDefMeritoCurricular;
   private long cMeritoCurricularId;
   private String dMeritoCurricular;
   private String dMeritoCurricularCorto;
   private String cTipoMeritoCurricular;
   private String bAcreditadoMeritoCurricular;
   private long cMerActividadMC;
   private String dMerActividadMC;
   private long cMerModalidadMC;
   private String dMerModalidadMC;
   private long cFactorImpactoMC;
   private String dFactorImpactoMC;
   private long cTipoFirmanteMC;
   private String dTipoFirmanteMC;
   private long cExpedienteMCId;
   private String dExpedienteMC;
   private long cDefMeritoOpcional;
   private String bOpcionalExpMC;
   private String bAcreditadoExpMC;
   private Date fInicio;
   private Date fFinalizacion;
   private String aOrganizador;
   private int fAnnio;
   private int nSesiones;
   private Date fExpedicion;
   private String aLugarExpedicion;
   private int nHoras;
   private int nDias;
   private int nAnnios;
   private String bParticipacion;
   private String aNombreRevista;
   private String aCargo;
   private String aObjetivo;
   private int nMeses;
   private float nCreditosCurso;
   private long nISBN;
   private String dEditorial;
   private String dCapitulo;
   private float nCreditosUsuario;
   private String bInvalidado;
   private float nCreditosCEIS;
   private String bInvalidadoCEIS;
   private String bPdteAclararCEIS;
   private float nCreditosCC;
   private String bInvalidadoCC;
   private String bPdteAclararCC;
   private long[] listaExpedientesBusqueda;
   private String bMCUsuario;
   private long cItinerarioId;
   private String dNombreItin;
   private String cEvidenciaId;
   private String dTipoPrueba;
   private String dCodEpr;
   private float nCreditosMC;
   private String dMotivoNegMC;
   
   private String dOrigenReport;
   private Boolean bVerCeisReport;
   private Boolean bVerCCReport;
   private JCYLUsuario jcylUsuario;
   
   private String dConvocatoria;
   private String dMotivoModif;
   
 
   public long getCExpedienteId()
   {
     return this.cExpedienteId;
   }
 
   public void setCExpedienteId(long cExpedienteId)
   {
     this.cExpedienteId = cExpedienteId;
   }
 
   public long getCUsuarioId()
   {
     return this.cUsuarioId;
   }
 
   public void setCUsuarioId(long cUsuarioId)
   {
     this.cUsuarioId = cUsuarioId;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public String getDApellidos()
   {
     return this.dApellidos;
   }
 
   public void setDApellidos(String dApellidos)
   {
     this.dApellidos = dApellidos;
   }
 
   public String getBSexo()
   {
     return this.bSexo;
   }
 
   public void setBSexo(String bSexo)
   {
     this.bSexo = bSexo;
   }
 
   public String getCDNI()
   {
     return this.cDNI;
   }
 
   public void setCDNI(String cDNI)
   {
     this.cDNI = cDNI;
   }
 
   public String getCDNIReal()
   {
     return this.cDNIReal;
   }
 
   public void setCDNIReal(String cDNIReal)
   {
     this.cDNIReal = cDNIReal;
   }
 
   public long getCGerenciaId()
   {
     return this.cGerenciaId;
   }
 
   public void setCGerenciaId(long cGerenciaId)
   {
     this.cGerenciaId = cGerenciaId;
   }
 
   public String getDGerencia()
   {
     return this.dGerencia;
   }
 
   public void setDGerencia(String dGerencia)
   {
     this.dGerencia = dGerencia;
   }
 
   public String getDProvinciaGerencia()
   {
     return this.dProvinciaGerencia;
   }
 
   public void setDProvinciaGerencia(String dProvinciaGerencia)
   {
     this.dProvinciaGerencia = dProvinciaGerencia;
   }
 
   public long getCCentroTrabajoId()
   {
     return this.cCentroTrabajoId;
   }
 
   public void setCCentroTrabajoId(long cCentroTrabajoId)
   {
     this.cCentroTrabajoId = cCentroTrabajoId;
   }
 
   public String getDCentroTrabajo()
   {
     return this.dCentroTrabajo;
   }
 
   public void setDCentroTrabajo(String dCentroTrabajo)
   {
     this.dCentroTrabajo = dCentroTrabajo;
   }
 
   public String getADomicilio()
   {
     return this.aDomicilio;
   }
 
   public void setADomicilio(String aDomicilio)
   {
     this.aDomicilio = aDomicilio;
   }
 
   public String getCProvinciaId()
   {
     return this.cProvinciaId;
   }
 
   public void setCProvinciaId(String cProvinciaId)
   {
     this.cProvinciaId = cProvinciaId;
   }
 
   public String getDProvincia()
   {
     return this.dProvincia;
   }
 
   public void setDProvincia(String dProvincia)
   {
     this.dProvincia = dProvincia;
   }
 
   public String getDLocalidad()
   {
     return this.dLocalidad;
   }
 
   public void setDLocalidad(String dLocalidad)
   {
     this.dLocalidad = dLocalidad;
   }
 
   public int getNCodigopostal()
   {
     return this.nCodigopostal;
   }
 
   public void setNCodigopostal(int nCodigopostal)
   {
     this.nCodigopostal = nCodigopostal;
   }
 
   public String getACorreoElectronico()
   {
     return this.aCorreoElectronico;
   }
 
   public void setACorreoElectronico(String aCorreoElectronico)
   {
     this.aCorreoElectronico = aCorreoElectronico;
   }
 
   public long getNTelefono1()
   {
     return this.nTelefono1;
   }
 
   public void setNTelefono1(long nTelefono1)
   {
     this.nTelefono1 = nTelefono1;
   }
 
   public long getNTelefono2()
   {
     return this.nTelefono2;
   }
 
   public void setNTelefono2(long nTelefono2)
   {
     this.nTelefono2 = nTelefono2;
   }
 
   public long getCGradoId()
   {
     return this.cGradoId;
   }
 
   public void setCGradoId(long cGradoId)
   {
     this.cGradoId = cGradoId;
   }
 
   public String getDGrado()
   {
     return this.dGrado;
   }
 
   public void setDGrado(String dGrado)
   {
     this.dGrado = dGrado;
   }
 
   public long getCModAnteriorId()
   {
     return this.cModAnteriorId;
   }
 
   public void setCModAnteriorId(long cModAnteriorId)
   {
     this.cModAnteriorId = cModAnteriorId;
   }
 
   public String getDModAnterior()
   {
     return this.dModAnterior;
   }
 
   public void setDModAnterior(String dModAnterior)
   {
     this.dModAnterior = dModAnterior;
   }
 
   public long getCRegJuridicoId()
   {
     return this.cRegJuridicoId;
   }
 
   public void setCRegJuridicoId(long cRegJuridicoId)
   {
     this.cRegJuridicoId = cRegJuridicoId;
   }
 
   public String getDRegJuridico()
   {
     return this.dRegJuridico;
   }
 
   public void setDRegJuridico(String dRegJuridico)
   {
     this.dRegJuridico = dRegJuridico;
   }
 
   public long getCTipo()
   {
     return this.cTipo;
   }
 
   public void setCTipo(long cTipo)
   {
     this.cTipo = cTipo;
   }
 
   public long getCProcAdministrativoId()
   {
     return this.cProcAdministrativoId;
   }
 
   public void setCProcAdministrativoId(long cProcAdministrativoId)
   {
     this.cProcAdministrativoId = cProcAdministrativoId;
   }
 
   public String getDProcAdministrativo()
   {
     return this.dProcAdministrativo;
   }
 
   public void setDProcAdministrativo(String dProcAdministrativo)
   {
     this.dProcAdministrativo = dProcAdministrativo;
   }
 
   public long getCSitAdministrativaId()
   {
     return this.cSitAdministrativaId;
   }
 
   public void setCSitAdministrativaId(long cSitAdministrativaId)
   {
     this.cSitAdministrativaId = cSitAdministrativaId;
   }
 
   public String getDSitAdministrativa()
   {
     return this.dSitAdministrativa;
   }
 
   public void setDSitAdministrativa(String dSitAdministrativa)
   {
     this.dSitAdministrativa = dSitAdministrativa;
   }
 
   public long getCCategProfesionalId()
   {
     return this.cCategProfesionalId;
   }
 
   public void setCCategProfesionalId(long cCategProfesionalId)
   {
     this.cCategProfesionalId = cCategProfesionalId;
   }
 
   public String getDCategProfesional()
   {
     return this.dCategProfesional;
   }
 
   public void setDCategProfesional(String dCategProfesional)
   {
     this.dCategProfesional = dCategProfesional;
   }
 
   public long getCEspecialidadId()
   {
     return this.cEspecialidadId;
   }
 
   public void setCEspecialidadId(long cEspecialidadId)
   {
     this.cEspecialidadId = cEspecialidadId;
   }
 
   public String getDEspecialidad()
   {
     return this.dEspecialidad;
   }
 
   public void setDEspecialidad(String dEspecialidad)
   {
     this.dEspecialidad = dEspecialidad;
   }
 
   public String getAServicio()
   {
     return this.aServicio;
   }
 
   public void setAServicio(String aServicio)
   {
     this.aServicio = aServicio;
   }
 
   public String getAPuesto()
   {
     return this.aPuesto;
   }
 
   public void setAPuesto(String aPuesto)
   {
     this.aPuesto = aPuesto;
   }
 
   public long getNAniosAntiguedad()
   {
     return this.nAniosAntiguedad;
   }
 
   public void setNAniosAntiguedad(long nAniosAntiguedad)
   {
     this.nAniosAntiguedad = nAniosAntiguedad;
   }
 
   public long getNMesesAntiguedad()
   {
     return this.nMesesAntiguedad;
   }
 
   public void setNMesesAntiguedad(long nMesesAntiguedad)
   {
     this.nMesesAntiguedad = nMesesAntiguedad;
   }
 
   public long getNDiasAntiguedad()
   {
     return this.nDiasAntiguedad;
   }
 
   public void setNDiasAntiguedad(long nDiasAntiguedad)
   {
     this.nDiasAntiguedad = nDiasAntiguedad;
   }
 
   public long getCEstatutarioId()
   {
     return this.cEstatutarioId;
   }
 
   public void setCEstatutarioId(long cEstatutarioId)
   {
     this.cEstatutarioId = cEstatutarioId;
   }
 
   public long getCDefMeritoCurricularId()
   {
     return this.cDefMeritoCurricularId;
   }
 
   public void setCDefMeritoCurricularId(long cDefMeritoCurricularId)
   {
     this.cDefMeritoCurricularId = cDefMeritoCurricularId;
   }
 
   public String getDDefMeritoCurricular()
   {
     return this.dDefMeritoCurricular;
   }
 
   public void setDDefMeritoCurricular(String dDefMeritoCurricular)
   {
     this.dDefMeritoCurricular = dDefMeritoCurricular;
   }
 
   public String getDMensajeDefMeritoCurricular()
   {
     return this.dMensajeDefMeritoCurricular;
   }
 
   public void setDMensajeDefMeritoCurricular(String dMensajeDefMeritoCurricular)
   {
     this.dMensajeDefMeritoCurricular = dMensajeDefMeritoCurricular;
   }
 
   public float getNCreditosDefMeritoCurricular()
   {
     return this.nCreditosDefMeritoCurricular;
   }
 
   public void setNCreditosDefMeritoCurricular(float nCreditosDefMeritoCurricular)
   {
     this.nCreditosDefMeritoCurricular = nCreditosDefMeritoCurricular;
   }
 
   public long getCMeritoCurricularId()
   {
     return this.cMeritoCurricularId;
   }
 
   public void setCMeritoCurricularId(long cMeritoCurricularId)
   {
     this.cMeritoCurricularId = cMeritoCurricularId;
   }
 
   public String getDMeritoCurricular()
   {
     return this.dMeritoCurricular;
   }
 
   public void setDMeritoCurricular(String dMeritoCurricular)
   {
     this.dMeritoCurricular = dMeritoCurricular;
   }
 
   public String getDMeritoCurricularCorto()
   {
     return this.dMeritoCurricularCorto;
   }
 
   public void setDMeritoCurricularCorto(String dMeritoCurricularCorto)
   {
     this.dMeritoCurricularCorto = dMeritoCurricularCorto;
   }
 
   public String getCTipoMeritoCurricular()
   {
     return this.cTipoMeritoCurricular;
   }
 
   public void setCTipoMeritoCurricular(String cTipoMeritoCurricular)
   {
     this.cTipoMeritoCurricular = cTipoMeritoCurricular;
   }
 
   public String getBAcreditadoMeritoCurricular()
   {
     return this.bAcreditadoMeritoCurricular;
   }
 
   public void setBAcreditadoMeritoCurricular(String bAcreditadoMeritoCurricular)
   {
     this.bAcreditadoMeritoCurricular = bAcreditadoMeritoCurricular;
   }
 
   public long getCMerActividadMC()
   {
     return this.cMerActividadMC;
   }
 
   public void setCMerActividadMC(long cMerActividadMC)
   {
     this.cMerActividadMC = cMerActividadMC;
   }
 
   public String getDMerActividadMC()
   {
     return this.dMerActividadMC;
   }
 
   public void setDMerActividadMC(String dMerActividadMC)
   {
     this.dMerActividadMC = dMerActividadMC;
   }
 
   public long getCMerModalidadMC()
   {
     return this.cMerModalidadMC;
   }
 
   public void setCMerModalidadMC(long cMerModalidadMC)
   {
     this.cMerModalidadMC = cMerModalidadMC;
   }
 
   public String getDMerModalidadMC()
   {
     return this.dMerModalidadMC;
   }
 
   public void setDMerModalidadMC(String dMerModalidadMC)
   {
     this.dMerModalidadMC = dMerModalidadMC;
   }
 
   public long getCFactorImpactoMC()
   {
     return this.cFactorImpactoMC;
   }
 
   public void setCFactorImpactoMC(long cFactorImpactoMC)
   {
     this.cFactorImpactoMC = cFactorImpactoMC;
   }
 
   public String getDFactorImpactoMC()
   {
     return this.dFactorImpactoMC;
   }
 
   public void setDFactorImpactoMC(String dFactorImpactoMC)
   {
     this.dFactorImpactoMC = dFactorImpactoMC;
   }
 
   public long getCTipoFirmanteMC()
   {
     return this.cTipoFirmanteMC;
   }
 
   public void setCTipoFirmanteMC(long cTipoFirmanteMC)
   {
     this.cTipoFirmanteMC = cTipoFirmanteMC;
   }
 
   public String getDTipoFirmanteMC()
   {
     return this.dTipoFirmanteMC;
   }
 
   public void setDTipoFirmanteMC(String dTipoFirmanteMC)
   {
     this.dTipoFirmanteMC = dTipoFirmanteMC;
   }
 
   public long getCExpedienteMCId()
   {
     return this.cExpedienteMCId;
   }
 
   public void setCExpedienteMCId(long cExpedienteMCId)
   {
     this.cExpedienteMCId = cExpedienteMCId;
   }
 
   public String getDExpedienteMC()
   {
     return this.dExpedienteMC;
   }
 
   public void setDExpedienteMC(String dExpedienteMC)
   {
     this.dExpedienteMC = dExpedienteMC;
   }
 
   public long getCDefMeritoOpcional()
   {
     return this.cDefMeritoOpcional;
   }
 
   public void setCDefMeritoOpcional(long cDefMeritoOpcional)
   {
     this.cDefMeritoOpcional = cDefMeritoOpcional;
   }
 
   public String getBOpcionalExpMC()
   {
     return this.bOpcionalExpMC;
   }
 
   public void setBOpcionalExpMC(String bOpcionalExpMC)
   {
     this.bOpcionalExpMC = bOpcionalExpMC;
   }
 
   public String getBAcreditadoExpMC()
   {
     return this.bAcreditadoExpMC;
   }
 
   public void setBAcreditadoExpMC(String bAcreditadoExpMC)
   {
     this.bAcreditadoExpMC = bAcreditadoExpMC;
   }
 
   public Date getFInicio()
   {
     return this.fInicio;
   }
 
   public void setFInicio(Date fInicio)
   {
     this.fInicio = fInicio;
   }
 
   public Date getFFinalizacion()
   {
     return this.fFinalizacion;
   }
 
   public void setFFinalizacion(Date fFinalizacion)
   {
     this.fFinalizacion = fFinalizacion;
   }
 
   public String getAOrganizador()
   {
     return this.aOrganizador;
   }
 
   public void setAOrganizador(String aOrganizador)
   {
     this.aOrganizador = aOrganizador;
   }
 
   public int getFAnnio()
   {
     return this.fAnnio;
   }
 
   public void setFAnnio(int fAnnio)
   {
     this.fAnnio = fAnnio;
   }
 
   public int getNSesiones()
   {
     return this.nSesiones;
   }
 
   public void setNSesiones(int nSesiones)
   {
     this.nSesiones = nSesiones;
   }
 
   public Date getFExpedicion()
   {
     return this.fExpedicion;
   }
 
   public void setFExpedicion(Date fExpedicion)
   {
     this.fExpedicion = fExpedicion;
   }
 
   public String getALugarExpedicion()
   {
     return this.aLugarExpedicion;
   }
 
   public void setALugarExpedicion(String aLugarExpedicion)
   {
     this.aLugarExpedicion = aLugarExpedicion;
   }
 
   public int getNHoras()
   {
     return this.nHoras;
   }
 
   public void setNHoras(int nHoras)
   {
     this.nHoras = nHoras;
   }
 
   public int getNDias()
   {
     return this.nDias;
   }
 
   public void setNDias(int nDias)
   {
     this.nDias = nDias;
   }
 
   public int getNAnnios()
   {
     return this.nAnnios;
   }
 
   public void setNAnnios(int nAnnios)
   {
     this.nAnnios = nAnnios;
   }
 
   public String getBParticipacion()
   {
     return this.bParticipacion;
   }
 
   public void setBParticipacion(String bParticipacion)
   {
     this.bParticipacion = bParticipacion;
   }
 
   public String getANombreRevista()
   {
     return this.aNombreRevista;
   }
 
   public void setANombreRevista(String aNombreRevista)
   {
     this.aNombreRevista = aNombreRevista;
   }
 
   public String getACargo()
   {
     return this.aCargo;
   }
 
   public void setACargo(String aCargo)
   {
     this.aCargo = aCargo;
   }
 
   public String getAObjetivo()
   {
     return this.aObjetivo;
   }
 
   public void setAObjetivo(String aObjetivo)
   {
     this.aObjetivo = aObjetivo;
   }
 
   public int getNMeses()
   {
     return this.nMeses;
   }
 
   public void setNMeses(int nMeses)
   {
     this.nMeses = nMeses;
   }
 
   public float getNCreditosCurso()
   {
     return this.nCreditosCurso;
   }
 
   public void setNCreditosCurso(float nCreditosCurso)
   {
     this.nCreditosCurso = nCreditosCurso;
   }
 
   public long getNISBN()
   {
     return this.nISBN;
   }
 
   public void setNISBN(long nISBN)
   {
     this.nISBN = nISBN;
   }
 
   public String getDEditorial()
   {
     return this.dEditorial;
   }
 
   public void setDEditorial(String dEditorial)
   {
     this.dEditorial = dEditorial;
   }
 
   public String getDCapitulo()
   {
     return this.dCapitulo;
   }
 
   public void setDCapitulo(String dCapitulo)
   {
     this.dCapitulo = dCapitulo;
   }
 
   public float getNCreditosUsuario()
   {
     return this.nCreditosUsuario;
   }
 
   public void setNCreditosUsuario(float nCreditosUsuario)
   {
     this.nCreditosUsuario = nCreditosUsuario;
   }
 
   public String getBInvalidado()
   {
     return this.bInvalidado;
   }
 
   public void setBInvalidado(String bInvalidado)
   {
     this.bInvalidado = bInvalidado;
   }
 
   public float getNCreditosCEIS()
   {
     return this.nCreditosCEIS;
   }
 
   public void setNCreditosCEIS(float nCreditosCEIS)
   {
     this.nCreditosCEIS = nCreditosCEIS;
   }
 
   public String getBInvalidadoCEIS()
   {
     return this.bInvalidadoCEIS;
   }
 
   public void setBInvalidadoCEIS(String bInvalidadoCEIS)
   {
     this.bInvalidadoCEIS = bInvalidadoCEIS;
   }
 
   public String getBPdteAclararCEIS()
   {
     return this.bPdteAclararCEIS;
   }
 
   public void setBPdteAclararCEIS(String bPdteAclararCEIS)
   {
     this.bPdteAclararCEIS = bPdteAclararCEIS;
   }
 
   public float getNCreditosCC()
   {
     return this.nCreditosCC;
   }
 
   public void setNCreditosCC(float nCreditosCC)
   {
     this.nCreditosCC = nCreditosCC;
   }
 
   public String getBInvalidadoCC()
   {
     return this.bInvalidadoCC;
   }
 
   public void setBInvalidadoCC(String bInvalidadoCC)
   {
     this.bInvalidadoCC = bInvalidadoCC;
   }
 
   public String getBPdteAclararCC()
   {
     return this.bPdteAclararCC;
   }
 
   public void setBPdteAclararCC(String bPdteAclararCC)
   {
     this.bPdteAclararCC = bPdteAclararCC;
   }
 
   public long[] getListaExpedientesBusqueda()
   {
     return this.listaExpedientesBusqueda;
   }
 
   public void setListaExpedientesBusqueda(long[] listaExpedientesBusqueda)
   {
     this.listaExpedientesBusqueda = listaExpedientesBusqueda;
   }
 
   public String getAntiguedad()
   {
     String cadena = "";
     boolean paso = false;
 
     if (this.nAniosAntiguedad != 0L) {
       cadena = cadena + "" + this.nAniosAntiguedad + " Años";
       paso = true;
     }
     if (this.nMesesAntiguedad != 0L) {
       if (paso)
         cadena = cadena + " / ";
       cadena = cadena + "" + this.nMesesAntiguedad + " Meses";
       paso = true;
     }
 
     if (this.nDiasAntiguedad != 0L) {
       if (paso)
         cadena = cadena + " / ";
       cadena = cadena + "" + this.nDiasAntiguedad + " Días";
     }
 
     return !cadena.equals("") ? cadena : null;
   }
 
   public String getACodigopostal()
   {
     String cadena = null;
     try {
       DecimalFormat formato = new DecimalFormat("00000");
       if (this.nCodigopostal != 0)
         cadena = formato.format(this.nCodigopostal);
     }
     catch (Exception e) {
     }
     return cadena;
   }
 
   public String getADireccionCompleta()
   {
     StringBuffer cadena = new StringBuffer();
     try
     {
       boolean siEntro = false;
 
       if (this.dApellidos != null) {
         cadena.append(this.dApellidos);
         siEntro = true;
       }
       if (this.dNombre != null) {
         cadena.append(", ").append(this.dNombre);
         siEntro = true;
       }
       if (siEntro)
         cadena.append("\n");
       siEntro = false;
 
       if (this.aDomicilio != null) {
         cadena.append(this.aDomicilio);
         siEntro = true;
       }
       if (siEntro)
         cadena.append("\n");
       siEntro = false;
 
       if (this.nCodigopostal != 0) {
         cadena.append(getACodigopostal());
       }
       if (this.dProvincia != null) {
         cadena.append(" ").append(this.dProvincia);
         siEntro = true;
       }
       if (siEntro) {
         cadena.append("\n");
       }
       if (this.dLocalidad != null) {
         cadena.append(this.dLocalidad);
       }
     }
     catch (Exception e)
     {
     }
 
     return cadena.length() != 0 ? cadena.toString() : null;
   }
 
   public void setDGerente(String dGerente)
   {
     this.dGerente = dGerente;
   }
 
   public String getDGerente()
   {
     return this.dGerente;
   }
 
   public void set_dGerente(String dGerente)
   {
     this.dGerente = dGerente;
   }
 
   public String get_dGerente()
   {
     return this.dGerente;
   }
 
   public void setCConvocatoriaId(long cConvocatoriaId)
   {
     this.cConvocatoriaId = cConvocatoriaId;
   }
 
   public long getCConvocatoriaId()
   {
     return this.cConvocatoriaId;
   }
 
   public void setBMCUsuario(String bMCUsuario)
   {
     this.bMCUsuario = bMCUsuario;
   }
 
   public String getBMCUsuario()
   {
     return this.bMCUsuario;
   }
 
   public void set_cConvocatoriaId(long cConvocatoriaId)
   {
     this.cConvocatoriaId = cConvocatoriaId;
   }
 
   public long get_cConvocatoriaId()
   {
     return this.cConvocatoriaId;
   }
 
   public void set_bMCUsuario(String bMCUsuario)
   {
     this.bMCUsuario = bMCUsuario;
   }
 
   public String get_bMCUsuario()
   {
     return this.bMCUsuario;
   }
 
   public void setCItinerarioId(long cItinerarioId)
   {
     this.cItinerarioId = cItinerarioId;
   }
 
   public long getCItinerarioId()
   {
     return this.cItinerarioId;
   }
 
   public void setDNombreItin(String dNombreItin)
   {
     this.dNombreItin = dNombreItin;
   }
 
   public String getDNombreItin()
   {
     return this.dNombreItin;
   }
 
   public void setCEvidenciaId(String cEvidenciaId)
   {
     this.cEvidenciaId = cEvidenciaId;
   }
 
   public String getCEvidenciaId()
   {
     return this.cEvidenciaId;
   }
 
   public void setDTipoPrueba(String dTipoPrueba)
   {
     this.dTipoPrueba = dTipoPrueba;
   }
 
   public String getDTipoPrueba()
   {
     return this.dTipoPrueba;
   }
 
   public void setDCodEpr(String dCodEpr)
   {
     this.dCodEpr = dCodEpr;
   }
 
   public String getDCodEpr()
   {
     return this.dCodEpr;
   }
 
   public void setNCreditosMC(float nCreditosMC) {
     this.nCreditosMC = nCreditosMC;
   }
 
   public float getNCreditosMC()
   {
     return this.nCreditosMC;
   }
 
   public void setDMotivoNegMC(String dMotivoNegMC) {
     this.dMotivoNegMC = dMotivoNegMC;
   }
 
   public String getDMotivoNegMC()
   {
     return this.dMotivoNegMC;
   }



public String getDOrigenReport() {
	return dOrigenReport;
}

public void setDOrigenReport(String dOrigenReport) {
	this.dOrigenReport = dOrigenReport;
}

public JCYLUsuario getJcylUsuario() {
	return jcylUsuario;
}

public void setJcylUsuario(JCYLUsuario jcylUsuario) {
	this.jcylUsuario = jcylUsuario;
}

public Boolean getBVerCeisReport() {
	return bVerCeisReport;
}

public void setBVerCeisReport(Boolean bVerCeisReport) {
	this.bVerCeisReport = bVerCeisReport;
}

public Boolean getBVerCCReport() {
	return bVerCCReport;
}

public void setBVerCCReport(Boolean bVerCCReport) {
	this.bVerCCReport = bVerCCReport;
}


public String getDConvocatoria()
{
  return this.dConvocatoria;
}

public void setDConvocatoria(String dConvocatoria)
{
  this.dConvocatoria = dConvocatoria;
}

public String getDMotivoModif() {
	return dMotivoModif;
}

public void setDMotivoModif(String dMotivoModif) {
	this.dMotivoModif = dMotivoModif;
}




 }

