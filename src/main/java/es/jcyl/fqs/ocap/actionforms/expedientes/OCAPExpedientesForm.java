package es.jcyl.fqs.ocap.actionforms.expedientes;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class OCAPExpedientesForm extends ActionForm {

	private static final long serialVersionUID = 6617467725583426303L;
	private String dNombre;
	private String dApellidos;
	private String cDNI;
	private String cDNIReal;
	private long cGerenciaId;
	private String dGerencia;
	private long cEstadoId;
	private String dEstado;
	private long cGradoId;
	private String dGrado;
	private long cCategProfesionalId;
	private String dCategProfesional;
	private long cConvocatoriaId;
	private String dConvocatoria;
	private long[] listaExpedientes;
	private String bMCUsuario;
	private String codigoId;

	public String getDNombre() {
		return this.dNombre;
	}

	public void setDNombre(String dNombre) {
		this.dNombre = dNombre;
	}

	public String getDApellidos() {
		return this.dApellidos;
	}

	public void setDApellidos(String dApellidos) {
		this.dApellidos = dApellidos;
	}

	public String getCDNI() {
		return this.cDNI;
	}

	public void setCDNI(String cDNI) {
		this.cDNI = cDNI;
	}

	public String getCDNIReal() {
		return this.cDNIReal;
	}

	public void setCDNIReal(String cDNIReal) {
		this.cDNIReal = cDNIReal;
	}

	public long getCGerenciaId() {
		return this.cGerenciaId;
	}

	public void setCGerenciaId(long cGerenciaId) {
		this.cGerenciaId = cGerenciaId;
	}

	public String getDGerencia() {
		return this.dGerencia;
	}

	public void setDGerencia(String dGerencia) {
		this.dGerencia = dGerencia;
	}

	public long getCGradoId() {
		return this.cGradoId;
	}

	public void setCGradoId(long cGradoId) {
		this.cGradoId = cGradoId;
	}

	public String getDGrado() {
		return this.dGrado;
	}

	public void setDGrado(String dGrado) {
		this.dGrado = dGrado;
	}

	public long getCCategProfesionalId() {
		return this.cCategProfesionalId;
	}

	public void setCCategProfesionalId(long cCategProfesionalId) {
		this.cCategProfesionalId = cCategProfesionalId;
	}

	public String getDCategProfesional() {
		return this.dCategProfesional;
	}

	public void setDCategProfesional(String dCategProfesional) {
		this.dCategProfesional = dCategProfesional;
	}

	public long getCConvocatoriaId() {
		return this.cConvocatoriaId;
	}

	public void setCConvocatoriaId(long cConvocatoriaId) {
		this.cConvocatoriaId = cConvocatoriaId;
	}

	public String getDConvocatoria() {
		return this.dConvocatoria;
	}

	public void setDConvocatoria(String dConvocatoria) {
		this.dConvocatoria = dConvocatoria;
	}

	public long[] getListaExpedientes() {
		return this.listaExpedientes;
	}

	public void setListaExpedientes(long[] listaExpedientes) {
		this.listaExpedientes = listaExpedientes;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.listaExpedientes = new long[0];
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errores = super.validate(mapping, request);
		if (errores == null) {
			errores = new ActionErrors();
		}
		ActionErrors erroresAction = new ActionErrors();

		String nombreMetodoAction = request.getParameter(mapping.getParameter());

		if ((nombreMetodoAction == null) || (!nombreMetodoAction.equals("listarExpedientes"))) {
			if ((nombreMetodoAction != null)
					&& ((nombreMetodoAction.equals("generarReportPDFExpedientesMeritosCurriculares"))
							|| (nombreMetodoAction.equals("generarReportPDFInfMotivado")))) {
				if ((this.listaExpedientes != null) && (this.listaExpedientes.length < 1))
					erroresAction.add("erroresListado",
							new ActionError("error.checkbox.min.generico", "un", "profesional"));
				else if ((this.listaExpedientes != null) && (this.listaExpedientes.length > 50))
					erroresAction.add("erroresListado",
							new ActionError("error.checkbox.max.generico", "50", "profesionales"));
			} else if ((nombreMetodoAction != null)
					&& (nombreMetodoAction.equals("generarReportPDFExpedientesEtiquetas"))) {
				if ((this.listaExpedientes != null) && (this.listaExpedientes.length < 1))
					erroresAction.add("erroresListado",
							new ActionError("error.checkbox.min.generico", "un", "profesional"));
				else if ((this.listaExpedientes != null) && (this.listaExpedientes.length > 100)) {
					erroresAction.add("erroresListado",
							new ActionError("error.checkbox.max.generico", "100", "profesionales"));
				}
			}
		}
		if (erroresAction.size() != 0) {
			request.setAttribute("erroresAction", erroresAction);
		}

		return errores;
	}

	public void setCEstadoId(long cEstadoId) {
		this.cEstadoId = cEstadoId;
	}

	public long getCEstadoId() {
		return this.cEstadoId;
	}

	public void setDEstado(String dEstado) {
		this.dEstado = dEstado;
	}

	public String getDEstado() {
		return this.dEstado;
	}

	public void set_cEstadoId(long cEstadoId) {
		this.cEstadoId = cEstadoId;
	}

	public long get_cEstadoId() {
		return this.cEstadoId;
	}

	public void set_dEstado(String dEstado) {
		this.dEstado = dEstado;
	}

	public String get_dEstado() {
		return this.dEstado;
	}

	public void setBMCUsuario(String bMCUsuario) {
		this.bMCUsuario = bMCUsuario;
	}

	public String getBMCUsuario() {
		return this.bMCUsuario;
	}

	public void setCodigoId(String codigoId) {
		this.codigoId = codigoId;
	}

	public String getCodigoId() {
		return this.codigoId;
	}
}
