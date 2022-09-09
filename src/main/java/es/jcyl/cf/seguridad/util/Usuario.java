package es.jcyl.cf.seguridad.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Usuario implements Serializable {
	private String c_usr_id;
	private String rol;
	private String menu;
	private String d_apell;
	private String d_nom;
	private String datoSeguridad;
	private String a_password;
	private String c_dni;
	private String c_nif;
	private Date f_ultima_conexion;
	private HashMap extensiones;
	private HashMap ampliaciones;
	private String d_correo;
	private String d_telefono;
	private String d_movil;
	private String d_gerencia;
	private ArrayList<String> listaPerfiles;

	public String getC_usr_id() {
		return this.c_usr_id;
	}

	public void setC_usr_id(String newC_usr_id) {
		this.c_usr_id = newC_usr_id;
	}

	public String getRol() {
		return this.rol;
	}

	public void setRol(String newRol) {
		this.rol = newRol;
	}

	public String getDatoSeguridad() {
		return this.datoSeguridad;
	}

	public void setDatoSeguridad(String dtSeg) {
		this.datoSeguridad = dtSeg;
	}

	public String getMenu() {
		return this.menu;
	}

	public void setMenu(String newMenu) {
		this.menu = newMenu;
	}

	public String getDApell() {
		return this.d_apell;
	}

	public void setDApell(String newApell) {
		this.d_apell = newApell;
	}

	public String getDNom() {
		return this.d_nom;
	}

	public void setDNom(String newNom) {
		this.d_nom = newNom;
	}

	public String getAPassword() {
		return this.a_password;
	}

	public void setAPassword(String valor) {
		this.a_password = valor;
	}

	public String getCDni() {
		return this.c_dni;
	}

	public void setCDni(String valor) {
		this.c_dni = valor;
	}

	public String getCNif() {
		return this.c_nif;
	}

	public void setCNif(String valor) {
		this.c_nif = valor;
	}

	public Date getFUltimaConexion() {
		return this.f_ultima_conexion;
	}

	public void setFUltimaConexion(Date valor) {
		this.f_ultima_conexion = valor;
	}

	public HashMap getExtensiones() {
		return this.extensiones;
	}

	public void setExtensiones(HashMap valor) {
		this.extensiones = valor;
	}

	public void setAmpliaciones(HashMap ampliaciones) {
		this.ampliaciones = ampliaciones;
	}

	public HashMap getAmpliaciones() {
		return this.ampliaciones;
	}

	public void setD_correo(String d_correo) {
		this.d_correo = d_correo;
	}

	public String getD_correo() {
		return this.d_correo;
	}

	public void setD_telefono(String d_telefono) {
		this.d_telefono = d_telefono;
	}

	public String getD_telefono() {
		return this.d_telefono;
	}

	public void setD_movil(String d_movil) {
		this.d_movil = d_movil;
	}

	public String getD_movil() {
		return this.d_movil;
	}

	public void setD_gerencia(String d_gerencia) {
		this.d_gerencia = d_gerencia;
	}

	public String getD_gerencia() {
		return this.d_gerencia;
	}

	/**
	 * @return the listaPerfiles
	 */
	public ArrayList<String> getListaPerfiles() {
		return listaPerfiles;
	}

	/**
	 * @param listaPerfiles the listaPerfiles to set
	 */
	public void setListaPerfiles(ArrayList<String> listaPerfiles) {
		this.listaPerfiles = listaPerfiles;
	}
}
