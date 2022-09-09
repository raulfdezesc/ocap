package es.jcyl.fqs.ocap.actionforms.categProfesionales;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import java.sql.Date;

public class OCAPCategProfesionalesForm extends OCAPComunForm {
	
	 
	private static final long serialVersionUID = -5334839132127187142L;
	private long cProfesional_id;
	private long cEstatut_id;
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
	private String dEstatut_nombre;
	private long cModalidad_id;
	private String dModalidad_nombre;

	public void setCProfesional_id(long cProfesional_id) {
		this.cProfesional_id = cProfesional_id;
	}

	public long getCProfesional_id() {
		return this.cProfesional_id;
	}

	public void setCEstatut_id(long cEstatut_id) {
		this.cEstatut_id = cEstatut_id;
	}

	public long getCEstatut_id() {
		return this.cEstatut_id;
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

	public void setDEstatut_nombre(String dEstatut_nombre) {
		this.dEstatut_nombre = dEstatut_nombre;
	}

	public String getDEstatut_nombre() {
		return this.dEstatut_nombre;
	}

	public void setCModalidad_id(long cModalidad_id) {
		this.cModalidad_id = cModalidad_id;
	}

	public long getCModalidad_id() {
		return this.cModalidad_id;
	}

	public void setDModalidad_nombre(String dModalidad_nombre) {
		this.dModalidad_nombre = dModalidad_nombre;
	}

	public String getDModalidad_nombre() {
		return this.dModalidad_nombre;
	}
}
