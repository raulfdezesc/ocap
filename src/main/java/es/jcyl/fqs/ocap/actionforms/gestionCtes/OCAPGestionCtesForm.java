package es.jcyl.fqs.ocap.actionforms.gestionCtes;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import java.util.ArrayList;

public class OCAPGestionCtesForm extends OCAPComunForm {

	private static final long serialVersionUID = -4610105260887449674L;
	private long cCte_id;
	private String dNombre;
	private String dDescripcion;
	private String fConstitucion;
	private String aConstitucion;
	private String mConstitucion;
	private String dConstitucion;
	private long[] listaManuales;
	private long cConvocatoria_id;
	private String dConvoc_nombre;
	private long cCteConvoId;
	private ArrayList listadoConv;
	private long cGerenciaId;
	private String dNombreGerencia;

	public void setCCte_id(long cCte_id) {
		this.cCte_id = cCte_id;
	}

	public long getCCte_id() {
		return this.cCte_id;
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

	public void setCConvocatoria_id(long cConvocatoria_id) {
		this.cConvocatoria_id = cConvocatoria_id;
	}

	public long getCConvocatoria_id() {
		return this.cConvocatoria_id;
	}

	public void setDConvoc_nombre(String dConvoc_nombre) {
		this.dConvoc_nombre = dConvoc_nombre;
	}

	public String getDConvoc_nombre() {
		return this.dConvoc_nombre;
	}

	public void setFConstitucion(String fConstitucion) {
		this.fConstitucion = fConstitucion;
	}

	public String getFConstitucion() {
		return this.fConstitucion;
	}

	public void setAConstitucion(String aConstitucion) {
		this.aConstitucion = aConstitucion;
	}

	public String getAConstitucion() {
		return this.aConstitucion;
	}

	public void setMConstitucion(String mConstitucion) {
		this.mConstitucion = mConstitucion;
	}

	public String getMConstitucion() {
		return this.mConstitucion;
	}

	public void setDConstitucion(String dConstitucion) {
		this.dConstitucion = dConstitucion;
	}

	public String getDConstitucion() {
		return this.dConstitucion;
	}

	public void setListadoConv(ArrayList listadoConv) {
		this.listadoConv = listadoConv;
	}

	public ArrayList getListadoConv() {
		return this.listadoConv;
	}

	public void setListaManuales(long[] listaManuales) {
		this.listaManuales = listaManuales;
	}

	public long[] getListaManuales() {
		return this.listaManuales;
	}

	public void setCCteConvoId(long cCteConvoId) {
		this.cCteConvoId = cCteConvoId;
	}

	public long getCCteConvoId() {
		return this.cCteConvoId;
	}

	public void setCGerenciaId(long cGerenciaId) {
		this.cGerenciaId = cGerenciaId;
	}

	public long getCGerenciaId() {
		return this.cGerenciaId;
	}

	public void setDNombreGerencia(String dNombreGerencia) {
		this.dNombreGerencia = dNombreGerencia;
	}

	public String getDNombreGerencia() {
		return this.dNombreGerencia;
	}
}
