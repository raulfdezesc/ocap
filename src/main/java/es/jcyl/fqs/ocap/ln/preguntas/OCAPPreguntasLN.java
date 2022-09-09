 package es.jcyl.fqs.ocap.ln.preguntas;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.preguntas.OCAPPreguntasOAD;
 import es.jcyl.fqs.ocap.ot.preguntas.OCAPPreguntasOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPPreguntasLN
 {
   private JCYLUsuario jcylUsuario;
 
   public OCAPPreguntasLN(JCYLUsuario jcylUsuario)
   {
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList buscarOCAPPreguntas(long idPregunta, String dNombre, int primerRegistro, int registrosPorPagina)
   {
     ArrayList array = null;
     try
     {
       OCAPPreguntasOAD variableOAD = OCAPPreguntasOAD.getInstance();
       array = variableOAD.buscarOCAPPreguntas(idPregunta, dNombre, primerRegistro, registrosPorPagina);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public int listadoOCAPPreguntasCuenta(String dNombre)
     throws Exception
   {
     int total = 0;
     try
     {
       OCAPPreguntasOAD variableOAD = OCAPPreguntasOAD.getInstance();
       total = variableOAD.listadoOCAPPreguntasCuenta(dNombre);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public OCAPPreguntasOT buscarOCAPPreguntaId(long cPreguntaId)
   {
     OCAPPreguntasOT dato = null;
     try
     {
       OCAPPreguntasOAD variableOAD = OCAPPreguntasOAD.getInstance();
       dato = variableOAD.buscarOCAPPreguntaId(cPreguntaId);
     }
     catch (Exception e) {
       dato = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return dato;
   }
 
   public int altaOCAPPreguntas(OCAPPreguntasOT datos)
     throws Exception
   {
     int result = 0;
     try
     {
       OCAPPreguntasOAD variableOAD = OCAPPreguntasOAD.getInstance();
       result = variableOAD.altaOCAPPreguntas(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public int altaOCAPRespuestas(OCAPPreguntasOT datos)
     throws Exception
   {
     int result = 0;
     try
     {
       OCAPPreguntasOAD variableOAD = OCAPPreguntasOAD.getInstance();
       result = variableOAD.altaOCAPRespuestas(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public OCAPPreguntasOT buscarOCAPPregunta(long cPreguntaId)
     throws Exception
   {
     OCAPPreguntasOT dato = null;
     try
     {
       OCAPPreguntasOAD variableOAD = OCAPPreguntasOAD.getInstance();
       dato = variableOAD.buscarOCAPPregunta(cPreguntaId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return dato;
   }
 }

