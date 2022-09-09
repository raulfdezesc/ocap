 package es.jcyl.fqs.ocap.ot.monitorizacion;
 
 import java.io.Serializable;
 
 public class OCAPMonitorizacionOT
   implements Serializable
 {
   protected String propietarioObjeto;
   protected String nombreObjeto;
   protected String tipoObjeto;
   protected String monitorizacion;
 
   public void setPropietarioObjeto(String propietarioObjeto)
   {
     this.propietarioObjeto = propietarioObjeto;
   }
 
   public String getPropietarioObjeto()
   {
     return this.propietarioObjeto;
   }
 
   public void setNombreObjeto(String nombreObjeto)
   {
     this.nombreObjeto = nombreObjeto;
   }
 
   public String getNombreObjeto()
   {
     return this.nombreObjeto;
   }
 
   public void setTipoObjeto(String tipoObjeto)
   {
     this.tipoObjeto = tipoObjeto;
   }
 
   public String getTipoObjeto()
   {
     return this.tipoObjeto;
   }
 
   public void setMonitorizacion(String monitorizacion)
   {
     this.monitorizacion = monitorizacion;
   }
 
   public String getMonitorizacion()
   {
     return this.monitorizacion;
   }
 }

