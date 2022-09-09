 package es.jcyl.fqs.ocap.ln.meritosActividad;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.meritosActividad.OCAPMeractividadOAD;
 import es.jcyl.fqs.ocap.ot.meritosActividad.OCAPMeractividadOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPMeractividadLN
 {
   OCAPMeractividadOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   public void OCAPMeractividadLN()
   {
     this.variableOAD = OCAPMeractividadOAD.getInstance();
   }
 
   public OCAPMeractividadLN(JCYLUsuario jcylUsuario) {
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPMeractividad(OCAPMeractividadOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPMeractividadOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.altaOCAPMeractividad(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int bajaOCAPMeractividad(int cActividadId)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPMeractividadOAD.getInstance();
       result = this.variableOAD.bajaOCAPMeractividad(cActividadId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPMeractividad(OCAPMeractividadOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPMeractividadOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.modificacionOCAPMeractividad(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public OCAPMeractividadOT buscarOCAPMeractividad(long cActividadId)
   {
     OCAPMeractividadOT dato = null;
     try
     {
       this.variableOAD = OCAPMeractividadOAD.getInstance();
       dato = this.variableOAD.buscarOCAPMeractividad(cActividadId);
     }
     catch (Exception e) {
       dato = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return dato;
   }
 
   public int listadoOCAPMeractividadCuenta(long cActividadId, String dNombre, String dObservaciones, String fCreacion, String fModificacion)
     throws Exception
   {
     int total = 0;
     try {
       this.variableOAD = OCAPMeractividadOAD.getInstance();
       total = this.variableOAD.listadoOCAPMeractividadCuenta(cActividadId, dNombre, dObservaciones, fCreacion, fModificacion);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPMeractividad(long cActividadId, String dNombre, String dObservaciones, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPMeractividadOAD.getInstance();
       array = this.variableOAD.consultaOCAPMeractividad(cActividadId, dNombre, dObservaciones, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPMeractividad()
     throws Exception
   {
     ArrayList array = new ArrayList();
     try {
       this.variableOAD = OCAPMeractividadOAD.getInstance();
       array = this.variableOAD.listadoOCAPMeractividad();
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPMeractividad(int inicio, int cuantos)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPMeractividadOAD.getInstance();
       array = this.variableOAD.listadoOCAPMeractividad(inicio, cuantos);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public ArrayList buscarOCAPMeractividadDeMeritocurricular(Integer cDefMeritoId, Integer cEstatutId, String dMeritoNombre, Integer cActividadId)
     throws Exception
   {
     ArrayList array = new ArrayList();
     try {
       this.variableOAD = OCAPMeractividadOAD.getInstance();
       array = this.variableOAD.buscarOCAPMeractividadDeMeritocurricular(cDefMeritoId, cEstatutId, dMeritoNombre, cActividadId);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 }

