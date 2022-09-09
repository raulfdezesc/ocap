 package es.jcyl.fqs.ocap.ot.puestos;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPPuestosOT
   implements Serializable
 {
   protected String dObservaciones;
   protected String bBorrado;
   protected long cProfesionalId;
   protected Date fModificacion;
   protected Date fCreacion;
   protected String dNombre;
   protected long cPuestoId;
   protected String dProfesionalNombre;
   protected String aCreadoPor;
   protected String aModificadoPor;
   protected long cItinerarioId;
   protected String aNombreCorto;
 
   public String getDObservaciones()
   {
     return this.dObservaciones;
   }
 
   public void setDObservaciones(String dObservaciones)
   {
     this.dObservaciones = dObservaciones;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public long getCProfesionalId()
   {
     return this.cProfesionalId;
   }
 
   public void setCProfesionalId(long cProfesionalId)
   {
     this.cProfesionalId = cProfesionalId;
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
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public long getCPuestoId()
   {
     return this.cPuestoId;
   }
 
   public void setCPuestoId(long cPuestoId)
   {
     this.cPuestoId = cPuestoId;
   }
 
   public void setDProfesionalNombre(String dProfesionalNombre)
   {
     this.dProfesionalNombre = dProfesionalNombre;
   }
 
   public String getDProfesionalNombre()
   {
     return this.dProfesionalNombre;
   }
 
   public void setACreadoPor(String aCreadoPor)
   {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 
   public void setAModificadoPor(String aModificadoPor)
   {
     this.aModificadoPor = aModificadoPor;
   }
 
   public String getAModificadoPor()
   {
     return this.aModificadoPor;
   }
 
   public void setCItinerarioId(long cItinerarioId)
   {
     this.cItinerarioId = cItinerarioId;
   }
 
   public long getCItinerarioId()
   {
     return this.cItinerarioId;
   }
 
   public void setANombreCorto(String aNombreCorto)
   {
     this.aNombreCorto = aNombreCorto;
   }
 
   public String getANombreCorto()
   {
     return this.aNombreCorto;
   }
 }

