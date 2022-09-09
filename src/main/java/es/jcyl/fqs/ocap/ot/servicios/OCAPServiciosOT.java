 package es.jcyl.fqs.ocap.ot.servicios;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPServiciosOT
   implements Serializable
 {
   protected long cServicioId;
   protected String bBorrado;
   protected String dObservaciones;
   protected String dNombreLargo;
   protected Date fModificacion;
   protected Date fCreacion;
   protected String dNombreCorto;
   protected long cProfesionalId;
   protected long cEspecId;
   protected long cItinerarioId;
   protected String aCreadoPor;
   protected String aModificadoPor;
   protected String dProfesionalNombre;
   protected String dEspecNombre;
 
   public long getCServicioId()
   {
     return this.cServicioId;
   }
 
   public void setCServicioId(long cServicioId)
   {
     this.cServicioId = cServicioId;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public String getDObservaciones()
   {
     return this.dObservaciones;
   }
 
   public void setDObservaciones(String dObservaciones)
   {
     this.dObservaciones = dObservaciones;
   }
 
   public String getDNombreLargo()
   {
     return this.dNombreLargo;
   }
 
   public void setDNombreLargo(String dNombreLargo)
   {
     this.dNombreLargo = dNombreLargo;
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
 
   public String getDNombreCorto()
   {
     return this.dNombreCorto;
   }
 
   public void setDNombreCorto(String dNombreCorto)
   {
     this.dNombreCorto = dNombreCorto;
   }
 
   public void setCProfesionalId(long cProfesionalId)
   {
     this.cProfesionalId = cProfesionalId;
   }
 
   public long getCProfesionalId()
   {
     return this.cProfesionalId;
   }
 
   public void setCEspecId(long cEspecId)
   {
     this.cEspecId = cEspecId;
   }
 
   public long getCEspecId()
   {
     return this.cEspecId;
   }
 
   public void setCItinerarioId(long cItinerarioId)
   {
     this.cItinerarioId = cItinerarioId;
   }
 
   public long getCItinerarioId()
   {
     return this.cItinerarioId;
   }
 
   public void setDProfesionalNombre(String dProfesionalNombre)
   {
     this.dProfesionalNombre = dProfesionalNombre;
   }
 
   public String getDProfesionalNombre()
   {
     return this.dProfesionalNombre;
   }
 
   public void setDEspecNombre(String dEspecNombre)
   {
     this.dEspecNombre = dEspecNombre;
   }
 
   public String getDEspecNombre()
   {
     return this.dEspecNombre;
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
 }

