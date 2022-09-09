 package es.jcyl.fqs.ocap.oad.solicitudes;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.CallableStatement;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.text.DecimalFormat;
 import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
 
 public class OCAPNuevaSolicitudOAD
 {
   public static Logger logger = OCAPConfigApp.logger;
   protected Connection conTransaccion;
   private static OCAPNuevaSolicitudOAD instance;
 
   public static OCAPNuevaSolicitudOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPNuevaSolicitudOAD();
     }
     return instance;
   }
 
   public int altaOCAPSolicitudes(OCAPSolicitudesOT datos, Connection con)
     throws Exception
   {
     CallableStatement st = null;
     int filas = 0;
     int idSolicitud = 0;
     try
     {
       String sql = " BEGIN INSERT INTO OCAP_SOLICITUDES  (A_CORREOELECTRONICO, A_DOMICILIO, A_OTRASITADMINISTRATIVA,  A_OTROJURIDICO, A_PUESTO, A_SERVICIO,  B_BORRADO, B_LOPD_SOLICITUD, B_SEXO,  C_CATEGORIA_ID, C_CENTROTRABAJO_ID, C_CONVOCATORIA_ID,  C_DNI, C_ESPECIALIDAD_ID, C_ESTADO_ID,  C_GERENCIA_ID, C_GRADO_ID, C_JURIDICO_ID,  C_MOD_ANTERIOR_ID, C_PROC_EVALUACION_ID, C_PROVINCIA_CARRERA_ID,  C_PROVINCIA_ID, C_SIT_ADMINISTRATIVA_ID, C_SOLICITUD_ID,  C_USR_ID, C_USUALTA, C_USUMODI,  D_APELLIDOS, D_LOCALIDAD, D_NOMBRE,  F_REGISTRO_OFICIAL, F_REGISTRO_SOLIC, F_USUALTA,  F_USUMODI, N_ANIOSANTIGUEDAD, N_CODIGOPOSTAL,  N_DIAS_ANTIGUEDAD, N_MESES_ANTIGUEDAD, N_TELEFONO1, N_TELEFONO2,  C_SIT_ADMON_ACTUAL_ID, A_OTRASITADMON_ACTUAL, C_PROV_CARRERA_ACTUAL_ID, C_GERENCIA_ACTUAL_ID, C_CENTROTRAB_ACTUAL_ID ";
 
       sql = sql + ") VALUES (?, ?, ?, " + "?, ?, ?, " + "?, ?, ?, " + "?, ?, ?, " + "?, ?, ?, " + "?, ?, ?, " + "?, ?, ?, " + "?, ?, OCAP_SOL_ID_SEQ.NEXTVAL, " + "?, ?, ?, " + "?, ?, ?, " + "?, SYSDATE, SYSDATE, " + "?, ?, ?, " + "?, ?, ?, ?, " + "?, ?, ?, ?, ? ";
 
       sql = sql + ")  RETURNING C_SOLICITUD_ID INTO ?; END;";
 
       st = con.prepareCall(sql);
       int cont = 1;
       st.setString(cont++, datos.getDCorreoelectronico());
       st.setString(cont++, datos.getDDomicilio());
       st.setString(cont++, datos.getDSitAdministrativaOtros());
 
       st.setString(cont++, datos.getDRegimenJuridicoOtros());
       st.setString(cont++, datos.getAPuesto());
       st.setString(cont++, datos.getAServicio());
 
       st.setString(cont++, Constantes.NO);
       st.setString(cont++, datos.getBLopdSolicitud());
       st.setString(cont++, datos.getBSexo());
 
       st.setLong(cont++, datos.getCProfesional_id());
       st.setLong(cont++, datos.getCCentrotrabajo_id());
       st.setLong(cont++, datos.getCConvocatoriaId());
 
       st.setString(cont++, datos.getCDni().toUpperCase());
       st.setLong(cont++, datos.getCEspec_id());
       st.setString(cont++, datos.getCEstado());
 
       st.setLong(cont++, datos.getCGerencia_id());
       st.setLong(cont++, datos.getCGrado_id());
       st.setString(cont++, datos.getCJuridicoId());
 
       st.setLong(cont++, datos.getCModAnterior_id());
       st.setString(cont++, datos.getCProcedimientoId());
       st.setString(cont++, datos.getCProvinciaCarrera_id());
 
       st.setString(cont++, datos.getCProvincia_id());
       st.setString(cont++, datos.getCSitAdministrativaAuxId());
 
       st.setString(cont++, null);
       st.setString(cont++, datos.getCUsuAlta());
       st.setString(cont++, null);
 
       st.setString(cont++, datos.getDApellido1());
       st.setString(cont++, datos.getDLocalidad().toUpperCase());
       st.setString(cont++, datos.getDNombre());
 
       st.setString(cont++, null);
 
       st.setString(cont++, null);
       st.setLong(cont++, datos.getNAniosantiguedad());
       st.setString(cont++, datos.getCPostalUsu());
 
       st.setLong(cont++, datos.getNDiasantiguedad());
       st.setLong(cont++, datos.getNMesesantiguedad());
       if ((datos.getNTelefono1() != null) && (!datos.getNTelefono1().equals("")))
         st.setLong(cont++, Long.parseLong(datos.getNTelefono1()));
       else {
         st.setNull(cont++, 2);
       }
       if ((datos.getNTelefono2() != null) && (!datos.getNTelefono2().equals("")))
         st.setLong(cont++, Long.parseLong(datos.getNTelefono2()));
       else {
         st.setNull(cont++, 2);
       }
 
       st.setLong(cont++, datos.getCSitAdmonActualId());
       st.setString(cont++, datos.getAOtraSitAdmonActual());
       st.setString(cont++, datos.getCProvCarreraActualId());
       st.setLong(cont++, datos.getCGerenciaActualId());
       st.setLong(cont++, datos.getCCentroTrabActualId());
 
       st.registerOutParameter(cont++, 4);
 
       filas = st.executeUpdate();
       idSolicitud = st.getInt(--cont);
     }
     catch (Exception ex) {
       throw ex;
     } finally {
       if (st != null) {
         st.close();
       }
     }
 
     return idSolicitud;
   }

   	/**
   	 * actualizar
   	 * @param datos
   	 * @param con
   	 * @throws Exception
   	 */
	public void actualizar(OCAPSolicitudesOT datos, Connection con) throws Exception {
		CallableStatement st = null;
		int filas = 0;
		int idSolicitud = 0;
		StringBuffer consulta = new StringBuffer();
		try {
			consulta.append(" UPDATE OCAP_SOLICITUDES SET ")
				.append(" A_CORREOELECTRONICO = ? ,")
				.append(" A_DOMICILIO = ? ,")
				.append(" A_OTRASITADMINISTRATIVA = ? ,")
				.append(" A_OTROJURIDICO = ? ,")
				.append(" A_PUESTO = ? ,")
				.append(" A_SERVICIO = ? ,")
				.append(" B_BORRADO = ? ,")
				.append(" B_LOPD_SOLICITUD = ? ,")
				.append(" B_SEXO = ? ,")
				.append(" C_CATEGORIA_ID = ? ,")
				.append(" C_CENTROTRABAJO_ID = ? ,")
				.append(" C_CONVOCATORIA_ID = ? ,")
				.append(" C_DNI = ? ,")
				.append(" C_ESPECIALIDAD_ID = ? ,")
				.append(" C_ESTADO_ID = ? ,")
				.append(" C_GERENCIA_ID = ? ,")
				.append(" C_GRADO_ID = ? ,")
				.append(" C_JURIDICO_ID = ? ,")
				.append(" C_MOD_ANTERIOR_ID = ? ,")
				.append(" C_PROC_EVALUACION_ID = ? ,")
				.append(" C_PROVINCIA_CARRERA_ID = ? ,")
				.append(" C_PROVINCIA_ID = ? ,")
				.append(" C_SIT_ADMINISTRATIVA_ID = ? ,")
				.append(" C_USR_ID = ? ,")
				.append(" C_USUMODI = ? ,")
				.append(" D_APELLIDOS = ? ,")
				.append(" D_LOCALIDAD = ? ,")
				.append(" D_NOMBRE = ? ,")
				.append(" F_REGISTRO_SOLIC = SYSDATE ,")
				.append(" F_USUMODI = SYSDATE ,")
				.append(" N_ANIOSANTIGUEDAD = ? ,")
				.append(" N_CODIGOPOSTAL = ? ,")
				.append(" N_DIAS_ANTIGUEDAD = ? ,")
				.append(" N_MESES_ANTIGUEDAD = ? ,")
				.append(" N_TELEFONO1 = ? ,")
				.append(" N_TELEFONO2 = ? ,")
				.append(" C_SIT_ADMON_ACTUAL_ID = ? ,")
				.append(" A_OTRASITADMON_ACTUAL = ? ,")
				.append(" C_PROV_CARRERA_ACTUAL_ID = ? ,")
				.append(" C_GERENCIA_ACTUAL_ID = ? ,")
				.append(" C_CENTROTRAB_ACTUAL_ID = ? ")
				.append(" WHERE C_SOLICITUD_ID = ? ");

			st = con.prepareCall(consulta.toString());
			int cont = 1;
			st.setString(cont++, datos.getDCorreoelectronico());
			st.setString(cont++, datos.getDDomicilio());
			st.setString(cont++, datos.getDSitAdministrativaOtros());

			st.setString(cont++, datos.getDRegimenJuridicoOtros());
			st.setString(cont++, datos.getAPuesto());
			st.setString(cont++, datos.getAServicio());

			st.setString(cont++, Constantes.NO);
			st.setString(cont++, datos.getBLopdSolicitud());
			st.setString(cont++, datos.getBSexo());

			st.setLong(cont++, datos.getCProfesional_id());
			st.setLong(cont++, datos.getCCentrotrabajo_id());
			st.setLong(cont++, datos.getCConvocatoriaId());

			st.setString(cont++, datos.getCDni().toUpperCase());
			st.setLong(cont++, datos.getCEspec_id());
			st.setString(cont++, datos.getCEstado());

			st.setLong(cont++, datos.getCGerencia_id());
			st.setLong(cont++, datos.getCGrado_id());
			st.setString(cont++, datos.getCJuridicoId());

			st.setLong(cont++, datos.getCModAnterior_id());
			st.setString(cont++, datos.getCProcedimientoId());
			st.setString(cont++, datos.getCProvinciaCarrera_id());

			st.setString(cont++, datos.getCProvincia_id());
			st.setString(cont++, datos.getCSitAdministrativaAuxId());

			st.setString(cont++, null);
			st.setString(cont++, datos.getCDni());

			st.setString(cont++, datos.getDApellido1());
			st.setString(cont++, datos.getDLocalidad());
			st.setString(cont++, datos.getDNombre());

			st.setLong(cont++, datos.getNAniosantiguedad());
			st.setString(cont++, datos.getCPostalUsu());

			st.setLong(cont++, datos.getNDiasantiguedad());
			st.setLong(cont++, datos.getNMesesantiguedad());
			if ((datos.getNTelefono1() != null) && (!datos.getNTelefono1().equals("")))
				st.setLong(cont++, Long.parseLong(datos.getNTelefono1()));
			else {
				st.setNull(cont++, 2);
			}
			if ((datos.getNTelefono2() != null) && (!datos.getNTelefono2().equals("")))
				st.setLong(cont++, Long.parseLong(datos.getNTelefono2()));
			else {
				st.setNull(cont++, 2);
			}

			st.setLong(cont++, datos.getCSitAdmonActualId());
			st.setString(cont++, datos.getAOtraSitAdmonActual());
			st.setString(cont++, datos.getCProvCarreraActualId());
			st.setLong(cont++, datos.getCGerenciaActualId());
			st.setLong(cont++, datos.getCCentroTrabActualId());
			st.setLong(cont++, datos.getCSolicitudId());
			
			st.executeUpdate();

		} catch (Exception ex) {
			throw ex;
		} finally {
			if (st != null) {
				st.close();
			}
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

	}
 
   public ArrayList buscarSolicitudes(int inicio, int cuantos, OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario)
     throws Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudes: Inicio");
     PreparedStatement sentencia = null;
     Connection con = null;
     StringBuffer consulta = new StringBuffer();
     ResultSet resultado = null;
     ArrayList listado = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       consulta.append("SELECT sol.c_dni, sol.d_nombre, sol.d_apellidos, gra.d_nombre nombregrado, sol.c_solicitud_id, sol.c_usr_id, sol.c_estado_id estadoSol, con.d_nombre nombreconvo, con.d_nombre_corto nombrecorto,con.F_FIN_PGP,").append("gra.c_grado_id, con.c_convocatoria_id, c_exp_id, exp.c_estado_id estadoExp ");
 
       consulta.append(" FROM ocap_grados gra, ocap_convocatorias con, ");
       
       if(!Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
    	   consulta.append("ocap_gerencias ger,");
       }
       
       consulta.append(" ocap_solicitudes sol left outer join ocap_expedientescarrera exp").append(" on sol.c_solicitud_id = exp.c_solicitud_id");
 
       consulta.append(" WHERE gra.c_grado_id = sol.c_grado_id").append(" AND con.c_convocatoria_id = sol.c_convocatoria_id");
       
       if(!Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
    	   consulta.append(" AND sol.c_gerencia_actual_id = ger.c_gerencia_id");
       }
       
       consulta.append(" AND sol.b_borrado = 'N'");
 
       if ((solicitudesOT.getDApellido1() != null) && (!"".equals(solicitudesOT.getDApellido1()))) {
         consulta.append(" AND UPPER(translate(sol.d_apellidos,'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) LIKE UPPER(translate('%" + solicitudesOT.getDApellido1() + "%','áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) ");
       }
       if ((solicitudesOT.getDNombre() != null) && (!"".equals(solicitudesOT.getDNombre()))) {
         consulta.append(" AND UPPER(translate(sol.d_nombre,'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) LIKE UPPER(translate('%" + solicitudesOT.getDNombre() + "%','áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) ");
       }
       if ((solicitudesOT.getCDni() != null) && (!"".equals(solicitudesOT.getCDni()))) {
         consulta.append(" AND UPPER(LPAD(sol.c_dni,10,'0')) = UPPER(LPAD('" + solicitudesOT.getCDni() + "',10,'0')) ");
       }
       if (solicitudesOT.getCGrado_id() != 0L) {
         consulta.append(" AND sol.c_grado_id = '" + solicitudesOT.getCGrado_id() + "'");
       }
       if (solicitudesOT.getCConvocatoriaId() != 0L) {
         consulta.append(" AND sol.c_convocatoria_id = '" + solicitudesOT.getCConvocatoriaId() + "'");
       }
       Map parametros = jcylUsuario.getParametrosUsuario();
       
       if(!Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
    	   consulta.append(" AND ger.c_gerencia_id = '" + (String)parametros.get("PARAM_GERENCIA") + "'");
    	   consulta.append(" AND ger.c_tipogerencia_id = '" + (String)parametros.get("PARAM_TIPO_GERENCIA") + "'");
    	   consulta.append(" AND ger.c_provincia_id = '" + (String)parametros.get("PARAM_PROV") + "'");
       }
 
       consulta.append(" ORDER BY sol.c_solicitud_id ASC, sol.c_dni ASC, sol.d_apellidos ASC");
 
       sentencia = con.prepareStatement(consulta.toString(), 1004, 1008);
       resultado = sentencia.executeQuery();
 
       if (inicio > 1) {
         resultado.absolute(inicio - 1);
       }
       listado = new ArrayList();
       int i = 0;
       while (resultado.next()) {
         solicitudesOT = new OCAPSolicitudesOT();
         solicitudesOT.setDNombre(resultado.getString("d_nombre"));
         solicitudesOT.setDApellido1(resultado.getString("d_apellidos"));
         solicitudesOT.setCDni(resultado.getString("c_dni"));
         solicitudesOT.setDGrado_des(resultado.getString("nombregrado"));
         solicitudesOT.setDConvocatoria_nombre(resultado.getString("nombreconvo"));
         solicitudesOT.setDConvocatoriaNombreCorto(resultado.getString("nombrecorto"));
         solicitudesOT.setCSolicitudId(resultado.getLong("c_solicitud_id"));
         solicitudesOT.setCUsr_id(resultado.getLong("c_usr_id"));
         solicitudesOT.setCEstado_id(resultado.getLong("estadoSol"));
         solicitudesOT.setCEstadoId(resultado.getLong("estadoExp"));
         solicitudesOT.setCExp_id(resultado.getLong("c_exp_id"));
         solicitudesOT.setFechaFinPgp(resultado.getDate("f_fin_pgp"));
 
         listado.add(solicitudesOT);
 
         if (++i == cuantos)	break;
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudes: Fin");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw new SQLException(e.toString() + "<br>Error al buscar las solicitudes");
     } finally {
       if (resultado != null)
         resultado.close();
       if (sentencia != null)
         sentencia.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 
   public int contarSolicitudes(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario)
     throws Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " contarSolicitudes: Inicio");
     PreparedStatement sentencia = null;
     Connection con = null;
     StringBuffer consulta = new StringBuffer();
     ResultSet resultado = null;
     ArrayList listado = null;
     int numSolicitudes = 0;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
 
       consulta = new StringBuffer();
       consulta.append("SELECT COUNT(*)");
       consulta.append(" FROM ocap_grados gra, ocap_convocatorias con, ");
       
       if(!Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
    	   consulta.append("ocap_gerencias ger,");
       }
       
       consulta.append(" ocap_solicitudes sol left outer join ocap_expedientescarrera exp").append(" on sol.c_solicitud_id = exp.c_solicitud_id");
 
       consulta.append(" WHERE gra.c_grado_id = sol.c_grado_id").append(" AND con.c_convocatoria_id = sol.c_convocatoria_id");
       
       if(!Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
    	   consulta.append(" AND sol.c_gerencia_actual_id = ger.c_gerencia_id");
       }
       
       consulta.append(" AND sol.b_borrado = 'N'");
 
       if ((solicitudesOT.getDApellido1() != null) && (!"".equals(solicitudesOT.getDApellido1()))) {
         consulta.append(" AND UPPER(translate(sol.d_apellidos,'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) LIKE UPPER(translate('%" + solicitudesOT.getDApellido1() + "%','áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) ");
       }
       if ((solicitudesOT.getDNombre() != null) && (!"".equals(solicitudesOT.getDNombre()))) {
         consulta.append(" AND UPPER(translate(sol.d_nombre,'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) LIKE UPPER(translate('%" + solicitudesOT.getDNombre() + "%','áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) ");
       }
       if ((solicitudesOT.getCDni() != null) && (!"".equals(solicitudesOT.getCDni()))) {
         consulta.append(" AND UPPER(LPAD(sol.c_dni,10,'0')) = UPPER(LPAD('" + solicitudesOT.getCDni() + "',10,'0')) ");
       }
       if (solicitudesOT.getCGrado_id() != 0L) {
         consulta.append(" AND sol.c_grado_id = '" + solicitudesOT.getCGrado_id() + "'");
       }
       if (solicitudesOT.getCConvocatoriaId() != 0L) {
         consulta.append(" AND sol.c_convocatoria_id = '" + solicitudesOT.getCConvocatoriaId() + "'");
       }
       Map parametros = jcylUsuario.getParametrosUsuario();
       if(!Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
    	   consulta.append(" AND ger.c_gerencia_id = '" + (String)parametros.get("PARAM_GERENCIA") + "'");
    	   consulta.append(" AND ger.c_tipogerencia_id = '" + (String)parametros.get("PARAM_TIPO_GERENCIA") + "'");
    	   consulta.append(" AND ger.c_provincia_id = '" + (String)parametros.get("PARAM_PROV") + "'");
       }
 
       sentencia = con.prepareStatement(consulta.toString(), 1004, 1008);
       resultado = sentencia.executeQuery();
 
       if (resultado.next()) {
         numSolicitudes = resultado.getInt(1);
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " contarSolicitudes: Fin");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw new SQLException(e.toString() + "<br>Error al contar las solicitudes");
     } finally {
       if (resultado != null)
         resultado.close();
       if (sentencia != null)
         sentencia.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return numSolicitudes;
   }
 
   public OCAPSolicitudesOT detalleSolicitud(long cSolicitudId)
     throws Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " detalleSolicitud: Inicio");
     PreparedStatement sentencia = null;
     PreparedStatement sentencia2 = null;
     Connection con = null;
     OCAPSolicitudesOT solicitudesOT = null;
     StringBuffer consulta = new StringBuffer();
     StringBuffer consulta2 = new StringBuffer();
     ResultSet resultado = null;
     //ResultSet resultado2 = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       consulta.append("SELECT con.d_NOMBRE AS nombre_convocatoria, con.d_anio_convocatoria, sol.c_usr_id, sol.d_apellidos, sol.d_nombre, sol.c_dni, sol.b_sexo, sol.n_telefono1, sol.n_telefono2, sol.a_correoelectronico, sol.a_domicilio, ").append(" sol.c_provincia_id, sol.D_LOCALIDAD, sol.n_codigopostal, sol.c_juridico_id, sol.A_OTROJURIDICO, sol.C_SIT_ADMINISTRATIVA_ID, sol.c_mod_anterior_id, ").append(" sol.C_PROC_EVALUACION_ID, sol.c_categoria_id, sol.C_ESPECIALIDAD_ID, sol.N_ANIOSANTIGUEDAD, sol.N_MESES_ANTIGUEDAD, sol.N_DIAS_ANTIGUEDAD, ").append(" TO_CHAR(F_REGISTRO_SOLIC,'DD/MM/RRRR hh24:mi:ss') as F_REGISTRO_SOLIC, B_LOPD_SOLICITUD, TO_CHAR(F_REGISTRO_OFICIAL,'DD/MM/RRRR hh24:mi:ss') as F_REGISTRO_OFICIAL, sol.A_OTRASITADMINISTRATIVA, ").append(" sol.C_PROVINCIA_CARRERA_ID, sol.C_ESTADO_ID, sol.A_SERVICIO, sol.A_PUESTO,  ").append(" sol.c_grado_id, sol.c_gerencia_id, sol.C_CENTROTRABAJO_ID, sol.c_convocatoria_id, ").append(" sol.c_sit_admon_actual_id, sol.a_otrasitadmon_actual, sol.c_prov_carrera_actual_id, sol.c_gerencia_actual_id, sol.c_centrotrab_actual_id ").append("  , gra.D_DESCRIPCION as dGrado_des ").append("  , cpr.d_nombre as dProfesional_nombre ").append("  , esp.d_nombre as dEspec_nombre ").append("  , mod.d_nombre as dModalidad, mod.c_modalidad_id as modId ").append("  , (select d_provincia from comu_provincias pro where pro.c_provincia_id = sol.c_provincia_id) as dProvinciaUsu ").append("  , (select d_provincia from comu_provincias pro where pro.c_provincia_id = sol.c_provincia_carrera_id) as dProvincia ").append("  , (select d_provincia from comu_provincias pro where pro.c_provincia_id = sol.c_prov_carrera_actual_id) as dProvinciaActual ").append("  , man.d_nombre as dModAnterior ").append("  , rju.d_nombre as cJuridico ").append("  , pev.d_nombre as dProcedimiento ").append("  , sad.d_nombre as dSitAdministrativaAux ").append("  , ger.d_nombre as dGerencia_nombre ").append("  , ctr.d_nombre as dCentroTrabajo_nombre ").append("  , est.c_personal_id as personalId ").append("  , (select sad.d_nombre from ocap_sit_administrativa sad where sol.c_sit_admon_actual_id = sad.c_sit_administrativa_id and sad.b_borrado='N') as dSit_admon_actual ").append("  , (select ger.d_nombre from ocap_gerencias ger where sol.c_gerencia_actual_id = ger.c_gerencia_id and ger.b_borrado='N') as dGerencia_nombre_actual ").append("  , (select ctr.d_nombre from ocap_centrostrabajo ctr where sol.c_centrotrab_actual_id = ctr.c_centrotrabajo_id and ctr.b_borrado='N') as dCentroTrabajo_nombre_actual ");
 
       consulta.append(" FROM ocap_solicitudes sol, ocap_grados gra, ocap_categ_profesionales cpr, ocap_especialidades esp,  ").append("    ocap_modalidades mod, ocap_mod_anterior man, ocap_reg_juridico rju, ocap_proc_evaluacion pev, ocap_sit_administrativa sad, ").append("    ocap_gerencias ger, ocap_centrostrabajo ctr, ocap_estatutario est , ocap_convocatorias con");
 
       consulta.append(" WHERE c_solicitud_id = ? ").append(" AND sol.b_borrado = 'N'").append(" AND sol.c_grado_id = gra.c_grado_id AND gra.b_borrado='N' ").append(" AND sol.C_CATEGORIA_ID = cpr.c_profesional_id and cpr.b_borrado='N' ").append(" AND sol.C_especialidad_ID = esp.c_ESPEC_id(+) ").append(" AND cpr.C_MODALIDAD_ID = mod.c_MODALIDAD_id and mod.b_borrado='N' ").append(" AND sol.C_MOD_ANTERIOR_ID = man.c_mod_anterior_id and mod.b_borrado='N' ").append(" AND sol.C_JURIDICO_ID = rju.C_JURIDICO_ID and rju.b_borrado='N' ").append(" AND sol.C_PROC_EVALUACION_ID = pev.C_PROC_EVALUACION_ID(+) and pev.b_borrado(+)='N' ").append(" AND sol.C_SIT_ADMINISTRATIVA_ID = sad.C_SIT_ADMINISTRATIVA_ID and sad.b_borrado='N' ").append(" AND sol.C_GERENCIA_ID = ger.C_GERENCIA_ID and ger.b_borrado='N' ").append(" AND sol.c_centrotrabajo_id = ctr.c_centrotrabajo_id and ctr.b_borrado='N' ").append(" AND cpr.c_estatut_id = est.c_estatut_id AND est.b_Borrado='N' AND sol.c_convocatoria_id = con.c_convocatoria_id");
       
       /*consulta2.append("select count(C_EXP_ID) from  ocap_expedientescarrera\r\n" + 
       		"   where b_conformidad_cte is NULL and\r\n" + 
       		"        b_decision_ce is NULL and\r\n" + 
       		"       f_informe_cte is NULL and\r\n" + 
       		"       f_informe_ce is null and\r\n" + 
       		"       f_informe_cc is null and\r\n" + 
       		"       b_reconocimiento_grado is NULL and\r\n" + 
       		"       f_informe_eval is null and\r\n" + 
       		"       a_especificaciones_eval is null and\r\n" + 
       		"       f_reunion_ce is null and\r\n" + 
       		"       f_reunion_cte is null and\r\n" + 
       		"       c_estado_id = 9 and\r\n" +
       		"		c_solicitud_id = ?"	);*/
       
 
       sentencia = con.prepareStatement(consulta.toString(), 1004, 1008);
       sentencia.setLong(1, cSolicitudId);
       resultado = sentencia.executeQuery();
       
       solicitudesOT = new OCAPSolicitudesOT();
 
       if (resultado.next()) {
    	 solicitudesOT.setDConvocatoria_nombre(resultado.getString("nombre_convocatoria"));
         solicitudesOT.setCUsr_id(resultado.getLong("c_usr_id"));
         solicitudesOT.setDApellido1(resultado.getString("d_apellidos"));
         solicitudesOT.setDNombre(resultado.getString("d_nombre"));
         solicitudesOT.setCDniReal(resultado.getString("c_dni"));
         solicitudesOT.setCDni(resultado.getString("c_dni"));
         solicitudesOT.setBSexo(resultado.getString("b_sexo"));
         solicitudesOT.setNTelefono1(resultado.getString("n_telefono1"));
         solicitudesOT.setNTelefono2(resultado.getString("n_telefono2"));
         solicitudesOT.setDCorreoelectronico(resultado.getString("a_correoelectronico"));
         solicitudesOT.setDDomicilio(resultado.getString("a_domicilio"));
         solicitudesOT.setCProvincia_id(resultado.getString("c_provincia_id"));
         solicitudesOT.setDProvinciaUsu(resultado.getString("dProvinciaUsu"));
         solicitudesOT.setCPostalUsu(resultado.getString("n_codigopostal"));
         solicitudesOT.setDLocalidad(resultado.getString("D_LOCALIDAD"));
         solicitudesOT.setCModAnterior_id(resultado.getLong("c_mod_anterior_id"));
         solicitudesOT.setDModAnterior(resultado.getString("dModAnterior"));
         solicitudesOT.setCJuridicoId(resultado.getString("c_juridico_id"));
         solicitudesOT.setCJuridico(resultado.getString("cJuridico"));
         solicitudesOT.setCEstatutId(resultado.getLong("personalId"));
         if ("1".equals(solicitudesOT.getCJuridicoId())) {
           if ("1".equals(resultado.getString("personalId")))
             solicitudesOT.setDRegimenJuridicoOtros("Sanitario");
           else if ("2".equals(resultado.getString("personalId")))
             solicitudesOT.setDRegimenJuridicoOtros("Gestión y Servicios");
         }
         else solicitudesOT.setDRegimenJuridicoOtros(resultado.getString("A_OTROJURIDICO"));
         solicitudesOT.setCSitAdministrativaAuxId(resultado.getString("C_SIT_ADMINISTRATIVA_ID"));
         solicitudesOT.setDSitAdministrativaAux_nombre(resultado.getString("dSitAdministrativaAux"));
         solicitudesOT.setDSitAdministrativaOtros(resultado.getString("A_OTRASITADMINISTRATIVA"));
         solicitudesOT.setCGrado_id(resultado.getLong("c_grado_id"));
         solicitudesOT.setDGrado_des(resultado.getString("dGrado_des"));
         if (resultado.getLong("c_grado_id") == 1L)
           solicitudesOT.setBGradoI("x");
         if (resultado.getLong("c_grado_id") == 2)
           solicitudesOT.setBGradoII("x");
         if (resultado.getLong("c_grado_id") == 3)
           solicitudesOT.setBGradoIII("x");
         if (resultado.getLong("c_grado_id") == 4)
           solicitudesOT.setBGradoIV("x");
         solicitudesOT.setCProfesional_id(resultado.getLong("c_categoria_id"));
         solicitudesOT.setDProfesional_nombre(resultado.getString("dProfesional_nombre"));
 
         solicitudesOT.setCEspec_id(resultado.getLong("C_ESPECIALIDAD_ID"));
         solicitudesOT.setDEspec_nombre(resultado.getString("dEspec_nombre"));
         solicitudesOT.setDModalidad(resultado.getString("dModalidad"));
 
         if ((resultado.getLong("modId") == 1L) || (resultado.getLong("modId") == 2)) {
           if ("1".equals(resultado.getString("c_juridico_id")))
             solicitudesOT.setBModPESFU("x");
           if ("2".equals(resultado.getString("c_juridico_id")))
             solicitudesOT.setBModPFSFU("x");
         }
         if ((resultado.getLong("modId") == 3) || (resultado.getLong("modId") == 4)) {
           if ("1".equals(resultado.getString("c_juridico_id")))
             solicitudesOT.setBModPESFP("x");
           if ("2".equals(resultado.getString("c_juridico_id")))
             solicitudesOT.setBModPFSFP("x");
         }
         if (((resultado.getLong("modId") == 5) || (resultado.getLong("modId") == 6)) && 
           ("1".equals(resultado.getString("c_juridico_id")))) {
           solicitudesOT.setBModPEGSFU("x");
         }
         if (((resultado.getLong("modId") == 7) || (resultado.getLong("modId") == 8) || (resultado.getLong("modId") == 9)) && 
           ("1".equals(resultado.getString("c_juridico_id")))) {
           solicitudesOT.setBModPEGSFP("x");
         }
         solicitudesOT.setCProvinciaCarrera_id(resultado.getString("C_PROVINCIA_CARRERA_ID"));
         solicitudesOT.setDProvincia(resultado.getString("dProvincia"));
         solicitudesOT.setCProcedimientoId(resultado.getString("C_PROC_EVALUACION_ID"));
         solicitudesOT.setDProcedimiento(resultado.getString("dProcedimiento"));
         solicitudesOT.setNAniosantiguedad(resultado.getLong("N_ANIOSANTIGUEDAD"));
         solicitudesOT.setNMesesantiguedad(resultado.getLong("N_MESES_ANTIGUEDAD"));
         solicitudesOT.setNDiasantiguedad(resultado.getLong("N_DIAS_ANTIGUEDAD"));
         solicitudesOT.setCGerencia_id(resultado.getLong("C_GERENCIA_ID"));
         solicitudesOT.setDGerencia_nombre(resultado.getString("dGerencia_nombre"));
         solicitudesOT.setCCentrotrabajo_id(resultado.getLong("C_CENTROTRABAJO_ID"));
         solicitudesOT.setDCentrotrabajo_nombre(resultado.getString("dCentroTrabajo_nombre"));
         solicitudesOT.setCConvocatoriaId(resultado.getLong("c_convocatoria_id"));
         solicitudesOT.setAServicio(resultado.getString("A_SERVICIO"));
         solicitudesOT.setAPuesto(resultado.getString("A_PUESTO"));
         solicitudesOT.setFRegistro_solic(resultado.getString("f_registro_solic"));
         solicitudesOT.setFRegistro_oficial(resultado.getString("f_registro_oficial"));
         solicitudesOT.setCEstado_id(resultado.getLong("c_estado_id"));
 
         solicitudesOT.setCSitAdmonActualId(resultado.getLong("c_sit_admon_actual_id"));
         solicitudesOT.setDSitAdmonActual(resultado.getString("dSit_admon_actual"));
         solicitudesOT.setAOtraSitAdmonActual(resultado.getString("a_otrasitadmon_actual"));
         solicitudesOT.setCProvCarreraActualId(resultado.getString("c_prov_carrera_actual_id"));
         solicitudesOT.setDProvCarreraActual(resultado.getString("dProvinciaActual"));
         solicitudesOT.setCGerenciaActualId(resultado.getLong("c_gerencia_actual_id"));
         solicitudesOT.setDGerenciaActual(resultado.getString("dGerencia_nombre_actual"));
         solicitudesOT.setCCentroTrabActualId(resultado.getLong("c_centrotrab_actual_id"));
         solicitudesOT.setDCentroTrabActual(resultado.getString("dCentroTrabajo_nombre_actual"));
 
         solicitudesOT.setCSolicitudId(cSolicitudId);
         DecimalFormat formato = new DecimalFormat("000000");
         solicitudesOT.setCodigoSolicitud(formato.format(solicitudesOT.getCSolicitudId()));
         solicitudesOT.setDAnioConvocatoria(resultado.getString("d_anio_convocatoria"));
         
         Date fechaActual = new Date(); 
         Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
         cal.setTime(fechaActual);
         int anno = cal.get(Calendar.YEAR);
         String mes = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.forLanguageTag("es-ES"));
         int dia = cal.get(Calendar.DAY_OF_MONTH);
         solicitudesOT.setDiaActual(String.valueOf(dia));
         solicitudesOT.setMesActual(mes);
         solicitudesOT.setAnyoActual(String.valueOf(anno));
         /*sentencia2=con.prepareStatement(consulta2.toString(), 1004,1008);
         sentencia2.setLong(1, cSolicitudId);
         resultado2 = sentencia2.executeQuery();
         if(resultado2.next()) {
        	if(resultado2.getInt(1)>0)
        		solicitudesOT.setEliminarEvaluacion(true);
        	else
        	 solicitudesOT.setEliminarEvaluacion(false);
         }*/
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " detalleSolicitud: Fin");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw new SQLException(e.toString() + "<br>Error al buscar las solicitudes");
     } finally {
       if (resultado != null)
         resultado.close();
       if (sentencia != null)
         sentencia.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return solicitudesOT;
   }
 
   public int modificarSolicitud(OCAPSolicitudesOT datos)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     int filas = 0;
     Connection con = JCYLGestionTransacciones.getConnection();
     try
     {
       String sql = "UPDATE OCAP_SOLICITUDES SET ";
 
       if (datos.getCUsr_id() != 0L) {
         sql = sql + " C_USR_ID = ?, ";
       }
       if (datos.getCEstado_id() != 0L) {
         sql = sql + " C_ESTADO_ID = ?, ";
       }
       if ((datos.getFRegistro_oficial() != null) && (!datos.getFRegistro_oficial().equals(""))) {
         sql = sql + " F_REGISTRO_OFICIAL = TO_DATE(?,'DD/MM/RRRR hh24:mi:ss'), ";
       }
       sql = sql + " C_USUMODI = ?,  F_USUMODI = SYSDATE  WHERE C_SOLICITUD_ID = ?";
 
       st = con.prepareStatement(sql);
       int cont = 1;
       if (datos.getCUsr_id() != 0L) {
         st.setLong(cont++, datos.getCUsr_id());
       }
       if (datos.getCEstado_id() != 0L) {
         st.setLong(cont++, datos.getCEstado_id());
       }
       if ((datos.getFRegistro_oficial() != null) && (!datos.getFRegistro_oficial().equals(""))) {
         st.setString(cont++, datos.getFRegistro_oficial());
       }
       st.setString(cont++, datos.getAModificadoPor());
       st.setLong(cont, datos.getCSolicitudId());
       filas = st.executeUpdate();
     }
     catch (SQLException ex) {
       throw ex;
     } catch (Exception e) {
       throw e;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return filas;
   }
 
   public int cambiarEstados(OCAPSolicitudesOT datos)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     int filas = 0;
     Connection con = JCYLGestionTransacciones.getConnection();
     try
     {
       String sql = "UPDATE OCAP_SOLICITUDES SET ";
       sql = sql + " C_ESTADO_ID = ?, ";
       sql = sql + " C_USUMODI = ?,  F_USUMODI = SYSDATE  WHERE C_USR_ID = ?  AND C_SOLICITUD_ID <> ?";
 
       st = con.prepareStatement(sql);
       int cont = 1;
 
       st.setLong(cont++, datos.getCEstado_id());
       st.setString(cont++, datos.getAModificadoPor());
       st.setLong(cont++, datos.getCUsr_id());
       st.setLong(cont++, datos.getCSolicitudId());
 
       filas = st.executeUpdate();
     }
     catch (SQLException ex) {
       throw ex;
     } catch (Exception e) {
       throw e;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return filas;
   }
 
   public OCAPSolicitudesOT buscarUltimaSolicitudAnuladaUsuario(Long cUsrId, long cConvocatoriaId)
     throws Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " buscarUltimaSolicitudAnuladaUsuario: Inicio");
     PreparedStatement sentencia = null;
     Connection con = null;
     StringBuffer consulta = new StringBuffer();
     ResultSet resultado = null;
     OCAPSolicitudesOT solicitudOT = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       consulta.append("SELECT c_solicitud_id ").append(" FROM ocap_solicitudes ").append(" WHERE c_usr_id = ? ").append(" AND c_convocatoria_id = ? ").append(" AND c_estado_id = 8 ").append(" AND b_borrado = 'N'").append(" ORDER BY f_registro_oficial DESC ");
 
       sentencia = con.prepareStatement(consulta.toString(), 1004, 1008);
       sentencia.setLong(1, cUsrId.longValue());
       sentencia.setLong(2, cConvocatoriaId);
 
       resultado = sentencia.executeQuery();
 
       if (resultado.next()) {
         solicitudOT = new OCAPSolicitudesOT();
 
         solicitudOT.setCSolicitudId(resultado.getLong("c_solicitud_id"));
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudesDNI: Fin");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw new SQLException(e.toString() + "<br>Error al buscar las solicitudes");
     } finally {
       if (resultado != null)
         resultado.close();
       if (sentencia != null)
         sentencia.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
		return solicitudOT;
	}

	/**
	 * 
	 * @param cExpId
	 * @return
	 * @throws SQLException
	 */
	public long obtenerIdSolicitud(Long cExpId) throws SQLException {
		long retorno = 0;
		OCAPConfigApp.logger.info(getClass().getName() + " obtenerIdSolicitud: Inicio");
		PreparedStatement sentencia = null;
		Connection con = null;
		StringBuffer consulta = new StringBuffer();
		ResultSet resultado = null;
		try {
			con = JCYLGestionTransacciones.getConnection();
			consulta.append("SELECT c_solicitud_id ").append(" FROM ocap_expedientescarrera ")
					.append(" WHERE c_exp_id = ? ");

			sentencia = con.prepareStatement(consulta.toString(), 1004, 1008);
			sentencia.setLong(1, cExpId.longValue());

			resultado = sentencia.executeQuery();

			if (resultado.next()) {
				retorno = resultado.getLong("c_solicitud_id");
			}

			OCAPConfigApp.logger.info(getClass().getName() + " obtenerIdSolicitud: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw new SQLException(e.toString() + "<br>Error al obtener el id de la solicitud");
		} finally {
			if (resultado != null)
				resultado.close();
			if (sentencia != null)
				sentencia.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}
		return retorno;
	}
 }
