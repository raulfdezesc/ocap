 package es.jcyl.fqs.ocap.ln.areasTrabajo;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.areasTrabajo.OCAPAreasTrabajoOAD;
 import es.jcyl.fqs.ocap.ot.areasTrabajo.OCAPAreasTrabajoOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPAreasTrabajoLN
 {
   OCAPAreasTrabajoOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   public void OCAPAreasTrabajoLN()
   {
     this.variableOAD = OCAPAreasTrabajoOAD.getInstance();
   }
 
   public OCAPAreasTrabajoLN(JCYLUsuario jcylUsuario) {
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPAreasTrabajo(OCAPAreasTrabajoOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPAreasTrabajoOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.altaOCAPAreasTrabajo(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int bajaOCAPAreasTrabajo(int cAreaId)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPAreasTrabajoOAD.getInstance();
       result = this.variableOAD.bajaOCAPAreasTrabajo(cAreaId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPAreasTrabajo(OCAPAreasTrabajoOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPAreasTrabajoOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.modificacionOCAPAreasTrabajo(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public OCAPAreasTrabajoOT buscarOCAPAreasTrabajo(long cAreaId)
   {
     OCAPAreasTrabajoOT dato = null;
     try
     {
       this.variableOAD = OCAPAreasTrabajoOAD.getInstance();
       dato = this.variableOAD.buscarOCAPAreasTrabajo(cAreaId);
     }
     catch (Exception e) {
       dato = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return dato;
   }
 
   public int listadoOCAPAreasTrabajoCuenta(long cAreaId, String dNombre, String dDescripcion, String fCreacion, String fModificacion)
     throws Exception
   {
     int total = 0;
     try {
       this.variableOAD = OCAPAreasTrabajoOAD.getInstance();
       total = this.variableOAD.listadoOCAPAreasTrabajoCuenta(cAreaId, dNombre, dDescripcion, fCreacion, fModificacion);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPAreasTrabajo(long cAreaId, String dNombre, String dDescripcion, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPAreasTrabajoOAD.getInstance();
       array = this.variableOAD.consultaOCAPAreasTrabajo(cAreaId, dNombre, dDescripcion, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPAreasTrabajo()
     throws Exception
   {
     ArrayList array = new ArrayList();
     try {
       this.variableOAD = OCAPAreasTrabajoOAD.getInstance();
       array = this.variableOAD.listadoOCAPAreasTrabajo();
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPAreasTrabajo(int inicio, int cuantos)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPAreasTrabajoOAD.getInstance();
       array = this.variableOAD.listadoOCAPAreasTrabajo(inicio, cuantos);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 }

