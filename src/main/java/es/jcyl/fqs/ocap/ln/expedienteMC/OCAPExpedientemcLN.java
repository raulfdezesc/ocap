 package es.jcyl.fqs.ocap.ln.expedienteMC;
 
 import es.jcyl.cf.seguridad.util.Usuario;
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ln.expedienteCarrera.OCAPExpedienteCarreraLN;
 import es.jcyl.fqs.ocap.oad.expedienteMC.OCAPExpedientemcOAD;
 import es.jcyl.fqs.ocap.oad.meritosCurriculares.OCAPMeritoscurricularesOAD;
 import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;
 import es.jcyl.fqs.ocap.ot.expedienteMC.OCAPExpedientemcOT;
 import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.SQLException;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Date;
 import org.apache.log4j.Logger;
 
 public class OCAPExpedientemcLN
 {
   Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPExpedientemcLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int insertar(OCAPExpedientemcOT expedientemcOT, JCYLUsuario jcylUsuario)
     throws SQLException, Exception
   {
     int idExpediente = 0;
     float creditosMerito = 0.0F;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " insertar: Inicio");
       JCYLGestionTransacciones.open(false);
 
       OCAPExpedientemcOAD expmcOAD = OCAPExpedientemcOAD.getInstance();
       OCAPMeritoscurricularesOAD meritosCurricularesOAD = OCAPMeritoscurricularesOAD.getInstance();
 
       if ("DocenciaPost".equals(expedientemcOT.getCTipoMerito())) {
         String cadena = expedientemcOT.getAOrganizador();
         while (cadena.indexOf("%") != -1) {
           if (cadena.indexOf("%") > 1) {
             expedientemcOT.setCActividadId(Integer.valueOf(cadena.substring(0, cadena.indexOf("#"))));
             expedientemcOT.setAOrganizador(cadena.substring(cadena.indexOf("#") + 1, cadena.indexOf("%")));
             expedientemcOT.setACreadoPor(jcylUsuario.getUsuario().getC_usr_id());
 
             expedientemcOT = meritosCurricularesOAD.buscarMeritoCurricular(expedientemcOT);
             creditosMerito = calcularCreditos(expedientemcOT);
             expedientemcOT.setNCreditos(Float.valueOf(creditosMerito));
             idExpediente = expmcOAD.altaOCAPExpeditentemc(expedientemcOT);
             cadena = cadena.substring(cadena.indexOf("%") + 1, cadena.length());
           } else {
             cadena = cadena.substring(cadena.indexOf("%") + 1, cadena.length());
           }
         }
       }
       else
       {
         expedientemcOT = meritosCurricularesOAD.buscarMeritoCurricular(expedientemcOT);
         creditosMerito = calcularCreditos(expedientemcOT);
         expedientemcOT.setNCreditos(Float.valueOf(creditosMerito));
         expedientemcOT.setACreadoPor(jcylUsuario.getUsuario().getC_usr_id());
         idExpediente = expmcOAD.altaOCAPExpeditentemc(expedientemcOT);
       }
 
       JCYLGestionTransacciones.commit(true);
       OCAPConfigApp.logger.info(getClass().getName() + " insertar: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       JCYLGestionTransacciones.rollback();
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       JCYLGestionTransacciones.rollback();
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
 
     return idExpediente;
   }
 
   public OCAPExpedientemcOT buscarPorId(Long cExpmcId, JCYLUsuario jcylUsuario)
     throws SQLException, Exception
   {
     OCAPExpedientemcOT expmcOT = null;
     Connection con = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarPorId: Inicio");
 
       con = JCYLGestionTransacciones.getConnection();
 
       OCAPExpedientemcOAD expmcOAD = OCAPExpedientemcOAD.getInstance();
 
       expmcOT = expmcOAD.buscarOCAPExpeditentemc(cExpmcId, con);
 
       if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {
         expmcOT.setBPdteAclararCeis(expmcOT.getBPdteAclararCc());
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarPorId: Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
 
       throw e;
     }
     finally
     {
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return expmcOT;
   }
   
   
   public OCAPExpedientemcOT buscarOCAPExpeditentemcModif(Long cExpmcId, JCYLUsuario jcylUsuario)
		     throws SQLException, Exception
		   {
		     OCAPExpedientemcOT expmcOT = null;
		     Connection con = null;
		     try
		     {
		       OCAPConfigApp.logger.info(getClass().getName() + " buscarOCAPExpeditentemcModif: Inicio");
		 
		       con = JCYLGestionTransacciones.getConnection();
		 
		       OCAPExpedientemcOAD expmcOAD = OCAPExpedientemcOAD.getInstance();
		 
		       expmcOT = expmcOAD.buscarOCAPExpeditentemcModif(cExpmcId, con);
		 
		       OCAPConfigApp.logger.info(getClass().getName() + " buscarPorId: Fin");
		     } catch (SQLException exSQL) {
		       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
		       throw exSQL;
		     }
		     catch (Exception e)
		     {
		       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
		 
		       throw e;
		     }
		     finally
		     {
		       JCYLGestionTransacciones.close(con.getAutoCommit());
		     }
		 
		     return expmcOT;
		   }
 
   public long buscarPdtesAclararPorExpId(long cExpId, JCYLUsuario jcylUsuario, Connection con)
     throws SQLException, Exception
   {
     long contador = 0L;
     boolean crearConexion = false;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarPdtesAclararPorExpId: Inicio");
       if (con == null)
       {
         con = JCYLGestionTransacciones.getConnection();
         crearConexion = true;
       }
 
       OCAPExpedientemcOAD expmcOAD = OCAPExpedientemcOAD.getInstance();
 
       contador = expmcOAD.buscarOCAPExpeditentemcPdtesAclararPorExpId(cExpId, jcylUsuario.getUser().getRol(), con);
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarPdtesAclararPorExpId: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } finally {
       if (crearConexion) {
         JCYLGestionTransacciones.close(con.getAutoCommit());
       }
     }
 
     return contador;
   }
 
   public void desinvalidarPorExpId(Long cExpId, JCYLUsuario jcylUsuario)
     throws SQLException, Exception
   {
     int numModificados = 0;
     try
     {
       OCAPConfigApp.logger.info("desinvalidarPorExpId: Inicio");
 
       OCAPExpedientemcOAD expmcOAD = OCAPExpedientemcOAD.getInstance();
 
       numModificados = expmcOAD.desinvalidarOCAPporExpId(cExpId,jcylUsuario.getUsuario().getC_usr_id());
 
       OCAPConfigApp.logger.info("desinvalidarPorExpId: Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
 
       throw e;
     }
     finally
     {
     }
   }
 
   /**
    * Busca méritos de un expediente
 * @param expmcOT Expediente
 * @param jcylUsuario  Usuario que realiza la acción
 * @param opcional  Flag indicando si se filtra por Opcionales (O), No opcionales (N) o TODOS (T)  
 * @return Lista de méritos del expediente
 * @throws SQLException
 * @throws Exception
 */
public ArrayList buscarPorMeritoId(OCAPExpedientemcOT expmcOT, JCYLUsuario jcylUsuario, String opcional)
     throws SQLException, Exception
   {
     ArrayList listado = null;
     Connection con = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarPorMeritoId: Inicio");
 
       con = JCYLGestionTransacciones.getConnection();
 
       OCAPExpedientemcOAD expmcOAD = OCAPExpedientemcOAD.getInstance();
 
       listado = expmcOAD.buscarOCAPExpeditentemcPorMeritoId(expmcOT, con,opcional);
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarPorMeritoId: Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
 
       throw e;
     }
     finally
     {
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 
   public long buscarExpMCRepetidoPorFAnnio(OCAPExpedientemcOT expmcOT, JCYLUsuario jcylUsuario)
     throws SQLException, Exception
   {
     long cExpmcId = 0L;
     try
     {
       OCAPConfigApp.logger.info("buscarPorId: Inicio");
 
       OCAPExpedientemcOAD expmcOAD = OCAPExpedientemcOAD.getInstance();
 
       cExpmcId = expmcOAD.buscarOCAPExpeditentemcRepetidoPorFAnnio(expmcOT);
 
       OCAPConfigApp.logger.info("buscarPorId: Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
 
       throw e;
     }
     finally
     {
     }
 
     return cExpmcId;
   }
 
   public int modificar(OCAPExpedientemcOT expedientemcOT, JCYLUsuario jcylUsuario)
     throws SQLException, Exception
   {
     int fila = 0;
     float creditosMerito = 0.0F;
     try
     {
       OCAPConfigApp.logger.info("modificar: Inicio");
       JCYLGestionTransacciones.open(false);
 
       OCAPExpedientemcOAD expmcOAD = OCAPExpedientemcOAD.getInstance();
       OCAPMeritoscurricularesOAD meritosCurricularesOAD = OCAPMeritoscurricularesOAD.getInstance();
 
       if ("DocenciaPost".equals(expedientemcOT.getCTipoMerito()))
       {
         String cExpcm = expedientemcOT.getALugarExpedicion();
         while ((cExpcm != null) && (!cExpcm.equals(""))) {
           if (cExpcm.indexOf("#") != -1) {
             fila = expmcOAD.bajaOCAPExpeditentemc(Long.valueOf(cExpcm.substring(0, cExpcm.indexOf("#"))),jcylUsuario.getUser().getC_usr_id());
             cExpcm = cExpcm.substring(cExpcm.indexOf("#") + 1, cExpcm.length());
           } else {
             fila = expmcOAD.bajaOCAPExpeditentemc(Long.valueOf(cExpcm), jcylUsuario.getUser().getC_usr_id());
             cExpcm = "";
           }
         }
 
         expedientemcOT.setALugarExpedicion("");
         String cadena = expedientemcOT.getAOrganizador();
         String cadena_aux = cadena;
         String cadena_parcial = "";
         String cadena_parcial_aux = "";
         String cadena_parcial_aux_bis = "";
         String codActividad = "";
         String codExpmc = "";
         while (cadena.indexOf("%") != -1) {
           String fechas = "";
           if (cadena.indexOf("%") > 1) {
             cadena_parcial = cadena.substring(0, cadena.indexOf("%"));
             expedientemcOT.setCActividadId(Integer.valueOf(cadena_parcial.substring(0, cadena_parcial.indexOf("#"))));
             expedientemcOT.setAOrganizador(cadena_parcial.substring(cadena_parcial.indexOf("#") + 1, cadena_parcial.length()));
             expedientemcOT.setACreadoPor(jcylUsuario.getUsuario().getC_usr_id());
             expedientemcOT = meritosCurricularesOAD.buscarMeritoCurricular(expedientemcOT);
             creditosMerito = calcularCreditos(expedientemcOT);
             expedientemcOT.setNCreditos(Float.valueOf(creditosMerito));
 
             fila = expmcOAD.altaOCAPExpeditentemc(expedientemcOT);
             cadena = cadena.substring(cadena.indexOf("%") + 1, cadena.length());
           } else {
             cadena = cadena.substring(cadena.indexOf("%") + 1, cadena.length());
           }
         }
       }
       else {
         if ((expedientemcOT.getNCredCeis() == null) && (expedientemcOT.getNCredCc() == null)) {
           expedientemcOT = meritosCurricularesOAD.buscarMeritoCurricular(expedientemcOT);
           creditosMerito = calcularCreditos(expedientemcOT);
           expedientemcOT.setNCreditos(Float.valueOf(creditosMerito));
         }
         fila = expmcOAD.modificacionOCAPExpeditentemc(expedientemcOT,jcylUsuario.getUsuario().getC_usr_id());
       }
 
       JCYLGestionTransacciones.commit(true);
       OCAPConfigApp.logger.info("modificar: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       JCYLGestionTransacciones.rollback();
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       JCYLGestionTransacciones.rollback();
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
 
     return fila;
   }
 
   public int eliminar(Long cExpmcId, String userMod)
     throws SQLException, Exception
   {
     int fila = 0;
     Connection con = null;
     try
     {
       OCAPConfigApp.logger.info("eliminar: Inicio");
 
       OCAPExpedientemcOAD expmcOAD = OCAPExpedientemcOAD.getInstance();
 
       fila = expmcOAD.bajaOCAPExpeditentemc(cExpmcId, userMod);
 
       OCAPConfigApp.logger.info("eliminar: Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
 
       throw e;
     }
     finally
     {
     }
 
     return fila;
   }
 
   public int invalidar(Long cExpmcId, boolean bInvalidar, String perfil, String userMod)
     throws SQLException, Exception
   {
     int fila = 0;
     try
     {
       OCAPConfigApp.logger.info("invalidar: " + cExpmcId);
 
       OCAPExpedientemcOAD expmcOAD = OCAPExpedientemcOAD.getInstance();
 
       fila = expmcOAD.invalidaOCAPExpeditentemc(cExpmcId, bInvalidar, perfil,userMod);
 
       OCAPConfigApp.logger.info(getClass().getName() + " invalidar: Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
 
       throw e;
     }
     finally
     {
     }
 
     return fila;
   }
 
   public int eliminarAclaracionesInvalidar(long cExpId, String perfil)
     throws SQLException, Exception
   {
     int fila = 0;
     try
     {
       OCAPConfigApp.logger.info("eliminarAclaracionesInvalidar: Inicio");
 
       OCAPExpedientemcOAD expmcOAD = OCAPExpedientemcOAD.getInstance();
 
       fila = expmcOAD.eliminarAclaracionesInvalidarOCAPExpeditentemc(cExpId, perfil);
 
       OCAPConfigApp.logger.info("eliminarAclaracionesInvalidar: Fin");
     } catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
 
       throw e;
     }
     finally
     {
     }
 
     return fila;
   }
 
   public int aclarar(Long cExpmcId, boolean bPedirAclarar, String perfil, OCAPExpedientecarreraOT expOT)
     throws SQLException, Exception
   {
     int fila = 0;
     Connection con = null;
     OCAPExpedienteCarreraLN expLN = null;
     try {
       OCAPConfigApp.logger.info("aclarar: Inicio");
 
       JCYLGestionTransacciones.open(false);
 
       OCAPExpedientemcOAD expmcOAD = OCAPExpedientemcOAD.getInstance();
 
       fila = expmcOAD.aclaraOCAPExpeditentemc(cExpmcId, bPedirAclarar, perfil, jcylUsuario.getUsuario().getC_usr_id());
 
       expLN = new OCAPExpedienteCarreraLN(this.jcylUsuario);
       expOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());
       expLN.modificacionOCAPExpedientecarrera(expOT, false);
 
       JCYLGestionTransacciones.commit(true);
 
       OCAPConfigApp.logger.info("aclarar: Fin");
     } catch (SQLException exSQL) {
       JCYLGestionTransacciones.rollback();
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     }
     catch (Exception e)
     {
       JCYLGestionTransacciones.rollback();
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
 
     return fila;
   }
 
   public int enviarAOpcionales(Long cExpmcId, String userMod)
     throws SQLException, Exception
   {
     int fila = 0;
     Connection con = null;
     try
     {
       OCAPConfigApp.logger.info("enviarAOpcionales: Inicio");
 
       con = JCYLGestionTransacciones.getConnection();
 
       OCAPExpedientemcOAD expmcOAD = OCAPExpedientemcOAD.getInstance();
 
       fila = expmcOAD.enviarOCAPExpeditentemcOpcionales(cExpmcId, con,  userMod);
 
       OCAPConfigApp.logger.info("enviarAOpcionales: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
 
       throw e;
     }
     finally
     {
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return fila;
   }
 
   public float calcularCreditos(OCAPExpedientemcOT expmcOT)
     throws Exception
   {
     float creditosResultado = 0.0F;
     Date dInicio = null;
     Date dFin = null;
     SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
 
     float nCreditos = 0.0F;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " calcularCreditos: Ini ");
 
       if ((expmcOT.getNCreditosTramo() != null) && (expmcOT.getNCreditosTramo().floatValue() != nCreditos))
         nCreditos = expmcOT.getNCreditosTramo().floatValue();
       else if (expmcOT.getNCreditos() != null) {
         nCreditos = expmcOT.getNCreditos().floatValue();
       }
       OCAPConfigApp.logger.info(getClass().getName() + " calcularCreditos: nCreditos=" + nCreditos + " Tipo de Merito=" + expmcOT.getCTipoMerito());
 
       if ((expmcOT.getCTipoMerito().indexOf("Responsable") != -1) || ("MiembroConsejo".equals(expmcOT.getCTipoMerito())))
       {
         if ((expmcOT.getNAnnios() != null) && (expmcOT.getNAnnios().intValue() != 0)) {
           creditosResultado = nCreditos * expmcOT.getNAnnios().intValue();
         } else if ((expmcOT.getFInicio() != null) && (!"".equals(expmcOT.getFInicio()))) {
           dInicio = df.parse(expmcOT.getFInicio());
           if ((expmcOT.getFFinalizacion() != null) && (!"".equals(expmcOT.getFFinalizacion())))
             dFin = df.parse(expmcOT.getFFinalizacion());
           else dFin = new Date();
           int nAnnios = DateUtil.getNumAnnios(dInicio, dFin);
           creditosResultado = nCreditos * nAnnios;
         } else if ((expmcOT.getFAnnio() != null) && (expmcOT.getFAnnio().intValue() != 0)) {
           creditosResultado = nCreditos;
         }
       }
       else if ("DocenciaPost".equals(expmcOT.getCTipoMerito()))
       {
         if ((expmcOT.getAOrganizador() != null) && (expmcOT.getAOrganizador().length() > 0))
         {
           ArrayList cadenasCertificados = null;
           cadenasCertificados = Cadenas.obtenerArrayListTokens(expmcOT.getAOrganizador(), "#");
 
           creditosResultado = nCreditos * cadenasCertificados.size();
         }
         else {
           creditosResultado = nCreditos;
         }
       }
       else if ("DocenciaPre".equals(expmcOT.getCTipoMerito()))
       {
         creditosResultado = nCreditos;
       }
       else if (("Estancia".equals(expmcOT.getCTipoMerito())) || ("EstanciaInv".equals(expmcOT.getCTipoMerito())))
       {
         if ((expmcOT.getFInicio() != null) && (!"".equals(expmcOT.getFInicio())) && (expmcOT.getFFinalizacion() != null) && (!"".equals(expmcOT.getFFinalizacion())))
         {
           dInicio = df.parse(expmcOT.getFInicio());
           dFin = df.parse(expmcOT.getFFinalizacion());
           int nMeses = DateUtil.getNumMeses(dInicio, dFin);
           creditosResultado = nCreditos * nMeses;
         }
 
       }
       else if ("CursoDoc".equals(expmcOT.getCTipoMerito()))
       {
         if ((expmcOT.getNHoras() != null) && (expmcOT.getNHoras().intValue() != 0)) {
           int grupo5horas = expmcOT.getNHoras().intValue() / 5;
           creditosResultado = nCreditos * grupo5horas;
         }
         else if ((expmcOT.getNDias() != null) && (expmcOT.getNDias().intValue() != 0)) {
           int grupo5horas = 1 * expmcOT.getNDias().intValue();
           creditosResultado = nCreditos * grupo5horas;
         }
         else if ((expmcOT.getFInicio() != null) && (!"".equals(expmcOT.getFInicio())) && (expmcOT.getFFinalizacion() != null) && (!"".equals(expmcOT.getFFinalizacion())))
         {
           dInicio = df.parse(expmcOT.getFInicio());
           dFin = df.parse(expmcOT.getFFinalizacion());
           int nDias = DateUtil.getNumDiasHabiles(dInicio, dFin);
           int grupo5horas = 1 * nDias;
           creditosResultado = nCreditos * grupo5horas;
         }
 
       }
       else if ("Taller".equals(expmcOT.getCTipoMerito()))
       {
         if ((expmcOT.getCModalidadId() != null) && ((1 == expmcOT.getCModalidadId().intValue()) || (2 == expmcOT.getCModalidadId().intValue()) || (8 == expmcOT.getCModalidadId().intValue())))
         {
           if ((expmcOT.getNHoras() != null) && (expmcOT.getNHoras().intValue() != 0)) {
             int grupo10horas = expmcOT.getNHoras().intValue() / 10;
             creditosResultado = nCreditos * grupo10horas;
           }
           else if ((expmcOT.getNDias() != null) && (expmcOT.getNDias().intValue() != 0)) {
             int grupo10horas = 5 * expmcOT.getNDias().intValue() / 10;
             creditosResultado = nCreditos * grupo10horas;
           }
           else if ((expmcOT.getFInicio() != null) && (!"".equals(expmcOT.getFInicio())) && (expmcOT.getFFinalizacion() != null) && (!"".equals(expmcOT.getFFinalizacion())))
           {
             dInicio = df.parse(expmcOT.getFInicio());
             dFin = df.parse(expmcOT.getFFinalizacion());
             int nDias = DateUtil.getNumDiasHabiles(dInicio, dFin);
             int grupo10horas = 5 * nDias / 10;
             creditosResultado = nCreditos * grupo10horas;
           }
 
         }
         else if (Constantes.SI.equals(expmcOT.getBAcreditado())) {
           creditosResultado = expmcOT.getNCreditosTramo().floatValue();
         }
 
       }
       else if ("SeminariosDoc".equals(expmcOT.getCTipoMerito()))
       {
         if ((expmcOT.getCModalidadId() != null) && (14 == expmcOT.getCModalidadId().intValue()) && (expmcOT.getNHoras() != null) && (expmcOT.getNHoras().intValue() != 0))
           creditosResultado = nCreditos * expmcOT.getNHoras().intValue();
         else creditosResultado = nCreditos;
 
       }
       else if ("Bibliograficas".equals(expmcOT.getCTipoMerito()))
       {
         ArrayList cadenasCertificados = null;
         int nHoras = 0;
         cadenasCertificados = Cadenas.obtenerArrayListTokens(expmcOT.getAOrganizador(), "#");
 
         for (int j = 0; j < cadenasCertificados.size(); j++) {
           String cadena = (String)cadenasCertificados.get(j);
           cadena = cadena.substring(cadena.indexOf("&") + 1);
           nHoras += new Integer(cadena).intValue();
         }
 
         int grupo5horas = nHoras / 5;
         creditosResultado = nCreditos * grupo5horas;
       }
       else if ("Centinela".equals(expmcOT.getCTipoMerito()))
       {
         creditosResultado = 0.0F;
         if ((expmcOT.getFInicio() != null) && (!"".equals(expmcOT.getFInicio())) && (expmcOT.getFFinalizacion() != null) && (!"".equals(expmcOT.getFFinalizacion()))) {
           dInicio = df.parse(expmcOT.getFInicio());
           dFin = df.parse(expmcOT.getFFinalizacion());
           int nAnnios = DateUtil.getNumAnnios(dInicio, dFin);
           if (nAnnios >= 1)
             creditosResultado = nCreditos;
         }
       }
       else if ("MiembroComision".equals(expmcOT.getCTipoMerito()))
       {
         creditosResultado = 0.0F;
         if ((expmcOT.getFInicio() != null) && (!"".equals(expmcOT.getFInicio())) && (expmcOT.getFFinalizacion() != null) && (!"".equals(expmcOT.getFFinalizacion()))) {
           dInicio = df.parse(expmcOT.getFInicio());
           dFin = df.parse(expmcOT.getFFinalizacion());
           int nMeses = DateUtil.getNumMeses(dInicio, dFin);
           if (nMeses >= 1)
             creditosResultado = nCreditos * nMeses;
         }
       }
       else if ("Congreso".equals(expmcOT.getCTipoMerito()))
       {
         if (Constantes.SI.equals(expmcOT.getBAcreditado())) creditosResultado = expmcOT.getNCreditosTramo().floatValue(); else {
           creditosResultado = nCreditos;
         }
       }
       else if (Constantes.NO.equals(expmcOT.getBParticipacion()))
       {
         creditosResultado = 0.0F;
       }
       else if ("MiembroGrupo".equals(expmcOT.getCTipoMerito()))
       {
         creditosResultado = 0.0F;
         if ((expmcOT.getFInicio() != null) && (!"".equals(expmcOT.getFInicio())) && (expmcOT.getFFinalizacion() != null) && (!"".equals(expmcOT.getFFinalizacion()))) {
           dInicio = df.parse(expmcOT.getFInicio());
           dFin = df.parse(expmcOT.getFFinalizacion());
           int nMeses = DateUtil.getNumMeses(dInicio, dFin);
           if (nMeses >= 3)
             creditosResultado = nCreditos;
         }
       }
       else
       {
         creditosResultado = nCreditos;
       }
 
       creditosResultado = (float)(Math.rint(creditosResultado * 100.0F) / 100.0D);
 
       OCAPConfigApp.logger.info(getClass().getName() + " calcularCreditos: Fin ");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return creditosResultado;
   }
 
   public int modificarCreditosMerito(OCAPExpedientemcOT datos, JCYLUsuario jcylUsuario, OCAPExpedientecarreraOT expOT, boolean modificarHoras)
     throws SQLException, Exception
   {
     int fila = 0;
     OCAPExpedienteCarreraLN expCarreraLN = null;
     try {
       OCAPConfigApp.logger.info("modificarCreditosMerito: Inicio");
       JCYLGestionTransacciones.open(false);
       OCAPExpedientemcOAD expmcOAD = OCAPExpedientemcOAD.getInstance();
 
       fila = expmcOAD.modificarCreditosMerito(datos, jcylUsuario, modificarHoras);
 
       expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
       expOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());
       expCarreraLN.modificacionOCAPExpedientecarrera(expOT, false);
 
       JCYLGestionTransacciones.commit(true);
       OCAPConfigApp.logger.info("modificarCreditosMerito: Fin");
     } catch (SQLException exSQL) {
       JCYLGestionTransacciones.rollback();
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
 
     return fila;
   }
 }

