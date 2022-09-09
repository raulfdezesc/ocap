package es.jcyl.fqs.ocap.actionforms.componentesfqs;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;
import java.util.ArrayList;

public class OCAPComponentesfqsForm extends OCAPComunForm {

	private static final long serialVersionUID = 724301690271545996L;
	private long cCompfqs_id;
	private long cGerenciaId;
	private String dNombre;
	private String dApellidos;
	private String cDni;
	private String nTelefono1;
	private String aCorreoelectronico;
	private String aDir_nombre;
	private String aDir_num;
	private String aDir_escalera;
	private String aDir_piso;
	private String aDir_letra;
	private String cCp;
	private String cProv_id;
	private String cLocalidad_id;
	private String bSexo;
	private String cComunidad_id;
	private long cExptecompfqs_id;
	private String aTitulacion;
	private String aEspecialidad;
	private long cCte_id_ant;
	private String aForm_acreditacion;
	private String aForm_gestion;
	private String aForm_comunicacion;
	private String aForm_evaluacion;
	private String aProfesion;
	private String aDatosprofesionales;
	private String aCentrotrabajo;
	private String cComuni_id;
	private String cProvincia_id;
	private String aExpprof_ss;
	private String aExpcal_asistencia;
	private String aAct_docente;
	private long cGrado_id;
	private String dGrado_nombre;
	private String dComuni_nombre;
	private String dProv_nombre;
	private String dProv_nombre_post;
	private String aCategoria;
	private String aCargo;
	private ArrayList listadoConv;
	private String resumenConv;
	private String nAniosConv;
	private ArrayList listadoAct;
	private ArrayList listadoActMod;
	private ArrayList listadoActNoMod;
	private String resumenAct;
	private long cConvocatoria_id;
	private String dConvoc_nombre;
	private long cCte_id;
	private long cProfesional_id;
	private long cEspec_id;
	private long cItinerario_id;
	private String fVinculacion;
	private String fFinalizacion;
	private long cCompfqs_convo_id;
	private String dNombreCte;
	private String codigo_id;
	private String dEstado;
	private String bContestado;
	private String codigoEvaluador;
	private float porcIndicadoresOK;
	private float porcIndicadoresKO;
	private float porcIndicadorOK;
	private float porcIndicadorKO;
	private int totalEvaluados;
	private int totalIndicadores;
	private int totalIndicador;
	private int totalIndicadorOK;
	private int totalIndicadorKO;
	private int totalIndicadoresOK;
	private int totalIndicadoresKO;
	private float porcIndicadoresModOK;
	private float porcIndicadoresModKO;
	private float porcIndicadorModOK;
	private float porcIndicadorModKO;
	private int totalEvaluadosMod;
	private int totalIndicadoresMod;
	private int totalIndicadorMod;
	private int totalIndicadorModOK;
	private int totalIndicadorModKO;
	private int totalIndicadoresModOK;
	private int totalIndicadoresModKO;
	private float porcIndicadoresNoModOK;
	private float porcIndicadoresNoModKO;
	private float porcIndicadorNoModOK;
	private float porcIndicadorNoModKO;
	private int totalEvaluadosNoMod;
	private int totalIndicadoresNoMod;
	private int totalIndicadorNoMod;
	private int totalIndicadorNoModOK;
	private int totalIndicadorNoModKO;
	private int totalIndicadoresNoModOK;
	private int totalIndicadoresNoModKO;
	private int totalEvaluadosGRS;
	private int totalEvaluadosEnFaseIII;
	private int totalEvaluadosExcluidos;
	private String porcEvaluadosFQS;
	private String porcEvaluadosFQSOK;
	private String porcEvaluadosFQSKO;
	private String porcEvaluadosGRS;
	private String porcEvaluadosGRSOK;
	private String porcEvaluadosGRSKO;
	private ArrayList listaFQS;
	private ArrayList listaGRS;
	private ArrayList listaCategorias;
	private ArrayList listaEvaluadores;
	private ArrayList listaAuditorias;
	private ArrayList listaCTE;
	private ArrayList listaCTE2;
	private int totalEvaluadoresFQS;
	private int totalEvaluadoresBajaFQS;
	private String porcEvaluadoresBajaFQS;
	private int totalEvaluadoresFinFQS;
	private String porcEvaluadoresFinFQS;
	private int totalConformes;
	private int totalNoConformes;
	private int totalAuditorias;
	private String porcConformes;
	private String porcNoConformes;
	private ArrayList listaProfesionalesPendientes;
	private ArrayList listaProfesionalesPendientesAuditoria;
	private ArrayList listaProfesionalesEvaluados;
	private ArrayList listaPosiblesEvaluados;
	private long[] listaPosiblesEvaluadosSelec;

	public void setCCompfqs_id(long cCompfqs_id) {
		this.cCompfqs_id = cCompfqs_id;
	}

	public long getCCompfqs_id() {
		return this.cCompfqs_id;
	}

	public void setDNombre(String dNombre) {
		this.dNombre = dNombre;
	}

	public String getDNombre() {
		return this.dNombre;
	}

	public void setDApellidos(String dApellidos) {
		this.dApellidos = dApellidos;
	}
	public void setCGerenciaId(long cGerenciaId) {
		this.cGerenciaId = cGerenciaId;
	}

	public long getCGerenciaId() {
		return this.cGerenciaId;
	}
	public String getDApellidos() {
		return this.dApellidos;
	}

	public void setCDni(String cDni) {
		this.cDni = cDni;
	}

	public String getCDni() {
		return this.cDni;
	}

	public void setNTelefono1(String nTelefono1) {
		this.nTelefono1 = nTelefono1;
	}

	public String getNTelefono1() {
		return this.nTelefono1;
	}

	public void setACorreoelectronico(String aCorreoelectronico) {
		this.aCorreoelectronico = aCorreoelectronico;
	}

	public String getACorreoelectronico() {
		return this.aCorreoelectronico;
	}

	public void setCExptecompfqs_id(long cExptecompfqs_id) {
		this.cExptecompfqs_id = cExptecompfqs_id;
	}

	public long getCExptecompfqs_id() {
		return this.cExptecompfqs_id;
	}

	public void setCConvocatoria_id(long cConvocatoria_id) {
		this.cConvocatoria_id = cConvocatoria_id;
	}

	public long getCConvocatoria_id() {
		return this.cConvocatoria_id;
	}

	public void setDConvoc_nombre(String dConvoc_nombre) {
		this.dConvoc_nombre = dConvoc_nombre;
	}

	public String getDConvoc_nombre() {
		return this.dConvoc_nombre;
	}

	public void setAForm_acreditacion(String aForm_acreditacion) {
		this.aForm_acreditacion = aForm_acreditacion;
	}

	public String getAForm_acreditacion() {
		return this.aForm_acreditacion;
	}

	public void setAForm_gestion(String aForm_gestion) {
		this.aForm_gestion = aForm_gestion;
	}

	public String getAForm_gestion() {
		return this.aForm_gestion;
	}

	public void setAForm_comunicacion(String aForm_comunicacion) {
		this.aForm_comunicacion = aForm_comunicacion;
	}

	public String getAForm_comunicacion() {
		return this.aForm_comunicacion;
	}

	public void setAForm_evaluacion(String aForm_evaluacion) {
		this.aForm_evaluacion = aForm_evaluacion;
	}

	public String getAForm_evaluacion() {
		return this.aForm_evaluacion;
	}

	public void setADir_nombre(String aDir_nombre) {
		this.aDir_nombre = aDir_nombre;
	}

	public String getADir_nombre() {
		return this.aDir_nombre;
	}

	public void setADir_num(String aDir_num) {
		this.aDir_num = aDir_num;
	}

	public String getADir_num() {
		return this.aDir_num;
	}

	public void setADir_escalera(String aDir_escalera) {
		this.aDir_escalera = aDir_escalera;
	}

	public String getADir_escalera() {
		return this.aDir_escalera;
	}

	public void setADir_piso(String aDir_piso) {
		this.aDir_piso = aDir_piso;
	}

	public String getADir_piso() {
		return this.aDir_piso;
	}

	public void setADir_letra(String aDir_letra) {
		this.aDir_letra = aDir_letra;
	}

	public String getADir_letra() {
		return this.aDir_letra;
	}

	public void setCCp(String cCp) {
		this.cCp = cCp;
	}

	public String getCCp() {
		return this.cCp;
	}

	public void setCProv_id(String cProv_id) {
		this.cProv_id = cProv_id;
	}

	public String getCProv_id() {
		return this.cProv_id;
	}

	public void setCLocalidad_id(String cLocalidad_id) {
		this.cLocalidad_id = cLocalidad_id;
	}

	public String getCLocalidad_id() {
		return this.cLocalidad_id;
	}

	public void setCCte_id(long cCte_id) {
		this.cCte_id = cCte_id;
	}

	public long getCCte_id() {
		return this.cCte_id;
	}

	public void setAProfesion(String aProfesion) {
		this.aProfesion = aProfesion;
	}

	public String getAProfesion() {
		return this.aProfesion;
	}

	public void setADatosprofesionales(String aDatosprofesionales) {
		this.aDatosprofesionales = aDatosprofesionales;
	}

	public String getADatosprofesionales() {
		return this.aDatosprofesionales;
	}

	public void setFVinculacion(String fVinculacion) {
		this.fVinculacion = fVinculacion;
	}

	public String getFVinculacion() {
		return this.fVinculacion;
	}

	public void setFFinalizacion(String fFinalizacion) {
		this.fFinalizacion = fFinalizacion;
	}

	public String getFFinalizacion() {
		return this.fFinalizacion;
	}

	public void setCCte_id_ant(long cCte_id_ant) {
		this.cCte_id_ant = cCte_id_ant;
	}

	public long getCCte_id_ant() {
		return this.cCte_id_ant;
	}

	public void setBSexo(String bSexo) {
		this.bSexo = bSexo;
	}

	public String getBSexo() {
		return this.bSexo;
	}

	public void setATitulacion(String aTitulacion) {
		this.aTitulacion = aTitulacion;
	}

	public String getATitulacion() {
		return this.aTitulacion;
	}

	public void setAEspecialidad(String aEspecialidad) {
		this.aEspecialidad = aEspecialidad;
	}

	public String getAEspecialidad() {
		return this.aEspecialidad;
	}

	public void setACentrotrabajo(String aCentrotrabajo) {
		this.aCentrotrabajo = aCentrotrabajo;
	}

	public String getACentrotrabajo() {
		return this.aCentrotrabajo;
	}

	public void setCComuni_id(String cComuni_id) {
		this.cComuni_id = cComuni_id;
	}

	public String getCComuni_id() {
		return this.cComuni_id;
	}

	public void setCProvincia_id(String cProvincia_id) {
		this.cProvincia_id = cProvincia_id;
	}

	public String getCProvincia_id() {
		return this.cProvincia_id;
	}

	public void setAExpprof_ss(String aExpprof_ss) {
		this.aExpprof_ss = aExpprof_ss;
	}

	public String getAExpprof_ss() {
		return this.aExpprof_ss;
	}

	public void setAExpcal_asistencia(String aExpcal_asistencia) {
		this.aExpcal_asistencia = aExpcal_asistencia;
	}

	public String getAExpcal_asistencia() {
		return this.aExpcal_asistencia;
	}

	public void setAAct_docente(String aAct_docente) {
		this.aAct_docente = aAct_docente;
	}

	public String getAAct_docente() {
		return this.aAct_docente;
	}

	public void setCGrado_id(long cGrado_id) {
		this.cGrado_id = cGrado_id;
	}

	public long getCGrado_id() {
		return this.cGrado_id;
	}

	public void setDGrado_nombre(String dGrado_nombre) {
		this.dGrado_nombre = dGrado_nombre;
	}

	public String getDGrado_nombre() {
		return this.dGrado_nombre;
	}

	public void setDComuni_nombre(String dComuni_nombre) {
		this.dComuni_nombre = dComuni_nombre;
	}

	public String getDComuni_nombre() {
		return this.dComuni_nombre;
	}

	public void setDProv_nombre(String dProv_nombre) {
		this.dProv_nombre = dProv_nombre;
	}

	public String getDProv_nombre() {
		return this.dProv_nombre;
	}

	public void setACategoria(String aCategoria) {
		this.aCategoria = aCategoria;
	}

	public String getACategoria() {
		return this.aCategoria;
	}

	public void setACargo(String aCargo) {
		this.aCargo = aCargo;
	}

	public String getACargo() {
		return this.aCargo;
	}

	public void setDProv_nombre_post(String dProv_nombre_post) {
		this.dProv_nombre_post = dProv_nombre_post;
	}

	public String getDProv_nombre_post() {
		return this.dProv_nombre_post;
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

	public void setCItinerario_id(long cItinerario_id) {
		this.cItinerario_id = cItinerario_id;
	}

	public long getCItinerario_id() {
		return this.cItinerario_id;
	}

	public void setListadoConv(ArrayList listadoConv) {
		this.listadoConv = listadoConv;
	}

	public ArrayList getListadoConv() {
		return this.listadoConv;
	}

	public void setResumenConv(String resumenConv) {
		this.resumenConv = resumenConv;
	}

	public String getResumenConv() {
		return this.resumenConv;
	}

	public void setNAniosConv(String nAniosConv) {
		this.nAniosConv = nAniosConv;
	}

	public String getNAniosConv() {
		return this.nAniosConv;
	}

	public void setListadoAct(ArrayList listadoAct) {
		this.listadoAct = listadoAct;
	}

	public ArrayList getListadoAct() {
		return this.listadoAct;
	}

	public void setResumenAct(String resumenAct) {
		this.resumenAct = resumenAct;
	}

	public String getResumenAct() {
		return this.resumenAct;
	}

	public void setCCompfqs_convo_id(long cCompfqs_convo_id) {
		this.cCompfqs_convo_id = cCompfqs_convo_id;
	}

	public long getCCompfqs_convo_id() {
		return this.cCompfqs_convo_id;
	}

	public void setCodigo_id(String codigoId) {
		this.codigo_id = codigoId;
	}

	public String getCodigo_id() {
		return this.codigo_id;
	}

	public void setDEstado(String dEstado) {
		this.dEstado = dEstado;
	}

	public String getDEstado() {
		return this.dEstado;
	}

	public void setCComunidad_id(String cComunidad_id) {
		this.cComunidad_id = cComunidad_id;
	}

	public String getCComunidad_id() {
		return this.cComunidad_id;
	}

	public void setCodigoEvaluador(String codigoEvaluador) {
		this.codigoEvaluador = codigoEvaluador;
	}

	public String getCodigoEvaluador() {
		return this.codigoEvaluador;
	}

	public void setDNombreCte(String dNombreCte) {
		this.dNombreCte = dNombreCte;
	}

	public String getDNombreCte() {
		return this.dNombreCte;
	}

	public void setBContestado(String bContestado) {
		this.bContestado = bContestado;
	}

	public String getBContestado() {
		return this.bContestado;
	}

	public void setPorcIndicadoresOK(float porcIndicadoresOK) {
		this.porcIndicadoresOK = porcIndicadoresOK;
	}

	public float getPorcIndicadoresOK() {
		return this.porcIndicadoresOK;
	}

	public void setPorcIndicadoresKO(float porcIndicadoresKO) {
		this.porcIndicadoresKO = porcIndicadoresKO;
	}

	public float getPorcIndicadoresKO() {
		return this.porcIndicadoresKO;
	}

	public void setPorcIndicadorOK(float porcIndicadorOK) {
		this.porcIndicadorOK = porcIndicadorOK;
	}

	public float getPorcIndicadorOK() {
		return this.porcIndicadorOK;
	}

	public void setPorcIndicadorKO(float porcIndicadorKO) {
		this.porcIndicadorKO = porcIndicadorKO;
	}

	public float getPorcIndicadorKO() {
		return this.porcIndicadorKO;
	}

	public void setTotalIndicadores(int totalIndicadores) {
		this.totalIndicadores = totalIndicadores;
	}

	public int getTotalIndicadores() {
		return this.totalIndicadores;
	}

	public void setTotalIndicador(int totalIndicador) {
		this.totalIndicador = totalIndicador;
	}

	public int getTotalIndicador() {
		return this.totalIndicador;
	}

	public void setTotalIndicadorOK(int totalIndicadorOK) {
		this.totalIndicadorOK = totalIndicadorOK;
	}

	public int getTotalIndicadorOK() {
		return this.totalIndicadorOK;
	}

	public void setTotalIndicadorKO(int totalIndicadorKO) {
		this.totalIndicadorKO = totalIndicadorKO;
	}

	public int getTotalIndicadorKO() {
		return this.totalIndicadorKO;
	}

	public void setTotalIndicadoresOK(int totalIndicadoresOK) {
		this.totalIndicadoresOK = totalIndicadoresOK;
	}

	public int getTotalIndicadoresOK() {
		return this.totalIndicadoresOK;
	}

	public void setTotalIndicadoresKO(int totalIndicadoresKO) {
		this.totalIndicadoresKO = totalIndicadoresKO;
	}

	public int getTotalIndicadoresKO() {
		return this.totalIndicadoresKO;
	}

	public void setTotalEvaluados(int totalEvaluados) {
		this.totalEvaluados = totalEvaluados;
	}

	public int getTotalEvaluados() {
		return this.totalEvaluados;
	}

	public void setPorcIndicadoresModOK(float porcIndicadoresModOK) {
		this.porcIndicadoresModOK = porcIndicadoresModOK;
	}

	public float getPorcIndicadoresModOK() {
		return this.porcIndicadoresModOK;
	}

	public void setPorcIndicadoresModKO(float porcIndicadoresModKO) {
		this.porcIndicadoresModKO = porcIndicadoresModKO;
	}

	public float getPorcIndicadoresModKO() {
		return this.porcIndicadoresModKO;
	}

	public void setPorcIndicadorModOK(float porcIndicadorModOK) {
		this.porcIndicadorModOK = porcIndicadorModOK;
	}

	public float getPorcIndicadorModOK() {
		return this.porcIndicadorModOK;
	}

	public void setPorcIndicadorModKO(float porcIndicadorModKO) {
		this.porcIndicadorModKO = porcIndicadorModKO;
	}

	public float getPorcIndicadorModKO() {
		return this.porcIndicadorModKO;
	}

	public void setTotalEvaluadosMod(int totalEvaluadosMod) {
		this.totalEvaluadosMod = totalEvaluadosMod;
	}

	public int getTotalEvaluadosMod() {
		return this.totalEvaluadosMod;
	}

	public void setTotalIndicadoresMod(int totalIndicadoresMod) {
		this.totalIndicadoresMod = totalIndicadoresMod;
	}

	public int getTotalIndicadoresMod() {
		return this.totalIndicadoresMod;
	}

	public void setTotalIndicadorMod(int totalIndicadorMod) {
		this.totalIndicadorMod = totalIndicadorMod;
	}

	public int getTotalIndicadorMod() {
		return this.totalIndicadorMod;
	}

	public void setTotalIndicadorModOK(int totalIndicadorModOK) {
		this.totalIndicadorModOK = totalIndicadorModOK;
	}

	public int getTotalIndicadorModOK() {
		return this.totalIndicadorModOK;
	}

	public void setTotalIndicadorModKO(int totalIndicadorModKO) {
		this.totalIndicadorModKO = totalIndicadorModKO;
	}

	public int getTotalIndicadorModKO() {
		return this.totalIndicadorModKO;
	}

	public void setTotalIndicadoresModOK(int totalIndicadoresModOK) {
		this.totalIndicadoresModOK = totalIndicadoresModOK;
	}

	public int getTotalIndicadoresModOK() {
		return this.totalIndicadoresModOK;
	}

	public void setTotalIndicadoresModKO(int totalIndicadoresModKO) {
		this.totalIndicadoresModKO = totalIndicadoresModKO;
	}

	public int getTotalIndicadoresModKO() {
		return this.totalIndicadoresModKO;
	}

	public void setListadoActMod(ArrayList listadoActMod) {
		this.listadoActMod = listadoActMod;
	}

	public ArrayList getListadoActMod() {
		return this.listadoActMod;
	}

	public void setListadoActNoMod(ArrayList listadoActNoMod) {
		this.listadoActNoMod = listadoActNoMod;
	}

	public ArrayList getListadoActNoMod() {
		return this.listadoActNoMod;
	}

	public void setPorcIndicadoresNoModOK(float porcIndicadoresNoModOK) {
		this.porcIndicadoresNoModOK = porcIndicadoresNoModOK;
	}

	public float getPorcIndicadoresNoModOK() {
		return this.porcIndicadoresNoModOK;
	}

	public void setPorcIndicadoresNoModKO(float porcIndicadoresNoModKO) {
		this.porcIndicadoresNoModKO = porcIndicadoresNoModKO;
	}

	public float getPorcIndicadoresNoModKO() {
		return this.porcIndicadoresNoModKO;
	}

	public void setPorcIndicadorNoModOK(float porcIndicadorNoModOK) {
		this.porcIndicadorNoModOK = porcIndicadorNoModOK;
	}

	public float getPorcIndicadorNoModOK() {
		return this.porcIndicadorNoModOK;
	}

	public void setPorcIndicadorNoModKO(float porcIndicadorNoModKO) {
		this.porcIndicadorNoModKO = porcIndicadorNoModKO;
	}

	public float getPorcIndicadorNoModKO() {
		return this.porcIndicadorNoModKO;
	}

	public void setTotalEvaluadosNoMod(int totalEvaluadosNoMod) {
		this.totalEvaluadosNoMod = totalEvaluadosNoMod;
	}

	public int getTotalEvaluadosNoMod() {
		return this.totalEvaluadosNoMod;
	}

	public void setTotalIndicadoresNoMod(int totalIndicadoresNoMod) {
		this.totalIndicadoresNoMod = totalIndicadoresNoMod;
	}

	public int getTotalIndicadoresNoMod() {
		return this.totalIndicadoresNoMod;
	}

	public void setTotalIndicadorNoMod(int totalIndicadorNoMod) {
		this.totalIndicadorNoMod = totalIndicadorNoMod;
	}

	public int getTotalIndicadorNoMod() {
		return this.totalIndicadorNoMod;
	}

	public void setTotalIndicadorNoModOK(int totalIndicadorNoModOK) {
		this.totalIndicadorNoModOK = totalIndicadorNoModOK;
	}

	public int getTotalIndicadorNoModOK() {
		return this.totalIndicadorNoModOK;
	}

	public void setTotalIndicadorNoModKO(int totalIndicadorNoModKO) {
		this.totalIndicadorNoModKO = totalIndicadorNoModKO;
	}

	public int getTotalIndicadorNoModKO() {
		return this.totalIndicadorNoModKO;
	}

	public void setTotalIndicadoresNoModOK(int totalIndicadoresNoModOK) {
		this.totalIndicadoresNoModOK = totalIndicadoresNoModOK;
	}

	public int getTotalIndicadoresNoModOK() {
		return this.totalIndicadoresNoModOK;
	}

	public void setTotalIndicadoresNoModKO(int totalIndicadoresNoModKO) {
		this.totalIndicadoresNoModKO = totalIndicadoresNoModKO;
	}

	public int getTotalIndicadoresNoModKO() {
		return this.totalIndicadoresNoModKO;
	}

	public void setTotalEvaluadosGRS(int totalEvaluadosGRS) {
		this.totalEvaluadosGRS = totalEvaluadosGRS;
	}

	public int getTotalEvaluadosGRS() {
		return this.totalEvaluadosGRS;
	}

	public void setTotalEvaluadosEnFaseIII(int totalEvaluadosEnFaseIII) {
		this.totalEvaluadosEnFaseIII = totalEvaluadosEnFaseIII;
	}

	public int getTotalEvaluadosEnFaseIII() {
		return this.totalEvaluadosEnFaseIII;
	}

	public void setTotalEvaluadosExcluidos(int totalEvaluadosExcluidos) {
		this.totalEvaluadosExcluidos = totalEvaluadosExcluidos;
	}

	public int getTotalEvaluadosExcluidos() {
		return this.totalEvaluadosExcluidos;
	}

	public void setPorcEvaluadosFQSOK(String porcEvaluadosFQSOK) {
		this.porcEvaluadosFQSOK = porcEvaluadosFQSOK;
	}

	public String getPorcEvaluadosFQSOK() {
		return this.porcEvaluadosFQSOK;
	}

	public void setPorcEvaluadosFQSKO(String porcEvaluadosFQSKO) {
		this.porcEvaluadosFQSKO = porcEvaluadosFQSKO;
	}

	public String getPorcEvaluadosFQSKO() {
		return this.porcEvaluadosFQSKO;
	}

	public void setPorcEvaluadosGRSOK(String porcEvaluadosGRSOK) {
		this.porcEvaluadosGRSOK = porcEvaluadosGRSOK;
	}

	public String getPorcEvaluadosGRSOK() {
		return this.porcEvaluadosGRSOK;
	}

	public void setPorcEvaluadosGRSKO(String porcEvaluadosGRSKO) {
		this.porcEvaluadosGRSKO = porcEvaluadosGRSKO;
	}

	public String getPorcEvaluadosGRSKO() {
		return this.porcEvaluadosGRSKO;
	}

	public void setListaFQS(ArrayList listaFQS) {
		this.listaFQS = listaFQS;
	}

	public ArrayList getListaFQS() {
		return this.listaFQS;
	}

	public void setListaGRS(ArrayList listaGRS) {
		this.listaGRS = listaGRS;
	}

	public ArrayList getListaGRS() {
		return this.listaGRS;
	}

	public void setPorcEvaluadosFQS(String porcEvaluadosFQS) {
		this.porcEvaluadosFQS = porcEvaluadosFQS;
	}

	public String getPorcEvaluadosFQS() {
		return this.porcEvaluadosFQS;
	}

	public void setPorcEvaluadosGRS(String porcEvaluadosGRS) {
		this.porcEvaluadosGRS = porcEvaluadosGRS;
	}

	public String getPorcEvaluadosGRS() {
		return this.porcEvaluadosGRS;
	}

	public void setTotalEvaluadoresFQS(int totalEvaluadoresFQS) {
		this.totalEvaluadoresFQS = totalEvaluadoresFQS;
	}

	public int getTotalEvaluadoresFQS() {
		return this.totalEvaluadoresFQS;
	}

	public void setTotalEvaluadoresBajaFQS(int totalEvaluadoresBajaFQS) {
		this.totalEvaluadoresBajaFQS = totalEvaluadoresBajaFQS;
	}

	public int getTotalEvaluadoresBajaFQS() {
		return this.totalEvaluadoresBajaFQS;
	}

	public void setTotalEvaluadoresFinFQS(int totalEvaluadoresFinFQS) {
		this.totalEvaluadoresFinFQS = totalEvaluadoresFinFQS;
	}

	public int getTotalEvaluadoresFinFQS() {
		return this.totalEvaluadoresFinFQS;
	}

	public void setPorcEvaluadoresBajaFQS(String porcEvaluadoresBajaFQS) {
		this.porcEvaluadoresBajaFQS = porcEvaluadoresBajaFQS;
	}

	public String getPorcEvaluadoresBajaFQS() {
		return this.porcEvaluadoresBajaFQS;
	}

	public void setPorcEvaluadoresFinFQS(String porcEvaluadoresFinFQS) {
		this.porcEvaluadoresFinFQS = porcEvaluadoresFinFQS;
	}

	public String getPorcEvaluadoresFinFQS() {
		return this.porcEvaluadoresFinFQS;
	}

	public void setListaEvaluadores(ArrayList listaEvaluadores) {
		this.listaEvaluadores = listaEvaluadores;
	}

	public ArrayList getListaEvaluadores() {
		return this.listaEvaluadores;
	}

	public void setListaCategorias(ArrayList listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public ArrayList getListaCategorias() {
		return this.listaCategorias;
	}

	public void setTotalConformes(int totalConformes) {
		this.totalConformes = totalConformes;
	}

	public int getTotalConformes() {
		return this.totalConformes;
	}

	public void setTotalNoConformes(int totalNoConformes) {
		this.totalNoConformes = totalNoConformes;
	}

	public int getTotalNoConformes() {
		return this.totalNoConformes;
	}

	public void setTotalAuditorias(int totalAuditorias) {
		this.totalAuditorias = totalAuditorias;
	}

	public int getTotalAuditorias() {
		return this.totalAuditorias;
	}

	public void setListaAuditorias(ArrayList listaAuditorias) {
		this.listaAuditorias = listaAuditorias;
	}

	public ArrayList getListaAuditorias() {
		return this.listaAuditorias;
	}

	public void setPorcConformes(String porcConformes) {
		this.porcConformes = porcConformes;
	}

	public String getPorcConformes() {
		return this.porcConformes;
	}

	public void setPorcNoConformes(String porcNoConformes) {
		this.porcNoConformes = porcNoConformes;
	}

	public String getPorcNoConformes() {
		return this.porcNoConformes;
	}

	public void setListaCTE(ArrayList listaCTE) {
		this.listaCTE = listaCTE;
	}

	public ArrayList getListaCTE() {
		return this.listaCTE;
	}

	public void setListaCTE2(ArrayList listaCTE2) {
		this.listaCTE2 = listaCTE2;
	}

	public ArrayList getListaCTE2() {
		return this.listaCTE2;
	}

	public void setListaProfesionalesPendientes(ArrayList listaProfesionalesPendientes) {
		this.listaProfesionalesPendientes = listaProfesionalesPendientes;
	}

	public ArrayList getListaProfesionalesPendientes() {
		return this.listaProfesionalesPendientes;
	}

	public void setListaProfesionalesPendientesAuditoria(ArrayList listaProfesionalesPendientesAuditoria) {
		this.listaProfesionalesPendientesAuditoria = listaProfesionalesPendientesAuditoria;
	}

	public ArrayList getListaProfesionalesPendientesAuditoria() {
		return this.listaProfesionalesPendientesAuditoria;
	}

	public void setListaProfesionalesEvaluados(ArrayList listaProfesionalesEvaluados) {
		this.listaProfesionalesEvaluados = listaProfesionalesEvaluados;
	}

	public ArrayList getListaProfesionalesEvaluados() {
		return this.listaProfesionalesEvaluados;
	}

	public void setListaPosiblesEvaluados(ArrayList listaPosiblesEvaluados) {
		this.listaPosiblesEvaluados = listaPosiblesEvaluados;
	}

	public ArrayList getListaPosiblesEvaluados() {
		return this.listaPosiblesEvaluados;
	}

	public void setListaPosiblesEvaluadosSelec(long[] listaPosiblesEvaluadosSelec) {
		this.listaPosiblesEvaluadosSelec = listaPosiblesEvaluadosSelec;
	}

	public long[] getListaPosiblesEvaluadosSelec() {
		return this.listaPosiblesEvaluadosSelec;
	}
}
