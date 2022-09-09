package es.jcyl.fqs.ocap.actionforms.reuniones;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import java.util.ArrayList;

public class OCAPReunionesForm extends OCAPComunForm {

	private static final long serialVersionUID = 4978316769832467908L;
	private long cCte_id;
	private String fReunion;
	private String dOrdendia;
	private String dDecisiones;
	private long nInformestotales;
	private long cEvaluadorId;
	private long nEvaluacionesevaluador;
	private long nInformesrecibidos;
	private long nInformespositivos;
	private long nInformesnegativos;
	private String nHora;
	private ArrayList listadoEval;
	private String resumenEval;

	public void setCCte_id(long cCte_id) {
		this.cCte_id = cCte_id;
	}

	public long getCCte_id() {
		return this.cCte_id;
	}

	public void setFReunion(String fReunion) {
		this.fReunion = fReunion;
	}

	public String getFReunion() {
		return this.fReunion;
	}

	public void setDOrdendia(String dOrdendia) {
		this.dOrdendia = dOrdendia;
	}

	public String getDOrdendia() {
		return this.dOrdendia;
	}

	public void setDDecisiones(String dDecisiones) {
		this.dDecisiones = dDecisiones;
	}

	public String getDDecisiones() {
		return this.dDecisiones;
	}

	public void setNInformestotales(long nInformestotales) {
		this.nInformestotales = nInformestotales;
	}

	public long getNInformestotales() {
		return this.nInformestotales;
	}

	public void setCEvaluadorId(long cEvaluadorId) {
		this.cEvaluadorId = cEvaluadorId;
	}

	public long getCEvaluadorId() {
		return this.cEvaluadorId;
	}

	public void setNEvaluacionesevaluador(long nEvaluacionesevaluador) {
		this.nEvaluacionesevaluador = nEvaluacionesevaluador;
	}

	public long getNEvaluacionesevaluador() {
		return this.nEvaluacionesevaluador;
	}

	public void setNInformesrecibidos(long nInformesrecibidos) {
		this.nInformesrecibidos = nInformesrecibidos;
	}

	public long getNInformesrecibidos() {
		return this.nInformesrecibidos;
	}

	public void setNInformespositivos(long nInformespositivos) {
		this.nInformespositivos = nInformespositivos;
	}

	public long getNInformespositivos() {
		return this.nInformespositivos;
	}

	public void setNInformesnegativos(long nInformesnegativos) {
		this.nInformesnegativos = nInformesnegativos;
	}

	public long getNInformesnegativos() {
		return this.nInformesnegativos;
	}

	public void setNHora(String nHora) {
		this.nHora = nHora;
	}

	public String getNHora() {
		return this.nHora;
	}

	public void setListadoEval(ArrayList listadoEval) {
		this.listadoEval = listadoEval;
	}

	public ArrayList getListadoEval() {
		return this.listadoEval;
	}

	public void setResumenEval(String resumenEval) {
		this.resumenEval = resumenEval;
	}

	public String getResumenEval() {
		return this.resumenEval;
	}
}
