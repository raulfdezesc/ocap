 package es.jcyl.fqs.ocap.ln.grado;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.grado.OCAPGradoOAD;
 import es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT;
 import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPGradoLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPGradoLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPGrado(OCAPGradoOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try {
       OCAPGradoOAD gradoOAD = OCAPGradoOAD.getInstance();
       this.logger.info("LN");
       result = gradoOAD.altaOCAPGrado(datos);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return result;
   }
 
   public int bajaOCAPGrado(int cGradoId)
     throws SQLException, Exception
   {
     int result = 0;
     try {
       OCAPGradoOAD gradoOAD = OCAPGradoOAD.getInstance();
       result = gradoOAD.bajaOCAPGrado(cGradoId);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return result;
   }
 
   public int modificacionOCAPGrado(OCAPGradoOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try {
       OCAPGradoOAD gradoOAD = OCAPGradoOAD.getInstance();
       this.logger.info("LN");
       result = gradoOAD.modificacionOCAPGrado(datos);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return result;
   }
 
   public OCAPGradoOT buscarOCAPGrado(long cGradoId)
     throws SQLException, Exception
   {
     OCAPGradoOT dato = null;
     try
     {
       OCAPGradoOAD gradoOAD = OCAPGradoOAD.getInstance();
       dato = gradoOAD.buscarOCAPGrado(cGradoId);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.toString());
       throw e;
     }
 
     return dato;
   }
 
   public int listadoOCAPGradoCuenta(long cGradoId, String dNombre, String dDescripcion, String fCreacion, String fModificacion, String nAniosejercicio)
     throws SQLException, Exception
   {
     int total = 0;
     try {
       OCAPGradoOAD gradoOAD = OCAPGradoOAD.getInstance();
       total = gradoOAD.listadoOCAPGradoCuenta(cGradoId, dNombre, dDescripcion, fCreacion, fModificacion, nAniosejercicio);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPGrado(long cGradoId, String dNombre, String dDescripcion, String fCreacion, String fModificacion, String nAniosejercicio, int primerRegistro, int registrosPorPagina)
     throws SQLException, Exception
   {
     ArrayList array = null;
     try {
       OCAPGradoOAD gradoOAD = OCAPGradoOAD.getInstance();
       array = gradoOAD.consultaOCAPGrado(cGradoId, dNombre, dDescripcion, fCreacion, fModificacion, nAniosejercicio, primerRegistro, registrosPorPagina);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPGrado()
     throws SQLException, Exception
   {
     ArrayList array = new ArrayList();
     try {
       OCAPGradoOAD gradoOAD = OCAPGradoOAD.getInstance();
       array = gradoOAD.listadoOCAPGrado();
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listarGradosConvocatoriasAbiertas(String cDni, JCYLUsuario jcylUsuario)
     throws SQLException, Exception
   {
     ArrayList array = new ArrayList();
     try {
       OCAPGradoOAD gradoOAD = OCAPGradoOAD.getInstance();
       array = gradoOAD.listadoOCAPGradoConvocatoriasAbiertas(cDni, jcylUsuario);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listarGradosConvocatoriasModif(String cDni, long cExp)
     throws SQLException, Exception
   {
     ArrayList array = new ArrayList();
     try {
       OCAPGradoOAD gradoOAD = OCAPGradoOAD.getInstance();
       array = gradoOAD.listadoOCAPGradoConvocatoriasModif(cDni, cExp);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPGrado(int inicio, int cuantos)
     throws SQLException, Exception
   {
     ArrayList array = null;
     try {
       OCAPGradoOAD gradoOAD = OCAPGradoOAD.getInstance();
       array = gradoOAD.listadoOCAPGrado(inicio, cuantos);
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listarGrados()
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try
     {
       this.logger.info(getClass().getName() + " listarGrados: Inicio");
       OCAPGradoOAD gradoOAD = OCAPGradoOAD.getInstance();
       resultado = gradoOAD.listadoOCAPGrado();
       this.logger.info(getClass().getName() + " listarGrados: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error("[ERROR] " + e.getMessage());
       throw e;
     }
 
     return resultado;
   }
 }

