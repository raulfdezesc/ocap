 package es.jcyl.fqs.ocap.ot.subsanaciones;
 
 import java.util.Date;
 
 public class OCAPSubsanacionesOT
 {
   private long cSubsanacionId;
   private long cSolicitudId;
   private Date fPeticion;
   private String aPeticion;
   private String aModificadoPor;
   private String aCreadoPor;
 
   public void setCSubsanacionId(long cSubsanacionId)
   {
     this.cSubsanacionId = cSubsanacionId;
   }
 
   public long getCSubsanacionId()
   {
     return this.cSubsanacionId;
   }
 
   public void setCSolicitudId(long cSolicitudId)
   {
     this.cSolicitudId = cSolicitudId;
   }
 
   public long getCSolicitudId()
   {
     return this.cSolicitudId;
   }
 
   public void setFPeticion(Date fPeticion)
   {
     this.fPeticion = fPeticion;
   }
 
   public Date getFPeticion()
   {
     return this.fPeticion;
   }
 
   public void setAPeticion(String aPeticion)
   {
     this.aPeticion = aPeticion;
   }
 
   public String getAPeticion()
   {
     return this.aPeticion;
   }
 
   public void setAModificadoPor(String aModificadoPor)
   {
     this.aModificadoPor = aModificadoPor;
   }
 
   public String getAModificadoPor()
   {
     return this.aModificadoPor;
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

