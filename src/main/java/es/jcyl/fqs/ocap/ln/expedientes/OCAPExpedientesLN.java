 package es.jcyl.fqs.ocap.ln.expedientes;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.expedientes.OCAPExpedientesOAD;
 import es.jcyl.fqs.ocap.ot.expedientes.OCAPExpedientesOT;
 import es.jcyl.framework.JCYLUsuario;
import es.jcyl.framework.db.JCYLGestionTransacciones;

import java.sql.Connection;
import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPExpedientesLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPExpedientesLN(JCYLUsuario jcylUsuario)
   {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList listarExpedientes()
     throws SQLException, Exception
   {
     return listarExpedientes(null);
   }
 
   public ArrayList listarExpedientes(OCAPExpedientesOT datosBusqueda) throws SQLException, Exception
   {
     ArrayList listado = null;
     try
     {
       OCAPExpedientesOAD expedientesOAD = OCAPExpedientesOAD.getInstance();
       listado = expedientesOAD.listarExpedientes(datosBusqueda);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return listado;
   }
 
   public int obtenerTotalExpedientes()
     throws SQLException, Exception
   {
     return obtenerTotalExpedientes(null);
   }
 
   public int obtenerTotalExpedientes(OCAPExpedientesOT datosBusqueda) throws SQLException, Exception {
     int total = 0;
     try
     {
       OCAPExpedientesOAD expedientesOAD = OCAPExpedientesOAD.getInstance();
       total = expedientesOAD.obtenerTotalExpedientes(datosBusqueda);
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
 
   public ArrayList listarPosiblesEvaluados(OCAPExpedientesOT datosBusqueda)
     throws SQLException, Exception
   {
     ArrayList listado = null;
     try
     {
       OCAPExpedientesOAD expedientesOAD = OCAPExpedientesOAD.getInstance();
       listado = expedientesOAD.listarPosiblesEvaluados(datosBusqueda);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return listado;
   }
   
	public Integer ejecutarProcMc(Integer convocatoriaId, String usuario) throws SQLException, Exception {
		Integer retorno;
		Connection con = null;
		try {
			con = JCYLGestionTransacciones.getConnection();
			con.setAutoCommit(false);
			OCAPExpedientesOAD expedientesOAD = OCAPExpedientesOAD.getInstance();
			retorno = expedientesOAD.ejecutarProcMc(convocatoriaId, usuario, con);
			con.commit();
		} catch (Exception e) {
			con.rollback();
			this.logger.error(e);
			throw e;
		}finally {
			con.setAutoCommit(true);
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return retorno;
	}
	
	public Integer ejecutarProcMa(Integer convocatoriaId, String usuario) throws SQLException, Exception {
		Integer retorno;
		Connection con = null;
		try {
			con = JCYLGestionTransacciones.getConnection();
			con.setAutoCommit(false);
			OCAPExpedientesOAD expedientesOAD = OCAPExpedientesOAD.getInstance();
			retorno = expedientesOAD.ejecutarProcMa(convocatoriaId, usuario, con);
			con.commit();
		} catch (Exception e) {
			con.rollback();
			this.logger.error(e);
			throw e;
		}finally {
			con.setAutoCommit(true);
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return retorno;
	}
 }

