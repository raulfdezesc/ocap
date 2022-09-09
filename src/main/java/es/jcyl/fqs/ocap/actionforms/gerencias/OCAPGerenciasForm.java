package es.jcyl.fqs.ocap.actionforms.gerencias;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import java.sql.Date;

public class OCAPGerenciasForm extends OCAPComunForm {

	private static final long serialVersionUID = 6228822373054694931L;
	private long cGerencia_id;
	private String cProvincia_id;
	private long cTipogerencia_id;
	private String dNombre;
	private String dNombre_corto;
	private String aCodldap;
	private Date fCreacion;
	private Date fModificacion;
	private String bBorrado;
	private String aCreacion;
	private String mCreacion;
	private String dCreacion;
	private String aModificacion;
	private String mModificacion;
	private String dModificacion;
	private String dProvincia_nombre;
	private String dTipogerenciaNombre;
	private String dGerente;
	private String dDirector;

	public long getCGerencia_id() {
		return this.cGerencia_id;
	}

	public void setCGerencia_id(long cGerencia_id) {
		this.cGerencia_id = cGerencia_id;
	}

	public String getCProvincia_id() {
		return this.cProvincia_id;
	}

	public void setCProvincia_id(String cProvincia_id) {
		this.cProvincia_id = cProvincia_id;
	}

	public long getCTipogerencia_id() {
		return this.cTipogerencia_id;
	}

	public void setCTipogerencia_id(long cTipogerencia_id) {
		this.cTipogerencia_id = cTipogerencia_id;
	}

	public String getDNombre() {
		return this.dNombre;
	}

	public void setDNombre(String dNombre) {
		this.dNombre = dNombre;
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

	public void setDProvincia_nombre(String dProvincia_nombre) {
		this.dProvincia_nombre = dProvincia_nombre;
	}

	public String getDProvincia_nombre() {
		return this.dProvincia_nombre;
	}

	public void setDTipogerenciaNombre(String dTipogerenciaNombre) {
		this.dTipogerenciaNombre = dTipogerenciaNombre;
	}

	public String getDTipogerenciaNombre() {
		return this.dTipogerenciaNombre;
	}

	public void setDNombre_corto(String dNombre_corto) {
		this.dNombre_corto = dNombre_corto;
	}

	public String getDNombre_corto() {
		return this.dNombre_corto;
	}

	public void setACodldap(String aCodldap) {
		this.aCodldap = aCodldap;
	}

	public String getACodldap() {
		return this.aCodldap;
	}

	public void setDGerente(String dGerente) {
		this.dGerente = dGerente;
	}

	public String getDGerente() {
		return this.dGerente;
	}

	public void setDDirector(String dDirector) {
		this.dDirector = dDirector;
	}

	public String getDDirector() {
		return this.dDirector;
	}
}
