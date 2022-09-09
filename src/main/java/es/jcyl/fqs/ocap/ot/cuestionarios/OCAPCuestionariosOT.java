 package es.jcyl.fqs.ocap.ot.cuestionarios;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Date;
 
 public class OCAPCuestionariosOT
   implements Serializable
 {
   protected String bBorrado;
   protected long cCuestionarioId;
   protected long cCuestionarioPadreId;
   protected long cCuestionarioIntermedioId;
   protected long cEvidenciaId;
   protected long cEvidenciaDocumentalId;
   protected ArrayList listaEviDocumentales;
   protected String nEvidencia;
   protected String cTipoEvidencia;
   protected long cPadreEvidenciaId;
   protected String bMostrar;
   protected long cAreaId;
   protected long cItinerarioId;
   protected String dNombreItinerario;
   protected long cEspecId;
   protected long cPuestoId;
   protected long cServicioId;
   protected long cPreguntaId;
   protected String dNombre;
   protected float nCreditos;
   protected float nCreditosConseguidos;
   protected float nPuntosConseguidos;
   protected float nCreditosEvaluados;
   protected float nCreditosFinales;
   protected Integer nRepeticiones;
   protected Integer nSubCuestionarios;
   protected Integer nPreguntas;
   protected Integer nPreguntasContestables;
   protected int nPreguntasFormula;
   protected String bNumeracion;
   protected String bObligatorio;
   protected String bContestarUsuario;
   protected String bIndicadores;
   protected String bSubdivision;
   protected String dRepeticion;
   protected String bPonderaFormula;
   protected int nMaxHijosContestados;
   protected int nHijosContestados;
   protected String bPregIndicadores;
   protected String bSimulacion;
   protected String bContestado;
   protected String bParcialmenteContestado;
   protected String dMensaje;
   protected String dEvidencia;
   protected String dObservacionesEvidencia;
   protected String dPonderacion;
   protected String dNombreLargo;
   protected int nItinerario;
   protected String dArea;
   protected String dAreaLargo;
   protected ArrayList listaRespuestas;
   protected ArrayList listaPosiblesRespuestas;
   protected ArrayList listaSubCuestionarios;
   protected ArrayList listaRepeticiones;
   protected String bVerPreguntas;
   protected String dPregunta;
   protected String dObservaciones;
   protected String dTextoElementos;
   protected String cTipoPregunta;
   protected int nElementos;
   protected int nSubElementos;
   protected int nNivel;
   protected String bCorto;
   protected String bPuntuarTodo;
   protected int nMinElemRellenos;
   protected int nMinSubElemOpcionales;
   protected String bEvaluado;
   private String bAcuerdo;
   private String bProposicion;
   private String aRazon;
   private String aComentarios;
   protected String bAnalizado;
   protected ArrayList listaDocumentos;
   protected String dNombreEvaluador;
   protected String dCodigoEvaluadorId;
   protected String aEspecificaciones;
   protected String dNombreCte;
   protected String dCategoriaEspecialidadEvaluado;
   protected String dGrado_des;
   protected String dCodigoId;
   protected String fInicioEvaluacion;
   protected String fFinEvaluacion;
   protected String dNombrePresidente;
   protected String fReunion;
   protected String bConformidadCte;
   protected String bNuevaRevision;
   protected String aDiscrepancias;
   protected String fEvaluacion;
   protected String bDecisionCe;
   protected String dConformidadCte;
   protected Date fModificacion;
   protected Date fCreacion;
   protected boolean bVerPonderacion;
   protected int cPlantillaId;
   protected String bMostrarBtnImprimir;
   protected String bMostrarBtnVolver;
   protected Double nAutoponderacionMax;
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setCCuestionarioId(long cCuestionarioId)
   {
     this.cCuestionarioId = cCuestionarioId;
   }
 
   public long getCCuestionarioId()
   {
     return this.cCuestionarioId;
   }
 
   public void setCEspecId(long cEspecId)
   {
     this.cEspecId = cEspecId;
   }
 
   public long getCEspecId()
   {
     return this.cEspecId;
   }
 
   public void setCPuestoId(long cPuestoId)
   {
     this.cPuestoId = cPuestoId;
   }
 
   public long getCPuestoId()
   {
     return this.cPuestoId;
   }
 
   public void setCServicioId(long cServicioId)
   {
     this.cServicioId = cServicioId;
   }
 
   public long getCServicioId()
   {
     return this.cServicioId;
   }
 
   public void setCPreguntaId(long cPreguntaId)
   {
     this.cPreguntaId = cPreguntaId;
   }
 
   public long getCPreguntaId()
   {
     return this.cPreguntaId;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setNCreditos(float nCreditos)
   {
     this.nCreditos = nCreditos;
   }
 
   public float getNCreditos()
   {
     return this.nCreditos;
   }
 
   public void setBContestado(String bContestado)
   {
     this.bContestado = bContestado;
   }
 
   public String getBContestado()
   {
     return this.bContestado;
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
 
   public void setListaRespuestas(ArrayList listaRespuestas)
   {
     this.listaRespuestas = listaRespuestas;
   }
 
   public ArrayList getListaRespuestas()
   {
     return this.listaRespuestas;
   }
 
   public void setListaPosiblesRespuestas(ArrayList listaPosiblesRespuestas)
   {
     this.listaPosiblesRespuestas = listaPosiblesRespuestas;
   }
 
   public ArrayList getListaPosiblesRespuestas()
   {
     return this.listaPosiblesRespuestas;
   }
 
   public void setDPregunta(String dPregunta)
   {
     this.dPregunta = dPregunta;
   }
 
   public String getDPregunta()
   {
     return this.dPregunta;
   }
 
   public void setDObservaciones(String dObservaciones)
   {
     this.dObservaciones = dObservaciones;
   }
 
   public String getDObservaciones()
   {
     return this.dObservaciones;
   }
 
   public void setCTipoPregunta(String cTipoPregunta)
   {
     this.cTipoPregunta = cTipoPregunta;
   }
 
   public String getCTipoPregunta()
   {
     return this.cTipoPregunta;
   }
 
   public void setNElementos(int nElementos)
   {
     this.nElementos = nElementos;
   }
 
   public int getNElementos()
   {
     return this.nElementos;
   }
 
   public void setNSubElementos(int nSubElementos)
   {
     this.nSubElementos = nSubElementos;
   }
 
   public int getNSubElementos()
   {
     return this.nSubElementos;
   }
 
   public void setNNivel(int nNivel)
   {
     this.nNivel = nNivel;
   }
 
   public int getNNivel()
   {
     return this.nNivel;
   }
 
   public void setFModificacion(Date fModificacion)
   {
     this.fModificacion = fModificacion;
   }
 
   public Date getFModificacion()
   {
     return this.fModificacion;
   }
 
   public void setFCreacion(Date fCreacion)
   {
     this.fCreacion = fCreacion;
   }
 
   public Date getFCreacion()
   {
     return this.fCreacion;
   }
 
   public void setNRepeticiones(Integer nRepeticiones)
   {
     this.nRepeticiones = nRepeticiones;
   }
 
   public Integer getNRepeticiones()
   {
     return this.nRepeticiones;
   }
 
   public void setNSubCuestionarios(Integer nSubCuestionarios)
   {
     this.nSubCuestionarios = nSubCuestionarios;
   }
 
   public Integer getNSubCuestionarios()
   {
     return this.nSubCuestionarios;
   }
 
   public void setNPreguntas(Integer nPreguntas)
   {
     this.nPreguntas = nPreguntas;
   }
 
   public Integer getNPreguntas()
   {
     return this.nPreguntas;
   }
 
   public void setCAreaId(long cAreaId)
   {
     this.cAreaId = cAreaId;
   }
 
   public long getCAreaId()
   {
     return this.cAreaId;
   }
 
   public void setCItinerarioId(long cItinerarioId)
   {
     this.cItinerarioId = cItinerarioId;
   }
 
   public long getCItinerarioId()
   {
     return this.cItinerarioId;
   }
 
   public void setCCuestionarioPadreId(long cCuestionarioPadreId)
   {
     this.cCuestionarioPadreId = cCuestionarioPadreId;
   }
 
   public long getCCuestionarioPadreId()
   {
     return this.cCuestionarioPadreId;
   }
 
   public void setListaSubCuestionarios(ArrayList listaSubCuestionarios)
   {
     this.listaSubCuestionarios = listaSubCuestionarios;
   }
 
   public ArrayList getListaSubCuestionarios()
   {
     return this.listaSubCuestionarios;
   }
 
   public void setListaRepeticiones(ArrayList listaRepeticiones)
   {
     this.listaRepeticiones = listaRepeticiones;
   }
 
   public ArrayList getListaRepeticiones()
   {
     return this.listaRepeticiones;
   }
 
   public void setBVerPreguntas(String vBerPreguntas)
   {
     this.bVerPreguntas = vBerPreguntas;
   }
 
   public String getBVerPreguntas()
   {
     return this.bVerPreguntas;
   }
 
   public void setDMensaje(String dMensaje)
   {
     this.dMensaje = dMensaje;
   }
 
   public String getDMensaje()
   {
     return this.dMensaje;
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
 
   public void setBNumeracion(String bNumeracion)
   {
     this.bNumeracion = bNumeracion;
   }
 
   public String getBNumeracion()
   {
     return this.bNumeracion;
   }
 
   public void setBObligatorio(String bObligatorio)
   {
     this.bObligatorio = bObligatorio;
   }
 
   public String getBObligatorio()
   {
     return this.bObligatorio;
   }
 
   public void setCCuestionarioIntermedioId(long cCuestionarioIntermedioId)
   {
     this.cCuestionarioIntermedioId = cCuestionarioIntermedioId;
   }
 
   public long getCCuestionarioIntermedioId()
   {
     return this.cCuestionarioIntermedioId;
   }
 
   public void setBContestarUsuario(String bContestarUsuario)
   {
     this.bContestarUsuario = bContestarUsuario;
   }
 
   public String getBContestarUsuario()
   {
     return this.bContestarUsuario;
   }
 
   public void setDObservacionesEvidencia(String dObservacionesEvidencia)
   {
     this.dObservacionesEvidencia = dObservacionesEvidencia;
   }
 
   public String getDObservacionesEvidencia()
   {
     return this.dObservacionesEvidencia;
   }
 
   public void setNPreguntasContestables(Integer nPreguntasContestables)
   {
     this.nPreguntasContestables = nPreguntasContestables;
   }
 
   public Integer getNPreguntasContestables()
   {
     return this.nPreguntasContestables;
   }
 
   public void setBCorto(String bCorto)
   {
     this.bCorto = bCorto;
   }
 
   public String getBCorto()
   {
     return this.bCorto;
   }
 
   public void setNCreditosConseguidos(float nCreditosConseguidos)
   {
     this.nCreditosConseguidos = nCreditosConseguidos;
   }
 
   public float getNCreditosConseguidos()
   {
     return this.nCreditosConseguidos;
   }
 
   public void setNPuntosConseguidos(float nPuntosConseguidos)
   {
     this.nPuntosConseguidos = nPuntosConseguidos;
   }
 
   public float getNPuntosConseguidos()
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
 
   public void setDTextoElementos(String dTextoElementos)
   {
     this.dTextoElementos = dTextoElementos;
   }
 
   public String getDTextoElementos()
   {
     return this.dTextoElementos;
   }
   public void setBIndicadores(String bIndicadores) {
     this.bIndicadores = bIndicadores;
   }
 
   public String getBIndicadores()
   {
     return this.bIndicadores;
   }
 
   public void setBSubdivision(String bSubdivision)
   {
     this.bSubdivision = bSubdivision;
   }
 
   public String getBSubdivision()
   {
     return this.bSubdivision;
   }
 
   public void setDRepeticion(String dRepeticion)
   {
     this.dRepeticion = dRepeticion;
   }
 
   public String getDRepeticion()
   {
     return this.dRepeticion;
   }
 
   public void setCEvidenciaId(long cEvidenciaId)
   {
     this.cEvidenciaId = cEvidenciaId;
   }
 
   public long getCEvidenciaId()
   {
     return this.cEvidenciaId;
   }
 
   public void setCTipoEvidencia(String cTipoEvidencia)
   {
     this.cTipoEvidencia = cTipoEvidencia;
   }
 
   public String getCTipoEvidencia()
   {
     return this.cTipoEvidencia;
   }
 
   public void setBEvaluado(String bEvaluado)
   {
     this.bEvaluado = bEvaluado;
   }
 
   public String getBEvaluado()
   {
     return this.bEvaluado;
   }
 
   public void setNCreditosEvaluados(float nCreditosEvaluados)
   {
     this.nCreditosEvaluados = nCreditosEvaluados;
   }
 
   public float getNCreditosEvaluados()
   {
     return this.nCreditosEvaluados;
   }
 
   public void setBParcialmenteContestado(String bParcialmenteContestado)
   {
     this.bParcialmenteContestado = bParcialmenteContestado;
   }
 
   public String getBParcialmenteContestado()
   {
     return this.bParcialmenteContestado;
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
 
   public void setBSimulacion(String bSimulacion)
   {
     this.bSimulacion = bSimulacion;
   }
 
   public String getBSimulacion()
   {
     return this.bSimulacion;
   }
 
   public void setBAnalizado(String bAnalizado)
   {
     this.bAnalizado = bAnalizado;
   }
 
   public String getBAnalizado()
   {
     return this.bAnalizado;
   }
 
   public void setBPuntuarTodo(String bPuntuarTodo)
   {
     this.bPuntuarTodo = bPuntuarTodo;
   }
 
   public String getBPuntuarTodo()
   {
     return this.bPuntuarTodo;
   }
 
   public void setNPreguntasFormula(int nPreguntasFormula)
   {
     this.nPreguntasFormula = nPreguntasFormula;
   }
 
   public int getNPreguntasFormula()
   {
     return this.nPreguntasFormula;
   }
 
   public void setBPonderaFormula(String bPonderaFormula)
   {
     this.bPonderaFormula = bPonderaFormula;
   }
 
   public String getBPonderaFormula()
   {
     return this.bPonderaFormula;
   }
 
   public void setNMinElemRellenos(int nMinElemRellenos)
   {
     this.nMinElemRellenos = nMinElemRellenos;
   }
 
   public int getNMinElemRellenos()
   {
     return this.nMinElemRellenos;
   }
 
   public void setNMinSubElemOpcionales(int nMinSubElemOpcionales)
   {
     this.nMinSubElemOpcionales = nMinSubElemOpcionales;
   }
 
   public int getNMinSubElemOpcionales()
   {
     return this.nMinSubElemOpcionales;
   }
 
   public void setNMaxHijosContestados(int nMaxHijosContestados)
   {
     this.nMaxHijosContestados = nMaxHijosContestados;
   }
 
   public int getNMaxHijosContestados()
   {
     return this.nMaxHijosContestados;
   }
 
   public void setNHijosContestados(int nHijosContestados)
   {
     this.nHijosContestados = nHijosContestados;
   }
 
   public int getNHijosContestados()
   {
     return this.nHijosContestados;
   }
 
   public void setBPregIndicadores(String bPregIndicadores)
   {
     this.bPregIndicadores = bPregIndicadores;
   }
 
   public String getBPregIndicadores()
   {
     return this.bPregIndicadores;
   }
 
   public void setNCreditosFinales(float nCreditosFinales)
   {
     this.nCreditosFinales = nCreditosFinales;
   }
 
   public float getNCreditosFinales()
   {
     return this.nCreditosFinales;
   }
 
   public void setDNombreItinerario(String dNombreItinerario)
   {
     this.dNombreItinerario = dNombreItinerario;
   }
 
   public String getDNombreItinerario()
   {
     return this.dNombreItinerario;
   }
 
   public void setDNombreEvaluador(String dNombreEvaluador)
   {
     this.dNombreEvaluador = dNombreEvaluador;
   }
 
   public String getDNombreEvaluador()
   {
     return this.dNombreEvaluador;
   }
 
   public void setDCodigoId(String dCodigoId)
   {
     this.dCodigoId = dCodigoId;
   }
 
   public String getDCodigoId()
   {
     return this.dCodigoId;
   }
 
   public void setAEspecificaciones(String aEspecificaciones)
   {
     this.aEspecificaciones = aEspecificaciones;
   }
 
   public String getAEspecificaciones()
   {
     return this.aEspecificaciones;
   }
 
   public void setDNombreCte(String dNombreCte)
   {
     this.dNombreCte = dNombreCte;
   }
 
   public String getDNombreCte()
   {
     return this.dNombreCte;
   }
 
   public void setDCategoriaEspecialidadEvaluado(String dCategoriaEspecialidadEvaluado)
   {
     this.dCategoriaEspecialidadEvaluado = dCategoriaEspecialidadEvaluado;
   }
 
   public String getDCategoriaEspecialidadEvaluado()
   {
     return this.dCategoriaEspecialidadEvaluado;
   }
 
   public void setDGrado_des(String dGrado_des)
   {
     this.dGrado_des = dGrado_des;
   }
 
   public String getDGrado_des()
   {
     return this.dGrado_des;
   }
 
   public void setDCodigoEvaluadorId(String dCodigoEvaluadorId)
   {
     this.dCodigoEvaluadorId = dCodigoEvaluadorId;
   }
 
   public String getDCodigoEvaluadorId()
   {
     return this.dCodigoEvaluadorId;
   }
 
   public void setFInicioEvaluacion(String fInicioEvaluacion)
   {
     this.fInicioEvaluacion = fInicioEvaluacion;
   }
 
   public String getFInicioEvaluacion()
   {
     return this.fInicioEvaluacion;
   }
 
   public void setFFinEvaluacion(String fFinEvaluacion)
   {
     this.fFinEvaluacion = fFinEvaluacion;
   }
 
   public String getFFinEvaluacion()
   {
     return this.fFinEvaluacion;
   }
 
   public void setDNombrePresidente(String dNombrePresidente)
   {
     this.dNombrePresidente = dNombrePresidente;
   }
 
   public String getDNombrePresidente()
   {
     return this.dNombrePresidente;
   }
 
   public void setFReunion(String fReunion)
   {
     this.fReunion = fReunion;
   }
 
   public String getFReunion()
   {
     return this.fReunion;
   }
 
   public void setBConformidadCte(String bConformidadCte)
   {
     this.bConformidadCte = bConformidadCte;
   }
 
   public String getBConformidadCte()
   {
     return this.bConformidadCte;
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
 
   public void setFEvaluacion(String fEvaluacion)
   {
     this.fEvaluacion = fEvaluacion;
   }
 
   public String getFEvaluacion()
   {
     return this.fEvaluacion;
   }
 
   public void setBDecisionCe(String bDecisionCe)
   {
     this.bDecisionCe = bDecisionCe;
   }
 
   public String getBDecisionCe()
   {
     return this.bDecisionCe;
   }
 
   public void setDConformidadCte(String dConformidadCte)
   {
     this.dConformidadCte = dConformidadCte;
   }
 
   public String getDConformidadCte()
   {
     return this.dConformidadCte;
   }
 
   public void setNEvidencia(String nEvidencia)
   {
     this.nEvidencia = nEvidencia;
   }
 
   public String getNEvidencia()
   {
     return this.nEvidencia;
   }
 
   public void setCPadreEvidenciaId(long cPadreEvidenciaId)
   {
     this.cPadreEvidenciaId = cPadreEvidenciaId;
   }
 
   public long getCPadreEvidenciaId()
   {
     return this.cPadreEvidenciaId;
   }
 
   public void setBVerPonderacion(boolean bVerPonderacion)
   {
     this.bVerPonderacion = bVerPonderacion;
   }
 
   public boolean getBVerPonderacion()
   {
     return this.bVerPonderacion;
   }
 
   public void setCEvidenciaDocumentalId(long cEvidenciaDocumentalId)
   {
     this.cEvidenciaDocumentalId = cEvidenciaDocumentalId;
   }
 
   public long getCEvidenciaDocumentalId()
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
 
   public void setBMostrar(String bMostrar)
   {
     this.bMostrar = bMostrar;
   }
 
   public String getBMostrar()
   {
     return this.bMostrar;
   }
 
   public void setCPlantillaId(int cPlantillaId) {
     this.cPlantillaId = cPlantillaId;
   }
 
   public int getCPlantillaId()
   {
     return this.cPlantillaId;
   }

public String getBMostrarBtnImprimir() {
	return bMostrarBtnImprimir;
}

public void setBMostrarBtnImprimir(String bMostrarBtnImprimir) {
	this.bMostrarBtnImprimir = bMostrarBtnImprimir;
}

public String getbMostrarBtnVolver() {
	return bMostrarBtnVolver;
}

public void setbMostrarBtnVolver(String bMostrarBtnVolver) {
	this.bMostrarBtnVolver = bMostrarBtnVolver;
}

public Double getNAutoponderacionMax() {
	return this.nAutoponderacionMax;
}

public void setNAutoponderacionMax(Double autoponderacion) {
	if (autoponderacion != null) {
		autoponderacion = Math.round(autoponderacion * 100.0D) / 100.0D;
	}
		this.nAutoponderacionMax = autoponderacion;
}
 }

