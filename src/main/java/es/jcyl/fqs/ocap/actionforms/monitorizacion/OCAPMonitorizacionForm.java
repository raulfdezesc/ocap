package es.jcyl.fqs.ocap.actionforms.monitorizacion;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

public class OCAPMonitorizacionForm extends OCAPComunForm {

	private static final long serialVersionUID = 7169691191959631301L;
	protected String propietarioObjeto;
	protected String nombreObjeto;
	protected String tipoObjeto;
	protected String monitorizacion;

	public void setPropietarioObjeto(String propietarioObjeto) {
		this.propietarioObjeto = propietarioObjeto;
	}

	public String getPropietarioObjeto() {
		return this.propietarioObjeto;
	}

	public void setNombreObjeto(String nombreObjeto) {
		this.nombreObjeto = nombreObjeto;
	}

	public String getNombreObjeto() {
		return this.nombreObjeto;
	}

	public void setTipoObjeto(String tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

	public String getTipoObjeto() {
		return this.tipoObjeto;
	}

	public void setMonitorizacion(String monitorizacion) {
		this.monitorizacion = monitorizacion;
	}

	public String getMonitorizacion() {
		return this.monitorizacion;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errores = super.validate(mapping, request);
		if (errores == null) {
			errores = new ActionErrors();
		}
		String nombreMetodoAction = request.getParameter(mapping.getParameter());

		if ((nombreMetodoAction != null) && (nombreMetodoAction.equals("monitorizarBD"))) {
			if (GenericValidator.isBlankOrNull(this.propietarioObjeto)) {
				errores.add("propietarioObjeto", new ActionError("error.obligatorio.generico"));
			}

		}

		return errores;
	}
}
