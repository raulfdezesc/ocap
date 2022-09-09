package es.jcyl.fqs.ocap.actionforms.mensajesMc;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import java.sql.Date;

public class OCAPMensajesMcForm extends OCAPComunForm {

	private static final long serialVersionUID = 2633927080436047290L;
	private long cMensaje_id;
	private long cEstatut_id;
	private long cDefmerito_id;
	private String dDefmerito_nombre;
	private String dDescripcion;
	private String dEstatut_nombre;
	private Date fCreacion;
	private Date fModificacion;
	private String bBorrado;
	private String aCreacion;
	private String mCreacion;
	private String dCreacion;
	private String aModificacion;
	private String mModificacion;
	private String dModificacion;

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

	public void setDDefmerito_nombre(String dDefmerito_nombre) {
		this.dDefmerito_nombre = dDefmerito_nombre;
	}

	public String getDDefmerito_nombre() {
		return this.dDefmerito_nombre;
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

	public void setCDefmerito_id(long cDefmerito_id) {
		this.cDefmerito_id = cDefmerito_id;
	}

	public long getCDefmerito_id() {
		return this.cDefmerito_id;
	}
}
