 package es.jcyl.fqs.ocap.oad.documentos;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.documentos.OCAPDocumentosOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.io.OutputStream;
 import java.lang.reflect.Method;
 import java.sql.Blob;
 import java.sql.CallableStatement;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPDocumentosOAD
 {
   Logger logger;
   private static OCAPDocumentosOAD instance;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
 
   public OCAPDocumentosOAD() {
     $init$();
   }
 
   public static OCAPDocumentosOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPDocumentosOAD();
     }
     return instance;
   }
 
   public long altaOCAPDocFormulario(OCAPDocumentosOT documentosOT)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " altaOCAPDocFormulario: Inicio");
 
     CallableStatement st = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     long cDocumento = 0L;
     try
     {
       String sql = "begin INSERT INTO OCAP_DOC_FORMULARIO (C_DOCUMENTO_ID, C_EXP_ID, C_EVIDENCIA_ID, D_DESCRIPCION, X_DATOS, A_EXT, A_TIPO_DOCUMENTO, F_USUALTA, C_CUESTIONARIO_ID, N_REPETICION, N_DOCUMENTOS, C_USUALTA) VALUES (OCAP_DOC_ID_SEQ.nextval, ?, ?, ?, empty_blob(), ? ,?, SYSDATE, ?, ?, ?, ?) returning C_DOCUMENTO_ID into ?; end;";
 
       st = con.prepareCall(sql);
       st.setLong(1, documentosOT.getCExpId().longValue());
       st.setString(2, documentosOT.getDTitulo());
       st.setString(3, documentosOT.getDDescripcion());
 
       st.setString(4, documentosOT.getAExt());
       st.setString(5, documentosOT.getATipo_documento());
       st.setLong(6, documentosOT.getCCuestionarioId().longValue());
       st.setInt(7, documentosOT.getNRepeticion() != null ? documentosOT.getNRepeticion().intValue() : 0);
       st.setInt(8, documentosOT.getNDocumentos() != null ? documentosOT.getNDocumentos().intValue() : 0);
       st.setString(9, documentosOT.getACreadoPor());
       st.registerOutParameter(10, 4);
 
       st.executeUpdate();
       cDocumento = st.getLong(10);
       OCAPConfigApp.logger.info(getClass().getName() + " altaOCAPDocFormulario: Fin");
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
 
     return cDocumento;
   }
 
   public long guardarDocumento(OCAPDocumentosOT documentosOT)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " guardarDocumento: Inicio");
     Connection con = null;
     CallableStatement stmt = null;
     long cDocumento = 0L;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       StringBuffer sql = new StringBuffer();
       sql.append(" begin insert into OCAP_DOC_FORMULARIO  ").append(" (c_documento_id,c_proyecto_id,c_usuario_id, c_evidencia_id, d_descripcion,x_datos,b_public,a_tipo_documento,a_ext) ").append(" values (ocap_DOC_ID_DOC_SEQ.NEXTVAL,?,?,?,?,empty_blob(),?,?,?) returning c_documento_id into ?; end; ");
 
       stmt = con.prepareCall(sql.toString());
 
       stmt.setString(2, documentosOT.getCUsuario_id());
       stmt.setString(3, documentosOT.getDTitulo());
       stmt.setString(4, documentosOT.getDDescripcion());
 
       stmt.setString(6, documentosOT.getATipo_documento());
       stmt.setString(7, documentosOT.getAExt());
       stmt.registerOutParameter(8, 4);
       stmt.executeUpdate();
       cDocumento = stmt.getLong(8);
 
       OCAPConfigApp.logger.info(getClass().getName() + " guardarDocumento: Fin");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (stmt != null)
         stmt.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return cDocumento;
   }
 
   public long modificarDocumento(OCAPDocumentosOT documentosOT)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " modificarDocumento: Inicio: ");
     Connection con = null;
     CallableStatement stmt = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
 
       StringBuffer sql = new StringBuffer();
       sql.append(" update OCAP_DOC_FORMULARIO  ").append(" set c_usuario_id = ?, c_evidencia_id = ?, d_descripcion = ?, ").append(" x_datos = empty_blob(), b_public = ? ");
 
       if (documentosOT.getADatos() != null) {
         sql.append(" ,a_tipo_documento = ?,a_ext = ? ");
       }
       sql.append(" where c_documento_id = ? ");
 
       stmt = con.prepareCall(sql.toString());
       int posicion = 1;
       stmt.setString(posicion++, documentosOT.getCUsuario_id());
       stmt.setString(posicion++, documentosOT.getDTitulo());
       stmt.setString(posicion++, documentosOT.getDDescripcion());
 
       if (documentosOT.getADatos() != null) {
         stmt.setString(posicion++, documentosOT.getATipo_documento());
         stmt.setString(posicion++, documentosOT.getAExt());
       }
       stmt.setLong(posicion++, documentosOT.getCDocumento_id());
 
       stmt.executeUpdate();
 
       OCAPConfigApp.logger.info(getClass().getName() + " modificarDocumento: Fin: ");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (stmt != null)
         stmt.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return documentosOT.getCDocumento_id();
   }
 
   public long modificarDocumentoRepeticion(OCAPDocumentosOT documentosOT, int nRepeticion)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " modificarDocumentoRepeticion: Inicio: ");
     Connection con = null;
     CallableStatement stmt = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       StringBuffer sql = new StringBuffer();
       sql.append(" update OCAP_DOC_FORMULARIO set n_repeticion = ? ");
       sql.append(" where c_documento_id = (select min(c_documento_id) from ocap_doc_formulario where c_exp_id=? and c_cuestionario_id = ? and b_borrado='N' and n_repeticion=1) ");
 
       stmt = con.prepareCall(sql.toString());
       int posicion = 1;
       stmt.setInt(posicion++, nRepeticion);
       stmt.setLong(posicion++, documentosOT.getCExpId().longValue());
       stmt.setLong(posicion++, documentosOT.getCCuestionarioId().longValue());
       stmt.executeUpdate();
 
       OCAPConfigApp.logger.info(getClass().getName() + " modificarDocumentoRepeticion: Fin: ");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (stmt != null)
         stmt.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return documentosOT.getCDocumento_id();
   }
 
   public void guardarDatosBlob(OCAPDocumentosOT documentosOT)
     throws SQLException, Exception
   {
     PreparedStatement stmt = null;
     Connection con = null;
     ResultSet rs = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       StringBuffer sql = new StringBuffer();
       sql.append("select x_datos from OCAP_DOC_FORMULARIO where c_documento_id = ? for update ");
       stmt = con.prepareStatement(sql.toString());
       stmt.setLong(1, documentosOT.getCDocumento_id());
       rs = stmt.executeQuery();
 
       if (rs.next())
       {
         Blob blob = rs.getBlob("x_datos");
         Class blobClass = blob.getClass();
         Method methodGetBinaryOutputStream = blobClass.getMethod("getBinaryOutputStream", null);
         Method methodGetChunkSize = blobClass.getMethod("getChunkSize", null);
         OutputStream blobWrite = (OutputStream)methodGetBinaryOutputStream.invoke(blob, null);
         int chunkSizeBlobWrite = ((Integer)methodGetChunkSize.invoke(blob, null)).intValue();
         Utilidades utilidades = new Utilidades();
         utilidades.setOutputInput(documentosOT.getADatos(), chunkSizeBlobWrite, blobWrite);
       }
       else {
         throw new SQLException("Error al asociar el blob al documento");
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " guardarBlob: Fin: ");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (stmt != null)
         stmt.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
   }
 
   public ArrayList buscarDocumentos(long CExpId)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentos: Inicio: ");
     ArrayList listado = new ArrayList();
     OCAPDocumentosOT documentosOT = null;
     Connection con = null;
     PreparedStatement stmt = null;
 
     ResultSet rs = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       StringBuffer sql = new StringBuffer();
 
       sql.append("SELECT odf.d_descripcion, odf.c_evidencia_id, odf.c_documento_id, odf.a_tipo_documento, odf.a_ext, odf.x_datos, odf.c_cuestionario_id, odf.n_repeticion, ").append(" (select ocu.d_nombre FROM itcp_cuestionarios ocu where ocu.C_CUESTIONARIO_ID = odf.C_CUESTIONARIO_ID) as d_cuest_nombre, ").append(" (select ocu.d_nombre_largo FROM itcp_cuestionarios ocu where ocu.C_CUESTIONARIO_ID = odf.C_CUESTIONARIO_ID) as d_cuest_nombre_largo, ").append(" (select oa.D_NOMBRE from itcp_areas_manual oa, itcp_cuestionarios ocu where oa.c_area_id = ocu.c_area_id and ocu.C_CUESTIONARIO_ID = odf.C_CUESTIONARIO_ID) AS d_area, ").append(" (select oa.C_AREA_ID from itcp_areas_manual oa, itcp_cuestionarios ocu where oa.c_area_id = ocu.c_area_id and ocu.C_CUESTIONARIO_ID = odf.C_CUESTIONARIO_ID) AS c_area_id ").append(" from OCAP_DOC_FORMULARIO odf where odf.c_exp_id = ? AND odf.B_BORRADO = 'N' ").append(" order by c_evidencia_id ");
 
       stmt = con.prepareStatement(sql.toString());
       stmt.setLong(1, CExpId);
       rs = stmt.executeQuery();
 
       while (rs.next()) {
         documentosOT = new OCAPDocumentosOT();
         documentosOT.setDDescripcion(rs.getString("d_descripcion"));
         documentosOT.setDTitulo(rs.getString("c_evidencia_id"));
         documentosOT.setCDocumento_id(rs.getLong("c_documento_id"));
         documentosOT.setATipo_documento(rs.getString("a_tipo_documento"));
         documentosOT.setAExt(rs.getString("a_ext"));
         documentosOT.setCCuestionarioId(Long.valueOf(rs.getString("C_CUESTIONARIO_ID")));
         documentosOT.setNRepeticion(Integer.valueOf(rs.getString("N_REPETICION")));
         documentosOT.setDCuestionario(rs.getString("d_area") + rs.getString("d_cuest_nombre") + " - " + rs.getString("d_cuest_nombre_largo"));
         documentosOT.setADatos(rs.getBlob("x_datos") != null ? rs.getBlob("x_datos").getBinaryStream() : null);
         listado.add(documentosOT);
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentos: Fin: ");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (stmt != null)
         stmt.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 
   public ArrayList buscarDocumentosRellenables(long cExpId, long itinerarioId)
     throws SQLException, Exception
   {
     ArrayList listado = new ArrayList();
     OCAPDocumentosOT documentosOT = new OCAPDocumentosOT();
     Connection con = null;
     PreparedStatement stmt = null;
 
     ResultSet rs = null;
     Blob blob = null;
     try
     {
       OCAPConfigApp.logger.info("Inicio");
       con = JCYLGestionTransacciones.getConnection();
 
       StringBuffer sql = new StringBuffer();
       sql.append(" SELECT odf.d_descripcion, odf.c_evidencia_id, odf.c_documento_id, odf.a_tipo_documento, odf.a_ext, odf.x_datos, odf.c_cuestionario_id, odf.n_repeticion, ").append("(select ocu.d_nombre FROM itcp_cuestionarios ocu where ocu.C_CUESTIONARIO_ID = odf.C_CUESTIONARIO_ID) as d_cuest_nombre,").append("(select ocu.d_nombre_largo FROM itcp_cuestionarios ocu where ocu.C_CUESTIONARIO_ID = odf.C_CUESTIONARIO_ID) as d_cuest_nombre_largo,").append("(select oa.D_NOMBRE from itcp_areas_manual oa, itcp_cuestionarios ocu where oa.c_area_id = ocu.c_area_id and ocu.C_CUESTIONARIO_ID = odf.C_CUESTIONARIO_ID) AS d_area, ").append("(select oa.C_AREA_ID from itcp_areas_manual oa, itcp_cuestionarios ocu where oa.c_area_id = ocu.c_area_id and ocu.C_CUESTIONARIO_ID = odf.C_CUESTIONARIO_ID) AS c_area_id, ").append(" (select b_contestar_usuario from itcp_cuestionarios oc where oc.c_cuestionario_id = odf.c_cuestionario_id and oc.b_borrado='N') as contestaUsuario, ").append(" (select n_repeticiones from itcp_cuestionarios oc where oc.c_cuestionario_id = odf.c_cuestionario_id and oc.b_borrado='N') as nRepeticiones, ").append(" (select distinct(c_estado) from ocap_estadoscuestionario oe where oe.c_cuestionario_id = (select c_evidencia_id from itcp_evidencias_cuestionarios oc where oc.c_cuestionario_id = odf.c_cuestionario_id ) and oe.b_borrado='N' AND oe.c_exp_id=?) as estadoEvi, ").append(" (select count(c_estcues_id) from ocap_estadoscuestionario oe where oe.c_cuestionario_id = odf.c_cuestionario_id and oe.b_borrado='N' AND oe.c_exp_id=? AND oe.c_estado='F') as cuentaFinalizados ").append(" from OCAP_DOC_FORMULARIO odf where odf.c_exp_id = ? AND odf.B_BORRADO='N' ").append(" AND odf.c_cuestionario_id in ").append("     (select cues.c_cuestionario_id from itcp_cuestionarios cues,  itcp_evidencias_cuestionarios evid where cues.c_cuestionario_id = evid.c_cuestionario_id and cues.c_manual_evaluacion_id = ? and cues.b_borrado='N') ").append(" order by c_evidencia_id ");
 
       stmt = con.prepareStatement(sql.toString());
       stmt.setLong(1, cExpId);
       stmt.setLong(2, cExpId);
       stmt.setLong(3, cExpId);
       stmt.setLong(4, itinerarioId);
 
       rs = stmt.executeQuery();
 
       while (rs.next()) {
         documentosOT = new OCAPDocumentosOT();
         documentosOT.setDDescripcion(rs.getString("d_descripcion"));
         documentosOT.setDTitulo(rs.getString("c_evidencia_id"));
         documentosOT.setCDocumento_id(rs.getLong("c_documento_id"));
         documentosOT.setATipo_documento(rs.getString("a_tipo_documento"));
         documentosOT.setAExt(rs.getString("a_ext"));
         documentosOT.setCCuestionarioId(Long.valueOf(rs.getString("C_CUESTIONARIO_ID")));
         documentosOT.setNRepeticion(Integer.valueOf(rs.getString("N_REPETICION")));
         documentosOT.setDCuestionario(rs.getString("d_area") + "." + rs.getString("d_cuest_nombre") + "-" + rs.getString("d_cuest_nombre_largo"));
 
         blob = rs.getBlob("x_datos");
         documentosOT.setADatos(blob.getBinaryStream());
 
         if ((Constantes.SI.equals(rs.getString("contestaUsuario"))) && ("F".equals(rs.getString("estadoEvi"))))
           documentosOT.setBFinalizado(Constantes.SI);
         else if ((Constantes.NO.equals(rs.getString("contestaUsuario"))) && (rs.getLong("cuentaFinalizados") == rs.getLong("nRepeticiones")) && (rs.getLong("nRepeticiones") != 0L))
           documentosOT.setBFinalizado(Constantes.SI);
         else {
           documentosOT.setBFinalizado(Constantes.NO);
         }
         listado.add(documentosOT);
       }
 
       OCAPConfigApp.logger.info("Fin");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (stmt != null)
         stmt.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 
   public ArrayList buscarDocumentosCuestionario(long CExpId, long cCuestionarioId, long cEvidenciaId)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentosCuestionario: Inicio: ");
     ArrayList listado = new ArrayList();
     OCAPDocumentosOT documentosOT = null;
     PreparedStatement stmt = null;
     ResultSet rs = null;
     Blob blob = null;
     Connection con = JCYLGestionTransacciones.getConnection();
     try
     {
       StringBuffer sql = new StringBuffer();
       sql.append(" SELECT odf.d_descripcion, odf.c_evidencia_id, odf.c_documento_id, odf.a_tipo_documento, odf.a_ext, odf.x_datos, odf.c_cuestionario_id, odf.n_repeticion, ").append("(select ocu.d_nombre FROM itcp_cuestionarios ocu where ocu.C_CUESTIONARIO_ID = odf.C_CUESTIONARIO_ID) as d_cuest_nombre,").append("(select ocu.d_nombre_largo FROM itcp_cuestionarios ocu where ocu.C_CUESTIONARIO_ID = odf.C_CUESTIONARIO_ID) as d_cuest_nombre_largo,").append("(select oa.D_NOMBRE from itcp_areas_manual oa, itcp_cuestionarios ocu where oa.c_area_id = ocu.c_area_id and ocu.C_CUESTIONARIO_ID = odf.C_CUESTIONARIO_ID) AS d_area, ").append("(select oa.C_AREA_ID from itcp_areas_manual oa, itcp_cuestionarios ocu where oa.c_area_id = ocu.c_area_id and ocu.C_CUESTIONARIO_ID = odf.C_CUESTIONARIO_ID) AS c_area_id ").append(" from OCAP_DOC_FORMULARIO odf where odf.c_exp_id = ? AND odf.B_BORRADO='N' ").append("  AND (   c_cuestionario_id = ? ").append("     OR c_cuestionario_id IN (SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_cuestionario_padre_idp = ?  and b_borrado='N') ").append("     OR c_cuestionario_id IN (SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_cuestionario_padre_idp IN (SELECT c_cuestionario_id FROM itcp_cuestionarios WHERE c_cuestionario_padre_idp = ?  and b_borrado='N')) ").append("     OR c_cuestionario_id in (select c_evidencia_id from itcp_evidencias_cuestionarios where c_cuestionario_id = ? ) ) ");
 
       if (cEvidenciaId != 0L) {
         sql.append(" AND c_evidencia_id = ? ");
       }
 
       sql.append(" order by c_evidencia_id ");
 
       stmt = con.prepareStatement(sql.toString());
       stmt.setLong(1, CExpId);
       stmt.setLong(2, cCuestionarioId);
       stmt.setLong(3, cCuestionarioId);
       stmt.setLong(4, cCuestionarioId);
       stmt.setLong(5, cCuestionarioId);
 
       if (cEvidenciaId != 0L) {
         stmt.setLong(6, cEvidenciaId);
       }
       rs = stmt.executeQuery();
 
       while (rs.next()) {
         documentosOT = new OCAPDocumentosOT();
         documentosOT.setDDescripcion(rs.getString("d_descripcion"));
         documentosOT.setDTitulo(rs.getString("c_evidencia_id"));
         documentosOT.setCDocumento_id(rs.getLong("c_documento_id"));
         documentosOT.setATipo_documento(rs.getString("a_tipo_documento"));
         documentosOT.setAExt(rs.getString("a_ext"));
         documentosOT.setCCuestionarioId(Long.valueOf(rs.getString("C_CUESTIONARIO_ID")));
         documentosOT.setNRepeticion(Integer.valueOf(rs.getString("N_REPETICION")));
         documentosOT.setDCuestionario(rs.getString("d_area") + "." + rs.getString("d_cuest_nombre") + "-" + rs.getString("d_cuest_nombre_largo"));
         blob = rs.getBlob("x_datos");
         documentosOT.setADatos(blob.getBinaryStream());
         listado.add(documentosOT);
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentosCuestionario: Fin: ");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (stmt != null)
         stmt.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 
   public ArrayList buscarDocumentosRepeticiones(long CExpId)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentosRepeticiones: Inicio: ");
     ArrayList listado = new ArrayList();
     Connection con = null;
     PreparedStatement stmt = null;
 
     ResultSet rs = null;
     Blob blob = null;
     OCAPDocumentosOT documentosOT = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       StringBuffer sql = new StringBuffer();
       sql.append(" select distinct(c_cuestionario_id), ").append("(select count(*) from ocap_doc_formulario odB where odA.c_cuestionario_id = odB.c_cuestionario_id and odb.b_borrado='N' ) as repes ").append(" from ocap_doc_formulario odA where c_exp_id = ? and b_borrado ='N'");
 
       stmt = con.prepareStatement(sql.toString());
       stmt.setLong(1, CExpId);
 
       rs = stmt.executeQuery();
       while (rs.next()) {
         documentosOT = new OCAPDocumentosOT();
         documentosOT.setCCuestionarioId(Long.valueOf(rs.getString("C_CUESTIONARIO_ID")));
         documentosOT.setNRepeticion(new Integer(rs.getInt("repes")));
         documentosOT.setCExpId(new Long(CExpId));
 
         listado.add(documentosOT);
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentosRepeticiones: Fin: ");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (stmt != null)
         stmt.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 
   public OCAPDocumentosOT buscarDocumento(long cDocumento)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumento: Inicio: ");
     OCAPDocumentosOT documentosOT = null;
     Connection con = null;
     PreparedStatement stmt = null;
     ResultSet rs = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
 
       StringBuffer sql = new StringBuffer();
       sql.append(" select d_descripcion, c_evidencia_id, c_documento_id, x_datos, a_tipo_documento, a_ext ").append(" from OCAP_DOC_FORMULARIO where c_documento_id = ? ");
 
       stmt = con.prepareStatement(sql.toString());
       stmt.setLong(1, cDocumento);
       rs = stmt.executeQuery();
 
       documentosOT = new OCAPDocumentosOT();
       if (rs.next()) {
         documentosOT.setDDescripcion(rs.getString("d_descripcion"));
         documentosOT.setDTitulo(rs.getString("c_evidencia_id"));
         documentosOT.setCDocumento_id(rs.getLong("c_documento_id"));
         documentosOT.setADatos(rs.getBlob("x_datos") != null ? rs.getBlob("x_datos").getBinaryStream() : null);
         documentosOT.setATipo_documento(rs.getString("a_tipo_documento"));
         documentosOT.setAExt(rs.getString("a_ext"));
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumento: Fin: ");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (stmt != null)
         stmt.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return documentosOT;
   }
 
   public OCAPDocumentosOT buscarDocumentoExpedienteTitulo(long cExpId, String dTitulo)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentoExpedienteTitulo: Inicio: ");
     OCAPDocumentosOT documentosOT = null;
     Connection con = null;
     PreparedStatement stmt = null;
     ResultSet rs = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
 
       StringBuffer sql = new StringBuffer();
       sql.append(" select c_evidencia_id, n_documentos").append(" from OCAP_DOC_FORMULARIO where c_exp_id = ? and c_evidencia_id = ? and b_borrado='N'");
 
       stmt = con.prepareStatement(sql.toString());
       stmt.setLong(1, cExpId);
       stmt.setString(2, dTitulo);
       rs = stmt.executeQuery();
 
       if (rs.next()) {
         documentosOT = new OCAPDocumentosOT();
         documentosOT.setDTitulo(rs.getString("c_evidencia_id"));
         documentosOT.setNDocumentos(Integer.valueOf(rs.getString("n_documentos") == null ? "0" : rs.getString("n_documentos")));
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentoExpedienteTitulo: Fin: ");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (stmt != null)
         stmt.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return documentosOT;
   }
 
   public void borrarDocumento(long cDocumento)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " borrarDocumento: Inicio: ");
     Connection con = null;
     PreparedStatement stmt = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
       StringBuffer sql = new StringBuffer();
       sql.append("delete from OCAP_DOC_FORMULARIO where c_documento_id = ? ");
 
       stmt = con.prepareStatement(sql.toString());
       stmt.setLong(1, cDocumento);
       stmt.executeUpdate();
       OCAPConfigApp.logger.info(getClass().getName() + " borrarDocumento: Fin: ");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (stmt != null)
         stmt.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
   }
 
   public OCAPDocumentosOT buscarDocumentoEvidencia(long cExpedienteId)
     throws SQLException, Exception
   {
     OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentoEvidencia: Inicio: ");
     OCAPDocumentosOT documentosOT = null;
     Connection con = null;
     PreparedStatement stmt = null;
     ResultSet rs = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
 
       StringBuffer sql = new StringBuffer();
       sql.append(" select d_descripcion, c_evidencia_id, c_documento_id, x_datos, a_tipo_documento, a_ext ").append(" from OCAP_DOC_FORMULARIO where c_exp_id = ? ").append(" and c_evidencia_id = 0 ").append(" and c_cuestionario_id = 0 ");
 
       stmt = con.prepareStatement(sql.toString());
       stmt.setLong(1, cExpedienteId);
       rs = stmt.executeQuery();
 
       documentosOT = new OCAPDocumentosOT();
       if (rs.next()) {
         documentosOT.setDDescripcion(rs.getString("d_descripcion"));
         documentosOT.setDTitulo(rs.getString("c_evidencia_id"));
         documentosOT.setCDocumento_id(rs.getLong("c_documento_id"));
         documentosOT.setADatos(rs.getBlob("x_datos") != null ? rs.getBlob("x_datos").getBinaryStream() : null);
         documentosOT.setATipo_documento(rs.getString("a_tipo_documento"));
         documentosOT.setAExt(rs.getString("a_ext"));
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarDocumentoEvidencia: Fin: ");
     }
     catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       throw ex;
     } finally {
       if (rs != null)
         rs.close();
       if (stmt != null)
         stmt.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return documentosOT;
   }
 }

