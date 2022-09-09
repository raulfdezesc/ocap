 package es.jcyl.fqs.ocap.ot.centroTrabajo;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPCentroTrabajoOT
   implements Serializable
 {
   protected Date fCreacion;
   protected String aObservaciones;
   protected long cGerenciaId;
   protected String aDireccion;
   protected String dDescripcion;
   protected long cCentrotrabajoId;
   protected String dNombre;
   protected String aTelefono;
   protected String aCodigopostal;
   protected String aCiudad;
   protected String dGerenciaNombre;
   protected String bBorrado;
   protected Date fModificacion;
   protected String aCreadoPor;
 
   public Date getFCreacion()
   {
     return this.fCreacion;
   }
 
   public void setFCreacion(Date fCreacion)
   {
     this.fCreacion = fCreacion;
   }
 
   public String getAObservaciones()
   {
     return this.aObservaciones;
   }
 
   public void setAObservaciones(String aObservaciones)
   {
     this.aObservaciones = aObservaciones;
   }
 
   public long getCGerenciaId()
   {
     return this.cGerenciaId;
   }
 
   public void setCGerenciaId(long cGerenciaId)
   {
     this.cGerenciaId = cGerenciaId;
   }
 
   public String getADireccion()
   {
     return this.aDireccion;
   }
 
   public void setADireccion(String aDireccion)
   {
     this.aDireccion = aDireccion;
   }
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
   }
 
   public void setDDescripcion(String dDescripcion)
   {
     this.dDescripcion = dDescripcion;
   }
 
   public long getCCentrotrabajoId()
   {
     return this.cCentrotrabajoId;
   }
 
   public void setCCentrotrabajoId(long cCentrotrabajoId)
   {
     this.cCentrotrabajoId = cCentrotrabajoId;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public String getATelefono()
   {
     return this.aTelefono;
   }
 
   public void setATelefono(String aTelefono)
   {
     this.aTelefono = aTelefono;
   }
 
   public String getACodigopostal()
   {
     return this.aCodigopostal;
   }
 
   public void setACodigopostal(String aCodigopostal)
   {
     this.aCodigopostal = aCodigopostal;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public Date getFModificacion()
   {
     return this.fModificacion;
   }
 
   public void setFModificacion(Date fModificacion)
   {
     this.fModificacion = fModificacion;
   }
 
   public void setACiudad(String aCiudad)
   {
     this.aCiudad = aCiudad;
   }
 
   public String getACiudad()
   {
     return this.aCiudad;
   }
 
   public void setDGerenciaNombre(String dGerenciaNombre)
   {
     this.dGerenciaNombre = dGerenciaNombre;
   }
 
   public String getDGerenciaNombre()
   {
     return this.dGerenciaNombre;
   }
 
   public void setACreadoPor(String aCreadoPor)
   {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 }

