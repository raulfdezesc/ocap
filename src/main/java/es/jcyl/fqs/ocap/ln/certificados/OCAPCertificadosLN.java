 package es.jcyl.fqs.ocap.ln.certificados;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.db.OCAPConexionDB;
 import es.jcyl.fqs.ocap.oad.certificados.OCAPCertificadosOAD;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.sql.Connection;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPCertificadosLN
 {
   OCAPCertificadosOAD certificadosOAD;
   private JCYLUsuario jcylUsuario;
 
   public OCAPCertificadosLN()
   {
   }
 
   public OCAPCertificadosLN(JCYLUsuario jcylUsuario)
   {
     this.jcylUsuario = jcylUsuario;
   }
 
   public void altaCertificados(Integer cExp, ArrayList certificados, String creado, String documento, JCYLUsuario jcylUsuario) throws SQLException, Exception
   {
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " altaCertificados: Inicio");
       this.certificadosOAD = OCAPCertificadosOAD.getInstance();
 
       this.certificadosOAD.altaCertificados(cExp, certificados, creado, documento, jcylUsuario);
 
       OCAPConfigApp.logger.info(getClass().getName() + " actualizarSupervisor: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
   }
 
   public ArrayList buscarOCAPCertificados(Long cExp)
     throws SQLException, Exception
   {
     Connection con = null;
     ArrayList listado = new ArrayList();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " altaCertificados: Inicio");
 
       con = OCAPConexionDB.getDBConnection(null, this.jcylUsuario);
 
       this.certificadosOAD = OCAPCertificadosOAD.getInstance();
 
       listado = this.certificadosOAD.buscarOCAPCertificados(cExp, con);
 
       OCAPConfigApp.logger.info(getClass().getName() + " actualizarSupervisor: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
     finally
     {
       OCAPConexionDB.freeConnection(con.getAutoCommit());
     }
 
     return listado;
   }
 
   public ArrayList buscarOCAPCertificadosDoc(long cExp, String cDoc) throws SQLException, Exception
   {
     Connection con = null;
     ArrayList listado = new ArrayList();
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarOCAPCertificadosDoc: Inicio");
 
       con = OCAPConexionDB.getDBConnection(null, this.jcylUsuario);
 
       this.certificadosOAD = OCAPCertificadosOAD.getInstance();
 
       listado = this.certificadosOAD.buscarOCAPCertificadosDoc(cExp, cDoc, con);
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarOCAPCertificadosDoc: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
     finally
     {
       OCAPConexionDB.freeConnection(con.getAutoCommit());
     }
 
     return listado;
   }
 }

