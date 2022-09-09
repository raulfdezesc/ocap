package es.jcyl.fqs.ocap.actionforms.acceso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import es.jcyl.fqs.ocap.util.Utilidades;

public class OCAPAccesoForm extends ValidatorForm {

	private static final long serialVersionUID = 1L;

	private String aDni;
	private String aPassword;
	private String aPasswordRepet;
	private String aEmailRecuerdo;

	public String getADni() {
		return aDni;
	}

	public void setADni(String aDni) {
		this.aDni = aDni;
	}

	public String getAPassword() {
		return aPassword;
	}

	public void setAPassword(String aPassword) {
		this.aPassword = aPassword;
	}

	public String getAPasswordRepet() {
		return aPasswordRepet;
	}

	public void setAPasswordRepet(String aPasswordRepet) {
		this.aPasswordRepet = aPasswordRepet;
	}

	public String getAEmailRecuerdo() {
		return aEmailRecuerdo;
	}

	public void setAEmailRecuerdo(String aEmailRecuerdo) {
		this.aEmailRecuerdo = aEmailRecuerdo;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		String nombreMetodoAction = request.getParameter(mapping.getParameter());

		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);

		ActionErrors errores = super.validate(mapping, request);
		if (errores == null) {
			errores = new ActionErrors();
		}

		if (nombreMetodoAction.equals("guardarDatosAcceso")) 
		{

			if (GenericValidator.isBlankOrNull(this.aDni)) {
				errores.add("ADni", new ActionError("error.obligatorio.generico"));
			}
			if (GenericValidator.isBlankOrNull(this.aPassword)) {
				errores.add("APassword", new ActionError("error.obligatorio.generico"));
			}
			if (GenericValidator.isBlankOrNull(this.aPasswordRepet)) {
				errores.add("APasswordRepet", new ActionError("error.obligatorio.generico"));
			}
			if (GenericValidator.isBlankOrNull(this.aEmailRecuerdo)) {
				errores.add("AEmailRecuerdo", new ActionError("error.obligatorio.generico"));
			}

			

			if (!GenericValidator.isBlankOrNull(this.aPassword)
					&& !GenericValidator.isBlankOrNull(this.aPasswordRepet)) {
				if (this.aPassword.length() < 8 || this.aPassword.length() > 10) {
					errores.add("APassword", new ActionError("error.password.corto"));
				}
				if (!this.aPassword.equals(this.aPasswordRepet)) {
					errores.add("APasswordRepet", new ActionError("error.password.no.coinciden"));
				}
			}

			if (!GenericValidator.isBlankOrNull(this.aEmailRecuerdo)) {
				Matcher matcher = pattern.matcher(aEmailRecuerdo);
				if (!matcher.matches()) {
					errores.add("AEmailRecuerdo", new ActionError("errors.email"));
				}
			}
		}

		return errores;
	}
}
