 package es.jcyl.fqs.ocap.ln.evaluadores;
 
 import es.jcyl.cf.seguridad.util.Usuario;
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.compfqsConvocatorias.OCAPCompfqsConvocatoriasOAD;
 import es.jcyl.fqs.ocap.oad.componentesfqs.OCAPComponentesfqsOAD;
 import es.jcyl.fqs.ocap.oad.expedienteCarrera.OCAPExpedientecarreraOAD;
 import es.jcyl.fqs.ocap.oad.expteComponentesfqs.OCAPExpteComponentesfqsOAD;
 import es.jcyl.fqs.ocap.oad.perfiles.OCAPPerfilesOAD;
 import es.jcyl.fqs.ocap.oad.usuarios.OCAPUsuariosOAD;
 import es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT;
 import es.jcyl.fqs.ocap.ot.perfiles.OCAPPerfilesOT;
 import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.SQLException;
 import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;
 import org.apache.struts.util.LabelValueBean;
 
 public class OCAPEvaluadoresLN
 {
   public Logger loggerBD;
   OCAPComponentesfqsOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public OCAPEvaluadoresLN(JCYLUsuario jcylUsuario)
   {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int altaOCAPEvaluadores(OCAPComponentesfqsOT componente)
     throws Exception
   {
     int result = 0;
     long idComp = 0L;
     int total = 0;
     ArrayList array = null;
     Connection con = null;
     try
     {
       OCAPConfigApp.logger.info("altaOCAPEvaluadores");
 
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       OCAPExpteComponentesfqsOAD expteComponentesfqsOAD = OCAPExpteComponentesfqsOAD.getInstance();
       OCAPPerfilesOAD perfilesOAD = OCAPPerfilesOAD.getInstance();
 
       total = this.variableOAD.listadoOCAPDniCuenta(componente.getCDni(), 3);
 
       if (total == 0) {
         array = perfilesOAD.consultaOCAPPerfiles(0L, "Evaluador", "", "", "", 1, 1);
 
         Object[] listadoelementos = array.toArray();
 
         componente.setCPerfilId(((OCAPPerfilesOT)listadoelementos[0]).getCPerfilId());
 
         JCYLGestionTransacciones.open(false);
 
         idComp = this.variableOAD.altaOCAPComponentesfqs(componente);
 
         componente.setCCompfqsId(idComp);
 
         result = expteComponentesfqsOAD.altaOCAPExpteComponentesfqs(componente);
 
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
     catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
 
     return result;
   }
 
   public ArrayList consultaOCAPEvaluadores(OCAPComponentesfqsOT componentesOT, String opcion, int primerRegistro, int registrosPorPagina)
     throws Exception
   {
     ArrayList array = null;
     ArrayList listadoCte = null;
     ArrayList listadoEval = null;
     OCAPComponentesfqsOT datos = null;
     OCAPComponentesfqsOT evaluador = null;
     OCAPComponentesfqsOT cte = null;
     long cteAnt = 0L;
     try
     {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       OCAPPerfilesOAD perfilesOAD = OCAPPerfilesOAD.getInstance();
 
       array = perfilesOAD.consultaOCAPPerfiles(0L, "Evaluador", "", "", "", 1, 1);
 
       Object[] listadoelementos = array.toArray();
 
       componentesOT.setCPerfilId(((OCAPPerfilesOT)listadoelementos[0]).getCPerfilId());
 
       if (!opcion.equals("ce")) {
         array = this.variableOAD.consultaOCAPEvaluadores(componentesOT, opcion, primerRegistro, registrosPorPagina, null);
       } else {
         array = this.variableOAD.consultaOCAPEvaluadoresCte(componentesOT, primerRegistro, registrosPorPagina);
 
         listadoCte = new ArrayList();
 
         for (int i = 0; i < array.size(); i++) {
           datos = (OCAPComponentesfqsOT)array.get(i);
 
           evaluador = new OCAPComponentesfqsOT();
           evaluador.setDApellidos(datos.getDApellidos());
           evaluador.setDNombre(datos.getDNombre());
           evaluador.setCDni(datos.getCDni());
           evaluador.setATitulacion(datos.getATitulacion());
           evaluador.setAEspecialidad(datos.getAEspecialidad());
           evaluador.setCCteId(datos.getCCteId());
           evaluador.setCCompfqsId(datos.getCCompfqsId());
 
           if ((datos.getCCteId() != 0L) && (cteAnt != datos.getCCteId())) {
             if ((listadoEval != null) && (listadoEval.size() > 0)) {
               cte = new OCAPComponentesfqsOT();
 
               cte.setListaEvalCte(listadoEval);
 
               cte.setCCteId(cteAnt);
 
               listadoCte.add(cte);
             }
 
             listadoEval = new ArrayList();
 
             cteAnt = datos.getCCteId();
           }
 
           listadoEval.add(evaluador);
         }
 
         if (array.size() > 0) {
           cte = new OCAPComponentesfqsOT();
 
           cte.setListaEvalCte(listadoEval);
 
           cte.setCCteId(cteAnt);
 
           listadoCte.add(cte);
         }
       }
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     if (!opcion.equals("ce"))
     {
       return array;
     }
 
     return listadoCte;
   }
 
   public int listadoOCAPEvaluadoresCuenta(OCAPComponentesfqsOT componentesOT, String opcion)
     throws Exception
   {
     int total = 0;
     ArrayList array = null;
     try
     {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       OCAPPerfilesOAD perfilesOAD = OCAPPerfilesOAD.getInstance();
 
       array = perfilesOAD.consultaOCAPPerfiles(0L, "Evaluador", "", "", "", 1, 1);
 
       Object[] listadoelementos = array.toArray();
 
       componentesOT.setCPerfilId(((OCAPPerfilesOT)listadoelementos[0]).getCPerfilId());
 
       total = this.variableOAD.listadoOCAPEvaluadoresCuenta(componentesOT, opcion);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public OCAPComponentesfqsOT buscarOCAPEvaluadores(long cCompfqsId)
     throws Exception
   {
     OCAPComponentesfqsOT dato = null;
     try
     {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       dato = this.variableOAD.buscarOCAPComponentesfqs(cCompfqsId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return dato;
   }
 
   public OCAPComponentesfqsOT buscarOCAPEvaluadoresCategoria(OCAPComponentesfqsOT datos, long cItinerarioId)
     throws Exception
   {
     OCAPComponentesfqsOT dato = null;
     try {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
 
       dato = this.variableOAD.buscarOCAPComponentesfqsCategoria(datos, cItinerarioId);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return dato;
   }
 
   public OCAPComponentesfqsOT buscarOCAPEvaluadoresDNI(String cDni, long itinerarioId)
     throws Exception
   {
     OCAPComponentesfqsOT dato = null;
     ArrayList array = null;
     long cPerfilId = 0L;
     try {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
 
       OCAPCompfqsConvocatoriasOAD convocatoriasOAD = OCAPCompfqsConvocatoriasOAD.getInstance();
 
       OCAPPerfilesOAD perfilesOAD = OCAPPerfilesOAD.getInstance();
 
       array = perfilesOAD.consultaOCAPPerfiles(0L, "Evaluador", "", "", "", 1, 1);
 
       Object[] listadoelementos = array.toArray();
 
       cPerfilId = ((OCAPPerfilesOT)listadoelementos[0]).getCPerfilId();
 
       dato = this.variableOAD.buscarOCAPComponenteDni(cDni, cPerfilId);
 
       if (dato.getCCompfqsId() != 0L) {
         dato = buscarOCAPEvaluadores(dato.getCCompfqsId());
         dato = buscarOCAPEvaluadoresCategoria(dato, itinerarioId);
       }
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return dato;
   }
 
   public ArrayList buscarOCAPConvAnt(long cCompfqsId)
     throws Exception
   {
     ArrayList listado = null;
     try {
       OCAPCompfqsConvocatoriasOAD convocatoriasOAD = OCAPCompfqsConvocatoriasOAD.getInstance();
 
       listado = convocatoriasOAD.buscarOCAPConvAnt(cCompfqsId);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return listado;
   }
 
   public ArrayList listarEvaluados(OCAPUsuariosOT datos, JCYLUsuario jcylUsuario, int primerRegistro, int registrosPorPagina, int cConvocatoriaId)
     throws Exception
   {
     ArrayList array = null;
     ArrayList listadoEval = new ArrayList();
 
     ArrayList listadoCateg = null;
     OCAPUsuariosOT evaluado = null;
     OCAPUsuariosOT categ = null;
     int catAnt = 0;
     try {
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
       array = usuariosOAD.listarEvaluados(datos, jcylUsuario, primerRegistro, registrosPorPagina, cConvocatoriaId);
 
       listadoCateg = new ArrayList();
 
       for (int i = 0; i < array.size(); i++) {
         datos = (OCAPUsuariosOT)array.get(i);
 
         evaluado = new OCAPUsuariosOT();
         evaluado.setDApellido1(datos.getDApellido1());
         evaluado.setDNombre(datos.getDNombre());
         evaluado.setCDni(datos.getCDni());
         evaluado.setCProfesionalId(datos.getCProfesionalId());
         evaluado.setCodigoId(datos.getCodigoId());
         evaluado.setCExpId(datos.getCExpId());
         evaluado.setFInformeCte(datos.getFInformeCte());
         evaluado.setFInformeCe(datos.getFInformeCe());
         evaluado.setFInformeEval(datos.getFInformeEval());
         evaluado.setFInformeCC(datos.getFInformeCC());
         evaluado.setBContestado(datos.getBContestado());
         evaluado.setBSegundaEvaluacion(datos.getBSegundaEvaluacion());
         evaluado.setFInformeEval2(datos.getFInformeEval2());
         evaluado.setCItinerarioId(datos.getCItinerarioId());
         evaluado.setFAuditoriaPropuesta(datos.getFAuditoriaPropuesta());
         evaluado.setDConvocatoriaNombreCorto(datos.getDConvocatoriaNombreCorto());
         if ((datos.getCProfesionalId().intValue() != 0) && (catAnt != datos.getCProfesionalId().intValue())) {
           if ((listadoEval != null) && (listadoEval.size() > 0)) {
             categ = new OCAPUsuariosOT();
 
             categ.setListaCategorias(listadoEval);
             categ.setCProfesionalId(new Integer(catAnt));
 
             listadoCateg.add(categ);
           }
 
           listadoEval = new ArrayList();
 
           catAnt = datos.getCProfesionalId().intValue();
         }
 
         listadoEval.add(evaluado);
       }
 
       if (array.size() > 0) {
         categ = new OCAPUsuariosOT();
 
         categ.setListaCategorias(listadoEval);
         categ.setCProfesionalId(new Integer(catAnt));
 
         listadoCateg.add(categ);
       }
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
     if ((jcylUsuario.getUser().getRol() != null) && (((jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION)) && (datos.getCCompfqsId() == 0L)) || (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR)) || (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC))))
     {
       return listadoCateg;
     }
 
     return array;
   }
 
   public ArrayList listarEvaluadosEncuesta(OCAPUsuariosOT datos, JCYLUsuario jcylUsuario, int primerRegistro, int registrosPorPagina)
     throws Exception
   {
     ArrayList array = null;
     ArrayList listadoEval = null;
     ArrayList listadoCateg = null;
     ArrayList listadoIti = null;
     OCAPUsuariosOT evaluado = null;
     OCAPUsuariosOT categ = null;
     OCAPUsuariosOT iti = null;
     String catAnt = "";
     String espAnt = "";
     String itiAnt = "";
     try {
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
       array = usuariosOAD.listarEvaluados(datos, jcylUsuario, primerRegistro, registrosPorPagina, 0);
 
       listadoCateg = new ArrayList();
 
       for (int i = 0; i < array.size(); i++) {
         datos = (OCAPUsuariosOT)array.get(i);
         if (((!"".equals(datos.getDProfesional_nombre())) && (!catAnt.equals(datos.getDProfesional_nombre()))) || ((datos.getDEspec_nombre() != null) && (!"".equals(datos.getDEspec_nombre())) && (!espAnt.equals(datos.getDEspec_nombre()))))
         {
           if ((listadoIti != null) && (listadoEval != null) && ((listadoIti.size() > 0) || (listadoEval.size() > 0))) {
             iti = new OCAPUsuariosOT();
             iti.setDItinerario(itiAnt);
             iti.setListaCategorias(listadoEval);
             listadoIti.add(iti);
 
             categ = new OCAPUsuariosOT();
             categ.setListaItinerarios(listadoIti);
             categ.setDProfesional_nombre(catAnt);
             categ.setDEspec_nombre(espAnt);
 
             listadoCateg.add(categ);
           }
 
           itiAnt = datos.getDItinerario();
           catAnt = datos.getDProfesional_nombre();
           espAnt = datos.getDEspec_nombre() == null ? "" : datos.getDEspec_nombre();
 
           listadoIti = new ArrayList();
           listadoEval = new ArrayList();
 
           listadoEval.add(datos);
         }
         else if ((datos.getDItinerario() != null) && (datos.getDItinerario().equals(itiAnt))) {
           listadoEval.add(datos);
         } else {
           iti = new OCAPUsuariosOT();
           iti.setDItinerario(itiAnt);
           iti.setListaCategorias(listadoEval);
           listadoIti.add(iti);
 
           listadoEval = new ArrayList();
 
           listadoEval.add(datos);
 
           itiAnt = datos.getDItinerario();
         }
 
       }
 
       if (array.size() > 0) {
         iti = new OCAPUsuariosOT();
         iti.setDItinerario(itiAnt);
         iti.setListaCategorias(listadoEval);
         listadoIti.add(iti);
 
         categ = new OCAPUsuariosOT();
         categ.setListaItinerarios(listadoIti);
         categ.setDProfesional_nombre(catAnt);
         categ.setDEspec_nombre(espAnt);
 
         listadoCateg.add(categ);
       }
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
     if ((jcylUsuario.getUser().getRol() != null) && ((jcylUsuario.getUser().getRol().equals(Constantes.OCAP_DIRECCION)) || (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_ADMINISTRADOR)) || (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CC))))
     {
       return listadoCateg;
     }
 
     return array;
   }
 
   public int listarEvaluadosCuenta(OCAPUsuariosOT datos, JCYLUsuario jcylUsuario)
     throws Exception
   {
     int total = 0;
     try {
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
       total = usuariosOAD.listarEvaluadosCuenta(datos, jcylUsuario);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public int modificacionOCAPEvaluadores(OCAPComponentesfqsOT datos)
     throws Exception
   {
     int result = 0;
     int total = 0;
     ArrayList array = null;
     try {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       OCAPExpteComponentesfqsOAD expteComponentesfqsOAD = OCAPExpteComponentesfqsOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
 
       total = this.variableOAD.listadoOCAPDniCuenta(datos.getCDni(), 3);
 
       if (total != 0) {
         result = this.variableOAD.modificacionOCAPComponentesfqs(datos);
 
         if (result != 0)
           result = expteComponentesfqsOAD.modificacionOCAPExpteComponentesfqs(datos);
       }
       else {
         throw new Exception("Evaluador no existe");
       }
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public int activarEvaluadoresSegunda(OCAPComponentesfqsOT datos)
     throws Exception
   {
     int result = 0;
     int total = 0;
     ArrayList array = null;
     try {
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       OCAPExpteComponentesfqsOAD expteComponentesfqsOAD = OCAPExpteComponentesfqsOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
 
       result = this.variableOAD.activarEvaluadoresSegunda(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public ArrayList listadoOCAPEvaluadores(long cItin)
     throws Exception
   {
     ArrayList array = null;
     try
     {
       OCAPPerfilesOAD perfilesOAD = OCAPPerfilesOAD.getInstance();
       array = perfilesOAD.consultaOCAPPerfiles(0L, "Evaluador", "", "", "", 1, 1);
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       Object[] listadoelementos = array.toArray();
       OCAPComponentesfqsOT componente = new OCAPComponentesfqsOT();
       componente.setCPerfilId(((OCAPPerfilesOT)listadoelementos[0]).getCPerfilId());
       componente.setCItinerarioId(cItin);
       array = this.variableOAD.consultaOCAPEvaluadores(componente, "", 1, -1, null);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public ArrayList listadoOCAPEvaluadoresSegunda(long cItin)
     throws Exception
   {
     ArrayList array = new ArrayList();
     try {
       OCAPPerfilesOAD perfilesOAD = OCAPPerfilesOAD.getInstance();
       array = perfilesOAD.consultaOCAPPerfiles(0L, "Evaluador", "", "", "", 1, 1);
 
       this.variableOAD = OCAPComponentesfqsOAD.getInstance();
       Object[] listadoelementos = array.toArray();
 
       OCAPComponentesfqsOT componente = new OCAPComponentesfqsOT();
       componente.setCPerfilId(((OCAPPerfilesOT)listadoelementos[0]).getCPerfilId());
 
       componente.setCItinerarioId(cItin);
       array = this.variableOAD.consultaOCAPEvaluadores(componente, "", 1, -1, Constantes.SI);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return array;
   }
 
   public int asignarConvocatoria(OCAPComponentesfqsOT datos)
     throws Exception
   {
     int result = 0;
     try {
       OCAPCompfqsConvocatoriasOAD convocatoriasOAD = OCAPCompfqsConvocatoriasOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
 
       result = convocatoriasOAD.altaOCAPCompfqsConvocatorias(datos, true);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public ArrayList buscarOCAPConvocatoriasAct(long cCompfqsId)
     throws Exception
   {
     ArrayList listado = null;
     try {
       OCAPCompfqsConvocatoriasOAD convocatoriasOAD = OCAPCompfqsConvocatoriasOAD.getInstance();
 
       listado = convocatoriasOAD.buscarOCAPConvocatoriasAct(cCompfqsId);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return listado;
   }
 
   public OCAPComponentesfqsOT buscarOCAPConvEvaluador(long cConvoId)
     throws Exception
   {
     OCAPComponentesfqsOT dato = null;
     try {
       OCAPCompfqsConvocatoriasOAD convocatoriasOAD = OCAPCompfqsConvocatoriasOAD.getInstance();
 
       dato = convocatoriasOAD.buscarOCAPConvEvaluador(cConvoId);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return dato;
   }
 
   public int modificacionOCAPConvocatoria(OCAPComponentesfqsOT datos)
     throws Exception
   {
     int result = 0;
     try
     {
       OCAPCompfqsConvocatoriasOAD convocatoriasOAD = OCAPCompfqsConvocatoriasOAD.getInstance();
 
       result = convocatoriasOAD.modificacionOCAPConvocatoria(datos);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public ArrayList listadoEstados()
     throws Exception
   {
     ArrayList listado = new ArrayList();
 
     listado.add(new LabelValueBean("Fin autoevaluación".toUpperCase(), "4"));
     listado.add(new LabelValueBean("Fin evaluación".toUpperCase(), "8"));
     listado.add(new LabelValueBean("Fuera de plazo".toUpperCase(), "5"));
     listado.add(new LabelValueBean("Inicio evaluanet".toUpperCase(), "2"));
     listado.add(new LabelValueBean("Proceso autoevaluación".toUpperCase(), "3"));
     listado.add(new LabelValueBean("Proceso evaluación".toUpperCase(), "6"));
     listado.add(new LabelValueBean("Segunda evaluación".toUpperCase(), "9"));
     listado.add(new LabelValueBean("Sin evidencias profesionales".toUpperCase(), "11"));
     listado.add(new LabelValueBean("Sin Informe CTE".toUpperCase(), "10"));
     listado.add(new LabelValueBean("Sin informe Eval".toUpperCase(), "7"));
     listado.add(new LabelValueBean("Sin seleccionar itinerario".toUpperCase(), "1"));
     
     return listado;
   }
   
   public ArrayList listadoEstadosCTE()
		     throws Exception
		   {
		     ArrayList listado = new ArrayList();
		 
		     listado.add(new LabelValueBean("Sin Informe Eval".toUpperCase(), "7"));
		     listado.add(new LabelValueBean("Sin Informe CTE".toUpperCase(), "8"));	 
		     return listado;
		   }
 
   public OCAPUsuariosOT buscarIndicador(String opcion)
     throws Exception
   {
     ArrayList array = null;
     ArrayList listadoIndicador = null;
     OCAPUsuariosOT total = new OCAPUsuariosOT();
     OCAPUsuariosOT datos = null;
     OCAPUsuariosOT datosAnt = null;
     long indAnt = 0L;
     long indAct = 0L;
     int totalEval = 0;
     int totalEvalOK = 0;
     int totalEvalKO = 0;
     int totalInd = 0;
     int totalIndOK = 0;
     int totalIndKO = 0;
     float porcIndOK = 0.0F;
     float porcIndKO = 0.0F;
     try {
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
       array = usuariosOAD.buscarIndicador(opcion);
 
       listadoIndicador = new ArrayList();
 
       for (int i = 0; i < array.size(); i++) {
         datos = (OCAPUsuariosOT)array.get(i);
         if (opcion.equals("eval"))
           indAct = datos.getCCompfqsId();
         else if (opcion.equals("itin"))
           indAct = datos.getCItinerarioId();
         else if (opcion.equals("gere"))
           indAct = datos.getCGerenciaId().longValue();
         else if (opcion.equals("evalfqs")) {
           indAct = datos.getCCompfqsId();
         }
         if ((indAct != 0L) && (indAnt != indAct)) {
           totalEval += 1;
           if (i > 0) {
             datosAnt = (OCAPUsuariosOT)array.get(i - 1);
             datosAnt.setTotalIndicador(totalIndOK + totalIndKO);
             datosAnt.setTotalIndicadorOK(totalIndOK);
             datosAnt.setTotalIndicadorKO(totalIndKO);
 
             porcIndOK = (float)(100.0D * totalIndOK / (totalIndOK + totalIndKO));
             porcIndKO = (float)(100.0D * totalIndKO / (totalIndOK + totalIndKO));
 
             porcIndOK = (float)(Math.rint(porcIndOK * 100.0F) / 100.0D);
             porcIndKO = (float)(Math.rint(porcIndKO * 100.0F) / 100.0D);
 
             datosAnt.setPorcIndicadorOK(porcIndOK);
             datosAnt.setPorcIndicadorKO(porcIndKO);
 
             listadoIndicador.add(datosAnt);
           }
           totalIndOK = 0;
           totalIndKO = 0;
 
           indAnt = indAct;
         }
 
         if (Constantes.SI.equals(datos.getBReconocimientoGrado())) {
           totalEvalOK += datos.getTotalIndicador();
           totalIndOK += datos.getTotalIndicador();
         } else {
           totalEvalKO += datos.getTotalIndicador();
           totalIndKO += datos.getTotalIndicador();
         }
 
       }
 
       if (array.size() > 0) {
         datosAnt = (OCAPUsuariosOT)array.get(array.size() - 1);
         datosAnt.setTotalIndicador(totalIndOK + totalIndKO);
         datosAnt.setTotalIndicadorOK(totalIndOK);
         datosAnt.setTotalIndicadorKO(totalIndKO);
 
         porcIndOK = (float)(100.0D * totalIndOK / (totalIndOK + totalIndKO));
         porcIndKO = (float)(100.0D * totalIndKO / (totalIndOK + totalIndKO));
         porcIndOK = (float)(Math.rint(porcIndOK * 100.0F) / 100.0D);
         porcIndKO = (float)(Math.rint(porcIndKO * 100.0F) / 100.0D);
 
         datosAnt.setPorcIndicadorOK(porcIndOK);
         datosAnt.setPorcIndicadorKO(porcIndKO);
 
         listadoIndicador.add(datos);
       }
 
       total.setTotalEvaluados(totalEvalOK + totalEvalKO);
       total.setTotalIndicadores(totalEval);
       total.setTotalIndicadoresOK(totalEvalOK);
       total.setTotalIndicadoresKO(totalEvalKO);
       total.setListaIndicadores(listadoIndicador);
 
       float porcOK = (float)(100.0D * totalEvalOK / (totalEvalOK + totalEvalKO));
       float porcKO = (float)(100.0D * totalEvalKO / (totalEvalOK + totalEvalKO));
       porcOK = (float)(Math.rint(porcOK * 100.0F) / 100.0D);
       porcKO = (float)(Math.rint(porcKO * 100.0F) / 100.0D);
 
       total.setPorcIndicadoresOK(porcOK);
       total.setPorcIndicadoresKO(porcKO);
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public int asignarConvocatoriaYEvaluados(OCAPComponentesfqsOT datos, long cGerenciaId, String usuario)
     throws Exception
   {
     int result = 0;
     try {
       OCAPCompfqsConvocatoriasOAD convocatoriasOAD = OCAPCompfqsConvocatoriasOAD.getInstance();
       OCAPConfigApp.logger.info("Inicio asignarConvocatoriaYEvaluados");
       JCYLGestionTransacciones.open(false);
       OCAPConfigApp.logger.info("asignarConvocatoriaYEvaluados: se asigna convocatorias");
       result = convocatoriasOAD.altaOCAPCompfqsConvocatorias(datos, false);
 
       OCAPConfigApp.logger.info("asignarConvocatoriaYEvaluados: se asigna evaluados");
       OCAPExpedientecarreraOAD expedientecarreraOAD = OCAPExpedientecarreraOAD.getInstance();
       expedientecarreraOAD.asignarConvocatoriaYEvaluados(datos, cGerenciaId, usuario);
       JCYLGestionTransacciones.commit(true);
       OCAPConfigApp.logger.info("fin asignarConvocatoriaYEvaluados");
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return result;
   }
 
   public int asignarEvaluador(long cCompfqsId, long[] listaPosiblesEvaluados, String usuario)
     throws Exception
   {
     int result = 0;
     try {
       OCAPConfigApp.logger.info("inicio asignarEvaluador");
 
       OCAPExpedientecarreraOAD expedientecarreraOAD = OCAPExpedientecarreraOAD.getInstance();
       result = expedientecarreraOAD.asignarEvaluador(cCompfqsId, listaPosiblesEvaluados, usuario);
 
       OCAPConfigApp.logger.info("fin asignarEvaluador");
     } catch (SQLException exSQL) {
       JCYLGestionTransacciones.rollback();
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       OCAPConfigApp.logger.info(e);
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
 
     return result;
   }
 
   public ArrayList listarEvaluadosGerenciaCTE(OCAPUsuariosOT datos, JCYLUsuario jcylUsuario, int primerRegistro, int registrosPorPagina)
     throws Exception
   {
     ArrayList array = null;
     ArrayList listadoEval = new ArrayList();
 
     ArrayList listadoCateg = null;
     OCAPUsuariosOT evaluado = null;
     OCAPUsuariosOT categ = null;
     int catAnt = 0;
     try {
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
       array = usuariosOAD.listarEvaluadosGerenciaCTE(datos, primerRegistro, registrosPorPagina, jcylUsuario);
 
       listadoEval = new ArrayList();
 
       for (int i = 0; i < array.size(); i++) {
         datos = (OCAPUsuariosOT)array.get(i);
 
         evaluado = new OCAPUsuariosOT();
         evaluado.setDConvocatoriaNombreCorto(datos.getDConvocatoriaNombreCorto());
         evaluado.setDApellido1(datos.getDApellido1());
         evaluado.setDNombre(datos.getDNombre());
         evaluado.setCProfesionalId(datos.getCProfesionalId());
         evaluado.setCodigoId(datos.getCodigoId());
         evaluado.setCExpId(datos.getCExpId());
         evaluado.setFInformeCte(datos.getFInformeCte());
         evaluado.setFInformeCe(datos.getFInformeCe());
         evaluado.setFInformeEval(datos.getFInformeEval());
         evaluado.setFInformeCC(datos.getFInformeCC());
         evaluado.setBContestado(datos.getBContestado());
         evaluado.setBSegundaEvaluacion(datos.getBSegundaEvaluacion());
         evaluado.setFInformeEval2(datos.getFInformeEval2());
         evaluado.setCItinerarioId(datos.getCItinerarioId());
         evaluado.setFAuditoriaPropuesta(datos.getFAuditoriaPropuesta());
         evaluado.setCCompfqsId(datos.getCCompfqsId());
         evaluado.setCDni(datos.getCDni());
         evaluado.setCEspecId(datos.getCEspecId());
         
         catAnt = datos.getCProfesionalId().intValue();
 
         listadoEval.add(evaluado);
       }
 
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return listadoEval;
   }
 
   public int listarEvaluadosCuentaGerenciaCTE(OCAPUsuariosOT datos, JCYLUsuario jcylUsuario)
     throws Exception
   {
     int total = 0;
     try {
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
       total = usuariosOAD.listarEvaluadosCuentaGerenciaCTE(datos, jcylUsuario);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 }

