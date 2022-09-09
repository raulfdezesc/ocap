package es.jcyl.fqs.ocap.actions.meritos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.jcyl.fqs.ocap.actionforms.meritos.OCAPMeritosAnterioresForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.meritosCurriculares.OCAPMeritoscurricularesLN;
import es.jcyl.fqs.ocap.ot.meritosCurriculares.OCAPMeritoscurricularesOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.TrataError;
import es.jcyl.framework.JCYLUsuario;

public class OCAPMeritosAnterioresAction extends OCAPGenericAction
{
	 
	 
	public ActionForward verMeritosAnteriores(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " verMeritosAnteriores: Inicio");
			OCAPMeritosAnterioresForm formulario = (OCAPMeritosAnterioresForm) form;
			HttpSession session = request.getSession();
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPMeritoscurricularesOT mcOTaux = null;
			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPUsuariosOT usuariosOT = new OCAPUsuariosOT();
	         
			usuariosOT.setCExpId(Long.valueOf(formulario.getIdExpediente()));
			usuariosOT.setCodigoId(formulario.getIdCateg());
			usuariosOT.setDDescripcion(formulario.getDescCateg());
			
			if (formulario.getOpcional().equals(Constantes.NO)) {
		         mcOTaux = mcLN.buscarOCAPMeritosValidadosPorUsuarioOT(usuariosOT, Long.valueOf(formulario.getTipoMerito()), Constantes.NO, jcylUsuario);
			} else if (formulario.getOpcional().equals(Constantes.SI)) {
				mcOTaux = mcLN.buscarOCAPMeritosValidadosPorUsuarioOT(usuariosOT, Long.valueOf(formulario.getTipoMerito()), Constantes.SI, jcylUsuario);
			}
			
	              
		    formulario.setListaMeritos(mcOTaux.getListaMeritosUsuario());
			
			

		} catch (Exception e) {
			OCAPConfigApp.logger.error(getClass().getName() + " verMeritosAnteriores: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
			
		}

		saveErrors(request, errors);

		return mapping.findForward("verMeritosAnterioresValidados");
	}
	
}
