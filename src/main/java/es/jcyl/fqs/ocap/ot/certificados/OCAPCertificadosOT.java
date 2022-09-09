 package es.jcyl.fqs.ocap.ot.certificados;
 
 import java.util.Date;
 
 public class OCAPCertificadosOT
 {
   private long cCertificado_id;
   private long cExp_id;
   private String dTitulo;
   private String dDescripcion;
   private String fCreacion;
   private Date fModicacion;
   private String bBorrado;
 
   public long getCCertificado_id()
   {
     return this.cCertificado_id; } 
   public long getCExp_id() { return this.cExp_id; } 
   public String getDTitulo() { return this.dTitulo; } 
   public String getDDescripcion() { return this.dDescripcion; } 
   public String getFCreacion() { return this.fCreacion; } 
   public Date getFModicacion() { return this.fModicacion; } 
   public String getBBorrado() { return this.bBorrado; }
 
   public void setCCertificado_id(long cCertificado_id)
   {
     this.cCertificado_id = cCertificado_id; } 
   public void setCExp_id(long cExp_id) { this.cExp_id = cExp_id; } 
   public void setDTitulo(String dTitulo) { this.dTitulo = dTitulo; } 
   public void setDDescripcion(String dDesc) { this.dDescripcion = dDesc; } 
   public void setFCreacion(String fCreacion) { this.fCreacion = fCreacion; } 
   public void setFModicacion(Date fModicacion) { this.fModicacion = fModicacion; } 
   public void setBBorrado(String bBorrado) { this.bBorrado = bBorrado; }
 
 }

