 package es.jcyl.fqs.ocap.ot.motExclusion;
 
 import java.io.Serializable;
 
 public class OCAPMotExclusionOT
   implements Serializable
 {
   protected long cExclusionId;
   protected String dNombre;
   protected String dDescripcion;
 
   public void setCExclusionId(long cExclusionId)
   {
     this.cExclusionId = cExclusionId;
   }
 
   public long getCExclusionId()
   {
     return this.cExclusionId;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDDescripcion(String dDescripcion)
   {
     this.dDescripcion = dDescripcion;
   }
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
   }
 }

