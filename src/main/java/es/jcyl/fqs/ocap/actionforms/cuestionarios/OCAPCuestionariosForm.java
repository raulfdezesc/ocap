package es.jcyl.fqs.ocap.actionforms.cuestionarios;

import java.util.ArrayList;
import org.apache.struts.action.ActionForm;

public class OCAPCuestionariosForm extends ActionForm
{
  private Long cExpId;
  private String codigoId;
  private long cCuestionarioId;
  private long cCuestionarioPadreId;
  private String dTitulo;
  private String dNombreItinerario;
  private String bObligatorio;
  private String bContestarUsuario;
  private String bFinalizado;
  private String bSubdivision;
  private ArrayList listadoAreas;
  private ArrayList listadoPreguntas;
  private ArrayList listadoSubCuestionarios;
  private ArrayList listaCreditos;
  private Integer nRepeticiones;
  private String dRepeticion;
  private String idRepeticion;
  private String dNombre;
  private String dNombreLargo;
  private String bSimulacion;
  private int nItinerario;
  private String dMensaje;
  private String cGradoId;
  private String dGrado_des;
  private String cConvocatoriaId;
  private String dArea;
  private String dAreaLargo;
  private String cAreaId;
  private String dInformacionArea;
  private String dEvidencia;
  private String dObservacionesEvidencia;
  private String dPonderacion;
  private String nCreditosItinerario;
  private String nCreditosPosiblesItinerario;
  private String nPuntosConseguidos;
  private String nCreditosNecesarios;
  private String nCreditosObtenidos;
  private String nCreditosEvaluados;
  private String dNombreUsuario;
  private String dApellido1;
  private String dCorreoelectronico;
  private String cDni;
  private String cDniReal;
  private String nTelefono1;
  private String bSexo;
  private String nTelefono2;
  private String dDomicilio;
  private String fModicacion;
  private String cProvinciaUsu_id;
  private String dProvinciaUsu;
  private String dLocalidadUsu;
  private String cPostalUsu;
  private String aCiudad;
  private String cProcedimientoId;
  private String cSitAdministrativaAuxId;
  private String dSitAdministrativaAuxOtros;
  private String cJuridico;
  private String cJuridicoCombo;
  private String fInformeCC;
  private String dModAnterior;
  private String dProcedimiento;
  private String dRegimenJuridicoOtros;
  private String cPersonalId;
  private String nAniosantiguedad;
  private String nMesesantiguedad;
  private String nDiasantiguedad;
  private String dEstatutario_nombre;
  private String dEstatutarioActual_nombre;
  private String dProfesional_nombre;
  private String dProfesionalActual_nombre;
  private String dEspec_nombre;
  private String dEspecActual_nombre;
  private String cEspecActual_id;
  private String dCentrotrabajo_nombre;
  private String dServicio;
  private String cServicioId;
  private String dProvincia;
  private String dTipogerencia_desc;
  private String dGerencia_nombre;
  private String dPuesto;
  private String dAreaUsuario;
  private String dUnidad;
  private long nDiasEvaluacion;
  private ArrayList listaCalculos;
  private String bFinalizadoUnoPorArea;
  private String dIntroduccion;
  private Integer nEvidencias;
  private String bIndicadores;
  private ArrayList listaEvidencias;
  private ArrayList listaItinerarios;
  private String cItinerarioId;
  private String cadenaRespuestas;
  private String cEvidenciaId;
  private String nEvidencia;
  private String cPadreEvidenciaId;
  private String cEvidenciaDocumentalId;
  private ArrayList listaEviDocumentales;
  private String[] idEvidencia;
  private ArrayList listaDocumentos;
  private String bEvaluado;
  private String bAcuerdo;
  private String bProposicion;
  private String aRazon;
  private String aComentarios;
  private String bAcuerdo1;
  private String bAcuerdo2;
  private String bAcuerdo3;
  private String bAcuerdo4;
  private String bAcuerdo5;
  private String bAcuerdo6;
  private ArrayList listadoAuditorias;
  private String aEspecificaciones;
  private String dCategoriaEspecialidadEvaluado;
  private String dNombreEvaluador;
  private String dApellidosEvaluador;
  private String cDniEvaluador;
  private String nTelefonoEvaluador;
  private String ACorreoelectronicoEvaluador;
  private String dCategoriaEspecialidadEvaluador;
  private String codigoEvaluadorId;
  private String fEvaluacion;
  private String fInicioEvaluacion;
  private String fFinEvaluacion;
  private String fReunion;
  private String dNombreCTE;
  private String dNombrePresidente;
  private String bConformidadCTE;
  private String bNuevaRevision;
  private String aDiscrepancias;
  private String bDecisionCE;
  private String nCreditosEvaluadosCTE;
  private String bReconocimientoGrado;
  private String bSegundaEvaluacion;
  protected String bAuditable;
  protected String bAuditoria;
  protected String aMotivoAuditoria;
  protected String aLugarRealizacion;
  protected String fAuditoria;
  protected String aSuperior;
  protected String aHallazgos;
  protected String bCumplimiento;
  protected String bCategorizacion;
  protected String aRecomendaciones;
  protected String aConclusiones;
  protected String bAutoEvaluacion;
  protected String b2Evaluacion;
  protected String bAnalisis;
  protected String aResultados;
  protected String aTipoRegistro;
  protected String aDocumento;
  protected String aNHistoria1;
  protected String aNHistoria2;
  protected String aNHistoria3;
  protected int contadorAuditorias;
  protected String fAuditoriaPropuesta;
  protected String hAuditoriaPropuesta;
  protected String bEraRepe0;
  private String cPlantillaId;
  
  private String nAnioConvocatoria;
  
  private String bMostrarBtnImprimir;
  
  private String bMostrarBtnVolver;
  
  private String nombreApellidos;
  
  private String dni;

  public void setCExpId(Long cExpId)
  {
    this.cExpId = cExpId;
  }

  public Long getCExpId()
  {
    return this.cExpId;
  }

  public void setListadoAreas(ArrayList listadoAreas)
  {
    this.listadoAreas = listadoAreas;
  }

  public ArrayList getListadoAreas()
  {
    return this.listadoAreas;
  }

  public void setListadoPreguntas(ArrayList listadoPreguntas)
  {
    this.listadoPreguntas = listadoPreguntas;
  }

  public ArrayList getListadoPreguntas()
  {
    return this.listadoPreguntas;
  }

  public void setDNombre(String dNombre)
  {
    this.dNombre = dNombre;
  }

  public String getDNombre()
  {
    return this.dNombre;
  }

  public void setDNombreLargo(String dNombreLargo)
  {
    this.dNombreLargo = dNombreLargo;
  }

  public String getDNombreLargo()
  {
    return this.dNombreLargo;
  }

  public void setNItinerario(int nItinerario)
  {
    this.nItinerario = nItinerario;
  }

  public int getNItinerario()
  {
    return this.nItinerario;
  }

  public void setDArea(String dArea)
  {
    this.dArea = dArea;
  }

  public String getDArea()
  {
    return this.dArea;
  }

  public void setCAreaId(String cAreaId)
  {
    this.cAreaId = cAreaId;
  }

  public String getCAreaId()
  {
    return this.cAreaId;
  }

  public void setNCreditosItinerario(String nCreditosItinerario)
  {
    this.nCreditosItinerario = nCreditosItinerario;
  }

  public String getNCreditosItinerario()
  {
    return this.nCreditosItinerario;
  }

  public void setListadoSubCuestionarios(ArrayList listadoSubCuestionarios)
  {
    this.listadoSubCuestionarios = listadoSubCuestionarios;
  }

  public ArrayList getListadoSubCuestionarios()
  {
    return this.listadoSubCuestionarios;
  }

  public void setNRepeticiones(Integer nRepeticiones)
  {
    this.nRepeticiones = nRepeticiones;
  }

  public Integer getNRepeticiones()
  {
    return this.nRepeticiones;
  }

  public void setIdRepeticion(String idRepeticion)
  {
    this.idRepeticion = idRepeticion;
  }

  public String getIdRepeticion()
  {
    return this.idRepeticion;
  }

  public void setDMensaje(String dMensaje)
  {
    this.dMensaje = dMensaje;
  }

  public String getDMensaje()
  {
    return this.dMensaje;
  }

  public void setCCuestionarioId(long cCuestionarioId)
  {
    this.cCuestionarioId = cCuestionarioId;
  }

  public long getCCuestionarioId()
  {
    return this.cCuestionarioId;
  }

  public void setDServicio(String dServicio)
  {
    this.dServicio = dServicio;
  }

  public String getDServicio()
  {
    return this.dServicio;
  }

  public void setDTitulo(String dTitulo)
  {
    this.dTitulo = dTitulo;
  }

  public String getDTitulo()
  {
    return this.dTitulo;
  }

  public void setDNombreUsuario(String dNombreUsuario)
  {
    this.dNombreUsuario = dNombreUsuario;
  }

  public String getDNombreUsuario()
  {
    return this.dNombreUsuario;
  }

  public void setDApellido1(String dApellido1)
  {
    this.dApellido1 = dApellido1;
  }

  public String getDApellido1()
  {
    return this.dApellido1;
  }

  public void setDCorreoelectronico(String dCorreoelectronico)
  {
    this.dCorreoelectronico = dCorreoelectronico;
  }

  public String getDCorreoelectronico()
  {
    return this.dCorreoelectronico;
  }

  public void setCDni(String cDni)
  {
    this.cDni = cDni;
  }

  public String getCDni()
  {
    return this.cDni;
  }

  public void setCDniReal(String cDniReal)
  {
    this.cDniReal = cDniReal;
  }

  public String getCDniReal()
  {
    return this.cDniReal;
  }

  public void setNTelefono1(String nTelefono1)
  {
    this.nTelefono1 = nTelefono1;
  }

  public String getNTelefono1()
  {
    return this.nTelefono1;
  }

  public void setBSexo(String bSexo)
  {
    this.bSexo = bSexo;
  }

  public String getBSexo()
  {
    return this.bSexo;
  }

  public void setDAreaLargo(String dAreaLargo)
  {
    this.dAreaLargo = dAreaLargo;
  }

  public String getDAreaLargo()
  {
    return this.dAreaLargo;
  }

  public void setDEvidencia(String dEvidencia)
  {
    this.dEvidencia = dEvidencia;
  }

  public String getDEvidencia()
  {
    return this.dEvidencia;
  }

  public void setBObligatorio(String bObligatorio)
  {
    this.bObligatorio = bObligatorio;
  }

  public String getBObligatorio()
  {
    return this.bObligatorio;
  }

  public void setDProfesional_nombre(String dProfesional_nombre)
  {
    this.dProfesional_nombre = dProfesional_nombre;
  }

  public String getDProfesional_nombre()
  {
    return this.dProfesional_nombre;
  }

  public void setDEspec_nombre(String dEspec_nombre)
  {
    this.dEspec_nombre = dEspec_nombre;
  }

  public String getDEspec_nombre()
  {
    return this.dEspec_nombre;
  }

  public void setDProvincia(String dProvincia)
  {
    this.dProvincia = dProvincia;
  }

  public String getDProvincia()
  {
    return this.dProvincia;
  }

  public void setDTipogerencia_desc(String dTipogerencia_desc)
  {
    this.dTipogerencia_desc = dTipogerencia_desc;
  }

  public String getDTipogerencia_desc()
  {
    return this.dTipogerencia_desc;
  }

  public void setDGerencia_nombre(String dGerencia_nombre)
  {
    this.dGerencia_nombre = dGerencia_nombre;
  }

  public String getDGerencia_nombre()
  {
    return this.dGerencia_nombre;
  }

  public void setDCentrotrabajo_nombre(String dCentrotrabajo_nombre)
  {
    this.dCentrotrabajo_nombre = dCentrotrabajo_nombre;
  }

  public String getDCentrotrabajo_nombre()
  {
    return this.dCentrotrabajo_nombre;
  }

  public void setDPuesto(String dPuesto)
  {
    this.dPuesto = dPuesto;
  }

  public String getDPuesto()
  {
    return this.dPuesto;
  }

  public void setDAreaUsuario(String dAreaUsuario)
  {
    this.dAreaUsuario = dAreaUsuario;
  }

  public String getDAreaUsuario()
  {
    return this.dAreaUsuario;
  }

  public void setDUnidad(String dUnidad)
  {
    this.dUnidad = dUnidad;
  }

  public String getDUnidad()
  {
    return this.dUnidad;
  }

  public void setListaCreditos(ArrayList listaCreditos)
  {
    this.listaCreditos = listaCreditos;
  }

  public ArrayList getListaCreditos()
  {
    return this.listaCreditos;
  }

  public void setDInformacionArea(String dInformacionArea)
  {
    this.dInformacionArea = dInformacionArea;
  }

  public String getDInformacionArea()
  {
    return this.dInformacionArea;
  }

  public void setNDiasEvaluacion(long nDiasEvaluacion)
  {
    this.nDiasEvaluacion = nDiasEvaluacion;
  }

  public long getNDiasEvaluacion()
  {
    return this.nDiasEvaluacion;
  }

  public void setDObservacionesEvidencia(String dObservacionesEvidencia)
  {
    this.dObservacionesEvidencia = dObservacionesEvidencia;
  }

  public String getDObservacionesEvidencia()
  {
    return this.dObservacionesEvidencia;
  }

  public void setBFinalizado(String bFinalizado)
  {
    this.bFinalizado = bFinalizado;
  }

  public String getBFinalizado()
  {
    return this.bFinalizado;
  }

  public void setNCreditosPosiblesItinerario(String nCreditosPosiblesItinerario)
  {
    this.nCreditosPosiblesItinerario = nCreditosPosiblesItinerario;
  }

  public String getNCreditosPosiblesItinerario()
  {
    return this.nCreditosPosiblesItinerario;
  }

  public void setListaCalculos(ArrayList listaCalculos)
  {
    this.listaCalculos = listaCalculos;
  }

  public ArrayList getListaCalculos()
  {
    return this.listaCalculos;
  }

  public void setNPuntosConseguidos(String nPuntosConseguidos)
  {
    this.nPuntosConseguidos = nPuntosConseguidos;
  }

  public String getNPuntosConseguidos()
  {
    return this.nPuntosConseguidos;
  }

  public void setDPonderacion(String dPonderacion)
  {
    this.dPonderacion = dPonderacion;
  }

  public String getDPonderacion()
  {
    return this.dPonderacion;
  }

  public void setBContestarUsuario(String bContestarUsuario)
  {
    this.bContestarUsuario = bContestarUsuario;
  }

  public String getBContestarUsuario()
  {
    return this.bContestarUsuario;
  }

  public void setCGradoId(String cGradoId)
  {
    this.cGradoId = cGradoId;
  }

  public String getCGradoId()
  {
    return this.cGradoId;
  }

  public void setNTelefono2(String nTelefono2)
  {
    this.nTelefono2 = nTelefono2;
  }

  public String getNTelefono2()
  {
    return this.nTelefono2;
  }

  public void setDDomicilio(String dDomicilio)
  {
    this.dDomicilio = dDomicilio;
  }

  public String getDDomicilio()
  {
    return this.dDomicilio;
  }

  public void setFModicacion(String fModicacion)
  {
    this.fModicacion = fModicacion;
  }

  public String getFModicacion()
  {
    return this.fModicacion;
  }

  public void setCProvinciaUsu_id(String cProvinciaUsu_id)
  {
    this.cProvinciaUsu_id = cProvinciaUsu_id;
  }

  public String getCProvinciaUsu_id()
  {
    return this.cProvinciaUsu_id;
  }

  public void setDProvinciaUsu(String dProvinciaUsu)
  {
    this.dProvinciaUsu = dProvinciaUsu;
  }

  public String getDProvinciaUsu()
  {
    return this.dProvinciaUsu;
  }

  public void setDLocalidadUsu(String dLocalidadUsu)
  {
    this.dLocalidadUsu = dLocalidadUsu;
  }

  public String getDLocalidadUsu()
  {
    return this.dLocalidadUsu;
  }

  public void setCPostalUsu(String cPostalUsu)
  {
    this.cPostalUsu = cPostalUsu;
  }

  public String getCPostalUsu()
  {
    return this.cPostalUsu;
  }

  public void setACiudad(String aCiudad)
  {
    this.aCiudad = aCiudad;
  }

  public String getACiudad()
  {
    return this.aCiudad;
  }

  public void setCSitAdministrativaAuxId(String cSitAdministrativaAuxId)
  {
    this.cSitAdministrativaAuxId = cSitAdministrativaAuxId;
  }

  public String getCSitAdministrativaAuxId()
  {
    return this.cSitAdministrativaAuxId;
  }

  public void setDSitAdministrativaAuxOtros(String dSitAdministrativaAuxOtros)
  {
    this.dSitAdministrativaAuxOtros = dSitAdministrativaAuxOtros;
  }

  public String getDSitAdministrativaAuxOtros()
  {
    return this.dSitAdministrativaAuxOtros;
  }

  public void setDEstatutario_nombre(String dEstatutario_nombre)
  {
    this.dEstatutario_nombre = dEstatutario_nombre;
  }

  public String getDEstatutario_nombre()
  {
    return this.dEstatutario_nombre;
  }

  public void setDEstatutarioActual_nombre(String dEstatutarioActual_nombre)
  {
    this.dEstatutarioActual_nombre = dEstatutarioActual_nombre;
  }

  public String getDEstatutarioActual_nombre()
  {
    return this.dEstatutarioActual_nombre;
  }

  public void setDProfesionalActual_nombre(String dProfesionalActual_nombre)
  {
    this.dProfesionalActual_nombre = dProfesionalActual_nombre;
  }

  public String getDProfesionalActual_nombre()
  {
    return this.dProfesionalActual_nombre;
  }

  public void setDEspecActual_nombre(String dEspecActual_nombre)
  {
    this.dEspecActual_nombre = dEspecActual_nombre;
  }

  public String getDEspecActual_nombre()
  {
    return this.dEspecActual_nombre;
  }

  public void setCJuridico(String cJuridico)
  {
    this.cJuridico = cJuridico;
  }

  public String getCJuridico()
  {
    return this.cJuridico;
  }

  public void setDGrado_des(String dGrado_des)
  {
    this.dGrado_des = dGrado_des;
  }

  public String getDGrado_des()
  {
    return this.dGrado_des;
  }

  public void setCConvocatoriaId(String cConvocatoriaId)
  {
    this.cConvocatoriaId = cConvocatoriaId;
  }

  public String getCConvocatoriaId()
  {
    return this.cConvocatoriaId;
  }

  public void setCEspecActual_id(String cEspecActual_id)
  {
    this.cEspecActual_id = cEspecActual_id;
  }

  public String getCEspecActual_id()
  {
    return this.cEspecActual_id;
  }

  public void setBSubdivision(String bSubdivision)
  {
    this.bSubdivision = bSubdivision;
  }

  public String getBSubdivision()
  {
    return this.bSubdivision;
  }

  public void setBFinalizadoUnoPorArea(String bFinalizadoUnoPorArea) {
    this.bFinalizadoUnoPorArea = bFinalizadoUnoPorArea;
  }

  public String getBFinalizadoUnoPorArea()
  {
    return this.bFinalizadoUnoPorArea;
  }

  public void setDIntroduccion(String dIntroduccion)
  {
    this.dIntroduccion = dIntroduccion;
  }

  public String getDIntroduccion()
  {
    return this.dIntroduccion;
  }

  public void setNEvidencias(Integer nEvidencias)
  {
    this.nEvidencias = nEvidencias;
  }

  public Integer getNEvidencias()
  {
    return this.nEvidencias;
  }

  public void setBIndicadores(String bIndicadores)
  {
    this.bIndicadores = bIndicadores;
  }

  public String getBIndicadores()
  {
    return this.bIndicadores;
  }

  public void setListaEvidencias(ArrayList listaEvidencias)
  {
    this.listaEvidencias = listaEvidencias;
  }

  public ArrayList getListaEvidencias()
  {
    return this.listaEvidencias;
  }

  public void setDRepeticion(String dRepeticion)
  {
    this.dRepeticion = dRepeticion;
  }

  public String getDRepeticion()
  {
    return this.dRepeticion;
  }

  public void setCServicioId(String cServicioId)
  {
    this.cServicioId = cServicioId;
  }

  public String getCServicioId()
  {
    return this.cServicioId;
  }

  public void setListaItinerarios(ArrayList listaItinerarios)
  {
    this.listaItinerarios = listaItinerarios;
  }

  public ArrayList getListaItinerarios()
  {
    return this.listaItinerarios;
  }

  public void setCItinerarioId(String cItinerarioId)
  {
    this.cItinerarioId = cItinerarioId;
  }

  public String getCItinerarioId()
  {
    return this.cItinerarioId;
  }

  public void setCadenaRespuestas(String cadenaRespuestas)
  {
    this.cadenaRespuestas = cadenaRespuestas;
  }

  public String getCadenaRespuestas()
  {
    return this.cadenaRespuestas;
  }

  public void setDNombreItinerario(String dNombreItinerario)
  {
    this.dNombreItinerario = dNombreItinerario;
  }

  public String getDNombreItinerario()
  {
    return this.dNombreItinerario;
  }

  public void setNCreditosNecesarios(String nCreditosNecesarios)
  {
    this.nCreditosNecesarios = nCreditosNecesarios;
  }

  public String getNCreditosNecesarios()
  {
    return this.nCreditosNecesarios;
  }

  public void setCodigoId(String codigoId)
  {
    this.codigoId = codigoId;
  }

  public String getCodigoId()
  {
    return this.codigoId;
  }

  public void setCEvidenciaId(String cEvidenciaId)
  {
    this.cEvidenciaId = cEvidenciaId;
  }

  public String getCEvidenciaId()
  {
    return this.cEvidenciaId;
  }

  public void setNCreditosObtenidos(String nCreditosObtenidos)
  {
    this.nCreditosObtenidos = nCreditosObtenidos;
  }

  public String getNCreditosObtenidos()
  {
    return this.nCreditosObtenidos;
  }

  public void setNCreditosEvaluados(String nCreditosEvaluados)
  {
    this.nCreditosEvaluados = nCreditosEvaluados;
  }

  public String getNCreditosEvaluados()
  {
    return this.nCreditosEvaluados;
  }

  public void setListaDocumentos(ArrayList listaDocumentos)
  {
    this.listaDocumentos = listaDocumentos;
  }

  public ArrayList getListaDocumentos()
  {
    return this.listaDocumentos;
  }

  public void setBAcuerdo(String bAcuerdo)
  {
    this.bAcuerdo = bAcuerdo;
  }

  public String getBAcuerdo()
  {
    return this.bAcuerdo;
  }

  public void setBProposicion(String bProposicion)
  {
    this.bProposicion = bProposicion;
  }

  public String getBProposicion()
  {
    return this.bProposicion;
  }

  public void setARazon(String aRazon)
  {
    this.aRazon = aRazon;
  }

  public String getARazon()
  {
    return this.aRazon;
  }

  public void setAComentarios(String aComentarios)
  {
    this.aComentarios = aComentarios;
  }

  public String getAComentarios()
  {
    return this.aComentarios;
  }

  public void setBAcuerdo1(String bAcuerdo1)
  {
    this.bAcuerdo1 = bAcuerdo1;
  }

  public String getBAcuerdo1()
  {
    return this.bAcuerdo1;
  }

  public void setBAcuerdo2(String bAcuerdo2)
  {
    this.bAcuerdo2 = bAcuerdo2;
  }

  public String getBAcuerdo2()
  {
    return this.bAcuerdo2;
  }

  public void setBAcuerdo3(String bAcuerdo3)
  {
    this.bAcuerdo3 = bAcuerdo3;
  }

  public String getBAcuerdo3()
  {
    return this.bAcuerdo3;
  }

  public void setBAcuerdo4(String bAcuerdo4)
  {
    this.bAcuerdo4 = bAcuerdo4;
  }

  public String getBAcuerdo4()
  {
    return this.bAcuerdo4;
  }

  public void setBAcuerdo5(String bAcuerdo5)
  {
    this.bAcuerdo5 = bAcuerdo5;
  }

  public String getBAcuerdo5()
  {
    return this.bAcuerdo5;
  }

  public void setBAcuerdo6(String bAcuerdo6)
  {
    this.bAcuerdo6 = bAcuerdo6;
  }

  public String getBAcuerdo6()
  {
    return this.bAcuerdo6;
  }

  public void setBEvaluado(String bEvaluado)
  {
    this.bEvaluado = bEvaluado;
  }

  public String getBEvaluado()
  {
    return this.bEvaluado;
  }

  public void setDNombreEvaluador(String dNombreEvaluador)
  {
    this.dNombreEvaluador = dNombreEvaluador;
  }

  public String getDNombreEvaluador()
  {
    return this.dNombreEvaluador;
  }

  public void setDApellidosEvaluador(String dApellidosEvaluador)
  {
    this.dApellidosEvaluador = dApellidosEvaluador;
  }

  public String getDApellidosEvaluador()
  {
    return this.dApellidosEvaluador;
  }

  public void setCDniEvaluador(String cDniEvaluador)
  {
    this.cDniEvaluador = cDniEvaluador;
  }

  public String getCDniEvaluador()
  {
    return this.cDniEvaluador;
  }

  public void setNTelefonoEvaluador(String nTelefonoEvaluador) {
    this.nTelefonoEvaluador = nTelefonoEvaluador;
  }

  public String getNTelefonoEvaluador()
  {
    return this.nTelefonoEvaluador;
  }

  public void setACorreoelectronicoEvaluador(String ACorreoelectronicoEvaluador)
  {
    this.ACorreoelectronicoEvaluador = ACorreoelectronicoEvaluador;
  }

  public String getACorreoelectronicoEvaluador()
  {
    return this.ACorreoelectronicoEvaluador;
  }

  public void setFEvaluacion(String fEvaluacion)
  {
    this.fEvaluacion = fEvaluacion;
  }

  public String getFEvaluacion()
  {
    return this.fEvaluacion;
  }

  public void setDCategoriaEspecialidadEvaluado(String dCategoriaEspecialidadEvaluado)
  {
    this.dCategoriaEspecialidadEvaluado = dCategoriaEspecialidadEvaluado;
  }

  public String getDCategoriaEspecialidadEvaluado()
  {
    return this.dCategoriaEspecialidadEvaluado;
  }

  public void setDCategoriaEspecialidadEvaluador(String dCategoriaEspecialidadEvaluador)
  {
    this.dCategoriaEspecialidadEvaluador = dCategoriaEspecialidadEvaluador;
  }

  public String getDCategoriaEspecialidadEvaluador()
  {
    return this.dCategoriaEspecialidadEvaluador;
  }

  public void setCodigoEvaluadorId(String codigoEvaluadorId)
  {
    this.codigoEvaluadorId = codigoEvaluadorId;
  }

  public String getCodigoEvaluadorId()
  {
    return this.codigoEvaluadorId;
  }

  public void setListadoAuditorias(ArrayList listadoAuditorias)
  {
    this.listadoAuditorias = listadoAuditorias;
  }

  public ArrayList getListadoAuditorias()
  {
    return this.listadoAuditorias;
  }

  public void setAEspecificaciones(String aEspecificaciones)
  {
    this.aEspecificaciones = aEspecificaciones;
  }

  public String getAEspecificaciones()
  {
    return this.aEspecificaciones;
  }

  public void setDNombreCTE(String dNombreCTE)
  {
    this.dNombreCTE = dNombreCTE;
  }

  public String getDNombreCTE()
  {
    return this.dNombreCTE;
  }

  public void setFInicioEvaluacion(String fInicioEvaluacion)
  {
    this.fInicioEvaluacion = fInicioEvaluacion;
  }

  public String getFInicioEvaluacion()
  {
    return this.fInicioEvaluacion;
  }

  public void setDNombrePresidente(String dNombrePresidente)
  {
    this.dNombrePresidente = dNombrePresidente;
  }

  public String getDNombrePresidente()
  {
    return this.dNombrePresidente;
  }

  public void setBConformidadCTE(String bConformidadCTE)
  {
    this.bConformidadCTE = bConformidadCTE;
  }

  public String getBConformidadCTE()
  {
    return this.bConformidadCTE;
  }

  public void setBNuevaRevision(String bNuevaRevision)
  {
    this.bNuevaRevision = bNuevaRevision;
  }

  public String getBNuevaRevision()
  {
    return this.bNuevaRevision;
  }

  public void setADiscrepancias(String aDiscrepancias)
  {
    this.aDiscrepancias = aDiscrepancias;
  }

  public String getADiscrepancias()
  {
    return this.aDiscrepancias;
  }

  public void setFReunion(String fReunion)
  {
    this.fReunion = fReunion;
  }

  public String getFReunion()
  {
    return this.fReunion;
  }

  public void setFFinEvaluacion(String fFinEvaluacion)
  {
    this.fFinEvaluacion = fFinEvaluacion;
  }

  public String getFFinEvaluacion()
  {
    return this.fFinEvaluacion;
  }

  public void setBSimulacion(String bSimulacion)
  {
    this.bSimulacion = bSimulacion;
  }

  public String getBSimulacion()
  {
    return this.bSimulacion;
  }

  public void setBDecisionCE(String bDecisionCE)
  {
    this.bDecisionCE = bDecisionCE;
  }

  public String getBDecisionCE()
  {
    return this.bDecisionCE;
  }

  public void setFInformeCC(String fInformeCC)
  {
    this.fInformeCC = fInformeCC;
  }

  public String getFInformeCC()
  {
    return this.fInformeCC;
  }

  public void setBReconocimientoGrado(String bReconocimientoGrado)
  {
    this.bReconocimientoGrado = bReconocimientoGrado;
  }

  public String getBReconocimientoGrado()
  {
    return this.bReconocimientoGrado;
  }

  public void setBSegundaEvaluacion(String bSegundaEvaluacion)
  {
    this.bSegundaEvaluacion = bSegundaEvaluacion;
  }

  public String getBSegundaEvaluacion()
  {
    return this.bSegundaEvaluacion;
  }

  public void setBAuditoria(String bAuditoria)
  {
    this.bAuditoria = bAuditoria;
  }

  public String getBAuditoria()
  {
    return this.bAuditoria;
  }

  public void setAMotivoAuditoria(String aMotivoAuditoria)
  {
    this.aMotivoAuditoria = aMotivoAuditoria;
  }

  public String getAMotivoAuditoria()
  {
    return this.aMotivoAuditoria;
  }

  public void setALugarRealizacion(String aLugarRealizacion)
  {
    this.aLugarRealizacion = aLugarRealizacion;
  }

  public String getALugarRealizacion()
  {
    return this.aLugarRealizacion;
  }

  public void setFAuditoria(String fAuditoria)
  {
    this.fAuditoria = fAuditoria;
  }

  public String getFAuditoria()
  {
    return this.fAuditoria;
  }

  public void setASuperior(String aSuperior)
  {
    this.aSuperior = aSuperior;
  }

  public String getASuperior()
  {
    return this.aSuperior;
  }

  public void setAHallazgos(String aHallazgos)
  {
    this.aHallazgos = aHallazgos;
  }

  public String getAHallazgos()
  {
    return this.aHallazgos;
  }

  public void setBCumplimiento(String bCumplimiento)
  {
    this.bCumplimiento = bCumplimiento;
  }

  public String getBCumplimiento()
  {
    return this.bCumplimiento;
  }

  public void setBCategorizacion(String bCategorizacion)
  {
    this.bCategorizacion = bCategorizacion;
  }

  public String getBCategorizacion()
  {
    return this.bCategorizacion;
  }

  public void setARecomendaciones(String aRecomendaciones)
  {
    this.aRecomendaciones = aRecomendaciones;
  }

  public String getARecomendaciones()
  {
    return this.aRecomendaciones;
  }

  public void setAConclusiones(String aConclusiones)
  {
    this.aConclusiones = aConclusiones;
  }

  public String getAConclusiones()
  {
    return this.aConclusiones;
  }

  public void setBAuditable(String bAuditable)
  {
    this.bAuditable = bAuditable;
  }

  public String getBAuditable()
  {
    return this.bAuditable;
  }

  public void setATipoRegistro(String aTipoRegistro)
  {
    this.aTipoRegistro = aTipoRegistro;
  }

  public String getATipoRegistro()
  {
    return this.aTipoRegistro;
  }

  public void setADocumento(String aDocumento)
  {
    this.aDocumento = aDocumento;
  }

  public String getADocumento()
  {
    return this.aDocumento;
  }

  public void setANHistoria1(String aNHistoria1)
  {
    this.aNHistoria1 = aNHistoria1;
  }

  public String getANHistoria1()
  {
    return this.aNHistoria1;
  }

  public void setANHistoria2(String aNHistoria2)
  {
    this.aNHistoria2 = aNHistoria2;
  }

  public String getANHistoria2()
  {
    return this.aNHistoria2;
  }

  public void setANHistoria3(String aNHistoria3)
  {
    this.aNHistoria3 = aNHistoria3;
  }

  public String getANHistoria3()
  {
    return this.aNHistoria3;
  }

  public void setContadorAuditorias(int contadorAuditorias)
  {
    this.contadorAuditorias = contadorAuditorias;
  }

  public int getContadorAuditorias()
  {
    return this.contadorAuditorias;
  }

  public void setFAuditoriaPropuesta(String fAuditoriaPropuesta)
  {
    this.fAuditoriaPropuesta = fAuditoriaPropuesta;
  }

  public String getFAuditoriaPropuesta()
  {
    return this.fAuditoriaPropuesta;
  }

  public void setHAuditoriaPropuesta(String hAuditoriaPropuesta)
  {
    this.hAuditoriaPropuesta = hAuditoriaPropuesta;
  }

  public String getHAuditoriaPropuesta()
  {
    return this.hAuditoriaPropuesta;
  }

  public void setBAutoEvaluacion(String bAutoEvaluacion)
  {
    this.bAutoEvaluacion = bAutoEvaluacion;
  }

  public String getBAutoEvaluacion()
  {
    return this.bAutoEvaluacion;
  }

  public void setB2Evaluacion(String b2Evaluacion)
  {
    this.b2Evaluacion = b2Evaluacion;
  }

  public String getB2Evaluacion()
  {
    return this.b2Evaluacion;
  }

  public void setBAnalisis(String bAnalisis)
  {
    this.bAnalisis = bAnalisis;
  }

  public String getBAnalisis()
  {
    return this.bAnalisis;
  }

  public void setAResultados(String aResultados)
  {
    this.aResultados = aResultados;
  }

  public String getAResultados()
  {
    return this.aResultados;
  }

  public void setDModAnterior(String dModAnterior)
  {
    this.dModAnterior = dModAnterior;
  }

  public String getDModAnterior()
  {
    return this.dModAnterior;
  }

  public void setDProcedimiento(String dProcedimiento)
  {
    this.dProcedimiento = dProcedimiento;
  }

  public String getDProcedimiento()
  {
    return this.dProcedimiento;
  }

  public void setCJuridicoCombo(String cJuridicoCombo)
  {
    this.cJuridicoCombo = cJuridicoCombo;
  }

  public String getCJuridicoCombo()
  {
    return this.cJuridicoCombo;
  }

  public void setDRegimenJuridicoOtros(String dRegimenJuridicoOtros)
  {
    this.dRegimenJuridicoOtros = dRegimenJuridicoOtros;
  }

  public String getDRegimenJuridicoOtros()
  {
    return this.dRegimenJuridicoOtros;
  }

  public void setCPersonalId(String cPersonalId)
  {
    this.cPersonalId = cPersonalId;
  }

  public String getCPersonalId()
  {
    return this.cPersonalId;
  }

  public void setNAniosantiguedad(String nAniosantiguedad)
  {
    this.nAniosantiguedad = nAniosantiguedad;
  }

  public String getNAniosantiguedad()
  {
    return this.nAniosantiguedad;
  }

  public void setNMesesantiguedad(String nMesesantiguedad)
  {
    this.nMesesantiguedad = nMesesantiguedad;
  }

  public String getNMesesantiguedad()
  {
    return this.nMesesantiguedad;
  }

  public void setNDiasantiguedad(String nDiasantiguedad)
  {
    this.nDiasantiguedad = nDiasantiguedad;
  }

  public String getNDiasantiguedad()
  {
    return this.nDiasantiguedad;
  }

  public void setCProcedimientoId(String cProcedimientoId)
  {
    this.cProcedimientoId = cProcedimientoId;
  }

  public String getCProcedimientoId()
  {
    return this.cProcedimientoId;
  }

  public void setIdEvidencia(String[] idEvidencia)
  {
    this.idEvidencia = idEvidencia;
  }

  public String[] getIdEvidencia()
  {
    return this.idEvidencia;
  }

  public void setNEvidencia(String nEvidencia)
  {
    this.nEvidencia = nEvidencia;
  }

  public String getNEvidencia()
  {
    return this.nEvidencia;
  }

  public void setCPadreEvidenciaId(String cPadreEvidenciaId)
  {
    this.cPadreEvidenciaId = cPadreEvidenciaId;
  }

  public String getCPadreEvidenciaId()
  {
    return this.cPadreEvidenciaId;
  }

  public void setCEvidenciaDocumentalId(String cEvidenciaDocumentalId)
  {
    this.cEvidenciaDocumentalId = cEvidenciaDocumentalId;
  }

  public String getCEvidenciaDocumentalId()
  {
    return this.cEvidenciaDocumentalId;
  }

  public void setListaEviDocumentales(ArrayList listaEviDocumentales)
  {
    this.listaEviDocumentales = listaEviDocumentales;
  }

  public ArrayList getListaEviDocumentales()
  {
    return this.listaEviDocumentales;
  }

  public void setBEraRepe0(String bEraRepe0)
  {
    this.bEraRepe0 = bEraRepe0;
  }

  public String getBEraRepe0()
  {
    return this.bEraRepe0;
  }

  public void setCCuestionarioPadreId(long cCuestionarioPadreId)
  {
    this.cCuestionarioPadreId = cCuestionarioPadreId;
  }

  public long getCCuestionarioPadreId()
  {
    return this.cCuestionarioPadreId;
  }

  public void setCPlantillaId(String cPlantillaId)
  {
    this.cPlantillaId = cPlantillaId;
  }

  public String getCPlantillaId()
  {
    return this.cPlantillaId;
  }

  public void setNCreditosEvaluadosCTE(String nCreditosEvaluadosCTE) {
    this.nCreditosEvaluadosCTE = nCreditosEvaluadosCTE;
  }

  public String getNCreditosEvaluadosCTE()
  {
    return this.nCreditosEvaluadosCTE;
  }

public String getNAnioConvocatoria() {
	return nAnioConvocatoria;
}

public void setNAnioConvocatoria(String nAnioConvocatoria) {
	this.nAnioConvocatoria = nAnioConvocatoria;
}

public String getBMostrarBtnImprimir() {
	return bMostrarBtnImprimir;
}

public void setBMostrarBtnImprimir(String bMostrarBtnImprimir) {
	this.bMostrarBtnImprimir = bMostrarBtnImprimir;
}

public String getBMostrarBtnVolver() {
	return bMostrarBtnVolver;
}

public void setBMostrarBtnVolver(String bMostrarBtnVolver) {
	this.bMostrarBtnVolver = bMostrarBtnVolver;
}

public String getNombreApellidos() {
	return nombreApellidos;
}

public void setNombreApellidos(String nombreApellidos) {
	this.nombreApellidos = nombreApellidos;
}

public String getDni() {
	return dni;
}

public void setDni(String dni) {
	this.dni = dni;
}
}

