package webservices;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.AttributesResult;
import model.UsuarioLDAP;

public abstract interface Query extends Remote
{
  public abstract AttributesResult buscaAtributosUsuario(UsuarioLDAP paramUsuarioLDAP, String[] paramArrayOfString, String paramString)
    throws RemoteException;
}

