 package es.jcyl.fqs.ocap.oad.areasTrabajo;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.areasTrabajo.OCAPAreasTrabajoOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPAreasTrabajoOAD
 {
   public Logger logger;
   private static OCAPAreasTrabajoOAD instance;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
   public static OCAPAreasTrabajoOAD getInstance() {
     if (instance == null) {
       instance = new OCAPAreasTrabajoOAD();
     }
     return instance;
   }
 
   private OCAPAreasTrabajoOAD() {
     $init$();
   }
 
   public int altaOCAPAreasTrabajo(OCAPAreasTrabajoOT datos)
     throws Exception
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "INSERT INTO OCAP_AREASTRABAJO (C_AREA_ID, B_BORRADO, D_NOMBRE, F_USUALTA, D_DESCRIPCION, C_USUALTA) VALUES (OCAP_ARE_ID_SEQ.nextval, 'N', ?, SYSDATE, ?, ?)";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
       st.setString(1, datos.getDNombre());
       st.setString(2, datos.getDDescripcion());
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
 
   public int bajaOCAPAreasTrabajo(int cAreaId)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_AREASTRABAJO SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_AREA_ID =  ?";
 
       st = con.prepareStatement(sql);
       st.setInt(1, cAreaId);
 
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
 
   public int modificacionOCAPAreasTrabajo(OCAPAreasTrabajoOT datos)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_AREASTRABAJO SET D_NOMBRE = ?, F_USUMODI = SYSDATE, D_DESCRIPCION = ? WHERE C_AREA_ID = ?";
 
       st = con.prepareStatement(sql);
 
       st.setString(1, datos.getDNombre());
 
       if (datos.getDDescripcion() != null)
         st.setString(2, datos.getDDescripcion());
       else {
         st.setNull(2, 12);
       }
       st.setLong(3, datos.getCAreaId());
 
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
 
   public OCAPAreasTrabajoOT buscarOCAPAreasTrabajo(long cAreaId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     OCAPAreasTrabajoOT datos = null;
     try
     {
       String sql = "SELECT C_AREA_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION FROM OCAP_AREASTRABAJO WHERE C_AREA_ID = ? AND B_BORRADO = 'N'";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cAreaId);
 
       rs = st.executeQuery();
 
       datos = new OCAPAreasTrabajoOT();
       if (rs.next()) {
         datos.setCAreaId(rs.getLong("C_AREA_ID"));
         datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
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
 
   public ArrayList listadoOCAPAreasTrabajo()
     throws Exception
   {
     return listadoOCAPAreasTrabajo(1, -1);
   }
 
   public ArrayList listadoOCAPAreasTrabajo(int inicio, int cuantos)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     ArrayList listado = null;
     try
     {
       String sql = "SELECT C_AREA_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION FROM OCAP_AREASTRABAJO WHERE B_BORRADO = 'N' order by C_AREA_ID";
 
       st = con.prepareStatement(sql, 1004, 1008);
       rs = st.executeQuery();
 
       if (inicio > 1) {
         rs.absolute(inicio - 1);
       }
   	listado = new ArrayList();
	int i = 0;
	while (rs.next()) {
         OCAPAreasTrabajoOT datos = new OCAPAreasTrabajoOT();
         datos.setCAreaId(rs.getLong("C_AREA_ID"));
         datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setFCreacion(rs.getDate("F_USUALTA"));
         datos.setFModificacion(rs.getDate("F_USUMODI"));
         datos.setBBorrado(rs.getString("B_BORRADO"));
 
         listado.add(datos);
         if (++i == cuantos)
				break;
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
 
   public int listadoOCAPAreasTrabajoCuenta(long cAreaId, String dNombre, String dDescripcion, String fCreacion, String fModificacion)
     throws Exception
   {
     ResultSet rs = null;
     PreparedStatement st = null;
 
     String where = "";
     StringBuffer sbWhere = new StringBuffer(40);
     Connection con = JCYLGestionTransacciones.getConnection();
     String sql = "SELECT COUNT(*) FROM OCAP_AREASTRABAJO";
 
     boolean isCAreaId = cAreaId != 0L;
     if (isCAreaId) sbWhere.append("C_AREA_ID = ").append(cAreaId).append(" AND ");
     boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
     if (isDNombre) sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
     boolean isDDescripcion = (dDescripcion != null) && (!dDescripcion.equals(""));
     if (isDDescripcion) sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dDescripcion).append("%')) AND ");
     boolean isFCreacion = (fCreacion != null) && (!fCreacion.equals(""));
     if (isFCreacion) sbWhere.append("to_char(F_USUALTA,'DD/MM/RRRR') = '").append(fCreacion).append("' AND ");
     boolean isFModificacion = (fModificacion != null) && (!fModificacion.equals(""));
     if (isFModificacion) sbWhere.append("to_char(F_USUMODI,'DD/MM/RRRR') = '").append(fModificacion).append("' AND ");
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
 
   public ArrayList consultaOCAPAreasTrabajo(long cAreaId, String dNombre, String dDescripcion, String fCreacion, String fModificacion, int inicio, int cuantos)
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
       String selectFrom = "SELECT C_AREA_ID, B_BORRADO, D_NOMBRE, F_USUMODI, F_USUALTA, D_DESCRIPCION FROM OCAP_AREASTRABAJO";
 
       String orderBy = " order by C_AREA_ID";
       boolean isCAreaId = cAreaId != 0L;
       if (isCAreaId) sbWhere.append("C_AREA_ID = ?  AND ");
       boolean isDNombre = (dNombre != null) && (!dNombre.equals(""));
       if (isDNombre) sbWhere.append("(upper(D_NOMBRE) like upper('%").append(dNombre).append("%')) AND ");
       boolean isDDescripcion = (dDescripcion != null) && (!dDescripcion.equals(""));
       if (isDDescripcion) sbWhere.append("(upper(D_DESCRIPCION) like upper('%").append(dDescripcion).append("%')) AND ");
       boolean isFCreacion = (fCreacion != null) && (!fCreacion.equals(""));
       if (isFCreacion) sbWhere.append("to_char(F_USUALTA,'DD/MM/RRRR') = '").append(fCreacion).append("' AND ");
       boolean isFModificacion = (fModificacion != null) && (!fModificacion.equals(""));
       if (isFModificacion) sbWhere.append("to_char(F_USUMODI,'DD/MM/RRRR') = '").append(fModificacion).append("' AND ");
       sbWhere.append("B_BORRADO = 'N'  AND ");
       int len = sbWhere.length();
       if (len > 0) {
         where = sbWhere.insert(0, " Where ").substring(0, len + 1);
       }
       String sqlStatement = selectFrom + where + orderBy;
       OCAPConfigApp.logger.info("Sentencia SQL:" + sqlStatement);
       st = con.prepareStatement(sqlStatement, 1004, 1008);
 
       int index = 1;
       OCAPAreasTrabajoOT datos = new OCAPAreasTrabajoOT();
 
       if (isCAreaId) {
         st.setLong(index++, cAreaId);
       }
       rs = st.executeQuery();
       if (inicio > 1)
         rs.absolute(inicio - 1);
       listado = new ArrayList();
		int i = 0;
		while (rs.next()) {
      
    
         datos = new OCAPAreasTrabajoOT();
         datos.setCAreaId(rs.getLong("C_AREA_ID"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
         datos.setFCreacion(rs.getDate("F_USUALTA"));
         datos.setFModificacion(rs.getDate("F_USUMODI"));
         datos.setBBorrado(rs.getString("B_BORRADO"));
 
         listado.add(datos);
         if (++i == cuantos)	break;
       }
     } catch (Exception ex) {
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

