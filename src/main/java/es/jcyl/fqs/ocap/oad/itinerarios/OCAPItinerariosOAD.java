package es.jcyl.fqs.ocap.oad.itinerarios;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.categProfesionales.OCAPCategProfesionalesOT;
import es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT;
import es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT;
import es.jcyl.fqs.ocap.ot.itinerarios.OCAPItinerariosOT;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.io.InputStream;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPItinerariosOAD
{
  public Logger loggerBD;
  private static OCAPItinerariosOAD instance;

  public static OCAPItinerariosOAD getInstance()
  {
    if (instance == null) {
      instance = new OCAPItinerariosOAD();
    }
    return instance;
  }

  private OCAPItinerariosOAD() {
	  this.loggerBD = OCAPConfigApp.loggerBD;
  }

  public OCAPItinerariosOT buscarOCAPItinerarios(long cItinerarioId)
    throws SQLException, Exception
  {
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = JCYLGestionTransacciones.getConnection();
    OCAPItinerariosOT datos = null;
    try
    {
      this.loggerBD.info(getClass().getName() + " buscarOCAPItinerarios: Inicio");

      String sql = "SELECT D_NOMBRE, C_MANUAL_EVALUACION_ID, D_DESCRIPCION, D_INTRODUCCION, N_EVIDENCIAS, N_CREDITOS_NECESARIOS FROM ITCP_MANUALES_EVALUACION WHERE C_MANUAL_EVALUACION_ID = ? AND B_BORRADO = 'N'";

      st = con.prepareStatement(sql);
      st.setLong(1, cItinerarioId);
      rs = st.executeQuery();
      datos = new OCAPItinerariosOT();
      if (rs.next()) {
        datos.setCItinerarioId(rs.getLong("C_MANUAL_EVALUACION_ID"));
        datos.setDNombre(rs.getString("D_NOMBRE"));
        datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
        datos.setNEvidencias(rs.getInt("N_EVIDENCIAS"));
        datos.setNCreditosNecesarios(rs.getFloat("N_CREDITOS_NECESARIOS"));
        if (rs.getClob("D_INTRODUCCION") != null) {
          InputStream inputStreamDIntroduccion = rs.getClob("D_INTRODUCCION").getAsciiStream();
          StringBuffer dIntroduccion = new StringBuffer();
          int caracter;
          while ((caracter = inputStreamDIntroduccion.read()) != -1) {
            dIntroduccion.append((char)caracter);
          }
          datos.setDIntroduccion(dIntroduccion.toString());
        }
      }

      this.loggerBD.info(getClass().getName() + " buscarOCAPItinerarios: Fin");
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

    return datos;
  }

  public OCAPItinerariosOT buscarManualesReferencia(long cManualId)
    throws SQLException, Exception
  {
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = JCYLGestionTransacciones.getConnection();

    OCAPItinerariosOT datos = null;
    try
    {
      this.loggerBD.info(getClass().getName() + " buscarManualesReferencia: Inicio");
      String sql = "SELECT D_NOMBRE FROM ITCP_MANUALES_REF WHERE C_MANUAL_REF_ID = ? AND B_BORRADO = 'N'";

      st = con.prepareStatement(sql);
      st.setLong(1, cManualId);

      rs = st.executeQuery();

      datos = new OCAPItinerariosOT();
      if (rs.next()) {
        datos.setCItinerarioId(cManualId);
        datos.setDNombre(rs.getString("D_NOMBRE"));
      }
      this.loggerBD.info(getClass().getName() + " buscarManualesReferencia: Fin");
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

  public ArrayList listadoItinerarios()
    throws SQLException, Exception
  {
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = JCYLGestionTransacciones.getConnection();

    StringBuffer consulta = new StringBuffer();
    ArrayList listado = null;
    OCAPItinerariosOT datos = null;
    try
    {
      this.loggerBD.info(getClass().getName() + " listadoItinerarios: Inicio");
      consulta.append("SELECT D_DESCRIPCION, C_ITINERARIO_ID ").append("FROM OCAP_ITINERARIOS  ").append("WHERE B_BORRADO = 'N' ").append("ORDER BY D_DESCRIPCION ");

      st = con.prepareStatement(consulta.toString(), 1004, 1008);

      rs = st.executeQuery();

      listado = new ArrayList();
      int i = 0;
      while (rs.next()) {
        datos = new OCAPItinerariosOT();
        datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
        datos.setCItinerarioId(rs.getLong("C_ITINERARIO_ID"));
        listado.add(datos);
      }

      this.loggerBD.info(getClass().getName() + " listadoItinerarios: Fin");
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

  public ArrayList listadoItinerariosUsados()
    throws SQLException, Exception
  {
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = JCYLGestionTransacciones.getConnection();

    StringBuffer consulta = new StringBuffer();
    ArrayList listado = null;
    OCAPItinerariosOT datos = null;
    try
    {
      this.loggerBD.info(getClass().getName() + " listadoItinerarios: Inicio");
      consulta.append("SELECT distinct(oi.C_ITINERARIO_ID), oi.D_DESCRIPCION ").append("FROM OCAP_ITINERARIOS oi, OCAP_EXPEDIENTESCARRERA oe ").append("WHERE oi.B_BORRADO = 'N' AND oe.B_BORRADO ='N' ").append("AND oe.C_ITINERARIO_ID = oi.C_ITINERARIO_ID ").append("ORDER BY oi.D_DESCRIPCION  ");

      st = con.prepareStatement(consulta.toString(), 1004, 1008);

      rs = st.executeQuery();

      listado = new ArrayList();
      int i = 0;
      while (rs.next()) {
        datos = new OCAPItinerariosOT();
        datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
        datos.setCItinerarioId(rs.getLong("C_ITINERARIO_ID"));
        listado.add(datos);
      }

      this.loggerBD.info(getClass().getName() + " listadoItinerarios: Fin");
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

  public ArrayList listadoOCAPItinerarios(long cGradoId, long idCategProfesional, long idEspecialidad, long cProcedimientoId)
    throws SQLException, Exception
  {
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = JCYLGestionTransacciones.getConnection();
    StringBuffer consulta = new StringBuffer();
    ArrayList listado = null;
    OCAPItinerariosOT datos = null;
    try
    {
      consulta.append("SELECT distinct eva.c_manual_evaluacion_id, eva.d_nombre, eva.d_descripcion ").append(" FROM ITCP_MANUALES_ASIG asi, ITCP_MANUALES_REF refe, ITCP_MANUALES_EVALUACION eva ").append(" WHERE eva.c_manual_ref_id = refe.C_MANUAL_REF_ID ").append(" AND eva.c_manual_ref_id = asi.C_MANUAL_REF_ID ").append(" AND asi.B_BORRADO = 'N' AND refe.B_BORRADO = 'N' AND eva.B_BORRADO = 'N'").append(" AND asi.C_GRADO_ID = '" + cGradoId + "'").append(" AND asi.C_GRADO_ID = eva.c_grado_id ");

      if (idCategProfesional != 0L) {
        consulta.append(" AND (asi.C_PROFESIONAL_ID = '" + idCategProfesional + "'");
        if (idEspecialidad != 0L) {
          consulta.append(" AND (asi.C_ESPEC_ID = '" + idEspecialidad + "' OR asi.C_ESPEC_ID is null) ");
        }
        consulta.append(" or (asi.c_profesional_id is null and refe.C_GRUPO_CATEGORIA_ID = ").append(" (select c_grupo_categoria_id from OCAP_CATEG_PROFESIONALES ").append(" where c_profesional_id = '" + idCategProfesional + "'").append(" and b_borrado = 'N'))) ");
      }

      if (cProcedimientoId != 0L)
        consulta.append(" AND C_PROC_EVALUACION_ID = '" + cProcedimientoId + "' ");
      consulta.append(" ORDER BY D_DESCRIPCION ");

      st = con.prepareStatement(consulta.toString(), 1004, 1008);
      rs = st.executeQuery();
      listado = new ArrayList();
      int i = 0;
      while (rs.next()) {
        datos = new OCAPItinerariosOT();
        datos.setDNombre(rs.getString("D_NOMBRE"));
        datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
        datos.setCItinerarioId(rs.getLong("c_manual_evaluacion_id"));
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

  public ArrayList buscarOCAPCategEspecItinerarios(ArrayList itinerarios)
    throws SQLException, Exception
  {
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = JCYLGestionTransacciones.getConnection();

    ArrayList listado = null;
    try
    {
      StringBuffer sbWhere = new StringBuffer();
      for (int i = 0; i < itinerarios.size(); i++) {
        sbWhere.append("?,");
      }

      String where = "";
      int len = sbWhere.length();
      if (len > 0) {
        where = sbWhere.substring(0, len - 1);
      }
      StringBuffer sql = new StringBuffer();
      sql.append(" SELECT cate.c_profesional_id, cate.d_nombre ").append(" FROM ocap_categ_profesionales cate, ").append(" (SELECT DISTINCT c_profesional_id ").append(" FROM ( ").append(" SELECT DISTINCT c_profesional_id FROM ITCP_MANUALES_ASIG ").append(" WHERE C_MANUAL_REF_ID IN (").append("    SELECT C_MANUAL_REF_ID FROM ITCP_MANUALES_EVALUACION ").append("          WHERE C_MANUAL_EVALUACION_ID IN (").append(where).append(") ").append("          AND b_borrado = 'N') ").append(" AND b_borrado = 'N' ").append(" AND c_profesional_id IS NOT NULL ").append(" UNION ").append(" SELECT DISTINCT c_profesional_id FROM ocap_categ_profesionales ").append(" WHERE C_GRUPO_CATEGORIA_ID IN( ").append("       SELECT C_GRUPO_CATEGORIA_ID FROM ITCP_MANUALES_REF").append("       WHERE C_MANUAL_REF_ID IN( ").append("        SELECT C_MANUAL_REF_ID FROM ITCP_MANUALES_ASIG ").append("           WHERE C_MANUAL_REF_ID IN( ").append("              SELECT C_MANUAL_REF_ID FROM ITCP_MANUALES_EVALUACION ").append("                  WHERE C_MANUAL_EVALUACION_ID IN (").append(where).append(") ").append("                     AND b_borrado = 'N') ").append("              AND c_profesional_id IS NULL  ").append("              AND b_borrado = 'N') ").append("       AND b_borrado = 'N') ").append(" AND b_borrado = 'N' ").append(" ) ").append(" ) itin ").append(" WHERE cate.b_borrado = 'N' ").append(" AND cate.c_profesional_id = itin.c_profesional_id ").append(" ORDER BY cate.c_estatut_id, cate.c_profesional_id ");

      Object[] listadoItin = itinerarios.toArray();

      st = con.prepareStatement(sql.toString());

      int cont = 1;
      int contDos = cont + itinerarios.size();

      for (int i = 0; i < itinerarios.size(); i++) {
        st.setLong(cont++, ((OCAPComponentesfqsOT)listadoItin[i]).getCItinerarioId());
        st.setLong(contDos++, ((OCAPComponentesfqsOT)listadoItin[i]).getCItinerarioId());
      }

      rs = st.executeQuery();

      listado = new ArrayList();
      while (rs.next()) {
        OCAPItinerariosOT datos = new OCAPItinerariosOT();
        datos.setCProfesionalId(rs.getLong("C_PROFESIONAL_ID"));
        datos.setDNombre(rs.getString("d_nombre"));

        listado.add(datos);
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

    return listado;
  }

  public ArrayList listadoEvidencias(long cItinerarioId)
    throws SQLException, Exception
  {
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = JCYLGestionTransacciones.getConnection();

    StringBuffer consulta = new StringBuffer();
    ArrayList listado = null;
    OCAPCuestionariosOT datos = null;
    try
    {
      this.loggerBD.info(getClass().getName() + " listadoEvidencias: Inicio");
      consulta.append("SELECT n_evidencia,  ").append(" (SELECT count(*) from ITCP_PREGUNTAS_CUESTIONARIOS where c_cuestionario_id = c_evidencia_id) as consulta ").append(" from ITCP_EVIDENCIAS_CUESTIONARIOS ").append(" where c_cuestionario_id in( ").append("     select c_cuestionario_id").append("         from ITCP_CUESTIONARIOS ").append("        where c_manual_evaluacion_id = ? ").append("         and b_borrado = 'N') ").append("  order by n_evidencia ");

      st = con.prepareStatement(consulta.toString(), 1004, 1008);
      st.setLong(1, cItinerarioId);

      rs = st.executeQuery();

      listado = new ArrayList();
      int i = 0;
      while (rs.next()) {
        datos = new OCAPCuestionariosOT();
        datos.setCEvidenciaId(rs.getLong("n_evidencia"));
        datos.setNPreguntas(new Integer(rs.getInt("consulta")));
        listado.add(datos);
      }

      this.loggerBD.info(getClass().getName() + " listadoEvidencias: Fin");
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

  public ArrayList listarManualesCte(long cCteId)
    throws SQLException, Exception
  {
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = JCYLGestionTransacciones.getConnection();

    StringBuffer consulta = new StringBuffer();
    ArrayList listado = null;
    OCAPItinerariosOT datos = null;
    try
    {
      this.loggerBD.info(getClass().getName() + " listarManualesCte: Inicio");
      consulta.append("SELECT man.d_nombre, cte.c_manual_ref_id ").append("from ITCP_MANUALES_REF man, OCAP_MANUALES_REF_CTES cte ").append("where cte.c_manual_ref_id = man.c_manual_ref_id ").append("and man.b_borrado = 'N' ").append("and cte.b_borrado = 'N' ");

      if (cCteId != 0L) {
        consulta.append("and cte.c_cte_id = ").append(cCteId);
      }
      consulta.append(" ORDER BY D_NOMBRE ");

      st = con.prepareStatement(consulta.toString(), 1004, 1008);

      rs = st.executeQuery();

      listado = new ArrayList();
      int i = 0;
      while (rs.next()) {
        datos = new OCAPItinerariosOT();
        datos.setDNombre(rs.getString("D_NOMBRE"));
        datos.setCItinerarioId(rs.getLong("C_MANUAL_REF_ID"));
        listado.add(datos);
      }

      this.loggerBD.info(getClass().getName() + " listarManualesCte: Fin");
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

  public ArrayList listarManualesPorGrupo(long cCteId, boolean verNoAsignadosCte)
    throws SQLException, Exception
  {
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = JCYLGestionTransacciones.getConnection();

    StringBuffer consulta = new StringBuffer();
    ArrayList listaManuales = null;
    ArrayList listaGrupos = null;
    OCAPItinerariosOT datos = null;
    long idGrupo = 0L;
    String dGrupo = "";
    try {
      this.loggerBD.info("Inicio");
      consulta.append(" SELECT man.c_grupo_categoria_id, grc.d_nombre as d_grupo, man.c_manual_ref_id, man.d_nombre as d_manual, ").append(" DECODE((select count(*) from OCAP_MANUALES_REF_CTES mr where mr.c_manual_ref_id = man.c_manual_ref_id and b_borrado='N'),0,'N','Y') bAsignadoCte ").append(" FROM itcp_manuales_ref man, ocap_grupo_categorias grc ").append(" WHERE man.c_grupo_categoria_id = grc.c_grupo_categoria_id AND man.b_borrado = 'N' AND grc.b_borrado = 'N' ");

      if (cCteId == 0L)
        consulta.append(" AND man.c_manual_ref_id NOT IN (select c_manual_ref_id from OCAP_MANUALES_REF_CTES where b_borrado='N') ");
      else if (verNoAsignadosCte) {
        consulta.append(" AND (man.c_manual_ref_id IN (select c_manual_ref_id from OCAP_MANUALES_REF_CTES where b_borrado='N' AND c_cte_id =? ) ").append("OR man.c_manual_ref_id NOT IN (select c_manual_ref_id from OCAP_MANUALES_REF_CTES where b_borrado='N')) ");
      }
      else {
        consulta.append(" AND man.c_manual_ref_id IN (select c_manual_ref_id from OCAP_MANUALES_REF_CTES where b_borrado='N' AND c_cte_id =? ) ");
      }
      consulta.append(" ORDER BY man.c_grupo_categoria_id, man.d_nombre ");

      st = con.prepareStatement(consulta.toString(), 1004, 1008);
      if (cCteId != 0L)
        st.setLong(1, cCteId);
      rs = st.executeQuery();

      listaGrupos = new ArrayList();
      int i = 0;
      while (rs.next()) {
        if (idGrupo != rs.getLong("c_grupo_categoria_id")) {
          if (idGrupo != 0L) {
            OCAPCategProfesionalesOT profOT = new OCAPCategProfesionalesOT();
            profOT.setCProfesionalId(idGrupo);
            profOT.setDNombre(dGrupo);
            profOT.setListaCategorias(listaManuales);
            listaGrupos.add(profOT);
          }
          idGrupo = rs.getLong("c_grupo_categoria_id");
          dGrupo = rs.getString("d_grupo");
          listaManuales = new ArrayList();
        }
        datos = new OCAPItinerariosOT();
        datos.setDNombre(rs.getString("d_manual"));
        datos.setCItinerarioId(rs.getLong("C_MANUAL_REF_ID"));
        datos.setBAsignadoCte(rs.getString("bAsignadoCte"));
        listaManuales.add(datos);
      }

      if (idGrupo != 0L) {
        OCAPCategProfesionalesOT profOT = new OCAPCategProfesionalesOT();
        profOT.setCProfesionalId(idGrupo);
        profOT.setDNombre(dGrupo);
        profOT.setListaCategorias(listaManuales);
        listaGrupos.add(profOT);
      }

      this.loggerBD.info("Fin");
    } catch (SQLException ex) {
      throw ex;
    } finally {
      if (rs != null)
        rs.close();
      if (st != null)
        st.close();
      JCYLGestionTransacciones.close(con.getAutoCommit());
    }

    return listaGrupos;
  }

  public ArrayList listarItinerariosConvocatoria(long cConvocatoriaId, long cCompfqsId)
    throws SQLException, Exception
  {
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = JCYLGestionTransacciones.getConnection();
    StringBuffer consulta = new StringBuffer();
    ArrayList listado = null;
    OCAPItinerariosOT datos = null;
    try
    {
      consulta.append("SELECT distinct eva.c_manual_evaluacion_id, eva.d_nombre, eva.d_descripcion ").append(" FROM OCAP_COMPFQS_CONVOCATORIAS asi,  ITCP_MANUALES_EVALUACION eva ").append(" WHERE asi.C_ITINERARIO_ID = eva.C_MANUAL_EVALUACION_ID ").append(" AND asi.B_BORRADO = 'N'  AND eva.B_BORRADO = 'N'").append(" AND asi.C_COMPFQS_ID  = " + cCompfqsId).append(" AND asi.C_CONVOCATORIA_ID =  " + cConvocatoriaId);

      consulta.append(" ORDER BY D_DESCRIPCION ");

      st = con.prepareStatement(consulta.toString(), 1004, 1008);
      rs = st.executeQuery();
      listado = new ArrayList();
      int i = 0;
      while (rs.next()) {
        datos = new OCAPItinerariosOT();
        datos.setDNombre(rs.getString("D_NOMBRE"));
        datos.setDDescripcion(rs.getString("D_DESCRIPCION"));
        datos.setCItinerarioId(rs.getLong("c_manual_evaluacion_id"));
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
}
