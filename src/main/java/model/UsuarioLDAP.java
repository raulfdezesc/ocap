 package model;
 
 import java.io.Serializable;
 import javax.xml.namespace.QName;
 import org.apache.axis.description.ElementDesc;
 import org.apache.axis.description.TypeDesc;
 import org.apache.axis.encoding.Deserializer;
 import org.apache.axis.encoding.Serializer;
 import org.apache.axis.encoding.ser.BeanDeserializer;
 import org.apache.axis.encoding.ser.BeanSerializer;
 
 public class UsuarioLDAP
   implements Serializable
 {
   private String clave;
   private String uid_usuario;
   private Object __equalsCalc;
   private boolean __hashCodeCalc;
   private static TypeDesc typeDesc = new TypeDesc(UsuarioLDAP.class, true);
 
   public UsuarioLDAP()
   {
     $init$();
   }
 
   public UsuarioLDAP(String clave, String uid_usuario)
   {
     $init$();
     this.clave = clave;
     this.uid_usuario = uid_usuario;
   }
 
   public String getClave()
   {
     return this.clave;
   }
 
   public void setClave(String clave)
   {
     this.clave = clave;
   }
 
   public String getUid_usuario()
   {
     return this.uid_usuario;
   }
 
   public void setUid_usuario(String uid_usuario)
   {
     this.uid_usuario = uid_usuario;
   }
   private void $init$() {
     this.__equalsCalc = null;
 
     this.__hashCodeCalc = false;
   }
 
   public synchronized boolean equals(Object obj)
   {
     if (!(obj instanceof UsuarioLDAP))
     {
       return false;
     }UsuarioLDAP other = (UsuarioLDAP)obj;
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
 
     boolean _equals = ((this.clave == null) && (other.getClave() == null)) || ((this.clave != null) && (this.clave.equals(other.getClave())) && (((this.uid_usuario == null) && (other.getUid_usuario() == null)) || ((this.uid_usuario != null) && (this.uid_usuario.equals(other.getUid_usuario())))));
 
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
     if (getClave() != null) {
       _hashCode += getClave().hashCode();
     }
     if (getUid_usuario() != null) {
       _hashCode += getUid_usuario().hashCode();
     }
     this.__hashCodeCalc = false;
 
     return _hashCode;
   }
 
   static
   {
     typeDesc.setXmlType(new QName("http://webservices.campass/", "user"));
     ElementDesc elemField = new ElementDesc();
     elemField.setFieldName("clave");
     elemField.setXmlName(new QName("", "clave"));
     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
     elemField.setNillable(false);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("uid_usuario");
     elemField.setXmlName(new QName("", "uid_usuario"));
     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
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

