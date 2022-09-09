 package es.jcyl.fqs.ocap.ot.estados;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPEstadosOT
   implements Serializable
 {
   protected String bBorrado;
   protected long cEstadoId;
   protected String dNombre;
   protected Date fUsumodi;
   protected Date fUsualta;
   protected String dDescripcion;
   protected String cUsualta;
   protected String cUsumodi;
   protected String dFase;
   protected String bListado;
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public long getcEstadoId()
   {
     return this.cEstadoId;
   }
 
   public void setcEstadoId(long cEstadoId)
   {
     this.cEstadoId = cEstadoId;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public Date getFUsumodi()
   {
     return this.fUsumodi;
   }
 
   public void setFUsumodi(Date fUsumodi)
   {
     this.fUsumodi = fUsumodi;
   }
 
   public Date getFUsualta()
   {
     return this.fUsualta;
   }
 
   public void setFUsualta(Date fUsualta)
   {
     this.fUsualta = fUsualta;
   }
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
   }
 
   public void setDDescripcion(String dDescripcion)
   {
     this.dDescripcion = dDescripcion;
   }
 
   public void setCUsualta(String cUsualta)
   {
     this.cUsualta = cUsualta;
   }
 
   public String getCUsualta()
   {
     return this.cUsualta;
   }
 
   public void setCUsumodi(String cUsumodi)
   {
     this.cUsumodi = cUsumodi;
   }
 
   public String getCUsumodi()
   {
     return this.cUsumodi;
   }
 
   public void setDFase(String dFase)
   {
     this.dFase = dFase;
   }
 
   public String getDFase()
   {
     return this.dFase;
   }
 
   public void setBListado(String bListado)
   {
     this.bListado = bListado;
   }
 
   public String getBListado()
   {
     return this.bListado;
   }
 }

