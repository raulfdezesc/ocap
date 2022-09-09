 package es.jcyl.fqs.ocap.ot.reuniones;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPReunionesOT
   implements Serializable
 {
   protected long cCteId;
   protected Date fFecha;
   protected String dOrdendia;
   protected String dDecisiones;
   protected long nInformestotales;
   protected String aCreadoPor;
   protected long cEvaluadorId;
   protected long cReunionId;
   protected long nEvaluacionesevaluador;
   protected long nInformesrecibidos;
   protected long nInformespositivos;
   protected long nInformesnegativos;
 
   public void setCCteId(long cCteId)
   {
     this.cCteId = cCteId;
   }
 
   public long getCCteId()
   {
     return this.cCteId;
   }
 
   public void setFFecha(Date fFecha)
   {
     this.fFecha = fFecha;
   }
 
   public Date getFFecha()
   {
     return this.fFecha;
   }
 
   public void setDOrdendia(String dOrdendia)
   {
     this.dOrdendia = dOrdendia;
   }
 
   public String getDOrdendia()
   {
     return this.dOrdendia;
   }
 
   public void setDDecisiones(String dDecisiones)
   {
     this.dDecisiones = dDecisiones;
   }
 
   public String getDDecisiones()
   {
     return this.dDecisiones;
   }
 
   public void setNInformestotales(long nInformestotales)
   {
     this.nInformestotales = nInformestotales;
   }
 
   public long getNInformestotales()
   {
     return this.nInformestotales;
   }
 
   public void setACreadoPor(String aCreadoPor)
   {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 
   public void setCEvaluadorId(long cEvaluadorId)
   {
     this.cEvaluadorId = cEvaluadorId;
   }
 
   public long getCEvaluadorId()
   {
     return this.cEvaluadorId;
   }
 
   public void setCReunionId(long cReunionId)
   {
     this.cReunionId = cReunionId;
   }
 
   public long getCReunionId()
   {
     return this.cReunionId;
   }
 
   public void setNEvaluacionesevaluador(long nEvaluacionesevaluador)
   {
     this.nEvaluacionesevaluador = nEvaluacionesevaluador;
   }
 
   public long getNEvaluacionesevaluador()
   {
     return this.nEvaluacionesevaluador;
   }
 
   public void setNInformesrecibidos(long nInformesrecibidos)
   {
     this.nInformesrecibidos = nInformesrecibidos;
   }
 
   public long getNInformesrecibidos()
   {
     return this.nInformesrecibidos;
   }
 
   public void setNInformespositivos(long nInformespositivos)
   {
     this.nInformespositivos = nInformespositivos;
   }
 
   public long getNInformespositivos()
   {
     return this.nInformespositivos;
   }
 
   public void setNInformesnegativos(long nInformesnegativos)
   {
     this.nInformesnegativos = nInformesnegativos;
   }
 
   public long getNInformesnegativos()
   {
     return this.nInformesnegativos;
   }
 }

