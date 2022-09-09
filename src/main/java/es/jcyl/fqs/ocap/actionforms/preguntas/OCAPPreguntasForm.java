package es.jcyl.fqs.ocap.actionforms.preguntas;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import java.util.Date;

public class OCAPPreguntasForm extends OCAPComunForm {

	private static final long serialVersionUID = -3981357256576253497L;
	private long cPreguntaId;
	private long cPreguntaNuevoId;
	private String dNombre;
	private String dObservaciones;
	private String cTipoPregunta;
	private Date fUsuAlta;
	private Date fUsuModi;
	private String bBorrado;
	private long nElementos;
	private long nNivel;
	private long nSubElementos;
	private String cUsuAlta;
	private String cUsuModi;
	private String bNumeracion;
	private String bCorto;
	private long cRespuestaId;
	private long cCuestionarioId;
	private String dNombreRespuesta;
	private String dValor;
	private float nCreditos;

	public void setCPreguntaId(long cPreguntaId) {
		this.cPreguntaId = cPreguntaId;
	}

	public long getCPreguntaId() {
		return this.cPreguntaId;
	}

	public void setDNombre(String dNombre) {
		this.dNombre = dNombre;
	}

	public String getDNombre() {
		return this.dNombre;
	}

	public void setDObservaciones(String dObservaciones) {
		this.dObservaciones = dObservaciones;
	}

	public String getDObservaciones() {
		return this.dObservaciones;
	}

	public void setCTipoPregunta(String cTipoPregunta) {
		this.cTipoPregunta = cTipoPregunta;
	}

	public String getCTipoPregunta() {
		return this.cTipoPregunta;
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

	public void setBBorrado(String bBorrado) {
		this.bBorrado = bBorrado;
	}

	public String getBBorrado() {
		return this.bBorrado;
	}

	public void setCUsuAlta(String cUsuAlta) {
		this.cUsuAlta = cUsuAlta;
	}

	public String getCUsuAlta() {
		return this.cUsuAlta;
	}

	public void setCUsuModi(String cUsuModi) {
		this.cUsuModi = cUsuModi;
	}

	public String getCUsuModi() {
		return this.cUsuModi;
	}

	public void setBNumeracion(String bNumeracion) {
		this.bNumeracion = bNumeracion;
	}

	public String getBNumeracion() {
		return this.bNumeracion;
	}

	public void setBCorto(String bCorto) {
		this.bCorto = bCorto;
	}

	public String getBCorto() {
		return this.bCorto;
	}

	public void setNElementos(long nElementos) {
		this.nElementos = nElementos;
	}

	public long getNElementos() {
		return this.nElementos;
	}

	public void setNNivel(long nNivel) {
		this.nNivel = nNivel;
	}

	public long getNNivel() {
		return this.nNivel;
	}

	public void setNSubElementos(long nSubElementos) {
		this.nSubElementos = nSubElementos;
	}

	public long getNSubElementos() {
		return this.nSubElementos;
	}

	public void setCRespuestaId(long cRespuestaId) {
		this.cRespuestaId = cRespuestaId;
	}

	public long getCRespuestaId() {
		return this.cRespuestaId;
	}

	public void setCCuestionarioId(long cCuestionarioId) {
		this.cCuestionarioId = cCuestionarioId;
	}

	public long getCCuestionarioId() {
		return this.cCuestionarioId;
	}

	public void setDNombreRespuesta(String dNombreRespuesta) {
		this.dNombreRespuesta = dNombreRespuesta;
	}

	public String getDNombreRespuesta() {
		return this.dNombreRespuesta;
	}

	public void setDValor(String dValor) {
		this.dValor = dValor;
	}

	public String getDValor() {
		return this.dValor;
	}

	public void setCPreguntaNuevoId(long cPreguntaNuevoId) {
		this.cPreguntaNuevoId = cPreguntaNuevoId;
	}

	public long getCPreguntaNuevoId() {
		return this.cPreguntaNuevoId;
	}

	public void setNCreditos(float nCreditos) {
		this.nCreditos = nCreditos;
	}

	public float getNCreditos() {
		return this.nCreditos;
	}
}
