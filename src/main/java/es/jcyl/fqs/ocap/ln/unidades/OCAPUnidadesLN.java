 package es.jcyl.fqs.ocap.ln.unidades;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.unidades.OCAPUnidadesOAD;
 import es.jcyl.fqs.ocap.ot.unidades.OCAPUnidadesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPUnidadesLN
 {
   OCAPUnidadesOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   public void OCAPUnidadesLN()
   {
     this.variableOAD = OCAPUnidadesOAD.getInstance();
   }
 
   public OCAPUnidadesLN(JCYLUsuario jcylUsuario) {
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPUnidades(OCAPUnidadesOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPUnidadesOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.altaOCAPUnidades(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int bajaOCAPUnidades(long cUnidadId, String usuario)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPUnidadesOAD.getInstance();
       result = this.variableOAD.bajaOCAPUnidades(cUnidadId, usuario);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPUnidades(OCAPUnidadesOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPUnidadesOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.modificacionOCAPUnidades(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public OCAPUnidadesOT buscarOCAPUnidades(long cUnidadId)
   {
     OCAPUnidadesOT dato = null;
     try
     {
       this.variableOAD = OCAPUnidadesOAD.getInstance();
       dato = this.variableOAD.buscarOCAPUnidades(cUnidadId);
     }
     catch (Exception e) {
       dato = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return dato;
   }
 
   public int listadoOCAPUnidadesCuenta(long cUnidadId, long cAreaId, String dNombre, String dDescripcion, String dObservaciones, long cProfesionalId, long cItinerarioId, String aNombreCorto, String fCreacion, String fModificacion)
     throws Exception
   {
     int total = 0;
     try {
       this.variableOAD = OCAPUnidadesOAD.getInstance();
       total = this.variableOAD.listadoOCAPUnidadesCuenta(cUnidadId, cAreaId, dNombre, dDescripcion, dObservaciones, cProfesionalId, cItinerarioId, aNombreCorto, fCreacion, fModificacion);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPUnidades(long cUnidadId, long cAreaId, String dNombre, String dDescripcion, String dObservaciones, long cProfesionalId, long cItinerarioId, String aNombreCorto, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPUnidadesOAD.getInstance();
       array = this.variableOAD.consultaOCAPUnidades(cUnidadId, cAreaId, dNombre, dDescripcion, dObservaciones, cProfesionalId, cItinerarioId, aNombreCorto, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPUnidades()
     throws Exception
   {
     ArrayList array = new ArrayList();
     try {
       this.variableOAD = OCAPUnidadesOAD.getInstance();
       array = this.variableOAD.listadoOCAPUnidades();
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPUnidades(int inicio, int cuantos)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPUnidadesOAD.getInstance();
       array = this.variableOAD.listadoOCAPUnidades(inicio, cuantos);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 }

