package es.jcyl.fqs.ocap.actionforms.centroTrabajo;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import java.sql.Date;

public class OCAPCentroTrabajoForm extends OCAPComunForm {
	 
	private static final long serialVersionUID = -8803259576098745303L;
	private long cCentrotrabajo_id;
	private long cGerencia_id;
	private String dNombre;
	private String dDescripcion;
	private String aObservaciones;
	private String aDireccion;
	private String aCodigopostal;
	private String aTelefono;
	private String aCiudad;
	private String dGerencia_nombre;
	private Date fCreacion;
	private Date fModificacion;
	private String bBorrado;
	private String aCreacion;
	private String mCreacion;
	private String dCreacion;
	private String aModificacion;
	private String mModificacion;
	private String dModificacion;

	public void setCCentrotrabajo_id(long cCentrotrabajo_id) {
		this.cCentrotrabajo_id = cCentrotrabajo_id;
	}

	public long getCCentrotrabajo_id() {
		return this.cCentrotrabajo_id;
	}

	public void setCGerencia_id(long cGerencia_id) {
		this.cGerencia_id = cGerencia_id;
	}

	public long getCGerencia_id() {
		return this.cGerencia_id;
	}

	public void setDNombre(String dNombre) {
		this.dNombre = dNombre;
	}

	public String getDNombre() {
		return this.dNombre;
	}

	public void setDDescripcion(String dDescripcion) {
		this.dDescripcion = dDescripcion;
	}

	public String getDDescripcion() {
		return this.dDescripcion;
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

	public void setAObservaciones(String aObservaciones) {
		this.aObservaciones = aObservaciones;
	}

	public String getAObservaciones() {
		return this.aObservaciones;
	}

	public void setADireccion(String aDireccion) {
		this.aDireccion = aDireccion;
	}

	public String getADireccion() {
		return this.aDireccion;
	}

	public void setACodigopostal(String aCodigopostal) {
		this.aCodigopostal = aCodigopostal;
	}

	public String getACodigopostal() {
		return this.aCodigopostal;
	}

	public void setATelefono(String aTelefono) {
		this.aTelefono = aTelefono;
	}

	public String getATelefono() {
		return this.aTelefono;
	}

	public void setACiudad(String aCiudad) {
		this.aCiudad = aCiudad;
	}

	public String getACiudad() {
		return this.aCiudad;
	}

	public void setDGerencia_nombre(String dGerencia_nombre) {
		this.dGerencia_nombre = dGerencia_nombre;
	}

	public String getDGerencia_nombre() {
		return this.dGerencia_nombre;
	}
}
