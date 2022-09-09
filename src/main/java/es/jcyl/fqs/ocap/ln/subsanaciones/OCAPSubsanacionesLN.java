 package es.jcyl.fqs.ocap.ln.subsanaciones;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.subsanaciones.OCAPSubsanacionesOAD;
 import es.jcyl.fqs.ocap.ot.subsanaciones.OCAPSubsanacionesOT;
 import es.jcyl.framework.JCYLUsuario;
 import org.apache.log4j.Logger;
 
 public class OCAPSubsanacionesLN
 {
   OCAPSubsanacionesOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   public void OCAPSubsanacionesLN()
   {
     this.variableOAD = OCAPSubsanacionesOAD.getInstance();
   }
 
   public OCAPSubsanacionesLN(JCYLUsuario jcylUsuario) {
     this.jcylUsuario = jcylUsuario;
   }
 
   public void altaSubsanaciones(OCAPSubsanacionesOT subsanacionesOT)
     throws Exception
   {
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " altaSubsanaciones: Inicio");
 
       OCAPSubsanacionesOAD subsanacionesOAD = OCAPSubsanacionesOAD.getInstance();
 
       subsanacionesOAD.altaSubsanaciones(subsanacionesOT);
 
       OCAPConfigApp.logger.info(getClass().getName() + " altaSubsanaciones: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " altaSubsanaciones: ERROR: " + e.getMessage());
       throw e;
     }
   }
 
   public String buscaUltimaSubsanacion(long cSolicitudId)
     throws Exception
   {
     String resultado = null;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " buscaUltimaSubsanacion: Inicio");
 
       OCAPSubsanacionesOAD subsanacionesOAD = OCAPSubsanacionesOAD.getInstance();
 
       resultado = subsanacionesOAD.buscaUltimaSubsanacion(cSolicitudId);
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscaUltimaSubsanacion: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " buscaUltimaSubsanacion: ERROR: " + e.getMessage());
       throw e;
     }
 
     return resultado;
   }
 }

