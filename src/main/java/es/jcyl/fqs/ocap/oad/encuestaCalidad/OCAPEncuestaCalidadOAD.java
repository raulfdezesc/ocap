 package es.jcyl.fqs.ocap.oad.encuestaCalidad;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.encuestaCalidad.OCAPEncuestaCalidadOT;
 import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.text.DecimalFormat;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPEncuestaCalidadOAD
 {
   public Logger logger;
   private static OCAPEncuestaCalidadOAD instance;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
   public static OCAPEncuestaCalidadOAD getInstance() {
     if (instance == null) {
       instance = new OCAPEncuestaCalidadOAD();
     }
     return instance;
   }
 
   private OCAPEncuestaCalidadOAD() {
     $init$();
   }
 
   public int rellenaOCAPEncuestaCalidad(OCAPEncuestaCalidadOT datos)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     int filas = 0;
     OCAPEncuestaCalidadOT areaOT = null;
     OCAPEncuestaCalidadOT pregOT = null;
     OCAPEncuestaCalidadOT respOT = null;
     try
     {
       String sql = "INSERT INTO OCAP_EXPEDIENTESENCUESTAS (C_EXPENC_ID, C_EXP_ID, C_PREGUNTA_ID, D_RESPUESTA, c_respuesta_id, B_BORRADO, F_USUALTA, C_USUALTA) VALUES (OCAP_EEN_ID_SEQ.nextval, ?, ?, ?, ?, 'N', SYSDATE,?)";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
       int contador = 0;
       for (int i = 0; i < datos.getListaPreguntas().size(); i++) {
         areaOT = (OCAPEncuestaCalidadOT)datos.getListaPreguntas().get(i);
         for (int j = 0; j < areaOT.getListaPreguntas().size(); j++) {
           boolean bInsertar = false;
           pregOT = (OCAPEncuestaCalidadOT)areaOT.getListaPreguntas().get(j);
           st.setLong(1, datos.getCExpId());
           st.setLong(2, pregOT.getCPreguntaId());
           if ((pregOT.getCTipoRespuesta() == 3) || (pregOT.getCTipoRespuesta() == 4)) {
             st.setString(3, (String)datos.getListaRespuestas().get(contador));
             st.setString(4, null);
             bInsertar = true;
           } else if (((pregOT.getCTipoRespuesta() == 0L) && (pregOT.getNumSubPreguntas() == 0L)) || (pregOT.getCTipoRespuesta() != 0L)) {
             st.setString(3, null);
             st.setString(4, (String)datos.getListaRespuestas().get(contador));
             bInsertar = true;
           }
           st.setString(5, datos.getACreadoPor());
 
           if (bInsertar) {
             filas = st.executeUpdate();
             OCAPConfigApp.logger.info("Pregunta:" + pregOT.getCPreguntaId() + "-Respuesta:" + datos.getListaRespuestas().get(contador));
             contador++;
           }
         }
       }
     }
     catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return filas;
   }
 
   public int OCAPCuentaRespuestasEncuesta(long expId)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = null;
 
     int respuestas = 0;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       String sql = "select count(c_expenc_id) as numRespuestas from ocap_expedientesencuestas where c_exp_id = ? and b_borrado ='N' ";
       st = con.prepareStatement(sql);
       st.setLong(1, expId);
       rs = st.executeQuery();
       if (rs.next())
         respuestas = rs.getInt("numRespuestas");
     } catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return respuestas;
   }
 
   public ArrayList OCAPBuscaRespuestasEncuesta(long expId)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = null;
     OCAPEncuestaCalidadOT datos = null;
     ArrayList listado = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       String sql = "select c_pregunta_id, d_respuesta, c_respuesta_id  from ocap_expedientesencuestas where c_exp_id = ? and b_borrado ='N' order by c_pregunta_id ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, expId);
       rs = st.executeQuery();
       listado = new ArrayList();
       while (rs.next()) {
         datos = new OCAPEncuestaCalidadOT();
         datos.setCPreguntaId(rs.getLong("c_pregunta_id"));
         if (rs.getString("d_respuesta") != null)
           datos.setDRespuesta(rs.getString("d_respuesta"));
         else
           datos.setDRespuesta(rs.getString("c_respuesta_id"));
         listado.add(datos);
       }
     } catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
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
 
   public void OCAPEliminaRespuestasEncuesta(long expId, Connection con)
     throws Exception
   {
     PreparedStatement st = null;
     int respuestas = 0;
     boolean bCrearConexion = false;
     try {
       if (con == null) {
         con = JCYLGestionTransacciones.getConnection();
         bCrearConexion = true;
       }
       String sql = "delete from ocap_expedientesencuestas where c_exp_id=? ";
 
       st = con.prepareStatement(sql);
       st.setLong(1, expId);
       st.executeUpdate();
     } catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (st != null)
         st.close();
       if (bCrearConexion)
         JCYLGestionTransacciones.close(con.getAutoCommit());
     }
   }
 
   public ArrayList listarEstadisticas(long itinerarioId, String porItinerario)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     OCAPEncuestaCalidadOT datos = null;
     ArrayList listado = null;
     try
     {
       StringBuffer sql = new StringBuffer();
 
       sql.append(" SELECT ");
 
       if (Constantes.SI.equals(porItinerario))
         sql.append(" itin.d_descripcion as descripcion,");
       if (Constantes.NO.equals(porItinerario)) {
         sql.append(" esta.d_nombre as descripcion, ");
       }
       sql.append(" exen.c_pregunta_encuesta_id,  ").append(" SUM (DECODE (exen.d_respuesta, '1', 1, 0 )) AS TOTAL_1, ").append(" SUM (DECODE (exen.d_respuesta, '2', 1, 0 )) AS TOTAL_2, ").append(" SUM (DECODE (exen.d_respuesta, '3', 1, 0 )) AS TOTAL_3, ").append(" SUM (DECODE (exen.d_respuesta, '4', 1, 0 )) AS TOTAL_4, ").append(" SUM (DECODE (exen.d_respuesta, '5', 1, 0 )) AS TOTAL_5, ").append(" SUM (DECODE (exen.d_respuesta, 'S', 1, 0 )) AS TOTAL_SI, ").append(" SUM (DECODE (exen.d_respuesta, 'S', 0, 1 )) AS TOTAL_NO ").append(" FROM ").append(" (SELECT * ").append(" FROM ocap_expedientesencuestas  ").append(" WHERE b_borrado = 'N' ").append(" AND d_respuesta IS NOT NULL ").append(" ) exen, ").append(" (SELECT * ").append(" FROM ocap_expedientescarrera ").append(" WHERE b_borrado = 'N' ");
 
       if ((Constantes.SI.equals(porItinerario)) && 
         (itinerarioId > 0L))
         sql.append(" AND c_itinerario_id = ").append(itinerarioId);
       sql.append(" ) exca ");
 
       if (Constantes.SI.equals(porItinerario)) {
         sql.append(" ,(SELECT * ").append(" FROM ocap_itinerarios ").append(" WHERE b_borrado = 'N' ");
 
         if (itinerarioId > 0L)
           sql.append(" AND c_itinerario_id = ").append(itinerarioId);
         sql.append(" ) itin ");
       }
 
       if (Constantes.NO.equals(porItinerario)) {
         sql.append(" ,(SELECT * ").append(" FROM ocap_categ_profesionales ").append(" WHERE b_borrado = 'N' ");
 
         if (itinerarioId > 0L)
           sql.append(" AND c_estatut_id = ").append(itinerarioId);
         sql.append(" ) cate, ");
         sql.append(" (SELECT * ").append(" FROM ocap_estatutario ").append(" WHERE b_borrado = 'N' ");
 
         if (itinerarioId > 0L)
           sql.append(" AND c_estatut_id = ").append(itinerarioId);
         sql.append(" ) esta ");
       }
 
       sql.append(" WHERE exen.c_exp_id = exca.c_exp_id ");
 
       if (Constantes.SI.equals(porItinerario)) {
         sql.append(" AND exca.c_itinerario_id = itin.c_itinerario_id ").append("GROUP BY exen.c_pregunta_encuesta_id, itin.d_descripcion ").append("ORDER BY itin.d_descripcion, exen.c_pregunta_encuesta_id ");
       }
 
       if (Constantes.NO.equals(porItinerario)) {
         sql.append(" AND exca.c_profesional_id = cate.c_profesional_id ").append(" AND cate.c_estatut_id = esta.c_estatut_id ").append(" GROUP BY exen.c_pregunta_encuesta_id, esta.d_nombre ").append(" ORDER BY esta.d_nombre, exen.c_pregunta_encuesta_id  ");
       }
 
       if (porItinerario == null) {
         sql.append(" GROUP BY exen.c_pregunta_encuesta_id ").append(" ORDER BY exen.c_pregunta_encuesta_id  ");
       }
 
       st = con.prepareStatement(sql.toString());
       OCAPConfigApp.logger.info(getClass().getName() + " SQL: " + sql);
       rs = st.executeQuery();
       listado = new ArrayList();
       while (rs.next()) {
         datos = new OCAPEncuestaCalidadOT();
         datos.setCPreguntaId(rs.getInt("c_pregunta_encuesta_id"));
 
         if (porItinerario != null)
           datos.setDItinerario(rs.getString("descripcion"));
         datos.setTotal1(rs.getInt("TOTAL_1"));
         datos.setTotal2(rs.getInt("TOTAL_2"));
         datos.setTotal3(rs.getInt("TOTAL_3"));
         datos.setTotal4(rs.getInt("TOTAL_4"));
         datos.setTotal5(rs.getInt("TOTAL_5"));
         datos.setTotalN(rs.getInt("TOTAL_NO"));
         datos.setTotalS(rs.getInt("TOTAL_SI"));
         listado.add(datos);
       }
     } catch (SQLException ex) {
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
 
   public ArrayList listarPreguntas(long itinerarioId, long pregId, String porItinerario)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     OCAPUsuariosOT datos = null;
     ArrayList listado = null;
     try
     {
       StringBuffer sql = new StringBuffer();
 
       sql.append(" SELECT usua.d_apellidos, usua.d_nombre, ").append(" exca.c_exp_id,  exen.d_respuesta ");
 
       if (Constantes.SI.equals(porItinerario))
         sql.append(", itin.d_descripcion as descripcion ");
       if (Constantes.NO.equals(porItinerario)) {
         sql.append(", esta.d_nombre as descripcion ");
       }
       sql.append(" FROM ").append(" (SELECT * ").append(" FROM ocap_expedientesencuestas ").append(" WHERE b_borrado = 'N' ").append(" AND c_pregunta_encuesta_id = ? ").append(" AND d_respuesta IS NOT NULL ").append(" ) exen, ").append(" (SELECT * ").append(" FROM ocap_expedientescarrera ").append(" WHERE b_borrado = 'N' ");
 
       if ((Constantes.SI.equals(porItinerario)) && 
         (itinerarioId > 0L))
         sql.append(" AND c_itinerario_id = ? ");
       sql.append(" ) exca, ").append(" (SELECT * ").append(" FROM ocap_usuarios ").append(" WHERE b_borrado = 'N' ").append(" ) usua ");
 
       if (Constantes.SI.equals(porItinerario)) {
         sql.append(" ,(SELECT * ").append(" FROM ocap_itinerarios ").append(" WHERE b_borrado = 'N' ");
 
         if (itinerarioId > 0L)
           sql.append(" AND c_itinerario_id = ? ");
         sql.append(" ) itin ");
       }
       if (Constantes.NO.equals(porItinerario)) {
         sql.append(" ,(SELECT * ").append(" FROM ocap_categ_profesionales ").append(" WHERE b_borrado = 'N' ");
 
         if (itinerarioId > 0L)
           sql.append(" AND c_estatut_id = ? ");
         sql.append(" ) cate, ");
         sql.append(" (SELECT * ").append(" FROM ocap_estatutario ").append(" WHERE b_borrado = 'N' ");
 
         if (itinerarioId > 0L)
           sql.append(" AND c_estatut_id = ? ");
         sql.append(" ) esta ");
       }
 
       sql.append(" WHERE exen.c_exp_id = exca.c_exp_id ").append(" AND exca.c_usr_id = usua.c_usr_id ");
 
       if (Constantes.SI.equals(porItinerario)) {
         sql.append(" AND exca.c_itinerario_id = itin.c_itinerario_id ");
       }
       if (Constantes.NO.equals(porItinerario)) {
         sql.append(" AND exca.c_profesional_id = cate.c_profesional_id  ").append(" AND cate.c_estatut_id = esta.c_estatut_id ");
       }
 
       st = con.prepareStatement(sql.toString());
       st.setLong(1, pregId);
       if ((porItinerario != null) && (itinerarioId != 0L)) {
         st.setLong(2, itinerarioId);
         st.setLong(3, itinerarioId);
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " SQL: " + sql);
 
       rs = st.executeQuery();
       listado = new ArrayList();
       while (rs.next()) {
         datos = new OCAPUsuariosOT();
         if (porItinerario != null)
           datos.setDItinerario(rs.getString("descripcion"));
         datos.setDApellido1(rs.getString("d_apellidos"));
         datos.setDNombre(rs.getString("d_nombre"));
         datos.setCExpId(new Long(rs.getLong("c_exp_id")));
         DecimalFormat formato = new DecimalFormat("000000");
         datos.setCodigoId("EPR" + formato.format(datos.getCExpId().longValue()));
         datos.setBContestado(rs.getString("d_respuesta") == null ? "-" : rs.getString("d_respuesta"));
 
         if (((pregId != 18) && (pregId != 24)) || (((pregId == 18) || (pregId == 24)) && (!Constantes.SI_ESP.equals(datos.getBContestado()))))
           listado.add(datos);
       }
     } catch (SQLException ex) {
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
 
   public ArrayList contarEstadisticas(long itinerarioId, String porItinerario)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     OCAPEncuestaCalidadOT datos = null;
     ArrayList listado = null;
     try
     {
       StringBuffer sql = new StringBuffer();
 
       sql.append(" SELECT ").append(" COUNT (exen.c_pregunta_encuesta_id) as total, exen.c_pregunta_encuesta_id ").append(" FROM ").append(" (SELECT * ").append(" FROM ocap_expedientesencuestas  ").append(" WHERE b_borrado = 'N' ").append(" AND d_respuesta IS NOT NULL ").append(" ) exen, ").append(" (SELECT * ").append(" FROM ocap_expedientescarrera ").append(" WHERE b_borrado = 'N' ");
 
       if ((Constantes.SI.equals(porItinerario)) && 
         (itinerarioId > 0L))
         sql.append(" AND c_itinerario_id = ").append(itinerarioId);
       sql.append(" ) exca ");
 
       if (Constantes.SI.equals(porItinerario)) {
         sql.append(" ,(SELECT * ").append(" FROM ocap_itinerarios ").append(" WHERE b_borrado = 'N' ");
 
         if (itinerarioId > 0L)
           sql.append(" AND c_itinerario_id = ").append(itinerarioId);
         sql.append(" ) itin ");
       }
 
       if (Constantes.NO.equals(porItinerario)) {
         sql.append(" ,(SELECT * ").append(" FROM ocap_categ_profesionales ").append(" WHERE b_borrado = 'N' ");
 
         if (itinerarioId > 0L)
           sql.append(" AND c_estatut_id = ").append(itinerarioId);
         sql.append(" ) cate, ");
         sql.append(" (SELECT * ").append(" FROM ocap_estatutario ").append(" WHERE b_borrado = 'N' ");
 
         if (itinerarioId > 0L)
           sql.append(" AND c_estatut_id = ").append(itinerarioId);
         sql.append(" ) esta ");
       }
 
       sql.append(" WHERE exen.c_exp_id = exca.c_exp_id ");
 
       if (Constantes.SI.equals(porItinerario)) {
         sql.append(" AND exca.c_itinerario_id = itin.c_itinerario_id ");
       }
       if (Constantes.NO.equals(porItinerario)) {
         sql.append(" AND exca.c_profesional_id = cate.c_profesional_id ").append(" AND cate.c_estatut_id = esta.c_estatut_id ");
       }
 
       sql.append(" GROUP BY exen.c_pregunta_encuesta_id ").append(" ORDER BY exen.c_pregunta_encuesta_id ");
 
       st = con.prepareStatement(sql.toString());
       OCAPConfigApp.logger.info(getClass().getName() + " SQL: " + sql);
       rs = st.executeQuery();
       listado = new ArrayList();
       while (rs.next()) {
         datos = new OCAPEncuestaCalidadOT();
         datos.setCPreguntaId(rs.getInt("c_pregunta_encuesta_id"));
 
         datos.setTotalRespuestas(rs.getInt("total"));
         listado.add(datos);
       }
     } catch (SQLException ex) {
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
 
   public int OCAPContarEncuesta(long cEncuestaId, long cItinerarioId, String porItinerario)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     OCAPEncuestaCalidadOT datos = null;
     int contador = 0;
     try
     {
       StringBuffer sql = new StringBuffer();
       sql.append(" SELECT COUNT(DISTINCT(exen.c_exp_id)) as numEncuestas ").append(" FROM ").append(" (SELECT * ").append(" FROM ocap_expedientesencuestas ").append(" WHERE b_borrado = 'N' ").append(" AND d_respuesta IS NOT NULL ").append(" ) exen, ").append(" (SELECT * ").append(" FROM ocap_expedientescarrera ").append(" WHERE b_borrado = 'N' ");
 
       if ((Constantes.SI.equals(porItinerario)) && (cItinerarioId > 0L))
         sql.append(" AND c_itinerario_id = ? ");
       if ((Constantes.NO.equals(porItinerario)) && (cItinerarioId > 0L))
         sql.append(" AND c_profesional_id = ? ");
       sql.append(" ) exca ").append(" WHERE exen.c_exp_id = exca.c_exp_id ");
 
       st = con.prepareStatement(sql.toString());
 
       st.setLong(1, cEncuestaId);
 
       if ((porItinerario != null) && (cItinerarioId > 0L)) {
         st.setLong(2, cItinerarioId);
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " SQL: " + sql);
 
       rs = st.executeQuery();
 
       if (rs.next())
         contador = rs.getInt("numEncuestas");
     }
     catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return contador;
   }
 
   public ArrayList buscarPreguntas()
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = null;
     OCAPEncuestaCalidadOT datos = null;
     OCAPEncuestaCalidadOT datosArea = null;
     OCAPEncuestaCalidadOT datosRespuesta = null;
     ArrayList listadoAreas = null;
     ArrayList listadoPreguntas = null;
     ArrayList listadoRespuestas = null;
     String dAreaAnt = "";
     long cAreaAntId = 0L;
     try {
       con = JCYLGestionTransacciones.getConnection();
       StringBuffer sql = new StringBuffer();
       sql.append("select epr.c_pregunta_id, epr.c_pregunta_padre_id, epr.d_nombre as dPregunta, epr.c_area_id, epr.a_observaciones, epr.c_respuesta_id, epr.n_ponderacion, ear.d_nombre as dArea, ").append("(select count(*) from OCAP_ENCUESTAS_PREGUNTAS where c_pregunta_padre_id = epr.c_pregunta_id) as numSubPreguntas ").append(" from OCAP_ENCUESTAS_PREGUNTAS epr, OCAP_ENCUESTAS_AREAS ear ").append(" where epr.c_area_id = ear.c_area_id ").append(" AND epr.b_borrado='N' AND ear.b_borrado='N' ").append(" order by epr.c_area_id, epr.n_orden");
 
       st = con.prepareStatement(sql.toString());
       rs = st.executeQuery();
       listadoAreas = new ArrayList();
       listadoPreguntas = new ArrayList();
       while (rs.next()) {
         if ((cAreaAntId != 0L) && (cAreaAntId != rs.getLong("c_area_id"))) {
           datosArea = new OCAPEncuestaCalidadOT();
           datosArea.setCAreaId(cAreaAntId);
           datosArea.setDArea(dAreaAnt);
           datosArea.setListaPreguntas(listadoPreguntas);
           listadoAreas.add(datosArea);
           listadoPreguntas = new ArrayList();
         }
         datos = new OCAPEncuestaCalidadOT();
         datos.setCPreguntaId(rs.getLong("c_pregunta_id"));
         datos.setDPregunta(rs.getString("dPregunta"));
         datos.setCPreguntaPadreId(rs.getLong("c_pregunta_padre_id"));
         datos.setCAreaId(rs.getLong("c_area_id"));
         datos.setDArea(rs.getString("dArea"));
         datos.setAObservaciones(rs.getString("a_observaciones"));
         datos.setCTipoRespuesta(rs.getLong("c_respuesta_id"));
         datos.setNPonderacion(rs.getFloat("n_ponderacion"));
         datos.setNumSubPreguntas(rs.getLong("numSubPreguntas"));
         listadoPreguntas.add(datos);
         dAreaAnt = rs.getString("dArea");
         cAreaAntId = rs.getLong("c_area_id");
       }
       if ((listadoPreguntas != null) && (listadoPreguntas.size() != 0)) {
         datosArea = new OCAPEncuestaCalidadOT();
         datosArea.setCAreaId(cAreaAntId);
         datosArea.setDArea(dAreaAnt);
         datosArea.setListaPreguntas(listadoPreguntas);
         listadoAreas.add(datosArea);
       }
 
       sql = new StringBuffer();
       sql.append("select c_respuesta_id, d_respuesta, n_ponderacion ").append(" from OCAP_ENCUESTAS_RESPUESTAS ").append(" where b_borrado='N' ").append(" AND c_tipo_respuesta =? ").append(" order by n_orden");
 
       for (int i = 0; i < listadoAreas.size(); i++) {
         datosArea = (OCAPEncuestaCalidadOT)listadoAreas.get(i);
         for (int j = 0; j < datosArea.getListaPreguntas().size(); j++) {
           datos = (OCAPEncuestaCalidadOT)datosArea.getListaPreguntas().get(j);
           st = con.prepareStatement(sql.toString());
           st.setLong(1, datos.getCTipoRespuesta());
           rs = st.executeQuery();
           listadoRespuestas = new ArrayList();
           while (rs.next()) {
             datosRespuesta = new OCAPEncuestaCalidadOT();
             datosRespuesta.setCRespuestaId(rs.getLong("c_respuesta_id"));
             datosRespuesta.setDRespuesta(rs.getString("d_respuesta"));
             datosRespuesta.setNPonderacionRespuesta(rs.getFloat("n_ponderacion"));
             listadoRespuestas.add(datosRespuesta);
           }
           datos.setListaRespuestas(listadoRespuestas);
         }
       }
     }
     catch (SQLException ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listadoAreas;
   }
 }

