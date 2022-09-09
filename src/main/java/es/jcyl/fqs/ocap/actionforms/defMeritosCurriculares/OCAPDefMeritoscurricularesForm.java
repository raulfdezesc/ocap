package es.jcyl.fqs.ocap.actionforms.defMeritosCurriculares;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import java.sql.Date;

public class OCAPDefMeritoscurricularesForm extends OCAPComunForm {

	private static final long serialVersionUID = -4021579514982360369L;
	private long cDefmerito_id;
	private String dNombre;
	private String dDescripcion;
	private Date fCreacion;
	private Date fModificacion;
	private String bBorrado;
	private String aCreacion;
	private String mCreacion;
	private String dCreacion;
	private String aModificacion;
	private String mModificacion;
	private String dModificacion;

	public long getCDefmerito_id() {
		return this.cDefmerito_id;
	}

	public void setCDefmerito_id(long cDefmerito_id) {
		this.cDefmerito_id = cDefmerito_id;
	}

	public String getDNombre() {
		return this.dNombre;
	}

	public void setDNombre(String dNombre) {
		this.dNombre = dNombre;
	}

	public String getDDescripcion() {
		return this.dDescripcion;
	}

	public void setDDescripcion(String dDescripcion) {
		this.dDescripcion = dDescripcion;
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
}
