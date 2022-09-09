 package es.jcyl.fqs.ocap.ln.creditosCuestionarios;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.creditosCuestionarios.OCAPCreditosCuestionariosOAD;
 import es.jcyl.fqs.ocap.ot.creditosCuestionarios.OCAPCreditosCuestionariosOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.sql.Connection;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPCreditosCuestionariosLN
 {
   private JCYLUsuario jcylUsuario;
 
   public OCAPCreditosCuestionariosLN(JCYLUsuario jcylUsuario)
   {
     this.jcylUsuario = jcylUsuario;
   }
 
   public int borrarOCAPCreditosCuestionario(OCAPCreditosCuestionariosOT datos)
     throws Exception
   {
     int result = 0;
     try
     {
       OCAPCreditosCuestionariosOAD creditosCuestionariosOAD = OCAPCreditosCuestionariosOAD.getInstance();
       result = creditosCuestionariosOAD.borrarOCAPCreditosCuestionario(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public int borrarOCAPCreditosCuestionarioExpId(long cExpId, Connection con)
     throws Exception
   {
     int result = 0;
     try
     {
       OCAPCreditosCuestionariosOAD creditosCuestionariosOAD = OCAPCreditosCuestionariosOAD.getInstance();
       result = creditosCuestionariosOAD.borrarOCAPCreditosCuestionarioExpId(cExpId, con);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public int altaOCAPCreditosCuestionarios(OCAPCreditosCuestionariosOT datos)
     throws Exception
   {
     int result = 0;
     try
     {
       OCAPCreditosCuestionariosOAD creditosCuestionariosOAD = OCAPCreditosCuestionariosOAD.getInstance();
       result = creditosCuestionariosOAD.altaOCAPCreditosCuestionarios(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public int bajaOCAPCreditosCuestionarios(int cGerenciaId)
   {
     int result = 0;
     try
     {
       OCAPCreditosCuestionariosOAD creditosCuestionariosOAD = OCAPCreditosCuestionariosOAD.getInstance();
       result = creditosCuestionariosOAD.bajaOCAPCreditosCuestionarios(cGerenciaId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPCreditosCuestionarios(OCAPCreditosCuestionariosOT datos, boolean bAutoponderacion)
   {
     int result = 0;
     try
     {
       OCAPCreditosCuestionariosOAD creditosCuestionariosOAD = OCAPCreditosCuestionariosOAD.getInstance();
       result = creditosCuestionariosOAD.modificacionOCAPCreditosCuestionarios(datos, bAutoponderacion);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public ArrayList buscarOCAPCreditosCuestionario(long cCuestionarioId, int idRepeticion, long cExpId)
   {
     ArrayList listado = null;
     try
     {
       OCAPCreditosCuestionariosOAD creditosCuestionariosOAD = OCAPCreditosCuestionariosOAD.getInstance();
       listado = creditosCuestionariosOAD.buscarOCAPCreditosCuestionario(cCuestionarioId, idRepeticion, cExpId);
     }
     catch (Exception e) {
       listado = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return listado;
   }
 
   public ArrayList buscarOCAPPuntosCuestionariosArea(long cAreaId, long cExpId)
     throws Exception
   {
     ArrayList listado = null;
     try
     {
       OCAPCreditosCuestionariosOAD creditosCuestionariosOAD = OCAPCreditosCuestionariosOAD.getInstance();
       listado = creditosCuestionariosOAD.buscarOCAPPuntosCuestionariosArea(cAreaId, cExpId);
     }
     catch (Exception e) {
       listado = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return listado;
   }
 
   public ArrayList buscarOCAPCreditosCuestionariosPorExpId(long cExpId)
   {
     ArrayList datos = null;
     try
     {
       OCAPCreditosCuestionariosOAD creditosCuestionariosOAD = OCAPCreditosCuestionariosOAD.getInstance();
       datos = creditosCuestionariosOAD.buscarOCAPCreditosCuestionariosPorExpId(cExpId);
     }
     catch (Exception e) {
       datos = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return datos;
   }
 
   public int altaEvaluacion(OCAPCreditosCuestionariosOT datos)
     throws Exception
   {
     int result = 0;
     try
     {
       OCAPCreditosCuestionariosOAD creditosCuestionariosOAD = OCAPCreditosCuestionariosOAD.getInstance();
       ArrayList lista = buscarOCAPCreditosCuestionario(datos.getCCuestionarioId(), 0, datos.getCExpId());
 
       if ((lista == null) || (lista.size() == 0)) {
         datos.setNRepeticion(1);
         result = creditosCuestionariosOAD.altaOCAPCreditosCuestionarios(datos);
       }
       else {
         OCAPCreditosCuestionariosOT aux = (OCAPCreditosCuestionariosOT)lista.get(0);
         datos.setNRepeticion(aux.getNRepeticion());
         result = creditosCuestionariosOAD.modificaOCAPCreditosCuestionarios(datos);
       }
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public int altaAnalisis(OCAPCreditosCuestionariosOT datos)
     throws Exception
   {
     int result = 0;
     try
     {
       OCAPCreditosCuestionariosOAD creditosCuestionariosOAD = OCAPCreditosCuestionariosOAD.getInstance();
       ArrayList lista = buscarOCAPCreditosCuestionario(datos.getCCuestionarioId(), 0, datos.getCExpId());
 
       if ((lista == null) || (lista.size() == 0)) {
         datos.setNRepeticion(1);
         result = creditosCuestionariosOAD.altaOCAPCreditosCuestionarios(datos);
       }
       else {
         OCAPCreditosCuestionariosOT aux = (OCAPCreditosCuestionariosOT)lista.get(0);
         datos.setNRepeticion(aux.getNRepeticion());
         result = creditosCuestionariosOAD.modificaOCAPCreditosCuestionarios2(datos);
       }
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public int modificaOCAPCreditosCuestionarios(OCAPCreditosCuestionariosOT datos)
   {
     int result = 0;
     try
     {
       OCAPCreditosCuestionariosOAD creditosCuestionariosOAD = OCAPCreditosCuestionariosOAD.getInstance();
       result = creditosCuestionariosOAD.modificaOCAPCreditosCuestionarios(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int cuentaSegundasAuditorias(long cExpId)
   {
     int result = 0;
     try
     {
       OCAPCreditosCuestionariosOAD creditosCuestionariosOAD = OCAPCreditosCuestionariosOAD.getInstance();
       result = creditosCuestionariosOAD.cuentaSegundasAuditorias(cExpId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public ArrayList buscarOCAPAuditoriasPorExpId(long cExpId)
   {
     ArrayList datos = null;
     try
     {
       OCAPCreditosCuestionariosOAD creditosCuestionariosOAD = OCAPCreditosCuestionariosOAD.getInstance();
       datos = creditosCuestionariosOAD.buscarOCAPAuditoriasPorExpId(cExpId);
     }
     catch (Exception e) {
       datos = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return datos;
   }
 }

