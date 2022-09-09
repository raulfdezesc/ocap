 package es.jcyl.fqs.ocap.oad.acceso;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.acceso.OCAPAccesoOT;
import es.jcyl.fqs.ocap.ot.mensajes.OCAPMensajesOT;
import es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.CallableStatement;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.text.DecimalFormat;
 import java.util.ArrayList;
 import java.util.Map;
 import org.apache.log4j.Logger;
 
 public class OCAPAccesoOAD
 {
   public static Logger logger = OCAPConfigApp.logger;
   protected Connection conTransaccion;
   private static OCAPAccesoOAD instance;
 
   public static OCAPAccesoOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPAccesoOAD();
     }
     return instance;
   }
 
   public int altaOCAPAcceso(OCAPAccesoOT datos )
     throws Exception
   {
	   PreparedStatement st = null;
	Connection con = JCYLGestionTransacciones.getConnection();
	int filas = 0;
	
     try
     {
       String sql = "INSERT INTO OCAP_ACCESO  (C_DNI,A_CONTRASENA,A_CORREOELECTRONICO_RECUP ) VALUES (?,?,?)";
  
       st = con.prepareStatement(sql);
       int cont = 1;
       st.setString(cont++, datos.getCDni());
       st.setString(cont++, datos.getAContrasenia());
       st.setString(cont++, datos.getACorreoRecupera());
       
       filas = st.executeUpdate();
        
       
      
     } catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
 
      
   }
   
   
   public OCAPAccesoOT buscarOCAPAcceso(String cDni) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPAccesoOT datos = null;
		try {
			String sql = "SELECT C_DNI, A_CONTRASENA,A_CORREOELECTRONICO_RECUP FROM  OCAP_ACCESO WHERE C_DNI = ?";

			st = con.prepareStatement(sql);
			st.setString(1, cDni);
			rs = st.executeQuery();

			
			if (rs.next()) {
				datos = new OCAPAccesoOT();
				datos.setCDni(rs.getString("C_DNI"));
				datos.setAContrasenia(rs.getString("A_CONTRASENA"));
				datos.setACorreoRecupera(rs.getString("A_CORREOELECTRONICO_RECUP"));				
			}
		} catch (SQLException ex) {
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

  
 
  }
