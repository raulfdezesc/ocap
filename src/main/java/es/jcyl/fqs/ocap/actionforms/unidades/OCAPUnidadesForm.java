package es.jcyl.fqs.ocap.actionforms.unidades;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import java.sql.Date;

public class OCAPUnidadesForm extends OCAPComunForm {

	private static final long serialVersionUID = -7031512453182664267L;
	private long cUnidad_id;
	private long cArea_id;
	private String dNombre;
	private String dDescripcion;
	private String dObservaciones;
	private String dArea_nombre;
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
	private String aNombre_corto;
	private String dProfesional_nombre;

	public void setCUnidad_id(long cUnidad_id) {
		this.cUnidad_id = cUnidad_id;
	}

	public long getCUnidad_id() {
		return this.cUnidad_id;
	}

	public void setCArea_id(long cArea_id) {
		this.cArea_id = cArea_id;
	}

	public long getCArea_id() {
		return this.cArea_id;
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

	public void setDArea_nombre(String dArea_nombre) {
		this.dArea_nombre = dArea_nombre;
	}

	public String getDArea_nombre() {
		return this.dArea_nombre;
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

	public void setDObservaciones(String dObservaciones) {
		this.dObservaciones = dObservaciones;
	}

	public String getDObservaciones() {
		return this.dObservaciones;
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

	public void setANombre_corto(String aNombre_corto) {
		this.aNombre_corto = aNombre_corto;
	}

	public String getANombre_corto() {
		return this.aNombre_corto;
	}

	public void setDProfesional_nombre(String dProfesional_nombre) {
		this.dProfesional_nombre = dProfesional_nombre;
	}

	public String getDProfesional_nombre() {
		return this.dProfesional_nombre;
	}
}
