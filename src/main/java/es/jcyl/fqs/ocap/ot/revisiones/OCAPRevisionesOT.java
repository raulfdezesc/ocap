 package es.jcyl.fqs.ocap.ot.revisiones;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPRevisionesOT
   implements Serializable
 {
   protected long cRevisionId;
   protected Long cExpId;
   protected long cCompFqsId;
   protected Date fInicioEval;
   protected String aModificadoPor;
   protected String bAuditoria;
   protected String bAuditoriaAE;
   protected String bAuditoriaAA;
   protected String aMotivoAuditoria;
   protected String aLugarRealizacion;
   protected Date fAuditoria;
   protected String fAuditoriaPDF;
   protected Date fAuditoriaPropuesta;
   protected String aSuperior;
   protected String aHallazgos;
   protected String bCumplimiento;
   protected String bCumplimientoY;
   protected String bCumplimientoN;
   protected String bCategorizacion;
   protected String bCategorizacionN;
   protected String bCategorizacionD;
   protected String bCategorizacionO;
   protected String aRecomendaciones;
   protected String aConclusiones;
   protected Date fInforme;
   protected String fInformePDF;
   protected String dNombreEvaluador;
   protected String codigoId;
   protected String bAutoEvaluacion;
   protected String b2Evaluacion;
   protected String bAnalisis;
   protected String aResultados;
 
   public void setCRevisionId(long cRevisionId)
   {
     this.cRevisionId = cRevisionId;
   }
 
   public long getCRevisionId()
   {
     return this.cRevisionId;
   }
 
   public void setCCompFqsId(long cCompFqsId)
   {
     this.cCompFqsId = cCompFqsId;
   }
 
   public long getCCompFqsId()
   {
     return this.cCompFqsId;
   }
 
   public void setFInicioEval(Date fInicioEval)
   {
     this.fInicioEval = fInicioEval;
   }
 
   public Date getFInicioEval()
   {
     return this.fInicioEval;
   }
 
   public void setCExpId(Long cExpId)
   {
     this.cExpId = cExpId;
   }
 
   public Long getCExpId()
   {
     return this.cExpId;
   }
 
   public void setAModificadoPor(String aModificadoPor)
   {
     this.aModificadoPor = aModificadoPor;
   }
 
   public String getAModificadoPor()
   {
     return this.aModificadoPor;
   }
 
   public void setBAuditoria(String bAuditoria)
   {
     this.bAuditoria = bAuditoria;
   }
 
   public String getBAuditoria()
   {
     return this.bAuditoria;
   }
 
   public void setAMotivoAuditoria(String aMotivoAuditoria)
   {
     this.aMotivoAuditoria = aMotivoAuditoria;
   }
 
   public String getAMotivoAuditoria()
   {
     return this.aMotivoAuditoria;
   }
 
   public void setALugarRealizacion(String aLugarRealizacion)
   {
     this.aLugarRealizacion = aLugarRealizacion;
   }
 
   public String getALugarRealizacion()
   {
     return this.aLugarRealizacion;
   }
 
   public void setFAuditoria(Date fAuditoria)
   {
     this.fAuditoria = fAuditoria;
   }
 
   public Date getFAuditoria()
   {
     return this.fAuditoria;
   }
 
   public void setASuperior(String aSuperior)
   {
     this.aSuperior = aSuperior;
   }
 
   public String getASuperior()
   {
     return this.aSuperior;
   }
 
   public void setAHallazgos(String aHallazgos)
   {
     this.aHallazgos = aHallazgos;
   }
 
   public String getAHallazgos()
   {
     return this.aHallazgos;
   }
 
   public void setBCumplimiento(String bCumplimiento)
   {
     this.bCumplimiento = bCumplimiento;
   }
 
   public String getBCumplimiento()
   {
     return this.bCumplimiento;
   }
 
   public void setBCategorizacion(String bCategorizacion)
   {
     this.bCategorizacion = bCategorizacion;
   }
 
   public String getBCategorizacion()
   {
     return this.bCategorizacion;
   }
 
   public void setARecomendaciones(String aRecomendaciones)
   {
     this.aRecomendaciones = aRecomendaciones;
   }
 
   public String getARecomendaciones()
   {
     return this.aRecomendaciones;
   }
 
   public void setAConclusiones(String aConclusiones)
   {
     this.aConclusiones = aConclusiones;
   }
 
   public String getAConclusiones()
   {
     return this.aConclusiones;
   }
 
   public void setFInforme(Date fInforme)
   {
     this.fInforme = fInforme;
   }
 
   public Date getFInforme()
   {
     return this.fInforme;
   }
 
   public void setDNombreEvaluador(String dNombreEvaluador)
   {
     this.dNombreEvaluador = dNombreEvaluador;
   }
 
   public String getDNombreEvaluador()
   {
     return this.dNombreEvaluador;
   }
 
   public void setCodigoId(String codigoId)
   {
     this.codigoId = codigoId;
   }
 
   public String getCodigoId()
   {
     return this.codigoId;
   }
 
   public void setBAuditoriaAE(String bAuditoriaAE)
   {
     this.bAuditoriaAE = bAuditoriaAE;
   }
 
   public String getBAuditoriaAE()
   {
     return this.bAuditoriaAE;
   }
 
   public void setBAuditoriaAA(String bAuditoriaAA)
   {
     this.bAuditoriaAA = bAuditoriaAA;
   }
 
   public String getBAuditoriaAA()
   {
     return this.bAuditoriaAA;
   }
 
   public void setBCumplimientoY(String bCumplimientoY)
   {
     this.bCumplimientoY = bCumplimientoY;
   }
 
   public String getBCumplimientoY()
   {
     return this.bCumplimientoY;
   }
 
   public void setBCumplimientoN(String bCumplimientoN)
   {
     this.bCumplimientoN = bCumplimientoN;
   }
 
   public String getBCumplimientoN()
   {
     return this.bCumplimientoN;
   }
 
   public void setBCategorizacionN(String bCategorizacionN)
   {
     this.bCategorizacionN = bCategorizacionN;
   }
 
   public String getBCategorizacionN()
   {
     return this.bCategorizacionN;
   }
 
   public void setBCategorizacionD(String bCategorizacionD)
   {
     this.bCategorizacionD = bCategorizacionD;
   }
 
   public String getBCategorizacionD()
   {
     return this.bCategorizacionD;
   }
 
   public void setBCategorizacionO(String bCategorizacionO)
   {
     this.bCategorizacionO = bCategorizacionO;
   }
 
   public String getBCategorizacionO()
   {
     return this.bCategorizacionO;
   }
 
   public void setFInformePDF(String fInformePDF)
   {
     this.fInformePDF = fInformePDF;
   }
 
   public String getFInformePDF()
   {
     return this.fInformePDF;
   }
 
   public void setFAuditoriaPDF(String fAuditoriaPDF)
   {
     this.fAuditoriaPDF = fAuditoriaPDF;
   }
 
   public String getFAuditoriaPDF()
   {
     return this.fAuditoriaPDF;
   }
 
   public void setFAuditoriaPropuesta(Date fAuditoriaPropuesta)
   {
     this.fAuditoriaPropuesta = fAuditoriaPropuesta;
   }
 
   public Date getFAuditoriaPropuesta()
   {
     return this.fAuditoriaPropuesta;
   }
 
   public void setBAutoEvaluacion(String bAutoEvaluacion)
   {
     this.bAutoEvaluacion = bAutoEvaluacion;
   }
 
   public String getBAutoEvaluacion()
   {
     return this.bAutoEvaluacion;
   }
 
   public void setB2Evaluacion(String b2Evaluacion)
   {
     this.b2Evaluacion = b2Evaluacion;
   }
 
   public String getB2Evaluacion()
   {
     return this.b2Evaluacion;
   }
 
   public void setBAnalisis(String bAnalisis)
   {
     this.bAnalisis = bAnalisis;
   }
 
   public String getBAnalisis()
   {
     return this.bAnalisis;
   }
 
   public void setAResultados(String aResultados)
   {
     this.aResultados = aResultados;
   }
 
   public String getAResultados()
   {
     return this.aResultados;
   }
 }

