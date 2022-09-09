 package es.jcyl.fqs.ocap.ot.meritosModalidad;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPMerModalidadOT
   implements Serializable
 {
   protected String bBorrado;
   protected String dDescripcion;
   protected Long cModalidadId;
   protected Date fModificacion;
   protected String dNombre;
   protected Date fCreacion;
   protected String aCreadoPor;
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
   }
 
   public void setDDescripcion(String dDescripcion)
   {
     this.dDescripcion = dDescripcion;
   }
 
   public Long getCModalidadId()
   {
     return this.cModalidadId;
   }
 
   public void setCModalidadId(Long cModalidadId)
   {
     this.cModalidadId = cModalidadId;
   }
 
   public Date getFModificacion()
   {
     return this.fModificacion;
   }
 
   public void setFModificacion(Date fModificacion)
   {
     this.fModificacion = fModificacion;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
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

