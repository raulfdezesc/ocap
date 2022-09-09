 package es.jcyl.fqs.ocap.actions.reports;
 
 import es.jcyl.fqs.ocap.error.reports.ReportException;
 import es.jcyl.fqs.ocap.util.reports.VirtualizerReport;
 import es.jcyl.fqs.ocap.util.reports.ot.ReportOT;
 import es.jcyl.framework.JCYLConfiguracion;
 import java.io.IOException;
 import java.util.ArrayList;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.log4j.Logger;
 
 public class VirtualizerReportAction extends ReportAction
 {
   public static Logger logger = JCYLConfiguracion.getLogger("Aplicacion");
 
   protected static void generateReportToPDF(HttpServletResponse pResponse, ReportOT pReportOT)
     throws ReportException
   {
     try
     {
       ServletOutputStream out = pResponse.getOutputStream();
 
       pResponse.setContentType("application/pdf");
 
       VirtualizerReport.reportToPDF(out, pReportOT);
 
       out.flush();
       out.close();
     }
     catch (ReportException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en PDF con Virtualizer. Error: " + e.getMessage());
     }
     catch (IOException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en PDF con Virtualizer. Error: " + e.getMessage());
     }
   }
 
   protected static void generateReportToPDFCat(HttpServletResponse pResponse, ArrayList listadoReports)
     throws ReportException
   {
     try
     {
       ServletOutputStream out = pResponse.getOutputStream();
 
       pResponse.setContentType("application/pdf");
 
       VirtualizerReport.reportToPDFCat(out, listadoReports);
 
       out.flush();
       out.close();
     }
     catch (ReportException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en PDF concatenado con Virtualizer. Error: " + e.getMessage());
     }
     catch (IOException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en PDF concatenado con Virtualizer. Error: " + e.getMessage());
     }
   }
 
   protected static void generateReportToXLS(HttpServletResponse pResponse, ReportOT pReportOT)
     throws ReportException
   {
     try
     {
       ServletOutputStream out = pResponse.getOutputStream();
 
       pResponse.setContentType("application/vnd.ms-excel");
 
       VirtualizerReport.reportToXLS(out, pReportOT);
 
       out.flush();
       out.close();
     }
     catch (ReportException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en EXCEL con Virtualizer. Error: " + e.getMessage());
     }
     catch (IOException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en EXCEL con Virtualizer. Error: " + e.getMessage());
     }
   }
 
   protected static void generateReportToXLSCat(HttpServletResponse pResponse, ArrayList listadoReports)
     throws ReportException
   {
     try
     {
       ServletOutputStream out = pResponse.getOutputStream();
 
       pResponse.setContentType("application/vnd.ms-excel");
 
       VirtualizerReport.reportToXLSCat(out, listadoReports);
 
       out.flush();
       out.close();
     }
     catch (ReportException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en EXCEL concatenado con Virtualizer. Error: " + e.getMessage());
     } catch (IOException e) {
       logger.error(e.getMessage());
       throw new ReportException("No se pudo obtener el report en EXCEL concatenado con Virtualizer. Error: " + e.getMessage());
     }
   }
 }

