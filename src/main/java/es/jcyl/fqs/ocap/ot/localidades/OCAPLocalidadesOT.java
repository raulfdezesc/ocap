 package es.jcyl.fqs.ocap.ot.localidades;
 
 import java.io.Serializable;
 
 public class OCAPLocalidadesOT
   implements Serializable
 {
   protected String CLocalidadId;
   protected String dLocalidad;
 
   public void setCLocalidadId(String CLocalidadId)
   {
     this.CLocalidadId = CLocalidadId;
   }
 
   public String getCLocalidadId()
   {
     return this.CLocalidadId;
   }
 
   public void setDLocalidad(String dLocalidad)
   {
     this.dLocalidad = dLocalidad;
   }
 
   public String getDLocalidad()
   {
     return this.dLocalidad;
   }
 }

