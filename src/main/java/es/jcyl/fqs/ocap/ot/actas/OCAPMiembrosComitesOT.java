 package es.jcyl.fqs.ocap.ot.actas;
 
 import java.io.Serializable;
 
 public class OCAPMiembrosComitesOT
   implements Serializable
 {
   private long cMiembroId;
   private long cConvocatoria;
   private String dConvocatoria;
   private long cGerenciaId;
   private long cProfesionalId;
   private String dProfesional;
   private String bBorrado;
   private String cSexo;
   private String cTipo;
   private String cUsuAlta;
   private String cUsuModi;
   private String dApellidos;
   private String dNombre;
   private String fUsuAlta;
   private String fUsuModi;
   private String dRol;
   private String bPresidente;
   private String bEnCalidadPresi;
   private String cEnCalidad;
   private String dDatosMiembro;
 
   public void setCMiembroId(long cMiembroId)
   {
     this.cMiembroId = cMiembroId;
   }
 
   public long getCMiembroId()
   {
     return this.cMiembroId;
   }
 
   public void setCConvocatoria(long cConvocatoria)
   {
     this.cConvocatoria = cConvocatoria;
   }
 
   public long getCConvocatoria()
   {
     return this.cConvocatoria;
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
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setCSexo(String cSexo)
   {
     this.cSexo = cSexo;
   }
 
   public String getCSexo()
   {
     return this.cSexo;
   }
 
   public void setCTipo(String cTipo)
   {
     this.cTipo = cTipo;
   }
 
   public String getCTipo()
   {
     return this.cTipo;
   }
 
   public void setCUsuAlta(String cUsuAlta)
   {
     this.cUsuAlta = cUsuAlta;
   }
 
   public String getCUsuAlta()
   {
     return this.cUsuAlta;
   }
 
   public void setCUsuModi(String cUsuModi)
   {
     this.cUsuModi = cUsuModi;
   }
 
   public String getCUsuModi()
   {
     return this.cUsuModi;
   }
 
   public void setDApellidos(String dApellidos)
   {
     this.dApellidos = dApellidos;
   }
 
   public String getDApellidos()
   {
     return this.dApellidos;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setFUsuAlta(String fUsuAlta)
   {
     this.fUsuAlta = fUsuAlta;
   }
 
   public String getFUsuAlta()
   {
     return this.fUsuAlta;
   }
 
   public void setFUsuModi(String fUsuModi)
   {
     this.fUsuModi = fUsuModi;
   }
 
   public String getFUsuModi()
   {
     return this.fUsuModi;
   }
 
   public void setBPresidente(String bPresidente)
   {
     this.bPresidente = bPresidente;
   }
 
   public String getBPresidente()
   {
     return this.bPresidente;
   }
 
   public void setBEnCalidadPresi(String bEnCalidadPresi)
   {
     this.bEnCalidadPresi = bEnCalidadPresi;
   }
 
   public String getBEnCalidadPresi()
   {
     return this.bEnCalidadPresi;
   }
 
   public void setDDatosMiembro(String dDatosMiembro) {
     this.dDatosMiembro = dDatosMiembro;
   }
 
   public String getDDatosMiembro()
   {
     return this.dDatosMiembro;
   }
 
   public void setDRol(String dRol)
   {
     this.dRol = dRol;
   }
 
   public String getDRol()
   {
     return this.dRol;
   }
   public void setCEnCalidad(String cEnCalidad) {
     this.cEnCalidad = cEnCalidad;
   }
 
   public String getCEnCalidad()
   {
     return this.cEnCalidad;
   }
 
   public void setDConvocatoria(String dConvocatoria)
   {
     this.dConvocatoria = dConvocatoria;
   }
 
   public String getDConvocatoria()
   {
     return this.dConvocatoria;
   }
 
   public void setDProfesional(String dProfesional)
   {
     this.dProfesional = dProfesional;
   }
 
   public String getDProfesional()
   {
     return this.dProfesional;
   }
 }

