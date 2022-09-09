 package model;
 
 import java.io.Serializable;
 import java.lang.reflect.Array;
 import java.util.Arrays;
 import javax.xml.namespace.QName;
 import org.apache.axis.description.ElementDesc;
 import org.apache.axis.description.TypeDesc;
 import org.apache.axis.encoding.Deserializer;
 import org.apache.axis.encoding.Serializer;
 import org.apache.axis.encoding.ser.BeanDeserializer;
 import org.apache.axis.encoding.ser.BeanSerializer;
 
 public class AttributesResult
   implements Serializable
 {
   private AttributeLdap[] atributo;
   private int resultado;
   private Object __equalsCalc;
   private boolean __hashCodeCalc;
   private static TypeDesc typeDesc = new TypeDesc(AttributesResult.class, true);
 
   public AttributesResult()
   {
     $init$();
   }
 
   public AttributesResult(AttributeLdap[] atributo, int resultado)
   {
     $init$();
     this.atributo = atributo;
     this.resultado = resultado;
   }
 
   public AttributeLdap[] getAtributo()
   {
     return this.atributo;
   }
 
   public void setAtributo(AttributeLdap[] atributo)
   {
     this.atributo = atributo;
   }
 
   public AttributeLdap getAtributo(int i)
   {
     return this.atributo[i];
   }
 
   public void setAtributo(int i, AttributeLdap _value) {
     this.atributo[i] = _value;
   }
 
   public int getResultado()
   {
     return this.resultado;
   }
 
   public void setResultado(int resultado)
   {
     this.resultado = resultado;
   }
   private void $init$() {
     this.__equalsCalc = null;
 
     this.__hashCodeCalc = false;
   }
 
   public synchronized boolean equals(Object obj)
   {
     if (!(obj instanceof AttributesResult))
     {
       return false;
     }AttributesResult other = (AttributesResult)obj;
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
 
     boolean _equals = ((this.atributo == null) && (other.getAtributo() == null)) || ((this.atributo != null) && (Arrays.equals(this.atributo, other.getAtributo())) && (this.resultado == other.getResultado()));
 
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
     if (getAtributo() != null) {
       for (int i = 0; 
         i < Array.getLength(getAtributo()); 
         i++) {
         Object obj = Array.get(getAtributo(), i);
         if ((obj != null) && (!obj.getClass().isArray()))
         {
           _hashCode += obj.hashCode();
         }
       }
     }
     _hashCode += getResultado();
     this.__hashCodeCalc = false;
 
     return _hashCode;
   }
 
   static
   {
     typeDesc.setXmlType(new QName("http://webservices.campass/", "attributesResult"));
     ElementDesc elemField = new ElementDesc();
     elemField.setFieldName("atributo");
     elemField.setXmlName(new QName("", "atributo"));
     elemField.setXmlType(new QName("http://webservices.campass/", "attributeLdap"));
     elemField.setNillable(false);
     elemField.setMaxOccursUnbounded(true);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("resultado");
     elemField.setXmlName(new QName("", "resultado"));
     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
     elemField.setNillable(false);
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

