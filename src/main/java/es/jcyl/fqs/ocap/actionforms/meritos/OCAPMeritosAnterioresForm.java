package es.jcyl.fqs.ocap.actionforms.meritos;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class OCAPMeritosAnterioresForm extends ActionForm {

	private static final long serialVersionUID = 4512558302745578743L;
	
	private String idExpediente;
	private String idCateg;
	private String descCateg;
	private String tipoMerito;
	private String opcional;
	private ArrayList listaMeritos;
	
	
	public String getIdExpediente() {
		return idExpediente;
	}
	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}
	public String getIdCateg() {
		return idCateg;
	}
	public void setIdCateg(String idCateg) {
		this.idCateg = idCateg;
	}
	public String getDescCateg() {
		return descCateg;
	}
	public void setDescCateg(String descCateg) {
		this.descCateg = descCateg;
	}
	public String getTipoMerito() {
		return tipoMerito;
	}
	public void setTipoMerito(String tipoMerito) {
		this.tipoMerito = tipoMerito;
	}
	public String getOpcional() {
		return opcional;
	}
	public void setOpcional(String opcional) {
		this.opcional = opcional;
	}
	public ArrayList getListaMeritos() {
		return listaMeritos;
	}
	public void setListaMeritos(ArrayList listaMeritos) {
		this.listaMeritos = listaMeritos;
	}
	

	
}
