package es.jcyl.fqs.ocap.actionforms.encuestaCalidad;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import java.util.ArrayList;

public class OCAPEncuestaCalidadForm extends OCAPComunForm {

	private static final long serialVersionUID = 6613668855899256020L;
	private long cExpId;
	private String[] respuesta;
	private String[] respuestaRadio;
	private long cItinerario_id;
	private long cProfesional_id;
	private long cEspec_id;
	private ArrayList listaItinerariosPreguntas;
	private ArrayList listaItinerariosPreguntasCuenta;
	private float porc1;
	private float porc2;
	private float porc3;
	private float porc4;
	private float porc5;
	private String numTotalEvaluados;
	private String numTotalEncuestas;
	private int nTotalEvaluados;
	private int nTotalEncuestas;
	private int nTotalSi;
	private int nTotalNo;
	private ArrayList listaAreas;

	public void setCExpId(long cExpId) {
		this.cExpId = cExpId;
	}

	public long getCExpId() {
		return this.cExpId;
	}

	public void setCItinerario_id(long cItinerario_id) {
		this.cItinerario_id = cItinerario_id;
	}

	public long getCItinerario_id() {
		return this.cItinerario_id;
	}

	public void setListaItinerariosPreguntas(ArrayList listaItinerariosPreguntas) {
		this.listaItinerariosPreguntas = listaItinerariosPreguntas;
	}

	public ArrayList getListaItinerariosPreguntas() {
		return this.listaItinerariosPreguntas;
	}

	public void setListaItinerariosPreguntasCuenta(ArrayList listaItinerariosPreguntasCuenta) {
		this.listaItinerariosPreguntasCuenta = listaItinerariosPreguntasCuenta;
	}

	public ArrayList getListaItinerariosPreguntasCuenta() {
		return this.listaItinerariosPreguntasCuenta;
	}

	public void setPorc1(float porc1) {
		this.porc1 = porc1;
	}

	public float getPorc1() {
		return this.porc1;
	}

	public void setPorc2(float porc2) {
		this.porc2 = porc2;
	}

	public float getPorc2() {
		return this.porc2;
	}

	public void setPorc3(float porc3) {
		this.porc3 = porc3;
	}

	public float getPorc3() {
		return this.porc3;
	}

	public void setPorc4(float porc4) {
		this.porc4 = porc4;
	}

	public float getPorc4() {
		return this.porc4;
	}

	public void setPorc5(float porc5) {
		this.porc5 = porc5;
	}

	public float getPorc5() {
		return this.porc5;
	}

	public void setNumTotalEvaluados(String numTotalEvaluados) {
		this.numTotalEvaluados = numTotalEvaluados;
	}

	public String getNumTotalEvaluados() {
		return this.numTotalEvaluados;
	}

	public void setNumTotalEncuestas(String numTotalEncuestas) {
		this.numTotalEncuestas = numTotalEncuestas;
	}

	public String getNumTotalEncuestas() {
		return this.numTotalEncuestas;
	}

	public void setNTotalEvaluados(int nTotalEvaluados) {
		this.nTotalEvaluados = nTotalEvaluados;
	}

	public int getNTotalEvaluados() {
		return this.nTotalEvaluados;
	}

	public void setNTotalEncuestas(int nTotalEncuestas) {
		this.nTotalEncuestas = nTotalEncuestas;
	}

	public int getNTotalEncuestas() {
		return this.nTotalEncuestas;
	}

	public void setCProfesional_id(long cProfesional_id) {
		this.cProfesional_id = cProfesional_id;
	}

	public long getCProfesional_id() {
		return this.cProfesional_id;
	}

	public void setCEspec_id(long cEspec_id) {
		this.cEspec_id = cEspec_id;
	}

	public long getCEspec_id() {
		return this.cEspec_id;
	}

	public void setNTotalSi(int nTotalSi) {
		this.nTotalSi = nTotalSi;
	}

	public int getNTotalSi() {
		return this.nTotalSi;
	}

	public void setNTotalNo(int nTotalNo) {
		this.nTotalNo = nTotalNo;
	}

	public int getNTotalNo() {
		return this.nTotalNo;
	}

	public void setListaAreas(ArrayList listaAreas) {
		this.listaAreas = listaAreas;
	}

	public ArrayList getListaAreas() {
		return this.listaAreas;
	}

	public void setRespuesta(String[] respuesta) {
		this.respuesta = respuesta;
	}

	public String[] getRespuesta() {
		return this.respuesta;
	}

	public void setRespuestaRadio(String[] respuestaRadio) {
		this.respuestaRadio = respuestaRadio;
	}

	public void setRespuesta(int index, String respuesta) {
		if (this.respuesta == null)
			this.respuesta = new String[35];
		this.respuesta[index] = respuesta;
	}

	public String getRespuesta(int index) {
		if (index > this.respuesta.length) {
			return this.respuesta[(this.respuesta.length - 1)];
		}

		return this.respuesta[index];
	}

	public String[] getRespuestaRadio() {
		return this.respuestaRadio;
	}
}
