package es.jcyl.fqs.ocap.ot.itinerarios;

import java.io.Serializable;

public class OCAPItinerariosOT
  implements Serializable
{
  protected long cItinerarioId;
  protected String dNombre;
  protected String dDescripcion;
  protected String dIntroduccion;
  protected int nEvidencias;
  protected float nCreditosNecesarios;
  protected long cProfesionalId;
  protected long cEspecId;
  protected long cEstatutId;
  protected String bAsignadoCte;

  public void setCItinerarioId(long cItinerarioId)
  {
    this.cItinerarioId = cItinerarioId;
  }

  public long getCItinerarioId()
  {
    return this.cItinerarioId;
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

  public void setDIntroduccion(String dIntroduccion)
  {
    this.dIntroduccion = dIntroduccion;
  }

  public String getDIntroduccion()
  {
    return this.dIntroduccion;
  }

  public void setNEvidencias(int nEvidencias)
  {
    this.nEvidencias = nEvidencias;
  }

  public int getNEvidencias()
  {
    return this.nEvidencias;
  }

  public void setNCreditosNecesarios(float nCreditosNecesarios)
  {
    this.nCreditosNecesarios = nCreditosNecesarios;
  }

  public float getNCreditosNecesarios()
  {
    return this.nCreditosNecesarios;
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

  public void setCEstatutId(long cEstatutId)
  {
    this.cEstatutId = cEstatutId;
  }

  public long getCEstatutId()
  {
    return this.cEstatutId;
  }

  public void setBAsignadoCte(String bAsignadoCte)
  {
    this.bAsignadoCte = bAsignadoCte;
  }

  public String getBAsignadoCte()
  {
    return this.bAsignadoCte;
  }
}