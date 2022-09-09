package es.jcyl.fqs.ocap.actionforms.sugerencias;

import org.apache.struts.action.ActionForm;

public class OCAPSugerenciasForm extends ActionForm {

	private static final long serialVersionUID = 5260058016183274993L;
	private String dCorreoElectronico;
	private String dTelefono;
	private String dSugerencia;

	public void setDCorreoElectronico(String dCorreoElectronico) {
		this.dCorreoElectronico = dCorreoElectronico;
	}

	public String getDCorreoElectronico() {
		return this.dCorreoElectronico;
	}

	public void setDTelefono(String dTelefono) {
		this.dTelefono = dTelefono;
	}

	public String getDTelefono() {
		return this.dTelefono;
	}

	public void setDSugerencia(String dSugerencia) {
		this.dSugerencia = dSugerencia;
	}

	public String getDSugerencia() {
		return this.dSugerencia;
	}
}
