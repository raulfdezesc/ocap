 package es.jcyl.fqs.ocap.ot.unidades;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPUnidadesOT
   implements Serializable
 {
   protected String bBorrado;
   protected long cAreaId;
   protected long cUnidadId;
   protected String dNombre;
   protected Date fModificacion;
   protected String dDescripcion;
   protected String dObservaciones;
   protected Date fCreacion;
   protected String dAreaNombre;
   protected long cProfesionalId;
   protected String dProfesionalNombre;
   protected String aCreadoPor;
   protected String aModificadoPor;
   protected long cItinerarioId;
   protected String aNombreCorto;
 
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
 
   public long getCUnidadId()
   {
     return this.cUnidadId;
   }
 
   public void setCUnidadId(long cUnidadId)
   {
     this.cUnidadId = cUnidadId;
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
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
   }
 
   public void setDDescripcion(String dDescripcion)
   {
     this.dDescripcion = dDescripcion;
   }
 
   public Date getFCreacion()
   {
     return this.fCreacion;
   }
 
   public void setFCreacion(Date fCreacion)
   {
     this.fCreacion = fCreacion;
   }
 
   public void setDAreaNombre(String dAreaNombre)
   {
     this.dAreaNombre = dAreaNombre;
   }
 
   public String getDAreaNombre()
   {
     return this.dAreaNombre;
   }
 
   public void setDObservaciones(String dObservaciones)
   {
     this.dObservaciones = dObservaciones;
   }
 
   public String getDObservaciones()
   {
     return this.dObservaciones;
   }
 
   public void setCProfesionalId(long cProfesionalId)
   {
     this.cProfesionalId = cProfesionalId;
   }
 
   public long getCProfesionalId()
   {
     return this.cProfesionalId;
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

