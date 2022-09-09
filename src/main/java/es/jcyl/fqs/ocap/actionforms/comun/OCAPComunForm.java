package es.jcyl.fqs.ocap.actionforms.comun;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class OCAPComunForm extends ActionForm {

	private static final long serialVersionUID = -1039734354039059662L;
	private String c_UsuarioId;
	private String c_RolId;
	private boolean jspInicio;
	private String jspFuncion;
	private String jspNumRegistros;
	private String jspForward;

	public void setc_UsuarioId(String cUsuarioId) {
		this.c_UsuarioId = cUsuarioId;
	}

	public void setc_RolId(String cRolId) {
		this.c_RolId = cRolId;
	}

	public void setjspInicio(boolean pInicio) {
		this.jspInicio = pInicio;
	}

	public void setjspFuncion(String pFuncion) {
		this.jspFuncion = pFuncion;
	}

	public void setjspNumRegistros(String jspNumRegistros) {
		this.jspNumRegistros = jspNumRegistros;
	}

	public void setjspForward(String jspForward) {
		this.jspForward = jspForward;
	}

	public String getc_UsuarioId() {
		return this.c_UsuarioId;
	}

	public String getc_RolId() {
		return this.c_RolId;
	}

	public boolean getjspInicio() {
		return this.jspInicio;
	}

	public String getjspFuncion() {
		return this.jspFuncion;
	}

	public String getjspNumRegistros() {
		return this.jspNumRegistros;
	}

	public String getjspForward() {
		return this.jspForward;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.c_UsuarioId = null;
		this.c_RolId = null;
		this.jspInicio = false;
		this.jspFuncion = null;
		this.jspNumRegistros = null;
		this.jspForward = null;
	}
}
