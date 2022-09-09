 package es.jcyl.fqs.ocap.ln.convocatorias;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.convocatorias.OCAPConvocatoriasOAD;
 import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
 import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;
 
 public class OCAPConvocatoriasLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPConvocatoriasLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPConvocatorias(OCAPConvocatoriasOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
       result = convocatoriasOAD.altaOCAPConvocatorias(datos);
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
 
   public int bajaOCAPConvocatorias(long cConvocatoriaId)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
       result = convocatoriasOAD.bajaOCAPConvocatorias(cConvocatoriaId);
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
 
   public int modificacionOCAPConvocatorias(OCAPConvocatoriasOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
       result = convocatoriasOAD.modificacionOCAPConvocatorias(datos);
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
 
   public int listadoOCAPConvocatoriasCuenta(OCAPConvocatoriasOT datos)
     throws SQLException, Exception
   {
     int total = 0;
     try
     {
       OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
       total = convocatoriasOAD.listadoOCAPConvocatoriasCuenta(datos);
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
 
   public ArrayList consultaOCAPConvocatorias(OCAPConvocatoriasOT datos, int primerRegistro, int registrosPorPagina)
     throws SQLException, Exception
   {
     ArrayList array = null;
     try
     {
       OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
       array = convocatoriasOAD.consultaOCAPConvocatorias(datos, primerRegistro, registrosPorPagina);
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
 
   public ArrayList consultaOCAPConvocatoriasPorGradoId(long cGradoId)
     throws SQLException, Exception
   {
     ArrayList array = null;
     try
     {
       OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
       array = convocatoriasOAD.consultaOCAPConvocatoriasPorGradoId(cGradoId);
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
 
   public ArrayList listadoOCAPConvocatorias(int inicio, int cuantos)
     throws SQLException, Exception
   {
     ArrayList array = null;
     try
     {
       OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
       array = convocatoriasOAD.listadoOCAPConvocatorias(inicio, cuantos);
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
 
   public ArrayList listarConvocatorias()
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try
     {
       OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
       resultado = convocatoriasOAD.listadoOCAPConvocatorias();
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
 
   public OCAPConvocatoriasOT buscarOCAPConvocatorias(long cConvocatoriaId)
     throws SQLException, Exception
   {
     OCAPConvocatoriasOT dato = null;
     try
     {
       OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
       dato = convocatoriasOAD.buscarOCAPConvocatorias(cConvocatoriaId);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return dato;
   }
 
   public ArrayList listarConvocatoriasActivas()
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try
     {
       OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
       resultado = convocatoriasOAD.listarConvocatoriasActivas();
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
 
   public ArrayList listarConvocatoriasActivasAlta()
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try
     {
       OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
       resultado = convocatoriasOAD.listarConvocatoriasActivasAlta();
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
 
   public ArrayList obtenerConvocatorias(boolean orderName)
     throws SQLException, Exception
   {
     ArrayList<OCAPConvocatoriasOT> resultado = null;
     try
     {
       OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
       resultado = convocatoriasOAD.obtenerConvocatorias(orderName);
       Collections.sort(resultado);
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
 
   public ArrayList listarConvocatoriasActivasEvaluador(long cCompfqsId)
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try
     {
       OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
       resultado = convocatoriasOAD.listarConvocatoriasActivasEvaluador(cCompfqsId);
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
   
   public OCAPConvocatoriasOT buscarOCAPConvocatoriasPorExpediente(long cExpId)
		     throws SQLException, Exception
		   {
		     OCAPConvocatoriasOT dato = null;
		     try
		     {
		       OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
		       dato = convocatoriasOAD.buscarOCAPConvocatoriasPorExpediente(cExpId);
		     }
		     catch (SQLException exSQL) {
		       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
		       throw exSQL;
		     } catch (Exception e) {
		       this.logger.error(e);
		       throw e;
		     }
		 
		     return dato;
		   }
   
	public ArrayList listarConvocatoriasFinMc() throws SQLException, Exception {
		ArrayList resultado = null;
		try {
			OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
			resultado = convocatoriasOAD.listarConvocatoriasFinMc();
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
	
	public ArrayList listarConvocatoriasFinMa() throws SQLException, Exception {
		ArrayList resultado = null;
		try {
			OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
			resultado = convocatoriasOAD.listarConvocatoriasFinMa();
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

	public ArrayList consultaGradoDeConvocatoria(int cConvocatoriaId) {
		ArrayList resultado = null;
		
		try {
			OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
			resultado = convocatoriasOAD.consultaGradoDeConvocatoria(cConvocatoriaId);
		
		} catch (Exception e) {
			this.logger.error(e);
		}

		return resultado;
	}
	public String buscarOCAPConvocatoriasPorCA(long cConvoId)
		     throws SQLException, Exception
		   {
		     String dato = null;
		     try
		     {
		       OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
		       dato = convocatoriasOAD.buscarOCAPConvocatoriasPorCA(cConvoId);
		     }
		     catch (Exception e) {
		       this.logger.error(e);
		       throw e;
		     }
		 
		     return dato;
		   }

	   public ArrayList listarConvocatoriasEdicionPgp()
			     throws SQLException, Exception {
			ArrayList resultado = null;
			try {
				OCAPConvocatoriasOAD convocatoriasOAD = OCAPConvocatoriasOAD.getInstance();
				resultado = convocatoriasOAD.listarConvocatoriasEdicionPgp();
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
	
 }

