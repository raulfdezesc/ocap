 package es.jcyl.fqs.ocap.ot.creditosCuestionarios;
 
 import java.io.Serializable;
 
 public class OCAPCreditosCuestionariosOT
   implements Serializable
 {
   protected long cCCuestionarioId;
   protected long cCuestionarioId;
   protected long cPadreEvidenciaId;
   protected int nEvidencia;
   protected long cExpId;
   protected float nCreditos;
   protected float nPuntuacion;
   protected int nRepeticion;
   protected String bBorrado;
   protected String aCreadoPor;
   protected String aModificadoPor;
   protected String dCuestionario;
   protected float nCreditosPosibles;
   protected float nCreditosEvaluador;
   protected float nCreditosFinales;
   protected String BAcuerdo;
   protected String BProposicion;
   protected String ARazon;
   protected String AComentarios;
   protected boolean bSimulado;
   protected String BAcuerdo2;
   protected String ARazon2;
   protected String AComentarios2;
   protected String bAuditoria;
   protected String aTipoRegistro;
   protected String aDocumento;
   protected String aNHistoria1;
   protected String aNHistoria2;
   protected String aNHistoria3;
 
   public void setCCCuestionariosId(long cCCuestionariosId)
   {
     this.cCCuestionarioId = cCCuestionariosId;
   }
 
   public long getCCCuestionariosId()
   {
     return this.cCCuestionarioId;
   }
 
   public void setCCuestionarioId(long cCuestionarioId)
   {
     this.cCuestionarioId = cCuestionarioId;
   }
 
   public long getCCuestionarioId()
   {
     return this.cCuestionarioId;
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
 
   public void setNRepeticion(int nRepeticion)
   {
     this.nRepeticion = nRepeticion;
   }
 
   public int getNRepeticion()
   {
     return this.nRepeticion;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setNPuntuacion(float nPuntuacion)
   {
     this.nPuntuacion = nPuntuacion;
   }
 
   public float getNPuntuacion()
   {
     return this.nPuntuacion;
   }
 
   public void setACreadoPor(String aCreadoPor)
   {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 
   public void setDCuestionario(String dCuestionario)
   {
     this.dCuestionario = dCuestionario;
   }
 
   public String getDCuestionario()
   {
     return this.dCuestionario;
   }
 
   public void setNCreditosPosibles(float nCreditosPosibles)
   {
     this.nCreditosPosibles = nCreditosPosibles;
   }
 
   public float getNCreditosPosibles()
   {
     return this.nCreditosPosibles;
   }
 
   public void setNCreditosEvaluador(float nCreditosEvaluador)
   {
     this.nCreditosEvaluador = nCreditosEvaluador;
   }
 
   public float getNCreditosEvaluador()
   {
     return this.nCreditosEvaluador;
   }
 
   public void setBAcuerdo(String BAcuerdo)
   {
     this.BAcuerdo = BAcuerdo;
   }
 
   public String getBAcuerdo()
   {
     return this.BAcuerdo;
   }
 
   public void setBProposicion(String BProposicion)
   {
     this.BProposicion = BProposicion;
   }
 
   public String getBProposicion()
   {
     return this.BProposicion;
   }
 
   public void setARazon(String ARazon)
   {
     this.ARazon = ARazon;
   }
 
   public String getARazon()
   {
     return this.ARazon;
   }
 
   public void setAComentarios(String AComentarios)
   {
     this.AComentarios = AComentarios;
   }
 
   public String getAComentarios()
   {
     return this.AComentarios;
   }
 
   public void setAModificadoPor(String aModificadoPor)
   {
     this.aModificadoPor = aModificadoPor;
   }
 
   public String getAModificadoPor()
   {
     return this.aModificadoPor;
   }
 
   public void setBSimulado(boolean bSimulado)
   {
     this.bSimulado = bSimulado;
   }
 
   public boolean getBSimulado()
   {
     return this.bSimulado;
   }
 
   public void setBAcuerdo2(String BAcuerdo2)
   {
     this.BAcuerdo2 = BAcuerdo2;
   }
 
   public String getBAcuerdo2()
   {
     return this.BAcuerdo2;
   }
 
   public void setARazon2(String ARazon2)
   {
     this.ARazon2 = ARazon2;
   }
 
   public String getARazon2()
   {
     return this.ARazon2;
   }
 
   public void setAComentarios2(String AComentarios2)
   {
     this.AComentarios2 = AComentarios2;
   }
 
   public String getAComentarios2()
   {
     return this.AComentarios2;
   }
 
   public void setATipoRegistro(String aTipoRegistro)
   {
     this.aTipoRegistro = aTipoRegistro;
   }
 
   public String getATipoRegistro()
   {
     return this.aTipoRegistro;
   }
 
   public void setADocumento(String aDocumento)
   {
     this.aDocumento = aDocumento;
   }
 
   public String getADocumento()
   {
     return this.aDocumento;
   }
 
   public void setANHistoria1(String aNHistoria1)
   {
     this.aNHistoria1 = aNHistoria1;
   }
 
   public String getANHistoria1()
   {
     return this.aNHistoria1;
   }
 
   public void setANHistoria2(String aNHistoria2)
   {
     this.aNHistoria2 = aNHistoria2;
   }
 
   public String getANHistoria2()
   {
     return this.aNHistoria2;
   }
 
   public void setANHistoria3(String aNHistoria3)
   {
     this.aNHistoria3 = aNHistoria3;
   }
 
   public String getANHistoria3()
   {
     return this.aNHistoria3;
   }
 
   public void setBAuditoria(String bAuditoria)
   {
     this.bAuditoria = bAuditoria;
   }
 
   public String getBAuditoria()
   {
     return this.bAuditoria;
   }
 
   public void setNCreditosFinales(float nCreditosFinales)
   {
     this.nCreditosFinales = nCreditosFinales;
   }
 
   public float getNCreditosFinales()
   {
     return this.nCreditosFinales;
   }
 
   public void setCPadreEvidenciaId(long cPadreEvidenciaId)
   {
     this.cPadreEvidenciaId = cPadreEvidenciaId;
   }
 
   public long getCPadreEvidenciaId()
   {
     return this.cPadreEvidenciaId;
   }
 
   public void setNEvidencia(int nEvidencia)
   {
     this.nEvidencia = nEvidencia;
   }
 
   public int getNEvidencia()
   {
     return this.nEvidencia;
   }
 }

