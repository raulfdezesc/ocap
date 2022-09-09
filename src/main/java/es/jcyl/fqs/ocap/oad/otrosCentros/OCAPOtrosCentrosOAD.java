 package es.jcyl.fqs.ocap.oad.otrosCentros;
 
 import es.jcyl.cf.seguridad.util.Usuario;
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.otrosCentros.OCAPOtrosCentrosOT;
 import es.jcyl.fqs.ocap.util.DateUtil;
 import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPOtrosCentrosOAD
 {
   public static Logger logger = OCAPConfigApp.logger;
   public Logger loggerBD;
   private static OCAPOtrosCentrosOAD instance;
   protected Connection conTransaccion;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public static OCAPOtrosCentrosOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPOtrosCentrosOAD();
     }
     return instance;
   }
 
   private OCAPOtrosCentrosOAD() {
     $init$();
   }
   
	public void removeAllCentros(Long cSolicitudId, Long cExpId) throws SQLException {
		OCAPConfigApp.logger.info(getClass().getName() + " removeAllCentros: Inicio");

		Connection con = null;

		PreparedStatement sentenciaDelete = null;
		StringBuffer previousDelete = new StringBuffer();
		try {
			con = JCYLGestionTransacciones.getConnection();
			if (cSolicitudId != null) {
				previousDelete.append("DELETE FROM OCAP_OTROSCENTROS WHERE C_SOLICITUD_ID = ? ");
				sentenciaDelete = con.prepareStatement(previousDelete.toString());
				sentenciaDelete.setLong(1, cSolicitudId);
				sentenciaDelete.executeUpdate();
				if (sentenciaDelete != null) {
					sentenciaDelete.close();
				}
				JCYLGestionTransacciones.close(con.getAutoCommit());
			} else if (cExpId != null) {
				previousDelete.append("DELETE FROM OCAP_OTROSCENTROS WHERE C_SOLICITUD_ID = "
						+ "( SELECT exp.C_SOLICITUD_ID FROM OCAP_EXPEDIENTESCARRERA exp WHERE exp.C_EXP_ID = ? )");
				sentenciaDelete = con.prepareStatement(previousDelete.toString());
				sentenciaDelete.setLong(1, cExpId);
				sentenciaDelete.executeUpdate();
				if (sentenciaDelete != null) {
					sentenciaDelete.close();
				}
				JCYLGestionTransacciones.close(con.getAutoCommit());
			}
		} catch (SQLException e) {
			OCAPConfigApp.logger.info(getClass().getName() + " removeAllCentros: ERROR: " + e.getMessage());
			throw new SQLException(e.getMessage() + "<br>Error al insertar los otrosCentros");
		} finally {
			if (sentenciaDelete != null)
				sentenciaDelete.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}
	}
 
   public void altaOtrosCentros(int cExp, ArrayList otrosCentros, JCYLUsuario jcylUsuario)
     throws SQLException
   {
     OCAPConfigApp.logger.info(getClass().getName() + " altaOtrosCentros: Inicio");
 
     Connection con = null;
 
     PreparedStatement sentencia = null;
     PreparedStatement sentenciaDelete = null;
     StringBuffer consulta = new StringBuffer();
     StringBuffer previousDelete = new StringBuffer();
     try
     {
		con = JCYLGestionTransacciones.getConnection();
       for (int i = 0; i < otrosCentros.size(); i++) {
         consulta = new StringBuffer();
         OCAPOtrosCentrosOT otrosCentrosOT = (OCAPOtrosCentrosOT)otrosCentros.get(i);
         consulta.append("INSERT INTO OCAP_OTROSCENTROS ( ").append(" C_CENTRO_ID, C_EXP_ID, D_NOMBRE, D_OBSERVACIONES, F_USUALTA, C_USUALTA, B_BORRADO, A_PROVINCIA, ").append(" A_CATEGORIA, A_SITUACION, A_VINCULO, F_INICIO, F_FIN, C_SOLICITUD_ID ) ").append(" VALUES (OCAP_OTC_ID_SEQ.NEXTVAL, ?,?,?, SYSDATE, ?, 'N' , ?, ").append("?, ?, ?, TO_DATE(?,'DD/MM/RRRR'), TO_DATE(?,'DD/MM/RRRR'), ?) ");
 
         sentencia = con.prepareStatement(consulta.toString());
 
         if (cExp != 0)
           sentencia.setInt(1, cExp);
         else {
           sentencia.setNull(1, 2);
         }
         sentencia.setString(2, otrosCentrosOT.getDNombre());
         sentencia.setString(3, otrosCentrosOT.getDObservaciones());
         sentencia.setString(4, jcylUsuario.getUser().getC_usr_id());
         sentencia.setString(5, otrosCentrosOT.getAProvincia());
         sentencia.setString(6, otrosCentrosOT.getACategoria());
         sentencia.setString(7, otrosCentrosOT.getASituacion());
         sentencia.setString(8, otrosCentrosOT.getAVinculo());
         sentencia.setString(9, DateUtil.convertDateToString(otrosCentrosOT.getFInicio()));
         sentencia.setString(10, DateUtil.convertDateToString(otrosCentrosOT.getFFin()));
         sentencia.setLong(11, otrosCentrosOT.getCSolicitud_id());
 
         sentencia.executeUpdate();
       }
       OCAPConfigApp.logger.info(getClass().getName() + " altaOtrosCentros: Fin");
     } catch (SQLException e) {
       OCAPConfigApp.logger.info(getClass().getName() + " altaOtrosCentros: ERROR: " + e.getMessage());
       throw new SQLException(e.getMessage() + "<br>Error al insertar los otrosCentros");
     } finally {
       if (sentencia != null)
         sentencia.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
   }
 
   public ArrayList buscarOCAPOtrosCentros(Long cExpId, Connection con)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     ArrayList listadoOtrosCentros = new ArrayList();
     try
     {
       String sql = "SELECT C_CENTRO_ID, C_EXP_ID, D_NOMBRE, D_OBSERVACIONES, F_USUALTA, F_USUMODI, A_PROVINCIA, A_CATEGORIA, A_SITUACION, A_VINCULO, F_INICIO, F_FIN, B_BORRADO FROM OCAP_OTROSCENTROS WHERE C_EXP_ID = ? AND B_BORRADO = 'N' ORDER BY D_NOMBRE asc, F_USUALTA asc ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId.longValue());
 
       rs = st.executeQuery();
 
       while (rs.next()) {
         OCAPOtrosCentrosOT datos = new OCAPOtrosCentrosOT();
         datos.setCCentro_id(rs.getLong("C_CENTRO_ID"));
         datos.setCExp_id(rs.getLong("C_EXP_ID"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
         datos.setFCreacion(rs.getDate("F_USUALTA"));
         datos.setFModicacion(rs.getDate("F_USUMODI"));
         datos.setAProvincia(rs.getString("A_PROVINCIA"));
         datos.setACategoria(rs.getString("A_CATEGORIA"));
         datos.setASituacion(rs.getString("A_SITUACION"));
         datos.setAVinculo(rs.getString("A_VINCULO"));
         datos.setFInicio(rs.getDate("F_INICIO"));
         datos.setFFin(rs.getDate("F_FIN"));
         datos.setBBorrado(rs.getString("B_BORRADO"));
 
         listadoOtrosCentros.add(datos);
       }
     }
     catch (SQLException ex) {
       throw ex;
     } catch (Exception ex) {
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null) {
         st.close();
       }
 
     }
 
     return listadoOtrosCentros;
   }
 
   public int bajaOtrosCentros(long cExp, String modificador, Connection con)
     throws SQLException
   {
     OCAPConfigApp.logger.info(getClass().getName() + " bajaOtrosCentros: Inicio");
 
     PreparedStatement st = null;
     int filas = 0;
     try
     {
       String sql = "UPDATE OCAP_OTROSCENTROS SET B_BORRADO = 'Y', C_USUMODI = ?, F_USUMODI = SYSDATE WHERE C_EXP_ID =  ?";
 
       st = con.prepareStatement(sql);
       st.setString(1, modificador);
       st.setLong(2, cExp);
 
       filas = st.executeUpdate();
 
       OCAPConfigApp.logger.info(getClass().getName() + " bajaOtrosCentros: Fin");
     } catch (SQLException e) {
       OCAPConfigApp.logger.info(getClass().getName() + " bajaOtrosCentros: ERROR: " + e.getMessage());
       throw new SQLException(e.getMessage() + "<br>Error al borrar los otrosCentros");
     } finally {
       if (st != null) {
         st.close();
       }
 
     }
 
     return filas;
   }
 
   public ArrayList buscarOCAPOtrosCentrosSolic(long cSolicitudId)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     ArrayList listadoOtrosCentros = new ArrayList();
     Connection con = JCYLGestionTransacciones.getConnection();
     try
     {
       this.loggerBD.info(" buscarOCAPOtrosCentrosSolic: " + cSolicitudId);
 
       String sql = "SELECT C_CENTRO_ID, C_EXP_ID, D_NOMBRE, D_OBSERVACIONES, F_USUALTA, F_USUMODI, A_PROVINCIA, A_CATEGORIA, A_SITUACION, A_VINCULO, F_INICIO, F_FIN, B_BORRADO FROM OCAP_OTROSCENTROS WHERE C_SOLICITUD_ID = ? AND B_BORRADO = 'N' ORDER BY D_NOMBRE asc, F_USUALTA asc ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cSolicitudId);
 
       rs = st.executeQuery();
 
       while (rs.next()) {
         OCAPOtrosCentrosOT datos = new OCAPOtrosCentrosOT();
         datos.setCCentro_id(rs.getLong("C_CENTRO_ID"));
         datos.setCExp_id(rs.getLong("C_EXP_ID"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
         datos.setFCreacion(rs.getDate("F_USUALTA"));
         datos.setFModicacion(rs.getDate("F_USUMODI"));
         datos.setAProvincia(rs.getString("A_PROVINCIA"));
         datos.setACategoria(rs.getString("A_CATEGORIA"));
         datos.setASituacion(rs.getString("A_SITUACION"));
         datos.setAVinculo(rs.getString("A_VINCULO"));
         datos.setFInicio(rs.getDate("F_INICIO"));
         datos.setFFin(rs.getDate("F_FIN"));
         datos.setBBorrado(rs.getString("B_BORRADO"));
 
         listadoOtrosCentros.add(datos);
       }
     }
     catch (SQLException ex) {
       throw ex;
     }
     finally {
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listadoOtrosCentros;
   }
 
   public int modificarOtrosCentros(long idExpediente, long cSolicitudId, String cUsumodi, Connection con)
     throws SQLException
   {
     OCAPConfigApp.logger.info(getClass().getName() + " modificarOtrosCentros: Inicio");
 
     boolean bCrearConexion = false;
     PreparedStatement st = null;
     int filas = 0;
     try
     {
       if (con == null) {
         bCrearConexion = true;
         con = JCYLGestionTransacciones.getConnection();
       }
       String sql = "UPDATE OCAP_OTROSCENTROS SET C_EXP_ID = ?, C_USUMODI = ?, F_USUMODI = SYSDATE WHERE C_SOLICITUD_ID =  ?";
 
       st = con.prepareStatement(sql);
       if (idExpediente != 0L)
         st.setLong(1, idExpediente);
       else
         st.setNull(1, 2);
       st.setString(2, cUsumodi);
       st.setLong(3, cSolicitudId);
 
       filas = st.executeUpdate();
 
       OCAPConfigApp.logger.info(getClass().getName() + " modificarOtrosCentros: Fin");
     } catch (SQLException e) {
       OCAPConfigApp.logger.info(getClass().getName() + " modificarOtrosCentros: ERROR: " + e.getMessage());
       throw new SQLException(e.getMessage() + "<br>Error al modificar los otrosCentros");
     } finally {
       if (st != null)
         st.close();
       if (bCrearConexion) {
         JCYLGestionTransacciones.close(con.getAutoCommit());
       }
 
     }
 
     return filas;
   }
 
   public int desasociarOtrosCentrosExpediente(long idExpediente, String cUsumodi, Connection con)
     throws SQLException
   {
     OCAPConfigApp.logger.info(getClass().getName() + " desasociarOtrosCentrosExpediente: Inicio");
 
     boolean bCrearConexion = false;
     PreparedStatement st = null;
     int filas = 0;
     try
     {
       if (con == null) {
         bCrearConexion = true;
         con = JCYLGestionTransacciones.getConnection();
       }
       String sql = "UPDATE OCAP_OTROSCENTROS SET C_EXP_ID = null, C_USUMODI = ?, F_USUMODI = SYSDATE WHERE C_EXP_ID =  ?";
 
       st = con.prepareStatement(sql);
       st.setString(1, cUsumodi);
       st.setLong(2, idExpediente);
 
       filas = st.executeUpdate();
 
       OCAPConfigApp.logger.info(getClass().getName() + " desasociarOtrosCentrosExpediente: Fin");
     } catch (SQLException e) {
       OCAPConfigApp.logger.info(getClass().getName() + " desasociarOtrosCentrosExpediente: ERROR: " + e.getMessage());
       throw new SQLException(e.getMessage() + "<br>Error al desasociar los otrosCentros");
     } finally {
       if (st != null)
         st.close();
       if (bCrearConexion) {
         JCYLGestionTransacciones.close(con.getAutoCommit());
       }
 
     }
 
     return filas;
   }
 }

