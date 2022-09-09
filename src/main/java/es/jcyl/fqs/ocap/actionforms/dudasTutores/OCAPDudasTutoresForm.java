package es.jcyl.fqs.ocap.actionforms.dudasTutores;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.Utilidades;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

public class OCAPDudasTutoresForm extends OCAPComunForm {

	private static final long serialVersionUID = 7081206984908583656L;
	private long cTutorId;
	private String dNombreTutor;
	private String dApellidosTutor;
	private String dCorreoElectronicoTutor;
	private String bActivado;
	private String cTipoTutor;
	private String cTipoDuda;
	private long nDudasRecibidas;
	private long nDudasContestadas;
	private long nControl;
	private String cDni;
	private long cDudaId;
	private long cExpId;
	private long cTemaId;
	private String codigoEPR;
	private String dCorreoElectronico;
	private String dDuda;
	private String dRespuesta;
	private String bContestado;
	private String bLeido;
	private String fRecepcion;
	private String fEnvioContestacion;
	private String bCambioTipo;
	private String fInicio;
	private String fFin;
	private String fInicioRespuesta;
	private String fFinRespuesta;
	private int nHorasRespuesta;
	private int nMinutosRespuesta;
	private ArrayList listaTemas;
	private String dTema;
	private long cGerenciaId;
	private long cProfesionalId;
	private long cEspecialidadId;
	private boolean jspInicio;

	public void setCTutorId(long cTutorId) {
		this.cTutorId = cTutorId;
	}

	public long getCTutorId() {
		return this.cTutorId;
	}

	public void setDNombreTutor(String dNombreTutor) {
		this.dNombreTutor = dNombreTutor;
	}

	public String getDNombreTutor() {
		return this.dNombreTutor;
	}

	public void setDApellidosTutor(String dApellidosTutor) {
		this.dApellidosTutor = dApellidosTutor;
	}

	public String getDApellidosTutor() {
		return this.dApellidosTutor;
	}

	public void setDCorreoElectronicoTutor(String dCorreoElectronicoTutor) {
		this.dCorreoElectronicoTutor = dCorreoElectronicoTutor;
	}

	public String getDCorreoElectronicoTutor() {
		return this.dCorreoElectronicoTutor;
	}

	public void setBActivado(String bActivado) {
		this.bActivado = bActivado;
	}

	public String getBActivado() {
		return this.bActivado;
	}

	public void setCTipoTutor(String cTipoTutor) {
		this.cTipoTutor = cTipoTutor;
	}

	public String getCTipoTutor() {
		return this.cTipoTutor;
	}

	public void setCTipoDuda(String cTipoDuda) {
		this.cTipoDuda = cTipoDuda;
	}

	public String getCTipoDuda() {
		return this.cTipoDuda;
	}

	public void setNDudasRecibidas(long nDudasRecibidas) {
		this.nDudasRecibidas = nDudasRecibidas;
	}

	public long getNDudasRecibidas() {
		return this.nDudasRecibidas;
	}

	public void setNDudasContestadas(long nDudasContestadas) {
		this.nDudasContestadas = nDudasContestadas;
	}

	public long getNDudasContestadas() {
		return this.nDudasContestadas;
	}

	public void setNControl(long nControl) {
		this.nControl = nControl;
	}

	public long getNControl() {
		return this.nControl;
	}

	public void setCDudaId(long cDudaId) {
		this.cDudaId = cDudaId;
	}

	public long getCDudaId() {
		return this.cDudaId;
	}

	public void setCExpId(long cExpId) {
		this.cExpId = cExpId;
	}

	public long getCExpId() {
		return this.cExpId;
	}

	public void setDCorreoElectronico(String dCorreoElectronico) {
		this.dCorreoElectronico = dCorreoElectronico;
	}

	public String getDCorreoElectronico() {
		return this.dCorreoElectronico;
	}

	public void setDDuda(String dDuda) {
		this.dDuda = dDuda;
	}

	public String getDDuda() {
		return this.dDuda;
	}

	public void setDRespuesta(String dRespuesta) {
		this.dRespuesta = dRespuesta;
	}

	public String getDRespuesta() {
		return this.dRespuesta;
	}

	public void setBContestado(String bContestado) {
		this.bContestado = bContestado;
	}

	public String getBContestado() {
		return this.bContestado;
	}

	public void setBLeido(String bLeido) {
		this.bLeido = bLeido;
	}

	public String getBLeido() {
		return this.bLeido;
	}

	public void setFRecepcion(String fRecepcion) {
		this.fRecepcion = fRecepcion;
	}

	public String getFRecepcion() {
		return this.fRecepcion;
	}

	public void setFEnvioContestacion(String fEnvioContestacion) {
		this.fEnvioContestacion = fEnvioContestacion;
	}

	public String getFEnvioContestacion() {
		return this.fEnvioContestacion;
	}

	public void setJspInicio(boolean jspInicio) {
		this.jspInicio = jspInicio;
	}

	public boolean getJspInicio() {
		return this.jspInicio;
	}

	public void setCodigoEPR(String codigoEPR) {
		this.codigoEPR = codigoEPR;
	}

	public String getCodigoEPR() {
		return this.codigoEPR;
	}

	public void setListaTemas(ArrayList listaTemas) {
		this.listaTemas = listaTemas;
	}

	public ArrayList getListaTemas() {
		return this.listaTemas;
	}

	public void setCTemaId(long cTemaId) {
		this.cTemaId = cTemaId;
	}

	public long getCTemaId() {
		return this.cTemaId;
	}

	public void setCDni(String cDni) {
		this.cDni = cDni;
	}

	public String getCDni() {
		return this.cDni;
	}

	public void setBCambioTipo(String bCambioTipo) {
		this.bCambioTipo = bCambioTipo;
	}

	public String getBCambioTipo() {
		return this.bCambioTipo;
	}

	public void setFInicio(String fInicio) {
		this.fInicio = fInicio;
	}

	public String getFInicio() {
		return this.fInicio;
	}

	public void setFFin(String fFin) {
		this.fFin = fFin;
	}

	public String getFFin() {
		return this.fFin;
	}

	public void setDTema(String dTema) {
		this.dTema = dTema;
	}

	public String getDTema() {
		return this.dTema;
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

	public void setCEspecialidadId(long cEspecialidadId) {
		this.cEspecialidadId = cEspecialidadId;
	}

	public long getCEspecialidadId() {
		return this.cEspecialidadId;
	}

	public void setFInicioRespuesta(String fInicioRespuesta) {
		this.fInicioRespuesta = fInicioRespuesta;
	}

	public String getFInicioRespuesta() {
		return this.fInicioRespuesta;
	}

	public void setFFinRespuesta(String fFinRespuesta) {
		this.fFinRespuesta = fFinRespuesta;
	}

	public String getFFinRespuesta() {
		return this.fFinRespuesta;
	}

	public void setNHorasRespuesta(int nHorasRespuesta) {
		this.nHorasRespuesta = nHorasRespuesta;
	}

	public int getNHorasRespuesta() {
		return this.nHorasRespuesta;
	}

	public void setNMinutosRespuesta(int nMinutosRespuesta) {
		this.nMinutosRespuesta = nMinutosRespuesta;
	}

	public int getNMinutosRespuesta() {
		return this.nMinutosRespuesta;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errores = super.validate(mapping, request);
		if (errores == null) {
			errores = new ActionErrors();
		}
		ActionErrors erroresAction = new ActionErrors();
		String auxFInicio = "";
		String auxFFin = "";
		String auxFInicioRespuesta = "";
		String auxFFinRespuesta = "";

		String nombreMetodoAction = request.getParameter(mapping.getParameter());

		if ((nombreMetodoAction != null)
				&& ((nombreMetodoAction.equals("insertarTutor")) || (nombreMetodoAction.equals("modificarTutor")))) {
			if (GenericValidator.isBlankOrNull(this.dNombreTutor)) {
				erroresAction.add("dNombreTutor", new ActionError("error.obligatorio.generico"));
			}
			if (GenericValidator.isBlankOrNull(this.dApellidosTutor)) {
				erroresAction.add("dApellidosTutor", new ActionError("error.obligatorio.generico"));
			}
			if (GenericValidator.isBlankOrNull(this.cDni))
				erroresAction.add("cDni", new ActionError("error.obligatorio.generico"));
			else if (!Utilidades.esDNI(this.cDni)) {
				erroresAction.add("cDni", new ActionError("error.maskmsg", "NIF/NIE"));
			}
			if (GenericValidator.isBlankOrNull(this.dCorreoElectronicoTutor))
				erroresAction.add("dCorreoElectronicoTutor", new ActionError("error.obligatorio.generico"));
			else if (!new Utilidades().esEmail(this.dCorreoElectronicoTutor)) {
				erroresAction.add("dCorreoElectronicoTutor", new ActionError("error.maskmsg", "Correo electrónico"));
			}
			if (GenericValidator.isBlankOrNull(this.cTipoTutor)) {
				erroresAction.add("cTipoTutor", new ActionError("error.obligatorio.generico"));
			}
			if ((nombreMetodoAction.equals("insertarTutor")) && (GenericValidator.isBlankOrNull(this.cTipoDuda)))
				erroresAction.add("cTipoDuda", new ActionError("error.obligatorio.generico"));
		} else if ((nombreMetodoAction != null) && (nombreMetodoAction.equals("insertarDuda"))) {
			if (GenericValidator.isBlankOrNull(this.cTipoDuda))
				erroresAction.add("cTipoDuda", new ActionError("error.obligatorio.generico"));
			if (this.cTemaId == 0L)
				erroresAction.add("cTemaId", new ActionError("error.obligatorio.generico"));
			if (GenericValidator.isBlankOrNull(this.dCorreoElectronico))
				erroresAction.add("dCorreoElectronico", new ActionError("error.obligatorio.generico"));
			else if (!new Utilidades().esEmail(this.dCorreoElectronico))
				erroresAction.add("dCorreoElectronico", new ActionError("error.formato.generico"));
			if (GenericValidator.isBlankOrNull(this.dDuda))
				erroresAction.add("dDuda", new ActionError("error.obligatorio.generico"));
		} else if ((nombreMetodoAction != null) && ((nombreMetodoAction.equals("buscarDudasTutores"))
				|| (nombreMetodoAction.equals("buscarDudasUsuario")) || (nombreMetodoAction.equals("generarCSVDudas"))
				|| (nombreMetodoAction.equals("buscarDudas")))) {
			if ((this.codigoEPR != null) && (!"".equals(this.codigoEPR))) {
				if (this.codigoEPR.toUpperCase().indexOf("EPR") != 0) {
					erroresAction.add("codigoEPR", new ActionError("errors.formaEpr"));
				} else {
					String auxCodigo = this.codigoEPR.toUpperCase().substring("EPR".length());
					if (!GenericValidator.isLong(auxCodigo)) {
						erroresAction.add("codigoEPR", new ActionError("errors.formaEpr"));
					}
				}
			}
			if (!GenericValidator.isBlankOrNull(this.fInicio)) {
				this.fInicio = this.fInicio.trim();
				auxFInicio = this.fInicio;

				if (this.fInicio.length() <= 10) {
					this.fInicio += " 00:00:00";
				} else if (this.fInicio.length() == 16)
					this.fInicio += ":00";
				if (!GenericValidator.isDate(this.fInicio, Constantes.FECHA_COMPLETA_DATEUTIL, true))
					erroresAction.add("fInicio", new ActionError("error.formatoFecha", "Inicio intervalo"));
			}
			if (!GenericValidator.isBlankOrNull(this.fFin)) {
				this.fFin = this.fFin.trim();
				auxFFin = this.fFin;

				if (this.fFin.length() <= 10) {
					this.fFin += " 23:59:59";
				} else if (this.fFin.length() == 16)
					this.fFin += ":59";
				if (!GenericValidator.isDate(this.fFin, Constantes.FECHA_COMPLETA_DATEUTIL, true))
					erroresAction.add("fFin", new ActionError("error.formatoFecha", "Fin intervalo"));
			}
			if ((!GenericValidator.isBlankOrNull(this.fInicio)) && (!GenericValidator.isBlankOrNull(this.fFin))
					&& (GenericValidator.isDate(this.fInicio, Constantes.FECHA_COMPLETA_DATEUTIL, true))
					&& (GenericValidator.isDate(this.fFin, Constantes.FECHA_COMPLETA_DATEUTIL, true))) {
				try {
					if (DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL, this.fInicio)
							.after(DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL, this.fFin)))
						erroresAction.add("fInicio",
								new ActionError("error.fechaAnterior", "Fin intervalo", "Inicio intervalo"));
				} catch (Exception e) {
				}
			}
			if (!GenericValidator.isBlankOrNull(this.fInicioRespuesta)) {
				this.fInicioRespuesta = this.fInicioRespuesta.trim();
				auxFInicioRespuesta = this.fInicioRespuesta;

				if (this.fInicioRespuesta.length() <= 10) {
					this.fInicioRespuesta += " 00:00:00";
				} else if (this.fInicioRespuesta.length() == 16)
					this.fInicioRespuesta += ":00";
				if (!GenericValidator.isDate(this.fInicioRespuesta, Constantes.FECHA_COMPLETA_DATEUTIL, true))
					erroresAction.add("fInicioRespuesta", new ActionError("error.formatoFecha", "Inicio intervalo"));
			}
			if (!GenericValidator.isBlankOrNull(this.fFinRespuesta)) {
				this.fFinRespuesta = this.fFinRespuesta.trim();
				auxFFinRespuesta = this.fFinRespuesta;

				if (this.fFinRespuesta.length() <= 10) {
					this.fFinRespuesta += " 23:59:59";
				} else if (this.fFinRespuesta.length() == 16)
					this.fFinRespuesta += ":59";
				if (!GenericValidator.isDate(this.fFinRespuesta, Constantes.FECHA_COMPLETA_DATEUTIL, true))
					erroresAction.add("fFinRespuesta", new ActionError("error.formatoFecha", "Fin intervalo"));
			}
			if ((!GenericValidator.isBlankOrNull(this.fInicioRespuesta))
					&& (!GenericValidator.isBlankOrNull(this.fFinRespuesta))
					&& (GenericValidator.isDate(this.fInicioRespuesta, Constantes.FECHA_COMPLETA_DATEUTIL, true))
					&& (GenericValidator.isDate(this.fFinRespuesta, Constantes.FECHA_COMPLETA_DATEUTIL, true))) {
				try {
					if (DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL, this.fInicioRespuesta)
							.after(DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL, this.fFinRespuesta)))
						erroresAction.add("fInicioRespuesta",
								new ActionError("error.fechaAnterior", "Fin intervalo", "Inicio intervalo"));
				} catch (Exception e) {
				}
			}
			if (erroresAction.size() != 0) {
				this.fInicio = auxFInicio;
				this.fFin = auxFFin;
				this.fInicioRespuesta = auxFInicioRespuesta;
				this.fFinRespuesta = auxFFinRespuesta;
			}
		} else if ((nombreMetodoAction != null) && (nombreMetodoAction.equals("contestarDuda"))
				&& (GenericValidator.isBlankOrNull(this.dRespuesta))) {
			erroresAction.add("dRespuesta", new ActionError("error.obligatorio.generico"));
		}

		if (erroresAction.size() != 0) {
			request.setAttribute("erroresAction", erroresAction);
		}

		return errores;
	}
}
