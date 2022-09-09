 package es.jcyl.fqs.ocap.ln.actas;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.actas.OCAPMiembrosComitesOAD;
 import es.jcyl.fqs.ocap.ot.actas.OCAPMiembrosComitesOT;
 import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import java.util.StringTokenizer;
 import org.apache.log4j.Logger;
 
 public class OCAPMiembrosComitesLN
 {
   public Logger logger;
   public Logger loggerBD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPMiembrosComitesLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public String eliminarEspacios(String cadena)
   {
     String cadenaSinEspacios = "";
 
     for (int x = 0; x < cadena.length(); x++) {
       if (cadena.charAt(x) != ' ') {
         cadenaSinEspacios = cadenaSinEspacios + cadena.charAt(x);
       }
     }
 
     return cadenaSinEspacios;
   }
 
   public String elmiminarEspaciosAlaIzquierda(String cadena)
   {
     String temp = "";
     for (int i = 0; i < cadena.length(); i++) {
       if (cadena.charAt(i) != ' ') {
         temp = cadena.substring(i);
         break;
       }
     }
 
     return temp;
   }
 
   public static String elmiminarEspaciosAlaDerecha(String palabra) {
     String temp = "";
     for (int i = palabra.length() - 1; i >= 0; i--) {
       if (palabra.charAt(i) != ' ') {
         temp = palabra.substring(0, i);
         break;
       }
     }
 
     return temp;
   }
 
   public int altaOCAPMiembrosComites(OCAPMiembrosComitesOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     ArrayList listadoMiembrosComites = new ArrayList();
     OCAPMiembrosComitesOT datosInsercion = new OCAPMiembrosComitesOT();
     try
     {
       String datosMiembro = datos.getDDatosMiembro();
       JCYLGestionTransacciones.open(false);
       OCAPMiembrosComitesOAD oCAPMiembrosComitesOAD = OCAPMiembrosComitesOAD.getInstance();
       StringTokenizer token = new StringTokenizer(datosMiembro, ";");
       datosInsercion = new OCAPMiembrosComitesOT();
       datosInsercion.setCUsuAlta(datos.getCUsuAlta());
       datosInsercion.setCConvocatoria(datos.getCConvocatoria());
       datosInsercion.setCProfesionalId(datos.getCProfesionalId());
       datosInsercion.setCGerenciaId(datos.getCGerenciaId());
       datosInsercion.setDRol(datos.getDRol());
       while (token.hasMoreTokens()) {
         datosInsercion.setDNombre(token.nextToken().replaceAll("[\n\r]", " ").replaceAll("  ", " ").trim());
         datosInsercion.setDApellidos(token.nextToken().replaceAll("[\n\r]", " ").replaceAll("  ", " ").trim());
         datosInsercion.setCTipo(token.nextToken().trim());
         datosInsercion.setCSexo(token.nextToken().trim());
         result = oCAPMiembrosComitesOAD.altaOCAPMiembrosComites(datosInsercion);
       }
       JCYLGestionTransacciones.commit(true);
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
 
     return result;
   }
 
   public ArrayList buscarMiembros(OCAPMiembrosComitesOT datos)
     throws SQLException, Exception
   {
     ArrayList listado = null;
     try {
       this.logger.debug("Inicio");
       OCAPMiembrosComitesOAD miembrosOAD = OCAPMiembrosComitesOAD.getInstance();
       listado = miembrosOAD.buscarMiembros(datos);
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
 
   public int bajaOCAPMiembrosComites(int cMiembroId)
     throws SQLException, Exception
   {
     int result = 0;
     try
     {
       OCAPMiembrosComitesOAD miembrosComitesOAD = OCAPMiembrosComitesOAD.getInstance();
       result = miembrosComitesOAD.bajaOCAPMiembrosComites(cMiembroId);
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
 
   public int contarMiembros(OCAPMiembrosComitesOT miembrosComitesOT, JCYLUsuario jcylUsuario)
     throws Exception
   {
     int contador = 0;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " contarMiembros: Inicio");
 
       OCAPMiembrosComitesOAD miembrosComitesOAD = OCAPMiembrosComitesOAD.getInstance();
 
       contador = miembrosComitesOAD.contarMiembros(miembrosComitesOT, jcylUsuario);
 
       OCAPConfigApp.logger.info(getClass().getName() + " contarMiembros: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return contador;
   }
 
   public ArrayList buscar(int inicio, int cuantos, OCAPMiembrosComitesOT miembrosComitesOT)
     throws Exception
   {
     ArrayList listadoMiembros = null;
     String grado = "";
     String gradoAnt = "";
     String categProfesional = "";
     String categProfAnt = "";
     String especialidad = "";
     String especAnt = "";
     OCAPMiembrosComitesOT solicitudOT = null;
     ArrayList listadoSolicitudes = null;
     ArrayList listadoGrados = null;
     ArrayList listadoCategEspec = null;
     ArrayList listaRetorno = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " buscar: Inicio");
 
       OCAPMiembrosComitesOAD miembrosComitesOAD = OCAPMiembrosComitesOAD.getInstance();
 
       listadoMiembros = miembrosComitesOAD.buscar(inicio, cuantos, miembrosComitesOT, this.jcylUsuario);
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscar: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return listadoMiembros;
   }
 
   public OCAPMiembrosComitesOT buscarDatosMiembro(long cMiembroId)
     throws SQLException, Exception
   {
     OCAPMiembrosComitesOT resultado = null;
     try {
       this.logger.debug("Inicio");
       OCAPMiembrosComitesOAD miembrosOAD = OCAPMiembrosComitesOAD.getInstance();
       resultado = miembrosOAD.buscarDatosMiembro(cMiembroId);
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
 }

