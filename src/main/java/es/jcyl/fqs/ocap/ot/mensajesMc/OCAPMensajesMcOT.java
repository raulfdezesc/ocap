 package es.jcyl.fqs.ocap.ot.mensajesMc;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPMensajesMcOT
   implements Serializable
 {
   protected long cMensajeId;
   protected long cEstatutId;
   protected String bBorrado;
   protected long cDefmeritoId;
   protected Date fModificacion;
   protected Date fCreacion;
   protected String dDescripcion;
   protected String dEstatutNombre;
   protected String dDefmeritoNombre;
   protected String aCreadoPor;
 
   public long getCEstatutId()
   {
     return this.cEstatutId;
   }
 
   public void setCEstatutId(long cEstatutId)
   {
     this.cEstatutId = cEstatutId;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public long getCDefmeritoId()
   {
     return this.cDefmeritoId;
   }
 
   public void setCDefmeritoId(long cDefmeritoId)
   {
     this.cDefmeritoId = cDefmeritoId;
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
 
   public String getDEstatutNombre()
   {
     return this.dEstatutNombre;
   }
 
   public void setDEstatutNombre(String dEstatutNombre)
   {
     this.dEstatutNombre = dEstatutNombre;
   }
 
   public String getDDefmeritoNombre()
   {
     return this.dDefmeritoNombre;
   }
 
   public void setDDefmeritoNombre(String dDefmeritoNombre)
   {
     this.dDefmeritoNombre = dDefmeritoNombre;
   }
 
   public void setCMensajeId(long cMensajeId)
   {
     this.cMensajeId = cMensajeId;
   }
 
   public long getCMensajeId()
   {
     return this.cMensajeId;
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

