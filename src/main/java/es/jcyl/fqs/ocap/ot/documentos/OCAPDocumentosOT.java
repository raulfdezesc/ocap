 package es.jcyl.fqs.ocap.ot.documentos;
 
 import java.io.InputStream;
 
 public class OCAPDocumentosOT
 {
   private long cDocumento_id;
   private String cUsuario_id;
   private String dTitulo;
   private String dDescripcion;
   private InputStream aDatos;
   private String aTipo_documento;
   private String aExt;
   private Long cExpId;
   private Long cCuestionarioId;
   private String dCuestionario;
   private String aCreadoPor;
   private Integer nRepeticion;
   private Integer nDocumentos;
   private String bFinalizado;
 
   public void setCDocumento_id(long cDocumento_id)
   {
     this.cDocumento_id = cDocumento_id;
   }
 
   public long getCDocumento_id()
   {
     return this.cDocumento_id;
   }
 
   public void setCUsuario_id(String cUsuario_id) {
     this.cUsuario_id = cUsuario_id;
   }
 
   public String getCUsuario_id()
   {
     return this.cUsuario_id;
   }
 
   public void setDTitulo(String dTitulo) {
     this.dTitulo = dTitulo;
   }
 
   public String getDTitulo()
   {
     return this.dTitulo;
   }
 
   public void setDDescripcion(String dDescripcion) {
     this.dDescripcion = dDescripcion;
   }
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
   }
 
   public void setADatos(InputStream aDatos) {
     this.aDatos = aDatos;
   }
 
   public InputStream getADatos()
   {
     return this.aDatos;
   }
 
   public void setATipo_documento(String aTipo_documento) {
     this.aTipo_documento = aTipo_documento;
   }
 
   public String getATipo_documento()
   {
     return this.aTipo_documento;
   }
 
   public void setAExt(String aExt) {
     this.aExt = aExt;
   }
 
   public String getAExt()
   {
     return this.aExt;
   }
 
   public void setCExpId(Long cExpId) {
     this.cExpId = cExpId;
   }
 
   public Long getCExpId()
   {
     return this.cExpId;
   }
 
   public void setCCuestionarioId(Long cCuestionarioId) {
     this.cCuestionarioId = cCuestionarioId;
   }
 
   public Long getCCuestionarioId()
   {
     return this.cCuestionarioId;
   }
 
   public void setDCuestionario(String dCuestionario) {
     this.dCuestionario = dCuestionario;
   }
 
   public String getDCuestionario()
   {
     return this.dCuestionario;
   }
 
   public void setACreadoPor(String aCreadoPor) {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 
   public void setNRepeticion(Integer nRepeticion) {
     this.nRepeticion = nRepeticion;
   }
 
   public Integer getNRepeticion()
   {
     return this.nRepeticion;
   }
 
   public void setNDocumentos(Integer nDocumentos) {
     this.nDocumentos = nDocumentos;
   }
 
   public Integer getNDocumentos()
   {
     return this.nDocumentos;
   }
 
   public void setBFinalizado(String bFinalizado) {
     this.bFinalizado = bFinalizado;
   }
 
   public String getBFinalizado()
   {
     return this.bFinalizado;
   }
 }

