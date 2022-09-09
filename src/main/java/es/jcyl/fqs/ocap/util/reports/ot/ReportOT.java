 package es.jcyl.fqs.ocap.util.reports.ot;
 
 import es.jcyl.fqs.ocap.util.reports.IConstantes;
 import es.jcyl.fqs.ocap.util.reports.conceptos.ConceptoReport;
 import java.io.File;
 import java.util.Hashtable;
 
 public class ReportOT
   implements IConstantes
 {
   private String rutaBase;
   private String rutaRelativaFicheroReport;
   private String nombreFicheroReport;
   private String rutaFicheroReportFuente;
   private String rutaFicheroReportCompilado;
   private int maxTamanhoVirtualizer;
   private Hashtable parametrosReport;
   private ConceptoReport datosReport;
   private String nombreHoja;
 
   private void $init$()
   {
     this.maxTamanhoVirtualizer = 10;
   }
 
   public ReportOT(String rutaBase, String rutaRelativaFicheroReport, String nombreFicheroReport)
   {
     $init$();
 
     this.rutaBase = rutaBase;
     this.rutaRelativaFicheroReport = rutaRelativaFicheroReport;
     this.nombreFicheroReport = nombreFicheroReport;
 
     StringBuffer rutaFuente = new StringBuffer();
     rutaFuente.append(this.rutaBase).append(File.separator).append("reports").append(File.separator).append("fuentes").append(this.rutaRelativaFicheroReport).append(this.nombreFicheroReport).append(".jrxml");
 
     StringBuffer rutaCompilado = new StringBuffer();
     rutaCompilado.append(this.rutaBase).append(File.separator).append("reports").append(File.separator).append("compilados").append(this.rutaRelativaFicheroReport).append(this.nombreFicheroReport).append(".jasper");
 
     this.rutaFicheroReportFuente = rutaFuente.toString();
     this.rutaFicheroReportCompilado = rutaCompilado.toString();
   }
 
   public String getRutaBase()
   {
     return this.rutaBase;
   }
 
   public void setRutaBase(String pRutaBase)
   {
     this.rutaBase = pRutaBase;
   }
 
   public String getRutaRelativaFicheroReport()
   {
     return this.rutaRelativaFicheroReport;
   }
 
   public void setRutaRelativaFicheroReport(String pRutaRelativaFicheroReport)
   {
     this.rutaRelativaFicheroReport = pRutaRelativaFicheroReport;
   }
 
   public String getNombreFicheroReport()
   {
     return this.nombreFicheroReport;
   }
 
   public void setNombreFicheroReport(String pNombreFicheroReport)
   {
     this.nombreFicheroReport = pNombreFicheroReport;
   }
 
   public String getRutaFicheroReportFuente()
   {
     return this.rutaFicheroReportFuente;
   }
 
   public void setRutaFicheroReportFuente(String pRutaFicheroReportFuente)
   {
     this.rutaFicheroReportFuente = pRutaFicheroReportFuente;
   }
 
   public String getRutaFicheroReportCompilado()
   {
     return this.rutaFicheroReportCompilado;
   }
 
   public void setRutaFicheroReportCompilado(String pRutaFicheroReportCompilado)
   {
     this.rutaFicheroReportCompilado = pRutaFicheroReportCompilado;
   }
 
   public int getMaxTamanhoVirtualizer()
   {
     return this.maxTamanhoVirtualizer;
   }
 
   public void setMaxTamanhoVirtualizer(int pMaxTamanhoVirtualizer)
   {
     this.maxTamanhoVirtualizer = pMaxTamanhoVirtualizer;
   }
 
   public Hashtable getParametrosReport()
   {
     return this.parametrosReport;
   }
 
   public void setParametrosReport(Hashtable parametrosReport)
   {
     this.parametrosReport = parametrosReport;
   }
 
   public ConceptoReport getDatosReport()
   {
     return this.datosReport;
   }
 
   public void setDatosReport(ConceptoReport datosReport)
   {
     this.datosReport = datosReport;
   }
 
   public String getNombreHoja()
   {
     return this.nombreHoja;
   }
 
   public void setNombreHoja(String nombreHoja)
   {
     this.nombreHoja = nombreHoja;
   }
 }

