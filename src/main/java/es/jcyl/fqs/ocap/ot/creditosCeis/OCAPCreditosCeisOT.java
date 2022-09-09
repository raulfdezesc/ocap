 package es.jcyl.fqs.ocap.ot.creditosCeis;
 
 import java.io.Serializable;
 
 public class OCAPCreditosCeisOT
   implements Serializable
 {
   protected long cCCeisId;
   protected long cDefMeritoId;
   protected long cExpId;
   protected float nCreditosValidados;
   protected float nCreditos;
   protected float nCreditosNecesarios;
   protected String dDefMeritoNombre;
   protected String bBorrado;
   protected String aCreadoPor;
   protected String aModificadoPor;
 
   public void setCCCeisId(long cCCeisId)
   {
     this.cCCeisId = cCCeisId;
   }
 
   public long getCCCeisId()
   {
     return this.cCCeisId;
   }
 
   public void setCDefMeritoId(long cDefMeritoId)
   {
     this.cDefMeritoId = cDefMeritoId;
   }
 
   public long getCDefMeritoId()
   {
     return this.cDefMeritoId;
   }
 
   public void setCExpId(long cExpId)
   {
     this.cExpId = cExpId;
   }
 
   public long getCExpId()
   {
     return this.cExpId;
   }
 
   public void setNCreditos(float nCreditos)
   {
     this.nCreditos = nCreditos;
   }
 
   public float getNCreditos()
   {
     return this.nCreditos;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setNCreditosNecesarios(float nCreditosNecesarios)
   {
     this.nCreditosNecesarios = nCreditosNecesarios;
   }
 
   public float getNCreditosNecesarios()
   {
     return this.nCreditosNecesarios;
   }
 
   public void setDDefMeritoNombre(String dDefMeritoNombre)
   {
     this.dDefMeritoNombre = dDefMeritoNombre;
   }
 
   public String getDDefMeritoNombre()
   {
     return this.dDefMeritoNombre;
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
 
   public void setNCreditosValidados(float nCreditosValidados)
   {
     this.nCreditosValidados = nCreditosValidados;
   }
 
   public float getNCreditosValidados()
   {
     return this.nCreditosValidados;
   }
 }

