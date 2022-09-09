 package webservices;
 
 import java.net.URL;
 import java.rmi.RemoteException;
 import java.util.Enumeration;
 import java.util.Properties;
 import java.util.Vector;
 import javax.xml.namespace.QName;
 import model.UsuarioLDAP;
 import org.apache.axis.AxisFault;
 import org.apache.axis.NoEndPointException;
 import org.apache.axis.client.Call;
 import org.apache.axis.client.Stub;
 import org.apache.axis.constants.Style;
 import org.apache.axis.constants.Use;
 import org.apache.axis.description.OperationDesc;
 import org.apache.axis.description.ParameterDesc;
 import org.apache.axis.encoding.DeserializerFactory;
 import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
 import org.apache.axis.encoding.ser.ArraySerializerFactory;
 import org.apache.axis.encoding.ser.BeanDeserializerFactory;
 import org.apache.axis.encoding.ser.BeanSerializerFactory;
 import org.apache.axis.encoding.ser.EnumDeserializerFactory;
 import org.apache.axis.encoding.ser.EnumSerializerFactory;
 import org.apache.axis.encoding.ser.SimpleDeserializerFactory;
 import org.apache.axis.encoding.ser.SimpleListDeserializerFactory;
 import org.apache.axis.encoding.ser.SimpleListSerializerFactory;
 import org.apache.axis.encoding.ser.SimpleSerializerFactory;
 import org.apache.axis.soap.SOAPConstants;
 import org.apache.axis.utils.JavaUtils;
 
 public class AuthenticationServiceBindingStub extends Stub
   implements AuthenticationService
 {
   private Vector cachedSerClasses;
   private Vector cachedSerQNames;
   private Vector cachedSerFactories;
   private Vector cachedDeserFactories;
   static OperationDesc[] _operations = new OperationDesc[2];
 
   private void $init$()
   {
     this.cachedSerClasses = new Vector();
     this.cachedSerQNames = new Vector();
     this.cachedSerFactories = new Vector();
     this.cachedDeserFactories = new Vector();
   }
 
   static
   {
     _initOperationDesc1();
   }
 
   private static void _initOperationDesc1()
   {
     OperationDesc oper = new OperationDesc();
     oper.setName("cambiaClave");
     ParameterDesc param = new ParameterDesc(new QName("", "usuario"), (byte)1, new QName("http://webservices.campass/", "user"), UsuarioLDAP.class, false, false);
     oper.addParameter(param);
     param = new ParameterDesc(new QName("", "clave_nueva"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
     oper.addParameter(param);
     param = new ParameterDesc(new QName("", "aplicacion"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
     oper.addParameter(param);
     oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
     oper.setReturnClass(Integer.TYPE);
     oper.setReturnQName(new QName("", "resultado"));
     oper.setStyle(Style.WRAPPED);
     oper.setUse(Use.LITERAL);
     _operations[0] = oper;
 
     oper = new OperationDesc();
     oper.setName("validaUsuario");
     param = new ParameterDesc(new QName("", "usuario"), (byte)1, new QName("http://webservices.campass/", "user"), UsuarioLDAP.class, false, false);
     oper.addParameter(param);
     param = new ParameterDesc(new QName("", "aplicacion"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
     oper.addParameter(param);
     oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
     oper.setReturnClass(Integer.TYPE);
     oper.setReturnQName(new QName("", "resultado"));
     oper.setStyle(Style.WRAPPED);
     oper.setUse(Use.LITERAL);
     _operations[1] = oper;
   }
 
   public AuthenticationServiceBindingStub() throws AxisFault
   {
     this(null);
   }
 
   public AuthenticationServiceBindingStub(URL endpointURL, javax.xml.rpc.Service service) throws AxisFault {
     this(service);
     this.cachedEndpoint = endpointURL;
   }
   public AuthenticationServiceBindingStub(javax.xml.rpc.Service service) throws AxisFault {
     $init$();
     if (service == null)
       this.service = new org.apache.axis.client.Service();
     else {
       this.service = service;
     }
     ((org.apache.axis.client.Service)this.service).setTypeMappingVersion("1.2");
 
     Class beansf = BeanSerializerFactory.class;
     Class beandf = BeanDeserializerFactory.class;
     Class enumsf = EnumSerializerFactory.class;
     Class enumdf = EnumDeserializerFactory.class;
     Class arraysf = ArraySerializerFactory.class;
     Class arraydf = ArrayDeserializerFactory.class;
     Class simplesf = SimpleSerializerFactory.class;
     Class simpledf = SimpleDeserializerFactory.class;
     Class simplelistsf = SimpleListSerializerFactory.class;
     Class simplelistdf = SimpleListDeserializerFactory.class;
     QName qName = new QName("http://webservices.campass/", "user");
     this.cachedSerQNames.add(qName);
     Class cls = UsuarioLDAP.class;
     this.cachedSerClasses.add(cls);
     this.cachedSerFactories.add(beansf);
     this.cachedDeserFactories.add(beandf);
   }
 
   protected Call createCall() throws RemoteException
   {
     try {
       Call _call = super._createCall();
       if (this.maintainSessionSet) {
         _call.setMaintainSession(this.maintainSession);
       }
       if (this.cachedUsername != null) {
         _call.setUsername(this.cachedUsername);
       }
       if (this.cachedPassword != null) {
         _call.setPassword(this.cachedPassword);
       }
       if (this.cachedEndpoint != null) {
         _call.setTargetEndpointAddress(this.cachedEndpoint);
       }
       if (this.cachedTimeout != null) {
         _call.setTimeout(this.cachedTimeout);
       }
       if (this.cachedPortName != null) {
         _call.setPortName(this.cachedPortName);
       }
       Enumeration keys = this.cachedProperties.keys();
       while (keys.hasMoreElements()) {
         String key = (String)keys.nextElement();
         _call.setProperty(key, this.cachedProperties.get(key));
       }
 
       synchronized (this) {
         if (firstCall())
         {
           _call.setEncodingStyle(null);
           for (int i = 0; i < this.cachedSerFactories.size(); i++) {
             Class cls = (Class)this.cachedSerClasses.get(i);
             QName qName = (QName)this.cachedSerQNames.get(i);
 
             Object x = this.cachedSerFactories.get(i);
             if ((x instanceof Class)) {
               Class sf = (Class)this.cachedSerFactories.get(i);
 
               Class df = (Class)this.cachedDeserFactories.get(i);
 
               _call.registerTypeMapping(cls, qName, sf, df, false);
             }
             else if ((x instanceof javax.xml.rpc.encoding.SerializerFactory)) {
               org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)this.cachedSerFactories.get(i);
 
               DeserializerFactory df = (DeserializerFactory)this.cachedDeserFactories.get(i);
 
               _call.registerTypeMapping(cls, qName, sf, df, false);
             }
           }
         }
       }
       return _call;
     }
     catch (Throwable _t) {
       throw new AxisFault("Failure trying to get the Call object", _t);
     }
   }
 
   public int cambiaClave(UsuarioLDAP usuario, String clave_nueva, String aplicacion) throws RemoteException {
     if (this.cachedEndpoint == null) {
       throw new NoEndPointException();
     }
     Call _call = createCall();
     _call.setOperation(_operations[0]);
     _call.setUseSOAPAction(true);
     _call.setSOAPActionURI("");
     _call.setEncodingStyle(null);
     _call.setProperty("sendXsiTypes", Boolean.FALSE);
     _call.setProperty("sendMultiRefs", Boolean.FALSE);
     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
     _call.setOperationName(new QName("http://webservices.campass/", "cambiaClave"));
 
     setRequestHeaders(_call);
     setAttachments(_call);
     try { Object _resp = _call.invoke(new Object[] { usuario, clave_nueva, aplicacion });
 
       if ((_resp instanceof RemoteException)) {
         throw ((RemoteException)_resp);
       }
 
       extractAttachments(_call);
       try {
         return ((Integer)_resp).intValue();
       } catch (Exception _exception) {
         return ((Integer)JavaUtils.convert(_resp, Integer.TYPE)).intValue();
       }
     } catch (AxisFault axisFaultException)
     {
       throw axisFaultException;
     }
   }
 
   public int validaUsuario(UsuarioLDAP usuario, String aplicacion) throws RemoteException {
     if (this.cachedEndpoint == null) {
       throw new NoEndPointException();
     }
     Call _call = createCall();
     _call.setOperation(_operations[1]);
     _call.setUseSOAPAction(true);
     _call.setSOAPActionURI("");
     _call.setEncodingStyle(null);
     _call.setProperty("sendXsiTypes", Boolean.FALSE);
     _call.setProperty("sendMultiRefs", Boolean.FALSE);
     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
     _call.setOperationName(new QName("http://webservices.campass/", "validaUsuario"));
 
     setRequestHeaders(_call);
     setAttachments(_call);
     try { Object _resp = _call.invoke(new Object[] { usuario, aplicacion });
 
       if ((_resp instanceof RemoteException)) {
         throw ((RemoteException)_resp);
       }
 
       extractAttachments(_call);
       try {
         return ((Integer)_resp).intValue();
       } catch (Exception _exception) {
         return ((Integer)JavaUtils.convert(_resp, Integer.TYPE)).intValue();
       }
     } catch (AxisFault axisFaultException)
     {
       throw axisFaultException;
     }
   }
 }

