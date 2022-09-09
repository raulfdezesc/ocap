 package es.jcyl.fqs.ocap.ot.provincias;
 
 import java.io.Serializable;
 
 public class OCAPProvinciasOT
   implements Serializable
 {
   protected String dProvincia;
   protected String cProvinciaId;
 
   public String getDProvincia()
   {
     return this.dProvincia;
   }
 
   public void setDProvincia(String dProvincia)
   {
     this.dProvincia = dProvincia;
   }
 
   public void setCProvinciaId(String cProvinciaId)
   {
     this.cProvinciaId = cProvinciaId;
   }
 
   public String getCProvinciaId()
   {
     return this.cProvinciaId;
   }
 }

