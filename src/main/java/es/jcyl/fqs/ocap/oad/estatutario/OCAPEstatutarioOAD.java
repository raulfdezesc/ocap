 package es.jcyl.fqs.ocap.oad.estatutario;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.estatutario.OCAPEstatutarioOT;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPEstatutarioOAD
 {
   public Logger loggerBD;
   private static OCAPEstatutarioOAD instance;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public static OCAPEstatutarioOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPEstatutarioOAD();
     }
     return instance;
   }
 
   private OCAPEstatutarioOAD() {
     $init$();
   }
 
   public int altaOCAPEstatutario(OCAPEstatutarioOT datos)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "INSERT INTO OCAP_ESTATUTARIO(C_ESTATUT_ID, C_PERSONAL_ID, B_BORRADO, D_NOMBRE, F_USUALTA, D_DESCRIPCION, C_USUALTA) VALUES (OCAP_EST_ID_SEQ.nextval, ?,'N', ?, SYSDATE, ?, ?)";
 
       this.loggerBD.info("Sentencia SQL:" + sql);
 
       st = con.prepareStatement(sql);
 
       st.setLong(1, datos.getCPersonalId());
       st.setString(2, datos.getDNombre());
       st.setString(3, datos.getACreadoPor());
 
       if (datos.getDDescripcion() != null)
         st.setString(3, datos.getDDescripcion());
       else {
         st.setNull(3, 12);
       }
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
 
   public int bajaOCAPEstatutario(int cEstatutId)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_ESTATUTARIO SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_ESTATUT_ID =  ?";
 
       st = con.prepareStatement(sql);
 
       st.setInt(1, cEstatutId);
 
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
 
   public int modificacionOCAPEstatutario(OCAPEstatutarioOT datos)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_ESTATUTARIO SET D_NOMBRE = ?, F_USUMODI = SYSDATE, D_DESCRIPCION = ?, C_PERSONAL_ID = ? WHERE C_ESTATUT_ID = ?";
 
       st = con.prepareStatement(sql);
 
       st.setString(1, datos.getDNombre());
 
       if (datos.getDDescripcion() != null)
         st.setString(2, datos.getDDescripcion());
       else {
         st.setNull(2, 12);
       }
       st.setLong(3, datos.getCPersonalId());
 
       st.setLong(4, datos.getCEstatutId());
 
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
 
   public OCAPEstatutarioOT buscarOCAPEstatutario(long cEstatutId)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     OCAPEstatutarioOT datos = null;
     try
     {
       this.loggerBD.info(" buscarOCAPEstatutario: " + cEstatutId);
 
       String sql = "SELECT C_ESTATUT_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION, C_PERSONAL_ID FROM OCAP_ESTATUTARIO WHERE C_ESTATUT_ID = ? AND B_BORRADO = 'N'";
 
       st = con.prepareStatement(sql);
 
       st.setLong(1, cEstatutId);
 
       rs = st.executeQuery();
 
       datos = new OCAPEstatutarioOT();
       if (rs.next()) {
         datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
         datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setCPersonalId(rs.getLong("C_PERSONAL_ID"));
         datos.setFCreacion(rs.getDate("F_USUALTA"));
         datos.setFModificacion(rs.getDate("F_USUMODI"));
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
 
   public ArrayList listadoOCAPEstatutario()
     throws Exception
   {
     return listadoOCAPEstatutario(1, -1);
   }
 
   public ArrayList listadoOCAPEstatutario(int inicio, int cuantos)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String sql = "SELECT C_ESTATUT_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION, C_PERSONAL_ID FROM OCAP_ESTATUTARIO WHERE B_BORRADO = 'N' order by D_NOMBRE";
 
       st = con.prepareStatement(sql, 1004, 1008);
 
       rs = st.executeQuery();
 
       if (inicio > 1) {
         rs.absolute(inicio - 1);
       }
       listado = new ArrayList();
 
       int i = 0;
       while (rs.next()) {
         OCAPEstatutarioOT datos = new OCAPEstatutarioOT();
         datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
         datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setCPersonalId(rs.getLong("C_PERSONAL_ID"));
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
 
   public int listadoOCAPEstatutarioCuenta(long cEstatutId, long cPersonalId, String dNombre, String dDescripcion, String fCreacion, String fModificacion)
     throws SQLException, Exception
   {
     ResultSet rs = null;
     Statement st = null;
 
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int total = -1;
     try
     {
       String where = "";
       StringBuffer sbWhere = new StringBuffer(40);
 
       String sql = "SELECT COUNT(*) FROM OCAP_ESTATUTARIO ";
 
       boolean isCEstatutId = cEstatutId != 0L;
       if (isCEstatutId)
         sbWhere.append("C_ESTATUT_ID = ").append(cEstatutId).append(" AND ");
       boolean isCPersonalId = cPersonalId != 0L;
       if (isCPersonalId)
         sbWhere.append("C_PERSONAL_ID = ").append(cPersonalId).append(" AND ");
       boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
       if (isDNombre)
         sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
       boolean isDDescripcion = (dDescripcion != null) && (!dDescripcion.equals(""));
       if (isDDescripcion)
         sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dDescripcion).append("%')) AND ");
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
       st = con.prepareStatement(sqlStatement);
       rs = st.executeQuery(sqlStatement);
       if (rs.next())
         total = rs.getInt(1);
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
 
     return total;
   }
 
   public ArrayList consultaOCAPEstatutario(long cEstatutId, long cPersonalId, String dNombre, String dDescripcion, String fCreacion, String fModificacion, int inicio, int cuantos)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String where = "";
       StringBuffer sbWhere = new StringBuffer(40);
       String selectFrom = "SELECT C_ESTATUT_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION, C_PERSONAL_ID FROM OCAP_ESTATUTARIO";
 
       String orderBy = " order by C_ESTATUT_ID";
       boolean isCEstatutId = cEstatutId != 0L;
       if (isCEstatutId)
         sbWhere.append("C_ESTATUT_ID = ?  AND ");
       boolean isCPersonalId = cPersonalId != 0L;
       if (isCPersonalId)
         sbWhere.append("C_PERSONAL_ID = ?  AND ");
       boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
       if (isDNombre)
         sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
       boolean isDDescripcion = (dDescripcion != null) && (!dDescripcion.equals(""));
       if (isDDescripcion)
         sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dDescripcion).append("%')) AND ");
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
 
       this.loggerBD.info("Sentencia SQL:" + sqlStatement);
 
       st = con.prepareStatement(sqlStatement, 1004, 1008);
 
       int index = 1;
       if (isCEstatutId) {
         st.setLong(index++, cEstatutId);
       }
       if (isCPersonalId) {
         st.setLong(index++, cPersonalId);
       }
       rs = st.executeQuery();
       if (inicio > 1)
         rs.absolute(inicio - 1);
       listado = new ArrayList();
       int i = 0;
       while (rs.next()) {
         OCAPEstatutarioOT datos = new OCAPEstatutarioOT();
         datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
         datos.setCPersonalId(rs.getLong("C_PERSONAL_ID"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
         datos.setFCreacion(rs.getDate("F_USUALTA"));
         datos.setFModificacion(rs.getDate("F_USUMODI"));
         datos.setBBorrado(rs.getString("B_BORRADO"));
 
         listado.add(datos);
         if (++i == cuantos)	break;
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
 
     return listado;
   }
 
   public ArrayList listadoOCAPEstatutariosUsados()
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       StringBuffer sql = new StringBuffer();
       sql.append(" SELECT DISTINCT esta.c_estatut_id, esta.d_nombre ").append(" FROM (SELECT DISTINCT c_profesional_id FROM ocap_expedientescarrera WHERE b_borrado='N' AND c_itinerario_id IS NOT NULL) exca, ").append(" ocap_estatutario esta, ").append(" ocap_categ_profesionales cate ").append(" WHERE cate.b_borrado = 'N' ").append(" AND esta.b_borrado = 'N' ").append(" AND cate.c_estatut_id = esta.c_estatut_id ").append(" AND cate.c_profesional_id = exca.c_profesional_id ").append(" ORDER BY esta.c_estatut_id ");
 
       st = con.prepareStatement(sql.toString(), 1004, 1008);
 
       rs = st.executeQuery();
 
       listado = new ArrayList();
       int i = 0;
       while (rs.next()) {
         OCAPEstatutarioOT datos = new OCAPEstatutarioOT();
         datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
 
         listado.add(datos);
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
 
     return listado;
   }
 }

