 package es.jcyl.fqs.ocap.ot.estatutario;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPEstatutarioOT
   implements Serializable
 {
   protected long cEstatutId;
   protected String bBorrado;
   protected long cPersonalId;
   protected Date fModificacion;
   protected Date fCreacion;
   protected String dDescripcion;
   protected String dNombre;
   protected String dPersonalNombre;
   protected String aCreadoPor;
 
   public long getCEstatutId()
   {
     return this.cEstatutId;
   }
 
   public void setCEstatutId(long cEstatutId)
   {
     this.cEstatutId = cEstatutId;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public long getCPersonalId()
   {
     return this.cPersonalId;
   }
 
   public void setCPersonalId(long cPersonalId)
   {
     this.cPersonalId = cPersonalId;
   }
 
   public Date getFModificacion()
   {
     return this.fModificacion;
   }
 
   public void setFModificacion(Date fModificacion)
   {
     this.fModificacion = fModificacion;
   }
 
   public Date getFCreacion()
   {
     return this.fCreacion;
   }
 
   public void setFCreacion(Date fCreacion)
   {
     this.fCreacion = fCreacion;
   }
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
   }
 
   public void setDDescripcion(String dDescripcion)
   {
     this.dDescripcion = dDescripcion;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public String getDPersonalNombre()
   {
     return this.dPersonalNombre;
   }
 
   public void setDPersonalNombre(String dPersonalNombre)
   {
     this.dPersonalNombre = dPersonalNombre;
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

