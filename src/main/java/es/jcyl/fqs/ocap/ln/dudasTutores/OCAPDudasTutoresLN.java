 package es.jcyl.fqs.ocap.ln.dudasTutores;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.dudasTutores.OCAPDudasTutoresOAD;
 import es.jcyl.fqs.ocap.ot.dudasTutores.OCAPDudasTutoresOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.framework.JCYLConfiguracion;
 import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPDudasTutoresLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPDudasTutoresLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public long altaOCAPTutores(OCAPDudasTutoresOT dudasTutoresOT)
     throws SQLException, Exception
   {
     long resultado = 0L;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       JCYLGestionTransacciones.open(false);
       resultado = dudasTutoresOAD.altaOCAPTutores(dudasTutoresOT);
 
       JCYLGestionTransacciones.commit();
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       JCYLGestionTransacciones.rollback();
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       this.logger.error(e);
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
 
     return resultado;
   }
 
   public OCAPDudasTutoresOT buscarDatosTutor(long cTutorId)
     throws SQLException, Exception
   {
     OCAPDudasTutoresOT resultado = null;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       resultado = dudasTutoresOAD.buscarDatosTutor(cTutorId);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return resultado;
   }
 
   public OCAPDudasTutoresOT buscarDatosTutor(String cDni)
     throws SQLException, Exception
   {
     OCAPDudasTutoresOT resultado = null;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       resultado = dudasTutoresOAD.buscarDatosTutor(cDni);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return resultado;
   }
 
   public ArrayList buscarTutores(OCAPDudasTutoresOT datos, int primerRegistro, int registrosPorPagina, String ordenacion, String tipoOrdenacion)
     throws SQLException, Exception
   {
     ArrayList listado = null;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       listado = dudasTutoresOAD.buscarTutores(datos, primerRegistro, registrosPorPagina, ordenacion, tipoOrdenacion);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return listado;
   }
 
   public int buscarTutoresCuenta(OCAPDudasTutoresOT datos)
     throws SQLException, Exception
   {
     int numTutores = 0;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       numTutores = dudasTutoresOAD.buscarTutoresCuenta(datos);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return numTutores;
   }
 
   public int bajaTutor(long cTutorId)
     throws SQLException, Exception
   {
     int resultado = 0;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       resultado = dudasTutoresOAD.bajaTutor(cTutorId);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return resultado;
   }
 
   public int modificarTutor(OCAPDudasTutoresOT dudasTutoresOT)
     throws SQLException, Exception
   {
     int resultado = 0;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       resultado = dudasTutoresOAD.modificarTutor(dudasTutoresOT);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return resultado;
   }
 
   public long buscarMinNControlTutoresActivos(String cTipoDuda)
     throws SQLException, Exception
   {
     long resultado = 0L;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       resultado = dudasTutoresOAD.buscarMinNControlTutoresActivos(cTipoDuda);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return resultado;
   }
 
   public ArrayList buscarTemas(String cTipoDuda)
     throws SQLException, Exception
   {
     ArrayList resultado = null;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       resultado = dudasTutoresOAD.buscarTemas(cTipoDuda);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return resultado;
   }
 
   public long altaDuda(OCAPDudasTutoresOT dudasTutoresOT, boolean existeTransaccion)
     throws SQLException, Exception
   {
     long resultado = 0L;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       JCYLGestionTransacciones.open(false);
 
       long cTutorId = buscarTutorAAsignar(dudasTutoresOT.getCTipoDuda(), 0L);
       if (cTutorId == 0L) {
         throw new Exception("sinTutor");
       }
       dudasTutoresOT.setCTutorId(cTutorId);
 
       resultado = dudasTutoresOAD.altaDuda(dudasTutoresOT);
 
       dudasTutoresOAD.aumentarContadorRecibidas(dudasTutoresOT);
       JCYLGestionTransacciones.commit(existeTransaccion ^ true);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       JCYLGestionTransacciones.rollback();
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       this.logger.error(e);
       throw e;
     } finally {
       JCYLGestionTransacciones.close(existeTransaccion ^ true);
     }
 
     return resultado;
   }
 
   public long buscarTutorAAsignar(String cTipoDuda, long cTutorEvitarId)
     throws SQLException, Exception
   {
     long cTutorId = 0L;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
 
       cTutorId = dudasTutoresOAD.buscarTutorAAsignar(cTipoDuda, 1, cTutorEvitarId);
 
       if (cTutorId == 0L)
         cTutorId = dudasTutoresOAD.buscarTutorAAsignar(cTipoDuda, 2, cTutorEvitarId);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return cTutorId;
   }
 
   public String buscarCorreoExp(long cExpId)
     throws SQLException, Exception
   {
     String resultado = null;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       resultado = dudasTutoresOAD.buscarCorreoExp(cExpId);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return resultado;
   }
 
   public ArrayList buscarDudasTutores(OCAPDudasTutoresOT datos, int primerRegistro, int registrosPorPagina, String ordenacion, String tipoOrdenacion)
     throws SQLException, Exception
   {
     ArrayList listado = null;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       listado = dudasTutoresOAD.buscarDudasTutores(datos, primerRegistro, registrosPorPagina, ordenacion, tipoOrdenacion);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return listado;
   }
 
   public int buscarDudasTutoresCuenta(OCAPDudasTutoresOT datos)
     throws SQLException, Exception
   {
     int numDudas = 0;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       numDudas = dudasTutoresOAD.buscarDudasTutoresCuenta(datos);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return numDudas;
   }
 
   public ArrayList buscarDudasUsuario(OCAPDudasTutoresOT datos, int primerRegistro, int registrosPorPagina, String ordenacion, String tipoOrdenacion)
     throws SQLException, Exception
   {
     ArrayList listado = null;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       listado = dudasTutoresOAD.buscarDudasUsuario(datos, primerRegistro, registrosPorPagina, ordenacion, tipoOrdenacion);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return listado;
   }
 
   public int buscarDudasUsuarioCuenta(OCAPDudasTutoresOT datos)
     throws SQLException, Exception
   {
     int numDudas = 0;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       numDudas = dudasTutoresOAD.buscarDudasUsuarioCuenta(datos);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return numDudas;
   }
 
   public ArrayList buscarDudas(OCAPDudasTutoresOT datos, int primerRegistro, int registrosPorPagina, String ordenacion, String tipoOrdenacion)
     throws SQLException, Exception
   {
     ArrayList listado = null;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       listado = dudasTutoresOAD.buscarDudas(datos, primerRegistro, registrosPorPagina, ordenacion, tipoOrdenacion);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return listado;
   }
 
   public int buscarDudasCuenta(OCAPDudasTutoresOT datos)
     throws SQLException, Exception
   {
     int numDudas = 0;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       numDudas = dudasTutoresOAD.buscarDudasCuenta(datos);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return numDudas;
   }
 
   public OCAPDudasTutoresOT buscarDatosDuda(long cDudaId)
     throws SQLException, Exception
   {
     OCAPDudasTutoresOT resultado = null;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       resultado = dudasTutoresOAD.buscarDatosDuda(cDudaId);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return resultado;
   }
 
   public int contestarDuda(OCAPDudasTutoresOT dudasTutoresOT, long idTutorAsignado, boolean existeTransaccion)
     throws SQLException, Exception
   {
     int resultado = 0;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       JCYLGestionTransacciones.open(false);
 
       resultado = dudasTutoresOAD.contestarDuda(dudasTutoresOT);
 
       if (resultado == 1)
       {
         if (dudasTutoresOT.getCTutorId() != idTutorAsignado)
         {
           dudasTutoresOAD.aumentarContadorRecibidas(dudasTutoresOT);
           dudasTutoresOAD.aumentarContadorContestadas(dudasTutoresOT);
 
           OCAPDudasTutoresOT antiguoTutorOT = new OCAPDudasTutoresOT();
           antiguoTutorOT.setCTutorId(idTutorAsignado);
           antiguoTutorOT.setACreadoPor(dudasTutoresOT.getACreadoPor());
           dudasTutoresOAD.disminuirContadorRecibidas(antiguoTutorOT);
         } else {
           dudasTutoresOAD.aumentarContadorContestadas(dudasTutoresOT);
         }
       }
       JCYLGestionTransacciones.commit(existeTransaccion ^ true);
 
       String servidor_de_correo = JCYLConfiguracion.getValor("SERVIDOR_CORREO");
       String puerto = JCYLConfiguracion.getValor("PUERTO_CORREO");
       String remitente = JCYLConfiguracion.getValor("EMAIL_REMITENTE_DUDAS");
       String destinatario = dudasTutoresOT.getDCorreoElectronico();
       String asunto = "Carrera Profesional: evalu@netFQS (tutor-guía)";
       String cuerpo = "Ya est&aacute; disponible en la aplicaci&oacute;n OCAP la respuesta a su duda.";
 
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       JCYLGestionTransacciones.rollback();
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       this.logger.error(e);
       throw e;
     } finally {
       JCYLGestionTransacciones.close(existeTransaccion ^ true);
     }
 
     return resultado;
   }
 
   public int cambiarTipoDuda(OCAPDudasTutoresOT dudasTutoresOT, long idTutorAsignado)
     throws SQLException, Exception
   {
     int resultado = 0;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       JCYLGestionTransacciones.open(false);
 
       resultado = contestarDuda(dudasTutoresOT, idTutorAsignado, true);
 
       if (resultado == 1) {
         OCAPDudasTutoresOT dudaOT = buscarDatosDuda(dudasTutoresOT.getCDudaId());
         if ("1".equals(dudasTutoresOT.getCTipoDuda()))
           dudasTutoresOT.setCTipoDuda("2");
         else {
           dudasTutoresOT.setCTipoDuda("1");
         }
         dudasTutoresOT.setDDuda(dudaOT.getDDuda());
         dudasTutoresOT.setBProcedeCambio(Constantes.SI);
         altaDuda(dudasTutoresOT, true);
       }
       JCYLGestionTransacciones.commit();
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       JCYLGestionTransacciones.rollback();
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       this.logger.error(e);
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
 
     return resultado;
   }
 
   public int marcarDudaLeida(OCAPDudasTutoresOT dudasTutoresOT)
     throws SQLException, Exception
   {
     int resultado = 0;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       resultado = dudasTutoresOAD.marcarDudaLeida(dudasTutoresOT);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return resultado;
   }
 
   public int reasignarDuda(OCAPDudasTutoresOT dudasTutoresOT)
     throws SQLException, Exception
   {
     int resultado = 0;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       long cTutorAnteriorId = dudasTutoresOT.getCTutorId();
       long cTutorId = buscarTutorAAsignar(dudasTutoresOT.getCTipoDuda(), cTutorAnteriorId);
       if (cTutorId == 0L)
         throw new Exception("sinTutor");
       JCYLGestionTransacciones.open(false);
 
       dudasTutoresOT.setCTutorId(cTutorId);
       resultado = dudasTutoresOAD.modificarDuda(dudasTutoresOT);
 
       if (resultado == 1)
       {
         dudasTutoresOAD.aumentarContadorRecibidas(dudasTutoresOT);
 
         OCAPDudasTutoresOT antiguoTutorOT = new OCAPDudasTutoresOT();
         antiguoTutorOT.setCTutorId(cTutorAnteriorId);
         antiguoTutorOT.setACreadoPor(dudasTutoresOT.getACreadoPor());
         dudasTutoresOAD.disminuirContadorRecibidas(antiguoTutorOT);
       }
       JCYLGestionTransacciones.commit();
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       JCYLGestionTransacciones.rollback();
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       this.logger.error(e);
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
 
     return resultado;
   }
 
   public ArrayList buscarDudasCSV(OCAPDudasTutoresOT datos)
     throws SQLException, Exception
   {
     ArrayList listado = null;
     try {
       this.logger.debug("Inicio");
       OCAPDudasTutoresOAD dudasTutoresOAD = OCAPDudasTutoresOAD.getInstance();
       listado = dudasTutoresOAD.buscarDudasCSV(datos);
       this.logger.debug("Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return listado;
   }
 
   public String crearCSVDudas(ArrayList listadoSolicitudes)
     throws Exception
   {
     StringBuffer cadenaDatos = new StringBuffer();
     OCAPDudasTutoresOT datos = null;
     try {
       this.logger.debug("Inicio");
       if (listadoSolicitudes.size() > 0)
       {
         cadenaDatos.append("Evaluado;").append("Fecha recepción duda;").append("Fecha respuesta;").append("Tiempo respuesta;").append("Tutor;").append("Tema;").append("Gerencia;").append("Categoría;").append("Especialidad;").append(Constantes.SALTO_LINEA);
       }
 
       for (int i = 0; i < listadoSolicitudes.size(); i++) {
         datos = (OCAPDudasTutoresOT)listadoSolicitudes.get(i);
         cadenaDatos.append(datos.getCodigoEPR() + ";");
         cadenaDatos.append(datos.getFRecepcion() + ";");
         cadenaDatos.append(datos.getFEnvioContestacion() + ";");
         if (datos.getNTiempoRespuesta() == 0L)
           cadenaDatos.append(";");
         else
           cadenaDatos.append("" + datos.getTiempoRespuesta() + ";");
         cadenaDatos.append(datos.getDNombreTutor() + " " + datos.getDApellidosTutor() + ";");
         cadenaDatos.append(datos.getDTema() + ";");
         cadenaDatos.append(datos.getDGerencia() + ";");
         cadenaDatos.append(datos.getDProfesional() + ";");
         cadenaDatos.append(datos.getDEspecialidad() + ";");
         cadenaDatos.append(Constantes.SALTO_LINEA);
       }
       this.logger.debug("Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " crearCSVDinamicoListado: ERROR" + e.getMessage());
       throw new Exception("CSV NO generado");
     }
     return cadenaDatos.toString();
   }
 }

