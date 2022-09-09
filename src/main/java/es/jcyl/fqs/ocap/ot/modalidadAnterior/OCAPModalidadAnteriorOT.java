 package es.jcyl.fqs.ocap.ot.modalidadAnterior;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPModalidadAnteriorOT
   implements Serializable
 {
   protected long cModAnteriorId;
   protected String dNombre;
   protected String dDescripcion;
   protected long cGradoId;
   protected String bBorrado;
   protected String aModalidad;
   protected Date fUsuAlta;
   protected String cUsuAlta;
   protected Date fUsuModi;
   protected String cUsuModi;
 
   public void setCModAnteriorId(long cModAnteriorId)
   {
     this.cModAnteriorId = cModAnteriorId;
   }
 
   public long getCModAnteriorId()
   {
     return this.cModAnteriorId;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDDescripcion(String dDescripcion)
   {
     this.dDescripcion = dDescripcion;
   }
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
   }
 
   public void setCGradoId(long cGradoId)
   {
     this.cGradoId = cGradoId;
   }
 
   public long getCGradoId()
   {
     return this.cGradoId;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setAModalidad(String aModalidad)
   {
     this.aModalidad = aModalidad;
   }
 
   public String getAModalidad()
   {
     return this.aModalidad;
   }
 
   public void setFUsuAlta(Date fUsuAlta)
   {
     this.fUsuAlta = fUsuAlta;
   }
 
   public Date getFUsuAlta()
   {
     return this.fUsuAlta;
   }
 
   public void setCUsuAlta(String cUsuAlta)
   {
     this.cUsuAlta = cUsuAlta;
   }
 
   public String getCUsuAlta()
   {
     return this.cUsuAlta;
   }
 
   public void setFUsuModi(Date fUsuModi)
   {
     this.fUsuModi = fUsuModi;
   }
 
   public Date getFUsuModi()
   {
     return this.fUsuModi;
   }
 
   public void setCUsuModi(String cUsuModi)
   {
     this.cUsuModi = cUsuModi;
   }
 
   public String getCUsuModi()
   {
     return this.cUsuModi;
   }
 }

