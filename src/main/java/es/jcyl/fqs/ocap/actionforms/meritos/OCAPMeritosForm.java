package es.jcyl.fqs.ocap.actionforms.meritos;

import java.util.ArrayList;
import org.apache.struts.action.ActionForm;

public class OCAPMeritosForm extends ActionForm {

	private static final long serialVersionUID = 4512558302745952743L;
	private Long cExpmcId;
	private Long cExpId;
	private Integer cMeritoId;
	private String dTipoMerito;
	private String cTipoMerito;
	private Integer cActividadId;
	private Integer cModalidadId;
	private String dTitulo;
	private String nISBN;
	private String dEditorial;
	private String fInicio;
	private String fFinalizacion;
	private String aOrganizador;
	private String nHoras;
	private String nDias;
	private String nCreditosCurso;
	private Integer fAnnio;
	private String nSesiones;
	private String fExpedicion;
	private String aLugarExpedicion;
	private String bBorrado;
	private String bParticipacion;
	private Integer fAnnioFin;
	private String bAcreditado;
	private String pestanaSeleccionada;
	private String cDefMeritoId;
	private Integer cEstatutId;
	private String accionBoton;
	private String dDescripcionMerito;
	private String dNombreMerito;
	private String dObservMerito;
	private ArrayList listaActividades;
	private ArrayList listaModalidades;
	private ArrayList listaFactores;
	private ArrayList listaTiposFirmante;
	private String aNombreRevista;
	private String bOpcional;
	private String nAnnios;
	private String nMeses;
	private String aCargo;
	private String aObjetivo;
	private Integer cFactorId;
	private Integer cTipoId;
	private String resumenCertificados;
	private ArrayList listadoGerencias;
	private String bDetalle;
	private String cEstado;
	private String bPdteAclarar;
	private String bInvalidado;
	private String cDni;
	private String cPersonalId;
	private String nCredUsuario;
	private String nCredModif;
	private String dMotivoCredModif;
	private String meritosAnteriores;
	private String nHorasUsuario;
	private String nHorasModif;
	
	private String fComprobMeritos;

	public void setCExpId(Long cExpId) {
		this.cExpId = cExpId;
	}

	public Long getCExpId() {
		return this.cExpId;
	}

	public void setCMeritoId(Integer cMeritoId) {
		this.cMeritoId = cMeritoId;
	}

	public Integer getCMeritoId() {
		return this.cMeritoId;
	}

	public void setCActividadId(Integer cActividadId) {
		this.cActividadId = cActividadId;
	}

	public Integer getCActividadId() {
		return this.cActividadId;
	}

	public void setCModalidadId(Integer cModalidadId) {
		this.cModalidadId = cModalidadId;
	}

	public Integer getCModalidadId() {
		return this.cModalidadId;
	}

	public void setDTitulo(String dTitulo) {
		this.dTitulo = dTitulo;
	}

	public String getDTitulo() {
		return this.dTitulo;
	}

	public void setFInicio(String fInicio) {
		this.fInicio = fInicio;
	}

	public String getFInicio() {
		return this.fInicio;
	}

	public void setFFinalizacion(String fFinalizacion) {
		this.fFinalizacion = fFinalizacion;
	}

	public String getFFinalizacion() {
		return this.fFinalizacion;
	}

	public void setNHoras(String nHoras) {
		this.nHoras = nHoras;
	}

	public String getNHoras() {
		return this.nHoras;
	}

	public void setNDias(String nDias) {
		this.nDias = nDias;
	}

	public String getNDias() {
		return this.nDias;
	}

	public void setFAnnio(Integer fAnnio) {
		this.fAnnio = fAnnio;
	}

	public Integer getFAnnio() {
		return this.fAnnio;
	}

	public void setNSesiones(String nSesiones) {
		this.nSesiones = nSesiones;
	}

	public String getNSesiones() {
		return this.nSesiones;
	}

	public void setFExpedicion(String fExpedicion) {
		this.fExpedicion = fExpedicion;
	}

	public String getFExpedicion() {
		return this.fExpedicion;
	}

	public void setBBorrado(String bBorrado) {
		this.bBorrado = bBorrado;
	}

	public String getBBorrado() {
		return this.bBorrado;
	}

	public void setBParticipacion(String bParticipacion) {
		this.bParticipacion = bParticipacion;
	}

	public String getBParticipacion() {
		return this.bParticipacion;
	}

	public void setPestanaSeleccionada(String pestanaSeleccionada) {
		this.pestanaSeleccionada = pestanaSeleccionada;
	}

	public String getPestanaSeleccionada() {
		return this.pestanaSeleccionada;
	}

	public void setDTipoMerito(String dTipoMerito) {
		this.dTipoMerito = dTipoMerito;
	}

	public String getDTipoMerito() {
		return this.dTipoMerito;
	}

	public void setAccionBoton(String accionBoton) {
		this.accionBoton = accionBoton;
	}

	public String getAccionBoton() {
		return this.accionBoton;
	}

	public void setCEstatutId(Integer cEstatutId) {
		this.cEstatutId = cEstatutId;
	}

	public Integer getCEstatutId() {
		return this.cEstatutId;
	}

	public void setCExpmcId(Long cExpmcId) {
		this.cExpmcId = cExpmcId;
	}

	public Long getCExpmcId() {
		return this.cExpmcId;
	}

	public void setCDefMeritoId(String cDefMeritoId) {
		this.cDefMeritoId = cDefMeritoId;
	}

	public String getCDefMeritoId() {
		return this.cDefMeritoId;
	}

	public void setDDescripcionMerito(String dDescripcionMerito) {
		this.dDescripcionMerito = dDescripcionMerito;
	}

	public String getDDescripcionMerito() {
		return this.dDescripcionMerito;
	}

	public void setDNombreMerito(String dNombreMerito) {
		this.dNombreMerito = dNombreMerito;
	}

	public String getDNombreMerito() {
		return this.dNombreMerito;
	}

	public void setListaActividades(ArrayList listaActividades) {
		this.listaActividades = listaActividades;
	}

	public ArrayList getListaActividades() {
		return this.listaActividades;
	}

	public void setListaModalidades(ArrayList listaModalidades) {
		this.listaModalidades = listaModalidades;
	}

	public ArrayList getListaModalidades() {
		return this.listaModalidades;
	}

	public void setNAnnios(String nAnnios) {
		this.nAnnios = nAnnios;
	}

	public String getNAnnios() {
		return this.nAnnios;
	}

	public void setACargo(String aCargo) {
		this.aCargo = aCargo;
	}

	public String getACargo() {
		return this.aCargo;
	}

	public void setAObjetivo(String aObjetivo) {
		this.aObjetivo = aObjetivo;
	}

	public String getAObjetivo() {
		return this.aObjetivo;
	}

	public void setListaFactores(ArrayList listaFactores) {
		this.listaFactores = listaFactores;
	}

	public ArrayList getListaFactores() {
		return this.listaFactores;
	}

	public void setListaTiposFirmante(ArrayList listaTiposFirmante) {
		this.listaTiposFirmante = listaTiposFirmante;
	}

	public ArrayList getListaTiposFirmante() {
		return this.listaTiposFirmante;
	}

	public void setCFactorId(Integer cFactorId) {
		this.cFactorId = cFactorId;
	}

	public Integer getCFactorId() {
		return this.cFactorId;
	}

	public void setCTipoId(Integer cTipoId) {
		this.cTipoId = cTipoId;
	}

	public Integer getCTipoId() {
		return this.cTipoId;
	}

	public void setDObservMerito(String dObservMerito) {
		this.dObservMerito = dObservMerito;
	}

	public String getDObservMerito() {
		return this.dObservMerito;
	}

	public void setNMeses(String nMeses) {
		this.nMeses = nMeses;
	}

	public String getNMeses() {
		return this.nMeses;
	}

	public void setAOrganizador(String aOrganizador) {
		this.aOrganizador = aOrganizador;
	}

	public String getAOrganizador() {
		return this.aOrganizador;
	}

	public void setALugarExpedicion(String aLugarExpedicion) {
		this.aLugarExpedicion = aLugarExpedicion;
	}

	public String getALugarExpedicion() {
		return this.aLugarExpedicion;
	}

	public void setANombreRevista(String aNombreRevista) {
		this.aNombreRevista = aNombreRevista;
	}

	public String getANombreRevista() {
		return this.aNombreRevista;
	}

	public void setBOpcional(String bOpcional) {
		this.bOpcional = bOpcional;
	}

	public String getBOpcional() {
		return this.bOpcional;
	}

	public void setNCreditosCurso(String nCreditosCurso) {
		this.nCreditosCurso = nCreditosCurso;
	}

	public String getNCreditosCurso() {
		return this.nCreditosCurso;
	}

	public void setNISBN(String nISBN) {
		this.nISBN = nISBN;
	}

	public String getNISBN() {
		return this.nISBN;
	}

	public void setDEditorial(String dEditorial) {
		this.dEditorial = dEditorial;
	}

	public String getDEditorial() {
		return this.dEditorial;
	}

	public void setResumenCertificados(String resumenCertificados) {
		this.resumenCertificados = resumenCertificados;
	}

	public String getResumenCertificados() {
		return this.resumenCertificados;
	}

	public void setListadoGerencias(ArrayList listadoGerencias) {
		this.listadoGerencias = listadoGerencias;
	}

	public ArrayList getListadoGerencias() {
		return this.listadoGerencias;
	}

	public void setBDetalle(String bDetalle) {
		this.bDetalle = bDetalle;
	}

	public String getBDetalle() {
		return this.bDetalle;
	}

	public void setCDni(String cDni) {
		this.cDni = cDni;
	}

	public String getCDni() {
		return this.cDni;
	}

	public void setCEstado(String cEstado) {
		this.cEstado = cEstado;
	}

	public String getCEstado() {
		return this.cEstado;
	}

	public void setBPdteAclarar(String bPdteAclarar) {
		this.bPdteAclarar = bPdteAclarar;
	}

	public String getBPdteAclarar() {
		return this.bPdteAclarar;
	}

	public void setCTipoMerito(String cTipoMerito) {
		this.cTipoMerito = cTipoMerito;
	}

	public String getCTipoMerito() {
		return this.cTipoMerito;
	}

	public void setFAnnioFin(Integer fAnnioFin) {
		this.fAnnioFin = fAnnioFin;
	}

	public Integer getFAnnioFin() {
		return this.fAnnioFin;
	}

	public void setBAcreditado(String bAcreditado) {
		this.bAcreditado = bAcreditado;
	}

	public String getBAcreditado() {
		return this.bAcreditado;
	}

	public void setNCredUsuario(String nCredUsuario) {
		this.nCredUsuario = nCredUsuario;
	}

	public String getNCredUsuario() {
		return this.nCredUsuario;
	}

	public void setNCredModif(String nCredModif) {
		this.nCredModif = nCredModif;
	}

	public String getNCredModif() {
		return this.nCredModif;
	}

	public void setBInvalidado(String bInvalidado) {
		this.bInvalidado = bInvalidado;
	}

	public String getBInvalidado() {
		return this.bInvalidado;
	}

	public void setCPersonalId(String cPersonalId) {
		this.cPersonalId = cPersonalId;
	}

	public String getCPersonalId() {
		return this.cPersonalId;
	}

	public String getFComprobMeritos() {
		return fComprobMeritos;
	}

	public void setFComprobMeritos(String fComprobMeritos) {
		this.fComprobMeritos = fComprobMeritos;
	}

	public String getDMotivoCredModif() {
		return dMotivoCredModif;
	}

	public void setDMotivoCredModif(String dMotivoCredModif) {
		this.dMotivoCredModif = dMotivoCredModif;
	}

	public String getMeritosAnteriores() {
		return meritosAnteriores;
	}

	public void setMeritosAnteriores(String meritosAnteriores) {
		this.meritosAnteriores = meritosAnteriores;
	}

	public String getNHorasUsuario() {
		return nHorasUsuario;
	}

	public void setNHorasUsuario(String nHorasUsuario) {
		this.nHorasUsuario = nHorasUsuario;
	}

	public String getNHorasModif() {
		return nHorasModif;
	}

	public void setNHorasModif(String nHorasModif) {
		this.nHorasModif = nHorasModif;
	}


	
}
