 package es.jcyl.fqs.ocap.ln.tiposFirmante;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.tiposFirmante.OCAPTiposFirmanteOAD;
 import es.jcyl.fqs.ocap.ot.tiposFirmante.OCAPTiposFirmanteOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPTiposFirmanteLN
 {
   OCAPTiposFirmanteOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   public void OCAPTiposFirmanteLN()
   {
     this.variableOAD = OCAPTiposFirmanteOAD.getInstance();
   }
 
   public OCAPTiposFirmanteLN(JCYLUsuario jcylUsuario) {
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPTiposFirmante(OCAPTiposFirmanteOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPTiposFirmanteOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.altaOCAPTiposFirmante(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int bajaOCAPTiposFirmante(int cTipoId)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPTiposFirmanteOAD.getInstance();
       result = this.variableOAD.bajaOCAPTiposFirmante(cTipoId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPTiposFirmante(OCAPTiposFirmanteOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPTiposFirmanteOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.modificacionOCAPTiposFirmante(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public OCAPTiposFirmanteOT buscarOCAPTiposFirmante(long cTipoId)
   {
     OCAPTiposFirmanteOT dato = null;
     try
     {
       this.variableOAD = OCAPTiposFirmanteOAD.getInstance();
       dato = this.variableOAD.buscarOCAPTiposFirmante(cTipoId);
     }
     catch (Exception e) {
       dato = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return dato;
   }
 
   public int listadoOCAPTiposFirmanteCuenta(long cTipoId, String dNombre, String dObservaciones, String fCreacion, String fModificacion)
     throws Exception
   {
     int total = 0;
     try {
       this.variableOAD = OCAPTiposFirmanteOAD.getInstance();
       total = this.variableOAD.listadoOCAPTiposFirmanteCuenta(cTipoId, dNombre, dObservaciones, fCreacion, fModificacion);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPTiposFirmante(long cTipoId, String dNombre, String dObservaciones, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPTiposFirmanteOAD.getInstance();
       array = this.variableOAD.consultaOCAPTiposFirmante(cTipoId, dNombre, dObservaciones, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPTiposFirmante()
     throws Exception
   {
     ArrayList array = new ArrayList();
     try {
       this.variableOAD = OCAPTiposFirmanteOAD.getInstance();
       array = this.variableOAD.listadoOCAPTiposFirmante();
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public ArrayList buscarOCAPTiposFirmanteDeMeritocurricular(Integer cDefMeritoId, Integer cEstatutId, String dMeritoNombre, Integer cTipoId)
     throws Exception
   {
     ArrayList array = new ArrayList();
     try {
       this.variableOAD = OCAPTiposFirmanteOAD.getInstance();
       array = this.variableOAD.buscarOCAPTiposFirmanteDeMeritocurricular(cDefMeritoId, cEstatutId, dMeritoNombre, cTipoId);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 }

