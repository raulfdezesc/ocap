 package es.jcyl.fqs.ocap.ln.servicios;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.servicios.OCAPServiciosOAD;
 import es.jcyl.fqs.ocap.ot.servicios.OCAPServiciosOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPServiciosLN
 {
   OCAPServiciosOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   public void OCAPServiciosLN()
   {
     this.variableOAD = OCAPServiciosOAD.getInstance();
   }
 
   public OCAPServiciosLN(JCYLUsuario jcylUsuario) {
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPServicios(OCAPServiciosOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPServiciosOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.altaOCAPServicios(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int bajaOCAPServicios(int cServicioId, String usuario)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPServiciosOAD.getInstance();
       result = this.variableOAD.bajaOCAPServicios(cServicioId, usuario);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPServicios(OCAPServiciosOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPServiciosOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.modificacionOCAPServicios(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public OCAPServiciosOT buscarOCAPServicios(long cServicioId)
   {
     OCAPServiciosOT dato = null;
     try
     {
       this.variableOAD = OCAPServiciosOAD.getInstance();
       dato = this.variableOAD.buscarOCAPServicios(cServicioId);
     }
     catch (Exception e) {
       dato = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return dato;
   }
 
   public int listadoOCAPServiciosCuenta(long cServicioId, String dServicioNombre, String dServicioDesc, String dServicioObserv, long cProfesionalId, long cEspecId, long cItinerarioId, String fCreacion, String fModificacion)
     throws Exception
   {
     int total = 0;
     try {
       this.variableOAD = OCAPServiciosOAD.getInstance();
       total = this.variableOAD.listadoOCAPServiciosCuenta(cServicioId, dServicioNombre, dServicioDesc, dServicioObserv, cProfesionalId, cEspecId, cItinerarioId, fCreacion, fModificacion);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPServicios(long cServicioId, String dServicioNombre, String dServicioDesc, String dServicioObserv, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina, long cCategoriaId, long cEspecId, long cItinerarioId)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPServiciosOAD.getInstance();
       array = this.variableOAD.consultaOCAPServicios(cServicioId, dServicioNombre, dServicioDesc, dServicioObserv, fCreacion, fModificacion, primerRegistro, registrosPorPagina, cCategoriaId, cEspecId, cItinerarioId);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public ArrayList consultaOCAPAsociacionesServicios(long cCategoriaId, long cEspecId)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPServiciosOAD.getInstance();
       array = this.variableOAD.consultaOCAPAsociacionesServicios(cCategoriaId, cEspecId);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPServicios()
     throws Exception
   {
     ArrayList array = new ArrayList();
     try {
       this.variableOAD = OCAPServiciosOAD.getInstance();
       array = this.variableOAD.listadoOCAPServicios();
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPServicios(int inicio, int cuantos)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPServiciosOAD.getInstance();
       array = this.variableOAD.listadoOCAPServicios(inicio, cuantos);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 }

