package es.jcyl.fqs.ocap.actionforms.mensajes;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import java.sql.Date;

public class OCAPMensajesForm extends OCAPComunForm {

	private static final long serialVersionUID = -8325761239804667493L;
	private long cMensaje_id;
	private long cEstatut_id;
	private long cGrado_id;
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
	private String dGrado_nombre;

	public void setCMensaje_id(long cMensaje_id) {
		this.cMensaje_id = cMensaje_id;
	}

	public long getCMensaje_id() {
		return this.cMensaje_id;
	}

	public void setCEstatut_id(long cEstatut_id) {
		this.cEstatut_id = cEstatut_id;
	}

	public long getCEstatut_id() {
		return this.cEstatut_id;
	}

	public void setCGrado_id(long cGrado_id) {
		this.cGrado_id = cGrado_id;
	}

	public long getCGrado_id() {
		return this.cGrado_id;
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

	public void setDGrado_nombre(String dGrado_nombre) {
		this.dGrado_nombre = dGrado_nombre;
	}

	public String getDGrado_nombre() {
		return this.dGrado_nombre;
	}

	public void setDDescripcion(String dDescripcion) {
		this.dDescripcion = dDescripcion;
	}

	public String getDDescripcion() {
		return this.dDescripcion;
	}
}
