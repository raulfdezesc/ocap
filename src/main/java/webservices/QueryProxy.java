 package webservices;
 
 import java.rmi.RemoteException;
 import javax.xml.rpc.ServiceException;
 import javax.xml.rpc.Stub;
 import model.AttributesResult;
 import model.UsuarioLDAP;
 
 public class QueryProxy
   implements Query
 {
   private String _endpoint;
   private Query query;
 
   private void $init$()
   {
     this._endpoint = null;
     this.query = null;
   }
   public QueryProxy() { $init$();
     _initQueryProxy(); }
 
   public QueryProxy(String endpoint) {
     $init$();
     this._endpoint = endpoint;
     _initQueryProxy();
   }
 
   private void _initQueryProxy() {
     try {
       this.query = new QueryServiceLocator().getQueryPort();
       if (this.query != null)
         if (this._endpoint != null)
           ((Stub)this.query)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint);
         else
           this._endpoint = ((String)((Stub)this.query)._getProperty("javax.xml.rpc.service.endpoint.address"));
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
     if (this.query != null)
       ((Stub)this.query)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint);
   }
 
   public Query getQuery()
   {
     if (this.query == null) {
       _initQueryProxy();
     }
     return this.query;
   }
 
   public AttributesResult buscaAtributosUsuario(UsuarioLDAP usuario, String[] atributo, String aplicacion) throws RemoteException {
     if (this.query == null) {
       _initQueryProxy();
     }
     return this.query.buscaAtributosUsuario(usuario, atributo, aplicacion);
   }
 }

