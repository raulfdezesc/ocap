 package webservices;
 
 import java.rmi.RemoteException;
 import javax.xml.rpc.ServiceException;
 import javax.xml.rpc.Stub;
 import model.UsuarioLDAP;
 
 public class AuthenticationServiceProxy
   implements AuthenticationService
 {
   private String _endpoint;
   private AuthenticationService authenticationService;
 
   private void $init$()
   {
     this._endpoint = null;
     this.authenticationService = null;
   }
   public AuthenticationServiceProxy() { $init$();
     _initAuthenticationServiceProxy(); }
 
   private void _initAuthenticationServiceProxy() {
     try {
       this.authenticationService = new AuthenticationServiceServiceLocator().getAuthenticationServicePort();
       if (this.authenticationService != null)
         if (this._endpoint != null)
           ((Stub)this.authenticationService)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint);
         else
           this._endpoint = ((String)((Stub)this.authenticationService)._getProperty("javax.xml.rpc.service.endpoint.address"));
     }
     catch (ServiceException serviceException)
     {
     }
   }
 
   public String getEndpoint()
   {
     return this._endpoint;
   }
 
   public void setEndpoint(String endpoint) {
     this._endpoint = endpoint;
     if (this.authenticationService != null)
       ((Stub)this.authenticationService)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint);
   }
 
   public AuthenticationService getAuthenticationService()
   {
     if (this.authenticationService == null) {
       _initAuthenticationServiceProxy();
     }
     return this.authenticationService;
   }
 
   public int cambiaClave(UsuarioLDAP usuario, String clave_nueva, String aplicacion) throws RemoteException {
     if (this.authenticationService == null) {
       _initAuthenticationServiceProxy();
     }
     return this.authenticationService.cambiaClave(usuario, clave_nueva, aplicacion);
   }
 
   public int validaUsuario(UsuarioLDAP usuario, String aplicacion) throws RemoteException {
     if (this.authenticationService == null) {
       _initAuthenticationServiceProxy();
     }
     return this.authenticationService.validaUsuario(usuario, aplicacion);
   }
 }

