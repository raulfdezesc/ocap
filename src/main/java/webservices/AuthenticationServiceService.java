package webservices;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public abstract interface AuthenticationServiceService extends Service
{
  public abstract String getAuthenticationServicePortAddress();

  public abstract AuthenticationService getAuthenticationServicePort()
    throws ServiceException;

  public abstract AuthenticationService getAuthenticationServicePort(URL paramURL)
    throws ServiceException;
}

