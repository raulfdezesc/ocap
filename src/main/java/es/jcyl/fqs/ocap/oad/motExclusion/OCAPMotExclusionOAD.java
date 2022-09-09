 package es.jcyl.fqs.ocap.oad.motExclusion;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.motExclusion.OCAPMotExclusionOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPMotExclusionOAD
 {
   public Logger logger;
   private static OCAPMotExclusionOAD instance;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
 
   public static OCAPMotExclusionOAD getInstance() {
     if (instance == null) {
       instance = new OCAPMotExclusionOAD();
     }
     return instance;
   }
 
   private OCAPMotExclusionOAD() {
     $init$();
   }
 
   public OCAPMotExclusionOT buscarMotivo(String dNombre)
     throws SQLException
   {     
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     OCAPMotExclusionOT motivos = null;
      StringBuffer sql = new StringBuffer();
     try
     {
       sql.append("SELECT C_EXCLUSION_ID, D_DESCRIPCION ").append("FROM OCAP_MOT_EXCLUSION ").append(" WHERE UPPER(translate(D_NOMBRE,'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) LIKE UPPER(translate('%" + dNombre + "%','áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) ").append(" AND B_BORRADO = 'N'");
        st = con.prepareStatement(sql.toString());
       rs = st.executeQuery();
       motivos = new OCAPMotExclusionOT();
       if (rs.next()) {
         motivos.setDDescripcion(rs.getString("D_DESCRIPCION"));
         motivos.setCExclusionId(rs.getLong("C_EXCLUSION_ID"));
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
 
     return motivos;
   }
 
   public String buscarMotivosExpediente(long cExpId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     StringBuffer motivos = new StringBuffer();
 
     StringBuffer sql = new StringBuffer();
     try
     {
       
       sql.append("SELECT DECODE(UPPER(D_NOMBRE), 'OTROS', D_OTROS_MOTIVOS, D_DESCRIPCION)D_DESCRIPCION ").append("FROM OCAP_MOT_EXCLUSION mex, OCAP_EXPEDIENTESEXCLUSION eex ").append(" WHERE mex.c_exclusion_id = eex.c_exclusion_id ").append(" AND eex.c_exp_id = ? ").append(" AND mex.B_BORRADO = 'N' AND eex.B_BORRADO = 'N'");
 
       st = con.prepareStatement(sql.toString());
       st.setLong(1, cExpId);
       rs = st.executeQuery();
       while (rs.next())
         if (!"".equals(motivos.toString()))
           motivos.append("\n").append(rs.getString("D_DESCRIPCION"));
         else
           motivos.append(rs.getString("D_DESCRIPCION"));
     }
     catch (SQLException ex)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return motivos.toString();
   }
 
   public ArrayList listarMotivosExclusion(String anio)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = null;
     OCAPMotExclusionOT motivoOT = null;
     ArrayList motivos = null;
     StringBuffer sql = new StringBuffer();
     try
     {
      sql.append("SELECT C_EXCLUSION_ID, D_NOMBRE, D_DESCRIPCION FROM OCAP_MOT_EXCLUSION WHERE B_BORRADO = 'N' ORDER BY C_EXCLUSION_ID ");
        con = JCYLGestionTransacciones.getConnection();
       st = con.prepareStatement(sql.toString());
       rs = st.executeQuery();
       motivos = new ArrayList();
       while (rs.next()) {
         motivoOT = new OCAPMotExclusionOT();
         motivoOT.setCExclusionId(rs.getLong("C_EXCLUSION_ID"));
         motivoOT.setDNombre(rs.getString("D_NOMBRE"));       
         motivoOT.setDDescripcion(rs.getString("D_DESCRIPCION").replace("[año]", anio!=null&&!anio.trim().equalsIgnoreCase("")?anio:""));
         motivos.add(motivoOT);
       }
     }
     catch (SQLException ex) {    	 
       throw ex;
     } finally {
      
       if (rs != null) rs.close();
       if (st != null) st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return motivos;
   }
   
   
   public ArrayList listarMotivosExclusionCompleto()
		     throws SQLException
		   {
		     PreparedStatement st = null;
		     ResultSet rs = null;
		     Connection con = null;
		     OCAPMotExclusionOT motivoOT = null;
		     ArrayList motivos = null;
		     StringBuffer sql = new StringBuffer();
		     try
		     {
		  		 
		       sql.append("SELECT C_EXCLUSION_ID, D_NOMBRE, D_DESCRIPCION FROM OCAP_MOT_EXCLUSION ORDER BY C_EXCLUSION_ID ");		 
		       con = JCYLGestionTransacciones.getConnection();
		       st = con.prepareStatement(sql.toString());
		       rs = st.executeQuery();
		       motivos = new ArrayList();
		       while (rs.next()) {
		         motivoOT = new OCAPMotExclusionOT();
		         motivoOT.setCExclusionId(rs.getLong("C_EXCLUSION_ID"));
		         motivoOT.setDNombre(rs.getString("D_NOMBRE"));
		         motivoOT.setDDescripcion(rs.getString("D_DESCRIPCION"));
		         motivos.add(motivoOT);
		       }
		     }
		     catch (SQLException ex) {
		       throw ex;
		     } finally {
		       OCAPConfigApp.logger.info(getClass().getName() + " listarMotivosExclusionCompleto: Fin");
		       if (rs != null) rs.close();
		       if (st != null) st.close();
		       JCYLGestionTransacciones.close(con.getAutoCommit());
		     }
		     return motivos;
		   }
   
   
   public String buscarCodigoMotivosExpediente(long cExpId)
		     throws SQLException
		   {
		     PreparedStatement st = null;
		     ResultSet rs = null;
		     Connection con = JCYLGestionTransacciones.getConnection();
		     StringBuffer motivos = new StringBuffer();
		 
		     StringBuffer sql = new StringBuffer();
		     try
		     {		       
		       sql.append("SELECT DECODE(UPPER(mex.D_NOMBRE), 'OTROS', D_OTROS_MOTIVOS, mex.D_DESCRIPCION) AS D_DESCRIPCION, UPPER(mex.D_NOMBRE) AS NOMBRE, mex.C_EXCLUSION_ID AS CODIGO ").append("FROM OCAP_MOT_EXCLUSION mex, OCAP_EXPEDIENTESEXCLUSION eex ").append(" WHERE mex.c_exclusion_id = eex.c_exclusion_id ").append(" AND eex.c_exp_id = ? ").append(" AND mex.B_BORRADO = 'N' AND eex.B_BORRADO = 'N'");		 
		       st = con.prepareStatement(sql.toString());
		       st.setLong(1, cExpId);
		       rs = st.executeQuery();
		       while (rs.next())
		         if (rs.getString("NOMBRE").equals("OTROS") )
		           motivos.append("\n").append("Otros: ").append( (rs.getString("D_DESCRIPCION")!=null)?rs.getString("D_DESCRIPCION"):" - ");
		         else
		           motivos.append("\n").append("Cod: ").append(  String.format("%02d", Integer.parseInt(rs.getString("CODIGO"))));
		       
		      
		       
		     }
		     catch (SQLException ex)
		     {
		       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
		       throw ex;
		     } finally {
		       
		       if (rs != null)
		         rs.close();
		       if (st != null)
		         st.close();
		       JCYLGestionTransacciones.close(con.getAutoCommit());
		     }
		 
		     return motivos.toString();
		   }
   
 }

