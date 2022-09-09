 package es.jcyl.fqs.ocap.ot.componentesfqs;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Date;
 
 public class OCAPComponentesfqsOT
   implements Serializable
 {
   protected long cCompfqsId;
   protected String dNombre;
   protected String dApellidos;
   protected String cDni;
   protected String nTelefono1;
   protected String aCorreoelectronico;
   protected String aDirNombre;
   protected String aDirNum;
   protected String aDirEscalera;
   protected String aDirPiso;
   protected String aDirLetra;
   protected String cCp;
   protected String cProvId;
   protected String cLocalidadId;
   protected String aCreadoPorComponente;
   protected String aModificadoPorComponente;
   protected long cPerfilId;
   protected String dPerfil;
   protected String bSexo;
   protected String dApellNom;
   protected String cComunidadId;
   protected long cExptecompfqsId;
   protected String aTitulacion;
   protected String aEspecialidad;
   protected String cComuniId;
   protected String cProvinId;
   protected String aFormAcreditacion;
   protected String aFormGestion;
   protected String aFormComunicacion;
   protected String aFormEvaluacion;
   protected long cGradoId;
   protected String aCreadoPorExp;
   protected String aModificadoPorExp;
   protected String aCategoria;
   protected String aCargo;
   protected String aDatosprofesionales;
   protected String aCentrotrabajo;
   protected String fVinculacion;
   protected String fFinalizacion;
   protected String dCteNombre;
   protected String aExpprofSs;
   protected String aExpcalAsistencia;
   protected String aActDocente;
   protected String nAniosConv;
   protected long cConvocatoriaId;
   protected long cCteId;
   protected long cProfesionalId;
   protected long cEspecId;
   protected long cItinerarioId;
   protected String dItinerarioNombre;
   protected String dProfesionalNombre;
   protected String dEspecNombre;
   protected String dConvocNombre;
   protected long cCompfqsConvoId;
   protected ArrayList listaEvalCte;
   protected ArrayList listaEvaluados;
   protected long nEvaluacionesevaluador;
   protected long nInformesrecibidos;
   protected long nInformespositivos;
   protected long nInformesnegativos;
   protected String codigoId;
   protected String codigoEvalId;
   protected String dNombrePresidente;
   protected Date fInformeCE;
   protected String bEvaluacion2;
 
   public void setCCompfqsId(long cCompfqsId)
   {
     this.cCompfqsId = cCompfqsId;
   }
 
   public long getCCompfqsId()
   {
     return this.cCompfqsId;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDApellidos(String dApellidos)
   {
     this.dApellidos = dApellidos;
   }
 
   public String getDApellidos()
   {
     return this.dApellidos;
   }
 
   public void setCDni(String cDni)
   {
     this.cDni = cDni;
   }
 
   public String getCDni()
   {
     return this.cDni;
   }
 
   public void setNTelefono1(String nTelefono1)
   {
     this.nTelefono1 = nTelefono1;
   }
 
   public String getNTelefono1()
   {
     return this.nTelefono1;
   }
 
   public void setACorreoelectronico(String aCorreoelectronico)
   {
     this.aCorreoelectronico = aCorreoelectronico;
   }
 
   public String getACorreoelectronico()
   {
     return this.aCorreoelectronico;
   }
 
   public void setaCreadoPorComponente(String aCreadoPorComponente)
   {
     this.aCreadoPorComponente = aCreadoPorComponente;
   }
 
   public String getaCreadoPorComponente()
   {
     return this.aCreadoPorComponente;
   }
 
   public void setCConvocatoriaId(long cConvocatoriaId)
   {
     this.cConvocatoriaId = cConvocatoriaId;
   }
 
   public long getCConvocatoriaId()
   {
     return this.cConvocatoriaId;
   }
 
   public void setDConvocNombre(String dConvocNombre)
   {
     this.dConvocNombre = dConvocNombre;
   }
 
   public String getDConvocNombre()
   {
     return this.dConvocNombre;
   }
 
   public void setCExptecompfqsId(long cExptecompfqsId)
   {
     this.cExptecompfqsId = cExptecompfqsId;
   }
 
   public long getCExptecompfqsId()
   {
     return this.cExptecompfqsId;
   }
 
   public void setAFormAcreditacion(String aFormAcreditacion)
   {
     this.aFormAcreditacion = aFormAcreditacion;
   }
 
   public String getAFormAcreditacion()
   {
     return this.aFormAcreditacion;
   }
 
   public void setACreadoPorExp(String aCreadoPorExp)
   {
     this.aCreadoPorExp = aCreadoPorExp;
   }
 
   public String getACreadoPorExp()
   {
     return this.aCreadoPorExp;
   }
 
   public void setDProfesionalNombre(String dProfesionalNombre)
   {
     this.dProfesionalNombre = dProfesionalNombre;
   }
 
   public String getDProfesionalNombre()
   {
     return this.dProfesionalNombre;
   }
 
   public void setDEspecNombre(String dEspecNombre)
   {
     this.dEspecNombre = dEspecNombre;
   }
 
   public String getDEspecNombre()
   {
     return this.dEspecNombre;
   }
 
   public void setAFormGestion(String aFormGestion)
   {
     this.aFormGestion = aFormGestion;
   }
 
   public String getAFormGestion()
   {
     return this.aFormGestion;
   }
 
   public void setAFormComunicacion(String aFormComunicacion)
   {
     this.aFormComunicacion = aFormComunicacion;
   }
 
   public String getAFormComunicacion()
   {
     return this.aFormComunicacion;
   }
 
   public void setAFormEvaluacion(String aFormEvaluacion)
   {
     this.aFormEvaluacion = aFormEvaluacion;
   }
 
   public String getAFormEvaluacion()
   {
     return this.aFormEvaluacion;
   }
 
   public void setAModificadoPorComponente(String aModificadoPorComponente)
   {
     this.aModificadoPorComponente = aModificadoPorComponente;
   }
 
   public String getAModificadoPorComponente()
   {
     return this.aModificadoPorComponente;
   }
 
   public void setAModificadoPorExp(String aModificadoPorExp)
   {
     this.aModificadoPorExp = aModificadoPorExp;
   }
 
   public String getAModificadoPorExp()
   {
     return this.aModificadoPorExp;
   }
 
   public void setCPerfilId(long cPerfilId)
   {
     this.cPerfilId = cPerfilId;
   }
 
   public long getCPerfilId()
   {
     return this.cPerfilId;
   }
 
   public void setADirNombre(String aDirNombre)
   {
     this.aDirNombre = aDirNombre;
   }
 
   public String getADirNombre()
   {
     return this.aDirNombre;
   }
 
   public void setADirNum(String aDirNum)
   {
     this.aDirNum = aDirNum;
   }
 
   public String getADirNum()
   {
     return this.aDirNum;
   }
 
   public void setADirEscalera(String aDirEscalera)
   {
     this.aDirEscalera = aDirEscalera;
   }
 
   public String getADirEscalera()
   {
     return this.aDirEscalera;
   }
 
   public void setADirPiso(String aDirPiso)
   {
     this.aDirPiso = aDirPiso;
   }
 
   public String getADirPiso()
   {
     return this.aDirPiso;
   }
 
   public void setADirLetra(String aDirLetra)
   {
     this.aDirLetra = aDirLetra;
   }
 
   public String getADirLetra()
   {
     return this.aDirLetra;
   }
 
   public void setCCp(String cCp)
   {
     this.cCp = cCp;
   }
 
   public String getCCp()
   {
     return this.cCp;
   }
 
   public void setCProvId(String cProvId)
   {
     this.cProvId = cProvId;
   }
 
   public String getCProvId()
   {
     return this.cProvId;
   }
 
   public void setCLocalidadId(String cLocalidadId)
   {
     this.cLocalidadId = cLocalidadId;
   }
 
   public String getCLocalidadId()
   {
     return this.cLocalidadId;
   }
 
   public void setCCteId(long cCteId)
   {
     this.cCteId = cCteId;
   }
 
   public long getCCteId()
   {
     return this.cCteId;
   }
 
   public void setADatosprofesionales(String aDatosprofesionales)
   {
     this.aDatosprofesionales = aDatosprofesionales;
   }
 
   public String getADatosprofesionales()
   {
     return this.aDatosprofesionales;
   }
 
   public void setFVinculacion(String fVinculacion)
   {
     this.fVinculacion = fVinculacion;
   }
 
   public String getFVinculacion()
   {
     return this.fVinculacion;
   }
 
   public void setFFinalizacion(String fFinalizacion)
   {
     this.fFinalizacion = fFinalizacion;
   }
 
   public String getFFinalizacion()
   {
     return this.fFinalizacion;
   }
 
   public void setDApellNom(String dApellNom)
   {
     this.dApellNom = dApellNom;
   }
 
   public String getDApellNom()
   {
     return this.dApellNom;
   }
 
   public void setBSexo(String bSexo)
   {
     this.bSexo = bSexo;
   }
 
   public String getBSexo()
   {
     return this.bSexo;
   }
 
   public void setDCteNombre(String dCteNombre)
   {
     this.dCteNombre = dCteNombre;
   }
 
   public String getDCteNombre()
   {
     return this.dCteNombre;
   }
 
   public void setATitulacion(String aTitulacion)
   {
     this.aTitulacion = aTitulacion;
   }
 
   public String getATitulacion()
   {
     return this.aTitulacion;
   }
 
   public void setAEspecialidad(String aEspecialidad)
   {
     this.aEspecialidad = aEspecialidad;
   }
 
   public String getAEspecialidad()
   {
     return this.aEspecialidad;
   }
 
   public void setCComuniId(String cComuniId)
   {
     this.cComuniId = cComuniId;
   }
 
   public String getCComuniId()
   {
     return this.cComuniId;
   }
 
   public void setAExpprofSs(String aExpprofSs)
   {
     this.aExpprofSs = aExpprofSs;
   }
 
   public String getAExpprofSs()
   {
     return this.aExpprofSs;
   }
 
   public void setAExpcalAsistencia(String aExpcalAsistencia)
   {
     this.aExpcalAsistencia = aExpcalAsistencia;
   }
 
   public String getAExpcalAsistencia()
   {
     return this.aExpcalAsistencia;
   }
 
   public void setAActDocente(String aActDocente)
   {
     this.aActDocente = aActDocente;
   }
 
   public String getAActDocente()
   {
     return this.aActDocente;
   }
 
   public void setCProvinId(String cProvinId)
   {
     this.cProvinId = cProvinId;
   }
 
   public String getCProvinId()
   {
     return this.cProvinId;
   }
 
   public void setACentrotrabajo(String aCentrotrabajo)
   {
     this.aCentrotrabajo = aCentrotrabajo;
   }
 
   public String getACentrotrabajo()
   {
     return this.aCentrotrabajo;
   }
 
   public void setCGradoId(long cGradoId)
   {
     this.cGradoId = cGradoId;
   }
 
   public long getCGradoId()
   {
     return this.cGradoId;
   }
 
   public void setACategoria(String aCategoria)
   {
     this.aCategoria = aCategoria;
   }
 
   public String getACategoria()
   {
     return this.aCategoria;
   }
 
   public void setACargo(String aCargo)
   {
     this.aCargo = aCargo;
   }
 
   public String getACargo()
   {
     return this.aCargo;
   }
 
   public void setDItinerarioNombre(String dItinerarioNombre) {
     this.dItinerarioNombre = dItinerarioNombre;
   }
 
   public String getDItinerarioNombre()
   {
     return this.dItinerarioNombre;
   }
 
   public void setCProfesionalId(long cProfesionalId)
   {
     this.cProfesionalId = cProfesionalId;
   }
 
   public long getCProfesionalId()
   {
     return this.cProfesionalId;
   }
 
   public void setCEspecId(long cEspecId)
   {
     this.cEspecId = cEspecId;
   }
 
   public long getCEspecId()
   {
     return this.cEspecId;
   }
 
   public void setCItinerarioId(long cItinerarioId)
   {
     this.cItinerarioId = cItinerarioId;
   }
 
   public long getCItinerarioId()
   {
     return this.cItinerarioId;
   }
 
   public void setNAniosConv(String nAniosConv)
   {
     this.nAniosConv = nAniosConv;
   }
 
   public String getNAniosConv()
   {
     return this.nAniosConv;
   }
 
   public void setCCompfqsConvoId(long cCompfqsConvoId)
   {
     this.cCompfqsConvoId = cCompfqsConvoId;
   }
 
   public long getCCompfqsConvoId()
   {
     return this.cCompfqsConvoId;
   }
 
   public void setListaEvalCte(ArrayList listaEvalCte)
   {
     this.listaEvalCte = listaEvalCte;
   }
 
   public ArrayList getListaEvalCte()
   {
     return this.listaEvalCte;
   }
 
   public void setCComunidadId(String cComunidadId)
   {
     this.cComunidadId = cComunidadId;
   }
 
   public String getCComunidadId()
   {
     return this.cComunidadId;
   }
 
   public void setNEvaluacionesevaluador(long nEvaluacionesevaluador)
   {
     this.nEvaluacionesevaluador = nEvaluacionesevaluador;
   }
 
   public long getNEvaluacionesevaluador()
   {
     return this.nEvaluacionesevaluador;
   }
 
   public void setNInformesrecibidos(long nInformesrecibidos)
   {
     this.nInformesrecibidos = nInformesrecibidos;
   }
 
   public long getNInformesrecibidos()
   {
     return this.nInformesrecibidos;
   }
 
   public void setNInformespositivos(long nInformespositivos)
   {
     this.nInformespositivos = nInformespositivos;
   }
 
   public long getNInformespositivos()
   {
     return this.nInformespositivos;
   }
 
   public void setNInformesnegativos(long nInformesnegativos)
   {
     this.nInformesnegativos = nInformesnegativos;
   }
 
   public long getNInformesnegativos()
   {
     return this.nInformesnegativos;
   }
 
   public void setCodigoId(String codigoId)
   {
     this.codigoId = codigoId;
   }
 
   public String getCodigoId()
   {
     return this.codigoId;
   }
 
   public void setDNombrePresidente(String dNombrePresidente)
   {
     this.dNombrePresidente = dNombrePresidente;
   }
 
   public String getDNombrePresidente()
   {
     return this.dNombrePresidente;
   }
 
   public void setListaEvaluados(ArrayList listaEvaluados)
   {
     this.listaEvaluados = listaEvaluados;
   }
 
   public ArrayList getListaEvaluados()
   {
     return this.listaEvaluados;
   }
 
   public void setCodigoEvalId(String codigoEvalId)
   {
     this.codigoEvalId = codigoEvalId;
   }
 
   public String getCodigoEvalId()
   {
     return this.codigoEvalId;
   }
 
   public void setFInformeCE(Date fInformeCE)
   {
     this.fInformeCE = fInformeCE;
   }
 
   public Date getFInformeCE()
   {
     return this.fInformeCE;
   }
 
   public void setBEvaluacion2(String bEvaluacion2)
   {
     this.bEvaluacion2 = bEvaluacion2;
   }
 
   public String getBEvaluacion2()
   {
     return this.bEvaluacion2;
   }
 
   public void setDPerfil(String dPerfil)
   {
     this.dPerfil = dPerfil;
   }
 
   public String getDPerfil()
   {
     return this.dPerfil;
   }
 }

