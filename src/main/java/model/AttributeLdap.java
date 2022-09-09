 package model;
 
 import java.io.Serializable;
 import javax.xml.namespace.QName;
 import org.apache.axis.description.ElementDesc;
 import org.apache.axis.description.TypeDesc;
 import org.apache.axis.encoding.Deserializer;
 import org.apache.axis.encoding.Serializer;
 import org.apache.axis.encoding.ser.BeanDeserializer;
 import org.apache.axis.encoding.ser.BeanSerializer;
 
 public class AttributeLdap
   implements Serializable
 {
   private String nombre;
   private String[] valor;
   private Object __equalsCalc;
   private boolean __hashCodeCalc;
   private static TypeDesc typeDesc = new TypeDesc(AttributeLdap.class, true);
 
   public AttributeLdap()
   {
     $init$();
   }
 
   public AttributeLdap(String nombre, String[] valor)
   {
     $init$();
     this.nombre = nombre;
     this.valor = valor;
   }
 
   public String getNombre()
   {
     return this.nombre;
   }
 
   public void setNombre(String nombre)
   {
     this.nombre = nombre;
   }
 
   public String[] getValor()
   {
     return this.valor;
   }
 
   public void setValor(String[] valor)
   {
     this.valor = valor;
   }
   private void $init$() {
     this.__equalsCalc = null;
 
     this.__hashCodeCalc = false;
   }
 
   public synchronized boolean equals(Object obj)
   {
     if (!(obj instanceof AttributeLdap))
     {
       return false;
     }AttributeLdap other = (AttributeLdap)obj;
     if (obj == null)
     {
       return false;
     }if (this == obj)
     {
       return true;
     }if (this.__equalsCalc != null)
     {
       return this.__equalsCalc == obj;
     }
     this.__equalsCalc = obj;
 
     boolean _equals = ((this.nombre == null) && (other.getNombre() == null)) || ((this.nombre != null) && (this.nombre.equals(other.getNombre())) && (((this.valor == null) && (other.getValor() == null)) || ((this.valor != null) && (this.valor.equals(other.getValor())))));
 
     this.__equalsCalc = null;
 
     return _equals;
   }
 
   public synchronized int hashCode()
   {
     if (this.__hashCodeCalc)
     {
       return 0;
     }
     this.__hashCodeCalc = true;
     int _hashCode = 1;
     if (getNombre() != null) {
       _hashCode += getNombre().hashCode();
     }
     if (getValor() != null) {
       _hashCode += getValor().hashCode();
     }
     this.__hashCodeCalc = false;
 
     return _hashCode;
   }
 
   static
   {
     typeDesc.setXmlType(new QName("http://webservices.campass/", "attributeLdap"));
     ElementDesc elemField = new ElementDesc();
     elemField.setFieldName("nombre");
     elemField.setXmlName(new QName("", "nombre"));
     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
     elemField.setNillable(false);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("valor");
     elemField.setXmlName(new QName("", "valor"));
     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
     elemField.setMinOccurs(0);
     elemField.setNillable(false);
     elemField.setMaxOccursUnbounded(true);
     typeDesc.addFieldDesc(elemField);
   }
 
   public static TypeDesc getTypeDesc()
   {
     return typeDesc;
   }
 
   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
   {
     return new BeanSerializer(_javaType, _xmlType, typeDesc);
   }
 
   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
   {
     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
   }
 }

