 package es.jcyl.fqs.ocap.ot.tipoGerencias;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPTipoGerenciasOT
   implements Serializable
 {
   protected String dDescripcion;
   protected long cTipogerenciaId;
   protected String dNombre;
   protected String bBorrado;
   protected Date fModificacion;
   protected Date fCreacion;
   protected String aCreadoPor;
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
   }
 
   public void setDDescripcion(String dDescripcion)
   {
     this.dDescripcion = dDescripcion;
   }
 
   public long getCTipogerenciaId()
   {
     return this.cTipogerenciaId;
   }
 
   public void setCTipogerenciaId(long cTipogerenciaId)
   {
     this.cTipogerenciaId = cTipogerenciaId;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
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
 
   public void setACreadoPor(String aCreadoPor)
   {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 }

