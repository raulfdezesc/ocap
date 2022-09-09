 package es.jcyl.fqs.ocap.ot;
 
 import java.util.Date;
 
 public class OCAPUsuarioSeguOT
 {
   private String cUsrId;
   private String dApellidos;
   private String dNombre;
   private Date fCreacion;
   private String cUsrCreacion;
   private String aPassword;
   private Date fModificacion;
   private String cUsrModificacion;
   private String cUnidadId;
   private boolean bActivo;
   private String dSeguridad;
   private String cAplicacionId;
   private String cGrupoId;
 
   public String getCUsrId()
   {
     return this.cUsrId;
   }
 
   public String getDApellidos()
   {
     return this.dApellidos;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public Date getFCreacion()
   {
     return this.fCreacion;
   }
 
   public String getCUsrCreacion()
   {
     return this.cUsrCreacion;
   }
 
   public String getAPassword()
   {
     return this.aPassword;
   }
 
   public Date getFModificacion()
   {
     return this.fModificacion;
   }
 
   public String getCUsrModificacion()
   {
     return this.cUsrModificacion;
   }
 
   public String getCUnidadId()
   {
     return this.cUnidadId;
   }
 
   public boolean isBActivo()
   {
     return this.bActivo;
   }
 
   public String getDSeguridad()
   {
     return this.dSeguridad;
   }
 
   public String getCAplicacionId()
   {
     return this.cAplicacionId;
   }
 
   public String getCGrupoId()
   {
     return this.cGrupoId;
   }
 
   public void setCUsrId(String nuevo)
   {
     this.cUsrId = nuevo;
   }
 
   public void setDApellidos(String nuevo) {
     this.dApellidos = nuevo;
   }
 
   public void setDNombre(String nuevo) {
     this.dNombre = nuevo;
   }
 
   public void setFCreacion(Date nuevo) {
     this.fCreacion = nuevo;
   }
 
   public void setCUsrCreacion(String nuevo) {
     this.cUsrCreacion = nuevo;
   }
 
   public void setAPassword(String nuevo) {
     this.aPassword = nuevo;
   }
 
   public void setFModificacion(Date nuevo) {
     this.fModificacion = nuevo;
   }
 
   public void setCUsrModificacion(String nuevo) {
     this.cUsrModificacion = nuevo;
   }
 
   public void setCUnidadId(String nuevo) {
     this.cUnidadId = nuevo;
   }
 
   public void setBActivo(boolean nuevo) {
     this.bActivo = nuevo;
   }
 
   public void setDSeguridad(String nuevo) {
     this.dSeguridad = nuevo;
   }
 
   public void setCAplicacionId(String nuevo)
   {
     this.cAplicacionId = nuevo;
   }
 
   public void setCGrupoId(String nuevo) {
     this.cGrupoId = nuevo;
   }
 }

