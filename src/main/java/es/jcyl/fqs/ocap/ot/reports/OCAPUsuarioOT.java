 package es.jcyl.fqs.ocap.ot.reports;
 
 import java.io.Serializable;
 
 public class OCAPUsuarioOT
   implements Serializable
 {
   private String dNombreApellidos;
   private String dNIF;
   private String dAccion;
   private String dGrado;
   private long cExpId;
 
   public OCAPUsuarioOT()
   {
     this.dNombreApellidos = this.dNombreApellidos;
     this.dNIF = this.dNIF;
     this.dAccion = this.dAccion;
   }
 
   public OCAPUsuarioOT(String dNombreApellidos, String dNIF, String dAccion, String dGrado) {
     this.dNombreApellidos = dNombreApellidos;
     this.dNIF = dNIF;
     this.dAccion = dAccion;
     this.dGrado = dGrado;
   }
 
   public void setDNombreApellidos(String dNombreApellidos) {
     this.dNombreApellidos = dNombreApellidos;
   }
 
   public String getDNombreApellidos()
   {
     return this.dNombreApellidos;
   }
 
   public void setDNIF(String dNIF)
   {
     this.dNIF = dNIF;
   }
 
   public String getDNIF()
   {
     return this.dNIF;
   }
 
   public void setDAccion(String dAccion)
   {
     this.dAccion = dAccion;
   }
 
   public String getDAccion()
   {
     return this.dAccion;
   }
 
   public void setCExpId(long cExpId)
   {
     this.cExpId = cExpId;
   }
 
   public long getCExpId()
   {
     return this.cExpId;
   }
 
   public void setDGrado(String dGrado)
   {
     this.dGrado = dGrado;
   }
 
   public String getDGrado()
   {
     return this.dGrado;
   }
 }

