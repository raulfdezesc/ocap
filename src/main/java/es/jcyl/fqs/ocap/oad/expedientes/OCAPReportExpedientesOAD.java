 package es.jcyl.fqs.ocap.oad.expedientes;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.expedientes.OCAPReportExpedientesOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 
 public class OCAPReportExpedientesOAD
 {
   public Logger loggerBD;
   private static OCAPReportExpedientesOAD instance;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   private OCAPReportExpedientesOAD()
   {
     $init$();
   }
 
   public static OCAPReportExpedientesOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPReportExpedientesOAD();
     }
     return instance;
   }
 
   public ArrayList buscarDatosUsuariosPorExpediente(OCAPReportExpedientesOT datosBusqueda)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     ArrayList listado = null;
     try
     {
       StringBuffer sql = new StringBuffer();
       sql.append(" SELECT ecar.c_exp_id, c.d_nombre as d_convocatoria, ecar.c_usr_id, ").append(" solic.d_nombre,  solic.d_apellidos, solic.b_sexo, ").append(" solic.c_dni, solic.c_dni as C_DNI_REAL, ").append(" solic.c_gerencia_actual_id as C_GERENCIA_ID, gere.d_nombre d_gerencia, prge.d_provincia d_provincia_gerencia, gere.d_gerente, ").append(" solic.C_CENTROTRAB_ACTUAL_ID as c_centrotrabajo_id, cent.d_nombre d_centrotrabajo, ").append(" solic.a_domicilio, solic.c_provincia_id, prov.d_provincia, solic.d_localidad as d_local, solic.n_codigopostal, ").append(" solic.a_correoelectronico, solic.n_telefono1, solic.n_telefono2, ").append(" ecar.c_grado_id, grad.d_descripcion d_grado, ecar.c_convocatoria_id, ").append(" ecar.c_mod_anterior_id, moan.d_nombre d_mod_anterior, ").append(" ecar.c_juridico_id, reju.d_nombre d_juridico, DECODE(ecar.c_juridico_id, 1, estt.c_personal_id,NULL) c_tipo, ").append(" ecar.c_proc_evaluacion_id c_proc_administrativo_id, pcev.d_nombre d_proc_administrativo, ").append(" ecar.c_sit_administrativa_id, siad.d_nombre d_sit_administrativa, ecar.a_otrasitadministrativa, ").append(" ecar.c_profesional_id, cpro.d_nombre d_categ_profesional, ").append(" ecar.c_espec_id, espe.d_nombre d_especialidad, ").append(" ecar.a_servicio, ecar.a_puesto, ").append(" ecar.n_aniosantiguedad, ecar.n_meses_antiguedad, ecar.n_dias_antiguedad, ecar.d_motivoneg_mc ").append(" FROM ocap_solicitudes solic, ocap_expedientescarrera ecar, ocap_convocatorias c, ").append(" ocap_usuarios usua, ").append(" ocap_gerencias gere, comu_provincias prge, ").append(" ocap_centrostrabajo cent, ").append(" comu_provincias prov, ").append(" ocap_grados grad, ").append(" ocap_mod_anterior moan, ").append(" ocap_proc_evaluacion pcev, ").append(" ocap_reg_juridico reju, ").append(" ocap_sit_administrativa siad, ").append(" ocap_categ_profesionales cpro, ocap_estatutario estt, ").append(" ocap_especialidades espe ").append(" WHERE ecar.c_exp_id IN (");
 
       for (int i = 0; i < datosBusqueda.getListaExpedientesBusqueda().length; i++)
         sql.append("?,");
       sql.deleteCharAt(sql.length() - 1);
 
       sql.append(")").append(" and ecar.c_convocatoria_id = c.c_convocatoria_id AND ecar.c_usr_id = usua.c_usr_id ").append(" AND solic.c_usr_id = usua.c_usr_id ").append(" AND solic.c_convocatoria_id = ecar.c_convocatoria_id ").append(" AND solic.c_gerencia_actual_id = gere.c_gerencia_id ").append(" AND gere.c_provincia_id = prge.c_provincia_id ").append(" AND solic.c_gerencia_actual_id = cent.c_gerencia_id ").append(" AND solic.C_CENTROTRAB_ACTUAL_ID = cent.c_centrotrabajo_id ").append(" AND solic.C_PROVINCIA_ID = prov.c_provincia_id(+) ").append(" AND ecar.c_grado_id = grad.c_grado_id ").append(" AND ecar.c_mod_anterior_id = moan.c_mod_anterior_id(+) ").append(" AND ecar.c_sit_administrativa_id = siad.c_sit_administrativa_id ").append(" AND ecar.c_juridico_id = reju.c_juridico_id(+) ").append(" AND ecar.c_proc_evaluacion_id = pcev.c_proc_evaluacion_id").append(" AND ecar.c_profesional_id = cpro.c_profesional_id(+) ").append(" AND cpro.c_estatut_id = estt.c_estatut_id ").append(" AND ecar.c_profesional_id = espe.c_profesional_id(+) ").append(" AND ecar.c_espec_id = espe.c_espec_id(+) ").append(" ORDER BY  NLSSORT(solic.d_apellidos, 'NLS_SORT = Spanish') ASC, ").append(" NLSSORT(solic.d_nombre, 'NLS_SORT = Spanish') ASC ");
 
       this.loggerBD.info("Sentencia SQL:" + sql.toString());
       
       st = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 
 
       int cont = 1;
       for (int i = 0; i < datosBusqueda.getListaExpedientesBusqueda().length; i++) {
         st.setLong(cont++, datosBusqueda.getListaExpedientesBusqueda()[i]);
       }
       rs = st.executeQuery();
       listado = new ArrayList();
       while (rs.next()) {
         OCAPReportExpedientesOT datos = new OCAPReportExpedientesOT();
         datos.setCExpedienteId(rs.getLong("c_exp_id"));
         datos.setCUsuarioId(rs.getLong("c_usr_id"));
         datos.setDNombre(rs.getString("d_nombre"));
         datos.setDApellidos(rs.getString("d_apellidos"));
         datos.setBSexo(rs.getString("b_sexo"));
         datos.setCDNI(rs.getString("c_dni"));
         datos.setCDNIReal(rs.getString("c_dni_real"));
         datos.setCGerenciaId(rs.getLong("c_gerencia_id"));
         datos.setDGerencia(rs.getString("d_gerencia"));
         datos.setDGerente(rs.getString("d_gerente"));
         datos.setDProvinciaGerencia(rs.getString("d_provincia_gerencia"));
         datos.setCCentroTrabajoId(rs.getLong("c_centrotrabajo_id"));
         datos.setDCentroTrabajo(rs.getString("d_centrotrabajo"));
         datos.setADomicilio(rs.getString("a_domicilio"));
         datos.setCProvinciaId(rs.getString("c_provincia_id"));
         datos.setDProvincia(rs.getString("d_provincia"));
         datos.setDLocalidad(rs.getString("d_local"));
         datos.setNCodigopostal(rs.getInt("n_codigopostal"));
         datos.setACorreoElectronico(rs.getString("a_correoelectronico"));
         datos.setNTelefono1(rs.getLong("n_telefono1"));
         datos.setNTelefono2(rs.getLong("n_telefono2"));
         datos.setCGradoId(rs.getLong("c_grado_id"));
         datos.setDGrado(rs.getString("d_grado"));
         datos.setCConvocatoriaId(rs.getLong("c_convocatoria_id"));
         datos.setCModAnteriorId(rs.getLong("c_mod_anterior_id"));
         datos.setDModAnterior(rs.getString("d_mod_anterior"));
         datos.setCRegJuridicoId(rs.getLong("c_juridico_id"));
         datos.setDRegJuridico(rs.getString("d_juridico"));
         datos.setCTipo(rs.getLong("c_tipo"));
         datos.setCProcAdministrativoId(rs.getLong("c_proc_administrativo_id"));
         datos.setDProcAdministrativo(rs.getString("d_proc_administrativo"));
         datos.setCSitAdministrativaId(rs.getLong("c_sit_administrativa_id"));
         datos.setDSitAdministrativa(rs.getString("d_sit_administrativa"));
         if (rs.getString("a_otrasitadministrativa") != null)
           datos.setDSitAdministrativa(datos.getDSitAdministrativa() + ": " + rs.getString("a_otrasitadministrativa"));
         datos.setCCategProfesionalId(rs.getLong("c_profesional_id"));
         datos.setDCategProfesional(rs.getString("d_categ_profesional"));
         datos.setCEspecialidadId(rs.getLong("c_espec_id"));
         datos.setDEspecialidad(rs.getString("d_especialidad"));
         datos.setAServicio(rs.getString("a_servicio"));
         datos.setAPuesto(rs.getString("a_puesto"));
         datos.setNAniosAntiguedad(rs.getLong("n_aniosantiguedad"));
         datos.setNMesesAntiguedad(rs.getLong("n_meses_antiguedad"));
         datos.setNDiasAntiguedad(rs.getLong("n_dias_antiguedad"));
         datos.setDMotivoNegMC(rs.getString("d_motivoneg_mc"));
         datos.setDConvocatoria(rs.getString("d_convocatoria"));
 
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
 
   public ArrayList buscarDatosItinerariosPorExpediente(OCAPReportExpedientesOT datosBusqueda)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     ArrayList listado = null;
     DecimalFormat formato = new DecimalFormat("000000");
     try
     {
       StringBuffer sql = new StringBuffer();
       sql.append(" SELECT ecar.c_exp_id, ecar.c_itinerario_id, ").append(" meva.d_nombre ").append(" FROM ocap_expedientescarrera ecar, ").append(" itcp_manuales_evaluacion meva ").append(" WHERE ecar.c_exp_id IN (");
 
       for (int i = 0; i < datosBusqueda.getListaExpedientesBusqueda().length; i++)
         sql.append("?,");
       sql.deleteCharAt(sql.length() - 1);
 
       sql.append(")").append(" AND ecar.c_itinerario_id = meva.c_manual_evaluacion_id ");
 
       this.loggerBD.info("Sentencia SQL:" + sql.toString());
 
       st = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 
       int cont = 1;
       for (int i = 0; i < datosBusqueda.getListaExpedientesBusqueda().length; i++) {
         st.setLong(cont++, datosBusqueda.getListaExpedientesBusqueda()[i]);
       }
       rs = st.executeQuery();
 
       listado = new ArrayList();
       while (rs.next()) {
         OCAPReportExpedientesOT datos = new OCAPReportExpedientesOT();
         datos.setCExpedienteId(rs.getLong("c_exp_id"));
         datos.setCItinerarioId(rs.getLong("c_itinerario_id"));
         datos.setDNombreItin(rs.getString("d_nombre"));
         datos.setDCodEpr("EPR" + formato.format(datos.getCExpedienteId()));
 
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
 
   public Hashtable buscarDatosCreditosDefMCPorExpediente(OCAPReportExpedientesOT datosBusqueda)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     Hashtable tablaExpediente = null;
     ArrayList listado = null;
     try
     {
       StringBuffer sql = new StringBuffer();
       sql.append(" SELECT ecar.c_exp_id, ecar.c_grado_id, ecar.c_profesional_id, cpro.c_estatut_id,  ").append(" cred.c_defmerito_id, demc.d_nombre d_defmerito, ").append(" cred.n_creditos ").append(" FROM ( ").append(" SELECT c_exp_id, c_grado_id, c_profesional_id ").append(" FROM ocap_expedientescarrera  ").append(" WHERE  c_exp_id IN ( ");
 
       for (int i = 0; i < datosBusqueda.getListaExpedientesBusqueda().length; i++)
         sql.append("?,");
       sql.deleteCharAt(sql.length() - 1);
 
       sql.append(" )) ecar,  ").append(" ocap_categ_profesionales cpro, ").append(" ocap_creditos cred, ").append(" ocap_def_meritoscurriculares demc ").append(" WHERE ecar.c_profesional_id = cpro.c_profesional_id ").append(" AND ecar.c_grado_id = cred.c_grado_id ").append(" AND cpro.c_estatut_id = cred.c_estatut_id ").append(" AND cred.c_defmerito_id = demc.c_defmerito_id ").append(" AND cpro.b_borrado = 'N' ").append(" AND cred.b_borrado = 'N' ").append(" AND demc.b_borrado = 'N' ").append(" ORDER BY ecar.c_exp_id asc, cred.c_defmerito_id asc ");
 
       this.loggerBD.info("Sentencia SQL:" + sql.toString());
 
       st = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 
       int cont = 1;
       for (int i = 0; i < datosBusqueda.getListaExpedientesBusqueda().length; i++) {
         st.setLong(cont++, datosBusqueda.getListaExpedientesBusqueda()[i]);
       }
       rs = st.executeQuery();
       tablaExpediente = new Hashtable();
       listado = new ArrayList();
       boolean siCorrido = false;
       long cExpedienteId = 0L;
 
       while (rs.next())
       {
         siCorrido = true;
         if (cExpedienteId != rs.getLong("c_exp_id")) {
           if (cExpedienteId != 0L) {
             tablaExpediente.put(Long.valueOf(cExpedienteId), listado);
             listado = new ArrayList();
           }
           cExpedienteId = rs.getLong("c_exp_id");
         }
 
         OCAPReportExpedientesOT datos = new OCAPReportExpedientesOT();
         datos.setCExpedienteId(rs.getLong("c_exp_id"));
         datos.setCGradoId(rs.getLong("c_grado_id"));
         datos.setCCategProfesionalId(rs.getLong("c_profesional_id"));
         datos.setCEstatutarioId(rs.getLong("c_estatut_id"));
         datos.setCDefMeritoCurricularId(rs.getLong("c_defmerito_id"));
         datos.setDDefMeritoCurricular(rs.getString("d_defmerito"));
 
         datos.setNCreditosDefMeritoCurricular(rs.getFloat("n_creditos"));
 
         listado.add(datos);
       }
 
       if (siCorrido)
         tablaExpediente.put(Long.valueOf(cExpedienteId), listado);
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
 
     return tablaExpediente;
   }
 
   public Hashtable buscarDatosMCPorExpediente(OCAPReportExpedientesOT datosBusqueda)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     Hashtable tablaExpediente = null;
     Hashtable tablaDefMC = null;
     ArrayList listado = null;
     String motivo ="";
     try
     {
       StringBuffer sql = new StringBuffer();
       sql.append(" SELECT exmc.c_exp_id, mecu.c_defmerito_id, mecu.d_defmerito, mecu.c_estatut_id, ")
       .append(" exmc.c_merito_id, mecu.d_merito, mecu.d_merito_corto, mecu.c_tipo_merito, ")
       .append(" mecu.b_acreditado b_acreditado_mc, mecu.n_creditos_mc, ")
       .append(" mecu.c_mer_actividad_mc, mecu.d_mer_actividad_mc, ")
       .append(" mecu.c_mer_modalidad_mc, mecu.d_mer_modalidad_mc, ")
       .append(" mecu.c_factor_impacto_mc, mecu.d_factor_impacto_mc, ")
       .append(" mecu.c_tipo_firmante_mc, mecu.d_tipo_firmante_mc, ")
       .append(" DECODE(exmc.b_opcional,'Y',5,mecu.c_defmerito_id) c_defmerito_opcional, ")
       .append(" exmc.c_expmc_id, exmc.d_titulo d_expmc, exmc.b_opcional b_opcional_emc, ")
       .append(" exmc.f_inicio, exmc.f_finalizacion, exmc.CLOB_ORGANIZADOR, exmc.f_annio, exmc.n_sesiones, ")
       .append(" exmc.f_expedicion, exmc.a_lugar_expedicion, exmc.n_horas, exmc.n_dias, exmc.n_annios, ")
       .append(" exmc.b_participacion, exmc.a_nombre_revista, exmc.a_cargo, exmc.a_objetivo, ")
       .append(" exmc.n_meses, exmc.n_cred_curso, exmc.n_isbn, exmc.d_editorial, exmc.d_capitulo, exmc.b_acreditado b_acreditado_emc, ")
       .append(" exmc.n_cred_usuario, ")
       .append(" exmc.n_cred_ceis, exmc.b_invalidado_ceis, exmc.b_pdte_aclarar_ceis, ")
       .append(" exmc.n_cred_cc, exmc.b_invalidado_cc, exmc.b_pdte_aclarar_cc, exmc.D_MOTIVOS_CEIS, exmc.D_MOTIVOS_CC ")
       .append(" FROM ocap_expedientesmcs exmc, ocap_expedientescarrera expdte,ocap_categ_profesionales categoria, ")
       .append(" ( SELECT mc.c_merito_id, mc.c_estatut_id, mc.c_tipo_merito, ")
       .append(" mc.d_nombrecorto d_merito_corto, mc.d_nombre d_merito, ")
       .append(" mc.c_defmerito_id, df.d_nombre d_defmerito, ")
       .append(" mc.c_actividad_id c_mer_actividad_mc, ac.d_nombre d_mer_actividad_mc, ")
       .append(" mc.c_modalidad_id c_mer_modalidad_mc, md.d_nombre d_mer_modalidad_mc, ")
       .append(" mc.c_factor_id c_factor_impacto_mc, fi.d_nombre d_factor_impacto_mc, ")
       .append(" mc.c_tipo_id c_tipo_firmante_mc, tf.d_nombre d_tipo_firmante_mc, ")
       .append(" mc.b_acreditado, mc.n_creditos n_creditos_mc, mc.n_orden n_orden_mc, mc.b_borrado ")
       .append(" FROM ocap_meritoscurriculares mc, ")
       .append(" ocap_def_meritoscurriculares df, ")
       .append(" ocap_meractividades ac, ")
       .append(" ocap_mermodalidades md, ")
       .append(" ocap_factoresimpacto fi, ")
       .append(" ocap_tiposfirmante tf ")
       .append(" WHERE mc.c_defmerito_id = df.c_defmerito_id ")
       .append(" AND mc.c_actividad_id = ac.c_actividad_id(+) ")
       .append(" AND mc.c_modalidad_id = md.c_modalidad_id(+) ")
       .append(" AND mc.c_factor_id = fi.c_factor_id(+) ")
       .append(" AND mc.c_tipo_id = tf.c_tipo_id(+) ")
       .append(" AND mc.b_borrado = 'N' ")
       .append(" AND df.b_borrado = 'N' ")
       .append(" ORDER BY mc.c_defmerito_id asc, mc.n_orden asc ")
       .append(" )mecu ")
       .append(" WHERE exmc.c_exp_id IN ( ");
 
       for (int i = 0; i < datosBusqueda.getListaExpedientesBusqueda().length; i++)
         sql.append("?,");
       sql.deleteCharAt(sql.length() - 1);
 
       sql.append(" ) ")
       
       .append(" AND expdte.c_exp_id = exmc.c_exp_id ")
       .append(" AND categoria.c_profesional_id = expdte.c_profesional_id ")
       .append(" AND mecu.c_estatut_id = categoria.c_estatut_id ")
       
       .append(" AND exmc.c_merito_id = mecu.c_merito_id ")
       .append(" AND mecu.b_borrado = 'N' ")
       .append(" AND exmc.b_borrado = 'N' ")
       .append("  ORDER BY exmc.c_exp_id asc, DECODE(exmc.b_opcional,'Y',2,1) asc, mecu.c_defmerito_id asc, ")
       .append(" mecu.n_orden_mc asc, exmc.b_acreditado desc, mecu.c_mer_modalidad_mc asc, exmc.f_annio ASC ");
 
       st = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 
       int cont = 1;
       for (int i = 0; i < datosBusqueda.getListaExpedientesBusqueda().length; i++) {
         st.setLong(cont++, datosBusqueda.getListaExpedientesBusqueda()[i]);
       }
       rs = st.executeQuery();
 
       tablaExpediente = new Hashtable();
       tablaDefMC = new Hashtable();
       listado = new ArrayList();
       boolean siCorrido = false;
       long cExpedienteId = 0L;
       long cDefMeritoOpcional = 0L;
       String codigo = "" + cExpedienteId + "#" + cDefMeritoOpcional;
 
       while (rs.next())
       {
         siCorrido = true;
         motivo = "";
 
         if (!codigo.equals("" + rs.getLong("c_exp_id") + "#" + rs.getLong("c_defmerito_opcional")))
         {
           if ((cExpedienteId != 0L) && (cExpedienteId != rs.getLong("c_exp_id"))) {
             tablaDefMC.put(Long.valueOf(cDefMeritoOpcional), listado);
             tablaExpediente.put(Long.valueOf(cExpedienteId), tablaDefMC);
             listado = new ArrayList();
             tablaDefMC = new Hashtable();
           } else if ((cDefMeritoOpcional != 0L) && (cDefMeritoOpcional != rs.getLong("c_defmerito_opcional"))) {
             tablaDefMC.put(Long.valueOf(cDefMeritoOpcional), listado);
             listado = new ArrayList();
           }
 
           if (cExpedienteId != rs.getLong("c_exp_id")) cExpedienteId = rs.getLong("c_exp_id");
           if (cDefMeritoOpcional != rs.getLong("c_defmerito_opcional")) cDefMeritoOpcional = rs.getLong("c_defmerito_opcional");
           codigo = "" + cExpedienteId + "#" + cDefMeritoOpcional;
         }
 
         OCAPReportExpedientesOT datos = new OCAPReportExpedientesOT();
         datos.setCExpedienteId(rs.getLong("c_exp_id"));
         datos.setCEstatutarioId(rs.getLong("c_estatut_id"));
         datos.setCDefMeritoCurricularId(rs.getLong("c_defmerito_id"));
         datos.setDDefMeritoCurricular(rs.getString("d_defmerito"));
         datos.setCMeritoCurricularId(rs.getLong("c_merito_id"));
         datos.setDMeritoCurricular(rs.getString("d_merito"));
         datos.setDMeritoCurricularCorto(rs.getString("d_merito_corto"));
         datos.setCTipoMeritoCurricular(rs.getString("c_tipo_merito"));
         datos.setBAcreditadoMeritoCurricular(rs.getString("b_acreditado_mc"));
         datos.setCMerActividadMC(rs.getLong("c_mer_actividad_mc"));
         datos.setDMerActividadMC(rs.getString("d_mer_actividad_mc"));
         datos.setCMerModalidadMC(rs.getLong("c_mer_modalidad_mc"));
         datos.setDMerModalidadMC(rs.getString("d_mer_modalidad_mc"));
         datos.setCFactorImpactoMC(rs.getLong("c_factor_impacto_mc"));
         datos.setDFactorImpactoMC(rs.getString("d_factor_impacto_mc"));
         datos.setCTipoFirmanteMC(rs.getLong("c_tipo_firmante_mc"));
         datos.setDTipoFirmanteMC(rs.getString("d_tipo_firmante_mc"));
         datos.setCExpedienteMCId(rs.getLong("c_expmc_id"));
         datos.setDExpedienteMC(rs.getString("d_expmc"));
         datos.setCDefMeritoOpcional(rs.getLong("c_defmerito_opcional"));
         datos.setBOpcionalExpMC(rs.getString("b_opcional_emc"));
         datos.setBAcreditadoExpMC(rs.getString("b_acreditado_emc"));
         datos.setFInicio(rs.getDate("f_inicio"));
         datos.setFFinalizacion(rs.getDate("f_finalizacion"));
         datos.setAOrganizador(rs.getString("CLOB_ORGANIZADOR"));
         datos.setFAnnio(rs.getInt("f_annio"));
         datos.setNSesiones(rs.getInt("n_sesiones"));
         datos.setFExpedicion(rs.getDate("f_expedicion"));
         datos.setALugarExpedicion(rs.getString("a_lugar_expedicion"));
         datos.setNHoras(rs.getInt("n_horas"));
         datos.setNDias(rs.getInt("n_dias"));
         datos.setNAnnios(rs.getInt("n_annios"));
         datos.setBParticipacion(rs.getString("b_participacion"));
         datos.setANombreRevista(rs.getString("a_nombre_revista"));
         datos.setACargo(rs.getString("a_cargo"));
         datos.setAObjetivo(rs.getString("a_objetivo"));
         datos.setNMeses(rs.getInt("n_meses"));
         datos.setNCreditosCurso(rs.getFloat("n_cred_curso"));
         datos.setNISBN(rs.getLong("n_isbn"));
         datos.setDEditorial(rs.getString("d_editorial"));
         datos.setDCapitulo(rs.getString("d_capitulo"));
         datos.setNCreditosUsuario(rs.getFloat("n_cred_usuario"));
 
         datos.setNCreditosCEIS(rs.getFloat("n_cred_ceis"));
         datos.setBInvalidadoCEIS(rs.getString("b_invalidado_ceis"));
         datos.setBPdteAclararCEIS(rs.getString("b_pdte_aclarar_ceis"));
         datos.setNCreditosCC(rs.getFloat("n_cred_cc"));
         datos.setBInvalidadoCC(rs.getString("b_invalidado_cc"));
         datos.setBPdteAclararCC(rs.getString("b_pdte_aclarar_cc"));
         datos.setNCreditosMC(rs.getFloat("n_creditos_mc"));
         if(null!= rs.getString("D_MOTIVOS_CEIS") && !rs.getString("D_MOTIVOS_CEIS").equals("")) {
        	 motivo ="CEIS: "+ rs.getString("D_MOTIVOS_CEIS");
         }
         if(null!= rs.getString("D_MOTIVOS_CC") && !rs.getString("D_MOTIVOS_CC").equals("")) { 
        	 if(motivo.length() > 1) {
        		 motivo = motivo + ".\nCC: "+rs.getString("D_MOTIVOS_CC");
        	 }else {
        		 motivo = "CC: "+rs.getString("D_MOTIVOS_CC");
        	 }
        	 
         }
         if(motivo.equals("")) {
        	 datos.setDMotivoModif(null);
         }else {
        	 datos.setDMotivoModif(motivo);
         }
        	 
 
         listado.add(datos);
       }
 
       if (siCorrido) {
         tablaDefMC.put(Long.valueOf(cDefMeritoOpcional), listado);
         tablaExpediente.put(Long.valueOf(cExpedienteId), tablaDefMC);
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
 
     return tablaExpediente;
   }
 
   public float calcularCreditosTotalesCTSNA(long cExpId, String bMCUsuario)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = null;
     float creditosTotalesCTSNA = 0.0F;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
 
       StringBuffer sql = new StringBuffer();
       sql.append(" select sum(floor(numhoras/10) * creditoshora)creditostotal ").append(" from ").append(" (select emc.c_exp_id, mecu.c_tipo_merito, mecu.b_acreditado, mecu.c_modalidad_id, (mecu.n_creditos)creditoshora, sum(emc.n_horas)numhoras ").append(" from ocap_expedientesmcs emc, ocap_meritoscurriculares mecu ").append(" where emc.c_merito_id = mecu.c_merito_id ").append(" and emc.c_exp_id = ? ").append(" and emc.b_acreditado = 'N' ");
 
       if (Constantes.NO.equals(bMCUsuario)) {
         sql.append(" and (emc.b_invalidado_ceis is null or emc.b_invalidado_ceis = 'N') ");
       } else if (Constantes.CC.equals(bMCUsuario)){
    	   sql.append(" and (emc.b_invalidado_cc is null or emc.b_invalidado_cc = 'N') ");
       }
 
       sql.append(" and emc.b_borrado = 'N' ").append(" and mecu.c_tipo_merito = '").append("Taller").append("' ").append(" and mecu.b_acreditado = 'N' ").append(" and mecu.b_borrado = 'N' ").append(" group by emc.c_exp_id, mecu.c_tipo_merito, mecu.b_acreditado, mecu.c_modalidad_id, mecu.n_creditos) ");
 
       this.loggerBD.info("Sentencia SQL calcularCreditosTotalesCTSNA: " + sql.toString());
 
       st = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
       st.setLong(1, cExpId);
       rs = st.executeQuery();
 
       if (rs.next())
         creditosTotalesCTSNA = rs.getFloat("creditostotal");
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
 
     return creditosTotalesCTSNA;
   }
 }

