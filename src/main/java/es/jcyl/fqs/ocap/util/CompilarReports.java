 package es.jcyl.fqs.ocap.util;
 
 import es.jcyl.fqs.ocap.util.reports.Report;
 import es.jcyl.fqs.ocap.util.reports.ot.ReportOT;
 import java.io.File;
 import java.io.PrintStream;
 
 public class CompilarReports
 {
   private static String path;
 
   public static void main(String[] args)
   {
     try
     {
       System.out.println("======== INICIO COMPILAR JASPER REPORTS ====== ");
       path = "web";
 
       Report.compilar(new ReportOT("web", File.separator, "actaAclaraciones"));
 
       System.out.println("======== FINAL COMPILAR JASPER REPORTS ====== ");
     }
     catch (Exception e) {
       System.out.println(e.toString());
     }
   }
 }

