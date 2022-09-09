 package es.jcyl.fqs.ocap.ln.expedienteCarrera;
 
 import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.creditosCuestionarios.OCAPCreditosCuestionariosLN;
import es.jcyl.fqs.ocap.ln.cuestionarios.OCAPCuestionariosLN;
import es.jcyl.fqs.ocap.ln.dossier.OCAPDossierLN;
import es.jcyl.fqs.ocap.ln.encuestaCalidad.OCAPEncuestaCalidadLN;
import es.jcyl.fqs.ocap.ln.estadosCuestionario.OCAPEstadosCuestionarioLN;
import es.jcyl.fqs.ocap.oad.expedienteCarrera.OCAPExpedientecarreraOAD;
import es.jcyl.fqs.ocap.oad.expedientesexclusion.OCAPExpedientesExclusionOAD;
import es.jcyl.fqs.ocap.oad.solicitudes.OCAPNuevaSolicitudOAD;
import es.jcyl.fqs.ocap.oad.subsanaciones.OCAPSubsanacionesOAD;
import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;
import es.jcyl.fqs.ocap.ot.expedientesexclusion.OCAPExpedientesExclusionOT;
import es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT;
import es.jcyl.fqs.ocap.ot.subsanaciones.OCAPSubsanacionesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 
 /**
 * @author 
 *
 */
public class OCAPExpedienteCarreraLN
 {
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPExpedienteCarreraLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public OCAPExpedientecarreraOT buscarOCAPExpedienteCarrera(Long cExpId)
     throws Exception
   {
     OCAPExpedientecarreraOT expCarreraOT = null;
     try
     {
       OCAPExpedientecarreraOAD expCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
       expCarreraOT = expCarreraOAD.buscarOCAPExpedientecarrera(cExpId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return expCarreraOT;
   }
 
   public OCAPExpedientecarreraOT buscarOCAPExpedienteCarrera(OCAPExpedientecarreraOT datos)
     throws Exception
   {
     OCAPExpedientecarreraOT expCarreraOT = null;
     try
     {
       OCAPExpedientecarreraOAD expCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
       expCarreraOT = expCarreraOAD.buscarOCAPExpedientecarrera(datos.getCExpId());
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return expCarreraOT;
   }
 
   public OCAPExpedientecarreraOT buscarOCAPExpedientecarreraPorUsuario(Long cUsrId)
     throws SQLException, Exception
   {
     OCAPExpedientecarreraOT expCarreraOT = null;
     try
     {
       OCAPExpedientecarreraOAD expCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
       expCarreraOT = expCarreraOAD.buscarOCAPExpedientecarreraPorUsuario(cUsrId);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return expCarreraOT;
   }
 
   public OCAPExpedientecarreraOT buscarExpedienteCarreraPorUsuarioConvocatoria(Long cUsrId, Long cConvocatoriaId)
     throws SQLException, Exception
   {
     OCAPExpedientecarreraOT expCarreraOT = null;
     try
     {
       OCAPExpedientecarreraOAD expCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
       expCarreraOT = expCarreraOAD.buscarExpedienteCarreraPorUsuarioConvocatoria(cUsrId, cConvocatoriaId);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return expCarreraOT;
   }
 
   public long buscarCExpIdPorUsuarioConvocatoria(long cUsrId, long cConvocatoriaId)
     throws Exception
   {
     long result = 0L;
     try
     {
       OCAPExpedientecarreraOAD expCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
       result = expCarreraOAD.buscarCExpIdPorUsuarioConvocatoria(cUsrId, cConvocatoriaId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public int modificacionOCAPExpedientecarrera(OCAPExpedientecarreraOT datos, boolean sobreescribirExpdte)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPConfigApp.logger.info("modificacionOCAPExpedientecarrera");
       OCAPExpedientecarreraOAD expCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
       result = expCarreraOAD.modificacionOCAPExpedientecarrera(datos, sobreescribirExpdte, false);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public int eliminarItinerarioAsignado(long cExpId, JCYLUsuario jcylUsuario) throws Exception
   {
     int result = 0;
     OCAPEncuestaCalidadLN encuestaLN = null;
     OCAPDossierLN dossierLN = null;
     OCAPCreditosCuestionariosLN credCuestLN = null;
     OCAPCuestionariosLN cuestLN = null;
     OCAPEstadosCuestionarioLN estadoLN = null;
     try
     {
       OCAPConfigApp.logger.info("LN");
 
       OCAPExpedientecarreraOAD expCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
       JCYLGestionTransacciones.open(false);
       Connection con = JCYLGestionTransacciones.getConnection();
       result = expCarreraOAD.eliminarItinerarioAsignado(cExpId, jcylUsuario.getUser().getC_usr_id());
       encuestaLN = new OCAPEncuestaCalidadLN(jcylUsuario);
       encuestaLN.eliminaEncuesta(cExpId, con);
       dossierLN = new OCAPDossierLN(jcylUsuario);
       dossierLN.eliminarDossier(cExpId);
       credCuestLN = new OCAPCreditosCuestionariosLN(jcylUsuario);
       credCuestLN.borrarOCAPCreditosCuestionarioExpId(cExpId, con);
       cuestLN = new OCAPCuestionariosLN(jcylUsuario);
       cuestLN.borrarRespuestasExpediente(cExpId);
       estadoLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
       estadoLN.borraOCAPEstadosCuestionarioExpediente(cExpId);
 
       JCYLGestionTransacciones.commit(true);
     }
     catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
 
     return result;
   }
 
   public int insertarExpedienteCarrera_a(OCAPExpedientecarreraOT datos) throws Exception
   {
     int result = 0;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " insertarExpedienteCarrera_a: Inicio");
       OCAPExpedientecarreraOAD expCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
       result = expCarreraOAD.altaOCAPExpedientecarrera(datos);
       OCAPConfigApp.logger.info(getClass().getName() + " insertarExpedienteCarrera_a: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
     finally
     {
     }
     return result;
   }
 
   public OCAPExpedientecarreraOT buscarExpediente(OCAPExpedientecarreraOT expedienteOT)
     throws Exception
   {
     OCAPExpedientecarreraOT expCarreraOT = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarExpediente: Inicio");
       OCAPExpedientecarreraOAD expedientesOAD = OCAPExpedientecarreraOAD.getInstance();
       expCarreraOT = expedientesOAD.buscarExpediente_a(expedienteOT);
       OCAPConfigApp.logger.info(getClass().getName() + " buscarExpediente: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return expCarreraOT;
   }
 
   public ArrayList buscarOCAPExpedienteRechaz(long cUsrId)
     throws Exception
   {
     ArrayList expedientes = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarOCAPExpedienteRechaz: Inicio");
       OCAPExpedientecarreraOAD expedientesOAD = OCAPExpedientecarreraOAD.getInstance();
       expedientes = expedientesOAD.buscarOCAPExpedienteRechaz(cUsrId);
       OCAPConfigApp.logger.info(getClass().getName() + " buscarOCAPExpedienteRechaz: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return expedientes;
   }
 
   public int bajaOCAPExpedientecarrera(Long cExpId) throws Exception
   {
     int result = 0;
     try
     {
       OCAPConfigApp.logger.info("LN");
       OCAPExpedientecarreraOAD expCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
       result = expCarreraOAD.bajaOCAPExpedientecarrera(cExpId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public int modificarEstadoExpCarrera(long cExpId, long estado, boolean modificarFechaRenuncia)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPConfigApp.logger.info("modificacionOCAPEstadoExpCarrera");
       OCAPExpedientecarreraOAD expCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
       result = expCarreraOAD.modificarEstadoExpCarrera(cExpId, estado, this.jcylUsuario.getUsuario().getC_usr_id(), modificarFechaRenuncia);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public String buscarDNIUsuarioExpediente(long cExpId)
     throws SQLException, Exception
   {
     String c_dni = "";
     try
     {
       OCAPExpedientecarreraOAD expCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
       c_dni = expCarreraOAD.buscarDNIUsuarioExpediente(cExpId);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return c_dni;
   }
 
   public void cambiarEstadoExpediente(OCAPExpedientecarreraOT expedienteCarreraOT, boolean bExclusionAnterior)
     throws Exception
   {
     try
     {
    	 
       OCAPConfigApp.logger.info(getClass().getName() + " cambiarEstadoExpediente: Inicio");
       boolean resetearFechasCC = false;
 
       JCYLGestionTransacciones.open(false);
 
       if (expedienteCarreraOT.getCEstadoId() == 3)
       {
         if (bExclusionAnterior) {
           OCAPExpedientesExclusionOT expedientesExclusionOT = new OCAPExpedientesExclusionOT();
           expedientesExclusionOT.setCUsumodi(this.jcylUsuario.getUsuario().getC_usr_id());
           expedientesExclusionOT.setCExpId(expedienteCarreraOT.getCExpId().longValue());
           OCAPExpedientesExclusionOAD expedientesExclusionOAD = OCAPExpedientesExclusionOAD.getInstance();
           expedientesExclusionOAD.bajaExpediente(expedientesExclusionOT);
           expedientesExclusionOT = null;
           resetearFechasCC = true;
         }
       }
       else if (expedienteCarreraOT.getCEstadoId() == 4)
       {
         OCAPExpedientesExclusionOAD expedientesExclusionOAD = OCAPExpedientesExclusionOAD.getInstance();
 
         if (bExclusionAnterior) {
           OCAPExpedientesExclusionOT expedientesExclusionOT = new OCAPExpedientesExclusionOT();
           expedientesExclusionOT.setCUsumodi(this.jcylUsuario.getUsuario().getC_usr_id());
           expedientesExclusionOT.setCExpId(expedienteCarreraOT.getCExpId().longValue());
           expedientesExclusionOAD.bajaExpediente(expedientesExclusionOT);
           expedientesExclusionOT = null;
         }
 
         expedientesExclusionOAD.altaMotivosExpediente(expedienteCarreraOT.getMotivosExclusion());
       }
       else if (expedienteCarreraOT.getCEstadoId() == 6)
       {
         OCAPSubsanacionesOT subsanacionesOT = new OCAPSubsanacionesOT();
         subsanacionesOT.setCSolicitudId(expedienteCarreraOT.getCSolicitudId());
         subsanacionesOT.setFPeticion(new Date());
         subsanacionesOT.setAPeticion(expedienteCarreraOT.getAPeticionSubsanacion());
         subsanacionesOT.setACreadoPor(this.jcylUsuario.getUsuario().getC_usr_id());
 
         OCAPSubsanacionesOAD subsanacionesOAD = OCAPSubsanacionesOAD.getInstance();
         subsanacionesOAD.altaSubsanaciones(subsanacionesOT);
         subsanacionesOT = null;
       }
 
       OCAPSolicitudesOT solicitudOT = new OCAPSolicitudesOT();
       solicitudOT.setCSolicitudId(expedienteCarreraOT.getCSolicitudId());
       solicitudOT.setCEstado_id(expedienteCarreraOT.getCEstadoId());
       solicitudOT.setAModificadoPor(this.jcylUsuario.getUsuario().getC_usr_id());
 
       OCAPNuevaSolicitudOAD solicitudesOAD = OCAPNuevaSolicitudOAD.getInstance();
       solicitudesOAD.modificarSolicitud(solicitudOT);
       solicitudOT = null;
 
       OCAPExpedientecarreraOAD expCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
       expCarreraOAD.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false, resetearFechasCC);
 
       JCYLGestionTransacciones.commit(true);
 
       OCAPConfigApp.logger.info(getClass().getName() + " cambiarEstadoExpediente: Inicio");
     }
     catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
   }
 
   public int modificacionFechaOCAPExpedientecarrera(OCAPExpedientecarreraOT datos, boolean flagExcluidaAceptada)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPConfigApp.logger.info("modificacionOCAPExpedientecarrera");
       OCAPExpedientecarreraOAD expCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
       
       //OCAP-1530
       if (flagExcluidaAceptada) {
           OCAPExpedientesExclusionOT expedientesExclusionOT = new OCAPExpedientesExclusionOT();
           expedientesExclusionOT.setCUsumodi(this.jcylUsuario.getUsuario().getC_usr_id());
           expedientesExclusionOT.setCExpId(datos.getCExpId().longValue());
           OCAPExpedientesExclusionOAD expedientesExclusionOAD = OCAPExpedientesExclusionOAD.getInstance();
           expedientesExclusionOAD.bajaExpediente(expedientesExclusionOT);
           expedientesExclusionOT = null;
         }
       result = expCarreraOAD.modificacionFechaOCAPExpedientecarrera(datos, flagExcluidaAceptada);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }

   public void finalizarFaseMeritos(String cExp_id, String usuMod) {
	     try
	     {
	       OCAPConfigApp.logger.info("finalizarFaseMeritos");
	       OCAPExpedientecarreraOAD expCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
	       expCarreraOAD.finalizarFaseMeritos(cExp_id, usuMod);
	     }
	     catch (SQLException exSQL) {
	    	 OCAPConfigApp.logger.error(Utilidades.getStackTrace(exSQL));
	     } catch (Exception e) {
	       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
	     }
	
   }
   
   
	/**
	 * @param cExpId
	 * @return
	 * @throws Exception
	 */
	public Integer buscarModAnteriorExpedienteCarrera(Long cExpId) {
		Integer modAnterior = null;
		try {
			OCAPExpedientecarreraOAD expCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
			modAnterior = expCarreraOAD.buscarModAnteriorExpedienteCarrera(cExpId);
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
		}

		return modAnterior;
	}
	
	 public int modificacionFechaMA(OCAPExpedientecarreraOT datos)
		     throws SQLException, Exception
		   {
		     int result = 0;
		     try
		     {
		       OCAPConfigApp.logger.info("modificacionOCAPExpedientecarrera");
		       OCAPExpedientecarreraOAD expCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
		       result= expCarreraOAD.modificacionFechaMA(datos);
		     }
		     catch (SQLException exSQL) {
		       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
		       throw exSQL;
		     } catch (Exception e) {
		       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
		       throw e;
		     }
		 
		     return result;
		   }

	public int desbloquearMeritosCurriculares(OCAPExpedientecarreraOT expedienteOT) throws Exception {
		int result = 0;
		try {
			OCAPConfigApp.logger.info("desbloquearMeritosCurriculares");
			OCAPExpedientecarreraOAD expCarreraOAD = OCAPExpedientecarreraOAD.getInstance();
			result = expCarreraOAD.desbloquearMeritosCurriculares(expedienteOT);
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return result;
	}
 }

