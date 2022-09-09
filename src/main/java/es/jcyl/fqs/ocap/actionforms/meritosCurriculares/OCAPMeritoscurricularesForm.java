package es.jcyl.fqs.ocap.actionforms.meritosCurriculares;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import java.sql.Date;

public class OCAPMeritoscurricularesForm extends OCAPComunForm {

	private static final long serialVersionUID = -5985544611426158542L;
	private long cMerito_id;
	private long cDefmerito_id;
	private long cEstatut_id;
	private long cActividad_id;
	private long cModalidad_id;
	private long cFactor_id;
	private long cTipo_id;
	private String cTipo_merito;
	private String dNombrecorto;
	private String dNombre;
	private String dDescripcion;
	private String dObservaciones;
	private String nCreditos;
	private Date fCreacion;
	private Date fModificacion;
	private String bBorrado;
	private String aCreacion;
	private String mCreacion;
	private String dCreacion;
	private String aModificacion;
	private String mModificacion;
	private String dModificacion;
	private String dDefmerito_nombre;
	private String dEstatut_nombre;
	private String dActividad_nombre;
	private String dModalidad_nombre;
	private String dFactor_nombre;
	private String dTipo_nombre;

	public void setCMerito_id(long cMerito_id) {
		this.cMerito_id = cMerito_id;
	}

	public long getCMerito_id() {
		return this.cMerito_id;
	}

	public void setCDefmerito_id(long cDefmerito_id) {
		this.cDefmerito_id = cDefmerito_id;
	}

	public long getCDefmerito_id() {
		return this.cDefmerito_id;
	}

	public void setCEstatut_id(long cEstatut_id) {
		this.cEstatut_id = cEstatut_id;
	}

	public long getCEstatut_id() {
		return this.cEstatut_id;
	}

	public void setCActividad_id(long cActividad_id) {
		this.cActividad_id = cActividad_id;
	}

	public long getCActividad_id() {
		return this.cActividad_id;
	}

	public void setCModalidad_id(long cModalidad_id) {
		this.cModalidad_id = cModalidad_id;
	}

	public long getCModalidad_id() {
		return this.cModalidad_id;
	}

	public void setCFactor_id(long cFactor_id) {
		this.cFactor_id = cFactor_id;
	}

	public long getCFactor_id() {
		return this.cFactor_id;
	}

	public void setCTipo_id(long cTipo_id) {
		this.cTipo_id = cTipo_id;
	}

	public long getCTipo_id() {
		return this.cTipo_id;
	}

	public void setCTipo_merito(String cTipo_merito) {
		this.cTipo_merito = cTipo_merito;
	}

	public String getCTipo_merito() {
		return this.cTipo_merito;
	}

	public void setDNombrecorto(String dNombrecorto) {
		this.dNombrecorto = dNombrecorto;
	}

	public String getDNombrecorto() {
		return this.dNombrecorto;
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

	public void setDObservaciones(String dObservaciones) {
		this.dObservaciones = dObservaciones;
	}

	public String getDObservaciones() {
		return this.dObservaciones;
	}

	public void setNCreditos(String nCreditos) {
		this.nCreditos = nCreditos;
	}

	public String getNCreditos() {
		return this.nCreditos;
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

	public void setDDefmerito_nombre(String dDefmerito_nombre) {
		this.dDefmerito_nombre = dDefmerito_nombre;
	}

	public String getDDefmerito_nombre() {
		return this.dDefmerito_nombre;
	}

	public void setDEstatut_nombre(String dEstatut_nombre) {
		this.dEstatut_nombre = dEstatut_nombre;
	}

	public String getDEstatut_nombre() {
		return this.dEstatut_nombre;
	}

	public void setDActividad_nombre(String dActividad_nombre) {
		this.dActividad_nombre = dActividad_nombre;
	}

	public String getDActividad_nombre() {
		return this.dActividad_nombre;
	}

	public void setDModalidad_nombre(String dModalidad_nombre) {
		this.dModalidad_nombre = dModalidad_nombre;
	}

	public String getDModalidad_nombre() {
		return this.dModalidad_nombre;
	}

	public void setDFactor_nombre(String dFactor_nombre) {
		this.dFactor_nombre = dFactor_nombre;
	}

	public String getDFactor_nombre() {
		return this.dFactor_nombre;
	}

	public void setDTipo_nombre(String dTipo_nombre) {
		this.dTipo_nombre = dTipo_nombre;
	}

	public String getDTipo_nombre() {
		return this.dTipo_nombre;
	}
}
