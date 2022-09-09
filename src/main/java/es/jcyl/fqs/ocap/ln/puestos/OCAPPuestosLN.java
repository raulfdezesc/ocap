 package es.jcyl.fqs.ocap.ln.puestos;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.puestos.OCAPPuestosOAD;
 import es.jcyl.fqs.ocap.ot.puestos.OCAPPuestosOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPPuestosLN
 {
   Logger logger;
   private OCAPPuestosOAD puestosOAD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
 
   public void OCAPPuestosLN()
   {
     this.puestosOAD = OCAPPuestosOAD.getInstance();
   }
   public OCAPPuestosLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPPuestos(OCAPPuestosOT datos)
   {
     int result = 0;
     try
     {
       this.puestosOAD = OCAPPuestosOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.puestosOAD.altaOCAPPuestos(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int bajaOCAPPuestos(int cPuestoId, String usuario)
   {
     int result = 0;
     try
     {
       this.puestosOAD = OCAPPuestosOAD.getInstance();
       result = this.puestosOAD.bajaOCAPPuestos(cPuestoId, usuario);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPPuestos(OCAPPuestosOT datos)
   {
     int result = 0;
     try
     {
       this.puestosOAD = OCAPPuestosOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.puestosOAD.modificacionOCAPPuestos(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public OCAPPuestosOT buscarOCAPPuestos(long cPuestoId)
   {
     OCAPPuestosOT dato = null;
     try
     {
       this.puestosOAD = OCAPPuestosOAD.getInstance();
       dato = this.puestosOAD.buscarOCAPPuestos(cPuestoId);
     }
     catch (Exception e) {
       dato = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return dato;
   }
 
   public int listadoOCAPPuestosCuenta(long cPuestoId, long cProfesionalId, String dNombre, String dObservaciones, long cItinerarioId, String aNombreCorto, String fCreacion, String fModificacion)
     throws Exception
   {
     int total = 0;
     try {
       this.puestosOAD = OCAPPuestosOAD.getInstance();
       total = this.puestosOAD.listadoOCAPPuestosCuenta(cPuestoId, cProfesionalId, dNombre, dObservaciones, cItinerarioId, aNombreCorto, fCreacion, fModificacion);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPPuestos(long cPuestoId, long cProfesionalId, String dNombre, String dObservaciones, long cItinerarioId, String aNombreCorto, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
   {
     ArrayList array = null;
     try
     {
       this.puestosOAD = OCAPPuestosOAD.getInstance();
       array = this.puestosOAD.consultaOCAPPuestos(cPuestoId, cProfesionalId, dNombre, dObservaciones, cItinerarioId, aNombreCorto, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 }

