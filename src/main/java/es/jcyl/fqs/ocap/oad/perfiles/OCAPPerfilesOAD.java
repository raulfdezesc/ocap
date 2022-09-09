 package es.jcyl.fqs.ocap.oad.perfiles;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.perfiles.OCAPPerfilesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPPerfilesOAD
 {
   public static Logger logger = OCAPConfigApp.logger;
   private static OCAPPerfilesOAD instance;
 
   public static OCAPPerfilesOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPPerfilesOAD();
     }
     return instance;
   }
 
   public int altaOCAPPerfiles(OCAPPerfilesOT datos)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "INSERT INTO OCAP_PERFILES (C_PERFIL_ID, B_BORRADO, D_NOMBRE, F_USUALTA, D_DESCRIPCION, C_USUALTA) VALUES (OCAP_PRF_ID_SEQ.nextval, 'N', ?,  SYSDATE, ?, ?)";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
 
       st.setString(1, datos.getDNombre());
 
       if (datos.getDDescripcion() != null)
         st.setString(2, datos.getDDescripcion());
       else
         st.setNull(2, 12);
       st.setString(3, datos.getACreadoPor());
 
       filas = st.executeUpdate();
     }
     catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return filas;
   }
 
   public int bajaOCAPPerfiles(long cPerfilId)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_PERFILES SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_PERFIL_ID =  ?";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cPerfilId);
 
       filas = st.executeUpdate();
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return filas;
   }
 
   public int modificacionOCAPPerfiles(OCAPPerfilesOT datos)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_PERFILES SET D_NOMBRE = ?, F_USUMODI = SYSDATE, D_DESCRIPCION = ? WHERE C_PERFIL_ID = ?";
 
       st = con.prepareStatement(sql);
 
       st.setString(1, datos.getDNombre());
 
       if (datos.getDDescripcion() != null)
         st.setString(2, datos.getDDescripcion());
       else {
         st.setNull(2, 12);
       }
       st.setLong(3, datos.getCPerfilId());
 
       filas = st.executeUpdate();
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return filas;
   }
 
   public OCAPPerfilesOT buscarOCAPPerfiles(long cPerfilId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     OCAPPerfilesOT datos = null;
     try
     {
       String sql = "SELECT C_PERFIL_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION FROM OCAP_PERFILES WHERE C_PERFIL_ID = ? AND B_BORRADO = 'N'";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cPerfilId);
 
       rs = st.executeQuery();
 
       datos = new OCAPPerfilesOT();
       if (rs.next()) {
         datos.setCPerfilId(rs.getLong("C_PERFIL_ID"));
         datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setFModificacion(rs.getDate("F_USUMODI"));
         datos.setFCreacion(rs.getDate("F_USUALTA"));
         datos.setBBorrado(rs.getString("B_BORRADO"));
       }
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return datos;
   }
 
   public ArrayList listadoOCAPPerfiles()
     throws SQLException
   {
     return listadoOCAPPerfiles(1, -1);
   }
 
   public ArrayList listadoOCAPPerfiles(int inicio, int cuantos)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String sql = "SELECT C_PERFIL_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION FROM OCAP_PERFILES WHERE B_BORRADO='N'  order by C_PERFIL_ID";
 
       st = con.prepareStatement(sql, 1004, 1008);
       rs = st.executeQuery();
 
       if (inicio > 1) {
         rs.absolute(inicio - 1);
       }
       listado = new ArrayList();
       int i = 0;
       while (rs.next()) {
         OCAPPerfilesOT datos = new OCAPPerfilesOT();
         datos.setCPerfilId(rs.getLong("C_PERFIL_ID"));
         datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setFCreacion(rs.getDate("F_USUALTA"));
         datos.setFModificacion(rs.getDate("F_USUMODI"));
         datos.setBBorrado(rs.getString("B_BORRADO"));
 
         listado.add(datos);
 
         if (++i == cuantos)	break;
       }
 
     }
     catch (SQLException ex)
     {
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 
   public int listadoOCAPPerfilesCuenta(long cPerfilId, String dNombre, String dDescripcion, String fCreacion, String fModificacion)
     throws Exception
   {
     ResultSet rs = null;
     Statement st = null;
     String where = "";
     StringBuffer sbWhere = new StringBuffer(40);
     Connection con = JCYLGestionTransacciones.getConnection();
     String sql = "SELECT COUNT(*) FROM OCAP_PERFILES ";
 
     boolean isCPerfilId = cPerfilId != 0L;
     if (isCPerfilId)
       sbWhere.append("C_PERFIL_ID = ").append(cPerfilId).append(" AND ");
     boolean isDPerfilDesc = (dDescripcion != null) && (!dDescripcion.equals(""));
     if (isDPerfilDesc)
       sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dDescripcion).append("%')) AND ");
     boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
     if (isDNombre)
       sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
     boolean isFCreacion = (fCreacion != null) && (!fCreacion.equals(""));
     if (isFCreacion)
       sbWhere.append("to_char(F_USUALTA,'DD/MM/RRRR') = '").append(fCreacion).append("' AND ");
     boolean isFModificacion = (fModificacion != null) && (!fModificacion.equals(""));
     if (isFModificacion)
       sbWhere.append("to_char(F_USUMODI,'DD/MM/RRRR') = '").append(fModificacion).append("' AND ");
     sbWhere.append("B_BORRADO = 'N'  AND ");
     int len = sbWhere.length();
     if (len > 0) {
       where = sbWhere.insert(0, " Where ").substring(0, len + 1);
     }
     String sqlStatement = sql + where;
     int total = -1;
     try {
       st = con.prepareStatement(sqlStatement);
       rs = st.executeQuery(sqlStatement);
       if (rs.next())
         total = rs.getInt(1);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return total;
   }
 
   public ArrayList consultaOCAPPerfiles(long cPerfilId, String dNombre, String dDescripcion, String fCreacion, String fModificacion, int inicio, int cuantos)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String where = "";
       StringBuffer sbWhere = new StringBuffer(40);
       String selectFrom = "SELECT C_PERFIL_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION FROM OCAP_PERFILES";
 
       String orderBy = " order by C_PERFIL_ID";
       boolean isCPerfilId = cPerfilId != 0L;
       if (isCPerfilId)
         sbWhere.append("C_PERFIL_ID = ?  AND ");
       boolean isDPerfilDesc = (dDescripcion != null) && (!dDescripcion.equals(""));
       if (isDPerfilDesc)
         sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dDescripcion).append("%')) AND ");
       boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
       if (isDNombre)
         sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
       boolean isFCreacion = (fCreacion != null) && (!fCreacion.equals(""));
       if (isFCreacion)
         sbWhere.append("to_char(F_USUALTA,'DD/MM/RRRR') = '").append(fCreacion).append("' AND ");
       boolean isFModificacion = (fModificacion != null) && (!fModificacion.equals(""));
       if (isFModificacion)
         sbWhere.append("to_char(F_USUMODI,'DD/MM/RRRR') = '").append(fModificacion).append("' AND ");
       sbWhere.append("B_BORRADO = 'N'  AND ");
       int len = sbWhere.length();
       if (len > 0) {
         where = sbWhere.insert(0, " Where ").substring(0, len + 1);
       }
       String sqlStatement = selectFrom + where + orderBy;
       OCAPConfigApp.logger.info("Sentencia SQL:" + sqlStatement);
       st = con.prepareStatement(sqlStatement, 1004, 1008);
 
       int index = 1;
       OCAPPerfilesOT datos = new OCAPPerfilesOT();
 
       if (isCPerfilId) {
         st.setLong(index++, cPerfilId);
       }
       rs = st.executeQuery();
       if (inicio > 1)
         rs.absolute(inicio - 1);
       listado = new ArrayList();
       int i = 0;
       while (rs.next()) {
         datos = new OCAPPerfilesOT();
         datos.setCPerfilId(rs.getLong("C_PERFIL_ID"));
         datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setFCreacion(rs.getDate("F_USUALTA"));
         datos.setFModificacion(rs.getDate("F_USUMODI"));
         datos.setBBorrado(rs.getString("B_BORRADO"));
 
         listado.add(datos);
         if (++i == cuantos)	break;
       }
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 }

