 package webservices;
 
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
 
 public class QueryServiceLocator extends Service
   implements QueryService
 {
   private String QueryPort_address;
   private String QueryPortWSDDServiceName;
   private HashSet ports;
 
   public QueryServiceLocator()
   {
     $init$();
   }
 
   public QueryServiceLocator(EngineConfiguration config)
   {
     super(config); $init$();
   }
 
   public QueryServiceLocator(String wsdlLoc, QName sName) throws ServiceException {
     super(wsdlLoc, sName); $init$();
   }
 
   private void $init$() {
     this.QueryPort_address = "https://10.36.60.143:8443/LDAPWebServices/QueryService";
 
     this.QueryPortWSDDServiceName = "QueryPort";
 
     this.ports = null;
   }
 
   public String getQueryPortAddress()
   {
     return this.QueryPort_address;
   }
 
   public String getQueryPortWSDDServiceName()
   {
     return this.QueryPortWSDDServiceName;
   }
 
   public void setQueryPortWSDDServiceName(String name) {
     this.QueryPortWSDDServiceName = name;
   }
 
   public Query getQueryPort() throws ServiceException {
     URL endpoint;
     try {
       endpoint = new URL(this.QueryPort_address);
     }
     catch (MalformedURLException e) {
       throw new ServiceException(e);
     }
 
     return getQueryPort(endpoint);
   }
 
   public Query getQueryPort(URL portAddress) throws ServiceException {
     try {
       QueryBindingStub _stub = new QueryBindingStub(portAddress, this);
       _stub.setPortName(getQueryPortWSDDServiceName());
       return _stub;
     }
     catch (AxisFault e) {
       return null;
     }
   }
 
   public void setQueryPortEndpointAddress(String address) {
     this.QueryPort_address = address;
   }
 
   public Remote getPort(Class serviceEndpointInterface)
     throws ServiceException
   {
     try
     {
       if (Query.class.isAssignableFrom(serviceEndpointInterface)) {
         QueryBindingStub _stub = new QueryBindingStub(new URL(this.QueryPort_address), this);
         _stub.setPortName(getQueryPortWSDDServiceName());
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
     if ("QueryPort".equals(inputPortName))
     {
       return getQueryPort();
     }
 
     Remote _stub = getPort(serviceEndpointInterface);
     ((Stub)_stub).setPortName(portName);
 
     return _stub;
   }
 
   public QName getServiceName()
   {
     return new QName("http://webservices.campass/", "QueryService");
   }
 
   public Iterator getPorts()
   {
     if (this.ports == null) {
       this.ports = new HashSet();
       this.ports.add(new QName("http://webservices.campass/", "QueryPort"));
     }
 
     return this.ports.iterator();
   }
 
   public void setEndpointAddress(String portName, String address)
     throws ServiceException
   {
     if ("QueryPort".equals(portName)) {
       setQueryPortEndpointAddress(address);
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

