 package es.jcyl.fqs.ocap.ot.grado;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPGradoOT
   implements Serializable
 {
   protected Long cGradoId;
   protected String dDescripcion;
   protected String dNombre;
   protected Date fCreacion;
   protected Date fModificacion;
   protected String bBorrado;
   protected Integer nAniosejercicio;
   protected Long cConvocatoriaId;
   protected String fFinRegistro;
   protected String aCreadoPor;
 
   public Long getCGradoId()
   {
     return this.cGradoId;
   }
   public void setCGradoId(Long cGradoIdValue) {
     this.cGradoId = cGradoIdValue;
   }
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
   }
   public void setDDescripcion(String dDescripcionValue) {
     this.dDescripcion = dDescripcionValue;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
   public void setDNombre(String dNombreValue) {
     this.dNombre = dNombreValue;
   }
 
   public Date getFCreacion()
   {
     return this.fCreacion;
   }
   public void setFCreacion(Date fCreacionValue) {
     this.fCreacion = fCreacionValue;
   }
 
   public Date getFModificacion()
   {
     return this.fModificacion;
   }
   public void setFModificacion(Date fModificacionValue) {
     this.fModificacion = fModificacionValue;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
   public void setBBorrado(String bBorradoValue) {
     this.bBorrado = bBorradoValue;
   }
 
   public Integer getNAniosejercicio()
   {
     return this.nAniosejercicio;
   }
   public void setNAniosejercicio(Integer nAniosejercicio) {
     this.nAniosejercicio = nAniosejercicio;
   }
 
   public void setCConvocatoriaId(Long cConvocatoriaId)
   {
     this.cConvocatoriaId = cConvocatoriaId;
   }
 
   public Long getCConvocatoriaId()
   {
     return this.cConvocatoriaId;
   }
 
   public void setFFinRegistro(String fFinRegistro)
   {
     this.fFinRegistro = fFinRegistro;
   }
 
   public String getFFinRegistro()
   {
     return this.fFinRegistro;
   }
 
   public void setACreadoPor(String aCreadoPor)
   {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 }

