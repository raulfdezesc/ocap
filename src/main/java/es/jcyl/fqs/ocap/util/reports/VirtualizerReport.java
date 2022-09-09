 package es.jcyl.fqs.ocap.util.reports;
 
 import es.jcyl.fqs.ocap.error.reports.ReportException;
 import es.jcyl.fqs.ocap.util.reports.conceptos.ConceptoReport;
 import es.jcyl.fqs.ocap.util.reports.ot.ReportOT;
 import es.jcyl.framework.JCYLConfiguracion;
 import java.io.File;
 import java.util.ArrayList;
 import java.util.Hashtable;
 import java.util.List;
 import javax.servlet.ServletOutputStream;
 import net.sf.jasperreports.engine.JREmptyDataSource;
 import net.sf.jasperreports.engine.JRException;
 import net.sf.jasperreports.engine.JRExporterParameter;
 import net.sf.jasperreports.engine.JasperFillManager;
 import net.sf.jasperreports.engine.JasperPrint;
 import net.sf.jasperreports.engine.export.JExcelApiExporter;
 import net.sf.jasperreports.engine.export.JRPdfExporter;
 import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
 import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
 import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
 import net.sf.jasperreports.engine.fill.JRVirtualizationContext;
 import net.sf.jasperreports.engine.util.JRSwapFile;
 import org.apache.log4j.Logger;
 
 public class VirtualizerReport
 {
   private static final Logger logger = JCYLConfiguracion.getLogger("Aplicacion");
			
			private static JasperPrint jprint;

			public static void reportToPDF(ServletOutputStream pOut, ReportOT pReportOT)
     throws ReportException
   {
     JRSwapFileVirtualizer virtualizer = null;
     try
     {
       String nombreFichero = pReportOT.getRutaFicheroReportCompilado();
       Hashtable parametros = pReportOT.getParametrosReport();
       ConceptoReport datosReport = pReportOT.getDatosReport();
       int maxSize = pReportOT.getMaxTamanhoVirtualizer();
 
       String ruta = nombreFichero.substring(0, nombreFichero.lastIndexOf(File.separator));
       JRSwapFile swapFile = new JRSwapFile(ruta, 1024, 1024);
       virtualizer = new JRSwapFileVirtualizer(maxSize, swapFile, true);
       parametros.put("REPORT_VIRTUALIZER", virtualizer);
 
       if (datosReport.isNull())
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, datosReport);
       else {
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, new JREmptyDataSource());
       }
       JRVirtualizationContext.getRegistered(jprint).setReadOnly(true);
 
       JRPdfExporter exporter = new JRPdfExporter();
       exporter.setParameter(JRPdfExporterParameter.FORCE_LINEBREAK_POLICY, Boolean.TRUE);
       exporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
       exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, pOut);
       exporter.exportReport();
       JasperPrint jprint = null;
     }
     catch (JRException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo generar el report en PDF con Virtualizer. Error: " + e.getMessage());
     } catch (OutOfMemoryError e) {
       logger.error(e.getMessage());
       throw new ReportException("OutOfMemory: No se pudo generar el report en PDF con Virtualizer. Error: " + e.getMessage());
     } catch (Throwable e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo generar el report en PDF con Virtualizer. Error: " + e.getMessage());
     } finally {
       if (virtualizer != null)
         virtualizer.cleanup();
     }
   }
 
   public static void reportToPDFCat(ServletOutputStream out, ArrayList listadoReports)
     throws ReportException
   {
     List jasperVirtualizer = new ArrayList();
     JRSwapFileVirtualizer virtualizer = null;
     JRSwapFile swapFile = null;
     try
     {
       List jasperPrintList = new ArrayList();
 
       String ruta = null;
 
       for (int i = 0; i < listadoReports.size(); i++)
       {
         ReportOT reportOT = (ReportOT)listadoReports.get(i);
 
         String nombreFichero = reportOT.getRutaFicheroReportCompilado();
         Hashtable parametros = reportOT.getParametrosReport();
         ConceptoReport datosReport = reportOT.getDatosReport();
         int maxSize = reportOT.getMaxTamanhoVirtualizer();
 
         ruta = nombreFichero.substring(0, nombreFichero.lastIndexOf(File.separator));
         swapFile = new JRSwapFile(ruta, 1024, 1024);
         virtualizer = new JRSwapFileVirtualizer(maxSize, swapFile, true);
         parametros.put("REPORT_VIRTUALIZER", virtualizer);
 
         if (datosReport.isNull())
           jprint = JasperFillManager.fillReport(nombreFichero, parametros, datosReport);
         else {
           jprint = JasperFillManager.fillReport(nombreFichero, parametros, new JREmptyDataSource());
         }
         JRVirtualizationContext.getRegistered(jprint).setReadOnly(true);
 
         jasperPrintList.add(jprint);
         jasperVirtualizer.add(virtualizer);
 
         jprint = new JasperPrint();
       }
 
       JRPdfExporter exporter = new JRPdfExporter();
       exporter.setParameter(JRPdfExporterParameter.FORCE_LINEBREAK_POLICY, Boolean.TRUE);
       exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
       exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
       exporter.exportReport();
       JasperPrint jprint = null;
     }
     catch (JRException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo generar el report en PDF concatenado con Virtualizer. Error: " + e.getMessage());
     } catch (OutOfMemoryError e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo generar el report en PDF concatenado con Virtualizer. Error: " + e.getMessage());
     } catch (Throwable e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo generar el report en PDF concatenado con Virtualizer. Error: " + e.getMessage());
     } finally {
       for (int i = 0; i < jasperVirtualizer.size(); i++)
         if (jasperVirtualizer.get(i) != null)
           ((JRSwapFileVirtualizer)jasperVirtualizer.get(i)).cleanup();
     }
   }
 
   public static void reportToXLS(ServletOutputStream pOut, ReportOT pReportOT)
     throws ReportException
   {
     JRSwapFileVirtualizer virtualizer = null;
     try
     {
       String nombreFichero = pReportOT.getRutaFicheroReportCompilado();
       Hashtable parametros = pReportOT.getParametrosReport();
       ConceptoReport datosReport = pReportOT.getDatosReport();
       int maxSize = pReportOT.getMaxTamanhoVirtualizer();
 
       String ruta = nombreFichero.substring(0, nombreFichero.lastIndexOf(File.separator));
       JRSwapFile swapFile = new JRSwapFile(ruta, 1024, 1024);
       virtualizer = new JRSwapFileVirtualizer(maxSize, swapFile, true);
       parametros.put("REPORT_VIRTUALIZER", virtualizer);
       JasperPrint jprint;
       if (datosReport.isNull())
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, datosReport);
       else {
         jprint = JasperFillManager.fillReport(nombreFichero, parametros, new JREmptyDataSource());
       }
       JExcelApiExporter exporter = new JExcelApiExporter();
       exporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
       exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, pOut);
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
       throw new ReportException("No se pudo generar el report en EXCEL con Virtualizer. Error: " + e.getMessage());
     } catch (OutOfMemoryError e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo generar el report en EXCEL con Virtualizer. Error: " + e.getMessage());
     } catch (Throwable e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo generar el report en EXCEL con Virtualizer. Error: " + e.getMessage());
     } finally {
       if (virtualizer != null)
         virtualizer.cleanup();
     }
   }
 
   public static void reportToXLSCat(ServletOutputStream pOut, ArrayList listadoReports)
     throws ReportException
   {
     List jasperVirtualizer = new ArrayList();
     JRSwapFileVirtualizer virtualizer = null;
     JRSwapFile swapFile = null;
     try
     {
       List jasperPrintList = new ArrayList();
 
       String ruta = null;
 
       for (int i = 0; i < listadoReports.size(); i++)
       {
         ReportOT reportOT = (ReportOT)listadoReports.get(i);
 
         String nombreFichero = reportOT.getRutaFicheroReportCompilado();
         Hashtable parametros = reportOT.getParametrosReport();
         ConceptoReport datosReport = reportOT.getDatosReport();
         String hoja = reportOT.getNombreHoja();
         int maxSize = reportOT.getMaxTamanhoVirtualizer();
 
         ruta = nombreFichero.substring(0, nombreFichero.lastIndexOf(File.separator));
         swapFile = new JRSwapFile(ruta, 1024, 1024);
         virtualizer = new JRSwapFileVirtualizer(maxSize, swapFile, true);
         parametros.put("REPORT_VIRTUALIZER", virtualizer);
 
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
         JRVirtualizationContext.getRegistered(jprint).setReadOnly(true);
 
         jasperPrintList.add(jprint);
         jasperVirtualizer.add(virtualizer);
 
         jprint = new JasperPrint();
       }
 
       JExcelApiExporter exporter = new JExcelApiExporter();
       exporter.setParameter(JRPdfExporterParameter.FORCE_LINEBREAK_POLICY, Boolean.TRUE);
       exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
       exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, pOut);
       exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, new Boolean(true));
       exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, new Boolean(true));
       exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, new Boolean(false));
       exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, new Boolean(false));
       exporter.setParameter(JRXlsAbstractExporterParameter.MAXIMUM_ROWS_PER_SHEET, (Object)new Integer(0));
       exporter.setParameter(JRXlsAbstractExporterParameter.IS_DETECT_CELL_TYPE, new Boolean(true));
       exporter.exportReport();
       JasperPrint jprint = null;
     }
     catch (JRException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo generar el report concatenado en EXCEL con Virtualizer. Error: " + e.getMessage());
     } catch (OutOfMemoryError e) {
       logger.error(e.getMessage());
       throw new ReportException("OutOfMemory: No se pudo generar el report concatenado en EXCEL con Virtualizer. Error: " + e.getMessage());
     } catch (Throwable e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo generar el report concatenado en EXCEL con Virtualizer. Error: " + e.getMessage());
     } finally {
       for (int i = 0; i < jasperVirtualizer.size(); i++)
         if (jasperVirtualizer.get(i) != null)
           ((JRSwapFileVirtualizer)jasperVirtualizer.get(i)).cleanup();
     }
   }
 }

