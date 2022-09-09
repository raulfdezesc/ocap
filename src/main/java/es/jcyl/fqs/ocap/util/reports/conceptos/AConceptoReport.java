 package es.jcyl.fqs.ocap.util.reports.conceptos;
 
 import java.util.ArrayList;
 import java.util.HashMap;
 import net.sf.jasperreports.engine.JRException;
 import net.sf.jasperreports.engine.JRField;
 
 public abstract class AConceptoReport
   implements IConceptoReport
 {
   protected ArrayList datos;
   protected HashMap linea;
   protected int numeroLinea;
 
   private void $init$()
   {
     this.datos = null;
 
     this.linea = null;
 
     this.numeroLinea = -1;
   }
 
   public void moveFirst() {
     this.numeroLinea = -1;
     this.linea = ((HashMap)this.datos.get(0));
   }
 
   public void addRow()
   {
     this.linea = new HashMap();
     this.datos.add(this.linea);
   }
 
   public void addRow(int i)
   {
     this.linea = new HashMap();
     this.datos.add(i, this.linea);
   }
 
   public void getRow(int i)
   {
     this.linea = ((HashMap)this.datos.get(i));
   }
 
   public void deleteRow(int i)
   {
     this.datos.remove(i);
   }
 
   public int size()
   {
     return this.datos.size();
   }
 
   public boolean isNull()
   {
     return (this.datos != null) && (this.datos.size() > 0);
   }
 
   public void putElement(String clave, Object valor)
   {
     this.linea.put(clave, valor);
   }
 
   public Object getElement(String clave)
   {
     return this.linea.get(clave);
   }
 
   public void deleteElement(String clave)
   {
     this.linea.remove(clave);
   }
 
   public boolean next()
     throws JRException
   {
     this.numeroLinea += 1;
 
     boolean esUltimo = false;
 
     if (this.datos != null) {
       esUltimo = this.numeroLinea < this.datos.size();
       if (esUltimo) {
         this.linea = ((HashMap)this.datos.get(this.numeroLinea));
       }
 
     }
 
     return esUltimo;
   }
 
   public Object getFieldValue(JRField campo)
     throws JRException
   {
     Object objeto = null;
 
     if (campo != null) {
       String nombreCampo = campo.getName();
       objeto = this.linea.get(nombreCampo);
     }
 
     return objeto;
   }
 
   public AConceptoReport()
   {
     $init$();
   }
 }

