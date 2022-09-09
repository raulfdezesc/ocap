 package es.jcyl.fqs.ocap.ot.gestionCtes;
 
 import java.io.Serializable;
 
 public class OCAPGestionCtesOT
   implements Serializable
 {
   protected long cCteId;
   protected String dNombre;
   protected String dDescripcion;
   protected String fConstitucion;
   protected String aCreadoPorCte;
   protected String aModificadoPorCte;
   protected long cConvocatoriaId;
   protected String dConvocNombre;
   protected String aCreadoPorCac;
   protected String aModificadoPorCac;
   protected String bActivado;
   protected long cCteConvoId;
   protected long[] listaManuales;
   private long cProfesionalId;
   private long cGerenciaId;
 
   public void setCCteId(long cCteId)
   {
     this.cCteId = cCteId;
   }
 
   public long getCCteId()
   {
     return this.cCteId;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDDescripcion(String dDescripcion)
   {
     this.dDescripcion = dDescripcion;
   }
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
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
 
   public void setFConstitucion(String fConstitucion)
   {
     this.fConstitucion = fConstitucion;
   }
 
   public String getFConstitucion()
   {
     return this.fConstitucion;
   }
 
   public void setACreadoPorCte(String aCreadoPorCte)
   {
     this.aCreadoPorCte = aCreadoPorCte;
   }
 
   public String getACreadoPorCte()
   {
     return this.aCreadoPorCte;
   }
 
   public void setAModificadoPorCte(String aModificadoPorCte)
   {
     this.aModificadoPorCte = aModificadoPorCte;
   }
 
   public String getAModificadoPorCte()
   {
     return this.aModificadoPorCte;
   }
 
   public void setACreadoPorCac(String aCreadoPorCac)
   {
     this.aCreadoPorCac = aCreadoPorCac;
   }
 
   public String getACreadoPorCac()
   {
     return this.aCreadoPorCac;
   }
 
   public void setAModificadoPorCac(String aModificadoPorCac)
   {
     this.aModificadoPorCac = aModificadoPorCac;
   }
 
   public String getAModificadoPorCac()
   {
     return this.aModificadoPorCac;
   }
 
   public void setBActivado(String bActivado)
   {
     this.bActivado = bActivado;
   }
 
   public String getBActivado()
   {
     return this.bActivado;
   }
 
   public void setListaManuales(long[] listaManuales)
   {
     this.listaManuales = listaManuales;
   }
 
   public long[] getListaManuales()
   {
     return this.listaManuales;
   }
 
   public void setCCteConvoId(long cCteConvoId)
   {
     this.cCteConvoId = cCteConvoId;
   }
 
   public long getCCteConvoId()
   {
     return this.cCteConvoId;
   }
 
   public void setCProfesionalId(long cProfesionalId)
   {
     this.cProfesionalId = cProfesionalId;
   }
 
   public long getCProfesionalId()
   {
     return this.cProfesionalId;
   }
 
   public void setCGerenciaId(long cGerenciaId) {
     this.cGerenciaId = cGerenciaId;
   }
 
   public long getCGerenciaId()
   {
     return this.cGerenciaId;
   }
 }

