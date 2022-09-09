 package es.jcyl.fqs.ocap.ln.meritosCurriculares;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.meritosCurriculares.OCAPMeritoscurricularesOAD;
 import es.jcyl.fqs.ocap.ot.meritosCurriculares.OCAPMeritoscurricularesOT;
 import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPMeritoscurricularesLN
 {
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPMeritoscurricularesLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int bajaOCAPMeritoscurriculares(int cGerenciaId)
   {
     int result = 0;
     try
     {
       OCAPMeritoscurricularesOAD meritosCurricularesOAD = OCAPMeritoscurricularesOAD.getInstance();
       result = meritosCurricularesOAD.bajaOCAPMeritoscurriculares(cGerenciaId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPMeritoscurriculares(OCAPMeritoscurricularesOT datos)
     throws Exception
   {
     int result = 0;
     try
     {
       OCAPMeritoscurricularesOAD meritosCurricularesOAD = OCAPMeritoscurricularesOAD.getInstance();
       result = meritosCurricularesOAD.modificacionOCAPMeritoscurriculares(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public OCAPMeritoscurricularesOT buscarOCAPMeritoscurriculares(long cMeritoId)
   {
     OCAPMeritoscurricularesOT dato = null;
     try
     {
       OCAPMeritoscurricularesOAD meritosCurricularesOAD = OCAPMeritoscurricularesOAD.getInstance();
       dato = meritosCurricularesOAD.buscarOCAPMeritoscurriculares(cMeritoId);
     }
     catch (Exception e) {
       dato = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return dato;
   }
 
   public OCAPMeritoscurricularesOT buscarOCAPMeritoscurricularesPorNombre(String nombreMerito, String estatutId)
     throws SQLException, Exception
   {
     OCAPMeritoscurricularesOT dato = null;
     try
     {
       OCAPMeritoscurricularesOAD meritosCurricularesOAD = OCAPMeritoscurricularesOAD.getInstance();
       dato = meritosCurricularesOAD.buscarOCAPMeritoscurricularesPorNombre(nombreMerito, estatutId);
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
 
   public OCAPMeritoscurricularesOT buscarOCAPMeritoscurricularesPorUsuarioOT(OCAPUsuariosOT usuarioOT, Long cDefMeritoId, String bOpcionales, JCYLUsuario jcylUsuario, boolean buscaCV)
     throws SQLException, Exception
   {
     OCAPMeritoscurricularesOT dato = null;
     try
     {
       OCAPMeritoscurricularesOAD meritosCurricularesOAD = OCAPMeritoscurricularesOAD.getInstance();
       dato = meritosCurricularesOAD.buscarOCAPMeritoscurricularesPorUsuarioOT(usuarioOT, cDefMeritoId, bOpcionales, jcylUsuario, buscaCV);
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
 
   public ArrayList listadoOCAPMeritoscurriculares()
     throws Exception
   {
     ArrayList array = null;
     try
     {
       OCAPMeritoscurricularesOAD meritosCurricularesOAD = OCAPMeritoscurricularesOAD.getInstance();
       array = meritosCurricularesOAD.listadoOCAPMeritoscurriculares();
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public int listadoOCAPMeritoscurricularesCuenta(long cMeritoId, long cDefmeritoId, long cEstatutarioId, long cActividadId, long cModalidadId, long cFactorId, long cTipoId, String cTipoMerito, String dNombrecorto, String dNombre, String dDescripcion, String dObservaciones, String nCreditos, String fCreacion, String fModificacion)
     throws Exception
   {
     int total = 0;
     try
     {
       OCAPMeritoscurricularesOAD meritosCurricularesOAD = OCAPMeritoscurricularesOAD.getInstance();
       total = meritosCurricularesOAD.listadoOCAPMeritoscurricularesCuenta(cMeritoId, cDefmeritoId, cEstatutarioId, cActividadId, cModalidadId, cFactorId, cTipoId, cTipoMerito, dNombrecorto, dNombre, dDescripcion, dObservaciones, nCreditos, fCreacion, fModificacion);
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPMeritoscurriculares(long cMeritoId, long cDefmeritoId, long cEstatutarioId, long cActividadId, long cModalidadId, long cFactorId, long cTipoId, String cTipoMerito, String dNombrecorto, String dNombre, String dDescripcion, String dObservaciones, String nCreditos, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
   {
     ArrayList array = null;
     try
     {
       OCAPMeritoscurricularesOAD meritosCurricularesOAD = OCAPMeritoscurricularesOAD.getInstance();
       array = meritosCurricularesOAD.consultaOCAPMeritoscurriculares(cMeritoId, cDefmeritoId, cEstatutarioId, cActividadId, cModalidadId, cFactorId, cTipoId, cTipoMerito, dNombrecorto, dNombre, dDescripcion, dObservaciones, nCreditos, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     }
     catch (Exception e)
     {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }

	public OCAPMeritoscurricularesOT buscarOCAPMeritosValidadosPorUsuarioOT(OCAPUsuariosOT usuarioOT, Long cDefMeritoId,
			String bOpcionales, JCYLUsuario jcylUsuario) throws SQLException, Exception {
		OCAPMeritoscurricularesOT dato = null;
		try {
			OCAPMeritoscurricularesOAD meritosCurricularesOAD = OCAPMeritoscurricularesOAD.getInstance();
			dato = meritosCurricularesOAD.buscarOCAPMeritosValidadosPorUsuarioOT(usuarioOT, cDefMeritoId, bOpcionales,jcylUsuario);
		} catch (SQLException exSQL) {
			this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: "
					+ exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
			throw exSQL;
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return dato;
	}
   
 }

