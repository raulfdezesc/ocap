 package es.jcyl.framework.ot;
 
 import java.util.Date;
 
 public class JCYLIntentosFallidosOT
 {
   private int nIntento;
   private Date fechaIntento;
   private Date fechaFinBloqueo;
 
   public JCYLIntentosFallidosOT(int nIntento)
   {
     this.nIntento = nIntento;
     this.fechaIntento = new Date();
     this.fechaFinBloqueo = null;
   }
 
   public String toString()
   {
     String s = new String();
     if (this.fechaFinBloqueo == null)
       s = " Intento = " + this.nIntento + " Fecha = " + this.fechaIntento.toString();
     else {
       s = " Intentos =" + this.nIntento + " Bloqueado hasta = " + this.fechaFinBloqueo.toString();
     }
     return s;
   }
 
   public Date getFechaFinBloqueo()
   {
     return this.fechaFinBloqueo;
   }
 
   public void setFechaFinBloqueo(Date fechaFinBloqueo)
   {
     this.fechaFinBloqueo = fechaFinBloqueo;
   }
 
   public Date getFechaIntento()
   {
     return this.fechaIntento;
   }
 
   public void setFechaIntento(Date fechaIntento)
   {
     this.fechaIntento = fechaIntento;
   }
 
   public int getNIntento()
   {
     return this.nIntento;
   }
 
   public void setNIntento(int nIntento)
   {
     this.nIntento = nIntento;
   }
 }

