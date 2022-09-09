 package es.jcyl.fqs.ocap.util;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.RandomAccessFile;
 import java.io.Serializable;
 import java.nio.channels.FileChannel;
 import java.nio.channels.FileLock;
 import java.util.ArrayList;
 import java.util.Date;
 import org.apache.commons.lang.RandomStringUtils;
 import org.apache.log4j.Logger;
 
 public class Ficheros
   implements Serializable
 {
   public static Logger logger = OCAPConfigApp.logger;
   public static final String TIPO_MIME_PDF = "application/pdf";
   public static final String SEPARADOR = File.separator;
 
   public static boolean borrarFicheroTemporal(String rutaFichero, long segundosDePermanecia)
   {
     boolean ficheroProcesado = false;
     File fichero = new File(rutaFichero);
 
     if ((fichero != null) && (fichero.exists()))
     {
       long msUltimaModificacion = fichero.lastModified();
       long msAhora = System.currentTimeMillis();
       long minDiferencia = (msAhora - msUltimaModificacion) / 1000L;
 
       if (minDiferencia > segundosDePermanecia)
       {
         try
         {
           boolean ficheroBorrado = borrarFichero(rutaFichero);
           if (!ficheroBorrado) {
             logger.error("ERROR borrando fichero/directorio: " + fichero.getAbsolutePath());
           } else {
             ficheroProcesado = true;
             logger.debug("Borrado correctamente fichero/directorio " + rutaFichero + " con fecha de último acceso " + new Date(msUltimaModificacion));
           }
         }
         catch (Exception e) {
           logger.error("ERROR borrando fichero/directorio: " + fichero.getAbsolutePath() + ": " + e.getMessage());
         }
       }
       else {
         ficheroProcesado = true;
       }
 
     }
 
     return ficheroProcesado;
   }
 
   public static String crearFicheroVacio(String rutaAbsoluta, String rutaRelativa, String nombreFichero)
     throws IOException
   {
     byte[] contenido = new byte[5];
     contenido[0] = 86;
     contenido[1] = 97;
     contenido[2] = 99;
     contenido[3] = 105;
     contenido[4] = 111;
 
     return guardarFichero(rutaAbsoluta, rutaRelativa, nombreFichero, contenido);
   }
 
   public static String guardarFichero(String rutaAbsoluta, String rutaRelativa, String nombreFichero, byte[] contenido)
     throws IOException
   {
     boolean carpetaCreada = false;
     boolean ficheroCreado = false;
     File carpeta = null;
     File fichero = null;
     try
     {
       if ((!Cadenas.EsVacia(rutaAbsoluta)) && (!Cadenas.EsVacia(rutaRelativa))) {
         carpeta = new File(rutaAbsoluta + SEPARADOR + rutaRelativa);
         if (!carpeta.exists()) {
           carpeta.mkdirs();
         }
         carpetaCreada = true;
       }
 
       if ((carpetaCreada) && (!Cadenas.EsVacia(nombreFichero))) {
         fichero = new File(carpeta.getAbsolutePath() + SEPARADOR + nombreFichero);
         if (!fichero.exists()) {
           ficheroCreado = fichero.createNewFile();
         }
       }
 
       if ((ficheroCreado) && (contenido != null) && (contenido.length > 0)) {
         FileOutputStream fos = new FileOutputStream(fichero);
         fos.write(contenido);
         fos.flush();
         fos.close();
         logger.debug("Guardando fichero: " + fichero.getAbsolutePath());
       } else {
         logger.error("Error guardando fichero: " + fichero.getAbsolutePath());
       }
     }
     catch (IOException ioe) {
       logger.error("Error E/S guardando fichero: " + fichero.getAbsolutePath());
       throw ioe;
     }
 
     return fichero.getAbsolutePath();
   }
 
   public static boolean borrarFichero(String rutaFichero)
     throws FileNotFoundException, IOException
   {
     boolean ficheroBorrado = false;
     File fichero = null;
 
     if (!Cadenas.EsVacia(rutaFichero)) {
       fichero = new File(rutaFichero);
       if ((fichero != null) && (fichero.exists())) {
         if (fichero.isDirectory())
           ficheroBorrado = borrarFichero(rutaFichero, null);
         else {
           ficheroBorrado = borrarFichero(rutaFichero.substring(0, rutaFichero.lastIndexOf(SEPARADOR) + 1), rutaFichero.substring(rutaFichero.lastIndexOf(SEPARADOR) + 1));
         }
       }
     }
 
     return ficheroBorrado;
   }
 
   public static boolean borrarFichero(String nombreDirectorio, String nombreFichero)
     throws FileNotFoundException, IOException
   {
     boolean ficheroBorrado = false;
     File fichero = null;
 
     if ((!Cadenas.EsVacia(nombreDirectorio)) && (!Cadenas.EsVacia(nombreFichero)))
     {
       File directorio = new File(nombreDirectorio);
       if (directorio.exists()) {
         fichero = new File(directorio.getAbsolutePath() + SEPARADOR + nombreFichero);
       }
     }
     else if (!Cadenas.EsVacia(nombreDirectorio)) {
       fichero = new File(nombreDirectorio);
     } else if (!Cadenas.EsVacia(nombreFichero)) {
       fichero = new File(nombreFichero);
     }
 
     if ((fichero != null) && (fichero.exists())) {
       logger.debug("Borrando fichero: " + fichero);
       if (fichero.isDirectory()) {
         String[] children = fichero.list();
         for (int i = 0; i < children.length; i++) {
           boolean ficheroHijoBorrado = borrarFichero(fichero.getAbsolutePath(), children[i]);
           if (!ficheroHijoBorrado) {
             logger.debug("No se pudo borrar el fichero: " + children[i] + " del directorio " + fichero.getPath());
           }
           ficheroBorrado = (ficheroBorrado) && (ficheroHijoBorrado);
         }
       }
 
       ficheroBorrado = fichero.delete();
 
       if (!ficheroBorrado) {
         logger.debug("No se pudo borrar el fichero: " + fichero.getPath());
         try
         {
           RandomAccessFile randomAccessFile = new RandomAccessFile(fichero, "rw");
           FileChannel channel = randomAccessFile.getChannel();
           FileLock lock = channel.tryLock();
           boolean esCompartido = lock.isShared();
           if (!esCompartido) {
             ficheroBorrado = fichero.delete();
           }
 
           randomAccessFile.close();
           channel.close();
           lock.release();
           if (!ficheroBorrado)
             ficheroBorrado = fichero.delete();
           if (!ficheroBorrado)
             fichero.deleteOnExit();
         }
         catch (Exception e)
         {
           logger.debug("No se pudo bloquear y borrar el fichero: " + fichero.getPath());
           fichero.deleteOnExit();
         }
       }
     }
 
     return ficheroBorrado;
   }
 
   public static ArrayList listarDirectorio(String rutaDirectorio)
     throws FileNotFoundException, IOException
   {
     return listarDirectorio(rutaDirectorio, null);
   }
 
   public static ArrayList listarDirectorio(String rutaDirectorio, ArrayList rutasFicheros) throws FileNotFoundException, IOException
   {
     File fichero = null;
 
     if (!Cadenas.EsVacia(rutaDirectorio)) {
       fichero = new File(rutaDirectorio);
     }
     if (rutasFicheros == null) {
       rutasFicheros = new ArrayList();
     }
     if ((fichero != null) && (fichero.exists())) {
       rutasFicheros.add(fichero.getAbsolutePath());
       if (fichero.isDirectory()) {
         String[] children = fichero.list();
         for (int i = 0; i < children.length; i++) {
           listarDirectorio(fichero.getAbsolutePath() + File.separator + children[i], rutasFicheros);
         }
       }
     }
 
     return rutasFicheros;
   }
 
   public static boolean isFicheroLeido(String nombreDirectorio, String nombreArchivo)
     throws FileNotFoundException, IOException
   {
     boolean isFicheroLeido = false;
 
     if (!Cadenas.EsVacia(nombreDirectorio)) {
       File directorio = new File(nombreDirectorio);
       if (directorio.exists())
         isFicheroLeido = isFicheroLeido(directorio.getAbsolutePath() + SEPARADOR + nombreArchivo);
       else {
         logger.error("Error leyendo la carpeta: " + nombreDirectorio);
       }
     }
 
     return isFicheroLeido;
   }
 
   public static boolean isFicheroLeido(String rutaFichero)
     throws FileNotFoundException, IOException
   {
     boolean isFicheroLeido = false;
 
     if (!Cadenas.EsVacia(rutaFichero)) {
       File fichero = new File(rutaFichero);
       if (fichero.exists()) {
         byte[] contenido = leerFichero(fichero.getAbsolutePath());
         if ((contenido != null) && (contenido.length > 0)) {
           isFicheroLeido = true;
           logger.debug("Es leido el fichero: " + rutaFichero);
         }
       } else {
         logger.error("Error leyendo el fichero: " + rutaFichero);
       }
     }
 
     return isFicheroLeido;
   }
 
   public static byte[] leerFichero(String nombreDirectorio, String nombreArchivo)
     throws FileNotFoundException, IOException
   {
     byte[] contenido = new byte[0];
     File directorio = null;
 
     if (!Cadenas.EsVacia(nombreDirectorio)) {
       directorio = new File(nombreDirectorio);
       if (directorio.exists())
         contenido = leerFichero(directorio.getAbsolutePath() + SEPARADOR + nombreArchivo);
       else {
         logger.error("Error leyendo la carpeta: " + nombreDirectorio);
       }
     }
 
     return contenido;
   }
 
   public static byte[] leerFichero(String rutaFichero)
     throws FileNotFoundException, IOException
   {
     byte[] contenido = new byte[0];
     File fichero = null;
     try
     {
       if (!Cadenas.EsVacia(rutaFichero))
       {
         fichero = new File(rutaFichero);
         if (fichero.exists()) {
           FileInputStream fis = new FileInputStream(fichero);
           long longitud = fichero.length();
           if (longitud > 2147483647L)
             throw new IOException("No se pudo leer completamente el archivo " + fichero.getName());
           if (contenido.length != longitud) {
             contenido = new byte[(int)longitud];
           }
 
           int offset = 0;
           int numRead = 0;
           while ((offset < contenido.length) && ((numRead = fis.read(contenido, offset, contenido.length - offset)) >= 0)) {
             offset += numRead;
           }
 
           if (offset < contenido.length) {
             throw new IOException("No se pudo leer completamente el archivo " + fichero.getName());
           }
 
           fis.close();
         }
       }
     }
     catch (FileNotFoundException fnfe)
     {
       logger.error("Error FileNotFoundException leyendo el fichero: " + fnfe.getMessage());
       throw fnfe;
     } catch (IOException ioe) {
       logger.error("Error IOException leyendo el fichero: " + ioe.getMessage());
       throw ioe;
     }
 
     return contenido;
   }
 
   public static boolean renombrarFichero(String rutaFichero, String nuevoNombre)
   {
     boolean ficheroRenombrado = false;
     File fichero = null;
 
     if (!Cadenas.EsVacia(rutaFichero)) {
       fichero = new File(rutaFichero);
       if (fichero.exists()) {
         String rutaNueva = rutaFichero.substring(0, rutaFichero.lastIndexOf(SEPARADOR) + 1) + nuevoNombre;
         ficheroRenombrado = fichero.renameTo(new File(rutaNueva));
       }
     }
 
     return ficheroRenombrado;
   }
 
   public static long tamanoFichero(String rutaFichero)
   {
     long tamanoTotal = 0L;
     File fichero = null;
 
     if (!Cadenas.EsVacia(rutaFichero)) {
       fichero = new File(rutaFichero);
       if ((fichero != null) && (fichero.exists())) {
         if (fichero.isFile()) {
           tamanoTotal += fichero.length();
         } else if (fichero.isDirectory()) {
           String[] ficherosIncluidos = fichero.list();
           int numFicherosIncluidos = ficherosIncluidos != null ? ficherosIncluidos.length : 0;
           for (int i = 0; i < numFicherosIncluidos; i++) {
             tamanoTotal += tamanoFichero(fichero.getAbsolutePath() + SEPARADOR + ficherosIncluidos[i]);
           }
         }
       }
     }
 
     return tamanoTotal;
   }
 
   public static boolean esDirectorioVacio(String rutaDirectorio)
   {
     boolean directorioVacio = false;
     if (!Cadenas.EsVacia(rutaDirectorio)) {
       long tamDirectorio = tamanoFichero(rutaDirectorio);
       directorioVacio = tamDirectorio == 0L;
     }
 
     return directorioVacio;
   }
 
   public static String generarNombreAleatorioParaFichero(String nombreFichero)
   {
     String nuevoNombreFichero = null;
     nuevoNombreFichero = RandomStringUtils.randomAlphabetic(16) + nombreFichero.substring(nombreFichero.lastIndexOf("."), nombreFichero.length());
 
     return nuevoNombreFichero;
   }
 
   public static int validarPDF(byte[] contenido)
   {
     int resultado = 0;
 
     if (contenido != null) {
       if ((contenido[0] != 37) || (contenido[1] != 80) || (contenido[2] != 68) || (contenido[3] != 70))
         resultado = 0;
       else {
         resultado = 1;
       }
     }
 
     return resultado;
   }
 
   public static String obtenerContentType(String extension)
   {
     String resultado = null;
     if (extension != null)
     {
       StringBuffer contentType = new StringBuffer();
       if ((extension.equalsIgnoreCase("gif")) || (extension.equalsIgnoreCase("jpeg")) || (extension.equalsIgnoreCase("tiff")) || (extension.equalsIgnoreCase("ief")) || (extension.equalsIgnoreCase("rgb")))
       {
         contentType.append("image/").append(extension);
       } else if (extension.equalsIgnoreCase("jpg"))
         contentType.append("image/jpeg");
       else if (extension.equalsIgnoreCase("ico"))
         contentType.append("image/x-icon");
       else if (extension.equalsIgnoreCase("png"))
         contentType.append("image/x-png");
       else if (extension.equalsIgnoreCase("bmp"))
         contentType.append("image/x-ms-bmp");
       else if (extension.equalsIgnoreCase("doc"))
         contentType.append("application/msword");
       else if (extension.equalsIgnoreCase("docx"))
         contentType.append("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
       else if (extension.equalsIgnoreCase("rtf"))
         contentType.append("text/rtf");
       else if (extension.equalsIgnoreCase("xlsx"))
         contentType.append("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
       else if ((extension.equalsIgnoreCase("xls")) || (extension.equalsIgnoreCase("xlc")) || (extension.equalsIgnoreCase("xll")) || (extension.equalsIgnoreCase("xlm")) || (extension.equalsIgnoreCase("xlw")) || (extension.equalsIgnoreCase("xla")) || (extension.equalsIgnoreCase("xlt")) || (extension.equalsIgnoreCase("xld")))
       {
         contentType.append("application/vnd.ms-excel");
       } else if ((extension.equalsIgnoreCase("htm")) || (extension.equalsIgnoreCase("html")))
         contentType.append("text/html");
       else if ((extension.equalsIgnoreCase("txt")) || (extension.equalsIgnoreCase("c")) || (extension.equalsIgnoreCase("c++")) || (extension.equalsIgnoreCase("pl")) || (extension.equalsIgnoreCase("cc")) || (extension.equalsIgnoreCase("h")) || (extension.equalsIgnoreCase("java")))
       {
         contentType.append("text/plain");
       } else if (extension.equalsIgnoreCase("css"))
         contentType.append("text/css");
       else if (extension.equalsIgnoreCase("pdf"))
         contentType.append("application/pdf");
       else if (extension.equalsIgnoreCase("gtar"))
         contentType.append("application/x-gtar");
       else if (extension.equalsIgnoreCase("tar"))
         contentType.append("application/x-tar");
       else if (extension.equalsIgnoreCase("zip"))
         contentType.append("application/zip");
       else if (extension.equalsIgnoreCase("exe"))
         contentType.append("application/octet-stream");
       else if (extension.equalsIgnoreCase("js"))
         contentType.append("application/x-javascript");
       else if ((extension.equalsIgnoreCase("ppz")) || (extension.equalsIgnoreCase("ppt")) || (extension.equalsIgnoreCase("pps")) || (extension.equalsIgnoreCase("pot")))
         contentType.append("application/vnd.ms-powerpoint");
       else if ((extension.equalsIgnoreCase("xhtml")) || (extension.equalsIgnoreCase("xht")))
         contentType.append("application/xhtml+xml");
       else if (extension.equalsIgnoreCase("odt"))
         contentType.append("application/vnd.oasis.opendocument.text");
       else if (extension.equalsIgnoreCase("ott"))
         contentType.append("application/vnd.oasis.opendocument.text-template");
       else if (extension.equalsIgnoreCase("odg"))
         contentType.append("application/vnd.oasis.opendocument.graphics");
       else if (extension.equalsIgnoreCase("otg"))
         contentType.append("application/vnd.oasis.opendocument.graphics-template");
       else if (extension.equalsIgnoreCase("odp"))
         contentType.append("application/vnd.oasis.opendocument.presentation");
       else if (extension.equalsIgnoreCase("otp"))
         contentType.append("application/vnd.oasis.opendocument.presentation-template");
       else if (extension.equalsIgnoreCase("ods"))
         contentType.append("application/vnd.oasis.opendocument.spreadsheet");
       else if (extension.equalsIgnoreCase("ots"))
         contentType.append("application/vnd.oasis.opendocument.spreadsheet-template");
       else if (extension.equalsIgnoreCase("odc"))
         contentType.append("application/vnd.oasis.opendocument.chart");
       else if (extension.equalsIgnoreCase("otc"))
         contentType.append("application/vnd.oasis.opendocument.chart-template");
       else if (extension.equalsIgnoreCase("odi"))
         contentType.append("application/vnd.oasis.opendocument.image");
       else if (extension.equalsIgnoreCase("oti"))
         contentType.append("application/vnd.oasis.opendocument.image-template");
       else if (extension.equalsIgnoreCase("odf"))
         contentType.append("application/vnd.oasis.opendocument.formula");
       else if (extension.equalsIgnoreCase("otf"))
         contentType.append("application/vnd.oasis.opendocument.formula-template");
       else if (extension.equalsIgnoreCase("odm"))
         contentType.append("application/vnd.oasis.opendocument.text-master");
       else if (extension.equalsIgnoreCase("oth"))
         contentType.append("application/vnd.oasis.opendocument.text-web");
       else if (extension.equalsIgnoreCase("bin"))
         contentType.append("application/octet-stream");
       else if (extension.equalsIgnoreCase("hqx"))
         contentType.append("application/mac-binhex40");
       else if (extension.equalsIgnoreCase("cpt"))
         contentType.append("application/mac-compactpro");
       else if (extension.equalsIgnoreCase("rss"))
         contentType.append("application/rss+xml");
       else if (extension.equalsIgnoreCase("xml"))
         contentType.append("application/xml");
       else if (extension.equalsIgnoreCase("wbxml"))
         contentType.append("application/vnd.wap.wbxml");
       else if (extension.equalsIgnoreCase("wmlc"))
         contentType.append("application/vnd.wap.wmlc");
       else if (extension.equalsIgnoreCase("wmlsc"))
         contentType.append("application/vnd.wap.wmlscriptc");
       else if (extension.equalsIgnoreCase("wmls"))
         contentType.append("text/vnd.wap.wmlscript");
       else if ((extension.equalsIgnoreCase("tbz")) || (extension.equalsIgnoreCase("tbz2")))
         contentType.append("application/x-bzip-compressed-tar");
       else if (extension.equalsIgnoreCase("vcd"))
         contentType.append("application/x-cdlink");
       else if ((extension.equalsIgnoreCase("dcr")) || (extension.equalsIgnoreCase("dir")) || (extension.equalsIgnoreCase("dxr")))
         contentType.append("application/x-director");
       else if (extension.equalsIgnoreCase("spl"))
         contentType.append("application/x-futuresplash");
       else if ((extension.equalsIgnoreCase("skp")) || (extension.equalsIgnoreCase("skd")) || (extension.equalsIgnoreCase("skt")) || (extension.equalsIgnoreCase("skm")))
         contentType.append("application/x-koan");
       else if (extension.equalsIgnoreCase("latex"))
         contentType.append("application/x-latex");
       else if (extension.equalsIgnoreCase("kar"))
         contentType.append("audio/midi");
       else if (extension.equalsIgnoreCase("wbmp"))
         contentType.append("image/vnd.wap.wbmp");
       else if ((extension.equalsIgnoreCase("igs")) || (extension.equalsIgnoreCase("iges")))
         contentType.append("model/iges");
       else if ((extension.equalsIgnoreCase("msh")) || (extension.equalsIgnoreCase("mesh")) || (extension.equalsIgnoreCase("silo")))
         contentType.append("model/mesh");
       else if (extension.equalsIgnoreCase("vrml"))
         contentType.append("model/vrml");
       else if (extension.equalsIgnoreCase("mxu"))
         contentType.append("video/vnd.mpegurl");
       else if (extension.equalsIgnoreCase("ice"))
         contentType.append("x-conference/x-cooltalk");
       else if (extension.equalsIgnoreCase("swf"))
         contentType.append("application/x-shockwave-flash");
       else if (extension.equalsIgnoreCase("wpd"))
         contentType.append("application/wordperfect");
       else if (extension.equalsIgnoreCase("wiki"))
         contentType.append("text/x-wiki");
       else if (extension.equalsIgnoreCase("sxc"))
         contentType.append("application/vnd.sun.xml.calc");
       else if (extension.equalsIgnoreCase("csv"))
         contentType.append("text/csv");
       else if (extension.equalsIgnoreCase("tsv"))
         contentType.append("text/tab-separated-values");
       else if (extension.equalsIgnoreCase("sxi"))
         contentType.append("application/vnd.sun.xml.impress");
       else if (extension.equalsIgnoreCase("svg"))
         contentType.append("image/svg+xml");
       else {
         contentType.append("application/octet-stream");
       }
       resultado = contentType.toString();
     }
 
     return resultado;
   }
 }

