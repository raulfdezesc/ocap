package es.jcyl.fqs.ocap.actionforms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public final class OCAPLoginForm extends ValidatorForm {

	private static final long serialVersionUID = 720179404774379638L;
	private String login;
	private String psswd;
	private String perfil;

	public OCAPLoginForm() {
		this.login = null;
		this.psswd = null;
		this.perfil = null;
	}

	public String getLogin() {
		return this.login;
	}

	public String getPsswd() {
		return this.psswd;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPsswd(String psswd) {
		this.psswd = psswd;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errores = super.validate(mapping, request);
		if (errores == null) {
			errores = new ActionErrors();
		}

		return errores;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getPerfil() {
		return this.perfil;
	}

	 
}
