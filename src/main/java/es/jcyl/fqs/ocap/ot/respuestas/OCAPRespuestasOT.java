 package es.jcyl.fqs.ocap.ot.respuestas;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPRespuestasOT
   implements Serializable
 {
   protected String bBorrado;
   protected long cCuestionarioId;
   protected long cPreguntaId;
   protected long cRespuestaId;
   protected float nCreditos;
   protected String dValor;
   protected String dNombre;
   protected String bOpcional;
   protected Date fModificacion;
   protected Date fCreacion;
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setCCuestionarioId(long cCuestionarioId)
   {
     this.cCuestionarioId = cCuestionarioId;
   }
 
   public long getCCuestionarioId()
   {
     return this.cCuestionarioId;
   }
 
   public void setCPreguntaId(long cPreguntaId)
   {
     this.cPreguntaId = cPreguntaId;
   }
 
   public long getCPreguntaId()
   {
     return this.cPreguntaId;
   }
 
   public void setCRespuestaId(long cRespuestaId)
   {
     this.cRespuestaId = cRespuestaId;
   }
 
   public long getCRespuestaId()
   {
     return this.cRespuestaId;
   }
 
   public void setNCreditos(float nCreditos)
   {
     this.nCreditos = nCreditos;
   }
 
   public float getNCreditos()
   {
     return this.nCreditos;
   }
 
   public void setDValor(String dValor)
   {
     this.dValor = dValor;
   }
 
   public String getDValor()
   {
     return this.dValor;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setFModificacion(Date fModificacion)
   {
     this.fModificacion = fModificacion;
   }
 
   public Date getFModificacion()
   {
     return this.fModificacion;
   }
 
   public void setFCreacion(Date fCreacion)
   {
     this.fCreacion = fCreacion;
   }
 
   public Date getFCreacion()
   {
     return this.fCreacion;
   }
 
   public void setBOpcional(String bOpcional)
   {
     this.bOpcional = bOpcional;
   }
 
   public String getBOpcional()
   {
     return this.bOpcional;
   }
 }

