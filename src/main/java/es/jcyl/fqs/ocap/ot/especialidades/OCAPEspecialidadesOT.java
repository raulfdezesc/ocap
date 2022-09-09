 package es.jcyl.fqs.ocap.ot.especialidades;
 
 import java.io.Serializable;
import java.text.Collator;
import java.util.Date;
import java.util.Locale;
 
 public class OCAPEspecialidadesOT
   implements Serializable, Comparable
 {
   protected String dDescripcion;
   protected String bBorrado;
   protected long cProfesionalId;
   protected Date fModificacion;
   protected Date fCreacion;
   protected String dNombre;
   protected long cEspecId;
   protected String dProfesionalNombre;
   protected String aCreadoPor;
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
   }
 
   public void setDDescripcion(String dDescripcion)
   {
     this.dDescripcion = dDescripcion;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public long getCProfesionalId()
   {
     return this.cProfesionalId;
   }
 
   public void setCProfesionalId(long cProfesionalId)
   {
     this.cProfesionalId = cProfesionalId;
   }
 
   public Date getFModificacion()
   {
     return this.fModificacion;
   }
 
   public void setFModificacion(Date fModificacion)
   {
     this.fModificacion = fModificacion;
   }
 
   public Date getFCreacion()
   {
     return this.fCreacion;
   }
 
   public void setFCreacion(Date fCreacion)
   {
     this.fCreacion = fCreacion;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
   }
 
   public long getCEspecId()
   {
     return this.cEspecId;
   }
 
   public void setCEspecId(long cEspecId)
   {
     this.cEspecId = cEspecId;
   }
 
   public void setDProfesionalNombre(String dProfesionalNombre)
   {
     this.dProfesionalNombre = dProfesionalNombre;
   }
 
   public String getDProfesionalNombre()
   {
     return this.dProfesionalNombre;
   }
 
   public void setACreadoPor(String aCreadoPor)
   {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }

	/**
	 * Ordenacion alfabética
	 */
	@Override
	public int compareTo(Object o) {
		try {
			Collator comparador = Collator.getInstance(new Locale("es"));
			comparador.setStrength(Collator.PRIMARY);

			if (o != null && ((OCAPEspecialidadesOT) o).getDNombre() != null && this.getDNombre() != null) {
				return comparador.compare(this.getDNombre(), ((OCAPEspecialidadesOT) o).getDNombre());
			} else {
				return 1;
			}
		} catch (Exception e) {
			return 1;
		}
	}
 }

