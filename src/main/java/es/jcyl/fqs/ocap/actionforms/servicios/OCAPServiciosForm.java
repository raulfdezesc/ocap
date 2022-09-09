package es.jcyl.fqs.ocap.actionforms.servicios;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import java.sql.Date;

public class OCAPServiciosForm extends OCAPComunForm {

	private static final long serialVersionUID = -3749063942765482233L;
	private long cServicio_id;
	private String dNombre_corto;
	private String dNombre_largo;
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
	private long cProfesional_id;
	private long cItinerario_id;
	private long cEspec_id;
	private String dProfesional_nombre;
	private String dEspec_nombre;

	public void setCServicio_id(long cServicio_id) {
		this.cServicio_id = cServicio_id;
	}

	public long getCServicio_id() {
		return this.cServicio_id;
	}

	public void setDNombre_corto(String dNombre_corto) {
		this.dNombre_corto = dNombre_corto;
	}

	public String getDNombre_corto() {
		return this.dNombre_corto;
	}

	public void setDNombre_largo(String dNombre_largo) {
		this.dNombre_largo = dNombre_largo;
	}

	public String getDNombre_largo() {
		return this.dNombre_largo;
	}

	public void setDObservaciones(String dObservaciones) {
		this.dObservaciones = dObservaciones;
	}

	public String getDObservaciones() {
		return this.dObservaciones;
	}

	public void setFCreacion(Date fCreacion) {
		this.fCreacion = fCreacion;
	}

	public Date getFCreacion() {
		return this.fCreacion;
	}

	public void setFModificacion(Date fModificacion) {
		this.fModificacion = fModificacion;
	}

	public Date getFModificacion() {
		return this.fModificacion;
	}

	public void setBBorrado(String bBorrado) {
		this.bBorrado = bBorrado;
	}

	public String getBBorrado() {
		return this.bBorrado;
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

	public void setCProfesional_id(long cProfesional_id) {
		this.cProfesional_id = cProfesional_id;
	}

	public long getCProfesional_id() {
		return this.cProfesional_id;
	}

	public void setCItinerario_id(long cItinerario_id) {
		this.cItinerario_id = cItinerario_id;
	}

	public long getCItinerario_id() {
		return this.cItinerario_id;
	}

	public void setCEspec_id(long cEspec_id) {
		this.cEspec_id = cEspec_id;
	}

	public long getCEspec_id() {
		return this.cEspec_id;
	}

	public void setDProfesional_nombre(String dProfesional_nombre) {
		this.dProfesional_nombre = dProfesional_nombre;
	}

	public String getDProfesional_nombre() {
		return this.dProfesional_nombre;
	}

	public void setDEspec_nombre(String dEspec_nombre) {
		this.dEspec_nombre = dEspec_nombre;
	}

	public String getDEspec_nombre() {
		return this.dEspec_nombre;
	}
}
