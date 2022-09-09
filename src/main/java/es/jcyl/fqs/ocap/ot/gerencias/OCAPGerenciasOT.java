 package es.jcyl.fqs.ocap.ot.gerencias;
 
 import java.io.Serializable;
 import java.util.Date;
 
 public class OCAPGerenciasOT
   implements Serializable
 {
   protected long cGerenciaId;
   protected String cProvinciaId;
   protected long cTipogerenciaId;
   protected String dNombre;
   protected Date fCreacion;
   protected Date fModificacion;
   protected String bBorrado;
   protected String dProvinciaNombre;
   protected String dTipogerenciaNombre;
   protected String dNombreCorto;
   protected String aCodldap;
   protected String dGerente;
   protected String dDirector;
   protected String aCreadoPor;
 
   public long getCGerenciaId()
   {
     return this.cGerenciaId;
   }
   public void setCGerenciaId(long cGerenciaIdValue) {
     this.cGerenciaId = cGerenciaIdValue;
   }
 
   public String getCProvinciaId()
   {
     return this.cProvinciaId;
   }
   public void setCProvinciaId(String cProvinciaIdValue) {
     this.cProvinciaId = cProvinciaIdValue;
   }
 
   public long getCTipogerenciaId()
   {
     return this.cTipogerenciaId;
   }
   public void setCTipogerenciaId(long cTipogerenciaIdValue) {
     this.cTipogerenciaId = cTipogerenciaIdValue;
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
 
   public String getDProvinciaNombre()
   {
     return this.dProvinciaNombre;
   }
 
   public void setDProvinciaNombre(String dProvinciaNombre) {
     this.dProvinciaNombre = dProvinciaNombre;
   }
 
   public void setDTipogerenciaNombre(String dTipogerenciaNombre)
   {
     this.dTipogerenciaNombre = dTipogerenciaNombre;
   }
 
   public String getDTipogerenciaNombre()
   {
     return this.dTipogerenciaNombre;
   }
 
   public void setDNombreCorto(String dNombreCorto)
   {
     this.dNombreCorto = dNombreCorto;
   }
 
   public String getDNombreCorto()
   {
     return this.dNombreCorto;
   }
 
   public void setACodldap(String aCodldap)
   {
     this.aCodldap = aCodldap;
   }
 
   public String getACodldap()
   {
     return this.aCodldap;
   }
 
   public void setDGerente(String dGerente)
   {
     this.dGerente = dGerente;
   }
 
   public String getDGerente()
   {
     return this.dGerente;
   }
 
   public void setDDirector(String dDirector)
   {
     this.dDirector = dDirector;
   }
 
   public String getDDirector()
   {
     return this.dDirector;
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

