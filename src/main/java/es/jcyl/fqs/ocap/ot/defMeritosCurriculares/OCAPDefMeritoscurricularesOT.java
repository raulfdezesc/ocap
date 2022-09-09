 package es.jcyl.fqs.ocap.ot.defMeritosCurriculares;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPDefMeritoscurricularesOT
   implements Serializable
 {
   protected String bBorrado;
   protected String dNombre;
   protected Date fModificacion;
   protected Date fCreacion;
   protected String dDescripcion;
   protected long cDefmeritoId;
   protected String aCreadoPor;
   protected String bPresente;
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
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
 
   public long getCDefmeritoId()
   {
     return this.cDefmeritoId;
   }
 
   public void setCDefmeritoId(long cDefmeritoId)
   {
     this.cDefmeritoId = cDefmeritoId;
   }
 
   public void setACreadoPor(String aCreadoPor)
   {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 
   public void setBPresente(String bPresente)
   {
     this.bPresente = bPresente;
   }
 
   public String getBPresente()
   {
     return this.bPresente;
   }
 }

