package es.jcyl.fqs.ocap.actions.meritos;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.jcyl.fqs.ocap.actionforms.meritos.OCAPMeritosForm;
import es.jcyl.fqs.ocap.actionforms.meritos.OCAPNoAportaMeritosForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
import es.jcyl.fqs.ocap.ln.expedientes.OCAPExpedientesLN;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.TrataError;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;

public class OCAPNoAportaMeritosAction extends OCAPGenericAction
{
	 
	 
	public ActionForward irMeritosMc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irMeritosMc: Inicio");

			HttpSession session = request.getSession();
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPConvocatoriasLN convoLN = new OCAPConvocatoriasLN(jcylUsuario);
			ArrayList listaConvocatorias = convoLN.listarConvocatoriasFinMc();
			if ((listaConvocatorias == null) || (listaConvocatorias.size() == 0)) {
				return mapping.findForward("sinConvocatoriasMc");
			}
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listaConvocatorias);
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " irMeritosMc: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta",
					"OCAPUsuarios.do?accion=irInsertar&pestana="
							+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
									: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		saveErrors(request, errors);

		return mapping.findForward("MC");
	}
	
	public ActionForward irMeritosMa(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irMeritosMc: Inicio");

			HttpSession session = request.getSession();
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPConvocatoriasLN convoLN = new OCAPConvocatoriasLN(jcylUsuario);
			ArrayList listaConvocatorias = convoLN.listarConvocatoriasFinMa();
			if ((listaConvocatorias == null) || (listaConvocatorias.size() == 0)) {
				return mapping.findForward("sinConvocatoriasMa");
			}
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listaConvocatorias);
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " irMeritosMc: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta",
					"OCAPUsuarios.do?accion=irInsertar&pestana="
							+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
									: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		saveErrors(request, errors);

		return mapping.findForward("MA");
	}
	
	public ActionForward ejecutarMc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionErrors errors = new ActionErrors();
		String forward = "";
		try {
			
			OCAPConfigApp.logger.info(getClass().getName() + " ejecutarMc: Inicio");

			HttpSession session = request.getSession();
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			Integer convocatoriaId;
			
			if (Utilidades.notNullAndNotEmpty(((OCAPNoAportaMeritosForm) form).getCConvocatoriaId()))
				convocatoriaId = Integer.parseInt(((OCAPNoAportaMeritosForm) form).getCConvocatoriaId());
			else{
				convocatoriaId = 0;
			}

			OCAPExpedientesLN expedientesLN = new OCAPExpedientesLN(jcylUsuario);
			Integer resultado = expedientesLN.ejecutarProcMc(convocatoriaId, jcylUsuario.getUser().getC_usr_id());
			if (resultado >= 0) {
				((OCAPNoAportaMeritosForm) form).setNumeroResultados(String.valueOf(resultado));
				forward = "OK";
			}else {
				forward = "ERROR";
			}
		} catch (Exception e) {
			OCAPConfigApp.logger.error(getClass().getName() + " ejecutarMc: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		saveErrors(request, errors);

		return mapping.findForward(forward);
	}
	
	public ActionForward ejecutarMa(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionErrors errors = new ActionErrors();
		String forward = "";
		try {
			
			OCAPConfigApp.logger.info(getClass().getName() + " ejecutarMa: Inicio");

			HttpSession session = request.getSession();
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			Integer convocatoriaId;
			
			if (Utilidades.notNullAndNotEmpty(((OCAPNoAportaMeritosForm) form).getCConvocatoriaId()))
				convocatoriaId = Integer.parseInt(((OCAPNoAportaMeritosForm) form).getCConvocatoriaId());
			else{
				convocatoriaId = 0;
			}

			OCAPExpedientesLN expedientesLN = new OCAPExpedientesLN(jcylUsuario);
			Integer resultado = expedientesLN.ejecutarProcMa(convocatoriaId, jcylUsuario.getUser().getC_usr_id());
			if (resultado >= 0) {
				((OCAPNoAportaMeritosForm) form).setNumeroResultados(String.valueOf(resultado));
				forward = "OK";
			}else {
				forward = "ERROR";
			}
		} catch (Exception e) {
			OCAPConfigApp.logger.error(getClass().getName() + " ejecutarMc: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		saveErrors(request, errors);

		return mapping.findForward(forward);
	}
}
