 package es.jcyl.fqs.ocap.ln.areas;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.areas.OCAPAreasOAD;
 import es.jcyl.fqs.ocap.ot.areas.OCAPAreasOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 
 public class OCAPAreasLN
 {
   OCAPAreasOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   public   OCAPAreasLN()
   {
     this.variableOAD = OCAPAreasOAD.getInstance();
   }
 
   public OCAPAreasLN(JCYLUsuario jcylUsuario) {
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPAreas(OCAPAreasOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPAreasOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.altaOCAPAreas(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int bajaOCAPAreas(int cAreaId)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPAreasOAD.getInstance();
       result = this.variableOAD.bajaOCAPAreas(cAreaId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPAreas(OCAPAreasOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPAreasOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.modificacionOCAPAreas(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public OCAPAreasOT buscarOCAPAreas(long cAreaId)
   {
     OCAPAreasOT dato = null;
     try
     {
       this.variableOAD = OCAPAreasOAD.getInstance();
       dato = this.variableOAD.buscarOCAPAreas(cAreaId);
     }
     catch (Exception e) {
       dato = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return dato;
   }
 
   public int listadoOCAPAreasCuenta(long cAreaId, String dNombre, String dDescripcion, String dObservaciones, String fCreacion, String fModificacion)
     throws Exception
   {
     int total = 0;
     try {
       this.variableOAD = OCAPAreasOAD.getInstance();
       total = this.variableOAD.listadoOCAPAreasCuenta(cAreaId, dNombre, dDescripcion, dObservaciones, fCreacion, fModificacion);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPAreas(long cAreaId, String dNombre, String dDescripcion, String dObservaciones, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPAreasOAD.getInstance();
       array = this.variableOAD.consultaOCAPAreas(cAreaId, dNombre, dDescripcion, dObservaciones, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public ArrayList consultaOCAPAsociacionesAreas(long cCategoriaId)
   {
     ArrayList array = null;
     try {
       this.variableOAD = OCAPAreasOAD.getInstance();
       array = this.variableOAD.consultaOCAPAsociacionesAreas(cCategoriaId);
     } catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPAreas()
     throws Exception
   {
     ArrayList array = new ArrayList();
     try {
       this.variableOAD = OCAPAreasOAD.getInstance();
       array = this.variableOAD.listadoOCAPAreas();
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPAreas(int inicio, int cuantos)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPAreasOAD.getInstance();
       array = this.variableOAD.listadoOCAPAreas(inicio, cuantos);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 }

