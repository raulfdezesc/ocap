 package es.jcyl.fqs.ocap.ln.mensajes;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.mensajes.OCAPMensajesOAD;
 import es.jcyl.fqs.ocap.ot.mensajes.OCAPMensajesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPMensajesLN
 {
   public Logger loggerBD;
   OCAPMensajesOAD mensajesOAD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public void OCAPMensajesLN()
   {
     this.mensajesOAD = OCAPMensajesOAD.getInstance();
   }
   public OCAPMensajesLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPMensajes(OCAPMensajesOT datos)
     throws Exception
   {
     int result = 0;
     try {
       this.mensajesOAD = OCAPMensajesOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.mensajesOAD.altaOCAPMensajes(datos);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public int bajaOCAPMensajes(int cMensajeId)
   {
     int result = 0;
     try {
       this.mensajesOAD = OCAPMensajesOAD.getInstance();
       result = this.mensajesOAD.bajaOCAPMensajes(cMensajeId);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPMensajes(OCAPMensajesOT datos)
     throws Exception
   {
     int result = 0;
     try {
       this.mensajesOAD = OCAPMensajesOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.mensajesOAD.modificacionOCAPMensajes(datos);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public OCAPMensajesOT buscarOCAPMensajes(long cMensajeId)
   {
     OCAPMensajesOT dato = null;
     try {
       this.mensajesOAD = OCAPMensajesOAD.getInstance();
       dato = this.mensajesOAD.buscarOCAPMensajes(cMensajeId);
     } catch (Exception e) {
       dato = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return dato;
   }
 
   public OCAPMensajesOT buscarOCAPMensajesPorGradoIdYEstatutId(Integer cGradoId, Integer cEstatutId)
     throws SQLException, Exception
   {
     OCAPMensajesOT dato = null;
     try
     {
       this.mensajesOAD = OCAPMensajesOAD.getInstance();
       dato = this.mensajesOAD.buscarOCAPMensajesPorGradoYEstatut(cGradoId, cEstatutId);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return dato;
   }
 
   public ArrayList listadoOCAPMensajes()
     throws Exception
   {
     ArrayList array = new ArrayList();
     try {
       this.mensajesOAD = OCAPMensajesOAD.getInstance();
       array = this.mensajesOAD.listadoOCAPMensajes();
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public int listadoOCAPMensajesCuenta(long cMensajeId, long cEstatutId, long cGradoId, String dDescripcion, String fCreacion, String fModificacion)
     throws Exception
   {
     int total = 0;
     try {
       this.mensajesOAD = OCAPMensajesOAD.getInstance();
       total = this.mensajesOAD.listadoOCAPMensajesCuenta(cMensajeId, cEstatutId, cGradoId, dDescripcion, fCreacion, fModificacion);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPMensajes(long cMensajeId, long cEstatutId, long cGradoId, String dDescripcion, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
   {
     ArrayList array = null;
     try {
       this.mensajesOAD = OCAPMensajesOAD.getInstance();
       array = this.mensajesOAD.consultaOCAPMensajes(cMensajeId, cEstatutId, cGradoId, dDescripcion, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     } catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 }

