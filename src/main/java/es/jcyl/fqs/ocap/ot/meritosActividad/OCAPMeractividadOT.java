 package es.jcyl.fqs.ocap.ot.meritosActividad;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPMeractividadOT
   implements Serializable
 {
   protected String dNombre;
   protected String bBorrado;
   protected Integer cMeritoId;
   protected Date fModificacion;
   protected String dObservaciones;
   protected Long cActividadId;
   protected Date fCreacion;
   protected String aCreadoPor;
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public Integer getCMeritoId()
   {
     return this.cMeritoId;
   }
 
   public void setCMeritoId(Integer cMeritoId)
   {
     this.cMeritoId = cMeritoId;
   }
 
   public Date getFModificacion()
   {
     return this.fModificacion;
   }
 
   public void setFModificacion(Date fModificacion)
   {
     this.fModificacion = fModificacion;
   }
 
   public String getDObservaciones()
   {
     return this.dObservaciones;
   }
 
   public void setDObservaciones(String dObservaciones)
   {
     this.dObservaciones = dObservaciones;
   }
 
   public Long getCActividadId()
   {
     return this.cActividadId;
   }
 
   public void setCActividadId(Long cActividadId)
   {
     this.cActividadId = cActividadId;
   }
 
   public Date getFCreacion()
   {
     return this.fCreacion;
   }
 
   public void setFCreacion(Date fCreacion)
   {
     this.fCreacion = fCreacion;
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

