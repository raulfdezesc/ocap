package es.jcyl.fqs.ocap.actionforms.meritos;

import org.apache.struts.action.ActionForm;

public class OCAPNoAportaMeritosForm extends ActionForm {

	private static final long serialVersionUID = 4512558302745952743L;
	
	private String cConvocatoriaId;
	private String numeroResultados;
	
	public String getCConvocatoriaId() {
		return cConvocatoriaId;
	}
	public void setCConvocatoriaId(String cConvocatoriaId) {
		this.cConvocatoriaId = cConvocatoriaId;
	}
	public String getNumeroResultados() {
		return numeroResultados;
	}
	public void setNumeroResultados(String numeroResultados) {
		this.numeroResultados = numeroResultados;
	}
	
}
