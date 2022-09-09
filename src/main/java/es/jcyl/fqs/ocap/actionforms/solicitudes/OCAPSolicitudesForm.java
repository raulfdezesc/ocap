package es.jcyl.fqs.ocap.actionforms.solicitudes;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class OCAPSolicitudesForm extends ActionForm {

	private static final long serialVersionUID = -7307585025019743389L;
	private String cUsr_id;
	private String dApellido1;
	private String dApellido2;
	private String dNombre;
	private String cDni;
	private String cDniReal;
	private String aPassword;
	private String dCorreoelectronico;
	private String cCentrotrabajo_id;
	private String cEspec_id;
	private String cPerfil_id;
	private String cProfesional_id;
	private String cEstatutarioId;
	private String cEspecActual_id;
	private String cProfesionalActual_id;
	private String cEstatutarioActualId;
	private String cGerencia_id;
	private String cGerenciaFiltro_id;
	private String fPlazapropiedad;
	private String nTelefono1;
	private String nTelefono2;
	private String dDomicilio;
	private String fModicacion;
	private String bSexo;
	private String cProvinciaUsu_id;
	private String dProvinciaUsu;
	private String dLocalidadUsu;
	private String cPostalUsu;
	private String dLocalidad;
	private String dModAnterior;
	private String dProvinciaCarrera;
	private String dServicio;
	private String dPuesto;
	private String cExp_id;
	private String codigoId;
	private String fCreacion_exp;
	private String fRegistro_solic;
	private String fAceptac_solic;
	private String fNegacion_solic;
	private String dMotivo_neg;
	private String dObserv_neg_solic;
	private String fInconf_solic;
	private String dMotivo_inconf_solic;
	private String dDocum_inconf;
	private String fRespuesta_inconf_solic;
	private String fInicio_mc;
	private String fFin_mc;
	private Integer nRes_mc;
	private String fInicio_eval_mc;
	private String fFin_eval_mc;
	private String fAceptacionceis;
	private String fAceptacionCC;
	private String fFin_ca;
	private Integer nRes_ca;
	private String fInicio_cc;
	private String fFin_cc;
	private String bCertificado;
	private String cGrado_id;
	private String nAniosantiguedad;
	private String nMesesantiguedad;
	private String nDiasantiguedad;
	private String fNegacion_tmc;
	private String fNegacion_mc;
	private String fInconf_tmc;
	private String fInconf_mc;
	private String cConvocatoriaId;
	private String dNombreConvocatoria;
	private String fRegistro_oficial;
	private String fRegistro_oficialMC;
	private String fInformeEval;
	private String cProvincia_id;
	private String dProvincia;
	private String aCiudad;
	private String cSitAdministrativaAuxId;
	private String dSitAdministrativaAuxOtros;
	private String dSitAdministrativaOtros;
	private String dRegimenJuridicoOtros;
	private String bReconocimientoGrado;
	private String resumenCentros;
	private String cCertificado_id;
	private String dTitulo;
	private String dDesc;
	private String resumenCertificados;
	private String jspCadenaCertificado;
	private String dGrado_des;
	private String nAniosejercicio;
	private String nDiasPresentarSolicitud;
	private String dEstatutario_nombre;
	private String dEstatutarioActual_nombre;
	private String dProfesional_nombre;
	private String dProfesionalActual_nombre;
	private String dEspec_nombre;
	private String dEspecActual_nombre;
	private String dCentrotrabajo_nombre;
	private String cTipogerencia_id;
	private String dTipogerencia_desc;
	private String dGerencia_nombre;
	private String dDirector;
	private String dGerente;
	private long cExpmc_id;
	private long cCompfqs_id;
	private long cCuestionarioId;
	private String dObservacionesEvidencia;
	private String dApellNom;
	private String fFinRegistro;
	private String accionEnviar;
	private boolean jspInicio;
	private String dSolicitud;
	private ArrayList listadoCategorias;
	private ArrayList listadoEspecialidades;
	private ArrayList listadoProvincias;
	private ArrayList listadoGerencias;
	private ArrayList listadoCentrosTrabajo;
	private ArrayList listadoCertificados;
	private ArrayList listaOtrosCentros;
	private ArrayList listaCreditos;
	private ArrayList listaDocumentos;
	private String rSolicitud_acceso;
	private String rConstancia_antiguedad;
	private String rConstancia_categoria;
	private String rFase_inicio;
	private String rContinuidad_proceso;
	private String rFase_tramitacion;
	private String rContinuidad_proceso_tramitacion;
	private String rFase_validacion;
	private String rContinuidad_proceso_validacion;
	private String rTramitacion_correcta;
	private String rFase_ca;
	private String rContinuidad_proceso_ca;
	private String bOtroServicio;
	private String bValidacion_cc;
	private String aDondeServicio;
	private String cJuridico;
	private String cJuridicoId;
	private String cJuridicoCombo;
	private String bInconf_plazaprop;
	private String bInconf_antiguedad;
	private String bPersonales;
	private String bOtrosCentros;
	private String bPlazo;
	private String cFase;
	private String cEstado;
	private String cEstadoFiltro;
	private String cEstadoHistFiltro;
	private String cTipo;
	private String cProcedimientoId;
	private String cProcedimientoFiltro;
	private String dProcedimiento;
	private String dNombreCentro;
	private String aProvinciaCentro;
	private String aCategoria;
	private String aSituacion;
	private String aVinculo;
	private String fInicio;
	private String fFin;
	private String dNombreApellidosPresi1;
	private String dNombreApellidosPresi2;
	private String dNombreApellidosVocal;
	private String dTodosVocales;
	private String dNombreApellidosSecre1;
	private String dNombreApellidosSecre2;
	private String bSexoPresi1;
	private String bSexoPresi2;
	private String bSexoVocal;
	private String bSexoSecre1;
	private String bSexoSecre2;
	private String bEnCalidadPresi1;
	private String bEnCalidadPresi2;
	private String bEnCalidadVocal;
	private String bEnCalidadSecre1;
	private String bEnCalidadSecre2;
	private String dMiembrosAusentes;
	private String dAsuntosTramite;
	private String dRuegosPreguntas;
	private String nHoraInicio;
	private String nMinutosInicio;
	private String nHoraFin;
	private String nMinutosFin;
	private String resumenVocales;
	private boolean BNifNie;
	private boolean BApellidos;
	private boolean BNombre;
	private boolean BCSVSexo;
	private boolean BCategoria;
	private boolean BEspecialidad;
	private boolean BSituacionAdm;
	private boolean BProcedimiento;
	private boolean BGerencia;
	private boolean BEstado;
	private boolean BCausas;
	private boolean BConvocatoria;
	private boolean BFechaSolic;
	private boolean BJuridico;
	private boolean BAnios;
	private boolean BCentro;
	private boolean BModalidad;
	private boolean BPuesto;
	private boolean BServicio;
	private boolean BEpr;
	private long[] listaExpedientes;

	public String getCUsr_id() {
		return this.cUsr_id;
	}

	public String getDApellido1() {
		return this.dApellido1;
	}

	public String getDApellido2() {
		return this.dApellido2;
	}

	public String getFRegistro_solic() {
		return this.fRegistro_solic;
	}

	public String getDNombre() {
		return this.dNombre;
	}

	public String getCDni() {
		return this.cDni;
	}

	public String getAPassword() {
		return this.aPassword;
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

	public String getNTelefono1() {
		return this.nTelefono1;
	}

	public String getFModicacion() {
		return this.fModicacion;
	}

	public String getCExp_id() {
		return this.cExp_id;
	}

	public String getFCreacion_exp() {
		return this.fCreacion_exp;
	}

	public String getFAceptac_solic() {
		return this.fAceptac_solic;
	}

	public String getFNegacion_solic() {
		return this.fNegacion_solic;
	}

	public String getDMotivo_neg() {
		return this.dMotivo_neg;
	}

	public String getDObserv_neg_solic() {
		return this.dObserv_neg_solic;
	}

	public String getFInconf_solic() {
		return this.fInconf_solic;
	}

	public String getDMotivo_inconf_solic() {
		return this.dMotivo_inconf_solic;
	}

	public String getDDocum_inconf() {
		return this.dDocum_inconf;
	}

	public String getFRespuesta_inconf_solic() {
		return this.fRespuesta_inconf_solic;
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

	public String getFAceptacionceis() {
		return this.fAceptacionceis;
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

	public String getBCertificado() {
		return this.bCertificado;
	}

	public String getCGrado_id() {
		return this.cGrado_id;
	}

	public String getNAniosantiguedad() {
		return this.nAniosantiguedad;
	}

	public String getCProvincia_id() {
		return this.cProvincia_id;
	}

	public String getNAniosejercicio() {
		return this.nAniosejercicio;
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

	public String getRTramitacion_correcta() {
		return this.rTramitacion_correcta;
	}

	public String getBInconf_plazaprop() {
		return this.bInconf_plazaprop;
	}

	public String getBInconf_antiguedad() {
		return this.bInconf_antiguedad;
	}

	public boolean getjspInicio() {
		return this.jspInicio;
	}

	public String getDSolicitud() {
		return this.dSolicitud;
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

	public String getResumenCertificados() {
		return this.resumenCertificados;
	}

	public String getJspCadenaCertificado() {
		return this.jspCadenaCertificado;
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

	public ArrayList getListadoCertificados() {
		return this.listadoCertificados;
	}

	public void setCUsr_id(String cUsr_id) {
		this.cUsr_id = cUsr_id;
	}

	public void setDApellido1(String dApellido1) {
		this.dApellido1 = dApellido1;
	}

	public void setDApellido2(String dApellido2) {
		this.dApellido2 = dApellido2;
	}

	public void setFRegistro_solic(String fRegistro_solic) {
		this.fRegistro_solic = fRegistro_solic;
	}

	public void setDNombre(String dNombre) {
		this.dNombre = dNombre;
	}

	public void setCDni(String cDni) {
		this.cDni = cDni;
	}

	public void setAPassword(String aPassword) {
		this.aPassword = aPassword;
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

	public void setNTelefono1(String nTelefono1) {
		this.nTelefono1 = nTelefono1;
	}

	public void setFModicacion(String fModicacion) {
		this.fModicacion = fModicacion;
	}

	public void setCExp_id(String cExp_id) {
		this.cExp_id = cExp_id;
	}

	public void setFCreacion_exp(String fCreacion_exp) {
		this.fCreacion_exp = fCreacion_exp;
	}

	public void setFAceptac_solic(String fAceptac_solic) {
		this.fAceptac_solic = fAceptac_solic;
	}

	public void setFNegacion_solic(String fNegacion_solic) {
		this.fNegacion_solic = fNegacion_solic;
	}

	public void setDMotivo_neg(String dMotivo_neg) {
		this.dMotivo_neg = dMotivo_neg;
	}

	public void setDObserv_neg_solic(String dObserv_neg_solic) {
		this.dObserv_neg_solic = dObserv_neg_solic;
	}

	public void setFInconf_solic(String fInconf_solic) {
		this.fInconf_solic = fInconf_solic;
	}

	public void setDMotivo_inconf_solic(String dMotivo_inconf_solic) {
		this.dMotivo_inconf_solic = dMotivo_inconf_solic;
	}

	public void setDDocum_inconf(String dDocum_inconf) {
		this.dDocum_inconf = dDocum_inconf;
	}

	public void setFRespuesta_inconf_solic(String fRespuesta_inconf_solic) {
		this.fRespuesta_inconf_solic = fRespuesta_inconf_solic;
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

	public void setFAceptacionceis(String fAceptacionceis) {
		this.fAceptacionceis = fAceptacionceis;
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

	public void setBCertificado(String bCertificado) {
		this.bCertificado = bCertificado;
	}

	public void setCGrado_id(String cGrado_id) {
		this.cGrado_id = cGrado_id;
	}

	public void setNAniosantiguedad(String nAniosantiguedad) {
		this.nAniosantiguedad = nAniosantiguedad;
	}

	public void setCProvincia_id(String cProvincia_id) {
		this.cProvincia_id = cProvincia_id;
	}

	public void setNAniosejercicio(String nAniosejercicio) {
		this.nAniosejercicio = nAniosejercicio;
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

	public void setDSolicitud(String dSolicitud) {
		this.dSolicitud = dSolicitud;
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

	public void setResumenCertificados(String resumenCertificados) {
		this.resumenCertificados = resumenCertificados;
	}

	public void setJspCadenaCertificado(String jspCadenaCertificado) {
		this.jspCadenaCertificado = jspCadenaCertificado;
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

	public void setListadoCertificados(ArrayList listadoCertificados) {
		this.listadoCertificados = listadoCertificados;
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

	public void setRTramitacion_correcta(String rTramitacion_correcta) {
		this.rTramitacion_correcta = rTramitacion_correcta;
	}

	public void setBInconf_plazaprop(String bInconf_plazaprop) {
		this.bInconf_plazaprop = bInconf_plazaprop;
	}

	public void setBInconf_antiguedad(String bInconf_antiguedad) {
		this.bInconf_antiguedad = bInconf_antiguedad;
	}

	public void setListaCreditos(ArrayList listaCreditos) {
		this.listaCreditos = listaCreditos;
	}

	public ArrayList getListaCreditos() {
		return this.listaCreditos;
	}

	public void setFNegacion_tmc(String fNegacion_tmc) {
		this.fNegacion_tmc = fNegacion_tmc;
	}

	public String getFNegacion_tmc() {
		return this.fNegacion_tmc;
	}

	public void setFNegacion_mc(String fNegacion_mc) {
		this.fNegacion_mc = fNegacion_mc;
	}

	public String getFNegacion_mc() {
		return this.fNegacion_mc;
	}

	public void setFInconf_tmc(String fInconf_tmc) {
		this.fInconf_tmc = fInconf_tmc;
	}

	public String getFInconf_tmc() {
		return this.fInconf_tmc;
	}

	public void setFInconf_mc(String fInconf_mc) {
		this.fInconf_mc = fInconf_mc;
	}

	public String getFInconf_mc() {
		return this.fInconf_mc;
	}

	public void setCFase(String cFase) {
		this.cFase = cFase;
	}

	public String getCFase() {
		return this.cFase;
	}

	public void setCEstado(String cEstado) {
		this.cEstado = cEstado;
	}

	public String getCEstado() {
		return this.cEstado;
	}

	public void setCConvocatoriaId(String cConvocatoriaId) {
		this.cConvocatoriaId = cConvocatoriaId;
	}

	public String getCConvocatoriaId() {
		return this.cConvocatoriaId;
	}
	
	public void setDNombreConvocatoria(String dNombreConvocatoria) {
		this.dNombreConvocatoria = dNombreConvocatoria;
	}

	public String getDNombreConvocatoria() {
		return this.dNombreConvocatoria;
	}	

	public void setFFinRegistro(String fFinRegistro) {
		this.fFinRegistro = fFinRegistro;
	}

	public String getFFinRegistro() {
		return this.fFinRegistro;
	}

	public void setBSexo(String bSexo) {
		this.bSexo = bSexo;
	}

	public String getBSexo() {
		return this.bSexo;
	}

	public void setCDniReal(String cDniReal) {
		this.cDniReal = cDniReal;
	}

	public String getCDniReal() {
		return this.cDniReal;
	}

	public void setFRegistro_oficial(String fRegistro_oficial) {
		this.fRegistro_oficial = fRegistro_oficial;
	}

	public String getFRegistro_oficial() {
		return this.fRegistro_oficial;
	}

	public void setNDiasPresentarSolicitud(String nDiasPresentarSolicitud) {
		this.nDiasPresentarSolicitud = nDiasPresentarSolicitud;
	}

	public String getNDiasPresentarSolicitud() {
		return this.nDiasPresentarSolicitud;
	}

	public void setCCompfqs_id(long cCompfqs_id) {
		this.cCompfqs_id = cCompfqs_id;
	}

	public long getCCompfqs_id() {
		return this.cCompfqs_id;
	}

	public void setRFase_ca(String rFase_ca) {
		this.rFase_ca = rFase_ca;
	}

	public String getRFase_ca() {
		return this.rFase_ca;
	}

	public void setRContinuidad_proceso_ca(String rContinuidad_proceso_ca) {
		this.rContinuidad_proceso_ca = rContinuidad_proceso_ca;
	}

	public String getRContinuidad_proceso_ca() {
		return this.rContinuidad_proceso_ca;
	}

	public void setDApellNom(String dApellNom) {
		this.dApellNom = dApellNom;
	}

	public String getDApellNom() {
		return this.dApellNom;
	}

	public void setCEstatutarioId(String cEstatutarioId) {
		this.cEstatutarioId = cEstatutarioId;
	}

	public String getCEstatutarioId() {
		return this.cEstatutarioId;
	}

	public void setDEstatutario_nombre(String dEstatutario_nombre) {
		this.dEstatutario_nombre = dEstatutario_nombre;
	}

	public String getDEstatutario_nombre() {
		return this.dEstatutario_nombre;
	}

	public void setBOtroServicio(String bOtroServicio) {
		this.bOtroServicio = bOtroServicio;
	}

	public String getBOtroServicio() {
		return this.bOtroServicio;
	}

	public void setADondeServicio(String aDondeServicio) {
		this.aDondeServicio = aDondeServicio;
	}

	public String getADondeServicio() {
		return this.aDondeServicio;
	}

	public void setCJuridico(String cJuridico) {
		this.cJuridico = cJuridico;
	}

	public String getCJuridico() {
		return this.cJuridico;
	}

	public void setFRegistro_oficialMC(String fRegistro_oficialMC) {
		this.fRegistro_oficialMC = fRegistro_oficialMC;
	}

	public String getFRegistro_oficialMC() {
		return this.fRegistro_oficialMC;
	}

	public void setNTelefono2(String nTelefono2) {
		this.nTelefono2 = nTelefono2;
	}

	public String getNTelefono2() {
		return this.nTelefono2;
	}

	public void setDDomicilio(String dDomicilio) {
		this.dDomicilio = dDomicilio;
	}

	public String getDDomicilio() {
		return this.dDomicilio;
	}

	public void setCProvinciaUsu_id(String cProvinciaUsu_id) {
		this.cProvinciaUsu_id = cProvinciaUsu_id;
	}

	public String getCProvinciaUsu_id() {
		return this.cProvinciaUsu_id;
	}

	public void setDProvinciaUsu(String dProvinciaUsu) {
		this.dProvinciaUsu = dProvinciaUsu;
	}

	public String getDProvinciaUsu() {
		return this.dProvinciaUsu;
	}

	public void setDLocalidadUsu(String dLocalidadUsu) {
		this.dLocalidadUsu = dLocalidadUsu;
	}

	public String getDLocalidadUsu() {
		return this.dLocalidadUsu;
	}

	public void setCPostalUsu(String cPostalUsu) {
		this.cPostalUsu = cPostalUsu;
	}

	public String getCPostalUsu() {
		return this.cPostalUsu;
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

	public void setACiudad(String aCiudad) {
		this.aCiudad = aCiudad;
	}

	public String getACiudad() {
		return this.aCiudad;
	}

	public void setCSitAdministrativaAuxId(String cSitAdministrativaAuxId) {
		this.cSitAdministrativaAuxId = cSitAdministrativaAuxId;
	}

	public String getCSitAdministrativaAuxId() {
		return this.cSitAdministrativaAuxId;
	}

	public void setDSitAdministrativaAuxOtros(String dSitAdministrativaAuxOtros) {
		this.dSitAdministrativaAuxOtros = dSitAdministrativaAuxOtros;
	}

	public String getDSitAdministrativaAuxOtros() {
		return this.dSitAdministrativaAuxOtros;
	}

	public void setDNombreCentro(String dNombreCentro) {
		this.dNombreCentro = dNombreCentro;
	}

	public String getDNombreCentro() {
		return this.dNombreCentro;
	}

	public void setAProvinciaCentro(String aProvinciaCentro) {
		this.aProvinciaCentro = aProvinciaCentro;
	}

	public String getAProvinciaCentro() {
		return this.aProvinciaCentro;
	}

	public void setACategoria(String aCategoria) {
		this.aCategoria = aCategoria;
	}

	public String getACategoria() {
		return this.aCategoria;
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

	public void setASituacion(String aSituacion) {
		this.aSituacion = aSituacion;
	}

	public String getASituacion() {
		return this.aSituacion;
	}

	public void setAVinculo(String aVinculo) {
		this.aVinculo = aVinculo;
	}

	public String getAVinculo() {
		return this.aVinculo;
	}

	public void setResumenCentros(String resumenCentros) {
		this.resumenCentros = resumenCentros;
	}

	public String getResumenCentros() {
		return this.resumenCentros;
	}

	public void setCEspecActual_id(String cEspecActual_id) {
		this.cEspecActual_id = cEspecActual_id;
	}

	public String getCEspecActual_id() {
		return this.cEspecActual_id;
	}

	public void setCProfesionalActual_id(String cProfesionalActual_id) {
		this.cProfesionalActual_id = cProfesionalActual_id;
	}

	public String getCProfesionalActual_id() {
		return this.cProfesionalActual_id;
	}

	public void setCEstatutarioActualId(String cEstatutarioActualId) {
		this.cEstatutarioActualId = cEstatutarioActualId;
	}

	public String getCEstatutarioActualId() {
		return this.cEstatutarioActualId;
	}

	public void setDEstatutarioActual_nombre(String dEstatutarioActual_nombre) {
		this.dEstatutarioActual_nombre = dEstatutarioActual_nombre;
	}

	public String getDEstatutarioActual_nombre() {
		return this.dEstatutarioActual_nombre;
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

	public void setListaOtrosCentros(ArrayList listaOtrosCentros) {
		this.listaOtrosCentros = listaOtrosCentros;
	}

	public ArrayList getListaOtrosCentros() {
		return this.listaOtrosCentros;
	}

	public void setBPersonales(String bPersonales) {
		this.bPersonales = bPersonales;
	}

	public String getBPersonales() {
		return this.bPersonales;
	}

	public void setBOtrosCentros(String bOtrosCentros) {
		this.bOtrosCentros = bOtrosCentros;
	}

	public String getBOtrosCentros() {
		return this.bOtrosCentros;
	}

	public void setDDirector(String dDirector) {
		this.dDirector = dDirector;
	}

	public String getDDirector() {
		return this.dDirector;
	}

	public void setBValidacion_cc(String bValidacion_cc) {
		this.bValidacion_cc = bValidacion_cc;
	}

	public String getBValidacion_cc() {
		return this.bValidacion_cc;
	}

	public void setBPlazo(String bPlazo) {
		this.bPlazo = bPlazo;
	}

	public String getBPlazo() {
		return this.bPlazo;
	}

	public void setBNifNie(boolean BNifNie) {
		this.BNifNie = BNifNie;
	}

	public boolean getBNifNie() {
		return this.BNifNie;
	}

	public void setBApellidos(boolean BApellidos) {
		this.BApellidos = BApellidos;
	}

	public boolean getBApellidos() {
		return this.BApellidos;
	}

	public void setBNombre(boolean BNombre) {
		this.BNombre = BNombre;
	}

	public boolean getBNombre() {
		return this.BNombre;
	}

	public void setBCategoria(boolean BCategoria) {
		this.BCategoria = BCategoria;
	}

	public boolean getBCategoria() {
		return this.BCategoria;
	}

	public void setBEspecialidad(boolean BEspecialidad) {
		this.BEspecialidad = BEspecialidad;
	}

	public boolean getBEspecialidad() {
		return this.BEspecialidad;
	}

	public void setBSituacionAdm(boolean BSituacionAdm) {
		this.BSituacionAdm = BSituacionAdm;
	}

	public boolean getBSituacionAdm() {
		return this.BSituacionAdm;
	}

	public void setBProcedimiento(boolean BProcedimiento) {
		this.BProcedimiento = BProcedimiento;
	}

	public boolean getBProcedimiento() {
		return this.BProcedimiento;
	}

	public void setBCSVSexo(boolean BCSVSexo) {
		this.BCSVSexo = BCSVSexo;
	}

	public boolean getBCSVSexo() {
		return this.BCSVSexo;
	}

	public void setDNombreApellidosPresi1(String dNombreApellidosPresi1) {
		this.dNombreApellidosPresi1 = dNombreApellidosPresi1;
	}

	public String getDNombreApellidosPresi1() {
		return this.dNombreApellidosPresi1;
	}

	public void setDNombreApellidosPresi2(String dNombreApellidosPresi2) {
		this.dNombreApellidosPresi2 = dNombreApellidosPresi2;
	}

	public String getDNombreApellidosPresi2() {
		return this.dNombreApellidosPresi2;
	}

	public void setDNombreApellidosVocal(String dNombreApellidosVocal) {
		this.dNombreApellidosVocal = dNombreApellidosVocal;
	}

	public String getDNombreApellidosVocal() {
		return this.dNombreApellidosVocal;
	}

	public void setDNombreApellidosSecre1(String dNombreApellidosSecre1) {
		this.dNombreApellidosSecre1 = dNombreApellidosSecre1;
	}

	public String getDNombreApellidosSecre1() {
		return this.dNombreApellidosSecre1;
	}

	public void setDNombreApellidosSecre2(String dNombreApellidosSecre2) {
		this.dNombreApellidosSecre2 = dNombreApellidosSecre2;
	}

	public String getDNombreApellidosSecre2() {
		return this.dNombreApellidosSecre2;
	}

	public void setBSexoPresi1(String bSexoPresi1) {
		this.bSexoPresi1 = bSexoPresi1;
	}

	public String getBSexoPresi1() {
		return this.bSexoPresi1;
	}

	public void setBSexoPresi2(String bSexoPresi2) {
		this.bSexoPresi2 = bSexoPresi2;
	}

	public String getBSexoPresi2() {
		return this.bSexoPresi2;
	}

	public void setBSexoVocal(String bSexoVocal) {
		this.bSexoVocal = bSexoVocal;
	}

	public String getBSexoVocal() {
		return this.bSexoVocal;
	}

	public void setBSexoSecre1(String bSexoSecre1) {
		this.bSexoSecre1 = bSexoSecre1;
	}

	public String getBSexoSecre1() {
		return this.bSexoSecre1;
	}

	public void setBSexoSecre2(String bSexoSecre2) {
		this.bSexoSecre2 = bSexoSecre2;
	}

	public String getBSexoSecre2() {
		return this.bSexoSecre2;
	}

	public void setBEnCalidadPresi1(String bEnCalidadPresi1) {
		this.bEnCalidadPresi1 = bEnCalidadPresi1;
	}

	public String getBEnCalidadPresi1() {
		return this.bEnCalidadPresi1;
	}

	public void setBEnCalidadPresi2(String bEnCalidadPresi2) {
		this.bEnCalidadPresi2 = bEnCalidadPresi2;
	}

	public String getBEnCalidadPresi2() {
		return this.bEnCalidadPresi2;
	}

	public void setBEnCalidadVocal(String bEnCalidadVocal) {
		this.bEnCalidadVocal = bEnCalidadVocal;
	}

	public String getBEnCalidadVocal() {
		return this.bEnCalidadVocal;
	}

	public void setBEnCalidadSecre1(String bEnCalidadSecre1) {
		this.bEnCalidadSecre1 = bEnCalidadSecre1;
	}

	public String getBEnCalidadSecre1() {
		return this.bEnCalidadSecre1;
	}

	public void setBEnCalidadSecre2(String bEnCalidadSecre2) {
		this.bEnCalidadSecre2 = bEnCalidadSecre2;
	}

	public String getBEnCalidadSecre2() {
		return this.bEnCalidadSecre2;
	}

	public void setDMiembrosAusentes(String dMiembrosAusentes) {
		this.dMiembrosAusentes = dMiembrosAusentes;
	}

	public String getDMiembrosAusentes() {
		return this.dMiembrosAusentes;
	}

	public void setDRuegosPreguntas(String dRuegosPreguntas) {
		this.dRuegosPreguntas = dRuegosPreguntas;
	}

	public String getDRuegosPreguntas() {
		return this.dRuegosPreguntas;
	}

	public void setDAsuntosTramite(String dAsuntosTramite) {
		this.dAsuntosTramite = dAsuntosTramite;
	}

	public String getDAsuntosTramite() {
		return this.dAsuntosTramite;
	}

	public void setDTodosVocales(String dTodosVocales) {
		this.dTodosVocales = dTodosVocales;
	}

	public String getDTodosVocales() {
		return this.dTodosVocales;
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

	public void setResumenVocales(String resumenVocales) {
		this.resumenVocales = resumenVocales;
	}

	public String getResumenVocales() {
		return this.resumenVocales;
	}

	public void setFAceptacionCC(String fAceptacionCC) {
		this.fAceptacionCC = fAceptacionCC;
	}

	public String getFAceptacionCC() {
		return this.fAceptacionCC;
	}

	public void setDGerente(String dGerente) {
		this.dGerente = dGerente;
	}

	public String getDGerente() {
		return this.dGerente;
	}

	public void setBGerencia(boolean BGerencia) {
		this.BGerencia = BGerencia;
	}

	public boolean getBGerencia() {
		return this.BGerencia;
	}

	public void setBEstado(boolean BEstado) {
		this.BEstado = BEstado;
	}

	public boolean getBEstado() {
		return this.BEstado;
	}

	public void setListaDocumentos(ArrayList listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public ArrayList getListaDocumentos() {
		return this.listaDocumentos;
	}

	public void setCodigoId(String codigoId) {
		this.codigoId = codigoId;
	}

	public String getCodigoId() {
		return this.codigoId;
	}

	public void setCCuestionarioId(long cCuestionarioId) {
		this.cCuestionarioId = cCuestionarioId;
	}

	public long getCCuestionarioId() {
		return this.cCuestionarioId;
	}

	public void setDObservacionesEvidencia(String dObservacionesEvidencia) {
		this.dObservacionesEvidencia = dObservacionesEvidencia;
	}

	public String getDObservacionesEvidencia() {
		return this.dObservacionesEvidencia;
	}

	public void setBReconocimientoGrado(String bReconocimientoGrado) {
		this.bReconocimientoGrado = bReconocimientoGrado;
	}

	public String getBReconocimientoGrado() {
		return this.bReconocimientoGrado;
	}

	public void setCGerenciaFiltro_id(String cGerenciaFiltro_id) {
		this.cGerenciaFiltro_id = cGerenciaFiltro_id;
	}

	public String getCGerenciaFiltro_id() {
		return this.cGerenciaFiltro_id;
	}

	public void setCEstadoFiltro(String cEstadoFiltro) {
		this.cEstadoFiltro = cEstadoFiltro;
	}

	public String getCEstadoFiltro() {
		return this.cEstadoFiltro;
	}
	
	public void setCEstadoHistFiltro(String cEstadoHistFiltro) {
		this.cEstadoHistFiltro = cEstadoHistFiltro;
	}

	public String getCEstadoHistFiltro() {
		return this.cEstadoHistFiltro;
	}

	public void setBCausas(boolean BCausas) {
		this.BCausas = BCausas;
	}

	public boolean getBCausas() {
		return this.BCausas;
	}

	public void setCProcedimientoFiltro(String cProcedimientoFiltro) {
		this.cProcedimientoFiltro = cProcedimientoFiltro;
	}

	public String getCProcedimientoFiltro() {
		return this.cProcedimientoFiltro;
	}

	public void setBConvocatoria(boolean BConvocatoria) {
		this.BConvocatoria = BConvocatoria;
	}

	public boolean getBConvocatoria() {
		return this.BConvocatoria;
	}

	public void setBFechaSolic(boolean BFechaSolic) {
		this.BFechaSolic = BFechaSolic;
	}

	public boolean getBFechaSolic() {
		return this.BFechaSolic;
	}

	public void setBJuridico(boolean BJuridico) {
		this.BJuridico = BJuridico;
	}

	public boolean getBJuridico() {
		return this.BJuridico;
	}

	public void setBAnios(boolean BAnios) {
		this.BAnios = BAnios;
	}

	public boolean getBAnios() {
		return this.BAnios;
	}

	public void setBCentro(boolean BCentro) {
		this.BCentro = BCentro;
	}

	public boolean getBCentro() {
		return this.BCentro;
	}

	public void setBModalidad(boolean BModalidad) {
		this.BModalidad = BModalidad;
	}

	public boolean getBModalidad() {
		return this.BModalidad;
	}

	public void setFInformeEval(String fInformeEval) {
		this.fInformeEval = fInformeEval;
	}

	public String getFInformeEval() {
		return this.fInformeEval;
	}

	public void setBPuesto(boolean BPuesto) {
		this.BPuesto = BPuesto;
	}

	public boolean getBPuesto() {
		return this.BPuesto;
	}

	public void setBServicio(boolean BServicio) {
		this.BServicio = BServicio;
	}

	public boolean getBServicio() {
		return this.BServicio;
	}

	public void setBEpr(boolean BEpr) {
		this.BEpr = BEpr;
	}

	public boolean getBEpr() {
		return this.BEpr;
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

	public void setCJuridicoId(String cJuridicoId) {
		this.cJuridicoId = cJuridicoId;
	}

	public String getCJuridicoId() {
		return this.cJuridicoId;
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

	public void setDRegimenJuridicoOtros(String dRegimenJuridicoOtros) {
		this.dRegimenJuridicoOtros = dRegimenJuridicoOtros;
	}

	public String getDRegimenJuridicoOtros() {
		return this.dRegimenJuridicoOtros;
	}

	public void setDSitAdministrativaOtros(String dSitAdministrativaOtros) {
		this.dSitAdministrativaOtros = dSitAdministrativaOtros;
	}

	public String getDSitAdministrativaOtros() {
		return this.dSitAdministrativaOtros;
	}

	public void setCTipo(String cTipo) {
		this.cTipo = cTipo;
	}

	public String getCTipo() {
		return this.cTipo;
	}

	public void setCProcedimientoId(String cProcedimientoId) {
		this.cProcedimientoId = cProcedimientoId;
	}

	public String getCProcedimientoId() {
		return this.cProcedimientoId;
	}

	public long[] getListaExpedientes() {
		return listaExpedientes;
	}

	public void setListaExpedientes(long[] listaExpedientes) {
		this.listaExpedientes = listaExpedientes;
	}
}
