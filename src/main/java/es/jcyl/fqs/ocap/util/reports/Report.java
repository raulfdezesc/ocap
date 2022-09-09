 package es.jcyl.fqs.ocap.util.reports;
 
 import es.jcyl.fqs.ocap.error.reports.ReportException;
 import es.jcyl.fqs.ocap.util.reports.conceptos.ConceptoReport;
 import es.jcyl.fqs.ocap.util.reports.ot.ReportOT;
 import es.jcyl.framework.JCYLConfiguracion;
 import java.io.File;
 import java.util.ArrayList;
 import java.util.Hashtable;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.ServletOutputStream;
 import net.sf.jasperreports.engine.JREmptyDataSource;
 import net.sf.jasperreports.engine.JRException;
 import net.sf.jasperreports.engine.JRExporterParameter;
 import net.sf.jasperreports.engine.JasperCompileManager;
 import net.sf.jasperreports.engine.JasperExportManager;
 import net.sf.jasperreports.engine.JasperFillManager;
 import net.sf.jasperreports.engine.JasperPrint;
 import net.sf.jasperreports.engine.export.JExcelApiExporter;
 import net.sf.jasperreports.engine.export.JRHtmlExporter;
 import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
 import net.sf.jasperreports.engine.export.JRPdfExporter;
 import net.sf.jasperreports.engine.export.JRRtfExporter;
 import net.sf.jasperreports.engine.export.JRTextExporter;
 import net.sf.jasperreports.engine.export.JRTextExporterParameter;
 import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
 import org.apache.log4j.Logger;
 
 public class Report
   implements IConstantes
 {
   private static final Logger logger = JCYLConfiguracion.getLogger("Aplicacion");
 
   public static void reportToPDFFile(String rutaFicheroCreado, ReportOT pReportOT)
     throws ReportException
   {
     try
     {
       String nombreFichero = pReportOT.getRutaFicheroReportCompilado();
       Hashtable parametros = pReportOT.getParametrosReport();
       ConceptoReport datosReport = pReportOT.getDatosReport();
       JasperPrint jprint;
       if (datosReport.isNull())
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, datosReport);
       else {
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, new JREmptyDataSource());
       }
       JasperExportManager.exportReportToPdfFile(jprint, rutaFicheroCreado);
     }
     catch (JRException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en PDF File. Error: " + e.getMessage());
     } catch (OutOfMemoryError e) {
       logger.error(e.getMessage());
       throw new ReportException("OutOfMemory: No se pudo obtener el report en PDF File. Error: " + e.getMessage());
     } catch (Throwable e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en PDF File. Error: " + e.getMessage());
     }
   }
 
   public static void reportToPDFFileCat(String rutaFicheroCreado, ArrayList listadoReports)
     throws ReportException
   {
     try
     {
       List listaJPrint = new ArrayList();
 
       for (int i = 0; i < listadoReports.size(); i++)
       {
         ReportOT reportOT = (ReportOT)listadoReports.get(i);
         JasperPrint jprint = new JasperPrint();
 
         String nombreFichero = reportOT.getRutaFicheroReportCompilado();
         Hashtable parametros = reportOT.getParametrosReport();
         ConceptoReport datosReport = reportOT.getDatosReport();
 
         if (datosReport.isNull())
           jprint = JasperFillManager.fillReport(nombreFichero, parametros, datosReport);
         else {
           jprint = JasperFillManager.fillReport(nombreFichero, parametros, new JREmptyDataSource());
         }
         listaJPrint.add(jprint);
       }
 
       JRPdfExporter exporter = new JRPdfExporter();
       exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, listaJPrint);
       exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, rutaFicheroCreado);
       exporter.exportReport();
     }
     catch (JRException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en PDF File concatenado. Error: " + e.getMessage());
     } catch (OutOfMemoryError e) {
       logger.error(e.getMessage());
       throw new ReportException("OutOfMemory: No se pudo obtener el reporte en PDF File concatenado. Error: " + e.getMessage());
     } catch (Throwable e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en PDF File concatenado. Error: " + e.getMessage());
     }
   }
 
   public static void reportToPDF(ServletOutputStream flujoSalida, ReportOT pReportOT)
     throws ReportException
   {
     try
     {
       String nombreFichero = pReportOT.getRutaFicheroReportCompilado();
       Hashtable parametros = pReportOT.getParametrosReport();
       ConceptoReport datosReport = pReportOT.getDatosReport();
       JasperPrint jprint;
       if ((datosReport != null) && (datosReport.isNull()))
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, datosReport);
       else {
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, new JREmptyDataSource());
       }
       JasperExportManager.exportReportToPdfStream(jprint, flujoSalida);
     }
     catch (JRException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en PDF. Error: " + e.getMessage());
     } catch (OutOfMemoryError e) {
       logger.error(e.getMessage());
       throw new ReportException("OutOfMemory: No se pudo obtener el reporte en PDF Error: " + e.getMessage());
     } catch (Throwable e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en PDF. Error: " + e.getMessage());
     }
   }
 
   public static void reportToPDFCat(ServletOutputStream flujoSalida, ArrayList listadoReports)
     throws ReportException
   {
     try
     {
       List listaJPrint = new ArrayList();
 
       for (int i = 0; i < listadoReports.size(); i++)
       {
         ReportOT reportOT = (ReportOT)listadoReports.get(i);
         JasperPrint jprint = new JasperPrint();
 
         String nombreFichero = reportOT.getRutaFicheroReportCompilado();
         Hashtable parametros = reportOT.getParametrosReport();
         ConceptoReport datosReport = reportOT.getDatosReport();
 
         if ((datosReport != null) && (datosReport.isNull()))
           jprint = JasperFillManager.fillReport(nombreFichero, parametros, datosReport);
         else {
           jprint = JasperFillManager.fillReport(nombreFichero, parametros, new JREmptyDataSource());
         }
         listaJPrint.add(jprint);
       }
 
       JRPdfExporter exporter = new JRPdfExporter();
       exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, listaJPrint);
       exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, flujoSalida);
       exporter.exportReport();
     }
     catch (JRException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en PDF concatenado. Error: " + e.getMessage());
     } catch (OutOfMemoryError e) {
       logger.error(e.getMessage());
       throw new ReportException("OutOfMemory: No se pudo obtener el reporte en PDF concatenado Error: " + e.getMessage());
     } catch (Throwable e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en PDF concatenado. Error: " + e.getMessage());
     }
   }
 
   public static void reportToXLS(ServletOutputStream flujoSalida, ReportOT pReportOT)
     throws ReportException
   {
     try
     {
       String nombreFichero = pReportOT.getRutaFicheroReportCompilado();
       Hashtable parametros = pReportOT.getParametrosReport();
       ConceptoReport datosReport = pReportOT.getDatosReport();
       JasperPrint jprint;
       if (datosReport.isNull())
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, datosReport);
       else {
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, new JREmptyDataSource());
       }
       JExcelApiExporter exporter = new JExcelApiExporter();
       exporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
       exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, flujoSalida);
       exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, new Boolean(true));
       exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, new Boolean(false));
       exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, new Boolean(true));
       exporter.setParameter(JRXlsAbstractExporterParameter.IS_DETECT_CELL_TYPE, new Boolean(true));
       exporter.exportReport();
     }
     catch (JRException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en XLS. Error: " + e.getMessage());
     } catch (OutOfMemoryError e) {
       logger.error(e.getMessage());
       throw new ReportException("OutOfMemory: No se pudo obtener el reporte en XLS Error: " + e.getMessage());
     } catch (Throwable e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en XLS. Error: " + e.getMessage());
     }
   }
 
   public static void reportToXLSCat(ServletOutputStream flujoSalida, ArrayList listadoReports)
     throws ReportException
   {
     try
     {
       List listaJPrint = new ArrayList();
 
       for (int i = 0; i < listadoReports.size(); i++)
       {
         ReportOT reportOT = (ReportOT)listadoReports.get(i);
 		  JasperPrint jprint = new JasperPrint();
         String nombreFichero = reportOT.getRutaFicheroReportCompilado();
         Hashtable parametros = reportOT.getParametrosReport();
         ConceptoReport datosReport = reportOT.getDatosReport();
         String hoja = reportOT.getNombreHoja();
 
         if (datosReport.isNull())
           jprint = JasperFillManager.fillReport(nombreFichero, parametros, datosReport);
         else {
           jprint = JasperFillManager.fillReport(nombreFichero, parametros, new JREmptyDataSource());
         }
         if ((hoja != null) && (!hoja.trim().equals("")))
           jprint.setName(hoja);
         else {
           jprint.setName("Informe " + i);
         }
         listaJPrint.add(jprint);
       }
 
       JExcelApiExporter exporter = new JExcelApiExporter();
       exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, listaJPrint);
       exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, flujoSalida);
       exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, new Boolean(true));
       exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, new Boolean(true));
       exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, new Boolean(false));
       exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, new Boolean(false));
       exporter.setParameter(JRXlsAbstractExporterParameter.MAXIMUM_ROWS_PER_SHEET, (Object)new Integer(0));
       exporter.setParameter(JRXlsAbstractExporterParameter.IS_DETECT_CELL_TYPE, new Boolean(true));
       exporter.exportReport();
     }
     catch (JRException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en XLS concatenado. Error: " + e.getMessage());
     } catch (OutOfMemoryError e) {
       logger.error(e.getMessage());
       throw new ReportException("OutOfMemory: No se pudo obtener el reporte en XLS concatenado Error: " + e.getMessage());
     } catch (Throwable e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en XLS concatenado. Error: " + e.getMessage());
     }
   }
 
   public static void reportToRTF(ServletOutputStream flujoSalida, ReportOT pReportOT)
     throws ReportException
   {
     try
     {
       String nombreFichero = pReportOT.getRutaFicheroReportCompilado();
       Hashtable parametros = pReportOT.getParametrosReport();
       ConceptoReport datosReport = pReportOT.getDatosReport();
       JasperPrint jprint;
       if (datosReport.isNull())
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, datosReport);
       else {
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, new JREmptyDataSource());
       }
       JRRtfExporter exporter = new JRRtfExporter();
       exporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
       exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, nombreFichero);
       exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, flujoSalida);
       exporter.exportReport();
     }
     catch (JRException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en RTF. Error: " + e.getMessage());
     } catch (OutOfMemoryError e) {
       logger.error(e.getMessage());
       throw new ReportException("OutOfMemory: No se pudo obtener el reporte en RTF Error: " + e.getMessage());
     } catch (Throwable e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en RTF. Error: " + e.getMessage());
     }
   }
 
   public static void reportToRTFCat(ServletOutputStream flujoSalida, ArrayList listadoReports)
     throws ReportException
   {
     try
     {
       List listaJPrint = new ArrayList();
 
       for (int i = 0; i < listadoReports.size(); i++)
       {
         ReportOT reportOT = (ReportOT)listadoReports.get(i);
         JasperPrint jprint = new JasperPrint();
 
         String nombreFichero = reportOT.getRutaFicheroReportCompilado();
         Hashtable parametros = reportOT.getParametrosReport();
         ConceptoReport datosReport = reportOT.getDatosReport();
 
         if (datosReport.isNull())
           jprint = JasperFillManager.fillReport(nombreFichero, parametros, datosReport);
         else {
           jprint = JasperFillManager.fillReport(nombreFichero, parametros, new JREmptyDataSource());
         }
         listaJPrint.add(jprint);
       }
 
       JRRtfExporter exporter = new JRRtfExporter();
       exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, listaJPrint);
       exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, flujoSalida);
       exporter.exportReport();
     }
     catch (JRException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en RTF concatenado. Error: " + e.getMessage());
     } catch (OutOfMemoryError e) {
       logger.error(e.getMessage());
       throw new ReportException("OutOfMemory: No se pudo obtener el reporte en RTF concatenado Error: " + e.getMessage());
     } catch (Throwable e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en RTF concatenado. Error: " + e.getMessage());
     }
   }
 
   public static byte[] reportToPDFToBytes(ReportOT pReportOT)
     throws ReportException
   {
     byte[] bytes = null;
     try
     {
       String nombreFichero = pReportOT.getRutaFicheroReportCompilado();
       Hashtable parametros = pReportOT.getParametrosReport();
       ConceptoReport datosReport = pReportOT.getDatosReport();
       JasperPrint jprint;
       if (datosReport.isNull())
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, datosReport);
       else {
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, new JREmptyDataSource());
       }
       bytes = JasperExportManager.exportReportToPdf(jprint);
     }
     catch (JRException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en PDF a Bytes . Error: " + e.getMessage());
     } catch (OutOfMemoryError e) {
       logger.error(e.getMessage());
       throw new ReportException("OutOfMemory: No se pudo obtener el reporte en PDF a Bytes Error: " + e.getMessage());
     } catch (Throwable e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en PDF a Bytes. Error: " + e.getMessage());
     }
 
     return bytes;
   }
 
   public static void reportToHTML(ServletOutputStream flujoSalida, ReportOT pReportOT, Map mapaImagenes)
     throws ReportException
   {
     try
     {
       String nombreFichero = pReportOT.getRutaFicheroReportCompilado();
       Hashtable parametros = pReportOT.getParametrosReport();
       ConceptoReport datosReport = pReportOT.getDatosReport();
       JasperPrint jprint;
       if (datosReport.isNull())
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, datosReport);
       else {
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, new JREmptyDataSource());
       }
       JRHtmlExporter exporter = new JRHtmlExporter();
       exporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
       exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, flujoSalida);
       exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, mapaImagenes);
       exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image?image=");
       exporter.exportReport();
     }
     catch (JRException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en HTML. Error: " + e.getMessage());
     } catch (OutOfMemoryError e) {
       logger.error(e.getMessage());
       throw new ReportException("OutOfMemory: No se pudo obtener el report en HTML Error: " + e.getMessage());
     } catch (Throwable e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en HTML. Error: " + e.getMessage());
     }
   }
 
   public static void reportToTexto(ServletOutputStream flujoSalida, ReportOT pReportOT)
     throws ReportException
   {
     try
     {
       String nombreFichero = pReportOT.getRutaFicheroReportCompilado();
       Hashtable parametros = pReportOT.getParametrosReport();
       ConceptoReport datosReport = pReportOT.getDatosReport();
       JasperPrint jprint;
       if (datosReport.isNull())
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, datosReport);
       else {
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, new JREmptyDataSource());
       }
       JRTextExporter texto = new JRTextExporter();
       texto.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
       texto.setParameter(JRExporterParameter.OUTPUT_STREAM, flujoSalida);
       texto.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(10));
       texto.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(10));
       texto.exportReport();
     }
     catch (JRException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en formato texto. Error: " + e.getMessage());
     } catch (OutOfMemoryError e) {
       logger.error(e.getMessage());
       throw new ReportException("OutOfMemory: No se pudo obtener el reporte en formato texto Error: " + e.getMessage());
     } catch (Throwable e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en formato texto. Error: " + e.getMessage());
     }
   }
 
   public static void compilar(ReportOT reportOT)
     throws ReportException
   {
     File ficheroFuente = new File(reportOT.getRutaFicheroReportFuente());
 
     if (!ficheroFuente.exists()) {
       throw new ReportException("No se ha encontrado el archivo fuente para el report: " + ficheroFuente.getAbsolutePath());
     }
     File ficheroCompilado = new File(reportOT.getRutaFicheroReportCompilado());
 
     if ((!ficheroCompilado.exists()) || (ficheroFuente.lastModified() > ficheroCompilado.lastModified())) {
       try {
         JasperCompileManager.compileReportToFile(ficheroFuente.getPath(), ficheroCompilado.getPath());
       } catch (JRException e) {
         logger.error(e.getMessage());
         throw new ReportException("Error al compilar el report " + ficheroFuente.getAbsolutePath() + "\n " + e.getMessage());
       }
     }
 
     if (!ficheroCompilado.exists())
       throw new ReportException("No se ha encontrado el archivo compilado para el report: " + ficheroCompilado.getAbsolutePath());
   }
 }

