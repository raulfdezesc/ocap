 package es.jcyl.fqs.ocap.ot.calculoCredCuestionarios;
 
 import java.io.Serializable;
 
 public class OCAPCalculoCredCuestionariosOT
   implements Serializable
 {
   protected long cCalculoId;
   protected long cCuestionarioId;
   protected Float nCreditos;
   protected float nPuntuacionMin;
   protected float nPuntuacionMax;
   protected String bBorrado;
 
   public void setCCalculoId(long cCalculoId)
   {
     this.cCalculoId = cCalculoId;
   }
 
   public long getCCalculoId()
   {
     return this.cCalculoId;
   }
 
   public void setCCuestionarioId(long cCuestionarioId)
   {
     this.cCuestionarioId = cCuestionarioId;
   }
 
   public long getCCuestionarioId()
   {
     return this.cCuestionarioId;
   }
 
   public void setNCreditos(Float nCreditos)
   {
     this.nCreditos = nCreditos;
   }
 
   public Float getNCreditos()
   {
     return this.nCreditos;
   }
 
   public void setNPuntuacionMin(float nPuntuacionMin)
   {
     this.nPuntuacionMin = nPuntuacionMin;
   }
 
   public float getNPuntuacionMin()
   {
     return this.nPuntuacionMin;
   }
 
   public void setNPuntuacionMax(float nPuntuacionMax)
   {
     this.nPuntuacionMax = nPuntuacionMax;
   }
 
   public float getNPuntuacionMax()
   {
     return this.nPuntuacionMax;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 }

