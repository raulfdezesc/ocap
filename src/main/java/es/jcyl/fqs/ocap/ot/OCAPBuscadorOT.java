 package es.jcyl.fqs.ocap.ot;
 
 import java.io.Serializable;
 
 public class OCAPBuscadorOT
   implements Serializable
 {
   private String campoOrdenacion;
   private String tipoOrdenacion;
   private String tipoBuscador;
   private int primerRegistro;
   private int registrosPorPagina;
 
   public String getCampoOrdenacion()
   {
     return this.campoOrdenacion;
   }
 
   public void setCampoOrdenacion(String campoOrdenacion)
   {
     this.campoOrdenacion = campoOrdenacion;
   }
 
   public String getTipoOrdenacion()
   {
     return this.tipoOrdenacion;
   }
 
   public void setTipoOrdenacion(String tipoOrdenacion)
   {
     this.tipoOrdenacion = tipoOrdenacion;
   }
 
   public String getTipoBuscador()
   {
     return this.tipoBuscador;
   }
 
   public void setTipoBuscador(String tipoBuscador)
   {
     this.tipoBuscador = tipoBuscador;
   }
 
   public int getPrimerRegistro()
   {
     return this.primerRegistro;
   }
 
   public void setPrimerRegistro(int primerRegistro)
   {
     this.primerRegistro = primerRegistro;
   }
 
   public int getRegistrosPorPagina()
   {
     return this.registrosPorPagina;
   }
 
   public void setRegistrosPorPagina(int registrosPorPagina)
   {
     this.registrosPorPagina = registrosPorPagina;
   }
 }

