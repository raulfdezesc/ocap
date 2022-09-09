 package es.jcyl.fqs.ocap.oad.historicoProfesionales;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.historicoProfesionales.OCAPHistoricoProfesionalesOT;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPHistoricoProfesionalesOAD
 {
   public static Logger logger = OCAPConfigApp.logger;
   private static OCAPHistoricoProfesionalesOAD instance;
 
   public static OCAPHistoricoProfesionalesOAD getInstance()
   {
     if (instance == null) {
       instance = new OCAPHistoricoProfesionalesOAD();
     }
     return instance;
   }
 
   public ArrayList buscarHistorico(String dni)
     throws Exception
   {
     logger.info(getClass().getName() + " buscarHistorico: Inicio");
     PreparedStatement sentencia = null;
     ResultSet resultado = null;
     Connection con = null;
     StringBuffer consulta = new StringBuffer();
     OCAPHistoricoProfesionalesOT datos = null;
     ArrayList listado = null;
     try
     {
       con = JCYLGestionTransacciones.getConnection();
 
       consulta.append("SELECT HIST.C_HISTORICO_ID, HIST.D_NOMBRE, HIST.D_APELLIDOS, HIST.C_DNI, TO_CHAR(HIST.F_RESOLUCION,'DD/MM/YYYY')F_RESOLUCION, ").append(" HIST.C_GRADO_ID, (GRAD.D_DESCRIPCION)D_GRADO_OCAP, ").append(" HIST.C_GERENCIA_ID, HIST.D_GERENCIA, (GERE.D_NOMBRE)D_GERENCIA_OCAP, ").append(" HIST.C_CATEGORIA_ID, HIST.D_CATEGORIA, (CATE.D_NOMBRE)D_CATEGORIA_OCAP, ").append(" HIST.C_ESPECIALIDAD_ID, HIST.D_ESPECIALIDAD, (ESPE.D_NOMBRE)D_ESPECIALIDAD_OCAP, ").append(" HIST.C_ESTADO_ID, (ESTA.D_NOMBRE)D_ESTADO_OCAP, ").append(" HIST.D_PROCEDIMIENTO, HIST.D_MOTIVOEXCLUSION ").append(" FROM OCAP_HISTORICOPROFESIONALES HIST, OCAP_GRADOS GRAD, OCAP_ESTADOS ESTA, OCAP_GERENCIAS GERE, ").append("      OCAP_CATEG_PROFESIONALES CATE, OCAP_ESPECIALIDADES ESPE ").append(" WHERE UPPER(LPAD(C_DNI,10,'0')) = UPPER(LPAD(?,10,'0')) ").append("   AND HIST.C_GRADO_ID = GRAD.C_GRADO_ID ").append("   AND HIST.C_ESTADO_ID = ESTA.C_ESTADO_ID ").append("   AND HIST.C_GERENCIA_ID = GERE.C_GERENCIA_ID (+) ").append("   AND HIST.C_CATEGORIA_ID = CATE.C_PROFESIONAL_ID (+) ").append("   AND HIST.C_ESPECIALIDAD_ID = ESPE.C_ESPEC_ID (+) ").append(" ORDER BY F_RESOLUCION ASC ");
 
       sentencia = con.prepareStatement(consulta.toString(), 1004, 1008);
       sentencia.setString(1, dni);
       resultado = sentencia.executeQuery();
       listado = new ArrayList();
       while (resultado.next()) {
         datos = new OCAPHistoricoProfesionalesOT();
         datos.setCHistoricoId(resultado.getLong("C_HISTORICO_ID"));
         datos.setDNombre(resultado.getString("D_NOMBRE"));
         datos.setDApellidos(resultado.getString("D_APELLIDOS"));
         datos.setCDni(resultado.getString("C_DNI"));
         datos.setFResolucion(resultado.getString("F_RESOLUCION"));
         datos.setCGradoId(resultado.getLong("C_GRADO_ID"));
         datos.setDGrado(resultado.getString("D_GRADO_OCAP"));
         datos.setCGerenciaId(resultado.getLong("C_GERENCIA_ID"));
         if ((resultado.getString("D_GERENCIA_OCAP") != null) && (!"".equals(resultado.getString("D_GERENCIA_OCAP"))))
           datos.setDGerencia(resultado.getString("D_GERENCIA_OCAP"));
         else
           datos.setDGerencia(resultado.getString("D_GERENCIA"));
         datos.setCCategoriaId(resultado.getLong("C_CATEGORIA_ID"));
         if ((resultado.getString("D_CATEGORIA_OCAP") != null) && (!"".equals(resultado.getString("D_CATEGORIA_OCAP"))))
           datos.setDCategoria(resultado.getString("D_CATEGORIA_OCAP"));
         else
           datos.setDCategoria(resultado.getString("D_CATEGORIA"));
         datos.setCEspecialidadId(resultado.getLong("C_ESPECIALIDAD_ID"));
         if ((resultado.getString("D_ESPECIALIDAD_OCAP") != null) && (!"".equals(resultado.getString("D_ESPECIALIDAD_OCAP"))))
           datos.setDEspecialidad(resultado.getString("D_ESPECIALIDAD_OCAP"));
         else
           datos.setDEspecialidad(resultado.getString("D_ESPECIALIDAD"));
         datos.setCEstadoId(resultado.getLong("C_ESTADO_ID"));
         datos.setDEstado(resultado.getString("D_ESTADO_OCAP"));
         datos.setDProcedimiento(resultado.getString("D_PROCEDIMIENTO"));
         datos.setDMotivoExclusion(resultado.getString("D_MOTIVOEXCLUSION"));
         listado.add(datos);
       }
 
       logger.info(getClass().getName() + " buscarHistorico: Fin");
     }
     catch (SQLException e) {
       logger.error(getClass().getName() + " buscarHistorico: ERROR: " + e.toString());
       throw new Exception(e);
     } finally {
       if (resultado != null)
         resultado.close();
       if (sentencia != null)
         sentencia.close();
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return listado;
   }
 }

