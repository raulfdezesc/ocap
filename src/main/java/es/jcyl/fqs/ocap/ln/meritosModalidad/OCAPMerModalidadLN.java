 package es.jcyl.fqs.ocap.ln.meritosModalidad;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.meritosModalidad.OCAPMerModalidadOAD;
 import es.jcyl.fqs.ocap.ot.meritosModalidad.OCAPMerModalidadOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPMerModalidadLN
 {
   OCAPMerModalidadOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   public void OCAPMerModalidadLN()
   {
     this.variableOAD = OCAPMerModalidadOAD.getInstance();
   }
 
   public OCAPMerModalidadLN(JCYLUsuario jcylUsuario) {
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPMerModalidad(OCAPMerModalidadOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPMerModalidadOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.altaOCAPMerModalidad(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int bajaOCAPMerModalidad(int cModalidadId)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPMerModalidadOAD.getInstance();
       result = this.variableOAD.bajaOCAPMerModalidad(cModalidadId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPMerModalidad(OCAPMerModalidadOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPMerModalidadOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.modificacionOCAPMerModalidad(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public OCAPMerModalidadOT buscarOCAPMerModalidad(long cModalidadId)
   {
     OCAPMerModalidadOT dato = null;
     try
     {
       this.variableOAD = OCAPMerModalidadOAD.getInstance();
       dato = this.variableOAD.buscarOCAPMerModalidad(cModalidadId);
     }
     catch (Exception e) {
       dato = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return dato;
   }
 
   public int listadoOCAPMerModalidadCuenta(long cModalidadId, String dNombre, String dDescripcion, String fCreacion, String fModificacion)
     throws Exception
   {
     int total = 0;
     try {
       this.variableOAD = OCAPMerModalidadOAD.getInstance();
       total = this.variableOAD.listadoOCAPMerModalidadCuenta(cModalidadId, dNombre, dDescripcion, fCreacion, fModificacion);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPMerModalidad(long cModalidadId, String dNombre, String dDescripcion, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPMerModalidadOAD.getInstance();
       array = this.variableOAD.consultaOCAPMerModalidad(cModalidadId, dNombre, dDescripcion, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPMerModalidad()
     throws Exception
   {
     ArrayList array = new ArrayList();
     try {
       this.variableOAD = OCAPMerModalidadOAD.getInstance();
       array = this.variableOAD.listadoOCAPMerModalidad();
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPMerModalidad(int inicio, int cuantos)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPMerModalidadOAD.getInstance();
       array = this.variableOAD.listadoOCAPMerModalidad(inicio, cuantos);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public ArrayList buscarOCAPMerModalidadDeMeritocurricular(Integer cDefMeritoId, Integer cEstatutId, String dMeritoNombre, Integer cModalidadId)
     throws Exception
   {
     ArrayList array = new ArrayList();
     try {
       this.variableOAD = OCAPMerModalidadOAD.getInstance();
       array = this.variableOAD.buscarOCAPMerModalidadDeMeritocurricular(cDefMeritoId, cEstatutId, dMeritoNombre, cModalidadId);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 }

