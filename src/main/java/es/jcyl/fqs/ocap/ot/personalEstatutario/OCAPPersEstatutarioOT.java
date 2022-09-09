 package es.jcyl.fqs.ocap.ot.personalEstatutario;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPPersEstatutarioOT
   implements Serializable
 {
   protected String bBorrado;
   protected long cPersonalId;
   protected Date fModificacion;
   protected Date fCreacion;
   protected String dNombre;
   protected String dDescripcion;
   protected String aCreadoPor;
 
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
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
   }
 
   public void setDDescripcion(String dDescripcion)
   {
     this.dDescripcion = dDescripcion;
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

