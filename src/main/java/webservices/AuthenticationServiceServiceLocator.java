 package webservices;
 
 import config.Propiedades;
 import java.net.MalformedURLException;
 import java.net.URL;
 import java.rmi.Remote;
 import java.util.HashSet;
 import java.util.Iterator;
 import javax.xml.namespace.QName;
 import javax.xml.rpc.ServiceException;
 import org.apache.axis.AxisFault;
 import org.apache.axis.EngineConfiguration;
 import org.apache.axis.client.Service;
 import org.apache.axis.client.Stub;
 
 public class AuthenticationServiceServiceLocator extends Service
   implements AuthenticationServiceService
 {
   private static final String LDAP_CONFIG_FILE = "config" + System.getProperty("file.separator") + "WSConfig.properties";
   private String AuthenticationServicePort_address;
   private String AuthenticationServicePortWSDDServiceName;
   private HashSet ports;
 
   public AuthenticationServiceServiceLocator()
   {
     $init$();
     try {
       this.AuthenticationServicePort_address = Propiedades.getPropiedad("endpointAuthentication");
     } catch (Exception ex) {
       ex.printStackTrace();
     }
   }
 
   public AuthenticationServiceServiceLocator(EngineConfiguration config)
   {
     super(config); $init$();
     try {
       this.AuthenticationServicePort_address = Propiedades.getPropiedad("endpointAuthentication");
     } catch (Exception ex) {
       ex.printStackTrace();
     }
   }
 
   public AuthenticationServiceServiceLocator(String wsdlLoc, QName sName) throws ServiceException
   {
     super(wsdlLoc, sName); $init$();
     try {
       this.AuthenticationServicePort_address = Propiedades.getPropiedad("endpointAuthentication");
     } catch (Exception ex) {
       ex.printStackTrace();
     }
   }
 
   public String getAuthenticationServicePortAddress()
   {
     return this.AuthenticationServicePort_address;
   }
 
   private void $init$() {
     this.AuthenticationServicePortWSDDServiceName = "AuthenticationServicePort";
 
     this.ports = null;
   }
 
   public String getAuthenticationServicePortWSDDServiceName()
   {
     return this.AuthenticationServicePortWSDDServiceName;
   }
 
   public void setAuthenticationServicePortWSDDServiceName(String name) {
     this.AuthenticationServicePortWSDDServiceName = name;
   }
 
   public AuthenticationService getAuthenticationServicePort() throws ServiceException {
     URL endpoint;
     try {
       endpoint = new URL(this.AuthenticationServicePort_address);
     }
     catch (MalformedURLException e) {
       throw new ServiceException(e);
     }
 
     return getAuthenticationServicePort(endpoint);
   }
 
   public AuthenticationService getAuthenticationServicePort(URL portAddress) throws ServiceException {
     try {
       AuthenticationServiceBindingStub _stub = new AuthenticationServiceBindingStub(portAddress, this);
       _stub.setPortName(getAuthenticationServicePortWSDDServiceName());
       return _stub;
     }
     catch (AxisFault e) {
       return null;
     }
   }
 
   public void setAuthenticationServicePortEndpointAddress(String address) {
     this.AuthenticationServicePort_address = address;
   }
 
   public Remote getPort(Class serviceEndpointInterface)
     throws ServiceException
   {
     try
     {
       if (AuthenticationService.class.isAssignableFrom(serviceEndpointInterface)) {
         AuthenticationServiceBindingStub _stub = new AuthenticationServiceBindingStub(new URL(this.AuthenticationServicePort_address), this);
         _stub.setPortName(getAuthenticationServicePortWSDDServiceName());
         return _stub;
       }
     }
     catch (Throwable t) {
       throw new ServiceException(t);
     }
     throw new ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
   }
 
   public Remote getPort(QName portName, Class serviceEndpointInterface)
     throws ServiceException
   {
     if (portName == null)
     {
       return getPort(serviceEndpointInterface);
     }
     String inputPortName = portName.getLocalPart();
     if ("AuthenticationServicePort".equals(inputPortName))
     {
       return getAuthenticationServicePort();
     }
 
     Remote _stub = getPort(serviceEndpointInterface);
     ((Stub)_stub).setPortName(portName);
 
     return _stub;
   }
 
   public QName getServiceName()
   {
     return new QName("http://webservices.campass/", "AuthenticationServiceService");
   }
 
   public Iterator getPorts()
   {
     if (this.ports == null) {
       this.ports = new HashSet();
       this.ports.add(new QName("http://webservices.campass/", "AuthenticationServicePort"));
     }
 
     return this.ports.iterator();
   }
 
   public void setEndpointAddress(String portName, String address)
     throws ServiceException
   {
     if ("AuthenticationServicePort".equals(portName)) {
       setAuthenticationServicePortEndpointAddress(address);
     }
     else
     {
       throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
     }
   }
 
   public void setEndpointAddress(QName portName, String address)
     throws ServiceException
   {
     setEndpointAddress(portName.getLocalPart(), address);
   }
 }

