 package es.jcyl.fqs.ocap.ln.componentesCtes;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.compfqsConvocatorias.OCAPCompfqsConvocatoriasOAD;
 import es.jcyl.fqs.ocap.oad.componentesfqs.OCAPComponentesfqsOAD;
 import es.jcyl.fqs.ocap.oad.expteComponentesfqs.OCAPExpteComponentesfqsOAD;
 import es.jcyl.fqs.ocap.oad.perfiles.OCAPPerfilesOAD;
 import es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT;
 import es.jcyl.fqs.ocap.ot.perfiles.OCAPPerfilesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 import org.apache.struts.util.LabelValueBean;
 
 public class OCAPComponentesCtesLN
 {
   public Logger loggerBD;
   OCAPComponentesfqsOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPComponentesCtesLN(JCYLUsuario jcylUsuario)
   {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPComponentesCtes(OCAPComponentesfqsOT componente, String opcion)
     throws Exception
   {
     int result = 0;
     long idComp = 0L;
     long idPerfil = 0L;
     int total = 0;
     ArrayList array = null;
     Connection con = null;
     try
     {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       OCAPExpteComponentesfqsOAD expteComponentesfqsOAD = OCAPExpteComponentesfqsOAD.getInstance();
       OCAPPerfilesOAD perfilesOAD = OCAPPerfilesOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
 
       total = this.variableOAD.listadoOCAPDniCTECuenta(componente.getCCteId(), componente.getCDni(), 4);
 
       if (total == 0) {
         array = perfilesOAD.consultaOCAPPerfiles(0L, opcion, "", "", "", 1, 1);
 
         Object[] listadoelementos = array.toArray();
 
         componente.setCPerfilId(((OCAPPerfilesOT)listadoelementos[0]).getCPerfilId());
 
         JCYLGestionTransacciones.open(false);
 
         idComp = this.variableOAD.altaOCAPComponentesfqs(componente);
 
         componente.setCCompfqsId(idComp);
 
         result = expteComponentesfqsOAD.altaOCAPExpteComponentesfqs(componente);
 
         if (result == 1) {
           OCAPCompfqsConvocatoriasOAD convocatoriasOAD = OCAPCompfqsConvocatoriasOAD.getInstance();
           result = convocatoriasOAD.altaOCAPCompfqsConvocatorias(componente, true);
         }
 
         JCYLGestionTransacciones.commit(true);
       } else {
         throw new Exception("ORA-00001");
       }
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       JCYLGestionTransacciones.rollback();
       throw exSQL;
     }
     catch (Exception e)
     {
       JCYLGestionTransacciones.rollback();
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
 
     return result;
   }
 
   public OCAPComponentesfqsOT buscarOCAPComponentesCtes(OCAPComponentesfqsOT datos)
     throws Exception
   {
     OCAPComponentesfqsOT dato = null;
     OCAPComponentesfqsOT cte = null;
     ArrayList array = null;
     long cPerfilId = 0L;
     try
     {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       OCAPCompfqsConvocatoriasOAD convocatoriasOAD = OCAPCompfqsConvocatoriasOAD.getInstance();
 
       if ((datos.getCDni() != null) && (!datos.getCDni().equals(""))) {
         this.variableOAD = OCAPComponentesfqsOAD.getInstance();
         OCAPPerfilesOAD perfilesOAD = OCAPPerfilesOAD.getInstance();
 
         array = perfilesOAD.consultaOCAPPerfiles(0L, "CTE", "", "", "", 1, 1);
 
         Object[] listadoelementos = array.toArray();
 
         cPerfilId = ((OCAPPerfilesOT)listadoelementos[0]).getCPerfilId();
 
         dato = this.variableOAD.buscarOCAPComponenteDni(datos.getCDni(), cPerfilId);
 
         if (dato.getCCompfqsId() != 0L)
           datos.setCCompfqsId(dato.getCCompfqsId());
       }
       else {
         dato = this.variableOAD.buscarOCAPComponentesfqs(datos.getCCompfqsId());
       }
 
       cte = buscarOCAPComponentescte(datos.getCCompfqsId());
 
       dato.setCCteId(cte.getCCteId());
       dato.setFFinalizacion(cte.getFFinalizacion());
       dato.setFVinculacion(cte.getFVinculacion());
       dato.setCCompfqsConvoId(cte.getCCompfqsConvoId());
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return dato;
   }
 
   public int modificacionOCAPComponentesCtes(OCAPComponentesfqsOT datos)
     throws Exception
   {
     int result = 0;
     try
     {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       OCAPExpteComponentesfqsOAD expteComponentesfqsOAD = OCAPExpteComponentesfqsOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
 
       result = this.variableOAD.modificacionOCAPComponentesfqs(datos);
 
       if (result != 0) {
         result = expteComponentesfqsOAD.modificacionOCAPExpteComponentesfqs(datos);
 
         if (result != 0) {
           OCAPCompfqsConvocatoriasOAD convocatoriasOAD = OCAPCompfqsConvocatoriasOAD.getInstance();
           result = convocatoriasOAD.modificacionOCAPConvocatoria(datos);
         }
       }
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public ArrayList listarComponentes(OCAPComponentesfqsOT datos, String opcion, int primerRegistro, int registrosPorPagina)
     throws Exception
   {
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       OCAPPerfilesOAD perfilesOAD = OCAPPerfilesOAD.getInstance();
 
       array = perfilesOAD.consultaOCAPPerfiles(0L, opcion, "", "", "", 1, 1);
 
       Object[] listadoelementos = array.toArray();
 
       datos.setCPerfilId(((OCAPPerfilesOT)listadoelementos[0]).getCPerfilId());
 
       array = this.variableOAD.listarComponentes(datos, opcion, primerRegistro, registrosPorPagina);
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public int listarComponentesCuenta(OCAPComponentesfqsOT datos, String opcion)
     throws Exception
   {
     int total = 0;
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
 
       total = this.variableOAD.listarComponentesCuenta(datos, opcion);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public int bajaOCAPComponentesCtes(OCAPComponentesfqsOT datos)
     throws Exception
   {
     int result = 0;
     try
     {
       OCAPConfigApp.logger.info("bajaOCAPComponentesCtes");
 
       JCYLGestionTransacciones.open(false);
 
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       OCAPExpteComponentesfqsOAD expteComponentesfqsOAD = OCAPExpteComponentesfqsOAD.getInstance();
 
       result = expteComponentesfqsOAD.bajaOCAPComponentesCtes(datos);
 
       if (result != 0) {
         result = this.variableOAD.bajaOCAPComponentesCtes(datos);
       }
 
       JCYLGestionTransacciones.commit(true);
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       JCYLGestionTransacciones.rollback();
       throw exSQL;
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       JCYLGestionTransacciones.rollback();
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
 
     return result;
   }
 
   public ArrayList listadoCargos()
     throws Exception
   {
     ArrayList listado = new ArrayList();
 
     listado.add(new LabelValueBean("Presidente", "1"));
     listado.add(new LabelValueBean("Vocal", "2"));
 
     return listado;
   }
 
   public ArrayList buscarOCAPEvaluadorescte(OCAPComponentesfqsOT datos)
     throws Exception
   {
     ArrayList array = null;
     long cPerfilId = 0L;
     try
     {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       OCAPCompfqsConvocatoriasOAD convocatoriasOAD = OCAPCompfqsConvocatoriasOAD.getInstance();
 
       OCAPPerfilesOAD perfilesOAD = OCAPPerfilesOAD.getInstance();
 
       array = perfilesOAD.consultaOCAPPerfiles(0L, "Evaluador", "", "", "", 1, 1);
 
       Object[] listadoelementos = array.toArray();
 
       cPerfilId = ((OCAPPerfilesOT)listadoelementos[0]).getCPerfilId();
 
       array = convocatoriasOAD.buscarOCAPEvaluadorescte(datos.getCCteId(), cPerfilId);
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public OCAPComponentesfqsOT buscarCteUsuario(long cExpId)
     throws Exception
   {
     OCAPComponentesfqsOT result = null;
     try
     {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       OCAPExpteComponentesfqsOAD expteComponentesfqsOAD = OCAPExpteComponentesfqsOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
 
       result = this.variableOAD.buscarCteUsuario(cExpId);
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public ArrayList listarEvaluados(long cCteId, int primerRegistro, int registrosPorPagina)
     throws Exception
   {
     ArrayList array = null;
     ArrayList listadoEvaluadores = null;
     ArrayList listadoEvaluados = null;
     OCAPComponentesfqsOT evaluadorOT = null;
     OCAPComponentesfqsOT evaluadoresOT = null;
     long cPerfilId = 0L;
     String evalAnt = "";
     long codEvalAnt = 0L;
     try
     {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
 
       OCAPPerfilesOAD perfilesOAD = OCAPPerfilesOAD.getInstance();
 
       array = perfilesOAD.consultaOCAPPerfiles(0L, "Evaluador", "", "", "", 1, 1);
 
       Object[] listadoelementos = array.toArray();
 
       cPerfilId = ((OCAPPerfilesOT)listadoelementos[0]).getCPerfilId();
 
       array = this.variableOAD.listarEvaluados(cCteId, cPerfilId, primerRegistro, registrosPorPagina);
 
       listadoEvaluadores = new ArrayList();
       listadoEvaluados = new ArrayList();
       for (int i = 0; i < array.size(); i++) {
         evaluadorOT = (OCAPComponentesfqsOT)array.get(i);
         if (!evalAnt.equals(evaluadorOT.getCodigoId()))
         {
           if ((listadoEvaluados != null) && (listadoEvaluados.size() > 0)) {
             evaluadoresOT = new OCAPComponentesfqsOT();
             evaluadoresOT.setCodigoId(evalAnt);
             evaluadoresOT.setCCompfqsId(codEvalAnt);
             evaluadoresOT.setListaEvaluados(listadoEvaluados);
             listadoEvaluadores.add(evaluadoresOT);
           }
 
           evalAnt = evaluadorOT.getCodigoId();
           codEvalAnt = evaluadorOT.getCCompfqsId();
 
           listadoEvaluados = new ArrayList();
           listadoEvaluados.add(evaluadorOT);
         }
         else if ((evaluadorOT.getCodigoId() != null) && (evaluadorOT.getCodigoId().equals(evalAnt))) {
           listadoEvaluados.add(evaluadorOT);
         } else {
           evaluadoresOT = new OCAPComponentesfqsOT();
           evaluadoresOT.setCodigoId(evalAnt);
           evaluadoresOT.setCCompfqsId(codEvalAnt);
           evaluadoresOT.setListaEvaluados(listadoEvaluados);
           listadoEvaluadores.add(evaluadoresOT);
 
           evalAnt = evaluadorOT.getCodigoId();
           codEvalAnt = evaluadorOT.getCCompfqsId();
         }
 
       }
 
       if (listadoEvaluados.size() > 0) {
         evaluadoresOT = new OCAPComponentesfqsOT();
         evaluadoresOT.setCodigoId(evalAnt);
         evaluadoresOT.setCCompfqsId(codEvalAnt);
         evaluadoresOT.setListaEvaluados(listadoEvaluados);
         listadoEvaluadores.add(evaluadoresOT);
       }
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return listadoEvaluadores;
   }
 
   public int contarEvaluados(long cCteId)
     throws Exception
   {
     ArrayList array = null;
     long cPerfilId = 0L;
     int contador = 0;
     try
     {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       OCAPPerfilesOAD perfilesOAD = OCAPPerfilesOAD.getInstance();
 
       array = perfilesOAD.consultaOCAPPerfiles(0L, "Evaluador", "", "", "", 1, 1);
 
       Object[] listadoelementos = array.toArray();
 
       cPerfilId = ((OCAPPerfilesOT)listadoelementos[0]).getCPerfilId();
 
       contador = this.variableOAD.contarEvaluados(cCteId, cPerfilId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return contador;
   }
 
   public OCAPComponentesfqsOT buscarOCAPComponentescte(long cCompfqsId)
     throws Exception
   {
     OCAPComponentesfqsOT datos = null;
     try
     {
       OCAPCompfqsConvocatoriasOAD convocatoriasOAD = OCAPCompfqsConvocatoriasOAD.getInstance();
       datos = convocatoriasOAD.buscarOCAPComponentescte(cCompfqsId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return datos;
   }
 
   public long buscarGerenciaCte(String dni)
     throws Exception
   {
     long cGerenciaId = 0L;
     try
     {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       cGerenciaId = this.variableOAD.buscarGerenciaCte(dni);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return cGerenciaId;
   }
 
   public String buscarNombreCte(String dni)
     throws Exception
   {
     String nombre = "";
     try
     {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       nombre = this.variableOAD.buscarNombreCte(dni);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return nombre;
   }
 }

