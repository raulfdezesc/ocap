 package es.jcyl.fqs.ocap.oad.expedientes;
 
 import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.expedientes.OCAPExpedientesOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 
 public class OCAPExpedientesOAD
 {
   public Logger loggerBD;
   private static OCAPExpedientesOAD instance;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   private OCAPExpedientesOAD()
   {
     $init$();
   }
 
   public static OCAPExpedientesOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPExpedientesOAD();
     }
     return instance;
   }
 
   private PreparedStatement crearSentencia(String tipoSentencia, OCAPExpedientesOT datosBusqueda, Connection conexion)
     throws SQLException
   {
     PreparedStatement sentencia = null;
     try
     {
       StringBuffer sql = new StringBuffer();
       StringBuffer sqlSelect = new StringBuffer();
       StringBuffer sqlFrom = new StringBuffer();
       StringBuffer sqlWhere = new StringBuffer();
       StringBuffer sqlOrderBy = new StringBuffer();
 
       if ((tipoSentencia != null) && (tipoSentencia.equals("listarExpedientes"))) {
         sqlSelect.append(" SELECT ecar.c_exp_id, c.d_nombre_corto as nombre_convocatoria, ecar.c_usr_id, ecar.c_estado_id, ")
         	.append(" solic.d_nombre, solic.d_apellidos, ").append(" solic.c_dni, solic.c_dni as C_DNI_REAL, estd.d_nombre nombre_estado ");
       }
       else if ((tipoSentencia != null) && (tipoSentencia.equals("obtenerTotalExpedientes"))) {
         sqlSelect.append(" SELECT COUNT(DISTINCT(ecar.c_exp_id)) ");
       }
 
       sqlFrom.append(" FROM ocap_solicitudes solic, ocap_expedientescarrera ecar, ocap_convocatorias c, ").append(" ocap_usuarios usua, ocap_estados estd ");

       sqlWhere.append(" ecar.c_convocatoria_id = c.c_convocatoria_id  ")
		.append(" AND solic.c_usr_id = usua.c_usr_id ")
		.append(" AND solic.c_convocatoria_id = ecar.c_convocatoria_id AND ");
       
       boolean isDNombre = (datosBusqueda != null) && (datosBusqueda.getDNombre() != null) && (!datosBusqueda.getDNombre().trim().equals(""));
       if (isDNombre) {
         sqlWhere.append(" TRANSLATE(UPPER(solic.d_nombre), 'ÁÉÍÓÚÜ','AEIOUU') LIKE TRANSLATE('%' || ? ||'%', 'ÁÉÍÓÚÜ', 'AEIOUU') AND ");
       }
       boolean isDApellidos = (datosBusqueda != null) && (datosBusqueda.getDApellidos() != null) && (!datosBusqueda.getDApellidos().trim().equals(""));
       if (isDApellidos) {
         sqlWhere.append(" TRANSLATE(UPPER(solic.d_apellidos), 'ÁÉÍÓÚÜ', 'AEIOUU') LIKE TRANSLATE('%' || ? ||'%', 'ÁÉÍÓÚÜ', 'AEIOUU') AND ");
       }
       boolean isCDNIReal = (datosBusqueda != null) && (datosBusqueda.getCDNIReal() != null) && (!datosBusqueda.getCDNIReal().trim().equals(""));
       if (isCDNIReal) {
         sqlWhere.append(" UPPER(solic.c_dni) = ? AND ");
       }
       boolean isCConvocatoriaId = (datosBusqueda != null) && (datosBusqueda.getCConvocatoriaId() != 0L);
       if (isCConvocatoriaId) {
         sqlWhere.append(" ecar.c_convocatoria_id = ? AND ");
       }
       boolean isCEstadoId = (datosBusqueda != null) && (datosBusqueda.getCEstadoId() != 0L);
       if ((isCEstadoId) && (!"infMotivado".equals(datosBusqueda.getCModo()))) {
         sqlWhere.append(" ecar.c_estado_id = ? AND ");
       }
 
       if ((isCEstadoId) && ("infMotivado".equals(datosBusqueda.getCModo())))
       {
         if (14 == datosBusqueda.getCEstadoId()) {
           sqlWhere.append(" ecar.f_negacion_mc is not null AND ");
         }
         if (10 == datosBusqueda.getCEstadoId()) {
           sqlWhere.append(" ((ecar.f_negacion_mc is not null AND ecar.C_PROC_EVALUACION_ID in (").append("4").append(", ").append("5").append(")) OR (ecar.f_respuestainconf_mc is not null AND ecar.f_inconf_mc is not null)) AND ");
         }
         if (9 == datosBusqueda.getCEstadoId()) {
           sqlWhere.append(" ecar.f_aceptacioncc is not null AND ecar.f_inconf_mc is not null AND ");
         }
       }
       if ("faseIII".equals(datosBusqueda.getCModo())) {
         sqlWhere.append(" ecar.c_itinerario_id is not null AND ");
       }
       if ((datosBusqueda.getCEstadoId() == 14) && ("aceptacion".equals(datosBusqueda.getCModo()))) {
         sqlWhere.append(" ecar.f_inconf_mc IS NULL AND ");
       }
 
       boolean isCGerenciaId = (datosBusqueda != null) && (datosBusqueda.getCGerenciaId() != 0L);
       if (isCGerenciaId) {
         sqlWhere.append(" solic.c_gerencia_actual_id = ? AND ");
       }
       boolean isCGradoId = (datosBusqueda != null) && (datosBusqueda.getCGradoId() != 0L);
       if (isCGradoId) {
         sqlWhere.append(" ecar.c_grado_id = ? AND ");
       }
       boolean isCCategProfesionalId = (datosBusqueda != null) && (datosBusqueda.getCCategProfesionalId() != 0L);
       if (isCCategProfesionalId) {
         sqlWhere.append(" ecar.c_profesional_id = ? AND ");
       }
       boolean isCExpedienteId = (datosBusqueda != null) && (datosBusqueda.getCExpedienteId() != 0L);
       if (isCExpedienteId) {
         sqlWhere.append(" ecar.c_exp_id = ? AND ");
       }
 
       if ((datosBusqueda != null) && (Constantes.SI.equals(datosBusqueda.getEsCEIS()))) {
         sqlWhere.append(" ecar.C_PROC_EVALUACION_ID not in (").append("4").append(", ").append("5").append(") AND ");
       }
 
       if ("CV".equals(datosBusqueda.getCModo())) {
         sqlWhere.append(" sysdate > ecar.f_fin_mc AND ");
       }
       sqlWhere.append(" ecar.c_usr_id = usua.c_usr_id AND ").append(" estd.c_estado_id = ecar.c_estado_id AND ").append(" ecar.b_borrado = 'N' AND ").append(" usua.b_borrado = 'N' AND ");
       if("CV".equals(datosBusqueda.getCModo()) && (Constantes.SI.equals(datosBusqueda.getEsGRS()))) {
    	   sqlWhere.append(" ecar.c_estado_id not in (1, 2, 4, 6, 7, 8) AND ");
       }else {
	       if (!"etiqueta".equals(datosBusqueda.getCModo())) {
	         sqlWhere.append(" ecar.c_estado_id not in (1, 2, 4, 5, 6, 7, 8, 15) AND ");
	       }
       }
       if (sqlWhere.length() > 0) {
         sqlWhere.insert(0, " WHERE ").delete(sqlWhere.length() - 4, sqlWhere.length());
       }
 
       if ((tipoSentencia != null) && (!tipoSentencia.equals("obtenerTotalExpedientes"))) {
         if ((datosBusqueda != null) && (datosBusqueda.getCampoOrdenacion() != null) && (datosBusqueda.getTipoOrdenacion() != null))
         {
           if (datosBusqueda.getCampoOrdenacion().trim().toUpperCase().equals("NIF")) {
             sqlOrderBy.append(" ORDER BY  solic.c_dni ").append(datosBusqueda.getTipoOrdenacion().trim().toUpperCase()).append(", ").append(" NLSSORT(solic.d_apellidos, 'NLS_SORT = Spanish') ASC, ").append(" NLSSORT(solic.d_nombre, 'NLS_SORT = Spanish') ASC ");
           }
           else if (datosBusqueda.getCampoOrdenacion().trim().toUpperCase().equals("NOMBRE")) {
             sqlOrderBy.append(" ORDER BY  NLSSORT(solic.d_nombre, 'NLS_SORT = Spanish') ").append(datosBusqueda.getTipoOrdenacion().trim().toUpperCase()).append(", ").append(" NLSSORT(solic.d_apellidos, 'NLS_SORT = Spanish') ASC ");
           }
           else if (datosBusqueda.getCampoOrdenacion().trim().toUpperCase().equals("APELLIDOS")) {
             sqlOrderBy.append(" ORDER BY  NLSSORT(solic.d_apellidos, 'NLS_SORT = Spanish') ").append(datosBusqueda.getTipoOrdenacion().trim().toUpperCase()).append(", ").append(" NLSSORT(solic.d_nombre, 'NLS_SORT = Spanish') ASC ");
           }
         }
         else
         {
           sqlOrderBy.append(" ORDER BY  NLSSORT(solic.d_apellidos, 'NLS_SORT = Spanish') ASC, ").append(" NLSSORT(solic.d_nombre, 'NLS_SORT = Spanish') ASC ");
         }
 
       }
 
       sql.append(sqlSelect).append(sqlFrom).append(sqlWhere).append(sqlOrderBy);
 
       this.loggerBD.info("Sentencia SQL:" + sql.toString());
 
       sentencia = conexion.prepareStatement(sql.toString(), 1004, 1007);
 
       int contador = 1;
       if (isDNombre) {
         sentencia.setString(contador++, datosBusqueda.getDNombre().toUpperCase());
       }
       if (isDApellidos) {
         sentencia.setString(contador++, datosBusqueda.getDApellidos().toUpperCase());
       }
       if (isCDNIReal) {
         sentencia.setString(contador++, datosBusqueda.getCDNIReal().toUpperCase());
       }
       if (isCConvocatoriaId) {
         sentencia.setLong(contador++, datosBusqueda.getCConvocatoriaId());
       }
       if ((isCEstadoId) && (!"infMotivado".equals(datosBusqueda.getCModo()))) {
         sentencia.setLong(contador++, datosBusqueda.getCEstadoId());
       }
 
       if (isCGerenciaId) {
         sentencia.setLong(contador++, datosBusqueda.getCGerenciaId());
       }
       if (isCGradoId) {
         sentencia.setLong(contador++, datosBusqueda.getCGradoId());
       }
       if (isCCategProfesionalId) {
         sentencia.setLong(contador++, datosBusqueda.getCCategProfesionalId());
       }
       if (isCExpedienteId)
         sentencia.setLong(contador++, datosBusqueda.getCExpedienteId());
     }
     catch (SQLException ex) {
       throw ex;
     }
 
     return sentencia;
   }
 
   public ArrayList listarExpedientes(OCAPExpedientesOT datosBusqueda)
     throws SQLException, Exception
   {
     PreparedStatement sentencia = null;
     ResultSet resultado = null;
     Connection conexion = JCYLGestionTransacciones.getConnection();
     ArrayList listado = null;
     try
     {
       sentencia = crearSentencia("listarExpedientes", datosBusqueda, conexion);
       resultado = sentencia.executeQuery();
 
       if ((datosBusqueda != null) && (datosBusqueda.getPrimerRegistro() > 1)) {
         resultado.absolute(datosBusqueda.getPrimerRegistro() - 1);
       }
       listado = new ArrayList();
       int i = 0;
       while (resultado.next()) {
         OCAPExpedientesOT datos = new OCAPExpedientesOT();
         datos.setCExpedienteId(resultado.getLong("c_exp_id"));
         datos.setCUsuarioId(resultado.getLong("c_usr_id"));
         datos.setCEstadoId(resultado.getLong("c_estado_id"));
         datos.setDNombre(resultado.getString("d_nombre"));
         datos.setDApellidos(resultado.getString("d_apellidos"));
         datos.setCDNI(resultado.getString("c_dni"));
         datos.setCDNIReal(resultado.getString("c_dni_real"));
         datos.setDEstado(resultado.getString("nombre_estado"));
         datos.setDConvocatoria(resultado.getString("nombre_convocatoria"));
         listado.add(datos);
         if (datosBusqueda != null) { i++; if (i == datosBusqueda.getRegistrosPorPagina())
             break; }
       }
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       if (resultado != null)
         resultado.close();
       if (sentencia != null)
         sentencia.close();
       JCYLGestionTransacciones.close(conexion.getAutoCommit());
     }
 
     return listado;
   }
 
   public int obtenerTotalExpedientes(OCAPExpedientesOT datosBusqueda)
     throws SQLException, Exception
   {
     PreparedStatement sentencia = null;
     ResultSet resultado = null;
     Connection conexion = JCYLGestionTransacciones.getConnection();
 
     int total = 0;
     try
     {
       sentencia = crearSentencia("obtenerTotalExpedientes", datosBusqueda, conexion);
 
       resultado = sentencia.executeQuery();
 
       if (resultado.next())
         total = resultado.getInt(1);
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       if (resultado != null)
         resultado.close();
       if (sentencia != null)
         sentencia.close();
       JCYLGestionTransacciones.close(conexion.getAutoCommit());
     }
 
     return total;
   }
 
	public ArrayList listarPosiblesEvaluados(OCAPExpedientesOT datosBusqueda) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;

		long cte = 0L;
		try {
			String where = "";
			String sqlStatement = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "";

			String selectFromEval = "SELECT EXP.C_EXP_ID, solic. C_DNI , solic.D_APELLIDOS, solic.D_NOMBRE FROM ocap_solicitudes solic, ocap_usuarios u,  ocap_expedientescarrera EXP ";

			where = "WHERE EXP.b_borrado = 'N' ";
			where = where + " and EXP.c_compfqs_id is null ";
			where = where + " AND EXP.c_convocatoria_id = " + datosBusqueda.getCConvocatoriaId();
			where = where + " AND EXP.c_itinerario_id = " + datosBusqueda.getCItinerarioId();
			where = where + " AND EXP.c_usr_id = u.c_usr_id ";
			where = where + " AND u.b_borrado = 'N'";
			// where = where + " AND solic.c_gerencia_actual_id = " +
			// datosBusqueda.getCGerenciaId();
			where += "AND solic.c_usr_id = u.c_usr_id AND solic.c_convocatoria_id = exp.c_convocatoria_id";

			String order = " ORDER BY  solic.c_dni";

			sqlStatement = selectFromEval + where + order;

			st = con.prepareStatement(sqlStatement, 1004, 1008);

			rs = st.executeQuery();

			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPUsuariosOT result = new OCAPUsuariosOT();

				result.setCExpId(new Long(rs.getLong("C_EXP_ID")));
				result.setCDni(rs.getString("C_DNI"));
				result.setDApellido1(rs.getString("D_APELLIDOS"));
				result.setDNombre(rs.getString("D_NOMBRE"));
				listado.add(result);
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

		return listado;
	}

	public Integer ejecutarProcMc(Integer convocatoriaId, String usuario, Connection con) {
		
		return ejecutarProcMeritos(convocatoriaId, usuario, con, "{call PROC_MERITOS_MC(?,?,?)}");

	}

	public Integer ejecutarProcMa(Integer convocatoriaId, String usuario, Connection con) {

		return ejecutarProcMeritos(convocatoriaId, usuario, con, "{call PROC_MERITOS_MA(?,?,?)}");
		
	}
	
	public Integer ejecutarProcMeritos(Integer convocatoriaId, String usuario, Connection con, String llamada) {
		CallableStatement cs = null;
		try {
			cs = con.prepareCall(llamada);
			cs.setFloat(1, convocatoriaId);
			cs.setString(2, usuario);
			cs.registerOutParameter(3, oracle.jdbc.OracleTypes.NUMBER);
			cs.executeUpdate();

			Integer val = cs.getInt(3);
			cs.close();
			return val;
		} catch (Exception e) {
			loggerBD.error(e);
			return -1;
		}
	}
 }

