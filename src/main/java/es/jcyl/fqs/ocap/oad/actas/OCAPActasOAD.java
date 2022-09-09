 package es.jcyl.fqs.ocap.oad.actas;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.actas.OCAPActasOT;
 import es.jcyl.fqs.ocap.ot.actas.OCAPMiembrosComitesOT;
 import es.jcyl.fqs.ocap.ot.reports.OCAPAsistenteOT;
 import es.jcyl.fqs.ocap.ot.reports.OCAPUsuarioOT;
import es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.framework.JCYLUsuario;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.io.InputStream;
 import java.sql.CallableStatement;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPActasOAD
 {
   public static Logger logger = OCAPConfigApp.logger;
   public Logger loggerBD;
   private static OCAPActasOAD instance;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public static OCAPActasOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPActasOAD();
     }
     return instance;
   }
 
   private OCAPActasOAD() {
     $init$();
   }
 
   public long altaActa(OCAPActasOT datos)
     throws SQLException
   {
     Connection con = JCYLGestionTransacciones.getConnection();
     CallableStatement st = null;
     StringBuffer sql = new StringBuffer();
     int filas = 0;
     long idActa = 0L;
     try {
       sql.append("BEGIN INSERT INTO OCAP_ACTAS(c_acta_id, c_tipo, c_convocatoria_id, c_profesional_id, ");
       if (datos.getCGradoId() != 0L)
         sql.append(" c_grado_id,");
       if (datos.getCGerenciaId() != 0L)
         sql.append(" c_gerencia_id,");
       sql.append(" a_miembros_ausencia, a_asunto, a_ruegos, f_acta, h_inicio, h_fin, c_usualta, f_usualta, b_borrado) ").append(" VALUES (OCAP_ACS_ID_SEQ.nextval, ?, ?, ?, ");
       if (datos.getCGradoId() != 0L)
         sql.append(" ?, ");
       if (datos.getCGerenciaId() != 0L)
         sql.append(" ?, ");
       sql.append(" ?, ?, ?, TO_DATE(?,'DD/MM/RRRR'), TO_DATE(?,'DD/MM/RRRR hh24:mi'), TO_DATE(?,'DD/MM/RRRR hh24:mi'), ?, SYSDATE, 'N') ").append("RETURNING c_acta_id INTO ?; END;");
 
       int cont = 1;
       st = con.prepareCall(sql.toString());
       st.setString(cont++, datos.getCTipoActa());
       st.setLong(cont++, datos.getCConvocatoriaId());
       st.setLong(cont++, datos.getCProfesionalId());
       if (datos.getCGradoId() != 0L)
         st.setLong(cont++, datos.getCGradoId());
       if (datos.getCGerenciaId() != 0L)
         st.setLong(cont++, datos.getCGerenciaId());
       st.setString(cont++, datos.getDMiembrosAusentes());
       st.setString(cont++, datos.getDAsuntosTramite());
       st.setString(cont++, datos.getDRuegosPreguntas());
       st.setString(cont++, DateUtil.convertDateToString(datos.getFSesion()));
       st.setString(cont++, DateUtil.convertDateToString(datos.getFSesion()) + " " + datos.getNHoraInicio() + ":" + datos.getNMinutosInicio());
       st.setString(cont++, DateUtil.convertDateToString(datos.getFSesion()) + " " + datos.getNHoraFin() + ":" + datos.getNMinutosFin());
       st.setString(cont++, datos.getACreadoPor());
       st.registerOutParameter(cont, 4);
 
       filas = st.executeUpdate();
       idActa = st.getLong(cont);
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return idActa;
   }
 
   public int altaMiembrosActa(OCAPActasOT datos)
     throws SQLException
   {
     Connection con = JCYLGestionTransacciones.getConnection();
     CallableStatement st = null;
     StringBuffer sql = new StringBuffer();
     int filas = 0;
     OCAPAsistenteOT miembroOT = null;
     try {
       sql.append("INSERT INTO OCAP_MIEMBROS_ACTA(c_miembro_acta_id, c_acta_id, c_miembro_id, c_encalidad, c_usualta, f_usualta, b_borrado) ").append(" VALUES (OCAP_MAC_ID_SEQ.nextval, ?, ?, ?, ?, SYSDATE, 'N') ");
 
       st = con.prepareCall(sql.toString());
 
       int i = 0;
       do { st.setLong(1, datos.getCActaId());
         miembroOT = (OCAPAsistenteOT)datos.getListadoMiembros().get(i);
         st.setLong(2, miembroOT.getCMiembroId());
         st.setString(3, miembroOT.getBEnCalidad());
         st.setString(4, datos.getACreadoPor());
 
         filas += st.executeUpdate();
 
         i++; if (datos.getListadoMiembros() == null) break;  } while (i < datos.getListadoMiembros().size());
     }
     catch (SQLException ex)
     {
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return filas;
   }
 
   public int altaExpedientesActa(OCAPActasOT datos)
     throws SQLException
   {
     Connection con = JCYLGestionTransacciones.getConnection();
     CallableStatement st = null;
     StringBuffer sql = new StringBuffer();
     OCAPUsuarioOT usuarioOT = null;
     int filas = 0;
     OCAPAsistenteOT miembroOT = null;
     try {
       sql.append("INSERT INTO OCAP_EXP_ACTA(c_exp_acta_id, c_acta_id, c_exp_id, c_usualta, f_usualta, b_borrado, c_tipo_informe) ").append(" VALUES (OCAP_EAC_ID_SEQ.nextval, ?, ?, ?, SYSDATE, 'N', ?) ");
 
       st = con.prepareCall(sql.toString());
 
       int i = 0;
       do { st.setLong(1, datos.getCActaId());
         usuarioOT = (OCAPUsuarioOT)datos.getListadoUsuarios().get(i);
         st.setLong(2, usuarioOT.getCExpId());
         st.setString(3, datos.getACreadoPor());
         st.setString(4, usuarioOT.getDAccion());
 
         filas += st.executeUpdate();
 
         i++; if (datos.getListadoUsuarios() == null) break;  } while (i < datos.getListadoUsuarios().size());
     }
     catch (SQLException ex)
     {
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return filas;
   }
 
   public OCAPActasOT buscarDatosActa(long cActaId)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     OCAPActasOT datos = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     try {
       StringBuffer sql = new StringBuffer();
 
       sql.append("SELECT c_acta_id, c_tipo, c_convocatoria_id, a_asunto, a_ruegos, a_miembros_ausencia, c_grado_id, c_gerencia_id, c_profesional_id, ").append(" TO_CHAR(f_acta,'DD/MM/RRRR') as f_acta, TO_CHAR(h_inicio,'hh24:mi') as h_inicio, TO_CHAR(h_fin,'hh24:mi') as h_fin ").append(" FROM ocap_actas ").append(" WHERE c_acta_id = ? ").append(" AND b_borrado='N' ");
 
       st = con.prepareStatement(sql.toString());
       st.setLong(1, cActaId);
 
       rs = st.executeQuery();
 
       if (rs.next()) {
         datos = new OCAPActasOT();
         datos.setCActaId(rs.getLong("c_acta_id"));
         datos.setCTipoActa(rs.getString("c_tipo"));
         datos.setCConvocatoriaId(rs.getLong("c_convocatoria_id"));
         datos.setCGradoId(rs.getLong("c_grado_id"));
         datos.setCGerenciaId(rs.getLong("c_gerencia_id"));
         datos.setCProfesionalId(rs.getLong("c_profesional_id"));
         datos.setDRuegosPreguntas(rs.getString("a_ruegos"));
         datos.setDMiembrosAusentes(rs.getString("a_miembros_ausencia"));
         if (rs.getClob("a_asunto") != null) {
           InputStream inputStreamDDuda = rs.getClob("a_asunto").getAsciiStream();
           StringBuffer dAsunto = new StringBuffer();
           int caracter;
           while ((caracter = inputStreamDDuda.read()) != -1) {
             dAsunto.append((char)caracter);
           }
           datos.setDAsuntosTramite(dAsunto.toString());
         }
         datos.setFSesion(DateUtil.convertStringToDate(Constantes.FECHA_CORTA_DATEUTIL, rs.getString("f_acta")));
         datos.setNHoraInicio(rs.getString("h_inicio"));
         datos.setNHoraFin(rs.getString("h_fin"));
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
 
     return datos;
   }
 
   public ArrayList buscarMiembrosActa(long cActaId)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     OCAPMiembrosComitesOT datos = null;
     ArrayList listaMiembros = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     try {
       StringBuffer sql = new StringBuffer();
 
       sql.append("SELECT miac.c_miembro_id, miac.c_encalidad, miem.d_nombre, miem.d_apellidos, miem.c_sexo, miem.c_tipo ").append(" FROM ocap_miembros_acta miac, ocap_miembros_comites miem ").append(" WHERE miac.c_acta_id = ? ").append(" AND miac.c_miembro_id = miem.c_miembro_id ").append(" AND miac.b_borrado='N' AND miem.b_borrado='N' ").append(" ORDER BY miem.c_tipo, miac.c_encalidad DESC, miem.d_apellidos, miem.d_nombre ");
 
       st = con.prepareStatement(sql.toString());
       st.setLong(1, cActaId);
 
       rs = st.executeQuery();
 
       listaMiembros = new ArrayList();
       while (rs.next()) {
         datos = new OCAPMiembrosComitesOT();
         datos.setCMiembroId(rs.getLong("c_miembro_id"));
         datos.setCEnCalidad(rs.getString("c_encalidad"));
         datos.setDNombre(rs.getString("d_nombre"));
         datos.setDApellidos(rs.getString("d_apellidos"));
         datos.setCSexo(rs.getString("c_sexo"));
         datos.setCTipo(rs.getString("c_tipo"));
 
         listaMiembros.add(datos);
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
 
     return listaMiembros;
   }
 
   public ArrayList buscarExpedientesActa(long cActaId)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     ArrayList listaExpedientes = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     OCAPUsuarioOT usuarioOT = null;
     try {
       StringBuffer sql = new StringBuffer();
 
       sql.append("SELECT c_exp_id,  c_tipo_informe ").append(" FROM ocap_exp_acta ").append(" WHERE c_acta_id = ? ").append(" AND b_borrado='N' ");
 
       st = con.prepareStatement(sql.toString());
       st.setLong(1, cActaId);
 
       rs = st.executeQuery();
 
       listaExpedientes = new ArrayList();
       while (rs.next()) {
         usuarioOT = new OCAPUsuarioOT();
         usuarioOT.setCExpId(rs.getLong("c_exp_id"));
         usuarioOT.setDAccion(rs.getString("c_tipo_informe"));
         listaExpedientes.add(usuarioOT);
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
 
     return listaExpedientes;
   }
   
   
   public ArrayList buscarActas(int inicio, int cuantos, OCAPActasOT actaOT)
		     throws SQLException, Exception
		   {
		     PreparedStatement st = null;
		     ResultSet rs = null;
		     OCAPActasOT datos = null;
		     ArrayList listaActas = null;
		     Connection con = JCYLGestionTransacciones.getConnection();
		     try {
		       StringBuffer sql = new StringBuffer();
		 
		       
		       	sql.append(" select distinct * from ( ")
		       	.append(" SELECT a.c_acta_id, ")
		    	.append(" a.c_tipo, ")
		    	.append(" decode(a.c_tipo, 'A', 'Aclaraciones', 'C', 'Constitución', 'R', 'Reunión', '') d_tipo, ")
		    	.append(" a.c_convocatoria_id, ")
		    	.append(" c.D_NOMBRE as d_convocatoria, ")
		    	.append(" to_char(a.f_acta, 'dd/mm/yyyy') as f_acta, ")
		    	.append(" to_char(a.f_usualta, 'dd/mm/yyyy hh24:mi:ss') as f_generacion, ")
		    	.append(" a.c_profesional_id, ")
		    	.append(" gc.D_NOMBRE as categoria, ")
		    	.append(" a.c_grado_id, ")
		    	.append(" g.D_DESCRIPCION as dgrado ")
		    	.append(" FROM ocap_actas a, ocap_convocatorias c, ocap_categ_profesionales gc, ")
		    	.append(" ocap_grados g ")
		    	.append(" where a.c_convocatoria_id = c.c_convocatoria_id ")
		    	.append(" and a.c_profesional_id = gc.C_PROFESIONAL_ID ")
		    	.append(" AND (a.c_grado_id = g.c_grado_id or (a.c_grado_id is null and g.c_grado_id = 0))	 ")
		       	.append(" and a.c_tipo = ? ")
		       	.append(" and a.c_profesional_id  = ? ")
		       	.append(" and a.c_convocatoria_id = ? ");
		       	
		       	if (actaOT.getCGradoId()>0){
		       		sql.append(" and a.c_grado_id = ? ");
		       	}
		       	
		       	if (actaOT.getCGerenciaId()>0){
		       		sql.append(" and a.c_gerencia_id = ? ");
		       	}			       	
		       	
		       	sql.append(" )order by f_acta desc ");
		       	int parametroNumero = 1;
		       st = con.prepareStatement(sql.toString(),1004,1008);
		       st.setString(parametroNumero++, actaOT.getCTipoActa());
		       st.setLong(parametroNumero++, actaOT.getCProfesionalId());
		       st.setLong(parametroNumero++, actaOT.getCConvocatoriaId());
		   	   if (actaOT.getCGradoId()>0){
		   		st.setLong(parametroNumero++, actaOT.getCGradoId());	
		   	   }
		   	   
		   	   if (actaOT.getCGerenciaId()>0){
		   		st.setLong(parametroNumero++, actaOT.getCGerenciaId());	
		   	   }		   	   
		 
		       rs = st.executeQuery();
		       
		       
				if (inicio > 1) {
					rs.absolute(inicio - 1);
				}
				listaActas = new ArrayList();
				int i = 0;
				while (rs.next()) {
					datos = new OCAPActasOT();
			        datos.setCActaId(rs.getLong("C_ACTA_ID"));
			        datos.setCTipoActa(rs.getString("c_tipo"));
			        datos.setDTipoActa(rs.getString("d_tipo"));
			        datos.setCConvocatoriaId(rs.getLong("c_convocatoria_id"));
			        datos.setDConvocatoria(rs.getString("d_convocatoria"));
			        datos.setFSesionImprimible(rs.getString("f_acta"));
			        datos.setFGeneracion(rs.getString("f_generacion"));
			        datos.setCProfesionalId(rs.getLong("c_profesional_id"));
			        datos.setDProfesional(rs.getString("categoria"));
			        datos.setCGradoId(rs.getLong("c_grado_id"));
			        if (datos.getCGradoId()>0){
			        	datos.setDGrado(rs.getString("dgrado"));
			        } else {
			        	datos.setDGrado("No especificado");
			        }
			        listaActas.add(datos);

					if (++i == cuantos)
						break;
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
		 
		     return listaActas;
		   }   
   
   public int contarActas(OCAPActasOT actaOT)
		     throws SQLException, Exception
		   {
		     PreparedStatement st = null;
		     ResultSet rs = null;
		     OCAPActasOT datos = null;
		     int numActas = 0;
		     Connection con = JCYLGestionTransacciones.getConnection();
		     try {
		       StringBuffer sql = new StringBuffer();
		 
		       
		       	sql.append(" SELECT count(1) as numero ")
		    	.append(" FROM ocap_actas a, ocap_convocatorias c, ocap_categ_profesionales gc, ")
		    	.append(" ocap_grados g ")
		    	.append(" where a.c_convocatoria_id = c.c_convocatoria_id ")
		    	.append(" and a.c_profesional_id = gc.C_PROFESIONAL_ID ")
		    	.append(" AND (a.c_grado_id = g.c_grado_id or (a.c_grado_id is null and g.c_grado_id = 0))	 ")
		       	.append(" and a.c_tipo = ? ")
		       	.append(" and a.c_profesional_id  = ? ")
		       	.append(" and a.c_convocatoria_id = ? ");
		       	
		       	if (actaOT.getCGradoId()>0){
		       		sql.append(" and a.c_grado_id = ? ");
		       	}
		       	
		       	if (actaOT.getCGerenciaId()>0){
		       		sql.append(" and a.c_gerencia_id = ? ");
		       	}		       	
		       	
		       	
		 
		       st = con.prepareStatement(sql.toString());
		       int parametroNumero = 1;
		       st.setString(parametroNumero++, actaOT.getCTipoActa());
		       st.setLong(parametroNumero++, actaOT.getCProfesionalId());
		       st.setLong(parametroNumero++, actaOT.getCConvocatoriaId());
		   	   if (actaOT.getCGradoId()>0){
		   		st.setLong(parametroNumero++, actaOT.getCGradoId());	
		   	   }
		   	   if (actaOT.getCGerenciaId()>0){
		   		st.setLong(parametroNumero++, actaOT.getCGerenciaId());	
		   	   }		   	   
		 
		       rs = st.executeQuery();
		 
				if (rs.next()) {
					numActas = rs.getInt("numero");
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
		 
		     return numActas;
		   }      
   
 }

