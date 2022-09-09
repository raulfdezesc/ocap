 package es.jcyl.fqs.ocap.ot.cuestionarios;
 
 import java.io.Serializable;
 
 public class OCAPRepeticionesCuestionariosOT
   implements Serializable
 {
   protected long cCuestionarioId;
   protected int nRepeticion;
 
   public void setCCuestionarioId(long cCuestionarioId)
   {
     this.cCuestionarioId = cCuestionarioId;
   }
 
   public long getCCuestionarioId()
   {
     return this.cCuestionarioId;
   }
 
   public void setNRepeticion(int nRepeticion)
   {
     this.nRepeticion = nRepeticion;
   }
 
   public int getNRepeticion()
   {
     return this.nRepeticion;
   }
 }

