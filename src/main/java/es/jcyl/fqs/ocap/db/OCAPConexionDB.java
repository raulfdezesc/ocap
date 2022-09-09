package es.jcyl.fqs.ocap.db;

import es.jcyl.cf.seguridad.util.Usuario;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLUsuario;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class OCAPConexionDB
{
  public static Connection getDBConnection(String nombreDataSource, Usuario usuario)
    throws SQLException
  {
    try
    {
      return JCYLGestionTransacciones.getConnection(nombreDataSource);
    } catch (Throwable e) {
      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
      throw new SQLException(e.getMessage());
    }
  }

  public static Connection getDBConnection(String username, String password, String thinConn, String driverClass)
    throws SQLException
  {
    JCYLGestionTransacciones.open(username, password, thinConn, driverClass, false);

    return JCYLGestionTransacciones.getConnection();
  }

  public static Connection getDBConnection(String nombreDataSource, JCYLUsuario jcylUsuario)
    throws SQLException
  {
    String nombreDS = null;
    try
    {
      if ((nombreDataSource == null) || (nombreDataSource.length() == 0))
        nombreDS = JCYLConfiguracion.getValor("NOMBRE_DATASOURCE");
      else {
        nombreDS = nombreDataSource;
      }

      PreparedStatement sentencia = null;
      String cadenaSQL = "BEGIN DBMS_SESSION.SET_IDENTIFIER('" + jcylUsuario.getUsuario().getC_usr_id() + "');END;";
      sentencia = JCYLGestionTransacciones.getConnection(nombreDataSource).prepareStatement(cadenaSQL);
      sentencia.executeUpdate();
      sentencia.close();
      sentencia = null;

      return JCYLGestionTransacciones.getConnection(nombreDataSource);
    } catch (Throwable e) {
      throw new SQLException(e.getMessage());
    }
  }

  public static void freeConnection(Connection con)
  {
    try
    {
      if (con != null)
        con.close();
      else
        freeConnection(true);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static void freeConnection(boolean autoCommit) {
    try {
      JCYLGestionTransacciones.close(autoCommit);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
