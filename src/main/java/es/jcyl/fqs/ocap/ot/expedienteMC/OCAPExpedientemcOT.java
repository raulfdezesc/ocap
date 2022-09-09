 package es.jcyl.fqs.ocap.ot.expedienteMC;
 
 import java.io.Serializable;
 
 public class OCAPExpedientemcOT
   implements Serializable
 {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1795258216805722388L;
protected Integer cMeritoId;
   protected String dTipoMerito;
   protected String cDefMeritoId;
   protected Integer cEstatutId;
   protected Integer cActividadId;
   protected String fModificacion;
   protected String bBorrado;
   protected Integer nSesiones;
   protected Integer cModalidadId;
   protected Integer nHoras;
   protected Integer fAnnio;
   protected Integer fAnnioFin;
   protected String fFinalizacion;
   protected String bParticipacion;
   protected String aOrganizador;
   protected String dTitulo;
   protected String dTituloCodificado;
private String nISBN;
   private String dEditorial;
   protected Long cExpId;
   protected String fInicio;
   protected String aLugarExpedicion;
   protected Integer nDias;
   private String nCreditosCurso;
   protected String fExpedicion;
   protected Long cExpmcId;
   protected String fCreacion;
   protected String aNombreRevista;
   protected Float nCreditos;
   protected Float nCredCeis;
   protected Float nCredCc;
   protected Float nCreditosTramo;
   protected String bOpcional;
   protected String cTipoMerito;
   protected Integer nAnnios;
   protected Integer nMeses;
   protected String aCargo;
   protected String aObjetivo;
   protected Integer cFactorId;
   protected Integer cTipoId;
   protected String aCreadoPor;
   protected String bInvalidado;
   protected String bInvalidadoCeis;
   protected String bInvalidadoCc;
   protected String bPdteAclararCeis;
   protected String bPdteAclararCc;
   protected String bPdteAclarar;
   protected String bAcreditado;
   protected String dMotivosCeis;
   protected String dMotivosCc;
 
   public Integer getCMeritoId()
   {
     return this.cMeritoId;
   }
 
   public void setCMeritoId(Integer cMeritoId)
   {
     this.cMeritoId = cMeritoId;
   }
 
   public Integer getCActividadId()
   {
     return this.cActividadId;
   }
 
   public void setCActividadId(Integer cActividadId)
   {
     this.cActividadId = cActividadId;
   }
 
   public String getFModificacion()
   {
     return this.fModificacion;
   }
 
   public void setFModificacion(String fModificacion)
   {
     this.fModificacion = fModificacion;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public Integer getNSesiones()
   {
     return this.nSesiones;
   }
 
   public void setNSesiones(Integer nSesiones)
   {
     this.nSesiones = nSesiones;
   }
 
   public Integer getCModalidadId()
   {
     return this.cModalidadId;
   }
 
   public void setCModalidadId(Integer cModalidadId)
   {
     this.cModalidadId = cModalidadId;
   }
 
   public Integer getNHoras()
   {
     return this.nHoras;
   }
 
   public void setNHoras(Integer nHoras)
   {
     this.nHoras = nHoras;
   }
 
   public Integer getFAnnio()
   {
     return this.fAnnio;
   }
 
   public void setFAnnio(Integer fAnnio)
   {
     this.fAnnio = fAnnio;
   }
 
   public String getFFinalizacion()
   {
     return this.fFinalizacion;
   }
 
   public void setFFinalizacion(String fFinalizacion)
   {
     this.fFinalizacion = fFinalizacion;
   }
 
   public String getBParticipacion()
   {
     return this.bParticipacion;
   }
 
   public void setBParticipacion(String bParticipacion)
   {
     this.bParticipacion = bParticipacion;
   }
 
   public String getDTitulo()
   {
     return this.dTitulo;
   }
 
   public void setDTitulo(String dTitulo)
   {
     this.dTitulo = dTitulo;
   }
 
   public Long getCExpId()
   {
     return this.cExpId;
   }
 
   public void setCExpId(Long cExpId)
   {
     this.cExpId = cExpId;
   }
 
   public String getFInicio()
   {
     return this.fInicio;
   }
 
   public void setFInicio(String fInicio)
   {
     this.fInicio = fInicio;
   }
 
   public Integer getNDias()
   {
     return this.nDias;
   }
 
   public void setNDias(Integer nDias)
   {
     this.nDias = nDias;
   }
 
   public String getFExpedicion()
   {
     return this.fExpedicion;
   }
 
   public void setFExpedicion(String fExpedicion)
   {
     this.fExpedicion = fExpedicion;
   }
 
   public Long getCExpmcId()
   {
     return this.cExpmcId;
   }
 
   public void setCExpmcId(Long cExpmcId)
   {
     this.cExpmcId = cExpmcId;
   }
 
   public String getFCreacion()
   {
     return this.fCreacion;
   }
 
   public void setFCreacion(String fCreacion)
   {
     this.fCreacion = fCreacion;
   }
 
   public void setDTipoMerito(String dTipoMerito)
   {
     this.dTipoMerito = dTipoMerito;
   }
 
   public String getDTipoMerito()
   {
     return this.dTipoMerito;
   }
 
   public void setCDefMeritoId(String cDefMeritoId)
   {
     this.cDefMeritoId = cDefMeritoId;
   }
 
   public String getCDefMeritoId()
   {
     return this.cDefMeritoId;
   }
 
   public void setCEstatutId(Integer cEstatutId)
   {
     this.cEstatutId = cEstatutId;
   }
 
   public Integer getCEstatutId()
   {
     return this.cEstatutId;
   }
 
   public void setNCreditos(Float nCreditos) {
     this.nCreditos = nCreditos;
   }
 
   public Float getNCreditos()
   {
     return this.nCreditos;
   }
 
   public void setCTipoMerito(String cTipoMerito) {
     this.cTipoMerito = cTipoMerito;
   }
 
   public String getCTipoMerito()
   {
     return this.cTipoMerito;
   }
 
   public void setNAnnios(Integer nAnnios)
   {
     this.nAnnios = nAnnios;
   }
 
   public Integer getNAnnios()
   {
     return this.nAnnios;
   }
 
   public void setACargo(String aCargo)
   {
     this.aCargo = aCargo;
   }
 
   public String getACargo()
   {
     return this.aCargo;
   }
 
   public void setAObjetivo(String aObjetivo)
   {
     this.aObjetivo = aObjetivo;
   }
 
   public String getAObjetivo()
   {
     return this.aObjetivo;
   }
 
   public void setCFactorId(Integer cFactorId)
   {
     this.cFactorId = cFactorId;
   }
 
   public Integer getCFactorId()
   {
     return this.cFactorId;
   }
 
   public void setCTipoId(Integer cTipoId)
   {
     this.cTipoId = cTipoId;
   }
 
   public Integer getCTipoId()
   {
     return this.cTipoId;
   }
 
   public void setNMeses(Integer nMeses)
   {
     this.nMeses = nMeses;
   }
 
   public Integer getNMeses()
   {
     return this.nMeses;
   }
 
   public void setAOrganizador(String aOrganizador)
   {
     this.aOrganizador = aOrganizador;
   }
 
   public String getAOrganizador()
   {
     return this.aOrganizador;
   }
 
   public void setALugarExpedicion(String aLugarExpedicion)
   {
     this.aLugarExpedicion = aLugarExpedicion;
   }
 
   public String getALugarExpedicion()
   {
     return this.aLugarExpedicion;
   }
 
   public void setANombreRevista(String aNombreRevista)
   {
     this.aNombreRevista = aNombreRevista;
   }
 
   public String getANombreRevista()
   {
     return this.aNombreRevista;
   }
 
   public void setBOpcional(String bOpcional)
   {
     this.bOpcional = bOpcional;
   }
 
   public String getBOpcional()
   {
     return this.bOpcional;
   }
 
   public void setNCreditosCurso(String nCreditosCurso)
   {
     this.nCreditosCurso = nCreditosCurso;
   }
 
   public String getNCreditosCurso()
   {
     return this.nCreditosCurso;
   }
 
   public void setNISBN(String nISBN)
   {
     this.nISBN = nISBN;
   }
 
   public String getNISBN()
   {
     return this.nISBN;
   }
 
   public void setDEditorial(String dEditorial)
   {
     this.dEditorial = dEditorial;
   }
 
   public String getDEditorial()
   {
     return this.dEditorial;
   }
 
   public void setACreadoPor(String aCreadoPor)
   {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 
   public void setBInvalidado(String bInvalidado)
   {
     this.bInvalidado = bInvalidado;
   }
 
   public String getBInvalidado()
   {
     return this.bInvalidado;
   }
 
   public void setBPdteAclararCeis(String bPdteAclararCeis)
   {
     this.bPdteAclararCeis = bPdteAclararCeis;
   }
 
   public String getBPdteAclararCeis()
   {
     return this.bPdteAclararCeis;
   }
 
   public void setFAnnioFin(Integer fAnnioFin)
   {
     this.fAnnioFin = fAnnioFin;
   }
 
   public Integer getFAnnioFin()
   {
     return this.fAnnioFin;
   }
 
   public void setNCreditosTramo(Float nCreditosTramo)
   {
     this.nCreditosTramo = nCreditosTramo;
   }
 
   public Float getNCreditosTramo()
   {
     return this.nCreditosTramo;
   }
 
   public void setBAcreditado(String bAcreditado)
   {
     this.bAcreditado = bAcreditado;
   }
 
   public String getBAcreditado()
   {
     return this.bAcreditado;
   }
 
   public void setNCredCeis(Float nCredCeis)
   {
     this.nCredCeis = nCredCeis;
   }
 
   public Float getNCredCeis()
   {
     return this.nCredCeis;
   }
 
   public void setNCredCc(Float nCredCc)
   {
     this.nCredCc = nCredCc;
   }
 
   public Float getNCredCc()
   {
     return this.nCredCc;
   }
 
   public void setBPdteAclararCc(String bPdteAclararCc)
   {
     this.bPdteAclararCc = bPdteAclararCc;
   }
 
   public String getBPdteAclararCc()
   {
     return this.bPdteAclararCc;
   }
 
   public void setBInvalidadoCc(String bInvalidadoCc)
   {
     this.bInvalidadoCc = bInvalidadoCc;
   }
 
   public String getBInvalidadoCc()
   {
     return this.bInvalidadoCc;
   }
 
   public void setBInvalidadoCeis(String bInvalidadoCeis)
   {
     this.bInvalidadoCeis = bInvalidadoCeis;
   }
 
   public String getBInvalidadoCeis()
   {
     return this.bInvalidadoCeis;
   }
 
   public void setBPdteAclarar(String bPdteAclarar)
   {
     this.bPdteAclarar = bPdteAclarar;
   }
 
   public String getBPdteAclarar()
   {
     return this.bPdteAclarar;
   }
   
   public String getDTituloCodificado() {
	return dTituloCodificado;
}

public void setDTituloCodificado(String dTituloCodificado) {
	this.dTituloCodificado = dTituloCodificado;
}

public String getDMotivosCeis() {
	return dMotivosCeis;
}

public void setDMotivosCeis(String dMotivosCeis) {
	this.dMotivosCeis = dMotivosCeis;
}

public String getDMotivosCc() {
	return dMotivosCc;
}

public void setDMotivosCc(String dMotivosCc) {
	this.dMotivosCc = dMotivosCc;
}




 }

