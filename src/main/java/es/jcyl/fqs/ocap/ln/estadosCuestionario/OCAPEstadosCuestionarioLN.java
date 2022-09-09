 package es.jcyl.fqs.ocap.ln.estadosCuestionario;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.cuestionarios.OCAPCuestionariosOAD;
 import es.jcyl.fqs.ocap.oad.estadosCuestionario.OCAPEstadosCuestionarioOAD;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
 
 public class OCAPEstadosCuestionarioLN
 {
   private JCYLUsuario jcylUsuario;
 
   public OCAPEstadosCuestionarioLN(JCYLUsuario jcylUsuario)
   {
     this.jcylUsuario = jcylUsuario;
   }
 
   public void borrarOCAPEstadosCuestionario(long cExpId, long cCuestionarioId, int idRepeticion, long idPadreEvidencia)
     throws Exception
   {
     try
     {
       OCAPEstadosCuestionarioOAD estadosCuestionarioOAD = OCAPEstadosCuestionarioOAD.getInstance();
       estadosCuestionarioOAD.borraOCAPEstadosCuestionario(cExpId, cCuestionarioId, idRepeticion, idPadreEvidencia);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
   }
 
   public void borraOCAPEstadosCuestionarioExpediente(long cExpId)
     throws Exception
   {
     try
     {
       OCAPEstadosCuestionarioOAD estadosCuestionarioOAD = OCAPEstadosCuestionarioOAD.getInstance();
       estadosCuestionarioOAD.borraOCAPEstadosCuestionarioExpediente(cExpId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
   }
 
   public int altaOCAPEstadosCuestionario(long cExpId, long cCuestionarioId, int idRepeticion, String cEstado, String cEstadoPonderacion, String usuAlta, long idPadreEvidencia)
     throws Exception
   {
     int resultado = 0;
     try
     {
       OCAPEstadosCuestionarioOAD estadosCuestionarioOAD = OCAPEstadosCuestionarioOAD.getInstance();
       resultado = estadosCuestionarioOAD.altaOCAPEstadosCuestionario(cExpId, cCuestionarioId, idRepeticion, cEstado, cEstadoPonderacion, usuAlta, idPadreEvidencia);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return resultado;
   }
 
   public String buscarEstadoCuestionario(long cExpId, long cCuestionarioId, int idRepeticion, long idPadreEvidencia)
     throws Exception
   {
     String estado = null;
     try
     {
       OCAPEstadosCuestionarioOAD estadosCuestionarioOAD = OCAPEstadosCuestionarioOAD.getInstance();
       estado = estadosCuestionarioOAD.buscarEstadoCuestionario(cExpId, cCuestionarioId, idRepeticion, idPadreEvidencia);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return estado;
   }
 
   public String buscarEstadoSimulacion(long cExpId, long cCuestionarioId, int idRepeticion)
     throws Exception
   {
     String estado = null;
     try
     {
       OCAPEstadosCuestionarioOAD estadosCuestionarioOAD = OCAPEstadosCuestionarioOAD.getInstance();
       estado = estadosCuestionarioOAD.buscarEstadoSimulacion(cExpId, cCuestionarioId, idRepeticion);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return estado;
   }
 
   public String buscarEstadoPonderacion(long cExpId, long cCuestionarioId, int idRepeticion)
     throws Exception
   {
     String estado = null;
     try
     {
       OCAPEstadosCuestionarioOAD estadosCuestionarioOAD = OCAPEstadosCuestionarioOAD.getInstance();
       estado = estadosCuestionarioOAD.buscarEstadoPonderacion(cExpId, cCuestionarioId, idRepeticion);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return estado;
   }
 
   public int modificacionOCAPEstadosCuestionario(long cExpId, long cCuestionarioId, int idRepeticion, String cEstado, String usuAlta)
     throws Exception
   {
     int resultado = 0;
     try
     {
       OCAPEstadosCuestionarioOAD estadosCuestionarioOAD = OCAPEstadosCuestionarioOAD.getInstance();
       resultado = estadosCuestionarioOAD.modificacionOCAPEstadosCuestionario(cExpId, cCuestionarioId, idRepeticion, cEstado, usuAlta);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return resultado;
   }
 
   public int modificarOCAPEstadoSimulacion(long cExpId, long cCuestionarioId, int idRepeticion, String cEstado, String usuAlta)
     throws Exception
   {
     int resultado = 0;
     try
     {
       OCAPEstadosCuestionarioOAD estadosCuestionarioOAD = OCAPEstadosCuestionarioOAD.getInstance();
       resultado = estadosCuestionarioOAD.modificarOCAPEstadoSimulacion(cExpId, cCuestionarioId, idRepeticion, cEstado, usuAlta);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return resultado;
   }
 
   public int contarCuestionariosFinalizadosArea(long cExpId, long cAreaId)
     throws Exception
   {
     int numFinalizados = 0;
     try
     {
       OCAPEstadosCuestionarioOAD estadosCuestionarioOAD = OCAPEstadosCuestionarioOAD.getInstance();
       numFinalizados = estadosCuestionarioOAD.contarCuestionariosFinalizadosArea(cExpId, cAreaId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return numFinalizados;
   }
 
   public void abrirCuestionariosExp(long cExpId, long cCuestionarioId, String usuModi)
     throws Exception
   {
     int numCuestionarios = 0;
     try
     {
       OCAPCuestionariosOAD cuestionariosOAD = OCAPCuestionariosOAD.getInstance();
       ArrayList listaCuestionarios = cuestionariosOAD.buscarCuestionariosAsociados(cCuestionarioId);
 
       if (listaCuestionarios != null) numCuestionarios = listaCuestionarios.size();
 
       if (numCuestionarios > 0) {
         OCAPEstadosCuestionarioOAD estadosCuestionarioOAD = OCAPEstadosCuestionarioOAD.getInstance();
         for (int i = 0; i < numCuestionarios; i++)
         {
           estadosCuestionarioOAD.borrarCuestionariosExp(cExpId, ((Long)listaCuestionarios.get(i)).longValue(), usuModi);
         }
       }
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
   }
   
   public int actualizarDesbloqueoF3(Long cExpId)
		     throws Exception
		   {
		     int resultado = 0;
		     try
		     {
		       OCAPEstadosCuestionarioOAD estadosCuestionarioOAD = OCAPEstadosCuestionarioOAD.getInstance();
		       resultado = estadosCuestionarioOAD.actualizarDesbloqueoF3(cExpId);
		     }
		     catch (Exception e) {
		       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
		       throw e;
		     }
		 
		     return resultado;
		   }
 }

