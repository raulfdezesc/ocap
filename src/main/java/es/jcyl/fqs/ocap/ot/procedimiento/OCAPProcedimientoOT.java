 package es.jcyl.fqs.ocap.ot.procedimiento;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPProcedimientoOT
   implements Serializable
 {
   protected Long cProcedimientoId;
   protected String dNombre;
   protected String dDescripcion;
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
 
   public Long getCProcedimientoId()
   {
     return this.cProcedimientoId;
   }
 
   public void setCProcedimientoId(Long cProcedimientoId)
   {
     this.cProcedimientoId = cProcedimientoId;
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
 
   public void setACreadoPor(String aCreadoPor)
   {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 }

