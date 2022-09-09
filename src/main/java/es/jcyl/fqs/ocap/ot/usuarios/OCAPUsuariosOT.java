 package es.jcyl.fqs.ocap.ot.usuarios;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Date;
 
 public class OCAPUsuariosOT
   implements Serializable
 {
   private String aPassword;
   private String dNombre;
   private Long cUsrId;
   private Date fCreacion;
   private Integer cGerenciaId;
   private String dApellido1;
   private Integer cCentrotrabajoId;
   private Integer cEspecId;
   private String dEspec_nombre;
   private Integer cPerfilId;
   private Integer cProfesionalId;
   private String dProfesional_nombre;
   private Integer cEstatutId;
   private String dEstatut_nombre;
   private String dCorreoelectronico;
   private String cDni;
   private String cDniReal;
   private String bBorrado;
   private Date fPlazapropiedad;
   private String bSexo;
   private Integer nTelefono1;
   private Integer nTelefono2;
   private String dDomicilio;
   private String cProvinciaUsu_id;
   private String dProvinciaUsu;
   private String dLocalidadUsu;
   private String cPostalUsu;
   private String dProvincia;
   private String dGrado_des;
   private long cGrado_id;
   private long cConvocatoriaId;
   private String dTipogerencia_desc;
   private String dGerencia_nombre;
   private String dCentrotrabajo_nombre;
   private long cExpmc_id;
   private Long cExpId;
   private long cEstadoExpId;
   private String dMensaje;
   private ArrayList listaCreditos;
   private ArrayList listaMeritos;
   private ArrayList listaMeritosOpcionales;
   private ArrayList listaGrados;
   private ArrayList listaCategorias;
   private ArrayList listaItinerarios;
   private ArrayList listaIndicadores;
   private ArrayList listaIndicadoresMod;
   private ArrayList listaIndicadoresNoMod;
   private ArrayList listaProfesionales;
   private ArrayList listaProfesionalesPendientes;
   private ArrayList listaProfesionalesPendientesAuditoria;
   private ArrayList listaProfesionalesEvaluados;
   private Date fAceptacSolic;
   private Date fInicioEvaluacion;
   private Date fIncioMC;
   private Date fFinMC;
   private Date fAceptacionCEIS;
   private Date fAceptacionCC;
   private Date fNegacionMC;
   private Date fRespuestainconfMC;
   private Date fInicioEvalMC;
   private Date fFinEvalMC;
   private String bValidacionCC;
   private Date fIncioCA;
   private Date fFinCA;
   private String aModificadoPor;
   private String aCreadoPor;
   private long cCompfqsId;
   private String cProcedimientoId;
   private String dProcedimiento;
   private Date fRegDocEscaneados;
   private Date fRegEvidenciasConf;
   private String bPdtesAclarar;
   private Long cServicioId;
   private long cItinerarioId;
   private String dItinerario;
   private String codigoId;
   private String dEstado;
   private Date fInformeCte;
   private Date fInformeCe;
   private Date fInformeEval;
   private Date fInformeCC;
   private Date fInformeEval2;
   private Date fAuditoriaPropuesta;
   private String bAuditoria;
   private String bNuevaRevision;
   private Date fReunionCE;
   private Date fReunionCTE;
   private String AEspecificacionesCC;
   private String AEspecificacionesCE;
   private String AEspecificacionesCTE;
   private String AEspecificacionesEval;
   private String ADiscrepanciasCE;
   private String ADiscrepanciasCTE;
   private String bContestado;
   private String dDescripcion;
   private String opcion;
   private int totalEvaluados;
   private int totalIndicador;
   private int totalIndicadorOK;
   private int totalIndicadorKO;
   private int totalIndicadores;
   private int totalIndicadoresOK;
   private int totalIndicadoresKO;
   private float porcIndicadoresOK;
   private float porcIndicadoresKO;
   private float porcIndicadorOK;
   private float porcIndicadorKO;
   private float porcIndicador;
   private String bReconocimientoGrado;
   private int totalEvaluadosMod;
   private int totalIndicadorMod;
   private int totalIndicadorModOK;
   private int totalIndicadorModKO;
   private int totalIndicadoresMod;
   private int totalIndicadoresModOK;
   private int totalIndicadoresModKO;
   private float porcIndicadoresModOK;
   private float porcIndicadoresModKO;
   private float porcIndicadorModOK;
   private float porcIndicadorModKO;
   private float porcIndicadorMod;
   private String porcentajeIndicador;
   private String porcentajeIndicadores;
   private String porcentajeIndicadoresOK;
   private String porcentajeIndicadoresKO;
   private String porcentajeIndicadoresModOK;
   private String porcentajeIndicadoresModKO;
   private int totalEvaluadosNoMod;
   private int totalIndicadorNoMod;
   private int totalIndicadorNoModOK;
   private int totalIndicadorNoModKO;
   private int totalIndicadoresNoMod;
   private int totalIndicadoresNoModOK;
   private int totalIndicadoresNoModKO;
   private float porcIndicadoresNoModOK;
   private float porcIndicadoresNoModKO;
   private float porcIndicadorNoModOK;
   private float porcIndicadorNoModKO;
   private float porcIndicadorNoMod;
   private long nCreditosEvaluador;
   private long nCreditosNecesarios;
   private boolean bModEvaluador;
   private boolean bConformeCTE;
   private String bConformidadCTE;
   private String dNombreCte;
   private long cCteId;
   private boolean bDecisionCE;
   private String bDecisionCEStr;
   private String bSegundaEvaluacion;
   private long numSolicitudes;
   private double nCreditosEvaluadosCTE;
   
   private String dConvocatoriaNombreCorto;
 
   public void setAPassword(String aPassword)
   {
     this.aPassword = aPassword;
   }
 
   public String getAPassword()
   {
     return this.aPassword;
   }
 
   public void setDNombre(String dNombre) {
     this.dNombre = dNombre;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setCUsrId(Long cUsrId) {
     this.cUsrId = cUsrId;
   }
 
   public Long getCUsrId()
   {
     return this.cUsrId;
   }
 
   public void setFCreacion(Date fCreacion) {
     this.fCreacion = fCreacion;
   }
 
   public Date getFCreacion()
   {
     return this.fCreacion;
   }
 
   public void setCGerenciaId(Integer cGerenciaId) {
     this.cGerenciaId = cGerenciaId;
   }
 
   public Integer getCGerenciaId()
   {
     return this.cGerenciaId;
   }
 
   public void setDApellido1(String dApellido1) {
     this.dApellido1 = dApellido1;
   }
 
   public String getDApellido1()
   {
     return this.dApellido1;
   }
 
   public void setCCentrotrabajoId(Integer cCentrotrabajoId) {
     this.cCentrotrabajoId = cCentrotrabajoId;
   }
 
   public Integer getCCentrotrabajoId()
   {
     return this.cCentrotrabajoId;
   }
 
   public void setCEspecId(Integer cEspecId) {
     this.cEspecId = cEspecId;
   }
 
   public Integer getCEspecId()
   {
     return this.cEspecId;
   }
 
   public void setDEspec_nombre(String dEspec_nombre) {
     this.dEspec_nombre = dEspec_nombre;
   }
 
   public String getDEspec_nombre()
   {
     return this.dEspec_nombre;
   }
 
   public void setCPerfilId(Integer cPerfilId) {
     this.cPerfilId = cPerfilId;
   }
 
   public Integer getCPerfilId()
   {
     return this.cPerfilId;
   }
 
   public void setCProfesionalId(Integer cProfesionalId) {
     this.cProfesionalId = cProfesionalId;
   }
 
   public Integer getCProfesionalId()
   {
     return this.cProfesionalId;
   }
 
   public void setDProfesional_nombre(String dProfesional_nombre) {
     this.dProfesional_nombre = dProfesional_nombre;
   }
 
   public String getDProfesional_nombre()
   {
     return this.dProfesional_nombre;
   }
 
   public void setCEstatutId(Integer cEstatutId) {
     this.cEstatutId = cEstatutId;
   }
 
   public Integer getCEstatutId()
   {
     return this.cEstatutId;
   }
 
   public void setDEstatut_nombre(String dEstatut_nombre) {
     this.dEstatut_nombre = dEstatut_nombre;
   }
 
   public String getDEstatut_nombre()
   {
     return this.dEstatut_nombre;
   }
 
   public void setDCorreoelectronico(String dCorreoelectronico) {
     this.dCorreoelectronico = dCorreoelectronico;
   }
 
   public String getDCorreoelectronico()
   {
     return this.dCorreoelectronico;
   }
 
   public void setCDni(String cDni) {
     this.cDni = cDni;
   }
 
   public String getCDni()
   {
     return this.cDni;
   }
 
   public void setCDniReal(String cDniReal) {
     this.cDniReal = cDniReal;
   }
 
   public String getCDniReal()
   {
     return this.cDniReal;
   }
 
   public void setBBorrado(String bBorrado) {
     this.bBorrado = bBorrado;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setFPlazapropiedad(Date fPlazapropiedad) {
     this.fPlazapropiedad = fPlazapropiedad;
   }
 
   public Date getFPlazapropiedad()
   {
     return this.fPlazapropiedad;
   }
 
   public void setBSexo(String bSexo) {
     this.bSexo = bSexo;
   }
 
   public String getBSexo()
   {
     return this.bSexo;
   }
 
   public void setNTelefono1(Integer nTelefono1) {
     this.nTelefono1 = nTelefono1;
   }
 
   public Integer getNTelefono1()
   {
     return this.nTelefono1;
   }
 
   public void setNTelefono2(Integer nTelefono2) {
     this.nTelefono2 = nTelefono2;
   }
 
   public Integer getNTelefono2()
   {
     return this.nTelefono2;
   }
 
   public void setDDomicilio(String dDomicilio) {
     this.dDomicilio = dDomicilio;
   }
 
   public String getDDomicilio()
   {
     return this.dDomicilio;
   }
 
   public void setCProvinciaUsu_id(String cProvinciaUsu_id) {
     this.cProvinciaUsu_id = cProvinciaUsu_id;
   }
 
   public String getCProvinciaUsu_id()
   {
     return this.cProvinciaUsu_id;
   }
 
   public void setDProvinciaUsu(String dProvinciaUsu) {
     this.dProvinciaUsu = dProvinciaUsu;
   }
 
   public String getDProvinciaUsu()
   {
     return this.dProvinciaUsu;
   }
 
   public void setDLocalidadUsu(String dLocalidadUsu) {
     this.dLocalidadUsu = dLocalidadUsu;
   }
 
   public String getDLocalidadUsu()
   {
     return this.dLocalidadUsu;
   }
 
   public void setCPostalUsu(String cPostalUsu) {
     this.cPostalUsu = cPostalUsu;
   }
 
   public String getCPostalUsu()
   {
     return this.cPostalUsu;
   }
 
   public void setDProvincia(String dProvincia) {
     this.dProvincia = dProvincia;
   }
 
   public String getDProvincia()
   {
     return this.dProvincia;
   }
 
   public void setDGrado_des(String dGrado_des) {
     this.dGrado_des = dGrado_des;
   }
 
   public String getDGrado_des()
   {
     return this.dGrado_des;
   }
 
   public void setCGrado_id(long cGrado_id) {
     this.cGrado_id = cGrado_id;
   }
 
   public long getCGrado_id()
   {
     return this.cGrado_id;
   }
 
   public void setCConvocatoriaId(long cConvocatoriaId) {
     this.cConvocatoriaId = cConvocatoriaId;
   }
 
   public long getCConvocatoriaId()
   {
     return this.cConvocatoriaId;
   }
 
   public void setDTipogerencia_desc(String dTipogerencia_desc) {
     this.dTipogerencia_desc = dTipogerencia_desc;
   }
 
   public String getDTipogerencia_desc()
   {
     return this.dTipogerencia_desc;
   }
 
   public void setDGerencia_nombre(String dGerencia_nombre) {
     this.dGerencia_nombre = dGerencia_nombre;
   }
 
   public String getDGerencia_nombre()
   {
     return this.dGerencia_nombre;
   }
 
   public void setDCentrotrabajo_nombre(String dCentrotrabajo_nombre) {
     this.dCentrotrabajo_nombre = dCentrotrabajo_nombre;
   }
 
   public String getDCentrotrabajo_nombre()
   {
     return this.dCentrotrabajo_nombre;
   }
 
   public void setCExpmc_id(long cExpmc_id) {
     this.cExpmc_id = cExpmc_id;
   }
 
   public long getCExpmc_id()
   {
     return this.cExpmc_id;
   }
 
   public void setCExpId(Long cExpId) {
     this.cExpId = cExpId;
   }
 
   public Long getCExpId()
   {
     return this.cExpId;
   }
 
   public void setDMensaje(String dMensaje) {
     this.dMensaje = dMensaje;
   }
 
   public String getDMensaje()
   {
     return this.dMensaje;
   }
 
   public void setListaCreditos(ArrayList listaCreditos) {
     this.listaCreditos = listaCreditos;
   }
 
   public ArrayList getListaCreditos()
   {
     return this.listaCreditos;
   }
 
   public void setListaMeritos(ArrayList listaMeritos) {
     this.listaMeritos = listaMeritos;
   }
 
   public ArrayList getListaMeritos()
   {
     return this.listaMeritos;
   }
 
   public void setListaMeritosOpcionales(ArrayList listaMeritosOpcionales) {
     this.listaMeritosOpcionales = listaMeritosOpcionales;
   }
 
   public ArrayList getListaMeritosOpcionales()
   {
     return this.listaMeritosOpcionales;
   }
 
   public void setListaGrados(ArrayList listaGrados) {
     this.listaGrados = listaGrados;
   }
 
   public ArrayList getListaGrados()
   {
     return this.listaGrados;
   }
 
   public void setListaCategorias(ArrayList listaCategorias) {
     this.listaCategorias = listaCategorias;
   }
 
   public ArrayList getListaCategorias()
   {
     return this.listaCategorias;
   }
 
   public void setListaItinerarios(ArrayList listaItinerarios) {
     this.listaItinerarios = listaItinerarios;
   }
 
   public ArrayList getListaItinerarios()
   {
     return this.listaItinerarios;
   }
 
   public void setListaIndicadores(ArrayList listaIndicadores) {
     this.listaIndicadores = listaIndicadores;
   }
 
   public ArrayList getListaIndicadores()
   {
     return this.listaIndicadores;
   }
 
   public void setListaIndicadoresMod(ArrayList listaIndicadoresMod) {
     this.listaIndicadoresMod = listaIndicadoresMod;
   }
 
   public ArrayList getListaIndicadoresMod()
   {
     return this.listaIndicadoresMod;
   }
 
   public void setListaIndicadoresNoMod(ArrayList listaIndicadoresNoMod) {
     this.listaIndicadoresNoMod = listaIndicadoresNoMod;
   }
 
   public ArrayList getListaIndicadoresNoMod()
   {
     return this.listaIndicadoresNoMod;
   }
 
   public void setListaProfesionales(ArrayList listaProfesionales) {
     this.listaProfesionales = listaProfesionales;
   }
 
   public ArrayList getListaProfesionales()
   {
     return this.listaProfesionales;
   }
 
   public void setListaProfesionalesPendientes(ArrayList listaProfesionalesPendientes) {
     this.listaProfesionalesPendientes = listaProfesionalesPendientes;
   }
 
   public ArrayList getListaProfesionalesPendientes()
   {
     return this.listaProfesionalesPendientes;
   }
 
   public void setListaProfesionalesPendientesAuditoria(ArrayList listaProfesionalesPendientesAuditoria) {
     this.listaProfesionalesPendientesAuditoria = listaProfesionalesPendientesAuditoria;
   }
 
   public ArrayList getListaProfesionalesPendientesAuditoria()
   {
     return this.listaProfesionalesPendientesAuditoria;
   }
 
   public void setListaProfesionalesEvaluados(ArrayList listaProfesionalesEvaluados) {
     this.listaProfesionalesEvaluados = listaProfesionalesEvaluados;
   }
 
   public ArrayList getListaProfesionalesEvaluados()
   {
     return this.listaProfesionalesEvaluados;
   }
 
   public void setFAceptacSolic(Date fAceptacSolic) {
     this.fAceptacSolic = fAceptacSolic;
   }
 
   public Date getFAceptacSolic()
   {
     return this.fAceptacSolic;
   }
 
   public void setFIncioMC(Date fIncioMC) {
     this.fIncioMC = fIncioMC;
   }
 
   public Date getFIncioMC()
   {
     return this.fIncioMC;
   }
 
   public void setFFinMC(Date fFinMC) {
     this.fFinMC = fFinMC;
   }
 
   public Date getFFinMC()
   {
     return this.fFinMC;
   }
 
   public void setFAceptacionCEIS(Date fAceptacionCEIS) {
     this.fAceptacionCEIS = fAceptacionCEIS;
   }
 
   public Date getFAceptacionCEIS()
   {
     return this.fAceptacionCEIS;
   }
 
   public void setFAceptacionCC(Date fAceptacionCC) {
     this.fAceptacionCC = fAceptacionCC;
   }
 
   public Date getFAceptacionCC()
   {
     return this.fAceptacionCC;
   }
 
   public void setFNegacionMC(Date fNegacionMC) {
     this.fNegacionMC = fNegacionMC;
   }
 
   public Date getFNegacionMC()
   {
     return this.fNegacionMC;
   }
 
   public void setFRespuestainconfMC(Date fRespuestainconfMC) {
     this.fRespuestainconfMC = fRespuestainconfMC;
   }
 
   public Date getFRespuestainconfMC()
   {
     return this.fRespuestainconfMC;
   }
 
   public void setFInicioEvalMC(Date fInicioEvalMC) {
     this.fInicioEvalMC = fInicioEvalMC;
   }
 
   public Date getFInicioEvalMC()
   {
     return this.fInicioEvalMC;
   }
 
   public void setFFinEvalMC(Date fFinEvalMC) {
     this.fFinEvalMC = fFinEvalMC;
   }
 
   public Date getFFinEvalMC()
   {
     return this.fFinEvalMC;
   }
 
   public void setBValidacionCC(String bValidacionCC) {
     this.bValidacionCC = bValidacionCC;
   }
 
   public String getBValidacionCC()
   {
     return this.bValidacionCC;
   }
 
   public void setFIncioCA(Date fIncioCA) {
     this.fIncioCA = fIncioCA;
   }
 
   public Date getFIncioCA()
   {
     return this.fIncioCA;
   }
 
   public void setFFinCA(Date fFinCA) {
     this.fFinCA = fFinCA;
   }
 
   public Date getFFinCA()
   {
     return this.fFinCA;
   }
 
   public void setAModificadoPor(String aModificadoPor) {
     this.aModificadoPor = aModificadoPor;
   }
 
   public String getAModificadoPor()
   {
     return this.aModificadoPor;
   }
 
   public void setACreadoPor(String aCreadoPor) {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 
   public void setCCompfqsId(long cCompfqsId) {
     this.cCompfqsId = cCompfqsId;
   }
 
   public long getCCompfqsId()
   {
     return this.cCompfqsId;
   }
 
   public void setCProcedimientoId(String cProcedimientoId) {
     this.cProcedimientoId = cProcedimientoId;
   }
 
   public String getCProcedimientoId()
   {
     return this.cProcedimientoId;
   }
 
   public void setDProcedimiento(String dProcedimiento) {
     this.dProcedimiento = dProcedimiento;
   }
 
   public String getDProcedimiento()
   {
     return this.dProcedimiento;
   }
 
   public void setFRegDocEscaneados(Date fRegDocEscaneados) {
     this.fRegDocEscaneados = fRegDocEscaneados;
   }
 
   public Date getFRegDocEscaneados()
   {
     return this.fRegDocEscaneados;
   }
 
   public void setFRegEvidenciasConf(Date fRegEvidenciasConf) {
     this.fRegEvidenciasConf = fRegEvidenciasConf;
   }
 
   public Date getFRegEvidenciasConf()
   {
     return this.fRegEvidenciasConf;
   }
 
   public void setBPdtesAclarar(String bPdtesAclarar) {
     this.bPdtesAclarar = bPdtesAclarar;
   }
 
   public String getBPdtesAclarar()
   {
     return this.bPdtesAclarar;
   }
 
   public void setCServicioId(Long cServicioId) {
     this.cServicioId = cServicioId;
   }
 
   public Long getCServicioId()
   {
     return this.cServicioId;
   }
 
   public void setCItinerarioId(long cItinerarioId) {
     this.cItinerarioId = cItinerarioId;
   }
 
   public long getCItinerarioId()
   {
     return this.cItinerarioId;
   }
 
   public void setDItinerario(String dItinerario) {
     this.dItinerario = dItinerario;
   }
 
   public String getDItinerario()
   {
     return this.dItinerario;
   }
 
   public void setCodigoId(String codigoId) {
     this.codigoId = codigoId;
   }
 
   public String getCodigoId()
   {
     return this.codigoId;
   }
 
   public void setDEstado(String dEstado) {
     this.dEstado = dEstado;
   }
 
   public String getDEstado()
   {
     return this.dEstado;
   }
 
   public void setFInformeCte(Date fInformeCte) {
     this.fInformeCte = fInformeCte;
   }
 
   public Date getFInformeCte()
   {
     return this.fInformeCte;
   }
 
   public void setFInformeCe(Date fInformeCe) {
     this.fInformeCe = fInformeCe;
   }
 
   public Date getFInformeCe()
   {
     return this.fInformeCe;
   }
 
   public void setFInformeEval(Date fInformeEval) {
     this.fInformeEval = fInformeEval;
   }
 
   public Date getFInformeEval()
   {
     return this.fInformeEval;
   }
 
   public void setFInformeCC(Date fInformeCC) {
     this.fInformeCC = fInformeCC;
   }
 
   public Date getFInformeCC()
   {
     return this.fInformeCC;
   }
 
   public void setFInformeEval2(Date fInformeEval2) {
     this.fInformeEval2 = fInformeEval2;
   }
 
   public Date getFInformeEval2()
   {
     return this.fInformeEval2;
   }
 
   public void setFAuditoriaPropuesta(Date fAuditoriaPropuesta) {
     this.fAuditoriaPropuesta = fAuditoriaPropuesta;
   }
 
   public Date getFAuditoriaPropuesta()
   {
     return this.fAuditoriaPropuesta;
   }
 
   public void setBAuditoria(String bAuditoria) {
     this.bAuditoria = bAuditoria;
   }
 
   public String getBAuditoria()
   {
     return this.bAuditoria;
   }
 
   public void setAEspecificacionesCC(String aEspecificacionesCC) {
     this.AEspecificacionesCC = aEspecificacionesCC;
   }
 
   public String getAEspecificacionesCC()
   {
     return this.AEspecificacionesCC;
   }
 
   public void setAEspecificacionesCE(String aEspecificacionesCE) {
     this.AEspecificacionesCE = aEspecificacionesCE;
   }
 
   public String getAEspecificacionesCE()
   {
     return this.AEspecificacionesCE;
   }
 
   public void setAEspecificacionesCTE(String aEspecificacionesCTE) {
     this.AEspecificacionesCTE = aEspecificacionesCTE;
   }
 
   public String getAEspecificacionesCTE()
   {
     return this.AEspecificacionesCTE;
   }
 
   public void setAEspecificacionesEval(String aEspecificacionesEval) {
     this.AEspecificacionesEval = aEspecificacionesEval;
   }
 
   public String getAEspecificacionesEval()
   {
     return this.AEspecificacionesEval;
   }
 
   public void setBContestado(String bContestado) {
     this.bContestado = bContestado;
   }
 
   public String getBContestado()
   {
     return this.bContestado;
   }
 
   public void setDDescripcion(String dDescripcion) {
     this.dDescripcion = dDescripcion;
   }
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
   }
 
   public void setOpcion(String opcion) {
     this.opcion = opcion;
   }
 
   public String getOpcion()
   {
     return this.opcion;
   }
 
   public void setTotalEvaluados(int totalEvaluados) {
     this.totalEvaluados = totalEvaluados;
   }
 
   public int getTotalEvaluados()
   {
     return this.totalEvaluados;
   }
 
   public void setTotalIndicador(int totalIndicador) {
     this.totalIndicador = totalIndicador;
   }
 
   public int getTotalIndicador()
   {
     return this.totalIndicador;
   }
 
   public void setTotalIndicadorOK(int totalIndicadorOK) {
     this.totalIndicadorOK = totalIndicadorOK;
   }
 
   public int getTotalIndicadorOK()
   {
     return this.totalIndicadorOK;
   }
 
   public void setTotalIndicadorKO(int totalIndicadorKO) {
     this.totalIndicadorKO = totalIndicadorKO;
   }
 
   public int getTotalIndicadorKO()
   {
     return this.totalIndicadorKO;
   }
 
   public void setTotalIndicadores(int totalIndicadores) {
     this.totalIndicadores = totalIndicadores;
   }
 
   public int getTotalIndicadores()
   {
     return this.totalIndicadores;
   }
 
   public void setTotalIndicadoresOK(int totalIndicadoresOK) {
     this.totalIndicadoresOK = totalIndicadoresOK;
   }
 
   public int getTotalIndicadoresOK()
   {
     return this.totalIndicadoresOK;
   }
 
   public void setTotalIndicadoresKO(int totalIndicadoresKO) {
     this.totalIndicadoresKO = totalIndicadoresKO;
   }
 
   public int getTotalIndicadoresKO()
   {
     return this.totalIndicadoresKO;
   }
 
   public void setPorcIndicadoresOK(float porcIndicadoresOK) {
     this.porcIndicadoresOK = porcIndicadoresOK;
   }
 
   public float getPorcIndicadoresOK()
   {
     return this.porcIndicadoresOK;
   }
 
   public void setPorcIndicadoresKO(float porcIndicadoresKO) {
     this.porcIndicadoresKO = porcIndicadoresKO;
   }
 
   public float getPorcIndicadoresKO()
   {
     return this.porcIndicadoresKO;
   }
 
   public void setPorcIndicadorOK(float porcIndicadorOK) {
     this.porcIndicadorOK = porcIndicadorOK;
   }
 
   public float getPorcIndicadorOK()
   {
     return this.porcIndicadorOK;
   }
 
   public void setPorcIndicadorKO(float porcIndicadorKO) {
     this.porcIndicadorKO = porcIndicadorKO;
   }
 
   public float getPorcIndicadorKO()
   {
     return this.porcIndicadorKO;
   }
 
   public void setPorcIndicador(float porcIndicador) {
     this.porcIndicador = porcIndicador;
   }
 
   public float getPorcIndicador()
   {
     return this.porcIndicador;
   }
 
   public void setBReconocimientoGrado(String bReconocimientoGrado) {
     this.bReconocimientoGrado = bReconocimientoGrado;
   }
 
   public String getBReconocimientoGrado()
   {
     return this.bReconocimientoGrado;
   }
 
   public void setTotalEvaluadosMod(int totalEvaluadosMod) {
     this.totalEvaluadosMod = totalEvaluadosMod;
   }
 
   public int getTotalEvaluadosMod()
   {
     return this.totalEvaluadosMod;
   }
 
   public void setTotalIndicadorMod(int totalIndicadorMod) {
     this.totalIndicadorMod = totalIndicadorMod;
   }
 
   public int getTotalIndicadorMod()
   {
     return this.totalIndicadorMod;
   }
 
   public void setTotalIndicadorModOK(int totalIndicadorModOK) {
     this.totalIndicadorModOK = totalIndicadorModOK;
   }
 
   public int getTotalIndicadorModOK()
   {
     return this.totalIndicadorModOK;
   }
 
   public void setTotalIndicadorModKO(int totalIndicadorModKO) {
     this.totalIndicadorModKO = totalIndicadorModKO;
   }
 
   public int getTotalIndicadorModKO()
   {
     return this.totalIndicadorModKO;
   }
 
   public void setTotalIndicadoresMod(int totalIndicadoresMod) {
     this.totalIndicadoresMod = totalIndicadoresMod;
   }
 
   public int getTotalIndicadoresMod()
   {
     return this.totalIndicadoresMod;
   }
 
   public void setTotalIndicadoresModOK(int totalIndicadoresModOK) {
     this.totalIndicadoresModOK = totalIndicadoresModOK;
   }
 
   public int getTotalIndicadoresModOK()
   {
     return this.totalIndicadoresModOK;
   }
 
   public void setTotalIndicadoresModKO(int totalIndicadoresModKO) {
     this.totalIndicadoresModKO = totalIndicadoresModKO;
   }
 
   public int getTotalIndicadoresModKO()
   {
     return this.totalIndicadoresModKO;
   }
 
   public void setPorcIndicadoresModOK(float porcIndicadoresModOK) {
     this.porcIndicadoresModOK = porcIndicadoresModOK;
   }
 
   public float getPorcIndicadoresModOK()
   {
     return this.porcIndicadoresModOK;
   }
 
   public void setPorcIndicadoresModKO(float porcIndicadoresModKO) {
     this.porcIndicadoresModKO = porcIndicadoresModKO;
   }
 
   public float getPorcIndicadoresModKO()
   {
     return this.porcIndicadoresModKO;
   }
 
   public void setPorcIndicadorModOK(float porcIndicadorModOK) {
     this.porcIndicadorModOK = porcIndicadorModOK;
   }
 
   public float getPorcIndicadorModOK()
   {
     return this.porcIndicadorModOK;
   }
 
   public void setPorcIndicadorModKO(float porcIndicadorModKO) {
     this.porcIndicadorModKO = porcIndicadorModKO;
   }
 
   public float getPorcIndicadorModKO()
   {
     return this.porcIndicadorModKO;
   }
 
   public void setPorcIndicadorMod(float porcIndicadorMod) {
     this.porcIndicadorMod = porcIndicadorMod;
   }
 
   public float getPorcIndicadorMod()
   {
     return this.porcIndicadorMod;
   }
 
   public void setPorcentajeIndicador(String porcentajeIndicador) {
     this.porcentajeIndicador = porcentajeIndicador;
   }
 
   public String getPorcentajeIndicador()
   {
     return this.porcentajeIndicador;
   }
 
   public void setPorcentajeIndicadores(String porcentajeIndicadores) {
     this.porcentajeIndicadores = porcentajeIndicadores;
   }
 
   public String getPorcentajeIndicadores()
   {
     return this.porcentajeIndicadores;
   }
 
   public void setPorcentajeIndicadoresOK(String porcentajeIndicadoresOK) {
     this.porcentajeIndicadoresOK = porcentajeIndicadoresOK;
   }
 
   public String getPorcentajeIndicadoresOK()
   {
     return this.porcentajeIndicadoresOK;
   }
 
   public void setPorcentajeIndicadoresKO(String porcentajeIndicadoresKO) {
     this.porcentajeIndicadoresKO = porcentajeIndicadoresKO;
   }
 
   public String getPorcentajeIndicadoresKO()
   {
     return this.porcentajeIndicadoresKO;
   }
 
   public void setPorcentajeIndicadoresModOK(String porcentajeIndicadoresModOK) {
     this.porcentajeIndicadoresModOK = porcentajeIndicadoresModOK;
   }
 
   public String getPorcentajeIndicadoresModOK()
   {
     return this.porcentajeIndicadoresModOK;
   }
 
   public void setPorcentajeIndicadoresModKO(String porcentajeIndicadoresModKO) {
     this.porcentajeIndicadoresModKO = porcentajeIndicadoresModKO;
   }
 
   public String getPorcentajeIndicadoresModKO()
   {
     return this.porcentajeIndicadoresModKO;
   }
 
   public void setTotalEvaluadosNoMod(int totalEvaluadosNoMod) {
     this.totalEvaluadosNoMod = totalEvaluadosNoMod;
   }
 
   public int getTotalEvaluadosNoMod()
   {
     return this.totalEvaluadosNoMod;
   }
 
   public void setTotalIndicadorNoMod(int totalIndicadorNoMod) {
     this.totalIndicadorNoMod = totalIndicadorNoMod;
   }
 
   public int getTotalIndicadorNoMod()
   {
     return this.totalIndicadorNoMod;
   }
 
   public void setTotalIndicadorNoModOK(int totalIndicadorNoModOK) {
     this.totalIndicadorNoModOK = totalIndicadorNoModOK;
   }
 
   public int getTotalIndicadorNoModOK()
   {
     return this.totalIndicadorNoModOK;
   }
 
   public void setTotalIndicadorNoModKO(int totalIndicadorNoModKO) {
     this.totalIndicadorNoModKO = totalIndicadorNoModKO;
   }
 
   public int getTotalIndicadorNoModKO()
   {
     return this.totalIndicadorNoModKO;
   }
 
   public void setTotalIndicadoresNoMod(int totalIndicadoresNoMod) {
     this.totalIndicadoresNoMod = totalIndicadoresNoMod;
   }
 
   public int getTotalIndicadoresNoMod()
   {
     return this.totalIndicadoresNoMod;
   }
 
   public void setTotalIndicadoresNoModOK(int totalIndicadoresNoModOK) {
     this.totalIndicadoresNoModOK = totalIndicadoresNoModOK;
   }
 
   public int getTotalIndicadoresNoModOK()
   {
     return this.totalIndicadoresNoModOK;
   }
 
   public void setTotalIndicadoresNoModKO(int totalIndicadoresNoModKO) {
     this.totalIndicadoresNoModKO = totalIndicadoresNoModKO;
   }
 
   public int getTotalIndicadoresNoModKO()
   {
     return this.totalIndicadoresNoModKO;
   }
 
   public void setPorcIndicadoresNoModOK(float porcIndicadoresNoModOK) {
     this.porcIndicadoresNoModOK = porcIndicadoresNoModOK;
   }
 
   public float getPorcIndicadoresNoModOK()
   {
     return this.porcIndicadoresNoModOK;
   }
 
   public void setPorcIndicadoresNoModKO(float porcIndicadoresNoModKO) {
     this.porcIndicadoresNoModKO = porcIndicadoresNoModKO;
   }
 
   public float getPorcIndicadoresNoModKO()
   {
     return this.porcIndicadoresNoModKO;
   }
 
   public void setPorcIndicadorNoModOK(float porcIndicadorNoModOK) {
     this.porcIndicadorNoModOK = porcIndicadorNoModOK;
   }
 
   public float getPorcIndicadorNoModOK()
   {
     return this.porcIndicadorNoModOK;
   }
 
   public void setPorcIndicadorNoModKO(float porcIndicadorNoModKO) {
     this.porcIndicadorNoModKO = porcIndicadorNoModKO;
   }
 
   public float getPorcIndicadorNoModKO()
   {
     return this.porcIndicadorNoModKO;
   }
 
   public void setPorcIndicadorNoMod(float porcIndicadorNoMod) {
     this.porcIndicadorNoMod = porcIndicadorNoMod;
   }
 
   public float getPorcIndicadorNoMod()
   {
     return this.porcIndicadorNoMod;
   }
 
   public void setNCreditosEvaluador(long nCreditosEvaluador) {
     this.nCreditosEvaluador = nCreditosEvaluador;
   }
 
   public long getNCreditosEvaluador()
   {
     return this.nCreditosEvaluador;
   }
 
   public void setNCreditosNecesarios(long nCreditosNecesarios) {
     this.nCreditosNecesarios = nCreditosNecesarios;
   }
 
   public long getNCreditosNecesarios()
   {
     return this.nCreditosNecesarios;
   }
 
   public void setBModEvaluador(boolean bModEvaluador) {
     this.bModEvaluador = bModEvaluador;
   }
 
   public boolean isBModEvaluador()
   {
     return this.bModEvaluador;
   }
 
   public void setBConformeCTE(boolean bConformeCTE) {
     this.bConformeCTE = bConformeCTE;
   }
 
   public boolean isBConformeCTE()
   {
     return this.bConformeCTE;
   }
 
   public void setDNombreCte(String dNombreCte) {
     this.dNombreCte = dNombreCte;
   }
 
   public String getDNombreCte()
   {
     return this.dNombreCte;
   }
 
   public void setCCteId(long cCteId) {
     this.cCteId = cCteId;
   }
 
   public long getCCteId()
   {
     return this.cCteId;
   }
 
   public void setBDecisionCE(boolean bDecisionCE) {
     this.bDecisionCE = bDecisionCE;
   }
 
   public boolean isBDecisionCE()
   {
     return this.bDecisionCE;
   }
 
   public void setBSegundaEvaluacion(String bSegundaEvaluacion) {
     this.bSegundaEvaluacion = bSegundaEvaluacion;
   }
 
   public String getBSegundaEvaluacion()
   {
     return this.bSegundaEvaluacion;
   }
 
   public void setNumSolicitudes(long numSolicitudes) {
     this.numSolicitudes = numSolicitudes;
   }
 
   public long getNumSolicitudes()
   {
     return this.numSolicitudes;
   }
 
   public void setFInicioEvaluacion(Date fInicioEvaluacion) {
     this.fInicioEvaluacion = fInicioEvaluacion;
   }
 
   public Date getFInicioEvaluacion()
   {
     return this.fInicioEvaluacion;
   }
 
   public void setBNuevaRevision(String bNuevaRevision) {
     this.bNuevaRevision = bNuevaRevision;
   }
 
   public String getBNuevaRevision()
   {
     return this.bNuevaRevision;
   }
 
   public void setFReunionCTE(Date fReunionCTE) {
     this.fReunionCTE = fReunionCTE;
   }
 
   public Date getFReunionCTE()
   {
     return this.fReunionCTE;
   }
 
   public void setADiscrepanciasCE(String aDiscrepanciasCE) {
     this.ADiscrepanciasCE = aDiscrepanciasCE;
   }
 
   public String getADiscrepanciasCE()
   {
     return this.ADiscrepanciasCE;
   }
 
   public void setADiscrepanciasCTE(String aDiscrepanciasCTE) {
     this.ADiscrepanciasCTE = aDiscrepanciasCTE;
   }
 
   public String getADiscrepanciasCTE()
   {
     return this.ADiscrepanciasCTE;
   }
 
   public void setFReunionCE(Date fReunionCE) {
     this.fReunionCE = fReunionCE;
   }
 
   public Date getFReunionCE()
   {
     return this.fReunionCE;
   }
 
   public void setBConformidadCTE(String bConformidadCTE) {
     this.bConformidadCTE = bConformidadCTE;
   }
 
   public String getBConformidadCTE()
   {
     return this.bConformidadCTE;
   }
 
   public void setBDecisionCEStr(String bDecisionCEStr) {
     this.bDecisionCEStr = bDecisionCEStr;
   }
 
   public String getBDecisionCEStr()
   {
     return this.bDecisionCEStr;
   }
 
   public void setCEstadoExpId(long cEstadoExpId) {
     this.cEstadoExpId = cEstadoExpId;
   }
 
   public long getCEstadoExpId()
   {
     return this.cEstadoExpId;
   }
 
   public void setNCreditosEvaluadosCTE(double nCreditosEvaluadosCTE) {
     this.nCreditosEvaluadosCTE = nCreditosEvaluadosCTE;
   }
 
   public double getNCreditosEvaluadosCTE()
   {
     return this.nCreditosEvaluadosCTE;
   }

public String getDConvocatoriaNombreCorto() {
	return dConvocatoriaNombreCorto;
}

public void setDConvocatoriaNombreCorto(String dConvocatoriaNombreCorto) {
	this.dConvocatoriaNombreCorto = dConvocatoriaNombreCorto;
}
   
   
   
   
 }

