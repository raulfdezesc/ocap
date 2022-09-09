 package es.jcyl.fqs.ocap.ln.gerencias;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.gerencias.OCAPGerenciasOAD;
 import es.jcyl.fqs.ocap.oad.provincias.OCAPProvinciasOAD;
 import es.jcyl.fqs.ocap.ot.gerencias.OCAPGerenciasOT;
 import es.jcyl.fqs.ocap.ot.provincias.OCAPProvinciasOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPGerenciasLN
 {
   public Logger loggerBD;
   public Logger logger;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
 
     this.logger = OCAPConfigApp.logger;
   }
 
   public OCAPGerenciasLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPGerencias(OCAPGerenciasOT datos)
   {
     int result = 0;
     try
     {
       OCAPGerenciasOAD gerenciasOAD = OCAPGerenciasOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = gerenciasOAD.altaOCAPGerencias(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int bajaOCAPGerencias(int cGerenciaId)
   {
     int result = 0;
     try
     {
       OCAPGerenciasOAD gerenciasOAD = OCAPGerenciasOAD.getInstance();
       result = gerenciasOAD.bajaOCAPGerencias(cGerenciaId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public int modificacionOCAPGerencias(OCAPGerenciasOT datos)
   {
     int result = 0;
     try
     {
       OCAPGerenciasOAD gerenciasOAD = OCAPGerenciasOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = gerenciasOAD.modificacionOCAPGerencias(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return result;
   }
 
   public OCAPGerenciasOT buscarOCAPGerencias(long cGerenciaId)
     throws SQLException, Exception
   {
     OCAPGerenciasOT dato = null;
     try
     {
       OCAPConfigApp.logger.info("buscarOCAPGerencias");
       OCAPGerenciasOAD gerenciasOAD = OCAPGerenciasOAD.getInstance();
       dato = gerenciasOAD.buscarOCAPGerencias(cGerenciaId);
 
       OCAPProvinciasOAD provinciasOAD = OCAPProvinciasOAD.getInstance();
       dato.setDProvinciaNombre(provinciasOAD.buscarProvincia(dato.getCProvinciaId()).getDProvincia());
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
 
   public OCAPGerenciasOT buscaOCAPGerenciaLDAP(String codLdap)
   {
     OCAPGerenciasOT dato = null;
     ArrayList listado = null;
     try
     {
       OCAPGerenciasOAD gerenciasOAD = OCAPGerenciasOAD.getInstance();
       listado = gerenciasOAD.consultaOCAPGerencias(0L, "", 0L, "", "", codLdap, "", "", "", "", 0, 0);
       if ((listado != null) && (listado.size() > 0))
         dato = (OCAPGerenciasOT)listado.get(0);
     }
     catch (Exception e) {
       dato = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return dato;
   }
 
   public ArrayList listadoOCAPGerencias()
     throws Exception
   {
     ArrayList array = null;
     try
     {
       OCAPGerenciasOAD gerenciasOAD = OCAPGerenciasOAD.getInstance();
       array = gerenciasOAD.listadoOCAPGerencias();
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPGerenciasMasOtros()
     throws Exception
   {
     ArrayList array = null;
     OCAPGerenciasOT otros = new OCAPGerenciasOT();
     try
     {
       OCAPGerenciasOAD gerenciasOAD = OCAPGerenciasOAD.getInstance();
       array = gerenciasOAD.listadoOCAPGerencias();
       otros.setDNombre("Otros");
       array.add(otros);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public int listadoOCAPGerenciasCuenta(long cGerenciaId, String cProvinciaId, long cTipogerenciaId, String dNombre, String dNombreCorto, String aCodldap, String dGerente, String dDirector, String fCreacion, String fModificacion)
     throws Exception
   {
     int total = 0;
     try
     {
       OCAPGerenciasOAD gerenciasOAD = OCAPGerenciasOAD.getInstance();
       total = gerenciasOAD.listadoOCAPGerenciasCuenta(cGerenciaId, cProvinciaId, cTipogerenciaId, dNombre, dNombreCorto, aCodldap, dGerente, dDirector, fCreacion, fModificacion);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPGerencias(long cGerenciaId, String cProvinciaId, long cTipogerenciaId, String dNombre, String dNombreCorto, String aCodldap, String dGerente, String dDirector, String fCreacion, String fModificacion, int primerRegistro, int registrosPorPagina)
   {
     ArrayList array = null;
     try
     {
       OCAPGerenciasOAD gerenciasOAD = OCAPGerenciasOAD.getInstance();
       array = gerenciasOAD.consultaOCAPGerencias(cGerenciaId, cProvinciaId, cTipogerenciaId, dNombre, dNombreCorto, aCodldap, dGerente, dDirector, fCreacion, fModificacion, primerRegistro, registrosPorPagina);
     }
     catch (Exception e) {
       array = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return array;
   }
 
   public ArrayList listarGerencias(String idProv, String idTipoG)
     throws Exception
   {
     ArrayList resultado = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " listarGerencias: Inicio");
       OCAPGerenciasOAD gerenciasOAD = OCAPGerenciasOAD.getInstance();
       resultado = gerenciasOAD.listadoOCAPGerencias(idProv, idTipoG, Constantes.NO);
       OCAPConfigApp.logger.info(getClass().getName() + " listarGerencias: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return resultado;
   }
 
	public ArrayList listarGerenciasSolicitud(String idProv) throws Exception {
		ArrayList resultado = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " listarGerenciasSolicitud: Inicio");
			OCAPGerenciasOAD gerenciasOAD = OCAPGerenciasOAD.getInstance();
			resultado = gerenciasOAD.listadoOCAPGerenciasSolicitud(idProv, Constantes.NO);
			OCAPConfigApp.logger.info(getClass().getName() + " listarGerenciasSolicitud: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return resultado;
	}
 
   public OCAPGerenciasOT buscarOCAPGerenciasCodLDAP(String aCodLDAP)
     throws SQLException, Exception
   {
     OCAPGerenciasOT dato = null;
     try
     {
       OCAPConfigApp.logger.info("buscarOCAPGerencias");
       OCAPGerenciasOAD gerenciasOAD = OCAPGerenciasOAD.getInstance();
       dato = gerenciasOAD.buscarOCAPGerenciasCodLDAP(aCodLDAP);
 
       OCAPProvinciasOAD provinciasOAD = OCAPProvinciasOAD.getInstance();
       dato.setDProvinciaNombre(provinciasOAD.buscarProvincia(dato.getCProvinciaId()).getDProvincia());
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
 }

