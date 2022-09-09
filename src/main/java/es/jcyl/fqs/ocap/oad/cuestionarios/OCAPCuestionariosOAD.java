 package es.jcyl.fqs.ocap.oad.cuestionarios;
 
 import es.jcyl.cf.seguridad.util.Usuario;
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ln.cuestionarios.OCAPCuestionariosLN;
 import es.jcyl.fqs.ocap.ln.documentos.OCAPDocumentosLN;
 import es.jcyl.fqs.ocap.ln.expedientesCAs.OCAPExpedientesCAsLN;
 import es.jcyl.fqs.ocap.ln.respuestas.OCAPRespuestasLN;
 import es.jcyl.fqs.ocap.ot.areasItinerarios.OCAPAreasItinerariosOT;
 import es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT;
 import es.jcyl.fqs.ocap.ot.expedientesCAs.OCAPExpedientesCAsOT;
 import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.text.DecimalFormat;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPCuestionariosOAD
 {
   public static Logger logger = OCAPConfigApp.logger;
   private static OCAPCuestionariosOAD instance;
 
   public static OCAPCuestionariosOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPCuestionariosOAD();
     }
     return instance;
   }
 
   public void borrarRespuestasCuestionario(OCAPUsuariosOT usuOT, long idCuestionario, ArrayList listaRepeticiones, int idRepeticion, long idPadreEvidencia, JCYLUsuario jcylUsuario)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     try
     {
       String sql = "delete from ocap_repeticionescuestionarios where c_expca_id in (select c_expca_id from ocap_expedientescas where c_exp_id = ? and c_cuestionario_id= ? and n_repeticion = ? ";
 
       if (idPadreEvidencia != 0L)
         sql = sql + " and c_padre_evidencia_id = ? ";
       sql = sql + ")";
 
       st = con.prepareStatement(sql);
       st.setLong(1, usuOT.getCExpId().longValue());
       st.setLong(2, idCuestionario);
       st.setInt(3, idRepeticion);
       if (idPadreEvidencia != 0L)
         st.setLong(4, idPadreEvidencia);
       st.executeUpdate();
 
       String sql2 = "delete from ocap_expedientescas where c_exp_id = ? and c_cuestionario_id= ? and n_repeticion = ? ";
       if (idPadreEvidencia != 0L)
         sql2 = sql2 + " and c_padre_evidencia_id = ? ";
       st = con.prepareStatement(sql2);
       st.setLong(1, usuOT.getCExpId().longValue());
       st.setLong(2, idCuestionario);
       st.setInt(3, idRepeticion);
       if (idPadreEvidencia != 0L)
         st.setLong(4, idPadreEvidencia);
       st.executeUpdate();
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
   }
 
   public void borrarRespuestasExpediente(long cExpId)
     throws SQLException
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     try
     {
       String sql = "delete from ocap_repeticionescuestionarios where c_expca_id in (select c_expca_id from ocap_expedientescas where c_exp_id = ?)";
 
       st = con.prepareStatement(sql);
       st.setLong(1, cExpId);
       st.executeUpdate();
 
       String sql2 = "delete from ocap_expedientescas where c_exp_id = ?";
       st = con.prepareStatement(sql2);
       st.setLong(1, cExpId);
       st.executeUpdate();
     }
     catch (SQLException ex) {
       throw ex;
     } finally {
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
   }
 
   public ArrayList buscarPreguntasCuestionarioPorUsuario(OCAPUsuariosOT usuOT, long idCuestionario, ArrayList listaRepeticiones, int idRepeticion, boolean bSimulacion, boolean bUsuario, int nEvidencia, long idPadreEvidencia, JCYLUsuario jcylUsuario)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     ArrayList listado = null;
     ArrayList listadoAuxiliar = null;
     ArrayList listadoEvidenciaDocumentales = null;
     OCAPCuestionariosOT datos = null;
     OCAPCuestionariosOT eviOT = null;
     OCAPExpedientesCAsOT expedienteCAOT = null;
     OCAPExpedientesCAsLN expedienteCALN = null;
     OCAPRespuestasLN respuestasLN = null;
     boolean hacerCommit = false;
     try
     {
       if (con.getAutoCommit()) {
         con.setAutoCommit(false);
         hacerCommit = true;
       }
 
       String sql = "SELECT oc.D_NOMBRE_LARGO, oc.B_OBLIGATORIO, oc.C_CUESTIONARIO_ID, oc.C_CUESTIONARIO_PADRE_IDP,  oc.C_CUESTIONARIO_INTERMEDIO_IDP, oc.B_INDICADORES,  oai.D_NOMBRE as D_AREA, oai.D_NOMBRE_LARGO as D_AREA_LARGO, oc.D_NOMBRE,  opc.C_PREGUNTA_ID, op.D_NOMBRE as D_PREGUNTA, op.D_OBSERVACIONES, op.D_TEXTO_ELEMENTOS,  op.N_ELEMENTOS, op.N_SUB_ELEMENTOS, op.N_NIVEL, op.B_CORTO, op.B_PUNTUAR_TODO,  op.n_min_elem_rellenos, op.n_min_subelem_opcionales, op.b_indicadores as preguntaIndicadores,  oc.b_pondera_formula, oc.n_preguntas as nPreguntasFormula, oc.n_creditos,  (select icu.n_creditos from ITCP_CUESTIONARIOS icu where icu.c_cuestionario_id = " + idPadreEvidencia + " ) as n_creditos_padre_evidencia, " + " (select distinct c_tipo_ponderacion from itcp_evidencias_cuestionarios where c_evidencia_id = " + idCuestionario + "  and n_evidencia= " + nEvidencia + " and c_cuestionario_id in (select c_cuestionario_id from ITCP_CUESTIONARIOS where c_manual_evaluacion_id=" + usuOT.getCItinerarioId() + ")) as tipoevidencia, " + " (select c_evidencia_id from itcp_evidencias_cuestionarios ecu where c_cuestionario_id =  opc.C_CUESTIONARIO_ID AND EXISTS (SELECT c_cuestionario_id FROM ITCP_PREGUNTAS_CUESTIONARIOS WHERE c_cuestionario_id=ecu.c_evidencia_id and rownum <= 1)) as c_evidencia_id, " + " (select d_nombre from itcp_tipos_preguntas where c_tipo_pregunta_id =  op.C_TIPO_PREGUNTA_ID) as c_tipo_pregunta, " + " (select d_nombre from itcp_tipos_numeracion where c_tipo_numeracion_id =  op.C_TIPO_NUMERACION_ID) as b_numeracion " + " FROM ITCP_PREGUNTAS_CUESTIONARIOS opc, ITCP_CUESTIONARIOS oc, itcp_preguntas op, itcp_areas_manual oai " + " WHERE opc.C_CUESTIONARIO_ID = " + idCuestionario + " AND opc.C_CUESTIONARIO_ID = oc.C_CUESTIONARIO_ID " + " AND opc.C_PREGUNTA_ID = op.C_PREGUNTA_ID " + " AND oc.C_AREA_ID = oai.C_AREA_ID " + " AND oc.B_BORRADO='N' ";
 
       sql = sql + " ORDER BY opc.n_orden ";
 
       st = con.prepareStatement(sql);
       rs = st.executeQuery();
 
       listadoAuxiliar = new ArrayList();
       while (rs.next()) {
         datos = new OCAPCuestionariosOT();
         datos.setCCuestionarioId(idCuestionario);
         datos.setDNombreLargo(rs.getString("D_NOMBRE_LARGO"));
         datos.setBObligatorio(rs.getString("B_OBLIGATORIO"));
         datos.setDArea(rs.getString("D_AREA"));
         datos.setDAreaLargo(rs.getString("D_AREA_LARGO"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setCPreguntaId(rs.getLong("C_PREGUNTA_ID"));
 
         datos.setDPregunta(rs.getString("D_PREGUNTA"));
         datos.setDObservaciones(rs.getString("D_OBSERVACIONES"));
         datos.setDTextoElementos(rs.getString("D_TEXTO_ELEMENTOS") == null ? "" : rs.getString("D_TEXTO_ELEMENTOS"));
         datos.setCTipoPregunta(rs.getString("C_TIPO_PREGUNTA"));
         datos.setNElementos(rs.getInt("N_ELEMENTOS"));
         datos.setNSubElementos(rs.getInt("N_SUB_ELEMENTOS"));
         datos.setNNivel(rs.getInt("N_NIVEL"));
         datos.setBNumeracion(rs.getString("B_NUMERACION"));
         datos.setBCorto(rs.getString("B_CORTO"));
         datos.setBPuntuarTodo(rs.getString("B_PUNTUAR_TODO"));
         datos.setCCuestionarioId(rs.getLong("C_CUESTIONARIO_ID"));
         datos.setCCuestionarioPadreId(rs.getLong("C_CUESTIONARIO_PADRE_IDP"));
         datos.setCCuestionarioIntermedioId(rs.getLong("C_CUESTIONARIO_INTERMEDIO_IDP"));
         datos.setBIndicadores(rs.getString("B_INDICADORES"));
         datos.setCEvidenciaId(rs.getLong("c_evidencia_id"));
 
         datos.setCTipoEvidencia(rs.getString("tipoevidencia"));
         datos.setBPonderaFormula(rs.getString("b_pondera_formula"));
         datos.setNPreguntasFormula(rs.getInt("nPreguntasFormula"));
         if ((rs.getFloat("n_creditos") == 0.0F) && (rs.getFloat("n_creditos_padre_evidencia") != 0.0F))
           datos.setNCreditos(rs.getFloat("n_creditos_padre_evidencia"));
         else
           datos.setNCreditos(rs.getFloat("n_creditos"));
         datos.setNMinElemRellenos(rs.getInt("n_min_elem_rellenos"));
         datos.setNMinSubElemOpcionales(rs.getInt("n_min_subelem_opcionales"));
         datos.setBPregIndicadores(rs.getString("preguntaIndicadores"));
         listadoAuxiliar.add(datos);
       }
 
       sql = " SELECT ecu.c_evidencia_id, ecu.n_evidencia, cue.n_creditos FROM itcp_evidencias_cuestionarios ecu, itcp_cuestionarios cue WHERE ecu.c_cuestionario_id =  ? AND ecu.c_evidencia_id = cue.c_cuestionario_id AND NOT EXISTS (SELECT c_cuestionario_id FROM itcp_preguntas_cuestionarios WHERE c_cuestionario_id= ecu.c_evidencia_id and rownum <= 1) ";
       st = con.prepareStatement(sql);
 
       expedienteCALN = new OCAPExpedientesCAsLN(jcylUsuario);
       respuestasLN = new OCAPRespuestasLN(jcylUsuario);
       listado = new ArrayList();
       for (int i = 0; i < listadoAuxiliar.size(); i++) {
         datos = (OCAPCuestionariosOT)listadoAuxiliar.get(i);
         st.setLong(1, datos.getCCuestionarioId());
         rs = st.executeQuery();
         listadoEvidenciaDocumentales = new ArrayList();
         while (rs.next()) {
           eviOT = new OCAPCuestionariosOT();
           eviOT.setCEvidenciaId(rs.getLong("c_evidencia_id"));
           eviOT.setNEvidencia(rs.getString("n_evidencia"));
           eviOT.setNCreditos(rs.getFloat("n_creditos"));
           listadoEvidenciaDocumentales.add(eviOT);
         }
         datos.setListaEviDocumentales(listadoEvidenciaDocumentales);
         expedienteCAOT = new OCAPExpedientesCAsOT();
         expedienteCAOT.setCCuestionarioId(datos.getCCuestionarioId());
         expedienteCAOT.setCPreguntaId(datos.getCPreguntaId());
         expedienteCAOT.setCExpId(usuOT.getCExpId().longValue());
         expedienteCAOT.setNRepeticion(idRepeticion);
         expedienteCAOT.setCPadreEvidenciaId(idPadreEvidencia);
         if (!Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol()))
           datos.setListaRespuestas(expedienteCALN.buscarOCAPExpedientesCAs(expedienteCAOT, listaRepeticiones, bUsuario, bSimulacion));
         else
           datos.setListaRespuestas(expedienteCALN.buscarOCAPExpedientesCAs(expedienteCAOT, listaRepeticiones, false, bSimulacion));
         datos.setListaPosiblesRespuestas(respuestasLN.buscarOCAPRespuestasPorCuestionarioPregunta(datos.getCCuestionarioId(), datos.getCPreguntaId()));
         listado.add(datos);
       }
 
       if (hacerCommit)
         con.setAutoCommit(true);
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
 
   public ArrayList listadoOCAPCuestionariosPorItinerario(OCAPUsuariosOT usuOT, JCYLUsuario jcylUsuario)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = null;
     ArrayList listado = null;
     ArrayList listadoAuxiliar = null;
     ArrayList listadoResultado = null;
     OCAPCuestionariosOT datos = null;
     OCAPAreasItinerariosOT areasItinerariosOT = null;
     OCAPCuestionariosLN cuestionarioLN = null;
     OCAPDocumentosLN docuLN = null;
     boolean hacerCommit = false;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
 
       if (con.getAutoCommit()) {
         con.setAutoCommit(false);
         hacerCommit = true;
       }
 
       docuLN = new OCAPDocumentosLN();
       cuestionarioLN = new OCAPCuestionariosLN(jcylUsuario);
 
       String sql = "select distinct(ama.C_AREA_ID), ama.D_NOMBRE, ama.D_NOMBRE_LARGO, ama.n_orden,  (select sum(ocu.n_creditos*ocu.n_repeticiones) from itcp_cuestionarios ocu      where ocu.c_area_id=ama.c_area_id and ocu.c_cuestionario_padre_idp=0 and      ocu.c_cuestionario_id not in (select c_cuestionario_intermedio_idp from itcp_cuestionarios aux where aux.c_cuestionario_intermedio_idp=ocu.c_cuestionario_id)) as creditosArea  from itcp_areas_manual ama,  itcp_cuestionarios oc  where oc.C_AREA_ID = ama.C_AREA_ID  AND ama.B_BORRADO='N' AND oc.B_BORRADO='N' ";
 
       sql = sql + " AND oc.c_manual_evaluacion_id = '" + usuOT.getCItinerarioId() + "'";
       sql = sql + " ORDER BY ama.n_orden ";
 
       st = con.prepareStatement(sql);
       rs = st.executeQuery();
 
       listadoAuxiliar = new ArrayList();
       while (rs.next()) {
         areasItinerariosOT = new OCAPAreasItinerariosOT();
         areasItinerariosOT.setCItinerarioId(Long.valueOf(usuOT.getCItinerarioId()));
         areasItinerariosOT.setCAreaId(Long.valueOf(rs.getLong("C_AREA_ID")));
         areasItinerariosOT.setDNombre(rs.getString("D_NOMBRE"));
         areasItinerariosOT.setDNombreLargo(rs.getString("D_NOMBRE_LARGO"));
         areasItinerariosOT.setNCreditosArea((float)(Math.rint(rs.getFloat("creditosArea") * 100.0F) / 100.0D));
         listadoAuxiliar.add(areasItinerariosOT);
       }
 
       String sql2 = "SELECT oc.c_cuestionario_id, oc.d_nombre_largo, oc.d_nombre,  oc.n_repeticiones,  (select count(*) from itcp_cuestionarios ocu where ocu.C_CUESTIONARIO_PADRE_IDP = oc.C_CUESTIONARIO_ID) as nSubCuestionarios,  (select count(c_expca_id) from ocap_expedientescas oeca, itcp_cuestionarios oc1  where oeca.C_CUESTIONARIO_ID = oc1.C_CUESTIONARIO_ID  AND oeca.C_EXP_ID= '" + usuOT.getCExpId().longValue() + "') as nRespuestas, " + " (select count(*) from itcp_preguntas_cuestionarios opc where opc.C_CUESTIONARIO_ID = oc.c_cuestionario_id) as nPreguntas " + " FROM itcp_cuestionarios oc " + " WHERE oc.b_borrado = 'N' " + " AND oc.c_area_id = ? " + " AND oc.c_manual_evaluacion_id = ? " + " AND oc.c_cuestionario_padre_idp=0 " + " AND oc.c_cuestionario_id not in (select c_cuestionario_intermedio_idp from itcp_cuestionarios where c_cuestionario_intermedio_idp is not null and c_manual_evaluacion_id = ?) " + " order by oc.n_orden ";
 
       listadoResultado = new ArrayList();
 
       for (int i = 0; i < listadoAuxiliar.size(); i++) {
         areasItinerariosOT = (OCAPAreasItinerariosOT)listadoAuxiliar.get(i);
         st = con.prepareStatement(sql2);
         st.setLong(1, areasItinerariosOT.getCAreaId().longValue());
         st.setLong(2, usuOT.getCItinerarioId());
         st.setLong(3, usuOT.getCItinerarioId());
         rs = st.executeQuery();
         listado = new ArrayList();
         while (rs.next()) {
           datos = new OCAPCuestionariosOT();
           datos.setCCuestionarioId(rs.getLong("c_cuestionario_id"));
           datos.setDNombreLargo(rs.getString("D_NOMBRE_LARGO"));
           datos.setDNombre(rs.getString("D_NOMBRE"));
           datos.setNRepeticiones(Integer.valueOf(rs.getInt("n_repeticiones")));
           datos.setNSubCuestionarios(Integer.valueOf(rs.getInt("nSubCuestionarios")));
           datos.setNPreguntas(Integer.valueOf(rs.getInt("nPreguntas")));
 
           datos = detalleCuestionario(usuOT, datos, 0, jcylUsuario);
           if (cuestionarioLN.estaContestado(datos, usuOT, 0, new ArrayList(), 0L, jcylUsuario)) {
             datos.setBContestado(Constantes.SI);
             datos.setBParcialmenteContestado(Constantes.NO);
           } else {
             datos.setBContestado(Constantes.NO);
             if (cuestionarioLN.estaParcialmenteContestado(datos, usuOT, 0, new ArrayList(), 0L, jcylUsuario))
               datos.setBParcialmenteContestado(Constantes.SI);
             else datos.setBParcialmenteContestado(Constantes.NO);
 
           }
 
           datos.setBMostrar(Constantes.NO);
 
           ArrayList listadoDocumentos = docuLN.buscarDocumentosCuestionario(usuOT.getCExpId().longValue(), datos.getCCuestionarioId(), 0L);
           datos.setListaDocumentos(listadoDocumentos);
 
           listado.add(datos);
         }
 
         areasItinerariosOT.setListaCuestionarios(listado);
         listadoResultado.add(areasItinerariosOT);
       }
 
       if (hacerCommit)
         con.setAutoCommit(true);
     }
     catch (SQLException ex)
     {
       throw ex;
     } catch (Exception ex) {
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listadoResultado;
   }
 
   public ArrayList listadoOCAPCuestionariosAreaDocumentos(Long cExpId, Long cItinerarioId, String cAreaId)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     ArrayList listado = null;
     Connection con = null;
     OCAPCuestionariosOT cuestOT = null;
     StringBuffer sql = new StringBuffer();
     try
     {
       con = JCYLGestionTransacciones.getConnection();
 
       sql.append("select cues.c_cuestionario_id, cues.d_nombre, cues.d_nombre_largo, evcu.c_evidencia_id, evcu.n_evidencia, ").append(" (select d_nombre from itcp_areas_manual arma where arma.c_area_id = cues.c_area_id) as d_area ").append(" from itcp_cuestionarios cues, itcp_evidencias_cuestionarios evcu ").append("where cues.c_cuestionario_id = evcu.c_cuestionario_id ").append("  and cues.c_manual_evaluacion_id = ? ").append("  and cues.c_area_id = ? ").append("  and cues.b_borrado = 'N' ").append("  and evcu.c_evidencia_id in (select c_cuestionario_id from itcp_cuestionarios where b_contestar_usuario = 'N' and b_borrado = 'N') ").append("order by d_nombre");
 
       int contador = 1;
       st = con.prepareStatement(sql.toString());
       st.setLong(contador++, cItinerarioId.longValue());
       st.setString(contador++, cAreaId);
 
       rs = st.executeQuery();
 
       listado = new ArrayList();
       while (rs.next()) {
         cuestOT = new OCAPCuestionariosOT();
 
         cuestOT.setCCuestionarioId(rs.getLong("C_CUESTIONARIO_ID"));
         cuestOT.setDNombre(rs.getString("D_NOMBRE"));
         cuestOT.setDNombreLargo(rs.getString("D_NOMBRE_LARGO"));
         cuestOT.setDArea(rs.getString("D_AREA"));
         cuestOT.setCEvidenciaId(rs.getLong("C_EVIDENCIA_ID"));
         cuestOT.setNEvidencia(rs.getString("N_EVIDENCIA"));
         listado.add(cuestOT);
       }
     }
     catch (SQLException e) {
       throw e;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 
   public OCAPCuestionariosOT detalleCuestionario(OCAPUsuariosOT usuOT, OCAPCuestionariosOT cuestOT, int idRepe, JCYLUsuario jcylUsuario)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     OCAPCuestionariosOT datos = null;
     OCAPCuestionariosOT eviOT = null;
     ArrayList listado = null;
     ArrayList listadoEvidenciaDocumentales = null;
     StringBuffer sqlSelect = new StringBuffer();
     DecimalFormat formato = new DecimalFormat("00");
     boolean hacerCommit = false;
     try
     {
       if (con.getAutoCommit()) {
         con.setAutoCommit(false);
         hacerCommit = true;
       }
 
       String sql = "select oc.C_AREA_ID, oc.C_MANUAL_EVALUACION_ID, oc.D_NOMBRE, oc.D_NOMBRE_LARGO, oc.B_OBLIGATORIO, oc.B_CONTESTAR_USUARIO, oc.B_INDICADORES, oc.B_SIMULACION, oc.D_MENSAJE, oc.c_PLANTILLA_ID, oc.B_PONDERA_FORMULA, oc.B_MOSTRAR_BTN_IMPRIMIR,oc.B_MOSTRAR_BTN_VOLVER,oc.N_AUTOPONDERACION_MAX, (select oai.d_nombre from itcp_areas_manual oai where oai.C_AREA_ID = oc.C_AREA_ID) as D_AREA,  (select oai.d_nombre_largo from itcp_areas_manual oai where oai.C_AREA_ID = oc.C_AREA_ID) as D_AREA_LARGO,  (select c_evidencia_id from itcp_evidencias_cuestionarios oc_evi where oc_evi.c_cuestionario_id =  oc.c_cuestionario_id AND EXISTS (SELECT c_cuestionario_id FROM itcp_preguntas_cuestionarios WHERE c_cuestionario_id=oc_evi.c_evidencia_id and rownum <= 1)) as c_evidencia_id,  (select n_evidencia from itcp_evidencias_cuestionarios oc_evi where oc_evi.c_cuestionario_id =  oc.c_cuestionario_id AND EXISTS (SELECT c_cuestionario_id FROM itcp_preguntas_cuestionarios WHERE c_cuestionario_id=oc_evi.c_evidencia_id and rownum <= 1)) as n_evidencia,  (select d_observaciones from itcp_evidencias_cuestionarios oc_evi where oc_evi.c_cuestionario_id =  oc.c_cuestionario_id AND EXISTS (SELECT c_cuestionario_id FROM itcp_preguntas_cuestionarios WHERE c_cuestionario_id=oc_evi.c_evidencia_id and rownum <= 1)) as d_observaciones_evidencia,  oc.N_REPETICIONES, oc.D_REPETICION, oc.C_CUESTIONARIO_PADRE_IDP,  oc.C_CUESTIONARIO_INTERMEDIO_IDP, oc.B_SUBDIVISION, ";
 
       if (idRepe == 0) {
         sql = sql + " (select icu.n_creditos*icu.n_repeticiones from ITCP_CUESTIONARIOS icu where icu.c_cuestionario_id = " + cuestOT.getCPadreEvidenciaId() + " ) as N_CREDITOS_PADRE_EVI, ";
         sql = sql + "(oc.N_CREDITOS * oc.N_REPETICIONES) as N_CREDITOS, ";
       } else {
         sql = sql + " (select icu.n_creditos from ITCP_CUESTIONARIOS icu where icu.c_cuestionario_id = " + cuestOT.getCPadreEvidenciaId() + " ) as N_CREDITOS_PADRE_EVI, ";
         sql = sql + "oc.N_CREDITOS, ";
       }
 
       sql = sql + " DBMS_LOB.SUBSTR(oc.D_PONDERACION) as D_PONDERACION,  oc.n_max_hijoscontestados,  (select count(*) from itcp_cuestionarios ocu where ocu.C_CUESTIONARIO_PADRE_IDP = oc.C_CUESTIONARIO_ID AND ocu.B_BORRADO='N') as nSubCuestionarios,  (SELECT COUNT(*) FROM ocap_estadoscuestionario escu WHERE c_estado='F' AND c_exp_id = " + usuOT.getCExpId().longValue() + " AND c_cuestionario_id IN (select c_cuestionario_id from itcp_cuestionarios ocu where ocu.c_cuestionario_padre_idp =oc.c_cuestionario_id AND ocu.b_borrado = 'N')) AS nSubcuestionariosFinalizados," + " (select count(*) from itcp_preguntas_cuestionarios opc where opc.C_CUESTIONARIO_ID = oc.c_cuestionario_id ) as nPreguntas, " + " (select count(*) from itcp_preguntas_cuestionarios opc, itcp_preguntas op where opc.C_CUESTIONARIO_ID = oc.c_cuestionario_id AND opc.c_pregunta_id = op.c_pregunta_id AND op.C_TIPO_PREGUNTA_ID not in (1, 2)) as nPreguntasContestables ";
 
       sql = sql + ", (select b_acuerdo from ocap_creditoscuestionarios where c_exp_id=" + usuOT.getCExpId().longValue() + " and c_cuestionario_id=" + cuestOT.getCCuestionarioId() + " and b_borrado='N' and b_acuerdo is not null AND ROWNUM <= 1) as bAcuerdoEvaluador ";
       sql = sql + ", (select b_acuerdo2e from ocap_creditoscuestionarios where c_exp_id=" + usuOT.getCExpId().longValue() + " and c_cuestionario_id=" + cuestOT.getCCuestionarioId() + " and b_borrado='N' and b_acuerdo2e is not null AND ROWNUM <= 1) as bAcuerdoEvaluador2 ";
       sql = sql + " from ITCP_CUESTIONARIOS oc  where oc.B_BORRADO='N'  AND oc.C_CUESTIONARIO_ID='" + cuestOT.getCCuestionarioId() + "'";
 
       st = con.prepareStatement(sql);
       OCAPConfigApp.logger.info(getClass().getName() + " ID CUESTIONARIO: " + cuestOT.getCCuestionarioId());
       rs = st.executeQuery();
 
       if (rs.next()) {
         cuestOT.setCCuestionarioId(cuestOT.getCCuestionarioId());
         cuestOT.setCAreaId(rs.getLong("C_AREA_ID"));
         cuestOT.setDArea(rs.getString("D_AREA"));
         cuestOT.setDAreaLargo(rs.getString("D_AREA_LARGO"));
         cuestOT.setCEvidenciaId(rs.getLong("C_EVIDENCIA_ID"));
         cuestOT.setNEvidencia(formato.format(rs.getLong("N_EVIDENCIA")));
         cuestOT.setDObservacionesEvidencia(rs.getString("d_observaciones_evidencia"));
 
         cuestOT.setCItinerarioId(rs.getLong("C_MANUAL_EVALUACION_ID"));
         cuestOT.setDNombre(rs.getString("D_NOMBRE"));
         cuestOT.setDNombreLargo(rs.getString("D_NOMBRE_LARGO"));
         cuestOT.setBObligatorio(rs.getString("B_OBLIGATORIO"));
         cuestOT.setBContestarUsuario(rs.getString("B_CONTESTAR_USUARIO"));
         cuestOT.setNRepeticiones(new Integer(rs.getInt("N_REPETICIONES")));
         cuestOT.setDRepeticion(rs.getString("D_REPETICION"));
         cuestOT.setNSubCuestionarios(new Integer(rs.getInt("nSubCuestionarios")));
         cuestOT.setNPreguntas(new Integer(rs.getInt("nPreguntas")));
         cuestOT.setNPreguntasContestables(new Integer(rs.getInt("nPreguntasContestables")));
         if ((cuestOT.getCPadreEvidenciaId() != 0L) && (rs.getFloat("N_CREDITOS") == 0.0F))
           cuestOT.setNCreditos(rs.getFloat("N_CREDITOS_PADRE_EVI"));
         else
           cuestOT.setNCreditos(rs.getFloat("N_CREDITOS") + rs.getFloat("N_CREDITOS_PADRE_EVI"));
         cuestOT.setCCuestionarioPadreId(rs.getLong("C_CUESTIONARIO_PADRE_IDP"));
         cuestOT.setCCuestionarioIntermedioId(rs.getLong("C_CUESTIONARIO_INTERMEDIO_IDP"));
         cuestOT.setDMensaje(rs.getString("D_MENSAJE"));
         cuestOT.setCPlantillaId(rs.getInt("c_PLANTILLA_ID"));
         cuestOT.setBPonderaFormula(rs.getString("B_PONDERA_FORMULA"));
         cuestOT.setDPonderacion(rs.getString("D_PONDERACION"));
         cuestOT.setBIndicadores(rs.getString("B_INDICADORES"));
         cuestOT.setBSubdivision(rs.getString("B_SUBDIVISION"));
         if ((rs.getString("bAcuerdoEvaluador") != null) && (!"".equals(rs.getString("bAcuerdoEvaluador"))))
           cuestOT.setBEvaluado(Constantes.SI);
         else cuestOT.setBEvaluado(Constantes.NO);
         if ((rs.getString("bAcuerdoEvaluador2") != null) && (!"".equals(rs.getString("bAcuerdoEvaluador2"))))
           cuestOT.setBAnalizado(Constantes.SI);
         else cuestOT.setBAnalizado(Constantes.NO);
         cuestOT.setBSimulacion(rs.getString("B_SIMULACION"));
         cuestOT.setNMaxHijosContestados(rs.getInt("n_max_hijoscontestados"));
         cuestOT.setNHijosContestados(rs.getInt("nSubcuestionariosFinalizados"));
         cuestOT.setBMostrarBtnImprimir(rs.getString("B_MOSTRAR_BTN_IMPRIMIR"));
         cuestOT.setbMostrarBtnVolver(rs.getString("B_MOSTRAR_BTN_VOLVER"));
         cuestOT.setNAutoponderacionMax(new Double(rs.getFloat("N_AUTOPONDERACION_MAX")));
       }
 
       if (rs != null)
         rs.close();
       if (st != null) {
         st.close();
       }
 
       sql = " SELECT ecu.c_evidencia_id, ecu.n_evidencia, cue.n_creditos, ecu.d_observaciones FROM itcp_evidencias_cuestionarios ecu, itcp_cuestionarios cue WHERE ecu.c_cuestionario_id =  ? AND ecu.c_evidencia_id = cue.c_cuestionario_id AND NOT EXISTS (SELECT c_cuestionario_id FROM itcp_preguntas_cuestionarios WHERE c_cuestionario_id= ecu.c_evidencia_id and rownum <= 1) ";
       st = con.prepareStatement(sql);
       st.setLong(1, cuestOT.getCCuestionarioId());
       rs = st.executeQuery();
       listadoEvidenciaDocumentales = new ArrayList();
       while (rs.next()) {
         eviOT = new OCAPCuestionariosOT();
         eviOT.setCEvidenciaId(rs.getLong("c_evidencia_id"));
         eviOT.setNEvidencia(rs.getString("n_evidencia"));
         eviOT.setNCreditos(rs.getFloat("n_creditos"));
         if ((rs.getString("d_observaciones") != null) && (!"".equals(rs.getString("d_observaciones"))))
           cuestOT.setDObservacionesEvidencia(cuestOT.getDObservacionesEvidencia() + "<BR />" + rs.getString("d_observaciones"));
         listadoEvidenciaDocumentales.add(eviOT);
       }
       cuestOT.setListaEviDocumentales(listadoEvidenciaDocumentales);
 
       if (rs != null)
         rs.close();
       if (st != null) {
         st.close();
       }
 
       if (cuestOT.getNSubCuestionarios().intValue() > 0) {
         sql = "select oc.C_CUESTIONARIO_ID from itcp_cuestionarios oc where oc.C_CUESTIONARIO_PADRE_IDP ='" + cuestOT.getCCuestionarioId() + "' AND oc.B_BORRADO='N' order by oc.n_orden ";
         st = con.prepareStatement(sql);
         rs = st.executeQuery();
 
         listado = new ArrayList();
         while (rs.next()) {
           datos = new OCAPCuestionariosOT();
           datos.setCCuestionarioId(rs.getLong("C_CUESTIONARIO_ID"));
           datos = detalleCuestionario(usuOT, datos, idRepe, jcylUsuario);
           listado.add(datos);
         }
         cuestOT.setListaSubCuestionarios(listado);
       }
 
       if (rs != null)
         rs.close();
       if (st != null) {
         st.close();
       }
       sqlSelect.append("SELECT  SUM(n_creditos) AS n_creditos from ( ").append("   SELECT CASE WHEN (cons.cuenta = 0) ").append("      THEN (SELECT sum(n_creditos) FROM ocap_creditoscuestionarios WHERE c_cuestionario_id = ? AND c_exp_id = ? ");
 
       if (cuestOT.getCPadreEvidenciaId() != 0L)
         sqlSelect.append(" AND c_padre_evidencia_id = ? ");
       if (idRepe != 0)
         sqlSelect.append(" AND n_repeticion = '" + idRepe + "' ");
       sqlSelect.append(")").append("      ELSE cons.suma ").append("   END n_creditos ").append("   FROM (SELECT count(*) as cuenta, sum(n_creditos) as suma from ocap_creditoscuestionarios ").append("   WHERE c_exp_id = ? AND c_padre_evidencia_id = ? ");
 
       if (cuestOT.getBVerPonderacion())
         sqlSelect.append(" AND 1=0 ");
       sqlSelect.append("      AND c_cuestionario_id in ( ").append("                  SELECT c_evidencia_id FROM itcp_evidencias_cuestionarios WHERE c_cuestionario_id = ?)) cons ").append(" UNION ALL").append("   SELECT  CASE WHEN (cons.cuenta = 0) ").append("      THEN (SELECT sum(n_creditos) FROM ocap_creditoscuestionarios WHERE c_exp_id = ? AND c_cuestionario_id IN( ").append("\t   \t\t SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_cuestionario_padre_idp = ?) ");
 
       if (idRepe != 0)
         sqlSelect.append(" AND n_repeticion = '" + idRepe + "' ");
       sqlSelect.append(")").append("      ELSE cons.suma ").append("   END n_creditos ").append("   FROM (select count(*) as cuenta, sum(n_creditos) as suma FROM ocap_creditoscuestionarios WHERE c_exp_id = ? ");
 
       if (cuestOT.getBVerPonderacion())
         sqlSelect.append(" AND 1=0 ");
       sqlSelect.append("   AND c_padre_evidencia_id IN(SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_cuestionario_padre_idp = ? )").append("   AND c_cuestionario_id in ( ").append("               SELECT c_evidencia_id FROM itcp_evidencias_cuestionarios WHERE c_cuestionario_id IN ( ").append("                  SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_cuestionario_padre_idp = ?))) cons ").append(" UNION ALL").append("   SELECT  CASE WHEN (cons.cuenta = 0) ").append("      THEN (SELECT sum(n_creditos) FROM ocap_creditoscuestionarios WHERE c_exp_id = ? AND c_cuestionario_id IN( ").append("\t   \t\t SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_cuestionario_padre_idp IN ( ").append("                  SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_cuestionario_padre_idp = ?))");
 
       if (idRepe != 0)
         sqlSelect.append(" AND n_repeticion = '" + idRepe + "' ");
       sqlSelect.append(")").append("      ELSE cons.suma ").append("   END n_creditos ").append("   FROM (SELECT count(*) as cuenta, sum(n_creditos) as suma FROM ocap_creditoscuestionarios WHERE c_exp_id = ? ");
 
       if (cuestOT.getBVerPonderacion())
         sqlSelect.append(" AND 1=0 ");
       sqlSelect.append("   AND c_padre_evidencia_id IN(SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_cuestionario_padre_idp IN ( ").append("      SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_cuestionario_padre_idp = ? ) )").append("   AND c_cuestionario_id IN ( ").append("               SELECT c_evidencia_id FROM itcp_evidencias_cuestionarios WHERE c_cuestionario_id IN ( ").append("                  SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_cuestionario_padre_idp IN( ").append("                     SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_cuestionario_padre_idp = ?)))) cons )");
 
       st = con.prepareStatement(sqlSelect.toString());
 
       int cont = 1;
       st.setLong(cont++, cuestOT.getCCuestionarioId());
       st.setLong(cont++, usuOT.getCExpId().longValue());
       if (cuestOT.getCPadreEvidenciaId() != 0L)
         st.setLong(cont++, cuestOT.getCPadreEvidenciaId());
       st.setLong(cont++, usuOT.getCExpId().longValue());
       st.setLong(cont++, cuestOT.getCCuestionarioId());
       st.setLong(cont++, cuestOT.getCCuestionarioId());
       st.setLong(cont++, usuOT.getCExpId().longValue());
       st.setLong(cont++, cuestOT.getCCuestionarioId());
       st.setLong(cont++, usuOT.getCExpId().longValue());
       st.setLong(cont++, cuestOT.getCCuestionarioId());
       st.setLong(cont++, cuestOT.getCCuestionarioId());
       st.setLong(cont++, usuOT.getCExpId().longValue());
       st.setLong(cont++, cuestOT.getCCuestionarioId());
       st.setLong(cont++, usuOT.getCExpId().longValue());
       st.setLong(cont++, cuestOT.getCCuestionarioId());
       st.setLong(cont++, cuestOT.getCCuestionarioId());
 
       rs = st.executeQuery();
       if (rs.next())
       {
         cuestOT.setNCreditosConseguidos(rs.getFloat("n_creditos"));
       }
       else {
         cuestOT.setNCreditosConseguidos(0.0F);
       }
       if (rs != null)
         rs.close();
       if (st != null) {
         st.close();
       }
       sqlSelect = new StringBuffer();
       sqlSelect.append("select sum(n_puntuacion) as n_puntuacion from ( ").append("   select case when (cons.cuenta = 0) ").append("      then (select sum(n_puntuacion) from OCAP_CREDITOSCUESTIONARIOS where c_cuestionario_id = ? and c_exp_id = ? ");
 
       if (cuestOT.getCPadreEvidenciaId() != 0L)
         sqlSelect.append(" AND c_padre_evidencia_id = ? ");
       if (idRepe != 0)
         sqlSelect.append(" AND n_repeticion = '" + idRepe + "' ");
       sqlSelect.append(")").append("      else cons.suma ").append("   end n_puntuacion ").append("   from (select count(*) as cuenta, sum(n_puntuacion) as suma from OCAP_CREDITOSCUESTIONARIOS where c_exp_id = ? AND c_padre_evidencia_id = ? ");
 
       if (cuestOT.getBVerPonderacion())
         sqlSelect.append(" AND 1=0 ");
       sqlSelect.append("   and c_cuestionario_id in ( ").append("                  select c_evidencia_id from ITCP_EVIDENCIAS_CUESTIONARIOS where c_cuestionario_id = ?)) cons ").append(" UNION").append("   select case when (cons.cuenta = 0) ").append("      then (select sum(n_puntuacion) from OCAP_CREDITOSCUESTIONARIOS where c_exp_id = ? and c_cuestionario_id in( ").append("\t   \t\t select c_cuestionario_id from ITCP_CUESTIONARIOS where c_cuestionario_padre_idp = ?) ");
 
       if (idRepe != 0)
         sqlSelect.append(" AND n_repeticion = '" + idRepe + "' ");
       sqlSelect.append(")").append("      else cons.suma ").append("   end n_puntuacion ").append("   from (select count(*) as cuenta, sum(n_puntuacion) as suma from OCAP_CREDITOSCUESTIONARIOS where c_exp_id = ? ");
 
       if (cuestOT.getBVerPonderacion())
         sqlSelect.append(" AND 1=0 ");
       sqlSelect.append("   AND c_padre_evidencia_id IN(SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_cuestionario_padre_idp = ? )").append("   and c_cuestionario_id in ( ").append("               select c_evidencia_id from ITCP_EVIDENCIAS_CUESTIONARIOS where c_cuestionario_id in ( ").append("                  select c_cuestionario_id from ITCP_CUESTIONARIOS where c_cuestionario_padre_idp = ?))) cons ").append(" UNION").append("   select case when (cons.cuenta = 0) ").append("      then (select sum(n_puntuacion) from OCAP_CREDITOSCUESTIONARIOS where c_exp_id = ? and c_cuestionario_id in( ").append("\t   \t\t select c_cuestionario_id from ITCP_CUESTIONARIOS where c_cuestionario_padre_idp in ( ").append("                  select c_cuestionario_id from ITCP_CUESTIONARIOS where c_cuestionario_padre_idp = ?))");
 
       if (idRepe != 0)
         sqlSelect.append(" AND n_repeticion = '" + idRepe + "' ");
       sqlSelect.append(")").append("      else cons.suma ").append("   end n_puntuacion ").append("   from (select count(*) as cuenta, sum(n_puntuacion) as suma from OCAP_CREDITOSCUESTIONARIOS where c_exp_id = ? ");
 
       if (cuestOT.getBVerPonderacion())
         sqlSelect.append(" AND 1=0 ");
       sqlSelect.append("   AND c_padre_evidencia_id IN(SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_cuestionario_padre_idp IN ( ").append("      SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_cuestionario_padre_idp = ? ) )").append("   and c_cuestionario_id in ( ").append("               select c_evidencia_id from ITCP_EVIDENCIAS_CUESTIONARIOS where c_cuestionario_id in ( ").append("                  select c_cuestionario_id from ITCP_CUESTIONARIOS where c_cuestionario_padre_idp in( ").append("                     select c_cuestionario_id from ITCP_CUESTIONARIOS where c_cuestionario_padre_idp = ?)))) cons )");
 
       st = con.prepareStatement(sqlSelect.toString());
 
       cont = 1;
       st.setLong(cont++, cuestOT.getCCuestionarioId());
       st.setLong(cont++, usuOT.getCExpId().longValue());
       if (cuestOT.getCPadreEvidenciaId() != 0L)
         st.setLong(cont++, cuestOT.getCPadreEvidenciaId());
       st.setLong(cont++, usuOT.getCExpId().longValue());
       st.setLong(cont++, cuestOT.getCCuestionarioId());
       st.setLong(cont++, cuestOT.getCCuestionarioId());
       st.setLong(cont++, usuOT.getCExpId().longValue());
       st.setLong(cont++, cuestOT.getCCuestionarioId());
       st.setLong(cont++, usuOT.getCExpId().longValue());
       st.setLong(cont++, cuestOT.getCCuestionarioId());
       st.setLong(cont++, cuestOT.getCCuestionarioId());
       st.setLong(cont++, usuOT.getCExpId().longValue());
       st.setLong(cont++, cuestOT.getCCuestionarioId());
       st.setLong(cont++, usuOT.getCExpId().longValue());
       st.setLong(cont++, cuestOT.getCCuestionarioId());
       st.setLong(cont++, cuestOT.getCCuestionarioId());
 
       rs = st.executeQuery();
       if (rs.next()) {
         cuestOT.setNPuntosConseguidos(rs.getFloat("n_puntuacion"));
       }
       if (rs != null)
         rs.close();
       if (st != null) {
         st.close();
       }
 
       sql = "select sum((select n_creditos_evaluador from ocap_creditoscuestionarios occ where occ.c_ccuestionario_id = ocr.c_ccuestionario_id and occ.b_acuerdo is not null )) AS n_creditos_evaluados,  sum((select n_creditos_finales from ocap_creditoscuestionarios occ where occ.c_ccuestionario_id = ocr.c_ccuestionario_id and occ.b_acuerdo is not null )) AS n_creditos_finales  from ocap_creditoscuestionarios ocr  where c_exp_id = ?  and c_cuestionario_id = ?  ";
 
       if (idRepe != 0)
         sql = sql + " AND n_repeticion = '" + idRepe + "' ";
       st = con.prepareStatement(sql);
       st.setLong(1, usuOT.getCExpId().longValue());
       st.setLong(2, cuestOT.getCCuestionarioId());
 
       rs = st.executeQuery();
       if (rs.next()) {
         cuestOT.setNCreditosEvaluados(rs.getFloat("n_creditos_evaluados"));
         cuestOT.setNCreditosFinales(rs.getFloat("n_creditos_finales"));
       }
 
       if (hacerCommit)
         con.setAutoCommit(true);
     }
     catch (SQLException ex) {
       throw ex;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return cuestOT;
   }
 
   public OCAPAreasItinerariosOT informacionArea(long cAreaId, JCYLUsuario jcylUsuario)
     throws SQLException, Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     OCAPAreasItinerariosOT datos = null;
     try
     {
       String sql = "select DBMS_LOB.SUBSTR(D_INFORMACION) as D_INFORMACION, D_NOMBRE, D_NOMBRE_LARGO  from itcp_areas_manual  where c_area_id = '" + cAreaId + "' " + " and b_borrado = 'N' ";
 
       st = con.prepareStatement(sql);
       rs = st.executeQuery();
 
       if (rs.next()) {
         datos = new OCAPAreasItinerariosOT();
         datos.setDInformacion(rs.getString("D_INFORMACION"));
         datos.setDNombre(rs.getString("D_NOMBRE"));
         datos.setDNombreLargo(rs.getString("D_NOMBRE_LARGO"));
       }
     }
     catch (SQLException ex) {
       throw ex;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return datos;
   }
 
   public String buscarRespuestaCuestionarioSubdivision(OCAPUsuariosOT usuOT, long idCuestionario, int idRepeticion)
     throws SQLException
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     String respuesta = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     try
     {
       String sql = "select d_valor from ocap_expedientescas where c_exp_id=? and c_cuestionario_id= ? and n_repeticion = ? ";
       st = con.prepareStatement(sql);
       st.setLong(1, usuOT.getCExpId().longValue());
       st.setLong(2, idCuestionario);
       st.setInt(3, idRepeticion);
       rs = st.executeQuery();
       if (rs.next())
         respuesta = rs.getString("d_valor");
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
 
     return respuesta;
   }
 
   public OCAPCuestionariosOT buscarCuestionario(long cCuestionarioId)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     OCAPCuestionariosOT cuestionarioOT = null;
     try
     {
       String sql = "SELECT N_REPETICIONES, C_MANUAL_EVALUACION_ID FROM ITCP_CUESTIONARIOS WHERE C_CUESTIONARIO_ID = ?";
 
       OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
       st = con.prepareStatement(sql);
       st.setLong(1, cCuestionarioId);
       rs = st.executeQuery();
       cuestionarioOT = new OCAPCuestionariosOT();
       if (rs.next()) {
         cuestionarioOT.setCCuestionarioId(cCuestionarioId);
         cuestionarioOT.setNRepeticiones(new Integer(rs.getInt("N_REPETICIONES")));
         cuestionarioOT.setCItinerarioId(rs.getLong("C_MANUAL_EVALUACION_ID"));
       }
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return cuestionarioOT;
   }
 
   public long buscarCuestionarioAsociado(long cItinerarioId, int nEvidencia, Integer cuestionarioPadre)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     long idCuestionario = 0L;
     int cuenta = 0;
     StringBuffer sql = new StringBuffer();
     try
     {
       sql.append("SELECT count(*) as cuenta FROM itcp_evidencias_cuestionarios ").append(" WHERE n_evidencia = ? ").append(" AND c_cuestionario_id IN (SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_manual_evaluacion_id = ?)");
 
       st = con.prepareStatement(sql.toString());
       st.setInt(1, nEvidencia);
       st.setLong(2, cItinerarioId);
       rs = st.executeQuery();
       if (rs.next())
    	 cuenta = rs.getInt("cuenta");
       
       if(cuenta == 1){
    	   idCuestionario = obtenerIdCuestionario(con,nEvidencia, cItinerarioId);
       }else if (cuenta > 1){
    	   if(cuestionarioPadre != null){
    		   return cuestionarioPadre.longValue();
    	   }else{
    		   idCuestionario = obtenerIdCuestionario(con,nEvidencia, cItinerarioId);
    	   }
       }else{
    	   return idCuestionario;
       }
     }
     catch (Exception ex)
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
 
     return idCuestionario;
   }
 
   private long obtenerIdCuestionario(Connection con, int nEvidencia, long cItinerarioId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		long idCuestionario = 0L;
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT c_cuestionario_id FROM itcp_evidencias_cuestionarios ").append(" WHERE n_evidencia = ? ")
				.append(" AND c_cuestionario_id IN (SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_manual_evaluacion_id = ?)");
		st = con.prepareStatement(sql.toString());
		st.setInt(1, nEvidencia);
		st.setLong(2, cItinerarioId);
		rs = st.executeQuery();
		if (rs.next())
			idCuestionario = rs.getLong("c_cuestionario_id");
		return idCuestionario;
}

public void guardarCuestionarioObservEvidencia(OCAPCuestionariosOT cuestionarioOT)
     throws Exception
   {
     PreparedStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
 
     if (cuestionarioOT != null)
     {
       try
       {
         String sql = "UPDATE OCAP_CUESTIONARIOS SET D_OBSERVACIONES_EVIDENCIA = ? WHERE C_CUESTIONARIO_ID = ?";
 
         OCAPConfigApp.logger.info("Sentencia SQL:" + sql);
         st = con.prepareStatement(sql);
         st.setString(1, cuestionarioOT.getDObservacionesEvidencia());
         st.setLong(2, cuestionarioOT.getCCuestionarioId());
         st.executeUpdate();
       }
       catch (Exception ex) {
         OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
         throw ex;
       } finally {
         if (st != null)
           st.close();
         JCYLGestionTransacciones.close(con.getAutoCommit());
       }
     }
   }
 
   public OCAPCuestionariosOT buscarEvidenciaDocumental(long cExpId, long cItinerarioId, int nEvidencia)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     OCAPCuestionariosOT evidencia = null;
     StringBuffer sql = new StringBuffer();
     try
     {
       sql.append("SELECT evi.c_evidencia_id, cue.N_CREDITOS, ").append(" (SELECT COUNT (*) FROM ocap_creditoscuestionarios crc, itcp_evidencias_cuestionarios evc ").append(" WHERE evc.c_cuestionario_id = evi.c_cuestionario_id ").append(" AND crc.c_padre_evidencia_id = evi.c_cuestionario_id ").append(" AND c_tipo_ponderacion = 'S' ").append(" AND evc.n_evidencia != ? ").append(" AND evc.n_evidencia = crc.n_evidencia ").append(" AND c_exp_id = ? ").append(" AND b_borrado = 'N') as numCreditosEvidencias  ").append(" FROM itcp_evidencias_cuestionarios  evi, itcp_cuestionarios cue ").append(" WHERE n_evidencia = ? ").append(" AND evi.c_cuestionario_id IN (SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_manual_evaluacion_id = ?)").append(" AND evi.c_evidencia_id = cue.c_cuestionario_id ");
 
       st = con.prepareStatement(sql.toString());
       st.setInt(1, nEvidencia);
       st.setLong(2, cExpId);
       st.setInt(3, nEvidencia);
       st.setLong(4, cItinerarioId);
       rs = st.executeQuery();
       if (rs.next()) {
         evidencia = new OCAPCuestionariosOT();
         evidencia.setCCuestionarioId(rs.getLong("c_evidencia_id"));
         evidencia.setNCreditos(rs.getFloat("n_creditos"));
         evidencia.setNElementos(rs.getInt("numCreditosEvidencias"));
       }
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (st != null)
         st.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return evidencia;
   }
 
   public boolean mostrarInterrogacion(long cExpId, long cCuestionarioId)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     boolean bMostrar = false;
     StringBuffer sql = new StringBuffer();
     try
     {
       sql.append("SELECT evi.c_cuestionario_id,  ").append("(select c_documento_id from OCAP_DOC_FORMULARIO where c_exp_id = ? AND c_cuestionario_id = evi.c_cuestionario_id and c_evidencia_id = evi.n_evidencia) as docuEvi ").append("FROM itcp_cuestionarios cue, OCAP_ESTADOSCUESTIONARIO est, ITCP_EVIDENCIAS_CUESTIONARIOS evi  ").append("WHERE cue.c_cuestionario_id = evi.c_cuestionario_id ").append("AND cue.c_cuestionario_id = est.c_cuestionario_id AND est.c_exp_id = ? AND est.C_ESTADO = 'F' ").append("AND (cue.c_cuestionario_id = ? ").append("OR cue.c_cuestionario_padre_idp = ? ").append("OR cue.c_cuestionario_padre_idp IN (SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_cuestionario_padre_idp = ?) ").append(") ").append("UNION ").append("SELECT evi.c_cuestionario_id,  ").append("(select c_documento_id from OCAP_DOC_FORMULARIO where c_exp_id = ? AND c_cuestionario_id = evi.c_cuestionario_id and c_evidencia_id = evi.n_evidencia) as docuEvi ").append("FROM itcp_cuestionarios cue, ITCP_EVIDENCIAS_CUESTIONARIOS evi  ").append("WHERE cue.c_cuestionario_id = evi.c_cuestionario_id ").append("AND (cue.b_contestar_usuario='N' OR not exists (select c_cuestionario_id from itcp_preguntas_cuestionarios where c_cuestionario_id = cue.c_cuestionario_id and rownum <= 1)) ").append("AND (cue.c_cuestionario_id = ? ").append("OR cue.c_cuestionario_padre_idp = ? ").append("OR cue.c_cuestionario_padre_idp IN (SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_cuestionario_padre_idp = ?) ").append(") ");
 
       st = con.prepareStatement(sql.toString());
       st.setLong(1, cExpId);
       st.setLong(2, cExpId);
       st.setLong(3, cCuestionarioId);
       st.setLong(4, cCuestionarioId);
       st.setLong(5, cCuestionarioId);
       st.setLong(6, cExpId);
       st.setLong(7, cCuestionarioId);
       st.setLong(8, cCuestionarioId);
       st.setLong(9, cCuestionarioId);
 
       rs = st.executeQuery();
 
       if ((rs.next()) && 
         (rs.getString("docuEvi") == null))
         bMostrar = true;
     }
     catch (Exception ex)
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
 
     return bMostrar;
   }
 
   public ArrayList buscarCuestionariosAsociados(long cCuestionarioId)
     throws Exception
   {
     PreparedStatement st = null;
     ResultSet rs = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     ArrayList listaCuestionarios = null;
     StringBuffer sql = new StringBuffer();
     try
     {
       sql.append("select c_cuestionario_id as cuestionario from itcp_cuestionarios ").append(" where (c_cuestionario_id = ? ").append(" or c_cuestionario_padre_idp = ? ").append(" or c_cuestionario_padre_idp in (select c_cuestionario_id from itcp_cuestionarios where c_cuestionario_padre_idp = ?)) ").append(" and c_cuestionario_id <> 0 ").append(" union ").append(" select c_cuestionario_intermedio_idp as cuestionario from itcp_cuestionarios ").append(" where (c_cuestionario_id = ? ").append(" or c_cuestionario_padre_idp = ? ").append(" or c_cuestionario_padre_idp in (select c_cuestionario_id from itcp_cuestionarios where c_cuestionario_padre_idp = ?)) ").append(" and c_cuestionario_intermedio_idp <> 0 ").append(" union ").append(" select c_cuestionario_intermedio_idp as cuestionario from itcp_cuestionarios ").append(" where c_cuestionario_id in ( ").append(" select c_cuestionario_intermedio_idp as cuestionario from itcp_cuestionarios ").append(" where (c_cuestionario_id = ? ").append(" or c_cuestionario_padre_idp = ? ").append(" or c_cuestionario_padre_idp in (select c_cuestionario_id from itcp_cuestionarios where c_cuestionario_padre_idp = ?)) ) ").append(" and c_cuestionario_intermedio_idp <> 0 ").append(" union ").append(" select c_cuestionario_id as cuestionario from itcp_cuestionarios ").append(" where c_cuestionario_id in ( ").append(" select c_evidencia_id as cuestionario from itcp_evidencias_cuestionarios ").append(" where c_cuestionario_id = ?  )").append(" or c_cuestionario_id IN (SELECT c_evidencia_id AS cuestionario").append(" FROM itcp_evidencias_cuestionarios e").append(" WHERE c_cuestionario_id in (SELECT c_cuestionario_id").append(" FROM itcp_cuestionarios").append(" WHERE c_cuestionario_padre_idp = ?  ").append(" OR c_cuestionario_padre_idp IN (SELECT c_cuestionario_id  ").append(" FROM itcp_cuestionarios  ").append("  WHERE c_cuestionario_padre_idp= ?) ) ) ").append(" order by cuestionario ");
 
       st = con.prepareStatement(sql.toString());
       st.setLong(1, cCuestionarioId);
       st.setLong(2, cCuestionarioId);
       st.setLong(3, cCuestionarioId);
       st.setLong(4, cCuestionarioId);
       st.setLong(5, cCuestionarioId);
       st.setLong(6, cCuestionarioId);
       st.setLong(7, cCuestionarioId);
       st.setLong(8, cCuestionarioId);
       st.setLong(9, cCuestionarioId);
       st.setLong(10, cCuestionarioId);
       st.setLong(11, cCuestionarioId);
       st.setLong(12, cCuestionarioId);
       rs = st.executeQuery();
       listaCuestionarios = new ArrayList();
       while (rs.next())
         listaCuestionarios.add(Long.valueOf(rs.getLong("cuestionario")));
     }
     catch (Exception ex)
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
 
     return listaCuestionarios;
   }
 }

