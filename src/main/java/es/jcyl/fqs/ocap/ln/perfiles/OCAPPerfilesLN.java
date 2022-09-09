 package es.jcyl.fqs.ocap.ln.perfiles;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.perfiles.OCAPPerfilesOAD;
 import es.jcyl.fqs.ocap.ot.perfiles.OCAPPerfilesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPPerfilesLN
 {
   OCAPPerfilesOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   public void OCAPPerfilesLN()
   {
     this.variableOAD = OCAPPerfilesOAD.getInstance();
   }
 
   public OCAPPerfilesLN(JCYLUsuario jcylUsuario) {
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPPerfiles(OCAPPerfilesOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPPerfilesOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.altaOCAPPerfiles(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int bajaOCAPPerfiles(long cPerfilId)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPPerfilesOAD.getInstance();
       result = this.variableOAD.bajaOCAPPerfiles(cPerfilId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPPerfiles(OCAPPerfilesOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPPerfilesOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.modificacionOCAPPerfiles(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public OCAPPerfilesOT buscarOCAPPerfiles(long cPerfilId)
   {
     OCAPPerfilesOT dato = null;
     try
     {
       this.variableOAD = OCAPPerfilesOAD.getInstance();
       dato = this.variableOAD.buscarOCAPPerfiles(cPerfilId);
     }
     catch (Exception e) {
       dato = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return dato;
   }
 
   public int listadoOCAPPerfilesCuenta(long cPerfilId, String dNombre, String dDescripcion, String fCreacion, String fModificacion)
     throws Exception
   {
     int total = 0;
     try {
       this.variableOAD = OCAPPerfilesOAD.getInstance();
       total = this.variableOAD.listadoOCAPPerfilesCuenta(cPerfilId, dNombre, dDescripcion, fCreacion, fModificacion);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPPerfiles(long cPerfilId, String dNombre, String dDescripcion, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPPerfilesOAD.getInstance();
       array = this.variableOAD.consultaOCAPPerfiles(cPerfilId, dNombre, dDescripcion, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPPerfiles()
     throws Exception
   {
     ArrayList array = new ArrayList();
     try {
       this.variableOAD = OCAPPerfilesOAD.getInstance();
       array = this.variableOAD.listadoOCAPPerfiles();
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPPerfiles(int inicio, int cuantos)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPPerfilesOAD.getInstance();
       array = this.variableOAD.listadoOCAPPerfiles(inicio, cuantos);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 }

