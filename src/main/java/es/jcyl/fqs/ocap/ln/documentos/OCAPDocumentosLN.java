 package es.jcyl.fqs.ocap.ln.documentos;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.documentos.OCAPDocumentosOAD;
 import es.jcyl.fqs.ocap.ot.documentos.OCAPDocumentosOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPDocumentosLN
 {
   Logger logger;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
   public OCAPDocumentosLN() {
     $init$();
   }
 
   public void guardarDocumento(OCAPDocumentosOT documentosOT)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " guardarDocumento: Inicio: ");
     long cDocumento = 0L;
     try
     {
       JCYLGestionTransacciones.open(false);
       OCAPDocumentosOAD documentosOAD = OCAPDocumentosOAD.getInstance();
       cDocumento = documentosOAD.altaOCAPDocFormulario(documentosOT);
       documentosOT.setCDocumento_id(cDocumento);
       documentosOAD.guardarDatosBlob(documentosOT);
       JCYLGestionTransacciones.commit(true);
     }
     catch (SQLException e) {
       JCYLGestionTransacciones.rollback();
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
     OCAPConfigApp.logger.info(getClass().getName() + " guardarDocumento: Fin: ");
   }
 
   public void modificarDocumento(OCAPDocumentosOT documentosOT)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " modificarDocumento: Inicio: ");
     long cDocumento = 0L;
     try
     {
       JCYLGestionTransacciones.open(false);
       OCAPDocumentosOAD documentosOAD = OCAPDocumentosOAD.getInstance();
       cDocumento = documentosOAD.modificarDocumento(documentosOT);
       if (documentosOT.getADatos() != null) {
         documentosOT.setCDocumento_id(cDocumento);
         documentosOAD.guardarDatosBlob(documentosOT);
       }
       JCYLGestionTransacciones.commit(true);
     }
     catch (SQLException e) {
       JCYLGestionTransacciones.rollback();
       OCAPConfigApp.logger.info(getClass().getName() + " modificarDocumento: ERROR: " + e.toString());
       throw e;
     } catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       OCAPConfigApp.logger.info(getClass().getName() + " modificarDocumento: ERROR: " + e.toString());
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
     OCAPConfigApp.logger.info(getClass().getName() + " modificarDocumento: Fin: ");
   }
 
   public void modificarDocumentoRepeticion(OCAPDocumentosOT documentosOT, int nRepeticion)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " modificarDocumentoRepeticion: Inicio: ");
     long cDocumento = 0L;
     try
     {
       JCYLGestionTransacciones.open(false);
       OCAPDocumentosOAD documentosOAD = OCAPDocumentosOAD.getInstance();
       cDocumento = documentosOAD.modificarDocumentoRepeticion(documentosOT, nRepeticion);
       JCYLGestionTransacciones.commit(true);
     }
     catch (SQLException e) {
       JCYLGestionTransacciones.rollback();
       OCAPConfigApp.logger.info(getClass().getName() + " modificarDocumentoRepeticion: ERROR: " + e.toString());
       throw e;
     } catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       OCAPConfigApp.logger.info(getClass().getName() + " modificarDocumentoRepeticion: ERROR: " + e.toString());
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
     OCAPConfigApp.logger.info(getClass().getName() + " modificarDocumentoRepeticion: Fin: ");
   }
 
   public ArrayList buscarDocumentos(long CExpId)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentos: Inicio");
     ArrayList listado = null;
     try
     {
       OCAPDocumentosOAD documentosOAD = OCAPDocumentosOAD.getInstance();
       listado = documentosOAD.buscarDocumentos(CExpId);
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentos: Fin");
 
     return listado;
   }
 
   public ArrayList buscarDocumentosRellenables(long CExpId, long itinerarioId)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentosRellenables: Inicio");
     ArrayList listado = null;
     try
     {
       OCAPDocumentosOAD documentosOAD = OCAPDocumentosOAD.getInstance();
       listado = documentosOAD.buscarDocumentosRellenables(CExpId, itinerarioId);
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentosRellenables: Fin");
 
     return listado;
   }
 
   public ArrayList buscarDocumentosCuestionario(long CExpId, long cCuestionarioId, long cEvidenciaId)
     throws SQLException, Exception
   {
     ArrayList listado = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentosCuestionario: Inicio");
       OCAPDocumentosOAD documentosOAD = OCAPDocumentosOAD.getInstance();
       listado = documentosOAD.buscarDocumentosCuestionario(CExpId, cCuestionarioId, cEvidenciaId);
       OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentosCuestionario: Fin");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return listado;
   }
 
   public ArrayList buscarDocumentosRepeticiones(long CExpId)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentosRepeticiones: Inicio");
     ArrayList listado = null;
     try
     {
       OCAPDocumentosOAD documentosOAD = OCAPDocumentosOAD.getInstance();
       listado = documentosOAD.buscarDocumentosRepeticiones(CExpId);
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentosRepeticiones: Fin");
 
     return listado;
   }
 
   public OCAPDocumentosOT buscarDocumento(long cDocumento)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumento: Inicio");
     OCAPDocumentosOT documentosOT = null;
     try
     {
       OCAPDocumentosOAD documentosOAD = OCAPDocumentosOAD.getInstance();
       documentosOT = documentosOAD.buscarDocumento(cDocumento);
     }
     catch (SQLException e) {
       this.logger.error(e.toString());
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumento: Fin");
 
     return documentosOT;
   }
 
   public OCAPDocumentosOT buscarDocumentoExpedienteTitulo(long cExpId, String dTitulo)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumento: Inicio");
     OCAPDocumentosOT documentosOT = null;
     try
     {
       OCAPDocumentosOAD documentosOAD = OCAPDocumentosOAD.getInstance();
       documentosOT = documentosOAD.buscarDocumentoExpedienteTitulo(cExpId, dTitulo);
     }
     catch (SQLException e) {
       this.logger.error(e.toString());
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentoExpedienteTitulo: Fin");
 
     return documentosOT;
   }
 
   public void borrarDocumento(long cDocumento)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " borrarDocumento: Inicio");
     try
     {
       OCAPDocumentosOAD documentosOAD = OCAPDocumentosOAD.getInstance();
       documentosOAD.borrarDocumento(cDocumento);
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
     OCAPConfigApp.logger.info(getClass().getName() + " borrarDocumento: Fin");
   }
 
   public OCAPDocumentosOT buscarDocumentoEvidencia(long cExpedienteId)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentoEvidencia: Inicio");
     OCAPDocumentosOT documentosOT = null;
     try
     {
       OCAPDocumentosOAD documentosOAD = OCAPDocumentosOAD.getInstance();
       documentosOT = documentosOAD.buscarDocumentoEvidencia(cExpedienteId);
     }
     catch (SQLException e) {
       this.logger.error(e.toString());
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentoEvidencia: Fin");
 
     return documentosOT;
   }
 }

