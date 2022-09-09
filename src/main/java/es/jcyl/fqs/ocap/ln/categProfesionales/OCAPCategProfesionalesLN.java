 package es.jcyl.fqs.ocap.ln.categProfesionales;
 
 import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.oad.categProfesionales.OCAPCategProfesionalesOAD;
import es.jcyl.fqs.ocap.ot.categProfesionales.OCAPCategProfesionalesOT;
import es.jcyl.framework.JCYLUsuario;
 
 public class OCAPCategProfesionalesLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public void OCAPCategProfesionalesLN()
   {
   }
 
   public OCAPCategProfesionalesLN(JCYLUsuario jcylUsuario)
   {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPCategProfesionales(OCAPCategProfesionalesOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try {
       OCAPCategProfesionalesOAD categProfesionalesOAD = OCAPCategProfesionalesOAD.getInstance();
       this.logger.info("LN");
       result = categProfesionalesOAD.altaOCAPCategProfesionales(datos);
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
 
   public int bajaOCAPCategProfesionales(long cProfesionalId)
     throws SQLException, Exception
   {
     int result = 0;
     try {
       OCAPCategProfesionalesOAD categProfesionalesOAD = OCAPCategProfesionalesOAD.getInstance();
       result = categProfesionalesOAD.bajaOCAPCategProfesionales(cProfesionalId);
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
 
   public int modificacionOCAPCategProfesionales(OCAPCategProfesionalesOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try {
       OCAPCategProfesionalesOAD categProfesionalesOAD = OCAPCategProfesionalesOAD.getInstance();
       this.logger.info("LN");
       result = categProfesionalesOAD.modificacionOCAPCategProfesionales(datos);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return result;
   }
 
   public int listadoOCAPCategProfesionalesCuenta(long cProfesionalId, String dNombre, long cEstatutId, String dDescripcion, String fCreacion, String fModificacion, long cModalidadId)
     throws SQLException, Exception
   {
     int total = 0;
     try {
       OCAPCategProfesionalesOAD categProfesionalesOAD = OCAPCategProfesionalesOAD.getInstance();
       total = categProfesionalesOAD.listadoOCAPCategProfesionalesCuenta(cProfesionalId, dNombre, cEstatutId, dDescripcion, fCreacion, fModificacion, cModalidadId);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPCategProfesionales(long cProfesionalId, String dNombre, long cEstatutId, long cPersonalId, String dDescripcion, String fCreacion, String fModificacion, long cModalidadId, int primerRegistro, int registrosPorPagina)
     throws SQLException, Exception
   {
     ArrayList array = null;
     try {
       OCAPCategProfesionalesOAD categProfesionalesOAD = OCAPCategProfesionalesOAD.getInstance();
       array = categProfesionalesOAD.consultaOCAPCategProfesionales(cProfesionalId, dNombre, cEstatutId, cPersonalId, dDescripcion, fCreacion, fModificacion, cModalidadId, primerRegistro, registrosPorPagina);
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
 
	public ArrayList<OCAPCategProfesionalesOT> listarCategoriasRJ(String cJuridico) throws SQLException, Exception {
		ArrayList<OCAPCategProfesionalesOT> resultado = null;
		try {
			OCAPCategProfesionalesOAD categProfesionalesOAD = OCAPCategProfesionalesOAD.getInstance();
			resultado = categProfesionalesOAD.listarCategoriasRJ(cJuridico);
			Collections.sort(resultado);
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
 
	public ArrayList listarCategoriasRJFijo(String cJuridico) throws SQLException, Exception {
		ArrayList<OCAPCategProfesionalesOT> resultado = null;
		try {
			OCAPCategProfesionalesOAD categProfesionalesOAD = OCAPCategProfesionalesOAD.getInstance();
			resultado = categProfesionalesOAD.listarCategoriasRJFijo(cJuridico);
			Collections.sort(resultado);
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
 
   public ArrayList<OCAPCategProfesionalesOT> listadoOCAPCategProfesionales(int inicio, int cuantos)
     throws SQLException, Exception
   {
     ArrayList<OCAPCategProfesionalesOT> array = null;
     try {
       OCAPCategProfesionalesOAD categProfesionalesOAD = OCAPCategProfesionalesOAD.getInstance();
       array = categProfesionalesOAD.listadoOCAPCategProfesionales(inicio, cuantos);
       Collections.sort(array);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return array;
   }
 
   public ArrayList<OCAPCategProfesionalesOT> listarCategoriasProfesionales()
     throws SQLException, Exception
   {
     ArrayList<OCAPCategProfesionalesOT> resultado = null;
     try
     {
       this.logger.info("Inicio");
       OCAPCategProfesionalesOAD categProfesionalesOAD = OCAPCategProfesionalesOAD.getInstance();
       resultado = categProfesionalesOAD.listadoOCAPCategProfesionales();
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
 
   public OCAPCategProfesionalesOT buscarOCAPCategProfesionales(long cProfesionalId)
     throws SQLException, Exception
   {
     OCAPCategProfesionalesOT dato = null;
     try
     {
       OCAPCategProfesionalesOAD categProfesionalesOAD = OCAPCategProfesionalesOAD.getInstance();
       dato = categProfesionalesOAD.buscarOCAPCategProfesionales(cProfesionalId);
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
 
   public ArrayList<OCAPCategProfesionalesOT> listarCategoriasProfesionales(String tabla)
     throws SQLException, Exception
   {
     ArrayList<OCAPCategProfesionalesOT> resultado = null;
     try
     {
       this.logger.info("Inicio");
       OCAPCategProfesionalesOAD categProfesionalesOAD = OCAPCategProfesionalesOAD.getInstance();
       resultado = categProfesionalesOAD.listarCategoriasProfesionales(tabla);
       Collections.sort(resultado);
       this.logger.info("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return resultado;
   }
 }

