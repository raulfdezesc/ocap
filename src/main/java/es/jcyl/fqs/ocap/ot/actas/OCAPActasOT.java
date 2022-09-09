 package es.jcyl.fqs.ocap.ot.actas;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Date;
 
 public class OCAPActasOT
   implements Serializable
 {
   private String dNombreApellidosPresi1;
   private String dNombreApellidosPresi2;
   private ArrayList listadoVocalesTitulares;
   private ArrayList listadoVocalesSuplentes;
   private ArrayList listadoMiembros;
   private String dNombreApellidosSecre1;
   private String dNombreApellidosSecre2;
   private String bSexoPresi1;
   private String bSexoPresi2;
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
   private ArrayList listadoUsuarios;
   private ArrayList listadoUsuariosInfMotivado;
   private ArrayList listadoUsuariosAclaraciones;
   private long cActaId;
   private String cTipoActa;
   private long cGradoId;
   private long cConvocatoriaId;
   private long cGerenciaId;
   private long cProfesionalId;
   private String aCreadoPor;
   
   private String dGrado;
   private String dConvocatoria;
   private String dProfesional;
   private String dTipoActa;
   
   private String fGeneracion;
 
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
 
   public void setCActaId(long cActaId)
   {
     this.cActaId = cActaId;
   }
 
   public long getCActaId()
   {
     return this.cActaId;
   }
 
   public void setCGradoId(long cGradoId)
   {
     this.cGradoId = cGradoId;
   }
 
   public long getCGradoId()
   {
     return this.cGradoId;
   }
 
   public void setCConvocatoriaId(long cConvocatoriaId)
   {
     this.cConvocatoriaId = cConvocatoriaId;
   }
 
   public long getCConvocatoriaId()
   {
     return this.cConvocatoriaId;
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
 
   public void setListadoUsuariosInfMotivado(ArrayList listadoUsuariosInfMotivado)
   {
     this.listadoUsuariosInfMotivado = listadoUsuariosInfMotivado;
   }
 
   public ArrayList getListadoUsuariosInfMotivado()
   {
     return this.listadoUsuariosInfMotivado;
   }
 
   public void setListadoUsuariosAclaraciones(ArrayList listadoUsuariosAclaraciones)
   {
     this.listadoUsuariosAclaraciones = listadoUsuariosAclaraciones;
   }
 
   public ArrayList getListadoUsuariosAclaraciones()
   {
     return this.listadoUsuariosAclaraciones;
   }
 
   public void setCTipoActa(String cTipoActa)
   {
     this.cTipoActa = cTipoActa;
   }
 
   public String getCTipoActa()
   {
     return this.cTipoActa;
   }
 
   public void setACreadoPor(String aCreadoPor)
   {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 
   public void setListadoMiembros(ArrayList listadoMiembros)
   {
     this.listadoMiembros = listadoMiembros;
   }
 
   public ArrayList getListadoMiembros()
   {
     return this.listadoMiembros;
   }
 
   public void setListadoUsuarios(ArrayList listadoUsuarios)
   {
     this.listadoUsuarios = listadoUsuarios;
   }
 
   public ArrayList getListadoUsuarios()
   {
     return this.listadoUsuarios;
   }

	public String getDGrado() {
		return dGrado;
	}
	
	public void setDGrado(String dGrado) {
		this.dGrado = dGrado;
	}
	
	public String getDConvocatoria() {
		return dConvocatoria;
	}
	
	public void setDConvocatoria(String dConvocatoria) {
		this.dConvocatoria = dConvocatoria;
	}
	
	public String getDProfesional() {
		return dProfesional;
	}
	
	public void setDProfesional(String dProfesional) {
		this.dProfesional = dProfesional;
	}
	
	public String getDTipoActa() {
		return dTipoActa;
	}
	
	public void setDTipoActa(String dTipoActa) {
		this.dTipoActa = dTipoActa;
	}

	public String getFGeneracion() {
		return fGeneracion;
	}

	public void setFGeneracion(String fGeneracion) {
		this.fGeneracion = fGeneracion;
	}
   
   
   
 }

