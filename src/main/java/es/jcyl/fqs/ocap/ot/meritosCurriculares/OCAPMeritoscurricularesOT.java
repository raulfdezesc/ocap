 package es.jcyl.fqs.ocap.ot.meritosCurriculares;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Date;
 
 public class OCAPMeritoscurricularesOT
   implements Serializable
 {
  
	private static final long serialVersionUID = 8825020247841967822L;
	protected Date fCreacion;
   protected long cDefmeritoId;
   protected float nCreditos;
   protected String dDescripcion;
   protected long cMeritoId;
   protected String dObservaciones;
   protected String bBorrado;
   protected String dNombre;
   protected Date fModificacion;
   protected long cEstatutId;
   protected long cFactorId;
   protected long cActividadId;
   protected long cModalidadId;
   protected long cTipoId;
   protected String cTipoMerito;
   protected String dNombrecorto;
   protected String dEstatutNombre;
   protected String dFactorNombre;
   protected String dActividadNombre;
   protected String dModalidadNombre;
   protected String dDefmeritoNombre;
   protected String dTipoNombre;
   protected ArrayList listaMeritosUsuario;
   protected ArrayList listaExpmc;
   protected ArrayList listaExpmcCTSP;
   protected ArrayList listaExpmcCTSNP;
   protected ArrayList listaExpmcCTSM;
   protected Float creditos;
   protected Float creditosCTSP;
   protected Float creditosCTSNP;
   protected Float creditosCTSM;
   protected Float creditosValidados;
   protected Float creditosValidadosCeis;
   protected Float creditosValidadosCeisCTSP;
   protected Float creditosValidadosCeisCTSNP;
   protected Float creditosValidadosCeisCTSM;
   protected Float creditosValidadosCc;
   protected Float creditosValidadosCcCTSP;
   protected Float creditosValidadosCcCTSNP;
   protected Float creditosValidadosCcCTSM;
   private String bAcreditado;
   private String meritosAnterioresValidados;
 
   public Date getFCreacion()
   {
     return this.fCreacion;
   }
 
   public void setFCreacion(Date fCreacion)
   {
     this.fCreacion = fCreacion;
   }
 
   public long getCDefmeritoId()
   {
     return this.cDefmeritoId;
   }
 
   public void setCDefmeritoId(long cDefmeritoId)
   {
     this.cDefmeritoId = cDefmeritoId;
   }
 
   public float getNCreditos()
   {
     return this.nCreditos;
   }
 
   public void setNCreditos(float nCreditos)
   {
     this.nCreditos = nCreditos;
   }
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
   }
 
   public void setDDescripcion(String dDescripcion)
   {
     this.dDescripcion = dDescripcion;
   }
 
   public long getCMeritoId()
   {
     return this.cMeritoId;
   }
 
   public void setCMeritoId(long cMeritoId)
   {
     this.cMeritoId = cMeritoId;
   }
 
   public String getDObservaciones()
   {
     return this.dObservaciones;
   }
 
   public void setDObservaciones(String dObservaciones)
   {
     this.dObservaciones = dObservaciones;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public Date getFModificacion()
   {
     return this.fModificacion;
   }
 
   public void setFModificacion(Date fModificacion)
   {
     this.fModificacion = fModificacion;
   }
 
   public void setListaMeritosUsuario(ArrayList listaExpmc) {
     this.listaMeritosUsuario = listaExpmc;
   }
 
   public ArrayList getListaMeritosUsuario()
   {
     return this.listaMeritosUsuario;
   }
 
   public void setCEstatutId(long cEstatutId)
   {
     this.cEstatutId = cEstatutId;
   }
 
   public long getCEstatutId()
   {
     return this.cEstatutId;
   }
 
   public void setCFactorId(long cFactorId)
   {
     this.cFactorId = cFactorId;
   }
 
   public long getCFactorId()
   {
     return this.cFactorId;
   }
 
   public void setCActividadId(long cActividadId)
   {
     this.cActividadId = cActividadId;
   }
 
   public long getCActividadId()
   {
     return this.cActividadId;
   }
 
   public void setCModalidadId(long cModalidadId)
   {
     this.cModalidadId = cModalidadId;
   }
 
   public long getCModalidadId()
   {
     return this.cModalidadId;
   }
 
   public void setDEstatutNombre(String dEstatutNombre)
   {
     this.dEstatutNombre = dEstatutNombre;
   }
 
   public String getDEstatutNombre()
   {
     return this.dEstatutNombre;
   }
 
   public void setDFactorNombre(String dFactorNombre)
   {
     this.dFactorNombre = dFactorNombre;
   }
 
   public String getDFactorNombre()
   {
     return this.dFactorNombre;
   }
 
   public void setDActividadNombre(String dActividadNombre)
   {
     this.dActividadNombre = dActividadNombre;
   }
 
   public String getDActividadNombre()
   {
     return this.dActividadNombre;
   }
 
   public void setDModalidadNombre(String dModalidadNombre)
   {
     this.dModalidadNombre = dModalidadNombre;
   }
 
   public String getDModalidadNombre()
   {
     return this.dModalidadNombre;
   }
 
   public void setDDefmeritoNombre(String dDefmeritoNombre)
   {
     this.dDefmeritoNombre = dDefmeritoNombre;
   }
 
   public String getDDefmeritoNombre()
   {
     return this.dDefmeritoNombre;
   }
 
   public void setCTipoMerito(String cTipoMerito) {
     this.cTipoMerito = cTipoMerito;
   }
 
   public String getCTipoMerito()
   {
     return this.cTipoMerito;
   }
 
   public void setCTipoId(long cTipoId) {
     this.cTipoId = cTipoId;
   }
 
   public long getCTipoId()
   {
     return this.cTipoId;
   }
 
   public void setDNombrecorto(String dNombrecorto) {
     this.dNombrecorto = dNombrecorto;
   }
 
   public String getDNombrecorto()
   {
     return this.dNombrecorto;
   }
 
   public void setDTipoNombre(String dTipoNombre) {
     this.dTipoNombre = dTipoNombre;
   }
 
   public String getDTipoNombre()
   {
     return this.dTipoNombre;
   }
 
   public void setCreditos(Float creditos) {
     this.creditos = creditos;
   }
 
   public Float getCreditos()
   {
     return this.creditos;
   }
 
   public void setCreditosValidados(Float creditosValidados) {
     this.creditosValidados = creditosValidados;
   }
 
   public Float getCreditosValidados()
   {
     return this.creditosValidados;
   }
 
   public void setBAcreditado(String bAcreditado) {
     this.bAcreditado = bAcreditado;
   }
 
   public String getBAcreditado()
   {
     return this.bAcreditado;
   }
 
   public void setCreditosValidadosCeis(Float creditosValidadosCeis) {
     this.creditosValidadosCeis = creditosValidadosCeis;
   }
 
   public Float getCreditosValidadosCeis()
   {
     return this.creditosValidadosCeis;
   }
 
   public void setCreditosValidadosCc(Float creditosValidadosCc) {
     this.creditosValidadosCc = creditosValidadosCc;
   }
 
   public Float getCreditosValidadosCc()
   {
     return this.creditosValidadosCc;
   }
 
   public void setListaExpmcCTSP(ArrayList listaExpmcCTSP) {
     this.listaExpmcCTSP = listaExpmcCTSP;
   }
 
   public ArrayList getListaExpmcCTSP()
   {
     return this.listaExpmcCTSP;
   }
 
   public void setListaExpmcCTSNP(ArrayList listaExpmcCTSNP) {
     this.listaExpmcCTSNP = listaExpmcCTSNP;
   }
 
   public ArrayList getListaExpmcCTSNP()
   {
     return this.listaExpmcCTSNP;
   }
 
   public void setListaExpmcCTSM(ArrayList listaExpmcCTSM) {
     this.listaExpmcCTSM = listaExpmcCTSM;
   }
 
   public ArrayList getListaExpmcCTSM()
   {
     return this.listaExpmcCTSM;
   }
 
   public void setCreditosCTSP(Float creditosCTSP) {
     this.creditosCTSP = creditosCTSP;
   }
 
   public Float getCreditosCTSP()
   {
     return this.creditosCTSP;
   }
 
   public void setCreditosCTSNP(Float creditosCTSNP) {
     this.creditosCTSNP = creditosCTSNP;
   }
 
   public Float getCreditosCTSNP()
   {
     return this.creditosCTSNP;
   }
 
   public void setCreditosCTSM(Float creditosCTSM) {
     this.creditosCTSM = creditosCTSM;
   }
 
   public Float getCreditosCTSM()
   {
     return this.creditosCTSM;
   }
 
   public void setListaExpmc(ArrayList listaExpmc) {
     this.listaExpmc = listaExpmc;
   }
 
   public ArrayList getListaExpmc()
   {
     return this.listaExpmc;
   }
 
   public void setCreditosValidadosCeisCTSP(Float creditosValidadosCeisCTSP) {
     this.creditosValidadosCeisCTSP = creditosValidadosCeisCTSP;
   }
 
   public Float getCreditosValidadosCeisCTSP()
   {
     return this.creditosValidadosCeisCTSP;
   }
 
   public void setCreditosValidadosCeisCTSNP(Float creditosValidadosCeisCTSNP) {
     this.creditosValidadosCeisCTSNP = creditosValidadosCeisCTSNP;
   }
 
   public Float getCreditosValidadosCeisCTSNP()
   {
     return this.creditosValidadosCeisCTSNP;
   }
 
   public void setCreditosValidadosCeisCTSM(Float creditosValidadosCeisCTSM) {
     this.creditosValidadosCeisCTSM = creditosValidadosCeisCTSM;
   }
 
   public Float getCreditosValidadosCeisCTSM()
   {
     return this.creditosValidadosCeisCTSM;
   }
 
   public void setCreditosValidadosCcCTSP(Float creditosValidadosCcCTSP) {
     this.creditosValidadosCcCTSP = creditosValidadosCcCTSP;
   }
 
   public Float getCreditosValidadosCcCTSP()
   {
     return this.creditosValidadosCcCTSP;
   }
 
   public void setCreditosValidadosCcCTSNP(Float creditosValidadosCcCTSNP) {
     this.creditosValidadosCcCTSNP = creditosValidadosCcCTSNP;
   }
 
   public Float getCreditosValidadosCcCTSNP()
   {
     return this.creditosValidadosCcCTSNP;
   }
 
   public void setCreditosValidadosCcCTSM(Float creditosValidadosCcCTSM) {
     this.creditosValidadosCcCTSM = creditosValidadosCcCTSM;
   }
 
   public Float getCreditosValidadosCcCTSM()
   {
     return this.creditosValidadosCcCTSM;
   }

public String getMeritosAnterioresValidados() {
	return meritosAnterioresValidados;
}

public void setMeritosAnterioresValidados(String meritosAnterioresValidados) {
	this.meritosAnterioresValidados = meritosAnterioresValidados;
}
 }

