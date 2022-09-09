 package es.jcyl.fqs.ocap.ot.tiposFirmante;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPTiposFirmanteOT
   implements Serializable
 {
   protected Long cTipoId;
   protected String dNombre;
   protected String dObservaciones;
   protected Date fCreacion;
   protected Date fModificacion;
   protected String bBorrado;
   protected String aCreadoPor;
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public Long getCTipoId()
   {
     return this.cTipoId;
   }
 
   public void setCTipoId(Long cTipoId)
   {
     this.cTipoId = cTipoId;
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
 
   public String getDObservaciones()
   {
     return this.dObservaciones;
   }
 
   public void setDObservaciones(String dObservaciones)
   {
     this.dObservaciones = dObservaciones;
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

