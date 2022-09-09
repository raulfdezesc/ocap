package es.jcyl.fqs.ocap.actionforms.tiposFirmante;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import java.sql.Date;

public class OCAPTiposFirmanteForm extends OCAPComunForm {

	private static final long serialVersionUID = 5036194623163447138L;
	private long cTipo_id;
	private String dNombre;
	private String dObservaciones;
	private Date fCreacion;
	private Date fModificacion;
	private String bBorrado;
	private String aCreacion;
	private String mCreacion;
	private String dCreacion;
	private String aModificacion;
	private String mModificacion;
	private String dModificacion;
	private String nAniosejercicio;

	public long getCTipo_id() {
		return this.cTipo_id;
	}

	public void setCTipo_id(long cTipo_id) {
		this.cTipo_id = cTipo_id;
	}

	public String getDNombre() {
		return this.dNombre;
	}

	public void setDNombre(String dNombre) {
		this.dNombre = dNombre;
	}

	public String getDObservaciones() {
		return this.dObservaciones;
	}

	public void setDObservaciones(String dObservaciones) {
		this.dObservaciones = dObservaciones;
	}

	public Date getFCreacion() {
		return this.fCreacion;
	}

	public void setFCreacion(Date fCreacion) {
		this.fCreacion = fCreacion;
	}

	public Date getFModificacion() {
		return this.fModificacion;
	}

	public void setFModificacion(Date fModificacion) {
		this.fModificacion = fModificacion;
	}

	public String getBBorrado() {
		return this.bBorrado;
	}

	public void setBBorrado(String bBorrado) {
		this.bBorrado = bBorrado;
	}

	public void setACreacion(String aCreacion) {
		this.aCreacion = aCreacion;
	}

	public String getACreacion() {
		return this.aCreacion;
	}

	public void setMCreacion(String mCreacion) {
		this.mCreacion = mCreacion;
	}

	public String getMCreacion() {
		return this.mCreacion;
	}

	public void setDCreacion(String dCreacion) {
		this.dCreacion = dCreacion;
	}

	public String getDCreacion() {
		return this.dCreacion;
	}

	public void setAModificacion(String aModificacion) {
		this.aModificacion = aModificacion;
	}

	public String getAModificacion() {
		return this.aModificacion;
	}

	public void setMModificacion(String mModificacion) {
		this.mModificacion = mModificacion;
	}

	public String getMModificacion() {
		return this.mModificacion;
	}

	public void setDModificacion(String dModificacion) {
		this.dModificacion = dModificacion;
	}

	public String getDModificacion() {
		return this.dModificacion;
	}

	public void setNAniosejercicio(String nAniosejercicio) {
		this.nAniosejercicio = nAniosejercicio;
	}

	public String getNAniosejercicio() {
		return this.nAniosejercicio;
	}
}
