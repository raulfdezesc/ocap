 package es.jcyl.fqs.ocap.ot.reports;
 
 import java.io.Serializable;
 
 public class OCAPAsistenteOT
   implements Serializable
 {
   private String dNombreApellidos;
   private String bSexo;
   private String bEnCalidad;
   private long cMiembroId;
 
   public OCAPAsistenteOT()
   {
   }
 
   public OCAPAsistenteOT(String dNombreApellidos, String bSexo, String bEnCalidad)
   {
     this.dNombreApellidos = dNombreApellidos;
     this.bSexo = bSexo;
     this.bEnCalidad = bEnCalidad;
   }
 
   public void setDNombreApellidos(String dNombreApellidos) {
     this.dNombreApellidos = dNombreApellidos;
   }
 
   public String getDNombreApellidos()
   {
     return this.dNombreApellidos;
   }
 
   public void setBSexo(String bSexo)
   {
     this.bSexo = bSexo;
   }
 
   public String getBSexo()
   {
     return this.bSexo;
   }
 
   public void setBEnCalidad(String bEnCalidad)
   {
     this.bEnCalidad = bEnCalidad;
   }
 
   public String getBEnCalidad()
   {
     return this.bEnCalidad;
   }
 
   public void setCMiembroId(long cMiembroId)
   {
     this.cMiembroId = cMiembroId;
   }
 
   public long getCMiembroId()
   {
     return this.cMiembroId;
   }
 }

