 package es.jcyl.fqs.ocap.actions.reports;
 
 import es.jcyl.fqs.ocap.error.reports.ReportException;
 import es.jcyl.fqs.ocap.util.reports.Report;
 import es.jcyl.fqs.ocap.util.reports.ot.ReportOT;
 import es.jcyl.framework.JCYLConfiguracion;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.Map;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.log4j.Logger;
 import org.apache.struts.actions.DispatchAction;
 
 public class ReportAction extends DispatchAction
 {
   public static Logger logger = JCYLConfiguracion.getLogger("Aplicacion");
 
   protected static void generateReportToPDF(HttpServletResponse pResponse, ReportOT pReportOT)
     throws ReportException
   {
     try
     {
       ServletOutputStream out = pResponse.getOutputStream();
 
       pResponse.setContentType("application/pdf");
 
       Report.reportToPDF(out, pReportOT);
 
       out.flush();
       out.close();
     }
     catch (ReportException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en PDF. Error: " + e.getMessage());
     } catch (IOException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en PDF. Error: " + e.getMessage());
     }
   }
 
   protected static void generateReportToPDFCat(HttpServletResponse pResponse, ArrayList listadoReports)
     throws ReportException
   {
     try
     {
       ServletOutputStream out = pResponse.getOutputStream();
 
       pResponse.setContentType("application/pdf");
 
       Report.reportToPDFCat(out, listadoReports);
 
       out.flush();
       out.close();
     }
     catch (ReportException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en PDF concatenado. Error: " + e.getMessage());
     } catch (IOException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en PDF concatenado. Error: " + e.getMessage());
     }
   }
 
   protected static void generateReportToPDFFile(String rutaFichero, ReportOT pReportOT)
     throws ReportException
   {
     try
     {
       String fichero = pReportOT.getRutaFicheroReportCompilado();
 
       Report.reportToPDFFile(rutaFichero, pReportOT);
     }
     catch (ReportException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo crear el report en PDF en la ruta " + rutaFichero + ". Error: " + e.getMessage());
     }
   }
 
   protected static void generateReportToPDFFileCat(String rutaFichero, ArrayList listadoReports)
     throws ReportException
   {
     try
     {
       ArrayList ficheros = new ArrayList();
 
       Report.reportToPDFFileCat(rutaFichero, listadoReports);
     }
     catch (ReportException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo crear el report en PDF en la ruta " + rutaFichero + ". Error: " + e.getMessage());
     }
   }
 
   protected static void generateReportToXLS(HttpServletResponse pResponse, ReportOT pReportOT)
     throws ReportException
   {
     try
     {
       ServletOutputStream out = pResponse.getOutputStream();
 
       pResponse.setContentType("application/vnd.ms-excel");
 
       Report.reportToXLS(out, pReportOT);
 
       out.flush();
       out.close();
     }
     catch (ReportException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en EXCEL. Error: " + e.getMessage());
     } catch (IOException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en EXCEL. Error: " + e.getMessage());
     }
   }
 
   protected static void generateReportToXLSCat(HttpServletResponse pResponse, ArrayList listadoReports)
     throws ReportException
   {
     try
     {
       ServletOutputStream out = pResponse.getOutputStream();
 
       pResponse.setContentType("application/vnd.ms-excel");
 
       Report.reportToXLSCat(out, listadoReports);
 
       out.flush();
       out.close();
     }
     catch (ReportException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en EXCEL concatenado. Error: " + e.getMessage());
     } catch (IOException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en EXCEL concatenado. Error: " + e.getMessage());
     }
   }
 
   protected static void generateReportToRTF(HttpServletResponse pResponse, ReportOT pReportOT)
     throws ReportException
   {
     try
     {
       ServletOutputStream out = pResponse.getOutputStream();
       String fichero = pReportOT.getRutaFicheroReportCompilado();
 
       pResponse.setContentType("application/rtf");
 
       Report.reportToRTF(out, pReportOT);
 
       out.flush();
       out.close();
     }
     catch (ReportException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en RTF. Error: " + e.getMessage());
     } catch (IOException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en RTF. Error: " + e.getMessage());
     }
   }
 
   protected static void generateReportToRTFCat(HttpServletResponse pResponse, ArrayList listadoReports)
     throws ReportException
   {
     try
     {
       ServletOutputStream out = pResponse.getOutputStream();
 
       pResponse.setContentType("application/rtf");
 
       Report.reportToRTFCat(out, listadoReports);
 
       out.flush();
       out.close();
     }
     catch (ReportException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en RTF concatenado. Error: " + e.getMessage());
     } catch (IOException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el reporte en RTF concatenado. Error: " + e.getMessage());
     }
   }
 
   protected static void generateReportToHTML(HttpServletResponse pResponse, ReportOT pReportOT, Map mapaImagenes)
     throws ReportException
   {
     try
     {
       ServletOutputStream out = pResponse.getOutputStream();
 
       Report.reportToHTML(out, pReportOT, mapaImagenes);
 
       out.flush();
       out.close();
     }
     catch (ReportException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en HTML. Error: " + e.getMessage());
     } catch (IOException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en HTML. Error: " + e.getMessage());
     }
   }
 
   protected static void generateReportToTexto(HttpServletResponse pResponse, ReportOT pReportOT)
     throws ReportException
   {
     try
     {
       ServletOutputStream out = pResponse.getOutputStream();
 
       Report.reportToTexto(out, pReportOT);
 
       out.flush();
       out.close();
     }
     catch (ReportException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en TXT. Error: " + e.getMessage());
     } catch (IOException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en TXT. Error: " + e.getMessage());
     }
   }
 }

