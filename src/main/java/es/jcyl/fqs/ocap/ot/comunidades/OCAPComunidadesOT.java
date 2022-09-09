 package es.jcyl.fqs.ocap.ot.comunidades;
 
 import java.io.Serializable;
 
 public class OCAPComunidadesOT
   implements Serializable
 {
   protected String dComuni;
   protected String cComuniId;
 
   public String getDComuni()
   {
     return this.dComuni;
   }
 
   public void setDComuni(String dComuni)
   {
     this.dComuni = dComuni;
   }
 
   public void setCComuniId(String cComuniId)
   {
     this.cComuniId = cComuniId;
   }
 
   public String getCComuniId()
   {
     return this.cComuniId;
   }
 }

