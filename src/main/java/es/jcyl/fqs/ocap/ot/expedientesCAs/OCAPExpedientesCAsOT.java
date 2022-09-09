 package es.jcyl.fqs.ocap.ot.expedientesCAs;
 
 import java.io.InputStream;
 import java.io.Serializable;
 
 public class OCAPExpedientesCAsOT
   implements Serializable
 {
   protected long cExpId;
   protected long cCuestionarioId;
   protected long cPreguntaId;
   protected long cExpCaId;
   protected int nRepeticion;
   protected String dValor;
   protected String aCreadoPor;
   protected long cPadreEvidenciaId;
   protected InputStream aRespuestaescaneada;
 
   public void setCExpId(long cExpId)
   {
     this.cExpId = cExpId;
   }
 
   public long getCExpId()
   {
     return this.cExpId;
   }
 
   public void setCCuestionarioId(long cCuestionarioId)
   {
     this.cCuestionarioId = cCuestionarioId;
   }
 
   public long getCCuestionarioId()
   {
     return this.cCuestionarioId;
   }
 
   public void setCExpCaId(long cExpCaId)
   {
     this.cExpCaId = cExpCaId;
   }
 
   public long getCExpCaId()
   {
     return this.cExpCaId;
   }
 
   public void setDValor(String dValor)
   {
     this.dValor = dValor;
   }
 
   public String getDValor()
   {
     return this.dValor;
   }
 
   public void setCPreguntaId(long cPreguntaId)
   {
     this.cPreguntaId = cPreguntaId;
   }
 
   public long getCPreguntaId()
   {
     return this.cPreguntaId;
   }
 
   public void setNRepeticion(int nRepeticion)
   {
     this.nRepeticion = nRepeticion;
   }
 
   public int getNRepeticion()
   {
     return this.nRepeticion;
   }
 
   public void setACreadoPor(String aCreadoPor)
   {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 
   public void setCPadreEvidenciaId(long cPadreEvidenciaId)
   {
     this.cPadreEvidenciaId = cPadreEvidenciaId;
   }
 
   public long getCPadreEvidenciaId()
   {
     return this.cPadreEvidenciaId;
   }
 
   public void setARespuestaescaneada(InputStream aRespuestaescaneada)
   {
     this.aRespuestaescaneada = aRespuestaescaneada;
   }
 
   public InputStream getARespuestaescaneada()
   {
     return this.aRespuestaescaneada;
   }
 }

