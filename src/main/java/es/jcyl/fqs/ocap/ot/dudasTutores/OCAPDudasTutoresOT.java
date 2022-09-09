 package es.jcyl.fqs.ocap.ot.dudasTutores;
 
 import java.io.Serializable;
 
 public class OCAPDudasTutoresOT
   implements Serializable
 {
   protected long cTutorId;
   protected String dNombreTutor;
   protected String dApellidosTutor;
   protected String dCorreoElectronicoTutor;
   protected String bActivado;
   protected String cTipoTutor;
   protected String cTipoDuda;
   protected long nDudasRecibidas;
   protected long nDudasContestadas;
   protected long nDudasPendientes;
   protected long nControl;
   protected String cDni;
   protected long cDudaId;
   protected long cExpId;
   protected String codigoEPR;
   protected String dCorreoElectronico;
   protected String dDuda;
   protected String dRespuesta;
   protected String bContestado;
   protected String bLeido;
   protected String fRecepcion;
   protected String fEnvioContestacion;
   protected String bCambio;
   protected long nTiempoRespuesta;
   protected String tiempoRespuesta;
   protected String fInicio;
   protected String fFin;
   protected String fInicioRespuesta;
   protected String fFinRespuesta;
   protected String bProcedeCambio;
   protected long cTemaId;
   protected String dTema;
   protected long cGerenciaId;
   protected String dGerencia;
   protected long cProfesionalId;
   protected String dProfesional;
   protected long cEspecialidadId;
   protected String dEspecialidad;
   protected String aCreadoPor;
 
   public void setCTutorId(long cTutorId)
   {
     this.cTutorId = cTutorId;
   }
 
   public long getCTutorId()
   {
     return this.cTutorId;
   }
 
   public void setDNombreTutor(String dNombreTutor)
   {
     this.dNombreTutor = dNombreTutor;
   }
 
   public String getDNombreTutor()
   {
     return this.dNombreTutor;
   }
 
   public void setDApellidosTutor(String dApellidosTutor)
   {
     this.dApellidosTutor = dApellidosTutor;
   }
 
   public String getDApellidosTutor()
   {
     return this.dApellidosTutor;
   }
 
   public void setDCorreoElectronicoTutor(String dCorreoElectronicoTutor)
   {
     this.dCorreoElectronicoTutor = dCorreoElectronicoTutor;
   }
 
   public String getDCorreoElectronicoTutor()
   {
     return this.dCorreoElectronicoTutor;
   }
 
   public void setBActivado(String bActivado)
   {
     this.bActivado = bActivado;
   }
 
   public String getBActivado()
   {
     return this.bActivado;
   }
 
   public void setCTipoTutor(String cTipoTutor)
   {
     this.cTipoTutor = cTipoTutor;
   }
 
   public String getCTipoTutor()
   {
     return this.cTipoTutor;
   }
 
   public void setCTipoDuda(String cTipoDuda)
   {
     this.cTipoDuda = cTipoDuda;
   }
 
   public String getCTipoDuda()
   {
     return this.cTipoDuda;
   }
 
   public void setNDudasRecibidas(long nDudasRecibidas)
   {
     this.nDudasRecibidas = nDudasRecibidas;
   }
 
   public long getNDudasRecibidas()
   {
     return this.nDudasRecibidas;
   }
 
   public void setNDudasContestadas(long nDudasContestadas)
   {
     this.nDudasContestadas = nDudasContestadas;
   }
 
   public long getNDudasContestadas()
   {
     return this.nDudasContestadas;
   }
 
   public void setNControl(long nControl)
   {
     this.nControl = nControl;
   }
 
   public long getNControl()
   {
     return this.nControl;
   }
 
   public void setCDudaId(long cDudaId)
   {
     this.cDudaId = cDudaId;
   }
 
   public long getCDudaId()
   {
     return this.cDudaId;
   }
 
   public void setCExpId(long cExpId)
   {
     this.cExpId = cExpId;
   }
 
   public long getCExpId()
   {
     return this.cExpId;
   }
 
   public void setDCorreoElectronico(String dCorreoElectronico)
   {
     this.dCorreoElectronico = dCorreoElectronico;
   }
 
   public String getDCorreoElectronico()
   {
     return this.dCorreoElectronico;
   }
 
   public void setDDuda(String dDuda)
   {
     this.dDuda = dDuda;
   }
 
   public String getDDuda()
   {
     return this.dDuda;
   }
 
   public void setDRespuesta(String dRespuesta)
   {
     this.dRespuesta = dRespuesta;
   }
 
   public String getDRespuesta()
   {
     return this.dRespuesta;
   }
 
   public void setBContestado(String bContestado)
   {
     this.bContestado = bContestado;
   }
 
   public String getBContestado()
   {
     return this.bContestado;
   }
 
   public void setBLeido(String bLeido)
   {
     this.bLeido = bLeido;
   }
 
   public String getBLeido()
   {
     return this.bLeido;
   }
 
   public void setFRecepcion(String fRecepcion)
   {
     this.fRecepcion = fRecepcion;
   }
 
   public String getFRecepcion()
   {
     return this.fRecepcion;
   }
 
   public void setFEnvioContestacion(String fEnvioContestacion)
   {
     this.fEnvioContestacion = fEnvioContestacion;
   }
 
   public String getFEnvioContestacion()
   {
     return this.fEnvioContestacion;
   }
 
   public void setACreadoPor(String aCreadoPor)
   {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 
   public void setCTemaId(long cTemaId)
   {
     this.cTemaId = cTemaId;
   }
 
   public long getCTemaId()
   {
     return this.cTemaId;
   }
 
   public void setDTema(String dTema)
   {
     this.dTema = dTema;
   }
 
   public String getDTema()
   {
     return this.dTema;
   }
 
   public void setBCambio(String bCambio)
   {
     this.bCambio = bCambio;
   }
 
   public String getBCambio()
   {
     return this.bCambio;
   }
 
   public void setCDni(String cDni)
   {
     this.cDni = cDni;
   }
 
   public String getCDni()
   {
     return this.cDni;
   }
 
   public void setNDudasPendientes(long nDudasPendientes)
   {
     this.nDudasPendientes = nDudasPendientes;
   }
 
   public long getNDudasPendientes()
   {
     return this.nDudasPendientes;
   }
 
   public void setFInicio(String fInicio)
   {
     this.fInicio = fInicio;
   }
 
   public String getFInicio()
   {
     return this.fInicio;
   }
 
   public void setFFin(String fFin)
   {
     this.fFin = fFin;
   }
 
   public String getFFin()
   {
     return this.fFin;
   }
 
   public void setCodigoEPR(String codigoEPR)
   {
     this.codigoEPR = codigoEPR;
   }
 
   public String getCodigoEPR()
   {
     return this.codigoEPR;
   }
 
   public void setCGerenciaId(long cGerenciaId)
   {
     this.cGerenciaId = cGerenciaId;
   }
 
   public long getCGerenciaId()
   {
     return this.cGerenciaId;
   }
 
   public void setCProfesionalId(long cProfesionalId)
   {
     this.cProfesionalId = cProfesionalId;
   }
 
   public long getCProfesionalId()
   {
     return this.cProfesionalId;
   }
 
   public void setCEspecialidadId(long cEspecialidadId)
   {
     this.cEspecialidadId = cEspecialidadId;
   }
 
   public long getCEspecialidadId()
   {
     return this.cEspecialidadId;
   }
 
   public void setDGerencia(String dGerencia)
   {
     this.dGerencia = dGerencia;
   }
 
   public String getDGerencia()
   {
     return this.dGerencia;
   }
 
   public void setDProfesional(String dProfesional)
   {
     this.dProfesional = dProfesional;
   }
 
   public String getDProfesional()
   {
     return this.dProfesional;
   }
 
   public void setDEspecialidad(String dEspecialidad)
   {
     this.dEspecialidad = dEspecialidad;
   }
 
   public String getDEspecialidad()
   {
     return this.dEspecialidad;
   }
 
   public void setBProcedeCambio(String bProcedeCambio)
   {
     this.bProcedeCambio = bProcedeCambio;
   }
 
   public String getBProcedeCambio()
   {
     return this.bProcedeCambio;
   }
 
   public void setFInicioRespuesta(String fInicioRespuesta)
   {
     this.fInicioRespuesta = fInicioRespuesta;
   }
 
   public String getFInicioRespuesta()
   {
     return this.fInicioRespuesta;
   }
 
   public void setFFinRespuesta(String fFinRespuesta)
   {
     this.fFinRespuesta = fFinRespuesta;
   }
 
   public String getFFinRespuesta()
   {
     return this.fFinRespuesta;
   }
 
   public void setNTiempoRespuesta(long nTiempoRespuesta)
   {
     this.nTiempoRespuesta = nTiempoRespuesta;
   }
 
   public long getNTiempoRespuesta()
   {
     return this.nTiempoRespuesta;
   }
 
   public void setTiempoRespuesta(String tiempoRespuesta)
   {
     this.tiempoRespuesta = tiempoRespuesta;
   }
 
   public String getTiempoRespuesta()
   {
     return this.tiempoRespuesta;
   }
 }

