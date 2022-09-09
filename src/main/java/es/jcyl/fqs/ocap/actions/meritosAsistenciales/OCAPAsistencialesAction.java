package es.jcyl.fqs.ocap.actions.meritosAsistenciales;

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
import es.jcyl.fqs.ocap.ln.estadosCuestionario.OCAPEstadosCuestionarioLN;
import es.jcyl.fqs.ocap.ln.expedienteCarrera.OCAPExpedienteCarreraLN;
import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;

public class OCAPAsistencialesAction extends OCAPGenericAction {
	
	 private JCYLUsuario jcylUsuario;
	   public ActionForward irDesbloqueoAsistenciales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	     throws IOException, ServletException
	   {
	     HttpSession session = request.getSession();
	     ActionErrors errors = new ActionErrors();
	     String cConvocatoriaId = null;
	     try
	     {
	       OCAPConfigApp.logger.debug(getClass().getName() + " irDesbloqueoAsistenciales: Inicio");
	 
	       cConvocatoriaId = obtenerConvocatoria(request, null);
	 
	       if ((cConvocatoriaId != null) && (!"".equals(cConvocatoriaId)))
	       {
	         JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
	         String usuId = jcylUsuario.getUser().getC_usr_id();
	         OCAPConfigApp.logger.debug(getClass().getName() + " irDesbloqueoAsistenciales: UsuarioId: " + usuId);
	 
	         OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);
	         OCAPUsuariosOT usuariosOT = usuariosLN.buscarOCAPUsuariosPorNIF(usuId);
	 
	         if ((usuariosOT == null) || (usuariosOT.getCUsrId() == null)) {
	           return mapping.findForward("irErrorAsistenciales");
	         }else {
	        	 return mapping.findForward("irOCAPDesbloqueoAsistenciales");
	         }
	       }
	       else {
	         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
	       }
	 
	       OCAPConfigApp.logger.debug(getClass().getName() + " irDesbloquear: Fin");
	     }
	     catch (Exception e) {
	       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
	       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
	     }
	 
	     if ((errors == null) || (errors.isEmpty()))
	     {
	       return mapping.findForward("irDesbloqueoAsistenciales");
	     }
	     saveErrors(request, errors);
	 
	     return mapping.findForward("fallo");
	   }
	   
	   
	   
	   public ActionForward desbloquear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			     throws IOException, ServletException
			   {
			     String sig = "error";
			     HttpSession session = request.getSession();
			     ActionErrors errors = new ActionErrors();
			     String cConvocatoriaId = null;
			     OCAPExpedientecarreraOT expedienteOT = null;
			     OCAPConvocatoriasOT convocatoriaOT = null;
			     OCAPEstadosCuestionarioLN estadosCuestionariosLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
		         OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
		         OCAPConvocatoriasLN convocatoriaLN = new OCAPConvocatoriasLN(jcylUsuario);
			     try
			     {
			       OCAPConfigApp.logger.debug(getClass().getName() + " desbloquear: Inicio");
			       validarAccion(mapping, form, request, response);
			 
			       cConvocatoriaId = obtenerConvocatoria(request, null);
			       
			       if ((cConvocatoriaId != null) && (!"".equals(cConvocatoriaId)))
			       {
			         JCYLUsuario jcylUsuario = (JCYLUsuario)session.getAttribute("JCYLUsuario");
			         String usuId = jcylUsuario.getUser().getC_usr_id();
			         OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);
			         OCAPUsuariosOT usuariosOT = usuariosLN.buscarOCAPUsuariosPorNIF(usuId);
			         OCAPConfigApp.logger.debug(getClass().getName() + " desbloquear: UsuarioId: " + usuId);
			         expedienteOT = expedienteCarreraLN.buscarExpedienteCarreraPorUsuarioConvocatoria(usuariosOT.getCUsrId(), Long.valueOf(cConvocatoriaId));
			         convocatoriaOT = convocatoriaLN.buscarOCAPConvocatorias(new Long(cConvocatoriaId));
			         expedienteOT.setDniReal(usuId);
			         if(null!=convocatoriaOT.getFInicioCA() && null!= convocatoriaOT.getFFinCA()) 
			         {
			        	 //OCAP-1194
			        	 Calendar c = Calendar.getInstance();
			        	 c.setTime(DateUtil.convertStringToDate(convocatoriaOT.getFFinCA())); 
			        	 c.add(Calendar.DATE, 1);
			        	 expedienteOT.setfFin_ca_convocatoria(c.getTime());
			        	 
			        	if(Utilidades.fechaActualEntreMeritosAsistenciales(expedienteOT, convocatoriaOT)) {

			        		if(fechaConvocatoriaPasada(expedienteOT, convocatoriaOT)) {
			        			//Mantenemos la fecha fin_ca del expediente
			        			expedienteOT.setfFin_ca_convocatoria(expedienteOT.getFFinCa());
			        		}
			        		//desbloqueamos
			        		 int result=expedienteCarreraLN.modificacionFechaMA(expedienteOT);
					         int result2=estadosCuestionariosLN.actualizarDesbloqueoF3(expedienteOT.getCExpId());
					         if (result == 1 || result2 >0) {
					           request.setAttribute("rutaVuelta", "PaginaInicio.do");
					           sig = "irOkAsistenciales";
					         } else {
					           sig = "irErrorAsistenciales";
					         }
			        	}else {
			        		 sig = "irOCAPAvisoAsistenciales"; 
			        	}
			         }else {
			        	 sig = "irOCAPAvisoAsistenciales"; 
			         }
			         
			       }
			       else {
			         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.general"));
			       }
			 
			       OCAPConfigApp.logger.debug(getClass().getName() + " desbloquear: Fin");
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



		private boolean fechaConvocatoriaPasada(OCAPExpedientecarreraOT expedienteOT, OCAPConvocatoriasOT convocatoriaOT) {
			try {
				if (Utilidades.notNullAndNotEmpty(convocatoriaOT.getFFinCA())) {
					Date fechaFinCA = DateUtil.convertStringToDate(convocatoriaOT.getFFinCA());
					// Miramos que fecha actual sea posterior a F_FIN_CA
					if (Utilidades.eliminarHoras(new Date()).compareTo(fechaFinCA) > 0) {
						return true;
					}
				}
				return false;
			} catch (Exception e) {
				OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
				return false;
			}
		}

}
