 package es.jcyl.fqs.ocap.ln.centroTrabajo;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.centroTrabajo.OCAPCentroTrabajoOAD;
 import es.jcyl.fqs.ocap.ot.centroTrabajo.OCAPCentroTrabajoOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPCentroTrabajoLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPCentroTrabajoLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
	public ArrayList listarCentroTrabajo(String idGerencia, String codLdapGerencia) throws SQLException, Exception {
		ArrayList resultado = null;
		try {
			this.logger.info("Inicio");

			OCAPCentroTrabajoOAD centroTrabajoOAD = OCAPCentroTrabajoOAD.getInstance();
			resultado = centroTrabajoOAD.listadoOCAPCentroTrabajo(idGerencia, Constantes.NO, codLdapGerencia);

			this.logger.info("Fin");
		} catch (SQLException exSQL) {
			this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: "
					+ exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
			throw exSQL;
		} catch (Exception e) {
			this.logger.error(e);
			throw e;
		}

		return resultado;
	}
 
   public int altaOCAPCentroTrabajo(OCAPCentroTrabajoOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPCentroTrabajoOAD centroTrabajoOAD = OCAPCentroTrabajoOAD.getInstance();
       this.logger.info("LN");
       result = centroTrabajoOAD.altaOCAPCentroTrabajo(datos);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return result;
   }
 
   public int bajaOCAPCentroTrabajo(int cCentrotrabajoId)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPCentroTrabajoOAD centroTrabajoOAD = OCAPCentroTrabajoOAD.getInstance();
       result = centroTrabajoOAD.bajaOCAPCentroTrabajo(cCentrotrabajoId);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return result;
   }
 
   public int modificacionOCAPCentroTrabajo(OCAPCentroTrabajoOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPCentroTrabajoOAD centroTrabajoOAD = OCAPCentroTrabajoOAD.getInstance();
       this.logger.info("LN");
       result = centroTrabajoOAD.modificacionOCAPCentroTrabajo(datos);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return result;
   }
 
   public OCAPCentroTrabajoOT buscarOCAPCentroTrabajo(long cCentrotrabajoId)
     throws SQLException, Exception
   {
     OCAPCentroTrabajoOT dato = null;
     try
     {
       OCAPCentroTrabajoOAD centroTrabajoOAD = OCAPCentroTrabajoOAD.getInstance();
       dato = centroTrabajoOAD.buscarOCAPCentroTrabajo(cCentrotrabajoId);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e.toString());
       throw e;
     }
 
     return dato;
   }
 
   public int listadoOCAPCentroTrabajoCuenta(long cCentrotrabajoId, long cGerenciaId, String dNombre, String dDescripcion, String aObservaciones, String aDireccion, String aCodigopostal, String aTelefono, String aCiudad, String fCreacion, String fModificacion)
     throws SQLException, Exception
   {
     int total = 0;
     try
     {
       OCAPCentroTrabajoOAD centroTrabajoOAD = OCAPCentroTrabajoOAD.getInstance();
       total = centroTrabajoOAD.listadoOCAPCentroTrabajoCuenta(cCentrotrabajoId, cGerenciaId, dNombre, dDescripcion, aObservaciones, aDireccion, aCodigopostal, aTelefono, aCiudad, fCreacion, fModificacion);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPCentroTrabajo(long cCentrotrabajoId, long cGerenciaId, String dNombre, String dDescripcion, String aObservaciones, String aDireccion, String aCodigopostal, String aTelefono, String aCiudad, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
     throws SQLException, Exception
   {
     ArrayList array = null;
     try
     {
       OCAPCentroTrabajoOAD centroTrabajoOAD = OCAPCentroTrabajoOAD.getInstance();
       array = centroTrabajoOAD.consultaOCAPCentroTrabajo(cCentrotrabajoId, cGerenciaId, dNombre, dDescripcion, aObservaciones, aDireccion, aCodigopostal, aTelefono, aCiudad, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return array;
   }
 }

