 package es.jcyl.fqs.ocap.ln.expedientes;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.expedientes.OCAPReportExpedientesOT;
 import es.jcyl.fqs.ocap.util.Cadenas;
 import es.jcyl.fqs.ocap.util.Constantes;
 import es.jcyl.fqs.ocap.util.DateUtil;
 import es.jcyl.fqs.ocap.util.reports.conceptos.ConceptoReport;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLUsuario;

import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
 
 public class OCAPReportExpedientesMerCurricuValoresLN
   implements Constantes
 {
   public Logger logger;
   String campoTitulo;
   String campoEtiqueta1;
   String campoValor1;
   String campoEtiqueta2;
   String campoValor2;
   String campoCreditosObtenidos;
   String campoTipo;
   String campoMod;
   String campoTipoMod;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.campoTitulo = "titulo";
     this.campoEtiqueta1 = "etiqueta1";
     this.campoValor1 = "valor1";
     this.campoEtiqueta2 = "etiqueta2";
     this.campoValor2 = "valor2";
     this.campoCreditosObtenidos = "creditosObtenidos";
     this.campoTipo = "tipoValor";
     this.campoMod ="valorMod";
     this.campoTipoMod = "valorTipoMod";
   }
   public OCAPReportExpedientesMerCurricuValoresLN() { $init$();}
 
 
   public void rellenarDatosExpMCBeca(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {

       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCBibliograficas(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       StringTokenizer token = new StringTokenizer(datosMC.getAOrganizador(), "#");
       StringTokenizer token2 = null;
       StringTokenizer token3 = null;
       while (token.hasMoreTokens()) {
         token2 = new StringTokenizer(token.nextToken(), "$");
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoTitulo, token2.nextToken());
         conceptoValores.putElement(this.campoTipo, "Titulo");
 
         token3 = new StringTokenizer(token2.nextToken(), "&");
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoEtiqueta1, "Fecha");
         conceptoValores.putElement(this.campoValor1, token3.nextToken());
         conceptoValores.putElement(this.campoEtiqueta2, "Horas");
         conceptoValores.putElement(this.campoValor2, token3.nextToken());
         conceptoValores.putElement(this.campoTipo, "Parametros2");
         if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
      	   conceptoValores.addRow();
      	 conceptoValores.putElement(this.campoTipoMod, "Motivo modif.: ");
         conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
       }
     } catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCBibliograficasAct(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Horas");
       conceptoValores.putElement(this.campoValor1, "" + datosMC.getNHoras());
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCBResponsableCO(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
     if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCCentinela(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, "Centinela");
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCCongreso(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       if (datosMC.getBAcreditadoMeritoCurricular().equals(Constantes.NO)) {
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoEtiqueta1, "Acreditación");
         conceptoValores.putElement(this.campoValor1, Constantes.NO_TEXTO_min);
         conceptoValores.putElement(this.campoTipo, "Parametro");
       } else {
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoEtiqueta1, "Acreditación");
         conceptoValores.putElement(this.campoValor1, Constantes.SI_TEXTO_min);
         conceptoValores.putElement(this.campoTipo, "Parametro");
       }
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
 
       if (!datosMC.getBAcreditadoMeritoCurricular().equals(Constantes.NO)) {
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoEtiqueta1, "Nº de Créditos");
         conceptoValores.putElement(this.campoValor1, "" + datosMC.getNCreditosCurso());
         conceptoValores.putElement(this.campoTipo, "Parametro");
       }
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Organizado por");
       conceptoValores.putElement(this.campoValor1, datosMC.getAOrganizador());
       conceptoValores.putElement(this.campoTipo, "Parametro");
       
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e)
     {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCCoordinaEAP(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCCursoDoc(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Horas");
       conceptoValores.putElement(this.campoValor1, "" + datosMC.getNHoras());
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Organizado por");
       conceptoValores.putElement(this.campoValor1, datosMC.getAOrganizador());
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCDocenciaPost(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDMerActividadMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       if ("Docencia Posgrado Acreditada".equals(datosMC.getDMeritoCurricular())) {
         StringTokenizer token = new StringTokenizer(datosMC.getAOrganizador(), "#");
         StringTokenizer token2 = null;
         while (token.hasMoreTokens()) {
           token2 = new StringTokenizer(token.nextToken(), "$");
           conceptoValores.addRow();
           conceptoValores.putElement(this.campoEtiqueta1, "Año Inicio");
           conceptoValores.putElement(this.campoValor1, token2.nextToken());
           conceptoValores.putElement(this.campoEtiqueta2, "Unidad / Servicio / Centro");
           conceptoValores.putElement(this.campoValor2, token2.nextToken());
           conceptoValores.putElement(this.campoTipo, "Parametros2");
         }
       } else {
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoEtiqueta1, "Año Inicio-Fin");
         conceptoValores.putElement(this.campoValor1, Cadenas.reemplazar("#", " / ", Cadenas.reemplazar("$", "-", datosMC.getAOrganizador())));
         conceptoValores.putElement(this.campoTipo, "Parametro");
       }
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
 
     }
     catch (Exception e)
     {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCDocenciaPre(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDMerActividadMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Año Inicio");
       conceptoValores.putElement(this.campoValor1, "" + datosMC.getFAnnio());
       conceptoValores.putElement(this.campoEtiqueta2, "Año Finalización");
       conceptoValores.putElement(this.campoValor2, "" + (datosMC.getFAnnio() + 1));
       conceptoValores.putElement(this.campoTipo, "Parametros2");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e)
     {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCDoctorado(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCEspecialidad(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, "Especialidad: " + datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Expedición");
       conceptoValores.putElement(this.campoValor1, datosMC.getFExpedicion() != null ? DateUtil.getDate(datosMC.getFExpedicion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Lugar Expedición");
       conceptoValores.putElement(this.campoValor1, datosMC.getALugarExpedicion());
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
    }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCEstancia(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Lugar Realización");
       conceptoValores.putElement(this.campoValor1, datosMC.getALugarExpedicion());
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCEvaluadorProyectos(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Agencia pública");
       conceptoValores.putElement(this.campoValor1, datosMC.getAOrganizador());
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Evaluación");
       conceptoValores.putElement(this.campoValor1, datosMC.getFExpedicion() != null ? DateUtil.getDate(datosMC.getFExpedicion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e)
     {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCExponeCasosSesCli(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCFormaNuevoPersonal(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, "Año: " + datosMC.getFAnnio());
       conceptoValores.putElement(this.campoTipo, "Titulo");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCFPSuperior(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Fecha de Expedición");
       conceptoValores.putElement(this.campoValor1, datosMC.getFExpedicion() != null ? DateUtil.getDate(datosMC.getFExpedicion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Lugar de Expedición");
       conceptoValores.putElement(this.campoValor1, datosMC.getALugarExpedicion() != null ? datosMC.getALugarExpedicion() : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCJefeServicio(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
    }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCLicenDiplo(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, "Licenciatura/Diplomatura: " + datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Expedición");
       conceptoValores.putElement(this.campoValor1, datosMC.getFExpedicion() != null ? DateUtil.getDate(datosMC.getFExpedicion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Lugar Expedición");
       conceptoValores.putElement(this.campoValor1, datosMC.getALugarExpedicion());
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCMaster(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Tip. Actividad");
       conceptoValores.putElement(this.campoValor1, datosMC.getDMerActividadMC());
       conceptoValores.putElement(this.campoEtiqueta2, "Tip. Modalidad");
       conceptoValores.putElement(this.campoValor2, datosMC.getDMerModalidadMC());
       conceptoValores.putElement(this.campoTipo, "Parametros2");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Organizado por");
       conceptoValores.putElement(this.campoValor1, datosMC.getAOrganizador());
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
      }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCMeritoSiNo(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       if (datosMC.getBParticipacion().equals(Constantes.NO))
         conceptoValores.putElement(this.campoTitulo, Constantes.NO_TEXTO);
       else {
         conceptoValores.putElement(this.campoTitulo, Constantes.SI_TEXTO);
       }
       conceptoValores.putElement(this.campoTipo, "Titulo");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
      }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCMiembroComision(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCMiembroComite(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Realización");
       conceptoValores.putElement(this.campoValor1, datosMC.getFExpedicion() != null ? DateUtil.getDate(datosMC.getFExpedicion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCMiembroConsejo(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCMiembroGrupo(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Cargo");
       conceptoValores.putElement(this.campoValor1, datosMC.getACargo());
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Objetivo");
       conceptoValores.putElement(this.campoValor1, datosMC.getAObjetivo());
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
     }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCMiembroTribunal(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Publicación");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCModeradorMesa(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Evento");
       conceptoValores.putElement(this.campoValor1, datosMC.getFExpedicion() != null ? DateUtil.getDate(datosMC.getFExpedicion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
      }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCOrganizadorDifusor(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCOtraPublicacion(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
 
       conceptoValores.putElement(this.campoTitulo, datosMC.getAOrganizador());
 
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       if (datosMC.getDMeritoCurricular().matches(".*Póster.*"))
       {
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoEtiqueta1, "Título Póster");
         conceptoValores.putElement(this.campoValor1, datosMC.getDExpedienteMC());
         conceptoValores.putElement(this.campoTipo, "Parametro");
       }
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Celebración");
       conceptoValores.putElement(this.campoValor1, datosMC.getFExpedicion() != null ? DateUtil.getDate(datosMC.getFExpedicion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       if ((datosMC.getDMerModalidadMC() != null) && (!datosMC.getDMerModalidadMC().trim().equals(""))) {
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoEtiqueta1, "Ámbito");
         conceptoValores.putElement(this.campoValor1, datosMC.getDMerModalidadMC());
         conceptoValores.putElement(this.campoTipo, "Parametro");
       }
 
       if ((datosMC.getDTipoFirmanteMC() != null) && (!datosMC.getDTipoFirmanteMC().trim().equals(""))) {
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoEtiqueta1, "Tip. Firmante");
         conceptoValores.putElement(this.campoValor1, datosMC.getDTipoFirmanteMC());
         conceptoValores.putElement(this.campoTipo, "Parametro");
       }
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCOtraPublicacionISBN(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "ISBN");
       conceptoValores.putElement(this.campoValor1, "" + datosMC.getNISBN());
 
       conceptoValores.putElement(this.campoEtiqueta2, "Año Publicación");
       conceptoValores.putElement(this.campoValor2, "" + datosMC.getFAnnio());
       conceptoValores.putElement(this.campoTipo, "Parametros2");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Editorial");
       conceptoValores.putElement(this.campoValor1, datosMC.getDEditorial());
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Lugar Publicación");
       conceptoValores.putElement(this.campoValor1, datosMC.getALugarExpedicion());
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Tip. Firmante");
       conceptoValores.putElement(this.campoValor1, datosMC.getDTipoFirmanteMC());
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCOtroFP(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Expedición");
       conceptoValores.putElement(this.campoValor1, datosMC.getFExpedicion() != null ? DateUtil.getDate(datosMC.getFExpedicion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Lugar de Expedición");
       conceptoValores.putElement(this.campoValor1, datosMC.getALugarExpedicion());
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCParticipaProyecto(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Entidad");
       conceptoValores.putElement(this.campoValor1, datosMC.getAOrganizador());
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCParticipaProyectoInvestigacion(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Participa como");
       conceptoValores.putElement(this.campoValor1, datosMC.getDMerActividadMC());
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       if (12 == datosMC.getCMerActividadMC()) {
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoEtiqueta1, "Horas");
         conceptoValores.putElement(this.campoValor1, datosMC.getNHoras() + "");
         conceptoValores.putElement(this.campoTipo, "Parametro");
       }
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Comisión Investigación");
       conceptoValores.putElement(this.campoValor1, datosMC.getAOrganizador());
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Memoria Final");
       conceptoValores.putElement(this.campoValor1, datosMC.getFExpedicion() != null ? DateUtil.getDate(datosMC.getFExpedicion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCPatentesModelos(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Reconocimiento");
       conceptoValores.putElement(this.campoValor1, datosMC.getFExpedicion() != null ? DateUtil.getDate(datosMC.getFExpedicion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCPreparaImparteAct(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCPublicacionIndex(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Publicación");
       conceptoValores.putElement(this.campoValor1, datosMC.getFExpedicion() != null ? DateUtil.getDate(datosMC.getFExpedicion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Tip. Publicación");
       conceptoValores.putElement(this.campoValor1, datosMC.getDMerActividadMC());
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Tip. Firmante");
       conceptoValores.putElement(this.campoValor1, datosMC.getDTipoFirmanteMC());
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Fact. Impacto");
       conceptoValores.putElement(this.campoValor1, datosMC.getDFactorImpactoMC());
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCPublicacionRevistaProf(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Nombre Revista");
       conceptoValores.putElement(this.campoValor1, datosMC.getANombreRevista());
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Fecha Publicación");
       conceptoValores.putElement(this.campoValor1, datosMC.getFExpedicion() != null ? DateUtil.getDate(datosMC.getFExpedicion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCResponsableActAE(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCResponsableActEspecifica(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e)
     {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCResponsableCartServicio(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, "Responsable");
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Gerencia");
       conceptoValores.putElement(this.campoValor1, datosMC.getAOrganizador());
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Asunción");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Prog. Asistencial");
       conceptoValores.putElement(this.campoValor1, datosMC.getAObjetivo());
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Cargo");
       conceptoValores.putElement(this.campoValor1, datosMC.getACargo());
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCResponsableComisionCal(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCResponsableDocencia(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       
       if(datosMC.getFInicio() != null) {
    	   conceptoValores.putElement(this.campoValor1, DateUtil.getDate(datosMC.getFInicio()));
       }else if(datosMC.getFAnnio() != 0) {
    	   conceptoValores.putElement(this.campoValor1, String.valueOf(datosMC.getFAnnio()));
       }else {
    	   conceptoValores.putElement(this.campoValor1, "");
       }
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       //Se está comparando con FInicio cuando deberia ser con FFin, lo mantenemos pero no entendemos el motivo 
       if(datosMC.getFInicio() != null) {
    	   conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       }else if(datosMC.getFAnnio() != 0) {
    	   conceptoValores.putElement(this.campoValor2, String.valueOf(datosMC.getFAnnio()+1));
       }else {
    	   conceptoValores.putElement(this.campoValor2, "");
       }
       conceptoValores.putElement(this.campoTipo, "Parametros2");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
      }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCResponsableMaterialServ(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCResponsableMaterialUnidad(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCRevisaProtocoloGuia(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCRevisaProtocoloProced(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCRevistaCientifica(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Revisión");
       conceptoValores.putElement(this.campoValor1, datosMC.getFExpedicion() != null ? DateUtil.getDate(datosMC.getFExpedicion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCSeminariosDoc(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
 
       conceptoValores.putElement(this.campoEtiqueta1, "Ámbito");
       conceptoValores.putElement(this.campoValor1, datosMC.getDMerActividadMC());
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       if (datosMC.getNHoras() != 0) {
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoEtiqueta1, "Horas");
         conceptoValores.putElement(this.campoValor1, "" + datosMC.getNHoras());
         conceptoValores.putElement(this.campoTipo, "Parametro");
       }
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Celebración");
       conceptoValores.putElement(this.campoValor1, datosMC.getFExpedicion() != null ? DateUtil.getDate(datosMC.getFExpedicion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCSesionClinica(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
 
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Fecha");
 
       conceptoValores.putElement(this.campoValor1, DateUtil.getDate(datosMC.getFInicio()));
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCTaller(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       if (datosMC.getBAcreditadoMeritoCurricular().equals(Constantes.NO))
       {
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoEtiqueta1, "Acreditación");
         conceptoValores.putElement(this.campoValor1, Constantes.NO_TEXTO_min);
         conceptoValores.putElement(this.campoEtiqueta2, "Modalidad");
         conceptoValores.putElement(this.campoValor2, datosMC.getDMerModalidadMC());
         conceptoValores.putElement(this.campoTipo, "Parametros2");
       }
       else
       {
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoEtiqueta1, "Acreditación");
         conceptoValores.putElement(this.campoValor1, Constantes.SI_TEXTO_min);
         conceptoValores.putElement(this.campoTipo, "Parametro");
       }
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "F. Inicio");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoEtiqueta2, "F. Finalización");
       conceptoValores.putElement(this.campoValor2, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametros2");
 
       if (datosMC.getBAcreditadoMeritoCurricular().equals(Constantes.NO))
       {
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoEtiqueta1, "Horas");
         conceptoValores.putElement(this.campoValor1, "" + datosMC.getNHoras());
         conceptoValores.putElement(this.campoEtiqueta2, "Días");
         conceptoValores.putElement(this.campoValor2, "" + datosMC.getNDias());
         conceptoValores.putElement(this.campoTipo, "Parametros2");
       }
       else
       {
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoEtiqueta1, "Nº de Créditos");
         conceptoValores.putElement(this.campoValor1, "" + datosMC.getNCreditosCurso());
         conceptoValores.putElement(this.campoTipo, "Parametro");
       }
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Organizado por");
       conceptoValores.putElement(this.campoValor1, datosMC.getAOrganizador());
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCTesis(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Universidad");
       conceptoValores.putElement(this.campoValor1, datosMC.getAOrganizador());
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       if ("Tesis Doctoral".equals(datosMC.getDMeritoCurricular())) {
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoEtiqueta1, "F. defensa Tesis");
         conceptoValores.putElement(this.campoValor1, DateUtil.getDate(datosMC.getFExpedicion()));
         conceptoValores.putElement(this.campoTipo, "Parametro");
       }
       if (("Dirección de Tesis Doctoral".equals(datosMC.getDMeritoCurricular())) || ("Codirección de Tesis Doctoral".equals(datosMC.getDMeritoCurricular()))) {
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoEtiqueta1, "Año dirección");
         conceptoValores.putElement(this.campoValor1, "" + datosMC.getFAnnio());
         conceptoValores.putElement(this.campoTipo, "Parametro");
       }
       if ("Suficiencia Investigadora".equals(datosMC.getDMeritoCurricular())) {
         conceptoValores.addRow();
         conceptoValores.putElement(this.campoEtiqueta1, "Año defensa");
         conceptoValores.putElement(this.campoValor1, "" + datosMC.getFAnnio());
         conceptoValores.putElement(this.campoTipo, "Parametro");
       }
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 
   public void rellenarDatosExpMCCreaProtocolo(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoTitulo, datosMC.getDExpedienteMC());
       conceptoValores.putElement(this.campoTipo, "Titulo");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Fecha de Aprobación institucional del proyecto");
       conceptoValores.putElement(this.campoValor1, datosMC.getFInicio() != null ? DateUtil.getDate(datosMC.getFInicio()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
 
       conceptoValores.addRow();
       conceptoValores.putElement(this.campoEtiqueta1, "Fecha de Aprobación del fin del proyecto");
       conceptoValores.putElement(this.campoValor1, datosMC.getFFinalizacion() != null ? DateUtil.getDate(datosMC.getFFinalizacion()) : null);
       conceptoValores.putElement(this.campoTipo, "Parametro");
       if(null!=datosMC.getDMotivoModif() && Constantes.OCAP_USUARIOS.equals(jcylUsuario.getUser().getRol())) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement(this.campoTipoMod, "Motivo modif.:");
           conceptoValores.putElement(this.campoMod, datosMC.getDMotivoModif());
       }
     }
     catch (Exception e) {
       throw e;
     }
   }
 }

