 package es.jcyl.fqs.ocap.ot.mensajes;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPMensajesOT
   implements Serializable
 {
   protected long cEstatutId;
   protected long cMensajeId;
   protected String dDescripcion;
   protected String bBorrado;
   protected Date fModificacion;
   protected Date fCreacion;
   protected long cGradoId;
   protected String dEstatutNombre;
   protected String dGradoNombre;
   protected String aCreadoPor;
 
   public long getCEstatutId()
   {
     return this.cEstatutId;
   }
 
   public void setCEstatutId(long cEstatutId)
   {
     this.cEstatutId = cEstatutId;
   }
 
   public long getCMensajeId()
   {
     return this.cMensajeId;
   }
 
   public void setCMensajeId(long cMensajeId)
   {
     this.cMensajeId = cMensajeId;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
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
 
   public long getCGradoId()
   {
     return this.cGradoId;
   }
 
   public void setCGradoId(long cGradoId)
   {
     this.cGradoId = cGradoId;
   }
 
   public void setDEstatutNombre(String dEstatutNombre)
   {
     this.dEstatutNombre = dEstatutNombre;
   }
 
   public String getDEstatutNombre()
   {
     return this.dEstatutNombre;
   }
 
   public void setDGradoNombre(String dGradoNombre)
   {
     this.dGradoNombre = dGradoNombre;
   }
 
   public String getDGradoNombre()
   {
     return this.dGradoNombre;
   }
 
   public void setDDescripcion(String dDescripcion)
   {
     this.dDescripcion = dDescripcion;
   }
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
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

