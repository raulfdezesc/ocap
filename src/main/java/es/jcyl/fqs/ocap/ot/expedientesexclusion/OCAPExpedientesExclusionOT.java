 package es.jcyl.fqs.ocap.ot.expedientesexclusion;
 
 import java.io.Serializable;
 
 public class OCAPExpedientesExclusionOT
   implements Serializable
 {
   protected long cExclusionId;
   protected long cExpId;
   protected String dNombre;
   protected String dDescripcion;
   protected String cUsualta;
   protected String cUsumodi;
   protected String dOtrosMotivos;
 
   public void setCExclusionId(long cExclusionId)
   {
     this.cExclusionId = cExclusionId;
   }
 
   public long getCExclusionId()
   {
     return this.cExclusionId;
   }
 
   public void setCExpId(long cExpId) {
     this.cExpId = cExpId;
   }
 
   public long getCExpId()
   {
     return this.cExpId;
   }
 
   public void setDNombre(String dNombre) {
     this.dNombre = dNombre;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDDescripcion(String dDescripcion) {
     this.dDescripcion = dDescripcion;
   }
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
   }
 
   public void setCUsualta(String cUsualta) {
     this.cUsualta = cUsualta;
   }
 
   public String getCUsualta()
   {
     return this.cUsualta;
   }
 
   public void setCUsumodi(String cUsumodi) {
     this.cUsumodi = cUsumodi;
   }
 
   public String getCUsumodi()
   {
     return this.cUsumodi;
   }
 
   public void setDOtrosMotivos(String dOtrosMotivos) {
     this.dOtrosMotivos = dOtrosMotivos;
   }
 
   public String getDOtrosMotivos()
   {
     return this.dOtrosMotivos;
   }
 }

