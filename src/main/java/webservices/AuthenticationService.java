package webservices;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.UsuarioLDAP;

public abstract interface AuthenticationService extends Remote
{
  public abstract int cambiaClave(UsuarioLDAP paramUsuarioLDAP, String paramString1, String paramString2)
    throws RemoteException;

  public abstract int validaUsuario(UsuarioLDAP paramUsuarioLDAP, String paramString)
    throws RemoteException;
}

