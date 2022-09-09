 package es.jcyl.fqs.ocap.ot.areasItinerarios;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Date;
 
 public class OCAPAreasItinerariosOT
   implements Serializable
 {
   protected String bBorrado;
   protected Long cAreaId;
   protected Long cItinerarioId;
   protected String dNombre;
   protected String dNombreLargo;
   protected String dInformacion;
   protected float nCreditosArea;
   protected Date fModificacion;
   protected Date fCreacion;
   protected ArrayList listaCuestionarios;
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setCAreaId(Long cAreaId)
   {
     this.cAreaId = cAreaId;
   }
 
   public Long getCAreaId()
   {
     return this.cAreaId;
   }
 
   public void setCItinerarioId(Long cItinerarioId)
   {
     this.cItinerarioId = cItinerarioId;
   }
 
   public Long getCItinerarioId()
   {
     return this.cItinerarioId;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setFModificacion(Date fModificacion)
   {
     this.fModificacion = fModificacion;
   }
 
   public Date getFModificacion()
   {
     return this.fModificacion;
   }
 
   public void setFCreacion(Date fCreacion)
   {
     this.fCreacion = fCreacion;
   }
 
   public Date getFCreacion()
   {
     return this.fCreacion;
   }
 
   public void setListaCuestionarios(ArrayList listaCuestionarios)
   {
     this.listaCuestionarios = listaCuestionarios;
   }
 
   public ArrayList getListaCuestionarios()
   {
     return this.listaCuestionarios;
   }
 
   public void setDNombreLargo(String dNombreLargo)
   {
     this.dNombreLargo = dNombreLargo;
   }
 
   public String getDNombreLargo()
   {
     return this.dNombreLargo;
   }
 
   public void setNCreditosArea(float nCreditosArea)
   {
     this.nCreditosArea = nCreditosArea;
   }
 
   public float getNCreditosArea()
   {
     return this.nCreditosArea;
   }
 
   public void setDInformacion(String dInformacion)
   {
     this.dInformacion = dInformacion;
   }
 
   public String getDInformacion()
   {
     return this.dInformacion;
   }
 }

