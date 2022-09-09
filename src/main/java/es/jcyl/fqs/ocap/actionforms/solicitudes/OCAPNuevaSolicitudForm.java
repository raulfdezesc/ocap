package es.jcyl.fqs.ocap.actionforms.solicitudes;

import java.util.ArrayList;
import org.apache.struts.action.ActionForm;

public class OCAPNuevaSolicitudForm extends ActionForm {

	private static final long serialVersionUID = -7287212912396829235L;
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
	private String cEspecFijo_id;
	private String cPerfil_id;
	private String cProfesional_id;
	private String cProfesionalFijo_id;
	private String cGerencia_id;
	private String fPlazapropiedad;
	private String nTelefono1;
	private String nTelefono2;
	private String dDomicilio;
	private String fModicacion;
	private String bSexo;
	private String cProvinciaUsu_id;
	private String dProvinciaUsu;
	private String cLocalidad_id;
	private String dLocalidad;
	private String cPostalUsu;
	private String bLopdSolicitud;
	private String cModAnteriorId;
	private String dModAnterior;
	private String dServicio;
	private String dPuesto;
	private String cProvinciaCarrera_id;
	private String dProvinciaCarrera;
	private String dGerenciaCarrera_nombre;
	private String dCentrotrabajoCarrera_nombre;
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
	private String fRespuesta_inconf_mc;
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
	private String cGradoAnteriorId;
	private String nAniosantiguedad;
	private String nMesesantiguedad;
	private String nDiasantiguedad;
	private String fNegacion_tmc;
	private String fNegacion_mc;
	private String fInconf_tmc;
	private String fInconf_mc;
	private String cConvocatoriaId;
	private String dConvocatoria_nombre;
	private String fRegistro_oficial;
	private String fRegistro_oficialMC;
	private String fInformeEval;
	private String fIniciocc;
	private String fFincc;
	private long cItinerarioId;
	private String cProvincia_id;
	private String dProvincia;
	private String aCiudad;
	private String cSitAdministrativaId;
	private String dSitAdministrativaOtros;
	private String dRegimenJuridicoOtros;
	private String bReconocimientoGrado;
	private String resumenCentros;
	private long cEstatutId;
	private String fInicio_ca;
	private String dMotivosCambio;
	private String bExclusion;
	private String cCertificado_id;
	private String dTitulo;
	private String dDesc;
	private String resumenCertificados;
	private String jspCadenaCertificado;
	private String dGrado_des;
	private String nAniosejercicio;
	private String nDiasPresentarSolicitud;
	private String BCierreSo;
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
	private String dSitAdministrativa_nombre;
	private long cCompfqs_id;
	private long cCuestionarioId;
	private String dObservacionesEvidencia;
	private long cSolicitudId;
	private String APeticion;
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
	private ArrayList listaUsuarios;
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
	private String cJuridicoId;
	private String cJuridicoCombo;
	private boolean bInconf_plazaprop;
	private boolean bInconf_antiguedad;
	private String bPersonales;
	private String bOtrosCentros;
	private boolean bPlazo;
	private boolean bAmbito;
	private boolean bPenalizado;
	private String[] cMotivosExclusion;
	private String dOtrosMotivosExclusion;
	private String cFase;
	private String cEstado;
	private String cEstadoFiltro;
	private long cEstadoId;
	private String cProcedimientoId;
	private String dProcedimiento;
	private String cProcedimientoFiltro;
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
	private String codigoCaptcha;
	private String nHoraRegistro;
	private String nHoraAlega;
	private String fFin_penaliza;
	private String fRecGrado;
	private String dAccion;
	private String bRespuesta;
	private String cSitAdmonActualId;
	private String dSitAdmonActual;
	private String aOtraSitAdmonActual;
	private String cGerenciaActualId;
	private String dGerenciaActual;
	private String cCentroTrabActualId;
	private String dCentroTrabActual;
	private String cProvCarreraActualId;
	private String dProvCarreraActual;
	private ArrayList historialProfesional;
	private boolean bExisteSolicParaNifConv = false;
	private String ordenListado;
	private String anioConvocatoria;
	private String meritosBloqueados;
	private boolean reasociarGrs;
	private String fRenuncia;
	private String cItineraio_id;
	private boolean eliminarEvaluacion;
	private boolean desbloqueoAsistenciales;	
	private String fFin_ca_convocatoria;
	private String BFechaFinPgp;
	
	
	public String getAnioConvocatoria() {
		return anioConvocatoria;
	}

	public void setAnioConvocatoria(String anioConvocatoria) {
		this.anioConvocatoria = anioConvocatoria;
	}

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

	public boolean getBInconf_plazaprop() {
		return this.bInconf_plazaprop;
	}

	public boolean getBInconf_antiguedad() {
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

	public void setBInconf_plazaprop(boolean bInconf_plazaprop) {
		this.bInconf_plazaprop = bInconf_plazaprop;
	}

	public void setBInconf_antiguedad(boolean bInconf_antiguedad) {
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

	public void setCSitAdministrativaId(String cSitAdministrativaId) {
		this.cSitAdministrativaId = cSitAdministrativaId;
	}

	public String getCSitAdministrativaId() {
		return this.cSitAdministrativaId;
	}

	public void setDSitAdministrativa_nombre(String dSitAdministrativa_nombre) {
		this.dSitAdministrativa_nombre = dSitAdministrativa_nombre;
	}

	public String getDSitAdministrativa_nombre() {
		return this.dSitAdministrativa_nombre;
	}

	public void setCEstatutId(long cEstatutId) {
		this.cEstatutId = cEstatutId;
	}

	public long getCEstatutId() {
		return this.cEstatutId;
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

	public void setBPlazo(boolean bPlazo) {
		this.bPlazo = bPlazo;
	}

	public boolean getBPlazo() {
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

	public void setCEstadoFiltro(String cEstadoFiltro) {
		this.cEstadoFiltro = cEstadoFiltro;
	}

	public String getCEstadoFiltro() {
		return this.cEstadoFiltro;
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

	public void setCModAnteriorId(String cModAnteriorId) {
		this.cModAnteriorId = cModAnteriorId;
	}

	public String getCModAnteriorId() {
		return this.cModAnteriorId;
	}

	public void setDModAnterior(String dModAnterior) {
		this.dModAnterior = dModAnterior;
	}

	public String getDModAnterior() {
		return this.dModAnterior;
	}

	public void setCProvinciaCarrera_id(String cProvinciaCarrera_id) {
		this.cProvinciaCarrera_id = cProvinciaCarrera_id;
	}

	public String getCProvinciaCarrera_id() {
		return this.cProvinciaCarrera_id;
	}

	public void setDProvinciaCarrera(String dProvinciaCarrera) {
		this.dProvinciaCarrera = dProvinciaCarrera;
	}

	public String getDProvinciaCarrera() {
		return this.dProvinciaCarrera;
	}

	public void setDGerenciaCarrera_nombre(String dGerenciaCarrera_nombre) {
		this.dGerenciaCarrera_nombre = dGerenciaCarrera_nombre;
	}

	public String getDGerenciaCarrera_nombre() {
		return this.dGerenciaCarrera_nombre;
	}

	public void setDCentrotrabajoCarrera_nombre(String dCentrotrabajoCarrera_nombre) {
		this.dCentrotrabajoCarrera_nombre = dCentrotrabajoCarrera_nombre;
	}

	public String getDCentrotrabajoCarrera_nombre() {
		return this.dCentrotrabajoCarrera_nombre;
	}

	public void setDSitAdministrativaOtros(String dSitAdministrativaOtros) {
		this.dSitAdministrativaOtros = dSitAdministrativaOtros;
	}

	public String getDSitAdministrativaOtros() {
		return this.dSitAdministrativaOtros;
	}

	public void setDProcedimiento(String dProcedimiento) {
		this.dProcedimiento = dProcedimiento;
	}

	public String getDProcedimiento() {
		return this.dProcedimiento;
	}

	public void setListaUsuarios(ArrayList listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public ArrayList getListaUsuarios() {
		return this.listaUsuarios;
	}

	public void setCSolicitudId(long cSolicitudId) {
		this.cSolicitudId = cSolicitudId;
	}

	public long getCSolicitudId() {
		return this.cSolicitudId;
	}

	public void setDRegimenJuridicoOtros(String dRegimenJuridicoOtros) {
		this.dRegimenJuridicoOtros = dRegimenJuridicoOtros;
	}

	public String getDRegimenJuridicoOtros() {
		return this.dRegimenJuridicoOtros;
	}

	public void setCodigoCaptcha(String codigoCaptcha) {
		this.codigoCaptcha = codigoCaptcha;
	}

	public String getCodigoCaptcha() {
		return this.codigoCaptcha;
	}

	public void setDLocalidad(String dLocalidad) {
		this.dLocalidad = dLocalidad;
	}

	public String getDLocalidad() {
		return this.dLocalidad;
	}

	public void setCLocalidad_id(String cLocalidad_id) {
		this.cLocalidad_id = cLocalidad_id;
	}

	public String getCLocalidad_id() {
		return this.cLocalidad_id;
	}

	public void setCJuridicoCombo(String cJuridicoCombo) {
		this.cJuridicoCombo = cJuridicoCombo;
	}

	public String getCJuridicoCombo() {
		return this.cJuridicoCombo;
	}

	public void setBLopdSolicitud(String bLopdSolicitud) {
		this.bLopdSolicitud = bLopdSolicitud;
	}

	public String getBLopdSolicitud() {
		return this.bLopdSolicitud;
	}

	public void setCEspecFijo_id(String cEspecFijo_id) {
		this.cEspecFijo_id = cEspecFijo_id;
	}

	public String getCEspecFijo_id() {
		return this.cEspecFijo_id;
	}

	public void setCProfesionalFijo_id(String cProfesionalFijo_id) {
		this.cProfesionalFijo_id = cProfesionalFijo_id;
	}

	public String getCProfesionalFijo_id() {
		return this.cProfesionalFijo_id;
	}

	public void setCJuridicoId(String cJuridicoId) {
		this.cJuridicoId = cJuridicoId;
	}

	public String getCJuridicoId() {
		return this.cJuridicoId;
	}

	public void setNHoraRegistro(String nHoraRegistro) {
		this.nHoraRegistro = nHoraRegistro;
	}

	public String getNHoraRegistro() {
		return this.nHoraRegistro;
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

	public void setCGradoAnteriorId(String cGradoAnteriorId) {
		this.cGradoAnteriorId = cGradoAnteriorId;
	}

	public String getCGradoAnteriorId() {
		return this.cGradoAnteriorId;
	}

	public void setBAmbito(boolean bAmbito) {
		this.bAmbito = bAmbito;
	}

	public boolean getBAmbito() {
		return this.bAmbito;
	}

	public void setAPeticion(String APeticion) {
		this.APeticion = APeticion;
	}

	public String getAPeticion() {
		return this.APeticion;
	}

	public void setCEstadoId(long cEstadoId) {
		this.cEstadoId = cEstadoId;
	}

	public long getCEstadoId() {
		return this.cEstadoId;
	}

	public void setNHoraAlega(String nHoraAlega) {
		this.nHoraAlega = nHoraAlega;
	}

	public String getNHoraAlega() {
		return this.nHoraAlega;
	}

	public void setFFin_penaliza(String fFin_penaliza) {
		this.fFin_penaliza = fFin_penaliza;
	}

	public String getFFin_penaliza() {
		return this.fFin_penaliza;
	}

	public void setFRecGrado(String fRecGrado) {
		this.fRecGrado = fRecGrado;
	}

	public String getFRecGrado() {
		return this.fRecGrado;
	}

	public void setBPenalizado(boolean bPenalizado) {
		this.bPenalizado = bPenalizado;
	}

	public boolean getBPenalizado() {
		return this.bPenalizado;
	}

	public void setBCierreSo(String BCierreSo) {
		this.BCierreSo = BCierreSo;
	}

	public String getBCierreSo() {
		return this.BCierreSo;
	}

	public void setCProcedimientoId(String cProcedimientoId) {
		this.cProcedimientoId = cProcedimientoId;
	}

	public String getCProcedimientoId() {
		return this.cProcedimientoId;
	}

	public void setFInformeEval(String fInformeEval) {
		this.fInformeEval = fInformeEval;
	}

	public String getFInformeEval() {
		return this.fInformeEval;
	}

	public void setDAccion(String dAccion) {
		this.dAccion = dAccion;
	}

	public String getDAccion() {
		return this.dAccion;
	}

	public void setBRespuesta(String bRespuesta) {
		this.bRespuesta = bRespuesta;
	}

	public String getBRespuesta() {
		return this.bRespuesta;
	}

	public void setFInicio_ca(String fInicio_ca) {
		this.fInicio_ca = fInicio_ca;
	}

	public String getFInicio_ca() {
		return this.fInicio_ca;
	}

	public void setFRespuesta_inconf_mc(String fRespuesta_inconf_mc) {
		this.fRespuesta_inconf_mc = fRespuesta_inconf_mc;
	}

	public String getFRespuesta_inconf_mc() {
		return this.fRespuesta_inconf_mc;
	}

	public void setDMotivosCambio(String dMotivosCambio) {
		this.dMotivosCambio = dMotivosCambio;
	}

	public String getDMotivosCambio() {
		return this.dMotivosCambio;
	}

	public void setBExclusion(String bExclusion) {
		this.bExclusion = bExclusion;
	}

	public String getBExclusion() {
		return this.bExclusion;
	}

	public void setCItinerarioId(long cItinerarioId) {
		this.cItinerarioId = cItinerarioId;
	}

	public long getCItinerarioId() {
		return this.cItinerarioId;
	}

	public void setCSitAdmonActualId(String cSitAdmonActualId) {
		this.cSitAdmonActualId = cSitAdmonActualId;
	}

	public String getCSitAdmonActualId() {
		return this.cSitAdmonActualId;
	}

	public void setDSitAdmonActual(String dSitAdmonActual) {
		this.dSitAdmonActual = dSitAdmonActual;
	}

	public String getDSitAdmonActual() {
		return this.dSitAdmonActual;
	}

	public void setAOtraSitAdmonActual(String aOtraSitAdmonActual) {
		this.aOtraSitAdmonActual = aOtraSitAdmonActual;
	}

	public String getAOtraSitAdmonActual() {
		return this.aOtraSitAdmonActual;
	}

	public void setCGerenciaActualId(String cGerenciaActualId) {
		this.cGerenciaActualId = cGerenciaActualId;
	}

	public String getCGerenciaActualId() {
		return this.cGerenciaActualId;
	}

	public void setDGerenciaActual(String dGerenciaActual) {
		this.dGerenciaActual = dGerenciaActual;
	}

	public String getDGerenciaActual() {
		return this.dGerenciaActual;
	}

	public void setCCentroTrabActualId(String cCentroTrabActualId) {
		this.cCentroTrabActualId = cCentroTrabActualId;
	}

	public String getCCentroTrabActualId() {
		return this.cCentroTrabActualId;
	}

	public void setDCentroTrabActual(String dCentroTrabActual) {
		this.dCentroTrabActual = dCentroTrabActual;
	}

	public String getDCentroTrabActual() {
		return this.dCentroTrabActual;
	}

	public void setCProvCarreraActualId(String cProvCarreraActualId) {
		this.cProvCarreraActualId = cProvCarreraActualId;
	}

	public String getCProvCarreraActualId() {
		return this.cProvCarreraActualId;
	}

	public void setDProvCarreraActual(String dProvCarreraActual) {
		this.dProvCarreraActual = dProvCarreraActual;
	}

	public String getDProvCarreraActual() {
		return this.dProvCarreraActual;
	}

	public void setHistorialProfesional(ArrayList historialProfesional) {
		this.historialProfesional = historialProfesional;
	}

	public ArrayList getHistorialProfesional() {
		return this.historialProfesional;
	}

	public void setCMotivosExclusion(String[] cMotivosExclusion) {
		this.cMotivosExclusion = cMotivosExclusion;
	}

	public String[] getCMotivosExclusion() {
		return this.cMotivosExclusion;
	}

	public void setDOtrosMotivosExclusion(String dOtrosMotivosExclusion) {
		this.dOtrosMotivosExclusion = dOtrosMotivosExclusion;
	}

	public String getDOtrosMotivosExclusion() {
		return this.dOtrosMotivosExclusion;
	}

	public void setDConvocatoria_nombre(String dConvocatoria_nombre) {
		this.dConvocatoria_nombre = dConvocatoria_nombre;
	}

	public String getDConvocatoria_nombre() {
		return this.dConvocatoria_nombre;
	}
	
	public void setbExisteSolicParaNifConv(boolean BExisteSolicParaNifConv) {
		this.bExisteSolicParaNifConv = BExisteSolicParaNifConv;
	}

	public boolean getbExisteSolicParaNifConv() {
		return this.bExisteSolicParaNifConv;
	}

	/**
	 * @return the ordenListado
	 */
	public String getOrdenListado() {
		return ordenListado;
	}

	/**
	 * @param ordenListado the ordenListado to set
	 */
	public void setOrdenListado(String ordenListado) {
		this.ordenListado = ordenListado;
	}

	/**
	 * @return the fIniciocc
	 */
	public String getFIniciocc() {
		return fIniciocc;
	}

	/**
	 * @param fIniciocc the fIniciocc to set
	 */
	public void setFIniciocc(String fIniciocc) {
		this.fIniciocc = fIniciocc;
	}

	/**
	 * @return the fFincc
	 */
	public String getFFincc() {
		return fFincc;
	}

	/**
	 * @param fFincc the fFincc to set
	 */
	public void setFFincc(String fFincc) {
		this.fFincc = fFincc;
	}

	/**
	 * @return the meritosBloqueados
	 */
	public String getMeritosBloqueados() {
		return meritosBloqueados;
	}

	/**
	 * @param meritosBloqueados the meritosBloqueados to set
	 */
	public void setMeritosBloqueados(String meritosBloqueados) {
		this.meritosBloqueados = meritosBloqueados;
	}

	public boolean isReasociarGrs() {
		return reasociarGrs;
	}

	public void setReasociarGrs(boolean reasociarGrs) {
		this.reasociarGrs = reasociarGrs;
	}

	public String getFRenuncia() {
		return fRenuncia;
	}

	public void setFRenuncia(String fRenuncia) {
		this.fRenuncia = fRenuncia;
	}

	public String getcItineraio_id() {
		return cItineraio_id;
	}

	public void setcItineraio_id(String cItineraio_id) {
		this.cItineraio_id = cItineraio_id;
	}

	public boolean isEliminarEvaluacion() {
		return eliminarEvaluacion;
	}

	public void setEliminarEvaluacion(boolean eliminarEvaluacion) {
		this.eliminarEvaluacion = eliminarEvaluacion;
	}

	public boolean isDesbloqueoAsistenciales() {
		return desbloqueoAsistenciales;
	}

	public void setDesbloqueoAsistenciales(boolean desbloqueoAsistenciales) {
		this.desbloqueoAsistenciales = desbloqueoAsistenciales;
	}

	public String getfFin_ca_convocatoria() {
		return fFin_ca_convocatoria;
	}

	public void setfFin_ca_convocatoria(String fFin_ca_convocatoria) {
		this.fFin_ca_convocatoria = fFin_ca_convocatoria;
	}

	public String getBFechaFinPgp() {
		return BFechaFinPgp;
	}

	public void setBFechaFinPgp(String bFechaFinPgp) {
		BFechaFinPgp = bFechaFinPgp;
	}



	
	
}
