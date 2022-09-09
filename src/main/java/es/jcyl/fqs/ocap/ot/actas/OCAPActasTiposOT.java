 package es.jcyl.fqs.ocap.ot.actas;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Date;
 
 public class OCAPActasTiposOT
   implements Serializable
 {
   private String cActaTipo;
   private String dNombre;
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
   
   public void setCActaTipo(String cActaTipo)
   {
     this.cActaTipo = cActaTipo;
   }
 
   public String getCActaTipo()
   {
     return this.cActaTipo;
   }   
 
 }

