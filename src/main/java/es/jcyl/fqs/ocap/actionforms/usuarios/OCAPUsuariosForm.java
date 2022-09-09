package es.jcyl.fqs.ocap.actionforms.usuarios;

import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class OCAPUsuariosForm extends ActionForm {

	private static final long serialVersionUID = -3916586604983714112L;
	private String cUsr_id;
	private String dApellido1;
	private String dNombre;
	private String cDni;
	private String letraUID;
	private String cDniReal;
	private String dCorreoelectronico;
	private String nTelefono1;
	private String nTelefono2;
	private String cProfesional_id;
	private String cEspec_id;
	private String cProvincia_id;
	private String dProvincia;
	private String dProvinciaCarrera;
	private String cGerencia_id;
	private String cCentrotrabajo_id;
	private Integer cEstatutId;
	private String cPerfil_id;
	private String fPlazapropiedad;
	private String bSexo;
	private String aDomicilio;
	private String cPostalUsu;
	private String dLocalidad;
	private String dServicio;
	private String dPuesto;
	private String dEstatutActual_nombre;
	private String cExp_id;
	private String fInicio_mc;
	private String fFin_mc;
	private Integer nRes_mc;
	private String fInicio_eval_mc;
	private String fFin_eval_mc;
	private String fInicio_ca;
	private String fFin_ca;
	private Integer nRes_ca;
	private String fInicio_cc;
	private String fFin_cc;
	private String aPuesto;
	private String cConvocatoriaId;
	private String dConvocatoriaNombre;
	private String cGrado_id;
	private String dSitAdministrativa_nombre;
	private String cJuridico;
	private String cJuridicoCombo;
	private String cSitAdministrativaAuxId;
	private String dSitAdministrativaAuxOtros;
	private String dRegimenJuridicoOtros;
	private String dModAnterior;
	private String dProcedimiento;
	private String cProcedimientoId;
	private String nAniosantiguedad;
	private String nMesesantiguedad;
	private String nDiasantiguedad;
	private String pestanaSeleccionada;
	private ArrayList listaCreditos;
	private ArrayList listaMeritos;
	private ArrayList listaMeritosOpcionales;
	private Integer nCreditosFormacion;
	private Integer nCreditosDocencia;
	private Integer nCreditosInvestigacion;
	private Integer nCreditosGestionCli;
	private Integer nCreditosOpcionales;
	private String cCertificado_id;
	private String dTitulo;
	private String dDesc;
	private String dGrado_des;
	private String dEstatutario_nombre;
	private String dEstatutarioActual_nombre;
	private String dProfesional_nombre;
	private String dProfesionalActual_nombre;
	private String dEspec_nombre;
	private String dEspecActual_nombre;
	private String dCentrotrabajo_nombre;
	private String aCiudad;
	private String cTipogerencia_id;
	private String dTipogerencia_desc;
	private String dGerencia_nombre;
	private Long cExpId;
	private long cExpmc_id;
	private String accionEnviar;
	private boolean jspInicio;
	private ArrayList listadoCategorias;
	private ArrayList listadoEspecialidades;
	private ArrayList listadoProvincias;
	private ArrayList listadoGerencias;
	private ArrayList listadoCentrosTrabajo;
	private String rSolicitud_acceso;
	private String rConstancia_antiguedad;
	private String rConstancia_categoria;
	private String rFase_inicio;
	private String rContinuidad_proceso;
	private String rFase_tramitacion;
	private String rContinuidad_proceso_tramitacion;
	private String rFase_validacion;
	private String rContinuidad_proceso_validacion;
	private String dMensaje;
	private String dMensajeInforme;
	private String cDniReal_Busqueda;
	private String cTipo;
	private String cPersonalId;
	private String enPeriodoValMc = Constantes.NO;
	private String enPeriodoValCc = Constantes.NO;
	
	private String dAnioConvocatoria;
	private boolean bVerCeisReport = false;
	private boolean bVerCCReport = false;


	public String getCUsr_id() {
		return this.cUsr_id;
	}

	public String getDApellido1() {
		return this.dApellido1;
	}

	public String getDNombre() {
		return this.dNombre;
	}

	public String getCDni() {
		return this.cDni;
	}

	public String getLetraUID() {
		return this.letraUID;
	}

	public String getDCorreoelectronico() {
		return this.dCorreoelectronico;
	}

	public String getCCentrotrabajo_id() {
		return this.cCentrotrabajo_id;
	}

	public String getCEspec_id() {
		return this.cEspec_id;
	}

	public String getCPerfil_id() {
		return this.cPerfil_id;
	}

	public String getCProfesional_id() {
		return this.cProfesional_id;
	}

	public String getCGerencia_id() {
		return this.cGerencia_id;
	}

	public String getFPlazapropiedad() {
		return this.fPlazapropiedad;
	}

	public String getCExp_id() {
		return this.cExp_id;
	}

	public String getFInicio_mc() {
		return this.fInicio_mc;
	}

	public String getFFin_mc() {
		return this.fFin_mc;
	}

	public Integer getNRes_mc() {
		return this.nRes_mc;
	}

	public String getFInicio_eval_mc() {
		return this.fInicio_eval_mc;
	}

	public String getFFin_eval_mc() {
		return this.fFin_eval_mc;
	}

	public String getFInicio_ca() {
		return this.fInicio_ca;
	}

	public String getFFin_ca() {
		return this.fFin_ca;
	}

	public Integer getNRes_ca() {
		return this.nRes_ca;
	}

	public String getFInicio_cc() {
		return this.fInicio_cc;
	}

	public String getFFin_cc() {
		return this.fFin_cc;
	}

	public String getCGrado_id() {
		return this.cGrado_id;
	}

	public String getCProvincia_id() {
		return this.cProvincia_id;
	}

	public String getDGrado_des() {
		return this.dGrado_des;
	}

	public String getDProfesional_nombre() {
		return this.dProfesional_nombre;
	}

	public String getDEspec_nombre() {
		return this.dEspec_nombre;
	}

	public String getCTipogerencia_id() {
		return this.cTipogerencia_id;
	}

	public String getDProvincia() {
		return this.dProvincia;
	}

	public String getDCentrotrabajo_nombre() {
		return this.dCentrotrabajo_nombre;
	}

	public String getDTipogerencia_desc() {
		return this.dTipogerencia_desc;
	}

	public String getDGerencia_nombre() {
		return this.dGerencia_nombre;
	}

	public String getAccionEnviar() {
		return this.accionEnviar;
	}

	public String getRSolicitud_acceso() {
		return this.rSolicitud_acceso;
	}

	public String getRConstancia_antiguedad() {
		return this.rConstancia_antiguedad;
	}

	public String getRConstancia_categoria() {
		return this.rConstancia_categoria;
	}

	public String getRFase_inicio() {
		return this.rFase_inicio;
	}

	public String getRContinuidad_proceso() {
		return this.rContinuidad_proceso;
	}

	public String getRFase_tramitacion() {
		return this.rFase_tramitacion;
	}

	public String getRContinuidad_proceso_tramitacion() {
		return this.rContinuidad_proceso_tramitacion;
	}

	public String getRFase_validacion() {
		return this.rFase_validacion;
	}

	public String getRContinuidad_proceso_validacion() {
		return this.rContinuidad_proceso_validacion;
	}

	public boolean getjspInicio() {
		return this.jspInicio;
	}

	public long getCExpmc_id() {
		return this.cExpmc_id;
	}

	public String getCCertificado_id() {
		return this.cCertificado_id;
	}

	public String getDTitulo() {
		return this.dTitulo;
	}

	public String getDDesc() {
		return this.dDesc;
	}

	public ArrayList getListadoCategorias() {
		return this.listadoCategorias;
	}

	public ArrayList getListadoEspecialidades() {
		return this.listadoEspecialidades;
	}

	public ArrayList getListadoProvincias() {
		return this.listadoProvincias;
	}

	public ArrayList getListadoGerencias() {
		return this.listadoGerencias;
	}

	public ArrayList getListadoCentrosTrabajo() {
		return this.listadoCentrosTrabajo;
	}

	public void setCUsr_id(String cUsr_id) {
		this.cUsr_id = cUsr_id;
	}

	public void setDApellido1(String dApellido1) {
		this.dApellido1 = dApellido1;
	}

	public void setDNombre(String dNombre) {
		this.dNombre = dNombre;
	}

	public void setCDni(String cDni) {
		this.cDni = cDni;
	}

	public void setLetraUID(String letraUID) {
		this.letraUID = letraUID;
	}

	public void setDCorreoelectronico(String dCorreoelectronico) {
		this.dCorreoelectronico = dCorreoelectronico;
	}

	public void setCCentrotrabajo_id(String cCentrotrabajo_id) {
		this.cCentrotrabajo_id = cCentrotrabajo_id;
	}

	public void setCEspec_id(String cEspec_id) {
		this.cEspec_id = cEspec_id;
	}

	public void setCPerfil_id(String cPerfil_id) {
		this.cPerfil_id = cPerfil_id;
	}

	public void setCProfesional_id(String cProfesional_id) {
		this.cProfesional_id = cProfesional_id;
	}

	public void setCGerencia_id(String cGerencia_id) {
		this.cGerencia_id = cGerencia_id;
	}

	public void setFPlazapropiedad(String fPlazapropiedad) {
		this.fPlazapropiedad = fPlazapropiedad;
	}

	public void setCExp_id(String cExp_id) {
		this.cExp_id = cExp_id;
	}

	public void setFInicio_mc(String fInicio_mc) {
		this.fInicio_mc = fInicio_mc;
	}

	public void setFFin_mc(String fFin_mc) {
		this.fFin_mc = fFin_mc;
	}

	public void setNRes_mc(Integer nRes_mc) {
		this.nRes_mc = nRes_mc;
	}

	public void setFInicio_eval_mc(String fInicio_eval_mc) {
		this.fInicio_eval_mc = fInicio_eval_mc;
	}

	public void setFFin_eval_mc(String fFin_eval_mc) {
		this.fFin_eval_mc = fFin_eval_mc;
	}

	public void setFInicio_ca(String fInicio_ca) {
		this.fInicio_ca = fInicio_ca;
	}

	public void setFFin_ca(String fFin_ca) {
		this.fFin_ca = fFin_ca;
	}

	public void setNRes_ca(Integer nRes_ca) {
		this.nRes_ca = nRes_ca;
	}

	public void setFInicio_cc(String fInicio_cc) {
		this.fInicio_cc = fInicio_cc;
	}

	public void setFFin_cc(String fFin_cc) {
		this.fFin_cc = fFin_cc;
	}

	public void setCGrado_id(String cGrado_id) {
		this.cGrado_id = cGrado_id;
	}

	public void setCProvincia_id(String cProvincia_id) {
		this.cProvincia_id = cProvincia_id;
	}

	public void setDGrado_des(String dGrado_des) {
		this.dGrado_des = dGrado_des;
	}

	public void setDProfesional_nombre(String dProfesional_nombre) {
		this.dProfesional_nombre = dProfesional_nombre;
	}

	public void setDEspec_nombre(String dEspec_nombre) {
		this.dEspec_nombre = dEspec_nombre;
	}

	public void setCTipogerencia_id(String cTipogerencia_id) {
		this.cTipogerencia_id = cTipogerencia_id;
	}

	public void setDProvincia(String dProvincia) {
		this.dProvincia = dProvincia;
	}

	public void setDCentrotrabajo_nombre(String dCentrotrabajo_nombre) {
		this.dCentrotrabajo_nombre = dCentrotrabajo_nombre;
	}

	public void setDTipogerencia_desc(String dTipogerencia_desc) {
		this.dTipogerencia_desc = dTipogerencia_desc;
	}

	public void setDGerencia_nombre(String dGerencia_nombre) {
		this.dGerencia_nombre = dGerencia_nombre;
	}

	public void setAccionEnviar(String accionEnviar) {
		this.accionEnviar = accionEnviar;
	}

	public void setjspInicio(boolean pInicio) {
		this.jspInicio = pInicio;
	}

	public void setCExpmc_id(long cExpmc_id) {
		this.cExpmc_id = cExpmc_id;
	}

	public void setCCertificado_id(String cCertificado_id) {
		this.cCertificado_id = cCertificado_id;
	}

	public void setDTitulo(String dTitulo) {
		this.dTitulo = dTitulo;
	}

	public void setDDesc(String dDesc) {
		this.dDesc = dDesc;
	}

	public void setListadoCategorias(ArrayList listadoCategorias) {
		this.listadoCategorias = listadoCategorias;
	}

	public void setListadoEspecialidades(ArrayList listadoEspecialidades) {
		this.listadoEspecialidades = listadoEspecialidades;
	}

	public void setListadoProvincias(ArrayList listadoProvincias) {
		this.listadoProvincias = listadoProvincias;
	}

	public void setListadoGerencias(ArrayList listadoGerencias) {
		this.listadoGerencias = listadoGerencias;
	}

	public void setListadoCentrosTrabajo(ArrayList listadoCentrosTrabajo) {
		this.listadoCentrosTrabajo = listadoCentrosTrabajo;
	}

	public void setRSolicitud_acceso(String rSolicitud_acceso) {
		this.rSolicitud_acceso = rSolicitud_acceso;
	}

	public void setRConstancia_antiguedad(String rConstancia_antiguedad) {
		this.rConstancia_antiguedad = rConstancia_antiguedad;
	}

	public void setRConstancia_categoria(String rConstancia_categoria) {
		this.rConstancia_categoria = rConstancia_categoria;
	}

	public void setRFase_inicio(String rFase_inicio) {
		this.rFase_inicio = rFase_inicio;
	}

	public void setRContinuidad_proceso(String rContinuidad_proceso) {
		this.rContinuidad_proceso = rContinuidad_proceso;
	}

	public void setRFase_tramitacion(String rFase_tramitacion) {
		this.rFase_tramitacion = rFase_tramitacion;
	}

	public void setRContinuidad_proceso_tramitacion(String rContinuidad_proceso_tramitacion) {
		this.rContinuidad_proceso_tramitacion = rContinuidad_proceso_tramitacion;
	}

	public void setRFase_validacion(String rFase_validacion) {
		this.rFase_validacion = rFase_validacion;
	}

	public void setRContinuidad_proceso_validacion(String rContinuidad_proceso_validacion) {
		this.rContinuidad_proceso_validacion = rContinuidad_proceso_validacion;
	}

	public void setNTelefono1(String nTelefono1) {
		this.nTelefono1 = nTelefono1;
	}

	public String getNTelefono1() {
		return this.nTelefono1;
	}

	public void setNCreditosFormacion(Integer nCreditosFormacion) {
		this.nCreditosFormacion = nCreditosFormacion;
	}

	public Integer getNCreditosFormacion() {
		return this.nCreditosFormacion;
	}

	public void setNCreditosDocencia(Integer nCreditosDocencia) {
		this.nCreditosDocencia = nCreditosDocencia;
	}

	public Integer getNCreditosDocencia() {
		return this.nCreditosDocencia;
	}

	public void setNCreditosInvestigacion(Integer nCreditosInvestigacion) {
		this.nCreditosInvestigacion = nCreditosInvestigacion;
	}

	public Integer getNCreditosInvestigacion() {
		return this.nCreditosInvestigacion;
	}

	public void setNCreditosGestionCli(Integer nCreditosGestionCli) {
		this.nCreditosGestionCli = nCreditosGestionCli;
	}

	public Integer getNCreditosGestionCli() {
		return this.nCreditosGestionCli;
	}

	public void setNCreditosOpcionales(Integer nCreditosOpcionales) {
		this.nCreditosOpcionales = nCreditosOpcionales;
	}

	public Integer getNCreditosOpcionales() {
		return this.nCreditosOpcionales;
	}

	public void setListaMeritos(ArrayList listaMeritos) {
		this.listaMeritos = listaMeritos;
	}

	public ArrayList getListaMeritos() {
		return this.listaMeritos;
	}

	public void setPestanaSeleccionada(String pestanaSeleccionada) {
		this.pestanaSeleccionada = pestanaSeleccionada;
	}

	public String getPestanaSeleccionada() {
		return this.pestanaSeleccionada;
	}

	public void setListaCreditos(ArrayList listaCreditos) {
		this.listaCreditos = listaCreditos;
	}

	public ArrayList getListaCreditos() {
		return this.listaCreditos;
	}

	public void setCExpId(Long cExpId) {
		this.cExpId = cExpId;
	}

	public Long getCExpId() {
		return this.cExpId;
	}

	public void setCEstatutId(Integer cEstatutId) {
		this.cEstatutId = cEstatutId;
	}

	public Integer getCEstatutId() {
		return this.cEstatutId;
	}

	public void setListaMeritosOpcionales(ArrayList listaMeritosOpcionales) {
		this.listaMeritosOpcionales = listaMeritosOpcionales;
	}

	public ArrayList getListaMeritosOpcionales() {
		return this.listaMeritosOpcionales;
	}

	public void setDMensaje(String dMensaje) {
		this.dMensaje = dMensaje;
	}

	public String getDMensaje() {
		return this.dMensaje;
	}

	public void setCDniReal(String cDniReal) {
		this.cDniReal = cDniReal;
	}

	public String getCDniReal() {
		return this.cDniReal;
	}

	public String getCDniReal_Busqueda() {
		return this.cDniReal_Busqueda;
	}

	public void setCDniReal_Busqueda(String cDniReal_Busqueda) {
		this.cDniReal_Busqueda = cDniReal_Busqueda;
	}

	public void setDMensajeInforme(String dMensajeInforme) {
		this.dMensajeInforme = dMensajeInforme;
	}

	public String getDMensajeInforme() {
		return this.dMensajeInforme;
	}

	public void setDSitAdministrativa_nombre(String dSitAdministrativa_nombre) {
		this.dSitAdministrativa_nombre = dSitAdministrativa_nombre;
	}

	public String getDSitAdministrativa_nombre() {
		return this.dSitAdministrativa_nombre;
	}

	public void setDEstatutario_nombre(String dEstatutario_nombre) {
		this.dEstatutario_nombre = dEstatutario_nombre;
	}

	public String getDEstatutario_nombre() {
		return this.dEstatutario_nombre;
	}

	public void setDEstatutActual_nombre(String dEstatutActual_nombre) {
		this.dEstatutActual_nombre = dEstatutActual_nombre;
	}

	public String getDEstatutActual_nombre() {
		return this.dEstatutActual_nombre;
	}

	public void setDProfesionalActual_nombre(String dProfesionalActual_nombre) {
		this.dProfesionalActual_nombre = dProfesionalActual_nombre;
	}

	public String getDProfesionalActual_nombre() {
		return this.dProfesionalActual_nombre;
	}

	public void setDEspecActual_nombre(String dEspecActual_nombre) {
		this.dEspecActual_nombre = dEspecActual_nombre;
	}

	public String getDEspecActual_nombre() {
		return this.dEspecActual_nombre;
	}

	public void setCJuridico(String cJuridico) {
		this.cJuridico = cJuridico;
	}

	public String getCJuridico() {
		return this.cJuridico;
	}

	public void setCSitAdministrativaAuxId(String cSitAdministrativaAuxId) {
		this.cSitAdministrativaAuxId = cSitAdministrativaAuxId;
	}

	public String getCSitAdministrativaAuxId() {
		return this.cSitAdministrativaAuxId;
	}

	public void setDEstatutarioActual_nombre(String dEstatutarioActual_nombre) {
		this.dEstatutarioActual_nombre = dEstatutarioActual_nombre;
	}

	public String getDEstatutarioActual_nombre() {
		return this.dEstatutarioActual_nombre;
	}

	public void setACiudad(String aCiudad) {
		this.aCiudad = aCiudad;
	}

	public String getACiudad() {
		return this.aCiudad;
	}

	public void setDSitAdministrativaAuxOtros(String dSitAdministrativaAuxOtros) {
		this.dSitAdministrativaAuxOtros = dSitAdministrativaAuxOtros;
	}

	public String getDSitAdministrativaAuxOtros() {
		return this.dSitAdministrativaAuxOtros;
	}

	public void setAPuesto(String aPuesto) {
		this.aPuesto = aPuesto;
	}

	public String getAPuesto() {
		return this.aPuesto;
	}

	public void setCConvocatoriaId(String cConvocatoriaId) {
		this.cConvocatoriaId = cConvocatoriaId;
	}

	public String getCConvocatoriaId() {
		return this.cConvocatoriaId;
	}
	
	public void setDConvocatoriaNombre(String dConvocatoriaNombre) {
		this.dConvocatoriaNombre = dConvocatoriaNombre;
	}

	public String getDConvocatoriaNombre() {
		return this.dConvocatoriaNombre;
	}	

	public void setBSexo(String bSexo) {
		this.bSexo = bSexo;
	}

	public String getBSexo() {
		return this.bSexo;
	}

	public void setNTelefono2(String nTelefono2) {
		this.nTelefono2 = nTelefono2;
	}

	public String getNTelefono2() {
		return this.nTelefono2;
	}

	public void setADomicilio(String aDomicilio) {
		this.aDomicilio = aDomicilio;
	}

	public String getADomicilio() {
		return this.aDomicilio;
	}

	public void setCPostalUsu(String cPostalUsu) {
		this.cPostalUsu = cPostalUsu;
	}

	public String getCPostalUsu() {
		return this.cPostalUsu;
	}

	public void setDLocalidad(String dLocalidad) {
		this.dLocalidad = dLocalidad;
	}

	public String getDLocalidad() {
		return this.dLocalidad;
	}

	public void setDModAnterior(String dModAnterior) {
		this.dModAnterior = dModAnterior;
	}

	public String getDModAnterior() {
		return this.dModAnterior;
	}

	public void setDProcedimiento(String dProcedimiento) {
		this.dProcedimiento = dProcedimiento;
	}

	public String getDProcedimiento() {
		return this.dProcedimiento;
	}

	public void setCJuridicoCombo(String cJuridicoCombo) {
		this.cJuridicoCombo = cJuridicoCombo;
	}

	public String getCJuridicoCombo() {
		return this.cJuridicoCombo;
	}

	public void setDProvinciaCarrera(String dProvinciaCarrera) {
		this.dProvinciaCarrera = dProvinciaCarrera;
	}

	public String getDProvinciaCarrera() {
		return this.dProvinciaCarrera;
	}

	public void setDServicio(String dServicio) {
		this.dServicio = dServicio;
	}

	public String getDServicio() {
		return this.dServicio;
	}

	public void setDPuesto(String dPuesto) {
		this.dPuesto = dPuesto;
	}

	public String getDPuesto() {
		return this.dPuesto;
	}

	public void setNAniosantiguedad(String nAniosantiguedad) {
		this.nAniosantiguedad = nAniosantiguedad;
	}

	public String getNAniosantiguedad() {
		return this.nAniosantiguedad;
	}

	public void setNMesesantiguedad(String nMesesantiguedad) {
		this.nMesesantiguedad = nMesesantiguedad;
	}

	public String getNMesesantiguedad() {
		return this.nMesesantiguedad;
	}

	public void setNDiasantiguedad(String nDiasantiguedad) {
		this.nDiasantiguedad = nDiasantiguedad;
	}

	public String getNDiasantiguedad() {
		return this.nDiasantiguedad;
	}

	public void setDRegimenJuridicoOtros(String dRegimenJuridicoOtros) {
		this.dRegimenJuridicoOtros = dRegimenJuridicoOtros;
	}

	public String getDRegimenJuridicoOtros() {
		return this.dRegimenJuridicoOtros;
	}

	public void setCTipo(String cTipo) {
		this.cTipo = cTipo;
	}

	public String getCTipo() {
		return this.cTipo;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		
		ActionErrors errores = super.validate(mapping, request);
		if (errores == null) {
			errores = new ActionErrors();
		}
		ActionErrors erroresAction = new ActionErrors();

		String nombreMetodoAction = request.getParameter(mapping.getParameter());

		if ((nombreMetodoAction != null) && (nombreMetodoAction.equals("listarUsuariosDNI"))) {
			if (GenericValidator.isBlankOrNull(this.cDniReal_Busqueda))
				erroresAction.add("cDniReal_Busqueda", new ActionError("error.obligatorio.generico"));
			else if (!Utilidades.esDNI(this.cDniReal_Busqueda))
				erroresAction.add("cDniReal_Busqueda", new ActionError("error.maskmsg", "NIF/NIE"));
		} else if ((nombreMetodoAction != null) && (nombreMetodoAction.equals("modificarUsuarioDNI"))) {
			if (!GenericValidator.isBlankOrNull(this.letraUID)) {
				if (!GenericValidator.maxLength(this.letraUID, 1))
					erroresAction.add("letraUID", new ActionError("errors.minlength", "Este campo", "1"));
				else if ((this.letraUID.toUpperCase().charAt(0) < 'A')
						|| (this.letraUID.toUpperCase().charAt(0) > 'C')) {
					erroresAction.add("letraUID", new ActionError("error.maskmsg.letraUID", "A, B o C"));
				}
			}

		}

		if (erroresAction.size() != 0) {
			request.setAttribute("erroresAction", erroresAction);
		}

		return errores;
	}

	public void setCPersonalId(String cPersonalId) {
		this.cPersonalId = cPersonalId;
	}

	public String getCPersonalId() {
		return this.cPersonalId;
	}

	public void setCProcedimientoId(String cProcedimientoId) {
		this.cProcedimientoId = cProcedimientoId;
	}

	public String getCProcedimientoId() {
		return this.cProcedimientoId;
	}
	
	public String getenPeriodoValMc() {
		return enPeriodoValMc;
	}
	
	public void setenPeriodoValMc(String enPeriodoValMc) {
		this.enPeriodoValMc = enPeriodoValMc;
	}

	public String getenPeriodoValCc() {
		return enPeriodoValCc;
	}

	public void setenPeriodoValCc(String enPeriodoValCc) {
		this.enPeriodoValCc = enPeriodoValCc;
	}

	public String getDAnioConvocatoria() {
		return dAnioConvocatoria;
	}

	public void setDAnioConvocatoria(String dAnioConvocatoria) {
		this.dAnioConvocatoria = dAnioConvocatoria;
	}
 

	public boolean getBVerCeisReport() {
		return bVerCeisReport;
	}

	public void setBVerCeisReport(boolean bVerCeisReport) {
		this.bVerCeisReport = bVerCeisReport;
	}

	public boolean getBVerCCReport() {
		return bVerCCReport;
	}

	public void setBVerCCReport(boolean bVerCCReport) {
		this.bVerCCReport = bVerCCReport;
	}
}
