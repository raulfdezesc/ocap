 package es.jcyl.fqs.ocap.ot.categProfesionales;
 
 import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
 import java.util.Date;
import java.util.Locale;

import es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
 
 public class OCAPCategProfesionalesOT
   implements Serializable, Comparable
 {
   protected long cEstatutId;
   protected String bBorrado;
   protected String dDescripcion;
   protected String dNombre;
   protected long cProfesionalId;
   protected Date fModificacion;
   protected Date fCreacion;
   protected String dEstatutNombre;
   protected String aCreadoPor;
   protected long cModalidadId;
   protected String dModalidadNombre;
   protected ArrayList listaCategorias;
 
   public long getCEstatutId()
   {
     return this.cEstatutId;
   }
 
   public void setCEstatutId(long cEstatutId)
   {
     this.cEstatutId = cEstatutId;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public String getDDescripcion()
   {
     return this.dDescripcion;
   }
 
   public void setDDescripcion(String dDescripcion)
   {
     this.dDescripcion = dDescripcion;
   }
 
   public String getDNombre()
   {
     return this.dNombre;
   }
 
   public void setDNombre(String dNombre)
   {
     this.dNombre = dNombre;
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
 
   public void setDEstatutNombre(String dEstatutNombre)
   {
     this.dEstatutNombre = dEstatutNombre;
   }
 
   public String getDEstatutNombre()
   {
     return this.dEstatutNombre;
   }
 
   public void setACreadoPor(String aCreadoPor)
   {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 
   public void setCModalidadId(long cModalidadId)
   {
     this.cModalidadId = cModalidadId;
   }
 
   public long getCModalidadId()
   {
     return this.cModalidadId;
   }
 
   public void setDModalidadNombre(String dModalidadNombre)
   {
     this.dModalidadNombre = dModalidadNombre;
   }
 
   public String getDModalidadNombre()
   {
     return this.dModalidadNombre;
   }
 
   public void setListaCategorias(ArrayList listaCategorias)
   {
     this.listaCategorias = listaCategorias;
   }
 
   public ArrayList getListaCategorias()
   {
     return this.listaCategorias;
   }
   
	
	/**
	 * Ordenacion alfabética
	 */
	@Override
	public int compareTo(Object o) {
		try {
			Collator comparador = Collator.getInstance(new Locale("es"));
			comparador.setStrength(Collator.PRIMARY);

			if (o != null && ((OCAPCategProfesionalesOT) o).getDNombre() != null && this.getDNombre() != null) {
				return comparador.compare(this.getDNombre(), ((OCAPCategProfesionalesOT) o).getDNombre());
			} else {
				return 1;
			}
		} catch (Exception e) {
			return 1;
		}
	}
   
 }

