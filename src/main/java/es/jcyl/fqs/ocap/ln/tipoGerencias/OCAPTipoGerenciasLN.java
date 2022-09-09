 package es.jcyl.fqs.ocap.ln.tipoGerencias;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.tipoGerencias.OCAPTipoGerenciasOAD;
 import es.jcyl.fqs.ocap.ot.tipoGerencias.OCAPTipoGerenciasOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPTipoGerenciasLN
 {
   OCAPTipoGerenciasOAD tipoGerenciasOAD;
   private JCYLUsuario jcylUsuario;
 
   public void OCAPTipoGerenciasLN()
   {
     this.tipoGerenciasOAD = OCAPTipoGerenciasOAD.getInstance();
   }
 
   public OCAPTipoGerenciasLN(JCYLUsuario jcylUsuario)
   {
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList listadoOCAPTipoGerencias()
     throws Exception
   {
     ArrayList resultado = null;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " listarTipos: Inicio");
 
       this.tipoGerenciasOAD = OCAPTipoGerenciasOAD.getInstance();
       resultado = this.tipoGerenciasOAD.listadoOCAPTipoGerencias();
 
       OCAPConfigApp.logger.info(getClass().getName() + " listarGerencias: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " listarGerencias: ERROR: " + e.getMessage());
       throw e;
     }
 
     return resultado;
   }
 
   public OCAPTipoGerenciasOT buscarOCAPTipoGerencias(long cTipoGerenciaId)
     throws Exception
   {
     OCAPTipoGerenciasOT dato = null;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " listarTipos: Inicio");
 
       this.tipoGerenciasOAD = OCAPTipoGerenciasOAD.getInstance();
       dato = this.tipoGerenciasOAD.buscarOCAPTipoGerencias(cTipoGerenciaId);
 
       OCAPConfigApp.logger.info(getClass().getName() + " listarGerencias: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " listarGerencias: ERROR: " + e.getMessage());
       throw e;
     }
 
     return dato;
   }
 
   public int altaOCAPTipoGerencias(OCAPTipoGerenciasOT datos)
   {
     int result = 0;
     try
     {
       this.tipoGerenciasOAD = OCAPTipoGerenciasOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.tipoGerenciasOAD.altaOCAPTipoGerencias(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int bajaOCAPTipoGerencias(int cTipogerenciaId)
   {
     int result = 0;
     try
     {
       this.tipoGerenciasOAD = OCAPTipoGerenciasOAD.getInstance();
       result = this.tipoGerenciasOAD.bajaOCAPTipoGerencias(cTipogerenciaId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPTipoGerencias(OCAPTipoGerenciasOT datos)
   {
     int result = 0;
     try
     {
       this.tipoGerenciasOAD = OCAPTipoGerenciasOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.tipoGerenciasOAD.modificacionOCAPTipoGerencias(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int listadoOCAPTipoGerenciasCuenta(long cTipogerenciaId, String dNombre, String dDescripcion, String fCreacion, String fModificacion)
     throws Exception
   {
     int total = 0;
     try {
       this.tipoGerenciasOAD = OCAPTipoGerenciasOAD.getInstance();
       total = this.tipoGerenciasOAD.listadoOCAPTipoGerenciasCuenta(cTipogerenciaId, dNombre, dDescripcion, fCreacion, fModificacion);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPTipoGerencias(long cTipogerenciaId, String dNombre, String dDescripcion, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
   {
     ArrayList array = null;
     try
     {
       this.tipoGerenciasOAD = OCAPTipoGerenciasOAD.getInstance();
       array = this.tipoGerenciasOAD.consultaOCAPTipoGerencias(cTipogerenciaId, dNombre, dDescripcion, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPTipoGerencias(int inicio, int cuantos)
   {
     ArrayList array = null;
     try
     {
       this.tipoGerenciasOAD = OCAPTipoGerenciasOAD.getInstance();
       array = this.tipoGerenciasOAD.listadoOCAPTipoGerencias(inicio, cuantos);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public ArrayList listarTipoGerencias()
     throws Exception
   {
     ArrayList resultado = null;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " listarTipoGerencias: Inicio");
 
       this.tipoGerenciasOAD = OCAPTipoGerenciasOAD.getInstance();
       resultado = this.tipoGerenciasOAD.listadoOCAPTipoGerencias();
 
       OCAPConfigApp.logger.info(getClass().getName() + " listarTipoGerencias: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " listarTipoGerencias: ERROR: " + e.getMessage());
       throw e;
     }
 
     return resultado;
   }
 }

