 package es.jcyl.fqs.ocap.actions.renuncia;
 
 import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
import es.jcyl.fqs.ocap.ln.expedienteCarrera.OCAPExpedienteCarreraLN;
import es.jcyl.fqs.ocap.ln.motExclusion.OCAPMotExclusionLN;
import es.jcyl.fqs.ocap.ln.renuncia.OCAPRenunciaLN;
import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;
import es.jcyl.fqs.ocap.ot.expedientesexclusion.OCAPExpedientesExclusionOT;
import es.jcyl.fqs.ocap.ot.motExclusion.OCAPMotExclusionOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 
 public class OCAPRenunciaAction extends OCAPGenericAction
 {

   public ActionForward irRenunciar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     HttpSession session = request.getSession();
     ActionErrors errors = new ActionErrors();
     OCAPExpedientecarreraOT expCarreraOT = null;
     String cConvocatoriaId = null;
     try
     {
       OCAPConfigApp.logger.debug(getClass().getName() + " irRenunciar: Inicio");
 
       cConvocatoriaId = obtenerConvocatoria(request, null);
 
       if ((cConvocatoriaId != null) && (!"".equals(cConvocatoriaId)))
       {
         JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
         String usuId = jcylUsuario.getUser().getC_usr_id();
         OCAPConfigApp.logger.debug(getClass().getName() + " irRenunciar: UsuarioId: " + usuId);
 
         OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);
         OCAPUsuariosOT usuariosOT = usuariosLN.buscarOCAPUsuariosPorNIF(usuId);
 
         if ((usuariosOT == null) || (usuariosOT.getCUsrId() == null)) {
           return mapping.findForward("irNoPermiteRenunciaSolicitud");
         }
 
         OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
         expCarreraOT = expedienteCarreraLN.buscarExpedienteCarreraPorUsuarioConvocatoria(usuariosOT.getCUsrId(), Long.valueOf(cConvocatoriaId));
 
         if ((expCarreraOT == null) || (expCarreraOT.getCExpId() == null))
           return mapping.findForward("irNoExisteSolicitud");
         if (expCarreraOT.getCEstadoId() == 15)
         {
           return mapping.findForward("irRenunciaSolicitud");
         }
         
         //OCAP-1886

         OCAPConvocatoriasLN convocatoriaLN = new OCAPConvocatoriasLN(jcylUsuario);
         OCAPConvocatoriasOT convoOT = convocatoriaLN.buscarOCAPConvocatorias(expCarreraOT.getCConvocatoriaId());
         
         //Solo puede renunciar si la fecha actual está comprendida entre F_INICIO_MC - F_FIN_MC ó entre F_INICIO_CA – F_FIN_CA --> referidas a fechas de la convocatoria
         //Tambien se puede renunciar si la fecha actual está comprendida entre F_INICIO_MC - F_FIN_MC ó entre F_INICIO_CA – F_FIN_CA --> referidas a fechas del expediente
         if(!Utilidades.fechaActualEntreMeritosConvocatoria(convoOT) && !Utilidades.fechaActualEntreMeritosExpediente(expCarreraOT,convoOT)) {
        	 return mapping.findForward("irNoPermiteRenunciaSolicitud");
         }

       }
       else {
         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
       }
 
       OCAPConfigApp.logger.debug(getClass().getName() + " irRenunciar: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward("irRenunciar");
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 


public ActionForward renunciar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException
   {
     String sig = "error";
     HttpSession session = request.getSession();
     ActionErrors errors = new ActionErrors();
     String cConvocatoriaId = null;
     try
     {
       OCAPConfigApp.logger.debug(getClass().getName() + " renunciar: Inicio");
       validarAccion(mapping, form, request, response);
 
       cConvocatoriaId = obtenerConvocatoria(request, null);
 
       if ((cConvocatoriaId != null) && (!"".equals(cConvocatoriaId)))
       {
         JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
         String usuId = jcylUsuario.getUser().getC_usr_id();
         OCAPConfigApp.logger.debug(getClass().getName() + " renunciar: UsuarioId: " + usuId);
 
         OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);
         OCAPUsuariosOT usuariosOT = usuariosLN.buscarOCAPUsuariosPorNIF(usuId);
 
         OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
         OCAPExpedientecarreraOT expCarreraOT = expedienteCarreraLN.buscarExpedienteCarreraPorUsuarioConvocatoria(usuariosOT.getCUsrId(), Long.valueOf(cConvocatoriaId));
 
         if ((expCarreraOT == null) || (expCarreraOT.getCExpId() == null)) {
           return mapping.findForward("irNoExisteSolicitud");
         }
 
         if ((expCarreraOT.getFAceptacSolic() == null) || (expCarreraOT.getFAceptacSolic().getTime() == 0L)) {
           return mapping.findForward("irSolicitudNoAceptada");
         }
 
         if (expCarreraOT.getCEstadoId() == 15)
         {
           return mapping.findForward("irRenunciaSolicitud");
         }
 
         OCAPConvocatoriasLN convocatoriaLN = new OCAPConvocatoriasLN(jcylUsuario);
         OCAPConvocatoriasOT convoOT = convocatoriaLN.buscarOCAPConvocatorias(expCarreraOT.getCConvocatoriaId());
         if (convoOT.getFInicioMC() == null) {
           return mapping.findForward("irFaseMCnoIniciada");
         }

         expCarreraOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());
         
 
         /*
          * 
			OCAP - 371 
          * */
//         if (new Date().before(DateUtil.convertStringToDate(convoOT.getFInicioMC()))) {
//           return mapping.findForward("irFaseMCnoIniciada");
//         }
 
         OCAPMotExclusionLN motExclusionLN = new OCAPMotExclusionLN(jcylUsuario);
         OCAPMotExclusionOT motivosOT = motExclusionLN.buscarMotivo("Renuncia");
 
         OCAPExpedientesExclusionOT expedientesExclusionOT = new OCAPExpedientesExclusionOT();
         expedientesExclusionOT.setCExpId(expCarreraOT.getCExpId().longValue());
         expedientesExclusionOT.setCExclusionId(motivosOT.getCExclusionId());
         expedientesExclusionOT.setCUsualta(((JCYLUsuario)request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
 
         OCAPRenunciaLN renunciaLN = new OCAPRenunciaLN(jcylUsuario);
         int result = renunciaLN.renunciar(expedientesExclusionOT, jcylUsuario);
         if (result == 1) {
           request.setAttribute("mensaje", "La solicitud ha sido renunciada con éxito en el sistema ");
           request.setAttribute("rutaVuelta", "PaginaInicio.do");
           sig = "mensajeExitoSinFormulario";
         } else {
           request.setAttribute("mensaje", "Se ha producido un error al renunciar la solicitud");
           sig = "error";
         }
       }
       else {
         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
       }
 
       OCAPConfigApp.logger.debug(getClass().getName() + " renunciar: Fin");
     }
     catch (Exception ex) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
     }
 
     if ((errors == null) || (errors.isEmpty()))
     {
       return mapping.findForward(sig);
     }
     saveErrors(request, errors);
 
     return mapping.findForward("fallo");
   }
 }

