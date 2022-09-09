 package es.jcyl.fqs.ocap.util.reports;
 
 import es.jcyl.fqs.ocap.util.reports.ot.ReportOT;
 import java.io.File;
 import java.util.LinkedList;
 
 public class UtilidadesReports
 {
   private static void buscarFicheros(String rutaInicial, String rutaRelativaFichero, LinkedList listaFicheros, boolean busquedaRecursiva, String rutaBase)
     throws Exception
   {
     try
     {
       File directorioInicial = new File(rutaInicial);
 
       if (directorioInicial.isDirectory())
       {
         File[] ficheros = directorioInicial.listFiles();
 
         for (int i = 0; i < ficheros.length; i++)
         {
           if ((ficheros[i].isDirectory()) && (!"CVS".equals(ficheros[i].getName())) && (!".svn".equals(ficheros[i].getName())) && (!"imagenes".equals(ficheros[i].getName())) && (!"compilados".equals(ficheros[i].getName())) && (busquedaRecursiva))
           {
             String rutaRelativa = "";
             if ((!rutaRelativaFichero.equals("")) && (!"fuentes".equals(rutaRelativaFichero)))
             {
               rutaRelativa = rutaRelativa + rutaRelativaFichero + File.separator + ficheros[i].getName();
             }
             else rutaRelativa = rutaRelativa + ficheros[i].getName();
 
             buscarFicheros(ficheros[i].getAbsolutePath(), rutaRelativa, listaFicheros, busquedaRecursiva, rutaBase);
           }
           else if ((ficheros[i].isFile()) && (ficheros[i].getName().endsWith(".jrxml")))
           {
             ReportOT reportOT = null;
 
             int posicion = ficheros[i].getName().lastIndexOf(".");
             if (posicion > -1) {
               reportOT = new ReportOT(rutaBase.substring(0, rutaBase.indexOf("reports")), !rutaRelativaFichero.equals("fuentes") ? File.separator + rutaRelativaFichero + File.separator : File.separator, ficheros[i].getName().substring(0, posicion));
 
               listaFicheros.add(reportOT);
             }
           }
         }
       }
     } catch (Exception e) {
       throw e;
     }
   }
 
   public static void compilarFicheros(String pathRaiz)
     throws Exception
   {
     try
     {
       LinkedList listaFicheros = new LinkedList();
       String rutaBase = pathRaiz + File.separator + "reports" + File.separator;
       buscarFicheros(rutaBase, "", listaFicheros, true, rutaBase);
       Report report = new Report();
       for (int i = 0; i < listaFicheros.size(); i++) {
         Report.compilar((ReportOT)listaFicheros.get(i));
       }
     }
     catch (Exception e)
     {
       throw e;
     }
   }
 }

