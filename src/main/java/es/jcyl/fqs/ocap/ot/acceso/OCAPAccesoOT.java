package es.jcyl.fqs.ocap.ot.acceso;

import java.io.Serializable;

public class OCAPAccesoOT implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String cDni;
	private String aContrasenia;
	private String aCorreoRecupera;
	
	private String aNombre;
	private String aApellidos;
	private String nTelefono;
	private String nMovil;
	
	
	public String getACorreoRecupera() {
		return aCorreoRecupera;
	}
	public void setACorreoRecupera(String aCorreoRecupera) {
		this.aCorreoRecupera = aCorreoRecupera;
	}
	public String getAContrasenia() {
		return aContrasenia;
	}
	public void setAContrasenia(String aContrasenia) {
		this.aContrasenia = aContrasenia;
	}
	public String getCDni() {
		return cDni;
	}
	public void setCDni(String cDni) {
		this.cDni = cDni;
	}
	public String getANombre() {
		return aNombre;
	}
	public void setANombre(String aNombre) {
		this.aNombre = aNombre;
	}
	public String getAApellidos() {
		return aApellidos;
	}
	public void setAApellidos(String aApellidos) {
		this.aApellidos = aApellidos;
	}
	public String getNTelefono() {
		return nTelefono;
	}
	public void setNTelefono(String nTelefono) {
		this.nTelefono = nTelefono;
	}
	public String getNMovil() {
		return nMovil;
	}
	public void setNMovil(String nMovil) {
		this.nMovil = nMovil;
	}
}
