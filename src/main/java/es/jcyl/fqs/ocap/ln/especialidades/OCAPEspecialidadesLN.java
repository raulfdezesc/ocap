 package es.jcyl.fqs.ocap.ln.especialidades;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.especialidades.OCAPEspecialidadesOAD;
import es.jcyl.fqs.ocap.ot.centroTrabajo.OCAPCentroTrabajoOT;
import es.jcyl.fqs.ocap.ot.especialidades.OCAPEspecialidadesOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;
 
 public class OCAPEspecialidadesLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPEspecialidadesLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList<OCAPEspecialidadesOT> listarEspecialidades(long idCategProfesional)
     throws SQLException, Exception
   {
     ArrayList<OCAPEspecialidadesOT> resultado = null;
     try
     {
       this.logger.info("Inicio");
       OCAPEspecialidadesOAD especialidadesOAD = OCAPEspecialidadesOAD.getInstance();
       resultado = especialidadesOAD.listadoOCAPEspecialidades(idCategProfesional, Constantes.NO);
       Collections.sort(resultado);
       this.logger.info("Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return resultado;
   }
 
   public int altaOCAPEspecialidades(OCAPEspecialidadesOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPEspecialidadesOAD especialidadesOAD = OCAPEspecialidadesOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = especialidadesOAD.altaOCAPEspecialidades(datos);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return result;
   }
 
   public int bajaOCAPEspecialidades(int cEspecId)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPEspecialidadesOAD especialidadesOAD = OCAPEspecialidadesOAD.getInstance();
       result = especialidadesOAD.bajaOCAPEspecialidades(cEspecId);
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
 
   public int modificacionOCAPEspecialidades(OCAPEspecialidadesOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPEspecialidadesOAD especialidadesOAD = OCAPEspecialidadesOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = especialidadesOAD.modificacionOCAPEspecialidades(datos);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return result;
   }
 
   public OCAPEspecialidadesOT buscarOCAPEspecialidades(long cEspecId)
     throws SQLException, Exception
   {
     OCAPEspecialidadesOT dato = null;
     try
     {
       OCAPEspecialidadesOAD especialidadesOAD = OCAPEspecialidadesOAD.getInstance();
       dato = especialidadesOAD.buscarOCAPEspecialidades(cEspecId);
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
 
   public int listadoOCAPEspecialidadesCuenta(long cEspecId, long cProfesionalId, String dNombre, String dDescripcion, String fCreacion, String fModificacion)
     throws SQLException, Exception
   {
     int total = 0;
     try
     {
       OCAPEspecialidadesOAD especialidadesOAD = OCAPEspecialidadesOAD.getInstance();
       total = especialidadesOAD.listadoOCAPEspecialidadesCuenta(cEspecId, cProfesionalId, dNombre, dDescripcion, fCreacion, fModificacion);
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
 
   public ArrayList<OCAPEspecialidadesOT> consultaOCAPEspecialidades(long cEspecId, long cProfesionalId, String dNombre, String dDescripcion, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
     throws SQLException, Exception
   {
     ArrayList<OCAPEspecialidadesOT> array = null;
     try
     {
       OCAPEspecialidadesOAD especialidadesOAD = OCAPEspecialidadesOAD.getInstance();
       array = especialidadesOAD.consultaOCAPEspecialidades(cEspecId, cProfesionalId, dNombre, dDescripcion, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
       Collections.sort(array);
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
 
   public ArrayList listarEspecialidadesServicio()
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try {
       this.logger.info("Inicio");
       OCAPEspecialidadesOAD especialidadesOAD = OCAPEspecialidadesOAD.getInstance();
       resultado = especialidadesOAD.listarEspecialidadesServicio();
       this.logger.info("Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return resultado;
   }
 }

