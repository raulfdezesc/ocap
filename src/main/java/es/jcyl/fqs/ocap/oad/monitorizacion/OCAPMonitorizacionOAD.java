 package es.jcyl.fqs.ocap.oad.monitorizacion;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.monitorizacion.OCAPMonitorizacionOT;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPMonitorizacionOAD
 {
   public Logger loggerBD;
   private static OCAPMonitorizacionOAD instance;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public static OCAPMonitorizacionOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPMonitorizacionOAD();
     }
     return instance;
   }
 
   public OCAPMonitorizacionOAD() {
     $init$();
   }
 
   public ArrayList monitorizarBD(OCAPMonitorizacionOT monitorizacionOT)
     throws SQLException, Exception
   {
     PreparedStatement stObjetos = null;
     ResultSet rsObjetos = null;
 
     PreparedStatement stMonitorizacion = null;
     ResultSet rsMonitorizacion = null;
 
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String[] filtroObjetos = { "TABLE", "VIEW", "SEQUENCE" };
       String nombreUsuario = (monitorizacionOT.getPropietarioObjeto() != null) && (!monitorizacionOT.getPropietarioObjeto().trim().equals("")) ? monitorizacionOT.getPropietarioObjeto().toUpperCase() : "%";
 
       StringBuffer sqlObjetos = new StringBuffer();
       sqlObjetos.append(" SELECT c_owner propietarioObjeto, ").append(" c_object_name nombreObjeto, ").append(" d_object_type tipoObjeto ").append(" FROM comu_objetos ").append(" WHERE UPPER(c_owner) LIKE UPPER(?)").append(" AND UPPER(d_object_type) IN (");
 
       for (int i = 0; i < filtroObjetos.length; i++)
         sqlObjetos.append("?,");
       sqlObjetos.deleteCharAt(sqlObjetos.length() - 1);
 
       sqlObjetos.append(") ").append(" ORDER BY c_owner ASC, d_object_type ASC,  c_object_name ASC ");
 
       stObjetos = con.prepareStatement(sqlObjetos.toString(), 1003, 1007);
 
       int cont = 1;
       stObjetos.setString(cont++, nombreUsuario);
 
       for (int i = 0; i < filtroObjetos.length; i++) {
         stObjetos.setString(cont++, filtroObjetos[i]);
       }
       rsObjetos = stObjetos.executeQuery();
 
       listado = new ArrayList();
       while (rsObjetos.next()) {
         this.loggerBD.debug("Objetos de usuario: '" + nombreUsuario + "' |" + rsObjetos.getString("propietarioObjeto") + " | " + rsObjetos.getString("nombreObjeto") + " | " + rsObjetos.getString("tipoObjeto") + " |");
         OCAPMonitorizacionOT datos = new OCAPMonitorizacionOT();
 
         StringBuffer sqlMonitorizacion = new StringBuffer();
         if ((rsObjetos.getString("tipoObjeto") != null) && (rsObjetos.getString("tipoObjeto").equals("SEQUENCE"))) {
           sqlMonitorizacion.append(" SELECT '").append(rsObjetos.getString("nombreObjeto")).append("' nombreObjeto, ").append(" '").append(rsObjetos.getString("tipoObjeto")).append("' tipoObjeto, ").append(" ").append(rsObjetos.getString("nombreObjeto")).append(".currval monitorizacion ").append(" FROM dual ");
         }
         else if ((rsObjetos.getString("tipoObjeto") != null) && (rsObjetos.getString("tipoObjeto").equals("TABLE"))) {
           sqlMonitorizacion.append(" SELECT '").append(rsObjetos.getString("nombreObjeto")).append("' nombreObjeto, ").append(" '").append(rsObjetos.getString("tipoObjeto")).append("' tipoObjeto, ").append(" TO_CHAR(COUNT(*))||' Registros' monitorizacion ").append(" FROM ").append(rsObjetos.getString("nombreObjeto")).append(" ");
         }
         else if ((rsObjetos.getString("tipoObjeto") != null) && (rsObjetos.getString("tipoObjeto").equals("VIEW"))) {
           sqlMonitorizacion.append(" SELECT '").append(rsObjetos.getString("nombreObjeto")).append("' nombreObjeto, ").append(" '").append(rsObjetos.getString("tipoObjeto")).append("' tipoObjeto, ").append(" TO_CHAR(COUNT(*))||' Registros' monitorizacion ").append(" FROM ").append(rsObjetos.getString("nombreObjeto")).append(" ");
         }
 
         this.loggerBD.info("Sentencia SQL:" + sqlMonitorizacion.toString());
         try
         {
           if ((sqlMonitorizacion != null) && (sqlMonitorizacion.length() > 0)) {
             stMonitorizacion = con.prepareStatement(sqlMonitorizacion.toString(), 1003, 1007);
 
             rsMonitorizacion = stMonitorizacion.executeQuery();
 
             while (rsMonitorizacion.next()) {
               datos.setNombreObjeto(rsMonitorizacion.getString("nombreObjeto"));
               datos.setTipoObjeto(rsMonitorizacion.getString("tipoObjeto"));
               datos.setMonitorizacion(rsMonitorizacion.getString("monitorizacion"));
             }
           } else {
             datos.setNombreObjeto(rsObjetos.getString("nombreObjeto"));
             datos.setTipoObjeto(rsObjetos.getString("tipoObjeto"));
             datos.setMonitorizacion("No existe monitorización para el objeto");
           }
         }
         catch (SQLException ex)
         {
           if (ex.getErrorCode() == 8002) {
             datos.setNombreObjeto(rsObjetos.getString("nombreObjeto"));
             datos.setTipoObjeto(rsObjetos.getString("tipoObjeto"));
             datos.setMonitorizacion("Acceso: OK");
           } else {
             datos.setNombreObjeto(rsObjetos.getString("nombreObjeto"));
             datos.setTipoObjeto(rsObjetos.getString("tipoObjeto"));
             datos.setMonitorizacion("Error acceso: " + ex.getMessage());
           }
         } finally {
           if (rsMonitorizacion != null)
             rsMonitorizacion.close();
           if (stMonitorizacion != null)
             stMonitorizacion.close();
         }
         listado.add(datos);
       }
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       if (rsObjetos != null)
         rsObjetos.close();
       if (stObjetos != null)
         stObjetos.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 }

