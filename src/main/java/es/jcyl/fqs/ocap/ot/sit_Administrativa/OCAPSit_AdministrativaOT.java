 package es.jcyl.fqs.ocap.ot.sit_Administrativa;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPSit_AdministrativaOT
   implements Serializable
 {
   protected String bBorrado;
   protected Long cSit_AdministrativaId;
   protected String cUsuAlta;
   protected String cUsuModi;
   protected String dDescripcion;
   protected String dNombre;
   protected Date fUsuAlta;
   protected Date fUsuModi;
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setCSit_AdministrativaId(Long cSit_AdministrativaId)
   {
     this.cSit_AdministrativaId = cSit_AdministrativaId;
   }
 
   public Long getCSit_AdministrativaId()
   {
     return this.cSit_AdministrativaId;
   }
 
   public void setCUsuAlta(String cUsuAlta)
   {
     this.cUsuAlta = cUsuAlta;
   }
 
   public String getCUsuAlta()
   {
     return this.cUsuAlta;
   }
 
   public void setCUsuModi(String cUsuModi)
   {
     this.cUsuModi = cUsuModi;
   }
 
   public String getCUsuModi()
   {
     return this.cUsuModi;
   }
 
   public void setDDescripcion(String dDescripcion)
   {
     this.dDescripcion = dDescripcion;
   }
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setFUsuAlta(Date fUsuAlta)
   {
     this.fUsuAlta = fUsuAlta;
   }
 
   public Date getFUsuAlta()
   {
     return this.fUsuAlta;
   }
 
   public void setFUsuModi(Date fUsuModi)
   {
     this.fUsuModi = fUsuModi;
   }
 
   public Date getFUsuModi()
   {
     return this.fUsuModi;
   }
 }

