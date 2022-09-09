package es.jcyl.fqs.ocap.actionforms.actas;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import java.util.ArrayList;

public class OCAPActasForm extends OCAPComunForm {
	private boolean jspInicio;
	private long cGradoId;
	private long cConvocatoriaId;
	private long cProfesionalId;
	private String cActaTipo;
	private long cGerenciaId;
	private ArrayList listaPresidentes;
	private ArrayList listaVocales;
	private ArrayList listaSecretarios;
	private String[] bPresidente;
	private String[] bVocal;
	private String[] bSecretario;
	private String[] bEnCalidadPresi;
	private String[] bEnCalidadVocal;
	private String[] bEnCalidadSecre;
	private String dMiembrosAusentes;
	private String dAsuntosTramite;
	private String dRuegosPreguntas;
	private String nHoraInicio;
	private String nMinutosInicio;
	private String nHoraFin;
	private String nMinutosFin;
	private String fInicio;
	private ArrayList listaUsuariosInformeMotivado;
	private ArrayList listaUsuariosAclaraciones;
	private String[] bUsuario;
	private String[] bTipoInformeUsuario;
	private long cActaId;
	private ArrayList listados;

	public void setJspInicio(boolean jspInicio) {
		this.jspInicio = jspInicio;
	}

	public boolean getJspInicio() {
		return this.jspInicio;
	}

	public void setCConvocatoriaId(long cConvocatoriaId) {
		this.cConvocatoriaId = cConvocatoriaId;
	}

	public long getCConvocatoriaId() {
		return this.cConvocatoriaId;
	}


	
	public void setCProfesionalId(long cProfesionalId) {
		this.cProfesionalId = cProfesionalId;
	}

	public long getCProfesionalId() {
		return this.cProfesionalId;
	}	

	public void setCGerenciaId(long cGerenciaId) {
		this.cGerenciaId = cGerenciaId;
	}

	public long getCGerenciaId() {
		return this.cGerenciaId;
	}

	public void setListados(ArrayList listados) {
		this.listados = listados;
	}

	public ArrayList getListados() {
		return this.listados;
	}
	
	public void setListaPresidentes(ArrayList listaPresidentes) {
		this.listaPresidentes = listaPresidentes;
	}

	public ArrayList getListaPresidentes() {
		return this.listaPresidentes;
	}

	public void setListaVocales(ArrayList listaVocales) {
		this.listaVocales = listaVocales;
	}

	public ArrayList getListaVocales() {
		return this.listaVocales;
	}

	public void setListaSecretarios(ArrayList listaSecretarios) {
		this.listaSecretarios = listaSecretarios;
	}

	public ArrayList getListaSecretarios() {
		return this.listaSecretarios;
	}

	public void setBPresidente(String[] bPresidente) {
		this.bPresidente = bPresidente;
	}

	public String[] getBPresidente() {
		return this.bPresidente;
	}

	public void setBVocal(String[] bVocal) {
		this.bVocal = bVocal;
	}

	public String[] getBVocal() {
		return this.bVocal;
	}

	public void setBSecretario(String[] bSecretario) {
		this.bSecretario = bSecretario;
	}

	public String[] getBSecretario() {
		return this.bSecretario;
	}

	public void setBEnCalidadPresi(String[] bEnCalidadPresi) {
		this.bEnCalidadPresi = bEnCalidadPresi;
	}

	public String[] getBEnCalidadPresi() {
		return this.bEnCalidadPresi;
	}

	public void setBEnCalidadVocal(String[] bEnCalidadVocal) {
		this.bEnCalidadVocal = bEnCalidadVocal;
	}

	public String[] getBEnCalidadVocal() {
		return this.bEnCalidadVocal;
	}

	public void setBEnCalidadSecre(String[] bEnCalidadSecre) {
		this.bEnCalidadSecre = bEnCalidadSecre;
	}

	public String[] getBEnCalidadSecre() {
		return this.bEnCalidadSecre;
	}

	public void setDMiembrosAusentes(String dMiembrosAusentes) {
		this.dMiembrosAusentes = dMiembrosAusentes;
	}

	public String getDMiembrosAusentes() {
		return this.dMiembrosAusentes;
	}

	public void setDAsuntosTramite(String dAsuntosTramite) {
		this.dAsuntosTramite = dAsuntosTramite;
	}

	public String getDAsuntosTramite() {
		return this.dAsuntosTramite;
	}

	public void setDRuegosPreguntas(String dRuegosPreguntas) {
		this.dRuegosPreguntas = dRuegosPreguntas;
	}

	public String getDRuegosPreguntas() {
		return this.dRuegosPreguntas;
	}

	public void setNHoraInicio(String nHoraInicio) {
		this.nHoraInicio = nHoraInicio;
	}

	public String getNHoraInicio() {
		return this.nHoraInicio;
	}

	public void setNMinutosInicio(String nMinutosInicio) {
		this.nMinutosInicio = nMinutosInicio;
	}

	public String getNMinutosInicio() {
		return this.nMinutosInicio;
	}

	public void setNHoraFin(String nHoraFin) {
		this.nHoraFin = nHoraFin;
	}

	public String getNHoraFin() {
		return this.nHoraFin;
	}

	public void setNMinutosFin(String nMinutosFin) {
		this.nMinutosFin = nMinutosFin;
	}

	public String getNMinutosFin() {
		return this.nMinutosFin;
	}

	public void setFInicio(String fInicio) {
		this.fInicio = fInicio;
	}

	public String getFInicio() {
		return this.fInicio;
	}

	public void setCGradoId(long cGradoId) {
		this.cGradoId = cGradoId;
	}

	public long getCGradoId() {
		return this.cGradoId;
	}

	public void setListaUsuariosInformeMotivado(ArrayList listaUsuariosInformeMotivado) {
		this.listaUsuariosInformeMotivado = listaUsuariosInformeMotivado;
	}

	public ArrayList getListaUsuariosInformeMotivado() {
		return this.listaUsuariosInformeMotivado;
	}

	public void setListaUsuariosAclaraciones(ArrayList listaUsuariosAclaraciones) {
		this.listaUsuariosAclaraciones = listaUsuariosAclaraciones;
	}

	public ArrayList getListaUsuariosAclaraciones() {
		return this.listaUsuariosAclaraciones;
	}

	public void setCActaId(long cActaId) {
		this.cActaId = cActaId;
	}

	public long getCActaId() {
		return this.cActaId;
	}

	public void setBUsuario(String[] bUsuario) {
		this.bUsuario = bUsuario;
	}

	public String[] getBUsuario() {
		return this.bUsuario;
	}

	public void setBTipoInformeUsuario(String[] bTipoInformeUsuario) {
		this.bTipoInformeUsuario = bTipoInformeUsuario;
	}

	public String[] getBTipoInformeUsuario() {
		return this.bTipoInformeUsuario;
	}

	public String getCActaTipo() {
		return cActaTipo;
	}

	public void setCActaTipo(String cActaTipo) {
		this.cActaTipo = cActaTipo;
	}
	
	
}
