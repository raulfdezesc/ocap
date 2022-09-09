 package es.jcyl.fqs.ocap.ln.factoresImpacto;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.factoresImpacto.OCAPFactoresImpactoOAD;
 import es.jcyl.fqs.ocap.ot.factoresImpacto.OCAPFactoresImpactoOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPFactoresImpactoLN
 {
   OCAPFactoresImpactoOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   public void OCAPAreasTrabajoLN()
   {
     this.variableOAD = OCAPFactoresImpactoOAD.getInstance();
   }
 
   public OCAPFactoresImpactoLN(JCYLUsuario jcylUsuario) {
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList listadoOCAPFactoresImpacto()
     throws Exception
   {
     ArrayList array = new ArrayList();
     try {
       this.variableOAD = OCAPFactoresImpactoOAD.getInstance();
       array = this.variableOAD.listadoOCAPFactoresImpacto();
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public ArrayList buscarOCAPFactoresImpactoDeMeritocurricular(Integer cDefMeritoId, Integer cEstatutId, String dMeritoNombre, Integer cFactorId)
     throws Exception
   {
     ArrayList array = new ArrayList();
     try {
       this.variableOAD = OCAPFactoresImpactoOAD.getInstance();
       array = this.variableOAD.buscarOCAPFactoresImpactoDeMeritocurricular(cDefMeritoId, cEstatutId, dMeritoNombre, cFactorId);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public OCAPFactoresImpactoOT buscarOCAPFactoresImpacto(long cFactorId)
   {
     OCAPFactoresImpactoOT dato = null;
     try
     {
       this.variableOAD = OCAPFactoresImpactoOAD.getInstance();
       dato = this.variableOAD.buscarOCAPFactoresImpacto(cFactorId);
     }
     catch (Exception e) {
       dato = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return dato;
   }
 
   public int altaOCAPFactoresImpacto(OCAPFactoresImpactoOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPFactoresImpactoOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.altaOCAPFactoresImpacto(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int bajaOCAPFactoresImpacto(int cFactorId)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPFactoresImpactoOAD.getInstance();
       result = this.variableOAD.bajaOCAPFactoresImpacto(cFactorId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPFactoresImpacto(OCAPFactoresImpactoOT datos)
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPFactoresImpactoOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.modificacionOCAPFactoresImpacto(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int listadoOCAPFactoresImpactoCuenta(long cFactorId, String dNombre, String dObservaciones, String fCreacion, String fModificacion)
     throws Exception
   {
     int total = 0;
     try {
       this.variableOAD = OCAPFactoresImpactoOAD.getInstance();
       total = this.variableOAD.listadoOCAPFactoresImpactoCuenta(cFactorId, dNombre, dObservaciones, fCreacion, fModificacion);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPFactoresImpacto(long cFactorId, String dNombre, String dObservaciones, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPFactoresImpactoOAD.getInstance();
       array = this.variableOAD.consultaOCAPFactoresImpacto(cFactorId, dNombre, dObservaciones, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 }

