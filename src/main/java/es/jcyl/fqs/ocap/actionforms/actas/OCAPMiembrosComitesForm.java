package es.jcyl.fqs.ocap.actionforms.actas;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import java.sql.Date;

import org.apache.struts.upload.FormFile;

public class OCAPMiembrosComitesForm extends OCAPComunForm {
	private long cMiembroId;
	private long cConvocatoria;
	private long cGerenciaId;
	private long cProfesionalId;
	private String bBorrado;
	private String cSexo;
	private String cTipo;
	private String cUsuAlta;
	private String cUsuModi;
	private String dApellidos;
	private String dNombre;
	private Date fUsuAlta;
	private Date fUsuModi;
	private String dDatosMiembro;
	private FormFile archivoExcel;
	private String dFichero;

	public void setCMiembroId(long cMiembroId) {
		this.cMiembroId = cMiembroId;
	}

	public long getCMiembroId() {
		return this.cMiembroId;
	}

	public void setCConvocatoria(long cConvocatoria) {
		this.cConvocatoria = cConvocatoria;
	}

	public long getCConvocatoria() {
		return this.cConvocatoria;
	}

	public void setCGerenciaId(long cGerenciaId) {
		this.cGerenciaId = cGerenciaId;
	}

	public long getCGerenciaId() {
		return this.cGerenciaId;
	}

	public void setCProfesionalId(long cProfesionalId) {
		this.cProfesionalId = cProfesionalId;
	}

	public long getCProfesionalId() {
		return this.cProfesionalId;
	}

	public void setBBorrado(String bBorrado) {
		this.bBorrado = bBorrado;
	}

	public String getBBorrado() {
		return this.bBorrado;
	}

	public void setCSexo(String cSexo) {
		this.cSexo = cSexo;
	}

	public String getCSexo() {
		return this.cSexo;
	}

	public void setCTipo(String cTipo) {
		this.cTipo = cTipo;
	}

	public String getCTipo() {
		return this.cTipo;
	}

	public void setCUsuAlta(String cUsuAlta) {
		this.cUsuAlta = cUsuAlta;
	}

	public String getCUsuAlta() {
		return this.cUsuAlta;
	}

	public void setDApellidos(String dApellidos) {
		this.dApellidos = dApellidos;
	}

	public String getDApellidos() {
		return this.dApellidos;
	}

	public void setDNombre(String dNombre) {
		this.dNombre = dNombre;
	}

	public String getDNombre() {
		return this.dNombre;
	}

	public void setFUsuAlta(Date fUsuAlta) {
		this.fUsuAlta = fUsuAlta;
	}

	public Date getFUsuAlta() {
		return this.fUsuAlta;
	}

	public void setFUsuModi(Date fUsuModi) {
		this.fUsuModi = fUsuModi;
	}

	public Date getFUsuModi() {
		return this.fUsuModi;
	}

	public void setDDatosMiembro(String dDatosMiembro) {
		this.dDatosMiembro = dDatosMiembro;
	}

	public String getDDatosMiembro() {
		return this.dDatosMiembro;
	}

	public FormFile getArchivoExcel() {
		return archivoExcel;
	}

	public void setArchivoExcel(FormFile archivoExcel) {
		this.archivoExcel = archivoExcel;
	}

	public String getdFichero() {
		return dFichero;
	}

	public void setdFichero(String dFichero) {
		this.dFichero = dFichero;
	}


}
