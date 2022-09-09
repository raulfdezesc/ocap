 package es.jcyl.fqs.ocap.ot.areas;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPAreasOT
   implements Serializable
 {
   protected String bBorrado;
   protected long cAreaId;
   protected String dNombre;
   protected Date fModificacion;
   protected Date fCreacion;
   protected String dDescripcion;
   protected String dObservaciones;
   protected String aCreadoPor;
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public long getCAreaId()
   {
     return this.cAreaId;
   }
 
   public void setCAreaId(long cAreaId)
   {
     this.cAreaId = cAreaId;
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
 
   public void setDObservaciones(String dObservaciones)
   {
     this.dObservaciones = dObservaciones;
   }
 
   public String getDObservaciones()
   {
     return this.dObservaciones;
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

