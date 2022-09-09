 package es.jcyl.fqs.ocap.ot.creditos;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPCreditosOT
   implements Serializable
 {
   protected double nCreditos;
   protected Float nCreditosConseguidos;
   protected Float nCreditosValidados;
   protected Float nCreditosValidadosCeis;
   protected Float nCreditosValidadosCc;
   protected long cEstatutId;
   protected long cCreditoId;
   protected String bBorrado;
   protected Date fModificacion;
   protected String aCreadoPor;
   protected String aModificadoPor;
   protected long cDefmeritoId;
   protected String dMerito;
   protected String dDefMeritoDesc;
   protected String bMeritoSeleccionado;
   protected Date fCreacion;
   protected long cGradoId;
   protected String dGrado;
   protected String dEstatutNombre;
   protected String dDefmeritoNombre;
   protected String dGradoNombre;
 
   public double getNCreditos()
   {
     return this.nCreditos;
   }
 
   public void setNCreditos(double nCreditos)
   {
     this.nCreditos = nCreditos;
   }
 
   public long getCEstatutId()
   {
     return this.cEstatutId;
   }
 
   public void setCEstatutId(long cEstatutId)
   {
     this.cEstatutId = cEstatutId;
   }
 
   public long getCCreditoId()
   {
     return this.cCreditoId;
   }
 
   public void setCCreditoId(long cCreditoId)
   {
     this.cCreditoId = cCreditoId;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public Date getFModificacion()
   {
     return this.fModificacion;
   }
 
   public void setFModificacion(Date fModificacion)
   {
     this.fModificacion = fModificacion;
   }
 
   public long getCDefmeritoId()
   {
     return this.cDefmeritoId;
   }
 
   public void setCDefmeritoId(long cDefmeritoId)
   {
     this.cDefmeritoId = cDefmeritoId;
   }
 
   public Date getFCreacion()
   {
     return this.fCreacion;
   }
 
   public void setFCreacion(Date fCreacion)
   {
     this.fCreacion = fCreacion;
   }
 
   public long getCGradoId()
   {
     return this.cGradoId;
   }
 
   public void setCGradoId(long cGradoId)
   {
     this.cGradoId = cGradoId;
   }
 
   public void setDEstatutNombre(String dEstatutNombre)
   {
     this.dEstatutNombre = dEstatutNombre;
   }
 
   public String getDEstatutNombre()
   {
     return this.dEstatutNombre;
   }
 
   public void setDDefmeritoNombre(String dDefmeritoNombre)
   {
     this.dDefmeritoNombre = dDefmeritoNombre;
   }
 
   public String getDDefmeritoNombre()
   {
     return this.dDefmeritoNombre;
   }
 
   public void setDGradoNombre(String dGradoNombre)
   {
     this.dGradoNombre = dGradoNombre;
   }
 
   public String getDGradoNombre()
   {
     return this.dGradoNombre;
   }
 
   public void setDMerito(String dMerito)
   {
     this.dMerito = dMerito;
   }
 
   public String getDMerito()
   {
     return this.dMerito;
   }
 
   public void setDDefMeritoDesc(String dDefMeritoDesc)
   {
     this.dDefMeritoDesc = dDefMeritoDesc;
   }
 
   public String getDDefMeritoDesc()
   {
     return this.dDefMeritoDesc;
   }
 
   public void setBMeritoSeleccionado(String bMeritoSeleccionado)
   {
     this.bMeritoSeleccionado = bMeritoSeleccionado;
   }
 
   public String getBMeritoSeleccionado()
   {
     return this.bMeritoSeleccionado;
   }
 
   public void setNCreditosConseguidos(Float nCreditosConseguidos)
   {
     this.nCreditosConseguidos = nCreditosConseguidos;
   }
 
   public Float getNCreditosConseguidos()
   {
     return this.nCreditosConseguidos;
   }
 
   public void setDGrado(String dGrado)
   {
     this.dGrado = dGrado;
   }
 
   public String getDGrado()
   {
     return this.dGrado;
   }
 
   public void setACreadoPor(String aCreadoPor)
   {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 
   public void setAModificadoPor(String aModificadoPor)
   {
     this.aModificadoPor = aModificadoPor;
   }
 
   public String getAModificadoPor()
   {
     return this.aModificadoPor;
   }
 
   public void setNCreditosValidados(Float nCreditosValidados)
   {
     this.nCreditosValidados = nCreditosValidados;
   }
 
   public Float getNCreditosValidados()
   {
     return this.nCreditosValidados;
   }
 
   public void setNCreditosValidadosCeis(Float nCreditosValidadosCeis)
   {
     this.nCreditosValidadosCeis = nCreditosValidadosCeis;
   }
 
   public Float getNCreditosValidadosCeis()
   {
     return this.nCreditosValidadosCeis;
   }
 
   public void setNCreditosValidadosCc(Float nCreditosValidadosCc)
   {
     this.nCreditosValidadosCc = nCreditosValidadosCc;
   }
 
   public Float getNCreditosValidadosCc()
   {
     return this.nCreditosValidadosCc;
   }
 }

