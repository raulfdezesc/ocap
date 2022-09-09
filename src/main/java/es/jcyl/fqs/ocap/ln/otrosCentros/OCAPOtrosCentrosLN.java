 package es.jcyl.fqs.ocap.ln.otrosCentros;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.otrosCentros.OCAPOtrosCentrosOAD;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPOtrosCentrosLN
 {
   public Logger logger;
   public Logger loggerBD;
   OCAPOtrosCentrosOAD otrosCentrosOAD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
   public OCAPOtrosCentrosLN() { $init$(); }
 
 
   public OCAPOtrosCentrosLN(JCYLUsuario jcylUsuario)
   {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
   
   public void removeAllCentros(Long cSolicitudId, Long cExpId) throws SQLException, Exception
   {
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " removeAllCentros: Inicio");
       this.otrosCentrosOAD = OCAPOtrosCentrosOAD.getInstance();
 
       this.otrosCentrosOAD.removeAllCentros(cSolicitudId, cExpId);
 
       OCAPConfigApp.logger.info(getClass().getName() + " removeAllCentros: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
   }
   
   public void altaOtrosCentros(int cExp, ArrayList otrosCentros, JCYLUsuario jcylUsuario) throws SQLException, Exception
   {
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " altaOtrosCentros: Inicio");
       this.otrosCentrosOAD = OCAPOtrosCentrosOAD.getInstance();
 
       this.otrosCentrosOAD.altaOtrosCentros(cExp, otrosCentros, jcylUsuario);
 
       OCAPConfigApp.logger.info(getClass().getName() + " altaOtrosCentros: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
   }
 
   public ArrayList buscarOCAPOtrosCentros(Long cExp, Connection con) throws SQLException, Exception
   {
     ArrayList listado = new ArrayList();
     boolean bCrearConexion = false;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarOCAPOtrosCentros: Inicio");
 
       if (con == null) {
         con = JCYLGestionTransacciones.getConnection();
 
         bCrearConexion = true;
       }
 
       this.otrosCentrosOAD = OCAPOtrosCentrosOAD.getInstance();
 
       listado = this.otrosCentrosOAD.buscarOCAPOtrosCentros(cExp, con);
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarOCAPOtrosCentros: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     } finally {
       if (bCrearConexion)
       {
         JCYLGestionTransacciones.close(con.getAutoCommit());
       }
     }
 
     return listado;
   }
 
   public int bajaOCAPOtrosCentros(long cExp, String modificador, Connection con) throws SQLException, Exception
   {
     int fila = 0;
     boolean bCrearConexion = false;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " bajaOCAPOtrosCentros: Inicio");
 
       if (con == null) {
         con = JCYLGestionTransacciones.getConnection();
 
         bCrearConexion = true;
       }
 
       this.otrosCentrosOAD = OCAPOtrosCentrosOAD.getInstance();
       fila = this.otrosCentrosOAD.bajaOtrosCentros(cExp, modificador, con);
 
       OCAPConfigApp.logger.info(getClass().getName() + " bajaOCAPOtrosCentros: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     } finally {
       if (bCrearConexion)
       {
         JCYLGestionTransacciones.close(con.getAutoCommit());
       }
     }
 
     return fila;
   }
 
   public ArrayList buscarOCAPOtrosCentrosSolic(long cSolicitudId)
     throws SQLException, Exception
   {
     ArrayList listado = new ArrayList();
     boolean bCrearConexion = false;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarOCAPOtrosCentrosSolic: Inicio");
 
       this.otrosCentrosOAD = OCAPOtrosCentrosOAD.getInstance();
 
       listado = this.otrosCentrosOAD.buscarOCAPOtrosCentrosSolic(cSolicitudId);
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarOCAPOtrosCentrosSolic: Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return listado;
   }
 
   public void modificarOtrosCentros(int idExpediente, long cSolicitudId, String cUsumodi, Connection con) throws SQLException, Exception
   {
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " modificarOtrosCentros: Inicio");
       this.otrosCentrosOAD = OCAPOtrosCentrosOAD.getInstance();
       this.otrosCentrosOAD.modificarOtrosCentros(idExpediente, cSolicitudId, cUsumodi, con);
       OCAPConfigApp.logger.info(getClass().getName() + " modificarOtrosCentros: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
   }
 
   public void desasociarOtrosCentrosExpediente(long idExpediente, String cUsumodi, Connection con) throws SQLException, Exception
   {
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " desasociarOtrosCentrosExpediente: Inicio");
       this.otrosCentrosOAD = OCAPOtrosCentrosOAD.getInstance();
 
       this.otrosCentrosOAD.desasociarOtrosCentrosExpediente(idExpediente, cUsumodi, con);
 
       OCAPConfigApp.logger.info(getClass().getName() + " desasociarOtrosCentrosExpediente: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
   }
 }

